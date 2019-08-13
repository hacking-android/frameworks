/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.content.Intent;
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
import android.os.Message;
import android.print.PrintDocumentAdapter;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebBackForwardList;
import android.webkit.WebChromeClient;
import android.webkit.WebMessage;
import android.webkit.WebMessagePort;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewRenderProcess;
import android.webkit.WebViewRenderProcessClient;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Map;
import java.util.concurrent.Executor;

@SystemApi
public interface WebViewProvider {
    public void addJavascriptInterface(Object var1, String var2);

    public boolean canGoBack();

    public boolean canGoBackOrForward(int var1);

    public boolean canGoForward();

    public boolean canZoomIn();

    public boolean canZoomOut();

    public Picture capturePicture();

    public void clearCache(boolean var1);

    public void clearFormData();

    public void clearHistory();

    public void clearMatches();

    public void clearSslPreferences();

    public void clearView();

    public WebBackForwardList copyBackForwardList();

    public PrintDocumentAdapter createPrintDocumentAdapter(String var1);

    public WebMessagePort[] createWebMessageChannel();

    public void destroy();

    public void documentHasImages(Message var1);

    public void dumpViewHierarchyWithProperties(BufferedWriter var1, int var2);

    public void evaluateJavaScript(String var1, ValueCallback<String> var2);

    public int findAll(String var1);

    public void findAllAsync(String var1);

    public View findHierarchyView(String var1, int var2);

    public void findNext(boolean var1);

    public void flingScroll(int var1, int var2);

    public void freeMemory();

    public SslCertificate getCertificate();

    public int getContentHeight();

    public int getContentWidth();

    public Bitmap getFavicon();

    public WebView.HitTestResult getHitTestResult();

    public String[] getHttpAuthUsernamePassword(String var1, String var2);

    public String getOriginalUrl();

    public int getProgress();

    public boolean getRendererPriorityWaivedWhenNotVisible();

    public int getRendererRequestedPriority();

    public float getScale();

    public ScrollDelegate getScrollDelegate();

    public WebSettings getSettings();

    default public TextClassifier getTextClassifier() {
        return TextClassifier.NO_OP;
    }

    public String getTitle();

    public String getTouchIconUrl();

    public String getUrl();

    public ViewDelegate getViewDelegate();

    public int getVisibleTitleHeight();

    public WebChromeClient getWebChromeClient();

    public WebViewClient getWebViewClient();

    public WebViewRenderProcess getWebViewRenderProcess();

    public WebViewRenderProcessClient getWebViewRenderProcessClient();

    public View getZoomControls();

    public void goBack();

    public void goBackOrForward(int var1);

    public void goForward();

    public void init(Map<String, Object> var1, boolean var2);

    public void insertVisualStateCallback(long var1, WebView.VisualStateCallback var3);

    public void invokeZoomPicker();

    public boolean isPaused();

    public boolean isPrivateBrowsingEnabled();

    public void loadData(String var1, String var2, String var3);

    public void loadDataWithBaseURL(String var1, String var2, String var3, String var4, String var5);

    public void loadUrl(String var1);

    public void loadUrl(String var1, Map<String, String> var2);

    public void notifyFindDialogDismissed();

    public void onPause();

    public void onResume();

    public boolean overlayHorizontalScrollbar();

    public boolean overlayVerticalScrollbar();

    public boolean pageDown(boolean var1);

    public boolean pageUp(boolean var1);

    public void pauseTimers();

    public void postMessageToMainFrame(WebMessage var1, Uri var2);

    public void postUrl(String var1, byte[] var2);

    public void reload();

    public void removeJavascriptInterface(String var1);

    public void requestFocusNodeHref(Message var1);

    public void requestImageRef(Message var1);

    public boolean restorePicture(Bundle var1, File var2);

    public WebBackForwardList restoreState(Bundle var1);

    public void resumeTimers();

    public void savePassword(String var1, String var2, String var3);

    public boolean savePicture(Bundle var1, File var2);

    public WebBackForwardList saveState(Bundle var1);

    public void saveWebArchive(String var1);

    public void saveWebArchive(String var1, boolean var2, ValueCallback<String> var3);

    public void setCertificate(SslCertificate var1);

    public void setDownloadListener(DownloadListener var1);

    public void setFindListener(WebView.FindListener var1);

    public void setHorizontalScrollbarOverlay(boolean var1);

    public void setHttpAuthUsernamePassword(String var1, String var2, String var3, String var4);

    public void setInitialScale(int var1);

    public void setMapTrackballToArrowKeys(boolean var1);

    public void setNetworkAvailable(boolean var1);

    public void setPictureListener(WebView.PictureListener var1);

    public void setRendererPriorityPolicy(int var1, boolean var2);

    default public void setTextClassifier(TextClassifier textClassifier) {
    }

    public void setVerticalScrollbarOverlay(boolean var1);

    public void setWebChromeClient(WebChromeClient var1);

    public void setWebViewClient(WebViewClient var1);

    public void setWebViewRenderProcessClient(Executor var1, WebViewRenderProcessClient var2);

    public boolean showFindDialog(String var1, boolean var2);

    public void stopLoading();

    public boolean zoomBy(float var1);

    public boolean zoomIn();

    public boolean zoomOut();

    public static interface ScrollDelegate {
        public int computeHorizontalScrollOffset();

        public int computeHorizontalScrollRange();

        public void computeScroll();

        public int computeVerticalScrollExtent();

        public int computeVerticalScrollOffset();

        public int computeVerticalScrollRange();
    }

    public static interface ViewDelegate {
        default public void autofill(SparseArray<AutofillValue> sparseArray) {
        }

        public boolean dispatchKeyEvent(KeyEvent var1);

        public View findFocus(View var1);

        public AccessibilityNodeProvider getAccessibilityNodeProvider();

        public Handler getHandler(Handler var1);

        default public boolean isVisibleToUserForAutofill(int n) {
            return true;
        }

        public void onActivityResult(int var1, int var2, Intent var3);

        public void onAttachedToWindow();

        default public boolean onCheckIsTextEditor() {
            return false;
        }

        public void onConfigurationChanged(Configuration var1);

        public InputConnection onCreateInputConnection(EditorInfo var1);

        public void onDetachedFromWindow();

        public boolean onDragEvent(DragEvent var1);

        public void onDraw(Canvas var1);

        public void onDrawVerticalScrollBar(Canvas var1, Drawable var2, int var3, int var4, int var5, int var6);

        public void onFinishTemporaryDetach();

        public void onFocusChanged(boolean var1, int var2, Rect var3);

        public boolean onGenericMotionEvent(MotionEvent var1);

        public boolean onHoverEvent(MotionEvent var1);

        public void onInitializeAccessibilityEvent(AccessibilityEvent var1);

        public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo var1);

        public boolean onKeyDown(int var1, KeyEvent var2);

        public boolean onKeyMultiple(int var1, int var2, KeyEvent var3);

        public boolean onKeyUp(int var1, KeyEvent var2);

        public void onMeasure(int var1, int var2);

        default public void onMovedToDisplay(int n, Configuration configuration) {
        }

        public void onOverScrolled(int var1, int var2, boolean var3, boolean var4);

        default public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int n) {
        }

        default public void onProvideContentCaptureStructure(ViewStructure viewStructure, int n) {
        }

        public void onProvideVirtualStructure(ViewStructure var1);

        public void onScrollChanged(int var1, int var2, int var3, int var4);

        public void onSizeChanged(int var1, int var2, int var3, int var4);

        public void onStartTemporaryDetach();

        public boolean onTouchEvent(MotionEvent var1);

        public boolean onTrackballEvent(MotionEvent var1);

        public void onVisibilityChanged(View var1, int var2);

        public void onWindowFocusChanged(boolean var1);

        public void onWindowVisibilityChanged(int var1);

        public boolean performAccessibilityAction(int var1, Bundle var2);

        public boolean performLongClick();

        public void preDispatchDraw(Canvas var1);

        public boolean requestChildRectangleOnScreen(View var1, Rect var2, boolean var3);

        public boolean requestFocus(int var1, Rect var2);

        public void setBackgroundColor(int var1);

        public boolean setFrame(int var1, int var2, int var3, int var4);

        public void setLayerType(int var1, Paint var2);

        public void setLayoutParams(ViewGroup.LayoutParams var1);

        public void setOverScrollMode(int var1);

        public void setScrollBarStyle(int var1);

        public boolean shouldDelayChildPressedState();
    }

}

