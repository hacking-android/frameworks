/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageParser;
import android.content.pm.PackageSharedLibraryUpdater;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting
public class AndroidHidlUpdater
extends PackageSharedLibraryUpdater {
    @Override
    public void updatePackage(PackageParser.Package package_) {
        ApplicationInfo applicationInfo = package_.applicationInfo;
        int n = applicationInfo.targetSdkVersion;
        boolean bl = true;
        n = n <= 28 ? 1 : 0;
        boolean bl2 = bl;
        if (!applicationInfo.isSystemApp()) {
            bl2 = applicationInfo.isUpdatedSystemApp() ? bl : false;
        }
        if (n != 0 && bl2) {
            this.prefixRequiredLibrary(package_, "android.hidl.base-V1.0-java");
            this.prefixRequiredLibrary(package_, "android.hidl.manager-V1.0-java");
        } else {
            AndroidHidlUpdater.removeLibrary(package_, "android.hidl.base-V1.0-java");
            AndroidHidlUpdater.removeLibrary(package_, "android.hidl.manager-V1.0-java");
        }
    }
}

