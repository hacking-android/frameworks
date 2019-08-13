/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Handler
 *  android.os.Message
 *  com.android.internal.telephony.SmsConstants
 *  com.android.internal.telephony.SmsConstants$MessageClass
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsHeader$PortAddrs
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.gsm.SmsMessage
 */
package com.android.internal.telephony.gsm;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.android.internal.telephony.CellBroadcastHandler;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.InboundSmsHandler;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.VisualVoicemailSmsFilter;
import com.android.internal.telephony.gsm.GsmCellBroadcastHandler;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.gsm.UsimDataDownloadHandler;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.UsimServiceTable;

public class GsmInboundSmsHandler
extends InboundSmsHandler {
    private final UsimDataDownloadHandler mDataDownloadHandler;

    private GsmInboundSmsHandler(Context context, SmsStorageMonitor smsStorageMonitor, Phone phone) {
        super("GsmInboundSmsHandler", context, smsStorageMonitor, phone, GsmCellBroadcastHandler.makeGsmCellBroadcastHandler(context, phone));
        phone.mCi.setOnNewGsmSms(this.getHandler(), 1, null);
        this.mDataDownloadHandler = new UsimDataDownloadHandler(phone.mCi, phone.getPhoneId());
    }

    private void addSmsTypeZeroToMetrics() {
        this.mMetrics.writeIncomingSmsTypeZero(this.mPhone.getPhoneId(), "3gpp");
    }

    private void addVoicemailSmsToMetrics() {
        this.mMetrics.writeIncomingVoiceMailSms(this.mPhone.getPhoneId(), "3gpp");
    }

    public static GsmInboundSmsHandler makeInboundSmsHandler(Context object, SmsStorageMonitor smsStorageMonitor, Phone phone) {
        object = new GsmInboundSmsHandler((Context)object, smsStorageMonitor, phone);
        object.start();
        return object;
    }

    private static int resultToCause(int n) {
        if (n != -1 && n != 1) {
            if (n != 3) {
                return 255;
            }
            return 211;
        }
        return 0;
    }

    private void updateMessageWaitingIndicator(int n) {
        int n2;
        if (n < 0) {
            n2 = -1;
        } else {
            n2 = n;
            if (n > 255) {
                n2 = 255;
            }
        }
        this.mPhone.setVoiceMessageCount(n2);
    }

    @Override
    protected void acknowledgeLastIncomingSms(boolean bl, int n, Message message) {
        this.mPhone.mCi.acknowledgeLastIncomingGsmSms(bl, GsmInboundSmsHandler.resultToCause(n), message);
    }

    @Override
    protected int dispatchMessageRadioSpecific(SmsMessageBase object) {
        byte[] arrby = (byte[])object;
        if (arrby.isTypeZero()) {
            int n = -1;
            object = arrby.getUserDataHeader();
            int n2 = n;
            if (object != null) {
                n2 = n;
                if (object.portAddrs != null) {
                    n2 = object.portAddrs.destPort;
                }
            }
            object = this.mContext;
            arrby = arrby.getPdu();
            n = this.mPhone.getSubId();
            VisualVoicemailSmsFilter.filter((Context)object, new byte[][]{arrby}, "3gpp", n2, n);
            this.log("Received short message type 0, Don't display or store it. Send Ack");
            this.addSmsTypeZeroToMetrics();
            return 1;
        }
        if (arrby.isUsimDataDownload()) {
            object = this.mPhone.getUsimServiceTable();
            return this.mDataDownloadHandler.handleUsimDataDownload((UsimServiceTable)object, (SmsMessage)arrby);
        }
        boolean bl = false;
        if (arrby.isMWISetMessage()) {
            this.updateMessageWaitingIndicator(arrby.getNumOfVoicemails());
            bl = arrby.isMwiDontStore();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Received voice mail indicator set SMS shouldStore=");
            stringBuilder.append(bl ^ true);
            this.log(stringBuilder.toString());
        } else if (arrby.isMWIClearMessage()) {
            this.updateMessageWaitingIndicator(0);
            bl = arrby.isMwiDontStore();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Received voice mail indicator clear SMS shouldStore=");
            stringBuilder.append(bl ^ true);
            this.log(stringBuilder.toString());
        }
        if (bl) {
            this.addVoicemailSmsToMetrics();
            return 1;
        }
        if (!this.mStorageMonitor.isStorageAvailable() && arrby.getMessageClass() != SmsConstants.MessageClass.CLASS_0) {
            return 3;
        }
        return this.dispatchNormalMessage((SmsMessageBase)object);
    }

    @Override
    protected boolean is3gpp2() {
        return false;
    }

    @Override
    protected void onQuitting() {
        this.mPhone.mCi.unSetOnNewGsmSms(this.getHandler());
        this.mCellBroadcastHandler.dispose();
        this.log("unregistered for 3GPP SMS");
        super.onQuitting();
    }
}

