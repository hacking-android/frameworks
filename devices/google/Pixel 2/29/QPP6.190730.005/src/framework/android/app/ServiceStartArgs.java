/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

public class ServiceStartArgs
implements Parcelable {
    public static final Parcelable.Creator<ServiceStartArgs> CREATOR = new Parcelable.Creator<ServiceStartArgs>(){

        @Override
        public ServiceStartArgs createFromParcel(Parcel parcel) {
            return new ServiceStartArgs(parcel);
        }

        public ServiceStartArgs[] newArray(int n) {
            return new ServiceStartArgs[n];
        }
    };
    public final Intent args;
    public final int flags;
    public final int startId;
    public final boolean taskRemoved;

    public ServiceStartArgs(Parcel parcel) {
        boolean bl = parcel.readInt() != 0;
        this.taskRemoved = bl;
        this.startId = parcel.readInt();
        this.flags = parcel.readInt();
        this.args = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
    }

    public ServiceStartArgs(boolean bl, int n, int n2, Intent intent) {
        this.taskRemoved = bl;
        this.startId = n;
        this.flags = n2;
        this.args = intent;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ServiceStartArgs{taskRemoved=");
        stringBuilder.append(this.taskRemoved);
        stringBuilder.append(", startId=");
        stringBuilder.append(this.startId);
        stringBuilder.append(", flags=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        stringBuilder.append(", args=");
        stringBuilder.append(this.args);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.taskRemoved);
        parcel.writeInt(this.startId);
        parcel.writeInt(n);
        if (this.args != null) {
            parcel.writeInt(1);
            this.args.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

}

