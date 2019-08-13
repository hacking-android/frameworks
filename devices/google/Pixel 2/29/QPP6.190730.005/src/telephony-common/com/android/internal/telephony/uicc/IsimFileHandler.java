/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc;

import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.IccConstants;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.UiccCardApplication;

public final class IsimFileHandler
extends IccFileHandler
implements IccConstants {
    static final String LOG_TAG = "IsimFH";

    public IsimFileHandler(UiccCardApplication uiccCardApplication, String string, CommandsInterface commandsInterface) {
        super(uiccCardApplication, string, commandsInterface);
    }

    @Override
    protected String getEFPath(int n) {
        if (n != 28423 && n != 28425) {
            switch (n) {
                default: {
                    return this.getCommonIccEFPath(n);
                }
                case 28418: 
                case 28419: 
                case 28420: 
            }
        }
        return "3F007FFF";
    }

    @Override
    protected void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @Override
    protected void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }
}

