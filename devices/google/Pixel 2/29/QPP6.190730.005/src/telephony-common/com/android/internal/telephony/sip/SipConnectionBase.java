/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.SystemClock
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.sip;

import android.os.SystemClock;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.sip.SipPhone;

abstract class SipConnectionBase
extends Connection {
    private static final boolean DBG = true;
    private static final String LOG_TAG = "SipConnBase";
    private static final boolean VDBG = false;
    private long mConnectTime;
    private long mConnectTimeReal;
    private long mCreateTime;
    private long mDisconnectTime;
    private long mDuration = -1L;
    private long mHoldingStartTime;

    SipConnectionBase(String string) {
        super(3);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SipConnectionBase: ctor dialString=");
        stringBuilder.append(SipPhone.hidePii(string));
        this.log(stringBuilder.toString());
        this.mPostDialString = PhoneNumberUtils.extractPostDialPortion((String)string);
        this.mCreateTime = System.currentTimeMillis();
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    @Override
    public void cancelPostDial() {
        this.log("cancelPostDial: ignore");
    }

    @Override
    public long getConnectTime() {
        return this.mConnectTime;
    }

    @Override
    public long getConnectTimeReal() {
        return this.mConnectTimeReal;
    }

    @Override
    public long getCreateTime() {
        return this.mCreateTime;
    }

    @Override
    public long getDisconnectTime() {
        return this.mDisconnectTime;
    }

    @Override
    public long getDurationMillis() {
        long l = this.mConnectTimeReal == 0L ? 0L : (this.mDuration < 0L ? SystemClock.elapsedRealtime() - this.mConnectTimeReal : this.mDuration);
        return l;
    }

    @Override
    public long getHoldDurationMillis() {
        long l = this.getState() != Call.State.HOLDING ? 0L : SystemClock.elapsedRealtime() - this.mHoldingStartTime;
        return l;
    }

    @Override
    public long getHoldingStartTime() {
        return this.mHoldingStartTime;
    }

    @Override
    public int getNumberPresentation() {
        return 1;
    }

    @Override
    public Connection getOrigConnection() {
        return null;
    }

    protected abstract Phone getPhone();

    @Override
    public int getPreciseDisconnectCause() {
        return 0;
    }

    @Override
    public UUSInfo getUUSInfo() {
        return null;
    }

    @Override
    public String getVendorDisconnectCause() {
        return null;
    }

    @Override
    public boolean isMultiparty() {
        return false;
    }

    @Override
    public void proceedAfterWaitChar() {
        this.log("proceedAfterWaitChar: ignore");
    }

    @Override
    public void proceedAfterWildChar(String string) {
        this.log("proceedAfterWildChar: ignore");
    }

    void setDisconnectCause(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setDisconnectCause: prev=");
        stringBuilder.append(this.mCause);
        stringBuilder.append(" new=");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        this.mCause = n;
    }

    protected void setState(Call.State state) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setState: state=");
        stringBuilder.append((Object)((Object)state));
        this.log(stringBuilder.toString());
        int n = 1.$SwitchMap$com$android$internal$telephony$Call$State[state.ordinal()];
        if (n != 1) {
            if (n != 2) {
                if (n == 3) {
                    this.mHoldingStartTime = SystemClock.elapsedRealtime();
                }
            } else {
                this.mDuration = this.getDurationMillis();
                this.mDisconnectTime = System.currentTimeMillis();
            }
        } else if (this.mConnectTime == 0L) {
            this.mConnectTimeReal = SystemClock.elapsedRealtime();
            this.mConnectTime = System.currentTimeMillis();
        }
    }

}

