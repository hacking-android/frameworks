/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util;

import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.util.Arrays;

public class Fingerprint {
    private static char[] encodingTable = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private final byte[] fingerprint;

    public Fingerprint(byte[] arrby) {
        this(arrby, 160);
    }

    public Fingerprint(byte[] arrby, int n) {
        this.fingerprint = Fingerprint.calculateFingerprint(arrby, n);
    }

    public static byte[] calculateFingerprint(byte[] arrby) {
        return Fingerprint.calculateFingerprint(arrby, 160);
    }

    public static byte[] calculateFingerprint(byte[] arrby, int n) {
        if (n % 8 == 0) {
            Digest digest = AndroidDigestFactory.getSHA256();
            digest.update(arrby, 0, arrby.length);
            arrby = new byte[n / 8];
            byte[] arrby2 = new byte[32];
            digest.doFinal(arrby2, 0);
            if (n / 8 >= 32) {
                return arrby2;
            }
            System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)0, (int)arrby.length);
            return arrby;
        }
        throw new IllegalArgumentException("bitLength must be a multiple of 8");
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Fingerprint) {
            return Arrays.areEqual(((Fingerprint)object).fingerprint, this.fingerprint);
        }
        return false;
    }

    public byte[] getFingerprint() {
        return Arrays.clone(this.fingerprint);
    }

    public int hashCode() {
        return Arrays.hashCode(this.fingerprint);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i != this.fingerprint.length; ++i) {
            if (i > 0) {
                stringBuffer.append(":");
            }
            stringBuffer.append(encodingTable[this.fingerprint[i] >>> 4 & 15]);
            stringBuffer.append(encodingTable[this.fingerprint[i] & 15]);
        }
        return stringBuffer.toString();
    }
}

