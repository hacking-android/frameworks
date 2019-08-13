/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.HexEncoding
 */
package android.net.wifi.aware;

import android.content.Context;
import android.net.NetworkSpecifier;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.Characteristics;
import android.net.wifi.aware.ConfigRequest;
import android.net.wifi.aware.DiscoverySession;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.IWifiAwareDiscoverySessionCallback;
import android.net.wifi.aware.IWifiAwareEventCallback;
import android.net.wifi.aware.IWifiAwareManager;
import android.net.wifi.aware.IdentityChangedListener;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeConfig;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.net.wifi.aware.TlvBufferUtils;
import android.net.wifi.aware.WifiAwareNetworkSpecifier;
import android.net.wifi.aware.WifiAwareSession;
import android.net.wifi.aware.WifiAwareUtils;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.nio.BufferOverflowException;
import java.util.List;
import libcore.util.HexEncoding;

public class WifiAwareManager {
    public static final String ACTION_WIFI_AWARE_STATE_CHANGED = "android.net.wifi.aware.action.WIFI_AWARE_STATE_CHANGED";
    private static final boolean DBG = false;
    private static final String TAG = "WifiAwareManager";
    private static final boolean VDBG = false;
    public static final int WIFI_AWARE_DATA_PATH_ROLE_INITIATOR = 0;
    public static final int WIFI_AWARE_DATA_PATH_ROLE_RESPONDER = 1;
    private final Context mContext;
    private final Object mLock = new Object();
    private final IWifiAwareManager mService;

    public WifiAwareManager(Context context, IWifiAwareManager iWifiAwareManager) {
        this.mContext = context;
        this.mService = iWifiAwareManager;
    }

    public void attach(AttachCallback attachCallback, IdentityChangedListener identityChangedListener, Handler handler) {
        this.attach(handler, null, attachCallback, identityChangedListener);
    }

    public void attach(AttachCallback attachCallback, Handler handler) {
        this.attach(handler, null, attachCallback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void attach(Handler object, ConfigRequest configRequest, AttachCallback attachCallback, IdentityChangedListener identityChangedListener) {
        if (attachCallback == null) {
            throw new IllegalArgumentException("Null callback provided");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            object = object == null ? Looper.getMainLooper() : ((Handler)object).getLooper();
            try {
                Binder binder = new Binder();
                IWifiAwareManager iWifiAwareManager = this.mService;
                String string2 = this.mContext.getOpPackageName();
                WifiAwareEventCallbackProxy wifiAwareEventCallbackProxy = new WifiAwareEventCallbackProxy(this, (Looper)object, binder, attachCallback, identityChangedListener);
                boolean bl = identityChangedListener != null;
                iWifiAwareManager.connect(binder, string2, wifiAwareEventCallbackProxy, configRequest, bl);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public NetworkSpecifier createNetworkSpecifier(int n, int n2, int n3, PeerHandle peerHandle, byte[] arrby, String string2) {
        if (WifiAwareUtils.isLegacyVersion(this.mContext, 29)) {
            if (n2 != 0 && n2 != 1) {
                throw new IllegalArgumentException("createNetworkSpecifier: Invalid 'role' argument when creating a network specifier");
            }
            if (n2 != 0 && WifiAwareUtils.isLegacyVersion(this.mContext, 28) || peerHandle != null) {
                int n4 = peerHandle == null ? 1 : 0;
                int n5 = peerHandle != null ? peerHandle.peerId : 0;
                return new WifiAwareNetworkSpecifier(n4, n2, n, n3, n5, null, arrby, string2, 0, -1, Process.myUid());
            }
            throw new IllegalArgumentException("createNetworkSpecifier: Invalid peer handle - cannot be null");
        }
        throw new UnsupportedOperationException("API deprecated - use WifiAwareNetworkSpecifier.Builder");
    }

    public NetworkSpecifier createNetworkSpecifier(int n, int n2, byte[] arrby, byte[] arrby2, String string2) {
        if (n2 != 0 && n2 != 1) {
            throw new IllegalArgumentException("createNetworkSpecifier: Invalid 'role' argument when creating a network specifier");
        }
        if (n2 != 0 && WifiAwareUtils.isLegacyVersion(this.mContext, 28) || arrby != null) {
            if (arrby != null && arrby.length != 6) {
                throw new IllegalArgumentException("createNetworkSpecifier: Invalid peer MAC address");
            }
            int n3 = arrby == null ? 3 : 2;
            return new WifiAwareNetworkSpecifier(n3, n2, n, 0, 0, arrby, arrby2, string2, 0, -1, Process.myUid());
        }
        throw new IllegalArgumentException("createNetworkSpecifier: Invalid peer MAC - cannot be null");
    }

    public void disconnect(int n, Binder binder) {
        try {
            this.mService.disconnect(n, binder);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Characteristics getCharacteristics() {
        try {
            Characteristics characteristics = this.mService.getCharacteristics();
            return characteristics;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAvailable() {
        try {
            boolean bl = this.mService.isUsageEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void publish(int n, Looper looper, PublishConfig publishConfig, DiscoverySessionCallback discoverySessionCallback) {
        if (discoverySessionCallback != null) {
            try {
                IWifiAwareManager iWifiAwareManager = this.mService;
                String string2 = this.mContext.getOpPackageName();
                WifiAwareDiscoverySessionCallbackProxy wifiAwareDiscoverySessionCallbackProxy = new WifiAwareDiscoverySessionCallbackProxy(this, looper, true, discoverySessionCallback, n);
                iWifiAwareManager.publish(string2, n, publishConfig, wifiAwareDiscoverySessionCallbackProxy);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Null callback provided");
    }

    public void sendMessage(int n, int n2, PeerHandle peerHandle, byte[] arrby, int n3, int n4) {
        if (peerHandle != null) {
            try {
                this.mService.sendMessage(n, n2, peerHandle.peerId, arrby, n3, n4);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("sendMessage: invalid peerHandle - must be non-null");
    }

    public void subscribe(int n, Looper looper, SubscribeConfig subscribeConfig, DiscoverySessionCallback discoverySessionCallback) {
        if (discoverySessionCallback != null) {
            try {
                IWifiAwareManager iWifiAwareManager = this.mService;
                String string2 = this.mContext.getOpPackageName();
                WifiAwareDiscoverySessionCallbackProxy wifiAwareDiscoverySessionCallbackProxy = new WifiAwareDiscoverySessionCallbackProxy(this, looper, false, discoverySessionCallback, n);
                iWifiAwareManager.subscribe(string2, n, subscribeConfig, wifiAwareDiscoverySessionCallbackProxy);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Null callback provided");
    }

    public void terminateSession(int n, int n2) {
        try {
            this.mService.terminateSession(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updatePublish(int n, int n2, PublishConfig publishConfig) {
        try {
            this.mService.updatePublish(n, n2, publishConfig);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateSubscribe(int n, int n2, SubscribeConfig subscribeConfig) {
        try {
            this.mService.updateSubscribe(n, n2, subscribeConfig);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DataPathRole {
    }

    private static class WifiAwareDiscoverySessionCallbackProxy
    extends IWifiAwareDiscoverySessionCallback.Stub {
        private static final int CALLBACK_MATCH = 4;
        private static final int CALLBACK_MATCH_WITH_DISTANCE = 8;
        private static final int CALLBACK_MESSAGE_RECEIVED = 7;
        private static final int CALLBACK_MESSAGE_SEND_FAIL = 6;
        private static final int CALLBACK_MESSAGE_SEND_SUCCESS = 5;
        private static final int CALLBACK_SESSION_CONFIG_FAIL = 2;
        private static final int CALLBACK_SESSION_CONFIG_SUCCESS = 1;
        private static final int CALLBACK_SESSION_STARTED = 0;
        private static final int CALLBACK_SESSION_TERMINATED = 3;
        private static final String MESSAGE_BUNDLE_KEY_MESSAGE = "message";
        private static final String MESSAGE_BUNDLE_KEY_MESSAGE2 = "message2";
        private final WeakReference<WifiAwareManager> mAwareManager;
        private final int mClientId;
        private final Handler mHandler;
        private final boolean mIsPublish;
        private final DiscoverySessionCallback mOriginalCallback;
        private DiscoverySession mSession;

        WifiAwareDiscoverySessionCallbackProxy(WifiAwareManager wifiAwareManager, Looper looper, boolean bl, DiscoverySessionCallback discoverySessionCallback, int n) {
            this.mAwareManager = new WeakReference<WifiAwareManager>(wifiAwareManager);
            this.mIsPublish = bl;
            this.mOriginalCallback = discoverySessionCallback;
            this.mClientId = n;
            this.mHandler = new Handler(looper){

                @Override
                public void handleMessage(Message message) {
                    if (WifiAwareDiscoverySessionCallbackProxy.this.mAwareManager.get() == null) {
                        Log.w(WifiAwareManager.TAG, "WifiAwareDiscoverySessionCallbackProxy: handleMessage post GC");
                        return;
                    }
                    switch (message.what) {
                        default: {
                            break;
                        }
                        case 7: {
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onMessageReceived(new PeerHandle(message.arg1), (byte[])message.obj);
                            break;
                        }
                        case 6: {
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onMessageSendFailed(message.arg1);
                            break;
                        }
                        case 5: {
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onMessageSendSucceeded(message.arg1);
                            break;
                        }
                        case 4: 
                        case 8: {
                            Iterable<TlvBufferUtils.TlvElement> iterable;
                            byte[] arrby = message.getData().getByteArray(WifiAwareDiscoverySessionCallbackProxy.MESSAGE_BUNDLE_KEY_MESSAGE2);
                            try {
                                iterable = new TlvBufferUtils.TlvIterable(0, 1, arrby);
                                iterable = iterable.toList();
                            }
                            catch (BufferOverflowException bufferOverflowException) {
                                iterable = null;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("onServiceDiscovered: invalid match filter byte array '");
                                stringBuilder.append(new String(HexEncoding.encode((byte[])arrby)));
                                stringBuilder.append("' - cannot be parsed: e=");
                                stringBuilder.append(bufferOverflowException);
                                Log.e(WifiAwareManager.TAG, stringBuilder.toString());
                            }
                            if (message.what == 4) {
                                WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onServiceDiscovered(new PeerHandle(message.arg1), message.getData().getByteArray(WifiAwareDiscoverySessionCallbackProxy.MESSAGE_BUNDLE_KEY_MESSAGE), (List<byte[]>)iterable);
                                break;
                            }
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onServiceDiscoveredWithinRange(new PeerHandle(message.arg1), message.getData().getByteArray(WifiAwareDiscoverySessionCallbackProxy.MESSAGE_BUNDLE_KEY_MESSAGE), (List<byte[]>)iterable, message.arg2);
                            break;
                        }
                        case 3: {
                            WifiAwareDiscoverySessionCallbackProxy.this.onProxySessionTerminated(message.arg1);
                            break;
                        }
                        case 2: {
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onSessionConfigFailed();
                            if (WifiAwareDiscoverySessionCallbackProxy.this.mSession != null) break;
                            WifiAwareDiscoverySessionCallbackProxy.this.mAwareManager.clear();
                            break;
                        }
                        case 1: {
                            WifiAwareDiscoverySessionCallbackProxy.this.mOriginalCallback.onSessionConfigUpdated();
                            break;
                        }
                        case 0: {
                            WifiAwareDiscoverySessionCallbackProxy.this.onProxySessionStarted(message.arg1);
                        }
                    }
                }
            };
        }

        private void onMatchCommon(int n, int n2, byte[] object, byte[] arrby, int n3) {
            Bundle bundle = new Bundle();
            bundle.putByteArray(MESSAGE_BUNDLE_KEY_MESSAGE, (byte[])object);
            bundle.putByteArray(MESSAGE_BUNDLE_KEY_MESSAGE2, arrby);
            object = this.mHandler.obtainMessage(n);
            ((Message)object).arg1 = n2;
            ((Message)object).arg2 = n3;
            ((Message)object).setData(bundle);
            this.mHandler.sendMessage((Message)object);
        }

        @Override
        public void onMatch(int n, byte[] arrby, byte[] arrby2) {
            this.onMatchCommon(4, n, arrby, arrby2, 0);
        }

        @Override
        public void onMatchWithDistance(int n, byte[] arrby, byte[] arrby2, int n2) {
            this.onMatchCommon(8, n, arrby, arrby2, n2);
        }

        @Override
        public void onMessageReceived(int n, byte[] arrby) {
            Message message = this.mHandler.obtainMessage(7);
            message.arg1 = n;
            message.obj = arrby;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onMessageSendFail(int n, int n2) {
            Message message = this.mHandler.obtainMessage(6);
            message.arg1 = n;
            message.arg2 = n2;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onMessageSendSuccess(int n) {
            Message message = this.mHandler.obtainMessage(5);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

        public void onProxySessionStarted(int n) {
            if (this.mSession == null) {
                Object object = (WifiAwareManager)this.mAwareManager.get();
                if (object == null) {
                    Log.w(WifiAwareManager.TAG, "onProxySessionStarted: mgr GC'd");
                    return;
                }
                if (this.mIsPublish) {
                    this.mSession = object = new PublishDiscoverySession((WifiAwareManager)object, this.mClientId, n);
                    this.mOriginalCallback.onPublishStarted((PublishDiscoverySession)object);
                } else {
                    this.mSession = object = new SubscribeDiscoverySession((WifiAwareManager)object, this.mClientId, n);
                    this.mOriginalCallback.onSubscribeStarted((SubscribeDiscoverySession)object);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSessionStarted: sessionId=");
            stringBuilder.append(n);
            stringBuilder.append(": session already created!?");
            Log.e(WifiAwareManager.TAG, stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("onSessionStarted: sessionId=");
            stringBuilder.append(n);
            stringBuilder.append(": session already created!?");
            throw new IllegalStateException(stringBuilder.toString());
        }

        public void onProxySessionTerminated(int n) {
            DiscoverySession discoverySession = this.mSession;
            if (discoverySession != null) {
                discoverySession.setTerminated();
                this.mSession = null;
            } else {
                Log.w(WifiAwareManager.TAG, "Proxy: onSessionTerminated called but mSession is null!?");
            }
            this.mAwareManager.clear();
            this.mOriginalCallback.onSessionTerminated();
        }

        @Override
        public void onSessionConfigFail(int n) {
            Message message = this.mHandler.obtainMessage(2);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onSessionConfigSuccess() {
            Message message = this.mHandler.obtainMessage(1);
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onSessionStarted(int n) {
            Message message = this.mHandler.obtainMessage(0);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onSessionTerminated(int n) {
            Message message = this.mHandler.obtainMessage(3);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

    }

    private static class WifiAwareEventCallbackProxy
    extends IWifiAwareEventCallback.Stub {
        private static final int CALLBACK_CONNECT_FAIL = 1;
        private static final int CALLBACK_CONNECT_SUCCESS = 0;
        private static final int CALLBACK_IDENTITY_CHANGED = 2;
        private final WeakReference<WifiAwareManager> mAwareManager;
        private final Binder mBinder;
        private final Handler mHandler;
        private final Looper mLooper;

        WifiAwareEventCallbackProxy(WifiAwareManager wifiAwareManager, Looper looper, Binder binder, final AttachCallback attachCallback, final IdentityChangedListener identityChangedListener) {
            this.mAwareManager = new WeakReference<WifiAwareManager>(wifiAwareManager);
            this.mLooper = looper;
            this.mBinder = binder;
            this.mHandler = new Handler(looper){

                @Override
                public void handleMessage(Message message) {
                    Object object = (WifiAwareManager)WifiAwareEventCallbackProxy.this.mAwareManager.get();
                    if (object == null) {
                        Log.w(WifiAwareManager.TAG, "WifiAwareEventCallbackProxy: handleMessage post GC");
                        return;
                    }
                    int n = message.what;
                    if (n != 0) {
                        if (n != 1) {
                            if (n == 2) {
                                object = identityChangedListener;
                                if (object == null) {
                                    Log.e(WifiAwareManager.TAG, "CALLBACK_IDENTITY_CHANGED: null listener.");
                                } else {
                                    ((IdentityChangedListener)object).onIdentityChanged((byte[])message.obj);
                                }
                            }
                        } else {
                            WifiAwareEventCallbackProxy.this.mAwareManager.clear();
                            attachCallback.onAttachFailed();
                        }
                    } else {
                        attachCallback.onAttached(new WifiAwareSession((WifiAwareManager)object, WifiAwareEventCallbackProxy.this.mBinder, message.arg1));
                    }
                }
            };
        }

        @Override
        public void onConnectFail(int n) {
            Message message = this.mHandler.obtainMessage(1);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onConnectSuccess(int n) {
            Message message = this.mHandler.obtainMessage(0);
            message.arg1 = n;
            this.mHandler.sendMessage(message);
        }

        @Override
        public void onIdentityChanged(byte[] arrby) {
            Message message = this.mHandler.obtainMessage(2);
            message.obj = arrby;
            this.mHandler.sendMessage(message);
        }

    }

}

