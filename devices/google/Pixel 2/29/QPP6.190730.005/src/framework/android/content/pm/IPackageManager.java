/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.ChangedPackages;
import android.content.pm.IDexModuleRegisterCallback;
import android.content.pm.IOnPermissionsChangeListener;
import android.content.pm.IPackageDataObserver;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.IPackageDeleteObserver2;
import android.content.pm.IPackageInstaller;
import android.content.pm.IPackageMoveObserver;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.InstrumentationInfo;
import android.content.pm.KeySet;
import android.content.pm.ModuleInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ParceledListSlice;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.SuspendDialogInfo;
import android.content.pm.VerifierDeviceIdentity;
import android.content.pm.VersionedPackage;
import android.content.pm.dex.IArtManager;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IPackageManager
extends IInterface {
    public boolean activitySupportsIntent(ComponentName var1, Intent var2, String var3) throws RemoteException;

    public void addCrossProfileIntentFilter(IntentFilter var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public void addOnPermissionsChangeListener(IOnPermissionsChangeListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean addPermission(PermissionInfo var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean addPermissionAsync(PermissionInfo var1) throws RemoteException;

    public void addPersistentPreferredActivity(IntentFilter var1, ComponentName var2, int var3) throws RemoteException;

    public void addPreferredActivity(IntentFilter var1, int var2, ComponentName[] var3, ComponentName var4, int var5) throws RemoteException;

    public boolean addWhitelistedRestrictedPermission(String var1, String var2, int var3, int var4) throws RemoteException;

    public boolean canForwardTo(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public boolean canRequestPackageInstalls(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public String[] canonicalToCurrentPackageNames(String[] var1) throws RemoteException;

    public void checkPackageStartable(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public int checkPermission(String var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public int checkSignatures(String var1, String var2) throws RemoteException;

    public int checkUidPermission(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public int checkUidSignatures(int var1, int var2) throws RemoteException;

    public void clearApplicationProfileData(String var1) throws RemoteException;

    public void clearApplicationUserData(String var1, IPackageDataObserver var2, int var3) throws RemoteException;

    public void clearCrossProfileIntentFilters(int var1, String var2) throws RemoteException;

    public void clearPackagePersistentPreferredActivities(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void clearPackagePreferredActivities(String var1) throws RemoteException;

    public boolean compileLayouts(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public String[] currentToCanonicalPackageNames(String[] var1) throws RemoteException;

    @UnsupportedAppUsage
    public void deleteApplicationCacheFiles(String var1, IPackageDataObserver var2) throws RemoteException;

    public void deleteApplicationCacheFilesAsUser(String var1, int var2, IPackageDataObserver var3) throws RemoteException;

    public void deletePackageAsUser(String var1, int var2, IPackageDeleteObserver var3, int var4, int var5) throws RemoteException;

    public void deletePackageVersioned(VersionedPackage var1, IPackageDeleteObserver2 var2, int var3, int var4) throws RemoteException;

    public void deletePreloadsFileCache() throws RemoteException;

    public void dumpProfiles(String var1) throws RemoteException;

    public void enterSafeMode() throws RemoteException;

    public void extendVerificationTimeout(int var1, int var2, long var3) throws RemoteException;

    public ResolveInfo findPersistentPreferredActivity(Intent var1, int var2) throws RemoteException;

    public void finishPackageInstall(int var1, boolean var2) throws RemoteException;

    public void flushPackageRestrictionsAsUser(int var1) throws RemoteException;

    public void forceDexOpt(String var1) throws RemoteException;

    public void freeStorage(String var1, long var2, int var4, IntentSender var5) throws RemoteException;

    public void freeStorageAndNotify(String var1, long var2, int var4, IPackageDataObserver var5) throws RemoteException;

    @UnsupportedAppUsage
    public ActivityInfo getActivityInfo(ComponentName var1, int var2, int var3) throws RemoteException;

    public ParceledListSlice getAllIntentFilters(String var1) throws RemoteException;

    public List<String> getAllPackages() throws RemoteException;

    public ParceledListSlice getAllPermissionGroups(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public String[] getAppOpPermissionPackages(String var1) throws RemoteException;

    public String getAppPredictionServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    public int getApplicationEnabledSetting(String var1, int var2) throws RemoteException;

    public boolean getApplicationHiddenSettingAsUser(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public ApplicationInfo getApplicationInfo(String var1, int var2, int var3) throws RemoteException;

    public IArtManager getArtManager() throws RemoteException;

    public String getAttentionServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    public boolean getBlockUninstallForUser(String var1, int var2) throws RemoteException;

    public ChangedPackages getChangedPackages(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public int getComponentEnabledSetting(ComponentName var1, int var2) throws RemoteException;

    public ParceledListSlice getDeclaredSharedLibraries(String var1, int var2, int var3) throws RemoteException;

    public byte[] getDefaultAppsBackup(int var1) throws RemoteException;

    public String getDefaultBrowserPackageName(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getFlagsForUid(int var1) throws RemoteException;

    public CharSequence getHarmfulAppWarning(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public ComponentName getHomeActivities(List<ResolveInfo> var1) throws RemoteException;

    public String getIncidentReportApproverPackageName() throws RemoteException;

    @UnsupportedAppUsage
    public int getInstallLocation() throws RemoteException;

    public int getInstallReason(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice getInstalledApplications(int var1, int var2) throws RemoteException;

    public List<ModuleInfo> getInstalledModules(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice getInstalledPackages(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public String getInstallerPackageName(String var1) throws RemoteException;

    public String getInstantAppAndroidId(String var1, int var2) throws RemoteException;

    public byte[] getInstantAppCookie(String var1, int var2) throws RemoteException;

    public Bitmap getInstantAppIcon(String var1, int var2) throws RemoteException;

    public ComponentName getInstantAppInstallerComponent() throws RemoteException;

    public ComponentName getInstantAppResolverComponent() throws RemoteException;

    public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException;

    public ParceledListSlice getInstantApps(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public InstrumentationInfo getInstrumentationInfo(ComponentName var1, int var2) throws RemoteException;

    public byte[] getIntentFilterVerificationBackup(int var1) throws RemoteException;

    public ParceledListSlice getIntentFilterVerifications(String var1) throws RemoteException;

    public int getIntentVerificationStatus(String var1, int var2) throws RemoteException;

    public KeySet getKeySetByAlias(String var1, String var2) throws RemoteException;

    @UnsupportedAppUsage
    public ResolveInfo getLastChosenActivity(Intent var1, String var2, int var3) throws RemoteException;

    public ModuleInfo getModuleInfo(String var1, int var2) throws RemoteException;

    public int getMoveStatus(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public String getNameForUid(int var1) throws RemoteException;

    public String[] getNamesForUids(int[] var1) throws RemoteException;

    public int[] getPackageGids(String var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public PackageInfo getPackageInfo(String var1, int var2, int var3) throws RemoteException;

    public PackageInfo getPackageInfoVersioned(VersionedPackage var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public IPackageInstaller getPackageInstaller() throws RemoteException;

    public void getPackageSizeInfo(String var1, int var2, IPackageStatsObserver var3) throws RemoteException;

    @UnsupportedAppUsage
    public int getPackageUid(String var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public String[] getPackagesForUid(int var1) throws RemoteException;

    public ParceledListSlice getPackagesHoldingPermissions(String[] var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public String getPermissionControllerPackageName() throws RemoteException;

    public int getPermissionFlags(String var1, String var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public PermissionGroupInfo getPermissionGroupInfo(String var1, int var2) throws RemoteException;

    public PermissionInfo getPermissionInfo(String var1, String var2, int var3) throws RemoteException;

    public ParceledListSlice getPersistentApplications(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public int getPreferredActivities(List<IntentFilter> var1, List<ComponentName> var2, String var3) throws RemoteException;

    public byte[] getPreferredActivityBackup(int var1) throws RemoteException;

    public int getPrivateFlagsForUid(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public ProviderInfo getProviderInfo(ComponentName var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public ActivityInfo getReceiverInfo(ComponentName var1, int var2, int var3) throws RemoteException;

    public int getRuntimePermissionsVersion(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public ServiceInfo getServiceInfo(ComponentName var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public String getServicesSystemSharedLibraryPackageName() throws RemoteException;

    public ParceledListSlice getSharedLibraries(String var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public String getSharedSystemSharedLibraryPackageName() throws RemoteException;

    public KeySet getSigningKeySet(String var1) throws RemoteException;

    public PersistableBundle getSuspendedPackageAppExtras(String var1, int var2) throws RemoteException;

    public ParceledListSlice getSystemAvailableFeatures() throws RemoteException;

    public String getSystemCaptionsServicePackageName() throws RemoteException;

    @UnsupportedAppUsage
    public String[] getSystemSharedLibraryNames() throws RemoteException;

    public String getSystemTextClassifierPackageName() throws RemoteException;

    @UnsupportedAppUsage
    public int getUidForSharedUser(String var1) throws RemoteException;

    public String[] getUnsuspendablePackagesForUser(String[] var1, int var2) throws RemoteException;

    public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException;

    public String getWellbeingPackageName() throws RemoteException;

    public List<String> getWhitelistedRestrictedPermissions(String var1, int var2, int var3) throws RemoteException;

    public void grantDefaultPermissionsToActiveLuiApp(String var1, int var2) throws RemoteException;

    public void grantDefaultPermissionsToEnabledCarrierApps(String[] var1, int var2) throws RemoteException;

    public void grantDefaultPermissionsToEnabledImsServices(String[] var1, int var2) throws RemoteException;

    public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void grantRuntimePermission(String var1, String var2, int var3) throws RemoteException;

    public boolean hasSigningCertificate(String var1, byte[] var2, int var3) throws RemoteException;

    public boolean hasSystemFeature(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean hasSystemUidErrors() throws RemoteException;

    public boolean hasUidSigningCertificate(int var1, byte[] var2, int var3) throws RemoteException;

    public int installExistingPackageAsUser(String var1, int var2, int var3, int var4, List<String> var5) throws RemoteException;

    public boolean isDeviceUpgrading() throws RemoteException;

    public boolean isFirstBoot() throws RemoteException;

    public boolean isInstantApp(String var1, int var2) throws RemoteException;

    public boolean isOnlyCoreApps() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isPackageAvailable(String var1, int var2) throws RemoteException;

    public boolean isPackageDeviceAdminOnAnyUser(String var1) throws RemoteException;

    public boolean isPackageSignedByKeySet(String var1, KeySet var2) throws RemoteException;

    public boolean isPackageSignedByKeySetExactly(String var1, KeySet var2) throws RemoteException;

    public boolean isPackageStateProtected(String var1, int var2) throws RemoteException;

    public boolean isPackageSuspendedForUser(String var1, int var2) throws RemoteException;

    public boolean isPermissionEnforced(String var1) throws RemoteException;

    public boolean isPermissionRevokedByPolicy(String var1, String var2, int var3) throws RemoteException;

    public boolean isProtectedBroadcast(String var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean isSafeMode() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isStorageLow() throws RemoteException;

    @UnsupportedAppUsage
    public boolean isUidPrivileged(int var1) throws RemoteException;

    public void logAppProcessStartIfNeeded(String var1, int var2, String var3, String var4, int var5) throws RemoteException;

    public int movePackage(String var1, String var2) throws RemoteException;

    public int movePrimaryStorage(String var1) throws RemoteException;

    public void notifyDexLoad(String var1, List<String> var2, List<String> var3, String var4) throws RemoteException;

    public void notifyPackageUse(String var1, int var2) throws RemoteException;

    public void notifyPackagesReplacedReceived(String[] var1) throws RemoteException;

    public boolean performDexOptMode(String var1, boolean var2, String var3, boolean var4, boolean var5, String var6) throws RemoteException;

    public boolean performDexOptSecondary(String var1, String var2, boolean var3) throws RemoteException;

    public void performFstrimIfNeeded() throws RemoteException;

    public ParceledListSlice queryContentProviders(String var1, int var2, int var3, String var4) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice queryInstrumentation(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public ParceledListSlice queryIntentActivities(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public ParceledListSlice queryIntentActivityOptions(ComponentName var1, Intent[] var2, String[] var3, Intent var4, String var5, int var6, int var7) throws RemoteException;

    public ParceledListSlice queryIntentContentProviders(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public ParceledListSlice queryIntentReceivers(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public ParceledListSlice queryIntentServices(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public ParceledListSlice queryPermissionsByGroup(String var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void querySyncProviders(List<String> var1, List<ProviderInfo> var2) throws RemoteException;

    public void reconcileSecondaryDexFiles(String var1) throws RemoteException;

    public void registerDexModule(String var1, String var2, boolean var3, IDexModuleRegisterCallback var4) throws RemoteException;

    public void registerMoveCallback(IPackageMoveObserver var1) throws RemoteException;

    public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener var1) throws RemoteException;

    @UnsupportedAppUsage
    public void removePermission(String var1) throws RemoteException;

    public boolean removeWhitelistedRestrictedPermission(String var1, String var2, int var3, int var4) throws RemoteException;

    @UnsupportedAppUsage
    public void replacePreferredActivity(IntentFilter var1, int var2, ComponentName[] var3, ComponentName var4, int var5) throws RemoteException;

    public void resetApplicationPreferences(int var1) throws RemoteException;

    public void resetRuntimePermissions() throws RemoteException;

    public ProviderInfo resolveContentProvider(String var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public ResolveInfo resolveIntent(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public ResolveInfo resolveService(Intent var1, String var2, int var3, int var4) throws RemoteException;

    public void restoreDefaultApps(byte[] var1, int var2) throws RemoteException;

    public void restoreIntentFilterVerification(byte[] var1, int var2) throws RemoteException;

    public void restorePreferredActivities(byte[] var1, int var2) throws RemoteException;

    public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] var1, int var2) throws RemoteException;

    public void revokeDefaultPermissionsFromLuiApps(String[] var1, int var2) throws RemoteException;

    public void revokeRuntimePermission(String var1, String var2, int var3) throws RemoteException;

    public boolean runBackgroundDexoptJob(List<String> var1) throws RemoteException;

    public void sendDeviceCustomizationReadyBroadcast() throws RemoteException;

    public void setApplicationCategoryHint(String var1, int var2, String var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setApplicationEnabledSetting(String var1, int var2, int var3, int var4, String var5) throws RemoteException;

    @UnsupportedAppUsage
    public boolean setApplicationHiddenSettingAsUser(String var1, boolean var2, int var3) throws RemoteException;

    public boolean setBlockUninstallForUser(String var1, boolean var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setComponentEnabledSetting(ComponentName var1, int var2, int var3, int var4) throws RemoteException;

    public boolean setDefaultBrowserPackageName(String var1, int var2) throws RemoteException;

    public String[] setDistractingPackageRestrictionsAsUser(String[] var1, int var2, int var3) throws RemoteException;

    public void setHarmfulAppWarning(String var1, CharSequence var2, int var3) throws RemoteException;

    public void setHomeActivity(ComponentName var1, int var2) throws RemoteException;

    public boolean setInstallLocation(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setInstallerPackageName(String var1, String var2) throws RemoteException;

    public boolean setInstantAppCookie(String var1, byte[] var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void setLastChosenActivity(Intent var1, String var2, int var3, IntentFilter var4, int var5, ComponentName var6) throws RemoteException;

    @UnsupportedAppUsage
    public void setPackageStoppedState(String var1, boolean var2, int var3) throws RemoteException;

    public String[] setPackagesSuspendedAsUser(String[] var1, boolean var2, PersistableBundle var3, PersistableBundle var4, SuspendDialogInfo var5, String var6, int var7) throws RemoteException;

    public void setPermissionEnforced(String var1, boolean var2) throws RemoteException;

    public boolean setRequiredForSystemUser(String var1, boolean var2) throws RemoteException;

    public void setRuntimePermissionsVersion(int var1, int var2) throws RemoteException;

    public void setSystemAppHiddenUntilInstalled(String var1, boolean var2) throws RemoteException;

    public boolean setSystemAppInstallState(String var1, boolean var2, int var3) throws RemoteException;

    public void setUpdateAvailable(String var1, boolean var2) throws RemoteException;

    public boolean shouldShowRequestPermissionRationale(String var1, String var2, int var3) throws RemoteException;

    public void systemReady() throws RemoteException;

    public void unregisterMoveCallback(IPackageMoveObserver var1) throws RemoteException;

    public boolean updateIntentVerificationStatus(String var1, int var2, int var3) throws RemoteException;

    public void updatePackagesIfNeeded() throws RemoteException;

    public void updatePermissionFlags(String var1, String var2, int var3, int var4, boolean var5, int var6) throws RemoteException;

    public void updatePermissionFlagsForAllApps(int var1, int var2, int var3) throws RemoteException;

    public void verifyIntentFilter(int var1, int var2, List<String> var3) throws RemoteException;

    public void verifyPendingInstall(int var1, int var2) throws RemoteException;

    public static class Default
    implements IPackageManager {
        @Override
        public boolean activitySupportsIntent(ComponentName componentName, Intent intent, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void addCrossProfileIntentFilter(IntentFilter intentFilter, String string2, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
        }

        @Override
        public boolean addPermission(PermissionInfo permissionInfo) throws RemoteException {
            return false;
        }

        @Override
        public boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException {
            return false;
        }

        @Override
        public void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public void addPreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) throws RemoteException {
        }

        @Override
        public boolean addWhitelistedRestrictedPermission(String string2, String string3, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean canForwardTo(Intent intent, String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean canRequestPackageInstalls(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public String[] canonicalToCurrentPackageNames(String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public void checkPackageStartable(String string2, int n) throws RemoteException {
        }

        @Override
        public int checkPermission(String string2, String string3, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int checkSignatures(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int checkUidPermission(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int checkUidSignatures(int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public void clearApplicationProfileData(String string2) throws RemoteException {
        }

        @Override
        public void clearApplicationUserData(String string2, IPackageDataObserver iPackageDataObserver, int n) throws RemoteException {
        }

        @Override
        public void clearCrossProfileIntentFilters(int n, String string2) throws RemoteException {
        }

        @Override
        public void clearPackagePersistentPreferredActivities(String string2, int n) throws RemoteException {
        }

        @Override
        public void clearPackagePreferredActivities(String string2) throws RemoteException {
        }

        @Override
        public boolean compileLayouts(String string2) throws RemoteException {
            return false;
        }

        @Override
        public String[] currentToCanonicalPackageNames(String[] arrstring) throws RemoteException {
            return null;
        }

        @Override
        public void deleteApplicationCacheFiles(String string2, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override
        public void deleteApplicationCacheFilesAsUser(String string2, int n, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override
        public void deletePackageAsUser(String string2, int n, IPackageDeleteObserver iPackageDeleteObserver, int n2, int n3) throws RemoteException {
        }

        @Override
        public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int n, int n2) throws RemoteException {
        }

        @Override
        public void deletePreloadsFileCache() throws RemoteException {
        }

        @Override
        public void dumpProfiles(String string2) throws RemoteException {
        }

        @Override
        public void enterSafeMode() throws RemoteException {
        }

        @Override
        public void extendVerificationTimeout(int n, int n2, long l) throws RemoteException {
        }

        @Override
        public ResolveInfo findPersistentPreferredActivity(Intent intent, int n) throws RemoteException {
            return null;
        }

        @Override
        public void finishPackageInstall(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void flushPackageRestrictionsAsUser(int n) throws RemoteException {
        }

        @Override
        public void forceDexOpt(String string2) throws RemoteException {
        }

        @Override
        public void freeStorage(String string2, long l, int n, IntentSender intentSender) throws RemoteException {
        }

        @Override
        public void freeStorageAndNotify(String string2, long l, int n, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override
        public ActivityInfo getActivityInfo(ComponentName componentName, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getAllIntentFilters(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getAllPackages() throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getAllPermissionGroups(int n) throws RemoteException {
            return null;
        }

        @Override
        public String[] getAppOpPermissionPackages(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getAppPredictionServicePackageName() throws RemoteException {
            return null;
        }

        @Override
        public int getApplicationEnabledSetting(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean getApplicationHiddenSettingAsUser(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public ApplicationInfo getApplicationInfo(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public IArtManager getArtManager() throws RemoteException {
            return null;
        }

        @Override
        public String getAttentionServicePackageName() throws RemoteException {
            return null;
        }

        @Override
        public boolean getBlockUninstallForUser(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public ChangedPackages getChangedPackages(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public int getComponentEnabledSetting(ComponentName componentName, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getDeclaredSharedLibraries(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getDefaultAppsBackup(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getDefaultBrowserPackageName(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getFlagsForUid(int n) throws RemoteException {
            return 0;
        }

        @Override
        public CharSequence getHarmfulAppWarning(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getHomeActivities(List<ResolveInfo> list) throws RemoteException {
            return null;
        }

        @Override
        public String getIncidentReportApproverPackageName() throws RemoteException {
            return null;
        }

        @Override
        public int getInstallLocation() throws RemoteException {
            return 0;
        }

        @Override
        public int getInstallReason(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getInstalledApplications(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public List<ModuleInfo> getInstalledModules(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getInstalledPackages(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getInstallerPackageName(String string2) throws RemoteException {
            return null;
        }

        @Override
        public String getInstantAppAndroidId(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getInstantAppCookie(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public Bitmap getInstantAppIcon(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getInstantAppInstallerComponent() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getInstantAppResolverComponent() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getInstantApps(int n) throws RemoteException {
            return null;
        }

        @Override
        public InstrumentationInfo getInstrumentationInfo(ComponentName componentName, int n) throws RemoteException {
            return null;
        }

        @Override
        public byte[] getIntentFilterVerificationBackup(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getIntentFilterVerifications(String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getIntentVerificationStatus(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public KeySet getKeySetByAlias(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public ResolveInfo getLastChosenActivity(Intent intent, String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ModuleInfo getModuleInfo(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getMoveStatus(int n) throws RemoteException {
            return 0;
        }

        @Override
        public String getNameForUid(int n) throws RemoteException {
            return null;
        }

        @Override
        public String[] getNamesForUids(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public int[] getPackageGids(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public PackageInfo getPackageInfo(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public PackageInfo getPackageInfoVersioned(VersionedPackage versionedPackage, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public IPackageInstaller getPackageInstaller() throws RemoteException {
            return null;
        }

        @Override
        public void getPackageSizeInfo(String string2, int n, IPackageStatsObserver iPackageStatsObserver) throws RemoteException {
        }

        @Override
        public int getPackageUid(String string2, int n, int n2) throws RemoteException {
            return 0;
        }

        @Override
        public String[] getPackagesForUid(int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getPackagesHoldingPermissions(String[] arrstring, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getPermissionControllerPackageName() throws RemoteException {
            return null;
        }

        @Override
        public int getPermissionFlags(String string2, String string3, int n) throws RemoteException {
            return 0;
        }

        @Override
        public PermissionGroupInfo getPermissionGroupInfo(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public PermissionInfo getPermissionInfo(String string2, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getPersistentApplications(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public byte[] getPreferredActivityBackup(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getPrivateFlagsForUid(int n) throws RemoteException {
            return 0;
        }

        @Override
        public ProviderInfo getProviderInfo(ComponentName componentName, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ActivityInfo getReceiverInfo(ComponentName componentName, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public int getRuntimePermissionsVersion(int n) throws RemoteException {
            return 0;
        }

        @Override
        public ServiceInfo getServiceInfo(ComponentName componentName, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getSharedLibraries(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
            return null;
        }

        @Override
        public KeySet getSigningKeySet(String string2) throws RemoteException {
            return null;
        }

        @Override
        public PersistableBundle getSuspendedPackageAppExtras(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
            return null;
        }

        @Override
        public String getSystemCaptionsServicePackageName() throws RemoteException {
            return null;
        }

        @Override
        public String[] getSystemSharedLibraryNames() throws RemoteException {
            return null;
        }

        @Override
        public String getSystemTextClassifierPackageName() throws RemoteException {
            return null;
        }

        @Override
        public int getUidForSharedUser(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public String[] getUnsuspendablePackagesForUser(String[] arrstring, int n) throws RemoteException {
            return null;
        }

        @Override
        public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
            return null;
        }

        @Override
        public String getWellbeingPackageName() throws RemoteException {
            return null;
        }

        @Override
        public List<String> getWhitelistedRestrictedPermissions(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void grantDefaultPermissionsToActiveLuiApp(String string2, int n) throws RemoteException {
        }

        @Override
        public void grantDefaultPermissionsToEnabledCarrierApps(String[] arrstring, int n) throws RemoteException {
        }

        @Override
        public void grantDefaultPermissionsToEnabledImsServices(String[] arrstring, int n) throws RemoteException {
        }

        @Override
        public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] arrstring, int n) throws RemoteException {
        }

        @Override
        public void grantRuntimePermission(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public boolean hasSigningCertificate(String string2, byte[] arrby, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasSystemFeature(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasSystemUidErrors() throws RemoteException {
            return false;
        }

        @Override
        public boolean hasUidSigningCertificate(int n, byte[] arrby, int n2) throws RemoteException {
            return false;
        }

        @Override
        public int installExistingPackageAsUser(String string2, int n, int n2, int n3, List<String> list) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isDeviceUpgrading() throws RemoteException {
            return false;
        }

        @Override
        public boolean isFirstBoot() throws RemoteException {
            return false;
        }

        @Override
        public boolean isInstantApp(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isOnlyCoreApps() throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageAvailable(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageDeviceAdminOnAnyUser(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageSignedByKeySet(String string2, KeySet keySet) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageSignedByKeySetExactly(String string2, KeySet keySet) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageStateProtected(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageSuspendedForUser(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPermissionEnforced(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPermissionRevokedByPolicy(String string2, String string3, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isProtectedBroadcast(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSafeMode() throws RemoteException {
            return false;
        }

        @Override
        public boolean isStorageLow() throws RemoteException {
            return false;
        }

        @Override
        public boolean isUidPrivileged(int n) throws RemoteException {
            return false;
        }

        @Override
        public void logAppProcessStartIfNeeded(String string2, int n, String string3, String string4, int n2) throws RemoteException {
        }

        @Override
        public int movePackage(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public int movePrimaryStorage(String string2) throws RemoteException {
            return 0;
        }

        @Override
        public void notifyDexLoad(String string2, List<String> list, List<String> list2, String string3) throws RemoteException {
        }

        @Override
        public void notifyPackageUse(String string2, int n) throws RemoteException {
        }

        @Override
        public void notifyPackagesReplacedReceived(String[] arrstring) throws RemoteException {
        }

        @Override
        public boolean performDexOptMode(String string2, boolean bl, String string3, boolean bl2, boolean bl3, String string4) throws RemoteException {
            return false;
        }

        @Override
        public boolean performDexOptSecondary(String string2, String string3, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void performFstrimIfNeeded() throws RemoteException {
        }

        @Override
        public ParceledListSlice queryContentProviders(String string2, int n, int n2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryInstrumentation(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryIntentActivities(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryIntentActivityOptions(ComponentName componentName, Intent[] arrintent, String[] arrstring, Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryIntentContentProviders(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryIntentReceivers(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryIntentServices(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice queryPermissionsByGroup(String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
        }

        @Override
        public void reconcileSecondaryDexFiles(String string2) throws RemoteException {
        }

        @Override
        public void registerDexModule(String string2, String string3, boolean bl, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException {
        }

        @Override
        public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override
        public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
        }

        @Override
        public void removePermission(String string2) throws RemoteException {
        }

        @Override
        public boolean removeWhitelistedRestrictedPermission(String string2, String string3, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void replacePreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) throws RemoteException {
        }

        @Override
        public void resetApplicationPreferences(int n) throws RemoteException {
        }

        @Override
        public void resetRuntimePermissions() throws RemoteException {
        }

        @Override
        public ProviderInfo resolveContentProvider(String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ResolveInfo resolveIntent(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public ResolveInfo resolveService(Intent intent, String string2, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void restoreDefaultApps(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public void restoreIntentFilterVerification(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public void restorePreferredActivities(byte[] arrby, int n) throws RemoteException {
        }

        @Override
        public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] arrstring, int n) throws RemoteException {
        }

        @Override
        public void revokeDefaultPermissionsFromLuiApps(String[] arrstring, int n) throws RemoteException {
        }

        @Override
        public void revokeRuntimePermission(String string2, String string3, int n) throws RemoteException {
        }

        @Override
        public boolean runBackgroundDexoptJob(List<String> list) throws RemoteException {
            return false;
        }

        @Override
        public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
        }

        @Override
        public void setApplicationCategoryHint(String string2, int n, String string3) throws RemoteException {
        }

        @Override
        public void setApplicationEnabledSetting(String string2, int n, int n2, int n3, String string3) throws RemoteException {
        }

        @Override
        public boolean setApplicationHiddenSettingAsUser(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean setBlockUninstallForUser(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setComponentEnabledSetting(ComponentName componentName, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public boolean setDefaultBrowserPackageName(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public String[] setDistractingPackageRestrictionsAsUser(String[] arrstring, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void setHarmfulAppWarning(String string2, CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void setHomeActivity(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public boolean setInstallLocation(int n) throws RemoteException {
            return false;
        }

        @Override
        public void setInstallerPackageName(String string2, String string3) throws RemoteException {
        }

        @Override
        public boolean setInstantAppCookie(String string2, byte[] arrby, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setLastChosenActivity(Intent intent, String string2, int n, IntentFilter intentFilter, int n2, ComponentName componentName) throws RemoteException {
        }

        @Override
        public void setPackageStoppedState(String string2, boolean bl, int n) throws RemoteException {
        }

        @Override
        public String[] setPackagesSuspendedAsUser(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, String string2, int n) throws RemoteException {
            return null;
        }

        @Override
        public void setPermissionEnforced(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setRequiredForSystemUser(String string2, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setRuntimePermissionsVersion(int n, int n2) throws RemoteException {
        }

        @Override
        public void setSystemAppHiddenUntilInstalled(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setSystemAppInstallState(String string2, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setUpdateAvailable(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public boolean shouldShowRequestPermissionRationale(String string2, String string3, int n) throws RemoteException {
            return false;
        }

        @Override
        public void systemReady() throws RemoteException {
        }

        @Override
        public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
        }

        @Override
        public boolean updateIntentVerificationStatus(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void updatePackagesIfNeeded() throws RemoteException {
        }

        @Override
        public void updatePermissionFlags(String string2, String string3, int n, int n2, boolean bl, int n3) throws RemoteException {
        }

        @Override
        public void updatePermissionFlagsForAllApps(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void verifyIntentFilter(int n, int n2, List<String> list) throws RemoteException {
        }

        @Override
        public void verifyPendingInstall(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPackageManager {
        private static final String DESCRIPTOR = "android.content.pm.IPackageManager";
        static final int TRANSACTION_activitySupportsIntent = 15;
        static final int TRANSACTION_addCrossProfileIntentFilter = 78;
        static final int TRANSACTION_addOnPermissionsChangeListener = 162;
        static final int TRANSACTION_addPermission = 21;
        static final int TRANSACTION_addPermissionAsync = 131;
        static final int TRANSACTION_addPersistentPreferredActivity = 76;
        static final int TRANSACTION_addPreferredActivity = 72;
        static final int TRANSACTION_addWhitelistedRestrictedPermission = 30;
        static final int TRANSACTION_canForwardTo = 47;
        static final int TRANSACTION_canRequestPackageInstalls = 186;
        static final int TRANSACTION_canonicalToCurrentPackageNames = 8;
        static final int TRANSACTION_checkPackageStartable = 1;
        static final int TRANSACTION_checkPermission = 19;
        static final int TRANSACTION_checkSignatures = 34;
        static final int TRANSACTION_checkUidPermission = 20;
        static final int TRANSACTION_checkUidSignatures = 35;
        static final int TRANSACTION_clearApplicationProfileData = 105;
        static final int TRANSACTION_clearApplicationUserData = 104;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 79;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 77;
        static final int TRANSACTION_clearPackagePreferredActivities = 74;
        static final int TRANSACTION_compileLayouts = 121;
        static final int TRANSACTION_currentToCanonicalPackageNames = 7;
        static final int TRANSACTION_deleteApplicationCacheFiles = 102;
        static final int TRANSACTION_deleteApplicationCacheFilesAsUser = 103;
        static final int TRANSACTION_deletePackageAsUser = 66;
        static final int TRANSACTION_deletePackageVersioned = 67;
        static final int TRANSACTION_deletePreloadsFileCache = 187;
        static final int TRANSACTION_dumpProfiles = 122;
        static final int TRANSACTION_enterSafeMode = 110;
        static final int TRANSACTION_extendVerificationTimeout = 136;
        static final int TRANSACTION_findPersistentPreferredActivity = 46;
        static final int TRANSACTION_finishPackageInstall = 63;
        static final int TRANSACTION_flushPackageRestrictionsAsUser = 98;
        static final int TRANSACTION_forceDexOpt = 123;
        static final int TRANSACTION_freeStorage = 101;
        static final int TRANSACTION_freeStorageAndNotify = 100;
        static final int TRANSACTION_getActivityInfo = 14;
        static final int TRANSACTION_getAllIntentFilters = 141;
        static final int TRANSACTION_getAllPackages = 36;
        static final int TRANSACTION_getAllPermissionGroups = 12;
        static final int TRANSACTION_getAppOpPermissionPackages = 44;
        static final int TRANSACTION_getAppPredictionServicePackageName = 200;
        static final int TRANSACTION_getApplicationEnabledSetting = 96;
        static final int TRANSACTION_getApplicationHiddenSettingAsUser = 152;
        static final int TRANSACTION_getApplicationInfo = 13;
        static final int TRANSACTION_getArtManager = 192;
        static final int TRANSACTION_getAttentionServicePackageName = 198;
        static final int TRANSACTION_getBlockUninstallForUser = 157;
        static final int TRANSACTION_getChangedPackages = 181;
        static final int TRANSACTION_getComponentEnabledSetting = 94;
        static final int TRANSACTION_getDeclaredSharedLibraries = 185;
        static final int TRANSACTION_getDefaultAppsBackup = 87;
        static final int TRANSACTION_getDefaultBrowserPackageName = 143;
        static final int TRANSACTION_getFlagsForUid = 41;
        static final int TRANSACTION_getHarmfulAppWarning = 194;
        static final int TRANSACTION_getHomeActivities = 91;
        static final int TRANSACTION_getIncidentReportApproverPackageName = 202;
        static final int TRANSACTION_getInstallLocation = 133;
        static final int TRANSACTION_getInstallReason = 183;
        static final int TRANSACTION_getInstalledApplications = 56;
        static final int TRANSACTION_getInstalledModules = 205;
        static final int TRANSACTION_getInstalledPackages = 54;
        static final int TRANSACTION_getInstallerPackageName = 68;
        static final int TRANSACTION_getInstantAppAndroidId = 191;
        static final int TRANSACTION_getInstantAppCookie = 173;
        static final int TRANSACTION_getInstantAppIcon = 175;
        static final int TRANSACTION_getInstantAppInstallerComponent = 190;
        static final int TRANSACTION_getInstantAppResolverComponent = 188;
        static final int TRANSACTION_getInstantAppResolverSettingsComponent = 189;
        static final int TRANSACTION_getInstantApps = 172;
        static final int TRANSACTION_getInstrumentationInfo = 61;
        static final int TRANSACTION_getIntentFilterVerificationBackup = 89;
        static final int TRANSACTION_getIntentFilterVerifications = 140;
        static final int TRANSACTION_getIntentVerificationStatus = 138;
        static final int TRANSACTION_getKeySetByAlias = 158;
        static final int TRANSACTION_getLastChosenActivity = 70;
        static final int TRANSACTION_getModuleInfo = 206;
        static final int TRANSACTION_getMoveStatus = 126;
        static final int TRANSACTION_getNameForUid = 38;
        static final int TRANSACTION_getNamesForUids = 39;
        static final int TRANSACTION_getPackageGids = 6;
        static final int TRANSACTION_getPackageInfo = 3;
        static final int TRANSACTION_getPackageInfoVersioned = 4;
        static final int TRANSACTION_getPackageInstaller = 155;
        static final int TRANSACTION_getPackageSizeInfo = 106;
        static final int TRANSACTION_getPackageUid = 5;
        static final int TRANSACTION_getPackagesForUid = 37;
        static final int TRANSACTION_getPackagesHoldingPermissions = 55;
        static final int TRANSACTION_getPermissionControllerPackageName = 171;
        static final int TRANSACTION_getPermissionFlags = 26;
        static final int TRANSACTION_getPermissionGroupInfo = 11;
        static final int TRANSACTION_getPermissionInfo = 9;
        static final int TRANSACTION_getPersistentApplications = 57;
        static final int TRANSACTION_getPreferredActivities = 75;
        static final int TRANSACTION_getPreferredActivityBackup = 85;
        static final int TRANSACTION_getPrivateFlagsForUid = 42;
        static final int TRANSACTION_getProviderInfo = 18;
        static final int TRANSACTION_getReceiverInfo = 16;
        static final int TRANSACTION_getRuntimePermissionsVersion = 207;
        static final int TRANSACTION_getServiceInfo = 17;
        static final int TRANSACTION_getServicesSystemSharedLibraryPackageName = 179;
        static final int TRANSACTION_getSharedLibraries = 184;
        static final int TRANSACTION_getSharedSystemSharedLibraryPackageName = 180;
        static final int TRANSACTION_getSigningKeySet = 159;
        static final int TRANSACTION_getSuspendedPackageAppExtras = 84;
        static final int TRANSACTION_getSystemAvailableFeatures = 108;
        static final int TRANSACTION_getSystemCaptionsServicePackageName = 201;
        static final int TRANSACTION_getSystemSharedLibraryNames = 107;
        static final int TRANSACTION_getSystemTextClassifierPackageName = 197;
        static final int TRANSACTION_getUidForSharedUser = 40;
        static final int TRANSACTION_getUnsuspendablePackagesForUser = 82;
        static final int TRANSACTION_getVerifierDeviceIdentity = 144;
        static final int TRANSACTION_getWellbeingPackageName = 199;
        static final int TRANSACTION_getWhitelistedRestrictedPermissions = 29;
        static final int TRANSACTION_grantDefaultPermissionsToActiveLuiApp = 168;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledCarrierApps = 164;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledImsServices = 165;
        static final int TRANSACTION_grantDefaultPermissionsToEnabledTelephonyDataServices = 166;
        static final int TRANSACTION_grantRuntimePermission = 23;
        static final int TRANSACTION_hasSigningCertificate = 195;
        static final int TRANSACTION_hasSystemFeature = 109;
        static final int TRANSACTION_hasSystemUidErrors = 113;
        static final int TRANSACTION_hasUidSigningCertificate = 196;
        static final int TRANSACTION_installExistingPackageAsUser = 134;
        static final int TRANSACTION_isDeviceUpgrading = 147;
        static final int TRANSACTION_isFirstBoot = 145;
        static final int TRANSACTION_isInstantApp = 176;
        static final int TRANSACTION_isOnlyCoreApps = 146;
        static final int TRANSACTION_isPackageAvailable = 2;
        static final int TRANSACTION_isPackageDeviceAdminOnAnyUser = 182;
        static final int TRANSACTION_isPackageSignedByKeySet = 160;
        static final int TRANSACTION_isPackageSignedByKeySetExactly = 161;
        static final int TRANSACTION_isPackageStateProtected = 203;
        static final int TRANSACTION_isPackageSuspendedForUser = 83;
        static final int TRANSACTION_isPermissionEnforced = 149;
        static final int TRANSACTION_isPermissionRevokedByPolicy = 170;
        static final int TRANSACTION_isProtectedBroadcast = 33;
        static final int TRANSACTION_isSafeMode = 111;
        static final int TRANSACTION_isStorageLow = 150;
        static final int TRANSACTION_isUidPrivileged = 43;
        static final int TRANSACTION_logAppProcessStartIfNeeded = 97;
        static final int TRANSACTION_movePackage = 129;
        static final int TRANSACTION_movePrimaryStorage = 130;
        static final int TRANSACTION_notifyDexLoad = 117;
        static final int TRANSACTION_notifyPackageUse = 116;
        static final int TRANSACTION_notifyPackagesReplacedReceived = 209;
        static final int TRANSACTION_performDexOptMode = 119;
        static final int TRANSACTION_performDexOptSecondary = 120;
        static final int TRANSACTION_performFstrimIfNeeded = 114;
        static final int TRANSACTION_queryContentProviders = 60;
        static final int TRANSACTION_queryInstrumentation = 62;
        static final int TRANSACTION_queryIntentActivities = 48;
        static final int TRANSACTION_queryIntentActivityOptions = 49;
        static final int TRANSACTION_queryIntentContentProviders = 53;
        static final int TRANSACTION_queryIntentReceivers = 50;
        static final int TRANSACTION_queryIntentServices = 52;
        static final int TRANSACTION_queryPermissionsByGroup = 10;
        static final int TRANSACTION_querySyncProviders = 59;
        static final int TRANSACTION_reconcileSecondaryDexFiles = 125;
        static final int TRANSACTION_registerDexModule = 118;
        static final int TRANSACTION_registerMoveCallback = 127;
        static final int TRANSACTION_removeOnPermissionsChangeListener = 163;
        static final int TRANSACTION_removePermission = 22;
        static final int TRANSACTION_removeWhitelistedRestrictedPermission = 31;
        static final int TRANSACTION_replacePreferredActivity = 73;
        static final int TRANSACTION_resetApplicationPreferences = 69;
        static final int TRANSACTION_resetRuntimePermissions = 25;
        static final int TRANSACTION_resolveContentProvider = 58;
        static final int TRANSACTION_resolveIntent = 45;
        static final int TRANSACTION_resolveService = 51;
        static final int TRANSACTION_restoreDefaultApps = 88;
        static final int TRANSACTION_restoreIntentFilterVerification = 90;
        static final int TRANSACTION_restorePreferredActivities = 86;
        static final int TRANSACTION_revokeDefaultPermissionsFromDisabledTelephonyDataServices = 167;
        static final int TRANSACTION_revokeDefaultPermissionsFromLuiApps = 169;
        static final int TRANSACTION_revokeRuntimePermission = 24;
        static final int TRANSACTION_runBackgroundDexoptJob = 124;
        static final int TRANSACTION_sendDeviceCustomizationReadyBroadcast = 204;
        static final int TRANSACTION_setApplicationCategoryHint = 65;
        static final int TRANSACTION_setApplicationEnabledSetting = 95;
        static final int TRANSACTION_setApplicationHiddenSettingAsUser = 151;
        static final int TRANSACTION_setBlockUninstallForUser = 156;
        static final int TRANSACTION_setComponentEnabledSetting = 93;
        static final int TRANSACTION_setDefaultBrowserPackageName = 142;
        static final int TRANSACTION_setDistractingPackageRestrictionsAsUser = 80;
        static final int TRANSACTION_setHarmfulAppWarning = 193;
        static final int TRANSACTION_setHomeActivity = 92;
        static final int TRANSACTION_setInstallLocation = 132;
        static final int TRANSACTION_setInstallerPackageName = 64;
        static final int TRANSACTION_setInstantAppCookie = 174;
        static final int TRANSACTION_setLastChosenActivity = 71;
        static final int TRANSACTION_setPackageStoppedState = 99;
        static final int TRANSACTION_setPackagesSuspendedAsUser = 81;
        static final int TRANSACTION_setPermissionEnforced = 148;
        static final int TRANSACTION_setRequiredForSystemUser = 177;
        static final int TRANSACTION_setRuntimePermissionsVersion = 208;
        static final int TRANSACTION_setSystemAppHiddenUntilInstalled = 153;
        static final int TRANSACTION_setSystemAppInstallState = 154;
        static final int TRANSACTION_setUpdateAvailable = 178;
        static final int TRANSACTION_shouldShowRequestPermissionRationale = 32;
        static final int TRANSACTION_systemReady = 112;
        static final int TRANSACTION_unregisterMoveCallback = 128;
        static final int TRANSACTION_updateIntentVerificationStatus = 139;
        static final int TRANSACTION_updatePackagesIfNeeded = 115;
        static final int TRANSACTION_updatePermissionFlags = 27;
        static final int TRANSACTION_updatePermissionFlagsForAllApps = 28;
        static final int TRANSACTION_verifyIntentFilter = 137;
        static final int TRANSACTION_verifyPendingInstall = 135;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPackageManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPackageManager) {
                return (IPackageManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPackageManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 209: {
                    return "notifyPackagesReplacedReceived";
                }
                case 208: {
                    return "setRuntimePermissionsVersion";
                }
                case 207: {
                    return "getRuntimePermissionsVersion";
                }
                case 206: {
                    return "getModuleInfo";
                }
                case 205: {
                    return "getInstalledModules";
                }
                case 204: {
                    return "sendDeviceCustomizationReadyBroadcast";
                }
                case 203: {
                    return "isPackageStateProtected";
                }
                case 202: {
                    return "getIncidentReportApproverPackageName";
                }
                case 201: {
                    return "getSystemCaptionsServicePackageName";
                }
                case 200: {
                    return "getAppPredictionServicePackageName";
                }
                case 199: {
                    return "getWellbeingPackageName";
                }
                case 198: {
                    return "getAttentionServicePackageName";
                }
                case 197: {
                    return "getSystemTextClassifierPackageName";
                }
                case 196: {
                    return "hasUidSigningCertificate";
                }
                case 195: {
                    return "hasSigningCertificate";
                }
                case 194: {
                    return "getHarmfulAppWarning";
                }
                case 193: {
                    return "setHarmfulAppWarning";
                }
                case 192: {
                    return "getArtManager";
                }
                case 191: {
                    return "getInstantAppAndroidId";
                }
                case 190: {
                    return "getInstantAppInstallerComponent";
                }
                case 189: {
                    return "getInstantAppResolverSettingsComponent";
                }
                case 188: {
                    return "getInstantAppResolverComponent";
                }
                case 187: {
                    return "deletePreloadsFileCache";
                }
                case 186: {
                    return "canRequestPackageInstalls";
                }
                case 185: {
                    return "getDeclaredSharedLibraries";
                }
                case 184: {
                    return "getSharedLibraries";
                }
                case 183: {
                    return "getInstallReason";
                }
                case 182: {
                    return "isPackageDeviceAdminOnAnyUser";
                }
                case 181: {
                    return "getChangedPackages";
                }
                case 180: {
                    return "getSharedSystemSharedLibraryPackageName";
                }
                case 179: {
                    return "getServicesSystemSharedLibraryPackageName";
                }
                case 178: {
                    return "setUpdateAvailable";
                }
                case 177: {
                    return "setRequiredForSystemUser";
                }
                case 176: {
                    return "isInstantApp";
                }
                case 175: {
                    return "getInstantAppIcon";
                }
                case 174: {
                    return "setInstantAppCookie";
                }
                case 173: {
                    return "getInstantAppCookie";
                }
                case 172: {
                    return "getInstantApps";
                }
                case 171: {
                    return "getPermissionControllerPackageName";
                }
                case 170: {
                    return "isPermissionRevokedByPolicy";
                }
                case 169: {
                    return "revokeDefaultPermissionsFromLuiApps";
                }
                case 168: {
                    return "grantDefaultPermissionsToActiveLuiApp";
                }
                case 167: {
                    return "revokeDefaultPermissionsFromDisabledTelephonyDataServices";
                }
                case 166: {
                    return "grantDefaultPermissionsToEnabledTelephonyDataServices";
                }
                case 165: {
                    return "grantDefaultPermissionsToEnabledImsServices";
                }
                case 164: {
                    return "grantDefaultPermissionsToEnabledCarrierApps";
                }
                case 163: {
                    return "removeOnPermissionsChangeListener";
                }
                case 162: {
                    return "addOnPermissionsChangeListener";
                }
                case 161: {
                    return "isPackageSignedByKeySetExactly";
                }
                case 160: {
                    return "isPackageSignedByKeySet";
                }
                case 159: {
                    return "getSigningKeySet";
                }
                case 158: {
                    return "getKeySetByAlias";
                }
                case 157: {
                    return "getBlockUninstallForUser";
                }
                case 156: {
                    return "setBlockUninstallForUser";
                }
                case 155: {
                    return "getPackageInstaller";
                }
                case 154: {
                    return "setSystemAppInstallState";
                }
                case 153: {
                    return "setSystemAppHiddenUntilInstalled";
                }
                case 152: {
                    return "getApplicationHiddenSettingAsUser";
                }
                case 151: {
                    return "setApplicationHiddenSettingAsUser";
                }
                case 150: {
                    return "isStorageLow";
                }
                case 149: {
                    return "isPermissionEnforced";
                }
                case 148: {
                    return "setPermissionEnforced";
                }
                case 147: {
                    return "isDeviceUpgrading";
                }
                case 146: {
                    return "isOnlyCoreApps";
                }
                case 145: {
                    return "isFirstBoot";
                }
                case 144: {
                    return "getVerifierDeviceIdentity";
                }
                case 143: {
                    return "getDefaultBrowserPackageName";
                }
                case 142: {
                    return "setDefaultBrowserPackageName";
                }
                case 141: {
                    return "getAllIntentFilters";
                }
                case 140: {
                    return "getIntentFilterVerifications";
                }
                case 139: {
                    return "updateIntentVerificationStatus";
                }
                case 138: {
                    return "getIntentVerificationStatus";
                }
                case 137: {
                    return "verifyIntentFilter";
                }
                case 136: {
                    return "extendVerificationTimeout";
                }
                case 135: {
                    return "verifyPendingInstall";
                }
                case 134: {
                    return "installExistingPackageAsUser";
                }
                case 133: {
                    return "getInstallLocation";
                }
                case 132: {
                    return "setInstallLocation";
                }
                case 131: {
                    return "addPermissionAsync";
                }
                case 130: {
                    return "movePrimaryStorage";
                }
                case 129: {
                    return "movePackage";
                }
                case 128: {
                    return "unregisterMoveCallback";
                }
                case 127: {
                    return "registerMoveCallback";
                }
                case 126: {
                    return "getMoveStatus";
                }
                case 125: {
                    return "reconcileSecondaryDexFiles";
                }
                case 124: {
                    return "runBackgroundDexoptJob";
                }
                case 123: {
                    return "forceDexOpt";
                }
                case 122: {
                    return "dumpProfiles";
                }
                case 121: {
                    return "compileLayouts";
                }
                case 120: {
                    return "performDexOptSecondary";
                }
                case 119: {
                    return "performDexOptMode";
                }
                case 118: {
                    return "registerDexModule";
                }
                case 117: {
                    return "notifyDexLoad";
                }
                case 116: {
                    return "notifyPackageUse";
                }
                case 115: {
                    return "updatePackagesIfNeeded";
                }
                case 114: {
                    return "performFstrimIfNeeded";
                }
                case 113: {
                    return "hasSystemUidErrors";
                }
                case 112: {
                    return "systemReady";
                }
                case 111: {
                    return "isSafeMode";
                }
                case 110: {
                    return "enterSafeMode";
                }
                case 109: {
                    return "hasSystemFeature";
                }
                case 108: {
                    return "getSystemAvailableFeatures";
                }
                case 107: {
                    return "getSystemSharedLibraryNames";
                }
                case 106: {
                    return "getPackageSizeInfo";
                }
                case 105: {
                    return "clearApplicationProfileData";
                }
                case 104: {
                    return "clearApplicationUserData";
                }
                case 103: {
                    return "deleteApplicationCacheFilesAsUser";
                }
                case 102: {
                    return "deleteApplicationCacheFiles";
                }
                case 101: {
                    return "freeStorage";
                }
                case 100: {
                    return "freeStorageAndNotify";
                }
                case 99: {
                    return "setPackageStoppedState";
                }
                case 98: {
                    return "flushPackageRestrictionsAsUser";
                }
                case 97: {
                    return "logAppProcessStartIfNeeded";
                }
                case 96: {
                    return "getApplicationEnabledSetting";
                }
                case 95: {
                    return "setApplicationEnabledSetting";
                }
                case 94: {
                    return "getComponentEnabledSetting";
                }
                case 93: {
                    return "setComponentEnabledSetting";
                }
                case 92: {
                    return "setHomeActivity";
                }
                case 91: {
                    return "getHomeActivities";
                }
                case 90: {
                    return "restoreIntentFilterVerification";
                }
                case 89: {
                    return "getIntentFilterVerificationBackup";
                }
                case 88: {
                    return "restoreDefaultApps";
                }
                case 87: {
                    return "getDefaultAppsBackup";
                }
                case 86: {
                    return "restorePreferredActivities";
                }
                case 85: {
                    return "getPreferredActivityBackup";
                }
                case 84: {
                    return "getSuspendedPackageAppExtras";
                }
                case 83: {
                    return "isPackageSuspendedForUser";
                }
                case 82: {
                    return "getUnsuspendablePackagesForUser";
                }
                case 81: {
                    return "setPackagesSuspendedAsUser";
                }
                case 80: {
                    return "setDistractingPackageRestrictionsAsUser";
                }
                case 79: {
                    return "clearCrossProfileIntentFilters";
                }
                case 78: {
                    return "addCrossProfileIntentFilter";
                }
                case 77: {
                    return "clearPackagePersistentPreferredActivities";
                }
                case 76: {
                    return "addPersistentPreferredActivity";
                }
                case 75: {
                    return "getPreferredActivities";
                }
                case 74: {
                    return "clearPackagePreferredActivities";
                }
                case 73: {
                    return "replacePreferredActivity";
                }
                case 72: {
                    return "addPreferredActivity";
                }
                case 71: {
                    return "setLastChosenActivity";
                }
                case 70: {
                    return "getLastChosenActivity";
                }
                case 69: {
                    return "resetApplicationPreferences";
                }
                case 68: {
                    return "getInstallerPackageName";
                }
                case 67: {
                    return "deletePackageVersioned";
                }
                case 66: {
                    return "deletePackageAsUser";
                }
                case 65: {
                    return "setApplicationCategoryHint";
                }
                case 64: {
                    return "setInstallerPackageName";
                }
                case 63: {
                    return "finishPackageInstall";
                }
                case 62: {
                    return "queryInstrumentation";
                }
                case 61: {
                    return "getInstrumentationInfo";
                }
                case 60: {
                    return "queryContentProviders";
                }
                case 59: {
                    return "querySyncProviders";
                }
                case 58: {
                    return "resolveContentProvider";
                }
                case 57: {
                    return "getPersistentApplications";
                }
                case 56: {
                    return "getInstalledApplications";
                }
                case 55: {
                    return "getPackagesHoldingPermissions";
                }
                case 54: {
                    return "getInstalledPackages";
                }
                case 53: {
                    return "queryIntentContentProviders";
                }
                case 52: {
                    return "queryIntentServices";
                }
                case 51: {
                    return "resolveService";
                }
                case 50: {
                    return "queryIntentReceivers";
                }
                case 49: {
                    return "queryIntentActivityOptions";
                }
                case 48: {
                    return "queryIntentActivities";
                }
                case 47: {
                    return "canForwardTo";
                }
                case 46: {
                    return "findPersistentPreferredActivity";
                }
                case 45: {
                    return "resolveIntent";
                }
                case 44: {
                    return "getAppOpPermissionPackages";
                }
                case 43: {
                    return "isUidPrivileged";
                }
                case 42: {
                    return "getPrivateFlagsForUid";
                }
                case 41: {
                    return "getFlagsForUid";
                }
                case 40: {
                    return "getUidForSharedUser";
                }
                case 39: {
                    return "getNamesForUids";
                }
                case 38: {
                    return "getNameForUid";
                }
                case 37: {
                    return "getPackagesForUid";
                }
                case 36: {
                    return "getAllPackages";
                }
                case 35: {
                    return "checkUidSignatures";
                }
                case 34: {
                    return "checkSignatures";
                }
                case 33: {
                    return "isProtectedBroadcast";
                }
                case 32: {
                    return "shouldShowRequestPermissionRationale";
                }
                case 31: {
                    return "removeWhitelistedRestrictedPermission";
                }
                case 30: {
                    return "addWhitelistedRestrictedPermission";
                }
                case 29: {
                    return "getWhitelistedRestrictedPermissions";
                }
                case 28: {
                    return "updatePermissionFlagsForAllApps";
                }
                case 27: {
                    return "updatePermissionFlags";
                }
                case 26: {
                    return "getPermissionFlags";
                }
                case 25: {
                    return "resetRuntimePermissions";
                }
                case 24: {
                    return "revokeRuntimePermission";
                }
                case 23: {
                    return "grantRuntimePermission";
                }
                case 22: {
                    return "removePermission";
                }
                case 21: {
                    return "addPermission";
                }
                case 20: {
                    return "checkUidPermission";
                }
                case 19: {
                    return "checkPermission";
                }
                case 18: {
                    return "getProviderInfo";
                }
                case 17: {
                    return "getServiceInfo";
                }
                case 16: {
                    return "getReceiverInfo";
                }
                case 15: {
                    return "activitySupportsIntent";
                }
                case 14: {
                    return "getActivityInfo";
                }
                case 13: {
                    return "getApplicationInfo";
                }
                case 12: {
                    return "getAllPermissionGroups";
                }
                case 11: {
                    return "getPermissionGroupInfo";
                }
                case 10: {
                    return "queryPermissionsByGroup";
                }
                case 9: {
                    return "getPermissionInfo";
                }
                case 8: {
                    return "canonicalToCurrentPackageNames";
                }
                case 7: {
                    return "currentToCanonicalPackageNames";
                }
                case 6: {
                    return "getPackageGids";
                }
                case 5: {
                    return "getPackageUid";
                }
                case 4: {
                    return "getPackageInfoVersioned";
                }
                case 3: {
                    return "getPackageInfo";
                }
                case 2: {
                    return "isPackageAvailable";
                }
                case 1: 
            }
            return "checkPackageStartable";
        }

        public static boolean setDefaultImpl(IPackageManager iPackageManager) {
            if (Proxy.sDefaultImpl == null && iPackageManager != null) {
                Proxy.sDefaultImpl = iPackageManager;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                Object object3 = null;
                Object object4 = null;
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                boolean bl10 = false;
                boolean bl11 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 209: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPackagesReplacedReceived(((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 208: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRuntimePermissionsVersion(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 207: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getRuntimePermissionsVersion(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 206: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getModuleInfo(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ModuleInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 205: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstalledModules(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 204: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendDeviceCustomizationReadyBroadcast();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 203: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackageStateProtected(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 202: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIncidentReportApproverPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 201: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemCaptionsServicePackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 200: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppPredictionServicePackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 199: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWellbeingPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 198: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAttentionServicePackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 197: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemTextClassifierPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 196: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasUidSigningCertificate(((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 195: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasSigningCertificate(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 194: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getHarmfulAppWarning(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, (Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 193: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        object4 = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setHarmfulAppWarning((String)object3, (CharSequence)object4, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 192: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = this.getArtManager();
                        ((Parcel)object2).writeNoException();
                        object = object4;
                        if (object3 != null) {
                            object = object3.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 191: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppAndroidId(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 190: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppInstallerComponent();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 189: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppResolverSettingsComponent();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 188: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppResolverComponent();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 187: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deletePreloadsFileCache();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 186: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.canRequestPackageInstalls(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 185: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeclaredSharedLibraries(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 184: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSharedLibraries(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 183: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInstallReason(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 182: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackageDeviceAdminOnAnyUser(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 181: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getChangedPackages(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ChangedPackages)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 180: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSharedSystemSharedLibraryPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 179: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getServicesSystemSharedLibraryPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 178: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setUpdateAvailable((String)object4, bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 177: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.setRequiredForSystemUser((String)object4, bl11) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 176: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isInstantApp(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 175: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppIcon(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((Bitmap)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 174: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setInstantAppCookie(((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 173: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantAppCookie(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeByteArray((byte[])object);
                        return true;
                    }
                    case 172: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstantApps(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 171: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPermissionControllerPackageName();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 170: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPermissionRevokedByPolicy(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 169: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeDefaultPermissionsFromLuiApps(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 168: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantDefaultPermissionsToActiveLuiApp(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 167: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeDefaultPermissionsFromDisabledTelephonyDataServices(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 166: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantDefaultPermissionsToEnabledTelephonyDataServices(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 165: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantDefaultPermissionsToEnabledImsServices(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 164: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantDefaultPermissionsToEnabledCarrierApps(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 163: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeOnPermissionsChangeListener(IOnPermissionsChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 162: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addOnPermissionsChangeListener(IOnPermissionsChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 161: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? KeySet.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isPackageSignedByKeySetExactly((String)object4, (KeySet)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 160: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? KeySet.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isPackageSignedByKeySet((String)object4, (KeySet)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 159: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSigningKeySet(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((KeySet)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 158: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getKeySetByAlias(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((KeySet)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 157: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBlockUninstallForUser(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 156: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.setBlockUninstallForUser((String)object4, bl11, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 155: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = this.getPackageInstaller();
                        ((Parcel)object2).writeNoException();
                        object = object3;
                        if (object4 != null) {
                            object = object4.asBinder();
                        }
                        ((Parcel)object2).writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 154: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.setSystemAppInstallState((String)object4, bl11, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 153: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setSystemAppHiddenUntilInstalled((String)object4, bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 152: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getApplicationHiddenSettingAsUser(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 151: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.setApplicationHiddenSettingAsUser((String)object4, bl11, ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 150: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isStorageLow() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 149: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPermissionEnforced(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 148: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setPermissionEnforced((String)object4, bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 147: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceUpgrading() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 146: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isOnlyCoreApps() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 145: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isFirstBoot() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 144: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getVerifierDeviceIdentity();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((VerifierDeviceIdentity)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 143: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultBrowserPackageName(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 142: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setDefaultBrowserPackageName(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 141: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllIntentFilters(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 140: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIntentFilterVerifications(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 139: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.updateIntentVerificationStatus(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 138: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getIntentVerificationStatus(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 137: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.verifyIntentFilter(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createStringArrayList());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 136: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.extendVerificationTimeout(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readLong());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 135: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.verifyPendingInstall(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 134: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.installExistingPackageAsUser(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createStringArrayList());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 133: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getInstallLocation();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 132: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.setInstallLocation(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 131: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PermissionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addPermissionAsync((PermissionInfo)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 130: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.movePrimaryStorage(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 129: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.movePackage(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 128: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterMoveCallback(IPackageMoveObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 127: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerMoveCallback(IPackageMoveObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 126: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getMoveStatus(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 125: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reconcileSecondaryDexFiles(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 124: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.runBackgroundDexoptJob(((Parcel)object).createStringArrayList()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 123: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceDexOpt(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 122: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dumpProfiles(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 121: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.compileLayouts(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 120: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = ((Parcel)object).readString();
                        object4 = ((Parcel)object).readString();
                        bl11 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        n = this.performDexOptSecondary((String)object3, (String)object4, bl11) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = ((Parcel)object).readInt() != 0;
                        object3 = ((Parcel)object).readString();
                        bl = ((Parcel)object).readInt() != 0;
                        bl2 = ((Parcel)object).readInt() != 0;
                        n = this.performDexOptMode((String)object4, bl11, (String)object3, bl, bl2, ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object4 = ((Parcel)object).readString();
                        bl11 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.registerDexModule((String)object2, (String)object4, bl11, IDexModuleRegisterCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyDexLoad(((Parcel)object).readString(), ((Parcel)object).createStringArrayList(), ((Parcel)object).createStringArrayList(), ((Parcel)object).readString());
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyPackageUse(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updatePackagesIfNeeded();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.performFstrimIfNeeded();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasSystemUidErrors() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.systemReady();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSafeMode() ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enterSafeMode();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasSystemFeature(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemAvailableFeatures();
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemSharedLibraryNames();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getPackageSizeInfo(((Parcel)object).readString(), ((Parcel)object).readInt(), IPackageStatsObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearApplicationProfileData(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearApplicationUserData(((Parcel)object).readString(), IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteApplicationCacheFilesAsUser(((Parcel)object).readString(), ((Parcel)object).readInt(), IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteApplicationCacheFiles(((Parcel)object).readString(), IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        long l = ((Parcel)object).readLong();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? IntentSender.CREATOR.createFromParcel((Parcel)object) : null;
                        this.freeStorage((String)object4, l, n, (IntentSender)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.freeStorageAndNotify(((Parcel)object).readString(), ((Parcel)object).readLong(), ((Parcel)object).readInt(), IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        bl11 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.setPackageStoppedState((String)object4, bl11, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.flushPackageRestrictionsAsUser(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.logAppProcessStartIfNeeded(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getApplicationEnabledSetting(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setApplicationEnabledSetting(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getComponentEnabledSetting((ComponentName)object4, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 93: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setComponentEnabledSetting((ComponentName)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setHomeActivity((ComponentName)object4, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = new ArrayList();
                        object4 = this.getHomeActivities((List<ResolveInfo>)object);
                        ((Parcel)object2).writeNoException();
                        if (object4 != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ComponentName)object4).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 90: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreIntentFilterVerification(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIntentFilterVerificationBackup(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeByteArray((byte[])object);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restoreDefaultApps(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDefaultAppsBackup(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeByteArray((byte[])object);
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.restorePreferredActivities(((Parcel)object).createByteArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPreferredActivityBackup(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeByteArray((byte[])object);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSuspendedPackageAppExtras(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PersistableBundle)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackageSuspendedForUser(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getUnsuspendablePackagesForUser(((Parcel)object).createStringArray(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String[] arrstring = ((Parcel)object).createStringArray();
                        bl11 = ((Parcel)object).readInt() != 0;
                        object4 = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object3 = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        SuspendDialogInfo suspendDialogInfo = ((Parcel)object).readInt() != 0 ? SuspendDialogInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.setPackagesSuspendedAsUser(arrstring, bl11, (PersistableBundle)object4, (PersistableBundle)object3, suspendDialogInfo, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.setDistractingPackageRestrictionsAsUser(((Parcel)object).createStringArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearCrossProfileIntentFilters(((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addCrossProfileIntentFilter((IntentFilter)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearPackagePersistentPreferredActivities(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        object3 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addPersistentPreferredActivity((IntentFilter)object4, (ComponentName)object3, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object3 = new ArrayList();
                        object4 = new ArrayList();
                        n = this.getPreferredActivities((List<IntentFilter>)object3, (List<ComponentName>)object4, ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        ((Parcel)object2).writeTypedList(object3);
                        ((Parcel)object2).writeTypedList(object4);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearPackagePreferredActivities(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        ComponentName[] arrcomponentName = ((Parcel)object).createTypedArray(ComponentName.CREATOR);
                        object3 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.replacePreferredActivity((IntentFilter)object4, n, arrcomponentName, (ComponentName)object3, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        ComponentName[] arrcomponentName = ((Parcel)object).createTypedArray(ComponentName.CREATOR);
                        object3 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addPreferredActivity((IntentFilter)object4, n, arrcomponentName, (ComponentName)object3, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        object3 = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setLastChosenActivity((Intent)object4, string2, n2, (IntentFilter)object3, n, (ComponentName)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLastChosenActivity((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ResolveInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetApplicationPreferences(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstallerPackageName(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? VersionedPackage.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deletePackageVersioned((VersionedPackage)object4, IPackageDeleteObserver2.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deletePackageAsUser(((Parcel)object).readString(), ((Parcel)object).readInt(), IPackageDeleteObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setApplicationCategoryHint(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setInstallerPackageName(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl11 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl11 = true;
                        }
                        this.finishPackageInstall(n, bl11);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryInstrumentation(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getInstrumentationInfo((ComponentName)object4, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((InstrumentationInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryContentProviders(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).createStringArrayList();
                        object = ((Parcel)object).createTypedArrayList(ProviderInfo.CREATOR);
                        this.querySyncProviders((List<String>)object4, (List<ProviderInfo>)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object4);
                        ((Parcel)object2).writeTypedList(object);
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.resolveContentProvider(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ProviderInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPersistentApplications(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstalledApplications(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackagesHoldingPermissions(((Parcel)object).createStringArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInstalledPackages(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.queryIntentContentProviders((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.queryIntentServices((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.resolveService((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ResolveInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.queryIntentReceivers((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        Intent[] arrintent = ((Parcel)object).createTypedArray(Intent.CREATOR);
                        String[] arrstring = ((Parcel)object).createStringArray();
                        object3 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.queryIntentActivityOptions((ComponentName)object4, arrintent, arrstring, (Intent)object3, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.queryIntentActivities((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.canForwardTo((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.findPersistentPreferredActivity((Intent)object4, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ResolveInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.resolveIntent((Intent)object4, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ResolveInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAppOpPermissionPackages(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUidPrivileged(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPrivateFlagsForUid(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getFlagsForUid(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUidForSharedUser(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNamesForUids(((Parcel)object).createIntArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNameForUid(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackagesForUid(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllPackages();
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkUidSignatures(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkSignatures(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isProtectedBroadcast(((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.shouldShowRequestPermissionRationale(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.removeWhitelistedRestrictedPermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.addWhitelistedRestrictedPermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getWhitelistedRestrictedPermissions(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updatePermissionFlagsForAllApps(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readString();
                        object3 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        bl11 = ((Parcel)object).readInt() != 0;
                        this.updatePermissionFlags((String)object4, (String)object3, n2, n, bl11, ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPermissionFlags(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resetRuntimePermissions();
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.revokeRuntimePermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.grantRuntimePermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removePermission(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PermissionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addPermission((PermissionInfo)object) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkUidPermission(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkPermission(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getProviderInfo((ComponentName)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ProviderInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getServiceInfo((ComponentName)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ServiceInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getReceiverInfo((ComponentName)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object3 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.activitySupportsIntent((ComponentName)object4, (Intent)object3, ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getActivityInfo((ComponentName)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ActivityInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getApplicationInfo(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ApplicationInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllPermissionGroups(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPermissionGroupInfo(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PermissionGroupInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.queryPermissionsByGroup(((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParceledListSlice)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPermissionInfo(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PermissionInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.canonicalToCurrentPackageNames(((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.currentToCanonicalPackageNames(((Parcel)object).createStringArray());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringArray((String[])object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackageGids(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeIntArray((int[])object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPackageUid(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object4 = ((Parcel)object).readInt() != 0 ? VersionedPackage.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPackageInfoVersioned((VersionedPackage)object4, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PackageInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPackageInfo(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PackageInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackageAvailable(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.checkPackageStartable(((Parcel)object).readString(), ((Parcel)object).readInt());
                ((Parcel)object2).writeNoException();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPackageManager {
            public static IPackageManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean activitySupportsIntent(ComponentName componentName, Intent intent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().activitySupportsIntent(componentName, intent, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void addCrossProfileIntentFilter(IntentFilter intentFilter, String string2, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCrossProfileIntentFilter(intentFilter, string2, n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnPermissionsChangeListener != null ? iOnPermissionsChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(162, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnPermissionsChangeListener(iOnPermissionsChangeListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean addPermission(PermissionInfo permissionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (permissionInfo != null) {
                        parcel.writeInt(1);
                        permissionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addPermission(permissionInfo);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean addPermissionAsync(PermissionInfo permissionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (permissionInfo != null) {
                        parcel.writeInt(1);
                        permissionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(131, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addPermissionAsync(permissionInfo);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void addPersistentPreferredActivity(IntentFilter intentFilter, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPersistentPreferredActivity(intentFilter, componentName, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void addPreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrcomponentName, 0);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPreferredActivity(intentFilter, n, arrcomponentName, componentName, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean addWhitelistedRestrictedPermission(String string2, String string3, int n, int n2) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(30, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().addWhitelistedRestrictedPermission(string2, string3, n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean canForwardTo(Intent intent, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().canForwardTo(intent, string2, n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean canRequestPackageInstalls(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(186, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canRequestPackageInstalls(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public String[] canonicalToCurrentPackageNames(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().canonicalToCurrentPackageNames(arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void checkPackageStartable(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().checkPackageStartable(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkPermission(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkPermission(string2, string3, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkSignatures(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().checkSignatures(string2, string3);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkUidPermission(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkUidPermission(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int checkUidSignatures(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().checkUidSignatures(n, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearApplicationProfileData(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(105, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearApplicationProfileData(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void clearApplicationUserData(String string2, IPackageDataObserver iPackageDataObserver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iPackageDataObserver != null ? iPackageDataObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(104, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearApplicationUserData(string2, iPackageDataObserver, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearCrossProfileIntentFilters(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(79, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearCrossProfileIntentFilters(n, string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearPackagePersistentPreferredActivities(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPackagePersistentPreferredActivities(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void clearPackagePreferredActivities(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(74, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPackagePreferredActivities(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean compileLayouts(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(121, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().compileLayouts(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public String[] currentToCanonicalPackageNames(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().currentToCanonicalPackageNames(arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void deleteApplicationCacheFiles(String string2, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iPackageDataObserver != null ? iPackageDataObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteApplicationCacheFiles(string2, iPackageDataObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void deleteApplicationCacheFilesAsUser(String string2, int n, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    IBinder iBinder = iPackageDataObserver != null ? iPackageDataObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(103, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteApplicationCacheFilesAsUser(string2, n, iPackageDataObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void deletePackageAsUser(String string2, int n, IPackageDeleteObserver iPackageDeleteObserver, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    IBinder iBinder = iPackageDeleteObserver != null ? iPackageDeleteObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deletePackageAsUser(string2, n, iPackageDeleteObserver, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void deletePackageVersioned(VersionedPackage versionedPackage, IPackageDeleteObserver2 iPackageDeleteObserver2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (versionedPackage != null) {
                        parcel.writeInt(1);
                        versionedPackage.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPackageDeleteObserver2 != null ? iPackageDeleteObserver2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(67, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deletePackageVersioned(versionedPackage, iPackageDeleteObserver2, n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deletePreloadsFileCache() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(187, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deletePreloadsFileCache();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void dumpProfiles(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(122, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dumpProfiles(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void enterSafeMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enterSafeMode();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void extendVerificationTimeout(int n, int n2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(136, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().extendVerificationTimeout(n, n2, l);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ResolveInfo findPersistentPreferredActivity(Intent parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ResolveInfo resolveInfo = Stub.getDefaultImpl().findPersistentPreferredActivity((Intent)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return resolveInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void finishPackageInstall(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishPackageInstall(n, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void flushPackageRestrictionsAsUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().flushPackageRestrictionsAsUser(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void forceDexOpt(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(123, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceDexOpt(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void freeStorage(String string2, long l, int n, IntentSender intentSender) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (intentSender != null) {
                        parcel.writeInt(1);
                        intentSender.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(101, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().freeStorage(string2, l, n, intentSender);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void freeStorageAndNotify(String string2, long l, int n, IPackageDataObserver iPackageDataObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    IBinder iBinder = iPackageDataObserver != null ? iPackageDataObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(100, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().freeStorageAndNotify(string2, l, n, iPackageDataObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ActivityInfo getActivityInfo(ComponentName parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ActivityInfo activityInfo = Stub.getDefaultImpl().getActivityInfo((ComponentName)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return activityInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ActivityInfo activityInfo = ActivityInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public ParceledListSlice getAllIntentFilters(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(141, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getAllIntentFilters((String)((Object)parceledListSlice));
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public List<String> getAllPackages() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getAllPackages();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getAllPermissionGroups(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getAllPermissionGroups(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public String[] getAppOpPermissionPackages(String arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrstring);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getAppOpPermissionPackages((String)arrstring);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getAppPredictionServicePackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(200, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getAppPredictionServicePackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getApplicationEnabledSetting(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getApplicationEnabledSetting(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getApplicationHiddenSettingAsUser(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(152, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getApplicationHiddenSettingAsUser(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public ApplicationInfo getApplicationInfo(String object, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getApplicationInfo((String)object, n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? ApplicationInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public IArtManager getArtManager() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(192, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IArtManager iArtManager = Stub.getDefaultImpl().getArtManager();
                        return iArtManager;
                    }
                    parcel2.readException();
                    IArtManager iArtManager = IArtManager.Stub.asInterface(parcel2.readStrongBinder());
                    return iArtManager;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getAttentionServicePackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(198, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getAttentionServicePackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getBlockUninstallForUser(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(157, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getBlockUninstallForUser(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public ChangedPackages getChangedPackages(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(181, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ChangedPackages changedPackages = Stub.getDefaultImpl().getChangedPackages(n, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return changedPackages;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ChangedPackages changedPackages = parcel.readInt() != 0 ? ChangedPackages.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return changedPackages;
            }

            @Override
            public int getComponentEnabledSetting(ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getComponentEnabledSetting(componentName, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getDeclaredSharedLibraries(String parceledListSlice, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(185, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getDeclaredSharedLibraries((String)((Object)parceledListSlice), n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public byte[] getDefaultAppsBackup(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getDefaultAppsBackup(n);
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getDefaultBrowserPackageName(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(143, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDefaultBrowserPackageName(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getFlagsForUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getFlagsForUid(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public CharSequence getHarmfulAppWarning(String charSequence, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)charSequence);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(194, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        charSequence = Stub.getDefaultImpl().getHarmfulAppWarning((String)charSequence, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return charSequence;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public ComponentName getHomeActivities(List<ResolveInfo> object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getHomeActivities((List<ResolveInfo>)object);
                        return object;
                    }
                    parcel2.readException();
                    ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.readTypedList(object, ResolveInfo.CREATOR);
                    return componentName;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getIncidentReportApproverPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(202, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getIncidentReportApproverPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getInstallLocation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(133, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getInstallLocation();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getInstallReason(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(183, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getInstallReason(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getInstalledApplications(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(56, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getInstalledApplications(n, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public List<ModuleInfo> getInstalledModules(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(205, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ModuleInfo> list = Stub.getDefaultImpl().getInstalledModules(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ModuleInfo> arrayList = parcel2.createTypedArrayList(ModuleInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getInstalledPackages(int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        if (this.mRemote.transact(54, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getInstalledPackages(n, n2);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public String getInstallerPackageName(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(68, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getInstallerPackageName(string2);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getInstantAppAndroidId(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(191, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        string2 = Stub.getDefaultImpl().getInstantAppAndroidId(string2, n);
                        return string2;
                    }
                    parcel2.readException();
                    string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] getInstantAppCookie(String arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(173, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrby = Stub.getDefaultImpl().getInstantAppCookie((String)arrby, n);
                        return arrby;
                    }
                    parcel2.readException();
                    arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public Bitmap getInstantAppIcon(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(175, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getInstantAppIcon((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public ComponentName getInstantAppInstallerComponent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(190, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getInstantAppInstallerComponent();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public ComponentName getInstantAppResolverComponent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(188, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getInstantAppResolverComponent();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public ComponentName getInstantAppResolverSettingsComponent() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(189, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getInstantAppResolverSettingsComponent();
                        parcel.recycle();
                        parcel2.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return componentName;
            }

            @Override
            public ParceledListSlice getInstantApps(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(172, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getInstantApps(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public InstrumentationInfo getInstrumentationInfo(ComponentName parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        InstrumentationInfo instrumentationInfo = Stub.getDefaultImpl().getInstrumentationInfo((ComponentName)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return instrumentationInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        InstrumentationInfo instrumentationInfo = InstrumentationInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public byte[] getIntentFilterVerificationBackup(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(89, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getIntentFilterVerificationBackup(n);
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getIntentFilterVerifications(String parceledListSlice) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        if (this.mRemote.transact(140, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getIntentFilterVerifications((String)((Object)parceledListSlice));
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public int getIntentVerificationStatus(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(138, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getIntentVerificationStatus(string2, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public KeySet getKeySetByAlias(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(158, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getKeySetByAlias((String)object, string2);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? KeySet.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ResolveInfo getLastChosenActivity(Intent parcelable, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ResolveInfo resolveInfo = Stub.getDefaultImpl().getLastChosenActivity((Intent)parcelable, (String)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return resolveInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public ModuleInfo getModuleInfo(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(206, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getModuleInfo((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? ModuleInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public int getMoveStatus(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(126, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getMoveStatus(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getNameForUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getNameForUid(n);
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getNamesForUids(int[] arrobject) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])arrobject);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrobject = Stub.getDefaultImpl().getNamesForUids((int[])arrobject);
                        return arrobject;
                    }
                    parcel2.readException();
                    arrobject = parcel2.createStringArray();
                    return arrobject;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getPackageGids(String arrn, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrn);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrn = Stub.getDefaultImpl().getPackageGids((String)arrn, n, n2);
                        return arrn;
                    }
                    parcel2.readException();
                    arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PackageInfo getPackageInfo(String object, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPackageInfo((String)object, n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? PackageInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PackageInfo getPackageInfoVersioned(VersionedPackage parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((VersionedPackage)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PackageInfo packageInfo = Stub.getDefaultImpl().getPackageInfoVersioned((VersionedPackage)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return packageInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        PackageInfo packageInfo = PackageInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public IPackageInstaller getPackageInstaller() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(155, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IPackageInstaller iPackageInstaller = Stub.getDefaultImpl().getPackageInstaller();
                        return iPackageInstaller;
                    }
                    parcel2.readException();
                    IPackageInstaller iPackageInstaller = IPackageInstaller.Stub.asInterface(parcel2.readStrongBinder());
                    return iPackageInstaller;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getPackageSizeInfo(String string2, int n, IPackageStatsObserver iPackageStatsObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    IBinder iBinder = iPackageStatsObserver != null ? iPackageStatsObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(106, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getPackageSizeInfo(string2, n, iPackageStatsObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getPackageUid(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPackageUid(string2, n, n2);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getPackagesForUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getPackagesForUid(n);
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getPackagesHoldingPermissions(String[] object, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStringArray((String[])object);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(55, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPackagesHoldingPermissions((String[])object, n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public String getPermissionControllerPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(171, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getPermissionControllerPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getPermissionFlags(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPermissionFlags(string2, string3, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public PermissionGroupInfo getPermissionGroupInfo(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPermissionGroupInfo((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? PermissionGroupInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public PermissionInfo getPermissionInfo(String object, String string2, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(9, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPermissionInfo((String)object, string2, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? PermissionInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public ParceledListSlice getPersistentApplications(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(57, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getPersistentApplications(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public int getPreferredActivities(List<IntentFilter> list, List<ComponentName> list2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPreferredActivities(list, list2, string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    parcel2.readTypedList(list, IntentFilter.CREATOR);
                    parcel2.readTypedList(list2, ComponentName.CREATOR);
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] getPreferredActivityBackup(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getPreferredActivityBackup(n);
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getPrivateFlagsForUid(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPrivateFlagsForUid(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ProviderInfo getProviderInfo(ComponentName parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ProviderInfo providerInfo = Stub.getDefaultImpl().getProviderInfo((ComponentName)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return providerInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ProviderInfo providerInfo = ProviderInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ActivityInfo getReceiverInfo(ComponentName parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ActivityInfo activityInfo = Stub.getDefaultImpl().getReceiverInfo((ComponentName)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return activityInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ActivityInfo activityInfo = ActivityInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public int getRuntimePermissionsVersion(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(207, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getRuntimePermissionsVersion(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ServiceInfo getServiceInfo(ComponentName parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ServiceInfo serviceInfo = Stub.getDefaultImpl().getServiceInfo((ComponentName)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return serviceInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ServiceInfo serviceInfo = ServiceInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public String getServicesSystemSharedLibraryPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(179, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getServicesSystemSharedLibraryPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice getSharedLibraries(String parceledListSlice, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)((Object)parceledListSlice));
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(184, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().getSharedLibraries((String)((Object)parceledListSlice), n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public String getSharedSystemSharedLibraryPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(180, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSharedSystemSharedLibraryPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public KeySet getSigningKeySet(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(159, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getSigningKeySet((String)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? KeySet.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public PersistableBundle getSuspendedPackageAppExtras(String object, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(84, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getSuspendedPackageAppExtras((String)object, n);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public ParceledListSlice getSystemAvailableFeatures() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(108, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getSystemAvailableFeatures();
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public String getSystemCaptionsServicePackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(201, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSystemCaptionsServicePackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSystemSharedLibraryNames() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(107, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSystemSharedLibraryNames();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getSystemTextClassifierPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(197, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getSystemTextClassifierPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getUidForSharedUser(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getUidForSharedUser(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getUnsuspendablePackagesForUser(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(82, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getUnsuspendablePackagesForUser(arrstring, n);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public VerifierDeviceIdentity getVerifierDeviceIdentity() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(144, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        VerifierDeviceIdentity verifierDeviceIdentity = Stub.getDefaultImpl().getVerifierDeviceIdentity();
                        parcel.recycle();
                        parcel2.recycle();
                        return verifierDeviceIdentity;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                VerifierDeviceIdentity verifierDeviceIdentity = parcel.readInt() != 0 ? VerifierDeviceIdentity.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return verifierDeviceIdentity;
            }

            @Override
            public String getWellbeingPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(199, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getWellbeingPackageName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getWhitelistedRestrictedPermissions(String list, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)list));
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getWhitelistedRestrictedPermissions((String)((Object)list), n, n2);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantDefaultPermissionsToActiveLuiApp(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(168, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDefaultPermissionsToActiveLuiApp(string2, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantDefaultPermissionsToEnabledCarrierApps(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(164, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDefaultPermissionsToEnabledCarrierApps(arrstring, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantDefaultPermissionsToEnabledImsServices(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(165, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDefaultPermissionsToEnabledImsServices(arrstring, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantDefaultPermissionsToEnabledTelephonyDataServices(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(166, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDefaultPermissionsToEnabledTelephonyDataServices(arrstring, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantRuntimePermission(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantRuntimePermission(string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean hasSigningCertificate(String string2, byte[] arrby, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeByteArray(arrby);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(195, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasSigningCertificate(string2, arrby, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean hasSystemFeature(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(109, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasSystemFeature(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasSystemUidErrors() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(113, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasSystemUidErrors();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean hasUidSigningCertificate(int n, byte[] arrby, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        parcel.writeByteArray(arrby);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(196, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasUidSigningCertificate(n, arrby, n2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public int installExistingPackageAsUser(String string2, int n, int n2, int n3, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(134, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().installExistingPackageAsUser(string2, n, n2, n3, list);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isDeviceUpgrading() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(147, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceUpgrading();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isFirstBoot() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(145, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isFirstBoot();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isInstantApp(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(176, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isInstantApp(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isOnlyCoreApps() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(146, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isOnlyCoreApps();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isPackageAvailable(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackageAvailable(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isPackageDeviceAdminOnAnyUser(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(182, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackageDeviceAdminOnAnyUser(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isPackageSignedByKeySet(String string2, KeySet keySet) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (keySet != null) {
                        parcel.writeInt(1);
                        keySet.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(160, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isPackageSignedByKeySet(string2, keySet);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isPackageSignedByKeySetExactly(String string2, KeySet keySet) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (keySet != null) {
                        parcel.writeInt(1);
                        keySet.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(161, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isPackageSignedByKeySetExactly(string2, keySet);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isPackageStateProtected(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(203, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackageStateProtected(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isPackageSuspendedForUser(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(83, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackageSuspendedForUser(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isPermissionEnforced(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(149, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPermissionEnforced(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isPermissionRevokedByPolicy(String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(170, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPermissionRevokedByPolicy(string2, string3, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isProtectedBroadcast(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(33, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isProtectedBroadcast(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isSafeMode() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(111, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSafeMode();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isStorageLow() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(150, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isStorageLow();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isUidPrivileged(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(43, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUidPrivileged(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void logAppProcessStartIfNeeded(String string2, int n, String string3, String string4, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(97, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().logAppProcessStartIfNeeded(string2, n, string3, string4, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int movePackage(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(129, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().movePackage(string2, string3);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int movePrimaryStorage(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(130, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().movePrimaryStorage(string2);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyDexLoad(String string2, List<String> list, List<String> list2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    parcel.writeStringList(list2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(117, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDexLoad(string2, list, list2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPackageUse(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(116, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPackageUse(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyPackagesReplacedReceived(String[] arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(209, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPackagesReplacedReceived(arrstring);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean performDexOptMode(String string2, boolean bl, String string3, boolean bl2, boolean bl3, String string4) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block12 : {
                    int n;
                    boolean bl4;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                        bl4 = true;
                        n = bl ? 1 : 0;
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeString(string3);
                        n = bl2 ? 1 : 0;
                        parcel2.writeInt(n);
                        n = bl3 ? 1 : 0;
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        if (!this.mRemote.transact(119, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().performDexOptMode(string2, bl, string3, bl2, bl3, string4);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        bl = n != 0 ? bl4 : false;
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    catch (Throwable throwable) {}
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            @Override
            public boolean performDexOptSecondary(String string2, String string3, boolean bl) throws RemoteException {
                Parcel parcel;
                int n;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(120, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().performDexOptSecondary(string2, string3, bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void performFstrimIfNeeded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(114, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performFstrimIfNeeded();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ParceledListSlice queryContentProviders(String parceledListSlice, int n, int n2, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(60, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().queryContentProviders((String)((Object)parceledListSlice), n, n2, string2);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public ParceledListSlice queryInstrumentation(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(62, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().queryInstrumentation((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice queryIntentActivities(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().queryIntentActivities((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParceledListSlice parceledListSlice = (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public ParceledListSlice queryIntentActivityOptions(ComponentName parcelable, Intent[] arrintent, String[] arrstring, Intent intent, String string2, int n, int n2) throws RemoteException {
                Parcel parcel;
                void var1_10;
                Parcel parcel2;
                block16 : {
                    void var4_13;
                    void var3_12;
                    void var2_11;
                    block15 : {
                        block14 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            if (parcelable != null) {
                                parcel.writeInt(1);
                                ((ComponentName)parcelable).writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        try {
                            parcel.writeTypedArray((Parcelable[])var2_11, 0);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel.writeStringArray((String[])var3_12);
                            if (var4_13 != null) {
                                parcel.writeInt(1);
                                var4_13.writeToParcel(parcel, 0);
                                break block15;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        void var5_14;
                        void var6_15;
                        void var1_5;
                        void var7_16;
                        parcel.writeString((String)var5_14);
                        parcel.writeInt((int)var6_15);
                        parcel.writeInt((int)var7_16);
                        if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            ParceledListSlice parceledListSlice = Stub.getDefaultImpl().queryIntentActivityOptions((ComponentName)parcelable, (Intent[])var2_11, (String[])var3_12, (Intent)var4_13, (String)var5_14, (int)var6_15, (int)var7_16);
                            parcel2.recycle();
                            parcel.recycle();
                            return parceledListSlice;
                        }
                        parcel2.readException();
                        if (parcel2.readInt() != 0) {
                            ParceledListSlice parceledListSlice = (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2);
                        } else {
                            Object var1_4 = null;
                        }
                        parcel2.recycle();
                        parcel.recycle();
                        return var1_5;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_10;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice queryIntentContentProviders(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().queryIntentContentProviders((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParceledListSlice parceledListSlice = (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice queryIntentReceivers(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().queryIntentReceivers((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParceledListSlice parceledListSlice = (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParceledListSlice queryIntentServices(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().queryIntentServices((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParceledListSlice parceledListSlice = (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public ParceledListSlice queryPermissionsByGroup(String parceledListSlice, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)((Object)parceledListSlice));
                        parcel2.writeInt(n);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        parceledListSlice = Stub.getDefaultImpl().queryPermissionsByGroup((String)((Object)parceledListSlice), n);
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public void querySyncProviders(List<String> list, List<ProviderInfo> list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    parcel.writeTypedList(list2);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().querySyncProviders(list, list2);
                        return;
                    }
                    parcel2.readException();
                    parcel2.readStringList(list);
                    parcel2.readTypedList(list2, ProviderInfo.CREATOR);
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void reconcileSecondaryDexFiles(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(125, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reconcileSecondaryDexFiles(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerDexModule(String string2, String string3, boolean bl, IDexModuleRegisterCallback iDexModuleRegisterCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder = iDexModuleRegisterCallback != null ? iDexModuleRegisterCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(118, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerDexModule(string2, string3, bl, iDexModuleRegisterCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPackageMoveObserver != null ? iPackageMoveObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(127, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerMoveCallback(iPackageMoveObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeOnPermissionsChangeListener(IOnPermissionsChangeListener iOnPermissionsChangeListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnPermissionsChangeListener != null ? iOnPermissionsChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(163, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnPermissionsChangeListener(iOnPermissionsChangeListener);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removePermission(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePermission(string2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean removeWhitelistedRestrictedPermission(String string2, String string3, int n, int n2) throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeString(string3);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(31, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeWhitelistedRestrictedPermission(string2, string3, n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void replacePreferredActivity(IntentFilter intentFilter, int n, ComponentName[] arrcomponentName, ComponentName componentName, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeTypedArray((Parcelable[])arrcomponentName, 0);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(73, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().replacePreferredActivity(intentFilter, n, arrcomponentName, componentName, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void resetApplicationPreferences(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetApplicationPreferences(n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void resetRuntimePermissions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetRuntimePermissions();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ProviderInfo resolveContentProvider(String object, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        if (this.mRemote.transact(58, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().resolveContentProvider((String)object, n, n2);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? ProviderInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ResolveInfo resolveIntent(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ResolveInfo resolveInfo = Stub.getDefaultImpl().resolveIntent((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return resolveInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ResolveInfo resolveService(Intent parcelable, String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((Intent)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    parcel.writeInt((int)var3_8);
                    parcel.writeInt((int)var4_9);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ResolveInfo resolveInfo = Stub.getDefaultImpl().resolveService((Intent)parcelable, (String)var2_7, (int)var3_8, (int)var4_9);
                        parcel2.recycle();
                        parcel.recycle();
                        return resolveInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ResolveInfo resolveInfo = ResolveInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void restoreDefaultApps(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(88, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreDefaultApps(arrby, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void restoreIntentFilterVerification(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(90, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restoreIntentFilterVerification(arrby, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void restorePreferredActivities(byte[] arrby, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restorePreferredActivities(arrby, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void revokeDefaultPermissionsFromDisabledTelephonyDataServices(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(167, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeDefaultPermissionsFromDisabledTelephonyDataServices(arrstring, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void revokeDefaultPermissionsFromLuiApps(String[] arrstring, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(169, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeDefaultPermissionsFromLuiApps(arrstring, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void revokeRuntimePermission(String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().revokeRuntimePermission(string2, string3, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean runBackgroundDexoptJob(List<String> list) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStringList(list);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(124, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().runBackgroundDexoptJob(list);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void sendDeviceCustomizationReadyBroadcast() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(204, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendDeviceCustomizationReadyBroadcast();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setApplicationCategoryHint(String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setApplicationCategoryHint(string2, n, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setApplicationEnabledSetting(String string2, int n, int n2, int n3, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setApplicationEnabledSetting(string2, n, n2, n3, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setApplicationHiddenSettingAsUser(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(151, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setApplicationHiddenSettingAsUser(string2, bl, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean setBlockUninstallForUser(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(156, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setBlockUninstallForUser(string2, bl, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setComponentEnabledSetting(ComponentName componentName, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(93, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setComponentEnabledSetting(componentName, n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setDefaultBrowserPackageName(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(142, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setDefaultBrowserPackageName(string2, n);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public String[] setDistractingPackageRestrictionsAsUser(String[] arrstring, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringArray(arrstring);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(80, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().setDistractingPackageRestrictionsAsUser(arrstring, n, n2);
                        return arrstring;
                    }
                    parcel2.readException();
                    arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setHarmfulAppWarning(String string2, CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(193, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHarmfulAppWarning(string2, charSequence, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setHomeActivity(ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(92, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHomeActivity(componentName, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setInstallLocation(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(132, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setInstallLocation(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setInstallerPackageName(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInstallerPackageName(string2, string3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setInstantAppCookie(String string2, byte[] arrby, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeByteArray(arrby);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(174, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().setInstantAppCookie(string2, arrby, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void setLastChosenActivity(Intent intent, String string2, int n, IntentFilter intentFilter, int n2, ComponentName componentName) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block16 : {
                    block15 : {
                        block14 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            if (intent != null) {
                                parcel2.writeInt(1);
                                intent.writeToParcel(parcel2, 0);
                                break block14;
                            }
                            parcel2.writeInt(0);
                        }
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block16;
                        }
                        try {
                            parcel2.writeInt(n);
                            if (intentFilter != null) {
                                parcel2.writeInt(1);
                                intentFilter.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n2);
                        if (componentName != null) {
                            parcel2.writeInt(1);
                            componentName.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(71, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setLastChosenActivity(intent, string2, n, intentFilter, n2, componentName);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_6;
            }

            @Override
            public void setPackageStoppedState(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(99, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPackageStoppedState(string2, bl, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public String[] setPackagesSuspendedAsUser(String[] arrstring, boolean bl, PersistableBundle persistableBundle, PersistableBundle persistableBundle2, SuspendDialogInfo suspendDialogInfo, String string2, int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_5;
                block14 : {
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeStringArray(arrstring);
                            int n2 = bl ? 1 : 0;
                            parcel2.writeInt(n2);
                            if (persistableBundle != null) {
                                parcel2.writeInt(1);
                                persistableBundle.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (persistableBundle2 != null) {
                                parcel2.writeInt(1);
                                persistableBundle2.writeToParcel(parcel2, 0);
                            } else {
                                parcel2.writeInt(0);
                            }
                            if (suspendDialogInfo != null) {
                                parcel2.writeInt(1);
                                suspendDialogInfo.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        if (!this.mRemote.transact(81, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            arrstring = Stub.getDefaultImpl().setPackagesSuspendedAsUser(arrstring, bl, persistableBundle, persistableBundle2, suspendDialogInfo, string2, n);
                            parcel.recycle();
                            parcel2.recycle();
                            return arrstring;
                        }
                        parcel.readException();
                        arrstring = parcel.createStringArray();
                        parcel.recycle();
                        parcel2.recycle();
                        return arrstring;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_5;
            }

            @Override
            public void setPermissionEnforced(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(148, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPermissionEnforced(string2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setRequiredForSystemUser(String string2, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl2;
                int n;
                block4 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(177, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setRequiredForSystemUser(string2, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                bl = n != 0 ? bl2 : false;
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void setRuntimePermissionsVersion(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(208, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRuntimePermissionsVersion(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSystemAppHiddenUntilInstalled(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(153, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemAppHiddenUntilInstalled(string2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean setSystemAppInstallState(String string2, boolean bl, int n) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(154, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setSystemAppInstallState(string2, bl, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setUpdateAvailable(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(178, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUpdateAvailable(string2, bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean shouldShowRequestPermissionRationale(String string2, String string3, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(32, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().shouldShowRequestPermissionRationale(string2, string3, n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void systemReady() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(112, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().systemReady();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterMoveCallback(IPackageMoveObserver iPackageMoveObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPackageMoveObserver != null ? iPackageMoveObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(128, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterMoveCallback(iPackageMoveObserver);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean updateIntentVerificationStatus(String string2, int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(139, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().updateIntentVerificationStatus(string2, n, n2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void updatePackagesIfNeeded() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePackagesIfNeeded();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void updatePermissionFlags(String string2, String string3, int n, int n2, boolean bl, int n3) throws RemoteException {
                Parcel parcel;
                void var1_9;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                        int n4 = bl ? 1 : 0;
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(27, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().updatePermissionFlags(string2, string3, n, n2, bl, n3);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_9;
            }

            @Override
            public void updatePermissionFlagsForAllApps(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePermissionFlagsForAllApps(n, n2, n3);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void verifyIntentFilter(int n, int n2, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(137, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().verifyIntentFilter(n, n2, list);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void verifyPendingInstall(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(135, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().verifyPendingInstall(n, n2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

