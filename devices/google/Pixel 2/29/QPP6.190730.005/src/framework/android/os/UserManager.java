/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.accounts.AccountManager;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IUserManager;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import com.android.internal.os.RoSystemProperties;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserManager {
    private static final String ACTION_CREATE_USER = "android.os.action.CREATE_USER";
    @SystemApi
    public static final String ACTION_USER_RESTRICTIONS_CHANGED = "android.os.action.USER_RESTRICTIONS_CHANGED";
    public static final String ALLOW_PARENT_PROFILE_APP_LINKING = "allow_parent_profile_app_linking";
    public static final String DISALLOW_ADD_MANAGED_PROFILE = "no_add_managed_profile";
    public static final String DISALLOW_ADD_USER = "no_add_user";
    public static final String DISALLOW_ADJUST_VOLUME = "no_adjust_volume";
    public static final String DISALLOW_AIRPLANE_MODE = "no_airplane_mode";
    public static final String DISALLOW_AMBIENT_DISPLAY = "no_ambient_display";
    public static final String DISALLOW_APPS_CONTROL = "no_control_apps";
    public static final String DISALLOW_AUTOFILL = "no_autofill";
    public static final String DISALLOW_BLUETOOTH = "no_bluetooth";
    public static final String DISALLOW_BLUETOOTH_SHARING = "no_bluetooth_sharing";
    public static final String DISALLOW_CAMERA = "no_camera";
    public static final String DISALLOW_CONFIG_BLUETOOTH = "no_config_bluetooth";
    public static final String DISALLOW_CONFIG_BRIGHTNESS = "no_config_brightness";
    public static final String DISALLOW_CONFIG_CELL_BROADCASTS = "no_config_cell_broadcasts";
    public static final String DISALLOW_CONFIG_CREDENTIALS = "no_config_credentials";
    public static final String DISALLOW_CONFIG_DATE_TIME = "no_config_date_time";
    public static final String DISALLOW_CONFIG_LOCALE = "no_config_locale";
    public static final String DISALLOW_CONFIG_LOCATION = "no_config_location";
    public static final String DISALLOW_CONFIG_MOBILE_NETWORKS = "no_config_mobile_networks";
    public static final String DISALLOW_CONFIG_PRIVATE_DNS = "disallow_config_private_dns";
    public static final String DISALLOW_CONFIG_SCREEN_TIMEOUT = "no_config_screen_timeout";
    public static final String DISALLOW_CONFIG_TETHERING = "no_config_tethering";
    public static final String DISALLOW_CONFIG_VPN = "no_config_vpn";
    public static final String DISALLOW_CONFIG_WIFI = "no_config_wifi";
    public static final String DISALLOW_CONTENT_CAPTURE = "no_content_capture";
    public static final String DISALLOW_CONTENT_SUGGESTIONS = "no_content_suggestions";
    public static final String DISALLOW_CREATE_WINDOWS = "no_create_windows";
    public static final String DISALLOW_CROSS_PROFILE_COPY_PASTE = "no_cross_profile_copy_paste";
    public static final String DISALLOW_DATA_ROAMING = "no_data_roaming";
    public static final String DISALLOW_DEBUGGING_FEATURES = "no_debugging_features";
    public static final String DISALLOW_FACTORY_RESET = "no_factory_reset";
    public static final String DISALLOW_FUN = "no_fun";
    public static final String DISALLOW_INSTALL_APPS = "no_install_apps";
    public static final String DISALLOW_INSTALL_UNKNOWN_SOURCES = "no_install_unknown_sources";
    public static final String DISALLOW_INSTALL_UNKNOWN_SOURCES_GLOBALLY = "no_install_unknown_sources_globally";
    public static final String DISALLOW_MODIFY_ACCOUNTS = "no_modify_accounts";
    public static final String DISALLOW_MOUNT_PHYSICAL_MEDIA = "no_physical_media";
    public static final String DISALLOW_NETWORK_RESET = "no_network_reset";
    @SystemApi
    @Deprecated
    public static final String DISALLOW_OEM_UNLOCK = "no_oem_unlock";
    public static final String DISALLOW_OUTGOING_BEAM = "no_outgoing_beam";
    public static final String DISALLOW_OUTGOING_CALLS = "no_outgoing_calls";
    public static final String DISALLOW_PRINTING = "no_printing";
    @UnsupportedAppUsage
    public static final String DISALLOW_RECORD_AUDIO = "no_record_audio";
    public static final String DISALLOW_REMOVE_MANAGED_PROFILE = "no_remove_managed_profile";
    public static final String DISALLOW_REMOVE_USER = "no_remove_user";
    @SystemApi
    public static final String DISALLOW_RUN_IN_BACKGROUND = "no_run_in_background";
    public static final String DISALLOW_SAFE_BOOT = "no_safe_boot";
    public static final String DISALLOW_SET_USER_ICON = "no_set_user_icon";
    public static final String DISALLOW_SET_WALLPAPER = "no_set_wallpaper";
    public static final String DISALLOW_SHARE_INTO_MANAGED_PROFILE = "no_sharing_into_profile";
    public static final String DISALLOW_SHARE_LOCATION = "no_share_location";
    public static final String DISALLOW_SMS = "no_sms";
    public static final String DISALLOW_SYSTEM_ERROR_DIALOGS = "no_system_error_dialogs";
    public static final String DISALLOW_UNIFIED_PASSWORD = "no_unified_password";
    public static final String DISALLOW_UNINSTALL_APPS = "no_uninstall_apps";
    public static final String DISALLOW_UNMUTE_DEVICE = "disallow_unmute_device";
    public static final String DISALLOW_UNMUTE_MICROPHONE = "no_unmute_microphone";
    public static final String DISALLOW_USB_FILE_TRANSFER = "no_usb_file_transfer";
    public static final String DISALLOW_USER_SWITCH = "no_user_switch";
    public static final String DISALLOW_WALLPAPER = "no_wallpaper";
    public static final String ENSURE_VERIFY_APPS = "ensure_verify_apps";
    public static final String EXTRA_USER_ACCOUNT_NAME = "android.os.extra.USER_ACCOUNT_NAME";
    public static final String EXTRA_USER_ACCOUNT_OPTIONS = "android.os.extra.USER_ACCOUNT_OPTIONS";
    public static final String EXTRA_USER_ACCOUNT_TYPE = "android.os.extra.USER_ACCOUNT_TYPE";
    public static final String EXTRA_USER_NAME = "android.os.extra.USER_NAME";
    public static final String KEY_RESTRICTIONS_PENDING = "restrictions_pending";
    public static final int PIN_VERIFICATION_FAILED_INCORRECT = -3;
    public static final int PIN_VERIFICATION_FAILED_NOT_SET = -2;
    public static final int PIN_VERIFICATION_SUCCESS = -1;
    @SystemApi
    public static final int RESTRICTION_NOT_SET = 0;
    @SystemApi
    public static final int RESTRICTION_SOURCE_DEVICE_OWNER = 2;
    @SystemApi
    public static final int RESTRICTION_SOURCE_PROFILE_OWNER = 4;
    @SystemApi
    public static final int RESTRICTION_SOURCE_SYSTEM = 1;
    @SystemApi
    public static final int SWITCHABILITY_STATUS_OK = 0;
    @SystemApi
    public static final int SWITCHABILITY_STATUS_SYSTEM_USER_LOCKED = 4;
    @SystemApi
    public static final int SWITCHABILITY_STATUS_USER_IN_CALL = 1;
    @SystemApi
    public static final int SWITCHABILITY_STATUS_USER_SWITCH_DISALLOWED = 2;
    private static final String TAG = "UserManager";
    public static final int USER_CREATION_FAILED_NOT_PERMITTED = 1;
    public static final int USER_CREATION_FAILED_NO_MORE_USERS = 2;
    public static final int USER_OPERATION_ERROR_CURRENT_USER = 4;
    public static final int USER_OPERATION_ERROR_LOW_STORAGE = 5;
    public static final int USER_OPERATION_ERROR_MANAGED_PROFILE = 2;
    public static final int USER_OPERATION_ERROR_MAX_RUNNING_USERS = 3;
    public static final int USER_OPERATION_ERROR_MAX_USERS = 6;
    public static final int USER_OPERATION_ERROR_UNKNOWN = 1;
    public static final int USER_OPERATION_SUCCESS = 0;
    private final Context mContext;
    private Boolean mIsManagedProfileCached;
    @UnsupportedAppUsage
    private final IUserManager mService;

    public UserManager(Context context, IUserManager iUserManager) {
        this.mService = iUserManager;
        this.mContext = context.getApplicationContext();
    }

    public static Intent createUserCreationIntent(String string2, String string3, String string4, PersistableBundle persistableBundle) {
        Intent intent = new Intent(ACTION_CREATE_USER);
        if (string2 != null) {
            intent.putExtra(EXTRA_USER_NAME, string2);
        }
        if (string3 != null && string4 == null) {
            throw new IllegalArgumentException("accountType must be specified if accountName is specified");
        }
        if (string3 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_NAME, string3);
        }
        if (string4 != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_TYPE, string4);
        }
        if (persistableBundle != null) {
            intent.putExtra(EXTRA_USER_ACCOUNT_OPTIONS, persistableBundle);
        }
        return intent;
    }

    @UnsupportedAppUsage
    public static UserManager get(Context context) {
        return (UserManager)context.getSystemService("user");
    }

    @UnsupportedAppUsage
    public static int getMaxSupportedUsers() {
        if (Build.ID.startsWith("JVP")) {
            return 1;
        }
        if (ActivityManager.isLowRamDeviceStatic() && (Resources.getSystem().getConfiguration().uiMode & 15) != 4) {
            return 1;
        }
        return SystemProperties.getInt("fw.max_users", Resources.getSystem().getInteger(17694846));
    }

    @UnsupportedAppUsage
    public static boolean isDeviceInDemoMode(Context object) {
        object = ((Context)object).getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)object, "device_demo_mode", 0) > 0) {
            bl = true;
        }
        return bl;
    }

    public static boolean isGuestUserEphemeral() {
        return Resources.getSystem().getBoolean(17891461);
    }

    public static boolean isSplitSystemUser() {
        return RoSystemProperties.FW_SYSTEM_USER_SPLIT;
    }

    public static boolean supportsMultipleUsers() {
        int n = UserManager.getMaxSupportedUsers();
        boolean bl = true;
        if (n <= 1 || !SystemProperties.getBoolean("fw.show_multiuserui", Resources.getSystem().getBoolean(17891443))) {
            bl = false;
        }
        return bl;
    }

    public boolean canAddMoreManagedProfiles(int n, boolean bl) {
        try {
            bl = this.mService.canAddMoreManagedProfiles(n, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean canAddMoreUsers() {
        boolean bl = true;
        List<UserInfo> list = this.getUsers(true);
        int n = list.size();
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = n2;
            if (!list.get(i).isGuest()) {
                n3 = n2 + 1;
            }
            n2 = n3;
        }
        if (n2 >= UserManager.getMaxSupportedUsers()) {
            bl = false;
        }
        return bl;
    }

    public boolean canHaveRestrictedProfile(int n) {
        try {
            boolean bl = this.mService.canHaveRestrictedProfile(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean canSwitchUsers() {
        boolean bl;
        block6 : {
            boolean bl2;
            boolean bl3;
            boolean bl4;
            block5 : {
                ContentResolver contentResolver = this.mContext.getContentResolver();
                bl2 = false;
                boolean bl5 = Settings.Global.getInt(contentResolver, "allow_user_switching_when_system_user_locked", 0) != 0;
                boolean bl6 = this.isUserUnlocked(UserHandle.SYSTEM);
                bl4 = TelephonyManager.getDefault().getCallState() != 0;
                bl3 = this.hasUserRestriction(DISALLOW_USER_SWITCH);
                if (bl5) break block5;
                bl = bl2;
                if (!bl6) break block6;
            }
            bl = bl2;
            if (!bl4) {
                bl = bl2;
                if (!bl3) {
                    bl = true;
                }
            }
        }
        return bl;
    }

    @SystemApi
    public void clearSeedAccountData() {
        try {
            this.mService.clearSeedAccountData();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserInfo createGuest(Context context, String object) {
        block3 : {
            try {
                object = this.mService.createUser((String)object, 4);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            Settings.Secure.putStringForUser(context.getContentResolver(), "skip_first_use_hints", "1", ((UserInfo)object).id);
        }
        return object;
    }

    @UnsupportedAppUsage
    public UserInfo createProfileForUser(String string2, int n, int n2) {
        return this.createProfileForUser(string2, n, n2, null);
    }

    public UserInfo createProfileForUser(String object, int n, int n2, String[] arrstring) {
        try {
            object = this.mService.createProfileForUser((String)object, n, n2, arrstring);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserInfo createProfileForUserEvenWhenDisallowed(String object, int n, int n2, String[] arrstring) {
        try {
            object = this.mService.createProfileForUserEvenWhenDisallowed((String)object, n, n2, arrstring);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserInfo createRestrictedProfile(String object) {
        block3 : {
            UserHandle userHandle;
            try {
                userHandle = Process.myUserHandle();
                object = this.mService.createRestrictedProfile((String)object, userHandle.getIdentifier());
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            AccountManager.get(this.mContext).addSharedAccountsFromParentUser(userHandle, UserHandle.of(((UserInfo)object).id));
        }
        return object;
    }

    @UnsupportedAppUsage
    public UserInfo createUser(String object, int n) {
        block3 : {
            try {
                object = this.mService.createUser((String)object, n);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            if (((UserInfo)object).isAdmin() || ((UserInfo)object).isDemo()) break block3;
            this.mService.setUserRestriction(DISALLOW_SMS, true, ((UserInfo)object).id);
            this.mService.setUserRestriction(DISALLOW_OUTGOING_CALLS, true, ((UserInfo)object).id);
        }
        return object;
    }

    public void evictCredentialEncryptionKey(int n) {
        try {
            this.mService.evictCredentialEncryptionKey(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions(String object) {
        try {
            object = this.mService.getApplicationRestrictions((String)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getApplicationRestrictions(String object, UserHandle userHandle) {
        try {
            object = this.mService.getApplicationRestrictionsForUser((String)object, userHandle.getIdentifier());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Drawable getBadgedDrawableForUser(Drawable drawable2, UserHandle userHandle, Rect rect, int n) {
        return this.mContext.getPackageManager().getUserBadgedDrawableForDensity(drawable2, userHandle, rect, n);
    }

    public Drawable getBadgedIconForUser(Drawable drawable2, UserHandle userHandle) {
        return this.mContext.getPackageManager().getUserBadgedIcon(drawable2, userHandle);
    }

    public CharSequence getBadgedLabelForUser(CharSequence charSequence, UserHandle userHandle) {
        return this.mContext.getPackageManager().getUserBadgedLabel(charSequence, userHandle);
    }

    public int getCredentialOwnerProfile(int n) {
        try {
            n = this.mService.getCredentialOwnerProfile(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getDefaultGuestRestrictions() {
        try {
            Bundle bundle = this.mService.getDefaultGuestRestrictions();
            return bundle;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int[] getEnabledProfileIds(int n) {
        return this.getProfileIds(n, true);
    }

    @UnsupportedAppUsage
    public List<UserInfo> getEnabledProfiles(int n) {
        try {
            List<UserInfo> list = this.mService.getProfiles(n, true);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getManagedProfileBadge(int n) {
        try {
            n = this.mService.getManagedProfileBadge(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserInfo getPrimaryUser() {
        try {
            UserInfo userInfo = this.mService.getPrimaryUser();
            return userInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int[] getProfileIds(int n, boolean bl) {
        try {
            int[] arrn = this.mService.getProfileIds(n, bl);
            return arrn;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int[] getProfileIdsWithDisabled(int n) {
        return this.getProfileIds(n, false);
    }

    @UnsupportedAppUsage
    public UserInfo getProfileParent(int n) {
        try {
            UserInfo userInfo = this.mService.getProfileParent(n);
            return userInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getProfileParent(UserHandle parcelable) {
        if ((parcelable = this.getProfileParent(parcelable.getIdentifier())) == null) {
            return null;
        }
        return UserHandle.of(((UserInfo)parcelable).id);
    }

    @UnsupportedAppUsage
    public List<UserInfo> getProfiles(int n) {
        try {
            List<UserInfo> list = this.mService.getProfiles(n, false);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getSeedAccountName() {
        try {
            String string2 = this.mService.getSeedAccountName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public PersistableBundle getSeedAccountOptions() {
        try {
            PersistableBundle persistableBundle = this.mService.getSeedAccountOptions();
            return persistableBundle;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public String getSeedAccountType() {
        try {
            String string2 = this.mService.getSeedAccountType();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getSerialNumberForUser(UserHandle userHandle) {
        return this.getUserSerialNumber(userHandle.getIdentifier());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @SystemApi
    public long[] getSerialNumbersOfUsers(boolean bl) {
        int n;
        long[] arrl;
        List<UserInfo> list;
        try {
            list = this.mService.getUsers(bl);
            arrl = new long[list.size()];
            n = 0;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        do {
            if (n >= arrl.length) return arrl;
            arrl[n] = list.get((int)n).serialNumber;
            ++n;
            continue;
            break;
        } while (true);
    }

    public String getUserAccount(int n) {
        try {
            String string2 = this.mService.getUserAccount(n);
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getUserCount() {
        List<UserInfo> list = this.getUsers();
        int n = list != null ? list.size() : 1;
        return n;
    }

    public long getUserCreationTime(UserHandle userHandle) {
        try {
            long l = this.mService.getUserCreationTime(userHandle.getIdentifier());
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UserHandle getUserForSerialNumber(long l) {
        int n = this.getUserHandle((int)l);
        UserHandle userHandle = n >= 0 ? new UserHandle(n) : null;
        return userHandle;
    }

    @UnsupportedAppUsage
    public int getUserHandle() {
        return UserHandle.myUserId();
    }

    @UnsupportedAppUsage
    public int getUserHandle(int n) {
        try {
            n = this.mService.getUserHandle(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public Bitmap getUserIcon() {
        return this.getUserIcon(this.getUserHandle());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public Bitmap getUserIcon(int n) {
        ParcelFileDescriptor parcelFileDescriptor;
        try {
            parcelFileDescriptor = this.mService.getUserIcon(n);
            if (parcelFileDescriptor == null) return null;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        Bitmap bitmap = BitmapFactory.decodeFileDescriptor(parcelFileDescriptor.getFileDescriptor());
        {
            catch (Throwable throwable) {
                try {
                    parcelFileDescriptor.close();
                    throw throwable;
                }
                catch (IOException iOException) {
                    // empty catch block
                }
                throw throwable;
            }
        }
        try {
            parcelFileDescriptor.close();
            return bitmap;
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return bitmap;
    }

    @UnsupportedAppUsage
    public UserInfo getUserInfo(int n) {
        try {
            UserInfo userInfo = this.mService.getUserInfo(n);
            return userInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getUserName() {
        try {
            String string2 = this.mService.getUserName();
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<UserHandle> getUserProfiles() {
        int[] arrn = this.getProfileIds(UserHandle.myUserId(), true);
        ArrayList<UserHandle> arrayList = new ArrayList<UserHandle>(arrn.length);
        int n = arrn.length;
        for (int i = 0; i < n; ++i) {
            arrayList.add(UserHandle.of(arrn[i]));
        }
        return arrayList;
    }

    @SystemApi
    @Deprecated
    public int getUserRestrictionSource(String string2, UserHandle userHandle) {
        try {
            int n = this.mService.getUserRestrictionSource(string2, userHandle.getIdentifier());
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<EnforcingUser> getUserRestrictionSources(String object, UserHandle userHandle) {
        try {
            object = this.mService.getUserRestrictionSources((String)object, userHandle.getIdentifier());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Bundle getUserRestrictions() {
        return this.getUserRestrictions(Process.myUserHandle());
    }

    public Bundle getUserRestrictions(UserHandle parcelable) {
        try {
            parcelable = this.mService.getUserRestrictions(parcelable.getIdentifier());
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public int getUserSerialNumber(int n) {
        try {
            n = this.mService.getUserSerialNumber(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public long getUserStartRealtime() {
        try {
            long l = this.mService.getUserStartRealtime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getUserSwitchability() {
        Object object = this.mContext.getContentResolver();
        boolean bl = false;
        if (Settings.Global.getInt((ContentResolver)object, "allow_user_switching_when_system_user_locked", 0) != 0) {
            bl = true;
        }
        boolean bl2 = this.isUserUnlocked(UserHandle.SYSTEM);
        object = (TelephonyManager)this.mContext.getSystemService("phone");
        int n = 0;
        if (((TelephonyManager)object).getCallState() != 0) {
            n = false | true;
        }
        int n2 = n;
        if (this.hasUserRestriction(DISALLOW_USER_SWITCH)) {
            n2 = n | 2;
        }
        n = n2;
        if (!bl) {
            n = n2;
            if (!bl2) {
                n = n2 | 4;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    public long getUserUnlockRealtime() {
        try {
            long l = this.mService.getUserUnlockRealtime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public List<UserInfo> getUsers() {
        try {
            List<UserInfo> list = this.mService.getUsers(false);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public List<UserInfo> getUsers(boolean bl) {
        try {
            List<UserInfo> list = this.mService.getUsers(bl);
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean hasBaseUserRestriction(String string2, UserHandle userHandle) {
        try {
            boolean bl = this.mService.hasBaseUserRestriction(string2, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean hasRestrictedProfiles() {
        try {
            boolean bl = this.mService.hasRestrictedProfiles();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasUserRestriction(String string2) {
        return this.hasUserRestriction(string2, Process.myUserHandle());
    }

    @UnsupportedAppUsage
    public boolean hasUserRestriction(String string2, UserHandle userHandle) {
        try {
            boolean bl = this.mService.hasUserRestriction(string2, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasUserRestrictionOnAnyUser(String string2) {
        try {
            boolean bl = this.mService.hasUserRestrictionOnAnyUser(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isAdminUser() {
        return this.isUserAdmin(UserHandle.myUserId());
    }

    public boolean isDemoUser() {
        try {
            boolean bl = this.mService.isDemoUser(UserHandle.myUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isEphemeralUser() {
        return this.isUserEphemeral(UserHandle.myUserId());
    }

    @SystemApi
    public boolean isGuestUser() {
        UserInfo userInfo = this.getUserInfo(UserHandle.myUserId());
        boolean bl = userInfo != null && userInfo.isGuest();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isGuestUser(int n) {
        UserInfo userInfo = this.getUserInfo(n);
        boolean bl = userInfo != null && userInfo.isGuest();
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean isLinkedUser() {
        return this.isRestrictedProfile();
    }

    @SystemApi
    public boolean isManagedProfile() {
        Boolean bl = this.mIsManagedProfileCached;
        if (bl != null) {
            return bl;
        }
        try {
            this.mIsManagedProfileCached = this.mService.isManagedProfile(UserHandle.myUserId());
            boolean bl2 = this.mIsManagedProfileCached;
            return bl2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isManagedProfile(int n) {
        if (n == UserHandle.myUserId()) {
            return this.isManagedProfile();
        }
        try {
            boolean bl = this.mService.isManagedProfile(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isPrimaryUser() {
        UserInfo userInfo = this.getUserInfo(UserHandle.myUserId());
        boolean bl = userInfo != null && userInfo.isPrimary();
        return bl;
    }

    public boolean isQuietModeEnabled(UserHandle userHandle) {
        try {
            boolean bl = this.mService.isQuietModeEnabled(userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isRestrictedProfile() {
        try {
            boolean bl = this.mService.isRestricted();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isRestrictedProfile(UserHandle userHandle) {
        try {
            boolean bl = this.mService.getUserInfo(userHandle.getIdentifier()).isRestricted();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isSameProfileGroup(int n, int n2) {
        try {
            boolean bl = this.mService.isSameProfileGroup(n, n2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isSystemUser() {
        boolean bl = UserHandle.myUserId() == 0;
        return bl;
    }

    public boolean isUserAGoat() {
        return this.mContext.getPackageManager().isPackageAvailable("com.coffeestainstudios.goatsimulator");
    }

    @UnsupportedAppUsage
    public boolean isUserAdmin(int n) {
        UserInfo userInfo = this.getUserInfo(n);
        boolean bl = userInfo != null && userInfo.isAdmin();
        return bl;
    }

    public boolean isUserEphemeral(int n) {
        UserInfo userInfo = this.getUserInfo(n);
        boolean bl = userInfo != null && userInfo.isEphemeral();
        return bl;
    }

    public boolean isUserNameSet() {
        try {
            boolean bl = this.mService.isUserNameSet(this.getUserHandle());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunning(int n) {
        try {
            boolean bl = this.mService.isUserRunning(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUserRunning(UserHandle userHandle) {
        return this.isUserRunning(userHandle.getIdentifier());
    }

    public boolean isUserRunningOrStopping(UserHandle userHandle) {
        try {
            boolean bl = ActivityManager.getService().isUserRunning(userHandle.getIdentifier(), 1);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUserSwitcherEnabled() {
        boolean bl = UserManager.supportsMultipleUsers();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (this.hasUserRestriction(DISALLOW_USER_SWITCH)) {
            return false;
        }
        if (UserManager.isDeviceInDemoMode(this.mContext)) {
            return false;
        }
        int n = Settings.Global.getInt(this.mContext.getContentResolver(), "user_switcher_enabled", 1) != 0 ? 1 : 0;
        if (n == 0) {
            return false;
        }
        List<UserInfo> list = this.getUsers(true);
        if (list == null) {
            return false;
        }
        int n2 = 0;
        list = list.iterator();
        while (list.hasNext()) {
            n = n2;
            if (((UserInfo)list.next()).supportsSwitchToByUser()) {
                n = n2 + 1;
            }
            n2 = n;
        }
        bl = this.mContext.getSystemService(DevicePolicyManager.class).getGuestUserDisabled(null);
        if (n2 > 1 || bl ^ true) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isUserUnlocked() {
        return this.isUserUnlocked(Process.myUserHandle());
    }

    @UnsupportedAppUsage
    public boolean isUserUnlocked(int n) {
        try {
            boolean bl = this.mService.isUserUnlocked(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUserUnlocked(UserHandle userHandle) {
        return this.isUserUnlocked(userHandle.getIdentifier());
    }

    public boolean isUserUnlockingOrUnlocked(int n) {
        try {
            boolean bl = this.mService.isUserUnlockingOrUnlocked(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUserUnlockingOrUnlocked(UserHandle userHandle) {
        return this.isUserUnlockingOrUnlocked(userHandle.getIdentifier());
    }

    public boolean markGuestForDeletion(int n) {
        try {
            boolean bl = this.mService.markGuestForDeletion(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean removeUser(int n) {
        try {
            boolean bl = this.mService.removeUser(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean removeUser(UserHandle userHandle) {
        if (userHandle != null) {
            return this.removeUser(userHandle.getIdentifier());
        }
        throw new IllegalArgumentException("user cannot be null");
    }

    public boolean removeUserEvenWhenDisallowed(int n) {
        try {
            boolean bl = this.mService.removeUserEvenWhenDisallowed(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean requestQuietModeEnabled(boolean bl, UserHandle userHandle) {
        return this.requestQuietModeEnabled(bl, userHandle, null);
    }

    public boolean requestQuietModeEnabled(boolean bl, UserHandle userHandle, IntentSender intentSender) {
        try {
            bl = this.mService.requestQuietModeEnabled(this.mContext.getPackageName(), bl, userHandle.getIdentifier(), intentSender);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setApplicationRestrictions(String string2, Bundle bundle, UserHandle userHandle) {
        try {
            this.mService.setApplicationRestrictions(string2, bundle, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDefaultGuestRestrictions(Bundle bundle) {
        try {
            this.mService.setDefaultGuestRestrictions(bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public boolean setRestrictionsChallenge(String string2) {
        return false;
    }

    public void setSeedAccountData(int n, String string2, String string3, PersistableBundle persistableBundle) {
        try {
            this.mService.setSeedAccountData(n, string2, string3, persistableBundle, true);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserAccount(int n, String string2) {
        try {
            this.mService.setUserAccount(n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserAdmin(int n) {
        try {
            this.mService.setUserAdmin(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserEnabled(int n) {
        try {
            this.mService.setUserEnabled(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserIcon(int n, Bitmap bitmap) {
        try {
            this.mService.setUserIcon(n, bitmap);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setUserIcon(Bitmap bitmap) {
        this.setUserIcon(this.getUserHandle(), bitmap);
    }

    public void setUserName(int n, String string2) {
        try {
            this.mService.setUserName(n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setUserName(String string2) {
        this.setUserName(this.getUserHandle(), string2);
    }

    @Deprecated
    public void setUserRestriction(String string2, boolean bl) {
        this.setUserRestriction(string2, bl, Process.myUserHandle());
    }

    @Deprecated
    public void setUserRestriction(String string2, boolean bl, UserHandle userHandle) {
        try {
            this.mService.setUserRestriction(string2, bl, userHandle.getIdentifier());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    public void setUserRestrictions(Bundle bundle) {
        throw new UnsupportedOperationException("This method is no longer supported");
    }

    @Deprecated
    public void setUserRestrictions(Bundle bundle, UserHandle userHandle) {
        throw new UnsupportedOperationException("This method is no longer supported");
    }

    public boolean someUserHasSeedAccount(String string2, String string3) {
        try {
            boolean bl = this.mService.someUserHasSeedAccount(string2, string3);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public static final class EnforcingUser
    implements Parcelable {
        public static final Parcelable.Creator<EnforcingUser> CREATOR = new Parcelable.Creator<EnforcingUser>(){

            @Override
            public EnforcingUser createFromParcel(Parcel parcel) {
                return new EnforcingUser(parcel);
            }

            public EnforcingUser[] newArray(int n) {
                return new EnforcingUser[n];
            }
        };
        private final int userId;
        private final int userRestrictionSource;

        public EnforcingUser(int n, int n2) {
            this.userId = n;
            this.userRestrictionSource = n2;
        }

        private EnforcingUser(Parcel parcel) {
            this.userId = parcel.readInt();
            this.userRestrictionSource = parcel.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public UserHandle getUserHandle() {
            return UserHandle.of(this.userId);
        }

        public int getUserRestrictionSource() {
            return this.userRestrictionSource;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.userId);
            parcel.writeInt(this.userRestrictionSource);
        }

    }

    public static class UserOperationException
    extends RuntimeException {
        private final int mUserOperationResult;

        public UserOperationException(String string2, int n) {
            super(string2);
            this.mUserOperationResult = n;
        }

        public int getUserOperationResult() {
            return this.mUserOperationResult;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UserOperationResult {
    }

    @SystemApi
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UserRestrictionSource {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UserSwitchabilityResult {
    }

}

