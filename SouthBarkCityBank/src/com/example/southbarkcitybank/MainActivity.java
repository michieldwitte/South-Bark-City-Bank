package com.example.southbarkcitybank;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

	public String ssecret = "";
	public String message = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
		
		Bundle extras = getIntent().getExtras(); 
		if(extras !=null) {
		    ssecret = extras.getString("SSecret");
		} else {
			((TextView) findViewById(R.id.textView2)).setText("Error: Shared Secret is niet ingeladen");
			((TextView) findViewById(R.id.textView2)).setVisibility(TextView.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
	}
	
	public String generateTOTPCode(String message){        
        long unixTime = System.currentTimeMillis() / 1000L;
        long time = unixTime - (unixTime%150); //tijdspanne inlassen
    	Long toEncrypt = null;
    	
    	try{
    		toEncrypt = Long.parseLong(message)^time; // de message is een long die we xorren met de tijd
    	} catch (Exception e){
    		return "Error: Invalid QR-Code";
    	}
        return TOTP.generateTOTP(ssecret, toEncrypt.toString(), "8", "HmacSHA512");
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				message = contents;
				String TOTPCode = generateTOTPCode(message);
				
				((TextView) findViewById(R.id.textView2)).setText(TOTPCode);
				((TextView) findViewById(R.id.textView2)).setVisibility(TextView.VISIBLE);

			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				((TextView) findViewById(R.id.textView2)).setText("Failed to read QR-Code");
				((TextView) findViewById(R.id.textView2)).setVisibility(TextView.VISIBLE);
				
			}
		}
	}
}
