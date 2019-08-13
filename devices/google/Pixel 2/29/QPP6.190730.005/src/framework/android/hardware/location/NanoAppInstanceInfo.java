/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import libcore.util.EmptyArray;

@SystemApi
@Deprecated
public class NanoAppInstanceInfo
implements Parcelable {
    public static final Parcelable.Creator<NanoAppInstanceInfo> CREATOR = new Parcelable.Creator<NanoAppInstanceInfo>(){

        @Override
        public NanoAppInstanceInfo createFromParcel(Parcel parcel) {
            return new NanoAppInstanceInfo(parcel);
        }

        public NanoAppInstanceInfo[] newArray(int n) {
            return new NanoAppInstanceInfo[n];
        }
    };
    private long mAppId;
    private int mAppVersion;
    private int mContexthubId;
    private int mHandle;
    private String mName = "Unknown";
    private int mNeededExecMemBytes = 0;
    private int mNeededReadMemBytes = 0;
    private int[] mNeededSensors = EmptyArray.INT;
    private int mNeededWriteMemBytes = 0;
    private int[] mOutputEvents = EmptyArray.INT;
    private String mPublisher = "Unknown";

    public NanoAppInstanceInfo() {
    }

    public NanoAppInstanceInfo(int n, long l, int n2, int n3) {
        this.mHandle = n;
        this.mAppId = l;
        this.mAppVersion = n2;
        this.mContexthubId = n3;
    }

    private NanoAppInstanceInfo(Parcel parcel) {
        this.mPublisher = parcel.readString();
        this.mName = parcel.readString();
        this.mHandle = parcel.readInt();
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mContexthubId = parcel.readInt();
        this.mNeededReadMemBytes = parcel.readInt();
        this.mNeededWriteMemBytes = parcel.readInt();
        this.mNeededExecMemBytes = parcel.readInt();
        this.mNeededSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mNeededSensors);
        this.mOutputEvents = new int[parcel.readInt()];
        parcel.readIntArray(this.mOutputEvents);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getAppId() {
        return this.mAppId;
    }

    public int getAppVersion() {
        return this.mAppVersion;
    }

    public int getContexthubId() {
        return this.mContexthubId;
    }

    public int getHandle() {
        return this.mHandle;
    }

    public String getName() {
        return this.mName;
    }

    public int getNeededExecMemBytes() {
        return this.mNeededExecMemBytes;
    }

    public int getNeededReadMemBytes() {
        return this.mNeededReadMemBytes;
    }

    public int[] getNeededSensors() {
        return this.mNeededSensors;
    }

    public int getNeededWriteMemBytes() {
        return this.mNeededWriteMemBytes;
    }

    public int[] getOutputEvents() {
        return this.mOutputEvents;
    }

    public String getPublisher() {
        return this.mPublisher;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("handle : ");
        charSequence.append(this.mHandle);
        charSequence = charSequence.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(", Id : 0x");
        stringBuilder.append(Long.toHexString(this.mAppId));
        charSequence = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(", Version : 0x");
        stringBuilder.append(Integer.toHexString(this.mAppVersion));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPublisher);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mHandle);
        parcel.writeLong(this.mAppId);
        parcel.writeInt(this.mAppVersion);
        parcel.writeInt(this.mContexthubId);
        parcel.writeInt(this.mNeededReadMemBytes);
        parcel.writeInt(this.mNeededWriteMemBytes);
        parcel.writeInt(this.mNeededExecMemBytes);
        parcel.writeInt(this.mNeededSensors.length);
        parcel.writeIntArray(this.mNeededSensors);
        parcel.writeInt(this.mOutputEvents.length);
        parcel.writeIntArray(this.mOutputEvents);
    }

}

