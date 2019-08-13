/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BaseDexClassLoader
 *  dalvik.system.BaseDexClassLoader$Reporter
 *  dalvik.system.VMRuntime
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.ApplicationLoaders;
import android.app.ContextImpl;
import android.app.DexLoadReporter;
import android.app.IActivityManager;
import android.app.IServiceConnection;
import android.app.Instrumentation;
import android.app.IntentReceiverLeaked;
import android.app.ResourcesManager;
import android.app.ServiceConnectionLeaked;
import android.app._$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IIntentReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.SharedLibraryInfo;
import android.content.pm.dex.ArtManager;
import android.content.pm.split.SplitDependencyLoader;
import android.content.res.AssetManager;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import android.view.DisplayAdjustments;
import com.android.internal.util.ArrayUtils;
import dalvik.system.BaseDexClassLoader;
import dalvik.system.VMRuntime;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executor;

public final class LoadedApk {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final boolean DEBUG = false;
    private static final String PROPERTY_NAME_APPEND_NATIVE = "pi.append_native_lib_paths";
    static final String TAG = "LoadedApk";
    @UnsupportedAppUsage
    private final ActivityThread mActivityThread;
    private AppComponentFactory mAppComponentFactory;
    @UnsupportedAppUsage
    private String mAppDir;
    @UnsupportedAppUsage
    private Application mApplication;
    @UnsupportedAppUsage
    private ApplicationInfo mApplicationInfo;
    @UnsupportedAppUsage
    private final ClassLoader mBaseClassLoader;
    @UnsupportedAppUsage
    private ClassLoader mClassLoader;
    private File mCredentialProtectedDataDirFile;
    @UnsupportedAppUsage
    private String mDataDir;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private File mDataDirFile;
    private ClassLoader mDefaultClassLoader;
    private File mDeviceProtectedDataDirFile;
    @UnsupportedAppUsage
    private final DisplayAdjustments mDisplayAdjustments = new DisplayAdjustments();
    private final boolean mIncludeCode;
    @UnsupportedAppUsage
    private String mLibDir;
    private String[] mOverlayDirs;
    @UnsupportedAppUsage
    final String mPackageName;
    @UnsupportedAppUsage
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mReceivers = new ArrayMap();
    private final boolean mRegisterPackage;
    @UnsupportedAppUsage
    private String mResDir;
    @UnsupportedAppUsage
    Resources mResources;
    private final boolean mSecurityViolation;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mServices = new ArrayMap();
    private String[] mSplitAppDirs;
    private String[] mSplitClassLoaderNames;
    private SplitDependencyLoaderImpl mSplitLoader;
    private String[] mSplitNames;
    @UnsupportedAppUsage
    private String[] mSplitResDirs;
    private final ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> mUnboundServices = new ArrayMap();
    private final ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> mUnregisteredReceivers = new ArrayMap();

    LoadedApk(ActivityThread activityThread) {
        this.mActivityThread = activityThread;
        this.mApplicationInfo = new ApplicationInfo();
        this.mApplicationInfo.packageName = "android";
        this.mPackageName = "android";
        this.mAppDir = null;
        this.mResDir = null;
        this.mSplitAppDirs = null;
        this.mSplitResDirs = null;
        this.mSplitClassLoaderNames = null;
        this.mOverlayDirs = null;
        this.mDataDir = null;
        this.mDataDirFile = null;
        this.mDeviceProtectedDataDirFile = null;
        this.mCredentialProtectedDataDirFile = null;
        this.mLibDir = null;
        this.mBaseClassLoader = null;
        this.mSecurityViolation = false;
        this.mIncludeCode = true;
        this.mRegisterPackage = false;
        this.mResources = Resources.getSystem();
        this.mDefaultClassLoader = ClassLoader.getSystemClassLoader();
        this.mAppComponentFactory = this.createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    public LoadedApk(ActivityThread activityThread, ApplicationInfo applicationInfo, CompatibilityInfo compatibilityInfo, ClassLoader classLoader, boolean bl, boolean bl2, boolean bl3) {
        this.mActivityThread = activityThread;
        this.setApplicationInfo(applicationInfo);
        this.mPackageName = applicationInfo.packageName;
        this.mBaseClassLoader = classLoader;
        this.mSecurityViolation = bl;
        this.mIncludeCode = bl2;
        this.mRegisterPackage = bl3;
        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
        this.mAppComponentFactory = this.createAppFactory(this.mApplicationInfo, this.mBaseClassLoader);
    }

    private static ApplicationInfo adjustNativeLibraryPaths(ApplicationInfo applicationInfo) {
        if (applicationInfo.primaryCpuAbi != null && applicationInfo.secondaryCpuAbi != null) {
            String string2 = VMRuntime.getRuntime().vmInstructionSet();
            CharSequence charSequence = VMRuntime.getInstructionSet((String)applicationInfo.secondaryCpuAbi);
            CharSequence charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("ro.dalvik.vm.isa.");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = SystemProperties.get(((StringBuilder)charSequence2).toString());
            if (!((String)charSequence2).isEmpty()) {
                charSequence = charSequence2;
            }
            if (string2.equals(charSequence)) {
                applicationInfo = new ApplicationInfo(applicationInfo);
                applicationInfo.nativeLibraryDir = applicationInfo.secondaryNativeLibraryDir;
                applicationInfo.primaryCpuAbi = applicationInfo.secondaryCpuAbi;
                return applicationInfo;
            }
        }
        return applicationInfo;
    }

    private StrictMode.ThreadPolicy allowThreadDiskReads() {
        if (this.mActivityThread == null) {
            return null;
        }
        return StrictMode.allowThreadDiskReads();
    }

    private static void appendApkLibPathIfNeeded(String string2, ApplicationInfo applicationInfo, List<String> list) {
        if (list != null && applicationInfo.primaryCpuAbi != null && string2.endsWith(".apk") && applicationInfo.targetSdkVersion >= 26) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("!/lib/");
            stringBuilder.append(applicationInfo.primaryCpuAbi);
            list.add(stringBuilder.toString());
        }
    }

    private static void appendSharedLibrariesLibPathsIfNeeded(List<SharedLibraryInfo> object, ApplicationInfo applicationInfo, Set<String> set, List<String> list) {
        if (object == null) {
            return;
        }
        object = object.iterator();
        while (object.hasNext()) {
            SharedLibraryInfo sharedLibraryInfo = (SharedLibraryInfo)object.next();
            Object object2 = sharedLibraryInfo.getAllCodePaths();
            set.addAll((Collection<String>)object2);
            object2 = object2.iterator();
            while (object2.hasNext()) {
                LoadedApk.appendApkLibPathIfNeeded((String)object2.next(), applicationInfo, list);
            }
            LoadedApk.appendSharedLibrariesLibPathsIfNeeded(sharedLibraryInfo.getDependencies(), applicationInfo, set, list);
        }
    }

    private AppComponentFactory createAppFactory(ApplicationInfo object, ClassLoader classLoader) {
        if (((ApplicationInfo)object).appComponentFactory != null && classLoader != null) {
            try {
                object = (AppComponentFactory)classLoader.loadClass(((ApplicationInfo)object).appComponentFactory).newInstance();
                return object;
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException reflectiveOperationException) {
                Slog.e(TAG, "Unable to instantiate appComponentFactory", reflectiveOperationException);
            }
        }
        return AppComponentFactory.DEFAULT;
    }

    private void createOrUpdateClassLoaderLocked(List<String> object) {
        CharSequence charSequence;
        boolean bl;
        if (this.mPackageName.equals("android")) {
            if (this.mClassLoader != null) {
                return;
            }
            object = this.mBaseClassLoader;
            this.mDefaultClassLoader = object != null ? object : ClassLoader.getSystemClassLoader();
            this.mAppComponentFactory = this.createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
            this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
            return;
        }
        if (this.mActivityThread != null && !Objects.equals(this.mPackageName, ActivityThread.currentPackageName()) && this.mIncludeCode) {
            try {
                ActivityThread.getPackageManager().notifyPackageUse(this.mPackageName, 6);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        if (this.mRegisterPackage) {
            try {
                ActivityManager.getService().addPackageDependency(this.mPackageName);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Object object2 = new ArrayList<String>(10);
        Serializable serializable = new ArrayList<String>(10);
        boolean bl2 = this.mApplicationInfo.isSystemApp() && !this.mApplicationInfo.isUpdatedSystemApp();
        CharSequence charSequence2 = System.getProperty("java.library.path");
        boolean bl3 = ((String)charSequence2).contains("/vendor/lib");
        if (this.mApplicationInfo.getCodePath() != null && this.mApplicationInfo.isVendor() && bl3 ^ true) {
            bl2 = false;
        }
        LoadedApk.makePaths(this.mActivityThread, bl2, this.mApplicationInfo, object2, serializable);
        Object object3 = this.mDataDir;
        if (bl2) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object3);
            ((StringBuilder)charSequence).append(File.pathSeparator);
            ((StringBuilder)charSequence).append(Paths.get(this.getAppDir(), new String[0]).getParent().toString());
            object3 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object3);
            ((StringBuilder)charSequence).append(File.pathSeparator);
            ((StringBuilder)charSequence).append((String)charSequence2);
            object3 = ((StringBuilder)charSequence).toString();
        }
        charSequence = TextUtils.join((CharSequence)File.pathSeparator, serializable);
        if (!this.mIncludeCode) {
            if (this.mDefaultClassLoader == null) {
                object = this.allowThreadDiskReads();
                this.mDefaultClassLoader = ApplicationLoaders.getDefault().getClassLoader("", this.mApplicationInfo.targetSdkVersion, bl2, (String)charSequence, (String)object3, this.mBaseClassLoader, null);
                this.setThreadPolicy((StrictMode.ThreadPolicy)object);
                this.mAppComponentFactory = AppComponentFactory.DEFAULT;
            }
            if (this.mClassLoader == null) {
                this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
            }
            return;
        }
        object2 = object2.size() == 1 ? (String)object2.get(0) : TextUtils.join((CharSequence)File.pathSeparator, object2);
        if (this.mDefaultClassLoader == null) {
            StrictMode.ThreadPolicy threadPolicy = this.allowThreadDiskReads();
            List<ClassLoader> list = this.createSharedLibrariesLoaders(this.mApplicationInfo.sharedLibraryInfos, bl2, (String)charSequence, (String)object3);
            this.mDefaultClassLoader = ApplicationLoaders.getDefault().getClassLoaderWithSharedLibraries((String)object2, this.mApplicationInfo.targetSdkVersion, bl2, (String)charSequence, (String)object3, this.mBaseClassLoader, this.mApplicationInfo.classLoaderName, list);
            this.mAppComponentFactory = this.createAppFactory(this.mApplicationInfo, this.mDefaultClassLoader);
            this.setThreadPolicy(threadPolicy);
            bl = true;
        } else {
            bl = false;
        }
        if (!serializable.isEmpty() && SystemProperties.getBoolean(PROPERTY_NAME_APPEND_NATIVE, true)) {
            object3 = this.allowThreadDiskReads();
            try {
                ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, (Collection<String>)((Object)serializable));
            }
            finally {
                this.setThreadPolicy((StrictMode.ThreadPolicy)object3);
            }
        }
        object2 = new ArrayList<String>(4);
        object3 = VMRuntime.getRuntime().is64Bit() ? "64" : "";
        if (!((String)charSequence2).contains("/apex/com.android.runtime/lib")) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("/apex/com.android.runtime/lib");
            ((StringBuilder)serializable).append((String)object3);
            object2.add(((StringBuilder)serializable).toString());
        }
        if (!((String)charSequence2).contains("/vendor/lib")) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("/vendor/lib");
            ((StringBuilder)serializable).append((String)object3);
            object2.add(((StringBuilder)serializable).toString());
        }
        if (!((String)charSequence2).contains("/odm/lib")) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("/odm/lib");
            ((StringBuilder)serializable).append((String)object3);
            object2.add(((StringBuilder)serializable).toString());
        }
        if (!((String)charSequence2).contains("/product/lib")) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("/product/lib");
            ((StringBuilder)charSequence2).append((String)object3);
            object2.add((String)((StringBuilder)charSequence2).toString());
        }
        if (!object2.isEmpty()) {
            object3 = this.allowThreadDiskReads();
            try {
                ApplicationLoaders.getDefault().addNative(this.mDefaultClassLoader, (Collection<String>)object2);
            }
            finally {
                this.setThreadPolicy((StrictMode.ThreadPolicy)object3);
            }
        }
        boolean bl4 = bl;
        if (object != null) {
            bl4 = bl;
            if (object.size() > 0) {
                object = TextUtils.join((CharSequence)File.pathSeparator, (Iterable)object);
                ApplicationLoaders.getDefault().addPath(this.mDefaultClassLoader, (String)object);
                bl4 = true;
            }
        }
        if (bl4 && !ActivityThread.isSystem() && this.mActivityThread != null) {
            this.setupJitProfileSupport();
        }
        if (this.mClassLoader == null) {
            this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
        }
    }

    private List<ClassLoader> createSharedLibrariesLoaders(List<SharedLibraryInfo> object, boolean bl, String string2, String string3) {
        if (object == null) {
            return null;
        }
        ArrayList<ClassLoader> arrayList = new ArrayList<ClassLoader>();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(this.createSharedLibraryLoader((SharedLibraryInfo)object.next(), bl, string2, string3));
        }
        return arrayList;
    }

    private static String[] getLibrariesFor(String object) {
        try {
            object = ActivityThread.getPackageManager().getApplicationInfo((String)object, 1024, UserHandle.myUserId());
            if (object == null) {
                return null;
            }
            return ((ApplicationInfo)object).sharedLibraryFiles;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IServiceConnection getServiceDispatcherCommon(ServiceConnection object, Context context, Handler object2, Executor arrayMap, int n) {
        ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> arrayMap2 = this.mServices;
        synchronized (arrayMap2) {
            ServiceDispatcher serviceDispatcher = null;
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap3 = this.mServices.get(context);
            if (arrayMap3 != null) {
                serviceDispatcher = arrayMap3.get(object);
            }
            if (serviceDispatcher == null) {
                object2 = arrayMap != null ? new ServiceDispatcher((ServiceConnection)object, context, (Executor)((Object)arrayMap), n) : new ServiceDispatcher((ServiceConnection)object, context, (Handler)object2, n);
                arrayMap = arrayMap3;
                if (arrayMap3 == null) {
                    arrayMap = new ArrayMap<Object, Object>();
                    this.mServices.put(context, arrayMap);
                }
                arrayMap.put(object, object2);
                return ((ServiceDispatcher)object2).getIServiceConnection();
            } else {
                serviceDispatcher.validate(context, (Handler)object2, (Executor)((Object)arrayMap));
                object2 = serviceDispatcher;
            }
            return ((ServiceDispatcher)object2).getIServiceConnection();
        }
    }

    private void initializeJavaContextClassLoader() {
        Object object = ActivityThread.getPackageManager();
        try {
            PackageInfo packageInfo = object.getPackageInfo(this.mPackageName, 268435456, UserHandle.myUserId());
            if (packageInfo != null) {
                object = packageInfo.sharedUserId;
                boolean bl = true;
                boolean bl2 = object != null;
                boolean bl3 = packageInfo.applicationInfo != null && !this.mPackageName.equals(packageInfo.applicationInfo.processName);
                boolean bl4 = bl;
                if (!bl2) {
                    bl4 = bl3 ? bl : false;
                }
                object = bl4 ? new WarningContextClassLoader() : this.mClassLoader;
                Thread.currentThread().setContextClassLoader((ClassLoader)object);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unable to get package info for ");
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        ((StringBuilder)object).append(this.mPackageName);
        ((StringBuilder)object).append("; is package not installed?");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static void makePaths(ActivityThread activityThread, ApplicationInfo applicationInfo, List<String> list) {
        LoadedApk.makePaths(activityThread, false, applicationInfo, list, null);
    }

    /*
     * WARNING - void declaration
     */
    public static void makePaths(ActivityThread object3, boolean bl, ApplicationInfo applicationInfo, List<String> list, List<String> list2) {
        String[] arrstring;
        void var4_16;
        void var3_15;
        String string2;
        String[] arrstring2;
        void var2_14;
        block26 : {
            String string3;
            String[] arrstring3;
            String string4;
            String[] arrstring4;
            String string5;
            String string6;
            String string7;
            block27 : {
                String string8 = var2_14.sourceDir;
                string2 = var2_14.nativeLibraryDir;
                var3_15.clear();
                var3_15.add(string8);
                if (var2_14.splitSourceDirs != null && !var2_14.requestsIsolatedSplitLoading()) {
                    Collections.addAll(var3_15, var2_14.splitSourceDirs);
                }
                if (var4_16 != null) {
                    var4_16.clear();
                }
                arrstring2 = arrstring = null;
                if (object3 == null) break block26;
                string6 = ((ActivityThread)object3).mInstrumentationPackageName;
                string5 = ((ActivityThread)object3).mInstrumentationAppDir;
                arrstring3 = ((ActivityThread)object3).mInstrumentationSplitAppDirs;
                string3 = ((ActivityThread)object3).mInstrumentationLibDir;
                string7 = ((ActivityThread)object3).mInstrumentedAppDir;
                arrstring4 = ((ActivityThread)object3).mInstrumentedSplitAppDirs;
                string4 = ((ActivityThread)object3).mInstrumentedLibDir;
                if (string8.equals(string5)) break block27;
                arrstring2 = arrstring;
                if (!string8.equals(string7)) break block26;
            }
            var3_15.clear();
            var3_15.add(string5);
            if (!var2_14.requestsIsolatedSplitLoading()) {
                if (arrstring3 != null) {
                    Collections.addAll(var3_15, arrstring3);
                }
                if (!string5.equals(string7)) {
                    var3_15.add(string7);
                    if (arrstring4 != null) {
                        Collections.addAll(var3_15, arrstring4);
                    }
                }
            }
            if (var4_16 != null) {
                var4_16.add(string3);
                if (!string3.equals(string4)) {
                    var4_16.add(string4);
                }
            }
            arrstring2 = arrstring;
            if (!string7.equals(string5)) {
                arrstring2 = LoadedApk.getLibrariesFor(string6);
            }
        }
        if (var4_16 != null) {
            void var1_13;
            if (var4_16.isEmpty()) {
                var4_16.add(string2);
            }
            if (var2_14.primaryCpuAbi != null) {
                if (var2_14.targetSdkVersion < 24) {
                    void var0_5;
                    arrstring = new StringBuilder();
                    arrstring.append("/system/fake-libs");
                    if (VMRuntime.is64BitAbi((String)var2_14.primaryCpuAbi)) {
                        String string9 = "64";
                    } else {
                        String string10 = "";
                    }
                    arrstring.append((String)var0_5);
                    var4_16.add(arrstring.toString());
                }
                for (String string11 : var3_15) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string11);
                    stringBuilder.append("!/lib/");
                    stringBuilder.append(var2_14.primaryCpuAbi);
                    var4_16.add(stringBuilder.toString());
                }
            }
            if (var1_13 != false) {
                var4_16.add(System.getProperty("java.library.path"));
            }
        }
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();
        LoadedApk.appendSharedLibrariesLibPathsIfNeeded(var2_14.sharedLibraryInfos, (ApplicationInfo)var2_14, linkedHashSet, (List<String>)var4_16);
        if (var2_14.sharedLibraryFiles != null) {
            arrstring = var2_14.sharedLibraryFiles;
            int n = arrstring.length;
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                String string12 = arrstring[i];
                int n3 = n2;
                if (!linkedHashSet.contains(string12)) {
                    n3 = n2;
                    if (!var3_15.contains(string12)) {
                        var3_15.add(n2, string12);
                        n3 = n2 + 1;
                        LoadedApk.appendApkLibPathIfNeeded(string12, (ApplicationInfo)var2_14, (List<String>)var4_16);
                    }
                }
                n2 = n3;
            }
        }
        if (arrstring2 != null) {
            for (String string13 : arrstring2) {
                if (var3_15.contains(string13)) continue;
                var3_15.add(0, string13);
                LoadedApk.appendApkLibPathIfNeeded(string13, (ApplicationInfo)var2_14, (List<String>)var4_16);
            }
        }
    }

    @UnsupportedAppUsage
    private void rewriteRValues(ClassLoader object, String string2, int n) {
        Throwable throwable;
        StringBuilder stringBuilder;
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(".R");
            object = ((ClassLoader)object).loadClass(stringBuilder.toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("No resource references to update in package ");
            stringBuilder2.append(string2);
            Log.i(TAG, stringBuilder2.toString());
            return;
        }
        try {
            object = ((Class)object).getMethod("onResourcesLoaded", Integer.TYPE);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return;
        }
        try {
            ((Method)object).invoke(null, n);
            return;
        }
        catch (InvocationTargetException invocationTargetException) {
            throwable = invocationTargetException.getCause();
        }
        catch (IllegalAccessException illegalAccessException) {
            // empty catch block
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to rewrite resource references for ");
        stringBuilder.append(string2);
        throw new RuntimeException(stringBuilder.toString(), throwable);
    }

    private void setApplicationInfo(ApplicationInfo object) {
        ApplicationInfo applicationInfo;
        int n = Process.myUid();
        this.mApplicationInfo = applicationInfo = LoadedApk.adjustNativeLibraryPaths((ApplicationInfo)object);
        this.mAppDir = applicationInfo.sourceDir;
        object = applicationInfo.uid == n ? applicationInfo.sourceDir : applicationInfo.publicSourceDir;
        this.mResDir = object;
        this.mOverlayDirs = applicationInfo.resourceDirs;
        this.mDataDir = applicationInfo.dataDir;
        this.mLibDir = applicationInfo.nativeLibraryDir;
        this.mDataDirFile = FileUtils.newFileOrNull(applicationInfo.dataDir);
        this.mDeviceProtectedDataDirFile = FileUtils.newFileOrNull(applicationInfo.deviceProtectedDataDir);
        this.mCredentialProtectedDataDirFile = FileUtils.newFileOrNull(applicationInfo.credentialProtectedDataDir);
        this.mSplitNames = applicationInfo.splitNames;
        this.mSplitAppDirs = applicationInfo.splitSourceDirs;
        object = applicationInfo.uid == n ? applicationInfo.splitSourceDirs : applicationInfo.splitPublicSourceDirs;
        this.mSplitResDirs = object;
        this.mSplitClassLoaderNames = applicationInfo.splitClassLoaderNames;
        if (applicationInfo.requestsIsolatedSplitLoading() && !ArrayUtils.isEmpty(this.mSplitNames)) {
            this.mSplitLoader = new SplitDependencyLoaderImpl(applicationInfo.splitDependencies);
        }
    }

    private void setThreadPolicy(StrictMode.ThreadPolicy threadPolicy) {
        if (this.mActivityThread != null && threadPolicy != null) {
            StrictMode.setThreadPolicy(threadPolicy);
        }
    }

    private void setupJitProfileSupport() {
        if (!SystemProperties.getBoolean("dalvik.vm.usejitprofiles", false)) {
            return;
        }
        BaseDexClassLoader.setReporter((BaseDexClassLoader.Reporter)DexLoadReporter.getInstance());
        if (this.mApplicationInfo.uid != Process.myUid()) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        if ((this.mApplicationInfo.flags & 4) != 0) {
            arrayList.add(this.mApplicationInfo.sourceDir);
        }
        if (this.mApplicationInfo.splitSourceDirs != null) {
            Collections.addAll(arrayList, this.mApplicationInfo.splitSourceDirs);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            String string2 = i == 0 ? null : this.mApplicationInfo.splitNames[i - 1];
            VMRuntime.registerAppInfo((String)ArtManager.getCurrentProfilePath(this.mPackageName, UserHandle.myUserId(), string2), (String[])new String[]{(String)arrayList.get(i)});
        }
        DexLoadReporter.getInstance().registerAppDataDir(this.mPackageName, this.mDataDir);
    }

    ClassLoader createSharedLibraryLoader(SharedLibraryInfo object, boolean bl, String string2, String string3) {
        List<String> list = ((SharedLibraryInfo)object).getAllCodePaths();
        List<ClassLoader> list2 = this.createSharedLibrariesLoaders(((SharedLibraryInfo)object).getDependencies(), bl, string2, string3);
        object = list.size() == 1 ? list.get(0) : TextUtils.join((CharSequence)File.pathSeparator, list);
        return ApplicationLoaders.getDefault().getSharedLibraryClassLoaderWithSharedLibraries((String)object, this.mApplicationInfo.targetSdkVersion, bl, string2, string3, null, null, list2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IIntentReceiver forgetReceiverDispatcher(Context object, BroadcastReceiver broadcastReceiver) {
        ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> arrayMap = this.mReceivers;
        synchronized (arrayMap) {
            ReceiverDispatcher receiverDispatcher;
            ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap2 = this.mReceivers.get(object);
            if (arrayMap2 != null && (receiverDispatcher = arrayMap2.get(broadcastReceiver)) != null) {
                arrayMap2.remove(broadcastReceiver);
                if (arrayMap2.size() == 0) {
                    this.mReceivers.remove(object);
                }
                if (broadcastReceiver.getDebugUnregister()) {
                    ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap3;
                    arrayMap2 = arrayMap3 = this.mUnregisteredReceivers.get(object);
                    if (arrayMap3 == null) {
                        arrayMap2 = new ArrayMap<BroadcastReceiver, ReceiverDispatcher>();
                        this.mUnregisteredReceivers.put((Context)object, arrayMap2);
                    }
                    object = new IllegalArgumentException("Originally unregistered here:");
                    ((Throwable)object).fillInStackTrace();
                    receiverDispatcher.setUnregisterLocation((RuntimeException)object);
                    arrayMap2.put(broadcastReceiver, receiverDispatcher);
                }
                receiverDispatcher.mForgotten = true;
                return receiverDispatcher.getIIntentReceiver();
            }
            arrayMap2 = this.mUnregisteredReceivers.get(object);
            if (arrayMap2 != null && (arrayMap2 = arrayMap2.get(broadcastReceiver)) != null) {
                object = ((ReceiverDispatcher)((Object)arrayMap2)).getUnregisterLocation();
                arrayMap2 = new ArrayMap<BroadcastReceiver, ReceiverDispatcher>();
                ((StringBuilder)((Object)arrayMap2)).append("Unregistering Receiver ");
                ((StringBuilder)((Object)arrayMap2)).append(broadcastReceiver);
                ((StringBuilder)((Object)arrayMap2)).append(" that was already unregistered");
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)((Object)arrayMap2)).toString(), (Throwable)object);
                throw illegalArgumentException;
            }
            if (object == null) {
                arrayMap2 = new ArrayMap<BroadcastReceiver, ReceiverDispatcher>();
                ((StringBuilder)((Object)arrayMap2)).append("Unbinding Receiver ");
                ((StringBuilder)((Object)arrayMap2)).append(broadcastReceiver);
                ((StringBuilder)((Object)arrayMap2)).append(" from Context that is no longer in use: ");
                ((StringBuilder)((Object)arrayMap2)).append(object);
                IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)((Object)arrayMap2)).toString());
                throw illegalStateException;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Receiver not registered: ");
            ((StringBuilder)object).append(broadcastReceiver);
            arrayMap2 = new ArrayMap<BroadcastReceiver, ReceiverDispatcher>(((StringBuilder)object).toString());
            throw arrayMap2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final IServiceConnection forgetServiceDispatcher(Context object, ServiceConnection serviceConnection) {
        ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> arrayMap = this.mServices;
        synchronized (arrayMap) {
            ServiceDispatcher serviceDispatcher;
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap2 = this.mServices.get(object);
            if (arrayMap2 != null && (serviceDispatcher = arrayMap2.get(serviceConnection)) != null) {
                ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap3;
                arrayMap2.remove(serviceConnection);
                serviceDispatcher.doForget();
                if (arrayMap2.size() == 0) {
                    this.mServices.remove(object);
                }
                if ((serviceDispatcher.getFlags() & 2) == 0) return serviceDispatcher.getIServiceConnection();
                arrayMap2 = arrayMap3 = this.mUnboundServices.get(object);
                if (arrayMap3 == null) {
                    arrayMap2 = new ArrayMap<ServiceConnection, ServiceDispatcher>();
                    this.mUnboundServices.put((Context)object, arrayMap2);
                }
                object = new IllegalArgumentException("Originally unbound here:");
                ((Throwable)object).fillInStackTrace();
                serviceDispatcher.setUnbindLocation((RuntimeException)object);
                arrayMap2.put(serviceConnection, serviceDispatcher);
                return serviceDispatcher.getIServiceConnection();
            }
            arrayMap2 = this.mUnboundServices.get(object);
            if (arrayMap2 != null && (arrayMap2 = arrayMap2.get(serviceConnection)) != null) {
                object = ((ServiceDispatcher)((Object)arrayMap2)).getUnbindLocation();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unbinding Service ");
                stringBuilder.append(serviceConnection);
                stringBuilder.append(" that was already unbound");
                arrayMap2 = new ArrayMap<ServiceConnection, ServiceDispatcher>(stringBuilder.toString(), (Throwable)object);
                throw arrayMap2;
            }
            if (object == null) {
                arrayMap2 = new ArrayMap<ServiceConnection, ServiceDispatcher>();
                ((StringBuilder)((Object)arrayMap2)).append("Unbinding Service ");
                ((StringBuilder)((Object)arrayMap2)).append(serviceConnection);
                ((StringBuilder)((Object)arrayMap2)).append(" from Context that is no longer in use: ");
                ((StringBuilder)((Object)arrayMap2)).append(object);
                IllegalStateException illegalStateException = new IllegalStateException(((StringBuilder)((Object)arrayMap2)).toString());
                throw illegalStateException;
            }
            arrayMap2 = new ArrayMap<ServiceConnection, ServiceDispatcher>();
            ((StringBuilder)((Object)arrayMap2)).append("Service not registered: ");
            ((StringBuilder)((Object)arrayMap2)).append(serviceConnection);
            object = new IllegalArgumentException(((StringBuilder)((Object)arrayMap2)).toString());
            throw object;
        }
    }

    @UnsupportedAppUsage
    public String getAppDir() {
        return this.mAppDir;
    }

    public AppComponentFactory getAppFactory() {
        return this.mAppComponentFactory;
    }

    Application getApplication() {
        return this.mApplication;
    }

    @UnsupportedAppUsage
    public ApplicationInfo getApplicationInfo() {
        return this.mApplicationInfo;
    }

    @UnsupportedAppUsage
    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ClassLoader getClassLoader() {
        synchronized (this) {
            if (this.mClassLoader != null) return this.mClassLoader;
            this.createOrUpdateClassLoaderLocked(null);
            return this.mClassLoader;
        }
    }

    @UnsupportedAppUsage
    public CompatibilityInfo getCompatibilityInfo() {
        return this.mDisplayAdjustments.getCompatibilityInfo();
    }

    public File getCredentialProtectedDataDirFile() {
        return this.mCredentialProtectedDataDirFile;
    }

    public String getDataDir() {
        return this.mDataDir;
    }

    @UnsupportedAppUsage
    public File getDataDirFile() {
        return this.mDataDirFile;
    }

    public File getDeviceProtectedDataDirFile() {
        return this.mDeviceProtectedDataDirFile;
    }

    public String getLibDir() {
        return this.mLibDir;
    }

    @UnsupportedAppUsage
    public String[] getOverlayDirs() {
        return this.mOverlayDirs;
    }

    @UnsupportedAppUsage
    public String getPackageName() {
        return this.mPackageName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public IIntentReceiver getReceiverDispatcher(BroadcastReceiver object, Context context, Handler object2, Instrumentation arrayMap, boolean bl) {
        ArrayMap<Context, ArrayMap<BroadcastReceiver, ReceiverDispatcher>> arrayMap2 = this.mReceivers;
        synchronized (arrayMap2) {
            Throwable throwable2;
            block9 : {
                ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap3;
                Object object3;
                block8 : {
                    ReceiverDispatcher receiverDispatcher = null;
                    arrayMap3 = null;
                    object3 = receiverDispatcher;
                    if (bl) {
                        try {
                            ArrayMap<BroadcastReceiver, ReceiverDispatcher> arrayMap4 = this.mReceivers.get(context);
                            object3 = receiverDispatcher;
                            arrayMap3 = arrayMap4;
                            if (arrayMap4 == null) break block8;
                            object3 = arrayMap4.get(object);
                            arrayMap3 = arrayMap4;
                        }
                        catch (Throwable throwable2) {
                            break block9;
                        }
                    }
                }
                if (object3 == null) {
                    object3 = object2 = (object3 = new ReceiverDispatcher((BroadcastReceiver)object, context, (Handler)object2, (Instrumentation)((Object)arrayMap), bl));
                    if (bl) {
                        arrayMap = arrayMap3;
                        if (arrayMap3 == null) {
                            arrayMap = new ArrayMap<Object, Object>();
                            this.mReceivers.put(context, arrayMap);
                        }
                        arrayMap.put(object, object2);
                        object3 = object2;
                    }
                } else {
                    ((ReceiverDispatcher)object3).validate(context, (Handler)object2);
                }
                ((ReceiverDispatcher)object3).mForgotten = false;
                return ((ReceiverDispatcher)object3).getIIntentReceiver();
            }
            throw throwable2;
        }
    }

    @UnsupportedAppUsage
    public String getResDir() {
        return this.mResDir;
    }

    @UnsupportedAppUsage
    public Resources getResources() {
        if (this.mResources == null) {
            try {
                String[] arrstring = this.getSplitPaths(null);
                this.mResources = ResourcesManager.getInstance().getResources(null, this.mResDir, arrstring, this.mOverlayDirs, this.mApplicationInfo.sharedLibraryFiles, 0, null, this.getCompatibilityInfo(), this.getClassLoader());
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                throw new AssertionError((Object)"null split not found");
            }
        }
        return this.mResources;
    }

    @UnsupportedAppUsage
    public final IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Context context, Handler handler, int n) {
        return this.getServiceDispatcherCommon(serviceConnection, context, handler, null, n);
    }

    public final IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Context context, Executor executor, int n) {
        return this.getServiceDispatcherCommon(serviceConnection, context, null, executor, n);
    }

    public String[] getSplitAppDirs() {
        return this.mSplitAppDirs;
    }

    ClassLoader getSplitClassLoader(String string2) throws PackageManager.NameNotFoundException {
        SplitDependencyLoaderImpl splitDependencyLoaderImpl = this.mSplitLoader;
        if (splitDependencyLoaderImpl == null) {
            return this.mClassLoader;
        }
        return splitDependencyLoaderImpl.getClassLoaderForSplit(string2);
    }

    String[] getSplitPaths(String string2) throws PackageManager.NameNotFoundException {
        SplitDependencyLoaderImpl splitDependencyLoaderImpl = this.mSplitLoader;
        if (splitDependencyLoaderImpl == null) {
            return this.mSplitResDirs;
        }
        return splitDependencyLoaderImpl.getSplitPathsForSplit(string2);
    }

    @UnsupportedAppUsage
    public String[] getSplitResDirs() {
        return this.mSplitResDirs;
    }

    public int getTargetSdkVersion() {
        return this.mApplicationInfo.targetSdkVersion;
    }

    void installSystemApplicationInfo(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        this.mApplicationInfo = applicationInfo;
        this.mDefaultClassLoader = classLoader;
        this.mAppComponentFactory = this.createAppFactory(applicationInfo, this.mDefaultClassLoader);
        this.mClassLoader = this.mAppComponentFactory.instantiateClassLoader(this.mDefaultClassLoader, new ApplicationInfo(this.mApplicationInfo));
    }

    public boolean isSecurityViolation() {
        return this.mSecurityViolation;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public IServiceConnection lookupServiceDispatcher(ServiceConnection object, Context object2) {
        ArrayMap<Context, ArrayMap<ServiceConnection, ServiceDispatcher>> arrayMap = this.mServices;
        synchronized (arrayMap) {
            Object var4_4 = null;
            ArrayMap<ServiceConnection, ServiceDispatcher> arrayMap2 = this.mServices.get(object2);
            object2 = var4_4;
            if (arrayMap2 != null) {
                object2 = arrayMap2.get(object);
            }
            if (object2 == null) return null;
            return ((ServiceDispatcher)object2).getIServiceConnection();
        }
    }

    @UnsupportedAppUsage
    public Application makeApplication(boolean bl, Instrumentation object) {
        Exception exception2;
        Object object2;
        block19 : {
            Object object3;
            block20 : {
                ClassLoader classLoader;
                Application application;
                block18 : {
                    block22 : {
                        block21 : {
                            object3 = this.mApplication;
                            if (object3 != null) {
                                return object3;
                            }
                            Trace.traceBegin(64L, "makeApplication");
                            application = null;
                            object3 = this.mApplicationInfo.className;
                            if (bl) break block21;
                            object2 = object3;
                            if (object3 != null) break block22;
                        }
                        object2 = "android.app.Application";
                    }
                    object3 = application;
                    classLoader = this.getClassLoader();
                    object3 = application;
                    if (this.mPackageName.equals("android")) break block18;
                    object3 = application;
                    Trace.traceBegin(64L, "initializeJavaContextClassLoader");
                    object3 = application;
                    this.initializeJavaContextClassLoader();
                    object3 = application;
                    Trace.traceEnd(64L);
                }
                object3 = application;
                ContextImpl contextImpl = ContextImpl.createAppContext(this.mActivityThread, this);
                object3 = application;
                application = this.mActivityThread.mInstrumentation.newApplication(classLoader, (String)object2, contextImpl);
                object3 = application;
                try {
                    contextImpl.setOuterContext(application);
                    object3 = application;
                }
                catch (Exception exception2) {
                    if (!this.mActivityThread.mInstrumentation.onException(object3, exception2)) break block19;
                }
                this.mActivityThread.mAllApplications.add((Application)object3);
                this.mApplication = object3;
                if (object != null) {
                    try {
                        ((Instrumentation)object).callApplicationOnCreate((Application)object3);
                    }
                    catch (Exception exception3) {
                        if (((Instrumentation)object).onException(object3, exception3)) break block20;
                        Trace.traceEnd(64L);
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unable to create application ");
                        ((StringBuilder)object).append(object3.getClass().getName());
                        ((StringBuilder)object).append(": ");
                        ((StringBuilder)object).append(exception3.toString());
                        throw new RuntimeException(((StringBuilder)object).toString(), exception3);
                    }
                }
            }
            object = this.getAssets().getAssignedPackageIdentifiers();
            int n = ((SparseArray)object).size();
            for (int i = 0; i < n; ++i) {
                int n2 = ((SparseArray)object).keyAt(i);
                if (n2 == 1 || n2 == 127) continue;
                this.rewriteRValues(this.getClassLoader(), (String)((SparseArray)object).valueAt(i), n2);
            }
            Trace.traceEnd(64L);
            return object3;
        }
        Trace.traceEnd(64L);
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to instantiate application ");
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(exception2.toString());
        throw new RuntimeException(((StringBuilder)object).toString(), exception2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeContextRegistrations(Context context, String string2, String string3) {
        int n;
        AndroidRuntimeException androidRuntimeException;
        Object object;
        ArrayMap<Object, Object> arrayMap;
        StringBuilder stringBuilder;
        boolean bl = StrictMode.vmRegistrationLeaksEnabled();
        ArrayMap<Context, ArrayMap<Object, Object>> arrayMap2 = this.mReceivers;
        synchronized (arrayMap2) {
            arrayMap = this.mReceivers.remove(context);
            int n2 = 0;
            if (arrayMap != null) {
                for (n = 0; n < arrayMap.size(); ++n) {
                    object = arrayMap.valueAt(n);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string3);
                    stringBuilder.append(" ");
                    stringBuilder.append(string2);
                    stringBuilder.append(" has leaked IntentReceiver ");
                    stringBuilder.append(((ReceiverDispatcher)object).getIntentReceiver());
                    stringBuilder.append(" that was originally registered here. Are you missing a call to unregisterReceiver()?");
                    androidRuntimeException = new IntentReceiverLeaked(stringBuilder.toString());
                    androidRuntimeException.setStackTrace(((ReceiverDispatcher)object).getLocation().getStackTrace());
                    Slog.e("ActivityThread", androidRuntimeException.getMessage(), androidRuntimeException);
                    if (bl) {
                        StrictMode.onIntentReceiverLeaked(androidRuntimeException);
                    }
                    try {
                        ActivityManager.getService().unregisterReceiver(((ReceiverDispatcher)object).getIIntentReceiver());
                        continue;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnregisteredReceivers.remove(context);
        }
        arrayMap2 = this.mServices;
        synchronized (arrayMap2) {
            arrayMap = this.mServices.remove(context);
            if (arrayMap != null) {
                for (n = n2; n < arrayMap.size(); ++n) {
                    object = (ServiceDispatcher)arrayMap.valueAt(n);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append(string3);
                    stringBuilder.append(" ");
                    stringBuilder.append(string2);
                    stringBuilder.append(" has leaked ServiceConnection ");
                    stringBuilder.append(((ServiceDispatcher)object).getServiceConnection());
                    stringBuilder.append(" that was originally bound here");
                    androidRuntimeException = new ServiceConnectionLeaked(stringBuilder.toString());
                    androidRuntimeException.setStackTrace(((ServiceDispatcher)object).getLocation().getStackTrace());
                    Slog.e("ActivityThread", androidRuntimeException.getMessage(), androidRuntimeException);
                    if (bl) {
                        StrictMode.onServiceConnectionLeaked(androidRuntimeException);
                    }
                    try {
                        ActivityManager.getService().unbindService(((ServiceDispatcher)object).getIServiceConnection());
                        ((ServiceDispatcher)object).doForget();
                        continue;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
            this.mUnboundServices.remove(context);
            return;
        }
    }

    public void setCompatibilityInfo(CompatibilityInfo compatibilityInfo) {
        this.mDisplayAdjustments.setCompatibilityInfo(compatibilityInfo);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void updateApplicationInfo(ApplicationInfo applicationInfo, List<String> arrstring) {
        this.setApplicationInfo(applicationInfo);
        Object object = new ArrayList<String>();
        LoadedApk.makePaths(this.mActivityThread, applicationInfo, object);
        ArrayList<String> arrayList = new ArrayList<String>(object.size());
        if (arrstring != null) {
            Iterator iterator = object.iterator();
            while (iterator.hasNext()) {
                String string2;
                boolean bl;
                block10 : {
                    String string3;
                    string2 = (String)iterator.next();
                    object = string2.substring(string2.lastIndexOf(File.separator));
                    boolean bl2 = false;
                    Iterator iterator2 = arrstring.iterator();
                    do {
                        bl = bl2;
                        if (!iterator2.hasNext()) break block10;
                    } while (!((String)object).equals((string3 = (String)iterator2.next()).substring(string3.lastIndexOf(File.separator))));
                    bl = true;
                }
                if (bl) continue;
                arrayList.add(string2);
            }
        } else {
            arrayList.addAll((Collection<String>)object);
        }
        synchronized (this) {
            this.createOrUpdateClassLoaderLocked(arrayList);
            arrstring = this.mResources;
            if (arrstring != null) {
                try {
                    arrstring = this.getSplitPaths(null);
                    this.mResources = ResourcesManager.getInstance().getResources(null, this.mResDir, arrstring, this.mOverlayDirs, this.mApplicationInfo.sharedLibraryFiles, 0, null, this.getCompatibilityInfo(), this.getClassLoader());
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    AssertionError assertionError = new AssertionError((Object)"null split not found");
                    throw assertionError;
                }
            }
        }
        this.mAppComponentFactory = this.createAppFactory(applicationInfo, this.mDefaultClassLoader);
    }

    static final class ReceiverDispatcher {
        final Handler mActivityThread;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        final Context mContext;
        boolean mForgotten;
        final IIntentReceiver.Stub mIIntentReceiver;
        final Instrumentation mInstrumentation;
        final IntentReceiverLeaked mLocation;
        @UnsupportedAppUsage
        final BroadcastReceiver mReceiver;
        final boolean mRegistered;
        RuntimeException mUnregisterLocation;

        ReceiverDispatcher(BroadcastReceiver broadcastReceiver, Context context, Handler handler, Instrumentation instrumentation, boolean bl) {
            if (handler != null) {
                this.mIIntentReceiver = new InnerReceiver(this, bl ^ true);
                this.mReceiver = broadcastReceiver;
                this.mContext = context;
                this.mActivityThread = handler;
                this.mInstrumentation = instrumentation;
                this.mRegistered = bl;
                this.mLocation = new IntentReceiverLeaked(null);
                this.mLocation.fillInStackTrace();
                return;
            }
            throw new NullPointerException("Handler must not be null");
        }

        @UnsupportedAppUsage
        IIntentReceiver getIIntentReceiver() {
            return this.mIIntentReceiver;
        }

        @UnsupportedAppUsage
        BroadcastReceiver getIntentReceiver() {
            return this.mReceiver;
        }

        IntentReceiverLeaked getLocation() {
            return this.mLocation;
        }

        RuntimeException getUnregisterLocation() {
            return this.mUnregisterLocation;
        }

        public void performReceive(Intent intent, int n, String object, Bundle bundle, boolean bl, boolean bl2, int n2) {
            object = new Args(intent, n, (String)object, bundle, bl, bl2, n2);
            if (intent == null) {
                Log.wtf(LoadedApk.TAG, "Null intent received");
            }
            if ((intent == null || !this.mActivityThread.post(((Args)object).getRunnable())) && this.mRegistered && bl) {
                ((BroadcastReceiver.PendingResult)object).sendFinished(ActivityManager.getService());
            }
        }

        void setUnregisterLocation(RuntimeException runtimeException) {
            this.mUnregisterLocation = runtimeException;
        }

        void validate(Context object, Handler object2) {
            if (this.mContext == object) {
                if (this.mActivityThread == object2) {
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Receiver ");
                ((StringBuilder)object).append(this.mReceiver);
                ((StringBuilder)object).append(" registered with differing handler (was ");
                ((StringBuilder)object).append(this.mActivityThread);
                ((StringBuilder)object).append(" now ");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(")");
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Receiver ");
            ((StringBuilder)object2).append(this.mReceiver);
            ((StringBuilder)object2).append(" registered with differing Context (was ");
            ((StringBuilder)object2).append(this.mContext);
            ((StringBuilder)object2).append(" now ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(")");
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }

        final class Args
        extends BroadcastReceiver.PendingResult {
            private Intent mCurIntent;
            private boolean mDispatched;
            private final boolean mOrdered;
            private boolean mRunCalled;

            public Args(Intent intent, int n, String string2, Bundle bundle, boolean bl, boolean bl2, int n2) {
                int n3 = ReceiverDispatcher.this.mRegistered ? 1 : 2;
                super(n, string2, bundle, n3, bl, bl2, ReceiverDispatcher.this.mIIntentReceiver.asBinder(), n2, intent.getFlags());
                this.mCurIntent = intent;
                this.mOrdered = bl;
            }

            public final Runnable getRunnable() {
                return new _$$Lambda$LoadedApk$ReceiverDispatcher$Args$_BumDX2UKsnxLVrE6UJsJZkotuA(this);
            }

            public /* synthetic */ void lambda$getRunnable$0$LoadedApk$ReceiverDispatcher$Args() {
                Object object;
                Object object2 = ReceiverDispatcher.this.mReceiver;
                boolean bl = this.mOrdered;
                IActivityManager iActivityManager = ActivityManager.getService();
                Intent intent = this.mCurIntent;
                if (intent == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Null intent being dispatched, mDispatched=");
                    stringBuilder.append(this.mDispatched);
                    object = this.mRunCalled ? ", run() has already been called" : "";
                    stringBuilder.append((String)object);
                    Log.wtf(LoadedApk.TAG, stringBuilder.toString());
                }
                this.mCurIntent = null;
                this.mDispatched = true;
                this.mRunCalled = true;
                if (object2 != null && intent != null && !ReceiverDispatcher.this.mForgotten) {
                    Exception exception2;
                    block7 : {
                        Trace.traceBegin(64L, "broadcastReceiveReg");
                        try {
                            object = ReceiverDispatcher.this.mReceiver.getClass().getClassLoader();
                            intent.setExtrasClassLoader((ClassLoader)object);
                            intent.prepareToEnterProcess();
                            this.setExtrasClassLoader((ClassLoader)object);
                            ((BroadcastReceiver)object2).setPendingResult(this);
                            ((BroadcastReceiver)object2).onReceive(ReceiverDispatcher.this.mContext, intent);
                        }
                        catch (Exception exception2) {
                            if (ReceiverDispatcher.this.mRegistered && bl) {
                                this.sendFinished(iActivityManager);
                            }
                            if (ReceiverDispatcher.this.mInstrumentation == null || !ReceiverDispatcher.this.mInstrumentation.onException(ReceiverDispatcher.this.mReceiver, exception2)) break block7;
                        }
                        if (((BroadcastReceiver)object2).getPendingResult() != null) {
                            this.finish();
                        }
                        Trace.traceEnd(64L);
                        return;
                    }
                    Trace.traceEnd(64L);
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Error receiving broadcast ");
                    ((StringBuilder)object2).append(intent);
                    ((StringBuilder)object2).append(" in ");
                    ((StringBuilder)object2).append(ReceiverDispatcher.this.mReceiver);
                    throw new RuntimeException(((StringBuilder)object2).toString(), exception2);
                }
                if (ReceiverDispatcher.this.mRegistered && bl) {
                    this.sendFinished(iActivityManager);
                }
            }
        }

        static final class InnerReceiver
        extends IIntentReceiver.Stub {
            final WeakReference<ReceiverDispatcher> mDispatcher;
            final ReceiverDispatcher mStrongRef;

            InnerReceiver(ReceiverDispatcher receiverDispatcher, boolean bl) {
                this.mDispatcher = new WeakReference<ReceiverDispatcher>(receiverDispatcher);
                if (!bl) {
                    receiverDispatcher = null;
                }
                this.mStrongRef = receiverDispatcher;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void performReceive(Intent var1_1, int var2_3, String var3_4, Bundle var4_5, boolean var5_6, boolean var6_7, int var7_8) {
                if (var1_1 == null) {
                    Log.wtf("LoadedApk", "Null intent received");
                    var8_9 = null;
                } else {
                    var8_9 = (ReceiverDispatcher)this.mDispatcher.get();
                }
                if (var8_9 != null) {
                    var8_9.performReceive(var1_1, var2_3, var3_4, var4_5, var5_6, var6_7, var7_8);
                    return;
                }
                var8_9 = ActivityManager.getService();
                if (var4_5 == null) ** GOTO lbl15
                try {
                    var4_5.setAllowFds(false);
lbl15: // 2 sources:
                    var8_9.finishReceiver(this, var2_3, var3_4, var4_5, false, var1_1.getFlags());
                    return;
                }
                catch (RemoteException var1_2) {
                    throw var1_2.rethrowFromSystemServer();
                }
            }
        }

    }

    static final class ServiceDispatcher {
        private final ArrayMap<ComponentName, ConnectionInfo> mActiveConnections = new ArrayMap();
        private final Executor mActivityExecutor;
        private final Handler mActivityThread;
        @UnsupportedAppUsage
        private final ServiceConnection mConnection;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        private final Context mContext;
        private final int mFlags;
        private boolean mForgotten;
        private final InnerConnection mIServiceConnection = new InnerConnection(this);
        private final ServiceConnectionLeaked mLocation;
        private RuntimeException mUnbindLocation;

        @UnsupportedAppUsage
        ServiceDispatcher(ServiceConnection serviceConnection, Context context, Handler handler, int n) {
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = handler;
            this.mActivityExecutor = null;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = n;
        }

        ServiceDispatcher(ServiceConnection serviceConnection, Context context, Executor executor, int n) {
            this.mConnection = serviceConnection;
            this.mContext = context;
            this.mActivityThread = null;
            this.mActivityExecutor = executor;
            this.mLocation = new ServiceConnectionLeaked(null);
            this.mLocation.fillInStackTrace();
            this.mFlags = n;
        }

        public void connected(ComponentName componentName, IBinder iBinder, boolean bl) {
            Object object = this.mActivityExecutor;
            if (object != null) {
                object.execute(new RunConnection(componentName, iBinder, 0, bl));
            } else {
                object = this.mActivityThread;
                if (object != null) {
                    ((Handler)object).post(new RunConnection(componentName, iBinder, 0, bl));
                } else {
                    this.doConnected(componentName, iBinder, bl);
                }
            }
        }

        public void death(ComponentName componentName, IBinder iBinder) {
            Object object = this.mActivityExecutor;
            if (object != null) {
                object.execute(new RunConnection(componentName, iBinder, 1, false));
            } else {
                object = this.mActivityThread;
                if (object != null) {
                    ((Handler)object).post(new RunConnection(componentName, iBinder, 1, false));
                } else {
                    this.doDeath(componentName, iBinder);
                }
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        public void doConnected(ComponentName componentName, IBinder iBinder, boolean bl) {
            // MONITORENTER : this
            if (this.mForgotten) {
                // MONITOREXIT : this
                return;
            }
            ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
            if (connectionInfo != null && connectionInfo.binder == iBinder) {
                // MONITOREXIT : this
                return;
            }
            if (iBinder != null) {
                ConnectionInfo connectionInfo2 = new ConnectionInfo();
                connectionInfo2.binder = iBinder;
                DeathMonitor deathMonitor = new DeathMonitor(componentName, iBinder);
                connectionInfo2.deathMonitor = deathMonitor;
                try {
                    iBinder.linkToDeath(connectionInfo2.deathMonitor, 0);
                    this.mActiveConnections.put(componentName, connectionInfo2);
                }
                catch (RemoteException remoteException) {
                    this.mActiveConnections.remove(componentName);
                    // MONITOREXIT : this
                    return;
                }
            } else {
                this.mActiveConnections.remove(componentName);
            }
            if (connectionInfo != null) {
                connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
            }
            // MONITOREXIT : this
            if (connectionInfo != null) {
                this.mConnection.onServiceDisconnected(componentName);
            }
            if (bl) {
                this.mConnection.onBindingDied(componentName);
            }
            if (iBinder != null) {
                this.mConnection.onServiceConnected(componentName, iBinder);
                return;
            }
            this.mConnection.onNullBinding(componentName);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void doDeath(ComponentName componentName, IBinder iBinder) {
            synchronized (this) {
                ConnectionInfo connectionInfo = this.mActiveConnections.get(componentName);
                if (connectionInfo != null && connectionInfo.binder == iBinder) {
                    this.mActiveConnections.remove(componentName);
                    connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                    // MONITOREXIT [2, 3] lbl8 : MonitorExitStatement: MONITOREXIT : this
                    this.mConnection.onServiceDisconnected(componentName);
                    return;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void doForget() {
            synchronized (this) {
                int n = 0;
                do {
                    if (n >= this.mActiveConnections.size()) {
                        this.mActiveConnections.clear();
                        this.mForgotten = true;
                        return;
                    }
                    ConnectionInfo connectionInfo = this.mActiveConnections.valueAt(n);
                    connectionInfo.binder.unlinkToDeath(connectionInfo.deathMonitor, 0);
                    ++n;
                } while (true);
            }
        }

        int getFlags() {
            return this.mFlags;
        }

        @UnsupportedAppUsage
        IServiceConnection getIServiceConnection() {
            return this.mIServiceConnection;
        }

        ServiceConnectionLeaked getLocation() {
            return this.mLocation;
        }

        ServiceConnection getServiceConnection() {
            return this.mConnection;
        }

        RuntimeException getUnbindLocation() {
            return this.mUnbindLocation;
        }

        void setUnbindLocation(RuntimeException runtimeException) {
            this.mUnbindLocation = runtimeException;
        }

        void validate(Context object, Handler object2, Executor executor) {
            if (this.mContext == object) {
                if (this.mActivityThread == object2) {
                    if (this.mActivityExecutor == executor) {
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ServiceConnection ");
                    ((StringBuilder)object).append(this.mConnection);
                    ((StringBuilder)object).append(" registered with differing executor (was ");
                    ((StringBuilder)object).append(this.mActivityExecutor);
                    ((StringBuilder)object).append(" now ");
                    ((StringBuilder)object).append(executor);
                    ((StringBuilder)object).append(")");
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("ServiceConnection ");
                ((StringBuilder)object).append(this.mConnection);
                ((StringBuilder)object).append(" registered with differing handler (was ");
                ((StringBuilder)object).append(this.mActivityThread);
                ((StringBuilder)object).append(" now ");
                ((StringBuilder)object).append(object2);
                ((StringBuilder)object).append(")");
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ServiceConnection ");
            ((StringBuilder)object2).append(this.mConnection);
            ((StringBuilder)object2).append(" registered with differing Context (was ");
            ((StringBuilder)object2).append(this.mContext);
            ((StringBuilder)object2).append(" now ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(")");
            throw new RuntimeException(((StringBuilder)object2).toString());
        }

        private static class ConnectionInfo {
            IBinder binder;
            IBinder.DeathRecipient deathMonitor;

            private ConnectionInfo() {
            }
        }

        private final class DeathMonitor
        implements IBinder.DeathRecipient {
            final ComponentName mName;
            final IBinder mService;

            DeathMonitor(ComponentName componentName, IBinder iBinder) {
                this.mName = componentName;
                this.mService = iBinder;
            }

            @Override
            public void binderDied() {
                ServiceDispatcher.this.death(this.mName, this.mService);
            }
        }

        private static class InnerConnection
        extends IServiceConnection.Stub {
            @UnsupportedAppUsage
            final WeakReference<ServiceDispatcher> mDispatcher;

            InnerConnection(ServiceDispatcher serviceDispatcher) {
                this.mDispatcher = new WeakReference<ServiceDispatcher>(serviceDispatcher);
            }

            @Override
            public void connected(ComponentName componentName, IBinder iBinder, boolean bl) throws RemoteException {
                ServiceDispatcher serviceDispatcher = (ServiceDispatcher)this.mDispatcher.get();
                if (serviceDispatcher != null) {
                    serviceDispatcher.connected(componentName, iBinder, bl);
                }
            }
        }

        private final class RunConnection
        implements Runnable {
            final int mCommand;
            final boolean mDead;
            final ComponentName mName;
            final IBinder mService;

            RunConnection(ComponentName componentName, IBinder iBinder, int n, boolean bl) {
                this.mName = componentName;
                this.mService = iBinder;
                this.mCommand = n;
                this.mDead = bl;
            }

            @Override
            public void run() {
                int n = this.mCommand;
                if (n == 0) {
                    ServiceDispatcher.this.doConnected(this.mName, this.mService, this.mDead);
                } else if (n == 1) {
                    ServiceDispatcher.this.doDeath(this.mName, this.mService);
                }
            }
        }

    }

    private class SplitDependencyLoaderImpl
    extends SplitDependencyLoader<PackageManager.NameNotFoundException> {
        private final ClassLoader[] mCachedClassLoaders;
        private final String[][] mCachedResourcePaths;

        SplitDependencyLoaderImpl(SparseArray<int[]> sparseArray) {
            super(sparseArray);
            this.mCachedResourcePaths = new String[LoadedApk.this.mSplitNames.length + 1][];
            this.mCachedClassLoaders = new ClassLoader[LoadedApk.this.mSplitNames.length + 1];
        }

        private int ensureSplitLoaded(String string2) throws PackageManager.NameNotFoundException {
            int n = 0;
            if (string2 != null) {
                n = Arrays.binarySearch(LoadedApk.this.mSplitNames, string2);
                if (n >= 0) {
                    ++n;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Split name '");
                    stringBuilder.append(string2);
                    stringBuilder.append("' is not installed");
                    throw new PackageManager.NameNotFoundException(stringBuilder.toString());
                }
            }
            this.loadDependenciesForSplit(n);
            return n;
        }

        @Override
        protected void constructSplit(int n, int[] arrn, int n2) throws PackageManager.NameNotFoundException {
            ArrayList<String> arrayList = new ArrayList<String>();
            int n32 = 0;
            if (n == 0) {
                LoadedApk.this.createOrUpdateClassLoaderLocked(null);
                this.mCachedClassLoaders[0] = LoadedApk.this.mClassLoader;
                for (int n32 : arrn) {
                    arrayList.add(LoadedApk.this.mSplitResDirs[n32 - 1]);
                }
                this.mCachedResourcePaths[0] = arrayList.toArray(new String[arrayList.size()]);
                return;
            }
            ClassLoader[] arrclassLoader = this.mCachedClassLoaders;
            ClassLoader classLoader = arrclassLoader[n2];
            arrclassLoader[n] = ApplicationLoaders.getDefault().getClassLoader(LoadedApk.this.mSplitAppDirs[n - 1], LoadedApk.this.getTargetSdkVersion(), false, null, null, classLoader, LoadedApk.this.mSplitClassLoaderNames[n - 1]);
            Collections.addAll(arrayList, this.mCachedResourcePaths[n2]);
            arrayList.add(LoadedApk.this.mSplitResDirs[n - 1]);
            int n4 = arrn.length;
            for (n2 = n32; n2 < n4; ++n2) {
                n32 = arrn[n2];
                arrayList.add(LoadedApk.this.mSplitResDirs[n32 - 1]);
            }
            this.mCachedResourcePaths[n] = arrayList.toArray(new String[arrayList.size()]);
        }

        ClassLoader getClassLoaderForSplit(String string2) throws PackageManager.NameNotFoundException {
            return this.mCachedClassLoaders[this.ensureSplitLoaded(string2)];
        }

        String[] getSplitPathsForSplit(String string2) throws PackageManager.NameNotFoundException {
            return this.mCachedResourcePaths[this.ensureSplitLoaded(string2)];
        }

        @Override
        protected boolean isSplitCached(int n) {
            boolean bl = this.mCachedClassLoaders[n] != null;
            return bl;
        }
    }

    private static class WarningContextClassLoader
    extends ClassLoader {
        private static boolean warned = false;

        private WarningContextClassLoader() {
        }

        private void warn(String string2) {
            if (warned) {
                return;
            }
            warned = true;
            Thread.currentThread().setContextClassLoader(this.getParent());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ClassLoader.");
            stringBuilder.append(string2);
            stringBuilder.append(": The class loader returned by Thread.getContextClassLoader() may fail for processes that host multiple applications. You should explicitly specify a context class loader. For example: Thread.setContextClassLoader(getClass().getClassLoader());");
            Slog.w("ActivityThread", stringBuilder.toString());
        }

        @Override
        public void clearAssertionStatus() {
            this.warn("clearAssertionStatus");
            this.getParent().clearAssertionStatus();
        }

        @Override
        public URL getResource(String string2) {
            this.warn("getResource");
            return this.getParent().getResource(string2);
        }

        @Override
        public InputStream getResourceAsStream(String string2) {
            this.warn("getResourceAsStream");
            return this.getParent().getResourceAsStream(string2);
        }

        @Override
        public Enumeration<URL> getResources(String string2) throws IOException {
            this.warn("getResources");
            return this.getParent().getResources(string2);
        }

        @Override
        public Class<?> loadClass(String string2) throws ClassNotFoundException {
            this.warn("loadClass");
            return this.getParent().loadClass(string2);
        }

        @Override
        public void setClassAssertionStatus(String string2, boolean bl) {
            this.warn("setClassAssertionStatus");
            this.getParent().setClassAssertionStatus(string2, bl);
        }

        @Override
        public void setDefaultAssertionStatus(boolean bl) {
            this.warn("setDefaultAssertionStatus");
            this.getParent().setDefaultAssertionStatus(bl);
        }

        @Override
        public void setPackageAssertionStatus(String string2, boolean bl) {
            this.warn("setPackageAssertionStatus");
            this.getParent().setPackageAssertionStatus(string2, bl);
        }
    }

}

