package crypto;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;


public class PBKDF2 {

	public static byte[] deriveKey( byte[] password, byte[] salt, int iterationCount) throws NoSuchAlgorithmException, InvalidKeyException
	{

		MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
		SecretKeySpec secretKeySpec = new SecretKeySpec( sha256.digest(password), "HmacSHA256" );
		Mac prf = Mac.getInstance("HmacSHA256"); //output len is 256bit -> 32byte.
		prf.init(secretKeySpec);

		// output length of PRF.
		// Ti is 256 bit.
		int hahLen = prf.getMacLength();  
		byte TempDK[] = new byte[hahLen];
		F(TempDK,prf, salt, iterationCount);
		return TempDK;
	} 


	private static void F(byte[] destinationArray, Mac prf, byte[] S, int c) {
		int hashLen = prf.getMacLength();
		byte Ur[] = new byte[ hashLen ];
		byte Ui[] = new byte[S.length + 4];

		System.arraycopy(S, 0, Ui, 0, S.length);

		INT_msb(Ui, S.length);
		for(int i = 0; i < c; i++){
			Ui = prf.doFinal(Ui);
			xor(Ur, Ui);
		}
		System.arraycopy(Ur, 0, destinationArray, 0, hashLen);
	}

	private static void xor(byte[] destinationArray, byte[] sourceArray) {
		for( int i = 0; i < destinationArray.length; i++ ) {
			destinationArray[i] ^= sourceArray[i];
		}
	}

	private static void INT_msb( byte[] destinationArray, int offset) {
		destinationArray[offset + 0] = (byte) (1 / (256 * 256 * 256));
		destinationArray[offset + 1] = (byte) (1 / (256 * 256));
		destinationArray[offset + 2] = (byte) (1 / (256));
		destinationArray[offset + 3] = (byte) (1);
	} 
}
