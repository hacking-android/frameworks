/*
 * Decompiled with CFR 0.145.
 */
package android.content.rollback;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.content.pm.VersionedPackage;
import android.content.rollback.IRollbackManager;
import android.content.rollback.RollbackInfo;
import android.os.RemoteException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

@SystemApi
public final class RollbackManager {
    public static final String EXTRA_STATUS = "android.content.rollback.extra.STATUS";
    public static final String EXTRA_STATUS_MESSAGE = "android.content.rollback.extra.STATUS_MESSAGE";
    public static final String PROPERTY_ROLLBACK_LIFETIME_MILLIS = "rollback_lifetime_in_millis";
    public static final int STATUS_FAILURE = 1;
    public static final int STATUS_FAILURE_INSTALL = 3;
    public static final int STATUS_FAILURE_ROLLBACK_UNAVAILABLE = 2;
    public static final int STATUS_SUCCESS = 0;
    private final IRollbackManager mBinder;
    private final String mCallerPackageName;

    public RollbackManager(Context context, IRollbackManager iRollbackManager) {
        this.mCallerPackageName = context.getPackageName();
        this.mBinder = iRollbackManager;
    }

    public void commitRollback(int n, List<VersionedPackage> list, IntentSender intentSender) {
        try {
            IRollbackManager iRollbackManager = this.mBinder;
            ParceledListSlice<VersionedPackage> parceledListSlice = new ParceledListSlice<VersionedPackage>(list);
            iRollbackManager.commitRollback(n, parceledListSlice, this.mCallerPackageName, intentSender);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void expireRollbackForPackage(String string2) {
        try {
            this.mBinder.expireRollbackForPackage(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<RollbackInfo> getAvailableRollbacks() {
        try {
            List list = this.mBinder.getAvailableRollbacks().getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<RollbackInfo> getRecentlyCommittedRollbacks() {
        try {
            List list = this.mBinder.getRecentlyExecutedRollbacks().getList();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reloadPersistedData() {
        try {
            this.mBinder.reloadPersistedData();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Status {
    }

}

