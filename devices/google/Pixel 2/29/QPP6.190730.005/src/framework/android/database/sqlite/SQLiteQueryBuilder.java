/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.database.sqlite;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.CancellationSignal;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.util.EmptyArray;

public class SQLiteQueryBuilder {
    private static final String TAG = "SQLiteQueryBuilder";
    private static final Pattern sAggregationPattern;
    private static final Pattern sLimitPattern;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mDistinct = false;
    private SQLiteDatabase.CursorFactory mFactory = null;
    private boolean mProjectionAggregationAllowed = false;
    private List<Pattern> mProjectionGreylist = null;
    private Map<String, String> mProjectionMap = null;
    private boolean mStrict;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mTables = "";
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private StringBuilder mWhereClause = null;

    static {
        sLimitPattern = Pattern.compile("\\s*\\d+\\s*(,\\s*\\d+\\s*)?");
        sAggregationPattern = Pattern.compile("(?i)(AVG|COUNT|MAX|MIN|SUM|TOTAL|GROUP_CONCAT)\\((.+)\\)");
    }

    private static void appendClause(StringBuilder stringBuilder, String string2, String string3) {
        if (!TextUtils.isEmpty(string3)) {
            stringBuilder.append(string2);
            stringBuilder.append(string3);
        }
    }

    public static void appendColumns(StringBuilder stringBuilder, String[] arrstring) {
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            String string2 = arrstring[i];
            if (string2 == null) continue;
            if (i > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(string2);
        }
        stringBuilder.append(' ');
    }

    public static String buildQueryString(boolean bl, String charSequence, String[] arrstring, String string2, String string3, String string4, String string5, String string6) {
        if (TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string4)) {
            throw new IllegalArgumentException("HAVING clauses are only permitted when using a groupBy clause");
        }
        if (!TextUtils.isEmpty(string6) && !sLimitPattern.matcher(string6).matches()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("invalid LIMIT clauses:");
            ((StringBuilder)charSequence).append(string6);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder(120);
        stringBuilder.append("SELECT ");
        if (bl) {
            stringBuilder.append("DISTINCT ");
        }
        if (arrstring != null && arrstring.length != 0) {
            SQLiteQueryBuilder.appendColumns(stringBuilder, arrstring);
        } else {
            stringBuilder.append("* ");
        }
        stringBuilder.append("FROM ");
        stringBuilder.append((String)charSequence);
        SQLiteQueryBuilder.appendClause(stringBuilder, " WHERE ", string2);
        SQLiteQueryBuilder.appendClause(stringBuilder, " GROUP BY ", string3);
        SQLiteQueryBuilder.appendClause(stringBuilder, " HAVING ", string4);
        SQLiteQueryBuilder.appendClause(stringBuilder, " ORDER BY ", string5);
        SQLiteQueryBuilder.appendClause(stringBuilder, " LIMIT ", string6);
        return stringBuilder.toString();
    }

    private static String maybeWithOperator(String string2, String string3) {
        if (string2 != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("(");
            stringBuilder.append(string3);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
        return string3;
    }

    private String wrap(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(string2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void appendWhere(CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(charSequence.length() + 16);
        }
        this.mWhereClause.append(charSequence);
    }

    public void appendWhereEscapeString(String string2) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(string2.length() + 16);
        }
        DatabaseUtils.appendEscapedSQLString(this.mWhereClause, string2);
    }

    public void appendWhereStandalone(CharSequence charSequence) {
        if (this.mWhereClause == null) {
            this.mWhereClause = new StringBuilder(charSequence.length() + 16);
        }
        if (this.mWhereClause.length() > 0) {
            this.mWhereClause.append(" AND ");
        }
        StringBuilder stringBuilder = this.mWhereClause;
        stringBuilder.append('(');
        stringBuilder.append(charSequence);
        stringBuilder.append(')');
    }

    public String buildDelete(String string2) {
        StringBuilder stringBuilder = new StringBuilder(120);
        stringBuilder.append("DELETE FROM ");
        stringBuilder.append(this.mTables);
        SQLiteQueryBuilder.appendClause(stringBuilder, " WHERE ", this.computeWhere(string2));
        return stringBuilder.toString();
    }

    public String buildQuery(String[] arrstring, String string2, String string3, String string4, String string5, String string6) {
        arrstring = this.computeProjection(arrstring);
        string2 = this.computeWhere(string2);
        return SQLiteQueryBuilder.buildQueryString(this.mDistinct, this.mTables, arrstring, string2, string3, string4, string5, string6);
    }

    @Deprecated
    public String buildQuery(String[] arrstring, String string2, String[] arrstring2, String string3, String string4, String string5, String string6) {
        return this.buildQuery(arrstring, string2, string3, string4, string5, string6);
    }

    public String buildUnionQuery(String[] arrstring, String string2, String string3) {
        StringBuilder stringBuilder = new StringBuilder(128);
        int n = arrstring.length;
        String string4 = this.mDistinct ? " UNION " : " UNION ALL ";
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                stringBuilder.append(string4);
            }
            stringBuilder.append(arrstring[i]);
        }
        SQLiteQueryBuilder.appendClause(stringBuilder, " ORDER BY ", string2);
        SQLiteQueryBuilder.appendClause(stringBuilder, " LIMIT ", string3);
        return stringBuilder.toString();
    }

    public String buildUnionSubQuery(String string2, String[] arrstring, Set<String> set, int n, String string3, String string4, String string5, String string6) {
        int n2 = arrstring.length;
        String[] arrstring2 = new String[n2];
        for (int i = 0; i < n2; ++i) {
            CharSequence charSequence = arrstring[i];
            if (((String)charSequence).equals(string2)) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("'");
                ((StringBuilder)charSequence).append(string3);
                ((StringBuilder)charSequence).append("' AS ");
                ((StringBuilder)charSequence).append(string2);
                arrstring2[i] = ((StringBuilder)charSequence).toString();
                continue;
            }
            if (i > n && !set.contains(charSequence)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("NULL AS ");
                stringBuilder.append((String)charSequence);
                arrstring2[i] = stringBuilder.toString();
                continue;
            }
            arrstring2[i] = charSequence;
        }
        return this.buildQuery(arrstring2, string4, string5, string6, null, null);
    }

    @Deprecated
    public String buildUnionSubQuery(String string2, String[] arrstring, Set<String> set, int n, String string3, String string4, String[] arrstring2, String string5, String string6) {
        return this.buildUnionSubQuery(string2, arrstring, set, n, string3, string4, string5, string6);
    }

    public String buildUpdate(ContentValues object, String string2) {
        if (object != null && !((ContentValues)object).isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder(120);
            stringBuilder.append("UPDATE ");
            stringBuilder.append(this.mTables);
            stringBuilder.append(" SET ");
            object = ((ContentValues)object).getValues();
            for (int i = 0; i < ((ArrayMap)object).size(); ++i) {
                if (i > 0) {
                    stringBuilder.append(',');
                }
                stringBuilder.append((String)((ArrayMap)object).keyAt(i));
                stringBuilder.append("=?");
            }
            SQLiteQueryBuilder.appendClause(stringBuilder, " WHERE ", this.computeWhere(string2));
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("Empty values");
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public String[] computeProjection(String[] object) {
        if (object != null && ((String[])object).length > 0) {
            if (this.mProjectionMap != null) {
                String[] arrstring = new String[((Object)object).length];
                int n = ((Object)object).length;
                for (int i = 0; i < n; ++i) {
                    String string2 = null;
                    Object object2 = object[i];
                    String string3 = this.mProjectionMap.get(object2);
                    String string4 = string2;
                    Object object3 = object2;
                    List<Pattern> list = string3;
                    if (this.mProjectionAggregationAllowed) {
                        Matcher matcher = sAggregationPattern.matcher((CharSequence)object2);
                        string4 = string2;
                        object3 = object2;
                        list = string3;
                        if (matcher.matches()) {
                            string4 = matcher.group(1);
                            object3 = matcher.group(2);
                            list = this.mProjectionMap.get(object3);
                        }
                    }
                    if (list != null) {
                        arrstring[i] = SQLiteQueryBuilder.maybeWithOperator(string4, (String)((Object)list));
                        continue;
                    }
                    if (!this.mStrict && (((String)object3).contains(" AS ") || ((String)object3).contains(" as "))) {
                        arrstring[i] = SQLiteQueryBuilder.maybeWithOperator(string4, (String)object3);
                        continue;
                    }
                    list = this.mProjectionGreylist;
                    if (list != null) {
                        boolean bl;
                        block12 : {
                            boolean bl2 = false;
                            list = list.iterator();
                            do {
                                bl = bl2;
                                if (!list.hasNext()) break block12;
                            } while (!((Pattern)list.next()).matcher((CharSequence)object3).matches());
                            bl = true;
                        }
                        if (bl) {
                            list = new StringBuilder();
                            ((StringBuilder)((Object)list)).append("Allowing abusive custom column: ");
                            ((StringBuilder)((Object)list)).append((String)object3);
                            Log.w(TAG, ((StringBuilder)((Object)list)).toString());
                            arrstring[i] = SQLiteQueryBuilder.maybeWithOperator(string4, (String)object3);
                            continue;
                        }
                    }
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Invalid column ");
                    ((StringBuilder)object3).append((String)object[i]);
                    throw new IllegalArgumentException(((StringBuilder)object3).toString());
                }
                return arrstring;
            }
            return object;
        }
        object = this.mProjectionMap;
        if (object != null) {
            Object object4 = object.entrySet();
            object = new String[object4.size()];
            object4 = object4.iterator();
            int n = 0;
            while (object4.hasNext()) {
                Map.Entry entry = (Map.Entry)object4.next();
                if (((String)entry.getKey()).equals("_count")) continue;
                object[n] = (String)entry.getValue();
                ++n;
            }
            return object;
        }
        return null;
    }

    public String computeWhere(String string2) {
        boolean bl = TextUtils.isEmpty(this.mWhereClause) ^ true;
        boolean bl2 = TextUtils.isEmpty(string2) ^ true;
        if (!bl && !bl2) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (bl) {
            stringBuilder.append('(');
            stringBuilder.append(this.mWhereClause);
            stringBuilder.append(')');
        }
        if (bl && bl2) {
            stringBuilder.append(" AND ");
        }
        if (bl2) {
            stringBuilder.append('(');
            stringBuilder.append(string2);
            stringBuilder.append(')');
        }
        return stringBuilder.toString();
    }

    public int delete(SQLiteDatabase sQLiteDatabase, String string2, String[] arrstring) {
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(sQLiteDatabase, "No database defined");
        CharSequence charSequence = this.buildDelete(string2);
        if (this.mStrict) {
            sQLiteDatabase.validateSql((String)charSequence, null);
            string2 = this.buildDelete(this.wrap(string2));
        } else {
            string2 = charSequence;
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" with args ");
                ((StringBuilder)charSequence).append(Arrays.toString(arrstring));
                Log.d(TAG, ((StringBuilder)charSequence).toString());
            } else {
                Log.d(TAG, string2);
            }
        }
        return sQLiteDatabase.executeSql(string2, arrstring);
    }

    public SQLiteDatabase.CursorFactory getCursorFactory() {
        return this.mFactory;
    }

    public List<Pattern> getProjectionGreylist() {
        return this.mProjectionGreylist;
    }

    public Map<String, String> getProjectionMap() {
        return this.mProjectionMap;
    }

    public String getTables() {
        return this.mTables;
    }

    public boolean isDistinct() {
        return this.mDistinct;
    }

    public boolean isProjectionAggregationAllowed() {
        return this.mProjectionAggregationAllowed;
    }

    public boolean isStrict() {
        return this.mStrict;
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] arrstring, String string2, String[] arrstring2, String string3, String string4, String string5) {
        return this.query(sQLiteDatabase, arrstring, string2, arrstring2, string3, string4, string5, null, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] arrstring, String string2, String[] arrstring2, String string3, String string4, String string5, String string6) {
        return this.query(sQLiteDatabase, arrstring, string2, arrstring2, string3, string4, string5, string6, null);
    }

    public Cursor query(SQLiteDatabase sQLiteDatabase, String[] object, String charSequence, String[] arrstring, String string2, String string3, String string4, String string5, CancellationSignal cancellationSignal) {
        if (this.mTables == null) {
            return null;
        }
        String string6 = this.buildQuery((String[])object, (String)charSequence, string2, string3, string4, string5);
        if (this.mStrict && charSequence != null && ((String)charSequence).length() > 0) {
            sQLiteDatabase.validateSql(string6, cancellationSignal);
            object = this.buildQuery((String[])object, this.wrap((String)charSequence), string2, string3, string4, string5);
        } else {
            object = string6;
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object);
                ((StringBuilder)charSequence).append(" with args ");
                ((StringBuilder)charSequence).append(Arrays.toString(arrstring));
                Log.d(TAG, ((StringBuilder)charSequence).toString());
            } else {
                Log.d(TAG, (String)object);
            }
        }
        return sQLiteDatabase.rawQueryWithFactory(this.mFactory, (String)object, arrstring, SQLiteDatabase.findEditTable(this.mTables), cancellationSignal);
    }

    public void setCursorFactory(SQLiteDatabase.CursorFactory cursorFactory) {
        this.mFactory = cursorFactory;
    }

    public void setDistinct(boolean bl) {
        this.mDistinct = bl;
    }

    public void setProjectionAggregationAllowed(boolean bl) {
        this.mProjectionAggregationAllowed = bl;
    }

    public void setProjectionGreylist(List<Pattern> list) {
        this.mProjectionGreylist = list;
    }

    public void setProjectionMap(Map<String, String> map) {
        this.mProjectionMap = map;
    }

    public void setStrict(boolean bl) {
        this.mStrict = bl;
    }

    public void setTables(String string2) {
        this.mTables = string2;
    }

    public int update(SQLiteDatabase sQLiteDatabase, ContentValues arrobject, String string2, String[] object) {
        Objects.requireNonNull(this.mTables, "No tables defined");
        Objects.requireNonNull(sQLiteDatabase, "No database defined");
        Objects.requireNonNull(arrobject, "No values defined");
        String[] arrstring = this.buildUpdate((ContentValues)arrobject, string2);
        if (this.mStrict) {
            sQLiteDatabase.validateSql((String)arrstring, null);
            string2 = this.buildUpdate((ContentValues)arrobject, this.wrap(string2));
        } else {
            string2 = arrstring;
        }
        arrstring = object;
        if (object == null) {
            arrstring = EmptyArray.STRING;
        }
        object = arrobject.getValues();
        int n = ((ArrayMap)object).size();
        arrobject = new Object[arrstring.length + n];
        for (int i = 0; i < arrobject.length; ++i) {
            arrobject[i] = i < n ? ((ArrayMap)object).valueAt(i) : arrstring[i - n];
        }
        if (Log.isLoggable(TAG, 3)) {
            if (Build.IS_DEBUGGABLE) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" with args ");
                ((StringBuilder)object).append(Arrays.toString(arrobject));
                Log.d(TAG, ((StringBuilder)object).toString());
            } else {
                Log.d(TAG, string2);
            }
        }
        return sQLiteDatabase.executeSql(string2, arrobject);
    }
}

