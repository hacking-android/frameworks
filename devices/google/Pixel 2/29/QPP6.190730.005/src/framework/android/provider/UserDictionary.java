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
import java.util.Locale;

public class UserDictionary {
    public static final String AUTHORITY = "user_dictionary";
    public static final Uri CONTENT_URI = Uri.parse("content://user_dictionary");
    private static final int FREQUENCY_MAX = 255;
    private static final int FREQUENCY_MIN = 0;

    public static class Words
    implements BaseColumns {
        public static final String APP_ID = "appid";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.google.userword";
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.google.userword";
        public static final Uri CONTENT_URI = Uri.parse("content://user_dictionary/words");
        public static final String DEFAULT_SORT_ORDER = "frequency DESC";
        public static final String FREQUENCY = "frequency";
        public static final String LOCALE = "locale";
        @Deprecated
        public static final int LOCALE_TYPE_ALL = 0;
        @Deprecated
        public static final int LOCALE_TYPE_CURRENT = 1;
        public static final String SHORTCUT = "shortcut";
        public static final String WORD = "word";
        public static final String _ID = "_id";

        @Deprecated
        public static void addWord(Context context, String string2, int n, int n2) {
            if (n2 != 0 && n2 != 1) {
                return;
            }
            Locale locale = n2 == 1 ? Locale.getDefault() : null;
            Words.addWord(context, string2, n, null, locale);
        }

        public static void addWord(Context object, String string2, int n, String string3, Locale locale) {
            ContentResolver contentResolver = ((Context)object).getContentResolver();
            if (TextUtils.isEmpty(string2)) {
                return;
            }
            int n2 = n;
            if (n < 0) {
                n2 = 0;
            }
            n = n2;
            if (n2 > 255) {
                n = 255;
            }
            ContentValues contentValues = new ContentValues(5);
            contentValues.put(WORD, string2);
            contentValues.put(FREQUENCY, n);
            object = locale == null ? null : locale.toString();
            contentValues.put(LOCALE, (String)object);
            contentValues.put(APP_ID, 0);
            contentValues.put(SHORTCUT, string3);
            contentResolver.insert(CONTENT_URI, contentValues);
        }
    }

}

