/*
 * Decompiled with CFR 0.145.
 */
package android.database;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteClosable;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.database.sqlite.SQLiteProgram;
import android.database.sqlite.SQLiteStatement;
import android.os.OperationCanceledException;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.util.ArrayUtils;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Serializable;
import java.text.CollationKey;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class DatabaseUtils {
    private static final boolean DEBUG = false;
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    public static final int STATEMENT_ABORT = 6;
    public static final int STATEMENT_ATTACH = 3;
    public static final int STATEMENT_BEGIN = 4;
    public static final int STATEMENT_COMMIT = 5;
    public static final int STATEMENT_DDL = 8;
    public static final int STATEMENT_OTHER = 99;
    public static final int STATEMENT_PRAGMA = 7;
    public static final int STATEMENT_SELECT = 1;
    public static final int STATEMENT_UNPREPARED = 9;
    public static final int STATEMENT_UPDATE = 2;
    private static final String TAG = "DatabaseUtils";
    private static Collator mColl = null;

    public static void appendEscapedSQLString(StringBuilder stringBuilder, String string2) {
        stringBuilder.append('\'');
        if (string2.indexOf(39) != -1) {
            int n = string2.length();
            for (int i = 0; i < n; ++i) {
                char c = string2.charAt(i);
                if (c == '\'') {
                    stringBuilder.append('\'');
                }
                stringBuilder.append(c);
            }
        } else {
            stringBuilder.append(string2);
        }
        stringBuilder.append('\'');
    }

    public static String[] appendSelectionArgs(String[] arrstring, String[] arrstring2) {
        if (arrstring != null && arrstring.length != 0) {
            String[] arrstring3 = new String[arrstring.length + arrstring2.length];
            System.arraycopy(arrstring, 0, arrstring3, 0, arrstring.length);
            System.arraycopy(arrstring2, 0, arrstring3, arrstring.length, arrstring2.length);
            return arrstring3;
        }
        return arrstring2;
    }

    public static final void appendValueToSql(StringBuilder stringBuilder, Object object) {
        if (object == null) {
            stringBuilder.append("NULL");
        } else if (object instanceof Boolean) {
            if (((Boolean)object).booleanValue()) {
                stringBuilder.append('1');
            } else {
                stringBuilder.append('0');
            }
        } else {
            DatabaseUtils.appendEscapedSQLString(stringBuilder, object.toString());
        }
    }

    public static void bindObjectToProgram(SQLiteProgram sQLiteProgram, int n, Object object) {
        if (object == null) {
            sQLiteProgram.bindNull(n);
        } else if (!(object instanceof Double) && !(object instanceof Float)) {
            if (object instanceof Number) {
                sQLiteProgram.bindLong(n, ((Number)object).longValue());
            } else if (object instanceof Boolean) {
                if (((Boolean)object).booleanValue()) {
                    sQLiteProgram.bindLong(n, 1L);
                } else {
                    sQLiteProgram.bindLong(n, 0L);
                }
            } else if (object instanceof byte[]) {
                sQLiteProgram.bindBlob(n, (byte[])object);
            } else {
                sQLiteProgram.bindString(n, object.toString());
            }
        } else {
            sQLiteProgram.bindDouble(n, ((Number)object).doubleValue());
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String bindSelection(String string2, Object ... arrobject) {
        if (string2 == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(arrobject)) {
            return string2;
        }
        if (string2.indexOf(63) == -1) {
            return string2;
        }
        int n = 32;
        int n2 = 0;
        int n3 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n3);
        int n4 = 0;
        while (n4 < n3) {
            int n5 = n4 + 1;
            char c = string2.charAt(n4);
            if (c == '?') {
                int n6;
                int n7 = 32;
                n4 = n5;
                do {
                    n6 = n7;
                    if (n4 >= n3 || (n6 = (int)string2.charAt(n4)) < 48 || n6 > 57) break;
                    ++n4;
                } while (true);
                if (n5 != n4) {
                    n2 = Integer.parseInt(string2.substring(n5, n4)) - 1;
                }
                Object object = arrobject[n2];
                if (n != 32 && n != 61) {
                    stringBuilder.append(' ');
                }
                if ((n5 = DatabaseUtils.getTypeOfObject(object)) != 0) {
                    if (n5 != 1) {
                        if (n5 != 2) {
                            if (n5 == 4) throw new IllegalArgumentException("Blobs not supported");
                            if (object instanceof Boolean) {
                                stringBuilder.append((int)((Boolean)object).booleanValue());
                            } else {
                                stringBuilder.append('\'');
                                stringBuilder.append(object.toString());
                                stringBuilder.append('\'');
                            }
                        } else {
                            stringBuilder.append(((Number)object).doubleValue());
                        }
                    } else {
                        stringBuilder.append(((Number)object).longValue());
                    }
                } else {
                    stringBuilder.append("NULL");
                }
                if (n6 != 32) {
                    stringBuilder.append(' ');
                }
                ++n2;
                continue;
            }
            stringBuilder.append(c);
            n = c;
            n4 = n5;
        }
        return stringBuilder.toString();
    }

    public static ParcelFileDescriptor blobFileDescriptorForQuery(SQLiteDatabase sQLiteClosable, String object, String[] arrstring) {
        sQLiteClosable = ((SQLiteDatabase)sQLiteClosable).compileStatement((String)object);
        try {
            object = DatabaseUtils.blobFileDescriptorForQuery((SQLiteStatement)sQLiteClosable, arrstring);
            return object;
        }
        finally {
            sQLiteClosable.close();
        }
    }

    public static ParcelFileDescriptor blobFileDescriptorForQuery(SQLiteStatement sQLiteStatement, String[] arrstring) {
        sQLiteStatement.bindAllArgsAsStrings(arrstring);
        return sQLiteStatement.simpleQueryForBlobFileDescriptor();
    }

    public static String concatenateWhere(String string2, String string3) {
        if (TextUtils.isEmpty(string2)) {
            return string3;
        }
        if (TextUtils.isEmpty(string3)) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(string2);
        stringBuilder.append(") AND (");
        stringBuilder.append(string3);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static void createDbFromSqlStatements(Context object, String arrstring, int n, String string22) {
        object = ((Context)object).openOrCreateDatabase((String)arrstring, 0, null);
        for (String string22 : TextUtils.split(string22, ";\n")) {
            if (TextUtils.isEmpty(string22)) continue;
            ((SQLiteDatabase)object).execSQL(string22);
        }
        ((SQLiteDatabase)object).setVersion(n);
        ((SQLiteClosable)object).close();
    }

    public static void cursorDoubleToContentValues(Cursor cursor, String string2, ContentValues contentValues, String string3) {
        int n = cursor.getColumnIndex(string2);
        if (!cursor.isNull(n)) {
            contentValues.put(string3, cursor.getDouble(n));
        } else {
            contentValues.put(string3, (Double)null);
        }
    }

    public static void cursorDoubleToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, cursor.getDouble(n));
        }
    }

    public static void cursorDoubleToCursorValues(Cursor cursor, String string2, ContentValues contentValues) {
        DatabaseUtils.cursorDoubleToContentValues(cursor, string2, contentValues, string2);
    }

    public static void cursorFillWindow(Cursor cursor, int n, CursorWindow cursorWindow) {
        if (n >= 0 && n < cursor.getCount()) {
            int n2 = cursor.getPosition();
            int n3 = cursor.getColumnCount();
            cursorWindow.clear();
            cursorWindow.setStartPosition(n);
            cursorWindow.setNumColumns(n3);
            if (cursor.moveToPosition(n)) {
                block0 : while (cursorWindow.allocRow()) {
                    for (int i = 0; i < n3; ++i) {
                        byte[] arrby;
                        int n4 = cursor.getType(i);
                        boolean bl = n4 != 0 ? (n4 != 1 ? (n4 != 2 ? (n4 != 4 ? ((arrby = cursor.getString(i)) != null ? cursorWindow.putString((String)arrby, n, i) : cursorWindow.putNull(n, i)) : ((arrby = cursor.getBlob(i)) != null ? cursorWindow.putBlob(arrby, n, i) : cursorWindow.putNull(n, i))) : cursorWindow.putDouble(cursor.getDouble(i), n, i)) : cursorWindow.putLong(cursor.getLong(i), n, i)) : cursorWindow.putNull(n, i);
                        if (bl) continue;
                        cursorWindow.freeLastRow();
                        break block0;
                    }
                    ++n;
                    if (cursor.moveToNext()) continue;
                }
            }
            cursor.moveToPosition(n2);
            return;
        }
    }

    public static void cursorFloatToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, Float.valueOf(cursor.getFloat(n)));
        }
    }

    public static void cursorIntToContentValues(Cursor cursor, String string2, ContentValues contentValues) {
        DatabaseUtils.cursorIntToContentValues(cursor, string2, contentValues, string2);
    }

    public static void cursorIntToContentValues(Cursor cursor, String string2, ContentValues contentValues, String string3) {
        int n = cursor.getColumnIndex(string2);
        if (!cursor.isNull(n)) {
            contentValues.put(string3, cursor.getInt(n));
        } else {
            contentValues.put(string3, (Integer)null);
        }
    }

    public static void cursorIntToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, cursor.getInt(n));
        }
    }

    public static void cursorLongToContentValues(Cursor cursor, String string2, ContentValues contentValues) {
        DatabaseUtils.cursorLongToContentValues(cursor, string2, contentValues, string2);
    }

    public static void cursorLongToContentValues(Cursor cursor, String string2, ContentValues contentValues, String string3) {
        int n = cursor.getColumnIndex(string2);
        if (!cursor.isNull(n)) {
            contentValues.put(string3, cursor.getLong(n));
        } else {
            contentValues.put(string3, (Long)null);
        }
    }

    public static void cursorLongToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, cursor.getLong(n));
        }
    }

    @UnsupportedAppUsage
    public static int cursorPickFillWindowStartPosition(int n, int n2) {
        return Math.max(n - n2 / 3, 0);
    }

    public static void cursorRowToContentValues(Cursor cursor, ContentValues contentValues) {
        String[] arrstring = cursor.getColumnNames();
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (cursor.getType(i) == 4) {
                contentValues.put(arrstring[i], cursor.getBlob(i));
                continue;
            }
            contentValues.put(arrstring[i], cursor.getString(i));
        }
    }

    public static void cursorShortToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, cursor.getShort(n));
        }
    }

    public static void cursorStringToContentValues(Cursor cursor, String string2, ContentValues contentValues) {
        DatabaseUtils.cursorStringToContentValues(cursor, string2, contentValues, string2);
    }

    public static void cursorStringToContentValues(Cursor cursor, String string2, ContentValues contentValues, String string3) {
        contentValues.put(string3, cursor.getString(cursor.getColumnIndexOrThrow(string2)));
    }

    public static void cursorStringToContentValuesIfPresent(Cursor cursor, ContentValues contentValues, String string2) {
        int n = cursor.getColumnIndex(string2);
        if (n != -1 && !cursor.isNull(n)) {
            contentValues.put(string2, cursor.getString(n));
        }
    }

    public static void cursorStringToInsertHelper(Cursor cursor, String string2, InsertHelper insertHelper, int n) {
        insertHelper.bind(n, cursor.getString(cursor.getColumnIndexOrThrow(string2)));
    }

    public static void dumpCurrentRow(Cursor cursor) {
        DatabaseUtils.dumpCurrentRow(cursor, System.out);
    }

    public static void dumpCurrentRow(Cursor cursor, PrintStream printStream) {
        String[] arrstring = cursor.getColumnNames();
        CharSequence charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(cursor.getPosition());
        charSequence.append(" {");
        printStream.println(charSequence.toString());
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            try {
                charSequence = cursor.getString(i);
            }
            catch (SQLiteException sQLiteException) {
                charSequence = "<unprintable>";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("   ");
            stringBuilder.append(arrstring[i]);
            stringBuilder.append('=');
            stringBuilder.append((String)charSequence);
            printStream.println(stringBuilder.toString());
        }
        printStream.println("}");
    }

    public static void dumpCurrentRow(Cursor cursor, StringBuilder stringBuilder) {
        String[] arrstring = cursor.getColumnNames();
        CharSequence charSequence = new StringBuilder();
        charSequence.append("");
        charSequence.append(cursor.getPosition());
        charSequence.append(" {\n");
        stringBuilder.append(charSequence.toString());
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            try {
                charSequence = cursor.getString(i);
            }
            catch (SQLiteException sQLiteException) {
                charSequence = "<unprintable>";
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("   ");
            stringBuilder2.append(arrstring[i]);
            stringBuilder2.append('=');
            stringBuilder2.append((String)charSequence);
            stringBuilder2.append("\n");
            stringBuilder.append(stringBuilder2.toString());
        }
        stringBuilder.append("}\n");
    }

    public static String dumpCurrentRowToString(Cursor cursor) {
        StringBuilder stringBuilder = new StringBuilder();
        DatabaseUtils.dumpCurrentRow(cursor, stringBuilder);
        return stringBuilder.toString();
    }

    public static void dumpCursor(Cursor cursor) {
        DatabaseUtils.dumpCursor(cursor, System.out);
    }

    public static void dumpCursor(Cursor cursor, PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(">>>>> Dumping cursor ");
        stringBuilder.append(cursor);
        printStream.println(stringBuilder.toString());
        if (cursor != null) {
            int n = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                DatabaseUtils.dumpCurrentRow(cursor, printStream);
            }
            cursor.moveToPosition(n);
        }
        printStream.println("<<<<<");
    }

    public static void dumpCursor(Cursor cursor, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(">>>>> Dumping cursor ");
        stringBuilder2.append(cursor);
        stringBuilder2.append("\n");
        stringBuilder.append(stringBuilder2.toString());
        if (cursor != null) {
            int n = cursor.getPosition();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                DatabaseUtils.dumpCurrentRow(cursor, stringBuilder);
            }
            cursor.moveToPosition(n);
        }
        stringBuilder.append("<<<<<\n");
    }

    public static String dumpCursorToString(Cursor cursor) {
        StringBuilder stringBuilder = new StringBuilder();
        DatabaseUtils.dumpCursor(cursor, stringBuilder);
        return stringBuilder.toString();
    }

    private static char[] encodeHex(byte[] arrby) {
        int n = arrby.length;
        char[] arrc = new char[n << 1];
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = n2 + 1;
            char[] arrc2 = DIGITS;
            arrc[n2] = arrc2[(arrby[i] & 240) >>> 4];
            n2 = n3 + 1;
            arrc[n3] = arrc2[arrby[i] & 15];
        }
        return arrc;
    }

    public static int findRowIdColumnIndex(String[] arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals("_id")) continue;
            return i;
        }
        return -1;
    }

    public static String getCollationKey(String object) {
        object = DatabaseUtils.getCollationKeyInBytes((String)object);
        try {
            object = new String((byte[])object, 0, DatabaseUtils.getKeyLen(object), "ISO8859_1");
            return object;
        }
        catch (Exception exception) {
            return "";
        }
    }

    private static byte[] getCollationKeyInBytes(String string2) {
        if (mColl == null) {
            mColl = Collator.getInstance();
            mColl.setStrength(0);
        }
        return mColl.getCollationKey(string2).toByteArray();
    }

    public static String getHexCollationKey(String arrby) {
        arrby = DatabaseUtils.getCollationKeyInBytes((String)arrby);
        return new String(DatabaseUtils.encodeHex(arrby), 0, DatabaseUtils.getKeyLen(arrby) * 2);
    }

    private static int getKeyLen(byte[] arrby) {
        if (arrby[arrby.length - 1] != 0) {
            return arrby.length;
        }
        return arrby.length - 1;
    }

    public static int getSqlStatementType(String string2) {
        if ((string2 = string2.trim()).length() < 3) {
            return 99;
        }
        CharSequence charSequence = string2.substring(0, 3).toUpperCase(Locale.ROOT);
        if (((String)charSequence).equals("SEL")) {
            return 1;
        }
        if (!(((String)charSequence).equals("INS") || ((String)charSequence).equals("UPD") || ((String)charSequence).equals("REP") || ((String)charSequence).equals("DEL"))) {
            if (((String)charSequence).equals("ATT")) {
                return 3;
            }
            if (((String)charSequence).equals("COM")) {
                return 5;
            }
            if (((String)charSequence).equals("END")) {
                return 5;
            }
            if (((String)charSequence).equals("ROL")) {
                if (string2.toUpperCase(Locale.ROOT).contains(" TO ")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Statement '");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("' may not work on API levels 16-27, use ';");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append("' instead");
                    Log.w(TAG, ((StringBuilder)charSequence).toString());
                    return 99;
                }
                return 6;
            }
            if (((String)charSequence).equals("BEG")) {
                return 4;
            }
            if (((String)charSequence).equals("PRA")) {
                return 7;
            }
            if (!(((String)charSequence).equals("CRE") || ((String)charSequence).equals("DRO") || ((String)charSequence).equals("ALT"))) {
                if (!((String)charSequence).equals("ANA") && !((String)charSequence).equals("DET")) {
                    return 99;
                }
                return 9;
            }
            return 8;
        }
        return 2;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static int getTypeOfObject(Object object) {
        if (object == null) {
            return 0;
        }
        if (object instanceof byte[]) {
            return 4;
        }
        if (!(object instanceof Float) && !(object instanceof Double)) {
            if (!(object instanceof Long || object instanceof Integer || object instanceof Short || object instanceof Byte)) {
                return 3;
            }
            return 1;
        }
        return 2;
    }

    public static long longForQuery(SQLiteDatabase sQLiteClosable, String string2, String[] arrstring) {
        sQLiteClosable = ((SQLiteDatabase)sQLiteClosable).compileStatement(string2);
        try {
            long l = DatabaseUtils.longForQuery((SQLiteStatement)sQLiteClosable, arrstring);
            return l;
        }
        finally {
            sQLiteClosable.close();
        }
    }

    public static long longForQuery(SQLiteStatement sQLiteStatement, String[] arrstring) {
        sQLiteStatement.bindAllArgsAsStrings(arrstring);
        return sQLiteStatement.simpleQueryForLong();
    }

    public static boolean queryIsEmpty(SQLiteDatabase sQLiteDatabase, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("select exists(select 1 from ");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        boolean bl = DatabaseUtils.longForQuery(sQLiteDatabase, stringBuilder.toString(), null) == 0L;
        return bl;
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String string2) {
        return DatabaseUtils.queryNumEntries(sQLiteDatabase, string2, null, null);
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String string2, String string3) {
        return DatabaseUtils.queryNumEntries(sQLiteDatabase, string2, string3, null);
    }

    public static long queryNumEntries(SQLiteDatabase sQLiteDatabase, String string2, String string3, String[] arrstring) {
        StringBuilder stringBuilder;
        if (!TextUtils.isEmpty(string3)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(" where ");
            stringBuilder.append(string3);
            string3 = stringBuilder.toString();
        } else {
            string3 = "";
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("select count(*) from ");
        stringBuilder.append(string2);
        stringBuilder.append(string3);
        return DatabaseUtils.longForQuery(sQLiteDatabase, stringBuilder.toString(), arrstring);
    }

    public static final void readExceptionFromParcel(Parcel parcel) {
        int n = parcel.readExceptionCode();
        if (n == 0) {
            return;
        }
        DatabaseUtils.readExceptionFromParcel(parcel, parcel.readString(), n);
    }

    private static final void readExceptionFromParcel(Parcel parcel, String string2, int n) {
        switch (n) {
            default: {
                parcel.readException(n, string2);
                return;
            }
            case 11: {
                throw new OperationCanceledException(string2);
            }
            case 9: {
                throw new SQLiteException(string2);
            }
            case 8: {
                throw new SQLiteDiskIOException(string2);
            }
            case 7: {
                throw new SQLiteFullException(string2);
            }
            case 6: {
                throw new SQLiteDatabaseCorruptException(string2);
            }
            case 5: {
                throw new SQLiteConstraintException(string2);
            }
            case 4: {
                throw new SQLiteAbortException(string2);
            }
            case 3: {
                throw new UnsupportedOperationException(string2);
            }
            case 2: 
        }
        throw new IllegalArgumentException(string2);
    }

    public static void readExceptionWithFileNotFoundExceptionFromParcel(Parcel parcel) throws FileNotFoundException {
        int n = parcel.readExceptionCode();
        if (n == 0) {
            return;
        }
        String string2 = parcel.readString();
        if (n != 1) {
            DatabaseUtils.readExceptionFromParcel(parcel, string2, n);
            return;
        }
        throw new FileNotFoundException(string2);
    }

    public static void readExceptionWithOperationApplicationExceptionFromParcel(Parcel parcel) throws OperationApplicationException {
        int n = parcel.readExceptionCode();
        if (n == 0) {
            return;
        }
        String string2 = parcel.readString();
        if (n != 10) {
            DatabaseUtils.readExceptionFromParcel(parcel, string2, n);
            return;
        }
        throw new OperationApplicationException(string2);
    }

    public static String sqlEscapeString(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        DatabaseUtils.appendEscapedSQLString(stringBuilder, string2);
        return stringBuilder.toString();
    }

    public static String stringForQuery(SQLiteDatabase sQLiteClosable, String string2, String[] arrstring) {
        sQLiteClosable = ((SQLiteDatabase)sQLiteClosable).compileStatement(string2);
        try {
            string2 = DatabaseUtils.stringForQuery((SQLiteStatement)sQLiteClosable, arrstring);
            return string2;
        }
        finally {
            sQLiteClosable.close();
        }
    }

    public static String stringForQuery(SQLiteStatement sQLiteStatement, String[] arrstring) {
        sQLiteStatement.bindAllArgsAsStrings(arrstring);
        return sQLiteStatement.simpleQueryForString();
    }

    public static final void writeExceptionToParcel(Parcel parcel, Exception exception) {
        block15 : {
            boolean bl;
            int n;
            block5 : {
                block14 : {
                    block13 : {
                        block12 : {
                            block11 : {
                                block10 : {
                                    block9 : {
                                        block8 : {
                                            block7 : {
                                                block6 : {
                                                    block4 : {
                                                        bl = true;
                                                        if (!(exception instanceof FileNotFoundException)) break block4;
                                                        n = 1;
                                                        bl = false;
                                                        break block5;
                                                    }
                                                    if (!(exception instanceof IllegalArgumentException)) break block6;
                                                    n = 2;
                                                    break block5;
                                                }
                                                if (!(exception instanceof UnsupportedOperationException)) break block7;
                                                n = 3;
                                                break block5;
                                            }
                                            if (!(exception instanceof SQLiteAbortException)) break block8;
                                            n = 4;
                                            break block5;
                                        }
                                        if (!(exception instanceof SQLiteConstraintException)) break block9;
                                        n = 5;
                                        break block5;
                                    }
                                    if (!(exception instanceof SQLiteDatabaseCorruptException)) break block10;
                                    n = 6;
                                    break block5;
                                }
                                if (!(exception instanceof SQLiteFullException)) break block11;
                                n = 7;
                                break block5;
                            }
                            if (!(exception instanceof SQLiteDiskIOException)) break block12;
                            n = 8;
                            break block5;
                        }
                        if (!(exception instanceof SQLiteException)) break block13;
                        n = 9;
                        break block5;
                    }
                    if (!(exception instanceof OperationApplicationException)) break block14;
                    n = 10;
                    break block5;
                }
                if (!(exception instanceof OperationCanceledException)) break block15;
                n = 11;
                bl = false;
            }
            parcel.writeInt(n);
            parcel.writeString(exception.getMessage());
            if (bl) {
                Log.e(TAG, "Writing exception to parcel", exception);
            }
            return;
        }
        parcel.writeException(exception);
        Log.e(TAG, "Writing exception to parcel", exception);
    }

    @Deprecated
    public static class InsertHelper {
        public static final int TABLE_INFO_PRAGMA_COLUMNNAME_INDEX = 1;
        public static final int TABLE_INFO_PRAGMA_DEFAULT_INDEX = 4;
        private HashMap<String, Integer> mColumns;
        private final SQLiteDatabase mDb;
        private String mInsertSQL = null;
        private SQLiteStatement mInsertStatement = null;
        private SQLiteStatement mPreparedStatement = null;
        private SQLiteStatement mReplaceStatement = null;
        private final String mTableName;

        public InsertHelper(SQLiteDatabase sQLiteDatabase, String string2) {
            this.mDb = sQLiteDatabase;
            this.mTableName = string2;
        }

        private void buildSQL() throws SQLException {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("INSERT INTO ");
            stringBuilder.append(this.mTableName);
            stringBuilder.append(" (");
            StringBuilder stringBuilder2 = new StringBuilder(128);
            stringBuilder2.append("VALUES (");
            int n = 1;
            Object object = null;
            Closeable closeable = object;
            Closeable closeable2 = this.mDb;
            closeable = object;
            closeable = object;
            CharSequence charSequence = new StringBuilder();
            closeable = object;
            charSequence.append("PRAGMA table_info(");
            closeable = object;
            charSequence.append(this.mTableName);
            closeable = object;
            charSequence.append(")");
            closeable = object;
            closeable2 = ((SQLiteDatabase)closeable2).rawQuery(charSequence.toString(), null);
            closeable = closeable2;
            closeable = closeable2;
            object = new HashMap(closeable2.getCount());
            closeable = closeable2;
            try {
                this.mColumns = object;
            }
            catch (Throwable throwable) {
                if (closeable != null) {
                    closeable.close();
                }
                throw throwable;
            }
            do {
                block30 : {
                    block29 : {
                        closeable = closeable2;
                        if (!closeable2.moveToNext()) break;
                        closeable = closeable2;
                        charSequence = closeable2.getString(1);
                        closeable = closeable2;
                        object = closeable2.getString(4);
                        closeable = closeable2;
                        this.mColumns.put((String)charSequence, n);
                        closeable = closeable2;
                        stringBuilder.append("'");
                        closeable = closeable2;
                        stringBuilder.append((String)charSequence);
                        closeable = closeable2;
                        stringBuilder.append("'");
                        if (object != null) break block29;
                        closeable = closeable2;
                        stringBuilder2.append("?");
                        break block30;
                    }
                    closeable = closeable2;
                    stringBuilder2.append("COALESCE(?, ");
                    closeable = closeable2;
                    stringBuilder2.append((String)object);
                    closeable = closeable2;
                    stringBuilder2.append(")");
                }
                closeable = closeable2;
                int n2 = closeable2.getCount();
                charSequence = ", ";
                object = n == n2 ? ") " : ", ";
                closeable = closeable2;
                stringBuilder.append((String)object);
                object = charSequence;
                closeable = closeable2;
                if (n == closeable2.getCount()) {
                    object = ");";
                }
                closeable = closeable2;
                stringBuilder2.append((String)object);
                ++n;
            } while (true);
            closeable2.close();
            stringBuilder.append(stringBuilder2);
            this.mInsertSQL = stringBuilder.toString();
            return;
        }

        private SQLiteStatement getStatement(boolean bl) throws SQLException {
            if (bl) {
                if (this.mReplaceStatement == null) {
                    if (this.mInsertSQL == null) {
                        this.buildSQL();
                    }
                    CharSequence charSequence = new StringBuilder();
                    charSequence.append("INSERT OR REPLACE");
                    charSequence.append(this.mInsertSQL.substring(6));
                    charSequence = charSequence.toString();
                    this.mReplaceStatement = this.mDb.compileStatement((String)charSequence);
                }
                return this.mReplaceStatement;
            }
            if (this.mInsertStatement == null) {
                if (this.mInsertSQL == null) {
                    this.buildSQL();
                }
                this.mInsertStatement = this.mDb.compileStatement(this.mInsertSQL);
            }
            return this.mInsertStatement;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private long insertInternal(ContentValues contentValues, boolean bl) {
            Throwable throwable2222;
            this.mDb.beginTransactionNonExclusive();
            SQLiteStatement sQLiteStatement = this.getStatement(bl);
            sQLiteStatement.clearBindings();
            for (Map.Entry<String, Object> entry : contentValues.valueSet()) {
                DatabaseUtils.bindObjectToProgram(sQLiteStatement, this.getColumnIndex(entry.getKey()), entry.getValue());
            }
            long l = sQLiteStatement.executeInsert();
            this.mDb.setTransactionSuccessful();
            this.mDb.endTransaction();
            return l;
            {
                catch (Throwable throwable2222) {
                }
                catch (SQLException sQLException) {}
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error inserting ");
                    stringBuilder.append(contentValues);
                    stringBuilder.append(" into table  ");
                    stringBuilder.append(this.mTableName);
                    Log.e(DatabaseUtils.TAG, stringBuilder.toString(), sQLException);
                    this.mDb.endTransaction();
                    return -1L;
                }
            }
            this.mDb.endTransaction();
            throw throwable2222;
        }

        public void bind(int n, double d) {
            this.mPreparedStatement.bindDouble(n, d);
        }

        public void bind(int n, float f) {
            this.mPreparedStatement.bindDouble(n, f);
        }

        public void bind(int n, int n2) {
            this.mPreparedStatement.bindLong(n, n2);
        }

        public void bind(int n, long l) {
            this.mPreparedStatement.bindLong(n, l);
        }

        public void bind(int n, String string2) {
            if (string2 == null) {
                this.mPreparedStatement.bindNull(n);
            } else {
                this.mPreparedStatement.bindString(n, string2);
            }
        }

        public void bind(int n, boolean bl) {
            SQLiteStatement sQLiteStatement = this.mPreparedStatement;
            long l = bl ? 1L : 0L;
            sQLiteStatement.bindLong(n, l);
        }

        public void bind(int n, byte[] arrby) {
            if (arrby == null) {
                this.mPreparedStatement.bindNull(n);
            } else {
                this.mPreparedStatement.bindBlob(n, arrby);
            }
        }

        public void bindNull(int n) {
            this.mPreparedStatement.bindNull(n);
        }

        public void close() {
            SQLiteStatement sQLiteStatement = this.mInsertStatement;
            if (sQLiteStatement != null) {
                sQLiteStatement.close();
                this.mInsertStatement = null;
            }
            if ((sQLiteStatement = this.mReplaceStatement) != null) {
                sQLiteStatement.close();
                this.mReplaceStatement = null;
            }
            this.mInsertSQL = null;
            this.mColumns = null;
        }

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public long execute() {
            Throwable throwable2222;
            Object object = this.mPreparedStatement;
            if (object == null) throw new IllegalStateException("you must prepare this inserter before calling execute");
            long l = ((SQLiteStatement)object).executeInsert();
            this.mPreparedStatement = null;
            return l;
            {
                catch (Throwable throwable2222) {
                }
                catch (SQLException sQLException) {}
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error executing InsertHelper with table ");
                    ((StringBuilder)object).append(this.mTableName);
                    Log.e(DatabaseUtils.TAG, ((StringBuilder)object).toString(), sQLException);
                    this.mPreparedStatement = null;
                    return -1L;
                }
            }
            this.mPreparedStatement = null;
            throw throwable2222;
        }

        public int getColumnIndex(String string2) {
            this.getStatement(false);
            Serializable serializable = this.mColumns.get(string2);
            if (serializable != null) {
                return (Integer)serializable;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("column '");
            ((StringBuilder)serializable).append(string2);
            ((StringBuilder)serializable).append("' is invalid");
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }

        public long insert(ContentValues contentValues) {
            return this.insertInternal(contentValues, false);
        }

        public void prepareForInsert() {
            this.mPreparedStatement = this.getStatement(false);
            this.mPreparedStatement.clearBindings();
        }

        public void prepareForReplace() {
            this.mPreparedStatement = this.getStatement(true);
            this.mPreparedStatement.clearBindings();
        }

        public long replace(ContentValues contentValues) {
            return this.insertInternal(contentValues, true);
        }
    }

}

