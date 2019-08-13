/*
 * Decompiled with CFR 0.145.
 */
package android.content.om;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class OverlayInfo
implements Parcelable {
    public static final String CATEGORY_THEME = "android.theme";
    public static final Parcelable.Creator<OverlayInfo> CREATOR = new Parcelable.Creator<OverlayInfo>(){

        @Override
        public OverlayInfo createFromParcel(Parcel parcel) {
            return new OverlayInfo(parcel);
        }

        public OverlayInfo[] newArray(int n) {
            return new OverlayInfo[n];
        }
    };
    public static final int STATE_DISABLED = 2;
    public static final int STATE_ENABLED = 3;
    public static final int STATE_ENABLED_STATIC = 6;
    public static final int STATE_MISSING_TARGET = 0;
    public static final int STATE_NO_IDMAP = 1;
    public static final int STATE_OVERLAY_IS_BEING_REPLACED = 5;
    @Deprecated
    public static final int STATE_TARGET_IS_BEING_REPLACED = 4;
    public static final int STATE_UNKNOWN = -1;
    public final String baseCodePath;
    public final String category;
    public final boolean isStatic;
    public final String packageName;
    public final int priority;
    @UnsupportedAppUsage
    public final int state;
    public final String targetOverlayableName;
    public final String targetPackageName;
    public final int userId;

    public OverlayInfo(OverlayInfo overlayInfo, int n) {
        this(overlayInfo.packageName, overlayInfo.targetPackageName, overlayInfo.targetOverlayableName, overlayInfo.category, overlayInfo.baseCodePath, n, overlayInfo.userId, overlayInfo.priority, overlayInfo.isStatic);
    }

    public OverlayInfo(Parcel parcel) {
        this.packageName = parcel.readString();
        this.targetPackageName = parcel.readString();
        this.targetOverlayableName = parcel.readString();
        this.category = parcel.readString();
        this.baseCodePath = parcel.readString();
        this.state = parcel.readInt();
        this.userId = parcel.readInt();
        this.priority = parcel.readInt();
        this.isStatic = parcel.readBoolean();
        this.ensureValidState();
    }

    public OverlayInfo(String string2, String string3, String string4, String string5, String string6, int n, int n2, int n3, boolean bl) {
        this.packageName = string2;
        this.targetPackageName = string3;
        this.targetOverlayableName = string4;
        this.category = string5;
        this.baseCodePath = string6;
        this.state = n;
        this.userId = n2;
        this.priority = n3;
        this.isStatic = bl;
        this.ensureValidState();
    }

    private void ensureValidState() {
        if (this.packageName != null) {
            if (this.targetPackageName != null) {
                if (this.baseCodePath != null) {
                    switch (this.state) {
                        default: {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("State ");
                            stringBuilder.append(this.state);
                            stringBuilder.append(" is not a valid state");
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                        case -1: 
                        case 0: 
                        case 1: 
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: 
                        case 6: 
                    }
                    return;
                }
                throw new IllegalArgumentException("baseCodePath must not be null");
            }
            throw new IllegalArgumentException("targetPackageName must not be null");
        }
        throw new IllegalArgumentException("packageName must not be null");
    }

    public static String stateToString(int n) {
        switch (n) {
            default: {
                return "<unknown state>";
            }
            case 6: {
                return "STATE_ENABLED_STATIC";
            }
            case 5: {
                return "STATE_OVERLAY_IS_BEING_REPLACED";
            }
            case 4: {
                return "STATE_TARGET_IS_BEING_REPLACED";
            }
            case 3: {
                return "STATE_ENABLED";
            }
            case 2: {
                return "STATE_DISABLED";
            }
            case 1: {
                return "STATE_NO_IDMAP";
            }
            case 0: {
                return "STATE_MISSING_TARGET";
            }
            case -1: 
        }
        return "STATE_UNKNOWN";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (OverlayInfo)object;
        if (this.userId != ((OverlayInfo)object).userId) {
            return false;
        }
        if (this.state != ((OverlayInfo)object).state) {
            return false;
        }
        if (!this.packageName.equals(((OverlayInfo)object).packageName)) {
            return false;
        }
        if (!this.targetPackageName.equals(((OverlayInfo)object).targetPackageName)) {
            return false;
        }
        if (!Objects.equals(this.targetOverlayableName, ((OverlayInfo)object).targetOverlayableName)) {
            return false;
        }
        if (!Objects.equals(this.category, ((OverlayInfo)object).category)) {
            return false;
        }
        return this.baseCodePath.equals(((OverlayInfo)object).baseCodePath);
    }

    @SystemApi
    public String getCategory() {
        return this.category;
    }

    @SystemApi
    public String getPackageName() {
        return this.packageName;
    }

    @SystemApi
    public String getTargetOverlayableName() {
        return this.targetOverlayableName;
    }

    @SystemApi
    public String getTargetPackageName() {
        return this.targetPackageName;
    }

    @SystemApi
    public int getUserId() {
        return this.userId;
    }

    public int hashCode() {
        int n = this.userId;
        int n2 = this.state;
        String string2 = this.packageName;
        int n3 = 0;
        int n4 = string2 == null ? 0 : string2.hashCode();
        string2 = this.targetPackageName;
        int n5 = string2 == null ? 0 : string2.hashCode();
        string2 = this.targetOverlayableName;
        int n6 = string2 == null ? 0 : string2.hashCode();
        string2 = this.category;
        int n7 = string2 == null ? 0 : string2.hashCode();
        string2 = this.baseCodePath;
        if (string2 != null) {
            n3 = string2.hashCode();
        }
        return ((((((1 * 31 + n) * 31 + n2) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7) * 31 + n3;
    }

    @SystemApi
    public boolean isEnabled() {
        int n = this.state;
        return n == 3 || n == 6;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("OverlayInfo { overlay=");
        stringBuilder.append(this.packageName);
        stringBuilder.append(", targetPackage=");
        stringBuilder.append(this.targetPackageName);
        if (this.targetOverlayableName == null) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(", targetOverlayable=");
            ((StringBuilder)charSequence).append(this.targetOverlayableName);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(", state=");
        stringBuilder.append(this.state);
        stringBuilder.append(" (");
        stringBuilder.append(OverlayInfo.stateToString(this.state));
        stringBuilder.append("), userId=");
        stringBuilder.append(this.userId);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.targetPackageName);
        parcel.writeString(this.targetOverlayableName);
        parcel.writeString(this.category);
        parcel.writeString(this.baseCodePath);
        parcel.writeInt(this.state);
        parcel.writeInt(this.userId);
        parcel.writeInt(this.priority);
        parcel.writeBoolean(this.isStatic);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

