/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.Int32Ref
 *  dalvik.system.CloseGuard
 */
package android.content;

import android.accounts.Account;
import android.annotation.RequiresPermission;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.UriGrantsManager;
import android.content.ComponentName;
import android.content.ContentInterface;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.IContentService;
import android.content.ISyncStatusObserver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.PeriodicSync;
import android.content.SyncAdapterType;
import android.content.SyncInfo;
import android.content.SyncRequest;
import android.content.SyncStatusInfo;
import android.content.SyncStatusObserver;
import android.content.UriPermission;
import android.content._$$Lambda$ContentResolver$7ILY1SWNxC2xhk_fQUG6tAXW9Ik;
import android.content._$$Lambda$ContentResolver$RVw7W0M7r0cGmbYi8rAG5GKxq4M;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.database.IContentObserver;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DeadObjectException;
import android.os.ICancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.system.Int32Ref;
import android.text.TextUtils;
import android.util.Log;
import android.util.Size;
import com.android.internal.util.MimeIconUtils;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ContentResolver
implements ContentInterface {
    public static final Intent ACTION_SYNC_CONN_STATUS_CHANGED;
    public static final String ANY_CURSOR_ITEM_TYPE = "vnd.android.cursor.item/*";
    public static final String CONTENT_SERVICE_NAME = "content";
    public static final String CURSOR_DIR_BASE_TYPE = "vnd.android.cursor.dir";
    public static final String CURSOR_ITEM_BASE_TYPE = "vnd.android.cursor.item";
    public static final boolean DEPRECATE_DATA_COLUMNS;
    public static final String DEPRECATE_DATA_PREFIX = "/mnt/content/";
    private static final boolean ENABLE_CONTENT_SAMPLE = false;
    public static final String EXTRA_HONORED_ARGS = "android.content.extra.HONORED_ARGS";
    public static final String EXTRA_REFRESH_SUPPORTED = "android.content.extra.REFRESH_SUPPORTED";
    public static final String EXTRA_SIZE = "android.content.extra.SIZE";
    public static final String EXTRA_TOTAL_COUNT = "android.content.extra.TOTAL_COUNT";
    public static final String MIME_TYPE_DEFAULT = "application/octet-stream";
    public static final int NOTIFY_SKIP_NOTIFY_FOR_DESCENDANTS = 2;
    public static final int NOTIFY_SYNC_TO_NETWORK = 1;
    public static final String QUERY_ARG_LIMIT = "android:query-arg-limit";
    public static final String QUERY_ARG_OFFSET = "android:query-arg-offset";
    public static final String QUERY_ARG_SORT_COLLATION = "android:query-arg-sort-collation";
    public static final String QUERY_ARG_SORT_COLUMNS = "android:query-arg-sort-columns";
    public static final String QUERY_ARG_SORT_DIRECTION = "android:query-arg-sort-direction";
    public static final String QUERY_ARG_SQL_GROUP_BY = "android:query-arg-sql-group-by";
    public static final String QUERY_ARG_SQL_HAVING = "android:query-arg-sql-having";
    public static final String QUERY_ARG_SQL_LIMIT = "android:query-arg-sql-limit";
    public static final String QUERY_ARG_SQL_SELECTION = "android:query-arg-sql-selection";
    public static final String QUERY_ARG_SQL_SELECTION_ARGS = "android:query-arg-sql-selection-args";
    public static final String QUERY_ARG_SQL_SORT_ORDER = "android:query-arg-sql-sort-order";
    public static final int QUERY_SORT_DIRECTION_ASCENDING = 0;
    public static final int QUERY_SORT_DIRECTION_DESCENDING = 1;
    public static final String SCHEME_ANDROID_RESOURCE = "android.resource";
    public static final String SCHEME_CONTENT = "content";
    public static final String SCHEME_FILE = "file";
    private static final int SLOW_THRESHOLD_MILLIS = 500;
    public static final int SYNC_ERROR_AUTHENTICATION = 2;
    public static final int SYNC_ERROR_CONFLICT = 5;
    public static final int SYNC_ERROR_INTERNAL = 8;
    public static final int SYNC_ERROR_IO = 3;
    private static final String[] SYNC_ERROR_NAMES;
    public static final int SYNC_ERROR_PARSE = 4;
    @UnsupportedAppUsage
    public static final int SYNC_ERROR_SYNC_ALREADY_IN_PROGRESS = 1;
    public static final int SYNC_ERROR_TOO_MANY_DELETIONS = 6;
    public static final int SYNC_ERROR_TOO_MANY_RETRIES = 7;
    public static final int SYNC_EXEMPTION_NONE = 0;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET = 1;
    public static final int SYNC_EXEMPTION_PROMOTE_BUCKET_WITH_TEMP = 2;
    @Deprecated
    public static final String SYNC_EXTRAS_ACCOUNT = "account";
    public static final String SYNC_EXTRAS_DISALLOW_METERED = "allow_metered";
    public static final String SYNC_EXTRAS_DISCARD_LOCAL_DELETIONS = "discard_deletions";
    public static final String SYNC_EXTRAS_DO_NOT_RETRY = "do_not_retry";
    public static final String SYNC_EXTRAS_EXPECTED_DOWNLOAD = "expected_download";
    public static final String SYNC_EXTRAS_EXPECTED_UPLOAD = "expected_upload";
    public static final String SYNC_EXTRAS_EXPEDITED = "expedited";
    @Deprecated
    public static final String SYNC_EXTRAS_FORCE = "force";
    public static final String SYNC_EXTRAS_IGNORE_BACKOFF = "ignore_backoff";
    public static final String SYNC_EXTRAS_IGNORE_SETTINGS = "ignore_settings";
    public static final String SYNC_EXTRAS_INITIALIZE = "initialize";
    public static final String SYNC_EXTRAS_MANUAL = "force";
    public static final String SYNC_EXTRAS_OVERRIDE_TOO_MANY_DELETIONS = "deletions_override";
    public static final String SYNC_EXTRAS_PRIORITY = "sync_priority";
    public static final String SYNC_EXTRAS_REQUIRE_CHARGING = "require_charging";
    public static final String SYNC_EXTRAS_UPLOAD = "upload";
    public static final int SYNC_OBSERVER_TYPE_ACTIVE = 4;
    public static final int SYNC_OBSERVER_TYPE_ALL = Integer.MAX_VALUE;
    public static final int SYNC_OBSERVER_TYPE_PENDING = 2;
    public static final int SYNC_OBSERVER_TYPE_SETTINGS = 1;
    @UnsupportedAppUsage
    public static final int SYNC_OBSERVER_TYPE_STATUS = 8;
    public static final String SYNC_VIRTUAL_EXTRAS_EXEMPTION_FLAG = "v_exemption";
    private static final String TAG = "ContentResolver";
    @UnsupportedAppUsage
    private static volatile IContentService sContentService;
    @UnsupportedAppUsage
    private final Context mContext;
    @UnsupportedAppUsage
    final String mPackageName;
    private final Random mRandom = new Random();
    final int mTargetSdkVersion;
    final ContentInterface mWrapped;

    static {
        DEPRECATE_DATA_COLUMNS = StorageManager.hasIsolatedStorage();
        ACTION_SYNC_CONN_STATUS_CHANGED = new Intent("com.android.sync.SYNC_CONN_STATUS_CHANGED");
        SYNC_ERROR_NAMES = new String[]{"already-in-progress", "authentication-error", "io-error", "parse-error", "conflict", "too-many-deletions", "too-many-retries", "internal-error"};
    }

    public ContentResolver(Context context) {
        this(context, null);
    }

    public ContentResolver(Context context, ContentInterface contentInterface) {
        if (context == null) {
            context = ActivityThread.currentApplication();
        }
        this.mContext = context;
        this.mPackageName = this.mContext.getOpPackageName();
        this.mTargetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion;
        this.mWrapped = contentInterface;
    }

    public static void addPeriodicSync(Account account, String string2, Bundle bundle, long l) {
        ContentResolver.validateSyncExtrasBundle(bundle);
        if (!ContentResolver.invalidPeriodicExtras(bundle)) {
            try {
                ContentResolver.getContentService().addPeriodicSync(account, string2, bundle, l);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("illegal extras were set");
    }

    public static Object addStatusChangeListener(int n, SyncStatusObserver syncStatusObserver) {
        if (syncStatusObserver != null) {
            try {
                ISyncStatusObserver.Stub stub = new ISyncStatusObserver.Stub(){

                    @Override
                    public void onStatusChanged(int n) throws RemoteException {
                        SyncStatusObserver.this.onStatusChanged(n);
                    }
                };
                ContentResolver.getContentService().addStatusChangeListener(n, stub);
                return stub;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("you passed in a null callback");
    }

    public static void cancelSync(Account account, String string2) {
        try {
            ContentResolver.getContentService().cancelSync(account, string2, null);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void cancelSync(SyncRequest syncRequest) {
        if (syncRequest != null) {
            try {
                ContentResolver.getContentService().cancelRequest(syncRequest);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("request cannot be null");
    }

    public static void cancelSyncAsUser(Account account, String string2, int n) {
        try {
            ContentResolver.getContentService().cancelSyncAsUser(account, string2, null, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static Bundle createSqlQueryBundle(String string2, String[] arrstring, String string3) {
        if (string2 == null && arrstring == null && string3 == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        if (string2 != null) {
            bundle.putString(QUERY_ARG_SQL_SELECTION, string2);
        }
        if (arrstring != null) {
            bundle.putStringArray(QUERY_ARG_SQL_SELECTION_ARGS, arrstring);
        }
        if (string3 != null) {
            bundle.putString(QUERY_ARG_SQL_SORT_ORDER, string3);
        }
        return bundle;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String createSqlSortClause(Bundle object) {
        Object object2;
        int n;
        block5 : {
            String string2;
            block4 : {
                object2 = ((BaseBundle)object).getStringArray(QUERY_ARG_SORT_COLUMNS);
                if (object2 == null) throw new IllegalArgumentException("Can't create sort clause without columns.");
                if (((String[])object2).length == 0) throw new IllegalArgumentException("Can't create sort clause without columns.");
                string2 = TextUtils.join((CharSequence)", ", (Object[])object2);
                n = ((BaseBundle)object).getInt(QUERY_ARG_SORT_COLLATION, 3);
                if (n == 0) break block4;
                object2 = string2;
                if (n != 1) break block5;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" COLLATE NOCASE");
            object2 = ((StringBuilder)object2).toString();
        }
        n = ((BaseBundle)object).getInt(QUERY_ARG_SORT_DIRECTION, Integer.MIN_VALUE);
        object = object2;
        if (n == Integer.MIN_VALUE) return object;
        if (n != 0) {
            if (n != 1) throw new IllegalArgumentException("Unsupported sort direction value. See ContentResolver documentation for details.");
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append(" DESC");
            return ((StringBuilder)object).toString();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append((String)object2);
        ((StringBuilder)object).append(" ASC");
        return ((StringBuilder)object).toString();
    }

    @UnsupportedAppUsage
    public static IContentService getContentService() {
        if (sContentService != null) {
            return sContentService;
        }
        sContentService = IContentService.Stub.asInterface(ServiceManager.getService("content"));
        return sContentService;
    }

    @Deprecated
    public static SyncInfo getCurrentSync() {
        Object object;
        block3 : {
            try {
                object = ContentResolver.getContentService().getCurrentSyncs();
                if (!object.isEmpty()) break block3;
                return null;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        object = object.get(0);
        return object;
    }

    public static List<SyncInfo> getCurrentSyncs() {
        try {
            List<SyncInfo> list = ContentResolver.getContentService().getCurrentSyncs();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static List<SyncInfo> getCurrentSyncsAsUser(int n) {
        try {
            List<SyncInfo> list = ContentResolver.getContentService().getCurrentSyncsAsUser(n);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncable(Account account, String string2) {
        try {
            int n = ContentResolver.getContentService().getIsSyncable(account, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static int getIsSyncableAsUser(Account account, String string2, int n) {
        try {
            n = ContentResolver.getContentService().getIsSyncableAsUser(account, string2, n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomatically() {
        try {
            boolean bl = ContentResolver.getContentService().getMasterSyncAutomatically();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean getMasterSyncAutomaticallyAsUser(int n) {
        try {
            boolean bl = ContentResolver.getContentService().getMasterSyncAutomaticallyAsUser(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static List<PeriodicSync> getPeriodicSyncs(Account object, String string2) {
        try {
            object = ContentResolver.getContentService().getPeriodicSyncs((Account)object, string2, null);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static String[] getSyncAdapterPackagesForAuthorityAsUser(String arrstring, int n) {
        try {
            arrstring = ContentResolver.getContentService().getSyncAdapterPackagesForAuthorityAsUser((String)arrstring, n);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static SyncAdapterType[] getSyncAdapterTypes() {
        try {
            SyncAdapterType[] arrsyncAdapterType = ContentResolver.getContentService().getSyncAdapterTypes();
            return arrsyncAdapterType;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static SyncAdapterType[] getSyncAdapterTypesAsUser(int n) {
        try {
            SyncAdapterType[] arrsyncAdapterType = ContentResolver.getContentService().getSyncAdapterTypesAsUser(n);
            return arrsyncAdapterType;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomatically(Account account, String string2) {
        try {
            boolean bl = ContentResolver.getContentService().getSyncAutomatically(account, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean getSyncAutomaticallyAsUser(Account account, String string2, int n) {
        try {
            boolean bl = ContentResolver.getContentService().getSyncAutomaticallyAsUser(account, string2, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static SyncStatusInfo getSyncStatus(Account parcelable, String string2) {
        try {
            parcelable = ContentResolver.getContentService().getSyncStatus((Account)parcelable, string2, null);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public static SyncStatusInfo getSyncStatusAsUser(Account parcelable, String string2, int n) {
        try {
            parcelable = ContentResolver.getContentService().getSyncStatusAsUser((Account)parcelable, string2, null, n);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static boolean invalidPeriodicExtras(Bundle bundle) {
        return bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_DO_NOT_RETRY, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_BACKOFF, false) || bundle.getBoolean(SYNC_EXTRAS_IGNORE_SETTINGS, false) || bundle.getBoolean(SYNC_EXTRAS_INITIALIZE, false) || bundle.getBoolean("force", false) || bundle.getBoolean(SYNC_EXTRAS_EXPEDITED, false);
        {
        }
    }

    public static boolean isSyncActive(Account account, String string2) {
        if (account != null) {
            if (string2 != null) {
                try {
                    boolean bl = ContentResolver.getContentService().isSyncActive(account, string2, null);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("authority must not be null");
        }
        throw new IllegalArgumentException("account must not be null");
    }

    public static boolean isSyncPending(Account account, String string2) {
        return ContentResolver.isSyncPendingAsUser(account, string2, UserHandle.myUserId());
    }

    public static boolean isSyncPendingAsUser(Account account, String string2, int n) {
        try {
            boolean bl = ContentResolver.getContentService().isSyncPendingAsUser(account, string2, null, n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    static /* synthetic */ AssetFileDescriptor lambda$loadThumbnail$0(ContentInterface object, Uri parcelable, Bundle bundle, CancellationSignal cancellationSignal, Int32Ref int32Ref) throws Exception {
        parcelable = object.openTypedAssetFile((Uri)parcelable, "image/*", bundle, cancellationSignal);
        object = ((AssetFileDescriptor)parcelable).getExtras();
        int n = 0;
        if (object != null) {
            n = ((BaseBundle)object).getInt("android.provider.extra.ORIENTATION", 0);
        }
        int32Ref.value = n;
        return parcelable;
    }

    static /* synthetic */ void lambda$loadThumbnail$1(int n, CancellationSignal cancellationSignal, Size size, ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        imageDecoder.setAllocator(n);
        if (cancellationSignal != null) {
            cancellationSignal.throwIfCanceled();
        }
        if ((n = Math.min(imageInfo.getSize().getWidth() / size.getWidth(), imageInfo.getSize().getHeight() / size.getHeight())) > 1) {
            imageDecoder.setTargetSampleSize(n);
        }
    }

    public static Bitmap loadThumbnail(ContentInterface object, Uri parcelable, Size size, CancellationSignal cancellationSignal, int n) throws IOException {
        Objects.requireNonNull(object);
        Objects.requireNonNull(parcelable);
        Objects.requireNonNull(size);
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_SIZE, Point.convert(size));
        Int32Ref int32Ref = new Int32Ref(0);
        parcelable = ImageDecoder.decodeBitmap(ImageDecoder.createSource(new _$$Lambda$ContentResolver$7ILY1SWNxC2xhk_fQUG6tAXW9Ik((ContentInterface)object, (Uri)parcelable, bundle, cancellationSignal, int32Ref)), new _$$Lambda$ContentResolver$RVw7W0M7r0cGmbYi8rAG5GKxq4M(n, cancellationSignal, size));
        object = parcelable;
        if (int32Ref.value != 0) {
            n = ((Bitmap)parcelable).getWidth();
            int n2 = ((Bitmap)parcelable).getHeight();
            object = new Matrix();
            ((Matrix)object).setRotate(int32Ref.value, n / 2, n2 / 2);
            object = Bitmap.createBitmap((Bitmap)parcelable, 0, 0, n, n2, (Matrix)object, false);
        }
        return object;
    }

    private void maybeLogQueryToEventLog(long l, Uri uri, String[] arrstring, Bundle bundle) {
    }

    private void maybeLogUpdateToEventLog(long l, Uri uri, String string2, String string3) {
    }

    public static void onDbCorruption(String string2, String string3, Throwable throwable) {
        try {
            ContentResolver.getContentService().onDbCorruption(string2, string3, Log.getStackTraceString(throwable));
        }
        catch (RemoteException remoteException) {
            remoteException.rethrowFromSystemServer();
        }
    }

    public static void removePeriodicSync(Account account, String string2, Bundle bundle) {
        ContentResolver.validateSyncExtrasBundle(bundle);
        try {
            ContentResolver.getContentService().removePeriodicSync(account, string2, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void removeStatusChangeListener(Object object) {
        if (object != null) {
            try {
                ContentResolver.getContentService().removeStatusChangeListener((ISyncStatusObserver.Stub)object);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("you passed in a null handle");
    }

    public static void requestSync(Account account, String string2, Bundle bundle) {
        ContentResolver.requestSyncAsUser(account, string2, UserHandle.myUserId(), bundle);
    }

    public static void requestSync(SyncRequest syncRequest) {
        try {
            ContentResolver.getContentService().sync(syncRequest, ActivityThread.currentPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void requestSyncAsUser(Account parcelable, String string2, int n, Bundle bundle) {
        if (bundle != null) {
            parcelable = new SyncRequest.Builder().setSyncAdapter((Account)parcelable, string2).setExtras(bundle).syncOnce().build();
            try {
                ContentResolver.getContentService().syncAsUser((SyncRequest)parcelable, n, ActivityThread.currentPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("Must specify extras.");
    }

    private int samplePercentForDuration(long l) {
        if (l >= 500L) {
            return 100;
        }
        return (int)(100L * l / 500L) + 1;
    }

    public static void setIsSyncable(Account account, String string2, int n) {
        try {
            ContentResolver.getContentService().setIsSyncable(account, string2, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void setIsSyncableAsUser(Account account, String string2, int n, int n2) {
        try {
            ContentResolver.getContentService().setIsSyncableAsUser(account, string2, n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void setMasterSyncAutomatically(boolean bl) {
        ContentResolver.setMasterSyncAutomaticallyAsUser(bl, UserHandle.myUserId());
    }

    public static void setMasterSyncAutomaticallyAsUser(boolean bl, int n) {
        try {
            ContentResolver.getContentService().setMasterSyncAutomaticallyAsUser(bl, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static void setSyncAutomatically(Account account, String string2, boolean bl) {
        ContentResolver.setSyncAutomaticallyAsUser(account, string2, bl, UserHandle.myUserId());
    }

    public static void setSyncAutomaticallyAsUser(Account account, String string2, boolean bl, int n) {
        try {
            ContentResolver.getContentService().setSyncAutomaticallyAsUser(account, string2, bl, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static int syncErrorStringToInt(String string2) {
        int n;
        int n2 = SYNC_ERROR_NAMES.length;
        for (n = 0; n < n2; ++n) {
            if (!SYNC_ERROR_NAMES[n].equals(string2)) continue;
            return n + 1;
        }
        if (string2 != null) {
            try {
                n = Integer.parseInt(string2);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("error parsing sync error: ");
                stringBuilder.append(string2);
                Log.d(TAG, stringBuilder.toString());
            }
        }
        return 0;
    }

    public static String syncErrorToString(int n) {
        String[] arrstring;
        if (n >= 1 && n <= (arrstring = SYNC_ERROR_NAMES).length) {
            return arrstring[n - 1];
        }
        return String.valueOf(n);
    }

    public static Uri translateDeprecatedDataPath(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("//");
        stringBuilder.append(string2.substring(DEPRECATE_DATA_PREFIX.length()));
        string2 = stringBuilder.toString();
        return Uri.parse(new Uri.Builder().scheme("content").encodedOpaquePart(string2).build().toString());
    }

    public static String translateDeprecatedDataPath(Uri uri) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DEPRECATE_DATA_PREFIX);
        stringBuilder.append(uri.getEncodedSchemeSpecificPart().substring(2));
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void validateSyncExtrasBundle(Bundle object) {
        try {
            Object object2;
            Object object3 = ((BaseBundle)object).keySet().iterator();
            do {
                if (object3.hasNext()) continue;
                return;
            } while ((object2 = ((BaseBundle)object).get(object3.next())) == null || object2 instanceof Long || object2 instanceof Integer || object2 instanceof Boolean || object2 instanceof Float || object2 instanceof Double || object2 instanceof String || object2 instanceof Account);
            object = new StringBuilder();
            ((StringBuilder)object).append("unexpected value type: ");
            ((StringBuilder)object).append(object2.getClass().getName());
            object3 = new IllegalArgumentException(((StringBuilder)object).toString());
            throw object3;
        }
        catch (RuntimeException runtimeException) {
            throw new IllegalArgumentException("error unparceling Bundle", runtimeException);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw illegalArgumentException;
        }
    }

    public static ContentResolver wrap(ContentInterface contentInterface) {
        Preconditions.checkNotNull(contentInterface);
        return new ContentResolver(null, contentInterface){

            @Override
            protected IContentProvider acquireProvider(Context context, String string2) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected IContentProvider acquireUnstableProvider(Context context, String string2) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean releaseProvider(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean releaseUnstableProvider(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void unstableProviderDied(IContentProvider iContentProvider) {
                throw new UnsupportedOperationException();
            }
        };
    }

    public static ContentResolver wrap(ContentProvider contentProvider) {
        return ContentResolver.wrap((ContentInterface)contentProvider);
    }

    public static ContentResolver wrap(ContentProviderClient contentProviderClient) {
        return ContentResolver.wrap((ContentInterface)contentProviderClient);
    }

    public final ContentProviderClient acquireContentProviderClient(Uri uri) {
        Preconditions.checkNotNull(uri, "uri");
        IContentProvider iContentProvider = this.acquireProvider(uri);
        if (iContentProvider != null) {
            return new ContentProviderClient(this, iContentProvider, uri.getAuthority(), true);
        }
        return null;
    }

    public final ContentProviderClient acquireContentProviderClient(String string2) {
        Preconditions.checkNotNull(string2, "name");
        IContentProvider iContentProvider = this.acquireProvider(string2);
        if (iContentProvider != null) {
            return new ContentProviderClient(this, iContentProvider, string2, true);
        }
        return null;
    }

    @UnsupportedAppUsage
    protected IContentProvider acquireExistingProvider(Context context, String string2) {
        return this.acquireProvider(context, string2);
    }

    @UnsupportedAppUsage
    public final IContentProvider acquireExistingProvider(Uri object) {
        if (!"content".equals(((Uri)object).getScheme())) {
            return null;
        }
        if ((object = ((Uri)object).getAuthority()) != null) {
            return this.acquireExistingProvider(this.mContext, (String)object);
        }
        return null;
    }

    @UnsupportedAppUsage
    protected abstract IContentProvider acquireProvider(Context var1, String var2);

    @UnsupportedAppUsage
    public final IContentProvider acquireProvider(Uri object) {
        if (!"content".equals(((Uri)object).getScheme())) {
            return null;
        }
        if ((object = ((Uri)object).getAuthority()) != null) {
            return this.acquireProvider(this.mContext, (String)object);
        }
        return null;
    }

    @UnsupportedAppUsage
    public final IContentProvider acquireProvider(String string2) {
        if (string2 == null) {
            return null;
        }
        return this.acquireProvider(this.mContext, string2);
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(Uri uri) {
        Preconditions.checkNotNull(uri, "uri");
        IContentProvider iContentProvider = this.acquireUnstableProvider(uri);
        if (iContentProvider != null) {
            return new ContentProviderClient(this, iContentProvider, uri.getAuthority(), false);
        }
        return null;
    }

    public final ContentProviderClient acquireUnstableContentProviderClient(String string2) {
        Preconditions.checkNotNull(string2, "name");
        IContentProvider iContentProvider = this.acquireUnstableProvider(string2);
        if (iContentProvider != null) {
            return new ContentProviderClient(this, iContentProvider, string2, false);
        }
        return null;
    }

    @UnsupportedAppUsage
    protected abstract IContentProvider acquireUnstableProvider(Context var1, String var2);

    public final IContentProvider acquireUnstableProvider(Uri uri) {
        if (!"content".equals(uri.getScheme())) {
            return null;
        }
        if (uri.getAuthority() != null) {
            return this.acquireUnstableProvider(this.mContext, uri.getAuthority());
        }
        return null;
    }

    @UnsupportedAppUsage
    public final IContentProvider acquireUnstableProvider(String string2) {
        if (string2 == null) {
            return null;
        }
        return this.acquireUnstableProvider(this.mContext, string2);
    }

    public void appNotRespondingViaProvider(IContentProvider iContentProvider) {
        throw new UnsupportedOperationException("appNotRespondingViaProvider");
    }

    @Override
    public ContentProviderResult[] applyBatch(String arrcontentProviderResult, ArrayList<ContentProviderOperation> serializable) throws RemoteException, OperationApplicationException {
        block6 : {
            ContentProviderClient contentProviderClient;
            Preconditions.checkNotNull(arrcontentProviderResult, "authority");
            Preconditions.checkNotNull(serializable, "operations");
            try {
                if (this.mWrapped != null) {
                    arrcontentProviderResult = this.mWrapped.applyBatch((String)arrcontentProviderResult, (ArrayList<ContentProviderOperation>)serializable);
                    return arrcontentProviderResult;
                }
                contentProviderClient = this.acquireContentProviderClient((String)arrcontentProviderResult);
                if (contentProviderClient == null) break block6;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            try {
                arrcontentProviderResult = contentProviderClient.applyBatch((ArrayList<ContentProviderOperation>)serializable);
                return arrcontentProviderResult;
            }
            finally {
                contentProviderClient.release();
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unknown authority ");
        ((StringBuilder)serializable).append((String)arrcontentProviderResult);
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    @Override
    public final int bulkInsert(@RequiresPermission.Write Uri uri, ContentValues[] object) {
        block7 : {
            IContentProvider iContentProvider;
            Preconditions.checkNotNull(uri, "url");
            Preconditions.checkNotNull(object, "values");
            try {
                if (this.mWrapped != null) {
                    int n = this.mWrapped.bulkInsert(uri, (ContentValues[])object);
                    return n;
                }
                iContentProvider = this.acquireProvider(uri);
                if (iContentProvider == null) break block7;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
            try {
                long l = SystemClock.uptimeMillis();
                int n = iContentProvider.bulkInsert(this.mPackageName, uri, (ContentValues[])object);
                this.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - l, uri, "bulkinsert", null);
                return n;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
            finally {
                this.releaseProvider(iContentProvider);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown URL ");
        ((StringBuilder)object).append(uri);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public final Bundle call(Uri uri, String string2, String string3, Bundle bundle) {
        return this.call(uri.getAuthority(), string2, string3, bundle);
    }

    @Override
    public final Bundle call(String object, String charSequence, String string2, Bundle bundle) {
        block7 : {
            IContentProvider iContentProvider;
            Preconditions.checkNotNull(object, "authority");
            Preconditions.checkNotNull(charSequence, "method");
            try {
                if (this.mWrapped != null) {
                    object = this.mWrapped.call((String)object, (String)charSequence, string2, bundle);
                    return object;
                }
                iContentProvider = this.acquireProvider((String)object);
                if (iContentProvider == null) break block7;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            try {
                object = iContentProvider.call(this.mPackageName, (String)object, (String)charSequence, string2, bundle);
                Bundle.setDefusable((Bundle)object, true);
                return object;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            finally {
                this.releaseProvider(iContentProvider);
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unknown authority ");
        ((StringBuilder)charSequence).append((String)object);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @Deprecated
    public void cancelSync(Uri object) {
        object = object != null ? ((Uri)object).getAuthority() : null;
        ContentResolver.cancelSync(null, (String)object);
    }

    @Override
    public final Uri canonicalize(Uri uri) {
        IContentProvider iContentProvider;
        block7 : {
            Preconditions.checkNotNull(uri, "url");
            try {
                if (this.mWrapped != null) {
                    uri = this.mWrapped.canonicalize(uri);
                    return uri;
                }
                iContentProvider = this.acquireProvider(uri);
                if (iContentProvider != null) break block7;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        try {
            uri = iContentProvider.canonicalize(this.mPackageName, uri);
            return uri;
        }
        catch (RemoteException remoteException) {
            return null;
        }
        finally {
            this.releaseProvider(iContentProvider);
        }
    }

    public final Uri canonicalizeOrElse(Uri uri) {
        block0 : {
            Uri uri2 = this.canonicalize(uri);
            if (uri2 == null) break block0;
            uri = uri2;
        }
        return uri;
    }

    @Override
    public final int delete(@RequiresPermission.Write Uri uri, String charSequence, String[] arrstring) {
        block7 : {
            IContentProvider iContentProvider;
            Preconditions.checkNotNull(uri, "url");
            try {
                if (this.mWrapped != null) {
                    int n = this.mWrapped.delete(uri, (String)charSequence, arrstring);
                    return n;
                }
                iContentProvider = this.acquireProvider(uri);
                if (iContentProvider == null) break block7;
            }
            catch (RemoteException remoteException) {
                return 0;
            }
            try {
                long l = SystemClock.uptimeMillis();
                int n = iContentProvider.delete(this.mPackageName, uri, (String)charSequence, arrstring);
                this.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - l, uri, "delete", (String)charSequence);
                return n;
            }
            catch (RemoteException remoteException) {
                return -1;
            }
            finally {
                this.releaseProvider(iContentProvider);
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unknown URL ");
        ((StringBuilder)charSequence).append(uri);
        throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
    }

    @SystemApi
    public Bundle getCache(Uri parcelable) {
        block3 : {
            try {
                parcelable = ContentResolver.getContentService().getCache(this.mContext.getPackageName(), (Uri)parcelable, this.mContext.getUserId());
                if (parcelable == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            ((Bundle)parcelable).setClassLoader(this.mContext.getClassLoader());
        }
        return parcelable;
    }

    public List<UriPermission> getOutgoingPersistedUriPermissions() {
        try {
            List list = UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, true).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<UriPermission> getOutgoingUriPermissions() {
        try {
            List list = UriGrantsManager.getService().getUriPermissions(this.mPackageName, false, false).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public String getPackageName() {
        return this.mPackageName;
    }

    public List<UriPermission> getPersistedUriPermissions() {
        try {
            List list = UriGrantsManager.getService().getUriPermissions(this.mPackageName, true, true).getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public OpenResourceIdResult getResourceId(Uri object) throws FileNotFoundException {
        block10 : {
            Object object2;
            block8 : {
                block11 : {
                    String string2;
                    List<String> list;
                    int n;
                    block9 : {
                        string2 = ((Uri)object).getAuthority();
                        if (TextUtils.isEmpty(string2)) break block10;
                        try {
                            object2 = this.mContext.getPackageManager().getResourcesForApplication(string2);
                            list = ((Uri)object).getPathSegments();
                            if (list == null) break block8;
                            n = list.size();
                            if (n != 1) break block9;
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("No package found for authority: ");
                            stringBuilder.append(object);
                            throw new FileNotFoundException(stringBuilder.toString());
                        }
                        try {
                            n = Integer.parseInt(list.get(0));
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Single path segment is not a resource ID: ");
                            stringBuilder.append(object);
                            throw new FileNotFoundException(stringBuilder.toString());
                        }
                    }
                    if (n != 2) break block11;
                    n = ((Resources)object2).getIdentifier(list.get(1), list.get(0), string2);
                    if (n != 0) {
                        object = new OpenResourceIdResult();
                        ((OpenResourceIdResult)object).r = object2;
                        ((OpenResourceIdResult)object).id = n;
                        return object;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("No resource found for: ");
                    ((StringBuilder)object2).append(object);
                    throw new FileNotFoundException(((StringBuilder)object2).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("More than two path segments: ");
                ((StringBuilder)object2).append(object);
                throw new FileNotFoundException(((StringBuilder)object2).toString());
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No path: ");
            ((StringBuilder)object2).append(object);
            throw new FileNotFoundException(((StringBuilder)object2).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No authority: ");
        stringBuilder.append(object);
        throw new FileNotFoundException(stringBuilder.toString());
    }

    @Override
    public String[] getStreamTypes(Uri arrstring, String string2) {
        IContentProvider iContentProvider;
        block7 : {
            Preconditions.checkNotNull(arrstring, "url");
            Preconditions.checkNotNull(string2, "mimeTypeFilter");
            try {
                if (this.mWrapped != null) {
                    arrstring = this.mWrapped.getStreamTypes((Uri)arrstring, string2);
                    return arrstring;
                }
                iContentProvider = this.acquireProvider((Uri)arrstring);
                if (iContentProvider != null) break block7;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        try {
            arrstring = iContentProvider.getStreamTypes((Uri)arrstring, string2);
            return arrstring;
        }
        catch (RemoteException remoteException) {
            return null;
        }
        finally {
            this.releaseProvider(iContentProvider);
        }
    }

    public int getTargetSdkVersion() {
        return this.mTargetSdkVersion;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final String getType(Uri object) {
        Throwable throwable2222;
        Object object2;
        block12 : {
            Preconditions.checkNotNull(object, "url");
            try {
                if (this.mWrapped != null) {
                    return this.mWrapped.getType((Uri)object);
                }
                object2 = this.acquireExistingProvider((Uri)object);
                if (object2 == null) break block12;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            String string2 = object2.getType((Uri)object);
            this.releaseProvider((IContentProvider)object2);
            return string2;
        }
        if (!"content".equals(((Uri)object).getScheme())) {
            return null;
        }
        try {
            return ActivityManager.getService().getProviderMimeType(ContentProvider.getUriWithoutUserId((Uri)object), this.resolveUserId((Uri)object));
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Failed to get type for: ");
            ((StringBuilder)object2).append(object);
            ((StringBuilder)object2).append(" (");
            ((StringBuilder)object2).append(exception.getMessage());
            ((StringBuilder)object2).append(")");
            Log.w("ContentResolver", ((StringBuilder)object2).toString());
            return null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to get type for: ");
                stringBuilder.append(object);
                stringBuilder.append(" (");
                stringBuilder.append(exception.getMessage());
                stringBuilder.append(")");
                Log.w("ContentResolver", stringBuilder.toString());
                this.releaseProvider((IContentProvider)object2);
            }
            return null;
        }
        this.releaseProvider((IContentProvider)object2);
        throw throwable2222;
        catch (RemoteException remoteException) {
            this.releaseProvider((IContentProvider)object2);
            return null;
        }
    }

    @Deprecated
    public Drawable getTypeDrawable(String string2) {
        return this.getTypeInfo(string2).getIcon().loadDrawable(this.mContext);
    }

    public final MimeTypeInfo getTypeInfo(String string2) {
        Objects.requireNonNull(string2);
        return MimeIconUtils.getTypeInfo(string2);
    }

    public int getUserId() {
        return this.mContext.getUserId();
    }

    @Override
    public final Uri insert(@RequiresPermission.Write Uri uri, ContentValues object) {
        block7 : {
            IContentProvider iContentProvider;
            Preconditions.checkNotNull(uri, "url");
            try {
                if (this.mWrapped != null) {
                    uri = this.mWrapped.insert(uri, (ContentValues)object);
                    return uri;
                }
                iContentProvider = this.acquireProvider(uri);
                if (iContentProvider == null) break block7;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            try {
                long l = SystemClock.uptimeMillis();
                object = iContentProvider.insert(this.mPackageName, uri, (ContentValues)object);
                this.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - l, uri, "insert", null);
                return object;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            finally {
                this.releaseProvider(iContentProvider);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown URL ");
        ((StringBuilder)object).append(uri);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public Bitmap loadThumbnail(Uri uri, Size size, CancellationSignal cancellationSignal) throws IOException {
        return ContentResolver.loadThumbnail(this, uri, size, cancellationSignal, 1);
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver) {
        this.notifyChange(uri, contentObserver, true);
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, int n) {
        Preconditions.checkNotNull(uri, "uri");
        this.notifyChange(ContentProvider.getUriWithoutUserId(uri), contentObserver, n, ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, int n, int n2) {
        boolean bl;
        IContentObserver iContentObserver;
        IContentService iContentService;
        block9 : {
            block8 : {
                block7 : {
                    block6 : {
                        try {
                            iContentService = ContentResolver.getContentService();
                            if (contentObserver != null) break block6;
                            iContentObserver = null;
                            break block7;
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    iContentObserver = contentObserver.getContentObserver();
                }
                if (contentObserver != null) {
                    if (!contentObserver.deliverSelfNotifications()) break block8;
                    bl = true;
                    break block9;
                }
            }
            bl = false;
        }
        iContentService.notifyChange(uri, iContentObserver, bl, n, n2, this.mTargetSdkVersion, this.mContext.getPackageName());
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, boolean bl) {
        Preconditions.checkNotNull(uri, "uri");
        this.notifyChange(ContentProvider.getUriWithoutUserId(uri), contentObserver, bl, ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    public void notifyChange(Uri uri, ContentObserver contentObserver, boolean bl, int n) {
        boolean bl2;
        IContentObserver iContentObserver;
        IContentService iContentService;
        block9 : {
            block8 : {
                block7 : {
                    block6 : {
                        try {
                            iContentService = ContentResolver.getContentService();
                            if (contentObserver != null) break block6;
                            iContentObserver = null;
                            break block7;
                        }
                        catch (RemoteException remoteException) {
                            throw remoteException.rethrowFromSystemServer();
                        }
                    }
                    iContentObserver = contentObserver.getContentObserver();
                }
                if (contentObserver != null) {
                    if (!contentObserver.deliverSelfNotifications()) break block8;
                    bl2 = true;
                    break block9;
                }
            }
            bl2 = false;
        }
        int n2 = bl ? 1 : 0;
        iContentService.notifyChange(uri, iContentObserver, bl2, n2, n, this.mTargetSdkVersion, this.mContext.getPackageName());
    }

    @Override
    public final AssetFileDescriptor openAssetFile(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            if (this.mWrapped != null) {
                parcelable = this.mWrapped.openAssetFile((Uri)parcelable, string2, cancellationSignal);
                return parcelable;
            }
            return this.openAssetFileDescriptor((Uri)parcelable, string2, cancellationSignal);
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public final AssetFileDescriptor openAssetFileDescriptor(Uri uri, String string2) throws FileNotFoundException {
        return this.openAssetFileDescriptor(uri, string2, null);
    }

    /*
     * Exception decompiling
     */
    public final AssetFileDescriptor openAssetFileDescriptor(Uri var1_1, String var2_5, CancellationSignal var3_10) throws FileNotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 12[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public final ParcelFileDescriptor openFile(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            if (this.mWrapped != null) {
                parcelable = this.mWrapped.openFile((Uri)parcelable, string2, cancellationSignal);
                return parcelable;
            }
            return this.openFileDescriptor((Uri)parcelable, string2, cancellationSignal);
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public final ParcelFileDescriptor openFileDescriptor(Uri uri, String string2) throws FileNotFoundException {
        return this.openFileDescriptor(uri, string2, null);
    }

    public final ParcelFileDescriptor openFileDescriptor(Uri parcelable, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        block6 : {
            try {
                if (this.mWrapped != null) {
                    parcelable = this.mWrapped.openFile((Uri)parcelable, string2, cancellationSignal);
                    return parcelable;
                }
                if ((parcelable = this.openAssetFileDescriptor((Uri)parcelable, string2, cancellationSignal)) == null) {
                    return null;
                }
                if (((AssetFileDescriptor)parcelable).getDeclaredLength() >= 0L) break block6;
                return ((AssetFileDescriptor)parcelable).getParcelFileDescriptor();
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        try {
            ((AssetFileDescriptor)parcelable).close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        throw new FileNotFoundException("Not a whole file");
    }

    public final InputStream openInputStream(Uri object) throws FileNotFoundException {
        Preconditions.checkNotNull(object, "uri");
        Object object2 = ((Uri)object).getScheme();
        if ("android.resource".equals(object2)) {
            object2 = this.getResourceId((Uri)object);
            try {
                object2 = ((OpenResourceIdResult)object2).r.openRawResource(((OpenResourceIdResult)object2).id);
                return object2;
            }
            catch (Resources.NotFoundException notFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Resource does not exist: ");
                stringBuilder.append(object);
                throw new FileNotFoundException(stringBuilder.toString());
            }
        }
        if ("file".equals(object2)) {
            return new FileInputStream(((Uri)object).getPath());
        }
        object2 = null;
        AssetFileDescriptor assetFileDescriptor = this.openAssetFileDescriptor((Uri)object, "r", null);
        object = object2;
        if (assetFileDescriptor != null) {
            try {
                object = assetFileDescriptor.createInputStream();
            }
            catch (IOException iOException) {
                throw new FileNotFoundException("Unable to create stream");
            }
        }
        return object;
    }

    public final OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        return this.openOutputStream(uri, "w");
    }

    public final OutputStream openOutputStream(Uri object, String object2) throws FileNotFoundException {
        Object var3_4 = null;
        object2 = this.openAssetFileDescriptor((Uri)object, (String)object2, null);
        object = var3_4;
        if (object2 != null) {
            try {
                object = ((AssetFileDescriptor)object2).createOutputStream();
            }
            catch (IOException iOException) {
                throw new FileNotFoundException("Unable to create stream");
            }
        }
        return object;
    }

    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri parcelable, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        try {
            if (this.mWrapped != null) {
                parcelable = this.mWrapped.openTypedAssetFile((Uri)parcelable, string2, bundle, cancellationSignal);
                return parcelable;
            }
            return this.openTypedAssetFileDescriptor((Uri)parcelable, string2, bundle, cancellationSignal);
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String string2, Bundle bundle) throws FileNotFoundException {
        return this.openTypedAssetFileDescriptor(uri, string2, bundle, null);
    }

    /*
     * Exception decompiling
     */
    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri var1_1, String var2_6, Bundle var3_9, CancellationSignal var4_10) throws FileNotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 12[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @SystemApi
    public void putCache(Uri uri, Bundle bundle) {
        try {
            ContentResolver.getContentService().putCache(this.mContext.getPackageName(), uri, bundle, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final Cursor query(@RequiresPermission.Read Uri object, String[] arrstring, Bundle bundle, CancellationSignal cancellationSignal) {
        IContentProvider iContentProvider;
        block28 : {
            Object object3;
            Object object4;
            long l;
            Object object2;
            IContentProvider iContentProvider2;
            IContentProvider iContentProvider4;
            IContentProvider iContentProvider3;
            block27 : {
                block26 : {
                    Preconditions.checkNotNull(object, "uri");
                    if (this.mWrapped == null) break block26;
                    ContentInterface contentInterface = this.mWrapped;
                    try {
                        return contentInterface.query((Uri)object, arrstring, bundle, cancellationSignal);
                    }
                    catch (RemoteException remoteException) {
                        return null;
                    }
                }
                iContentProvider = this.acquireUnstableProvider((Uri)object);
                if (iContentProvider == null) {
                    return null;
                }
                iContentProvider2 = null;
                IContentProvider iContentProvider5 = null;
                IContentProvider iContentProvider6 = null;
                Cursor cursor = null;
                Cursor cursor2 = null;
                iContentProvider3 = iContentProvider2;
                object4 = cursor2;
                iContentProvider4 = iContentProvider5;
                object2 = cursor;
                l = SystemClock.uptimeMillis();
                if (cancellationSignal != null) {
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider5;
                    object2 = cursor;
                    cancellationSignal.throwIfCanceled();
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider5;
                    object2 = cursor;
                    object3 = iContentProvider.createCancellationSignal();
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider5;
                    object2 = cursor;
                    cancellationSignal.setRemote((ICancellationSignal)object3);
                } else {
                    object3 = null;
                }
                iContentProvider3 = iContentProvider2;
                object4 = cursor2;
                iContentProvider4 = iContentProvider5;
                object2 = cursor;
                try {
                    Cursor cursor3 = iContentProvider.query(this.mPackageName, (Uri)object, arrstring, bundle, (ICancellationSignal)object3);
                    object3 = cursor3;
                    iContentProvider2 = iContentProvider6;
                }
                catch (DeadObjectException deadObjectException) {
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider5;
                    object2 = cursor;
                    this.unstableProviderDied(iContentProvider);
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider5;
                    object2 = cursor;
                    iContentProvider2 = this.acquireProvider((Uri)object);
                    if (iContentProvider2 == null) {
                        if (false) {
                            throw new NullPointerException();
                        }
                        if (cancellationSignal != null) {
                            cancellationSignal.setRemote(null);
                        }
                        this.releaseUnstableProvider(iContentProvider);
                        if (iContentProvider2 == null) return null;
                        this.releaseProvider(iContentProvider2);
                        return null;
                    }
                    iContentProvider3 = iContentProvider2;
                    object4 = cursor2;
                    iContentProvider4 = iContentProvider2;
                    object2 = cursor;
                    object3 = iContentProvider2.query(this.mPackageName, (Uri)object, arrstring, bundle, (ICancellationSignal)object3);
                }
                if (object3 != null) break block27;
                if (object3 != null) {
                    object3.close();
                }
                if (cancellationSignal != null) {
                    cancellationSignal.setRemote(null);
                }
                this.releaseUnstableProvider(iContentProvider);
                if (iContentProvider2 == null) return null;
                this.releaseProvider(iContentProvider2);
                return null;
            }
            iContentProvider3 = iContentProvider2;
            object4 = object3;
            iContentProvider4 = iContentProvider2;
            object2 = object3;
            try {
                object3.getCount();
                iContentProvider3 = iContentProvider2;
                object4 = object3;
                iContentProvider4 = iContentProvider2;
                object2 = object3;
                this.maybeLogQueryToEventLog(SystemClock.uptimeMillis() - l, (Uri)object, arrstring, bundle);
                if (iContentProvider2 != null) {
                    object = iContentProvider2;
                } else {
                    iContentProvider3 = iContentProvider2;
                    object4 = object3;
                    iContentProvider4 = iContentProvider2;
                    object2 = object3;
                    object = this.acquireProvider((Uri)object);
                }
                iContentProvider3 = iContentProvider2;
                object4 = object3;
                iContentProvider4 = iContentProvider2;
                object2 = object3;
                object = new CursorWrapperInner((Cursor)object3, (IContentProvider)object);
                if (!false) break block28;
            }
            catch (Throwable throwable) {
                if (object4 != null) {
                    object4.close();
                }
                if (cancellationSignal != null) {
                    cancellationSignal.setRemote(null);
                }
                this.releaseUnstableProvider(iContentProvider);
                if (iContentProvider3 == null) throw throwable;
                this.releaseProvider(iContentProvider3);
                throw throwable;
            }
            catch (RemoteException remoteException) {
                if (object2 != null) {
                    object2.close();
                }
                if (cancellationSignal != null) {
                    cancellationSignal.setRemote(null);
                }
                this.releaseUnstableProvider(iContentProvider);
                if (iContentProvider4 == null) return null;
                this.releaseProvider(iContentProvider4);
                return null;
            }
            throw new NullPointerException();
        }
        if (cancellationSignal != null) {
            cancellationSignal.setRemote(null);
        }
        this.releaseUnstableProvider(iContentProvider);
        if (!false) return object;
        this.releaseProvider(null);
        return object;
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return null;
    }

    public final Cursor query(@RequiresPermission.Read Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        return this.query(uri, arrstring, string2, arrstring2, string3, null);
    }

    public final Cursor query(@RequiresPermission.Read Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, CancellationSignal cancellationSignal) {
        return this.query(uri, arrstring, ContentResolver.createSqlQueryBundle(string2, arrstring2, string3), cancellationSignal);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final boolean refresh(Uri var1_1, Bundle var2_5, CancellationSignal var3_6) {
        Preconditions.checkNotNull(var1_1, "url");
        try {
            if (this.mWrapped != null) {
                return this.mWrapped.refresh(var1_1, var2_5, var3_6);
            }
            var5_9 = this.acquireProvider(var1_1);
            if (var5_9 == null) {
                return false;
            }
            var6_10 = null;
            if (var3_6 == null) ** GOTO lbl17
        }
        catch (RemoteException var1_4) {
            return false;
        }
        try {
            var3_6.throwIfCanceled();
            var6_10 = var5_9.createCancellationSignal();
            var3_6.setRemote(var6_10);
lbl17: // 2 sources:
            var4_8 = var5_9.refresh(this.mPackageName, var1_1, var2_5, var6_10);
            return var4_8;
        }
        catch (RemoteException var1_3) {
            return false;
        }
        finally {
            this.releaseProvider(var5_9);
        }
    }

    public final void registerContentObserver(Uri uri, boolean bl, ContentObserver contentObserver) {
        Preconditions.checkNotNull(uri, "uri");
        Preconditions.checkNotNull(contentObserver, "observer");
        this.registerContentObserver(ContentProvider.getUriWithoutUserId(uri), bl, contentObserver, ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId()));
    }

    @UnsupportedAppUsage
    public final void registerContentObserver(Uri uri, boolean bl, ContentObserver contentObserver, int n) {
        try {
            ContentResolver.getContentService().registerContentObserver(uri, bl, contentObserver.getContentObserver(), n, this.mTargetSdkVersion);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void releasePersistableUriPermission(Uri uri, int n) {
        Preconditions.checkNotNull(uri, "uri");
        try {
            UriGrantsManager.getService().releasePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), n, null, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public abstract boolean releaseProvider(IContentProvider var1);

    @UnsupportedAppUsage
    public abstract boolean releaseUnstableProvider(IContentProvider var1);

    public int resolveUserId(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, this.mContext.getUserId());
    }

    @Deprecated
    public void startSync(Uri object, Bundle bundle) {
        Account account = null;
        Account account2 = null;
        if (bundle != null) {
            String string2 = bundle.getString("account");
            account = account2;
            if (!TextUtils.isEmpty(string2)) {
                account = new Account(string2, "com.google");
            }
            bundle.remove("account");
        }
        object = object != null ? ((Uri)object).getAuthority() : null;
        ContentResolver.requestSync(account, (String)object, bundle);
    }

    public void takePersistableUriPermission(Uri uri, int n) {
        Preconditions.checkNotNull(uri, "uri");
        try {
            UriGrantsManager.getService().takePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), n, null, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public void takePersistableUriPermission(String string2, Uri uri, int n) {
        Preconditions.checkNotNull(string2, "toPackage");
        Preconditions.checkNotNull(uri, "uri");
        try {
            UriGrantsManager.getService().takePersistableUriPermission(ContentProvider.getUriWithoutUserId(uri), n, string2, this.resolveUserId(uri));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Override
    public final Uri uncanonicalize(Uri uri) {
        IContentProvider iContentProvider;
        block7 : {
            Preconditions.checkNotNull(uri, "url");
            try {
                if (this.mWrapped != null) {
                    uri = this.mWrapped.uncanonicalize(uri);
                    return uri;
                }
                iContentProvider = this.acquireProvider(uri);
                if (iContentProvider != null) break block7;
                return null;
            }
            catch (RemoteException remoteException) {
                return null;
            }
        }
        try {
            uri = iContentProvider.uncanonicalize(this.mPackageName, uri);
            return uri;
        }
        catch (RemoteException remoteException) {
            return null;
        }
        finally {
            this.releaseProvider(iContentProvider);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void unregisterContentObserver(ContentObserver object) {
        Preconditions.checkNotNull(object, "observer");
        try {
            object = ((ContentObserver)object).releaseContentObserver();
            if (object == null) return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        ContentResolver.getContentService().unregisterContentObserver((IContentObserver)object);
    }

    @UnsupportedAppUsage
    public abstract void unstableProviderDied(IContentProvider var1);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final int update(@RequiresPermission.Write Uri uri, ContentValues object, String string2, String[] arrstring) {
        block9 : {
            Preconditions.checkNotNull(uri, "uri");
            if (this.mWrapped == null) break block9;
            ContentInterface contentInterface = this.mWrapped;
            try {
                return contentInterface.update(uri, (ContentValues)object, string2, arrstring);
            }
            catch (RemoteException remoteException) {
                return 0;
            }
        }
        IContentProvider iContentProvider = this.acquireProvider(uri);
        if (iContentProvider == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown URI ");
            ((StringBuilder)object).append(uri);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        try {
            long l = SystemClock.uptimeMillis();
            int n = iContentProvider.update(this.mPackageName, uri, (ContentValues)object, string2, arrstring);
            this.maybeLogUpdateToEventLog(SystemClock.uptimeMillis() - l, uri, "update", string2);
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
        finally {
            this.releaseProvider(iContentProvider);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return 0;
    }

    private final class CursorWrapperInner
    extends CrossProcessCursorWrapper {
        private final CloseGuard mCloseGuard;
        private final IContentProvider mContentProvider;
        private final AtomicBoolean mProviderReleased;

        CursorWrapperInner(Cursor cursor, IContentProvider iContentProvider) {
            super(cursor);
            this.mProviderReleased = new AtomicBoolean();
            this.mCloseGuard = CloseGuard.get();
            this.mContentProvider = iContentProvider;
            this.mCloseGuard.open("close");
        }

        @Override
        public void close() {
            this.mCloseGuard.close();
            super.close();
            if (this.mProviderReleased.compareAndSet(false, true)) {
                ContentResolver.this.releaseProvider(this.mContentProvider);
            }
        }

        protected void finalize() throws Throwable {
            try {
                if (this.mCloseGuard != null) {
                    this.mCloseGuard.warnIfOpen();
                }
                this.close();
                return;
            }
            finally {
                Object.super.finalize();
            }
        }
    }

    public static final class MimeTypeInfo {
        private final CharSequence mContentDescription;
        private final Icon mIcon;
        private final CharSequence mLabel;

        public MimeTypeInfo(Icon icon, CharSequence charSequence, CharSequence charSequence2) {
            this.mIcon = Objects.requireNonNull(icon);
            this.mLabel = Objects.requireNonNull(charSequence);
            this.mContentDescription = Objects.requireNonNull(charSequence2);
        }

        public CharSequence getContentDescription() {
            return this.mContentDescription;
        }

        public Icon getIcon() {
            return this.mIcon;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NotifyFlags {
    }

    public class OpenResourceIdResult {
        @UnsupportedAppUsage
        public int id;
        @UnsupportedAppUsage
        public Resources r;
    }

    private final class ParcelFileDescriptorInner
    extends ParcelFileDescriptor {
        private final IContentProvider mContentProvider;
        private final AtomicBoolean mProviderReleased;

        ParcelFileDescriptorInner(ParcelFileDescriptor parcelFileDescriptor, IContentProvider iContentProvider) {
            super(parcelFileDescriptor);
            this.mProviderReleased = new AtomicBoolean();
            this.mContentProvider = iContentProvider;
        }

        @Override
        public void releaseResources() {
            if (this.mProviderReleased.compareAndSet(false, true)) {
                ContentResolver.this.releaseProvider(this.mContentProvider);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface QueryCollator {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SortDirection {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SyncExemption {
    }

}

