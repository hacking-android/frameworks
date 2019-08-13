/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

class OpenSSLBIOInputStream
extends FilterInputStream {
    private long ctx;

    @UnsupportedAppUsage
    OpenSSLBIOInputStream(InputStream inputStream, boolean bl) {
        super(inputStream);
        this.ctx = NativeCrypto.create_BIO_InputStream(this, bl);
    }

    @UnsupportedAppUsage
    long getBioContext() {
        return this.ctx;
    }

    int gets(byte[] arrby) throws IOException {
        if (arrby != null && arrby.length != 0) {
            int n;
            int n2 = 0;
            while (n2 < arrby.length && (n = this.read()) != -1) {
                if (n == 10) {
                    if (n2 != 0) break;
                    continue;
                }
                arrby[n2] = (byte)n;
                ++n2;
            }
            return n2;
        }
        return 0;
    }

    @Override
    public int read(byte[] arrby) throws IOException {
        return this.read(arrby, 0, arrby.length);
    }

    @Override
    public int read(byte[] arrby, int n, int n2) throws IOException {
        if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
            int n3;
            int n4;
            block3 : {
                int n5;
                if (n2 == 0) {
                    return 0;
                }
                n4 = 0;
                do {
                    n5 = super.read(arrby, n + n4, n2 - n4 - n);
                    n3 = -1;
                    if (n5 == -1) break block3;
                    n4 = n5 = n4 + n5;
                } while (n + n5 < n2);
                n4 = n5;
            }
            n = n4 == 0 ? n3 : n4;
            return n;
        }
        throw new IndexOutOfBoundsException("Invalid bounds");
    }

    @UnsupportedAppUsage
    void release() {
        NativeCrypto.BIO_free_all(this.ctx);
    }
}

