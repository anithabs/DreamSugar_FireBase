package com.project.uwm.mydiabitiestracker.Alarm;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

public abstract class WakeReminderIntentService extends IntentService {
abstract void doReminderWork(Intent intent);

	private static PowerManager.WakeLock lockStatic = null;
	private static String LOCK_NAME_STATIC = "MyWakeLock";

	public static void acquireStaticLock(Context context) {
		getLock(context).acquire();
	}

	synchronized private static PowerManager.WakeLock getLock(Context context) {
		if (lockStatic == null) {
			PowerManager mgr = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			lockStatic = mgr.newWakeLock(PowerManager.FULL_WAKE_LOCK,
					LOCK_NAME_STATIC);
			lockStatic.setReferenceCounted(true);
		}
		return (lockStatic);
	}

	public WakeReminderIntentService(String name) {
		super(name);
	}
	
	@Override
	final protected void onHandleIntent(Intent intent) {
		try {
			doReminderWork(intent);
		}
		finally {
			getLock(this).release();
		}
	}
}
