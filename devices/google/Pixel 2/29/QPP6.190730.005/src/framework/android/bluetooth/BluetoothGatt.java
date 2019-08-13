/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothGattCallback;
import android.os.Handler;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public final class BluetoothGatt
implements BluetoothProfile {
    static final int AUTHENTICATION_MITM = 2;
    static final int AUTHENTICATION_NONE = 0;
    static final int AUTHENTICATION_NO_MITM = 1;
    private static final int AUTH_RETRY_STATE_IDLE = 0;
    private static final int AUTH_RETRY_STATE_MITM = 2;
    private static final int AUTH_RETRY_STATE_NO_MITM = 1;
    public static final int CONNECTION_PRIORITY_BALANCED = 0;
    public static final int CONNECTION_PRIORITY_HIGH = 1;
    public static final int CONNECTION_PRIORITY_LOW_POWER = 2;
    private static final int CONN_STATE_CLOSED = 4;
    private static final int CONN_STATE_CONNECTED = 2;
    private static final int CONN_STATE_CONNECTING = 1;
    private static final int CONN_STATE_DISCONNECTING = 3;
    private static final int CONN_STATE_IDLE = 0;
    private static final boolean DBG = true;
    public static final int GATT_CONNECTION_CONGESTED = 143;
    public static final int GATT_FAILURE = 257;
    public static final int GATT_INSUFFICIENT_AUTHENTICATION = 5;
    public static final int GATT_INSUFFICIENT_ENCRYPTION = 15;
    public static final int GATT_INVALID_ATTRIBUTE_LENGTH = 13;
    public static final int GATT_INVALID_OFFSET = 7;
    public static final int GATT_READ_NOT_PERMITTED = 2;
    public static final int GATT_REQUEST_NOT_SUPPORTED = 6;
    public static final int GATT_SUCCESS = 0;
    public static final int GATT_WRITE_NOT_PERMITTED = 3;
    private static final String TAG = "BluetoothGatt";
    private static final boolean VDBG = false;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private int mAuthRetryState;
    @UnsupportedAppUsage
    private boolean mAutoConnect;
    private final IBluetoothGattCallback mBluetoothGattCallback = new IBluetoothGattCallback.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onCharacteristicRead(String object, final int n, int n2, byte[] arrby) {
            if (!((String)object).equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            Object object2 = BluetoothGatt.this.mDeviceBusyLock;
            // MONITORENTER : object2
            BluetoothGatt.this.mDeviceBusy = false;
            // MONITOREXIT : object2
            if (n == 5 || n == 15) {
                int n3 = BluetoothGatt.this.mAuthRetryState;
                int n4 = 2;
                if (n3 != 2) {
                    try {
                        if (BluetoothGatt.this.mAuthRetryState == 0) {
                            n4 = 1;
                        }
                        BluetoothGatt.this.mService.readCharacteristic(BluetoothGatt.this.mClientIf, (String)object, n2, n4);
                        BluetoothGatt.access$1408(BluetoothGatt.this);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(BluetoothGatt.TAG, "", remoteException);
                    }
                }
            }
            BluetoothGatt.this.mAuthRetryState = 0;
            object = BluetoothGatt.this;
            object = ((BluetoothGatt)object).getCharacteristicById(((BluetoothGatt)object).mDevice, n2);
            if (object == null) {
                Log.w(BluetoothGatt.TAG, "onCharacteristicRead() failed to find characteristic!");
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable((BluetoothGattCharacteristic)object, arrby){
                final /* synthetic */ BluetoothGattCharacteristic val$characteristic;
                final /* synthetic */ byte[] val$value;
                {
                    this.val$characteristic = bluetoothGattCharacteristic;
                    this.val$value = arrby;
                }

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        if (n == 0) {
                            this.val$characteristic.setValue(this.val$value);
                        }
                        bluetoothGattCallback.onCharacteristicRead(BluetoothGatt.this, this.val$characteristic, n);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onCharacteristicWrite(String string2, int n, int n2) {
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            Object object = BluetoothGatt.this.mDeviceBusyLock;
            synchronized (object) {
                BluetoothGatt.this.mDeviceBusy = false;
            }
            object = BluetoothGatt.this;
            object = ((BluetoothGatt)object).getCharacteristicById(((BluetoothGatt)object).mDevice, n2);
            if (object == null) {
                return;
            }
            if (n == 5 || n == 15) {
                int n3 = BluetoothGatt.this.mAuthRetryState;
                int n4 = 2;
                if (n3 != 2) {
                    try {
                        if (BluetoothGatt.this.mAuthRetryState == 0) {
                            n4 = 1;
                        }
                        BluetoothGatt.this.mService.writeCharacteristic(BluetoothGatt.this.mClientIf, string2, n2, ((BluetoothGattCharacteristic)object).getWriteType(), n4, ((BluetoothGattCharacteristic)object).getValue());
                        BluetoothGatt.access$1408(BluetoothGatt.this);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(BluetoothGatt.TAG, "", remoteException);
                    }
                }
            }
            BluetoothGatt.this.mAuthRetryState = 0;
            BluetoothGatt.this.runOrQueueCallback(new Runnable((BluetoothGattCharacteristic)object, n){
                final /* synthetic */ BluetoothGattCharacteristic val$characteristic;
                final /* synthetic */ int val$status;
                {
                    this.val$characteristic = bluetoothGattCharacteristic;
                    this.val$status = n;
                }

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onCharacteristicWrite(BluetoothGatt.this, this.val$characteristic, this.val$status);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onClientConnectionState(final int n, final int n2, boolean bl, String object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onClientConnectionState() - status=");
            stringBuilder.append(n);
            stringBuilder.append(" clientIf=");
            stringBuilder.append(n2);
            stringBuilder.append(" device=");
            stringBuilder.append((String)object);
            Log.d(BluetoothGatt.TAG, stringBuilder.toString());
            if (!((String)object).equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            n2 = bl ? 2 : 0;
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onConnectionStateChange(BluetoothGatt.this, n, n2);
                    }
                }
            });
            object = BluetoothGatt.this.mStateLock;
            synchronized (object) {
                if (bl) {
                    BluetoothGatt.this.mConnState = 2;
                } else {
                    BluetoothGatt.this.mConnState = 0;
                }
            }
            object = BluetoothGatt.this.mDeviceBusyLock;
            synchronized (object) {
                BluetoothGatt.this.mDeviceBusy = false;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onClientRegistered(int n, int n2) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onClientRegistered() - status=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" clientIf=");
            ((StringBuilder)object).append(n2);
            Log.d(BluetoothGatt.TAG, ((StringBuilder)object).toString());
            BluetoothGatt.this.mClientIf = n2;
            boolean bl = false;
            if (n != 0) {
                BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                    @Override
                    public void run() {
                        BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                        if (bluetoothGattCallback != null) {
                            bluetoothGattCallback.onConnectionStateChange(BluetoothGatt.this, 257, 0);
                        }
                    }
                });
                object = BluetoothGatt.this.mStateLock;
                synchronized (object) {
                    BluetoothGatt.this.mConnState = 0;
                    return;
                }
            }
            try {
                object = BluetoothGatt.this.mService;
                n = BluetoothGatt.this.mClientIf;
                String string2 = BluetoothGatt.this.mDevice.getAddress();
                if (!BluetoothGatt.this.mAutoConnect) {
                    bl = true;
                }
                object.clientConnect(n, string2, bl, BluetoothGatt.this.mTransport, BluetoothGatt.this.mOpportunistic, BluetoothGatt.this.mPhy);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(BluetoothGatt.TAG, "", remoteException);
            }
        }

        @Override
        public void onConfigureMTU(String string2, final int n, final int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConfigureMTU() - Device=");
            stringBuilder.append(string2);
            stringBuilder.append(" mtu=");
            stringBuilder.append(n);
            stringBuilder.append(" status=");
            stringBuilder.append(n2);
            Log.d(BluetoothGatt.TAG, stringBuilder.toString());
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onMtuChanged(BluetoothGatt.this, n, n2);
                    }
                }
            });
        }

        @Override
        public void onConnectionUpdated(String string2, final int n, final int n2, final int n3, final int n4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionUpdated() - Device=");
            stringBuilder.append(string2);
            stringBuilder.append(" interval=");
            stringBuilder.append(n);
            stringBuilder.append(" latency=");
            stringBuilder.append(n2);
            stringBuilder.append(" timeout=");
            stringBuilder.append(n3);
            stringBuilder.append(" status=");
            stringBuilder.append(n4);
            Log.d(BluetoothGatt.TAG, stringBuilder.toString());
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onConnectionUpdated(BluetoothGatt.this, n, n2, n3, n4);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDescriptorRead(String string2, final int n, int n2, byte[] arrby) {
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            Object object = BluetoothGatt.this.mDeviceBusyLock;
            synchronized (object) {
                BluetoothGatt.this.mDeviceBusy = false;
            }
            object = BluetoothGatt.this;
            object = ((BluetoothGatt)object).getDescriptorById(((BluetoothGatt)object).mDevice, n2);
            if (object == null) {
                return;
            }
            if (n == 5 || n == 15) {
                int n3 = BluetoothGatt.this.mAuthRetryState;
                int n4 = 2;
                if (n3 != 2) {
                    try {
                        if (BluetoothGatt.this.mAuthRetryState == 0) {
                            n4 = 1;
                        }
                        BluetoothGatt.this.mService.readDescriptor(BluetoothGatt.this.mClientIf, string2, n2, n4);
                        BluetoothGatt.access$1408(BluetoothGatt.this);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(BluetoothGatt.TAG, "", remoteException);
                    }
                }
            }
            BluetoothGatt.this.mAuthRetryState = 0;
            BluetoothGatt.this.runOrQueueCallback(new Runnable((BluetoothGattDescriptor)object, arrby){
                final /* synthetic */ BluetoothGattDescriptor val$descriptor;
                final /* synthetic */ byte[] val$value;
                {
                    this.val$descriptor = bluetoothGattDescriptor;
                    this.val$value = arrby;
                }

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        if (n == 0) {
                            this.val$descriptor.setValue(this.val$value);
                        }
                        bluetoothGattCallback.onDescriptorRead(BluetoothGatt.this, this.val$descriptor, n);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onDescriptorWrite(String string2, int n, int n2) {
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            Object object = BluetoothGatt.this.mDeviceBusyLock;
            synchronized (object) {
                BluetoothGatt.this.mDeviceBusy = false;
            }
            object = BluetoothGatt.this;
            object = ((BluetoothGatt)object).getDescriptorById(((BluetoothGatt)object).mDevice, n2);
            if (object == null) {
                return;
            }
            if (n == 5 || n == 15) {
                int n3 = BluetoothGatt.this.mAuthRetryState;
                int n4 = 2;
                if (n3 != 2) {
                    try {
                        if (BluetoothGatt.this.mAuthRetryState == 0) {
                            n4 = 1;
                        }
                        BluetoothGatt.this.mService.writeDescriptor(BluetoothGatt.this.mClientIf, string2, n2, n4, ((BluetoothGattDescriptor)object).getValue());
                        BluetoothGatt.access$1408(BluetoothGatt.this);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(BluetoothGatt.TAG, "", remoteException);
                    }
                }
            }
            BluetoothGatt.this.mAuthRetryState = 0;
            BluetoothGatt.this.runOrQueueCallback(new Runnable((BluetoothGattDescriptor)object, n){
                final /* synthetic */ BluetoothGattDescriptor val$descriptor;
                final /* synthetic */ int val$status;
                {
                    this.val$descriptor = bluetoothGattDescriptor;
                    this.val$status = n;
                }

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onDescriptorWrite(BluetoothGatt.this, this.val$descriptor, this.val$status);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onExecuteWrite(String string2, final int n) {
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            Object object = BluetoothGatt.this.mDeviceBusyLock;
            synchronized (object) {
                BluetoothGatt.this.mDeviceBusy = false;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onReliableWriteCompleted(BluetoothGatt.this, n);
                    }
                }
            });
        }

        @Override
        public void onNotify(String object, int n, byte[] arrby) {
            if (!((String)object).equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            object = BluetoothGatt.this;
            if ((object = ((BluetoothGatt)object).getCharacteristicById(((BluetoothGatt)object).mDevice, n)) == null) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable((BluetoothGattCharacteristic)object, arrby){
                final /* synthetic */ BluetoothGattCharacteristic val$characteristic;
                final /* synthetic */ byte[] val$value;
                {
                    this.val$characteristic = bluetoothGattCharacteristic;
                    this.val$value = arrby;
                }

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        this.val$characteristic.setValue(this.val$value);
                        bluetoothGattCallback.onCharacteristicChanged(BluetoothGatt.this, this.val$characteristic);
                    }
                }
            });
        }

        @Override
        public void onPhyRead(String string2, final int n, final int n2, final int n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPhyRead() - status=");
            stringBuilder.append(n3);
            stringBuilder.append(" address=");
            stringBuilder.append(string2);
            stringBuilder.append(" txPhy=");
            stringBuilder.append(n);
            stringBuilder.append(" rxPhy=");
            stringBuilder.append(n2);
            Log.d(BluetoothGatt.TAG, stringBuilder.toString());
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onPhyRead(BluetoothGatt.this, n, n2, n3);
                    }
                }
            });
        }

        @Override
        public void onPhyUpdate(String string2, final int n, final int n2, final int n3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPhyUpdate() - status=");
            stringBuilder.append(n3);
            stringBuilder.append(" address=");
            stringBuilder.append(string2);
            stringBuilder.append(" txPhy=");
            stringBuilder.append(n);
            stringBuilder.append(" rxPhy=");
            stringBuilder.append(n2);
            Log.d(BluetoothGatt.TAG, stringBuilder.toString());
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onPhyUpdate(BluetoothGatt.this, n, n2, n3);
                    }
                }
            });
        }

        @Override
        public void onReadRemoteRssi(String string2, final int n, final int n2) {
            if (!string2.equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onReadRemoteRssi(BluetoothGatt.this, n, n2);
                    }
                }
            });
        }

        @Override
        public void onSearchComplete(String object4, List<BluetoothGattService> object2, final int n) {
            Object object3 = new StringBuilder();
            ((StringBuilder)object3).append("onSearchComplete() = Device=");
            ((StringBuilder)object3).append((String)object4);
            ((StringBuilder)object3).append(" Status=");
            ((StringBuilder)object3).append(n);
            Log.d(BluetoothGatt.TAG, ((StringBuilder)object3).toString());
            if (!((String)object4).equals(BluetoothGatt.this.mDevice.getAddress())) {
                return;
            }
            object4 = object2.iterator();
            while (object4.hasNext()) {
                ((BluetoothGattService)object4.next()).setDevice(BluetoothGatt.this.mDevice);
            }
            BluetoothGatt.this.mServices.addAll(object2);
            for (Object object4 : BluetoothGatt.this.mServices) {
                object3 = new ArrayList<BluetoothGattService>(((BluetoothGattService)object4).getIncludedServices());
                ((BluetoothGattService)object4).getIncludedServices().clear();
                object3 = ((ArrayList)object3).iterator();
                while (object3.hasNext()) {
                    BluetoothGattService bluetoothGattService = (BluetoothGattService)object3.next();
                    Object object5 = BluetoothGatt.this;
                    if ((object5 = ((BluetoothGatt)object5).getService(((BluetoothGatt)object5).mDevice, bluetoothGattService.getUuid(), bluetoothGattService.getInstanceId())) != null) {
                        ((BluetoothGattService)object4).addIncludedService((BluetoothGattService)object5);
                        continue;
                    }
                    Log.e(BluetoothGatt.TAG, "Broken GATT database: can't find included service.");
                }
            }
            BluetoothGatt.this.runOrQueueCallback(new Runnable(){

                @Override
                public void run() {
                    BluetoothGattCallback bluetoothGattCallback = BluetoothGatt.this.mCallback;
                    if (bluetoothGattCallback != null) {
                        bluetoothGattCallback.onServicesDiscovered(BluetoothGatt.this, n);
                    }
                }
            });
        }

    };
    @UnsupportedAppUsage
    private volatile BluetoothGattCallback mCallback;
    @UnsupportedAppUsage
    private int mClientIf;
    private int mConnState;
    private BluetoothDevice mDevice;
    @UnsupportedAppUsage
    private Boolean mDeviceBusy = false;
    private final Object mDeviceBusyLock = new Object();
    private Handler mHandler;
    private boolean mOpportunistic;
    private int mPhy;
    @UnsupportedAppUsage
    private IBluetoothGatt mService;
    private List<BluetoothGattService> mServices;
    private final Object mStateLock = new Object();
    @UnsupportedAppUsage
    private int mTransport;

    BluetoothGatt(IBluetoothGatt iBluetoothGatt, BluetoothDevice bluetoothDevice, int n, boolean bl, int n2) {
        this.mService = iBluetoothGatt;
        this.mDevice = bluetoothDevice;
        this.mTransport = n;
        this.mPhy = n2;
        this.mOpportunistic = bl;
        this.mServices = new ArrayList<BluetoothGattService>();
        this.mConnState = 0;
        this.mAuthRetryState = 0;
    }

    static /* synthetic */ int access$1408(BluetoothGatt bluetoothGatt) {
        int n = bluetoothGatt.mAuthRetryState;
        bluetoothGatt.mAuthRetryState = n + 1;
        return n;
    }

    private boolean registerApp(BluetoothGattCallback object, Handler object2) {
        Log.d(TAG, "registerApp()");
        if (this.mService == null) {
            return false;
        }
        this.mCallback = object;
        this.mHandler = object2;
        object = UUID.randomUUID();
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("registerApp() - UUID=");
        ((StringBuilder)object2).append(object);
        Log.d(TAG, ((StringBuilder)object2).toString());
        try {
            IBluetoothGatt iBluetoothGatt = this.mService;
            object2 = new ParcelUuid((UUID)object);
            iBluetoothGatt.registerClient((ParcelUuid)object2, this.mBluetoothGattCallback);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    private void runOrQueueCallback(Runnable runnable) {
        Handler handler = this.mHandler;
        if (handler == null) {
            try {
                runnable.run();
            }
            catch (Exception exception) {
                Log.w(TAG, "Unhandled exception in callback", exception);
            }
        } else {
            handler.post(runnable);
        }
    }

    @UnsupportedAppUsage
    private void unregisterApp() {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("unregisterApp() - mClientIf=");
        ((StringBuilder)object).append(this.mClientIf);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mClientIf) != 0) {
            try {
                this.mCallback = null;
                object.unregisterClient(n);
                this.mClientIf = 0;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    public void abortReliableWrite() {
        int n;
        IBluetoothGatt iBluetoothGatt = this.mService;
        if (iBluetoothGatt != null && (n = this.mClientIf) != 0) {
            try {
                iBluetoothGatt.endReliableWrite(n, this.mDevice.getAddress(), false);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    @Deprecated
    public void abortReliableWrite(BluetoothDevice bluetoothDevice) {
        this.abortReliableWrite();
    }

    public boolean beginReliableWrite() {
        int n;
        IBluetoothGatt iBluetoothGatt = this.mService;
        if (iBluetoothGatt != null && (n = this.mClientIf) != 0) {
            try {
                iBluetoothGatt.beginReliableWrite(n, this.mDevice.getAddress());
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public void close() {
        Log.d(TAG, "close()");
        this.unregisterApp();
        this.mConnState = 4;
        this.mAuthRetryState = 0;
    }

    public boolean connect() {
        try {
            this.mService.clientConnect(this.mClientIf, this.mDevice.getAddress(), false, this.mTransport, this.mOpportunistic, this.mPhy);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    boolean connect(Boolean serializable, BluetoothGattCallback object, Handler handler) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("connect() - device: ");
        ((StringBuilder)object2).append(this.mDevice.getAddress());
        ((StringBuilder)object2).append(", auto: ");
        ((StringBuilder)object2).append(serializable);
        Log.d(TAG, ((StringBuilder)object2).toString());
        object2 = this.mStateLock;
        synchronized (object2) {
            if (this.mConnState != 0) {
                serializable = new IllegalStateException("Not idle");
                throw serializable;
            }
            this.mConnState = 1;
        }
        this.mAutoConnect = (Boolean)serializable;
        if (!this.registerApp((BluetoothGattCallback)object, handler)) {
            object = this.mStateLock;
            synchronized (object) {
                this.mConnState = 0;
            }
            Log.e(TAG, "Failed to register callback");
            return false;
        }
        return true;
    }

    public void disconnect() {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("cancelOpen() - device: ");
        ((StringBuilder)object).append(this.mDevice.getAddress());
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mClientIf) != 0) {
            try {
                object.clientDisconnect(n, this.mDevice.getAddress());
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            return;
        }
    }

    public boolean discoverServiceByUuid(UUID uUID) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("discoverServiceByUuid() - device: ");
        ((StringBuilder)object).append(this.mDevice.getAddress());
        Log.d(TAG, ((StringBuilder)object).toString());
        if (this.mService != null && this.mClientIf != 0) {
            this.mServices.clear();
            try {
                IBluetoothGatt iBluetoothGatt = this.mService;
                int n = this.mClientIf;
                String string2 = this.mDevice.getAddress();
                object = new ParcelUuid(uUID);
                iBluetoothGatt.discoverServiceByUuid(n, string2, (ParcelUuid)object);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public boolean discoverServices() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("discoverServices() - device: ");
        stringBuilder.append(this.mDevice.getAddress());
        Log.d(TAG, stringBuilder.toString());
        if (this.mService != null && this.mClientIf != 0) {
            this.mServices.clear();
            try {
                this.mService.discoverServices(this.mClientIf, this.mDevice.getAddress());
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean executeReliableWrite() {
        if (this.mService == null) return false;
        if (this.mClientIf == 0) {
            return false;
        }
        Object object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            this.mService.endReliableWrite(this.mClientIf, this.mDevice.getAddress(), true);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

    BluetoothGattCharacteristic getCharacteristicById(BluetoothDevice object, int n) {
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

    BluetoothGattDescriptor getDescriptorById(BluetoothDevice object, int n) {
        object = this.mServices.iterator();
        while (object.hasNext()) {
            Iterator<BluetoothGattCharacteristic> iterator = ((BluetoothGattService)object.next()).getCharacteristics().iterator();
            while (iterator.hasNext()) {
                for (BluetoothGattDescriptor bluetoothGattDescriptor : iterator.next().getDescriptors()) {
                    if (bluetoothGattDescriptor.getInstanceId() != n) continue;
                    return bluetoothGattDescriptor;
                }
            }
        }
        return null;
    }

    public BluetoothDevice getDevice() {
        return this.mDevice;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] arrn) {
        throw new UnsupportedOperationException("Use BluetoothManager#getDevicesMatchingConnectionStates instead.");
    }

    BluetoothGattService getService(BluetoothDevice bluetoothDevice, UUID uUID, int n) {
        for (BluetoothGattService bluetoothGattService : this.mServices) {
            if (!bluetoothGattService.getDevice().equals(bluetoothDevice) || bluetoothGattService.getInstanceId() != n || !bluetoothGattService.getUuid().equals(uUID)) continue;
            return bluetoothGattService;
        }
        return null;
    }

    public BluetoothGattService getService(UUID uUID) {
        for (BluetoothGattService bluetoothGattService : this.mServices) {
            if (!bluetoothGattService.getDevice().equals(this.mDevice) || !bluetoothGattService.getUuid().equals(uUID)) continue;
            return bluetoothGattService;
        }
        return null;
    }

    public List<BluetoothGattService> getServices() {
        ArrayList<BluetoothGattService> arrayList = new ArrayList<BluetoothGattService>();
        for (BluetoothGattService bluetoothGattService : this.mServices) {
            if (!bluetoothGattService.getDevice().equals(this.mDevice)) continue;
            arrayList.add(bluetoothGattService);
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean readCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if ((bluetoothGattCharacteristic.getProperties() & 2) == 0) {
            return false;
        }
        if (this.mService == null) return false;
        if (this.mClientIf == 0) {
            return false;
        }
        Object object = bluetoothGattCharacteristic.getService();
        if (object == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = ((BluetoothGattService)object).getDevice();
        if (bluetoothDevice == null) {
            return false;
        }
        object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            this.mService.readCharacteristic(this.mClientIf, bluetoothDevice.getAddress(), bluetoothGattCharacteristic.getInstanceId(), 0);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean readDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.mService == null) return false;
        if (this.mClientIf == 0) {
            return false;
        }
        Object object = bluetoothGattDescriptor.getCharacteristic();
        if (object == null) {
            return false;
        }
        if ((object = ((BluetoothGattCharacteristic)object).getService()) == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = ((BluetoothGattService)object).getDevice();
        if (bluetoothDevice == null) {
            return false;
        }
        object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            this.mService.readDescriptor(this.mClientIf, bluetoothDevice.getAddress(), bluetoothGattDescriptor.getInstanceId(), 0);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

    public void readPhy() {
        try {
            this.mService.clientReadPhy(this.mClientIf, this.mDevice.getAddress());
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
        }
    }

    public boolean readRemoteRssi() {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("readRssi() - device: ");
        ((StringBuilder)object).append(this.mDevice.getAddress());
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mClientIf) != 0) {
            try {
                object.readRemoteRssi(n, this.mDevice.getAddress());
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean readUsingCharacteristicUuid(UUID uUID, int n, int n2) {
        if (this.mService == null) return false;
        if (this.mClientIf == 0) {
            return false;
        }
        Object object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            IBluetoothGatt iBluetoothGatt = this.mService;
            int n3 = this.mClientIf;
            String string2 = this.mDevice.getAddress();
            object = new ParcelUuid(uUID);
            iBluetoothGatt.readUsingCharacteristicUuid(n3, string2, (ParcelUuid)object, n, n2, 0);
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean refresh() {
        int n;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("refresh() - device: ");
        ((StringBuilder)object).append(this.mDevice.getAddress());
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n = this.mClientIf) != 0) {
            try {
                object.refreshDevice(n, this.mDevice.getAddress());
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public boolean requestConnectionPriority(int n) {
        if (n >= 0 && n <= 2) {
            int n2;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("requestConnectionPriority() - params: ");
            ((StringBuilder)object).append(n);
            Log.d(TAG, ((StringBuilder)object).toString());
            object = this.mService;
            if (object != null && (n2 = this.mClientIf) != 0) {
                try {
                    object.connectionParameterUpdate(n2, this.mDevice.getAddress(), n);
                    return true;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "", remoteException);
                    return false;
                }
            }
            return false;
        }
        throw new IllegalArgumentException("connectionPriority not within valid range");
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean requestLeConnectionUpdate(int n, int n2, int n3, int n4, int n5, int n6) {
        void var7_10;
        block5 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("requestLeConnectionUpdate() - min=(");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(")");
            ((StringBuilder)object).append((double)n * 1.25);
            ((StringBuilder)object).append("msec, max=(");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(")");
            ((StringBuilder)object).append((double)n2 * 1.25);
            ((StringBuilder)object).append("msec, latency=");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(", timeout=");
            ((StringBuilder)object).append(n4);
            ((StringBuilder)object).append("msec, min_ce=");
            ((StringBuilder)object).append(n5);
            ((StringBuilder)object).append(", max_ce=");
            ((StringBuilder)object).append(n6);
            Log.d(TAG, ((StringBuilder)object).toString());
            object = this.mService;
            if (object == null) return false;
            int n7 = this.mClientIf;
            if (n7 == 0) {
                return false;
            }
            String string2 = this.mDevice.getAddress();
            try {
                object.leConnectionUpdate(n7, string2, n, n2, n3, n4, n5, n6);
                return true;
            }
            catch (RemoteException remoteException) {}
            break block5;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.e(TAG, "", (Throwable)var7_10);
        return false;
    }

    public boolean requestMtu(int n) {
        int n2;
        Object object = new StringBuilder();
        ((StringBuilder)object).append("configureMTU() - device: ");
        ((StringBuilder)object).append(this.mDevice.getAddress());
        ((StringBuilder)object).append(" mtu: ");
        ((StringBuilder)object).append(n);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && (n2 = this.mClientIf) != 0) {
            try {
                object.configureMTU(n2, this.mDevice.getAddress(), n);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public boolean setCharacteristicNotification(BluetoothGattCharacteristic bluetoothGattCharacteristic, boolean bl) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setCharacteristicNotification() - uuid: ");
        ((StringBuilder)object).append(bluetoothGattCharacteristic.getUuid());
        ((StringBuilder)object).append(" enable: ");
        ((StringBuilder)object).append(bl);
        Log.d(TAG, ((StringBuilder)object).toString());
        if (this.mService != null && this.mClientIf != 0) {
            object = bluetoothGattCharacteristic.getService();
            if (object == null) {
                return false;
            }
            if ((object = ((BluetoothGattService)object).getDevice()) == null) {
                return false;
            }
            try {
                this.mService.registerForNotification(this.mClientIf, ((BluetoothDevice)object).getAddress(), bluetoothGattCharacteristic.getInstanceId(), bl);
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        return false;
    }

    public void setPreferredPhy(int n, int n2, int n3) {
        try {
            this.mService.clientSetPreferredPhy(this.mClientIf, this.mDevice.getAddress(), n, n2, n3);
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
    public boolean writeCharacteristic(BluetoothGattCharacteristic bluetoothGattCharacteristic) {
        if ((bluetoothGattCharacteristic.getProperties() & 8) == 0 && (bluetoothGattCharacteristic.getProperties() & 4) == 0) {
            return false;
        }
        if (this.mService == null) return false;
        if (this.mClientIf == 0) return false;
        if (bluetoothGattCharacteristic.getValue() == null) {
            return false;
        }
        Object object = bluetoothGattCharacteristic.getService();
        if (object == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = ((BluetoothGattService)object).getDevice();
        if (bluetoothDevice == null) {
            return false;
        }
        object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            this.mService.writeCharacteristic(this.mClientIf, bluetoothDevice.getAddress(), bluetoothGattCharacteristic.getInstanceId(), bluetoothGattCharacteristic.getWriteType(), 0, bluetoothGattCharacteristic.getValue());
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean writeDescriptor(BluetoothGattDescriptor bluetoothGattDescriptor) {
        if (this.mService == null) return false;
        if (this.mClientIf == 0) return false;
        if (bluetoothGattDescriptor.getValue() == null) {
            return false;
        }
        Object object = bluetoothGattDescriptor.getCharacteristic();
        if (object == null) {
            return false;
        }
        if ((object = ((BluetoothGattCharacteristic)object).getService()) == null) {
            return false;
        }
        BluetoothDevice bluetoothDevice = ((BluetoothGattService)object).getDevice();
        if (bluetoothDevice == null) {
            return false;
        }
        object = this.mDeviceBusyLock;
        synchronized (object) {
            if (this.mDeviceBusy.booleanValue()) {
                return false;
            }
            this.mDeviceBusy = true;
        }
        try {
            this.mService.writeDescriptor(this.mClientIf, bluetoothDevice.getAddress(), bluetoothGattDescriptor.getInstanceId(), 0, bluetoothGattDescriptor.getValue());
            return true;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            this.mDeviceBusy = false;
            return false;
        }
    }

}

