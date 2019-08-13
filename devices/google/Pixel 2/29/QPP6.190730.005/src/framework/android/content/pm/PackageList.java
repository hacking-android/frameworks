/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.PackageManagerInternal;
import com.android.server.LocalServices;
import java.util.List;

public class PackageList
implements PackageManagerInternal.PackageListObserver,
AutoCloseable {
    private final List<String> mPackageNames;
    private final PackageManagerInternal.PackageListObserver mWrappedObserver;

    public PackageList(List<String> list, PackageManagerInternal.PackageListObserver packageListObserver) {
        this.mPackageNames = list;
        this.mWrappedObserver = packageListObserver;
    }

    @Override
    public void close() throws Exception {
        LocalServices.getService(PackageManagerInternal.class).removePackageListObserver(this);
    }

    public List<String> getPackageNames() {
        return this.mPackageNames;
    }

    @Override
    public void onPackageAdded(String string2, int n) {
        PackageManagerInternal.PackageListObserver packageListObserver = this.mWrappedObserver;
        if (packageListObserver != null) {
            packageListObserver.onPackageAdded(string2, n);
        }
    }

    @Override
    public void onPackageRemoved(String string2, int n) {
        PackageManagerInternal.PackageListObserver packageListObserver = this.mWrappedObserver;
        if (packageListObserver != null) {
            packageListObserver.onPackageRemoved(string2, n);
        }
    }
}

