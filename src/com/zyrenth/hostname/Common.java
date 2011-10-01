package com.zyrenth.hostname;

import java.io.DataOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.widget.Toast;

public class Common {

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
					// TODO Code to run on success 
					toastMessage(context, "Hostname set to " + hostname);
					WifiManager wifi = (WifiManager) context.getSystemService(Activity.WIFI_SERVICE);
					if(wifi.isWifiEnabled())
						wifi.reassociate();
				}    
				else {    
					// TODO Code to run on unsuccessful   
					//toastMessage(context, "Could not set hostname");       
				}    
			} catch (InterruptedException e) {    
				// TODO Code to run in interrupted exception   
				//toastMessage(context, "Could not set hostname");    
			}    
		} catch (IOException e) {    
			// TODO Code to run in input/output exception   
			//toastMessage(context, "Could not set hostname");    
		}
		
	}
	
	private static void toastMessage(Context context, String text) {
		// TODO Auto-generated method stub
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}
	
}
