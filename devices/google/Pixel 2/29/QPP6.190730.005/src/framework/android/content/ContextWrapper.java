/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.content.AutofillOptions;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentCaptureOptions;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.UserHandle;
import android.view.Display;
import android.view.DisplayAdjustments;
import android.view.autofill.AutofillManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public class ContextWrapper
extends Context {
    @UnsupportedAppUsage
    Context mBase;

    public ContextWrapper(Context context) {
        this.mBase = context;
    }

    protected void attachBaseContext(Context context) {
        if (this.mBase == null) {
            this.mBase = context;
            return;
        }
        throw new IllegalStateException("Base context already set");
    }

    @Override
    public boolean bindIsolatedService(Intent intent, int n, String string2, Executor executor, ServiceConnection serviceConnection) {
        return this.mBase.bindIsolatedService(intent, n, string2, executor, serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, int n, Executor executor, ServiceConnection serviceConnection) {
        return this.mBase.bindService(intent, n, executor, serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int n) {
        return this.mBase.bindService(intent, serviceConnection, n);
    }

    @Override
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, Handler handler, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, n, handler, userHandle);
    }

    @Override
    public boolean bindServiceAsUser(Intent intent, ServiceConnection serviceConnection, int n, UserHandle userHandle) {
        return this.mBase.bindServiceAsUser(intent, serviceConnection, n, userHandle);
    }

    @Override
    public boolean canLoadUnsafeResources() {
        return this.mBase.canLoadUnsafeResources();
    }

    @Override
    public boolean canStartActivityForResult() {
        return this.mBase.canStartActivityForResult();
    }

    @Override
    public int checkCallingOrSelfPermission(String string2) {
        return this.mBase.checkCallingOrSelfPermission(string2);
    }

    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int n) {
        return this.mBase.checkCallingOrSelfUriPermission(uri, n);
    }

    @Override
    public int checkCallingPermission(String string2) {
        return this.mBase.checkCallingPermission(string2);
    }

    @Override
    public int checkCallingUriPermission(Uri uri, int n) {
        return this.mBase.checkCallingUriPermission(uri, n);
    }

    @Override
    public int checkPermission(String string2, int n, int n2) {
        return this.mBase.checkPermission(string2, n, n2);
    }

    @Override
    public int checkPermission(String string2, int n, int n2, IBinder iBinder) {
        return this.mBase.checkPermission(string2, n, n2, iBinder);
    }

    @Override
    public int checkSelfPermission(String string2) {
        return this.mBase.checkSelfPermission(string2);
    }

    @Override
    public int checkUriPermission(Uri uri, int n, int n2, int n3) {
        return this.mBase.checkUriPermission(uri, n, n2, n3);
    }

    @Override
    public int checkUriPermission(Uri uri, int n, int n2, int n3, IBinder iBinder) {
        return this.mBase.checkUriPermission(uri, n, n2, n3, iBinder);
    }

    @Override
    public int checkUriPermission(Uri uri, String string2, String string3, int n, int n2, int n3) {
        return this.mBase.checkUriPermission(uri, string2, string3, n, n2, n3);
    }

    @Deprecated
    @Override
    public void clearWallpaper() throws IOException {
        this.mBase.clearWallpaper();
    }

    @UnsupportedAppUsage
    @Override
    public Context createApplicationContext(ApplicationInfo applicationInfo, int n) throws PackageManager.NameNotFoundException {
        return this.mBase.createApplicationContext(applicationInfo, n);
    }

    @Override
    public Context createConfigurationContext(Configuration configuration) {
        return this.mBase.createConfigurationContext(configuration);
    }

    @Override
    public Context createContextForSplit(String string2) throws PackageManager.NameNotFoundException {
        return this.mBase.createContextForSplit(string2);
    }

    @SystemApi
    @Override
    public Context createCredentialProtectedStorageContext() {
        return this.mBase.createCredentialProtectedStorageContext();
    }

    @Override
    public Context createDeviceProtectedStorageContext() {
        return this.mBase.createDeviceProtectedStorageContext();
    }

    @Override
    public Context createDisplayContext(Display display) {
        return this.mBase.createDisplayContext(display);
    }

    @Override
    public Context createPackageContext(String string2, int n) throws PackageManager.NameNotFoundException {
        return this.mBase.createPackageContext(string2, n);
    }

    @Override
    public Context createPackageContextAsUser(String string2, int n, UserHandle userHandle) throws PackageManager.NameNotFoundException {
        return this.mBase.createPackageContextAsUser(string2, n, userHandle);
    }

    @Override
    public String[] databaseList() {
        return this.mBase.databaseList();
    }

    @Override
    public boolean deleteDatabase(String string2) {
        return this.mBase.deleteDatabase(string2);
    }

    @Override
    public boolean deleteFile(String string2) {
        return this.mBase.deleteFile(string2);
    }

    @Override
    public boolean deleteSharedPreferences(String string2) {
        return this.mBase.deleteSharedPreferences(string2);
    }

    @Override
    public void enforceCallingOrSelfPermission(String string2, String string3) {
        this.mBase.enforceCallingOrSelfPermission(string2, string3);
    }

    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int n, String string2) {
        this.mBase.enforceCallingOrSelfUriPermission(uri, n, string2);
    }

    @Override
    public void enforceCallingPermission(String string2, String string3) {
        this.mBase.enforceCallingPermission(string2, string3);
    }

    @Override
    public void enforceCallingUriPermission(Uri uri, int n, String string2) {
        this.mBase.enforceCallingUriPermission(uri, n, string2);
    }

    @Override
    public void enforcePermission(String string2, int n, int n2, String string3) {
        this.mBase.enforcePermission(string2, n, n2, string3);
    }

    @Override
    public void enforceUriPermission(Uri uri, int n, int n2, int n3, String string2) {
        this.mBase.enforceUriPermission(uri, n, n2, n3, string2);
    }

    @Override
    public void enforceUriPermission(Uri uri, String string2, String string3, int n, int n2, int n3, String string4) {
        this.mBase.enforceUriPermission(uri, string2, string3, n, n2, n3, string4);
    }

    @Override
    public String[] fileList() {
        return this.mBase.fileList();
    }

    @Override
    public IBinder getActivityToken() {
        return this.mBase.getActivityToken();
    }

    @Override
    public Context getApplicationContext() {
        return this.mBase.getApplicationContext();
    }

    @Override
    public ApplicationInfo getApplicationInfo() {
        return this.mBase.getApplicationInfo();
    }

    @Override
    public AssetManager getAssets() {
        return this.mBase.getAssets();
    }

    @Override
    public AutofillManager.AutofillClient getAutofillClient() {
        return this.mBase.getAutofillClient();
    }

    @Override
    public AutofillOptions getAutofillOptions() {
        Object object = this.mBase;
        object = object == null ? null : ((Context)object).getAutofillOptions();
        return object;
    }

    public Context getBaseContext() {
        return this.mBase;
    }

    @UnsupportedAppUsage
    @Override
    public String getBasePackageName() {
        return this.mBase.getBasePackageName();
    }

    @Override
    public File getCacheDir() {
        return this.mBase.getCacheDir();
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.mBase.getClassLoader();
    }

    @Override
    public File getCodeCacheDir() {
        return this.mBase.getCodeCacheDir();
    }

    @Override
    public ContentCaptureOptions getContentCaptureOptions() {
        Object object = this.mBase;
        object = object == null ? null : ((Context)object).getContentCaptureOptions();
        return object;
    }

    @Override
    public ContentResolver getContentResolver() {
        return this.mBase.getContentResolver();
    }

    @Override
    public File getDataDir() {
        return this.mBase.getDataDir();
    }

    @Override
    public File getDatabasePath(String string2) {
        return this.mBase.getDatabasePath(string2);
    }

    @Override
    public File getDir(String string2, int n) {
        return this.mBase.getDir(string2, n);
    }

    @Override
    public Display getDisplay() {
        return this.mBase.getDisplay();
    }

    @Override
    public DisplayAdjustments getDisplayAdjustments(int n) {
        return this.mBase.getDisplayAdjustments(n);
    }

    @Override
    public int getDisplayId() {
        return this.mBase.getDisplayId();
    }

    @Override
    public File getExternalCacheDir() {
        return this.mBase.getExternalCacheDir();
    }

    @Override
    public File[] getExternalCacheDirs() {
        return this.mBase.getExternalCacheDirs();
    }

    @Override
    public File getExternalFilesDir(String string2) {
        return this.mBase.getExternalFilesDir(string2);
    }

    @Override
    public File[] getExternalFilesDirs(String string2) {
        return this.mBase.getExternalFilesDirs(string2);
    }

    @Override
    public File[] getExternalMediaDirs() {
        return this.mBase.getExternalMediaDirs();
    }

    @Override
    public File getFileStreamPath(String string2) {
        return this.mBase.getFileStreamPath(string2);
    }

    @Override
    public File getFilesDir() {
        return this.mBase.getFilesDir();
    }

    @Override
    public IApplicationThread getIApplicationThread() {
        return this.mBase.getIApplicationThread();
    }

    @Override
    public Executor getMainExecutor() {
        return this.mBase.getMainExecutor();
    }

    @Override
    public Looper getMainLooper() {
        return this.mBase.getMainLooper();
    }

    @Override
    public Handler getMainThreadHandler() {
        return this.mBase.getMainThreadHandler();
    }

    @Override
    public int getNextAutofillId() {
        return this.mBase.getNextAutofillId();
    }

    @Override
    public File getNoBackupFilesDir() {
        return this.mBase.getNoBackupFilesDir();
    }

    @Override
    public File getObbDir() {
        return this.mBase.getObbDir();
    }

    @Override
    public File[] getObbDirs() {
        return this.mBase.getObbDirs();
    }

    @Override
    public String getOpPackageName() {
        return this.mBase.getOpPackageName();
    }

    @Override
    public String getPackageCodePath() {
        return this.mBase.getPackageCodePath();
    }

    @Override
    public PackageManager getPackageManager() {
        return this.mBase.getPackageManager();
    }

    @Override
    public String getPackageName() {
        return this.mBase.getPackageName();
    }

    @Override
    public String getPackageResourcePath() {
        return this.mBase.getPackageResourcePath();
    }

    @Override
    public File getPreloadsFileCache() {
        return this.mBase.getPreloadsFileCache();
    }

    @Override
    public Resources getResources() {
        return this.mBase.getResources();
    }

    @Override
    public IServiceConnection getServiceDispatcher(ServiceConnection serviceConnection, Handler handler, int n) {
        return this.mBase.getServiceDispatcher(serviceConnection, handler, n);
    }

    @Override
    public SharedPreferences getSharedPreferences(File file, int n) {
        return this.mBase.getSharedPreferences(file, n);
    }

    @Override
    public SharedPreferences getSharedPreferences(String string2, int n) {
        return this.mBase.getSharedPreferences(string2, n);
    }

    @Override
    public File getSharedPreferencesPath(String string2) {
        return this.mBase.getSharedPreferencesPath(string2);
    }

    @Override
    public Object getSystemService(String string2) {
        return this.mBase.getSystemService(string2);
    }

    @Override
    public String getSystemServiceName(Class<?> class_) {
        return this.mBase.getSystemServiceName(class_);
    }

    @Override
    public Resources.Theme getTheme() {
        return this.mBase.getTheme();
    }

    @UnsupportedAppUsage
    @Override
    public int getThemeResId() {
        return this.mBase.getThemeResId();
    }

    @Override
    public int getUserId() {
        return this.mBase.getUserId();
    }

    @Deprecated
    @Override
    public Drawable getWallpaper() {
        return this.mBase.getWallpaper();
    }

    @Deprecated
    @Override
    public int getWallpaperDesiredMinimumHeight() {
        return this.mBase.getWallpaperDesiredMinimumHeight();
    }

    @Deprecated
    @Override
    public int getWallpaperDesiredMinimumWidth() {
        return this.mBase.getWallpaperDesiredMinimumWidth();
    }

    @Override
    public void grantUriPermission(String string2, Uri uri, int n) {
        this.mBase.grantUriPermission(string2, uri, n);
    }

    @SystemApi
    @Override
    public boolean isCredentialProtectedStorage() {
        return this.mBase.isCredentialProtectedStorage();
    }

    @Override
    public boolean isDeviceProtectedStorage() {
        return this.mBase.isDeviceProtectedStorage();
    }

    @Override
    public boolean isRestricted() {
        return this.mBase.isRestricted();
    }

    @Override
    public boolean moveDatabaseFrom(Context context, String string2) {
        return this.mBase.moveDatabaseFrom(context, string2);
    }

    @Override
    public boolean moveSharedPreferencesFrom(Context context, String string2) {
        return this.mBase.moveSharedPreferencesFrom(context, string2);
    }

    @Override
    public FileInputStream openFileInput(String string2) throws FileNotFoundException {
        return this.mBase.openFileInput(string2);
    }

    @Override
    public FileOutputStream openFileOutput(String string2, int n) throws FileNotFoundException {
        return this.mBase.openFileOutput(string2, n);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory) {
        return this.mBase.openOrCreateDatabase(string2, n, cursorFactory);
    }

    @Override
    public SQLiteDatabase openOrCreateDatabase(String string2, int n, SQLiteDatabase.CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return this.mBase.openOrCreateDatabase(string2, n, cursorFactory, databaseErrorHandler);
    }

    @Deprecated
    @Override
    public Drawable peekWallpaper() {
        return this.mBase.peekWallpaper();
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, int n) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, n);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String string2, Handler handler) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, string2, handler);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter, String string2, Handler handler, int n) {
        return this.mBase.registerReceiver(broadcastReceiver, intentFilter, string2, handler, n);
    }

    @UnsupportedAppUsage
    @Override
    public Intent registerReceiverAsUser(BroadcastReceiver broadcastReceiver, UserHandle userHandle, IntentFilter intentFilter, String string2, Handler handler) {
        return this.mBase.registerReceiverAsUser(broadcastReceiver, userHandle, intentFilter, string2, handler);
    }

    @Override
    public void reloadSharedPreferences() {
        this.mBase.reloadSharedPreferences();
    }

    @Deprecated
    @Override
    public void removeStickyBroadcast(Intent intent) {
        this.mBase.removeStickyBroadcast(intent);
    }

    @Deprecated
    @Override
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.removeStickyBroadcastAsUser(intent, userHandle);
    }

    @Override
    public void revokeUriPermission(Uri uri, int n) {
        this.mBase.revokeUriPermission(uri, n);
    }

    @Override
    public void revokeUriPermission(String string2, Uri uri, int n) {
        this.mBase.revokeUriPermission(string2, uri, n);
    }

    @Override
    public void sendBroadcast(Intent intent) {
        this.mBase.sendBroadcast(intent);
    }

    @Override
    public void sendBroadcast(Intent intent, String string2) {
        this.mBase.sendBroadcast(intent, string2);
    }

    @Override
    public void sendBroadcast(Intent intent, String string2, int n) {
        this.mBase.sendBroadcast(intent, string2, n);
    }

    @SystemApi
    @Override
    public void sendBroadcast(Intent intent, String string2, Bundle bundle) {
        this.mBase.sendBroadcast(intent, string2, bundle);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String string2) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, string2);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, int n) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, string2, n);
    }

    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, Bundle bundle) {
        this.mBase.sendBroadcastAsUser(intent, userHandle, string2, bundle);
    }

    @Override
    public void sendBroadcastAsUserMultiplePermissions(Intent intent, UserHandle userHandle, String[] arrstring) {
        this.mBase.sendBroadcastAsUserMultiplePermissions(intent, userHandle, arrstring);
    }

    @Override
    public void sendBroadcastMultiplePermissions(Intent intent, String[] arrstring) {
        this.mBase.sendBroadcastMultiplePermissions(intent, arrstring);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2) {
        this.mBase.sendOrderedBroadcast(intent, string2);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, int n, BroadcastReceiver broadcastReceiver, Handler handler, int n2, String string3, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, string2, n, broadcastReceiver, handler, n2, string3, bundle);
    }

    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle) {
        this.mBase.sendOrderedBroadcast(intent, string2, broadcastReceiver, handler, n, string3, bundle);
    }

    @SystemApi
    @Override
    public void sendOrderedBroadcast(Intent intent, String string2, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle2) {
        this.mBase.sendOrderedBroadcast(intent, string2, bundle, broadcastReceiver, handler, n, string3, bundle2);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, int n, BroadcastReceiver broadcastReceiver, Handler handler, int n2, String string3, Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, string2, n, broadcastReceiver, handler, n2, string3, bundle);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, int n, Bundle bundle, BroadcastReceiver broadcastReceiver, Handler handler, int n2, String string3, Bundle bundle2) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, string2, n, bundle, broadcastReceiver, handler, n2, string3, bundle2);
    }

    @Override
    public void sendOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, String string2, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string3, Bundle bundle) {
        this.mBase.sendOrderedBroadcastAsUser(intent, userHandle, string2, broadcastReceiver, handler, n, string3, bundle);
    }

    @Deprecated
    @Override
    public void sendStickyBroadcast(Intent intent) {
        this.mBase.sendStickyBroadcast(intent);
    }

    @Deprecated
    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle);
    }

    @Deprecated
    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle userHandle, Bundle bundle) {
        this.mBase.sendStickyBroadcastAsUser(intent, userHandle, bundle);
    }

    @Deprecated
    @Override
    public void sendStickyOrderedBroadcast(Intent intent, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string2, Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcast(intent, broadcastReceiver, handler, n, string2, bundle);
    }

    @Deprecated
    @Override
    public void sendStickyOrderedBroadcastAsUser(Intent intent, UserHandle userHandle, BroadcastReceiver broadcastReceiver, Handler handler, int n, String string2, Bundle bundle) {
        this.mBase.sendStickyOrderedBroadcastAsUser(intent, userHandle, broadcastReceiver, handler, n, string2, bundle);
    }

    @Override
    public void setAutofillClient(AutofillManager.AutofillClient autofillClient) {
        this.mBase.setAutofillClient(autofillClient);
    }

    @Override
    public void setAutofillOptions(AutofillOptions autofillOptions) {
        Context context = this.mBase;
        if (context != null) {
            context.setAutofillOptions(autofillOptions);
        }
    }

    @Override
    public void setContentCaptureOptions(ContentCaptureOptions contentCaptureOptions) {
        Context context = this.mBase;
        if (context != null) {
            context.setContentCaptureOptions(contentCaptureOptions);
        }
    }

    @Override
    public void setTheme(int n) {
        this.mBase.setTheme(n);
    }

    @Deprecated
    @Override
    public void setWallpaper(Bitmap bitmap) throws IOException {
        this.mBase.setWallpaper(bitmap);
    }

    @Deprecated
    @Override
    public void setWallpaper(InputStream inputStream) throws IOException {
        this.mBase.setWallpaper(inputStream);
    }

    @Override
    public void startActivities(Intent[] arrintent) {
        this.mBase.startActivities(arrintent);
    }

    @Override
    public void startActivities(Intent[] arrintent, Bundle bundle) {
        this.mBase.startActivities(arrintent, bundle);
    }

    @Override
    public int startActivitiesAsUser(Intent[] arrintent, Bundle bundle, UserHandle userHandle) {
        return this.mBase.startActivitiesAsUser(arrintent, bundle, userHandle);
    }

    @Override
    public void startActivity(Intent intent) {
        this.mBase.startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle bundle) {
        this.mBase.startActivity(intent, bundle);
    }

    @Override
    public void startActivityAsUser(Intent intent, Bundle bundle, UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, bundle, userHandle);
    }

    @Override
    public void startActivityAsUser(Intent intent, UserHandle userHandle) {
        this.mBase.startActivityAsUser(intent, userHandle);
    }

    @Override
    public void startActivityForResult(String string2, Intent intent, int n, Bundle bundle) {
        this.mBase.startActivityForResult(string2, intent, n, bundle);
    }

    @Override
    public ComponentName startForegroundService(Intent intent) {
        return this.mBase.startForegroundService(intent);
    }

    @UnsupportedAppUsage
    @Override
    public ComponentName startForegroundServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.startForegroundServiceAsUser(intent, userHandle);
    }

    @Override
    public boolean startInstrumentation(ComponentName componentName, String string2, Bundle bundle) {
        return this.mBase.startInstrumentation(componentName, string2, bundle);
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int n, int n2, int n3) throws IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, n, n2, n3);
    }

    @Override
    public void startIntentSender(IntentSender intentSender, Intent intent, int n, int n2, int n3, Bundle bundle) throws IntentSender.SendIntentException {
        this.mBase.startIntentSender(intentSender, intent, n, n2, n3, bundle);
    }

    @Override
    public ComponentName startService(Intent intent) {
        return this.mBase.startService(intent);
    }

    @UnsupportedAppUsage
    @Override
    public ComponentName startServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.startServiceAsUser(intent, userHandle);
    }

    @Override
    public boolean stopService(Intent intent) {
        return this.mBase.stopService(intent);
    }

    @Override
    public boolean stopServiceAsUser(Intent intent, UserHandle userHandle) {
        return this.mBase.stopServiceAsUser(intent, userHandle);
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        this.mBase.unbindService(serviceConnection);
    }

    @Override
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        this.mBase.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void updateDisplay(int n) {
        this.mBase.updateDisplay(n);
    }

    @Override
    public void updateServiceGroup(ServiceConnection serviceConnection, int n, int n2) {
        this.mBase.updateServiceGroup(serviceConnection, n, n2);
    }
}

