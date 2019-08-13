/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.telephony.Rlog
 *  android.util.LocalLog
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.Rlog;
import android.util.LocalLog;
import com.android.internal.telephony.Phone;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;

public class SimActivationTracker {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "SAT";
    private static final boolean VDBG = Rlog.isLoggable((String)"SAT", (int)2);
    private int mDataActivationState;
    private final LocalLog mDataActivationStateLog = new LocalLog(10);
    private Phone mPhone;
    private final BroadcastReceiver mReceiver;
    private int mVoiceActivationState;
    private final LocalLog mVoiceActivationStateLog = new LocalLog(10);

    public SimActivationTracker(Phone phone) {
        this.mPhone = phone;
        this.mVoiceActivationState = 0;
        this.mDataActivationState = 0;
        this.mReceiver = new BroadcastReceiver(){

            public void onReceive(Context object, Intent intent) {
                String string = intent.getAction();
                if (VDBG) {
                    object = SimActivationTracker.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("action: ");
                    stringBuilder.append(string);
                    ((SimActivationTracker)object).log(stringBuilder.toString());
                }
                if ("android.intent.action.SIM_STATE_CHANGED".equals(string) && "ABSENT".equals(intent.getStringExtra("ss"))) {
                    SimActivationTracker.this.log("onSimAbsent, reset activation state to UNKNOWN");
                    SimActivationTracker.this.setVoiceActivationState(0);
                    SimActivationTracker.this.setDataActivationState(0);
                }
            }
        };
        phone = new IntentFilter("android.intent.action.SIM_STATE_CHANGED");
        this.mPhone.getContext().registerReceiver(this.mReceiver, (IntentFilter)phone);
    }

    private static boolean isValidActivationState(int n) {
        return n == 0 || n == 1 || n == 2 || n == 3 || n == 4;
    }

    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mPhone.getPhoneId());
        stringBuilder.append("]");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private static String toString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return "invalid";
                        }
                        return "restricted";
                    }
                    return "deactivated";
                }
                return "activated";
            }
            return "activating";
        }
        return "unknown";
    }

    public void dispose() {
        this.mPhone.getContext().unregisterReceiver(this.mReceiver);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        printWriter.println(" mVoiceActivationState Log:");
        indentingPrintWriter.increaseIndent();
        this.mVoiceActivationStateLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        printWriter.println(" mDataActivationState Log:");
        indentingPrintWriter.increaseIndent();
        this.mDataActivationStateLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
    }

    public int getDataActivationState() {
        return this.mDataActivationState;
    }

    public int getVoiceActivationState() {
        return this.mVoiceActivationState;
    }

    public void setDataActivationState(int n) {
        if (SimActivationTracker.isValidActivationState(n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setDataActivationState=");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
            this.mDataActivationState = n;
            this.mDataActivationStateLog.log(SimActivationTracker.toString(n));
            this.mPhone.notifyDataActivationStateChanged(n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid data activation state: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setVoiceActivationState(int n) {
        if (SimActivationTracker.isValidActivationState(n) && 4 != n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setVoiceActivationState=");
            stringBuilder.append(n);
            this.log(stringBuilder.toString());
            this.mVoiceActivationState = n;
            this.mVoiceActivationStateLog.log(SimActivationTracker.toString(n));
            this.mPhone.notifyVoiceActivationStateChanged(n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid voice activation state: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

}

