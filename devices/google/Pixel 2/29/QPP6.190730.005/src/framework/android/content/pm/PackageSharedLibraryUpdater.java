/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.PackageParser;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import java.util.ArrayList;

@VisibleForTesting
public abstract class PackageSharedLibraryUpdater {
    private static boolean isLibraryPresent(ArrayList<String> arrayList, ArrayList<String> arrayList2, String string2) {
        boolean bl = ArrayUtils.contains(arrayList, string2) || ArrayUtils.contains(arrayList2, string2);
        return bl;
    }

    static <T> ArrayList<T> prefix(ArrayList<T> arrayList, T t) {
        ArrayList<Object> arrayList2 = arrayList;
        if (arrayList == null) {
            arrayList2 = new ArrayList();
        }
        arrayList2.add(0, t);
        return arrayList2;
    }

    static void removeLibrary(PackageParser.Package package_, String string2) {
        package_.usesLibraries = ArrayUtils.remove(package_.usesLibraries, string2);
        package_.usesOptionalLibraries = ArrayUtils.remove(package_.usesOptionalLibraries, string2);
    }

    void prefixImplicitDependency(PackageParser.Package package_, String string2, String string3) {
        ArrayList<String> arrayList = package_.usesLibraries;
        ArrayList<String> arrayList2 = package_.usesOptionalLibraries;
        if (!PackageSharedLibraryUpdater.isLibraryPresent(arrayList, arrayList2, string3)) {
            if (ArrayUtils.contains(arrayList, string2)) {
                PackageSharedLibraryUpdater.prefix(arrayList, string3);
            } else if (ArrayUtils.contains(arrayList2, string2)) {
                PackageSharedLibraryUpdater.prefix(arrayList2, string3);
            }
            package_.usesLibraries = arrayList;
            package_.usesOptionalLibraries = arrayList2;
        }
    }

    void prefixRequiredLibrary(PackageParser.Package package_, String string2) {
        ArrayList<String> arrayList = package_.usesLibraries;
        if (!PackageSharedLibraryUpdater.isLibraryPresent(arrayList, package_.usesOptionalLibraries, string2)) {
            package_.usesLibraries = PackageSharedLibraryUpdater.prefix(arrayList, string2);
        }
    }

    public abstract void updatePackage(PackageParser.Package var1);
}

