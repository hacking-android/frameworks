/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothA2dpSink;
import android.bluetooth.BluetoothActivityEnergyInfo;
import android.bluetooth.BluetoothAvrcpController;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothHeadsetClient;
import android.bluetooth.BluetoothHearingAid;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidHost;
import android.bluetooth.BluetoothMap;
import android.bluetooth.BluetoothMapClient;
import android.bluetooth.BluetoothPan;
import android.bluetooth.BluetoothPbapClient;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSap;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.IBluetooth;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.IBluetoothManagerCallback;
import android.bluetooth.IBluetoothMetadataListener;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.bluetooth._$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc;
import android.bluetooth._$$Lambda$BluetoothAdapter$2$INSd_aND_SGWhhPZUtIqya_Uxw4;
import android.bluetooth._$$Lambda$BluetoothAdapter$3qDRCAtPJRu3UbUEFsHnCxkioak;
import android.bluetooth._$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.PeriodicAdvertisingManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanRecord;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.SynchronousResultReceiver;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Pair;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public final class BluetoothAdapter {
    public static final String ACTION_BLE_ACL_CONNECTED = "android.bluetooth.adapter.action.BLE_ACL_CONNECTED";
    public static final String ACTION_BLE_ACL_DISCONNECTED = "android.bluetooth.adapter.action.BLE_ACL_DISCONNECTED";
    @SystemApi
    public static final String ACTION_BLE_STATE_CHANGED = "android.bluetooth.adapter.action.BLE_STATE_CHANGED";
    public static final String ACTION_BLUETOOTH_ADDRESS_CHANGED = "android.bluetooth.adapter.action.BLUETOOTH_ADDRESS_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.adapter.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_DISCOVERY_FINISHED = "android.bluetooth.adapter.action.DISCOVERY_FINISHED";
    public static final String ACTION_DISCOVERY_STARTED = "android.bluetooth.adapter.action.DISCOVERY_STARTED";
    public static final String ACTION_LOCAL_NAME_CHANGED = "android.bluetooth.adapter.action.LOCAL_NAME_CHANGED";
    @SystemApi
    public static final String ACTION_REQUEST_BLE_SCAN_ALWAYS_AVAILABLE = "android.bluetooth.adapter.action.REQUEST_BLE_SCAN_ALWAYS_AVAILABLE";
    public static final String ACTION_REQUEST_DISABLE = "android.bluetooth.adapter.action.REQUEST_DISABLE";
    public static final String ACTION_REQUEST_DISCOVERABLE = "android.bluetooth.adapter.action.REQUEST_DISCOVERABLE";
    public static final String ACTION_REQUEST_ENABLE = "android.bluetooth.adapter.action.REQUEST_ENABLE";
    public static final String ACTION_SCAN_MODE_CHANGED = "android.bluetooth.adapter.action.SCAN_MODE_CHANGED";
    public static final String ACTION_STATE_CHANGED = "android.bluetooth.adapter.action.STATE_CHANGED";
    private static final int ADDRESS_LENGTH = 17;
    public static final String BLUETOOTH_MANAGER_SERVICE = "bluetooth_manager";
    private static final boolean DBG = true;
    public static final String DEFAULT_MAC_ADDRESS = "02:00:00:00:00:00";
    public static final int ERROR = Integer.MIN_VALUE;
    public static final String EXTRA_BLUETOOTH_ADDRESS = "android.bluetooth.adapter.extra.BLUETOOTH_ADDRESS";
    public static final String EXTRA_CONNECTION_STATE = "android.bluetooth.adapter.extra.CONNECTION_STATE";
    public static final String EXTRA_DISCOVERABLE_DURATION = "android.bluetooth.adapter.extra.DISCOVERABLE_DURATION";
    public static final String EXTRA_LOCAL_NAME = "android.bluetooth.adapter.extra.LOCAL_NAME";
    public static final String EXTRA_PREVIOUS_CONNECTION_STATE = "android.bluetooth.adapter.extra.PREVIOUS_CONNECTION_STATE";
    public static final String EXTRA_PREVIOUS_SCAN_MODE = "android.bluetooth.adapter.extra.PREVIOUS_SCAN_MODE";
    public static final String EXTRA_PREVIOUS_STATE = "android.bluetooth.adapter.extra.PREVIOUS_STATE";
    public static final String EXTRA_SCAN_MODE = "android.bluetooth.adapter.extra.SCAN_MODE";
    public static final String EXTRA_STATE = "android.bluetooth.adapter.extra.STATE";
    public static final int IO_CAPABILITY_IN = 2;
    public static final int IO_CAPABILITY_IO = 1;
    public static final int IO_CAPABILITY_KBDISP = 4;
    public static final int IO_CAPABILITY_MAX = 5;
    public static final int IO_CAPABILITY_NONE = 3;
    public static final int IO_CAPABILITY_OUT = 0;
    public static final int IO_CAPABILITY_UNKNOWN = 255;
    public static final UUID LE_PSM_CHARACTERISTIC_UUID = UUID.fromString("2d410339-82b6-42aa-b34e-e2e01df8cc1a");
    public static final int SCAN_MODE_CONNECTABLE = 21;
    public static final int SCAN_MODE_CONNECTABLE_DISCOVERABLE = 23;
    public static final int SCAN_MODE_NONE = 20;
    public static final int SOCKET_CHANNEL_AUTO_STATIC_NO_SDP = -2;
    public static final int STATE_BLE_ON = 15;
    public static final int STATE_BLE_TURNING_OFF = 16;
    public static final int STATE_BLE_TURNING_ON = 14;
    public static final int STATE_CONNECTED = 2;
    public static final int STATE_CONNECTING = 1;
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_DISCONNECTING = 3;
    public static final int STATE_OFF = 10;
    public static final int STATE_ON = 12;
    public static final int STATE_TURNING_OFF = 13;
    public static final int STATE_TURNING_ON = 11;
    private static final String TAG = "BluetoothAdapter";
    private static final boolean VDBG = false;
    private static BluetoothAdapter sAdapter;
    private static BluetoothLeAdvertiser sBluetoothLeAdvertiser;
    private static BluetoothLeScanner sBluetoothLeScanner;
    private static final IBluetoothMetadataListener sBluetoothMetadataListener;
    private static final Map<BluetoothDevice, List<Pair<OnMetadataChangedListener, Executor>>> sMetadataListeners;
    private static PeriodicAdvertisingManager sPeriodicAdvertisingManager;
    private Context mContext;
    private final Map<LeScanCallback, ScanCallback> mLeScanClients;
    private final Object mLock = new Object();
    private final IBluetoothManagerCallback mManagerCallback = new IBluetoothManagerCallback.Stub(){

        public /* synthetic */ void lambda$onBluetoothServiceUp$0$BluetoothAdapter$2(BluetoothDevice bluetoothDevice, List list) {
            try {
                BluetoothAdapter.this.mService.registerMetadataListener(sBluetoothMetadataListener, bluetoothDevice);
            }
            catch (RemoteException remoteException) {
                Log.e(BluetoothAdapter.TAG, "Failed to register metadata listener", remoteException);
            }
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onBluetoothServiceDown() {
            block12 : {
                var1_1 /* !! */  = new StringBuilder();
                var1_1 /* !! */ .append("onBluetoothServiceDown: ");
                var1_1 /* !! */ .append(BluetoothAdapter.access$200(BluetoothAdapter.this));
                Log.d("BluetoothAdapter", var1_1 /* !! */ .toString());
                BluetoothAdapter.access$100(BluetoothAdapter.this).writeLock().lock();
                BluetoothAdapter.access$202(BluetoothAdapter.this, null);
                if (BluetoothAdapter.access$400(BluetoothAdapter.this) != null) {
                    BluetoothAdapter.access$400(BluetoothAdapter.this).clear();
                }
                if (BluetoothAdapter.access$500() != null) {
                    BluetoothAdapter.access$500().cleanup();
                }
                if (BluetoothAdapter.access$600() != null) {
                    BluetoothAdapter.access$600().cleanup();
                }
                var1_1 /* !! */  = BluetoothAdapter.access$300(BluetoothAdapter.this);
                break block12;
                finally {
                    BluetoothAdapter.access$100(BluetoothAdapter.this).writeLock().unlock();
                }
            }
            // MONITORENTER : var1_1 /* !! */ 
            var2_3 = BluetoothAdapter.access$300(BluetoothAdapter.this).iterator();
            do {
                if (!var2_3.hasNext()) {
                    // MONITOREXIT : var1_1 /* !! */ 
                    return;
                }
                var3_4 = (IBluetoothManagerCallback)var2_3.next();
                if (var3_4 == null) ** GOTO lbl35
                try {
                    var3_4.onBluetoothServiceDown();
                    continue;
lbl35: // 1 sources:
                    Log.d("BluetoothAdapter", "onBluetoothServiceDown: cb is null!");
                }
                catch (Exception var3_5) {
                    Log.e("BluetoothAdapter", "", var3_5);
                    continue;
                }
                break;
            } while (true);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onBluetoothServiceUp(IBluetooth var1_1) {
            var2_2 = new StringBuilder();
            var2_2.append("onBluetoothServiceUp: ");
            var2_2.append(var1_1);
            Log.d("BluetoothAdapter", var2_2.toString());
            BluetoothAdapter.access$100(BluetoothAdapter.this).writeLock().lock();
            BluetoothAdapter.access$202(BluetoothAdapter.this, (IBluetooth)var1_1);
            BluetoothAdapter.access$100(BluetoothAdapter.this).writeLock().unlock();
            var2_2 = BluetoothAdapter.access$300(BluetoothAdapter.this);
            // MONITORENTER : var2_2
            var3_3 = BluetoothAdapter.access$300(BluetoothAdapter.this).iterator();
            do {
                if (!var3_3.hasNext()) {
                    // MONITOREXIT : var2_2
                    var1_1 = BluetoothAdapter.access$000();
                    // MONITORENTER : var1_1
                    var2_2 = BluetoothAdapter.access$000();
                    var3_3 = new _$$Lambda$BluetoothAdapter$2$INSd_aND_SGWhhPZUtIqya_Uxw4(this);
                    var2_2.forEach(var3_3);
                    // MONITOREXIT : var1_1
                    return;
                }
                var4_4 = (IBluetoothManagerCallback)var3_3.next();
                if (var4_4 == null) ** GOTO lbl30
                try {
                    var4_4.onBluetoothServiceUp((IBluetooth)var1_1);
                    continue;
lbl30: // 1 sources:
                    Log.d("BluetoothAdapter", "onBluetoothServiceUp: cb is null!");
                }
                catch (Exception var4_5) {
                    Log.e("BluetoothAdapter", "", var4_5);
                    continue;
                }
                break;
            } while (true);
        }

        @Override
        public void onBrEdrDown() {
        }
    };
    private final IBluetoothManager mManagerService;
    private final ArrayList<IBluetoothManagerCallback> mProxyServiceStateCallbacks = new ArrayList();
    @UnsupportedAppUsage
    private IBluetooth mService;
    private final ReentrantReadWriteLock mServiceLock = new ReentrantReadWriteLock();
    private final IBinder mToken;

    static {
        sMetadataListeners = new HashMap<BluetoothDevice, List<Pair<OnMetadataChangedListener, Executor>>>();
        sBluetoothMetadataListener = new IBluetoothMetadataListener.Stub(){

            static /* synthetic */ void lambda$onMetadataChanged$0(OnMetadataChangedListener onMetadataChangedListener, BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
                onMetadataChangedListener.onMetadataChanged(bluetoothDevice, n, arrby);
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onMetadataChanged(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
                Map map = sMetadataListeners;
                synchronized (map) {
                    if (sMetadataListeners.containsKey(bluetoothDevice)) {
                        for (Object object : (List)sMetadataListeners.get(bluetoothDevice)) {
                            OnMetadataChangedListener onMetadataChangedListener = (OnMetadataChangedListener)((Pair)object).first;
                            object = (Executor)((Pair)object).second;
                            _$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc _$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc = new _$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc(onMetadataChangedListener, bluetoothDevice, n, arrby);
                            object.execute(_$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc);
                        }
                    }
                    return;
                }
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    BluetoothAdapter(IBluetoothManager iBluetoothManager) {
        if (iBluetoothManager == null) {
            throw new IllegalArgumentException("bluetooth manager service is null");
        }
        try {
            try {
                this.mServiceLock.writeLock().lock();
                this.mService = iBluetoothManager.registerAdapter(this.mManagerCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            this.mServiceLock.writeLock().unlock();
            this.mManagerService = iBluetoothManager;
            this.mLeScanClients = new HashMap<LeScanCallback, ScanCallback>();
            this.mToken = new Binder();
            return;
        }
        catch (Throwable throwable2) {}
        this.mServiceLock.writeLock().unlock();
        throw throwable2;
    }

    static /* synthetic */ ReentrantReadWriteLock access$100(BluetoothAdapter bluetoothAdapter) {
        return bluetoothAdapter.mServiceLock;
    }

    static /* synthetic */ IBluetooth access$202(BluetoothAdapter bluetoothAdapter, IBluetooth iBluetooth) {
        bluetoothAdapter.mService = iBluetooth;
        return iBluetooth;
    }

    static /* synthetic */ ArrayList access$300(BluetoothAdapter bluetoothAdapter) {
        return bluetoothAdapter.mProxyServiceStateCallbacks;
    }

    static /* synthetic */ Map access$400(BluetoothAdapter bluetoothAdapter) {
        return bluetoothAdapter.mLeScanClients;
    }

    static /* synthetic */ BluetoothLeAdvertiser access$500() {
        return sBluetoothLeAdvertiser;
    }

    static /* synthetic */ BluetoothLeScanner access$600() {
        return sBluetoothLeScanner;
    }

    public static boolean checkBluetoothAddress(String string2) {
        if (string2 != null && string2.length() == 17) {
            for (int i = 0; i < 17; ++i) {
                char c = string2.charAt(i);
                int n = i % 3;
                if (n != 0 && n != 1) {
                    if (n != 2 || c == ':') continue;
                    return false;
                }
                if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F') {
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private BluetoothServerSocket createNewRfcommSocketAndRecord(String charSequence, UUID object, boolean bl, boolean bl2) throws IOException {
        object = new BluetoothServerSocket(1, bl, bl2, new ParcelUuid((UUID)object));
        ((BluetoothServerSocket)object).setServiceName((String)charSequence);
        int n = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == 0) {
            return object;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Error: ");
        ((StringBuilder)charSequence).append(n);
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public static BluetoothAdapter getDefaultAdapter() {
        synchronized (BluetoothAdapter.class) {
            Object object;
            if (sAdapter == null) {
                object = ServiceManager.getService(BLUETOOTH_MANAGER_SERVICE);
                if (object != null) {
                    IBluetoothManager iBluetoothManager = IBluetoothManager.Stub.asInterface((IBinder)object);
                    sAdapter = object = new BluetoothAdapter(iBluetoothManager);
                } else {
                    Log.e(TAG, "Bluetooth binder is null");
                }
            }
            object = sAdapter;
            return object;
        }
    }

    private String getOpPackageName() {
        Context context = this.mContext;
        if (context != null) {
            return context.getOpPackageName();
        }
        return ActivityThread.currentOpPackageName();
    }

    private boolean isHearingAidProfileSupported() {
        try {
            boolean bl = this.mManagerService.isHearingAidProfileSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote expection when calling isHearingAidProfileSupported", remoteException);
            return false;
        }
    }

    static /* synthetic */ boolean lambda$addOnMetadataChangedListener$0(OnMetadataChangedListener onMetadataChangedListener, Pair pair) {
        return ((OnMetadataChangedListener)pair.first).equals(onMetadataChangedListener);
    }

    static /* synthetic */ boolean lambda$removeOnMetadataChangedListener$1(OnMetadataChangedListener onMetadataChangedListener, Pair pair) {
        return ((OnMetadataChangedListener)pair.first).equals(onMetadataChangedListener);
    }

    public static BluetoothServerSocket listenUsingScoOn() throws IOException {
        BluetoothServerSocket bluetoothServerSocket = new BluetoothServerSocket(2, false, false, -1);
        bluetoothServerSocket.mSocket.bindListen();
        return bluetoothServerSocket;
    }

    public static String nameForState(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?!?!? (");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            case 16: {
                return "BLE_TURNING_OFF";
            }
            case 15: {
                return "BLE_ON";
            }
            case 14: {
                return "BLE_TURNING_ON";
            }
            case 13: {
                return "TURNING_OFF";
            }
            case 12: {
                return "ON";
            }
            case 11: {
                return "TURNING_ON";
            }
            case 10: 
        }
        return "OFF";
    }

    private Set<BluetoothDevice> toDeviceSet(BluetoothDevice[] arrbluetoothDevice) {
        return Collections.unmodifiableSet(new HashSet<BluetoothDevice>(Arrays.asList(arrbluetoothDevice)));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @SystemApi
    public boolean addOnMetadataChangedListener(BluetoothDevice var1_1, Executor var2_2, OnMetadataChangedListener var3_5) {
        block15 : {
            block14 : {
                Log.d("BluetoothAdapter", "addOnMetadataChangedListener()");
                var4_6 = this.mService;
                if (var4_6 == null) {
                    Log.e("BluetoothAdapter", "Bluetooth is not enabled. Cannot register metadata listener");
                    return false;
                }
                if (var3_5 == null) throw new NullPointerException("listener is null");
                if (var1_1 == null) throw new NullPointerException("device is null");
                if (var2_2 == null) throw new NullPointerException("executor is null");
                var5_7 = BluetoothAdapter.sMetadataListeners;
                // MONITORENTER : var5_7
                var6_8 = BluetoothAdapter.sMetadataListeners.get(var1_1);
                if (var6_8 == null) {
                    var6_8 = new ArrayList<Pair<OnMetadataChangedListener, Map<BluetoothDevice, List<Pair<OnMetadataChangedListener, Executor>>>>>();
                    BluetoothAdapter.sMetadataListeners.put((BluetoothDevice)var1_1, var6_8);
                } else {
                    var7_9 = var6_8.stream();
                    if (var7_9.anyMatch(var8_10 = new _$$Lambda$BluetoothAdapter$3qDRCAtPJRu3UbUEFsHnCxkioak(var3_5))) {
                        var1_1 = new IllegalArgumentException("listener was already regestered for the device");
                        throw var1_1;
                    }
                }
                var7_9 = new Object(var3_5, var2_2);
                var6_8.add((Pair<OnMetadataChangedListener, Map<BluetoothDevice, List<Pair<OnMetadataChangedListener, Executor>>>>)var7_9);
                var9_11 = false;
                var10_12 = false;
                var11_13 = var10_12 = (var11_13 = var4_6.registerMetadataListener(BluetoothAdapter.sBluetoothMetadataListener, (BluetoothDevice)var1_1));
                if (var10_12) break block14;
                var6_8.remove(var7_9);
                var11_13 = var10_12;
                if (!var6_8.isEmpty()) return var11_13;
                {
                    var2_2 = BluetoothAdapter.sMetadataListeners;
                    var11_13 = var10_12;
lbl37: // 2 sources:
                    do {
                        var2_2.remove(var1_1);
                        return var11_13;
                        break;
                    } while (true);
                }
                {
                    catch (Throwable var2_3) {
                        break block15;
                    }
                    catch (RemoteException var2_4) {}
                    {
                        Log.e("BluetoothAdapter", "registerMetadataListener fail", var2_4);
                        var11_13 = var9_11;
                        if (false) break block14;
                        var6_8.remove(var7_9);
                    }
                    var11_13 = var9_11;
                    if (!var6_8.isEmpty()) break block14;
                    var2_2 = BluetoothAdapter.sMetadataListeners;
                    var11_13 = var10_12;
                    ** continue;
                }
            }
            // MONITOREXIT : var5_7
            return var11_13;
        }
        if (false != false) throw var2_3;
        var6_8.remove(var7_9);
        if (var6_8.isEmpty() == false) throw var2_3;
        BluetoothAdapter.sMetadataListeners.remove(var1_1);
        throw var2_3;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean cancelDiscovery() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.cancelDiscovery();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public boolean changeApplicationBluetoothState(boolean bl, BluetoothStateChangeCallback bluetoothStateChangeCallback) {
        return false;
    }

    public void closeProfileProxy(int n, BluetoothProfile bluetoothProfile) {
        if (bluetoothProfile == null) {
            return;
        }
        switch (n) {
            default: {
                break;
            }
            case 21: {
                ((BluetoothHearingAid)bluetoothProfile).close();
                break;
            }
            case 19: {
                ((BluetoothHidDevice)bluetoothProfile).close();
                break;
            }
            case 18: {
                ((BluetoothMapClient)bluetoothProfile).close();
                break;
            }
            case 17: {
                ((BluetoothPbapClient)bluetoothProfile).close();
                break;
            }
            case 16: {
                ((BluetoothHeadsetClient)bluetoothProfile).close();
                break;
            }
            case 12: {
                ((BluetoothAvrcpController)bluetoothProfile).close();
                break;
            }
            case 11: {
                ((BluetoothA2dpSink)bluetoothProfile).close();
                break;
            }
            case 10: {
                ((BluetoothSap)bluetoothProfile).close();
                break;
            }
            case 9: {
                ((BluetoothMap)bluetoothProfile).close();
                break;
            }
            case 8: {
                ((BluetoothGattServer)bluetoothProfile).close();
                break;
            }
            case 7: {
                ((BluetoothGatt)bluetoothProfile).close();
                break;
            }
            case 5: {
                ((BluetoothPan)bluetoothProfile).close();
                break;
            }
            case 4: {
                ((BluetoothHidHost)bluetoothProfile).close();
                break;
            }
            case 2: {
                ((BluetoothA2dp)bluetoothProfile).close();
                break;
            }
            case 1: {
                ((BluetoothHeadset)bluetoothProfile).close();
            }
        }
    }

    public boolean disable() {
        try {
            boolean bl = this.mManagerService.disable(ActivityThread.currentPackageName(), true);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean disable(boolean bl) {
        try {
            bl = this.mManagerService.disable(ActivityThread.currentPackageName(), bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean disableBLE() {
        if (!this.isBleScanAlwaysAvailable()) {
            return false;
        }
        int n = this.getLeState();
        if (n != 12 && n != 15) {
            Log.d(TAG, "disableBLE(): Already disabled");
            return false;
        }
        String string2 = ActivityThread.currentPackageName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("disableBLE(): de-registering ");
        stringBuilder.append(string2);
        Log.d(TAG, stringBuilder.toString());
        try {
            this.mManagerService.updateBleAppCount(this.mToken, false, string2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
        }
        return true;
    }

    public boolean enable() {
        if (this.isEnabled()) {
            Log.d(TAG, "enable(): BT already enabled!");
            return true;
        }
        try {
            boolean bl = this.mManagerService.enable(ActivityThread.currentPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean enableBLE() {
        String string2;
        block4 : {
            if (!this.isBleScanAlwaysAvailable()) {
                return false;
            }
            try {
                string2 = ActivityThread.currentPackageName();
                this.mManagerService.updateBleAppCount(this.mToken, true, string2);
                if (!this.isLeEnabled()) break block4;
                Log.d(TAG, "enableBLE(): Bluetooth already enabled");
                return true;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        Log.d(TAG, "enableBLE(): Calling enable");
        boolean bl = this.mManagerService.enable(string2);
        return bl;
    }

    @SystemApi
    public boolean enableNoAutoConnect() {
        if (this.isEnabled()) {
            Log.d(TAG, "enableNoAutoConnect(): BT already enabled!");
            return true;
        }
        try {
            boolean bl = this.mManagerService.enableNoAutoConnect(ActivityThread.currentPackageName());
            return bl;
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
    public boolean factoryReset() {
        Throwable throwable2;
        block7 : {
            try {
                this.mServiceLock.readLock().lock();
                if (this.mService != null) {
                    boolean bl = this.mService.factoryReset();
                    this.mServiceLock.readLock().unlock();
                    return bl;
                }
                SystemProperties.set("persist.bluetooth.factoryreset", "true");
            }
            catch (Throwable throwable2) {
                break block7;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void finalize() throws Throwable {
        this.mManagerService.unregisterAdapter(this.mManagerCallback);
lbl3: // 2 sources:
        do {
            super.finalize();
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_1) {
            }
            catch (RemoteException var1_2) {}
            {
                Log.e("BluetoothAdapter", "", var1_2);
                ** continue;
            }
        }
        super.finalize();
        throw var1_1;
    }

    public String getAddress() {
        try {
            String string2 = this.mManagerService.getAddress();
            return string2;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return null;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public BluetoothClass getBluetoothClass() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return null;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                BluetoothClass bluetoothClass = this.mService.getBluetoothClass();
                this.mServiceLock.readLock().unlock();
                return bluetoothClass;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return null;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BluetoothLeAdvertiser getBluetoothLeAdvertiser() {
        if (!this.getLeAccess()) {
            return null;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (sBluetoothLeAdvertiser == null) {
                BluetoothLeAdvertiser bluetoothLeAdvertiser;
                sBluetoothLeAdvertiser = bluetoothLeAdvertiser = new BluetoothLeAdvertiser(this.mManagerService);
            }
            return sBluetoothLeAdvertiser;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BluetoothLeScanner getBluetoothLeScanner() {
        if (!this.getLeAccess()) {
            return null;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (sBluetoothLeScanner == null) {
                BluetoothLeScanner bluetoothLeScanner;
                sBluetoothLeScanner = bluetoothLeScanner = new BluetoothLeScanner(this.mManagerService);
            }
            return sBluetoothLeScanner;
        }
    }

    @UnsupportedAppUsage
    IBluetoothManager getBluetoothManager() {
        return this.mManagerService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    IBluetooth getBluetoothService(IBluetoothManagerCallback iBluetoothManagerCallback) {
        ArrayList<IBluetoothManagerCallback> arrayList = this.mProxyServiceStateCallbacks;
        synchronized (arrayList) {
            if (iBluetoothManagerCallback == null) {
                Log.w(TAG, "getBluetoothService() called with no BluetoothManagerCallback");
            } else if (!this.mProxyServiceStateCallbacks.contains(iBluetoothManagerCallback)) {
                this.mProxyServiceStateCallbacks.add(iBluetoothManagerCallback);
            }
            return this.mService;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Set<BluetoothDevice> getBondedDevices() {
        Throwable throwable2222;
        if (this.getState() != 12) {
            return this.toDeviceSet(new BluetoothDevice[0]);
        }
        this.mServiceLock.readLock().lock();
        if (this.mService != null) {
            Set<BluetoothDevice> set = this.toDeviceSet(this.mService.getBondedDevices());
            this.mServiceLock.readLock().unlock();
            return set;
        }
        Set<BluetoothDevice> set = this.toDeviceSet(new BluetoothDevice[0]);
        this.mServiceLock.readLock().unlock();
        return set;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.e(TAG, "", remoteException);
                this.mServiceLock.readLock().unlock();
                return null;
            }
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public int getConnectionState() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return 0;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getAdapterConnectionState();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "getConnectionState:", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 0;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    @Deprecated
    public BluetoothActivityEnergyInfo getControllerActivityEnergyInfo(int n) {
        Object object = new SynchronousResultReceiver();
        this.requestControllerActivityEnergyInfo((ResultReceiver)object);
        try {
            object = ((SynchronousResultReceiver)object).awaitResult(1000L);
            if (((SynchronousResultReceiver.Result)object).bundle != null) {
                object = (BluetoothActivityEnergyInfo)((SynchronousResultReceiver.Result)object).bundle.getParcelable("controller_activity");
                return object;
            }
        }
        catch (TimeoutException timeoutException) {
            Log.e(TAG, "getControllerActivityEnergyInfo timed out");
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public int getDiscoverableTimeout() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return -1;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getDiscoverableTimeout();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return -1;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long getDiscoveryEndMillis() {
        Throwable throwable2222;
        block5 : {
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                long l = this.mService.getDiscoveryEndMillis();
                this.mServiceLock.readLock().unlock();
                return l;
            }
            {
                catch (Throwable throwable2222) {
                    break block5;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return -1L;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getIoCapability() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return 255;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getIoCapability();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, remoteException.getMessage(), remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 255;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    boolean getLeAccess() {
        if (this.getLeState() == 12) {
            return true;
        }
        return this.getLeState() == 15;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getLeIoCapability() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return 255;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getLeIoCapability();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, remoteException.getMessage(), remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 255;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getLeMaximumAdvertisingDataLength() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return 0;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getLeMaximumAdvertisingDataLength();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get getLeMaximumAdvertisingDataLength, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 0;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public int getLeState() {
        int n2 = 10;
        try {
            int n;
            try {
                this.mServiceLock.readLock().lock();
                n = n2;
                if (this.mService != null) {
                    n = this.mService.getState();
                }
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                n = n2;
            }
            this.mServiceLock.readLock().unlock();
            return n;
        }
        catch (Throwable throwable2) {}
        this.mServiceLock.readLock().unlock();
        throw throwable2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getMaxConnectedAudioDevices() {
        Throwable throwable2222;
        block5 : {
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getMaxConnectedAudioDevices();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block5;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get getMaxConnectedAudioDevices, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 1;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public String getName() {
        try {
            String string2 = this.mManagerService.getName();
            return string2;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PeriodicAdvertisingManager getPeriodicAdvertisingManager() {
        if (!this.getLeAccess()) {
            return null;
        }
        if (!this.isLePeriodicAdvertisingSupported()) {
            return null;
        }
        Object object = this.mLock;
        synchronized (object) {
            if (sPeriodicAdvertisingManager == null) {
                PeriodicAdvertisingManager periodicAdvertisingManager;
                sPeriodicAdvertisingManager = periodicAdvertisingManager = new PeriodicAdvertisingManager(this.mManagerService);
            }
            return sPeriodicAdvertisingManager;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getProfileConnectionState(int n) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return 0;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                n = this.mService.getProfileConnectionState(n);
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "getProfileConnectionState:", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 0;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public boolean getProfileProxy(Context context, BluetoothProfile.ServiceListener serviceListener, int n) {
        if (context != null && serviceListener != null) {
            if (n == 1) {
                new BluetoothHeadset(context, serviceListener);
                return true;
            }
            if (n == 2) {
                new BluetoothA2dp(context, serviceListener);
                return true;
            }
            if (n == 11) {
                new BluetoothA2dpSink(context, serviceListener);
                return true;
            }
            if (n == 12) {
                new BluetoothAvrcpController(context, serviceListener);
                return true;
            }
            if (n == 4) {
                new BluetoothHidHost(context, serviceListener);
                return true;
            }
            if (n == 5) {
                new BluetoothPan(context, serviceListener);
                return true;
            }
            if (n == 3) {
                Log.e(TAG, "getProfileProxy(): BluetoothHealth is deprecated");
                return false;
            }
            if (n == 9) {
                new BluetoothMap(context, serviceListener);
                return true;
            }
            if (n == 16) {
                new BluetoothHeadsetClient(context, serviceListener);
                return true;
            }
            if (n == 10) {
                new BluetoothSap(context, serviceListener);
                return true;
            }
            if (n == 17) {
                new BluetoothPbapClient(context, serviceListener);
                return true;
            }
            if (n == 18) {
                new BluetoothMapClient(context, serviceListener);
                return true;
            }
            if (n == 19) {
                new BluetoothHidDevice(context, serviceListener);
                return true;
            }
            if (n == 21) {
                if (this.isHearingAidProfileSupported()) {
                    new BluetoothHearingAid(context, serviceListener);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public BluetoothDevice getRemoteDevice(String string2) {
        return new BluetoothDevice(string2);
    }

    public BluetoothDevice getRemoteDevice(byte[] arrby) {
        if (arrby != null && arrby.length == 6) {
            return new BluetoothDevice(String.format(Locale.US, "%02X:%02X:%02X:%02X:%02X:%02X", arrby[0], arrby[1], arrby[2], arrby[3], arrby[4], arrby[5]));
        }
        throw new IllegalArgumentException("Bluetooth address must have 6 bytes");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getScanMode() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return 20;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                int n = this.mService.getScanMode();
                this.mServiceLock.readLock().unlock();
                return n;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return 20;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int getState() {
        int n2 = 10;
        try {
            int n;
            try {
                this.mServiceLock.readLock().lock();
                n = n2;
                if (this.mService != null) {
                    n = this.mService.getState();
                }
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                n = n2;
            }
            this.mServiceLock.readLock().unlock();
            if (n == 15) return 10;
            if (n == 14) return 10;
            n2 = n;
            if (n != 16) return n2;
            return 10;
        }
        catch (Throwable throwable2) {}
        this.mServiceLock.readLock().unlock();
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public List<Integer> getSupportedProfiles() {
        ArrayList<Integer> arrayList;
        block9 : {
            arrayList = new ArrayList<Integer>();
            try {
                IBluetoothManagerCallback iBluetoothManagerCallback = this.mManagerCallback;
                // MONITORENTER : iBluetoothManagerCallback
                if (this.mService == null) break block9;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "getSupportedProfiles:", remoteException);
            }
            long l = this.mService.getSupportedProfiles();
            for (int i = 0; i <= 21; ++i) {
                if (((long)(1 << i) & l) == 0L) continue;
                arrayList.add(i);
            }
            return arrayList;
        }
        if (this.isHearingAidProfileSupported()) {
            arrayList.add(21);
        }
        // MONITOREXIT : iBluetoothManagerCallback
        return arrayList;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public ParcelUuid[] getUuids() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return null;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                ParcelUuid[] arrparcelUuid = this.mService.getUuids();
                this.mServiceLock.readLock().unlock();
                return arrparcelUuid;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return null;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    @SystemApi
    public boolean isBleScanAlwaysAvailable() {
        try {
            boolean bl = this.mManagerService.isBleScanAlwaysAvailable();
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "remote expection when calling isBleScanAlwaysAvailable", remoteException);
            return false;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isDiscovering() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isDiscovering();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isEnabled() {
        Throwable throwable2222;
        block5 : {
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isEnabled();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block5;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public boolean isHardwareTrackingFiltersAvailable() {
        IBluetoothGatt iBluetoothGatt;
        boolean bl;
        block5 : {
            boolean bl2 = this.getLeAccess();
            bl = false;
            if (!bl2) {
                return false;
            }
            try {
                iBluetoothGatt = this.mManagerService.getBluetoothGatt();
                if (iBluetoothGatt != null) break block5;
                return false;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        int n = iBluetoothGatt.numHwTrackFiltersAvailable();
        if (n != 0) {
            bl = true;
        }
        return bl;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isLe2MPhySupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isLe2MPhySupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isExtendedAdvertisingSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isLeCodedPhySupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isLeCodedPhySupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isLeCodedPhySupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    @SystemApi
    public boolean isLeEnabled() {
        int n = this.getLeState();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("isLeEnabled(): ");
        stringBuilder.append(BluetoothAdapter.nameForState(n));
        Log.d(TAG, stringBuilder.toString());
        boolean bl = n == 12 || n == 15;
        return bl;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isLeExtendedAdvertisingSupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isLeExtendedAdvertisingSupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isLeExtendedAdvertisingSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isLePeriodicAdvertisingSupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isLePeriodicAdvertisingSupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isLePeriodicAdvertisingSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isMultipleAdvertisementSupported() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isMultiAdvertisementSupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isMultipleAdvertisementSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isOffloadedFilteringSupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isOffloadedFilteringSupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isOffloadedFilteringSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isOffloadedScanBatchingSupported() {
        Throwable throwable2222;
        block6 : {
            if (!this.getLeAccess()) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.isOffloadedScanBatchingSupported();
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "failed to get isOffloadedScanBatchingSupported, error: ", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public BluetoothServerSocket listenUsingEncryptedRfcommOn(int n) throws IOException {
        Object object = new BluetoothServerSocket(1, false, true, n);
        int n2 = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == -2) {
            ((BluetoothServerSocket)object).setChannel(((BluetoothServerSocket)object).mSocket.getPort());
        }
        if (n2 >= 0) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public BluetoothServerSocket listenUsingEncryptedRfcommWithServiceRecord(String string2, UUID uUID) throws IOException {
        return this.createNewRfcommSocketAndRecord(string2, uUID, false, true);
    }

    public BluetoothServerSocket listenUsingInsecureL2capChannel() throws IOException {
        BluetoothServerSocket bluetoothServerSocket = new BluetoothServerSocket(4, false, false, -2, false, false);
        int n = bluetoothServerSocket.mSocket.bindListen();
        if (n == 0) {
            n = bluetoothServerSocket.mSocket.getPort();
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("listenUsingInsecureL2capChannel: set assigned PSM to ");
                stringBuilder.append(n);
                Log.d(TAG, stringBuilder.toString());
                bluetoothServerSocket.setChannel(n);
                return bluetoothServerSocket;
            }
            throw new IOException("Error: Unable to assign PSM value");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error: ");
        stringBuilder.append(n);
        throw new IOException(stringBuilder.toString());
    }

    public BluetoothServerSocket listenUsingInsecureL2capCoc(int n) throws IOException {
        Log.e(TAG, "listenUsingInsecureL2capCoc: PLEASE USE THE OFFICIAL API, listenUsingInsecureL2capChannel");
        return this.listenUsingInsecureL2capChannel();
    }

    public BluetoothServerSocket listenUsingInsecureL2capOn(int n) throws IOException {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("listenUsingInsecureL2capOn: port=");
        ((StringBuilder)object).append(n);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = new BluetoothServerSocket(3, false, false, n, false, false);
        int n2 = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == -2) {
            n = ((BluetoothServerSocket)object).mSocket.getPort();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("listenUsingInsecureL2capOn: set assigned channel to ");
            stringBuilder.append(n);
            Log.d(TAG, stringBuilder.toString());
            ((BluetoothServerSocket)object).setChannel(n);
        }
        if (n2 == 0) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    public BluetoothServerSocket listenUsingInsecureRfcommOn(int n) throws IOException {
        Object object = new BluetoothServerSocket(1, false, false, n);
        int n2 = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == -2) {
            ((BluetoothServerSocket)object).setChannel(((BluetoothServerSocket)object).mSocket.getPort());
        }
        if (n2 == 0) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    public BluetoothServerSocket listenUsingInsecureRfcommWithServiceRecord(String string2, UUID uUID) throws IOException {
        return this.createNewRfcommSocketAndRecord(string2, uUID, false, false);
    }

    public BluetoothServerSocket listenUsingL2capChannel() throws IOException {
        Object object = new BluetoothServerSocket(4, true, true, -2, false, false);
        int n = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == 0) {
            n = ((BluetoothServerSocket)object).mSocket.getPort();
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("listenUsingL2capChannel: set assigned PSM to ");
                stringBuilder.append(n);
                Log.d(TAG, stringBuilder.toString());
                ((BluetoothServerSocket)object).setChannel(n);
                return object;
            }
            throw new IOException("Error: Unable to assign PSM value");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n);
        throw new IOException(((StringBuilder)object).toString());
    }

    public BluetoothServerSocket listenUsingL2capCoc(int n) throws IOException {
        Log.e(TAG, "listenUsingL2capCoc: PLEASE USE THE OFFICIAL API, listenUsingL2capChannel");
        return this.listenUsingL2capChannel();
    }

    public BluetoothServerSocket listenUsingL2capOn(int n) throws IOException {
        return this.listenUsingL2capOn(n, false, false);
    }

    public BluetoothServerSocket listenUsingL2capOn(int n, boolean bl, boolean bl2) throws IOException {
        Object object = new BluetoothServerSocket(3, true, true, n, bl, bl2);
        int n2 = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == -2) {
            n = ((BluetoothServerSocket)object).mSocket.getPort();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("listenUsingL2capOn: set assigned channel to ");
            stringBuilder.append(n);
            Log.d(TAG, stringBuilder.toString());
            ((BluetoothServerSocket)object).setChannel(n);
        }
        if (n2 == 0) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    public BluetoothServerSocket listenUsingRfcommOn(int n) throws IOException {
        return this.listenUsingRfcommOn(n, false, false);
    }

    @UnsupportedAppUsage
    public BluetoothServerSocket listenUsingRfcommOn(int n, boolean bl, boolean bl2) throws IOException {
        Object object = new BluetoothServerSocket(1, true, true, n, bl, bl2);
        int n2 = ((BluetoothServerSocket)object).mSocket.bindListen();
        if (n == -2) {
            ((BluetoothServerSocket)object).setChannel(((BluetoothServerSocket)object).mSocket.getPort());
        }
        if (n2 == 0) {
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Error: ");
        ((StringBuilder)object).append(n2);
        throw new IOException(((StringBuilder)object).toString());
    }

    public BluetoothServerSocket listenUsingRfcommWithServiceRecord(String string2, UUID uUID) throws IOException {
        return this.createNewRfcommSocketAndRecord(string2, uUID, true, true);
    }

    public Pair<byte[], byte[]> readOutOfBandData() {
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public boolean removeOnMetadataChangedListener(BluetoothDevice object, OnMetadataChangedListener object2) {
        Log.d(TAG, "removeOnMetadataChangedListener()");
        if (object == null) {
            throw new NullPointerException("device is null");
        }
        if (object2 == null) {
            throw new NullPointerException("listener is null");
        }
        Map<BluetoothDevice, List<Pair<OnMetadataChangedListener, Executor>>> map = sMetadataListeners;
        synchronized (map) {
            if (!sMetadataListeners.containsKey(object)) {
                object = new IllegalArgumentException("device was not registered");
                throw object;
            }
            List<Pair<OnMetadataChangedListener, Executor>> list = sMetadataListeners.get(object);
            _$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc _$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc = new _$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc((OnMetadataChangedListener)object2);
            list.removeIf(_$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc);
            if (!sMetadataListeners.get(object).isEmpty()) {
                return true;
            }
            sMetadataListeners.remove(object);
            object2 = this.mService;
            if (object2 == null) {
                return true;
            }
            try {
                return object2.unregisterMetadataListener((BluetoothDevice)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "unregisterMetadataListener fail", remoteException);
                return false;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removeServiceStateCallback(IBluetoothManagerCallback iBluetoothManagerCallback) {
        ArrayList<IBluetoothManagerCallback> arrayList = this.mProxyServiceStateCallbacks;
        synchronized (arrayList) {
            this.mProxyServiceStateCallbacks.remove(iBluetoothManagerCallback);
            return;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void requestControllerActivityEnergyInfo(ResultReceiver var1_1) {
        this.mServiceLock.readLock().lock();
        var2_2 = var1_1;
        if (this.mService != null) {
            this.mService.requestActivityInfo(var1_1);
            var2_2 = null;
        }
        this.mServiceLock.readLock().unlock();
        if (var2_2 == null) return;
        var1_1 = var2_2;
        var1_1.send(0, null);
        return;
        {
            catch (RemoteException var3_5) {}
            {
                var2_4 = new StringBuilder();
                var2_4.append("getControllerActivityEnergyInfoCallback: ");
                var2_4.append(var3_5);
                Log.e("BluetoothAdapter", var2_4.toString());
                this.mServiceLock.readLock().unlock();
                if (var1_1 == null) return;
            }
        }
        ** finally { 
lbl23: // 1 sources:
        this.mServiceLock.readLock().unlock();
        if (var1_1 == null) throw var2_3;
        var1_1.send(0, null);
        throw var2_3;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean setBluetoothClass(BluetoothClass bluetoothClass) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.setBluetoothClass(bluetoothClass);
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setDiscoverableTimeout(int n) {
        if (this.getState() != 12) {
            return;
        }
        try {
            try {
                this.mServiceLock.readLock().lock();
                if (this.mService != null) {
                    this.mService.setDiscoverableTimeout(n);
                }
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
            this.mServiceLock.readLock().unlock();
            return;
        }
        catch (Throwable throwable2) {}
        this.mServiceLock.readLock().unlock();
        throw throwable2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean setIoCapability(int n) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.setIoCapability(n);
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, remoteException.getMessage(), remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean setLeIoCapability(int n) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.setLeIoCapability(n);
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, remoteException.getMessage(), remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean setName(String string2) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.setName(string2);
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    @UnsupportedAppUsage
    public boolean setScanMode(int n) {
        if (this.getState() != 12) {
            return false;
        }
        return this.setScanMode(n, this.getDiscoverableTimeout());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public boolean setScanMode(int n, int n2) {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.setScanMode(n, n2);
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean startDiscovery() {
        Throwable throwable2222;
        block6 : {
            if (this.getState() != 12) {
                return false;
            }
            this.mServiceLock.readLock().lock();
            if (this.mService != null) {
                boolean bl = this.mService.startDiscovery(this.getOpPackageName());
                this.mServiceLock.readLock().unlock();
                return bl;
            }
            {
                catch (Throwable throwable2222) {
                    break block6;
                }
                catch (RemoteException remoteException) {}
                {
                    Log.e(TAG, "", remoteException);
                }
            }
            this.mServiceLock.readLock().unlock();
            return false;
        }
        this.mServiceLock.readLock().unlock();
        throw throwable2222;
    }

    @Deprecated
    public boolean startLeScan(LeScanCallback leScanCallback) {
        return this.startLeScan(null, leScanCallback);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean startLeScan(final UUID[] arruUID, final LeScanCallback leScanCallback) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("startLeScan(): ");
        ((StringBuilder)object).append(Arrays.toString(arruUID));
        Log.d(TAG, ((StringBuilder)object).toString());
        if (leScanCallback == null) {
            Log.e(TAG, "startLeScan: null callback");
            return false;
        }
        BluetoothLeScanner bluetoothLeScanner = this.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            Log.e(TAG, "startLeScan: cannot get BluetoothLeScanner");
            return false;
        }
        object = this.mLeScanClients;
        synchronized (object) {
            ScanCallback scanCallback;
            block9 : {
                if (this.mLeScanClients.containsKey(leScanCallback)) {
                    Log.e(TAG, "LE Scan has already started");
                    return false;
                }
                try {
                    scanCallback = this.mManagerService.getBluetoothGatt();
                    if (scanCallback != null) break block9;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "", remoteException);
                    return false;
                }
                return false;
            }
            scanCallback = new ScanCallback(){

                @Override
                public void onScanResult(int n, ScanResult scanResult) {
                    if (n != 1) {
                        Log.e(BluetoothAdapter.TAG, "LE Scan has already started");
                        return;
                    }
                    ScanRecord scanRecord = scanResult.getScanRecord();
                    if (scanRecord == null) {
                        return;
                    }
                    if (arruUID != null) {
                        ArrayList<ParcelUuid> arrayList = new ArrayList<ParcelUuid>();
                        Object object = arruUID;
                        int n2 = ((UUID[])object).length;
                        for (n = 0; n < n2; ++n) {
                            arrayList.add(new ParcelUuid(object[n]));
                        }
                        object = scanRecord.getServiceUuids();
                        if (object == null || !object.containsAll(arrayList)) {
                            Log.d(BluetoothAdapter.TAG, "uuids does not match");
                            return;
                        }
                    }
                    leScanCallback.onLeScan(scanResult.getDevice(), scanResult.getRssi(), scanRecord.getBytes());
                }
            };
            Object object2 = new ScanSettings.Builder();
            object2 = ((ScanSettings.Builder)object2).setCallbackType(1).setScanMode(2).build();
            ArrayList<ScanFilter> arrayList = new ArrayList<ScanFilter>();
            if (arruUID != null && arruUID.length > 0) {
                ScanFilter.Builder builder = new ScanFilter.Builder();
                ParcelUuid parcelUuid = new ParcelUuid(arruUID[0]);
                arrayList.add(builder.setServiceUuid(parcelUuid).build());
            }
            bluetoothLeScanner.startScan(arrayList, (ScanSettings)object2, scanCallback);
            this.mLeScanClients.put(leScanCallback, scanCallback);
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void stopLeScan(LeScanCallback object) {
        Log.d(TAG, "stopLeScan()");
        BluetoothLeScanner bluetoothLeScanner = this.getBluetoothLeScanner();
        if (bluetoothLeScanner == null) {
            return;
        }
        Map<LeScanCallback, ScanCallback> map = this.mLeScanClients;
        synchronized (map) {
            object = this.mLeScanClients.remove(object);
            if (object == null) {
                Log.d(TAG, "scan not started yet");
                return;
            }
            bluetoothLeScanner.stopScan((ScanCallback)object);
            return;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AdapterState {
    }

    public static interface BluetoothStateChangeCallback {
        public void onBluetoothStateChange(boolean var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IoCapability {
    }

    public static interface LeScanCallback {
        public void onLeScan(BluetoothDevice var1, int var2, byte[] var3);
    }

    @SystemApi
    public static interface OnMetadataChangedListener {
        public void onMetadataChanged(BluetoothDevice var1, int var2, byte[] var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScanMode {
    }

    public class StateChangeCallbackWrapper
    extends IBluetoothStateChangeCallback.Stub {
        private BluetoothStateChangeCallback mCallback;

        StateChangeCallbackWrapper(BluetoothStateChangeCallback bluetoothStateChangeCallback) {
            this.mCallback = bluetoothStateChangeCallback;
        }

        @Override
        public void onBluetoothStateChange(boolean bl) {
            this.mCallback.onBluetoothStateChange(bl);
        }
    }

}

