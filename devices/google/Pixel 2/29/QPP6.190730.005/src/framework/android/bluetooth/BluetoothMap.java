/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothMap;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothMap
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.map.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = true;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private static final String TAG = "BluetoothMap";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter;
    private final BluetoothProfileConnector<IBluetoothMap> mProfileConnector = new BluetoothProfileConnector(this, 9, "BluetoothMap", IBluetoothMap.class.getName()){

        public IBluetoothMap getServiceInterface(IBinder iBinder) {
            return IBluetoothMap.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothMap(Context context, BluetoothProfile.ServiceListener serviceListener) {
        Log.d(TAG, "Create BluetoothMap proxy object");
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnector.connect(context, serviceListener);
    }

    public static boolean doesClassMatchSink(BluetoothClass bluetoothClass) {
        int n = bluetoothClass.getDeviceClass();
        return n == 256 || n == 260 || n == 264 || n == 268;
    }

    private IBluetoothMap getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && bluetoothAdapter.getState() == 12) {
            return true;
        }
        BluetoothMap.log("Bluetooth is Not enabled");
        return false;
    }

    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    public void close() {
        synchronized (this) {
            this.mProfileConnector.disconnect();
            return;
        }
    }

    public boolean connect(BluetoothDevice bluetoothDevice) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("connect(");
        stringBuilder.append(bluetoothDevice);
        stringBuilder.append(")not supported for MAPS");
        BluetoothMap.log(stringBuilder.toString());
        return false;
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("disconnect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothMap.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothMap.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.disconnect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    protected void finalize() throws Throwable {
        try {
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public BluetoothDevice getClient() {
        Object object = this.getService();
        if (object != null) {
            try {
                object = object.getClient();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            BluetoothMap.log(Log.getStackTraceString(new Throwable()));
        }
        return null;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        BluetoothMap.log("getConnectedDevices()");
        Object object = this.getService();
        if (object != null && this.isEnabled()) {
            try {
                object = object.getConnectedDevices();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
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
        Object object = new StringBuilder();
        ((StringBuilder)object).append("getConnectionState(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothMap.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothMap.isValidDevice(bluetoothDevice)) {
            try {
                int n = object.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        BluetoothMap.log("getDevicesMatchingStates()");
        IBluetoothMap iBluetoothMap = this.getService();
        if (iBluetoothMap != null && this.isEnabled()) {
            try {
                object = iBluetoothMap.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothMap == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothMap iBluetoothMap = this.getService();
        if (iBluetoothMap != null && this.isEnabled() && BluetoothMap.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothMap.getPriority(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothMap == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public int getState() {
        IBluetoothMap iBluetoothMap = this.getService();
        if (iBluetoothMap != null) {
            try {
                int n = iBluetoothMap.getState();
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            BluetoothMap.log(Log.getStackTraceString(new Throwable()));
        }
        return -1;
    }

    public boolean isConnected(BluetoothDevice bluetoothDevice) {
        IBluetoothMap iBluetoothMap = this.getService();
        if (iBluetoothMap != null) {
            try {
                boolean bl = iBluetoothMap.isConnected(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            BluetoothMap.log(Log.getStackTraceString(new Throwable()));
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
        BluetoothMap.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothMap.isValidDevice(bluetoothDevice)) {
            if (n != 0 && n != 100) {
                return false;
            }
            try {
                boolean bl = object.setPriority(bluetoothDevice, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

