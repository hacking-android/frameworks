/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Message
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc.euicc.apdu;

import android.os.AsyncResult;
import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.euicc.async.AsyncMessageInvocation;

class CloseLogicalChannelInvocation
extends AsyncMessageInvocation<Integer, Boolean> {
    private static final String LOG_TAG = "CloseChan";
    private final CommandsInterface mCi;

    CloseLogicalChannelInvocation(CommandsInterface commandsInterface) {
        this.mCi = commandsInterface;
    }

    @Override
    protected Boolean parseResult(AsyncResult asyncResult) {
        if (asyncResult.exception == null) {
            return true;
        }
        if (asyncResult.exception instanceof CommandException) {
            Rlog.e((String)LOG_TAG, (String)"CommandException", (Throwable)asyncResult.exception);
        } else {
            Rlog.e((String)LOG_TAG, (String)"Unknown exception", (Throwable)asyncResult.exception);
        }
        return false;
    }

    @Override
    protected void sendRequestMessage(Integer n, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Channel: ");
        stringBuilder.append(n);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        this.mCi.iccCloseLogicalChannel(n, message);
    }
}

