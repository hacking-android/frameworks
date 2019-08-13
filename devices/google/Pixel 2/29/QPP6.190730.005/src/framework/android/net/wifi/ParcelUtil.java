/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Parcel;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;

public class ParcelUtil {
    public static X509Certificate readCertificate(Parcel object) {
        if ((object = object.createByteArray()) == null) {
            return null;
        }
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            object = (X509Certificate)certificateFactory.generateCertificate(byteArrayInputStream);
            return object;
        }
        catch (CertificateException certificateException) {
            return null;
        }
    }

    public static X509Certificate[] readCertificates(Parcel parcel) {
        int n = parcel.readInt();
        if (n == 0) {
            return null;
        }
        X509Certificate[] arrx509Certificate = new X509Certificate[n];
        for (int i = 0; i < n; ++i) {
            arrx509Certificate[i] = ParcelUtil.readCertificate(parcel);
        }
        return arrx509Certificate;
    }

    public static PrivateKey readPrivateKey(Parcel object) {
        Object object2 = object.readString();
        if (object2 == null) {
            return null;
        }
        object = object.createByteArray();
        try {
            object2 = KeyFactory.getInstance((String)object2);
            PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec((byte[])object);
            object = ((KeyFactory)object2).generatePrivate(pKCS8EncodedKeySpec);
            return object;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
            return null;
        }
    }

    public static void writeCertificate(Parcel parcel, X509Certificate x509Certificate) {
        byte[] arrby;
        byte[] arrby2 = arrby = null;
        if (x509Certificate != null) {
            try {
                arrby2 = x509Certificate.getEncoded();
            }
            catch (CertificateEncodingException certificateEncodingException) {
                arrby2 = arrby;
            }
        }
        parcel.writeByteArray(arrby2);
    }

    public static void writeCertificates(Parcel parcel, X509Certificate[] arrx509Certificate) {
        if (arrx509Certificate != null && arrx509Certificate.length != 0) {
            parcel.writeInt(arrx509Certificate.length);
            for (int i = 0; i < arrx509Certificate.length; ++i) {
                ParcelUtil.writeCertificate(parcel, arrx509Certificate[i]);
            }
            return;
        }
        parcel.writeInt(0);
    }

    public static void writePrivateKey(Parcel parcel, PrivateKey privateKey) {
        if (privateKey == null) {
            parcel.writeString(null);
            return;
        }
        parcel.writeString(privateKey.getAlgorithm());
        parcel.writeByteArray(privateKey.getEncoded());
    }
}

