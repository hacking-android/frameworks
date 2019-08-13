/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.OpenSSLX509Certificate;
import com.android.org.conscrypt.ct.Serialization;
import com.android.org.conscrypt.ct.SerializationException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Set;

public class CertificateEntry {
    private final byte[] certificate;
    private final LogEntryType entryType;
    private final byte[] issuerKeyHash;

    private CertificateEntry(LogEntryType logEntryType, byte[] arrby, byte[] arrby2) {
        if (logEntryType == LogEntryType.PRECERT_ENTRY && arrby2 == null) {
            throw new IllegalArgumentException("issuerKeyHash missing for precert entry.");
        }
        if (logEntryType == LogEntryType.X509_ENTRY && arrby2 != null) {
            throw new IllegalArgumentException("unexpected issuerKeyHash for X509 entry.");
        }
        if (arrby2 != null && arrby2.length != 32) {
            throw new IllegalArgumentException("issuerKeyHash must be 32 bytes long");
        }
        this.entryType = logEntryType;
        this.issuerKeyHash = arrby2;
        this.certificate = arrby;
    }

    public static CertificateEntry createForPrecertificate(OpenSSLX509Certificate arrby, OpenSSLX509Certificate object) throws CertificateException {
        try {
            if (arrby.getNonCriticalExtensionOIDs().contains("1.3.6.1.4.1.11129.2.4.2")) {
                arrby = arrby.withDeletedExtension("1.3.6.1.4.1.11129.2.4.2").getTBSCertificate();
                byte[] arrby2 = ((OpenSSLX509Certificate)object).getPublicKey().getEncoded();
                object = MessageDigest.getInstance("SHA-256");
                ((MessageDigest)object).update(arrby2);
                return CertificateEntry.createForPrecertificate(arrby, ((MessageDigest)object).digest());
            }
            arrby = new CertificateException("Certificate does not contain embedded signed timestamps");
            throw arrby;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException(noSuchAlgorithmException);
        }
    }

    public static CertificateEntry createForPrecertificate(byte[] arrby, byte[] arrby2) {
        return new CertificateEntry(LogEntryType.PRECERT_ENTRY, arrby, arrby2);
    }

    public static CertificateEntry createForX509Certificate(X509Certificate x509Certificate) throws CertificateEncodingException {
        return CertificateEntry.createForX509Certificate(x509Certificate.getEncoded());
    }

    public static CertificateEntry createForX509Certificate(byte[] arrby) {
        return new CertificateEntry(LogEntryType.X509_ENTRY, arrby, null);
    }

    public void encode(OutputStream outputStream) throws SerializationException {
        Serialization.writeNumber(outputStream, this.entryType.ordinal(), 2);
        if (this.entryType == LogEntryType.PRECERT_ENTRY) {
            Serialization.writeFixedBytes(outputStream, this.issuerKeyHash);
        }
        Serialization.writeVariableBytes(outputStream, this.certificate, 3);
    }

    public byte[] getCertificate() {
        return this.certificate;
    }

    public LogEntryType getEntryType() {
        return this.entryType;
    }

    public byte[] getIssuerKeyHash() {
        return this.issuerKeyHash;
    }

    public static enum LogEntryType {
        X509_ENTRY,
        PRECERT_ENTRY;
        
    }

}

