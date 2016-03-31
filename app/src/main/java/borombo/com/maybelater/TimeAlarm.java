package borombo.com.maybelater;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

/**
 * Created by Erwan on 03/05/2015.
 */
public class TimeAlarm extends BroadcastReceiver {

    NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence from = "developpez.et";
        CharSequence message = "message de notif";
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, new Intent(), 0);
        Notification notif = new Notification(R.mipmap.ic_launcher, "message de notif", System.currentTimeMillis());
        notif.setLatestEventInfo(context, from, message, contentIntent);
        nm.notify(1, notif);
        String msg = intent.getStringExtra("MESSAGE");
        //SmsManager.getDefault().sendTextMessage("0698518781",null, String.valueOf(msg),null,null);
    }

}