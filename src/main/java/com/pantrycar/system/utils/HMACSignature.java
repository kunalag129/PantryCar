package com.pantrycar.system.utils;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;

/**
 * Created by kunal.agarwal on 03/05/15.
 */
public class HMACSignature {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static String getSignature(String data, String key) throws SignatureException {
        String result = "";
        try {

            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(data.getBytes());
            result = Hex.encodeHexString(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }

}
