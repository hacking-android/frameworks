/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.crypto.Mac;

public class MacAuthenticatedInputStream
extends FilterInputStream {
    private final Mac mMac;

    public MacAuthenticatedInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mMac = mac;
    }

    public boolean isTagEqual(byte[] arrby) {
        byte[] arrby2 = this.mMac.doFinal();
        boolean bl = false;
        if (arrby != null && arrby2 != null && arrby.length == arrby2.length) {
            int n = 0;
            for (int i = 0; i < arrby.length; ++i) {
                n |= arrby[i] ^ arrby2[i];
            }
            if (n == 0) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @Override
    public int read() throws IOException {
        int n = super.read();
        if (n >= 0) {
            this.mMac.update((byte)n);
        }
        return n;
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if ((n2 = super.read(arrby, n, n2)) > 0) {
            this.mMac.update(arrby, n, n2);
        }
        return n2;
    }
}

