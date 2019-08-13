/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.admin.-$
 *  android.app.admin.-$$Lambda
 *  android.app.admin.-$$Lambda$pWaRScwKTZTgGW4Wa_v5R_pKBDU
 *  com.android.org.conscrypt.TrustedCertificateStore
 */
package android.app.admin;

import android.annotation.SuppressLint;
import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.IApplicationThread;
import android.app.IServiceConnection;
import android.app.admin.-$;
import android.app.admin.IDevicePolicyManager;
import android.app.admin.NetworkEvent;
import android.app.admin.PasswordMetrics;
import android.app.admin.SecurityLog;
import android.app.admin.StartInstallingUpdateCallback;
import android.app.admin.SystemUpdateInfo;
import android.app.admin.SystemUpdatePolicy;
import android.app.admin._$$Lambda$DevicePolicyManager$1$k6Rmp3Fg9FFATYRU5Z7rHDXGemA;
import android.app.admin._$$Lambda$DevicePolicyManager$aBAov4sAc4DWENs1_hCXh31NAg0;
import android.app.admin._$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk;
import android.app.admin._$$Lambda$pWaRScwKTZTgGW4Wa_v5R_pKBDU;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.IPackageDataObserver;
import android.content.pm.PackageManager;
import android.content.pm.ParceledListSlice;
import android.content.pm.StringParceledListSlice;
import android.content.pm.UserInfo;
import android.graphics.Bitmap;
import android.net.NetworkUtils;
import android.net.PrivateDnsConnectivityChecker;
import android.net.Proxy;
import android.net.ProxyInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import android.os.UserHandle;
import android.os.UserManager;
import android.security.AttestedKeyPair;
import android.security.Credentials;
import android.security.KeyChain;
import android.security.KeyChainException;
import android.security.keymaster.KeymasterCertificateChain;
import android.security.keystore.AttestationUtils;
import android.security.keystore.KeyAttestationException;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.ParcelableKeyGenParameterSpec;
import android.security.keystore.StrongBoxUnavailableException;
import android.telephony.data.ApnSetting;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.org.conscrypt.TrustedCertificateStore;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class DevicePolicyManager {
    @SystemApi
    public static final String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_ALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_ALLOWED";
    @SystemApi
    public static final String ACCOUNT_FEATURE_DEVICE_OR_PROFILE_OWNER_DISALLOWED = "android.account.DEVICE_OR_PROFILE_OWNER_DISALLOWED";
    public static final String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final String ACTION_ADMIN_POLICY_COMPLIANCE = "android.app.action.ADMIN_POLICY_COMPLIANCE";
    public static final String ACTION_APPLICATION_DELEGATION_SCOPES_CHANGED = "android.app.action.APPLICATION_DELEGATION_SCOPES_CHANGED";
    public static final String ACTION_BUGREPORT_SHARING_ACCEPTED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_ACCEPTED";
    public static final String ACTION_BUGREPORT_SHARING_DECLINED = "com.android.server.action.REMOTE_BUGREPORT_SHARING_DECLINED";
    public static final String ACTION_DATA_SHARING_RESTRICTION_APPLIED = "android.app.action.DATA_SHARING_RESTRICTION_APPLIED";
    public static final String ACTION_DATA_SHARING_RESTRICTION_CHANGED = "android.app.action.DATA_SHARING_RESTRICTION_CHANGED";
    public static final String ACTION_DEVICE_ADMIN_SERVICE = "android.app.action.DEVICE_ADMIN_SERVICE";
    public static final String ACTION_DEVICE_OWNER_CHANGED = "android.app.action.DEVICE_OWNER_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_DEVICE_POLICY_MANAGER_STATE_CHANGED = "android.app.action.DEVICE_POLICY_MANAGER_STATE_CHANGED";
    public static final String ACTION_GET_PROVISIONING_MODE = "android.app.action.GET_PROVISIONING_MODE";
    public static final String ACTION_MANAGED_PROFILE_PROVISIONED = "android.app.action.MANAGED_PROFILE_PROVISIONED";
    public static final String ACTION_MANAGED_USER_CREATED = "android.app.action.MANAGED_USER_CREATED";
    public static final String ACTION_PROFILE_OWNER_CHANGED = "android.app.action.PROFILE_OWNER_CHANGED";
    public static final String ACTION_PROVISIONING_SUCCESSFUL = "android.app.action.PROVISIONING_SUCCESSFUL";
    @SystemApi
    public static final String ACTION_PROVISION_FINALIZATION = "android.app.action.PROVISION_FINALIZATION";
    public static final String ACTION_PROVISION_MANAGED_DEVICE = "android.app.action.PROVISION_MANAGED_DEVICE";
    @SystemApi
    public static final String ACTION_PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE = "android.app.action.PROVISION_MANAGED_DEVICE_FROM_TRUSTED_SOURCE";
    public static final String ACTION_PROVISION_MANAGED_PROFILE = "android.app.action.PROVISION_MANAGED_PROFILE";
    public static final String ACTION_PROVISION_MANAGED_SHAREABLE_DEVICE = "android.app.action.PROVISION_MANAGED_SHAREABLE_DEVICE";
    public static final String ACTION_PROVISION_MANAGED_USER = "android.app.action.PROVISION_MANAGED_USER";
    public static final String ACTION_REMOTE_BUGREPORT_DISPATCH = "android.intent.action.REMOTE_BUGREPORT_DISPATCH";
    public static final String ACTION_SET_NEW_PARENT_PROFILE_PASSWORD = "android.app.action.SET_NEW_PARENT_PROFILE_PASSWORD";
    public static final String ACTION_SET_NEW_PASSWORD = "android.app.action.SET_NEW_PASSWORD";
    @SystemApi
    public static final String ACTION_SET_PROFILE_OWNER = "android.app.action.SET_PROFILE_OWNER";
    public static final String ACTION_SHOW_DEVICE_MONITORING_DIALOG = "android.app.action.SHOW_DEVICE_MONITORING_DIALOG";
    public static final String ACTION_START_ENCRYPTION = "android.app.action.START_ENCRYPTION";
    @SystemApi
    public static final String ACTION_STATE_USER_SETUP_COMPLETE = "android.app.action.STATE_USER_SETUP_COMPLETE";
    public static final String ACTION_SYSTEM_UPDATE_POLICY_CHANGED = "android.app.action.SYSTEM_UPDATE_POLICY_CHANGED";
    public static final int CODE_ACCOUNTS_NOT_EMPTY = 6;
    public static final int CODE_ADD_MANAGED_PROFILE_DISALLOWED = 15;
    public static final int CODE_CANNOT_ADD_MANAGED_PROFILE = 11;
    public static final int CODE_DEVICE_ADMIN_NOT_SUPPORTED = 13;
    public static final int CODE_HAS_DEVICE_OWNER = 1;
    public static final int CODE_HAS_PAIRED = 8;
    public static final int CODE_MANAGED_USERS_NOT_SUPPORTED = 9;
    public static final int CODE_NONSYSTEM_USER_EXISTS = 5;
    public static final int CODE_NOT_SYSTEM_USER = 7;
    public static final int CODE_NOT_SYSTEM_USER_SPLIT = 12;
    public static final int CODE_OK = 0;
    public static final int CODE_SPLIT_SYSTEM_USER_DEVICE_SYSTEM_USER = 14;
    public static final int CODE_SYSTEM_USER = 10;
    public static final int CODE_USER_HAS_PROFILE_OWNER = 2;
    public static final int CODE_USER_NOT_RUNNING = 3;
    public static final int CODE_USER_SETUP_COMPLETED = 4;
    public static final long DEFAULT_STRONG_AUTH_TIMEOUT_MS = 259200000L;
    public static final String DELEGATION_APP_RESTRICTIONS = "delegation-app-restrictions";
    public static final String DELEGATION_BLOCK_UNINSTALL = "delegation-block-uninstall";
    public static final String DELEGATION_CERT_INSTALL = "delegation-cert-install";
    public static final String DELEGATION_CERT_SELECTION = "delegation-cert-selection";
    public static final String DELEGATION_ENABLE_SYSTEM_APP = "delegation-enable-system-app";
    public static final String DELEGATION_INSTALL_EXISTING_PACKAGE = "delegation-install-existing-package";
    public static final String DELEGATION_KEEP_UNINSTALLED_PACKAGES = "delegation-keep-uninstalled-packages";
    public static final String DELEGATION_NETWORK_LOGGING = "delegation-network-logging";
    public static final String DELEGATION_PACKAGE_ACCESS = "delegation-package-access";
    public static final String DELEGATION_PERMISSION_GRANT = "delegation-permission-grant";
    public static final int ENCRYPTION_STATUS_ACTIVATING = 2;
    public static final int ENCRYPTION_STATUS_ACTIVE = 3;
    public static final int ENCRYPTION_STATUS_ACTIVE_DEFAULT_KEY = 4;
    public static final int ENCRYPTION_STATUS_ACTIVE_PER_USER = 5;
    public static final int ENCRYPTION_STATUS_INACTIVE = 1;
    public static final int ENCRYPTION_STATUS_UNSUPPORTED = 0;
    public static final int ERROR_VPN_PACKAGE_NOT_FOUND = 1;
    public static final String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final String EXTRA_BUGREPORT_NOTIFICATION_TYPE = "android.app.extra.bugreport_notification_type";
    public static final String EXTRA_DELEGATION_SCOPES = "android.app.extra.DELEGATION_SCOPES";
    public static final String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final String EXTRA_PASSWORD_COMPLEXITY = "android.app.extra.PASSWORD_COMPLEXITY";
    @SystemApi
    public static final String EXTRA_PROFILE_OWNER_NAME = "android.app.extra.PROFILE_OWNER_NAME";
    public static final String EXTRA_PROVISIONING_ACCOUNT_TO_MIGRATE = "android.app.extra.PROVISIONING_ACCOUNT_TO_MIGRATE";
    public static final String EXTRA_PROVISIONING_ADMIN_EXTRAS_BUNDLE = "android.app.extra.PROVISIONING_ADMIN_EXTRAS_BUNDLE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE = "android.app.extra.PROVISIONING_DEVICE_ADMIN_MINIMUM_VERSION_CODE";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_COOKIE_HEADER";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION";
    @SystemApi
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_ICON_URI";
    @SystemApi
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_LABEL";
    @Deprecated
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME = "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_NAME";
    public static final String EXTRA_PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM = "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM";
    public static final String EXTRA_PROVISIONING_DISCLAIMERS = "android.app.extra.PROVISIONING_DISCLAIMERS";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_CONTENT = "android.app.extra.PROVISIONING_DISCLAIMER_CONTENT";
    public static final String EXTRA_PROVISIONING_DISCLAIMER_HEADER = "android.app.extra.PROVISIONING_DISCLAIMER_HEADER";
    @Deprecated
    public static final String EXTRA_PROVISIONING_EMAIL_ADDRESS = "android.app.extra.PROVISIONING_EMAIL_ADDRESS";
    public static final String EXTRA_PROVISIONING_IMEI = "android.app.extra.PROVISIONING_IMEI";
    public static final String EXTRA_PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION = "android.app.extra.PROVISIONING_KEEP_ACCOUNT_ON_MIGRATION";
    public static final String EXTRA_PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED = "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED";
    public static final String EXTRA_PROVISIONING_LOCALE = "android.app.extra.PROVISIONING_LOCALE";
    public static final String EXTRA_PROVISIONING_LOCAL_TIME = "android.app.extra.PROVISIONING_LOCAL_TIME";
    public static final String EXTRA_PROVISIONING_LOGO_URI = "android.app.extra.PROVISIONING_LOGO_URI";
    public static final String EXTRA_PROVISIONING_MAIN_COLOR = "android.app.extra.PROVISIONING_MAIN_COLOR";
    public static final String EXTRA_PROVISIONING_MODE = "android.app.extra.PROVISIONING_MODE";
    @SystemApi
    public static final String EXTRA_PROVISIONING_ORGANIZATION_NAME = "android.app.extra.PROVISIONING_ORGANIZATION_NAME";
    public static final String EXTRA_PROVISIONING_SERIAL_NUMBER = "android.app.extra.PROVISIONING_SERIAL_NUMBER";
    public static final String EXTRA_PROVISIONING_SKIP_EDUCATION_SCREENS = "android.app.extra.PROVISIONING_SKIP_EDUCATION_SCREENS";
    public static final String EXTRA_PROVISIONING_SKIP_ENCRYPTION = "android.app.extra.PROVISIONING_SKIP_ENCRYPTION";
    public static final String EXTRA_PROVISIONING_SKIP_USER_CONSENT = "android.app.extra.PROVISIONING_SKIP_USER_CONSENT";
    public static final String EXTRA_PROVISIONING_SKIP_USER_SETUP = "android.app.extra.PROVISIONING_SKIP_USER_SETUP";
    @SystemApi
    public static final String EXTRA_PROVISIONING_SUPPORT_URL = "android.app.extra.PROVISIONING_SUPPORT_URL";
    public static final String EXTRA_PROVISIONING_TIME_ZONE = "android.app.extra.PROVISIONING_TIME_ZONE";
    @SystemApi
    public static final String EXTRA_PROVISIONING_TRIGGER = "android.app.extra.PROVISIONING_TRIGGER";
    public static final String EXTRA_PROVISIONING_USE_MOBILE_DATA = "android.app.extra.PROVISIONING_USE_MOBILE_DATA";
    public static final String EXTRA_PROVISIONING_WIFI_ANONYMOUS_IDENTITY = "android.app.extra.PROVISIONING_WIFI_ANONYMOUS_IDENTITY";
    public static final String EXTRA_PROVISIONING_WIFI_CA_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_CA_CERTIFICATE";
    public static final String EXTRA_PROVISIONING_WIFI_DOMAIN = "android.app.extra.PROVISIONING_WIFI_DOMAIN";
    public static final String EXTRA_PROVISIONING_WIFI_EAP_METHOD = "android.app.extra.PROVISIONING_WIFI_EAP_METHOD";
    public static final String EXTRA_PROVISIONING_WIFI_HIDDEN = "android.app.extra.PROVISIONING_WIFI_HIDDEN";
    public static final String EXTRA_PROVISIONING_WIFI_IDENTITY = "android.app.extra.PROVISIONING_WIFI_IDENTITY";
    public static final String EXTRA_PROVISIONING_WIFI_PAC_URL = "android.app.extra.PROVISIONING_WIFI_PAC_URL";
    public static final String EXTRA_PROVISIONING_WIFI_PASSWORD = "android.app.extra.PROVISIONING_WIFI_PASSWORD";
    public static final String EXTRA_PROVISIONING_WIFI_PHASE2_AUTH = "android.app.extra.PROVISIONING_WIFI_PHASE2_AUTH";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_BYPASS = "android.app.extra.PROVISIONING_WIFI_PROXY_BYPASS";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_HOST = "android.app.extra.PROVISIONING_WIFI_PROXY_HOST";
    public static final String EXTRA_PROVISIONING_WIFI_PROXY_PORT = "android.app.extra.PROVISIONING_WIFI_PROXY_PORT";
    public static final String EXTRA_PROVISIONING_WIFI_SECURITY_TYPE = "android.app.extra.PROVISIONING_WIFI_SECURITY_TYPE";
    public static final String EXTRA_PROVISIONING_WIFI_SSID = "android.app.extra.PROVISIONING_WIFI_SSID";
    public static final String EXTRA_PROVISIONING_WIFI_USER_CERTIFICATE = "android.app.extra.PROVISIONING_WIFI_USER_CERTIFICATE";
    public static final String EXTRA_REMOTE_BUGREPORT_HASH = "android.intent.extra.REMOTE_BUGREPORT_HASH";
    @SystemApi
    public static final String EXTRA_RESTRICTION = "android.app.extra.RESTRICTION";
    public static final int FLAG_EVICT_CREDENTIAL_ENCRYPTION_KEY = 1;
    public static final int FLAG_MANAGED_CAN_ACCESS_PARENT = 2;
    public static final int FLAG_PARENT_CAN_ACCESS_MANAGED = 1;
    public static final int ID_TYPE_BASE_INFO = 1;
    public static final int ID_TYPE_IMEI = 4;
    public static final int ID_TYPE_MEID = 8;
    public static final int ID_TYPE_SERIAL = 2;
    public static final int INSTALLKEY_REQUEST_CREDENTIALS_ACCESS = 1;
    public static final int INSTALLKEY_SET_USER_SELECTABLE = 2;
    public static final int KEYGUARD_DISABLE_BIOMETRICS = 416;
    public static final int KEYGUARD_DISABLE_FACE = 128;
    public static final int KEYGUARD_DISABLE_FEATURES_ALL = Integer.MAX_VALUE;
    public static final int KEYGUARD_DISABLE_FEATURES_NONE = 0;
    public static final int KEYGUARD_DISABLE_FINGERPRINT = 32;
    public static final int KEYGUARD_DISABLE_IRIS = 256;
    public static final int KEYGUARD_DISABLE_REMOTE_INPUT = 64;
    public static final int KEYGUARD_DISABLE_SECURE_CAMERA = 2;
    public static final int KEYGUARD_DISABLE_SECURE_NOTIFICATIONS = 4;
    public static final int KEYGUARD_DISABLE_TRUST_AGENTS = 16;
    public static final int KEYGUARD_DISABLE_UNREDACTED_NOTIFICATIONS = 8;
    public static final int KEYGUARD_DISABLE_WIDGETS_ALL = 1;
    public static final int KEY_GEN_STRONGBOX_UNAVAILABLE = 1;
    public static final int LEAVE_ALL_SYSTEM_APPS_ENABLED = 16;
    public static final int LOCK_TASK_FEATURE_GLOBAL_ACTIONS = 16;
    public static final int LOCK_TASK_FEATURE_HOME = 4;
    public static final int LOCK_TASK_FEATURE_KEYGUARD = 32;
    public static final int LOCK_TASK_FEATURE_NONE = 0;
    public static final int LOCK_TASK_FEATURE_NOTIFICATIONS = 2;
    public static final int LOCK_TASK_FEATURE_OVERVIEW = 8;
    public static final int LOCK_TASK_FEATURE_SYSTEM_INFO = 1;
    public static final int MAKE_USER_DEMO = 4;
    public static final int MAKE_USER_EPHEMERAL = 2;
    public static final String MIME_TYPE_PROVISIONING_NFC = "application/com.android.managedprovisioning";
    public static final int NOTIFICATION_BUGREPORT_ACCEPTED_NOT_FINISHED = 2;
    public static final int NOTIFICATION_BUGREPORT_FINISHED_NOT_ACCEPTED = 3;
    public static final int NOTIFICATION_BUGREPORT_STARTED = 1;
    public static final int PASSWORD_COMPLEXITY_HIGH = 327680;
    public static final int PASSWORD_COMPLEXITY_LOW = 65536;
    public static final int PASSWORD_COMPLEXITY_MEDIUM = 196608;
    public static final int PASSWORD_COMPLEXITY_NONE = 0;
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_BIOMETRIC_WEAK = 32768;
    public static final int PASSWORD_QUALITY_COMPLEX = 393216;
    public static final int PASSWORD_QUALITY_MANAGED = 524288;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_NUMERIC_COMPLEX = 196608;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final int PERMISSION_GRANT_STATE_DEFAULT = 0;
    public static final int PERMISSION_GRANT_STATE_DENIED = 2;
    public static final int PERMISSION_GRANT_STATE_GRANTED = 1;
    public static final int PERMISSION_POLICY_AUTO_DENY = 2;
    public static final int PERMISSION_POLICY_AUTO_GRANT = 1;
    public static final int PERMISSION_POLICY_PROMPT = 0;
    public static final String POLICY_DISABLE_CAMERA = "policy_disable_camera";
    public static final String POLICY_DISABLE_SCREEN_CAPTURE = "policy_disable_screen_capture";
    public static final String POLICY_SUSPEND_PACKAGES = "policy_suspend_packages";
    public static final int PRIVATE_DNS_MODE_OFF = 1;
    public static final int PRIVATE_DNS_MODE_OPPORTUNISTIC = 2;
    public static final int PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = 3;
    public static final int PRIVATE_DNS_MODE_UNKNOWN = 0;
    public static final int PRIVATE_DNS_SET_ERROR_FAILURE_SETTING = 2;
    public static final int PRIVATE_DNS_SET_ERROR_HOST_NOT_SERVING = 1;
    public static final int PRIVATE_DNS_SET_NO_ERROR = 0;
    public static final int PROFILE_KEYGUARD_FEATURES_AFFECT_OWNER = 432;
    public static final int PROVISIONING_MODE_FULLY_MANAGED_DEVICE = 1;
    public static final int PROVISIONING_MODE_MANAGED_PROFILE = 2;
    @SystemApi
    public static final int PROVISIONING_TRIGGER_CLOUD_ENROLLMENT = 1;
    @SystemApi
    public static final int PROVISIONING_TRIGGER_PERSISTENT_DEVICE_OWNER = 3;
    @SystemApi
    public static final int PROVISIONING_TRIGGER_QR_CODE = 2;
    @SystemApi
    public static final int PROVISIONING_TRIGGER_UNSPECIFIED = 0;
    public static final int RESET_PASSWORD_DO_NOT_ASK_CREDENTIALS_ON_BOOT = 2;
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;
    public static final int SKIP_SETUP_WIZARD = 1;
    @SystemApi
    public static final int STATE_USER_PROFILE_COMPLETE = 4;
    @SystemApi
    public static final int STATE_USER_SETUP_COMPLETE = 2;
    @SystemApi
    public static final int STATE_USER_SETUP_FINALIZED = 3;
    @SystemApi
    public static final int STATE_USER_SETUP_INCOMPLETE = 1;
    @SystemApi
    public static final int STATE_USER_UNMANAGED = 0;
    private static String TAG = "DevicePolicyManager";
    public static final int WIPE_EUICC = 4;
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static final int WIPE_RESET_PROTECTION_DATA = 2;
    public static final int WIPE_SILENTLY = 8;
    private final Context mContext;
    private final boolean mParentInstance;
    private final IDevicePolicyManager mService;

    public DevicePolicyManager(Context context, IDevicePolicyManager iDevicePolicyManager) {
        this(context, iDevicePolicyManager, false);
    }

    @VisibleForTesting
    protected DevicePolicyManager(Context context, IDevicePolicyManager iDevicePolicyManager, boolean bl) {
        this.mContext = context;
        this.mService = iDevicePolicyManager;
        this.mParentInstance = bl;
    }

    private void executeCallback(int n, String string2, Executor executor, InstallSystemUpdateCallback installSystemUpdateCallback) {
        executor.execute(new _$$Lambda$DevicePolicyManager$aBAov4sAc4DWENs1_hCXh31NAg0(installSystemUpdateCallback, n, string2));
    }

    private static String getCaCertAlias(byte[] object) throws CertificateException {
        object = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream((byte[])object));
        return new TrustedCertificateStore().getCertificateAlias((Certificate)object);
    }

    private ComponentName getDeviceOwnerComponentInner(boolean bl) {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getDeviceOwnerComponent(bl);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    private boolean isDeviceOwnerAppOnAnyUserInner(String string2, boolean bl) {
        if (string2 == null) {
            return false;
        }
        ComponentName componentName = this.getDeviceOwnerComponentInner(bl);
        if (componentName == null) {
            return false;
        }
        return string2.equals(componentName.getPackageName());
    }

    static /* synthetic */ void lambda$executeCallback$1(InstallSystemUpdateCallback installSystemUpdateCallback, int n, String string2) {
        installSystemUpdateCallback.onInstallUpdateError(n, string2);
    }

    static /* synthetic */ void lambda$setPermissionGrantState$0(CompletableFuture completableFuture, Bundle bundle) {
        boolean bl = bundle != null;
        completableFuture.complete(bl);
    }

    @UnsupportedAppUsage
    private void throwIfParentInstance(String string2) {
        if (!this.mParentInstance) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" cannot be called on the parent instance");
        throw new SecurityException(stringBuilder.toString());
    }

    private void wipeDataInternal(int n, String string2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.wipeDataWithReason(n, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void addCrossProfileIntentFilter(ComponentName componentName, IntentFilter intentFilter, int n) {
        this.throwIfParentInstance("addCrossProfileIntentFilter");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.addCrossProfileIntentFilter(componentName, intentFilter, n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean addCrossProfileWidgetProvider(ComponentName componentName, String string2) {
        this.throwIfParentInstance("addCrossProfileWidgetProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.addCrossProfileWidgetProvider(componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int addOverrideApn(ComponentName componentName, ApnSetting apnSetting) {
        this.throwIfParentInstance("addOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.addOverrideApn(componentName, apnSetting);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public void addPersistentPreferredActivity(ComponentName componentName, IntentFilter intentFilter, ComponentName componentName2) {
        this.throwIfParentInstance("addPersistentPreferredActivity");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.addPersistentPreferredActivity(componentName, intentFilter, componentName2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void addUserRestriction(ComponentName componentName, String string2) {
        this.throwIfParentInstance("addUserRestriction");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestriction(componentName, string2, true);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean approveCaCert(String string2, int n, boolean bl) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                bl = iDevicePolicyManager.approveCaCert(string2, n, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean bindDeviceAdminServiceAsUser(ComponentName componentName, Intent intent, ServiceConnection object, int n, UserHandle userHandle) {
        this.throwIfParentInstance("bindDeviceAdminServiceAsUser");
        try {
            object = this.mContext.getServiceDispatcher((ServiceConnection)object, this.mContext.getMainThreadHandler(), n);
            intent.prepareToLeaveProcess(this.mContext);
            boolean bl = this.mService.bindDeviceAdminServiceAsUser(componentName, this.mContext.getIApplicationThread(), this.mContext.getActivityToken(), intent, (IServiceConnection)object, n, userHandle.getIdentifier());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean checkDeviceIdentifierAccess(String string2, int n, int n2) {
        this.throwIfParentInstance("checkDeviceIdentifierAccess");
        if (string2 == null) {
            return false;
        }
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.checkDeviceIdentifierAccess(string2, n, n2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int checkProvisioningPreCondition(String string2, String string3) {
        try {
            int n = this.mService.checkProvisioningPreCondition(string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void clearApplicationUserData(ComponentName componentName, String string2, final Executor executor, final OnClearApplicationUserDataListener onClearApplicationUserDataListener) {
        this.throwIfParentInstance("clearAppData");
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(onClearApplicationUserDataListener);
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            IPackageDataObserver.Stub stub = new IPackageDataObserver.Stub(){

                static /* synthetic */ void lambda$onRemoveCompleted$0(OnClearApplicationUserDataListener onClearApplicationUserDataListener2, String string2, boolean bl) {
                    onClearApplicationUserDataListener2.onApplicationUserDataCleared(string2, bl);
                }

                @Override
                public void onRemoveCompleted(String string2, boolean bl) {
                    executor.execute(new _$$Lambda$DevicePolicyManager$1$k6Rmp3Fg9FFATYRU5Z7rHDXGemA(onClearApplicationUserDataListener, string2, bl));
                }
            };
            iDevicePolicyManager.clearApplicationUserData(componentName, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void clearCrossProfileIntentFilters(ComponentName componentName) {
        this.throwIfParentInstance("clearCrossProfileIntentFilters");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearCrossProfileIntentFilters(componentName);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void clearDeviceOwnerApp(String string2) {
        this.throwIfParentInstance("clearDeviceOwnerApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearDeviceOwner(string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void clearPackagePersistentPreferredActivities(ComponentName componentName, String string2) {
        this.throwIfParentInstance("clearPackagePersistentPreferredActivities");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearPackagePersistentPreferredActivities(componentName, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void clearProfileOwner(ComponentName componentName) {
        this.throwIfParentInstance("clearProfileOwner");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.clearProfileOwner(componentName);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean clearResetPasswordToken(ComponentName componentName) {
        this.throwIfParentInstance("clearResetPasswordToken");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.clearResetPasswordToken(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void clearSystemUpdatePolicyFreezePeriodRecord() {
        this.throwIfParentInstance("clearSystemUpdatePolicyFreezePeriodRecord");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.clearSystemUpdatePolicyFreezePeriodRecord();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void clearUserRestriction(ComponentName componentName, String string2) {
        this.throwIfParentInstance("clearUserRestriction");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserRestriction(componentName, string2, false);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public Intent createAdminSupportIntent(String object) {
        this.throwIfParentInstance("createAdminSupportIntent");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.createAdminSupportIntent((String)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @Deprecated
    public UserHandle createAndInitializeUser(ComponentName componentName, String string2, String string3, ComponentName componentName2, Bundle bundle) {
        return null;
    }

    public UserHandle createAndManageUser(ComponentName parcelable, String string2, ComponentName componentName, PersistableBundle persistableBundle, int n) {
        this.throwIfParentInstance("createAndManageUser");
        try {
            parcelable = this.mService.createAndManageUser((ComponentName)parcelable, string2, componentName, persistableBundle, n);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw new UserManager.UserOperationException(serviceSpecificException.getMessage(), serviceSpecificException.errorCode);
        }
    }

    @Deprecated
    public UserHandle createUser(ComponentName componentName, String string2) {
        return null;
    }

    public int enableSystemApp(ComponentName componentName, Intent intent) {
        this.throwIfParentInstance("enableSystemApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.enableSystemAppWithIntent(componentName, this.mContext.getPackageName(), intent);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public void enableSystemApp(ComponentName componentName, String string2) {
        this.throwIfParentInstance("enableSystemApp");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.enableSystemApp(componentName, this.mContext.getPackageName(), string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public long forceNetworkLogs() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return -1L;
        }
        try {
            long l = iDevicePolicyManager.forceNetworkLogs();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void forceRemoveActiveAdmin(ComponentName componentName, int n) {
        try {
            this.mService.forceRemoveActiveAdmin(componentName, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long forceSecurityLogs() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0L;
        }
        try {
            long l = iDevicePolicyManager.forceSecurityLogs();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void forceUpdateUserSetupComplete() {
        try {
            this.mService.forceUpdateUserSetupComplete();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public AttestedKeyPair generateKeyPair(ComponentName componentName, String object, KeyGenParameterSpec object2, int n) {
        this.throwIfParentInstance("generateKeyPair");
        try {
            Object object3 = new ParcelableKeyGenParameterSpec((KeyGenParameterSpec)object2);
            KeymasterCertificateChain keymasterCertificateChain = new KeymasterCertificateChain();
            if (!this.mService.generateKeyPair(componentName, this.mContext.getPackageName(), (String)object, (ParcelableKeyGenParameterSpec)object3, n, keymasterCertificateChain)) {
                Log.e(TAG, "Error generating key via DevicePolicyManagerService.");
                return null;
            }
            object2 = ((KeyGenParameterSpec)object2).getKeystoreAlias();
            object3 = KeyChain.getKeyPair(this.mContext, (String)object2);
            object = null;
            try {
                if (!AttestationUtils.isChainValid(keymasterCertificateChain)) return new AttestedKeyPair((KeyPair)object3, (Certificate[])object);
                object = AttestationUtils.parseCertificateChain(keymasterCertificateChain);
                return new AttestedKeyPair((KeyPair)object3, (Certificate[])object);
            }
            catch (KeyAttestationException keyAttestationException) {
                object3 = TAG;
                object = new StringBuilder();
                ((StringBuilder)object).append("Error parsing attestation chain for alias ");
                ((StringBuilder)object).append((String)object2);
                Log.e((String)object3, ((StringBuilder)object).toString(), keyAttestationException);
                this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), (String)object2);
                return null;
            }
        }
        catch (ServiceSpecificException serviceSpecificException) {
            Log.w(TAG, String.format("Key Generation failure: %d", serviceSpecificException.errorCode));
            if (serviceSpecificException.errorCode == 1) throw new StrongBoxUnavailableException("No StrongBox for key generation.");
            throw new RuntimeException(String.format("Unknown error while generating key: %d", serviceSpecificException.errorCode));
        }
        catch (InterruptedException interruptedException) {
            Log.w(TAG, "Interrupted while generating key", interruptedException);
            Thread.currentThread().interrupt();
            return null;
        }
        catch (KeyChainException keyChainException) {
            Log.w(TAG, "Failed to generate key", keyChainException);
        }
        return null;
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String[] getAccountTypesWithManagementDisabled() {
        this.throwIfParentInstance("getAccountTypesWithManagementDisabled");
        return this.getAccountTypesWithManagementDisabledAsUser(this.myUserId());
    }

    public String[] getAccountTypesWithManagementDisabledAsUser(int n) {
        String[] arrstring = this.mService;
        if (arrstring != null) {
            try {
                arrstring = arrstring.getAccountTypesWithManagementDisabledAsUser(n);
                return arrstring;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<ComponentName> getActiveAdmins() {
        this.throwIfParentInstance("getActiveAdmins");
        return this.getActiveAdminsAsUser(this.myUserId());
    }

    @UnsupportedAppUsage
    public List<ComponentName> getActiveAdminsAsUser(int n) {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getActiveAdmins(n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public Set<String> getAffiliationIds(ComponentName object) {
        this.throwIfParentInstance("getAffiliationIds");
        try {
            object = new ArraySet<String>(this.mService.getAffiliationIds((ComponentName)object));
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public Set<String> getAlwaysOnVpnLockdownWhitelist(ComponentName hashSet) {
        this.throwIfParentInstance("getAlwaysOnVpnLockdownWhitelist");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        Object var3_4 = null;
        if (iDevicePolicyManager != null) {
            block4 : {
                try {
                    hashSet = iDevicePolicyManager.getAlwaysOnVpnLockdownWhitelist((ComponentName)((Object)hashSet));
                    if (hashSet != null) break block4;
                    hashSet = var3_4;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            hashSet = new HashSet(hashSet);
            return hashSet;
        }
        return null;
    }

    public String getAlwaysOnVpnPackage(ComponentName object) {
        this.throwIfParentInstance("getAlwaysOnVpnPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getAlwaysOnVpnPackage((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public Bundle getApplicationRestrictions(ComponentName parcelable, String string2) {
        this.throwIfParentInstance("getApplicationRestrictions");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                parcelable = iDevicePolicyManager.getApplicationRestrictions((ComponentName)parcelable, this.mContext.getPackageName(), string2);
                return parcelable;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @Deprecated
    public String getApplicationRestrictionsManagingPackage(ComponentName object) {
        this.throwIfParentInstance("getApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getApplicationRestrictionsManagingPackage((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean getAutoTimeRequired() {
        this.throwIfParentInstance("getAutoTimeRequired");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getAutoTimeRequired();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public List<UserHandle> getBindDeviceAdminTargetUsers(ComponentName object) {
        this.throwIfParentInstance("getBindDeviceAdminTargetUsers");
        try {
            object = this.mService.getBindDeviceAdminTargetUsers((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getBluetoothContactSharingDisabled(ComponentName componentName) {
        this.throwIfParentInstance("getBluetoothContactSharingDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getBluetoothContactSharingDisabled(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean getBluetoothContactSharingDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getBluetoothContactSharingDisabledForUser(userHandle.getIdentifier());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean getCameraDisabled(ComponentName componentName) {
        this.throwIfParentInstance("getCameraDisabled");
        return this.getCameraDisabled(componentName, this.myUserId());
    }

    @UnsupportedAppUsage
    public boolean getCameraDisabled(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getCameraDisabled(componentName, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Deprecated
    public String getCertInstallerPackage(ComponentName object) throws SecurityException {
        this.throwIfParentInstance("getCertInstallerPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getCertInstallerPackage((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public Set<String> getCrossProfileCalendarPackages() {
        this.throwIfParentInstance("getCrossProfileCalendarPackages");
        ArraySet arraySet = this.mService;
        if (arraySet != null) {
            block4 : {
                try {
                    arraySet = arraySet.getCrossProfileCalendarPackagesForUser(this.myUserId());
                    if (arraySet != null) break block4;
                    arraySet = null;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            arraySet = new ArraySet(arraySet);
            return arraySet;
        }
        return Collections.emptySet();
    }

    public Set<String> getCrossProfileCalendarPackages(ComponentName arraySet) {
        this.throwIfParentInstance("getCrossProfileCalendarPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            block4 : {
                try {
                    arraySet = iDevicePolicyManager.getCrossProfileCalendarPackages((ComponentName)((Object)arraySet));
                    if (arraySet != null) break block4;
                    arraySet = null;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            arraySet = new ArraySet(arraySet);
            return arraySet;
        }
        return Collections.emptySet();
    }

    public boolean getCrossProfileCallerIdDisabled(ComponentName componentName) {
        this.throwIfParentInstance("getCrossProfileCallerIdDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getCrossProfileCallerIdDisabled(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean getCrossProfileCallerIdDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getCrossProfileCallerIdDisabledForUser(userHandle.getIdentifier());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean getCrossProfileContactsSearchDisabled(ComponentName componentName) {
        this.throwIfParentInstance("getCrossProfileContactsSearchDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getCrossProfileContactsSearchDisabled(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean getCrossProfileContactsSearchDisabled(UserHandle userHandle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getCrossProfileContactsSearchDisabledForUser(userHandle.getIdentifier());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public List<String> getCrossProfileWidgetProviders(ComponentName object) {
        this.throwIfParentInstance("getCrossProfileWidgetProviders");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getCrossProfileWidgetProviders((ComponentName)object);
                if (object != null) {
                    return object;
                }
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return Collections.emptyList();
    }

    public int getCurrentFailedPasswordAttempts() {
        return this.getCurrentFailedPasswordAttempts(this.myUserId());
    }

    @UnsupportedAppUsage
    public int getCurrentFailedPasswordAttempts(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getCurrentFailedPasswordAttempts(n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return -1;
    }

    public List<String> getDelegatePackages(ComponentName object, String string2) {
        this.throwIfParentInstance("getDelegatePackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getDelegatePackages((ComponentName)object, string2);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<String> getDelegatedScopes(ComponentName object, String string2) {
        this.throwIfParentInstance("getDelegatedScopes");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getDelegatedScopes((ComponentName)object, string2);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public String getDeviceInitializerApp() {
        return null;
    }

    @SystemApi
    @Deprecated
    @SuppressLint(value={"Doclava125"})
    public ComponentName getDeviceInitializerComponent() {
        return null;
    }

    @SystemApi
    public String getDeviceOwner() {
        this.throwIfParentInstance("getDeviceOwner");
        Object object = this.getDeviceOwnerComponentOnCallingUser();
        object = object != null ? ((ComponentName)object).getPackageName() : null;
        return object;
    }

    @SystemApi
    public ComponentName getDeviceOwnerComponentOnAnyUser() {
        return this.getDeviceOwnerComponentInner(false);
    }

    public ComponentName getDeviceOwnerComponentOnCallingUser() {
        return this.getDeviceOwnerComponentInner(true);
    }

    public CharSequence getDeviceOwnerLockScreenInfo() {
        this.throwIfParentInstance("getDeviceOwnerLockScreenInfo");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getDeviceOwnerLockScreenInfo();
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    public String getDeviceOwnerNameOnAnyUser() {
        this.throwIfParentInstance("getDeviceOwnerNameOnAnyUser");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getDeviceOwnerName();
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public CharSequence getDeviceOwnerOrganizationName() {
        try {
            CharSequence charSequence = this.mService.getDeviceOwnerOrganizationName();
            return charSequence;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public UserHandle getDeviceOwnerUser() {
        block4 : {
            Object object = this.mService;
            if (object != null) {
                int n;
                try {
                    n = object.getDeviceOwnerUserId();
                    if (n == -10000) break block4;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                object = UserHandle.of(n);
                return object;
            }
        }
        return null;
    }

    public int getDeviceOwnerUserId() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.getDeviceOwnerUserId();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return -10000;
    }

    public Set<String> getDisallowedSystemApps(ComponentName object, int n, String string2) {
        try {
            object = new ArraySet<String>(this.mService.getDisallowedSystemApps((ComponentName)object, n, string2));
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getDoNotAskCredentialsOnBoot() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getDoNotAskCredentialsOnBoot();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public CharSequence getEndUserSessionMessage(ComponentName object) {
        this.throwIfParentInstance("getEndUserSessionMessage");
        try {
            object = this.mService.getEndUserSessionMessage((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getForceEphemeralUsers(ComponentName componentName) {
        this.throwIfParentInstance("getForceEphemeralUsers");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getForceEphemeralUsers(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public String getGlobalPrivateDnsHost(ComponentName object) {
        this.throwIfParentInstance("setGlobalPrivateDns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return null;
        }
        try {
            object = iDevicePolicyManager.getGlobalPrivateDnsHost((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getGlobalPrivateDnsMode(ComponentName componentName) {
        this.throwIfParentInstance("setGlobalPrivateDns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            int n = iDevicePolicyManager.getGlobalPrivateDnsMode(componentName);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ComponentName getGlobalProxyAdmin() {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getGlobalProxyAdmin(this.myUserId());
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public boolean getGuestUserDisabled(ComponentName componentName) {
        return false;
    }

    public List<byte[]> getInstalledCaCerts(ComponentName object) {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        this.throwIfParentInstance("getInstalledCaCerts");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            iDevicePolicyManager.enforceCanManageCaCerts((ComponentName)object, this.mContext.getPackageName());
            iDevicePolicyManager = new TrustedCertificateStore();
            for (String string2 : iDevicePolicyManager.userAliases()) {
                try {
                    arrayList.add(iDevicePolicyManager.getCertificate(string2).getEncoded());
                }
                catch (CertificateException certificateException) {
                    try {
                        String string3 = TAG;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not encode certificate: ");
                        stringBuilder.append(string2);
                        Log.w(string3, stringBuilder.toString(), certificateException);
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
        }
        return arrayList;
    }

    public List<String> getKeepUninstalledPackages(ComponentName object) {
        this.throwIfParentInstance("getKeepUninstalledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getKeepUninstalledPackages((ComponentName)object, this.mContext.getPackageName());
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int getKeyguardDisabledFeatures(ComponentName componentName) {
        return this.getKeyguardDisabledFeatures(componentName, this.myUserId());
    }

    @UnsupportedAppUsage
    public int getKeyguardDisabledFeatures(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getKeyguardDisabledFeatures(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public long getLastBugReportRequestTime() {
        try {
            long l = this.mService.getLastBugReportRequestTime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getLastNetworkLogRetrievalTime() {
        try {
            long l = this.mService.getLastNetworkLogRetrievalTime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getLastSecurityLogRetrievalTime() {
        try {
            long l = this.mService.getLastSecurityLogRetrievalTime();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getLockTaskFeatures(ComponentName componentName) {
        this.throwIfParentInstance("getLockTaskFeatures");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.getLockTaskFeatures(componentName);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public String[] getLockTaskPackages(ComponentName arrstring) {
        this.throwIfParentInstance("getLockTaskPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                arrstring = iDevicePolicyManager.getLockTaskPackages((ComponentName)arrstring);
                return arrstring;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return new String[0];
    }

    public CharSequence getLongSupportMessage(ComponentName object) {
        this.throwIfParentInstance("getLongSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getLongSupportMessage((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public CharSequence getLongSupportMessageForUser(ComponentName object, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getLongSupportMessageForUser((ComponentName)object, n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int getMaximumFailedPasswordsForWipe(ComponentName componentName) {
        return this.getMaximumFailedPasswordsForWipe(componentName, this.myUserId());
    }

    @UnsupportedAppUsage
    public int getMaximumFailedPasswordsForWipe(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getMaximumFailedPasswordsForWipe(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public long getMaximumTimeToLock(ComponentName componentName) {
        return this.getMaximumTimeToLock(componentName, this.myUserId());
    }

    @UnsupportedAppUsage
    public long getMaximumTimeToLock(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                long l = iDevicePolicyManager.getMaximumTimeToLock(componentName, n, this.mParentInstance);
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public List<String> getMeteredDataDisabledPackages(ComponentName object) {
        this.throwIfParentInstance("getMeteredDataDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getMeteredDataDisabledPackages((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return new ArrayList<String>();
    }

    public int getOrganizationColor(ComponentName componentName) {
        this.throwIfParentInstance("getOrganizationColor");
        try {
            int n = this.mService.getOrganizationColor(componentName);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getOrganizationColorForUser(int n) {
        try {
            n = this.mService.getOrganizationColorForUser(n);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public CharSequence getOrganizationName(ComponentName object) {
        this.throwIfParentInstance("getOrganizationName");
        try {
            object = this.mService.getOrganizationName((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public CharSequence getOrganizationNameForUser(int n) {
        try {
            CharSequence charSequence = this.mService.getOrganizationNameForUser(n);
            return charSequence;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<ApnSetting> getOverrideApns(ComponentName object) {
        this.throwIfParentInstance("getOverrideApns");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getOverrideApns((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return Collections.emptyList();
    }

    public List<String> getOwnerInstalledCaCerts(UserHandle object) {
        try {
            object = this.mService.getOwnerInstalledCaCerts((UserHandle)object).getList();
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public DevicePolicyManager getParentProfileInstance(ComponentName object) {
        this.throwIfParentInstance("getParentProfileInstance");
        try {
            if (this.mService.isManagedProfile((ComponentName)object)) {
                return new DevicePolicyManager(this.mContext, this.mService, true);
            }
            object = new SecurityException("The current user does not have a parent profile.");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public DevicePolicyManager getParentProfileInstance(UserInfo userInfo) {
        this.mContext.checkSelfPermission("android.permission.MANAGE_PROFILE_AND_DEVICE_OWNERS");
        if (userInfo.isManagedProfile()) {
            return new DevicePolicyManager(this.mContext, this.mService, true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("The user ");
        stringBuilder.append(userInfo.id);
        stringBuilder.append(" does not have a parent profile.");
        throw new SecurityException(stringBuilder.toString());
    }

    public int getPasswordComplexity() {
        this.throwIfParentInstance("getPasswordComplexity");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 0;
        }
        try {
            int n = iDevicePolicyManager.getPasswordComplexity();
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getPasswordExpiration(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                long l = iDevicePolicyManager.getPasswordExpiration(componentName, this.myUserId(), this.mParentInstance);
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public long getPasswordExpirationTimeout(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                long l = iDevicePolicyManager.getPasswordExpirationTimeout(componentName, this.myUserId(), this.mParentInstance);
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0L;
    }

    public int getPasswordHistoryLength(ComponentName componentName) {
        return this.getPasswordHistoryLength(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordHistoryLength(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordHistoryLength(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMaximumLength(int n) {
        if (!this.mContext.getPackageManager().hasSystemFeature("android.software.secure_lock_screen")) {
            return 0;
        }
        return 16;
    }

    public int getPasswordMinimumLength(ComponentName componentName) {
        return this.getPasswordMinimumLength(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumLength(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumLength(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumLetters(ComponentName componentName) {
        return this.getPasswordMinimumLetters(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumLetters(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumLetters(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumLowerCase(ComponentName componentName) {
        return this.getPasswordMinimumLowerCase(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumLowerCase(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumLowerCase(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumNonLetter(ComponentName componentName) {
        return this.getPasswordMinimumNonLetter(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumNonLetter(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumNonLetter(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumNumeric(ComponentName componentName) {
        return this.getPasswordMinimumNumeric(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumNumeric(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumNumeric(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumSymbols(ComponentName componentName) {
        return this.getPasswordMinimumSymbols(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumSymbols(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumSymbols(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordMinimumUpperCase(ComponentName componentName) {
        return this.getPasswordMinimumUpperCase(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordMinimumUpperCase(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordMinimumUpperCase(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public int getPasswordQuality(ComponentName componentName) {
        return this.getPasswordQuality(componentName, this.myUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public int getPasswordQuality(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getPasswordQuality(componentName, n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public SystemUpdateInfo getPendingSystemUpdate(ComponentName parcelable) {
        this.throwIfParentInstance("getPendingSystemUpdate");
        try {
            parcelable = this.mService.getPendingSystemUpdate((ComponentName)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getPermissionGrantState(ComponentName componentName, String string2, String string3) {
        this.throwIfParentInstance("getPermissionGrantState");
        try {
            int n = this.mService.getPermissionGrantState(componentName, this.mContext.getPackageName(), string2, string3);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int getPermissionPolicy(ComponentName componentName) {
        this.throwIfParentInstance("getPermissionPolicy");
        try {
            int n = this.mService.getPermissionPolicy(componentName);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<String> getPermittedAccessibilityServices(int n) {
        this.throwIfParentInstance("getPermittedAccessibilityServices");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getPermittedAccessibilityServicesForUser(n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<String> getPermittedAccessibilityServices(ComponentName object) {
        this.throwIfParentInstance("getPermittedAccessibilityServices");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getPermittedAccessibilityServices((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<String> getPermittedCrossProfileNotificationListeners(ComponentName object) {
        this.throwIfParentInstance("getPermittedCrossProfileNotificationListeners");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getPermittedCrossProfileNotificationListeners((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public List<String> getPermittedInputMethods(ComponentName object) {
        this.throwIfParentInstance("getPermittedInputMethods");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getPermittedInputMethods((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    public List<String> getPermittedInputMethodsForCurrentUser() {
        this.throwIfParentInstance("getPermittedInputMethodsForCurrentUser");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getPermittedInputMethodsForCurrentUser();
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    public ComponentName getProfileOwner() throws IllegalArgumentException {
        this.throwIfParentInstance("getProfileOwner");
        return this.getProfileOwnerAsUser(this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public ComponentName getProfileOwnerAsUser(int n) {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getProfileOwnerAsUser(n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public ComponentName getProfileOwnerAsUser(UserHandle parcelable) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                parcelable = iDevicePolicyManager.getProfileOwnerAsUser(parcelable.getIdentifier());
                return parcelable;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public String getProfileOwnerName() throws IllegalArgumentException {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getProfileOwnerName(this.mContext.getUserId());
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    @SystemApi
    public String getProfileOwnerNameAsUser(int n) throws IllegalArgumentException {
        this.throwIfParentInstance("getProfileOwnerNameAsUser");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getProfileOwnerName(n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public int getProfileWithMinimumFailedPasswordsForWipe(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getProfileWithMinimumFailedPasswordsForWipe(n, this.mParentInstance);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return -10000;
    }

    public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.getRemoveWarning(componentName, remoteCallback, this.myUserId());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public long getRequiredStrongAuthTimeout(ComponentName componentName) {
        return this.getRequiredStrongAuthTimeout(componentName, this.myUserId());
    }

    @UnsupportedAppUsage
    public long getRequiredStrongAuthTimeout(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                long l = iDevicePolicyManager.getRequiredStrongAuthTimeout(componentName, n, this.mParentInstance);
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 259200000L;
    }

    public boolean getScreenCaptureDisabled(ComponentName componentName) {
        this.throwIfParentInstance("getScreenCaptureDisabled");
        return this.getScreenCaptureDisabled(componentName, this.myUserId());
    }

    public boolean getScreenCaptureDisabled(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getScreenCaptureDisabled(componentName, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public List<UserHandle> getSecondaryUsers(ComponentName object) {
        this.throwIfParentInstance("getSecondaryUsers");
        try {
            object = this.mService.getSecondaryUsers((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public CharSequence getShortSupportMessage(ComponentName object) {
        this.throwIfParentInstance("getShortSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getShortSupportMessage((ComponentName)object);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public CharSequence getShortSupportMessageForUser(ComponentName object, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getShortSupportMessageForUser((ComponentName)object, n);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public CharSequence getStartUserSessionMessage(ComponentName object) {
        this.throwIfParentInstance("getStartUserSessionMessage");
        try {
            object = this.mService.getStartUserSessionMessage((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean getStorageEncryption(ComponentName componentName) {
        this.throwIfParentInstance("getStorageEncryption");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.getStorageEncryption(componentName, this.myUserId());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int getStorageEncryptionStatus() {
        this.throwIfParentInstance("getStorageEncryptionStatus");
        return this.getStorageEncryptionStatus(this.myUserId());
    }

    @UnsupportedAppUsage
    public int getStorageEncryptionStatus(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                n = iDevicePolicyManager.getStorageEncryptionStatus(this.mContext.getPackageName(), n);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public SystemUpdatePolicy getSystemUpdatePolicy() {
        this.throwIfParentInstance("getSystemUpdatePolicy");
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getSystemUpdatePolicy();
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public PersistableBundle getTransferOwnershipBundle() {
        this.throwIfParentInstance("getTransferOwnershipBundle");
        try {
            PersistableBundle persistableBundle = this.mService.getTransferOwnershipBundle();
            return persistableBundle;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<PersistableBundle> getTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2) {
        return this.getTrustAgentConfiguration(componentName, componentName2, this.myUserId());
    }

    @UnsupportedAppUsage
    public List<PersistableBundle> getTrustAgentConfiguration(ComponentName object, ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.getTrustAgentConfiguration((ComponentName)object, componentName, n, this.mParentInstance);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return new ArrayList<PersistableBundle>();
    }

    @SystemApi
    public int getUserProvisioningState() {
        this.throwIfParentInstance("getUserProvisioningState");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.getUserProvisioningState();
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public Bundle getUserRestrictions(ComponentName parcelable) {
        this.throwIfParentInstance("getUserRestrictions");
        Bundle bundle = null;
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                bundle = iDevicePolicyManager.getUserRestrictions((ComponentName)parcelable);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        parcelable = bundle == null ? new Bundle() : bundle;
        return parcelable;
    }

    public String getWifiMacAddress(ComponentName object) {
        this.throwIfParentInstance("getWifiMacAddress");
        try {
            object = this.mService.getWifiMacAddress((ComponentName)object);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasCaCertInstalled(ComponentName object, byte[] arrby) {
        this.throwIfParentInstance("hasCaCertInstalled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        boolean bl = false;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.enforceCanManageCaCerts((ComponentName)object, this.mContext.getPackageName());
                object = DevicePolicyManager.getCaCertAlias(arrby);
                if (object != null) {
                    bl = true;
                }
                return bl;
            }
            catch (CertificateException certificateException) {
                Log.w(TAG, "Could not parse certificate", certificateException);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int n) {
        this.throwIfParentInstance("hasGrantedPolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.hasGrantedPolicy(componentName, n, this.myUserId());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean hasUserSetupCompleted() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.hasUserSetupCompleted();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean installCaCert(ComponentName componentName, byte[] arrby) {
        this.throwIfParentInstance("installCaCert");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.installCaCert(componentName, this.mContext.getPackageName(), arrby);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean installExistingPackage(ComponentName componentName, String string2) {
        this.throwIfParentInstance("installExistingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.installExistingPackage(componentName, this.mContext.getPackageName(), string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean installKeyPair(ComponentName componentName, PrivateKey privateKey, Certificate certificate, String string2) {
        return this.installKeyPair(componentName, privateKey, new Certificate[]{certificate}, string2, false);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean installKeyPair(ComponentName componentName, PrivateKey arrby, Certificate[] object, String string2, int n) {
        void var1_10;
        void var1_8;
        block10 : {
            void var1_6;
            block9 : {
                this.throwIfParentInstance("installKeyPair");
                boolean bl = (n & 1) == 1;
                boolean bl2 = (n & 2) == 2;
                byte[] arrby2 = Credentials.convertToPem(new Certificate[]{object[0]});
                byte[] arrby3 = null;
                if (((Object)object).length > 1) {
                    arrby3 = Credentials.convertToPem((Certificate[])Arrays.copyOfRange(object, 1, ((Object)object).length));
                }
                object = KeyFactory.getInstance(arrby.getAlgorithm());
                try {
                    arrby = ((KeyFactory)object).getKeySpec((Key)arrby, PKCS8EncodedKeySpec.class).getEncoded();
                    return this.mService.installKeyPair(componentName, this.mContext.getPackageName(), arrby, arrby2, arrby3, string2, bl, bl2);
                }
                catch (IOException | CertificateException exception) {
                    break block9;
                }
                catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                    break block10;
                }
                catch (RemoteException remoteException) {
                    throw var1_10.rethrowFromSystemServer();
                }
                catch (IOException | CertificateException exception) {
                    // empty catch block
                }
            }
            Log.w(TAG, "Could not pem-encode certificate", (Throwable)var1_6);
            return false;
            catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                // empty catch block
            }
        }
        Log.w(TAG, "Failed to obtain private key material", (Throwable)var1_8);
        return false;
        catch (RemoteException remoteException) {
            // empty catch block
        }
        throw var1_10.rethrowFromSystemServer();
    }

    public boolean installKeyPair(ComponentName componentName, PrivateKey privateKey, Certificate[] arrcertificate, String string2, boolean bl) {
        int n = 2;
        if (bl) {
            n = 2 | 1;
        }
        return this.installKeyPair(componentName, privateKey, arrcertificate, string2, n);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void installSystemUpdate(ComponentName componentName, Uri parcelable, final Executor executor, final InstallSystemUpdateCallback installSystemUpdateCallback) {
        this.throwIfParentInstance("installUpdate");
        if (this.mService == null) {
            return;
        }
        parcelable = this.mContext.getContentResolver().openFileDescriptor((Uri)parcelable, "r");
        try {
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            StartInstallingUpdateCallback.Stub stub = new StartInstallingUpdateCallback.Stub(){

                @Override
                public void onStartInstallingUpdateError(int n, String string2) {
                    DevicePolicyManager.this.executeCallback(n, string2, executor, installSystemUpdateCallback);
                }
            };
            iDevicePolicyManager.installUpdateFromFile(componentName, (ParcelFileDescriptor)parcelable, stub);
            if (parcelable == null) return;
        }
        catch (Throwable throwable) {
            try {
                throw throwable;
            }
            catch (Throwable throwable2) {
                if (parcelable == null) throw throwable2;
                try {
                    ((ParcelFileDescriptor)parcelable).close();
                    throw throwable2;
                }
                catch (Throwable throwable3) {
                    try {
                        throwable.addSuppressed(throwable3);
                        throw throwable2;
                    }
                    catch (IOException iOException) {
                        Log.w(TAG, iOException);
                        this.executeCallback(1, Log.getStackTraceString(iOException), executor, installSystemUpdateCallback);
                        return;
                    }
                    catch (FileNotFoundException fileNotFoundException) {
                        Log.w(TAG, fileNotFoundException);
                        this.executeCallback(4, Log.getStackTraceString(fileNotFoundException), executor, installSystemUpdateCallback);
                    }
                    return;
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                }
            }
        }
        ((ParcelFileDescriptor)parcelable).close();
    }

    public boolean isAccessibilityServicePermittedByAdmin(ComponentName componentName, String string2, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isAccessibilityServicePermittedByAdmin(componentName, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isActivePasswordSufficient() {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isActivePasswordSufficient(this.myUserId(), this.mParentInstance);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isAdminActive(ComponentName componentName) {
        this.throwIfParentInstance("isAdminActive");
        return this.isAdminActiveAsUser(componentName, this.myUserId());
    }

    public boolean isAdminActiveAsUser(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isAdminActive(componentName, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isAffiliatedUser() {
        this.throwIfParentInstance("isAffiliatedUser");
        try {
            boolean bl = this.mService.isAffiliatedUser();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAlwaysOnVpnLockdownEnabled(ComponentName componentName) {
        this.throwIfParentInstance("isAlwaysOnVpnLockdownEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isAlwaysOnVpnLockdownEnabled(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isApplicationHidden(ComponentName componentName, String string2) {
        this.throwIfParentInstance("isApplicationHidden");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isApplicationHidden(componentName, this.mContext.getPackageName(), string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isBackupServiceEnabled(ComponentName componentName) {
        this.throwIfParentInstance("isBackupServiceEnabled");
        try {
            boolean bl = this.mService.isBackupServiceEnabled(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isCaCertApproved(String string2, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isCaCertApproved(string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Deprecated
    public boolean isCallerApplicationRestrictionsManagingPackage() {
        this.throwIfParentInstance("isCallerApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isCallerApplicationRestrictionsManagingPackage(this.mContext.getPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isCurrentInputMethodSetByOwner() {
        try {
            boolean bl = this.mService.isCurrentInputMethodSetByOwner();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceIdAttestationSupported() {
        return this.mContext.getPackageManager().hasSystemFeature("android.software.device_id_attestation");
    }

    @SystemApi
    @SuppressLint(value={"Doclava125"})
    public boolean isDeviceManaged() {
        try {
            boolean bl = this.mService.hasDeviceOwner();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isDeviceOwnerApp(String string2) {
        this.throwIfParentInstance("isDeviceOwnerApp");
        return this.isDeviceOwnerAppOnCallingUser(string2);
    }

    public boolean isDeviceOwnerAppOnAnyUser(String string2) {
        return this.isDeviceOwnerAppOnAnyUserInner(string2, false);
    }

    public boolean isDeviceOwnerAppOnCallingUser(String string2) {
        return this.isDeviceOwnerAppOnAnyUserInner(string2, true);
    }

    @SystemApi
    public boolean isDeviceProvisioned() {
        try {
            boolean bl = this.mService.isDeviceProvisioned();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isDeviceProvisioningConfigApplied() {
        try {
            boolean bl = this.mService.isDeviceProvisioningConfigApplied();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isEphemeralUser(ComponentName componentName) {
        this.throwIfParentInstance("isEphemeralUser");
        try {
            boolean bl = this.mService.isEphemeralUser(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isInputMethodPermittedByAdmin(ComponentName componentName, String string2, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isInputMethodPermittedByAdmin(componentName, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isLockTaskPermitted(String string2) {
        this.throwIfParentInstance("isLockTaskPermitted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isLockTaskPermitted(string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isLogoutEnabled() {
        this.throwIfParentInstance("isLogoutEnabled");
        try {
            boolean bl = this.mService.isLogoutEnabled();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isManagedKiosk() {
        this.throwIfParentInstance("isManagedKiosk");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isManagedKiosk();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isManagedProfile(ComponentName componentName) {
        this.throwIfParentInstance("isManagedProfile");
        try {
            boolean bl = this.mService.isManagedProfile(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isMasterVolumeMuted(ComponentName componentName) {
        this.throwIfParentInstance("isMasterVolumeMuted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isMasterVolumeMuted(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isMeteredDataDisabledPackageForUser(ComponentName componentName, String string2, int n) {
        this.throwIfParentInstance("getMeteredDataDisabledForUser");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isMeteredDataDisabledPackageForUser(componentName, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isNetworkLoggingEnabled(ComponentName componentName) {
        this.throwIfParentInstance("isNetworkLoggingEnabled");
        try {
            boolean bl = this.mService.isNetworkLoggingEnabled(componentName, this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isNotificationListenerServicePermitted(String string2, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isNotificationListenerServicePermitted(string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public boolean isOverrideApnEnabled(ComponentName componentName) {
        this.throwIfParentInstance("isOverrideApnEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isOverrideApnEnabled(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isPackageAllowedToAccessCalendar(String string2) {
        this.throwIfParentInstance("isPackageAllowedToAccessCalendar");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isPackageAllowedToAccessCalendarForUser(string2, this.myUserId());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isPackageSuspended(ComponentName componentName, String string2) throws PackageManager.NameNotFoundException {
        this.throwIfParentInstance("isPackageSuspended");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isPackageSuspended(componentName, this.mContext.getPackageName(), string2);
                return bl;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                throw new PackageManager.NameNotFoundException(string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isProfileActivePasswordSufficientForParent(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isProfileActivePasswordSufficientForParent(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isProfileOwnerApp(String string2) {
        this.throwIfParentInstance("isProfileOwnerApp");
        Object object = this.mService;
        boolean bl = false;
        if (object != null) {
            block4 : {
                try {
                    object = object.getProfileOwner(this.myUserId());
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
                boolean bl2 = ((ComponentName)object).getPackageName().equals(string2);
                if (!bl2) break block4;
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public boolean isProvisioningAllowed(String string2) {
        this.throwIfParentInstance("isProvisioningAllowed");
        try {
            boolean bl = this.mService.isProvisioningAllowed(string2, this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isRemovingAdmin(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isRemovingAdmin(componentName, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isResetPasswordTokenActive(ComponentName componentName) {
        this.throwIfParentInstance("isResetPasswordTokenActive");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isResetPasswordTokenActive(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isSecurityLoggingEnabled(ComponentName componentName) {
        this.throwIfParentInstance("isSecurityLoggingEnabled");
        try {
            boolean bl = this.mService.isSecurityLoggingEnabled(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isSeparateProfileChallengeAllowed(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isSeparateProfileChallengeAllowed(n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isSystemOnlyUser(ComponentName componentName) {
        try {
            boolean bl = this.mService.isSystemOnlyUser(componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public boolean isUnattendedManagedKiosk() {
        this.throwIfParentInstance("isUnattendedManagedKiosk");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isUnattendedManagedKiosk();
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isUninstallBlocked(ComponentName componentName, String string2) {
        this.throwIfParentInstance("isUninstallBlocked");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isUninstallBlocked(componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean isUninstallInQueue(String string2) {
        try {
            boolean bl = this.mService.isUninstallInQueue(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isUsingUnifiedPassword(ComponentName componentName) {
        this.throwIfParentInstance("isUsingUnifiedPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.isUsingUnifiedPassword(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return true;
    }

    public void lockNow() {
        this.lockNow(0);
    }

    public void lockNow(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.lockNow(n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public int logoutUser(ComponentName componentName) {
        this.throwIfParentInstance("logoutUser");
        try {
            int n = this.mService.logoutUser(componentName);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @VisibleForTesting
    protected int myUserId() {
        return this.mContext.getUserId();
    }

    @SystemApi
    public void notifyPendingSystemUpdate(long l) {
        this.throwIfParentInstance("notifyPendingSystemUpdate");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.notifyPendingSystemUpdate(SystemUpdateInfo.of(l));
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void notifyPendingSystemUpdate(long l, boolean bl) {
        this.throwIfParentInstance("notifyPendingSystemUpdate");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.notifyPendingSystemUpdate(SystemUpdateInfo.of(l, bl));
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public boolean packageHasActiveAdmins(String string2) {
        return this.packageHasActiveAdmins(string2, this.myUserId());
    }

    @UnsupportedAppUsage
    public boolean packageHasActiveAdmins(String string2, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.packageHasActiveAdmins(string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void reboot(ComponentName componentName) {
        this.throwIfParentInstance("reboot");
        try {
            this.mService.reboot(componentName);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeActiveAdmin(ComponentName componentName) {
        this.throwIfParentInstance("removeActiveAdmin");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.removeActiveAdmin(componentName, this.myUserId());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean removeCrossProfileWidgetProvider(ComponentName componentName, String string2) {
        this.throwIfParentInstance("removeCrossProfileWidgetProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.removeCrossProfileWidgetProvider(componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean removeKeyPair(ComponentName componentName, String string2) {
        this.throwIfParentInstance("removeKeyPair");
        try {
            boolean bl = this.mService.removeKeyPair(componentName, this.mContext.getPackageName(), string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean removeOverrideApn(ComponentName componentName, int n) {
        this.throwIfParentInstance("removeOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.removeOverrideApn(componentName, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean removeUser(ComponentName componentName, UserHandle userHandle) {
        this.throwIfParentInstance("removeUser");
        try {
            boolean bl = this.mService.removeUser(componentName, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void reportFailedBiometricAttempt(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportFailedBiometricAttempt(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public void reportFailedPasswordAttempt(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportFailedPasswordAttempt(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardDismissed(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportKeyguardDismissed(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void reportKeyguardSecured(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportKeyguardSecured(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void reportPasswordChanged(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportPasswordChanged(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void reportSuccessfulBiometricAttempt(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportSuccessfulBiometricAttempt(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public void reportSuccessfulPasswordAttempt(int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.reportSuccessfulPasswordAttempt(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean requestBugreport(ComponentName componentName) {
        this.throwIfParentInstance("requestBugreport");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.requestBugreport(componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean resetPassword(String string2, int n) {
        this.throwIfParentInstance("resetPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.resetPassword(string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean resetPasswordWithToken(ComponentName componentName, String string2, byte[] arrby, int n) {
        this.throwIfParentInstance("resetPassword");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.resetPasswordWithToken(componentName, string2, arrby, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public List<NetworkEvent> retrieveNetworkLogs(ComponentName object, long l) {
        this.throwIfParentInstance("retrieveNetworkLogs");
        try {
            object = this.mService.retrieveNetworkLogs((ComponentName)object, this.mContext.getPackageName(), l);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<SecurityLog.SecurityEvent> retrievePreRebootSecurityLogs(ComponentName object) {
        block3 : {
            this.throwIfParentInstance("retrievePreRebootSecurityLogs");
            try {
                object = this.mService.retrievePreRebootSecurityLogs((ComponentName)object);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = ((ParceledListSlice)object).getList();
            return object;
        }
        return null;
    }

    public List<SecurityLog.SecurityEvent> retrieveSecurityLogs(ComponentName object) {
        block3 : {
            this.throwIfParentInstance("retrieveSecurityLogs");
            try {
                object = this.mService.retrieveSecurityLogs((ComponentName)object);
                if (object == null) break block3;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            object = ((ParceledListSlice)object).getList();
            return object;
        }
        return null;
    }

    public void setAccountManagementDisabled(ComponentName componentName, String string2, boolean bl) {
        this.throwIfParentInstance("setAccountManagementDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAccountManagementDisabled(componentName, string2, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public void setActiveAdmin(ComponentName componentName, boolean bl) {
        this.setActiveAdmin(componentName, bl, this.myUserId());
    }

    @UnsupportedAppUsage
    public void setActiveAdmin(ComponentName componentName, boolean bl, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setActiveAdmin(componentName, bl, n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @UnsupportedAppUsage
    public void setActivePasswordState(PasswordMetrics passwordMetrics, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setActivePasswordState(passwordMetrics, n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    @Deprecated
    public boolean setActiveProfileOwner(ComponentName componentName, @Deprecated String string2) throws IllegalArgumentException {
        this.throwIfParentInstance("setActiveProfileOwner");
        if (this.mService != null) {
            try {
                int n = this.myUserId();
                this.mService.setActiveAdmin(componentName, false, n);
                boolean bl = this.mService.setProfileOwner(componentName, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setAffiliationIds(ComponentName componentName, Set<String> set) {
        this.throwIfParentInstance("setAffiliationIds");
        if (set != null) {
            try {
                IDevicePolicyManager iDevicePolicyManager = this.mService;
                ArrayList<String> arrayList = new ArrayList<String>(set);
                iDevicePolicyManager.setAffiliationIds(componentName, arrayList);
                return;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        throw new IllegalArgumentException("ids must not be null");
    }

    public void setAlwaysOnVpnPackage(ComponentName componentName, String string2, boolean bl) throws PackageManager.NameNotFoundException {
        this.setAlwaysOnVpnPackage(componentName, string2, bl, Collections.<String>emptySet());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setAlwaysOnVpnPackage(ComponentName var1_1, String var2_4, boolean var3_5, Set<String> var4_6) throws PackageManager.NameNotFoundException {
        block3 : {
            this.throwIfParentInstance("setAlwaysOnVpnPackage");
            var5_10 = this.mService;
            if (var5_10 == null) return;
            if (var4_6 /* !! */  != null) break block3;
            var4_7 = null;
            ** GOTO lbl10
        }
        try {
            var4_8 = new ArrayList<String>(var4_6 /* !! */ );
lbl10: // 2 sources:
            var5_10.setAlwaysOnVpnPackage(var1_1, (String)var2_4, var3_5, (List<String>)var4_9);
            return;
        }
        catch (RemoteException var1_2) {
            throw var1_2.rethrowFromSystemServer();
        }
        catch (ServiceSpecificException var1_3) {
            if (var1_3.errorCode == 1) throw new PackageManager.NameNotFoundException(var1_3.getMessage());
            var2_4 = new StringBuilder();
            var2_4.append("Unknown error setting always-on VPN: ");
            var2_4.append(var1_3.errorCode);
            throw new RuntimeException(var2_4.toString(), var1_3);
        }
    }

    public boolean setApplicationHidden(ComponentName componentName, String string2, boolean bl) {
        this.throwIfParentInstance("setApplicationHidden");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                bl = iDevicePolicyManager.setApplicationHidden(componentName, this.mContext.getPackageName(), string2, bl);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setApplicationRestrictions(ComponentName componentName, String string2, Bundle bundle) {
        this.throwIfParentInstance("setApplicationRestrictions");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setApplicationRestrictions(componentName, this.mContext.getPackageName(), string2, bundle);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void setApplicationRestrictionsManagingPackage(ComponentName object, String string2) throws PackageManager.NameNotFoundException {
        this.throwIfParentInstance("setApplicationRestrictionsManagingPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                if (!iDevicePolicyManager.setApplicationRestrictionsManagingPackage((ComponentName)object, string2)) {
                    object = new PackageManager.NameNotFoundException(string2);
                    throw object;
                }
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setAutoTimeRequired(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setAutoTimeRequired");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setAutoTimeRequired(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setBackupServiceEnabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setBackupServiceEnabled");
        try {
            this.mService.setBackupServiceEnabled(componentName, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setBluetoothContactSharingDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setBluetoothContactSharingDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setBluetoothContactSharingDisabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setCameraDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setCameraDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCameraDisabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Deprecated
    public void setCertInstallerPackage(ComponentName componentName, String string2) throws SecurityException {
        this.throwIfParentInstance("setCertInstallerPackage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCertInstallerPackage(componentName, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void setCrossProfileCalendarPackages(ComponentName var1_1, Set<String> var2_3) {
        block2 : {
            this.throwIfParentInstance("setCrossProfileCalendarPackages");
            var3_7 = this.mService;
            if (var3_7 == null) return;
            if (var2_3 /* !! */  != null) break block2;
            var2_4 = null;
            ** GOTO lbl10
        }
        try {
            var2_5 = new ArrayList<String>(var2_3 /* !! */ );
lbl10: // 2 sources:
            var3_7.setCrossProfileCalendarPackages(var1_1, (List<String>)var2_6);
            return;
        }
        catch (RemoteException var1_2) {
            throw var1_2.rethrowFromSystemServer();
        }
    }

    public void setCrossProfileCallerIdDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setCrossProfileCallerIdDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfileCallerIdDisabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setCrossProfileContactsSearchDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setCrossProfileContactsSearchDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setCrossProfileContactsSearchDisabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setDefaultSmsApplication(ComponentName componentName, String string2) {
        this.throwIfParentInstance("setDefaultSmsApplication");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDefaultSmsApplication(componentName, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setDelegatedScopes(ComponentName componentName, String string2, List<String> list) {
        this.throwIfParentInstance("setDelegatedScopes");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDelegatedScopes(componentName, string2, list);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setDeviceOwner(ComponentName componentName) {
        return this.setDeviceOwner(componentName, null);
    }

    public boolean setDeviceOwner(ComponentName componentName, int n) {
        return this.setDeviceOwner(componentName, null, n);
    }

    public boolean setDeviceOwner(ComponentName componentName, String string2) {
        return this.setDeviceOwner(componentName, string2, 0);
    }

    public boolean setDeviceOwner(ComponentName componentName, String string2, int n) throws IllegalArgumentException, IllegalStateException {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setDeviceOwner(componentName, string2, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setDeviceOwnerLockScreenInfo(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setDeviceOwnerLockScreenInfo");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setDeviceOwnerLockScreenInfo(componentName, charSequence);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @SystemApi
    public void setDeviceProvisioningConfigApplied() {
        try {
            this.mService.setDeviceProvisioningConfigApplied();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setEndUserSessionMessage(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setEndUserSessionMessage");
        try {
            this.mService.setEndUserSessionMessage(componentName, charSequence);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setForceEphemeralUsers(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setForceEphemeralUsers");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setForceEphemeralUsers(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public int setGlobalPrivateDnsModeOpportunistic(ComponentName componentName) {
        this.throwIfParentInstance("setGlobalPrivateDnsModeOpportunistic");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return 2;
        }
        try {
            int n = iDevicePolicyManager.setGlobalPrivateDns(componentName, 2, null);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int setGlobalPrivateDnsModeSpecifiedHost(ComponentName componentName, String string2) {
        this.throwIfParentInstance("setGlobalPrivateDnsModeSpecifiedHost");
        Preconditions.checkNotNull(string2, "dns resolver is null");
        if (this.mService == null) {
            return 2;
        }
        if (NetworkUtils.isWeaklyValidatedHostname(string2) && !PrivateDnsConnectivityChecker.canConnectToPrivateDnsServer(string2)) {
            return 1;
        }
        try {
            int n = this.mService.setGlobalPrivateDns(componentName, 3, string2);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public ComponentName setGlobalProxy(ComponentName object, java.net.Proxy object2, List<String> object3) {
        this.throwIfParentInstance("setGlobalProxy");
        if (object2 == null) throw new NullPointerException();
        if (this.mService == null) return null;
        try {
            if (((java.net.Proxy)object2).equals(java.net.Proxy.NO_PROXY)) {
                object3 = null;
                object2 = null;
                return this.mService.setGlobalProxy((ComponentName)object, (String)object3, (String)object2);
            }
            if (!((java.net.Proxy)object2).type().equals((Object)Proxy.Type.HTTP)) {
                object = new IllegalArgumentException();
                throw object;
            }
            object2 = (InetSocketAddress)((java.net.Proxy)object2).address();
            String string2 = ((InetSocketAddress)object2).getHostName();
            int n = ((InetSocketAddress)object2).getPort();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(":");
            ((StringBuilder)object2).append(Integer.toString(n));
            String string3 = ((StringBuilder)object2).toString();
            if (object3 == null) {
                object2 = "";
            } else {
                object2 = new StringBuilder();
                boolean bl = true;
                object3 = object3.iterator();
                while (object3.hasNext()) {
                    String string4 = (String)object3.next();
                    if (!bl) {
                        ((StringBuilder)object2).append(",");
                    } else {
                        bl = false;
                    }
                    ((StringBuilder)object2).append(string4.trim());
                }
                object2 = ((StringBuilder)object2).toString();
            }
            if (Proxy.validate(string2, Integer.toString(n), (String)object2) == 0) {
                object3 = string3;
                return this.mService.setGlobalProxy((ComponentName)object, (String)object3, (String)object2);
            }
            object = new IllegalArgumentException();
            throw object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setGlobalSetting(ComponentName componentName, String string2, String string3) {
        this.throwIfParentInstance("setGlobalSetting");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setGlobalSetting(componentName, string2, string3);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setKeepUninstalledPackages(ComponentName componentName, List<String> list) {
        this.throwIfParentInstance("setKeepUninstalledPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setKeepUninstalledPackages(componentName, this.mContext.getPackageName(), list);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setKeyPairCertificate(ComponentName componentName, String string2, List<Certificate> object, boolean bl) {
        this.throwIfParentInstance("setKeyPairCertificate");
        byte[] arrby = Credentials.convertToPem(object.get(0));
        object = object.size() > 1 ? Credentials.convertToPem(object.subList(1, object.size()).toArray(new Certificate[0])) : null;
        try {
            bl = this.mService.setKeyPairCertificate(componentName, this.mContext.getPackageName(), string2, arrby, (byte[])object, bl);
            return bl;
        }
        catch (IOException | CertificateException exception) {
            Log.w(TAG, "Could not pem-encode certificate", exception);
            return false;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setKeyguardDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setKeyguardDisabled");
        try {
            bl = this.mService.setKeyguardDisabled(componentName, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setKeyguardDisabledFeatures(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setKeyguardDisabledFeatures(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setLockTaskFeatures(ComponentName componentName, int n) {
        this.throwIfParentInstance("setLockTaskFeatures");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLockTaskFeatures(componentName, n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setLockTaskPackages(ComponentName componentName, String[] arrstring) throws SecurityException {
        this.throwIfParentInstance("setLockTaskPackages");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLockTaskPackages(componentName, arrstring);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setLogoutEnabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setLogoutEnabled");
        try {
            this.mService.setLogoutEnabled(componentName, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setLongSupportMessage(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setLongSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setLongSupportMessage(componentName, charSequence);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setMasterVolumeMuted(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setMasterVolumeMuted");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMasterVolumeMuted(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setMaximumFailedPasswordsForWipe(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMaximumFailedPasswordsForWipe(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setMaximumTimeToLock(ComponentName componentName, long l) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setMaximumTimeToLock(componentName, l, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public List<String> setMeteredDataDisabledPackages(ComponentName object, List<String> list) {
        this.throwIfParentInstance("setMeteredDataDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                object = iDevicePolicyManager.setMeteredDataDisabledPackages((ComponentName)object, list);
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return list;
    }

    public void setNetworkLoggingEnabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setNetworkLoggingEnabled");
        try {
            this.mService.setNetworkLoggingEnabled(componentName, this.mContext.getPackageName(), bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOrganizationColor(ComponentName componentName, int n) {
        this.throwIfParentInstance("setOrganizationColor");
        try {
            this.mService.setOrganizationColor(componentName, n | -16777216);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOrganizationColorForUser(int n, int n2) {
        try {
            this.mService.setOrganizationColorForUser(n | -16777216, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOrganizationName(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setOrganizationName");
        try {
            this.mService.setOrganizationName(componentName, charSequence);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setOverrideApnsEnabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setOverrideApnEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setOverrideApnsEnabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public String[] setPackagesSuspended(ComponentName arrstring, String[] arrstring2, boolean bl) {
        this.throwIfParentInstance("setPackagesSuspended");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                arrstring = iDevicePolicyManager.setPackagesSuspended((ComponentName)arrstring, this.mContext.getPackageName(), arrstring2, bl);
                return arrstring;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return arrstring2;
    }

    public void setPasswordExpirationTimeout(ComponentName componentName, long l) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordExpirationTimeout(componentName, l, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordHistoryLength(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordHistoryLength(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumLength(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLength(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumLetters(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLetters(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumLowerCase(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumLowerCase(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumNonLetter(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumNonLetter(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumNumeric(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumNumeric(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumSymbols(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumSymbols(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordMinimumUpperCase(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordMinimumUpperCase(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setPasswordQuality(ComponentName componentName, int n) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setPasswordQuality(componentName, n, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setPermissionGrantState(ComponentName componentName, String string2, String string3, int n) {
        this.throwIfParentInstance("setPermissionGrantState");
        try {
            CompletableFuture completableFuture = new CompletableFuture();
            IDevicePolicyManager iDevicePolicyManager = this.mService;
            String string4 = this.mContext.getPackageName();
            _$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk _$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk = new _$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk(completableFuture);
            RemoteCallback remoteCallback = new RemoteCallback(_$$Lambda$DevicePolicyManager$w2TynM9H41ejac4JVpNbnemNVWk);
            iDevicePolicyManager.setPermissionGrantState(componentName, string4, string2, string3, n, remoteCallback);
            BackgroundThread.getHandler().sendMessageDelayed(PooledLambda.obtainMessage(_$$Lambda$pWaRScwKTZTgGW4Wa_v5R_pKBDU.INSTANCE, completableFuture, false), 20000L);
            boolean bl = (Boolean)completableFuture.get();
            return bl;
        }
        catch (InterruptedException | ExecutionException exception) {
            throw new RuntimeException(exception);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setPermissionPolicy(ComponentName componentName, int n) {
        this.throwIfParentInstance("setPermissionPolicy");
        try {
            this.mService.setPermissionPolicy(componentName, this.mContext.getPackageName(), n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setPermittedAccessibilityServices(ComponentName componentName, List<String> list) {
        this.throwIfParentInstance("setPermittedAccessibilityServices");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setPermittedAccessibilityServices(componentName, list);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setPermittedCrossProfileNotificationListeners(ComponentName componentName, List<String> list) {
        this.throwIfParentInstance("setPermittedCrossProfileNotificationListeners");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setPermittedCrossProfileNotificationListeners(componentName, list);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setPermittedInputMethods(ComponentName componentName, List<String> list) {
        this.throwIfParentInstance("setPermittedInputMethods");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setPermittedInputMethods(componentName, list);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setProfileEnabled(ComponentName componentName) {
        this.throwIfParentInstance("setProfileEnabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setProfileEnabled(componentName);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setProfileName(ComponentName componentName, String string2) {
        this.throwIfParentInstance("setProfileName");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setProfileName(componentName, string2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setProfileOwner(ComponentName componentName, @Deprecated String string2, int n) throws IllegalArgumentException {
        if (this.mService != null) {
            String string3 = string2;
            if (string2 == null) {
                string3 = "";
            }
            try {
                boolean bl = this.mService.setProfileOwner(componentName, string3, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @SystemApi
    public void setProfileOwnerCanAccessDeviceIds(ComponentName componentName) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager == null) {
            return;
        }
        try {
            iDevicePolicyManager.grantDeviceIdsAccessToProfileOwner(componentName, this.myUserId());
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setRecommendedGlobalProxy(ComponentName componentName, ProxyInfo proxyInfo) {
        this.throwIfParentInstance("setRecommendedGlobalProxy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRecommendedGlobalProxy(componentName, proxyInfo);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setRequiredStrongAuthTimeout(ComponentName componentName, long l) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRequiredStrongAuthTimeout(componentName, l, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setResetPasswordToken(ComponentName componentName, byte[] arrby) {
        this.throwIfParentInstance("setResetPasswordToken");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setResetPasswordToken(componentName, arrby);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setRestrictionsProvider(ComponentName componentName, ComponentName componentName2) {
        this.throwIfParentInstance("setRestrictionsProvider");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setRestrictionsProvider(componentName, componentName2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setScreenCaptureDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setScreenCaptureDisabled");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setScreenCaptureDisabled(componentName, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setSecureSetting(ComponentName componentName, String string2, String string3) {
        this.throwIfParentInstance("setSecureSetting");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSecureSetting(componentName, string2, string3);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setSecurityLoggingEnabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setSecurityLoggingEnabled");
        try {
            this.mService.setSecurityLoggingEnabled(componentName, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setShortSupportMessage(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setShortSupportMessage");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setShortSupportMessage(componentName, charSequence);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setStartUserSessionMessage(ComponentName componentName, CharSequence charSequence) {
        this.throwIfParentInstance("setStartUserSessionMessage");
        try {
            this.mService.setStartUserSessionMessage(componentName, charSequence);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean setStatusBarDisabled(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setStatusBarDisabled");
        try {
            bl = this.mService.setStatusBarDisabled(componentName, bl);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public int setStorageEncryption(ComponentName componentName, boolean bl) {
        this.throwIfParentInstance("setStorageEncryption");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                int n = iDevicePolicyManager.setStorageEncryption(componentName, bl);
                return n;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return 0;
    }

    public void setSystemSetting(ComponentName componentName, String string2, String string3) {
        this.throwIfParentInstance("setSystemSetting");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSystemSetting(componentName, string2, string3);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setSystemUpdatePolicy(ComponentName componentName, SystemUpdatePolicy systemUpdatePolicy) {
        this.throwIfParentInstance("setSystemUpdatePolicy");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setSystemUpdatePolicy(componentName, systemUpdatePolicy);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean setTime(ComponentName componentName, long l) {
        this.throwIfParentInstance("setTime");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setTime(componentName, l);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public boolean setTimeZone(ComponentName componentName, String string2) {
        this.throwIfParentInstance("setTimeZone");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.setTimeZone(componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void setTrustAgentConfiguration(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setTrustAgentConfiguration(componentName, componentName2, persistableBundle, this.mParentInstance);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setUninstallBlocked(ComponentName componentName, String string2, boolean bl) {
        this.throwIfParentInstance("setUninstallBlocked");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUninstallBlocked(componentName, this.mContext.getPackageName(), string2, bl);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setUserIcon(ComponentName componentName, Bitmap bitmap) {
        this.throwIfParentInstance("setUserIcon");
        try {
            this.mService.setUserIcon(componentName, bitmap);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUserProvisioningState(int n, int n2) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.setUserProvisioningState(n, n2);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void startManagedQuickContact(String string2, long l, Intent intent) {
        this.startManagedQuickContact(string2, l, false, 0L, intent);
    }

    public void startManagedQuickContact(String string2, long l, boolean bl, long l2, Intent intent) {
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                iDevicePolicyManager.startManagedQuickContact(string2, l, bl, l2, intent);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public int startUserInBackground(ComponentName componentName, UserHandle userHandle) {
        this.throwIfParentInstance("startUserInBackground");
        try {
            int n = this.mService.startUserInBackground(componentName, userHandle);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean startViewCalendarEventInManagedProfile(long l, long l2, long l3, boolean bl, int n) {
        this.throwIfParentInstance("startViewCalendarEventInManagedProfile");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                bl = iDevicePolicyManager.startViewCalendarEventInManagedProfile(this.mContext.getPackageName(), l, l2, l3, bl, n);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public int stopUser(ComponentName componentName, UserHandle userHandle) {
        this.throwIfParentInstance("stopUser");
        try {
            int n = this.mService.stopUser(componentName, userHandle);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean switchUser(ComponentName componentName, UserHandle userHandle) {
        this.throwIfParentInstance("switchUser");
        try {
            boolean bl = this.mService.switchUser(componentName, userHandle);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void transferOwnership(ComponentName componentName, ComponentName componentName2, PersistableBundle persistableBundle) {
        this.throwIfParentInstance("transferOwnership");
        try {
            this.mService.transferOwnership(componentName, componentName2, persistableBundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void uninstallAllUserCaCerts(ComponentName componentName) {
        this.throwIfParentInstance("uninstallAllUserCaCerts");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                String string2 = this.mContext.getPackageName();
                TrustedCertificateStore trustedCertificateStore = new TrustedCertificateStore();
                iDevicePolicyManager.uninstallCaCerts(componentName, string2, trustedCertificateStore.userAliases().toArray(new String[0]));
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void uninstallCaCert(ComponentName componentName, byte[] object) {
        this.throwIfParentInstance("uninstallCaCert");
        if (this.mService != null) {
            try {
                object = DevicePolicyManager.getCaCertAlias(object);
                this.mService.uninstallCaCerts(componentName, this.mContext.getPackageName(), new String[]{object});
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            catch (CertificateException certificateException) {
                Log.w(TAG, "Unable to parse certificate", certificateException);
            }
        }
    }

    public void uninstallPackageWithActiveAdmins(String string2) {
        try {
            this.mService.uninstallPackageWithActiveAdmins(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean updateOverrideApn(ComponentName componentName, int n, ApnSetting apnSetting) {
        this.throwIfParentInstance("updateOverrideApn");
        IDevicePolicyManager iDevicePolicyManager = this.mService;
        if (iDevicePolicyManager != null) {
            try {
                boolean bl = iDevicePolicyManager.updateOverrideApn(componentName, n, apnSetting);
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    public void wipeData(int n) {
        this.throwIfParentInstance("wipeData");
        this.wipeDataInternal(n, this.mContext.getString(17041316));
    }

    public void wipeData(int n, CharSequence charSequence) {
        this.throwIfParentInstance("wipeData");
        Preconditions.checkNotNull(charSequence, "reason string is null");
        Preconditions.checkStringNotEmpty(charSequence, "reason string is empty");
        boolean bl = (n & 8) == 0;
        Preconditions.checkArgument(bl, "WIPE_SILENTLY cannot be set");
        this.wipeDataInternal(n, charSequence.toString());
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AttestationIdType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CreateAndManageUserFlags {
    }

    public static abstract class InstallSystemUpdateCallback {
        public static final int UPDATE_ERROR_BATTERY_LOW = 5;
        public static final int UPDATE_ERROR_FILE_NOT_FOUND = 4;
        public static final int UPDATE_ERROR_INCORRECT_OS_VERSION = 2;
        public static final int UPDATE_ERROR_UNKNOWN = 1;
        public static final int UPDATE_ERROR_UPDATE_FILE_INVALID = 3;

        public void onInstallUpdateError(int n, String string2) {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InstallUpdateCallbackErrorConstants {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LockNowFlag {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LockTaskFeature {
    }

    public static interface OnClearApplicationUserDataListener {
        public void onApplicationUserDataCleared(String var1, boolean var2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PasswordComplexity {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PermissionGrantState {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PrivateDnsMode {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PrivateDnsModeErrorCodes {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProvisioningPreCondition {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SystemSettingsWhitelist {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface UserProvisioningState {
    }

}

