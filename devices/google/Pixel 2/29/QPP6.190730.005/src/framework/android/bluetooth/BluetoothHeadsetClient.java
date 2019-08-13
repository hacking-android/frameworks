/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.annotation.UnsupportedAppUsage;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadsetClientCall;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothProfileConnector;
import android.bluetooth.IBluetoothHeadsetClient;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

public final class BluetoothHeadsetClient
implements BluetoothProfile {
    public static final String ACTION_AG_EVENT = "android.bluetooth.headsetclient.profile.action.AG_EVENT";
    public static final String ACTION_AUDIO_STATE_CHANGED = "android.bluetooth.headsetclient.profile.action.AUDIO_STATE_CHANGED";
    public static final String ACTION_CALL_CHANGED = "android.bluetooth.headsetclient.profile.action.AG_CALL_CHANGED";
    public static final String ACTION_CONNECTION_STATE_CHANGED = "android.bluetooth.headsetclient.profile.action.CONNECTION_STATE_CHANGED";
    public static final String ACTION_LAST_VTAG = "android.bluetooth.headsetclient.profile.action.LAST_VTAG";
    public static final String ACTION_RESULT = "android.bluetooth.headsetclient.profile.action.RESULT";
    public static final int ACTION_RESULT_ERROR = 1;
    public static final int ACTION_RESULT_ERROR_BLACKLISTED = 6;
    public static final int ACTION_RESULT_ERROR_BUSY = 3;
    public static final int ACTION_RESULT_ERROR_CME = 7;
    public static final int ACTION_RESULT_ERROR_DELAYED = 5;
    public static final int ACTION_RESULT_ERROR_NO_ANSWER = 4;
    public static final int ACTION_RESULT_ERROR_NO_CARRIER = 2;
    public static final int ACTION_RESULT_OK = 0;
    public static final int CALL_ACCEPT_HOLD = 1;
    public static final int CALL_ACCEPT_NONE = 0;
    public static final int CALL_ACCEPT_TERMINATE = 2;
    public static final int CME_CORPORATE_PERSONALIZATION_PIN_REQUIRED = 46;
    public static final int CME_CORPORATE_PERSONALIZATION_PUK_REQUIRED = 47;
    public static final int CME_DIAL_STRING_TOO_LONG = 26;
    public static final int CME_EAP_NOT_SUPPORTED = 49;
    public static final int CME_EMERGENCY_SERVICE_ONLY = 32;
    public static final int CME_HIDDEN_KEY_REQUIRED = 48;
    public static final int CME_INCORRECT_PARAMETERS = 50;
    public static final int CME_INCORRECT_PASSWORD = 16;
    public static final int CME_INVALID_CHARACTER_IN_DIAL_STRING = 27;
    public static final int CME_INVALID_CHARACTER_IN_TEXT_STRING = 25;
    public static final int CME_INVALID_INDEX = 21;
    public static final int CME_MEMORY_FAILURE = 23;
    public static final int CME_MEMORY_FULL = 20;
    public static final int CME_NETWORK_PERSONALIZATION_PIN_REQUIRED = 40;
    public static final int CME_NETWORK_PERSONALIZATION_PUK_REQUIRED = 41;
    public static final int CME_NETWORK_SUBSET_PERSONALIZATION_PIN_REQUIRED = 42;
    public static final int CME_NETWORK_SUBSET_PERSONALIZATION_PUK_REQUIRED = 43;
    public static final int CME_NETWORK_TIMEOUT = 31;
    public static final int CME_NOT_FOUND = 22;
    public static final int CME_NOT_SUPPORTED_FOR_VOIP = 34;
    public static final int CME_NO_CONNECTION_TO_PHONE = 1;
    public static final int CME_NO_NETWORK_SERVICE = 30;
    public static final int CME_NO_SIMULTANOUS_VOIP_CS_CALLS = 33;
    public static final int CME_OPERATION_NOT_ALLOWED = 3;
    public static final int CME_OPERATION_NOT_SUPPORTED = 4;
    public static final int CME_PHFSIM_PIN_REQUIRED = 6;
    public static final int CME_PHFSIM_PUK_REQUIRED = 7;
    public static final int CME_PHONE_FAILURE = 0;
    public static final int CME_PHSIM_PIN_REQUIRED = 5;
    public static final int CME_SERVICE_PROVIDER_PERSONALIZATION_PIN_REQUIRED = 44;
    public static final int CME_SERVICE_PROVIDER_PERSONALIZATION_PUK_REQUIRED = 45;
    public static final int CME_SIM_BUSY = 14;
    public static final int CME_SIM_FAILURE = 13;
    public static final int CME_SIM_NOT_INSERTED = 10;
    public static final int CME_SIM_PIN2_REQUIRED = 17;
    public static final int CME_SIM_PIN_REQUIRED = 11;
    public static final int CME_SIM_PUK2_REQUIRED = 18;
    public static final int CME_SIM_PUK_REQUIRED = 12;
    public static final int CME_SIM_WRONG = 15;
    public static final int CME_SIP_RESPONSE_CODE = 35;
    public static final int CME_TEXT_STRING_TOO_LONG = 24;
    private static final boolean DBG = true;
    public static final String EXTRA_AG_FEATURE_3WAY_CALLING = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_3WAY_CALLING";
    public static final String EXTRA_AG_FEATURE_ACCEPT_HELD_OR_WAITING_CALL = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_ACCEPT_HELD_OR_WAITING_CALL";
    public static final String EXTRA_AG_FEATURE_ATTACH_NUMBER_TO_VT = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_ATTACH_NUMBER_TO_VT";
    public static final String EXTRA_AG_FEATURE_ECC = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_ECC";
    public static final String EXTRA_AG_FEATURE_MERGE = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_MERGE";
    public static final String EXTRA_AG_FEATURE_MERGE_AND_DETACH = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_MERGE_AND_DETACH";
    public static final String EXTRA_AG_FEATURE_REJECT_CALL = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_REJECT_CALL";
    public static final String EXTRA_AG_FEATURE_RELEASE_AND_ACCEPT = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_RELEASE_AND_ACCEPT";
    public static final String EXTRA_AG_FEATURE_RELEASE_HELD_OR_WAITING_CALL = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_RELEASE_HELD_OR_WAITING_CALL";
    public static final String EXTRA_AG_FEATURE_RESPONSE_AND_HOLD = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_RESPONSE_AND_HOLD";
    public static final String EXTRA_AG_FEATURE_VOICE_RECOGNITION = "android.bluetooth.headsetclient.extra.EXTRA_AG_FEATURE_VOICE_RECOGNITION";
    public static final String EXTRA_AUDIO_WBS = "android.bluetooth.headsetclient.extra.AUDIO_WBS";
    public static final String EXTRA_BATTERY_LEVEL = "android.bluetooth.headsetclient.extra.BATTERY_LEVEL";
    public static final String EXTRA_CALL = "android.bluetooth.headsetclient.extra.CALL";
    public static final String EXTRA_CME_CODE = "android.bluetooth.headsetclient.extra.CME_CODE";
    public static final String EXTRA_IN_BAND_RING = "android.bluetooth.headsetclient.extra.IN_BAND_RING";
    public static final String EXTRA_NETWORK_ROAMING = "android.bluetooth.headsetclient.extra.NETWORK_ROAMING";
    public static final String EXTRA_NETWORK_SIGNAL_STRENGTH = "android.bluetooth.headsetclient.extra.NETWORK_SIGNAL_STRENGTH";
    public static final String EXTRA_NETWORK_STATUS = "android.bluetooth.headsetclient.extra.NETWORK_STATUS";
    public static final String EXTRA_NUMBER = "android.bluetooth.headsetclient.extra.NUMBER";
    public static final String EXTRA_OPERATOR_NAME = "android.bluetooth.headsetclient.extra.OPERATOR_NAME";
    public static final String EXTRA_RESULT_CODE = "android.bluetooth.headsetclient.extra.RESULT_CODE";
    public static final String EXTRA_SUBSCRIBER_INFO = "android.bluetooth.headsetclient.extra.SUBSCRIBER_INFO";
    public static final String EXTRA_VOICE_RECOGNITION = "android.bluetooth.headsetclient.extra.VOICE_RECOGNITION";
    public static final int STATE_AUDIO_CONNECTED = 2;
    public static final int STATE_AUDIO_CONNECTING = 1;
    public static final int STATE_AUDIO_DISCONNECTED = 0;
    private static final String TAG = "BluetoothHeadsetClient";
    private static final boolean VDBG = false;
    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private final BluetoothProfileConnector<IBluetoothHeadsetClient> mProfileConnector = new BluetoothProfileConnector(this, 16, "BluetoothHeadsetClient", IBluetoothHeadsetClient.class.getName()){

        public IBluetoothHeadsetClient getServiceInterface(IBinder iBinder) {
            return IBluetoothHeadsetClient.Stub.asInterface(Binder.allowBlocking(iBinder));
        }
    };

    BluetoothHeadsetClient(Context context, BluetoothProfile.ServiceListener serviceListener) {
        this.mProfileConnector.connect(context, serviceListener);
    }

    private IBluetoothHeadsetClient getService() {
        return this.mProfileConnector.getService();
    }

    private boolean isEnabled() {
        boolean bl = this.mAdapter.getState() == 12;
        return bl;
    }

    private static boolean isValidDevice(BluetoothDevice bluetoothDevice) {
        boolean bl = bluetoothDevice != null && BluetoothAdapter.checkBluetoothAddress(bluetoothDevice.getAddress());
        return bl;
    }

    private static void log(String string2) {
        Log.d(TAG, string2);
    }

    @UnsupportedAppUsage
    public boolean acceptCall(BluetoothDevice bluetoothDevice, int n) {
        BluetoothHeadsetClient.log("acceptCall()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.acceptCall(bluetoothDevice, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    void close() {
        this.mProfileConnector.disconnect();
    }

    @UnsupportedAppUsage
    public boolean connect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("connect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHeadsetClient.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
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

    public boolean connectAudio(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadsetClient.connectAudio(bluetoothDevice);
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

    public BluetoothHeadsetClientCall dial(BluetoothDevice parcelable, String string2) {
        BluetoothHeadsetClient.log("dial()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(parcelable)) {
            try {
                parcelable = iBluetoothHeadsetClient.dial((BluetoothDevice)parcelable, string2);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    @UnsupportedAppUsage
    public boolean disconnect(BluetoothDevice bluetoothDevice) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("disconnect(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(")");
        BluetoothHeadsetClient.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
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

    public boolean disconnectAudio(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadsetClient.disconnectAudio(bluetoothDevice);
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

    public boolean enterPrivateMode(BluetoothDevice bluetoothDevice, int n) {
        BluetoothHeadsetClient.log("enterPrivateMode()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.enterPrivateMode(bluetoothDevice, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean explicitCallTransfer(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("explicitCallTransfer()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.explicitCallTransfer(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean getAudioRouteAllowed(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                boolean bl = iBluetoothHeadsetClient.getAudioRouteAllowed(bluetoothDevice);
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
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                int n = iBluetoothHeadsetClient.getAudioState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return 0;
    }

    @Override
    public List<BluetoothDevice> getConnectedDevices() {
        Object object = this.getService();
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
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHeadsetClient.getConnectionState(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public Bundle getCurrentAgEvents(BluetoothDevice parcelable) {
        BluetoothHeadsetClient.log("getCurrentCalls()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(parcelable)) {
            try {
                parcelable = iBluetoothHeadsetClient.getCurrentAgEvents((BluetoothDevice)parcelable);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    public Bundle getCurrentAgFeatures(BluetoothDevice parcelable) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                parcelable = iBluetoothHeadsetClient.getCurrentAgFeatures((BluetoothDevice)parcelable);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
        return null;
    }

    public List<BluetoothHeadsetClientCall> getCurrentCalls(BluetoothDevice object) {
        BluetoothHeadsetClient.log("getCurrentCalls()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice((BluetoothDevice)object)) {
            try {
                object = iBluetoothHeadsetClient.getCurrentCalls((BluetoothDevice)object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return null;
    }

    @Override
    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                object = iBluetoothHeadsetClient.getDevicesMatchingConnectionStates((int[])object);
                return object;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return new ArrayList<BluetoothDevice>();
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return new ArrayList<BluetoothDevice>();
    }

    public boolean getLastVoiceTagNumber(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("getLastVoiceTagNumber()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.getLastVoiceTagNumber(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public int getPriority(BluetoothDevice bluetoothDevice) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                int n = iBluetoothHeadsetClient.getPriority(bluetoothDevice);
                return n;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
                return 0;
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return 0;
    }

    public boolean holdCall(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("holdCall()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.holdCall(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    @UnsupportedAppUsage
    public boolean rejectCall(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("rejectCall()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.rejectCall(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean sendDTMF(BluetoothDevice bluetoothDevice, byte by) {
        BluetoothHeadsetClient.log("sendDTMF()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.sendDTMF(bluetoothDevice, by);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public void setAudioRouteAllowed(BluetoothDevice bluetoothDevice, boolean bl) {
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled()) {
            try {
                iBluetoothHeadsetClient.setAudioRouteAllowed(bluetoothDevice, bl);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, remoteException.toString());
            }
        } else {
            Log.w(TAG, "Proxy not attached to service");
            Log.d(TAG, Log.getStackTraceString(new Throwable()));
        }
    }

    public boolean setPriority(BluetoothDevice bluetoothDevice, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("setPriority(");
        ((StringBuilder)object).append(bluetoothDevice);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        BluetoothHeadsetClient.log(((StringBuilder)object).toString());
        object = this.getService();
        if (object != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
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

    public boolean startVoiceRecognition(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("startVoiceRecognition()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.startVoiceRecognition(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean stopVoiceRecognition(BluetoothDevice bluetoothDevice) {
        BluetoothHeadsetClient.log("stopVoiceRecognition()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.stopVoiceRecognition(bluetoothDevice);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

    public boolean terminateCall(BluetoothDevice bluetoothDevice, BluetoothHeadsetClientCall bluetoothHeadsetClientCall) {
        BluetoothHeadsetClient.log("terminateCall()");
        IBluetoothHeadsetClient iBluetoothHeadsetClient = this.getService();
        if (iBluetoothHeadsetClient != null && this.isEnabled() && BluetoothHeadsetClient.isValidDevice(bluetoothDevice)) {
            try {
                boolean bl = iBluetoothHeadsetClient.terminateCall(bluetoothDevice, bluetoothHeadsetClientCall);
                return bl;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, Log.getStackTraceString(new Throwable()));
            }
        }
        if (iBluetoothHeadsetClient == null) {
            Log.w(TAG, "Proxy not attached to service");
        }
        return false;
    }

}

