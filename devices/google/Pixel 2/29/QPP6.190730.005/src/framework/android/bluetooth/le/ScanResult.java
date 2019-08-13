/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanRecord;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class ScanResult
implements Parcelable {
    public static final Parcelable.Creator<ScanResult> CREATOR = new Parcelable.Creator<ScanResult>(){

        @Override
        public ScanResult createFromParcel(Parcel parcel) {
            return new ScanResult(parcel);
        }

        public ScanResult[] newArray(int n) {
            return new ScanResult[n];
        }
    };
    public static final int DATA_COMPLETE = 0;
    public static final int DATA_TRUNCATED = 2;
    private static final int ET_CONNECTABLE_MASK = 1;
    private static final int ET_LEGACY_MASK = 16;
    public static final int PERIODIC_INTERVAL_NOT_PRESENT = 0;
    public static final int PHY_UNUSED = 0;
    public static final int SID_NOT_PRESENT = 255;
    public static final int TX_POWER_NOT_PRESENT = 127;
    private int mAdvertisingSid;
    private BluetoothDevice mDevice;
    private int mEventType;
    private int mPeriodicAdvertisingInterval;
    private int mPrimaryPhy;
    private int mRssi;
    private ScanRecord mScanRecord;
    private int mSecondaryPhy;
    private long mTimestampNanos;
    private int mTxPower;

    public ScanResult(BluetoothDevice bluetoothDevice, int n, int n2, int n3, int n4, int n5, int n6, int n7, ScanRecord scanRecord, long l) {
        this.mDevice = bluetoothDevice;
        this.mEventType = n;
        this.mPrimaryPhy = n2;
        this.mSecondaryPhy = n3;
        this.mAdvertisingSid = n4;
        this.mTxPower = n5;
        this.mRssi = n6;
        this.mPeriodicAdvertisingInterval = n7;
        this.mScanRecord = scanRecord;
        this.mTimestampNanos = l;
    }

    @Deprecated
    public ScanResult(BluetoothDevice bluetoothDevice, ScanRecord scanRecord, int n, long l) {
        this.mDevice = bluetoothDevice;
        this.mScanRecord = scanRecord;
        this.mRssi = n;
        this.mTimestampNanos = l;
        this.mEventType = 17;
        this.mPrimaryPhy = 1;
        this.mSecondaryPhy = 0;
        this.mAdvertisingSid = 255;
        this.mTxPower = 127;
        this.mPeriodicAdvertisingInterval = 0;
    }

    private ScanResult(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        if (parcel.readInt() == 1) {
            this.mDevice = BluetoothDevice.CREATOR.createFromParcel(parcel);
        }
        if (parcel.readInt() == 1) {
            this.mScanRecord = ScanRecord.parseFromBytes(parcel.createByteArray());
        }
        this.mRssi = parcel.readInt();
        this.mTimestampNanos = parcel.readLong();
        this.mEventType = parcel.readInt();
        this.mPrimaryPhy = parcel.readInt();
        this.mSecondaryPhy = parcel.readInt();
        this.mAdvertisingSid = parcel.readInt();
        this.mTxPower = parcel.readInt();
        this.mPeriodicAdvertisingInterval = parcel.readInt();
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
        if (object != null && this.getClass() == object.getClass()) {
            object = (ScanResult)object;
            if (!Objects.equals(this.mDevice, ((ScanResult)object).mDevice) || this.mRssi != ((ScanResult)object).mRssi || !Objects.equals(this.mScanRecord, ((ScanResult)object).mScanRecord) || this.mTimestampNanos != ((ScanResult)object).mTimestampNanos || this.mEventType != ((ScanResult)object).mEventType || this.mPrimaryPhy != ((ScanResult)object).mPrimaryPhy || this.mSecondaryPhy != ((ScanResult)object).mSecondaryPhy || this.mAdvertisingSid != ((ScanResult)object).mAdvertisingSid || this.mTxPower != ((ScanResult)object).mTxPower || this.mPeriodicAdvertisingInterval != ((ScanResult)object).mPeriodicAdvertisingInterval) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getAdvertisingSid() {
        return this.mAdvertisingSid;
    }

    public int getDataStatus() {
        return this.mEventType >> 5 & 3;
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    public int getPeriodicAdvertisingInterval() {
        return this.mPeriodicAdvertisingInterval;
    }

    public int getPrimaryPhy() {
        return this.mPrimaryPhy;
    }

    public int getRssi() {
        return this.mRssi;
    }

    public ScanRecord getScanRecord() {
        return this.mScanRecord;
    }

    public int getSecondaryPhy() {
        return this.mSecondaryPhy;
    }

    public long getTimestampNanos() {
        return this.mTimestampNanos;
    }

    public int getTxPower() {
        return this.mTxPower;
    }

    public int hashCode() {
        return Objects.hash(this.mDevice, this.mRssi, this.mScanRecord, this.mTimestampNanos, this.mEventType, this.mPrimaryPhy, this.mSecondaryPhy, this.mAdvertisingSid, this.mTxPower, this.mPeriodicAdvertisingInterval);
    }

    public boolean isConnectable() {
        int n = this.mEventType;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean isLegacy() {
        boolean bl = (this.mEventType & 16) != 0;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ScanResult{device=");
        stringBuilder.append(this.mDevice);
        stringBuilder.append(", scanRecord=");
        stringBuilder.append(Objects.toString(this.mScanRecord));
        stringBuilder.append(", rssi=");
        stringBuilder.append(this.mRssi);
        stringBuilder.append(", timestampNanos=");
        stringBuilder.append(this.mTimestampNanos);
        stringBuilder.append(", eventType=");
        stringBuilder.append(this.mEventType);
        stringBuilder.append(", primaryPhy=");
        stringBuilder.append(this.mPrimaryPhy);
        stringBuilder.append(", secondaryPhy=");
        stringBuilder.append(this.mSecondaryPhy);
        stringBuilder.append(", advertisingSid=");
        stringBuilder.append(this.mAdvertisingSid);
        stringBuilder.append(", txPower=");
        stringBuilder.append(this.mTxPower);
        stringBuilder.append(", periodicAdvertisingInterval=");
        stringBuilder.append(this.mPeriodicAdvertisingInterval);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mDevice != null) {
            parcel.writeInt(1);
            this.mDevice.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        if (this.mScanRecord != null) {
            parcel.writeInt(1);
            parcel.writeByteArray(this.mScanRecord.getBytes());
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mRssi);
        parcel.writeLong(this.mTimestampNanos);
        parcel.writeInt(this.mEventType);
        parcel.writeInt(this.mPrimaryPhy);
        parcel.writeInt(this.mSecondaryPhy);
        parcel.writeInt(this.mAdvertisingSid);
        parcel.writeInt(this.mTxPower);
        parcel.writeInt(this.mPeriodicAdvertisingInterval);
    }

}

