/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.os.RemoteException;
import com.android.internal.annotations.Immutable;
import com.android.server.SystemConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SystemApi
public final class PermissionManager {
    public static final ArrayList<SplitPermissionInfo> SPLIT_PERMISSIONS = SystemConfig.getInstance().getSplitPermissions();
    private final Context mContext;
    private final IPackageManager mPackageManager;

    public PermissionManager(Context context, IPackageManager iPackageManager) {
        this.mContext = context;
        this.mPackageManager = iPackageManager;
    }

    @SystemApi
    public int getRuntimePermissionsVersion() {
        try {
            int n = this.mPackageManager.getRuntimePermissionsVersion(this.mContext.getUserId());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<SplitPermissionInfo> getSplitPermissions() {
        return SPLIT_PERMISSIONS;
    }

    @SystemApi
    public void setRuntimePermissionsVersion(int n) {
        try {
            this.mPackageManager.setRuntimePermissionsVersion(n, this.mContext.getUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Immutable
    public static final class SplitPermissionInfo {
        private final List<String> mNewPerms;
        private final String mSplitPerm;
        private final int mTargetSdk;

        public SplitPermissionInfo(String string2, List<String> list, int n) {
            this.mSplitPerm = string2;
            this.mNewPerms = list;
            this.mTargetSdk = n;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (SplitPermissionInfo)object;
                if (this.mTargetSdk != ((SplitPermissionInfo)object).mTargetSdk || !this.mSplitPerm.equals(((SplitPermissionInfo)object).mSplitPerm) || !this.mNewPerms.equals(((SplitPermissionInfo)object).mNewPerms)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public List<String> getNewPermissions() {
            return this.mNewPerms;
        }

        public String getSplitPermission() {
            return this.mSplitPerm;
        }

        public int getTargetSdk() {
            return this.mTargetSdk;
        }

        public int hashCode() {
            return Objects.hash(this.mSplitPerm, this.mNewPerms, this.mTargetSdk);
        }
    }

}

