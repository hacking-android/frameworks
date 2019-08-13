/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.pm.ComponentInfo;
import android.content.pm.PathPermission;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.PatternMatcher;
import android.util.Printer;

public final class ProviderInfo
extends ComponentInfo
implements Parcelable {
    public static final Parcelable.Creator<ProviderInfo> CREATOR = new Parcelable.Creator<ProviderInfo>(){

        @Override
        public ProviderInfo createFromParcel(Parcel parcel) {
            return new ProviderInfo(parcel);
        }

        public ProviderInfo[] newArray(int n) {
            return new ProviderInfo[n];
        }
    };
    public static final int FLAG_SINGLE_USER = 1073741824;
    public static final int FLAG_VISIBLE_TO_INSTANT_APP = 1048576;
    public String authority = null;
    public int flags;
    public boolean forceUriPermissions;
    public boolean grantUriPermissions;
    public int initOrder;
    @Deprecated
    public boolean isSyncable;
    public boolean multiprocess;
    public PathPermission[] pathPermissions;
    public String readPermission = null;
    public PatternMatcher[] uriPermissionPatterns;
    public String writePermission = null;

    public ProviderInfo() {
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
    }

    public ProviderInfo(ProviderInfo providerInfo) {
        super(providerInfo);
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
        this.authority = providerInfo.authority;
        this.readPermission = providerInfo.readPermission;
        this.writePermission = providerInfo.writePermission;
        this.grantUriPermissions = providerInfo.grantUriPermissions;
        this.forceUriPermissions = providerInfo.forceUriPermissions;
        this.uriPermissionPatterns = providerInfo.uriPermissionPatterns;
        this.pathPermissions = providerInfo.pathPermissions;
        this.multiprocess = providerInfo.multiprocess;
        this.initOrder = providerInfo.initOrder;
        this.flags = providerInfo.flags;
        this.isSyncable = providerInfo.isSyncable;
    }

    private ProviderInfo(Parcel parcel) {
        super(parcel);
        boolean bl = false;
        this.grantUriPermissions = false;
        this.forceUriPermissions = false;
        this.uriPermissionPatterns = null;
        this.pathPermissions = null;
        this.multiprocess = false;
        this.initOrder = 0;
        this.flags = 0;
        this.isSyncable = false;
        this.authority = parcel.readString();
        this.readPermission = parcel.readString();
        this.writePermission = parcel.readString();
        boolean bl2 = parcel.readInt() != 0;
        this.grantUriPermissions = bl2;
        bl2 = parcel.readInt() != 0;
        this.forceUriPermissions = bl2;
        this.uriPermissionPatterns = parcel.createTypedArray(PatternMatcher.CREATOR);
        this.pathPermissions = parcel.createTypedArray(PathPermission.CREATOR);
        bl2 = parcel.readInt() != 0;
        this.multiprocess = bl2;
        this.initOrder = parcel.readInt();
        this.flags = parcel.readInt();
        bl2 = bl;
        if (parcel.readInt() != 0) {
            bl2 = true;
        }
        this.isSyncable = bl2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        this.dump(printer, string2, 3);
    }

    public void dump(Printer printer, String string2, int n) {
        super.dumpFront(printer, string2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("authority=");
        stringBuilder.append(this.authority);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("flags=0x");
        stringBuilder.append(Integer.toHexString(this.flags));
        printer.println(stringBuilder.toString());
        super.dumpBack(printer, string2, n);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ContentProviderInfo{name=");
        stringBuilder.append(this.authority);
        stringBuilder.append(" className=");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeString(this.authority);
        parcel.writeString(this.readPermission);
        parcel.writeString(this.writePermission);
        parcel.writeInt((int)this.grantUriPermissions);
        parcel.writeInt((int)this.forceUriPermissions);
        parcel.writeTypedArray((Parcelable[])this.uriPermissionPatterns, n);
        parcel.writeTypedArray((Parcelable[])this.pathPermissions, n);
        parcel.writeInt((int)this.multiprocess);
        parcel.writeInt(this.initOrder);
        parcel.writeInt(this.flags);
        parcel.writeInt((int)this.isSyncable);
    }

}

