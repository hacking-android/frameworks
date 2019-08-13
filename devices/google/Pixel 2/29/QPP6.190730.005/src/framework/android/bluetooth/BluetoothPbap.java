/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.IBluetoothPbap;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BluetoothPbap
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.pbap.profile.action.CONNECTION_STATE_CHANGED";
    private static final boolean DBG = false;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILURE = 0;
    public static final int RESULT_SUCCESS = 1;
    private static final String TAG = "BluetoothPbap";
    private BluetoothAdapter mAdapter;
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub(){

        @Override
        public void onBluetoothStateChange(boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onBluetoothStateChange: up=");
            stringBuilder.append(bl);
            BluetoothPbap.log(stringBuilder.toString());
            if (!bl) {
                BluetoothPbap.this.doUnbind();
            } else {
                BluetoothPbap.this.doBind();
            }
        }
    };
    private final ServiceConnection mConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BluetoothPbap.log("Proxy object connected");
            BluetoothPbap.this.mService = IBluetoothPbap.Stub.asInterface(iBinder);
            if (BluetoothPbap.this.mServiceListener != null) {
                BluetoothPbap.this.mServiceListener.onServiceConnected(BluetoothPbap.this);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            BluetoothPbap.log("Proxy object disconnected");
            BluetoothPbap.this.doUnbind();
            if (BluetoothPbap.this.mServiceListener != null) {
                BluetoothPbap.this.mServiceListener.onServiceDisconnected();
            }
        }
    };
    private final Context mContext;
    private volatile IBluetoothPbap mService;
    private ServiceListener mServiceListener;

    public BluetoothPbap(Context object, ServiceListener serviceListener) {
        this.mContext = object;
        this.mServiceListener = serviceListener;
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        object = this.mAdapter.getBluetoothManager();
        if (object != null) {
            try {
                object.registerStateChangeCallback(this.mBluetoothStateChangeCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
        }
        this.doBind();
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void doUnbind() {
        Throwable throwable2222;
        ServiceConnection serviceConnection = this.mConnection;
        // MONITORENTER : serviceConnection
        if (this.mService == null) {
            // MONITOREXIT : serviceConnection
            return;
        }
        BluetoothPbap.log("Unbinding service...");
        this.mContext.unbindService(this.mConnection);
        this.mService = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            {
                Log.e(TAG, "", illegalArgumentException);
                this.mService = null;
                return;
            }
        }
        this.mService = null;
        throw throwable2222;
    }

    private static void log(String string2) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        synchronized (this) {
            IBluetoothManager iBluetoothManager = this.mAdapter.getBluetoothManager();
            if (iBluetoothManager != null) {
                try {
                    iBluetoothManager.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.doUnbind();
            this.mServiceListener = null;
            return;
        }
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        BluetoothPbap.log("disconnect()");
        IBluetoothPbap iBluetoothPbap = this.mService;
        if (iBluetoothPbap == null) {
            Log.w(TAG, "Proxy not attached to service");
            return false;
        }
        try {
            iBluetoothPbap.disconnect(bluetoothDevice);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, remoteException.toString());
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean doBind() {
        ServiceConnection serviceConnection = this.mConnection;
        synchronized (serviceConnection) {
            try {
                block5 : {
                    try {
                        if (this.mService != null) break block5;
                        BluetoothPbap.log("Binding service...");
                        Intent intent = new Intent(IBluetoothPbap.class.getName());
                        Object object = intent.resolveSystemService(this.mContext.getPackageManager(), 0);
                        intent.setComponent((ComponentName)object);
                        if (object != null && this.mContext.bindServiceAsUser(intent, this.mConnection, 0, UserHandle.CURRENT_OR_SELF)) break block5;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Could not bind to Bluetooth Pbap Service with ");
                        ((StringBuilder)object).append(intent);
                        Log.e(TAG, ((StringBuilder)object).toString());
                        return false;
                    }
                    catch (SecurityException securityException) {
                        Log.e(TAG, "", securityException);
                        return false;
                    }
                }
                return true;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
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
        BluetoothPbap.log("getConnectedDevices()");
        Object object = this.mService;
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList<BluetoothDevice>();
        }
        try {
            object = object.getConnectedDevices();
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, remoteException.toString());
            return new ArrayList<BluetoothDevice>();
        }
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("getConnectionState: device=");
        ((StringBuilder)object).append(bluetoothDevice);
        BluetoothPbap.log(((StringBuilder)object).toString());
        object = this.mService;
        if (object == null) {
            Log.w(TAG, "Proxy not attached to service");
            return 0;
        }
        try {
            int n = object.getConnectionState(bluetoothDevice);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, remoteException.toString());
            return 0;
        }
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("getDevicesMatchingConnectionStates: states=");
        ((StringBuilder)object2).append(Arrays.toString(object));
        BluetoothPbap.log(((StringBuilder)object2).toString());
        object2 = this.mService;
        if (object2 == null) {
            Log.w(TAG, "Proxy not attached to service");
            return new ArrayList<BluetoothDevice>();
        }
        try {
            object = object2.getDevicesMatchingConnectionStates((int[])object);
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, remoteException.toString());
            return new ArrayList<BluetoothDevice>();
        }
    }

    public boolean isConnected(BluetoothDevice bluetoothDevice) {
        boolean bl = this.getConnectionState(bluetoothDevice) == 2;
        return bl;
    }

    public static interface ServiceListener {
        public void onServiceConnected(BluetoothPbap var1);

        public void onServiceDisconnected();
    }

}

