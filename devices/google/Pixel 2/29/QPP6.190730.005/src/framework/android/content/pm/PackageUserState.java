/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.SuspendDialogInfo;
import android.os.BaseBundle;
import android.os.PersistableBundle;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.Arrays;
import java.util.Objects;

public class PackageUserState {
    private static final boolean DEBUG = false;
    private static final String LOG_TAG = "PackageUserState";
    public int appLinkGeneration;
    public int categoryHint = -1;
    public long ceDataInode;
    public SuspendDialogInfo dialogInfo;
    public ArraySet<String> disabledComponents;
    public int distractionFlags;
    public int domainVerificationStatus;
    public int enabled;
    public ArraySet<String> enabledComponents;
    public String harmfulAppWarning;
    public boolean hidden;
    public int installReason;
    public boolean installed;
    public boolean instantApp;
    public String lastDisableAppCaller;
    public boolean notLaunched;
    public String[] overlayPaths;
    public boolean stopped;
    public boolean suspended;
    public PersistableBundle suspendedAppExtras;
    public PersistableBundle suspendedLauncherExtras;
    public String suspendingPackage;
    public boolean virtualPreload;

    @UnsupportedAppUsage
    public PackageUserState() {
        this.installed = true;
        this.hidden = false;
        this.suspended = false;
        this.enabled = 0;
        this.domainVerificationStatus = 0;
        this.installReason = 0;
    }

    @VisibleForTesting
    public PackageUserState(PackageUserState packageUserState) {
        this.ceDataInode = packageUserState.ceDataInode;
        this.installed = packageUserState.installed;
        this.stopped = packageUserState.stopped;
        this.notLaunched = packageUserState.notLaunched;
        this.hidden = packageUserState.hidden;
        this.distractionFlags = packageUserState.distractionFlags;
        this.suspended = packageUserState.suspended;
        this.suspendingPackage = packageUserState.suspendingPackage;
        this.dialogInfo = packageUserState.dialogInfo;
        this.suspendedAppExtras = packageUserState.suspendedAppExtras;
        this.suspendedLauncherExtras = packageUserState.suspendedLauncherExtras;
        this.instantApp = packageUserState.instantApp;
        this.virtualPreload = packageUserState.virtualPreload;
        this.enabled = packageUserState.enabled;
        this.lastDisableAppCaller = packageUserState.lastDisableAppCaller;
        this.domainVerificationStatus = packageUserState.domainVerificationStatus;
        this.appLinkGeneration = packageUserState.appLinkGeneration;
        this.categoryHint = packageUserState.categoryHint;
        this.installReason = packageUserState.installReason;
        this.disabledComponents = ArrayUtils.cloneOrNull(packageUserState.disabledComponents);
        this.enabledComponents = ArrayUtils.cloneOrNull(packageUserState.enabledComponents);
        Object object = packageUserState.overlayPaths;
        object = object == null ? null : Arrays.copyOf(object, ((String[])object).length);
        this.overlayPaths = object;
        this.harmfulAppWarning = packageUserState.harmfulAppWarning;
    }

    private boolean reportIfDebug(boolean bl, int n) {
        return bl;
    }

    public final boolean equals(Object object) {
        ArraySet<String> arraySet;
        int n;
        if (!(object instanceof PackageUserState)) {
            return false;
        }
        object = (PackageUserState)object;
        if (this.ceDataInode != ((PackageUserState)object).ceDataInode) {
            return false;
        }
        if (this.installed != ((PackageUserState)object).installed) {
            return false;
        }
        if (this.stopped != ((PackageUserState)object).stopped) {
            return false;
        }
        if (this.notLaunched != ((PackageUserState)object).notLaunched) {
            return false;
        }
        if (this.hidden != ((PackageUserState)object).hidden) {
            return false;
        }
        if (this.distractionFlags != ((PackageUserState)object).distractionFlags) {
            return false;
        }
        boolean bl = this.suspended;
        if (bl != ((PackageUserState)object).suspended) {
            return false;
        }
        if (bl) {
            arraySet = this.suspendingPackage;
            if (arraySet != null && ((String)((Object)arraySet)).equals(((PackageUserState)object).suspendingPackage)) {
                if (!Objects.equals(this.dialogInfo, ((PackageUserState)object).dialogInfo)) {
                    return false;
                }
                if (!BaseBundle.kindofEquals(this.suspendedAppExtras, ((PackageUserState)object).suspendedAppExtras)) {
                    return false;
                }
                if (!BaseBundle.kindofEquals(this.suspendedLauncherExtras, ((PackageUserState)object).suspendedLauncherExtras)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        if (this.instantApp != ((PackageUserState)object).instantApp) {
            return false;
        }
        if (this.virtualPreload != ((PackageUserState)object).virtualPreload) {
            return false;
        }
        if (this.enabled != ((PackageUserState)object).enabled) {
            return false;
        }
        if (this.lastDisableAppCaller == null && ((PackageUserState)object).lastDisableAppCaller != null || (arraySet = this.lastDisableAppCaller) != null && !((String)((Object)arraySet)).equals(((PackageUserState)object).lastDisableAppCaller)) {
            return false;
        }
        if (this.domainVerificationStatus != ((PackageUserState)object).domainVerificationStatus) {
            return false;
        }
        if (this.appLinkGeneration != ((PackageUserState)object).appLinkGeneration) {
            return false;
        }
        if (this.categoryHint != ((PackageUserState)object).categoryHint) {
            return false;
        }
        if (this.installReason != ((PackageUserState)object).installReason) {
            return false;
        }
        if (this.disabledComponents == null && ((PackageUserState)object).disabledComponents != null || this.disabledComponents != null && ((PackageUserState)object).disabledComponents == null) {
            return false;
        }
        arraySet = this.disabledComponents;
        if (arraySet != null) {
            if (arraySet.size() != ((PackageUserState)object).disabledComponents.size()) {
                return false;
            }
            for (n = this.disabledComponents.size() - 1; n >= 0; --n) {
                if (((PackageUserState)object).disabledComponents.contains(this.disabledComponents.valueAt(n))) continue;
                return false;
            }
        }
        if (this.enabledComponents == null && ((PackageUserState)object).enabledComponents != null || this.enabledComponents != null && ((PackageUserState)object).enabledComponents == null) {
            return false;
        }
        arraySet = this.enabledComponents;
        if (arraySet != null) {
            if (arraySet.size() != ((PackageUserState)object).enabledComponents.size()) {
                return false;
            }
            for (n = this.enabledComponents.size() - 1; n >= 0; --n) {
                if (((PackageUserState)object).enabledComponents.contains(this.enabledComponents.valueAt(n))) continue;
                return false;
            }
        }
        return (this.harmfulAppWarning != null || ((PackageUserState)object).harmfulAppWarning == null) && ((arraySet = this.harmfulAppWarning) == null || ((String)((Object)arraySet)).equals(((PackageUserState)object).harmfulAppWarning));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isAvailable(int n) {
        boolean bl = true;
        boolean bl2 = (4194304 & n) != 0;
        n = (n & 8192) != 0 ? 1 : 0;
        boolean bl3 = bl;
        if (bl2) return bl3;
        if (!this.installed) return false;
        bl3 = bl;
        if (!this.hidden) return bl3;
        if (n == 0) return false;
        return bl;
    }

    public boolean isEnabled(ComponentInfo componentInfo, int n) {
        block7 : {
            block5 : {
                block6 : {
                    if ((n & 512) != 0) {
                        return true;
                    }
                    int n2 = this.enabled;
                    if (n2 == 0) break block5;
                    if (n2 == 2 || n2 == 3) break block6;
                    if (n2 != 4) break block7;
                    if ((32768 & n) == 0) {
                        return false;
                    }
                    break block5;
                }
                return false;
            }
            if (!componentInfo.applicationInfo.enabled) {
                return false;
            }
        }
        if (ArrayUtils.contains(this.enabledComponents, componentInfo.name)) {
            return true;
        }
        if (ArrayUtils.contains(this.disabledComponents, componentInfo.name)) {
            return false;
        }
        return componentInfo.enabled;
    }

    public boolean isMatch(ComponentInfo componentInfo, int n) {
        boolean bl = componentInfo.applicationInfo.isSystemApp();
        boolean bl2 = true;
        boolean bl3 = (4202496 & n) != 0;
        if (!(this.isAvailable(n) || bl && bl3)) {
            return this.reportIfDebug(false, n);
        }
        if (!this.isEnabled(componentInfo, n)) {
            return this.reportIfDebug(false, n);
        }
        if ((1048576 & n) != 0 && !bl) {
            return this.reportIfDebug(false, n);
        }
        bl3 = (262144 & n) != 0 && !componentInfo.directBootAware;
        boolean bl4 = (524288 & n) != 0 && componentInfo.directBootAware;
        bl = bl2;
        if (!bl3) {
            bl = bl4 ? bl2 : false;
        }
        return this.reportIfDebug(bl, n);
    }
}

