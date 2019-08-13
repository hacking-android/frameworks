/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.cat.ValueObject;

class CommandDetails
extends ValueObject
implements Parcelable {
    public static final Parcelable.Creator<CommandDetails> CREATOR = new Parcelable.Creator<CommandDetails>(){

        public CommandDetails createFromParcel(Parcel parcel) {
            return new CommandDetails(parcel);
        }

        public CommandDetails[] newArray(int n) {
            return new CommandDetails[n];
        }
    };
    @UnsupportedAppUsage
    public int commandNumber;
    @UnsupportedAppUsage
    public int commandQualifier;
    @UnsupportedAppUsage
    public boolean compRequired;
    @UnsupportedAppUsage
    public int typeOfCommand;

    CommandDetails() {
    }

    public CommandDetails(Parcel parcel) {
        boolean bl = parcel.readInt() != 0;
        this.compRequired = bl;
        this.commandNumber = parcel.readInt();
        this.typeOfCommand = parcel.readInt();
        this.commandQualifier = parcel.readInt();
    }

    public boolean compareTo(CommandDetails commandDetails) {
        boolean bl = this.compRequired == commandDetails.compRequired && this.commandNumber == commandDetails.commandNumber && this.commandQualifier == commandDetails.commandQualifier && this.typeOfCommand == commandDetails.typeOfCommand;
        return bl;
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public ComprehensionTlvTag getTag() {
        return ComprehensionTlvTag.COMMAND_DETAILS;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CmdDetails: compRequired=");
        stringBuilder.append(this.compRequired);
        stringBuilder.append(" commandNumber=");
        stringBuilder.append(this.commandNumber);
        stringBuilder.append(" typeOfCommand=");
        stringBuilder.append(this.typeOfCommand);
        stringBuilder.append(" commandQualifier=");
        stringBuilder.append(this.commandQualifier);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.compRequired);
        parcel.writeInt(this.commandNumber);
        parcel.writeInt(this.typeOfCommand);
        parcel.writeInt(this.commandQualifier);
    }

}

