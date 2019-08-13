/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Message
 *  android.telephony.IccOpenLogicalChannelResponse
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc.euicc.apdu;

import android.os.AsyncResult;
import android.os.Message;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.uicc.euicc.async.AsyncMessageInvocation;

class OpenLogicalChannelInvocation
extends AsyncMessageInvocation<String, IccOpenLogicalChannelResponse> {
    private static final String LOG_TAG = "OpenChan";
    private final CommandsInterface mCi;

    OpenLogicalChannelInvocation(CommandsInterface commandsInterface) {
        this.mCi = commandsInterface;
    }

    @Override
    protected IccOpenLogicalChannelResponse parseResult(AsyncResult object) {
        Object object2;
        if (object.exception == null && object.result != null) {
            int[] arrn = (int[])object.result;
            int n = arrn[0];
            object = null;
            if (arrn.length > 1) {
                object2 = new byte[arrn.length - 1];
                int n2 = 1;
                do {
                    object = object2;
                    if (n2 >= arrn.length) break;
                    object2[n2 - 1] = (byte)arrn[n2];
                    ++n2;
                } while (true);
            }
            object = new IccOpenLogicalChannelResponse(n, 1, object);
        } else {
            int n;
            if (object.result == null) {
                Rlog.e((String)LOG_TAG, (String)"Empty response");
            }
            if (object.exception != null) {
                Rlog.e((String)LOG_TAG, (String)"Exception", (Throwable)object.exception);
            }
            int n3 = n = 4;
            if (object.exception instanceof CommandException) {
                object = ((CommandException)object.exception).getCommandError();
                if (object == CommandException.Error.MISSING_RESOURCE) {
                    n3 = 2;
                } else {
                    n3 = n;
                    if (object == CommandException.Error.NO_SUCH_ELEMENT) {
                        n3 = 3;
                    }
                }
            }
            object = new IccOpenLogicalChannelResponse(-1, n3, null);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Response: ");
        ((StringBuilder)object2).append(object);
        Rlog.v((String)LOG_TAG, (String)((StringBuilder)object2).toString());
        return object;
    }

    @Override
    protected void sendRequestMessage(String string, Message message) {
        this.mCi.iccOpenLogicalChannel(string, 0, message);
    }
}

