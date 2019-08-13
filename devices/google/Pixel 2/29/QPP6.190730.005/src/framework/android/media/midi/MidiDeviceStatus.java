/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.media.midi.MidiDeviceInfo;
import android.os.Parcel;
import android.os.Parcelable;

public final class MidiDeviceStatus
implements Parcelable {
    public static final Parcelable.Creator<MidiDeviceStatus> CREATOR = new Parcelable.Creator<MidiDeviceStatus>(){

        @Override
        public MidiDeviceStatus createFromParcel(Parcel parcel) {
            return new MidiDeviceStatus((MidiDeviceInfo)parcel.readParcelable(MidiDeviceInfo.class.getClassLoader()), parcel.createBooleanArray(), parcel.createIntArray());
        }

        public MidiDeviceStatus[] newArray(int n) {
            return new MidiDeviceStatus[n];
        }
    };
    private static final String TAG = "MidiDeviceStatus";
    private final MidiDeviceInfo mDeviceInfo;
    private final boolean[] mInputPortOpen;
    private final int[] mOutputPortOpenCount;

    public MidiDeviceStatus(MidiDeviceInfo midiDeviceInfo) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mInputPortOpen = new boolean[midiDeviceInfo.getInputPortCount()];
        this.mOutputPortOpenCount = new int[midiDeviceInfo.getOutputPortCount()];
    }

    public MidiDeviceStatus(MidiDeviceInfo midiDeviceInfo, boolean[] arrbl, int[] arrn) {
        this.mDeviceInfo = midiDeviceInfo;
        this.mInputPortOpen = new boolean[arrbl.length];
        System.arraycopy(arrbl, 0, this.mInputPortOpen, 0, arrbl.length);
        this.mOutputPortOpenCount = new int[arrn.length];
        System.arraycopy(arrn, 0, this.mOutputPortOpenCount, 0, arrn.length);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public MidiDeviceInfo getDeviceInfo() {
        return this.mDeviceInfo;
    }

    public int getOutputPortOpenCount(int n) {
        return this.mOutputPortOpenCount[n];
    }

    public boolean isInputPortOpen(int n) {
        return this.mInputPortOpen[n];
    }

    public String toString() {
        int n;
        int n2 = this.mDeviceInfo.getInputPortCount();
        int n3 = this.mDeviceInfo.getOutputPortCount();
        StringBuilder stringBuilder = new StringBuilder("mInputPortOpen=[");
        for (n = 0; n < n2; ++n) {
            stringBuilder.append(this.mInputPortOpen[n]);
            if (n >= n2 - 1) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append("] mOutputPortOpenCount=[");
        for (n = 0; n < n3; ++n) {
            stringBuilder.append(this.mOutputPortOpenCount[n]);
            if (n >= n3 - 1) continue;
            stringBuilder.append(",");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mDeviceInfo, n);
        parcel.writeBooleanArray(this.mInputPortOpen);
        parcel.writeIntArray(this.mOutputPortOpenCount);
    }

}

