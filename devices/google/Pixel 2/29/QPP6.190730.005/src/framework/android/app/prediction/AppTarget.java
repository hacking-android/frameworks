/*
 * Decompiled with CFR 0.145.
 */
package android.app.prediction;

import android.annotation.SystemApi;
import android.app.prediction.AppTargetId;
import android.content.pm.ShortcutInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.UserHandle;
import com.android.internal.util.Preconditions;

@SystemApi
public final class AppTarget
implements Parcelable {
    public static final Parcelable.Creator<AppTarget> CREATOR = new Parcelable.Creator<AppTarget>(){

        @Override
        public AppTarget createFromParcel(Parcel parcel) {
            return new AppTarget(parcel);
        }

        public AppTarget[] newArray(int n) {
            return new AppTarget[n];
        }
    };
    private final String mClassName;
    private final AppTargetId mId;
    private final String mPackageName;
    private final int mRank;
    private final ShortcutInfo mShortcutInfo;
    private final UserHandle mUser;

    @Deprecated
    public AppTarget(AppTargetId appTargetId, ShortcutInfo shortcutInfo, String string2) {
        this.mId = appTargetId;
        this.mShortcutInfo = Preconditions.checkNotNull(shortcutInfo);
        this.mPackageName = this.mShortcutInfo.getPackage();
        this.mUser = this.mShortcutInfo.getUserHandle();
        this.mClassName = string2;
        this.mRank = 0;
    }

    private AppTarget(AppTargetId appTargetId, String string2, UserHandle userHandle, ShortcutInfo shortcutInfo, String string3, int n) {
        this.mId = appTargetId;
        this.mShortcutInfo = shortcutInfo;
        this.mPackageName = string2;
        this.mClassName = string3;
        this.mUser = userHandle;
        this.mRank = n;
    }

    @Deprecated
    public AppTarget(AppTargetId appTargetId, String string2, String string3, UserHandle userHandle) {
        this.mId = appTargetId;
        this.mShortcutInfo = null;
        this.mPackageName = Preconditions.checkNotNull(string2);
        this.mClassName = string3;
        this.mUser = Preconditions.checkNotNull(userHandle);
        this.mRank = 0;
    }

    private AppTarget(Parcel parcel) {
        this.mId = parcel.readTypedObject(AppTargetId.CREATOR);
        ShortcutInfo shortcutInfo = this.mShortcutInfo = parcel.readTypedObject(ShortcutInfo.CREATOR);
        if (shortcutInfo == null) {
            this.mPackageName = parcel.readString();
            this.mUser = UserHandle.of(parcel.readInt());
        } else {
            this.mPackageName = shortcutInfo.getPackage();
            this.mUser = this.mShortcutInfo.getUserHandle();
        }
        this.mClassName = parcel.readString();
        this.mRank = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            Class<?> class_ = this.getClass();
            Class<?> class_2 = object != null ? object.getClass() : null;
            boolean bl2 = class_.equals(class_2);
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (AppTarget)object;
            boolean bl3 = this.mClassName == null && ((AppTarget)object).mClassName == null || (class_2 = this.mClassName) != null && ((String)((Object)class_2)).equals(((AppTarget)object).mClassName);
            boolean bl4 = this.mShortcutInfo == null && ((AppTarget)object).mShortcutInfo == null || (class_2 = this.mShortcutInfo) != null && ((AppTarget)object).mShortcutInfo != null && ((ShortcutInfo)((Object)class_2)).getId() == ((AppTarget)object).mShortcutInfo.getId();
            if (!this.mId.equals(((AppTarget)object).mId) || !this.mPackageName.equals(((AppTarget)object).mPackageName) || !bl3 || !this.mUser.equals(((AppTarget)object).mUser) || !bl4 || this.mRank != ((AppTarget)object).mRank) break block1;
            bl = true;
        }
        return bl;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public AppTargetId getId() {
        return this.mId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int getRank() {
        return this.mRank;
    }

    public ShortcutInfo getShortcutInfo() {
        return this.mShortcutInfo;
    }

    public UserHandle getUser() {
        return this.mUser;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedObject(this.mId, n);
        parcel.writeTypedObject(this.mShortcutInfo, n);
        if (this.mShortcutInfo == null) {
            parcel.writeString(this.mPackageName);
            parcel.writeInt(this.mUser.getIdentifier());
        }
        parcel.writeString(this.mClassName);
        parcel.writeInt(this.mRank);
    }

    @SystemApi
    public static final class Builder {
        private String mClassName;
        private final AppTargetId mId;
        private String mPackageName;
        private int mRank;
        private ShortcutInfo mShortcutInfo;
        private UserHandle mUser;

        @SystemApi
        @Deprecated
        public Builder(AppTargetId appTargetId) {
            this.mId = appTargetId;
        }

        @SystemApi
        public Builder(AppTargetId appTargetId, ShortcutInfo shortcutInfo) {
            this.mId = Preconditions.checkNotNull(appTargetId);
            this.mShortcutInfo = Preconditions.checkNotNull(shortcutInfo);
            this.mPackageName = shortcutInfo.getPackage();
            this.mUser = shortcutInfo.getUserHandle();
        }

        @SystemApi
        public Builder(AppTargetId appTargetId, String string2, UserHandle userHandle) {
            this.mId = Preconditions.checkNotNull(appTargetId);
            this.mPackageName = Preconditions.checkNotNull(string2);
            this.mUser = Preconditions.checkNotNull(userHandle);
        }

        public AppTarget build() {
            String string2 = this.mPackageName;
            if (string2 != null) {
                return new AppTarget(this.mId, string2, this.mUser, this.mShortcutInfo, this.mClassName, this.mRank);
            }
            throw new IllegalStateException("No target is set");
        }

        public Builder setClassName(String string2) {
            this.mClassName = Preconditions.checkNotNull(string2);
            return this;
        }

        public Builder setRank(int n) {
            if (n >= 0) {
                this.mRank = n;
                return this;
            }
            throw new IllegalArgumentException("rank cannot be a negative value");
        }

        @Deprecated
        public Builder setTarget(ShortcutInfo shortcutInfo) {
            this.setTarget(shortcutInfo.getPackage(), shortcutInfo.getUserHandle());
            this.mShortcutInfo = Preconditions.checkNotNull(shortcutInfo);
            return this;
        }

        @Deprecated
        public Builder setTarget(String string2, UserHandle userHandle) {
            if (this.mPackageName == null) {
                this.mPackageName = Preconditions.checkNotNull(string2);
                this.mUser = Preconditions.checkNotNull(userHandle);
                return this;
            }
            throw new IllegalArgumentException("Target is already set");
        }
    }

}

