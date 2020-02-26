package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SetReminderActivity extends AppCompatActivity {

    private Switch setDailyReminderSwitch, setReleaseTodayReminderSwitch;
    private Session session;
    private boolean dailyReminder, releaseToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        setDailyReminderSwitch = (Switch) findViewById(R.id.setDailyReminderSwitch);
        setReleaseTodayReminderSwitch = (Switch) findViewById(R.id.setReleaseTodayReminderSwitch);

        session = new Session(this);

        dailyReminder = session.getDailyReminderSetting();
        releaseToday = session.getReleaseTodaySetting();

        setDailyReminderSwitch.setChecked(dailyReminder);
        setReleaseTodayReminderSwitch.setChecked(releaseToday);

        setDailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               session.setDailyReminderSetting(isChecked);
               Log.d("setDailyReminder", String.valueOf(isChecked));
           }
        });

        setReleaseTodayReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                session.setReleaseTodaySetting(isChecked);
                Log.d("setReleaseToday", String.valueOf(isChecked));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SetReminderActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
