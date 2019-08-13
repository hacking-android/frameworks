/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.ArrayUtils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class Signature
implements Parcelable {
    public static final Parcelable.Creator<Signature> CREATOR = new Parcelable.Creator<Signature>(){

        @Override
        public Signature createFromParcel(Parcel parcel) {
            return new Signature(parcel);
        }

        public Signature[] newArray(int n) {
            return new Signature[n];
        }
    };
    private Certificate[] mCertificateChain;
    private int mFlags;
    private int mHashCode;
    private boolean mHaveHashCode;
    private final byte[] mSignature;
    private SoftReference<String> mStringRef;

    private Signature(Parcel parcel) {
        this.mSignature = parcel.createByteArray();
    }

    public Signature(String arrby) {
        byte[] arrby2 = arrby.getBytes();
        int n = arrby2.length;
        if (n % 2 == 0) {
            arrby = new byte[n / 2];
            int n2 = 0;
            int n3 = 0;
            while (n3 < n) {
                int n4 = n3 + 1;
                arrby[n2] = (byte)(Signature.parseHexDigit(arrby2[n3]) << 4 | Signature.parseHexDigit(arrby2[n4]));
                n3 = n4 + 1;
                ++n2;
            }
            this.mSignature = arrby;
            return;
        }
        arrby = new StringBuilder();
        arrby.append("text size ");
        arrby.append(n);
        arrby.append(" is not even");
        throw new IllegalArgumentException(arrby.toString());
    }

    public Signature(byte[] arrby) {
        this.mSignature = (byte[])arrby.clone();
        this.mCertificateChain = null;
    }

    public Signature(Certificate[] arrcertificate) throws CertificateEncodingException {
        this.mSignature = arrcertificate[0].getEncoded();
        if (arrcertificate.length > 1) {
            this.mCertificateChain = Arrays.copyOfRange(arrcertificate, 1, arrcertificate.length);
        }
    }

    public static boolean areEffectiveMatch(Signature signature, Signature signature2) throws CertificateException {
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        return Signature.bounce(certificateFactory, signature).equals(Signature.bounce(certificateFactory, signature2));
    }

    public static boolean areEffectiveMatch(Signature[] arrsignature, Signature[] arrsignature2) throws CertificateException {
        int n;
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        Signature[] arrsignature3 = new Signature[arrsignature.length];
        for (n = 0; n < arrsignature.length; ++n) {
            arrsignature3[n] = Signature.bounce(certificateFactory, arrsignature[n]);
        }
        arrsignature = new Signature[arrsignature2.length];
        for (n = 0; n < arrsignature2.length; ++n) {
            arrsignature[n] = Signature.bounce(certificateFactory, arrsignature2[n]);
        }
        return Signature.areExactMatch(arrsignature3, arrsignature);
    }

    public static boolean areExactMatch(Signature[] arrsignature, Signature[] arrsignature2) {
        boolean bl = arrsignature.length == arrsignature2.length && ArrayUtils.containsAll(arrsignature, arrsignature2) && ArrayUtils.containsAll(arrsignature2, arrsignature);
        return bl;
    }

    public static Signature bounce(CertificateFactory object, Signature signature) throws CertificateException {
        Signature signature2 = new Signature(((X509Certificate)((CertificateFactory)object).generateCertificate(new ByteArrayInputStream(signature.mSignature))).getEncoded());
        if (Math.abs(signature2.mSignature.length - signature.mSignature.length) <= 2) {
            return signature2;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bounced cert length looks fishy; before ");
        ((StringBuilder)object).append(signature.mSignature.length);
        ((StringBuilder)object).append(", after ");
        ((StringBuilder)object).append(signature2.mSignature.length);
        throw new CertificateException(((StringBuilder)object).toString());
    }

    private static final int parseHexDigit(int n) {
        if (48 <= n && n <= 57) {
            return n - 48;
        }
        if (97 <= n && n <= 102) {
            return n - 97 + 10;
        }
        if (65 <= n && n <= 70) {
            return n - 65 + 10;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid character ");
        stringBuilder.append(n);
        stringBuilder.append(" in hex string");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null) {
            block5 : {
                block4 : {
                    object = (Signature)object;
                    if (this == object) break block4;
                    try {
                        boolean bl2 = Arrays.equals(this.mSignature, ((Signature)object).mSignature);
                        if (!bl2) break block5;
                    }
                    catch (ClassCastException classCastException) {}
                }
                bl = true;
            }
            return bl;
            {
            }
        }
        return false;
    }

    public Signature[] getChainSignatures() throws CertificateEncodingException {
        Certificate[] arrcertificate = this.mCertificateChain;
        int n = 0;
        if (arrcertificate == null) {
            return new Signature[]{this};
        }
        Signature[] arrsignature = new Signature[arrcertificate.length + 1];
        arrsignature[0] = this;
        int n2 = 1;
        int n3 = arrcertificate.length;
        while (n < n3) {
            arrsignature[n2] = new Signature(arrcertificate[n].getEncoded());
            ++n;
            ++n2;
        }
        return arrsignature;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @UnsupportedAppUsage
    public PublicKey getPublicKey() throws CertificateException {
        return CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(this.mSignature)).getPublicKey();
    }

    public int hashCode() {
        if (this.mHaveHashCode) {
            return this.mHashCode;
        }
        this.mHashCode = Arrays.hashCode(this.mSignature);
        this.mHaveHashCode = true;
        return this.mHashCode;
    }

    public void setFlags(int n) {
        this.mFlags = n;
    }

    public byte[] toByteArray() {
        byte[] arrby = this.mSignature;
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
        return arrby2;
    }

    public char[] toChars() {
        return this.toChars(null, null);
    }

    public char[] toChars(char[] arrc, int[] arrn) {
        byte[] arrby = this.mSignature;
        int n = arrby.length;
        int n2 = n * 2;
        if (arrc == null || n2 > arrc.length) {
            arrc = new char[n2];
        }
        for (n2 = 0; n2 < n; ++n2) {
            byte by = arrby[n2];
            int n3 = by >> 4 & 15;
            n3 = n3 >= 10 ? n3 + 97 - 10 : (n3 += 48);
            arrc[n2 * 2] = (char)n3;
            n3 = by & 15;
            n3 = n3 >= 10 ? n3 + 97 - 10 : (n3 += 48);
            arrc[n2 * 2 + 1] = (char)n3;
        }
        if (arrn != null) {
            arrn[0] = n;
        }
        return arrc;
    }

    public String toCharsString() {
        SoftReference<String> softReference = this.mStringRef;
        softReference = softReference == null ? null : softReference.get();
        if (softReference != null) {
            return softReference;
        }
        softReference = new String(this.toChars());
        this.mStringRef = new SoftReference<Object>(softReference);
        return softReference;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mSignature);
    }

}

