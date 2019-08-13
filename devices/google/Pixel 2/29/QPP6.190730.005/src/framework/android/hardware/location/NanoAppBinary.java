/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

@SystemApi
public final class NanoAppBinary
implements Parcelable {
    public static final Parcelable.Creator<NanoAppBinary> CREATOR;
    private static final int EXPECTED_HEADER_VERSION = 1;
    private static final int EXPECTED_MAGIC_VALUE = 1330528590;
    private static final ByteOrder HEADER_ORDER;
    private static final int HEADER_SIZE_BYTES = 40;
    private static final int NANOAPP_ENCRYPTED_FLAG_BIT = 2;
    private static final int NANOAPP_SIGNED_FLAG_BIT = 1;
    private static final String TAG = "NanoAppBinary";
    private int mFlags;
    private boolean mHasValidHeader = false;
    private int mHeaderVersion;
    private long mHwHubType;
    private int mMagic;
    private byte[] mNanoAppBinary;
    private long mNanoAppId;
    private int mNanoAppVersion;
    private byte mTargetChreApiMajorVersion;
    private byte mTargetChreApiMinorVersion;

    static {
        HEADER_ORDER = ByteOrder.LITTLE_ENDIAN;
        CREATOR = new Parcelable.Creator<NanoAppBinary>(){

            @Override
            public NanoAppBinary createFromParcel(Parcel parcel) {
                return new NanoAppBinary(parcel);
            }

            public NanoAppBinary[] newArray(int n) {
                return new NanoAppBinary[n];
            }
        };
    }

    private NanoAppBinary(Parcel parcel) {
        this.mNanoAppBinary = new byte[parcel.readInt()];
        parcel.readByteArray(this.mNanoAppBinary);
        this.parseBinaryHeader();
    }

    public NanoAppBinary(byte[] arrby) {
        this.mNanoAppBinary = arrby;
        this.parseBinaryHeader();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void parseBinaryHeader() {
        block3 : {
            Object object = ByteBuffer.wrap(this.mNanoAppBinary).order(HEADER_ORDER);
            this.mHasValidHeader = false;
            try {
                int n = this.mHeaderVersion = ((ByteBuffer)object).getInt();
                if (n != 1) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected header version ");
                    ((StringBuilder)object).append(this.mHeaderVersion);
                    ((StringBuilder)object).append(" while parsing header (expected ");
                    ((StringBuilder)object).append(1);
                    ((StringBuilder)object).append(")");
                    Log.e(TAG, ((StringBuilder)object).toString());
                    return;
                }
                this.mMagic = ((ByteBuffer)object).getInt();
                this.mNanoAppId = ((ByteBuffer)object).getLong();
                this.mNanoAppVersion = ((ByteBuffer)object).getInt();
                this.mFlags = ((ByteBuffer)object).getInt();
                this.mHwHubType = ((ByteBuffer)object).getLong();
                this.mTargetChreApiMajorVersion = ((ByteBuffer)object).get();
                this.mTargetChreApiMinorVersion = ((ByteBuffer)object).get();
                if (this.mMagic == 1330528590) break block3;
            }
            catch (BufferUnderflowException bufferUnderflowException) {
                Log.e(TAG, "Not enough contents in nanoapp header");
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected magic value ");
            ((StringBuilder)object).append(String.format("0x%08X", this.mMagic));
            ((StringBuilder)object).append("while parsing header (expected ");
            ((StringBuilder)object).append(String.format("0x%08X", 1330528590));
            ((StringBuilder)object).append(")");
            Log.e(TAG, ((StringBuilder)object).toString());
            return;
        }
        this.mHasValidHeader = true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getBinary() {
        return this.mNanoAppBinary;
    }

    public byte[] getBinaryNoHeader() {
        Object object = this.mNanoAppBinary;
        if (((byte[])object).length >= 40) {
            return Arrays.copyOfRange((byte[])object, 40, ((byte[])object).length);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("NanoAppBinary binary byte size (");
        ((StringBuilder)object).append(this.mNanoAppBinary.length);
        ((StringBuilder)object).append(") is less than header size (");
        ((StringBuilder)object).append(40);
        ((StringBuilder)object).append(")");
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public int getFlags() {
        return this.mFlags;
    }

    public int getHeaderVersion() {
        return this.mHeaderVersion;
    }

    public long getHwHubType() {
        return this.mHwHubType;
    }

    public long getNanoAppId() {
        return this.mNanoAppId;
    }

    public int getNanoAppVersion() {
        return this.mNanoAppVersion;
    }

    public byte getTargetChreApiMajorVersion() {
        return this.mTargetChreApiMajorVersion;
    }

    public byte getTargetChreApiMinorVersion() {
        return this.mTargetChreApiMinorVersion;
    }

    public boolean hasValidHeader() {
        return this.mHasValidHeader;
    }

    public boolean isEncrypted() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    public boolean isSigned() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mNanoAppBinary.length);
        parcel.writeByteArray(this.mNanoAppBinary);
    }

}

