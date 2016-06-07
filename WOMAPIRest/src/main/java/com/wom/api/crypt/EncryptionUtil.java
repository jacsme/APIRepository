package com.wom.api.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.wom.api.util.HelperUtil;

public class EncryptionUtil {
	
	static final Logger logger = Logger.getLogger(EncryptionUtil.class);
	public static String encrypt(String strword){
        byte[] raw;
        String encryptedString;
        SecretKeySpec skeySpec;
        byte[] encryptText = strword.getBytes();
        Cipher cipher;
        try {
            raw = Base64.decodeBase64(HelperUtil.SECRET_KEY);
            skeySpec = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            encryptedString = Base64.encodeBase64String(cipher.doFinal(encryptText));
        } 
        catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return encryptedString;
	}
}
