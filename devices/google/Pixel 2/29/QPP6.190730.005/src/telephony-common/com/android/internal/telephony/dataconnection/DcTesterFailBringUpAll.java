/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Build
 *  android.os.Handler
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.dataconnection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Handler;
import android.telephony.Rlog;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.DcFailBringUp;

public class DcTesterFailBringUpAll {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "DcTesterFailBrinupAll";
    private String mActionFailBringUp;
    private DcFailBringUp mFailBringUp;
    private BroadcastReceiver mIntentReceiver;
    private Phone mPhone;

    DcTesterFailBringUpAll(Phone phone, Handler handler) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DcFailBringUp.INTENT_BASE);
        stringBuilder.append(".");
        stringBuilder.append("action_fail_bringup");
        this.mActionFailBringUp = stringBuilder.toString();
        this.mFailBringUp = new DcFailBringUp();
        this.mIntentReceiver = new BroadcastReceiver(){

            public void onReceive(Context object, Intent object2) {
                object = object2.getAction();
                DcTesterFailBringUpAll dcTesterFailBringUpAll = DcTesterFailBringUpAll.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sIntentReceiver.onReceive: action=");
                stringBuilder.append((String)object);
                dcTesterFailBringUpAll.log(stringBuilder.toString());
                if (((String)object).equals(DcTesterFailBringUpAll.this.mActionFailBringUp)) {
                    DcTesterFailBringUpAll.this.mFailBringUp.saveParameters((Intent)object2, "sFailBringUp");
                } else if (((String)object).equals(DcTesterFailBringUpAll.this.mPhone.getActionDetached())) {
                    DcTesterFailBringUpAll.this.log("simulate detaching");
                    DcTesterFailBringUpAll.this.mFailBringUp.saveParameters(Integer.MAX_VALUE, 65540, -1);
                } else if (((String)object).equals(DcTesterFailBringUpAll.this.mPhone.getActionAttached())) {
                    DcTesterFailBringUpAll.this.log("simulate attaching");
                    DcTesterFailBringUpAll.this.mFailBringUp.saveParameters(0, 0, -1);
                } else {
                    object2 = DcTesterFailBringUpAll.this;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("onReceive: unknown action=");
                    stringBuilder.append((String)object);
                    ((DcTesterFailBringUpAll)object2).log(stringBuilder.toString());
                }
            }
        };
        this.mPhone = phone;
        if (Build.IS_DEBUGGABLE) {
            stringBuilder = new IntentFilter();
            stringBuilder.addAction(this.mActionFailBringUp);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("register for intent action=");
            stringBuilder2.append(this.mActionFailBringUp);
            this.log(stringBuilder2.toString());
            stringBuilder.addAction(this.mPhone.getActionDetached());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("register for intent action=");
            stringBuilder2.append(this.mPhone.getActionDetached());
            this.log(stringBuilder2.toString());
            stringBuilder.addAction(this.mPhone.getActionAttached());
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("register for intent action=");
            stringBuilder2.append(this.mPhone.getActionAttached());
            this.log(stringBuilder2.toString());
            phone.getContext().registerReceiver(this.mIntentReceiver, (IntentFilter)stringBuilder, null, handler);
        }
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    void dispose() {
        if (Build.IS_DEBUGGABLE) {
            this.mPhone.getContext().unregisterReceiver(this.mIntentReceiver);
        }
    }

    public DcFailBringUp getDcFailBringUp() {
        return this.mFailBringUp;
    }

}

