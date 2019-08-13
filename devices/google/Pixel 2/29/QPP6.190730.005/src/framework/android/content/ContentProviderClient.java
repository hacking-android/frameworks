/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 *  libcore.io.IoUtils
 */
package android.content;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.ContentInterface;
import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.IContentProvider;
import android.content.OperationApplicationException;
import android.content.res.AssetFileDescriptor;
import android.database.CrossProcessCursorWrapper;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.ICancellationSignal;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.Preconditions;
import dalvik.system.CloseGuard;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import libcore.io.IoUtils;

public class ContentProviderClient
implements ContentInterface,
AutoCloseable {
    private static final String TAG = "ContentProviderClient";
    @GuardedBy(value={"ContentProviderClient.class"})
    private static Handler sAnrHandler;
    private NotRespondingRunnable mAnrRunnable;
    private long mAnrTimeout;
    private final String mAuthority;
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    @UnsupportedAppUsage
    private final IContentProvider mContentProvider;
    private final ContentResolver mContentResolver;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final String mPackageName;
    private final boolean mStable;

    public ContentProviderClient(ContentResolver contentResolver, IContentProvider iContentProvider, String string2, boolean bl) {
        this.mContentResolver = contentResolver;
        this.mContentProvider = iContentProvider;
        this.mPackageName = contentResolver.mPackageName;
        this.mAuthority = string2;
        this.mStable = bl;
        this.mCloseGuard.open("close");
    }

    @VisibleForTesting
    public ContentProviderClient(ContentResolver contentResolver, IContentProvider iContentProvider, boolean bl) {
        this(contentResolver, iContentProvider, "unknown", bl);
    }

    private void afterRemote() {
        NotRespondingRunnable notRespondingRunnable = this.mAnrRunnable;
        if (notRespondingRunnable != null) {
            sAnrHandler.removeCallbacks(notRespondingRunnable);
        }
    }

    private void beforeRemote() {
        NotRespondingRunnable notRespondingRunnable = this.mAnrRunnable;
        if (notRespondingRunnable != null) {
            sAnrHandler.postDelayed(notRespondingRunnable, this.mAnrTimeout);
        }
    }

    private boolean closeInternal() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            this.setDetectNotResponding(0L);
            if (this.mStable) {
                return this.mContentResolver.releaseProvider(this.mContentProvider);
            }
            return this.mContentResolver.releaseUnstableProvider(this.mContentProvider);
        }
        return false;
    }

    @Deprecated
    public static void closeQuietly(ContentProviderClient contentProviderClient) {
        IoUtils.closeQuietly((AutoCloseable)contentProviderClient);
    }

    @Deprecated
    public static void releaseQuietly(ContentProviderClient contentProviderClient) {
        IoUtils.closeQuietly((AutoCloseable)contentProviderClient);
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
    public ContentProviderResult[] applyBatch(String arrcontentProviderResult, ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        Throwable throwable2222;
        Preconditions.checkNotNull(arrayList, "operations");
        this.beforeRemote();
        arrcontentProviderResult = this.mContentProvider.applyBatch(this.mPackageName, (String)arrcontentProviderResult, arrayList);
        this.afterRemote();
        return arrcontentProviderResult;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
    }

    public ContentProviderResult[] applyBatch(ArrayList<ContentProviderOperation> arrayList) throws RemoteException, OperationApplicationException {
        return this.applyBatch(this.mAuthority, arrayList);
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
    public int bulkInsert(Uri uri, ContentValues[] arrcontentValues) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        Preconditions.checkNotNull(arrcontentValues, "initialValues");
        this.beforeRemote();
        int n = this.mContentProvider.bulkInsert(this.mPackageName, uri, arrcontentValues);
        this.afterRemote();
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
    }

    public Bundle call(String string2, String string3, Bundle bundle) throws RemoteException {
        return this.call(this.mAuthority, string2, string3, bundle);
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
    public Bundle call(String object, String string2, String string3, Bundle bundle) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(object, "authority");
        Preconditions.checkNotNull(string2, "method");
        this.beforeRemote();
        object = this.mContentProvider.call(this.mPackageName, (String)object, string2, string3, bundle);
        this.afterRemote();
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
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
    public final Uri canonicalize(Uri uri) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        this.beforeRemote();
        uri = this.mContentProvider.canonicalize(this.mPackageName, uri);
        this.afterRemote();
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
    }

    @Override
    public void close() {
        this.closeInternal();
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
    public int delete(Uri uri, String string2, String[] arrstring) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        this.beforeRemote();
        int n = this.mContentProvider.delete(this.mPackageName, uri, string2, arrstring);
        this.afterRemote();
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
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
            super.finalize();
        }
    }

    public ContentProvider getLocalContentProvider() {
        return ContentProvider.coerceToLocalContentProvider(this.mContentProvider);
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
    public String[] getStreamTypes(Uri arrstring, String string2) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(arrstring, "url");
        Preconditions.checkNotNull(string2, "mimeTypeFilter");
        this.beforeRemote();
        arrstring = this.mContentProvider.getStreamTypes((Uri)arrstring, string2);
        this.afterRemote();
        return arrstring;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
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
    public String getType(Uri object) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(object, "url");
        this.beforeRemote();
        object = this.mContentProvider.getType((Uri)object);
        this.afterRemote();
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
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
    public Uri insert(Uri uri, ContentValues contentValues) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        this.beforeRemote();
        uri = this.mContentProvider.insert(this.mPackageName, uri, contentValues);
        this.afterRemote();
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
    }

    public AssetFileDescriptor openAssetFile(Uri uri, String string2) throws RemoteException, FileNotFoundException {
        return this.openAssetFile(uri, string2, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public AssetFileDescriptor openAssetFile(Uri var1_1, String var2_4, CancellationSignal var3_5) throws RemoteException, FileNotFoundException {
        Preconditions.checkNotNull(var1_1 /* !! */ , "url");
        Preconditions.checkNotNull(var2_4, "mode");
        this.beforeRemote();
        var4_6 = null;
        if (var3_5 == null) ** GOTO lbl12
        var3_5.throwIfCanceled();
        var4_6 = this.mContentProvider.createCancellationSignal();
        var3_5.setRemote(var4_6);
lbl12: // 2 sources:
        var1_1 /* !! */  = this.mContentProvider.openAssetFile(this.mPackageName, var1_1 /* !! */ , var2_4, var4_6);
        this.afterRemote();
        return var1_1 /* !! */ ;
        {
            catch (Throwable var1_2) {
            }
            catch (DeadObjectException var1_3) {}
            {
                if (this.mStable != false) throw var1_3;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw var1_3;
            }
        }
        this.afterRemote();
        throw var1_2;
    }

    public ParcelFileDescriptor openFile(Uri uri, String string2) throws RemoteException, FileNotFoundException {
        return this.openFile(uri, string2, null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ParcelFileDescriptor openFile(Uri var1_1, String var2_4, CancellationSignal var3_5) throws RemoteException, FileNotFoundException {
        Preconditions.checkNotNull(var1_1 /* !! */ , "url");
        Preconditions.checkNotNull(var2_4, "mode");
        this.beforeRemote();
        var4_6 = null;
        if (var3_5 == null) ** GOTO lbl12
        var3_5.throwIfCanceled();
        var4_6 = this.mContentProvider.createCancellationSignal();
        var3_5.setRemote(var4_6);
lbl12: // 2 sources:
        var1_1 /* !! */  = this.mContentProvider.openFile(this.mPackageName, var1_1 /* !! */ , var2_4, var4_6, null);
        this.afterRemote();
        return var1_1 /* !! */ ;
        {
            catch (Throwable var1_2) {
            }
            catch (DeadObjectException var1_3) {}
            {
                if (this.mStable != false) throw var1_3;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw var1_3;
            }
        }
        this.afterRemote();
        throw var1_2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public final AssetFileDescriptor openTypedAssetFile(Uri var1_1, String var2_4, Bundle var3_5, CancellationSignal var4_6) throws RemoteException, FileNotFoundException {
        Preconditions.checkNotNull(var1_1 /* !! */ , "uri");
        Preconditions.checkNotNull(var2_4, "mimeTypeFilter");
        this.beforeRemote();
        var5_7 = null;
        if (var4_6 == null) ** GOTO lbl12
        var4_6.throwIfCanceled();
        var5_7 = this.mContentProvider.createCancellationSignal();
        var4_6.setRemote(var5_7);
lbl12: // 2 sources:
        var1_1 /* !! */  = this.mContentProvider.openTypedAssetFile(this.mPackageName, var1_1 /* !! */ , var2_4, var3_5, var5_7);
        this.afterRemote();
        return var1_1 /* !! */ ;
        {
            catch (Throwable var1_2) {
            }
            catch (DeadObjectException var1_3) {}
            {
                if (this.mStable != false) throw var1_3;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw var1_3;
            }
        }
        this.afterRemote();
        throw var1_2;
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String string2, Bundle bundle) throws RemoteException, FileNotFoundException {
        return this.openTypedAssetFileDescriptor(uri, string2, bundle, null);
    }

    public final AssetFileDescriptor openTypedAssetFileDescriptor(Uri uri, String string2, Bundle bundle, CancellationSignal cancellationSignal) throws RemoteException, FileNotFoundException {
        return this.openTypedAssetFile(uri, string2, bundle, cancellationSignal);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public Cursor query(Uri var1_1, String[] var2_4, Bundle var3_5, CancellationSignal var4_6) throws RemoteException {
        Preconditions.checkNotNull(var1_1, "url");
        this.beforeRemote();
        var5_7 = null;
        if (var4_6 == null) ** GOTO lbl10
        var4_6.throwIfCanceled();
        var5_7 = this.mContentProvider.createCancellationSignal();
        var4_6.setRemote(var5_7);
lbl10: // 2 sources:
        if ((var1_1 = this.mContentProvider.query(this.mPackageName, (Uri)var1_1, var2_4, var3_5, var5_7)) == null) {
            this.afterRemote();
            return null;
        }
        var1_1 = new CursorWrapperInner((Cursor)var1_1);
        this.afterRemote();
        return var1_1;
        {
            catch (Throwable var1_2) {
            }
            catch (DeadObjectException var1_3) {}
            {
                if (this.mStable != false) throw var1_3;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw var1_3;
            }
        }
        this.afterRemote();
        throw var1_2;
    }

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) throws RemoteException {
        return this.query(uri, arrstring, string2, arrstring2, string3, null);
    }

    public Cursor query(Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3, CancellationSignal cancellationSignal) throws RemoteException {
        return this.query(uri, arrstring, ContentResolver.createSqlQueryBundle(string2, arrstring2, string3), cancellationSignal);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean refresh(Uri var1_1, Bundle var2_4, CancellationSignal var3_5) throws RemoteException {
        Preconditions.checkNotNull(var1_1, "url");
        this.beforeRemote();
        var4_6 = null;
        if (var3_5 == null) ** GOTO lbl10
        var3_5.throwIfCanceled();
        var4_6 = this.mContentProvider.createCancellationSignal();
        var3_5.setRemote(var4_6);
lbl10: // 2 sources:
        var5_7 = this.mContentProvider.refresh(this.mPackageName, var1_1, var2_4, var4_6);
        this.afterRemote();
        return var5_7;
        {
            catch (Throwable var1_2) {
            }
            catch (DeadObjectException var1_3) {}
            {
                if (this.mStable != false) throw var1_3;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw var1_3;
            }
        }
        this.afterRemote();
        throw var1_2;
    }

    @Deprecated
    public boolean release() {
        return this.closeInternal();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void setDetectNotResponding(long l) {
        synchronized (ContentProviderClient.class) {
            this.mAnrTimeout = l;
            if (l > 0L) {
                Object object;
                if (this.mAnrRunnable == null) {
                    this.mAnrRunnable = object = new NotRespondingRunnable();
                }
                if (sAnrHandler == null) {
                    sAnrHandler = object = new Handler(Looper.getMainLooper(), null, true);
                }
                Binder.allowBlocking(this.mContentProvider.asBinder());
            } else {
                this.mAnrRunnable = null;
                Binder.defaultBlocking(this.mContentProvider.asBinder());
            }
            return;
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
    public final Uri uncanonicalize(Uri uri) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        this.beforeRemote();
        uri = this.mContentProvider.uncanonicalize(this.mPackageName, uri);
        this.afterRemote();
        return uri;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
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
    public int update(Uri uri, ContentValues contentValues, String string2, String[] arrstring) throws RemoteException {
        Throwable throwable2222;
        Preconditions.checkNotNull(uri, "url");
        this.beforeRemote();
        int n = this.mContentProvider.update(this.mPackageName, uri, contentValues, string2, arrstring);
        this.afterRemote();
        return n;
        {
            catch (Throwable throwable2222) {
            }
            catch (DeadObjectException deadObjectException) {}
            {
                if (this.mStable) throw deadObjectException;
                this.mContentResolver.unstableProviderDied(this.mContentProvider);
                throw deadObjectException;
            }
        }
        this.afterRemote();
        throw throwable2222;
    }

    private final class CursorWrapperInner
    extends CrossProcessCursorWrapper {
        private final CloseGuard mCloseGuard;

        CursorWrapperInner(Cursor cursor) {
            super(cursor);
            this.mCloseGuard = CloseGuard.get();
            this.mCloseGuard.open("close");
        }

        @Override
        public void close() {
            this.mCloseGuard.close();
            super.close();
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

    private class NotRespondingRunnable
    implements Runnable {
        private NotRespondingRunnable() {
        }

        @Override
        public void run() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Detected provider not responding: ");
            stringBuilder.append(ContentProviderClient.this.mContentProvider);
            Log.w(ContentProviderClient.TAG, stringBuilder.toString());
            ContentProviderClient.this.mContentResolver.appNotRespondingViaProvider(ContentProviderClient.this.mContentProvider);
        }
    }

}

