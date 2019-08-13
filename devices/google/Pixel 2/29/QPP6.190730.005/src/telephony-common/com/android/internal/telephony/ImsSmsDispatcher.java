/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Binder
 *  android.os.PersistableBundle
 *  android.os.RemoteException
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SmsMessage
 *  android.telephony.ims.ImsMmTelManager
 *  android.telephony.ims.ImsMmTelManager$CapabilityCallback
 *  android.telephony.ims.ImsMmTelManager$RegistrationCallback
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.aidl.IImsSmsListener
 *  android.telephony.ims.aidl.IImsSmsListener$Stub
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.util.Pair
 *  com.android.ims.ImsException
 *  com.android.ims.ImsManager
 *  com.android.ims.ImsManager$Connector
 *  com.android.ims.ImsManager$Connector$Listener
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.GsmAlphabet$TextEncodingDetails
 *  com.android.internal.telephony.SmsHeader
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.SmsMessageBase$SubmitPduBase
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.Binder;
import android.os.PersistableBundle;
import android.os.RemoteException;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SmsMessage;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.aidl.IImsSmsListener;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Pair;
import com.android.ims.ImsException;
import com.android.ims.ImsManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SMSDispatcher;
import com.android.internal.telephony.SmsDispatchersController;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony._$$Lambda$ImsSmsDispatcher$3$q7JFSZBuWsj_jBm5R51WxdJYNxc;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.util.SMSDispatcherUtil;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ImsSmsDispatcher
extends SMSDispatcher {
    private static final String TAG = "ImsSmsDispacher";
    private ImsMmTelManager.CapabilityCallback mCapabilityCallback = new ImsMmTelManager.CapabilityCallback(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onCapabilitiesStatusChanged(MmTelFeature.MmTelCapabilities mmTelCapabilities) {
            Object object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.mIsSmsCapable = mmTelCapabilities.isCapable(8);
                return;
            }
        }
    };
    private final ImsManager.Connector mImsManagerConnector = new ImsManager.Connector(this.mContext, this.mPhone.getPhoneId(), new ImsManager.Connector.Listener(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void connectionReady(ImsManager object) throws ImsException {
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)"ImsManager: connection ready.");
            object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.setListeners();
                ImsSmsDispatcher.this.mIsImsServiceUp = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void connectionUnavailable() {
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)"ImsManager: connection unavailable.");
            Object object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.mIsImsServiceUp = false;
                return;
            }
        }
    });
    private final IImsSmsListener mImsSmsListener = new IImsSmsListener.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public /* synthetic */ void lambda$onSmsReceived$0$ImsSmsDispatcher$3(SmsMessage object, int n, int n2) {
            ImsException imsException2;
            block4 : {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("SMS handled result: ");
                stringBuilder.append(n2);
                Rlog.d((String)ImsSmsDispatcher.TAG, (String)stringBuilder.toString());
                n2 = n2 != 1 ? (n2 != 3 ? (n2 != 4 ? 2 : 4) : 3) : 1;
                if (object != null) {
                    try {
                        if (((SmsMessage)object).mWrappedSmsMessage != null) {
                            ImsSmsDispatcher.this.getImsManager().acknowledgeSms(n, object.mWrappedSmsMessage.mMessageRef, n2);
                            return;
                        }
                    }
                    catch (ImsException imsException2) {
                        break block4;
                    }
                }
                Rlog.w((String)ImsSmsDispatcher.TAG, (String)"SMS Received with a PDU that could not be parsed.");
                ImsSmsDispatcher.this.getImsManager().acknowledgeSms(n, 0, n2);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Failed to acknowledgeSms(). Error: ");
            ((StringBuilder)object).append(imsException2.getMessage());
            Rlog.e((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
        }

        public void onSendSmsResult(int n, int n2, int n3, int n4) throws RemoteException {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onSendSmsResult token=");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" messageRef=");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" status=");
            ((StringBuilder)object).append(n3);
            ((StringBuilder)object).append(" reason=");
            ((StringBuilder)object).append(n4);
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
            ImsSmsDispatcher.this.mMetrics.writeOnImsServiceSmsSolicitedResponse(ImsSmsDispatcher.this.mPhone.getPhoneId(), n3, n4);
            object = ImsSmsDispatcher.this.mTrackers.get(n);
            if (object != null) {
                ((SMSDispatcher.SmsTracker)object).mMessageRef = n2;
                if (n3 != 1) {
                    if (n3 != 2) {
                        if (n3 != 3) {
                            if (n3 == 4) {
                                ++((SMSDispatcher.SmsTracker)object).mRetryCount;
                                ImsSmsDispatcher.this.fallbackToPstn(n, (SMSDispatcher.SmsTracker)object);
                            }
                        } else {
                            ++((SMSDispatcher.SmsTracker)object).mRetryCount;
                            ImsSmsDispatcher.this.sendSms((SMSDispatcher.SmsTracker)object);
                        }
                    } else {
                        ((SMSDispatcher.SmsTracker)object).onFailed(ImsSmsDispatcher.this.mContext, n4, 0);
                        ImsSmsDispatcher.this.mTrackers.remove(n);
                    }
                } else {
                    ((SMSDispatcher.SmsTracker)object).onSent(ImsSmsDispatcher.this.mContext);
                    ImsSmsDispatcher.this.mPhone.notifySmsSent(((SMSDispatcher.SmsTracker)object).mDestAddress);
                }
                return;
            }
            throw new IllegalArgumentException("Invalid token.");
        }

        public void onSmsReceived(int n, String string, byte[] smsMessage) {
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)"SMS received.");
            smsMessage = SmsMessage.createFromPdu((byte[])smsMessage, (String)string);
            ImsSmsDispatcher.this.mSmsDispatchersController.injectSmsPdu(smsMessage, string, new _$$Lambda$ImsSmsDispatcher$3$q7JFSZBuWsj_jBm5R51WxdJYNxc(this, smsMessage, n), true);
        }

        public void onSmsStatusReportReceived(int n, int n2, String pair, byte[] object) throws RemoteException {
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)"Status report received.");
            SMSDispatcher.SmsTracker smsTracker = ImsSmsDispatcher.this.mTrackers.get(n);
            if (smsTracker != null) {
                pair = ImsSmsDispatcher.this.mSmsDispatchersController.handleSmsStatusReport(smsTracker, (String)pair, (byte[])object);
                object = new StringBuilder();
                ((StringBuilder)object).append("Status report handle result, success: ");
                ((StringBuilder)object).append(pair.first);
                ((StringBuilder)object).append("complete: ");
                ((StringBuilder)object).append(pair.second);
                Rlog.d((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
                object = ImsSmsDispatcher.this.getImsManager();
                int n3 = (Boolean)pair.first != false ? 1 : 2;
                try {
                    object.acknowledgeSmsReport(n, n2, n3);
                }
                catch (ImsException imsException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Failed to acknowledgeSmsReport(). Error: ");
                    ((StringBuilder)object).append(imsException.getMessage());
                    Rlog.e((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
                }
                if (((Boolean)pair.second).booleanValue()) {
                    ImsSmsDispatcher.this.mTrackers.remove(n);
                }
                return;
            }
            throw new RemoteException("Invalid token.");
        }
    };
    private volatile boolean mIsImsServiceUp;
    private volatile boolean mIsRegistered;
    private volatile boolean mIsSmsCapable;
    private final Object mLock = new Object();
    private TelephonyMetrics mMetrics = TelephonyMetrics.getInstance();
    @VisibleForTesting
    public AtomicInteger mNextToken = new AtomicInteger();
    private ImsMmTelManager.RegistrationCallback mRegistrationCallback = new ImsMmTelManager.RegistrationCallback(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onRegistered(int n) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onImsConnected imsRadioTech=");
            ((StringBuilder)object).append(n);
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
            object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.mIsRegistered = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onRegistering(int n) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onImsProgressing imsRadioTech=");
            ((StringBuilder)object).append(n);
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
            object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.mIsRegistered = false;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onUnregistered(ImsReasonInfo imsReasonInfo) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("onImsDisconnected imsReasonInfo=");
            ((StringBuilder)object).append((Object)imsReasonInfo);
            Rlog.d((String)ImsSmsDispatcher.TAG, (String)((StringBuilder)object).toString());
            object = ImsSmsDispatcher.this.mLock;
            synchronized (object) {
                ImsSmsDispatcher.this.mIsRegistered = false;
                return;
            }
        }
    };
    @VisibleForTesting
    public Map<Integer, SMSDispatcher.SmsTracker> mTrackers = new ConcurrentHashMap<Integer, SMSDispatcher.SmsTracker>();

    public ImsSmsDispatcher(Phone phone, SmsDispatchersController smsDispatchersController) {
        super(phone, smsDispatchersController);
        this.mImsManagerConnector.connect();
    }

    private ImsManager getImsManager() {
        return ImsManager.getInstance((Context)this.mContext, (int)this.mPhone.getPhoneId());
    }

    private boolean isEmergencySmsPossible() {
        boolean bl = this.isLteService() || this.isLimitedLteService();
        return bl;
    }

    private boolean isLimitedLteService() {
        boolean bl = this.mPhone.getServiceState().getRilVoiceRadioTechnology() == 14 && this.mPhone.getServiceState().isEmergencyOnly();
        return bl;
    }

    private boolean isLteService() {
        boolean bl = this.mPhone.getServiceState().getRilVoiceRadioTechnology() == 14 && this.mPhone.getServiceState().getState() == 0;
        return bl;
    }

    private void setListeners() throws ImsException {
        this.getImsManager().addRegistrationCallback(this.mRegistrationCallback);
        this.getImsManager().addCapabilitiesCallback(this.mCapabilityCallback);
        this.getImsManager().setSmsListener(this.getSmsListener());
        this.getImsManager().onSmsReady();
    }

    @Override
    protected GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean bl) {
        return SMSDispatcherUtil.calculateLength(this.isCdmaMo(), charSequence, bl);
    }

    @VisibleForTesting
    public void fallbackToPstn(int n, SMSDispatcher.SmsTracker smsTracker) {
        this.mSmsDispatchersController.sendRetrySms(smsTracker);
        this.mTrackers.remove(n);
    }

    @Override
    protected String getFormat() {
        try {
            String string = this.getImsManager().getSmsFormat();
            return string;
        }
        catch (ImsException imsException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to get sms format. Error: ");
            stringBuilder.append(imsException.getMessage());
            Rlog.e((String)TAG, (String)stringBuilder.toString());
            return "unknown";
        }
    }

    @VisibleForTesting
    public IImsSmsListener getSmsListener() {
        return this.mImsSmsListener;
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, int n, byte[] arrby, boolean bl) {
        return SMSDispatcherUtil.getSubmitPdu(this.isCdmaMo(), string, string2, n, arrby, bl);
    }

    @Override
    protected SmsMessageBase.SubmitPduBase getSubmitPdu(String string, String string2, String string3, boolean bl, SmsHeader smsHeader, int n, int n2) {
        return SMSDispatcherUtil.getSubmitPdu(this.isCdmaMo(), string, string2, string3, bl, smsHeader, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean isAvailable() {
        Object object = this.mLock;
        synchronized (object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isAvailable: up=");
            stringBuilder.append(this.mIsImsServiceUp);
            stringBuilder.append(", reg= ");
            stringBuilder.append(this.mIsRegistered);
            stringBuilder.append(", cap= ");
            stringBuilder.append(this.mIsSmsCapable);
            Rlog.d((String)TAG, (String)stringBuilder.toString());
            if (!this.mIsImsServiceUp) return false;
            if (!this.mIsRegistered) return false;
            if (!this.mIsSmsCapable) return false;
            return true;
        }
    }

    @Override
    protected boolean isCdmaMo() {
        return this.mSmsDispatchersController.isCdmaFormat(this.getFormat());
    }

    public boolean isEmergencySmsSupport(String string) {
        boolean bl;
        long l;
        block11 : {
            Object object;
            boolean bl2;
            block10 : {
                block9 : {
                    bl = PhoneNumberUtils.isLocalEmergencyNumber((Context)this.mContext, (int)this.mPhone.getSubId(), (String)string);
                    bl2 = false;
                    if (!bl) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Emergency Sms is not supported for: ");
                        stringBuilder.append(Rlog.pii((String)TAG, (Object)string));
                        Rlog.e((String)TAG, (String)stringBuilder.toString());
                        return false;
                    }
                    l = Binder.clearCallingIdentity();
                    object = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
                    if (object != null) break block9;
                    Rlog.e((String)TAG, (String)"configManager is null");
                    return false;
                }
                object = object.getConfigForSubId(this.getSubId());
                if (object != null) break block10;
                Rlog.e((String)TAG, (String)"PersistableBundle is null");
                Binder.restoreCallingIdentity((long)l);
                return false;
            }
            boolean bl3 = object.getBoolean("support_emergency_sms_over_ims_bool");
            boolean bl4 = this.isEmergencySmsPossible();
            object = new StringBuilder();
            ((StringBuilder)object).append("isEmergencySmsSupport emergencySmsCarrierSupport: ");
            ((StringBuilder)object).append(bl3);
            ((StringBuilder)object).append(" destAddr: ");
            ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)string));
            ((StringBuilder)object).append(" mIsImsServiceUp: ");
            ((StringBuilder)object).append(this.mIsImsServiceUp);
            ((StringBuilder)object).append(" lteOrLimitedLte: ");
            ((StringBuilder)object).append(bl4);
            Rlog.i((String)TAG, (String)((StringBuilder)object).toString());
            bl = bl2;
            if (!bl3) break block11;
            bl3 = this.mIsImsServiceUp;
            bl = bl2;
            if (!bl3) break block11;
            bl = bl2;
            if (!bl4) break block11;
            bl = true;
        }
        Binder.restoreCallingIdentity((long)l);
        return bl;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void sendSms(SMSDispatcher.SmsTracker smsTracker) {
        int n;
        Object object;
        void var2_6;
        String string;
        block9 : {
            int n2;
            Object object2;
            boolean bl;
            ImsManager imsManager;
            block8 : {
                block7 : {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("sendSms:  mRetryCount=");
                    ((StringBuilder)object2).append(smsTracker.mRetryCount);
                    ((StringBuilder)object2).append(" mMessageRef=");
                    ((StringBuilder)object2).append(smsTracker.mMessageRef);
                    ((StringBuilder)object2).append(" SS=");
                    ((StringBuilder)object2).append(this.mPhone.getServiceState().getState());
                    Rlog.d((String)TAG, (String)((StringBuilder)object2).toString());
                    smsTracker.mUsesImsServiceForIms = true;
                    object2 = smsTracker.getData();
                    object = (byte[])((HashMap)object2).get("pdu");
                    byte[] arrby = (byte[])((HashMap)object2).get("smsc");
                    bl = smsTracker.mRetryCount > 0;
                    string = this.getFormat();
                    if ("3gpp".equals(string) && smsTracker.mRetryCount > 0 && (object[0] & 1) == 1) {
                        object[0] = (byte)(object[0] | 4);
                        object[1] = (byte)smsTracker.mMessageRef;
                    }
                    n = this.mNextToken.incrementAndGet();
                    this.mTrackers.put(n, smsTracker);
                    imsManager = this.getImsManager();
                    n2 = smsTracker.mMessageRef;
                    if (arrby == null) break block7;
                    try {
                        object2 = new String(arrby);
                        break block8;
                    }
                    catch (ImsException imsException) {
                        break block9;
                    }
                }
                object2 = null;
            }
            try {
                imsManager.sendSms(n, n2, string, (String)object2, bl, (byte[])object);
                this.mMetrics.writeImsServiceSendSms(this.mPhone.getPhoneId(), string, 1);
                return;
            }
            catch (ImsException imsException) {}
            break block9;
            catch (ImsException imsException) {
                // empty catch block
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("sendSms failed. Falling back to PSTN. Error: ");
        ((StringBuilder)object).append(var2_6.getMessage());
        Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
        this.fallbackToPstn(n, smsTracker);
        this.mMetrics.writeImsServiceSendSms(this.mPhone.getPhoneId(), string, 4);
    }

    @Override
    protected boolean shouldBlockSmsForEcbm() {
        return false;
    }

}

