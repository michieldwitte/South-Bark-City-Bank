package com.example.southbarkcitybank;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyCodeActivity extends Activity implements OnClickListener{
	
	public String pin;
	public Boolean ssecretPresent;
	public String ssecret;
	public int triesleft;
	public byte[] iv;
	private final String fileName="TotalyNotTheSharedSecret";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify);
		((Button) findViewById(R.id.button1)).setOnClickListener(this);
		((Button) findViewById(R.id.button2)).setOnClickListener(this);
		((Button) findViewById(R.id.button3)).setOnClickListener(this);
		((Button) findViewById(R.id.button4)).setOnClickListener(this);
		((Button) findViewById(R.id.button5)).setOnClickListener(this);
		((Button) findViewById(R.id.button6)).setOnClickListener(this);
		((Button) findViewById(R.id.button7)).setOnClickListener(this);
		((Button) findViewById(R.id.button8)).setOnClickListener(this);
		((Button) findViewById(R.id.button9)).setOnClickListener(this);
		((Button) findViewById(R.id.button10)).setOnClickListener(this);
		((Button) findViewById(R.id.button11)).setOnClickListener(this);
		((Button) findViewById(R.id.button12)).setOnClickListener(this);
		((EditText) findViewById(R.id.editText1)).setCursorVisible(false);
		
		pin = "";
		triesleft = 3;
		
		File fl = new File(getFilesDir() +"/" + fileName);
		if(fl.exists()){
			ssecretPresent=true;
		} else {
			ssecretPresent=false;
		}
	}

	@Override
	public void onClick(View arg0){
		Button btn = (Button) arg0;
		String txt = btn.getText().toString();
		EditText pinfield = (EditText) findViewById(R.id.editText1);
		if(txt.equals("OK")){
			onOKClick(arg0);
		} else if(txt.equals("Clear")){
			pinfield.setText("");
		} else {//een getal
			pinfield.setText(pinfield.getText().toString() + txt);
		}
	}
	
	public void onOKClick(View arg0) {
		if (!ssecretPresent){
			if (pin.equals("")){
				pin = ((EditText) findViewById(R.id.editText1)).getText().toString();
				((TextView) findViewById(R.id.textView1)).setText("Enter pin again to verify");
				((EditText) findViewById(R.id.editText1)).setText("");
			} else if(pin.equals(((EditText) findViewById(R.id.editText1)).getText().toString())){
				Intent intent = new Intent("com.google.zxing.client.android.SCAN");
				intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
				startActivityForResult(intent, 0);

			} else { // pin was niet 2x dezelfde
				pin = "";
				((TextView) findViewById(R.id.textView1)).setText("The pin was not verified, try the procedure again");
				((EditText) findViewById(R.id.editText1)).setText("");
			}
		} else { //ssecret decoden met pin en overgaan naar main-state
			if (pin.equals("")){
				pin = ((EditText) findViewById(R.id.editText1)).getText().toString();
				((TextView) findViewById(R.id.textView1)).setText("Enter pin again to verify");
				((EditText) findViewById(R.id.editText1)).setText("");
			} else if(pin.equals(((EditText) findViewById(R.id.editText1)).getText().toString())){
				try{
					ssecret = ReadEncryptedString();
				} catch (Exception ex){
					triesleft--;
					pin="";
					
					if (triesleft == 0){
						deleteFile(getFilesDir() +"/" + fileName);
						((TextView) findViewById(R.id.textView1)).setText("Exceeded the number of tries: the shared secret has been removed");
						((EditText) findViewById(R.id.editText1)).setText("");
						return;
					} else {
						((TextView) findViewById(R.id.textView1)).setText("The code was invalid, " + triesleft + " tries remaining");
						((EditText) findViewById(R.id.editText1)).setText("");
						return;
					}
				}
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("SSecret", ssecret);
				startActivity(i);
				finish();
			}
		}
	}
	
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				ssecret = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				
				WriteSeedAndIV();
				
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("SSecret", ssecret);
				startActivity(i);
				finish();
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				((TextView) findViewById(R.id.textView1)).setText("Failed to read shared secret, try again");
			}
		}
	}
	
	
	public String ReadEncryptedString() throws Exception{
		File fl = new File(getFilesDir() +"/" + fileName);
		BufferedReader br = new BufferedReader(new FileReader(fl));
		byte[] input = Base64.decode(br.readLine(), Base64.NO_WRAP);
		iv = Base64.decode(br.readLine(), Base64.NO_WRAP);
		return new String(decrypt(pin.getBytes(), input));
	}
	
	public void WriteSeedAndIV(){
		//seed schrijven
		try {
			File fl = new File(getFilesDir() + "/" + fileName);
			BufferedWriter bw = new BufferedWriter(new FileWriter(fl));
			String encrypted = Base64.encodeToString(encrypt(pin.getBytes(), ssecret.getBytes()), Base64.NO_WRAP);
			String IV = Base64.encodeToString(iv, Base64.NO_WRAP);
			bw.write(encrypted);
			bw.write("\n");
			bw.write(IV);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	private byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        SecretKeySpec skeySpec = new SecretKeySpec(sha256.digest(raw), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        AlgorithmParameters params = cipher.getParameters();
        iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    private byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
    	MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        SecretKeySpec skeySpec = new SecretKeySpec(sha256.digest(raw), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }
}
