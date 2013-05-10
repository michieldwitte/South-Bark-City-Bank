/**
 * 
 */
package crypto;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author sander
 *
 */
public class AES {

	private static AES instance = null;
	private Cipher cipher;

	private AES(){
		try {
			cipher = Cipher.getInstance("AES");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		}
	}

	public static AES getInstance(){
		if(instance == null)
			instance = new AES();
		return instance;
	}

	public String encryptMessage(byte[] password, byte[] message){
		Key key = new SecretKeySpec(password, "AES");
		byte[] encryptedValue = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE,key);
			encryptedValue = cipher.doFinal(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(encryptedValue);
	}

	public String decryptMessage(byte[] password, byte[] message){
		Key key = new SecretKeySpec(password,"AES");
		byte[] decryptedValue = null;
		try{
			cipher.init(Cipher.DECRYPT_MODE, key);
			cipher.doFinal(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decryptedValue);
	}

}
