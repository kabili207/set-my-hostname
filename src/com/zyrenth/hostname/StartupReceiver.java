package com.zyrenth.hostname;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences settings =
			PreferenceManager.getDefaultSharedPreferences(context);
		String hostname = settings.getString("hostname", "");
		Boolean atBoot = settings.getBoolean("startAtBoot", false);

		hostname = hostname.replaceAll("[^A-Za-z0-9\\-]+", "").trim();
		if (atBoot && !hostname.equals("")) {
		    Shared.SetHostname(context, hostname);
		}
	}
}