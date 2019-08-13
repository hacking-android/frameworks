/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.data;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.net.LinkProperties;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.telephony.data.DataCallResponse;
import android.telephony.data.DataProfile;
import android.telephony.data.DataServiceCallback;
import android.telephony.data.IDataService;
import android.telephony.data.IDataServiceCallback;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SystemApi
public abstract class DataService
extends Service {
    private static final int DATA_SERVICE_CREATE_DATA_SERVICE_PROVIDER = 1;
    private static final int DATA_SERVICE_INDICATION_DATA_CALL_LIST_CHANGED = 11;
    private static final int DATA_SERVICE_REMOVE_ALL_DATA_SERVICE_PROVIDERS = 3;
    private static final int DATA_SERVICE_REMOVE_DATA_SERVICE_PROVIDER = 2;
    private static final int DATA_SERVICE_REQUEST_DEACTIVATE_DATA_CALL = 5;
    private static final int DATA_SERVICE_REQUEST_REGISTER_DATA_CALL_LIST_CHANGED = 9;
    private static final int DATA_SERVICE_REQUEST_REQUEST_DATA_CALL_LIST = 8;
    private static final int DATA_SERVICE_REQUEST_SETUP_DATA_CALL = 4;
    private static final int DATA_SERVICE_REQUEST_SET_DATA_PROFILE = 7;
    private static final int DATA_SERVICE_REQUEST_SET_INITIAL_ATTACH_APN = 6;
    private static final int DATA_SERVICE_REQUEST_UNREGISTER_DATA_CALL_LIST_CHANGED = 10;
    public static final int REQUEST_REASON_HANDOVER = 3;
    public static final int REQUEST_REASON_NORMAL = 1;
    public static final int REQUEST_REASON_SHUTDOWN = 2;
    public static final int REQUEST_REASON_UNKNOWN = 0;
    public static final String SERVICE_INTERFACE = "android.telephony.data.DataService";
    private static final String TAG = DataService.class.getSimpleName();
    @VisibleForTesting
    public final IDataServiceWrapper mBinder = new IDataServiceWrapper();
    private final DataServiceHandler mHandler;
    private final HandlerThread mHandlerThread = new HandlerThread(TAG);
    private final SparseArray<DataServiceProvider> mServiceMap = new SparseArray();

    public DataService() {
        this.mHandlerThread.start();
        this.mHandler = new DataServiceHandler(this.mHandlerThread.getLooper());
        this.log("Data service created");
    }

    private void log(String string2) {
        Rlog.d(TAG, string2);
    }

    private void loge(String string2) {
        Rlog.e(TAG, string2);
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

    public abstract DataServiceProvider onCreateDataServiceProvider(int var1);

    @Override
    public void onDestroy() {
        this.mHandlerThread.quit();
        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.mHandler.obtainMessage(3).sendToTarget();
        return false;
    }

    private static final class DataCallListChangedIndication {
        public final IDataServiceCallback callback;
        public final List<DataCallResponse> dataCallList;

        DataCallListChangedIndication(List<DataCallResponse> list, IDataServiceCallback iDataServiceCallback) {
            this.dataCallList = list;
            this.callback = iDataServiceCallback;
        }
    }

    private class DataServiceHandler
    extends Handler {
        DataServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).arg1;
            DataServiceProvider dataServiceProvider = (DataServiceProvider)DataService.this.mServiceMap.get(n);
            int n2 = ((Message)object).what;
            Object object2 = null;
            Object var6_7 = null;
            Object object3 = null;
            switch (n2) {
                default: {
                    break;
                }
                case 11: {
                    if (dataServiceProvider == null) break;
                    object = (DataCallListChangedIndication)((Message)object).obj;
                    try {
                        ((DataCallListChangedIndication)object).callback.onDataCallListChanged(((DataCallListChangedIndication)object).dataCallList);
                    }
                    catch (RemoteException remoteException) {
                        object2 = DataService.this;
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Failed to call onDataCallListChanged. ");
                        ((StringBuilder)object3).append(remoteException);
                        ((DataService)object2).loge(((StringBuilder)object3).toString());
                    }
                    break;
                }
                case 10: {
                    if (dataServiceProvider == null) break;
                    dataServiceProvider.unregisterForDataCallListChanged((IDataServiceCallback)((Message)object).obj);
                    break;
                }
                case 9: {
                    if (dataServiceProvider == null) break;
                    dataServiceProvider.registerForDataCallListChanged((IDataServiceCallback)((Message)object).obj);
                    break;
                }
                case 8: {
                    if (dataServiceProvider == null) break;
                    dataServiceProvider.requestDataCallList(new DataServiceCallback((IDataServiceCallback)((Message)object).obj));
                    break;
                }
                case 7: {
                    if (dataServiceProvider == null) break;
                    object = (SetDataProfileRequest)((Message)object).obj;
                    object2 = ((SetDataProfileRequest)object).dps;
                    boolean bl = ((SetDataProfileRequest)object).isRoaming;
                    object = ((SetDataProfileRequest)object).callback != null ? new DataServiceCallback(((SetDataProfileRequest)object).callback) : object3;
                    dataServiceProvider.setDataProfile((List<DataProfile>)object2, bl, (DataServiceCallback)object);
                    break;
                }
                case 6: {
                    if (dataServiceProvider == null) break;
                    object = (SetInitialAttachApnRequest)((Message)object).obj;
                    object3 = ((SetInitialAttachApnRequest)object).dataProfile;
                    boolean bl = ((SetInitialAttachApnRequest)object).isRoaming;
                    object = ((SetInitialAttachApnRequest)object).callback != null ? new DataServiceCallback(((SetInitialAttachApnRequest)object).callback) : object2;
                    dataServiceProvider.setInitialAttachApn((DataProfile)object3, bl, (DataServiceCallback)object);
                    break;
                }
                case 5: {
                    if (dataServiceProvider == null) break;
                    object = (DeactivateDataCallRequest)((Message)object).obj;
                    n2 = ((DeactivateDataCallRequest)object).cid;
                    n = ((DeactivateDataCallRequest)object).reason;
                    object = ((DeactivateDataCallRequest)object).callback != null ? new DataServiceCallback(((DeactivateDataCallRequest)object).callback) : var6_7;
                    dataServiceProvider.deactivateDataCall(n2, n, (DataServiceCallback)object);
                    break;
                }
                case 4: {
                    if (dataServiceProvider == null) break;
                    object = (SetupDataCallRequest)((Message)object).obj;
                    n = ((SetupDataCallRequest)object).accessNetworkType;
                    object2 = ((SetupDataCallRequest)object).dataProfile;
                    boolean bl = ((SetupDataCallRequest)object).isRoaming;
                    boolean bl2 = ((SetupDataCallRequest)object).allowRoaming;
                    n2 = ((SetupDataCallRequest)object).reason;
                    object3 = ((SetupDataCallRequest)object).linkProperties;
                    object = ((SetupDataCallRequest)object).callback != null ? new DataServiceCallback(((SetupDataCallRequest)object).callback) : null;
                    dataServiceProvider.setupDataCall(n, (DataProfile)object2, bl, bl2, n2, (LinkProperties)object3, (DataServiceCallback)object);
                    break;
                }
                case 3: {
                    for (n2 = 0; n2 < DataService.this.mServiceMap.size(); ++n2) {
                        object = (DataServiceProvider)DataService.this.mServiceMap.get(n2);
                        if (object == null) continue;
                        ((DataServiceProvider)object).close();
                    }
                    DataService.this.mServiceMap.clear();
                    break;
                }
                case 2: {
                    if (dataServiceProvider == null) break;
                    dataServiceProvider.close();
                    DataService.this.mServiceMap.remove(n);
                    break;
                }
                case 1: {
                    object = DataService.this.onCreateDataServiceProvider(((Message)object).arg1);
                    if (object == null) break;
                    DataService.this.mServiceMap.put(n, object);
                }
            }
        }
    }

    public abstract class DataServiceProvider
    implements AutoCloseable {
        private final List<IDataServiceCallback> mDataCallListChangedCallbacks = new ArrayList<IDataServiceCallback>();
        private final int mSlotIndex;

        public DataServiceProvider(int n) {
            this.mSlotIndex = n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void registerForDataCallListChanged(IDataServiceCallback iDataServiceCallback) {
            List<IDataServiceCallback> list = this.mDataCallListChangedCallbacks;
            synchronized (list) {
                this.mDataCallListChangedCallbacks.add(iDataServiceCallback);
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void unregisterForDataCallListChanged(IDataServiceCallback iDataServiceCallback) {
            List<IDataServiceCallback> list = this.mDataCallListChangedCallbacks;
            synchronized (list) {
                this.mDataCallListChangedCallbacks.remove(iDataServiceCallback);
                return;
            }
        }

        @Override
        public abstract void close();

        public void deactivateDataCall(int n, int n2, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onDeactivateDataCallComplete(1);
            }
        }

        public final int getSlotIndex() {
            return this.mSlotIndex;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public final void notifyDataCallListChanged(List<DataCallResponse> list) {
            List<IDataServiceCallback> list2 = this.mDataCallListChangedCallbacks;
            synchronized (list2) {
                Iterator<IDataServiceCallback> iterator = this.mDataCallListChangedCallbacks.iterator();
                while (iterator.hasNext()) {
                    IDataServiceCallback iDataServiceCallback = iterator.next();
                    DataServiceHandler dataServiceHandler = DataService.this.mHandler;
                    int n = this.mSlotIndex;
                    DataCallListChangedIndication dataCallListChangedIndication = new DataCallListChangedIndication(list, iDataServiceCallback);
                    dataServiceHandler.obtainMessage(11, n, 0, dataCallListChangedIndication).sendToTarget();
                }
                return;
            }
        }

        public void requestDataCallList(DataServiceCallback dataServiceCallback) {
            dataServiceCallback.onRequestDataCallListComplete(1, null);
        }

        public void setDataProfile(List<DataProfile> list, boolean bl, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetDataProfileComplete(1);
            }
        }

        public void setInitialAttachApn(DataProfile dataProfile, boolean bl, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetInitialAttachApnComplete(1);
            }
        }

        public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, DataServiceCallback dataServiceCallback) {
            if (dataServiceCallback != null) {
                dataServiceCallback.onSetupDataCallComplete(1, null);
            }
        }
    }

    private static final class DeactivateDataCallRequest {
        public final IDataServiceCallback callback;
        public final int cid;
        public final int reason;

        DeactivateDataCallRequest(int n, int n2, IDataServiceCallback iDataServiceCallback) {
            this.cid = n;
            this.reason = n2;
            this.callback = iDataServiceCallback;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DeactivateDataReason {
    }

    private class IDataServiceWrapper
    extends IDataService.Stub {
        private IDataServiceWrapper() {
        }

        @Override
        public void createDataServiceProvider(int n) {
            DataService.this.mHandler.obtainMessage(1, n, 0).sendToTarget();
        }

        @Override
        public void deactivateDataCall(int n, int n2, int n3, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(5, n, 0, new DeactivateDataCallRequest(n2, n3, iDataServiceCallback)).sendToTarget();
        }

        @Override
        public void registerForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("registerForDataCallListChanged: callback is null");
                return;
            }
            DataService.this.mHandler.obtainMessage(9, n, 0, iDataServiceCallback).sendToTarget();
        }

        @Override
        public void removeDataServiceProvider(int n) {
            DataService.this.mHandler.obtainMessage(2, n, 0).sendToTarget();
        }

        @Override
        public void requestDataCallList(int n, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("requestDataCallList: callback is null");
                return;
            }
            DataService.this.mHandler.obtainMessage(8, n, 0, iDataServiceCallback).sendToTarget();
        }

        @Override
        public void setDataProfile(int n, List<DataProfile> list, boolean bl, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(7, n, 0, new SetDataProfileRequest(list, bl, iDataServiceCallback)).sendToTarget();
        }

        @Override
        public void setInitialAttachApn(int n, DataProfile dataProfile, boolean bl, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(6, n, 0, new SetInitialAttachApnRequest(dataProfile, bl, iDataServiceCallback)).sendToTarget();
        }

        @Override
        public void setupDataCall(int n, int n2, DataProfile dataProfile, boolean bl, boolean bl2, int n3, LinkProperties linkProperties, IDataServiceCallback iDataServiceCallback) {
            DataService.this.mHandler.obtainMessage(4, n, 0, new SetupDataCallRequest(n2, dataProfile, bl, bl2, n3, linkProperties, iDataServiceCallback)).sendToTarget();
        }

        @Override
        public void unregisterForDataCallListChanged(int n, IDataServiceCallback iDataServiceCallback) {
            if (iDataServiceCallback == null) {
                DataService.this.loge("unregisterForDataCallListChanged: callback is null");
                return;
            }
            DataService.this.mHandler.obtainMessage(10, n, 0, iDataServiceCallback).sendToTarget();
        }
    }

    private static final class SetDataProfileRequest {
        public final IDataServiceCallback callback;
        public final List<DataProfile> dps;
        public final boolean isRoaming;

        SetDataProfileRequest(List<DataProfile> list, boolean bl, IDataServiceCallback iDataServiceCallback) {
            this.dps = list;
            this.isRoaming = bl;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetInitialAttachApnRequest {
        public final IDataServiceCallback callback;
        public final DataProfile dataProfile;
        public final boolean isRoaming;

        SetInitialAttachApnRequest(DataProfile dataProfile, boolean bl, IDataServiceCallback iDataServiceCallback) {
            this.dataProfile = dataProfile;
            this.isRoaming = bl;
            this.callback = iDataServiceCallback;
        }
    }

    private static final class SetupDataCallRequest {
        public final int accessNetworkType;
        public final boolean allowRoaming;
        public final IDataServiceCallback callback;
        public final DataProfile dataProfile;
        public final boolean isRoaming;
        public final LinkProperties linkProperties;
        public final int reason;

        SetupDataCallRequest(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, IDataServiceCallback iDataServiceCallback) {
            this.accessNetworkType = n;
            this.dataProfile = dataProfile;
            this.isRoaming = bl;
            this.allowRoaming = bl2;
            this.linkProperties = linkProperties;
            this.reason = n2;
            this.callback = iDataServiceCallback;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SetupDataReason {
    }

}

