/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothAvrcpPlayerSettings;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothAvrcpController;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothAvrcpController
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.avrcp-controller.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_PLAYER_SETTING = "android.bluetooth.avrcp-controller.profile.action.PLAYER_SETTING";
    private static final boolean DBG = false;
    public static final String EXTRA_PLAYER_SETTING = "android.bluetooth.avrcp-controller.profile.extra.PLAYER_SETTING";
    private static final String TAG = "BluetoothAvrcpController";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothAvrcpController> mProfileConnector = new BluetoothProfileConnector(this, 12, "BluetoothAvrcpController", IBluetoothAvrcpController.class.getName()){

        public IBluetoothAvrcpController getServiceInterface(IBinder iBinder) {
            return IBluetoothAvrcpController.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothAvrcpController(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothAvrcpController getService() {
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

    void close() {
        this.mProfileConnector.disconnect();
    }

    public void finalize() {
        this.close();
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
        IBluetoothAvrcpController iBluetoothAvrcpController = this.getService();
        if (iBluetoothAvrcpController != null && this.isEnabled() && BluetoothAvrcpController.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothAvrcpController.getConnectionState(bluetoothDevice);
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
        if (iBluetoothAvrcpController == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothAvrcpController iBluetoothAvrcpController = this.getService();
        if (iBluetoothAvrcpController != null && this.isEnabled()) {
            try {
                object = iBluetoothAvrcpController.getDevicesMatchingConnectionStates((int[])object);
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
        if (iBluetoothAvrcpController == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public BluetoothAvrcpPlayerSettings getPlayerSettings(BluetoothDevice object) {
        BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings = null;
        IBluetoothAvrcpController iBluetoothAvrcpController = this.getService();
        BluetoothAvrcpPlayerSettings bluetoothAvrcpPlayerSettings2 = bluetoothAvrcpPlayerSettings;
        if (iBluetoothAvrcpController != null) {
            bluetoothAvrcpPlayerSettings2 = bluetoothAvrcpPlayerSettings;
            if (this.isEnabled()) {
                try {
                    bluetoothAvrcpPlayerSettings2 = iBluetoothAvrcpController.getPlayerSettings((BluetoothDevice)object);
                }
                catch (RemoteException remoteException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error talking to BT service in getMetadata() ");
                    ((StringBuilder)object).append(remoteException);
                    Log.e(TAG, ((StringBuilder)object).toString());
                    return null;
                }
            }
        }
        return bluetoothAvrcpPlayerSettings2;
    }

    public void sendGroupNavigationCmd(BluetoothDevice bluetoothDevice, int n, int n2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("sendGroupNavigationCmd dev = ");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(" key ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" State = ");
        ((StringBuilder)object).append(n2);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled()) {
            try {
                object.sendGroupNavigationCmd(bluetoothDevice, n, n2);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error talking to BT service in sendGroupNavigationCmd()", remoteException);
                return;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
    }

    public boolean setPlayerApplicationSetting(BluetoothAvrcpPlayerSettings object) {
        IBluetoothAvrcpController iBluetoothAvrcpController = this.getService();
        if (iBluetoothAvrcpController != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothAvrcpController.setPlayerApplicationSetting((BluetoothAvrcpPlayerSettings)object);
                return bl;
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error talking to BT service in setPlayerApplicationSetting() ");
                ((StringBuilder)object).append(remoteException);
                Log.e(TAG, ((StringBuilder)object).toString());
                return false;
            }
        }
        if (iBluetoothAvrcpController == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

