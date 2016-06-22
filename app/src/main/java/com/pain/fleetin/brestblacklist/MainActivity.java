package com.pain.fleetin.brestblacklist;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pain.fleetin.brestblacklist.adapter.BeautyAndHealthAdapter;
import com.pain.fleetin.brestblacklist.adapter.TabAdapter;
import com.pain.fleetin.brestblacklist.database.DBHelper;
import com.pain.fleetin.brestblacklist.list_fragments.BeautyAndHealthFragment;
import com.pain.fleetin.brestblacklist.list_fragments.CrimeFragment;
import com.pain.fleetin.brestblacklist.model.ModelCard;
import com.pain.fleetin.brestblacklist.new_crime_dialog.AddingCrimeDialogFragment;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiGroups;
import com.vk.sdk.api.methods.VKApiWall;
import com.vk.sdk.api.model.VKList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AddingCrimeDialogFragment.AddingCrimeListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private FragmentManager fragmentManager;
    private TabAdapter tabAdapter;

    private CrimeFragment crimeFragment;

    private BeautyAndHealthFragment beautyAndHealthFragment;
    /*private BuyFragment buyFragment;
    private FunFragment funFragment;
    private PubFragment pubFragment;
    private TransportFragment transportFragment;*/


    public DBHelper dbHelper;
    public BeautyAndHealthAdapter beautyAndHealthAdapter;
    Calendar calendar;
    SearchView searchView;

    private String[] scope = {VKScope.GROUPS, VKScope.PHOTOS, VKScope.WALL, VKScope.PAGES};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        //if (!VKSdk.wakeUpSession(this)){
            VKSdk.login(this, scope);
        //}

        dbHelper = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        initNavigationView();

        beautyAndHealthFragment = new BeautyAndHealthFragment();

        setUI();
    }

    // 71924797 fleetin_pain
    // 122565629 closed noname
    // 84025643 brest blacklist

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {

                VKRequest vkRequest = new VKApiGroups()
                        .getById(VKParameters.from("group_ids", 84025643));

                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {

                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKList vkList = (VKList) response.parsedModel;

                        try {
                            VKRequest vkRequest1 = new VKApiWall()
                                    .get(VKParameters.from(VKApiConst.OWNER_ID, "-" +
                                            vkList.get(0).fields.getInt("id"), VKApiConst.COUNT, 100));
                            vkRequest1.executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    super.onComplete(response);
                                    ArrayList<ModelCard> crimePosts = new ArrayList<>();
                                    try {
                                        JSONObject jsonObject = (JSONObject) response.json.get("response");
                                        JSONArray jsonArray = (JSONArray) jsonObject.get("items");

                                        for (int i = 0; i < jsonArray.length(); i++){
                                            JSONObject post = (JSONObject) jsonArray.get(i);
                                            ModelCard modelCard = new ModelCard();

                                            if (!post.getString("text").equals("")){
                                                modelCard.setTitle(post.getString("text"));
                                                //нужно домножить на 1000, потому что Date() ожидает
                                                //милисекунды, а response возвращает unxTimeStamp:
                                                modelCard.setDate(post.getLong("date") * 1000);
                                                try {
                                                    JSONArray attachmentsArray = (JSONArray) post.get("attachments");
                                                    for (int j = 0; j < attachmentsArray.length(); j++){
                                                        JSONObject firstAttachment = (JSONObject) attachmentsArray.get(j);
                                                        if (((JSONObject) attachmentsArray.get(j)).get("type").equals("photo")){
                                                            JSONObject photo = (JSONObject) firstAttachment.get("photo");
                                                            modelCard.setPictureURL(photo.getString("photo_604"));
                                                            break;
                                                        }
                                                    }
                                                } catch (JSONException e){
                                                    modelCard.setPictureURL(null);
                                                    e.printStackTrace();
                                                }

                                                crimePosts.add(modelCard);
                                            }

                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    /*for (int k = 0; k < crimePosts.size(); k++){
                                        System.out.println(crimePosts.get(k));
                                    }*/

                                    vkQuery(crimePosts);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onError(VKError error) {
                // Произошла ошибка авторизации (например, пользователь запретил авторизацию)
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        })) {

        }
    }

    private void setUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            setSupportActionBar(toolbar);
        }

        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                beautyAndHealthFragment.findCrimes(newText);
                return false;
            }
        });

        /*TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_beauty));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_buy));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_fun));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_pub));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_transport));*/

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(fragmentManager, 1);

        viewPager.setAdapter(tabAdapter);
        /*viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });*/

        beautyAndHealthFragment = (BeautyAndHealthFragment) tabAdapter.getItem(TabAdapter.BEAUTY_AND_HEALTH_FRAGMENT_POSITION);
        /*buyFragment = (BuyFragment) tabAdapter.getItem(TabAdapter.BUY_FRAGMENT_POSITION);
        funFragment = (FunFragment) tabAdapter.getItem(TabAdapter.FUN_FRAGMENT_POSITION);
        pubFragment = (PubFragment) tabAdapter.getItem(TabAdapter.PUB_FRAGMENT_POSITION);
        transportFragment = (TransportFragment) tabAdapter.getItem(TabAdapter.TRANSPORT_FRAGMENT_POSITION);*/

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingCrimeDialogFragment = new AddingCrimeDialogFragment();
                addingCrimeDialogFragment.show(fragmentManager, "AddingCrimeDialogFragment");

            }
        });
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Кнопка для открытия NavigationView
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void vkQuery (List<ModelCard> crimePosts){
        /*DBHelper dbHelper = new DBHelper(this);
        dbHelper.onUpgrade(DBHelper.DATABASE_NAME);*/
        for (int i = 0; i < crimePosts.size(); i++){
            beautyAndHealthFragment.addCrime(crimePosts.get(i), true);
        }
    }


    @Override
    public void onCrimeAdded(ModelCard newCrime) {

        beautyAndHealthFragment.addCrime(newCrime, false);

        Toast.makeText(this, "Crime Added", Toast.LENGTH_LONG).show();
    }



    @Override
    public void onCrimeAddingCanceled() {
        Toast.makeText(this, "Crime Adding Canceled", Toast.LENGTH_LONG).show();
    }


}
