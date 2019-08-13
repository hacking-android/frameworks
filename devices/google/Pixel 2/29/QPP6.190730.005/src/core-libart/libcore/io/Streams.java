/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicReference;
import libcore.util.ArrayUtils;

public final class Streams {
    private static AtomicReference<byte[]> skipBuffer = new AtomicReference();

    private Streams() {
    }

    @UnsupportedAppUsage
    public static int copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        int n;
        int n2 = 0;
        byte[] arrby = new byte[8192];
        while ((n = inputStream.read(arrby)) != -1) {
            n2 += n;
            outputStream.write(arrby, 0, n);
        }
        return n2;
    }

    @UnsupportedAppUsage
    public static String readAsciiLine(InputStream inputStream) throws IOException {
        int n;
        StringBuilder stringBuilder = new StringBuilder(80);
        while ((n = inputStream.read()) != -1) {
            if (n == 10) {
                n = stringBuilder.length();
                if (n > 0 && stringBuilder.charAt(n - 1) == '\r') {
                    stringBuilder.setLength(n - 1);
                }
                return stringBuilder.toString();
            }
            stringBuilder.append((char)n);
        }
        throw new EOFException();
    }

    public static String readFully(Reader reader) throws IOException {
        try {
            int n;
            StringWriter stringWriter = new StringWriter();
            Object object = new char[1024];
            while ((n = reader.read((char[])object)) != -1) {
                stringWriter.write((char[])object, 0, n);
            }
            object = stringWriter.toString();
            return object;
        }
        finally {
            reader.close();
        }
    }

    @UnsupportedAppUsage
    public static void readFully(InputStream inputStream, byte[] arrby) throws IOException {
        Streams.readFully(inputStream, arrby, 0, arrby.length);
    }

    public static void readFully(InputStream inputStream, byte[] arrby, int n, int n2) throws IOException {
        if (n2 == 0) {
            return;
        }
        if (inputStream != null) {
            if (arrby != null) {
                ArrayUtils.throwsIfOutOfBounds(arrby.length, n, n2);
                while (n2 > 0) {
                    int n3 = inputStream.read(arrby, n, n2);
                    if (n3 >= 0) {
                        n += n3;
                        n2 -= n3;
                        continue;
                    }
                    throw new EOFException();
                }
                return;
            }
            throw new NullPointerException("dst == null");
        }
        throw new NullPointerException("in == null");
    }

    @UnsupportedAppUsage
    public static byte[] readFully(InputStream inputStream) throws IOException {
        try {
            byte[] arrby = Streams.readFullyNoClose(inputStream);
            return arrby;
        }
        finally {
            inputStream.close();
        }
    }

    public static byte[] readFullyNoClose(InputStream inputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[1024];
        while ((n = inputStream.read(arrby)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @UnsupportedAppUsage
    public static int readSingleByte(InputStream inputStream) throws IOException {
        byte[] arrby = new byte[1];
        int n = inputStream.read(arrby, 0, 1);
        int n2 = -1;
        if (n != -1) {
            n2 = arrby[0] & 255;
        }
        return n2;
    }

    @UnsupportedAppUsage
    public static void skipAll(InputStream inputStream) throws IOException {
        do {
            inputStream.skip(Long.MAX_VALUE);
        } while (inputStream.read() != -1);
    }

    public static long skipByReading(InputStream inputStream, long l) throws IOException {
        long l2;
        byte[] arrby;
        block3 : {
            byte[] arrby2;
            int n;
            int n2;
            arrby = arrby2 = (byte[])skipBuffer.getAndSet(null);
            if (arrby2 == null) {
                arrby = new byte[4096];
            }
            long l3 = 0L;
            do {
                l2 = l3;
                if (l3 >= l) break block3;
                n2 = (int)Math.min(l - l3, (long)arrby.length);
                n = inputStream.read(arrby, 0, n2);
                if (n == -1) {
                    l2 = l3;
                    break block3;
                }
                l3 += (long)n;
            } while (n >= n2);
            l2 = l3;
        }
        skipBuffer.set(arrby);
        return l2;
    }

    @UnsupportedAppUsage
    public static void writeSingleByte(OutputStream outputStream, int n) throws IOException {
        outputStream.write(new byte[]{(byte)(n & 255)});
    }
}

