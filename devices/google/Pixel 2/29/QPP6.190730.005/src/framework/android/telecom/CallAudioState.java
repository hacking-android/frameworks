/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.-$
 *  android.telecom.-$$Lambda
 *  android.telecom.-$$Lambda$cyYWqCYT05eM23eLVm4oQ5DrYjw
 */
package android.telecom;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;
import android.telecom.-$;
import android.telecom.AudioState;
import android.telecom._$$Lambda$cyYWqCYT05eM23eLVm4oQ5DrYjw;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class CallAudioState
implements Parcelable {
    public static final Parcelable.Creator<CallAudioState> CREATOR = new Parcelable.Creator<CallAudioState>(){

        @Override
        public CallAudioState createFromParcel(Parcel parcel) {
            boolean bl = parcel.readByte() != 0;
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            BluetoothDevice bluetoothDevice = (BluetoothDevice)parcel.readParcelable(ClassLoader.getSystemClassLoader());
            ArrayList<BluetoothDevice> arrayList = new ArrayList<BluetoothDevice>();
            parcel.readParcelableList(arrayList, ClassLoader.getSystemClassLoader());
            return new CallAudioState(bl, n, n2, bluetoothDevice, arrayList);
        }

        public CallAudioState[] newArray(int n) {
            return new CallAudioState[n];
        }
    };
    public static final int ROUTE_ALL = 15;
    public static final int ROUTE_BLUETOOTH = 2;
    public static final int ROUTE_EARPIECE = 1;
    public static final int ROUTE_SPEAKER = 8;
    public static final int ROUTE_WIRED_HEADSET = 4;
    public static final int ROUTE_WIRED_OR_EARPIECE = 5;
    private final BluetoothDevice activeBluetoothDevice;
    private final boolean isMuted;
    private final int route;
    private final Collection<BluetoothDevice> supportedBluetoothDevices;
    private final int supportedRouteMask;

    public CallAudioState(AudioState audioState) {
        this.isMuted = audioState.isMuted();
        this.route = audioState.getRoute();
        this.supportedRouteMask = audioState.getSupportedRouteMask();
        this.activeBluetoothDevice = null;
        this.supportedBluetoothDevices = Collections.emptyList();
    }

    public CallAudioState(CallAudioState callAudioState) {
        this.isMuted = callAudioState.isMuted();
        this.route = callAudioState.getRoute();
        this.supportedRouteMask = callAudioState.getSupportedRouteMask();
        this.activeBluetoothDevice = callAudioState.activeBluetoothDevice;
        this.supportedBluetoothDevices = callAudioState.getSupportedBluetoothDevices();
    }

    public CallAudioState(boolean bl, int n, int n2) {
        this(bl, n, n2, null, Collections.emptyList());
    }

    public CallAudioState(boolean bl, int n, int n2, BluetoothDevice bluetoothDevice, Collection<BluetoothDevice> collection) {
        this.isMuted = bl;
        this.route = n;
        this.supportedRouteMask = n2;
        this.activeBluetoothDevice = bluetoothDevice;
        this.supportedBluetoothDevices = collection;
    }

    public static String audioRouteToString(int n) {
        if (n != 0 && (n & -16) == 0) {
            StringBuffer stringBuffer = new StringBuffer();
            if ((n & 1) == 1) {
                CallAudioState.listAppend(stringBuffer, "EARPIECE");
            }
            if ((n & 2) == 2) {
                CallAudioState.listAppend(stringBuffer, "BLUETOOTH");
            }
            if ((n & 4) == 4) {
                CallAudioState.listAppend(stringBuffer, "WIRED_HEADSET");
            }
            if ((n & 8) == 8) {
                CallAudioState.listAppend(stringBuffer, "SPEAKER");
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

    public boolean equals(Object object2) {
        boolean bl;
        block4 : {
            bl = false;
            if (object2 == null) {
                return false;
            }
            if (!(object2 instanceof CallAudioState)) {
                return false;
            }
            CallAudioState callAudioState = (CallAudioState)object2;
            if (this.supportedBluetoothDevices.size() != callAudioState.supportedBluetoothDevices.size()) {
                return false;
            }
            for (BluetoothDevice bluetoothDevice : this.supportedBluetoothDevices) {
                if (callAudioState.supportedBluetoothDevices.contains(bluetoothDevice)) continue;
                return false;
            }
            if (!Objects.equals(this.activeBluetoothDevice, callAudioState.activeBluetoothDevice) || this.isMuted() != callAudioState.isMuted() || this.getRoute() != callAudioState.getRoute() || this.getSupportedRouteMask() != callAudioState.getSupportedRouteMask()) break block4;
            bl = true;
        }
        return bl;
    }

    public BluetoothDevice getActiveBluetoothDevice() {
        return this.activeBluetoothDevice;
    }

    public int getRoute() {
        return this.route;
    }

    public Collection<BluetoothDevice> getSupportedBluetoothDevices() {
        return this.supportedBluetoothDevices;
    }

    public int getSupportedRouteMask() {
        return this.supportedRouteMask;
    }

    public boolean isMuted() {
        return this.isMuted;
    }

    public String toString() {
        String string2 = this.supportedBluetoothDevices.stream().map(_$$Lambda$cyYWqCYT05eM23eLVm4oQ5DrYjw.INSTANCE).collect(Collectors.joining(", "));
        return String.format(Locale.US, "[AudioState isMuted: %b, route: %s, supportedRouteMask: %s, activeBluetoothDevice: [%s], supportedBluetoothDevices: [%s]]", this.isMuted, CallAudioState.audioRouteToString(this.route), CallAudioState.audioRouteToString(this.supportedRouteMask), this.activeBluetoothDevice, string2);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.isMuted ? 1 : 0));
        parcel.writeInt(this.route);
        parcel.writeInt(this.supportedRouteMask);
        parcel.writeParcelable(this.activeBluetoothDevice, 0);
        parcel.writeParcelableList(new ArrayList<BluetoothDevice>(this.supportedBluetoothDevices), 0);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CallAudioRoute {
    }

}

