/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

public final class ImsiEncryptionInfo
implements Parcelable {
    public static final Parcelable.Creator<ImsiEncryptionInfo> CREATOR = new Parcelable.Creator<ImsiEncryptionInfo>(){

        @Override
        public ImsiEncryptionInfo createFromParcel(Parcel parcel) {
            return new ImsiEncryptionInfo(parcel);
        }

        public ImsiEncryptionInfo[] newArray(int n) {
            return new ImsiEncryptionInfo[n];
        }
    };
    private static final String LOG_TAG = "ImsiEncryptionInfo";
    private final Date expirationTime;
    private final String keyIdentifier;
    private final int keyType;
    private final String mcc;
    private final String mnc;
    private final PublicKey publicKey;

    public ImsiEncryptionInfo(Parcel parcel) {
        byte[] arrby = new byte[parcel.readInt()];
        parcel.readByteArray(arrby);
        this.publicKey = ImsiEncryptionInfo.makeKeyObject(arrby);
        this.mcc = parcel.readString();
        this.mnc = parcel.readString();
        this.keyIdentifier = parcel.readString();
        this.keyType = parcel.readInt();
        this.expirationTime = new Date(parcel.readLong());
    }

    public ImsiEncryptionInfo(String string2, String string3, int n, String string4, PublicKey publicKey, Date date) {
        this.mcc = string2;
        this.mnc = string3;
        this.keyType = n;
        this.publicKey = publicKey;
        this.keyIdentifier = string4;
        this.expirationTime = date;
    }

    public ImsiEncryptionInfo(String string2, String string3, int n, String string4, byte[] arrby, Date date) {
        this(string2, string3, n, string4, ImsiEncryptionInfo.makeKeyObject(arrby), date);
    }

    private static PublicKey makeKeyObject(byte[] object) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
            object = KeyFactory.getInstance("RSA").generatePublic(x509EncodedKeySpec);
            return object;
        }
        catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
            Log.e(LOG_TAG, "Error makeKeyObject: unable to convert into PublicKey", generalSecurityException);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Date getExpirationTime() {
        return this.expirationTime;
    }

    public String getKeyIdentifier() {
        return this.keyIdentifier;
    }

    public int getKeyType() {
        return this.keyType;
    }

    public String getMcc() {
        return this.mcc;
    }

    public String getMnc() {
        return this.mnc;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsiEncryptionInfo mcc=");
        stringBuilder.append(this.mcc);
        stringBuilder.append("mnc=");
        stringBuilder.append(this.mnc);
        stringBuilder.append("publicKey=");
        stringBuilder.append(this.publicKey);
        stringBuilder.append(", keyIdentifier=");
        stringBuilder.append(this.keyIdentifier);
        stringBuilder.append(", keyType=");
        stringBuilder.append(this.keyType);
        stringBuilder.append(", expirationTime=");
        stringBuilder.append(this.expirationTime);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        byte[] arrby = this.publicKey.getEncoded();
        parcel.writeInt(arrby.length);
        parcel.writeByteArray(arrby);
        parcel.writeString(this.mcc);
        parcel.writeString(this.mnc);
        parcel.writeString(this.keyIdentifier);
        parcel.writeInt(this.keyType);
        parcel.writeLong(this.expirationTime.getTime());
    }

}

