/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.GeolocationPermissions;
import android.webkit.ServiceWorkerController;
import android.webkit.TokenBindingService;
import android.webkit.TracingController;
import android.webkit.ValueCallback;
import android.webkit.WebIconDatabase;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewDatabase;
import android.webkit.WebViewProvider;
import java.util.List;

@SystemApi
public interface WebViewFactoryProvider {
    public WebViewProvider createWebView(WebView var1, WebView.PrivateAccess var2);

    public CookieManager getCookieManager();

    public GeolocationPermissions getGeolocationPermissions();

    public ServiceWorkerController getServiceWorkerController();

    public Statics getStatics();

    public TokenBindingService getTokenBindingService();

    public TracingController getTracingController();

    public WebIconDatabase getWebIconDatabase();

    public WebStorage getWebStorage();

    public ClassLoader getWebViewClassLoader();

    public WebViewDatabase getWebViewDatabase(Context var1);

    public static interface Statics {
        public void clearClientCertPreferences(Runnable var1);

        public void enableSlowWholeDocumentDraw();

        public String findAddress(String var1);

        public void freeMemoryForTests();

        public String getDefaultUserAgent(Context var1);

        public Uri getSafeBrowsingPrivacyPolicyUrl();

        public void initSafeBrowsing(Context var1, ValueCallback<Boolean> var2);

        public Uri[] parseFileChooserResult(int var1, Intent var2);

        public void setSafeBrowsingWhitelist(List<String> var1, ValueCallback<Boolean> var2);

        public void setWebContentsDebuggingEnabled(boolean var1);
    }

}

