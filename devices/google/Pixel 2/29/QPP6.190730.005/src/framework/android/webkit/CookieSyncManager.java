/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.WebSyncManager;

@Deprecated
public final class CookieSyncManager
extends WebSyncManager {
    private static boolean sGetInstanceAllowed = false;
    private static final Object sLock = new Object();
    private static CookieSyncManager sRef;

    private CookieSyncManager() {
        super(null, null);
    }

    private static void checkInstanceIsAllowed() {
        if (sGetInstanceAllowed) {
            return;
        }
        throw new IllegalStateException("CookieSyncManager::createInstance() needs to be called before CookieSyncManager::getInstance()");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CookieSyncManager createInstance(Context object) {
        Object object2 = sLock;
        synchronized (object2) {
            Throwable throwable2;
            if (object != null) {
                try {
                    CookieSyncManager.setGetInstanceIsAllowed();
                    return CookieSyncManager.getInstance();
                }
                catch (Throwable throwable2) {}
            } else {
                object = new IllegalArgumentException("Invalid context argument");
                throw object;
            }
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CookieSyncManager getInstance() {
        Object object = sLock;
        synchronized (object) {
            CookieSyncManager cookieSyncManager;
            CookieSyncManager.checkInstanceIsAllowed();
            if (sRef != null) return sRef;
            sRef = cookieSyncManager = new CookieSyncManager();
            return sRef;
        }
    }

    static void setGetInstanceIsAllowed() {
        sGetInstanceAllowed = true;
    }

    @Deprecated
    @Override
    public void resetSync() {
    }

    @Deprecated
    @Override
    public void startSync() {
    }

    @Deprecated
    @Override
    public void stopSync() {
    }

    @Deprecated
    @Override
    public void sync() {
        CookieManager.getInstance().flush();
    }

    @Deprecated
    @Override
    protected void syncFromRamToFlash() {
        CookieManager.getInstance().flush();
    }
}

