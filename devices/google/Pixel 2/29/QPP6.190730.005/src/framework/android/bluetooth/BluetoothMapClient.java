/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothMapClient;
import android.content.Context;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothMapClient
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.mapmce.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_MESSAGE_DELIVERED_SUCCESSFULLY = "android.bluetooth.mapmce.profile.action.MESSAGE_DELIVERED_SUCCESSFULLY";
    public static final String ACTION_MESSAGE_RECEIVED = "android.bluetooth.mapmce.profile.action.MESSAGE_RECEIVED";
    public static final String ACTION_MESSAGE_SENT_SUCCESSFULLY = "android.bluetooth.mapmce.profile.action.MESSAGE_SENT_SUCCESSFULLY";
    private static final boolean DBG = Log.isLoggable("BluetoothMapClient", 3);
    public static final String EXTRA_MESSAGE_HANDLE = "android.bluetooth.mapmce.profile.extra.MESSAGE_HANDLE";
    public static final String EXTRA_MESSAGE_READ_STATUS = "android.bluetooth.mapmce.profile.extra.MESSAGE_READ_STATUS";
    public static final String EXTRA_MESSAGE_TIMESTAMP = "android.bluetooth.mapmce.profile.extra.MESSAGE_TIMESTAMP";
    public static final String EXTRA_SENDER_CONTACT_NAME = "android.bluetooth.mapmce.profile.extra.SENDER_CONTACT_NAME";
    public static final String EXTRA_SENDER_CONTACT_URI = "android.bluetooth.mapmce.profile.extra.SENDER_CONTACT_URI";
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    public static final int STATE_ERROR = -1;
    private static final String TAG = "BluetoothMapClient";
    private static final int UPLOADING_FEATURE_BITMASK = 8;
    private static final boolean VDBG = Log.isLoggable("BluetoothMapClient", 2);
    private BluetoothAdapter mAdapter;
    private final BluetoothProfileConnector<IBluetoothMapClient> mProfileConnector = new BluetoothProfileConnector(this, 18, "BluetoothMapClient", IBluetoothMapClient.class.getName()){

        public IBluetoothMapClient getServiceInterface(IBinder iBinder) {
            return IBluetoothMapClient.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothMapClient(Context context, BluetoothProfile.ServiceListener serviceListener) {
        if (DBG) {
            Log.d(TAG, "Create BluetoothMapClient proxy object");
        }
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothMapClient getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null && bluetoothAdapter.getState() == 12) {
            return true;
        }
        if (DBG) {
            Log.d(TAG, "Bluetooth is Not enabled");
        }
        return false;
    }

    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    public void close() {
        this.mProfileConnector.disconnect();
    }

    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("connect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")for MAPS MCE");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null) {
            try {
                boolean bl = object.connect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            if (DBG) {
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        return false;
    }

    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("disconnect(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.disconnect(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
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

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Object object;
        if (DBG) {
            Log.d(TAG, "getConnectedDevices()");
        }
        if ((object = this.getService()) != null && this.isEnabled()) {
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
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getConnectionState(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
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
        IBluetoothMapClient iBluetoothMapClient;
        if (DBG) {
            Log.d(TAG, "getDevicesMatchingStates()");
        }
        if ((iBluetoothMapClient = this.getService()) != null && this.isEnabled()) {
            try {
                object = iBluetoothMapClient.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothMapClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public int getPriority(BluetoothDevice bluetoothDevice) {
        Object object;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getPriority(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
            try {
                int n = object.getPriority(bluetoothDevice);
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

    public boolean getUnreadMessages(BluetoothDevice bluetoothDevice) {
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("getUnreadMessages(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.getUnreadMessages(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        return false;
    }

    public boolean isConnected(BluetoothDevice bluetoothDevice) {
        Object object;
        if (VDBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("isConnected(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null) {
            try {
                boolean bl = object.isConnected(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            if (DBG) {
                Log.d(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        return false;
    }

    public boolean isUploadingSupported(BluetoothDevice bluetoothDevice) {
        IBluetoothMapClient iBluetoothMapClient = this.getService();
        boolean bl = false;
        if (iBluetoothMapClient != null) {
            try {
                int n;
                if (this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice) && ((n = iBluetoothMapClient.getSupportedFeatures(bluetoothDevice)) & 8) > 0) {
                    bl = true;
                }
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.getMessage());
                return false;
            }
        }
        return bl;
    }

    @UnsupportedAppUsage
    public boolean sendMessage(BluetoothDevice bluetoothDevice, Uri[] arruri, String string2, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("sendMessage(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(arruri);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.sendMessage(bluetoothDevice, arruri, string2, pendingIntent, pendingIntent2);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return false;
            }
        }
        return false;
    }

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object;
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("setPriority(");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(")");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        if ((object = this.getService()) != null && this.isEnabled() && BluetoothMapClient.isValidDevice(bluetoothDevice)) {
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

