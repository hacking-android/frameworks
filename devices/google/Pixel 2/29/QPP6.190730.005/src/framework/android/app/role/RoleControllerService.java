/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.role.-$
 *  android.app.role.-$$Lambda
 *  android.app.role.-$$Lambda$RoleControllerService
 *  android.app.role.-$$Lambda$RoleControllerService$1
 *  android.app.role.-$$Lambda$RoleControllerService$1$-fmj7uDKaG3BoLl6bhtrA675gRI
 *  android.app.role.-$$Lambda$RoleControllerService$1$PB6H1df6VvLzUJ3hhB_75mN3u7s
 *  android.app.role.-$$Lambda$RoleControllerService$1$UVI1sAWAcBnt3Enqn2IT-Lirwtk
 *  android.app.role.-$$Lambda$RoleControllerService$1$dBm1t_MGyEA9yMAxoOUMOhYVmPo
 */
package android.app.role;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.role.-$;
import android.app.role.IRoleController;
import android.app.role.RoleManager;
import android.app.role._$$Lambda$RoleControllerService$1$PB6H1df6VvLzUJ3hhB_75mN3u7s;
import android.app.role._$$Lambda$RoleControllerService$1$UVI1sAWAcBnt3Enqn2IT_Lirwtk;
import android.app.role._$$Lambda$RoleControllerService$1$_fmj7uDKaG3BoLl6bhtrA675gRI;
import android.app.role._$$Lambda$RoleControllerService$1$dBm1t_MGyEA9yMAxoOUMOhYVmPo;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteCallback;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;

@SystemApi
public abstract class RoleControllerService
extends Service {
    public static final String SERVICE_INTERFACE = "android.app.role.RoleControllerService";
    private Handler mWorkerHandler;
    private HandlerThread mWorkerThread;

    private void grantDefaultRoles(RemoteCallback remoteCallback) {
        Bundle bundle = this.onGrantDefaultRoles() ? Bundle.EMPTY : null;
        remoteCallback.sendResult(bundle);
    }

    private void onAddRoleHolder(String object, String string2, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        object = this.onAddRoleHolder((String)object, string2, n) ? Bundle.EMPTY : null;
        remoteCallback.sendResult((Bundle)object);
    }

    private void onClearRoleHolders(String object, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        object = this.onClearRoleHolders((String)object, n) ? Bundle.EMPTY : null;
        remoteCallback.sendResult((Bundle)object);
    }

    private void onRemoveRoleHolder(String object, String string2, @RoleManager.ManageHoldersFlags int n, RemoteCallback remoteCallback) {
        object = this.onRemoveRoleHolder((String)object, string2, n) ? Bundle.EMPTY : null;
        remoteCallback.sendResult((Bundle)object);
    }

    public abstract boolean onAddRoleHolder(String var1, String var2, @RoleManager.ManageHoldersFlags int var3);

    @Override
    public final IBinder onBind(Intent intent) {
        return new IRoleController.Stub(){

            private void enforceCallerSystemUid(String string2) {
                if (Binder.getCallingUid() == 1000) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Only the system process can call ");
                stringBuilder.append(string2);
                stringBuilder.append("()");
                throw new SecurityException(stringBuilder.toString());
            }

            static /* synthetic */ void lambda$grantDefaultRoles$0(Object object, RemoteCallback remoteCallback) {
                ((RoleControllerService)object).grantDefaultRoles(remoteCallback);
            }

            static /* synthetic */ void lambda$onAddRoleHolder$1(Object object, String string2, String string3, int n, RemoteCallback remoteCallback) {
                ((RoleControllerService)object).onAddRoleHolder(string2, string3, n, remoteCallback);
            }

            static /* synthetic */ void lambda$onClearRoleHolders$3(Object object, String string2, int n, RemoteCallback remoteCallback) {
                ((RoleControllerService)object).onClearRoleHolders(string2, n, remoteCallback);
            }

            static /* synthetic */ void lambda$onRemoveRoleHolder$2(Object object, String string2, String string3, int n, RemoteCallback remoteCallback) {
                ((RoleControllerService)object).onRemoveRoleHolder(string2, string3, n, remoteCallback);
            }

            @Override
            public void grantDefaultRoles(RemoteCallback remoteCallback) {
                this.enforceCallerSystemUid("grantDefaultRoles");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                RoleControllerService.this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RoleControllerService$1$_fmj7uDKaG3BoLl6bhtrA675gRI.INSTANCE, RoleControllerService.this, remoteCallback));
            }

            @Override
            public void isApplicationQualifiedForRole(String string2, String string3, RemoteCallback remoteCallback) {
                RoleControllerService roleControllerService = RoleControllerService.this;
                Bundle bundle = null;
                roleControllerService.enforceCallingPermission("android.permission.MANAGE_ROLE_HOLDERS", null);
                Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
                Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                if (RoleControllerService.this.onIsApplicationQualifiedForRole(string2, string3)) {
                    bundle = Bundle.EMPTY;
                }
                remoteCallback.sendResult(bundle);
            }

            @Override
            public void isRoleVisible(String string2, RemoteCallback remoteCallback) {
                RoleControllerService roleControllerService = RoleControllerService.this;
                Bundle bundle = null;
                roleControllerService.enforceCallingPermission("android.permission.MANAGE_ROLE_HOLDERS", null);
                Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                if (RoleControllerService.this.onIsRoleVisible(string2)) {
                    bundle = Bundle.EMPTY;
                }
                remoteCallback.sendResult(bundle);
            }

            @Override
            public void onAddRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) {
                this.enforceCallerSystemUid("onAddRoleHolder");
                Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
                Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                RoleControllerService.this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RoleControllerService$1$UVI1sAWAcBnt3Enqn2IT_Lirwtk.INSTANCE, RoleControllerService.this, string2, string3, n, remoteCallback));
            }

            @Override
            public void onClearRoleHolders(String string2, int n, RemoteCallback remoteCallback) {
                this.enforceCallerSystemUid("onClearRoleHolders");
                Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                RoleControllerService.this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RoleControllerService$1$dBm1t_MGyEA9yMAxoOUMOhYVmPo.INSTANCE, RoleControllerService.this, string2, n, remoteCallback));
            }

            @Override
            public void onRemoveRoleHolder(String string2, String string3, int n, RemoteCallback remoteCallback) {
                this.enforceCallerSystemUid("onRemoveRoleHolder");
                Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
                Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
                Preconditions.checkNotNull(remoteCallback, "callback cannot be null");
                RoleControllerService.this.mWorkerHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$RoleControllerService$1$PB6H1df6VvLzUJ3hhB_75mN3u7s.INSTANCE, RoleControllerService.this, string2, string3, n, remoteCallback));
            }
        };
    }

    public abstract boolean onClearRoleHolders(String var1, @RoleManager.ManageHoldersFlags int var2);

    @Override
    public void onCreate() {
        super.onCreate();
        this.mWorkerThread = new HandlerThread(RoleControllerService.class.getSimpleName());
        this.mWorkerThread.start();
        this.mWorkerHandler = new Handler(this.mWorkerThread.getLooper());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mWorkerThread.quitSafely();
    }

    public abstract boolean onGrantDefaultRoles();

    public abstract boolean onIsApplicationQualifiedForRole(String var1, String var2);

    public abstract boolean onIsRoleVisible(String var1);

    public abstract boolean onRemoveRoleHolder(String var1, String var2, @RoleManager.ManageHoldersFlags int var3);

}

