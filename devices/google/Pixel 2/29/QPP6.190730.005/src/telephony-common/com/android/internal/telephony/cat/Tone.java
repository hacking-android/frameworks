/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.os.Parcel;
import android.os.Parcelable;

public enum Tone implements Parcelable
{
    DIAL(1),
    BUSY(2),
    CONGESTION(3),
    RADIO_PATH_ACK(4),
    RADIO_PATH_NOT_AVAILABLE(5),
    ERROR_SPECIAL_INFO(6),
    CALL_WAITING(7),
    RINGING(8),
    GENERAL_BEEP(16),
    POSITIVE_ACK(17),
    NEGATIVE_ACK(18),
    INCOMING_SPEECH_CALL(19),
    INCOMING_SMS(20),
    CRITICAL_ALERT(21),
    VIBRATE_ONLY(32),
    HAPPY(48),
    SAD(49),
    URGENT(50),
    QUESTION(51),
    MESSAGE_RECEIVED(52),
    MELODY_1(64),
    MELODY_2(65),
    MELODY_3(66),
    MELODY_4(67),
    MELODY_5(68),
    MELODY_6(69),
    MELODY_7(70),
    MELODY_8(71);
    
    public static final Parcelable.Creator<Tone> CREATOR;
    private int mValue;

    static {
        CREATOR = new Parcelable.Creator<Tone>(){

            public Tone createFromParcel(Parcel parcel) {
                return Tone.values()[parcel.readInt()];
            }

            public Tone[] newArray(int n) {
                return new Tone[n];
            }
        };
    }

    private Tone(int n2) {
        this.mValue = n2;
    }

    private Tone(Parcel parcel) {
        this.mValue = parcel.readInt();
    }

    public static Tone fromInt(int n) {
        for (Tone tone : Tone.values()) {
            if (tone.mValue != n) continue;
            return tone;
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.ordinal());
    }

}

