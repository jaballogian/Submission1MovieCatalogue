package com.example.submission1moviecatalogue;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "RepeatingAlarm";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    // Siapkan 2 id untuk 2 macam alarm, onetime dna repeating
    private final int ID_ONETIME = 100;
    private final int ID_REPEATING = 101;
    private String DATE_FORMAT = "yyyy-MM-dd";
    private String TIME_FORMAT = "HH:mm";
    final ArrayList<Movie> films = new ArrayList<>();
    private GetReleaseTodayData getReleaseTodayData;
    private int idNotification = 0;
    private static final CharSequence CHANNEL_NAME = "dicoding channel";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    private final static int NOTIFICATION_REQUEST_CODE = 200;
    private static final int MAX_NOTIFICATION = 10000;
    private Context context;


    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_REPEATING;
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        showToast(context, title, message);
        showAlarmNotification(context, title, message, notifId, "Channel_1", "AlarmManager channel");
    }

    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

    private void showAlarmNotification(Context context, String title, String message, int notifId, String CHANNEL_ID, String CHANNEL_NAME) {

        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.submission1moviecatalogue", "com.example.submission1moviecatalogue.MainActivity"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_access_time_white_24dp)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setAutoCancel(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});
            builder.setChannelId(CHANNEL_ID);
            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }
    }

    public void setRepeatingAlarm(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_TYPE, type);
        String[] timeArray = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
//        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();
    }

    public void setReleaseToday(Context context, String type, String time, String message) {
        if (isDateInvalid(time, TIME_FORMAT)) return;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        String[] timeArray = time.split(":");
        Calendar releaseToday = Calendar.getInstance();
        releaseToday.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        releaseToday.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        releaseToday.set(Calendar.SECOND, 0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, releaseToday.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
//        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show();

        this.context = context;
        getReleaseTodayData = new GetReleaseTodayData();
        getReleaseTodayData.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

    }

    private class GetReleaseTodayData extends AsyncTask<Void,Void,Void> {

        String result ="";
        int totalFilms;

        @Override
        protected Void doInBackground(Void... voids) {

            Calendar today = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String todayDate = simpleDateFormat.format(today.getTime());
            Log.d("todayDate", todayDate);
            String link =  "https://api.themoviedb.org/3/discover/movie?api_key=" + "58c087a2b6445fa861b67095aafefe77" + "&primary_release_date.gte=" + todayDate +
                    "&primary_release_date.lte=" + todayDate;
            try {
                URL url = new URL(link);
                Log.d("url", url.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line = "";
                while(line != null){
                    line = bufferedReader.readLine();
                    result = result + line;
                }
                Log.d("result", result);

                totalFilms = 0;

                JSONObject responseObject = new JSONObject(result);
                JSONArray resultsArray = responseObject.getJSONArray("results");

                for(int i = 0; i < resultsArray.length(); i++){

                    JSONObject JO = (JSONObject) resultsArray.get(i);

                    Movie film = new Movie();

                    film.setTitle(JO.get("title").toString());
                    film.setDescription(JO.get("overview").toString());

                    films.add(film);
                    totalFilms++;
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.d("U MalformedURLException", "U MalformedURLException ");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("U IOException", "U IOException");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("U JSONException", "U JSONException ");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            for(int i = 0; i < films.size(); i++){

                sendNotif();
                idNotification++;
            }
        }

    }

    private void sendNotif() {

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder;
        //Melakukan pengecekan jika idNotification lebih kecil dari Max Notif
        String CHANNEL_ID = "channel_01";
        if (idNotification < MAX_NOTIFICATION) {
            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(films.get(idNotification).getTitle())
                    .setContentText(films.get(idNotification).getDescription())
                    .setSmallIcon(R.drawable.ic_live_tv_white_24dp)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        } else {

            mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(films.get(idNotification).getTitle())
                    .setContentText(films.get(idNotification).getDescription())
                    .setSmallIcon(R.drawable.ic_live_tv_white_24dp)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
        }
     /*
    Untuk android Oreo ke atas perlu menambahkan notification channel
    Materi ini akan dibahas lebih lanjut di modul extended
     */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);
            mBuilder.setChannelId(CHANNEL_ID);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = mBuilder.build();
        if (mNotificationManager != null) {
            mNotificationManager.notify(idNotification, notification);
        }
    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        films.clear();
//        idNotification= 0;
//    }
}
