/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteSession;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;

public final class SQLiteStatement
extends SQLiteProgram {
    @UnsupportedAppUsage
    SQLiteStatement(SQLiteDatabase sQLiteDatabase, String string2, Object[] arrobject) {
        super(sQLiteDatabase, string2, arrobject, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void execute() {
        Throwable throwable2222;
        this.acquireReference();
        this.getSession().execute(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
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
    public long executeInsert() {
        Throwable throwable2222;
        this.acquireReference();
        long l = this.getSession().executeForLastInsertedRowId(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return l;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
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
    public int executeUpdateDelete() {
        Throwable throwable2222;
        this.acquireReference();
        int n = this.getSession().executeForChangedRowCount(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
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
    public ParcelFileDescriptor simpleQueryForBlobFileDescriptor() {
        Throwable throwable2222;
        this.acquireReference();
        ParcelFileDescriptor parcelFileDescriptor = this.getSession().executeForBlobFileDescriptor(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return parcelFileDescriptor;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
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
    public long simpleQueryForLong() {
        Throwable throwable2222;
        this.acquireReference();
        long l = this.getSession().executeForLong(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return l;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
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
    public String simpleQueryForString() {
        Throwable throwable2222;
        this.acquireReference();
        String string2 = this.getSession().executeForString(this.getSql(), this.getBindArgs(), this.getConnectionFlags(), null);
        this.releaseReference();
        return string2;
        {
            catch (Throwable throwable2222) {
            }
            catch (SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException) {}
            {
                this.onCorruption();
                throw sQLiteDatabaseCorruptException;
            }
        }
        this.releaseReference();
        throw throwable2222;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SQLiteProgram: ");
        stringBuilder.append(this.getSql());
        return stringBuilder.toString();
    }
}

