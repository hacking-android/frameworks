/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Connection;
import android.telecom.Log;
import android.telecom.ParcelableCall;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;

public class ConferenceParticipant
implements Parcelable {
    private static final String ANONYMOUS_INVALID_HOST = "anonymous.invalid";
    public static final Parcelable.Creator<ConferenceParticipant> CREATOR = new Parcelable.Creator<ConferenceParticipant>(){

        @Override
        public ConferenceParticipant createFromParcel(Parcel object) {
            Object object2 = ParcelableCall.class.getClassLoader();
            Uri uri = (Uri)((Parcel)object).readParcelable((ClassLoader)object2);
            String string2 = ((Parcel)object).readString();
            object2 = (Uri)((Parcel)object).readParcelable((ClassLoader)object2);
            int n = ((Parcel)object).readInt();
            long l = ((Parcel)object).readLong();
            long l2 = ((Parcel)object).readLong();
            int n2 = ((Parcel)object).readInt();
            object = new ConferenceParticipant(uri, string2, (Uri)object2, n, n2);
            ((ConferenceParticipant)object).setConnectTime(l);
            ((ConferenceParticipant)object).setConnectElapsedTime(l2);
            ((ConferenceParticipant)object).setCallDirection(n2);
            return object;
        }

        public ConferenceParticipant[] newArray(int n) {
            return new ConferenceParticipant[n];
        }
    };
    private int mCallDirection;
    private long mConnectElapsedTime;
    private long mConnectTime;
    private final String mDisplayName;
    private final Uri mEndpoint;
    private final Uri mHandle;
    private final int mState;

    public ConferenceParticipant(Uri uri, String string2, Uri uri2, int n, int n2) {
        this.mHandle = uri;
        this.mDisplayName = string2;
        this.mEndpoint = uri2;
        this.mState = n;
        this.mCallDirection = n2;
    }

    @VisibleForTesting
    public static Uri getParticipantAddress(Uri object, String string2) {
        if (object == null) {
            return object;
        }
        Object object2 = object.getSchemeSpecificPart();
        if (TextUtils.isEmpty((CharSequence)object2)) {
            return object;
        }
        if (((String[])(object2 = object2.split("[@;:]"))).length == 0) {
            return object;
        }
        object2 = object2[0];
        object = null;
        if (!TextUtils.isEmpty(string2)) {
            object = PhoneNumberUtils.formatNumberToE164((String)object2, string2);
        }
        if (object == null) {
            object = object2;
        }
        return Uri.fromParts("tel", (String)object, null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getCallDirection() {
        return this.mCallDirection;
    }

    public long getConnectElapsedTime() {
        return this.mConnectElapsedTime;
    }

    public long getConnectTime() {
        return this.mConnectTime;
    }

    public String getDisplayName() {
        return this.mDisplayName;
    }

    public Uri getEndpoint() {
        return this.mEndpoint;
    }

    public Uri getHandle() {
        return this.mHandle;
    }

    @VisibleForTesting
    public int getParticipantPresentation() {
        Object object = this.getHandle();
        if (object == null) {
            return 2;
        }
        if (TextUtils.isEmpty((CharSequence)(object = object.getSchemeSpecificPart()))) {
            return 2;
        }
        if (((String[])(object = object.split("[;]")[0].split("[@]"))).length != 2) {
            return 1;
        }
        if (object[1].equals(ANONYMOUS_INVALID_HOST)) {
            return 2;
        }
        return 1;
    }

    public int getState() {
        return this.mState;
    }

    public void setCallDirection(int n) {
        this.mCallDirection = n;
    }

    public void setConnectElapsedTime(long l) {
        this.mConnectElapsedTime = l;
    }

    public void setConnectTime(long l) {
        this.mConnectTime = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ConferenceParticipant Handle: ");
        stringBuilder.append(Log.pii(this.mHandle));
        stringBuilder.append(" DisplayName: ");
        stringBuilder.append(Log.pii(this.mDisplayName));
        stringBuilder.append(" Endpoint: ");
        stringBuilder.append(Log.pii(this.mEndpoint));
        stringBuilder.append(" State: ");
        stringBuilder.append(Connection.stateToString(this.mState));
        stringBuilder.append(" ConnectTime: ");
        stringBuilder.append(this.getConnectTime());
        stringBuilder.append(" ConnectElapsedTime: ");
        stringBuilder.append(this.getConnectElapsedTime());
        stringBuilder.append(" Direction: ");
        String string2 = this.getCallDirection() == 0 ? "Incoming" : "Outgoing";
        stringBuilder.append(string2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mHandle, 0);
        parcel.writeString(this.mDisplayName);
        parcel.writeParcelable(this.mEndpoint, 0);
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mConnectTime);
        parcel.writeLong(this.mConnectElapsedTime);
        parcel.writeInt(this.mCallDirection);
    }

}

