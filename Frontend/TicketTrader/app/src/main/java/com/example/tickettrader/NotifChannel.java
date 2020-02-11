package com.example.tickettrader;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotifChannel extends Application {
    public static final String CHANNEL_ID = "channel";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannels();
    }

    public void createNotificationChannels() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("This is a channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
