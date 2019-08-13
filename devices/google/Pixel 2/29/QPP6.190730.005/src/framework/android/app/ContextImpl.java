/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  dalvik.system.BlockGuard
 *  libcore.io.Memory
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.Application;
import android.app.ApplicationPackageManager;
import android.app.IActivityTaskManager;
import android.app.IApplicationThread;
import android.app.IInstrumentationWatcher;
import android.app.IServiceConnection;
import android.app.IUiAutomationConnection;
import android.app.Instrumentation;
import android.app.LoadedApk;
import android.app.ProfilerInfo;
import android.app.ReceiverRestrictedContext;
import android.app.ResourcesManager;
import android.app.SharedPreferencesImpl;
import android.app.SystemServiceRegistry;
import android.app.WallpaperManager;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.IContentProvider;
import android.content.IIntentReceiver;
import android.content.IIntentSender;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.CompatResources;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.os.FileUtils;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.AndroidRuntimeException;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Slog;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.autofill.AutofillManager;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import dalvik.system.BlockGuard;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Executor;
import libcore.io.Memory;

class ContextImpl
extends Context {
    private static final boolean DEBUG = false;
    static final int STATE_INITIALIZING = 1;
    static final int STATE_NOT_FOUND = 3;
    static final int STATE_READY = 2;
    static final int STATE_UNINITIALIZED = 0;
    private static final String TAG = "ContextImpl";
    private static final String XATTR_INODE_CACHE = "user.inode_cache";
    private static final String XATTR_INODE_CODE_CACHE = "user.inode_code_cache";
    @UnsupportedAppUsage
    @GuardedBy(value={"ContextImpl.class"})
    private static ArrayMap<String, ArrayMap<File, SharedPreferencesImpl>> sSharedPrefsCache;
    private final IBinder mActivityToken;
    private AutofillManager.AutofillClient mAutofillClient = null;
    private AutofillOptions mAutofillOptions;
    @UnsupportedAppUsage
    private final String mBasePackageName;
    @GuardedBy(value={"mSync"})
    private File mCacheDir;
    @UnsupportedAppUsage
    private ClassLoader mClassLoader;
    @GuardedBy(value={"mSync"})
    private File mCodeCacheDir;
    private ContentCaptureOptions mContentCaptureOptions = null;
    @UnsupportedAppUsage
    private final ApplicationContentResolver mContentResolver;
    @GuardedBy(value={"mSync"})
    private File mDatabasesDir;
    private Display mDisplay;
    @GuardedBy(value={"mSync"})
    private File mFilesDir;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mFlags;
    @UnsupportedAppUsage
    final ActivityThread mMainThread;
    @GuardedBy(value={"mSync"})
    private File mNoBackupFilesDir;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String mOpPackageName;
    @UnsupportedAppUsage
    private Context mOuterContext = this;
    @UnsupportedAppUsage
    final LoadedApk mPackageInfo;
    @UnsupportedAppUsage
    private PackageManager mPackageManager;
    @UnsupportedAppUsage
    @GuardedBy(value={"mSync"})
    private File mPreferencesDir;
    private Context mReceiverRestrictedContext = null;
    @UnsupportedAppUsage
    private Resources mResources;
    private final ResourcesManager mResourcesManager;
    @UnsupportedAppUsage
    final Object[] mServiceCache = SystemServiceRegistry.createServiceCache();
    final int[] mServiceInitializationStateArray = new int[this.mServiceCache.length];
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    @GuardedBy(value={"ContextImpl.class"})
    private ArrayMap<String, File> mSharedPrefsPaths;
    private String mSplitName = null;
    private final Object mSync = new Object();
    @UnsupportedAppUsage
    private Resources.Theme mTheme = null;
    @UnsupportedAppUsage
    private int mThemeResource = 0;
    private final UserHandle mUser;

    private ContextImpl(ContextImpl object, ActivityThread activityThread, LoadedApk object2, String string2, IBinder object3, UserHandle userHandle, int n, ClassLoader classLoader, String string3) {
        int n2 = n;
        if ((n & 24) == 0) {
            File file = ((LoadedApk)object2).getDataDirFile();
            if (Objects.equals(file, ((LoadedApk)object2).getCredentialProtectedDataDirFile())) {
                n2 = n | 16;
            } else {
                n2 = n;
                if (Objects.equals(file, ((LoadedApk)object2).getDeviceProtectedDataDirFile())) {
                    n2 = n | 8;
                }
            }
        }
        this.mMainThread = activityThread;
        this.mActivityToken = object3;
        this.mFlags = n2;
        object3 = userHandle;
        if (userHandle == null) {
            object3 = Process.myUserHandle();
        }
        this.mUser = object3;
        this.mPackageInfo = object2;
        this.mSplitName = string2;
        this.mClassLoader = classLoader;
        this.mResourcesManager = ResourcesManager.getInstance();
        if (object != null) {
            this.mBasePackageName = ((ContextImpl)object).mBasePackageName;
            object2 = ((ContextImpl)object).mOpPackageName;
            this.setResources(((ContextImpl)object).mResources);
            this.mDisplay = ((ContextImpl)object).mDisplay;
            object = object2;
        } else {
            this.mBasePackageName = ((LoadedApk)object2).mPackageName;
            object = ((LoadedApk)object2).getApplicationInfo();
            object = ((ApplicationInfo)object).uid == 1000 && ((ApplicationInfo)object).uid != Process.myUid() ? ActivityThread.currentPackageName() : this.mBasePackageName;
        }
        if (string3 != null) {
            object = string3;
        }
        this.mOpPackageName = object;
        this.mContentResolver = new ApplicationContentResolver(this, activityThread);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean bindServiceCommon(Intent intent, ServiceConnection object, int n, String charSequence, Handler handler, Executor executor, UserHandle userHandle) {
        void var1_4;
        block6 : {
            LoadedApk loadedApk;
            int n2;
            if (object == null) throw new IllegalArgumentException("connection is null");
            if (handler != null) {
                if (executor != null) throw new IllegalArgumentException("Handler and Executor both supplied");
            }
            if ((loadedApk = this.mPackageInfo) == null) throw new RuntimeException("Not supported in system context");
            object = executor != null ? loadedApk.getServiceDispatcher((ServiceConnection)object, this.getOuterContext(), executor, n) : loadedApk.getServiceDispatcher((ServiceConnection)object, this.getOuterContext(), handler, n);
            this.validateServiceIntent(intent);
            if (this.getActivityToken() != null || (n & 1) != 0 || this.mPackageInfo == null || (n2 = this.mPackageInfo.getApplicationInfo().targetSdkVersion) >= 14) break block6;
            n |= 32;
        }
        try {
            intent.prepareToLeaveProcess(this);
            n = ActivityManager.getService().bindIsolatedService(this.mMainThread.getApplicationThread(), this.getActivityToken(), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), (IServiceConnection)object, n, (String)charSequence, this.getOpPackageName(), userHandle.getIdentifier());
            if (n < 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Not allowed to bind to service ");
                ((StringBuilder)charSequence).append(intent);
                object = new SecurityException(((StringBuilder)charSequence).toString());
                throw object;
            }
            if (n == 0) return false;
            return true;
        }
        catch (RemoteException remoteException) {
            throw var1_4.rethrowFromSystemServer();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_4.rethrowFromSystemServer();
    }

    private void checkMode(int n) {
        if (this.getApplicationInfo().targetSdkVersion >= 24) {
            if ((n & 1) == 0) {
                if ((n & 2) != 0) {
                    throw new SecurityException("MODE_WORLD_WRITEABLE no longer supported");
                }
            } else {
                throw new SecurityException("MODE_WORLD_READABLE no longer supported");
            }
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    static ContextImpl createActivityContext(ActivityThread object, LoadedApk object2, ActivityInfo object3, IBinder iBinder, int n, Configuration configuration) {
        Throwable throwable2222;
        if (object2 == null) throw new IllegalArgumentException("packageInfo");
        String[] arrstring = ((LoadedApk)object2).getSplitResDirs();
        ClassLoader classLoader = ((LoadedApk)object2).getClassLoader();
        if (((LoadedApk)object2).getApplicationInfo().requestsIsolatedSplitLoading()) {
            Trace.traceBegin(8192L, "SplitDependencies");
            classLoader = ((LoadedApk)object2).getSplitClassLoader(((ActivityInfo)object3).splitName);
            arrstring = ((LoadedApk)object2).getSplitPaths(((ActivityInfo)object3).splitName);
            Trace.traceEnd(8192L);
        }
        object3 = new ContextImpl(null, (ActivityThread)object, (LoadedApk)object2, ((ActivityInfo)object3).splitName, iBinder, null, 0, classLoader, null);
        if (n == -1) {
            n = 0;
        }
        object = n == 0 ? ((LoadedApk)object2).getCompatibilityInfo() : CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO;
        ResourcesManager resourcesManager = ResourcesManager.getInstance();
        ((ContextImpl)object3).setResources(resourcesManager.createBaseActivityResources(iBinder, ((LoadedApk)object2).getResDir(), arrstring, ((LoadedApk)object2).getOverlayDirs(), object2.getApplicationInfo().sharedLibraryFiles, n, configuration, (CompatibilityInfo)object, classLoader));
        ((ContextImpl)object3).mDisplay = resourcesManager.getAdjustedDisplay(n, ((ContextImpl)object3).getResources());
        return object3;
        {
            catch (Throwable throwable2222) {
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {}
            {
                object2 = new RuntimeException(nameNotFoundException);
                throw object2;
            }
        }
        Trace.traceEnd(8192L);
        throw throwable2222;
    }

    @UnsupportedAppUsage
    static ContextImpl createAppContext(ActivityThread activityThread, LoadedApk loadedApk) {
        return ContextImpl.createAppContext(activityThread, loadedApk, null);
    }

    static ContextImpl createAppContext(ActivityThread object, LoadedApk loadedApk, String string2) {
        if (loadedApk != null) {
            object = new ContextImpl(null, (ActivityThread)object, loadedApk, null, null, null, 0, null, string2);
            ((ContextImpl)object).setResources(loadedApk.getResources());
            return object;
        }
        throw new IllegalArgumentException("packageInfo");
    }

    private static Resources createResources(IBinder iBinder, LoadedApk loadedApk, String object, int n, Configuration configuration, CompatibilityInfo compatibilityInfo) {
        try {
            String[] arrstring = loadedApk.getSplitPaths((String)object);
            object = loadedApk.getSplitClassLoader((String)object);
            return ResourcesManager.getInstance().getResources(iBinder, loadedApk.getResDir(), arrstring, loadedApk.getOverlayDirs(), loadedApk.getApplicationInfo().sharedLibraryFiles, n, configuration, compatibilityInfo, (ClassLoader)object);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            throw new RuntimeException(nameNotFoundException);
        }
    }

    @UnsupportedAppUsage
    static ContextImpl createSystemContext(ActivityThread object) {
        LoadedApk loadedApk = new LoadedApk((ActivityThread)object);
        object = new ContextImpl(null, (ActivityThread)object, loadedApk, null, null, null, 0, null, null);
        ((ContextImpl)object).setResources(loadedApk.getResources());
        ((ContextImpl)object).mResources.updateConfiguration(((ContextImpl)object).mResourcesManager.getConfiguration(), ((ContextImpl)object).mResourcesManager.getDisplayMetrics());
        return object;
    }

    static ContextImpl createSystemUiContext(ContextImpl contextImpl) {
        return ContextImpl.createSystemUiContext(contextImpl, 0);
    }

    static ContextImpl createSystemUiContext(ContextImpl contextImpl, int n) {
        LoadedApk loadedApk = contextImpl.mPackageInfo;
        contextImpl = new ContextImpl(null, contextImpl.mMainThread, loadedApk, null, null, null, 0, null, null);
        contextImpl.setResources(ContextImpl.createResources(null, loadedApk, null, n, null, loadedApk.getCompatibilityInfo()));
        contextImpl.updateDisplay(n);
        return contextImpl;
    }

    private void enforce(String string2, int n, boolean bl, int n2, String charSequence) {
        if (n != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            if (charSequence != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append((String)charSequence);
                stringBuilder2.append(": ");
                charSequence = stringBuilder2.toString();
            } else {
                charSequence = "";
            }
            stringBuilder.append((String)charSequence);
            if (bl) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Neither user ");
                ((StringBuilder)charSequence).append(n2);
                ((StringBuilder)charSequence).append(" nor current process has ");
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("uid ");
                ((StringBuilder)charSequence).append(n2);
                ((StringBuilder)charSequence).append(" does not have ");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            stringBuilder.append(string2);
            stringBuilder.append(".");
            throw new SecurityException(stringBuilder.toString());
        }
    }

    private void enforceForUri(int n, int n2, boolean bl, int n3, Uri uri, String charSequence) {
        if (n2 != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            if (charSequence != null) {
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append((String)charSequence);
                stringBuilder2.append(": ");
                charSequence = stringBuilder2.toString();
            } else {
                charSequence = "";
            }
            stringBuilder.append((String)charSequence);
            if (bl) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Neither user ");
                ((StringBuilder)charSequence).append(n3);
                ((StringBuilder)charSequence).append(" nor current process has ");
                charSequence = ((StringBuilder)charSequence).toString();
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("User ");
                ((StringBuilder)charSequence).append(n3);
                ((StringBuilder)charSequence).append(" does not have ");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
            stringBuilder.append(this.uriModeFlagToString(n));
            stringBuilder.append(" permission on ");
            stringBuilder.append(uri);
            stringBuilder.append(".");
            throw new SecurityException(stringBuilder.toString());
        }
    }

    private File[] ensureExternalDirsExistOrFilter(File[] arrfile) {
        StorageManager storageManager = this.getSystemService(StorageManager.class);
        File[] arrfile2 = new File[arrfile.length];
        for (int i = 0; i < arrfile.length; ++i) {
            File file = arrfile[i];
            Serializable serializable = file;
            if (!file.exists()) {
                serializable = file;
                if (!file.mkdirs()) {
                    serializable = file;
                    if (!file.exists()) {
                        try {
                            storageManager.mkdirs(file);
                            serializable = file;
                        }
                        catch (Exception exception) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Failed to ensure ");
                            ((StringBuilder)serializable).append(file);
                            ((StringBuilder)serializable).append(": ");
                            ((StringBuilder)serializable).append(exception);
                            Log.w(TAG, ((StringBuilder)serializable).toString());
                            serializable = null;
                        }
                    }
                }
            }
            arrfile2[i] = serializable;
        }
        return arrfile2;
    }

    private static File ensurePrivateCacheDirExists(File file, String string2) {
        return ContextImpl.ensurePrivateDirExists(file, 1529, UserHandle.getCacheAppGid(Process.myUid()), string2);
    }

    private static File ensurePrivateDirExists(File file) {
        return ContextImpl.ensurePrivateDirExists(file, 505, -1, null);
    }

    private static File ensurePrivateDirExists(File file, int n, int n2, String string2) {
        if (!file.exists()) {
            CharSequence charSequence;
            byte[] arrby;
            block7 : {
                charSequence = file.getAbsolutePath();
                Os.mkdir((String)charSequence, (int)n);
                Os.chmod((String)charSequence, (int)n);
                if (n2 == -1) break block7;
                try {
                    Os.chown((String)charSequence, (int)-1, (int)n2);
                }
                catch (ErrnoException errnoException) {
                    if (errnoException.errno == OsConstants.EEXIST) break block7;
                    arrby = new StringBuilder();
                    arrby.append("Failed to ensure ");
                    arrby.append(file);
                    arrby.append(": ");
                    arrby.append(errnoException.getMessage());
                    Log.w(TAG, arrby.toString());
                }
            }
            if (string2 != null) {
                try {
                    charSequence = Os.stat((String)file.getAbsolutePath());
                    arrby = new byte[8];
                    Memory.pokeLong((byte[])arrby, (int)0, (long)((StructStat)charSequence).st_ino, (ByteOrder)ByteOrder.nativeOrder());
                    Os.setxattr((String)file.getParentFile().getAbsolutePath(), (String)string2, (byte[])arrby, (int)0);
                }
                catch (ErrnoException errnoException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Failed to update ");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(": ");
                    ((StringBuilder)charSequence).append(errnoException.getMessage());
                    Log.w(TAG, ((StringBuilder)charSequence).toString());
                }
            }
        }
        return file;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private File getDatabasesDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mDatabasesDir != null) return ContextImpl.ensurePrivateDirExists(this.mDatabasesDir);
            this.mDatabasesDir = "android".equals(this.getPackageName()) ? (file = new File("/data/system")) : (file = new File(this.getDataDir(), "databases"));
            return ContextImpl.ensurePrivateDirExists(this.mDatabasesDir);
        }
    }

    @UnsupportedAppUsage
    static ContextImpl getImpl(Context context) {
        Context context2;
        while (context instanceof ContextWrapper && (context2 = ((ContextWrapper)context).getBaseContext()) != null) {
            context = context2;
        }
        return (ContextImpl)context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private File getPreferencesDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mPreferencesDir != null) return ContextImpl.ensurePrivateDirExists(this.mPreferencesDir);
            this.mPreferencesDir = file = new File(this.getDataDir(), "shared_prefs");
            return ContextImpl.ensurePrivateDirExists(this.mPreferencesDir);
        }
    }

    @GuardedBy(value={"ContextImpl.class"})
    private ArrayMap<File, SharedPreferencesImpl> getSharedPreferencesCacheLocked() {
        ArrayMap<File, SharedPreferencesImpl> arrayMap;
        if (sSharedPrefsCache == null) {
            sSharedPrefsCache = new ArrayMap();
        }
        String string2 = this.getPackageName();
        ArrayMap<File, SharedPreferencesImpl> arrayMap2 = arrayMap = sSharedPrefsCache.get(string2);
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            sSharedPrefsCache.put(string2, arrayMap2);
        }
        return arrayMap2;
    }

    private WallpaperManager getWallpaperManager() {
        return this.getSystemService(WallpaperManager.class);
    }

    private void initializeTheme() {
        if (this.mTheme == null) {
            this.mTheme = this.mResources.newTheme();
        }
        this.mTheme.applyStyle(this.mThemeResource, true);
    }

    private File makeFilename(File serializable, String string2) {
        if (string2.indexOf(File.separatorChar) < 0) {
            serializable = new File((File)serializable, string2);
            BlockGuard.getVmPolicy().onPathAccess(((File)serializable).getPath());
            return serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("File ");
        ((StringBuilder)serializable).append(string2);
        ((StringBuilder)serializable).append(" contains a path separator");
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    private static int moveFiles(File arrfile, File file, String object2) {
        arrfile = FileUtils.listFilesOrEmpty((File)arrfile, new FilenameFilter(){

            @Override
            public boolean accept(File file, String string2) {
                return string2.startsWith(String.this);
            }
        });
        int n = 0;
        for (File file2 : arrfile) {
            StringBuilder stringBuilder;
            Serializable serializable;
            block5 : {
                serializable = new File(file, file2.getName());
                stringBuilder = new StringBuilder();
                stringBuilder.append("Migrating ");
                stringBuilder.append(file2);
                stringBuilder.append(" to ");
                stringBuilder.append(serializable);
                Log.d(TAG, stringBuilder.toString());
                FileUtils.copyFileOrThrow(file2, (File)serializable);
                FileUtils.copyPermissions(file2, (File)serializable);
                if (!file2.delete()) break block5;
                int n2 = n;
                if (n != -1) {
                    n2 = n + 1;
                }
                n = n2;
                continue;
            }
            try {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to clean up ");
                stringBuilder.append(file2);
                serializable = new IOException(stringBuilder.toString());
                throw serializable;
            }
            catch (IOException iOException) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Failed to migrate ");
                ((StringBuilder)serializable).append(file2);
                ((StringBuilder)serializable).append(": ");
                ((StringBuilder)serializable).append(iOException);
                Log.w(TAG, ((StringBuilder)serializable).toString());
                n = -1;
            }
        }
        return n;
    }

    private Intent registerReceiverInternal(BroadcastReceiver object, int n, IntentFilter intentFilter, String string2, Handler handler, Context context, int n2) {
        block9 : {
            if (object != null) {
                if (this.mPackageInfo != null && context != null) {
                    if (handler == null) {
                        handler = this.mMainThread.getHandler();
                    }
                    object = this.mPackageInfo.getReceiverDispatcher((BroadcastReceiver)object, context, handler, this.mMainThread.getInstrumentation(), true);
                } else {
                    if (handler == null) {
                        handler = this.mMainThread.getHandler();
                    }
                    object = new LoadedApk.ReceiverDispatcher((BroadcastReceiver)object, context, handler, null, true).getIIntentReceiver();
                }
            } else {
                object = null;
            }
            try {
                object = ActivityManager.getService().registerReceiver(this.mMainThread.getApplicationThread(), this.mBasePackageName, (IIntentReceiver)object, intentFilter, string2, n, n2);
                if (object == null) break block9;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            ((Intent)object).setExtrasClassLoader(this.getClassLoader());
            ((Intent)object).prepareToEnterProcess();
        }
        return object;
    }

    private int resolveUserId(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, this.getUserId());
    }

    static void setFilePermissionsFromMode(String string2, int n, int n2) {
        int n3;
        n2 = n3 = n2 | 432;
        if ((n & 1) != 0) {
            n2 = n3 | 4;
        }
        n3 = n2;
        if ((n & 2) != 0) {
            n3 = n2 | 2;
        }
        FileUtils.setPermissions(string2, n3, -1, -1);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ComponentName startServiceCommon(Intent intent, boolean bl, UserHandle parcelable) {
        try {
            this.validateServiceIntent(intent);
            intent.prepareToLeaveProcess(this);
            parcelable = ActivityManager.getService().startService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), bl, this.getOpPackageName(), ((UserHandle)parcelable).getIdentifier());
            if (parcelable == null) return parcelable;
            bl = ((ComponentName)parcelable).getPackageName().equals("!");
            if (bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Not allowed to start service ");
                stringBuilder.append(intent);
                stringBuilder.append(" without permission ");
                stringBuilder.append(((ComponentName)parcelable).getClassName());
                SecurityException securityException = new SecurityException(stringBuilder.toString());
                throw securityException;
            }
            bl = ((ComponentName)parcelable).getPackageName().equals("!!");
            if (bl) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to start service ");
                stringBuilder.append(intent);
                stringBuilder.append(": ");
                stringBuilder.append(((ComponentName)parcelable).getClassName());
                SecurityException securityException = new SecurityException(stringBuilder.toString());
                throw securityException;
            }
            if (!((ComponentName)parcelable).getPackageName().equals("?")) {
                return parcelable;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not allowed to start service ");
            stringBuilder.append(intent);
            stringBuilder.append(": ");
            stringBuilder.append(((ComponentName)parcelable).getClassName());
            IllegalStateException illegalStateException = new IllegalStateException(stringBuilder.toString());
            throw illegalStateException;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    private boolean stopServiceCommon(Intent intent, UserHandle object) {
        block3 : {
            try {
                this.validateServiceIntent(intent);
                intent.prepareToLeaveProcess(this);
                int n = ActivityManager.getService().stopService(this.mMainThread.getApplicationThread(), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), ((UserHandle)object).getIdentifier());
                if (n < 0) break block3;
                boolean bl = n != 0;
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Not allowed to stop service ");
        stringBuilder.append(intent);
        object = new SecurityException(stringBuilder.toString());
        throw object;
    }

    private String uriModeFlagToString(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        if ((n & 1) != 0) {
            stringBuilder.append("read and ");
        }
        if ((n & 2) != 0) {
            stringBuilder.append("write and ");
        }
        if ((n & 64) != 0) {
            stringBuilder.append("persistable and ");
        }
        if ((n & 128) != 0) {
            stringBuilder.append("prefix and ");
        }
        if (stringBuilder.length() > 5) {
            stringBuilder.setLength(stringBuilder.length() - 5);
            return stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown permission mode flags: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void validateServiceIntent(Intent intent) {
        if (intent.getComponent() == null && intent.getPackage() == null) {
            if (this.getApplicationInfo().targetSdkVersion < 21) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Implicit intents with startService are not safe: ");
                stringBuilder.append(intent);
                stringBuilder.append(" ");
                stringBuilder.append(Debug.getCallers(2, 3));
                Log.w(TAG, stringBuilder.toString());
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Service Intent must be explicit: ");
                stringBuilder.append(intent);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        }
    }

    private void warnIfCallingFromSystemProcess() {
        if (Process.myUid() == 1000) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Calling a method in the system process without a qualified user: ");
            stringBuilder.append(Debug.getCallers(5));
            Slog.w(TAG, stringBuilder.toString());
        }
    }

    @Override
    public boolean bindIsolatedService(Intent intent, int n, String string2, Executor executor, ServiceConnection serviceConnection) {
        this.warnIfCallingFromSystemProcess();
        if (string2 != null) {
            return this.bindServiceCommon(intent, serviceConnection, n, string2, null, executor, this.getUser());
        }
        throw new NullPointerException("null instanceName");
    }

    @Override
    public boolean bindService(Intent intent, int n, Executor executor, ServiceConnection serviceConnection) {
        this.warnIfCallingFromSystemProcess();
        return this.bindServiceCommon(intent, serviceConnection, n, null, null, executor, this.getUser());
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int n) {
        this.warnIfCallingFromSystemProcess();
        return this.bindServiceCommon(intent, serviceConnection, n, null, this.mMainThread.getHandler(), null, this.getUser());
    }

    @Override
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, Handler handler, UserHandle userHandle) {
        if (handler != null) {
            return this.bindServiceCommon(intent, serviceConnection, n, null, handler, null, userHandle);
        }
        throw new IllegalArgumentException("handler must not be null.");
    }

    @Override
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, UserHandle userHandle) {
        return this.bindServiceCommon(intent, serviceConnection, n, null, this.mMainThread.getHandler(), null, userHandle);
    }

    @Override
    public boolean canLoadUnsafeResources() {
        boolean bl = this.getPackageName().equals(this.getOpPackageName());
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if ((this.mFlags & 2) == 0) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public int checkCallingOrSelfPermission(String string2) {
        if (string2 != null) {
            return this.checkPermission(string2, Binder.getCallingPid(), Binder.getCallingUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int n) {
        return this.checkUriPermission(uri, Binder.getCallingPid(), Binder.getCallingUid(), n);
    }

    @Override
    public int checkCallingPermission(String string2) {
        if (string2 != null) {
            int n = Binder.getCallingPid();
            if (n != Process.myPid()) {
                return this.checkPermission(string2, n, Binder.getCallingUid());
            }
            return -1;
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int n) {
        int n2 = Binder.getCallingPid();
        if (n2 != Process.myPid()) {
            return this.checkUriPermission(uri, n2, Binder.getCallingUid(), n);
        }
        return -1;
    }

    @Override
    public int checkPermission(String string2, int n, int n2) {
        if (string2 != null) {
            Object object = ActivityManager.getService();
            if (object == null) {
                n = UserHandle.getAppId(n2);
                if (n != 0 && n != 1000) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Missing ActivityManager; assuming ");
                    ((StringBuilder)object).append(n2);
                    ((StringBuilder)object).append(" does not hold ");
                    ((StringBuilder)object).append(string2);
                    Slog.w(TAG, ((StringBuilder)object).toString());
                    return -1;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Missing ActivityManager; assuming ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" holds ");
                ((StringBuilder)object).append(string2);
                Slog.w(TAG, ((StringBuilder)object).toString());
                return 0;
            }
            try {
                n = object.checkPermission(string2, n, n2);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Override
    public int checkPermission(String string2, int n, int n2, IBinder iBinder) {
        if (string2 != null) {
            try {
                n = ActivityManager.getService().checkPermissionWithToken(string2, n, n2, iBinder);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Override
    public int checkSelfPermission(String string2) {
        if (string2 != null) {
            return this.checkPermission(string2, Process.myPid(), Process.myUid());
        }
        throw new IllegalArgumentException("permission is null");
    }

    @Override
    public int checkUriPermission(Uri uri, int n, int n2, int n3) {
        try {
            n = ActivityManager.getService().checkUriPermission(ContentProvider.getUriWithoutUserId(uri), n, n2, n3, this.resolveUserId(uri), null);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int checkUriPermission(Uri uri, int n, int n2, int n3, IBinder iBinder) {
        try {
            n = ActivityManager.getService().checkUriPermission(ContentProvider.getUriWithoutUserId(uri), n, n2, n3, this.resolveUserId(uri), iBinder);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public int checkUriPermission(Uri uri, String string2, String string3, int n, int n2, int n3) {
        if ((n3 & 1) != 0 && (string2 == null || this.checkPermission(string2, n, n2) == 0)) {
            return 0;
        }
        if ((n3 & 2) != 0 && (string3 == null || this.checkPermission(string3, n, n2) == 0)) {
            return 0;
        }
        n = uri != null ? this.checkUriPermission(uri, n, n2, n3) : -1;
        return n;
    }

    @Deprecated
    @Override
    public void clearWallpaper() throws IOException {
        this.getWallpaperManager().clear();
    }

    @Override
    public Context createApplicationContext(ApplicationInfo applicationInfo, int n) throws PackageManager.NameNotFoundException {
        Object object = this.mMainThread.getPackageInfo(applicationInfo, this.mResources.getCompatibilityInfo(), 1073741824 | n);
        if (object != null) {
            ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, (LoadedApk)object, null, this.mActivityToken, new UserHandle(UserHandle.getUserId(applicationInfo.uid)), n, null, null);
            n = this.getDisplayId();
            contextImpl.setResources(ContextImpl.createResources(this.mActivityToken, (LoadedApk)object, null, n, null, this.getDisplayAdjustments(n).getCompatibilityInfo()));
            if (contextImpl.mResources != null) {
                return contextImpl;
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Application package ");
        ((StringBuilder)object).append(applicationInfo.packageName);
        ((StringBuilder)object).append(" not found");
        throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
    }

    @Override
    public Context createConfigurationContext(Configuration configuration) {
        if (configuration != null) {
            ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mSplitName, this.mActivityToken, this.mUser, this.mFlags, this.mClassLoader, null);
            int n = this.getDisplayId();
            contextImpl.setResources(ContextImpl.createResources(this.mActivityToken, this.mPackageInfo, this.mSplitName, n, configuration, this.getDisplayAdjustments(n).getCompatibilityInfo()));
            return contextImpl;
        }
        throw new IllegalArgumentException("overrideConfiguration must not be null");
    }

    @Override
    public Context createContextForSplit(String object) throws PackageManager.NameNotFoundException {
        if (!this.mPackageInfo.getApplicationInfo().requestsIsolatedSplitLoading()) {
            return this;
        }
        ClassLoader classLoader = this.mPackageInfo.getSplitClassLoader((String)object);
        String[] arrstring = this.mPackageInfo.getSplitPaths((String)object);
        object = new ContextImpl(this, this.mMainThread, this.mPackageInfo, (String)object, this.mActivityToken, this.mUser, this.mFlags, classLoader, null);
        int n = this.getDisplayId();
        ((ContextImpl)object).setResources(ResourcesManager.getInstance().getResources(this.mActivityToken, this.mPackageInfo.getResDir(), arrstring, this.mPackageInfo.getOverlayDirs(), this.mPackageInfo.getApplicationInfo().sharedLibraryFiles, n, null, this.mPackageInfo.getCompatibilityInfo(), classLoader));
        return object;
    }

    @Override
    public Context createCredentialProtectedStorageContext() {
        int n = this.mFlags;
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mSplitName, this.mActivityToken, this.mUser, n & -9 | 16, this.mClassLoader, null);
    }

    @Override
    public Context createDeviceProtectedStorageContext() {
        int n = this.mFlags;
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mSplitName, this.mActivityToken, this.mUser, n & -17 | 8, this.mClassLoader, null);
    }

    @Override
    public Context createDisplayContext(Display display) {
        if (display != null) {
            ContextImpl contextImpl = new ContextImpl(this, this.mMainThread, this.mPackageInfo, this.mSplitName, this.mActivityToken, this.mUser, this.mFlags, this.mClassLoader, null);
            int n = display.getDisplayId();
            contextImpl.setResources(ContextImpl.createResources(this.mActivityToken, this.mPackageInfo, this.mSplitName, n, null, this.getDisplayAdjustments(n).getCompatibilityInfo()));
            contextImpl.mDisplay = display;
            return contextImpl;
        }
        throw new IllegalArgumentException("display must not be null");
    }

    @Override
    public Context createPackageContext(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.createPackageContextAsUser(string2, n, this.mUser);
    }

    @Override
    public Context createPackageContextAsUser(String string2, int n, UserHandle object) throws PackageManager.NameNotFoundException {
        if (!string2.equals("system") && !string2.equals("android")) {
            LoadedApk loadedApk = this.mMainThread.getPackageInfo(string2, this.mResources.getCompatibilityInfo(), n | 1073741824, ((UserHandle)object).getIdentifier());
            if (loadedApk != null) {
                object = new ContextImpl(this, this.mMainThread, loadedApk, null, this.mActivityToken, (UserHandle)object, n, null, null);
                n = this.getDisplayId();
                ((ContextImpl)object).setResources(ContextImpl.createResources(this.mActivityToken, loadedApk, null, n, null, this.getDisplayAdjustments(n).getCompatibilityInfo()));
                if (((ContextImpl)object).mResources != null) {
                    return object;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Application package ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" not found");
            throw new PackageManager.NameNotFoundException(((StringBuilder)object).toString());
        }
        return new ContextImpl(this, this.mMainThread, this.mPackageInfo, null, this.mActivityToken, (UserHandle)object, n, null, null);
    }

    @Override
    public String[] databaseList() {
        return FileUtils.listOrEmpty(this.getDatabasesDir());
    }

    @Override
    public boolean deleteDatabase(String string2) {
        try {
            boolean bl = SQLiteDatabase.deleteDatabase(this.getDatabasePath(string2));
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(String string2) {
        return this.makeFilename(this.getFilesDir(), string2).delete();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean deleteSharedPreferences(String object) {
        synchronized (ContextImpl.class) {
            object = this.getSharedPreferencesPath((String)object);
            File file = SharedPreferencesImpl.makeBackupFile((File)object);
            this.getSharedPreferencesCacheLocked().remove(object);
            ((File)object).delete();
            file.delete();
            if (((File)object).exists()) return false;
            if (file.exists()) return false;
            return true;
        }
    }

    @Override
    public void enforceCallingOrSelfPermission(String string2, String string3) {
        this.enforce(string2, this.checkCallingOrSelfPermission(string2), true, Binder.getCallingUid(), string3);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int n, String string2) {
        this.enforceForUri(n, this.checkCallingOrSelfUriPermission(uri, n), true, Binder.getCallingUid(), uri, string2);
    }

    @Override
    public void enforceCallingPermission(String string2, String string3) {
        this.enforce(string2, this.checkCallingPermission(string2), false, Binder.getCallingUid(), string3);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int n, String string2) {
        this.enforceForUri(n, this.checkCallingUriPermission(uri, n), false, Binder.getCallingUid(), uri, string2);
    }

    @Override
    public void enforcePermission(String string2, int n, int n2, String string3) {
        this.enforce(string2, this.checkPermission(string2, n, n2), false, n2, string3);
    }

    @Override
    public void enforceUriPermission(Uri uri, int n, int n2, int n3, String string2) {
        this.enforceForUri(n3, this.checkUriPermission(uri, n, n2, n3), false, n2, uri, string2);
    }

    @Override
    public void enforceUriPermission(Uri uri, String string2, String string3, int n, int n2, int n3, String string4) {
        this.enforceForUri(n3, this.checkUriPermission(uri, string2, string3, n, n2, n3), false, n2, uri, string4);
    }

    @Override
    public String[] fileList() {
        return FileUtils.listOrEmpty(this.getFilesDir());
    }

    @UnsupportedAppUsage
    @Override
    public IBinder getActivityToken() {
        return this.mActivityToken;
    }

    @Override
    public Context getApplicationContext() {
        Object object = this.mPackageInfo;
        object = object != null ? ((LoadedApk)object).getApplication() : this.mMainThread.getApplication();
        return object;
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getApplicationInfo();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override
    public AssetManager getAssets() {
        return this.getResources().getAssets();
    }

    @Override
    public AutofillManager.AutofillClient getAutofillClient() {
        return this.mAutofillClient;
    }

    @Override
    public AutofillOptions getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override
    public String getBasePackageName() {
        String string2 = this.mBasePackageName;
        if (string2 == null) {
            string2 = this.getPackageName();
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getCacheDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mCacheDir != null) return ContextImpl.ensurePrivateCacheDirExists(this.mCacheDir, XATTR_INODE_CACHE);
            this.mCacheDir = file = new File(this.getDataDir(), "cache");
            return ContextImpl.ensurePrivateCacheDirExists(this.mCacheDir, XATTR_INODE_CACHE);
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        Object object = this.mClassLoader;
        if (object == null) {
            object = this.mPackageInfo;
            object = object != null ? ((LoadedApk)object).getClassLoader() : ClassLoader.getSystemClassLoader();
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getCodeCacheDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mCodeCacheDir != null) return ContextImpl.ensurePrivateCacheDirExists(this.mCodeCacheDir, XATTR_INODE_CODE_CACHE);
            this.mCodeCacheDir = file = new File(this.getDataDir(), "code_cache");
            return ContextImpl.ensurePrivateCacheDirExists(this.mCodeCacheDir, XATTR_INODE_CODE_CACHE);
        }
    }

    @Override
    public ContentCaptureOptions getContentCaptureOptions() {
        return this.mContentCaptureOptions;
    }

    @Override
    public ContentResolver getContentResolver() {
        return this.mContentResolver;
    }

    @Override
    public File getDataDir() {
        if (this.mPackageInfo != null) {
            Serializable serializable = this.isCredentialProtectedStorage() ? this.mPackageInfo.getCredentialProtectedDataDirFile() : (this.isDeviceProtectedStorage() ? this.mPackageInfo.getDeviceProtectedDataDirFile() : this.mPackageInfo.getDataDirFile());
            if (serializable != null) {
                if (!((File)serializable).exists() && Process.myUid() == 1000) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Data directory doesn't exist for package ");
                    stringBuilder.append(this.getPackageName());
                    Log.wtf(TAG, stringBuilder.toString(), new Throwable());
                }
                return serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("No data directory found for package ");
            ((StringBuilder)serializable).append(this.getPackageName());
            throw new RuntimeException(((StringBuilder)serializable).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No package details found for package ");
        stringBuilder.append(this.getPackageName());
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public File getDatabasePath(String object) {
        if (((String)object).charAt(0) == File.separatorChar) {
            File file = new File(((String)object).substring(0, ((String)object).lastIndexOf(File.separatorChar)));
            object = new File(file, ((String)object).substring(((String)object).lastIndexOf(File.separatorChar)));
            if (!file.isDirectory() && file.mkdir()) {
                FileUtils.setPermissions(file.getPath(), 505, -1, -1);
            }
        } else {
            object = this.makeFilename(this.getDatabasesDir(), (String)object);
        }
        return object;
    }

    @Override
    public File getDir(String object, int n) {
        this.checkMode(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("app_");
        stringBuilder.append((String)object);
        object = stringBuilder.toString();
        object = this.makeFilename(this.getDataDir(), (String)object);
        if (!((File)object).exists()) {
            ((File)object).mkdir();
            ContextImpl.setFilePermissionsFromMode(((File)object).getPath(), n, 505);
        }
        return object;
    }

    @Override
    public Display getDisplay() {
        Display display = this.mDisplay;
        if (display == null) {
            return this.mResourcesManager.getAdjustedDisplay(0, this.mResources);
        }
        return display;
    }

    @Override
    public DisplayAdjustments getDisplayAdjustments(int n) {
        return this.mResources.getDisplayAdjustments();
    }

    @Override
    public int getDisplayId() {
        Display display = this.mDisplay;
        int n = display != null ? display.getDisplayId() : 0;
        return n;
    }

    @Override
    public File getExternalCacheDir() {
        Object object = this.getExternalCacheDirs();
        object = object != null && ((File[])object).length > 0 ? object[0] : null;
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File[] getExternalCacheDirs() {
        Object object = this.mSync;
        synchronized (object) {
            return this.ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppCacheDirs(this.getPackageName()));
        }
    }

    @Override
    public File getExternalFilesDir(String object) {
        object = (object = this.getExternalFilesDirs((String)object)) != null && ((File[])object).length > 0 ? object[0] : null;
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File[] getExternalFilesDirs(String arrfile) {
        Object object = this.mSync;
        synchronized (object) {
            File[] arrfile2;
            File[] arrfile3 = arrfile2 = Environment.buildExternalStorageAppFilesDirs(this.getPackageName());
            if (arrfile == null) return this.ensureExternalDirsExistOrFilter(arrfile3);
            arrfile3 = Environment.buildPaths(arrfile2, new String[]{arrfile});
            return this.ensureExternalDirsExistOrFilter(arrfile3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File[] getExternalMediaDirs() {
        Object object = this.mSync;
        synchronized (object) {
            return this.ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppMediaDirs(this.getPackageName()));
        }
    }

    @Override
    public File getFileStreamPath(String string2) {
        return this.makeFilename(this.getFilesDir(), string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getFilesDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mFilesDir != null) return ContextImpl.ensurePrivateDirExists(this.mFilesDir);
            this.mFilesDir = file = new File(this.getDataDir(), "files");
            return ContextImpl.ensurePrivateDirExists(this.mFilesDir);
        }
    }

    @Override
    public IApplicationThread getIApplicationThread() {
        return this.mMainThread.getApplicationThread();
    }

    @Override
    public Executor getMainExecutor() {
        return this.mMainThread.getExecutor();
    }

    @Override
    public Looper getMainLooper() {
        return this.mMainThread.getLooper();
    }

    @Override
    public Handler getMainThreadHandler() {
        return this.mMainThread.getHandler();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File getNoBackupFilesDir() {
        Object object = this.mSync;
        synchronized (object) {
            File file;
            if (this.mNoBackupFilesDir != null) return ContextImpl.ensurePrivateDirExists(this.mNoBackupFilesDir);
            this.mNoBackupFilesDir = file = new File(this.getDataDir(), "no_backup");
            return ContextImpl.ensurePrivateDirExists(this.mNoBackupFilesDir);
        }
    }

    @Override
    public File getObbDir() {
        Object object = this.getObbDirs();
        object = object != null && ((File[])object).length > 0 ? object[0] : null;
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public File[] getObbDirs() {
        Object object = this.mSync;
        synchronized (object) {
            return this.ensureExternalDirsExistOrFilter(Environment.buildExternalStorageAppObbDirs(this.getPackageName()));
        }
    }

    @Override
    public String getOpPackageName() {
        String string2 = this.mOpPackageName;
        if (string2 == null) {
            string2 = this.getBasePackageName();
        }
        return string2;
    }

    @UnsupportedAppUsage
    final Context getOuterContext() {
        return this.mOuterContext;
    }

    @Override
    public String getPackageCodePath() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getAppDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override
    public PackageManager getPackageManager() {
        Object object = this.mPackageManager;
        if (object != null) {
            return object;
        }
        object = ActivityThread.getPackageManager();
        if (object != null) {
            this.mPackageManager = object = new ApplicationPackageManager(this, (IPackageManager)object);
            return object;
        }
        return null;
    }

    @Override
    public String getPackageName() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getPackageName();
        }
        return "android";
    }

    @Override
    public String getPackageResourcePath() {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            return loadedApk.getResDir();
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override
    public File getPreloadsFileCache() {
        return Environment.getDataPreloadsFileCacheDirectory(this.getPackageName());
    }

    @UnsupportedAppUsage
    final Context getReceiverRestrictedContext() {
        Context context = this.mReceiverRestrictedContext;
        if (context != null) {
            return context;
        }
        this.mReceiverRestrictedContext = context = new ReceiverRestrictedContext(this.getOuterContext());
        return context;
    }

    @Override
    public Resources getResources() {
        return this.mResources;
    }

    @Override
    public IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Handler handler, int n) {
        return this.mPackageInfo.getServiceDispatcher(serviceConnection, this.getOuterContext(), handler, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public SharedPreferences getSharedPreferences(File serializable, int n) {
        // MONITORENTER : android.app.ContextImpl.class
        ArrayMap<File, SharedPreferencesImpl> arrayMap = this.getSharedPreferencesCacheLocked();
        SharedPreferencesImpl sharedPreferencesImpl = arrayMap.get(serializable);
        if (sharedPreferencesImpl == null) {
            this.checkMode(n);
            if (this.getApplicationInfo().targetSdkVersion >= 26 && this.isCredentialProtectedStorage() && !this.getSystemService(UserManager.class).isUserUnlockingOrUnlocked(UserHandle.myUserId())) {
                serializable = new IllegalStateException("SharedPreferences in credential encrypted storage are not available until after user is unlocked");
                throw serializable;
            }
            sharedPreferencesImpl = new SharedPreferencesImpl((File)serializable, n);
            arrayMap.put((File)serializable, sharedPreferencesImpl);
            // MONITOREXIT : android.app.ContextImpl.class
            return sharedPreferencesImpl;
        }
        // MONITOREXIT : android.app.ContextImpl.class
        if ((n & 4) == 0) {
            if (this.getApplicationInfo().targetSdkVersion >= 11) return sharedPreferencesImpl;
        }
        sharedPreferencesImpl.startReloadIfChangedUnexpectedly();
        return sharedPreferencesImpl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public SharedPreferences getSharedPreferences(String object, int n) {
        String string2 = object;
        if (this.mPackageInfo.getApplicationInfo().targetSdkVersion < 19) {
            string2 = object;
            if (object == null) {
                string2 = "null";
            }
        }
        synchronized (ContextImpl.class) {
            if (this.mSharedPrefsPaths == null) {
                object = new ArrayMap();
                this.mSharedPrefsPaths = object;
            }
            File file = this.mSharedPrefsPaths.get(string2);
            object = file;
            if (file == null) {
                object = this.getSharedPreferencesPath(string2);
                this.mSharedPrefsPaths.put(string2, (File)object);
            }
            return this.getSharedPreferences((File)object, n);
        }
    }

    @Override
    public File getSharedPreferencesPath(String string2) {
        File file = this.getPreferencesDir();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(".xml");
        return this.makeFilename(file, stringBuilder.toString());
    }

    @Override
    public Object getSystemService(String string2) {
        return SystemServiceRegistry.getSystemService(this, string2);
    }

    @Override
    public String getSystemServiceName(Class<?> class_) {
        return SystemServiceRegistry.getSystemServiceName(class_);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Resources.Theme getTheme() {
        Object object = this.mSync;
        synchronized (object) {
            if (this.mTheme != null) {
                return this.mTheme;
            }
            this.mThemeResource = Resources.selectDefaultTheme(this.mThemeResource, this.getOuterContext().getApplicationInfo().targetSdkVersion);
            this.initializeTheme();
            return this.mTheme;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getThemeResId() {
        Object object = this.mSync;
        synchronized (object) {
            return this.mThemeResource;
        }
    }

    @Override
    public UserHandle getUser() {
        return this.mUser;
    }

    @Override
    public int getUserId() {
        return this.mUser.getIdentifier();
    }

    @Deprecated
    @Override
    public Drawable getWallpaper() {
        return this.getWallpaperManager().getDrawable();
    }

    @Deprecated
    @Override
    public int getWallpaperDesiredMinimumHeight() {
        return this.getWallpaperManager().getDesiredMinimumHeight();
    }

    @Deprecated
    @Override
    public int getWallpaperDesiredMinimumWidth() {
        return this.getWallpaperManager().getDesiredMinimumWidth();
    }

    @Override
    public void grantUriPermission(String string2, Uri uri, int n) {
        try {
            ActivityManager.getService().grantUriPermission(this.mMainThread.getApplicationThread(), string2, ContentProvider.getUriWithoutUserId(uri), n, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    void installSystemApplicationInfo(ApplicationInfo applicationInfo, ClassLoader classLoader) {
        this.mPackageInfo.installSystemApplicationInfo(applicationInfo, classLoader);
    }

    @Override
    public boolean isCredentialProtectedStorage() {
        boolean bl = (this.mFlags & 16) != 0;
        return bl;
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        boolean bl = (this.mFlags & 8) != 0;
        return bl;
    }

    @Override
    public boolean isRestricted() {
        boolean bl = (this.mFlags & 4) != 0;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean moveDatabaseFrom(Context object, String object2) {
        synchronized (ContextImpl.class) {
            object = ((Context)object).getDatabasePath((String)object2);
            object2 = this.getDatabasePath((String)object2);
            if (ContextImpl.moveFiles(((File)object).getParentFile(), ((File)object2).getParentFile(), ((File)object).getName()) == -1) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean moveSharedPreferencesFrom(Context object, String object2) {
        synchronized (ContextImpl.class) {
            object = ((Context)object).getSharedPreferencesPath((String)object2);
            File file = this.getSharedPreferencesPath((String)object2);
            int n = ContextImpl.moveFiles(((File)object).getParentFile(), file.getParentFile(), ((File)object).getName());
            if (n > 0) {
                object2 = this.getSharedPreferencesCacheLocked();
                ((ArrayMap)object2).remove(object);
                ((ArrayMap)object2).remove(file);
            }
            if (n == -1) return false;
            return true;
        }
    }

    @Override
    public FileInputStream openFileInput(String string2) throws FileNotFoundException {
        return new FileInputStream(this.makeFilename(this.getFilesDir(), string2));
    }

    @Override
    public FileOutputStream openFileOutput(String object, int n) throws FileNotFoundException {
        this.checkMode(n);
        boolean bl = (32768 & n) != 0;
        object = this.makeFilename(this.getFilesDir(), (String)object);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream((File)object, bl);
            ContextImpl.setFilePermissionsFromMode(((File)object).getPath(), n, 0);
            return fileOutputStream;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Object object2 = ((File)object).getParentFile();
            ((File)object2).mkdir();
            FileUtils.setPermissions(((File)object2).getPath(), 505, -1, -1);
            object2 = new FileOutputStream((File)object, bl);
            ContextImpl.setFilePermissionsFromMode(((File)object).getPath(), n, 0);
            return object2;
        }
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory) {
        return this.openOrCreateDatabase(string2, n, cursorFactory, null);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String object, int n, SQLiteDatabase.CursorFactory object2, DatabaseErrorHandler databaseErrorHandler) {
        this.checkMode(n);
        object = this.getDatabasePath((String)object);
        int n2 = 268435456;
        if ((n & 8) != 0) {
            n2 = 268435456 | 536870912;
        }
        int n3 = n2;
        if ((n & 16) != 0) {
            n3 = n2 | 16;
        }
        object2 = SQLiteDatabase.openDatabase(((File)object).getPath(), (SQLiteDatabase.CursorFactory)object2, n3, databaseErrorHandler);
        ContextImpl.setFilePermissionsFromMode(((File)object).getPath(), n, 0);
        return object2;
    }

    @Deprecated
    @Override
    public Drawable peekWallpaper() {
        return this.getWallpaperManager().peekDrawable();
    }

    final void performFinalCleanup(String string2, String string3) {
        this.mPackageInfo.removeContextRegistrations(this.getOuterContext(), string2, string3);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return this.registerReceiver(broadcastReceiver, intentFilter, null, null);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int n) {
        return this.registerReceiver(broadcastReceiver, intentFilter, null, null, n);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String string2, Handler handler) {
        return this.registerReceiverInternal(broadcastReceiver, this.getUserId(), intentFilter, string2, handler, this.getOuterContext(), 0);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String string2, Handler handler, int n) {
        return this.registerReceiverInternal(broadcastReceiver, this.getUserId(), intentFilter, string2, handler, this.getOuterContext(), n);
    }

    @Override
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String string2, Handler handler) {
        return this.registerReceiverInternal(broadcastReceiver, userHandle.getIdentifier(), intentFilter, string2, handler, this.getOuterContext(), 0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void reloadSharedPreferences() {
        int n;
        ArrayList<SharedPreferencesImpl> arrayList = new ArrayList<SharedPreferencesImpl>();
        synchronized (ContextImpl.class) {
            ArrayMap<File, SharedPreferencesImpl> arrayMap = this.getSharedPreferencesCacheLocked();
            for (n = 0; n < arrayMap.size(); ++n) {
                SharedPreferencesImpl sharedPreferencesImpl = arrayMap.valueAt(n);
                if (sharedPreferencesImpl == null) continue;
                arrayList.add(sharedPreferencesImpl);
            }
        }
        n = 0;
        while (n < arrayList.size()) {
            ((SharedPreferencesImpl)arrayList.get(n)).startReloadIfChangedUnexpectedly();
            ++n;
        }
        return;
    }

    @Deprecated
    @Override
    public void removeStickyBroadcast(Intent intent) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        Intent intent2 = intent;
        if (string2 != null) {
            intent2 = new Intent(intent);
            intent2.setDataAndType(intent2.getData(), string2);
        }
        try {
            intent2.prepareToLeaveProcess(this);
            ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent2, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        Intent intent2 = intent;
        if (string2 != null) {
            intent2 = new Intent(intent);
            intent2.setDataAndType(intent2.getData(), string2);
        }
        try {
            intent2.prepareToLeaveProcess(this);
            ActivityManager.getService().unbroadcastIntent(this.mMainThread.getApplicationThread(), intent2, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void revokeUriPermission(Uri uri, int n) {
        try {
            ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), null, ContentProvider.getUriWithoutUserId(uri), n, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void revokeUriPermission(String string2, Uri uri, int n) {
        try {
            ActivityManager.getService().revokeUriPermission(this.mMainThread.getApplicationThread(), string2, ContentProvider.getUriWithoutUserId(uri), n, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    final void scheduleFinalCleanup(String string2, String string3) {
        this.mMainThread.scheduleContextCleanup(this, string2, string3);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, null, -1, null, false, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcast(Intent intent, String object) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, -1, null, false, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcast(Intent intent, String object, int n) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, n, null, false, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcast(Intent intent, String object, Bundle bundle) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, -1, bundle, false, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, null, -1, null, false, false, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String string2) {
        this.sendBroadcastAsUser(intent, userHandle, string2, -1);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String object, int n) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, n, null, false, false, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String object, Bundle bundle) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, -1, bundle, false, false, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] arrstring) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, arrstring, -1, null, false, false, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendBroadcastMultiplePermissions(Intent intent, String[] arrstring) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, arrstring, -1, null, false, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String object) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, (String[])object, -1, null, true, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, int n, BroadcastReceiver broadcastReceiver, Handler handler, int n2, String string3, Bundle bundle) {
        this.sendOrderedBroadcast(intent, string2, n, broadcastReceiver, handler, n2, string3, bundle, null);
    }

    void sendOrderedBroadcast(Intent intent, String object, int n, BroadcastReceiver object2, Handler object3, int n2, String string2, Bundle bundle, Bundle bundle2) {
        this.warnIfCallingFromSystemProcess();
        if (object2 != null) {
            if (this.mPackageInfo != null) {
                if (object3 == null) {
                    object3 = this.mMainThread.getHandler();
                }
                object2 = this.mPackageInfo.getReceiverDispatcher((BroadcastReceiver)object2, this.getOuterContext(), (Handler)object3, this.mMainThread.getInstrumentation(), false);
            } else {
                if (object3 == null) {
                    object3 = this.mMainThread.getHandler();
                }
                object2 = new LoadedApk.ReceiverDispatcher((BroadcastReceiver)object2, this.getOuterContext(), (Handler)object3, null, false).getIIntentReceiver();
            }
        } else {
            object2 = null;
        }
        object3 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, (String)object3, (IIntentReceiver)object2, n2, string2, bundle, (String[])object, n, bundle2, true, false, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle) {
        this.sendOrderedBroadcast(intent, string2, -1, broadcastReceiver, handler, n, string3, bundle, null);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle2) {
        this.sendOrderedBroadcast(intent, string2, -1, broadcastReceiver, handler, n, string3, bundle2, bundle);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, int n, BroadcastReceiver broadcastReceiver, Handler handler, int n2, String string3, Bundle bundle) {
        this.sendOrderedBroadcastAsUser(intent, userHandle, string2, n, null, broadcastReceiver, handler, n2, string3, bundle);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String object, int n, Bundle bundle, BroadcastReceiver object2, Handler object3, int n2, String string2, Bundle bundle2) {
        if (object2 != null) {
            if (this.mPackageInfo != null) {
                if (object3 == null) {
                    object3 = this.mMainThread.getHandler();
                }
                object2 = this.mPackageInfo.getReceiverDispatcher((BroadcastReceiver)object2, this.getOuterContext(), (Handler)object3, this.mMainThread.getInstrumentation(), false);
            } else {
                if (object3 == null) {
                    object3 = this.mMainThread.getHandler();
                }
                object2 = new LoadedApk.ReceiverDispatcher((BroadcastReceiver)object2, this.getOuterContext(), (Handler)object3, null, false).getIIntentReceiver();
            }
        } else {
            object2 = null;
        }
        object3 = intent.resolveTypeIfNeeded(this.getContentResolver());
        object = object == null ? null : new String[]{object};
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, (String)object3, (IIntentReceiver)object2, n2, string2, bundle2, (String[])object, n, bundle, true, false, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle) {
        this.sendOrderedBroadcastAsUser(intent, userHandle, string2, -1, null, broadcastReceiver, handler, n, string3, bundle);
    }

    @Deprecated
    @Override
    public void sendStickyBroadcast(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, null, -1, null, false, true, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, null, -1, null, false, true, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle, Bundle bundle) {
        String string2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, string2, null, -1, null, null, null, -1, bundle, false, true, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver object, Handler object2, int n, String string2, Bundle bundle) {
        this.warnIfCallingFromSystemProcess();
        if (object != null) {
            if (this.mPackageInfo != null) {
                if (object2 == null) {
                    object2 = this.mMainThread.getHandler();
                }
                object = this.mPackageInfo.getReceiverDispatcher((BroadcastReceiver)object, this.getOuterContext(), (Handler)object2, this.mMainThread.getInstrumentation(), false);
            } else {
                if (object2 == null) {
                    object2 = this.mMainThread.getHandler();
                }
                object = new LoadedApk.ReceiverDispatcher((BroadcastReceiver)object, this.getOuterContext(), (Handler)object2, null, false).getIIntentReceiver();
            }
        } else {
            object = null;
        }
        object2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, (String)object2, (IIntentReceiver)object, n, string2, bundle, null, -1, null, true, true, this.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @Override
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, BroadcastReceiver object, Handler object2, int n, String string2, Bundle bundle) {
        if (object != null) {
            if (this.mPackageInfo != null) {
                if (object2 == null) {
                    object2 = this.mMainThread.getHandler();
                }
                object = this.mPackageInfo.getReceiverDispatcher((BroadcastReceiver)object, this.getOuterContext(), (Handler)object2, this.mMainThread.getInstrumentation(), false);
            } else {
                if (object2 == null) {
                    object2 = this.mMainThread.getHandler();
                }
                object = new LoadedApk.ReceiverDispatcher((BroadcastReceiver)object, this.getOuterContext(), (Handler)object2, null, false).getIIntentReceiver();
            }
        } else {
            object = null;
        }
        object2 = intent.resolveTypeIfNeeded(this.getContentResolver());
        try {
            intent.prepareToLeaveProcess(this);
            ActivityManager.getService().broadcastIntent(this.mMainThread.getApplicationThread(), intent, (String)object2, (IIntentReceiver)object, n, string2, bundle, null, -1, null, true, true, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void setAutofillClient(AutofillManager.AutofillClient autofillClient) {
        this.mAutofillClient = autofillClient;
    }

    @Override
    public void setAutofillOptions(AutofillOptions autofillOptions) {
        this.mAutofillOptions = autofillOptions;
    }

    @Override
    public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) {
        this.mContentCaptureOptions = contentCaptureOptions;
    }

    @UnsupportedAppUsage
    final void setOuterContext(Context context) {
        this.mOuterContext = context;
    }

    void setResources(Resources resources) {
        if (resources instanceof CompatResources) {
            ((CompatResources)resources).setContext(this);
        }
        this.mResources = resources;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setTheme(int n) {
        Object object = this.mSync;
        synchronized (object) {
            if (this.mThemeResource != n) {
                this.mThemeResource = n;
                this.initializeTheme();
            }
            return;
        }
    }

    @Deprecated
    @Override
    public void setWallpaper(Bitmap bitmap) throws IOException {
        this.getWallpaperManager().setBitmap(bitmap);
    }

    @Deprecated
    @Override
    public void setWallpaper(InputStream inputStream) throws IOException {
        this.getWallpaperManager().setStream(inputStream);
    }

    @Override
    public void startActivities(Intent[] arrintent) {
        this.warnIfCallingFromSystemProcess();
        this.startActivities(arrintent, null);
    }

    @Override
    public void startActivities(Intent[] arrintent, Bundle bundle) {
        this.warnIfCallingFromSystemProcess();
        if ((arrintent[0].getFlags() & 268435456) != 0) {
            this.mMainThread.getInstrumentation().execStartActivities(this.getOuterContext(), this.mMainThread.getApplicationThread(), null, null, arrintent, bundle);
            return;
        }
        throw new AndroidRuntimeException("Calling startActivities() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
    }

    @Override
    public int startActivitiesAsUser(Intent[] arrintent, Bundle bundle, UserHandle userHandle) {
        if ((arrintent[0].getFlags() & 268435456) != 0) {
            return this.mMainThread.getInstrumentation().execStartActivitiesAsUser(this.getOuterContext(), this.mMainThread.getApplicationThread(), null, null, arrintent, bundle, userHandle.getIdentifier());
        }
        throw new AndroidRuntimeException("Calling startActivities() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag on first Intent. Is this really what you want?");
    }

    @Override
    public void startActivity(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        this.startActivity(intent, null);
    }

    @Override
    public void startActivity(Intent intent, Bundle bundle) {
        this.warnIfCallingFromSystemProcess();
        int n = this.getApplicationInfo().targetSdkVersion;
        if (!((intent.getFlags() & 268435456) != 0 || n >= 24 && n < 28 || bundle != null && ActivityOptions.fromBundle(bundle).getLaunchTaskId() != -1)) {
            throw new AndroidRuntimeException("Calling startActivity() from outside of an Activity  context requires the FLAG_ACTIVITY_NEW_TASK flag. Is this really what you want?");
        }
        this.mMainThread.getInstrumentation().execStartActivity(this.getOuterContext(), (IBinder)this.mMainThread.getApplicationThread(), null, (Activity)null, intent, -1, bundle);
    }

    @Override
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        try {
            ActivityTaskManager.getService().startActivityAsUser(this.mMainThread.getApplicationThread(), this.getBasePackageName(), intent, intent.resolveTypeIfNeeded(this.getContentResolver()), null, null, 0, 268435456, null, bundle, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        this.startActivityAsUser(intent, null, userHandle);
    }

    @Override
    public ComponentName startForegroundService(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        return this.startServiceCommon(intent, true, this.mUser);
    }

    @Override
    public ComponentName startForegroundServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.startServiceCommon(intent, true, userHandle);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean startInstrumentation(ComponentName componentName, String string2, Bundle bundle) {
        if (bundle == null) return ActivityManager.getService().startInstrumentation(componentName, string2, 0, bundle, null, null, this.getUserId(), null);
        try {
            bundle.setAllowFds(false);
            return ActivityManager.getService().startInstrumentation(componentName, string2, 0, bundle, null, null, this.getUserId(), null);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int n, int n2, int n3) throws IntentSender.SendIntentException {
        this.startIntentSender(intentSender, intent, n, n2, n3, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void startIntentSender(IntentSender var1_1, Intent var2_3, int var3_4, int var4_5, int var5_6, Bundle var6_7) throws IntentSender.SendIntentException {
        var7_8 = null;
        if (var2_3 == null) ** GOTO lbl8
        try {
            var2_3.migrateExtraStreamToClipData();
            var2_3.prepareToLeaveProcess(this);
            var7_8 = var2_3.resolveTypeIfNeeded(this.getContentResolver());
lbl8: // 2 sources:
            var8_9 = ActivityTaskManager.getService();
            var9_10 = this.mMainThread.getApplicationThread();
            var10_11 = var1_1 != null ? var1_1.getTarget() : null;
            var3_4 = var8_9.startActivityIntentSender(var9_10, var10_11, (IBinder)(var1_1 = var1_1 != null ? var1_1.getWhitelistToken() : null), var2_3, var7_8, null, null, 0, var3_4, var4_5, var6_7);
            if (var3_4 != -96) {
                Instrumentation.checkStartActivityResult(var3_4, null);
                return;
            }
            var1_1 = new IntentSender.SendIntentException();
            throw var1_1;
        }
        catch (RemoteException var1_2) {
            throw var1_2.rethrowFromSystemServer();
        }
    }

    @Override
    public ComponentName startService(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        return this.startServiceCommon(intent, false, this.mUser);
    }

    @Override
    public ComponentName startServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.startServiceCommon(intent, false, userHandle);
    }

    @Override
    public boolean stopService(Intent intent) {
        this.warnIfCallingFromSystemProcess();
        return this.stopServiceCommon(intent, this.mUser);
    }

    @Override
    public boolean stopServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.stopServiceCommon(intent, userHandle);
    }

    @Override
    public void unbindService(ServiceConnection object) {
        if (object != null) {
            LoadedApk loadedApk = this.mPackageInfo;
            if (loadedApk != null) {
                object = loadedApk.forgetServiceDispatcher(this.getOuterContext(), (ServiceConnection)object);
                try {
                    ActivityManager.getService().unbindService((IServiceConnection)object);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new RuntimeException("Not supported in system context");
        }
        throw new IllegalArgumentException("connection is null");
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver object) {
        LoadedApk loadedApk = this.mPackageInfo;
        if (loadedApk != null) {
            object = loadedApk.forgetReceiverDispatcher(this.getOuterContext(), (BroadcastReceiver)object);
            try {
                ActivityManager.getService().unregisterReceiver((IIntentReceiver)object);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new RuntimeException("Not supported in system context");
    }

    @Override
    public void updateDisplay(int n) {
        this.mDisplay = this.mResourcesManager.getAdjustedDisplay(n, this.mResources);
    }

    @Override
    public void updateServiceGroup(ServiceConnection serviceConnection, int n, int n2) {
        if (serviceConnection != null) {
            Object object = this.mPackageInfo;
            if (object != null) {
                if ((object = ((LoadedApk)object).lookupServiceDispatcher(serviceConnection, this.getOuterContext())) != null) {
                    try {
                        ActivityManager.getService().updateServiceGroup((IServiceConnection)object, n, n2);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("ServiceConnection not currently bound: ");
                ((StringBuilder)object).append(serviceConnection);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new RuntimeException("Not supported in system context");
        }
        throw new IllegalArgumentException("connection is null");
    }

    private static final class ApplicationContentResolver
    extends ContentResolver {
        @UnsupportedAppUsage
        private final ActivityThread mMainThread;

        public ApplicationContentResolver(Context context, ActivityThread activityThread) {
            super(context);
            this.mMainThread = Preconditions.checkNotNull(activityThread);
        }

        @Override
        protected IContentProvider acquireExistingProvider(Context context, String string2) {
            return this.mMainThread.acquireExistingProvider(context, ContentProvider.getAuthorityWithoutUserId(string2), this.resolveUserIdFromAuthority(string2), true);
        }

        @UnsupportedAppUsage
        @Override
        protected IContentProvider acquireProvider(Context context, String string2) {
            return this.mMainThread.acquireProvider(context, ContentProvider.getAuthorityWithoutUserId(string2), this.resolveUserIdFromAuthority(string2), true);
        }

        @Override
        protected IContentProvider acquireUnstableProvider(Context context, String string2) {
            return this.mMainThread.acquireProvider(context, ContentProvider.getAuthorityWithoutUserId(string2), this.resolveUserIdFromAuthority(string2), false);
        }

        @Override
        public void appNotRespondingViaProvider(IContentProvider iContentProvider) {
            this.mMainThread.appNotRespondingViaProvider(iContentProvider.asBinder());
        }

        @Override
        public boolean releaseProvider(IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, true);
        }

        @Override
        public boolean releaseUnstableProvider(IContentProvider iContentProvider) {
            return this.mMainThread.releaseProvider(iContentProvider, false);
        }

        protected int resolveUserIdFromAuthority(String string2) {
            return ContentProvider.getUserIdFromAuthority(string2, this.getUserId());
        }

        @Override
        public void unstableProviderDied(IContentProvider iContentProvider) {
            this.mMainThread.handleUnstableProviderDied(iContentProvider.asBinder(), true);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface ServiceInitializationState {
    }

}

