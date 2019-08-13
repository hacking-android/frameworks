/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.app.ActivityThread;
import android.app.ApplicationLoaders;
import android.app.LoadedApk;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.net.LocalSocket;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebViewFactory;
import android.webkit.WebViewLibraryLoader;
import com.android.internal.os.ChildZygoteInit;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteConnection;
import com.android.internal.os.ZygoteServer;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

class WebViewZygoteInit {
    public static final String TAG = "WebViewZygoteInit";

    WebViewZygoteInit() {
    }

    public static void main(String[] arrstring) {
        Log.i(TAG, "Starting WebViewZygoteInit");
        ChildZygoteInit.runZygoteServer(new WebViewZygoteServer(), arrstring);
    }

    private static class WebViewZygoteConnection
    extends ZygoteConnection {
        WebViewZygoteConnection(LocalSocket localSocket, String string2) throws IOException {
            super(localSocket, string2);
        }

        private void doPreload(ClassLoader object, String object2) {
            int n;
            block13 : {
                boolean bl;
                block12 : {
                    boolean bl2;
                    boolean bl3;
                    block11 : {
                        WebViewLibraryLoader.loadNativeLibrary((ClassLoader)object, (String)object2);
                        bl3 = false;
                        bl2 = false;
                        n = 1;
                        bl = bl3;
                        object = WebViewFactory.getWebViewProviderClass((ClassLoader)object);
                        bl = bl3;
                        object2 = ((Class)object).getMethod("preloadInZygote", new Class[0]);
                        bl = bl3;
                        ((AccessibleObject)object2).setAccessible(true);
                        bl = bl3;
                        if (((Method)object2).getReturnType() == Boolean.TYPE) break block11;
                        bl = bl3;
                        Log.e(WebViewZygoteInit.TAG, "Unexpected return type: preloadInZygote must return boolean");
                        bl = bl2;
                    }
                    bl = bl3;
                    bl = bl2 = ((Boolean)((Class)object).getMethod("preloadInZygote", new Class[0]).invoke(null, new Object[0])).booleanValue();
                    if (bl2) break block12;
                    bl = bl2;
                    try {
                        Log.e(WebViewZygoteInit.TAG, "preloadInZygote returned false");
                        bl = bl2;
                    }
                    catch (ReflectiveOperationException reflectiveOperationException) {
                        Log.e(WebViewZygoteInit.TAG, "Exception while preloading package", reflectiveOperationException);
                    }
                }
                try {
                    object = this.getSocketOutputStream();
                    if (bl) break block13;
                    n = 0;
                }
                catch (IOException iOException) {
                    throw new IllegalStateException("Error writing to command socket", iOException);
                }
            }
            ((DataOutputStream)object).writeInt(n);
        }

        @Override
        protected boolean canPreloadApp() {
            return true;
        }

        @Override
        protected void handlePreloadApp(ApplicationInfo applicationInfo) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Beginning application preload for ");
            stringBuilder.append(applicationInfo.packageName);
            Log.i(WebViewZygoteInit.TAG, stringBuilder.toString());
            this.doPreload(new LoadedApk(null, applicationInfo, null, null, false, true, false).getClassLoader(), WebViewFactory.getWebViewLibrary(applicationInfo));
            Zygote.allowAppFilesAcrossFork(applicationInfo);
            Log.i(WebViewZygoteInit.TAG, "Application preload done");
        }

        @Override
        protected void handlePreloadPackage(String arrstring, String object, String string2, String string3) {
            Log.i(WebViewZygoteInit.TAG, "Beginning package preload");
            object = ApplicationLoaders.getDefault().createAndCacheWebViewClassLoader((String)arrstring, (String)object, string3);
            arrstring = TextUtils.split((String)arrstring, File.pathSeparator);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                Zygote.nativeAllowFileAcrossFork(arrstring[i]);
            }
            this.doPreload((ClassLoader)object, string2);
            Log.i(WebViewZygoteInit.TAG, "Package preload done");
        }

        @Override
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override
        protected void preload() {
        }
    }

    private static class WebViewZygoteServer
    extends ZygoteServer {
        private WebViewZygoteServer() {
        }

        @Override
        protected ZygoteConnection createNewConnection(LocalSocket localSocket, String string2) throws IOException {
            return new WebViewZygoteConnection(localSocket, string2);
        }
    }

}

