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

public final class CsimFileHandler
extends IccFileHandler
implements IccConstants {
    static final String LOG_TAG = "CsimFH";

    public CsimFileHandler(UiccCardApplication uiccCardApplication, String string, CommandsInterface commandsInterface) {
        super(uiccCardApplication, string, commandsInterface);
    }

    @Override
    protected String getEFPath(int n) {
        if (n != 28450 && n != 28456 && n != 28466 && n != 28484 && n != 28493 && n != 28506 && n != 28480 && n != 28481) {
            switch (n) {
                default: {
                    String string = this.getCommonIccEFPath(n);
                    if (string == null) {
                        return "3F007F105F3A";
                    }
                    return string;
                }
                case 28474: 
                case 28475: 
                case 28476: 
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

