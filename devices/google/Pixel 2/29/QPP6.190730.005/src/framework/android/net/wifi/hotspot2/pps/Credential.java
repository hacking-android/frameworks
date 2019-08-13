/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.pps;

import android.net.wifi.ParcelUtil;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Credential
implements Parcelable {
    public static final Parcelable.Creator<Credential> CREATOR = new Parcelable.Creator<Credential>(){

        @Override
        public Credential createFromParcel(Parcel parcel) {
            Credential credential = new Credential();
            credential.setCreationTimeInMillis(parcel.readLong());
            credential.setExpirationTimeInMillis(parcel.readLong());
            credential.setRealm(parcel.readString());
            boolean bl = parcel.readInt() != 0;
            credential.setCheckAaaServerCertStatus(bl);
            credential.setUserCredential((UserCredential)parcel.readParcelable(null));
            credential.setCertCredential((CertificateCredential)parcel.readParcelable(null));
            credential.setSimCredential((SimCredential)parcel.readParcelable(null));
            credential.setCaCertificates(ParcelUtil.readCertificates(parcel));
            credential.setClientCertificateChain(ParcelUtil.readCertificates(parcel));
            credential.setClientPrivateKey(ParcelUtil.readPrivateKey(parcel));
            return credential;
        }

        public Credential[] newArray(int n) {
            return new Credential[n];
        }
    };
    private static final int MAX_REALM_BYTES = 253;
    private static final String TAG = "Credential";
    private X509Certificate[] mCaCertificates = null;
    private CertificateCredential mCertCredential = null;
    private boolean mCheckAaaServerCertStatus = false;
    private X509Certificate[] mClientCertificateChain = null;
    private PrivateKey mClientPrivateKey = null;
    private long mCreationTimeInMillis = Long.MIN_VALUE;
    private long mExpirationTimeInMillis = Long.MIN_VALUE;
    private String mRealm = null;
    private SimCredential mSimCredential = null;
    private UserCredential mUserCredential = null;

    public Credential() {
    }

    public Credential(Credential credential) {
        if (credential != null) {
            this.mCreationTimeInMillis = credential.mCreationTimeInMillis;
            this.mExpirationTimeInMillis = credential.mExpirationTimeInMillis;
            this.mRealm = credential.mRealm;
            this.mCheckAaaServerCertStatus = credential.mCheckAaaServerCertStatus;
            Object object = credential.mUserCredential;
            if (object != null) {
                this.mUserCredential = new UserCredential((UserCredential)object);
            }
            if ((object = credential.mCertCredential) != null) {
                this.mCertCredential = new CertificateCredential((CertificateCredential)object);
            }
            if ((object = credential.mSimCredential) != null) {
                this.mSimCredential = new SimCredential((SimCredential)object);
            }
            if ((object = credential.mClientCertificateChain) != null) {
                this.mClientCertificateChain = Arrays.copyOf(object, ((X509Certificate[])object).length);
            }
            if ((object = credential.mCaCertificates) != null) {
                this.mCaCertificates = Arrays.copyOf(object, ((X509Certificate[])object).length);
            }
            this.mClientPrivateKey = credential.mClientPrivateKey;
        }
    }

    private static boolean isPrivateKeyEquals(PrivateKey privateKey, PrivateKey privateKey2) {
        boolean bl = true;
        if (privateKey == null && privateKey2 == null) {
            return true;
        }
        if (privateKey != null && privateKey2 != null) {
            if (!TextUtils.equals(privateKey.getAlgorithm(), privateKey2.getAlgorithm()) || !Arrays.equals(privateKey.getEncoded(), privateKey2.getEncoded())) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public static boolean isX509CertificateEquals(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        if (x509Certificate == null && x509Certificate2 == null) {
            return true;
        }
        if (x509Certificate != null && x509Certificate2 != null) {
            boolean bl;
            boolean bl2 = false;
            try {
                bl = Arrays.equals(x509Certificate.getEncoded(), x509Certificate2.getEncoded());
            }
            catch (CertificateEncodingException certificateEncodingException) {
                bl = bl2;
            }
            return bl;
        }
        return false;
    }

    private static boolean isX509CertificatesEquals(X509Certificate[] arrx509Certificate, X509Certificate[] arrx509Certificate2) {
        if (arrx509Certificate == null && arrx509Certificate2 == null) {
            return true;
        }
        if (arrx509Certificate != null && arrx509Certificate2 != null) {
            if (arrx509Certificate.length != arrx509Certificate2.length) {
                return false;
            }
            for (int i = 0; i < arrx509Certificate.length; ++i) {
                if (Credential.isX509CertificateEquals(arrx509Certificate[i], arrx509Certificate2[i])) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean verifyCertCredential(boolean bl) {
        Object object = this.mCertCredential;
        if (object == null) {
            Log.d(TAG, "Missing certificate credential");
            return false;
        }
        if (this.mUserCredential == null && this.mSimCredential == null) {
            if (!((CertificateCredential)object).validate()) {
                return false;
            }
            if (bl && this.mCaCertificates == null) {
                Log.d(TAG, "Missing CA Certificate for certificate credential");
                return false;
            }
            if (this.mClientPrivateKey == null) {
                Log.d(TAG, "Missing client private key for certificate credential");
                return false;
            }
            try {
                if (!Credential.verifySha256Fingerprint(this.mClientCertificateChain, this.mCertCredential.getCertSha256Fingerprint())) {
                    Log.d(TAG, "SHA-256 fingerprint mismatch");
                    return false;
                }
                return true;
            }
            catch (NoSuchAlgorithmException | CertificateEncodingException generalSecurityException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to verify SHA-256 fingerprint: ");
                ((StringBuilder)object).append(generalSecurityException.getMessage());
                Log.d(TAG, ((StringBuilder)object).toString());
                return false;
            }
        }
        Log.d(TAG, "Contained more than one type of credential");
        return false;
    }

    private static boolean verifySha256Fingerprint(X509Certificate[] arrx509Certificate, byte[] arrby) throws NoSuchAlgorithmException, CertificateEncodingException {
        if (arrx509Certificate == null) {
            return false;
        }
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        for (X509Certificate x509Certificate : arrx509Certificate) {
            messageDigest.reset();
            if (!Arrays.equals(arrby, messageDigest.digest(x509Certificate.getEncoded()))) continue;
            return true;
        }
        return false;
    }

    private boolean verifySimCredential() {
        SimCredential simCredential = this.mSimCredential;
        if (simCredential == null) {
            Log.d(TAG, "Missing SIM credential");
            return false;
        }
        if (this.mUserCredential == null && this.mCertCredential == null) {
            return simCredential.validate();
        }
        Log.d(TAG, "Contained more than one type of credential");
        return false;
    }

    private boolean verifyUserCredential(boolean bl) {
        UserCredential userCredential = this.mUserCredential;
        if (userCredential == null) {
            Log.d(TAG, "Missing user credential");
            return false;
        }
        if (this.mCertCredential == null && this.mSimCredential == null) {
            if (!userCredential.validate()) {
                return false;
            }
            if (bl && this.mCaCertificates == null) {
                Log.d(TAG, "Missing CA Certificate for user credential");
                return false;
            }
            return true;
        }
        Log.d(TAG, "Contained more than one type of credential");
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        Parcelable parcelable;
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof Credential)) {
            return false;
        }
        object = (Credential)object;
        if (!(TextUtils.equals(this.mRealm, ((Credential)object).mRealm) && this.mCreationTimeInMillis == ((Credential)object).mCreationTimeInMillis && this.mExpirationTimeInMillis == ((Credential)object).mExpirationTimeInMillis && this.mCheckAaaServerCertStatus == ((Credential)object).mCheckAaaServerCertStatus && ((parcelable = this.mUserCredential) == null ? ((Credential)object).mUserCredential == null : ((UserCredential)parcelable).equals(((Credential)object).mUserCredential)) && ((parcelable = this.mCertCredential) == null ? ((Credential)object).mCertCredential == null : ((CertificateCredential)parcelable).equals(((Credential)object).mCertCredential)) && ((parcelable = this.mSimCredential) == null ? ((Credential)object).mSimCredential == null : ((SimCredential)parcelable).equals(((Credential)object).mSimCredential)) && Credential.isX509CertificatesEquals(this.mCaCertificates, ((Credential)object).mCaCertificates) && Credential.isX509CertificatesEquals(this.mClientCertificateChain, ((Credential)object).mClientCertificateChain) && Credential.isPrivateKeyEquals(this.mClientPrivateKey, ((Credential)object).mClientPrivateKey))) {
            bl = false;
        }
        return bl;
    }

    public X509Certificate getCaCertificate() {
        Object object = this.mCaCertificates;
        object = object != null && ((X509Certificate[])object).length <= 1 ? object[0] : null;
        return object;
    }

    public X509Certificate[] getCaCertificates() {
        return this.mCaCertificates;
    }

    public CertificateCredential getCertCredential() {
        return this.mCertCredential;
    }

    public boolean getCheckAaaServerCertStatus() {
        return this.mCheckAaaServerCertStatus;
    }

    public X509Certificate[] getClientCertificateChain() {
        return this.mClientCertificateChain;
    }

    public PrivateKey getClientPrivateKey() {
        return this.mClientPrivateKey;
    }

    public long getCreationTimeInMillis() {
        return this.mCreationTimeInMillis;
    }

    public long getExpirationTimeInMillis() {
        return this.mExpirationTimeInMillis;
    }

    public String getRealm() {
        return this.mRealm;
    }

    public SimCredential getSimCredential() {
        return this.mSimCredential;
    }

    public UserCredential getUserCredential() {
        return this.mUserCredential;
    }

    public int hashCode() {
        return Objects.hash(this.mCreationTimeInMillis, this.mExpirationTimeInMillis, this.mRealm, this.mCheckAaaServerCertStatus, this.mUserCredential, this.mCertCredential, this.mSimCredential, this.mClientPrivateKey, Arrays.hashCode(this.mCaCertificates), Arrays.hashCode(this.mClientCertificateChain));
    }

    public void setCaCertificate(X509Certificate x509Certificate) {
        this.mCaCertificates = null;
        if (x509Certificate != null) {
            this.mCaCertificates = new X509Certificate[]{x509Certificate};
        }
    }

    public void setCaCertificates(X509Certificate[] arrx509Certificate) {
        this.mCaCertificates = arrx509Certificate;
    }

    public void setCertCredential(CertificateCredential certificateCredential) {
        this.mCertCredential = certificateCredential;
    }

    public void setCheckAaaServerCertStatus(boolean bl) {
        this.mCheckAaaServerCertStatus = bl;
    }

    public void setClientCertificateChain(X509Certificate[] arrx509Certificate) {
        this.mClientCertificateChain = arrx509Certificate;
    }

    public void setClientPrivateKey(PrivateKey privateKey) {
        this.mClientPrivateKey = privateKey;
    }

    public void setCreationTimeInMillis(long l) {
        this.mCreationTimeInMillis = l;
    }

    public void setExpirationTimeInMillis(long l) {
        this.mExpirationTimeInMillis = l;
    }

    public void setRealm(String string2) {
        this.mRealm = string2;
    }

    public void setSimCredential(SimCredential simCredential) {
        this.mSimCredential = simCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.mUserCredential = userCredential;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Realm: ");
        stringBuilder.append(this.mRealm);
        stringBuilder.append("\n");
        stringBuilder.append("CreationTime: ");
        long l = this.mCreationTimeInMillis;
        String string2 = "Not specified";
        Object object = l != Long.MIN_VALUE ? new Date(l) : "Not specified";
        stringBuilder.append(object);
        stringBuilder.append("\n");
        stringBuilder.append("ExpirationTime: ");
        l = this.mExpirationTimeInMillis;
        object = l != Long.MIN_VALUE ? new Date(l) : string2;
        stringBuilder.append(object);
        stringBuilder.append("\n");
        stringBuilder.append("CheckAAAServerStatus: ");
        stringBuilder.append(this.mCheckAaaServerCertStatus);
        stringBuilder.append("\n");
        if (this.mUserCredential != null) {
            stringBuilder.append("UserCredential Begin ---\n");
            stringBuilder.append(this.mUserCredential);
            stringBuilder.append("UserCredential End ---\n");
        }
        if (this.mCertCredential != null) {
            stringBuilder.append("CertificateCredential Begin ---\n");
            stringBuilder.append(this.mCertCredential);
            stringBuilder.append("CertificateCredential End ---\n");
        }
        if (this.mSimCredential != null) {
            stringBuilder.append("SIMCredential Begin ---\n");
            stringBuilder.append(this.mSimCredential);
            stringBuilder.append("SIMCredential End ---\n");
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean validate(boolean bl) {
        if (TextUtils.isEmpty(this.mRealm)) {
            Log.d(TAG, "Missing realm");
            return false;
        }
        if (this.mRealm.getBytes(StandardCharsets.UTF_8).length > 253) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("realm exceeding maximum length: ");
            stringBuilder.append(this.mRealm.getBytes(StandardCharsets.UTF_8).length);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (this.mUserCredential != null) {
            if (this.verifyUserCredential(bl)) return true;
            return false;
        }
        if (this.mCertCredential != null) {
            if (this.verifyCertCredential(bl)) return true;
            return false;
        }
        if (this.mSimCredential != null) {
            if (this.verifySimCredential()) return true;
            return false;
        }
        Log.d(TAG, "Missing required credential");
        return false;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mCreationTimeInMillis);
        parcel.writeLong(this.mExpirationTimeInMillis);
        parcel.writeString(this.mRealm);
        parcel.writeInt((int)this.mCheckAaaServerCertStatus);
        parcel.writeParcelable(this.mUserCredential, n);
        parcel.writeParcelable(this.mCertCredential, n);
        parcel.writeParcelable(this.mSimCredential, n);
        ParcelUtil.writeCertificates(parcel, this.mCaCertificates);
        ParcelUtil.writeCertificates(parcel, this.mClientCertificateChain);
        ParcelUtil.writePrivateKey(parcel, this.mClientPrivateKey);
    }

    public static final class CertificateCredential
    implements Parcelable {
        private static final int CERT_SHA256_FINGER_PRINT_LENGTH = 32;
        public static final String CERT_TYPE_X509V3 = "x509v3";
        public static final Parcelable.Creator<CertificateCredential> CREATOR = new Parcelable.Creator<CertificateCredential>(){

            @Override
            public CertificateCredential createFromParcel(Parcel parcel) {
                CertificateCredential certificateCredential = new CertificateCredential();
                certificateCredential.setCertType(parcel.readString());
                certificateCredential.setCertSha256Fingerprint(parcel.createByteArray());
                return certificateCredential;
            }

            public CertificateCredential[] newArray(int n) {
                return new CertificateCredential[n];
            }
        };
        private byte[] mCertSha256Fingerprint = null;
        private String mCertType = null;

        public CertificateCredential() {
        }

        public CertificateCredential(CertificateCredential arrby) {
            if (arrby != null) {
                this.mCertType = arrby.mCertType;
                arrby = arrby.mCertSha256Fingerprint;
                if (arrby != null) {
                    this.mCertSha256Fingerprint = Arrays.copyOf(arrby, arrby.length);
                }
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof CertificateCredential)) {
                return false;
            }
            object = (CertificateCredential)object;
            if (!TextUtils.equals(this.mCertType, ((CertificateCredential)object).mCertType) || !Arrays.equals(this.mCertSha256Fingerprint, ((CertificateCredential)object).mCertSha256Fingerprint)) {
                bl = false;
            }
            return bl;
        }

        public byte[] getCertSha256Fingerprint() {
            return this.mCertSha256Fingerprint;
        }

        public String getCertType() {
            return this.mCertType;
        }

        public int hashCode() {
            return Objects.hash(this.mCertType, Arrays.hashCode(this.mCertSha256Fingerprint));
        }

        public void setCertSha256Fingerprint(byte[] arrby) {
            this.mCertSha256Fingerprint = arrby;
        }

        public void setCertType(String string2) {
            this.mCertType = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CertificateType: ");
            stringBuilder.append(this.mCertType);
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }

        public boolean validate() {
            if (!TextUtils.equals(CERT_TYPE_X509V3, this.mCertType)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported certificate type: ");
                stringBuilder.append(this.mCertType);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            byte[] arrby = this.mCertSha256Fingerprint;
            if (arrby != null && arrby.length == 32) {
                return true;
            }
            Log.d(Credential.TAG, "Invalid SHA-256 fingerprint");
            return false;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mCertType);
            parcel.writeByteArray(this.mCertSha256Fingerprint);
        }

    }

    public static final class SimCredential
    implements Parcelable {
        public static final Parcelable.Creator<SimCredential> CREATOR = new Parcelable.Creator<SimCredential>(){

            @Override
            public SimCredential createFromParcel(Parcel parcel) {
                SimCredential simCredential = new SimCredential();
                simCredential.setImsi(parcel.readString());
                simCredential.setEapType(parcel.readInt());
                return simCredential;
            }

            public SimCredential[] newArray(int n) {
                return new SimCredential[n];
            }
        };
        private static final int MAX_IMSI_LENGTH = 15;
        private int mEapType = Integer.MIN_VALUE;
        private String mImsi = null;

        public SimCredential() {
        }

        public SimCredential(SimCredential simCredential) {
            if (simCredential != null) {
                this.mImsi = simCredential.mImsi;
                this.mEapType = simCredential.mEapType;
            }
        }

        private boolean verifyImsi() {
            char c;
            if (TextUtils.isEmpty(this.mImsi)) {
                Log.d(Credential.TAG, "Missing IMSI");
                return false;
            }
            if (this.mImsi.length() > 15) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IMSI exceeding maximum length: ");
                stringBuilder.append(this.mImsi.length());
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            char c2 = '\u0000';
            int n = 0;
            do {
                c = c2;
                if (n >= this.mImsi.length()) break;
                c = c2 = this.mImsi.charAt(n);
                if (c2 < '0') break;
                if (c2 > '9') {
                    c = c2;
                    break;
                }
                ++n;
            } while (true);
            if (n == this.mImsi.length()) {
                return true;
            }
            return n == this.mImsi.length() - 1 && c == '*';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof SimCredential)) {
                return false;
            }
            object = (SimCredential)object;
            if (!TextUtils.equals(this.mImsi, ((SimCredential)object).mImsi) || this.mEapType != ((SimCredential)object).mEapType) {
                bl = false;
            }
            return bl;
        }

        public int getEapType() {
            return this.mEapType;
        }

        public String getImsi() {
            return this.mImsi;
        }

        public int hashCode() {
            return Objects.hash(this.mImsi, this.mEapType);
        }

        public void setEapType(int n) {
            this.mEapType = n;
        }

        public void setImsi(String string2) {
            this.mImsi = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IMSI: ");
            stringBuilder.append(this.mImsi);
            stringBuilder.append("\n");
            stringBuilder.append("EAPType: ");
            stringBuilder.append(this.mEapType);
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }

        public boolean validate() {
            if (!this.verifyImsi()) {
                return false;
            }
            int n = this.mEapType;
            if (n != 18 && n != 23 && n != 50) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid EAP Type for SIM credential: ");
                stringBuilder.append(this.mEapType);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            return true;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mImsi);
            parcel.writeInt(this.mEapType);
        }

    }

    public static final class UserCredential
    implements Parcelable {
        public static final String AUTH_METHOD_MSCHAP = "MS-CHAP";
        public static final String AUTH_METHOD_MSCHAPV2 = "MS-CHAP-V2";
        public static final String AUTH_METHOD_PAP = "PAP";
        public static final Parcelable.Creator<UserCredential> CREATOR;
        private static final int MAX_PASSWORD_BYTES = 255;
        private static final int MAX_USERNAME_BYTES = 63;
        private static final Set<String> SUPPORTED_AUTH;
        private boolean mAbleToShare = false;
        private int mEapType = Integer.MIN_VALUE;
        private boolean mMachineManaged = false;
        private String mNonEapInnerMethod = null;
        private String mPassword = null;
        private String mSoftTokenApp = null;
        private String mUsername = null;

        static {
            SUPPORTED_AUTH = new HashSet<String>(Arrays.asList(AUTH_METHOD_PAP, AUTH_METHOD_MSCHAP, AUTH_METHOD_MSCHAPV2));
            CREATOR = new Parcelable.Creator<UserCredential>(){

                @Override
                public UserCredential createFromParcel(Parcel parcel) {
                    UserCredential userCredential = new UserCredential();
                    userCredential.setUsername(parcel.readString());
                    userCredential.setPassword(parcel.readString());
                    int n = parcel.readInt();
                    boolean bl = true;
                    boolean bl2 = n != 0;
                    userCredential.setMachineManaged(bl2);
                    userCredential.setSoftTokenApp(parcel.readString());
                    bl2 = parcel.readInt() != 0 ? bl : false;
                    userCredential.setAbleToShare(bl2);
                    userCredential.setEapType(parcel.readInt());
                    userCredential.setNonEapInnerMethod(parcel.readString());
                    return userCredential;
                }

                public UserCredential[] newArray(int n) {
                    return new UserCredential[n];
                }
            };
        }

        public UserCredential() {
        }

        public UserCredential(UserCredential userCredential) {
            if (userCredential != null) {
                this.mUsername = userCredential.mUsername;
                this.mPassword = userCredential.mPassword;
                this.mMachineManaged = userCredential.mMachineManaged;
                this.mSoftTokenApp = userCredential.mSoftTokenApp;
                this.mAbleToShare = userCredential.mAbleToShare;
                this.mEapType = userCredential.mEapType;
                this.mNonEapInnerMethod = userCredential.mNonEapInnerMethod;
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof UserCredential)) {
                return false;
            }
            object = (UserCredential)object;
            if (!(TextUtils.equals(this.mUsername, ((UserCredential)object).mUsername) && TextUtils.equals(this.mPassword, ((UserCredential)object).mPassword) && this.mMachineManaged == ((UserCredential)object).mMachineManaged && TextUtils.equals(this.mSoftTokenApp, ((UserCredential)object).mSoftTokenApp) && this.mAbleToShare == ((UserCredential)object).mAbleToShare && this.mEapType == ((UserCredential)object).mEapType && TextUtils.equals(this.mNonEapInnerMethod, ((UserCredential)object).mNonEapInnerMethod))) {
                bl = false;
            }
            return bl;
        }

        public boolean getAbleToShare() {
            return this.mAbleToShare;
        }

        public int getEapType() {
            return this.mEapType;
        }

        public boolean getMachineManaged() {
            return this.mMachineManaged;
        }

        public String getNonEapInnerMethod() {
            return this.mNonEapInnerMethod;
        }

        public String getPassword() {
            return this.mPassword;
        }

        public String getSoftTokenApp() {
            return this.mSoftTokenApp;
        }

        public String getUsername() {
            return this.mUsername;
        }

        public int hashCode() {
            return Objects.hash(this.mUsername, this.mPassword, this.mMachineManaged, this.mSoftTokenApp, this.mAbleToShare, this.mEapType, this.mNonEapInnerMethod);
        }

        public void setAbleToShare(boolean bl) {
            this.mAbleToShare = bl;
        }

        public void setEapType(int n) {
            this.mEapType = n;
        }

        public void setMachineManaged(boolean bl) {
            this.mMachineManaged = bl;
        }

        public void setNonEapInnerMethod(String string2) {
            this.mNonEapInnerMethod = string2;
        }

        public void setPassword(String string2) {
            this.mPassword = string2;
        }

        public void setSoftTokenApp(String string2) {
            this.mSoftTokenApp = string2;
        }

        public void setUsername(String string2) {
            this.mUsername = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Username: ");
            stringBuilder.append(this.mUsername);
            stringBuilder.append("\n");
            stringBuilder.append("MachineManaged: ");
            stringBuilder.append(this.mMachineManaged);
            stringBuilder.append("\n");
            stringBuilder.append("SoftTokenApp: ");
            stringBuilder.append(this.mSoftTokenApp);
            stringBuilder.append("\n");
            stringBuilder.append("AbleToShare: ");
            stringBuilder.append(this.mAbleToShare);
            stringBuilder.append("\n");
            stringBuilder.append("EAPType: ");
            stringBuilder.append(this.mEapType);
            stringBuilder.append("\n");
            stringBuilder.append("AuthMethod: ");
            stringBuilder.append(this.mNonEapInnerMethod);
            stringBuilder.append("\n");
            return stringBuilder.toString();
        }

        public boolean validate() {
            if (TextUtils.isEmpty(this.mUsername)) {
                Log.d(Credential.TAG, "Missing username");
                return false;
            }
            if (this.mUsername.getBytes(StandardCharsets.UTF_8).length > 63) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("username exceeding maximum length: ");
                stringBuilder.append(this.mUsername.getBytes(StandardCharsets.UTF_8).length);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            if (TextUtils.isEmpty(this.mPassword)) {
                Log.d(Credential.TAG, "Missing password");
                return false;
            }
            if (this.mPassword.getBytes(StandardCharsets.UTF_8).length > 255) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("password exceeding maximum length: ");
                stringBuilder.append(this.mPassword.getBytes(StandardCharsets.UTF_8).length);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            if (this.mEapType != 21) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid EAP Type for user credential: ");
                stringBuilder.append(this.mEapType);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            if (!SUPPORTED_AUTH.contains(this.mNonEapInnerMethod)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid non-EAP inner method for EAP-TTLS: ");
                stringBuilder.append(this.mNonEapInnerMethod);
                Log.d(Credential.TAG, stringBuilder.toString());
                return false;
            }
            return true;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mUsername);
            parcel.writeString(this.mPassword);
            parcel.writeInt((int)this.mMachineManaged);
            parcel.writeString(this.mSoftTokenApp);
            parcel.writeInt((int)this.mAbleToShare);
            parcel.writeInt(this.mEapType);
            parcel.writeString(this.mNonEapInnerMethod);
        }

    }

}

