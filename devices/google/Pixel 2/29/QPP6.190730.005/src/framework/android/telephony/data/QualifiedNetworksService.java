/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.data.-$
 *  android.telephony.data.-$$Lambda
 *  android.telephony.data.-$$Lambda$QualifiedNetworksService
 *  android.telephony.data.-$$Lambda$QualifiedNetworksService$NetworkAvailabilityProvider
 *  android.telephony.data.-$$Lambda$QualifiedNetworksService$NetworkAvailabilityProvider$sNPqwkqArvqymBmHYmxAc4rF5Es
 */
package android.telephony.data;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.data.-$;
import android.telephony.data.IQualifiedNetworksService;
import android.telephony.data.IQualifiedNetworksServiceCallback;
import android.telephony.data._$$Lambda$QualifiedNetworksService$NetworkAvailabilityProvider$sNPqwkqArvqymBmHYmxAc4rF5Es;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SystemApi
public abstract class QualifiedNetworksService
extends Service {
    private static final int QNS_CREATE_NETWORK_AVAILABILITY_PROVIDER = 1;
    private static final int QNS_REMOVE_ALL_NETWORK_AVAILABILITY_PROVIDERS = 3;
    private static final int QNS_REMOVE_NETWORK_AVAILABILITY_PROVIDER = 2;
    private static final int QNS_UPDATE_QUALIFIED_NETWORKS = 4;
    public static final String QUALIFIED_NETWORKS_SERVICE_INTERFACE = "android.telephony.data.QualifiedNetworksService";
    private static final String TAG = QualifiedNetworksService.class.getSimpleName();
    @VisibleForTesting
    public final IQualifiedNetworksServiceWrapper mBinder = new IQualifiedNetworksServiceWrapper();
    private final QualifiedNetworksServiceHandler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);
    private final SparseArray<NetworkAvailabilityProvider> mProviders = new SparseArray();

    public QualifiedNetworksService() {
        this.mHandlerThread.start();
        this.mHandler = new QualifiedNetworksServiceHandler(this.mHandlerThread.getLooper());
        this.log("Qualified networks service created");
    }

    private void log(String string2) {
        Rlog.d(TAG, string2);
    }

    private void loge(String string2) {
        Rlog.e(TAG, string2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (intent != null && QUALIFIED_NETWORKS_SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected intent ");
        stringBuilder.append(intent);
        this.loge(stringBuilder.toString());
        return null;
    }

    public abstract NetworkAvailabilityProvider onCreateNetworkAvailabilityProvider(int var1);

    @Override
    public void onDestroy() {
        this.mHandlerThread.quit();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    private class IQualifiedNetworksServiceWrapper
    extends IQualifiedNetworksService.Stub {
        private IQualifiedNetworksServiceWrapper() {
        }

        @Override
        public void createNetworkAvailabilityProvider(int n, IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            QualifiedNetworksService.this.mHandler.obtainMessage(1, n, 0, iQualifiedNetworksServiceCallback).sendToTarget();
        }

        @Override
        public void removeNetworkAvailabilityProvider(int n) {
            QualifiedNetworksService.this.mHandler.obtainMessage(2, n, 0).sendToTarget();
        }
    }

    public abstract class NetworkAvailabilityProvider
    implements AutoCloseable {
        private IQualifiedNetworksServiceCallback mCallback;
        private SparseArray<int[]> mQualifiedNetworkTypesList = new SparseArray();
        private final int mSlotIndex;

        public NetworkAvailabilityProvider(int n) {
            this.mSlotIndex = n;
        }

        static /* synthetic */ int lambda$updateQualifiedNetworkTypes$0(Integer n) {
            return n;
        }

        private void onUpdateQualifiedNetworkTypes(int n, int[] object) {
            this.mQualifiedNetworkTypesList.put(n, (int[])object);
            IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback = this.mCallback;
            if (iQualifiedNetworksServiceCallback != null) {
                try {
                    iQualifiedNetworksServiceCallback.onQualifiedNetworkTypesChanged(n, (int[])object);
                }
                catch (RemoteException remoteException) {
                    object = QualifiedNetworksService.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to call onQualifiedNetworksChanged. ");
                    stringBuilder.append(remoteException);
                    ((QualifiedNetworksService)object).loge(stringBuilder.toString());
                }
            }
        }

        private void registerForQualifiedNetworkTypesChanged(IQualifiedNetworksServiceCallback iQualifiedNetworksServiceCallback) {
            this.mCallback = iQualifiedNetworksServiceCallback;
            if (this.mCallback != null) {
                for (int i = 0; i < this.mQualifiedNetworkTypesList.size(); ++i) {
                    try {
                        this.mCallback.onQualifiedNetworkTypesChanged(this.mQualifiedNetworkTypesList.keyAt(i), this.mQualifiedNetworkTypesList.valueAt(i));
                        continue;
                    }
                    catch (RemoteException remoteException) {
                        QualifiedNetworksService qualifiedNetworksService = QualifiedNetworksService.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Failed to call onQualifiedNetworksChanged. ");
                        stringBuilder.append(remoteException);
                        qualifiedNetworksService.loge(stringBuilder.toString());
                    }
                }
            }
        }

        @Override
        public abstract void close();

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        public final void updateQualifiedNetworkTypes(int n, List<Integer> arrn) {
            arrn = arrn.stream().mapToInt(_$$Lambda$QualifiedNetworksService$NetworkAvailabilityProvider$sNPqwkqArvqymBmHYmxAc4rF5Es.INSTANCE).toArray();
            QualifiedNetworksService.this.mHandler.obtainMessage(4, this.mSlotIndex, n, arrn).sendToTarget();
        }
    }

    private class QualifiedNetworksServiceHandler
    extends Handler {
        QualifiedNetworksServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).arg1;
            Object object2 = (NetworkAvailabilityProvider)QualifiedNetworksService.this.mProviders.get(n);
            int n2 = ((Message)object).what;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 != 3) {
                        if (n2 == 4 && object2 != null) {
                            ((NetworkAvailabilityProvider)object2).onUpdateQualifiedNetworkTypes(((Message)object).arg2, (int[])((Message)object).obj);
                        }
                    } else {
                        for (n2 = 0; n2 < QualifiedNetworksService.this.mProviders.size(); ++n2) {
                            object = (NetworkAvailabilityProvider)QualifiedNetworksService.this.mProviders.get(n2);
                            if (object == null) continue;
                            ((NetworkAvailabilityProvider)object).close();
                        }
                        QualifiedNetworksService.this.mProviders.clear();
                    }
                } else if (object2 != null) {
                    ((NetworkAvailabilityProvider)object2).close();
                    QualifiedNetworksService.this.mProviders.remove(n);
                }
            } else {
                if (QualifiedNetworksService.this.mProviders.get(n) != null) {
                    object = QualifiedNetworksService.this;
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Network availability provider for slot ");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" already existed.");
                    ((QualifiedNetworksService)object).loge(((StringBuilder)object2).toString());
                    return;
                }
                object2 = QualifiedNetworksService.this.onCreateNetworkAvailabilityProvider(n);
                if (object2 != null) {
                    QualifiedNetworksService.this.mProviders.put(n, object2);
                    ((NetworkAvailabilityProvider)object2).registerForQualifiedNetworkTypesChanged((IQualifiedNetworksServiceCallback)((Message)object).obj);
                } else {
                    object2 = QualifiedNetworksService.this;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to create network availability provider. slot index = ");
                    ((StringBuilder)object).append(n);
                    ((QualifiedNetworksService)object2).loge(((StringBuilder)object).toString());
                }
            }
        }
    }

}

