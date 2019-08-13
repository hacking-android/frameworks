/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.util.Pair
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 *  com.android.internal.telephony.gsm.SmsMessage
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.gsm;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.util.Pair;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SMSDispatcher;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.gsm.GsmInboundSmsHandler;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.util.SMSDispatcherUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

public final class GsmSMSDispatcher
extends SMSDispatcher {
    private static final int EVENT_NEW_SMS_STATUS_REPORT = 100;
    private static final String TAG = "GsmSMSDispatcher";
    private GsmInboundSmsHandler mGsmInboundSmsHandler;
    private AtomicReference<IccRecords> mIccRecords = new AtomicReference();
    private AtomicReference<UiccCardApplication> mUiccApplication = new AtomicReference();
    protected UiccController mUiccController = null;

    public GsmSMSDispatcher(Phone phone, SmsDispatchersController smsDispatchersController, GsmInboundSmsHandler gsmInboundSmsHandler) {
        super(phone, smsDispatchersController);
        this.mCi.setOnSmsStatus(this, 100, null);
        this.mGsmInboundSmsHandler = gsmInboundSmsHandler;
        this.mUiccController = UiccController.getInstance();
        this.mUiccController.registerForIccChanged(this, 15, null);
        Rlog.d((String)TAG, (String)"GsmSMSDispatcher created");
    }

    private void handleStatusReport(AsyncResult arrby) {
        arrby = (byte[])arrby.result;
        Object object = SmsMessage.newFromCDS((byte[])arrby);
        if (object != null) {
            int n = object.mMessageRef;
            int n2 = this.deliveryPendingList.size();
            for (int i = 0; i < n2; ++i) {
                object = (SMSDispatcher.SmsTracker)this.deliveryPendingList.get(i);
                if (object.mMessageRef != n) continue;
                if (!((Boolean)this.mSmsDispatchersController.handleSmsStatusReport((SMSDispatcher.SmsTracker)object, (String)this.getFormat(), (byte[])arrby).second).booleanValue()) break;
                this.deliveryPendingList.remove(i);
                break;
            }
        }
        this.mCi.acknowledgeLastIncomingGsmSms(true, 1, null);
    }

    private void onUpdateIccAvailability() {
        if (this.mUiccController == null) {
            return;
        }
        UiccCardApplication uiccCardApplication = this.getUiccCardApplication();
        UiccCardApplication uiccCardApplication2 = this.mUiccApplication.get();
        if (uiccCardApplication2 != uiccCardApplication) {
            if (uiccCardApplication2 != null) {
                Rlog.d((String)TAG, (String)"Removing stale icc objects.");
                if (this.mIccRecords.get() != null) {
                    this.mIccRecords.get().unregisterForNewSms(this);
                }
                this.mIccRecords.set(null);
                this.mUiccApplication.set(null);
            }
            if (uiccCardApplication != null) {
                Rlog.d((String)TAG, (String)"New Uicc application found");
                this.mUiccApplication.set(uiccCardApplication);
                this.mIccRecords.set(uiccCardApplication.getIccRecords());
                if (this.mIccRecords.get() != null) {
                    this.mIccRecords.get().registerForNewSms(this, 14, null);
                }
            }
        }
    }

    @Override
    protected GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean bl) {
        return SMSDispatcherUtil.calculateLengthGsm(charSequence, bl);
    }

    @Override
    public void dispose() {
        super.dispose();
        this.mCi.unSetOnSmsStatus(this);
        this.mUiccController.unregisterForIccChanged(this);
    }

    @Override
    protected String getFormat() {
        return "3gpp";
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, int n, byte[] arrby, boolean bl) {
        return SMSDispatcherUtil.getSubmitPduGsm(string, string2, n, arrby, bl);
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, String string3, boolean bl, SmsHeader smsHeader, int n, int n2) {
        return SMSDispatcherUtil.getSubmitPduGsm(string, string2, string3, bl, n2);
    }

    protected UiccCardApplication getUiccCardApplication() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GsmSMSDispatcher: subId = ");
        stringBuilder.append(this.mPhone.getSubId());
        stringBuilder.append(" slotId = ");
        stringBuilder.append(this.mPhone.getPhoneId());
        Rlog.d((String)TAG, (String)stringBuilder.toString());
        return this.mUiccController.getUiccCardApplication(this.mPhone.getPhoneId(), 1);
    }

    @Override
    public void handleMessage(Message message) {
        int n = message.what;
        if (n != 14) {
            if (n != 15) {
                if (n != 100) {
                    super.handleMessage(message);
                } else {
                    this.handleStatusReport((AsyncResult)message.obj);
                }
            } else {
                this.onUpdateIccAvailability();
            }
        } else {
            this.mGsmInboundSmsHandler.sendMessage(1, message.obj);
        }
    }

    @Override
    protected void sendSms(SMSDispatcher.SmsTracker smsTracker) {
        StringBuilder stringBuilder;
        byte[] arrby = smsTracker.getData();
        byte[] arrby2 = (byte[])arrby.get("pdu");
        if (smsTracker.mRetryCount > 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("sendSms:  mRetryCount=");
            stringBuilder.append(smsTracker.mRetryCount);
            stringBuilder.append(" mMessageRef=");
            stringBuilder.append(smsTracker.mMessageRef);
            stringBuilder.append(" SS=");
            stringBuilder.append(this.mPhone.getServiceState().getState());
            Rlog.d((String)TAG, (String)stringBuilder.toString());
            if ((arrby2[0] & 1) == 1) {
                arrby2[0] = (byte)(arrby2[0] | 4);
                arrby2[1] = (byte)smsTracker.mMessageRef;
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("sendSms:  isIms()=");
        stringBuilder.append(this.isIms());
        stringBuilder.append(" mRetryCount=");
        stringBuilder.append(smsTracker.mRetryCount);
        stringBuilder.append(" mImsRetry=");
        stringBuilder.append(smsTracker.mImsRetry);
        stringBuilder.append(" mMessageRef=");
        stringBuilder.append(smsTracker.mMessageRef);
        stringBuilder.append(" mUsesImsServiceForIms=");
        stringBuilder.append(smsTracker.mUsesImsServiceForIms);
        stringBuilder.append(" SS=");
        stringBuilder.append(this.mPhone.getServiceState().getState());
        Rlog.d((String)TAG, (String)stringBuilder.toString());
        int n = this.mPhone.getServiceState().getState();
        if (!this.isIms() && n != 0) {
            smsTracker.onFailed(this.mContext, GsmSMSDispatcher.getNotInServiceError(n), 0);
            return;
        }
        arrby = (byte[])arrby.get("smsc");
        stringBuilder = this.obtainMessage(2, (Object)smsTracker);
        if (smsTracker.mImsRetry == 0 && !this.isIms() || smsTracker.mUsesImsServiceForIms) {
            if (smsTracker.mRetryCount == 0 && smsTracker.mExpectMore) {
                this.mCi.sendSMSExpectMore(IccUtils.bytesToHexString((byte[])arrby), IccUtils.bytesToHexString((byte[])arrby2), (Message)stringBuilder);
            } else {
                this.mCi.sendSMS(IccUtils.bytesToHexString((byte[])arrby), IccUtils.bytesToHexString((byte[])arrby2), (Message)stringBuilder);
            }
        } else {
            this.mCi.sendImsGsmSms(IccUtils.bytesToHexString((byte[])arrby), IccUtils.bytesToHexString((byte[])arrby2), smsTracker.mImsRetry, smsTracker.mMessageRef, (Message)stringBuilder);
            ++smsTracker.mImsRetry;
        }
    }

    @Override
    protected boolean shouldBlockSmsForEcbm() {
        return false;
    }
}

