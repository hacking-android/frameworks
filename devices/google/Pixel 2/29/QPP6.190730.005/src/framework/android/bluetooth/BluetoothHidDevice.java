/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothHidDevice;
import android.bluetooth.IBluetoothHidDeviceCallback;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$3bTGVlfKj7Y0SZdifW_Ya2myDKs;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$Eyz_qG6mvTlh6a8Bp41ZoEJzQCQ;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$NFluHjT4zTfYBRXClu_2k6mPKFI;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$jiodzbAJAcleQCwlDcBjvDddELM;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$qtStwQVkGfOs2iJIiePWqJJpi0w;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$xW99_tc95OmGApoKnpQ9q1TXb9k;
import android.bluetooth._$$Lambda$BluetoothHidDevice$CallbackWrapper$ypkr5GGxsAkGSBiLjIRwg_PzqCM;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public final class BluetoothHidDevice
implements BluetoothProfile {
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.hiddevice.profile.action.CONNECTION_STATE_CHANGED";
    public static final byte ERROR_RSP_INVALID_PARAM = 4;
    public static final byte ERROR_RSP_INVALID_RPT_ID = 2;
    public static final byte ERROR_RSP_NOT_READY = 1;
    public static final byte ERROR_RSP_SUCCESS = 0;
    public static final byte ERROR_RSP_UNKNOWN = 14;
    public static final byte ERROR_RSP_UNSUPPORTED_REQ = 3;
    public static final byte PROTOCOL_BOOT_MODE = 0;
    public static final byte PROTOCOL_REPORT_MODE = 1;
    public static final byte REPORT_TYPE_FEATURE = 3;
    public static final byte REPORT_TYPE_INPUT = 1;
    public static final byte REPORT_TYPE_OUTPUT = 2;
    public static final byte SUBCLASS1_COMBO = -64;
    public static final byte SUBCLASS1_KEYBOARD = 64;
    public static final byte SUBCLASS1_MOUSE = -128;
    public static final byte SUBCLASS1_NONE = 0;
    public static final byte SUBCLASS2_CARD_READER = 6;
    public static final byte SUBCLASS2_DIGITIZER_TABLET = 5;
    public static final byte SUBCLASS2_GAMEPAD = 2;
    public static final byte SUBCLASS2_JOYSTICK = 1;
    public static final byte SUBCLASS2_REMOTE_CONTROL = 3;
    public static final byte SUBCLASS2_SENSING_DEVICE = 4;
    public static final byte SUBCLASS2_UNCATEGORIZED = 0;
    private static final String TAG = BluetoothHidDevice.class.getSimpleName();
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothHidDevice> mProfileConnector = new BluetoothProfileConnector(this, 19, "BluetoothHidDevice", IBluetoothHidDevice.class.getName()){

        public IBluetoothHidDevice getServiceInterface(IBinder iBinder) {
            return IBluetoothHidDevice.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothHidDevice(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothHidDevice getService() {
        return this.mProfileConnector.getService();
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    public boolean connect(BluetoothDevice bluetoothDevice) {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.connect(bluetoothDevice);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.disconnect(bluetoothDevice);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Object object = this.getService();
        if (object != null) {
            try {
                object = object.getConnectedDevices();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    @Override
    public int getConnectionState(BluetoothDevice bluetoothDevice) {
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                int n = iBluetoothHidDevice.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                object = iBluetoothHidDevice.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public String getUserAppName() {
        Object object = this.getService();
        if (object != null) {
            try {
                object = object.getUserAppName();
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
        }
        return "";
    }

    public boolean registerApp(BluetoothHidDeviceAppSdpSettings bluetoothHidDeviceAppSdpSettings, BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings, BluetoothHidDeviceAppQosSettings bluetoothHidDeviceAppQosSettings2, Executor executor, Callback callback) {
        boolean bl = false;
        boolean bl2 = false;
        if (bluetoothHidDeviceAppSdpSettings != null) {
            if (executor != null) {
                if (callback != null) {
                    IBluetoothHidDevice iBluetoothHidDevice = this.getService();
                    if (iBluetoothHidDevice != null) {
                        try {
                            CallbackWrapper callbackWrapper = new CallbackWrapper(executor, callback);
                            bl2 = bl = iBluetoothHidDevice.registerApp(bluetoothHidDeviceAppSdpSettings, bluetoothHidDeviceAppQosSettings, bluetoothHidDeviceAppQosSettings2, callbackWrapper);
                        }
                        catch (RemoteException remoteException) {
                            Log.e(TAG, remoteException.toString());
                        }
                    } else {
                        Log.w(TAG, "Proxy not attached to service");
                        bl2 = bl;
                    }
                    return bl2;
                }
                throw new IllegalArgumentException("callback parameter cannot be null");
            }
            throw new IllegalArgumentException("executor parameter cannot be null");
        }
        throw new IllegalArgumentException("sdp parameter cannot be null");
    }

    public boolean replyReport(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.replyReport(bluetoothDevice, by, by2, arrby);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    public boolean reportError(BluetoothDevice bluetoothDevice, byte by) {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.reportError(bluetoothDevice, by);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    public boolean sendReport(BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.sendReport(bluetoothDevice, n, arrby);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    public boolean unregisterApp() {
        boolean bl = false;
        boolean bl2 = false;
        IBluetoothHidDevice iBluetoothHidDevice = this.getService();
        if (iBluetoothHidDevice != null) {
            try {
                bl2 = bl = iBluetoothHidDevice.unregisterApp();
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            bl2 = bl;
        }
        return bl2;
    }

    public static abstract class Callback {
        private static final String TAG = "BluetoothHidDevCallback";

        public void onAppStatusChanged(BluetoothDevice bluetoothDevice, boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onAppStatusChanged: pluggedDevice=");
            stringBuilder.append(bluetoothDevice);
            stringBuilder.append(" registered=");
            stringBuilder.append(bl);
            Log.d(TAG, stringBuilder.toString());
        }

        public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onConnectionStateChanged: device=");
            stringBuilder.append(bluetoothDevice);
            stringBuilder.append(" state=");
            stringBuilder.append(n);
            Log.d(TAG, stringBuilder.toString());
        }

        public void onGetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onGetReport: device=");
            stringBuilder.append(bluetoothDevice);
            stringBuilder.append(" type=");
            stringBuilder.append(by);
            stringBuilder.append(" id=");
            stringBuilder.append(by2);
            stringBuilder.append(" bufferSize=");
            stringBuilder.append(n);
            Log.d(TAG, stringBuilder.toString());
        }

        public void onInterruptData(BluetoothDevice bluetoothDevice, byte by, byte[] object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onInterruptData: device=");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(" reportId=");
            ((StringBuilder)object).append(by);
            Log.d(TAG, ((StringBuilder)object).toString());
        }

        public void onSetProtocol(BluetoothDevice bluetoothDevice, byte by) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSetProtocol: device=");
            stringBuilder.append(bluetoothDevice);
            stringBuilder.append(" protocol=");
            stringBuilder.append(by);
            Log.d(TAG, stringBuilder.toString());
        }

        public void onSetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] object) {
            object = new StringBuilder();
            ((StringBuilder)object).append("onSetReport: device=");
            ((StringBuilder)object).append(bluetoothDevice);
            ((StringBuilder)object).append(" type=");
            ((StringBuilder)object).append(by);
            ((StringBuilder)object).append(" id=");
            ((StringBuilder)object).append(by2);
            Log.d(TAG, ((StringBuilder)object).toString());
        }

        public void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onVirtualCableUnplug: device=");
            stringBuilder.append(bluetoothDevice);
            Log.d(TAG, stringBuilder.toString());
        }
    }

    private static class CallbackWrapper
    extends IBluetoothHidDeviceCallback.Stub {
        private final Callback mCallback;
        private final Executor mExecutor;

        CallbackWrapper(Executor executor, Callback callback) {
            this.mExecutor = executor;
            this.mCallback = callback;
        }

        public /* synthetic */ void lambda$onAppStatusChanged$0$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, boolean bl) {
            this.mCallback.onAppStatusChanged(bluetoothDevice, bl);
        }

        public /* synthetic */ void lambda$onConnectionStateChanged$1$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, int n) {
            this.mCallback.onConnectionStateChanged(bluetoothDevice, n);
        }

        public /* synthetic */ void lambda$onGetReport$2$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) {
            this.mCallback.onGetReport(bluetoothDevice, by, by2, n);
        }

        public /* synthetic */ void lambda$onInterruptData$5$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, byte by, byte[] arrby) {
            this.mCallback.onInterruptData(bluetoothDevice, by, arrby);
        }

        public /* synthetic */ void lambda$onSetProtocol$4$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, byte by) {
            this.mCallback.onSetProtocol(bluetoothDevice, by);
        }

        public /* synthetic */ void lambda$onSetReport$3$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) {
            this.mCallback.onSetReport(bluetoothDevice, by, by2, arrby);
        }

        public /* synthetic */ void lambda$onVirtualCableUnplug$6$BluetoothHidDevice$CallbackWrapper(BluetoothDevice bluetoothDevice) {
            this.mCallback.onVirtualCableUnplug(bluetoothDevice);
        }

        @Override
        public void onAppStatusChanged(BluetoothDevice bluetoothDevice, boolean bl) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$NFluHjT4zTfYBRXClu_2k6mPKFI(this, bluetoothDevice, bl));
        }

        @Override
        public void onConnectionStateChanged(BluetoothDevice bluetoothDevice, int n) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$qtStwQVkGfOs2iJIiePWqJJpi0w(this, bluetoothDevice, n));
        }

        @Override
        public void onGetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, int n) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$Eyz_qG6mvTlh6a8Bp41ZoEJzQCQ(this, bluetoothDevice, by, by2, n));
        }

        @Override
        public void onInterruptData(BluetoothDevice bluetoothDevice, byte by, byte[] arrby) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$xW99_tc95OmGApoKnpQ9q1TXb9k(this, bluetoothDevice, by, arrby));
        }

        @Override
        public void onSetProtocol(BluetoothDevice bluetoothDevice, byte by) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$ypkr5GGxsAkGSBiLjIRwg_PzqCM(this, bluetoothDevice, by));
        }

        @Override
        public void onSetReport(BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$3bTGVlfKj7Y0SZdifW_Ya2myDKs(this, bluetoothDevice, by, by2, arrby));
        }

        @Override
        public void onVirtualCableUnplug(BluetoothDevice bluetoothDevice) {
            CallbackWrapper.clearCallingIdentity();
            this.mExecutor.execute(new _$$Lambda$BluetoothHidDevice$CallbackWrapper$jiodzbAJAcleQCwlDcBjvDddELM(this, bluetoothDevice));
        }
    }

}

