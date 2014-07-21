package com.insta4j.instagram.util;


import com.insta4j.instagram.exception.InstagramException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class InstagramUtil {


	public static String createSHAKey(String ipAddress) throws InstagramException {


		String digest = null;
	  SecretKeySpec signingKey = new SecretKeySpec(getClientSecret().getBytes(), Constants.HMAC_SHA256_ALGO);
		Mac mac = null;
		try {
			mac = Mac.getInstance(Constants.HMAC_SHA256_ALGO);
			mac.init(signingKey);
			byte[] rawHmac = mac.doFinal(ipAddress.getBytes());
			digest = Hex.encodeHexString(rawHmac);

		} catch (NoSuchAlgorithmException e) {
			throw new InstagramException(-1, e.getMessage(), "Undefined", e);
		}
		  catch (InvalidKeyException e) {
 		  throw new InstagramException(-1, e.getMessage(), "Undefined", e);
		}

		return digest;
	}

	public static String getClientSecret(){

		try {
			InputStream input = new FileInputStream("insta4jprop.properties");
			Properties prop = new Properties();
			prop.load(input);
			return prop.getProperty(Constants.PARAM_CLIENT_SECRET);
		}
		catch (Exception e) {

		}
		return null;
	}
}