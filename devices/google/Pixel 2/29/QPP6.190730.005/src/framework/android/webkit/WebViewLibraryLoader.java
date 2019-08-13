/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package android.webkit;

import android.app.ActivityManagerInternal;
import android.app.ActivityThread;
import android.app.LoadedApk;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.CompatibilityInfo;
import android.os.Build;
import android.os.RemoteException;
import android.util.Log;
import android.webkit.WebViewFactory;
import com.android.internal.annotations.VisibleForTesting;
import com.android.server.LocalServices;
import dalvik.system.VMRuntime;
import java.util.Arrays;

@VisibleForTesting
public class WebViewLibraryLoader {
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_32 = "/data/misc/shared_relro/libwebviewchromium32.relro";
    private static final String CHROMIUM_WEBVIEW_NATIVE_RELRO_64 = "/data/misc/shared_relro/libwebviewchromium64.relro";
    private static final boolean DEBUG = false;
    private static final String LOGTAG = WebViewLibraryLoader.class.getSimpleName();
    private static boolean sAddressSpaceReserved = false;

    static void createRelroFile(boolean bl, String object, String string2) {
        String string3 = bl ? Build.SUPPORTED_64_BIT_ABIS[0] : Build.SUPPORTED_32_BIT_ABIS[0];
        Runnable runnable = new Runnable(){

            @Override
            public void run() {
                try {
                    String string2 = LOGTAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("relro file creator for ");
                    stringBuilder.append(String.this);
                    stringBuilder.append(" crashed. Proceeding without");
                    Log.e(string2, stringBuilder.toString());
                    WebViewFactory.getUpdateService().notifyRelroCreationCompleted();
                }
                catch (RemoteException remoteException) {
                    String string3 = LOGTAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot reach WebViewUpdateService. ");
                    stringBuilder.append(remoteException.getMessage());
                    Log.e(string3, stringBuilder.toString());
                }
            }
        };
        try {
            ActivityManagerInternal activityManagerInternal = LocalServices.getService(ActivityManagerInternal.class);
            String string4 = RelroFileCreator.class.getName();
            CharSequence charSequence = new StringBuilder();
            charSequence.append("WebViewLoader-");
            charSequence.append(string3);
            charSequence = charSequence.toString();
            if (!activityManagerInternal.startIsolatedProcess(string4, new String[]{object, string2}, (String)charSequence, string3, 1037, runnable)) {
                object = new Exception("Failed to start the relro file creator process");
                throw object;
            }
        }
        catch (Throwable throwable) {
            string2 = LOGTAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("error starting relro file creator for abi ");
            ((StringBuilder)object).append(string3);
            Log.e(string2, ((StringBuilder)object).toString(), throwable);
            runnable.run();
        }
    }

    private static int createRelros(String string2, String string3) {
        int n = 0;
        if (Build.SUPPORTED_32_BIT_ABIS.length > 0) {
            WebViewLibraryLoader.createRelroFile(false, string2, string3);
            n = 0 + 1;
        }
        int n2 = n;
        if (Build.SUPPORTED_64_BIT_ABIS.length > 0) {
            WebViewLibraryLoader.createRelroFile(true, string2, string3);
            n2 = n + 1;
        }
        return n2;
    }

    public static int loadNativeLibrary(ClassLoader classLoader, String string2) {
        if (!sAddressSpaceReserved) {
            Log.e(LOGTAG, "can't load with relro file; address space not reserved");
            return 2;
        }
        String string3 = VMRuntime.getRuntime().is64Bit() ? CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : CHROMIUM_WEBVIEW_NATIVE_RELRO_32;
        int n = WebViewLibraryLoader.nativeLoadWithRelroFile(string2, string3, classLoader);
        if (n != 0) {
            Log.w(LOGTAG, "failed to load with relro file, proceeding without");
        }
        return n;
    }

    static native boolean nativeCreateRelroFile(String var0, String var1, ClassLoader var2);

    static native int nativeLoadWithRelroFile(String var0, String var1, ClassLoader var2);

    static native boolean nativeReserveAddressSpace(long var0);

    static int prepareNativeLibraries(PackageInfo packageInfo) {
        String string2 = WebViewFactory.getWebViewLibrary(packageInfo.applicationInfo);
        if (string2 == null) {
            return 0;
        }
        return WebViewLibraryLoader.createRelros(packageInfo.packageName, string2);
    }

    static void reserveAddressSpaceInZygote() {
        System.loadLibrary("webviewchromium_loader");
        long l = VMRuntime.getRuntime().is64Bit() ? 0x40000000L : 136314880L;
        sAddressSpaceReserved = WebViewLibraryLoader.nativeReserveAddressSpace(l);
        if (!sAddressSpaceReserved) {
            String string2 = LOGTAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("reserving ");
            stringBuilder.append(l);
            stringBuilder.append(" bytes of address space failed");
            Log.e(string2, stringBuilder.toString());
        }
    }

    private static class RelroFileCreator {
        private RelroFileCreator() {
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static void main(String[] object) {
            boolean bl = VMRuntime.getRuntime().is64Bit();
            try {
                if (((String[])object).length == 2 && object[0] != null && object[1] != null) {
                    Object object2 = object[0];
                    Object object3 = object[1];
                    object = LOGTAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RelroFileCreator (64bit = ");
                    stringBuilder.append(bl);
                    stringBuilder.append("), package: ");
                    stringBuilder.append((String)object2);
                    stringBuilder.append(" library: ");
                    stringBuilder.append((String)object3);
                    Log.v((String)object, stringBuilder.toString());
                    if (!sAddressSpaceReserved) {
                        Log.e(LOGTAG, "can't create relro file; address space not reserved");
                        return;
                    }
                    object2 = ActivityThread.currentActivityThread().getPackageInfo((String)object2, null, 3);
                    object = bl ? WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_64 : WebViewLibraryLoader.CHROMIUM_WEBVIEW_NATIVE_RELRO_32;
                    bl = WebViewLibraryLoader.nativeCreateRelroFile((String)object3, (String)object, ((LoadedApk)object2).getClassLoader());
                    return;
                }
                String string2 = LOGTAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid RelroFileCreator args: ");
                stringBuilder.append(Arrays.toString(object));
                Log.e(string2, stringBuilder.toString());
                return;
            }
            finally {
                try {
                    WebViewFactory.getUpdateServiceUnchecked().notifyRelroCreationCompleted();
                }
                catch (RemoteException remoteException) {
                    Log.e(LOGTAG, "error notifying update service", remoteException);
                }
                if (!false) {
                    Log.e(LOGTAG, "failed to create relro file");
                }
                System.exit(0);
            }
        }
    }

}

