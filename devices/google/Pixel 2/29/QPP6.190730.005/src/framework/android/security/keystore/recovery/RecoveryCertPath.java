/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore.recovery;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public final class RecoveryCertPath
implements Parcelable {
    private static final String CERT_PATH_ENCODING = "PkiPath";
    public static final Parcelable.Creator<RecoveryCertPath> CREATOR = new Parcelable.Creator<RecoveryCertPath>(){

        @Override
        public RecoveryCertPath createFromParcel(Parcel parcel) {
            return new RecoveryCertPath(parcel);
        }

        public RecoveryCertPath[] newArray(int n) {
            return new RecoveryCertPath[n];
        }
    };
    private final byte[] mEncodedCertPath;

    private RecoveryCertPath(Parcel parcel) {
        this.mEncodedCertPath = parcel.createByteArray();
    }

    private RecoveryCertPath(byte[] arrby) {
        this.mEncodedCertPath = Preconditions.checkNotNull(arrby);
    }

    public static RecoveryCertPath createRecoveryCertPath(CertPath object) throws CertificateException {
        try {
            object = new RecoveryCertPath(RecoveryCertPath.encodeCertPath((CertPath)object));
            return object;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            throw new CertificateException("Failed to encode the given CertPath", certificateEncodingException);
        }
    }

    private static CertPath decodeCertPath(byte[] arrby) throws CertificateException {
        CertificateFactory certificateFactory;
        Preconditions.checkNotNull(arrby);
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
        }
        catch (CertificateException certificateException) {
            throw new RuntimeException(certificateException);
        }
        return certificateFactory.generateCertPath(new ByteArrayInputStream(arrby), CERT_PATH_ENCODING);
    }

    private static byte[] encodeCertPath(CertPath certPath) throws CertificateEncodingException {
        Preconditions.checkNotNull(certPath);
        return certPath.getEncoded(CERT_PATH_ENCODING);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CertPath getCertPath() throws CertificateException {
        return RecoveryCertPath.decodeCertPath(this.mEncodedCertPath);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mEncodedCertPath);
    }

}

