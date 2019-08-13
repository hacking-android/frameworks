/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.database.sqlite.-$
 *  android.database.sqlite.-$$Lambda
 *  android.database.sqlite.-$$Lambda$SQLiteDatabase
 *  android.database.sqlite.-$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE
 *  dalvik.system.CloseGuard
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.DefaultDatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.-$;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteCompatibilityWalFlags;
import android.database.sqlite.SQLiteConnectionPool;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteCustomFunction;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.SQLiteDirectCursorDriver;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteGlobal;
import android.database.sqlite.SQLiteQuery;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteSession;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteStatementInfo;
import android.database.sqlite.SQLiteTransactionListener;
import android.database.sqlite._$$Lambda$RBWjWVyGrOTsQrLCYzJ_G8Uk25Q;
import android.database.sqlite._$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE;
import android.os.CancellationSignal;
import android.os.Looper;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.Log;
import android.util.Pair;
import android.util.Printer;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public final class SQLiteDatabase
extends SQLiteClosable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONFLICT_ABORT = 2;
    public static final int CONFLICT_FAIL = 3;
    public static final int CONFLICT_IGNORE = 4;
    public static final int CONFLICT_NONE = 0;
    public static final int CONFLICT_REPLACE = 5;
    public static final int CONFLICT_ROLLBACK = 1;
    @UnsupportedAppUsage
    public static final String[] CONFLICT_VALUES;
    public static final int CREATE_IF_NECESSARY = 268435456;
    private static final boolean DEBUG_CLOSE_IDLE_CONNECTIONS;
    public static final int ENABLE_LEGACY_COMPATIBILITY_WAL = Integer.MIN_VALUE;
    public static final int ENABLE_WRITE_AHEAD_LOGGING = 536870912;
    private static final int EVENT_DB_CORRUPT = 75004;
    public static final int MAX_SQL_CACHE_SIZE = 100;
    public static final int NO_LOCALIZED_COLLATORS = 16;
    public static final int OPEN_READONLY = 1;
    public static final int OPEN_READWRITE = 0;
    private static final int OPEN_READ_MASK = 1;
    public static final int SQLITE_MAX_LIKE_PATTERN_LENGTH = 50000;
    private static final String TAG = "SQLiteDatabase";
    private static WeakHashMap<SQLiteDatabase, Object> sActiveDatabases;
    private final CloseGuard mCloseGuardLocked = CloseGuard.get();
    @UnsupportedAppUsage
    private final SQLiteDatabaseConfiguration mConfigurationLocked;
    @UnsupportedAppUsage
    private SQLiteConnectionPool mConnectionPoolLocked;
    private final CursorFactory mCursorFactory;
    private final DatabaseErrorHandler mErrorHandler;
    private boolean mHasAttachedDbsLocked;
    private final Object mLock = new Object();
    @UnsupportedAppUsage
    private final ThreadLocal<SQLiteSession> mThreadSession = ThreadLocal.withInitial(new _$$Lambda$RBWjWVyGrOTsQrLCYzJ_G8Uk25Q(this));

    static {
        DEBUG_CLOSE_IDLE_CONNECTIONS = SystemProperties.getBoolean("persist.debug.sqlite.close_idle_connections", false);
        sActiveDatabases = new WeakHashMap();
        CONFLICT_VALUES = new String[]{"", " OR ROLLBACK ", " OR ABORT ", " OR FAIL ", " OR IGNORE ", " OR REPLACE "};
    }

    private SQLiteDatabase(String object, int n, CursorFactory object2, DatabaseErrorHandler databaseErrorHandler, int n2, int n3, long l, String string2, String string3) {
        long l2;
        this.mCursorFactory = object2;
        object2 = databaseErrorHandler != null ? databaseErrorHandler : new DefaultDatabaseErrorHandler();
        this.mErrorHandler = object2;
        this.mConfigurationLocked = new SQLiteDatabaseConfiguration((String)object, n);
        object = this.mConfigurationLocked;
        ((SQLiteDatabaseConfiguration)object).lookasideSlotSize = n2;
        ((SQLiteDatabaseConfiguration)object).lookasideSlotCount = n3;
        if (ActivityManager.isLowRamDeviceStatic()) {
            object = this.mConfigurationLocked;
            ((SQLiteDatabaseConfiguration)object).lookasideSlotCount = 0;
            ((SQLiteDatabaseConfiguration)object).lookasideSlotSize = 0;
        }
        long l3 = l2 = Long.MAX_VALUE;
        if (!this.mConfigurationLocked.isInMemoryDb()) {
            if (l >= 0L) {
                l3 = l;
            } else {
                l3 = l2;
                if (DEBUG_CLOSE_IDLE_CONNECTIONS) {
                    l3 = SQLiteGlobal.getIdleConnectionTimeout();
                }
            }
        }
        object = this.mConfigurationLocked;
        ((SQLiteDatabaseConfiguration)object).idleConnectionTimeoutMs = l3;
        ((SQLiteDatabaseConfiguration)object).journalMode = string2;
        ((SQLiteDatabaseConfiguration)object).syncMode = string3;
        if (SQLiteCompatibilityWalFlags.isLegacyCompatibilityWalEnabled()) {
            object = this.mConfigurationLocked;
            ((SQLiteDatabaseConfiguration)object).openFlags |= Integer.MIN_VALUE;
        }
    }

    @UnsupportedAppUsage
    private void beginTransaction(SQLiteTransactionListener sQLiteTransactionListener, boolean bl) {
        this.acquireReference();
        SQLiteSession sQLiteSession = this.getThreadSession();
        int n = bl ? 2 : 1;
        try {
            sQLiteSession.beginTransaction(n, sQLiteTransactionListener, this.getThreadDefaultConnectionFlags(false), null);
            return;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConnectionPoolLocked != null) {
                this.mConnectionPoolLocked.collectDbStats(arrayList);
            }
            return;
        }
    }

    public static SQLiteDatabase create(CursorFactory cursorFactory) {
        return SQLiteDatabase.openDatabase(":memory:", cursorFactory, 268435456);
    }

    public static SQLiteDatabase createInMemory(OpenParams openParams) {
        return SQLiteDatabase.openDatabase(":memory:", openParams.toBuilder().addOpenFlags(268435456).build());
    }

    public static boolean deleteDatabase(File file) {
        return SQLiteDatabase.deleteDatabase(file, true);
    }

    public static boolean deleteDatabase(File arrfile, boolean bl) {
        if (arrfile != null) {
            bl = arrfile.delete();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(arrfile.getPath());
            stringBuilder.append("-journal");
            boolean bl2 = new File(stringBuilder.toString()).delete();
            stringBuilder = new StringBuilder();
            stringBuilder.append(arrfile.getPath());
            stringBuilder.append("-shm");
            boolean bl3 = new File(stringBuilder.toString()).delete();
            stringBuilder = new StringBuilder();
            stringBuilder.append(arrfile.getPath());
            stringBuilder.append("-wal");
            bl = false | bl | bl2 | bl3 | new File(stringBuilder.toString()).delete();
            stringBuilder = new StringBuilder();
            stringBuilder.append(arrfile.getPath());
            stringBuilder.append("-wipecheck");
            new File(stringBuilder.toString()).delete();
            File file = arrfile.getParentFile();
            bl2 = bl;
            if (file != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(arrfile.getName());
                stringBuilder.append("-mj");
                arrfile = file.listFiles(new FileFilter(){

                    @Override
                    public boolean accept(File file) {
                        return file.getName().startsWith(String.this);
                    }
                });
                bl2 = bl;
                if (arrfile != null) {
                    int n = arrfile.length;
                    int n2 = 0;
                    do {
                        bl2 = bl;
                        if (n2 >= n) break;
                        bl |= arrfile[n2].delete();
                        ++n2;
                    } while (true);
                }
            }
            return bl2;
        }
        throw new IllegalArgumentException("file must not be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispose(boolean bl) {
        SQLiteConnectionPool sQLiteConnectionPool;
        WeakHashMap<SQLiteDatabase, Object> weakHashMap = this.mLock;
        synchronized (weakHashMap) {
            if (this.mCloseGuardLocked != null) {
                if (bl) {
                    this.mCloseGuardLocked.warnIfOpen();
                }
                this.mCloseGuardLocked.close();
            }
            sQLiteConnectionPool = this.mConnectionPoolLocked;
            this.mConnectionPoolLocked = null;
        }
        if (bl) return;
        weakHashMap = sActiveDatabases;
        synchronized (weakHashMap) {
            sActiveDatabases.remove(this);
        }
        if (sQLiteConnectionPool == null) return;
        sQLiteConnectionPool.close();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dump(Printer printer, boolean bl, boolean bl2, ArraySet arraySet) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConnectionPoolLocked != null) {
                printer.println("");
                this.mConnectionPoolLocked.dump(printer, bl, arraySet);
            }
            return;
        }
    }

    static void dumpAll(Printer printer, boolean bl, boolean bl2) {
        ArraySet arraySet = new ArraySet();
        Object[] arrobject = SQLiteDatabase.getActiveDatabases().iterator();
        while (arrobject.hasNext()) {
            arrobject.next().dump(printer, bl, bl2, arraySet);
        }
        if (arraySet.size() > 0) {
            arrobject = arraySet.toArray(new String[arraySet.size()]);
            Arrays.sort(arrobject);
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                SQLiteDatabase.dumpDatabaseDirectory(printer, new File((String)arrobject[i]), bl2);
            }
        }
    }

    private static void dumpDatabaseDirectory(Printer printer, File arrfile, boolean bl) {
        printer.println("");
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Database files in ");
        ((StringBuilder)charSequence).append(arrfile.getAbsolutePath());
        ((StringBuilder)charSequence).append(":");
        printer.println(((StringBuilder)charSequence).toString());
        arrfile = arrfile.listFiles();
        if (arrfile != null && arrfile.length != 0) {
            Arrays.sort(arrfile, _$$Lambda$SQLiteDatabase$1FsSJH2q7x3eeDFXCAu9l4piDsE.INSTANCE);
            for (File file : arrfile) {
                if (bl && !((String)(charSequence = file.getName())).endsWith(".db") && !((String)charSequence).endsWith(".db-wal") && !((String)charSequence).endsWith(".db-journal") && !((String)charSequence).endsWith("-wipecheck")) continue;
                printer.println(String.format("  %-40s %7db %s", file.getName(), file.length(), SQLiteDatabase.getFileTimestamps(file.getAbsolutePath())));
            }
            return;
        }
        printer.println("  [none]");
    }

    public static String findEditTable(String string2) {
        if (!TextUtils.isEmpty(string2)) {
            int n = string2.indexOf(32);
            int n2 = string2.indexOf(44);
            if (n > 0 && (n < n2 || n2 < 0)) {
                return string2.substring(0, n);
            }
            if (n2 > 0 && (n2 < n || n < 0)) {
                return string2.substring(0, n2);
            }
            return string2;
        }
        throw new IllegalStateException("Invalid tables");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static ArrayList<SQLiteDatabase> getActiveDatabases() {
        ArrayList<SQLiteDatabase> arrayList = new ArrayList<SQLiteDatabase>();
        WeakHashMap<SQLiteDatabase, Object> weakHashMap = sActiveDatabases;
        synchronized (weakHashMap) {
            arrayList.addAll(sActiveDatabases.keySet());
            return arrayList;
        }
    }

    static ArrayList<SQLiteDebug.DbStats> getDbStats() {
        ArrayList<SQLiteDebug.DbStats> arrayList = new ArrayList<SQLiteDebug.DbStats>();
        Iterator<SQLiteDatabase> iterator = SQLiteDatabase.getActiveDatabases().iterator();
        while (iterator.hasNext()) {
            iterator.next().collectDbStats(arrayList);
        }
        return arrayList;
    }

    public static String getFileTimestamps(String object) {
        try {
            object = Files.readAttributes(FileSystems.getDefault().getPath((String)object, new String[0]), BasicFileAttributes.class, new LinkOption[0]);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ctime=");
            stringBuilder.append(object.creationTime());
            stringBuilder.append(" mtime=");
            stringBuilder.append(object.lastModifiedTime());
            stringBuilder.append(" atime=");
            stringBuilder.append(object.lastAccessTime());
            object = stringBuilder.toString();
            return object;
        }
        catch (IOException iOException) {
            return "[unable to obtain timestamp]";
        }
    }

    private static boolean isMainThread() {
        Looper looper = Looper.myLooper();
        boolean bl = looper != null && looper == Looper.getMainLooper();
        return bl;
    }

    private boolean isReadOnlyLocked() {
        int n = this.mConfigurationLocked.openFlags;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    static /* synthetic */ int lambda$dumpDatabaseDirectory$0(File file, File file2) {
        return file.getName().compareTo(file2.getName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void open() {
        try {
            try {
                this.openInner();
                return;
            }
            catch (RuntimeException runtimeException) {
                if (!SQLiteDatabaseCorruptException.isCorruptException(runtimeException)) throw runtimeException;
                Log.e(TAG, "Database corruption detected in open()", runtimeException);
                this.onCorruption();
                this.openInner();
            }
            return;
        }
        catch (SQLiteException sQLiteException2) {}
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to open database '");
        stringBuilder.append(this.getLabel());
        stringBuilder.append("'.");
        Log.e(TAG, stringBuilder.toString(), sQLiteException2);
        this.close();
        throw sQLiteException2;
    }

    public static SQLiteDatabase openDatabase(File file, OpenParams openParams) {
        return SQLiteDatabase.openDatabase(file.getPath(), openParams);
    }

    public static SQLiteDatabase openDatabase(String string2, CursorFactory cursorFactory, int n) {
        return SQLiteDatabase.openDatabase(string2, cursorFactory, n, null);
    }

    public static SQLiteDatabase openDatabase(String object, CursorFactory cursorFactory, int n, DatabaseErrorHandler databaseErrorHandler) {
        object = new SQLiteDatabase((String)object, n, cursorFactory, databaseErrorHandler, -1, -1, -1L, null, null);
        SQLiteDatabase.super.open();
        return object;
    }

    @UnsupportedAppUsage
    private static SQLiteDatabase openDatabase(String object, OpenParams openParams) {
        boolean bl = openParams != null;
        Preconditions.checkArgument(bl, "OpenParams cannot be null");
        object = new SQLiteDatabase((String)object, openParams.mOpenFlags, openParams.mCursorFactory, openParams.mErrorHandler, openParams.mLookasideSlotSize, openParams.mLookasideSlotCount, openParams.mIdleConnectionTimeout, openParams.mJournalMode, openParams.mSyncMode);
        SQLiteDatabase.super.open();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void openInner() {
        Object object = this.mLock;
        // MONITORENTER : object
        this.mConnectionPoolLocked = SQLiteConnectionPool.open(this.mConfigurationLocked);
        this.mCloseGuardLocked.open("close");
        // MONITOREXIT : object
        WeakHashMap<SQLiteDatabase, Object> weakHashMap = sActiveDatabases;
        sActiveDatabases.put(this, null);
        // MONITOREXIT : weakHashMap
    }

    public static SQLiteDatabase openOrCreateDatabase(File file, CursorFactory cursorFactory) {
        return SQLiteDatabase.openOrCreateDatabase(file.getPath(), cursorFactory);
    }

    public static SQLiteDatabase openOrCreateDatabase(String string2, CursorFactory cursorFactory) {
        return SQLiteDatabase.openDatabase(string2, cursorFactory, 268435456, null);
    }

    public static SQLiteDatabase openOrCreateDatabase(String string2, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
        return SQLiteDatabase.openDatabase(string2, cursorFactory, 268435456, databaseErrorHandler);
    }

    public static int releaseMemory() {
        return SQLiteGlobal.releaseMemory();
    }

    private void throwIfNotOpenLocked() {
        if (this.mConnectionPoolLocked != null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The database '");
        stringBuilder.append(this.mConfigurationLocked.label);
        stringBuilder.append("' is not open.");
        throw new IllegalStateException(stringBuilder.toString());
    }

    public static void wipeDetected(String string2, String charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DB wipe detected: package=");
        stringBuilder.append(ActivityThread.currentPackageName());
        stringBuilder.append(" reason=");
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" file=");
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(SQLiteDatabase.getFileTimestamps(string2));
        stringBuilder.append(" checkfile ");
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("-wipecheck");
        stringBuilder.append(SQLiteDatabase.getFileTimestamps(((StringBuilder)charSequence).toString()));
        SQLiteDatabase.wtfAsSystemServer(TAG, stringBuilder.toString(), new Throwable("STACKTRACE"));
    }

    static void wtfAsSystemServer(String string2, String string3, Throwable throwable) {
        Log.e(string2, string3, throwable);
        ContentResolver.onDbCorruption(string2, string3, throwable);
    }

    private boolean yieldIfContendedHelper(boolean bl, long l) {
        this.acquireReference();
        try {
            bl = this.getThreadSession().yieldTransaction(l, bl, null);
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addCustomFunction(String object, int n, CustomFunction object2) {
        object2 = new SQLiteCustomFunction((String)object, n, (CustomFunction)object2);
        object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            this.mConfigurationLocked.customFunctions.add((SQLiteCustomFunction)object2);
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return;
            }
            catch (RuntimeException runtimeException) {
                this.mConfigurationLocked.customFunctions.remove(object2);
                throw runtimeException;
            }
        }
    }

    public void beginTransaction() {
        this.beginTransaction(null, true);
    }

    public void beginTransactionNonExclusive() {
        this.beginTransaction(null, false);
    }

    public void beginTransactionWithListener(SQLiteTransactionListener sQLiteTransactionListener) {
        this.beginTransaction(sQLiteTransactionListener, true);
    }

    public void beginTransactionWithListenerNonExclusive(SQLiteTransactionListener sQLiteTransactionListener) {
        this.beginTransaction(sQLiteTransactionListener, false);
    }

    public SQLiteStatement compileStatement(String object) throws SQLException {
        this.acquireReference();
        try {
            object = new SQLiteStatement(this, (String)object, null);
            return object;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    SQLiteSession createSession() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            SQLiteConnectionPool sQLiteConnectionPool = this.mConnectionPoolLocked;
            return new SQLiteSession(sQLiteConnectionPool);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int delete(String charSequence, String string2, String[] arrstring) {
        int n;
        this.acquireReference();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append((String)charSequence);
        if (!TextUtils.isEmpty(string2)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" WHERE ");
            ((StringBuilder)charSequence).append(string2);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        SQLiteStatement sQLiteStatement = new SQLiteStatement(this, stringBuilder.toString(), arrstring);
        {
            catch (Throwable throwable) {
                throw throwable;
            }
        }
        try {
            n = sQLiteStatement.executeUpdateDelete();
        }
        catch (Throwable throwable) {
            sQLiteStatement.close();
            throw throwable;
        }
        try {
            sQLiteStatement.close();
            return n;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void disableWriteAheadLogging() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            int n = this.mConfigurationLocked.openFlags;
            boolean bl = true;
            boolean bl2 = (536870912 & n) != 0;
            if ((Integer.MIN_VALUE & n) == 0) {
                bl = false;
            }
            if (!bl2 && !bl) {
                return;
            }
            SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
            sQLiteDatabaseConfiguration.openFlags &= -536870913;
            sQLiteDatabaseConfiguration = this.mConfigurationLocked;
            sQLiteDatabaseConfiguration.openFlags &= Integer.MAX_VALUE;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return;
            }
            catch (RuntimeException runtimeException) {
                this.mConfigurationLocked.openFlags = n;
                throw runtimeException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean enableWriteAheadLogging() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            if ((this.mConfigurationLocked.openFlags & 536870912) != 0) {
                return true;
            }
            if (this.isReadOnlyLocked()) {
                return false;
            }
            if (this.mConfigurationLocked.isInMemoryDb()) {
                Log.i(TAG, "can't enable WAL for memory databases.");
                return false;
            }
            if (this.mHasAttachedDbsLocked) {
                if (Log.isLoggable(TAG, 3)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("this database: ");
                    stringBuilder.append(this.mConfigurationLocked.label);
                    stringBuilder.append(" has attached databases. can't  enable WAL.");
                    Log.d(TAG, stringBuilder.toString());
                }
                return false;
            }
            SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
            sQLiteDatabaseConfiguration.openFlags = 536870912 | sQLiteDatabaseConfiguration.openFlags;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return true;
            }
            catch (RuntimeException runtimeException) {
                sQLiteDatabaseConfiguration = this.mConfigurationLocked;
                sQLiteDatabaseConfiguration.openFlags &= -536870913;
                throw runtimeException;
            }
        }
    }

    public void endTransaction() {
        this.acquireReference();
        try {
            this.getThreadSession().endTransaction(null);
            return;
        }
        finally {
            this.releaseReference();
        }
    }

    public void execSQL(String string2) throws SQLException {
        this.executeSql(string2, null);
    }

    public void execSQL(String string2, Object[] arrobject) throws SQLException {
        if (arrobject != null) {
            this.executeSql(string2, arrobject);
            return;
        }
        throw new IllegalArgumentException("Empty bindArgs");
    }

    /*
     * Exception decompiling
     */
    public int executeSql(String var1_1, Object[] var2_5) throws SQLException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose(true);
            return;
        }
        finally {
            Object.super.finalize();
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
    public List<Pair<String, String>> getAttachedDbs() {
        var1_1 = new ArrayList<Pair<String, String>>();
        var2_2 = this.mLock;
        // MONITORENTER : var2_2
        if (this.mConnectionPoolLocked == null) {
            // MONITOREXIT : var2_2
            return null;
        }
        if (!this.mHasAttachedDbsLocked) {
            var3_4 = new Pair<String, String>("main", this.mConfigurationLocked.path);
            var1_1.add(var3_4);
            // MONITOREXIT : var2_2
            return var1_1;
        }
        this.acquireReference();
        // MONITOREXIT : var2_2
        var2_2 = null;
        try {
            var3_5 = this.rawQuery("pragma database_list;", null);
            do {
                var2_2 = var3_5;
                if (!var3_5.moveToNext()) ** break;
                var2_2 = var3_5;
                var2_2 = var3_5;
                var4_7 = new Pair<String, String>(var3_5.getString(1), var3_5.getString(2));
                var2_2 = var3_5;
                var1_1.add(var4_7);
            } while (true);
        }
        catch (Throwable var3_6) {
            if (var2_2 == null) throw var3_6;
            var2_2.close();
            throw var3_6;
        }
        try {
            var3_5.close();
            return var1_1;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    String getLabel() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConfigurationLocked.label;
        }
    }

    public long getMaximumSize() {
        long l = DatabaseUtils.longForQuery(this, "PRAGMA max_page_count;", null);
        return this.getPageSize() * l;
    }

    public long getPageSize() {
        return DatabaseUtils.longForQuery(this, "PRAGMA page_size;", null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final String getPath() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConfigurationLocked.path;
        }
    }

    @Deprecated
    public Map<String, String> getSyncedTables() {
        return new HashMap<String, String>(0);
    }

    int getThreadDefaultConnectionFlags(boolean bl) {
        int n = bl ? 1 : 2;
        int n2 = n;
        if (SQLiteDatabase.isMainThread()) {
            n2 = n | 4;
        }
        return n2;
    }

    @UnsupportedAppUsage
    SQLiteSession getThreadSession() {
        return this.mThreadSession.get();
    }

    public int getVersion() {
        return Long.valueOf(DatabaseUtils.longForQuery(this, "PRAGMA user_version;", null)).intValue();
    }

    public boolean inTransaction() {
        this.acquireReference();
        try {
            boolean bl = this.getThreadSession().hasTransaction();
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    public long insert(String charSequence, String string2, ContentValues contentValues) {
        try {
            long l = this.insertWithOnConflict((String)charSequence, string2, contentValues, 0);
            return l;
        }
        catch (SQLException sQLException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Error inserting ");
            ((StringBuilder)charSequence).append(contentValues);
            Log.e(TAG, ((StringBuilder)charSequence).toString(), sQLException);
            return -1L;
        }
    }

    public long insertOrThrow(String string2, String string3, ContentValues contentValues) throws SQLException {
        return this.insertWithOnConflict(string2, string3, contentValues, 0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public long insertWithOnConflict(String object, String object2, ContentValues object3, int n) {
        long l;
        this.acquireReference();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT");
        stringBuilder.append(CONFLICT_VALUES[n]);
        stringBuilder.append(" INTO ");
        stringBuilder.append((String)object);
        stringBuilder.append('(');
        object = null;
        n = object3 != null && !((ContentValues)object3).isEmpty() ? ((ContentValues)object3).size() : 0;
        if (n <= 0) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append((String)object2);
            ((StringBuilder)object3).append(") VALUES (NULL");
            stringBuilder.append(((StringBuilder)object3).toString());
        } else {
            object2 = new Object[n];
            int n2 = 0;
            for (String string2 : ((ContentValues)object3).keySet()) {
                object = n2 > 0 ? "," : "";
                stringBuilder.append((String)object);
                stringBuilder.append(string2);
                object2[n2] = ((ContentValues)object3).get(string2);
                ++n2;
            }
            stringBuilder.append(')');
            stringBuilder.append(" VALUES (");
            for (n2 = 0; n2 < n; ++n2) {
                object = n2 > 0 ? ",?" : "?";
                stringBuilder.append((String)object);
            }
            object = object2;
        }
        stringBuilder.append(')');
        object2 = new SQLiteStatement(this, stringBuilder.toString(), (Object[])object);
        {
            catch (Throwable throwable) {
                throw throwable;
            }
        }
        try {
            l = ((SQLiteStatement)object2).executeInsert();
        }
        catch (Throwable throwable) {
            ((SQLiteClosable)object2).close();
            throw throwable;
        }
        try {
            ((SQLiteClosable)object2).close();
            return l;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean isDatabaseIntegrityOk() {
        this.acquireReference();
        try {
            var1_1 = this.getAttachedDbs();
            if (var1_1 == null) {
                var2_3 = new StringBuilder();
                var2_3.append("databaselist for: ");
                var2_3.append(this.getPath());
                var2_3.append(" couldn't be retrieved. probably because the database is closed");
                var1_1 = new IllegalStateException(var2_3.toString());
                throw var1_1;
            }
            ** GOTO lbl25
        }
        catch (SQLiteException var2_5) {
            var1_1 = new ArrayList();
            var2_6 = new Pair("main", this.getPath());
            var1_1.add(var2_6);
        }
        ** GOTO lbl25
        {
            catch (Throwable var2_4) {}
            this.releaseReference();
            throw var2_4;
lbl25: // 3 sources:
            for (var3_7 = 0; var3_7 < var1_1.size(); ++var3_7) {
                var4_8 = (Pair)var1_1.get(var3_7);
                var5_9 = null;
                var2_6 = var5_9;
                try {
                    var2_6 = var5_9;
                    var6_10 = new StringBuilder();
                    var2_6 = var5_9;
                    var6_10.append("PRAGMA ");
                    var2_6 = var5_9;
                    var6_10.append((String)var4_8.first);
                    var2_6 = var5_9;
                    var6_10.append(".integrity_check(1);");
                    var2_6 = var5_9;
                    var5_9 = this.compileStatement(var6_10.toString());
                    var2_6 = var5_9;
                    var6_10 = var5_9.simpleQueryForString();
                    var2_6 = var5_9;
                    if (var6_10.equalsIgnoreCase("ok")) ** GOTO lbl-1000
                    var2_6 = var5_9;
                    var2_6 = var5_9;
                    var1_1 = new StringBuilder();
                    var2_6 = var5_9;
                    var1_1.append("PRAGMA integrity_check on ");
                    var2_6 = var5_9;
                    var1_1.append((String)var4_8.second);
                    var2_6 = var5_9;
                    var1_1.append(" returned: ");
                    var2_6 = var5_9;
                    var1_1.append((String)var6_10);
                    var2_6 = var5_9;
                    Log.e("SQLiteDatabase", var1_1.toString());
                }
                catch (Throwable var1_2) {}
                {
                    var5_9.close();
                }
                this.releaseReference();
                return false;
lbl-1000: // 1 sources:
                {
                    var5_9.close();
                }
                continue;
                if (var2_6 == null) throw var1_2;
                {
                    var2_6.close();
                    throw var1_2;
                }
            }
        }
        this.releaseReference();
        return true;
    }

    public boolean isDbLockedByCurrentThread() {
        this.acquireReference();
        try {
            boolean bl = this.getThreadSession().hasConnection();
            return bl;
        }
        finally {
            this.releaseReference();
        }
    }

    @Deprecated
    public boolean isDbLockedByOtherThreads() {
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isInMemoryDatabase() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mConfigurationLocked.isInMemoryDb();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isOpen() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mConnectionPoolLocked == null) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isReadOnly() {
        Object object = this.mLock;
        synchronized (object) {
            return this.isReadOnlyLocked();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isWriteAheadLoggingEnabled() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            if ((this.mConfigurationLocked.openFlags & 536870912) == 0) return false;
            return true;
        }
    }

    @Deprecated
    public void markTableSyncable(String string2, String string3) {
    }

    @Deprecated
    public void markTableSyncable(String string2, String string3, String string4) {
    }

    public boolean needUpgrade(int n) {
        boolean bl = n > this.getVersion();
        return bl;
    }

    @Override
    protected void onAllReferencesReleased() {
        this.dispose(false);
    }

    void onCorruption() {
        EventLog.writeEvent(75004, this.getLabel());
        this.mErrorHandler.onCorruption(this);
    }

    public Cursor query(String string2, String[] arrstring, String string3, String[] arrstring2, String string4, String string5, String string6) {
        return this.query(false, string2, arrstring, string3, arrstring2, string4, string5, string6, null);
    }

    public Cursor query(String string2, String[] arrstring, String string3, String[] arrstring2, String string4, String string5, String string6, String string7) {
        return this.query(false, string2, arrstring, string3, arrstring2, string4, string5, string6, string7);
    }

    public Cursor query(boolean bl, String string2, String[] arrstring, String string3, String[] arrstring2, String string4, String string5, String string6, String string7) {
        return this.queryWithFactory(null, bl, string2, arrstring, string3, arrstring2, string4, string5, string6, string7, null);
    }

    public Cursor query(boolean bl, String string2, String[] arrstring, String string3, String[] arrstring2, String string4, String string5, String string6, String string7, CancellationSignal cancellationSignal) {
        return this.queryWithFactory(null, bl, string2, arrstring, string3, arrstring2, string4, string5, string6, string7, cancellationSignal);
    }

    public Cursor queryWithFactory(CursorFactory cursorFactory, boolean bl, String string2, String[] arrstring, String string3, String[] arrstring2, String string4, String string5, String string6, String string7) {
        return this.queryWithFactory(cursorFactory, bl, string2, arrstring, string3, arrstring2, string4, string5, string6, string7, null);
    }

    public Cursor queryWithFactory(CursorFactory object, boolean bl, String string2, String[] object2, String string3, String[] arrstring, String string4, String string5, String string6, String string7, CancellationSignal cancellationSignal) {
        this.acquireReference();
        try {
            object2 = SQLiteQueryBuilder.buildQueryString(bl, string2, object2, string3, string4, string5, string6, string7);
            object = this.rawQueryWithFactory((CursorFactory)object, (String)object2, arrstring, SQLiteDatabase.findEditTable(string2), cancellationSignal);
            return object;
        }
        finally {
            this.releaseReference();
        }
    }

    public Cursor rawQuery(String string2, String[] arrstring) {
        return this.rawQueryWithFactory(null, string2, arrstring, null, null);
    }

    public Cursor rawQuery(String string2, String[] arrstring, CancellationSignal cancellationSignal) {
        return this.rawQueryWithFactory(null, string2, arrstring, null, cancellationSignal);
    }

    public Cursor rawQueryWithFactory(CursorFactory cursorFactory, String string2, String[] arrstring, String string3) {
        return this.rawQueryWithFactory(cursorFactory, string2, arrstring, string3, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Cursor rawQueryWithFactory(CursorFactory object, String string2, String[] arrstring, String string3, CancellationSignal cancellationSignal) {
        this.acquireReference();
        try {
            SQLiteDirectCursorDriver sQLiteDirectCursorDriver = new SQLiteDirectCursorDriver(this, string2, string3, cancellationSignal);
            if (object == null) {
                object = this.mCursorFactory;
            }
            object = sQLiteDirectCursorDriver.query((CursorFactory)object, arrstring);
            return object;
        }
        finally {
            this.releaseReference();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void reopenReadWrite() {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            if (!this.isReadOnlyLocked()) {
                return;
            }
            int n = this.mConfigurationLocked.openFlags;
            this.mConfigurationLocked.openFlags = this.mConfigurationLocked.openFlags & -2 | 0;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return;
            }
            catch (RuntimeException runtimeException) {
                this.mConfigurationLocked.openFlags = n;
                throw runtimeException;
            }
        }
    }

    public long replace(String charSequence, String string2, ContentValues contentValues) {
        try {
            long l = this.insertWithOnConflict((String)charSequence, string2, contentValues, 5);
            return l;
        }
        catch (SQLException sQLException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Error inserting ");
            ((StringBuilder)charSequence).append(contentValues);
            Log.e(TAG, ((StringBuilder)charSequence).toString(), sQLException);
            return -1L;
        }
    }

    public long replaceOrThrow(String string2, String string3, ContentValues contentValues) throws SQLException {
        return this.insertWithOnConflict(string2, string3, contentValues, 5);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setForeignKeyConstraintsEnabled(boolean bl) {
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            if (this.mConfigurationLocked.foreignKeyConstraintsEnabled == bl) {
                return;
            }
            this.mConfigurationLocked.foreignKeyConstraintsEnabled = bl;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return;
            }
            catch (RuntimeException runtimeException) {
                SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration = this.mConfigurationLocked;
                bl = !bl;
                sQLiteDatabaseConfiguration.foreignKeyConstraintsEnabled = bl;
                throw runtimeException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLocale(Locale locale) {
        if (locale == null) {
            throw new IllegalArgumentException("locale must not be null.");
        }
        Object object = this.mLock;
        synchronized (object) {
            this.throwIfNotOpenLocked();
            Locale locale2 = this.mConfigurationLocked.locale;
            this.mConfigurationLocked.locale = locale;
            try {
                this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                return;
            }
            catch (RuntimeException runtimeException) {
                this.mConfigurationLocked.locale = locale2;
                throw runtimeException;
            }
        }
    }

    @Deprecated
    public void setLockingEnabled(boolean bl) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setMaxSqlCacheSize(int n) {
        if (n <= 100 && n >= 0) {
            Object object = this.mLock;
            synchronized (object) {
                this.throwIfNotOpenLocked();
                int n2 = this.mConfigurationLocked.maxSqlCacheSize;
                this.mConfigurationLocked.maxSqlCacheSize = n;
                try {
                    this.mConnectionPoolLocked.reconfigure(this.mConfigurationLocked);
                    return;
                }
                catch (RuntimeException runtimeException) {
                    this.mConfigurationLocked.maxSqlCacheSize = n2;
                    throw runtimeException;
                }
            }
        }
        throw new IllegalStateException("expected value between 0 and 100");
    }

    public long setMaximumSize(long l) {
        long l2;
        long l3 = this.getPageSize();
        long l4 = l2 = l / l3;
        if (l % l3 != 0L) {
            l4 = l2 + 1L;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PRAGMA max_page_count = ");
        stringBuilder.append(l4);
        return DatabaseUtils.longForQuery(this, stringBuilder.toString(), null) * l3;
    }

    public void setPageSize(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PRAGMA page_size = ");
        stringBuilder.append(l);
        this.execSQL(stringBuilder.toString());
    }

    public void setTransactionSuccessful() {
        this.acquireReference();
        try {
            this.getThreadSession().setTransactionSuccessful();
            return;
        }
        finally {
            this.releaseReference();
        }
    }

    public void setVersion(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PRAGMA user_version = ");
        stringBuilder.append(n);
        this.execSQL(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLiteDatabase: ");
        stringBuilder.append(this.getPath());
        return stringBuilder.toString();
    }

    public int update(String string2, ContentValues contentValues, String string3, String[] arrstring) {
        return this.updateWithOnConflict(string2, contentValues, string3, arrstring, 0);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int updateWithOnConflict(String object, ContentValues contentValues, String string2, String[] arrstring, int n) {
        Object[] arrobject;
        StringBuilder stringBuilder;
        block11 : {
            if (contentValues == null) throw new IllegalArgumentException("Empty values");
            if (contentValues.isEmpty()) throw new IllegalArgumentException("Empty values");
            this.acquireReference();
            stringBuilder = new StringBuilder(120);
            stringBuilder.append("UPDATE ");
            stringBuilder.append(CONFLICT_VALUES[n]);
            stringBuilder.append((String)object);
            stringBuilder.append(" SET ");
            n = contentValues.size();
            int n2 = arrstring == null ? n : arrstring.length + n;
            arrobject = new Object[n2];
            int n3 = 0;
            for (String string3 : contentValues.keySet()) {
                object = n3 > 0 ? "," : "";
                stringBuilder.append((String)object);
                stringBuilder.append(string3);
                arrobject[n3] = contentValues.get(string3);
                stringBuilder.append("=?");
                ++n3;
            }
            if (arrstring == null) break block11;
            for (n3 = n; n3 < n2; ++n3) {
                arrobject[n3] = arrstring[n3 - n];
            }
        }
        if (!TextUtils.isEmpty(string2)) {
            stringBuilder.append(" WHERE ");
            stringBuilder.append(string2);
        }
        object = new SQLiteStatement(this, stringBuilder.toString(), arrobject);
        {
            catch (Throwable throwable) {
                throw throwable;
            }
        }
        try {
            n = ((SQLiteStatement)object).executeUpdateDelete();
        }
        catch (Throwable throwable) {
            ((SQLiteClosable)object).close();
            throw throwable;
        }
        try {
            ((SQLiteClosable)object).close();
            return n;
        }
        finally {
            this.releaseReference();
        }
    }

    public void validateSql(String string2, CancellationSignal cancellationSignal) {
        this.getThreadSession().prepare(string2, this.getThreadDefaultConnectionFlags(true), cancellationSignal, null);
    }

    @Deprecated
    public boolean yieldIfContended() {
        return this.yieldIfContendedHelper(false, -1L);
    }

    public boolean yieldIfContendedSafely() {
        return this.yieldIfContendedHelper(true, -1L);
    }

    public boolean yieldIfContendedSafely(long l) {
        return this.yieldIfContendedHelper(true, l);
    }

    public static interface CursorFactory {
        public Cursor newCursor(SQLiteDatabase var1, SQLiteCursorDriver var2, String var3, SQLiteQuery var4);
    }

    public static interface CustomFunction {
        public void callback(String[] var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DatabaseOpenFlags {
    }

    public static final class OpenParams {
        private final CursorFactory mCursorFactory;
        private final DatabaseErrorHandler mErrorHandler;
        private final long mIdleConnectionTimeout;
        private final String mJournalMode;
        private final int mLookasideSlotCount;
        private final int mLookasideSlotSize;
        private final int mOpenFlags;
        private final String mSyncMode;

        private OpenParams(int n, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler, int n2, int n3, long l, String string2, String string3) {
            this.mOpenFlags = n;
            this.mCursorFactory = cursorFactory;
            this.mErrorHandler = databaseErrorHandler;
            this.mLookasideSlotSize = n2;
            this.mLookasideSlotCount = n3;
            this.mIdleConnectionTimeout = l;
            this.mJournalMode = string2;
            this.mSyncMode = string3;
        }

        public CursorFactory getCursorFactory() {
            return this.mCursorFactory;
        }

        public DatabaseErrorHandler getErrorHandler() {
            return this.mErrorHandler;
        }

        public long getIdleConnectionTimeout() {
            return this.mIdleConnectionTimeout;
        }

        public String getJournalMode() {
            return this.mJournalMode;
        }

        public int getLookasideSlotCount() {
            return this.mLookasideSlotCount;
        }

        public int getLookasideSlotSize() {
            return this.mLookasideSlotSize;
        }

        public int getOpenFlags() {
            return this.mOpenFlags;
        }

        public String getSynchronousMode() {
            return this.mSyncMode;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder {
            private CursorFactory mCursorFactory;
            private DatabaseErrorHandler mErrorHandler;
            private long mIdleConnectionTimeout = -1L;
            private String mJournalMode;
            private int mLookasideSlotCount = -1;
            private int mLookasideSlotSize = -1;
            private int mOpenFlags;
            private String mSyncMode;

            public Builder() {
            }

            public Builder(OpenParams openParams) {
                this.mLookasideSlotSize = openParams.mLookasideSlotSize;
                this.mLookasideSlotCount = openParams.mLookasideSlotCount;
                this.mOpenFlags = openParams.mOpenFlags;
                this.mCursorFactory = openParams.mCursorFactory;
                this.mErrorHandler = openParams.mErrorHandler;
                this.mJournalMode = openParams.mJournalMode;
                this.mSyncMode = openParams.mSyncMode;
            }

            public Builder addOpenFlags(int n) {
                this.mOpenFlags |= n;
                return this;
            }

            public OpenParams build() {
                return new OpenParams(this.mOpenFlags, this.mCursorFactory, this.mErrorHandler, this.mLookasideSlotSize, this.mLookasideSlotCount, this.mIdleConnectionTimeout, this.mJournalMode, this.mSyncMode);
            }

            public boolean isWriteAheadLoggingEnabled() {
                boolean bl = (this.mOpenFlags & 536870912) != 0;
                return bl;
            }

            public Builder removeOpenFlags(int n) {
                this.mOpenFlags &= n;
                return this;
            }

            public Builder setCursorFactory(CursorFactory cursorFactory) {
                this.mCursorFactory = cursorFactory;
                return this;
            }

            public Builder setErrorHandler(DatabaseErrorHandler databaseErrorHandler) {
                this.mErrorHandler = databaseErrorHandler;
                return this;
            }

            @Deprecated
            public Builder setIdleConnectionTimeout(long l) {
                boolean bl = l >= 0L;
                Preconditions.checkArgument(bl, "idle connection timeout cannot be negative");
                this.mIdleConnectionTimeout = l;
                return this;
            }

            public Builder setJournalMode(String string2) {
                Preconditions.checkNotNull(string2);
                this.mJournalMode = string2;
                return this;
            }

            public Builder setLookasideConfig(int n, int n2) {
                boolean bl;
                block3 : {
                    boolean bl2;
                    block2 : {
                        bl2 = true;
                        bl = n >= 0;
                        Preconditions.checkArgument(bl, "lookasideSlotCount cannot be negative");
                        bl = n2 >= 0;
                        Preconditions.checkArgument(bl, "lookasideSlotSize cannot be negative");
                        if (n <= 0) break block2;
                        bl = bl2;
                        if (n2 > 0) break block3;
                    }
                    bl = n2 == 0 && n == 0 ? bl2 : false;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid configuration: ");
                stringBuilder.append(n);
                stringBuilder.append(", ");
                stringBuilder.append(n2);
                Preconditions.checkArgument(bl, stringBuilder.toString());
                this.mLookasideSlotSize = n;
                this.mLookasideSlotCount = n2;
                return this;
            }

            public Builder setOpenFlags(int n) {
                this.mOpenFlags = n;
                return this;
            }

            public Builder setSynchronousMode(String string2) {
                Preconditions.checkNotNull(string2);
                this.mSyncMode = string2;
                return this;
            }

            public void setWriteAheadLoggingEnabled(boolean bl) {
                if (bl) {
                    this.addOpenFlags(536870912);
                } else {
                    this.removeOpenFlags(536870912);
                }
            }
        }

    }

}

