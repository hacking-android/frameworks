/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class BluetoothManager {
    private static final boolean DBG = false;
    private static final String TAG = "BluetoothManager";
    private final BluetoothAdapter mAdapter;

    public BluetoothManager(Context context) {
        context = context.getApplicationContext();
        if (context != null) {
            this.mAdapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothAdapter bluetoothAdapter = this.mAdapter;
            if (bluetoothAdapter != null) {
                bluetoothAdapter.setContext(context);
            }
            return;
        }
        throw new IllegalArgumentException("context not associated with any application (using a mock context?)");
    }

    public BluetoothAdapter getAdapter() {
        return this.mAdapter;
    }

    public List<BluetoothDevice> getConnectedDevices(int n) {
        Object object;
        Object object2;
        block4 : {
            if (n != 7 && n != 8) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Profile not supported: ");
                stringBuilder.append(n);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            object2 = new ArrayList<BluetoothDevice>();
            object = this.mAdapter.getBluetoothManager().getBluetoothGatt();
            if (object != null) break block4;
            return object2;
        }
        try {
            object2 = object = object.getDevicesMatchingConnectionStates(new int[]{2});
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
        }
        return object2;
    }

    public int getConnectionState(BluetoothDevice bluetoothDevice, int n) {
        Iterator<BluetoothDevice> iterator = this.getConnectedDevices(n).iterator();
        while (iterator.hasNext()) {
            if (!bluetoothDevice.equals(iterator.next())) continue;
            return 2;
        }
        return 0;
    }

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int n, int[] object) {
        IBluetoothGatt iBluetoothGatt;
        ArrayList<BluetoothDevice> arrayList;
        block4 : {
            if (n != 7 && n != 8) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Profile not supported: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            arrayList = new ArrayList<BluetoothDevice>();
            iBluetoothGatt = this.mAdapter.getBluetoothManager().getBluetoothGatt();
            if (iBluetoothGatt != null) break block4;
            return arrayList;
        }
        try {
            object = iBluetoothGatt.getDevicesMatchingConnectionStates((int[])object);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            object = arrayList;
        }
        return object;
    }

    public BluetoothGattServer openGattServer(Context context, BluetoothGattServerCallback bluetoothGattServerCallback) {
        return this.openGattServer(context, bluetoothGattServerCallback, 0);
    }

    public BluetoothGattServer openGattServer(Context object, BluetoothGattServerCallback bluetoothGattServerCallback, int n) {
        if (object != null && bluetoothGattServerCallback != null) {
            IBluetoothGatt iBluetoothGatt;
            block6 : {
                object = null;
                try {
                    iBluetoothGatt = this.mAdapter.getBluetoothManager().getBluetoothGatt();
                    if (iBluetoothGatt != null) break block6;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "", remoteException);
                    return null;
                }
                Log.e(TAG, "Fail to get GATT Server connection");
                return null;
            }
            BluetoothGattServer bluetoothGattServer = new BluetoothGattServer(iBluetoothGatt, n);
            boolean bl = bluetoothGattServer.registerCallback(bluetoothGattServerCallback);
            if (bl) {
                object = bluetoothGattServer;
            }
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("null parameter: ");
        stringBuilder.append(object);
        stringBuilder.append(" ");
        stringBuilder.append(bluetoothGattServerCallback);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

