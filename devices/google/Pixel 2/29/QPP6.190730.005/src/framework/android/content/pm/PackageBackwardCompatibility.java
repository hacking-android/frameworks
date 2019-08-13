/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.pm.-$
 *  android.content.pm.-$$Lambda
 *  android.content.pm.-$$Lambda$jpya2qgMDDEok2GAoKRDqPM5lIE
 */
package android.content.pm;

import android.content.pm.-$;
import android.content.pm.AndroidHidlUpdater;
import android.content.pm.OrgApacheHttpLegacyUpdater;
import android.content.pm.PackageParser;
import android.content.pm.PackageSharedLibraryUpdater;
import android.content.pm._$$Lambda$jpya2qgMDDEok2GAoKRDqPM5lIE;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@VisibleForTesting
public class PackageBackwardCompatibility
extends PackageSharedLibraryUpdater {
    private static final PackageBackwardCompatibility INSTANCE;
    private static final String TAG;
    private final boolean mBootClassPathContainsATB;
    private final PackageSharedLibraryUpdater[] mPackageUpdaters;

    static {
        TAG = PackageBackwardCompatibility.class.getSimpleName();
        ArrayList<PackageSharedLibraryUpdater> arrayList = new ArrayList<PackageSharedLibraryUpdater>();
        arrayList.add(new OrgApacheHttpLegacyUpdater());
        arrayList.add(new AndroidHidlUpdater());
        arrayList.add(new AndroidTestRunnerSplitUpdater());
        INSTANCE = new PackageBackwardCompatibility(PackageBackwardCompatibility.addOptionalUpdater(arrayList, "android.content.pm.AndroidTestBaseUpdater", (Supplier<PackageSharedLibraryUpdater>)_$$Lambda$jpya2qgMDDEok2GAoKRDqPM5lIE.INSTANCE) ^ true, arrayList.toArray(new PackageSharedLibraryUpdater[0]));
    }

    private PackageBackwardCompatibility(boolean bl, PackageSharedLibraryUpdater[] arrpackageSharedLibraryUpdater) {
        this.mBootClassPathContainsATB = bl;
        this.mPackageUpdaters = arrpackageSharedLibraryUpdater;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static boolean addOptionalUpdater(List<PackageSharedLibraryUpdater> object, String object2, Supplier<PackageSharedLibraryUpdater> object3) {
        Serializable serializable;
        try {
            serializable = PackageBackwardCompatibility.class.getClassLoader().loadClass((String)object2).asSubclass(PackageSharedLibraryUpdater.class);
            String string2 = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Loaded ");
            stringBuilder.append((String)object2);
            Log.i(string2, stringBuilder.toString());
        }
        catch (ClassNotFoundException classNotFoundException) {
            String string3 = TAG;
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Could not find ");
            ((StringBuilder)serializable).append((String)object2);
            ((StringBuilder)serializable).append(", ignoring");
            Log.i(string3, ((StringBuilder)serializable).toString());
            serializable = null;
        }
        boolean bl = false;
        if (serializable == null) {
            object2 = (PackageSharedLibraryUpdater)object3.get();
        } else {
            object3 = (PackageSharedLibraryUpdater)((Class)serializable).getConstructor(new Class[0]).newInstance(new Object[0]);
            bl = true;
            object2 = object3;
        }
        object.add(object2);
        return bl;
        catch (ReflectiveOperationException reflectiveOperationException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Could not create instance of ");
            ((StringBuilder)object).append((String)object2);
            throw new IllegalStateException(((StringBuilder)object).toString(), reflectiveOperationException);
        }
    }

    @VisibleForTesting
    public static boolean bootClassPathContainsATB() {
        return PackageBackwardCompatibility.INSTANCE.mBootClassPathContainsATB;
    }

    @VisibleForTesting
    public static PackageSharedLibraryUpdater getInstance() {
        return INSTANCE;
    }

    @VisibleForTesting
    public static void modifySharedLibraries(PackageParser.Package package_) {
        INSTANCE.updatePackage(package_);
    }

    @Override
    public void updatePackage(PackageParser.Package package_) {
        PackageSharedLibraryUpdater[] arrpackageSharedLibraryUpdater = this.mPackageUpdaters;
        int n = arrpackageSharedLibraryUpdater.length;
        for (int i = 0; i < n; ++i) {
            arrpackageSharedLibraryUpdater[i].updatePackage(package_);
        }
    }

    @VisibleForTesting
    public static class AndroidTestRunnerSplitUpdater
    extends PackageSharedLibraryUpdater {
        @Override
        public void updatePackage(PackageParser.Package package_) {
            this.prefixImplicitDependency(package_, "android.test.runner", "android.test.mock");
        }
    }

    @VisibleForTesting
    public static class RemoveUnnecessaryAndroidTestBaseLibrary
    extends PackageSharedLibraryUpdater {
        @Override
        public void updatePackage(PackageParser.Package package_) {
            RemoveUnnecessaryAndroidTestBaseLibrary.removeLibrary(package_, "android.test.base");
        }
    }

    @VisibleForTesting
    public static class RemoveUnnecessaryOrgApacheHttpLegacyLibrary
    extends PackageSharedLibraryUpdater {
        @Override
        public void updatePackage(PackageParser.Package package_) {
            RemoveUnnecessaryOrgApacheHttpLegacyLibrary.removeLibrary(package_, "org.apache.http.legacy");
        }
    }

}

