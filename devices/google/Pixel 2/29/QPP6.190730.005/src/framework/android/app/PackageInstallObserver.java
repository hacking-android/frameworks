/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.content.pm.IPackageInstallObserver2;
import android.os.Bundle;

public class PackageInstallObserver {
    private final IPackageInstallObserver2.Stub mBinder = new IPackageInstallObserver2.Stub(){

        @Override
        public void onPackageInstalled(String string2, int n, String string3, Bundle bundle) {
            PackageInstallObserver.this.onPackageInstalled(string2, n, string3, bundle);
        }

        @Override
        public void onUserActionRequired(Intent intent) {
            PackageInstallObserver.this.onUserActionRequired(intent);
        }
    };

    public IPackageInstallObserver2 getBinder() {
        return this.mBinder;
    }

    @UnsupportedAppUsage
    public void onPackageInstalled(String string2, int n, String string3, Bundle bundle) {
    }

    public void onUserActionRequired(Intent intent) {
    }

}

