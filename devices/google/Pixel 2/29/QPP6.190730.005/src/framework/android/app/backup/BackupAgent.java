/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  libcore.io.IoUtils
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.app.backup;

import android.app.IBackupAgent;
import android.app.QueuedWork;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.BackupUtils;
import android.app.backup.FullBackup;
import android.app.backup.FullBackupDataOutput;
import android.app.backup.IBackupCallback;
import android.app.backup.IBackupManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.RemoteException;
import android.os.UserHandle;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.ArraySet;
import android.util.Log;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

public abstract class BackupAgent
extends ContextWrapper {
    private static final boolean DEBUG = false;
    public static final int FLAG_CLIENT_SIDE_ENCRYPTION_ENABLED = 1;
    public static final int FLAG_DEVICE_TO_DEVICE_TRANSFER = 2;
    public static final int FLAG_FAKE_CLIENT_SIDE_ENCRYPTION_ENABLED = Integer.MIN_VALUE;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_SUCCESS = 0;
    private static final String TAG = "BackupAgent";
    public static final int TYPE_DIRECTORY = 2;
    public static final int TYPE_EOF = 0;
    public static final int TYPE_FILE = 1;
    public static final int TYPE_SYMLINK = 3;
    private final IBinder mBinder = new BackupServiceBinder().asBinder();
    Handler mHandler = null;
    private UserHandle mUser;

    public BackupAgent() {
        super(null);
    }

    /*
     * WARNING - void declaration
     */
    private void applyXmlFiltersAndDoFullBackupForDomain(String string2, String string3, Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> object2, ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> arraySet, ArraySet<String> arraySet2, FullBackupDataOutput fullBackupDataOutput) throws IOException {
        void var5_7;
        void var6_8;
        void var4_6;
        if (object2 != null && object2.size() != 0) {
            if (object2.get(string3) != null) {
                for (FullBackup.BackupScheme.PathWithRequiredFlags pathWithRequiredFlags : (Set)object2.get(string3)) {
                    if (!this.areIncludeRequiredTransportFlagsSatisfied(pathWithRequiredFlags.getRequiredFlags(), var6_8.getTransportFlags())) continue;
                    this.fullBackupFileTree(string2, string3, pathWithRequiredFlags.getPath(), (ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags>)var4_6, (ArraySet<String>)var5_7, (FullBackupDataOutput)var6_8);
                }
            }
        } else {
            this.fullBackupFileTree(string2, string3, FullBackup.getBackupScheme(this).tokenToDirectoryPath(string3), (ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags>)var4_6, (ArraySet<String>)var5_7, (FullBackupDataOutput)var6_8);
        }
    }

    private boolean areIncludeRequiredTransportFlagsSatisfied(int n, int n2) {
        boolean bl = (n2 & n) == n;
        return bl;
    }

    private int getBackupUserId() {
        UserHandle userHandle = this.mUser;
        int n = userHandle == null ? super.getUserId() : userHandle.getIdentifier();
        return n;
    }

    private boolean isFileEligibleForRestore(File serializable) throws IOException {
        Object object;
        Object object2 = FullBackup.getBackupScheme(this);
        if (!((FullBackup.BackupScheme)object2).isFullBackupContentEnabled()) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onRestoreFile \"");
                stringBuilder.append(((File)serializable).getCanonicalPath());
                stringBuilder.append("\" : fullBackupContent not enabled for ");
                stringBuilder.append(this.getPackageName());
                Log.v("BackupXmlParserLogging", stringBuilder.toString());
            }
            return false;
        }
        String string2 = ((File)serializable).getCanonicalPath();
        try {
            object = ((FullBackup.BackupScheme)object2).maybeParseAndGetCanonicalIncludePaths();
            object2 = ((FullBackup.BackupScheme)object2).maybeParseAndGetCanonicalExcludePaths();
        }
        catch (XmlPullParserException xmlPullParserException) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("onRestoreFile \"");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append("\" : Exception trying to parse fullBackupContent xml file! Aborting onRestoreFile.");
                Log.v("BackupXmlParserLogging", ((StringBuilder)serializable).toString(), xmlPullParserException);
            }
            return false;
        }
        if (object2 != null && BackupUtils.isFileSpecifiedInPathList((File)serializable, (Collection<FullBackup.BackupScheme.PathWithRequiredFlags>)object2)) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("onRestoreFile: \"");
                ((StringBuilder)serializable).append(string2);
                ((StringBuilder)serializable).append("\": listed in excludes; skipping.");
                Log.v("BackupXmlParserLogging", ((StringBuilder)serializable).toString());
            }
            return false;
        }
        if (object != null && !object.isEmpty()) {
            boolean bl;
            block11 : {
                boolean bl2 = false;
                object = object.values().iterator();
                do {
                    bl = bl2;
                    if (!object.hasNext()) break block11;
                } while (!(bl2 |= BackupUtils.isFileSpecifiedInPathList((File)serializable, (Collection<FullBackup.BackupScheme.PathWithRequiredFlags>)(object2 = (Set)object.next()))));
                bl = bl2;
            }
            if (!bl) {
                if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("onRestoreFile: Trying to restore \"");
                    ((StringBuilder)serializable).append(string2);
                    ((StringBuilder)serializable).append("\" but it isn't specified in the included files; skipping.");
                    Log.v("BackupXmlParserLogging", ((StringBuilder)serializable).toString());
                }
                return false;
            }
        }
        return true;
    }

    private boolean manifestExcludesContainFilePath(ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> object, String string2) {
        object = ((ArraySet)object).iterator();
        while (object.hasNext()) {
            String string3 = ((FullBackup.BackupScheme.PathWithRequiredFlags)object.next()).getPath();
            if (string3 == null || !string3.equals(string2)) continue;
            return true;
        }
        return false;
    }

    private void waitForSharedPrefs() {
        Handler handler = this.getHandler();
        SharedPrefsSynchronizer sharedPrefsSynchronizer = new SharedPrefsSynchronizer();
        handler.postAtFrontOfQueue(sharedPrefsSynchronizer);
        try {
            sharedPrefsSynchronizer.mLatch.await();
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }

    public void attach(Context context) {
        this.attachBaseContext(context);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void fullBackupFile(File object, FullBackupDataOutput fullBackupDataOutput) {
        block20 : {
            block21 : {
                String string2;
                block23 : {
                    Object object2;
                    block22 : {
                        String string10;
                        String string9;
                        Object object3;
                        String string16;
                        String string13;
                        String string12;
                        String string7;
                        String string15;
                        String string11;
                        String string6;
                        String string4;
                        String string5;
                        String string14;
                        String string8;
                        String string3;
                        block19 : {
                            block18 : {
                                object2 = this.getApplicationInfo();
                                object3 = this.createCredentialProtectedStorageContext();
                                string4 = ((Context)object3).getDataDir().getCanonicalPath();
                                string7 = ((Context)object3).getFilesDir().getCanonicalPath();
                                string9 = ((Context)object3).getNoBackupFilesDir().getCanonicalPath();
                                string6 = ((Context)object3).getDatabasePath("foo").getParentFile().getCanonicalPath();
                                string11 = ((Context)object3).getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
                                string10 = ((Context)object3).getCacheDir().getCanonicalPath();
                                string14 = ((Context)object3).getCodeCacheDir().getCanonicalPath();
                                object3 = this.createDeviceProtectedStorageContext();
                                string3 = ((Context)object3).getDataDir().getCanonicalPath();
                                string15 = ((Context)object3).getFilesDir().getCanonicalPath();
                                string12 = ((Context)object3).getNoBackupFilesDir().getCanonicalPath();
                                string8 = ((Context)object3).getDatabasePath("foo").getParentFile().getCanonicalPath();
                                string13 = ((Context)object3).getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
                                string5 = ((Context)object3).getCacheDir().getCanonicalPath();
                                string16 = ((Context)object3).getCodeCacheDir().getCanonicalPath();
                                try {
                                    if (((ApplicationInfo)object2).nativeLibraryDir == null) {
                                        object3 = null;
                                    } else {
                                        object3 = new File(((ApplicationInfo)object2).nativeLibraryDir);
                                        object3 = ((File)object3).getCanonicalPath();
                                    }
                                    int n = Process.myUid();
                                    if (n == 1000) break block18;
                                }
                                catch (IOException iOException) {}
                                try {
                                    object2 = this.getExternalFilesDir(null);
                                    if (object2 == null) break block18;
                                    object2 = ((File)object2).getCanonicalPath();
                                    break block19;
                                }
                                catch (IOException iOException) {
                                    break block20;
                                }
                            }
                            object2 = null;
                        }
                        try {
                            string2 = ((File)object).getCanonicalPath();
                            if (string2.startsWith(string10) || string2.startsWith(string14) || string2.startsWith(string9) || string2.startsWith(string5) || string2.startsWith(string16) || string2.startsWith(string12) || string2.startsWith((String)object3)) break block21;
                            if (string2.startsWith(string6)) {
                                object = "db";
                                object2 = string6;
                                break block22;
                            }
                            if (string2.startsWith(string11)) {
                                object = "sp";
                                object2 = string11;
                                break block22;
                            }
                            if (string2.startsWith(string7)) {
                                object = "f";
                                object2 = string7;
                                break block22;
                            }
                            if (string2.startsWith(string4)) {
                                object = "r";
                                object2 = string4;
                                break block22;
                            }
                            if (string2.startsWith(string8)) {
                                object = "d_db";
                                object2 = string8;
                                break block22;
                            }
                            if (string2.startsWith(string13)) {
                                object = "d_sp";
                                object2 = string13;
                                break block22;
                            }
                            if (string2.startsWith(string15)) {
                                object = "d_f";
                                object2 = string15;
                                break block22;
                            }
                            if (string2.startsWith(string3)) {
                                object = "d_r";
                                object2 = string3;
                                break block22;
                            }
                            if (object2 == null || !string2.startsWith((String)object2)) break block23;
                            object = "ef";
                        }
                        catch (IOException iOException) {}
                    }
                    FullBackup.backupToTar(this.getPackageName(), (String)object, null, (String)object2, string2, fullBackupDataOutput);
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("File ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" is in an unsupported location; skipping");
                Log.w(TAG, ((StringBuilder)object).toString());
                return;
            }
            Log.w(TAG, "lib, cache, code_cache, and no_backup files are not backed up");
            return;
            break block20;
            catch (IOException iOException) {
                // empty catch block
            }
        }
        Log.w(TAG, "Unable to obtain canonical paths");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected final void fullBackupFileTree(String string2, String string3, String object, ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> arraySet, ArraySet<String> arraySet2, FullBackupDataOutput fullBackupDataOutput) {
        String string4 = FullBackup.getBackupScheme(this).tokenToDirectoryPath(string3);
        if (string4 == null) {
            return;
        }
        if (((File)(object = new File((String)object))).exists()) {
            LinkedList<Object> linkedList = new LinkedList<Object>();
            linkedList.add(object);
            do {
                CharSequence charSequence;
                block7 : {
                    object = arraySet;
                    if (linkedList.size() <= 0) break;
                    File file = (File)linkedList.remove(0);
                    try {
                        StructStat structStat = Os.lstat((String)file.getPath());
                        if (!OsConstants.S_ISREG((int)structStat.st_mode) && !OsConstants.S_ISDIR((int)structStat.st_mode)) continue;
                        charSequence = file.getCanonicalPath();
                        if (object != null && this.manifestExcludesContainFilePath((ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags>)object, (String)charSequence) || arraySet2 != null && arraySet2.contains(charSequence)) continue;
                        if (!OsConstants.S_ISDIR((int)structStat.st_mode) || (object = file.listFiles()) == null) break block7;
                        int n = ((Object)object).length;
                        for (int i = 0; i < n; ++i) {
                            linkedList.add(0, object[i]);
                        }
                    }
                    catch (ErrnoException errnoException) {
                        if (!Log.isLoggable("BackupXmlParserLogging", 2)) continue;
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Error scanning file ");
                        ((StringBuilder)charSequence).append(file);
                        ((StringBuilder)charSequence).append(" : ");
                        ((StringBuilder)charSequence).append((Object)errnoException);
                        Log.v("BackupXmlParserLogging", ((StringBuilder)charSequence).toString());
                        continue;
                    }
                    catch (IOException iOException) {
                        if (!Log.isLoggable("BackupXmlParserLogging", 2)) continue;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Error canonicalizing path of ");
                        ((StringBuilder)object).append(file);
                        Log.v("BackupXmlParserLogging", ((StringBuilder)object).toString());
                        continue;
                    }
                }
                FullBackup.backupToTar(string2, string3, null, string4, (String)charSequence, fullBackupDataOutput);
                continue;
                break;
            } while (true);
        }
    }

    Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    public abstract void onBackup(ParcelFileDescriptor var1, BackupDataOutput var2, ParcelFileDescriptor var3) throws IOException;

    public final IBinder onBind() {
        return this.mBinder;
    }

    public void onCreate() {
    }

    public void onCreate(UserHandle userHandle) {
        this.onCreate();
        this.mUser = userHandle;
    }

    public void onDestroy() {
    }

    public void onFullBackup(FullBackupDataOutput fullBackupDataOutput) throws IOException {
        ArraySet<FullBackup.BackupScheme.PathWithRequiredFlags> arraySet;
        Map<String, Set<FullBackup.BackupScheme.PathWithRequiredFlags>> map;
        Object object = FullBackup.getBackupScheme(this);
        if (!((FullBackup.BackupScheme)object).isFullBackupContentEnabled()) {
            return;
        }
        try {
            map = ((FullBackup.BackupScheme)object).maybeParseAndGetCanonicalIncludePaths();
            arraySet = ((FullBackup.BackupScheme)object).maybeParseAndGetCanonicalExcludePaths();
        }
        catch (IOException | XmlPullParserException throwable) {
            if (Log.isLoggable("BackupXmlParserLogging", 2)) {
                Log.v("BackupXmlParserLogging", "Exception trying to parse fullBackupContent xml file! Aborting full backup.", throwable);
            }
            return;
        }
        String string2 = this.getPackageName();
        object = this.getApplicationInfo();
        Object object2 = this.createCredentialProtectedStorageContext();
        String string3 = ((Context)object2).getDataDir().getCanonicalPath();
        String string4 = ((Context)object2).getFilesDir().getCanonicalPath();
        String string5 = ((Context)object2).getNoBackupFilesDir().getCanonicalPath();
        String string6 = ((Context)object2).getDatabasePath("foo").getParentFile().getCanonicalPath();
        String string7 = ((Context)object2).getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
        String string8 = ((Context)object2).getCacheDir().getCanonicalPath();
        String string9 = ((Context)object2).getCodeCacheDir().getCanonicalPath();
        Object object3 = this.createDeviceProtectedStorageContext();
        String string10 = ((Context)object3).getDataDir().getCanonicalPath();
        String string11 = ((Context)object3).getFilesDir().getCanonicalPath();
        String string12 = ((Context)object3).getNoBackupFilesDir().getCanonicalPath();
        String string13 = ((Context)object3).getDatabasePath("foo").getParentFile().getCanonicalPath();
        String string14 = ((Context)object3).getSharedPreferencesPath("foo").getParentFile().getCanonicalPath();
        object2 = ((Context)object3).getCacheDir().getCanonicalPath();
        object3 = ((Context)object3).getCodeCacheDir().getCanonicalPath();
        object = ((ApplicationInfo)object).nativeLibraryDir != null ? new File(((ApplicationInfo)object).nativeLibraryDir).getCanonicalPath() : null;
        ArraySet<String> arraySet2 = new ArraySet<String>();
        arraySet2.add(string4);
        arraySet2.add(string5);
        arraySet2.add(string6);
        arraySet2.add(string7);
        arraySet2.add(string8);
        arraySet2.add(string9);
        arraySet2.add(string11);
        arraySet2.add(string12);
        arraySet2.add(string13);
        arraySet2.add(string14);
        arraySet2.add((String)object2);
        arraySet2.add((String)object3);
        if (object != null) {
            arraySet2.add((String)object);
        }
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "r", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string3);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "d_r", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string10);
        arraySet2.remove(string4);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "f", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string4);
        arraySet2.remove(string11);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "d_f", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string11);
        arraySet2.remove(string6);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "db", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string6);
        arraySet2.remove(string13);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "d_db", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string13);
        arraySet2.remove(string7);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "sp", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string7);
        arraySet2.remove(string14);
        this.applyXmlFiltersAndDoFullBackupForDomain(string2, "d_sp", map, arraySet, arraySet2, fullBackupDataOutput);
        arraySet2.add(string14);
        if (Process.myUid() != 1000 && this.getExternalFilesDir(null) != null) {
            this.applyXmlFiltersAndDoFullBackupForDomain(string2, "ef", map, arraySet, arraySet2, fullBackupDataOutput);
        }
        return;
    }

    public void onQuotaExceeded(long l, long l2) {
    }

    public abstract void onRestore(BackupDataInput var1, int var2, ParcelFileDescriptor var3) throws IOException;

    public void onRestore(BackupDataInput backupDataInput, long l, ParcelFileDescriptor parcelFileDescriptor) throws IOException {
        this.onRestore(backupDataInput, (int)l, parcelFileDescriptor);
    }

    protected void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string2, String charSequence, long l2, long l3) throws IOException {
        String string3 = FullBackup.getBackupScheme(this).tokenToDirectoryPath(string2);
        if (string2.equals("ef")) {
            l2 = -1L;
        }
        if (string3 != null) {
            File file = new File(string3, (String)charSequence);
            string2 = file.getCanonicalPath();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append(File.separatorChar);
            if (string2.startsWith(((StringBuilder)charSequence).toString())) {
                this.onRestoreFile(parcelFileDescriptor, l, file, n, l2, l3);
                return;
            }
        }
        FullBackup.restoreFile(parcelFileDescriptor, l, n, l2, l3, null);
    }

    public void onRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long l, File file, int n, long l2, long l3) throws IOException {
        if (!this.isFileEligibleForRestore(file)) {
            file = null;
        }
        FullBackup.restoreFile(parcelFileDescriptor, l, n, l2, l3, file);
    }

    public void onRestoreFinished() {
    }

    private class BackupServiceBinder
    extends IBackupAgent.Stub {
        private static final String TAG = "BackupServiceBinder";

        private BackupServiceBinder() {
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doBackup(ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, ParcelFileDescriptor parcelFileDescriptor3, long l, IBackupCallback iBackupCallback, int n) throws RemoteException {
            block14 : {
                block15 : {
                    l2 = Binder.clearCallingIdentity();
                    backupDataOutput = new BackupDataOutput(parcelFileDescriptor2.getFileDescriptor(), l, n);
                    var11_21 = BackupAgent.this;
                    var11_21.onBackup(parcelFileDescriptor, backupDataOutput, parcelFileDescriptor3);
                    BackupAgent.access$100(BackupAgent.this);
                    Binder.restoreCallingIdentity(l2);
                    try {
                        iBackupCallback.operationComplete(0L);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                    if (Binder.getCallingPid() == Process.myPid()) return;
                    IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
                    IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor2);
                    IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor3);
                    return;
                    catch (Throwable throwable) {
                        break block14;
                    }
                    catch (RuntimeException runtimeException) {
                        break block15;
                    }
                    catch (IOException iOException) {
                        ** GOTO lbl43
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    catch (RuntimeException runtimeException) {
                        // empty catch block
                    }
                }
                try {
                    var11_21 = new StringBuilder();
                    var11_21.append("onBackup (");
                    var11_21.append(BackupAgent.this.getClass().getName());
                    var11_21.append(") threw");
                    Log.d("BackupServiceBinder", var11_21.toString(), (Throwable)var10_17);
                    throw var10_17;
                    catch (IOException iOException) {
                        // empty catch block
                    }
lbl43: // 2 sources:
                    var11_21 = new StringBuilder();
                    var11_21.append("onBackup (");
                    var11_21.append(BackupAgent.this.getClass().getName());
                    var11_21.append(") threw");
                    Log.d("BackupServiceBinder", var11_21.toString(), (Throwable)throwable);
                    var11_21 = new RuntimeException((Throwable)throwable);
                    throw var11_21;
                }
                catch (Throwable var10_20) {
                    // empty catch block
                }
            }
            BackupAgent.access$100(BackupAgent.this);
            Binder.restoreCallingIdentity(l2);
            try {
                iBackupCallback.operationComplete(-1L);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            if (Binder.getCallingPid() == Process.myPid()) throw var10_15;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor2);
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor3);
            throw var10_15;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doFullBackup(ParcelFileDescriptor var1_1, long var2_2, int var4_3, IBackupManager var5_4, int var6_7) {
            block17 : {
                var7_8 = Binder.clearCallingIdentity();
                BackupAgent.access$100(BackupAgent.this);
                var9_9 = BackupAgent.this;
                var10_11 = new FullBackupDataOutput(var1_1, var2_2, var6_7);
                var9_9.onFullBackup((FullBackupDataOutput)var10_11);
                BackupAgent.access$100(BackupAgent.this);
                try {
                    var10_11 = new FileOutputStream(var1_1.getFileDescriptor());
                    var10_11.write(new byte[4]);
                }
                catch (IOException var10_12) {
                    Log.e("BackupServiceBinder", "Unable to finalize backup stream!");
                }
                Binder.restoreCallingIdentity(var7_8);
                try {
                    var5_4.opCompleteForUser(BackupAgent.access$200(BackupAgent.this), var4_3, 0L);
                }
                catch (RemoteException var5_5) {
                    // empty catch block
                }
                if (Binder.getCallingPid() == Process.myPid()) return;
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                return;
                catch (Throwable var10_16) {
                    break block17;
                }
                catch (RuntimeException var10_18) {
                    // empty catch block
                    ** GOTO lbl39
                }
                {
                    block18 : {
                        catch (IOException var10_19) {
                            // empty catch block
                            break block18;
                        }
                        catch (Throwable var10_13) {
                            break block17;
                        }
                        catch (RuntimeException var10_14) {
                        }
                        catch (IOException var10_15) {
                            break block18;
                        }
lbl39: // 2 sources:
                        var9_9 = new StringBuilder();
                        var9_9.append("onFullBackup (");
                        var9_9.append(BackupAgent.this.getClass().getName());
                        var9_9.append(") threw");
                        Log.d("BackupServiceBinder", var9_9.toString(), (Throwable)var10_11);
                        throw var10_11;
                    }
                    var9_9 = new StringBuilder();
                    var9_9.append("onFullBackup (");
                    var9_9.append(BackupAgent.this.getClass().getName());
                    var9_9.append(") threw");
                    Log.d("BackupServiceBinder", var9_9.toString(), (Throwable)var10_20);
                    var9_9 = new RuntimeException((Throwable)var10_20);
                    throw var9_9;
                }
            }
            BackupAgent.access$100(BackupAgent.this);
            try {
                var9_9 = new FileOutputStream(var1_1.getFileDescriptor());
                var9_9.write(new byte[4]);
            }
            catch (IOException var9_10) {
                Log.e("BackupServiceBinder", "Unable to finalize backup stream!");
            }
            Binder.restoreCallingIdentity(var7_8);
            try {
                var5_4.opCompleteForUser(BackupAgent.access$200(BackupAgent.this), var4_3, 0L);
            }
            catch (RemoteException var5_6) {
                // empty catch block
            }
            if (Binder.getCallingPid() == Process.myPid()) throw var10_17;
            IoUtils.closeQuietly((AutoCloseable)var1_1);
            throw var10_17;
        }

        /*
         * Exception decompiling
         */
        @Override
        public void doMeasureFullBackup(long var1_1, int var3_2, IBackupManager var4_3, int var5_6) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [6[CATCHBLOCK]], but top level block is 2[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doQuotaExceeded(long l, long l2, IBackupCallback iBackupCallback) {
            Throwable throwable2222;
            long l3 = Binder.clearCallingIdentity();
            BackupAgent.this.onQuotaExceeded(l, l2);
            BackupAgent.this.waitForSharedPrefs();
            Binder.restoreCallingIdentity(l3);
            try {
                iBackupCallback.operationComplete(0L);
                return;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (Exception exception) {}
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onQuotaExceeded(");
                    stringBuilder.append(BackupAgent.this.getClass().getName());
                    stringBuilder.append(") threw");
                    Log.d(TAG, stringBuilder.toString(), exception);
                    throw exception;
                }
            }
            BackupAgent.this.waitForSharedPrefs();
            Binder.restoreCallingIdentity(l3);
            try {
                iBackupCallback.operationComplete(-1L);
                throw throwable2222;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            throw throwable2222;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doRestore(ParcelFileDescriptor var1_1, long var2_2, ParcelFileDescriptor var4_3, int var5_4, IBackupManager var6_5) throws RemoteException {
            block13 : {
                var7_8 = Binder.clearCallingIdentity();
                BackupAgent.access$100(BackupAgent.this);
                var9_9 = new BackupDataInput(var1_1.getFileDescriptor());
                var10_10 = BackupAgent.this;
                var10_10.onRestore((BackupDataInput)var9_9, var2_2, var4_3);
                BackupAgent.this.reloadSharedPreferences();
                Binder.restoreCallingIdentity(var7_8);
                try {
                    var6_5.opCompleteForUser(BackupAgent.access$200(BackupAgent.this), var5_4, 0L);
                }
                catch (RemoteException var6_6) {
                    // empty catch block
                }
                if (Binder.getCallingPid() == Process.myPid()) return;
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                IoUtils.closeQuietly((AutoCloseable)var4_3);
                return;
                catch (Throwable var10_14) {
                    break block13;
                }
                catch (RuntimeException var10_16) {
                    // empty catch block
                    ** GOTO lbl33
                }
                {
                    block14 : {
                        catch (IOException var10_17) {
                            // empty catch block
                            break block14;
                        }
                        catch (Throwable var10_11) {
                            break block13;
                        }
                        catch (RuntimeException var10_12) {
                        }
                        catch (IOException var10_13) {
                            break block14;
                        }
lbl33: // 2 sources:
                        var9_9 = new StringBuilder();
                        var9_9.append("onRestore (");
                        var9_9.append(BackupAgent.this.getClass().getName());
                        var9_9.append(") threw");
                        Log.d("BackupServiceBinder", var9_9.toString(), (Throwable)var10_10);
                        throw var10_10;
                    }
                    var9_9 = new StringBuilder();
                    var9_9.append("onRestore (");
                    var9_9.append(BackupAgent.this.getClass().getName());
                    var9_9.append(") threw");
                    Log.d("BackupServiceBinder", var9_9.toString(), (Throwable)var10_18);
                    var9_9 = new RuntimeException((Throwable)var10_18);
                    throw var9_9;
                }
            }
            BackupAgent.this.reloadSharedPreferences();
            Binder.restoreCallingIdentity(var7_8);
            try {
                var6_5.opCompleteForUser(BackupAgent.access$200(BackupAgent.this), var5_4, 0L);
            }
            catch (RemoteException var6_7) {
                // empty catch block
            }
            if (Binder.getCallingPid() == Process.myPid()) throw var10_15;
            IoUtils.closeQuietly((AutoCloseable)var1_1);
            IoUtils.closeQuietly((AutoCloseable)var4_3);
            throw var10_15;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doRestoreFile(ParcelFileDescriptor parcelFileDescriptor, long l, int n, String string2, String object, long l2, long l3, int n2, IBackupManager iBackupManager) throws RemoteException {
            Throwable throwable2222;
            long l4 = Binder.clearCallingIdentity();
            BackupAgent.this.onRestoreFile(parcelFileDescriptor, l, n, string2, (String)object, l2, l3);
            BackupAgent.this.waitForSharedPrefs();
            BackupAgent.this.reloadSharedPreferences();
            Binder.restoreCallingIdentity(l4);
            try {
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), n2, 0L);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            if (Binder.getCallingPid() == Process.myPid()) return;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (IOException iOException) {}
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onRestoreFile (");
                    ((StringBuilder)object).append(BackupAgent.this.getClass().getName());
                    ((StringBuilder)object).append(") threw");
                    Log.d(TAG, ((StringBuilder)object).toString(), iOException);
                    object = new RuntimeException(iOException);
                    throw object;
                }
            }
            BackupAgent.this.waitForSharedPrefs();
            BackupAgent.this.reloadSharedPreferences();
            Binder.restoreCallingIdentity(l4);
            try {
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), n2, 0L);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            if (Binder.getCallingPid() == Process.myPid()) throw throwable2222;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2222;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void doRestoreFinished(int n, IBackupManager iBackupManager) {
            Throwable throwable2222;
            long l = Binder.clearCallingIdentity();
            BackupAgent.this.onRestoreFinished();
            BackupAgent.this.waitForSharedPrefs();
            Binder.restoreCallingIdentity(l);
            try {
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), n, 0L);
                return;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
            {
                catch (Throwable throwable2222) {
                }
                catch (Exception exception) {}
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onRestoreFinished (");
                    stringBuilder.append(BackupAgent.this.getClass().getName());
                    stringBuilder.append(") threw");
                    Log.d(TAG, stringBuilder.toString(), exception);
                    throw exception;
                }
            }
            BackupAgent.this.waitForSharedPrefs();
            Binder.restoreCallingIdentity(l);
            try {
                iBackupManager.opCompleteForUser(BackupAgent.this.getBackupUserId(), n, 0L);
                throw throwable2222;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            throw throwable2222;
        }

        @Override
        public void fail(String string2) {
            BackupAgent.this.getHandler().post(new FailRunnable(string2));
        }
    }

    static class FailRunnable
    implements Runnable {
        private String mMessage;

        FailRunnable(String string2) {
            this.mMessage = string2;
        }

        @Override
        public void run() {
            throw new IllegalStateException(this.mMessage);
        }
    }

    class SharedPrefsSynchronizer
    implements Runnable {
        public final CountDownLatch mLatch = new CountDownLatch(1);

        SharedPrefsSynchronizer() {
        }

        @Override
        public void run() {
            QueuedWork.waitToFinish();
            this.mLatch.countDown();
        }
    }

}

