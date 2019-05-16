package com.app.boxee.shopper.services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.app.boxee.shopper.activities.MainActivity;
import com.app.boxee.shopper.application.App;
import com.google.firebase.messaging.RemoteMessage;
import com.app.boxee.shopper.R;

import java.util.Map;

/**
 * Created by Shafaq on 1/11/2018.
 */

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }

//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//        super.onMessageReceived(remoteMessage);
//    }

    public static String getToken(Context context) {
        return context.getSharedPreferences("_", MODE_PRIVATE).getString("fb", "");
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        sendNotification(remoteMessage.getData());
//        Log.e("Notification", "<>");

//        Intent resultIntent = new Intent(this, MainActivity.class);
//        Map<String, String> data = remoteMessage.getData();
//        try {
//
//
//            String actionCode = getString(R.string.actionCode);
//
//            if (data.containsKey(actionCode)) {
//                String code = data.get(actionCode);
//                resultIntent.putExtra(actionCode, ""+ code);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String channelId = getString(R.string.default_notification_channel_id);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        NotificationCompat.Builder mBuilder = new  NotificationCompat.Builder(this,channelId);
//        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
//        mBuilder .setContentTitle(remoteMessage.getNotification().getTitle())
//                .setContentText(remoteMessage.getNotification().getBody())
//                .setAutoCancel(false)
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setContentIntent(resultPendingIntent);
//
//        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
////
////
////        mBuilder.setContentIntent(resultPendingIntent);
////        NotificationManager mNotificationManager =
////                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        mNotificationManager.notify(0, mBuilder.build());
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void sendNotification(Map<String, String> data)
    {
        int color = getResources().getColor(R.color.white);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CN",data.get("cn"));


        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CN")
                .setContentTitle(getString(R.string.app_name))
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(data.get("title")))
                .setContentText(data.get("title"))
                //.setLargeIcon(AppUtils.getImage(remoteMessage.getNotification().getIcon()))
                //.setLargeIcon(((BitmapDrawable) getDrawable(R.drawable.icon_close)).getBitmap())
                .setColor(color)
                .setSound(defaultSoundUri);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("10001", "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert notificationManager != null;
            builder.setChannelId("10001");
            notificationChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        //Log.e("test","notification "+message);
//        if((Boolean)  AppController.getInstance().getPrefManger().getSharedPreferences( "notificationStatus", true))
//        {
            notificationManager.notify(0, builder.build());
//        }
//
//        NotificationModel notificationModel = new NotificationModel();
//        notificationModel.setTitle(data.get("title") == null ? "Muawin" : data.get("title"));
//        notificationModel.setBody(data.get("body") == null ? "":data.get("body"));
//        notificationModel.setOrderId(data.get("order_id") == null ? "0":data.get("order_id"));
//        notificationModel.setIsRead(0);
//        AppController.getInstance().getAppDatabase().notificationsListDao().insertNotification(notificationModel);



    }


}
