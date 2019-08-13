/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.content.res.Resources;
import android.database.sqlite.SQLiteCompatibilityWalFlags;
import android.os.StatFs;
import android.os.SystemProperties;

public final class SQLiteGlobal {
    public static final String SYNC_MODE_FULL = "FULL";
    private static final String TAG = "SQLiteGlobal";
    static final String WIPE_CHECK_FILE_SUFFIX = "-wipecheck";
    private static int sDefaultPageSize;
    public static volatile String sDefaultSyncMode;
    private static final Object sLock;

    static {
        sLock = new Object();
    }

    private SQLiteGlobal() {
    }

    public static boolean checkDbWipe() {
        return false;
    }

    public static String getDefaultJournalMode() {
        return SystemProperties.get("debug.sqlite.journalmode", Resources.getSystem().getString(17039834));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getDefaultPageSize() {
        Object object = sLock;
        synchronized (object) {
            if (sDefaultPageSize != 0) return SystemProperties.getInt("debug.sqlite.pagesize", sDefaultPageSize);
            StatFs statFs = new StatFs("/data");
            sDefaultPageSize = statFs.getBlockSize();
            return SystemProperties.getInt("debug.sqlite.pagesize", sDefaultPageSize);
        }
    }

    public static String getDefaultSyncMode() {
        String string2 = sDefaultSyncMode;
        if (string2 != null) {
            return string2;
        }
        return SystemProperties.get("debug.sqlite.syncmode", Resources.getSystem().getString(17039835));
    }

    public static int getIdleConnectionTimeout() {
        return SystemProperties.getInt("debug.sqlite.idle_connection_timeout", Resources.getSystem().getInteger(17694963));
    }

    public static int getJournalSizeLimit() {
        return SystemProperties.getInt("debug.sqlite.journalsizelimit", Resources.getSystem().getInteger(17694964));
    }

    public static int getWALAutoCheckpoint() {
        return Math.max(1, SystemProperties.getInt("debug.sqlite.wal.autocheckpoint", Resources.getSystem().getInteger(17694965)));
    }

    public static int getWALConnectionPoolSize() {
        return Math.max(2, SystemProperties.getInt("debug.sqlite.wal.poolsize", Resources.getSystem().getInteger(17694962)));
    }

    public static String getWALSyncMode() {
        String string2 = sDefaultSyncMode;
        if (string2 != null) {
            return string2;
        }
        return SystemProperties.get("debug.sqlite.wal.syncmode", Resources.getSystem().getString(17039836));
    }

    public static long getWALTruncateSize() {
        long l = SQLiteCompatibilityWalFlags.getTruncateSize();
        if (l >= 0L) {
            return l;
        }
        return SystemProperties.getInt("debug.sqlite.wal.truncatesize", Resources.getSystem().getInteger(17694966));
    }

    private static native int nativeReleaseMemory();

    public static int releaseMemory() {
        return SQLiteGlobal.nativeReleaseMemory();
    }
}

