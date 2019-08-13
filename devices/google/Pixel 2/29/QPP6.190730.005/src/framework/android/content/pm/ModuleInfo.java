/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public final class ModuleInfo
implements Parcelable {
    public static final Parcelable.Creator<ModuleInfo> CREATOR = new Parcelable.Creator<ModuleInfo>(){

        @Override
        public ModuleInfo createFromParcel(Parcel parcel) {
            return new ModuleInfo(parcel);
        }

        public ModuleInfo[] newArray(int n) {
            return new ModuleInfo[n];
        }
    };
    private boolean mHidden;
    private CharSequence mName;
    private String mPackageName;

    public ModuleInfo() {
    }

    public ModuleInfo(ModuleInfo moduleInfo) {
        this.mName = moduleInfo.mName;
        this.mPackageName = moduleInfo.mPackageName;
        this.mHidden = moduleInfo.mHidden;
    }

    private ModuleInfo(Parcel parcel) {
        this.mName = parcel.readCharSequence();
        this.mPackageName = parcel.readString();
        this.mHidden = parcel.readBoolean();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof ModuleInfo;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (ModuleInfo)object;
            if (!Objects.equals(this.mName, ((ModuleInfo)object).mName) || !Objects.equals(this.mPackageName, ((ModuleInfo)object).mPackageName) || this.mHidden != ((ModuleInfo)object).mHidden) break block1;
            bl = true;
        }
        return bl;
    }

    public CharSequence getName() {
        return this.mName;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public int hashCode() {
        return ((0 * 31 + Objects.hashCode(this.mName)) * 31 + Objects.hashCode(this.mPackageName)) * 31 + Boolean.hashCode(this.mHidden);
    }

    public boolean isHidden() {
        return this.mHidden;
    }

    public ModuleInfo setHidden(boolean bl) {
        this.mHidden = bl;
        return this;
    }

    public ModuleInfo setName(CharSequence charSequence) {
        this.mName = charSequence;
        return this;
    }

    public ModuleInfo setPackageName(String string2) {
        this.mPackageName = string2;
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ModuleInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" ");
        stringBuilder.append((Object)this.mName);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeCharSequence(this.mName);
        parcel.writeString(this.mPackageName);
        parcel.writeBoolean(this.mHidden);
    }

}

