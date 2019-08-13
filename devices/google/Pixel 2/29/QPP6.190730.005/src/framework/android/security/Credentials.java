/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.bouncycastle.util.io.pem.PemObject
 *  com.android.org.bouncycastle.util.io.pem.PemObjectGenerator
 *  com.android.org.bouncycastle.util.io.pem.PemReader
 *  com.android.org.bouncycastle.util.io.pem.PemWriter
 */
package android.security;

import android.annotation.UnsupportedAppUsage;
import android.security.KeyStore;
import com.android.org.bouncycastle.util.io.pem.PemObject;
import com.android.org.bouncycastle.util.io.pem.PemObjectGenerator;
import com.android.org.bouncycastle.util.io.pem.PemReader;
import com.android.org.bouncycastle.util.io.pem.PemWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

public class Credentials {
    public static final String CA_CERTIFICATE = "CACERT_";
    public static final String EXTENSION_CER = ".cer";
    public static final String EXTENSION_CRT = ".crt";
    public static final String EXTENSION_P12 = ".p12";
    public static final String EXTENSION_PFX = ".pfx";
    public static final String EXTRA_CA_CERTIFICATES_DATA = "ca_certificates_data";
    public static final String EXTRA_CA_CERTIFICATES_NAME = "ca_certificates_name";
    public static final String EXTRA_INSTALL_AS_UID = "install_as_uid";
    public static final String EXTRA_PRIVATE_KEY = "PKEY";
    public static final String EXTRA_PUBLIC_KEY = "KEY";
    public static final String EXTRA_USER_CERTIFICATE_DATA = "user_certificate_data";
    public static final String EXTRA_USER_CERTIFICATE_NAME = "user_certificate_name";
    public static final String EXTRA_USER_PRIVATE_KEY_DATA = "user_private_key_data";
    public static final String EXTRA_USER_PRIVATE_KEY_NAME = "user_private_key_name";
    public static final String INSTALL_ACTION = "android.credentials.INSTALL";
    public static final String INSTALL_AS_USER_ACTION = "android.credentials.INSTALL_AS_USER";
    public static final String LOCKDOWN_VPN = "LOCKDOWN_VPN";
    private static final String LOGTAG = "Credentials";
    public static final String USER_CERTIFICATE = "USRCERT_";
    public static final String USER_PRIVATE_KEY = "USRPKEY_";
    public static final String USER_SECRET_KEY = "USRSKEY_";
    public static final String VPN = "VPN_";
    public static final String WIFI = "WIFI_";

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static List<X509Certificate> convertFromPem(byte[] pemReader) throws IOException, CertificateException {
        pemReader = new PemReader((Reader)new InputStreamReader((InputStream)new ByteArrayInputStream((byte[])pemReader), StandardCharsets.US_ASCII));
        try {
            PemObject pemObject;
            Object object = CertificateFactory.getInstance("X509");
            Serializable serializable = new ArrayList();
            while ((pemObject = pemReader.readPemObject()) != null) {
                if (!pemObject.getType().equals("CERTIFICATE")) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown type ");
                    ((StringBuilder)object).append(pemObject.getType());
                    serializable = new IllegalArgumentException(((StringBuilder)object).toString());
                    throw serializable;
                }
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(pemObject.getContent());
                serializable.add((X509Certificate)((X509Certificate)((CertificateFactory)object).generateCertificate(byteArrayInputStream)));
            }
            return serializable;
        }
        finally {
            pemReader.close();
        }
    }

    @UnsupportedAppUsage
    public static byte[] convertToPem(Certificate ... arrcertificate) throws IOException, CertificateEncodingException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PemWriter pemWriter = new PemWriter((Writer)new OutputStreamWriter((OutputStream)byteArrayOutputStream, StandardCharsets.US_ASCII));
        int n = arrcertificate.length;
        for (int i = 0; i < n; ++i) {
            pemWriter.writeObject((PemObjectGenerator)new PemObject("CERTIFICATE", arrcertificate[i].getEncoded()));
        }
        pemWriter.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static boolean deleteAllTypesForAlias(KeyStore keyStore, String string2) {
        return Credentials.deleteAllTypesForAlias(keyStore, string2, -1);
    }

    public static boolean deleteAllTypesForAlias(KeyStore keyStore, String string2, int n) {
        return Credentials.deleteUserKeyTypeForAlias(keyStore, string2, n) & Credentials.deleteCertificateTypesForAlias(keyStore, string2, n);
    }

    public static boolean deleteCertificateTypesForAlias(KeyStore keyStore, String string2) {
        return Credentials.deleteCertificateTypesForAlias(keyStore, string2, -1);
    }

    public static boolean deleteCertificateTypesForAlias(KeyStore keyStore, String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(USER_CERTIFICATE);
        stringBuilder.append(string2);
        boolean bl = keyStore.delete(stringBuilder.toString(), n);
        stringBuilder = new StringBuilder();
        stringBuilder.append(CA_CERTIFICATE);
        stringBuilder.append(string2);
        return bl & keyStore.delete(stringBuilder.toString(), n);
    }

    public static boolean deleteLegacyKeyForAlias(KeyStore keyStore, String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(USER_SECRET_KEY);
        stringBuilder.append(string2);
        return keyStore.delete(stringBuilder.toString(), n);
    }

    public static boolean deleteUserKeyTypeForAlias(KeyStore keyStore, String string2) {
        return Credentials.deleteUserKeyTypeForAlias(keyStore, string2, -1);
    }

    public static boolean deleteUserKeyTypeForAlias(KeyStore keyStore, String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(USER_PRIVATE_KEY);
        stringBuilder.append(string2);
        int n2 = keyStore.delete2(stringBuilder.toString(), n);
        if (n2 == 7) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(USER_SECRET_KEY);
            stringBuilder.append(string2);
            return keyStore.delete(stringBuilder.toString(), n);
        }
        boolean bl = true;
        if (n2 != 1) {
            bl = false;
        }
        return bl;
    }
}

