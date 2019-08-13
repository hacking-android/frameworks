/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.IWifiP2pManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pGroup;
import android.net.wifi.p2p.WifiP2pGroupList;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pWfdInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceResponse;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pServiceResponse;
import android.net.wifi.p2p.nsd.WifiP2pUpnpServiceResponse;
import android.os.BaseBundle;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.AsyncChannel;
import dalvik.system.CloseGuard;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WifiP2pManager {
    public static final int ADD_LOCAL_SERVICE = 139292;
    public static final int ADD_LOCAL_SERVICE_FAILED = 139293;
    public static final int ADD_LOCAL_SERVICE_SUCCEEDED = 139294;
    public static final int ADD_SERVICE_REQUEST = 139301;
    public static final int ADD_SERVICE_REQUEST_FAILED = 139302;
    public static final int ADD_SERVICE_REQUEST_SUCCEEDED = 139303;
    private static final int BASE = 139264;
    public static final int BUSY = 2;
    public static final String CALLING_BINDER = "android.net.wifi.p2p.CALLING_BINDER";
    public static final String CALLING_PACKAGE = "android.net.wifi.p2p.CALLING_PACKAGE";
    public static final int CANCEL_CONNECT = 139274;
    public static final int CANCEL_CONNECT_FAILED = 139275;
    public static final int CANCEL_CONNECT_SUCCEEDED = 139276;
    public static final int CLEAR_LOCAL_SERVICES = 139298;
    public static final int CLEAR_LOCAL_SERVICES_FAILED = 139299;
    public static final int CLEAR_LOCAL_SERVICES_SUCCEEDED = 139300;
    public static final int CLEAR_SERVICE_REQUESTS = 139307;
    public static final int CLEAR_SERVICE_REQUESTS_FAILED = 139308;
    public static final int CLEAR_SERVICE_REQUESTS_SUCCEEDED = 139309;
    public static final int CONNECT = 139271;
    public static final int CONNECT_FAILED = 139272;
    public static final int CONNECT_SUCCEEDED = 139273;
    @UnsupportedAppUsage
    public static final int CREATE_GROUP = 139277;
    public static final int CREATE_GROUP_FAILED = 139278;
    public static final int CREATE_GROUP_SUCCEEDED = 139279;
    public static final int DELETE_PERSISTENT_GROUP = 139318;
    public static final int DELETE_PERSISTENT_GROUP_FAILED = 139319;
    public static final int DELETE_PERSISTENT_GROUP_SUCCEEDED = 139320;
    public static final int DISCOVER_PEERS = 139265;
    public static final int DISCOVER_PEERS_FAILED = 139266;
    public static final int DISCOVER_PEERS_SUCCEEDED = 139267;
    public static final int DISCOVER_SERVICES = 139310;
    public static final int DISCOVER_SERVICES_FAILED = 139311;
    public static final int DISCOVER_SERVICES_SUCCEEDED = 139312;
    public static final int ERROR = 0;
    public static final String EXTRA_DISCOVERY_STATE = "discoveryState";
    public static final String EXTRA_HANDOVER_MESSAGE = "android.net.wifi.p2p.EXTRA_HANDOVER_MESSAGE";
    public static final String EXTRA_NETWORK_INFO = "networkInfo";
    public static final String EXTRA_P2P_DEVICE_LIST = "wifiP2pDeviceList";
    public static final String EXTRA_WIFI_P2P_DEVICE = "wifiP2pDevice";
    public static final String EXTRA_WIFI_P2P_GROUP = "p2pGroupInfo";
    public static final String EXTRA_WIFI_P2P_INFO = "wifiP2pInfo";
    public static final String EXTRA_WIFI_STATE = "wifi_p2p_state";
    public static final int FACTORY_RESET = 139346;
    public static final int FACTORY_RESET_FAILED = 139347;
    public static final int FACTORY_RESET_SUCCEEDED = 139348;
    public static final int GET_HANDOVER_REQUEST = 139339;
    public static final int GET_HANDOVER_SELECT = 139340;
    public static final int INITIATOR_REPORT_NFC_HANDOVER = 139342;
    public static final int MIRACAST_DISABLED = 0;
    public static final int MIRACAST_SINK = 2;
    public static final int MIRACAST_SOURCE = 1;
    public static final int NO_SERVICE_REQUESTS = 3;
    public static final int P2P_UNSUPPORTED = 1;
    public static final int PING = 139313;
    public static final int REMOVE_GROUP = 139280;
    public static final int REMOVE_GROUP_FAILED = 139281;
    public static final int REMOVE_GROUP_SUCCEEDED = 139282;
    public static final int REMOVE_LOCAL_SERVICE = 139295;
    public static final int REMOVE_LOCAL_SERVICE_FAILED = 139296;
    public static final int REMOVE_LOCAL_SERVICE_SUCCEEDED = 139297;
    public static final int REMOVE_SERVICE_REQUEST = 139304;
    public static final int REMOVE_SERVICE_REQUEST_FAILED = 139305;
    public static final int REMOVE_SERVICE_REQUEST_SUCCEEDED = 139306;
    public static final int REPORT_NFC_HANDOVER_FAILED = 139345;
    public static final int REPORT_NFC_HANDOVER_SUCCEEDED = 139344;
    public static final int REQUEST_CONNECTION_INFO = 139285;
    public static final int REQUEST_DEVICE_INFO = 139361;
    public static final int REQUEST_DISCOVERY_STATE = 139356;
    public static final int REQUEST_GROUP_INFO = 139287;
    public static final int REQUEST_NETWORK_INFO = 139358;
    public static final int REQUEST_ONGOING_PEER_CONFIG = 139349;
    public static final int REQUEST_P2P_STATE = 139354;
    public static final int REQUEST_PEERS = 139283;
    public static final int REQUEST_PERSISTENT_GROUP_INFO = 139321;
    public static final int RESPONDER_REPORT_NFC_HANDOVER = 139343;
    public static final int RESPONSE_CONNECTION_INFO = 139286;
    public static final int RESPONSE_DEVICE_INFO = 139362;
    public static final int RESPONSE_DISCOVERY_STATE = 139357;
    public static final int RESPONSE_GET_HANDOVER_MESSAGE = 139341;
    public static final int RESPONSE_GROUP_INFO = 139288;
    public static final int RESPONSE_NETWORK_INFO = 139359;
    public static final int RESPONSE_ONGOING_PEER_CONFIG = 139350;
    public static final int RESPONSE_P2P_STATE = 139355;
    public static final int RESPONSE_PEERS = 139284;
    public static final int RESPONSE_PERSISTENT_GROUP_INFO = 139322;
    public static final int RESPONSE_SERVICE = 139314;
    public static final int SET_CHANNEL = 139335;
    public static final int SET_CHANNEL_FAILED = 139336;
    public static final int SET_CHANNEL_SUCCEEDED = 139337;
    public static final int SET_DEVICE_NAME = 139315;
    public static final int SET_DEVICE_NAME_FAILED = 139316;
    public static final int SET_DEVICE_NAME_SUCCEEDED = 139317;
    public static final int SET_ONGOING_PEER_CONFIG = 139351;
    public static final int SET_ONGOING_PEER_CONFIG_FAILED = 139352;
    public static final int SET_ONGOING_PEER_CONFIG_SUCCEEDED = 139353;
    public static final int SET_WFD_INFO = 139323;
    public static final int SET_WFD_INFO_FAILED = 139324;
    public static final int SET_WFD_INFO_SUCCEEDED = 139325;
    public static final int START_LISTEN = 139329;
    public static final int START_LISTEN_FAILED = 139330;
    public static final int START_LISTEN_SUCCEEDED = 139331;
    public static final int START_WPS = 139326;
    public static final int START_WPS_FAILED = 139327;
    public static final int START_WPS_SUCCEEDED = 139328;
    public static final int STOP_DISCOVERY = 139268;
    public static final int STOP_DISCOVERY_FAILED = 139269;
    public static final int STOP_DISCOVERY_SUCCEEDED = 139270;
    public static final int STOP_LISTEN = 139332;
    public static final int STOP_LISTEN_FAILED = 139333;
    public static final int STOP_LISTEN_SUCCEEDED = 139334;
    private static final String TAG = "WifiP2pManager";
    public static final int UPDATE_CHANNEL_INFO = 139360;
    public static final String WIFI_P2P_CONNECTION_CHANGED_ACTION = "android.net.wifi.p2p.CONNECTION_STATE_CHANGE";
    public static final String WIFI_P2P_DISCOVERY_CHANGED_ACTION = "android.net.wifi.p2p.DISCOVERY_STATE_CHANGE";
    public static final int WIFI_P2P_DISCOVERY_STARTED = 2;
    public static final int WIFI_P2P_DISCOVERY_STOPPED = 1;
    public static final String WIFI_P2P_PEERS_CHANGED_ACTION = "android.net.wifi.p2p.PEERS_CHANGED";
    public static final String WIFI_P2P_PERSISTENT_GROUPS_CHANGED_ACTION = "android.net.wifi.p2p.PERSISTENT_GROUPS_CHANGED";
    public static final String WIFI_P2P_STATE_CHANGED_ACTION = "android.net.wifi.p2p.STATE_CHANGED";
    public static final int WIFI_P2P_STATE_DISABLED = 1;
    public static final int WIFI_P2P_STATE_ENABLED = 2;
    public static final String WIFI_P2P_THIS_DEVICE_CHANGED_ACTION = "android.net.wifi.p2p.THIS_DEVICE_CHANGED";
    IWifiP2pManager mService;

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public WifiP2pManager(IWifiP2pManager iWifiP2pManager) {
        this.mService = iWifiP2pManager;
    }

    private static void checkChannel(Channel channel) {
        if (channel != null) {
            return;
        }
        throw new IllegalArgumentException("Channel needs to be initialized");
    }

    private static void checkP2pConfig(WifiP2pConfig wifiP2pConfig) {
        if (wifiP2pConfig != null) {
            if (!TextUtils.isEmpty(wifiP2pConfig.deviceAddress)) {
                return;
            }
            throw new IllegalArgumentException("deviceAddress cannot be empty");
        }
        throw new IllegalArgumentException("config cannot be null");
    }

    private static void checkServiceInfo(WifiP2pServiceInfo wifiP2pServiceInfo) {
        if (wifiP2pServiceInfo != null) {
            return;
        }
        throw new IllegalArgumentException("service info is null");
    }

    private static void checkServiceRequest(WifiP2pServiceRequest wifiP2pServiceRequest) {
        if (wifiP2pServiceRequest != null) {
            return;
        }
        throw new IllegalArgumentException("service request is null");
    }

    private Channel initalizeChannel(Context object, Looper object2, ChannelListener channelListener, Messenger messenger, Binder binder) {
        if (messenger == null) {
            return null;
        }
        if (((Channel)(object2 = new Channel((Context)object, (Looper)object2, channelListener, binder, this))).mAsyncChannel.connectSync((Context)object, (Handler)((Channel)object2).mHandler, messenger) == 0) {
            object = new Bundle();
            ((BaseBundle)object).putString(CALLING_PACKAGE, ((Channel)object2).mContext.getOpPackageName());
            ((Bundle)object).putBinder(CALLING_BINDER, binder);
            ((Channel)object2).mAsyncChannel.sendMessage(139360, 0, ((Channel)object2).putListener(null), object);
            return object2;
        }
        ((Channel)object2).close();
        return null;
    }

    public void addLocalService(Channel channel, WifiP2pServiceInfo wifiP2pServiceInfo, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkServiceInfo(wifiP2pServiceInfo);
        channel.mAsyncChannel.sendMessage(139292, 0, channel.putListener(actionListener), wifiP2pServiceInfo);
    }

    public void addServiceRequest(Channel channel, WifiP2pServiceRequest wifiP2pServiceRequest, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkServiceRequest(wifiP2pServiceRequest);
        channel.mAsyncChannel.sendMessage(139301, 0, channel.putListener(actionListener), wifiP2pServiceRequest);
    }

    public void cancelConnect(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139274, 0, channel.putListener(actionListener));
    }

    public void clearLocalServices(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139298, 0, channel.putListener(actionListener));
    }

    public void clearServiceRequests(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139307, 0, channel.putListener(actionListener));
    }

    public void connect(Channel channel, WifiP2pConfig wifiP2pConfig, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkP2pConfig(wifiP2pConfig);
        channel.mAsyncChannel.sendMessage(139271, 0, channel.putListener(actionListener), wifiP2pConfig);
    }

    public void createGroup(Channel channel, WifiP2pConfig wifiP2pConfig, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139277, 0, channel.putListener(actionListener), wifiP2pConfig);
    }

    public void createGroup(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139277, -2, channel.putListener(actionListener));
    }

    @UnsupportedAppUsage
    public void deletePersistentGroup(Channel channel, int n, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139318, n, channel.putListener(actionListener));
    }

    public void discoverPeers(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139265, 0, channel.putListener(actionListener));
    }

    public void discoverServices(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139310, 0, channel.putListener(actionListener));
    }

    public void factoryReset(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139346, 0, channel.putListener(actionListener));
    }

    public Messenger getMessenger(Binder object) {
        try {
            object = this.mService.getMessenger((IBinder)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void getNfcHandoverRequest(Channel channel, HandoverMessageListener handoverMessageListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139339, 0, channel.putListener(handoverMessageListener));
    }

    public void getNfcHandoverSelect(Channel channel, HandoverMessageListener handoverMessageListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139340, 0, channel.putListener(handoverMessageListener));
    }

    public Messenger getP2pStateMachineMessenger() {
        try {
            Messenger messenger = this.mService.getP2pStateMachineMessenger();
            return messenger;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Channel initialize(Context context, Looper looper, ChannelListener channelListener) {
        Binder binder = new Binder();
        return this.initalizeChannel(context, looper, channelListener, this.getMessenger(binder), binder);
    }

    public Channel initializeInternal(Context context, Looper looper, ChannelListener channelListener) {
        return this.initalizeChannel(context, looper, channelListener, this.getP2pStateMachineMessenger(), null);
    }

    public void initiatorReportNfcHandover(Channel channel, String string2, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_HANDOVER_MESSAGE, string2);
        channel.mAsyncChannel.sendMessage(139342, 0, channel.putListener(actionListener), bundle);
    }

    public void listen(Channel channel, boolean bl, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        AsyncChannel asyncChannel = channel.mAsyncChannel;
        int n = bl ? 139329 : 139332;
        asyncChannel.sendMessage(n, 0, channel.putListener(actionListener));
    }

    public void removeGroup(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139280, 0, channel.putListener(actionListener));
    }

    public void removeLocalService(Channel channel, WifiP2pServiceInfo wifiP2pServiceInfo, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkServiceInfo(wifiP2pServiceInfo);
        channel.mAsyncChannel.sendMessage(139295, 0, channel.putListener(actionListener), wifiP2pServiceInfo);
    }

    public void removeServiceRequest(Channel channel, WifiP2pServiceRequest wifiP2pServiceRequest, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkServiceRequest(wifiP2pServiceRequest);
        channel.mAsyncChannel.sendMessage(139304, 0, channel.putListener(actionListener), wifiP2pServiceRequest);
    }

    public void requestConnectionInfo(Channel channel, ConnectionInfoListener connectionInfoListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139285, 0, channel.putListener(connectionInfoListener));
    }

    public void requestDeviceInfo(Channel channel, DeviceInfoListener deviceInfoListener) {
        WifiP2pManager.checkChannel(channel);
        if (deviceInfoListener != null) {
            channel.mAsyncChannel.sendMessage(139361, 0, channel.putListener(deviceInfoListener));
            return;
        }
        throw new IllegalArgumentException("This listener cannot be null.");
    }

    public void requestDiscoveryState(Channel channel, DiscoveryStateListener discoveryStateListener) {
        WifiP2pManager.checkChannel(channel);
        if (discoveryStateListener != null) {
            channel.mAsyncChannel.sendMessage(139356, 0, channel.putListener(discoveryStateListener));
            return;
        }
        throw new IllegalArgumentException("This listener cannot be null.");
    }

    public void requestGroupInfo(Channel channel, GroupInfoListener groupInfoListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139287, 0, channel.putListener(groupInfoListener));
    }

    public void requestNetworkInfo(Channel channel, NetworkInfoListener networkInfoListener) {
        WifiP2pManager.checkChannel(channel);
        if (networkInfoListener != null) {
            channel.mAsyncChannel.sendMessage(139358, 0, channel.putListener(networkInfoListener));
            return;
        }
        throw new IllegalArgumentException("This listener cannot be null.");
    }

    public void requestOngoingPeerConfig(Channel channel, OngoingPeerInfoListener ongoingPeerInfoListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139349, Binder.getCallingUid(), channel.putListener(ongoingPeerInfoListener));
    }

    public void requestP2pState(Channel channel, P2pStateListener p2pStateListener) {
        WifiP2pManager.checkChannel(channel);
        if (p2pStateListener != null) {
            channel.mAsyncChannel.sendMessage(139354, 0, channel.putListener(p2pStateListener));
            return;
        }
        throw new IllegalArgumentException("This listener cannot be null.");
    }

    public void requestPeers(Channel channel, PeerListListener peerListListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139283, 0, channel.putListener(peerListListener));
    }

    @UnsupportedAppUsage
    public void requestPersistentGroupInfo(Channel channel, PersistentGroupInfoListener persistentGroupInfoListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139321, 0, channel.putListener(persistentGroupInfoListener));
    }

    public void responderReportNfcHandover(Channel channel, String string2, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_HANDOVER_MESSAGE, string2);
        channel.mAsyncChannel.sendMessage(139343, 0, channel.putListener(actionListener), bundle);
    }

    @UnsupportedAppUsage
    public void setDeviceName(Channel channel, String string2, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pDevice wifiP2pDevice = new WifiP2pDevice();
        wifiP2pDevice.deviceName = string2;
        channel.mAsyncChannel.sendMessage(139315, 0, channel.putListener(actionListener), wifiP2pDevice);
    }

    public void setDnsSdResponseListeners(Channel channel, DnsSdServiceResponseListener dnsSdServiceResponseListener, DnsSdTxtRecordListener dnsSdTxtRecordListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mDnsSdServRspListener = dnsSdServiceResponseListener;
        channel.mDnsSdTxtListener = dnsSdTxtRecordListener;
    }

    @UnsupportedAppUsage
    public void setMiracastMode(int n) {
        try {
            this.mService.setMiracastMode(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOngoingPeerConfig(Channel channel, WifiP2pConfig wifiP2pConfig, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        WifiP2pManager.checkP2pConfig(wifiP2pConfig);
        channel.mAsyncChannel.sendMessage(139351, 0, channel.putListener(actionListener), wifiP2pConfig);
    }

    public void setServiceResponseListener(Channel channel, ServiceResponseListener serviceResponseListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mServRspListener = serviceResponseListener;
    }

    public void setUpnpServiceResponseListener(Channel channel, UpnpServiceResponseListener upnpServiceResponseListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mUpnpServRspListener = upnpServiceResponseListener;
    }

    @UnsupportedAppUsage
    public void setWFDInfo(Channel channel, WifiP2pWfdInfo wifiP2pWfdInfo, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        try {
            this.mService.checkConfigureWifiDisplayPermission();
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
        channel.mAsyncChannel.sendMessage(139323, 0, channel.putListener(actionListener), wifiP2pWfdInfo);
    }

    @UnsupportedAppUsage
    public void setWifiP2pChannels(Channel channel, int n, int n2, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        Bundle bundle = new Bundle();
        bundle.putInt("lc", n);
        bundle.putInt("oc", n2);
        channel.mAsyncChannel.sendMessage(139335, 0, channel.putListener(actionListener), bundle);
    }

    @UnsupportedAppUsage
    public void startWps(Channel channel, WpsInfo wpsInfo, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139326, 0, channel.putListener(actionListener), wpsInfo);
    }

    public void stopPeerDiscovery(Channel channel, ActionListener actionListener) {
        WifiP2pManager.checkChannel(channel);
        channel.mAsyncChannel.sendMessage(139268, 0, channel.putListener(actionListener));
    }

    public static interface ActionListener {
        public void onFailure(int var1);

        public void onSuccess();
    }

    public static class Channel
    implements AutoCloseable {
        private static final int INVALID_LISTENER_KEY = 0;
        @UnsupportedAppUsage
        private AsyncChannel mAsyncChannel = new AsyncChannel();
        final Binder mBinder;
        private ChannelListener mChannelListener;
        private final CloseGuard mCloseGuard = CloseGuard.get();
        Context mContext;
        private DnsSdServiceResponseListener mDnsSdServRspListener;
        private DnsSdTxtRecordListener mDnsSdTxtListener;
        private P2pHandler mHandler;
        private int mListenerKey = 0;
        private HashMap<Integer, Object> mListenerMap = new HashMap();
        private final Object mListenerMapLock = new Object();
        private final WifiP2pManager mP2pManager;
        private ServiceResponseListener mServRspListener;
        private UpnpServiceResponseListener mUpnpServRspListener;

        public Channel(Context context, Looper looper, ChannelListener channelListener, Binder binder, WifiP2pManager wifiP2pManager) {
            this.mHandler = new P2pHandler(looper);
            this.mChannelListener = channelListener;
            this.mContext = context;
            this.mBinder = binder;
            this.mP2pManager = wifiP2pManager;
            this.mCloseGuard.open("close");
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Object getListener(int n) {
            if (n == 0) {
                return null;
            }
            Object object = this.mListenerMapLock;
            synchronized (object) {
                return this.mListenerMap.remove(n);
            }
        }

        private void handleDnsSdServiceResponse(WifiP2pDnsSdServiceResponse wifiP2pDnsSdServiceResponse) {
            if (wifiP2pDnsSdServiceResponse.getDnsType() == 12) {
                DnsSdServiceResponseListener dnsSdServiceResponseListener = this.mDnsSdServRspListener;
                if (dnsSdServiceResponseListener != null) {
                    dnsSdServiceResponseListener.onDnsSdServiceAvailable(wifiP2pDnsSdServiceResponse.getInstanceName(), wifiP2pDnsSdServiceResponse.getDnsQueryName(), wifiP2pDnsSdServiceResponse.getSrcDevice());
                }
            } else if (wifiP2pDnsSdServiceResponse.getDnsType() == 16) {
                DnsSdTxtRecordListener dnsSdTxtRecordListener = this.mDnsSdTxtListener;
                if (dnsSdTxtRecordListener != null) {
                    dnsSdTxtRecordListener.onDnsSdTxtRecordAvailable(wifiP2pDnsSdServiceResponse.getDnsQueryName(), wifiP2pDnsSdServiceResponse.getTxtRecord(), wifiP2pDnsSdServiceResponse.getSrcDevice());
                }
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unhandled resp ");
                stringBuilder.append(wifiP2pDnsSdServiceResponse);
                Log.e(WifiP2pManager.TAG, stringBuilder.toString());
            }
        }

        private void handleServiceResponse(WifiP2pServiceResponse wifiP2pServiceResponse) {
            if (wifiP2pServiceResponse instanceof WifiP2pDnsSdServiceResponse) {
                this.handleDnsSdServiceResponse((WifiP2pDnsSdServiceResponse)wifiP2pServiceResponse);
            } else if (wifiP2pServiceResponse instanceof WifiP2pUpnpServiceResponse) {
                if (this.mUpnpServRspListener != null) {
                    this.handleUpnpServiceResponse((WifiP2pUpnpServiceResponse)wifiP2pServiceResponse);
                }
            } else {
                ServiceResponseListener serviceResponseListener = this.mServRspListener;
                if (serviceResponseListener != null) {
                    serviceResponseListener.onServiceAvailable(wifiP2pServiceResponse.getServiceType(), wifiP2pServiceResponse.getRawData(), wifiP2pServiceResponse.getSrcDevice());
                }
            }
        }

        private void handleUpnpServiceResponse(WifiP2pUpnpServiceResponse wifiP2pUpnpServiceResponse) {
            this.mUpnpServRspListener.onUpnpServiceAvailable(wifiP2pUpnpServiceResponse.getUniqueServiceNames(), wifiP2pUpnpServiceResponse.getSrcDevice());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @UnsupportedAppUsage
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
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void close() {
            WifiP2pManager wifiP2pManager = this.mP2pManager;
            if (wifiP2pManager == null) {
                Log.w(WifiP2pManager.TAG, "Channel.close(): Null mP2pManager!?");
            } else {
                wifiP2pManager.mService.close(this.mBinder);
            }
            this.mAsyncChannel.disconnect();
            this.mCloseGuard.close();
            return;
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
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

        class P2pHandler
        extends Handler {
            P2pHandler(Looper looper) {
                super(looper);
            }

            @Override
            public void handleMessage(Message object) {
                Object object2 = Channel.this.getListener(((Message)object).arg2);
                int n = ((Message)object).what;
                StringBuilder stringBuilder = null;
                switch (n) {
                    default: {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Ignored ");
                        stringBuilder.append(object);
                        Log.d(WifiP2pManager.TAG, stringBuilder.toString());
                        break;
                    }
                    case 139362: {
                        if (object2 == null) break;
                        ((DeviceInfoListener)object2).onDeviceInfoAvailable((WifiP2pDevice)((Message)object).obj);
                        break;
                    }
                    case 139359: {
                        if (object2 == null) break;
                        ((NetworkInfoListener)object2).onNetworkInfoAvailable((NetworkInfo)((Message)object).obj);
                        break;
                    }
                    case 139357: {
                        if (object2 == null) break;
                        ((DiscoveryStateListener)object2).onDiscoveryStateAvailable(((Message)object).arg1);
                        break;
                    }
                    case 139355: {
                        if (object2 == null) break;
                        ((P2pStateListener)object2).onP2pStateAvailable(((Message)object).arg1);
                        break;
                    }
                    case 139350: {
                        object = (WifiP2pConfig)((Message)object).obj;
                        if (object2 == null) break;
                        ((OngoingPeerInfoListener)object2).onOngoingPeerAvailable((WifiP2pConfig)object);
                        break;
                    }
                    case 139341: {
                        object = (Bundle)((Message)object).obj;
                        if (object2 == null) break;
                        object = object != null ? ((BaseBundle)object).getString(WifiP2pManager.EXTRA_HANDOVER_MESSAGE) : stringBuilder;
                        ((HandoverMessageListener)object2).onHandoverMessageAvailable((String)object);
                        break;
                    }
                    case 139322: {
                        object = (WifiP2pGroupList)((Message)object).obj;
                        if (object2 == null) break;
                        ((PersistentGroupInfoListener)object2).onPersistentGroupInfoAvailable((WifiP2pGroupList)object);
                        break;
                    }
                    case 139314: {
                        object = (WifiP2pServiceResponse)((Message)object).obj;
                        Channel.this.handleServiceResponse((WifiP2pServiceResponse)object);
                        break;
                    }
                    case 139288: {
                        object = (WifiP2pGroup)((Message)object).obj;
                        if (object2 == null) break;
                        ((GroupInfoListener)object2).onGroupInfoAvailable((WifiP2pGroup)object);
                        break;
                    }
                    case 139286: {
                        object = (WifiP2pInfo)((Message)object).obj;
                        if (object2 == null) break;
                        ((ConnectionInfoListener)object2).onConnectionInfoAvailable((WifiP2pInfo)object);
                        break;
                    }
                    case 139284: {
                        object = (WifiP2pDeviceList)((Message)object).obj;
                        if (object2 == null) break;
                        ((PeerListListener)object2).onPeersAvailable((WifiP2pDeviceList)object);
                        break;
                    }
                    case 139267: 
                    case 139270: 
                    case 139273: 
                    case 139276: 
                    case 139279: 
                    case 139282: 
                    case 139294: 
                    case 139297: 
                    case 139300: 
                    case 139303: 
                    case 139306: 
                    case 139309: 
                    case 139312: 
                    case 139317: 
                    case 139320: 
                    case 139325: 
                    case 139328: 
                    case 139331: 
                    case 139334: 
                    case 139337: 
                    case 139344: 
                    case 139348: 
                    case 139353: {
                        if (object2 == null) break;
                        ((ActionListener)object2).onSuccess();
                        break;
                    }
                    case 139266: 
                    case 139269: 
                    case 139272: 
                    case 139275: 
                    case 139278: 
                    case 139281: 
                    case 139293: 
                    case 139296: 
                    case 139299: 
                    case 139302: 
                    case 139305: 
                    case 139308: 
                    case 139311: 
                    case 139316: 
                    case 139319: 
                    case 139324: 
                    case 139327: 
                    case 139330: 
                    case 139333: 
                    case 139336: 
                    case 139345: 
                    case 139347: 
                    case 139352: {
                        if (object2 == null) break;
                        ((ActionListener)object2).onFailure(((Message)object).arg1);
                        break;
                    }
                    case 69636: {
                        if (Channel.this.mChannelListener == null) break;
                        Channel.this.mChannelListener.onChannelDisconnected();
                        Channel.this.mChannelListener = null;
                    }
                }
            }
        }

    }

    public static interface ChannelListener {
        public void onChannelDisconnected();
    }

    public static interface ConnectionInfoListener {
        public void onConnectionInfoAvailable(WifiP2pInfo var1);
    }

    public static interface DeviceInfoListener {
        public void onDeviceInfoAvailable(WifiP2pDevice var1);
    }

    public static interface DiscoveryStateListener {
        public void onDiscoveryStateAvailable(int var1);
    }

    public static interface DnsSdServiceResponseListener {
        public void onDnsSdServiceAvailable(String var1, String var2, WifiP2pDevice var3);
    }

    public static interface DnsSdTxtRecordListener {
        public void onDnsSdTxtRecordAvailable(String var1, Map<String, String> var2, WifiP2pDevice var3);
    }

    public static interface GroupInfoListener {
        public void onGroupInfoAvailable(WifiP2pGroup var1);
    }

    public static interface HandoverMessageListener {
        public void onHandoverMessageAvailable(String var1);
    }

    public static interface NetworkInfoListener {
        public void onNetworkInfoAvailable(NetworkInfo var1);
    }

    public static interface OngoingPeerInfoListener {
        public void onOngoingPeerAvailable(WifiP2pConfig var1);
    }

    public static interface P2pStateListener {
        public void onP2pStateAvailable(int var1);
    }

    public static interface PeerListListener {
        public void onPeersAvailable(WifiP2pDeviceList var1);
    }

    public static interface PersistentGroupInfoListener {
        public void onPersistentGroupInfoAvailable(WifiP2pGroupList var1);
    }

    public static interface ServiceResponseListener {
        public void onServiceAvailable(int var1, byte[] var2, WifiP2pDevice var3);
    }

    public static interface UpnpServiceResponseListener {
        public void onUpnpServiceAvailable(List<String> var1, WifiP2pDevice var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WifiP2pDiscoveryState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WifiP2pState {
    }

}

