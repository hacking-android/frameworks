/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.PackageParser;
import android.content.pm.VersionedPackage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SharedLibraryInfo
implements Parcelable {
    public static final Parcelable.Creator<SharedLibraryInfo> CREATOR = new Parcelable.Creator<SharedLibraryInfo>(){

        @Override
        public SharedLibraryInfo createFromParcel(Parcel parcel) {
            return new SharedLibraryInfo(parcel);
        }

        public SharedLibraryInfo[] newArray(int n) {
            return new SharedLibraryInfo[n];
        }
    };
    public static final int TYPE_BUILTIN = 0;
    public static final int TYPE_DYNAMIC = 1;
    public static final int TYPE_STATIC = 2;
    public static final int VERSION_UNDEFINED = -1;
    private final List<String> mCodePaths;
    private final VersionedPackage mDeclaringPackage;
    private List<SharedLibraryInfo> mDependencies;
    private final List<VersionedPackage> mDependentPackages;
    private final String mName;
    private final String mPackageName;
    private final String mPath;
    private final int mType;
    private final long mVersion;

    private SharedLibraryInfo(Parcel parcel) {
        this(parcel.readString(), parcel.readString(), parcel.readArrayList(null), parcel.readString(), parcel.readLong(), parcel.readInt(), (VersionedPackage)parcel.readParcelable(null), parcel.readArrayList(null), parcel.createTypedArrayList(CREATOR));
    }

    public SharedLibraryInfo(String string2, String string3, List<String> list, String string4, long l, int n, VersionedPackage versionedPackage, List<VersionedPackage> list2, List<SharedLibraryInfo> list3) {
        this.mPath = string2;
        this.mPackageName = string3;
        this.mCodePaths = list;
        this.mName = string4;
        this.mVersion = l;
        this.mType = n;
        this.mDeclaringPackage = versionedPackage;
        this.mDependentPackages = list2;
        this.mDependencies = list3;
    }

    public static SharedLibraryInfo createForDynamic(PackageParser.Package package_, String string2) {
        return new SharedLibraryInfo(null, package_.packageName, package_.getAllCodePaths(), string2, -1L, 1, new VersionedPackage(package_.packageName, package_.getLongVersionCode()), null, null);
    }

    public static SharedLibraryInfo createForStatic(PackageParser.Package package_) {
        return new SharedLibraryInfo(null, package_.packageName, package_.getAllCodePaths(), package_.staticSharedLibName, package_.staticSharedLibVersion, 2, new VersionedPackage(package_.manifestPackageName, package_.getLongVersionCode()), null, null);
    }

    private static String typeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "unknown";
                }
                return "static";
            }
            return "dynamic";
        }
        return "builtin";
    }

    public void addDependency(SharedLibraryInfo sharedLibraryInfo) {
        if (sharedLibraryInfo == null) {
            return;
        }
        if (this.mDependencies == null) {
            this.mDependencies = new ArrayList<SharedLibraryInfo>();
        }
        this.mDependencies.add(sharedLibraryInfo);
    }

    public void clearDependencies() {
        this.mDependencies = null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<String> getAllCodePaths() {
        if (this.getPath() != null) {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add(this.getPath());
            return arrayList;
        }
        return this.mCodePaths;
    }

    public VersionedPackage getDeclaringPackage() {
        return this.mDeclaringPackage;
    }

    public List<SharedLibraryInfo> getDependencies() {
        return this.mDependencies;
    }

    public List<VersionedPackage> getDependentPackages() {
        List<VersionedPackage> list = this.mDependentPackages;
        if (list == null) {
            return Collections.emptyList();
        }
        return list;
    }

    public long getLongVersion() {
        return this.mVersion;
    }

    public String getName() {
        return this.mName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getPath() {
        return this.mPath;
    }

    public int getType() {
        return this.mType;
    }

    @Deprecated
    public int getVersion() {
        long l = this.mVersion;
        if (l >= 0L) {
            l &= Integer.MAX_VALUE;
        }
        return (int)l;
    }

    public boolean isBuiltin() {
        boolean bl = this.mType == 0;
        return bl;
    }

    public boolean isDynamic() {
        int n = this.mType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isStatic() {
        boolean bl = this.mType == 2;
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SharedLibraryInfo{name:");
        stringBuilder.append(this.mName);
        stringBuilder.append(", type:");
        stringBuilder.append(SharedLibraryInfo.typeToString(this.mType));
        stringBuilder.append(", version:");
        stringBuilder.append(this.mVersion);
        String string2 = !this.getDependentPackages().isEmpty() ? " has dependents" : "";
        stringBuilder.append(string2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mPath);
        parcel.writeString(this.mPackageName);
        parcel.writeList(this.mCodePaths);
        parcel.writeString(this.mName);
        parcel.writeLong(this.mVersion);
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mDeclaringPackage, n);
        parcel.writeList(this.mDependentPackages);
        parcel.writeTypedList(this.mDependencies);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface Type {
    }

}

