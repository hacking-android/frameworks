/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.IPackageManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.Environment;
import android.os.FileObserver;
import android.os.FileUtils;
import android.os.RecoverySystem;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.storage.StorageManager;
import android.provider.Downloads;
import android.text.TextUtils;
import android.util.AtomicFile;
import android.util.EventLog;
import android.util.Slog;
import android.util.StatsLog;
import android.util.Xml;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.util.FastXmlSerializer;
import com.android.internal.util.XmlUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class BootReceiver
extends BroadcastReceiver {
    private static final String FSCK_FS_MODIFIED = "FILE SYSTEM WAS MODIFIED";
    private static final String FSCK_PASS_PATTERN = "Pass ([1-9]E?):";
    private static final String FSCK_TREE_OPTIMIZATION_PATTERN = "Inode [0-9]+ extent tree.*could be shorter";
    private static final int FS_STAT_FS_FIXED = 1024;
    private static final String FS_STAT_PATTERN = "fs_stat,[^,]*/([^/,]+),(0x[0-9a-fA-F]+)";
    private static final String LAST_HEADER_FILE = "last-header.txt";
    private static final String[] LAST_KMSG_FILES;
    private static final String LAST_SHUTDOWN_TIME_PATTERN = "powerctl_shutdown_time_ms:([0-9]+):([0-9]+)";
    private static final String LOG_FILES_FILE = "log-files.xml";
    private static final int LOG_SIZE;
    private static final String METRIC_SHUTDOWN_TIME_START = "begin_shutdown";
    private static final String METRIC_SYSTEM_SERVER = "shutdown_system_server";
    private static final String[] MOUNT_DURATION_PROPS_POSTFIX;
    private static final String OLD_UPDATER_CLASS = "com.google.android.systemupdater.SystemUpdateReceiver";
    private static final String OLD_UPDATER_PACKAGE = "com.google.android.systemupdater";
    private static final String SHUTDOWN_METRICS_FILE = "/data/system/shutdown-metrics.txt";
    private static final String SHUTDOWN_TRON_METRICS_PREFIX = "shutdown_";
    private static final String TAG = "BootReceiver";
    private static final String TAG_TOMBSTONE = "SYSTEM_TOMBSTONE";
    private static final File TOMBSTONE_DIR;
    private static final int UMOUNT_STATUS_NOT_AVAILABLE = 4;
    private static final File lastHeaderFile;
    private static final AtomicFile sFile;
    private static FileObserver sTombstoneObserver;

    static {
        int n = SystemProperties.getInt("ro.debuggable", 0) == 1 ? 98304 : 65536;
        LOG_SIZE = n;
        TOMBSTONE_DIR = new File("/data/tombstones");
        sTombstoneObserver = null;
        sFile = new AtomicFile(new File(Environment.getDataSystemDirectory(), LOG_FILES_FILE), "log-files");
        lastHeaderFile = new File(Environment.getDataSystemDirectory(), LAST_HEADER_FILE);
        MOUNT_DURATION_PROPS_POSTFIX = new String[]{"early", "default", "late"};
        LAST_KMSG_FILES = new String[]{"/sys/fs/pstore/console-ramoops", "/proc/last_kmsg"};
    }

    private static void addAuditErrorsToDropBox(DropBoxManager dropBoxManager, HashMap<String, Long> serializable, String string2, int n, String string3) throws IOException {
        if (dropBoxManager != null && dropBoxManager.isTagEnabled(string3)) {
            long l;
            Slog.i(TAG, "Copying audit failures to DropBox");
            Object object = new File("/proc/last_kmsg");
            long l2 = l = ((File)object).lastModified();
            if (l <= 0L) {
                object = new File("/sys/fs/pstore/console-ramoops");
                l2 = l = ((File)object).lastModified();
                if (l <= 0L) {
                    object = new File("/sys/fs/pstore/console-ramoops-0");
                    l2 = ((File)object).lastModified();
                }
            }
            if (l2 <= 0L) {
                return;
            }
            if (((HashMap)serializable).containsKey(string3) && ((HashMap)serializable).get(string3) == l2) {
                return;
            }
            ((HashMap)serializable).put((String)string3, l2);
            object = FileUtils.readTextFile((File)object, n, "[[TRUNCATED]]\n");
            serializable = new StringBuilder();
            for (String string4 : ((String)object).split("\n")) {
                if (!string4.contains("audit")) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string4);
                stringBuilder.append("\n");
                ((StringBuilder)serializable).append(stringBuilder.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Copied ");
            ((StringBuilder)object).append(((StringBuilder)serializable).toString().length());
            ((StringBuilder)object).append(" worth of audits to DropBox");
            Slog.i(TAG, ((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(((StringBuilder)serializable).toString());
            dropBoxManager.addText(string3, ((StringBuilder)object).toString());
            return;
        }
    }

    private static void addFileToDropBox(DropBoxManager dropBoxManager, HashMap<String, Long> hashMap, String string2, String string3, int n, String string4) throws IOException {
        BootReceiver.addFileWithFootersToDropBox(dropBoxManager, hashMap, string2, "", string3, n, string4);
    }

    private static void addFileWithFootersToDropBox(DropBoxManager dropBoxManager, HashMap<String, Long> object, String string2, String string3, String string4, int n, String string5) throws IOException {
        if (dropBoxManager != null && dropBoxManager.isTagEnabled(string5)) {
            Serializable serializable = new File(string4);
            long l = ((File)serializable).lastModified();
            if (l <= 0L) {
                return;
            }
            if (((HashMap)object).containsKey(string4) && ((HashMap)object).get(string4) == l) {
                return;
            }
            ((HashMap)object).put((String)string4, l);
            object = FileUtils.readTextFile((File)serializable, n, "[[TRUNCATED]]\n");
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append(string3);
            string2 = ((StringBuilder)serializable).toString();
            if (string5.equals(TAG_TOMBSTONE) && ((String)object).contains(">>> system_server <<<")) {
                BootReceiver.addTextToDropBox(dropBoxManager, "system_server_native_crash", string2, string4, n);
            }
            if (string5.equals(TAG_TOMBSTONE)) {
                StatsLog.write(186);
            }
            BootReceiver.addTextToDropBox(dropBoxManager, string5, string2, string4, n);
            return;
        }
    }

    private static void addFsckErrorsToDropBoxAndLogFsStat(DropBoxManager dropBoxManager, HashMap<String, Long> hashMap, String string2, int n, String string3) throws IOException {
        boolean bl = dropBoxManager != null && dropBoxManager.isTagEnabled(string3);
        Slog.i(TAG, "Checking for fsck errors");
        File file = new File("/dev/fscklogs/log");
        if (file.lastModified() <= 0L) {
            return;
        }
        String[] arrstring = FileUtils.readTextFile(file, n, "[[TRUNCATED]]\n");
        Pattern pattern = Pattern.compile(FS_STAT_PATTERN);
        arrstring = arrstring.split("\n");
        int n2 = arrstring.length;
        boolean bl2 = false;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n2; ++i) {
            String string4 = arrstring[i];
            if (string4.contains(FSCK_FS_MODIFIED)) {
                bl2 = true;
            } else if (string4.contains("fs_stat")) {
                Object object = pattern.matcher(string4);
                if (((Matcher)object).find()) {
                    BootReceiver.handleFsckFsStat((Matcher)object, arrstring, n4, n3);
                    n4 = n3;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("cannot parse fs_stat:");
                    ((StringBuilder)object).append(string4);
                    Slog.w(TAG, ((StringBuilder)object).toString());
                }
            }
            ++n3;
        }
        if (bl && bl2) {
            BootReceiver.addFileToDropBox(dropBoxManager, hashMap, string2, "/dev/fscklogs/log", n, string3);
        }
        file.delete();
    }

    private static void addTextToDropBox(DropBoxManager dropBoxManager, String string2, String string3, String string4, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Copying ");
        stringBuilder.append(string4);
        stringBuilder.append(" to DropBox (");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        Slog.i(TAG, stringBuilder.toString());
        dropBoxManager.addText(string2, string3);
        EventLog.writeEvent(81002, string4, n, string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public static int fixFsckFsStat(String var0, int var1_1, String[] var2_2, int var3_3, int var4_4) {
        block12 : {
            var5_5 = var1_1;
            if ((var5_5 & 1024) == 0) {
                return var5_5;
            }
            var6_6 = Pattern.compile("Pass ([1-9]E?):");
            var7_7 = Pattern.compile("Inode [0-9]+ extent tree.*could be shorter");
            var8_8 = "";
            var9_9 = false;
            var1_1 = 0;
            var10_10 = 0;
            var11_11 = 0;
            var12_12 = null;
            while (var3_3 < var4_4) {
                block14 : {
                    block17 : {
                        block15 : {
                            block16 : {
                                block13 : {
                                    var13_13 = var2_2[var3_3];
                                    if (var13_13.contains("FILE SYSTEM WAS MODIFIED")) {
                                        var3_3 = var11_11;
                                        var2_2 = var12_12;
                                        break block12;
                                    }
                                    var14_14 = var13_13.startsWith("Pass ");
                                    if (!var14_14) break block13;
                                    if ((var13_13 = var6_6.matcher((CharSequence)var13_13)).find()) {
                                        var8_8 = var13_13.group(1);
                                    }
                                    var15_15 = var9_9;
                                    var16_16 = var10_10;
                                    break block14;
                                }
                                if (!var13_13.startsWith("Inode ")) break block15;
                                if (!var7_7.matcher((CharSequence)var13_13).find() || !var8_8.equals("1")) break block16;
                                var15_15 = true;
                                var17_17 = new StringBuilder();
                                var17_17.append("fs_stat, partition:");
                                var17_17.append(var0);
                                var17_17.append(" found tree optimization:");
                                var17_17.append((String)var13_13);
                                Slog.i("BootReceiver", var17_17.toString());
                                var16_16 = var10_10;
                                break block14;
                            }
                            var3_3 = 1;
                            var2_2 = var13_13;
                            break block12;
                        }
                        if (!var13_13.startsWith("[QUOTA WARNING]") || !var8_8.equals("5")) break block17;
                        var17_17 = new StringBuilder();
                        var17_17.append("fs_stat, partition:");
                        var17_17.append(var0);
                        var17_17.append(" found quota warning:");
                        var17_17.append((String)var13_13);
                        Slog.i("BootReceiver", var17_17.toString());
                        var1_1 = 1;
                        var15_15 = var9_9;
                        var16_16 = var10_10;
                        if (!var9_9) {
                            var2_2 = var13_13;
                            var1_1 = 1;
                            var3_3 = var11_11;
                            break block12;
                        }
                        break block14;
                    }
                    if (var13_13.startsWith("Update quota info") && var8_8.equals("5")) ** GOTO lbl93
                    if (var13_13.startsWith("Timestamp(s) on inode") && var13_13.contains("beyond 2310-04-04 are likely pre-1970") && var8_8.equals("1")) {
                        var17_17 = new StringBuilder();
                        var17_17.append("fs_stat, partition:");
                        var17_17.append(var0);
                        var17_17.append(" found timestamp adjustment:");
                        var17_17.append((String)var13_13);
                        Slog.i("BootReceiver", var17_17.toString());
                        var10_10 = var3_3;
                        if (var2_2[var3_3 + 1].contains("Fix? yes")) {
                            var10_10 = var3_3 + 1;
                        }
                        var16_16 = 1;
                        var15_15 = var9_9;
                        var3_3 = var10_10;
                    } else {
                        if (!(var13_13 = var13_13.trim()).isEmpty() && !var8_8.isEmpty()) {
                            var3_3 = 1;
                            var2_2 = var13_13;
                            break block12;
                        }
lbl93: // 3 sources:
                        var16_16 = var10_10;
                        var15_15 = var9_9;
                    }
                }
                ++var3_3;
                var9_9 = var15_15;
                var10_10 = var16_16;
            }
            var2_2 = var12_12;
            var3_3 = var11_11;
        }
        if (var3_3 != 0) {
            var1_1 = var5_5;
            if (var2_2 == null) return var1_1;
            var8_8 = new StringBuilder();
            var8_8.append("fs_stat, partition:");
            var8_8.append(var0);
            var8_8.append(" fix:");
            var8_8.append((String)var2_2);
            Slog.i("BootReceiver", var8_8.toString());
            return var5_5;
        }
        if (var1_1 != 0 && !var9_9) {
            var2_2 = new StringBuilder();
            var2_2.append("fs_stat, got quota fix without tree optimization, partition:");
            var2_2.append(var0);
            Slog.i("BootReceiver", var2_2.toString());
            return var5_5;
        }
        if (!var9_9 || var1_1 == 0) {
            var1_1 = var5_5;
            if (var10_10 == 0) return var1_1;
        }
        var2_2 = new StringBuilder();
        var2_2.append("fs_stat, partition:");
        var2_2.append(var0);
        var2_2.append(" fix ignored");
        Slog.i("BootReceiver", var2_2.toString());
        return var5_5 & -1025;
    }

    private String getBootHeadersToLogAndUpdate() throws IOException {
        CharSequence charSequence = this.getPreviousBootHeaders();
        CharSequence charSequence2 = this.getCurrentBootHeaders();
        try {
            FileUtils.stringToFile(lastHeaderFile, (String)charSequence2);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error writing ");
            stringBuilder.append(lastHeaderFile);
            Slog.e(TAG, stringBuilder.toString(), iOException);
        }
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("isPrevious: false\n");
            ((StringBuilder)charSequence).append((String)charSequence2);
            return ((StringBuilder)charSequence).toString();
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("isPrevious: true\n");
        ((StringBuilder)charSequence2).append((String)charSequence);
        return ((StringBuilder)charSequence2).toString();
    }

    private String getCurrentBootHeaders() throws IOException {
        StringBuilder stringBuilder = new StringBuilder(512);
        stringBuilder.append("Build: ");
        stringBuilder.append(Build.FINGERPRINT);
        stringBuilder.append("\n");
        stringBuilder.append("Hardware: ");
        stringBuilder.append(Build.BOARD);
        stringBuilder.append("\n");
        stringBuilder.append("Revision: ");
        stringBuilder.append(SystemProperties.get("ro.revision", ""));
        stringBuilder.append("\n");
        stringBuilder.append("Bootloader: ");
        stringBuilder.append(Build.BOOTLOADER);
        stringBuilder.append("\n");
        stringBuilder.append("Radio: ");
        stringBuilder.append(Build.getRadioVersion());
        stringBuilder.append("\n");
        stringBuilder.append("Kernel: ");
        stringBuilder.append(FileUtils.readTextFile(new File("/proc/version"), 1024, "...\n"));
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    private String getPreviousBootHeaders() {
        try {
            String string2 = FileUtils.readTextFile(lastHeaderFile, 0, null);
            return string2;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private static void handleFsckFsStat(Matcher object, String[] arrstring, int n, int n2) {
        int n3;
        String string2 = ((Matcher)object).group(1);
        try {
            n3 = Integer.decode(((Matcher)object).group(2));
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("cannot parse fs_stat: partition:");
            stringBuilder.append(string2);
            stringBuilder.append(" stat:");
            stringBuilder.append(((Matcher)object).group(2));
            Slog.w(TAG, stringBuilder.toString());
            return;
        }
        n = BootReceiver.fixFsckFsStat(string2, n3, arrstring, n, n2);
        object = new StringBuilder();
        ((StringBuilder)object).append("boot_fs_stat_");
        ((StringBuilder)object).append(string2);
        MetricsLogger.histogram(null, ((StringBuilder)object).toString(), n);
        object = new StringBuilder();
        ((StringBuilder)object).append("fs_stat, partition:");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" stat:0x");
        ((StringBuilder)object).append(Integer.toHexString(n));
        Slog.i(TAG, ((StringBuilder)object).toString());
    }

    private void logBootEvents(Context object) throws IOException {
        final DropBoxManager dropBoxManager = (DropBoxManager)object.getSystemService("dropbox");
        final String string2 = this.getBootHeadersToLogAndUpdate();
        Object object2 = SystemProperties.get("ro.boot.bootreason", null);
        if ((object = RecoverySystem.handleAftermath((Context)object)) != null && dropBoxManager != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append((String)object);
            dropBoxManager.addText("SYSTEM_RECOVERY_LOG", stringBuilder.toString());
        }
        if (object2 != null) {
            object = new StringBuilder(512);
            object.append("\n");
            object.append("Boot info:\n");
            object.append("Last boot reason: ");
            object.append((String)object2);
            object.append("\n");
            object = object.toString();
        } else {
            object = "";
        }
        object2 = BootReceiver.readTimestamps();
        if (SystemProperties.getLong("ro.runtime.firstboot", 0L) == 0L) {
            if (!StorageManager.inCryptKeeperBounce()) {
                SystemProperties.set("ro.runtime.firstboot", Long.toString(System.currentTimeMillis()));
            }
            if (dropBoxManager != null) {
                dropBoxManager.addText("SYSTEM_BOOT", string2);
            }
            BootReceiver.addFileWithFootersToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, (String)object, "/proc/last_kmsg", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            BootReceiver.addFileWithFootersToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, (String)object, "/sys/fs/pstore/console-ramoops", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            BootReceiver.addFileWithFootersToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, (String)object, "/sys/fs/pstore/console-ramoops-0", -LOG_SIZE, "SYSTEM_LAST_KMSG");
            BootReceiver.addFileToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, "/cache/recovery/log", -LOG_SIZE, "SYSTEM_RECOVERY_LOG");
            BootReceiver.addFileToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, "/cache/recovery/last_kmsg", -LOG_SIZE, "SYSTEM_RECOVERY_KMSG");
            BootReceiver.addAuditErrorsToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, -LOG_SIZE, "SYSTEM_AUDIT");
        } else if (dropBoxManager != null) {
            dropBoxManager.addText("SYSTEM_RESTART", string2);
        }
        BootReceiver.logFsShutdownTime();
        BootReceiver.logFsMountTime();
        BootReceiver.addFsckErrorsToDropBoxAndLogFsStat(dropBoxManager, (HashMap<String, Long>)object2, string2, -LOG_SIZE, "SYSTEM_FSCK");
        BootReceiver.logSystemServerShutdownTimeMetrics();
        object = TOMBSTONE_DIR.listFiles();
        for (int i = 0; object != null && i < ((File[])object).length; ++i) {
            if (!object[i].isFile()) continue;
            BootReceiver.addFileToDropBox(dropBoxManager, (HashMap<String, Long>)object2, string2, object[i].getPath(), LOG_SIZE, TAG_TOMBSTONE);
        }
        this.writeTimestamps((HashMap<String, Long>)object2);
        sTombstoneObserver = new FileObserver(TOMBSTONE_DIR.getPath(), 256){

            @Override
            public void onEvent(int n, String string22) {
                HashMap hashMap = BootReceiver.readTimestamps();
                try {
                    File file = new File(TOMBSTONE_DIR, string22);
                    if (file.isFile() && file.getName().startsWith("tombstone_")) {
                        BootReceiver.addFileToDropBox(dropBoxManager, hashMap, string2, file.getPath(), LOG_SIZE, BootReceiver.TAG_TOMBSTONE);
                    }
                }
                catch (IOException iOException) {
                    Slog.e(BootReceiver.TAG, "Can't log tombstone", iOException);
                }
                BootReceiver.this.writeTimestamps(hashMap);
            }
        };
        sTombstoneObserver.startWatching();
    }

    private static void logFsMountTime() {
        for (String string2 : MOUNT_DURATION_PROPS_POSTFIX) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ro.boottime.init.mount_all.");
            stringBuilder.append(string2);
            int n = SystemProperties.getInt(stringBuilder.toString(), 0);
            if (n == 0) continue;
            stringBuilder = new StringBuilder();
            stringBuilder.append("boot_mount_all_duration_");
            stringBuilder.append(string2);
            MetricsLogger.histogram(null, stringBuilder.toString(), n);
        }
    }

    private static void logFsShutdownTime() {
        block5 : {
            block4 : {
                Object object;
                Object object2 = null;
                String[] arrstring = LAST_KMSG_FILES;
                int n = arrstring.length;
                int n2 = 0;
                do {
                    object = object2;
                    if (n2 >= n || ((File)(object = new File(arrstring[n2]))).exists()) break;
                    ++n2;
                } while (true);
                if (object == null) {
                    return;
                }
                try {
                    object = FileUtils.readTextFile((File)object, -16384, null);
                    object2 = Pattern.compile(LAST_SHUTDOWN_TIME_PATTERN, 8).matcher((CharSequence)object);
                    if (!((Matcher)object2).find()) break block4;
                }
                catch (IOException iOException) {
                    Slog.w(TAG, "cannot read last msg", iOException);
                    return;
                }
                MetricsLogger.histogram(null, "boot_fs_shutdown_duration", Integer.parseInt(((Matcher)object2).group(1)));
                MetricsLogger.histogram(null, "boot_fs_shutdown_umount_stat", Integer.parseInt(((Matcher)object2).group(2)));
                object = new StringBuilder();
                ((StringBuilder)object).append("boot_fs_shutdown,");
                ((StringBuilder)object).append(((Matcher)object2).group(1));
                ((StringBuilder)object).append(",");
                ((StringBuilder)object).append(((Matcher)object2).group(2));
                Slog.i(TAG, ((StringBuilder)object).toString());
                break block5;
            }
            MetricsLogger.histogram(null, "boot_fs_shutdown_umount_stat", 4);
            Slog.w(TAG, "boot_fs_shutdown, string not found");
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void logStatsdShutdownAtom(String charSequence, String charSequence2, String string2, String string3) {
        long l;
        long l2;
        String string4;
        boolean bl;
        long l3;
        block13 : {
            block14 : {
                block11 : {
                    block12 : {
                        string4 = "<EMPTY>";
                        l3 = 0L;
                        l = 0L;
                        if (charSequence == null) break block11;
                        if (!((String)charSequence).equals("y")) break block12;
                        bl = true;
                        break block13;
                    }
                    if (!((String)charSequence).equals("n")) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected value for reboot : ");
                        stringBuilder.append((String)charSequence);
                        Slog.e(TAG, stringBuilder.toString());
                    }
                    break block14;
                }
                Slog.e(TAG, "No value received for reboot");
            }
            bl = false;
        }
        if (charSequence2 != null) {
            charSequence = charSequence2;
        } else {
            Slog.e(TAG, "No value received for shutdown reason");
            charSequence = string4;
        }
        if (string2 != null) {
            try {
                l3 = l2 = Long.parseLong(string2);
            }
            catch (NumberFormatException numberFormatException) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("Cannot parse shutdown start time: ");
                ((StringBuilder)charSequence2).append(string2);
                Slog.e(TAG, ((StringBuilder)charSequence2).toString());
            }
            l2 = l3;
        } else {
            Slog.e(TAG, "No value received for shutdown start time");
            l2 = l3;
        }
        if (string3 != null) {
            try {
                l3 = Long.parseLong(string3);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot parse shutdown duration: ");
                stringBuilder.append(string2);
                Slog.e(TAG, stringBuilder.toString());
                l3 = l;
            }
        } else {
            Slog.e(TAG, "No value received for shutdown duration");
            l3 = l;
        }
        StatsLog.write(56, bl, (String)charSequence, l2, l3);
    }

    private static void logSystemServerShutdownTimeMetrics() {
        CharSequence charSequence;
        CharSequence charSequence2;
        File file = new File(SHUTDOWN_METRICS_FILE);
        String string2 = charSequence = null;
        if (file.exists()) {
            try {
                string2 = FileUtils.readTextFile(file, 0, null);
            }
            catch (IOException iOException) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("Problem reading ");
                ((StringBuilder)charSequence2).append(file);
                Slog.e(TAG, ((StringBuilder)charSequence2).toString(), iOException);
                string2 = charSequence;
            }
        }
        if (!TextUtils.isEmpty(string2)) {
            String[] arrstring = string2.split(",");
            int n = arrstring.length;
            charSequence = null;
            String string3 = null;
            String string4 = null;
            String string5 = null;
            for (int i = 0; i < n; ++i) {
                String string6;
                String string7;
                String[] arrstring2 = arrstring[i].split(":");
                if (arrstring2.length != 2) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("Wrong format of shutdown metrics - ");
                    ((StringBuilder)charSequence2).append(string2);
                    Slog.e(TAG, ((StringBuilder)charSequence2).toString());
                    string7 = string5;
                    string6 = string4;
                } else {
                    charSequence2 = charSequence;
                    if (arrstring2[0].startsWith(SHUTDOWN_TRON_METRICS_PREFIX)) {
                        BootReceiver.logTronShutdownMetric(arrstring2[0], arrstring2[1]);
                        charSequence2 = charSequence;
                        if (arrstring2[0].equals(METRIC_SYSTEM_SERVER)) {
                            charSequence2 = arrstring2[1];
                        }
                    }
                    if (arrstring2[0].equals("reboot")) {
                        string7 = arrstring2[1];
                        string6 = string4;
                        charSequence = charSequence2;
                    } else if (arrstring2[0].equals("reason")) {
                        string6 = arrstring2[1];
                        string7 = string5;
                        charSequence = charSequence2;
                    } else {
                        string7 = string5;
                        string6 = string4;
                        charSequence = charSequence2;
                        if (arrstring2[0].equals(METRIC_SHUTDOWN_TIME_START)) {
                            string3 = arrstring2[1];
                            charSequence = charSequence2;
                            string6 = string4;
                            string7 = string5;
                        }
                    }
                }
                string5 = string7;
                string4 = string6;
            }
            BootReceiver.logStatsdShutdownAtom(string5, string4, string3, charSequence);
        }
        file.delete();
    }

    private static void logTronShutdownMetric(String string2, String string3) {
        block2 : {
            int n;
            try {
                n = Integer.parseInt(string3);
                if (n < 0) break block2;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot parse metric ");
                stringBuilder.append(string2);
                stringBuilder.append(" int value - ");
                stringBuilder.append(string3);
                Slog.e(TAG, stringBuilder.toString());
                return;
            }
            MetricsLogger.histogram(null, string2, n);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static HashMap<String, Long> readTimestamps() {
        block27 : {
            block28 : {
                block29 : {
                    block25 : {
                        block26 : {
                            var0 = BootReceiver.sFile;
                            // MONITORENTER : var0
                            var1_1 = new HashMap<String, Long>();
                            var2_2 = 0;
                            var3_3 = 0;
                            var4_4 = 0;
                            var5_5 = 0;
                            var6_6 = 0;
                            var8_8 = var7_7 = 0;
                            var9_9 = var2_2;
                            var10_10 = var3_3;
                            var11_11 = var4_4;
                            var12_12 = var5_5;
                            var13_13 = var6_6;
                            var14_14 = BootReceiver.sFile.openRead();
                            var15_20 = Xml.newPullParser();
                            var15_20.setInput((InputStream)var14_14, StandardCharsets.UTF_8.name());
                            while ((var8_8 = var15_20.next()) != 2 && var8_8 != 1) {
                            }
                            if (var8_8 != 2) break block25;
                            var9_9 = var15_20.getDepth();
                            while ((var8_8 = var15_20.next()) != 1 && (var8_8 != 3 || var15_20.getDepth() > var9_9)) {
                                if (var8_8 == 3 || var8_8 == 4) continue;
                                if (var15_20.getName().equals("log")) {
                                    var1_1.put(var15_20.getAttributeValue(null, "filename"), (long)Long.valueOf(var15_20.getAttributeValue(null, "timestamp")));
                                    continue;
                                }
                                var16_22 = new StringBuilder();
                                var16_22.append("Unknown tag: ");
                                var16_22.append(var15_20.getName());
                                Slog.w("BootReceiver", var16_22.toString());
                                XmlUtils.skipCurrentTag(var15_20);
                            }
                            var9_9 = 1;
                            var10_10 = 1;
                            var11_11 = 1;
                            var12_12 = 1;
                            var13_13 = 1;
                            var8_8 = 1;
                            if (var14_14 == null) break block26;
                            var14_14.close();
                        }
                        if (true) break block28;
                        break block29;
                    }
                    try {
                        var16_23 = new IllegalStateException("no start tag found");
                        throw var16_23;
                    }
                    catch (Throwable var16_24) {
                        try {
                            throw var16_24;
                        }
                        finally {
                            if (var14_14 == null) ** GOTO lbl73
                            try {
                                var14_14.close();
                                ** GOTO lbl73
                            }
                            catch (Throwable var14_15) {
                                var8_8 = var7_7;
                                var9_9 = var2_2;
                                var10_10 = var3_3;
                                var11_11 = var4_4;
                                var12_12 = var5_5;
                                var13_13 = var6_6;
                                try {
                                    var16_24.addSuppressed(var14_15);
lbl73: // 3 sources:
                                    var8_8 = var7_7;
                                    var9_9 = var2_2;
                                    var10_10 = var3_3;
                                    var11_11 = var4_4;
                                    var12_12 = var5_5;
                                    var13_13 = var6_6;
                                    throw var15_21;
                                }
                                catch (Throwable var14_16) {
                                    break block27;
                                }
                                catch (XmlPullParserException var16_25) {
                                    var8_8 = var9_9;
                                    var8_8 = var9_9;
                                    var14_14 = new StringBuilder();
                                    var8_8 = var9_9;
                                    var14_14.append("Failed parsing ");
                                    var8_8 = var9_9;
                                    var14_14.append((Object)var16_25);
                                    var8_8 = var9_9;
                                    Slog.w("BootReceiver", var14_14.toString());
                                    if (var9_9 != 0) break block28;
                                    break block29;
                                }
                                catch (NullPointerException var16_26) {
                                    var8_8 = var10_10;
                                    var8_8 = var10_10;
                                    var14_14 = new StringBuilder();
                                    var8_8 = var10_10;
                                    var14_14.append("Failed parsing ");
                                    var8_8 = var10_10;
                                    var14_14.append(var16_26);
                                    var8_8 = var10_10;
                                    Slog.w("BootReceiver", var14_14.toString());
                                    if (var10_10 != 0) break block28;
                                    break block29;
                                }
                                catch (IllegalStateException var14_17) {
                                    var8_8 = var11_11;
                                    var8_8 = var11_11;
                                    var16_22 = new StringBuilder();
                                    var8_8 = var11_11;
                                    var16_22.append("Failed parsing ");
                                    var8_8 = var11_11;
                                    var16_22.append(var14_17);
                                    var8_8 = var11_11;
                                    Slog.w("BootReceiver", var16_22.toString());
                                    if (var11_11 != 0) break block28;
                                    break block29;
                                }
                                catch (IOException var16_27) {
                                    var8_8 = var12_12;
                                    var8_8 = var12_12;
                                    var14_14 = new StringBuilder();
                                    var8_8 = var12_12;
                                    var14_14.append("Failed parsing ");
                                    var8_8 = var12_12;
                                    var14_14.append(var16_27);
                                    var8_8 = var12_12;
                                    Slog.w("BootReceiver", var14_14.toString());
                                    if (var12_12 != 0) break block28;
                                    break block29;
                                }
                                catch (FileNotFoundException var14_18) {
                                    var8_8 = var13_13;
                                    var8_8 = var13_13;
                                    var14_19 = new StringBuilder();
                                    var8_8 = var13_13;
                                    var14_19.append("No existing last log timestamp file ");
                                    var8_8 = var13_13;
                                    var14_19.append(BootReceiver.sFile.getBaseFile());
                                    var8_8 = var13_13;
                                    var14_19.append("; starting empty");
                                    var8_8 = var13_13;
                                    Slog.i("BootReceiver", var14_19.toString());
                                    if (var13_13 != 0) break block28;
                                }
                            }
                        }
                    }
                }
                var1_1.clear();
                return var1_1;
            }
            // MONITOREXIT : var0
            return var1_1;
        }
        if (var8_8 != 0) throw var14_16;
        var1_1.clear();
        throw var14_16;
    }

    private void removeOldUpdatePackages(Context context) {
        Downloads.removeAllDownloadsByPackage(context, OLD_UPDATER_PACKAGE, OLD_UPDATER_CLASS);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void writeTimestamps(HashMap<String, Long> hashMap) {
        AtomicFile atomicFile = sFile;
        // MONITORENTER : atomicFile
        FileOutputStream fileOutputStream = sFile.startWrite();
        try {
            FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
            fastXmlSerializer.setOutput((OutputStream)fileOutputStream, StandardCharsets.UTF_8.name());
            fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
            fastXmlSerializer.startTag(null, "log-files");
            Iterator<String> iterator = hashMap.keySet().iterator();
            do {
                if (!iterator.hasNext()) {
                    fastXmlSerializer.endTag(null, "log-files");
                    fastXmlSerializer.endDocument();
                    sFile.finishWrite(fileOutputStream);
                    return;
                }
                String string2 = iterator.next();
                fastXmlSerializer.startTag(null, "log");
                fastXmlSerializer.attribute(null, "filename", string2);
                fastXmlSerializer.attribute(null, "timestamp", hashMap.get(string2).toString());
                fastXmlSerializer.endTag(null, "log");
            } while (true);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to write timestamp file, using the backup: ");
            stringBuilder.append(iOException);
            Slog.w(TAG, stringBuilder.toString());
            sFile.failWrite(fileOutputStream);
        }
        return;
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to write timestamp file: ");
            stringBuilder.append(iOException);
            Slog.w(TAG, stringBuilder.toString());
            // MONITOREXIT : atomicFile
            return;
        }
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        new Thread(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                try {
                    BootReceiver.this.logBootEvents(context);
                }
                catch (Exception exception2) {
                    Slog.e(BootReceiver.TAG, "Can't log boot events", exception2);
                }
                boolean bl2 = false;
                try {
                    boolean bl;
                    try {
                        bl = IPackageManager.Stub.asInterface(ServiceManager.getService("package")).isOnlyCoreApps();
                    }
                    catch (RemoteException remoteException) {
                        bl = bl2;
                    }
                    if (bl) return;
                    BootReceiver.this.removeOldUpdatePackages(context);
                    return;
                }
                catch (Exception exception3) {}
                Slog.e(BootReceiver.TAG, "Can't remove old update packages", exception3);
            }
        }.start();
    }

}

