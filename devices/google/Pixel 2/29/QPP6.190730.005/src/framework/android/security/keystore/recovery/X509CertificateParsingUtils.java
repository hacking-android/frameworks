/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class X509CertificateParsingUtils {
    private static final String CERT_FORMAT = "X.509";

    private static byte[] decodeBase64(String string2) {
        return Base64.getDecoder().decode(string2);
    }

    public static X509Certificate decodeBase64Cert(String object) throws CertificateException {
        try {
            object = X509CertificateParsingUtils.decodeCert(X509CertificateParsingUtils.decodeBase64((String)object));
            return object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new CertificateException(illegalArgumentException);
        }
    }

    private static X509Certificate decodeCert(InputStream inputStream) throws CertificateException {
        CertificateFactory certificateFactory;
        try {
            certificateFactory = CertificateFactory.getInstance(CERT_FORMAT);
        }
        catch (CertificateException certificateException) {
            throw new RuntimeException(certificateException);
        }
        return (X509Certificate)certificateFactory.generateCertificate(inputStream);
    }

    private static X509Certificate decodeCert(byte[] arrby) throws CertificateException {
        return X509CertificateParsingUtils.decodeCert(new ByteArrayInputStream(arrby));
    }
}

