/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.pps;

import android.net.wifi.ParcelUtil;
import android.net.wifi.hotspot2.pps.Credential;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Objects;

public final class UpdateParameter
implements Parcelable {
    private static final int CERTIFICATE_SHA256_BYTES = 32;
    public static final Parcelable.Creator<UpdateParameter> CREATOR = new Parcelable.Creator<UpdateParameter>(){

        @Override
        public UpdateParameter createFromParcel(Parcel parcel) {
            UpdateParameter updateParameter = new UpdateParameter();
            updateParameter.setUpdateIntervalInMinutes(parcel.readLong());
            updateParameter.setUpdateMethod(parcel.readString());
            updateParameter.setRestriction(parcel.readString());
            updateParameter.setServerUri(parcel.readString());
            updateParameter.setUsername(parcel.readString());
            updateParameter.setBase64EncodedPassword(parcel.readString());
            updateParameter.setTrustRootCertUrl(parcel.readString());
            updateParameter.setTrustRootCertSha256Fingerprint(parcel.createByteArray());
            updateParameter.setCaCertificate(ParcelUtil.readCertificate(parcel));
            return updateParameter;
        }

        public UpdateParameter[] newArray(int n) {
            return new UpdateParameter[n];
        }
    };
    private static final int MAX_PASSWORD_BYTES = 255;
    private static final int MAX_URI_BYTES = 1023;
    private static final int MAX_URL_BYTES = 1023;
    private static final int MAX_USERNAME_BYTES = 63;
    private static final String TAG = "UpdateParameter";
    public static final long UPDATE_CHECK_INTERVAL_NEVER = 0xFFFFFFFFL;
    public static final String UPDATE_METHOD_OMADM = "OMA-DM-ClientInitiated";
    public static final String UPDATE_METHOD_SSP = "SSP-ClientInitiated";
    public static final String UPDATE_RESTRICTION_HOMESP = "HomeSP";
    public static final String UPDATE_RESTRICTION_ROAMING_PARTNER = "RoamingPartner";
    public static final String UPDATE_RESTRICTION_UNRESTRICTED = "Unrestricted";
    private String mBase64EncodedPassword = null;
    private X509Certificate mCaCertificate;
    private String mRestriction = null;
    private String mServerUri = null;
    private byte[] mTrustRootCertSha256Fingerprint = null;
    private String mTrustRootCertUrl = null;
    private long mUpdateIntervalInMinutes = Long.MIN_VALUE;
    private String mUpdateMethod = null;
    private String mUsername = null;

    public UpdateParameter() {
    }

    public UpdateParameter(UpdateParameter updateParameter) {
        if (updateParameter == null) {
            return;
        }
        this.mUpdateIntervalInMinutes = updateParameter.mUpdateIntervalInMinutes;
        this.mUpdateMethod = updateParameter.mUpdateMethod;
        this.mRestriction = updateParameter.mRestriction;
        this.mServerUri = updateParameter.mServerUri;
        this.mUsername = updateParameter.mUsername;
        this.mBase64EncodedPassword = updateParameter.mBase64EncodedPassword;
        this.mTrustRootCertUrl = updateParameter.mTrustRootCertUrl;
        byte[] arrby = updateParameter.mTrustRootCertSha256Fingerprint;
        if (arrby != null) {
            this.mTrustRootCertSha256Fingerprint = Arrays.copyOf(arrby, arrby.length);
        }
        this.mCaCertificate = updateParameter.mCaCertificate;
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
        if (!(object instanceof UpdateParameter)) {
            return false;
        }
        object = (UpdateParameter)object;
        if (!(this.mUpdateIntervalInMinutes == ((UpdateParameter)object).mUpdateIntervalInMinutes && TextUtils.equals(this.mUpdateMethod, ((UpdateParameter)object).mUpdateMethod) && TextUtils.equals(this.mRestriction, ((UpdateParameter)object).mRestriction) && TextUtils.equals(this.mServerUri, ((UpdateParameter)object).mServerUri) && TextUtils.equals(this.mUsername, ((UpdateParameter)object).mUsername) && TextUtils.equals(this.mBase64EncodedPassword, ((UpdateParameter)object).mBase64EncodedPassword) && TextUtils.equals(this.mTrustRootCertUrl, ((UpdateParameter)object).mTrustRootCertUrl) && Arrays.equals(this.mTrustRootCertSha256Fingerprint, ((UpdateParameter)object).mTrustRootCertSha256Fingerprint) && Credential.isX509CertificateEquals(this.mCaCertificate, ((UpdateParameter)object).mCaCertificate))) {
            bl = false;
        }
        return bl;
    }

    public String getBase64EncodedPassword() {
        return this.mBase64EncodedPassword;
    }

    public X509Certificate getCaCertificate() {
        return this.mCaCertificate;
    }

    public String getRestriction() {
        return this.mRestriction;
    }

    public String getServerUri() {
        return this.mServerUri;
    }

    public byte[] getTrustRootCertSha256Fingerprint() {
        return this.mTrustRootCertSha256Fingerprint;
    }

    public String getTrustRootCertUrl() {
        return this.mTrustRootCertUrl;
    }

    public long getUpdateIntervalInMinutes() {
        return this.mUpdateIntervalInMinutes;
    }

    public String getUpdateMethod() {
        return this.mUpdateMethod;
    }

    public String getUsername() {
        return this.mUsername;
    }

    public int hashCode() {
        return Objects.hash(this.mUpdateIntervalInMinutes, this.mUpdateMethod, this.mRestriction, this.mServerUri, this.mUsername, this.mBase64EncodedPassword, this.mTrustRootCertUrl, Arrays.hashCode(this.mTrustRootCertSha256Fingerprint), this.mCaCertificate);
    }

    public void setBase64EncodedPassword(String string2) {
        this.mBase64EncodedPassword = string2;
    }

    public void setCaCertificate(X509Certificate x509Certificate) {
        this.mCaCertificate = x509Certificate;
    }

    public void setRestriction(String string2) {
        this.mRestriction = string2;
    }

    public void setServerUri(String string2) {
        this.mServerUri = string2;
    }

    public void setTrustRootCertSha256Fingerprint(byte[] arrby) {
        this.mTrustRootCertSha256Fingerprint = arrby;
    }

    public void setTrustRootCertUrl(String string2) {
        this.mTrustRootCertUrl = string2;
    }

    public void setUpdateIntervalInMinutes(long l) {
        this.mUpdateIntervalInMinutes = l;
    }

    public void setUpdateMethod(String string2) {
        this.mUpdateMethod = string2;
    }

    public void setUsername(String string2) {
        this.mUsername = string2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UpdateInterval: ");
        stringBuilder.append(this.mUpdateIntervalInMinutes);
        stringBuilder.append("\n");
        stringBuilder.append("UpdateMethod: ");
        stringBuilder.append(this.mUpdateMethod);
        stringBuilder.append("\n");
        stringBuilder.append("Restriction: ");
        stringBuilder.append(this.mRestriction);
        stringBuilder.append("\n");
        stringBuilder.append("ServerURI: ");
        stringBuilder.append(this.mServerUri);
        stringBuilder.append("\n");
        stringBuilder.append("Username: ");
        stringBuilder.append(this.mUsername);
        stringBuilder.append("\n");
        stringBuilder.append("TrustRootCertURL: ");
        stringBuilder.append(this.mTrustRootCertUrl);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public boolean validate() {
        long l = this.mUpdateIntervalInMinutes;
        if (l == Long.MIN_VALUE) {
            Log.d(TAG, "Update interval not specified");
            return false;
        }
        if (l == 0xFFFFFFFFL) {
            return true;
        }
        if (!TextUtils.equals(this.mUpdateMethod, UPDATE_METHOD_OMADM) && !TextUtils.equals(this.mUpdateMethod, UPDATE_METHOD_SSP)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown update method: ");
            stringBuilder.append(this.mUpdateMethod);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (!(TextUtils.equals(this.mRestriction, UPDATE_RESTRICTION_HOMESP) || TextUtils.equals(this.mRestriction, UPDATE_RESTRICTION_ROAMING_PARTNER) || TextUtils.equals(this.mRestriction, UPDATE_RESTRICTION_UNRESTRICTED))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown restriction: ");
            stringBuilder.append(this.mRestriction);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (TextUtils.isEmpty(this.mServerUri)) {
            Log.d(TAG, "Missing update server URI");
            return false;
        }
        if (this.mServerUri.getBytes(StandardCharsets.UTF_8).length > 1023) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("URI bytes exceeded the max: ");
            stringBuilder.append(this.mServerUri.getBytes(StandardCharsets.UTF_8).length);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (TextUtils.isEmpty(this.mUsername)) {
            Log.d(TAG, "Missing username");
            return false;
        }
        if (this.mUsername.getBytes(StandardCharsets.UTF_8).length > 63) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Username bytes exceeded the max: ");
            stringBuilder.append(this.mUsername.getBytes(StandardCharsets.UTF_8).length);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (TextUtils.isEmpty(this.mBase64EncodedPassword)) {
            Log.d(TAG, "Missing username");
            return false;
        }
        if (this.mBase64EncodedPassword.getBytes(StandardCharsets.UTF_8).length > 255) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Password bytes exceeded the max: ");
            stringBuilder.append(this.mBase64EncodedPassword.getBytes(StandardCharsets.UTF_8).length);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        try {
            Base64.decode(this.mBase64EncodedPassword, 0);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid encoding for password: ");
            stringBuilder.append(this.mBase64EncodedPassword);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        if (TextUtils.isEmpty(this.mTrustRootCertUrl)) {
            Log.d(TAG, "Missing trust root certificate URL");
            return false;
        }
        if (this.mTrustRootCertUrl.getBytes(StandardCharsets.UTF_8).length > 1023) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Trust root cert URL bytes exceeded the max: ");
            stringBuilder.append(this.mTrustRootCertUrl.getBytes(StandardCharsets.UTF_8).length);
            Log.d(TAG, stringBuilder.toString());
            return false;
        }
        Object object = this.mTrustRootCertSha256Fingerprint;
        if (object == null) {
            Log.d(TAG, "Missing trust root certificate SHA-256 fingerprint");
            return false;
        }
        if (((byte[])object).length != 32) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Incorrect size of trust root certificate SHA-256 fingerprint: ");
            ((StringBuilder)object).append(this.mTrustRootCertSha256Fingerprint.length);
            Log.d(TAG, ((StringBuilder)object).toString());
            return false;
        }
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeLong(this.mUpdateIntervalInMinutes);
        parcel.writeString(this.mUpdateMethod);
        parcel.writeString(this.mRestriction);
        parcel.writeString(this.mServerUri);
        parcel.writeString(this.mUsername);
        parcel.writeString(this.mBase64EncodedPassword);
        parcel.writeString(this.mTrustRootCertUrl);
        parcel.writeByteArray(this.mTrustRootCertSha256Fingerprint);
        ParcelUtil.writeCertificate(parcel, this.mCaCertificate);
    }

}

