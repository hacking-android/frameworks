/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.util.Comparator;

public class ByteArrayTagOrder
implements Comparator<byte[]> {
    @Override
    public final int compare(byte[] arrby, byte[] arrby2) {
        return (arrby[0] | 32) - (arrby2[0] | 32);
    }
}

