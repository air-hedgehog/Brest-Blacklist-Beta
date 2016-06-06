package com.pain.fleetin.brestblacklist;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pain.fleetin.brestblacklist.adapter.TabAdapter;
import com.pain.fleetin.brestblacklist.database.DBHelper;
import com.pain.fleetin.brestblacklist.list_fragments.BeautyAndHealthFragment;
import com.pain.fleetin.brestblacklist.list_fragments.BuyFragment;
import com.pain.fleetin.brestblacklist.list_fragments.CrimeFragment;
import com.pain.fleetin.brestblacklist.list_fragments.FunFragment;
import com.pain.fleetin.brestblacklist.list_fragments.PubFragment;
import com.pain.fleetin.brestblacklist.list_fragments.TransportFragment;
import com.pain.fleetin.brestblacklist.model.ModelCard;
import com.pain.fleetin.brestblacklist.new_crime_dialog.AddingCrimeDialogFragment;

public class MainActivity extends AppCompatActivity
        implements AddingCrimeDialogFragment.AddingCrimeListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private FragmentManager fragmentManager;
    private TabAdapter tabAdapter;

    private CrimeFragment crimeFragment;
    private ModelCard modelCard;

    private BeautyAndHealthFragment beautyAndHealthFragment;
    private BuyFragment buyFragment;
    private FunFragment funFragment;
    private PubFragment pubFragment;
    private TransportFragment transportFragment;

    public int position = 0;

    public String hashtag_beauty;
    public String hashtag_buy;
    public String hashtag_fun;
    public String hashtag_pub;
    public String hashtag_transport;

    public DBHelper dbHelper;

    //private String[] scope = {VKScope.GROUPS, VKScope.PHOTOS, VKScope.WALL};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        hashtag_beauty = getResources().getString(R.string.hashtag_beauty);
        hashtag_buy = getResources().getString(R.string.hashtag_buy);
        hashtag_fun = getResources().getString(R.string.hashtag_fun);
        hashtag_pub = getResources().getString(R.string.hashtag_pub);
        hashtag_transport = getResources().getString(R.string.hashtag_transport);

        /*if (!VKSdk.wakeUpSession(this)){
            VKSdk.login(this);
        }*/

        dbHelper = new DBHelper(getApplicationContext());
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        initNavigationView();

        modelCard = new ModelCard();

        setUI();


    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                //71924797 fleetin_pain
                final VKRequest vkRequest = new VKApiGroups().getById(VKParameters.from("group_ids", 71924797));
                vkRequest.executeWithListener(new VKRequest.VKRequestListener() {
                    @Override
                    public void onComplete(VKResponse response) {
                        super.onComplete(response);

                        VKList vkList = (VKList) response.parsedModel;

                        try {
                            VKRequest vkRequest1 = new VKApiWall()
                                    .get(VKParameters.from(VKApiConst.OWNER_ID, "-" + vkList.get(0).fields.getInt("id"), VKApiConst.COUNT, 10));
                            vkRequest1.executeWithListener(new VKRequest.VKRequestListener() {
                                @Override
                                public void onComplete(VKResponse response) {
                                    super.onComplete(response);

                                    System.out.println(response.responseString);
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
            super.onActivityResult(requestCode, resultCode, data);
        }
    }*/

    private void setUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            setSupportActionBar(toolbar);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_beauty));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_buy));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_fun));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_pub));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_item_transport));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabAdapter = new TabAdapter(fragmentManager, 5);

        viewPager.setAdapter(tabAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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
        });

        beautyAndHealthFragment = (BeautyAndHealthFragment) tabAdapter.getItem(TabAdapter.BEAUTY_AND_HEALTH_FRAGMENT_POSITION);
        buyFragment = (BuyFragment) tabAdapter.getItem(TabAdapter.BUY_FRAGMENT_POSITION);
        funFragment = (FunFragment) tabAdapter.getItem(TabAdapter.FUN_FRAGMENT_POSITION);
        pubFragment = (PubFragment) tabAdapter.getItem(TabAdapter.PUB_FRAGMENT_POSITION);
        transportFragment = (TransportFragment) tabAdapter.getItem(TabAdapter.TRANSPORT_FRAGMENT_POSITION);

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


    @Override
    public void onCrimeAdded(ModelCard newCrime) {
        if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_beauty))) {
            beautyAndHealthFragment.addCrime(newCrime, true);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_buy))) {
            buyFragment.addCrime(newCrime, true);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_fun))) {
            funFragment.addCrime(newCrime, true);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_pub))) {
            pubFragment.addCrime(newCrime, true);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_transport))) {
            transportFragment.addCrime(newCrime, true);
        }

        Toast.makeText(this, "Crime Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCrimeAddingCanceled() {
        Toast.makeText(this, "Crime Adding Canceled", Toast.LENGTH_LONG).show();
    }


}
