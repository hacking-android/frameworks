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

class BIPClientParams
extends CommandParams {
    boolean mHasAlphaId;
    TextMessage mTextMsg;

    BIPClientParams(CommandDetails commandDetails, TextMessage textMessage, boolean bl) {
        super(commandDetails);
        this.mTextMsg = textMessage;
        this.mHasAlphaId = bl;
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
}

