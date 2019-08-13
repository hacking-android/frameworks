/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.util.encoders;

import com.android.org.bouncycastle.util.Strings;
import com.android.org.bouncycastle.util.encoders.DecoderException;
import com.android.org.bouncycastle.util.encoders.Encoder;
import com.android.org.bouncycastle.util.encoders.EncoderException;
import com.android.org.bouncycastle.util.encoders.HexEncoder;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Hex {
    private static final Encoder encoder = new HexEncoder();

    public static int decode(String string, OutputStream outputStream) throws IOException {
        return encoder.decode(string, outputStream);
    }

    public static byte[] decode(String charSequence) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encoder.decode((String)charSequence, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception exception) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("exception decoding Hex string: ");
            ((StringBuilder)charSequence).append(exception.getMessage());
            throw new DecoderException(((StringBuilder)charSequence).toString(), exception);
        }
    }

    public static byte[] decode(byte[] object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            encoder.decode((byte[])object, 0, ((byte[])object).length, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("exception decoding Hex data: ");
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
        return Hex.encode(arrby, 0, arrby.length);
    }

    public static byte[] encode(byte[] arrby, int n, int n2) {
        Object object = new ByteArrayOutputStream();
        try {
            encoder.encode(arrby, n, n2, (OutputStream)object);
            return ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("exception encoding Hex string: ");
            ((StringBuilder)object).append(exception.getMessage());
            throw new EncoderException(((StringBuilder)object).toString(), exception);
        }
    }

    public static String toHexString(byte[] arrby) {
        return Hex.toHexString(arrby, 0, arrby.length);
    }

    public static String toHexString(byte[] arrby, int n, int n2) {
        return Strings.fromByteArray(Hex.encode(arrby, n, n2));
    }
}

