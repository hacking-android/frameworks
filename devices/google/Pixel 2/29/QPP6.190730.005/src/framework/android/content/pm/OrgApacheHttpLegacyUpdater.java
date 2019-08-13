/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageParser;
import android.content.pm.PackageSharedLibraryUpdater;
import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting
public class OrgApacheHttpLegacyUpdater
extends PackageSharedLibraryUpdater {
    private static boolean apkTargetsApiLevelLessThanOrEqualToOMR1(PackageParser.Package package_) {
        boolean bl = package_.applicationInfo.targetSdkVersion < 28;
        return bl;
    }

    @Override
    public void updatePackage(PackageParser.Package package_) {
        if (OrgApacheHttpLegacyUpdater.apkTargetsApiLevelLessThanOrEqualToOMR1(package_)) {
            this.prefixRequiredLibrary(package_, "org.apache.http.legacy");
        }
    }
}

