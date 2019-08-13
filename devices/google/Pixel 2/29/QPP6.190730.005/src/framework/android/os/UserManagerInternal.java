/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.os.Bundle;

public abstract class UserManagerInternal {
    public static final int CAMERA_DISABLED_GLOBALLY = 2;
    public static final int CAMERA_DISABLED_LOCALLY = 1;
    public static final int CAMERA_NOT_DISABLED = 0;

    public abstract void addUserRestrictionsListener(UserRestrictionsListener var1);

    public abstract UserInfo createUserEvenWhenDisallowed(String var1, int var2, String[] var3);

    public abstract boolean exists(int var1);

    public abstract Bundle getBaseUserRestrictions(int var1);

    public abstract int getProfileParentId(int var1);

    public abstract int[] getUserIds();

    public abstract boolean getUserRestriction(int var1, String var2);

    public abstract boolean isProfileAccessible(int var1, int var2, String var3, boolean var4);

    public abstract boolean isSettingRestrictedForUser(String var1, int var2, String var3, int var4);

    public abstract boolean isUserInitialized(int var1);

    public abstract boolean isUserRunning(int var1);

    public abstract boolean isUserUnlocked(int var1);

    public abstract boolean isUserUnlockingOrUnlocked(int var1);

    public abstract void onEphemeralUserStop(int var1);

    public abstract void removeAllUsers();

    public abstract boolean removeUserEvenWhenDisallowed(int var1);

    public abstract void removeUserRestrictionsListener(UserRestrictionsListener var1);

    public abstract void removeUserState(int var1);

    public abstract void setBaseUserRestrictionsByDpmsForMigration(int var1, Bundle var2);

    public abstract void setDeviceManaged(boolean var1);

    public abstract void setDevicePolicyUserRestrictions(int var1, Bundle var2, boolean var3, int var4);

    public abstract void setForceEphemeralUsers(boolean var1);

    public abstract void setUserIcon(int var1, Bitmap var2);

    public abstract void setUserManaged(int var1, boolean var2);

    public abstract void setUserState(int var1, int var2);

    public static interface UserRestrictionsListener {
        public void onUserRestrictionsChanged(int var1, Bundle var2, Bundle var3);
    }

}

