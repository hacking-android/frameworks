/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.content.Context;
import android.net.INetworkScoreCache;
import android.net.NetworkKey;
import android.net.RssiCurve;
import android.net.ScoredNetwork;
import android.net.WifiKey;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Parcelable;
import android.os.Process;
import android.util.Log;
import android.util.LruCache;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WifiNetworkScoreCache
extends INetworkScoreCache.Stub {
    private static final boolean DBG = Log.isLoggable("WifiNetworkScoreCache", 3);
    private static final int DEFAULT_MAX_CACHE_SIZE = 100;
    public static final int INVALID_NETWORK_SCORE = -128;
    private static final String TAG = "WifiNetworkScoreCache";
    @GuardedBy(value={"mLock"})
    private final LruCache<String, ScoredNetwork> mCache;
    private final Context mContext;
    @GuardedBy(value={"mLock"})
    private CacheListener mListener;
    private final Object mLock = new Object();

    public WifiNetworkScoreCache(Context context) {
        this(context, null);
    }

    public WifiNetworkScoreCache(Context context, CacheListener cacheListener) {
        this(context, cacheListener, 100);
    }

    public WifiNetworkScoreCache(Context context, CacheListener cacheListener, int n) {
        this.mContext = context.getApplicationContext();
        this.mListener = cacheListener;
        this.mCache = new LruCache(n);
    }

    private String buildNetworkKey(NetworkKey networkKey) {
        if (networkKey == null) {
            return null;
        }
        if (networkKey.wifiKey == null) {
            return null;
        }
        if (networkKey.type == 1) {
            String string2 = networkKey.wifiKey.ssid;
            if (string2 == null) {
                return null;
            }
            CharSequence charSequence = string2;
            if (networkKey.wifiKey.bssid != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(networkKey.wifiKey.bssid);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            return charSequence;
        }
        return null;
    }

    private String buildNetworkKey(ScoredNetwork scoredNetwork) {
        if (scoredNetwork == null) {
            return null;
        }
        return this.buildNetworkKey(scoredNetwork.networkKey);
    }

    private String buildNetworkKey(ScanResult scanResult) {
        if (scanResult != null && scanResult.SSID != null) {
            StringBuilder stringBuilder = new StringBuilder("\"");
            stringBuilder.append(scanResult.SSID);
            stringBuilder.append("\"");
            if (scanResult.BSSID != null) {
                stringBuilder.append(scanResult.BSSID);
            }
            return stringBuilder.toString();
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void clearScores() {
        Object object = this.mLock;
        synchronized (object) {
            this.mCache.evictAll();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void dump(FileDescriptor object, PrintWriter printWriter, String[] object2) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", TAG);
        printWriter.println(String.format("WifiNetworkScoreCache (%s/%d)", this.mContext.getPackageName(), Process.myUid()));
        printWriter.println("  All score curves:");
        object = this.mLock;
        synchronized (object) {
            Object object32;
            for (Object object32 : this.mCache.snapshot().values()) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("    ");
                ((StringBuilder)object2).append(object32);
                printWriter.println(((StringBuilder)object2).toString());
            }
            printWriter.println("  Network scores for latest ScanResults:");
            object32 = ((WifiManager)this.mContext.getSystemService("wifi")).getScanResults().iterator();
            while (object32.hasNext()) {
                object2 = (ScanResult)object32.next();
                Object object4 = new StringBuilder();
                ((StringBuilder)object4).append("    ");
                ((StringBuilder)object4).append(this.buildNetworkKey((ScanResult)object2));
                ((StringBuilder)object4).append(": ");
                ((StringBuilder)object4).append(this.getNetworkScore((ScanResult)object2));
                printWriter.println(((StringBuilder)object4).toString());
            }
            return;
        }
    }

    public boolean getMeteredHint(ScanResult parcelable) {
        boolean bl = (parcelable = this.getScoredNetwork((ScanResult)parcelable)) != null && ((ScoredNetwork)parcelable).meteredHint;
        return bl;
    }

    public int getNetworkScore(ScanResult scanResult) {
        int n = -128;
        ScoredNetwork scoredNetwork = this.getScoredNetwork(scanResult);
        int n2 = n;
        if (scoredNetwork != null) {
            n2 = n;
            if (scoredNetwork.rssiCurve != null) {
                n2 = n = (int)scoredNetwork.rssiCurve.lookupScore(scanResult.level);
                if (DBG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getNetworkScore found scored network ");
                    stringBuilder.append(scoredNetwork.networkKey);
                    stringBuilder.append(" score ");
                    stringBuilder.append(Integer.toString(n));
                    stringBuilder.append(" RSSI ");
                    stringBuilder.append(scanResult.level);
                    Log.d(TAG, stringBuilder.toString());
                    n2 = n;
                }
            }
        }
        return n2;
    }

    public int getNetworkScore(ScanResult scanResult, boolean bl) {
        int n = -128;
        ScoredNetwork scoredNetwork = this.getScoredNetwork(scanResult);
        int n2 = n;
        if (scoredNetwork != null) {
            n2 = n;
            if (scoredNetwork.rssiCurve != null) {
                n2 = n = (int)scoredNetwork.rssiCurve.lookupScore(scanResult.level, bl);
                if (DBG) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("getNetworkScore found scored network ");
                    stringBuilder.append(scoredNetwork.networkKey);
                    stringBuilder.append(" score ");
                    stringBuilder.append(Integer.toString(n));
                    stringBuilder.append(" RSSI ");
                    stringBuilder.append(scanResult.level);
                    stringBuilder.append(" isActiveNetwork ");
                    stringBuilder.append(bl);
                    Log.d(TAG, stringBuilder.toString());
                    n2 = n;
                }
            }
        }
        return n2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ScoredNetwork getScoredNetwork(NetworkKey object) {
        Object object2 = this.buildNetworkKey((NetworkKey)object);
        if (object2 != null) {
            object = this.mLock;
            synchronized (object) {
                return this.mCache.get((String)object2);
            }
        }
        if (!DBG) return null;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Could not build key string for Network Key: ");
        ((StringBuilder)object2).append(object);
        Log.d(TAG, ((StringBuilder)object2).toString());
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ScoredNetwork getScoredNetwork(ScanResult object) {
        String string2 = this.buildNetworkKey((ScanResult)object);
        if (string2 == null) {
            return null;
        }
        object = this.mLock;
        synchronized (object) {
            return this.mCache.get(string2);
        }
    }

    public boolean hasScoreCurve(ScanResult parcelable) {
        boolean bl = (parcelable = this.getScoredNetwork((ScanResult)parcelable)) != null && ((ScoredNetwork)parcelable).rssiCurve != null;
        return bl;
    }

    public boolean isScoredNetwork(ScanResult scanResult) {
        boolean bl = this.getScoredNetwork(scanResult) != null;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerListener(CacheListener cacheListener) {
        Object object = this.mLock;
        synchronized (object) {
            this.mListener = cacheListener;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterListener() {
        Object object = this.mLock;
        synchronized (object) {
            this.mListener = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void updateScores(List<ScoredNetwork> list) {
        Object object;
        if (list == null) return;
        if (list.isEmpty()) {
            return;
        }
        if (DBG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("updateScores list size=");
            ((StringBuilder)object).append(list.size());
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        boolean bl = false;
        object = this.mLock;
        synchronized (object) {
            Iterator<ScoredNetwork> iterator = list.iterator();
            do {
                if (!iterator.hasNext()) {
                    if (this.mListener == null) return;
                    if (!bl) return;
                    this.mListener.post(list);
                    return;
                }
                ScoredNetwork scoredNetwork = iterator.next();
                CharSequence charSequence = this.buildNetworkKey(scoredNetwork);
                if (charSequence == null) {
                    if (!DBG) continue;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to build network key for ScoredNetwork");
                    ((StringBuilder)charSequence).append(scoredNetwork);
                    Log.d(TAG, ((StringBuilder)charSequence).toString());
                    continue;
                }
                this.mCache.put((String)charSequence, scoredNetwork);
                bl = true;
            } while (true);
        }
    }

    public static abstract class CacheListener {
        private Handler mHandler;

        public CacheListener(Handler handler) {
            Preconditions.checkNotNull(handler);
            this.mHandler = handler;
        }

        public abstract void networkCacheUpdated(List<ScoredNetwork> var1);

        void post(final List<ScoredNetwork> list) {
            this.mHandler.post(new Runnable(){

                @Override
                public void run() {
                    CacheListener.this.networkCacheUpdated(list);
                }
            });
        }

    }

}

