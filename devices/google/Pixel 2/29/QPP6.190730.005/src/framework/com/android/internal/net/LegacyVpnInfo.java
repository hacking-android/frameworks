/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.net;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.net.NetworkInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class LegacyVpnInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<LegacyVpnInfo> CREATOR = new Parcelable.Creator<LegacyVpnInfo>(){

        @Override
        public LegacyVpnInfo createFromParcel(Parcel parcel) {
            LegacyVpnInfo legacyVpnInfo = new LegacyVpnInfo();
            legacyVpnInfo.key = parcel.readString();
            legacyVpnInfo.state = parcel.readInt();
            legacyVpnInfo.intent = (PendingIntent)parcel.readParcelable(null);
            return legacyVpnInfo;
        }

        public LegacyVpnInfo[] newArray(int n) {
            return new LegacyVpnInfo[n];
        }
    };
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_FAILED = 5;
    public static final int STATE_INITIALIZING = 1;
    public static final int STATE_TIMEOUT = 4;
    private static final String TAG = "LegacyVpnInfo";
    public PendingIntent intent;
    @UnsupportedAppUsage
    public String key;
    @UnsupportedAppUsage
    public int state = -1;

    public static int stateFromNetworkInfo(NetworkInfo networkInfo) {
        int n = 2.$SwitchMap$android$net$NetworkInfo$DetailedState[networkInfo.getDetailedState().ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unhandled state ");
                        stringBuilder.append((Object)networkInfo.getDetailedState());
                        stringBuilder.append(" ; treating as disconnected");
                        Log.w(TAG, stringBuilder.toString());
                        return 0;
                    }
                    return 5;
                }
                return 0;
            }
            return 3;
        }
        return 2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.key);
        parcel.writeInt(this.state);
        parcel.writeParcelable(this.intent, n);
    }

}

