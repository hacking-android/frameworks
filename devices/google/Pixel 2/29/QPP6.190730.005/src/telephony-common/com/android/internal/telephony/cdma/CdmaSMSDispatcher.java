/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.util.Pair
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.cdma.SmsMessage
 */
package com.android.internal.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.util.Pair;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.GsmCdmaCallTracker;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.SMSDispatcher;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.util.SMSDispatcherUtil;
import java.util.ArrayList;
import java.util.HashMap;

public class CdmaSMSDispatcher
extends SMSDispatcher {
    private static final String TAG = "CdmaSMSDispatcher";
    private static final boolean VDBG = false;

    public CdmaSMSDispatcher(Phone phone, SmsDispatchersController smsDispatchersController) {
        super(phone, smsDispatchersController);
        Rlog.d((String)TAG, (String)"CdmaSMSDispatcher created");
    }

    @UnsupportedAppUsage
    private void handleCdmaStatusReport(SmsMessage smsMessage) {
        int n = this.deliveryPendingList.size();
        for (int i = 0; i < n; ++i) {
            SMSDispatcher.SmsTracker smsTracker = (SMSDispatcher.SmsTracker)this.deliveryPendingList.get(i);
            if (smsTracker.mMessageRef != smsMessage.mMessageRef) continue;
            if (!((Boolean)this.mSmsDispatchersController.handleSmsStatusReport((SMSDispatcher.SmsTracker)smsTracker, (String)this.getFormat(), (byte[])smsMessage.getPdu()).second).booleanValue()) break;
            this.deliveryPendingList.remove(i);
            break;
        }
    }

    @Override
    protected GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean bl) {
        return SMSDispatcherUtil.calculateLengthCdma(charSequence, bl);
    }

    @UnsupportedAppUsage
    @Override
    public String getFormat() {
        return "3gpp2";
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, int n, byte[] arrby, boolean bl) {
        return SMSDispatcherUtil.getSubmitPduCdma(string, string2, n, arrby, bl);
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, String string3, boolean bl, SmsHeader smsHeader, int n, int n2) {
        return SMSDispatcherUtil.getSubmitPduCdma(string, string2, string3, bl, smsHeader, n);
    }

    @Override
    protected void handleStatusReport(Object object) {
        if (object instanceof SmsMessage) {
            this.handleCdmaStatusReport((SmsMessage)object);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleStatusReport() called for object type ");
            stringBuilder.append(object.getClass().getName());
            Rlog.e((String)TAG, (String)stringBuilder.toString());
        }
    }

    @Override
    public void sendSms(SMSDispatcher.SmsTracker smsTracker) {
        byte[] arrby = new StringBuilder();
        arrby.append("sendSms:  isIms()=");
        arrby.append(this.isIms());
        arrby.append(" mRetryCount=");
        arrby.append(smsTracker.mRetryCount);
        arrby.append(" mImsRetry=");
        arrby.append(smsTracker.mImsRetry);
        arrby.append(" mMessageRef=");
        arrby.append(smsTracker.mMessageRef);
        arrby.append(" mUsesImsServiceForIms=");
        arrby.append(smsTracker.mUsesImsServiceForIms);
        arrby.append(" SS=");
        arrby.append(this.mPhone.getServiceState().getState());
        Rlog.d((String)TAG, (String)arrby.toString());
        int n = this.mPhone.getServiceState().getState();
        boolean bl = this.isIms();
        boolean bl2 = false;
        if (!bl && n != 0) {
            smsTracker.onFailed(this.mContext, CdmaSMSDispatcher.getNotInServiceError(n), 0);
            return;
        }
        Message message = this.obtainMessage(2, (Object)smsTracker);
        arrby = (byte[])smsTracker.getData().get("pdu");
        n = this.mPhone.getServiceState().getDataNetworkType();
        if ((n == 14 || ServiceState.isLte((int)n) && !this.mPhone.getServiceStateTracker().isConcurrentVoiceAndDataAllowed()) && this.mPhone.getServiceState().getVoiceNetworkType() == 7 && ((GsmCdmaPhone)this.mPhone).mCT.mState != PhoneConstants.State.IDLE) {
            bl2 = true;
        }
        if (!(smsTracker.mImsRetry == 0 && !this.isIms() || bl2 || smsTracker.mUsesImsServiceForIms)) {
            this.mCi.sendImsCdmaSms(arrby, smsTracker.mImsRetry, smsTracker.mMessageRef, message);
            ++smsTracker.mImsRetry;
        } else {
            this.mCi.sendCdmaSms(arrby, message);
        }
    }

    public void sendStatusReportMessage(SmsMessage smsMessage) {
        this.sendMessage(this.obtainMessage(10, (Object)smsMessage));
    }

    @Override
    protected boolean shouldBlockSmsForEcbm() {
        boolean bl = this.mPhone.isInEcm() && this.isCdmaMo() && !this.isIms();
        return bl;
    }
}

