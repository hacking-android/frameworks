/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothPbapClient;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothPbapClient
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.pbapclient.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private static final String TAG = "BluetoothPbapClient";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothPbapClient> mProfileConnector = new BluetoothProfileConnector(this, 17, "BluetoothPbapClient", IBluetoothPbapClient.class.getName()){

        public IBluetoothPbapClient getServiceInterface(IBinder iBinder) {
            return IBluetoothPbapClient.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothPbapClient(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothPbapClient getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && bluetoothAdapter.getState() == 12) {
            return true;
        }
        BluetoothPbapClient.log("Bluetooth is Not enabled");
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
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled() && BluetoothPbapClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothPbapClient.connect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (iBluetoothPbapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled() && BluetoothPbapClient.isValidDevice(bluetoothDevice)) {
            try {
                iBluetoothPbapClient.disconnect(bluetoothDevice);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (iBluetoothPbapClient == null) {
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

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
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
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled() && BluetoothPbapClient.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothPbapClient.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothPbapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled()) {
            try {
                object = iBluetoothPbapClient.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothPbapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled() && BluetoothPbapClient.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothPbapClient.getPriority(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothPbapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        IBluetoothPbapClient iBluetoothPbapClient = this.getService();
        if (iBluetoothPbapClient != null && this.isEnabled() && BluetoothPbapClient.isValidDevice(bluetoothDevice)) {
            if (n != 0 && n != 100) {
                return false;
            }
            try {
                boolean bl = iBluetoothPbapClient.setPriority(bluetoothDevice, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        if (iBluetoothPbapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

