/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Process;
import android.os.SystemProperties;
import android.util.Log;
import android.util.Printer;
import java.util.ArrayList;

public final class SQLiteDebug {
    private SQLiteDebug() {
    }

    public static void dump(Printer printer, String[] arrstring) {
        SQLiteDebug.dump(printer, arrstring, false);
    }

    public static void dump(Printer printer, String[] arrstring, boolean bl) {
        boolean bl2 = false;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals("-v")) continue;
            bl2 = true;
        }
        SQLiteDatabase.dumpAll(printer, bl2, bl);
    }

    public static PagerStats getDatabaseInfo() {
        PagerStats pagerStats = new PagerStats();
        SQLiteDebug.nativeGetPagerStats(pagerStats);
        pagerStats.dbStats = SQLiteDatabase.getDbStats();
        return pagerStats;
    }

    private static native void nativeGetPagerStats(PagerStats var0);

    public static boolean shouldLogSlowQuery(long l) {
        boolean bl = l >= (long)Math.min(SystemProperties.getInt("db.log.slow_query_threshold", Integer.MAX_VALUE), SystemProperties.getInt(NoPreloadHolder.SLOW_QUERY_THRESHOLD_UID_PROP, Integer.MAX_VALUE));
        return bl;
    }

    public static class DbStats {
        public String cache;
        public String dbName;
        public long dbSize;
        public int lookaside;
        public long pageSize;

        public DbStats(String charSequence, long l, long l2, int n, int n2, int n3, int n4) {
            this.dbName = charSequence;
            this.pageSize = l2 / 1024L;
            this.dbSize = l * l2 / 1024L;
            this.lookaside = n;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(n3);
            ((StringBuilder)charSequence).append("/");
            ((StringBuilder)charSequence).append(n4);
            this.cache = ((StringBuilder)charSequence).toString();
        }
    }

    public static final class NoPreloadHolder {
        public static final boolean DEBUG_LOG_DETAILED;
        public static final boolean DEBUG_LOG_SLOW_QUERIES;
        public static final boolean DEBUG_SQL_LOG;
        public static final boolean DEBUG_SQL_STATEMENTS;
        public static final boolean DEBUG_SQL_TIME;
        private static final String SLOW_QUERY_THRESHOLD_PROP = "db.log.slow_query_threshold";
        private static final String SLOW_QUERY_THRESHOLD_UID_PROP;

        static {
            DEBUG_SQL_LOG = Log.isLoggable("SQLiteLog", 2);
            DEBUG_SQL_STATEMENTS = Log.isLoggable("SQLiteStatements", 2);
            DEBUG_SQL_TIME = Log.isLoggable("SQLiteTime", 2);
            DEBUG_LOG_SLOW_QUERIES = Build.IS_DEBUGGABLE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("db.log.slow_query_threshold.");
            stringBuilder.append(Process.myUid());
            SLOW_QUERY_THRESHOLD_UID_PROP = stringBuilder.toString();
            boolean bl = Build.IS_DEBUGGABLE;
            boolean bl2 = false;
            if (bl && SystemProperties.getBoolean("db.log.detailed", false)) {
                bl2 = true;
            }
            DEBUG_LOG_DETAILED = bl2;
        }
    }

    public static class PagerStats {
        public ArrayList<DbStats> dbStats;
        public int largestMemAlloc;
        public int memoryUsed;
        public int pageCacheOverflow;
    }

}

