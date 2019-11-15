package net.potm.misc;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Random;

public class SecurityUtils {

	// !WARNING! Don't change these unless you know what you are doing. Existing
	// cipher text cannot be decrypted with new values.
	private static final int KEYLENGTH = 256;
	private static final int ITERATIONS = 10;

	/**
	 * Generate Base64 encoded password hash
	 * @param pwd
	 * @param saltB64
	 * @return
	 */
	public static String hashPassword(final String pwd, final String saltB64) {

		char[] password = pwd.toCharArray();
		byte[] salt = Base64.getDecoder().decode(saltB64);

		try {
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
			PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEYLENGTH);
			SecretKey key = skf.generateSecret(spec);
			return Base64.getEncoder().encodeToString(key.getEncoded());

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Generate Base64 encoded salt
	 * @return
	 */
	public static String getSalt() {
		SecureRandom sr;
		try {
			sr = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	public static String generateRandomString(int length) {
		int lowerLimit = 97;
		int upperLimit = 122;
		Random random = new Random();
		StringBuilder buffer = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomLimitedInt = lowerLimit + (int) (random.nextFloat() * (upperLimit - lowerLimit + 1));
			buffer.append((char) randomLimitedInt);
		}
		return buffer.toString();
	}
}
