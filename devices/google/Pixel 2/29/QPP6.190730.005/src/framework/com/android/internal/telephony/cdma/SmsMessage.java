/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cdma;

import android.content.res.Resources;
import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SmsCbCmasInfo;
import android.telephony.SmsCbEtwsInfo;
import android.telephony.SmsCbLocation;
import android.telephony.SmsCbMessage;
import android.telephony.cdma.CdmaSmsCbProgramData;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.Sms7BitEncodingTranslator;
import com.android.internal.telephony.SmsAddress;
import com.android.internal.telephony.SmsConstants;
import com.android.internal.telephony.SmsHeader;
import com.android.internal.telephony.SmsMessageBase;
import com.android.internal.telephony.cdma.sms.BearerData;
import com.android.internal.telephony.cdma.sms.CdmaSmsAddress;
import com.android.internal.telephony.cdma.sms.CdmaSmsSubaddress;
import com.android.internal.telephony.cdma.sms.SmsEnvelope;
import com.android.internal.telephony.cdma.sms.UserData;
import com.android.internal.util.HexDump;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class SmsMessage
extends SmsMessageBase {
    private static final byte BEARER_DATA = 8;
    private static final byte BEARER_REPLY_OPTION = 6;
    private static final byte CAUSE_CODES = 7;
    private static final byte DESTINATION_ADDRESS = 4;
    private static final byte DESTINATION_SUB_ADDRESS = 5;
    private static final String LOGGABLE_TAG = "CDMA:SMS";
    static final String LOG_TAG = "SmsMessage";
    private static final byte ORIGINATING_ADDRESS = 2;
    private static final byte ORIGINATING_SUB_ADDRESS = 3;
    private static final int PRIORITY_EMERGENCY = 3;
    private static final int PRIORITY_INTERACTIVE = 1;
    private static final int PRIORITY_NORMAL = 0;
    private static final int PRIORITY_URGENT = 2;
    private static final int RETURN_ACK = 1;
    private static final int RETURN_NO_ACK = 0;
    private static final byte SERVICE_CATEGORY = 1;
    private static final byte TELESERVICE_IDENTIFIER = 0;
    private static final boolean VDBG = false;
    private BearerData mBearerData;
    private SmsEnvelope mEnvelope;
    private int status;

    public SmsMessage() {
    }

    public SmsMessage(SmsAddress smsAddress, SmsEnvelope smsEnvelope) {
        this.mOriginatingAddress = smsAddress;
        this.mEnvelope = smsEnvelope;
        this.createPdu();
    }

    public static GsmAlphabet.TextEncodingDetails calculateLength(CharSequence charSequence, boolean bl, boolean bl2) {
        String string2 = null;
        if (Resources.getSystem().getBoolean(17891523)) {
            string2 = Sms7BitEncodingTranslator.translate(charSequence, true);
        }
        CharSequence charSequence2 = string2;
        if (TextUtils.isEmpty(string2)) {
            charSequence2 = charSequence;
        }
        return BearerData.calcTextEncodingDetails(charSequence2, bl, bl2);
    }

    public static byte convertDtmfToAscii(byte by) {
        byte by2;
        switch (by) {
            default: {
                by2 = by = (byte)32;
                break;
            }
            case 15: {
                by2 = by = (byte)67;
                break;
            }
            case 14: {
                by2 = by = (byte)66;
                break;
            }
            case 13: {
                by2 = by = (byte)65;
                break;
            }
            case 12: {
                by2 = by = (byte)35;
                break;
            }
            case 11: {
                by2 = by = (byte)42;
                break;
            }
            case 10: {
                by2 = by = (byte)48;
                break;
            }
            case 9: {
                by2 = by = (byte)57;
                break;
            }
            case 8: {
                by2 = by = (byte)56;
                break;
            }
            case 7: {
                by2 = by = (byte)55;
                break;
            }
            case 6: {
                by2 = by = (byte)54;
                break;
            }
            case 5: {
                by2 = by = (byte)53;
                break;
            }
            case 4: {
                by2 = by = (byte)52;
                break;
            }
            case 3: {
                by2 = by = (byte)51;
                break;
            }
            case 2: {
                by2 = by = (byte)50;
                break;
            }
            case 1: {
                by2 = by = (byte)49;
                break;
            }
            case 0: {
                by2 = by = (byte)68;
            }
        }
        return by2;
    }

    public static SmsMessage createFromEfRecord(int n, byte[] arrby) {
        SmsMessage smsMessage;
        try {
            smsMessage = new SmsMessage();
            smsMessage.mIndexOnIcc = n;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", runtimeException);
            return null;
        }
        if ((arrby[0] & 1) == 0) {
            Rlog.w(LOG_TAG, "SMS parsing failed: Trying to parse a free record");
            return null;
        }
        smsMessage.mStatusOnIcc = arrby[0] & 7;
        n = arrby[1] & 255;
        byte[] arrby2 = new byte[n];
        System.arraycopy(arrby, 2, arrby2, 0, n);
        smsMessage.parsePduFromEfRecord(arrby2);
        return smsMessage;
    }

    public static SmsMessage createFromPdu(byte[] arrby) {
        SmsMessage smsMessage = new SmsMessage();
        try {
            smsMessage.parsePdu(arrby);
            return smsMessage;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            Log.e(LOG_TAG, "SMS PDU parsing failed with out of memory: ", outOfMemoryError);
            return null;
        }
        catch (RuntimeException runtimeException) {
            Rlog.e(LOG_TAG, "SMS PDU parsing failed: ", runtimeException);
            return null;
        }
    }

    private void decodeSmsDisplayAddress(SmsAddress smsAddress) {
        StringBuilder stringBuilder;
        smsAddress.address = new String(smsAddress.origBytes);
        if (smsAddress.ton == 1 && smsAddress.address.charAt(0) != '+') {
            stringBuilder = new StringBuilder();
            stringBuilder.append("+");
            stringBuilder.append(smsAddress.address);
            smsAddress.address = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(" decodeSmsDisplayAddress = ");
        stringBuilder.append(smsAddress.address);
        Rlog.pii(LOG_TAG, (Object)stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static int getNextMessageId() {
        synchronized (SmsMessage.class) {
            int n = SystemProperties.getInt("persist.radio.cdma.msgid", 1);
            String string2 = Integer.toString(n % 65535 + 1);
            try {
                SystemProperties.set("persist.radio.cdma.msgid", string2);
                if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("next persist.radio.cdma.msgid = ");
                    stringBuilder.append(string2);
                    Rlog.d(LOG_TAG, stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("readback gets ");
                    stringBuilder.append(SystemProperties.get("persist.radio.cdma.msgid"));
                    Rlog.d(LOG_TAG, stringBuilder.toString());
                }
            }
            catch (RuntimeException runtimeException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("set nextMessage ID failed: ");
                stringBuilder.append(runtimeException);
                Rlog.e(LOG_TAG, stringBuilder.toString());
            }
            return n;
        }
    }

    public static SubmitPdu getSubmitPdu(String string2, UserData userData, boolean bl) {
        return SmsMessage.privateGetSubmitPdu(string2, bl, userData);
    }

    public static SubmitPdu getSubmitPdu(String string2, UserData userData, boolean bl, int n) {
        return SmsMessage.privateGetSubmitPdu(string2, bl, userData, n);
    }

    public static SubmitPdu getSubmitPdu(String object, String string2, int n, byte[] arrby, boolean bl) {
        Object object2 = new SmsHeader.PortAddrs();
        ((SmsHeader.PortAddrs)object2).destPort = n;
        ((SmsHeader.PortAddrs)object2).origPort = 0;
        ((SmsHeader.PortAddrs)object2).areEightBits = false;
        object = new SmsHeader();
        ((SmsHeader)object).portAddrs = object2;
        object2 = new UserData();
        ((UserData)object2).userDataHeader = object;
        ((UserData)object2).msgEncoding = 0;
        ((UserData)object2).msgEncodingSet = true;
        ((UserData)object2).payload = arrby;
        return SmsMessage.privateGetSubmitPdu(string2, bl, (UserData)object2);
    }

    public static SubmitPdu getSubmitPdu(String string2, String string3, String string4, boolean bl, SmsHeader smsHeader) {
        return SmsMessage.getSubmitPdu(string2, string3, string4, bl, smsHeader, -1);
    }

    public static SubmitPdu getSubmitPdu(String object, String string2, String string3, boolean bl, SmsHeader smsHeader, int n) {
        if (string3 != null && string2 != null) {
            object = new UserData();
            ((UserData)object).payloadStr = string3;
            ((UserData)object).userDataHeader = smsHeader;
            return SmsMessage.privateGetSubmitPdu(string2, bl, (UserData)object, n);
        }
        return null;
    }

    public static int getTPLayerLengthForPDU(String string2) {
        Rlog.w(LOG_TAG, "getTPLayerLengthForPDU: is not supported in CDMA mode.");
        return 0;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void parsePdu(byte[] object) {
        Object object2 = new DataInputStream(new ByteArrayInputStream((byte[])object));
        SmsEnvelope smsEnvelope = new SmsEnvelope();
        CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
        CdmaSmsSubaddress cdmaSmsSubaddress = new CdmaSmsSubaddress();
        try {
            int n;
            smsEnvelope.messageType = ((DataInputStream)object2).readInt();
            smsEnvelope.teleService = ((DataInputStream)object2).readInt();
            smsEnvelope.serviceCategory = ((DataInputStream)object2).readInt();
            cdmaSmsAddress.digitMode = ((DataInputStream)object2).readByte();
            cdmaSmsAddress.numberMode = ((DataInputStream)object2).readByte();
            cdmaSmsAddress.ton = ((DataInputStream)object2).readByte();
            cdmaSmsAddress.numberPlan = ((DataInputStream)object2).readByte();
            cdmaSmsAddress.numberOfDigits = n = ((DataInputStream)object2).readUnsignedByte();
            int n2 = ((Object)object).length;
            if (n > n2) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("createFromPdu: Invalid pdu, addr.numberOfDigits ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" > pdu len ");
                ((StringBuilder)object2).append(((Object)object).length);
                RuntimeException runtimeException = new RuntimeException(((StringBuilder)object2).toString());
                throw runtimeException;
            }
            cdmaSmsAddress.origBytes = new byte[n];
            ((DataInputStream)object2).read(cdmaSmsAddress.origBytes, 0, n);
            smsEnvelope.bearerReply = ((DataInputStream)object2).readInt();
            smsEnvelope.replySeqNo = ((DataInputStream)object2).readByte();
            smsEnvelope.errorClass = ((DataInputStream)object2).readByte();
            smsEnvelope.causeCode = ((DataInputStream)object2).readByte();
            n = ((DataInputStream)object2).readInt();
            if (n > ((Object)object).length) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("createFromPdu: Invalid pdu, bearerDataLength ");
                stringBuilder.append(n);
                stringBuilder.append(" > pdu len ");
                stringBuilder.append(((Object)object).length);
                object2 = new RuntimeException(stringBuilder.toString());
                throw object2;
            }
            smsEnvelope.bearerData = new byte[n];
            ((DataInputStream)object2).read(smsEnvelope.bearerData, 0, n);
            ((FilterInputStream)object2).close();
        }
        catch (Exception exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("createFromPdu: conversion from byte array to object failed: ");
            ((StringBuilder)object2).append(exception);
            Rlog.e(LOG_TAG, ((StringBuilder)object2).toString());
        }
        this.mOriginatingAddress = cdmaSmsAddress;
        smsEnvelope.origAddress = cdmaSmsAddress;
        smsEnvelope.origSubaddress = cdmaSmsSubaddress;
        this.mEnvelope = smsEnvelope;
        this.mPdu = object;
        this.parseSms();
        return;
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createFromPdu: conversion from byte array to object failed: ");
            ((StringBuilder)object).append(iOException);
            throw new RuntimeException(((StringBuilder)object).toString(), iOException);
        }
    }

    /*
     * Exception decompiling
     */
    private void parsePduFromEfRecord(byte[] var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 15[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static SubmitPdu privateGetSubmitPdu(String string2, boolean bl, UserData userData) {
        return SmsMessage.privateGetSubmitPdu(string2, bl, userData, -1);
    }

    private static SubmitPdu privateGetSubmitPdu(String object, boolean bl, UserData object2, int n) {
        Object object3;
        if ((object = CdmaSmsAddress.parse(PhoneNumberUtils.cdmaCheckAndProcessPlusCodeForSms((String)object))) == null) {
            return null;
        }
        Object object4 = new BearerData();
        ((BearerData)object4).messageType = 2;
        ((BearerData)object4).messageId = SmsMessage.getNextMessageId();
        ((BearerData)object4).deliveryAckReq = bl;
        ((BearerData)object4).userAckReq = false;
        ((BearerData)object4).readAckReq = false;
        ((BearerData)object4).reportReq = false;
        if (n >= 0 && n <= 3) {
            ((BearerData)object4).priorityIndicatorSet = true;
            ((BearerData)object4).priority = n;
        }
        ((BearerData)object4).userData = object2;
        byte[] arrby = BearerData.encode((BearerData)object4);
        if (arrby == null) {
            return null;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("MO (encoded) BearerData = ");
            ((StringBuilder)object3).append(object4);
            Rlog.d(LOG_TAG, ((StringBuilder)object3).toString());
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("MO raw BearerData = '");
            ((StringBuilder)object3).append(HexDump.toHexString(arrby));
            ((StringBuilder)object3).append("'");
            Rlog.d(LOG_TAG, ((StringBuilder)object3).toString());
        }
        n = ((BearerData)object4).hasUserDataHeader && ((UserData)object2).msgEncoding != 2 ? 4101 : 4098;
        object3 = new SmsEnvelope();
        ((SmsEnvelope)object3).messageType = 0;
        ((SmsEnvelope)object3).teleService = n;
        ((SmsEnvelope)object3).destAddress = object;
        ((SmsEnvelope)object3).bearerReply = 1;
        ((SmsEnvelope)object3).bearerData = arrby;
        try {
            object2 = new ByteArrayOutputStream(100);
            object4 = new DataOutputStream((OutputStream)object2);
            ((DataOutputStream)object4).writeInt(((SmsEnvelope)object3).teleService);
            ((DataOutputStream)object4).writeInt(0);
            ((DataOutputStream)object4).writeInt(0);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).digitMode);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).numberMode);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).ton);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).numberPlan);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).numberOfDigits);
            ((DataOutputStream)object4).write(((CdmaSmsAddress)object).origBytes, 0, ((CdmaSmsAddress)object).origBytes.length);
            ((DataOutputStream)object4).write(0);
            ((DataOutputStream)object4).write(0);
            ((DataOutputStream)object4).write(0);
            ((DataOutputStream)object4).write(arrby.length);
            ((DataOutputStream)object4).write(arrby, 0, arrby.length);
            ((FilterOutputStream)object4).close();
            object = new SubmitPdu();
            ((SubmitPdu)object).encodedMessage = ((ByteArrayOutputStream)object2).toByteArray();
            ((SubmitPdu)object).encodedScAddress = null;
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("creating SubmitPdu failed: ");
            ((StringBuilder)object).append(iOException);
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
            return null;
        }
    }

    public void createPdu() {
        SmsEnvelope smsEnvelope = this.mEnvelope;
        Object object = smsEnvelope.origAddress;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(100);
        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(byteArrayOutputStream));
        try {
            dataOutputStream.writeInt(smsEnvelope.messageType);
            dataOutputStream.writeInt(smsEnvelope.teleService);
            dataOutputStream.writeInt(smsEnvelope.serviceCategory);
            dataOutputStream.writeByte(((CdmaSmsAddress)object).digitMode);
            dataOutputStream.writeByte(((CdmaSmsAddress)object).numberMode);
            dataOutputStream.writeByte(((CdmaSmsAddress)object).ton);
            dataOutputStream.writeByte(((CdmaSmsAddress)object).numberPlan);
            dataOutputStream.writeByte(((CdmaSmsAddress)object).numberOfDigits);
            dataOutputStream.write(((CdmaSmsAddress)object).origBytes, 0, ((CdmaSmsAddress)object).origBytes.length);
            dataOutputStream.writeInt(smsEnvelope.bearerReply);
            dataOutputStream.writeByte(smsEnvelope.replySeqNo);
            dataOutputStream.writeByte(smsEnvelope.errorClass);
            dataOutputStream.writeByte(smsEnvelope.causeCode);
            dataOutputStream.writeInt(smsEnvelope.bearerData.length);
            dataOutputStream.write(smsEnvelope.bearerData, 0, smsEnvelope.bearerData.length);
            dataOutputStream.close();
            this.mPdu = byteArrayOutputStream.toByteArray();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("createPdu: conversion from object to byte array failed: ");
            ((StringBuilder)object).append(iOException);
            Rlog.e(LOG_TAG, ((StringBuilder)object).toString());
        }
    }

    public byte[] getIncomingSmsFingerprint() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(this.mEnvelope.serviceCategory);
        byteArrayOutputStream.write(this.mEnvelope.teleService);
        byteArrayOutputStream.write(this.mEnvelope.origAddress.origBytes, 0, this.mEnvelope.origAddress.origBytes.length);
        byteArrayOutputStream.write(this.mEnvelope.bearerData, 0, this.mEnvelope.bearerData.length);
        if (this.mEnvelope.origSubaddress != null && this.mEnvelope.origSubaddress.origBytes != null) {
            byteArrayOutputStream.write(this.mEnvelope.origSubaddress.origBytes, 0, this.mEnvelope.origSubaddress.origBytes.length);
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public SmsConstants.MessageClass getMessageClass() {
        if (this.mBearerData.displayMode == 0) {
            return SmsConstants.MessageClass.CLASS_0;
        }
        return SmsConstants.MessageClass.UNKNOWN;
    }

    public int getMessageType() {
        return this.mEnvelope.serviceCategory != 0;
    }

    public int getNumOfVoicemails() {
        return this.mBearerData.numberOfMessages;
    }

    @Override
    public int getProtocolIdentifier() {
        Rlog.w(LOG_TAG, "getProtocolIdentifier: is not supported in CDMA mode.");
        return 0;
    }

    public ArrayList<CdmaSmsCbProgramData> getSmsCbProgramData() {
        return this.mBearerData.serviceCategoryProgramData;
    }

    @Override
    public int getStatus() {
        return this.status << 16;
    }

    public int getTeleService() {
        return this.mEnvelope.teleService;
    }

    @Override
    public boolean isCphsMwiMessage() {
        Rlog.w(LOG_TAG, "isCphsMwiMessage: is not supported in CDMA mode.");
        return false;
    }

    @Override
    public boolean isMWIClearMessage() {
        BearerData bearerData = this.mBearerData;
        boolean bl = bearerData != null && bearerData.numberOfMessages == 0;
        return bl;
    }

    @Override
    public boolean isMWISetMessage() {
        BearerData bearerData = this.mBearerData;
        boolean bl = bearerData != null && bearerData.numberOfMessages > 0;
        return bl;
    }

    @Override
    public boolean isMwiDontStore() {
        BearerData bearerData = this.mBearerData;
        boolean bl = bearerData != null && bearerData.numberOfMessages > 0 && this.mBearerData.userData == null;
        return bl;
    }

    @Override
    public boolean isReplace() {
        Rlog.w(LOG_TAG, "isReplace: is not supported in CDMA mode.");
        return false;
    }

    @Override
    public boolean isReplyPathPresent() {
        Rlog.w(LOG_TAG, "isReplyPathPresent: is not supported in CDMA mode.");
        return false;
    }

    @Override
    public boolean isStatusReportMessage() {
        boolean bl = this.mBearerData.messageType == 4;
        return bl;
    }

    public SmsCbMessage parseBroadcastSms(String object) {
        BearerData bearerData = BearerData.decode(this.mEnvelope.bearerData, this.mEnvelope.serviceCategory);
        if (bearerData == null) {
            Rlog.w(LOG_TAG, "BearerData.decode() returned null");
            return null;
        }
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("MT raw BearerData = ");
            stringBuilder.append(HexDump.toHexString(this.mEnvelope.bearerData));
            Rlog.d(LOG_TAG, stringBuilder.toString());
        }
        object = new SmsCbLocation((String)object);
        return new SmsCbMessage(2, 1, bearerData.messageId, (SmsCbLocation)object, this.mEnvelope.serviceCategory, bearerData.getLanguage(), bearerData.userData.payloadStr, bearerData.priority, null, bearerData.cmasWarningInfo);
    }

    public void parseSms() {
        Object object;
        if (this.mEnvelope.teleService == 262144) {
            this.mBearerData = new BearerData();
            if (this.mEnvelope.bearerData != null) {
                this.mBearerData.numberOfMessages = this.mEnvelope.bearerData[0] & 255;
            }
            return;
        }
        this.mBearerData = BearerData.decode(this.mEnvelope.bearerData);
        if (Rlog.isLoggable(LOGGABLE_TAG, 2)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("MT raw BearerData = '");
            ((StringBuilder)object).append(HexDump.toHexString(this.mEnvelope.bearerData));
            ((StringBuilder)object).append("'");
            Rlog.d(LOG_TAG, ((StringBuilder)object).toString());
            object = new StringBuilder();
            ((StringBuilder)object).append("MT (decoded) BearerData = ");
            ((StringBuilder)object).append(this.mBearerData);
            Rlog.d(LOG_TAG, ((StringBuilder)object).toString());
        }
        this.mMessageRef = this.mBearerData.messageId;
        if (this.mBearerData.userData != null) {
            this.mUserData = this.mBearerData.userData.payload;
            this.mUserDataHeader = this.mBearerData.userData.userDataHeader;
            this.mMessageBody = this.mBearerData.userData.payloadStr;
        }
        if (this.mOriginatingAddress != null) {
            this.decodeSmsDisplayAddress(this.mOriginatingAddress);
        }
        if (this.mRecipientAddress != null) {
            this.decodeSmsDisplayAddress(this.mRecipientAddress);
        }
        if (this.mBearerData.msgCenterTimeStamp != null) {
            this.mScTimeMillis = this.mBearerData.msgCenterTimeStamp.toMillis(true);
        }
        if (this.mBearerData.messageType == 4) {
            if (!this.mBearerData.messageStatusSet) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("DELIVERY_ACK message without msgStatus (");
                object = this.mUserData == null ? "also missing" : "does have";
                stringBuilder.append((String)object);
                stringBuilder.append(" userData).");
                Rlog.d(LOG_TAG, stringBuilder.toString());
                this.status = 0;
            } else {
                this.status = this.mBearerData.errorClass << 8;
                this.status |= this.mBearerData.messageStatus;
            }
        } else if (this.mBearerData.messageType != 1 && this.mBearerData.messageType != 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported message type: ");
            ((StringBuilder)object).append(this.mBearerData.messageType);
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (this.mMessageBody != null) {
            this.parseMessageBody();
        } else {
            object = this.mUserData;
        }
    }

    public static class SubmitPdu
    extends SmsMessageBase.SubmitPduBase {
    }

}

