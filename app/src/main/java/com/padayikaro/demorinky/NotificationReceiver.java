package com.padayikaro.demorinky;

import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by appideas-user4 on 23/4/18.
 */

public class NotificationReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    @Override
    public void onReceive(Context context, Intent intent) {
        //getting the remote input bundle from intent
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);

        //if there is some input
        if (remoteInput != null) {

            //getting the input value
            CharSequence name = remoteInput.getCharSequence(Notification.NOTIFICATION_REPLY);

            //updating the notification with the input value
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Notification.CHANNNEL_ID)
                    .setSmallIcon(android.R.drawable.ic_menu_info_details)
                    .setContentTitle("Hey Thanks, " + name);
            NotificationManager notificationManager = (NotificationManager) context.
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(Notification.NOTIFICATION_ID, mBuilder.build());
        }

        //if help button is clicked
        if (intent.getIntExtra(Notification.KEY_INTENT_HELP, -1) == Notification.REQUEST_CODE_HELP) {
            Toast.makeText(context, "You Clicked Help", Toast.LENGTH_LONG).show();
        }

        //if more button is clicked
        if (intent.getIntExtra(Notification.KEY_INTENT_MORE, -1) == Notification.REQUEST_CODE_MORE) {
            Toast.makeText(context, "You Clicked More", Toast.LENGTH_LONG).show();
        }
    }
}
