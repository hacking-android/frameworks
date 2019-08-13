/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import android.telephony.Rlog;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public final class ImsExternalCallState
implements Parcelable {
    public static final int CALL_STATE_CONFIRMED = 1;
    public static final int CALL_STATE_TERMINATED = 2;
    public static final Parcelable.Creator<ImsExternalCallState> CREATOR = new Parcelable.Creator<ImsExternalCallState>(){

        @Override
        public ImsExternalCallState createFromParcel(Parcel parcel) {
            return new ImsExternalCallState(parcel);
        }

        public ImsExternalCallState[] newArray(int n) {
            return new ImsExternalCallState[n];
        }
    };
    private static final String TAG = "ImsExternalCallState";
    private Uri mAddress;
    private int mCallId;
    private int mCallState;
    private int mCallType;
    private boolean mIsHeld;
    private boolean mIsPullable;
    private Uri mLocalAddress;

    public ImsExternalCallState() {
    }

    public ImsExternalCallState(int n, Uri object, Uri uri, boolean bl, int n2, int n3, boolean bl2) {
        this.mCallId = n;
        this.mAddress = object;
        this.mLocalAddress = uri;
        this.mIsPullable = bl;
        this.mCallState = n2;
        this.mCallType = n3;
        this.mIsHeld = bl2;
        object = new StringBuilder();
        ((StringBuilder)object).append("ImsExternalCallState = ");
        ((StringBuilder)object).append(this);
        Rlog.d(TAG, ((StringBuilder)object).toString());
    }

    public ImsExternalCallState(int n, Uri object, boolean bl, int n2, int n3, boolean bl2) {
        this.mCallId = n;
        this.mAddress = object;
        this.mIsPullable = bl;
        this.mCallState = n2;
        this.mCallType = n3;
        this.mIsHeld = bl2;
        object = new StringBuilder();
        ((StringBuilder)object).append("ImsExternalCallState = ");
        ((StringBuilder)object).append(this);
        Rlog.d(TAG, ((StringBuilder)object).toString());
    }

    public ImsExternalCallState(Parcel object) {
        this.mCallId = ((Parcel)object).readInt();
        ClassLoader classLoader = ImsExternalCallState.class.getClassLoader();
        this.mAddress = (Uri)((Parcel)object).readParcelable(classLoader);
        this.mLocalAddress = (Uri)((Parcel)object).readParcelable(classLoader);
        int n = ((Parcel)object).readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.mIsPullable = bl2;
        this.mCallState = ((Parcel)object).readInt();
        this.mCallType = ((Parcel)object).readInt();
        bl2 = ((Parcel)object).readInt() != 0 ? bl : false;
        this.mIsHeld = bl2;
        object = new StringBuilder();
        ((StringBuilder)object).append("ImsExternalCallState const = ");
        ((StringBuilder)object).append(this);
        Rlog.d(TAG, ((StringBuilder)object).toString());
    }

    public ImsExternalCallState(String charSequence, Uri uri, Uri uri2, boolean bl, int n, int n2, boolean bl2) {
        this.mCallId = this.getIdForString((String)charSequence);
        this.mAddress = uri;
        this.mLocalAddress = uri2;
        this.mIsPullable = bl;
        this.mCallState = n;
        this.mCallType = n2;
        this.mIsHeld = bl2;
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("ImsExternalCallState = ");
        ((StringBuilder)charSequence).append(this);
        Rlog.d(TAG, ((StringBuilder)charSequence).toString());
    }

    private int getIdForString(String string2) {
        try {
            int n = Integer.parseInt(string2);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            return string2.hashCode();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getCallId() {
        return this.mCallId;
    }

    public int getCallState() {
        return this.mCallState;
    }

    public int getCallType() {
        return this.mCallType;
    }

    public Uri getLocalAddress() {
        return this.mLocalAddress;
    }

    public boolean isCallHeld() {
        return this.mIsHeld;
    }

    public boolean isCallPullable() {
        return this.mIsPullable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsExternalCallState { mCallId = ");
        stringBuilder.append(this.mCallId);
        stringBuilder.append(", mAddress = ");
        stringBuilder.append(Log.pii(this.mAddress));
        stringBuilder.append(", mLocalAddress = ");
        stringBuilder.append(Log.pii(this.mLocalAddress));
        stringBuilder.append(", mIsPullable = ");
        stringBuilder.append(this.mIsPullable);
        stringBuilder.append(", mCallState = ");
        stringBuilder.append(this.mCallState);
        stringBuilder.append(", mCallType = ");
        stringBuilder.append(this.mCallType);
        stringBuilder.append(", mIsHeld = ");
        stringBuilder.append(this.mIsHeld);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mCallId);
        parcel.writeParcelable(this.mAddress, 0);
        parcel.writeParcelable(this.mLocalAddress, 0);
        parcel.writeInt((int)this.mIsPullable);
        parcel.writeInt(this.mCallState);
        parcel.writeInt(this.mCallType);
        parcel.writeInt((int)this.mIsHeld);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ImsExternalCallState writeToParcel = ");
        stringBuilder.append(parcel.toString());
        Rlog.d(TAG, stringBuilder.toString());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ExternalCallState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ExternalCallType {
    }

}

