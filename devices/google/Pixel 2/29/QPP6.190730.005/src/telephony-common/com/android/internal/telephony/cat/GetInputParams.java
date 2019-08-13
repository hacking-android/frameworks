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
import com.android.internal.telephony.cat.Input;

class GetInputParams
extends CommandParams {
    Input mInput = null;

    @UnsupportedAppUsage
    GetInputParams(CommandDetails commandDetails, Input input) {
        super(commandDetails);
        this.mInput = input;
    }

    @Override
    boolean setIcon(Bitmap bitmap) {
        Input input;
        if (bitmap != null && (input = this.mInput) != null) {
            input.icon = bitmap;
        }
        return true;
    }
}

