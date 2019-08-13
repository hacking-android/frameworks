/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.android.internal.util.Preconditions;

@SystemApi
@Deprecated
public class NanoApp
implements Parcelable {
    public static final Parcelable.Creator<NanoApp> CREATOR = new Parcelable.Creator<NanoApp>(){

        @Override
        public NanoApp createFromParcel(Parcel parcel) {
            return new NanoApp(parcel);
        }

        public NanoApp[] newArray(int n) {
            return new NanoApp[n];
        }
    };
    private final String TAG;
    private final String UNKNOWN;
    private byte[] mAppBinary;
    private long mAppId;
    private boolean mAppIdSet;
    private int mAppVersion;
    private String mName;
    private int mNeededExecMemBytes;
    private int mNeededReadMemBytes;
    private int[] mNeededSensors;
    private int mNeededWriteMemBytes;
    private int[] mOutputEvents;
    private String mPublisher;

    public NanoApp() {
        this(0L, null);
        this.mAppIdSet = false;
    }

    @Deprecated
    public NanoApp(int n, byte[] arrby) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        Log.w("NanoApp", "NanoApp(int, byte[]) is deprecated, please use NanoApp(long, byte[]) instead.");
    }

    public NanoApp(long l, byte[] arrby) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = "Unknown";
        this.mName = "Unknown";
        this.mAppId = l;
        this.mAppIdSet = true;
        this.mAppVersion = 0;
        this.mNeededReadMemBytes = 0;
        this.mNeededWriteMemBytes = 0;
        this.mNeededExecMemBytes = 0;
        this.mNeededSensors = new int[0];
        this.mOutputEvents = new int[0];
        this.mAppBinary = arrby;
    }

    private NanoApp(Parcel parcel) {
        this.TAG = "NanoApp";
        this.UNKNOWN = "Unknown";
        this.mPublisher = parcel.readString();
        this.mName = parcel.readString();
        this.mAppId = parcel.readLong();
        this.mAppVersion = parcel.readInt();
        this.mNeededReadMemBytes = parcel.readInt();
        this.mNeededWriteMemBytes = parcel.readInt();
        this.mNeededExecMemBytes = parcel.readInt();
        this.mNeededSensors = new int[parcel.readInt()];
        parcel.readIntArray(this.mNeededSensors);
        this.mOutputEvents = new int[parcel.readInt()];
        parcel.readIntArray(this.mOutputEvents);
        this.mAppBinary = new byte[parcel.readInt()];
        parcel.readByteArray(this.mAppBinary);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public byte[] getAppBinary() {
        return this.mAppBinary;
    }

    public long getAppId() {
        return this.mAppId;
    }

    public int getAppVersion() {
        return this.mAppVersion;
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

    public void setAppBinary(byte[] arrby) {
        Preconditions.checkNotNull(arrby, "appBinary must not be null");
        this.mAppBinary = arrby;
    }

    public void setAppId(long l) {
        this.mAppId = l;
        this.mAppIdSet = true;
    }

    public void setAppVersion(int n) {
        this.mAppVersion = n;
    }

    public void setName(String string2) {
        this.mName = string2;
    }

    public void setNeededExecMemBytes(int n) {
        this.mNeededExecMemBytes = n;
    }

    public void setNeededReadMemBytes(int n) {
        this.mNeededReadMemBytes = n;
    }

    public void setNeededSensors(int[] arrn) {
        Preconditions.checkNotNull(arrn, "neededSensors must not be null");
        this.mNeededSensors = arrn;
    }

    public void setNeededWriteMemBytes(int n) {
        this.mNeededWriteMemBytes = n;
    }

    public void setOutputEvents(int[] arrn) {
        Preconditions.checkNotNull(arrn, "outputEvents must not be null");
        this.mOutputEvents = arrn;
    }

    public void setPublisher(String string2) {
        this.mPublisher = string2;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Id : ");
        ((StringBuilder)charSequence).append(this.mAppId);
        charSequence = ((StringBuilder)charSequence).toString();
        CharSequence charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", Version : ");
        ((StringBuilder)charSequence2).append(this.mAppVersion);
        charSequence2 = ((StringBuilder)charSequence2).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append(", Name : ");
        ((StringBuilder)charSequence).append(this.mName);
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(", Publisher : ");
        ((StringBuilder)charSequence2).append(this.mPublisher);
        return ((StringBuilder)charSequence2).toString();
    }

    @Override
    public void writeToParcel(Parcel object, int n) {
        if (this.mAppBinary != null) {
            if (this.mAppIdSet) {
                ((Parcel)object).writeString(this.mPublisher);
                ((Parcel)object).writeString(this.mName);
                ((Parcel)object).writeLong(this.mAppId);
                ((Parcel)object).writeInt(this.mAppVersion);
                ((Parcel)object).writeInt(this.mNeededReadMemBytes);
                ((Parcel)object).writeInt(this.mNeededWriteMemBytes);
                ((Parcel)object).writeInt(this.mNeededExecMemBytes);
                ((Parcel)object).writeInt(this.mNeededSensors.length);
                ((Parcel)object).writeIntArray(this.mNeededSensors);
                ((Parcel)object).writeInt(this.mOutputEvents.length);
                ((Parcel)object).writeIntArray(this.mOutputEvents);
                ((Parcel)object).writeInt(this.mAppBinary.length);
                ((Parcel)object).writeByteArray(this.mAppBinary);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Must set AppId for nanoapp ");
            ((StringBuilder)object).append(this.mName);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Must set non-null AppBinary for nanoapp ");
        ((StringBuilder)object).append(this.mName);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

}

