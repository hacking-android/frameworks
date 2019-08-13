/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.Base64Encoder;
import com.android.org.bouncycastle.util.encoders.DecoderException;
import com.android.org.bouncycastle.util.encoders.Encoder;
import com.android.org.bouncycastle.util.encoders.EncoderException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Base64 {
    private static final Encoder encoder = new Base64Encoder();

    public static int decode(String string, OutputStream outputStream) throws IOException {
        return encoder.decode(string, outputStream);
    }

    public static int decode(byte[] object, int n, int n2, OutputStream outputStream) {
        try {
            n = encoder.decode((byte[])object, n, n2, outputStream);
            return n;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("unable to decode base64 data: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new DecoderException(((StringBuilder)object).toString(), exception);
        }
    }

    public static byte[] decode(String string) {
        Object object = new ByteArrayOutputStream(string.length() / 4 * 3);
        try {
            encoder.decode(string, (OutputStream)object);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("unable to decode base64 string: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new DecoderException(((StringBuilder)object).toString(), exception);
        }
    }

    public static byte[] decode(byte[] arrby) {
        Object object = new ByteArrayOutputStream(arrby.length / 4 * 3);
        try {
            encoder.decode(arrby, 0, arrby.length, (OutputStream)object);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("unable to decode base64 data: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new DecoderException(((StringBuilder)object).toString(), exception);
        }
    }

    public static int encode(byte[] arrby, int n, int n2, OutputStream outputStream) throws IOException {
        return encoder.encode(arrby, n, n2, outputStream);
    }

    public static int encode(byte[] arrby, OutputStream outputStream) throws IOException {
        return encoder.encode(arrby, 0, arrby.length, outputStream);
    }

    public static byte[] encode(byte[] arrby) {
        return Base64.encode(arrby, 0, arrby.length);
    }

    public static byte[] encode(byte[] object, int n, int n2) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((n2 + 2) / 3 * 4);
        try {
            encoder.encode((byte[])object, n, n2, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("exception encoding base64 string: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new EncoderException(((StringBuilder)object).toString(), exception);
        }
    }

    public static String toBase64String(byte[] arrby) {
        return Base64.toBase64String(arrby, 0, arrby.length);
    }

    public static String toBase64String(byte[] arrby, int n, int n2) {
        return Strings.fromByteArray(Base64.encode(arrby, n, n2));
    }
}

