/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  javax.annotation.concurrent.NotThreadSafe
 */
package com.google.android.rappor;

import java.security.Key;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.annotation.concurrent.NotThreadSafe;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

@NotThreadSafe
public class HmacDrbg {
    private static final byte[] BYTE_ARRAY_0 = new byte[]{0};
    private static final byte[] BYTE_ARRAY_1 = new byte[]{1};
    private static final int DIGEST_NUM_BYTES = 32;
    public static final int ENTROPY_INPUT_SIZE_BYTES = 48;
    private static final int MAX_BYTES_PER_REQUEST = 937;
    public static final int MAX_BYTES_TOTAL = 10000;
    public static final int MAX_PERSONALIZATION_STRING_LENGTH_BYTES = 20;
    public static final int SECURITY_STRENGTH = 256;
    private int bytesGenerated;
    private Mac hmac;
    private byte[] value;

    public HmacDrbg(byte[] arrby, byte[] arrby2) {
        arrby = HmacDrbg.bytesConcat(arrby, HmacDrbg.emptyIfNull(arrby2));
        this.setKey(new byte[32]);
        this.value = new byte[32];
        Arrays.fill(this.value, (byte)1);
        this.hmacDrbgUpdate(arrby);
        this.bytesGenerated = 0;
    }

    private static byte[] bytesConcat(byte[] ... arrby) {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            n3 += arrby[n].length;
        }
        byte[] arrby2 = new byte[n3];
        n2 = arrby.length;
        n = 0;
        for (n3 = 0; n3 < n2; ++n3) {
            byte[] arrby3 = arrby[n3];
            System.arraycopy(arrby3, 0, arrby2, n, arrby3.length);
            n += arrby3.length;
        }
        return arrby2;
    }

    private static byte[] emptyIfNull(byte[] arrby) {
        block0 : {
            if (arrby != null) break block0;
            arrby = new byte[]{};
        }
        return arrby;
    }

    public static byte[] generateEntropyInput() {
        byte[] arrby = new byte[48];
        new SecureRandom().nextBytes(arrby);
        return arrby;
    }

    private byte[] hash(byte[] arrby) {
        try {
            arrby = this.hmac.doFinal(arrby);
            return arrby;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private void hmacDrbgGenerate(byte[] arrby, int n, int n2) {
        int n3;
        for (int i = 0; i < n2; i += n3) {
            this.value = this.hash(this.value);
            n3 = Math.min(n2 - i, 32);
            System.arraycopy(this.value, 0, arrby, n + i, n3);
        }
        this.hmacDrbgUpdate(null);
    }

    private void hmacDrbgUpdate(byte[] arrby) {
        this.setKey(this.hash(HmacDrbg.bytesConcat(this.value, BYTE_ARRAY_0, HmacDrbg.emptyIfNull(arrby))));
        this.value = this.hash(this.value);
        if (arrby == null) {
            return;
        }
        this.setKey(this.hash(HmacDrbg.bytesConcat(this.value, BYTE_ARRAY_1, arrby)));
        this.value = this.hash(this.value);
    }

    private void setKey(byte[] arrby) {
        try {
            this.hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(arrby, "HmacSHA256");
            this.hmac.init(secretKeySpec);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void nextBytes(byte[] arrby) {
        this.nextBytes(arrby, 0, arrby.length);
    }

    public void nextBytes(byte[] object, int n, int n2) {
        if (n2 == 0) {
            return;
        }
        if (this.bytesGenerated + n2 <= 10000) {
            int n3;
            for (int i = 0; i < n2; i += n3) {
                try {
                    n3 = Math.min(n2 - i, 937);
                    this.hmacDrbgGenerate((byte[])object, n + i, n3);
                }
                catch (Throwable throwable) {
                    this.bytesGenerated += n2;
                    throw throwable;
                }
            }
            this.bytesGenerated += n2;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot generate more than a total of ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(" bytes.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public byte[] nextBytes(int n) {
        byte[] arrby = new byte[n];
        this.nextBytes(arrby);
        return arrby;
    }
}

