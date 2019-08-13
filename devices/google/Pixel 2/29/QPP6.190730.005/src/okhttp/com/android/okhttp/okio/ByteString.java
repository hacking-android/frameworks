/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Base64;
import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.Util;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class ByteString
implements Serializable,
Comparable<ByteString> {
    public static final ByteString EMPTY;
    static final char[] HEX_DIGITS;
    private static final long serialVersionUID = 1L;
    final byte[] data;
    transient int hashCode;
    transient String utf8;

    static {
        HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        EMPTY = ByteString.of(new byte[0]);
    }

    ByteString(byte[] arrby) {
        this.data = arrby;
    }

    public static ByteString decodeBase64(String object) {
        if (object != null) {
            object = (object = Base64.decode((String)object)) != null ? new ByteString((byte[])object) : null;
            return object;
        }
        throw new IllegalArgumentException("base64 == null");
    }

    public static ByteString decodeHex(String string) {
        if (string != null) {
            if (string.length() % 2 == 0) {
                byte[] arrby = new byte[string.length() / 2];
                for (int i = 0; i < arrby.length; ++i) {
                    arrby[i] = (byte)((ByteString.decodeHexDigit(string.charAt(i * 2)) << 4) + ByteString.decodeHexDigit(string.charAt(i * 2 + 1)));
                }
                return ByteString.of(arrby);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected hex string: ");
            stringBuilder.append(string);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        throw new IllegalArgumentException("hex == null");
    }

    private static int decodeHexDigit(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected hex digit: ");
        stringBuilder.append(c);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private ByteString digest(String object) {
        try {
            object = ByteString.of(MessageDigest.getInstance((String)object).digest(this.data));
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
    }

    public static ByteString encodeUtf8(String string) {
        if (string != null) {
            ByteString byteString = new ByteString(string.getBytes(Util.UTF_8));
            byteString.utf8 = string;
            return byteString;
        }
        throw new IllegalArgumentException("s == null");
    }

    public static ByteString of(byte ... arrby) {
        if (arrby != null) {
            return new ByteString((byte[])arrby.clone());
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString of(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            Util.checkOffsetAndCount(arrby.length, n, n2);
            byte[] arrby2 = new byte[n2];
            System.arraycopy((byte[])arrby, (int)n, (byte[])arrby2, (int)0, (int)n2);
            return new ByteString(arrby2);
        }
        throw new IllegalArgumentException("data == null");
    }

    public static ByteString read(InputStream object, int n) throws IOException {
        if (object != null) {
            if (n >= 0) {
                int n2;
                byte[] arrby = new byte[n];
                for (int i = 0; i < n; i += n2) {
                    n2 = ((InputStream)object).read(arrby, i, n - i);
                    if (n2 != -1) {
                        continue;
                    }
                    throw new EOFException();
                }
                return new ByteString(arrby);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("byteCount < 0: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("in == null");
    }

    private void readObject(ObjectInputStream object) throws IOException {
        object = ByteString.read((InputStream)object, ((ObjectInputStream)object).readInt());
        try {
            Field field = ByteString.class.getDeclaredField("data");
            field.setAccessible(true);
            field.set(this, ((ByteString)object).data);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new AssertionError();
        }
        catch (NoSuchFieldException noSuchFieldException) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeInt(this.data.length);
        objectOutputStream.write(this.data);
    }

    public String base64() {
        return Base64.encode(this.data);
    }

    public String base64Url() {
        return Base64.encodeUrl(this.data);
    }

    @Override
    public int compareTo(ByteString byteString) {
        int n;
        int n2;
        int n3;
        block4 : {
            int n4;
            int n5;
            n3 = this.size();
            n = byteString.size();
            int n6 = 0;
            int n7 = Math.min(n3, n);
            do {
                n2 = -1;
                if (n6 >= n7) break block4;
                n5 = this.getByte(n6) & 255;
                if (n5 != (n4 = byteString.getByte(n6) & 255)) break;
                ++n6;
            } while (true);
            if (n5 >= n4) {
                n2 = 1;
            }
            return n2;
        }
        if (n3 == n) {
            return 0;
        }
        if (n3 >= n) {
            n2 = 1;
        }
        return n2;
    }

    public boolean equals(Object object) {
        int n;
        byte[] arrby;
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteString) || (n = ((ByteString)object).size()) != (arrby = this.data).length || !((ByteString)object).rangeEquals(0, arrby, 0, arrby.length)) {
            bl = false;
        }
        return bl;
    }

    public byte getByte(int n) {
        return this.data[n];
    }

    public int hashCode() {
        int n = this.hashCode;
        if (n == 0) {
            this.hashCode = n = Arrays.hashCode(this.data);
        }
        return n;
    }

    public String hex() {
        byte[] arrby = this.data;
        char[] arrc = new char[arrby.length * 2];
        int n = 0;
        for (byte by : arrby) {
            int n2 = n + 1;
            char[] arrc2 = HEX_DIGITS;
            arrc[n] = arrc2[by >> 4 & 15];
            n = n2 + 1;
            arrc[n2] = arrc2[by & 15];
        }
        return new String(arrc);
    }

    public ByteString md5() {
        return this.digest("MD5");
    }

    public boolean rangeEquals(int n, ByteString byteString, int n2, int n3) {
        return byteString.rangeEquals(n2, this.data, n, n3);
    }

    public boolean rangeEquals(int n, byte[] arrby, int n2, int n3) {
        byte[] arrby2 = this.data;
        boolean bl = n <= arrby2.length - n3 && n2 <= arrby.length - n3 && Util.arrayRangeEquals(arrby2, n, arrby, n2, n3);
        return bl;
    }

    public ByteString sha256() {
        return this.digest("SHA-256");
    }

    public int size() {
        return this.data.length;
    }

    public ByteString substring(int n) {
        return this.substring(n, this.data.length);
    }

    public ByteString substring(int n, int n2) {
        if (n >= 0) {
            Object object = this.data;
            if (n2 <= ((byte[])object).length) {
                int n3 = n2 - n;
                if (n3 >= 0) {
                    if (n == 0 && n2 == ((Object)object).length) {
                        return this;
                    }
                    object = new byte[n3];
                    System.arraycopy((byte[])this.data, (int)n, (byte[])object, (int)0, (int)n3);
                    return new ByteString((byte[])object);
                }
                throw new IllegalArgumentException("endIndex < beginIndex");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("endIndex > length(");
            ((StringBuilder)object).append(this.data.length);
            ((StringBuilder)object).append(")");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("beginIndex < 0");
    }

    public ByteString toAsciiLowercase() {
        byte[] arrby;
        for (int i = 0; i < (arrby = this.data).length; ++i) {
            byte by = arrby[i];
            if (by < 65 || by > 90) continue;
            arrby = (byte[])arrby.clone();
            int n = i + 1;
            arrby[i] = (byte)(by + 32);
            for (i = n; i < arrby.length; ++i) {
                n = arrby[i];
                if (n < 65 || n > 90) continue;
                arrby[i] = (byte)(n + 32);
            }
            return new ByteString(arrby);
        }
        return this;
    }

    public ByteString toAsciiUppercase() {
        byte[] arrby;
        for (int i = 0; i < (arrby = this.data).length; ++i) {
            byte by = arrby[i];
            if (by < 97 || by > 122) continue;
            arrby = (byte[])arrby.clone();
            int n = i + 1;
            arrby[i] = (byte)(by - 32);
            for (i = n; i < arrby.length; ++i) {
                n = arrby[i];
                if (n < 97 || n > 122) continue;
                arrby[i] = (byte)(n - 32);
            }
            return new ByteString(arrby);
        }
        return this;
    }

    public byte[] toByteArray() {
        return (byte[])this.data.clone();
    }

    public String toString() {
        byte[] arrby = this.data;
        if (arrby.length == 0) {
            return "ByteString[size=0]";
        }
        if (arrby.length <= 16) {
            return String.format("ByteString[size=%s data=%s]", arrby.length, this.hex());
        }
        return String.format("ByteString[size=%s md5=%s]", arrby.length, this.md5().hex());
    }

    public String utf8() {
        String string = this.utf8;
        if (string == null) {
            this.utf8 = string = new String(this.data, Util.UTF_8);
        }
        return string;
    }

    void write(Buffer buffer) {
        byte[] arrby = this.data;
        buffer.write(arrby, 0, arrby.length);
    }

    public void write(OutputStream outputStream) throws IOException {
        if (outputStream != null) {
            outputStream.write(this.data);
            return;
        }
        throw new IllegalArgumentException("out == null");
    }
}

