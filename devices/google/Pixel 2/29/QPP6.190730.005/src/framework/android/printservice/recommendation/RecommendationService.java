/*
 * Decompiled with CFR 0.145.
 */
package android.printservice.recommendation;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.printservice.recommendation.IRecommendationService;
import android.printservice.recommendation.IRecommendationServiceCallbacks;
import android.printservice.recommendation.RecommendationInfo;
import android.util.Log;
import java.util.List;

@SystemApi
public abstract class RecommendationService
extends Service {
    private static final String LOG_TAG = "PrintServiceRecS";
    public static final String SERVICE_INTERFACE = "android.printservice.recommendation.RecommendationService";
    private IRecommendationServiceCallbacks mCallbacks;
    private Handler mHandler;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        this.mHandler = new MyHandler();
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return new IRecommendationService.Stub(){

            @Override
            public void registerCallbacks(IRecommendationServiceCallbacks iRecommendationServiceCallbacks) {
                if (iRecommendationServiceCallbacks != null) {
                    RecommendationService.this.mHandler.obtainMessage(1, iRecommendationServiceCallbacks).sendToTarget();
                } else {
                    RecommendationService.this.mHandler.obtainMessage(2).sendToTarget();
                }
            }
        };
    }

    public abstract void onConnected();

    public abstract void onDisconnected();

    public final void updateRecommendations(List<RecommendationInfo> list) {
        this.mHandler.obtainMessage(3, list).sendToTarget();
    }

    private class MyHandler
    extends Handler {
        static final int MSG_CONNECT = 1;
        static final int MSG_DISCONNECT = 2;
        static final int MSG_UPDATE = 3;

        MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n == 3 && RecommendationService.this.mCallbacks != null) {
                        try {
                            RecommendationService.this.mCallbacks.onRecommendationsUpdated((List)message.obj);
                        }
                        catch (RemoteException | NullPointerException exception) {
                            Log.e(RecommendationService.LOG_TAG, "Could not update recommended services", exception);
                        }
                    }
                } else {
                    RecommendationService.this.onDisconnected();
                    RecommendationService.this.mCallbacks = null;
                }
            } else {
                RecommendationService.this.mCallbacks = (IRecommendationServiceCallbacks)message.obj;
                RecommendationService.this.onConnected();
            }
        }
    }

}

