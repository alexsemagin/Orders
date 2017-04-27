package ru.rarus.restart.orders.data.retrofit.dto;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


public class SignInRequest {

    private String password;
    private String session_id;

    private SignInRequest(String password, String sessionId) {
        this.password = password;
        this.session_id = sessionId;
    }

    public static SignInRequest create(String password) {
        try {
            String sha1pass = SHA1(password);
            String sessionId = UUID.randomUUID().toString();
            Log.d("SignInRequest", "password " + password);
            Log.d("SignInRequest", "sha1pass " + sha1pass);
            Log.d("SignInRequest", "sessionId " + sessionId);
            return new SignInRequest(sha1pass, sessionId);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }
}
