/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;
import java.util.concurrent.Semaphore;

public class SearchRecentSuggestions {
    private static final String LOG_TAG = "SearchSuggestions";
    private static final int MAX_HISTORY_COUNT = 250;
    public static final String[] QUERIES_PROJECTION_1LINE = new String[]{"_id", "date", "query", "display1"};
    public static final String[] QUERIES_PROJECTION_2LINE = new String[]{"_id", "date", "query", "display1", "display2"};
    public static final int QUERIES_PROJECTION_DATE_INDEX = 1;
    public static final int QUERIES_PROJECTION_DISPLAY1_INDEX = 3;
    public static final int QUERIES_PROJECTION_DISPLAY2_INDEX = 4;
    public static final int QUERIES_PROJECTION_QUERY_INDEX = 2;
    private static final Semaphore sWritesInProgress = new Semaphore(0);
    private final String mAuthority;
    private final Context mContext;
    private final Uri mSuggestionsUri;
    private final boolean mTwoLineDisplay;

    public SearchRecentSuggestions(Context object, String string2, int n) {
        if (!TextUtils.isEmpty(string2) && (n & 1) != 0) {
            boolean bl = (n & 2) != 0;
            this.mTwoLineDisplay = bl;
            this.mContext = object;
            this.mAuthority = new String(string2);
            object = new StringBuilder();
            ((StringBuilder)object).append("content://");
            ((StringBuilder)object).append(this.mAuthority);
            ((StringBuilder)object).append("/suggestions");
            this.mSuggestionsUri = Uri.parse(((StringBuilder)object).toString());
            return;
        }
        throw new IllegalArgumentException();
    }

    private void saveRecentQueryBlocking(String string2, String string3) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long l = System.currentTimeMillis();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("display1", string2);
            if (this.mTwoLineDisplay) {
                contentValues.put("display2", string3);
            }
            contentValues.put("query", string2);
            contentValues.put("date", l);
            contentResolver.insert(this.mSuggestionsUri, contentValues);
        }
        catch (RuntimeException runtimeException) {
            Log.e(LOG_TAG, "saveRecentQuery", runtimeException);
        }
        this.truncateHistory(contentResolver, 250);
    }

    public void clearHistory() {
        this.truncateHistory(this.mContext.getContentResolver(), 0);
    }

    public void saveRecentQuery(final String string2, final String string3) {
        if (TextUtils.isEmpty(string2)) {
            return;
        }
        if (!this.mTwoLineDisplay && !TextUtils.isEmpty(string3)) {
            throw new IllegalArgumentException();
        }
        new Thread("saveRecentQuery"){

            @Override
            public void run() {
                SearchRecentSuggestions.this.saveRecentQueryBlocking(string2, string3);
                sWritesInProgress.release();
            }
        }.start();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void truncateHistory(ContentResolver var1_1, int var2_3) {
        if (var2_3 < 0) throw new IllegalArgumentException();
        var3_4 = null;
        if (var2_3 <= 0) ** GOTO lbl13
        try {
            var3_5 = new StringBuilder();
            var3_5.append("_id IN (SELECT _id FROM suggestions ORDER BY date DESC LIMIT -1 OFFSET ");
            var3_5.append(String.valueOf(var2_3));
            var3_5.append(")");
            var3_6 = var3_5.toString();
lbl13: // 2 sources:
            var1_1.delete(this.mSuggestionsUri, (String)var3_7, null);
            return;
        }
        catch (RuntimeException var1_2) {
            Log.e("SearchSuggestions", "truncateHistory", var1_2);
        }
    }

    void waitForSave() {
        do {
            sWritesInProgress.acquireUninterruptibly();
        } while (sWritesInProgress.availablePermits() > 0);
    }

    private static class SuggestionColumns
    implements BaseColumns {
        public static final String DATE = "date";
        public static final String DISPLAY1 = "display1";
        public static final String DISPLAY2 = "display2";
        public static final String QUERY = "query";

        private SuggestionColumns() {
        }
    }

}

