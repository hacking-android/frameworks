/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.IBluetoothHeadset;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.IBluetoothProfileServiceConnection;
import android.bluetooth.IBluetoothStateChangeCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothHeadset
implements BluetoothProfile {
    @UnsupportedAppUsage
    public static final String ACTION_ACTIVE_DEVICE_CHANGED = "android.bluetooth.headset.profile.action.ACTIVE_DEVICE_CHANGED";
    public static final String ACTION_AUDIO_STATE_CHANGED = "android.bluetooth.headset.profile.action.AUDIO_STATE_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.headset.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_HF_INDICATORS_VALUE_CHANGED = "android.bluetooth.headset.action.HF_INDICATORS_VALUE_CHANGED";
    public static final String ACTION_VENDOR_SPECIFIC_HEADSET_EVENT = "android.bluetooth.headset.action.VENDOR_SPECIFIC_HEADSET_EVENT";
    public static final int AT_CMD_TYPE_ACTION = 4;
    public static final int AT_CMD_TYPE_BASIC = 3;
    public static final int AT_CMD_TYPE_READ = 0;
    public static final int AT_CMD_TYPE_SET = 2;
    public static final int AT_CMD_TYPE_TEST = 1;
    private static final boolean DBG = true;
    public static final String EXTRA_HF_INDICATORS_IND_ID = "android.bluetooth.headset.extra.HF_INDICATORS_IND_ID";
    public static final String EXTRA_HF_INDICATORS_IND_VALUE = "android.bluetooth.headset.extra.HF_INDICATORS_IND_VALUE";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_ARGS = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_ARGS";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_CMD = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_CMD";
    public static final String EXTRA_VENDOR_SPECIFIC_HEADSET_EVENT_CMD_TYPE = "android.bluetooth.headset.extra.VENDOR_SPECIFIC_HEADSET_EVENT_CMD_TYPE";
    private static final int MESSAGE_HEADSET_SERVICE_CONNECTED = 100;
    private static final int MESSAGE_HEADSET_SERVICE_DISCONNECTED = 101;
    public static final int STATE_AUDIO_CONNECTED = 12;
    public static final int STATE_AUDIO_CONNECTING = 11;
    public static final int STATE_AUDIO_DISCONNECTED = 10;
    private static final String TAG = "BluetoothHeadset";
    private static final boolean VDBG = false;
    public static final String VENDOR_RESULT_CODE_COMMAND_ANDROID = "+ANDROID";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_COMPANY_ID_CATEGORY = "android.bluetooth.headset.intent.category.companyid";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_IPHONEACCEV = "+IPHONEACCEV";
    public static final int VENDOR_SPECIFIC_HEADSET_EVENT_IPHONEACCEV_BATTERY_LEVEL = 1;
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XAPL = "+XAPL";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XEVENT = "+XEVENT";
    public static final String VENDOR_SPECIFIC_HEADSET_EVENT_XEVENT_BATTERY_LEVEL = "BATTERY";
    private BluetoothAdapter mAdapter;
    private final IBluetoothStateChangeCallback mBluetoothStateChangeCallback = new IBluetoothStateChangeCallback.Stub(){

        @Override
        public void onBluetoothStateChange(boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onBluetoothStateChange: up=");
            stringBuilder.append(bl);
            Log.d(BluetoothHeadset.TAG, stringBuilder.toString());
            if (!bl) {
                BluetoothHeadset.this.doUnbind();
            } else {
                BluetoothHeadset.this.doBind();
            }
        }
    };
    private final IBluetoothProfileServiceConnection mConnection = new IBluetoothProfileServiceConnection.Stub(){

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(BluetoothHeadset.TAG, "Proxy object connected");
            BluetoothHeadset.this.mService = IBluetoothHeadset.Stub.asInterface(Binder.allowBlocking(iBinder));
            BluetoothHeadset.this.mHandler.sendMessage(BluetoothHeadset.this.mHandler.obtainMessage(100));
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(BluetoothHeadset.TAG, "Proxy object disconnected");
            BluetoothHeadset.this.doUnbind();
            BluetoothHeadset.this.mHandler.sendMessage(BluetoothHeadset.this.mHandler.obtainMessage(101));
        }
    };
    private Context mContext;
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 100) {
                if (n == 101 && BluetoothHeadset.this.mServiceListener != null) {
                    BluetoothHeadset.this.mServiceListener.onServiceDisconnected(1);
                }
            } else if (BluetoothHeadset.this.mServiceListener != null) {
                BluetoothHeadset.this.mServiceListener.onServiceConnected(1, BluetoothHeadset.this);
            }
        }
    };
    private volatile IBluetoothHeadset mService;
    private BluetoothProfile.ServiceListener mServiceListener;

    BluetoothHeadset(Context object, BluetoothProfile.ServiceListener serviceListener) {
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
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean doBind() {
        IBluetoothProfileServiceConnection iBluetoothProfileServiceConnection = this.mConnection;
        synchronized (iBluetoothProfileServiceConnection) {
            IBluetoothHeadset iBluetoothHeadset = this.mService;
            if (iBluetoothHeadset != null) return false;
            try {
                return this.mAdapter.getBluetoothManager().bindBluetoothProfileService(1, this.mConnection);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Unable to bind HeadsetService", remoteException);
            }
            return false;
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
        IBluetoothProfileServiceConnection iBluetoothProfileServiceConnection = this.mConnection;
        // MONITORENTER : iBluetoothProfileServiceConnection
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset == null) {
            // MONITOREXIT : iBluetoothProfileServiceConnection
            return;
        }
        this.mAdapter.getBluetoothManager().unbindBluetoothProfileService(1, this.mConnection);
        this.mService = null;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RemoteException remoteException) {}
            {
                Log.e(TAG, "Unable to unbind HeadsetService", remoteException);
                this.mService = null;
                return;
            }
        }
        this.mService = null;
        throw throwable2222;
    }

    public static boolean isBluetoothVoiceDialingEnabled(Context context) {
        return context.getResources().getBoolean(17891380);
    }

    private boolean isDisabled() {
        boolean bl = this.mAdapter.getState() == 10;
        return bl;
    }

    @UnsupportedAppUsage
    private boolean isEnabled() {
        boolean bl = this.mAdapter.getState() == 12;
        return bl;
    }

    public static boolean isInbandRingingSupported(Context context) {
        return context.getResources().getBoolean(17891376);
    }

    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    public void clccResponse(int n, int n2, int n3, int n4, boolean bl, String string2, int n5) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                iBluetoothHeadset.clccResponse(n, n2, n3, n4, bl, string2, n5);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
    }

    @UnsupportedAppUsage
    void close() {
        IBluetoothManager iBluetoothManager = this.mAdapter.getBluetoothManager();
        if (iBluetoothManager != null) {
            try {
                iBluetoothManager.unregisterStateChangeCallback(this.mBluetoothStateChangeCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "", remoteException);
            }
        }
        this.mServiceListener = null;
        this.doUnbind();
    }

    @SystemApi
    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("connect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHeadset.log(((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.connect(bluetoothDevice);
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

    @UnsupportedAppUsage
    public boolean connectAudio() {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.connectAudio();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    @SystemApi
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("disconnect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHeadset.log(((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = object.disconnect(bluetoothDevice);
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

    @UnsupportedAppUsage
    public boolean disconnectAudio() {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.disconnectAudio();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    @UnsupportedAppUsage
    public BluetoothDevice getActiveDevice() {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                BluetoothDevice bluetoothDevice = iBluetoothHeadset.getActiveDevice();
                return bluetoothDevice;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    public boolean getAudioRouteAllowed() {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.getAudioRouteAllowed();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    @UnsupportedAppUsage
    public int getAudioState(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && !this.isDisabled()) {
            try {
                int n = iBluetoothHeadset.getAudioState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return 10;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Object object = this.mService;
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
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHeadset.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                object = iBluetoothHeadset.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    @UnsupportedAppUsage
    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHeadset.getPriority(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean isAudioConnected(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadset.isAudioConnected(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean isAudioOn() {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.isAudioOn();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean isInbandRingingEnabled() {
        BluetoothHeadset.log("isInbandRingingEnabled()");
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.isInbandRingingEnabled();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public void phoneStateChanged(int n, int n2, int n3, String string2, int n4, String string3) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                iBluetoothHeadset.phoneStateChanged(n, n2, n3, string2, n4, string3);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
    }

    public boolean sendVendorSpecificResultCode(BluetoothDevice bluetoothDevice, String string2, String string3) {
        BluetoothHeadset.log("sendVendorSpecificResultCode()");
        if (string2 != null) {
            IBluetoothHeadset iBluetoothHeadset = this.mService;
            if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
                try {
                    boolean bl = iBluetoothHeadset.sendVendorSpecificResultCode(bluetoothDevice, string2, string3);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, Log.getStackTraceString(new Throwable()));
                }
            }
            if (iBluetoothHeadset == null) {
                Log.w(TAG, "Proxy not attached to service");
            }
            return false;
        }
        throw new IllegalArgumentException("command is null");
    }

    @UnsupportedAppUsage
    public boolean setActiveDevice(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setActiveDevice: ");
        ((StringBuilder)object).append(bluetoothDevice);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && this.isEnabled() && (bluetoothDevice == null || BluetoothHeadset.isValidDevice(bluetoothDevice))) {
            try {
                boolean bl = object.setActiveDevice(bluetoothDevice);
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

    public void setAudioRouteAllowed(boolean bl) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                iBluetoothHeadset.setAudioRouteAllowed(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
    }

    public void setForceScoAudio(boolean bl) {
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                iBluetoothHeadset.setForceScoAudio(bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
    }

    @SystemApi
    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setPriority(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        BluetoothHeadset.log(((StringBuilder)object).toString());
        object = this.mService;
        if (object != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
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

    @UnsupportedAppUsage
    public boolean startScoUsingVirtualVoiceCall() {
        BluetoothHeadset.log("startScoUsingVirtualVoiceCall()");
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.startScoUsingVirtualVoiceCall();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    public boolean startVoiceRecognition(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset.log("startVoiceRecognition()");
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadset.startVoiceRecognition(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean stopScoUsingVirtualVoiceCall() {
        BluetoothHeadset.log("stopScoUsingVirtualVoiceCall()");
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadset.stopScoUsingVirtualVoiceCall();
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return false;
    }

    public boolean stopVoiceRecognition(BluetoothDevice bluetoothDevice) {
        BluetoothHeadset.log("stopVoiceRecognition()");
        IBluetoothHeadset iBluetoothHeadset = this.mService;
        if (iBluetoothHeadset != null && this.isEnabled() && BluetoothHeadset.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadset.stopVoiceRecognition(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadset == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

