/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PermissionGroupInfo
extends PackageItemInfo
implements Parcelable {
    public static final Parcelable.Creator<PermissionGroupInfo> CREATOR = new Parcelable.Creator<PermissionGroupInfo>(){

        @Override
        public PermissionGroupInfo createFromParcel(Parcel parcel) {
            return new PermissionGroupInfo(parcel);
        }

        public PermissionGroupInfo[] newArray(int n) {
            return new PermissionGroupInfo[n];
        }
    };
    public static final int FLAG_PERSONAL_INFO = 1;
    @SystemApi
    public final int backgroundRequestDetailResourceId;
    @SystemApi
    public final int backgroundRequestResourceId;
    public int descriptionRes;
    public int flags;
    public CharSequence nonLocalizedDescription;
    public int priority;
    @SystemApi
    public final int requestDetailResourceId;
    @SystemApi
    public int requestRes;

    @Deprecated
    public PermissionGroupInfo() {
        this(0, 0, 0);
    }

    public PermissionGroupInfo(int n, int n2, int n3) {
        this.requestDetailResourceId = n;
        this.backgroundRequestResourceId = n2;
        this.backgroundRequestDetailResourceId = n3;
    }

    @Deprecated
    public PermissionGroupInfo(PermissionGroupInfo permissionGroupInfo) {
        super(permissionGroupInfo);
        this.descriptionRes = permissionGroupInfo.descriptionRes;
        this.requestRes = permissionGroupInfo.requestRes;
        this.requestDetailResourceId = permissionGroupInfo.requestDetailResourceId;
        this.backgroundRequestResourceId = permissionGroupInfo.backgroundRequestResourceId;
        this.backgroundRequestDetailResourceId = permissionGroupInfo.backgroundRequestDetailResourceId;
        this.nonLocalizedDescription = permissionGroupInfo.nonLocalizedDescription;
        this.flags = permissionGroupInfo.flags;
        this.priority = permissionGroupInfo.priority;
    }

    private PermissionGroupInfo(Parcel parcel) {
        super(parcel);
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.requestDetailResourceId = parcel.readInt();
        this.backgroundRequestResourceId = parcel.readInt();
        this.backgroundRequestDetailResourceId = parcel.readInt();
        this.nonLocalizedDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.flags = parcel.readInt();
        this.priority = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence loadDescription(PackageManager object) {
        CharSequence charSequence = this.nonLocalizedDescription;
        if (charSequence != null) {
            return charSequence;
        }
        if (this.descriptionRes != 0 && (object = ((PackageManager)object).getText(this.packageName, this.descriptionRes, null)) != null) {
            return object;
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PermissionGroupInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.name);
        stringBuilder.append(" flgs=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        parcel.writeInt(this.requestDetailResourceId);
        parcel.writeInt(this.backgroundRequestResourceId);
        parcel.writeInt(this.backgroundRequestDetailResourceId);
        TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, n);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.priority);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

}

