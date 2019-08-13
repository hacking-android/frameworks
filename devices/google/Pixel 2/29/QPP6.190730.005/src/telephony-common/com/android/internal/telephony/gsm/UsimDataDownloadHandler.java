/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  com.android.internal.telephony.gsm.SmsMessage
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.gsm;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.cat.ComprehensionTlvTag;
import com.android.internal.telephony.gsm.SmsMessage;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UsimServiceTable;

public class UsimDataDownloadHandler
extends Handler {
    private static final int BER_SMS_PP_DOWNLOAD_TAG = 209;
    private static final int DEV_ID_NETWORK = 131;
    private static final int DEV_ID_UICC = 129;
    private static final int EVENT_SEND_ENVELOPE_RESPONSE = 2;
    private static final int EVENT_START_DATA_DOWNLOAD = 1;
    private static final int EVENT_WRITE_SMS_COMPLETE = 3;
    private static final String TAG = "UsimDataDownloadHandler";
    private final CommandsInterface mCi;
    private final int mPhoneId;

    public UsimDataDownloadHandler(CommandsInterface commandsInterface, int n) {
        this.mCi = commandsInterface;
        this.mPhoneId = n;
    }

    private void acknowledgeSmsWithError(int n) {
        this.mCi.acknowledgeLastIncomingGsmSms(false, n, null);
    }

    private void addUsimDataDownloadToMetrics(boolean bl) {
        TelephonyMetrics.getInstance().writeIncomingSMSPP(this.mPhoneId, "3gpp", bl);
    }

    private static int getEnvelopeBodyLength(int n, int n2) {
        int n3 = n2 > 127 ? 2 : 1;
        n2 = n3 = n2 + 5 + n3;
        if (n != 0) {
            n2 = n3 + 2 + n;
        }
        return n2;
    }

    private void handleDataDownload(SmsMessage object) {
        int n;
        int n2;
        int n3 = object.getDataCodingScheme();
        int n4 = object.getProtocolIdentifier();
        byte[] arrby = object.getPdu();
        int n5 = arrby[0] & 255;
        int n6 = UsimDataDownloadHandler.getEnvelopeBodyLength(n5, n = arrby.length - (n2 = n5 + 1));
        int n7 = n6 > 127 ? 2 : 1;
        object = new byte[n6 + 1 + n7];
        int n8 = 0 + 1;
        object[0] = (byte)-47;
        n7 = n8;
        if (n6 > 127) {
            object[n8] = (byte)-127;
            n7 = n8 + 1;
        }
        n8 = n7 + 1;
        object[n7] = (byte)n6;
        n6 = n8 + 1;
        object[n8] = (byte)(ComprehensionTlvTag.DEVICE_IDENTITIES.value() | 128);
        n7 = n6 + 1;
        object[n6] = (byte)2;
        n6 = n7 + 1;
        object[n7] = (byte)-125;
        n8 = n6 + 1;
        object[n6] = (byte)-127;
        n7 = n8;
        if (n5 != 0) {
            n7 = n8 + 1;
            object[n8] = (byte)ComprehensionTlvTag.ADDRESS.value();
            n8 = n7 + 1;
            object[n7] = (byte)n5;
            System.arraycopy(arrby, 1, object, n8, n5);
            n7 = n8 + n5;
        }
        n8 = n7 + 1;
        object[n7] = (byte)(ComprehensionTlvTag.SMS_TPDU.value() | 128);
        n7 = n8;
        if (n > 127) {
            object[n8] = (byte)-127;
            n7 = n8 + 1;
        }
        n8 = n7 + 1;
        object[n7] = (byte)n;
        System.arraycopy(arrby, n2, object, n8, n);
        if (n8 + n != ((byte[])object).length) {
            Rlog.e((String)TAG, (String)"startDataDownload() calculated incorrect envelope length, aborting.");
            this.acknowledgeSmsWithError(255);
            this.addUsimDataDownloadToMetrics(false);
            return;
        }
        object = IccUtils.bytesToHexString((byte[])object);
        this.mCi.sendEnvelopeWithStatus((String)object, this.obtainMessage(2, (Object)new int[]{n3, n4}));
        this.addUsimDataDownloadToMetrics(true);
    }

    private static boolean is7bitDcs(int n) {
        boolean bl = (n & 140) == 0 || (n & 244) == 240;
        return bl;
    }

    private void sendSmsAckForEnvelopeResponse(IccIoResult arrby, int n, int n2) {
        boolean bl;
        byte[] arrby2;
        int n3 = arrby.sw1;
        int n4 = arrby.sw2;
        if (n3 == 144 && n4 == 0 || n3 == 145) {
            arrby2 = new StringBuilder();
            arrby2.append("USIM data download succeeded: ");
            arrby2.append(arrby.toString());
            Rlog.d((String)TAG, (String)arrby2.toString());
            bl = true;
        } else {
            if (n3 == 147 && n4 == 0) {
                Rlog.e((String)TAG, (String)"USIM data download failed: Toolkit busy");
                this.acknowledgeSmsWithError(212);
                return;
            }
            if (n3 != 98 && n3 != 99) {
                arrby2 = new StringBuilder();
                arrby2.append("Unexpected SW1/SW2 response from UICC: ");
                arrby2.append(arrby.toString());
                Rlog.e((String)TAG, (String)arrby2.toString());
                bl = false;
            } else {
                arrby2 = new StringBuilder();
                arrby2.append("USIM data download failed: ");
                arrby2.append(arrby.toString());
                Rlog.e((String)TAG, (String)arrby2.toString());
                bl = false;
            }
        }
        arrby2 = arrby.payload;
        if (arrby2 != null && arrby2.length != 0) {
            if (bl) {
                arrby = new byte[arrby2.length + 5];
                n3 = 0 + 1;
                arrby[0] = (byte)(false ? 1 : 0);
                n4 = n3 + 1;
                arrby[n3] = (byte)7;
            } else {
                arrby = new byte[arrby2.length + 6];
                n3 = 0 + 1;
                arrby[0] = (byte)(false ? 1 : 0);
                n4 = n3 + 1;
                arrby[n3] = (byte)-43;
                arrby[n4] = (byte)7;
                ++n4;
            }
            n3 = n4 + 1;
            arrby[n4] = (byte)n2;
            n2 = n3 + 1;
            arrby[n3] = (byte)n;
            if (UsimDataDownloadHandler.is7bitDcs(n)) {
                n4 = arrby2.length * 8 / 7;
                n = n2 + 1;
                arrby[n2] = (byte)n4;
            } else {
                n = n2 + 1;
                arrby[n2] = (byte)arrby2.length;
            }
            System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
            this.mCi.acknowledgeIncomingGsmSmsWithPdu(bl, IccUtils.bytesToHexString((byte[])arrby), null);
            return;
        }
        if (bl) {
            this.mCi.acknowledgeLastIncomingGsmSms(true, 0, null);
        } else {
            this.acknowledgeSmsWithError(213);
        }
    }

    public void handleMessage(Message message) {
        int n = message.what;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Ignoring unexpected message, what=");
                    stringBuilder.append(message.what);
                    Rlog.e((String)TAG, (String)stringBuilder.toString());
                } else {
                    message = (AsyncResult)message.obj;
                    if (message.exception == null) {
                        Rlog.d((String)TAG, (String)"Successfully wrote SMS-PP message to UICC");
                        this.mCi.acknowledgeLastIncomingGsmSms(true, 0, null);
                    } else {
                        Rlog.d((String)TAG, (String)"Failed to write SMS-PP message to UICC", (Throwable)message.exception);
                        this.mCi.acknowledgeLastIncomingGsmSms(false, 255, null);
                    }
                }
            } else {
                message = (AsyncResult)message.obj;
                if (message.exception != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("UICC Send Envelope failure, exception: ");
                    stringBuilder.append(message.exception);
                    Rlog.e((String)TAG, (String)stringBuilder.toString());
                    this.acknowledgeSmsWithError(213);
                    return;
                }
                int[] arrn = (int[])message.userObj;
                this.sendSmsAckForEnvelopeResponse((IccIoResult)message.result, arrn[0], arrn[1]);
            }
        } else {
            this.handleDataDownload((SmsMessage)message.obj);
        }
    }

    int handleUsimDataDownload(UsimServiceTable object, SmsMessage smsMessage) {
        if (object != null && ((UsimServiceTable)object).isAvailable(UsimServiceTable.UsimService.DATA_DL_VIA_SMS_PP)) {
            Rlog.d((String)TAG, (String)"Received SMS-PP data download, sending to UICC.");
            return this.startDataDownload(smsMessage);
        }
        Rlog.d((String)TAG, (String)"DATA_DL_VIA_SMS_PP service not available, storing message to UICC.");
        object = IccUtils.bytesToHexString((byte[])PhoneNumberUtils.networkPortionToCalledPartyBCDWithLength((String)smsMessage.getServiceCenterAddress()));
        this.mCi.writeSmsToSim(3, (String)object, IccUtils.bytesToHexString((byte[])smsMessage.getPdu()), this.obtainMessage(3));
        this.addUsimDataDownloadToMetrics(false);
        return -1;
    }

    public int startDataDownload(SmsMessage smsMessage) {
        if (this.sendMessage(this.obtainMessage(1, (Object)smsMessage))) {
            return -1;
        }
        Rlog.e((String)TAG, (String)"startDataDownload failed to send message to start data download.");
        return 2;
    }
}

