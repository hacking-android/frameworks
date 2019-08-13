/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAudioConfig;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothA2dpSink;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothA2dpSink
implements BluetoothProfile {
    public static final String ACTION_AUDIO_CONFIG_CHANGED = "android.bluetooth.a2dp-sink.profile.action.AUDIO_CONFIG_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.a2dp-sink.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_PLAYING_STATE_CHANGED = "android.bluetooth.a2dp-sink.profile.action.PLAYING_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final String EXTRA_AUDIO_CONFIG = "android.bluetooth.a2dp-sink.profile.extra.AUDIO_CONFIG";
    public static final int STATE_NOT_PLAYING = 11;
    public static final int STATE_PLAYING = 10;
    private static final String TAG = "BluetoothA2dpSink";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothA2dpSink> mProfileConnector = new BluetoothProfileConnector(this, 11, "BluetoothA2dpSink", IBluetoothA2dpSink.class.getName()){

        public IBluetoothA2dpSink getServiceInterface(IBinder iBinder) {
            return IBluetoothA2dpSink.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothA2dpSink(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothA2dpSink getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        boolean bl = this.mAdapter.getState() == 12;
        return bl;
    }

    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    public static String stateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 10) {
                            if (n != 11) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("<unknown state ");
                                stringBuilder.append(n);
                                stringBuilder.append(">");
                                return stringBuilder.toString();
                            }
                            return "not playing";
                        }
                        return "playing";
                    }
                    return "disconnecting";
                }
                return "connected";
            }
            return "connecting";
        }
        return "disconnected";
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("connect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothA2dpSink.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.connect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("disconnect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothA2dpSink.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.disconnect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public void finalize() {
        this.close();
    }

    public BluetoothAudioConfig getAudioConfig(BluetoothDevice parcelable) {
        IBluetoothA2dpSink iBluetoothA2dpSink = this.getService();
        if (iBluetoothA2dpSink != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(parcelable)) {
            try {
                parcelable = iBluetoothA2dpSink.getAudioConfig((BluetoothDevice)parcelable);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return null;
            }
        }
        if (iBluetoothA2dpSink == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Object object = this.getService();
        if (object != null && this.isEnabled()) {
            try {
                object = object.getConnectedDevices();
                return object;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dpSink iBluetoothA2dpSink = this.getService();
        if (iBluetoothA2dpSink != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothA2dpSink.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return 0;
            }
        }
        if (iBluetoothA2dpSink == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothA2dpSink iBluetoothA2dpSink = this.getService();
        if (iBluetoothA2dpSink != null && this.isEnabled()) {
            try {
                object = iBluetoothA2dpSink.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothA2dpSink == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dpSink iBluetoothA2dpSink = this.getService();
        if (iBluetoothA2dpSink != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothA2dpSink.getPriority(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return 0;
            }
        }
        if (iBluetoothA2dpSink == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean isA2dpPlaying(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dpSink iBluetoothA2dpSink = this.getService();
        if (iBluetoothA2dpSink != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothA2dpSink.isA2dpPlaying(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        if (iBluetoothA2dpSink == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setPriority(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        BluetoothA2dpSink.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothA2dpSink.isValidDevice(bluetoothDevice)) {
            if (n != 0 && n != 100) {
                return false;
            }
            try {
                boolean bl = object.setPriority(bluetoothDevice, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

