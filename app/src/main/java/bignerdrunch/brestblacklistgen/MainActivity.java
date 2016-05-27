package bignerdrunch.brestblacklistgen;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import bignerdrunch.brestblacklistgen.adapter.BeautyAndHealthAdapter;
import bignerdrunch.brestblacklistgen.adapter.CrimeAdapter;
import bignerdrunch.brestblacklistgen.adapter.TabAdapter;
import bignerdrunch.brestblacklistgen.list_fragments.BeautyAndHealthFragment;
import bignerdrunch.brestblacklistgen.list_fragments.BuyFragment;
import bignerdrunch.brestblacklistgen.list_fragments.CrimeFragment;
import bignerdrunch.brestblacklistgen.list_fragments.FunFragment;
import bignerdrunch.brestblacklistgen.list_fragments.PubFragment;
import bignerdrunch.brestblacklistgen.list_fragments.TransportFragment;
import bignerdrunch.brestblacklistgen.model.ModelCard;
import bignerdrunch.brestblacklistgen.new_crime_dialog.AddingCrimeDialogFragment;

public class MainActivity extends AppCompatActivity implements AddingCrimeDialogFragment.AddingCrimeListener {


    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    private FragmentManager fragmentManager;
    private TabAdapter tabAdapter;

    private CrimeFragment crimeFragment;

    private BeautyAndHealthFragment beautyAndHealthFragment;
    private BuyFragment buyFragment;
    private FunFragment funFragment;
    private PubFragment pubFragment;
    private TransportFragment transportFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();

        initNavigationView();
        setUI();
    }

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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
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

        if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_beauty))){
            beautyAndHealthFragment.addCrime(newCrime);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_buy))){
            buyFragment.addCrime(newCrime);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_fun))){
            funFragment.addCrime(newCrime);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_pub))){
            pubFragment.addCrime(newCrime);
        } else if (newCrime.getHashtag().equals(getResources().getString(R.string.hashtag_transport))){
            transportFragment.addCrime(newCrime);
        }

        Toast.makeText(this, "Crime Added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCrimeAddingCanceled() {
        Toast.makeText(this, "Crime Adding Canceled", Toast.LENGTH_LONG).show();
    }
}
