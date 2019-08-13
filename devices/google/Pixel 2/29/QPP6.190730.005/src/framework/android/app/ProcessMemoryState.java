/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.os.Parcel;
import android.os.Parcelable;

public final class ProcessMemoryState
implements Parcelable {
    public static final Parcelable.Creator<ProcessMemoryState> CREATOR = new Parcelable.Creator<ProcessMemoryState>(){

        @Override
        public ProcessMemoryState createFromParcel(Parcel parcel) {
            return new ProcessMemoryState(parcel);
        }

        public ProcessMemoryState[] newArray(int n) {
            return new ProcessMemoryState[n];
        }
    };
    public final int oomScore;
    public final int pid;
    public final String processName;
    public final int uid;

    public ProcessMemoryState(int n, int n2, String string2, int n3) {
        this.uid = n;
        this.pid = n2;
        this.processName = string2;
        this.oomScore = n3;
    }

    private ProcessMemoryState(Parcel parcel) {
        this.uid = parcel.readInt();
        this.pid = parcel.readInt();
        this.processName = parcel.readString();
        this.oomScore = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.uid);
        parcel.writeInt(this.pid);
        parcel.writeString(this.processName);
        parcel.writeInt(this.oomScore);
    }

}

