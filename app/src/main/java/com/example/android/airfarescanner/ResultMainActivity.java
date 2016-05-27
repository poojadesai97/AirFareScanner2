package com.example.android.airfarescanner;

<<<<<<< HEAD
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
=======
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
>>>>>>> master
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


import java.util.ArrayList;
<<<<<<< HEAD
=======
import java.util.Collections;
>>>>>>> master
import java.util.List;


public class ResultMainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
<<<<<<< HEAD
        tabLayout.setupWithViewPager(viewPager);
=======


        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_cheapest);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_quickest);

>>>>>>> master

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
<<<<<<< HEAD
        adapter.addFragment(new OneFragment(), "Cheapest");
        adapter.addFragment(new TwoFragment(), "Quickest");
        viewPager.setAdapter(adapter);
    }

=======
        Bundle extras = getIntent().getExtras();
        ArrayList<tripPojo> resultList = null;
        if (extras != null) {
            resultList = (ArrayList<tripPojo>) extras.getSerializable("List");
        }
        Log.e("resultactivity", "List size : " + resultList.size());

        Bundle bundle = new Bundle();
        bundle.putSerializable("cheapest", resultList);
        CheapestFragment cheapestfragment = new CheapestFragment();
        cheapestfragment.setArguments(bundle);

        adapter.addFragment(cheapestfragment, "Cheapest");

        ArrayList<tripPojo> quickestList = new ArrayList<tripPojo>(resultList);
        //Collections.copy(quickestList, resultList);
        bundle = new Bundle();
        bundle.putSerializable("quickest", quickestList);
        QuickestFragment quickestFragment = new QuickestFragment();
        quickestFragment.setArguments(bundle);
        adapter.addFragment(quickestFragment, "Quickest");

        viewPager.setAdapter(adapter);
    }



>>>>>>> master
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        if (id == R.id.action_settings) {
            return true;
        }
<<<<<<< HEAD

        return super.onOptionsItemSelected(item);
    }
}
=======
        if (id == R.id.action_modify) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
>>>>>>> master
