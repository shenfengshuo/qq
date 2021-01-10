package org.example.utils;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtil {

	public static String rsa(String content, String privateKey) {
		String sign = null;
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(
					org.apache.commons.net.util.Base64.decodeBase64(privateKey));
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyf.generatePrivate(keySpec);
			Signature signature = Signature.getInstance("MD5withRSA");
			signature.initSign(priKey);
			signature.update(content.getBytes("UTF-8"));
			sign = org.apache.commons.net.util.Base64.encodeBase64URLSafeString(signature.sign()).trim();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return sign;
	}

	public static String snRSA(String content, String key) {
		String sign = null;
		try {
			PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(
					org.apache.commons.net.util.Base64.decodeBase64(key));
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			Signature signature = Signature.getInstance("SHA1WithRSA");
			signature.initSign(keyFactory.generatePrivate(priKeySpec));
			signature.update(content.getBytes("UTF-8"));
			sign = org.apache.commons.net.util.Base64.encodeBase64URLSafeString(signature.sign()).trim();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sign;

	}

	public static String md5(String data) {
		String returnString = data;
		if (data != null && !data.isEmpty()) {
			try {
				MessageDigest m = MessageDigest.getInstance("md5");
				m.update(data.getBytes("UTF-8"));
				byte[] s = m.digest();
				StringBuffer sb = new StringBuffer();
				for (int i = 0; i < s.length; i++)
					sb.append(Integer.toHexString(s[i] & 0xFF | 0x100).substring(1, 3));
				returnString = sb.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return returnString.toUpperCase();
	}

}
