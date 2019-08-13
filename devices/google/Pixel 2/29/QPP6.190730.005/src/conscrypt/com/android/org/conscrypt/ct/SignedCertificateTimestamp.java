/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.ct.CertificateEntry;
import com.android.org.conscrypt.ct.DigitallySigned;
import com.android.org.conscrypt.ct.Serialization;
import com.android.org.conscrypt.ct.SerializationException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SignedCertificateTimestamp {
    private final byte[] extensions;
    private final byte[] logId;
    private final Origin origin;
    private final DigitallySigned signature;
    private final long timestamp;
    private final Version version;

    public SignedCertificateTimestamp(Version version, byte[] arrby, long l, byte[] arrby2, DigitallySigned digitallySigned, Origin origin) {
        this.version = version;
        this.logId = arrby;
        this.timestamp = l;
        this.extensions = arrby2;
        this.signature = digitallySigned;
        this.origin = origin;
    }

    public static SignedCertificateTimestamp decode(InputStream object, Origin origin) throws SerializationException {
        int n = Serialization.readNumber((InputStream)object, 1);
        if (n == Version.V1.ordinal()) {
            return new SignedCertificateTimestamp(Version.V1, Serialization.readFixedBytes((InputStream)object, 32), Serialization.readLong((InputStream)object, 8), Serialization.readVariableBytes((InputStream)object, 2), DigitallySigned.decode((InputStream)object), origin);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported SCT version ");
        ((StringBuilder)object).append(n);
        throw new SerializationException(((StringBuilder)object).toString());
    }

    public static SignedCertificateTimestamp decode(byte[] arrby, Origin origin) throws SerializationException {
        return SignedCertificateTimestamp.decode(new ByteArrayInputStream(arrby), origin);
    }

    public void encodeTBS(OutputStream outputStream, CertificateEntry certificateEntry) throws SerializationException {
        Serialization.writeNumber(outputStream, this.version.ordinal(), 1);
        Serialization.writeNumber(outputStream, SignatureType.CERTIFICATE_TIMESTAMP.ordinal(), 1);
        Serialization.writeNumber(outputStream, this.timestamp, 8);
        certificateEntry.encode(outputStream);
        Serialization.writeVariableBytes(outputStream, this.extensions, 2);
    }

    public byte[] encodeTBS(CertificateEntry certificateEntry) throws SerializationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        this.encodeTBS(byteArrayOutputStream, certificateEntry);
        return byteArrayOutputStream.toByteArray();
    }

    public byte[] getExtensions() {
        return this.extensions;
    }

    public byte[] getLogID() {
        return this.logId;
    }

    public Origin getOrigin() {
        return this.origin;
    }

    public DigitallySigned getSignature() {
        return this.signature;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public Version getVersion() {
        return this.version;
    }

    public static enum Origin {
        EMBEDDED,
        TLS_EXTENSION,
        OCSP_RESPONSE;
        
    }

    public static enum SignatureType {
        CERTIFICATE_TIMESTAMP,
        TREE_HASH;
        
    }

    public static enum Version {
        V1;
        
    }

}

