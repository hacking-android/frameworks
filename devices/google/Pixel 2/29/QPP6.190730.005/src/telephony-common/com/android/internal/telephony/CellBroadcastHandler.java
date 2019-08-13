/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.UserHandle
 *  android.provider.Settings
 *  android.provider.Settings$Secure
 *  android.telephony.SmsCbMessage
 *  android.telephony.SubscriptionManager
 *  android.util.LocalLog
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.UserHandle;
import android.provider.Settings;
import android.telephony.SmsCbMessage;
import android.telephony.SubscriptionManager;
import android.util.LocalLog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.WakeLockStateMachine;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class CellBroadcastHandler
extends WakeLockStateMachine {
    private static final String EXTRA_MESSAGE = "message";
    private final LocalLog mLocalLog = new LocalLog(100);

    private CellBroadcastHandler(Context context, Phone phone) {
        this("CellBroadcastHandler", context, phone);
    }

    protected CellBroadcastHandler(String string, Context context, Phone phone) {
        super(string, context, phone);
    }

    public static CellBroadcastHandler makeCellBroadcastHandler(Context object, Phone phone) {
        object = new CellBroadcastHandler((Context)object, phone);
        object.start();
        return object;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.println("CellBroadcastHandler:");
        this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
    }

    protected void handleBroadcastSms(SmsCbMessage intent) {
        TelephonyMetrics.getInstance().writeNewCBSms(this.mPhone.getPhoneId(), intent.getMessageFormat(), intent.getMessagePriority(), intent.isCmasMessage(), intent.isEtwsMessage(), intent.getServiceCategory(), intent.getSerialNumber(), System.currentTimeMillis());
        if (intent.isEmergencyMessage()) {
            String string;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Dispatching emergency SMS CB, SmsCbMessage is: ");
            charSequence.append((Object)intent);
            charSequence = charSequence.toString();
            this.log((String)charSequence);
            this.mLocalLog.log((String)charSequence);
            charSequence = new Intent("android.provider.Telephony.SMS_EMERGENCY_CB_RECEIVED");
            charSequence.addFlags(268435456);
            charSequence.putExtra(EXTRA_MESSAGE, (Parcelable)intent);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)charSequence, (int)this.mPhone.getPhoneId());
            if (Build.IS_DEBUGGABLE && (string = Settings.Secure.getString((ContentResolver)this.mContext.getContentResolver(), (String)"cmas_additional_broadcast_pkg")) != null) {
                intent = new Intent((Intent)charSequence);
                intent.setPackage(string);
                this.mContext.sendOrderedBroadcastAsUser(intent, UserHandle.ALL, "android.permission.RECEIVE_EMERGENCY_BROADCAST", 17, null, this.getHandler(), -1, null, null);
            }
            intent = this.mContext.getResources().getStringArray(17236002);
            this.mReceiverCount.addAndGet(((String[])intent).length);
            int n = ((Intent)intent).length;
            for (int i = 0; i < n; ++i) {
                charSequence.setPackage((String)intent[i]);
                this.mContext.sendOrderedBroadcastAsUser((Intent)charSequence, UserHandle.ALL, "android.permission.RECEIVE_EMERGENCY_BROADCAST", 17, this.mReceiver, this.getHandler(), -1, null, null);
            }
        } else {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Dispatching SMS CB, SmsCbMessage is: ");
            charSequence.append((Object)intent);
            charSequence = charSequence.toString();
            this.log((String)charSequence);
            this.mLocalLog.log((String)charSequence);
            charSequence = new Intent("android.provider.Telephony.SMS_CB_RECEIVED");
            charSequence.addFlags(16777216);
            charSequence.putExtra(EXTRA_MESSAGE, (Parcelable)intent);
            SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)charSequence, (int)this.mPhone.getPhoneId());
            this.mReceiverCount.incrementAndGet();
            this.mContext.sendOrderedBroadcastAsUser((Intent)charSequence, UserHandle.ALL, "android.permission.RECEIVE_SMS", 16, this.mReceiver, this.getHandler(), -1, null, null);
        }
    }

    @Override
    protected boolean handleSmsMessage(Message message) {
        if (message.obj instanceof SmsCbMessage) {
            this.handleBroadcastSms((SmsCbMessage)message.obj);
            return true;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleMessage got object of type: ");
        stringBuilder.append(message.obj.getClass().getName());
        this.loge(stringBuilder.toString());
        return false;
    }
}

