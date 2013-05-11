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

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

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
	
	/**
	 * 
	 * @param password in byte formaat
	 * @param message in String formaat
	 * @return hex encoded message
	 */
	public String encryptMessage(byte[] password, String message){
		Key key = new SecretKeySpec(password, "AES");
		byte[] byteMessage = message.getBytes();
		byte[] encryptedValue = null;
		try {
			cipher.init(Cipher.ENCRYPT_MODE,key);
			encryptedValue = cipher.doFinal(byteMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(Hex.encodeHex(encryptedValue));
	}

	/**
	 * 
	 * @param password in byte formaat
	 * @param message in String formaat
	 * @return original message
	 * @throws DecoderException 
	 */
	public String decryptMessage(byte[] password, String message) throws DecoderException{
		Key key = new SecretKeySpec(password,"AES");
		
		byte[] encryptedValueFromHex = Hex.decodeHex(message.toCharArray());
		byte[] decryptedValue = null;
		try{
			cipher.init(Cipher.DECRYPT_MODE, key);
			decryptedValue = cipher.doFinal(encryptedValueFromHex);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new String(decryptedValue);
	}

}
