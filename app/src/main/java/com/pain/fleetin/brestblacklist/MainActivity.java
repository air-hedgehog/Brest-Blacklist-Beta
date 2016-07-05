package com.pain.fleetin.brestblacklist;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pain.fleetin.brestblacklist.VKUtils.LoginActivity;
import com.pain.fleetin.brestblacklist.VKUtils.VKUpload;
import com.pain.fleetin.brestblacklist.adapter.MainFragmentAdapter;
import com.pain.fleetin.brestblacklist.adapter.TabAdapter;
import com.pain.fleetin.brestblacklist.database.DBHelper;
import com.pain.fleetin.brestblacklist.list_fragments.MainFragment;
import com.pain.fleetin.brestblacklist.model.ModelCard;
import com.pain.fleetin.brestblacklist.new_crime_dialog.AddingCrimeDialogFragment;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKApiConst;
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
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AddingCrimeDialogFragment.AddingCrimeListener {


    public DBHelper dbHelper;
    public MainFragmentAdapter mainFragmentAdapter;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private MainFragment mainFragment;
    private VKUpload vkUpload;
    private VKUpload.UploadOnePhoto uploadOnePhoto;

    public NotificationManager nm;

    SearchView searchView;

    String hashTagBeauty;
    String hashTagBuy;
    String hashTagFun;
    String hashTagPub;
    String hashTagTransport;
    String hashTagDeceivers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(getApplicationContext());
        fragmentManager = getFragmentManager();

        mainFragment = new MainFragment();
        mainFragmentAdapter = new MainFragmentAdapter(mainFragment, this);

        setUI();
        mainFragmentAdapter.removeAllItems();
        dbHelper.query().removeCrimes();
        vkRequest();

        hashTagBeauty = getResources().getString(R.string.hashtag_beauty);
        hashTagBuy = getResources().getString(R.string.hashtag_buy);
        hashTagFun = getResources().getString(R.string.hashtag_fun);
        hashTagPub = getResources().getString(R.string.hashtag_pub);
        hashTagTransport = getResources().getString(R.string.hashtag_transport);
        hashTagDeceivers = getResources().getString(R.string.hastag_deceivers);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (vkUpload != null) {
            if (uploadOnePhoto != null)
                uploadOnePhoto.cancel(true);


            nm.cancel(Utils.UPLOAD_NOTIFICATION_ID);
        }
    }

    public void vkRequest() {
        mainFragmentAdapter.removeAllItems();
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

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject post = (JSONObject) jsonArray.get(i);
                                    ModelCard modelCard = new ModelCard();

                                    if (!post.getString("text").equals("")) {
                                        modelCard.setPostId(post.getLong("id"));
                                        System.out.println(modelCard.getPostId());
                                        modelCard.setTitle(post.getString("text"));
                                        //нужно домножить на 1000, потому что Date() ожидает
                                        //милисекунды, а response возвращает unxTimeStamp:
                                        modelCard.setDate(post.getLong("date") * 1000);
                                        try {
                                            JSONArray attachmentsArray = (JSONArray) post.get("attachments");
                                            for (int j = 0; j < attachmentsArray.length(); j++) {
                                                JSONObject firstAttachment = (JSONObject) attachmentsArray.get(j);
                                                if (((JSONObject) attachmentsArray.get(j)).has("photo")) {
                                                    JSONObject photo = (JSONObject) firstAttachment.get("photo");
                                                    modelCard.setPictureURL(photo.getString("photo_604"));
                                                    break;
                                                }
                                            }
                                        } catch (JSONException e) {
                                            modelCard.setPictureURL(null);
                                            e.printStackTrace();
                                        }

                                        crimePosts.add(modelCard);
                                    }

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            vkQuery(crimePosts);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void vkQuery(List<ModelCard> crimePosts) {
        for (int i = 0; i < crimePosts.size(); i++) {
            mainFragment.addCrime(crimePosts.get(i), true);
        }
    }


    private void setUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
            setSupportActionBar(toolbar);
            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            //Кнопка для открытия NavigationView
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                    R.string.view_navigation_open, R.string.view_navigation_close);
            drawerLayout.addDrawerListener(toggle);
            toggle.syncState();
            NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    drawerLayout.closeDrawers();
                    searchByHashtag(item.getItemId());
                    return true;
                }
            });
        }


        searchView = (SearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mainFragment.findCrimes(newText);
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
        TabAdapter tabAdapter = new TabAdapter(fragmentManager, 1);

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

        mainFragment = (MainFragment) tabAdapter.getItem(TabAdapter.BEAUTY_AND_HEALTH_FRAGMENT_POSITION);
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

    public void searchByHashtag(int res) {
        if (res == R.id.menu_navigation_item_beauty) {
            searchView.setQuery(hashTagBeauty, false);
        } else if (res == R.id.menu_navigation_item_buy){
            searchView.setQuery(hashTagBuy, false);
        } else if (res == R.id.menu_navigation_item_fun){
            searchView.setQuery(hashTagFun, false);
        } else if (res == R.id.menu_navigation_item_pub){
            searchView.setQuery(hashTagPub, false);
        } else if (res == R.id.menu_navigation_item_transport){
            searchView.setQuery(hashTagTransport, false);
        } else if (res == R.id.menu_navigation_item_deceivers) {
            searchView.setQuery(hashTagDeceivers, false);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_logout) {

            VKSdk.logout();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void uploadProgressNotification() {
        nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(android.R.drawable.stat_sys_upload)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setWhen(System.currentTimeMillis())
                .setTicker(getResources().getString(R.string.notification_ticker))
                .setContentTitle(getResources().getString(R.string.notification_title))
                .setContentText(getResources().getString(R.string.notification_text))
                .setProgress(100, 0, true)
                .setAutoCancel(true);

        Notification notification = builder.getNotification();
        notification.flags = notification.flags | Notification.FLAG_ONGOING_EVENT;
        nm.notify(Utils.UPLOAD_NOTIFICATION_ID, notification);

    }

    @Override
    public void onCrimeAdded(String picturePath, String fullPost) {

        vkUpload = new VKUpload(this.getApplicationContext(), this);

        if (picturePath == null) {
            vkUpload.makePost(null, fullPost);
        } else {
            uploadOnePhoto = new VKUpload(this.getApplicationContext(), this)
                    .new UploadOnePhoto(picturePath, fullPost);
            uploadOnePhoto.execute();
        } /*else if (picturePath.size() == 2) {
            uploadTwoPhotos = new VKUpload(this.getApplicationContext())
                    .new UploadTwoPhotos(picturePath, fullPost);
            uploadTwoPhotos.execute();
        } else if (picturePath.size() == 3) {
            uploadThreePhotos = new VKUpload(this.getApplicationContext())
                    .new UploadThreePhotos(picturePath, fullPost);
            uploadThreePhotos.execute();
        }*/
    }


    @Override
    public void onCrimeAddingCanceled() {
        Toast.makeText(this, "Crime Adding Canceled", Toast.LENGTH_LONG).show();
    }

}
