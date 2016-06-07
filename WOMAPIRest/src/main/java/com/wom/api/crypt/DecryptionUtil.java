package com.wom.api.crypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.wom.api.util.HelperUtil;

public class DecryptionUtil {
	
	static final Logger logger = Logger.getLogger(DecryptionUtil.class);
	public static String decrypt(String strword){
        Cipher cipher;
        String decryptedString;
        byte[] encryptText = null;
        byte[] raw;
        SecretKeySpec skeySpec;
        try {
            raw = Base64.decodeBase64(HelperUtil.SECRET_KEY);
            skeySpec = new SecretKeySpec(raw, "AES");
            encryptText = Base64.decodeBase64(strword);
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            decryptedString = new String(cipher.doFinal(encryptText));
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
        return decryptedString;
    }

}
