/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.AppGlobals;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.Trace;
import android.util.AndroidException;
import android.util.AndroidRuntimeException;
import android.util.ArraySet;
import android.util.Log;
import android.webkit.IWebViewUpdateService;
import android.webkit.WebViewDelegate;
import android.webkit.WebViewFactoryProvider;
import android.webkit.WebViewLibraryLoader;
import android.webkit.WebViewProviderResponse;
import android.webkit.WebViewZygote;
import java.io.File;
import java.lang.reflect.Method;

@SystemApi
public final class WebViewFactory {
    private static final String CHROMIUM_WEBVIEW_FACTORY = "com.android.webview.chromium.WebViewChromiumFactoryProviderForQ";
    private static final String CHROMIUM_WEBVIEW_FACTORY_METHOD = "create";
    private static final boolean DEBUG = false;
    public static final int LIBLOAD_ADDRESS_SPACE_NOT_RESERVED = 2;
    public static final int LIBLOAD_FAILED_JNI_CALL = 7;
    public static final int LIBLOAD_FAILED_LISTING_WEBVIEW_PACKAGES = 4;
    public static final int LIBLOAD_FAILED_TO_FIND_NAMESPACE = 10;
    public static final int LIBLOAD_FAILED_TO_LOAD_LIBRARY = 6;
    public static final int LIBLOAD_FAILED_TO_OPEN_RELRO_FILE = 5;
    public static final int LIBLOAD_FAILED_WAITING_FOR_RELRO = 3;
    public static final int LIBLOAD_FAILED_WAITING_FOR_WEBVIEW_REASON_UNKNOWN = 8;
    public static final int LIBLOAD_SUCCESS = 0;
    public static final int LIBLOAD_WRONG_PACKAGE_NAME = 1;
    private static final String LOGTAG = "WebViewFactory";
    private static String WEBVIEW_UPDATE_SERVICE_NAME;
    private static String sDataDirectorySuffix;
    @UnsupportedAppUsage
    private static PackageInfo sPackageInfo;
    @UnsupportedAppUsage
    private static WebViewFactoryProvider sProviderInstance;
    private static final Object sProviderLock;
    private static boolean sWebViewDisabled;
    private static Boolean sWebViewSupported;

    static {
        sProviderLock = new Object();
        WEBVIEW_UPDATE_SERVICE_NAME = "webviewupdate";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void disableWebView() {
        Object object = sProviderLock;
        synchronized (object) {
            if (sProviderInstance == null) {
                sWebViewDisabled = true;
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Can't disable WebView: WebView already initialized");
            throw illegalStateException;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static String getDataDirectorySuffix() {
        Object object = sProviderLock;
        synchronized (object) {
            return sDataDirectorySuffix;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static PackageInfo getLoadedPackageInfo() {
        Object object = sProviderLock;
        synchronized (object) {
            return sPackageInfo;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    static WebViewFactoryProvider getProvider() {
        Throwable throwable2222;
        Object object2;
        Object object = sProviderLock;
        // MONITORENTER : object
        if (sProviderInstance != null) {
            WebViewFactoryProvider webViewFactoryProvider = sProviderInstance;
            // MONITOREXIT : object
            return webViewFactoryProvider;
        }
        int n = Process.myUid();
        if (n != 0 && n != 1000 && n != 1001 && n != 1027 && n != 1002) {
            if (!WebViewFactory.isWebViewSupported()) {
                UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                throw unsupportedOperationException;
            }
            if (sWebViewDisabled) {
                IllegalStateException illegalStateException = new IllegalStateException("WebView.disableWebView() was called: WebView is disabled");
                throw illegalStateException;
            }
            Trace.traceBegin(16L, "WebViewFactory.getProvider()");
            Object object3 = WebViewFactory.getProviderClass();
            object2 = null;
            try {
                object2 = object3 = ((Class)object3).getMethod(CHROMIUM_WEBVIEW_FACTORY_METHOD, WebViewDelegate.class);
            }
            catch (Exception exception) {
                // empty catch block
            }
            Trace.traceBegin(16L, "WebViewFactoryProvider invocation");
            object3 = new WebViewDelegate();
            sProviderInstance = (WebViewFactoryProvider)((Method)object2).invoke(null, object3);
            object2 = sProviderInstance;
            try {
                Trace.traceEnd(16L);
                return object2;
            }
            finally {
                Trace.traceEnd(16L);
            }
        }
        UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException("For security reasons, WebView is not allowed in privileged processes");
        throw unsupportedOperationException;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                Log.e(LOGTAG, "error instantiating provider", exception);
                object2 = new AndroidRuntimeException(exception);
                throw object2;
            }
        }
        Trace.traceEnd(16L);
        throw throwable2222;
    }

    /*
     * Exception decompiling
     */
    @UnsupportedAppUsage
    private static Class<WebViewFactoryProvider> getProviderClass() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @UnsupportedAppUsage
    public static IWebViewUpdateService getUpdateService() {
        if (WebViewFactory.isWebViewSupported()) {
            return WebViewFactory.getUpdateServiceUnchecked();
        }
        return null;
    }

    static IWebViewUpdateService getUpdateServiceUnchecked() {
        return IWebViewUpdateService.Stub.asInterface(ServiceManager.getService(WEBVIEW_UPDATE_SERVICE_NAME));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private static Context getWebViewContextAndSetProvider() throws MissingWebViewPackageException {
        Object object = AppGlobals.getInitialApplication();
        Trace.traceBegin(16L, "WebViewUpdateService.waitForAndGetProvider()");
        Object object2 = WebViewFactory.getUpdateService().waitForAndGetProvider();
        {
            catch (Throwable throwable) {
                Trace.traceEnd(16L);
                throw throwable;
            }
        }
        Trace.traceEnd(16L);
        if (((WebViewProviderResponse)object2).status != 0 && ((WebViewProviderResponse)object2).status != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to load WebView provider: ");
            stringBuilder.append(WebViewFactory.getWebViewPreparationErrorReason(((WebViewProviderResponse)object2).status));
            object = new MissingWebViewPackageException(stringBuilder.toString());
            throw object;
        }
        Trace.traceBegin(16L, "ActivityManager.addPackageDependency()");
        ActivityManager.getService().addPackageDependency(object2.packageInfo.packageName);
        {
            catch (Throwable throwable) {
                Trace.traceEnd(16L);
                throw throwable;
            }
        }
        Trace.traceEnd(16L);
        Object object3 = ((ContextWrapper)object).getPackageManager();
        Trace.traceBegin(16L, "PackageManager.getPackageInfo()");
        object3 = ((PackageManager)object3).getPackageInfo(object2.packageInfo.packageName, 268444864);
        {
            catch (Throwable throwable) {
                Trace.traceEnd(16L);
                throw throwable;
            }
        }
        Trace.traceEnd(16L);
        WebViewFactory.verifyPackageInfo(((WebViewProviderResponse)object2).packageInfo, (PackageInfo)object3);
        object2 = ((PackageInfo)object3).applicationInfo;
        Trace.traceBegin(16L, "initialApplication.createApplicationContext");
        object2 = ((ContextWrapper)object).createApplicationContext((ApplicationInfo)object2, 3);
        sPackageInfo = object3;
        {
            catch (Throwable throwable) {
                Trace.traceEnd(16L);
                throw throwable;
            }
        }
        try {
            Trace.traceEnd(16L);
            return object2;
        }
        catch (PackageManager.NameNotFoundException | RemoteException androidException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to load WebView provider: ");
            ((StringBuilder)object2).append(androidException);
            throw new MissingWebViewPackageException(((StringBuilder)object2).toString());
        }
    }

    public static String getWebViewLibrary(ApplicationInfo applicationInfo) {
        if (applicationInfo.metaData != null) {
            return applicationInfo.metaData.getString("com.android.webview.WebViewLibrary");
        }
        return null;
    }

    private static String getWebViewPreparationErrorReason(int n) {
        if (n != 3) {
            if (n != 4) {
                if (n != 8) {
                    return "Unknown";
                }
                return "Crashed for unknown reason";
            }
            return "No WebView installed";
        }
        return "Time out waiting for Relro files being created";
    }

    public static Class<WebViewFactoryProvider> getWebViewProviderClass(ClassLoader classLoader) throws ClassNotFoundException {
        return Class.forName(CHROMIUM_WEBVIEW_FACTORY, true, classLoader);
    }

    private static boolean isWebViewSupported() {
        if (sWebViewSupported == null) {
            sWebViewSupported = AppGlobals.getInitialApplication().getPackageManager().hasSystemFeature("android.software.webview");
        }
        return sWebViewSupported;
    }

    public static int loadWebViewNativeLibraryFromPackage(String string2, ClassLoader classLoader) {
        WebViewProviderResponse webViewProviderResponse;
        block7 : {
            if (!WebViewFactory.isWebViewSupported()) {
                return 1;
            }
            try {
                webViewProviderResponse = WebViewFactory.getUpdateService().waitForAndGetProvider();
                if (webViewProviderResponse.status != 0 && webViewProviderResponse.status != 3) {
                    return webViewProviderResponse.status;
                }
                if (webViewProviderResponse.packageInfo.packageName.equals(string2)) break block7;
                return 1;
            }
            catch (RemoteException remoteException) {
                Log.e(LOGTAG, "error waiting for relro creation", remoteException);
                return 8;
            }
        }
        Object object = AppGlobals.getInitialApplication().getPackageManager();
        try {
            object = WebViewFactory.getWebViewLibrary(object.getPackageInfo((String)string2, (int)268435584).applicationInfo);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't find package ");
            stringBuilder.append(string2);
            Log.e(LOGTAG, stringBuilder.toString());
            return 1;
        }
        int n = WebViewLibraryLoader.loadNativeLibrary(classLoader, (String)object);
        if (n == 0) {
            return webViewProviderResponse.status;
        }
        return n;
    }

    public static int onWebViewProviderChanged(PackageInfo packageInfo) {
        int n = 0;
        try {
            int n2;
            n = n2 = WebViewLibraryLoader.prepareNativeLibraries(packageInfo);
        }
        catch (Throwable throwable) {
            Log.e(LOGTAG, "error preparing webview native library", throwable);
        }
        WebViewZygote.onWebViewProviderChanged(packageInfo);
        return n;
    }

    public static void prepareWebViewInZygote() {
        try {
            WebViewLibraryLoader.reserveAddressSpaceInZygote();
        }
        catch (Throwable throwable) {
            Log.e(LOGTAG, "error preparing native loader", throwable);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void setDataDirectorySuffix(String object) {
        Object object2 = sProviderLock;
        synchronized (object2) {
            if (sProviderInstance != null) {
                object = new IllegalStateException("Can't set data directory suffix: WebView already initialized");
                throw object;
            }
            if (((String)object).indexOf(File.separatorChar) < 0) {
                sDataDirectorySuffix = object;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Suffix ");
            stringBuilder.append((String)object);
            stringBuilder.append(" contains a path separator");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    private static boolean signaturesEquals(Signature[] object, Signature[] arrsignature) {
        int n;
        int n2 = 0;
        boolean bl = false;
        if (object == null) {
            if (arrsignature == null) {
                bl = true;
            }
            return bl;
        }
        if (arrsignature == null) {
            return false;
        }
        ArraySet<Signature> arraySet = new ArraySet<Signature>();
        int n3 = ((Signature[])object).length;
        for (n = 0; n < n3; ++n) {
            arraySet.add(object[n]);
        }
        object = new ArraySet();
        n3 = arrsignature.length;
        for (n = n2; n < n3; ++n) {
            ((ArraySet)object).add(arrsignature[n]);
        }
        return arraySet.equals(object);
    }

    private static void verifyPackageInfo(PackageInfo object, PackageInfo packageInfo) throws MissingWebViewPackageException {
        if (((PackageInfo)object).packageName.equals(packageInfo.packageName)) {
            if (((PackageInfo)object).getLongVersionCode() <= packageInfo.getLongVersionCode()) {
                if (WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo) != null) {
                    if (WebViewFactory.signaturesEquals(((PackageInfo)object).signatures, packageInfo.signatures)) {
                        return;
                    }
                    throw new MissingWebViewPackageException("Failed to verify WebView provider, signature mismatch");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Tried to load an invalid WebView provider: ");
                ((StringBuilder)object).append(packageInfo.packageName);
                throw new MissingWebViewPackageException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to verify WebView provider, version code is lower than expected: ");
            stringBuilder.append(((PackageInfo)object).getLongVersionCode());
            stringBuilder.append(" actual: ");
            stringBuilder.append(packageInfo.getLongVersionCode());
            throw new MissingWebViewPackageException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to verify WebView provider, packageName mismatch, expected: ");
        stringBuilder.append(((PackageInfo)object).packageName);
        stringBuilder.append(" actual: ");
        stringBuilder.append(packageInfo.packageName);
        throw new MissingWebViewPackageException(stringBuilder.toString());
    }

    static class MissingWebViewPackageException
    extends Exception {
        public MissingWebViewPackageException(Exception exception) {
            super(exception);
        }

        public MissingWebViewPackageException(String string2) {
            super(string2);
        }
    }

}

