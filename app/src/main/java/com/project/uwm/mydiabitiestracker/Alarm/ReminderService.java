package com.project.uwm.mydiabitiestracker.Alarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.project.uwm.mydiabitiestracker.R;

public class ReminderService extends WakeReminderIntentService {

	public ReminderService() {
		super("ReminderService");
			}

	@Override
	void doReminderWork(Intent intent) {
		Log.d("ReminderService", "Doing work.");
		Long rowId = intent.getExtras().getLong(RemindersDbAdapter.KEY_ROWID);
		String title = intent.getExtras().getString(RemindersDbAdapter.KEY_TITLE);
		String message = intent.getExtras().getString(RemindersDbAdapter.KEY_BODY);

		NotificationManager mgr = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Intent notificationIntent = new Intent(this, ReminderEditActivity.class); 
		notificationIntent.putExtra(RemindersDbAdapter.KEY_ROWID, rowId);
		PendingIntent pi = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setAutoCancel(false);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_menu_gallery);
        builder.setContentIntent(pi);
        builder.setOngoing(true);
        builder.setNumber(100);
        builder.build();
		int id = (int)((long)rowId);
		mgr.notify(id, builder.getNotification());
	}
}
