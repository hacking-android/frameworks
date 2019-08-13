/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.IActivityManager;
import android.app.IActivityTaskManager;
import android.app.INotificationManager;
import android.app._$$Lambda$KlsE01yvVI54Xvdo0TIjyhUKWHQ;
import android.app._$$Lambda$YTMEV7TmbMrzjIag59qAffcsEUw;
import android.app._$$Lambda$rztNj2LGZZegxvT34NFbOqZrZHM;
import android.app.trust.ITrustManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.service.persistentdata.IPersistentDataBlockService;
import android.util.Log;
import android.view.IOnKeyguardExitResult;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.internal.policy.IKeyguardDismissCallback;
import com.android.internal.widget.LockPatternUtils;
import java.util.List;
import java.util.Objects;

public class KeyguardManager {
    public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL = "android.app.action.CONFIRM_DEVICE_CREDENTIAL";
    public static final String ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER = "android.app.action.CONFIRM_DEVICE_CREDENTIAL_WITH_USER";
    public static final String ACTION_CONFIRM_FRP_CREDENTIAL = "android.app.action.CONFIRM_FRP_CREDENTIAL";
    public static final String EXTRA_ALTERNATE_BUTTON_LABEL = "android.app.extra.ALTERNATE_BUTTON_LABEL";
    public static final String EXTRA_BIOMETRIC_PROMPT_BUNDLE = "android.app.extra.BIOMETRIC_PROMPT_BUNDLE";
    public static final String EXTRA_DESCRIPTION = "android.app.extra.DESCRIPTION";
    public static final String EXTRA_TITLE = "android.app.extra.TITLE";
    public static final int RESULT_ALTERNATE = 1;
    private static final String TAG = "KeyguardManager";
    private final IActivityManager mAm;
    private final Context mContext;
    private final INotificationManager mNotificationManager;
    private final ITrustManager mTrustManager;
    private final IWindowManager mWM;

    KeyguardManager(Context context) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mWM = WindowManagerGlobal.getWindowManagerService();
        this.mAm = ActivityManager.getService();
        this.mTrustManager = ITrustManager.Stub.asInterface(ServiceManager.getServiceOrThrow("trust"));
        this.mNotificationManager = INotificationManager.Stub.asInterface(ServiceManager.getServiceOrThrow("notification"));
    }

    private String getSettingsPackageForIntent(Intent list) {
        list = this.mContext.getPackageManager().queryIntentActivities((Intent)((Object)list), 1048576);
        if (list.size() < 0) {
            return ((ResolveInfo)list.get((int)0)).activityInfo.packageName;
        }
        return "com.android.settings";
    }

    @Deprecated
    public Intent createConfirmDeviceCredentialIntent(CharSequence charSequence, CharSequence charSequence2) {
        if (!this.isDeviceSecure()) {
            return null;
        }
        Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.setPackage(this.getSettingsPackageForIntent(intent));
        return intent;
    }

    public Intent createConfirmDeviceCredentialIntent(CharSequence charSequence, CharSequence charSequence2, int n) {
        if (!this.isDeviceSecure(n)) {
            return null;
        }
        Intent intent = new Intent(ACTION_CONFIRM_DEVICE_CREDENTIAL_WITH_USER);
        intent.putExtra(EXTRA_TITLE, charSequence);
        intent.putExtra(EXTRA_DESCRIPTION, charSequence2);
        intent.putExtra("android.intent.extra.USER_ID", n);
        intent.setPackage(this.getSettingsPackageForIntent(intent));
        return intent;
    }

    @SystemApi
    public Intent createConfirmFactoryResetCredentialIntent(CharSequence object, CharSequence charSequence, CharSequence charSequence2) {
        if (LockPatternUtils.frpCredentialEnabled(this.mContext)) {
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "device_provisioned", 0) == 0) {
                block7 : {
                    Object object2;
                    try {
                        object2 = IPersistentDataBlockService.Stub.asInterface(ServiceManager.getService("persistent_data_block"));
                        if (object2 == null) break block7;
                    }
                    catch (RemoteException remoteException) {
                        throw remoteException.rethrowFromSystemServer();
                    }
                    if (!object2.hasFrpCredentialHandle()) {
                        Log.i(TAG, "The persistent data block does not have a factory reset credential.");
                        return null;
                    }
                    object2 = new Intent(ACTION_CONFIRM_FRP_CREDENTIAL);
                    ((Intent)object2).putExtra(EXTRA_TITLE, (CharSequence)object);
                    ((Intent)object2).putExtra(EXTRA_DESCRIPTION, charSequence);
                    ((Intent)object2).putExtra(EXTRA_ALTERNATE_BUTTON_LABEL, charSequence2);
                    ((Intent)object2).setPackage(this.getSettingsPackageForIntent((Intent)object2));
                    return object2;
                }
                Log.e(TAG, "No persistent data block service");
                object = new UnsupportedOperationException("not supported on this device");
                throw object;
            }
            Log.e(TAG, "Factory reset credential cannot be verified after provisioning.");
            throw new IllegalStateException("must not be provisioned yet");
        }
        Log.w(TAG, "Factory reset credentials not supported.");
        throw new UnsupportedOperationException("not supported on this device");
    }

    @Deprecated
    public void dismissKeyguard(Activity activity, KeyguardDismissCallback keyguardDismissCallback, Handler handler) {
        this.requestDismissKeyguard(activity, keyguardDismissCallback);
    }

    @Deprecated
    public void exitKeyguardSecurely(final OnKeyguardExitResult onKeyguardExitResult) {
        try {
            IWindowManager iWindowManager = this.mWM;
            IOnKeyguardExitResult.Stub stub = new IOnKeyguardExitResult.Stub(){

                @Override
                public void onKeyguardExitResult(boolean bl) throws RemoteException {
                    OnKeyguardExitResult onKeyguardExitResult2 = onKeyguardExitResult;
                    if (onKeyguardExitResult2 != null) {
                        onKeyguardExitResult2.onKeyguardExitResult(bl);
                    }
                }
            };
            iWindowManager.exitKeyguardSecurely(stub);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    @SystemApi
    public boolean getPrivateNotificationsAllowed() {
        try {
            boolean bl = this.mNotificationManager.getPrivateNotificationsAllowed();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean inKeyguardRestrictedInputMode() {
        return this.isKeyguardLocked();
    }

    public boolean isDeviceLocked() {
        return this.isDeviceLocked(this.mContext.getUserId());
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean isDeviceLocked(int n) {
        try {
            boolean bl = this.mTrustManager.isDeviceLocked(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isDeviceSecure() {
        return this.isDeviceSecure(this.mContext.getUserId());
    }

    @UnsupportedAppUsage
    public boolean isDeviceSecure(int n) {
        try {
            boolean bl = this.mTrustManager.isDeviceSecure(n);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isKeyguardLocked() {
        try {
            boolean bl = this.mWM.isKeyguardLocked();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isKeyguardSecure() {
        try {
            boolean bl = this.mWM.isKeyguardSecure(this.mContext.getUserId());
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    @Deprecated
    public KeyguardLock newKeyguardLock(String string2) {
        return new KeyguardLock(string2);
    }

    public void requestDismissKeyguard(Activity activity, KeyguardDismissCallback keyguardDismissCallback) {
        this.requestDismissKeyguard(activity, null, keyguardDismissCallback);
    }

    @SystemApi
    public void requestDismissKeyguard(final Activity activity, CharSequence charSequence, final KeyguardDismissCallback keyguardDismissCallback) {
        try {
            IActivityTaskManager iActivityTaskManager = ActivityTaskManager.getService();
            IBinder iBinder = activity.getActivityToken();
            IKeyguardDismissCallback.Stub stub = new IKeyguardDismissCallback.Stub(){

                @Override
                public void onDismissCancelled() throws RemoteException {
                    if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                        Handler handler = activity.mHandler;
                        KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                        Objects.requireNonNull(keyguardDismissCallback2);
                        handler.post(new _$$Lambda$KlsE01yvVI54Xvdo0TIjyhUKWHQ(keyguardDismissCallback2));
                    }
                }

                @Override
                public void onDismissError() throws RemoteException {
                    if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                        Handler handler = activity.mHandler;
                        KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                        Objects.requireNonNull(keyguardDismissCallback2);
                        handler.post(new _$$Lambda$rztNj2LGZZegxvT34NFbOqZrZHM(keyguardDismissCallback2));
                    }
                }

                @Override
                public void onDismissSucceeded() throws RemoteException {
                    if (keyguardDismissCallback != null && !activity.isDestroyed()) {
                        Handler handler = activity.mHandler;
                        KeyguardDismissCallback keyguardDismissCallback2 = keyguardDismissCallback;
                        Objects.requireNonNull(keyguardDismissCallback2);
                        handler.post(new _$$Lambda$YTMEV7TmbMrzjIag59qAffcsEUw(keyguardDismissCallback2));
                    }
                }
            };
            iActivityTaskManager.dismissKeyguard(iBinder, stub, charSequence);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void setPrivateNotificationsAllowed(boolean bl) {
        try {
            this.mNotificationManager.setPrivateNotificationsAllowed(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static abstract class KeyguardDismissCallback {
        public void onDismissCancelled() {
        }

        public void onDismissError() {
        }

        public void onDismissSucceeded() {
        }
    }

    @Deprecated
    public class KeyguardLock {
        private final String mTag;
        private final IBinder mToken = new Binder();

        KeyguardLock(String string2) {
            this.mTag = string2;
        }

        public void disableKeyguard() {
            try {
                KeyguardManager.this.mWM.disableKeyguard(this.mToken, this.mTag, KeyguardManager.this.mContext.getUserId());
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void reenableKeyguard() {
            try {
                KeyguardManager.this.mWM.reenableKeyguard(this.mToken, KeyguardManager.this.mContext.getUserId());
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @Deprecated
    public static interface OnKeyguardExitResult {
        public void onKeyguardExitResult(boolean var1);
    }

}

