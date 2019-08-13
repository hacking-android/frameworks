/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.ParceledListSlice;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.NetworkRequest;
import android.net.wifi.EasyConnectStatusCallback;
import android.net.wifi.IDppCallback;
import android.net.wifi.INetworkRequestMatchCallback;
import android.net.wifi.INetworkRequestUserSelectionCallback;
import android.net.wifi.IOnWifiUsabilityStatsListener;
import android.net.wifi.ISoftApCallback;
import android.net.wifi.ITrafficStateCallback;
import android.net.wifi.IWifiManager;
import android.net.wifi.RssiPacketCountInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiActivityEnergyInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiNetworkSuggestion;
import android.net.wifi.WifiUsabilityStatsEntry;
import android.net.wifi.WpsInfo;
import android.net.wifi._$$Lambda$WifiManager$1$HHq94tH9ygKDknRiBOn9DYskiOc;
import android.net.wifi._$$Lambda$WifiManager$1$jN3hHFyvfp2UAuLO8N_VxYJuzY8;
import android.net.wifi._$$Lambda$WifiManager$EasyConnectCallbackProxy$ObU39aoKguVIx_qQTyZyomhDAAg;
import android.net.wifi._$$Lambda$WifiManager$EasyConnectCallbackProxy$YV1XBtKl8L8u8zCEX4lzLkOT6LQ;
import android.net.wifi._$$Lambda$WifiManager$EasyConnectCallbackProxy$fmVMj2ImIgtBYa9roBT0GyOubTI;
import android.net.wifi._$$Lambda$WifiManager$EasyConnectCallbackProxy$wTsmN4734yyutavZxcKa2TZ_4Cc;
import android.net.wifi._$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$8wy7AFc9OgD124mPKDe8H6vuPTQ;
import android.net.wifi._$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$DYo_nMH0tB37PG_5OviApSTSGXg;
import android.net.wifi._$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$KPxBZNMm8VDinf6ZcYWL1RJk9Zc;
import android.net.wifi._$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$MJqaBvGtvUfHUJtjhgTRIQ7GCr4;
import android.net.wifi._$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$sy4224jn5G2QTmFKYUY0fGWCJ5Q;
import android.net.wifi._$$Lambda$WifiManager$ProvisioningCallbackProxy$0_NXiwyrbrT_579x_6QMO0y3rzc;
import android.net.wifi._$$Lambda$WifiManager$ProvisioningCallbackProxy$ARmFIxMD9Os9eGpiffTyA3WhD0Q;
import android.net.wifi._$$Lambda$WifiManager$ProvisioningCallbackProxy$rgPeSRj_1qriYZtaCu57EZHtc_Q;
import android.net.wifi._$$Lambda$WifiManager$SoftApCallbackProxy$f44R8L0UcqgnIaD5lXMmeuRHCWI;
import android.net.wifi._$$Lambda$WifiManager$SoftApCallbackProxy$vmSW5veUpC52oRINBy419US5snk;
import android.net.wifi._$$Lambda$WifiManager$TrafficStateCallbackProxy$zQoZBZ4jRXbcyDZer28skV_T0jI;
import android.net.wifi.hotspot2.IProvisioningCallback;
import android.net.wifi.hotspot2.OsuProvider;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.net.wifi.hotspot2.ProvisioningCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AsyncChannel;
import com.android.server.net.NetworkPinner;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

public class WifiManager {
    public static final String ACTION_PASSPOINT_DEAUTH_IMMINENT = "android.net.wifi.action.PASSPOINT_DEAUTH_IMMINENT";
    public static final String ACTION_PASSPOINT_ICON = "android.net.wifi.action.PASSPOINT_ICON";
    public static final String ACTION_PASSPOINT_LAUNCH_OSU_VIEW = "android.net.wifi.action.PASSPOINT_LAUNCH_OSU_VIEW";
    public static final String ACTION_PASSPOINT_OSU_PROVIDERS_LIST = "android.net.wifi.action.PASSPOINT_OSU_PROVIDERS_LIST";
    public static final String ACTION_PASSPOINT_SUBSCRIPTION_REMEDIATION = "android.net.wifi.action.PASSPOINT_SUBSCRIPTION_REMEDIATION";
    public static final String ACTION_PICK_WIFI_NETWORK = "android.net.wifi.PICK_WIFI_NETWORK";
    public static final String ACTION_REQUEST_DISABLE = "android.net.wifi.action.REQUEST_DISABLE";
    public static final String ACTION_REQUEST_ENABLE = "android.net.wifi.action.REQUEST_ENABLE";
    public static final String ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE = "android.net.wifi.action.REQUEST_SCAN_ALWAYS_AVAILABLE";
    public static final String ACTION_WIFI_NETWORK_SUGGESTION_POST_CONNECTION = "android.net.wifi.action.WIFI_NETWORK_SUGGESTION_POST_CONNECTION";
    private static final int BASE = 151552;
    @Deprecated
    public static final String BATCHED_SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.BATCHED_RESULTS";
    public static final int BUSY = 2;
    public static final int CANCEL_WPS = 151566;
    public static final int CANCEL_WPS_FAILED = 151567;
    public static final int CANCEL_WPS_SUCCEDED = 151568;
    @SystemApi
    public static final int CHANGE_REASON_ADDED = 0;
    @SystemApi
    public static final int CHANGE_REASON_CONFIG_CHANGE = 2;
    @SystemApi
    public static final int CHANGE_REASON_REMOVED = 1;
    @SystemApi
    public static final String CONFIGURED_NETWORKS_CHANGED_ACTION = "android.net.wifi.CONFIGURED_NETWORKS_CHANGE";
    public static final int CONNECT_NETWORK = 151553;
    public static final int CONNECT_NETWORK_FAILED = 151554;
    public static final int CONNECT_NETWORK_SUCCEEDED = 151555;
    public static final boolean DEFAULT_POOR_NETWORK_AVOIDANCE_ENABLED = false;
    @SystemApi
    public static final int DEVICE_MOBILITY_STATE_HIGH_MVMT = 1;
    @SystemApi
    public static final int DEVICE_MOBILITY_STATE_LOW_MVMT = 2;
    @SystemApi
    public static final int DEVICE_MOBILITY_STATE_STATIONARY = 3;
    @SystemApi
    public static final int DEVICE_MOBILITY_STATE_UNKNOWN = 0;
    public static final int DISABLE_NETWORK = 151569;
    public static final int DISABLE_NETWORK_FAILED = 151570;
    public static final int DISABLE_NETWORK_SUCCEEDED = 151571;
    @SystemApi
    public static final int EASY_CONNECT_NETWORK_ROLE_AP = 1;
    @SystemApi
    public static final int EASY_CONNECT_NETWORK_ROLE_STA = 0;
    public static final int ERROR = 0;
    @Deprecated
    public static final int ERROR_AUTHENTICATING = 1;
    @Deprecated
    public static final int ERROR_AUTH_FAILURE_EAP_FAILURE = 3;
    @Deprecated
    public static final int ERROR_AUTH_FAILURE_NONE = 0;
    @Deprecated
    public static final int ERROR_AUTH_FAILURE_TIMEOUT = 1;
    @Deprecated
    public static final int ERROR_AUTH_FAILURE_WRONG_PSWD = 2;
    public static final String EXTRA_ANQP_ELEMENT_DATA = "android.net.wifi.extra.ANQP_ELEMENT_DATA";
    @Deprecated
    public static final String EXTRA_BSSID = "bssid";
    public static final String EXTRA_BSSID_LONG = "android.net.wifi.extra.BSSID_LONG";
    @SystemApi
    public static final String EXTRA_CHANGE_REASON = "changeReason";
    public static final String EXTRA_DELAY = "android.net.wifi.extra.DELAY";
    public static final String EXTRA_ESS = "android.net.wifi.extra.ESS";
    public static final String EXTRA_FILENAME = "android.net.wifi.extra.FILENAME";
    public static final String EXTRA_ICON = "android.net.wifi.extra.ICON";
    public static final String EXTRA_LINK_PROPERTIES = "linkProperties";
    @SystemApi
    public static final String EXTRA_MULTIPLE_NETWORKS_CHANGED = "multipleChanges";
    public static final String EXTRA_NETWORK_CAPABILITIES = "networkCapabilities";
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_NETWORK_SUGGESTION = "android.net.wifi.extra.NETWORK_SUGGESTION";
    public static final String EXTRA_NEW_RSSI = "newRssi";
    @Deprecated
    public static final String EXTRA_NEW_STATE = "newState";
    public static final String EXTRA_OSU_NETWORK = "android.net.wifi.extra.OSU_NETWORK";
    @SystemApi
    public static final String EXTRA_PREVIOUS_WIFI_AP_STATE = "previous_wifi_state";
    public static final String EXTRA_PREVIOUS_WIFI_STATE = "previous_wifi_state";
    public static final String EXTRA_RESULTS_UPDATED = "resultsUpdated";
    public static final String EXTRA_SCAN_AVAILABLE = "scan_enabled";
    public static final String EXTRA_SUBSCRIPTION_REMEDIATION_METHOD = "android.net.wifi.extra.SUBSCRIPTION_REMEDIATION_METHOD";
    @Deprecated
    public static final String EXTRA_SUPPLICANT_CONNECTED = "connected";
    @Deprecated
    public static final String EXTRA_SUPPLICANT_ERROR = "supplicantError";
    @Deprecated
    public static final String EXTRA_SUPPLICANT_ERROR_REASON = "supplicantErrorReason";
    public static final String EXTRA_URL = "android.net.wifi.extra.URL";
    public static final String EXTRA_WIFI_AP_FAILURE_REASON = "wifi_ap_error_code";
    public static final String EXTRA_WIFI_AP_INTERFACE_NAME = "wifi_ap_interface_name";
    public static final String EXTRA_WIFI_AP_MODE = "wifi_ap_mode";
    @SystemApi
    public static final String EXTRA_WIFI_AP_STATE = "wifi_state";
    @SystemApi
    public static final String EXTRA_WIFI_CONFIGURATION = "wifiConfiguration";
    @SystemApi
    public static final String EXTRA_WIFI_CREDENTIAL_EVENT_TYPE = "et";
    @SystemApi
    public static final String EXTRA_WIFI_CREDENTIAL_SSID = "ssid";
    @Deprecated
    public static final String EXTRA_WIFI_INFO = "wifiInfo";
    public static final String EXTRA_WIFI_STATE = "wifi_state";
    public static final int FORGET_NETWORK = 151556;
    public static final int FORGET_NETWORK_FAILED = 151557;
    public static final int FORGET_NETWORK_SUCCEEDED = 151558;
    public static final int HOTSPOT_FAILED = 2;
    public static final int HOTSPOT_OBSERVER_REGISTERED = 3;
    public static final int HOTSPOT_STARTED = 0;
    public static final int HOTSPOT_STOPPED = 1;
    public static final int IFACE_IP_MODE_CONFIGURATION_ERROR = 0;
    public static final int IFACE_IP_MODE_LOCAL_ONLY = 2;
    public static final int IFACE_IP_MODE_TETHERED = 1;
    public static final int IFACE_IP_MODE_UNSPECIFIED = -1;
    public static final int INVALID_ARGS = 8;
    private static final int INVALID_KEY = 0;
    public static final int IN_PROGRESS = 1;
    @UnsupportedAppUsage
    public static final String LINK_CONFIGURATION_CHANGED_ACTION = "android.net.wifi.LINK_CONFIGURATION_CHANGED";
    private static final int MAX_ACTIVE_LOCKS = 50;
    @UnsupportedAppUsage
    private static final int MAX_RSSI = -55;
    @UnsupportedAppUsage
    private static final int MIN_RSSI = -100;
    public static final String NETWORK_IDS_CHANGED_ACTION = "android.net.wifi.NETWORK_IDS_CHANGED";
    public static final String NETWORK_STATE_CHANGED_ACTION = "android.net.wifi.STATE_CHANGE";
    public static final int NETWORK_SUGGESTIONS_MAX_PER_APP;
    public static final int NOT_AUTHORIZED = 9;
    @SystemApi
    public static final int PASSPOINT_HOME_NETWORK = 0;
    @SystemApi
    public static final int PASSPOINT_ROAMING_NETWORK = 1;
    public static final String RSSI_CHANGED_ACTION = "android.net.wifi.RSSI_CHANGED";
    @UnsupportedAppUsage
    public static final int RSSI_LEVELS = 5;
    public static final int RSSI_PKTCNT_FETCH = 151572;
    public static final int RSSI_PKTCNT_FETCH_FAILED = 151574;
    public static final int RSSI_PKTCNT_FETCH_SUCCEEDED = 151573;
    public static final int SAP_START_FAILURE_GENERAL = 0;
    public static final int SAP_START_FAILURE_NO_CHANNEL = 1;
    public static final int SAVE_NETWORK = 151559;
    public static final int SAVE_NETWORK_FAILED = 151560;
    public static final int SAVE_NETWORK_SUCCEEDED = 151561;
    public static final String SCAN_RESULTS_AVAILABLE_ACTION = "android.net.wifi.SCAN_RESULTS";
    public static final int START_WPS = 151562;
    public static final int START_WPS_SUCCEEDED = 151563;
    public static final int STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_DUPLICATE = 3;
    public static final int STATUS_NETWORK_SUGGESTIONS_ERROR_ADD_EXCEEDS_MAX_PER_APP = 4;
    public static final int STATUS_NETWORK_SUGGESTIONS_ERROR_APP_DISALLOWED = 2;
    public static final int STATUS_NETWORK_SUGGESTIONS_ERROR_INTERNAL = 1;
    public static final int STATUS_NETWORK_SUGGESTIONS_ERROR_REMOVE_INVALID = 5;
    public static final int STATUS_NETWORK_SUGGESTIONS_SUCCESS = 0;
    @Deprecated
    public static final String SUPPLICANT_CONNECTION_CHANGE_ACTION = "android.net.wifi.supplicant.CONNECTION_CHANGE";
    @Deprecated
    public static final String SUPPLICANT_STATE_CHANGED_ACTION = "android.net.wifi.supplicant.STATE_CHANGE";
    private static final String TAG = "WifiManager";
    @SystemApi
    public static final String WIFI_AP_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_AP_STATE_CHANGED";
    @SystemApi
    public static final int WIFI_AP_STATE_DISABLED = 11;
    @SystemApi
    public static final int WIFI_AP_STATE_DISABLING = 10;
    @SystemApi
    public static final int WIFI_AP_STATE_ENABLED = 13;
    @SystemApi
    public static final int WIFI_AP_STATE_ENABLING = 12;
    @SystemApi
    public static final int WIFI_AP_STATE_FAILED = 14;
    @SystemApi
    public static final String WIFI_CREDENTIAL_CHANGED_ACTION = "android.net.wifi.WIFI_CREDENTIAL_CHANGED";
    @SystemApi
    public static final int WIFI_CREDENTIAL_FORGOT = 1;
    @SystemApi
    public static final int WIFI_CREDENTIAL_SAVED = 0;
    public static final int WIFI_FEATURE_ADDITIONAL_STA = 2048;
    public static final int WIFI_FEATURE_AP_STA = 32768;
    public static final int WIFI_FEATURE_AWARE = 64;
    public static final int WIFI_FEATURE_BATCH_SCAN = 512;
    public static final int WIFI_FEATURE_CONFIG_NDO = 2097152;
    public static final int WIFI_FEATURE_CONTROL_ROAMING = 8388608;
    public static final int WIFI_FEATURE_D2AP_RTT = 256;
    public static final int WIFI_FEATURE_D2D_RTT = 128;
    public static final int WIFI_FEATURE_DPP = Integer.MIN_VALUE;
    public static final int WIFI_FEATURE_EPR = 16384;
    public static final int WIFI_FEATURE_HAL_EPNO = 262144;
    public static final int WIFI_FEATURE_IE_WHITELIST = 16777216;
    public static final int WIFI_FEATURE_INFRA = 1;
    public static final int WIFI_FEATURE_INFRA_5G = 2;
    public static final int WIFI_FEATURE_LINK_LAYER_STATS = 65536;
    public static final int WIFI_FEATURE_LOGGER = 131072;
    public static final int WIFI_FEATURE_LOW_LATENCY = 1073741824;
    public static final int WIFI_FEATURE_MKEEP_ALIVE = 1048576;
    public static final int WIFI_FEATURE_MOBILE_HOTSPOT = 16;
    public static final int WIFI_FEATURE_OWE = 536870912;
    public static final int WIFI_FEATURE_P2P = 8;
    public static final long WIFI_FEATURE_P2P_RAND_MAC = 0x100000000L;
    public static final int WIFI_FEATURE_PASSPOINT = 4;
    public static final int WIFI_FEATURE_PNO = 1024;
    public static final int WIFI_FEATURE_RSSI_MONITOR = 524288;
    public static final int WIFI_FEATURE_SCANNER = 32;
    public static final int WIFI_FEATURE_SCAN_RAND = 33554432;
    public static final int WIFI_FEATURE_TDLS = 4096;
    public static final int WIFI_FEATURE_TDLS_OFFCHANNEL = 8192;
    public static final int WIFI_FEATURE_TRANSMIT_POWER = 4194304;
    public static final int WIFI_FEATURE_TX_POWER_LIMIT = 67108864;
    public static final int WIFI_FEATURE_WPA3_SAE = 134217728;
    public static final int WIFI_FEATURE_WPA3_SUITE_B = 268435456;
    @UnsupportedAppUsage
    public static final int WIFI_FREQUENCY_BAND_2GHZ = 2;
    @UnsupportedAppUsage
    public static final int WIFI_FREQUENCY_BAND_5GHZ = 1;
    @UnsupportedAppUsage
    public static final int WIFI_FREQUENCY_BAND_AUTO = 0;
    @Deprecated
    public static final int WIFI_MODE_FULL = 1;
    public static final int WIFI_MODE_FULL_HIGH_PERF = 3;
    public static final int WIFI_MODE_FULL_LOW_LATENCY = 4;
    public static final int WIFI_MODE_NO_LOCKS_HELD = 0;
    @Deprecated
    public static final int WIFI_MODE_SCAN_ONLY = 2;
    public static final String WIFI_SCAN_AVAILABLE = "wifi_scan_available";
    public static final String WIFI_STATE_CHANGED_ACTION = "android.net.wifi.WIFI_STATE_CHANGED";
    public static final int WIFI_STATE_DISABLED = 1;
    public static final int WIFI_STATE_DISABLING = 0;
    public static final int WIFI_STATE_ENABLED = 3;
    public static final int WIFI_STATE_ENABLING = 2;
    public static final int WIFI_STATE_UNKNOWN = 4;
    public static final int WPS_AUTH_FAILURE = 6;
    public static final int WPS_COMPLETED = 151565;
    public static final int WPS_FAILED = 151564;
    public static final int WPS_OVERLAP_ERROR = 3;
    public static final int WPS_TIMED_OUT = 7;
    public static final int WPS_TKIP_ONLY_PROHIBITED = 5;
    public static final int WPS_WEP_PROHIBITED = 4;
    private static final Object sServiceHandlerDispatchLock;
    @UnsupportedAppUsage
    private int mActiveLockCount;
    private AsyncChannel mAsyncChannel;
    private CountDownLatch mConnected;
    private Context mContext;
    @GuardedBy(value={"mLock"})
    private LocalOnlyHotspotCallbackProxy mLOHSCallbackProxy;
    @GuardedBy(value={"mLock"})
    private LocalOnlyHotspotObserverProxy mLOHSObserverProxy;
    private int mListenerKey = 1;
    private final SparseArray mListenerMap = new SparseArray();
    private final Object mListenerMapLock = new Object();
    private final Object mLock = new Object();
    private Looper mLooper;
    @UnsupportedAppUsage
    IWifiManager mService;
    private final int mTargetSdkVersion;
    private boolean mVerboseLoggingEnabled = false;

    static {
        int n = ActivityManager.isLowRamDeviceStatic() ? 256 : 1024;
        NETWORK_SUGGESTIONS_MAX_PER_APP = n;
        sServiceHandlerDispatchLock = new Object();
    }

    public WifiManager(Context context, IWifiManager iWifiManager, Looper looper) {
        this.mContext = context;
        this.mService = iWifiManager;
        this.mLooper = looper;
        this.mTargetSdkVersion = context.getApplicationInfo().targetSdkVersion;
        this.updateVerboseLoggingEnabledFromService();
    }

    static /* synthetic */ int access$808(WifiManager wifiManager) {
        int n = wifiManager.mActiveLockCount;
        wifiManager.mActiveLockCount = n + 1;
        return n;
    }

    static /* synthetic */ int access$810(WifiManager wifiManager) {
        int n = wifiManager.mActiveLockCount;
        wifiManager.mActiveLockCount = n - 1;
        return n;
    }

    private int addOrUpdateNetwork(WifiConfiguration wifiConfiguration) {
        try {
            int n = this.mService.addOrUpdateNetwork(wifiConfiguration, this.mContext.getOpPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static int calculateSignalLevel(int n, int n2) {
        if (n <= -100) {
            return 0;
        }
        if (n >= -55) {
            return n2 - 1;
        }
        float f = n2 - 1;
        return (int)((float)(n + 100) * f / 45.0f);
    }

    public static int compareSignalLevel(int n, int n2) {
        return n - n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private AsyncChannel getChannel() {
        synchronized (this) {
            if (this.mAsyncChannel != null) return this.mAsyncChannel;
            Object object = this.getWifiServiceMessenger();
            if (object == null) {
                object = new IllegalStateException("getWifiServiceMessenger() returned null!  This is invalid.");
                throw object;
            }
            Object object2 = new AsyncChannel();
            this.mAsyncChannel = object2;
            this.mConnected = object2 = new CountDownLatch(1);
            object2 = new ServiceHandler(this.mLooper);
            this.mAsyncChannel.connect(this.mContext, (Handler)object2, (Messenger)object);
            try {
                this.mConnected.await();
                return this.mAsyncChannel;
            }
            catch (InterruptedException interruptedException) {
                Log.e(TAG, "interrupted wait at init");
            }
            return this.mAsyncChannel;
        }
    }

    private long getSupportedFeatures() {
        try {
            long l = this.mService.getSupportedFeatures();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    private Messenger getWifiServiceMessenger() {
        try {
            Messenger messenger = this.mService.getWifiServiceMessenger(this.mContext.getOpPackageName());
            return messenger;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private boolean isFeatureSupported(long l) {
        boolean bl = (this.getSupportedFeatures() & l) == l;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int putListener(Object object) {
        if (object == null) {
            return 0;
        }
        Object object2 = this.mListenerMapLock;
        synchronized (object2) {
            int n;
            do {
                n = this.mListenerKey;
                this.mListenerKey = n + 1;
            } while (n == 0);
            this.mListenerMap.put(n, object);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Object removeListener(int n) {
        if (n == 0) {
            return null;
        }
        Object object = this.mListenerMapLock;
        synchronized (object) {
            Object e = this.mListenerMap.get(n);
            this.mListenerMap.remove(n);
            return e;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void stopLocalOnlyHotspot() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mLOHSCallbackProxy == null) {
                return;
            }
            this.mLOHSCallbackProxy = null;
            try {
                this.mService.stopLocalOnlyHotspot();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    private void updateVerboseLoggingEnabledFromService() {
        boolean bl = this.getVerboseLoggingLevel() > 0;
        this.mVerboseLoggingEnabled = bl;
    }

    @Deprecated
    public int addNetwork(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration == null) {
            return -1;
        }
        wifiConfiguration.networkId = -1;
        return this.addOrUpdateNetwork(wifiConfiguration);
    }

    public int addNetworkSuggestions(List<WifiNetworkSuggestion> list) {
        try {
            int n = this.mService.addNetworkSuggestions(list, this.mContext.getOpPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void addOnWifiUsabilityStatsListener(final Executor executor, final OnWifiUsabilityStatsListener onWifiUsabilityStatsListener) {
        if (executor != null) {
            if (onWifiUsabilityStatsListener != null) {
                IOnWifiUsabilityStatsListener.Stub stub;
                if (this.mVerboseLoggingEnabled) {
                    stub = new StringBuilder();
                    ((StringBuilder)((Object)stub)).append("addOnWifiUsabilityStatsListener: listener=");
                    ((StringBuilder)((Object)stub)).append(onWifiUsabilityStatsListener);
                    Log.v(TAG, ((StringBuilder)((Object)stub)).toString());
                }
                try {
                    IWifiManager iWifiManager = this.mService;
                    Binder binder = new Binder();
                    stub = new IOnWifiUsabilityStatsListener.Stub(){

                        static /* synthetic */ void lambda$onWifiUsabilityStats$0(OnWifiUsabilityStatsListener onWifiUsabilityStatsListener2, int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) {
                            onWifiUsabilityStatsListener2.onWifiUsabilityStats(n, bl, wifiUsabilityStatsEntry);
                        }

                        static /* synthetic */ void lambda$onWifiUsabilityStats$1(Executor executor2, OnWifiUsabilityStatsListener onWifiUsabilityStatsListener2, int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) throws Exception {
                            executor2.execute(new _$$Lambda$WifiManager$1$jN3hHFyvfp2UAuLO8N_VxYJuzY8(onWifiUsabilityStatsListener2, n, bl, wifiUsabilityStatsEntry));
                        }

                        @Override
                        public void onWifiUsabilityStats(int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) {
                            if (WifiManager.this.mVerboseLoggingEnabled) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("OnWifiUsabilityStatsListener: onWifiUsabilityStats: seqNum=");
                                stringBuilder.append(n);
                                Log.v(WifiManager.TAG, stringBuilder.toString());
                            }
                            Binder.withCleanCallingIdentity(new _$$Lambda$WifiManager$1$HHq94tH9ygKDknRiBOn9DYskiOc(executor, onWifiUsabilityStatsListener, n, bl, wifiUsabilityStatsEntry));
                        }
                    };
                    iWifiManager.addOnWifiUsabilityStatsListener(binder, stub, onWifiUsabilityStatsListener.hashCode());
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("listener cannot be null");
        }
        throw new IllegalArgumentException("executor cannot be null");
    }

    public void addOrUpdatePasspointConfiguration(PasspointConfiguration object) {
        try {
            if (this.mService.addOrUpdatePasspointConfiguration((PasspointConfiguration)object, this.mContext.getOpPackageName())) {
                return;
            }
            object = new IllegalArgumentException();
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void cancelLocalOnlyHotspotRequest() {
        Object object = this.mLock;
        synchronized (object) {
            this.stopLocalOnlyHotspot();
            return;
        }
    }

    public void cancelWps(WpsCallback wpsCallback) {
        if (wpsCallback != null) {
            wpsCallback.onFailed(0);
        }
    }

    @SystemApi
    public void connect(int n, ActionListener actionListener) {
        if (n >= 0) {
            this.getChannel().sendMessage(151553, n, this.putListener(actionListener));
            return;
        }
        throw new IllegalArgumentException("Network id cannot be negative");
    }

    @SystemApi
    public void connect(WifiConfiguration wifiConfiguration, ActionListener actionListener) {
        if (wifiConfiguration != null) {
            this.getChannel().sendMessage(151553, -1, this.putListener(actionListener), wifiConfiguration);
            return;
        }
        throw new IllegalArgumentException("config cannot be null");
    }

    public MulticastLock createMulticastLock(String string2) {
        return new MulticastLock(string2);
    }

    public WifiLock createWifiLock(int n, String string2) {
        return new WifiLock(n, string2);
    }

    @Deprecated
    public WifiLock createWifiLock(String string2) {
        return new WifiLock(1, string2);
    }

    public void deauthenticateNetwork(long l, boolean bl) {
        try {
            this.mService.deauthenticateNetwork(l, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void disable(int n, ActionListener actionListener) {
        if (n >= 0) {
            this.getChannel().sendMessage(151569, n, this.putListener(actionListener));
            return;
        }
        throw new IllegalArgumentException("Network id cannot be negative");
    }

    public void disableEphemeralNetwork(String string2) {
        if (string2 != null) {
            try {
                this.mService.disableEphemeralNetwork(string2, this.mContext.getOpPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("SSID cannot be null");
    }

    @Deprecated
    public boolean disableNetwork(int n) {
        try {
            boolean bl = this.mService.disableNetwork(n, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean disconnect() {
        try {
            boolean bl = this.mService.disconnect(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean enableNetwork(int n, boolean bl) {
        block3 : {
            boolean bl2 = bl && this.mTargetSdkVersion < 21;
            if (bl2) {
                NetworkRequest networkRequest = new NetworkRequest.Builder().clearCapabilities().addCapability(15).addTransportType(1).build();
                NetworkPinner.pin(this.mContext, networkRequest);
            }
            try {
                bl = this.mService.enableNetwork(n, bl, this.mContext.getOpPackageName());
                if (!bl2 || bl) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            NetworkPinner.unpin();
        }
        return bl;
    }

    @UnsupportedAppUsage
    public void enableVerboseLogging(int n) {
        try {
            this.mService.enableVerboseLogging(n);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("enableVerboseLogging ");
            stringBuilder.append(exception.toString());
            Log.e(TAG, stringBuilder.toString());
        }
    }

    public void enableWifiConnectivityManager(boolean bl) {
        try {
            this.mService.enableWifiConnectivityManager(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset(this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mAsyncChannel != null) {
                this.mAsyncChannel.disconnect();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    @SystemApi
    public void forget(int n, ActionListener actionListener) {
        if (n >= 0) {
            this.getChannel().sendMessage(151556, n, this.putListener(actionListener));
            return;
        }
        throw new IllegalArgumentException("Network id cannot be negative");
    }

    @SystemApi
    public List<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>> getAllMatchingWifiConfigs(List<ScanResult> object) {
        Object object2;
        ArrayList<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>> arrayList;
        block5 : {
            arrayList = new ArrayList<Pair<WifiConfiguration, Map<Integer, List<ScanResult>>>>();
            object = this.mService.getAllMatchingFqdnsForScanResults((List<ScanResult>)object);
            if (!object.isEmpty()) break block5;
            return arrayList;
        }
        try {
            IWifiManager object32 = this.mService;
            object2 = new ArrayList(object.keySet());
            for (WifiConfiguration wifiConfiguration : object32.getWifiConfigsForPasspointProfiles((List<String>)object2)) {
                object2 = (Map)object.get(wifiConfiguration.FQDN);
                if (object2 == null) continue;
            }
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        {
            WifiConfiguration wifiConfiguration;
            arrayList.add(Pair.create(wifiConfiguration, object2));
            continue;
        }
        return arrayList;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Deprecated
    public List<WifiConfiguration> getConfiguredNetworks() {
        try {
            ParceledListSlice parceledListSlice = this.mService.getConfiguredNetworks(this.mContext.getOpPackageName());
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    public WifiInfo getConnectionInfo() {
        try {
            WifiInfo wifiInfo = this.mService.getConnectionInfo(this.mContext.getOpPackageName());
            return wifiInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public WifiActivityEnergyInfo getControllerActivityEnergyInfo() {
        if (this.mService == null) {
            return null;
        }
        WifiActivityEnergyInfo wifiActivityEnergyInfo = this.mService.reportActivityInfo();
        // MONITOREXIT : this
        return wifiActivityEnergyInfo;
    }

    @UnsupportedAppUsage
    public String getCountryCode() {
        try {
            String string2 = this.mService.getCountryCode();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public Network getCurrentNetwork() {
        try {
            Network network = this.mService.getCurrentNetwork();
            return network;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getCurrentNetworkWpsNfcConfigurationToken() {
        return null;
    }

    public DhcpInfo getDhcpInfo() {
        try {
            DhcpInfo dhcpInfo = this.mService.getDhcpInfo();
            return dhcpInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getEnableAutoJoinWhenAssociated() {
        return false;
    }

    public String[] getFactoryMacAddresses() {
        try {
            String[] arrstring = this.mService.getFactoryMacAddresses();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Map<OsuProvider, List<ScanResult>> getMatchingOsuProviders(List<ScanResult> object) {
        if (object == null) {
            return new HashMap<OsuProvider, List<ScanResult>>();
        }
        try {
            object = this.mService.getMatchingOsuProviders((List<ScanResult>)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Map<OsuProvider, PasspointConfiguration> getMatchingPasspointConfigsForOsuProviders(Set<OsuProvider> object) {
        try {
            IWifiManager iWifiManager = this.mService;
            ArrayList<OsuProvider> arrayList = new ArrayList<OsuProvider>((Collection<OsuProvider>)object);
            object = iWifiManager.getMatchingPasspointConfigsForOsuProviders(arrayList);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getMaxNumberOfNetworkSuggestionsPerApp() {
        return NETWORK_SUGGESTIONS_MAX_PER_APP;
    }

    @Deprecated
    public List<PasspointConfiguration> getPasspointConfigurations() {
        try {
            List<PasspointConfiguration> list = this.mService.getPasspointConfigurations(this.mContext.getOpPackageName());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public List<WifiConfiguration> getPrivilegedConfiguredNetworks() {
        try {
            ParceledListSlice parceledListSlice = this.mService.getPrivilegedConfiguredNetworks(this.mContext.getOpPackageName());
            if (parceledListSlice != null) return parceledListSlice.getList();
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        return Collections.emptyList();
    }

    public List<ScanResult> getScanResults() {
        try {
            List<ScanResult> list = this.mService.getScanResults(this.mContext.getOpPackageName());
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void getTxPacketCount(TxPacketCountListener txPacketCountListener) {
        this.getChannel().sendMessage(151572, 0, this.putListener(txPacketCountListener));
    }

    @UnsupportedAppUsage
    public int getVerboseLoggingLevel() {
        try {
            int n = this.mService.getVerboseLoggingLevel();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public WifiConfiguration getWifiApConfiguration() {
        try {
            WifiConfiguration wifiConfiguration = this.mService.getWifiApConfiguration();
            return wifiConfiguration;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getWifiApState() {
        try {
            int n = this.mService.getWifiApEnabledState();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getWifiState() {
        try {
            int n = this.mService.getWifiEnabledState();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean initializeMulticastFiltering() {
        try {
            this.mService.initializeMulticastFiltering();
            return true;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean is5GHzBandSupported() {
        return this.isFeatureSupported(2L);
    }

    public boolean isAdditionalStaSupported() {
        return this.isFeatureSupported(2048L);
    }

    @Deprecated
    public boolean isDeviceToApRttSupported() {
        return this.isFeatureSupported(256L);
    }

    @SystemApi
    @Deprecated
    public boolean isDeviceToDeviceRttSupported() {
        return this.isFeatureSupported(128L);
    }

    @UnsupportedAppUsage
    public boolean isDualBandSupported() {
        try {
            boolean bl = this.mService.isDualBandSupported();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDualModeSupported() {
        try {
            boolean bl = this.mService.needs5GHzToAnyApBandConversion();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isEasyConnectSupported() {
        return this.isFeatureSupported(Integer.MIN_VALUE);
    }

    public boolean isEnhancedOpenSupported() {
        return this.isFeatureSupported(0x20000000L);
    }

    public boolean isEnhancedPowerReportingSupported() {
        return this.isFeatureSupported(65536L);
    }

    public boolean isMulticastEnabled() {
        try {
            boolean bl = this.mService.isMulticastEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isOffChannelTdlsSupported() {
        return this.isFeatureSupported(8192L);
    }

    public boolean isP2pSupported() {
        return this.isFeatureSupported(8L);
    }

    public boolean isPasspointSupported() {
        return this.isFeatureSupported(4L);
    }

    @SystemApi
    public boolean isPortableHotspotSupported() {
        return this.isFeatureSupported(16L);
    }

    public boolean isPreferredNetworkOffloadSupported() {
        return this.isFeatureSupported(1024L);
    }

    @Deprecated
    public boolean isScanAlwaysAvailable() {
        try {
            boolean bl = this.mService.isScanAlwaysAvailable();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isTdlsSupported() {
        return this.isFeatureSupported(4096L);
    }

    @SystemApi
    public boolean isWifiApEnabled() {
        boolean bl = this.getWifiApState() == 13;
        return bl;
    }

    public boolean isWifiAwareSupported() {
        return this.isFeatureSupported(64L);
    }

    public boolean isWifiEnabled() {
        boolean bl = this.getWifiState() == 3;
        return bl;
    }

    @SystemApi
    public boolean isWifiScannerSupported() {
        return this.isFeatureSupported(32L);
    }

    public boolean isWpa3SaeSupported() {
        return this.isFeatureSupported(0x8000000L);
    }

    public boolean isWpa3SuiteBSupported() {
        return this.isFeatureSupported(0x10000000L);
    }

    public int matchProviderWithCurrentNetwork(String string2) {
        try {
            int n = this.mService.matchProviderWithCurrentNetwork(string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void notifyUserOfApBandConversion() {
        Log.d(TAG, "apBand was converted, notify the user");
        try {
            this.mService.notifyUserOfApBandConversion(this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean pingSupplicant() {
        return this.isWifiEnabled();
    }

    public void queryPasspointIcon(long l, String string2) {
        try {
            this.mService.queryPasspointIcon(l, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean reassociate() {
        try {
            boolean bl = this.mService.reassociate(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean reconnect() {
        try {
            boolean bl = this.mService.reconnect(this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerNetworkRequestMatchCallback(NetworkRequestMatchCallback networkRequestMatchCallback, Handler object) {
        if (networkRequestMatchCallback != null) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("registerNetworkRequestMatchCallback: callback=");
            ((StringBuilder)object2).append(networkRequestMatchCallback);
            ((StringBuilder)object2).append(", handler=");
            ((StringBuilder)object2).append(object);
            Log.v(TAG, ((StringBuilder)object2).toString());
            object = object == null ? this.mContext.getMainLooper() : ((Handler)object).getLooper();
            object2 = new Binder();
            try {
                IWifiManager iWifiManager = this.mService;
                NetworkRequestMatchCallbackProxy networkRequestMatchCallbackProxy = new NetworkRequestMatchCallbackProxy((Looper)object, networkRequestMatchCallback);
                iWifiManager.registerNetworkRequestMatchCallback((IBinder)object2, networkRequestMatchCallbackProxy, networkRequestMatchCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void registerSoftApCallback(SoftApCallback softApCallback, Handler object) {
        if (softApCallback != null) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("registerSoftApCallback: callback=");
            ((StringBuilder)object2).append(softApCallback);
            ((StringBuilder)object2).append(", handler=");
            ((StringBuilder)object2).append(object);
            Log.v(TAG, ((StringBuilder)object2).toString());
            object = object == null ? this.mContext.getMainLooper() : ((Handler)object).getLooper();
            object2 = new Binder();
            try {
                IWifiManager iWifiManager = this.mService;
                SoftApCallbackProxy softApCallbackProxy = new SoftApCallbackProxy((Looper)object, softApCallback);
                iWifiManager.registerSoftApCallback((IBinder)object2, softApCallbackProxy, softApCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void registerTrafficStateCallback(TrafficStateCallback trafficStateCallback, Handler object) {
        if (trafficStateCallback != null) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("registerTrafficStateCallback: callback=");
            ((StringBuilder)object2).append(trafficStateCallback);
            ((StringBuilder)object2).append(", handler=");
            ((StringBuilder)object2).append(object);
            Log.v(TAG, ((StringBuilder)object2).toString());
            object = object == null ? this.mContext.getMainLooper() : ((Handler)object).getLooper();
            Binder binder = new Binder();
            try {
                object2 = this.mService;
                TrafficStateCallbackProxy trafficStateCallbackProxy = new TrafficStateCallbackProxy((Looper)object, trafficStateCallback);
                object2.registerTrafficStateCallback(binder, trafficStateCallbackProxy, trafficStateCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    @Deprecated
    public boolean removeNetwork(int n) {
        try {
            boolean bl = this.mService.removeNetwork(n, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int removeNetworkSuggestions(List<WifiNetworkSuggestion> list) {
        try {
            int n = this.mService.removeNetworkSuggestions(list, this.mContext.getOpPackageName());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void removeOnWifiUsabilityStatsListener(OnWifiUsabilityStatsListener onWifiUsabilityStatsListener) {
        if (onWifiUsabilityStatsListener != null) {
            if (this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("removeOnWifiUsabilityStatsListener: listener=");
                stringBuilder.append(onWifiUsabilityStatsListener);
                Log.v(TAG, stringBuilder.toString());
            }
            try {
                this.mService.removeOnWifiUsabilityStatsListener(onWifiUsabilityStatsListener.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("listener cannot be null");
    }

    @Deprecated
    public void removePasspointConfiguration(String object) {
        try {
            if (this.mService.removePasspointConfiguration((String)object, this.mContext.getOpPackageName())) {
                return;
            }
            object = new IllegalArgumentException();
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void restoreBackupData(byte[] arrby) {
        try {
            this.mService.restoreBackupData(arrby);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void restoreSupplicantBackupData(byte[] arrby, byte[] arrby2) {
        try {
            this.mService.restoreSupplicantBackupData(arrby, arrby2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public byte[] retrieveBackupData() {
        try {
            byte[] arrby = this.mService.retrieveBackupData();
            return arrby;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void save(WifiConfiguration wifiConfiguration, ActionListener actionListener) {
        if (wifiConfiguration != null) {
            this.getChannel().sendMessage(151559, 0, this.putListener(actionListener), wifiConfiguration);
            return;
        }
        throw new IllegalArgumentException("config cannot be null");
    }

    @Deprecated
    public boolean saveConfiguration() {
        return false;
    }

    public void setCountryCode(String string2) {
        try {
            this.mService.setCountryCode(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setDeviceMobilityState(int n) {
        try {
            this.mService.setDeviceMobilityState(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setEnableAutoJoinWhenAssociated(boolean bl) {
        return false;
    }

    public void setTdlsEnabled(InetAddress inetAddress, boolean bl) {
        try {
            this.mService.enableTdls(inetAddress.getHostAddress(), bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setTdlsEnabledWithMacAddress(String string2, boolean bl) {
        try {
            this.mService.enableTdlsWithMacAddress(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setWifiApConfiguration(WifiConfiguration wifiConfiguration) {
        try {
            boolean bl = this.mService.setWifiApConfiguration(wifiConfiguration, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean setWifiEnabled(boolean bl) {
        try {
            bl = this.mService.setWifiEnabled(this.mContext.getOpPackageName(), bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startEasyConnectAsConfiguratorInitiator(String string2, int n, int n2, Executor executor, EasyConnectStatusCallback easyConnectStatusCallback) {
        Binder binder = new Binder();
        try {
            IWifiManager iWifiManager = this.mService;
            EasyConnectCallbackProxy easyConnectCallbackProxy = new EasyConnectCallbackProxy(executor, easyConnectStatusCallback);
            iWifiManager.startDppAsConfiguratorInitiator(binder, string2, n, n2, easyConnectCallbackProxy);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startEasyConnectAsEnrolleeInitiator(String string2, Executor executor, EasyConnectStatusCallback easyConnectStatusCallback) {
        Binder binder = new Binder();
        try {
            IWifiManager iWifiManager = this.mService;
            EasyConnectCallbackProxy easyConnectCallbackProxy = new EasyConnectCallbackProxy(executor, easyConnectStatusCallback);
            iWifiManager.startDppAsEnrolleeInitiator(binder, string2, easyConnectCallbackProxy);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void startLocalOnlyHotspot(LocalOnlyHotspotCallback object, Handler object2) {
        Object object3 = this.mLock;
        synchronized (object3) {
            object2 = object2 == null ? this.mContext.getMainLooper() : ((Handler)object2).getLooper();
            LocalOnlyHotspotCallbackProxy localOnlyHotspotCallbackProxy = new LocalOnlyHotspotCallbackProxy(this, (Looper)object2, (LocalOnlyHotspotCallback)object);
            try {
                object2 = this.mContext.getOpPackageName();
                object = this.mService;
                Messenger messenger = localOnlyHotspotCallbackProxy.getMessenger();
                Binder binder = new Binder();
                int n = object.startLocalOnlyHotspot(messenger, binder, (String)object2);
                if (n != 0) {
                    localOnlyHotspotCallbackProxy.notifyFailed(n);
                    return;
                }
                this.mLOHSCallbackProxy = localOnlyHotspotCallbackProxy;
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public boolean startScan() {
        return this.startScan(null);
    }

    @SystemApi
    public boolean startScan(WorkSource object) {
        try {
            object = this.mContext.getOpPackageName();
            boolean bl = this.mService.startScan((String)object);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean startSoftAp(WifiConfiguration wifiConfiguration) {
        try {
            boolean bl = this.mService.startSoftAp(wifiConfiguration);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startSubscriptionProvisioning(OsuProvider osuProvider, Executor executor, ProvisioningCallback provisioningCallback) {
        if (executor != null) {
            if (provisioningCallback != null) {
                try {
                    IWifiManager iWifiManager = this.mService;
                    ProvisioningCallbackProxy provisioningCallbackProxy = new ProvisioningCallbackProxy(executor, provisioningCallback);
                    iWifiManager.startSubscriptionProvisioning(osuProvider, provisioningCallbackProxy);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("callback must not be null");
        }
        throw new IllegalArgumentException("executor must not be null");
    }

    public void startWps(WpsInfo wpsInfo, WpsCallback wpsCallback) {
        if (wpsCallback != null) {
            wpsCallback.onFailed(0);
        }
    }

    @SystemApi
    public void stopEasyConnectSession() {
        try {
            this.mService.stopDppSession();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean stopSoftAp() {
        try {
            boolean bl = this.mService.stopSoftAp();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterLocalOnlyHotspotObserver() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mLOHSObserverProxy == null) {
                return;
            }
            this.mLOHSObserverProxy = null;
            try {
                this.mService.stopWatchLocalOnlyHotspot();
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void unregisterNetworkRequestMatchCallback(NetworkRequestMatchCallback networkRequestMatchCallback) {
        if (networkRequestMatchCallback != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unregisterNetworkRequestMatchCallback: callback=");
            stringBuilder.append(networkRequestMatchCallback);
            Log.v(TAG, stringBuilder.toString());
            try {
                this.mService.unregisterNetworkRequestMatchCallback(networkRequestMatchCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void unregisterSoftApCallback(SoftApCallback softApCallback) {
        if (softApCallback != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unregisterSoftApCallback: callback=");
            stringBuilder.append(softApCallback);
            Log.v(TAG, stringBuilder.toString());
            try {
                this.mService.unregisterSoftApCallback(softApCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void unregisterTrafficStateCallback(TrafficStateCallback trafficStateCallback) {
        if (trafficStateCallback != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unregisterTrafficStateCallback: callback=");
            stringBuilder.append(trafficStateCallback);
            Log.v(TAG, stringBuilder.toString());
            try {
                this.mService.unregisterTrafficStateCallback(trafficStateCallback.hashCode());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    public void updateInterfaceIpState(String string2, int n) {
        try {
            this.mService.updateInterfaceIpState(string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int updateNetwork(WifiConfiguration wifiConfiguration) {
        if (wifiConfiguration != null && wifiConfiguration.networkId >= 0) {
            return this.addOrUpdateNetwork(wifiConfiguration);
        }
        return -1;
    }

    @SystemApi
    public void updateWifiUsabilityScore(int n, int n2, int n3) {
        try {
            this.mService.updateWifiUsabilityScore(n, n2, n3);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void watchLocalOnlyHotspot(LocalOnlyHotspotObserver object, Handler object2) {
        Object object3 = this.mLock;
        synchronized (object3) {
            object2 = object2 == null ? this.mContext.getMainLooper() : ((Handler)object2).getLooper();
            Object object4 = new LocalOnlyHotspotObserverProxy(this, (Looper)object2, (LocalOnlyHotspotObserver)object);
            this.mLOHSObserverProxy = object4;
            try {
                object2 = this.mService;
                object = this.mLOHSObserverProxy.getMessenger();
                object4 = new Binder();
                object2.startWatchLocalOnlyHotspot((Messenger)object, (IBinder)object4);
                this.mLOHSObserverProxy.registered();
                return;
            }
            catch (RemoteException remoteException) {
                this.mLOHSObserverProxy = null;
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public static interface ActionListener {
        public void onFailure(int var1);

        public void onSuccess();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DeviceMobilityState {
    }

    private static class EasyConnectCallbackProxy
    extends IDppCallback.Stub {
        private final EasyConnectStatusCallback mEasyConnectStatusCallback;
        private final Executor mExecutor;

        EasyConnectCallbackProxy(Executor executor, EasyConnectStatusCallback easyConnectStatusCallback) {
            this.mExecutor = executor;
            this.mEasyConnectStatusCallback = easyConnectStatusCallback;
        }

        public /* synthetic */ void lambda$onFailure$2$WifiManager$EasyConnectCallbackProxy(int n) {
            this.mEasyConnectStatusCallback.onFailure(n);
        }

        public /* synthetic */ void lambda$onProgress$3$WifiManager$EasyConnectCallbackProxy(int n) {
            this.mEasyConnectStatusCallback.onProgress(n);
        }

        public /* synthetic */ void lambda$onSuccess$1$WifiManager$EasyConnectCallbackProxy(int n) {
            this.mEasyConnectStatusCallback.onConfiguratorSuccess(n);
        }

        public /* synthetic */ void lambda$onSuccessConfigReceived$0$WifiManager$EasyConnectCallbackProxy(int n) {
            this.mEasyConnectStatusCallback.onEnrolleeSuccess(n);
        }

        @Override
        public void onFailure(int n) {
            Log.d(WifiManager.TAG, "Easy Connect onFailure callback");
            this.mExecutor.execute(new _$$Lambda$WifiManager$EasyConnectCallbackProxy$fmVMj2ImIgtBYa9roBT0GyOubTI(this, n));
        }

        @Override
        public void onProgress(int n) {
            Log.d(WifiManager.TAG, "Easy Connect onProgress callback");
            this.mExecutor.execute(new _$$Lambda$WifiManager$EasyConnectCallbackProxy$YV1XBtKl8L8u8zCEX4lzLkOT6LQ(this, n));
        }

        @Override
        public void onSuccess(int n) {
            Log.d(WifiManager.TAG, "Easy Connect onSuccess callback");
            this.mExecutor.execute(new _$$Lambda$WifiManager$EasyConnectCallbackProxy$wTsmN4734yyutavZxcKa2TZ_4Cc(this, n));
        }

        @Override
        public void onSuccessConfigReceived(int n) {
            Log.d(WifiManager.TAG, "Easy Connect onSuccessConfigReceived callback");
            this.mExecutor.execute(new _$$Lambda$WifiManager$EasyConnectCallbackProxy$ObU39aoKguVIx_qQTyZyomhDAAg(this, n));
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EasyConnectNetworkRole {
    }

    public static class LocalOnlyHotspotCallback {
        public static final int ERROR_GENERIC = 2;
        public static final int ERROR_INCOMPATIBLE_MODE = 3;
        public static final int ERROR_NO_CHANNEL = 1;
        public static final int ERROR_TETHERING_DISALLOWED = 4;
        public static final int REQUEST_REGISTERED = 0;

        public void onFailed(int n) {
        }

        public void onStarted(LocalOnlyHotspotReservation localOnlyHotspotReservation) {
        }

        public void onStopped() {
        }
    }

    private static class LocalOnlyHotspotCallbackProxy {
        private final Handler mHandler;
        private final Looper mLooper;
        private final Messenger mMessenger;
        private final WeakReference<WifiManager> mWifiManager;

        LocalOnlyHotspotCallbackProxy(WifiManager wifiManager, Looper looper, final LocalOnlyHotspotCallback localOnlyHotspotCallback) {
            this.mWifiManager = new WeakReference<WifiManager>(wifiManager);
            this.mLooper = looper;
            this.mHandler = new Handler(looper){

                @Override
                public void handleMessage(Message object) {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("LocalOnlyHotspotCallbackProxy: handle message what: ");
                    ((StringBuilder)object2).append(((Message)object).what);
                    ((StringBuilder)object2).append(" msg: ");
                    ((StringBuilder)object2).append(object);
                    Log.d(WifiManager.TAG, ((StringBuilder)object2).toString());
                    object2 = (WifiManager)LocalOnlyHotspotCallbackProxy.this.mWifiManager.get();
                    if (object2 == null) {
                        Log.w(WifiManager.TAG, "LocalOnlyHotspotCallbackProxy: handle message post GC");
                        return;
                    }
                    int n = ((Message)object).what;
                    if (n != 0) {
                        if (n != 1) {
                            if (n != 2) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("LocalOnlyHotspotCallbackProxy unhandled message.  type: ");
                                ((StringBuilder)object2).append(((Message)object).what);
                                Log.e(WifiManager.TAG, ((StringBuilder)object2).toString());
                            } else {
                                n = ((Message)object).arg1;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("LocalOnlyHotspotCallbackProxy: failed to start.  reason: ");
                                ((StringBuilder)object).append(n);
                                Log.w(WifiManager.TAG, ((StringBuilder)object).toString());
                                localOnlyHotspotCallback.onFailed(n);
                                Log.w(WifiManager.TAG, "done with the callback...");
                            }
                        } else {
                            Log.w(WifiManager.TAG, "LocalOnlyHotspotCallbackProxy: hotspot stopped");
                            localOnlyHotspotCallback.onStopped();
                        }
                    } else {
                        WifiConfiguration wifiConfiguration = (WifiConfiguration)((Message)object).obj;
                        if (wifiConfiguration == null) {
                            Log.e(WifiManager.TAG, "LocalOnlyHotspotCallbackProxy: config cannot be null.");
                            localOnlyHotspotCallback.onFailed(2);
                            return;
                        }
                        object = localOnlyHotspotCallback;
                        Objects.requireNonNull(object2);
                        ((LocalOnlyHotspotCallback)object).onStarted((WifiManager)object2.new LocalOnlyHotspotReservation(wifiConfiguration));
                    }
                }
            };
            this.mMessenger = new Messenger(this.mHandler);
        }

        public Messenger getMessenger() {
            return this.mMessenger;
        }

        public void notifyFailed(int n) throws RemoteException {
            Message message = Message.obtain();
            message.what = 2;
            message.arg1 = n;
            this.mMessenger.send(message);
        }

    }

    public static class LocalOnlyHotspotObserver {
        public void onRegistered(LocalOnlyHotspotSubscription localOnlyHotspotSubscription) {
        }

        public void onStarted(WifiConfiguration wifiConfiguration) {
        }

        public void onStopped() {
        }
    }

    private static class LocalOnlyHotspotObserverProxy {
        private final Handler mHandler;
        private final Looper mLooper;
        private final Messenger mMessenger;
        private final WeakReference<WifiManager> mWifiManager;

        LocalOnlyHotspotObserverProxy(WifiManager wifiManager, Looper looper, final LocalOnlyHotspotObserver localOnlyHotspotObserver) {
            this.mWifiManager = new WeakReference<WifiManager>(wifiManager);
            this.mLooper = looper;
            this.mHandler = new Handler(looper){

                @Override
                public void handleMessage(Message object) {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("LocalOnlyHotspotObserverProxy: handle message what: ");
                    ((StringBuilder)object2).append(((Message)object).what);
                    ((StringBuilder)object2).append(" msg: ");
                    ((StringBuilder)object2).append(object);
                    Log.d(WifiManager.TAG, ((StringBuilder)object2).toString());
                    object2 = (WifiManager)LocalOnlyHotspotObserverProxy.this.mWifiManager.get();
                    if (object2 == null) {
                        Log.w(WifiManager.TAG, "LocalOnlyHotspotObserverProxy: handle message post GC");
                        return;
                    }
                    int n = ((Message)object).what;
                    if (n != 0) {
                        if (n != 1) {
                            if (n != 3) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("LocalOnlyHotspotObserverProxy unhandled message.  type: ");
                                ((StringBuilder)object2).append(((Message)object).what);
                                Log.e(WifiManager.TAG, ((StringBuilder)object2).toString());
                            } else {
                                object = localOnlyHotspotObserver;
                                Objects.requireNonNull(object2);
                                ((LocalOnlyHotspotObserver)object).onRegistered((WifiManager)object2.new LocalOnlyHotspotSubscription());
                            }
                        } else {
                            localOnlyHotspotObserver.onStopped();
                        }
                    } else {
                        object = (WifiConfiguration)((Message)object).obj;
                        if (object == null) {
                            Log.e(WifiManager.TAG, "LocalOnlyHotspotObserverProxy: config cannot be null.");
                            return;
                        }
                        localOnlyHotspotObserver.onStarted((WifiConfiguration)object);
                    }
                }
            };
            this.mMessenger = new Messenger(this.mHandler);
        }

        public Messenger getMessenger() {
            return this.mMessenger;
        }

        public void registered() throws RemoteException {
            Message message = Message.obtain();
            message.what = 3;
            this.mMessenger.send(message);
        }

    }

    public class LocalOnlyHotspotReservation
    implements AutoCloseable {
        private final CloseGuard mCloseGuard = CloseGuard.get();
        private final WifiConfiguration mConfig;

        @VisibleForTesting
        public LocalOnlyHotspotReservation(WifiConfiguration wifiConfiguration) {
            this.mConfig = wifiConfiguration;
            this.mCloseGuard.open("close");
        }

        @Override
        public void close() {
            try {
                WifiManager.this.stopLocalOnlyHotspot();
                this.mCloseGuard.close();
            }
            catch (Exception exception) {
                Log.e(WifiManager.TAG, "Failed to stop Local Only Hotspot.");
            }
        }

        protected void finalize() throws Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.close();
                return;
            }
            finally {
                super.finalize();
            }
        }

        public WifiConfiguration getWifiConfiguration() {
            return this.mConfig;
        }
    }

    public class LocalOnlyHotspotSubscription
    implements AutoCloseable {
        private final CloseGuard mCloseGuard = CloseGuard.get();

        @VisibleForTesting
        public LocalOnlyHotspotSubscription() {
            this.mCloseGuard.open("close");
        }

        @Override
        public void close() {
            try {
                WifiManager.this.unregisterLocalOnlyHotspotObserver();
                this.mCloseGuard.close();
            }
            catch (Exception exception) {
                Log.e(WifiManager.TAG, "Failed to unregister LocalOnlyHotspotObserver.");
            }
        }

        protected void finalize() throws Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.close();
                return;
            }
            finally {
                super.finalize();
            }
        }
    }

    public class MulticastLock {
        private final IBinder mBinder;
        private boolean mHeld;
        private int mRefCount;
        private boolean mRefCounted;
        private String mTag;

        private MulticastLock(String string2) {
            this.mTag = string2;
            this.mBinder = new Binder();
            this.mRefCount = 0;
            this.mRefCounted = true;
            this.mHeld = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void acquire() {
            block15 : {
                block16 : {
                    block14 : {
                        int n;
                        IBinder iBinder = this.mBinder;
                        // MONITORENTER : iBinder
                        if (!this.mRefCounted) break block14;
                        this.mRefCount = n = this.mRefCount + 1;
                        if (n != 1) break block15;
                        break block16;
                    }
                    boolean bl = this.mHeld;
                    if (bl) break block15;
                }
                try {
                    WifiManager.this.mService.acquireMulticastLock(this.mBinder, this.mTag);
                    WifiManager wifiManager = WifiManager.this;
                    // MONITORENTER : wifiManager
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                if (WifiManager.this.mActiveLockCount < 50) {
                    WifiManager.access$808(WifiManager.this);
                    // MONITOREXIT : wifiManager
                    this.mHeld = true;
                    return;
                }
                WifiManager.this.mService.releaseMulticastLock(this.mTag);
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Exceeded maximum number of wifi locks");
                throw unsupportedOperationException;
            }
            // MONITOREXIT : iBinder
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.setReferenceCounted(false);
            this.release();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean isHeld() {
            IBinder iBinder = this.mBinder;
            synchronized (iBinder) {
                return this.mHeld;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void release() {
            Object object;
            block15 : {
                block16 : {
                    block14 : {
                        int n;
                        IBinder iBinder = this.mBinder;
                        // MONITORENTER : iBinder
                        if (!this.mRefCounted) break block14;
                        this.mRefCount = n = this.mRefCount - 1;
                        if (n != 0) break block15;
                        break block16;
                    }
                    boolean bl = this.mHeld;
                    if (!bl) break block15;
                }
                try {
                    WifiManager.this.mService.releaseMulticastLock(this.mTag);
                    object = WifiManager.this;
                    // MONITORENTER : object
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                WifiManager.access$810(WifiManager.this);
                // MONITOREXIT : object
                this.mHeld = false;
            }
            if (this.mRefCount >= 0) {
                // MONITOREXIT : iBinder
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MulticastLock under-locked ");
            stringBuilder.append(this.mTag);
            object = new RuntimeException(stringBuilder.toString());
            throw object;
        }

        public void setReferenceCounted(boolean bl) {
            this.mRefCounted = bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String toString() {
            IBinder iBinder = this.mBinder;
            synchronized (iBinder) {
                CharSequence charSequence;
                String string2 = Integer.toHexString(System.identityHashCode(this));
                String string3 = this.mHeld ? "held; " : "";
                if (this.mRefCounted) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("refcounted: refcount = ");
                    ((StringBuilder)charSequence).append(this.mRefCount);
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = "not refcounted";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MulticastLock{ ");
                stringBuilder.append(string2);
                stringBuilder.append("; ");
                stringBuilder.append(string3);
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" }");
                return stringBuilder.toString();
            }
        }
    }

    public static interface NetworkRequestMatchCallback {
        public void onAbort();

        public void onMatch(List<ScanResult> var1);

        public void onUserSelectionCallbackRegistration(NetworkRequestUserSelectionCallback var1);

        public void onUserSelectionConnectFailure(WifiConfiguration var1);

        public void onUserSelectionConnectSuccess(WifiConfiguration var1);
    }

    private class NetworkRequestMatchCallbackProxy
    extends INetworkRequestMatchCallback.Stub {
        private final NetworkRequestMatchCallback mCallback;
        private final Handler mHandler;

        NetworkRequestMatchCallbackProxy(Looper looper, NetworkRequestMatchCallback networkRequestMatchCallback) {
            this.mHandler = new Handler(looper);
            this.mCallback = networkRequestMatchCallback;
        }

        public /* synthetic */ void lambda$onAbort$1$WifiManager$NetworkRequestMatchCallbackProxy() {
            this.mCallback.onAbort();
        }

        public /* synthetic */ void lambda$onMatch$2$WifiManager$NetworkRequestMatchCallbackProxy(List list) {
            this.mCallback.onMatch(list);
        }

        public /* synthetic */ void lambda$onUserSelectionCallbackRegistration$0$WifiManager$NetworkRequestMatchCallbackProxy(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) {
            this.mCallback.onUserSelectionCallbackRegistration(new NetworkRequestUserSelectionCallbackProxy(iNetworkRequestUserSelectionCallback));
        }

        public /* synthetic */ void lambda$onUserSelectionConnectFailure$4$WifiManager$NetworkRequestMatchCallbackProxy(WifiConfiguration wifiConfiguration) {
            this.mCallback.onUserSelectionConnectFailure(wifiConfiguration);
        }

        public /* synthetic */ void lambda$onUserSelectionConnectSuccess$3$WifiManager$NetworkRequestMatchCallbackProxy(WifiConfiguration wifiConfiguration) {
            this.mCallback.onUserSelectionConnectSuccess(wifiConfiguration);
        }

        @Override
        public void onAbort() {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                Log.v(WifiManager.TAG, "NetworkRequestMatchCallbackProxy: onAbort");
            }
            this.mHandler.post(new _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$sy4224jn5G2QTmFKYUY0fGWCJ5Q(this));
        }

        @Override
        public void onMatch(List<ScanResult> list) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NetworkRequestMatchCallbackProxy: onMatch scanResults: ");
                stringBuilder.append(list);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$8wy7AFc9OgD124mPKDe8H6vuPTQ(this, list));
        }

        @Override
        public void onUserSelectionCallbackRegistration(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NetworkRequestMatchCallbackProxy: onUserSelectionCallbackRegistration callback: ");
                stringBuilder.append(iNetworkRequestUserSelectionCallback);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$DYo_nMH0tB37PG_5OviApSTSGXg(this, iNetworkRequestUserSelectionCallback));
        }

        @Override
        public void onUserSelectionConnectFailure(WifiConfiguration wifiConfiguration) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NetworkRequestMatchCallbackProxy: onUserSelectionConnectFailure wificonfiguration: ");
                stringBuilder.append(wifiConfiguration);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$MJqaBvGtvUfHUJtjhgTRIQ7GCr4(this, wifiConfiguration));
        }

        @Override
        public void onUserSelectionConnectSuccess(WifiConfiguration wifiConfiguration) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NetworkRequestMatchCallbackProxy: onUserSelectionConnectSuccess  wificonfiguration: ");
                stringBuilder.append(wifiConfiguration);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$NetworkRequestMatchCallbackProxy$KPxBZNMm8VDinf6ZcYWL1RJk9Zc(this, wifiConfiguration));
        }
    }

    public static interface NetworkRequestUserSelectionCallback {
        public void reject();

        public void select(WifiConfiguration var1);
    }

    private class NetworkRequestUserSelectionCallbackProxy
    implements NetworkRequestUserSelectionCallback {
        private final INetworkRequestUserSelectionCallback mCallback;

        NetworkRequestUserSelectionCallbackProxy(INetworkRequestUserSelectionCallback iNetworkRequestUserSelectionCallback) {
            this.mCallback = iNetworkRequestUserSelectionCallback;
        }

        @Override
        public void reject() {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                Log.v(WifiManager.TAG, "NetworkRequestUserSelectionCallbackProxy: reject");
            }
            try {
                this.mCallback.reject();
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(WifiManager.TAG, "Failed to invoke onRejected", remoteException);
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @Override
        public void select(WifiConfiguration wifiConfiguration) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NetworkRequestUserSelectionCallbackProxy: select wificonfiguration: ");
                stringBuilder.append(wifiConfiguration);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            try {
                this.mCallback.select(wifiConfiguration);
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(WifiManager.TAG, "Failed to invoke onSelected", remoteException);
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NetworkSuggestionsStatusCode {
    }

    @SystemApi
    public static interface OnWifiUsabilityStatsListener {
        public void onWifiUsabilityStats(int var1, boolean var2, WifiUsabilityStatsEntry var3);
    }

    private static class ProvisioningCallbackProxy
    extends IProvisioningCallback.Stub {
        private final ProvisioningCallback mCallback;
        private final Executor mExecutor;

        ProvisioningCallbackProxy(Executor executor, ProvisioningCallback provisioningCallback) {
            this.mExecutor = executor;
            this.mCallback = provisioningCallback;
        }

        public /* synthetic */ void lambda$onProvisioningComplete$2$WifiManager$ProvisioningCallbackProxy() {
            this.mCallback.onProvisioningComplete();
        }

        public /* synthetic */ void lambda$onProvisioningFailure$1$WifiManager$ProvisioningCallbackProxy(int n) {
            this.mCallback.onProvisioningFailure(n);
        }

        public /* synthetic */ void lambda$onProvisioningStatus$0$WifiManager$ProvisioningCallbackProxy(int n) {
            this.mCallback.onProvisioningStatus(n);
        }

        @Override
        public void onProvisioningComplete() {
            this.mExecutor.execute(new _$$Lambda$WifiManager$ProvisioningCallbackProxy$ARmFIxMD9Os9eGpiffTyA3WhD0Q(this));
        }

        @Override
        public void onProvisioningFailure(int n) {
            this.mExecutor.execute(new _$$Lambda$WifiManager$ProvisioningCallbackProxy$rgPeSRj_1qriYZtaCu57EZHtc_Q(this, n));
        }

        @Override
        public void onProvisioningStatus(int n) {
            this.mExecutor.execute(new _$$Lambda$WifiManager$ProvisioningCallbackProxy$0_NXiwyrbrT_579x_6QMO0y3rzc(this, n));
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SapStartFailure {
    }

    private class ServiceHandler
    extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        private void dispatchMessageToListeners(Message parcelable) {
            Object object = WifiManager.this.removeListener(parcelable.arg2);
            switch (parcelable.what) {
                default: {
                    break;
                }
                case 151574: {
                    if (object == null) break;
                    ((TxPacketCountListener)object).onFailure(parcelable.arg1);
                    break;
                }
                case 151573: {
                    if (object == null) break;
                    parcelable = (RssiPacketCountInfo)parcelable.obj;
                    if (parcelable != null) {
                        ((TxPacketCountListener)object).onSuccess(((RssiPacketCountInfo)parcelable).txgood + ((RssiPacketCountInfo)parcelable).txbad);
                        break;
                    }
                    ((TxPacketCountListener)object).onFailure(0);
                    break;
                }
                case 151555: 
                case 151558: 
                case 151561: 
                case 151571: {
                    if (object == null) break;
                    ((ActionListener)object).onSuccess();
                    break;
                }
                case 151554: 
                case 151557: 
                case 151560: 
                case 151570: {
                    if (object == null) break;
                    ((ActionListener)object).onFailure(parcelable.arg1);
                    break;
                }
                case 69636: {
                    Log.e(WifiManager.TAG, "Channel connection lost");
                    WifiManager.this.mAsyncChannel = null;
                    this.getLooper().quit();
                    break;
                }
                case 69634: {
                    break;
                }
                case 69632: {
                    if (parcelable.arg1 == 0) {
                        WifiManager.this.mAsyncChannel.sendMessage(69633);
                    } else {
                        Log.e(WifiManager.TAG, "Failed to set up channel connection");
                        WifiManager.this.mAsyncChannel = null;
                    }
                    WifiManager.this.mConnected.countDown();
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object = sServiceHandlerDispatchLock;
            synchronized (object) {
                this.dispatchMessageToListeners(message);
                return;
            }
        }
    }

    public static interface SoftApCallback {
        public void onNumClientsChanged(int var1);

        public void onStateChanged(int var1, int var2);
    }

    private class SoftApCallbackProxy
    extends ISoftApCallback.Stub {
        private final SoftApCallback mCallback;
        private final Handler mHandler;

        SoftApCallbackProxy(Looper looper, SoftApCallback softApCallback) {
            this.mHandler = new Handler(looper);
            this.mCallback = softApCallback;
        }

        public /* synthetic */ void lambda$onNumClientsChanged$1$WifiManager$SoftApCallbackProxy(int n) {
            this.mCallback.onNumClientsChanged(n);
        }

        public /* synthetic */ void lambda$onStateChanged$0$WifiManager$SoftApCallbackProxy(int n, int n2) {
            this.mCallback.onStateChanged(n, n2);
        }

        @Override
        public void onNumClientsChanged(int n) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SoftApCallbackProxy: onNumClientsChanged: numClients=");
                stringBuilder.append(n);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$SoftApCallbackProxy$f44R8L0UcqgnIaD5lXMmeuRHCWI(this, n));
        }

        @Override
        public void onStateChanged(int n, int n2) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SoftApCallbackProxy: onStateChanged: state=");
                stringBuilder.append(n);
                stringBuilder.append(", failureReason=");
                stringBuilder.append(n2);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$SoftApCallbackProxy$vmSW5veUpC52oRINBy419US5snk(this, n, n2));
        }
    }

    public static interface TrafficStateCallback {
        public static final int DATA_ACTIVITY_IN = 1;
        public static final int DATA_ACTIVITY_INOUT = 3;
        public static final int DATA_ACTIVITY_NONE = 0;
        public static final int DATA_ACTIVITY_OUT = 2;

        public void onStateChanged(int var1);
    }

    private class TrafficStateCallbackProxy
    extends ITrafficStateCallback.Stub {
        private final TrafficStateCallback mCallback;
        private final Handler mHandler;

        TrafficStateCallbackProxy(Looper looper, TrafficStateCallback trafficStateCallback) {
            this.mHandler = new Handler(looper);
            this.mCallback = trafficStateCallback;
        }

        public /* synthetic */ void lambda$onStateChanged$0$WifiManager$TrafficStateCallbackProxy(int n) {
            this.mCallback.onStateChanged(n);
        }

        @Override
        public void onStateChanged(int n) {
            if (WifiManager.this.mVerboseLoggingEnabled) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("TrafficStateCallbackProxy: onStateChanged state=");
                stringBuilder.append(n);
                Log.v(WifiManager.TAG, stringBuilder.toString());
            }
            this.mHandler.post(new _$$Lambda$WifiManager$TrafficStateCallbackProxy$zQoZBZ4jRXbcyDZer28skV_T0jI(this, n));
        }
    }

    public static interface TxPacketCountListener {
        public void onFailure(int var1);

        public void onSuccess(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WifiApState {
    }

    public class WifiLock {
        private final IBinder mBinder;
        private boolean mHeld;
        int mLockType;
        private int mRefCount;
        private boolean mRefCounted;
        private String mTag;
        private WorkSource mWorkSource;

        private WifiLock(int n, String string2) {
            this.mTag = string2;
            this.mLockType = n;
            this.mBinder = new Binder();
            this.mRefCount = 0;
            this.mRefCounted = true;
            this.mHeld = false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void acquire() {
            block15 : {
                block16 : {
                    block14 : {
                        int n;
                        IBinder iBinder = this.mBinder;
                        // MONITORENTER : iBinder
                        if (!this.mRefCounted) break block14;
                        this.mRefCount = n = this.mRefCount + 1;
                        if (n != 1) break block15;
                        break block16;
                    }
                    boolean bl = this.mHeld;
                    if (bl) break block15;
                }
                try {
                    WifiManager.this.mService.acquireWifiLock(this.mBinder, this.mLockType, this.mTag, this.mWorkSource);
                    WifiManager wifiManager = WifiManager.this;
                    // MONITORENTER : wifiManager
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                if (WifiManager.this.mActiveLockCount < 50) {
                    WifiManager.access$808(WifiManager.this);
                    // MONITOREXIT : wifiManager
                    this.mHeld = true;
                    return;
                }
                WifiManager.this.mService.releaseWifiLock(this.mBinder);
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("Exceeded maximum number of wifi locks");
                throw unsupportedOperationException;
            }
            // MONITOREXIT : iBinder
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        protected void finalize() throws Throwable {
            super.finalize();
            IBinder iBinder = this.mBinder;
            // MONITORENTER : iBinder
            boolean bl = this.mHeld;
            if (!bl) {
                // MONITOREXIT : iBinder
                return;
            }
            try {
                WifiManager.this.mService.releaseWifiLock(this.mBinder);
                WifiManager wifiManager = WifiManager.this;
                // MONITORENTER : wifiManager
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            WifiManager.access$810(WifiManager.this);
            // MONITOREXIT : wifiManager
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean isHeld() {
            IBinder iBinder = this.mBinder;
            synchronized (iBinder) {
                return this.mHeld;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void release() {
            Object object;
            block15 : {
                block16 : {
                    block14 : {
                        int n;
                        IBinder iBinder = this.mBinder;
                        // MONITORENTER : iBinder
                        if (!this.mRefCounted) break block14;
                        this.mRefCount = n = this.mRefCount - 1;
                        if (n != 0) break block15;
                        break block16;
                    }
                    boolean bl = this.mHeld;
                    if (!bl) break block15;
                }
                try {
                    WifiManager.this.mService.releaseWifiLock(this.mBinder);
                    object = WifiManager.this;
                    // MONITORENTER : object
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                WifiManager.access$810(WifiManager.this);
                // MONITOREXIT : object
                this.mHeld = false;
            }
            if (this.mRefCount >= 0) {
                // MONITOREXIT : iBinder
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("WifiLock under-locked ");
            ((StringBuilder)object).append(this.mTag);
            RuntimeException runtimeException = new RuntimeException(((StringBuilder)object).toString());
            throw runtimeException;
        }

        public void setReferenceCounted(boolean bl) {
            this.mRefCounted = bl;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void setWorkSource(WorkSource workSource) {
            IBinder iBinder = this.mBinder;
            synchronized (iBinder) {
                Throwable throwable2;
                block14 : {
                    WorkSource workSource2;
                    boolean bl;
                    block13 : {
                        workSource2 = workSource;
                        if (workSource != null) {
                            workSource2 = workSource;
                            try {
                                if (!workSource.isEmpty()) break block13;
                                workSource2 = null;
                            }
                            catch (Throwable throwable2) {
                                break block14;
                            }
                        }
                    }
                    boolean bl2 = true;
                    if (workSource2 == null) {
                        this.mWorkSource = null;
                    } else {
                        workSource2.clearNames();
                        workSource = this.mWorkSource;
                        bl2 = true;
                        if (workSource == null) {
                            if (this.mWorkSource == null) {
                                bl2 = false;
                            }
                            this.mWorkSource = workSource = new WorkSource(workSource2);
                        } else {
                            boolean bl3;
                            bl2 = bl3 = this.mWorkSource.equals(workSource2) ^ true;
                            if (bl3) {
                                this.mWorkSource.set(workSource2);
                                bl2 = bl3;
                            }
                        }
                    }
                    if (bl2 && (bl = this.mHeld)) {
                        try {
                            WifiManager.this.mService.updateWifiLockWorkSource(this.mBinder, this.mWorkSource);
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    return;
                }
                throw throwable2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String toString() {
            IBinder iBinder = this.mBinder;
            synchronized (iBinder) {
                CharSequence charSequence;
                String string2 = Integer.toHexString(System.identityHashCode(this));
                String string3 = this.mHeld ? "held; " : "";
                if (this.mRefCounted) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("refcounted: refcount = ");
                    ((StringBuilder)charSequence).append(this.mRefCount);
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = "not refcounted";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("WifiLock{ ");
                stringBuilder.append(string2);
                stringBuilder.append("; ");
                stringBuilder.append(string3);
                stringBuilder.append((String)charSequence);
                stringBuilder.append(" }");
                return stringBuilder.toString();
            }
        }
    }

    public static abstract class WpsCallback {
        public abstract void onFailed(int var1);

        public abstract void onStarted(String var1);

        public abstract void onSucceeded();
    }

}

