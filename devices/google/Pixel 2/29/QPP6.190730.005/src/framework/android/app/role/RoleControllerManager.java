/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.role.-$
 *  android.app.role.-$$Lambda
 *  android.app.role.-$$Lambda$RoleControllerManager
 *  android.app.role.-$$Lambda$RoleControllerManager$RemoteService
 *  android.app.role.-$$Lambda$RoleControllerManager$RemoteService$45dMO3SdHJhfBB_YKrC44Sznmoo
 */
package android.app.role;

import android.app.ActivityThread;
import android.app.role.-$;
import android.app.role.IRoleController;
import android.app.role.RoleManager;
import android.app.role._$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$0iOorSSTMKMxorImfJcxQ8hscBs;
import android.app.role._$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$Qrnu382yknLH4_TvruMvYuK_N8M;
import android.app.role._$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$uMND2yv3BzXWyrtureF8K8b0f0A;
import android.app.role._$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$9YPce2vGDOZP97XHsgR7kBf64jQ;
import android.app.role._$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$YqB5KyJlcDUM5urf3ImMD1odxhI;
import android.app.role._$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$pbhRqekkSEnYlxVcT_rMcU6hV_E;
import android.app.role._$$Lambda$RoleControllerManager$IsRoleVisibleRequest$i7aWmxVK8GGR464ms_cqfIN7ou8;
import android.app.role._$$Lambda$RoleControllerManager$IsRoleVisibleRequest$mPvdI6Jc9sQbLKyjDLv3TR6mmlM;
import android.app.role._$$Lambda$RoleControllerManager$IsRoleVisibleRequest$oEPzdmOwBqsdvIknZm3f9_oOiE8;
import android.app.role._$$Lambda$RoleControllerManager$OnAddRoleHolderRequest$JT1k7eyE31b1Ili2aD3HPTU4d_Y;
import android.app.role._$$Lambda$RoleControllerManager$OnClearRoleHoldersRequest$WFtkA3AVOOzGz5tXwMpks5Iic_o;
import android.app.role._$$Lambda$RoleControllerManager$OnRemoveRoleHolderRequest$LtJIC2bE0p8jKF_FXl69Scqp5HE;
import android.app.role._$$Lambda$RoleControllerManager$RemoteService$45dMO3SdHJhfBB_YKrC44Sznmoo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.infra.AbstractMultiplePendingRequestsRemoteService;
import com.android.internal.infra.AbstractRemoteService;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class RoleControllerManager {
    private static final String LOG_TAG = RoleControllerManager.class.getSimpleName();
    private static volatile ComponentName sRemoteServiceComponentName;
    @GuardedBy(value={"sRemoteServicesLock"})
    private static final SparseArray<RemoteService> sRemoteServices;
    private static final Object sRemoteServicesLock;
    private final RemoteService mRemoteService;

    static {
        sRemoteServicesLock = new Object();
        sRemoteServices = new SparseArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private RoleControllerManager(ComponentName componentName, Handler handler, Context object) {
        Object object2 = sRemoteServicesLock;
        synchronized (object2) {
            int n = ((Context)object).getUserId();
            RemoteService remoteService = sRemoteServices.get(n);
            object = remoteService;
            if (remoteService == null) {
                object = new RemoteService(ActivityThread.currentApplication(), componentName, handler, n);
                sRemoteServices.put(n, (RemoteService)object);
            }
            this.mRemoteService = object;
            return;
        }
    }

    public RoleControllerManager(Context context) {
        this(RoleControllerManager.getRemoteServiceComponentName(context), context.getMainThreadHandler(), context);
    }

    public static RoleControllerManager createWithInitializedRemoteServiceComponentName(Handler handler, Context context) {
        return new RoleControllerManager(sRemoteServiceComponentName, handler, context);
    }

    private static ComponentName getRemoteServiceComponentName(Context object) {
        Intent intent = new Intent("android.app.role.RoleControllerService");
        object = ((Context)object).getPackageManager();
        intent.setPackage(((PackageManager)object).getPermissionControllerPackageName());
        return ((PackageManager)object).resolveService(intent, 0).getComponentInfo().getComponentName();
    }

    public static void initializeRemoteServiceComponentName(Context context) {
        sRemoteServiceComponentName = RoleControllerManager.getRemoteServiceComponentName(context);
    }

    public void grantDefaultRoles(Executor executor, Consumer<Boolean> consumer) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new GrantDefaultRolesRequest(remoteService, executor, consumer));
    }

    public void isApplicationQualifiedForRole(String string2, String string3, Executor executor, Consumer<Boolean> consumer) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new IsApplicationQualifiedForRoleRequest(remoteService, string2, string3, executor, consumer));
    }

    public void isRoleVisible(String string2, Executor executor, Consumer<Boolean> consumer) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new IsRoleVisibleRequest(remoteService, string2, executor, consumer));
    }

    public void onAddRoleHolder(String string2, String string3, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new OnAddRoleHolderRequest(remoteService, string2, string3, n, remoteCallback));
    }

    public void onClearRoleHolders(String string2, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new OnClearRoleHoldersRequest(remoteService, string2, n, remoteCallback));
    }

    public void onRemoveRoleHolder(String string2, String string3, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        RemoteService remoteService = this.mRemoteService;
        remoteService.scheduleRequest(new OnRemoveRoleHolderRequest(remoteService, string2, string3, n, remoteCallback));
    }

    private static final class GrantDefaultRolesRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final RemoteCallback mRemoteCallback;

        private GrantDefaultRolesRequest(RemoteService remoteService, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mExecutor = executor;
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$uMND2yv3BzXWyrtureF8K8b0f0A(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$GrantDefaultRolesRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            boolean bl = bundle != null;
            try {
                this.mCallback.accept(bl);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$RoleControllerManager$GrantDefaultRolesRequest(Bundle bundle) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$Qrnu382yknLH4_TvruMvYuK_N8M(this, bundle));
        }

        public /* synthetic */ void lambda$onTimeout$2$RoleControllerManager$GrantDefaultRolesRequest() {
            this.mCallback.accept(false);
        }

        @Override
        protected void onFailed() {
            this.mRemoteCallback.sendResult(null);
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$GrantDefaultRolesRequest$0iOorSSTMKMxorImfJcxQ8hscBs(this));
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).grantDefaultRoles(this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling grantDefaultRoles()", remoteException);
            }
        }
    }

    private static final class IsApplicationQualifiedForRoleRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private IsApplicationQualifiedForRoleRequest(RemoteService remoteService, String string2, String string3, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mRoleName = string2;
            this.mPackageName = string3;
            this.mExecutor = executor;
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$YqB5KyJlcDUM5urf3ImMD1odxhI(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$IsApplicationQualifiedForRoleRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            boolean bl = bundle != null;
            try {
                this.mCallback.accept(bl);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$RoleControllerManager$IsApplicationQualifiedForRoleRequest(Bundle bundle) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$pbhRqekkSEnYlxVcT_rMcU6hV_E(this, bundle));
        }

        public /* synthetic */ void lambda$onTimeout$2$RoleControllerManager$IsApplicationQualifiedForRoleRequest() {
            this.mCallback.accept(false);
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$IsApplicationQualifiedForRoleRequest$9YPce2vGDOZP97XHsgR7kBf64jQ(this));
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).isApplicationQualifiedForRole(this.mRoleName, this.mPackageName, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling isApplicationQualifiedForRole()", remoteException);
            }
        }
    }

    private static final class IsRoleVisibleRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final Consumer<Boolean> mCallback;
        private final Executor mExecutor;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private IsRoleVisibleRequest(RemoteService remoteService, String string2, Executor executor, Consumer<Boolean> consumer) {
            super(remoteService);
            this.mRoleName = string2;
            this.mExecutor = executor;
            this.mCallback = consumer;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$IsRoleVisibleRequest$oEPzdmOwBqsdvIknZm3f9_oOiE8(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$IsRoleVisibleRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            boolean bl = bundle != null;
            try {
                this.mCallback.accept(bl);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        public /* synthetic */ void lambda$new$1$RoleControllerManager$IsRoleVisibleRequest(Bundle bundle) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$IsRoleVisibleRequest$i7aWmxVK8GGR464ms_cqfIN7ou8(this, bundle));
        }

        public /* synthetic */ void lambda$onTimeout$2$RoleControllerManager$IsRoleVisibleRequest() {
            this.mCallback.accept(false);
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mExecutor.execute(new _$$Lambda$RoleControllerManager$IsRoleVisibleRequest$mPvdI6Jc9sQbLKyjDLv3TR6mmlM(this));
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).isRoleVisible(this.mRoleName, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling isRoleVisible()", remoteException);
            }
        }
    }

    private static final class OnAddRoleHolderRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnAddRoleHolderRequest(RemoteService remoteService, String string2, String string3, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
            super(remoteService);
            this.mRoleName = string2;
            this.mPackageName = string3;
            this.mFlags = n;
            this.mCallback = remoteCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$OnAddRoleHolderRequest$JT1k7eyE31b1Ili2aD3HPTU4d_Y(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$OnAddRoleHolderRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                this.mCallback.sendResult(bundle);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult(null);
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).onAddRoleHolder(this.mRoleName, this.mPackageName, this.mFlags, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling onAddRoleHolder()", remoteException);
            }
        }
    }

    private static final class OnClearRoleHoldersRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnClearRoleHoldersRequest(RemoteService remoteService, String string2, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
            super(remoteService);
            this.mRoleName = string2;
            this.mFlags = n;
            this.mCallback = remoteCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$OnClearRoleHoldersRequest$WFtkA3AVOOzGz5tXwMpks5Iic_o(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$OnClearRoleHoldersRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                this.mCallback.sendResult(bundle);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult(null);
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).onClearRoleHolders(this.mRoleName, this.mFlags, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling onClearRoleHolders()", remoteException);
            }
        }
    }

    private static final class OnRemoveRoleHolderRequest
    extends AbstractRemoteService.PendingRequest<RemoteService, IRoleController> {
        private final RemoteCallback mCallback;
        @RoleManager.ManageHoldersFlags
        private final int mFlags;
        private final String mPackageName;
        private final RemoteCallback mRemoteCallback;
        private final String mRoleName;

        private OnRemoveRoleHolderRequest(RemoteService remoteService, String string2, String string3, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
            super(remoteService);
            this.mRoleName = string2;
            this.mPackageName = string3;
            this.mFlags = n;
            this.mCallback = remoteCallback;
            this.mRemoteCallback = new RemoteCallback(new _$$Lambda$RoleControllerManager$OnRemoveRoleHolderRequest$LtJIC2bE0p8jKF_FXl69Scqp5HE(this));
        }

        public /* synthetic */ void lambda$new$0$RoleControllerManager$OnRemoveRoleHolderRequest(Bundle bundle) {
            long l = Binder.clearCallingIdentity();
            try {
                this.mCallback.sendResult(bundle);
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
                this.finish();
            }
        }

        @Override
        protected void onTimeout(RemoteService remoteService) {
            this.mCallback.sendResult(null);
        }

        @Override
        public void run() {
            try {
                ((IRoleController)((RemoteService)this.getService()).getServiceInterface()).onRemoveRoleHolder(this.mRoleName, this.mPackageName, this.mFlags, this.mRemoteCallback);
            }
            catch (RemoteException remoteException) {
                Log.e(LOG_TAG, "Error calling onRemoveRoleHolder()", remoteException);
            }
        }
    }

    private static final class RemoteService
    extends AbstractMultiplePendingRequestsRemoteService<RemoteService, IRoleController> {
        private static final long REQUEST_TIMEOUT_MILLIS = 15000L;
        private static final long UNBIND_DELAY_MILLIS = 15000L;

        RemoteService(Context context, ComponentName componentName, Handler handler, int n) {
            super(context, "android.app.role.RoleControllerService", componentName, n, _$$Lambda$RoleControllerManager$RemoteService$45dMO3SdHJhfBB_YKrC44Sznmoo.INSTANCE, handler, 0, false, 1);
        }

        static /* synthetic */ void lambda$new$0(RemoteService remoteService) {
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RemoteService ");
            stringBuilder.append(remoteService);
            stringBuilder.append(" died");
            Log.e(string2, stringBuilder.toString());
        }

        public Handler getHandler() {
            return this.mHandler;
        }

        @Override
        protected long getRemoteRequestMillis() {
            return 15000L;
        }

        @Override
        protected IRoleController getServiceInterface(IBinder iBinder) {
            return IRoleController.Stub.asInterface(iBinder);
        }

        @Override
        protected long getTimeoutIdleBindMillis() {
            return 15000L;
        }

        @Override
        public void scheduleAsyncRequest(AbstractRemoteService.AsyncRequest<IRoleController> asyncRequest) {
            super.scheduleAsyncRequest(asyncRequest);
        }

        @Override
        public void scheduleRequest(AbstractRemoteService.BasePendingRequest<RemoteService, IRoleController> basePendingRequest) {
            super.scheduleRequest(basePendingRequest);
        }
    }

}

