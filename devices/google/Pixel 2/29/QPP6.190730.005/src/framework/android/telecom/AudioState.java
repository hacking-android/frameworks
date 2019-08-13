/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.CallAudioState;
import java.util.Locale;

@SystemApi
@Deprecated
public class AudioState
implements Parcelable {
    public static final Parcelable.Creator<AudioState> CREATOR = new Parcelable.Creator<AudioState>(){

        @Override
        public AudioState createFromParcel(Parcel parcel) {
            boolean bl = parcel.readByte() != 0;
            return new AudioState(bl, parcel.readInt(), parcel.readInt());
        }

        public AudioState[] newArray(int n) {
            return new AudioState[n];
        }
    };
    private static final int ROUTE_ALL = 15;
    public static final int ROUTE_BLUETOOTH = 2;
    public static final int ROUTE_EARPIECE = 1;
    public static final int ROUTE_SPEAKER = 8;
    public static final int ROUTE_WIRED_HEADSET = 4;
    public static final int ROUTE_WIRED_OR_EARPIECE = 5;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    private final boolean isMuted;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    private final int route;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=127403196L)
    private final int supportedRouteMask;

    public AudioState(AudioState audioState) {
        this.isMuted = audioState.isMuted();
        this.route = audioState.getRoute();
        this.supportedRouteMask = audioState.getSupportedRouteMask();
    }

    public AudioState(CallAudioState callAudioState) {
        this.isMuted = callAudioState.isMuted();
        this.route = callAudioState.getRoute();
        this.supportedRouteMask = callAudioState.getSupportedRouteMask();
    }

    public AudioState(boolean bl, int n, int n2) {
        this.isMuted = bl;
        this.route = n;
        this.supportedRouteMask = n2;
    }

    public static String audioRouteToString(int n) {
        if (n != 0 && (n & -16) == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            if ((n & 1) == 1) {
                AudioState.listAppend(stringBuffer, "EARPIECE");
            }
            if ((n & 2) == 2) {
                AudioState.listAppend(stringBuffer, "BLUETOOTH");
            }
            if ((n & 4) == 4) {
                AudioState.listAppend(stringBuffer, "WIRED_HEADSET");
            }
            if ((n & 8) == 8) {
                AudioState.listAppend(stringBuffer, "SPEAKER");
            }
            return stringBuffer.toString();
        }
        return "UNKNOWN";
    }

    private static void listAppend(StringBuffer stringBuffer, String string2) {
        if (stringBuffer.length() > 0) {
            stringBuffer.append(", ");
        }
        stringBuffer.append(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block2 : {
            bl = false;
            if (object == null) {
                return false;
            }
            if (!(object instanceof AudioState)) {
                return false;
            }
            object = (AudioState)object;
            if (this.isMuted() != ((AudioState)object).isMuted() || this.getRoute() != ((AudioState)object).getRoute() || this.getSupportedRouteMask() != ((AudioState)object).getSupportedRouteMask()) break block2;
            bl = true;
        }
        return bl;
    }

    public int getRoute() {
        return this.route;
    }

    public int getSupportedRouteMask() {
        return this.supportedRouteMask;
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public String toString() {
        return String.format(Locale.US, "[AudioState isMuted: %b, route: %s, supportedRouteMask: %s]", this.isMuted, AudioState.audioRouteToString(this.route), AudioState.audioRouteToString(this.supportedRouteMask));
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.isMuted ? 1 : 0));
        parcel.writeInt(this.route);
        parcel.writeInt(this.supportedRouteMask);
    }

}

