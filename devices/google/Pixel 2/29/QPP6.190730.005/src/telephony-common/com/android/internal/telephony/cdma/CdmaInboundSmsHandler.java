/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.SmsCbMessage
 *  android.telephony.TelephonyManager
 *  com.android.internal.telephony.SmsConstants
 *  com.android.internal.telephony.SmsConstants$MessageClass
 *  com.android.internal.telephony.SmsMessageBase
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.util.HexDump
 */
package com.android.internal.telephony.cdma;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsCbMessage;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.CellBroadcastHandler;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.InboundSmsHandler;
import com.android.internal.telephony.InboundSmsTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.SmsStorageMonitor;
import com.android.internal.telephony.TelephonyComponentFactory;
import com.android.internal.telephony.WspTypeDecoder;
import com.android.internal.telephony.cdma.CdmaSMSDispatcher;
import com.android.internal.telephony.cdma.CdmaServiceCategoryProgramHandler;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.util.HexDump;
import java.util.Arrays;

public class CdmaInboundSmsHandler
extends InboundSmsHandler {
    private final boolean mCheckForDuplicatePortsInOmadmWapPush = Resources.getSystem().getBoolean(17891427);
    private byte[] mLastAcknowledgedSmsFingerprint;
    private byte[] mLastDispatchedSmsFingerprint;
    private final CdmaServiceCategoryProgramHandler mServiceCategoryProgramHandler;
    private final CdmaSMSDispatcher mSmsDispatcher;

    private CdmaInboundSmsHandler(Context context, SmsStorageMonitor smsStorageMonitor, Phone phone, CdmaSMSDispatcher cdmaSMSDispatcher) {
        super("CdmaInboundSmsHandler", context, smsStorageMonitor, phone, CellBroadcastHandler.makeCellBroadcastHandler(context, phone));
        this.mSmsDispatcher = cdmaSMSDispatcher;
        this.mServiceCategoryProgramHandler = CdmaServiceCategoryProgramHandler.makeScpHandler(context, phone.mCi);
        phone.mCi.setOnNewCdmaSms(this.getHandler(), 1, null);
    }

    private void addVoicemailSmsToMetrics() {
        this.mMetrics.writeIncomingVoiceMailSms(this.mPhone.getPhoneId(), "3gpp2");
    }

    private static boolean checkDuplicatePortOmadmWapPush(byte[] object, int n) {
        byte[] arrby = new byte[((byte[])object).length - (n += 4)];
        System.arraycopy(object, n, arrby, 0, arrby.length);
        object = new WspTypeDecoder(arrby);
        if (!((WspTypeDecoder)object).decodeUintvarInteger(2)) {
            return false;
        }
        if (!((WspTypeDecoder)object).decodeContentType(2 + ((WspTypeDecoder)object).getDecodedDataLength())) {
            return false;
        }
        return "application/vnd.syncml.notification".equals(((WspTypeDecoder)object).getValueString());
    }

    private void handleVoicemailTeleservice(SmsMessage object) {
        int n;
        int n2 = object.getNumOfVoicemails();
        object = new StringBuilder();
        ((StringBuilder)object).append("Voicemail count=");
        ((StringBuilder)object).append(n2);
        this.log(((StringBuilder)object).toString());
        if (n2 < 0) {
            n = -1;
        } else {
            n = n2;
            if (n2 > 99) {
                n = 99;
            }
        }
        this.mPhone.setVoiceMessageCount(n);
        this.addVoicemailSmsToMetrics();
    }

    public static CdmaInboundSmsHandler makeInboundSmsHandler(Context object, SmsStorageMonitor smsStorageMonitor, Phone phone, CdmaSMSDispatcher cdmaSMSDispatcher) {
        object = new CdmaInboundSmsHandler((Context)object, smsStorageMonitor, phone, cdmaSMSDispatcher);
        object.start();
        return object;
    }

    private int processCdmaWapPdu(byte[] object, int n, String string, String string2, long l) {
        int n2 = 0 + 1;
        int n3 = object[0] & 255;
        if (n3 != 0) {
            this.log("Received a WAP SMS which is not WDP. Discard.");
            return 1;
        }
        int n4 = n2 + 1;
        int n5 = object[n2] & 255;
        n2 = n4 + 1;
        int n6 = object[n4] & 255;
        if (n6 >= n5) {
            object = new StringBuilder();
            ((StringBuilder)object).append("WDP bad segment #");
            ((StringBuilder)object).append(n6);
            ((StringBuilder)object).append(" expecting 0-");
            ((StringBuilder)object).append(n5 - 1);
            this.loge(((StringBuilder)object).toString());
            return 1;
        }
        int n7 = false;
        if (n6 == 0) {
            n4 = n2 + 1;
            n7 = object[n2];
            n2 = n4 + 1;
            n7 = (n7 & 255) << 8 | object[n4] & 255;
            n4 = n2 + 1;
            Object object2 = object[n2];
            n2 = n4 + 1;
            n4 = (object2 & 255) << 8 | object[n4] & 255;
            if (this.mCheckForDuplicatePortsInOmadmWapPush && CdmaInboundSmsHandler.checkDuplicatePortOmadmWapPush((byte[])object, n2)) {
                n2 += 4;
            }
        } else {
            n4 = 0;
        }
        byte[] arrby = new StringBuilder();
        arrby.append("Received WAP PDU. Type = ");
        arrby.append(n3);
        arrby.append(", originator = ");
        arrby.append(string);
        arrby.append(", src-port = ");
        arrby.append(n7);
        arrby.append(", dst-port = ");
        arrby.append(n4);
        arrby.append(", ID = ");
        arrby.append(n);
        arrby.append(", segment# = ");
        arrby.append(n6);
        arrby.append('/');
        arrby.append(n5);
        this.log(arrby.toString());
        arrby = new byte[((Object)object).length - n2];
        System.arraycopy(object, n2, arrby, 0, ((Object)object).length - n2);
        return this.addTrackerToRawTableAndSendMessage(TelephonyComponentFactory.getInstance().inject(InboundSmsTracker.class.getName()).makeInboundSmsTracker(arrby, l, n4, true, string, string2, n, n6, n5, true, HexDump.toHexString((byte[])arrby), false), false);
    }

    private static int resultToCause(int n) {
        if (n != -1 && n != 1) {
            if (n != 3) {
                if (n != 4) {
                    return 39;
                }
                return 4;
            }
            return 35;
        }
        return 0;
    }

    @Override
    protected void acknowledgeLastIncomingSms(boolean bl, int n, Message message) {
        n = CdmaInboundSmsHandler.resultToCause(n);
        this.mPhone.mCi.acknowledgeLastIncomingCdmaSms(bl, n, message);
        if (n == 0) {
            this.mLastAcknowledgedSmsFingerprint = this.mLastDispatchedSmsFingerprint;
        }
        this.mLastDispatchedSmsFingerprint = null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected int dispatchMessageRadioSpecific(SmsMessageBase var1_1) {
        block11 : {
            var2_2 = (SmsMessage)var1_1;
            var3_3 = 1 == var2_2.getMessageType() ? 1 : 0;
            if (var3_3 != 0) {
                this.log("Broadcast type message");
                var1_1 = var2_2.parseBroadcastSms(TelephonyManager.from((Context)this.mContext).getNetworkOperatorForPhone(this.mPhone.getPhoneId()));
                if (var1_1 != null) {
                    this.mCellBroadcastHandler.dispatchSmsMessage(var1_1);
                    return 1;
                }
                this.loge("error trying to parse broadcast SMS");
                return 1;
            }
            this.mLastDispatchedSmsFingerprint = var2_2.getIncomingSmsFingerprint();
            var4_4 = this.mLastAcknowledgedSmsFingerprint;
            if (var4_4 != null && Arrays.equals(this.mLastDispatchedSmsFingerprint, var4_4)) {
                return 1;
            }
            var2_2.parseSms();
            var3_3 = var2_2.getTeleService();
            if (var3_3 == 262144) break block11;
            switch (var3_3) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("unsupported teleservice 0x");
                    var1_1.append(Integer.toHexString(var3_3));
                    this.loge(var1_1.toString());
                    return 4;
                }
                case 4102: {
                    this.mServiceCategoryProgramHandler.dispatchSmsMessage((Object)var2_2);
                    return 1;
                }
                case 4100: {
                    ** GOTO lbl36
                }
                case 4098: 
                case 4101: {
                    if (var2_2.isStatusReportMessage()) {
                        this.mSmsDispatcher.sendStatusReportMessage(var2_2);
                        return 1;
                    }
lbl36: // 3 sources:
                    if (!this.mStorageMonitor.isStorageAvailable() && var2_2.getMessageClass() != SmsConstants.MessageClass.CLASS_0) {
                        return 3;
                    }
                    if (4100 != var3_3) return this.dispatchNormalMessage((SmsMessageBase)var1_1);
                    return this.processCdmaWapPdu(var2_2.getUserData(), var2_2.mMessageRef, var2_2.getOriginatingAddress(), var2_2.getDisplayOriginatingAddress(), var2_2.getTimestampMillis());
                }
                case 4099: 
            }
        }
        this.handleVoicemailTeleservice(var2_2);
        return 1;
    }

    @Override
    protected boolean is3gpp2() {
        return true;
    }

    @Override
    protected void onQuitting() {
        this.mPhone.mCi.unSetOnNewCdmaSms(this.getHandler());
        this.mCellBroadcastHandler.dispose();
        this.log("unregistered for 3GPP2 SMS");
        super.onQuitting();
    }
}

