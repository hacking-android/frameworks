/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteSession;
import android.database.sqlite.SQLiteStatementInfo;
import android.os.CancellationSignal;
import java.util.Arrays;

public abstract class SQLiteProgram
extends SQLiteClosable {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    @UnsupportedAppUsage
    private final Object[] mBindArgs;
    private final String[] mColumnNames;
    private final SQLiteDatabase mDatabase;
    private final int mNumParameters;
    private final boolean mReadOnly;
    @UnsupportedAppUsage
    private final String mSql;

    SQLiteProgram(SQLiteDatabase object, String object2, Object[] arrobject, CancellationSignal cancellationSignal) {
        this.mDatabase = object;
        this.mSql = ((String)object2).trim();
        int n = DatabaseUtils.getSqlStatementType(this.mSql);
        if (n != 4 && n != 5 && n != 6) {
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            object2 = new SQLiteStatementInfo();
            ((SQLiteDatabase)object).getThreadSession().prepare(this.mSql, ((SQLiteDatabase)object).getThreadDefaultConnectionFlags(bl), cancellationSignal, (SQLiteStatementInfo)object2);
            this.mReadOnly = ((SQLiteStatementInfo)object2).readOnly;
            this.mColumnNames = ((SQLiteStatementInfo)object2).columnNames;
            this.mNumParameters = ((SQLiteStatementInfo)object2).numParameters;
        } else {
            this.mReadOnly = false;
            this.mColumnNames = EMPTY_STRING_ARRAY;
            this.mNumParameters = 0;
        }
        if (arrobject != null && arrobject.length > this.mNumParameters) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Too many bind arguments.  ");
            ((StringBuilder)object).append(arrobject.length);
            ((StringBuilder)object).append(" arguments were provided but the statement needs ");
            ((StringBuilder)object).append(this.mNumParameters);
            ((StringBuilder)object).append(" arguments.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        n = this.mNumParameters;
        if (n != 0) {
            this.mBindArgs = new Object[n];
            if (arrobject != null) {
                System.arraycopy(arrobject, 0, this.mBindArgs, 0, arrobject.length);
            }
        } else {
            this.mBindArgs = null;
        }
    }

    private void bind(int n, Object object) {
        if (n >= 1 && n <= this.mNumParameters) {
            this.mBindArgs[n - 1] = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Cannot bind argument at index ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" because the index is out of range.  The statement has ");
        ((StringBuilder)object).append(this.mNumParameters);
        ((StringBuilder)object).append(" parameters.");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void bindAllArgsAsStrings(String[] arrstring) {
        if (arrstring != null) {
            for (int i = arrstring.length; i != 0; --i) {
                this.bindString(i, arrstring[i - 1]);
            }
        }
    }

    public void bindBlob(int n, byte[] object) {
        if (object != null) {
            this.bind(n, object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("the bind value at index ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" is null");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void bindDouble(int n, double d) {
        this.bind(n, d);
    }

    public void bindLong(int n, long l) {
        this.bind(n, l);
    }

    public void bindNull(int n) {
        this.bind(n, null);
    }

    public void bindString(int n, String charSequence) {
        if (charSequence != null) {
            this.bind(n, charSequence);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("the bind value at index ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(" is null");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    public void clearBindings() {
        Object[] arrobject = this.mBindArgs;
        if (arrobject != null) {
            Arrays.fill(arrobject, null);
        }
    }

    final Object[] getBindArgs() {
        return this.mBindArgs;
    }

    final String[] getColumnNames() {
        return this.mColumnNames;
    }

    protected final int getConnectionFlags() {
        return this.mDatabase.getThreadDefaultConnectionFlags(this.mReadOnly);
    }

    final SQLiteDatabase getDatabase() {
        return this.mDatabase;
    }

    protected final SQLiteSession getSession() {
        return this.mDatabase.getThreadSession();
    }

    final String getSql() {
        return this.mSql;
    }

    @Deprecated
    public final int getUniqueId() {
        return -1;
    }

    @Override
    protected void onAllReferencesReleased() {
        this.clearBindings();
    }

    protected final void onCorruption() {
        this.mDatabase.onCorruption();
    }
}

