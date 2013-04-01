package com.example.southbarkcitybank;

import java.util.Scanner;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
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
	}
	
	public String generateTOTPCode(String message){
        String seed = "3132333435363738393031323334353637383930"; //de shared secret
        
        long unixTime = System.currentTimeMillis() / 1000L;
        long time = unixTime - (unixTime%150); //tijdspanne inlassen
    	time = time & Long.parseLong(message);
        
        String steps = "0";
        steps = Long.toHexString(time).toUpperCase();
        while(steps.length() < 16) steps = "0" + steps;
        return TOTP.generateTOTP(seed, steps, "8", "HmacSHA512");
	}

}
