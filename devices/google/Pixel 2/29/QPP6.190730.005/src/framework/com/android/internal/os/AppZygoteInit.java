/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.app.ZygotePreload;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;
import android.net.LocalSocket;
import android.util.Log;
import com.android.internal.os.ChildZygoteInit;
import com.android.internal.os.Zygote;
import com.android.internal.os.ZygoteConnection;
import com.android.internal.os.ZygoteServer;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;

class AppZygoteInit {
    public static final String TAG = "AppZygoteInit";
    private static ZygoteServer sServer;

    AppZygoteInit() {
    }

    public static void main(String[] arrstring) {
        ChildZygoteInit.runZygoteServer(new AppZygoteServer(), arrstring);
    }

    private static class AppZygoteConnection
    extends ZygoteConnection {
        AppZygoteConnection(LocalSocket localSocket, String string2) throws IOException {
            super(localSocket, string2);
        }

        @Override
        protected boolean canPreloadApp() {
            return true;
        }

        @Override
        protected void handlePreloadApp(ApplicationInfo object) {
            int n;
            block9 : {
                Object object2;
                block8 : {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Beginning application preload for ");
                    ((StringBuilder)object2).append(((ApplicationInfo)object).packageName);
                    Log.i(AppZygoteInit.TAG, ((StringBuilder)object2).toString());
                    object2 = new LoadedApk(null, (ApplicationInfo)object, null, null, false, true, false).getClassLoader();
                    Zygote.allowAppFilesAcrossFork((ApplicationInfo)object);
                    Object object3 = ((ApplicationInfo)object).zygotePreloadName;
                    n = 1;
                    if (object3 != null) {
                        try {
                            object3 = ComponentName.createRelative(((ApplicationInfo)object).packageName, ((ApplicationInfo)object).zygotePreloadName);
                            Serializable serializable = Class.forName(((ComponentName)object3).getClassName(), true, (ClassLoader)object2);
                            if (!ZygotePreload.class.isAssignableFrom((Class<?>)serializable)) {
                                serializable = new StringBuilder();
                                ((StringBuilder)serializable).append(((ComponentName)object3).getClassName());
                                ((StringBuilder)serializable).append(" does not implement ");
                                ((StringBuilder)serializable).append(ZygotePreload.class.getName());
                                Log.e(AppZygoteInit.TAG, ((StringBuilder)serializable).toString());
                                break block8;
                            }
                            ((ZygotePreload)((Class)serializable).getConstructor(new Class[0]).newInstance(new Object[0])).doPreload((ApplicationInfo)object);
                        }
                        catch (ReflectiveOperationException reflectiveOperationException) {
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("AppZygote application preload failed for ");
                            ((StringBuilder)object3).append(((ApplicationInfo)object).zygotePreloadName);
                            Log.e(AppZygoteInit.TAG, ((StringBuilder)object3).toString(), reflectiveOperationException);
                        }
                    } else {
                        Log.i(AppZygoteInit.TAG, "No zygotePreloadName attribute specified.");
                    }
                }
                try {
                    object = this.getSocketOutputStream();
                    if (object2 != null) break block9;
                    n = 0;
                }
                catch (IOException iOException) {
                    throw new IllegalStateException("Error writing to command socket", iOException);
                }
            }
            ((DataOutputStream)object).writeInt(n);
            Log.i(AppZygoteInit.TAG, "Application preload done");
        }

        @Override
        protected boolean isPreloadComplete() {
            return true;
        }

        @Override
        protected void preload() {
        }
    }

    private static class AppZygoteServer
    extends ZygoteServer {
        private AppZygoteServer() {
        }

        @Override
        protected ZygoteConnection createNewConnection(LocalSocket localSocket, String string2) throws IOException {
            return new AppZygoteConnection(localSocket, string2);
        }
    }

}

