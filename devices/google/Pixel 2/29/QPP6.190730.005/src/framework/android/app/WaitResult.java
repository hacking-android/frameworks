/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.ComponentName;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WaitResult
implements Parcelable {
    public static final Parcelable.Creator<WaitResult> CREATOR = new Parcelable.Creator<WaitResult>(){

        @Override
        public WaitResult createFromParcel(Parcel parcel) {
            return new WaitResult(parcel);
        }

        public WaitResult[] newArray(int n) {
            return new WaitResult[n];
        }
    };
    public static final int INVALID_DELAY = -1;
    public static final int LAUNCH_STATE_COLD = 1;
    public static final int LAUNCH_STATE_HOT = 3;
    public static final int LAUNCH_STATE_WARM = 2;
    public int launchState;
    public int result;
    public boolean timeout;
    public long totalTime;
    public ComponentName who;

    public WaitResult() {
    }

    private WaitResult(Parcel parcel) {
        this.result = parcel.readInt();
        boolean bl = parcel.readInt() != 0;
        this.timeout = bl;
        this.who = ComponentName.readFromParcel(parcel);
        this.totalTime = parcel.readLong();
        this.launchState = parcel.readInt();
    }

    public static String launchStateToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UNKNOWN (");
                    stringBuilder.append(n);
                    stringBuilder.append(")");
                    return stringBuilder.toString();
                }
                return "HOT";
            }
            return "WARM";
        }
        return "COLD";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("WaitResult:");
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  result=");
        stringBuilder.append(this.result);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  timeout=");
        stringBuilder.append(this.timeout);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  who=");
        stringBuilder.append(this.who);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  totalTime=");
        stringBuilder.append(this.totalTime);
        printWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("  launchState=");
        stringBuilder.append(this.launchState);
        printWriter.println(stringBuilder.toString());
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.result);
        parcel.writeInt((int)this.timeout);
        ComponentName.writeToParcel(this.who, parcel);
        parcel.writeLong(this.totalTime);
        parcel.writeInt(this.launchState);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LaunchState {
    }

}

