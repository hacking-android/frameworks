/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.UserInfo;
import android.metrics.LogMaker;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.Slog;
import android.widget.Toast;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.app.ChooserActivity;
import com.android.internal.app.ResolverActivity;
import com.android.internal.logging.MetricsLogger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntentForwarderActivity
extends Activity {
    private static final Set<String> ALLOWED_TEXT_MESSAGE_SCHEMES;
    public static String FORWARD_INTENT_TO_MANAGED_PROFILE;
    public static String FORWARD_INTENT_TO_PARENT;
    @UnsupportedAppUsage
    public static String TAG;
    private static final String TEL_SCHEME = "tel";
    private Injector mInjector;
    private MetricsLogger mMetricsLogger;

    static {
        TAG = "IntentForwarderActivity";
        FORWARD_INTENT_TO_PARENT = "com.android.internal.app.ForwardIntentToParent";
        FORWARD_INTENT_TO_MANAGED_PROFILE = "com.android.internal.app.ForwardIntentToManagedProfile";
        ALLOWED_TEXT_MESSAGE_SCHEMES = new HashSet<String>(Arrays.asList("sms", "smsto", "mms", "mmsto"));
    }

    private int getManagedProfile() {
        for (UserInfo object2 : this.mInjector.getUserManager().getProfiles(UserHandle.myUserId())) {
            if (!object2.isManagedProfile()) continue;
            return object2.id;
        }
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(FORWARD_INTENT_TO_MANAGED_PROFILE);
        stringBuilder.append(" has been called, but there is no managed profile");
        Slog.wtf(string2, stringBuilder.toString());
        return -10000;
    }

    private int getProfileParent() {
        Object object = this.mInjector.getUserManager().getProfileParent(UserHandle.myUserId());
        if (object == null) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(FORWARD_INTENT_TO_PARENT);
            stringBuilder.append(" has been called, but there is no parent");
            Slog.wtf((String)object, stringBuilder.toString());
            return -10000;
        }
        return ((UserInfo)object).id;
    }

    private boolean isDialerIntent(Intent intent) {
        boolean bl = "android.intent.action.DIAL".equals(intent.getAction()) || "android.intent.action.CALL".equals(intent.getAction()) || "android.intent.action.CALL_PRIVILEGED".equals(intent.getAction()) || "android.intent.action.CALL_EMERGENCY".equals(intent.getAction()) || this.isViewActionIntent(intent) && TEL_SCHEME.equals(intent.getScheme());
        return bl;
    }

    private boolean isTargetResolverOrChooserActivity(ActivityInfo activityInfo) {
        boolean bl = "android".equals(activityInfo.packageName);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (ResolverActivity.class.getName().equals(activityInfo.name) || ChooserActivity.class.getName().equals(activityInfo.name)) {
            bl2 = true;
        }
        return bl2;
    }

    private boolean isTextMessageIntent(Intent intent) {
        boolean bl = ("android.intent.action.SENDTO".equals(intent.getAction()) || this.isViewActionIntent(intent)) && ALLOWED_TEXT_MESSAGE_SCHEMES.contains(intent.getScheme());
        return bl;
    }

    private boolean isViewActionIntent(Intent intent) {
        boolean bl = "android.intent.action.VIEW".equals(intent.getAction()) && intent.hasCategory("android.intent.category.BROWSABLE");
        return bl;
    }

    private void sanitizeIntent(Intent intent) {
        intent.setPackage(null);
        intent.setComponent(null);
    }

    private boolean shouldShowDisclosure(ResolveInfo resolveInfo, Intent intent) {
        if (resolveInfo != null && resolveInfo.activityInfo != null) {
            if (resolveInfo.activityInfo.applicationInfo.isSystemApp() && (this.isDialerIntent(intent) || this.isTextMessageIntent(intent))) {
                return false;
            }
            return true ^ this.isTargetResolverOrChooserActivity(resolveInfo.activityInfo);
        }
        return true;
    }

    Intent canForward(Intent object, int n) {
        Object object2;
        Intent intent = new Intent((Intent)object);
        intent.addFlags(50331648);
        this.sanitizeIntent(intent);
        object = intent;
        if ("android.intent.action.CHOOSER".equals(intent.getAction())) {
            if (intent.hasExtra("android.intent.extra.INITIAL_INTENTS")) {
                Slog.wtf(TAG, "An chooser intent with extra initial intents cannot be forwarded to a different user");
                return null;
            }
            if (intent.hasExtra("android.intent.extra.REPLACEMENT_EXTRAS")) {
                Slog.wtf(TAG, "A chooser intent with replacement extras cannot be forwarded to a different user");
                return null;
            }
            object = object2 = (Intent)intent.getParcelableExtra("android.intent.extra.INTENT");
            if (object2 == null) {
                Slog.wtf(TAG, "Cannot forward a chooser intent with no extra android.intent.extra.INTENT");
                return null;
            }
        }
        if (intent.getSelector() != null) {
            object = intent.getSelector();
        }
        object2 = ((Intent)object).resolveTypeIfNeeded(this.getContentResolver());
        this.sanitizeIntent((Intent)object);
        try {
            boolean bl = this.mInjector.getIPackageManager().canForwardTo((Intent)object, (String)object2, this.getUserId(), n);
            if (bl) {
                return intent;
            }
        }
        catch (RemoteException remoteException) {
            Slog.e(TAG, "PackageManagerService is dead?");
        }
        return null;
    }

    @VisibleForTesting
    protected Injector createInjector() {
        return new InjectorImpl();
    }

    protected MetricsLogger getMetricsLogger() {
        if (this.mMetricsLogger == null) {
            this.mMetricsLogger = new MetricsLogger();
        }
        return this.mMetricsLogger;
    }

    @Override
    protected void onCreate(Bundle object) {
        int n;
        Object object2;
        int n2;
        super.onCreate((Bundle)object);
        this.mInjector = this.createInjector();
        Intent intent = this.getIntent();
        object = intent.getComponent().getClassName();
        if (((String)object).equals(FORWARD_INTENT_TO_PARENT)) {
            n = 17040045;
            n2 = this.getProfileParent();
            this.getMetricsLogger().write(new LogMaker(1661).setSubtype(1));
        } else if (((String)object).equals(FORWARD_INTENT_TO_MANAGED_PROFILE)) {
            n = 17040046;
            n2 = this.getManagedProfile();
            this.getMetricsLogger().write(new LogMaker(1661).setSubtype(2));
        } else {
            object2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append(IntentForwarderActivity.class.getName());
            ((StringBuilder)object).append(" cannot be called directly");
            Slog.wtf((String)object2, ((StringBuilder)object).toString());
            n = -1;
            n2 = -10000;
        }
        if (n2 == -10000) {
            this.finish();
            return;
        }
        int n3 = this.getUserId();
        object = this.canForward(intent, n2);
        if (object != null) {
            if ("android.intent.action.CHOOSER".equals(((Intent)object).getAction())) {
                object2 = (Intent)((Intent)object).getParcelableExtra("android.intent.extra.INTENT");
                ((Intent)object2).prepareToLeaveUser(n3);
                ((Intent)object2).fixUris(n3);
            } else {
                ((Intent)object).prepareToLeaveUser(n3);
            }
            ResolveInfo resolveInfo = this.mInjector.resolveActivityAsUser((Intent)object, 65536, n2);
            try {
                this.startActivityAsCaller((Intent)object, null, null, false, n2);
            }
            catch (RuntimeException runtimeException) {
                n2 = -1;
                object = "?";
                n2 = n3 = ActivityTaskManager.getService().getLaunchedFromUid(this.getActivityToken());
                try {
                    object = object2 = ActivityTaskManager.getService().getLaunchedFromPackage(this.getActivityToken());
                    n2 = n3;
                }
                catch (RemoteException remoteException) {
                    // empty catch block
                }
                String string2 = TAG;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unable to launch as UID ");
                ((StringBuilder)object2).append(n2);
                ((StringBuilder)object2).append(" package ");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append(", while running in ");
                ((StringBuilder)object2).append(ActivityThread.currentProcessName());
                Slog.wtf(string2, ((StringBuilder)object2).toString(), runtimeException);
            }
            if (this.shouldShowDisclosure(resolveInfo, intent)) {
                this.mInjector.showToast(n, 1);
            }
        } else {
            object2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("the intent: ");
            ((StringBuilder)object).append(intent);
            ((StringBuilder)object).append(" cannot be forwarded from user ");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" to user ");
            ((StringBuilder)object).append(n2);
            Slog.wtf((String)object2, ((StringBuilder)object).toString());
        }
        this.finish();
    }

    public static interface Injector {
        public IPackageManager getIPackageManager();

        public PackageManager getPackageManager();

        public UserManager getUserManager();

        public ResolveInfo resolveActivityAsUser(Intent var1, int var2, int var3);

        public void showToast(int var1, int var2);
    }

    private class InjectorImpl
    implements Injector {
        private InjectorImpl() {
        }

        @Override
        public IPackageManager getIPackageManager() {
            return AppGlobals.getPackageManager();
        }

        @Override
        public PackageManager getPackageManager() {
            return IntentForwarderActivity.this.getPackageManager();
        }

        @Override
        public UserManager getUserManager() {
            return IntentForwarderActivity.this.getSystemService(UserManager.class);
        }

        @Override
        public ResolveInfo resolveActivityAsUser(Intent intent, int n, int n2) {
            return this.getPackageManager().resolveActivityAsUser(intent, n, n2);
        }

        @Override
        public void showToast(int n, int n2) {
            IntentForwarderActivity intentForwarderActivity = IntentForwarderActivity.this;
            Toast.makeText((Context)intentForwarderActivity, intentForwarderActivity.getString(n), n2).show();
        }
    }

}

