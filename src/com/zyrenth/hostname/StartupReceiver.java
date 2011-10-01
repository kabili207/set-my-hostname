package com.zyrenth.hostname;
import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class StartupReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		SharedPreferences settings = context.getSharedPreferences("HostPrefs", 0);
		String hostname = settings.getString("hostname", "");
		Boolean atBoot = settings.getBoolean("startAtBoot", false);

		hostname = hostname.replaceAll("[^A-Za-z0-9\\-]+", "").trim();
		if (atBoot && !hostname.equals("")) {
		    Common.SetHostname(context, hostname);
		}
	}
}