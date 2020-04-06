package com.example.submission1moviecatalogue;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SetReminderActivity extends AppCompatActivity {

    private Switch setDailyReminderSwitch, setReleaseTodayReminderSwitch;
    private Session session;
    private boolean dailyReminder, releaseToday;
    private AlarmReceiver alarmReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);

        setDailyReminderSwitch = (Switch) findViewById(R.id.setDailyReminderSwitch);
        setReleaseTodayReminderSwitch = (Switch) findViewById(R.id.setReleaseTodayReminderSwitch);

        alarmReceiver = new AlarmReceiver();

//        session = new Session(this);
//
//        dailyReminder = session.getDailyReminderSetting();
//        releaseToday = session.getReleaseTodaySetting();
//
//        setDailyReminderSwitch.setChecked(dailyReminder);
//        setReleaseTodayReminderSwitch.setChecked(releaseToday);

        setDailyReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

               Log.d("setDailyReminder", String.valueOf(isChecked));

               if(isChecked == true){
                   setDailyReminder();
               }
               else {
                   alarmReceiver.cancelAlarm(SetReminderActivity.this, AlarmReceiver.TYPE_REPEATING);
               }
//               session.setDailyReminderSetting(isChecked);
//               Log.d("setDailyReminder", String.valueOf(isChecked));
           }
        });

        setReleaseTodayReminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Log.d("setReleaseToday", String.valueOf(isChecked));

                if(isChecked == true){
                    setReleaseToday();
                }
                else {
                    alarmReceiver.cancelAlarm(SetReminderActivity.this, AlarmReceiver.TYPE_RELEASE_TODAY);
                }
//                session.setReleaseTodaySetting(isChecked);
//                Log.d("setReleaseToday", String.valueOf(isChecked));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SetReminderActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
