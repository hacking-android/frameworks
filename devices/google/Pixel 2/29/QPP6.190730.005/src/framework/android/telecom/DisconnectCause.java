/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

public final class DisconnectCause
implements Parcelable {
    public static final int ANSWERED_ELSEWHERE = 11;
    public static final int BUSY = 7;
    public static final int CALL_PULLED = 12;
    public static final int CANCELED = 4;
    public static final int CONNECTION_MANAGER_NOT_SUPPORTED = 10;
    public static final Parcelable.Creator<DisconnectCause> CREATOR = new Parcelable.Creator<DisconnectCause>(){

        @Override
        public DisconnectCause createFromParcel(Parcel parcel) {
            return new DisconnectCause(parcel.readInt(), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel), parcel.readString(), parcel.readInt());
        }

        public DisconnectCause[] newArray(int n) {
            return new DisconnectCause[n];
        }
    };
    public static final int ERROR = 1;
    public static final int LOCAL = 2;
    public static final int MISSED = 5;
    public static final int OTHER = 9;
    public static final String REASON_EMULATING_SINGLE_CALL = "EMULATING_SINGLE_CALL";
    public static final String REASON_IMS_ACCESS_BLOCKED = "REASON_IMS_ACCESS_BLOCKED";
    public static final String REASON_WIFI_ON_BUT_WFC_OFF = "REASON_WIFI_ON_BUT_WFC_OFF";
    public static final int REJECTED = 6;
    public static final int REMOTE = 3;
    public static final int RESTRICTED = 8;
    public static final int UNKNOWN = 0;
    private int mDisconnectCode;
    private CharSequence mDisconnectDescription;
    private CharSequence mDisconnectLabel;
    private String mDisconnectReason;
    private int mToneToPlay;

    public DisconnectCause(int n) {
        this(n, null, null, null, -1);
    }

    public DisconnectCause(int n, CharSequence charSequence, CharSequence charSequence2, String string2) {
        this(n, charSequence, charSequence2, string2, -1);
    }

    public DisconnectCause(int n, CharSequence charSequence, CharSequence charSequence2, String string2, int n2) {
        this.mDisconnectCode = n;
        this.mDisconnectLabel = charSequence;
        this.mDisconnectDescription = charSequence2;
        this.mDisconnectReason = string2;
        this.mToneToPlay = n2;
    }

    public DisconnectCause(int n, String string2) {
        this(n, null, null, string2, -1);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DisconnectCause;
        boolean bl2 = false;
        if (bl) {
            object = (DisconnectCause)object;
            if (Objects.equals(this.mDisconnectCode, ((DisconnectCause)object).getCode()) && Objects.equals(this.mDisconnectLabel, ((DisconnectCause)object).getLabel()) && Objects.equals(this.mDisconnectDescription, ((DisconnectCause)object).getDescription()) && Objects.equals(this.mDisconnectReason, ((DisconnectCause)object).getReason()) && Objects.equals(this.mToneToPlay, ((DisconnectCause)object).getTone())) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public int getCode() {
        return this.mDisconnectCode;
    }

    public CharSequence getDescription() {
        return this.mDisconnectDescription;
    }

    public CharSequence getLabel() {
        return this.mDisconnectLabel;
    }

    public String getReason() {
        return this.mDisconnectReason;
    }

    public int getTone() {
        return this.mToneToPlay;
    }

    public int hashCode() {
        return Objects.hashCode(this.mDisconnectCode) + Objects.hashCode(this.mDisconnectLabel) + Objects.hashCode(this.mDisconnectDescription) + Objects.hashCode(this.mDisconnectReason) + Objects.hashCode(this.mToneToPlay);
    }

    public String toString() {
        CharSequence charSequence;
        switch (this.mDisconnectCode) {
            default: {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("invalid code: ");
                ((StringBuilder)charSequence).append(this.mDisconnectCode);
                charSequence = ((StringBuilder)charSequence).toString();
                break;
            }
            case 12: {
                charSequence = "CALL_PULLED";
                break;
            }
            case 11: {
                charSequence = "ANSWERED_ELSEWHERE";
                break;
            }
            case 10: {
                charSequence = "CONNECTION_MANAGER_NOT_SUPPORTED";
                break;
            }
            case 9: {
                charSequence = "OTHER";
                break;
            }
            case 8: {
                charSequence = "RESTRICTED";
                break;
            }
            case 7: {
                charSequence = "BUSY";
                break;
            }
            case 6: {
                charSequence = "REJECTED";
                break;
            }
            case 5: {
                charSequence = "MISSED";
                break;
            }
            case 4: {
                charSequence = "CANCELED";
                break;
            }
            case 3: {
                charSequence = "REMOTE";
                break;
            }
            case 2: {
                charSequence = "LOCAL";
                break;
            }
            case 1: {
                charSequence = "ERROR";
                break;
            }
            case 0: {
                charSequence = "UNKNOWN";
            }
        }
        CharSequence charSequence2 = this.mDisconnectLabel;
        String string2 = "";
        charSequence2 = charSequence2 == null ? "" : charSequence2.toString();
        CharSequence charSequence3 = this.mDisconnectDescription;
        charSequence3 = charSequence3 == null ? "" : charSequence3.toString();
        CharSequence charSequence4 = this.mDisconnectReason;
        if (charSequence4 != null) {
            string2 = charSequence4;
        }
        charSequence4 = new StringBuilder();
        ((StringBuilder)charSequence4).append("DisconnectCause [ Code: (");
        ((StringBuilder)charSequence4).append((String)charSequence);
        ((StringBuilder)charSequence4).append(") Label: (");
        ((StringBuilder)charSequence4).append((String)charSequence2);
        ((StringBuilder)charSequence4).append(") Description: (");
        ((StringBuilder)charSequence4).append((String)charSequence3);
        ((StringBuilder)charSequence4).append(") Reason: (");
        ((StringBuilder)charSequence4).append(string2);
        ((StringBuilder)charSequence4).append(") Tone: (");
        ((StringBuilder)charSequence4).append(this.mToneToPlay);
        ((StringBuilder)charSequence4).append(") ]");
        return ((StringBuilder)charSequence4).toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mDisconnectCode);
        TextUtils.writeToParcel(this.mDisconnectLabel, parcel, n);
        TextUtils.writeToParcel(this.mDisconnectDescription, parcel, n);
        parcel.writeString(this.mDisconnectReason);
        parcel.writeInt(this.mToneToPlay);
    }

}

