/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.LimitedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

class StreamUtil {
    StreamUtil() {
    }

    static int calculateBodyLength(int n) {
        int n2 = 1;
        int n3 = 1;
        if (n > 127) {
            n2 = 1;
            int n4 = n;
            n = n2;
            do {
                n4 = n2 = n4 >>> 8;
                if (n2 == 0) break;
                ++n;
            } while (true);
            n4 = (n - 1) * 8;
            n = n3;
            do {
                n2 = n++;
                if (n4 < 0) break;
                n4 -= 8;
            } while (true);
        }
        return n2;
    }

    static int calculateTagLength(int n) throws IOException {
        int n2 = 1;
        if (n >= 31) {
            if (n < 128) {
                n2 = 1 + 1;
            } else {
                int n3;
                int n4;
                byte[] arrby = new byte[5];
                n2 = arrby.length - 1;
                arrby[n2] = (byte)(n & 127);
                do {
                    n4 = n >> 7;
                    n3 = n2 - 1;
                    arrby[n3] = (byte)(n4 & 127 | 128);
                    n2 = n3;
                    n = n4;
                } while (n4 > 127);
                n2 = 1 + (arrby.length - n3);
            }
        }
        return n2;
    }

    static int findLimit(InputStream closeable) {
        long l;
        if (closeable instanceof LimitedInputStream) {
            return ((LimitedInputStream)closeable).getRemaining();
        }
        if (closeable instanceof ASN1InputStream) {
            return ((ASN1InputStream)closeable).getLimit();
        }
        if (closeable instanceof ByteArrayInputStream) {
            return ((ByteArrayInputStream)closeable).available();
        }
        if (closeable instanceof FileInputStream) {
            block10 : {
                block9 : {
                    closeable = ((FileInputStream)closeable).getChannel();
                    if (closeable == null) break block9;
                    try {
                        l = ((FileChannel)closeable).size();
                        break block10;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                l = Integer.MAX_VALUE;
            }
            if (l < Integer.MAX_VALUE) {
                return (int)l;
            }
        }
        if ((l = Runtime.getRuntime().maxMemory()) > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int)l;
    }
}

