/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.content.pm.Signature;
import android.util.ByteStringUtils;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public final class PackageUtils {
    private PackageUtils() {
    }

    public static String computeSha256Digest(byte[] arrby) {
        return ByteStringUtils.toHexString(PackageUtils.computeSha256DigestBytes(arrby));
    }

    public static byte[] computeSha256DigestBytes(byte[] arrby) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            messageDigest.update(arrby);
            return messageDigest.digest();
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return null;
        }
    }

    public static String computeSignaturesSha256Digest(Signature[] arrsignature) {
        if (arrsignature.length == 1) {
            return PackageUtils.computeSha256Digest(arrsignature[0].toByteArray());
        }
        return PackageUtils.computeSignaturesSha256Digest(PackageUtils.computeSignaturesSha256Digests(arrsignature));
    }

    public static String computeSignaturesSha256Digest(String[] arrstring) {
        int n = arrstring.length;
        if (n == 1) {
            return arrstring[0];
        }
        Arrays.sort(arrstring);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (String string2 : arrstring) {
            try {
                byteArrayOutputStream.write(string2.getBytes());
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return PackageUtils.computeSha256Digest(byteArrayOutputStream.toByteArray());
    }

    public static String[] computeSignaturesSha256Digests(Signature[] arrsignature) {
        int n = arrsignature.length;
        String[] arrstring = new String[n];
        for (int i = 0; i < n; ++i) {
            arrstring[i] = PackageUtils.computeSha256Digest(arrsignature[i].toByteArray());
        }
        return arrstring;
    }
}

