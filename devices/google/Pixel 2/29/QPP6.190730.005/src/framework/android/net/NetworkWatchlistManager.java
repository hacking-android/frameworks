/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.android.internal.net.INetworkWatchlistManager;
import com.android.internal.util.Preconditions;

public class NetworkWatchlistManager {
    private static final String SHARED_MEMORY_TAG = "NETWORK_WATCHLIST_SHARED_MEMORY";
    private static final String TAG = "NetworkWatchlistManager";
    private final Context mContext;
    private final INetworkWatchlistManager mNetworkWatchlistManager;

    public NetworkWatchlistManager(Context context) {
        this.mContext = Preconditions.checkNotNull(context, "missing context");
        this.mNetworkWatchlistManager = INetworkWatchlistManager.Stub.asInterface(ServiceManager.getService("network_watchlist"));
    }

    public NetworkWatchlistManager(Context context, INetworkWatchlistManager iNetworkWatchlistManager) {
        this.mContext = context;
        this.mNetworkWatchlistManager = iNetworkWatchlistManager;
    }

    public byte[] getWatchlistConfigHash() {
        try {
            byte[] arrby = this.mNetworkWatchlistManager.getWatchlistConfigHash();
            return arrby;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to get watchlist config hash");
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reloadWatchlist() {
        try {
            this.mNetworkWatchlistManager.reloadWatchlist();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Unable to reload watchlist");
            remoteException.rethrowFromSystemServer();
        }
    }

    public void reportWatchlistIfNecessary() {
        try {
            this.mNetworkWatchlistManager.reportWatchlistIfNecessary();
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Cannot report records", remoteException);
            remoteException.rethrowFromSystemServer();
        }
    }
}

