/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.app.AppOpsManager;
import android.content.ClipDescription;
import android.content.ComponentCallbacks2;
import android.content.ContentInterface;
import android.content.ContentProviderNative;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.IContentProvider;
import android.content.LoggingContentInterface;
import android.content.OperationApplicationException;
import android.content.pm.PathPermission;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

public abstract class ContentProvider
implements ContentInterface,
ComponentCallbacks2 {
    private static final String TAG = "ContentProvider";
    @UnsupportedAppUsage
    private String[] mAuthorities;
    @UnsupportedAppUsage
    private String mAuthority;
    private ThreadLocal<String> mCallingPackage;
    @UnsupportedAppUsage
    private Context mContext = null;
    private boolean mExported;
    private int mMyUid;
    private boolean mNoPerms;
    @UnsupportedAppUsage
    private PathPermission[] mPathPermissions;
    @UnsupportedAppUsage
    private String mReadPermission;
    private boolean mSingleUser;
    private Transport mTransport = new Transport();
    @UnsupportedAppUsage
    private String mWritePermission;

    public ContentProvider() {
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public ContentProvider(Context context, String string2, String string3, PathPermission[] arrpathPermission) {
        this.mContext = context;
        this.mReadPermission = string2;
        this.mWritePermission = string3;
        this.mPathPermissions = arrpathPermission;
    }

    private void attachInfo(Context context, ProviderInfo providerInfo, boolean bl) {
        this.mNoPerms = bl;
        this.mCallingPackage = new ThreadLocal();
        if (this.mContext == null) {
            Transport transport;
            this.mContext = context;
            if (context != null && (transport = this.mTransport) != null) {
                transport.mAppOpsManager = (AppOpsManager)context.getSystemService("appops");
            }
            this.mMyUid = Process.myUid();
            if (providerInfo != null) {
                this.setReadPermission(providerInfo.readPermission);
                this.setWritePermission(providerInfo.writePermission);
                this.setPathPermissions(providerInfo.pathPermissions);
                this.mExported = providerInfo.exported;
                bl = (providerInfo.flags & 1073741824) != 0;
                this.mSingleUser = bl;
                this.setAuthorities(providerInfo.authority);
            }
            this.onCreate();
        }
    }

    private int checkPermissionAndAppOp(String string2, String string3, IBinder iBinder) {
        if (this.getContext().checkPermission(string2, Binder.getCallingPid(), Binder.getCallingUid(), iBinder) != 0) {
            return 2;
        }
        return this.mTransport.noteProxyOp(string3, AppOpsManager.permissionToOpCode(string2));
    }

    @UnsupportedAppUsage
    public static ContentProvider coerceToLocalContentProvider(IContentProvider iContentProvider) {
        if (iContentProvider instanceof Transport) {
            return ((Transport)iContentProvider).getContentProvider();
        }
        return null;
    }

    public static String getAuthorityWithoutUserId(String string2) {
        if (string2 == null) {
            return null;
        }
        return string2.substring(string2.lastIndexOf(64) + 1);
    }

    public static Uri getUriWithoutUserId(Uri uri) {
        if (uri == null) {
            return null;
        }
        Uri.Builder builder = uri.buildUpon();
        builder.authority(ContentProvider.getAuthorityWithoutUserId(uri.getAuthority()));
        return builder.build();
    }

    public static int getUserIdFromAuthority(String string2) {
        return ContentProvider.getUserIdFromAuthority(string2, -2);
    }

    public static int getUserIdFromAuthority(String string2, int n) {
        if (string2 == null) {
            return n;
        }
        int n2 = string2.lastIndexOf(64);
        if (n2 == -1) {
            return n;
        }
        string2 = string2.substring(0, n2);
        try {
            n = Integer.parseInt(string2);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            Log.w(TAG, "Error parsing userId.", numberFormatException);
            return -10000;
        }
    }

    public static int getUserIdFromUri(Uri uri) {
        return ContentProvider.getUserIdFromUri(uri, -2);
    }

    public static int getUserIdFromUri(Uri uri, int n) {
        if (uri == null) {
            return n;
        }
        return ContentProvider.getUserIdFromAuthority(uri.getAuthority(), n);
    }

    @UnsupportedAppUsage
    public static Uri maybeAddUserId(Uri uri, int n) {
        if (uri == null) {
            return null;
        }
        if (n != -2 && "content".equals(uri.getScheme()) && !ContentProvider.uriHasUserId(uri)) {
            Uri.Builder builder = uri.buildUpon();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(n);
            stringBuilder.append("@");
            stringBuilder.append(uri.getEncodedAuthority());
            builder.encodedAuthority(stringBuilder.toString());
            return builder.build();
        }
        return uri;
    }

    private Uri maybeGetUriWithoutUserId(Uri uri) {
        if (this.mSingleUser) {
            return uri;
        }
        return ContentProvider.getUriWithoutUserId(uri);
    }

    private String setCallingPackage(String string2) {
        String string3 = this.mCallingPackage.get();
        this.mCallingPackage.set(string2);
        this.onCallingPackageChanged();
        return string3;
    }

    public static boolean uriHasUserId(Uri uri) {
        if (uri == null) {
            return false;
        }
        return TextUtils.isEmpty(uri.getUserInfo()) ^ true;
    }

    private void validateIncomingAuthority(String string2) throws SecurityException {
        if (!this.matchesOurAuthorities(ContentProvider.getAuthorityWithoutUserId(string2))) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The authority ");
            stringBuilder.append(string2);
            stringBuilder.append(" does not match the one of the contentProvider: ");
            string2 = stringBuilder.toString();
            if (this.mAuthority != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(this.mAuthority);
                string2 = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(Arrays.toString(this.mAuthorities));
                string2 = stringBuilder.toString();
            }
            throw new SecurityException(string2);
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(String string2, ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        return this.applyBatch(arrayList);
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
        int n = arrayList.size();
        ContentProviderResult[] arrcontentProviderResult = new ContentProviderResult[n];
        for (int i = 0; i < n; ++i) {
            arrcontentProviderResult[i] = arrayList.get(i).apply(this, arrcontentProviderResult, i);
        }
        return arrcontentProviderResult;
    }

    public void attachInfo(Context context, ProviderInfo providerInfo) {
        this.attachInfo(context, providerInfo, false);
    }

    @UnsupportedAppUsage
    public void attachInfoForTesting(Context context, ProviderInfo providerInfo) {
        this.attachInfo(context, providerInfo, true);
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] arrcontentValues) {
        int n = arrcontentValues.length;
        for (int i = 0; i < n; ++i) {
            this.insert(uri, arrcontentValues[i]);
        }
        return n;
    }

    public Bundle call(String string2, String string3, Bundle bundle) {
        return null;
    }

    @Override
    public Bundle call(String string2, String string3, String string4, Bundle bundle) {
        return this.call(string3, string4, bundle);
    }

    @Override
    public Uri canonicalize(Uri uri) {
        return null;
    }

    boolean checkUser(int n, int n2, Context context) {
        boolean bl = UserHandle.getUserId(n2) == context.getUserId() || this.mSingleUser || context.checkPermission("android.permission.INTERACT_ACROSS_USERS", n, n2) == 0;
        return bl;
    }

    public final CallingIdentity clearCallingIdentity() {
        return new CallingIdentity(Binder.clearCallingIdentity(), this.setCallingPackage(null));
    }

    @Override
    public abstract int delete(Uri var1, String var2, String[] var3);

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("nothing to dump");
    }

    protected int enforceReadPermissionInner(Uri uri, String object, IBinder object2) throws SecurityException {
        int n;
        Context context = this.getContext();
        int n2 = Binder.getCallingPid();
        int n3 = Binder.getCallingUid();
        Object object3 = null;
        int n4 = 0;
        if (UserHandle.isSameApp(n3, this.mMyUid)) {
            return 0;
        }
        if (this.mExported && this.checkUser(n2, n3, context)) {
            String string2 = this.getReadPermission();
            if (string2 != null) {
                n4 = this.checkPermissionAndAppOp(string2, (String)object, (IBinder)object2);
                if (n4 == 0) {
                    return 0;
                }
                object3 = string2;
                n4 = Math.max(0, n4);
            }
            n = string2 == null ? 1 : 0;
            PathPermission[] arrpathPermission = this.getPathPermissions();
            if (arrpathPermission != null) {
                String string3 = uri.getPath();
                for (PathPermission pathPermission : arrpathPermission) {
                    string2 = pathPermission.getReadPermission();
                    if (string2 == null || !pathPermission.match(string3)) continue;
                    int n5 = this.checkPermissionAndAppOp(string2, (String)object, (IBinder)object2);
                    if (n5 == 0) {
                        return 0;
                    }
                    n = 0;
                    n4 = Math.max(n4, n5);
                    object3 = string2;
                }
            }
            if (n != 0) {
                return 0;
            }
            object = object3;
        } else {
            object = null;
            n4 = 0;
        }
        n = UserHandle.getUserId(n3);
        object3 = this.mSingleUser && !UserHandle.isSameUser(this.mMyUid, n3) ? ContentProvider.maybeAddUserId(uri, n) : uri;
        if (context.checkUriPermission((Uri)object3, n2, n3, 1, (IBinder)object2) == 0) {
            return 0;
        }
        if (n4 == 1) {
            return 1;
        }
        if (!"android.permission.MANAGE_DOCUMENTS".equals(this.mReadPermission)) {
            if (this.mExported) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" requires ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(", or grantUriPermission()");
                object = ((StringBuilder)object2).toString();
            } else {
                object = " requires the provider be exported, or grantUriPermission()";
            }
        } else {
            object = " requires that you obtain access using ACTION_OPEN_DOCUMENT or related APIs";
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Permission Denial: reading ");
        ((StringBuilder)object2).append(this.getClass().getName());
        ((StringBuilder)object2).append(" uri ");
        ((StringBuilder)object2).append(uri);
        ((StringBuilder)object2).append(" from pid=");
        ((StringBuilder)object2).append(n2);
        ((StringBuilder)object2).append(", uid=");
        ((StringBuilder)object2).append(n3);
        ((StringBuilder)object2).append((String)object);
        throw new SecurityException(((StringBuilder)object2).toString());
    }

    protected int enforceWritePermissionInner(Uri uri, String string2, IBinder object) throws SecurityException {
        int n;
        Context context = this.getContext();
        int n2 = Binder.getCallingPid();
        int n3 = Binder.getCallingUid();
        String string3 = null;
        int n4 = 0;
        if (UserHandle.isSameApp(n3, this.mMyUid)) {
            return 0;
        }
        if (this.mExported && this.checkUser(n2, n3, context)) {
            int n5;
            String string4 = this.getWritePermission();
            if (string4 != null) {
                n4 = this.checkPermissionAndAppOp(string4, string2, (IBinder)object);
                if (n4 == 0) {
                    return 0;
                }
                string3 = string4;
                n4 = Math.max(0, n4);
            }
            n = string4 == null ? 1 : 0;
            PathPermission[] arrpathPermission = this.getPathPermissions();
            if (arrpathPermission != null) {
                String string5 = uri.getPath();
                int n6 = arrpathPermission.length;
                n5 = n4;
                int n7 = 0;
                n4 = n;
                n = n5;
                for (n5 = n7; n5 < n6; ++n5) {
                    PathPermission pathPermission = arrpathPermission[n5];
                    string4 = pathPermission.getWritePermission();
                    if (string4 == null || !pathPermission.match(string5)) continue;
                    n7 = this.checkPermissionAndAppOp(string4, string2, (IBinder)object);
                    if (n7 == 0) {
                        return 0;
                    }
                    n4 = 0;
                    n = Math.max(n, n7);
                    string3 = string4;
                }
                n5 = n4;
            } else {
                n5 = n;
                n = n4;
            }
            if (n5 != 0) {
                return 0;
            }
            string2 = string3;
        } else {
            string2 = null;
            n = 0;
        }
        if (context.checkUriPermission(uri, n2, n3, 2, (IBinder)object) == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (this.mExported) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" requires ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(", or grantUriPermission()");
            string2 = ((StringBuilder)object).toString();
        } else {
            string2 = " requires the provider be exported, or grantUriPermission()";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Permission Denial: writing ");
        ((StringBuilder)object).append(this.getClass().getName());
        ((StringBuilder)object).append(" uri ");
        ((StringBuilder)object).append(uri);
        ((StringBuilder)object).append(" from pid=");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(", uid=");
        ((StringBuilder)object).append(n3);
        ((StringBuilder)object).append(string2);
        throw new SecurityException(((StringBuilder)object).toString());
    }

    public AppOpsManager getAppOpsManager() {
        return this.mTransport.mAppOpsManager;
    }

    public final String getCallingPackage() {
        String string2 = this.mCallingPackage.get();
        if (string2 != null) {
            this.mTransport.mAppOpsManager.checkPackage(Binder.getCallingUid(), string2);
        }
        return string2;
    }

    public final String getCallingPackageUnchecked() {
        return this.mCallingPackage.get();
    }

    public final Context getContext() {
        return this.mContext;
    }

    @UnsupportedAppUsage
    public IContentProvider getIContentProvider() {
        return this.mTransport;
    }

    public final PathPermission[] getPathPermissions() {
        return this.mPathPermissions;
    }

    public final String getReadPermission() {
        return this.mReadPermission;
    }

    @Override
    public String[] getStreamTypes(Uri uri, String string2) {
        return null;
    }

    @Override
    public abstract String getType(Uri var1);

    public final String getWritePermission() {
        return this.mWritePermission;
    }

    @Override
    public abstract Uri insert(Uri var1, ContentValues var2);

    protected boolean isTemporary() {
        return false;
    }

    protected final boolean matchesOurAuthorities(String string2) {
        String[] arrstring = this.mAuthority;
        if (arrstring != null) {
            return arrstring.equals(string2);
        }
        arrstring = this.mAuthorities;
        if (arrstring != null) {
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                if (!this.mAuthorities[i].equals(string2)) continue;
                return true;
            }
        }
        return false;
    }

    public void onCallingPackageChanged() {
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {
    }

    public abstract boolean onCreate();

    @Override
    public void onLowMemory() {
    }

    @Override
    public void onTrimMemory(int n) {
    }

    public AssetFileDescriptor openAssetFile(Uri parcelable, String string2) throws FileNotFoundException {
        parcelable = (parcelable = this.openFile((Uri)parcelable, string2)) != null ? new AssetFileDescriptor((ParcelFileDescriptor)parcelable, 0L, -1L) : null;
        return parcelable;
    }

    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return this.openAssetFile(uri, string2);
    }

    public ParcelFileDescriptor openFile(Uri uri, String charSequence) throws FileNotFoundException {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("No files supported by provider at ");
        ((StringBuilder)charSequence).append(uri);
        throw new FileNotFoundException(((StringBuilder)charSequence).toString());
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String string2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return this.openFile(uri, string2);
    }

    protected final ParcelFileDescriptor openFileHelper(Uri object, String charSequence) throws FileNotFoundException {
        Cursor cursor = this.query((Uri)object, new String[]{"_data"}, null, null, null);
        int n = cursor != null ? cursor.getCount() : 0;
        if (n != 1) {
            if (cursor != null) {
                cursor.close();
            }
            if (n == 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("No entry for ");
                ((StringBuilder)charSequence).append(object);
                throw new FileNotFoundException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Multiple items at ");
            ((StringBuilder)charSequence).append(object);
            throw new FileNotFoundException(((StringBuilder)charSequence).toString());
        }
        cursor.moveToFirst();
        n = cursor.getColumnIndex("_data");
        object = n >= 0 ? cursor.getString(n) : null;
        cursor.close();
        if (object != null) {
            n = ParcelFileDescriptor.parseMode((String)charSequence);
            return ParcelFileDescriptor.open(new File((String)object), n);
        }
        throw new FileNotFoundException("Column _data not found.");
    }

    public <T> ParcelFileDescriptor openPipeHelper(final Uri parcelable, final String string2, final Bundle bundle, final T t, final PipeDataWriter<T> pipeDataWriter) throws FileNotFoundException {
        ParcelFileDescriptor[] arrparcelFileDescriptor;
        try {
            arrparcelFileDescriptor = ParcelFileDescriptor.createPipe();
            AsyncTask<Object, Object, Object> asyncTask = new AsyncTask<Object, Object, Object>(){

                @Override
                protected Object doInBackground(Object ... arrobject) {
                    pipeDataWriter.writeDataToPipe(arrparcelFileDescriptor[1], parcelable, string2, bundle, t);
                    try {
                        arrparcelFileDescriptor[1].close();
                    }
                    catch (IOException iOException) {
                        Log.w(ContentProvider.TAG, "Failure closing pipe", iOException);
                    }
                    return null;
                }
            };
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, null);
        }
        catch (IOException iOException) {
            throw new FileNotFoundException("failure making pipe");
        }
        parcelable = arrparcelFileDescriptor[0];
        return parcelable;
    }

    public AssetFileDescriptor openTypedAssetFile(Uri uri, String string2, Bundle object) throws FileNotFoundException {
        if ("*/*".equals(string2)) {
            return this.openAssetFile(uri, "r");
        }
        object = this.getType(uri);
        if (object != null && ClipDescription.compareMimeTypes((String)object, string2)) {
            return this.openAssetFile(uri, "r");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Can't open ");
        ((StringBuilder)object).append(uri);
        ((StringBuilder)object).append(" as type ");
        ((StringBuilder)object).append(string2);
        throw new FileNotFoundException(((StringBuilder)object).toString());
    }

    @Override
    public AssetFileDescriptor openTypedAssetFile(Uri uri, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return this.openTypedAssetFile(uri, string2, bundle);
    }

    @Override
    public Cursor query(Uri uri, String[] arrstring, Bundle bundle, CancellationSignal cancellationSignal) {
        String string2;
        if (bundle == null) {
            bundle = Bundle.EMPTY;
        }
        String string3 = string2 = bundle.getString("android:query-arg-sql-sort-order");
        if (string2 == null) {
            string3 = string2;
            if (bundle.containsKey("android:query-arg-sort-columns")) {
                string3 = ContentResolver.createSqlSortClause(bundle);
            }
        }
        return this.query(uri, arrstring, bundle.getString("android:query-arg-sql-selection"), bundle.getStringArray("android:query-arg-sql-selection-args"), string3, cancellationSignal);
    }

    public abstract Cursor query(Uri var1, String[] var2, String var3, String[] var4, String var5);

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, CancellationSignal cancellationSignal) {
        return this.query(uri, arrstring, string2, arrstring2, string3);
    }

    @Override
    public boolean refresh(Uri uri, Bundle bundle, CancellationSignal cancellationSignal) {
        return false;
    }

    public Uri rejectInsert(Uri uri, ContentValues contentValues) {
        return uri.buildUpon().appendPath("0").build();
    }

    public final void restoreCallingIdentity(CallingIdentity callingIdentity) {
        Binder.restoreCallingIdentity(callingIdentity.binderToken);
        this.mCallingPackage.set(callingIdentity.callingPackage);
    }

    @UnsupportedAppUsage
    public final void setAppOps(int n, int n2) {
        if (!this.mNoPerms) {
            Transport transport = this.mTransport;
            transport.mReadOp = n;
            transport.mWriteOp = n2;
        }
    }

    protected final void setAuthorities(String string2) {
        if (string2 != null) {
            if (string2.indexOf(59) == -1) {
                this.mAuthority = string2;
                this.mAuthorities = null;
            } else {
                this.mAuthority = null;
                this.mAuthorities = string2.split(";");
            }
        }
    }

    protected final void setPathPermissions(PathPermission[] arrpathPermission) {
        this.mPathPermissions = arrpathPermission;
    }

    protected final void setReadPermission(String string2) {
        this.mReadPermission = string2;
    }

    public final void setTransportLoggingEnabled(boolean bl) {
        this.mTransport.mInterface = bl ? new LoggingContentInterface(this.getClass().getSimpleName(), this) : this;
    }

    protected final void setWritePermission(String string2) {
        this.mWritePermission = string2;
    }

    public void shutdown() {
        Log.w("ContentProvider", "implement ContentProvider shutdown() to make sure all database connections are gracefully shutdown");
    }

    @Override
    public Uri uncanonicalize(Uri uri) {
        return uri;
    }

    @Override
    public abstract int update(Uri var1, ContentValues var2, String var3, String[] var4);

    @VisibleForTesting
    public Uri validateIncomingUri(Uri object) throws SecurityException {
        int n;
        CharSequence charSequence = ((Uri)object).getAuthority();
        if (!this.mSingleUser && (n = ContentProvider.getUserIdFromAuthority((String)charSequence, -2)) != -2 && n != this.mContext.getUserId()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("trying to query a ContentProvider in user ");
            ((StringBuilder)object).append(this.mContext.getUserId());
            ((StringBuilder)object).append(" with a uri belonging to user ");
            ((StringBuilder)object).append(n);
            throw new SecurityException(((StringBuilder)object).toString());
        }
        this.validateIncomingAuthority((String)charSequence);
        charSequence = ((Uri)object).getEncodedPath();
        if (charSequence != null && ((String)charSequence).indexOf("//") != -1) {
            Uri uri = ((Uri)object).buildUpon().encodedPath(((String)charSequence).replaceAll("//+", "/")).build();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Normalized ");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(" to ");
            ((StringBuilder)charSequence).append(uri);
            ((StringBuilder)charSequence).append(" to avoid possible security issues");
            Log.w("ContentProvider", ((StringBuilder)charSequence).toString());
            return uri;
        }
        return object;
    }

    public final class CallingIdentity {
        public final long binderToken;
        public final String callingPackage;

        public CallingIdentity(long l, String string2) {
            this.binderToken = l;
            this.callingPackage = string2;
        }
    }

    public static interface PipeDataWriter<T> {
        public void writeDataToPipe(ParcelFileDescriptor var1, Uri var2, String var3, Bundle var4, T var5);
    }

    class Transport
    extends ContentProviderNative {
        volatile AppOpsManager mAppOpsManager = null;
        volatile ContentInterface mInterface = ContentProvider.this;
        volatile int mReadOp = -1;
        volatile int mWriteOp = -1;

        Transport() {
        }

        private void enforceFilePermission(String string2, Uri uri, String string3, IBinder iBinder) throws FileNotFoundException, SecurityException {
            block7 : {
                block6 : {
                    block5 : {
                        if (string3 == null || string3.indexOf(119) == -1) break block5;
                        if (this.enforceWritePermission(string2, uri, iBinder) != 0) {
                            throw new FileNotFoundException("App op not allowed");
                        }
                        break block6;
                    }
                    if (this.enforceReadPermission(string2, uri, iBinder) != 0) break block7;
                }
                return;
            }
            throw new FileNotFoundException("App op not allowed");
        }

        private int enforceReadPermission(String string2, Uri uri, IBinder iBinder) throws SecurityException {
            int n = ContentProvider.this.enforceReadPermissionInner(uri, string2, iBinder);
            if (n != 0) {
                return n;
            }
            return this.noteProxyOp(string2, this.mReadOp);
        }

        private int enforceWritePermission(String string2, Uri uri, IBinder iBinder) throws SecurityException {
            int n = ContentProvider.this.enforceWritePermissionInner(uri, string2, iBinder);
            if (n != 0) {
                return n;
            }
            return this.noteProxyOp(string2, this.mWriteOp);
        }

        private int noteProxyOp(String string2, int n) {
            if (n != -1) {
                if ((n = this.mAppOpsManager.noteProxyOp(n, string2)) == 3) {
                    n = 1;
                }
                return n;
            }
            return 0;
        }

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public ContentProviderResult[] applyBatch(String string2, String arrcontentProviderResult, ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
            Throwable throwable2222;
            block9 : {
                ContentProvider.this.validateIncomingAuthority((String)arrcontentProviderResult);
                int n = arrayList.size();
                int[] arrn = new int[n];
                int n2 = 0;
                do {
                    int n3 = 0;
                    if (n2 >= n) break;
                    ContentProviderOperation contentProviderOperation = arrayList.get(n2);
                    Parcelable parcelable = contentProviderOperation.getUri();
                    arrn[n2] = ContentProvider.getUserIdFromUri((Uri)parcelable);
                    parcelable = ContentProvider.this.validateIncomingUri((Uri)parcelable);
                    Uri uri = ContentProvider.this.maybeGetUriWithoutUserId((Uri)parcelable);
                    parcelable = contentProviderOperation;
                    if (!Objects.equals(contentProviderOperation.getUri(), uri)) {
                        parcelable = new ContentProviderOperation(contentProviderOperation, uri);
                        arrayList.set(n2, (ContentProviderOperation)parcelable);
                    }
                    if (((ContentProviderOperation)parcelable).isReadOperation()) {
                        if (this.enforceReadPermission(string2, uri, null) != 0) throw new OperationApplicationException("App op not allowed", 0);
                    }
                    if (((ContentProviderOperation)parcelable).isWriteOperation()) {
                        if (this.enforceWritePermission(string2, uri, null) != 0) throw new OperationApplicationException("App op not allowed", 0);
                    }
                    ++n2;
                } while (true);
                Trace.traceBegin(0x100000L, "applyBatch");
                string2 = ContentProvider.this.setCallingPackage(string2);
                arrcontentProviderResult = this.mInterface.applyBatch((String)arrcontentProviderResult, arrayList);
                if (arrcontentProviderResult == null) break block9;
                for (n2 = n3; n2 < arrcontentProviderResult.length; ++n2) {
                    if (arrn[n2] == -2) continue;
                    arrcontentProviderResult[n2] = new ContentProviderResult(arrcontentProviderResult[n2], arrn[n2]);
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return arrcontentProviderResult;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public int bulkInsert(String string2, Uri uri, ContentValues[] arrcontentValues) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            if (this.enforceWritePermission(string2, uri = ContentProvider.this.maybeGetUriWithoutUserId(uri), null) != 0) {
                return 0;
            }
            Trace.traceBegin(0x100000L, "bulkInsert");
            string2 = ContentProvider.this.setCallingPackage(string2);
            int n = this.mInterface.bulkInsert(uri, arrcontentValues);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return n;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public Bundle call(String string2, String object, String string3, String string4, Bundle bundle) {
            Throwable throwable2222;
            ContentProvider.this.validateIncomingAuthority((String)object);
            Bundle.setDefusable(bundle, true);
            Trace.traceBegin(0x100000L, "call");
            string2 = ContentProvider.this.setCallingPackage(string2);
            object = this.mInterface.call((String)object, string3, string4, bundle);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return object;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public Uri canonicalize(String string2, Uri uri) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            int n = ContentProvider.getUserIdFromUri(uri);
            if (this.enforceReadPermission(string2, uri = ContentProvider.getUriWithoutUserId(uri), null) != 0) {
                return null;
            }
            Trace.traceBegin(0x100000L, "canonicalize");
            string2 = ContentProvider.this.setCallingPackage(string2);
            uri = ContentProvider.maybeAddUserId(this.mInterface.canonicalize(uri), n);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return uri;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
        }

        @Override
        public ICancellationSignal createCancellationSignal() {
            return CancellationSignal.createTransport();
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
        public int delete(String string2, Uri uri, String string3, String[] arrstring) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            if (this.enforceWritePermission(string2, uri = ContentProvider.this.maybeGetUriWithoutUserId(uri), null) != 0) {
                return 0;
            }
            Trace.traceBegin(0x100000L, "delete");
            string2 = ContentProvider.this.setCallingPackage(string2);
            int n = this.mInterface.delete(uri, string3, arrstring);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return n;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
        }

        ContentProvider getContentProvider() {
            return ContentProvider.this;
        }

        @Override
        public String getProviderName() {
            return this.getContentProvider().getClass().getName();
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
        public String[] getStreamTypes(Uri arrstring, String string2) {
            Throwable throwable2222;
            arrstring = ContentProvider.this.validateIncomingUri((Uri)arrstring);
            arrstring = ContentProvider.this.maybeGetUriWithoutUserId((Uri)arrstring);
            Trace.traceBegin(0x100000L, "getStreamTypes");
            arrstring = this.mInterface.getStreamTypes((Uri)arrstring, string2);
            Trace.traceEnd(0x100000L);
            return arrstring;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public String getType(Uri object) {
            Throwable throwable2222;
            object = ContentProvider.this.validateIncomingUri((Uri)object);
            object = ContentProvider.this.maybeGetUriWithoutUserId((Uri)object);
            Trace.traceBegin(0x100000L, "getType");
            object = this.mInterface.getType((Uri)object);
            Trace.traceEnd(0x100000L);
            return object;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public Uri insert(String string2, Uri uri, ContentValues contentValues) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            int n = ContentProvider.getUserIdFromUri(uri);
            if (this.enforceWritePermission(string2, uri = ContentProvider.this.maybeGetUriWithoutUserId(uri), null) != 0) {
                string2 = ContentProvider.this.setCallingPackage(string2);
                try {
                    uri = ContentProvider.this.rejectInsert(uri, contentValues);
                    return uri;
                }
                finally {
                    ContentProvider.this.setCallingPackage(string2);
                }
            }
            Trace.traceBegin(0x100000L, "insert");
            string2 = ContentProvider.this.setCallingPackage(string2);
            uri = ContentProvider.maybeAddUserId(this.mInterface.insert(uri, contentValues), n);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return uri;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public AssetFileDescriptor openAssetFile(String string2, Uri parcelable, String string3, ICancellationSignal iCancellationSignal) throws FileNotFoundException {
            Throwable throwable2222;
            parcelable = ContentProvider.this.validateIncomingUri((Uri)parcelable);
            parcelable = ContentProvider.this.maybeGetUriWithoutUserId((Uri)parcelable);
            this.enforceFilePermission(string2, (Uri)parcelable, string3, null);
            Trace.traceBegin(0x100000L, "openAssetFile");
            string2 = ContentProvider.this.setCallingPackage(string2);
            parcelable = this.mInterface.openAssetFile((Uri)parcelable, string3, CancellationSignal.fromTransport(iCancellationSignal));
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return parcelable;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public ParcelFileDescriptor openFile(String string2, Uri parcelable, String string3, ICancellationSignal iCancellationSignal, IBinder iBinder) throws FileNotFoundException {
            Throwable throwable2222;
            parcelable = ContentProvider.this.validateIncomingUri((Uri)parcelable);
            parcelable = ContentProvider.this.maybeGetUriWithoutUserId((Uri)parcelable);
            this.enforceFilePermission(string2, (Uri)parcelable, string3, iBinder);
            Trace.traceBegin(0x100000L, "openFile");
            string2 = ContentProvider.this.setCallingPackage(string2);
            parcelable = this.mInterface.openFile((Uri)parcelable, string3, CancellationSignal.fromTransport(iCancellationSignal));
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return parcelable;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public AssetFileDescriptor openTypedAssetFile(String string2, Uri parcelable, String string3, Bundle bundle, ICancellationSignal iCancellationSignal) throws FileNotFoundException {
            Throwable throwable2222;
            Bundle.setDefusable(bundle, true);
            parcelable = ContentProvider.this.validateIncomingUri((Uri)parcelable);
            parcelable = ContentProvider.this.maybeGetUriWithoutUserId((Uri)parcelable);
            this.enforceFilePermission(string2, (Uri)parcelable, "r", null);
            Trace.traceBegin(0x100000L, "openTypedAssetFile");
            string2 = ContentProvider.this.setCallingPackage(string2);
            parcelable = this.mInterface.openTypedAssetFile((Uri)parcelable, string3, bundle, CancellationSignal.fromTransport(iCancellationSignal));
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return parcelable;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public Cursor query(String string2, Uri object, String[] arrstring, Bundle bundle, ICancellationSignal iCancellationSignal) {
            Throwable throwable3222;
            Throwable throwable22222;
            object = ContentProvider.this.validateIncomingUri((Uri)object);
            if (this.enforceReadPermission(string2, (Uri)(object = ContentProvider.this.maybeGetUriWithoutUserId((Uri)object)), null) != 0) {
                if (arrstring != null) {
                    return new MatrixCursor(arrstring, 0);
                }
                string2 = ContentProvider.this.setCallingPackage(string2);
                object = this.mInterface.query((Uri)object, arrstring, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                ContentProvider.this.setCallingPackage(string2);
                if (object != null) return new MatrixCursor(object.getColumnNames(), 0);
                return null;
            }
            Trace.traceBegin(0x100000L, "query");
            string2 = ContentProvider.this.setCallingPackage(string2);
            object = this.mInterface.query((Uri)object, arrstring, bundle, CancellationSignal.fromTransport(iCancellationSignal));
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return object;
            {
                catch (Throwable throwable22222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            throw throwable22222;
            {
                catch (Throwable throwable3222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable3222;
        }

        @Override
        public boolean refresh(String string2, Uri uri, Bundle bundle, ICancellationSignal iCancellationSignal) throws RemoteException {
            if (this.enforceReadPermission(string2, uri = ContentProvider.getUriWithoutUserId(ContentProvider.this.validateIncomingUri(uri)), null) != 0) {
                return false;
            }
            Trace.traceBegin(0x100000L, "refresh");
            string2 = ContentProvider.this.setCallingPackage(string2);
            try {
                boolean bl = this.mInterface.refresh(uri, bundle, CancellationSignal.fromTransport(iCancellationSignal));
                return bl;
            }
            finally {
                ContentProvider.this.setCallingPackage(string2);
                Trace.traceEnd(0x100000L);
            }
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
        public Uri uncanonicalize(String string2, Uri uri) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            int n = ContentProvider.getUserIdFromUri(uri);
            if (this.enforceReadPermission(string2, uri = ContentProvider.getUriWithoutUserId(uri), null) != 0) {
                return null;
            }
            Trace.traceBegin(0x100000L, "uncanonicalize");
            string2 = ContentProvider.this.setCallingPackage(string2);
            uri = ContentProvider.maybeAddUserId(this.mInterface.uncanonicalize(uri), n);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return uri;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
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
        public int update(String string2, Uri uri, ContentValues contentValues, String string3, String[] arrstring) {
            Throwable throwable2222;
            uri = ContentProvider.this.validateIncomingUri(uri);
            if (this.enforceWritePermission(string2, uri = ContentProvider.this.maybeGetUriWithoutUserId(uri), null) != 0) {
                return 0;
            }
            Trace.traceBegin(0x100000L, "update");
            string2 = ContentProvider.this.setCallingPackage(string2);
            int n = this.mInterface.update(uri, contentValues, string3, arrstring);
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            return n;
            {
                catch (Throwable throwable2222) {
                }
                catch (RemoteException remoteException) {}
                {
                    throw remoteException.rethrowAsRuntimeException();
                }
            }
            ContentProvider.this.setCallingPackage(string2);
            Trace.traceEnd(0x100000L);
            throw throwable2222;
        }
    }

}

