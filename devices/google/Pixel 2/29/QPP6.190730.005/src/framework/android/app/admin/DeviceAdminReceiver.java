/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.UserHandle;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class DeviceAdminReceiver
extends BroadcastReceiver {
    public static final String ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE";
    public static final String ACTION_BUGREPORT_FAILED = "android.app.action.BUGREPORT_FAILED";
    public static final String ACTION_BUGREPORT_SHARE = "android.app.action.BUGREPORT_SHARE";
    public static final String ACTION_BUGREPORT_SHARING_DECLINED = "android.app.action.BUGREPORT_SHARING_DECLINED";
    public static final String ACTION_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.action.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final String ACTION_DEVICE_ADMIN_DISABLED = "android.app.action.DEVICE_ADMIN_DISABLED";
    public static final String ACTION_DEVICE_ADMIN_DISABLE_REQUESTED = "android.app.action.DEVICE_ADMIN_DISABLE_REQUESTED";
    public static final String ACTION_DEVICE_ADMIN_ENABLED = "android.app.action.DEVICE_ADMIN_ENABLED";
    public static final String ACTION_LOCK_TASK_ENTERING = "android.app.action.LOCK_TASK_ENTERING";
    public static final String ACTION_LOCK_TASK_EXITING = "android.app.action.LOCK_TASK_EXITING";
    public static final String ACTION_NETWORK_LOGS_AVAILABLE = "android.app.action.NETWORK_LOGS_AVAILABLE";
    public static final String ACTION_NOTIFY_PENDING_SYSTEM_UPDATE = "android.app.action.NOTIFY_PENDING_SYSTEM_UPDATE";
    public static final String ACTION_PASSWORD_CHANGED = "android.app.action.ACTION_PASSWORD_CHANGED";
    public static final String ACTION_PASSWORD_EXPIRING = "android.app.action.ACTION_PASSWORD_EXPIRING";
    public static final String ACTION_PASSWORD_FAILED = "android.app.action.ACTION_PASSWORD_FAILED";
    public static final String ACTION_PASSWORD_SUCCEEDED = "android.app.action.ACTION_PASSWORD_SUCCEEDED";
    public static final String ACTION_PROFILE_PROVISIONING_COMPLETE = "android.app.action.PROFILE_PROVISIONING_COMPLETE";
    public static final String ACTION_SECURITY_LOGS_AVAILABLE = "android.app.action.SECURITY_LOGS_AVAILABLE";
    public static final String ACTION_TRANSFER_OWNERSHIP_COMPLETE = "android.app.action.TRANSFER_OWNERSHIP_COMPLETE";
    public static final String ACTION_USER_ADDED = "android.app.action.USER_ADDED";
    public static final String ACTION_USER_REMOVED = "android.app.action.USER_REMOVED";
    public static final String ACTION_USER_STARTED = "android.app.action.USER_STARTED";
    public static final String ACTION_USER_STOPPED = "android.app.action.USER_STOPPED";
    public static final String ACTION_USER_SWITCHED = "android.app.action.USER_SWITCHED";
    public static final int BUGREPORT_FAILURE_FAILED_COMPLETING = 0;
    public static final int BUGREPORT_FAILURE_FILE_NO_LONGER_AVAILABLE = 1;
    public static final String DEVICE_ADMIN_META_DATA = "android.app.device_admin";
    public static final String EXTRA_BUGREPORT_FAILURE_REASON = "android.app.extra.BUGREPORT_FAILURE_REASON";
    public static final String EXTRA_BUGREPORT_HASH = "android.app.extra.BUGREPORT_HASH";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_ALIAS = "android.app.extra.CHOOSE_PRIVATE_KEY_ALIAS";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_RESPONSE = "android.app.extra.CHOOSE_PRIVATE_KEY_RESPONSE";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID = "android.app.extra.CHOOSE_PRIVATE_KEY_SENDER_UID";
    public static final String EXTRA_CHOOSE_PRIVATE_KEY_URI = "android.app.extra.CHOOSE_PRIVATE_KEY_URI";
    public static final String EXTRA_DISABLE_WARNING = "android.app.extra.DISABLE_WARNING";
    public static final String EXTRA_LOCK_TASK_PACKAGE = "android.app.extra.LOCK_TASK_PACKAGE";
    public static final String EXTRA_NETWORK_LOGS_COUNT = "android.app.extra.EXTRA_NETWORK_LOGS_COUNT";
    public static final String EXTRA_NETWORK_LOGS_TOKEN = "android.app.extra.EXTRA_NETWORK_LOGS_TOKEN";
    public static final String EXTRA_SYSTEM_UPDATE_RECEIVED_TIME = "android.app.extra.SYSTEM_UPDATE_RECEIVED_TIME";
    public static final String EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE = "android.app.extra.TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE";
    private static String TAG = "DevicePolicy";
    private static boolean localLOGV = false;
    private DevicePolicyManager mManager;
    private ComponentName mWho;

    public DevicePolicyManager getManager(Context context) {
        DevicePolicyManager devicePolicyManager = this.mManager;
        if (devicePolicyManager != null) {
            return devicePolicyManager;
        }
        this.mManager = (DevicePolicyManager)context.getSystemService("device_policy");
        return this.mManager;
    }

    public ComponentName getWho(Context context) {
        ComponentName componentName = this.mWho;
        if (componentName != null) {
            return componentName;
        }
        this.mWho = new ComponentName(context, this.getClass());
        return this.mWho;
    }

    public void onBugreportFailed(Context context, Intent intent, int n) {
    }

    public void onBugreportShared(Context context, Intent intent, String string2) {
    }

    public void onBugreportSharingDeclined(Context context, Intent intent) {
    }

    public String onChoosePrivateKeyAlias(Context context, Intent intent, int n, Uri uri, String string2) {
        return null;
    }

    public CharSequence onDisableRequested(Context context, Intent intent) {
        return null;
    }

    public void onDisabled(Context context, Intent intent) {
    }

    public void onEnabled(Context context, Intent intent) {
    }

    public void onLockTaskModeEntering(Context context, Intent intent, String string2) {
    }

    public void onLockTaskModeExiting(Context context, Intent intent) {
    }

    public void onNetworkLogsAvailable(Context context, Intent intent, long l, int n) {
    }

    @Deprecated
    public void onPasswordChanged(Context context, Intent intent) {
    }

    public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
        this.onPasswordChanged(context, intent);
    }

    @Deprecated
    public void onPasswordExpiring(Context context, Intent intent) {
    }

    public void onPasswordExpiring(Context context, Intent intent, UserHandle userHandle) {
        this.onPasswordExpiring(context, intent);
    }

    @Deprecated
    public void onPasswordFailed(Context context, Intent intent) {
    }

    public void onPasswordFailed(Context context, Intent intent, UserHandle userHandle) {
        this.onPasswordFailed(context, intent);
    }

    @Deprecated
    public void onPasswordSucceeded(Context context, Intent intent) {
    }

    public void onPasswordSucceeded(Context context, Intent intent, UserHandle userHandle) {
        this.onPasswordSucceeded(context, intent);
    }

    public void onProfileProvisioningComplete(Context context, Intent intent) {
    }

    @Deprecated
    public void onReadyForUserInitialization(Context context, Intent intent) {
    }

    @Override
    public void onReceive(Context object, Intent intent) {
        block1 : {
            String string2;
            block23 : {
                block22 : {
                    block21 : {
                        block20 : {
                            block19 : {
                                block18 : {
                                    block17 : {
                                        block16 : {
                                            block15 : {
                                                block14 : {
                                                    block13 : {
                                                        block12 : {
                                                            block11 : {
                                                                block10 : {
                                                                    block9 : {
                                                                        block8 : {
                                                                            block7 : {
                                                                                block6 : {
                                                                                    block5 : {
                                                                                        block4 : {
                                                                                            block3 : {
                                                                                                block2 : {
                                                                                                    block0 : {
                                                                                                        string2 = intent.getAction();
                                                                                                        if (!ACTION_PASSWORD_CHANGED.equals(string2)) break block0;
                                                                                                        this.onPasswordChanged((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                                                                                        break block1;
                                                                                                    }
                                                                                                    if (!ACTION_PASSWORD_FAILED.equals(string2)) break block2;
                                                                                                    this.onPasswordFailed((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                                                                                    break block1;
                                                                                                }
                                                                                                if (!ACTION_PASSWORD_SUCCEEDED.equals(string2)) break block3;
                                                                                                this.onPasswordSucceeded((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                                                                                break block1;
                                                                                            }
                                                                                            if (!ACTION_DEVICE_ADMIN_ENABLED.equals(string2)) break block4;
                                                                                            this.onEnabled((Context)object, intent);
                                                                                            break block1;
                                                                                        }
                                                                                        if (!ACTION_DEVICE_ADMIN_DISABLE_REQUESTED.equals(string2)) break block5;
                                                                                        if ((object = this.onDisableRequested((Context)object, intent)) == null) break block1;
                                                                                        this.getResultExtras(true).putCharSequence(EXTRA_DISABLE_WARNING, (CharSequence)object);
                                                                                        break block1;
                                                                                    }
                                                                                    if (!ACTION_DEVICE_ADMIN_DISABLED.equals(string2)) break block6;
                                                                                    this.onDisabled((Context)object, intent);
                                                                                    break block1;
                                                                                }
                                                                                if (!ACTION_PASSWORD_EXPIRING.equals(string2)) break block7;
                                                                                this.onPasswordExpiring((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                                                                break block1;
                                                                            }
                                                                            if (!ACTION_PROFILE_PROVISIONING_COMPLETE.equals(string2)) break block8;
                                                                            this.onProfileProvisioningComplete((Context)object, intent);
                                                                            break block1;
                                                                        }
                                                                        if (!ACTION_CHOOSE_PRIVATE_KEY_ALIAS.equals(string2)) break block9;
                                                                        this.setResultData(this.onChoosePrivateKeyAlias((Context)object, intent, intent.getIntExtra(EXTRA_CHOOSE_PRIVATE_KEY_SENDER_UID, -1), (Uri)intent.getParcelableExtra(EXTRA_CHOOSE_PRIVATE_KEY_URI), intent.getStringExtra(EXTRA_CHOOSE_PRIVATE_KEY_ALIAS)));
                                                                        break block1;
                                                                    }
                                                                    if (!ACTION_LOCK_TASK_ENTERING.equals(string2)) break block10;
                                                                    this.onLockTaskModeEntering((Context)object, intent, intent.getStringExtra(EXTRA_LOCK_TASK_PACKAGE));
                                                                    break block1;
                                                                }
                                                                if (!ACTION_LOCK_TASK_EXITING.equals(string2)) break block11;
                                                                this.onLockTaskModeExiting((Context)object, intent);
                                                                break block1;
                                                            }
                                                            if (!ACTION_NOTIFY_PENDING_SYSTEM_UPDATE.equals(string2)) break block12;
                                                            this.onSystemUpdatePending((Context)object, intent, intent.getLongExtra(EXTRA_SYSTEM_UPDATE_RECEIVED_TIME, -1L));
                                                            break block1;
                                                        }
                                                        if (!ACTION_BUGREPORT_SHARING_DECLINED.equals(string2)) break block13;
                                                        this.onBugreportSharingDeclined((Context)object, intent);
                                                        break block1;
                                                    }
                                                    if (!ACTION_BUGREPORT_SHARE.equals(string2)) break block14;
                                                    this.onBugreportShared((Context)object, intent, intent.getStringExtra(EXTRA_BUGREPORT_HASH));
                                                    break block1;
                                                }
                                                if (!ACTION_BUGREPORT_FAILED.equals(string2)) break block15;
                                                this.onBugreportFailed((Context)object, intent, intent.getIntExtra(EXTRA_BUGREPORT_FAILURE_REASON, 0));
                                                break block1;
                                            }
                                            if (!ACTION_SECURITY_LOGS_AVAILABLE.equals(string2)) break block16;
                                            this.onSecurityLogsAvailable((Context)object, intent);
                                            break block1;
                                        }
                                        if (!ACTION_NETWORK_LOGS_AVAILABLE.equals(string2)) break block17;
                                        this.onNetworkLogsAvailable((Context)object, intent, intent.getLongExtra(EXTRA_NETWORK_LOGS_TOKEN, -1L), intent.getIntExtra(EXTRA_NETWORK_LOGS_COUNT, 0));
                                        break block1;
                                    }
                                    if (!ACTION_USER_ADDED.equals(string2)) break block18;
                                    this.onUserAdded((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                    break block1;
                                }
                                if (!ACTION_USER_REMOVED.equals(string2)) break block19;
                                this.onUserRemoved((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                                break block1;
                            }
                            if (!ACTION_USER_STARTED.equals(string2)) break block20;
                            this.onUserStarted((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                            break block1;
                        }
                        if (!ACTION_USER_STOPPED.equals(string2)) break block21;
                        this.onUserStopped((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                        break block1;
                    }
                    if (!ACTION_USER_SWITCHED.equals(string2)) break block22;
                    this.onUserSwitched((Context)object, intent, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
                    break block1;
                }
                if (!ACTION_TRANSFER_OWNERSHIP_COMPLETE.equals(string2)) break block23;
                this.onTransferOwnershipComplete((Context)object, (PersistableBundle)intent.getParcelableExtra(EXTRA_TRANSFER_OWNERSHIP_ADMIN_EXTRAS_BUNDLE));
                break block1;
            }
            if (!ACTION_AFFILIATED_PROFILE_TRANSFER_OWNERSHIP_COMPLETE.equals(string2)) break block1;
            this.onTransferAffiliatedProfileOwnershipComplete((Context)object, (UserHandle)intent.getParcelableExtra("android.intent.extra.USER"));
        }
    }

    public void onSecurityLogsAvailable(Context context, Intent intent) {
    }

    public void onSystemUpdatePending(Context context, Intent intent, long l) {
    }

    public void onTransferAffiliatedProfileOwnershipComplete(Context context, UserHandle userHandle) {
    }

    public void onTransferOwnershipComplete(Context context, PersistableBundle persistableBundle) {
    }

    public void onUserAdded(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserRemoved(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserStarted(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserStopped(Context context, Intent intent, UserHandle userHandle) {
    }

    public void onUserSwitched(Context context, Intent intent, UserHandle userHandle) {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BugreportFailureCode {
    }

}

