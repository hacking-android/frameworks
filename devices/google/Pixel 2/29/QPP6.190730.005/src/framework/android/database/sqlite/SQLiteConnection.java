/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 *  dalvik.system.CloseGuard
 */
package android.database.sqlite;

import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteBindOrColumnIndexOutOfRangeException;
import android.database.sqlite.SQLiteCompatibilityWalFlags;
import android.database.sqlite.SQLiteConnectionPool;
import android.database.sqlite.SQLiteCustomFunction;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteGlobal;
import android.database.sqlite.SQLiteStatementInfo;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.SystemClock;
import android.os.Trace;
import android.util.Log;
import android.util.LruCache;
import android.util.Printer;
import dalvik.system.BlockGuard;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class SQLiteConnection
implements CancellationSignal.OnCancelListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final boolean DEBUG = false;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final String TAG = "SQLiteConnection";
    private int mCancellationSignalAttachCount;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final int mConnectionId;
    private long mConnectionPtr;
    private final boolean mIsPrimaryConnection;
    private final boolean mIsReadOnlyConnection;
    private boolean mOnlyAllowReadOnlyOperations;
    private final SQLiteConnectionPool mPool;
    private final PreparedStatementCache mPreparedStatementCache;
    private PreparedStatement mPreparedStatementPool;
    private final OperationLog mRecentOperations;

    static {
        EMPTY_STRING_ARRAY = new String[0];
        EMPTY_BYTE_ARRAY = new byte[0];
    }

    private SQLiteConnection(SQLiteConnectionPool sQLiteConnectionPool, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int n, boolean bl) {
        this.mPool = sQLiteConnectionPool;
        this.mRecentOperations = new OperationLog(this.mPool);
        this.mConfiguration = new SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.mConnectionId = n;
        this.mIsPrimaryConnection = bl;
        n = sQLiteDatabaseConfiguration.openFlags;
        bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        this.mIsReadOnlyConnection = bl;
        this.mPreparedStatementCache = new PreparedStatementCache(this.mConfiguration.maxSqlCacheSize);
        this.mCloseGuard.open("close");
    }

    private PreparedStatement acquirePreparedStatement(String string2) {
        PreparedStatement preparedStatement;
        block10 : {
            int n;
            preparedStatement = (PreparedStatement)this.mPreparedStatementCache.get(string2);
            boolean bl = false;
            if (preparedStatement != null) {
                if (!preparedStatement.mInUse) {
                    return preparedStatement;
                }
                bl = true;
            }
            long l = SQLiteConnection.nativePrepareStatement(this.mConnectionPtr, string2);
            PreparedStatement preparedStatement2 = preparedStatement;
            try {
                n = SQLiteConnection.nativeGetParameterCount(this.mConnectionPtr, l);
                preparedStatement2 = preparedStatement;
            }
            catch (RuntimeException runtimeException) {
                if (preparedStatement2 == null || !preparedStatement2.mInCache) {
                    SQLiteConnection.nativeFinalizeStatement(this.mConnectionPtr, l);
                }
                throw runtimeException;
            }
            int n2 = DatabaseUtils.getSqlStatementType(string2);
            preparedStatement2 = preparedStatement;
            preparedStatement = this.obtainPreparedStatement(string2, l, n, n2, SQLiteConnection.nativeIsReadOnly(this.mConnectionPtr, l));
            if (bl) break block10;
            preparedStatement2 = preparedStatement;
            if (!SQLiteConnection.isCacheable(n2)) break block10;
            preparedStatement2 = preparedStatement;
            this.mPreparedStatementCache.put(string2, preparedStatement);
            preparedStatement2 = preparedStatement;
            preparedStatement.mInCache = true;
        }
        preparedStatement.mInUse = true;
        return preparedStatement;
    }

    private void applyBlockGuardPolicy(PreparedStatement preparedStatement) {
        if (!this.mConfiguration.isInMemoryDb()) {
            if (preparedStatement.mReadOnly) {
                BlockGuard.getThreadPolicy().onReadFromDisk();
            } else {
                BlockGuard.getThreadPolicy().onWriteToDisk();
            }
        }
    }

    private void attachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
            ++this.mCancellationSignalAttachCount;
            if (this.mCancellationSignalAttachCount == 1) {
                SQLiteConnection.nativeResetCancel(this.mConnectionPtr, true);
                cancellationSignal.setOnCancelListener(this);
            }
        }
    }

    private void bindArguments(PreparedStatement object, Object[] object2) {
        int n = object2 != null ? ((Object[])object2).length : 0;
        if (n == ((PreparedStatement)object).mNumParameters) {
            if (n == 0) {
                return;
            }
            long l = ((PreparedStatement)object).mStatementPtr;
            for (int i = 0; i < n; ++i) {
                object = object2[i];
                int n2 = DatabaseUtils.getTypeOfObject(object);
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 != 2) {
                            if (n2 != 4) {
                                if (object instanceof Boolean) {
                                    long l2 = this.mConnectionPtr;
                                    long l3 = (Boolean)object != false ? 1L : 0L;
                                    SQLiteConnection.nativeBindLong(l2, l, i + 1, l3);
                                    continue;
                                }
                                SQLiteConnection.nativeBindString(this.mConnectionPtr, l, i + 1, object.toString());
                                continue;
                            }
                            SQLiteConnection.nativeBindBlob(this.mConnectionPtr, l, i + 1, (byte[])object);
                            continue;
                        }
                        SQLiteConnection.nativeBindDouble(this.mConnectionPtr, l, i + 1, ((Number)object).doubleValue());
                        continue;
                    }
                    SQLiteConnection.nativeBindLong(this.mConnectionPtr, l, i + 1, ((Number)object).longValue());
                    continue;
                }
                SQLiteConnection.nativeBindNull(this.mConnectionPtr, l, i + 1);
            }
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Expected ");
        ((StringBuilder)object2).append(((PreparedStatement)object).mNumParameters);
        ((StringBuilder)object2).append(" bind arguments but ");
        ((StringBuilder)object2).append(n);
        ((StringBuilder)object2).append(" were provided.");
        throw new SQLiteBindOrColumnIndexOutOfRangeException(((StringBuilder)object2).toString());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static String canonicalizeSyncMode(String var0) {
        block5 : {
            switch (var0.hashCode()) {
                case 50: {
                    if (!var0.equals("2")) break;
                    var1_1 = 2;
                    break block5;
                }
                case 49: {
                    if (!var0.equals("1")) break;
                    var1_1 = 1;
                    break block5;
                }
                case 48: {
                    if (!var0.equals("0")) break;
                    var1_1 = 0;
                    break block5;
                }
            }
            ** break;
lbl15: // 1 sources:
            var1_1 = -1;
        }
        if (var1_1 == 0) return "OFF";
        if (var1_1 == 1) return "NORMAL";
        if (var1_1 == 2) return "FULL";
        return var0;
    }

    private void checkDatabaseWiped() {
        block7 : {
            boolean bl;
            boolean bl2;
            block6 : {
                if (!SQLiteGlobal.checkDbWipe()) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mConfiguration.path);
                stringBuilder.append("-wipecheck");
                File file = new File(stringBuilder.toString());
                bl2 = this.executeForLong("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='android_metadata'", null, null) > 0L;
                bl = file.exists();
                if (this.mIsReadOnlyConnection || bl) break block6;
                file.createNewFile();
            }
            if (bl2 || !bl) break block7;
            try {
                SQLiteDatabase.wipeDetected(this.mConfiguration.path, "unknown");
            }
            catch (IOException | RuntimeException exception) {
                SQLiteDatabase.wtfAsSystemServer(TAG, "Unexpected exception while checking for wipe", exception);
            }
        }
    }

    private void detachCancellationSignal(CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            --this.mCancellationSignalAttachCount;
            if (this.mCancellationSignalAttachCount == 0) {
                cancellationSignal.setOnCancelListener(null);
                SQLiteConnection.nativeResetCancel(this.mConnectionPtr, false);
            }
        }
    }

    private void dispose(boolean bl) {
        CloseGuard closeGuard = this.mCloseGuard;
        if (closeGuard != null) {
            if (bl) {
                closeGuard.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (this.mConnectionPtr != 0L) {
            int n = this.mRecentOperations.beginOperation("close", null, null);
            try {
                this.mPreparedStatementCache.evictAll();
                SQLiteConnection.nativeClose(this.mConnectionPtr);
                this.mConnectionPtr = 0L;
            }
            finally {
                this.mRecentOperations.endOperation(n);
            }
        }
    }

    private void finalizePreparedStatement(PreparedStatement preparedStatement) {
        SQLiteConnection.nativeFinalizeStatement(this.mConnectionPtr, preparedStatement.mStatementPtr);
        this.recyclePreparedStatement(preparedStatement);
    }

    private SQLiteDebug.DbStats getMainDbStatsUnsafe(int n, long l, long l2) {
        String string2 = this.mConfiguration.path;
        CharSequence charSequence = string2;
        if (!this.mIsPrimaryConnection) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string2);
            ((StringBuilder)charSequence).append(" (");
            ((StringBuilder)charSequence).append(this.mConnectionId);
            ((StringBuilder)charSequence).append(")");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return new SQLiteDebug.DbStats((String)charSequence, l, l2, n, this.mPreparedStatementCache.hitCount(), this.mPreparedStatementCache.missCount(), this.mPreparedStatementCache.size());
    }

    private static boolean isCacheable(int n) {
        return n == 2 || n == 1;
        {
        }
    }

    private void maybeTruncateWalFile() {
        long l = SQLiteGlobal.getWALTruncateSize();
        if (l == 0L) {
            return;
        }
        Serializable serializable = new StringBuilder();
        ((StringBuilder)serializable).append(this.mConfiguration.path);
        ((StringBuilder)serializable).append("-wal");
        serializable = new File(((StringBuilder)serializable).toString());
        if (!((File)serializable).isFile()) {
            return;
        }
        long l2 = ((File)serializable).length();
        if (l2 < l) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((File)serializable).getAbsolutePath());
        stringBuilder.append(" ");
        stringBuilder.append(l2);
        stringBuilder.append(" bytes: Bigger than ");
        stringBuilder.append(l);
        stringBuilder.append("; truncating");
        Log.i(TAG, stringBuilder.toString());
        try {
            this.executeForString("PRAGMA wal_checkpoint(TRUNCATE)", null, null);
        }
        catch (SQLiteException sQLiteException) {
            Log.w(TAG, "Failed to truncate the -wal file", sQLiteException);
        }
    }

    private static native void nativeBindBlob(long var0, long var2, int var4, byte[] var5);

    private static native void nativeBindDouble(long var0, long var2, int var4, double var5);

    private static native void nativeBindLong(long var0, long var2, int var4, long var5);

    private static native void nativeBindNull(long var0, long var2, int var4);

    private static native void nativeBindString(long var0, long var2, int var4, String var5);

    private static native void nativeCancel(long var0);

    private static native void nativeClose(long var0);

    private static native void nativeExecute(long var0, long var2);

    private static native int nativeExecuteForBlobFileDescriptor(long var0, long var2);

    private static native int nativeExecuteForChangedRowCount(long var0, long var2);

    private static native long nativeExecuteForCursorWindow(long var0, long var2, long var4, int var6, int var7, boolean var8);

    private static native long nativeExecuteForLastInsertedRowId(long var0, long var2);

    private static native long nativeExecuteForLong(long var0, long var2);

    private static native String nativeExecuteForString(long var0, long var2);

    private static native void nativeFinalizeStatement(long var0, long var2);

    private static native int nativeGetColumnCount(long var0, long var2);

    private static native String nativeGetColumnName(long var0, long var2, int var4);

    private static native int nativeGetDbLookaside(long var0);

    private static native int nativeGetParameterCount(long var0, long var2);

    private static native boolean nativeIsReadOnly(long var0, long var2);

    private static native long nativeOpen(String var0, int var1, String var2, boolean var3, boolean var4, int var5, int var6);

    private static native long nativePrepareStatement(long var0, String var2);

    private static native void nativeRegisterCustomFunction(long var0, SQLiteCustomFunction var2);

    private static native void nativeRegisterLocalizedCollators(long var0, String var2);

    private static native void nativeResetCancel(long var0, boolean var2);

    private static native void nativeResetStatementAndClearBindings(long var0, long var2);

    private PreparedStatement obtainPreparedStatement(String string2, long l, int n, int n2, boolean bl) {
        PreparedStatement preparedStatement = this.mPreparedStatementPool;
        if (preparedStatement != null) {
            this.mPreparedStatementPool = preparedStatement.mPoolNext;
            preparedStatement.mPoolNext = null;
            preparedStatement.mInCache = false;
        } else {
            preparedStatement = new PreparedStatement();
        }
        preparedStatement.mSql = string2;
        preparedStatement.mStatementPtr = l;
        preparedStatement.mNumParameters = n;
        preparedStatement.mType = n2;
        preparedStatement.mReadOnly = bl;
        return preparedStatement;
    }

    static SQLiteConnection open(SQLiteConnectionPool object, SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, int n, boolean bl) {
        object = new SQLiteConnection((SQLiteConnectionPool)object, sQLiteDatabaseConfiguration, n, bl);
        try {
            SQLiteConnection.super.open();
            return object;
        }
        catch (SQLiteException sQLiteException) {
            SQLiteConnection.super.dispose(false);
            throw sQLiteException;
        }
    }

    private void open() {
        int n = this.mRecentOperations.beginOperation("open", null, null);
        this.mConnectionPtr = SQLiteConnection.nativeOpen(this.mConfiguration.path, this.mConfiguration.openFlags, this.mConfiguration.label, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_STATEMENTS, SQLiteDebug.NoPreloadHolder.DEBUG_SQL_TIME, this.mConfiguration.lookasideSlotSize, this.mConfiguration.lookasideSlotCount);
        this.setPageSize();
        this.setForeignKeyModeFromConfiguration();
        this.setWalModeFromConfiguration();
        this.setJournalSizeLimit();
        this.setAutoCheckpointInterval();
        this.setLocaleFromConfiguration();
        int n2 = this.mConfiguration.customFunctions.size();
        for (n = 0; n < n2; ++n) {
            SQLiteCustomFunction sQLiteCustomFunction = this.mConfiguration.customFunctions.get(n);
            SQLiteConnection.nativeRegisterCustomFunction(this.mConnectionPtr, sQLiteCustomFunction);
        }
        return;
        finally {
            this.mRecentOperations.endOperation(n);
        }
    }

    private void recyclePreparedStatement(PreparedStatement preparedStatement) {
        preparedStatement.mSql = null;
        preparedStatement.mPoolNext = this.mPreparedStatementPool;
        this.mPreparedStatementPool = preparedStatement;
    }

    private void releasePreparedStatement(PreparedStatement preparedStatement) {
        preparedStatement.mInUse = false;
        if (preparedStatement.mInCache) {
            try {
                SQLiteConnection.nativeResetStatementAndClearBindings(this.mConnectionPtr, preparedStatement.mStatementPtr);
            }
            catch (SQLiteException sQLiteException) {
                this.mPreparedStatementCache.remove(preparedStatement.mSql);
            }
        } else {
            this.finalizePreparedStatement(preparedStatement);
        }
    }

    private void setAutoCheckpointInterval() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long l = SQLiteGlobal.getWALAutoCheckpoint();
            if (this.executeForLong("PRAGMA wal_autocheckpoint", null, null) != l) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PRAGMA wal_autocheckpoint=");
                stringBuilder.append(l);
                this.executeForLong(stringBuilder.toString(), null, null);
            }
        }
    }

    private void setForeignKeyModeFromConfiguration() {
        if (!this.mIsReadOnlyConnection) {
            long l = this.mConfiguration.foreignKeyConstraintsEnabled ? 1L : 0L;
            if (this.executeForLong("PRAGMA foreign_keys", null, null) != l) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PRAGMA foreign_keys=");
                stringBuilder.append(l);
                this.execute(stringBuilder.toString(), null, null);
            }
        }
    }

    private void setJournalMode(String string2) {
        String string3 = this.executeForString("PRAGMA journal_mode", null, null);
        if (!string3.equalsIgnoreCase(string2)) {
            StringBuilder stringBuilder;
            try {
                stringBuilder = new StringBuilder();
                stringBuilder.append("PRAGMA journal_mode=");
                stringBuilder.append(string2);
                boolean bl = this.executeForString(stringBuilder.toString(), null, null).equalsIgnoreCase(string2);
                if (bl) {
                    return;
                }
            }
            catch (SQLiteDatabaseLockedException sQLiteDatabaseLockedException) {
                // empty catch block
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Could not change the database journal mode of '");
            stringBuilder.append(this.mConfiguration.label);
            stringBuilder.append("' from '");
            stringBuilder.append(string3);
            stringBuilder.append("' to '");
            stringBuilder.append(string2);
            stringBuilder.append("' because the database is locked.  This usually means that there are other open connections to the database which prevents the database from enabling or disabling write-ahead logging mode.  Proceeding without changing the journal mode.");
            Log.w("SQLiteConnection", stringBuilder.toString());
        }
    }

    private void setJournalSizeLimit() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long l = SQLiteGlobal.getJournalSizeLimit();
            if (this.executeForLong("PRAGMA journal_size_limit", null, null) != l) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PRAGMA journal_size_limit=");
                stringBuilder.append(l);
                this.executeForLong(stringBuilder.toString(), null, null);
            }
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void setLocaleFromConfiguration() {
        String string2;
        CharSequence charSequence;
        String string3;
        block11 : {
            string2 = "COMMIT";
            if ((this.mConfiguration.openFlags & 16) != 0) {
                return;
            }
            string3 = this.mConfiguration.locale.toString();
            SQLiteConnection.nativeRegisterLocalizedCollators(this.mConnectionPtr, string3);
            if (!this.mConfiguration.isInMemoryDb()) {
                this.checkDatabaseWiped();
            }
            if (this.mIsReadOnlyConnection) {
                return;
            }
            this.execute("CREATE TABLE IF NOT EXISTS android_metadata (locale TEXT)", null, null);
            charSequence = this.executeForString("SELECT locale FROM android_metadata UNION SELECT NULL ORDER BY locale DESC LIMIT 1", null, null);
            if (charSequence != null && ((String)charSequence).equals(string3)) {
                return;
            }
            this.execute("BEGIN", null, null);
            this.execute("DELETE FROM android_metadata", null, null);
            this.execute("INSERT INTO android_metadata (locale) VALUES(?)", new Object[]{string3}, null);
            this.execute("REINDEX LOCALIZED", null, null);
            if (true) break block11;
            string2 = "ROLLBACK";
            {
                catch (Throwable throwable) {
                    if (!false) {
                        string2 = "ROLLBACK";
                    }
                    this.execute(string2, null, null);
                    throw throwable;
                }
            }
        }
        try {
            this.execute(string2, null, null);
            return;
        }
        catch (RuntimeException runtimeException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Failed to change locale for db '");
            ((StringBuilder)charSequence).append(this.mConfiguration.label);
            ((StringBuilder)charSequence).append("' to '");
            ((StringBuilder)charSequence).append(string3);
            ((StringBuilder)charSequence).append("'.");
            throw new SQLiteException(((StringBuilder)charSequence).toString(), runtimeException);
        }
        catch (SQLiteException sQLiteException) {
            throw sQLiteException;
        }
    }

    private void setPageSize() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            long l = SQLiteGlobal.getDefaultPageSize();
            if (this.executeForLong("PRAGMA page_size", null, null) != l) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PRAGMA page_size=");
                stringBuilder.append(l);
                this.execute(stringBuilder.toString(), null, null);
            }
        }
    }

    private void setSyncMode(String string2) {
        if (!SQLiteConnection.canonicalizeSyncMode(this.executeForString("PRAGMA synchronous", null, null)).equalsIgnoreCase(SQLiteConnection.canonicalizeSyncMode(string2))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PRAGMA synchronous=");
            stringBuilder.append(string2);
            this.execute(stringBuilder.toString(), null, null);
        }
    }

    private void setWalModeFromConfiguration() {
        if (!this.mConfiguration.isInMemoryDb() && !this.mIsReadOnlyConnection) {
            boolean bl = (this.mConfiguration.openFlags & 536870912) != 0;
            boolean bl2 = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            if (!bl && !bl2) {
                String string2 = this.mConfiguration.journalMode == null ? SQLiteGlobal.getDefaultJournalMode() : this.mConfiguration.journalMode;
                this.setJournalMode(string2);
                string2 = this.mConfiguration.syncMode == null ? SQLiteGlobal.getDefaultSyncMode() : this.mConfiguration.syncMode;
                this.setSyncMode(string2);
            } else {
                this.setJournalMode("WAL");
                if (this.mConfiguration.syncMode != null) {
                    this.setSyncMode(this.mConfiguration.syncMode);
                } else if (bl2) {
                    this.setSyncMode(SQLiteCompatibilityWalFlags.getWALSyncMode());
                } else {
                    this.setSyncMode(SQLiteGlobal.getWALSyncMode());
                }
                this.maybeTruncateWalFile();
            }
        }
    }

    private void throwIfStatementForbidden(PreparedStatement preparedStatement) {
        if (this.mOnlyAllowReadOnlyOperations && !preparedStatement.mReadOnly) {
            throw new SQLiteException("Cannot execute this statement because it might modify the database but the connection is read-only.");
        }
    }

    private static String trimSqlForDisplay(String string2) {
        return string2.replaceAll("[\\s]*\\n+[\\s]*", " ");
    }

    void close() {
        this.dispose(false);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        CursorWindow cursorWindow;
        block15 : {
            void var1_6;
            block14 : {
                long l;
                long l2;
                int n = SQLiteConnection.nativeGetDbLookaside(this.mConnectionPtr);
                long l3 = 0L;
                try {
                    l3 = l2 = this.executeForLong("PRAGMA page_count;", null, null);
                    l = this.executeForLong("PRAGMA page_size;", null, null);
                    l3 = l2;
                    l2 = l;
                }
                catch (SQLiteException sQLiteException) {
                    l2 = 0L;
                }
                arrayList.add(this.getMainDbStatsUnsafe(n, l3, l2));
                Object object = new CursorWindow("collectDbStats");
                cursorWindow = object;
                Object object2 = cursorWindow;
                Object object3 = cursorWindow;
                try {
                    this.executeForCursorWindow("PRAGMA database_list;", null, (CursorWindow)object, 0, 0, false, null);
                    n = 1;
                }
                catch (Throwable throwable) {
                    cursorWindow = object2;
                    break block14;
                }
                catch (SQLiteException sQLiteException) {
                    cursorWindow = object3;
                    break block15;
                }
                do {
                    object2 = cursorWindow;
                    object3 = cursorWindow;
                    int n2 = cursorWindow.getNumRows();
                    if (n >= n2) break block15;
                    try {
                        object2 = cursorWindow.getString(n, 1);
                        object = cursorWindow.getString(n, 2);
                        l2 = 0L;
                        l = 0L;
                        l3 = l2;
                        try {
                            long l4;
                            l3 = l2;
                            object3 = new StringBuilder();
                            l3 = l2;
                            ((StringBuilder)object3).append("PRAGMA ");
                            l3 = l2;
                            ((StringBuilder)object3).append((String)object2);
                            l3 = l2;
                            ((StringBuilder)object3).append(".page_count;");
                            l3 = l2;
                            l3 = l2 = this.executeForLong(((StringBuilder)object3).toString(), null, null);
                            l3 = l2;
                            object3 = new StringBuilder();
                            l3 = l2;
                            ((StringBuilder)object3).append("PRAGMA ");
                            l3 = l2;
                            ((StringBuilder)object3).append((String)object2);
                            l3 = l2;
                            ((StringBuilder)object3).append(".page_size;");
                            l3 = l2;
                            l3 = l4 = this.executeForLong(((StringBuilder)object3).toString(), null, null);
                            l = l2;
                            l2 = l3;
                        }
                        catch (SQLiteException sQLiteException) {
                            l2 = l;
                            l = l3;
                        }
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("  (attached) ");
                        ((StringBuilder)object3).append((String)object2);
                        object2 = object3 = ((StringBuilder)object3).toString();
                        if (!((String)object).isEmpty()) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append((String)object3);
                            ((StringBuilder)object2).append(": ");
                            ((StringBuilder)object2).append((String)object);
                            object2 = ((StringBuilder)object2).toString();
                        }
                        object3 = new SQLiteDebug.DbStats((String)object2, l, l2, 0, 0, 0, 0);
                        arrayList.add((SQLiteDebug.DbStats)object3);
                        ++n;
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                } while (true);
                catch (SQLiteException sQLiteException) {
                    break block15;
                }
            }
            cursorWindow.close();
            throw var1_6;
        }
        cursorWindow.close();
    }

    void collectDbStatsUnsafe(ArrayList<SQLiteDebug.DbStats> arrayList) {
        arrayList.add(this.getMainDbStatsUnsafe(0, 0L, 0L));
    }

    String describeCurrentOperationUnsafe() {
        return this.mRecentOperations.describeCurrentOperation();
    }

    public void dump(Printer printer, boolean bl) {
        this.dumpUnsafe(printer, bl);
    }

    void dumpUnsafe(Printer printer, boolean bl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Connection #");
        stringBuilder.append(this.mConnectionId);
        stringBuilder.append(":");
        printer.println(stringBuilder.toString());
        if (bl) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("  connectionPtr: 0x");
            stringBuilder.append(Long.toHexString(this.mConnectionPtr));
            printer.println(stringBuilder.toString());
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("  isPrimaryConnection: ");
        stringBuilder.append(this.mIsPrimaryConnection);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  onlyAllowReadOnlyOperations: ");
        stringBuilder.append(this.mOnlyAllowReadOnlyOperations);
        printer.println(stringBuilder.toString());
        this.mRecentOperations.dump(printer);
        if (bl) {
            this.mPreparedStatementCache.dump(printer);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void execute(String object, Object[] arrobject, CancellationSignal cancellationSignal) {
        Throwable throwable4222;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n = this.mRecentOperations.beginOperation("execute", (String)object, arrobject);
            object = this.acquirePreparedStatement((String)object);
            this.throwIfStatementForbidden((PreparedStatement)object);
            this.bindArguments((PreparedStatement)object, arrobject);
            this.applyBlockGuardPolicy((PreparedStatement)object);
            this.attachCancellationSignal(cancellationSignal);
            SQLiteConnection.nativeExecute(this.mConnectionPtr, ((PreparedStatement)object).mStatementPtr);
            this.detachCancellationSignal(cancellationSignal);
            this.releasePreparedStatement((PreparedStatement)object);
            this.mRecentOperations.endOperation(n);
            return;
            catch (Throwable throwable2) {
                try {
                    this.detachCancellationSignal(cancellationSignal);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        this.releasePreparedStatement((PreparedStatement)object);
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        this.mRecentOperations.failOperation(n, runtimeException);
                        throw runtimeException;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(n);
        throw throwable4222;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ParcelFileDescriptor executeForBlobFileDescriptor(String object, Object[] arrobject, CancellationSignal cancellationSignal) {
        Throwable throwable4222;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n = this.mRecentOperations.beginOperation("executeForBlobFileDescriptor", (String)object, arrobject);
            PreparedStatement preparedStatement = this.acquirePreparedStatement((String)object);
            this.throwIfStatementForbidden(preparedStatement);
            this.bindArguments(preparedStatement, arrobject);
            this.applyBlockGuardPolicy(preparedStatement);
            this.attachCancellationSignal(cancellationSignal);
            int n2 = SQLiteConnection.nativeExecuteForBlobFileDescriptor(this.mConnectionPtr, preparedStatement.mStatementPtr);
            object = n2 >= 0 ? ParcelFileDescriptor.adoptFd(n2) : null;
            this.detachCancellationSignal(cancellationSignal);
            this.releasePreparedStatement(preparedStatement);
            this.mRecentOperations.endOperation(n);
            return object;
            catch (Throwable throwable2) {
                try {
                    this.detachCancellationSignal(cancellationSignal);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        this.releasePreparedStatement(preparedStatement);
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        this.mRecentOperations.failOperation(n, runtimeException);
                        throw runtimeException;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(n);
        throw throwable4222;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int executeForChangedRowCount(String object, Object[] object2, CancellationSignal object3) {
        Throwable throwable4222;
        int n2;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n2 = 0;
            int n3 = 0;
            int n4 = 0;
            n = this.mRecentOperations.beginOperation("executeForChangedRowCount", (String)object, (Object[])object2);
            object = this.acquirePreparedStatement((String)object);
            int n5 = n4;
            this.throwIfStatementForbidden((PreparedStatement)object);
            n5 = n4;
            this.bindArguments((PreparedStatement)object, (Object[])object2);
            n5 = n4;
            this.applyBlockGuardPolicy((PreparedStatement)object);
            n5 = n4;
            this.attachCancellationSignal((CancellationSignal)object3);
            n5 = n4 = (n2 = SQLiteConnection.nativeExecuteForChangedRowCount(this.mConnectionPtr, ((PreparedStatement)object).mStatementPtr));
            this.detachCancellationSignal((CancellationSignal)object3);
            n2 = n4;
            n3 = n4;
            this.releasePreparedStatement((PreparedStatement)object);
            if (!this.mRecentOperations.endOperationDeferLog(n)) return n4;
            object2 = this.mRecentOperations;
            object = new StringBuilder();
            ((StringBuilder)object).append("changedRows=");
            ((StringBuilder)object).append(n4);
            ((OperationLog)object2).logOperation(n, ((StringBuilder)object).toString());
            return n4;
            catch (Throwable throwable2) {
                n5 = n4;
                try {
                    this.detachCancellationSignal((CancellationSignal)object3);
                    n5 = n4;
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    n2 = n5;
                    n3 = n5;
                    try {
                        this.releasePreparedStatement((PreparedStatement)object);
                        n2 = n5;
                        n3 = n5;
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        n2 = n3;
                        this.mRecentOperations.failOperation(n, runtimeException);
                        n2 = n3;
                        throw runtimeException;
                    }
                }
            }
        }
        if (!this.mRecentOperations.endOperationDeferLog(n)) throw throwable4222;
        object = this.mRecentOperations;
        object3 = new StringBuilder();
        ((StringBuilder)object3).append("changedRows=");
        ((StringBuilder)object3).append(n2);
        ((OperationLog)object).logOperation(n, ((StringBuilder)object3).toString());
        throw throwable4222;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int executeForCursorWindow(String object, Object[] object2, CursorWindow cursorWindow, int n, int n2, boolean bl, CancellationSignal object3) {
        block38 : {
            block34 : {
                block35 : {
                    block36 : {
                        block37 : {
                            block33 : {
                                if (object == null) throw new IllegalArgumentException("sql must not be null.");
                                if (var3_24 == null) throw new IllegalArgumentException("window must not be null.");
                                var3_24.acquireReference();
                                var8_29 = -1;
                                var9_30 = -1;
                                var10_31 = -1;
                                var11_32 = this.mRecentOperations.beginOperation("executeForCursorWindow", (String)object, (Object[])var2_23);
                                var12_33 = this.acquirePreparedStatement((String)object);
                                this.throwIfStatementForbidden(var12_33);
                                this.bindArguments(var12_33, (Object[])var2_23);
                                this.applyBlockGuardPolicy(var12_33);
                                this.attachCancellationSignal((CancellationSignal)var7_28);
                                var13_34 = this.mConnectionPtr;
                                var15_35 = var12_33.mStatementPtr;
                                var17_36 = var3_24.mWindowPtr;
                                var15_35 = SQLiteConnection.nativeExecuteForCursorWindow(var13_34, var15_35, var17_36, (int)var4_25, var5_26, (boolean)var6_27);
                                var19_37 = (int)(var15_35 >> 32);
                                var9_30 = (int)var15_35;
                                var5_26 = var3_24.getNumRows();
                                var3_24.setStartPosition(var19_37);
                                this.detachCancellationSignal((CancellationSignal)var7_28);
                                this.releasePreparedStatement(var12_33);
                                if (!this.mRecentOperations.endOperationDeferLog(var11_32)) break block33;
                                object = this.mRecentOperations;
                                var2_23 = new StringBuilder();
                                var2_23.append("window='");
                                var2_23.append(var3_24);
                                var2_23.append("', startPos=");
                                try {
                                    var2_23.append((int)var4_25);
                                    var2_23.append(", actualPos=");
                                    var2_23.append(var19_37);
                                    var2_23.append(", filledRows=");
                                    var2_23.append(var5_26);
                                    var2_23.append(", countedRows=");
                                    var2_23.append(var9_30);
                                    object.logOperation(var11_32, var2_23.toString());
                                }
                                catch (Throwable throwable) {}
                            }
                            var3_24.releaseReference();
                            return var9_30;
                            catch (Throwable throwable) {}
                            break block38;
                            catch (Throwable throwable) {
                                break block34;
                            }
                            catch (RuntimeException runtimeException) {
                                var10_31 = var19_37;
                                var19_37 = var5_26;
                                var5_26 = var10_31;
                                ** GOTO lbl127
                            }
                            catch (Throwable throwable) {
                                var10_31 = var19_37;
                                var19_37 = var5_26;
                                var5_26 = var10_31;
                                break block35;
                            }
                            catch (Throwable throwable) {
                                var10_31 = var19_37;
                                var19_37 = var5_26;
                                var5_26 = var10_31;
                                break block36;
                            }
                            catch (Throwable throwable) {
                                var5_26 = var19_37;
                                var19_37 = var10_31;
                                break block36;
                            }
                            catch (Throwable throwable) {
                                var5_26 = var8_29;
                                var19_37 = var10_31;
                                break block36;
                            }
                            catch (Throwable throwable) {
                                break block37;
                            }
                            catch (Throwable throwable) {
                                // empty catch block
                            }
                        }
                        var19_37 = var10_31;
                        var5_26 = var8_29;
                    }
                    try {
                        this.detachCancellationSignal((CancellationSignal)var7_28);
                        throw var1_11;
                    }
                    catch (Throwable throwable) {}
                    break block35;
                    catch (Throwable throwable) {
                        var19_37 = var10_31;
                        var5_26 = var8_29;
                    }
                }
                var20_38 = var5_26;
                var8_29 = var9_30;
                var10_31 = var19_37;
                try {
                    block39 : {
                        try {
                            this.releasePreparedStatement(var12_33);
                            var20_38 = var5_26;
                            var8_29 = var9_30;
                            var10_31 = var19_37;
                            throw var1_14;
                        }
                        catch (RuntimeException runtimeException) {}
                        break block39;
                        catch (Throwable throwable) {
                            var19_37 = -1;
                            var9_30 = -1;
                            var5_26 = -1;
                            break block34;
                        }
                        catch (RuntimeException runtimeException) {
                            var19_37 = var10_31;
                            var5_26 = var8_29;
                        }
                    }
                    var20_38 = var5_26;
                    var8_29 = var9_30;
                    var10_31 = var19_37;
                    this.mRecentOperations.failOperation(var11_32, (Exception)throwable);
                    var20_38 = var5_26;
                    var8_29 = var9_30;
                    var10_31 = var19_37;
                    throw throwable;
                }
                catch (Throwable throwable) {
                    var19_37 = var20_38;
                    var9_30 = var8_29;
                    var5_26 = var10_31;
                }
            }
            if (this.mRecentOperations.endOperationDeferLog(var11_32) == false) throw object;
            var7_28 = this.mRecentOperations;
            var2_23 = new StringBuilder();
            var2_23.append("window='");
            var2_23.append(var3_24);
            var2_23.append("', startPos=");
            var2_23.append((int)var4_25);
            var2_23.append(", actualPos=");
            var2_23.append(var19_37);
            var2_23.append(", filledRows=");
            var2_23.append(var5_26);
            var2_23.append(", countedRows=");
            var2_23.append(var9_30);
            var7_28.logOperation(var11_32, var2_23.toString());
            throw object;
            break block38;
            catch (Throwable var1_21) {
                // empty catch block
            }
        }
        var3_24.releaseReference();
        throw var1_22;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long executeForLastInsertedRowId(String object, Object[] arrobject, CancellationSignal cancellationSignal) {
        Throwable throwable4222;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n = this.mRecentOperations.beginOperation("executeForLastInsertedRowId", (String)object, arrobject);
            object = this.acquirePreparedStatement((String)object);
            this.throwIfStatementForbidden((PreparedStatement)object);
            this.bindArguments((PreparedStatement)object, arrobject);
            this.applyBlockGuardPolicy((PreparedStatement)object);
            this.attachCancellationSignal(cancellationSignal);
            long l = SQLiteConnection.nativeExecuteForLastInsertedRowId(this.mConnectionPtr, ((PreparedStatement)object).mStatementPtr);
            this.detachCancellationSignal(cancellationSignal);
            this.releasePreparedStatement((PreparedStatement)object);
            this.mRecentOperations.endOperation(n);
            return l;
            catch (Throwable throwable2) {
                try {
                    this.detachCancellationSignal(cancellationSignal);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        this.releasePreparedStatement((PreparedStatement)object);
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        this.mRecentOperations.failOperation(n, runtimeException);
                        throw runtimeException;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(n);
        throw throwable4222;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long executeForLong(String object, Object[] arrobject, CancellationSignal cancellationSignal) {
        Throwable throwable4222;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n = this.mRecentOperations.beginOperation("executeForLong", (String)object, arrobject);
            object = this.acquirePreparedStatement((String)object);
            this.throwIfStatementForbidden((PreparedStatement)object);
            this.bindArguments((PreparedStatement)object, arrobject);
            this.applyBlockGuardPolicy((PreparedStatement)object);
            this.attachCancellationSignal(cancellationSignal);
            long l = SQLiteConnection.nativeExecuteForLong(this.mConnectionPtr, ((PreparedStatement)object).mStatementPtr);
            this.mRecentOperations.setResult(l);
            this.detachCancellationSignal(cancellationSignal);
            this.releasePreparedStatement((PreparedStatement)object);
            this.mRecentOperations.endOperation(n);
            return l;
            catch (Throwable throwable2) {
                try {
                    this.detachCancellationSignal(cancellationSignal);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        this.releasePreparedStatement((PreparedStatement)object);
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        this.mRecentOperations.failOperation(n, runtimeException);
                        throw runtimeException;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(n);
        throw throwable4222;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String executeForString(String object, Object[] object2, CancellationSignal cancellationSignal) {
        Throwable throwable4222;
        int n;
        block12 : {
            if (object == null) throw new IllegalArgumentException("sql must not be null.");
            n = this.mRecentOperations.beginOperation("executeForString", (String)object, (Object[])object2);
            object = this.acquirePreparedStatement((String)object);
            this.throwIfStatementForbidden((PreparedStatement)object);
            this.bindArguments((PreparedStatement)object, (Object[])object2);
            this.applyBlockGuardPolicy((PreparedStatement)object);
            this.attachCancellationSignal(cancellationSignal);
            object2 = SQLiteConnection.nativeExecuteForString(this.mConnectionPtr, ((PreparedStatement)object).mStatementPtr);
            this.mRecentOperations.setResult((String)object2);
            this.detachCancellationSignal(cancellationSignal);
            this.releasePreparedStatement((PreparedStatement)object);
            this.mRecentOperations.endOperation(n);
            return object2;
            catch (Throwable throwable2) {
                try {
                    this.detachCancellationSignal(cancellationSignal);
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        this.releasePreparedStatement((PreparedStatement)object);
                        throw throwable3;
                    }
                    catch (Throwable throwable4222) {
                        break block12;
                    }
                    catch (RuntimeException runtimeException) {
                        this.mRecentOperations.failOperation(n, runtimeException);
                        throw runtimeException;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(n);
        throw throwable4222;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mPool != null && this.mConnectionPtr != 0L) {
                this.mPool.onConnectionLeaked();
            }
            this.dispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    boolean isPreparedStatementInCache(String string2) {
        boolean bl = this.mPreparedStatementCache.get(string2) != null;
        return bl;
    }

    public boolean isPrimaryConnection() {
        return this.mIsPrimaryConnection;
    }

    @Override
    public void onCancel() {
        SQLiteConnection.nativeCancel(this.mConnectionPtr);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void prepare(String var1_1, SQLiteStatementInfo var2_4) {
        block8 : {
            if (var1_1 == null) throw new IllegalArgumentException("sql must not be null.");
            var3_6 = this.mRecentOperations.beginOperation("prepare", (String)var1_1, null);
            var1_1 = this.acquirePreparedStatement((String)var1_1);
            if (var2_4 == null) ** GOTO lbl23
            try {
                block9 : {
                    var2_4.numParameters = var1_1.mNumParameters;
                    var2_4.readOnly = var1_1.mReadOnly;
                    var4_7 = SQLiteConnection.nativeGetColumnCount(this.mConnectionPtr, var1_1.mStatementPtr);
                    if (var4_7 != 0) break block9;
                    var2_4.columnNames = SQLiteConnection.EMPTY_STRING_ARRAY;
                    ** GOTO lbl23
                }
                var2_4.columnNames = new String[var4_7];
                ** GOTO lbl20
            }
            catch (Throwable var2_5) {
                this.releasePreparedStatement((PreparedStatement)var1_1);
                throw var2_5;
lbl20: // 2 sources:
                for (var5_8 = 0; var5_8 < var4_7; ++var5_8) {
                    var2_4.columnNames[var5_8] = SQLiteConnection.nativeGetColumnName(this.mConnectionPtr, var1_1.mStatementPtr, var5_8);
                }
lbl23: // 3 sources:
                this.releasePreparedStatement((PreparedStatement)var1_1);
                this.mRecentOperations.endOperation(var3_6);
                return;
                {
                    catch (Throwable var1_2) {
                        break block8;
                    }
                    catch (RuntimeException var1_3) {}
                    {
                        this.mRecentOperations.failOperation(var3_6, var1_3);
                        throw var1_3;
                    }
                }
            }
        }
        this.mRecentOperations.endOperation(var3_6);
        throw var1_2;
    }

    void reconfigure(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        int n;
        boolean bl = false;
        this.mOnlyAllowReadOnlyOperations = false;
        int n2 = sQLiteDatabaseConfiguration.customFunctions.size();
        for (n = 0; n < n2; ++n) {
            SQLiteCustomFunction sQLiteCustomFunction = sQLiteDatabaseConfiguration.customFunctions.get(n);
            if (this.mConfiguration.customFunctions.contains(sQLiteCustomFunction)) continue;
            SQLiteConnection.nativeRegisterCustomFunction(this.mConnectionPtr, sQLiteCustomFunction);
        }
        n = sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled ? 1 : 0;
        if (((sQLiteDatabaseConfiguration.openFlags ^ this.mConfiguration.openFlags) & -1610612736) != 0) {
            bl = true;
        }
        boolean bl2 = sQLiteDatabaseConfiguration.locale.equals(this.mConfiguration.locale);
        this.mConfiguration.updateParametersFrom(sQLiteDatabaseConfiguration);
        this.mPreparedStatementCache.resize(sQLiteDatabaseConfiguration.maxSqlCacheSize);
        if (n != 0) {
            this.setForeignKeyModeFromConfiguration();
        }
        if (bl) {
            this.setWalModeFromConfiguration();
        }
        if (bl2 ^ true) {
            this.setLocaleFromConfiguration();
        }
    }

    void setOnlyAllowReadOnlyOperations(boolean bl) {
        this.mOnlyAllowReadOnlyOperations = bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLiteConnection: ");
        stringBuilder.append(this.mConfiguration.path);
        stringBuilder.append(" (");
        stringBuilder.append(this.mConnectionId);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private static final class Operation {
        private static final int MAX_TRACE_METHOD_NAME_LEN = 256;
        public ArrayList<Object> mBindArgs;
        public int mCookie;
        public long mEndTime;
        public Exception mException;
        public boolean mFinished;
        public String mKind;
        public String mPath;
        public long mResultLong;
        public String mResultString;
        public String mSql;
        public long mStartTime;
        public long mStartWallTime;

        private Operation() {
        }

        private String getStatus() {
            if (!this.mFinished) {
                return "running";
            }
            String string2 = this.mException != null ? "failed" : "succeeded";
            return string2;
        }

        private String getTraceMethodName() {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mKind);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(this.mSql);
            charSequence = ((StringBuilder)charSequence).toString();
            if (((String)charSequence).length() > 256) {
                return ((String)charSequence).substring(0, 256);
            }
            return charSequence;
        }

        public void describe(StringBuilder stringBuilder, boolean bl) {
            Object object;
            int n;
            stringBuilder.append(this.mKind);
            if (this.mFinished) {
                stringBuilder.append(" took ");
                stringBuilder.append(this.mEndTime - this.mStartTime);
                stringBuilder.append("ms");
            } else {
                stringBuilder.append(" started ");
                stringBuilder.append(System.currentTimeMillis() - this.mStartWallTime);
                stringBuilder.append("ms ago");
            }
            stringBuilder.append(" - ");
            stringBuilder.append(this.getStatus());
            if (this.mSql != null) {
                stringBuilder.append(", sql=\"");
                stringBuilder.append(SQLiteConnection.trimSqlForDisplay(this.mSql));
                stringBuilder.append("\"");
            }
            if ((n = bl && SQLiteDebug.NoPreloadHolder.DEBUG_LOG_DETAILED && (object = this.mBindArgs) != null && ((ArrayList)object).size() != 0 ? 1 : 0) != 0) {
                stringBuilder.append(", bindArgs=[");
                int n2 = this.mBindArgs.size();
                for (n = 0; n < n2; ++n) {
                    object = this.mBindArgs.get(n);
                    if (n != 0) {
                        stringBuilder.append(", ");
                    }
                    if (object == null) {
                        stringBuilder.append("null");
                        continue;
                    }
                    if (object instanceof byte[]) {
                        stringBuilder.append("<byte[]>");
                        continue;
                    }
                    if (object instanceof String) {
                        stringBuilder.append("\"");
                        stringBuilder.append((String)object);
                        stringBuilder.append("\"");
                        continue;
                    }
                    stringBuilder.append(object);
                }
                stringBuilder.append("]");
            }
            stringBuilder.append(", path=");
            stringBuilder.append(this.mPath);
            if (this.mException != null) {
                stringBuilder.append(", exception=\"");
                stringBuilder.append(this.mException.getMessage());
                stringBuilder.append("\"");
            }
            if (this.mResultLong != Long.MIN_VALUE) {
                stringBuilder.append(", result=");
                stringBuilder.append(this.mResultLong);
            }
            if (this.mResultString != null) {
                stringBuilder.append(", result=\"");
                stringBuilder.append(this.mResultString);
                stringBuilder.append("\"");
            }
        }
    }

    private static final class OperationLog {
        private static final int COOKIE_GENERATION_SHIFT = 8;
        private static final int COOKIE_INDEX_MASK = 255;
        private static final int MAX_RECENT_OPERATIONS = 20;
        private int mGeneration;
        private int mIndex;
        private final Operation[] mOperations = new Operation[20];
        private final SQLiteConnectionPool mPool;
        private long mResultLong = Long.MIN_VALUE;
        private String mResultString;

        OperationLog(SQLiteConnectionPool sQLiteConnectionPool) {
            this.mPool = sQLiteConnectionPool;
        }

        private boolean endOperationDeferLogLocked(int n) {
            Operation operation = this.getOperationLocked(n);
            boolean bl = false;
            if (operation != null) {
                if (Trace.isTagEnabled(0x100000L)) {
                    Trace.asyncTraceEnd(0x100000L, operation.getTraceMethodName(), operation.mCookie);
                }
                operation.mEndTime = SystemClock.uptimeMillis();
                operation.mFinished = true;
                long l = operation.mEndTime - operation.mStartTime;
                this.mPool.onStatementExecuted(l);
                boolean bl2 = bl;
                if (SQLiteDebug.NoPreloadHolder.DEBUG_LOG_SLOW_QUERIES) {
                    bl2 = bl;
                    if (SQLiteDebug.shouldLogSlowQuery(l)) {
                        bl2 = true;
                    }
                }
                return bl2;
            }
            return false;
        }

        private Operation getOperationLocked(int n) {
            Operation operation = this.mOperations[n & 255];
            if (operation.mCookie != n) {
                operation = null;
            }
            return operation;
        }

        private void logOperationLocked(int n, String string2) {
            Operation operation = this.getOperationLocked(n);
            operation.mResultLong = this.mResultLong;
            operation.mResultString = this.mResultString;
            StringBuilder stringBuilder = new StringBuilder();
            operation.describe(stringBuilder, true);
            if (string2 != null) {
                stringBuilder.append(", ");
                stringBuilder.append(string2);
            }
            Log.d(SQLiteConnection.TAG, stringBuilder.toString());
        }

        private int newOperationCookieLocked(int n) {
            int n2 = this.mGeneration;
            this.mGeneration = n2 + 1;
            return n2 << 8 | n;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public int beginOperation(String object, String string2, Object[] arrobject) {
            this.mResultLong = Long.MIN_VALUE;
            this.mResultString = null;
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                Operation operation;
                int n = (this.mIndex + 1) % 20;
                Operation operation2 = this.mOperations[n];
                if (operation2 == null) {
                    this.mOperations[n] = operation = new Operation();
                } else {
                    operation2.mFinished = false;
                    operation2.mException = null;
                    operation = operation2;
                    if (operation2.mBindArgs != null) {
                        operation2.mBindArgs.clear();
                        operation = operation2;
                    }
                }
                operation.mStartWallTime = System.currentTimeMillis();
                operation.mStartTime = SystemClock.uptimeMillis();
                operation.mKind = object;
                operation.mSql = string2;
                operation.mPath = this.mPool.getPath();
                operation.mResultLong = Long.MIN_VALUE;
                operation.mResultString = null;
                if (arrobject != null) {
                    if (operation.mBindArgs == null) {
                        object = new ArrayList();
                        operation.mBindArgs = object;
                    } else {
                        operation.mBindArgs.clear();
                    }
                    for (int i = 0; i < arrobject.length; ++i) {
                        object = arrobject[i];
                        if (object != null && object instanceof byte[]) {
                            operation.mBindArgs.add(EMPTY_BYTE_ARRAY);
                            continue;
                        }
                        operation.mBindArgs.add(object);
                    }
                }
                operation.mCookie = this.newOperationCookieLocked(n);
                if (Trace.isTagEnabled(0x100000L)) {
                    Trace.asyncTraceBegin(0x100000L, operation.getTraceMethodName(), operation.mCookie);
                }
                this.mIndex = n;
                return operation.mCookie;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String describeCurrentOperation() {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                Operation operation = this.mOperations[this.mIndex];
                if (operation == null) return null;
                if (operation.mFinished) return null;
                CharSequence charSequence = new StringBuilder();
                operation.describe((StringBuilder)charSequence, false);
                return charSequence.toString();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void dump(Printer printer) {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                printer.println("  Most recently executed operations:");
                int n = this.mIndex;
                Operation operation = this.mOperations[n];
                if (operation == null) {
                    printer.println("    <none>");
                } else {
                    int n2;
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    int n3 = 0;
                    do {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("    ");
                        stringBuilder.append(n3);
                        stringBuilder.append(": [");
                        Date date = new Date(operation.mStartWallTime);
                        stringBuilder.append(simpleDateFormat.format(date));
                        stringBuilder.append("] ");
                        operation.describe(stringBuilder, false);
                        printer.println(stringBuilder.toString());
                        n = n > 0 ? --n : 19;
                        n2 = n3 + 1;
                        operation = this.mOperations[n];
                        if (operation == null) break;
                        n3 = n2;
                    } while (n2 < 20);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void endOperation(int n) {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                if (this.endOperationDeferLogLocked(n)) {
                    this.logOperationLocked(n, null);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean endOperationDeferLog(int n) {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                return this.endOperationDeferLogLocked(n);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void failOperation(int n, Exception exception) {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                Operation operation = this.getOperationLocked(n);
                if (operation != null) {
                    operation.mException = exception;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void logOperation(int n, String string2) {
            Operation[] arroperation = this.mOperations;
            synchronized (arroperation) {
                this.logOperationLocked(n, string2);
                return;
            }
        }

        public void setResult(long l) {
            this.mResultLong = l;
        }

        public void setResult(String string2) {
            this.mResultString = string2;
        }
    }

    private static final class PreparedStatement {
        public boolean mInCache;
        public boolean mInUse;
        public int mNumParameters;
        public PreparedStatement mPoolNext;
        public boolean mReadOnly;
        public String mSql;
        public long mStatementPtr;
        public int mType;

        private PreparedStatement() {
        }
    }

    private final class PreparedStatementCache
    extends LruCache<String, PreparedStatement> {
        public PreparedStatementCache(int n) {
            super(n);
        }

        public void dump(Printer printer) {
            printer.println("  Prepared statement cache:");
            Map map = this.snapshot();
            if (!map.isEmpty()) {
                int n = 0;
                for (Map.Entry entry : map.entrySet()) {
                    PreparedStatement preparedStatement = (PreparedStatement)entry.getValue();
                    if (preparedStatement.mInCache) {
                        String string2 = (String)entry.getKey();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("    ");
                        stringBuilder.append(n);
                        stringBuilder.append(": statementPtr=0x");
                        stringBuilder.append(Long.toHexString(preparedStatement.mStatementPtr));
                        stringBuilder.append(", numParameters=");
                        stringBuilder.append(preparedStatement.mNumParameters);
                        stringBuilder.append(", type=");
                        stringBuilder.append(preparedStatement.mType);
                        stringBuilder.append(", readOnly=");
                        stringBuilder.append(preparedStatement.mReadOnly);
                        stringBuilder.append(", sql=\"");
                        stringBuilder.append(SQLiteConnection.trimSqlForDisplay(string2));
                        stringBuilder.append("\"");
                        printer.println(stringBuilder.toString());
                    }
                    ++n;
                }
            } else {
                printer.println("    <none>");
            }
        }

        @Override
        protected void entryRemoved(boolean bl, String string2, PreparedStatement preparedStatement, PreparedStatement preparedStatement2) {
            preparedStatement.mInCache = false;
            if (!preparedStatement.mInUse) {
                SQLiteConnection.this.finalizePreparedStatement(preparedStatement);
            }
        }
    }

}

