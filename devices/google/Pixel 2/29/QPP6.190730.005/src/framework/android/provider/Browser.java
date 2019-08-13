/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.annotation.UnsupportedAppUsage;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.BrowserContract;
import android.webkit.WebIconDatabase;

public class Browser {
    public static final Uri BOOKMARKS_URI = Uri.parse("content://browser/bookmarks");
    public static final String EXTRA_APPLICATION_ID = "com.android.browser.application_id";
    public static final String EXTRA_CREATE_NEW_TAB = "create_new_tab";
    public static final String EXTRA_HEADERS = "com.android.browser.headers";
    public static final String EXTRA_SHARE_FAVICON = "share_favicon";
    public static final String EXTRA_SHARE_SCREENSHOT = "share_screenshot";
    public static final String[] HISTORY_PROJECTION = new String[]{"_id", "url", "visits", "date", "bookmark", "title", "favicon", "thumbnail", "touch_icon", "user_entered"};
    public static final int HISTORY_PROJECTION_BOOKMARK_INDEX = 4;
    public static final int HISTORY_PROJECTION_DATE_INDEX = 3;
    public static final int HISTORY_PROJECTION_FAVICON_INDEX = 6;
    public static final int HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int HISTORY_PROJECTION_THUMBNAIL_INDEX = 7;
    public static final int HISTORY_PROJECTION_TITLE_INDEX = 5;
    public static final int HISTORY_PROJECTION_TOUCH_ICON_INDEX = 8;
    public static final int HISTORY_PROJECTION_URL_INDEX = 1;
    public static final int HISTORY_PROJECTION_VISITS_INDEX = 2;
    public static final String INITIAL_ZOOM_LEVEL = "browser.initialZoomLevel";
    private static final String LOGTAG = "browser";
    private static final int MAX_HISTORY_COUNT = 250;
    public static final String[] SEARCHES_PROJECTION;
    public static final int SEARCHES_PROJECTION_DATE_INDEX = 2;
    public static final int SEARCHES_PROJECTION_SEARCH_INDEX = 1;
    public static final Uri SEARCHES_URI;
    public static final String[] TRUNCATE_HISTORY_PROJECTION;
    public static final int TRUNCATE_HISTORY_PROJECTION_ID_INDEX = 0;
    public static final int TRUNCATE_N_OLDEST = 5;

    static {
        TRUNCATE_HISTORY_PROJECTION = new String[]{"_id", "date"};
        SEARCHES_URI = Uri.parse("content://browser/searches");
        SEARCHES_PROJECTION = new String[]{"_id", "search", "date"};
    }

    private static final void addOrUrlEquals(StringBuilder stringBuilder) {
        stringBuilder.append(" OR url = ");
    }

    public static final void addSearchUrl(ContentResolver contentResolver, String string2) {
    }

    public static final boolean canClearHistory(ContentResolver contentResolver) {
        return false;
    }

    public static final void clearHistory(ContentResolver contentResolver) {
    }

    public static final void clearSearches(ContentResolver contentResolver) {
    }

    public static final void deleteFromHistory(ContentResolver contentResolver, String string2) {
    }

    public static final void deleteHistoryTimeFrame(ContentResolver contentResolver, long l, long l2) {
    }

    public static final Cursor getAllBookmarks(ContentResolver contentResolver) throws IllegalStateException {
        return new MatrixCursor(new String[]{"url"}, 0);
    }

    public static final Cursor getAllVisitedUrls(ContentResolver contentResolver) throws IllegalStateException {
        return new MatrixCursor(new String[]{"url"}, 0);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static final String[] getVisitedHistory(ContentResolver contentResolver) {
        return new String[0];
    }

    private static final Cursor getVisitedLike(ContentResolver contentResolver, String charSequence) {
        boolean bl = false;
        Object object = charSequence;
        if (((String)object).startsWith("http://")) {
            charSequence = ((String)object).substring(7);
        } else {
            charSequence = object;
            if (((String)object).startsWith("https://")) {
                charSequence = ((String)object).substring(8);
                bl = true;
            }
        }
        object = charSequence;
        if (((String)charSequence).startsWith("www.")) {
            object = ((String)charSequence).substring(4);
        }
        if (bl) {
            charSequence = new StringBuilder("url = ");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://");
            stringBuilder.append((String)object);
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, stringBuilder.toString());
            Browser.addOrUrlEquals((StringBuilder)charSequence);
            stringBuilder = new StringBuilder();
            stringBuilder.append("https://www.");
            stringBuilder.append((String)object);
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, stringBuilder.toString());
        } else {
            charSequence = new StringBuilder("url = ");
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, (String)object);
            Browser.addOrUrlEquals((StringBuilder)charSequence);
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append("www.");
            charSequence2.append((String)object);
            charSequence2 = charSequence2.toString();
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, (String)charSequence2);
            Browser.addOrUrlEquals((StringBuilder)charSequence);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://");
            stringBuilder.append((String)object);
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, stringBuilder.toString());
            Browser.addOrUrlEquals((StringBuilder)charSequence);
            object = new StringBuilder();
            ((StringBuilder)object).append("http://");
            ((StringBuilder)object).append((String)charSequence2);
            DatabaseUtils.appendEscapedSQLString((StringBuilder)charSequence, ((StringBuilder)object).toString());
        }
        object = BrowserContract.History.CONTENT_URI;
        charSequence = ((StringBuilder)charSequence).toString();
        return contentResolver.query((Uri)object, new String[]{"_id", "visits"}, (String)charSequence, null, null);
    }

    public static final void requestAllIcons(ContentResolver contentResolver, String string2, WebIconDatabase.IconListener iconListener) {
    }

    public static final void saveBookmark(Context context, String string2, String string3) {
    }

    public static final void sendString(Context context, String string2) {
        Browser.sendString(context, string2, context.getString(17040986));
    }

    @UnsupportedAppUsage
    public static final void sendString(Context context, String object, String string2) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", (String)object);
        try {
            object = Intent.createChooser(intent, string2);
            ((Intent)object).setFlags(268435456);
            context.startActivity((Intent)object);
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            // empty catch block
        }
    }

    public static final void truncateHistory(ContentResolver contentResolver) {
    }

    public static final void updateVisitedHistory(ContentResolver contentResolver, String string2, boolean bl) {
    }

    public static class BookmarkColumns
    implements BaseColumns {
        public static final String BOOKMARK = "bookmark";
        public static final String CREATED = "created";
        public static final String DATE = "date";
        public static final String FAVICON = "favicon";
        public static final String THUMBNAIL = "thumbnail";
        public static final String TITLE = "title";
        public static final String TOUCH_ICON = "touch_icon";
        public static final String URL = "url";
        public static final String USER_ENTERED = "user_entered";
        public static final String VISITS = "visits";
    }

    public static class SearchColumns
    implements BaseColumns {
        public static final String DATE = "date";
        public static final String SEARCH = "search";
        @Deprecated
        public static final String URL = "url";
    }

}

