/*
 * Decompiled with CFR 0.145.
 */
package android.service.resolver;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.resolver.IResolverRankerResult;
import android.service.resolver.IResolverRankerService;
import android.service.resolver.ResolverTarget;
import android.util.Log;
import java.util.List;

@SystemApi
public abstract class ResolverRankerService
extends Service {
    public static final String BIND_PERMISSION = "android.permission.BIND_RESOLVER_RANKER_SERVICE";
    private static final boolean DEBUG = false;
    private static final String HANDLER_THREAD_NAME = "RESOLVER_RANKER_SERVICE";
    public static final String HOLD_PERMISSION = "android.permission.PROVIDE_RESOLVER_RANKER_SERVICE";
    public static final String SERVICE_INTERFACE = "android.service.resolver.ResolverRankerService";
    private static final String TAG = "ResolverRankerService";
    private volatile Handler mHandler;
    private HandlerThread mHandlerThread;
    private ResolverRankerServiceWrapper mWrapper = null;

    private static void sendResult(List<ResolverTarget> object, IResolverRankerResult iResolverRankerResult) {
        try {
            iResolverRankerResult.sendResult((List<ResolverTarget>)object);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("failed to send results: ");
            ((StringBuilder)object).append(exception);
            Log.e(TAG, ((StringBuilder)object).toString());
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (!SERVICE_INTERFACE.equals(intent.getAction())) {
            return null;
        }
        if (this.mHandlerThread == null) {
            this.mHandlerThread = new HandlerThread(HANDLER_THREAD_NAME);
            this.mHandlerThread.start();
            this.mHandler = new Handler(this.mHandlerThread.getLooper());
        }
        if (this.mWrapper == null) {
            this.mWrapper = new ResolverRankerServiceWrapper();
        }
        return this.mWrapper;
    }

    @Override
    public void onDestroy() {
        this.mHandler = null;
        HandlerThread handlerThread = this.mHandlerThread;
        if (handlerThread != null) {
            handlerThread.quitSafely();
        }
        super.onDestroy();
    }

    public void onPredictSharingProbabilities(List<ResolverTarget> list) {
    }

    public void onTrainRankingModel(List<ResolverTarget> list, int n) {
    }

    private class ResolverRankerServiceWrapper
    extends IResolverRankerService.Stub {
        private ResolverRankerServiceWrapper() {
        }

        @Override
        public void predict(List<ResolverTarget> object, IResolverRankerResult object2) throws RemoteException {
            object2 = new Runnable((List)object, (IResolverRankerResult)object2){
                final /* synthetic */ IResolverRankerResult val$result;
                final /* synthetic */ List val$targets;
                {
                    this.val$targets = list;
                    this.val$result = iResolverRankerResult;
                }

                @Override
                public void run() {
                    try {
                        ResolverRankerService.this.onPredictSharingProbabilities(this.val$targets);
                        ResolverRankerService.sendResult(this.val$targets, this.val$result);
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onPredictSharingProbabilities failed; send null results: ");
                        stringBuilder.append(exception);
                        Log.e(ResolverRankerService.TAG, stringBuilder.toString());
                        ResolverRankerService.sendResult(null, this.val$result);
                    }
                }
            };
            object = ResolverRankerService.this.mHandler;
            if (object != null) {
                ((Handler)object).post((Runnable)object2);
            }
        }

        @Override
        public void train(List<ResolverTarget> object, int n) throws RemoteException {
            object = new Runnable((List)object, n){
                final /* synthetic */ int val$selectedPosition;
                final /* synthetic */ List val$targets;
                {
                    this.val$targets = list;
                    this.val$selectedPosition = n;
                }

                @Override
                public void run() {
                    try {
                        ResolverRankerService.this.onTrainRankingModel(this.val$targets, this.val$selectedPosition);
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onTrainRankingModel failed; skip train: ");
                        stringBuilder.append(exception);
                        Log.e(ResolverRankerService.TAG, stringBuilder.toString());
                    }
                }
            };
            Handler handler = ResolverRankerService.this.mHandler;
            if (handler != null) {
                handler.post((Runnable)object);
            }
        }

    }

}

