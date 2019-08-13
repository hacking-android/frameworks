/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 */
package com.android.internal.telephony.cat;

import android.graphics.Bitmap;
import com.android.internal.telephony.cat.CommandDetails;
import com.android.internal.telephony.cat.CommandParams;
import com.android.internal.telephony.cat.LaunchBrowserMode;
import com.android.internal.telephony.cat.TextMessage;

class LaunchBrowserParams
extends CommandParams {
    TextMessage mConfirmMsg;
    LaunchBrowserMode mMode;
    String mUrl;

    LaunchBrowserParams(CommandDetails commandDetails, TextMessage textMessage, String string, LaunchBrowserMode launchBrowserMode) {
        super(commandDetails);
        this.mConfirmMsg = textMessage;
        this.mMode = launchBrowserMode;
        this.mUrl = string;
    }

    @Override
    boolean setIcon(Bitmap bitmap) {
        TextMessage textMessage;
        if (bitmap != null && (textMessage = this.mConfirmMsg) != null) {
            textMessage.icon = bitmap;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TextMessage=");
        stringBuilder.append(this.mConfirmMsg);
        stringBuilder.append(" ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

