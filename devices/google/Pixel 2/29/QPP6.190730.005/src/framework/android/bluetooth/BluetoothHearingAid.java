/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothHearingAid;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothHearingAid
implements BluetoothProfile {
    @UnsupportedAppUsage
    public static final String ACTION_ACTIVE_DEVICE_CHANGED = "android.bluetooth.hearingaid.profile.action.ACTIVE_DEVICE_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.hearingaid.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final long HI_SYNC_ID_INVALID = 0L;
    public static final int MODE_BINAURAL = 1;
    public static final int MODE_MONAURAL = 0;
    public static final int SIDE_LEFT = 0;
    public static final int SIDE_RIGHT = 1;
    private static final String TAG = "BluetoothHearingAid";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothHearingAid> mProfileConnector = new BluetoothProfileConnector(this, 21, "BluetoothHearingAid", IBluetoothHearingAid.class.getName()){

        public IBluetoothHearingAid getServiceInterface(IBinder iBinder) {
            return IBluetoothHearingAid.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothHearingAid(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothHearingAid getService() {
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

    public static String stateToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("<unknown state ");
                        stringBuilder.append(n);
                        stringBuilder.append(">");
                        return stringBuilder.toString();
                    }
                    return "disconnecting";
                }
                return "connected";
            }
            return "connecting";
        }
        return "disconnected";
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void adjustVolume(int var1_1) {
        var2_2 = new StringBuilder();
        var2_2.append("adjustVolume(");
        var2_2.append(var1_1);
        var2_2.append(")");
        BluetoothHearingAid.log(var2_2.toString());
        var2_2 = this.getService();
        if (var2_2 != null) ** GOTO lbl15
        try {
            Log.w("BluetoothHearingAid", "Proxy not attached to service");
            return;
lbl15: // 1 sources:
            if (!this.isEnabled()) {
                return;
            }
            var2_2.adjustVolume(var1_1);
            return;
        }
        catch (RemoteException var2_3) {
            var2_4 = new StringBuilder();
            var2_4.append("Stack:");
            var2_4.append(Log.getStackTraceString(new Throwable()));
            Log.e("BluetoothHearingAid", var2_4.toString());
        }
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean connect(BluetoothDevice bluetoothDevice) {
        block4 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("connect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothHearingAid.log(((StringBuilder)object).toString());
            object = this.getService();
            if (object != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return object.connect(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (object != null) return false;
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        block4 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("disconnect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothHearingAid.log(((StringBuilder)object).toString());
            object = this.getService();
            if (object != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return object.disconnect(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (object != null) return false;
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public List<BluetoothDevice> getActiveDevices() {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled()) {
                        return iBluetoothHearingAid.getActiveDevices();
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return new ArrayList();
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return new ArrayList<BluetoothDevice>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled()) {
                        return iBluetoothHearingAid.getConnectedDevices();
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return new ArrayList();
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return new ArrayList<BluetoothDevice>();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return iBluetoothHearingAid.getConnectionState(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return 0;
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getDeviceMode(BluetoothDevice bluetoothDevice) {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return iBluetoothHearingAid.getDeviceMode(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return 0;
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getDeviceSide(BluetoothDevice bluetoothDevice) {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return iBluetoothHearingAid.getDeviceSide(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return 0;
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled()) {
                        return iBluetoothHearingAid.getDevicesMatchingConnectionStates((int[])object);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return new ArrayList();
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return new ArrayList<BluetoothDevice>();
    }

    public long getHiSyncId(BluetoothDevice bluetoothDevice) {
        IBluetoothHearingAid iBluetoothHearingAid = this.getService();
        if (iBluetoothHearingAid == null) {
            Log.w(TAG, "Proxy not attached to service");
            return 0L;
        }
        try {
            if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                long l = iBluetoothHearingAid.getHiSyncId(bluetoothDevice);
                return l;
            }
            return 0L;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Stack:");
            stringBuilder.append(Log.getStackTraceString(new Throwable()));
            Log.e(TAG, stringBuilder.toString());
            return 0L;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getPriority(BluetoothDevice bluetoothDevice) {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        return iBluetoothHearingAid.getPriority(bluetoothDevice);
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return 0;
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getVolume() {
        block4 : {
            IBluetoothHearingAid iBluetoothHearingAid = this.getService();
            if (iBluetoothHearingAid != null) {
                try {
                    if (this.isEnabled()) {
                        return iBluetoothHearingAid.getVolume();
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (iBluetoothHearingAid != null) return 0;
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        block4 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("setActiveDevice(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            BluetoothHearingAid.log(((StringBuilder)object).toString());
            object = this.getService();
            if (object != null) {
                try {
                    if (this.isEnabled() && (bluetoothDevice == null || this.isValidDevice(bluetoothDevice))) {
                        object.setActiveDevice(bluetoothDevice);
                        return true;
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (object != null) return false;
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        block4 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("setPriority(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(")");
            BluetoothHearingAid.log(((StringBuilder)object).toString());
            object = this.getService();
            if (object != null) {
                try {
                    if (this.isEnabled() && this.isValidDevice(bluetoothDevice)) {
                        if (n == 0) return object.setPriority(bluetoothDevice, n);
                        if (n == 100) return object.setPriority(bluetoothDevice, n);
                        return false;
                    }
                }
                catch (RemoteException remoteException) {
                    break block4;
                }
            }
            if (object != null) return false;
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Stack:");
        stringBuilder.append(Log.getStackTraceString(new Throwable()));
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setVolume(int var1_1) {
        var2_2 = new StringBuilder();
        var2_2.append("setVolume(");
        var2_2.append(var1_1);
        var2_2.append(")");
        Log.d("BluetoothHearingAid", var2_2.toString());
        var2_2 = this.getService();
        if (var2_2 != null) ** GOTO lbl16
        try {
            Log.w("BluetoothHearingAid", "Proxy not attached to service");
            return;
lbl16: // 1 sources:
            if (!this.isEnabled()) {
                return;
            }
            var2_2.setVolume(var1_1);
            return;
        }
        catch (RemoteException var2_3) {
            var2_4 = new StringBuilder();
            var2_4.append("Stack:");
            var2_4.append(Log.getStackTraceString(new Throwable()));
            Log.e("BluetoothHearingAid", var2_4.toString());
        }
    }

}

