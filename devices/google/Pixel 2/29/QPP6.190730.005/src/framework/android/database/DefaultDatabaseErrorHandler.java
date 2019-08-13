/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.util.Pair;
import java.io.File;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public final class DefaultDatabaseErrorHandler
implements DatabaseErrorHandler {
    private static final String TAG = "DefaultDatabaseErrorHandler";

    private void deleteDatabaseFile(String charSequence) {
        if (!((String)charSequence).equalsIgnoreCase(":memory:") && ((String)charSequence).trim().length() != 0) {
            Serializable serializable = new StringBuilder();
            serializable.append("deleting the database file: ");
            serializable.append((String)charSequence);
            Log.e(TAG, serializable.toString());
            try {
                serializable = new File((String)charSequence);
                SQLiteDatabase.deleteDatabase((File)serializable, false);
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("delete failed: ");
                ((StringBuilder)charSequence).append(exception.getMessage());
                Log.w(TAG, ((StringBuilder)charSequence).toString());
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void onCorruption(SQLiteDatabase iterator) {
        StringBuilder stringBuilder;
        Throwable throwable22;
        block11 : {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("Corruption reported by sqlite on database: ");
            ((StringBuilder)object).append(((SQLiteDatabase)((Object)iterator)).getPath());
            Log.e(TAG, ((StringBuilder)object).toString());
            SQLiteDatabase.wipeDetected(((SQLiteDatabase)((Object)iterator)).getPath(), "corruption");
            if (!((SQLiteDatabase)((Object)iterator)).isOpen()) {
                this.deleteDatabaseFile(((SQLiteDatabase)((Object)iterator)).getPath());
                return;
            }
            object = null;
            stringBuilder = null;
            try {
                try {
                    List<Pair<String, String>> list = ((SQLiteDatabase)((Object)iterator)).getAttachedDbs();
                    object = list;
                }
                catch (SQLiteException sQLiteException) {
                    // empty catch block
                }
                stringBuilder = object;
                try {
                    ((SQLiteClosable)((Object)iterator)).close();
                }
                catch (SQLiteException sQLiteException) {
                    // empty catch block
                }
                if (object == null) break block11;
                iterator = object.iterator();
            }
            catch (Throwable throwable22) {}
            while (iterator.hasNext()) {
                this.deleteDatabaseFile((String)((Pair)iterator.next()).second);
            }
            return;
        }
        this.deleteDatabaseFile(((SQLiteDatabase)((Object)iterator)).getPath());
        return;
        if (stringBuilder != null) {
            iterator = stringBuilder.iterator();
            while (iterator.hasNext()) {
                this.deleteDatabaseFile((String)((Pair)iterator.next()).second);
            }
            throw throwable22;
        } else {
            this.deleteDatabaseFile(((SQLiteDatabase)((Object)iterator)).getPath());
        }
        throw throwable22;
    }
}

