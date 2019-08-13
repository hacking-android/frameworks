/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.PersistableBundle
 *  android.os.UserManager
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.os.UserManager;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.InboundSmsHandler;
import com.android.internal.telephony.InboundSmsTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.cdma.CdmaInboundSmsHandler;
import com.android.internal.telephony.gsm.GsmInboundSmsHandler;

public class SmsBroadcastUndelivered {
    private static final boolean DBG = true;
    static final long DEFAULT_PARTIAL_SEGMENT_EXPIRE_AGE = 604800000L;
    private static final String[] PDU_PENDING_MESSAGE_PROJECTION = new String[]{"pdu", "sequence", "destination_port", "date", "reference_number", "count", "address", "_id", "message_body", "display_originating_addr"};
    private static final String TAG = "SmsBroadcastUndelivered";
    private static SmsBroadcastUndelivered instance;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Received broadcast ");
            stringBuilder.append(intent.getAction());
            Rlog.d((String)SmsBroadcastUndelivered.TAG, (String)stringBuilder.toString());
            if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                new ScanRawTableThread(context).start();
            }
        }
    };
    private final CdmaInboundSmsHandler mCdmaInboundSmsHandler;
    private final GsmInboundSmsHandler mGsmInboundSmsHandler;
    private final ContentResolver mResolver;

    @UnsupportedAppUsage
    private SmsBroadcastUndelivered(Context context, GsmInboundSmsHandler gsmInboundSmsHandler, CdmaInboundSmsHandler cdmaInboundSmsHandler) {
        this.mResolver = context.getContentResolver();
        this.mGsmInboundSmsHandler = gsmInboundSmsHandler;
        this.mCdmaInboundSmsHandler = cdmaInboundSmsHandler;
        if (((UserManager)context.getSystemService("user")).isUserUnlocked()) {
            new ScanRawTableThread(context).start();
        } else {
            gsmInboundSmsHandler = new IntentFilter();
            gsmInboundSmsHandler.addAction("android.intent.action.USER_UNLOCKED");
            context.registerReceiver(this.mBroadcastReceiver, (IntentFilter)gsmInboundSmsHandler);
        }
    }

    private static void broadcastSms(InboundSmsTracker inboundSmsTracker, CdmaInboundSmsHandler object, GsmInboundSmsHandler gsmInboundSmsHandler) {
        if (!inboundSmsTracker.is3gpp2()) {
            object = gsmInboundSmsHandler;
        }
        if (object != null) {
            object.sendMessage(2, (Object)inboundSmsTracker);
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("null handler for ");
            ((StringBuilder)object).append(inboundSmsTracker.getFormat());
            ((StringBuilder)object).append(" format, can't deliver.");
            Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
        }
    }

    private static int getPhoneId(GsmInboundSmsHandler gsmInboundSmsHandler, CdmaInboundSmsHandler cdmaInboundSmsHandler) {
        int n = -1;
        if (gsmInboundSmsHandler != null) {
            n = gsmInboundSmsHandler.getPhone().getPhoneId();
        } else if (cdmaInboundSmsHandler != null) {
            n = cdmaInboundSmsHandler.getPhone().getPhoneId();
        }
        return n;
    }

    private long getUndeliveredSmsExpirationTime(Context context) {
        int n = SubscriptionManager.getDefaultSmsSubscriptionId();
        if ((context = ((CarrierConfigManager)context.getSystemService("carrier_config")).getConfigForSubId(n)) != null) {
            return context.getLong("undelivered_sms_message_expiration_time", 604800000L);
        }
        return 604800000L;
    }

    public static void initialize(Context context, GsmInboundSmsHandler gsmInboundSmsHandler, CdmaInboundSmsHandler cdmaInboundSmsHandler) {
        if (instance == null) {
            instance = new SmsBroadcastUndelivered(context, gsmInboundSmsHandler, cdmaInboundSmsHandler);
        }
        if (gsmInboundSmsHandler != null) {
            gsmInboundSmsHandler.sendMessage(6);
        }
        if (cdmaInboundSmsHandler != null) {
            cdmaInboundSmsHandler.sendMessage(6);
        }
    }

    /*
     * Exception decompiling
     */
    static void scanRawTable(Context var0, CdmaInboundSmsHandler var1_3, GsmInboundSmsHandler var2_12, long var3_14) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 22[WHILELOOP]
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

    private class ScanRawTableThread
    extends Thread {
        private final Context context;

        private ScanRawTableThread(Context context) {
            this.context = context;
        }

        @Override
        public void run() {
            SmsBroadcastUndelivered.scanRawTable(this.context, SmsBroadcastUndelivered.this.mCdmaInboundSmsHandler, SmsBroadcastUndelivered.this.mGsmInboundSmsHandler, System.currentTimeMillis() - SmsBroadcastUndelivered.this.getUndeliveredSmsExpirationTime(this.context));
            InboundSmsHandler.cancelNewMessageNotification(this.context);
        }
    }

    private static class SmsReferenceKey {
        final String mAddress;
        final String mFormat;
        final int mMessageCount;
        final String mQuery;
        final int mReferenceNumber;

        SmsReferenceKey(InboundSmsTracker inboundSmsTracker) {
            this.mAddress = inboundSmsTracker.getAddress();
            this.mReferenceNumber = inboundSmsTracker.getReferenceNumber();
            this.mMessageCount = inboundSmsTracker.getMessageCount();
            this.mQuery = inboundSmsTracker.getQueryForSegments();
            this.mFormat = inboundSmsTracker.getFormat();
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof SmsReferenceKey;
            boolean bl2 = false;
            if (bl) {
                object = (SmsReferenceKey)object;
                bl = bl2;
                if (((SmsReferenceKey)object).mAddress.equals(this.mAddress)) {
                    bl = bl2;
                    if (((SmsReferenceKey)object).mReferenceNumber == this.mReferenceNumber) {
                        bl = bl2;
                        if (((SmsReferenceKey)object).mMessageCount == this.mMessageCount) {
                            bl = true;
                        }
                    }
                }
                return bl;
            }
            return false;
        }

        String getDeleteWhere() {
            return this.mQuery;
        }

        String[] getDeleteWhereArgs() {
            return new String[]{this.mAddress, Integer.toString(this.mReferenceNumber), Integer.toString(this.mMessageCount)};
        }

        public int hashCode() {
            return (this.mReferenceNumber * 31 + this.mMessageCount) * 31 + this.mAddress.hashCode();
        }
    }

}

