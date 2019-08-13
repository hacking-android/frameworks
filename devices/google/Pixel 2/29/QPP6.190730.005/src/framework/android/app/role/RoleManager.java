/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.role.-$
 *  android.app.role.-$$Lambda
 *  android.app.role.-$$Lambda$o94o2jK_ei-IVw-3oY_QJ49zpAA
 */
package android.app.role;

import android.annotation.SystemApi;
import android.app.role.-$;
import android.app.role.IOnRoleHoldersChangedListener;
import android.app.role.IRoleManager;
import android.app.role.OnRoleHoldersChangedListener;
import android.app.role._$$Lambda$RoleManager$DrSVQgbDoLZaqkfPdGzAK3BvOGQ;
import android.app.role._$$Lambda$RoleManager$m9y_ZqrQy4gHK3mGDXvG129sdjU;
import android.app.role._$$Lambda$o94o2jK_ei_IVw_3oY_QJ49zpAA;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class RoleManager {
    public static final String ACTION_REQUEST_ROLE = "android.app.role.action.REQUEST_ROLE";
    private static final String LOG_TAG = RoleManager.class.getSimpleName();
    @SystemApi
    public static final int MANAGE_HOLDERS_FLAG_DONT_KILL_APP = 1;
    public static final String PERMISSION_MANAGE_ROLES_FROM_CONTROLLER = "com.android.permissioncontroller.permission.MANAGE_ROLES_FROM_CONTROLLER";
    public static final String ROLE_ASSISTANT = "android.app.role.ASSISTANT";
    public static final String ROLE_BROWSER = "android.app.role.BROWSER";
    public static final String ROLE_CALL_REDIRECTION = "android.app.role.CALL_REDIRECTION";
    public static final String ROLE_CALL_SCREENING = "android.app.role.CALL_SCREENING";
    public static final String ROLE_DIALER = "android.app.role.DIALER";
    public static final String ROLE_EMERGENCY = "android.app.role.EMERGENCY";
    public static final String ROLE_HOME = "android.app.role.HOME";
    public static final String ROLE_SMS = "android.app.role.SMS";
    private final Context mContext;
    @GuardedBy(value={"mListenersLock"})
    private final SparseArray<ArrayMap<OnRoleHoldersChangedListener, OnRoleHoldersChangedListenerDelegate>> mListeners = new SparseArray();
    private final Object mListenersLock = new Object();
    private final IRoleManager mService;

    public RoleManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mService = IRoleManager.Stub.asInterface(ServiceManager.getServiceOrThrow("role"));
    }

    private static RemoteCallback createRemoteCallback(Executor executor, Consumer<Boolean> consumer) {
        return new RemoteCallback(new _$$Lambda$RoleManager$m9y_ZqrQy4gHK3mGDXvG129sdjU(executor, consumer));
    }

    static /* synthetic */ void lambda$createRemoteCallback$0(Bundle bundle, Consumer consumer) {
        boolean bl = bundle != null;
        long l = Binder.clearCallingIdentity();
        try {
            consumer.accept(bl);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    static /* synthetic */ void lambda$createRemoteCallback$1(Executor executor, Consumer consumer, Bundle bundle) {
        executor.execute(new _$$Lambda$RoleManager$DrSVQgbDoLZaqkfPdGzAK3BvOGQ(bundle, consumer));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void addOnRoleHoldersChangedListenerAsUser(Executor executor, OnRoleHoldersChangedListener onRoleHoldersChangedListener, UserHandle arrayMap) {
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(onRoleHoldersChangedListener, "listener cannot be null");
        Preconditions.checkNotNull(arrayMap, "user cannot be null");
        int n = ((UserHandle)((Object)arrayMap)).getIdentifier();
        Object object = this.mListenersLock;
        synchronized (object) {
            Object object2 = this.mListeners.get(n);
            if (object2 == null) {
                arrayMap = new ArrayMap<OnRoleHoldersChangedListener, OnRoleHoldersChangedListenerDelegate>();
                this.mListeners.put(n, arrayMap);
            } else {
                arrayMap = object2;
                if (((ArrayMap)object2).containsKey(onRoleHoldersChangedListener)) {
                    return;
                }
            }
            object2 = new OnRoleHoldersChangedListenerDelegate(executor, onRoleHoldersChangedListener);
            try {
                this.mService.addOnRoleHoldersChangedListenerAsUser((IOnRoleHoldersChangedListener)object2, n);
                arrayMap.put(onRoleHoldersChangedListener, (OnRoleHoldersChangedListenerDelegate)object2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return;
        }
    }

    @SystemApi
    public void addRoleHolderAsUser(String string2, String string3, @ManageHoldersFlags int n, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
        Preconditions.checkNotNull(userHandle, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "callback cannot be null");
        try {
            this.mService.addRoleHolderAsUser(string2, string3, n, userHandle.getIdentifier(), RoleManager.createRemoteCallback(executor, consumer));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean addRoleHolderFromController(String string2, String string3) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
        try {
            boolean bl = this.mService.addRoleHolderFromController(string2, string3);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void clearRoleHoldersAsUser(String string2, @ManageHoldersFlags int n, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Preconditions.checkNotNull(userHandle, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "callback cannot be null");
        try {
            this.mService.clearRoleHoldersAsUser(string2, n, userHandle.getIdentifier(), RoleManager.createRemoteCallback(executor, consumer));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Intent createRequestRoleIntent(String string2) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Intent intent = new Intent(ACTION_REQUEST_ROLE);
        intent.setPackage(this.mContext.getPackageManager().getPermissionControllerPackageName());
        intent.putExtra("android.intent.extra.ROLE_NAME", string2);
        return intent;
    }

    public String getDefaultSmsPackage(int n) {
        try {
            String string2 = this.mService.getDefaultSmsPackage(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getHeldRolesFromController(String object) {
        Preconditions.checkStringNotEmpty(object, "packageName cannot be null or empty");
        try {
            object = this.mService.getHeldRolesFromController((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getRoleHolders(String string2) {
        return this.getRoleHoldersAsUser(string2, Process.myUserHandle());
    }

    @SystemApi
    public List<String> getRoleHoldersAsUser(String object, UserHandle userHandle) {
        Preconditions.checkStringNotEmpty(object, "roleName cannot be null or empty");
        Preconditions.checkNotNull(userHandle, "user cannot be null");
        try {
            object = this.mService.getRoleHoldersAsUser((String)object, userHandle.getIdentifier());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRoleAvailable(String string2) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        try {
            boolean bl = this.mService.isRoleAvailable(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRoleHeld(String string2) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        try {
            boolean bl = this.mService.isRoleHeld(string2, this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SystemApi
    public void removeOnRoleHoldersChangedListenerAsUser(OnRoleHoldersChangedListener onRoleHoldersChangedListener, UserHandle userHandle) {
        Preconditions.checkNotNull(onRoleHoldersChangedListener, "listener cannot be null");
        Preconditions.checkNotNull(userHandle, "user cannot be null");
        int n = userHandle.getIdentifier();
        Object object = this.mListenersLock;
        synchronized (object) {
            ArrayMap<OnRoleHoldersChangedListener, OnRoleHoldersChangedListenerDelegate> arrayMap = this.mListeners.get(n);
            if (arrayMap == null) {
                return;
            }
            OnRoleHoldersChangedListenerDelegate onRoleHoldersChangedListenerDelegate = arrayMap.get(onRoleHoldersChangedListener);
            if (onRoleHoldersChangedListenerDelegate == null) {
                return;
            }
            try {
                this.mService.removeOnRoleHoldersChangedListenerAsUser(onRoleHoldersChangedListenerDelegate, userHandle.getIdentifier());
                arrayMap.remove(onRoleHoldersChangedListener);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            if (arrayMap.isEmpty()) {
                this.mListeners.remove(n);
            }
            return;
        }
    }

    @SystemApi
    public void removeRoleHolderAsUser(String string2, String string3, @ManageHoldersFlags int n, UserHandle userHandle, Executor executor, Consumer<Boolean> consumer) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
        Preconditions.checkNotNull(userHandle, "user cannot be null");
        Preconditions.checkNotNull(executor, "executor cannot be null");
        Preconditions.checkNotNull(consumer, "callback cannot be null");
        try {
            this.mService.removeRoleHolderAsUser(string2, string3, n, userHandle.getIdentifier(), RoleManager.createRemoteCallback(executor, consumer));
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeRoleHolderFromController(String string2, String string3) {
        Preconditions.checkStringNotEmpty(string2, "roleName cannot be null or empty");
        Preconditions.checkStringNotEmpty(string3, "packageName cannot be null or empty");
        try {
            boolean bl = this.mService.removeRoleHolderFromController(string2, string3);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setRoleNamesFromController(List<String> list) {
        Preconditions.checkNotNull(list, "roleNames cannot be null");
        try {
            this.mService.setRoleNamesFromController(list);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static @interface ManageHoldersFlags {
    }

    private static class OnRoleHoldersChangedListenerDelegate
    extends IOnRoleHoldersChangedListener.Stub {
        private final Executor mExecutor;
        private final OnRoleHoldersChangedListener mListener;

        OnRoleHoldersChangedListenerDelegate(Executor executor, OnRoleHoldersChangedListener onRoleHoldersChangedListener) {
            this.mExecutor = executor;
            this.mListener = onRoleHoldersChangedListener;
        }

        @Override
        public void onRoleHoldersChanged(String string2, int n) {
            long l = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(PooledLambda.obtainRunnable(_$$Lambda$o94o2jK_ei_IVw_3oY_QJ49zpAA.INSTANCE, this.mListener, string2, UserHandle.of(n)));
                return;
            }
            finally {
                Binder.restoreCallingIdentity(l);
            }
        }
    }

}

