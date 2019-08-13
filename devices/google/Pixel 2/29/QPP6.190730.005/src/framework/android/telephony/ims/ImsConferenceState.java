/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@SystemApi
public final class ImsConferenceState
implements Parcelable {
    public static final Parcelable.Creator<ImsConferenceState> CREATOR = new Parcelable.Creator<ImsConferenceState>(){

        @Override
        public ImsConferenceState createFromParcel(Parcel parcel) {
            return new ImsConferenceState(parcel);
        }

        public ImsConferenceState[] newArray(int n) {
            return new ImsConferenceState[n];
        }
    };
    public static final String DISPLAY_TEXT = "display-text";
    public static final String ENDPOINT = "endpoint";
    public static final String SIP_STATUS_CODE = "sipstatuscode";
    public static final String STATUS = "status";
    public static final String STATUS_ALERTING = "alerting";
    public static final String STATUS_CONNECTED = "connected";
    public static final String STATUS_CONNECT_FAIL = "connect-fail";
    public static final String STATUS_DIALING_IN = "dialing-in";
    public static final String STATUS_DIALING_OUT = "dialing-out";
    public static final String STATUS_DISCONNECTED = "disconnected";
    public static final String STATUS_DISCONNECTING = "disconnecting";
    public static final String STATUS_MUTED_VIA_FOCUS = "muted-via-focus";
    public static final String STATUS_ON_HOLD = "on-hold";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_SEND_ONLY = "sendonly";
    public static final String STATUS_SEND_RECV = "sendrecv";
    public static final String USER = "user";
    public final HashMap<String, Bundle> mParticipants = new HashMap();

    public ImsConferenceState() {
    }

    private ImsConferenceState(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static int getConnectionStateForStatus(String string2) {
        if (string2.equals(STATUS_PENDING)) {
            return 0;
        }
        if (string2.equals(STATUS_DIALING_IN)) {
            return 2;
        }
        if (!string2.equals(STATUS_ALERTING) && !string2.equals(STATUS_DIALING_OUT)) {
            if (!string2.equals(STATUS_ON_HOLD) && !string2.equals(STATUS_SEND_ONLY)) {
                if (!(string2.equals(STATUS_CONNECTED) || string2.equals(STATUS_MUTED_VIA_FOCUS) || string2.equals(STATUS_DISCONNECTING) || string2.equals(STATUS_SEND_RECV))) {
                    if (string2.equals(STATUS_DISCONNECTED)) {
                        return 6;
                    }
                    return 4;
                }
                return 4;
            }
            return 5;
        }
        return 3;
    }

    private void readFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            String string2 = parcel.readString();
            Bundle bundle = (Bundle)parcel.readParcelable(null);
            this.mParticipants.put(string2, bundle);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(ImsConferenceState.class.getSimpleName());
        stringBuilder.append(" ");
        if (this.mParticipants.size() > 0 && (object = this.mParticipants.entrySet()) != null) {
            object = object.iterator();
            stringBuilder.append("<");
            while (object.hasNext()) {
                Object object2 = (Map.Entry)object.next();
                stringBuilder.append(Log.pii(object2.getKey()));
                stringBuilder.append(": ");
                object2 = (Bundle)object2.getValue();
                for (String string2 : ((BaseBundle)object2).keySet()) {
                    stringBuilder.append(string2);
                    stringBuilder.append("=");
                    if (!ENDPOINT.equals(string2) && !USER.equals(string2)) {
                        stringBuilder.append(((BaseBundle)object2).get(string2));
                    } else {
                        stringBuilder.append(Log.pii(((BaseBundle)object2).get(string2)));
                    }
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(">");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Object object;
        parcel.writeInt(this.mParticipants.size());
        if (this.mParticipants.size() > 0 && (object = this.mParticipants.entrySet()) != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                parcel.writeString((String)entry.getKey());
                parcel.writeParcelable((Parcelable)entry.getValue(), 0);
            }
        }
    }

}

