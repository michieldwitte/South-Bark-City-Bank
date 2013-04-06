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

	public String seed = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
		Button btn2 = (Button) findViewById(R.id.button2);
		btn2.setOnClickListener(this);
		//import seed ---------------------- nog te doen -----------------
		if(seed.equals("")){
			btn2.setVisibility(Button.VISIBLE);
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
		if (((Button) arg0).getText().toString().equals("Generate")){
			TextView tv2 = (TextView) findViewById(R.id.textView2);
			TextView tv3 = (TextView) findViewById(R.id.textView3);
			EditText et = (EditText) findViewById(R.id.editText1);
			
			String message = et.getText().toString();
			tv2.setVisibility(TextView.VISIBLE);
			if(!message.equals("")){
				tv3.setText(generateTOTPCode(message));
			} else {
				tv3.setText("Invalid input");
			}
			tv3.setVisibility(TextView.VISIBLE);
		} else {
			Intent intent = new Intent("com.google.zxing.client.android.SCAN");
			intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			startActivityForResult(intent, 0);
		}
	}
	
	public String generateTOTPCode(String message){
        seed = "3132333435363738393031323334353637383930"; //de shared secret
        
        long unixTime = System.currentTimeMillis() / 1000L;
        long time = unixTime - (unixTime%150); //tijdspanne inlassen
    	time = time & Long.parseLong(message);
        
        String steps = "0";
        steps = Long.toHexString(time).toUpperCase();
        while(steps.length() < 16) steps = "0" + steps;
        return TOTP.generateTOTP(seed, steps, "8", "HmacSHA512");
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
				seed = contents;
				((TextView) findViewById(R.id.textView3)).setText("Successfully read shared secret");
				((TextView) findViewById(R.id.textView3)).setVisibility(TextView.VISIBLE);
				((TextView) findViewById(R.id.textView2)).setVisibility(TextView.INVISIBLE);
				((Button) findViewById(R.id.button2)).setVisibility(Button.INVISIBLE);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				((TextView) findViewById(R.id.textView3)).setText("Failed to read shared secret");
			}
		}
	}
}
