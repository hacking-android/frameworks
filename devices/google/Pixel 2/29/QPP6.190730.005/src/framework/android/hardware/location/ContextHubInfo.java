/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.hardware.contexthub.V1_0.ContextHub;
import android.hardware.location.MemoryRegion;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
public class ContextHubInfo
implements Parcelable {
    public static final Parcelable.Creator<ContextHubInfo> CREATOR = new Parcelable.Creator<ContextHubInfo>(){

        @Override
        public ContextHubInfo createFromParcel(Parcel parcel) {
            return new ContextHubInfo(parcel);
        }

        public ContextHubInfo[] newArray(int n) {
            return new ContextHubInfo[n];
        }
    };
    private byte mChreApiMajorVersion;
    private byte mChreApiMinorVersion;
    private short mChrePatchVersion;
    private long mChrePlatformId;
    private int mId;
    private int mMaxPacketLengthBytes;
    private MemoryRegion[] mMemoryRegions;
    private String mName;
    private float mPeakMips;
    private float mPeakPowerDrawMw;
    private int mPlatformVersion;
    private float mSleepPowerDrawMw;
    private float mStoppedPowerDrawMw;
    private int[] mSupportedSensors;
    private String mToolchain;
    private int mToolchainVersion;
    private String mVendor;

    public ContextHubInfo() {
    }

    public ContextHubInfo(ContextHub contextHub) {
        this.mId = contextHub.hubId;
        this.mName = contextHub.name;
        this.mVendor = contextHub.vendor;
        this.mToolchain = contextHub.toolchain;
        this.mPlatformVersion = contextHub.platformVersion;
        this.mToolchainVersion = contextHub.toolchainVersion;
        this.mPeakMips = contextHub.peakMips;
        this.mStoppedPowerDrawMw = contextHub.stoppedPowerDrawMw;
        this.mSleepPowerDrawMw = contextHub.sleepPowerDrawMw;
        this.mPeakPowerDrawMw = contextHub.peakPowerDrawMw;
        this.mMaxPacketLengthBytes = contextHub.maxSupportedMsgLen;
        this.mChrePlatformId = contextHub.chrePlatformId;
        this.mChreApiMajorVersion = contextHub.chreApiMajorVersion;
        this.mChreApiMinorVersion = contextHub.chreApiMinorVersion;
        this.mChrePatchVersion = contextHub.chrePatchVersion;
        this.mSupportedSensors = new int[0];
        this.mMemoryRegions = new MemoryRegion[0];
    }

    private ContextHubInfo(Parcel parcel) {
        this.mId = parcel.readInt();
        this.mName = parcel.readString();
        this.mVendor = parcel.readString();
        this.mToolchain = parcel.readString();
        this.mPlatformVersion = parcel.readInt();
        this.mToolchainVersion = parcel.readInt();
        this.mPeakMips = parcel.readFloat();
        this.mStoppedPowerDrawMw = parcel.readFloat();
        this.mSleepPowerDrawMw = parcel.readFloat();
        this.mPeakPowerDrawMw = parcel.readFloat();
        this.mMaxPacketLengthBytes = parcel.readInt();
        this.mChrePlatformId = parcel.readLong();
        this.mChreApiMajorVersion = parcel.readByte();
        this.mChreApiMinorVersion = parcel.readByte();
        this.mChrePatchVersion = (short)parcel.readInt();
        this.mSupportedSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mSupportedSensors);
        this.mMemoryRegions = parcel.createTypedArray(MemoryRegion.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        boolean bl2 = false;
        if (object instanceof ContextHubInfo) {
            bl2 = ((ContextHubInfo)(object = (ContextHubInfo)object)).getId() == this.mId && ((ContextHubInfo)object).getName().equals(this.mName) && ((ContextHubInfo)object).getVendor().equals(this.mVendor) && ((ContextHubInfo)object).getToolchain().equals(this.mToolchain) && ((ContextHubInfo)object).getToolchainVersion() == this.mToolchainVersion && ((ContextHubInfo)object).getStaticSwVersion() == this.getStaticSwVersion() && ((ContextHubInfo)object).getChrePlatformId() == this.mChrePlatformId && ((ContextHubInfo)object).getPeakMips() == this.mPeakMips && ((ContextHubInfo)object).getStoppedPowerDrawMw() == this.mStoppedPowerDrawMw && ((ContextHubInfo)object).getSleepPowerDrawMw() == this.mSleepPowerDrawMw && ((ContextHubInfo)object).getPeakPowerDrawMw() == this.mPeakPowerDrawMw && ((ContextHubInfo)object).getMaxPacketLengthBytes() == this.mMaxPacketLengthBytes && Arrays.equals(((ContextHubInfo)object).getSupportedSensors(), this.mSupportedSensors) && Arrays.equals(((ContextHubInfo)object).getMemoryRegions(), this.mMemoryRegions) ? bl : false;
        }
        return bl2;
    }

    public byte getChreApiMajorVersion() {
        return this.mChreApiMajorVersion;
    }

    public byte getChreApiMinorVersion() {
        return this.mChreApiMinorVersion;
    }

    public short getChrePatchVersion() {
        return this.mChrePatchVersion;
    }

    public long getChrePlatformId() {
        return this.mChrePlatformId;
    }

    public int getId() {
        return this.mId;
    }

    public int getMaxPacketLengthBytes() {
        return this.mMaxPacketLengthBytes;
    }

    public MemoryRegion[] getMemoryRegions() {
        MemoryRegion[] arrmemoryRegion = this.mMemoryRegions;
        return Arrays.copyOf(arrmemoryRegion, arrmemoryRegion.length);
    }

    public String getName() {
        return this.mName;
    }

    public float getPeakMips() {
        return this.mPeakMips;
    }

    public float getPeakPowerDrawMw() {
        return this.mPeakPowerDrawMw;
    }

    public int getPlatformVersion() {
        return this.mPlatformVersion;
    }

    public float getSleepPowerDrawMw() {
        return this.mSleepPowerDrawMw;
    }

    public int getStaticSwVersion() {
        return this.mChreApiMajorVersion << 24 | this.mChreApiMinorVersion << 16 | this.mChrePatchVersion;
    }

    public float getStoppedPowerDrawMw() {
        return this.mStoppedPowerDrawMw;
    }

    public int[] getSupportedSensors() {
        int[] arrn = this.mSupportedSensors;
        return Arrays.copyOf(arrn, arrn.length);
    }

    public String getToolchain() {
        return this.mToolchain;
    }

    public int getToolchainVersion() {
        return this.mToolchainVersion;
    }

    public String getVendor() {
        return this.mVendor;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("");
        ((StringBuilder)charSequence).append("ID/handle : ");
        ((StringBuilder)charSequence).append(this.mId);
        CharSequence charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", Name : ");
        ((StringBuilder)charSequence).append(this.mName);
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\n\tVendor : ");
        ((StringBuilder)charSequence).append(this.mVendor);
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", Toolchain : ");
        ((StringBuilder)charSequence).append(this.mToolchain);
        charSequence2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", Toolchain version: 0x");
        ((StringBuilder)charSequence).append(Integer.toHexString(this.mToolchainVersion));
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append("\n\tPlatformVersion : 0x");
        ((StringBuilder)charSequence2).append(Integer.toHexString(this.mPlatformVersion));
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", SwVersion : ");
        ((StringBuilder)charSequence2).append(this.mChreApiMajorVersion);
        ((StringBuilder)charSequence2).append(".");
        ((StringBuilder)charSequence2).append(this.mChreApiMinorVersion);
        ((StringBuilder)charSequence2).append(".");
        ((StringBuilder)charSequence2).append(this.mChrePatchVersion);
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", CHRE platform ID: 0x");
        ((StringBuilder)charSequence2).append(Long.toHexString(this.mChrePlatformId));
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("\n\tPeakMips : ");
        ((StringBuilder)charSequence).append(this.mPeakMips);
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", StoppedPowerDraw : ");
        ((StringBuilder)charSequence2).append(this.mStoppedPowerDrawMw);
        ((StringBuilder)charSequence2).append(" mW");
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", PeakPowerDraw : ");
        ((StringBuilder)charSequence2).append(this.mPeakPowerDrawMw);
        ((StringBuilder)charSequence2).append(" mW");
        charSequence = ((StringBuilder)charSequence2).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", MaxPacketLength : ");
        ((StringBuilder)charSequence2).append(this.mMaxPacketLengthBytes);
        ((StringBuilder)charSequence2).append(" Bytes");
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mName);
        parcel.writeString(this.mVendor);
        parcel.writeString(this.mToolchain);
        parcel.writeInt(this.mPlatformVersion);
        parcel.writeInt(this.mToolchainVersion);
        parcel.writeFloat(this.mPeakMips);
        parcel.writeFloat(this.mStoppedPowerDrawMw);
        parcel.writeFloat(this.mSleepPowerDrawMw);
        parcel.writeFloat(this.mPeakPowerDrawMw);
        parcel.writeInt(this.mMaxPacketLengthBytes);
        parcel.writeLong(this.mChrePlatformId);
        parcel.writeByte(this.mChreApiMajorVersion);
        parcel.writeByte(this.mChreApiMinorVersion);
        parcel.writeInt(this.mChrePatchVersion);
        parcel.writeInt(this.mSupportedSensors.length);
        parcel.writeIntArray(this.mSupportedSensors);
        parcel.writeTypedArray((Parcelable[])this.mMemoryRegions, n);
    }

}

