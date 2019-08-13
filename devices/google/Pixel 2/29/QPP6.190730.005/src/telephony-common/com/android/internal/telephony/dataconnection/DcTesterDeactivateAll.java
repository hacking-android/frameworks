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
import com.android.internal.telephony.dataconnection.DataConnection;
import com.android.internal.telephony.dataconnection.DcController;
import java.util.ArrayList;
import java.util.Iterator;

public class DcTesterDeactivateAll {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "DcTesterDeacativateAll";
    public static String sActionDcTesterDeactivateAll = "com.android.internal.telephony.dataconnection.action_deactivate_all";
    private DcController mDcc;
    private Phone mPhone;
    protected BroadcastReceiver sIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("sIntentReceiver.onReceive: action=");
            ((StringBuilder)object2).append((String)object);
            DcTesterDeactivateAll.log(((StringBuilder)object2).toString());
            if (!((String)object).equals(sActionDcTesterDeactivateAll) && !((String)object).equals(DcTesterDeactivateAll.this.mPhone.getActionDetached())) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("onReceive: unknown action=");
                ((StringBuilder)object2).append((String)object);
                DcTesterDeactivateAll.log(((StringBuilder)object2).toString());
            } else {
                DcTesterDeactivateAll.log("Send DEACTIVATE to all Dcc's");
                if (DcTesterDeactivateAll.this.mDcc != null) {
                    object = DcTesterDeactivateAll.access$200((DcTesterDeactivateAll)DcTesterDeactivateAll.this).mDcListAll.iterator();
                    while (object.hasNext()) {
                        ((DataConnection)((Object)object.next())).tearDownNow();
                    }
                } else {
                    DcTesterDeactivateAll.log("onReceive: mDcc is null, ignoring");
                }
            }
        }
    };

    DcTesterDeactivateAll(Phone phone, DcController dcController, Handler handler) {
        this.mPhone = phone;
        this.mDcc = dcController;
        if (Build.IS_DEBUGGABLE) {
            dcController = new IntentFilter();
            dcController.addAction(sActionDcTesterDeactivateAll);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("register for intent action=");
            stringBuilder.append(sActionDcTesterDeactivateAll);
            DcTesterDeactivateAll.log(stringBuilder.toString());
            dcController.addAction(this.mPhone.getActionDetached());
            stringBuilder = new StringBuilder();
            stringBuilder.append("register for intent action=");
            stringBuilder.append(this.mPhone.getActionDetached());
            DcTesterDeactivateAll.log(stringBuilder.toString());
            phone.getContext().registerReceiver(this.sIntentReceiver, (IntentFilter)dcController, null, handler);
        }
    }

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    void dispose() {
        if (Build.IS_DEBUGGABLE) {
            this.mPhone.getContext().unregisterReceiver(this.sIntentReceiver);
        }
    }

}

