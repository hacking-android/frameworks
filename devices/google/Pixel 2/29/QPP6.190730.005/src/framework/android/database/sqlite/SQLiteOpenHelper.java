/*
 * Decompiled with CFR 0.145.
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.FileUtils;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.io.File;

public abstract class SQLiteOpenHelper
implements AutoCloseable {
    private static final String TAG = SQLiteOpenHelper.class.getSimpleName();
    private final Context mContext;
    private SQLiteDatabase mDatabase;
    private boolean mIsInitializing;
    private final int mMinimumSupportedVersion;
    @UnsupportedAppUsage
    private final String mName;
    private final int mNewVersion;
    private SQLiteDatabase.OpenParams.Builder mOpenParamsBuilder;

    private SQLiteOpenHelper(Context object, String string2, int n, int n2, SQLiteDatabase.OpenParams.Builder builder) {
        Preconditions.checkNotNull(builder);
        if (n >= 1) {
            this.mContext = object;
            this.mName = string2;
            this.mNewVersion = n;
            this.mMinimumSupportedVersion = Math.max(0, n2);
            this.setOpenParamsBuilder(builder);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Version must be >= 1, was ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public SQLiteOpenHelper(Context context, String string2, int n, SQLiteDatabase.OpenParams openParams) {
        this(context, string2, n, 0, openParams.toBuilder());
    }

    public SQLiteOpenHelper(Context context, String string2, SQLiteDatabase.CursorFactory cursorFactory, int n) {
        this(context, string2, cursorFactory, n, null);
    }

    public SQLiteOpenHelper(Context context, String string2, SQLiteDatabase.CursorFactory cursorFactory, int n, int n2, DatabaseErrorHandler databaseErrorHandler) {
        this(context, string2, n, n2, new SQLiteDatabase.OpenParams.Builder());
        this.mOpenParamsBuilder.setCursorFactory(cursorFactory);
        this.mOpenParamsBuilder.setErrorHandler(databaseErrorHandler);
    }

    public SQLiteOpenHelper(Context context, String string2, SQLiteDatabase.CursorFactory cursorFactory, int n, DatabaseErrorHandler databaseErrorHandler) {
        this(context, string2, cursorFactory, n, 0, databaseErrorHandler);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private SQLiteDatabase getDatabaseLocked(boolean var1_1) {
        block22 : {
            block25 : {
                block24 : {
                    block23 : {
                        var2_2 = this.mDatabase;
                        if (var2_2 != null) {
                            if (!var2_2.isOpen()) {
                                this.mDatabase = null;
                            } else {
                                if (var1_1 == false) return this.mDatabase;
                                if (!this.mDatabase.isReadOnly()) {
                                    return this.mDatabase;
                                }
                            }
                        }
                        if (this.mIsInitializing != false) throw new IllegalStateException("getDatabase called recursively");
                        var4_7 = var3_4 = this.mDatabase;
                        try {
                            this.mIsInitializing = true;
                            if (var3_4 != null) {
                                var2_2 = var3_4;
                                if (var1_1) {
                                    var2_2 = var3_4;
                                    var4_7 = var3_4;
                                    if (var3_4.isReadOnly()) {
                                        var4_7 = var3_4;
                                        var3_4.reopenReadWrite();
                                        var2_2 = var3_4;
                                    }
                                }
                            } else {
                                var4_7 = var3_4;
                                if (this.mName == null) {
                                    var4_7 = var3_4;
                                    var2_2 = SQLiteDatabase.createInMemory(this.mOpenParamsBuilder.build());
                                } else {
                                    var4_7 = var3_4;
                                    var5_8 = this.mContext.getDatabasePath(this.mName);
                                    var4_7 = var3_4;
                                    var6_9 = this.mOpenParamsBuilder.build();
                                    var2_2 = var3_4;
                                    var4_7 = var3_4;
                                    try {
                                        var2_2 = var3_4 = SQLiteDatabase.openDatabase(var5_8, (SQLiteDatabase.OpenParams)var6_9);
                                        var4_7 = var3_4;
                                        SQLiteOpenHelper.setFilePermissionsForDb(var5_8.getPath());
                                        var2_2 = var3_4;
                                    }
                                    catch (SQLException var3_5) {
                                        if (var1_1) break block22;
                                        var4_7 = var2_2;
                                        var7_10 = SQLiteOpenHelper.TAG;
                                        var4_7 = var2_2;
                                        var4_7 = var2_2;
                                        var8_11 = new StringBuilder();
                                        var4_7 = var2_2;
                                        var8_11.append("Couldn't open ");
                                        var4_7 = var2_2;
                                        var8_11.append(this.mName);
                                        var4_7 = var2_2;
                                        var8_11.append(" for writing (will try read-only):");
                                        var4_7 = var2_2;
                                        Log.e(var7_10, var8_11.toString(), var3_5);
                                        var4_7 = var2_2;
                                        var2_2 = SQLiteDatabase.openDatabase(var5_8, var6_9.toBuilder().addOpenFlags(1).build());
                                    }
                                }
                            }
                            var4_7 = var2_2;
                            this.onConfigure((SQLiteDatabase)var2_2);
                            var4_7 = var2_2;
                            var9_12 = var2_2.getVersion();
                            var4_7 = var2_2;
                            if (var9_12 == this.mNewVersion) ** GOTO lbl165
                            var4_7 = var2_2;
                            if (var2_2.isReadOnly()) ** GOTO lbl139
                            if (var9_12 <= 0) ** GOTO lbl117
                            var4_7 = var2_2;
                            if (var9_12 >= this.mMinimumSupportedVersion) ** GOTO lbl117
                            var4_7 = var2_2;
                            var4_7 = var2_2;
                            var3_4 = new File(var2_2.getPath());
                            var4_7 = var2_2;
                            this.onBeforeDelete((SQLiteDatabase)var2_2);
                            var4_7 = var2_2;
                            var2_2.close();
                            var4_7 = var2_2;
                            if (!SQLiteDatabase.deleteDatabase((File)var3_4)) break block23;
                            var4_7 = var2_2;
                            this.mIsInitializing = false;
                            var4_7 = var2_2;
                            var3_4 = this.getDatabaseLocked(var1_1);
                            this.mIsInitializing = false;
                            if (var2_2 == this.mDatabase) return var3_4;
                        }
                        catch (Throwable var2_3) {
                            this.mIsInitializing = false;
                            if (var4_7 == null) throw var2_3;
                            if (var4_7 == this.mDatabase) throw var2_3;
                            var4_7.close();
                            throw var2_3;
                        }
                        var2_2.close();
                        return var3_4;
                    }
                    var4_7 = var2_2;
                    var4_7 = var2_2;
                    var4_7 = var2_2;
                    var3_4 = new StringBuilder();
                    var4_7 = var2_2;
                    var3_4.append("Unable to delete obsolete database ");
                    var4_7 = var2_2;
                    var3_4.append(this.mName);
                    var4_7 = var2_2;
                    var3_4.append(" with version ");
                    var4_7 = var2_2;
                    var3_4.append(var9_12);
                    var4_7 = var2_2;
                    var6_9 = new IllegalStateException(var3_4.toString());
                    var4_7 = var2_2;
                    throw var6_9;
lbl117: // 2 sources:
                    var4_7 = var2_2;
                    var2_2.beginTransaction();
                    if (var9_12 != 0) ** GOTO lbl123
                    this.onCreate((SQLiteDatabase)var2_2);
                    break block24;
lbl123: // 1 sources:
                    if (var9_12 > this.mNewVersion) {
                        this.onDowngrade((SQLiteDatabase)var2_2, var9_12, this.mNewVersion);
                    } else {
                        this.onUpgrade((SQLiteDatabase)var2_2, var9_12, this.mNewVersion);
                    }
                }
                var2_2.setVersion(this.mNewVersion);
                var2_2.setTransactionSuccessful();
                var4_7 = var2_2;
                var2_2.endTransaction();
                break block25;
                catch (Throwable var3_6) {
                    var4_7 = var2_2;
                    var2_2.endTransaction();
                    var4_7 = var2_2;
                    throw var3_6;
                }
lbl139: // 1 sources:
                var4_7 = var2_2;
                var4_7 = var2_2;
                var4_7 = var2_2;
                var3_4 = new StringBuilder();
                var4_7 = var2_2;
                var3_4.append("Can't upgrade read-only database from version ");
                var4_7 = var2_2;
                var3_4.append(var2_2.getVersion());
                var4_7 = var2_2;
                var3_4.append(" to ");
                var4_7 = var2_2;
                var3_4.append(this.mNewVersion);
                var4_7 = var2_2;
                var3_4.append(": ");
                var4_7 = var2_2;
                var3_4.append(this.mName);
                var4_7 = var2_2;
                var6_9 = new SQLiteException(var3_4.toString());
                var4_7 = var2_2;
                throw var6_9;
            }
            var4_7 = var2_2;
            this.onOpen((SQLiteDatabase)var2_2);
            var4_7 = var2_2;
            if (var2_2.isReadOnly()) {
                var4_7 = var2_2;
                var3_4 = SQLiteOpenHelper.TAG;
                var4_7 = var2_2;
                var4_7 = var2_2;
                var6_9 = new StringBuilder();
                var4_7 = var2_2;
                var6_9.append("Opened ");
                var4_7 = var2_2;
                var6_9.append(this.mName);
                var4_7 = var2_2;
                var6_9.append(" in read-only mode");
                var4_7 = var2_2;
                Log.w((String)var3_4, var6_9.toString());
            }
            var4_7 = var2_2;
            this.mDatabase = var2_2;
            this.mIsInitializing = false;
            if (var2_2 == this.mDatabase) return var2_2;
            var2_2.close();
            return var2_2;
        }
        var4_7 = var2_2;
        throw var3_5;
    }

    private static void setFilePermissionsForDb(String string2) {
        FileUtils.setPermissions(string2, 432, -1, -1);
    }

    private void setOpenParamsBuilder(SQLiteDatabase.OpenParams.Builder builder) {
        this.mOpenParamsBuilder = builder;
        this.mOpenParamsBuilder.addOpenFlags(268435456);
    }

    @Override
    public void close() {
        synchronized (this) {
            if (!this.mIsInitializing) {
                if (this.mDatabase != null && this.mDatabase.isOpen()) {
                    this.mDatabase.close();
                    this.mDatabase = null;
                }
                return;
            }
            IllegalStateException illegalStateException = new IllegalStateException("Closed during initialization");
            throw illegalStateException;
        }
    }

    public String getDatabaseName() {
        return this.mName;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLiteDatabase getReadableDatabase() {
        synchronized (this) {
            return this.getDatabaseLocked(false);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SQLiteDatabase getWritableDatabase() {
        synchronized (this) {
            return this.getDatabaseLocked(true);
        }
    }

    public void onBeforeDelete(SQLiteDatabase sQLiteDatabase) {
    }

    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onCreate(SQLiteDatabase var1);

    public void onDowngrade(SQLiteDatabase object, int n, int n2) {
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't downgrade database from version ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" to ");
        ((StringBuilder)object).append(n2);
        throw new SQLiteException(((StringBuilder)object).toString());
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
    }

    public abstract void onUpgrade(SQLiteDatabase var1, int var2, int var3);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setIdleConnectionTimeout(long l) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                IllegalStateException illegalStateException = new IllegalStateException("Connection timeout setting cannot be changed after opening the database");
                throw illegalStateException;
            }
            this.mOpenParamsBuilder.setIdleConnectionTimeout(l);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setLookasideConfig(int n, int n2) {
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                IllegalStateException illegalStateException = new IllegalStateException("Lookaside memory config cannot be changed after opening the database");
                throw illegalStateException;
            }
            this.mOpenParamsBuilder.setLookasideConfig(n, n2);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOpenParams(SQLiteDatabase.OpenParams object) {
        Preconditions.checkNotNull(object);
        synchronized (this) {
            if (this.mDatabase != null && this.mDatabase.isOpen()) {
                object = new IllegalStateException("OpenParams cannot be set after opening the database");
                throw object;
            }
            SQLiteDatabase.OpenParams.Builder builder = new SQLiteDatabase.OpenParams.Builder((SQLiteDatabase.OpenParams)object);
            this.setOpenParamsBuilder(builder);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setWriteAheadLoggingEnabled(boolean bl) {
        synchronized (this) {
            if (this.mOpenParamsBuilder.isWriteAheadLoggingEnabled() != bl) {
                if (this.mDatabase != null && this.mDatabase.isOpen() && !this.mDatabase.isReadOnly()) {
                    if (bl) {
                        this.mDatabase.enableWriteAheadLogging();
                    } else {
                        this.mDatabase.disableWriteAheadLogging();
                    }
                }
                this.mOpenParamsBuilder.setWriteAheadLoggingEnabled(bl);
            }
            this.mOpenParamsBuilder.removeOpenFlags(Integer.MIN_VALUE);
            return;
        }
    }
}

