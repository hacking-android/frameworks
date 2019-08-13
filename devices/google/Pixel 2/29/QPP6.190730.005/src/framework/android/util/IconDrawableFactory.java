/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.LauncherIcons;
import com.android.internal.annotations.VisibleForTesting;

public class IconDrawableFactory {
    @VisibleForTesting
    public static final int[] CORP_BADGE_COLORS = new int[]{17170934, 17170935, 17170936};
    protected final Context mContext;
    protected final boolean mEmbedShadow;
    protected final LauncherIcons mLauncherIcons;
    protected final PackageManager mPm;
    protected final UserManager mUm;

    private IconDrawableFactory(Context context, boolean bl) {
        this.mContext = context;
        this.mPm = context.getPackageManager();
        this.mUm = context.getSystemService(UserManager.class);
        this.mLauncherIcons = new LauncherIcons(context);
        this.mEmbedShadow = bl;
    }

    public static int getUserBadgeColor(UserManager arrn, int n) {
        int n2;
        n = n2 = arrn.getManagedProfileBadge(n);
        if (n2 < 0) {
            n = 0;
        }
        arrn = CORP_BADGE_COLORS;
        n = arrn[n % arrn.length];
        return Resources.getSystem().getColor(n, null);
    }

    @UnsupportedAppUsage
    public static IconDrawableFactory newInstance(Context context) {
        return new IconDrawableFactory(context, true);
    }

    public static IconDrawableFactory newInstance(Context context, boolean bl) {
        return new IconDrawableFactory(context, bl);
    }

    @UnsupportedAppUsage
    public Drawable getBadgedIcon(ApplicationInfo applicationInfo) {
        return this.getBadgedIcon(applicationInfo, UserHandle.getUserId(applicationInfo.uid));
    }

    public Drawable getBadgedIcon(ApplicationInfo applicationInfo, int n) {
        return this.getBadgedIcon(applicationInfo, applicationInfo, n);
    }

    @UnsupportedAppUsage
    public Drawable getBadgedIcon(PackageItemInfo object, ApplicationInfo object2, int n) {
        object = this.mPm.loadUnbadgedItemIcon((PackageItemInfo)object, (ApplicationInfo)object2);
        if (!this.mEmbedShadow && !this.needsBadging((ApplicationInfo)object2, n)) {
            return object;
        }
        Drawable drawable2 = this.getShadowedIcon((Drawable)object);
        object = drawable2;
        if (((ApplicationInfo)object2).isInstantApp()) {
            int n2 = Resources.getSystem().getColor(17170808, null);
            object = this.mLauncherIcons.getBadgedDrawable(drawable2, 17302435, n2);
        }
        object2 = object;
        if (this.mUm.isManagedProfile(n)) {
            object2 = this.mLauncherIcons.getBadgedDrawable((Drawable)object, 17302366, IconDrawableFactory.getUserBadgeColor(this.mUm, n));
        }
        return object2;
    }

    public Drawable getShadowedIcon(Drawable drawable2) {
        return this.mLauncherIcons.wrapIconDrawableWithShadow(drawable2);
    }

    protected boolean needsBadging(ApplicationInfo applicationInfo, int n) {
        boolean bl = applicationInfo.isInstantApp() || this.mUm.isManagedProfile(n);
        return bl;
    }
}

