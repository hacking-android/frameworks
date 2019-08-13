/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.database.sqlite;

import android.database.sqlite.SQLiteCompatibilityWalFlags;
import android.database.sqlite.SQLiteConnection;
import android.database.sqlite.SQLiteDatabaseConfiguration;
import android.database.sqlite.SQLiteDebug;
import android.database.sqlite.SQLiteGlobal;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.OperationCanceledException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.util.PrefixPrinter;
import android.util.Printer;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import dalvik.system.CloseGuard;
import java.io.Closeable;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.LockSupport;

public final class SQLiteConnectionPool
implements Closeable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int CONNECTION_FLAG_INTERACTIVE = 4;
    public static final int CONNECTION_FLAG_PRIMARY_CONNECTION_AFFINITY = 2;
    public static final int CONNECTION_FLAG_READ_ONLY = 1;
    private static final long CONNECTION_POOL_BUSY_MILLIS = 30000L;
    private static final String TAG = "SQLiteConnectionPool";
    private final WeakHashMap<SQLiteConnection, AcquiredConnectionStatus> mAcquiredConnections = new WeakHashMap();
    private final ArrayList<SQLiteConnection> mAvailableNonPrimaryConnections = new ArrayList();
    private SQLiteConnection mAvailablePrimaryConnection;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final SQLiteDatabaseConfiguration mConfiguration;
    private final AtomicBoolean mConnectionLeaked = new AtomicBoolean();
    private ConnectionWaiter mConnectionWaiterPool;
    private ConnectionWaiter mConnectionWaiterQueue;
    @GuardedBy(value={"mLock"})
    private IdleConnectionHandler mIdleConnectionHandler;
    private boolean mIsOpen;
    private final Object mLock = new Object();
    private int mMaxConnectionPoolSize;
    private int mNextConnectionId;
    private final AtomicLong mTotalExecutionTimeCounter = new AtomicLong(0L);

    private SQLiteConnectionPool(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration) {
        this.mConfiguration = new SQLiteDatabaseConfiguration(sQLiteDatabaseConfiguration);
        this.setMaxConnectionPoolSizeLocked();
        if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
            this.setupIdleConnectionHandler(Looper.getMainLooper(), this.mConfiguration.idleConnectionTimeoutMs);
        }
    }

    static /* synthetic */ SQLiteDatabaseConfiguration access$500(SQLiteConnectionPool sQLiteConnectionPool) {
        return sQLiteConnectionPool.mConfiguration;
    }

    @GuardedBy(value={"mLock"})
    private void cancelConnectionWaiterLocked(ConnectionWaiter connectionWaiter) {
        if (connectionWaiter.mAssignedConnection == null && connectionWaiter.mException == null) {
            ConnectionWaiter connectionWaiter2 = null;
            ConnectionWaiter connectionWaiter3 = this.mConnectionWaiterQueue;
            while (connectionWaiter3 != connectionWaiter) {
                connectionWaiter2 = connectionWaiter3;
                connectionWaiter3 = connectionWaiter3.mNext;
            }
            if (connectionWaiter2 != null) {
                connectionWaiter2.mNext = connectionWaiter.mNext;
            } else {
                this.mConnectionWaiterQueue = connectionWaiter.mNext;
            }
            connectionWaiter.mException = new OperationCanceledException();
            LockSupport.unpark(connectionWaiter.mThread);
            this.wakeConnectionWaitersLocked();
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private boolean closeAvailableConnectionLocked(int n) {
        SQLiteConnection sQLiteConnection;
        for (int i = this.mAvailableNonPrimaryConnections.size() - 1; i >= 0; --i) {
            sQLiteConnection = this.mAvailableNonPrimaryConnections.get(i);
            if (sQLiteConnection.getConnectionId() != n) continue;
            this.closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            this.mAvailableNonPrimaryConnections.remove(i);
            return true;
        }
        sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null && sQLiteConnection.getConnectionId() == n) {
            this.closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
            this.mAvailablePrimaryConnection = null;
            return true;
        }
        return false;
    }

    @GuardedBy(value={"mLock"})
    private void closeAvailableConnectionsAndLogExceptionsLocked() {
        this.closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
        SQLiteConnection sQLiteConnection = this.mAvailablePrimaryConnection;
        if (sQLiteConnection != null) {
            this.closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            this.mAvailablePrimaryConnection = null;
        }
    }

    @GuardedBy(value={"mLock"})
    private void closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked() {
        int n = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < n; ++i) {
            this.closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.get(i));
        }
        this.mAvailableNonPrimaryConnections.clear();
    }

    @GuardedBy(value={"mLock"})
    private void closeConnectionAndLogExceptionsLocked(SQLiteConnection sQLiteConnection) {
        try {
            sQLiteConnection.close();
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionClosed(sQLiteConnection);
            }
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to close connection, its fate is now in the hands of the merciful GC: ");
            stringBuilder.append(sQLiteConnection);
            Log.e(TAG, stringBuilder.toString(), runtimeException);
        }
    }

    @GuardedBy(value={"mLock"})
    private void closeExcessConnectionsAndLogExceptionsLocked() {
        int n = this.mAvailableNonPrimaryConnections.size();
        do {
            int n2 = n - 1;
            if (n <= this.mMaxConnectionPoolSize - 1) break;
            this.closeConnectionAndLogExceptionsLocked(this.mAvailableNonPrimaryConnections.remove(n2));
            n = n2;
        } while (true);
    }

    private void discardAcquiredConnectionsLocked() {
        this.markAcquiredConnectionsLocked(AcquiredConnectionStatus.DISCARD);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dispose(boolean bl) {
        Object object = this.mCloseGuard;
        if (object != null) {
            if (bl) {
                object.warnIfOpen();
            }
            this.mCloseGuard.close();
        }
        if (bl) return;
        object = this.mLock;
        synchronized (object) {
            this.throwIfClosedLocked();
            this.mIsOpen = false;
            this.closeAvailableConnectionsAndLogExceptionsLocked();
            int n = this.mAcquiredConnections.size();
            if (n != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The connection pool for ");
                stringBuilder.append(this.mConfiguration.label);
                stringBuilder.append(" has been closed but there are still ");
                stringBuilder.append(n);
                stringBuilder.append(" connections in use.  They will be closed as they are released back to the pool.");
                Log.i(TAG, stringBuilder.toString());
            }
            this.wakeConnectionWaitersLocked();
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private void finishAcquireConnectionLocked(SQLiteConnection sQLiteConnection, int n) {
        boolean bl = (n & 1) != 0;
        try {
            sQLiteConnection.setOnlyAllowReadOnlyOperations(bl);
            this.mAcquiredConnections.put(sQLiteConnection, AcquiredConnectionStatus.NORMAL);
            return;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to prepare acquired connection for session, closing it: ");
            stringBuilder.append(sQLiteConnection);
            stringBuilder.append(", connectionFlags=");
            stringBuilder.append(n);
            Log.e(TAG, stringBuilder.toString());
            this.closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            throw runtimeException;
        }
    }

    private static int getPriority(int n) {
        n = (n & 4) != 0 ? 1 : 0;
        return n;
    }

    private boolean isSessionBlockingImportantConnectionWaitersLocked(boolean bl, int n) {
        ConnectionWaiter connectionWaiter = this.mConnectionWaiterQueue;
        if (connectionWaiter != null) {
            n = SQLiteConnectionPool.getPriority(n);
            while (n <= connectionWaiter.mPriority) {
                if (!bl && connectionWaiter.mWantPrimaryConnection) {
                    ConnectionWaiter connectionWaiter2;
                    connectionWaiter = connectionWaiter2 = connectionWaiter.mNext;
                    if (connectionWaiter2 != null) continue;
                    break;
                }
                return true;
            }
        }
        return false;
    }

    private void logConnectionPoolBusyLocked(long l, int n) {
        Iterator<SQLiteConnection> iterator;
        Object object = Thread.currentThread();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The connection pool for database '");
        stringBuilder.append(this.mConfiguration.label);
        stringBuilder.append("' has been unable to grant a connection to thread ");
        stringBuilder.append(((Thread)object).getId());
        stringBuilder.append(" (");
        stringBuilder.append(((Thread)object).getName());
        stringBuilder.append(") ");
        stringBuilder.append("with flags 0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(" for ");
        stringBuilder.append((float)l * 0.001f);
        stringBuilder.append(" seconds.\n");
        object = new ArrayList();
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        n = 0;
        if (!this.mAcquiredConnections.isEmpty()) {
            iterator = this.mAcquiredConnections.keySet().iterator();
            do {
                n2 = n3++;
                n4 = n++;
                if (!iterator.hasNext()) break;
                String string2 = iterator.next().describeCurrentOperationUnsafe();
                if (string2 == null) continue;
                ((ArrayList)object).add(string2);
            } while (true);
        }
        n = n3 = this.mAvailableNonPrimaryConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            n = n3 + 1;
        }
        stringBuilder.append("Connections: ");
        stringBuilder.append(n2);
        stringBuilder.append(" active, ");
        stringBuilder.append(n4);
        stringBuilder.append(" idle, ");
        stringBuilder.append(n);
        stringBuilder.append(" available.\n");
        if (!((ArrayList)object).isEmpty()) {
            stringBuilder.append("\nRequests in progress:\n");
            iterator = ((ArrayList)object).iterator();
            while (iterator.hasNext()) {
                object = (String)((Object)iterator.next());
                stringBuilder.append("  ");
                stringBuilder.append((String)object);
                stringBuilder.append("\n");
            }
        }
        Log.w(TAG, stringBuilder.toString());
    }

    private void markAcquiredConnectionsLocked(AcquiredConnectionStatus acquiredConnectionStatus) {
        if (!this.mAcquiredConnections.isEmpty()) {
            ArrayList<SQLiteConnection> arrayList = new ArrayList<SQLiteConnection>(this.mAcquiredConnections.size());
            for (Map.Entry<SQLiteConnection, AcquiredConnectionStatus> entry : this.mAcquiredConnections.entrySet()) {
                AcquiredConnectionStatus acquiredConnectionStatus2 = entry.getValue();
                if (acquiredConnectionStatus == acquiredConnectionStatus2 || acquiredConnectionStatus2 == AcquiredConnectionStatus.DISCARD) continue;
                arrayList.add(entry.getKey());
            }
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.mAcquiredConnections.put((SQLiteConnection)arrayList.get(i), acquiredConnectionStatus);
            }
        }
    }

    private ConnectionWaiter obtainConnectionWaiterLocked(Thread thread, long l, int n, boolean bl, String string2, int n2) {
        ConnectionWaiter connectionWaiter = this.mConnectionWaiterPool;
        if (connectionWaiter != null) {
            this.mConnectionWaiterPool = connectionWaiter.mNext;
            connectionWaiter.mNext = null;
        } else {
            connectionWaiter = new ConnectionWaiter();
        }
        connectionWaiter.mThread = thread;
        connectionWaiter.mStartTime = l;
        connectionWaiter.mPriority = n;
        connectionWaiter.mWantPrimaryConnection = bl;
        connectionWaiter.mSql = string2;
        connectionWaiter.mConnectionFlags = n2;
        return connectionWaiter;
    }

    public static SQLiteConnectionPool open(SQLiteDatabaseConfiguration object) {
        if (object != null) {
            object = new SQLiteConnectionPool((SQLiteDatabaseConfiguration)object);
            SQLiteConnectionPool.super.open();
            return object;
        }
        throw new IllegalArgumentException("configuration must not be null.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void open() {
        this.mAvailablePrimaryConnection = this.openConnectionLocked(this.mConfiguration, true);
        Object object = this.mLock;
        synchronized (object) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased(this.mAvailablePrimaryConnection);
            }
        }
        this.mIsOpen = true;
        this.mCloseGuard.open("close");
    }

    private SQLiteConnection openConnectionLocked(SQLiteDatabaseConfiguration sQLiteDatabaseConfiguration, boolean bl) {
        int n = this.mNextConnectionId;
        this.mNextConnectionId = n + 1;
        return SQLiteConnection.open(this, sQLiteDatabaseConfiguration, n, bl);
    }

    @GuardedBy(value={"mLock"})
    private void reconfigureAllConnectionsLocked() {
        Object object;
        Object object2 = this.mAvailablePrimaryConnection;
        if (object2 != null) {
            try {
                ((SQLiteConnection)object2).reconfigure(this.mConfiguration);
            }
            catch (RuntimeException runtimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to reconfigure available primary connection, closing it: ");
                ((StringBuilder)object).append(this.mAvailablePrimaryConnection);
                Log.e(TAG, ((StringBuilder)object).toString(), runtimeException);
                this.closeConnectionAndLogExceptionsLocked(this.mAvailablePrimaryConnection);
                this.mAvailablePrimaryConnection = null;
            }
        }
        int n = this.mAvailableNonPrimaryConnections.size();
        for (int i = 0; i < n; ++i) {
            object = this.mAvailableNonPrimaryConnections.get(i);
            try {
                ((SQLiteConnection)object).reconfigure(this.mConfiguration);
                continue;
            }
            catch (RuntimeException runtimeException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Failed to reconfigure available non-primary connection, closing it: ");
                ((StringBuilder)object2).append(object);
                Log.e(TAG, ((StringBuilder)object2).toString(), runtimeException);
                this.closeConnectionAndLogExceptionsLocked((SQLiteConnection)object);
                this.mAvailableNonPrimaryConnections.remove(i);
                --n;
                --i;
            }
        }
        this.markAcquiredConnectionsLocked(AcquiredConnectionStatus.RECONFIGURE);
    }

    @GuardedBy(value={"mLock"})
    private boolean recycleConnectionLocked(SQLiteConnection sQLiteConnection, AcquiredConnectionStatus object) {
        AcquiredConnectionStatus acquiredConnectionStatus = object;
        if (object == AcquiredConnectionStatus.RECONFIGURE) {
            try {
                sQLiteConnection.reconfigure(this.mConfiguration);
                acquiredConnectionStatus = object;
            }
            catch (RuntimeException runtimeException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Failed to reconfigure released connection, closing it: ");
                ((StringBuilder)object).append(sQLiteConnection);
                Log.e(TAG, ((StringBuilder)object).toString(), runtimeException);
                acquiredConnectionStatus = AcquiredConnectionStatus.DISCARD;
            }
        }
        if (acquiredConnectionStatus == AcquiredConnectionStatus.DISCARD) {
            this.closeConnectionAndLogExceptionsLocked(sQLiteConnection);
            return false;
        }
        return true;
    }

    private void recycleConnectionWaiterLocked(ConnectionWaiter connectionWaiter) {
        connectionWaiter.mNext = this.mConnectionWaiterPool;
        connectionWaiter.mThread = null;
        connectionWaiter.mSql = null;
        connectionWaiter.mAssignedConnection = null;
        connectionWaiter.mException = null;
        ++connectionWaiter.mNonce;
        this.mConnectionWaiterPool = connectionWaiter;
    }

    private void setMaxConnectionPoolSizeLocked() {
        this.mMaxConnectionPoolSize = !this.mConfiguration.isInMemoryDb() && (this.mConfiguration.openFlags & 536870912) != 0 ? SQLiteGlobal.getWALConnectionPoolSize() : 1;
    }

    private void throwIfClosedLocked() {
        if (this.mIsOpen) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because the connection pool has been closed.");
    }

    @GuardedBy(value={"mLock"})
    private SQLiteConnection tryAcquireNonPrimaryConnectionLocked(String object, int n) {
        int n2;
        int n3 = this.mAvailableNonPrimaryConnections.size();
        if (n3 > 1 && object != null) {
            for (n2 = 0; n2 < n3; ++n2) {
                SQLiteConnection sQLiteConnection = this.mAvailableNonPrimaryConnections.get(n2);
                if (!sQLiteConnection.isPreparedStatementInCache((String)object)) continue;
                this.mAvailableNonPrimaryConnections.remove(n2);
                this.finishAcquireConnectionLocked(sQLiteConnection, n);
                return sQLiteConnection;
            }
        }
        if (n3 > 0) {
            object = this.mAvailableNonPrimaryConnections.remove(n3 - 1);
            this.finishAcquireConnectionLocked((SQLiteConnection)object, n);
            return object;
        }
        n2 = n3 = this.mAcquiredConnections.size();
        if (this.mAvailablePrimaryConnection != null) {
            n2 = n3 + 1;
        }
        if (n2 >= this.mMaxConnectionPoolSize) {
            return null;
        }
        object = this.openConnectionLocked(this.mConfiguration, false);
        this.finishAcquireConnectionLocked((SQLiteConnection)object, n);
        return object;
    }

    @GuardedBy(value={"mLock"})
    private SQLiteConnection tryAcquirePrimaryConnectionLocked(int n) {
        Object object = this.mAvailablePrimaryConnection;
        if (object != null) {
            this.mAvailablePrimaryConnection = null;
            this.finishAcquireConnectionLocked((SQLiteConnection)object, n);
            return object;
        }
        object = this.mAcquiredConnections.keySet().iterator();
        while (object.hasNext()) {
            if (!((SQLiteConnection)object.next()).isPrimaryConnection()) continue;
            return null;
        }
        object = this.openConnectionLocked(this.mConfiguration, true);
        this.finishAcquireConnectionLocked((SQLiteConnection)object, n);
        return object;
    }

    /*
     * Exception decompiling
     */
    private SQLiteConnection waitForConnection(String var1_1, int var2_11, CancellationSignal var3_12) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @GuardedBy(value={"mLock"})
    private void wakeConnectionWaitersLocked() {
        ConnectionWaiter connectionWaiter = null;
        ConnectionWaiter connectionWaiter2 = this.mConnectionWaiterQueue;
        boolean bl = false;
        boolean bl2 = false;
        while (connectionWaiter2 != null) {
            boolean bl3;
            boolean bl5;
            Object object;
            boolean bl6 = false;
            if (!this.mIsOpen) {
                bl5 = true;
                bl3 = bl2;
                bl2 = bl5;
            } else {
                Object object2 = null;
                bl3 = bl2;
                object = object2;
                bl5 = bl;
                boolean bl4 = bl2;
                try {
                    if (!connectionWaiter2.mWantPrimaryConnection) {
                        bl3 = bl2;
                        object = object2;
                        if (!bl2) {
                            bl5 = bl;
                            bl4 = bl2;
                            object2 = this.tryAcquireNonPrimaryConnectionLocked(connectionWaiter2.mSql, connectionWaiter2.mConnectionFlags);
                            bl3 = bl2;
                            object = object2;
                            if (object2 == null) {
                                bl3 = true;
                                object = object2;
                            }
                        }
                    }
                    bl2 = bl;
                    object2 = object;
                    if (object == null) {
                        bl2 = bl;
                        object2 = object;
                        if (!bl) {
                            bl5 = bl;
                            bl4 = bl3;
                            object = this.tryAcquirePrimaryConnectionLocked(connectionWaiter2.mConnectionFlags);
                            bl2 = bl;
                            object2 = object;
                            if (object == null) {
                                bl2 = true;
                                object2 = object;
                            }
                        }
                    }
                    if (object2 != null) {
                        bl5 = bl2;
                        bl4 = bl3;
                        connectionWaiter2.mAssignedConnection = object2;
                        bl5 = true;
                    } else {
                        bl5 = bl6;
                        if (bl3) {
                            bl5 = bl6;
                            if (bl2) {
                                return;
                            }
                        }
                    }
                    bl = bl2;
                    bl2 = bl5;
                }
                catch (RuntimeException runtimeException) {
                    connectionWaiter2.mException = runtimeException;
                    bl2 = true;
                    bl3 = bl4;
                    bl = bl5;
                }
            }
            object = connectionWaiter2.mNext;
            if (bl2) {
                if (connectionWaiter != null) {
                    connectionWaiter.mNext = object;
                } else {
                    this.mConnectionWaiterQueue = object;
                }
                connectionWaiter2.mNext = null;
                LockSupport.unpark(connectionWaiter2.mThread);
            } else {
                connectionWaiter = connectionWaiter2;
            }
            connectionWaiter2 = object;
            bl2 = bl3;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLiteConnection acquireConnection(String object, int n, CancellationSignal object2) {
        object2 = this.waitForConnection((String)object, n, (CancellationSignal)object2);
        object = this.mLock;
        synchronized (object) {
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionAcquired((SQLiteConnection)object2);
            }
            return object2;
        }
    }

    @Override
    public void close() {
        this.dispose(false);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void closeAvailableNonPrimaryConnectionsAndLogExceptions() {
        Object object = this.mLock;
        synchronized (object) {
            this.closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void collectDbStats(ArrayList<SQLiteDebug.DbStats> arrayList) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.collectDbStats(arrayList);
            }
            Iterator<SQLiteConnection> iterator = this.mAvailableNonPrimaryConnections.iterator();
            while (iterator.hasNext()) {
                iterator.next().collectDbStats(arrayList);
            }
            iterator = this.mAcquiredConnections.keySet().iterator();
            while (iterator.hasNext()) {
                iterator.next().collectDbStatsUnsafe(arrayList);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void disableIdleConnectionHandler() {
        Object object = this.mLock;
        synchronized (object) {
            this.mIdleConnectionHandler = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(Printer object, boolean bl, ArraySet<String> object22) {
        Printer printer = PrefixPrinter.create((Printer)object, "    ");
        Object object3 = this.mLock;
        synchronized (object3) {
            int n;
            if (object22 != null) {
                Object object4 = new File(this.mConfiguration.path);
                ((ArraySet)object22).add(((File)object4).getParent());
            }
            boolean bl2 = this.mConfiguration.isLegacyCompatibilityWalEnabled();
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("Connection pool for ");
            ((StringBuilder)object22).append(this.mConfiguration.path);
            ((StringBuilder)object22).append(":");
            object.println(((StringBuilder)object22).toString());
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("  Open: ");
            ((StringBuilder)object22).append(this.mIsOpen);
            object.println(((StringBuilder)object22).toString());
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("  Max connections: ");
            ((StringBuilder)object22).append(this.mMaxConnectionPoolSize);
            object.println(((StringBuilder)object22).toString());
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("  Total execution time: ");
            ((StringBuilder)object22).append(this.mTotalExecutionTimeCounter);
            object.println(((StringBuilder)object22).toString());
            object22 = new StringBuilder();
            ((StringBuilder)object22).append("  Configuration: openFlags=");
            ((StringBuilder)object22).append(this.mConfiguration.openFlags);
            ((StringBuilder)object22).append(", isLegacyCompatibilityWalEnabled=");
            ((StringBuilder)object22).append(bl2);
            ((StringBuilder)object22).append(", journalMode=");
            ((StringBuilder)object22).append(TextUtils.emptyIfNull(this.mConfiguration.journalMode));
            ((StringBuilder)object22).append(", syncMode=");
            ((StringBuilder)object22).append(TextUtils.emptyIfNull(this.mConfiguration.syncMode));
            object.println(((StringBuilder)object22).toString());
            if (bl2) {
                object22 = new StringBuilder();
                ((StringBuilder)object22).append("  Compatibility WAL enabled: wal_syncmode=");
                ((StringBuilder)object22).append(SQLiteCompatibilityWalFlags.getWALSyncMode());
                object.println(((StringBuilder)object22).toString());
            }
            if (this.mConfiguration.isLookasideConfigSet()) {
                object22 = new StringBuilder();
                ((StringBuilder)object22).append("  Lookaside config: sz=");
                ((StringBuilder)object22).append(this.mConfiguration.lookasideSlotSize);
                ((StringBuilder)object22).append(" cnt=");
                ((StringBuilder)object22).append(this.mConfiguration.lookasideSlotCount);
                object.println(((StringBuilder)object22).toString());
            }
            if (this.mConfiguration.idleConnectionTimeoutMs != Long.MAX_VALUE) {
                object22 = new StringBuilder();
                ((StringBuilder)object22).append("  Idle connection timeout: ");
                ((StringBuilder)object22).append(this.mConfiguration.idleConnectionTimeoutMs);
                object.println(((StringBuilder)object22).toString());
            }
            object.println("  Available primary connection:");
            if (this.mAvailablePrimaryConnection != null) {
                this.mAvailablePrimaryConnection.dump(printer, bl);
            } else {
                printer.println("<none>");
            }
            object.println("  Available non-primary connections:");
            if (!this.mAvailableNonPrimaryConnections.isEmpty()) {
                int n2 = this.mAvailableNonPrimaryConnections.size();
                for (n = 0; n < n2; ++n) {
                    this.mAvailableNonPrimaryConnections.get(n).dump(printer, bl);
                }
            } else {
                printer.println("<none>");
            }
            object.println("  Acquired connections:");
            if (!this.mAcquiredConnections.isEmpty()) {
                for (Object object22 : this.mAcquiredConnections.entrySet()) {
                    ((SQLiteConnection)object22.getKey()).dumpUnsafe(printer, bl);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("  Status: ");
                    stringBuilder.append(object22.getValue());
                    printer.println(stringBuilder.toString());
                }
            } else {
                printer.println("<none>");
            }
            object.println("  Connection waiters:");
            if (this.mConnectionWaiterQueue != null) {
                n = 0;
                long l = SystemClock.uptimeMillis();
                object = this.mConnectionWaiterQueue;
                while (object != null) {
                    object22 = new StringBuilder();
                    ((StringBuilder)object22).append(n);
                    ((StringBuilder)object22).append(": waited for ");
                    ((StringBuilder)object22).append((float)(l - ((ConnectionWaiter)object).mStartTime) * 0.001f);
                    ((StringBuilder)object22).append(" ms - thread=");
                    ((StringBuilder)object22).append(((ConnectionWaiter)object).mThread);
                    ((StringBuilder)object22).append(", priority=");
                    ((StringBuilder)object22).append(((ConnectionWaiter)object).mPriority);
                    ((StringBuilder)object22).append(", sql='");
                    ((StringBuilder)object22).append(((ConnectionWaiter)object).mSql);
                    ((StringBuilder)object22).append("'");
                    printer.println(((StringBuilder)object22).toString());
                    object = ((ConnectionWaiter)object).mNext;
                    ++n;
                }
            } else {
                printer.println("<none>");
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            this.dispose(true);
            return;
        }
        finally {
            super.finalize();
        }
    }

    public String getPath() {
        return this.mConfiguration.path;
    }

    void onConnectionLeaked() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("A SQLiteConnection object for database '");
        stringBuilder.append(this.mConfiguration.label);
        stringBuilder.append("' was leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed.");
        Log.w(TAG, stringBuilder.toString());
        this.mConnectionLeaked.set(true);
    }

    void onStatementExecuted(long l) {
        this.mTotalExecutionTimeCounter.addAndGet(l);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void reconfigure(SQLiteDatabaseConfiguration object) {
        if (object == null) {
            throw new IllegalArgumentException("configuration must not be null.");
        }
        Object object2 = this.mLock;
        synchronized (object2) {
            this.throwIfClosedLocked();
            int n = ((SQLiteDatabaseConfiguration)object).openFlags;
            int n2 = this.mConfiguration.openFlags;
            int n3 = 0;
            n = ((n ^ n2) & 536870912) != 0 ? 1 : 0;
            if (n != 0) {
                if (!this.mAcquiredConnections.isEmpty()) {
                    object = new IllegalStateException("Write Ahead Logging (WAL) mode cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                    throw object;
                }
                this.closeAvailableNonPrimaryConnectionsAndLogExceptionsLocked();
            }
            if ((n2 = ((SQLiteDatabaseConfiguration)object).foreignKeyConstraintsEnabled != this.mConfiguration.foreignKeyConstraintsEnabled ? 1 : 0) != 0 && !this.mAcquiredConnections.isEmpty()) {
                object = new IllegalStateException("Foreign Key Constraints cannot be enabled or disabled while there are transactions in progress.  Finish all transactions and release all active database connections first.");
                throw object;
            }
            n2 = n3;
            if ((this.mConfiguration.openFlags ^ ((SQLiteDatabaseConfiguration)object).openFlags) == Integer.MIN_VALUE) {
                n2 = 1;
            }
            if (n2 == 0 && this.mConfiguration.openFlags != ((SQLiteDatabaseConfiguration)object).openFlags) {
                if (n != 0) {
                    this.closeAvailableConnectionsAndLogExceptionsLocked();
                }
                SQLiteConnection sQLiteConnection = this.openConnectionLocked((SQLiteDatabaseConfiguration)object, true);
                this.closeAvailableConnectionsAndLogExceptionsLocked();
                this.discardAcquiredConnectionsLocked();
                this.mAvailablePrimaryConnection = sQLiteConnection;
                this.mConfiguration.updateParametersFrom((SQLiteDatabaseConfiguration)object);
                this.setMaxConnectionPoolSizeLocked();
            } else {
                this.mConfiguration.updateParametersFrom((SQLiteDatabaseConfiguration)object);
                this.setMaxConnectionPoolSizeLocked();
                this.closeExcessConnectionsAndLogExceptionsLocked();
                this.reconfigureAllConnectionsLocked();
            }
            this.wakeConnectionWaitersLocked();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void releaseConnection(SQLiteConnection object) {
        Object object2 = this.mLock;
        synchronized (object2) {
            AcquiredConnectionStatus acquiredConnectionStatus;
            if (this.mIdleConnectionHandler != null) {
                this.mIdleConnectionHandler.connectionReleased((SQLiteConnection)object);
            }
            if ((acquiredConnectionStatus = this.mAcquiredConnections.remove(object)) == null) {
                object = new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
                throw object;
            }
            if (!this.mIsOpen) {
                this.closeConnectionAndLogExceptionsLocked((SQLiteConnection)object);
            } else if (((SQLiteConnection)object).isPrimaryConnection()) {
                if (this.recycleConnectionLocked((SQLiteConnection)object, acquiredConnectionStatus)) {
                    this.mAvailablePrimaryConnection = object;
                }
                this.wakeConnectionWaitersLocked();
            } else if (this.mAvailableNonPrimaryConnections.size() >= this.mMaxConnectionPoolSize - 1) {
                this.closeConnectionAndLogExceptionsLocked((SQLiteConnection)object);
            } else {
                if (this.recycleConnectionLocked((SQLiteConnection)object, acquiredConnectionStatus)) {
                    this.mAvailableNonPrimaryConnections.add((SQLiteConnection)object);
                }
                this.wakeConnectionWaitersLocked();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public void setupIdleConnectionHandler(Looper looper, long l) {
        Object object = this.mLock;
        synchronized (object) {
            IdleConnectionHandler idleConnectionHandler;
            this.mIdleConnectionHandler = idleConnectionHandler = new IdleConnectionHandler(looper, l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean shouldYieldConnection(SQLiteConnection object, int n) {
        Object object2 = this.mLock;
        synchronized (object2) {
            if (!this.mAcquiredConnections.containsKey(object)) {
                object = new IllegalStateException("Cannot perform this operation because the specified connection was not acquired from this pool or has already been released.");
                throw object;
            }
            if (this.mIsOpen) return this.isSessionBlockingImportantConnectionWaitersLocked(((SQLiteConnection)object).isPrimaryConnection(), n);
            return false;
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLiteConnectionPool: ");
        stringBuilder.append(this.mConfiguration.path);
        return stringBuilder.toString();
    }

    static enum AcquiredConnectionStatus {
        NORMAL,
        RECONFIGURE,
        DISCARD;
        
    }

    private static final class ConnectionWaiter {
        public SQLiteConnection mAssignedConnection;
        public int mConnectionFlags;
        public RuntimeException mException;
        public ConnectionWaiter mNext;
        public int mNonce;
        public int mPriority;
        public String mSql;
        public long mStartTime;
        public Thread mThread;
        public boolean mWantPrimaryConnection;

        private ConnectionWaiter() {
        }
    }

    private class IdleConnectionHandler
    extends Handler {
        private final long mTimeout;

        IdleConnectionHandler(Looper looper, long l) {
            super(looper);
            this.mTimeout = l;
        }

        void connectionAcquired(SQLiteConnection sQLiteConnection) {
            this.removeMessages(sQLiteConnection.getConnectionId());
        }

        void connectionClosed(SQLiteConnection sQLiteConnection) {
            this.removeMessages(sQLiteConnection.getConnectionId());
        }

        void connectionReleased(SQLiteConnection sQLiteConnection) {
            this.sendEmptyMessageDelayed(sQLiteConnection.getConnectionId(), this.mTimeout);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void handleMessage(Message message) {
            Object object = SQLiteConnectionPool.this.mLock;
            synchronized (object) {
                if (this != SQLiteConnectionPool.this.mIdleConnectionHandler) {
                    return;
                }
                if (SQLiteConnectionPool.this.closeAvailableConnectionLocked(message.what) && Log.isLoggable(SQLiteConnectionPool.TAG, 3)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Closed idle connection ");
                    stringBuilder.append(SQLiteConnectionPool.access$500((SQLiteConnectionPool)SQLiteConnectionPool.this).label);
                    stringBuilder.append(" ");
                    stringBuilder.append(message.what);
                    stringBuilder.append(" after ");
                    stringBuilder.append(this.mTimeout);
                    Log.d(SQLiteConnectionPool.TAG, stringBuilder.toString());
                }
                return;
            }
        }
    }

}

