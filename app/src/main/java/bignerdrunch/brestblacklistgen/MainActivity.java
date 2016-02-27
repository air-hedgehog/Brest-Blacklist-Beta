package bignerdrunch.brestblacklistgen;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import bignerdrunch.brestblacklistgen.adapter.TabPagerFragmentAdapter;

public class MainActivity extends AppCompatActivity {

    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private FloatingActionButton fab;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        initToolbar();
        intTabs();
        initNavigationView();
        initFab();

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu_main);
    }

    private void intTabs() {
        viewPager = (ViewPager)findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        TabPagerFragmentAdapter fragmentAdapter = new TabPagerFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
    }

    private void initFab() {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

    }
}
