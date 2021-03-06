package com.example.submission1moviecatalogue;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter movieAdapter;
    private ViewPager viewPager;
    private AlarmReceiver alarmReceiver;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), this);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabLayout);
        tabs.setupWithViewPager(viewPager);

        getSupportActionBar().setElevation(0);

        movieAdapter = new MovieAdapter(this);

//        alarmReceiver = new AlarmReceiver();

//        session = new Session(this);
//
//        if(session.getDailyReminderSetting() == true){
//            setDailyReminder();
//        }
//        else if(session.getDailyReminderSetting() == false){
//            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_REPEATING);
//        }
//
//        Log.d("dailyReminder", String.valueOf(session.getDailyReminderSetting()));
//
//        if(session.getReleaseTodaySetting() == true){
//            setReleaseToday();
//        }
//        else if(session.getReleaseTodaySetting() == false){
//            alarmReceiver.cancelAlarm(this, AlarmReceiver.TYPE_RELEASE_TODAY);
//        }
//
//        Log.d("releaseToday", String.valueOf(session.getReleaseTodaySetting()));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.language_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {

                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("currentTab", viewPager.getCurrentItem());
                    intent.putExtra("hint", query);
                    startActivity(intent);

                    return true;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        else if(item.getItemId() == R.id.to_favorite_activity){
            Intent mIntent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(mIntent);
        }
        else if(item.getItemId() == R.id.set_reminder){
            Intent mIntent = new Intent(MainActivity.this, SetReminderActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDailyReminder(){

        String repeatTime = "07:00";
        String repeatMessage = getString(R.string.daily_reminder);
        alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING,
                repeatTime, repeatMessage);

    }

    private void setReleaseToday(){

        String repeatTime = "08:00";
        String repeatMessage = getString(R.string.release_today);
        alarmReceiver.setReleaseToday(this, AlarmReceiver.TYPE_REPEATING,
                repeatTime, repeatMessage);
    }
}