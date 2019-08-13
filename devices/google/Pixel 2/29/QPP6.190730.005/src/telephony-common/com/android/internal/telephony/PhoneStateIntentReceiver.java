/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.internal.telephony.PhoneConstants;

@Deprecated
public final class PhoneStateIntentReceiver
extends BroadcastReceiver {
    private static final boolean DBG = false;
    private static final String LOG_TAG = "PhoneStatIntentReceiver";
    private static final int NOTIF_PHONE = 1;
    private static final int NOTIF_SERVICE = 2;
    private static final int NOTIF_SIGNAL = 4;
    private int mAsuEventWhat;
    private Context mContext;
    private IntentFilter mFilter = new IntentFilter();
    PhoneConstants.State mPhoneState = PhoneConstants.State.IDLE;
    private int mPhoneStateEventWhat;
    ServiceState mServiceState = new ServiceState();
    private int mServiceStateEventWhat;
    @UnsupportedAppUsage
    SignalStrength mSignalStrength = new SignalStrength();
    private Handler mTarget;
    @UnsupportedAppUsage
    private int mWants;

    public PhoneStateIntentReceiver() {
    }

    @UnsupportedAppUsage
    public PhoneStateIntentReceiver(Context context, Handler handler) {
        this();
        this.setContext(context);
        this.setTarget(handler);
    }

    public boolean getNotifyPhoneCallState() {
        int n = this.mWants;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean getNotifyServiceState() {
        boolean bl = (this.mWants & 2) != 0;
        return bl;
    }

    public boolean getNotifySignalStrength() {
        boolean bl = (this.mWants & 4) != 0;
        return bl;
    }

    public PhoneConstants.State getPhoneState() {
        if ((this.mWants & 1) != 0) {
            return this.mPhoneState;
        }
        throw new RuntimeException("client must call notifyPhoneCallState(int)");
    }

    public ServiceState getServiceState() {
        if ((this.mWants & 2) != 0) {
            return this.mServiceState;
        }
        throw new RuntimeException("client must call notifyServiceState(int)");
    }

    @UnsupportedAppUsage
    public int getSignalStrengthDbm() {
        if ((this.mWants & 4) != 0) {
            return this.mSignalStrength.getDbm();
        }
        throw new RuntimeException("client must call notifySignalStrength(int)");
    }

    public int getSignalStrengthLevelAsu() {
        if ((this.mWants & 4) != 0) {
            return this.mSignalStrength.getAsuLevel();
        }
        throw new RuntimeException("client must call notifySignalStrength(int)");
    }

    public void notifyPhoneCallState(int n) {
        this.mWants |= 1;
        this.mPhoneStateEventWhat = n;
        this.mFilter.addAction("android.intent.action.PHONE_STATE");
    }

    @UnsupportedAppUsage
    public void notifyServiceState(int n) {
        this.mWants |= 2;
        this.mServiceStateEventWhat = n;
        this.mFilter.addAction("android.intent.action.SERVICE_STATE");
    }

    @UnsupportedAppUsage
    public void notifySignalStrength(int n) {
        this.mWants |= 4;
        this.mAsuEventWhat = n;
        this.mFilter.addAction("android.intent.action.SIG_STR");
    }

    public void onReceive(Context object, Intent intent) {
        object = intent.getAction();
        try {
            if ("android.intent.action.SIG_STR".equals(object)) {
                this.mSignalStrength = SignalStrength.newFromBundle((Bundle)intent.getExtras());
                if (this.mTarget != null && this.getNotifySignalStrength()) {
                    object = Message.obtain((Handler)this.mTarget, (int)this.mAsuEventWhat);
                    this.mTarget.sendMessage((Message)object);
                }
            } else if ("android.intent.action.PHONE_STATE".equals(object)) {
                this.mPhoneState = Enum.valueOf(PhoneConstants.State.class, intent.getStringExtra("state"));
                if (this.mTarget != null && this.getNotifyPhoneCallState()) {
                    object = Message.obtain((Handler)this.mTarget, (int)this.mPhoneStateEventWhat);
                    this.mTarget.sendMessage((Message)object);
                }
            } else if ("android.intent.action.SERVICE_STATE".equals(object)) {
                this.mServiceState = ServiceState.newFromBundle((Bundle)intent.getExtras());
                if (this.mTarget != null && this.getNotifyServiceState()) {
                    object = Message.obtain((Handler)this.mTarget, (int)this.mServiceStateEventWhat);
                    this.mTarget.sendMessage((Message)object);
                }
            }
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("[PhoneStateIntentRecv] caught ");
            ((StringBuilder)object).append(exception);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
            exception.printStackTrace();
        }
    }

    @UnsupportedAppUsage
    public void registerIntent() {
        this.mContext.registerReceiver((BroadcastReceiver)this, this.mFilter);
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public void setTarget(Handler handler) {
        this.mTarget = handler;
    }

    @UnsupportedAppUsage
    public void unregisterIntent() {
        this.mContext.unregisterReceiver((BroadcastReceiver)this);
    }
}

