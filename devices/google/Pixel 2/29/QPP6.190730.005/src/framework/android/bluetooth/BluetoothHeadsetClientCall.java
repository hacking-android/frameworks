/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import java.util.UUID;

public final class BluetoothHeadsetClientCall
implements Parcelable {
    public static final int CALL_STATE_ACTIVE = 0;
    public static final int CALL_STATE_ALERTING = 3;
    public static final int CALL_STATE_DIALING = 2;
    public static final int CALL_STATE_HELD = 1;
    public static final int CALL_STATE_HELD_BY_RESPONSE_AND_HOLD = 6;
    public static final int CALL_STATE_INCOMING = 4;
    public static final int CALL_STATE_TERMINATED = 7;
    public static final int CALL_STATE_WAITING = 5;
    public static final Parcelable.Creator<BluetoothHeadsetClientCall> CREATOR = new Parcelable.Creator<BluetoothHeadsetClientCall>(){

        @Override
        public BluetoothHeadsetClientCall createFromParcel(Parcel parcel) {
            BluetoothDevice bluetoothDevice = (BluetoothDevice)parcel.readParcelable(null);
            int n = parcel.readInt();
            UUID uUID = UUID.fromString(parcel.readString());
            int n2 = parcel.readInt();
            String string2 = parcel.readString();
            boolean bl = parcel.readInt() == 1;
            boolean bl2 = parcel.readInt() == 1;
            boolean bl3 = parcel.readInt() == 1;
            return new BluetoothHeadsetClientCall(bluetoothDevice, n, uUID, n2, string2, bl, bl2, bl3);
        }

        public BluetoothHeadsetClientCall[] newArray(int n) {
            return new BluetoothHeadsetClientCall[n];
        }
    };
    private final long mCreationElapsedMilli;
    private final BluetoothDevice mDevice;
    private final int mId;
    private final boolean mInBandRing;
    private boolean mMultiParty;
    private String mNumber;
    private final boolean mOutgoing;
    private int mState;
    private final UUID mUUID;

    public BluetoothHeadsetClientCall(BluetoothDevice bluetoothDevice, int n, int n2, String string2, boolean bl, boolean bl2, boolean bl3) {
        this(bluetoothDevice, n, UUID.randomUUID(), n2, string2, bl, bl2, bl3);
    }

    public BluetoothHeadsetClientCall(BluetoothDevice object, int n, UUID uUID, int n2, String string2, boolean bl, boolean bl2, boolean bl3) {
        this.mDevice = object;
        this.mId = n;
        this.mUUID = uUID;
        this.mState = n2;
        object = string2 != null ? string2 : "";
        this.mNumber = object;
        this.mMultiParty = bl;
        this.mOutgoing = bl2;
        this.mInBandRing = bl3;
        this.mCreationElapsedMilli = SystemClock.elapsedRealtime();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getCreationElapsedMilli() {
        return this.mCreationElapsedMilli;
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    @UnsupportedAppUsage
    public int getId() {
        return this.mId;
    }

    @UnsupportedAppUsage
    public String getNumber() {
        return this.mNumber;
    }

    @UnsupportedAppUsage
    public int getState() {
        return this.mState;
    }

    public UUID getUUID() {
        return this.mUUID;
    }

    public boolean isInBandRing() {
        return this.mInBandRing;
    }

    @UnsupportedAppUsage
    public boolean isMultiParty() {
        return this.mMultiParty;
    }

    @UnsupportedAppUsage
    public boolean isOutgoing() {
        return this.mOutgoing;
    }

    public void setMultiParty(boolean bl) {
        this.mMultiParty = bl;
    }

    public void setNumber(String string2) {
        this.mNumber = string2;
    }

    public void setState(int n) {
        this.mState = n;
    }

    public String toString() {
        return this.toString(false);
    }

    public String toString(boolean bl) {
        StringBuilder stringBuilder = new StringBuilder("BluetoothHeadsetClientCall{mDevice: ");
        Object object = this.mDevice;
        if (!bl) {
            object = ((BluetoothDevice)object).hashCode();
        }
        stringBuilder.append(object);
        stringBuilder.append(", mId: ");
        stringBuilder.append(this.mId);
        stringBuilder.append(", mUUID: ");
        stringBuilder.append(this.mUUID);
        stringBuilder.append(", mState: ");
        int n = this.mState;
        switch (n) {
            default: {
                stringBuilder.append(n);
                break;
            }
            case 7: {
                stringBuilder.append("TERMINATED");
                break;
            }
            case 6: {
                stringBuilder.append("HELD_BY_RESPONSE_AND_HOLD");
                break;
            }
            case 5: {
                stringBuilder.append("WAITING");
                break;
            }
            case 4: {
                stringBuilder.append("INCOMING");
                break;
            }
            case 3: {
                stringBuilder.append("ALERTING");
                break;
            }
            case 2: {
                stringBuilder.append("DIALING");
                break;
            }
            case 1: {
                stringBuilder.append("HELD");
                break;
            }
            case 0: {
                stringBuilder.append("ACTIVE");
            }
        }
        stringBuilder.append(", mNumber: ");
        object = this.mNumber;
        if (!bl) {
            object = ((String)object).hashCode();
        }
        stringBuilder.append(object);
        stringBuilder.append(", mMultiParty: ");
        stringBuilder.append(this.mMultiParty);
        stringBuilder.append(", mOutgoing: ");
        stringBuilder.append(this.mOutgoing);
        stringBuilder.append(", mInBandRing: ");
        stringBuilder.append(this.mInBandRing);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mDevice, 0);
        parcel.writeInt(this.mId);
        parcel.writeString(this.mUUID.toString());
        parcel.writeInt(this.mState);
        parcel.writeString(this.mNumber);
        parcel.writeInt((int)this.mMultiParty);
        parcel.writeInt((int)this.mOutgoing);
        parcel.writeInt((int)this.mInBandRing);
    }

}

