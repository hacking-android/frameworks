/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.util.Comparator;

public class ByteArrayLexOrder
implements Comparator<byte[]> {
    @Override
    public final int compare(byte[] arrby, byte[] arrby2) {
        for (int i = 0; i < arrby.length && i < arrby2.length; ++i) {
            int n = (arrby[i] & 255) - (arrby2[i] & 255);
            if (n == 0) continue;
            return n;
        }
        return arrby.length - arrby2.length;
    }
}

