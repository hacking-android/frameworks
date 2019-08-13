/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import android.os.UserManager;

public class UserInfo
implements Parcelable {
    @UnsupportedAppUsage
    public static final Parcelable.Creator<UserInfo> CREATOR = new Parcelable.Creator<UserInfo>(){

        @Override
        public UserInfo createFromParcel(Parcel parcel) {
            return new UserInfo(parcel);
        }

        public UserInfo[] newArray(int n) {
            return new UserInfo[n];
        }
    };
    public static final int FLAG_ADMIN = 2;
    public static final int FLAG_DEMO = 512;
    public static final int FLAG_DISABLED = 64;
    public static final int FLAG_EPHEMERAL = 256;
    public static final int FLAG_GUEST = 4;
    public static final int FLAG_INITIALIZED = 16;
    public static final int FLAG_MANAGED_PROFILE = 32;
    public static final int FLAG_MASK_USER_TYPE = 65535;
    @UnsupportedAppUsage
    public static final int FLAG_PRIMARY = 1;
    public static final int FLAG_QUIET_MODE = 128;
    public static final int FLAG_RESTRICTED = 8;
    public static final int NO_PROFILE_GROUP_ID = -10000;
    @UnsupportedAppUsage
    public long creationTime;
    @UnsupportedAppUsage
    public int flags;
    @UnsupportedAppUsage
    public boolean guestToRemove;
    @UnsupportedAppUsage
    public String iconPath;
    @UnsupportedAppUsage
    public int id;
    public String lastLoggedInFingerprint;
    @UnsupportedAppUsage
    public long lastLoggedInTime;
    @UnsupportedAppUsage
    public String name;
    @UnsupportedAppUsage
    public boolean partial;
    public int profileBadge;
    @UnsupportedAppUsage
    public int profileGroupId;
    public int restrictedProfileParentId;
    @UnsupportedAppUsage
    public int serialNumber;

    public UserInfo() {
    }

    @UnsupportedAppUsage
    public UserInfo(int n, String string2, int n2) {
        this(n, string2, null, n2);
    }

    @UnsupportedAppUsage
    public UserInfo(int n, String string2, String string3, int n2) {
        this.id = n;
        this.name = string2;
        this.flags = n2;
        this.iconPath = string3;
        this.profileGroupId = -10000;
        this.restrictedProfileParentId = -10000;
    }

    public UserInfo(UserInfo userInfo) {
        this.name = userInfo.name;
        this.iconPath = userInfo.iconPath;
        this.id = userInfo.id;
        this.flags = userInfo.flags;
        this.serialNumber = userInfo.serialNumber;
        this.creationTime = userInfo.creationTime;
        this.lastLoggedInTime = userInfo.lastLoggedInTime;
        this.lastLoggedInFingerprint = userInfo.lastLoggedInFingerprint;
        this.partial = userInfo.partial;
        this.profileGroupId = userInfo.profileGroupId;
        this.restrictedProfileParentId = userInfo.restrictedProfileParentId;
        this.guestToRemove = userInfo.guestToRemove;
        this.profileBadge = userInfo.profileBadge;
    }

    private UserInfo(Parcel parcel) {
        this.id = parcel.readInt();
        this.name = parcel.readString();
        this.iconPath = parcel.readString();
        this.flags = parcel.readInt();
        this.serialNumber = parcel.readInt();
        this.creationTime = parcel.readLong();
        this.lastLoggedInTime = parcel.readLong();
        this.lastLoggedInFingerprint = parcel.readString();
        int n = parcel.readInt();
        boolean bl = true;
        boolean bl2 = n != 0;
        this.partial = bl2;
        this.profileGroupId = parcel.readInt();
        bl2 = parcel.readInt() != 0 ? bl : false;
        this.guestToRemove = bl2;
        this.restrictedProfileParentId = parcel.readInt();
        this.profileBadge = parcel.readInt();
    }

    public static boolean isSystemOnly(int n) {
        boolean bl = n == 0 && UserManager.isSplitSystemUser();
        return bl;
    }

    public boolean canHaveProfile() {
        boolean bl = this.isManagedProfile();
        boolean bl2 = false;
        boolean bl3 = false;
        if (!(bl || this.isGuest() || this.isRestricted())) {
            if (UserManager.isSplitSystemUser()) {
                if (this.id != 0) {
                    bl3 = true;
                }
                return bl3;
            }
            bl3 = bl2;
            if (this.id == 0) {
                bl3 = true;
            }
            return bl3;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public UserHandle getUserHandle() {
        return new UserHandle(this.id);
    }

    @UnsupportedAppUsage
    public boolean isAdmin() {
        boolean bl = (this.flags & 2) == 2;
        return bl;
    }

    public boolean isDemo() {
        boolean bl = (this.flags & 512) == 512;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isEnabled() {
        boolean bl = (this.flags & 64) != 64;
        return bl;
    }

    public boolean isEphemeral() {
        boolean bl = (this.flags & 256) == 256;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isGuest() {
        boolean bl = (this.flags & 4) == 4;
        return bl;
    }

    public boolean isInitialized() {
        boolean bl = (this.flags & 16) == 16;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isManagedProfile() {
        boolean bl = (this.flags & 32) == 32;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isPrimary() {
        int n = this.flags;
        boolean bl = true;
        if ((n & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isQuietModeEnabled() {
        boolean bl = (this.flags & 128) == 128;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isRestricted() {
        boolean bl = (this.flags & 8) == 8;
        return bl;
    }

    public boolean isSystemOnly() {
        return UserInfo.isSystemOnly(this.id);
    }

    public boolean supportsSwitchTo() {
        if (this.isEphemeral() && !this.isEnabled()) {
            return false;
        }
        return this.isManagedProfile() ^ true;
    }

    public boolean supportsSwitchToByUser() {
        boolean bl = (!UserManager.isSplitSystemUser() || this.id != 0) && this.supportsSwitchTo();
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserInfo{");
        stringBuilder.append(this.id);
        stringBuilder.append(":");
        stringBuilder.append(this.name);
        stringBuilder.append(":");
        stringBuilder.append(Integer.toHexString(this.flags));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.iconPath);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.serialNumber);
        parcel.writeLong(this.creationTime);
        parcel.writeLong(this.lastLoggedInTime);
        parcel.writeString(this.lastLoggedInFingerprint);
        parcel.writeInt((int)this.partial);
        parcel.writeInt(this.profileGroupId);
        parcel.writeInt((int)this.guestToRemove);
        parcel.writeInt(this.restrictedProfileParentId);
        parcel.writeInt(this.profileBadge);
    }

}

