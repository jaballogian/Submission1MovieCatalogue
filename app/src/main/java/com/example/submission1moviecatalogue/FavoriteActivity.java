package com.example.submission1moviecatalogue;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class FavoriteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        FavoriteSectionsPagerAdapter favoriteSectionsPagerAdapter = new FavoriteSectionsPagerAdapter(getSupportFragmentManager(), this);
        ViewPager viewPager = findViewById(R.id.viewPagerActivityFavorite);
        viewPager.setAdapter(favoriteSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayoutActivityFavorite);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
