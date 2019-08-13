/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.ActivityManager
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class IntentBroadcaster {
    private static final String TAG = "IntentBroadcaster";
    private static IntentBroadcaster sIntentBroadcaster;
    private Map<Integer, Intent> mRebroadcastIntents = new HashMap<Integer, Intent>();
    private final BroadcastReceiver mReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onReceive(Context object, Intent intent) {
            if (!intent.getAction().equals("android.intent.action.USER_UNLOCKED")) return;
            object = IntentBroadcaster.this.mRebroadcastIntents;
            synchronized (object) {
                Iterator iterator = IntentBroadcaster.this.mRebroadcastIntents.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = iterator.next();
                    intent = (Intent)entry.getValue();
                    intent.putExtra("rebroadcastOnUnlock", true);
                    iterator.remove();
                    IntentBroadcaster intentBroadcaster = IntentBroadcaster.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Rebroadcasting intent ");
                    stringBuilder.append(intent.getAction());
                    stringBuilder.append(" ");
                    stringBuilder.append(intent.getStringExtra("ss"));
                    stringBuilder.append(" for slotId ");
                    stringBuilder.append(entry.getKey());
                    intentBroadcaster.logd(stringBuilder.toString());
                    ActivityManager.broadcastStickyIntent((Intent)intent, (int)-1);
                }
                return;
            }
        }
    };

    private IntentBroadcaster(Context context) {
        context.registerReceiver(this.mReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
    }

    public static IntentBroadcaster getInstance() {
        return sIntentBroadcaster;
    }

    public static IntentBroadcaster getInstance(Context context) {
        if (sIntentBroadcaster == null) {
            sIntentBroadcaster = new IntentBroadcaster(context);
        }
        return sIntentBroadcaster;
    }

    private void logd(String string) {
        Log.d((String)TAG, (String)string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void broadcastStickyIntent(Intent intent, int n) {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("Broadcasting and adding intent for rebroadcast: ");
        ((StringBuilder)object).append(intent.getAction());
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(intent.getStringExtra("ss"));
        ((StringBuilder)object).append(" for slotId ");
        ((StringBuilder)object).append(n);
        this.logd(((StringBuilder)object).toString());
        object = this.mRebroadcastIntents;
        synchronized (object) {
            ActivityManager.broadcastStickyIntent((Intent)intent, (int)-1);
            this.mRebroadcastIntents.put(n, intent);
            return;
        }
    }

}

