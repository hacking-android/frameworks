/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class BluetoothAvrcpPlayerSettings
implements Parcelable {
    public static final Parcelable.Creator<BluetoothAvrcpPlayerSettings> CREATOR = new Parcelable.Creator<BluetoothAvrcpPlayerSettings>(){

        @Override
        public BluetoothAvrcpPlayerSettings createFromParcel(Parcel parcel) {
            return new BluetoothAvrcpPlayerSettings(parcel);
        }

        public BluetoothAvrcpPlayerSettings[] newArray(int n) {
            return new BluetoothAvrcpPlayerSettings[n];
        }
    };
    public static final int SETTING_EQUALIZER = 1;
    public static final int SETTING_REPEAT = 2;
    public static final int SETTING_SCAN = 8;
    public static final int SETTING_SHUFFLE = 4;
    public static final int STATE_ALL_TRACK = 3;
    public static final int STATE_GROUP = 4;
    public static final int STATE_INVALID = -1;
    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;
    public static final int STATE_SINGLE_TRACK = 2;
    public static final String TAG = "BluetoothAvrcpPlayerSettings";
    private int mSettings;
    private Map<Integer, Integer> mSettingsValue = new HashMap<Integer, Integer>();

    public BluetoothAvrcpPlayerSettings(int n) {
        this.mSettings = n;
    }

    private BluetoothAvrcpPlayerSettings(Parcel parcel) {
        this.mSettings = parcel.readInt();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            this.mSettingsValue.put(parcel.readInt(), parcel.readInt());
        }
    }

    public void addSettingValue(int n, int n2) {
        if ((this.mSettings & n) != 0) {
            this.mSettingsValue.put(n, n2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Setting not supported: ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(this.mSettings);
        Log.e(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Setting not supported: ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getSettingValue(int n) {
        if ((this.mSettings & n) != 0) {
            Integer n2 = this.mSettingsValue.get(n);
            if (n2 == null) {
                return -1;
            }
            return n2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Setting not supported: ");
        stringBuilder.append(n);
        stringBuilder.append(" ");
        stringBuilder.append(this.mSettings);
        Log.e(TAG, stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("Setting not supported: ");
        stringBuilder.append(n);
        throw new IllegalStateException(stringBuilder.toString());
    }

    public int getSettings() {
        return this.mSettings;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n22) {
        parcel.writeInt(this.mSettings);
        parcel.writeInt(this.mSettingsValue.size());
        for (int n22 : this.mSettingsValue.keySet()) {
            parcel.writeInt(n22);
            parcel.writeInt(this.mSettingsValue.get(n22));
        }
    }

}

