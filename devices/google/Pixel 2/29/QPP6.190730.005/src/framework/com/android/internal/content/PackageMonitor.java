/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.content;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.Preconditions;
import java.util.HashSet;

public abstract class PackageMonitor
extends BroadcastReceiver {
    public static final int PACKAGE_PERMANENT_CHANGE = 3;
    public static final int PACKAGE_TEMPORARY_CHANGE = 2;
    public static final int PACKAGE_UNCHANGED = 0;
    public static final int PACKAGE_UPDATING = 1;
    static final IntentFilter sExternalFilt;
    static final IntentFilter sNonDataFilt;
    static final IntentFilter sPackageFilt;
    String[] mAppearingPackages;
    int mChangeType;
    int mChangeUserId = -10000;
    String[] mDisappearingPackages;
    String[] mModifiedComponents;
    String[] mModifiedPackages;
    Context mRegisteredContext;
    Handler mRegisteredHandler;
    boolean mSomePackagesChanged;
    String[] mTempArray = new String[1];
    final HashSet<String> mUpdatingPackages = new HashSet();

    static {
        sPackageFilt = new IntentFilter();
        sNonDataFilt = new IntentFilter();
        sExternalFilt = new IntentFilter();
        sPackageFilt.addAction("android.intent.action.PACKAGE_ADDED");
        sPackageFilt.addAction("android.intent.action.PACKAGE_REMOVED");
        sPackageFilt.addAction("android.intent.action.PACKAGE_CHANGED");
        sPackageFilt.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        sPackageFilt.addAction("android.intent.action.PACKAGE_RESTARTED");
        sPackageFilt.addAction("android.intent.action.PACKAGE_DATA_CLEARED");
        sPackageFilt.addDataScheme("package");
        sNonDataFilt.addAction("android.intent.action.UID_REMOVED");
        sNonDataFilt.addAction("android.intent.action.USER_STOPPED");
        sNonDataFilt.addAction("android.intent.action.PACKAGES_SUSPENDED");
        sNonDataFilt.addAction("android.intent.action.PACKAGES_UNSUSPENDED");
        sExternalFilt.addAction("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE");
        sExternalFilt.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
    }

    public boolean anyPackagesAppearing() {
        boolean bl = this.mAppearingPackages != null;
        return bl;
    }

    public boolean anyPackagesDisappearing() {
        boolean bl = this.mDisappearingPackages != null;
        return bl;
    }

    public boolean didSomePackagesChange() {
        return this.mSomePackagesChanged;
    }

    public int getChangingUserId() {
        return this.mChangeUserId;
    }

    String getPackageName(Intent object) {
        object = (object = ((Intent)object).getData()) != null ? ((Uri)object).getSchemeSpecificPart() : null;
        return object;
    }

    public Handler getRegisteredHandler() {
        return this.mRegisteredHandler;
    }

    public boolean isComponentModified(String string2) {
        String[] arrstring;
        if (string2 != null && (arrstring = this.mModifiedComponents) != null) {
            for (int i = arrstring.length - 1; i >= 0; --i) {
                if (!string2.equals(this.mModifiedComponents[i])) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    public int isPackageAppearing(String string2) {
        String[] arrstring = this.mAppearingPackages;
        if (arrstring != null) {
            for (int i = arrstring.length - 1; i >= 0; --i) {
                if (!string2.equals(this.mAppearingPackages[i])) continue;
                return this.mChangeType;
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public int isPackageDisappearing(String string2) {
        String[] arrstring = this.mDisappearingPackages;
        if (arrstring != null) {
            for (int i = arrstring.length - 1; i >= 0; --i) {
                if (!string2.equals(this.mDisappearingPackages[i])) continue;
                return this.mChangeType;
            }
        }
        return 0;
    }

    @UnsupportedAppUsage
    public boolean isPackageModified(String string2) {
        String[] arrstring = this.mModifiedPackages;
        if (arrstring != null) {
            for (int i = arrstring.length - 1; i >= 0; --i) {
                if (!string2.equals(this.mModifiedPackages[i])) continue;
                return true;
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    boolean isPackageUpdating(String string2) {
        HashSet<String> hashSet = this.mUpdatingPackages;
        synchronized (hashSet) {
            return this.mUpdatingPackages.contains(string2);
        }
    }

    public boolean isReplacing() {
        int n = this.mChangeType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public void onBeginPackageChanges() {
    }

    public void onFinishPackageChanges() {
    }

    public boolean onHandleForceStop(Intent intent, String[] arrstring, int n, boolean bl) {
        return false;
    }

    public void onHandleUserStop(Intent intent, int n) {
    }

    public void onPackageAdded(String string2, int n) {
    }

    public void onPackageAppeared(String string2, int n) {
    }

    @UnsupportedAppUsage
    public boolean onPackageChanged(String string2, int n, String[] arrstring) {
        if (arrstring != null) {
            int n2 = arrstring.length;
            for (n = 0; n < n2; ++n) {
                if (!string2.equals(arrstring[n])) continue;
                return true;
            }
        }
        return false;
    }

    public void onPackageDataCleared(String string2, int n) {
    }

    public void onPackageDisappeared(String string2, int n) {
    }

    public void onPackageModified(String string2) {
    }

    @UnsupportedAppUsage
    public void onPackageRemoved(String string2, int n) {
    }

    public void onPackageRemovedAllUsers(String string2, int n) {
    }

    public void onPackageUpdateFinished(String string2, int n) {
    }

    public void onPackageUpdateStarted(String string2, int n) {
    }

    public void onPackagesAvailable(String[] arrstring) {
    }

    public void onPackagesSuspended(String[] arrstring) {
    }

    public void onPackagesSuspended(String[] arrstring, Bundle bundle) {
        this.onPackagesSuspended(arrstring);
    }

    public void onPackagesUnavailable(String[] arrstring) {
    }

    public void onPackagesUnsuspended(String[] arrstring) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void onReceive(Context object, Intent object2) {
        this.mChangeUserId = ((Intent)object2).getIntExtra("android.intent.extra.user_handle", -10000);
        if (this.mChangeUserId == -10000) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Intent broadcast does not contain user handle: ");
            ((StringBuilder)object).append(object2);
            Slog.w("PackageMonitor", ((StringBuilder)object).toString());
            return;
        }
        this.onBeginPackageChanges();
        this.mAppearingPackages = null;
        this.mDisappearingPackages = null;
        this.mSomePackagesChanged = false;
        this.mModifiedComponents = null;
        object = ((Intent)object2).getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(object)) {
            object = this.getPackageName((Intent)object2);
            int n = ((Intent)object2).getIntExtra("android.intent.extra.UID", 0);
            this.mSomePackagesChanged = true;
            if (object != null) {
                String[] arrstring = this.mTempArray;
                this.mAppearingPackages = arrstring;
                arrstring[0] = object;
                if (((Intent)object2).getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    this.mModifiedPackages = this.mTempArray;
                    this.mChangeType = 1;
                    this.onPackageUpdateFinished((String)object, n);
                    this.onPackageModified((String)object);
                } else {
                    this.mChangeType = 3;
                    this.onPackageAdded((String)object, n);
                }
                this.onPackageAppeared((String)object, this.mChangeType);
                if (this.mChangeType == 1) {
                    object2 = this.mUpdatingPackages;
                    // MONITORENTER : object2
                    this.mUpdatingPackages.remove(object);
                    // MONITOREXIT : object2
                }
            }
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(object)) {
            object = this.getPackageName((Intent)object2);
            int n = ((Intent)object2).getIntExtra("android.intent.extra.UID", 0);
            if (object != null) {
                String[] arrstring = this.mTempArray;
                this.mDisappearingPackages = arrstring;
                arrstring[0] = object;
                if (((Intent)object2).getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    this.mChangeType = 1;
                    object2 = this.mUpdatingPackages;
                    // MONITORENTER : object2
                    // MONITOREXIT : object2
                    this.onPackageUpdateStarted((String)object, n);
                } else {
                    this.mChangeType = 3;
                    this.mSomePackagesChanged = true;
                    this.onPackageRemoved((String)object, n);
                    if (((Intent)object2).getBooleanExtra("android.intent.extra.REMOVED_FOR_ALL_USERS", false)) {
                        this.onPackageRemovedAllUsers((String)object, n);
                    }
                }
                this.onPackageDisappeared((String)object, this.mChangeType);
            }
        } else if ("android.intent.action.PACKAGE_CHANGED".equals(object)) {
            object = this.getPackageName((Intent)object2);
            int n = ((Intent)object2).getIntExtra("android.intent.extra.UID", 0);
            this.mModifiedComponents = ((Intent)object2).getStringArrayExtra("android.intent.extra.changed_component_name_list");
            if (object != null) {
                object2 = this.mTempArray;
                this.mModifiedPackages = object2;
                object2[0] = object;
                this.mChangeType = 3;
                if (this.onPackageChanged((String)object, n, this.mModifiedComponents)) {
                    this.mSomePackagesChanged = true;
                }
                this.onPackageModified((String)object);
            }
        } else if ("android.intent.action.PACKAGE_DATA_CLEARED".equals(object)) {
            object = this.getPackageName((Intent)object2);
            int n = ((Intent)object2).getIntExtra("android.intent.extra.UID", 0);
            if (object != null) {
                this.onPackageDataCleared((String)object, n);
            }
        } else {
            boolean bl = "android.intent.action.QUERY_PACKAGE_RESTART".equals(object);
            int n = 2;
            if (bl) {
                this.mDisappearingPackages = ((Intent)object2).getStringArrayExtra("android.intent.extra.PACKAGES");
                this.mChangeType = 2;
                if (this.onHandleForceStop((Intent)object2, this.mDisappearingPackages, ((Intent)object2).getIntExtra("android.intent.extra.UID", 0), false)) {
                    this.setResultCode(-1);
                }
            } else if ("android.intent.action.PACKAGE_RESTARTED".equals(object)) {
                this.mDisappearingPackages = new String[]{this.getPackageName((Intent)object2)};
                this.mChangeType = 2;
                this.onHandleForceStop((Intent)object2, this.mDisappearingPackages, ((Intent)object2).getIntExtra("android.intent.extra.UID", 0), true);
            } else if ("android.intent.action.UID_REMOVED".equals(object)) {
                this.onUidRemoved(((Intent)object2).getIntExtra("android.intent.extra.UID", 0));
            } else if ("android.intent.action.USER_STOPPED".equals(object)) {
                if (((Intent)object2).hasExtra("android.intent.extra.user_handle")) {
                    this.onHandleUserStop((Intent)object2, ((Intent)object2).getIntExtra("android.intent.extra.user_handle", 0));
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_AVAILABLE".equals(object)) {
                object = ((Intent)object2).getStringArrayExtra("android.intent.extra.changed_package_list");
                this.mAppearingPackages = object;
                if (((Intent)object2).getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    n = 1;
                }
                this.mChangeType = n;
                this.mSomePackagesChanged = true;
                if (object != null) {
                    this.onPackagesAvailable((String[])object);
                    for (n = 0; n < ((Object)object).length; ++n) {
                        this.onPackageAppeared((String)object[n], this.mChangeType);
                    }
                }
            } else if ("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE".equals(object)) {
                object = ((Intent)object2).getStringArrayExtra("android.intent.extra.changed_package_list");
                this.mDisappearingPackages = object;
                if (((Intent)object2).getBooleanExtra("android.intent.extra.REPLACING", false)) {
                    n = 1;
                }
                this.mChangeType = n;
                this.mSomePackagesChanged = true;
                if (object != null) {
                    this.onPackagesUnavailable((String[])object);
                    for (n = 0; n < ((Object)object).length; ++n) {
                        this.onPackageDisappeared((String)object[n], this.mChangeType);
                    }
                }
            } else if ("android.intent.action.PACKAGES_SUSPENDED".equals(object)) {
                object = ((Intent)object2).getStringArrayExtra("android.intent.extra.changed_package_list");
                object2 = ((Intent)object2).getBundleExtra("android.intent.extra.LAUNCHER_EXTRAS");
                this.mSomePackagesChanged = true;
                this.onPackagesSuspended((String[])object, (Bundle)object2);
            } else if ("android.intent.action.PACKAGES_UNSUSPENDED".equals(object)) {
                object = ((Intent)object2).getStringArrayExtra("android.intent.extra.changed_package_list");
                this.mSomePackagesChanged = true;
                this.onPackagesUnsuspended((String[])object);
            }
        }
        if (this.mSomePackagesChanged) {
            this.onSomePackagesChanged();
        }
        this.onFinishPackageChanges();
        this.mChangeUserId = -10000;
    }

    public void onSomePackagesChanged() {
    }

    public void onUidRemoved(int n) {
    }

    @UnsupportedAppUsage
    public void register(Context context, Looper object, UserHandle userHandle, boolean bl) {
        object = object == null ? BackgroundThread.getHandler() : new Handler((Looper)object);
        this.register(context, userHandle, bl, (Handler)object);
    }

    @UnsupportedAppUsage
    public void register(Context context, Looper looper, boolean bl) {
        this.register(context, looper, null, bl);
    }

    public void register(Context context, UserHandle userHandle, boolean bl, Handler handler) {
        if (this.mRegisteredContext == null) {
            this.mRegisteredContext = context;
            this.mRegisteredHandler = Preconditions.checkNotNull(handler);
            if (userHandle != null) {
                context.registerReceiverAsUser(this, userHandle, sPackageFilt, null, this.mRegisteredHandler);
                context.registerReceiverAsUser(this, userHandle, sNonDataFilt, null, this.mRegisteredHandler);
                if (bl) {
                    context.registerReceiverAsUser(this, userHandle, sExternalFilt, null, this.mRegisteredHandler);
                }
            } else {
                context.registerReceiver(this, sPackageFilt, null, this.mRegisteredHandler);
                context.registerReceiver(this, sNonDataFilt, null, this.mRegisteredHandler);
                if (bl) {
                    context.registerReceiver(this, sExternalFilt, null, this.mRegisteredHandler);
                }
            }
            return;
        }
        throw new IllegalStateException("Already registered");
    }

    @UnsupportedAppUsage
    public void unregister() {
        Context context = this.mRegisteredContext;
        if (context != null) {
            context.unregisterReceiver(this);
            this.mRegisteredContext = null;
            return;
        }
        throw new IllegalStateException("Not registered");
    }
}

