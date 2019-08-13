/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

public class VoiceSpecificRegistrationInfo
implements Parcelable {
    public static final Parcelable.Creator<VoiceSpecificRegistrationInfo> CREATOR = new Parcelable.Creator<VoiceSpecificRegistrationInfo>(){

        @Override
        public VoiceSpecificRegistrationInfo createFromParcel(Parcel parcel) {
            return new VoiceSpecificRegistrationInfo(parcel);
        }

        public VoiceSpecificRegistrationInfo[] newArray(int n) {
            return new VoiceSpecificRegistrationInfo[n];
        }
    };
    public final boolean cssSupported;
    public final int defaultRoamingIndicator;
    public final int roamingIndicator;
    public final int systemIsInPrl;

    private VoiceSpecificRegistrationInfo(Parcel parcel) {
        this.cssSupported = parcel.readBoolean();
        this.roamingIndicator = parcel.readInt();
        this.systemIsInPrl = parcel.readInt();
        this.defaultRoamingIndicator = parcel.readInt();
    }

    VoiceSpecificRegistrationInfo(VoiceSpecificRegistrationInfo voiceSpecificRegistrationInfo) {
        this.cssSupported = voiceSpecificRegistrationInfo.cssSupported;
        this.roamingIndicator = voiceSpecificRegistrationInfo.roamingIndicator;
        this.systemIsInPrl = voiceSpecificRegistrationInfo.systemIsInPrl;
        this.defaultRoamingIndicator = voiceSpecificRegistrationInfo.defaultRoamingIndicator;
    }

    VoiceSpecificRegistrationInfo(boolean bl, int n, int n2, int n3) {
        this.cssSupported = bl;
        this.roamingIndicator = n;
        this.systemIsInPrl = n2;
        this.defaultRoamingIndicator = n3;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof VoiceSpecificRegistrationInfo) {
            object = (VoiceSpecificRegistrationInfo)object;
            if (this.cssSupported != ((VoiceSpecificRegistrationInfo)object).cssSupported || this.roamingIndicator != ((VoiceSpecificRegistrationInfo)object).roamingIndicator || this.systemIsInPrl != ((VoiceSpecificRegistrationInfo)object).systemIsInPrl || this.defaultRoamingIndicator != ((VoiceSpecificRegistrationInfo)object).defaultRoamingIndicator) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.cssSupported, this.roamingIndicator, this.systemIsInPrl, this.defaultRoamingIndicator);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VoiceSpecificRegistrationInfo { mCssSupported=");
        stringBuilder.append(this.cssSupported);
        stringBuilder.append(" mRoamingIndicator=");
        stringBuilder.append(this.roamingIndicator);
        stringBuilder.append(" mSystemIsInPrl=");
        stringBuilder.append(this.systemIsInPrl);
        stringBuilder.append(" mDefaultRoamingIndicator=");
        stringBuilder.append(this.defaultRoamingIndicator);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBoolean(this.cssSupported);
        parcel.writeInt(this.roamingIndicator);
        parcel.writeInt(this.systemIsInPrl);
        parcel.writeInt(this.defaultRoamingIndicator);
    }

}

