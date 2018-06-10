package webApp.utils;

import java.util.Base64;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class CookieMethods {

	static final String strKey = "jUo3Ap1X";

	public static String encrypt(String strToEncrypt) {

		try {
			Cipher ecipher = Cipher.getInstance("DES");
			SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "DES");
			ecipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] utf8 = strToEncrypt.getBytes("UTF8");
			byte[] enc = ecipher.doFinal(utf8);
			enc = Base64.getEncoder().encode(enc);
			return new String(enc);

		} catch (Exception e) {
			System.out.println("Encryption Failure");
		}

		return null;
	}

	public static String decrypt(String strToDecrypt) {

		try {
			Cipher dcipher = Cipher.getInstance("DES");
			SecretKeySpec key = new SecretKeySpec(strKey.getBytes(), "DES");
			dcipher.init(Cipher.DECRYPT_MODE, key);

			byte[] dec = Base64.getDecoder().decode(strToDecrypt.getBytes());
			byte[] utf8 = dcipher.doFinal(dec);

			return new String(utf8, "UTF8");
		} catch (Exception e) {
			System.out.println("Decryption Failure");
		}

		return null;
	}

	public static String createCookie(String username) {
		int rx, lx, ry, ly;
		int value = (int) (Math.random() * 100);

		rx = (int) (Math.random() * value);
		ry = value - rx;

		lx = (int) (Math.random() * value);
		ly = value - lx;

		String equation = lx + "+" + ly + "=" + rx + "+" + ry;
		String cookie = username + ";" + equation;
		cookie = encrypt(cookie);

		return cookie;
	}

	public static String getUsrnameFromCookie(String cookie) {
		try {
			int rx, lx, ry, ly;
			cookie = decrypt(cookie);
			String[] split = cookie.split(";");
			String username = split[0];
			String equation = split[1];

			String[] equSplit = equation.split("=");
			String[] left = equSplit[0].split("\\+");
			String[] right = equSplit[1].split("\\+");

			lx = Integer.parseInt(left[0]);
			ly = Integer.parseInt(left[1]);

			rx = Integer.parseInt(right[0]);
			ry = Integer.parseInt(right[1]);

			if ((lx + ly) != (rx + ry)) {
				return null;
			}

			return username;
		} catch (Exception e) {
			return null;
		}

	}

}
