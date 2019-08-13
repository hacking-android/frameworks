/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PowerManager
 *  android.os.PowerManager$WakeLock
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  com.android.internal.telephony.SmsApplication
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SmsApplication;

public class SmsStorageMonitor
extends Handler {
    private static final int EVENT_ICC_FULL = 1;
    private static final int EVENT_RADIO_ON = 3;
    private static final int EVENT_REPORT_MEMORY_STATUS_DONE = 2;
    private static final String TAG = "SmsStorageMonitor";
    private static final int WAKE_LOCK_TIMEOUT = 5000;
    @UnsupportedAppUsage
    final CommandsInterface mCi;
    private final Context mContext;
    Phone mPhone;
    private boolean mReportMemoryStatusPending;
    private final BroadcastReceiver mResultReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            if (intent.getAction().equals("android.intent.action.DEVICE_STORAGE_FULL")) {
                object = SmsStorageMonitor.this;
                object.mStorageAvailable = false;
                object.mCi.reportSmsMemoryStatus(false, SmsStorageMonitor.this.obtainMessage(2));
            } else if (intent.getAction().equals("android.intent.action.DEVICE_STORAGE_NOT_FULL")) {
                object = SmsStorageMonitor.this;
                object.mStorageAvailable = true;
                object.mCi.reportSmsMemoryStatus(true, SmsStorageMonitor.this.obtainMessage(2));
            }
        }
    };
    boolean mStorageAvailable = true;
    private PowerManager.WakeLock mWakeLock;

    public SmsStorageMonitor(Phone phone) {
        this.mPhone = phone;
        this.mContext = phone.getContext();
        this.mCi = phone.mCi;
        this.createWakelock();
        this.mCi.setOnIccSmsFull(this, 1, null);
        this.mCi.registerForOn(this, 3, null);
        phone = new IntentFilter();
        phone.addAction("android.intent.action.DEVICE_STORAGE_FULL");
        phone.addAction("android.intent.action.DEVICE_STORAGE_NOT_FULL");
        this.mContext.registerReceiver(this.mResultReceiver, (IntentFilter)phone);
    }

    private void createWakelock() {
        this.mWakeLock = ((PowerManager)this.mContext.getSystemService("power")).newWakeLock(1, TAG);
        this.mWakeLock.setReferenceCounted(true);
    }

    private void handleIccFull() {
        Intent intent = new Intent("android.provider.Telephony.SIM_FULL");
        intent.setComponent(SmsApplication.getDefaultSimFullApplication((Context)this.mContext, (boolean)false));
        this.mWakeLock.acquire(5000L);
        SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)intent, (int)this.mPhone.getPhoneId());
        this.mContext.sendBroadcast(intent, "android.permission.RECEIVE_SMS");
    }

    public void dispose() {
        this.mCi.unSetOnIccSmsFull(this);
        this.mCi.unregisterForOn(this);
        this.mContext.unregisterReceiver(this.mResultReceiver);
    }

    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 1) {
            if (n != 2) {
                if (n == 3 && this.mReportMemoryStatusPending) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Sending pending memory status report : mStorageAvailable = ");
                    ((StringBuilder)object).append(this.mStorageAvailable);
                    Rlog.v((String)TAG, (String)((StringBuilder)object).toString());
                    this.mCi.reportSmsMemoryStatus(this.mStorageAvailable, this.obtainMessage(2));
                }
            } else if (((AsyncResult)object.obj).exception != null) {
                this.mReportMemoryStatusPending = true;
                object = new StringBuilder();
                ((StringBuilder)object).append("Memory status report to modem pending : mStorageAvailable = ");
                ((StringBuilder)object).append(this.mStorageAvailable);
                Rlog.v((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                this.mReportMemoryStatusPending = false;
            }
        } else {
            this.handleIccFull();
        }
    }

    public boolean isStorageAvailable() {
        return this.mStorageAvailable;
    }

}

