/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class PermissionInfo
extends PackageItemInfo
implements Parcelable {
    public static final Parcelable.Creator<PermissionInfo> CREATOR = new Parcelable.Creator<PermissionInfo>(){

        @Override
        public PermissionInfo createFromParcel(Parcel parcel) {
            return new PermissionInfo(parcel);
        }

        public PermissionInfo[] newArray(int n) {
            return new PermissionInfo[n];
        }
    };
    public static final int FLAG_COSTS_MONEY = 1;
    public static final int FLAG_HARD_RESTRICTED = 4;
    public static final int FLAG_IMMUTABLY_RESTRICTED = 16;
    public static final int FLAG_INSTALLED = 1073741824;
    @SystemApi
    public static final int FLAG_REMOVED = 2;
    public static final int FLAG_SOFT_RESTRICTED = 8;
    public static final int PROTECTION_DANGEROUS = 1;
    public static final int PROTECTION_FLAG_APPOP = 64;
    @SystemApi
    public static final int PROTECTION_FLAG_APP_PREDICTOR = 2097152;
    @SystemApi
    public static final int PROTECTION_FLAG_CONFIGURATOR = 524288;
    public static final int PROTECTION_FLAG_DEVELOPMENT = 32;
    @SystemApi
    public static final int PROTECTION_FLAG_DOCUMENTER = 262144;
    @SystemApi
    public static final int PROTECTION_FLAG_INCIDENT_REPORT_APPROVER = 1048576;
    public static final int PROTECTION_FLAG_INSTALLER = 256;
    public static final int PROTECTION_FLAG_INSTANT = 4096;
    @SystemApi
    public static final int PROTECTION_FLAG_OEM = 16384;
    public static final int PROTECTION_FLAG_PRE23 = 128;
    public static final int PROTECTION_FLAG_PREINSTALLED = 1024;
    public static final int PROTECTION_FLAG_PRIVILEGED = 16;
    public static final int PROTECTION_FLAG_RUNTIME_ONLY = 8192;
    public static final int PROTECTION_FLAG_SETUP = 2048;
    @Deprecated
    public static final int PROTECTION_FLAG_SYSTEM = 16;
    @SystemApi
    public static final int PROTECTION_FLAG_SYSTEM_TEXT_CLASSIFIER = 65536;
    public static final int PROTECTION_FLAG_VENDOR_PRIVILEGED = 32768;
    public static final int PROTECTION_FLAG_VERIFIER = 512;
    @SystemApi
    public static final int PROTECTION_FLAG_WELLBEING = 131072;
    @Deprecated
    public static final int PROTECTION_MASK_BASE = 15;
    @Deprecated
    public static final int PROTECTION_MASK_FLAGS = 65520;
    public static final int PROTECTION_NORMAL = 0;
    public static final int PROTECTION_SIGNATURE = 2;
    @Deprecated
    public static final int PROTECTION_SIGNATURE_OR_SYSTEM = 3;
    @SystemApi
    public final String backgroundPermission;
    public int descriptionRes;
    public int flags;
    public String group;
    public CharSequence nonLocalizedDescription;
    @Deprecated
    public int protectionLevel;
    @SystemApi
    public int requestRes;

    @Deprecated
    public PermissionInfo() {
        this((String)null);
    }

    @Deprecated
    public PermissionInfo(PermissionInfo permissionInfo) {
        super(permissionInfo);
        this.protectionLevel = permissionInfo.protectionLevel;
        this.flags = permissionInfo.flags;
        this.group = permissionInfo.group;
        this.backgroundPermission = permissionInfo.backgroundPermission;
        this.descriptionRes = permissionInfo.descriptionRes;
        this.requestRes = permissionInfo.requestRes;
        this.nonLocalizedDescription = permissionInfo.nonLocalizedDescription;
    }

    private PermissionInfo(Parcel parcel) {
        super(parcel);
        this.protectionLevel = parcel.readInt();
        this.flags = parcel.readInt();
        this.group = parcel.readString();
        this.backgroundPermission = parcel.readString();
        this.descriptionRes = parcel.readInt();
        this.requestRes = parcel.readInt();
        this.nonLocalizedDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    public PermissionInfo(String string2) {
        this.backgroundPermission = string2;
    }

    public static int fixProtectionLevel(int n) {
        int n2 = n;
        if (n == 3) {
            n2 = 18;
        }
        n = n2;
        if ((32768 & n2) != 0) {
            n = n2;
            if ((n2 & 16) == 0) {
                n = n2 & -32769;
            }
        }
        return n;
    }

    @UnsupportedAppUsage
    public static String protectionToString(int n) {
        CharSequence charSequence = "????";
        int n2 = n & 15;
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3) {
                        charSequence = "signatureOrSystem";
                    }
                } else {
                    charSequence = "signature";
                }
            } else {
                charSequence = "dangerous";
            }
        } else {
            charSequence = "normal";
        }
        CharSequence charSequence2 = charSequence;
        if ((n & 16) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|privileged");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        CharSequence charSequence3 = charSequence2;
        if ((n & 32) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|development");
            charSequence3 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence3;
        if ((n & 64) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append("|appop");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((n & 128) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|pre23");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if ((n & 256) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|installer");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence3 = charSequence;
        if ((n & 512) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|verifier");
            charSequence3 = ((StringBuilder)charSequence2).toString();
        }
        charSequence2 = charSequence3;
        if ((n & 1024) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append("|preinstalled");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence2;
        if ((n & 2048) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|setup");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((n & 4096) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|instant");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if ((n & 8192) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|runtime");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((n & 16384) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|oem");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if ((32768 & n) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|vendorPrivileged");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((65536 & n) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|textClassifier");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if ((131072 & n) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|wellbeing");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((262144 & n) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|documenter");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence3 = charSequence2;
        if ((524288 & n) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("|configurator");
            charSequence3 = ((StringBuilder)charSequence).toString();
        }
        charSequence = charSequence3;
        if ((1048576 & n) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append("|incidentReportApprover");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = charSequence;
        if ((2097152 & n) != 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("|appPredictor");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    public int calculateFootprint() {
        int n;
        int n2 = n = this.name.length();
        if (this.nonLocalizedLabel != null) {
            n2 = n + this.nonLocalizedLabel.length();
        }
        CharSequence charSequence = this.nonLocalizedDescription;
        n = n2;
        if (charSequence != null) {
            n = n2 + charSequence.length();
        }
        return n;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getProtection() {
        return this.protectionLevel & 15;
    }

    public int getProtectionFlags() {
        return this.protectionLevel & -16;
    }

    public boolean isAppOp() {
        boolean bl = (this.protectionLevel & 64) != 0;
        return bl;
    }

    public boolean isHardRestricted() {
        boolean bl = (this.flags & 4) != 0;
        return bl;
    }

    public boolean isRestricted() {
        boolean bl = this.isHardRestricted() || this.isSoftRestricted();
        return bl;
    }

    public boolean isRuntime() {
        int n = this.getProtection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isSoftRestricted() {
        boolean bl = (this.flags & 8) != 0;
        return bl;
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
        stringBuilder.append("PermissionInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
        parcel.writeInt(this.protectionLevel);
        parcel.writeInt(this.flags);
        parcel.writeString(this.group);
        parcel.writeString(this.backgroundPermission);
        parcel.writeInt(this.descriptionRes);
        parcel.writeInt(this.requestRes);
        TextUtils.writeToParcel(this.nonLocalizedDescription, parcel, n);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Flags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Protection {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProtectionFlags {
    }

}

