package com.example.tickettrader;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import static com.example.tickettrader.NotifChannel.CHANNEL_ID;

public class NotificationService extends Service {
    private String url;
    private NotificationManagerCompat notificationManager;
    private WebSocketClient cc;
    private DatabaseHelper dbHelper;
    private String user;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        this.dbHelper = new DatabaseHelper(this);
        Cursor data = dbHelper.getData();
        data.moveToNext();
        data.moveToNext();
        data.moveToNext();
        this.user = data.getString(1);
        this.url = "ws://cs309-pp-1.misc.iastate.edu:8080/webNote/" + this.user;
        this.connectToServer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cc.close();
    }

    public void sendOnChannel() {
        this.notificationManager = NotificationManagerCompat.from(this);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_announcement_black_24dp)
                .setContentTitle("")
                .setContentText("You sold a ticket")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        this.notificationManager.notify(1, notification);
    }

    public void connectToServer() {
        Draft[] drafts = {new Draft_6455()};
        try {
            cc = new WebSocketClient(new URI(url),(Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    sendOnChannel();
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    System.out.println(reason);
                }

                @Override
                public void onError(Exception e) {
                    System.out.println("error");
                    e.printStackTrace();
                }
            };
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }
        cc.connect();
    }
}
