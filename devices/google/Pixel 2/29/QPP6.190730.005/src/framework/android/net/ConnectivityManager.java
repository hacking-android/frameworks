/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.event.NetworkEventDispatcher
 */
package android.net;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.net.ConnectionInfo;
import android.net.ConnectivityThread;
import android.net.IConnectivityManager;
import android.net.INetworkPolicyManager;
import android.net.ISocketKeepaliveCallback;
import android.net.ITetheringEventCallback;
import android.net.IpSecManager;
import android.net.LinkProperties;
import android.net.NattSocketKeepalive;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkMisc;
import android.net.NetworkQuotaInfo;
import android.net.NetworkRequest;
import android.net.NetworkUtils;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.net.SocketKeepalive;
import android.net.TcpSocketKeepalive;
import android.net._$$Lambda$ConnectivityManager$3$BfAvTRJTF0an2PdeqkENEBULYBU;
import android.net._$$Lambda$ConnectivityManager$3$Hh_etCA_vVs2IV58umWLOd1O4yk;
import android.net._$$Lambda$ConnectivityManager$4$GbcJVaUJX_pIrYQi94EYHYBwTJI;
import android.net._$$Lambda$ConnectivityManager$4$Jk_u9vR1DwqMOUorHyaTIOdhOAs;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$JWcQQZv8Qrs81cZ_BMAOZZ8MUeU;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$NfMgP6Nh6Ep6LcaiJ10o_zBccII;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$WmmtbYWlzqL_V8wWUDKe3CWjvy0;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$_H5tzn67t3ydWL8tXpl9UyOmDcc;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$iOtsqOYp69ztB6u3PYNu_iI_PGo;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$1$nt5Pgsn85fhX6h9EJ0eAK_PXAjU;
import android.net._$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.INetworkActivityListener;
import android.os.INetworkManagementService;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ServiceSpecificException;
import android.provider.Settings;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.Log;
import android.util.SparseIntArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.Preconditions;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import libcore.net.event.NetworkEventDispatcher;

public class ConnectivityManager {
    @Deprecated
    public static final String ACTION_BACKGROUND_DATA_SETTING_CHANGED = "android.net.conn.BACKGROUND_DATA_SETTING_CHANGED";
    public static final String ACTION_CAPTIVE_PORTAL_SIGN_IN = "android.net.conn.CAPTIVE_PORTAL";
    public static final String ACTION_CAPTIVE_PORTAL_TEST_COMPLETED = "android.net.conn.CAPTIVE_PORTAL_TEST_COMPLETED";
    public static final String ACTION_DATA_ACTIVITY_CHANGE = "android.net.conn.DATA_ACTIVITY_CHANGE";
    public static final String ACTION_PROMPT_LOST_VALIDATION = "android.net.conn.PROMPT_LOST_VALIDATION";
    public static final String ACTION_PROMPT_PARTIAL_CONNECTIVITY = "android.net.conn.PROMPT_PARTIAL_CONNECTIVITY";
    public static final String ACTION_PROMPT_UNVALIDATED = "android.net.conn.PROMPT_UNVALIDATED";
    public static final String ACTION_RESTRICT_BACKGROUND_CHANGED = "android.net.conn.RESTRICT_BACKGROUND_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_TETHER_STATE_CHANGED = "android.net.conn.TETHER_STATE_CHANGED";
    private static final NetworkRequest ALREADY_UNREGISTERED;
    private static final int BASE = 524288;
    public static final int CALLBACK_AVAILABLE = 524290;
    public static final int CALLBACK_BLK_CHANGED = 524299;
    public static final int CALLBACK_CAP_CHANGED = 524294;
    public static final int CALLBACK_IP_CHANGED = 524295;
    public static final int CALLBACK_LOSING = 524291;
    public static final int CALLBACK_LOST = 524292;
    public static final int CALLBACK_PRECHECK = 524289;
    public static final int CALLBACK_RESUMED = 524298;
    public static final int CALLBACK_SUSPENDED = 524297;
    public static final int CALLBACK_UNAVAIL = 524293;
    @Deprecated
    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String CONNECTIVITY_ACTION_SUPL = "android.net.conn.CONNECTIVITY_CHANGE_SUPL";
    private static final boolean DEBUG;
    @Deprecated
    public static final int DEFAULT_NETWORK_PREFERENCE = 1;
    private static final int EXPIRE_LEGACY_REQUEST = 524296;
    public static final String EXTRA_ACTIVE_LOCAL_ONLY = "localOnlyArray";
    @UnsupportedAppUsage
    public static final String EXTRA_ACTIVE_TETHER = "tetherArray";
    public static final String EXTRA_ADD_TETHER_TYPE = "extraAddTetherType";
    @UnsupportedAppUsage
    public static final String EXTRA_AVAILABLE_TETHER = "availableArray";
    public static final String EXTRA_CAPTIVE_PORTAL = "android.net.extra.CAPTIVE_PORTAL";
    @SystemApi
    public static final String EXTRA_CAPTIVE_PORTAL_PROBE_SPEC = "android.net.extra.CAPTIVE_PORTAL_PROBE_SPEC";
    public static final String EXTRA_CAPTIVE_PORTAL_URL = "android.net.extra.CAPTIVE_PORTAL_URL";
    @SystemApi
    public static final String EXTRA_CAPTIVE_PORTAL_USER_AGENT = "android.net.extra.CAPTIVE_PORTAL_USER_AGENT";
    public static final String EXTRA_DEVICE_TYPE = "deviceType";
    @UnsupportedAppUsage
    public static final String EXTRA_ERRORED_TETHER = "erroredArray";
    @Deprecated
    public static final String EXTRA_EXTRA_INFO = "extraInfo";
    public static final String EXTRA_INET_CONDITION = "inetCondition";
    public static final String EXTRA_IS_ACTIVE = "isActive";
    public static final String EXTRA_IS_CAPTIVE_PORTAL = "captivePortal";
    @Deprecated
    public static final String EXTRA_IS_FAILOVER = "isFailover";
    public static final String EXTRA_NETWORK = "android.net.extra.NETWORK";
    @Deprecated
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_NETWORK_REQUEST = "android.net.extra.NETWORK_REQUEST";
    @Deprecated
    public static final String EXTRA_NETWORK_TYPE = "networkType";
    public static final String EXTRA_NO_CONNECTIVITY = "noConnectivity";
    @Deprecated
    public static final String EXTRA_OTHER_NETWORK_INFO = "otherNetwork";
    public static final String EXTRA_PROVISION_CALLBACK = "extraProvisionCallback";
    public static final String EXTRA_REALTIME_NS = "tsNanos";
    public static final String EXTRA_REASON = "reason";
    public static final String EXTRA_REM_TETHER_TYPE = "extraRemTetherType";
    public static final String EXTRA_RUN_PROVISION = "extraRunProvision";
    public static final String EXTRA_SET_ALARM = "extraSetAlarm";
    @UnsupportedAppUsage
    public static final String INET_CONDITION_ACTION = "android.net.conn.INET_CONDITION_ACTION";
    private static final int LISTEN = 1;
    public static final int MAX_NETWORK_TYPE = 18;
    public static final int MAX_RADIO_TYPE = 18;
    private static final int MIN_NETWORK_TYPE = 0;
    public static final int MULTIPATH_PREFERENCE_HANDOVER = 1;
    public static final int MULTIPATH_PREFERENCE_PERFORMANCE = 4;
    public static final int MULTIPATH_PREFERENCE_RELIABILITY = 2;
    public static final int MULTIPATH_PREFERENCE_UNMETERED = 7;
    public static final int NETID_UNSET = 0;
    public static final String PRIVATE_DNS_DEFAULT_MODE_FALLBACK = "opportunistic";
    public static final String PRIVATE_DNS_MODE_OFF = "off";
    public static final String PRIVATE_DNS_MODE_OPPORTUNISTIC = "opportunistic";
    public static final String PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = "hostname";
    private static final int REQUEST = 2;
    public static final int REQUEST_ID_UNSET = 0;
    public static final int RESTRICT_BACKGROUND_STATUS_DISABLED = 1;
    public static final int RESTRICT_BACKGROUND_STATUS_ENABLED = 3;
    public static final int RESTRICT_BACKGROUND_STATUS_WHITELISTED = 2;
    private static final String TAG = "ConnectivityManager";
    @SystemApi
    public static final int TETHERING_BLUETOOTH = 2;
    public static final int TETHERING_INVALID = -1;
    @SystemApi
    public static final int TETHERING_USB = 1;
    @SystemApi
    public static final int TETHERING_WIFI = 0;
    public static final int TETHER_ERROR_DHCPSERVER_ERROR = 12;
    public static final int TETHER_ERROR_DISABLE_NAT_ERROR = 9;
    public static final int TETHER_ERROR_ENABLE_NAT_ERROR = 8;
    @SystemApi
    public static final int TETHER_ERROR_ENTITLEMENT_UNKONWN = 13;
    public static final int TETHER_ERROR_IFACE_CFG_ERROR = 10;
    public static final int TETHER_ERROR_MASTER_ERROR = 5;
    @SystemApi
    public static final int TETHER_ERROR_NO_ERROR = 0;
    @SystemApi
    public static final int TETHER_ERROR_PROVISION_FAILED = 11;
    public static final int TETHER_ERROR_SERVICE_UNAVAIL = 2;
    public static final int TETHER_ERROR_TETHER_IFACE_ERROR = 6;
    public static final int TETHER_ERROR_UNAVAIL_IFACE = 4;
    public static final int TETHER_ERROR_UNKNOWN_IFACE = 1;
    public static final int TETHER_ERROR_UNSUPPORTED = 3;
    public static final int TETHER_ERROR_UNTETHER_IFACE_ERROR = 7;
    @Deprecated
    public static final int TYPE_BLUETOOTH = 7;
    @Deprecated
    public static final int TYPE_DUMMY = 8;
    @Deprecated
    public static final int TYPE_ETHERNET = 9;
    @Deprecated
    public static final int TYPE_MOBILE = 0;
    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public static final int TYPE_MOBILE_CBS = 12;
    @Deprecated
    public static final int TYPE_MOBILE_DUN = 4;
    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public static final int TYPE_MOBILE_EMERGENCY = 15;
    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public static final int TYPE_MOBILE_FOTA = 10;
    @Deprecated
    public static final int TYPE_MOBILE_HIPRI = 5;
    @Deprecated
    @UnsupportedAppUsage
    public static final int TYPE_MOBILE_IA = 14;
    @Deprecated
    @UnsupportedAppUsage
    public static final int TYPE_MOBILE_IMS = 11;
    @Deprecated
    public static final int TYPE_MOBILE_MMS = 2;
    @Deprecated
    public static final int TYPE_MOBILE_SUPL = 3;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public static final int TYPE_NONE = -1;
    @Deprecated
    @UnsupportedAppUsage
    public static final int TYPE_PROXY = 16;
    @Deprecated
    public static final int TYPE_TEST = 18;
    @Deprecated
    public static final int TYPE_VPN = 17;
    @Deprecated
    public static final int TYPE_WIFI = 1;
    @Deprecated
    @UnsupportedAppUsage
    public static final int TYPE_WIFI_P2P = 13;
    @Deprecated
    public static final int TYPE_WIMAX = 6;
    private static CallbackHandler sCallbackHandler;
    private static final HashMap<NetworkRequest, NetworkCallback> sCallbacks;
    private static ConnectivityManager sInstance;
    @UnsupportedAppUsage
    private static final HashMap<NetworkCapabilities, LegacyRequest> sLegacyRequests;
    private static final SparseIntArray sLegacyTypeToCapability;
    private static final SparseIntArray sLegacyTypeToTransport;
    private final Context mContext;
    private INetworkManagementService mNMService;
    private INetworkPolicyManager mNPManager;
    private final ArrayMap<OnNetworkActiveListener, INetworkActivityListener> mNetworkActivityListeners = new ArrayMap();
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    private final IConnectivityManager mService;
    @GuardedBy(value={"mTetheringEventCallbacks"})
    private final ArrayMap<OnTetheringEventCallback, ITetheringEventCallback> mTetheringEventCallbacks = new ArrayMap();

    static {
        DEBUG = Log.isLoggable(TAG, 3);
        ALREADY_UNREGISTERED = new NetworkRequest.Builder().clearCapabilities().build();
        sLegacyRequests = new HashMap();
        sLegacyTypeToTransport = new SparseIntArray();
        sLegacyTypeToTransport.put(0, 0);
        sLegacyTypeToTransport.put(12, 0);
        sLegacyTypeToTransport.put(4, 0);
        sLegacyTypeToTransport.put(10, 0);
        sLegacyTypeToTransport.put(5, 0);
        sLegacyTypeToTransport.put(11, 0);
        sLegacyTypeToTransport.put(2, 0);
        sLegacyTypeToTransport.put(3, 0);
        sLegacyTypeToTransport.put(1, 1);
        sLegacyTypeToTransport.put(13, 1);
        sLegacyTypeToTransport.put(7, 2);
        sLegacyTypeToTransport.put(9, 3);
        sLegacyTypeToCapability = new SparseIntArray();
        sLegacyTypeToCapability.put(12, 5);
        sLegacyTypeToCapability.put(4, 2);
        sLegacyTypeToCapability.put(10, 3);
        sLegacyTypeToCapability.put(11, 4);
        sLegacyTypeToCapability.put(2, 0);
        sLegacyTypeToCapability.put(3, 1);
        sLegacyTypeToCapability.put(13, 6);
        sCallbacks = new HashMap();
    }

    public ConnectivityManager(Context context, IConnectivityManager iConnectivityManager) {
        this.mContext = Preconditions.checkNotNull(context, "missing context");
        this.mService = Preconditions.checkNotNull(iConnectivityManager, "missing IConnectivityManager");
        sInstance = this;
    }

    private static void checkCallbackNotNull(NetworkCallback networkCallback) {
        Preconditions.checkNotNull(networkCallback, "null NetworkCallback");
    }

    private void checkLegacyRoutingApiAccess() {
        this.unsupportedStartingFrom(23);
    }

    private static void checkPendingIntentNotNull(PendingIntent pendingIntent) {
        Preconditions.checkNotNull(pendingIntent, "PendingIntent cannot be null.");
    }

    private static void checkTimeout(int n) {
        Preconditions.checkArgumentPositive(n, "timeoutMs must be strictly positive.");
    }

    private static RuntimeException convertServiceException(ServiceSpecificException serviceSpecificException) {
        if (serviceSpecificException.errorCode != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown service error code ");
            stringBuilder.append(serviceSpecificException.errorCode);
            Log.w(TAG, stringBuilder.toString());
            return new RuntimeException(serviceSpecificException);
        }
        return new TooManyRequestsException();
    }

    public static final void enforceChangePermission(Context context) {
        int n = Binder.getCallingUid();
        Settings.checkAndNoteChangeNetworkStateOperation(context, n, Settings.getPackageNameForUid(context, n), true);
    }

    public static final void enforceTetherChangePermission(Context context, String string2) {
        Preconditions.checkNotNull(context, "Context cannot be null");
        Preconditions.checkNotNull(string2, "callingPkg cannot be null");
        if (context.getResources().getStringArray(17236038).length == 2) {
            context.enforceCallingOrSelfPermission("android.permission.TETHER_PRIVILEGED", "ConnectivityService");
        } else {
            Settings.checkAndNoteWriteSettingsOperation(context, Binder.getCallingUid(), string2, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void expireRequest(NetworkCapabilities object, int n) {
        int n2;
        HashMap<NetworkCapabilities, LegacyRequest> hashMap = sLegacyRequests;
        synchronized (hashMap) {
            LegacyRequest legacyRequest = sLegacyRequests.get(object);
            if (legacyRequest == null) {
                return;
            }
            n2 = legacyRequest.expireSequenceNumber;
            if (legacyRequest.expireSequenceNumber == n) {
                this.removeRequestForFeature((NetworkCapabilities)object);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("expireRequest with ");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(n);
        Log.d(TAG, ((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private NetworkRequest findRequestForFeature(NetworkCapabilities object) {
        HashMap<NetworkCapabilities, LegacyRequest> hashMap = sLegacyRequests;
        synchronized (hashMap) {
            object = sLegacyRequests.get(object);
            if (object == null) return null;
            return ((LegacyRequest)object).networkRequest;
        }
    }

    @UnsupportedAppUsage
    public static ConnectivityManager from(Context context) {
        return (ConnectivityManager)context.getSystemService("connectivity");
    }

    public static String getCallbackName(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 524299: {
                return "CALLBACK_BLK_CHANGED";
            }
            case 524298: {
                return "CALLBACK_RESUMED";
            }
            case 524297: {
                return "CALLBACK_SUSPENDED";
            }
            case 524296: {
                return "EXPIRE_LEGACY_REQUEST";
            }
            case 524295: {
                return "CALLBACK_IP_CHANGED";
            }
            case 524294: {
                return "CALLBACK_CAP_CHANGED";
            }
            case 524293: {
                return "CALLBACK_UNAVAIL";
            }
            case 524292: {
                return "CALLBACK_LOST";
            }
            case 524291: {
                return "CALLBACK_LOSING";
            }
            case 524290: {
                return "CALLBACK_AVAILABLE";
            }
            case 524289: 
        }
        return "CALLBACK_PRECHECK";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private CallbackHandler getDefaultHandler() {
        HashMap<NetworkRequest, NetworkCallback> hashMap = sCallbacks;
        synchronized (hashMap) {
            CallbackHandler callbackHandler;
            if (sCallbackHandler != null) return sCallbackHandler;
            sCallbackHandler = callbackHandler = new CallbackHandler(ConnectivityThread.getInstanceLooper());
            return sCallbackHandler;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    private static ConnectivityManager getInstance() {
        if (ConnectivityManager.getInstanceOrNull() != null) {
            return ConnectivityManager.getInstanceOrNull();
        }
        throw new IllegalStateException("No ConnectivityManager yet constructed");
    }

    @Deprecated
    static ConnectivityManager getInstanceOrNull() {
        return sInstance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private INetworkManagementService getNetworkManagementService() {
        synchronized (this) {
            this.mNMService = INetworkManagementService.Stub.asInterface(ServiceManager.getService("network_management"));
            if (this.mNMService == null) return this.mNMService;
            return this.mNMService;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private INetworkPolicyManager getNetworkPolicyManager() {
        synchronized (this) {
            this.mNPManager = INetworkPolicyManager.Stub.asInterface(ServiceManager.getService("netpolicy"));
            if (this.mNPManager == null) return this.mNPManager;
            return this.mNPManager;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static String getNetworkTypeName(int n) {
        switch (n) {
            default: {
                return Integer.toString(n);
            }
            case 17: {
                return "VPN";
            }
            case 16: {
                return "PROXY";
            }
            case 15: {
                return "MOBILE_EMERGENCY";
            }
            case 14: {
                return "MOBILE_IA";
            }
            case 13: {
                return "WIFI_P2P";
            }
            case 12: {
                return "MOBILE_CBS";
            }
            case 11: {
                return "MOBILE_IMS";
            }
            case 10: {
                return "MOBILE_FOTA";
            }
            case 9: {
                return "ETHERNET";
            }
            case 8: {
                return "DUMMY";
            }
            case 7: {
                return "BLUETOOTH";
            }
            case 6: {
                return "WIMAX";
            }
            case 5: {
                return "MOBILE_HIPRI";
            }
            case 4: {
                return "MOBILE_DUN";
            }
            case 3: {
                return "MOBILE_SUPL";
            }
            case 2: {
                return "MOBILE_MMS";
            }
            case 1: {
                return "WIFI";
            }
            case 0: {
                return "MOBILE";
            }
            case -1: 
        }
        return "NONE";
    }

    @Deprecated
    public static Network getProcessDefaultNetwork() {
        int n = NetworkUtils.getBoundNetworkForProcess();
        if (n == 0) {
            return null;
        }
        return new Network(n);
    }

    private int inferLegacyTypeForNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return -1;
        }
        if (!networkCapabilities.hasTransport(0)) {
            return -1;
        }
        if (!networkCapabilities.hasCapability(1)) {
            return -1;
        }
        Object object = null;
        int n = -1;
        if (networkCapabilities.hasCapability(5)) {
            object = "enableCBS";
            n = 12;
        } else if (networkCapabilities.hasCapability(4)) {
            object = "enableIMS";
            n = 11;
        } else if (networkCapabilities.hasCapability(3)) {
            object = "enableFOTA";
            n = 10;
        } else if (networkCapabilities.hasCapability(2)) {
            object = "enableDUN";
            n = 4;
        } else if (networkCapabilities.hasCapability(1)) {
            object = "enableSUPL";
            n = 3;
        } else if (networkCapabilities.hasCapability(12)) {
            object = "enableHIPRI";
            n = 5;
        }
        if (object != null && ((NetworkCapabilities)(object = this.networkCapabilitiesForFeature(0, (String)object))).equalsNetCapabilities(networkCapabilities) && ((NetworkCapabilities)object).equalsTransportTypes(networkCapabilities)) {
            return n;
        }
        return -1;
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public static boolean isNetworkTypeMobile(int n) {
        if (n != 0 && n != 2 && n != 3 && n != 4 && n != 5 && n != 14 && n != 15) {
            switch (n) {
                default: {
                    return false;
                }
                case 10: 
                case 11: 
                case 12: 
            }
        }
        return true;
    }

    @Deprecated
    public static boolean isNetworkTypeValid(int n) {
        boolean bl = n >= 0 && n <= 18;
        return bl;
    }

    @Deprecated
    public static boolean isNetworkTypeWifi(int n) {
        return n == 1 || n == 13;
    }

    private int legacyTypeForNetworkCapabilities(NetworkCapabilities networkCapabilities) {
        if (networkCapabilities == null) {
            return -1;
        }
        if (networkCapabilities.hasCapability(5)) {
            return 12;
        }
        if (networkCapabilities.hasCapability(4)) {
            return 11;
        }
        if (networkCapabilities.hasCapability(3)) {
            return 10;
        }
        if (networkCapabilities.hasCapability(2)) {
            return 4;
        }
        if (networkCapabilities.hasCapability(1)) {
            return 3;
        }
        if (networkCapabilities.hasCapability(0)) {
            return 2;
        }
        if (networkCapabilities.hasCapability(12)) {
            return 5;
        }
        if (networkCapabilities.hasCapability(6)) {
            return 13;
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private NetworkCapabilities networkCapabilitiesForFeature(int var1_1, String var2_2) {
        block20 : {
            var3_3 = 1;
            if (var1_1 != 0) {
                if (var1_1 != 1) return null;
                if ("p2p".equals(var2_2) == false) return null;
                return ConnectivityManager.networkCapabilitiesForType(13);
            }
            switch (var2_2.hashCode()) {
                case 1998933033: {
                    if (!var2_2.equals("enableDUNAlways")) break;
                    var1_1 = 2;
                    break block20;
                }
                case 1893183457: {
                    if (!var2_2.equals("enableSUPL")) break;
                    var1_1 = 7;
                    break block20;
                }
                case 1892790521: {
                    if (!var2_2.equals("enableFOTA")) break;
                    var1_1 = 3;
                    break block20;
                }
                case -631672240: {
                    if (!var2_2.equals("enableMMS")) break;
                    var1_1 = 6;
                    break block20;
                }
                case -631676084: {
                    if (!var2_2.equals("enableIMS")) break;
                    var1_1 = 5;
                    break block20;
                }
                case -631680646: {
                    if (!var2_2.equals("enableDUN")) break;
                    var1_1 = var3_3;
                    break block20;
                }
                case -631682191: {
                    if (!var2_2.equals("enableCBS")) break;
                    var1_1 = 0;
                    break block20;
                }
                case -1451370941: {
                    if (!var2_2.equals("enableHIPRI")) break;
                    var1_1 = 4;
                    break block20;
                }
            }
            ** break;
lbl40: // 1 sources:
            var1_1 = -1;
        }
        switch (var1_1) {
            default: {
                return null;
            }
            case 7: {
                return ConnectivityManager.networkCapabilitiesForType(3);
            }
            case 6: {
                return ConnectivityManager.networkCapabilitiesForType(2);
            }
            case 5: {
                return ConnectivityManager.networkCapabilitiesForType(11);
            }
            case 4: {
                return ConnectivityManager.networkCapabilitiesForType(5);
            }
            case 3: {
                return ConnectivityManager.networkCapabilitiesForType(10);
            }
            case 1: 
            case 2: {
                return ConnectivityManager.networkCapabilitiesForType(4);
            }
            case 0: 
        }
        return ConnectivityManager.networkCapabilitiesForType(12);
    }

    public static NetworkCapabilities networkCapabilitiesForType(int n) {
        NetworkCapabilities networkCapabilities = new NetworkCapabilities();
        int n2 = sLegacyTypeToTransport.get(n, -1);
        boolean bl = n2 != -1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unknown legacy type: ");
        stringBuilder.append(n);
        Preconditions.checkArgument(bl, stringBuilder.toString());
        networkCapabilities.addTransportType(n2);
        networkCapabilities.addCapability(sLegacyTypeToCapability.get(n, 12));
        networkCapabilities.maybeMarkCapabilitiesRestricted();
        return networkCapabilities;
    }

    private void printStackTrace() {
        if (DEBUG) {
            String string2;
            Object object = Thread.currentThread().getStackTrace();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 3; i < ((StackTraceElement[])object).length && (string2 = object[i].toString()) != null && !string2.contains("android.os"); ++i) {
                stringBuffer.append(" [");
                stringBuffer.append(string2);
                stringBuffer.append("]");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("StackLog:");
            ((StringBuilder)object).append(stringBuffer.toString());
            Log.d(TAG, ((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private boolean removeRequestForFeature(NetworkCapabilities object) {
        HashMap<NetworkCapabilities, LegacyRequest> hashMap = sLegacyRequests;
        // MONITORENTER : hashMap
        object = sLegacyRequests.remove(object);
        // MONITOREXIT : hashMap
        if (object == null) {
            return false;
        }
        this.unregisterNetworkCallback(((LegacyRequest)object).networkCallback);
        ((LegacyRequest)object).clearDnsBinding();
        return true;
    }

    private void renewRequestLocked(LegacyRequest legacyRequest) {
        ++legacyRequest.expireSequenceNumber;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("renewing request to seqNum ");
        stringBuilder.append(legacyRequest.expireSequenceNumber);
        Log.d(TAG, stringBuilder.toString());
        this.sendExpireMsgForFeature(legacyRequest.networkCapabilities, legacyRequest.expireSequenceNumber, legacyRequest.delay);
    }

    @UnsupportedAppUsage
    private NetworkRequest requestNetworkForFeatureLocked(NetworkCapabilities networkCapabilities) {
        LegacyRequest legacyRequest;
        int n;
        int n2 = this.legacyTypeForNetworkCapabilities(networkCapabilities);
        try {
            n = this.mService.getRestoreDefaultNetworkDelay(n2);
            legacyRequest = new LegacyRequest();
            legacyRequest.networkCapabilities = networkCapabilities;
            legacyRequest.delay = n;
            legacyRequest.expireSequenceNumber = 0;
            legacyRequest.networkRequest = this.sendRequestForNetwork(networkCapabilities, legacyRequest.networkCallback, 0, 2, n2, this.getDefaultHandler());
            if (legacyRequest.networkRequest == null) {
                return null;
            }
            sLegacyRequests.put(networkCapabilities, legacyRequest);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        this.sendExpireMsgForFeature(networkCapabilities, legacyRequest.expireSequenceNumber, n);
        return legacyRequest.networkRequest;
    }

    private void sendExpireMsgForFeature(NetworkCapabilities networkCapabilities, int n, int n2) {
        if (n2 >= 0) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("sending expire msg with seqNum ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" and delay ");
            ((StringBuilder)object).append(n2);
            Log.d(TAG, ((StringBuilder)object).toString());
            object = this.getDefaultHandler();
            ((Handler)object).sendMessageDelayed(((Handler)object).obtainMessage(524296, n, 0, networkCapabilities), n2);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private NetworkRequest sendRequestForNetwork(NetworkCapabilities parcelable, NetworkCallback networkCallback, int n, int n2, int n3, CallbackHandler object) {
        void var4_11;
        void var1_8;
        void var2_9;
        void var1_6;
        this.printStackTrace();
        ConnectivityManager.checkCallbackNotNull((NetworkCallback)var2_9);
        boolean bl = var4_11 == 2 || parcelable != null;
        Preconditions.checkArgument(bl, "null NetworkCapabilities");
        HashMap<NetworkRequest, NetworkCallback> hashMap = sCallbacks;
        // MONITORENTER : hashMap
        if (((NetworkCallback)var2_9).networkRequest != null && ((NetworkCallback)var2_9).networkRequest != ALREADY_UNREGISTERED) {
            Log.e(TAG, "NetworkCallback was already registered");
        }
        try {
            void var5_12;
            void var3_10;
            Binder binder;
            Messenger messenger = new Messenger((Handler)((Object)binder));
            binder = new Binder();
            parcelable = var4_11 == true ? this.mService.listenForNetwork((NetworkCapabilities)parcelable, messenger, binder) : this.mService.requestNetwork((NetworkCapabilities)parcelable, messenger, (int)var3_10, binder, (int)var5_12);
            if (parcelable != null) {
                sCallbacks.put((NetworkRequest)parcelable, (NetworkCallback)var2_9);
            }
            ((NetworkCallback)var2_9).networkRequest = (NetworkRequest)parcelable;
            // MONITOREXIT : hashMap
            return parcelable;
        }
        catch (Throwable throwable) {}
        try {
            throw throwable;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw ConnectivityManager.convertServiceException((ServiceSpecificException)var1_6);
        }
        catch (RemoteException remoteException) {
            throw var1_8.rethrowFromSystemServer();
        }
        catch (ServiceSpecificException serviceSpecificException) {
            // empty catch block
        }
        throw ConnectivityManager.convertServiceException((ServiceSpecificException)var1_6);
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_8.rethrowFromSystemServer();
    }

    @Deprecated
    public static boolean setProcessDefaultNetwork(Network network) {
        int n = network == null ? 0 : network.netId;
        boolean bl = n == NetworkUtils.getBoundNetworkForProcess();
        int n2 = n;
        if (n != 0) {
            n2 = network.getNetIdForResolv();
        }
        if (!NetworkUtils.bindProcessToNetwork(n2)) {
            return false;
        }
        if (!bl) {
            try {
                Proxy.setHttpProxySystemProperty(ConnectivityManager.getInstance().getDefaultProxy());
            }
            catch (SecurityException securityException) {
                Log.e(TAG, "Can't set proxy properties", securityException);
            }
            InetAddress.clearDnsCache();
            NetworkEventDispatcher.getInstance().onNetworkConfigurationChanged();
        }
        return true;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean setProcessDefaultNetworkForHostResolution(Network network) {
        int n = network == null ? 0 : network.getNetIdForResolv();
        return NetworkUtils.bindProcessToNetworkForHostResolution(n);
    }

    private void unsupportedStartingFrom(int n) {
        if (Process.myUid() == 1000) {
            return;
        }
        if (this.mContext.getApplicationInfo().targetSdkVersion < n) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("This method is not supported in target SDK version ");
        stringBuilder.append(n);
        stringBuilder.append(" and above");
        throw new UnsupportedOperationException(stringBuilder.toString());
    }

    public void addDefaultNetworkActiveListener(final OnNetworkActiveListener onNetworkActiveListener) {
        INetworkActivityListener.Stub stub = new INetworkActivityListener.Stub(){

            @Override
            public void onNetworkActive() throws RemoteException {
                onNetworkActiveListener.onNetworkActive();
            }
        };
        try {
            this.getNetworkManagementService().registerNetworkActivityListener(stub);
            this.mNetworkActivityListeners.put(onNetworkActiveListener, stub);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean bindProcessToNetwork(Network network) {
        return ConnectivityManager.setProcessDefaultNetwork(network);
    }

    public int checkMobileProvisioning(int n) {
        try {
            n = this.mService.checkMobileProvisioning(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public SocketKeepalive createNattKeepalive(Network network, ParcelFileDescriptor parcelFileDescriptor, InetAddress inetAddress, InetAddress inetAddress2, Executor executor, SocketKeepalive.Callback callback) {
        try {
            parcelFileDescriptor = parcelFileDescriptor.dup();
        }
        catch (IOException iOException) {
            parcelFileDescriptor = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new NattSocketKeepalive(this.mService, network, parcelFileDescriptor, -1, inetAddress, inetAddress2, executor, callback);
    }

    public SocketKeepalive createSocketKeepalive(Network network, IpSecManager.UdpEncapsulationSocket udpEncapsulationSocket, InetAddress inetAddress, InetAddress inetAddress2, Executor executor, SocketKeepalive.Callback callback) {
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = ParcelFileDescriptor.dup(udpEncapsulationSocket.getFileDescriptor());
        }
        catch (IOException iOException) {
            parcelFileDescriptor = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new NattSocketKeepalive(this.mService, network, parcelFileDescriptor, udpEncapsulationSocket.getResourceId(), inetAddress, inetAddress2, executor, callback);
    }

    @SystemApi
    public SocketKeepalive createSocketKeepalive(Network network, Socket closeable, Executor executor, SocketKeepalive.Callback callback) {
        try {
            closeable = ParcelFileDescriptor.fromSocket(closeable);
        }
        catch (UncheckedIOException uncheckedIOException) {
            closeable = new ParcelFileDescriptor(new FileDescriptor());
        }
        return new TcpSocketKeepalive(this.mService, network, (ParcelFileDescriptor)closeable, executor, callback);
    }

    public void factoryReset() {
        try {
            this.mService.factoryReset();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=109783091L)
    public LinkProperties getActiveLinkProperties() {
        try {
            LinkProperties linkProperties = this.mService.getActiveLinkProperties();
            return linkProperties;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Network getActiveNetwork() {
        try {
            Network network = this.mService.getActiveNetwork();
            return network;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Network getActiveNetworkForUid(int n) {
        return this.getActiveNetworkForUid(n, false);
    }

    public Network getActiveNetworkForUid(int n, boolean bl) {
        try {
            Network network = this.mService.getActiveNetworkForUid(n, bl);
            return network;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo getActiveNetworkInfo() {
        try {
            NetworkInfo networkInfo = this.mService.getActiveNetworkInfo();
            return networkInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public NetworkInfo getActiveNetworkInfoForUid(int n) {
        return this.getActiveNetworkInfoForUid(n, false);
    }

    public NetworkInfo getActiveNetworkInfoForUid(int n, boolean bl) {
        try {
            NetworkInfo networkInfo = this.mService.getActiveNetworkInfoForUid(n, bl);
            return networkInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public NetworkQuotaInfo getActiveNetworkQuotaInfo() {
        try {
            NetworkQuotaInfo networkQuotaInfo = this.mService.getActiveNetworkQuotaInfo();
            return networkQuotaInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo[] getAllNetworkInfo() {
        try {
            NetworkInfo[] arrnetworkInfo = this.mService.getAllNetworkInfo();
            return arrnetworkInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Network[] getAllNetworks() {
        try {
            Network[] arrnetwork = this.mService.getAllNetworks();
            return arrnetwork;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getAlwaysOnVpnPackageForUser(int n) {
        try {
            String string2 = this.mService.getAlwaysOnVpnPackage(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean getBackgroundDataSetting() {
        return true;
    }

    public Network getBoundNetworkForProcess() {
        return ConnectivityManager.getProcessDefaultNetwork();
    }

    @SystemApi
    public String getCaptivePortalServerUrl() {
        try {
            String string2 = this.mService.getCaptivePortalServerUrl();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getConnectionOwnerUid(int n, InetSocketAddress object, InetSocketAddress inetSocketAddress) {
        object = new ConnectionInfo(n, (InetSocketAddress)object, inetSocketAddress);
        try {
            n = this.mService.getConnectionOwnerUid((ConnectionInfo)object);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public NetworkCapabilities[] getDefaultNetworkCapabilitiesForUser(int n) {
        try {
            NetworkCapabilities[] arrnetworkCapabilities = this.mService.getDefaultNetworkCapabilitiesForUser(n);
            return arrnetworkCapabilities;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getDefaultProxy() {
        return this.getProxyForNetwork(this.getBoundNetworkForProcess());
    }

    public NetworkRequest getDefaultRequest() {
        try {
            NetworkRequest networkRequest = this.mService.getDefaultRequest();
            return networkRequest;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getGlobalProxy() {
        try {
            ProxyInfo proxyInfo = this.mService.getGlobalProxy();
            return proxyInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getLastTetherError(String string2) {
        try {
            int n = this.mService.getLastTetherError(string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void getLatestTetheringEntitlementResult(int n, boolean bl, Executor object, OnTetheringEntitlementResultListener object2) {
        Preconditions.checkNotNull(object2, "TetheringEntitlementResultListener cannot be null.");
        object2 = new ResultReceiver(null, (Executor)object, (OnTetheringEntitlementResultListener)object2){
            final /* synthetic */ Executor val$executor;
            final /* synthetic */ OnTetheringEntitlementResultListener val$listener;
            {
                this.val$executor = executor;
                this.val$listener = onTetheringEntitlementResultListener;
                super(handler);
            }

            static /* synthetic */ void lambda$onReceiveResult$0(OnTetheringEntitlementResultListener onTetheringEntitlementResultListener, int n) {
                onTetheringEntitlementResultListener.onTetheringEntitlementResult(n);
            }

            static /* synthetic */ void lambda$onReceiveResult$1(Executor executor, OnTetheringEntitlementResultListener onTetheringEntitlementResultListener, int n) throws Exception {
                executor.execute(new _$$Lambda$ConnectivityManager$4$GbcJVaUJX_pIrYQi94EYHYBwTJI(onTetheringEntitlementResultListener, n));
            }

            @Override
            protected void onReceiveResult(int n, Bundle bundle) {
                Binder.withCleanCallingIdentity(new _$$Lambda$ConnectivityManager$4$Jk_u9vR1DwqMOUorHyaTIOdhOAs(this.val$executor, this.val$listener, n));
            }
        };
        try {
            object = this.mContext.getOpPackageName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getLatestTetheringEntitlementResult:");
            stringBuilder.append((String)object);
            Log.i(TAG, stringBuilder.toString());
            this.mService.getLatestTetheringEntitlementResult(n, (ResultReceiver)object2, bl, (String)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public LinkProperties getLinkProperties(int n) {
        try {
            LinkProperties linkProperties = this.mService.getLinkPropertiesForType(n);
            return linkProperties;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public LinkProperties getLinkProperties(Network parcelable) {
        try {
            parcelable = this.mService.getLinkProperties((Network)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean getMobileDataEnabled() {
        Object object = ServiceManager.getService("phone");
        if (object != null) {
            try {
                object = ITelephony.Stub.asInterface((IBinder)object);
                int n = SubscriptionManager.getDefaultDataSubscriptionId();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("getMobileDataEnabled()+ subId=");
                stringBuilder.append(n);
                Log.d(TAG, stringBuilder.toString());
                boolean bl = object.isUserDataEnabled(n);
                object = new StringBuilder();
                ((StringBuilder)object).append("getMobileDataEnabled()- subId=");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" retVal=");
                ((StringBuilder)object).append(bl);
                Log.d(TAG, ((StringBuilder)object).toString());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.d(TAG, "getMobileDataEnabled()- remote exception retVal=false");
        return false;
    }

    public String getMobileProvisioningUrl() {
        try {
            String string2 = this.mService.getMobileProvisioningUrl();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getMultipathPreference(Network network) {
        try {
            int n = this.mService.getMultipathPreference(network);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public NetworkCapabilities getNetworkCapabilities(Network parcelable) {
        try {
            parcelable = this.mService.getNetworkCapabilities((Network)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public Network getNetworkForType(int n) {
        try {
            Network network = this.mService.getNetworkForType(n);
            return network;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo getNetworkInfo(int n) {
        try {
            NetworkInfo networkInfo = this.mService.getNetworkInfo(n);
            return networkInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public NetworkInfo getNetworkInfo(Network network) {
        return this.getNetworkInfoForUid(network, Process.myUid(), false);
    }

    public NetworkInfo getNetworkInfoForUid(Network parcelable, int n, boolean bl) {
        try {
            parcelable = this.mService.getNetworkInfoForUid((Network)parcelable, n, bl);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int getNetworkPreference() {
        return -1;
    }

    public byte[] getNetworkWatchlistConfigHash() {
        try {
            byte[] arrby = this.mService.getNetworkWatchlistConfigHash();
            return arrby;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to get watchlist config hash");
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ProxyInfo getProxyForNetwork(Network parcelable) {
        try {
            parcelable = this.mService.getProxyForNetwork((Network)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getRestrictBackgroundStatus() {
        try {
            int n = this.getNetworkPolicyManager().getRestrictBackgroundByCaller();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableBluetoothRegexs() {
        try {
            String[] arrstring = this.mService.getTetherableBluetoothRegexs();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableIfaces() {
        try {
            String[] arrstring = this.mService.getTetherableIfaces();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableUsbRegexs() {
        try {
            String[] arrstring = this.mService.getTetherableUsbRegexs();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetherableWifiRegexs() {
        try {
            String[] arrstring = this.mService.getTetherableWifiRegexs();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String[] getTetheredDhcpRanges() {
        try {
            String[] arrstring = this.mService.getTetheredDhcpRanges();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetheredIfaces() {
        try {
            String[] arrstring = this.mService.getTetheredIfaces();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String[] getTetheringErroredIfaces() {
        try {
            String[] arrstring = this.mService.getTetheringErroredIfaces();
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<String> getVpnLockdownWhitelist(int n) {
        try {
            List<String> list = this.mService.getVpnLockdownWhitelist(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isActiveNetworkMetered() {
        try {
            boolean bl = this.mService.isActiveNetworkMetered();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnPackageSupportedForUser(int n, String string2) {
        try {
            boolean bl = this.mService.isAlwaysOnVpnPackageSupported(n, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDefaultNetworkActive() {
        try {
            boolean bl = this.getNetworkManagementService().isNetworkActive();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=130143562L)
    public boolean isNetworkSupported(int n) {
        try {
            boolean bl = this.mService.isNetworkSupported(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isTetheringSupported() {
        String string2 = this.mContext.getOpPackageName();
        try {
            boolean bl = this.mService.isTetheringSupported(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (SecurityException securityException) {
            return false;
        }
    }

    public boolean isVpnLockdownEnabled(int n) {
        try {
            boolean bl = this.mService.isVpnLockdownEnabled(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerDefaultNetworkCallback(NetworkCallback networkCallback) {
        this.registerDefaultNetworkCallback(networkCallback, this.getDefaultHandler());
    }

    public void registerDefaultNetworkCallback(NetworkCallback networkCallback, Handler handler) {
        this.sendRequestForNetwork(null, networkCallback, 0, 2, -1, new CallbackHandler(handler));
    }

    public int registerNetworkAgent(Messenger messenger, NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n, NetworkMisc networkMisc) {
        return this.registerNetworkAgent(messenger, networkInfo, linkProperties, networkCapabilities, n, networkMisc, -1);
    }

    public int registerNetworkAgent(Messenger messenger, NetworkInfo networkInfo, LinkProperties linkProperties, NetworkCapabilities networkCapabilities, int n, NetworkMisc networkMisc, int n2) {
        try {
            n = this.mService.registerNetworkAgent(messenger, networkInfo, linkProperties, networkCapabilities, n, networkMisc, n2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerNetworkCallback(NetworkRequest networkRequest, PendingIntent pendingIntent) {
        this.printStackTrace();
        ConnectivityManager.checkPendingIntentNotNull(pendingIntent);
        try {
            this.mService.pendingListenForNetwork(networkRequest.networkCapabilities, pendingIntent);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw ConnectivityManager.convertServiceException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void registerNetworkCallback(NetworkRequest networkRequest, NetworkCallback networkCallback) {
        this.registerNetworkCallback(networkRequest, networkCallback, this.getDefaultHandler());
    }

    public void registerNetworkCallback(NetworkRequest networkRequest, NetworkCallback networkCallback, Handler handler) {
        handler = new CallbackHandler(handler);
        this.sendRequestForNetwork(networkRequest.networkCapabilities, networkCallback, 0, 1, -1, (CallbackHandler)handler);
    }

    @UnsupportedAppUsage
    public int registerNetworkFactory(Messenger messenger, String string2) {
        try {
            int n = this.mService.registerNetworkFactory(messenger, string2);
            return n;
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
    @SystemApi
    public void registerTetheringEventCallback(Executor object, OnTetheringEventCallback onTetheringEventCallback) {
        Preconditions.checkNotNull(onTetheringEventCallback, "OnTetheringEventCallback cannot be null.");
        ArrayMap<OnTetheringEventCallback, ITetheringEventCallback> arrayMap = this.mTetheringEventCallbacks;
        synchronized (arrayMap) {
            boolean bl = !this.mTetheringEventCallbacks.containsKey(onTetheringEventCallback);
            Preconditions.checkArgument(bl, "callback was already registered.");
            ITetheringEventCallback.Stub stub = new ITetheringEventCallback.Stub((Executor)object, onTetheringEventCallback){
                final /* synthetic */ OnTetheringEventCallback val$callback;
                final /* synthetic */ Executor val$executor;
                {
                    this.val$executor = executor;
                    this.val$callback = onTetheringEventCallback;
                }

                static /* synthetic */ void lambda$onUpstreamChanged$0(OnTetheringEventCallback onTetheringEventCallback, Network network) {
                    onTetheringEventCallback.onUpstreamChanged(network);
                }

                static /* synthetic */ void lambda$onUpstreamChanged$1(Executor executor, OnTetheringEventCallback onTetheringEventCallback, Network network) throws Exception {
                    executor.execute(new _$$Lambda$ConnectivityManager$3$Hh_etCA_vVs2IV58umWLOd1O4yk(onTetheringEventCallback, network));
                }

                @Override
                public void onUpstreamChanged(Network network) throws RemoteException {
                    Binder.withCleanCallingIdentity(new _$$Lambda$ConnectivityManager$3$BfAvTRJTF0an2PdeqkENEBULYBU(this.val$executor, this.val$callback, network));
                }
            };
            try {
                String string2 = this.mContext.getOpPackageName();
                object = new StringBuilder();
                ((StringBuilder)object).append("registerTetheringUpstreamCallback:");
                ((StringBuilder)object).append(string2);
                Log.i(TAG, ((StringBuilder)object).toString());
                this.mService.registerTetheringEventCallback(stub, string2);
                this.mTetheringEventCallbacks.put(onTetheringEventCallback, stub);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void releaseNetworkRequest(PendingIntent pendingIntent) {
        this.printStackTrace();
        ConnectivityManager.checkPendingIntentNotNull(pendingIntent);
        try {
            this.mService.releasePendingNetworkRequest(pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeDefaultNetworkActiveListener(OnNetworkActiveListener object) {
        boolean bl = (object = this.mNetworkActivityListeners.get(object)) != null;
        Preconditions.checkArgument(bl, "Listener was not registered.");
        try {
            this.getNetworkManagementService().unregisterNetworkActivityListener((INetworkActivityListener)object);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void reportBadNetwork(Network network) {
        this.printStackTrace();
        try {
            this.mService.reportNetworkConnectivity(network, true);
            this.mService.reportNetworkConnectivity(network, false);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportInetCondition(int n, int n2) {
        this.printStackTrace();
        try {
            this.mService.reportInetCondition(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportNetworkConnectivity(Network network, boolean bl) {
        this.printStackTrace();
        try {
            this.mService.reportNetworkConnectivity(network, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean requestBandwidthUpdate(Network network) {
        try {
            boolean bl = this.mService.requestBandwidthUpdate(network);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestNetwork(NetworkRequest networkRequest, PendingIntent pendingIntent) {
        this.printStackTrace();
        ConnectivityManager.checkPendingIntentNotNull(pendingIntent);
        try {
            this.mService.pendingRequestForNetwork(networkRequest.networkCapabilities, pendingIntent);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw ConnectivityManager.convertServiceException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestNetwork(NetworkRequest networkRequest, NetworkCallback networkCallback) {
        this.requestNetwork(networkRequest, networkCallback, this.getDefaultHandler());
    }

    public void requestNetwork(NetworkRequest networkRequest, NetworkCallback networkCallback, int n) {
        ConnectivityManager.checkTimeout(n);
        this.requestNetwork(networkRequest, networkCallback, n, this.inferLegacyTypeForNetworkCapabilities(networkRequest.networkCapabilities), this.getDefaultHandler());
    }

    public void requestNetwork(NetworkRequest networkRequest, NetworkCallback networkCallback, int n, int n2, Handler handler) {
        handler = new CallbackHandler(handler);
        this.sendRequestForNetwork(networkRequest.networkCapabilities, networkCallback, n, 2, n2, (CallbackHandler)handler);
    }

    public void requestNetwork(NetworkRequest networkRequest, NetworkCallback networkCallback, Handler handler) {
        this.requestNetwork(networkRequest, networkCallback, 0, this.inferLegacyTypeForNetworkCapabilities(networkRequest.networkCapabilities), new CallbackHandler(handler));
    }

    public void requestNetwork(NetworkRequest networkRequest, NetworkCallback networkCallback, Handler handler, int n) {
        ConnectivityManager.checkTimeout(n);
        this.requestNetwork(networkRequest, networkCallback, n, this.inferLegacyTypeForNetworkCapabilities(networkRequest.networkCapabilities), new CallbackHandler(handler));
    }

    @Deprecated
    public boolean requestRouteToHost(int n, int n2) {
        return this.requestRouteToHostAddress(n, NetworkUtils.intToInetAddress(n2));
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean requestRouteToHostAddress(int n, InetAddress inetAddress) {
        this.checkLegacyRoutingApiAccess();
        try {
            boolean bl = this.mService.requestRouteToHostAddress(n, inetAddress.getAddress());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAcceptPartialConnectivity(Network network, boolean bl, boolean bl2) {
        try {
            this.mService.setAcceptPartialConnectivity(network, bl, bl2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAcceptUnvalidated(Network network, boolean bl, boolean bl2) {
        try {
            this.mService.setAcceptUnvalidated(network, bl, bl2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setAirplaneMode(boolean bl) {
        try {
            this.mService.setAirplaneMode(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setAlwaysOnVpnPackageForUser(int n, String string2, boolean bl, List<String> list) {
        try {
            bl = this.mService.setAlwaysOnVpnPackage(n, string2, bl, list);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setAvoidUnvalidated(Network network) {
        try {
            this.mService.setAvoidUnvalidated(network);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setBackgroundDataSetting(boolean bl) {
    }

    public void setGlobalProxy(ProxyInfo proxyInfo) {
        try {
            this.mService.setGlobalProxy(proxyInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setNetworkPreference(int n) {
    }

    @Deprecated
    public void setProvisioningNotificationVisible(boolean bl, int n, String string2) {
        try {
            this.mService.setProvisioningNotificationVisible(bl, n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int setUsbTethering(boolean bl) {
        try {
            String string2 = this.mContext.getOpPackageName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setUsbTethering caller:");
            stringBuilder.append(string2);
            Log.i(TAG, stringBuilder.toString());
            int n = this.mService.setUsbTethering(bl, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean shouldAvoidBadWifi() {
        try {
            boolean bl = this.mService.shouldAvoidBadWifi();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startCaptivePortalApp(Network network) {
        try {
            this.mService.startCaptivePortalApp(network);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startCaptivePortalApp(Network network, Bundle bundle) {
        try {
            this.mService.startCaptivePortalAppInternal(network, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public PacketKeepalive startNattKeepalive(Network network, int n, PacketKeepaliveCallback object, InetAddress inetAddress, int n2, InetAddress inetAddress2) {
        object = new PacketKeepalive(network, (PacketKeepaliveCallback)object);
        try {
            this.mService.startNattKeepalive(network, n, ((PacketKeepalive)object).mCallback, inetAddress.getHostAddress(), n2, inetAddress2.getHostAddress());
            return object;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error starting packet keepalive: ", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void startTethering(int n, boolean bl, OnStartTetheringCallback onStartTetheringCallback) {
        this.startTethering(n, bl, onStartTetheringCallback, null);
    }

    @SystemApi
    public void startTethering(int n, boolean bl, OnStartTetheringCallback object, Handler object2) {
        Preconditions.checkNotNull(object, "OnStartTetheringCallback cannot be null.");
        object = new ResultReceiver((Handler)object2, (OnStartTetheringCallback)object){
            final /* synthetic */ OnStartTetheringCallback val$callback;
            {
                this.val$callback = onStartTetheringCallback;
                super(handler);
            }

            @Override
            protected void onReceiveResult(int n, Bundle bundle) {
                if (n == 0) {
                    this.val$callback.onTetheringStarted();
                } else {
                    this.val$callback.onTetheringFailed();
                }
            }
        };
        try {
            String string2 = this.mContext.getOpPackageName();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("startTethering caller:");
            ((StringBuilder)object2).append(string2);
            Log.i(TAG, ((StringBuilder)object2).toString());
            this.mService.startTethering(n, (ResultReceiver)object, bl, string2);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Exception trying to start tethering.", remoteException);
            ((ResultReceiver)object).send(2, null);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Deprecated
    public int startUsingNetworkFeature(int n, String serializable) {
        this.checkLegacyRoutingApiAccess();
        Object object = this.networkCapabilitiesForFeature(n, (String)((Object)serializable));
        if (object == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't satisfy startUsingNetworkFeature for ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append((String)((Object)serializable));
            Log.d(TAG, stringBuilder.toString());
            return 3;
        }
        serializable = sLegacyRequests;
        // MONITORENTER : serializable
        Object object2 = sLegacyRequests.get(object);
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("renewing startUsingNetworkFeature request ");
            ((StringBuilder)object).append(((LegacyRequest)object2).networkRequest);
            Log.d(TAG, ((StringBuilder)object).toString());
            this.renewRequestLocked((LegacyRequest)object2);
            if (((LegacyRequest)object2).currentNetwork != null) {
                // MONITOREXIT : serializable
                return 0;
            }
            // MONITOREXIT : serializable
            return 1;
        }
        object2 = this.requestNetworkForFeatureLocked((NetworkCapabilities)object);
        // MONITOREXIT : serializable
        if (object2 != null) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("starting startUsingNetworkFeature for request ");
            ((StringBuilder)serializable).append(object2);
            Log.d(TAG, ((StringBuilder)serializable).toString());
            return 1;
        }
        Log.d(TAG, " request Failed");
        return 3;
    }

    @SystemApi
    public void stopTethering(int n) {
        try {
            String string2 = this.mContext.getOpPackageName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("stopTethering caller:");
            stringBuilder.append(string2);
            Log.i(TAG, stringBuilder.toString());
            this.mService.stopTethering(n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public int stopUsingNetworkFeature(int n, String string2) {
        this.checkLegacyRoutingApiAccess();
        Object object = this.networkCapabilitiesForFeature(n, string2);
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't satisfy stopUsingNetworkFeature for ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            Log.d(TAG, ((StringBuilder)object).toString());
            return -1;
        }
        if (this.removeRequestForFeature((NetworkCapabilities)object)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("stopUsingNetworkFeature for ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        return 1;
    }

    @UnsupportedAppUsage
    public int tether(String string2) {
        try {
            String string3 = this.mContext.getOpPackageName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("tether caller:");
            stringBuilder.append(string3);
            Log.i(TAG, stringBuilder.toString());
            int n = this.mService.tether(string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unregisterNetworkCallback(PendingIntent pendingIntent) {
        ConnectivityManager.checkPendingIntentNotNull(pendingIntent);
        this.releaseNetworkRequest(pendingIntent);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void unregisterNetworkCallback(NetworkCallback networkCallback) {
        this.printStackTrace();
        ConnectivityManager.checkCallbackNotNull(networkCallback);
        var2_3 = new ArrayList<NetworkRequest>();
        var3_4 = ConnectivityManager.sCallbacks;
        // MONITORENTER : var3_4
        var4_5 = NetworkCallback.access$900(networkCallback) != null;
        Preconditions.checkArgument(var4_5, "NetworkCallback was not registered");
        if (NetworkCallback.access$900(networkCallback) == ConnectivityManager.ALREADY_UNREGISTERED) {
            Log.d("ConnectivityManager", "NetworkCallback was already unregistered");
            // MONITOREXIT : var3_4
            return;
        }
        for (Map.Entry<NetworkRequest, NetworkCallback> var6_7 : ConnectivityManager.sCallbacks.entrySet()) {
            if (var6_7.getValue() != networkCallback) continue;
            var2_3.add(var6_7.getKey());
        }
        var5_6 = var2_3.iterator();
        do lbl-1000: // 2 sources:
        {
            if (!var5_6.hasNext()) {
                NetworkCallback.access$902(networkCallback, ConnectivityManager.ALREADY_UNREGISTERED);
                // MONITOREXIT : var3_4
                return;
            }
            var2_3 = (NetworkRequest)var5_6.next();
            this.mService.releaseNetworkRequest((NetworkRequest)var2_3);
            ConnectivityManager.sCallbacks.remove(var2_3);
            break;
        } while (true);
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        {
            ** while (true)
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void unregisterNetworkFactory(Messenger messenger) {
        try {
            this.mService.unregisterNetworkFactory(messenger);
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
    @SystemApi
    public void unregisterTetheringEventCallback(OnTetheringEventCallback object) {
        ArrayMap<OnTetheringEventCallback, ITetheringEventCallback> arrayMap = this.mTetheringEventCallbacks;
        synchronized (arrayMap) {
            ITetheringEventCallback iTetheringEventCallback = this.mTetheringEventCallbacks.remove(object);
            Preconditions.checkNotNull(iTetheringEventCallback, "callback was not registered.");
            try {
                String string2 = this.mContext.getOpPackageName();
                object = new StringBuilder();
                ((StringBuilder)object).append("unregisterTetheringEventCallback:");
                ((StringBuilder)object).append(string2);
                Log.i(TAG, ((StringBuilder)object).toString());
                this.mService.unregisterTetheringEventCallback(iTetheringEventCallback, string2);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public int untether(String string2) {
        try {
            String string3 = this.mContext.getOpPackageName();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("untether caller:");
            stringBuilder.append(string3);
            Log.i(TAG, stringBuilder.toString());
            int n = this.mService.untether(string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean updateLockdownVpn() {
        try {
            boolean bl = this.mService.updateLockdownVpn();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private class CallbackHandler
    extends Handler {
        private static final boolean DBG = false;
        private static final String TAG = "ConnectivityManager.CallbackHandler";

        CallbackHandler(Handler handler) {
            this(Preconditions.checkNotNull(handler, "Handler cannot be null.").getLooper());
        }

        CallbackHandler(Looper looper) {
            super(looper);
        }

        private <T> T getObject(Message message, Class<T> class_) {
            return message.getData().getParcelable(class_.getSimpleName());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object;
            if (message.what == 524296) {
                ConnectivityManager.this.expireRequest((NetworkCapabilities)message.obj, message.arg1);
                return;
            }
            Parcelable parcelable = this.getObject(message, NetworkRequest.class);
            Network network = this.getObject(message, Network.class);
            Object object2 = sCallbacks;
            synchronized (object2) {
                object = (NetworkCallback)sCallbacks.get(parcelable);
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("callback not found for ");
                    ((StringBuilder)object).append(ConnectivityManager.getCallbackName(message.what));
                    ((StringBuilder)object).append(" message");
                    Log.w(TAG, ((StringBuilder)object).toString());
                    return;
                }
                if (message.what == 524293) {
                    sCallbacks.remove(parcelable);
                    ((NetworkCallback)object).networkRequest = ALREADY_UNREGISTERED;
                }
            }
            int n = message.what;
            boolean bl = true;
            boolean bl2 = true;
            switch (n) {
                default: {
                    return;
                }
                case 524299: {
                    if (message.arg1 == 0) {
                        bl2 = false;
                    }
                    ((NetworkCallback)object).onBlockedStatusChanged(network, bl2);
                    return;
                }
                case 524298: {
                    ((NetworkCallback)object).onNetworkResumed(network);
                    return;
                }
                case 524297: {
                    ((NetworkCallback)object).onNetworkSuspended(network);
                    return;
                }
                case 524295: {
                    ((NetworkCallback)object).onLinkPropertiesChanged(network, this.getObject(message, LinkProperties.class));
                    return;
                }
                case 524294: {
                    ((NetworkCallback)object).onCapabilitiesChanged(network, this.getObject(message, NetworkCapabilities.class));
                    return;
                }
                case 524293: {
                    ((NetworkCallback)object).onUnavailable();
                    return;
                }
                case 524292: {
                    ((NetworkCallback)object).onLost(network);
                    return;
                }
                case 524291: {
                    ((NetworkCallback)object).onLosing(network, message.arg1);
                    return;
                }
                case 524290: {
                    object2 = this.getObject(message, NetworkCapabilities.class);
                    parcelable = this.getObject(message, LinkProperties.class);
                    bl2 = message.arg1 != 0 ? bl : false;
                    ((NetworkCallback)object).onAvailable(network, (NetworkCapabilities)object2, (LinkProperties)parcelable, bl2);
                    return;
                }
                case 524289: 
            }
            ((NetworkCallback)object).onPreCheck(network);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EntitlementResultCode {
    }

    public static interface Errors {
        public static final int TOO_MANY_REQUESTS = 1;
    }

    private static class LegacyRequest {
        Network currentNetwork;
        int delay = -1;
        int expireSequenceNumber;
        NetworkCallback networkCallback = new NetworkCallback(){

            @Override
            public void onAvailable(Network network) {
                LegacyRequest.this.currentNetwork = network;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("startUsingNetworkFeature got Network:");
                stringBuilder.append(network);
                Log.d(ConnectivityManager.TAG, stringBuilder.toString());
                ConnectivityManager.setProcessDefaultNetworkForHostResolution(network);
            }

            @Override
            public void onLost(Network network) {
                if (network.equals(LegacyRequest.this.currentNetwork)) {
                    LegacyRequest.this.clearDnsBinding();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("startUsingNetworkFeature lost Network:");
                stringBuilder.append(network);
                Log.d(ConnectivityManager.TAG, stringBuilder.toString());
            }
        };
        NetworkCapabilities networkCapabilities;
        NetworkRequest networkRequest;

        private LegacyRequest() {
        }

        private void clearDnsBinding() {
            if (this.currentNetwork != null) {
                this.currentNetwork = null;
                ConnectivityManager.setProcessDefaultNetworkForHostResolution(null);
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MultipathPreference {
    }

    public static class NetworkCallback {
        private NetworkRequest networkRequest;

        public void onAvailable(Network network) {
        }

        public void onAvailable(Network network, NetworkCapabilities networkCapabilities, LinkProperties linkProperties, boolean bl) {
            this.onAvailable(network);
            if (!networkCapabilities.hasCapability(21)) {
                this.onNetworkSuspended(network);
            }
            this.onCapabilitiesChanged(network, networkCapabilities);
            this.onLinkPropertiesChanged(network, linkProperties);
            this.onBlockedStatusChanged(network, bl);
        }

        public void onBlockedStatusChanged(Network network, boolean bl) {
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        }

        public void onLinkPropertiesChanged(Network network, LinkProperties linkProperties) {
        }

        public void onLosing(Network network, int n) {
        }

        public void onLost(Network network) {
        }

        public void onNetworkResumed(Network network) {
        }

        public void onNetworkSuspended(Network network) {
        }

        public void onPreCheck(Network network) {
        }

        public void onUnavailable() {
        }
    }

    public static interface OnNetworkActiveListener {
        public void onNetworkActive();
    }

    @SystemApi
    public static abstract class OnStartTetheringCallback {
        public void onTetheringFailed() {
        }

        public void onTetheringStarted() {
        }
    }

    @SystemApi
    public static interface OnTetheringEntitlementResultListener {
        public void onTetheringEntitlementResult(int var1);
    }

    @SystemApi
    public static abstract class OnTetheringEventCallback {
        public void onUpstreamChanged(Network network) {
        }
    }

    public class PacketKeepalive {
        public static final int BINDER_DIED = -10;
        public static final int ERROR_HARDWARE_ERROR = -31;
        public static final int ERROR_HARDWARE_UNSUPPORTED = -30;
        public static final int ERROR_INVALID_INTERVAL = -24;
        public static final int ERROR_INVALID_IP_ADDRESS = -21;
        public static final int ERROR_INVALID_LENGTH = -23;
        public static final int ERROR_INVALID_NETWORK = -20;
        public static final int ERROR_INVALID_PORT = -22;
        public static final int MIN_INTERVAL = 10;
        public static final int NATT_PORT = 4500;
        public static final int NO_KEEPALIVE = -1;
        public static final int SUCCESS = 0;
        private static final String TAG = "PacketKeepalive";
        private final ISocketKeepaliveCallback mCallback;
        private final ExecutorService mExecutor;
        private final Network mNetwork;
        private volatile Integer mSlot;

        private PacketKeepalive(Network network, final PacketKeepaliveCallback packetKeepaliveCallback) {
            Preconditions.checkNotNull(network, "network cannot be null");
            Preconditions.checkNotNull(packetKeepaliveCallback, "callback cannot be null");
            this.mNetwork = network;
            this.mExecutor = Executors.newSingleThreadExecutor();
            this.mCallback = new ISocketKeepaliveCallback.Stub(){

                public /* synthetic */ void lambda$onError$4$ConnectivityManager$PacketKeepalive$1(PacketKeepaliveCallback packetKeepaliveCallback2, int n) {
                    PacketKeepalive.this.mSlot = null;
                    packetKeepaliveCallback2.onError(n);
                }

                public /* synthetic */ void lambda$onError$5$ConnectivityManager$PacketKeepalive$1(PacketKeepaliveCallback packetKeepaliveCallback2, int n) throws Exception {
                    PacketKeepalive.this.mExecutor.execute(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$JWcQQZv8Qrs81cZ_BMAOZZ8MUeU(this, packetKeepaliveCallback2, n));
                }

                public /* synthetic */ void lambda$onStarted$0$ConnectivityManager$PacketKeepalive$1(int n, PacketKeepaliveCallback packetKeepaliveCallback2) {
                    PacketKeepalive.this.mSlot = n;
                    packetKeepaliveCallback2.onStarted();
                }

                public /* synthetic */ void lambda$onStarted$1$ConnectivityManager$PacketKeepalive$1(int n, PacketKeepaliveCallback packetKeepaliveCallback2) throws Exception {
                    PacketKeepalive.this.mExecutor.execute(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$NfMgP6Nh6Ep6LcaiJ10o_zBccII(this, n, packetKeepaliveCallback2));
                }

                public /* synthetic */ void lambda$onStopped$2$ConnectivityManager$PacketKeepalive$1(PacketKeepaliveCallback packetKeepaliveCallback2) {
                    PacketKeepalive.this.mSlot = null;
                    packetKeepaliveCallback2.onStopped();
                }

                public /* synthetic */ void lambda$onStopped$3$ConnectivityManager$PacketKeepalive$1(PacketKeepaliveCallback packetKeepaliveCallback2) throws Exception {
                    PacketKeepalive.this.mExecutor.execute(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$WmmtbYWlzqL_V8wWUDKe3CWjvy0(this, packetKeepaliveCallback2));
                }

                @Override
                public void onDataReceived() {
                }

                @Override
                public void onError(int n) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$nt5Pgsn85fhX6h9EJ0eAK_PXAjU(this, packetKeepaliveCallback, n));
                    PacketKeepalive.this.mExecutor.shutdown();
                }

                @Override
                public void onStarted(int n) {
                    Binder.withCleanCallingIdentity(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$iOtsqOYp69ztB6u3PYNu_iI_PGo(this, n, packetKeepaliveCallback));
                }

                @Override
                public void onStopped() {
                    Binder.withCleanCallingIdentity(new _$$Lambda$ConnectivityManager$PacketKeepalive$1$_H5tzn67t3ydWL8tXpl9UyOmDcc(this, packetKeepaliveCallback));
                    PacketKeepalive.this.mExecutor.shutdown();
                }
            };
        }

        public /* synthetic */ void lambda$stop$0$ConnectivityManager$PacketKeepalive() {
            try {
                if (this.mSlot != null) {
                    ConnectivityManager.this.mService.stopKeepalive(this.mNetwork, this.mSlot);
                }
                return;
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error stopping packet keepalive: ", remoteException);
                throw remoteException.rethrowFromSystemServer();
            }
        }

        @UnsupportedAppUsage
        public void stop() {
            try {
                ExecutorService executorService = this.mExecutor;
                _$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg _$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg = new _$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg(this);
                executorService.execute(_$$Lambda$ConnectivityManager$PacketKeepalive$__8nwufwzyblnuYRFEYIKx7L4Vg);
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                // empty catch block
            }
        }

    }

    public static class PacketKeepaliveCallback {
        @UnsupportedAppUsage
        public void onError(int n) {
        }

        @UnsupportedAppUsage
        public void onStarted() {
        }

        @UnsupportedAppUsage
        public void onStopped() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RestrictBackgroundStatus {
    }

    public static class TooManyRequestsException
    extends RuntimeException {
    }

}

