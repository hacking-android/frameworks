/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.INetworkRecommendationProvider;
import android.net.NetworkKey;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.concurrent.Executor;

@SystemApi
public abstract class NetworkRecommendationProvider {
    private static final String TAG = "NetworkRecProvider";
    private static final boolean VERBOSE;
    private final IBinder mService;

    static {
        boolean bl = Build.IS_DEBUGGABLE && Log.isLoggable(TAG, 2);
        VERBOSE = bl;
    }

    public NetworkRecommendationProvider(Context context, Executor executor) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(executor);
        this.mService = new ServiceWrapper(context, executor);
    }

    public final IBinder getBinder() {
        return this.mService;
    }

    public abstract void onRequestScores(NetworkKey[] var1);

    private final class ServiceWrapper
    extends INetworkRecommendationProvider.Stub {
        private final Context mContext;
        private final Executor mExecutor;
        private final Handler mHandler;

        ServiceWrapper(Context context, Executor executor) {
            this.mContext = context;
            this.mExecutor = executor;
            this.mHandler = null;
        }

        private void enforceCallingPermission() {
            Context context = this.mContext;
            if (context != null) {
                context.enforceCallingOrSelfPermission("android.permission.REQUEST_NETWORK_SCORES", "Permission denied.");
            }
        }

        private void execute(Runnable runnable) {
            Executor executor = this.mExecutor;
            if (executor != null) {
                executor.execute(runnable);
            } else {
                this.mHandler.post(runnable);
            }
        }

        @Override
        public void requestScores(final NetworkKey[] arrnetworkKey) throws RemoteException {
            this.enforceCallingPermission();
            if (arrnetworkKey != null && arrnetworkKey.length > 0) {
                this.execute(new Runnable(){

                    @Override
                    public void run() {
                        NetworkRecommendationProvider.this.onRequestScores(arrnetworkKey);
                    }
                });
            }
        }

    }

}

