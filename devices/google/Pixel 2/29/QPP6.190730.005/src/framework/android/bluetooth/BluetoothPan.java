/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothPan;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothPan
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.pan.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final String EXTRA_LOCAL_ROLE = "android.bluetooth.pan.extra.LOCAL_ROLE";
    public static final int LOCAL_NAP_ROLE = 1;
    public static final int LOCAL_PANU_ROLE = 2;
    public static final int PAN_CONNECT_FAILED_ALREADY_CONNECTED = 1001;
    public static final int PAN_CONNECT_FAILED_ATTEMPT_FAILED = 1002;
    public static final int PAN_DISCONNECT_FAILED_NOT_CONNECTED = 1000;
    public static final int PAN_OPERATION_GENERIC_FAILURE = 1003;
    public static final int PAN_OPERATION_SUCCESS = 1004;
    public static final int PAN_ROLE_NONE = 0;
    public static final int REMOTE_NAP_ROLE = 1;
    public static final int REMOTE_PANU_ROLE = 2;
    private static final String TAG = "BluetoothPan";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothPan> mProfileConnector = new BluetoothProfileConnector(this, 5, "BluetoothPan", IBluetoothPan.class.getName()){

        public IBluetoothPan getServiceInterface(IBinder iBinder) {
            return IBluetoothPan.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    @UnsupportedAppUsage
    BluetoothPan(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothPan getService() {
        return this.mProfileConnector.getService();
    }

    @UnsupportedAppUsage
    private boolean isEnabled() {
        boolean bl = this.mAdapter.getState() == 12;
        return bl;
    }

    @UnsupportedAppUsage
    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    @UnsupportedAppUsage
    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    @UnsupportedAppUsage
    void close() {
        this.mProfileConnector.disconnect();
    }

    @UnsupportedAppUsage
    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("connect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothPan.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothPan.isValidDevice(bluetoothDevice)) {
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
        BluetoothPan.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothPan.isValidDevice(bluetoothDevice)) {
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

    protected void finalize() {
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
        IBluetoothPan iBluetoothPan = this.getService();
        if (iBluetoothPan != null && this.isEnabled() && BluetoothPan.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothPan.getConnectionState(bluetoothDevice);
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
        if (iBluetoothPan == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothPan iBluetoothPan = this.getService();
        if (iBluetoothPan != null && this.isEnabled()) {
            try {
                object = iBluetoothPan.getDevicesMatchingConnectionStates((int[])object);
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
        if (iBluetoothPan == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    @UnsupportedAppUsage
    public boolean isTetheringOn() {
        IBluetoothPan iBluetoothPan = this.getService();
        if (iBluetoothPan != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothPan.isTetheringOn();
                return bl;
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    public void setBluetoothTethering(boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setBluetoothTethering(");
        ((StringBuilder)object).append(bl);
        ((StringBuilder)object).append(")");
        BluetoothPan.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled()) {
            try {
                object.setBluetoothTethering(bl);
            }
            catch (RemoteException remoteException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Stack:");
                stringBuilder.append(Log.getStackTraceString(new Throwable()));
                Log.e(TAG, stringBuilder.toString());
            }
        }
    }

}

