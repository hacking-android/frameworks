/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.Intent;
import android.content.pm.IPackageDeleteObserver2;

public class PackageDeleteObserver {
    private final IPackageDeleteObserver2.Stub mBinder = new IPackageDeleteObserver2.Stub(){

        @Override
        public void onPackageDeleted(String string2, int n, String string3) {
            PackageDeleteObserver.this.onPackageDeleted(string2, n, string3);
        }

        @Override
        public void onUserActionRequired(Intent intent) {
            PackageDeleteObserver.this.onUserActionRequired(intent);
        }
    };

    public IPackageDeleteObserver2 getBinder() {
        return this.mBinder;
    }

    public void onPackageDeleted(String string2, int n, String string3) {
    }

    public void onUserActionRequired(Intent intent) {
    }

}

