/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.mms.pdu;

import java.io.ByteArrayOutputStream;

public class QuotedPrintable {
    private static byte ESCAPE_CHAR = (byte)61;

    public static final byte[] decodeQuotedPrintable(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < arrby.length; ++i) {
            int n = arrby[i];
            if (n == ESCAPE_CHAR) {
                block8 : {
                    if ('\r' == (char)arrby[i + 1] && '\n' == (char)arrby[i + 2]) {
                        i += 2;
                        continue;
                    }
                    ++i;
                    try {
                        n = Character.digit((char)arrby[i], 16);
                        ++i;
                    }
                    catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                        return null;
                    }
                    int n2 = Character.digit((char)arrby[i], 16);
                    if (n == -1 || n2 == -1) break block8;
                    byteArrayOutputStream.write((char)((n << 4) + n2));
                    continue;
                }
                return null;
            }
            byteArrayOutputStream.write(n);
        }
        return byteArrayOutputStream.toByteArray();
    }
}

