/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class WebViewClient {
    public static final int ERROR_AUTHENTICATION = -4;
    public static final int ERROR_BAD_URL = -12;
    public static final int ERROR_CONNECT = -6;
    public static final int ERROR_FAILED_SSL_HANDSHAKE = -11;
    public static final int ERROR_FILE = -13;
    public static final int ERROR_FILE_NOT_FOUND = -14;
    public static final int ERROR_HOST_LOOKUP = -2;
    public static final int ERROR_IO = -7;
    public static final int ERROR_PROXY_AUTHENTICATION = -5;
    public static final int ERROR_REDIRECT_LOOP = -9;
    public static final int ERROR_TIMEOUT = -8;
    public static final int ERROR_TOO_MANY_REQUESTS = -15;
    public static final int ERROR_UNKNOWN = -1;
    public static final int ERROR_UNSAFE_RESOURCE = -16;
    public static final int ERROR_UNSUPPORTED_AUTH_SCHEME = -3;
    public static final int ERROR_UNSUPPORTED_SCHEME = -10;
    public static final int SAFE_BROWSING_THREAT_BILLING = 4;
    public static final int SAFE_BROWSING_THREAT_MALWARE = 1;
    public static final int SAFE_BROWSING_THREAT_PHISHING = 2;
    public static final int SAFE_BROWSING_THREAT_UNKNOWN = 0;
    public static final int SAFE_BROWSING_THREAT_UNWANTED_SOFTWARE = 3;

    private void onUnhandledInputEventInternal(WebView viewParent, InputEvent inputEvent) {
        if ((viewParent = ((View)((Object)viewParent)).getViewRootImpl()) != null) {
            ((ViewRootImpl)viewParent).dispatchUnhandledInputEvent(inputEvent);
        }
    }

    public void doUpdateVisitedHistory(WebView webView, String string2, boolean bl) {
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        message.sendToTarget();
    }

    public void onLoadResource(WebView webView, String string2) {
    }

    public void onPageCommitVisible(WebView webView, String string2) {
    }

    public void onPageFinished(WebView webView, String string2) {
    }

    public void onPageStarted(WebView webView, String string2, Bitmap bitmap) {
    }

    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        clientCertRequest.cancel();
    }

    @Deprecated
    public void onReceivedError(WebView webView, int n, String string2, String string3) {
    }

    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        if (webResourceRequest.isForMainFrame()) {
            this.onReceivedError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }
    }

    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String string2, String string3) {
        httpAuthHandler.cancel();
    }

    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
    }

    public void onReceivedLoginRequest(WebView webView, String string2, String string3, String string4) {
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        sslErrorHandler.cancel();
    }

    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail renderProcessGoneDetail) {
        return false;
    }

    public void onSafeBrowsingHit(WebView webView, WebResourceRequest webResourceRequest, int n, SafeBrowsingResponse safeBrowsingResponse) {
        safeBrowsingResponse.showInterstitial(true);
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
    }

    @Deprecated
    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        message.sendToTarget();
    }

    public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
        if (inputEvent instanceof KeyEvent) {
            this.onUnhandledKeyEvent(webView, (KeyEvent)inputEvent);
            return;
        }
        this.onUnhandledInputEventInternal(webView, inputEvent);
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.onUnhandledInputEventInternal(webView, keyEvent);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return this.shouldInterceptRequest(webView, webResourceRequest.getUrl().toString());
    }

    @Deprecated
    public WebResourceResponse shouldInterceptRequest(WebView webView, String string2) {
        return null;
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return false;
    }

    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest webResourceRequest) {
        return this.shouldOverrideUrlLoading(webView, webResourceRequest.getUrl().toString());
    }

    @Deprecated
    public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
        return false;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SafeBrowsingThreat {
    }

}

