/*
 * Decompiled with CFR 0.145.
 */
package android.net.nsd;

import android.content.Context;
import android.net.nsd.INsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.AsyncChannel;
import com.android.internal.util.Preconditions;
import java.util.concurrent.CountDownLatch;

public final class NsdManager {
    public static final String ACTION_NSD_STATE_CHANGED = "android.net.nsd.STATE_CHANGED";
    private static final int BASE = 393216;
    private static final boolean DBG = false;
    public static final int DISABLE = 393241;
    public static final int DISCOVER_SERVICES = 393217;
    public static final int DISCOVER_SERVICES_FAILED = 393219;
    public static final int DISCOVER_SERVICES_STARTED = 393218;
    public static final int ENABLE = 393240;
    private static final SparseArray<String> EVENT_NAMES;
    public static final String EXTRA_NSD_STATE = "nsd_state";
    public static final int FAILURE_ALREADY_ACTIVE = 3;
    public static final int FAILURE_INTERNAL_ERROR = 0;
    public static final int FAILURE_MAX_LIMIT = 4;
    private static final int FIRST_LISTENER_KEY = 1;
    public static final int NATIVE_DAEMON_EVENT = 393242;
    public static final int NSD_STATE_DISABLED = 1;
    public static final int NSD_STATE_ENABLED = 2;
    public static final int PROTOCOL_DNS_SD = 1;
    public static final int REGISTER_SERVICE = 393225;
    public static final int REGISTER_SERVICE_FAILED = 393226;
    public static final int REGISTER_SERVICE_SUCCEEDED = 393227;
    public static final int RESOLVE_SERVICE = 393234;
    public static final int RESOLVE_SERVICE_FAILED = 393235;
    public static final int RESOLVE_SERVICE_SUCCEEDED = 393236;
    public static final int SERVICE_FOUND = 393220;
    public static final int SERVICE_LOST = 393221;
    public static final int STOP_DISCOVERY = 393222;
    public static final int STOP_DISCOVERY_FAILED = 393223;
    public static final int STOP_DISCOVERY_SUCCEEDED = 393224;
    private static final String TAG;
    public static final int UNREGISTER_SERVICE = 393228;
    public static final int UNREGISTER_SERVICE_FAILED = 393229;
    public static final int UNREGISTER_SERVICE_SUCCEEDED = 393230;
    private final AsyncChannel mAsyncChannel = new AsyncChannel();
    private final CountDownLatch mConnected = new CountDownLatch(1);
    private final Context mContext;
    private ServiceHandler mHandler;
    private int mListenerKey = 1;
    private final SparseArray mListenerMap = new SparseArray();
    private final Object mMapLock = new Object();
    private final INsdManager mService;
    private final SparseArray<NsdServiceInfo> mServiceMap = new SparseArray();

    static {
        TAG = NsdManager.class.getSimpleName();
        EVENT_NAMES = new SparseArray();
        EVENT_NAMES.put(393217, "DISCOVER_SERVICES");
        EVENT_NAMES.put(393218, "DISCOVER_SERVICES_STARTED");
        EVENT_NAMES.put(393219, "DISCOVER_SERVICES_FAILED");
        EVENT_NAMES.put(393220, "SERVICE_FOUND");
        EVENT_NAMES.put(393221, "SERVICE_LOST");
        EVENT_NAMES.put(393222, "STOP_DISCOVERY");
        EVENT_NAMES.put(393223, "STOP_DISCOVERY_FAILED");
        EVENT_NAMES.put(393224, "STOP_DISCOVERY_SUCCEEDED");
        EVENT_NAMES.put(393225, "REGISTER_SERVICE");
        EVENT_NAMES.put(393226, "REGISTER_SERVICE_FAILED");
        EVENT_NAMES.put(393227, "REGISTER_SERVICE_SUCCEEDED");
        EVENT_NAMES.put(393228, "UNREGISTER_SERVICE");
        EVENT_NAMES.put(393229, "UNREGISTER_SERVICE_FAILED");
        EVENT_NAMES.put(393230, "UNREGISTER_SERVICE_SUCCEEDED");
        EVENT_NAMES.put(393234, "RESOLVE_SERVICE");
        EVENT_NAMES.put(393235, "RESOLVE_SERVICE_FAILED");
        EVENT_NAMES.put(393236, "RESOLVE_SERVICE_SUCCEEDED");
        EVENT_NAMES.put(393240, "ENABLE");
        EVENT_NAMES.put(393241, "DISABLE");
        EVENT_NAMES.put(393242, "NATIVE_DAEMON_EVENT");
    }

    public NsdManager(Context context, INsdManager iNsdManager) {
        this.mService = iNsdManager;
        this.mContext = context;
        this.init();
    }

    private static void checkListener(Object object) {
        Preconditions.checkNotNull(object, "listener cannot be null");
    }

    private static void checkProtocol(int n) {
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        Preconditions.checkArgument(bl, "Unsupported protocol");
    }

    private static void checkServiceInfo(NsdServiceInfo nsdServiceInfo) {
        Preconditions.checkNotNull(nsdServiceInfo, "NsdServiceInfo cannot be null");
        Preconditions.checkStringNotEmpty(nsdServiceInfo.getServiceName(), "Service name cannot be empty");
        Preconditions.checkStringNotEmpty(nsdServiceInfo.getServiceType(), "Service type cannot be empty");
    }

    private static void fatal(String string2) {
        Log.e(TAG, string2);
        throw new RuntimeException(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getListenerKey(Object object) {
        NsdManager.checkListener(object);
        Object object2 = this.mMapLock;
        synchronized (object2) {
            int n = this.mListenerMap.indexOfValue(object);
            boolean bl = n != -1;
            Preconditions.checkArgument(bl, "listener not registered");
            return this.mListenerMap.keyAt(n);
        }
    }

    private Messenger getMessenger() {
        try {
            Messenger messenger = this.mService.getMessenger();
            return messenger;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private static String getNsdServiceInfoType(NsdServiceInfo nsdServiceInfo) {
        if (nsdServiceInfo == null) {
            return "?";
        }
        return nsdServiceInfo.getServiceType();
    }

    private void init() {
        Messenger messenger = this.getMessenger();
        if (messenger == null) {
            NsdManager.fatal("Failed to obtain service Messenger");
        }
        HandlerThread handlerThread = new HandlerThread("NsdManager");
        handlerThread.start();
        this.mHandler = new ServiceHandler(handlerThread.getLooper());
        this.mAsyncChannel.connect(this.mContext, (Handler)this.mHandler, messenger);
        try {
            this.mConnected.await();
        }
        catch (InterruptedException interruptedException) {
            NsdManager.fatal("Interrupted wait at init");
        }
    }

    public static String nameOf(int n) {
        String string2 = EVENT_NAMES.get(n);
        if (string2 == null) {
            return Integer.toString(n);
        }
        return string2;
    }

    private int nextListenerKey() {
        this.mListenerKey = Math.max(1, this.mListenerKey + 1);
        return this.mListenerKey;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int putListener(Object object, NsdServiceInfo nsdServiceInfo) {
        NsdManager.checkListener(object);
        Object object2 = this.mMapLock;
        synchronized (object2) {
            boolean bl = this.mListenerMap.indexOfValue(object) == -1;
            Preconditions.checkArgument(bl, "listener already in use");
            int n = this.nextListenerKey();
            this.mListenerMap.put(n, object);
            this.mServiceMap.put(n, nsdServiceInfo);
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeListener(int n) {
        Object object = this.mMapLock;
        synchronized (object) {
            this.mListenerMap.remove(n);
            this.mServiceMap.remove(n);
            return;
        }
    }

    @VisibleForTesting
    public void disconnect() {
        this.mAsyncChannel.disconnect();
        this.mHandler.getLooper().quitSafely();
    }

    public void discoverServices(String string2, int n, DiscoveryListener discoveryListener) {
        Preconditions.checkStringNotEmpty(string2, "Service type cannot be empty");
        NsdManager.checkProtocol(n);
        NsdServiceInfo nsdServiceInfo = new NsdServiceInfo();
        nsdServiceInfo.setServiceType(string2);
        n = this.putListener(discoveryListener, nsdServiceInfo);
        this.mAsyncChannel.sendMessage(393217, 0, n, nsdServiceInfo);
    }

    public void registerService(NsdServiceInfo nsdServiceInfo, int n, RegistrationListener registrationListener) {
        boolean bl = nsdServiceInfo.getPort() > 0;
        Preconditions.checkArgument(bl, "Invalid port number");
        NsdManager.checkServiceInfo(nsdServiceInfo);
        NsdManager.checkProtocol(n);
        n = this.putListener(registrationListener, nsdServiceInfo);
        this.mAsyncChannel.sendMessage(393225, 0, n, nsdServiceInfo);
    }

    public void resolveService(NsdServiceInfo nsdServiceInfo, ResolveListener resolveListener) {
        NsdManager.checkServiceInfo(nsdServiceInfo);
        int n = this.putListener(resolveListener, nsdServiceInfo);
        this.mAsyncChannel.sendMessage(393234, 0, n, nsdServiceInfo);
    }

    public void setEnabled(boolean bl) {
        try {
            this.mService.setEnabled(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void stopServiceDiscovery(DiscoveryListener discoveryListener) {
        int n = this.getListenerKey(discoveryListener);
        this.mAsyncChannel.sendMessage(393222, 0, n);
    }

    public void unregisterService(RegistrationListener registrationListener) {
        int n = this.getListenerKey(registrationListener);
        this.mAsyncChannel.sendMessage(393228, 0, n);
    }

    public static interface DiscoveryListener {
        public void onDiscoveryStarted(String var1);

        public void onDiscoveryStopped(String var1);

        public void onServiceFound(NsdServiceInfo var1);

        public void onServiceLost(NsdServiceInfo var1);

        public void onStartDiscoveryFailed(String var1, int var2);

        public void onStopDiscoveryFailed(String var1, int var2);
    }

    public static interface RegistrationListener {
        public void onRegistrationFailed(NsdServiceInfo var1, int var2);

        public void onServiceRegistered(NsdServiceInfo var1);

        public void onServiceUnregistered(NsdServiceInfo var1);

        public void onUnregistrationFailed(NsdServiceInfo var1, int var2);
    }

    public static interface ResolveListener {
        public void onResolveFailed(NsdServiceInfo var1, int var2);

        public void onServiceResolved(NsdServiceInfo var1);
    }

    @VisibleForTesting
    class ServiceHandler
    extends Handler {
        ServiceHandler(Looper looper) {
            super(looper);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            int n2 = ((Message)object).arg2;
            if (n == 69632) {
                NsdManager.this.mAsyncChannel.sendMessage(69633);
                return;
            }
            if (n == 69634) {
                NsdManager.this.mConnected.countDown();
                return;
            }
            if (n == 69636) {
                Log.e(TAG, "Channel lost");
                return;
            }
            Object object2 = NsdManager.this.mMapLock;
            // MONITORENTER : object2
            Object object3 = NsdManager.this.mListenerMap.get(n2);
            NsdServiceInfo nsdServiceInfo = (NsdServiceInfo)NsdManager.this.mServiceMap.get(n2);
            // MONITOREXIT : object2
            if (object3 == null) {
                object2 = TAG;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Stale key ");
                ((StringBuilder)object3).append(((Message)object).arg2);
                Log.d((String)object2, ((StringBuilder)object3).toString());
                return;
            }
            switch (n) {
                default: {
                    object2 = TAG;
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Ignored ");
                    ((StringBuilder)object3).append(object);
                    Log.d((String)object2, ((StringBuilder)object3).toString());
                    return;
                }
                case 393236: {
                    NsdManager.this.removeListener(n2);
                    ((ResolveListener)object3).onServiceResolved((NsdServiceInfo)((Message)object).obj);
                    return;
                }
                case 393235: {
                    NsdManager.this.removeListener(n2);
                    ((ResolveListener)object3).onResolveFailed(nsdServiceInfo, ((Message)object).arg1);
                    return;
                }
                case 393230: {
                    NsdManager.this.removeListener(((Message)object).arg2);
                    ((RegistrationListener)object3).onServiceUnregistered(nsdServiceInfo);
                    return;
                }
                case 393229: {
                    NsdManager.this.removeListener(n2);
                    ((RegistrationListener)object3).onUnregistrationFailed(nsdServiceInfo, ((Message)object).arg1);
                    return;
                }
                case 393227: {
                    ((RegistrationListener)object3).onServiceRegistered((NsdServiceInfo)((Message)object).obj);
                    return;
                }
                case 393226: {
                    NsdManager.this.removeListener(n2);
                    ((RegistrationListener)object3).onRegistrationFailed(nsdServiceInfo, ((Message)object).arg1);
                    return;
                }
                case 393224: {
                    NsdManager.this.removeListener(n2);
                    ((DiscoveryListener)object3).onDiscoveryStopped(NsdManager.getNsdServiceInfoType(nsdServiceInfo));
                    return;
                }
                case 393223: {
                    NsdManager.this.removeListener(n2);
                    ((DiscoveryListener)object3).onStopDiscoveryFailed(NsdManager.getNsdServiceInfoType(nsdServiceInfo), ((Message)object).arg1);
                    return;
                }
                case 393221: {
                    ((DiscoveryListener)object3).onServiceLost((NsdServiceInfo)((Message)object).obj);
                    return;
                }
                case 393220: {
                    ((DiscoveryListener)object3).onServiceFound((NsdServiceInfo)((Message)object).obj);
                    return;
                }
                case 393219: {
                    NsdManager.this.removeListener(n2);
                    ((DiscoveryListener)object3).onStartDiscoveryFailed(NsdManager.getNsdServiceInfoType(nsdServiceInfo), ((Message)object).arg1);
                    return;
                }
                case 393218: 
            }
            object = NsdManager.getNsdServiceInfoType((NsdServiceInfo)((Message)object).obj);
            ((DiscoveryListener)object3).onDiscoveryStarted((String)object);
        }
    }

}

