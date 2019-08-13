/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.StrictMode;
import android.print.PrintDocumentAdapter;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.webkit.CookieSyncManager;
import android.webkit.DownloadListener;
import android.webkit.FindAddress;
import android.webkit.PluginList;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebMessage;
import android.webkit.WebMessagePort;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebViewFactory;
import android.webkit.WebViewFactoryProvider;
import android.webkit.WebViewProvider;
import android.webkit.WebViewRenderProcess;
import android.webkit.WebViewRenderProcessClient;
import android.widget.AbsoluteLayout;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class WebView
extends AbsoluteLayout
implements ViewTreeObserver.OnGlobalFocusChangeListener,
ViewGroup.OnHierarchyChangeListener,
ViewDebug.HierarchyHandler {
    private static final String LOGTAG = "WebView";
    public static final int RENDERER_PRIORITY_BOUND = 1;
    public static final int RENDERER_PRIORITY_IMPORTANT = 2;
    public static final int RENDERER_PRIORITY_WAIVED = 0;
    public static final String SCHEME_GEO = "geo:0,0?q=";
    public static final String SCHEME_MAILTO = "mailto:";
    public static final String SCHEME_TEL = "tel:";
    @UnsupportedAppUsage
    private static volatile boolean sEnforceThreadChecking = false;
    private FindListenerDistributor mFindListener;
    @UnsupportedAppUsage
    private WebViewProvider mProvider;
    @UnsupportedAppUsage
    private final Looper mWebViewThread = Looper.myLooper();

    public WebView(Context context) {
        this(context, null);
    }

    public WebView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842885);
    }

    public WebView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public WebView(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, n2, null, false);
    }

    @UnsupportedAppUsage
    protected WebView(Context context, AttributeSet attributeSet, int n, int n2, Map<String, Object> map, boolean bl) {
        super(context, attributeSet, n, n2);
        n = this.getImportantForAutofill();
        boolean bl2 = true;
        if (n == 0) {
            this.setImportantForAutofill(1);
        }
        if (context != null) {
            if (this.mWebViewThread != null) {
                if (context.getApplicationInfo().targetSdkVersion < 18) {
                    bl2 = false;
                }
                sEnforceThreadChecking = bl2;
                this.checkThread();
                this.ensureProviderCreated();
                this.mProvider.init(map, bl);
                CookieSyncManager.setGetInstanceIsAllowed();
                return;
            }
            throw new RuntimeException("WebView cannot be initialized on a thread that has no Looper.");
        }
        throw new IllegalArgumentException("Invalid context argument");
    }

    @UnsupportedAppUsage
    protected WebView(Context context, AttributeSet attributeSet, int n, Map<String, Object> map, boolean bl) {
        this(context, attributeSet, n, 0, map, bl);
    }

    @Deprecated
    public WebView(Context context, AttributeSet attributeSet, int n, boolean bl) {
        this(context, attributeSet, n, 0, null, bl);
    }

    @UnsupportedAppUsage
    private void checkThread() {
        if (this.mWebViewThread != null && Looper.myLooper() != this.mWebViewThread) {
            Serializable serializable = new StringBuilder();
            serializable.append("A WebView method was called on thread '");
            serializable.append(Thread.currentThread().getName());
            serializable.append("'. All WebView methods must be called on the same thread. (Expected Looper ");
            serializable.append(this.mWebViewThread);
            serializable.append(" called on ");
            serializable.append(Looper.myLooper());
            serializable.append(", FYI main Looper is ");
            serializable.append(Looper.getMainLooper());
            serializable.append(")");
            serializable = new Throwable(serializable.toString());
            Log.w(LOGTAG, Log.getStackTraceString((Throwable)serializable));
            StrictMode.onWebViewMethodCalledOnWrongThread((Throwable)serializable);
            if (sEnforceThreadChecking) {
                throw new RuntimeException((Throwable)serializable);
            }
        }
    }

    public static void clearClientCertPreferences(Runnable runnable) {
        WebView.getFactory().getStatics().clearClientCertPreferences(runnable);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static void disablePlatformNotifications() {
    }

    public static void disableWebView() {
        WebViewFactory.disableWebView();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static void enablePlatformNotifications() {
    }

    public static void enableSlowWholeDocumentDraw() {
        WebView.getFactory().getStatics().enableSlowWholeDocumentDraw();
    }

    private void ensureProviderCreated() {
        this.checkThread();
        if (this.mProvider == null) {
            this.mProvider = WebView.getFactory().createWebView(this, new PrivateAccess());
        }
    }

    @Deprecated
    public static String findAddress(String string2) {
        if (string2 != null) {
            return FindAddress.findAddress(string2);
        }
        throw new NullPointerException("addr is null");
    }

    @UnsupportedAppUsage
    public static void freeMemoryForTests() {
        WebView.getFactory().getStatics().freeMemoryForTests();
    }

    public static PackageInfo getCurrentWebViewPackage() {
        Object object = WebViewFactory.getLoadedPackageInfo();
        if (object != null) {
            return object;
        }
        object = WebViewFactory.getUpdateService();
        if (object == null) {
            return null;
        }
        try {
            object = object.getCurrentWebViewPackage();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    private static WebViewFactoryProvider getFactory() {
        return WebViewFactory.getProvider();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static PluginList getPluginList() {
        synchronized (WebView.class) {
            PluginList pluginList = new PluginList();
            return pluginList;
        }
    }

    public static Uri getSafeBrowsingPrivacyPolicyUrl() {
        return WebView.getFactory().getStatics().getSafeBrowsingPrivacyPolicyUrl();
    }

    public static ClassLoader getWebViewClassLoader() {
        return WebView.getFactory().getWebViewClassLoader();
    }

    public static void setDataDirectorySuffix(String string2) {
        WebViewFactory.setDataDirectorySuffix(string2);
    }

    public static void setSafeBrowsingWhitelist(List<String> list, ValueCallback<Boolean> valueCallback) {
        WebView.getFactory().getStatics().setSafeBrowsingWhitelist(list, valueCallback);
    }

    public static void setWebContentsDebuggingEnabled(boolean bl) {
        WebView.getFactory().getStatics().setWebContentsDebuggingEnabled(bl);
    }

    private void setupFindListenerIfNeeded() {
        if (this.mFindListener == null) {
            this.mFindListener = new FindListenerDistributor();
            this.mProvider.setFindListener(this.mFindListener);
        }
    }

    public static void startSafeBrowsing(Context context, ValueCallback<Boolean> valueCallback) {
        WebView.getFactory().getStatics().initSafeBrowsing(context, valueCallback);
    }

    public void addJavascriptInterface(Object object, String string2) {
        this.checkThread();
        this.mProvider.addJavascriptInterface(object, string2);
    }

    @Override
    public void autofill(SparseArray<AutofillValue> sparseArray) {
        this.mProvider.getViewDelegate().autofill(sparseArray);
    }

    public boolean canGoBack() {
        this.checkThread();
        return this.mProvider.canGoBack();
    }

    public boolean canGoBackOrForward(int n) {
        this.checkThread();
        return this.mProvider.canGoBackOrForward(n);
    }

    public boolean canGoForward() {
        this.checkThread();
        return this.mProvider.canGoForward();
    }

    @Deprecated
    public boolean canZoomIn() {
        this.checkThread();
        return this.mProvider.canZoomIn();
    }

    @Deprecated
    public boolean canZoomOut() {
        this.checkThread();
        return this.mProvider.canZoomOut();
    }

    @Deprecated
    public Picture capturePicture() {
        this.checkThread();
        return this.mProvider.capturePicture();
    }

    public void clearCache(boolean bl) {
        this.checkThread();
        this.mProvider.clearCache(bl);
    }

    public void clearFormData() {
        this.checkThread();
        this.mProvider.clearFormData();
    }

    public void clearHistory() {
        this.checkThread();
        this.mProvider.clearHistory();
    }

    public void clearMatches() {
        this.checkThread();
        this.mProvider.clearMatches();
    }

    public void clearSslPreferences() {
        this.checkThread();
        this.mProvider.clearSslPreferences();
    }

    @Deprecated
    public void clearView() {
        this.checkThread();
        this.mProvider.clearView();
    }

    @Override
    protected int computeHorizontalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollOffset();
    }

    @Override
    protected int computeHorizontalScrollRange() {
        return this.mProvider.getScrollDelegate().computeHorizontalScrollRange();
    }

    @Override
    public void computeScroll() {
        this.mProvider.getScrollDelegate().computeScroll();
    }

    @Override
    protected int computeVerticalScrollExtent() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollExtent();
    }

    @Override
    protected int computeVerticalScrollOffset() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollOffset();
    }

    @Override
    protected int computeVerticalScrollRange() {
        return this.mProvider.getScrollDelegate().computeVerticalScrollRange();
    }

    public WebBackForwardList copyBackForwardList() {
        this.checkThread();
        return this.mProvider.copyBackForwardList();
    }

    @Deprecated
    public PrintDocumentAdapter createPrintDocumentAdapter() {
        this.checkThread();
        return this.mProvider.createPrintDocumentAdapter("default");
    }

    public PrintDocumentAdapter createPrintDocumentAdapter(String string2) {
        this.checkThread();
        return this.mProvider.createPrintDocumentAdapter(string2);
    }

    public WebMessagePort[] createWebMessageChannel() {
        this.checkThread();
        return this.mProvider.createWebMessageChannel();
    }

    @Deprecated
    @UnsupportedAppUsage
    public void debugDump() {
        this.checkThread();
    }

    public void destroy() {
        this.checkThread();
        this.mProvider.destroy();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        this.mProvider.getViewDelegate().preDispatchDraw(canvas);
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().dispatchKeyEvent(keyEvent);
    }

    public void documentHasImages(Message message) {
        this.checkThread();
        this.mProvider.documentHasImages(message);
    }

    @Override
    public void dumpViewHierarchyWithProperties(BufferedWriter bufferedWriter, int n) {
        this.mProvider.dumpViewHierarchyWithProperties(bufferedWriter, n);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void emulateShiftHeld() {
        this.checkThread();
    }

    @Override
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        this.checkThread();
        viewHierarchyEncoder.addProperty("webview:contentHeight", this.mProvider.getContentHeight());
        viewHierarchyEncoder.addProperty("webview:contentWidth", this.mProvider.getContentWidth());
        viewHierarchyEncoder.addProperty("webview:scale", this.mProvider.getScale());
        viewHierarchyEncoder.addProperty("webview:title", this.mProvider.getTitle());
        viewHierarchyEncoder.addProperty("webview:url", this.mProvider.getUrl());
        viewHierarchyEncoder.addProperty("webview:originalUrl", this.mProvider.getOriginalUrl());
    }

    public void evaluateJavascript(String string2, ValueCallback<String> valueCallback) {
        this.checkThread();
        this.mProvider.evaluateJavaScript(string2, valueCallback);
    }

    @Deprecated
    public int findAll(String string2) {
        this.checkThread();
        StrictMode.noteSlowCall("findAll blocks UI: prefer findAllAsync");
        return this.mProvider.findAll(string2);
    }

    public void findAllAsync(String string2) {
        this.checkThread();
        this.mProvider.findAllAsync(string2);
    }

    @Override
    public View findFocus() {
        return this.mProvider.getViewDelegate().findFocus(super.findFocus());
    }

    @Override
    public View findHierarchyView(String string2, int n) {
        return this.mProvider.findHierarchyView(string2, n);
    }

    public void findNext(boolean bl) {
        this.checkThread();
        this.mProvider.findNext(bl);
    }

    public void flingScroll(int n, int n2) {
        this.checkThread();
        this.mProvider.flingScroll(n, n2);
    }

    @Deprecated
    public void freeMemory() {
        this.checkThread();
        this.mProvider.freeMemory();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return WebView.class.getName();
    }

    @Override
    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        AccessibilityNodeProvider accessibilityNodeProvider;
        block0 : {
            accessibilityNodeProvider = this.mProvider.getViewDelegate().getAccessibilityNodeProvider();
            if (accessibilityNodeProvider != null) break block0;
            accessibilityNodeProvider = super.getAccessibilityNodeProvider();
        }
        return accessibilityNodeProvider;
    }

    public SslCertificate getCertificate() {
        this.checkThread();
        return this.mProvider.getCertificate();
    }

    @ViewDebug.ExportedProperty(category="webview")
    public int getContentHeight() {
        this.checkThread();
        return this.mProvider.getContentHeight();
    }

    @ViewDebug.ExportedProperty(category="webview")
    @UnsupportedAppUsage
    public int getContentWidth() {
        return this.mProvider.getContentWidth();
    }

    public Bitmap getFavicon() {
        this.checkThread();
        return this.mProvider.getFavicon();
    }

    @Override
    public Handler getHandler() {
        return this.mProvider.getViewDelegate().getHandler(super.getHandler());
    }

    public HitTestResult getHitTestResult() {
        this.checkThread();
        return this.mProvider.getHitTestResult();
    }

    @Deprecated
    public String[] getHttpAuthUsernamePassword(String string2, String string3) {
        this.checkThread();
        return this.mProvider.getHttpAuthUsernamePassword(string2, string3);
    }

    @ViewDebug.ExportedProperty(category="webview")
    public String getOriginalUrl() {
        this.checkThread();
        return this.mProvider.getOriginalUrl();
    }

    public int getProgress() {
        this.checkThread();
        return this.mProvider.getProgress();
    }

    public boolean getRendererPriorityWaivedWhenNotVisible() {
        return this.mProvider.getRendererPriorityWaivedWhenNotVisible();
    }

    public int getRendererRequestedPriority() {
        return this.mProvider.getRendererRequestedPriority();
    }

    @ViewDebug.ExportedProperty(category="webview")
    @Deprecated
    public float getScale() {
        this.checkThread();
        return this.mProvider.getScale();
    }

    public WebSettings getSettings() {
        this.checkThread();
        return this.mProvider.getSettings();
    }

    public TextClassifier getTextClassifier() {
        return this.mProvider.getTextClassifier();
    }

    @ViewDebug.ExportedProperty(category="webview")
    public String getTitle() {
        this.checkThread();
        return this.mProvider.getTitle();
    }

    @UnsupportedAppUsage
    public String getTouchIconUrl() {
        return this.mProvider.getTouchIconUrl();
    }

    @ViewDebug.ExportedProperty(category="webview")
    public String getUrl() {
        this.checkThread();
        return this.mProvider.getUrl();
    }

    @Deprecated
    @UnsupportedAppUsage
    public int getVisibleTitleHeight() {
        this.checkThread();
        return this.mProvider.getVisibleTitleHeight();
    }

    public WebChromeClient getWebChromeClient() {
        this.checkThread();
        return this.mProvider.getWebChromeClient();
    }

    public WebViewClient getWebViewClient() {
        this.checkThread();
        return this.mProvider.getWebViewClient();
    }

    public Looper getWebViewLooper() {
        return this.mWebViewThread;
    }

    @SystemApi
    public WebViewProvider getWebViewProvider() {
        return this.mProvider;
    }

    public WebViewRenderProcess getWebViewRenderProcess() {
        this.checkThread();
        return this.mProvider.getWebViewRenderProcess();
    }

    public WebViewRenderProcessClient getWebViewRenderProcessClient() {
        this.checkThread();
        return this.mProvider.getWebViewRenderProcessClient();
    }

    @Deprecated
    @UnsupportedAppUsage
    public View getZoomControls() {
        this.checkThread();
        return this.mProvider.getZoomControls();
    }

    public void goBack() {
        this.checkThread();
        this.mProvider.goBack();
    }

    public void goBackOrForward(int n) {
        this.checkThread();
        this.mProvider.goBackOrForward(n);
    }

    public void goForward() {
        this.checkThread();
        this.mProvider.goForward();
    }

    public void invokeZoomPicker() {
        this.checkThread();
        this.mProvider.invokeZoomPicker();
    }

    @UnsupportedAppUsage
    public boolean isPaused() {
        return this.mProvider.isPaused();
    }

    public boolean isPrivateBrowsingEnabled() {
        this.checkThread();
        return this.mProvider.isPrivateBrowsingEnabled();
    }

    @Override
    public boolean isVisibleToUserForAutofill(int n) {
        return this.mProvider.getViewDelegate().isVisibleToUserForAutofill(n);
    }

    public void loadData(String string2, String string3, String string4) {
        this.checkThread();
        this.mProvider.loadData(string2, string3, string4);
    }

    public void loadDataWithBaseURL(String string2, String string3, String string4, String string5, String string6) {
        this.checkThread();
        this.mProvider.loadDataWithBaseURL(string2, string3, string4, string5, string6);
    }

    public void loadUrl(String string2) {
        this.checkThread();
        this.mProvider.loadUrl(string2);
    }

    public void loadUrl(String string2, Map<String, String> map) {
        this.checkThread();
        this.mProvider.loadUrl(string2, map);
    }

    @UnsupportedAppUsage
    void notifyFindDialogDismissed() {
        this.checkThread();
        this.mProvider.notifyFindDialogDismissed();
    }

    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        this.mProvider.getViewDelegate().onActivityResult(n, n2, intent);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mProvider.getViewDelegate().onAttachedToWindow();
    }

    @Override
    public boolean onCheckIsTextEditor() {
        return this.mProvider.getViewDelegate().onCheckIsTextEditor();
    }

    @Deprecated
    @Override
    public void onChildViewAdded(View view, View view2) {
    }

    @Deprecated
    @Override
    public void onChildViewRemoved(View view, View view2) {
    }

    @Override
    protected void onConfigurationChanged(Configuration configuration) {
        this.mProvider.getViewDelegate().onConfigurationChanged(configuration);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return this.mProvider.getViewDelegate().onCreateInputConnection(editorInfo);
    }

    @Override
    protected void onDetachedFromWindowInternal() {
        this.mProvider.getViewDelegate().onDetachedFromWindow();
        super.onDetachedFromWindowInternal();
    }

    @Override
    public boolean onDragEvent(DragEvent dragEvent) {
        return this.mProvider.getViewDelegate().onDragEvent(dragEvent);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.mProvider.getViewDelegate().onDraw(canvas);
    }

    @UnsupportedAppUsage
    @Override
    protected void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable2, int n, int n2, int n3, int n4) {
        this.mProvider.getViewDelegate().onDrawVerticalScrollBar(canvas, drawable2, n, n2, n3, n4);
    }

    @Override
    public void onFinishTemporaryDetach() {
        super.onFinishTemporaryDetach();
        this.mProvider.getViewDelegate().onFinishTemporaryDetach();
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        this.mProvider.getViewDelegate().onFocusChanged(bl, n, rect);
        super.onFocusChanged(bl, n, rect);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onGenericMotionEvent(motionEvent);
    }

    @Deprecated
    @Override
    public void onGlobalFocusChanged(View view, View view2) {
    }

    @Override
    public boolean onHoverEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onHoverEvent(motionEvent);
    }

    @Override
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        this.mProvider.getViewDelegate().onInitializeAccessibilityEvent(accessibilityEvent);
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        this.mProvider.getViewDelegate().onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyDown(n, keyEvent);
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyMultiple(n, n2, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        return this.mProvider.getViewDelegate().onKeyUp(n, keyEvent);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        this.mProvider.getViewDelegate().onMeasure(n, n2);
    }

    @Override
    public void onMovedToDisplay(int n, Configuration configuration) {
        this.mProvider.getViewDelegate().onMovedToDisplay(n, configuration);
    }

    @Override
    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        this.mProvider.getViewDelegate().onOverScrolled(n, n2, bl, bl2);
    }

    public void onPause() {
        this.checkThread();
        this.mProvider.onPause();
    }

    @Override
    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int n) {
        this.mProvider.getViewDelegate().onProvideAutofillVirtualStructure(viewStructure, n);
    }

    @Override
    public void onProvideVirtualStructure(ViewStructure viewStructure) {
        this.mProvider.getViewDelegate().onProvideVirtualStructure(viewStructure);
    }

    public void onResume() {
        this.checkThread();
        this.mProvider.onResume();
    }

    @Override
    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        this.mProvider.getViewDelegate().onScrollChanged(n, n2, n3, n4);
    }

    @Override
    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        this.mProvider.getViewDelegate().onSizeChanged(n, n2, n3, n4);
    }

    @Override
    public void onStartTemporaryDetach() {
        super.onStartTemporaryDetach();
        this.mProvider.getViewDelegate().onStartTemporaryDetach();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTouchEvent(motionEvent);
    }

    @Override
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return this.mProvider.getViewDelegate().onTrackballEvent(motionEvent);
    }

    @Override
    protected void onVisibilityChanged(View view, int n) {
        super.onVisibilityChanged(view, n);
        this.ensureProviderCreated();
        this.mProvider.getViewDelegate().onVisibilityChanged(view, n);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        this.mProvider.getViewDelegate().onWindowFocusChanged(bl);
        super.onWindowFocusChanged(bl);
    }

    @Override
    protected void onWindowVisibilityChanged(int n) {
        super.onWindowVisibilityChanged(n);
        this.mProvider.getViewDelegate().onWindowVisibilityChanged(n);
    }

    @Deprecated
    public boolean overlayHorizontalScrollbar() {
        return true;
    }

    @Deprecated
    public boolean overlayVerticalScrollbar() {
        return false;
    }

    public boolean pageDown(boolean bl) {
        this.checkThread();
        return this.mProvider.pageDown(bl);
    }

    public boolean pageUp(boolean bl) {
        this.checkThread();
        return this.mProvider.pageUp(bl);
    }

    public void pauseTimers() {
        this.checkThread();
        this.mProvider.pauseTimers();
    }

    @Override
    public boolean performAccessibilityActionInternal(int n, Bundle bundle) {
        return this.mProvider.getViewDelegate().performAccessibilityAction(n, bundle);
    }

    @Override
    public boolean performLongClick() {
        return this.mProvider.getViewDelegate().performLongClick();
    }

    public void postUrl(String string2, byte[] arrby) {
        this.checkThread();
        if (URLUtil.isNetworkUrl(string2)) {
            this.mProvider.postUrl(string2, arrby);
        } else {
            this.mProvider.loadUrl(string2);
        }
    }

    public void postVisualStateCallback(long l, VisualStateCallback visualStateCallback) {
        this.checkThread();
        this.mProvider.insertVisualStateCallback(l, visualStateCallback);
    }

    public void postWebMessage(WebMessage webMessage, Uri uri) {
        this.checkThread();
        this.mProvider.postMessageToMainFrame(webMessage, uri);
    }

    @Deprecated
    @UnsupportedAppUsage
    public void refreshPlugins(boolean bl) {
        this.checkThread();
    }

    public void reload() {
        this.checkThread();
        this.mProvider.reload();
    }

    public void removeJavascriptInterface(String string2) {
        this.checkThread();
        this.mProvider.removeJavascriptInterface(string2);
    }

    @Override
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        return this.mProvider.getViewDelegate().requestChildRectangleOnScreen(view, rect, bl);
    }

    @Override
    public boolean requestFocus(int n, Rect rect) {
        return this.mProvider.getViewDelegate().requestFocus(n, rect);
    }

    public void requestFocusNodeHref(Message message) {
        this.checkThread();
        this.mProvider.requestFocusNodeHref(message);
    }

    public void requestImageRef(Message message) {
        this.checkThread();
        this.mProvider.requestImageRef(message);
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean restorePicture(Bundle bundle, File file) {
        this.checkThread();
        return this.mProvider.restorePicture(bundle, file);
    }

    public WebBackForwardList restoreState(Bundle bundle) {
        this.checkThread();
        return this.mProvider.restoreState(bundle);
    }

    public void resumeTimers() {
        this.checkThread();
        this.mProvider.resumeTimers();
    }

    @Deprecated
    public void savePassword(String string2, String string3, String string4) {
        this.checkThread();
        this.mProvider.savePassword(string2, string3, string4);
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean savePicture(Bundle bundle, File file) {
        this.checkThread();
        return this.mProvider.savePicture(bundle, file);
    }

    public WebBackForwardList saveState(Bundle bundle) {
        this.checkThread();
        return this.mProvider.saveState(bundle);
    }

    public void saveWebArchive(String string2) {
        this.checkThread();
        this.mProvider.saveWebArchive(string2);
    }

    public void saveWebArchive(String string2, boolean bl, ValueCallback<String> valueCallback) {
        this.checkThread();
        this.mProvider.saveWebArchive(string2, bl, valueCallback);
    }

    @Override
    public void setBackgroundColor(int n) {
        this.mProvider.getViewDelegate().setBackgroundColor(n);
    }

    @Deprecated
    public void setCertificate(SslCertificate sslCertificate) {
        this.checkThread();
        this.mProvider.setCertificate(sslCertificate);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.checkThread();
        this.mProvider.setDownloadListener(downloadListener);
    }

    void setFindDialogFindListener(FindListener findListener) {
        this.checkThread();
        this.setupFindListenerIfNeeded();
        this.mFindListener.mFindDialogFindListener = findListener;
    }

    public void setFindListener(FindListener findListener) {
        this.checkThread();
        this.setupFindListenerIfNeeded();
        this.mFindListener.mUserFindListener = findListener;
    }

    @UnsupportedAppUsage
    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        return this.mProvider.getViewDelegate().setFrame(n, n2, n3, n4);
    }

    @Deprecated
    public void setHorizontalScrollbarOverlay(boolean bl) {
    }

    @Deprecated
    public void setHttpAuthUsernamePassword(String string2, String string3, String string4, String string5) {
        this.checkThread();
        this.mProvider.setHttpAuthUsernamePassword(string2, string3, string4, string5);
    }

    public void setInitialScale(int n) {
        this.checkThread();
        this.mProvider.setInitialScale(n);
    }

    @Override
    public void setLayerType(int n, Paint paint) {
        super.setLayerType(n, paint);
        this.mProvider.getViewDelegate().setLayerType(n, paint);
    }

    @Override
    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        this.mProvider.getViewDelegate().setLayoutParams(layoutParams);
    }

    @Deprecated
    public void setMapTrackballToArrowKeys(boolean bl) {
        this.checkThread();
        this.mProvider.setMapTrackballToArrowKeys(bl);
    }

    public void setNetworkAvailable(boolean bl) {
        this.checkThread();
        this.mProvider.setNetworkAvailable(bl);
    }

    @Override
    public void setOverScrollMode(int n) {
        super.setOverScrollMode(n);
        this.ensureProviderCreated();
        this.mProvider.getViewDelegate().setOverScrollMode(n);
    }

    @Deprecated
    public void setPictureListener(PictureListener pictureListener) {
        this.checkThread();
        this.mProvider.setPictureListener(pictureListener);
    }

    public void setRendererPriorityPolicy(int n, boolean bl) {
        this.mProvider.setRendererPriorityPolicy(n, bl);
    }

    @Override
    public void setScrollBarStyle(int n) {
        this.mProvider.getViewDelegate().setScrollBarStyle(n);
        super.setScrollBarStyle(n);
    }

    public void setTextClassifier(TextClassifier textClassifier) {
        this.mProvider.setTextClassifier(textClassifier);
    }

    @Deprecated
    public void setVerticalScrollbarOverlay(boolean bl) {
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.checkThread();
        this.mProvider.setWebChromeClient(webChromeClient);
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        this.checkThread();
        this.mProvider.setWebViewClient(webViewClient);
    }

    public void setWebViewRenderProcessClient(WebViewRenderProcessClient webViewRenderProcessClient) {
        this.checkThread();
        this.mProvider.setWebViewRenderProcessClient(null, webViewRenderProcessClient);
    }

    public void setWebViewRenderProcessClient(Executor executor, WebViewRenderProcessClient webViewRenderProcessClient) {
        this.checkThread();
        this.mProvider.setWebViewRenderProcessClient(executor, webViewRenderProcessClient);
    }

    @Deprecated
    @Override
    public boolean shouldDelayChildPressedState() {
        return this.mProvider.getViewDelegate().shouldDelayChildPressedState();
    }

    @Deprecated
    public boolean showFindDialog(String string2, boolean bl) {
        this.checkThread();
        return this.mProvider.showFindDialog(string2, bl);
    }

    public void stopLoading() {
        this.checkThread();
        this.mProvider.stopLoading();
    }

    public void zoomBy(float f) {
        this.checkThread();
        if (!((double)f < 0.01)) {
            if (!((double)f > 100.0)) {
                this.mProvider.zoomBy(f);
                return;
            }
            throw new IllegalArgumentException("zoomFactor must be less than 100.");
        }
        throw new IllegalArgumentException("zoomFactor must be greater than 0.01.");
    }

    public boolean zoomIn() {
        this.checkThread();
        return this.mProvider.zoomIn();
    }

    public boolean zoomOut() {
        this.checkThread();
        return this.mProvider.zoomOut();
    }

    public static interface FindListener {
        public void onFindResultReceived(int var1, int var2, boolean var3);
    }

    private class FindListenerDistributor
    implements FindListener {
        private FindListener mFindDialogFindListener;
        private FindListener mUserFindListener;

        private FindListenerDistributor() {
        }

        @Override
        public void onFindResultReceived(int n, int n2, boolean bl) {
            FindListener findListener = this.mFindDialogFindListener;
            if (findListener != null) {
                findListener.onFindResultReceived(n, n2, bl);
            }
            if ((findListener = this.mUserFindListener) != null) {
                findListener.onFindResultReceived(n, n2, bl);
            }
        }
    }

    public static class HitTestResult {
        @Deprecated
        public static final int ANCHOR_TYPE = 1;
        public static final int EDIT_TEXT_TYPE = 9;
        public static final int EMAIL_TYPE = 4;
        public static final int GEO_TYPE = 3;
        @Deprecated
        public static final int IMAGE_ANCHOR_TYPE = 6;
        public static final int IMAGE_TYPE = 5;
        public static final int PHONE_TYPE = 2;
        public static final int SRC_ANCHOR_TYPE = 7;
        public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
        public static final int UNKNOWN_TYPE = 0;
        private String mExtra;
        private int mType = 0;

        public String getExtra() {
            return this.mExtra;
        }

        public int getType() {
            return this.mType;
        }

        @SystemApi
        public void setExtra(String string2) {
            this.mExtra = string2;
        }

        @SystemApi
        public void setType(int n) {
            this.mType = n;
        }
    }

    @Deprecated
    public static interface PictureListener {
        @Deprecated
        public void onNewPicture(WebView var1, Picture var2);
    }

    @SystemApi
    public class PrivateAccess {
        public void awakenScrollBars(int n) {
            WebView.this.awakenScrollBars(n);
        }

        public void awakenScrollBars(int n, boolean bl) {
            WebView.this.awakenScrollBars(n, bl);
        }

        public float getHorizontalScrollFactor() {
            return WebView.this.getHorizontalScrollFactor();
        }

        public int getHorizontalScrollbarHeight() {
            return WebView.this.getHorizontalScrollbarHeight();
        }

        public float getVerticalScrollFactor() {
            return WebView.this.getVerticalScrollFactor();
        }

        public void onScrollChanged(int n, int n2, int n3, int n4) {
            WebView.this.onScrollChanged(n, n2, n3, n4);
        }

        public void overScrollBy(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
            WebView.this.overScrollBy(n, n2, n3, n4, n5, n6, n7, n8, bl);
        }

        public void setMeasuredDimension(int n, int n2) {
            WebView.this.setMeasuredDimension(n, n2);
        }

        public void setScrollXRaw(int n) {
            WebView.this.mScrollX = n;
        }

        public void setScrollYRaw(int n) {
            WebView.this.mScrollY = n;
        }

        public void super_computeScroll() {
            WebView.super.computeScroll();
        }

        public boolean super_dispatchKeyEvent(KeyEvent keyEvent) {
            return WebView.super.dispatchKeyEvent(keyEvent);
        }

        public int super_getScrollBarStyle() {
            return WebView.super.getScrollBarStyle();
        }

        public void super_onDrawVerticalScrollBar(Canvas canvas, Drawable drawable2, int n, int n2, int n3, int n4) {
            WebView.super.onDrawVerticalScrollBar(canvas, drawable2, n, n2, n3, n4);
        }

        public boolean super_onGenericMotionEvent(MotionEvent motionEvent) {
            return WebView.super.onGenericMotionEvent(motionEvent);
        }

        public boolean super_onHoverEvent(MotionEvent motionEvent) {
            return WebView.super.onHoverEvent(motionEvent);
        }

        public boolean super_performAccessibilityAction(int n, Bundle bundle) {
            return WebView.super.performAccessibilityActionInternal(n, bundle);
        }

        public boolean super_performLongClick() {
            return WebView.super.performLongClick();
        }

        public boolean super_requestFocus(int n, Rect rect) {
            return WebView.super.requestFocus(n, rect);
        }

        public void super_scrollTo(int n, int n2) {
            WebView.super.scrollTo(n, n2);
        }

        public boolean super_setFrame(int n, int n2, int n3, int n4) {
            return WebView.super.setFrame(n, n2, n3, n4);
        }

        public void super_setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            WebView.super.setLayoutParams(layoutParams);
        }

        public void super_startActivityForResult(Intent intent, int n) {
            WebView.super.startActivityForResult(intent, n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RendererPriority {
    }

    public static abstract class VisualStateCallback {
        public abstract void onComplete(long var1);
    }

    public class WebViewTransport {
        private WebView mWebview;

        public WebView getWebView() {
            synchronized (this) {
                WebView webView = this.mWebview;
                return webView;
            }
        }

        public void setWebView(WebView webView) {
            synchronized (this) {
                this.mWebview = webView;
                return;
            }
        }
    }

}

