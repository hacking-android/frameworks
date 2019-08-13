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
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.TextMessage;

class DisplayTextParams
extends CommandParams {
    @UnsupportedAppUsage
    TextMessage mTextMsg;

    @UnsupportedAppUsage
    DisplayTextParams(CommandDetails commandDetails, TextMessage textMessage) {
        super(commandDetails);
        this.mTextMsg = textMessage;
    }

    @Override
    boolean setIcon(Bitmap bitmap) {
        TextMessage textMessage;
        if (bitmap != null && (textMessage = this.mTextMsg) != null) {
            textMessage.icon = bitmap;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TextMessage=");
        stringBuilder.append(this.mTextMsg);
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

