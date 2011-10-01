package com.zyrenth.hostname;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SetMyHostnameActivity extends Activity {
    private TextView txtHostname;
	private CheckBox chkAtBoot;
	private TextView lblHostname;
	protected WifiManager wifi;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

		Button loginButton = (Button) findViewById(R.id.btnApply);
		txtHostname = (TextView)findViewById(R.id.txtHostname); 
		chkAtBoot = (CheckBox)findViewById(R.id.chkAtBoot);
		lblHostname = (TextView)findViewById(R.id.lblHostname);

		SharedPreferences settings = getSharedPreferences("HostPrefs", MODE_PRIVATE);
		txtHostname.setText(settings.getString("hostname", ""));
		chkAtBoot.setChecked(settings.getBoolean("startAtBoot", false));
		
		// Setup WiFi
		
		loginButton.setOnClickListener(mSetHostname);
		
		getCurrentHostname();
		
    }
    
    public Button.OnClickListener mSetHostname = new Button.OnClickListener() {
		public void onClick(View v) {

			txtHostname = (TextView)findViewById(R.id.txtHostname); 
			chkAtBoot = (CheckBox)findViewById(R.id.chkAtBoot);
			String hostname = txtHostname.getText().toString();
			hostname = hostname.replaceAll("[^A-Za-z0-9\\-]+", "").trim();
			if(hostname.equals(""))
				return;
			SharedPreferences settings = getSharedPreferences("HostPrefs", MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("hostname", hostname);
			editor.putBoolean("startAtBoot", chkAtBoot.isChecked());
		    editor.commit();
			
		    Common.SetHostname(SetMyHostnameActivity.this, hostname);
			getCurrentHostname();
			
		}
	};

	private void getCurrentHostname() {
		try {
			// Executes the command.
			Process process = Runtime.getRuntime().exec("getprop net.hostname");

			BufferedReader reader = new BufferedReader(
			        new InputStreamReader(process.getInputStream()));
			int read;
			char[] buffer = new char[4096];
			StringBuffer output = new StringBuffer();
			while ((read = reader.read(buffer)) > 0) {
			    output.append(buffer, 0, read);
			}
			reader.close();

			// Waits for the command to finish.
			process.waitFor();

			String currHost =  output.toString();
			lblHostname = (TextView)findViewById(R.id.lblHostname);
			lblHostname.setText(currHost);
		} catch (IOException e) {
			// do nothing
		} catch (InterruptedException e) {
			// do nothing
		}	
	
	}
    
}