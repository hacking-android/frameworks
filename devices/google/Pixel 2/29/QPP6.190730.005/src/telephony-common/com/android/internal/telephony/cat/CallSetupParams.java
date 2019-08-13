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
import com.android.internal.telephony.cat.TextMessage;

class CallSetupParams
extends CommandParams {
    TextMessage mCallMsg;
    TextMessage mConfirmMsg;

    CallSetupParams(CommandDetails commandDetails, TextMessage textMessage, TextMessage textMessage2) {
        super(commandDetails);
        this.mConfirmMsg = textMessage;
        this.mCallMsg = textMessage2;
    }

    @Override
    boolean setIcon(Bitmap bitmap) {
        if (bitmap == null) {
            return false;
        }
        TextMessage textMessage = this.mConfirmMsg;
        if (textMessage != null && textMessage.icon == null) {
            this.mConfirmMsg.icon = bitmap;
            return true;
        }
        textMessage = this.mCallMsg;
        if (textMessage != null && textMessage.icon == null) {
            this.mCallMsg.icon = bitmap;
            return true;
        }
        return false;
    }
}

