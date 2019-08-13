/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConnection;
import android.database.sqlite.SQLiteConnectionPool;
import android.database.sqlite.SQLiteStatementInfo;
import android.database.sqlite.SQLiteTransactionListener;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;

public final class SQLiteSession {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int TRANSACTION_MODE_DEFERRED = 0;
    public static final int TRANSACTION_MODE_EXCLUSIVE = 2;
    public static final int TRANSACTION_MODE_IMMEDIATE = 1;
    private SQLiteConnection mConnection;
    private int mConnectionFlags;
    private final SQLiteConnectionPool mConnectionPool;
    private int mConnectionUseCount;
    private Transaction mTransactionPool;
    private Transaction mTransactionStack;

    public SQLiteSession(SQLiteConnectionPool sQLiteConnectionPool) {
        if (sQLiteConnectionPool != null) {
            this.mConnectionPool = sQLiteConnectionPool;
            return;
        }
        throw new IllegalArgumentException("connectionPool must not be null");
    }

    private void acquireConnection(String string2, int n, CancellationSignal cancellationSignal) {
        if (this.mConnection == null) {
            this.mConnection = this.mConnectionPool.acquireConnection(string2, n, cancellationSignal);
            this.mConnectionFlags = n;
        }
        ++this.mConnectionUseCount;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void beginTransactionUnchecked(int n, SQLiteTransactionListener object, int n2, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (this.mTransactionStack == null) {
            this.acquireConnection(null, n2, cancellationSignal);
        }
        try {
            if (this.mTransactionStack == null) {
                if (n != 1) {
                    if (n != 2) {
                        this.mConnection.execute("BEGIN;", null, cancellationSignal);
                    } else {
                        this.mConnection.execute("BEGIN EXCLUSIVE;", null, cancellationSignal);
                    }
                } else {
                    this.mConnection.execute("BEGIN IMMEDIATE;", null, cancellationSignal);
                }
            }
            if (object != null) {
                try {
                    object.onBegin();
                }
                catch (RuntimeException runtimeException) {
                    if (this.mTransactionStack != null) throw runtimeException;
                    this.mConnection.execute("ROLLBACK;", null, cancellationSignal);
                    throw runtimeException;
                }
            }
            object = this.obtainTransaction(n, (SQLiteTransactionListener)object);
            ((Transaction)object).mParent = this.mTransactionStack;
            this.mTransactionStack = object;
            return;
        }
        finally {
            if (this.mTransactionStack == null) {
                this.releaseConnection();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void endTransactionUnchecked(CancellationSignal var1_1, boolean var2_3) {
        block10 : {
            block11 : {
                block9 : {
                    block7 : {
                        if (var1_1 != null) {
                            var1_1.throwIfCanceled();
                        }
                        var3_4 = this.mTransactionStack;
                        var4_5 = (var3_4.mMarkedSuccessful != false || var2_3 != false) && var3_4.mChildFailed == false;
                        var5_6 = null;
                        var6_7 = var3_4.mListener;
                        var7_8 = var4_5;
                        var8_9 = var5_6;
                        if (var6_7 == null) break block7;
                        if (!var4_5) ** GOTO lbl14
                        try {
                            block8 : {
                                var6_7.onCommit();
                                break block8;
lbl14: // 1 sources:
                                var6_7.onRollback();
                            }
                            var7_8 = var4_5;
                            var8_9 = var5_6;
                        }
                        catch (RuntimeException var8_10) {
                            var7_8 = false;
                        }
                    }
                    this.mTransactionStack = var3_4.mParent;
                    this.recycleTransaction(var3_4);
                    var5_6 = this.mTransactionStack;
                    if (var5_6 == null) break block9;
                    if (!var7_8) {
                        var5_6.mChildFailed = true;
                    }
                    break block10;
                }
                if (!var7_8) ** GOTO lbl33
                break block11;
lbl33: // 1 sources:
                this.mConnection.execute("ROLLBACK;", null, var1_1);
            }
            this.mConnection.execute("COMMIT;", null, var1_1);
        }
        if (var8_9 != null) throw var8_9;
        return;
        finally {
            this.releaseConnection();
        }
    }

    private boolean executeSpecial(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        int n2;
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if ((n2 = DatabaseUtils.getSqlStatementType(string2)) != 4) {
            if (n2 != 5) {
                if (n2 != 6) {
                    return false;
                }
                this.endTransaction(cancellationSignal);
                return true;
            }
            this.setTransactionSuccessful();
            this.endTransaction(cancellationSignal);
            return true;
        }
        this.beginTransaction(2, null, n, cancellationSignal);
        return true;
    }

    private Transaction obtainTransaction(int n, SQLiteTransactionListener sQLiteTransactionListener) {
        Transaction transaction = this.mTransactionPool;
        if (transaction != null) {
            this.mTransactionPool = transaction.mParent;
            transaction.mParent = null;
            transaction.mMarkedSuccessful = false;
            transaction.mChildFailed = false;
        } else {
            transaction = new Transaction();
        }
        transaction.mMode = n;
        transaction.mListener = sQLiteTransactionListener;
        return transaction;
    }

    private void recycleTransaction(Transaction transaction) {
        transaction.mParent = this.mTransactionPool;
        transaction.mListener = null;
        this.mTransactionPool = transaction;
    }

    private void releaseConnection() {
        int n;
        this.mConnectionUseCount = n = this.mConnectionUseCount - 1;
        if (n == 0) {
            try {
                this.mConnectionPool.releaseConnection(this.mConnection);
            }
            finally {
                this.mConnection = null;
            }
        }
    }

    private void throwIfNestedTransaction() {
        if (!this.hasNestedTransaction()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because a nested transaction is in progress.");
    }

    private void throwIfNoTransaction() {
        if (this.mTransactionStack != null) {
            return;
        }
        throw new IllegalStateException("Cannot perform this operation because there is no current transaction.");
    }

    private void throwIfTransactionMarkedSuccessful() {
        Transaction transaction = this.mTransactionStack;
        if (transaction != null && transaction.mMarkedSuccessful) {
            throw new IllegalStateException("Cannot perform this operation because the transaction has already been marked successful.  The only thing you can do now is call endTransaction().");
        }
    }

    private boolean yieldTransactionUnchecked(long l, CancellationSignal cancellationSignal) {
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if (!this.mConnectionPool.shouldYieldConnection(this.mConnection, this.mConnectionFlags)) {
            return false;
        }
        int n = this.mTransactionStack.mMode;
        SQLiteTransactionListener sQLiteTransactionListener = this.mTransactionStack.mListener;
        int n2 = this.mConnectionFlags;
        this.endTransactionUnchecked(cancellationSignal, true);
        if (l > 0L) {
            try {
                Thread.sleep(l);
            }
            catch (InterruptedException interruptedException) {
                // empty catch block
            }
        }
        this.beginTransactionUnchecked(n, sQLiteTransactionListener, n2, cancellationSignal);
        return true;
    }

    @UnsupportedAppUsage
    public void beginTransaction(int n, SQLiteTransactionListener sQLiteTransactionListener, int n2, CancellationSignal cancellationSignal) {
        this.throwIfTransactionMarkedSuccessful();
        this.beginTransactionUnchecked(n, sQLiteTransactionListener, n2, cancellationSignal);
    }

    public void endTransaction(CancellationSignal cancellationSignal) {
        this.throwIfNoTransaction();
        this.endTransactionUnchecked(cancellationSignal, false);
    }

    public void execute(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (this.executeSpecial(string2, arrobject, n, cancellationSignal)) {
                return;
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                this.mConnection.execute(string2, arrobject, cancellationSignal);
                return;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public ParcelFileDescriptor executeForBlobFileDescriptor(String object, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (object != null) {
            if (this.executeSpecial((String)object, arrobject, n, cancellationSignal)) {
                return null;
            }
            this.acquireConnection((String)object, n, cancellationSignal);
            try {
                object = this.mConnection.executeForBlobFileDescriptor((String)object, arrobject, cancellationSignal);
                return object;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public int executeForChangedRowCount(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (this.executeSpecial(string2, arrobject, n, cancellationSignal)) {
                return 0;
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                n = this.mConnection.executeForChangedRowCount(string2, arrobject, cancellationSignal);
                return n;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public int executeForCursorWindow(String string2, Object[] arrobject, CursorWindow cursorWindow, int n, int n2, boolean bl, int n3, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (cursorWindow != null) {
                if (this.executeSpecial(string2, arrobject, n3, cancellationSignal)) {
                    cursorWindow.clear();
                    return 0;
                }
                this.acquireConnection(string2, n3, cancellationSignal);
                try {
                    n = this.mConnection.executeForCursorWindow(string2, arrobject, cursorWindow, n, n2, bl, cancellationSignal);
                    return n;
                }
                finally {
                    this.releaseConnection();
                }
            }
            throw new IllegalArgumentException("window must not be null.");
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public long executeForLastInsertedRowId(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (this.executeSpecial(string2, arrobject, n, cancellationSignal)) {
                return 0L;
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                long l = this.mConnection.executeForLastInsertedRowId(string2, arrobject, cancellationSignal);
                return l;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public long executeForLong(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (this.executeSpecial(string2, arrobject, n, cancellationSignal)) {
                return 0L;
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                long l = this.mConnection.executeForLong(string2, arrobject, cancellationSignal);
                return l;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public String executeForString(String string2, Object[] arrobject, int n, CancellationSignal cancellationSignal) {
        if (string2 != null) {
            if (this.executeSpecial(string2, arrobject, n, cancellationSignal)) {
                return null;
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                string2 = this.mConnection.executeForString(string2, arrobject, cancellationSignal);
                return string2;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public boolean hasConnection() {
        boolean bl = this.mConnection != null;
        return bl;
    }

    public boolean hasNestedTransaction() {
        Transaction transaction = this.mTransactionStack;
        boolean bl = transaction != null && transaction.mParent != null;
        return bl;
    }

    public boolean hasTransaction() {
        boolean bl = this.mTransactionStack != null;
        return bl;
    }

    public void prepare(String string2, int n, CancellationSignal cancellationSignal, SQLiteStatementInfo sQLiteStatementInfo) {
        if (string2 != null) {
            if (cancellationSignal != null) {
                cancellationSignal.throwIfCanceled();
            }
            this.acquireConnection(string2, n, cancellationSignal);
            try {
                this.mConnection.prepare(string2, sQLiteStatementInfo);
                return;
            }
            finally {
                this.releaseConnection();
            }
        }
        throw new IllegalArgumentException("sql must not be null.");
    }

    public void setTransactionSuccessful() {
        this.throwIfNoTransaction();
        this.throwIfTransactionMarkedSuccessful();
        this.mTransactionStack.mMarkedSuccessful = true;
    }

    public boolean yieldTransaction(long l, boolean bl, CancellationSignal cancellationSignal) {
        block6 : {
            block5 : {
                block4 : {
                    if (!bl) break block4;
                    this.throwIfNoTransaction();
                    this.throwIfTransactionMarkedSuccessful();
                    this.throwIfNestedTransaction();
                    break block5;
                }
                Transaction transaction = this.mTransactionStack;
                if (transaction == null || transaction.mMarkedSuccessful || this.mTransactionStack.mParent != null) break block6;
            }
            if (this.mTransactionStack.mChildFailed) {
                return false;
            }
            return this.yieldTransactionUnchecked(l, cancellationSignal);
        }
        return false;
    }

    private static final class Transaction {
        public boolean mChildFailed;
        public SQLiteTransactionListener mListener;
        public boolean mMarkedSuccessful;
        public int mMode;
        public Transaction mParent;

        private Transaction() {
        }
    }

}

