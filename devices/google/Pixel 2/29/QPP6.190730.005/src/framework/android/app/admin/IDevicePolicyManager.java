/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.admin.NetworkEvent;
import android.app.admin.PasswordMetrics;
import android.app.admin.StartInstallingUpdateCallback;
import android.app.admin.SystemUpdateInfo;
import android.app.admin.SystemUpdatePolicy;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageDataObserver;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.graphics.Bitmap;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.UserHandle;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IDevicePolicyManager
extends IInterface {
    public void addCrossProfileIntentFilter(ComponentName var1, IntentFilter var2, int var3) throws RemoteException;

    public boolean addCrossProfileWidgetProvider(ComponentName var1, String var2) throws RemoteException;

    public int addOverrideApn(ComponentName var1, ApnSetting var2) throws RemoteException;

    public void addPersistentPreferredActivity(ComponentName var1, IntentFilter var2, ComponentName var3) throws RemoteException;

    public boolean approveCaCert(String var1, int var2, boolean var3) throws RemoteException;

    public boolean bindDeviceAdminServiceAsUser(ComponentName var1, IApplicationThread var2, IBinder var3, Intent var4, IServiceConnection var5, int var6, int var7) throws RemoteException;

    public boolean checkDeviceIdentifierAccess(String var1, int var2, int var3) throws RemoteException;

    public int checkProvisioningPreCondition(String var1, String var2) throws RemoteException;

    public void choosePrivateKeyAlias(int var1, Uri var2, String var3, IBinder var4) throws RemoteException;

    public void clearApplicationUserData(ComponentName var1, String var2, IPackageDataObserver var3) throws RemoteException;

    public void clearCrossProfileIntentFilters(ComponentName var1) throws RemoteException;

    public void clearDeviceOwner(String var1) throws RemoteException;

    public void clearPackagePersistentPreferredActivities(ComponentName var1, String var2) throws RemoteException;

    public void clearProfileOwner(ComponentName var1) throws RemoteException;

    public boolean clearResetPasswordToken(ComponentName var1) throws RemoteException;

    public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException;

    public Intent createAdminSupportIntent(String var1) throws RemoteException;

    public UserHandle createAndManageUser(ComponentName var1, String var2, ComponentName var3, PersistableBundle var4, int var5) throws RemoteException;

    public void enableSystemApp(ComponentName var1, String var2, String var3) throws RemoteException;

    public int enableSystemAppWithIntent(ComponentName var1, String var2, Intent var3) throws RemoteException;

    public void enforceCanManageCaCerts(ComponentName var1, String var2) throws RemoteException;

    public long forceNetworkLogs() throws RemoteException;

    public void forceRemoveActiveAdmin(ComponentName var1, int var2) throws RemoteException;

    public long forceSecurityLogs() throws RemoteException;

    public void forceUpdateUserSetupComplete() throws RemoteException;

    public boolean generateKeyPair(ComponentName var1, String var2, String var3, ParcelableKeyGenParameterSpec var4, int var5, KeymasterCertificateChain var6) throws RemoteException;

    public String[] getAccountTypesWithManagementDisabled() throws RemoteException;

    public String[] getAccountTypesWithManagementDisabledAsUser(int var1) throws RemoteException;

    public List<ComponentName> getActiveAdmins(int var1) throws RemoteException;

    public List<String> getAffiliationIds(ComponentName var1) throws RemoteException;

    public List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName var1) throws RemoteException;

    public String getAlwaysOnVpnPackage(ComponentName var1) throws RemoteException;

    public Bundle getApplicationRestrictions(ComponentName var1, String var2, String var3) throws RemoteException;

    public String getApplicationRestrictionsManagingPackage(ComponentName var1) throws RemoteException;

    public boolean getAutoTimeRequired() throws RemoteException;

    public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName var1) throws RemoteException;

    public boolean getBluetoothContactSharingDisabled(ComponentName var1) throws RemoteException;

    public boolean getBluetoothContactSharingDisabledForUser(int var1) throws RemoteException;

    public boolean getCameraDisabled(ComponentName var1, int var2) throws RemoteException;

    public String getCertInstallerPackage(ComponentName var1) throws RemoteException;

    public List<String> getCrossProfileCalendarPackages(ComponentName var1) throws RemoteException;

    public List<String> getCrossProfileCalendarPackagesForUser(int var1) throws RemoteException;

    public boolean getCrossProfileCallerIdDisabled(ComponentName var1) throws RemoteException;

    public boolean getCrossProfileCallerIdDisabledForUser(int var1) throws RemoteException;

    public boolean getCrossProfileContactsSearchDisabled(ComponentName var1) throws RemoteException;

    public boolean getCrossProfileContactsSearchDisabledForUser(int var1) throws RemoteException;

    public List<String> getCrossProfileWidgetProviders(ComponentName var1) throws RemoteException;

    public int getCurrentFailedPasswordAttempts(int var1, boolean var2) throws RemoteException;

    public List<String> getDelegatePackages(ComponentName var1, String var2) throws RemoteException;

    public List<String> getDelegatedScopes(ComponentName var1, String var2) throws RemoteException;

    public ComponentName getDeviceOwnerComponent(boolean var1) throws RemoteException;

    public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException;

    public String getDeviceOwnerName() throws RemoteException;

    public CharSequence getDeviceOwnerOrganizationName() throws RemoteException;

    public int getDeviceOwnerUserId() throws RemoteException;

    public List<String> getDisallowedSystemApps(ComponentName var1, int var2, String var3) throws RemoteException;

    public boolean getDoNotAskCredentialsOnBoot() throws RemoteException;

    public CharSequence getEndUserSessionMessage(ComponentName var1) throws RemoteException;

    public boolean getForceEphemeralUsers(ComponentName var1) throws RemoteException;

    public String getGlobalPrivateDnsHost(ComponentName var1) throws RemoteException;

    public int getGlobalPrivateDnsMode(ComponentName var1) throws RemoteException;

    public ComponentName getGlobalProxyAdmin(int var1) throws RemoteException;

    public List<String> getKeepUninstalledPackages(ComponentName var1, String var2) throws RemoteException;

    public int getKeyguardDisabledFeatures(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public long getLastBugReportRequestTime() throws RemoteException;

    public long getLastNetworkLogRetrievalTime() throws RemoteException;

    public long getLastSecurityLogRetrievalTime() throws RemoteException;

    public int getLockTaskFeatures(ComponentName var1) throws RemoteException;

    public String[] getLockTaskPackages(ComponentName var1) throws RemoteException;

    public CharSequence getLongSupportMessage(ComponentName var1) throws RemoteException;

    public CharSequence getLongSupportMessageForUser(ComponentName var1, int var2) throws RemoteException;

    public int getMaximumFailedPasswordsForWipe(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public long getMaximumTimeToLock(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public List<String> getMeteredDataDisabledPackages(ComponentName var1) throws RemoteException;

    public int getOrganizationColor(ComponentName var1) throws RemoteException;

    public int getOrganizationColorForUser(int var1) throws RemoteException;

    public CharSequence getOrganizationName(ComponentName var1) throws RemoteException;

    public CharSequence getOrganizationNameForUser(int var1) throws RemoteException;

    public List<ApnSetting> getOverrideApns(ComponentName var1) throws RemoteException;

    public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle var1) throws RemoteException;

    public int getPasswordComplexity() throws RemoteException;

    public long getPasswordExpiration(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public long getPasswordExpirationTimeout(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordHistoryLength(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumLength(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumLetters(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumLowerCase(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumNonLetter(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumNumeric(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumSymbols(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordMinimumUpperCase(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public int getPasswordQuality(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public SystemUpdateInfo getPendingSystemUpdate(ComponentName var1) throws RemoteException;

    public int getPermissionGrantState(ComponentName var1, String var2, String var3, String var4) throws RemoteException;

    public int getPermissionPolicy(ComponentName var1) throws RemoteException;

    public List getPermittedAccessibilityServices(ComponentName var1) throws RemoteException;

    public List getPermittedAccessibilityServicesForUser(int var1) throws RemoteException;

    public List<String> getPermittedCrossProfileNotificationListeners(ComponentName var1) throws RemoteException;

    public List getPermittedInputMethods(ComponentName var1) throws RemoteException;

    public List getPermittedInputMethodsForCurrentUser() throws RemoteException;

    public ComponentName getProfileOwner(int var1) throws RemoteException;

    public ComponentName getProfileOwnerAsUser(int var1) throws RemoteException;

    public String getProfileOwnerName(int var1) throws RemoteException;

    public int getProfileWithMinimumFailedPasswordsForWipe(int var1, boolean var2) throws RemoteException;

    public void getRemoveWarning(ComponentName var1, RemoteCallback var2, int var3) throws RemoteException;

    public long getRequiredStrongAuthTimeout(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public ComponentName getRestrictionsProvider(int var1) throws RemoteException;

    public boolean getScreenCaptureDisabled(ComponentName var1, int var2) throws RemoteException;

    public List<UserHandle> getSecondaryUsers(ComponentName var1) throws RemoteException;

    public CharSequence getShortSupportMessage(ComponentName var1) throws RemoteException;

    public CharSequence getShortSupportMessageForUser(ComponentName var1, int var2) throws RemoteException;

    public CharSequence getStartUserSessionMessage(ComponentName var1) throws RemoteException;

    public boolean getStorageEncryption(ComponentName var1, int var2) throws RemoteException;

    public int getStorageEncryptionStatus(String var1, int var2) throws RemoteException;

    public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException;

    public PersistableBundle getTransferOwnershipBundle() throws RemoteException;

    public List<PersistableBundle> getTrustAgentConfiguration(ComponentName var1, ComponentName var2, int var3, boolean var4) throws RemoteException;

    public int getUserProvisioningState() throws RemoteException;

    public Bundle getUserRestrictions(ComponentName var1) throws RemoteException;

    public String getWifiMacAddress(ComponentName var1) throws RemoteException;

    public void grantDeviceIdsAccessToProfileOwner(ComponentName var1, int var2) throws RemoteException;

    public boolean hasDeviceOwner() throws RemoteException;

    public boolean hasGrantedPolicy(ComponentName var1, int var2, int var3) throws RemoteException;

    public boolean hasUserSetupCompleted() throws RemoteException;

    public boolean installCaCert(ComponentName var1, String var2, byte[] var3) throws RemoteException;

    public boolean installExistingPackage(ComponentName var1, String var2, String var3) throws RemoteException;

    public boolean installKeyPair(ComponentName var1, String var2, byte[] var3, byte[] var4, byte[] var5, String var6, boolean var7, boolean var8) throws RemoteException;

    public void installUpdateFromFile(ComponentName var1, ParcelFileDescriptor var2, StartInstallingUpdateCallback var3) throws RemoteException;

    public boolean isAccessibilityServicePermittedByAdmin(ComponentName var1, String var2, int var3) throws RemoteException;

    public boolean isActivePasswordSufficient(int var1, boolean var2) throws RemoteException;

    public boolean isAdminActive(ComponentName var1, int var2) throws RemoteException;

    public boolean isAffiliatedUser() throws RemoteException;

    public boolean isAlwaysOnVpnLockdownEnabled(ComponentName var1) throws RemoteException;

    public boolean isApplicationHidden(ComponentName var1, String var2, String var3) throws RemoteException;

    public boolean isBackupServiceEnabled(ComponentName var1) throws RemoteException;

    public boolean isCaCertApproved(String var1, int var2) throws RemoteException;

    public boolean isCallerApplicationRestrictionsManagingPackage(String var1) throws RemoteException;

    public boolean isCurrentInputMethodSetByOwner() throws RemoteException;

    public boolean isDeviceProvisioned() throws RemoteException;

    public boolean isDeviceProvisioningConfigApplied() throws RemoteException;

    public boolean isEphemeralUser(ComponentName var1) throws RemoteException;

    public boolean isInputMethodPermittedByAdmin(ComponentName var1, String var2, int var3) throws RemoteException;

    public boolean isLockTaskPermitted(String var1) throws RemoteException;

    public boolean isLogoutEnabled() throws RemoteException;

    public boolean isManagedKiosk() throws RemoteException;

    public boolean isManagedProfile(ComponentName var1) throws RemoteException;

    public boolean isMasterVolumeMuted(ComponentName var1) throws RemoteException;

    public boolean isMeteredDataDisabledPackageForUser(ComponentName var1, String var2, int var3) throws RemoteException;

    public boolean isNetworkLoggingEnabled(ComponentName var1, String var2) throws RemoteException;

    public boolean isNotificationListenerServicePermitted(String var1, int var2) throws RemoteException;

    public boolean isOverrideApnEnabled(ComponentName var1) throws RemoteException;

    public boolean isPackageAllowedToAccessCalendarForUser(String var1, int var2) throws RemoteException;

    public boolean isPackageSuspended(ComponentName var1, String var2, String var3) throws RemoteException;

    public boolean isProfileActivePasswordSufficientForParent(int var1) throws RemoteException;

    public boolean isProvisioningAllowed(String var1, String var2) throws RemoteException;

    public boolean isRemovingAdmin(ComponentName var1, int var2) throws RemoteException;

    public boolean isResetPasswordTokenActive(ComponentName var1) throws RemoteException;

    public boolean isSecurityLoggingEnabled(ComponentName var1) throws RemoteException;

    public boolean isSeparateProfileChallengeAllowed(int var1) throws RemoteException;

    public boolean isSystemOnlyUser(ComponentName var1) throws RemoteException;

    public boolean isUnattendedManagedKiosk() throws RemoteException;

    public boolean isUninstallBlocked(ComponentName var1, String var2) throws RemoteException;

    public boolean isUninstallInQueue(String var1) throws RemoteException;

    public boolean isUsingUnifiedPassword(ComponentName var1) throws RemoteException;

    public void lockNow(int var1, boolean var2) throws RemoteException;

    public int logoutUser(ComponentName var1) throws RemoteException;

    public void notifyLockTaskModeChanged(boolean var1, String var2, int var3) throws RemoteException;

    public void notifyPendingSystemUpdate(SystemUpdateInfo var1) throws RemoteException;

    @UnsupportedAppUsage
    public boolean packageHasActiveAdmins(String var1, int var2) throws RemoteException;

    public void reboot(ComponentName var1) throws RemoteException;

    public void removeActiveAdmin(ComponentName var1, int var2) throws RemoteException;

    public boolean removeCrossProfileWidgetProvider(ComponentName var1, String var2) throws RemoteException;

    public boolean removeKeyPair(ComponentName var1, String var2, String var3) throws RemoteException;

    public boolean removeOverrideApn(ComponentName var1, int var2) throws RemoteException;

    public boolean removeUser(ComponentName var1, UserHandle var2) throws RemoteException;

    public void reportFailedBiometricAttempt(int var1) throws RemoteException;

    public void reportFailedPasswordAttempt(int var1) throws RemoteException;

    public void reportKeyguardDismissed(int var1) throws RemoteException;

    public void reportKeyguardSecured(int var1) throws RemoteException;

    public void reportPasswordChanged(int var1) throws RemoteException;

    public void reportSuccessfulBiometricAttempt(int var1) throws RemoteException;

    public void reportSuccessfulPasswordAttempt(int var1) throws RemoteException;

    public boolean requestBugreport(ComponentName var1) throws RemoteException;

    public boolean resetPassword(String var1, int var2) throws RemoteException;

    public boolean resetPasswordWithToken(ComponentName var1, String var2, byte[] var3, int var4) throws RemoteException;

    public List<NetworkEvent> retrieveNetworkLogs(ComponentName var1, String var2, long var3) throws RemoteException;

    public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName var1) throws RemoteException;

    public ParceledListSlice retrieveSecurityLogs(ComponentName var1) throws RemoteException;

    public void setAccountManagementDisabled(ComponentName var1, String var2, boolean var3) throws RemoteException;

    public void setActiveAdmin(ComponentName var1, boolean var2, int var3) throws RemoteException;

    public void setActivePasswordState(PasswordMetrics var1, int var2) throws RemoteException;

    public void setAffiliationIds(ComponentName var1, List<String> var2) throws RemoteException;

    public boolean setAlwaysOnVpnPackage(ComponentName var1, String var2, boolean var3, List<String> var4) throws RemoteException;

    public boolean setApplicationHidden(ComponentName var1, String var2, String var3, boolean var4) throws RemoteException;

    public void setApplicationRestrictions(ComponentName var1, String var2, String var3, Bundle var4) throws RemoteException;

    public boolean setApplicationRestrictionsManagingPackage(ComponentName var1, String var2) throws RemoteException;

    public void setAutoTimeRequired(ComponentName var1, boolean var2) throws RemoteException;

    public void setBackupServiceEnabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setBluetoothContactSharingDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setCameraDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setCertInstallerPackage(ComponentName var1, String var2) throws RemoteException;

    public void setCrossProfileCalendarPackages(ComponentName var1, List<String> var2) throws RemoteException;

    public void setCrossProfileCallerIdDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setCrossProfileContactsSearchDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setDefaultSmsApplication(ComponentName var1, String var2) throws RemoteException;

    public void setDelegatedScopes(ComponentName var1, String var2, List<String> var3) throws RemoteException;

    public boolean setDeviceOwner(ComponentName var1, String var2, int var3) throws RemoteException;

    public void setDeviceOwnerLockScreenInfo(ComponentName var1, CharSequence var2) throws RemoteException;

    public void setDeviceProvisioningConfigApplied() throws RemoteException;

    public void setEndUserSessionMessage(ComponentName var1, CharSequence var2) throws RemoteException;

    public void setForceEphemeralUsers(ComponentName var1, boolean var2) throws RemoteException;

    public int setGlobalPrivateDns(ComponentName var1, int var2, String var3) throws RemoteException;

    public ComponentName setGlobalProxy(ComponentName var1, String var2, String var3) throws RemoteException;

    public void setGlobalSetting(ComponentName var1, String var2, String var3) throws RemoteException;

    public void setKeepUninstalledPackages(ComponentName var1, String var2, List<String> var3) throws RemoteException;

    public boolean setKeyPairCertificate(ComponentName var1, String var2, String var3, byte[] var4, byte[] var5, boolean var6) throws RemoteException;

    public boolean setKeyguardDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setKeyguardDisabledFeatures(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setLockTaskFeatures(ComponentName var1, int var2) throws RemoteException;

    public void setLockTaskPackages(ComponentName var1, String[] var2) throws RemoteException;

    public void setLogoutEnabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setLongSupportMessage(ComponentName var1, CharSequence var2) throws RemoteException;

    public void setMasterVolumeMuted(ComponentName var1, boolean var2) throws RemoteException;

    public void setMaximumFailedPasswordsForWipe(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setMaximumTimeToLock(ComponentName var1, long var2, boolean var4) throws RemoteException;

    public List<String> setMeteredDataDisabledPackages(ComponentName var1, List<String> var2) throws RemoteException;

    public void setNetworkLoggingEnabled(ComponentName var1, String var2, boolean var3) throws RemoteException;

    public void setOrganizationColor(ComponentName var1, int var2) throws RemoteException;

    public void setOrganizationColorForUser(int var1, int var2) throws RemoteException;

    public void setOrganizationName(ComponentName var1, CharSequence var2) throws RemoteException;

    public void setOverrideApnsEnabled(ComponentName var1, boolean var2) throws RemoteException;

    public String[] setPackagesSuspended(ComponentName var1, String var2, String[] var3, boolean var4) throws RemoteException;

    public void setPasswordExpirationTimeout(ComponentName var1, long var2, boolean var4) throws RemoteException;

    public void setPasswordHistoryLength(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumLength(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumLetters(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumLowerCase(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumNonLetter(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumNumeric(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumSymbols(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordMinimumUpperCase(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPasswordQuality(ComponentName var1, int var2, boolean var3) throws RemoteException;

    public void setPermissionGrantState(ComponentName var1, String var2, String var3, String var4, int var5, RemoteCallback var6) throws RemoteException;

    public void setPermissionPolicy(ComponentName var1, String var2, int var3) throws RemoteException;

    public boolean setPermittedAccessibilityServices(ComponentName var1, List var2) throws RemoteException;

    public boolean setPermittedCrossProfileNotificationListeners(ComponentName var1, List<String> var2) throws RemoteException;

    public boolean setPermittedInputMethods(ComponentName var1, List var2) throws RemoteException;

    public void setProfileEnabled(ComponentName var1) throws RemoteException;

    public void setProfileName(ComponentName var1, String var2) throws RemoteException;

    public boolean setProfileOwner(ComponentName var1, String var2, int var3) throws RemoteException;

    public void setRecommendedGlobalProxy(ComponentName var1, ProxyInfo var2) throws RemoteException;

    public void setRequiredStrongAuthTimeout(ComponentName var1, long var2, boolean var4) throws RemoteException;

    public boolean setResetPasswordToken(ComponentName var1, byte[] var2) throws RemoteException;

    public void setRestrictionsProvider(ComponentName var1, ComponentName var2) throws RemoteException;

    public void setScreenCaptureDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setSecureSetting(ComponentName var1, String var2, String var3) throws RemoteException;

    public void setSecurityLoggingEnabled(ComponentName var1, boolean var2) throws RemoteException;

    public void setShortSupportMessage(ComponentName var1, CharSequence var2) throws RemoteException;

    public void setStartUserSessionMessage(ComponentName var1, CharSequence var2) throws RemoteException;

    public boolean setStatusBarDisabled(ComponentName var1, boolean var2) throws RemoteException;

    public int setStorageEncryption(ComponentName var1, boolean var2) throws RemoteException;

    public void setSystemSetting(ComponentName var1, String var2, String var3) throws RemoteException;

    public void setSystemUpdatePolicy(ComponentName var1, SystemUpdatePolicy var2) throws RemoteException;

    public boolean setTime(ComponentName var1, long var2) throws RemoteException;

    public boolean setTimeZone(ComponentName var1, String var2) throws RemoteException;

    public void setTrustAgentConfiguration(ComponentName var1, ComponentName var2, PersistableBundle var3, boolean var4) throws RemoteException;

    public void setUninstallBlocked(ComponentName var1, String var2, String var3, boolean var4) throws RemoteException;

    public void setUserIcon(ComponentName var1, Bitmap var2) throws RemoteException;

    public void setUserProvisioningState(int var1, int var2) throws RemoteException;

    public void setUserRestriction(ComponentName var1, String var2, boolean var3) throws RemoteException;

    public void startManagedQuickContact(String var1, long var2, boolean var4, long var5, Intent var7) throws RemoteException;

    public int startUserInBackground(ComponentName var1, UserHandle var2) throws RemoteException;

    public boolean startViewCalendarEventInManagedProfile(String var1, long var2, long var4, long var6, boolean var8, int var9) throws RemoteException;

    public int stopUser(ComponentName var1, UserHandle var2) throws RemoteException;

    public boolean switchUser(ComponentName var1, UserHandle var2) throws RemoteException;

    public void transferOwnership(ComponentName var1, ComponentName var2, PersistableBundle var3) throws RemoteException;

    public void uninstallCaCerts(ComponentName var1, String var2, String[] var3) throws RemoteException;

    public void uninstallPackageWithActiveAdmins(String var1) throws RemoteException;

    public boolean updateOverrideApn(ComponentName var1, int var2, ApnSetting var3) throws RemoteException;

    public void wipeDataWithReason(int var1, String var2) throws RemoteException;

    public static class Default
    implements IDevicePolicyManager {
        @Override
        public void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int n) throws RemoteException {
        }

        @Override
        public boolean addCrossProfileWidgetProvider(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException {
            return 0;
        }

        @Override
        public void addPersistentPreferredActivity(ComponentName componentName, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException {
        }

        @Override
        public boolean approveCaCert(String string2, int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean checkDeviceIdentifierAccess(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public int checkProvisioningPreCondition(String string2, String string3) throws RemoteException {
            return 0;
        }

        @Override
        public void choosePrivateKeyAlias(int n, Uri uri, String string2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void clearApplicationUserData(ComponentName componentName, String string2, IPackageDataObserver iPackageDataObserver) throws RemoteException {
        }

        @Override
        public void clearCrossProfileIntentFilters(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void clearDeviceOwner(String string2) throws RemoteException {
        }

        @Override
        public void clearPackagePersistentPreferredActivities(ComponentName componentName, String string2) throws RemoteException {
        }

        @Override
        public void clearProfileOwner(ComponentName componentName) throws RemoteException {
        }

        @Override
        public boolean clearResetPasswordToken(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
        }

        @Override
        public Intent createAdminSupportIntent(String string2) throws RemoteException {
            return null;
        }

        @Override
        public UserHandle createAndManageUser(ComponentName componentName, String string2, ComponentName componentName2, PersistableBundle persistableBundle, int n) throws RemoteException {
            return null;
        }

        @Override
        public void enableSystemApp(ComponentName componentName, String string2, String string3) throws RemoteException {
        }

        @Override
        public int enableSystemAppWithIntent(ComponentName componentName, String string2, Intent intent) throws RemoteException {
            return 0;
        }

        @Override
        public void enforceCanManageCaCerts(ComponentName componentName, String string2) throws RemoteException {
        }

        @Override
        public long forceNetworkLogs() throws RemoteException {
            return 0L;
        }

        @Override
        public void forceRemoveActiveAdmin(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public long forceSecurityLogs() throws RemoteException {
            return 0L;
        }

        @Override
        public void forceUpdateUserSetupComplete() throws RemoteException {
        }

        @Override
        public boolean generateKeyPair(ComponentName componentName, String string2, String string3, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int n, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
            return false;
        }

        @Override
        public String[] getAccountTypesWithManagementDisabled() throws RemoteException {
            return null;
        }

        @Override
        public String[] getAccountTypesWithManagementDisabledAsUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<ComponentName> getActiveAdmins(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getAffiliationIds(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public String getAlwaysOnVpnPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public Bundle getApplicationRestrictions(ComponentName componentName, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public String getApplicationRestrictionsManagingPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public boolean getAutoTimeRequired() throws RemoteException {
            return false;
        }

        @Override
        public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean getBluetoothContactSharingDisabledForUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean getCameraDisabled(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public String getCertInstallerPackage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getCrossProfileCalendarPackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getCrossProfileCalendarPackagesForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean getCrossProfileCallerIdDisabledForUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean getCrossProfileContactsSearchDisabledForUser(int n) throws RemoteException {
            return false;
        }

        @Override
        public List<String> getCrossProfileWidgetProviders(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public int getCurrentFailedPasswordAttempts(int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getDelegatePackages(ComponentName componentName, String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getDelegatedScopes(ComponentName componentName, String string2) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getDeviceOwnerComponent(boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
            return null;
        }

        @Override
        public String getDeviceOwnerName() throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
            return null;
        }

        @Override
        public int getDeviceOwnerUserId() throws RemoteException {
            return 0;
        }

        @Override
        public List<String> getDisallowedSystemApps(ComponentName componentName, int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
            return false;
        }

        @Override
        public CharSequence getEndUserSessionMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public String getGlobalPrivateDnsHost(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public ComponentName getGlobalProxyAdmin(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getKeepUninstalledPackages(ComponentName componentName, String string2) throws RemoteException {
            return null;
        }

        @Override
        public int getKeyguardDisabledFeatures(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public long getLastBugReportRequestTime() throws RemoteException {
            return 0L;
        }

        @Override
        public long getLastNetworkLogRetrievalTime() throws RemoteException {
            return 0L;
        }

        @Override
        public long getLastSecurityLogRetrievalTime() throws RemoteException {
            return 0L;
        }

        @Override
        public int getLockTaskFeatures(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public String[] getLockTaskPackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getLongSupportMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getLongSupportMessageForUser(ComponentName componentName, int n) throws RemoteException {
            return null;
        }

        @Override
        public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public long getMaximumTimeToLock(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0L;
        }

        @Override
        public List<String> getMeteredDataDisabledPackages(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public int getOrganizationColor(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public int getOrganizationColorForUser(int n) throws RemoteException {
            return 0;
        }

        @Override
        public CharSequence getOrganizationName(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getOrganizationNameForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<ApnSetting> getOverrideApns(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override
        public int getPasswordComplexity() throws RemoteException {
            return 0;
        }

        @Override
        public long getPasswordExpiration(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0L;
        }

        @Override
        public long getPasswordExpirationTimeout(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0L;
        }

        @Override
        public int getPasswordHistoryLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumLetters(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumLowerCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumNonLetter(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumNumeric(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumSymbols(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordMinimumUpperCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public int getPasswordQuality(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public SystemUpdateInfo getPendingSystemUpdate(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public int getPermissionGrantState(ComponentName componentName, String string2, String string3, String string4) throws RemoteException {
            return 0;
        }

        @Override
        public int getPermissionPolicy(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public List getPermittedAccessibilityServices(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List getPermittedAccessibilityServicesForUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getPermittedCrossProfileNotificationListeners(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List getPermittedInputMethods(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public List getPermittedInputMethodsForCurrentUser() throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getProfileOwner(int n) throws RemoteException {
            return null;
        }

        @Override
        public ComponentName getProfileOwnerAsUser(int n) throws RemoteException {
            return null;
        }

        @Override
        public String getProfileOwnerName(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getProfileWithMinimumFailedPasswordsForWipe(int n, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int n) throws RemoteException {
        }

        @Override
        public long getRequiredStrongAuthTimeout(ComponentName componentName, int n, boolean bl) throws RemoteException {
            return 0L;
        }

        @Override
        public ComponentName getRestrictionsProvider(int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean getScreenCaptureDisabled(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public List<UserHandle> getSecondaryUsers(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getShortSupportMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getShortSupportMessageForUser(ComponentName componentName, int n) throws RemoteException {
            return null;
        }

        @Override
        public CharSequence getStartUserSessionMessage(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public boolean getStorageEncryption(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public int getStorageEncryptionStatus(String string2, int n) throws RemoteException {
            return 0;
        }

        @Override
        public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
            return null;
        }

        @Override
        public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
            return null;
        }

        @Override
        public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public int getUserProvisioningState() throws RemoteException {
            return 0;
        }

        @Override
        public Bundle getUserRestrictions(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public String getWifiMacAddress(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public void grantDeviceIdsAccessToProfileOwner(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public boolean hasDeviceOwner() throws RemoteException {
            return false;
        }

        @Override
        public boolean hasGrantedPolicy(ComponentName componentName, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasUserSetupCompleted() throws RemoteException {
            return false;
        }

        @Override
        public boolean installCaCert(ComponentName componentName, String string2, byte[] arrby) throws RemoteException {
            return false;
        }

        @Override
        public boolean installExistingPackage(ComponentName componentName, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean installKeyPair(ComponentName componentName, String string2, byte[] arrby, byte[] arrby2, byte[] arrby3, String string3, boolean bl, boolean bl2) throws RemoteException {
            return false;
        }

        @Override
        public void installUpdateFromFile(ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException {
        }

        @Override
        public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isActivePasswordSufficient(int n, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean isAdminActive(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isAffiliatedUser() throws RemoteException {
            return false;
        }

        @Override
        public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isApplicationHidden(ComponentName componentName, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isCaCertApproved(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isCallerApplicationRestrictionsManagingPackage(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
            return false;
        }

        @Override
        public boolean isDeviceProvisioned() throws RemoteException {
            return false;
        }

        @Override
        public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
            return false;
        }

        @Override
        public boolean isEphemeralUser(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isLockTaskPermitted(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isLogoutEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isManagedKiosk() throws RemoteException {
            return false;
        }

        @Override
        public boolean isManagedProfile(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNetworkLoggingEnabled(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isNotificationListenerServicePermitted(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageAllowedToAccessCalendarForUser(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isPackageSuspended(ComponentName componentName, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isProfileActivePasswordSufficientForParent(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isProvisioningAllowed(String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRemovingAdmin(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isResetPasswordTokenActive(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSecurityLoggingEnabled(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSeparateProfileChallengeAllowed(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isSystemOnlyUser(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUnattendedManagedKiosk() throws RemoteException {
            return false;
        }

        @Override
        public boolean isUninstallBlocked(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUninstallInQueue(String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public void lockNow(int n, boolean bl) throws RemoteException {
        }

        @Override
        public int logoutUser(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override
        public void notifyLockTaskModeChanged(boolean bl, String string2, int n) throws RemoteException {
        }

        @Override
        public void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException {
        }

        @Override
        public boolean packageHasActiveAdmins(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void reboot(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void removeActiveAdmin(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeKeyPair(ComponentName componentName, String string2, String string3) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeOverrideApn(ComponentName componentName, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public void reportFailedBiometricAttempt(int n) throws RemoteException {
        }

        @Override
        public void reportFailedPasswordAttempt(int n) throws RemoteException {
        }

        @Override
        public void reportKeyguardDismissed(int n) throws RemoteException {
        }

        @Override
        public void reportKeyguardSecured(int n) throws RemoteException {
        }

        @Override
        public void reportPasswordChanged(int n) throws RemoteException {
        }

        @Override
        public void reportSuccessfulBiometricAttempt(int n) throws RemoteException {
        }

        @Override
        public void reportSuccessfulPasswordAttempt(int n) throws RemoteException {
        }

        @Override
        public boolean requestBugreport(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override
        public boolean resetPassword(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean resetPasswordWithToken(ComponentName componentName, String string2, byte[] arrby, int n) throws RemoteException {
            return false;
        }

        @Override
        public List<NetworkEvent> retrieveNetworkLogs(ComponentName componentName, String string2, long l) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice retrieveSecurityLogs(ComponentName componentName) throws RemoteException {
            return null;
        }

        @Override
        public void setAccountManagementDisabled(ComponentName componentName, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setActiveAdmin(ComponentName componentName, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setActivePasswordState(PasswordMetrics passwordMetrics, int n) throws RemoteException {
        }

        @Override
        public void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException {
        }

        @Override
        public boolean setAlwaysOnVpnPackage(ComponentName componentName, String string2, boolean bl, List<String> list) throws RemoteException {
            return false;
        }

        @Override
        public boolean setApplicationHidden(ComponentName componentName, String string2, String string3, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setApplicationRestrictions(ComponentName componentName, String string2, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void setAutoTimeRequired(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setBackupServiceEnabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setCameraDisabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setCertInstallerPackage(ComponentName componentName, String string2) throws RemoteException {
        }

        @Override
        public void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException {
        }

        @Override
        public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setDefaultSmsApplication(ComponentName componentName, String string2) throws RemoteException {
        }

        @Override
        public void setDelegatedScopes(ComponentName componentName, String string2, List<String> list) throws RemoteException {
        }

        @Override
        public boolean setDeviceOwner(ComponentName componentName, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setDeviceProvisioningConfigApplied() throws RemoteException {
        }

        @Override
        public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setForceEphemeralUsers(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public int setGlobalPrivateDns(ComponentName componentName, int n, String string2) throws RemoteException {
            return 0;
        }

        @Override
        public ComponentName setGlobalProxy(ComponentName componentName, String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void setGlobalSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setKeepUninstalledPackages(ComponentName componentName, String string2, List<String> list) throws RemoteException {
        }

        @Override
        public boolean setKeyPairCertificate(ComponentName componentName, String string2, String string3, byte[] arrby, byte[] arrby2, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean setKeyguardDisabled(ComponentName componentName, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setKeyguardDisabledFeatures(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setLockTaskFeatures(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public void setLockTaskPackages(ComponentName componentName, String[] arrstring) throws RemoteException {
        }

        @Override
        public void setLogoutEnabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setMasterVolumeMuted(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setMaximumFailedPasswordsForWipe(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setMaximumTimeToLock(ComponentName componentName, long l, boolean bl) throws RemoteException {
        }

        @Override
        public List<String> setMeteredDataDisabledPackages(ComponentName componentName, List<String> list) throws RemoteException {
            return null;
        }

        @Override
        public void setNetworkLoggingEnabled(ComponentName componentName, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setOrganizationColor(ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public void setOrganizationColorForUser(int n, int n2) throws RemoteException {
        }

        @Override
        public void setOrganizationName(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setOverrideApnsEnabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public String[] setPackagesSuspended(ComponentName componentName, String string2, String[] arrstring, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public void setPasswordExpirationTimeout(ComponentName componentName, long l, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordHistoryLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumLetters(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumLowerCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumNonLetter(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumNumeric(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumSymbols(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordMinimumUpperCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPasswordQuality(ComponentName componentName, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPermissionGrantState(ComponentName componentName, String string2, String string3, String string4, int n, RemoteCallback remoteCallback) throws RemoteException {
        }

        @Override
        public void setPermissionPolicy(ComponentName componentName, String string2, int n) throws RemoteException {
        }

        @Override
        public boolean setPermittedAccessibilityServices(ComponentName componentName, List list) throws RemoteException {
            return false;
        }

        @Override
        public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException {
            return false;
        }

        @Override
        public boolean setPermittedInputMethods(ComponentName componentName, List list) throws RemoteException {
            return false;
        }

        @Override
        public void setProfileEnabled(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void setProfileName(ComponentName componentName, String string2) throws RemoteException {
        }

        @Override
        public boolean setProfileOwner(ComponentName componentName, String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException {
        }

        @Override
        public void setRequiredStrongAuthTimeout(ComponentName componentName, long l, boolean bl) throws RemoteException {
        }

        @Override
        public boolean setResetPasswordToken(ComponentName componentName, byte[] arrby) throws RemoteException {
            return false;
        }

        @Override
        public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException {
        }

        @Override
        public void setScreenCaptureDisabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setSecureSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setSecurityLoggingEnabled(ComponentName componentName, boolean bl) throws RemoteException {
        }

        @Override
        public void setShortSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public boolean setStatusBarDisabled(ComponentName componentName, boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public int setStorageEncryption(ComponentName componentName, boolean bl) throws RemoteException {
            return 0;
        }

        @Override
        public void setSystemSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
        }

        @Override
        public void setSystemUpdatePolicy(ComponentName componentName, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException {
        }

        @Override
        public boolean setTime(ComponentName componentName, long l) throws RemoteException {
            return false;
        }

        @Override
        public boolean setTimeZone(ComponentName componentName, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle, boolean bl) throws RemoteException {
        }

        @Override
        public void setUninstallBlocked(ComponentName componentName, String string2, String string3, boolean bl) throws RemoteException {
        }

        @Override
        public void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException {
        }

        @Override
        public void setUserProvisioningState(int n, int n2) throws RemoteException {
        }

        @Override
        public void setUserRestriction(ComponentName componentName, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void startManagedQuickContact(String string2, long l, boolean bl, long l2, Intent intent) throws RemoteException {
        }

        @Override
        public int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        @Override
        public boolean startViewCalendarEventInManagedProfile(String string2, long l, long l2, long l3, boolean bl, int n) throws RemoteException {
            return false;
        }

        @Override
        public int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return 0;
        }

        @Override
        public boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override
        public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException {
        }

        @Override
        public void uninstallCaCerts(ComponentName componentName, String string2, String[] arrstring) throws RemoteException {
        }

        @Override
        public void uninstallPackageWithActiveAdmins(String string2) throws RemoteException {
        }

        @Override
        public boolean updateOverrideApn(ComponentName componentName, int n, ApnSetting apnSetting) throws RemoteException {
            return false;
        }

        @Override
        public void wipeDataWithReason(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDevicePolicyManager {
        private static final String DESCRIPTOR = "android.app.admin.IDevicePolicyManager";
        static final int TRANSACTION_addCrossProfileIntentFilter = 116;
        static final int TRANSACTION_addCrossProfileWidgetProvider = 172;
        static final int TRANSACTION_addOverrideApn = 262;
        static final int TRANSACTION_addPersistentPreferredActivity = 104;
        static final int TRANSACTION_approveCaCert = 88;
        static final int TRANSACTION_bindDeviceAdminServiceAsUser = 238;
        static final int TRANSACTION_checkDeviceIdentifierAccess = 80;
        static final int TRANSACTION_checkProvisioningPreCondition = 194;
        static final int TRANSACTION_choosePrivateKeyAlias = 94;
        static final int TRANSACTION_clearApplicationUserData = 250;
        static final int TRANSACTION_clearCrossProfileIntentFilters = 117;
        static final int TRANSACTION_clearDeviceOwner = 70;
        static final int TRANSACTION_clearPackagePersistentPreferredActivities = 105;
        static final int TRANSACTION_clearProfileOwner = 78;
        static final int TRANSACTION_clearResetPasswordToken = 245;
        static final int TRANSACTION_clearSystemUpdatePolicyFreezePeriodRecord = 183;
        static final int TRANSACTION_createAdminSupportIntent = 129;
        static final int TRANSACTION_createAndManageUser = 132;
        static final int TRANSACTION_enableSystemApp = 139;
        static final int TRANSACTION_enableSystemAppWithIntent = 140;
        static final int TRANSACTION_enforceCanManageCaCerts = 87;
        static final int TRANSACTION_forceNetworkLogs = 225;
        static final int TRANSACTION_forceRemoveActiveAdmin = 56;
        static final int TRANSACTION_forceSecurityLogs = 226;
        static final int TRANSACTION_forceUpdateUserSetupComplete = 232;
        static final int TRANSACTION_generateKeyPair = 92;
        static final int TRANSACTION_getAccountTypesWithManagementDisabled = 143;
        static final int TRANSACTION_getAccountTypesWithManagementDisabledAsUser = 144;
        static final int TRANSACTION_getActiveAdmins = 52;
        static final int TRANSACTION_getAffiliationIds = 219;
        static final int TRANSACTION_getAlwaysOnVpnLockdownWhitelist = 103;
        static final int TRANSACTION_getAlwaysOnVpnPackage = 101;
        static final int TRANSACTION_getApplicationRestrictions = 108;
        static final int TRANSACTION_getApplicationRestrictionsManagingPackage = 110;
        static final int TRANSACTION_getAutoTimeRequired = 176;
        static final int TRANSACTION_getBindDeviceAdminTargetUsers = 239;
        static final int TRANSACTION_getBluetoothContactSharingDisabled = 168;
        static final int TRANSACTION_getBluetoothContactSharingDisabledForUser = 169;
        static final int TRANSACTION_getCameraDisabled = 45;
        static final int TRANSACTION_getCertInstallerPackage = 99;
        static final int TRANSACTION_getCrossProfileCalendarPackages = 275;
        static final int TRANSACTION_getCrossProfileCalendarPackagesForUser = 277;
        static final int TRANSACTION_getCrossProfileCallerIdDisabled = 161;
        static final int TRANSACTION_getCrossProfileCallerIdDisabledForUser = 162;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabled = 164;
        static final int TRANSACTION_getCrossProfileContactsSearchDisabledForUser = 165;
        static final int TRANSACTION_getCrossProfileWidgetProviders = 174;
        static final int TRANSACTION_getCurrentFailedPasswordAttempts = 26;
        static final int TRANSACTION_getDelegatePackages = 97;
        static final int TRANSACTION_getDelegatedScopes = 96;
        static final int TRANSACTION_getDeviceOwnerComponent = 67;
        static final int TRANSACTION_getDeviceOwnerLockScreenInfo = 82;
        static final int TRANSACTION_getDeviceOwnerName = 69;
        static final int TRANSACTION_getDeviceOwnerOrganizationName = 214;
        static final int TRANSACTION_getDeviceOwnerUserId = 71;
        static final int TRANSACTION_getDisallowedSystemApps = 253;
        static final int TRANSACTION_getDoNotAskCredentialsOnBoot = 186;
        static final int TRANSACTION_getEndUserSessionMessage = 259;
        static final int TRANSACTION_getForceEphemeralUsers = 178;
        static final int TRANSACTION_getGlobalPrivateDnsHost = 271;
        static final int TRANSACTION_getGlobalPrivateDnsMode = 270;
        static final int TRANSACTION_getGlobalProxyAdmin = 38;
        static final int TRANSACTION_getKeepUninstalledPackages = 196;
        static final int TRANSACTION_getKeyguardDisabledFeatures = 49;
        static final int TRANSACTION_getLastBugReportRequestTime = 242;
        static final int TRANSACTION_getLastNetworkLogRetrievalTime = 243;
        static final int TRANSACTION_getLastSecurityLogRetrievalTime = 241;
        static final int TRANSACTION_getLockTaskFeatures = 149;
        static final int TRANSACTION_getLockTaskPackages = 146;
        static final int TRANSACTION_getLongSupportMessage = 204;
        static final int TRANSACTION_getLongSupportMessageForUser = 206;
        static final int TRANSACTION_getMaximumFailedPasswordsForWipe = 29;
        static final int TRANSACTION_getMaximumTimeToLock = 32;
        static final int TRANSACTION_getMeteredDataDisabledPackages = 261;
        static final int TRANSACTION_getOrganizationColor = 210;
        static final int TRANSACTION_getOrganizationColorForUser = 211;
        static final int TRANSACTION_getOrganizationName = 213;
        static final int TRANSACTION_getOrganizationNameForUser = 215;
        static final int TRANSACTION_getOverrideApns = 265;
        static final int TRANSACTION_getOwnerInstalledCaCerts = 249;
        static final int TRANSACTION_getPasswordComplexity = 24;
        static final int TRANSACTION_getPasswordExpiration = 21;
        static final int TRANSACTION_getPasswordExpirationTimeout = 20;
        static final int TRANSACTION_getPasswordHistoryLength = 18;
        static final int TRANSACTION_getPasswordMinimumLength = 4;
        static final int TRANSACTION_getPasswordMinimumLetters = 10;
        static final int TRANSACTION_getPasswordMinimumLowerCase = 8;
        static final int TRANSACTION_getPasswordMinimumNonLetter = 16;
        static final int TRANSACTION_getPasswordMinimumNumeric = 12;
        static final int TRANSACTION_getPasswordMinimumSymbols = 14;
        static final int TRANSACTION_getPasswordMinimumUpperCase = 6;
        static final int TRANSACTION_getPasswordQuality = 2;
        static final int TRANSACTION_getPendingSystemUpdate = 188;
        static final int TRANSACTION_getPermissionGrantState = 192;
        static final int TRANSACTION_getPermissionPolicy = 190;
        static final int TRANSACTION_getPermittedAccessibilityServices = 119;
        static final int TRANSACTION_getPermittedAccessibilityServicesForUser = 120;
        static final int TRANSACTION_getPermittedCrossProfileNotificationListeners = 127;
        static final int TRANSACTION_getPermittedInputMethods = 123;
        static final int TRANSACTION_getPermittedInputMethodsForCurrentUser = 124;
        static final int TRANSACTION_getProfileOwner = 74;
        static final int TRANSACTION_getProfileOwnerAsUser = 73;
        static final int TRANSACTION_getProfileOwnerName = 75;
        static final int TRANSACTION_getProfileWithMinimumFailedPasswordsForWipe = 27;
        static final int TRANSACTION_getRemoveWarning = 54;
        static final int TRANSACTION_getRequiredStrongAuthTimeout = 34;
        static final int TRANSACTION_getRestrictionsProvider = 113;
        static final int TRANSACTION_getScreenCaptureDisabled = 47;
        static final int TRANSACTION_getSecondaryUsers = 138;
        static final int TRANSACTION_getShortSupportMessage = 202;
        static final int TRANSACTION_getShortSupportMessageForUser = 205;
        static final int TRANSACTION_getStartUserSessionMessage = 258;
        static final int TRANSACTION_getStorageEncryption = 41;
        static final int TRANSACTION_getStorageEncryptionStatus = 42;
        static final int TRANSACTION_getSystemUpdatePolicy = 182;
        static final int TRANSACTION_getTransferOwnershipBundle = 255;
        static final int TRANSACTION_getTrustAgentConfiguration = 171;
        static final int TRANSACTION_getUserProvisioningState = 216;
        static final int TRANSACTION_getUserRestrictions = 115;
        static final int TRANSACTION_getWifiMacAddress = 199;
        static final int TRANSACTION_grantDeviceIdsAccessToProfileOwner = 272;
        static final int TRANSACTION_hasDeviceOwner = 68;
        static final int TRANSACTION_hasGrantedPolicy = 57;
        static final int TRANSACTION_hasUserSetupCompleted = 79;
        static final int TRANSACTION_installCaCert = 85;
        static final int TRANSACTION_installExistingPackage = 141;
        static final int TRANSACTION_installKeyPair = 90;
        static final int TRANSACTION_installUpdateFromFile = 273;
        static final int TRANSACTION_isAccessibilityServicePermittedByAdmin = 121;
        static final int TRANSACTION_isActivePasswordSufficient = 22;
        static final int TRANSACTION_isAdminActive = 51;
        static final int TRANSACTION_isAffiliatedUser = 220;
        static final int TRANSACTION_isAlwaysOnVpnLockdownEnabled = 102;
        static final int TRANSACTION_isApplicationHidden = 131;
        static final int TRANSACTION_isBackupServiceEnabled = 234;
        static final int TRANSACTION_isCaCertApproved = 89;
        static final int TRANSACTION_isCallerApplicationRestrictionsManagingPackage = 111;
        static final int TRANSACTION_isCurrentInputMethodSetByOwner = 248;
        static final int TRANSACTION_isDeviceProvisioned = 229;
        static final int TRANSACTION_isDeviceProvisioningConfigApplied = 230;
        static final int TRANSACTION_isEphemeralUser = 240;
        static final int TRANSACTION_isInputMethodPermittedByAdmin = 125;
        static final int TRANSACTION_isLockTaskPermitted = 147;
        static final int TRANSACTION_isLogoutEnabled = 252;
        static final int TRANSACTION_isManagedKiosk = 278;
        static final int TRANSACTION_isManagedProfile = 197;
        static final int TRANSACTION_isMasterVolumeMuted = 156;
        static final int TRANSACTION_isMeteredDataDisabledPackageForUser = 268;
        static final int TRANSACTION_isNetworkLoggingEnabled = 236;
        static final int TRANSACTION_isNotificationListenerServicePermitted = 128;
        static final int TRANSACTION_isOverrideApnEnabled = 267;
        static final int TRANSACTION_isPackageAllowedToAccessCalendarForUser = 276;
        static final int TRANSACTION_isPackageSuspended = 84;
        static final int TRANSACTION_isProfileActivePasswordSufficientForParent = 23;
        static final int TRANSACTION_isProvisioningAllowed = 193;
        static final int TRANSACTION_isRemovingAdmin = 179;
        static final int TRANSACTION_isResetPasswordTokenActive = 246;
        static final int TRANSACTION_isSecurityLoggingEnabled = 222;
        static final int TRANSACTION_isSeparateProfileChallengeAllowed = 207;
        static final int TRANSACTION_isSystemOnlyUser = 198;
        static final int TRANSACTION_isUnattendedManagedKiosk = 279;
        static final int TRANSACTION_isUninstallBlocked = 159;
        static final int TRANSACTION_isUninstallInQueue = 227;
        static final int TRANSACTION_isUsingUnifiedPassword = 25;
        static final int TRANSACTION_lockNow = 35;
        static final int TRANSACTION_logoutUser = 137;
        static final int TRANSACTION_notifyLockTaskModeChanged = 157;
        static final int TRANSACTION_notifyPendingSystemUpdate = 187;
        static final int TRANSACTION_packageHasActiveAdmins = 53;
        static final int TRANSACTION_reboot = 200;
        static final int TRANSACTION_removeActiveAdmin = 55;
        static final int TRANSACTION_removeCrossProfileWidgetProvider = 173;
        static final int TRANSACTION_removeKeyPair = 91;
        static final int TRANSACTION_removeOverrideApn = 264;
        static final int TRANSACTION_removeUser = 133;
        static final int TRANSACTION_reportFailedBiometricAttempt = 62;
        static final int TRANSACTION_reportFailedPasswordAttempt = 60;
        static final int TRANSACTION_reportKeyguardDismissed = 64;
        static final int TRANSACTION_reportKeyguardSecured = 65;
        static final int TRANSACTION_reportPasswordChanged = 59;
        static final int TRANSACTION_reportSuccessfulBiometricAttempt = 63;
        static final int TRANSACTION_reportSuccessfulPasswordAttempt = 61;
        static final int TRANSACTION_requestBugreport = 43;
        static final int TRANSACTION_resetPassword = 30;
        static final int TRANSACTION_resetPasswordWithToken = 247;
        static final int TRANSACTION_retrieveNetworkLogs = 237;
        static final int TRANSACTION_retrievePreRebootSecurityLogs = 224;
        static final int TRANSACTION_retrieveSecurityLogs = 223;
        static final int TRANSACTION_setAccountManagementDisabled = 142;
        static final int TRANSACTION_setActiveAdmin = 50;
        static final int TRANSACTION_setActivePasswordState = 58;
        static final int TRANSACTION_setAffiliationIds = 218;
        static final int TRANSACTION_setAlwaysOnVpnPackage = 100;
        static final int TRANSACTION_setApplicationHidden = 130;
        static final int TRANSACTION_setApplicationRestrictions = 107;
        static final int TRANSACTION_setApplicationRestrictionsManagingPackage = 109;
        static final int TRANSACTION_setAutoTimeRequired = 175;
        static final int TRANSACTION_setBackupServiceEnabled = 233;
        static final int TRANSACTION_setBluetoothContactSharingDisabled = 167;
        static final int TRANSACTION_setCameraDisabled = 44;
        static final int TRANSACTION_setCertInstallerPackage = 98;
        static final int TRANSACTION_setCrossProfileCalendarPackages = 274;
        static final int TRANSACTION_setCrossProfileCallerIdDisabled = 160;
        static final int TRANSACTION_setCrossProfileContactsSearchDisabled = 163;
        static final int TRANSACTION_setDefaultSmsApplication = 106;
        static final int TRANSACTION_setDelegatedScopes = 95;
        static final int TRANSACTION_setDeviceOwner = 66;
        static final int TRANSACTION_setDeviceOwnerLockScreenInfo = 81;
        static final int TRANSACTION_setDeviceProvisioningConfigApplied = 231;
        static final int TRANSACTION_setEndUserSessionMessage = 257;
        static final int TRANSACTION_setForceEphemeralUsers = 177;
        static final int TRANSACTION_setGlobalPrivateDns = 269;
        static final int TRANSACTION_setGlobalProxy = 37;
        static final int TRANSACTION_setGlobalSetting = 150;
        static final int TRANSACTION_setKeepUninstalledPackages = 195;
        static final int TRANSACTION_setKeyPairCertificate = 93;
        static final int TRANSACTION_setKeyguardDisabled = 184;
        static final int TRANSACTION_setKeyguardDisabledFeatures = 48;
        static final int TRANSACTION_setLockTaskFeatures = 148;
        static final int TRANSACTION_setLockTaskPackages = 145;
        static final int TRANSACTION_setLogoutEnabled = 251;
        static final int TRANSACTION_setLongSupportMessage = 203;
        static final int TRANSACTION_setMasterVolumeMuted = 155;
        static final int TRANSACTION_setMaximumFailedPasswordsForWipe = 28;
        static final int TRANSACTION_setMaximumTimeToLock = 31;
        static final int TRANSACTION_setMeteredDataDisabledPackages = 260;
        static final int TRANSACTION_setNetworkLoggingEnabled = 235;
        static final int TRANSACTION_setOrganizationColor = 208;
        static final int TRANSACTION_setOrganizationColorForUser = 209;
        static final int TRANSACTION_setOrganizationName = 212;
        static final int TRANSACTION_setOverrideApnsEnabled = 266;
        static final int TRANSACTION_setPackagesSuspended = 83;
        static final int TRANSACTION_setPasswordExpirationTimeout = 19;
        static final int TRANSACTION_setPasswordHistoryLength = 17;
        static final int TRANSACTION_setPasswordMinimumLength = 3;
        static final int TRANSACTION_setPasswordMinimumLetters = 9;
        static final int TRANSACTION_setPasswordMinimumLowerCase = 7;
        static final int TRANSACTION_setPasswordMinimumNonLetter = 15;
        static final int TRANSACTION_setPasswordMinimumNumeric = 11;
        static final int TRANSACTION_setPasswordMinimumSymbols = 13;
        static final int TRANSACTION_setPasswordMinimumUpperCase = 5;
        static final int TRANSACTION_setPasswordQuality = 1;
        static final int TRANSACTION_setPermissionGrantState = 191;
        static final int TRANSACTION_setPermissionPolicy = 189;
        static final int TRANSACTION_setPermittedAccessibilityServices = 118;
        static final int TRANSACTION_setPermittedCrossProfileNotificationListeners = 126;
        static final int TRANSACTION_setPermittedInputMethods = 122;
        static final int TRANSACTION_setProfileEnabled = 76;
        static final int TRANSACTION_setProfileName = 77;
        static final int TRANSACTION_setProfileOwner = 72;
        static final int TRANSACTION_setRecommendedGlobalProxy = 39;
        static final int TRANSACTION_setRequiredStrongAuthTimeout = 33;
        static final int TRANSACTION_setResetPasswordToken = 244;
        static final int TRANSACTION_setRestrictionsProvider = 112;
        static final int TRANSACTION_setScreenCaptureDisabled = 46;
        static final int TRANSACTION_setSecureSetting = 152;
        static final int TRANSACTION_setSecurityLoggingEnabled = 221;
        static final int TRANSACTION_setShortSupportMessage = 201;
        static final int TRANSACTION_setStartUserSessionMessage = 256;
        static final int TRANSACTION_setStatusBarDisabled = 185;
        static final int TRANSACTION_setStorageEncryption = 40;
        static final int TRANSACTION_setSystemSetting = 151;
        static final int TRANSACTION_setSystemUpdatePolicy = 181;
        static final int TRANSACTION_setTime = 153;
        static final int TRANSACTION_setTimeZone = 154;
        static final int TRANSACTION_setTrustAgentConfiguration = 170;
        static final int TRANSACTION_setUninstallBlocked = 158;
        static final int TRANSACTION_setUserIcon = 180;
        static final int TRANSACTION_setUserProvisioningState = 217;
        static final int TRANSACTION_setUserRestriction = 114;
        static final int TRANSACTION_startManagedQuickContact = 166;
        static final int TRANSACTION_startUserInBackground = 135;
        static final int TRANSACTION_startViewCalendarEventInManagedProfile = 280;
        static final int TRANSACTION_stopUser = 136;
        static final int TRANSACTION_switchUser = 134;
        static final int TRANSACTION_transferOwnership = 254;
        static final int TRANSACTION_uninstallCaCerts = 86;
        static final int TRANSACTION_uninstallPackageWithActiveAdmins = 228;
        static final int TRANSACTION_updateOverrideApn = 263;
        static final int TRANSACTION_wipeDataWithReason = 36;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDevicePolicyManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDevicePolicyManager) {
                return (IDevicePolicyManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDevicePolicyManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 280: {
                    return "startViewCalendarEventInManagedProfile";
                }
                case 279: {
                    return "isUnattendedManagedKiosk";
                }
                case 278: {
                    return "isManagedKiosk";
                }
                case 277: {
                    return "getCrossProfileCalendarPackagesForUser";
                }
                case 276: {
                    return "isPackageAllowedToAccessCalendarForUser";
                }
                case 275: {
                    return "getCrossProfileCalendarPackages";
                }
                case 274: {
                    return "setCrossProfileCalendarPackages";
                }
                case 273: {
                    return "installUpdateFromFile";
                }
                case 272: {
                    return "grantDeviceIdsAccessToProfileOwner";
                }
                case 271: {
                    return "getGlobalPrivateDnsHost";
                }
                case 270: {
                    return "getGlobalPrivateDnsMode";
                }
                case 269: {
                    return "setGlobalPrivateDns";
                }
                case 268: {
                    return "isMeteredDataDisabledPackageForUser";
                }
                case 267: {
                    return "isOverrideApnEnabled";
                }
                case 266: {
                    return "setOverrideApnsEnabled";
                }
                case 265: {
                    return "getOverrideApns";
                }
                case 264: {
                    return "removeOverrideApn";
                }
                case 263: {
                    return "updateOverrideApn";
                }
                case 262: {
                    return "addOverrideApn";
                }
                case 261: {
                    return "getMeteredDataDisabledPackages";
                }
                case 260: {
                    return "setMeteredDataDisabledPackages";
                }
                case 259: {
                    return "getEndUserSessionMessage";
                }
                case 258: {
                    return "getStartUserSessionMessage";
                }
                case 257: {
                    return "setEndUserSessionMessage";
                }
                case 256: {
                    return "setStartUserSessionMessage";
                }
                case 255: {
                    return "getTransferOwnershipBundle";
                }
                case 254: {
                    return "transferOwnership";
                }
                case 253: {
                    return "getDisallowedSystemApps";
                }
                case 252: {
                    return "isLogoutEnabled";
                }
                case 251: {
                    return "setLogoutEnabled";
                }
                case 250: {
                    return "clearApplicationUserData";
                }
                case 249: {
                    return "getOwnerInstalledCaCerts";
                }
                case 248: {
                    return "isCurrentInputMethodSetByOwner";
                }
                case 247: {
                    return "resetPasswordWithToken";
                }
                case 246: {
                    return "isResetPasswordTokenActive";
                }
                case 245: {
                    return "clearResetPasswordToken";
                }
                case 244: {
                    return "setResetPasswordToken";
                }
                case 243: {
                    return "getLastNetworkLogRetrievalTime";
                }
                case 242: {
                    return "getLastBugReportRequestTime";
                }
                case 241: {
                    return "getLastSecurityLogRetrievalTime";
                }
                case 240: {
                    return "isEphemeralUser";
                }
                case 239: {
                    return "getBindDeviceAdminTargetUsers";
                }
                case 238: {
                    return "bindDeviceAdminServiceAsUser";
                }
                case 237: {
                    return "retrieveNetworkLogs";
                }
                case 236: {
                    return "isNetworkLoggingEnabled";
                }
                case 235: {
                    return "setNetworkLoggingEnabled";
                }
                case 234: {
                    return "isBackupServiceEnabled";
                }
                case 233: {
                    return "setBackupServiceEnabled";
                }
                case 232: {
                    return "forceUpdateUserSetupComplete";
                }
                case 231: {
                    return "setDeviceProvisioningConfigApplied";
                }
                case 230: {
                    return "isDeviceProvisioningConfigApplied";
                }
                case 229: {
                    return "isDeviceProvisioned";
                }
                case 228: {
                    return "uninstallPackageWithActiveAdmins";
                }
                case 227: {
                    return "isUninstallInQueue";
                }
                case 226: {
                    return "forceSecurityLogs";
                }
                case 225: {
                    return "forceNetworkLogs";
                }
                case 224: {
                    return "retrievePreRebootSecurityLogs";
                }
                case 223: {
                    return "retrieveSecurityLogs";
                }
                case 222: {
                    return "isSecurityLoggingEnabled";
                }
                case 221: {
                    return "setSecurityLoggingEnabled";
                }
                case 220: {
                    return "isAffiliatedUser";
                }
                case 219: {
                    return "getAffiliationIds";
                }
                case 218: {
                    return "setAffiliationIds";
                }
                case 217: {
                    return "setUserProvisioningState";
                }
                case 216: {
                    return "getUserProvisioningState";
                }
                case 215: {
                    return "getOrganizationNameForUser";
                }
                case 214: {
                    return "getDeviceOwnerOrganizationName";
                }
                case 213: {
                    return "getOrganizationName";
                }
                case 212: {
                    return "setOrganizationName";
                }
                case 211: {
                    return "getOrganizationColorForUser";
                }
                case 210: {
                    return "getOrganizationColor";
                }
                case 209: {
                    return "setOrganizationColorForUser";
                }
                case 208: {
                    return "setOrganizationColor";
                }
                case 207: {
                    return "isSeparateProfileChallengeAllowed";
                }
                case 206: {
                    return "getLongSupportMessageForUser";
                }
                case 205: {
                    return "getShortSupportMessageForUser";
                }
                case 204: {
                    return "getLongSupportMessage";
                }
                case 203: {
                    return "setLongSupportMessage";
                }
                case 202: {
                    return "getShortSupportMessage";
                }
                case 201: {
                    return "setShortSupportMessage";
                }
                case 200: {
                    return "reboot";
                }
                case 199: {
                    return "getWifiMacAddress";
                }
                case 198: {
                    return "isSystemOnlyUser";
                }
                case 197: {
                    return "isManagedProfile";
                }
                case 196: {
                    return "getKeepUninstalledPackages";
                }
                case 195: {
                    return "setKeepUninstalledPackages";
                }
                case 194: {
                    return "checkProvisioningPreCondition";
                }
                case 193: {
                    return "isProvisioningAllowed";
                }
                case 192: {
                    return "getPermissionGrantState";
                }
                case 191: {
                    return "setPermissionGrantState";
                }
                case 190: {
                    return "getPermissionPolicy";
                }
                case 189: {
                    return "setPermissionPolicy";
                }
                case 188: {
                    return "getPendingSystemUpdate";
                }
                case 187: {
                    return "notifyPendingSystemUpdate";
                }
                case 186: {
                    return "getDoNotAskCredentialsOnBoot";
                }
                case 185: {
                    return "setStatusBarDisabled";
                }
                case 184: {
                    return "setKeyguardDisabled";
                }
                case 183: {
                    return "clearSystemUpdatePolicyFreezePeriodRecord";
                }
                case 182: {
                    return "getSystemUpdatePolicy";
                }
                case 181: {
                    return "setSystemUpdatePolicy";
                }
                case 180: {
                    return "setUserIcon";
                }
                case 179: {
                    return "isRemovingAdmin";
                }
                case 178: {
                    return "getForceEphemeralUsers";
                }
                case 177: {
                    return "setForceEphemeralUsers";
                }
                case 176: {
                    return "getAutoTimeRequired";
                }
                case 175: {
                    return "setAutoTimeRequired";
                }
                case 174: {
                    return "getCrossProfileWidgetProviders";
                }
                case 173: {
                    return "removeCrossProfileWidgetProvider";
                }
                case 172: {
                    return "addCrossProfileWidgetProvider";
                }
                case 171: {
                    return "getTrustAgentConfiguration";
                }
                case 170: {
                    return "setTrustAgentConfiguration";
                }
                case 169: {
                    return "getBluetoothContactSharingDisabledForUser";
                }
                case 168: {
                    return "getBluetoothContactSharingDisabled";
                }
                case 167: {
                    return "setBluetoothContactSharingDisabled";
                }
                case 166: {
                    return "startManagedQuickContact";
                }
                case 165: {
                    return "getCrossProfileContactsSearchDisabledForUser";
                }
                case 164: {
                    return "getCrossProfileContactsSearchDisabled";
                }
                case 163: {
                    return "setCrossProfileContactsSearchDisabled";
                }
                case 162: {
                    return "getCrossProfileCallerIdDisabledForUser";
                }
                case 161: {
                    return "getCrossProfileCallerIdDisabled";
                }
                case 160: {
                    return "setCrossProfileCallerIdDisabled";
                }
                case 159: {
                    return "isUninstallBlocked";
                }
                case 158: {
                    return "setUninstallBlocked";
                }
                case 157: {
                    return "notifyLockTaskModeChanged";
                }
                case 156: {
                    return "isMasterVolumeMuted";
                }
                case 155: {
                    return "setMasterVolumeMuted";
                }
                case 154: {
                    return "setTimeZone";
                }
                case 153: {
                    return "setTime";
                }
                case 152: {
                    return "setSecureSetting";
                }
                case 151: {
                    return "setSystemSetting";
                }
                case 150: {
                    return "setGlobalSetting";
                }
                case 149: {
                    return "getLockTaskFeatures";
                }
                case 148: {
                    return "setLockTaskFeatures";
                }
                case 147: {
                    return "isLockTaskPermitted";
                }
                case 146: {
                    return "getLockTaskPackages";
                }
                case 145: {
                    return "setLockTaskPackages";
                }
                case 144: {
                    return "getAccountTypesWithManagementDisabledAsUser";
                }
                case 143: {
                    return "getAccountTypesWithManagementDisabled";
                }
                case 142: {
                    return "setAccountManagementDisabled";
                }
                case 141: {
                    return "installExistingPackage";
                }
                case 140: {
                    return "enableSystemAppWithIntent";
                }
                case 139: {
                    return "enableSystemApp";
                }
                case 138: {
                    return "getSecondaryUsers";
                }
                case 137: {
                    return "logoutUser";
                }
                case 136: {
                    return "stopUser";
                }
                case 135: {
                    return "startUserInBackground";
                }
                case 134: {
                    return "switchUser";
                }
                case 133: {
                    return "removeUser";
                }
                case 132: {
                    return "createAndManageUser";
                }
                case 131: {
                    return "isApplicationHidden";
                }
                case 130: {
                    return "setApplicationHidden";
                }
                case 129: {
                    return "createAdminSupportIntent";
                }
                case 128: {
                    return "isNotificationListenerServicePermitted";
                }
                case 127: {
                    return "getPermittedCrossProfileNotificationListeners";
                }
                case 126: {
                    return "setPermittedCrossProfileNotificationListeners";
                }
                case 125: {
                    return "isInputMethodPermittedByAdmin";
                }
                case 124: {
                    return "getPermittedInputMethodsForCurrentUser";
                }
                case 123: {
                    return "getPermittedInputMethods";
                }
                case 122: {
                    return "setPermittedInputMethods";
                }
                case 121: {
                    return "isAccessibilityServicePermittedByAdmin";
                }
                case 120: {
                    return "getPermittedAccessibilityServicesForUser";
                }
                case 119: {
                    return "getPermittedAccessibilityServices";
                }
                case 118: {
                    return "setPermittedAccessibilityServices";
                }
                case 117: {
                    return "clearCrossProfileIntentFilters";
                }
                case 116: {
                    return "addCrossProfileIntentFilter";
                }
                case 115: {
                    return "getUserRestrictions";
                }
                case 114: {
                    return "setUserRestriction";
                }
                case 113: {
                    return "getRestrictionsProvider";
                }
                case 112: {
                    return "setRestrictionsProvider";
                }
                case 111: {
                    return "isCallerApplicationRestrictionsManagingPackage";
                }
                case 110: {
                    return "getApplicationRestrictionsManagingPackage";
                }
                case 109: {
                    return "setApplicationRestrictionsManagingPackage";
                }
                case 108: {
                    return "getApplicationRestrictions";
                }
                case 107: {
                    return "setApplicationRestrictions";
                }
                case 106: {
                    return "setDefaultSmsApplication";
                }
                case 105: {
                    return "clearPackagePersistentPreferredActivities";
                }
                case 104: {
                    return "addPersistentPreferredActivity";
                }
                case 103: {
                    return "getAlwaysOnVpnLockdownWhitelist";
                }
                case 102: {
                    return "isAlwaysOnVpnLockdownEnabled";
                }
                case 101: {
                    return "getAlwaysOnVpnPackage";
                }
                case 100: {
                    return "setAlwaysOnVpnPackage";
                }
                case 99: {
                    return "getCertInstallerPackage";
                }
                case 98: {
                    return "setCertInstallerPackage";
                }
                case 97: {
                    return "getDelegatePackages";
                }
                case 96: {
                    return "getDelegatedScopes";
                }
                case 95: {
                    return "setDelegatedScopes";
                }
                case 94: {
                    return "choosePrivateKeyAlias";
                }
                case 93: {
                    return "setKeyPairCertificate";
                }
                case 92: {
                    return "generateKeyPair";
                }
                case 91: {
                    return "removeKeyPair";
                }
                case 90: {
                    return "installKeyPair";
                }
                case 89: {
                    return "isCaCertApproved";
                }
                case 88: {
                    return "approveCaCert";
                }
                case 87: {
                    return "enforceCanManageCaCerts";
                }
                case 86: {
                    return "uninstallCaCerts";
                }
                case 85: {
                    return "installCaCert";
                }
                case 84: {
                    return "isPackageSuspended";
                }
                case 83: {
                    return "setPackagesSuspended";
                }
                case 82: {
                    return "getDeviceOwnerLockScreenInfo";
                }
                case 81: {
                    return "setDeviceOwnerLockScreenInfo";
                }
                case 80: {
                    return "checkDeviceIdentifierAccess";
                }
                case 79: {
                    return "hasUserSetupCompleted";
                }
                case 78: {
                    return "clearProfileOwner";
                }
                case 77: {
                    return "setProfileName";
                }
                case 76: {
                    return "setProfileEnabled";
                }
                case 75: {
                    return "getProfileOwnerName";
                }
                case 74: {
                    return "getProfileOwner";
                }
                case 73: {
                    return "getProfileOwnerAsUser";
                }
                case 72: {
                    return "setProfileOwner";
                }
                case 71: {
                    return "getDeviceOwnerUserId";
                }
                case 70: {
                    return "clearDeviceOwner";
                }
                case 69: {
                    return "getDeviceOwnerName";
                }
                case 68: {
                    return "hasDeviceOwner";
                }
                case 67: {
                    return "getDeviceOwnerComponent";
                }
                case 66: {
                    return "setDeviceOwner";
                }
                case 65: {
                    return "reportKeyguardSecured";
                }
                case 64: {
                    return "reportKeyguardDismissed";
                }
                case 63: {
                    return "reportSuccessfulBiometricAttempt";
                }
                case 62: {
                    return "reportFailedBiometricAttempt";
                }
                case 61: {
                    return "reportSuccessfulPasswordAttempt";
                }
                case 60: {
                    return "reportFailedPasswordAttempt";
                }
                case 59: {
                    return "reportPasswordChanged";
                }
                case 58: {
                    return "setActivePasswordState";
                }
                case 57: {
                    return "hasGrantedPolicy";
                }
                case 56: {
                    return "forceRemoveActiveAdmin";
                }
                case 55: {
                    return "removeActiveAdmin";
                }
                case 54: {
                    return "getRemoveWarning";
                }
                case 53: {
                    return "packageHasActiveAdmins";
                }
                case 52: {
                    return "getActiveAdmins";
                }
                case 51: {
                    return "isAdminActive";
                }
                case 50: {
                    return "setActiveAdmin";
                }
                case 49: {
                    return "getKeyguardDisabledFeatures";
                }
                case 48: {
                    return "setKeyguardDisabledFeatures";
                }
                case 47: {
                    return "getScreenCaptureDisabled";
                }
                case 46: {
                    return "setScreenCaptureDisabled";
                }
                case 45: {
                    return "getCameraDisabled";
                }
                case 44: {
                    return "setCameraDisabled";
                }
                case 43: {
                    return "requestBugreport";
                }
                case 42: {
                    return "getStorageEncryptionStatus";
                }
                case 41: {
                    return "getStorageEncryption";
                }
                case 40: {
                    return "setStorageEncryption";
                }
                case 39: {
                    return "setRecommendedGlobalProxy";
                }
                case 38: {
                    return "getGlobalProxyAdmin";
                }
                case 37: {
                    return "setGlobalProxy";
                }
                case 36: {
                    return "wipeDataWithReason";
                }
                case 35: {
                    return "lockNow";
                }
                case 34: {
                    return "getRequiredStrongAuthTimeout";
                }
                case 33: {
                    return "setRequiredStrongAuthTimeout";
                }
                case 32: {
                    return "getMaximumTimeToLock";
                }
                case 31: {
                    return "setMaximumTimeToLock";
                }
                case 30: {
                    return "resetPassword";
                }
                case 29: {
                    return "getMaximumFailedPasswordsForWipe";
                }
                case 28: {
                    return "setMaximumFailedPasswordsForWipe";
                }
                case 27: {
                    return "getProfileWithMinimumFailedPasswordsForWipe";
                }
                case 26: {
                    return "getCurrentFailedPasswordAttempts";
                }
                case 25: {
                    return "isUsingUnifiedPassword";
                }
                case 24: {
                    return "getPasswordComplexity";
                }
                case 23: {
                    return "isProfileActivePasswordSufficientForParent";
                }
                case 22: {
                    return "isActivePasswordSufficient";
                }
                case 21: {
                    return "getPasswordExpiration";
                }
                case 20: {
                    return "getPasswordExpirationTimeout";
                }
                case 19: {
                    return "setPasswordExpirationTimeout";
                }
                case 18: {
                    return "getPasswordHistoryLength";
                }
                case 17: {
                    return "setPasswordHistoryLength";
                }
                case 16: {
                    return "getPasswordMinimumNonLetter";
                }
                case 15: {
                    return "setPasswordMinimumNonLetter";
                }
                case 14: {
                    return "getPasswordMinimumSymbols";
                }
                case 13: {
                    return "setPasswordMinimumSymbols";
                }
                case 12: {
                    return "getPasswordMinimumNumeric";
                }
                case 11: {
                    return "setPasswordMinimumNumeric";
                }
                case 10: {
                    return "getPasswordMinimumLetters";
                }
                case 9: {
                    return "setPasswordMinimumLetters";
                }
                case 8: {
                    return "getPasswordMinimumLowerCase";
                }
                case 7: {
                    return "setPasswordMinimumLowerCase";
                }
                case 6: {
                    return "getPasswordMinimumUpperCase";
                }
                case 5: {
                    return "setPasswordMinimumUpperCase";
                }
                case 4: {
                    return "getPasswordMinimumLength";
                }
                case 3: {
                    return "setPasswordMinimumLength";
                }
                case 2: {
                    return "getPasswordQuality";
                }
                case 1: 
            }
            return "setPasswordQuality";
        }

        private boolean onTransact$bindDeviceAdminServiceAsUser$(Parcel parcel, Parcel parcel2) throws RemoteException {
            parcel.enforceInterface(DESCRIPTOR);
            ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
            IApplicationThread iApplicationThread = IApplicationThread.Stub.asInterface(parcel.readStrongBinder());
            IBinder iBinder = parcel.readStrongBinder();
            Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
            int n = this.bindDeviceAdminServiceAsUser(componentName, iApplicationThread, iBinder, intent, IServiceConnection.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readInt());
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private boolean onTransact$installKeyPair$(Parcel parcel, Parcel parcel2) throws RemoteException {
            parcel.enforceInterface(DESCRIPTOR);
            ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
            String string2 = parcel.readString();
            byte[] arrby = parcel.createByteArray();
            byte[] arrby2 = parcel.createByteArray();
            byte[] arrby3 = parcel.createByteArray();
            String string3 = parcel.readString();
            boolean bl = parcel.readInt() != 0;
            boolean bl2 = parcel.readInt() != 0;
            int n = this.installKeyPair(componentName, string2, arrby, arrby2, arrby3, string3, bl, bl2);
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private boolean onTransact$setKeyPairCertificate$(Parcel parcel, Parcel parcel2) throws RemoteException {
            parcel.enforceInterface(DESCRIPTOR);
            ComponentName componentName = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
            String string2 = parcel.readString();
            String string3 = parcel.readString();
            byte[] arrby = parcel.createByteArray();
            byte[] arrby2 = parcel.createByteArray();
            boolean bl = parcel.readInt() != 0;
            int n = this.setKeyPairCertificate(componentName, string2, string3, arrby, arrby2, bl);
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private boolean onTransact$setPermissionGrantState$(Parcel object, Parcel parcel) throws RemoteException {
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
            String string2 = ((Parcel)object).readString();
            String string3 = ((Parcel)object).readString();
            String string4 = ((Parcel)object).readString();
            int n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
            this.setPermissionGrantState(componentName, string2, string3, string4, n, (RemoteCallback)object);
            parcel.writeNoException();
            return true;
        }

        private boolean onTransact$startViewCalendarEventInManagedProfile$(Parcel parcel, Parcel parcel2) throws RemoteException {
            parcel.enforceInterface(DESCRIPTOR);
            String string2 = parcel.readString();
            long l = parcel.readLong();
            long l2 = parcel.readLong();
            long l3 = parcel.readLong();
            boolean bl = parcel.readInt() != 0;
            int n = this.startViewCalendarEventInManagedProfile(string2, l, l2, l3, bl, parcel.readInt());
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        public static boolean setDefaultImpl(IDevicePolicyManager iDevicePolicyManager) {
            if (Proxy.sDefaultImpl == null && iDevicePolicyManager != null) {
                Proxy.sDefaultImpl = iDevicePolicyManager;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
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
                boolean bl12 = false;
                boolean bl13 = false;
                boolean bl14 = false;
                boolean bl15 = false;
                boolean bl16 = false;
                boolean bl17 = false;
                boolean bl18 = false;
                boolean bl19 = false;
                boolean bl20 = false;
                boolean bl21 = false;
                boolean bl22 = false;
                boolean bl23 = false;
                boolean bl24 = false;
                boolean bl25 = false;
                boolean bl26 = false;
                boolean bl27 = false;
                boolean bl28 = false;
                boolean bl29 = false;
                boolean bl30 = false;
                boolean bl31 = false;
                boolean bl32 = false;
                boolean bl33 = false;
                boolean bl34 = false;
                boolean bl35 = false;
                boolean bl36 = false;
                boolean bl37 = false;
                boolean bl38 = false;
                boolean bl39 = false;
                boolean bl40 = false;
                boolean bl41 = false;
                boolean bl42 = false;
                boolean bl43 = false;
                boolean bl44 = false;
                boolean bl45 = false;
                boolean bl46 = false;
                boolean bl47 = false;
                boolean bl48 = false;
                boolean bl49 = false;
                boolean bl50 = false;
                boolean bl51 = false;
                boolean bl52 = false;
                boolean bl53 = false;
                boolean bl54 = false;
                boolean bl55 = false;
                boolean bl56 = false;
                boolean bl57 = false;
                boolean bl58 = false;
                boolean bl59 = false;
                boolean bl60 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 280: {
                        return this.onTransact$startViewCalendarEventInManagedProfile$((Parcel)object, parcel);
                    }
                    case 279: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUnattendedManagedKiosk() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 278: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isManagedKiosk() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 277: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCrossProfileCalendarPackagesForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 276: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPackageAllowedToAccessCalendarForUser(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 275: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCrossProfileCalendarPackages((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 274: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setCrossProfileCalendarPackages(componentName, ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 273: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.installUpdateFromFile(componentName, parcelFileDescriptor, StartInstallingUpdateCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 272: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantDeviceIdsAccessToProfileOwner(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 271: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getGlobalPrivateDnsHost((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 270: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getGlobalPrivateDnsMode((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 269: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setGlobalPrivateDns(componentName, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 268: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isMeteredDataDisabledPackageForUser(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 267: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isOverrideApnEnabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 266: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setOverrideApnsEnabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 265: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getOverrideApns((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 264: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeOverrideApn(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 263: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ApnSetting.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.updateOverrideApn(componentName, n, (ApnSetting)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 262: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ApnSetting.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addOverrideApn(componentName, (ApnSetting)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 261: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getMeteredDataDisabledPackages((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 260: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.setMeteredDataDisabledPackages(componentName, ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 259: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getEndUserSessionMessage((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 258: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getStartUserSessionMessage((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 257: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setEndUserSessionMessage(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 256: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStartUserSessionMessage(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 255: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getTransferOwnershipBundle();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PersistableBundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 254: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        ComponentName componentName2 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.transferOwnership(componentName, componentName2, (PersistableBundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 253: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getDisallowedSystemApps(componentName, ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 252: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isLogoutEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 251: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setLogoutEnabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 250: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearApplicationUserData(componentName, ((Parcel)object).readString(), IPackageDataObserver.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 249: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getOwnerInstalledCaCerts((UserHandle)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((StringParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 248: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCurrentInputMethodSetByOwner() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 247: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.resetPasswordWithToken(componentName, ((Parcel)object).readString(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 246: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isResetPasswordTokenActive((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 245: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.clearResetPasswordToken((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 244: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setResetPasswordToken(componentName, ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 243: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getLastNetworkLogRetrievalTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 242: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getLastBugReportRequestTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 241: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getLastSecurityLogRetrievalTime();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 240: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isEphemeralUser((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 239: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getBindDeviceAdminTargetUsers((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 238: {
                        return this.onTransact$bindDeviceAdminServiceAsUser$((Parcel)object, parcel);
                    }
                    case 237: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.retrieveNetworkLogs(componentName, ((Parcel)object).readString(), ((Parcel)object).readLong());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 236: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isNetworkLoggingEnabled(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 235: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string2 = ((Parcel)object).readString();
                        bl60 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setNetworkLoggingEnabled(componentName, string2, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 234: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isBackupServiceEnabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 233: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setBackupServiceEnabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 232: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.forceUpdateUserSetupComplete();
                        parcel.writeNoException();
                        return true;
                    }
                    case 231: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDeviceProvisioningConfigApplied();
                        parcel.writeNoException();
                        return true;
                    }
                    case 230: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceProvisioningConfigApplied() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 229: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isDeviceProvisioned() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 228: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.uninstallPackageWithActiveAdmins(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 227: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUninstallInQueue(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 226: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.forceSecurityLogs();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 225: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.forceNetworkLogs();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 224: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.retrievePreRebootSecurityLogs((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 223: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.retrieveSecurityLogs((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 222: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSecurityLoggingEnabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 221: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setSecurityLoggingEnabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 220: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isAffiliatedUser() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 219: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getAffiliationIds((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 218: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAffiliationIds(componentName, ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 217: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setUserProvisioningState(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 216: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getUserProvisioningState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 215: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getOrganizationNameForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 214: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceOwnerOrganizationName();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 213: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getOrganizationName((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 212: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setOrganizationName(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 211: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getOrganizationColorForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 210: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getOrganizationColor((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 209: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOrganizationColorForUser(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 208: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setOrganizationColor(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 207: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSeparateProfileChallengeAllowed(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 206: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLongSupportMessageForUser(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 205: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShortSupportMessageForUser(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 204: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLongSupportMessage((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 203: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setLongSupportMessage(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 202: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getShortSupportMessage((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 201: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setShortSupportMessage(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 200: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reboot((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 199: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getWifiMacAddress((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 198: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSystemOnlyUser((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 197: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isManagedProfile((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 196: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getKeepUninstalledPackages(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 195: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setKeepUninstalledPackages(componentName, ((Parcel)object).readString(), ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 194: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkProvisioningPreCondition(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 193: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isProvisioningAllowed(((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 192: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getPermissionGrantState(componentName, ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 191: {
                        return this.onTransact$setPermissionGrantState$((Parcel)object, parcel);
                    }
                    case 190: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getPermissionPolicy((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 189: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPermissionPolicy(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 188: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPendingSystemUpdate((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SystemUpdateInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 187: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SystemUpdateInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyPendingSystemUpdate((SystemUpdateInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 186: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDoNotAskCredentialsOnBoot() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 185: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.setStatusBarDisabled(componentName, bl60) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 184: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl6;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.setKeyguardDisabled(componentName, bl60) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 183: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearSystemUpdatePolicyFreezePeriodRecord();
                        parcel.writeNoException();
                        return true;
                    }
                    case 182: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSystemUpdatePolicy();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SystemUpdatePolicy)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 181: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? SystemUpdatePolicy.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setSystemUpdatePolicy(componentName, (SystemUpdatePolicy)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 180: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUserIcon(componentName, (Bitmap)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 179: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isRemovingAdmin(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 178: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getForceEphemeralUsers((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 177: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl7;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setForceEphemeralUsers(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 176: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getAutoTimeRequired() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 175: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl8;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setAutoTimeRequired(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 174: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCrossProfileWidgetProviders((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 173: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeCrossProfileWidgetProvider(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 172: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.addCrossProfileWidgetProvider(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 171: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        ComponentName componentName3 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl9;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        object = this.getTrustAgentConfiguration(componentName, componentName3, n, bl60);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 170: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        ComponentName componentName4 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        PersistableBundle persistableBundle = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl10;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setTrustAgentConfiguration(componentName, componentName4, persistableBundle, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 169: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBluetoothContactSharingDisabledForUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 168: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getBluetoothContactSharingDisabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 167: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl11;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setBluetoothContactSharingDisabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 166: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        long l = ((Parcel)object).readLong();
                        bl60 = ((Parcel)object).readInt() != 0;
                        long l2 = ((Parcel)object).readLong();
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startManagedQuickContact(string3, l, bl60, l2, (Intent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 165: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCrossProfileContactsSearchDisabledForUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 164: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getCrossProfileContactsSearchDisabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 163: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl12;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setCrossProfileContactsSearchDisabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 162: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getCrossProfileCallerIdDisabledForUser(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 161: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getCrossProfileCallerIdDisabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 160: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl13;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setCrossProfileCallerIdDisabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 159: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isUninstallBlocked(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 158: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        String string5 = ((Parcel)object).readString();
                        bl60 = bl14;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setUninstallBlocked(componentName, string4, string5, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 157: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl60 = bl15;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.notifyLockTaskModeChanged(bl60, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 156: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isMasterVolumeMuted((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 155: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl16;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setMasterVolumeMuted(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 154: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setTimeZone(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 153: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setTime(componentName, ((Parcel)object).readLong()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 152: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setSecureSetting(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 151: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setSystemSetting(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 150: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setGlobalSetting(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 149: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getLockTaskFeatures((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 148: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setLockTaskFeatures(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 147: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isLockTaskPermitted(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 146: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getLockTaskPackages((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 145: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setLockTaskPackages(componentName, ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 144: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountTypesWithManagementDisabledAsUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 143: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAccountTypesWithManagementDisabled();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 142: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string6 = ((Parcel)object).readString();
                        bl60 = bl17;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setAccountManagementDisabled(componentName, string6, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 141: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.installExistingPackage(componentName, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 140: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.enableSystemAppWithIntent(componentName, string7, (Intent)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 139: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.enableSystemApp(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 138: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSecondaryUsers((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 137: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.logoutUser((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 136: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopUser(componentName, (UserHandle)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 135: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startUserInBackground(componentName, (UserHandle)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 134: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.switchUser(componentName, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 133: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeUser(componentName, (UserHandle)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 132: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string8 = ((Parcel)object).readString();
                        ComponentName componentName5 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        PersistableBundle persistableBundle = ((Parcel)object).readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createAndManageUser(componentName, string8, componentName5, persistableBundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UserHandle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 131: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isApplicationHidden(componentName, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 130: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string9 = ((Parcel)object).readString();
                        String string10 = ((Parcel)object).readString();
                        bl60 = bl18;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.setApplicationHidden(componentName, string9, string10, bl60) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 129: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createAdminSupportIntent(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Intent)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 128: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNotificationListenerServicePermitted(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 127: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPermittedCrossProfileNotificationListeners((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 126: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPermittedCrossProfileNotificationListeners(componentName, ((Parcel)object).createStringArrayList()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 125: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isInputMethodPermittedByAdmin(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 124: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPermittedInputMethodsForCurrentUser();
                        parcel.writeNoException();
                        parcel.writeList((List)object);
                        return true;
                    }
                    case 123: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPermittedInputMethods((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeList((List)object);
                        return true;
                    }
                    case 122: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPermittedInputMethods(componentName, ((Parcel)object).readArrayList(this.getClass().getClassLoader())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 121: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isAccessibilityServicePermittedByAdmin(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 120: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPermittedAccessibilityServicesForUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeList((List)object);
                        return true;
                    }
                    case 119: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPermittedAccessibilityServices((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeList((List)object);
                        return true;
                    }
                    case 118: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPermittedAccessibilityServices(componentName, ((Parcel)object).readArrayList(this.getClass().getClassLoader())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 117: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearCrossProfileIntentFilters((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 116: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        IntentFilter intentFilter = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addCrossProfileIntentFilter(componentName, intentFilter, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 115: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getUserRestrictions((ComponentName)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 114: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string11 = ((Parcel)object).readString();
                        bl60 = bl19;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setUserRestriction(componentName, string11, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 113: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRestrictionsProvider(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 112: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setRestrictionsProvider(componentName, (ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 111: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCallerApplicationRestrictionsManagingPackage(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 110: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getApplicationRestrictionsManagingPackage((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 109: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setApplicationRestrictionsManagingPackage(componentName, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 108: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getApplicationRestrictions(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 107: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string12 = ((Parcel)object).readString();
                        String string13 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setApplicationRestrictions(componentName, string12, string13, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 106: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDefaultSmsApplication(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 105: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearPackagePersistentPreferredActivities(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 104: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        IntentFilter intentFilter = ((Parcel)object).readInt() != 0 ? IntentFilter.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addPersistentPreferredActivity(componentName, intentFilter, (ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 103: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getAlwaysOnVpnLockdownWhitelist((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 102: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isAlwaysOnVpnLockdownEnabled((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 101: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getAlwaysOnVpnPackage((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 100: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string14 = ((Parcel)object).readString();
                        bl60 = bl20;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.setAlwaysOnVpnPackage(componentName, string14, bl60, ((Parcel)object).createStringArrayList()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 99: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCertInstallerPackage((ComponentName)object);
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 98: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setCertInstallerPackage(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 97: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getDelegatePackages(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 96: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getDelegatedScopes(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 95: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDelegatedScopes(componentName, ((Parcel)object).readString(), ((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        return true;
                    }
                    case 94: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        Uri uri = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.choosePrivateKeyAlias(n, uri, ((Parcel)object).readString(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 93: {
                        return this.onTransact$setKeyPairCertificate$((Parcel)object, parcel);
                    }
                    case 92: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string15 = ((Parcel)object).readString();
                        String string16 = ((Parcel)object).readString();
                        ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec = ((Parcel)object).readInt() != 0 ? ParcelableKeyGenParameterSpec.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = new KeymasterCertificateChain();
                        n = this.generateKeyPair(componentName, string15, string16, parcelableKeyGenParameterSpec, n, (KeymasterCertificateChain)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        parcel.writeInt(1);
                        ((KeymasterCertificateChain)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    case 91: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.removeKeyPair(componentName, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 90: {
                        return this.onTransact$installKeyPair$((Parcel)object, parcel);
                    }
                    case 89: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCaCertApproved(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 88: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string17 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl60 = bl21;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.approveCaCert(string17, n, bl60) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 87: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.enforceCanManageCaCerts(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 86: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.uninstallCaCerts(componentName, ((Parcel)object).readString(), ((Parcel)object).createStringArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 85: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.installCaCert(componentName, ((Parcel)object).readString(), ((Parcel)object).createByteArray()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 84: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isPackageSuspended(componentName, ((Parcel)object).readString(), ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 83: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        String string18 = ((Parcel)object).readString();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        bl60 = bl22;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        object = this.setPackagesSuspended(componentName, string18, arrstring, bl60);
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 82: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceOwnerLockScreenInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            TextUtils.writeToParcel((CharSequence)object, parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 81: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDeviceOwnerLockScreenInfo(componentName, (CharSequence)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 80: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.checkDeviceIdentifierAccess(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 79: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasUserSetupCompleted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 78: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.clearProfileOwner((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 77: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setProfileName(componentName, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 76: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setProfileEnabled((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 75: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProfileOwnerName(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 74: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProfileOwner(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 73: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getProfileOwnerAsUser(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 72: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setProfileOwner(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 71: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getDeviceOwnerUserId();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 70: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearDeviceOwner(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 69: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceOwnerName();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 68: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasDeviceOwner() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 67: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl60 = ((Parcel)object).readInt() != 0;
                        object = this.getDeviceOwnerComponent(bl60);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 66: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setDeviceOwner(componentName, ((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 65: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportKeyguardSecured(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 64: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportKeyguardDismissed(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 63: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportSuccessfulBiometricAttempt(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 62: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportFailedBiometricAttempt(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 61: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportSuccessfulPasswordAttempt(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 60: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportFailedPasswordAttempt(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 59: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportPasswordChanged(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 58: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PasswordMetrics passwordMetrics = ((Parcel)object).readInt() != 0 ? PasswordMetrics.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setActivePasswordState(passwordMetrics, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 57: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasGrantedPolicy(componentName, ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 56: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.forceRemoveActiveAdmin(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeActiveAdmin(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        RemoteCallback remoteCallback = ((Parcel)object).readInt() != 0 ? RemoteCallback.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getRemoveWarning(componentName, remoteCallback, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.packageHasActiveAdmins(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getActiveAdmins(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isAdminActive(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl23;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setActiveAdmin(componentName, bl60, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl24;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getKeyguardDisabledFeatures(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl25;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setKeyguardDisabledFeatures(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getScreenCaptureDisabled(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl26;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setScreenCaptureDisabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getCameraDisabled(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl27;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setCameraDisabled(componentName, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.requestBugreport((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getStorageEncryptionStatus(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getStorageEncryption(componentName, ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl60 = bl28;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.setStorageEncryption(componentName, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? ProxyInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setRecommendedGlobalProxy(componentName, (ProxyInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getGlobalProxyAdmin(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.setGlobalProxy(componentName, ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ComponentName)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.wipeDataWithReason(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl60 = bl29;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.lockNow(n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl30;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        long l = this.getRequiredStrongAuthTimeout(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        long l = ((Parcel)object).readLong();
                        bl60 = bl31;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setRequiredStrongAuthTimeout(componentName, l, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl32;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        long l = this.getMaximumTimeToLock(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        long l = ((Parcel)object).readLong();
                        bl60 = bl33;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setMaximumTimeToLock(componentName, l, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.resetPassword(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl34;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getMaximumFailedPasswordsForWipe(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl35;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setMaximumFailedPasswordsForWipe(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl60 = bl36;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getProfileWithMinimumFailedPasswordsForWipe(n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl60 = bl37;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getCurrentFailedPasswordAttempts(n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isUsingUnifiedPassword((ComponentName)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getPasswordComplexity();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isProfileActivePasswordSufficientForParent(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl60 = bl38;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.isActivePasswordSufficient(n, bl60) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl39;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        long l = this.getPasswordExpiration(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl40;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        long l = this.getPasswordExpirationTimeout(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        long l = ((Parcel)object).readLong();
                        bl60 = bl41;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordExpirationTimeout(componentName, l, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl42;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordHistoryLength(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl43;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordHistoryLength(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl44;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumNonLetter(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl45;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumNonLetter(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl46;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumSymbols(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl47;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumSymbols(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl48;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumNumeric(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl49;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumNumeric(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl50;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumLetters(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl51;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumLetters(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl52;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumLowerCase(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl53;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumLowerCase(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl54;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumUpperCase(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl55;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumUpperCase(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl56;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordMinimumLength(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl57;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        this.setPasswordMinimumLength(componentName, n, bl60);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl60 = bl58;
                        if (((Parcel)object).readInt() != 0) {
                            bl60 = true;
                        }
                        n = this.getPasswordQuality(componentName, n, bl60);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                n = ((Parcel)object).readInt();
                bl60 = bl59;
                if (((Parcel)object).readInt() != 0) {
                    bl60 = true;
                }
                this.setPasswordQuality(componentName, n, bl60);
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IDevicePolicyManager {
            public static IDevicePolicyManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int n) throws RemoteException {
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
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(116, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCrossProfileIntentFilter(componentName, intentFilter, n);
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
            public boolean addCrossProfileWidgetProvider(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(172, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().addCrossProfileWidgetProvider(componentName, string2);
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
            public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) throws RemoteException {
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
                    if (apnSetting != null) {
                        parcel.writeInt(1);
                        apnSetting.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(262, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().addOverrideApn(componentName, apnSetting);
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
            public void addPersistentPreferredActivity(ComponentName componentName, IntentFilter intentFilter, ComponentName componentName2) throws RemoteException {
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
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (componentName2 != null) {
                        parcel.writeInt(1);
                        componentName2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(104, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPersistentPreferredActivity(componentName, intentFilter, componentName2);
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
            public boolean approveCaCert(String string2, int n, boolean bl) throws RemoteException {
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
                        parcel.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(88, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().approveCaCert(string2, n, bl);
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
            public IBinder asBinder() {
                return this.mRemote;
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
            public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, IApplicationThread iApplicationThread, IBinder iBinder, Intent intent, IServiceConnection iServiceConnection, int n, int n2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block16 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    Object var11_16 = null;
                    IBinder iBinder2 = iApplicationThread != null ? iApplicationThread.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    try {
                        parcel.writeStrongBinder(iBinder);
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        iBinder2 = var11_16;
                        if (iServiceConnection != null) {
                            iBinder2 = iServiceConnection.asBinder();
                        }
                        parcel.writeStrongBinder(iBinder2);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel.writeInt(n2);
                        if (!this.mRemote.transact(238, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().bindDeviceAdminServiceAsUser(componentName, iApplicationThread, iBinder, intent, iServiceConnection, n, n2);
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
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_6;
            }

            @Override
            public boolean checkDeviceIdentifierAccess(String string2, int n, int n2) throws RemoteException {
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
                    if (iBinder.transact(80, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().checkDeviceIdentifierAccess(string2, n, n2);
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
            public int checkProvisioningPreCondition(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(194, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().checkProvisioningPreCondition(string2, string3);
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
            public void choosePrivateKeyAlias(int n, Uri uri, String string2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(94, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().choosePrivateKeyAlias(n, uri, string2, iBinder);
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
            public void clearApplicationUserData(ComponentName componentName, String string2, IPackageDataObserver iPackageDataObserver) throws RemoteException {
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
                    parcel.writeString(string2);
                    IBinder iBinder = iPackageDataObserver != null ? iPackageDataObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(250, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearApplicationUserData(componentName, string2, iPackageDataObserver);
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
            public void clearCrossProfileIntentFilters(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(117, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearCrossProfileIntentFilters(componentName);
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
            public void clearDeviceOwner(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(70, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDeviceOwner(string2);
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
            public void clearPackagePersistentPreferredActivities(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(105, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearPackagePersistentPreferredActivities(componentName, string2);
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
            public void clearProfileOwner(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(78, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearProfileOwner(componentName);
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
            public boolean clearResetPasswordToken(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(245, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().clearResetPasswordToken(componentName);
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
            public void clearSystemUpdatePolicyFreezePeriodRecord() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(183, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearSystemUpdatePolicyFreezePeriodRecord();
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
            public Intent createAdminSupportIntent(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(129, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().createAdminSupportIntent((String)object);
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
                object = parcel2.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel2) : null;
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
            public UserHandle createAndManageUser(ComponentName parcelable, String string2, ComponentName componentName, PersistableBundle persistableBundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var4_9;
                    void var5_10;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString((String)var2_7);
                    if (var3_8 != null) {
                        parcel.writeInt(1);
                        var3_8.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (var4_9 != null) {
                        parcel.writeInt(1);
                        var4_9.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var5_10);
                    if (!this.mRemote.transact(132, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        UserHandle userHandle = Stub.getDefaultImpl().createAndManageUser((ComponentName)parcelable, (String)var2_7, (ComponentName)var3_8, (PersistableBundle)var4_9, (int)var5_10);
                        parcel2.recycle();
                        parcel.recycle();
                        return userHandle;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        UserHandle userHandle = UserHandle.CREATOR.createFromParcel(parcel2);
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
            public void enableSystemApp(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(139, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableSystemApp(componentName, string2, string3);
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
            public int enableSystemAppWithIntent(ComponentName componentName, String string2, Intent intent) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(140, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().enableSystemAppWithIntent(componentName, string2, intent);
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
            public void enforceCanManageCaCerts(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(87, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enforceCanManageCaCerts(componentName, string2);
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
            public long forceNetworkLogs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(225, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().forceNetworkLogs();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void forceRemoveActiveAdmin(ComponentName componentName, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(56, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceRemoveActiveAdmin(componentName, n);
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
            public long forceSecurityLogs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(226, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().forceSecurityLogs();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void forceUpdateUserSetupComplete() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(232, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().forceUpdateUserSetupComplete();
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
            public boolean generateKeyPair(ComponentName componentName, String string2, String string3, ParcelableKeyGenParameterSpec parcelableKeyGenParameterSpec, int n, KeymasterCertificateChain keymasterCertificateChain) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block20 : {
                    boolean bl;
                    block19 : {
                        block18 : {
                            block17 : {
                                parcel2 = Parcel.obtain();
                                parcel = Parcel.obtain();
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                bl = true;
                                if (componentName != null) {
                                    parcel2.writeInt(1);
                                    componentName.writeToParcel(parcel2, 0);
                                    break block17;
                                }
                                parcel2.writeInt(0);
                            }
                            try {
                                parcel2.writeString(string2);
                            }
                            catch (Throwable throwable) {
                                break block20;
                            }
                            try {
                                parcel2.writeString(string3);
                                if (parcelableKeyGenParameterSpec != null) {
                                    parcel2.writeInt(1);
                                    parcelableKeyGenParameterSpec.writeToParcel(parcel2, 0);
                                    break block18;
                                }
                                parcel2.writeInt(0);
                            }
                            catch (Throwable throwable) {}
                        }
                        try {
                            parcel2.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block20;
                        }
                        try {
                            if (!this.mRemote.transact(92, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                                bl = Stub.getDefaultImpl().generateKeyPair(componentName, string2, string3, parcelableKeyGenParameterSpec, n, keymasterCertificateChain);
                                parcel.recycle();
                                parcel2.recycle();
                                return bl;
                            }
                            parcel.readException();
                            if (parcel.readInt() == 0) {
                                bl = false;
                            }
                            n = parcel.readInt();
                            if (n == 0) break block19;
                        }
                        catch (Throwable throwable) {}
                        try {
                            keymasterCertificateChain.readFromParcel(parcel);
                        }
                        catch (Throwable throwable) {
                            break block20;
                        }
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                    break block20;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            @Override
            public String[] getAccountTypesWithManagementDisabled() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(143, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getAccountTypesWithManagementDisabled();
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
            public String[] getAccountTypesWithManagementDisabledAsUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(144, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getAccountTypesWithManagementDisabledAsUser(n);
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
            public List<ComponentName> getActiveAdmins(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ComponentName> list = Stub.getDefaultImpl().getActiveAdmins(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ComponentName> arrayList = parcel2.createTypedArrayList(ComponentName.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getAffiliationIds(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(219, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAffiliationIds((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getAlwaysOnVpnLockdownWhitelist(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(103, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getAlwaysOnVpnLockdownWhitelist((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getAlwaysOnVpnPackage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(101, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getAlwaysOnVpnPackage((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
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
            public Bundle getApplicationRestrictions(ComponentName parcelable, String string2, String string3) throws RemoteException {
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
                    parcel.writeString((String)var2_7);
                    parcel.writeString((String)var3_8);
                    if (!this.mRemote.transact(108, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Bundle bundle = Stub.getDefaultImpl().getApplicationRestrictions((ComponentName)parcelable, (String)var2_7, (String)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return bundle;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Bundle bundle = Bundle.CREATOR.createFromParcel(parcel2);
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
            public String getApplicationRestrictionsManagingPackage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(110, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getApplicationRestrictionsManagingPackage((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getAutoTimeRequired() throws RemoteException {
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
                    if (iBinder.transact(176, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getAutoTimeRequired();
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
            public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(239, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getBindDeviceAdminTargetUsers((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(UserHandle.CREATOR);
                    return arrayList;
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
            public boolean getBluetoothContactSharingDisabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(168, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getBluetoothContactSharingDisabled(componentName);
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
            public boolean getBluetoothContactSharingDisabledForUser(int n) throws RemoteException {
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
                    if (iBinder.transact(169, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getBluetoothContactSharingDisabledForUser(n);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getCameraDisabled(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getCameraDisabled(componentName, n);
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
            public String getCertInstallerPackage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(99, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getCertInstallerPackage((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getCrossProfileCalendarPackages(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(275, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getCrossProfileCalendarPackages((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getCrossProfileCalendarPackagesForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(277, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getCrossProfileCalendarPackagesForUser(n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(161, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getCrossProfileCallerIdDisabled(componentName);
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
            public boolean getCrossProfileCallerIdDisabledForUser(int n) throws RemoteException {
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
                    if (iBinder.transact(162, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getCrossProfileCallerIdDisabledForUser(n);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(164, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getCrossProfileContactsSearchDisabled(componentName);
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
            public boolean getCrossProfileContactsSearchDisabledForUser(int n) throws RemoteException {
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
                    if (iBinder.transact(165, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getCrossProfileContactsSearchDisabledForUser(n);
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
            public List<String> getCrossProfileWidgetProviders(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(174, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getCrossProfileWidgetProviders((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getCurrentFailedPasswordAttempts(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getCurrentFailedPasswordAttempts(n, bl);
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
            public List<String> getDelegatePackages(ComponentName object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(97, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getDelegatePackages((ComponentName)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getDelegatedScopes(ComponentName object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(96, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getDelegatedScopes((ComponentName)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getDeviceOwnerComponent(boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(67, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    ComponentName componentName = Stub.getDefaultImpl().getDeviceOwnerComponent(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return componentName;
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            @Override
            public CharSequence getDeviceOwnerLockScreenInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(82, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CharSequence charSequence = Stub.getDefaultImpl().getDeviceOwnerLockScreenInfo();
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
                CharSequence charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public String getDeviceOwnerName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(69, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDeviceOwnerName();
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
            public CharSequence getDeviceOwnerOrganizationName() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(214, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CharSequence charSequence = Stub.getDefaultImpl().getDeviceOwnerOrganizationName();
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
                CharSequence charSequence = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return charSequence;
            }

            @Override
            public int getDeviceOwnerUserId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(71, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getDeviceOwnerUserId();
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
            public List<String> getDisallowedSystemApps(ComponentName object, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(253, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getDisallowedSystemApps((ComponentName)object, n, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getDoNotAskCredentialsOnBoot() throws RemoteException {
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
                    if (iBinder.transact(186, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getDoNotAskCredentialsOnBoot();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getEndUserSessionMessage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(259, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getEndUserSessionMessage((ComponentName)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getForceEphemeralUsers(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(178, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getForceEphemeralUsers(componentName);
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
            public String getGlobalPrivateDnsHost(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(271, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getGlobalPrivateDnsHost((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getGlobalPrivateDnsMode(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(270, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getGlobalPrivateDnsMode(componentName);
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
            public ComponentName getGlobalProxyAdmin(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(38, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getGlobalProxyAdmin(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public List<String> getKeepUninstalledPackages(ComponentName object, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(196, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getKeepUninstalledPackages((ComponentName)object, string2);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
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
            public int getKeyguardDisabledFeatures(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getKeyguardDisabledFeatures(componentName, n, bl);
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
            public long getLastBugReportRequestTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(242, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getLastBugReportRequestTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getLastNetworkLogRetrievalTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(243, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getLastNetworkLogRetrievalTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getLastSecurityLogRetrievalTime() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(241, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getLastSecurityLogRetrievalTime();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getLockTaskFeatures(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(149, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getLockTaskFeatures(componentName);
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
            public String[] getLockTaskPackages(ComponentName arrstring) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrstring != null) {
                        parcel.writeInt(1);
                        arrstring.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(146, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().getLockTaskPackages((ComponentName)arrstring);
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
            public CharSequence getLongSupportMessage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(204, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getLongSupportMessage((ComponentName)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getLongSupportMessageForUser(ComponentName object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(206, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getLongSupportMessageForUser((ComponentName)object, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getMaximumFailedPasswordsForWipe(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public long getMaximumTimeToLock(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getMaximumTimeToLock(componentName, n, bl);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getMeteredDataDisabledPackages(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(261, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getMeteredDataDisabledPackages((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getOrganizationColor(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(210, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getOrganizationColor(componentName);
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
            public int getOrganizationColorForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(211, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getOrganizationColorForUser(n);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getOrganizationName(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(213, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getOrganizationName((ComponentName)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            @Override
            public CharSequence getOrganizationNameForUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(215, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        CharSequence charSequence = Stub.getDefaultImpl().getOrganizationNameForUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return charSequence;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                CharSequence charSequence = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return charSequence;
            }

            @Override
            public List<ApnSetting> getOverrideApns(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(265, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getOverrideApns((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(ApnSetting.CREATOR);
                    return arrayList;
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
            public StringParceledListSlice getOwnerInstalledCaCerts(UserHandle parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((UserHandle)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(249, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        StringParceledListSlice stringParceledListSlice = Stub.getDefaultImpl().getOwnerInstalledCaCerts((UserHandle)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return stringParceledListSlice;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        StringParceledListSlice stringParceledListSlice = (StringParceledListSlice)StringParceledListSlice.CREATOR.createFromParcel(parcel2);
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
            public int getPasswordComplexity() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPasswordComplexity();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public long getPasswordExpiration(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getPasswordExpiration(componentName, n, bl);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
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
            public long getPasswordExpirationTimeout(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getPasswordExpirationTimeout(componentName, n, bl);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
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
            public int getPasswordHistoryLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordHistoryLength(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumLength(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumLetters(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumLetters(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumLowerCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumLowerCase(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumNonLetter(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumNonLetter(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumNumeric(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumNumeric(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumSymbols(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumSymbols(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordMinimumUpperCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordMinimumUpperCase(componentName, n, bl);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int getPasswordQuality(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getPasswordQuality(componentName, n, bl);
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
            public SystemUpdateInfo getPendingSystemUpdate(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(188, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SystemUpdateInfo systemUpdateInfo = Stub.getDefaultImpl().getPendingSystemUpdate((ComponentName)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return systemUpdateInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SystemUpdateInfo systemUpdateInfo = SystemUpdateInfo.CREATOR.createFromParcel(parcel2);
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
            public int getPermissionGrantState(ComponentName componentName, String string2, String string3, String string4) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(192, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPermissionGrantState(componentName, string2, string3, string4);
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
            public int getPermissionPolicy(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(190, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPermissionPolicy(componentName);
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
            public List getPermittedAccessibilityServices(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(119, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPermittedAccessibilityServices((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readArrayList(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List getPermittedAccessibilityServicesForUser(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(120, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List list = Stub.getDefaultImpl().getPermittedAccessibilityServicesForUser(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList arrayList = parcel2.readArrayList(this.getClass().getClassLoader());
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<String> getPermittedCrossProfileNotificationListeners(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(127, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getPermittedCrossProfileNotificationListeners((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List getPermittedInputMethods(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(123, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPermittedInputMethods((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readArrayList(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List getPermittedInputMethodsForCurrentUser() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(124, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List list = Stub.getDefaultImpl().getPermittedInputMethodsForCurrentUser();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList arrayList = parcel2.readArrayList(this.getClass().getClassLoader());
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getProfileOwner(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(74, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getProfileOwner(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            @Override
            public ComponentName getProfileOwnerAsUser(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(73, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getProfileOwnerAsUser(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            @Override
            public String getProfileOwnerName(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(75, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getProfileOwnerName(n);
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
            public int getProfileWithMinimumFailedPasswordsForWipe(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getProfileWithMinimumFailedPasswordsForWipe(n, bl);
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
            public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback, int n) throws RemoteException {
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
                    if (remoteCallback != null) {
                        parcel.writeInt(1);
                        remoteCallback.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getRemoveWarning(componentName, remoteCallback, n);
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
            public long getRequiredStrongAuthTimeout(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getRequiredStrongAuthTimeout(componentName, n, bl);
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ComponentName getRestrictionsProvider(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(113, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ComponentName componentName = Stub.getDefaultImpl().getRestrictionsProvider(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ComponentName componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return componentName;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getScreenCaptureDisabled(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getScreenCaptureDisabled(componentName, n);
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
            public List<UserHandle> getSecondaryUsers(ComponentName arrayList) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(138, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getSecondaryUsers((ComponentName)((Object)arrayList));
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(UserHandle.CREATOR);
                    return arrayList;
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
            public CharSequence getShortSupportMessage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(202, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getShortSupportMessage((ComponentName)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getShortSupportMessageForUser(ComponentName object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(205, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getShortSupportMessageForUser((ComponentName)object, n);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getStartUserSessionMessage(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(258, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getStartUserSessionMessage((ComponentName)object);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean getStorageEncryption(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().getStorageEncryption(componentName, n);
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
            public int getStorageEncryptionStatus(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getStorageEncryptionStatus(string2, n);
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
            public SystemUpdatePolicy getSystemUpdatePolicy() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(182, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        SystemUpdatePolicy systemUpdatePolicy = Stub.getDefaultImpl().getSystemUpdatePolicy();
                        parcel.recycle();
                        parcel2.recycle();
                        return systemUpdatePolicy;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                SystemUpdatePolicy systemUpdatePolicy = parcel.readInt() != 0 ? SystemUpdatePolicy.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return systemUpdatePolicy;
            }

            @Override
            public PersistableBundle getTransferOwnershipBundle() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(255, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        PersistableBundle persistableBundle = Stub.getDefaultImpl().getTransferOwnershipBundle();
                        parcel.recycle();
                        parcel2.recycle();
                        return persistableBundle;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                PersistableBundle persistableBundle = parcel.readInt() != 0 ? PersistableBundle.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return persistableBundle;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public List<PersistableBundle> getTrustAgentConfiguration(ComponentName list, ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (list != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)list)).writeToParcel(parcel, 0);
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
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(171, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getTrustAgentConfiguration((ComponentName)((Object)list), componentName, n, bl);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createTypedArrayList(PersistableBundle.CREATOR);
                    return list;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getUserProvisioningState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(216, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getUserProvisioningState();
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Bundle getUserRestrictions(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(115, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Bundle bundle = Stub.getDefaultImpl().getUserRestrictions((ComponentName)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return bundle;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Bundle bundle = Bundle.CREATOR.createFromParcel(parcel2);
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
            public String getWifiMacAddress(ComponentName object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(199, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getWifiMacAddress((ComponentName)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantDeviceIdsAccessToProfileOwner(ComponentName componentName, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(272, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDeviceIdsAccessToProfileOwner(componentName, n);
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
            public boolean hasDeviceOwner() throws RemoteException {
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
                    if (iBinder.transact(68, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasDeviceOwner();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean hasGrantedPolicy(ComponentName componentName, int n, int n2) throws RemoteException {
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
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(57, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasGrantedPolicy(componentName, n, n2);
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
            public boolean hasUserSetupCompleted() throws RemoteException {
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
                    if (iBinder.transact(79, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasUserSetupCompleted();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean installCaCert(ComponentName componentName, String string2, byte[] arrby) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(85, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().installCaCert(componentName, string2, arrby);
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
            public boolean installExistingPackage(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(141, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().installExistingPackage(componentName, string2, string3);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean installKeyPair(ComponentName componentName, String string2, byte[] arrby, byte[] arrby2, byte[] arrby3, String string3, boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                void var1_6;
                block12 : {
                    boolean bl3;
                    block11 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl3 = true;
                        if (componentName != null) {
                            parcel2.writeInt(1);
                            componentName.writeToParcel(parcel2, 0);
                            break block11;
                        }
                        parcel2.writeInt(0);
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeByteArray(arrby2);
                        parcel2.writeByteArray(arrby3);
                        parcel2.writeString(string3);
                        int n = bl ? 1 : 0;
                        parcel2.writeInt(n);
                        n = bl2 ? 1 : 0;
                        parcel2.writeInt(n);
                        if (!this.mRemote.transact(90, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().installKeyPair(componentName, string2, arrby, arrby2, arrby3, string3, bl, bl2);
                            parcel.recycle();
                            parcel2.recycle();
                            return bl;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        bl = n != 0 ? bl3 : false;
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
                throw var1_6;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void installUpdateFromFile(ComponentName componentName, ParcelFileDescriptor parcelFileDescriptor, StartInstallingUpdateCallback startInstallingUpdateCallback) throws RemoteException {
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
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = startInstallingUpdateCallback != null ? startInstallingUpdateCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(273, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().installUpdateFromFile(componentName, parcelFileDescriptor, startInstallingUpdateCallback);
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
            public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(121, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isAccessibilityServicePermittedByAdmin(componentName, string2, n);
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
            public boolean isActivePasswordSufficient(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                boolean bl2;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        bl2 = true;
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(22, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().isActivePasswordSufficient(n, bl);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isAdminActive(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(51, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isAdminActive(componentName, n);
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
            public boolean isAffiliatedUser() throws RemoteException {
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
                    if (iBinder.transact(220, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isAffiliatedUser();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(102, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isAlwaysOnVpnLockdownEnabled(componentName);
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
            public boolean isApplicationHidden(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(131, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isApplicationHidden(componentName, string2, string3);
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
            public boolean isBackupServiceEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(234, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isBackupServiceEnabled(componentName);
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
            public boolean isCaCertApproved(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(89, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCaCertApproved(string2, n);
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
            public boolean isCallerApplicationRestrictionsManagingPackage(String string2) throws RemoteException {
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
                    if (iBinder.transact(111, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCallerApplicationRestrictionsManagingPackage(string2);
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
            public boolean isCurrentInputMethodSetByOwner() throws RemoteException {
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
                    if (iBinder.transact(248, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCurrentInputMethodSetByOwner();
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
            public boolean isDeviceProvisioned() throws RemoteException {
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
                    if (iBinder.transact(229, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceProvisioned();
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
            public boolean isDeviceProvisioningConfigApplied() throws RemoteException {
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
                    if (iBinder.transact(230, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isDeviceProvisioningConfigApplied();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isEphemeralUser(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(240, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isEphemeralUser(componentName);
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
            public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(125, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isInputMethodPermittedByAdmin(componentName, string2, n);
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
            public boolean isLockTaskPermitted(String string2) throws RemoteException {
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
                    if (iBinder.transact(147, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isLockTaskPermitted(string2);
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
            public boolean isLogoutEnabled() throws RemoteException {
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
                    if (iBinder.transact(252, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isLogoutEnabled();
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
            public boolean isManagedKiosk() throws RemoteException {
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
                    if (iBinder.transact(278, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isManagedKiosk();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isManagedProfile(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(197, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isManagedProfile(componentName);
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
            public boolean isMasterVolumeMuted(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(156, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isMasterVolumeMuted(componentName);
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
            public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(268, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isMeteredDataDisabledPackageForUser(componentName, string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isNetworkLoggingEnabled(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(236, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isNetworkLoggingEnabled(componentName, string2);
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
            public boolean isNotificationListenerServicePermitted(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(128, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNotificationListenerServicePermitted(string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isOverrideApnEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(267, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isOverrideApnEnabled(componentName);
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
            public boolean isPackageAllowedToAccessCalendarForUser(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(276, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPackageAllowedToAccessCalendarForUser(string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isPackageSuspended(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(84, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isPackageSuspended(componentName, string2, string3);
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
            public boolean isProfileActivePasswordSufficientForParent(int n) throws RemoteException {
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
                    if (iBinder.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isProfileActivePasswordSufficientForParent(n);
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
            public boolean isProvisioningAllowed(String string2, String string3) throws RemoteException {
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
                        parcel2.writeString(string3);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(193, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isProvisioningAllowed(string2, string3);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isRemovingAdmin(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(179, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isRemovingAdmin(componentName, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isResetPasswordTokenActive(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(246, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isResetPasswordTokenActive(componentName);
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
            public boolean isSecurityLoggingEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(222, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSecurityLoggingEnabled(componentName);
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
            public boolean isSeparateProfileChallengeAllowed(int n) throws RemoteException {
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
                    if (iBinder.transact(207, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSeparateProfileChallengeAllowed(n);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isSystemOnlyUser(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(198, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSystemOnlyUser(componentName);
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
            public boolean isUnattendedManagedKiosk() throws RemoteException {
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
                    if (iBinder.transact(279, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUnattendedManagedKiosk();
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isUninstallBlocked(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(159, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isUninstallBlocked(componentName, string2);
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
            public boolean isUninstallInQueue(String string2) throws RemoteException {
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
                    if (iBinder.transact(227, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUninstallInQueue(string2);
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
            public boolean isUsingUnifiedPassword(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isUsingUnifiedPassword(componentName);
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
            public void lockNow(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().lockNow(n, bl);
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
            public int logoutUser(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(137, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().logoutUser(componentName);
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
            public void notifyLockTaskModeChanged(boolean bl, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(157, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyLockTaskModeChanged(bl, string2, n);
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
            public void notifyPendingSystemUpdate(SystemUpdateInfo systemUpdateInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (systemUpdateInfo != null) {
                        parcel.writeInt(1);
                        systemUpdateInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(187, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyPendingSystemUpdate(systemUpdateInfo);
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
            public boolean packageHasActiveAdmins(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(53, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().packageHasActiveAdmins(string2, n);
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
            public void reboot(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(200, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reboot(componentName);
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
            public void removeActiveAdmin(ComponentName componentName, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeActiveAdmin(componentName, n);
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
            public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(173, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeCrossProfileWidgetProvider(componentName, string2);
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
            public boolean removeKeyPair(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(91, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeKeyPair(componentName, string2, string3);
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
            public boolean removeOverrideApn(ComponentName componentName, int n) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(264, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeOverrideApn(componentName, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean removeUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
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
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(133, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().removeUser(componentName, userHandle);
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
            public void reportFailedBiometricAttempt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(62, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportFailedBiometricAttempt(n);
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
            public void reportFailedPasswordAttempt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(60, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportFailedPasswordAttempt(n);
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
            public void reportKeyguardDismissed(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(64, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportKeyguardDismissed(n);
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
            public void reportKeyguardSecured(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(65, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportKeyguardSecured(n);
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
            public void reportPasswordChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(59, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportPasswordChanged(n);
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
            public void reportSuccessfulBiometricAttempt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(63, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportSuccessfulBiometricAttempt(n);
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
            public void reportSuccessfulPasswordAttempt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(61, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportSuccessfulPasswordAttempt(n);
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
            public boolean requestBugreport(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().requestBugreport(componentName);
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
            public boolean resetPassword(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(30, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().resetPassword(string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean resetPasswordWithToken(ComponentName componentName, String string2, byte[] arrby, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeByteArray(arrby);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(247, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().resetPasswordWithToken(componentName, string2, arrby, n);
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
            public List<NetworkEvent> retrieveNetworkLogs(ComponentName object, String string2, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(237, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().retrieveNetworkLogs((ComponentName)object, string2, l);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createTypedArrayList(NetworkEvent.CREATOR);
                    return object;
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
            public ParceledListSlice retrievePreRebootSecurityLogs(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(224, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().retrievePreRebootSecurityLogs((ComponentName)parcelable);
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
            public ParceledListSlice retrieveSecurityLogs(ComponentName parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ComponentName)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(223, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().retrieveSecurityLogs((ComponentName)parcelable);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setAccountManagementDisabled(ComponentName componentName, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(142, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAccountManagementDisabled(componentName, string2, bl);
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
            public void setActiveAdmin(ComponentName componentName, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActiveAdmin(componentName, bl, n);
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
            public void setActivePasswordState(PasswordMetrics passwordMetrics, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (passwordMetrics != null) {
                        parcel.writeInt(1);
                        passwordMetrics.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(58, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActivePasswordState(passwordMetrics, n);
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
            public void setAffiliationIds(ComponentName componentName, List<String> list) throws RemoteException {
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
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(218, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAffiliationIds(componentName, list);
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
            public boolean setAlwaysOnVpnPackage(ComponentName componentName, String string2, boolean bl, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(100, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setAlwaysOnVpnPackage(componentName, string2, bl, list);
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
            public boolean setApplicationHidden(ComponentName componentName, String string2, String string3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(130, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setApplicationHidden(componentName, string2, string3, bl);
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
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void setApplicationRestrictions(ComponentName componentName, String string2, String string3, Bundle bundle) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(107, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setApplicationRestrictions(componentName, string2, string3, bundle);
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
            public boolean setApplicationRestrictionsManagingPackage(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(109, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setApplicationRestrictionsManagingPackage(componentName, string2);
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
            public void setAutoTimeRequired(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(175, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutoTimeRequired(componentName, bl);
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
            public void setBackupServiceEnabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(233, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBackupServiceEnabled(componentName, bl);
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
            public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(167, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBluetoothContactSharingDisabled(componentName, bl);
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
            public void setCameraDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCameraDisabled(componentName, bl);
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
            public void setCertInstallerPackage(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(98, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCertInstallerPackage(componentName, string2);
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
            public void setCrossProfileCalendarPackages(ComponentName componentName, List<String> list) throws RemoteException {
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
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(274, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCrossProfileCalendarPackages(componentName, list);
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
            public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(160, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCrossProfileCallerIdDisabled(componentName, bl);
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
            public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(163, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCrossProfileContactsSearchDisabled(componentName, bl);
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
            public void setDefaultSmsApplication(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(106, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDefaultSmsApplication(componentName, string2);
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
            public void setDelegatedScopes(ComponentName componentName, String string2, List<String> list) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(95, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDelegatedScopes(componentName, string2, list);
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
            public boolean setDeviceOwner(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(66, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setDeviceOwner(componentName, string2, n);
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
            public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(81, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceOwnerLockScreenInfo(componentName, charSequence);
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
            public void setDeviceProvisioningConfigApplied() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(231, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceProvisioningConfigApplied();
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
            public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(257, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEndUserSessionMessage(componentName, charSequence);
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
            public void setForceEphemeralUsers(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(177, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForceEphemeralUsers(componentName, bl);
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
            public int setGlobalPrivateDns(ComponentName componentName, int n, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(269, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setGlobalPrivateDns(componentName, n, string2);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ComponentName setGlobalProxy(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        componentName = Stub.getDefaultImpl().setGlobalProxy(componentName, string2, string3);
                        parcel2.recycle();
                        parcel.recycle();
                        return componentName;
                    }
                    parcel2.readException();
                    componentName = parcel2.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.recycle();
                    parcel.recycle();
                    return componentName;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void setGlobalSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(150, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setGlobalSetting(componentName, string2, string3);
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
            public void setKeepUninstalledPackages(ComponentName componentName, String string2, List<String> list) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(195, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setKeepUninstalledPackages(componentName, string2, list);
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
            public boolean setKeyPairCertificate(ComponentName componentName, String string2, String string3, byte[] arrby, byte[] arrby2, boolean bl) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    int n;
                    boolean bl2;
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        if (componentName != null) {
                            parcel2.writeInt(1);
                            componentName.writeToParcel(parcel2, 0);
                            break block15;
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
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby2);
                        n = bl ? 1 : 0;
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(93, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().setKeyPairCertificate(componentName, string2, string3, arrby, arrby2, bl);
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
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setKeyguardDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(184, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setKeyguardDisabled(componentName, bl);
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
            public void setKeyguardDisabledFeatures(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setKeyguardDisabledFeatures(componentName, n, bl);
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
            public void setLockTaskFeatures(ComponentName componentName, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(148, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLockTaskFeatures(componentName, n);
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
            public void setLockTaskPackages(ComponentName componentName, String[] arrstring) throws RemoteException {
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
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(145, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLockTaskPackages(componentName, arrstring);
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
            public void setLogoutEnabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(251, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLogoutEnabled(componentName, bl);
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
            public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(203, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setLongSupportMessage(componentName, charSequence);
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
            public void setMasterVolumeMuted(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(155, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMasterVolumeMuted(componentName, bl);
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
            public void setMaximumFailedPasswordsForWipe(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMaximumFailedPasswordsForWipe(componentName, n, bl);
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
            public void setMaximumTimeToLock(ComponentName componentName, long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMaximumTimeToLock(componentName, l, bl);
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
            public List<String> setMeteredDataDisabledPackages(ComponentName object, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ComponentName)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(260, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().setMeteredDataDisabledPackages((ComponentName)object, list);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createStringArrayList();
                    return object;
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
            public void setNetworkLoggingEnabled(ComponentName componentName, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(235, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setNetworkLoggingEnabled(componentName, string2, bl);
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
            public void setOrganizationColor(ComponentName componentName, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(208, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOrganizationColor(componentName, n);
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
            public void setOrganizationColorForUser(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(209, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOrganizationColorForUser(n, n2);
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
            public void setOrganizationName(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(212, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOrganizationName(componentName, charSequence);
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
            public void setOverrideApnsEnabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(266, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOverrideApnsEnabled(componentName, bl);
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
            public String[] setPackagesSuspended(ComponentName arrstring, String string2, String[] arrstring2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (arrstring != null) {
                        parcel.writeInt(1);
                        arrstring.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring2);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(83, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrstring = Stub.getDefaultImpl().setPackagesSuspended((ComponentName)arrstring, string2, arrstring2, bl);
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
            public void setPasswordExpirationTimeout(ComponentName componentName, long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordExpirationTimeout(componentName, l, bl);
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
            public void setPasswordHistoryLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordHistoryLength(componentName, n, bl);
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
            public void setPasswordMinimumLength(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumLength(componentName, n, bl);
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
            public void setPasswordMinimumLetters(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumLetters(componentName, n, bl);
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
            public void setPasswordMinimumLowerCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumLowerCase(componentName, n, bl);
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
            public void setPasswordMinimumNonLetter(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumNonLetter(componentName, n, bl);
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
            public void setPasswordMinimumNumeric(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumNumeric(componentName, n, bl);
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
            public void setPasswordMinimumSymbols(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumSymbols(componentName, n, bl);
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
            public void setPasswordMinimumUpperCase(ComponentName componentName, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordMinimumUpperCase(componentName, n, bl);
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
            public void setPasswordQuality(ComponentName componentName, int n, boolean bl) throws RemoteException {
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
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPasswordQuality(componentName, n, bl);
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
            public void setPermissionGrantState(ComponentName componentName, String string2, String string3, String string4, int n, RemoteCallback remoteCallback) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (componentName != null) {
                            parcel2.writeInt(1);
                            componentName.writeToParcel(parcel2, 0);
                            break block15;
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
                        parcel2.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                        if (remoteCallback != null) {
                            parcel2.writeInt(1);
                            remoteCallback.writeToParcel(parcel2, 0);
                        } else {
                            parcel2.writeInt(0);
                        }
                        if (!this.mRemote.transact(191, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().setPermissionGrantState(componentName, string2, string3, string4, n, remoteCallback);
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
                throw var1_7;
            }

            @Override
            public void setPermissionPolicy(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(189, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPermissionPolicy(componentName, string2, n);
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
            public boolean setPermittedAccessibilityServices(ComponentName componentName, List list) throws RemoteException {
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
                    parcel.writeList(list);
                    if (!this.mRemote.transact(118, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPermittedAccessibilityServices(componentName, list);
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
            public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) throws RemoteException {
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
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(126, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPermittedCrossProfileNotificationListeners(componentName, list);
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
            public boolean setPermittedInputMethods(ComponentName componentName, List list) throws RemoteException {
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
                    parcel.writeList(list);
                    if (!this.mRemote.transact(122, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPermittedInputMethods(componentName, list);
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
            public void setProfileEnabled(ComponentName componentName) throws RemoteException {
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
                    if (!this.mRemote.transact(76, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProfileEnabled(componentName);
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
            public void setProfileName(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(77, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProfileName(componentName, string2);
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
            public boolean setProfileOwner(ComponentName componentName, String string2, int n) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(72, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setProfileOwner(componentName, string2, n);
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
            public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) throws RemoteException {
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
                    if (proxyInfo != null) {
                        parcel.writeInt(1);
                        proxyInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRecommendedGlobalProxy(componentName, proxyInfo);
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
            public void setRequiredStrongAuthTimeout(ComponentName componentName, long l, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequiredStrongAuthTimeout(componentName, l, bl);
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
            public boolean setResetPasswordToken(ComponentName componentName, byte[] arrby) throws RemoteException {
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
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(244, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setResetPasswordToken(componentName, arrby);
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
            public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) throws RemoteException {
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
                    if (componentName2 != null) {
                        parcel.writeInt(1);
                        componentName2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(112, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRestrictionsProvider(componentName, componentName2);
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
            public void setScreenCaptureDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setScreenCaptureDisabled(componentName, bl);
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
            public void setSecureSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(152, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSecureSetting(componentName, string2, string3);
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
            public void setSecurityLoggingEnabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(221, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSecurityLoggingEnabled(componentName, bl);
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
            public void setShortSupportMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(201, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setShortSupportMessage(componentName, charSequence);
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
            public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(256, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStartUserSessionMessage(componentName, charSequence);
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
            public boolean setStatusBarDisabled(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl2 = true;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(185, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setStatusBarDisabled(componentName, bl);
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
            public int setStorageEncryption(ComponentName componentName, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().setStorageEncryption(componentName, bl);
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
            public void setSystemSetting(ComponentName componentName, String string2, String string3) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(151, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemSetting(componentName, string2, string3);
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
            public void setSystemUpdatePolicy(ComponentName componentName, SystemUpdatePolicy systemUpdatePolicy) throws RemoteException {
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
                    if (systemUpdatePolicy != null) {
                        parcel.writeInt(1);
                        systemUpdatePolicy.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(181, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemUpdatePolicy(componentName, systemUpdatePolicy);
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
            public boolean setTime(ComponentName componentName, long l) throws RemoteException {
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
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(153, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setTime(componentName, l);
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
            public boolean setTimeZone(ComponentName componentName, String string2) throws RemoteException {
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
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(154, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setTimeZone(componentName, string2);
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
            public void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (componentName2 != null) {
                        parcel.writeInt(1);
                        componentName2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(170, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTrustAgentConfiguration(componentName, componentName2, persistableBundle, bl);
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
            public void setUninstallBlocked(ComponentName componentName, String string2, String string3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(158, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUninstallBlocked(componentName, string2, string3, bl);
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
            public void setUserIcon(ComponentName componentName, Bitmap bitmap) throws RemoteException {
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
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(180, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserIcon(componentName, bitmap);
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
            public void setUserProvisioningState(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(217, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserProvisioningState(n, n2);
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
            public void setUserRestriction(ComponentName componentName, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(114, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUserRestriction(componentName, string2, bl);
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
            public void startManagedQuickContact(String string2, long l, boolean bl, long l2, Intent intent) throws RemoteException {
                Parcel parcel2;
                Parcel parcel;
                void var1_6;
                block12 : {
                    block11 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block12;
                        }
                        try {
                            parcel2.writeLong(l);
                            int n = bl ? 1 : 0;
                            parcel2.writeInt(n);
                            parcel2.writeLong(l2);
                            if (intent != null) {
                                parcel2.writeInt(1);
                                intent.writeToParcel(parcel2, 0);
                                break block11;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(166, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startManagedQuickContact(string2, l, bl, l2, intent);
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
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_6;
            }

            @Override
            public int startUserInBackground(ComponentName componentName, UserHandle userHandle) throws RemoteException {
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
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(135, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startUserInBackground(componentName, userHandle);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public boolean startViewCalendarEventInManagedProfile(String string2, long l, long l2, long l3, boolean bl, int n) throws RemoteException {
                Parcel parcel2;
                void var1_5;
                Parcel parcel;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block8;
                    }
                    try {
                        parcel.writeLong(l);
                        parcel.writeLong(l2);
                        parcel.writeLong(l3);
                        boolean bl2 = true;
                        int n2 = bl ? 1 : 0;
                        parcel.writeInt(n2);
                        parcel.writeInt(n);
                        if (!this.mRemote.transact(280, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            bl = Stub.getDefaultImpl().startViewCalendarEventInManagedProfile(string2, l, l2, l3, bl, n);
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
                    catch (Throwable throwable) {}
                    break block8;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public int stopUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
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
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(136, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().stopUser(componentName, userHandle);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean switchUser(ComponentName componentName, UserHandle userHandle) throws RemoteException {
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
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(134, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().switchUser(componentName, userHandle);
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
            public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) throws RemoteException {
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
                    if (componentName2 != null) {
                        parcel.writeInt(1);
                        componentName2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (persistableBundle != null) {
                        parcel.writeInt(1);
                        persistableBundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(254, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().transferOwnership(componentName, componentName2, persistableBundle);
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
            public void uninstallCaCerts(ComponentName componentName, String string2, String[] arrstring) throws RemoteException {
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
                    parcel.writeString(string2);
                    parcel.writeStringArray(arrstring);
                    if (!this.mRemote.transact(86, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().uninstallCaCerts(componentName, string2, arrstring);
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
            public void uninstallPackageWithActiveAdmins(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(228, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().uninstallPackageWithActiveAdmins(string2);
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
            public boolean updateOverrideApn(ComponentName componentName, int n, ApnSetting apnSetting) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (apnSetting != null) {
                        parcel.writeInt(1);
                        apnSetting.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(263, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().updateOverrideApn(componentName, n, apnSetting);
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
            public void wipeDataWithReason(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().wipeDataWithReason(n, string2);
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

