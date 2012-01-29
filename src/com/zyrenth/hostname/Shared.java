package com.zyrenth.hostname;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class Shared {

	public static void SetHostname(Context context, String hostname) {
		
		Process p;    
		try {    
			// Perform su to get root privileges   
			p = Runtime.getRuntime().exec("su");    

			// Attempt to write a file to a root-only    
			DataOutputStream os = new DataOutputStream(p.getOutputStream());    
			os.writeBytes("setprop net.hostname " + hostname + "\n");   

			// Close the terminal   
			os.writeBytes("exit\n");    
			os.flush();
			try {    
				p.waitFor();    
				if (p.exitValue() != 255) {    
					toastMessage(context, "Hostname set to " + hostname);
					WifiManager wifi = (WifiManager) context.getSystemService(Activity.WIFI_SERVICE);
					if(wifi.isWifiEnabled())
						wifi.reassociate();
				}    
				else {    
					// TODO Code to run on unsuccessful   
					notifyNeedRoot(context);       
				}    
			} catch (InterruptedException e) {    
				// TODO Code to run in interrupted exception   
				notifyNeedRoot(context);     
			}    
		} catch (IOException e) {    
			// TODO Code to run in input/output exception   
			notifyNeedRoot(context);  
		}
		
	}
	
	private static void notifyNeedRoot(Context context) {
		if(context instanceof Activity) {
			String message = "This application requires root!";
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message)
			       .setCancelable(false)
			       .setNeutralButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			AlertDialog alert = builder.create();
			alert.show();
		}
	}
	
	private static void toastMessage(Context context, String text) {
		// TODO Auto-generated method stub
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
}
