/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.SubscriptionManager
 *  com.android.internal.telephony.cdma.SmsMessage
 *  com.android.internal.telephony.cdma.sms.BearerData
 *  com.android.internal.telephony.cdma.sms.CdmaSmsAddress
 */
package com.android.internal.telephony.cdma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberUtils;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.WakeLockStateMachine;
import com.android.internal.telephony.cdma.SmsMessage;
import com.android.internal.telephony.cdma.sms.BearerData;
import com.android.internal.telephony.cdma.sms.CdmaSmsAddress;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public final class CdmaServiceCategoryProgramHandler
extends WakeLockStateMachine {
    final CommandsInterface mCi;
    private final BroadcastReceiver mScpResultsReceiver = new BroadcastReceiver(){

        /*
         * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        private void sendScpResults() {
            Object object;
            Throwable throwable2222;
            block13 : {
                int n = this.getResultCode();
                if (n != -1 && n != 1) {
                    CdmaServiceCategoryProgramHandler cdmaServiceCategoryProgramHandler = CdmaServiceCategoryProgramHandler.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("SCP results error: result code = ");
                    stringBuilder.append(n);
                    cdmaServiceCategoryProgramHandler.loge(stringBuilder.toString());
                    return;
                }
                object = this.getResultExtras(false);
                if (object == null) {
                    CdmaServiceCategoryProgramHandler.this.loge("SCP results error: missing extras");
                    return;
                }
                String string = object.getString("sender");
                if (string == null) {
                    CdmaServiceCategoryProgramHandler.this.loge("SCP results error: missing sender extra.");
                    return;
                }
                if ((object = object.getParcelableArrayList("results")) == null) {
                    CdmaServiceCategoryProgramHandler.this.loge("SCP results error: missing results extra.");
                    return;
                }
                Object object2 = new BearerData();
                ((BearerData)object2).messageType = 2;
                ((BearerData)object2).messageId = SmsMessage.getNextMessageId();
                ((BearerData)object2).serviceCategoryProgramResults = object;
                byte[] arrby = BearerData.encode((BearerData)object2);
                object2 = new ByteArrayOutputStream(100);
                object = new DataOutputStream((OutputStream)object2);
                ((DataOutputStream)object).writeInt(4102);
                ((DataOutputStream)object).writeInt(0);
                ((DataOutputStream)object).writeInt(0);
                string = CdmaSmsAddress.parse((String)PhoneNumberUtils.cdmaCheckAndProcessPlusCodeForSms((String)string));
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).digitMode);
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).numberMode);
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).ton);
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).numberPlan);
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).numberOfDigits);
                ((DataOutputStream)object).write(((CdmaSmsAddress)string).origBytes, 0, ((CdmaSmsAddress)string).origBytes.length);
                ((DataOutputStream)object).write(0);
                ((DataOutputStream)object).write(0);
                ((DataOutputStream)object).write(0);
                ((DataOutputStream)object).write(arrby.length);
                ((DataOutputStream)object).write(arrby, 0, arrby.length);
                CdmaServiceCategoryProgramHandler.this.mCi.sendCdmaSms(((ByteArrayOutputStream)object2).toByteArray(), null);
                ((FilterOutputStream)object).close();
                return;
                {
                    catch (Throwable throwable2222) {
                        break block13;
                    }
                    catch (IOException iOException) {}
                    {
                        CdmaServiceCategoryProgramHandler.this.loge("exception creating SCP results PDU", iOException);
                    }
                    try {
                        ((FilterOutputStream)object).close();
                        return;
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                }
                return;
            }
            try {
                ((FilterOutputStream)object).close();
                throw throwable2222;
            }
            catch (IOException iOException) {
                // empty catch block
            }
            throw throwable2222;
        }

        public void onReceive(Context context, Intent intent) {
            this.sendScpResults();
            CdmaServiceCategoryProgramHandler.this.log("mScpResultsReceiver finished");
            if (CdmaServiceCategoryProgramHandler.this.mReceiverCount.decrementAndGet() == 0) {
                CdmaServiceCategoryProgramHandler.this.sendMessage(2);
            }
        }
    };

    CdmaServiceCategoryProgramHandler(Context context, CommandsInterface commandsInterface) {
        super("CdmaServiceCategoryProgramHandler", context, null);
        this.mContext = context;
        this.mCi = commandsInterface;
    }

    private boolean handleServiceCategoryProgramData(SmsMessage arrstring) {
        ArrayList arrayList = arrstring.getSmsCbProgramData();
        if (arrayList == null) {
            this.loge("handleServiceCategoryProgramData: program data list is null!");
            return false;
        }
        Intent intent = new Intent("android.provider.Telephony.SMS_SERVICE_CATEGORY_PROGRAM_DATA_RECEIVED");
        intent.putExtra("sender", arrstring.getOriginatingAddress());
        intent.putParcelableArrayListExtra("program_data", arrayList);
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)this.mPhone.getPhoneId());
        arrstring = this.mContext.getResources().getStringArray(17236002);
        this.mReceiverCount.addAndGet(arrstring.length);
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            intent.setPackage(arrstring[i]);
            this.mContext.sendOrderedBroadcast(intent, "android.permission.RECEIVE_SMS", 16, this.mScpResultsReceiver, this.getHandler(), -1, null, null);
        }
        return true;
    }

    static CdmaServiceCategoryProgramHandler makeScpHandler(Context object, CommandsInterface commandsInterface) {
        object = new CdmaServiceCategoryProgramHandler((Context)object, commandsInterface);
        object.start();
        return object;
    }

    @Override
    protected boolean handleSmsMessage(Message message) {
        if (message.obj instanceof SmsMessage) {
            return this.handleServiceCategoryProgramData((SmsMessage)message.obj);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleMessage got object of type: ");
        stringBuilder.append(message.obj.getClass().getName());
        this.loge(stringBuilder.toString());
        return false;
    }

}

