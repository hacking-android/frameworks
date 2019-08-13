/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class PreciseCallState
implements Parcelable {
    public static final Parcelable.Creator<PreciseCallState> CREATOR = new Parcelable.Creator<PreciseCallState>(){

        @Override
        public PreciseCallState createFromParcel(Parcel parcel) {
            return new PreciseCallState(parcel);
        }

        public PreciseCallState[] newArray(int n) {
            return new PreciseCallState[n];
        }
    };
    public static final int PRECISE_CALL_STATE_ACTIVE = 1;
    public static final int PRECISE_CALL_STATE_ALERTING = 4;
    public static final int PRECISE_CALL_STATE_DIALING = 3;
    public static final int PRECISE_CALL_STATE_DISCONNECTED = 7;
    public static final int PRECISE_CALL_STATE_DISCONNECTING = 8;
    public static final int PRECISE_CALL_STATE_HOLDING = 2;
    public static final int PRECISE_CALL_STATE_IDLE = 0;
    public static final int PRECISE_CALL_STATE_INCOMING = 5;
    public static final int PRECISE_CALL_STATE_NOT_VALID = -1;
    public static final int PRECISE_CALL_STATE_WAITING = 6;
    private int mBackgroundCallState = -1;
    private int mDisconnectCause = -1;
    private int mForegroundCallState = -1;
    private int mPreciseDisconnectCause = -1;
    private int mRingingCallState = -1;

    public PreciseCallState() {
    }

    @UnsupportedAppUsage
    public PreciseCallState(int n, int n2, int n3, int n4, int n5) {
        this.mRingingCallState = n;
        this.mForegroundCallState = n2;
        this.mBackgroundCallState = n3;
        this.mDisconnectCause = n4;
        this.mPreciseDisconnectCause = n5;
    }

    private PreciseCallState(Parcel parcel) {
        this.mRingingCallState = parcel.readInt();
        this.mForegroundCallState = parcel.readInt();
        this.mBackgroundCallState = parcel.readInt();
        this.mDisconnectCause = parcel.readInt();
        this.mPreciseDisconnectCause = parcel.readInt();
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
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (PreciseCallState)object;
        if (this.mRingingCallState != ((PreciseCallState)object).mRingingCallState || this.mForegroundCallState != ((PreciseCallState)object).mForegroundCallState || this.mBackgroundCallState != ((PreciseCallState)object).mBackgroundCallState || this.mDisconnectCause != ((PreciseCallState)object).mDisconnectCause || this.mPreciseDisconnectCause != ((PreciseCallState)object).mPreciseDisconnectCause) {
            bl = false;
        }
        return bl;
    }

    public int getBackgroundCallState() {
        return this.mBackgroundCallState;
    }

    @UnsupportedAppUsage
    public int getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public int getForegroundCallState() {
        return this.mForegroundCallState;
    }

    @UnsupportedAppUsage
    public int getPreciseDisconnectCause() {
        return this.mPreciseDisconnectCause;
    }

    public int getRingingCallState() {
        return this.mRingingCallState;
    }

    public int hashCode() {
        return Objects.hash(this.mRingingCallState, this.mForegroundCallState, this.mForegroundCallState, this.mDisconnectCause, this.mPreciseDisconnectCause);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ringing call state: ");
        stringBuilder.append(this.mRingingCallState);
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(", Foreground call state: ");
        stringBuilder.append(this.mForegroundCallState);
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(", Background call state: ");
        stringBuilder.append(this.mBackgroundCallState);
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(", Disconnect cause: ");
        stringBuilder.append(this.mDisconnectCause);
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(", Precise disconnect cause: ");
        stringBuilder.append(this.mPreciseDisconnectCause);
        stringBuffer.append(stringBuilder.toString());
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRingingCallState);
        parcel.writeInt(this.mForegroundCallState);
        parcel.writeInt(this.mBackgroundCallState);
        parcel.writeInt(this.mDisconnectCause);
        parcel.writeInt(this.mPreciseDisconnectCause);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

