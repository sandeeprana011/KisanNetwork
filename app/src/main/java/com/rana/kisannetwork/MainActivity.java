package com.rana.kisannetwork;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.rana.kisannetwork.adapters.HomePagerAdapter;

public class MainActivity extends AppCompatActivity {


    ViewPager homeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        homeViewPager = (ViewPager) findViewById(R.id.pager_home);
        HomePagerAdapter homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        homeViewPager.setAdapter(homePagerAdapter);
    }


}
