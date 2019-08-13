/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class Serialization {
    private static final int DER_LENGTH_LONG_FORM_FLAG = 128;
    private static final int DER_TAG_MASK = 63;
    private static final int DER_TAG_OCTET_STRING = 4;

    private Serialization() {
    }

    public static byte readByte(InputStream object) throws SerializationException {
        block3 : {
            int n;
            try {
                n = ((InputStream)object).read();
                if (n == -1) break block3;
            }
            catch (IOException iOException) {
                throw new SerializationException(iOException);
            }
            return (byte)n;
        }
        object = new SerializationException("Premature end of input, could not read byte.");
        throw object;
    }

    public static byte[] readDEROctetString(InputStream object) throws SerializationException {
        int n = Serialization.readByte((InputStream)object) & 63;
        if (n == 4) {
            n = Serialization.readNumber((InputStream)object, 1);
            if ((n & 128) != 0) {
                n = Serialization.readNumber((InputStream)object, n & -129);
            }
            return Serialization.readFixedBytes((InputStream)object, n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Wrong DER tag, expected OCTET STRING, got ");
        ((StringBuilder)object).append(n);
        throw new SerializationException(((StringBuilder)object).toString());
    }

    public static byte[] readDEROctetString(byte[] arrby) throws SerializationException {
        return Serialization.readDEROctetString(new ByteArrayInputStream(arrby));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] readFixedBytes(InputStream object, int n) throws SerializationException {
        if (n >= 0) {
            try {
                Object object2 = new byte[n];
                int n2 = ((InputStream)object).read((byte[])object2);
                if (n2 >= n) {
                    return object2;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Premature end of input, expected ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" bytes, only read ");
                ((StringBuilder)object2).append(n2);
                object = new SerializationException(((StringBuilder)object2).toString());
                throw object;
            }
            catch (IOException iOException) {
                throw new SerializationException(iOException);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Negative length: ");
        ((StringBuilder)object).append(n);
        SerializationException serializationException = new SerializationException(((StringBuilder)object).toString());
        throw serializationException;
    }

    public static byte[][] readList(InputStream inputStream, int n, int n2) throws SerializationException {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        inputStream = new ByteArrayInputStream(Serialization.readVariableBytes(inputStream, n));
        try {
            while (inputStream.available() > 0) {
                arrayList.add(Serialization.readVariableBytes(inputStream, n2));
            }
        }
        catch (IOException iOException) {
            throw new SerializationException(iOException);
        }
        return (byte[][])arrayList.toArray((T[])new byte[arrayList.size()][]);
    }

    public static byte[][] readList(byte[] arrby, int n, int n2) throws SerializationException {
        return Serialization.readList(new ByteArrayInputStream(arrby), n, n2);
    }

    public static long readLong(InputStream object, int n) throws SerializationException {
        if (n <= 8 && n >= 0) {
            long l = 0L;
            for (int i = 0; i < n; ++i) {
                l = l << 8 | (long)(Serialization.readByte((InputStream)object) & 255);
            }
            return l;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid width: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static int readNumber(InputStream object, int n) throws SerializationException {
        if (n <= 4 && n >= 0) {
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                n2 = n2 << 8 | Serialization.readByte((InputStream)object) & 255;
            }
            return n2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid width: ");
        ((StringBuilder)object).append(n);
        throw new SerializationException(((StringBuilder)object).toString());
    }

    public static byte[] readVariableBytes(InputStream inputStream, int n) throws SerializationException {
        return Serialization.readFixedBytes(inputStream, Serialization.readNumber(inputStream, n));
    }

    public static void writeFixedBytes(OutputStream outputStream, byte[] arrby) throws SerializationException {
        try {
            outputStream.write(arrby);
            return;
        }
        catch (IOException iOException) {
            throw new SerializationException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void writeNumber(OutputStream var0, long var1_2, int var3_3) throws SerializationException {
        if (var3_3 < 0) {
            var0 = new StringBuilder();
            var0.append("Negative width: ");
            var0.append(var3_3);
            throw new SerializationException(var0.toString());
        }
        var4_4 = var3_3;
        if (var3_3 < 8) {
            if (var1_2 >= 1L << var3_3 * 8) {
                var0 = new StringBuilder();
                var0.append("Number too large, ");
                var0.append(var1_2);
                var0.append(" does not fit in ");
                var0.append(var3_3);
                var0.append(" bytes");
                throw new SerializationException(var0.toString());
            }
            var4_4 = var3_3;
        }
        while (var4_4 > 0) {
            var5_5 = (long)(var4_4 - 1) * 8L;
            if (var5_5 >= 64L) ** GOTO lbl31
            var3_3 = (byte)(var1_2 >> (int)var5_5 & 255L);
            try {
                block6 : {
                    var0.write(var3_3);
                    break block6;
lbl31: // 1 sources:
                    var0.write(0);
                }
                --var4_4;
            }
            catch (IOException var0_1) {
                throw new SerializationException(var0_1);
            }
        }
    }

    public static void writeVariableBytes(OutputStream outputStream, byte[] arrby, int n) throws SerializationException {
        Serialization.writeNumber(outputStream, arrby.length, n);
        Serialization.writeFixedBytes(outputStream, arrby);
    }
}

