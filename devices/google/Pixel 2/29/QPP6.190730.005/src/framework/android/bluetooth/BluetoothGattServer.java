/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothGattServerCallback;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class BluetoothGattServer
implements BluetoothProfile {
    private static final int CALLBACK_REG_TIMEOUT = 10000;
    private static final boolean DBG = true;
    private static final String TAG = "BluetoothGattServer";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter;
    private final IBluetoothGattServerCallback mBluetoothGattServerCallback = new IBluetoothGattServerCallback.Stub(){

        @Override
        public void onCharacteristicReadRequest(String object, int n, int n2, boolean bl, int n3) {
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            BluetoothGattCharacteristic bluetoothGattCharacteristic = BluetoothGattServer.this.getCharacteristicByHandle(n3);
            if (bluetoothGattCharacteristic == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onCharacteristicReadRequest() no char for handle ");
                ((StringBuilder)object).append(n3);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onCharacteristicReadRequest((BluetoothDevice)object, n, n2, bluetoothGattCharacteristic);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        @Override
        public void onCharacteristicWriteRequest(String object, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) {
            BluetoothDevice bluetoothDevice = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            object = BluetoothGattServer.this.getCharacteristicByHandle(n4);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onCharacteristicWriteRequest() no char for handle ");
                ((StringBuilder)object).append(n4);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onCharacteristicWriteRequest(bluetoothDevice, n, (BluetoothGattCharacteristic)object, bl, bl2, n2, arrby);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        @Override
        public void onConnectionUpdated(String object, int n, int n2, int n3, int n4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionUpdated() - Device=");
            stringBuilder.append((String)object);
            stringBuilder.append(" interval=");
            stringBuilder.append(n);
            stringBuilder.append(" latency=");
            stringBuilder.append(n2);
            stringBuilder.append(" timeout=");
            stringBuilder.append(n3);
            stringBuilder.append(" status=");
            stringBuilder.append(n4);
            Log.d(BluetoothGattServer.TAG, stringBuilder.toString());
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onConnectionUpdated((BluetoothDevice)object, n, n2, n3, n4);
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled exception: ");
                stringBuilder.append(exception);
                Log.w(BluetoothGattServer.TAG, stringBuilder.toString());
            }
        }

        @Override
        public void onDescriptorReadRequest(String object, int n, int n2, boolean bl, int n3) {
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            BluetoothGattDescriptor bluetoothGattDescriptor = BluetoothGattServer.this.getDescriptorByHandle(n3);
            if (bluetoothGattDescriptor == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onDescriptorReadRequest() no desc for handle ");
                ((StringBuilder)object).append(n3);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onDescriptorReadRequest((BluetoothDevice)object, n, n2, bluetoothGattDescriptor);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        @Override
        public void onDescriptorWriteRequest(String object, int n, int n2, int n3, boolean bl, boolean bl2, int n4, byte[] arrby) {
            BluetoothDevice bluetoothDevice = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            object = BluetoothGattServer.this.getDescriptorByHandle(n4);
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onDescriptorWriteRequest() no desc for handle ");
                ((StringBuilder)object).append(n4);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onDescriptorWriteRequest(bluetoothDevice, n, (BluetoothGattDescriptor)object, bl, bl2, n2, arrby);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        @Override
        public void onExecuteWrite(String object, int n, boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onExecuteWrite() - device=");
            stringBuilder.append((String)object);
            stringBuilder.append(", transId=");
            stringBuilder.append(n);
            stringBuilder.append("execWrite=");
            stringBuilder.append(bl);
            Log.d(BluetoothGattServer.TAG, stringBuilder.toString());
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onExecuteWrite((BluetoothDevice)object, n, bl);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        @Override
        public void onMtuChanged(String object, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onMtuChanged() - device=");
            stringBuilder.append((String)object);
            stringBuilder.append(", mtu=");
            stringBuilder.append(n);
            Log.d(BluetoothGattServer.TAG, stringBuilder.toString());
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onMtuChanged((BluetoothDevice)object, n);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unhandled exception: ");
                ((StringBuilder)object).append(exception);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void onNotificationSent(String object, int n) {
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onNotificationSent((BluetoothDevice)object, n);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unhandled exception: ");
                ((StringBuilder)object).append(exception);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void onPhyRead(String object, int n, int n2, int n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPhyUpdate() - device=");
            stringBuilder.append((String)object);
            stringBuilder.append(", txPHy=");
            stringBuilder.append(n);
            stringBuilder.append(", rxPHy=");
            stringBuilder.append(n2);
            Log.d(BluetoothGattServer.TAG, stringBuilder.toString());
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onPhyRead((BluetoothDevice)object, n, n2, n3);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unhandled exception: ");
                ((StringBuilder)object).append(exception);
                Log.w(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void onPhyUpdate(String object, int n, int n2, int n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPhyUpdate() - device=");
            stringBuilder.append((String)object);
            stringBuilder.append(", txPHy=");
            stringBuilder.append(n);
            stringBuilder.append(", rxPHy=");
            stringBuilder.append(n2);
            Log.d(BluetoothGattServer.TAG, stringBuilder.toString());
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            if (object == null) {
                return;
            }
            try {
                BluetoothGattServer.this.mCallback.onPhyUpdate((BluetoothDevice)object, n, n2, n3);
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled exception: ");
                stringBuilder.append(exception);
                Log.w(BluetoothGattServer.TAG, stringBuilder.toString());
            }
        }

        @Override
        public void onServerConnectionState(int n, int n2, boolean bl, String object) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("onServerConnectionState() - status=");
            ((StringBuilder)object2).append(n);
            ((StringBuilder)object2).append(" serverIf=");
            ((StringBuilder)object2).append(n2);
            ((StringBuilder)object2).append(" device=");
            ((StringBuilder)object2).append((String)object);
            Log.d(BluetoothGattServer.TAG, ((StringBuilder)object2).toString());
            object2 = BluetoothGattServer.this.mCallback;
            object = BluetoothGattServer.this.mAdapter.getRemoteDevice((String)object);
            n2 = bl ? 2 : 0;
            try {
                ((BluetoothGattServerCallback)object2).onConnectionStateChange((BluetoothDevice)object, n, n2);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onServerRegistered(int n, int n2) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onServerRegistered() - status=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" serverIf=");
            ((StringBuilder)object).append(n2);
            Log.d(BluetoothGattServer.TAG, ((StringBuilder)object).toString());
            object = BluetoothGattServer.this.mServerIfLock;
            synchronized (object) {
                if (BluetoothGattServer.this.mCallback != null) {
                    BluetoothGattServer.this.mServerIf = n2;
                    BluetoothGattServer.this.mServerIfLock.notify();
                } else {
                    Log.e(BluetoothGattServer.TAG, "onServerRegistered: mCallback is null");
                }
                return;
            }
        }

        @Override
        public void onServiceAdded(int n, BluetoothGattService object) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("onServiceAdded() - handle=");
            ((StringBuilder)object2).append(((BluetoothGattService)object).getInstanceId());
            ((StringBuilder)object2).append(" uuid=");
            ((StringBuilder)object2).append(((BluetoothGattService)object).getUuid());
            ((StringBuilder)object2).append(" status=");
            ((StringBuilder)object2).append(n);
            Log.d(BluetoothGattServer.TAG, ((StringBuilder)object2).toString());
            if (BluetoothGattServer.this.mPendingService == null) {
                return;
            }
            object2 = BluetoothGattServer.this.mPendingService;
            BluetoothGattServer.this.mPendingService = null;
            ((BluetoothGattService)object2).setInstanceId(((BluetoothGattService)object).getInstanceId());
            List<BluetoothGattCharacteristic> list = ((BluetoothGattService)object2).getCharacteristics();
            object = ((BluetoothGattService)object).getCharacteristics();
            for (int i = 0; i < object.size(); ++i) {
                Object object3 = list.get(i);
                Object object4 = (BluetoothGattCharacteristic)object.get(i);
                ((BluetoothGattCharacteristic)object3).setInstanceId(((BluetoothGattCharacteristic)object4).getInstanceId());
                object3 = ((BluetoothGattCharacteristic)object3).getDescriptors();
                object4 = ((BluetoothGattCharacteristic)object4).getDescriptors();
                for (int j = 0; j < object4.size(); ++j) {
                    ((BluetoothGattDescriptor)object3.get(j)).setInstanceId(((BluetoothGattDescriptor)object4.get(j)).getInstanceId());
                }
            }
            BluetoothGattServer.this.mServices.add(object2);
            try {
                BluetoothGattServer.this.mCallback.onServiceAdded(n, (BluetoothGattService)object2);
            }
            catch (Exception exception) {
                Log.w(BluetoothGattServer.TAG, "Unhandled exception in callback", exception);
            }
        }
    };
    private BluetoothGattServerCallback mCallback;
    private BluetoothGattService mPendingService;
    private int mServerIf;
    private Object mServerIfLock = new Object();
    private IBluetoothGatt mService;
    private List<BluetoothGattService> mServices;
    private int mTransport;

    BluetoothGattServer(IBluetoothGatt iBluetoothGatt, int n) {
        this.mService = iBluetoothGatt;
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mCallback = null;
        this.mServerIf = 0;
        this.mTransport = n;
        this.mServices = new ArrayList<BluetoothGattService>();
    }

    private void unregisterCallback() {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("unregisterCallback() - mServerIf=");
        ((StringBuilder)object).append(this.mServerIf);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mServerIf) != 0) {
            try {
                this.mCallback = null;
                object.unregisterServer(n);
                this.mServerIf = 0;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    public boolean addService(BluetoothGattService bluetoothGattService) {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("addService() - service: ");
        ((StringBuilder)object).append(bluetoothGattService.getUuid());
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mServerIf) != 0) {
            this.mPendingService = bluetoothGattService;
            try {
                object.addService(n, bluetoothGattService);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public void cancelConnection(BluetoothDevice bluetoothDevice) {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("cancelConnection() - device: ");
        ((StringBuilder)object).append(bluetoothDevice.getAddress());
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mServerIf) != 0) {
            try {
                object.serverDisconnect(n, bluetoothDevice.getAddress());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    public void clearServices() {
        int n;
        Log.d(TAG, "clearServices()");
        IBluetoothGatt iBluetoothGatt = this.mService;
        if (iBluetoothGatt != null && (n = this.mServerIf) != 0) {
            try {
                iBluetoothGatt.clearServices(n);
                this.mServices.clear();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    public void close() {
        Log.d(TAG, "close()");
        this.unregisterCallback();
    }

    public boolean connect(BluetoothDevice object, boolean bl) {
        int n;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("connect() - device: ");
        ((StringBuilder)object2).append(((BluetoothDevice)object).getAddress());
        ((StringBuilder)object2).append(", auto: ");
        ((StringBuilder)object2).append(bl);
        Log.d(TAG, ((StringBuilder)object2).toString());
        object2 = this.mService;
        if (object2 != null && (n = this.mServerIf) != 0) {
            try {
                object = ((BluetoothDevice)object).getAddress();
                bl = !bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
            object2.serverConnect(n, (String)object, bl, this.mTransport);
            return true;
        }
        return false;
    }

    BluetoothGattCharacteristic getCharacteristicByHandle(int n) {
        Iterator<BluetoothGattService> iterator = this.mServices.iterator();
        while (iterator.hasNext()) {
            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : iterator.next().getCharacteristics()) {
                if (bluetoothGattCharacteristic.getInstanceId() != n) continue;
                return bluetoothGattCharacteristic;
            }
        }
        return null;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        throw new UnsupportedOperationException("Use BluetoothManager#getConnectedDevices instead.");
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        throw new UnsupportedOperationException("Use BluetoothManager#getConnectionState instead.");
    }

    BluetoothGattDescriptor getDescriptorByHandle(int n) {
        Iterator<BluetoothGattService> iterator = this.mServices.iterator();
        while (iterator.hasNext()) {
            Iterator<BluetoothGattCharacteristic> iterator2 = iterator.next().getCharacteristics().iterator();
            while (iterator2.hasNext()) {
                for (BluetoothGattDescriptor bluetoothGattDescriptor : iterator2.next().getDescriptors()) {
                    if (bluetoothGattDescriptor.getInstanceId() != n) continue;
                    return bluetoothGattDescriptor;
                }
            }
        }
        return null;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] arrn) {
        throw new UnsupportedOperationException("Use BluetoothManager#getDevicesMatchingConnectionStates instead.");
    }

    public BluetoothGattService getService(UUID uUID) {
        for (BluetoothGattService bluetoothGattService : this.mServices) {
            if (!bluetoothGattService.getUuid().equals(uUID)) continue;
            return bluetoothGattService;
        }
        return null;
    }

    BluetoothGattService getService(UUID uUID, int n, int n2) {
        for (BluetoothGattService bluetoothGattService : this.mServices) {
            if (bluetoothGattService.getType() != n2 || bluetoothGattService.getInstanceId() != n || !bluetoothGattService.getUuid().equals(uUID)) continue;
            return bluetoothGattService;
        }
        return null;
    }

    public List<BluetoothGattService> getServices() {
        return this.mServices;
    }

    public boolean notifyCharacteristicChanged(BluetoothDevice bluetoothDevice, BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean bl) {
        if (this.mService != null && this.mServerIf != 0) {
            if (bluetoothGattCharacteristic.getService() == null) {
                return false;
            }
            if (bluetoothGattCharacteristic.getValue() != null) {
                try {
                    this.mService.sendNotification(this.mServerIf, bluetoothDevice.getAddress(), bluetoothGattCharacteristic.getInstanceId(), bl, bluetoothGattCharacteristic.getValue());
                    return true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "", remoteException);
                    return false;
                }
            }
            throw new IllegalArgumentException("Chracteristic value is empty. Use BluetoothGattCharacteristic#setvalue to update");
        }
        return false;
    }

    public void readPhy(BluetoothDevice bluetoothDevice) {
        try {
            this.mService.serverReadPhy(this.mServerIf, bluetoothDevice.getAddress());
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean registerCallback(BluetoothGattServerCallback object) {
        Log.d(TAG, "registerCallback()");
        if (this.mService == null) {
            Log.e(TAG, "GATT service not available");
            return false;
        }
        UUID uUID = UUID.randomUUID();
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("registerCallback() - UUID=");
        ((StringBuilder)object2).append(uUID);
        Log.d(TAG, ((StringBuilder)object2).toString());
        object2 = this.mServerIfLock;
        synchronized (object2) {
            if (this.mCallback != null) {
                Log.e(TAG, "App can register callback only once");
                return false;
            }
            this.mCallback = object;
            try {
                object = this.mService;
                ParcelUuid parcelUuid = new ParcelUuid(uUID);
                object.registerServer(parcelUuid, this.mBluetoothGattServerCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                this.mCallback = null;
                return false;
            }
            try {
                this.mServerIfLock.wait(10000L);
            }
            catch (InterruptedException interruptedException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("");
                ((StringBuilder)object).append(interruptedException);
                Log.e(TAG, ((StringBuilder)object).toString());
                this.mCallback = null;
            }
            if (this.mServerIf == 0) {
                this.mCallback = null;
                return false;
            }
            return true;
        }
    }

    public boolean removeService(BluetoothGattService bluetoothGattService) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("removeService() - service: ");
        ((StringBuilder)object).append(bluetoothGattService.getUuid());
        Log.d(TAG, ((StringBuilder)object).toString());
        if (this.mService != null && this.mServerIf != 0) {
            object = this.getService(bluetoothGattService.getUuid(), bluetoothGattService.getInstanceId(), bluetoothGattService.getType());
            if (object == null) {
                return false;
            }
            try {
                this.mService.removeService(this.mServerIf, bluetoothGattService.getInstanceId());
                this.mServices.remove(object);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public boolean sendResponse(BluetoothDevice bluetoothDevice, int n, int n2, int n3, byte[] arrby) {
        int n4;
        IBluetoothGatt iBluetoothGatt = this.mService;
        if (iBluetoothGatt != null && (n4 = this.mServerIf) != 0) {
            try {
                iBluetoothGatt.sendResponse(n4, bluetoothDevice.getAddress(), n, n2, n3, arrby);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public void setPreferredPhy(BluetoothDevice bluetoothDevice, int n, int n2, int n3) {
        try {
            this.mService.serverSetPreferredPhy(this.mServerIf, bluetoothDevice.getAddress(), n, n2, n3);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
        }
    }

}

