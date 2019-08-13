/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothManager;
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

public abstract class BluetoothProfileConnector<T> {
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub(){

        @Override
        public void onBluetoothStateChange(boolean bl) {
            if (bl) {
                BluetoothProfileConnector.this.doBind();
            } else {
                BluetoothProfileConnector.this.doUnbind();
            }
        }
    };
    private final ServiceConnection mConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            BluetoothProfileConnector.this.logDebug("Proxy object connected");
            object = BluetoothProfileConnector.this;
            ((BluetoothProfileConnector)object).mService = ((BluetoothProfileConnector)object).getServiceInterface(iBinder);
            if (BluetoothProfileConnector.this.mServiceListener != null) {
                BluetoothProfileConnector.this.mServiceListener.onServiceConnected(BluetoothProfileConnector.this.mProfileId, BluetoothProfileConnector.this.mProfileProxy);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            BluetoothProfileConnector.this.logDebug("Proxy object disconnected");
            BluetoothProfileConnector.this.doUnbind();
            if (BluetoothProfileConnector.this.mServiceListener != null) {
                BluetoothProfileConnector.this.mServiceListener.onServiceDisconnected(BluetoothProfileConnector.this.mProfileId);
            }
        }
    };
    private Context mContext;
    private final int mProfileId;
    private final String mProfileName;
    private final BluetoothProfile mProfileProxy;
    private volatile T mService;
    private BluetoothProfile.ServiceListener mServiceListener;
    private final String mServiceName;

    BluetoothProfileConnector(BluetoothProfile bluetoothProfile, int n, String string2, String string3) {
        this.mProfileId = n;
        this.mProfileProxy = bluetoothProfile;
        this.mProfileName = string2;
        this.mServiceName = string3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean doBind() {
        ServiceConnection serviceConnection = this.mConnection;
        synchronized (serviceConnection) {
            if (this.mService == null) {
                this.logDebug("Binding service...");
                try {
                    Intent intent = new Intent(this.mServiceName);
                    Object object = intent.resolveSystemService(this.mContext.getPackageManager(), 0);
                    intent.setComponent((ComponentName)object);
                    if (object == null || !this.mContext.bindServiceAsUser(intent, this.mConnection, 0, UserHandle.CURRENT_OR_SELF)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Could not bind to Bluetooth Service with ");
                        ((StringBuilder)object).append(intent);
                        this.logError(((StringBuilder)object).toString());
                        return false;
                    }
                }
                catch (SecurityException securityException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to bind service. ");
                    stringBuilder.append(securityException);
                    this.logError(stringBuilder.toString());
                    return false;
                }
            }
            return true;
        }
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
        this.logDebug("Unbinding service...");
        this.mContext.unbindService(this.mConnection);
        this.mService = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (IllegalArgumentException illegalArgumentException) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to unbind service: ");
                stringBuilder.append(illegalArgumentException);
                this.logError(stringBuilder.toString());
                this.mService = null;
                return;
            }
        }
        this.mService = null;
        throw throwable2222;
    }

    private void logDebug(String string2) {
        Log.d(this.mProfileName, string2);
    }

    private void logError(String string2) {
        Log.e(this.mProfileName, string2);
    }

    void connect(Context object, BluetoothProfile.ServiceListener object2) {
        this.mContext = object;
        this.mServiceListener = object2;
        object = BluetoothAdapter.getDefaultAdapter().getBluetoothManager();
        if (object != null) {
            try {
                object.registerStateChangeCallback(this.mBluetoothStateChangeCallback);
            }
            catch (RemoteException remoteException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to register state change callback. ");
                ((StringBuilder)object2).append(remoteException);
                this.logError(((StringBuilder)object2).toString());
            }
        }
        this.doBind();
    }

    void disconnect() {
        this.mServiceListener = null;
        Object object = BluetoothAdapter.getDefaultAdapter().getBluetoothManager();
        if (object != null) {
            try {
                object.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to unregister state change callback");
                ((StringBuilder)object).append(remoteException);
                this.logError(((StringBuilder)object).toString());
            }
        }
        this.doUnbind();
    }

    T getService() {
        return this.mService;
    }

    public abstract T getServiceInterface(IBinder var1);

}

