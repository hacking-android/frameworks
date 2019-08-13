/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;

public class SearchRecentSuggestionsProvider
extends ContentProvider {
    public static final int DATABASE_MODE_2LINES = 2;
    public static final int DATABASE_MODE_QUERIES = 1;
    private static final int DATABASE_VERSION = 512;
    private static final String NULL_COLUMN = "query";
    private static final String ORDER_BY = "date DESC";
    private static final String TAG = "SuggestionsProvider";
    private static final int URI_MATCH_SUGGEST = 1;
    private static final String sDatabaseName = "suggestions.db";
    private static final String sSuggestions = "suggestions";
    private String mAuthority;
    private int mMode;
    private SQLiteOpenHelper mOpenHelper;
    private String mSuggestSuggestionClause;
    @UnsupportedAppUsage
    private String[] mSuggestionProjection;
    private Uri mSuggestionsUri;
    private boolean mTwoLineDisplay;
    private UriMatcher mUriMatcher;

    @Override
    public int delete(Uri uri, String string2, String[] arrstring) {
        SQLiteDatabase sQLiteDatabase = this.mOpenHelper.getWritableDatabase();
        if (uri.getPathSegments().size() == 1) {
            if (uri.getPathSegments().get(0).equals(sSuggestions)) {
                int n = sQLiteDatabase.delete(sSuggestions, string2, arrstring);
                this.getContext().getContentResolver().notifyChange(uri, null);
                return n;
            }
            throw new IllegalArgumentException("Unknown Uri");
        }
        throw new IllegalArgumentException("Unknown Uri");
    }

    @Override
    public String getType(Uri uri) {
        if (this.mUriMatcher.match(uri) == 1) {
            return "vnd.android.cursor.dir/vnd.android.search.suggest";
        }
        int n = uri.getPathSegments().size();
        if (n >= 1 && uri.getPathSegments().get(0).equals(sSuggestions)) {
            if (n == 1) {
                return "vnd.android.cursor.dir/suggestion";
            }
            if (n == 2) {
                return "vnd.android.cursor.item/suggestion";
            }
        }
        throw new IllegalArgumentException("Unknown Uri");
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase sQLiteDatabase = this.mOpenHelper.getWritableDatabase();
        int n = uri.getPathSegments().size();
        if (n >= 1) {
            long l = -1L;
            String string2 = uri.getPathSegments().get(0);
            Object var8_7 = null;
            long l2 = l;
            uri = var8_7;
            if (string2.equals(sSuggestions)) {
                l2 = l;
                uri = var8_7;
                if (n == 1) {
                    l2 = l = sQLiteDatabase.insert(sSuggestions, NULL_COLUMN, contentValues);
                    uri = var8_7;
                    if (l > 0L) {
                        uri = Uri.withAppendedPath(this.mSuggestionsUri, String.valueOf(l));
                        l2 = l;
                    }
                }
            }
            if (l2 >= 0L) {
                this.getContext().getContentResolver().notifyChange(uri, null);
                return uri;
            }
            throw new IllegalArgumentException("Unknown Uri");
        }
        throw new IllegalArgumentException("Unknown Uri");
    }

    @Override
    public boolean onCreate() {
        int n;
        if (this.mAuthority != null && (n = this.mMode) != 0) {
            this.mOpenHelper = new DatabaseHelper(this.getContext(), n + 512);
            return true;
        }
        throw new IllegalArgumentException("Provider not configured");
    }

    @Override
    public Cursor query(Uri uri, String[] object, String string2, String[] arrstring, String string3) {
        SQLiteDatabase sQLiteDatabase = this.mOpenHelper.getReadableDatabase();
        if (this.mUriMatcher.match(uri) == 1) {
            if (TextUtils.isEmpty(arrstring[0])) {
                string2 = null;
                object = null;
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("%");
                ((StringBuilder)object).append(arrstring[0]);
                ((StringBuilder)object).append("%");
                string2 = ((StringBuilder)object).toString();
                object = this.mTwoLineDisplay ? new String[]{string2, string2} : new String[]{string2};
                string2 = this.mSuggestSuggestionClause;
            }
            object = sQLiteDatabase.query(sSuggestions, this.mSuggestionProjection, string2, (String[])object, null, null, ORDER_BY, null);
            object.setNotificationUri(this.getContext().getContentResolver(), uri);
            return object;
        }
        int n = uri.getPathSegments().size();
        if (n != 1 && n != 2) {
            throw new IllegalArgumentException("Unknown Uri");
        }
        String string4 = uri.getPathSegments().get(0);
        if (string4.equals(sSuggestions)) {
            String[] arrstring2;
            if (object != null && ((Object)object).length > 0) {
                arrstring2 = new String[((Object)object).length + 1];
                System.arraycopy(object, 0, arrstring2, 0, ((Object)object).length);
                arrstring2[((Object)object).length] = "_id AS _id";
                object = arrstring2;
            } else {
                object = null;
            }
            arrstring2 = new StringBuilder(256);
            if (n == 2) {
                arrstring2.append("(_id = ");
                arrstring2.append(uri.getPathSegments().get(1));
                arrstring2.append(")");
            }
            if (string2 != null && string2.length() > 0) {
                if (arrstring2.length() > 0) {
                    arrstring2.append(" AND ");
                }
                arrstring2.append('(');
                arrstring2.append(string2);
                arrstring2.append(')');
            }
            object = sQLiteDatabase.query(string4, (String[])object, arrstring2.toString(), arrstring, null, null, string3, null);
            object.setNotificationUri(this.getContext().getContentResolver(), uri);
            return object;
        }
        throw new IllegalArgumentException("Unknown Uri");
    }

    protected void setupSuggestions(String charSequence, int n) {
        if (!TextUtils.isEmpty(charSequence) && (n & 1) != 0) {
            boolean bl = (n & 2) != 0;
            this.mTwoLineDisplay = bl;
            this.mAuthority = new String((String)charSequence);
            this.mMode = n;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("content://");
            ((StringBuilder)charSequence).append(this.mAuthority);
            ((StringBuilder)charSequence).append("/suggestions");
            this.mSuggestionsUri = Uri.parse(((StringBuilder)charSequence).toString());
            this.mUriMatcher = new UriMatcher(-1);
            this.mUriMatcher.addURI(this.mAuthority, "search_suggest_query", 1);
            if (this.mTwoLineDisplay) {
                this.mSuggestSuggestionClause = "display1 LIKE ? OR display2 LIKE ?";
                this.mSuggestionProjection = new String[]{"0 AS suggest_format", "'android.resource://system/17301578' AS suggest_icon_1", "display1 AS suggest_text_1", "display2 AS suggest_text_2", "query AS suggest_intent_query", "_id"};
            } else {
                this.mSuggestSuggestionClause = "display1 LIKE ?";
                this.mSuggestionProjection = new String[]{"0 AS suggest_format", "'android.resource://system/17301578' AS suggest_icon_1", "display1 AS suggest_text_1", "query AS suggest_intent_query", "_id"};
            }
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) {
        throw new UnsupportedOperationException("Not implemented");
    }

    private static class DatabaseHelper
    extends SQLiteOpenHelper {
        private int mNewVersion;

        public DatabaseHelper(Context context, int n) {
            super(context, SearchRecentSuggestionsProvider.sDatabaseName, null, n);
            this.mNewVersion = n;
        }

        @Override
        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CREATE TABLE suggestions (_id INTEGER PRIMARY KEY,display1 TEXT UNIQUE ON CONFLICT REPLACE");
            if ((this.mNewVersion & 2) != 0) {
                stringBuilder.append(",display2 TEXT");
            }
            stringBuilder.append(",query TEXT,date LONG);");
            sQLiteDatabase.execSQL(stringBuilder.toString());
        }

        @Override
        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Upgrading database from version ");
            stringBuilder.append(n);
            stringBuilder.append(" to ");
            stringBuilder.append(n2);
            stringBuilder.append(", which will destroy all old data");
            Log.w(SearchRecentSuggestionsProvider.TAG, stringBuilder.toString());
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS suggestions");
            this.onCreate(sQLiteDatabase);
        }
    }

}

