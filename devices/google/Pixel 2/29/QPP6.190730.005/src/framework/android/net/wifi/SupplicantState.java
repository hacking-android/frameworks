/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Parcel;
import android.os.Parcelable;

public enum SupplicantState implements Parcelable
{
    DISCONNECTED,
    INTERFACE_DISABLED,
    INACTIVE,
    SCANNING,
    AUTHENTICATING,
    ASSOCIATING,
    ASSOCIATED,
    FOUR_WAY_HANDSHAKE,
    GROUP_HANDSHAKE,
    COMPLETED,
    DORMANT,
    UNINITIALIZED,
    INVALID;
    
    public static final Parcelable.Creator<SupplicantState> CREATOR;

    static {
        CREATOR = new Parcelable.Creator<SupplicantState>(){

            @Override
            public SupplicantState createFromParcel(Parcel parcel) {
                return SupplicantState.valueOf(parcel.readString());
            }

            public SupplicantState[] newArray(int n) {
                return new SupplicantState[n];
            }
        };
    }

    public static boolean isConnecting(SupplicantState supplicantState) {
        switch (supplicantState) {
            default: {
                throw new IllegalArgumentException("Unknown supplicant state");
            }
            case DISCONNECTED: 
            case INTERFACE_DISABLED: 
            case INACTIVE: 
            case SCANNING: 
            case DORMANT: 
            case UNINITIALIZED: 
            case INVALID: {
                return false;
            }
            case AUTHENTICATING: 
            case ASSOCIATING: 
            case ASSOCIATED: 
            case FOUR_WAY_HANDSHAKE: 
            case GROUP_HANDSHAKE: 
            case COMPLETED: 
        }
        return true;
    }

    public static boolean isDriverActive(SupplicantState supplicantState) {
        switch (supplicantState) {
            default: {
                throw new IllegalArgumentException("Unknown supplicant state");
            }
            case INTERFACE_DISABLED: 
            case UNINITIALIZED: 
            case INVALID: {
                return false;
            }
            case AUTHENTICATING: 
            case ASSOCIATING: 
            case ASSOCIATED: 
            case FOUR_WAY_HANDSHAKE: 
            case GROUP_HANDSHAKE: 
            case COMPLETED: 
            case DISCONNECTED: 
            case INACTIVE: 
            case SCANNING: 
            case DORMANT: 
        }
        return true;
    }

    public static boolean isHandshakeState(SupplicantState supplicantState) {
        switch (supplicantState) {
            default: {
                throw new IllegalArgumentException("Unknown supplicant state");
            }
            case COMPLETED: 
            case DISCONNECTED: 
            case INTERFACE_DISABLED: 
            case INACTIVE: 
            case SCANNING: 
            case DORMANT: 
            case UNINITIALIZED: 
            case INVALID: {
                return false;
            }
            case AUTHENTICATING: 
            case ASSOCIATING: 
            case ASSOCIATED: 
            case FOUR_WAY_HANDSHAKE: 
            case GROUP_HANDSHAKE: 
        }
        return true;
    }

    public static boolean isValidState(SupplicantState supplicantState) {
        boolean bl = supplicantState != UNINITIALIZED && supplicantState != INVALID;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.name());
    }

}

