/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.UserManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserPackage {
    public static final int MINIMUM_SUPPORTED_SDK = 29;
    private final PackageInfo mPackageInfo;
    private final UserInfo mUserInfo;

    public UserPackage(UserInfo userInfo, PackageInfo packageInfo) {
        this.mUserInfo = userInfo;
        this.mPackageInfo = packageInfo;
    }

    private static List<UserInfo> getAllUsers(Context context) {
        return ((UserManager)context.getSystemService("user")).getUsers(false);
    }

    public static List<UserPackage> getPackageInfosAllUsers(Context context, String string2, int n) {
        Object object = UserPackage.getAllUsers(context);
        ArrayList<UserPackage> arrayList = new ArrayList<UserPackage>(object.size());
        Iterator<UserInfo> iterator = object.iterator();
        while (iterator.hasNext()) {
            UserInfo userInfo = iterator.next();
            object = null;
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfoAsUser(string2, n, userInfo.id);
                object = packageInfo;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                // empty catch block
            }
            arrayList.add(new UserPackage(userInfo, (PackageInfo)object));
        }
        return arrayList;
    }

    public static boolean hasCorrectTargetSdkVersion(PackageInfo packageInfo) {
        boolean bl = packageInfo.applicationInfo.targetSdkVersion >= 29;
        return bl;
    }

    public PackageInfo getPackageInfo() {
        return this.mPackageInfo;
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    public boolean isEnabledPackage() {
        PackageInfo packageInfo = this.mPackageInfo;
        if (packageInfo == null) {
            return false;
        }
        return packageInfo.applicationInfo.enabled;
    }

    public boolean isInstalledPackage() {
        PackageInfo packageInfo = this.mPackageInfo;
        boolean bl = false;
        if (packageInfo == null) {
            return false;
        }
        boolean bl2 = bl;
        if ((packageInfo.applicationInfo.flags & 8388608) != 0) {
            bl2 = bl;
            if ((this.mPackageInfo.applicationInfo.privateFlags & 1) == 0) {
                bl2 = true;
            }
        }
        return bl2;
    }
}

