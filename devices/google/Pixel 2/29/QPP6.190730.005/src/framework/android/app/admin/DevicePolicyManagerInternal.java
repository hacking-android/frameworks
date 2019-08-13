/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.app.admin.DevicePolicyCache;
import android.content.Intent;
import java.util.List;

public abstract class DevicePolicyManagerInternal {
    public abstract void addOnCrossProfileWidgetProvidersChangeListener(OnCrossProfileWidgetProvidersChangeListener var1);

    public abstract boolean canSilentlyInstallPackage(String var1, int var2);

    public abstract boolean canUserHaveUntrustedCredentialReset(int var1);

    public abstract Intent createShowAdminSupportIntent(int var1, boolean var2);

    public abstract Intent createUserRestrictionSupportIntent(int var1, String var2);

    public abstract List<String> getCrossProfileWidgetProviders(int var1);

    protected abstract DevicePolicyCache getDevicePolicyCache();

    public abstract CharSequence getPrintingDisabledReasonForUser(int var1);

    public abstract boolean isActiveAdminWithPolicy(int var1, int var2);

    public abstract boolean isUserAffiliatedWithDevice(int var1);

    public abstract void reportSeparateProfileChallengeChanged(int var1);

    public static interface OnCrossProfileWidgetProvidersChangeListener {
        public void onCrossProfileWidgetProvidersChanged(int var1, List<String> var2);
    }

}

