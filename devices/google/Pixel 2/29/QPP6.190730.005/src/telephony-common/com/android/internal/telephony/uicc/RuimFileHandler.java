/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Message
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc;

import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.UiccCardApplication;

public final class RuimFileHandler
extends IccFileHandler {
    static final String LOG_TAG = "RuimFH";

    public RuimFileHandler(UiccCardApplication uiccCardApplication, String string, CommandsInterface commandsInterface) {
        super(uiccCardApplication, string, commandsInterface);
    }

    @Override
    protected String getEFPath(int n) {
        if (n != 28450 && n != 28456 && n != 28466 && n != 28474 && n != 28476 && n != 28481 && n != 28484 && n != 28493 && n != 28506) {
            return this.getCommonIccEFPath(n);
        }
        return "3F007F25";
    }

    @Override
    public void loadEFImgTransparent(int n, int n2, int n3, int n4, Message message) {
        message = this.obtainMessage(10, n, 0, (Object)message);
        this.mCi.iccIOForApp(192, n, this.getEFPath(20256), 0, 0, 10, null, null, this.mAid, message);
    }

    @Override
    protected void logd(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RuimFileHandler] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @Override
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RuimFileHandler] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }
}

