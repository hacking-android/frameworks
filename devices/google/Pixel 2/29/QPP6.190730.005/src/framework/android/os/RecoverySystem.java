/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Streams
 */
package android.os;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.FileUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IRecoverySystem;
import android.os.IRecoverySystemProgressListener;
import android.os.IVold;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.VintfObject;
import android.provider.Settings;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import libcore.io.Streams;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;

public class RecoverySystem {
    private static final String ACTION_EUICC_FACTORY_RESET = "com.android.internal.action.EUICC_FACTORY_RESET";
    public static final File BLOCK_MAP_FILE;
    private static final long DEFAULT_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 30000L;
    private static final File DEFAULT_KEYSTORE;
    private static final String LAST_INSTALL_PATH = "last_install";
    private static final String LAST_PREFIX = "last_";
    private static final File LOG_FILE;
    private static final int LOG_FILE_MAX_LENGTH = 65536;
    private static final long MAX_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 60000L;
    private static final long MIN_EUICC_FACTORY_RESET_TIMEOUT_MILLIS = 5000L;
    private static final String PACKAGE_NAME_WIPING_EUICC_DATA_CALLBACK = "android";
    private static final long PUBLISH_PROGRESS_INTERVAL_MS = 500L;
    private static final File RECOVERY_DIR;
    private static final String TAG = "RecoverySystem";
    public static final File UNCRYPT_PACKAGE_FILE;
    public static final File UNCRYPT_STATUS_FILE;
    private static final Object sRequestLock;
    private final IRecoverySystem mService;

    private static /* synthetic */ void $closeResource(Throwable throwable, AutoCloseable autoCloseable) {
        if (throwable != null) {
            try {
                autoCloseable.close();
            }
            catch (Throwable throwable2) {
                throwable.addSuppressed(throwable2);
            }
        } else {
            autoCloseable.close();
        }
    }

    static {
        DEFAULT_KEYSTORE = new File("/system/etc/security/otacerts.zip");
        RECOVERY_DIR = new File("/cache/recovery");
        LOG_FILE = new File(RECOVERY_DIR, "log");
        BLOCK_MAP_FILE = new File(RECOVERY_DIR, "block.map");
        UNCRYPT_PACKAGE_FILE = new File(RECOVERY_DIR, "uncrypt_file");
        UNCRYPT_STATUS_FILE = new File(RECOVERY_DIR, "uncrypt_status");
        sRequestLock = new Object();
    }

    public RecoverySystem() {
        this.mService = null;
    }

    public RecoverySystem(IRecoverySystem iRecoverySystem) {
        this.mService = iRecoverySystem;
    }

    private static void bootCommand(Context context, String ... arrstring) throws IOException {
        LOG_FILE.delete();
        StringBuilder stringBuilder = new StringBuilder();
        for (String string2 : arrstring) {
            if (TextUtils.isEmpty(string2)) continue;
            stringBuilder.append(string2);
            stringBuilder.append("\n");
        }
        ((RecoverySystem)context.getSystemService("recovery")).rebootRecoveryWithCommand(stringBuilder.toString());
        throw new IOException("Reboot failed (no permissions?)");
    }

    @SystemApi
    public static void cancelScheduledUpdate(Context context) throws IOException {
        if (((RecoverySystem)context.getSystemService("recovery")).clearBcb()) {
            return;
        }
        throw new IOException("cancel scheduled update failed");
    }

    private boolean clearBcb() {
        try {
            boolean bl = this.mService.clearBcb();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static HashSet<X509Certificate> getTrustedCerts(File object) throws IOException, GeneralSecurityException {
        CertificateFactory certificateFactory;
        HashSet<X509Certificate> hashSet = new HashSet<X509Certificate>();
        Object object2 = object;
        if (object == null) {
            object2 = DEFAULT_KEYSTORE;
        }
        object = new ZipFile((File)object2);
        try {
            certificateFactory = CertificateFactory.getInstance("X.509");
            Enumeration<? extends ZipEntry> enumeration = ((ZipFile)object).entries();
            while (enumeration.hasMoreElements()) {
                object2 = ((ZipFile)object).getInputStream(enumeration.nextElement());
            }
        }
        catch (Throwable throwable) {
            ((ZipFile)object).close();
            throw throwable;
        }
        {
            hashSet.add((X509Certificate)certificateFactory.generateCertificate((InputStream)object2));
            {
                catch (Throwable throwable) {
                    ((InputStream)object2).close();
                    throw throwable;
                }
            }
            ((InputStream)object2).close();
            continue;
        }
        ((ZipFile)object).close();
        return hashSet;
    }

    public static String handleAftermath(Context object) {
        CharSequence charSequence;
        Object object2 = null;
        object = null;
        try {
            charSequence = FileUtils.readTextFile(LOG_FILE, -65536, "...\n");
            object = charSequence;
        }
        catch (IOException iOException) {
            Log.e(TAG, "Error reading recovery log", iOException);
            object = object2;
        }
        catch (FileNotFoundException fileNotFoundException) {
            Log.i(TAG, "No recovery log file");
        }
        boolean bl = BLOCK_MAP_FILE.exists();
        if (!bl && UNCRYPT_PACKAGE_FILE.exists()) {
            object2 = null;
            try {
                charSequence = FileUtils.readTextFile(UNCRYPT_PACKAGE_FILE, 0, null);
                object2 = charSequence;
            }
            catch (IOException iOException) {
                Log.e(TAG, "Error reading uncrypt file", iOException);
            }
            if (object2 != null && ((String)object2).startsWith("/data")) {
                if (UNCRYPT_PACKAGE_FILE.delete()) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Deleted: ");
                    ((StringBuilder)charSequence).append((String)object2);
                    Log.i(TAG, ((StringBuilder)charSequence).toString());
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Can't delete: ");
                    ((StringBuilder)charSequence).append((String)object2);
                    Log.e(TAG, ((StringBuilder)charSequence).toString());
                }
            }
        }
        object2 = RECOVERY_DIR.list();
        for (int i = 0; object2 != null && i < ((String[])object2).length; ++i) {
            if (object2[i].startsWith(LAST_PREFIX) || ((String)object2[i]).equals(LAST_INSTALL_PATH) || bl && ((String)object2[i]).equals(BLOCK_MAP_FILE.getName()) || bl && ((String)object2[i]).equals(UNCRYPT_PACKAGE_FILE.getName())) continue;
            RecoverySystem.recursiveDelete(new File(RECOVERY_DIR, (String)object2[i]));
        }
        return object;
    }

    public static void installPackage(Context context, File file) throws IOException {
        RecoverySystem.installPackage(context, file, false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public static void installPackage(Context object, File object2, boolean bl) throws IOException {
        Object object3 = sRequestLock;
        synchronized (object3) {
            CharSequence charSequence;
            Object object4;
            boolean bl2;
            block15 : {
                LOG_FILE.delete();
                UNCRYPT_PACKAGE_FILE.delete();
                charSequence = ((File)object2).getCanonicalPath();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("!!! REBOOTING TO INSTALL ");
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append(" !!!");
                Log.w(TAG, ((StringBuilder)object2).toString());
                bl2 = ((String)charSequence).endsWith("_s.zip");
                object2 = charSequence;
                if (((String)charSequence).startsWith("/data/")) {
                    if (bl) {
                        if (!BLOCK_MAP_FILE.exists()) {
                            Log.e(TAG, "Package claimed to have been processed but failed to find the block map file.");
                            object = new IOException("Failed to find block map file");
                            throw object;
                        }
                    } else {
                        block14 : {
                            object2 = new FileWriter(UNCRYPT_PACKAGE_FILE);
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append((String)charSequence);
                            ((StringBuilder)object4).append("\n");
                            ((Writer)object2).write(((StringBuilder)object4).toString());
                            if (UNCRYPT_PACKAGE_FILE.setReadable(true, false) && UNCRYPT_PACKAGE_FILE.setWritable(true, false)) break block14;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Error setting permission for ");
                            ((StringBuilder)object2).append(UNCRYPT_PACKAGE_FILE);
                            Log.e(TAG, ((StringBuilder)object2).toString());
                        }
                        BLOCK_MAP_FILE.delete();
                    }
                    object2 = "@/cache/recovery/block.map";
                    break block15;
                    finally {
                        ((OutputStreamWriter)object2).close();
                    }
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("--update_package=");
            ((StringBuilder)charSequence).append((String)object2);
            ((StringBuilder)charSequence).append("\n");
            object2 = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("--locale=");
            ((StringBuilder)charSequence).append(Locale.getDefault().toLanguageTag());
            ((StringBuilder)charSequence).append("\n");
            charSequence = ((StringBuilder)charSequence).toString();
            object4 = new StringBuilder();
            ((StringBuilder)object4).append((String)object2);
            ((StringBuilder)object4).append((String)charSequence);
            charSequence = ((StringBuilder)object4).toString();
            object2 = charSequence;
            if (bl2) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append((String)charSequence);
                ((StringBuilder)object2).append("--security\n");
                object2 = ((StringBuilder)object2).toString();
            }
            if (!((RecoverySystem)((Context)object).getSystemService("recovery")).setupBcb((String)object2)) {
                object = new IOException("Setup BCB failed");
                throw object;
            }
            object4 = (PowerManager)((Context)object).getSystemService("power");
            charSequence = "recovery-update";
            object2 = charSequence;
            if (((Context)object).getPackageManager().hasSystemFeature("android.software.leanback")) {
                object2 = charSequence;
                if (((WindowManager)((Context)object).getSystemService("window")).getDefaultDisplay().getState() != 2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("recovery-update");
                    ((StringBuilder)object).append(",quiescent");
                    object2 = ((StringBuilder)object).toString();
                }
            }
            ((PowerManager)object4).reboot((String)object2);
            object = new IOException("Reboot failed (no permissions?)");
            throw object;
        }
    }

    @SystemApi
    public static void processPackage(Context context, File file, ProgressListener progressListener) throws IOException {
        RecoverySystem.processPackage(context, file, progressListener, null);
    }

    @SystemApi
    public static void processPackage(Context context, File object, final ProgressListener progressListener, Handler handler) throws IOException {
        String string2 = ((File)object).getCanonicalPath();
        if (!string2.startsWith("/data/")) {
            return;
        }
        RecoverySystem recoverySystem = (RecoverySystem)context.getSystemService("recovery");
        object = null;
        if (progressListener != null) {
            if (handler == null) {
                handler = new Handler(context.getMainLooper());
            }
            object = new IRecoverySystemProgressListener.Stub(){
                int lastProgress = 0;
                long lastPublishTime = System.currentTimeMillis();

                @Override
                public void onProgress(final int n) {
                    final long l = System.currentTimeMillis();
                    Handler.this.post(new Runnable(){

                        @Override
                        public void run() {
                            if (n > lastProgress && l - lastPublishTime > 500L) {
                                2 var1_1 = this;
                                var1_1.lastProgress = n;
                                var1_1.lastPublishTime = l;
                                var1_1.progressListener.onProgress(n);
                            }
                        }
                    });
                }

            };
        }
        if (recoverySystem.uncrypt(string2, (IRecoverySystemProgressListener)object)) {
            return;
        }
        throw new IOException("process package failed");
    }

    private static boolean readAndVerifyPackageCompatibilityEntry(File object) throws IOException {
        ZipEntry zipEntry;
        block5 : {
            object = new ZipFile((File)object);
            try {
                zipEntry = ((ZipFile)object).getEntry("compatibility.zip");
                if (zipEntry != null) break block5;
            }
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    RecoverySystem.$closeResource(throwable, (AutoCloseable)object);
                    throw throwable2;
                }
            }
            RecoverySystem.$closeResource(null, (AutoCloseable)object);
            return true;
        }
        boolean bl = RecoverySystem.verifyPackageCompatibility(((ZipFile)object).getInputStream(zipEntry));
        RecoverySystem.$closeResource(null, (AutoCloseable)object);
        return bl;
    }

    public static void rebootPromptAndWipeUserData(Context context, String charSequence) throws IOException {
        Object object;
        IVold iVold;
        boolean bl;
        block9 : {
            boolean bl2;
            block8 : {
                bl = false;
                bl2 = false;
                object = null;
                iVold = IVold.Stub.asInterface(ServiceManager.checkService("vold"));
                if (iVold == null) break block8;
                object = iVold;
                bl = bl2 = iVold.needsCheckpoint();
                break block9;
            }
            object = iVold;
            try {
                Log.w(TAG, "Failed to get vold");
                bl = bl2;
            }
            catch (Exception exception) {
                Log.w(TAG, "Failed to check for checkpointing");
            }
        }
        object = iVold;
        if (bl) {
            try {
                object.abortChanges("rescueparty", false);
                Log.i(TAG, "Rescue Party requested wipe. Aborting update");
            }
            catch (Exception exception) {
                Log.i(TAG, "Rescue Party requested wipe. Rebooting instead.");
                ((PowerManager)context.getSystemService("power")).reboot("rescueparty");
            }
            return;
        }
        object = null;
        if (!TextUtils.isEmpty(charSequence)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("--reason=");
            ((StringBuilder)object).append(RecoverySystem.sanitizeArg((String)charSequence));
            object = ((StringBuilder)object).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--locale=");
        ((StringBuilder)charSequence).append(Locale.getDefault().toString());
        RecoverySystem.bootCommand(context, new String[]{null, "--prompt_and_wipe_data", object, ((StringBuilder)charSequence).toString()});
    }

    private void rebootRecoveryWithCommand(String string2) {
        try {
            this.mService.rebootRecoveryWithCommand(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public static void rebootWipeAb(Context context, File object, String charSequence) throws IOException {
        CharSequence charSequence2 = null;
        if (!TextUtils.isEmpty(charSequence)) {
            charSequence2 = new StringBuilder();
            charSequence2.append("--reason=");
            charSequence2.append(RecoverySystem.sanitizeArg((String)charSequence));
            charSequence2 = charSequence2.toString();
        }
        object = ((File)object).getCanonicalPath();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--wipe_package=");
        ((StringBuilder)charSequence).append((String)object);
        object = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--locale=");
        ((StringBuilder)charSequence).append(Locale.getDefault().toLanguageTag());
        RecoverySystem.bootCommand(context, new String[]{"--wipe_ab", object, charSequence2, ((StringBuilder)charSequence).toString()});
    }

    public static void rebootWipeCache(Context context) throws IOException {
        RecoverySystem.rebootWipeCache(context, context.getPackageName());
    }

    public static void rebootWipeCache(Context context, String charSequence) throws IOException {
        CharSequence charSequence2 = null;
        if (!TextUtils.isEmpty(charSequence)) {
            charSequence2 = new StringBuilder();
            charSequence2.append("--reason=");
            charSequence2.append(RecoverySystem.sanitizeArg((String)charSequence));
            charSequence2 = charSequence2.toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--locale=");
        ((StringBuilder)charSequence).append(Locale.getDefault().toLanguageTag());
        RecoverySystem.bootCommand(context, new String[]{"--wipe_cache", charSequence2, ((StringBuilder)charSequence).toString()});
    }

    public static void rebootWipeUserData(Context context) throws IOException {
        RecoverySystem.rebootWipeUserData(context, false, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, String string2) throws IOException {
        RecoverySystem.rebootWipeUserData(context, false, string2, false, false);
    }

    public static void rebootWipeUserData(Context context, boolean bl) throws IOException {
        RecoverySystem.rebootWipeUserData(context, bl, context.getPackageName(), false, false);
    }

    public static void rebootWipeUserData(Context context, boolean bl, String string2, boolean bl2) throws IOException {
        RecoverySystem.rebootWipeUserData(context, bl, string2, bl2, false);
    }

    public static void rebootWipeUserData(Context context, boolean bl, String object, boolean bl2, boolean bl3) throws IOException {
        Object object2 = (UserManager)context.getSystemService("user");
        if (!bl2 && ((UserManager)object2).hasUserRestriction("no_factory_reset")) {
            throw new SecurityException("Wiping data is not allowed for this user.");
        }
        Object object3 = new ConditionVariable();
        object2 = new Intent("android.intent.action.MASTER_CLEAR_NOTIFICATION");
        ((Intent)object2).addFlags(285212672);
        context.sendOrderedBroadcastAsUser((Intent)object2, UserHandle.SYSTEM, "android.permission.MASTER_CLEAR", new BroadcastReceiver(){

            @Override
            public void onReceive(Context context, Intent intent) {
                ConditionVariable.this.open();
            }
        }, null, 0, null, null);
        ((ConditionVariable)object3).block();
        if (bl3) {
            RecoverySystem.wipeEuiccData(context, PACKAGE_NAME_WIPING_EUICC_DATA_CALLBACK);
        }
        object2 = null;
        if (bl) {
            object2 = "--shutdown_after";
        }
        object3 = null;
        if (!TextUtils.isEmpty((CharSequence)object)) {
            object3 = DateFormat.format((CharSequence)"yyyy-MM-ddTHH:mm:ssZ", System.currentTimeMillis()).toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("--reason=");
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append((String)object);
            stringBuilder2.append(",");
            stringBuilder2.append((String)object3);
            stringBuilder.append(RecoverySystem.sanitizeArg(stringBuilder2.toString()));
            object = stringBuilder.toString();
        } else {
            object = object3;
        }
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("--locale=");
        ((StringBuilder)object3).append(Locale.getDefault().toLanguageTag());
        RecoverySystem.bootCommand(context, new String[]{object2, "--wipe_data", object, ((StringBuilder)object3).toString()});
    }

    private static void recursiveDelete(File file) {
        Object object;
        if (file.isDirectory()) {
            object = file.list();
            for (int i = 0; object != null && i < ((String[])object).length; ++i) {
                RecoverySystem.recursiveDelete(new File(file, (String)object[i]));
            }
        }
        if (!file.delete()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Can't delete: ");
            ((StringBuilder)object).append(file);
            Log.e(TAG, ((StringBuilder)object).toString());
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("Deleted: ");
            ((StringBuilder)object).append(file);
            Log.i(TAG, ((StringBuilder)object).toString());
        }
    }

    private static String sanitizeArg(String string2) {
        return string2.replace('\u0000', '?').replace('\n', '?');
    }

    @SystemApi
    public static void scheduleUpdateOnBoot(Context context, File object) throws IOException {
        CharSequence charSequence = ((File)object).getCanonicalPath();
        boolean bl = ((String)charSequence).endsWith("_s.zip");
        object = charSequence;
        if (((String)charSequence).startsWith("/data/")) {
            object = "@/cache/recovery/block.map";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--update_package=");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("\n");
        object = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("--locale=");
        ((StringBuilder)charSequence).append(Locale.getDefault().toLanguageTag());
        ((StringBuilder)charSequence).append("\n");
        String string2 = ((StringBuilder)charSequence).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(string2);
        charSequence = ((StringBuilder)charSequence).toString();
        object = charSequence;
        if (bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("--security\n");
            object = ((StringBuilder)object).toString();
        }
        if (((RecoverySystem)context.getSystemService("recovery")).setupBcb((String)object)) {
            return;
        }
        throw new IOException("schedule update on boot failed");
    }

    private boolean setupBcb(String string2) {
        try {
            boolean bl = this.mService.setupBcb(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    private boolean uncrypt(String string2, IRecoverySystemProgressListener iRecoverySystemProgressListener) {
        try {
            boolean bl = this.mService.uncrypt(string2, iRecoverySystemProgressListener);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void verifyPackage(File var0, final ProgressListener var1_5, File var2_6) throws IOException, GeneralSecurityException {
        block18 : {
            var3_7 = var0.length();
            var5_8 = new RandomAccessFile((File)var0, "r");
            var6_9 = System.currentTimeMillis();
            if (var1_5 != null) {
                var1_5.onProgress(0);
            }
            var5_8.seek(var3_7 - 6L);
            var8_10 /* !! */  = new byte[6];
            var5_8.readFully(var8_10 /* !! */ );
            if (var8_10 /* !! */ [2] != -1 || var8_10 /* !! */ [3] != -1) ** GOTO lbl25
            var9_11 = var8_10 /* !! */ [4];
            var10_12 = (var8_10 /* !! */ [5] & 255) << 8 | var9_11 & 255;
            var11_13 = var8_10 /* !! */ [0] & 255 | (var8_10 /* !! */ [1] & 255) << 8;
            var12_14 /* !! */  = new byte[var10_12 + 22];
            var13_15 = var10_12 + 22;
            var5_8.seek(var3_7 - var13_15);
            var5_8.readFully(var12_14 /* !! */ );
            if (var12_14 /* !! */ [0] != 80 || var12_14 /* !! */ [1] != 75 || var12_14 /* !! */ [2] != 5 || var12_14 /* !! */ [3] != 6) ** GOTO lbl-1000
            var9_11 = 4;
            ** GOTO lbl30
lbl-1000: // 1 sources:
            {
                block21 : {
                    var0 = new SignatureException("no signature in file (bad footer)");
                    throw var0;
lbl25: // 1 sources:
                    var0 = new SignatureException("no signature in file (no footer)");
                    throw var0;
                    catch (Throwable var0_3) {
                        // empty catch block
                    }
                    break block18;
lbl30: // 1 sources:
                    do {
                        if (var9_11 >= var12_14 /* !! */ .length - 3) break;
                        if (var12_14 /* !! */ [var9_11] == 80 && var12_14 /* !! */ [var9_11 + 1] == 75 && var12_14 /* !! */ [var9_11 + 2] == 5 && var12_14 /* !! */ [var9_11 + 3] == 6) {
                            var0 = new SignatureException("EOCD marker found after start of EOCD");
                            throw var0;
                        }
                        ++var9_11;
                    } while (true);
                    var8_10 /* !! */  = new ByteArrayInputStream(var12_14 /* !! */ , var10_12 + 22 - var11_13, var11_13);
                    var15_16 = new PKCS7((InputStream)var8_10 /* !! */ );
                    var8_10 /* !! */  = var15_16.getCertificates();
                    if (var8_10 /* !! */  == null || var8_10 /* !! */ .length == 0) break block19;
                    var16_17 = var8_10 /* !! */ [0].getPublicKey();
                    var8_10 /* !! */  = var15_16.getSignerInfos();
                    if (var8_10 /* !! */  == null || var8_10 /* !! */ .length == 0) break block20;
                    var12_14 /* !! */  = (byte[])var8_10 /* !! */ [0];
                    if (var2_6 /* !! */  == null) {
                        var2_6 /* !! */  = RecoverySystem.DEFAULT_KEYSTORE;
                    }
                    var17_18 = RecoverySystem.getTrustedCerts((File)var2_6 /* !! */ ).iterator();
                    var2_6 /* !! */  = var8_10 /* !! */ ;
                    while (var17_18.hasNext()) {
                        if (!var17_18.next().getPublicKey().equals(var16_17)) continue;
                        var9_11 = 1;
                        break block21;
                    }
                    var9_11 = 0;
                }
                if (var9_11 == 0) break block22;
                var5_8.seek(0L);
                var2_6 /* !! */  = new InputStream(){
                    int lastPercent;
                    long lastPublishTime;
                    long soFar;
                    long toRead;
                    {
                        this.toRead = val$fileLen - (long)var10_12 - 2L;
                        this.soFar = 0L;
                        this.lastPercent = 0;
                        this.lastPublishTime = var6_9;
                    }

                    @Override
                    public int read() throws IOException {
                        throw new UnsupportedOperationException();
                    }

                    @Override
                    public int read(byte[] arrby, int n, int n2) throws IOException {
                        if (this.soFar >= this.toRead) {
                            return -1;
                        }
                        if (Thread.currentThread().isInterrupted()) {
                            return -1;
                        }
                        long l = n2;
                        long l2 = this.soFar;
                        long l3 = this.toRead;
                        if (l + l2 > l3) {
                            n2 = (int)(l3 - l2);
                        }
                        n2 = var5_8.read(arrby, n, n2);
                        this.soFar += (long)n2;
                        if (var1_5 != null) {
                            l3 = System.currentTimeMillis();
                            n = (int)(this.soFar * 100L / this.toRead);
                            if (n > this.lastPercent && l3 - this.lastPublishTime > 500L) {
                                this.lastPercent = n;
                                this.lastPublishTime = l3;
                                var1_5.onProgress(this.lastPercent);
                            }
                        }
                        return n2;
                    }
                };
                var2_6 /* !! */  = var15_16.verify((SignerInfo)var12_14 /* !! */ , (InputStream)var2_6 /* !! */ );
                var18_19 = Thread.interrupted();
                if (var1_5 == null) ** break block23
                var1_5.onProgress(100);
            }
lbl66: // 2 sources:
            catch (Throwable var0_2) {}
            {
                block19 : {
                    block20 : {
                        block22 : {
                            if (!var18_19) {
                                if (var2_6 /* !! */  != null) {
                                    var5_8.close();
                                    if (RecoverySystem.readAndVerifyPackageCompatibilityEntry((File)var0) == false) throw new SignatureException("package compatibility verification failed");
                                    return;
                                }
                                ** try [egrp 7[TRYBLOCK] [15 : 506->601)] { 
lbl74: // 1 sources:
                                var0 = new SignatureException("signature digest verification failed");
                                throw var0;
                            }
                            var0 = new SignatureException("verification was interrupted");
                            throw var0;
                        }
                        var0 = new SignatureException("signature doesn't match any trusted key");
                        throw var0;
                    }
                    var0 = new SignatureException("signature contains no signedData");
                    throw var0;
                }
                var0 = new SignatureException("signature contains no certificates");
                throw var0;
                catch (Throwable var0_1) {}
            }
        }
        var5_8.close();
        throw var0_4;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public static boolean verifyPackageCompatibility(File object) throws IOException {
        boolean bl;
        object = new FileInputStream((File)object);
        try {
            bl = RecoverySystem.verifyPackageCompatibility((InputStream)object);
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                RecoverySystem.$closeResource(throwable, (AutoCloseable)object);
                throw throwable2;
            }
        }
        RecoverySystem.$closeResource(null, (AutoCloseable)object);
        return bl;
    }

    @UnsupportedAppUsage
    private static boolean verifyPackageCompatibility(InputStream object) throws IOException {
        byte[] arrby;
        ArrayList<String> arrayList = new ArrayList<String>();
        object = new ZipInputStream((InputStream)object);
        while ((arrby = ((ZipInputStream)object).getNextEntry()) != null) {
            long l = arrby.getSize();
            if (l <= Integer.MAX_VALUE && l >= 0L) {
                arrby = new byte[(int)l];
                Streams.readFully((InputStream)object, (byte[])arrby);
                arrayList.add(new String(arrby, StandardCharsets.UTF_8));
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("invalid entry size (");
            ((StringBuilder)object).append(l);
            ((StringBuilder)object).append(") in the compatibility file");
            throw new IOException(((StringBuilder)object).toString());
        }
        if (!arrayList.isEmpty()) {
            boolean bl = VintfObject.verify(arrayList.toArray(new String[arrayList.size()])) == 0;
            return bl;
        }
        throw new IOException("no entries found in the compatibility file");
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean wipeEuiccData(Context context, String object) {
        BroadcastReceiver broadcastReceiver;
        void var1_8;
        block16 : {
            block15 : {
                long l;
                if (Settings.Global.getInt(context.getContentResolver(), "euicc_provisioned", 0) == 0) {
                    Log.d(TAG, "Skipping eUICC wipe/retain as it is not provisioned");
                    return true;
                }
                EuiccManager euiccManager = (EuiccManager)context.getSystemService("euicc");
                if (euiccManager == null) return false;
                if (!euiccManager.isEnabled()) return false;
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                AtomicBoolean atomicBoolean = new AtomicBoolean(false);
                broadcastReceiver = new BroadcastReceiver(){

                    @Override
                    public void onReceive(Context object, Intent intent) {
                        if (RecoverySystem.ACTION_EUICC_FACTORY_RESET.equals(intent.getAction())) {
                            if (this.getResultCode() != 0) {
                                int n = intent.getIntExtra("android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE", 0);
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Error wiping euicc data, Detailed code = ");
                                ((StringBuilder)object).append(n);
                                Log.e(RecoverySystem.TAG, ((StringBuilder)object).toString());
                            } else {
                                Log.d(RecoverySystem.TAG, "Successfully wiped euicc data.");
                                AtomicBoolean.this.set(true);
                            }
                            countDownLatch.countDown();
                        }
                    }
                };
                Parcelable parcelable = new Intent(ACTION_EUICC_FACTORY_RESET);
                ((Intent)parcelable).setPackage((String)object);
                object = PendingIntent.getBroadcastAsUser(context, 0, (Intent)parcelable, 134217728, UserHandle.SYSTEM);
                parcelable = new IntentFilter();
                ((IntentFilter)parcelable).addAction(ACTION_EUICC_FACTORY_RESET);
                Object object2 = new HandlerThread("euiccWipeFinishReceiverThread");
                ((Thread)object2).start();
                object2 = new Handler(((HandlerThread)object2).getLooper());
                context.getApplicationContext().registerReceiver(broadcastReceiver, (IntentFilter)parcelable, null, (Handler)object2);
                euiccManager.eraseSubscriptions((PendingIntent)object);
                object = context.getContentResolver();
                long l2 = Settings.Global.getLong((ContentResolver)object, "euicc_factory_reset_timeout_millis", 30000L);
                if (l2 < 5000L) {
                    l = 5000L;
                } else {
                    l = l2;
                    if (l2 > 60000L) {
                        l = 60000L;
                    }
                }
                object = TimeUnit.MILLISECONDS;
                try {
                    if (!countDownLatch.await(l, (TimeUnit)((Object)object))) {
                        Log.e(TAG, "Timeout wiping eUICC data.");
                        context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                        return false;
                    }
                    context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                    return atomicBoolean.get();
                }
                catch (InterruptedException interruptedException) {
                    break block15;
                }
                catch (Throwable throwable) {
                    break block16;
                }
                catch (InterruptedException interruptedException) {
                    break block15;
                }
                catch (Throwable throwable) {
                    break block16;
                }
                catch (InterruptedException interruptedException) {
                    // empty catch block
                }
            }
            try {
                Thread.currentThread().interrupt();
                Log.e(TAG, "Wiping eUICC data interrupted", (Throwable)object);
                context.getApplicationContext().unregisterReceiver(broadcastReceiver);
                return false;
            }
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        context.getApplicationContext().unregisterReceiver(broadcastReceiver);
        throw var1_8;
    }

    public static interface ProgressListener {
        public void onProgress(int var1);
    }

}

