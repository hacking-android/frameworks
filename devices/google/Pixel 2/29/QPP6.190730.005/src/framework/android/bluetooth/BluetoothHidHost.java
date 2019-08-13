/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothHidHost;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothHidHost
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.input.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_HANDSHAKE = "android.bluetooth.input.profile.action.HANDSHAKE";
    public static final String ACTION_IDLE_TIME_CHANGED = "android.bluetooth.input.profile.action.IDLE_TIME_CHANGED";
    public static final String ACTION_PROTOCOL_MODE_CHANGED = "android.bluetooth.input.profile.action.PROTOCOL_MODE_CHANGED";
    public static final String ACTION_REPORT = "android.bluetooth.input.profile.action.REPORT";
    public static final String ACTION_VIRTUAL_UNPLUG_STATUS = "android.bluetooth.input.profile.action.VIRTUAL_UNPLUG_STATUS";
    private static final boolean DBG = true;
    public static final String EXTRA_IDLE_TIME = "android.bluetooth.BluetoothHidHost.extra.IDLE_TIME";
    public static final String EXTRA_PROTOCOL_MODE = "android.bluetooth.BluetoothHidHost.extra.PROTOCOL_MODE";
    public static final String EXTRA_REPORT = "android.bluetooth.BluetoothHidHost.extra.REPORT";
    public static final String EXTRA_REPORT_BUFFER_SIZE = "android.bluetooth.BluetoothHidHost.extra.REPORT_BUFFER_SIZE";
    public static final String EXTRA_REPORT_ID = "android.bluetooth.BluetoothHidHost.extra.REPORT_ID";
    public static final String EXTRA_REPORT_TYPE = "android.bluetooth.BluetoothHidHost.extra.REPORT_TYPE";
    public static final String EXTRA_STATUS = "android.bluetooth.BluetoothHidHost.extra.STATUS";
    public static final String EXTRA_VIRTUAL_UNPLUG_STATUS = "android.bluetooth.BluetoothHidHost.extra.VIRTUAL_UNPLUG_STATUS";
    public static final int INPUT_CONNECT_FAILED_ALREADY_CONNECTED = 5001;
    public static final int INPUT_CONNECT_FAILED_ATTEMPT_FAILED = 5002;
    public static final int INPUT_DISCONNECT_FAILED_NOT_CONNECTED = 5000;
    public static final int INPUT_OPERATION_GENERIC_FAILURE = 5003;
    public static final int INPUT_OPERATION_SUCCESS = 5004;
    public static final int PROTOCOL_BOOT_MODE = 1;
    public static final int PROTOCOL_REPORT_MODE = 0;
    public static final int PROTOCOL_UNSUPPORTED_MODE = 255;
    public static final byte REPORT_TYPE_FEATURE = 3;
    public static final byte REPORT_TYPE_INPUT = 1;
    public static final byte REPORT_TYPE_OUTPUT = 2;
    private static final String TAG = "BluetoothHidHost";
    private static final boolean VDBG = false;
    public static final int VIRTUAL_UNPLUG_STATUS_FAIL = 1;
    public static final int VIRTUAL_UNPLUG_STATUS_SUCCESS = 0;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothHidHost> mProfileConnector = new BluetoothProfileConnector(this, 4, "BluetoothHidHost", IBluetoothHidHost.class.getName()){

        public IBluetoothHidHost getServiceInterface(IBinder iBinder) {
            return IBluetoothHidHost.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothHidHost(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothHidHost getService() {
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

    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("connect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
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

    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("disconnect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
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
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHidHost.getConnectionState(bluetoothDevice);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled()) {
            try {
                object = iBluetoothHidHost.getDevicesMatchingConnectionStates((int[])object);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public boolean getIdleTime(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("getIdletime(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.getIdleTime(bluetoothDevice);
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

    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHidHost.getPriority(bluetoothDevice);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean getProtocolMode(BluetoothDevice bluetoothDevice) {
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHidHost.getProtocolMode(bluetoothDevice);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean getReport(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) {
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHidHost.getReport(bluetoothDevice, by, by2, n);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean sendData(BluetoothDevice bluetoothDevice, String string2) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("sendData(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append("), report=");
        ((StringBuilder)object).append(string2);
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.sendData(bluetoothDevice, string2);
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

    public boolean setIdleTime(BluetoothDevice bluetoothDevice, byte by) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setIdletime(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append("), idleTime=");
        ((StringBuilder)object).append(by);
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.setIdleTime(bluetoothDevice, by);
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

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setPriority(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
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

    public boolean setProtocolMode(BluetoothDevice bluetoothDevice, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setProtocolMode(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.setProtocolMode(bluetoothDevice, n);
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

    public boolean setReport(BluetoothDevice bluetoothDevice, byte by, String string2) {
        IBluetoothHidHost iBluetoothHidHost = this.getService();
        if (iBluetoothHidHost != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHidHost.setReport(bluetoothDevice, by, string2);
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
        if (iBluetoothHidHost == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean virtualUnplug(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("virtualUnplug(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHidHost.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHidHost.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.virtualUnplug(bluetoothDevice);
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

