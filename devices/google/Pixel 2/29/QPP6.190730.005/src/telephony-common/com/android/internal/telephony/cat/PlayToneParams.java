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
import com.android.internal.telephony.cat.Duration;
import com.android.internal.telephony.cat.TextMessage;
import com.android.internal.telephony.cat.Tone;
import com.android.internal.telephony.cat.ToneSettings;

class PlayToneParams
extends CommandParams {
    ToneSettings mSettings;
    TextMessage mTextMsg;

    @UnsupportedAppUsage
    PlayToneParams(CommandDetails commandDetails, TextMessage textMessage, Tone tone, Duration duration, boolean bl) {
        super(commandDetails);
        this.mTextMsg = textMessage;
        this.mSettings = new ToneSettings(duration, tone, bl);
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

