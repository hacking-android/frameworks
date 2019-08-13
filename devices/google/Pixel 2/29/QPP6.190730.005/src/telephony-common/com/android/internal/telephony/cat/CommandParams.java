/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.graphics.Bitmap
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import com.android.internal.telephony.cat.AppInterface;
import com.android.internal.telephony.cat.CommandDetails;

class CommandParams {
    @UnsupportedAppUsage
    CommandDetails mCmdDet;
    boolean mLoadIconFailed = false;

    @UnsupportedAppUsage
    CommandParams(CommandDetails commandDetails) {
        this.mCmdDet = commandDetails;
    }

    @UnsupportedAppUsage
    AppInterface.CommandType getCommandType() {
        return AppInterface.CommandType.fromInt(this.mCmdDet.typeOfCommand);
    }

    boolean setIcon(Bitmap bitmap) {
        return true;
    }

    public String toString() {
        return this.mCmdDet.toString();
    }
}

