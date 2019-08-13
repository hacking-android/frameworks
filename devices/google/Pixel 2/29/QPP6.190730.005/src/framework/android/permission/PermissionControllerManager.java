/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.permission.-$
 *  android.permission.-$$Lambda
 *  android.permission.-$$Lambda$PermissionControllerManager
 *  android.permission.-$$Lambda$PermissionControllerManager$RemoteService
 *  android.permission.-$$Lambda$PermissionControllerManager$RemoteService$L8N-TbqIPWKu7tyiOxbu_00YKss
 *  libcore.io.IoUtils
 */
package android.permission;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.permission.-$;
import android.permission.IPermissionController;
import android.permission.RuntimePermissionPresentationInfo;
import android.permission.RuntimePermissionUsageInfo;
import android.permission._$$Lambda$PermissionControllerManager$PendingCountPermissionAppsRequest$5yk4p2I96nUHJ1QRErjoF1iiLLY;
import android.permission._$$Lambda$PermissionControllerManager$PendingGetAppPermissionRequest$7R0rGbvqPEHrjxlrMX66LMgfTj4;
import android.permission._$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$M0RAdfneqBIIFQEhfWzd068mi7g;
import android.permission._$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$WBIc65bpG47GE1DYeIzY6NX7Oyw;
import android.permission._$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE;
import android.permission._$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$LF2T0wqhyO211uMsePvWLLBRNHc;
import android.permission._$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$khE8_2qLkPzjjwzPXI9vCg1JiSo;
import android.permission._$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$S_BIiPaqfMH7CNqPH_RO6xHRCeQ;
import android.permission._$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$ZGmiW_2RcTI6YZLE1JgWr0ufJGk;
import android.permission._$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8;
import android.permission._$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$HQXgA6xx0k7jv6y22RQn3Fx34QQ;
import android.permission._$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$RY69_9rYfdoaXdLj_Ux_62tZUXg;
import android.permission._$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$StUWUj0fmNRuCwuUzh3M5C7e_o0;
import android.permission._$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$9CrKvc4Mj43M641VzAbk1z_vjck;
import android.permission._$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$L3EtiNpasfEGf_E2sSUKhk_dYUg;
import android.permission._$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$cgbsG1socgf6wsJmCUAPmh_jKmw;
import android.permission._$$Lambda$PermissionControllerManager$RemoteService$L8N_TbqIPWKu7tyiOxbu_00YKss;
import android.util.ArrayMap;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import com.android.internal.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import libcore.io.IoUtils;

@SystemApi
public final class PermissionControllerManager {
    public static final int COUNT_ONLY_WHEN_GRANTED = 1;
    public static final int COUNT_WHEN_SYSTEM = 2;
    public static final String KEY_RESULT = "android.permission.PermissionControllerManager.key.result";
    public static final int REASON_INSTALLER_POLICY_VIOLATION = 2;
    public static final int REASON_MALWARE = 1;
    private static final String TAG = PermissionControllerManager.class.getSimpleName();
    private static final Object sLock = new Object();
    @GuardedBy(value={"sLock"})
    private static ArrayMap<Pair<Integer, Thread>, RemoteService> sRemoteServices = new ArrayMap(1);
    private final Context mContext;
    private final RemoteService mRemoteService;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public PermissionControllerManager(Context context, Handler handler) {
        Object object = sLock;
        synchronized (object) {
            Object object2;
            Pair<Integer, Thread> pair = new Pair<Integer, Thread>(context.getUserId(), handler.getLooper().getThread());
            Object object3 = object2 = sRemoteServices.get(pair);
            if (object2 == null) {
                object3 = new Intent("android.permission.PermissionControllerService");
                ((Intent)object3).setPackage(context.getPackageManager().getPermissionControllerPackageName());
                object2 = context.getPackageManager().resolveService((Intent)object3, 0);
                object3 = new RemoteService(ActivityThread.currentApplication(), ((ResolveInfo)object2).getComponentInfo().getComponentName(), handler, context.getUser());
                sRemoteServices.put(pair, (RemoteService)object3);
            }
            this.mRemoteService = object3;
        }
        this.mContext = context;
    }

    public void countPermissionApps(List<String> list, int n, OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, Handler handler) {
        Preconditions.checkCollectionElementsNotNull(list, "permissionNames");
        Preconditions.checkFlagsArgument(n, 3);
        Preconditions.checkNotNull(onCountPermissionAppsResultCallback);
        RemoteService remoteService = this.mRemoteService;
        if (handler == null) {
            handler = remoteService.getHandler();
        }
        remoteService.scheduleRequest(new PendingCountPermissionAppsRequest(remoteService, list, n, onCountPermissionAppsResultCallback, handler));
    }

    public void getAppPermissions(String string2, OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, Handler handler) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(onGetAppPermissionResultCallback);
        RemoteService remoteService = this.mRemoteService;
        if (handler == null) {
            handler = remoteService.getHandler();
        }
        remoteService.scheduleRequest(new PendingGetAppPermissionRequest(remoteService, string2, onGetAppPermissionResultCallback, handler));
    }

    public void getPermissionUsages(boolean bl, long l, Executor executor, OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
        Preconditions.checkArgumentNonnegative(l);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onPermissionUsageResultCallback);
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new PendingGetPermissionUsagesRequest(remoteService, bl, l, executor, onPermissionUsageResultCallback));
    }

    public void getRuntimePermissionBackup(UserHandle userHandle, Executor executor, OnGetRuntimePermissionBackupCallback onGetRuntimePermissionBackupCallback) {
        Preconditions.checkNotNull(userHandle);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onGetRuntimePermissionBackupCallback);
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new PendingGetRuntimePermissionBackup(remoteService, userHandle, executor, onGetRuntimePermissionBackupCallback));
    }

    public void grantOrUpgradeDefaultRuntimePermissions(Executor executor, Consumer<Boolean> consumer) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(remoteService, executor, consumer));
    }

    public void restoreDelayedRuntimePermissionBackup(String string2, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(userHandle);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(consumer);
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new PendingRestoreDelayedRuntimePermissionBackup(remoteService, string2, userHandle, executor, consumer));
    }

    public void restoreRuntimePermissionBackup(byte[] arrby, UserHandle userHandle) {
        Preconditions.checkNotNull(arrby);
        Preconditions.checkNotNull(userHandle);
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleAsyncRequest(new PendingRestoreRuntimePermissionBackup(remoteService, arrby, userHandle));
    }

    public void revokeRuntimePermission(String string2, String string3) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(string3);
        this.mRemoteService.scheduleAsyncRequest(new PendingRevokeAppPermissionRequest(string2, string3));
    }

    public void revokeRuntimePermissions(Map<String, List<String>> map, boolean bl, int n, Executor executor, OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onRevokeRuntimePermissionsCallback);
        Preconditions.checkNotNull(map);
        for (Map.Entry<String, List<String>> object : map.entrySet()) {
            Preconditions.checkNotNull(object.getKey());
            Preconditions.checkCollectionElementsNotNull(object.getValue(), "permissions");
        }
        if (this.mContext.checkSelfPermission("android.permission.REVOKE_RUNTIME_PERMISSIONS") == 0) {
            RemoteService remoteService = this.mRemoteService;
            remoteService.scheduleRequest(new PendingRevokeRuntimePermissionRequest(remoteService, map, bl, n, this.mContext.getPackageName(), executor, onRevokeRuntimePermissionsCallback));
            return;
        }
        throw new SecurityException("android.permission.REVOKE_RUNTIME_PERMISSIONS required");
    }

    public void setRuntimePermissionGrantStateByDeviceAdmin(String string2, String string3, String string4, int n, Executor executor, Consumer<Boolean> consumer) {
        boolean bl;
        Preconditions.checkStringNotEmpty(string2);
        Preconditions.checkStringNotEmpty(string3);
        Preconditions.checkStringNotEmpty(string4);
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 2) {
                bl2 = n == 0 ? bl : false;
            }
        }
        Preconditions.checkArgument(bl2);
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(consumer);
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new PendingSetRuntimePermissionGrantStateByDeviceAdmin(remoteService, string2, string3, string4, n, executor, consumer));
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CountPermissionAppsFlag {
    }

    private static class FileReaderTask<Callback extends Consumer<byte[]>>
    extends AsyncTask<Void, Void, byte[]> {
        private final Callback mCallback;
        private ParcelFileDescriptor mLocalPipe;
        private ParcelFileDescriptor mRemotePipe;

        FileReaderTask(Callback Callback3) {
            this.mCallback = Callback3;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected byte[] doInBackground(Void ... object) {
            int n;
            object = new ByteArrayOutputStream();
            ParcelFileDescriptor.AutoCloseInputStream autoCloseInputStream = new ParcelFileDescriptor.AutoCloseInputStream(this.mLocalPipe);
            byte[] arrby = new byte[16384];
            while (!this.isCancelled() && (n = ((InputStream)autoCloseInputStream).read(arrby)) != -1) {
                ((ByteArrayOutputStream)object).write(arrby, 0, n);
            }
            ((InputStream)autoCloseInputStream).close();
            return ((ByteArrayOutputStream)object).toByteArray();
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        ((InputStream)autoCloseInputStream).close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
                            throw throwable2;
                        }
                        catch (IOException | NullPointerException exception) {
                            Log.e(TAG, "Error reading runtime permission backup", exception);
                            ((ByteArrayOutputStream)object).reset();
                        }
                    }
                }
            }
            return ((ByteArrayOutputStream)object).toByteArray();
        }

        ParcelFileDescriptor getRemotePipe() {
            return this.mRemotePipe;
        }

        void interruptRead() {
            IoUtils.closeQuietly((AutoCloseable)this.mLocalPipe);
        }

        @Override
        protected void onCancelled() {
            this.onPostExecute(new byte[0]);
        }

        @Override
        protected void onPostExecute(byte[] arrby) {
            IoUtils.closeQuietly((AutoCloseable)this.mLocalPipe);
            this.mCallback.accept((byte[])arrby);
        }

        @Override
        protected void onPreExecute() {
            ParcelFileDescriptor[] arrparcelFileDescriptor;
            try {
                arrparcelFileDescriptor = ParcelFileDescriptor.createPipe();
            }
            catch (IOException iOException) {
                Log.e(TAG, "Could not create pipe needed to get runtime permission backup", iOException);
                return;
            }
            this.mLocalPipe = arrparcelFileDescriptor[0];
            this.mRemotePipe = arrparcelFileDescriptor[1];
        }
    }

    private static class FileWriterTask
    extends AsyncTask<byte[], Void, Void> {
        private static final int CHUNK_SIZE = 4096;
        private ParcelFileDescriptor mLocalPipe;
        private ParcelFileDescriptor mRemotePipe;

        private FileWriterTask() {
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        protected Void doInBackground(byte[] ... arrby) {
            arrby = arrby[0];
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(this.mLocalPipe);
            for (int n = 0; n < arrby.length; n += 4096) {
                ((OutputStream)autoCloseOutputStream).write((byte[])arrby, n, Math.min(4096, arrby.length - n));
            }
            ((OutputStream)autoCloseOutputStream).close();
            return null;
            catch (Throwable throwable) {
                try {
                    throw throwable;
                }
                catch (Throwable throwable2) {
                    try {
                        ((OutputStream)autoCloseOutputStream).close();
                        throw throwable2;
                    }
                    catch (Throwable throwable3) {
                        try {
                            throwable.addSuppressed(throwable3);
                            throw throwable2;
                        }
                        catch (IOException | NullPointerException exception) {
                            Log.e(TAG, "Error sending runtime permission backup", exception);
                        }
                    }
                }
            }
            return null;
        }

        ParcelFileDescriptor getRemotePipe() {
            return this.mRemotePipe;
        }

        void interruptWrite() {
            IoUtils.closeQuietly((AutoCloseable)this.mLocalPipe);
        }

        @Override
        protected void onCancelled() {
            this.onPostExecute(null);
        }

        @Override
        protected void onPostExecute(Void void_) {
            IoUtils.closeQuietly((AutoCloseable)this.mLocalPipe);
        }

        @Override
        protected void onPreExecute() {
            ParcelFileDescriptor[] arrparcelFileDescriptor;
            try {
                arrparcelFileDescriptor = ParcelFileDescriptor.createPipe();
            }
            catch (IOException iOException) {
                Log.e(TAG, "Could not create pipe needed to send runtime permission backup", iOException);
                return;
            }
            this.mRemotePipe = arrparcelFileDescriptor[0];
            this.mLocalPipe = arrparcelFileDescriptor[1];
        }
    }

    public static interface OnCountPermissionAppsResultCallback {
        public void onCountPermissionApps(int var1);
    }

    public static interface OnGetAppPermissionResultCallback {
        public void onGetAppPermissions(List<RuntimePermissionPresentationInfo> var1);
    }

    public static interface OnGetRuntimePermissionBackupCallback {
        public void onGetRuntimePermissionsBackup(byte[] var1);
    }

    public static interface OnPermissionUsageResultCallback {
        public void onPermissionUsageResult(List<RuntimePermissionUsageInfo> var1);
    }

    public static abstract class OnRevokeRuntimePermissionsCallback {
        public abstract void onRevokeRuntimePermissions(Map<String, List<String>> var1);
    }

    private static final class PendingCountPermissionAppsRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnCountPermissionAppsResultCallback mCallback;
        private final int mFlags;
        private final List<String> mPermissionNames;
        private final RemoteCallback mRemoteCallback;

        private PendingCountPermissionAppsRequest(RemoteService remoteService, List<String> list, int n, OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, Handler handler) {
            super(remoteService);
            this.mPermissionNames = list;
            this.mFlags = n;
            this.mCallback = onCountPermissionAppsResultCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingCountPermissionAppsRequest$5yk4p2I96nUHJ1QRErjoF1iiLLY(this, onCountPermissionAppsResultCallback), handler);
        }

        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingCountPermissionAppsRequest(OnCountPermissionAppsResultCallback onCountPermissionAppsResultCallback, Bundle bundle) {
            int n = bundle != null ? bundle.getInt(PermissionControllerManager.KEY_RESULT) : 0;
            onCountPermissionAppsResultCallback.onCountPermissionApps(n);
            this.finish();
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.onCountPermissionApps(0);
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).countPermissionApps(this.mPermissionNames, this.mFlags, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error counting permission apps", remoteException);
            }
        }
    }

    private static final class PendingGetAppPermissionRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnGetAppPermissionResultCallback mCallback;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;

        private PendingGetAppPermissionRequest(RemoteService remoteService, String string2, OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, Handler handler) {
            super(remoteService);
            this.mPackageName = string2;
            this.mCallback = onGetAppPermissionResultCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingGetAppPermissionRequest$7R0rGbvqPEHrjxlrMX66LMgfTj4(this, onGetAppPermissionResultCallback), handler);
        }

        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingGetAppPermissionRequest(OnGetAppPermissionResultCallback onGetAppPermissionResultCallback, Bundle object) {
            ArrayList arrayList = null;
            if (object != null) {
                arrayList = ((Bundle)object).getParcelableArrayList(PermissionControllerManager.KEY_RESULT);
            }
            object = arrayList;
            if (arrayList == null) {
                object = Collections.emptyList();
            }
            onGetAppPermissionResultCallback.onGetAppPermissions((List<RuntimePermissionPresentationInfo>)object);
            this.finish();
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.onGetAppPermissions(Collections.<RuntimePermissionPresentationInfo>emptyList());
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).getAppPermissions(this.mPackageName, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error getting app permission", remoteException);
            }
        }
    }

    private static final class PendingGetPermissionUsagesRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnPermissionUsageResultCallback mCallback;
        private final boolean mCountSystem;
        private final long mNumMillis;
        private final RemoteCallback mRemoteCallback;

        private PendingGetPermissionUsagesRequest(RemoteService remoteService, boolean bl, long l, Executor executor, OnPermissionUsageResultCallback onPermissionUsageResultCallback) {
            super(remoteService);
            this.mCountSystem = bl;
            this.mNumMillis = l;
            this.mCallback = onPermissionUsageResultCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$M0RAdfneqBIIFQEhfWzd068mi7g(this, executor, onPermissionUsageResultCallback), null);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingGetPermissionUsagesRequest(Bundle var1_1, OnPermissionUsageResultCallback var2_3) {
            var3_4 = Binder.clearCallingIdentity();
            if (var1_1 /* !! */  == null) ** GOTO lbl6
            try {
                block3 : {
                    var1_1 /* !! */  = var1_1 /* !! */ .getParcelableArrayList("android.permission.PermissionControllerManager.key.result");
                    break block3;
lbl6: // 1 sources:
                    var1_1 /* !! */  = Collections.emptyList();
                }
                var2_3.onPermissionUsageResult(var1_1 /* !! */ );
                return;
            }
            finally {
                Binder.restoreCallingIdentity(var3_4);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$PermissionControllerManager$PendingGetPermissionUsagesRequest(Executor executor, OnPermissionUsageResultCallback onPermissionUsageResultCallback, Bundle bundle) {
            executor.execute(new _$$Lambda$PermissionControllerManager$PendingGetPermissionUsagesRequest$WBIc65bpG47GE1DYeIzY6NX7Oyw(this, bundle, onPermissionUsageResultCallback));
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.onPermissionUsageResult(Collections.<RuntimePermissionUsageInfo>emptyList());
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).getPermissionUsages(this.mCountSystem, this.mNumMillis, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error counting permission users", remoteException);
            }
        }
    }

    private static final class PendingGetRuntimePermissionBackup
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController>
    implements Consumer<byte[]> {
        private final FileReaderTask<PendingGetRuntimePermissionBackup> mBackupReader;
        private final OnGetRuntimePermissionBackupCallback mCallback;
        private final Executor mExecutor;
        private final UserHandle mUser;

        private PendingGetRuntimePermissionBackup(RemoteService remoteService, UserHandle userHandle, Executor executor, OnGetRuntimePermissionBackupCallback onGetRuntimePermissionBackupCallback) {
            super(remoteService);
            this.mUser = userHandle;
            this.mExecutor = executor;
            this.mCallback = onGetRuntimePermissionBackupCallback;
            this.mBackupReader = new FileReaderTask<PendingGetRuntimePermissionBackup>(this);
        }

        @Override
        public void accept(byte[] arrby) {
            long l = Binder.clearCallingIdentity();
            Executor executor = this.mExecutor;
            _$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE _$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE = new _$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE(this, arrby);
            executor.execute(_$$Lambda$PermissionControllerManager$PendingGetRuntimePermissionBackup$TnLX6gxZCMF3D0czwj_XwNhPIgE);
            this.finish();
            return;
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        public /* synthetic */ void lambda$accept$0$PermissionControllerManager$PendingGetRuntimePermissionBackup(byte[] arrby) {
            this.mCallback.onGetRuntimePermissionsBackup(arrby);
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mBackupReader.cancel(true);
            this.mBackupReader.interruptRead();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            if (this.mBackupReader.getStatus() != AsyncTask.Status.PENDING) {
                return;
            }
            this.mBackupReader.execute(new Void[0]);
            ParcelFileDescriptor parcelFileDescriptor = this.mBackupReader.getRemotePipe();
            try {
                try {
                    ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).getRuntimePermissionBackup(this.mUser, parcelFileDescriptor);
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Error getting runtime permission backup", remoteException);
                }
            }
            catch (Throwable throwable2) {}
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            return;
            IoUtils.closeQuietly((AutoCloseable)parcelFileDescriptor);
            throw throwable2;
        }
    }

    private static final class PendingGrantOrUpgradeDefaultRuntimePermissionsRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final RemoteCallback mRemoteCallback;

        private PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(RemoteService remoteService, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$khE8_2qLkPzjjwzPXI9vCg1JiSo(this, executor, consumer), null);
        }

        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(Consumer consumer, Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            boolean bl = bundle != null;
            try {
                consumer.accept(bl);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest(Executor executor, Consumer consumer, Bundle bundle) {
            executor.execute(new _$$Lambda$PermissionControllerManager$PendingGrantOrUpgradeDefaultRuntimePermissionsRequest$LF2T0wqhyO211uMsePvWLLBRNHc(this, consumer, bundle));
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            long l = Binder.clearCallingIdentity();
            try {
                this.mCallback.accept(false);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).grantOrUpgradeDefaultRuntimePermissions(this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error granting or upgrading runtime permissions", remoteException);
            }
        }
    }

    private static final class PendingRestoreDelayedRuntimePermissionBackup
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final UserHandle mUser;

        private PendingRestoreDelayedRuntimePermissionBackup(RemoteService remoteService, String string2, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mPackageName = string2;
            this.mUser = userHandle;
            this.mExecutor = executor;
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$S_BIiPaqfMH7CNqPH_RO6xHRCeQ(this, executor, consumer), null);
        }

        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup(Consumer consumer, Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                consumer.accept(bundle.getBoolean(PermissionControllerManager.KEY_RESULT, false));
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup(Executor executor, Consumer consumer, Bundle bundle) {
            executor.execute(new _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$ZGmiW_2RcTI6YZLE1JgWr0ufJGk(this, consumer, bundle));
        }

        public /* synthetic */ void lambda$onTimeout$2$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup() {
            this.mCallback.accept(true);
        }

        @Override
        protected void onTimeout(RemoteService object) {
            long l = Binder.clearCallingIdentity();
            try {
                object = this.mExecutor;
                _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8 _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8 = new _$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8(this);
                object.execute(_$$Lambda$PermissionControllerManager$PendingRestoreDelayedRuntimePermissionBackup$eZmglu_5wkoNFQT0fHebFoNMze8);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).restoreDelayedRuntimePermissionBackup(this.mPackageName, this.mUser, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error restoring delayed permissions for ");
                stringBuilder.append(this.mPackageName);
                Log.e(string2, stringBuilder.toString(), remoteException);
            }
        }
    }

    private static final class PendingRestoreRuntimePermissionBackup
    implements AbstractRemoteService.AsyncRequest<IPermissionController> {
        private final byte[] mBackup;
        private final FileWriterTask mBackupSender;
        private final UserHandle mUser;

        private PendingRestoreRuntimePermissionBackup(RemoteService remoteService, byte[] arrby, UserHandle userHandle) {
            this.mBackup = arrby;
            this.mUser = userHandle;
            this.mBackupSender = new FileWriterTask();
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
        public void run(IPermissionController var1_1) {
            if (this.mBackupSender.getStatus() != AsyncTask.Status.PENDING) {
                return;
            }
            this.mBackupSender.execute((Params[])new byte[][]{this.mBackup});
            var2_4 = this.mBackupSender.getRemotePipe();
            var1_1.restoreRuntimePermissionBackup(this.mUser, var2_4);
lbl8: // 2 sources:
            do {
                IoUtils.closeQuietly((AutoCloseable)var2_4);
                return;
                break;
            } while (true);
            {
                catch (Throwable var1_2) {
                }
                catch (RemoteException var1_3) {}
                {
                    Log.e(PermissionControllerManager.access$1000(), "Error sending runtime permission backup", var1_3);
                    this.mBackupSender.cancel(false);
                    this.mBackupSender.interruptWrite();
                    ** continue;
                }
            }
            IoUtils.closeQuietly((AutoCloseable)var2_4);
            throw var1_2;
        }
    }

    private static final class PendingRevokeAppPermissionRequest
    implements AbstractRemoteService.AsyncRequest<IPermissionController> {
        private final String mPackageName;
        private final String mPermissionName;

        private PendingRevokeAppPermissionRequest(String string2, String string3) {
            this.mPackageName = string2;
            this.mPermissionName = string3;
        }

        @Override
        public void run(IPermissionController iPermissionController) {
            try {
                iPermissionController.revokeRuntimePermission(this.mPackageName, this.mPermissionName);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error revoking app permission", remoteException);
            }
        }
    }

    private static final class PendingRevokeRuntimePermissionRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final OnRevokeRuntimePermissionsCallback mCallback;
        private final String mCallingPackage;
        private final boolean mDoDryRun;
        private final Executor mExecutor;
        private final int mReason;
        private final RemoteCallback mRemoteCallback;
        private final Map<String, List<String>> mRequest;

        private PendingRevokeRuntimePermissionRequest(RemoteService remoteService, Map<String, List<String>> map, boolean bl, int n, String string2, Executor executor, OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
            super(remoteService);
            this.mRequest = map;
            this.mDoDryRun = bl;
            this.mReason = n;
            this.mCallingPackage = string2;
            this.mExecutor = executor;
            this.mCallback = onRevokeRuntimePermissionsCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$StUWUj0fmNRuCwuUzh3M5C7e_o0(this, executor, onRevokeRuntimePermissionsCallback), null);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingRevokeRuntimePermissionRequest(Bundle object, OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback) {
            long l = Binder.clearCallingIdentity();
            try {
                void var2_6;
                ArrayMap<String, List<String>> arrayMap = new ArrayMap<String, List<String>>();
                try {
                    Bundle bundle = ((Bundle)object).getBundle(PermissionControllerManager.KEY_RESULT);
                    for (String string2 : bundle.keySet()) {
                        Preconditions.checkNotNull(string2);
                        ArrayList<String> arrayList = bundle.getStringArrayList(string2);
                        Preconditions.checkCollectionElementsNotNull(arrayList, "permissions");
                        arrayMap.put(string2, arrayList);
                    }
                }
                catch (Exception exception) {
                    Log.e(TAG, "Could not read result when revoking runtime permissions", exception);
                }
                var2_6.onRevokeRuntimePermissions(arrayMap);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$PermissionControllerManager$PendingRevokeRuntimePermissionRequest(Executor executor, OnRevokeRuntimePermissionsCallback onRevokeRuntimePermissionsCallback, Bundle bundle) {
            executor.execute(new _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$RY69_9rYfdoaXdLj_Ux_62tZUXg(this, bundle, onRevokeRuntimePermissionsCallback));
        }

        public /* synthetic */ void lambda$onTimeout$2$PermissionControllerManager$PendingRevokeRuntimePermissionRequest() {
            this.mCallback.onRevokeRuntimePermissions(Collections.<String, List<String>>emptyMap());
        }

        @Override
        protected void onTimeout(RemoteService object) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                object = new _$$Lambda$PermissionControllerManager$PendingRevokeRuntimePermissionRequest$HQXgA6xx0k7jv6y22RQn3Fx34QQ(this);
                executor.execute((Runnable)object);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void run() {
            Bundle bundle = new Bundle();
            for (Map.Entry<String, List<String>> entry : this.mRequest.entrySet()) {
                bundle.putStringArrayList(entry.getKey(), new ArrayList<String>((Collection)entry.getValue()));
            }
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).revokeRuntimePermissions(bundle, this.mDoDryRun, this.mReason, this.mCallingPackage, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Error revoking runtime permission", remoteException);
            }
        }
    }

    private static final class PendingSetRuntimePermissionGrantStateByDeviceAdmin
    extends AbstractRemoteService.PendingRequest<RemoteService, IPermissionController> {
        private final Consumer<Boolean> mCallback;
        private final String mCallerPackageName;
        private final Executor mExecutor;
        private final int mGrantState;
        private final String mPackageName;
        private final String mPermission;
        private final RemoteCallback mRemoteCallback;

        private PendingSetRuntimePermissionGrantStateByDeviceAdmin(RemoteService remoteService, String string2, String string3, String string4, int n, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mCallerPackageName = string2;
            this.mPackageName = string3;
            this.mPermission = string4;
            this.mGrantState = n;
            this.mExecutor = executor;
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$9CrKvc4Mj43M641VzAbk1z_vjck(this, executor, consumer), null);
        }

        public /* synthetic */ void lambda$new$0$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin(Consumer consumer, Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                consumer.accept(bundle.getBoolean(PermissionControllerManager.KEY_RESULT, false));
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin(Executor executor, Consumer consumer, Bundle bundle) {
            executor.execute(new _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$L3EtiNpasfEGf_E2sSUKhk_dYUg(this, consumer, bundle));
        }

        public /* synthetic */ void lambda$onTimeout$2$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin() {
            this.mCallback.accept(false);
        }

        @Override
        protected void onTimeout(RemoteService object) {
            long l = Binder.clearCallingIdentity();
            try {
                Executor executor = this.mExecutor;
                object = new _$$Lambda$PermissionControllerManager$PendingSetRuntimePermissionGrantStateByDeviceAdmin$cgbsG1socgf6wsJmCUAPmh_jKmw(this);
                executor.execute((Runnable)object);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }

        @Override
        public void run() {
            try {
                ((IPermissionController)((RemoteService)this.getService()).getServiceInterface()).setRuntimePermissionGrantStateByDeviceAdmin(this.mCallerPackageName, this.mPackageName, this.mPermission, this.mGrantState, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                String string2 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error setting permissions state for device admin ");
                stringBuilder.append(this.mPackageName);
                Log.e(string2, stringBuilder.toString(), remoteException);
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Reason {
    }

    static final class RemoteService
    extends AbstractMultiplePendingRequestsRemoteService<RemoteService, IPermissionController> {
        private static final long MESSAGE_TIMEOUT_MILLIS = 30000L;
        private static final long UNBIND_TIMEOUT_MILLIS = 10000L;

        RemoteService(Context context, ComponentName componentName, Handler handler, UserHandle userHandle) {
            super(context, "android.permission.PermissionControllerService", componentName, userHandle.getIdentifier(), _$$Lambda$PermissionControllerManager$RemoteService$L8N_TbqIPWKu7tyiOxbu_00YKss.INSTANCE, handler, 0, false, 1);
        }

        static /* synthetic */ void lambda$new$0(RemoteService remoteService) {
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteService ");
            stringBuilder.append(remoteService);
            stringBuilder.append(" died");
            Log.e(string2, stringBuilder.toString());
        }

        Handler getHandler() {
            return this.mHandler;
        }

        @Override
        protected long getRemoteRequestMillis() {
            return 30000L;
        }

        @Override
        protected IPermissionController getServiceInterface(IBinder iBinder) {
            return IPermissionController.Stub.asInterface(iBinder);
        }

        @Override
        protected long getTimeoutIdleBindMillis() {
            return 10000L;
        }

        @Override
        public void scheduleAsyncRequest(AbstractRemoteService.AsyncRequest<IPermissionController> asyncRequest) {
            super.scheduleAsyncRequest(asyncRequest);
        }

        @Override
        public void scheduleRequest(AbstractRemoteService.BasePendingRequest<RemoteService, IPermissionController> basePendingRequest) {
            super.scheduleRequest(basePendingRequest);
        }
    }

}

