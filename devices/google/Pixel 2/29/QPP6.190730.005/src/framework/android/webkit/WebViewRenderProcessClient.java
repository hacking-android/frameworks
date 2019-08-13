/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.WebView;
import android.webkit.WebViewRenderProcess;

public abstract class WebViewRenderProcessClient {
    public abstract void onRenderProcessResponsive(WebView var1, WebViewRenderProcess var2);

    public abstract void onRenderProcessUnresponsive(WebView var1, WebViewRenderProcess var2);
}

