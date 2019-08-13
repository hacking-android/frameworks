/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.webkit.ValueCallback;
import android.webkit.WebViewFactory;
import java.util.Map;

public class WebStorage {
    public static WebStorage getInstance() {
        return WebViewFactory.getProvider().getWebStorage();
    }

    public void deleteAllData() {
    }

    public void deleteOrigin(String string2) {
    }

    public void getOrigins(ValueCallback<Map> valueCallback) {
    }

    public void getQuotaForOrigin(String string2, ValueCallback<Long> valueCallback) {
    }

    public void getUsageForOrigin(String string2, ValueCallback<Long> valueCallback) {
    }

    @Deprecated
    public void setQuotaForOrigin(String string2, long l) {
    }

    public static class Origin {
        private String mOrigin = null;
        private long mQuota = 0L;
        private long mUsage = 0L;

        @SystemApi
        protected Origin(String string2, long l, long l2) {
            this.mOrigin = string2;
            this.mQuota = l;
            this.mUsage = l2;
        }

        public String getOrigin() {
            return this.mOrigin;
        }

        public long getQuota() {
            return this.mQuota;
        }

        public long getUsage() {
            return this.mUsage;
        }
    }

    @Deprecated
    public static interface QuotaUpdater {
        public void updateQuota(long var1);
    }

}

