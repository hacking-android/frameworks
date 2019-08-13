/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.IBluetooth;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.IBluetoothManagerCallback;
import android.bluetooth.OobData;
import android.content.Context;
import android.os.Handler;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public final class BluetoothDevice
implements Parcelable {
    @SystemApi
    public static final int ACCESS_ALLOWED = 1;
    @SystemApi
    public static final int ACCESS_REJECTED = 2;
    @SystemApi
    public static final int ACCESS_UNKNOWN = 0;
    public static final String ACTION_ACL_CONNECTED = "android.bluetooth.device.action.ACL_CONNECTED";
    public static final String ACTION_ACL_DISCONNECTED = "android.bluetooth.device.action.ACL_DISCONNECTED";
    public static final String ACTION_ACL_DISCONNECT_REQUESTED = "android.bluetooth.device.action.ACL_DISCONNECT_REQUESTED";
    @UnsupportedAppUsage
    public static final String ACTION_ALIAS_CHANGED = "android.bluetooth.device.action.ALIAS_CHANGED";
    public static final String ACTION_BATTERY_LEVEL_CHANGED = "android.bluetooth.device.action.BATTERY_LEVEL_CHANGED";
    public static final String ACTION_BOND_STATE_CHANGED = "android.bluetooth.device.action.BOND_STATE_CHANGED";
    public static final String ACTION_CLASS_CHANGED = "android.bluetooth.device.action.CLASS_CHANGED";
    public static final String ACTION_CONNECTION_ACCESS_CANCEL = "android.bluetooth.device.action.CONNECTION_ACCESS_CANCEL";
    public static final String ACTION_CONNECTION_ACCESS_REPLY = "android.bluetooth.device.action.CONNECTION_ACCESS_REPLY";
    public static final String ACTION_CONNECTION_ACCESS_REQUEST = "android.bluetooth.device.action.CONNECTION_ACCESS_REQUEST";
    public static final String ACTION_FOUND = "android.bluetooth.device.action.FOUND";
    public static final String ACTION_MAS_INSTANCE = "android.bluetooth.device.action.MAS_INSTANCE";
    public static final String ACTION_NAME_CHANGED = "android.bluetooth.device.action.NAME_CHANGED";
    public static final String ACTION_NAME_FAILED = "android.bluetooth.device.action.NAME_FAILED";
    @UnsupportedAppUsage
    public static final String ACTION_PAIRING_CANCEL = "android.bluetooth.device.action.PAIRING_CANCEL";
    public static final String ACTION_PAIRING_REQUEST = "android.bluetooth.device.action.PAIRING_REQUEST";
    @UnsupportedAppUsage
    public static final String ACTION_SDP_RECORD = "android.bluetooth.device.action.SDP_RECORD";
    @SystemApi
    public static final String ACTION_SILENCE_MODE_CHANGED = "android.bluetooth.device.action.SILENCE_MODE_CHANGED";
    public static final String ACTION_UUID = "android.bluetooth.device.action.UUID";
    public static final int BATTERY_LEVEL_UNKNOWN = -1;
    public static final int BOND_BONDED = 12;
    public static final int BOND_BONDING = 11;
    public static final int BOND_NONE = 10;
    public static final int BOND_SUCCESS = 0;
    public static final int CONNECTION_ACCESS_NO = 2;
    public static final int CONNECTION_ACCESS_YES = 1;
    private static final int CONNECTION_STATE_CONNECTED = 1;
    private static final int CONNECTION_STATE_DISCONNECTED = 0;
    private static final int CONNECTION_STATE_ENCRYPTED_BREDR = 2;
    private static final int CONNECTION_STATE_ENCRYPTED_LE = 4;
    public static final Parcelable.Creator<BluetoothDevice> CREATOR;
    private static final boolean DBG = false;
    public static final int DEVICE_TYPE_CLASSIC = 1;
    public static final int DEVICE_TYPE_DUAL = 3;
    public static final int DEVICE_TYPE_LE = 2;
    public static final int DEVICE_TYPE_UNKNOWN = 0;
    public static final int ERROR = Integer.MIN_VALUE;
    public static final String EXTRA_ACCESS_REQUEST_TYPE = "android.bluetooth.device.extra.ACCESS_REQUEST_TYPE";
    public static final String EXTRA_ALWAYS_ALLOWED = "android.bluetooth.device.extra.ALWAYS_ALLOWED";
    public static final String EXTRA_BATTERY_LEVEL = "android.bluetooth.device.extra.BATTERY_LEVEL";
    public static final String EXTRA_BOND_STATE = "android.bluetooth.device.extra.BOND_STATE";
    public static final String EXTRA_CLASS = "android.bluetooth.device.extra.CLASS";
    public static final String EXTRA_CLASS_NAME = "android.bluetooth.device.extra.CLASS_NAME";
    public static final String EXTRA_CONNECTION_ACCESS_RESULT = "android.bluetooth.device.extra.CONNECTION_ACCESS_RESULT";
    public static final String EXTRA_DEVICE = "android.bluetooth.device.extra.DEVICE";
    public static final String EXTRA_MAS_INSTANCE = "android.bluetooth.device.extra.MAS_INSTANCE";
    public static final String EXTRA_NAME = "android.bluetooth.device.extra.NAME";
    public static final String EXTRA_PACKAGE_NAME = "android.bluetooth.device.extra.PACKAGE_NAME";
    public static final String EXTRA_PAIRING_KEY = "android.bluetooth.device.extra.PAIRING_KEY";
    public static final String EXTRA_PAIRING_VARIANT = "android.bluetooth.device.extra.PAIRING_VARIANT";
    public static final String EXTRA_PREVIOUS_BOND_STATE = "android.bluetooth.device.extra.PREVIOUS_BOND_STATE";
    @UnsupportedAppUsage
    public static final String EXTRA_REASON = "android.bluetooth.device.extra.REASON";
    public static final String EXTRA_RSSI = "android.bluetooth.device.extra.RSSI";
    public static final String EXTRA_SDP_RECORD = "android.bluetooth.device.extra.SDP_RECORD";
    @UnsupportedAppUsage
    public static final String EXTRA_SDP_SEARCH_STATUS = "android.bluetooth.device.extra.SDP_SEARCH_STATUS";
    public static final String EXTRA_UUID = "android.bluetooth.device.extra.UUID";
    @SystemApi
    public static final int METADATA_COMPANION_APP = 4;
    @SystemApi
    public static final int METADATA_ENHANCED_SETTINGS_UI_URI = 16;
    @SystemApi
    public static final int METADATA_HARDWARE_VERSION = 3;
    @SystemApi
    public static final int METADATA_IS_UNTETHERED_HEADSET = 6;
    @SystemApi
    public static final int METADATA_MAIN_ICON = 5;
    @SystemApi
    public static final int METADATA_MANUFACTURER_NAME = 0;
    @SystemApi
    public static final int METADATA_MAX_LENGTH = 2048;
    @SystemApi
    public static final int METADATA_MODEL_NAME = 1;
    @SystemApi
    public static final int METADATA_SOFTWARE_VERSION = 2;
    @SystemApi
    public static final int METADATA_UNTETHERED_CASE_BATTERY = 12;
    @SystemApi
    public static final int METADATA_UNTETHERED_CASE_CHARGING = 15;
    @SystemApi
    public static final int METADATA_UNTETHERED_CASE_ICON = 9;
    @SystemApi
    public static final int METADATA_UNTETHERED_LEFT_BATTERY = 10;
    @SystemApi
    public static final int METADATA_UNTETHERED_LEFT_CHARGING = 13;
    @SystemApi
    public static final int METADATA_UNTETHERED_LEFT_ICON = 7;
    @SystemApi
    public static final int METADATA_UNTETHERED_RIGHT_BATTERY = 11;
    @SystemApi
    public static final int METADATA_UNTETHERED_RIGHT_CHARGING = 14;
    @SystemApi
    public static final int METADATA_UNTETHERED_RIGHT_ICON = 8;
    public static final int PAIRING_VARIANT_CONSENT = 3;
    public static final int PAIRING_VARIANT_DISPLAY_PASSKEY = 4;
    public static final int PAIRING_VARIANT_DISPLAY_PIN = 5;
    public static final int PAIRING_VARIANT_OOB_CONSENT = 6;
    public static final int PAIRING_VARIANT_PASSKEY = 1;
    public static final int PAIRING_VARIANT_PASSKEY_CONFIRMATION = 2;
    public static final int PAIRING_VARIANT_PIN = 0;
    public static final int PAIRING_VARIANT_PIN_16_DIGITS = 7;
    public static final int PHY_LE_1M = 1;
    public static final int PHY_LE_1M_MASK = 1;
    public static final int PHY_LE_2M = 2;
    public static final int PHY_LE_2M_MASK = 2;
    public static final int PHY_LE_CODED = 3;
    public static final int PHY_LE_CODED_MASK = 4;
    public static final int PHY_OPTION_NO_PREFERRED = 0;
    public static final int PHY_OPTION_S2 = 1;
    public static final int PHY_OPTION_S8 = 2;
    public static final int REQUEST_TYPE_MESSAGE_ACCESS = 3;
    public static final int REQUEST_TYPE_PHONEBOOK_ACCESS = 2;
    public static final int REQUEST_TYPE_PROFILE_CONNECTION = 1;
    public static final int REQUEST_TYPE_SIM_ACCESS = 4;
    private static final String TAG = "BluetoothDevice";
    public static final int TRANSPORT_AUTO = 0;
    public static final int TRANSPORT_BREDR = 1;
    public static final int TRANSPORT_LE = 2;
    public static final int UNBOND_REASON_AUTH_CANCELED = 3;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_AUTH_FAILED = 1;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_AUTH_REJECTED = 2;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_AUTH_TIMEOUT = 6;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_DISCOVERY_IN_PROGRESS = 5;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_REMOTE_AUTH_CANCELED = 8;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_REMOTE_DEVICE_DOWN = 4;
    public static final int UNBOND_REASON_REMOVED = 9;
    @UnsupportedAppUsage
    public static final int UNBOND_REASON_REPEATED_ATTEMPTS = 7;
    private static volatile IBluetooth sService;
    static IBluetoothManagerCallback sStateChangeCallback;
    private final String mAddress;

    static {
        sStateChangeCallback = new IBluetoothManagerCallback.Stub(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onBluetoothServiceDown() throws RemoteException {
                synchronized (BluetoothDevice.class) {
                    sService = null;
                    return;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onBluetoothServiceUp(IBluetooth iBluetooth) throws RemoteException {
                synchronized (BluetoothDevice.class) {
                    if (sService == null) {
                        sService = iBluetooth;
                    }
                    return;
                }
            }

            @Override
            public void onBrEdrDown() {
            }
        };
        CREATOR = new Parcelable.Creator<BluetoothDevice>(){

            @Override
            public BluetoothDevice createFromParcel(Parcel parcel) {
                return new BluetoothDevice(parcel.readString());
            }

            public BluetoothDevice[] newArray(int n) {
                return new BluetoothDevice[n];
            }
        };
    }

    @UnsupportedAppUsage
    BluetoothDevice(String string2) {
        BluetoothDevice.getService();
        if (BluetoothAdapter.checkBluetoothAddress(string2)) {
            this.mAddress = string2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" is not a valid Bluetooth address");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static byte[] convertPinToBytes(String arrby) {
        if (arrby == null) {
            return null;
        }
        try {
            arrby = arrby.getBytes("UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            Log.e(TAG, "UTF-8 not supported?!?");
            return null;
        }
        if (arrby.length > 0 && arrby.length <= 16) {
            return arrby;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    static IBluetooth getService() {
        synchronized (BluetoothDevice.class) {
            if (sService == null) {
                sService = BluetoothAdapter.getDefaultAdapter().getBluetoothService(sStateChangeCallback);
            }
            return sService;
        }
    }

    @SystemApi
    public boolean cancelBondProcess() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot cancel Remote Device bond");
            return false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cancelBondProcess() for device ");
            stringBuilder.append(this.getAddress());
            stringBuilder.append(" called by pid: ");
            stringBuilder.append(Process.myPid());
            stringBuilder.append(" tid: ");
            stringBuilder.append(Process.myTid());
            Log.i(TAG, stringBuilder.toString());
            boolean bl = iBluetooth.cancelBondProcess(this);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean cancelPairingUserInput() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot create pairing user input");
            return false;
        }
        try {
            boolean bl = iBluetooth.cancelBondProcess(this);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public BluetoothGatt connectGatt(Context context, boolean bl, BluetoothGattCallback bluetoothGattCallback) {
        return this.connectGatt(context, bl, bluetoothGattCallback, 0);
    }

    public BluetoothGatt connectGatt(Context context, boolean bl, BluetoothGattCallback bluetoothGattCallback, int n) {
        return this.connectGatt(context, bl, bluetoothGattCallback, n, 1);
    }

    public BluetoothGatt connectGatt(Context context, boolean bl, BluetoothGattCallback bluetoothGattCallback, int n, int n2) {
        return this.connectGatt(context, bl, bluetoothGattCallback, n, n2, null);
    }

    public BluetoothGatt connectGatt(Context context, boolean bl, BluetoothGattCallback bluetoothGattCallback, int n, int n2, Handler handler) {
        return this.connectGatt(context, bl, bluetoothGattCallback, n, false, n2, handler);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public BluetoothGatt connectGatt(Context object, boolean bl, BluetoothGattCallback bluetoothGattCallback, int n, boolean bl2, int n2, Handler handler) {
        void var1_4;
        block5 : {
            if (bluetoothGattCallback == null) throw new NullPointerException("callback is null");
            object = BluetoothAdapter.getDefaultAdapter().getBluetoothManager();
            object = object.getBluetoothGatt();
            if (object == null) {
                return null;
            }
            BluetoothGatt bluetoothGatt = new BluetoothGatt((IBluetoothGatt)object, this, n, bl2, n2);
            try {
                bluetoothGatt.connect(bl, bluetoothGattCallback, handler);
                return bluetoothGatt;
            }
            catch (RemoteException remoteException) {}
            break block5;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.e(TAG, "", (Throwable)var1_4);
        return null;
    }

    public boolean createBond() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot create bond to Remote Device");
            return false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("createBond() for device ");
            stringBuilder.append(this.getAddress());
            stringBuilder.append(" called by pid: ");
            stringBuilder.append(Process.myPid());
            stringBuilder.append(" tid: ");
            stringBuilder.append(Process.myTid());
            Log.i(TAG, stringBuilder.toString());
            boolean bl = iBluetooth.createBond(this, 0);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean createBond(int n) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot create bond to Remote Device");
            return false;
        }
        if (n >= 0 && n <= 2) {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("createBond() for device ");
                stringBuilder.append(this.getAddress());
                stringBuilder.append(" called by pid: ");
                stringBuilder.append(Process.myPid());
                stringBuilder.append(" tid: ");
                stringBuilder.append(Process.myTid());
                Log.i(TAG, stringBuilder.toString());
                boolean bl = iBluetooth.createBond(this, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is not a valid Bluetooth transport");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public boolean createBondOutOfBand(int n, OobData oobData) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.w(TAG, "BT not enabled, createBondOutOfBand failed");
            return false;
        }
        try {
            boolean bl = iBluetooth.createBondOutOfBand(this, n, oobData);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public BluetoothSocket createInsecureL2capChannel(int n) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(4, -1, false, false, this, n, null);
        }
        Log.e(TAG, "createInsecureL2capChannel: Bluetooth is not enabled");
        throw new IOException();
    }

    public BluetoothSocket createInsecureL2capCocSocket(int n, int n2) throws IOException {
        Log.e(TAG, "createL2capCocSocket: PLEASE USE THE OFFICIAL API, createInsecureL2capChannel");
        return this.createInsecureL2capChannel(n2);
    }

    public BluetoothSocket createInsecureL2capSocket(int n) throws IOException {
        return new BluetoothSocket(3, -1, false, false, this, n, null);
    }

    @UnsupportedAppUsage
    public BluetoothSocket createInsecureRfcommSocket(int n) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(1, -1, false, false, this, n, null);
        }
        Log.e(TAG, "Bluetooth is not enabled");
        throw new IOException();
    }

    public BluetoothSocket createInsecureRfcommSocketToServiceRecord(UUID uUID) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(1, -1, false, false, this, -1, new ParcelUuid(uUID));
        }
        Log.e(TAG, "Bluetooth is not enabled");
        throw new IOException();
    }

    public BluetoothSocket createL2capChannel(int n) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(4, -1, true, true, this, n, null);
        }
        Log.e(TAG, "createL2capChannel: Bluetooth is not enabled");
        throw new IOException();
    }

    public BluetoothSocket createL2capCocSocket(int n, int n2) throws IOException {
        Log.e(TAG, "createL2capCocSocket: PLEASE USE THE OFFICIAL API, createL2capChannel");
        return this.createL2capChannel(n2);
    }

    public BluetoothSocket createL2capSocket(int n) throws IOException {
        return new BluetoothSocket(3, -1, true, true, this, n, null);
    }

    @UnsupportedAppUsage
    public BluetoothSocket createRfcommSocket(int n) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(1, -1, true, true, this, n, null);
        }
        Log.e(TAG, "Bluetooth is not enabled");
        throw new IOException();
    }

    public BluetoothSocket createRfcommSocketToServiceRecord(UUID uUID) throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(1, -1, true, true, this, -1, new ParcelUuid(uUID));
        }
        Log.e(TAG, "Bluetooth is not enabled");
        throw new IOException();
    }

    @UnsupportedAppUsage
    public BluetoothSocket createScoSocket() throws IOException {
        if (this.isBluetoothEnabled()) {
            return new BluetoothSocket(2, -1, true, true, this, -1, null);
        }
        Log.e(TAG, "Bluetooth is not enabled");
        throw new IOException();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object instanceof BluetoothDevice) {
            return this.mAddress.equals(((BluetoothDevice)object).getAddress());
        }
        return false;
    }

    public boolean fetchUuidsWithSdp() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth != null && this.isBluetoothEnabled()) {
            try {
                boolean bl = iBluetooth.fetchRemoteUuids(this);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return false;
            }
        }
        Log.e(TAG, "BT not enabled. Cannot fetchUuidsWithSdp");
        return false;
    }

    public String getAddress() {
        return this.mAddress;
    }

    @UnsupportedAppUsage
    public String getAlias() {
        Object object = sService;
        if (object == null) {
            Log.e(TAG, "BT not enabled. Cannot get Remote Device Alias");
            return null;
        }
        try {
            object = object.getRemoteAlias(this);
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return null;
        }
    }

    @UnsupportedAppUsage
    public String getAliasName() {
        String string2;
        String string3 = string2 = this.getAlias();
        if (string2 == null) {
            string3 = this.getName();
        }
        return string3;
    }

    @UnsupportedAppUsage
    public int getBatteryLevel() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "Bluetooth disabled. Cannot get remote device battery level");
            return -1;
        }
        try {
            int n = iBluetooth.getBatteryLevel(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return -1;
        }
    }

    public BluetoothClass getBluetoothClass() {
        Object object;
        int n;
        block4 : {
            object = sService;
            if (object == null) {
                Log.e(TAG, "BT not enabled. Cannot get Bluetooth Class");
                return null;
            }
            try {
                n = object.getRemoteClass(this);
                if (n != -16777216) break block4;
                return null;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return null;
            }
        }
        object = new BluetoothClass(n);
        return object;
    }

    public int getBondState() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot get bond state");
            return 10;
        }
        try {
            int n = iBluetooth.getBondState(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return 10;
        }
    }

    @UnsupportedAppUsage
    public int getMessageAccessPermission() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return 0;
        }
        try {
            int n = iBluetooth.getMessageAccessPermission(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return 0;
        }
    }

    @SystemApi
    public byte[] getMetadata(int n) {
        byte[] arrby = sService;
        if (arrby == null) {
            Log.e(TAG, "Bluetooth is not enabled. Cannot get metadata");
            return null;
        }
        try {
            arrby = arrby.getMetadata(this, n);
            return arrby;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "getMetadata fail", remoteException);
            return null;
        }
    }

    public String getName() {
        block4 : {
            Object object = sService;
            if (object == null) {
                Log.e(TAG, "BT not enabled. Cannot get Remote Device name");
                return null;
            }
            try {
                object = object.getRemoteName(this);
                if (object == null) break block4;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return null;
            }
            object = ((String)object).replaceAll("[\\t\\n\\r]+", " ");
            return object;
        }
        return null;
    }

    @UnsupportedAppUsage
    public int getPhonebookAccessPermission() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return 0;
        }
        try {
            int n = iBluetooth.getPhonebookAccessPermission(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return 0;
        }
    }

    public int getSimAccessPermission() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return 0;
        }
        try {
            int n = iBluetooth.getSimAccessPermission(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return 0;
        }
    }

    public int getType() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot get Remote Device type");
            return 0;
        }
        try {
            int n = iBluetooth.getRemoteType(this);
            return n;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return 0;
        }
    }

    public ParcelUuid[] getUuids() {
        ParcelUuid[] arrparcelUuid = sService;
        if (arrparcelUuid != null && this.isBluetoothEnabled()) {
            try {
                arrparcelUuid = arrparcelUuid.getRemoteUuids(this);
                return arrparcelUuid;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
                return null;
            }
        }
        Log.e(TAG, "BT not enabled. Cannot get remote device Uuids");
        return null;
    }

    public int hashCode() {
        return this.mAddress.hashCode();
    }

    @UnsupportedAppUsage
    public boolean isBluetoothDock() {
        return false;
    }

    boolean isBluetoothEnabled() {
        boolean bl = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean bl2 = bl;
        if (bluetoothAdapter != null) {
            bl2 = bl;
            if (bluetoothAdapter.isEnabled()) {
                bl2 = true;
            }
        }
        return bl2;
    }

    @UnsupportedAppUsage
    public boolean isBondingInitiatedLocally() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.w(TAG, "BT not enabled, isBondingInitiatedLocally failed");
            return false;
        }
        try {
            boolean bl = iBluetooth.isBondingInitiatedLocally(this);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean isConnected() {
        IBluetooth iBluetooth = sService;
        boolean bl = false;
        if (iBluetooth == null) {
            return false;
        }
        try {
            int n = iBluetooth.getConnectionState(this);
            if (n != 0) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean isEncrypted() {
        IBluetooth iBluetooth = sService;
        boolean bl = false;
        if (iBluetooth == null) {
            return false;
        }
        try {
            int n = iBluetooth.getConnectionState(this);
            if (n > 1) {
                bl = true;
            }
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean isInSilenceMode() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth != null) {
            try {
                boolean bl = iBluetooth.getSilenceMode(this);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "isInSilenceMode fail", remoteException);
                return false;
            }
        }
        throw new IllegalStateException("Bluetooth is not turned ON");
    }

    @SystemApi
    public boolean removeBond() {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot remove Remote Device bond");
            return false;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("removeBond() for device ");
            stringBuilder.append(this.getAddress());
            stringBuilder.append(" called by pid: ");
            stringBuilder.append(Process.myPid());
            stringBuilder.append(" tid: ");
            stringBuilder.append(Process.myTid());
            Log.i(TAG, stringBuilder.toString());
            boolean bl = iBluetooth.removeBond(this);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public boolean sdpSearch(ParcelUuid parcelUuid) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot query remote device sdp records");
            return false;
        }
        try {
            boolean bl = iBluetooth.sdpSearch(this, parcelUuid);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean setAlias(String string2) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot set Remote Device name");
            return false;
        }
        try {
            boolean bl = iBluetooth.setRemoteAlias(this, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public boolean setDeviceOutOfBandData(byte[] arrby, byte[] arrby2) {
        return false;
    }

    @UnsupportedAppUsage
    public boolean setMessageAccessPermission(int n) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return false;
        }
        try {
            boolean bl = iBluetooth.setMessageAccessPermission(this, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @SystemApi
    public boolean setMetadata(int n, byte[] arrby) {
        Object object = sService;
        if (object == null) {
            Log.e(TAG, "Bluetooth is not enabled. Cannot set metadata");
            return false;
        }
        if (arrby.length <= 2048) {
            try {
                boolean bl = object.setMetadata(this, n, arrby);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setMetadata fail", remoteException);
                return false;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("value length is ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(", should not over ");
        ((StringBuilder)object).append(2048);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public boolean setPairingConfirmation(boolean bl) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot set pairing confirmation");
            return false;
        }
        try {
            bl = iBluetooth.setPairingConfirmation(this, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    @UnsupportedAppUsage
    public boolean setPasskey(int n) {
        return false;
    }

    @SystemApi
    public boolean setPhonebookAccessPermission(int n) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return false;
        }
        try {
            boolean bl = iBluetooth.setPhonebookAccessPermission(this, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public boolean setPin(byte[] arrby) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            Log.e(TAG, "BT not enabled. Cannot set Remote Device pin");
            return false;
        }
        try {
            boolean bl = iBluetooth.setPin(this, true, arrby.length, arrby);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public boolean setRemoteOutOfBandData() {
        return false;
    }

    @SystemApi
    public boolean setSilenceMode(boolean bl) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth != null) {
            try {
                bl = iBluetooth.setSilenceMode(this, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "setSilenceMode fail", remoteException);
                return false;
            }
        }
        throw new IllegalStateException("Bluetooth is not turned ON");
    }

    @UnsupportedAppUsage
    public boolean setSimAccessPermission(int n) {
        IBluetooth iBluetooth = sService;
        if (iBluetooth == null) {
            return false;
        }
        try {
            boolean bl = iBluetooth.setSimAccessPermission(this, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "", remoteException);
            return false;
        }
    }

    public String toString() {
        return this.mAddress;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mAddress);
    }

}

