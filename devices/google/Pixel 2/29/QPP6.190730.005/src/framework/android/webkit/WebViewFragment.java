/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

@Deprecated
public class WebViewFragment
extends Fragment {
    private boolean mIsWebViewAvailable;
    private WebView mWebView;

    public WebView getWebView() {
        WebView webView = this.mIsWebViewAvailable ? this.mWebView : null;
        return webView;
    }

    @Override
    public View onCreateView(LayoutInflater object, ViewGroup viewGroup, Bundle bundle) {
        object = this.mWebView;
        if (object != null) {
            ((WebView)object).destroy();
        }
        this.mWebView = new WebView(this.getContext());
        this.mIsWebViewAvailable = true;
        return this.mWebView;
    }

    @Override
    public void onDestroy() {
        WebView webView = this.mWebView;
        if (webView != null) {
            webView.destroy();
            this.mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        this.mIsWebViewAvailable = false;
        super.onDestroyView();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mWebView.onPause();
    }

    @Override
    public void onResume() {
        this.mWebView.onResume();
        super.onResume();
    }
}

