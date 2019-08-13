/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import gov.nist.core.StackLogger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestAlgorithm {
    private static final char[] toHex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String H(String string) {
        try {
            string = MessageDigestAlgorithm.toHexString(MessageDigest.getInstance("MD5").digest(string.getBytes()));
            return string;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException("Failed to instantiate an MD5 algorithm", noSuchAlgorithmException);
        }
    }

    private static String KD(String string, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(":");
        stringBuilder.append(string2);
        return MessageDigestAlgorithm.H(stringBuilder.toString());
    }

    static String calculateResponse(String charSequence, String string, String string2, String charSequence2, String string3, String charSequence3, String string4, String charSequence4, String string5, StackLogger stackLogger) {
        if (stackLogger.isLoggingEnabled()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("trying to authenticate using : ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(", ");
            stringBuilder.append(string);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            stringBuilder.append(", ");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append(", ");
            stringBuilder.append(string3);
            stringBuilder.append(", ");
            stringBuilder.append((String)charSequence3);
            stringBuilder.append(", ");
            stringBuilder.append(string4);
            stringBuilder.append(", ");
            stringBuilder.append((String)charSequence4);
            stringBuilder.append(", ");
            stringBuilder.append(string5);
            stackLogger.logDebug(stringBuilder.toString());
        }
        if (string != null && charSequence3 != null && string4 != null && string2 != null) {
            if (string3 != null && string3.length() != 0) {
                if (string5 != null && string5.trim().length() != 0 && !string5.trim().equalsIgnoreCase("auth")) {
                    charSequence = charSequence4;
                    if (charSequence4 == null) {
                        charSequence = "";
                    }
                    charSequence4 = new StringBuilder();
                    ((StringBuilder)charSequence4).append((String)charSequence3);
                    ((StringBuilder)charSequence4).append(":");
                    ((StringBuilder)charSequence4).append(string4);
                    ((StringBuilder)charSequence4).append(":");
                    ((StringBuilder)charSequence4).append(MessageDigestAlgorithm.H((String)charSequence));
                    charSequence = ((StringBuilder)charSequence4).toString();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence3);
                    ((StringBuilder)charSequence).append(":");
                    ((StringBuilder)charSequence).append(string4);
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                if (string5 != null && charSequence2 != null && (string5.equalsIgnoreCase("auth") || string5.equalsIgnoreCase("auth-int"))) {
                    charSequence3 = new StringBuilder();
                    ((StringBuilder)charSequence3).append(string2);
                    ((StringBuilder)charSequence3).append(":");
                    ((StringBuilder)charSequence3).append((String)charSequence2);
                    ((StringBuilder)charSequence3).append(":");
                    ((StringBuilder)charSequence3).append(string3);
                    ((StringBuilder)charSequence3).append(":");
                    ((StringBuilder)charSequence3).append(string5);
                    ((StringBuilder)charSequence3).append(":");
                    ((StringBuilder)charSequence3).append(MessageDigestAlgorithm.H((String)charSequence));
                    charSequence = MessageDigestAlgorithm.KD(string, ((StringBuilder)charSequence3).toString());
                } else {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append(string2);
                    ((StringBuilder)charSequence2).append(":");
                    ((StringBuilder)charSequence2).append(MessageDigestAlgorithm.H((String)charSequence));
                    charSequence = MessageDigestAlgorithm.KD(string, ((StringBuilder)charSequence2).toString());
                }
                return charSequence;
            }
            throw new NullPointerException("cnonce_value may not be absent for MD5-Sess algorithm.");
        }
        throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static String calculateResponse(String charSequence, String charSequence2, String charSequence3, String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, StackLogger object) {
        if (object.isLoggingEnabled()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("trying to authenticate using : ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(", ");
            stringBuilder.append((String)charSequence2);
            stringBuilder.append(", ");
            stringBuilder.append((String)charSequence3);
            stringBuilder.append(", ");
            boolean bl = string != null && string.trim().length() > 0;
            stringBuilder.append(bl);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            stringBuilder.append(", ");
            stringBuilder.append(string3);
            stringBuilder.append(", ");
            stringBuilder.append(string4);
            stringBuilder.append(", ");
            stringBuilder.append(string5);
            stringBuilder.append(", ");
            stringBuilder.append(string6);
            stringBuilder.append(", ");
            stringBuilder.append(string7);
            stringBuilder.append(", ");
            stringBuilder.append(string8);
            object.logDebug(stringBuilder.toString());
        }
        if (charSequence2 == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (charSequence3 == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (string == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (string5 == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (string6 == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (string2 == null) throw new NullPointerException("Null parameter to MessageDigestAlgorithm.calculateResponse()");
        if (charSequence != null && ((String)charSequence).trim().length() != 0 && !((String)charSequence).trim().equalsIgnoreCase("MD5")) {
            if (string4 == null) throw new NullPointerException("cnonce_value may not be absent for MD5-Sess algorithm.");
            if (string4.length() == 0) throw new NullPointerException("cnonce_value may not be absent for MD5-Sess algorithm.");
            object = new StringBuilder();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)object).append(MessageDigestAlgorithm.H(((StringBuilder)charSequence).toString()));
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(string4);
            charSequence = ((StringBuilder)object).toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        if (string8 != null && string8.trim().length() != 0 && !string8.trim().equalsIgnoreCase("auth")) {
            charSequence2 = string7;
            if (string7 == null) {
                charSequence2 = "";
            }
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append(string5);
            ((StringBuilder)charSequence3).append(":");
            ((StringBuilder)charSequence3).append(string6);
            ((StringBuilder)charSequence3).append(":");
            ((StringBuilder)charSequence3).append(MessageDigestAlgorithm.H((String)charSequence2));
            charSequence2 = ((StringBuilder)charSequence3).toString();
        } else {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(string5);
            ((StringBuilder)charSequence2).append(":");
            ((StringBuilder)charSequence2).append(string6);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        if (string4 != null && string8 != null && string3 != null && (string8.equalsIgnoreCase("auth") || string8.equalsIgnoreCase("auth-int"))) {
            charSequence3 = MessageDigestAlgorithm.H((String)charSequence);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string4);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(string8);
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(MessageDigestAlgorithm.H((String)charSequence2));
            return MessageDigestAlgorithm.KD((String)charSequence3, ((StringBuilder)charSequence).toString());
        }
        charSequence3 = MessageDigestAlgorithm.H((String)charSequence);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(":");
        ((StringBuilder)charSequence).append(MessageDigestAlgorithm.H((String)charSequence2));
        return MessageDigestAlgorithm.KD((String)charSequence3, ((StringBuilder)charSequence).toString());
    }

    private static String toHexString(byte[] arrby) {
        int n = 0;
        char[] arrc = new char[arrby.length * 2];
        for (int i = 0; i < arrby.length; ++i) {
            int n2 = n + 1;
            char[] arrc2 = toHex;
            arrc[n] = arrc2[arrby[i] >> 4 & 15];
            n = n2 + 1;
            arrc[n2] = arrc2[arrby[i] & 15];
        }
        return new String(arrc);
    }
}

