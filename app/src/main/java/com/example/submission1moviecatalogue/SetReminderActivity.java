package com.example.submission1moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;

public class SetReminderActivity extends AppCompatActivity {

    private Switch setDailyReminderSwitch, setReleaseTodayReminderSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        setDailyReminderSwitch = (Switch) findViewById(R.id.setDailyReminderSwitch);
        setReleaseTodayReminderSwitch = (Switch) findViewById(R.id.setReleaseTodayReminderSwitch);
    }
}
