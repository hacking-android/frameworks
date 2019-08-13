/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewFactory;
import android.webkit.WebViewFactoryProvider;

public class WebChromeClient {
    public Bitmap getDefaultVideoPoster() {
        return null;
    }

    public View getVideoLoadingProgressView() {
        return null;
    }

    public void getVisitedHistory(ValueCallback<String[]> valueCallback) {
    }

    public void onCloseWindow(WebView webView) {
    }

    @Deprecated
    public void onConsoleMessage(String string2, int n, String string3) {
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        this.onConsoleMessage(consoleMessage.message(), consoleMessage.lineNumber(), consoleMessage.sourceId());
        return false;
    }

    public boolean onCreateWindow(WebView webView, boolean bl, boolean bl2, Message message) {
        return false;
    }

    @Deprecated
    public void onExceededDatabaseQuota(String string2, String string3, long l, long l2, long l3, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(l);
    }

    public void onGeolocationPermissionsHidePrompt() {
    }

    public void onGeolocationPermissionsShowPrompt(String string2, GeolocationPermissions.Callback callback) {
    }

    public void onHideCustomView() {
    }

    public boolean onJsAlert(WebView webView, String string2, String string3, JsResult jsResult) {
        return false;
    }

    public boolean onJsBeforeUnload(WebView webView, String string2, String string3, JsResult jsResult) {
        return false;
    }

    public boolean onJsConfirm(WebView webView, String string2, String string3, JsResult jsResult) {
        return false;
    }

    public boolean onJsPrompt(WebView webView, String string2, String string3, String string4, JsPromptResult jsPromptResult) {
        return false;
    }

    @Deprecated
    public boolean onJsTimeout() {
        return true;
    }

    public void onPermissionRequest(PermissionRequest permissionRequest) {
        permissionRequest.deny();
    }

    public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
    }

    public void onProgressChanged(WebView webView, int n) {
    }

    @Deprecated
    public void onReachedMaxAppCacheSize(long l, long l2, WebStorage.QuotaUpdater quotaUpdater) {
        quotaUpdater.updateQuota(l2);
    }

    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
    }

    public void onReceivedTitle(WebView webView, String string2) {
    }

    public void onReceivedTouchIconUrl(WebView webView, String string2, boolean bl) {
    }

    public void onRequestFocus(WebView webView) {
    }

    @Deprecated
    public void onShowCustomView(View view, int n, CustomViewCallback customViewCallback) {
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
    }

    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
        return false;
    }

    @SystemApi
    @Deprecated
    public void openFileChooser(ValueCallback<Uri> valueCallback, String string2, String string3) {
        valueCallback.onReceiveValue(null);
    }

    public static interface CustomViewCallback {
        public void onCustomViewHidden();
    }

    public static abstract class FileChooserParams {
        public static final int MODE_OPEN = 0;
        public static final int MODE_OPEN_FOLDER = 2;
        public static final int MODE_OPEN_MULTIPLE = 1;
        public static final int MODE_SAVE = 3;

        public static Uri[] parseResult(int n, Intent intent) {
            return WebViewFactory.getProvider().getStatics().parseFileChooserResult(n, intent);
        }

        public abstract Intent createIntent();

        public abstract String[] getAcceptTypes();

        public abstract String getFilenameHint();

        public abstract int getMode();

        public abstract CharSequence getTitle();

        public abstract boolean isCaptureEnabled();
    }

}

