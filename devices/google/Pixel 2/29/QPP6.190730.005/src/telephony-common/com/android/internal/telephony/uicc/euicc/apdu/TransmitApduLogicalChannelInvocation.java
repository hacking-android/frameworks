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
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.euicc.apdu.ApduCommand;
import com.android.internal.telephony.uicc.euicc.async.AsyncMessageInvocation;

public class TransmitApduLogicalChannelInvocation
extends AsyncMessageInvocation<ApduCommand, IccIoResult> {
    private static final String LOG_TAG = "TransApdu";
    private static final int SW1_ERROR = 111;
    private final CommandsInterface mCi;

    TransmitApduLogicalChannelInvocation(CommandsInterface commandsInterface) {
        this.mCi = commandsInterface;
    }

    @Override
    protected IccIoResult parseResult(AsyncResult object) {
        if (object.exception == null && object.result != null) {
            object = (IccIoResult)object.result;
        } else {
            if (object.result == null) {
                Rlog.e((String)LOG_TAG, (String)"Empty response");
            } else if (object.exception instanceof CommandException) {
                Rlog.e((String)LOG_TAG, (String)"CommandException", (Throwable)object.exception);
            } else {
                Rlog.e((String)LOG_TAG, (String)"CommandException", (Throwable)object.exception);
            }
            object = new IccIoResult(111, 0, (byte[])null);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Response: ");
        stringBuilder.append(object);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        return object;
    }

    @Override
    protected void sendRequestMessage(ApduCommand apduCommand, Message message) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Send: ");
        stringBuilder.append(apduCommand);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
        this.mCi.iccTransmitApduLogicalChannel(apduCommand.channel, apduCommand.cla | apduCommand.channel, apduCommand.ins, apduCommand.p1, apduCommand.p2, apduCommand.p3, apduCommand.cmdHex, message);
    }
}

