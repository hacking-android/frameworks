/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.app.ResourcesManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.RecordingCanvas;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewRootImpl;
import android.webkit.LegacyErrorStrings;
import android.webkit.WebViewFactory;
import com.android.internal.util.ArrayUtils;

@SystemApi
public final class WebViewDelegate {
    @UnsupportedAppUsage
    WebViewDelegate() {
    }

    public void addWebViewAssetPath(Context arrstring) {
        String[] arrstring2 = WebViewFactory.getLoadedPackageInfo().applicationInfo.getAllApkPaths();
        ApplicationInfo applicationInfo = arrstring.getApplicationInfo();
        arrstring = applicationInfo.sharedLibraryFiles;
        int n = arrstring2.length;
        for (int i = 0; i < n; ++i) {
            arrstring = ArrayUtils.appendElement(String.class, arrstring, arrstring2[i]);
        }
        if (arrstring != applicationInfo.sharedLibraryFiles) {
            applicationInfo.sharedLibraryFiles = arrstring;
            ResourcesManager.getInstance().appendLibAssetsForMainAssetPath(applicationInfo.getBaseResourcePath(), arrstring2);
        }
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long l) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas)canvas).drawGLFunctor2(l, null);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(canvas.getClass().getName());
        stringBuilder.append(" is not a DisplayList canvas");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Deprecated
    public void callDrawGlFunction(Canvas canvas, long l, Runnable object) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas)canvas).drawGLFunctor2(l, (Runnable)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(canvas.getClass().getName());
        ((StringBuilder)object).append(" is not a DisplayList canvas");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Deprecated
    public boolean canInvokeDrawGlFunctor(View view) {
        return true;
    }

    @Deprecated
    public void detachDrawGlFunctor(View object, long l) {
        object = ((View)object).getViewRootImpl();
        if (l != 0L && object != null) {
            ((ViewRootImpl)object).detachFunctor(l);
        }
    }

    public void drawWebViewFunctor(Canvas canvas, int n) {
        if (canvas instanceof RecordingCanvas) {
            ((RecordingCanvas)canvas).drawWebViewFunctor(n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(canvas.getClass().getName());
        stringBuilder.append(" is not a RecordingCanvas canvas");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Application getApplication() {
        return ActivityThread.currentApplication();
    }

    public String getDataDirectorySuffix() {
        return WebViewFactory.getDataDirectorySuffix();
    }

    public String getErrorString(Context context, int n) {
        return LegacyErrorStrings.getString(n, context);
    }

    public int getPackageId(Resources object, String string2) {
        object = ((Resources)object).getAssets().getAssignedPackageIdentifiers();
        for (int i = 0; i < ((SparseArray)object).size(); ++i) {
            if (!string2.equals((String)((SparseArray)object).valueAt(i))) continue;
            return ((SparseArray)object).keyAt(i);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Package not found: ");
        ((StringBuilder)object).append(string2);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    @Deprecated
    public void invokeDrawGlFunctor(View view, long l, boolean bl) {
        ViewRootImpl.invokeFunctor(l, bl);
    }

    public boolean isMultiProcessEnabled() {
        try {
            boolean bl = WebViewFactory.getUpdateService().isMultiProcessEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isTraceTagEnabled() {
        return Trace.isTagEnabled(16L);
    }

    public void setOnTraceEnabledChangeListener(final OnTraceEnabledChangeListener onTraceEnabledChangeListener) {
        SystemProperties.addChangeCallback(new Runnable(){

            @Override
            public void run() {
                onTraceEnabledChangeListener.onTraceEnabledChange(WebViewDelegate.this.isTraceTagEnabled());
            }
        });
    }

    public static interface OnTraceEnabledChangeListener {
        public void onTraceEnabledChange(boolean var1);
    }

}

