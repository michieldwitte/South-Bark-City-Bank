package SRP;     

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.codec.binary.Hex;

/**
 * Manages a server SRP session
 * 
 * <p>
 * Released into the public domain
 *
 * @author Jordan Zimmerman - jordan@jordanzimmerman.com
 * @see SRPFactory Full Documentation
 * @version 1.4 Make sure safeguards are checked: abort if A == 0 (mod N) or u == 0 - 2/27/07
 * @version 1.3 Updated to use the SRP-6 spec 2/21/07
 * @version 1.2
 */
public class SRPServerSession
{
	/**
	 * @param constants constants to use
	 * @param verifier the verifier as returned from {@link SRPFactory#makeVerifier(byte[])}
	 */
	public SRPServerSession(SRPConstants constants, SRPVerifier verifier)
	{
		fConstants = constants;
		fVerifier = verifier;
		fRandom_b = SRPUtils.random(fConstants);
		fSRP6_u = new BigInteger("-1",10);
		fPublicKey_A = null;
		fCommonValue_S = null;
		fEvidenceValue_M1 = null;
		fSessionKey_K = null;
		
		calculatepublicKeyB();

		
	}
	
	private void calculatepublicKeyB(){
		
		fRandom_b = SRPUtils.random(fConstants);
		// B = 3v + g^b
		fPublicKey_B = fVerifier.verifier_v.multiply(fConstants.srp6Multiplier_k).add(fConstants.primitiveRoot_g.modPow(fRandom_b, fConstants.largePrime_N));

		byte[] ii1 = fPublicKey_B.toByteArray();

		int counter = 0;
		for(byte tbyte : ii1){
			if(tbyte < 0) ii1[counter] ^= (1 << 7);
			counter++;
		}

		fPublicKey_B = new BigInteger(ii1);
		
	}

	/**
	 * When the client sends the public key (value A in the docs) call this method to store the value
	 *
	 * @param publicKey_A A
	 * @throws SRPAuthenticationFailedException if A is invalid
	 */
	public void				setClientPublicKey_A(BigInteger publicKey_A) throws SRPAuthenticationFailedException
	{
		if ( publicKey_A.mod(fConstants.largePrime_N).equals(BigInteger.ZERO) )
		{
			throw new SRPAuthenticationFailedException("A%N == 0");
		}

		fPublicKey_A = publicKey_A;

		do{
			calculatepublicKeyB();
			String k1 = fPublicKey_A.toString();
			String k2 = fPublicKey_B.toString();

			byte[] ii1 = fPublicKey_A.toByteArray();
			byte[] ii2 = fPublicKey_B.toByteArray();

			int counter = 0;
			for(byte tbyte : ii1){
				if(tbyte < 0) ii1[counter] ^= (1 << 7);
				counter++;
			}
			counter=0;
			for(byte tbyte : ii2){
				if(tbyte < 0) ii2[counter] ^= (1 << 7);
				counter++;

			}

			byte[] ii3 = new byte[ii1.length + ii2.length];
			System.arraycopy(ii1, 0, ii3, 0, ii1.length);
			System.arraycopy(ii2, 0, ii3, ii1.length, ii2.length);

			System.out.println(Arrays.toString(ii3));
			BigInteger xx = new BigInteger(ii3);
			byte[] output =  new byte[32];
			try{
				MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
				output = sha256.digest(xx.toByteArray());
				String s_output = new String(Hex.encodeHex(output));
				System.out.println(s_output);
			}catch(Exception e){}

			fSRP6_u = new BigInteger(output);
		}while(fSRP6_u.signum() != 1);
		
		if ( fSRP6_u.mod(fConstants.largePrime_N).equals(BigInteger.ZERO) )
		{
			throw new SRPAuthenticationFailedException("u%N == 0");
		}

	}

	/**
	 * Returns the public key that should be sent to the client (value B in the docs).
	 *
	 * @return B
	 */
	public BigInteger		getPublicKey_B()
	{
		return fPublicKey_B;
	}

	/**
	 * Call to calculate the common session key (S/K in the docs)
	 */
	public void				computeCommonValue_S()
	{
		if ( fPublicKey_A == null )
		{
			throw new IllegalStateException("setClientPublicKey_A() has not been called yet.");
		}

		// S = (A � v^u)^b
		fCommonValue_S = fPublicKey_A.multiply(fVerifier.verifier_v.modPow(fSRP6_u, fConstants.largePrime_N)).modPow(fRandom_b, fConstants.largePrime_N);
		fEvidenceValue_M1 = SRPUtils.calcM1(fPublicKey_A, fPublicKey_B, fCommonValue_S);

		// the MD5 output is the same as the AES key length
		fSessionKey_K = SRPUtils.hashToBytesMD5(fCommonValue_S);
	}

	/**
	 * When M(1) is received from the client, call this method to validate it
	 *
	 * @param evidenceValueFromClient_M1 M(1) as recevied from the client
	 * @throws SRPAuthenticationFailedException if M(1) is incorrect
	 */
	public void			validateClientEvidenceValue_M1(BigInteger evidenceValueFromClient_M1) throws SRPAuthenticationFailedException
	{
		if ( fEvidenceValue_M1 == null )
		{
			throw new IllegalStateException("computeCommonValue_S() has not been called yet.");
		}

		if ( !fEvidenceValue_M1.equals(evidenceValueFromClient_M1) )
		{
			throw new SRPAuthenticationFailedException("M(1) incorrect");
		}
	}

	/**
	 * Return the value M(2) that should be sent to the client
	 *
	 * @return M(2)
	 */
	public BigInteger		getEvidenceValue_M2()
	{
		if ( fEvidenceValue_M1 == null )
		{
			throw new IllegalStateException("computeCommonValue_S() has not been called yet.");
		}

		return SRPUtils.calcM2(fPublicKey_A, fEvidenceValue_M1, fCommonValue_S);
	}

	/**
	 * Returns the session common value which is the pre-hashed version of K
	 *
	 * @return common value
	 */
	public BigInteger 	getSessionCommonValue()
	{
		return fCommonValue_S;
	}

	/**
	 * The 16 byte session key suitable for encryption
	 *
	 * @return session key - K
	 */
	public byte[] 		getSessionKey_K()
	{
		return fSessionKey_K;
	}

	public SRPConstants		getConstants()
	{
		return fConstants;
	}

	SRPVerifier			getVerifier()
	{
		return fVerifier;
	}

	public BigInteger getU(){
		return fSRP6_u;
	}

	public BigInteger getPublicKey_A(){
		return fPublicKey_A;
	}

	private SRPConstants 		fConstants;
	private SRPVerifier 		fVerifier;
	private BigInteger 			fRandom_b;
	private BigInteger 			fSRP6_u;
	private BigInteger 			fPublicKey_A;
	private BigInteger 			fPublicKey_B;
	private BigInteger 			fCommonValue_S;
	private byte[]	 			fSessionKey_K;
	private BigInteger 			fEvidenceValue_M1;
}
