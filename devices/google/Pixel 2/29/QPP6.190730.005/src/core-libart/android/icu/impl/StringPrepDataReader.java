/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUBinary;
import android.icu.impl.ICUDebug;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public final class StringPrepDataReader
implements ICUBinary.Authenticate {
    private static final int DATA_FORMAT_ID = 1397772880;
    private static final byte[] DATA_FORMAT_VERSION;
    private static final boolean debug;
    private ByteBuffer byteBuffer;
    private int unicodeVersion;

    static {
        debug = ICUDebug.enabled("NormalizerDataReader");
        DATA_FORMAT_VERSION = new byte[]{3, 2, 5, 2};
    }

    public StringPrepDataReader(ByteBuffer object) throws IOException {
        PrintStream printStream;
        if (debug) {
            printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bytes in buffer ");
            stringBuilder.append(((Buffer)object).remaining());
            printStream.println(stringBuilder.toString());
        }
        this.byteBuffer = object;
        this.unicodeVersion = ICUBinary.readHeader(this.byteBuffer, 1397772880, this);
        if (debug) {
            printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("Bytes left in byteBuffer ");
            ((StringBuilder)object).append(this.byteBuffer.remaining());
            printStream.println(((StringBuilder)object).toString());
        }
    }

    public byte[] getUnicodeVersion() {
        return ICUBinary.getVersionByteArrayFromCompactInt(this.unicodeVersion);
    }

    @Override
    public boolean isDataVersionAcceptable(byte[] arrby) {
        boolean bl = false;
        byte by = arrby[0];
        byte[] arrby2 = DATA_FORMAT_VERSION;
        boolean bl2 = bl;
        if (by == arrby2[0]) {
            bl2 = bl;
            if (arrby[2] == arrby2[2]) {
                bl2 = bl;
                if (arrby[3] == arrby2[3]) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    public char[] read(int n) throws IOException {
        return ICUBinary.getChars(this.byteBuffer, n, 0);
    }

    public int[] readIndexes(int n) throws IOException {
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = this.byteBuffer.getInt();
        }
        return arrn;
    }
}

