/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothCodecConfig;
import android.bluetooth.BluetoothCodecStatus;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.BluetoothUuid;
import android.bluetooth.IBluetoothA2dp;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothA2dp
implements BluetoothProfile {
    @UnsupportedAppUsage
    public static final String ACTION_ACTIVE_DEVICE_CHANGED = "android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED";
    public static final String ACTION_AVRCP_CONNECTION_STATE_CHANGED = "android.bluetooth.a2dp.profile.action.AVRCP_CONNECTION_STATE_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_CODEC_CONFIG_CHANGED = "android.bluetooth.a2dp.profile.action.CODEC_CONFIG_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.a2dp.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_PLAYING_STATE_CHANGED = "android.bluetooth.a2dp.profile.action.PLAYING_STATE_CHANGED";
    private static final boolean DBG = true;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_NOT_SUPPORTED = 0;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_PREF_DISABLED = 0;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_PREF_ENABLED = 1;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_PREF_UNKNOWN = -1;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_SUPPORTED = 1;
    @UnsupportedAppUsage
    public static final int OPTIONAL_CODECS_SUPPORT_UNKNOWN = -1;
    public static final int STATE_NOT_PLAYING = 11;
    public static final int STATE_PLAYING = 10;
    private static final String TAG = "BluetoothA2dp";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothA2dp> mProfileConnector = new BluetoothProfileConnector(this, 2, "BluetoothA2dp", IBluetoothA2dp.class.getName()){

        public IBluetoothA2dp getServiceInterface(IBinder iBinder) {
            return IBluetoothA2dp.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothA2dp(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void enableDisableOptionalCodecs(BluetoothDevice bluetoothDevice, boolean bl) {
        try {
            IBluetoothA2dp iBluetoothA2dp = this.getService();
            if (iBluetoothA2dp != null && this.isEnabled()) {
                if (bl) {
                    iBluetoothA2dp.enableOptionalCodecs(bluetoothDevice);
                } else {
                    iBluetoothA2dp.disableOptionalCodecs(bluetoothDevice);
                }
            }
            if (iBluetoothA2dp == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error talking to BT service in enableDisableOptionalCodecs()", remoteException);
            return;
        }
    }

    private IBluetoothA2dp getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        return this.mAdapter.getState() == 12;
    }

    private boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice == null) {
            return false;
        }
        return BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
    }

    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
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

    @UnsupportedAppUsage
    void close() {
        this.mProfileConnector.disconnect();
    }

    @UnsupportedAppUsage
    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object;
        block5 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("connect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothA2dp.log(((StringBuilder)object).toString());
            try {
                object = this.getService();
                if (object == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return object.connect(bluetoothDevice);
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public void disableOptionalCodecs(BluetoothDevice bluetoothDevice) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("disableOptionalCodecs(");
        stringBuilder.append(bluetoothDevice);
        stringBuilder.append(")");
        Log.d(TAG, stringBuilder.toString());
        this.enableDisableOptionalCodecs(bluetoothDevice, false);
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object;
        block5 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("disconnect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothA2dp.log(((StringBuilder)object).toString());
            try {
                object = this.getService();
                if (object == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return object.disconnect(bluetoothDevice);
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public void enableOptionalCodecs(BluetoothDevice bluetoothDevice) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("enableOptionalCodecs(");
        stringBuilder.append(bluetoothDevice);
        stringBuilder.append(")");
        Log.d(TAG, stringBuilder.toString());
        this.enableDisableOptionalCodecs(bluetoothDevice, true);
    }

    public void finalize() {
    }

    @UnsupportedAppUsage
    public BluetoothDevice getActiveDevice() {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return null;
            }
            if (!this.isEnabled()) break block5;
            return iBluetoothA2dp.getActiveDevice();
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    @UnsupportedAppUsage
    public BluetoothCodecStatus getCodecStatus(BluetoothDevice bluetoothDevice) {
        Object object;
        block5 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("getCodecStatus(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
            try {
                object = this.getService();
                if (object == null) break block5;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in getCodecStatus()", remoteException);
                return null;
            }
            if (!this.isEnabled()) break block5;
            return object.getCodecStatus(bluetoothDevice);
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        IBluetoothA2dp iBluetoothA2dp;
        block4 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block4;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return new ArrayList<BluetoothDevice>();
            }
            if (!this.isEnabled()) break block4;
            return iBluetoothA2dp.getConnectedDevices();
        }
        if (iBluetoothA2dp != null) return new ArrayList();
        Log.w(TAG, "Proxy not attached to service");
        return new ArrayList();
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return 0;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return iBluetoothA2dp.getConnectionState(bluetoothDevice);
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothA2dp iBluetoothA2dp;
        block4 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block4;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return new ArrayList<BluetoothDevice>();
            }
            if (!this.isEnabled()) break block4;
            return iBluetoothA2dp.getDevicesMatchingConnectionStates((int[])object);
        }
        if (iBluetoothA2dp != null) return new ArrayList<BluetoothDevice>();
        Log.w(TAG, "Proxy not attached to service");
        return new ArrayList<BluetoothDevice>();
    }

    @UnsupportedAppUsage
    public int getOptionalCodecsEnabled(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in getSupportsOptionalCodecs()", remoteException);
                return -1;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return iBluetoothA2dp.getOptionalCodecsEnabled(bluetoothDevice);
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return -1;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return 0;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return iBluetoothA2dp.getPriority(bluetoothDevice);
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean isA2dpPlaying(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return iBluetoothA2dp.isA2dpPlaying(bluetoothDevice);
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean isAvrcpAbsoluteVolumeSupported() {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            Log.d(TAG, "isAvrcpAbsoluteVolumeSupported");
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in isAvrcpAbsoluteVolumeSupported()", remoteException);
                return false;
            }
            if (!this.isEnabled()) break block5;
            return iBluetoothA2dp.isAvrcpAbsoluteVolumeSupported();
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        Object object;
        block5 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("setActiveDevice(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothA2dp.log(((StringBuilder)object).toString());
            try {
                object = this.getService();
                if (object == null) break block5;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
            if (!this.isEnabled()) break block5;
            if (bluetoothDevice == null) return object.setActiveDevice(bluetoothDevice);
            if (!this.isValidDevice(bluetoothDevice)) break block5;
            return object.setActiveDevice(bluetoothDevice);
        }
        if (object != null) return false;
        Log.w(TAG, "Proxy not attached to service");
        return false;
    }

    public void setAvrcpAbsoluteVolume(int n) {
        block5 : {
            IBluetoothA2dp iBluetoothA2dp;
            block4 : {
                Log.d(TAG, "setAvrcpAbsoluteVolume");
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block4;
                if (!this.isEnabled()) break block4;
                iBluetoothA2dp.setAvrcpAbsoluteVolume(n);
            }
            if (iBluetoothA2dp != null) break block5;
            try {
                Log.w(TAG, "Proxy not attached to service");
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in setAvrcpAbsoluteVolume()", remoteException);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setCodecConfigPreference(BluetoothDevice bluetoothDevice, BluetoothCodecConfig bluetoothCodecConfig) {
        Object object;
        block4 : {
            object = new StringBuilder();
            ((StringBuilder)object).append("setCodecConfigPreference(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
            try {
                object = this.getService();
                if (object == null) break block4;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in setCodecConfigPreference()", remoteException);
                return;
            }
            if (!this.isEnabled()) break block4;
            object.setCodecConfigPreference(bluetoothDevice, bluetoothCodecConfig);
        }
        if (object != null) return;
        Log.w(TAG, "Proxy not attached to service");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public void setOptionalCodecsEnabled(BluetoothDevice var1_1, int var2_4) {
        if (var2_4 == -1 || var2_4 == 0 || var2_4 == 1) ** GOTO lbl11
        try {
            var1_1 = new StringBuilder();
            var1_1.append("Invalid value passed to setOptionalCodecsEnabled: ");
            var1_1.append(var2_4);
            Log.e("BluetoothA2dp", var1_1.toString());
            return;
lbl11: // 1 sources:
            var3_5 = this.getService();
            if (var3_5 != null && this.isEnabled() && this.isValidDevice((BluetoothDevice)var1_1)) {
                var3_5.setOptionalCodecsEnabled((BluetoothDevice)var1_1, var2_4);
            }
            if (var3_5 != null) return;
            Log.w("BluetoothA2dp", "Proxy not attached to service");
            return;
        }
        catch (RemoteException var1_2) {
            var1_3 = new StringBuilder();
            var1_3.append("Stack:");
            var1_3.append(Log.getStackTraceString(new Throwable()));
            Log.e("BluetoothA2dp", var1_3.toString());
            return;
        }
    }

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object;
        block6 : {
            block7 : {
                object = new StringBuilder();
                ((StringBuilder)object).append("setPriority(");
                ((StringBuilder)object).append(bluetoothDevice);
                ((StringBuilder)object).append(", ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(")");
                BluetoothA2dp.log(((StringBuilder)object).toString());
                try {
                    object = this.getService();
                    if (object == null) break block6;
                }
                catch (RemoteException remoteException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Stack:");
                    stringBuilder.append(Log.getStackTraceString(new Throwable()));
                    Log.e(TAG, stringBuilder.toString());
                    return false;
                }
                if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block6;
                if (n == 0 || n == 100) break block7;
                return false;
            }
            return object.setPriority(bluetoothDevice, n);
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean shouldSendVolumeKeys(BluetoothDevice arrparcelUuid) {
        if (this.isEnabled() && this.isValidDevice((BluetoothDevice)arrparcelUuid)) {
            if ((arrparcelUuid = arrparcelUuid.getUuids()) == null) {
                return false;
            }
            int n = arrparcelUuid.length;
            for (int i = 0; i < n; ++i) {
                if (!BluetoothUuid.isAvrcpTarget(arrparcelUuid[i])) continue;
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public int supportsOptionalCodecs(BluetoothDevice bluetoothDevice) {
        IBluetoothA2dp iBluetoothA2dp;
        block5 : {
            try {
                iBluetoothA2dp = this.getService();
                if (iBluetoothA2dp == null) break block5;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in getSupportsOptionalCodecs()", remoteException);
                return -1;
            }
            if (!this.isEnabled() || !this.isValidDevice(bluetoothDevice)) break block5;
            return iBluetoothA2dp.supportsOptionalCodecs(bluetoothDevice);
        }
        if (iBluetoothA2dp == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return -1;
    }

}

