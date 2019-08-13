/*
 * Decompiled with CFR 0.145.
 */
package android.app.usage;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.usage.CacheQuotaHint;
import android.app.usage.ICacheQuotaService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.util.Log;
import android.util.Pair;
import java.util.List;

@SystemApi
public abstract class CacheQuotaService
extends Service {
    public static final String REQUEST_LIST_KEY = "requests";
    public static final String SERVICE_INTERFACE = "android.app.usage.CacheQuotaService";
    private static final String TAG = "CacheQuotaService";
    private Handler mHandler;
    private CacheQuotaServiceWrapper mWrapper;

    @Override
    public IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    public abstract List<CacheQuotaHint> onComputeCacheQuotaHints(List<CacheQuotaHint> var1);

    @Override
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new CacheQuotaServiceWrapper();
        this.mHandler = new ServiceHandler(this.getMainLooper());
    }

    private final class CacheQuotaServiceWrapper
    extends ICacheQuotaService.Stub {
        private CacheQuotaServiceWrapper() {
        }

        @Override
        public void computeCacheQuotaHints(RemoteCallback object, List<CacheQuotaHint> list) {
            object = Pair.create(object, list);
            object = CacheQuotaService.this.mHandler.obtainMessage(1, object);
            CacheQuotaService.this.mHandler.sendMessage((Message)object);
        }
    }

    private final class ServiceHandler
    extends Handler {
        public static final int MSG_SEND_LIST = 1;

        public ServiceHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Handling unknown message: ");
                ((StringBuilder)object).append(n);
                Log.w(CacheQuotaService.TAG, ((StringBuilder)object).toString());
            } else {
                Pair pair = (Pair)((Message)object).obj;
                List<CacheQuotaHint> list = CacheQuotaService.this.onComputeCacheQuotaHints((List)pair.second);
                object = new Bundle();
                ((Bundle)object).putParcelableList(CacheQuotaService.REQUEST_LIST_KEY, list);
                ((RemoteCallback)pair.first).sendResult((Bundle)object);
            }
        }
    }

}

