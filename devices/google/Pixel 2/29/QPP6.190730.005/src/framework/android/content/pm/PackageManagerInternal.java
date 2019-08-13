/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.AuxiliaryResolveInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageList;
import android.content.pm.PackageParser;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.pm.SuspendDialogInfo;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.SparseArray;
import com.android.internal.util.function.TriFunction;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public abstract class PackageManagerInternal {
    public static final int ENABLE_ROLLBACK_FAILED = -1;
    public static final int ENABLE_ROLLBACK_SUCCEEDED = 1;
    public static final String EXTRA_ENABLE_ROLLBACK_INSTALLED_USERS = "android.content.pm.extra.ENABLE_ROLLBACK_INSTALLED_USERS";
    public static final String EXTRA_ENABLE_ROLLBACK_INSTALL_FLAGS = "android.content.pm.extra.ENABLE_ROLLBACK_INSTALL_FLAGS";
    public static final String EXTRA_ENABLE_ROLLBACK_TOKEN = "android.content.pm.extra.ENABLE_ROLLBACK_TOKEN";
    public static final String EXTRA_ENABLE_ROLLBACK_USER = "android.content.pm.extra.ENABLE_ROLLBACK_USER";
    public static final int PACKAGE_APP_PREDICTOR = 11;
    public static final int PACKAGE_BROWSER = 4;
    public static final int PACKAGE_CONFIGURATOR = 9;
    public static final int PACKAGE_DOCUMENTER = 8;
    public static final int PACKAGE_INCIDENT_REPORT_APPROVER = 10;
    public static final int PACKAGE_INSTALLER = 2;
    public static final int PACKAGE_PERMISSION_CONTROLLER = 6;
    public static final int PACKAGE_SETUP_WIZARD = 1;
    public static final int PACKAGE_SYSTEM = 0;
    public static final int PACKAGE_SYSTEM_TEXT_CLASSIFIER = 5;
    public static final int PACKAGE_VERIFIER = 3;
    public static final int PACKAGE_WELLBEING = 7;

    public abstract void addIsolatedUid(int var1, int var2);

    public abstract boolean canAccessComponent(int var1, ComponentName var2, int var3);

    public abstract boolean canAccessInstantApps(int var1, int var2);

    public abstract boolean compileLayouts(String var1);

    public abstract boolean filterAppAccess(PackageParser.Package var1, int var2, int var3);

    public abstract void finishPackageInstall(int var1, boolean var2);

    public abstract void forEachInstalledPackage(Consumer<PackageParser.Package> var1, int var2);

    public abstract void forEachPackage(Consumer<PackageParser.Package> var1);

    public abstract void freeStorage(String var1, long var2, int var4) throws IOException;

    public abstract ActivityInfo getActivityInfo(ComponentName var1, int var2, int var3, int var4);

    public abstract int getApplicationEnabledState(String var1, int var2);

    public abstract ApplicationInfo getApplicationInfo(String var1, int var2, int var3, int var4);

    public abstract SparseArray<String> getAppsWithSharedUserIds();

    public abstract CheckPermissionDelegate getCheckPermissionDelegate();

    public abstract ComponentName getDefaultHomeActivity(int var1);

    public abstract ArraySet<String> getDisabledComponents(String var1, int var2);

    public abstract PackageParser.Package getDisabledSystemPackage(String var1);

    public abstract String getDisabledSystemPackageName(String var1);

    public abstract int getDistractingPackageRestrictions(String var1, int var2);

    public abstract ArraySet<String> getEnabledComponents(String var1, int var2);

    public abstract ComponentName getHomeActivitiesAsUser(List<ResolveInfo> var1, int var2);

    public abstract List<ApplicationInfo> getInstalledApplications(int var1, int var2, int var3);

    public abstract String getInstantAppPackageName(int var1);

    public abstract String getKnownPackageName(int var1, int var2);

    public abstract String getNameForUid(int var1);

    public abstract List<PackageInfo> getOverlayPackages(int var1);

    public abstract PackageParser.Package getPackage(String var1);

    public abstract PackageInfo getPackageInfo(String var1, int var2, int var3, int var4);

    public PackageList getPackageList() {
        return this.getPackageList(null);
    }

    public abstract PackageList getPackageList(PackageListObserver var1);

    public abstract int getPackageTargetSdkVersion(String var1);

    public abstract int getPackageUid(String var1, int var2, int var3);

    public abstract String[] getPackagesForSharedUserId(String var1, int var2);

    public abstract int getPermissionFlagsTEMP(String var1, String var2, int var3);

    public abstract String getSetupWizardPackageName();

    public abstract String getSharedUserIdForPackage(String var1);

    public abstract SuspendDialogInfo getSuspendedDialogInfo(String var1, int var2);

    public abstract Bundle getSuspendedPackageLauncherExtras(String var1, int var2);

    public abstract String getSuspendingPackage(String var1, int var2);

    public abstract List<String> getTargetPackageNames(int var1);

    public abstract int getUidTargetSdkVersion(int var1);

    public abstract void grantDefaultPermissionsToDefaultUseOpenWifiApp(String var1, int var2);

    public abstract void grantEphemeralAccess(int var1, Intent var2, int var3, int var4);

    public abstract void grantRuntimePermission(String var1, String var2, int var3, boolean var4);

    public abstract boolean hasInstantApplicationMetadata(String var1, int var2);

    public abstract boolean hasSignatureCapability(int var1, int var2, @PackageParser.SigningDetails.CertCapabilities int var3);

    public abstract boolean isApexPackage(String var1);

    public abstract boolean isDataRestoreSafe(Signature var1, String var2);

    public abstract boolean isDataRestoreSafe(byte[] var1, String var2);

    public abstract boolean isEnabledAndMatches(ComponentInfo var1, int var2, int var3);

    public abstract boolean isInstantApp(String var1, int var2);

    public abstract boolean isInstantAppInstallerComponent(ComponentName var1);

    public abstract boolean isLegacySystemApp(PackageParser.Package var1);

    public abstract boolean isOnlyCoreApps();

    public abstract boolean isPackageDataProtected(int var1, String var2);

    public abstract boolean isPackageEphemeral(int var1, String var2);

    public abstract boolean isPackagePersistent(String var1);

    public abstract boolean isPackageStateProtected(String var1, int var2);

    public abstract boolean isPackageSuspended(String var1, int var2);

    public abstract boolean isPermissionsReviewRequired(String var1, int var2);

    public abstract boolean isPlatformSigned(String var1);

    public abstract boolean isResolveActivityComponent(ComponentInfo var1);

    public abstract void migrateLegacyObbData();

    public abstract void notifyPackageUse(String var1, int var2);

    public void onDefaultSimCallManagerAppChanged(String string2, int n) {
    }

    public void onDefaultSmsAppChanged(String string2, int n) {
    }

    public abstract void pruneInstantApps();

    public abstract List<ResolveInfo> queryIntentActivities(Intent var1, int var2, int var3, int var4);

    public abstract List<ResolveInfo> queryIntentServices(Intent var1, int var2, int var3, int var4);

    public abstract void removeIsolatedUid(int var1);

    public abstract String removeLegacyDefaultBrowserPackageName(int var1);

    public abstract void removePackageListObserver(PackageListObserver var1);

    public abstract void requestInstantAppResolutionPhaseTwo(AuxiliaryResolveInfo var1, Intent var2, String var3, String var4, Bundle var5, int var6);

    public abstract ProviderInfo resolveContentProvider(String var1, int var2, int var3);

    public abstract ResolveInfo resolveIntent(Intent var1, String var2, int var3, int var4, boolean var5, int var6);

    public abstract ResolveInfo resolveService(Intent var1, String var2, int var3, int var4, int var5);

    public abstract void revokeRuntimePermission(String var1, String var2, int var3, boolean var4);

    public abstract void setCheckPermissionDelegate(CheckPermissionDelegate var1);

    public abstract void setDefaultBrowserProvider(DefaultBrowserProvider var1);

    public abstract void setDefaultDialerProvider(DefaultDialerProvider var1);

    public abstract void setDefaultHomeProvider(DefaultHomeProvider var1);

    public abstract void setDeviceAndProfileOwnerPackages(int var1, String var2, SparseArray<String> var3);

    public abstract void setEnableRollbackCode(int var1, int var2);

    public abstract boolean setEnabledOverlayPackages(int var1, String var2, List<String> var3);

    public abstract void setExternalSourcesPolicy(ExternalSourcesPolicy var1);

    public abstract void setKeepUninstalledPackages(List<String> var1);

    public abstract void setLocationExtraPackagesProvider(PackagesProvider var1);

    public abstract void setLocationPackagesProvider(PackagesProvider var1);

    public abstract void setRuntimePermissionsFingerPrint(String var1, int var2);

    public abstract void setSyncAdapterPackagesprovider(SyncAdapterPackagesProvider var1);

    public abstract void setUseOpenWifiAppPackagesProvider(PackagesProvider var1);

    public abstract void setVoiceInteractionPackagesProvider(PackagesProvider var1);

    public abstract void uninstallApex(String var1, long var2, int var4, IntentSender var5);

    public abstract void updatePermissionFlagsTEMP(String var1, String var2, int var3, int var4, int var5);

    public abstract boolean userNeedsBadging(int var1);

    public abstract boolean wasPackageEverLaunched(String var1, int var2);

    public abstract boolean wereDefaultPermissionsGrantedSinceBoot(int var1);

    public static interface CheckPermissionDelegate {
        public int checkPermission(String var1, String var2, int var3, TriFunction<String, String, Integer, Integer> var4);

        public int checkUidPermission(String var1, int var2, BiFunction<String, Integer, Integer> var3);
    }

    public static interface DefaultBrowserProvider {
        public String getDefaultBrowser(int var1);

        public boolean setDefaultBrowser(String var1, int var2);

        public void setDefaultBrowserAsync(String var1, int var2);
    }

    public static interface DefaultDialerProvider {
        public String getDefaultDialer(int var1);
    }

    public static interface DefaultHomeProvider {
        public String getDefaultHome(int var1);

        public void setDefaultHomeAsync(String var1, int var2, Consumer<Boolean> var3);
    }

    public static interface ExternalSourcesPolicy {
        public static final int USER_BLOCKED = 1;
        public static final int USER_DEFAULT = 2;
        public static final int USER_TRUSTED = 0;

        public int getPackageTrustedToInstallApps(String var1, int var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface KnownPackage {
    }

    public static interface PackageListObserver {
        public void onPackageAdded(String var1, int var2);

        default public void onPackageChanged(String string2, int n) {
        }

        public void onPackageRemoved(String var1, int var2);
    }

    public static interface PackagesProvider {
        public String[] getPackages(int var1);
    }

    public static interface SyncAdapterPackagesProvider {
        public String[] getPackages(String var1, int var2);
    }

}

