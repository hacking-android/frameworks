/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import android.webkit.CacheManager;
import android.webkit.PluginData;
import android.webkit.UrlInterceptHandler;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

@Deprecated
public final class UrlInterceptRegistry {
    private static final String LOGTAG = "intercept";
    private static boolean mDisabled = false;
    private static LinkedList mHandlerList;

    private static LinkedList getHandlers() {
        synchronized (UrlInterceptRegistry.class) {
            LinkedList linkedList;
            if (mHandlerList == null) {
                mHandlerList = linkedList = new LinkedList();
            }
            linkedList = mHandlerList;
            return linkedList;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public static PluginData getPluginData(String string2, Map<String, String> map) {
        synchronized (UrlInterceptRegistry.class) {
            void var1_1;
            PluginData pluginData;
            boolean bl = UrlInterceptRegistry.urlInterceptDisabled();
            if (bl) {
                return null;
            }
            ListIterator listIterator = UrlInterceptRegistry.getHandlers().listIterator();
            do {
                if (listIterator.hasNext()) continue;
                return null;
            } while ((pluginData = ((UrlInterceptHandler)listIterator.next()).getPluginData(string2, (Map<String, String>)var1_1)) == null);
            return pluginData;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public static CacheManager.CacheResult getSurrogate(String string2, Map<String, String> map) {
        synchronized (UrlInterceptRegistry.class) {
            void var1_1;
            CacheManager.CacheResult cacheResult;
            boolean bl = UrlInterceptRegistry.urlInterceptDisabled();
            if (bl) {
                return null;
            }
            ListIterator listIterator = UrlInterceptRegistry.getHandlers().listIterator();
            do {
                if (listIterator.hasNext()) continue;
                return null;
            } while ((cacheResult = ((UrlInterceptHandler)listIterator.next()).service(string2, (Map<String, String>)var1_1)) == null);
            return cacheResult;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean registerHandler(UrlInterceptHandler urlInterceptHandler) {
        synchronized (UrlInterceptRegistry.class) {
            if (!UrlInterceptRegistry.getHandlers().contains(urlInterceptHandler)) {
                UrlInterceptRegistry.getHandlers().addFirst(urlInterceptHandler);
                return true;
            }
            return false;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static void setUrlInterceptDisabled(boolean bl) {
        synchronized (UrlInterceptRegistry.class) {
            mDisabled = bl;
            return;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean unregisterHandler(UrlInterceptHandler urlInterceptHandler) {
        synchronized (UrlInterceptRegistry.class) {
            boolean bl = UrlInterceptRegistry.getHandlers().remove(urlInterceptHandler);
            return bl;
        }
    }

    @Deprecated
    public static boolean urlInterceptDisabled() {
        synchronized (UrlInterceptRegistry.class) {
            boolean bl = mDisabled;
            return bl;
        }
    }
}

