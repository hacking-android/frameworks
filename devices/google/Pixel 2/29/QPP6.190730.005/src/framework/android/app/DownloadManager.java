/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.AppGlobals;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.provider.Downloads;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Pair;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DownloadManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String ACTION_DOWNLOAD_COMPLETE = "android.intent.action.DOWNLOAD_COMPLETE";
    @SystemApi
    public static final String ACTION_DOWNLOAD_COMPLETED = "android.intent.action.DOWNLOAD_COMPLETED";
    public static final String ACTION_NOTIFICATION_CLICKED = "android.intent.action.DOWNLOAD_NOTIFICATION_CLICKED";
    public static final String ACTION_VIEW_DOWNLOADS = "android.intent.action.VIEW_DOWNLOADS";
    public static final String COLUMN_ALLOW_WRITE = "allow_write";
    public static final String COLUMN_BYTES_DOWNLOADED_SO_FAR = "bytes_so_far";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAST_MODIFIED_TIMESTAMP = "last_modified_timestamp";
    @Deprecated
    public static final String COLUMN_LOCAL_FILENAME = "local_filename";
    public static final String COLUMN_LOCAL_URI = "local_uri";
    public static final String COLUMN_MEDIAPROVIDER_URI = "mediaprovider_uri";
    public static final String COLUMN_MEDIASTORE_URI = "mediastore_uri";
    public static final String COLUMN_MEDIA_TYPE = "media_type";
    public static final String COLUMN_REASON = "reason";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TOTAL_SIZE_BYTES = "total_size";
    public static final String COLUMN_URI = "uri";
    public static final int ERROR_BLOCKED = 1010;
    public static final int ERROR_CANNOT_RESUME = 1008;
    public static final int ERROR_DEVICE_NOT_FOUND = 1007;
    public static final int ERROR_FILE_ALREADY_EXISTS = 1009;
    public static final int ERROR_FILE_ERROR = 1001;
    public static final int ERROR_HTTP_DATA_ERROR = 1004;
    public static final int ERROR_INSUFFICIENT_SPACE = 1006;
    public static final int ERROR_TOO_MANY_REDIRECTS = 1005;
    public static final int ERROR_UNHANDLED_HTTP_CODE = 1002;
    public static final int ERROR_UNKNOWN = 1000;
    public static final String EXTRA_DOWNLOAD_ID = "extra_download_id";
    public static final String EXTRA_NOTIFICATION_CLICK_DOWNLOAD_IDS = "extra_click_download_ids";
    public static final String INTENT_EXTRAS_SORT_BY_SIZE = "android.app.DownloadManager.extra_sortBySize";
    private static final String NON_DOWNLOADMANAGER_DOWNLOAD = "non-dwnldmngr-download-dont-retry2download";
    public static final int PAUSED_QUEUED_FOR_WIFI = 3;
    public static final int PAUSED_UNKNOWN = 4;
    public static final int PAUSED_WAITING_FOR_NETWORK = 2;
    public static final int PAUSED_WAITING_TO_RETRY = 1;
    public static final int STATUS_FAILED = 16;
    public static final int STATUS_PAUSED = 4;
    public static final int STATUS_PENDING = 1;
    public static final int STATUS_RUNNING = 2;
    public static final int STATUS_SUCCESSFUL = 8;
    @UnsupportedAppUsage
    public static final String[] UNDERLYING_COLUMNS = new String[]{"_id", "_data AS local_filename", "mediaprovider_uri", "destination", "title", "description", "uri", "status", "hint", "mimetype AS media_type", "total_bytes AS total_size", "lastmod AS last_modified_timestamp", "current_bytes AS bytes_so_far", "allow_write", "'placeholder' AS local_uri", "'placeholder' AS reason"};
    private boolean mAccessFilename;
    private Uri mBaseUri = Downloads.Impl.CONTENT_URI;
    private final String mPackageName;
    private final ContentResolver mResolver;

    public DownloadManager(Context context) {
        this.mResolver = context.getContentResolver();
        this.mPackageName = context.getPackageName();
        boolean bl = context.getApplicationInfo().targetSdkVersion < 24;
        this.mAccessFilename = bl;
    }

    public static long getActiveNetworkWarningBytes(Context context) {
        return -1L;
    }

    public static Long getMaxBytesOverMobile(Context context) {
        long l;
        try {
            l = Settings.Global.getLong(context.getContentResolver(), "download_manager_max_bytes_over_mobile");
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return null;
        }
        return l;
    }

    public static Long getRecommendedMaxBytesOverMobile(Context context) {
        long l;
        try {
            l = Settings.Global.getLong(context.getContentResolver(), "download_manager_recommended_max_bytes_over_mobile");
        }
        catch (Settings.SettingNotFoundException settingNotFoundException) {
            return null;
        }
        return l;
    }

    @UnsupportedAppUsage
    static String[] getWhereArgsForIds(long[] arrl) {
        return DownloadManager.getWhereArgsForIds(arrl, new String[arrl.length]);
    }

    static String[] getWhereArgsForIds(long[] arrl, String[] arrstring) {
        for (int i = 0; i < arrl.length; ++i) {
            arrstring[i] = Long.toString(arrl[i]);
        }
        return arrstring;
    }

    @UnsupportedAppUsage
    static String getWhereClauseForIds(long[] arrl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < arrl.length; ++i) {
            if (i > 0) {
                stringBuilder.append("OR ");
            }
            stringBuilder.append(COLUMN_ID);
            stringBuilder.append(" = ? ");
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static boolean isActiveNetworkExpensive(Context context) {
        return false;
    }

    private static void validateArgumentIsNonEmpty(String string2, String charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(" can't be null");
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Deprecated
    public long addCompletedDownload(String string2, String string3, boolean bl, String string4, String string5, long l, boolean bl2) {
        return this.addCompletedDownload(string2, string3, bl, string4, string5, l, bl2, false, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String string2, String string3, boolean bl, String string4, String string5, long l, boolean bl2, Uri uri, Uri uri2) {
        return this.addCompletedDownload(string2, string3, bl, string4, string5, l, bl2, false, uri, uri2);
    }

    @Deprecated
    public long addCompletedDownload(String string2, String string3, boolean bl, String string4, String string5, long l, boolean bl2, boolean bl3) {
        return this.addCompletedDownload(string2, string3, bl, string4, string5, l, bl2, bl3, null, null);
    }

    @Deprecated
    public long addCompletedDownload(String object, String string2, boolean bl, String string3, String string4, long l, boolean bl2, boolean bl3, Uri object2, Uri uri) {
        DownloadManager.validateArgumentIsNonEmpty(COLUMN_TITLE, (String)object);
        DownloadManager.validateArgumentIsNonEmpty(COLUMN_DESCRIPTION, string2);
        DownloadManager.validateArgumentIsNonEmpty("path", string4);
        DownloadManager.validateArgumentIsNonEmpty("mimeType", string3);
        if (l >= 0L) {
            object2 = object2 != null ? new Request((Uri)object2) : new Request(NON_DOWNLOADMANAGER_DOWNLOAD);
            ((Request)object2).setTitle((CharSequence)object).setDescription(string2).setMimeType(string3);
            if (uri != null) {
                ((Request)object2).addRequestHeader("Referer", uri.toString());
            }
            object = ((Request)object2).toContentValues(null);
            ((ContentValues)object).put("destination", 6);
            ((ContentValues)object).put("_data", string4);
            ((ContentValues)object).put(COLUMN_STATUS, 200);
            ((ContentValues)object).put("total_bytes", l);
            int n = 2;
            int n2 = bl ? 0 : 2;
            ((ContentValues)object).put("scanned", n2);
            n2 = bl2 ? 3 : n;
            ((ContentValues)object).put("visibility", n2);
            ((ContentValues)object).put(COLUMN_ALLOW_WRITE, (int)bl3);
            object = this.mResolver.insert(Downloads.Impl.CONTENT_URI, (ContentValues)object);
            if (object == null) {
                return -1L;
            }
            return Long.parseLong(((Uri)object).getLastPathSegment());
        }
        throw new IllegalArgumentException(" invalid value for param: totalBytes");
    }

    public long enqueue(Request object) {
        object = ((Request)object).toContentValues(this.mPackageName);
        return Long.parseLong(this.mResolver.insert(Downloads.Impl.CONTENT_URI, (ContentValues)object).getLastPathSegment());
    }

    public void forceDownload(long ... arrl) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STATUS, 190);
        contentValues.put("control", 0);
        contentValues.put("bypass_recommended_size_limit", 1);
        this.mResolver.update(this.mBaseUri, contentValues, DownloadManager.getWhereClauseForIds(arrl), DownloadManager.getWhereArgsForIds(arrl));
    }

    public Uri getDownloadUri(long l) {
        return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, l);
    }

    public String getMimeTypeForDownloadedFile(long l) {
        Object object;
        block7 : {
            object = new Query().setFilterById(l);
            Object object2 = null;
            try {
                object = this.query((Query)object);
                if (object == null) {
                    if (object != null) {
                        object.close();
                    }
                    return null;
                }
                object2 = object;
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    object2.close();
                }
                throw throwable;
            }
            if (!object.moveToFirst()) break block7;
            object2 = object;
            String string2 = object.getString(object.getColumnIndexOrThrow(COLUMN_MEDIA_TYPE));
            object.close();
            return string2;
        }
        object.close();
        return null;
    }

    public Uri getUriForDownloadedFile(long l) {
        Object object;
        block8 : {
            object = new Query().setFilterById(l);
            Object object2 = null;
            try {
                object = this.query((Query)object);
                if (object == null) {
                    if (object != null) {
                        object.close();
                    }
                    return null;
                }
                object2 = object;
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    object2.close();
                }
                throw throwable;
            }
            if (!object.moveToFirst()) break block8;
            object2 = object;
            if (8 != object.getInt(object.getColumnIndexOrThrow(COLUMN_STATUS))) break block8;
            object2 = object;
            Uri uri = ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, l);
            object.close();
            return uri;
        }
        object.close();
        return null;
    }

    public int markRowDeleted(long ... arrl) {
        if (arrl != null && arrl.length != 0) {
            return this.mResolver.delete(this.mBaseUri, DownloadManager.getWhereClauseForIds(arrl), DownloadManager.getWhereArgsForIds(arrl));
        }
        throw new IllegalArgumentException("input param 'ids' can't be null");
    }

    public ParcelFileDescriptor openDownloadedFile(long l) throws FileNotFoundException {
        return this.mResolver.openFileDescriptor(this.getDownloadUri(l), "r");
    }

    public Cursor query(Query query) {
        return this.query(query, UNDERLYING_COLUMNS);
    }

    public Cursor query(Query object, String[] arrstring) {
        if ((object = ((Query)object).runQuery(this.mResolver, arrstring, this.mBaseUri)) == null) {
            return null;
        }
        return new CursorTranslator((Cursor)object, this.mBaseUri, this.mAccessFilename);
    }

    public int remove(long ... arrl) {
        return this.markRowDeleted(arrl);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean rename(Context var1_1, long var2_3, String var4_4) {
        block12 : {
            block11 : {
                block8 : {
                    block9 : {
                        block10 : {
                            if (!FileUtils.isValidFatFilename((String)var4_4)) {
                                var1_1 = new StringBuilder();
                                var1_1.append((String)var4_4);
                                var1_1.append(" is not a valid filename");
                                throw new SecurityException(var1_1.toString());
                            }
                            var5_6 = this.query(new Query().setFilterById(new long[]{var2_3}));
                            if (var5_6 == null) break block11;
                            try {
                                if (!var5_6.moveToFirst()) ** GOTO lbl76
                                if (var5_6.getInt(var5_6.getColumnIndexOrThrow("status")) != 8) ** GOTO lbl69
                                var6_8 = var5_6.getString(var5_6.getColumnIndexOrThrow("local_filename"));
                                if (var6_8 == null) ** GOTO lbl62
                                var7_9 = new File((String)var6_8);
                                var8_10 = var7_9.exists();
                                if (!var8_10) break block8;
                                var5_6.close();
                                var6_8 = new File((String)var6_8);
                                var5_6 = new File(var6_8.getParentFile(), (String)var4_4);
                                if (var5_6.exists()) break block9;
                                if (!var6_8.renameTo((File)var5_6)) break block10;
                            }
                            catch (Throwable var1_2) {}
                            MediaStore.scanFile((Context)var1_1, (File)var6_8);
                            MediaStore.scanFile((Context)var1_1, (File)var5_6);
                            var1_1 = new ContentValues();
                            var1_1.put("title", (String)var4_4);
                            var1_1.put("_data", var5_6.toString());
                            var1_1.putNull("mediaprovider_uri");
                            var4_4 = new long[]{var2_3};
                            if (this.mResolver.update(this.mBaseUri, (ContentValues)var1_1, DownloadManager.getWhereClauseForIds((long[])var4_4), DownloadManager.getWhereArgsForIds((long[])var4_4)) != 1) return false;
                            return true;
                        }
                        var1_1 = new StringBuilder();
                        var1_1.append("Failed to rename file from ");
                        var1_1.append(var6_8);
                        var1_1.append(" to ");
                        var1_1.append(var5_6);
                        throw new IllegalStateException(var1_1.toString());
                    }
                    var1_1 = new StringBuilder();
                    var1_1.append("File already exists: ");
                    var1_1.append(var5_6);
                    throw new IllegalStateException(var1_1.toString());
                }
                var4_4 = new StringBuilder();
                var4_4.append("Downloaded file doesn't exist anymore: ");
                var4_4.append(DatabaseUtils.dumpCurrentRowToString((Cursor)var5_6));
                var1_1 = new IllegalStateException(var4_4.toString());
                throw var1_1;
lbl62: // 1 sources:
                var4_4 = new StringBuilder();
                var4_4.append("Download doesn't have a valid file path: ");
                var4_4.append(DatabaseUtils.dumpCurrentRowToString((Cursor)var5_6));
                var1_1 = new IllegalStateException(var4_4.toString());
                throw var1_1;
lbl69: // 1 sources:
                var4_4 = new StringBuilder();
                var4_4.append("Download is not completed yet: ");
                var4_4.append(DatabaseUtils.dumpCurrentRowToString((Cursor)var5_6));
                var1_1 = new IllegalStateException(var4_4.toString());
                throw var1_1;
lbl76: // 1 sources:
                var1_1 = new StringBuilder();
                var1_1.append("Missing download id=");
                var1_1.append(var2_3);
                var4_4 = new IllegalStateException(var1_1.toString());
                throw var4_4;
                break block12;
            }
            var4_4 = new StringBuilder();
            var4_4.append("Missing cursor for download id=");
            var4_4.append(var2_3);
            var1_1 = new IllegalStateException(var4_4.toString());
            throw var1_1;
        }
        try {
            throw var1_2;
        }
        catch (Throwable var4_5) {
            if (var5_6 == null) throw var4_5;
            try {
                var5_6.close();
                throw var4_5;
            }
            catch (Throwable var5_7) {
                var1_2.addSuppressed(var5_7);
            }
            throw var4_5;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void restartDownload(long ... object) {
        boolean bl;
        Object object2 = this.query(new Query().setFilterById((long)object));
        object2.moveToFirst();
        while (!(bl = object2.isAfterLast())) {
            int n = object2.getInt(object2.getColumnIndex(COLUMN_STATUS));
            if (n != 8 && n != 16) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot restart incomplete download: ");
                stringBuilder.append(object2.getLong(object2.getColumnIndex(COLUMN_ID)));
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            object2.moveToNext();
        }
        object2 = new ContentValues();
        ((ContentValues)object2).put("current_bytes", 0);
        ((ContentValues)object2).put("total_bytes", -1);
        ((ContentValues)object2).putNull("_data");
        ((ContentValues)object2).put(COLUMN_STATUS, 190);
        ((ContentValues)object2).put("numfailed", 0);
        this.mResolver.update(this.mBaseUri, (ContentValues)object2, DownloadManager.getWhereClauseForIds(object), DownloadManager.getWhereArgsForIds(object));
        return;
        finally {
            object2.close();
        }
    }

    @UnsupportedAppUsage
    public void setAccessAllDownloads(boolean bl) {
        this.mBaseUri = bl ? Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI : Downloads.Impl.CONTENT_URI;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setAccessFilename(boolean bl) {
        this.mAccessFilename = bl;
    }

    private static class CursorTranslator
    extends CursorWrapper {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean mAccessFilename;
        private final Uri mBaseUri;

        public CursorTranslator(Cursor cursor, Uri uri, boolean bl) {
            super(cursor);
            this.mBaseUri = uri;
            this.mAccessFilename = bl;
        }

        private long getErrorCode(int n) {
            if (400 <= n && n < 488 || 500 <= n && n < 600) {
                return n;
            }
            if (n != 198) {
                if (n != 199) {
                    if (n != 488) {
                        if (n != 489) {
                            if (n != 497) {
                                switch (n) {
                                    default: {
                                        return 1000L;
                                    }
                                    case 495: {
                                        return 1004L;
                                    }
                                    case 493: 
                                    case 494: {
                                        return 1002L;
                                    }
                                    case 492: 
                                }
                                return 1001L;
                            }
                            return 1005L;
                        }
                        return 1008L;
                    }
                    return 1009L;
                }
                return 1007L;
            }
            return 1006L;
        }

        private String getLocalUri() {
            long l = this.getLong(this.getColumnIndex("destination"));
            if (l != 4L && l != 0L && l != 6L) {
                l = this.getLong(this.getColumnIndex(DownloadManager.COLUMN_ID));
                return ContentUris.withAppendedId(Downloads.Impl.ALL_DOWNLOADS_CONTENT_URI, l).toString();
            }
            String string2 = super.getString(this.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
            if (string2 == null) {
                return null;
            }
            return Uri.fromFile(new File(string2)).toString();
        }

        private long getPausedReason(int n) {
            switch (n) {
                default: {
                    return 4L;
                }
                case 196: {
                    return 3L;
                }
                case 195: {
                    return 2L;
                }
                case 194: 
            }
            return 1L;
        }

        private long getReason(int n) {
            int n2 = this.translateStatus(n);
            if (n2 != 4) {
                if (n2 != 16) {
                    return 0L;
                }
                return this.getErrorCode(n);
            }
            return this.getPausedReason(n);
        }

        private int translateStatus(int n) {
            if (n != 190) {
                if (n != 200) {
                    switch (n) {
                        default: {
                            return 16;
                        }
                        case 193: 
                        case 194: 
                        case 195: 
                        case 196: {
                            return 4;
                        }
                        case 192: 
                    }
                    return 2;
                }
                return 8;
            }
            return 1;
        }

        @Override
        public int getInt(int n) {
            return (int)this.getLong(n);
        }

        @Override
        public long getLong(int n) {
            if (this.getColumnName(n).equals(DownloadManager.COLUMN_REASON)) {
                return this.getReason(super.getInt(this.getColumnIndex(DownloadManager.COLUMN_STATUS)));
            }
            if (this.getColumnName(n).equals(DownloadManager.COLUMN_STATUS)) {
                return this.translateStatus(super.getInt(this.getColumnIndex(DownloadManager.COLUMN_STATUS)));
            }
            return super.getLong(n);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public String getString(int var1_1) {
            block4 : {
                block3 : {
                    var2_2 = this.getColumnName(var1_1);
                    var3_3 = var2_2.hashCode();
                    if (var3_3 == -1204869480) break block3;
                    if (var3_3 != 22072411 || !var2_2.equals("local_filename")) ** GOTO lbl-1000
                    var3_3 = 1;
                    break block4;
                }
                if (var2_2.equals("local_uri")) {
                    var3_3 = 0;
                } else lbl-1000: // 2 sources:
                {
                    var3_3 = -1;
                }
            }
            if (var3_3 == 0) return this.getLocalUri();
            if (var3_3 != 1) {
                return super.getString(var1_1);
            }
            if (this.mAccessFilename == false) throw new SecurityException("COLUMN_LOCAL_FILENAME is deprecated; use ContentResolver.openFileDescriptor() instead");
            return super.getString(var1_1);
        }
    }

    public static class Query {
        public static final int ORDER_ASCENDING = 1;
        public static final int ORDER_DESCENDING = 2;
        private String mFilterString = null;
        private long[] mIds = null;
        private boolean mOnlyIncludeVisibleInDownloadsUi = false;
        private String mOrderByColumn = "lastmod";
        private int mOrderDirection = 2;
        private Integer mStatusFlags = null;

        private String joinStrings(String string2, Iterable<String> object) {
            StringBuilder stringBuilder = new StringBuilder();
            boolean bl = true;
            object = object.iterator();
            while (object.hasNext()) {
                String string3 = (String)object.next();
                if (!bl) {
                    stringBuilder.append(string2);
                }
                stringBuilder.append(string3);
                bl = false;
            }
            return stringBuilder.toString();
        }

        private String statusClause(String string2, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(DownloadManager.COLUMN_STATUS);
            stringBuilder.append(string2);
            stringBuilder.append("'");
            stringBuilder.append(n);
            stringBuilder.append("'");
            return stringBuilder.toString();
        }

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        public Query orderBy(String charSequence, int n) {
            block6 : {
                block5 : {
                    block4 : {
                        if (n != 1 && n != 2) {
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append("Invalid direction: ");
                            ((StringBuilder)charSequence).append(n);
                            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
                        }
                        if (!((String)charSequence).equals(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)) break block4;
                        this.mOrderByColumn = "lastmod";
                        break block5;
                    }
                    if (!((String)charSequence).equals(DownloadManager.COLUMN_TOTAL_SIZE_BYTES)) break block6;
                    this.mOrderByColumn = "total_bytes";
                }
                this.mOrderDirection = n;
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot order by ");
            stringBuilder.append((String)charSequence);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        Cursor runQuery(ContentResolver contentResolver, String[] arrstring, Uri uri) {
            Object object;
            Serializable serializable;
            Object object2 = new ArrayList<String>();
            Object[] arrobject = this.mIds;
            int n = arrobject == null ? 0 : arrobject.length;
            if (this.mFilterString != null) {
                ++n;
            }
            arrobject = new String[n];
            if (n > 0) {
                object = this.mIds;
                if (object != null) {
                    object2.add(DownloadManager.getWhereClauseForIds((long[])object));
                    DownloadManager.getWhereArgsForIds(this.mIds, (String[])arrobject);
                }
                if (this.mFilterString != null) {
                    object2.add("title LIKE ?");
                    n = arrobject.length;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("%");
                    ((StringBuilder)object).append(this.mFilterString);
                    ((StringBuilder)object).append("%");
                    arrobject[n - 1] = (long)((StringBuilder)object).toString();
                }
            }
            if (this.mStatusFlags != null) {
                serializable = new ArrayList<String>();
                if ((this.mStatusFlags & 1) != 0) {
                    serializable.add(this.statusClause("=", 190));
                }
                if ((this.mStatusFlags & 2) != 0) {
                    serializable.add(this.statusClause("=", 192));
                }
                if ((this.mStatusFlags & 4) != 0) {
                    serializable.add(this.statusClause("=", 193));
                    serializable.add(this.statusClause("=", 194));
                    serializable.add(this.statusClause("=", 195));
                    serializable.add(this.statusClause("=", 196));
                }
                if ((this.mStatusFlags & 8) != 0) {
                    serializable.add(this.statusClause("=", 200));
                }
                if ((this.mStatusFlags & 16) != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("(");
                    ((StringBuilder)object).append(this.statusClause(">=", 400));
                    ((StringBuilder)object).append(" AND ");
                    ((StringBuilder)object).append(this.statusClause("<", 600));
                    ((StringBuilder)object).append(")");
                    serializable.add(((StringBuilder)object).toString());
                }
                object2.add(this.joinStrings(" OR ", (Iterable<String>)((Object)serializable)));
            }
            if (this.mOnlyIncludeVisibleInDownloadsUi) {
                object2.add("is_visible_in_downloads_ui != '0'");
            }
            object2.add("deleted != '1'");
            object = this.joinStrings(" AND ", (Iterable<String>)object2);
            object2 = this.mOrderDirection == 1 ? "ASC" : "DESC";
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(this.mOrderByColumn);
            ((StringBuilder)serializable).append(" ");
            ((StringBuilder)serializable).append((String)object2);
            return contentResolver.query(uri, arrstring, (String)object, (String[])arrobject, ((StringBuilder)serializable).toString());
        }

        public Query setFilterById(long ... arrl) {
            this.mIds = arrl;
            return this;
        }

        public Query setFilterByStatus(int n) {
            this.mStatusFlags = n;
            return this;
        }

        public Query setFilterByString(String string2) {
            this.mFilterString = string2;
            return this;
        }

        @UnsupportedAppUsage
        public Query setOnlyIncludeVisibleInDownloadsUi(boolean bl) {
            this.mOnlyIncludeVisibleInDownloadsUi = bl;
            return this;
        }
    }

    public static class Request {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        @Deprecated
        public static final int NETWORK_BLUETOOTH = 4;
        public static final int NETWORK_MOBILE = 1;
        public static final int NETWORK_WIFI = 2;
        private static final int SCANNABLE_VALUE_NO = 2;
        private static final int SCANNABLE_VALUE_YES = 0;
        public static final int VISIBILITY_HIDDEN = 2;
        public static final int VISIBILITY_VISIBLE = 0;
        public static final int VISIBILITY_VISIBLE_NOTIFY_COMPLETED = 1;
        public static final int VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION = 3;
        private int mAllowedNetworkTypes = -1;
        private CharSequence mDescription;
        private Uri mDestinationUri;
        private int mFlags = 0;
        private boolean mIsVisibleInDownloadsUi = true;
        private boolean mMeteredAllowed = true;
        private String mMimeType;
        private int mNotificationVisibility = 0;
        private List<Pair<String, String>> mRequestHeaders = new ArrayList<Pair<String, String>>();
        private boolean mRoamingAllowed = true;
        private boolean mScannable = false;
        private CharSequence mTitle;
        @UnsupportedAppUsage
        private Uri mUri;

        public Request(Uri uri) {
            if (uri != null) {
                CharSequence charSequence = uri.getScheme();
                if (charSequence != null && (((String)charSequence).equals("http") || ((String)charSequence).equals("https"))) {
                    this.mUri = uri;
                    return;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Can only download HTTP/HTTPS URIs: ");
                ((StringBuilder)charSequence).append(uri);
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            throw new NullPointerException();
        }

        Request(String string2) {
            this.mUri = Uri.parse(string2);
        }

        private void encodeHttpHeaders(ContentValues contentValues) {
            int n = 0;
            for (Pair<String, String> pair : this.mRequestHeaders) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)pair.first);
                stringBuilder.append(": ");
                stringBuilder.append((String)pair.second);
                String object = stringBuilder.toString();
                stringBuilder = new StringBuilder();
                stringBuilder.append("http_header_");
                stringBuilder.append(n);
                contentValues.put(stringBuilder.toString(), object);
                ++n;
            }
        }

        private void putIfNonNull(ContentValues contentValues, String string2, Object object) {
            if (object != null) {
                contentValues.put(string2, object.toString());
            }
        }

        private void setDestinationFromBase(File file, String string2) {
            if (string2 != null) {
                this.mDestinationUri = Uri.withAppendedPath(Uri.fromFile(file), string2);
                return;
            }
            throw new NullPointerException("subPath cannot be null");
        }

        public Request addRequestHeader(String string2, String string3) {
            if (string2 != null) {
                if (!string2.contains(":")) {
                    String string4 = string3;
                    if (string3 == null) {
                        string4 = "";
                    }
                    this.mRequestHeaders.add(Pair.create(string2, string4));
                    return this;
                }
                throw new IllegalArgumentException("header may not contain ':'");
            }
            throw new NullPointerException("header cannot be null");
        }

        @Deprecated
        public void allowScanningByMediaScanner() {
            this.mScannable = true;
        }

        public Request setAllowedNetworkTypes(int n) {
            this.mAllowedNetworkTypes = n;
            return this;
        }

        public Request setAllowedOverMetered(boolean bl) {
            this.mMeteredAllowed = bl;
            return this;
        }

        public Request setAllowedOverRoaming(boolean bl) {
            this.mRoamingAllowed = bl;
            return this;
        }

        public Request setDescription(CharSequence charSequence) {
            this.mDescription = charSequence;
            return this;
        }

        public Request setDestinationInExternalFilesDir(Context object, String charSequence, String string2) {
            block5 : {
                block8 : {
                    block7 : {
                        block6 : {
                            if ((object = ((Context)object).getExternalFilesDir((String)charSequence)) == null) break block5;
                            if (!((File)object).exists()) break block6;
                            if (!((File)object).isDirectory()) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append(((File)object).getAbsolutePath());
                                ((StringBuilder)charSequence).append(" already exists and is not a directory");
                                throw new IllegalStateException(((StringBuilder)charSequence).toString());
                            }
                            break block7;
                        }
                        if (!((File)object).mkdirs()) break block8;
                    }
                    this.setDestinationFromBase((File)object, string2);
                    return this;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Unable to create directory: ");
                ((StringBuilder)charSequence).append(((File)object).getAbsolutePath());
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            throw new IllegalStateException("Failed to get external storage files directory");
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public Request setDestinationInExternalPublicDir(String charSequence, String string2) {
            File file = Environment.getExternalStoragePublicDirectory((String)charSequence);
            if (file == null) throw new IllegalStateException("Failed to get external storage public directory");
            Object object = AppGlobals.getInitialApplication();
            if (object.getApplicationInfo().targetSdkVersion < 29 && Environment.isExternalStorageLegacy()) {
                if (file.exists()) {
                    if (!file.isDirectory()) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(file.getAbsolutePath());
                        ((StringBuilder)charSequence).append(" already exists and is not a directory");
                        throw new IllegalStateException(((StringBuilder)charSequence).toString());
                    }
                } else if (!file.mkdirs()) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Unable to create directory: ");
                    ((StringBuilder)charSequence).append(file.getAbsolutePath());
                    throw new IllegalStateException(((StringBuilder)charSequence).toString());
                }
            } else {
                object = ((Context)object).getContentResolver().acquireContentProviderClient("downloads");
                Bundle bundle = new Bundle();
                bundle.putString("dir_type", (String)charSequence);
                ((ContentProviderClient)object).call("create_external_public_dir", null, bundle);
                ((ContentProviderClient)object).close();
            }
            this.setDestinationFromBase(file, string2);
            return this;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    if (object == null) throw throwable2;
                    try {
                        ((ContentProviderClient)object).close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
                            throw throwable2;
                        }
                        catch (RemoteException remoteException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unable to create directory: ");
                            stringBuilder.append(file.getAbsolutePath());
                            throw new IllegalStateException(stringBuilder.toString());
                        }
                    }
                }
            }
        }

        public Request setDestinationUri(Uri uri) {
            this.mDestinationUri = uri;
            return this;
        }

        public Request setMimeType(String string2) {
            this.mMimeType = string2;
            return this;
        }

        public Request setNotificationVisibility(int n) {
            this.mNotificationVisibility = n;
            return this;
        }

        public Request setRequiresCharging(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 1) : (this.mFlags &= -2);
            return this;
        }

        public Request setRequiresDeviceIdle(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 2) : (this.mFlags &= -3);
            return this;
        }

        @Deprecated
        public Request setShowRunningNotification(boolean bl) {
            Request request = bl ? this.setNotificationVisibility(0) : this.setNotificationVisibility(2);
            return request;
        }

        public Request setTitle(CharSequence charSequence) {
            this.mTitle = charSequence;
            return this;
        }

        @Deprecated
        public Request setVisibleInDownloadsUi(boolean bl) {
            this.mIsVisibleInDownloadsUi = bl;
            return this;
        }

        ContentValues toContentValues(String object) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DownloadManager.COLUMN_URI, this.mUri.toString());
            contentValues.put("is_public_api", true);
            contentValues.put("notificationpackage", (String)object);
            object = this.mDestinationUri;
            int n = 2;
            if (object != null) {
                contentValues.put("destination", 4);
                contentValues.put("hint", this.mDestinationUri.toString());
            } else {
                contentValues.put("destination", 2);
            }
            if (this.mScannable) {
                n = 0;
            }
            contentValues.put("scanned", n);
            if (!this.mRequestHeaders.isEmpty()) {
                this.encodeHttpHeaders(contentValues);
            }
            this.putIfNonNull(contentValues, DownloadManager.COLUMN_TITLE, this.mTitle);
            this.putIfNonNull(contentValues, DownloadManager.COLUMN_DESCRIPTION, this.mDescription);
            this.putIfNonNull(contentValues, "mimetype", this.mMimeType);
            contentValues.put("visibility", this.mNotificationVisibility);
            contentValues.put("allowed_network_types", this.mAllowedNetworkTypes);
            contentValues.put("allow_roaming", this.mRoamingAllowed);
            contentValues.put("allow_metered", this.mMeteredAllowed);
            contentValues.put("flags", this.mFlags);
            contentValues.put("is_visible_in_downloads_ui", this.mIsVisibleInDownloadsUi);
            return contentValues;
        }
    }

}

