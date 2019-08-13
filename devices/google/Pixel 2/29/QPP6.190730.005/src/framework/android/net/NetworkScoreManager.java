/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.INetworkScoreCache;
import android.net.INetworkScoreService;
import android.net.NetworkKey;
import android.net.NetworkScorerAppData;
import android.net.ScoredNetwork;
import android.os.RemoteException;
import android.os.ServiceManager;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
public class NetworkScoreManager {
    public static final String ACTION_CHANGE_ACTIVE = "android.net.scoring.CHANGE_ACTIVE";
    public static final String ACTION_CUSTOM_ENABLE = "android.net.scoring.CUSTOM_ENABLE";
    public static final String ACTION_RECOMMEND_NETWORKS = "android.net.action.RECOMMEND_NETWORKS";
    public static final String ACTION_SCORER_CHANGED = "android.net.scoring.SCORER_CHANGED";
    public static final String ACTION_SCORE_NETWORKS = "android.net.scoring.SCORE_NETWORKS";
    public static final int CACHE_FILTER_CURRENT_NETWORK = 1;
    public static final int CACHE_FILTER_NONE = 0;
    public static final int CACHE_FILTER_SCAN_RESULTS = 2;
    public static final String EXTRA_NETWORKS_TO_SCORE = "networksToScore";
    public static final String EXTRA_NEW_SCORER = "newScorer";
    public static final String EXTRA_PACKAGE_NAME = "packageName";
    public static final String NETWORK_AVAILABLE_NOTIFICATION_CHANNEL_ID_META_DATA = "android.net.wifi.notification_channel_id_network_available";
    public static final int RECOMMENDATIONS_ENABLED_FORCED_OFF = -1;
    public static final int RECOMMENDATIONS_ENABLED_OFF = 0;
    public static final int RECOMMENDATIONS_ENABLED_ON = 1;
    public static final String RECOMMENDATION_SERVICE_LABEL_META_DATA = "android.net.scoring.recommendation_service_label";
    public static final String USE_OPEN_WIFI_PACKAGE_META_DATA = "android.net.wifi.use_open_wifi_package";
    private final Context mContext;
    private final INetworkScoreService mService;

    public NetworkScoreManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = INetworkScoreService.Stub.asInterface(ServiceManager.getServiceOrThrow("network_score"));
    }

    public boolean clearScores() throws SecurityException {
        try {
            boolean bl = this.mService.clearScores();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableScoring() throws SecurityException {
        try {
            this.mService.disableScoring();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public NetworkScorerAppData getActiveScorer() {
        try {
            NetworkScorerAppData networkScorerAppData = this.mService.getActiveScorer();
            return networkScorerAppData;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getActiveScorerPackage() {
        try {
            String string2 = this.mService.getActiveScorerPackage();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<NetworkScorerAppData> getAllValidScorers() {
        try {
            List<NetworkScorerAppData> list = this.mService.getAllValidScorers();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isCallerActiveScorer(int n) {
        try {
            boolean bl = this.mService.isCallerActiveScorer(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void registerNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache) {
        this.registerNetworkScoreCache(n, iNetworkScoreCache, 0);
    }

    public void registerNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache, int n2) {
        try {
            this.mService.registerNetworkScoreCache(n, iNetworkScoreCache, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean requestScores(NetworkKey[] arrnetworkKey) throws SecurityException {
        try {
            boolean bl = this.mService.requestScores(arrnetworkKey);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean setActiveScorer(String string2) throws SecurityException {
        try {
            boolean bl = this.mService.setActiveScorer(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void unregisterNetworkScoreCache(int n, INetworkScoreCache iNetworkScoreCache) {
        try {
            this.mService.unregisterNetworkScoreCache(n, iNetworkScoreCache);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean updateScores(ScoredNetwork[] arrscoredNetwork) throws SecurityException {
        try {
            boolean bl = this.mService.updateScores(arrscoredNetwork);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CacheUpdateFilter {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RecommendationsEnabledSetting {
    }

}

