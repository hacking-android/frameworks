/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

public class AudioRoutesInfo
implements Parcelable {
    public static final Parcelable.Creator<AudioRoutesInfo> CREATOR = new Parcelable.Creator<AudioRoutesInfo>(){

        @Override
        public AudioRoutesInfo createFromParcel(Parcel parcel) {
            return new AudioRoutesInfo(parcel);
        }

        public AudioRoutesInfo[] newArray(int n) {
            return new AudioRoutesInfo[n];
        }
    };
    public static final int MAIN_DOCK_SPEAKERS = 4;
    public static final int MAIN_HDMI = 8;
    public static final int MAIN_HEADPHONES = 2;
    public static final int MAIN_HEADSET = 1;
    public static final int MAIN_SPEAKER = 0;
    public static final int MAIN_USB = 16;
    public CharSequence bluetoothName;
    public int mainType = 0;

    public AudioRoutesInfo() {
    }

    public AudioRoutesInfo(AudioRoutesInfo audioRoutesInfo) {
        this.bluetoothName = audioRoutesInfo.bluetoothName;
        this.mainType = audioRoutesInfo.mainType;
    }

    AudioRoutesInfo(Parcel parcel) {
        this.bluetoothName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mainType = parcel.readInt();
    }

    private static String typeToString(int n) {
        if (n == 0) {
            return "SPEAKER";
        }
        if ((n & 1) != 0) {
            return "HEADSET";
        }
        if ((n & 2) != 0) {
            return "HEADPHONES";
        }
        if ((n & 4) != 0) {
            return "DOCK_SPEAKERS";
        }
        if ((n & 8) != 0) {
            return "HDMI";
        }
        if ((n & 16) != 0) {
            return "USB";
        }
        return Integer.toHexString(n);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        CharSequence charSequence;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append("{ type=");
        stringBuilder.append(AudioRoutesInfo.typeToString(this.mainType));
        if (TextUtils.isEmpty(this.bluetoothName)) {
            charSequence = "";
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(", bluetoothName=");
            ((StringBuilder)charSequence).append((Object)this.bluetoothName);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        TextUtils.writeToParcel(this.bluetoothName, parcel, n);
        parcel.writeInt(this.mainType);
    }

}

