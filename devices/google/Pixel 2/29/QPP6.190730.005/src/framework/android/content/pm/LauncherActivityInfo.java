/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;

public class LauncherActivityInfo {
    private static final String TAG = "LauncherActivityInfo";
    @UnsupportedAppUsage
    private ActivityInfo mActivityInfo;
    private ComponentName mComponentName;
    private final PackageManager mPm;
    private UserHandle mUser;

    LauncherActivityInfo(Context context) {
        this.mPm = context.getPackageManager();
    }

    LauncherActivityInfo(Context context, ActivityInfo activityInfo, UserHandle userHandle) {
        this(context);
        this.mActivityInfo = activityInfo;
        this.mComponentName = new ComponentName(activityInfo.packageName, activityInfo.name);
        this.mUser = userHandle;
    }

    public int getApplicationFlags() {
        return this.mActivityInfo.applicationInfo.flags;
    }

    public ApplicationInfo getApplicationInfo() {
        return this.mActivityInfo.applicationInfo;
    }

    public Drawable getBadgedIcon(int n) {
        Drawable drawable2 = this.getIcon(n);
        return this.mPm.getUserBadgedIcon(drawable2, this.mUser);
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public long getFirstInstallTime() {
        try {
            long l = this.mPm.getPackageInfo((String)this.mActivityInfo.packageName, (int)8192).firstInstallTime;
            return l;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return 0L;
        }
    }

    public Drawable getIcon(int n) {
        Drawable drawable2;
        int n2 = this.mActivityInfo.getIconResource();
        Drawable drawable3 = drawable2 = null;
        if (n != 0) {
            drawable3 = drawable2;
            if (n2 != 0) {
                try {
                    drawable3 = this.mPm.getResourcesForApplication(this.mActivityInfo.applicationInfo).getDrawableForDensity(n2, n);
                }
                catch (PackageManager.NameNotFoundException | Resources.NotFoundException exception) {
                    drawable3 = drawable2;
                }
            }
        }
        drawable2 = drawable3;
        if (drawable3 == null) {
            drawable2 = this.mActivityInfo.loadIcon(this.mPm);
        }
        return drawable2;
    }

    public CharSequence getLabel() {
        return this.mActivityInfo.loadLabel(this.mPm);
    }

    public String getName() {
        return this.mActivityInfo.name;
    }

    public UserHandle getUser() {
        return this.mUser;
    }
}

