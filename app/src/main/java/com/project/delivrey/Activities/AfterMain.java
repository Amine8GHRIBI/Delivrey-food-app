package com.project.delivrey.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.view.View;

import androidx.core.view.MenuItemCompat;
import androidx.viewpager.widget.ViewPager;
import 	androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.firebase.auth.FirebaseAuth;
import com.project.delivrey.MenuActivity.EightFragment;
import com.project.delivrey.MenuActivity.FiveFragment;
import com.project.delivrey.MenuActivity.FourFragment;
import com.project.delivrey.MenuActivity.NineFragment;
import com.project.delivrey.MenuActivity.OneFragment;
import com.project.delivrey.MenuActivity.SevenFragment;
import com.project.delivrey.MenuActivity.SixFragment;
import com.project.delivrey.MenuActivity.Threefragment;
import com.project.delivrey.MenuActivity.TwoFragment;
import com.project.delivrey.R;
import com.project.delivrey.Unnecessary.DatabaseEntry;

import java.util.ArrayList;
import java.util.List;


public class AfterMain extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    FirebaseAuth auth;
    public static TextView tv;
    private ViewPager mViewPager;
    private DatabaseEntry databaseEntry;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        Intent intent = getIntent();

        int position = intent.getIntExtra("viewPosition" ,0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setCurrentItem(position);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(Color.parseColor("#FFFFFF"), Color.parseColor("#5CA67C"));
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_after_main, menu);
        databaseEntry = new DatabaseEntry(this);
        MenuItem item = menu.findItem(R.id.action_cart);
        MenuItemCompat.setActionView(item, R.layout.badge_layout);
        RelativeLayout notifCount = (RelativeLayout) MenuItemCompat.getActionView(item);
        tv = (TextView) notifCount.findViewById(R.id.actionbar_notifcation_textview);
        imageView = (ImageView)notifCount.findViewById(R.id.imagenotif);
        tv.setText(String.valueOf(databaseEntry.totalQty()));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfterMain.this, Cart_Favorite.class);
                intent.putExtra("ViewPager", 0);
                startActivity(intent);
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            finish();
        }
        if(id == R.id.action_cart){
            Intent intent = new Intent(AfterMain.this, Cart_Favorite.class);
            intent.putExtra("ViewPager", 0);
            startActivity(intent);
        }
        if(id == R.id.favorite){
            Intent intent = new Intent(AfterMain.this, Cart_Favorite.class);
            intent.putExtra("ViewPager", 1);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "Veg Starters");
        adapter.addFrag(new TwoFragment(), "Non-Veg Starters");
        adapter.addFrag(new Threefragment(), "Veg Main Course");
        adapter.addFrag(new FourFragment(), "Non-Veg Main Course");
        adapter.addFrag(new FiveFragment(), "Platters");
        adapter.addFrag(new SixFragment(), "Rolls");
        adapter.addFrag(new SevenFragment(), "Rice");
        adapter.addFrag(new EightFragment(), "Breads");
        adapter.addFrag(new NineFragment(), "Beverages");
        viewPager.setAdapter(adapter);
    }

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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
