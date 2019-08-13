/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;

public class WpsResult
implements Parcelable {
    public static final Parcelable.Creator<WpsResult> CREATOR = new Parcelable.Creator<WpsResult>(){

        @Override
        public WpsResult createFromParcel(Parcel parcel) {
            WpsResult wpsResult = new WpsResult();
            wpsResult.status = Status.valueOf(parcel.readString());
            wpsResult.pin = parcel.readString();
            return wpsResult;
        }

        public WpsResult[] newArray(int n) {
            return new WpsResult[n];
        }
    };
    public String pin;
    public Status status;

    public WpsResult() {
        this.status = Status.FAILURE;
        this.pin = null;
    }

    public WpsResult(Status status) {
        this.status = status;
        this.pin = null;
    }

    public WpsResult(WpsResult wpsResult) {
        if (wpsResult != null) {
            this.status = wpsResult.status;
            this.pin = wpsResult.pin;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" status: ");
        stringBuffer.append(this.status.toString());
        stringBuffer.append('\n');
        stringBuffer.append(" pin: ");
        stringBuffer.append(this.pin);
        stringBuffer.append("\n");
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.status.name());
        parcel.writeString(this.pin);
    }

    public static enum Status {
        SUCCESS,
        FAILURE,
        IN_PROGRESS;
        
    }

}

