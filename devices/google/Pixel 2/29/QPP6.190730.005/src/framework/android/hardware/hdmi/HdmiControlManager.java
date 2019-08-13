/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.hardware.hdmi.HdmiAudioSystemClient;
import android.hardware.hdmi.HdmiClient;
import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiHotplugEvent;
import android.hardware.hdmi.HdmiPlaybackClient;
import android.hardware.hdmi.HdmiSwitchClient;
import android.hardware.hdmi.HdmiTvClient;
import android.hardware.hdmi.HdmiUtils;
import android.hardware.hdmi.IHdmiControlService;
import android.hardware.hdmi.IHdmiHotplugEventListener;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.ArrayMap;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.util.List;

@SystemApi
public final class HdmiControlManager {
    public static final String ACTION_OSD_MESSAGE = "android.hardware.hdmi.action.OSD_MESSAGE";
    public static final int AVR_VOLUME_MUTED = 101;
    public static final int CLEAR_TIMER_STATUS_CEC_DISABLE = 162;
    public static final int CLEAR_TIMER_STATUS_CHECK_RECORDER_CONNECTION = 160;
    public static final int CLEAR_TIMER_STATUS_FAIL_TO_CLEAR_SELECTED_SOURCE = 161;
    public static final int CLEAR_TIMER_STATUS_TIMER_CLEARED = 128;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_NO_INFO_AVAILABLE = 2;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_NO_MATCHING = 1;
    public static final int CLEAR_TIMER_STATUS_TIMER_NOT_CLEARED_RECORDING = 0;
    public static final int CONTROL_STATE_CHANGED_REASON_SETTING = 1;
    public static final int CONTROL_STATE_CHANGED_REASON_STANDBY = 3;
    public static final int CONTROL_STATE_CHANGED_REASON_START = 0;
    public static final int CONTROL_STATE_CHANGED_REASON_WAKEUP = 2;
    public static final int DEVICE_EVENT_ADD_DEVICE = 1;
    public static final int DEVICE_EVENT_REMOVE_DEVICE = 2;
    public static final int DEVICE_EVENT_UPDATE_DEVICE = 3;
    public static final String EXTRA_MESSAGE_EXTRA_PARAM1 = "android.hardware.hdmi.extra.MESSAGE_EXTRA_PARAM1";
    public static final String EXTRA_MESSAGE_ID = "android.hardware.hdmi.extra.MESSAGE_ID";
    private static final int INVALID_PHYSICAL_ADDRESS = 65535;
    public static final int ONE_TOUCH_RECORD_ALREADY_RECORDING = 18;
    public static final int ONE_TOUCH_RECORD_CEC_DISABLED = 51;
    public static final int ONE_TOUCH_RECORD_CHECK_RECORDER_CONNECTION = 49;
    public static final int ONE_TOUCH_RECORD_DISALLOW_TO_COPY = 13;
    public static final int ONE_TOUCH_RECORD_DISALLOW_TO_FUTHER_COPIES = 14;
    public static final int ONE_TOUCH_RECORD_FAIL_TO_RECORD_DISPLAYED_SCREEN = 50;
    public static final int ONE_TOUCH_RECORD_INVALID_EXTERNAL_PHYSICAL_ADDRESS = 10;
    public static final int ONE_TOUCH_RECORD_INVALID_EXTERNAL_PLUG_NUMBER = 9;
    public static final int ONE_TOUCH_RECORD_MEDIA_PROBLEM = 21;
    public static final int ONE_TOUCH_RECORD_MEDIA_PROTECTED = 19;
    public static final int ONE_TOUCH_RECORD_NOT_ENOUGH_SPACE = 22;
    public static final int ONE_TOUCH_RECORD_NO_MEDIA = 16;
    public static final int ONE_TOUCH_RECORD_NO_OR_INSUFFICIENT_CA_ENTITLEMENTS = 12;
    public static final int ONE_TOUCH_RECORD_NO_SOURCE_SIGNAL = 20;
    public static final int ONE_TOUCH_RECORD_OTHER_REASON = 31;
    public static final int ONE_TOUCH_RECORD_PARENT_LOCK_ON = 23;
    public static final int ONE_TOUCH_RECORD_PLAYING = 17;
    public static final int ONE_TOUCH_RECORD_PREVIOUS_RECORDING_IN_PROGRESS = 48;
    public static final int ONE_TOUCH_RECORD_RECORDING_ALREADY_TERMINATED = 27;
    public static final int ONE_TOUCH_RECORD_RECORDING_ANALOGUE_SERVICE = 3;
    public static final int ONE_TOUCH_RECORD_RECORDING_CURRENTLY_SELECTED_SOURCE = 1;
    public static final int ONE_TOUCH_RECORD_RECORDING_DIGITAL_SERVICE = 2;
    public static final int ONE_TOUCH_RECORD_RECORDING_EXTERNAL_INPUT = 4;
    public static final int ONE_TOUCH_RECORD_RECORDING_TERMINATED_NORMALLY = 26;
    public static final int ONE_TOUCH_RECORD_UNABLE_ANALOGUE_SERVICE = 6;
    public static final int ONE_TOUCH_RECORD_UNABLE_DIGITAL_SERVICE = 5;
    public static final int ONE_TOUCH_RECORD_UNABLE_SELECTED_SERVICE = 7;
    public static final int ONE_TOUCH_RECORD_UNSUPPORTED_CA = 11;
    public static final int OSD_MESSAGE_ARC_CONNECTED_INVALID_PORT = 1;
    public static final int OSD_MESSAGE_AVR_VOLUME_CHANGED = 2;
    public static final int POWER_STATUS_ON = 0;
    public static final int POWER_STATUS_STANDBY = 1;
    public static final int POWER_STATUS_TRANSIENT_TO_ON = 2;
    public static final int POWER_STATUS_TRANSIENT_TO_STANDBY = 3;
    public static final int POWER_STATUS_UNKNOWN = -1;
    @Deprecated
    public static final int RESULT_ALREADY_IN_PROGRESS = 4;
    public static final int RESULT_COMMUNICATION_FAILED = 7;
    public static final int RESULT_EXCEPTION = 5;
    public static final int RESULT_INCORRECT_MODE = 6;
    public static final int RESULT_SOURCE_NOT_AVAILABLE = 2;
    public static final int RESULT_SUCCESS = 0;
    public static final int RESULT_TARGET_NOT_AVAILABLE = 3;
    public static final int RESULT_TIMEOUT = 1;
    private static final String TAG = "HdmiControlManager";
    public static final int TIMER_RECORDING_RESULT_EXTRA_CEC_DISABLED = 3;
    public static final int TIMER_RECORDING_RESULT_EXTRA_CHECK_RECORDER_CONNECTION = 1;
    public static final int TIMER_RECORDING_RESULT_EXTRA_FAIL_TO_RECORD_SELECTED_SOURCE = 2;
    public static final int TIMER_RECORDING_RESULT_EXTRA_NO_ERROR = 0;
    public static final int TIMER_RECORDING_TYPE_ANALOGUE = 2;
    public static final int TIMER_RECORDING_TYPE_DIGITAL = 1;
    public static final int TIMER_RECORDING_TYPE_EXTERNAL = 3;
    public static final int TIMER_STATUS_MEDIA_INFO_NOT_PRESENT = 2;
    public static final int TIMER_STATUS_MEDIA_INFO_PRESENT_NOT_PROTECTED = 0;
    public static final int TIMER_STATUS_MEDIA_INFO_PRESENT_PROTECTED = 1;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_CA_NOT_SUPPORTED = 6;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_CLOCK_FAILURE = 10;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_DATE_OUT_OF_RANGE = 2;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_DUPLICATED = 14;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_EXTERNAL_PHYSICAL_NUMBER = 5;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_EXTERNAL_PLUG_NUMBER = 4;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_INVALID_SEQUENCE = 3;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_NO_CA_ENTITLEMENTS = 7;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_NO_FREE_TIME = 1;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_PARENTAL_LOCK_ON = 9;
    public static final int TIMER_STATUS_NOT_PROGRAMMED_UNSUPPORTED_RESOLUTION = 8;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_ENOUGH_SPACE = 8;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_MIGHT_NOT_ENOUGH_SPACE = 11;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_NOT_ENOUGH_SPACE = 9;
    public static final int TIMER_STATUS_PROGRAMMED_INFO_NO_MEDIA_INFO = 10;
    private final boolean mHasAudioSystemDevice;
    private final boolean mHasPlaybackDevice;
    private final boolean mHasSwitchDevice;
    private final boolean mHasTvDevice;
    private final ArrayMap<HotplugEventListener, IHdmiHotplugEventListener> mHotplugEventListeners = new ArrayMap();
    private final boolean mIsSwitchDevice;
    private int mPhysicalAddress = 65535;
    private final IHdmiControlService mService;

    public HdmiControlManager(IHdmiControlService arrn) {
        this.mService = arrn;
        arrn = null;
        IHdmiControlService iHdmiControlService = this.mService;
        if (iHdmiControlService != null) {
            try {
                arrn = iHdmiControlService.getSupportedTypes();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        this.mHasTvDevice = HdmiControlManager.hasDeviceType(arrn, 0);
        this.mHasPlaybackDevice = HdmiControlManager.hasDeviceType(arrn, 4);
        this.mHasAudioSystemDevice = HdmiControlManager.hasDeviceType(arrn, 5);
        this.mHasSwitchDevice = HdmiControlManager.hasDeviceType(arrn, 6);
        this.mIsSwitchDevice = SystemProperties.getBoolean("ro.hdmi.property_is_device_hdmi_cec_switch", false);
    }

    private IHdmiHotplugEventListener getHotplugEventListenerWrapper(final HotplugEventListener hotplugEventListener) {
        return new IHdmiHotplugEventListener.Stub(){

            @Override
            public void onReceived(HdmiHotplugEvent hdmiHotplugEvent) {
                hotplugEventListener.onReceived(hdmiHotplugEvent);
            }
        };
    }

    private static boolean hasDeviceType(int[] arrn, int n) {
        if (arrn == null) {
            return false;
        }
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    public void addHotplugEventListener(HotplugEventListener hotplugEventListener) {
        if (this.mService == null) {
            Log.e(TAG, "HdmiControlService is not available");
            return;
        }
        if (this.mHotplugEventListeners.containsKey(hotplugEventListener)) {
            Log.e(TAG, "listener is already registered");
            return;
        }
        IHdmiHotplugEventListener iHdmiHotplugEventListener = this.getHotplugEventListenerWrapper(hotplugEventListener);
        this.mHotplugEventListeners.put(hotplugEventListener, iHdmiHotplugEventListener);
        try {
            this.mService.addHotplugEventListener(iHdmiHotplugEventListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public HdmiAudioSystemClient getAudioSystemClient() {
        return (HdmiAudioSystemClient)this.getClient(5);
    }

    @SuppressLint(value={"Doclava125"})
    public HdmiClient getClient(int n) {
        IHdmiControlService iHdmiControlService = this.mService;
        HdmiTvClient hdmiTvClient = null;
        HdmiTvClient hdmiTvClient2 = null;
        HdmiTvClient hdmiTvClient3 = null;
        HdmiClient hdmiClient = null;
        if (iHdmiControlService == null) {
            return null;
        }
        if (n != 0) {
            if (n != 4) {
                if (n != 5) {
                    if (n != 6) {
                        return null;
                    }
                    if (this.mHasSwitchDevice || this.mIsSwitchDevice) {
                        hdmiClient = new HdmiSwitchClient(this.mService);
                    }
                    return hdmiClient;
                }
                hdmiClient = hdmiTvClient;
                if (this.mHasAudioSystemDevice) {
                    hdmiClient = new HdmiAudioSystemClient(iHdmiControlService);
                }
                return hdmiClient;
            }
            hdmiClient = hdmiTvClient2;
            if (this.mHasPlaybackDevice) {
                hdmiClient = new HdmiPlaybackClient(iHdmiControlService);
            }
            return hdmiClient;
        }
        hdmiClient = hdmiTvClient3;
        if (this.mHasTvDevice) {
            hdmiClient = new HdmiTvClient(iHdmiControlService);
        }
        return hdmiClient;
    }

    @SystemApi
    public List<HdmiDeviceInfo> getConnectedDevices() {
        try {
            List<HdmiDeviceInfo> list = this.mService.getDeviceList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public List<HdmiDeviceInfo> getConnectedDevicesList() {
        try {
            List<HdmiDeviceInfo> list = this.mService.getDeviceList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getPhysicalAddress() {
        int n = this.mPhysicalAddress;
        if (n != 65535) {
            return n;
        }
        try {
            n = this.mPhysicalAddress = this.mService.getPhysicalAddress();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public HdmiPlaybackClient getPlaybackClient() {
        return (HdmiPlaybackClient)this.getClient(4);
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public HdmiSwitchClient getSwitchClient() {
        return (HdmiSwitchClient)this.getClient(6);
    }

    public boolean getSystemAudioMode() {
        try {
            boolean bl = this.mService.getSystemAudioMode();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SuppressLint(value={"Doclava125"})
    public HdmiTvClient getTvClient() {
        return (HdmiTvClient)this.getClient(0);
    }

    @SystemApi
    public boolean isDeviceConnected(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        int n = this.mPhysicalAddress = this.getPhysicalAddress();
        boolean bl = false;
        if (n == 65535) {
            return false;
        }
        n = hdmiDeviceInfo.getPhysicalAddress();
        if (n == 65535) {
            return false;
        }
        if (HdmiUtils.getLocalPortFromPhysicalAddress(n, this.mPhysicalAddress) != -1) {
            bl = true;
        }
        return bl;
    }

    @SystemApi
    @Deprecated
    public boolean isRemoteDeviceConnected(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        int n = this.mPhysicalAddress = this.getPhysicalAddress();
        boolean bl = false;
        if (n == 65535) {
            return false;
        }
        n = hdmiDeviceInfo.getPhysicalAddress();
        if (n == 65535) {
            return false;
        }
        if (HdmiUtils.getLocalPortFromPhysicalAddress(n, this.mPhysicalAddress) != -1) {
            bl = true;
        }
        return bl;
    }

    @SystemApi
    public void powerOffDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.powerOffRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void powerOffRemoteDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.powerOffRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void powerOnDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.powerOnRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void powerOnRemoteDevice(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.powerOnRemoteDevice(hdmiDeviceInfo.getLogicalAddress(), hdmiDeviceInfo.getDevicePowerStatus());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeHotplugEventListener(HotplugEventListener object) {
        if (this.mService == null) {
            Log.e(TAG, "HdmiControlService is not available");
            return;
        }
        if ((object = this.mHotplugEventListeners.remove(object)) == null) {
            Log.e(TAG, "tried to remove not-registered listener");
            return;
        }
        try {
            this.mService.removeHotplugEventListener((IHdmiHotplugEventListener)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    @Deprecated
    public void requestRemoteDeviceToBecomeActiveSource(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.askRemoteDeviceToBecomeActiveSource(hdmiDeviceInfo.getPhysicalAddress());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setActiveSource(HdmiDeviceInfo hdmiDeviceInfo) {
        Preconditions.checkNotNull(hdmiDeviceInfo);
        try {
            this.mService.askRemoteDeviceToBecomeActiveSource(hdmiDeviceInfo.getPhysicalAddress());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setStandbyMode(boolean bl) {
        try {
            this.mService.setStandbyMode(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static @interface ControlCallbackResult {
    }

    public static interface HotplugEventListener {
        public void onReceived(HdmiHotplugEvent var1);
    }

    public static interface VendorCommandListener {
        public void onControlStateChanged(boolean var1, int var2);

        public void onReceived(int var1, int var2, byte[] var3, boolean var4);
    }

}

