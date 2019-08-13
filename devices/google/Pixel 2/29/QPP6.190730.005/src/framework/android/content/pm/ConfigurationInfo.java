/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.os.Parcel;
import android.os.Parcelable;

public class ConfigurationInfo
implements Parcelable {
    public static final Parcelable.Creator<ConfigurationInfo> CREATOR = new Parcelable.Creator<ConfigurationInfo>(){

        @Override
        public ConfigurationInfo createFromParcel(Parcel parcel) {
            return new ConfigurationInfo(parcel);
        }

        public ConfigurationInfo[] newArray(int n) {
            return new ConfigurationInfo[n];
        }
    };
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public static final int INPUT_FEATURE_FIVE_WAY_NAV = 2;
    public static final int INPUT_FEATURE_HARD_KEYBOARD = 1;
    public int reqGlEsVersion;
    public int reqInputFeatures = 0;
    public int reqKeyboardType;
    public int reqNavigation;
    public int reqTouchScreen;

    public ConfigurationInfo() {
    }

    public ConfigurationInfo(ConfigurationInfo configurationInfo) {
        this.reqTouchScreen = configurationInfo.reqTouchScreen;
        this.reqKeyboardType = configurationInfo.reqKeyboardType;
        this.reqNavigation = configurationInfo.reqNavigation;
        this.reqInputFeatures = configurationInfo.reqInputFeatures;
        this.reqGlEsVersion = configurationInfo.reqGlEsVersion;
    }

    private ConfigurationInfo(Parcel parcel) {
        this.reqTouchScreen = parcel.readInt();
        this.reqKeyboardType = parcel.readInt();
        this.reqNavigation = parcel.readInt();
        this.reqInputFeatures = parcel.readInt();
        this.reqGlEsVersion = parcel.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getGlEsVersion() {
        int n = this.reqGlEsVersion;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.valueOf((-65536 & n) >> 16));
        stringBuilder.append(".");
        stringBuilder.append(String.valueOf(n & 65535));
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ConfigurationInfo{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" touchscreen = ");
        stringBuilder.append(this.reqTouchScreen);
        stringBuilder.append(" inputMethod = ");
        stringBuilder.append(this.reqKeyboardType);
        stringBuilder.append(" navigation = ");
        stringBuilder.append(this.reqNavigation);
        stringBuilder.append(" reqInputFeatures = ");
        stringBuilder.append(this.reqInputFeatures);
        stringBuilder.append(" reqGlEsVersion = ");
        stringBuilder.append(this.reqGlEsVersion);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.reqTouchScreen);
        parcel.writeInt(this.reqKeyboardType);
        parcel.writeInt(this.reqNavigation);
        parcel.writeInt(this.reqInputFeatures);
        parcel.writeInt(this.reqGlEsVersion);
    }

}

