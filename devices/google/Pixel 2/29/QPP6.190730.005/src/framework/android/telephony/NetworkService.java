/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.INetworkService;
import android.telephony.INetworkServiceCallback;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.NetworkServiceCallback;
import android.telephony.Rlog;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import java.util.ArrayList;
import java.util.List;

@SystemApi
public abstract class NetworkService
extends Service {
    private static final int NETWORK_SERVICE_CREATE_NETWORK_SERVICE_PROVIDER = 1;
    private static final int NETWORK_SERVICE_GET_REGISTRATION_INFO = 4;
    private static final int NETWORK_SERVICE_INDICATION_NETWORK_INFO_CHANGED = 7;
    private static final int NETWORK_SERVICE_REGISTER_FOR_INFO_CHANGE = 5;
    private static final int NETWORK_SERVICE_REMOVE_ALL_NETWORK_SERVICE_PROVIDERS = 3;
    private static final int NETWORK_SERVICE_REMOVE_NETWORK_SERVICE_PROVIDER = 2;
    private static final int NETWORK_SERVICE_UNREGISTER_FOR_INFO_CHANGE = 6;
    public static final String SERVICE_INTERFACE = "android.telephony.NetworkService";
    private final String TAG = NetworkService.class.getSimpleName();
    @VisibleForTesting
    public final INetworkServiceWrapper mBinder = new INetworkServiceWrapper();
    private final NetworkServiceHandler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread(this.TAG);
    private final SparseArray<NetworkServiceProvider> mServiceMap = new SparseArray();

    public NetworkService() {
        this.mHandlerThread.start();
        this.mHandler = new NetworkServiceHandler(this.mHandlerThread.getLooper());
        this.log("network service created");
    }

    private final void log(String string2) {
        Rlog.d(this.TAG, string2);
    }

    private final void loge(String string2) {
        Rlog.e(this.TAG, string2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null && SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected intent ");
        stringBuilder.append(intent);
        this.loge(stringBuilder.toString());
        return null;
    }

    public abstract NetworkServiceProvider onCreateNetworkServiceProvider(int var1);

    @Override
    public void onDestroy() {
        this.mHandlerThread.quit();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3, 0, 0, null).sendToTarget();
        return false;
    }

    private class INetworkServiceWrapper
    extends INetworkService.Stub {
        private INetworkServiceWrapper() {
        }

        @Override
        public void createNetworkServiceProvider(int n) {
            NetworkService.this.mHandler.obtainMessage(1, n, 0, null).sendToTarget();
        }

        @Override
        public void registerForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(5, n, 0, iNetworkServiceCallback).sendToTarget();
        }

        @Override
        public void removeNetworkServiceProvider(int n) {
            NetworkService.this.mHandler.obtainMessage(2, n, 0, null).sendToTarget();
        }

        @Override
        public void requestNetworkRegistrationInfo(int n, int n2, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(4, n, n2, iNetworkServiceCallback).sendToTarget();
        }

        @Override
        public void unregisterForNetworkRegistrationInfoChanged(int n, INetworkServiceCallback iNetworkServiceCallback) {
            NetworkService.this.mHandler.obtainMessage(6, n, 0, iNetworkServiceCallback).sendToTarget();
        }
    }

    private class NetworkServiceHandler
    extends Handler {
        NetworkServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).arg1;
            INetworkServiceCallback iNetworkServiceCallback = (INetworkServiceCallback)((Message)object).obj;
            NetworkServiceProvider networkServiceProvider = (NetworkServiceProvider)NetworkService.this.mServiceMap.get(n);
            switch (((Message)object).what) {
                default: {
                    break;
                }
                case 7: {
                    if (networkServiceProvider == null) break;
                    networkServiceProvider.notifyInfoChangedToCallbacks();
                    break;
                }
                case 6: {
                    if (networkServiceProvider == null) break;
                    networkServiceProvider.unregisterForInfoChanged(iNetworkServiceCallback);
                    break;
                }
                case 5: {
                    if (networkServiceProvider == null) break;
                    networkServiceProvider.registerForInfoChanged(iNetworkServiceCallback);
                    break;
                }
                case 4: {
                    if (networkServiceProvider == null) break;
                    networkServiceProvider.requestNetworkRegistrationInfo(((Message)object).arg2, new NetworkServiceCallback(iNetworkServiceCallback));
                    break;
                }
                case 3: {
                    for (n = 0; n < NetworkService.this.mServiceMap.size(); ++n) {
                        object = (NetworkServiceProvider)NetworkService.this.mServiceMap.get(n);
                        if (object == null) continue;
                        ((NetworkServiceProvider)object).close();
                    }
                    NetworkService.this.mServiceMap.clear();
                    break;
                }
                case 2: {
                    if (networkServiceProvider == null) break;
                    networkServiceProvider.close();
                    NetworkService.this.mServiceMap.remove(n);
                    break;
                }
                case 1: {
                    if (networkServiceProvider != null) break;
                    NetworkService.this.mServiceMap.put(n, NetworkService.this.onCreateNetworkServiceProvider(n));
                }
            }
        }
    }

    public abstract class NetworkServiceProvider
    implements AutoCloseable {
        private final List<INetworkServiceCallback> mNetworkRegistrationInfoChangedCallbacks = new ArrayList<INetworkServiceCallback>();
        private final int mSlotIndex;

        public NetworkServiceProvider(int n) {
            this.mSlotIndex = n;
        }

        private void notifyInfoChangedToCallbacks() {
            for (INetworkServiceCallback iNetworkServiceCallback : this.mNetworkRegistrationInfoChangedCallbacks) {
                try {
                    iNetworkServiceCallback.onNetworkStateChanged();
                }
                catch (RemoteException remoteException) {}
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void registerForInfoChanged(INetworkServiceCallback iNetworkServiceCallback) {
            List<INetworkServiceCallback> list = this.mNetworkRegistrationInfoChangedCallbacks;
            synchronized (list) {
                this.mNetworkRegistrationInfoChangedCallbacks.add(iNetworkServiceCallback);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void unregisterForInfoChanged(INetworkServiceCallback iNetworkServiceCallback) {
            List<INetworkServiceCallback> list = this.mNetworkRegistrationInfoChangedCallbacks;
            synchronized (list) {
                this.mNetworkRegistrationInfoChangedCallbacks.remove(iNetworkServiceCallback);
                return;
            }
        }

        @Override
        public abstract void close();

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public final void notifyNetworkRegistrationInfoChanged() {
            NetworkService.this.mHandler.obtainMessage(7, this.mSlotIndex, 0, null).sendToTarget();
        }

        public void requestNetworkRegistrationInfo(int n, NetworkServiceCallback networkServiceCallback) {
            networkServiceCallback.onRequestNetworkRegistrationInfoComplete(1, null);
        }
    }

}

