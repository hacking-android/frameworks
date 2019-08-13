/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.SntpClient;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.TrustedTime;

public class NtpTrustedTime
implements TrustedTime {
    private static final boolean LOGD = false;
    private static final String TAG = "NtpTrustedTime";
    private static Context sContext;
    private static NtpTrustedTime sSingleton;
    private ConnectivityManager mCM;
    private long mCachedNtpCertainty;
    private long mCachedNtpElapsedRealtime;
    private long mCachedNtpTime;
    private boolean mHasCache;
    private final String mServer;
    private final long mTimeout;

    private NtpTrustedTime(String string2, long l) {
        this.mServer = string2;
        this.mTimeout = l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static NtpTrustedTime getInstance(Context object) {
        synchronized (NtpTrustedTime.class) {
            if (sSingleton != null) return sSingleton;
            Object object2 = ((Context)object).getResources();
            ContentResolver contentResolver = ((Context)object).getContentResolver();
            Object object3 = ((Resources)object2).getString(17039762);
            long l = ((Resources)object2).getInteger(17694870);
            object2 = Settings.Global.getString(contentResolver, "ntp_server");
            l = Settings.Global.getLong(contentResolver, "ntp_timeout", l);
            if (object2 != null) {
                object3 = object2;
            }
            sSingleton = object2 = new NtpTrustedTime((String)object3, l);
            sContext = object;
            return sSingleton;
        }
    }

    @UnsupportedAppUsage
    @Override
    public long currentTimeMillis() {
        if (this.mHasCache) {
            return this.mCachedNtpTime + this.getCacheAge();
        }
        throw new IllegalStateException("Missing authoritative time source");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public boolean forceRefresh() {
        synchronized (this) {
            if (this.mCM == null) {
                this.mCM = sContext.getSystemService(ConnectivityManager.class);
            }
        }
        Object object = this.mCM;
        if (object == null) {
            object = null;
            return this.forceRefresh((Network)object);
        }
        object = ((ConnectivityManager)object).getActiveNetwork();
        return this.forceRefresh((Network)object);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean forceRefresh(Network network) {
        if (TextUtils.isEmpty(this.mServer)) {
            return false;
        }
        synchronized (this) {
            if (this.mCM == null) {
                this.mCM = sContext.getSystemService(ConnectivityManager.class);
            }
        }
        Object object = this.mCM;
        object = object == null ? null : ((ConnectivityManager)object).getNetworkInfo(network);
        if (object == null) return false;
        if (!((NetworkInfo)object).isConnected()) {
            return false;
        }
        object = new SntpClient();
        if (!((SntpClient)object).requestTime(this.mServer, (int)this.mTimeout, network)) return false;
        this.mHasCache = true;
        this.mCachedNtpTime = ((SntpClient)object).getNtpTime();
        this.mCachedNtpElapsedRealtime = ((SntpClient)object).getNtpTimeReference();
        this.mCachedNtpCertainty = ((SntpClient)object).getRoundTripTime() / 2L;
        return true;
    }

    @Override
    public long getCacheAge() {
        if (this.mHasCache) {
            return SystemClock.elapsedRealtime() - this.mCachedNtpElapsedRealtime;
        }
        return Long.MAX_VALUE;
    }

    @Override
    public long getCacheCertainty() {
        if (this.mHasCache) {
            return this.mCachedNtpCertainty;
        }
        return Long.MAX_VALUE;
    }

    @UnsupportedAppUsage
    public long getCachedNtpTime() {
        return this.mCachedNtpTime;
    }

    @UnsupportedAppUsage
    public long getCachedNtpTimeReference() {
        return this.mCachedNtpElapsedRealtime;
    }

    @UnsupportedAppUsage
    @Override
    public boolean hasCache() {
        return this.mHasCache;
    }
}

