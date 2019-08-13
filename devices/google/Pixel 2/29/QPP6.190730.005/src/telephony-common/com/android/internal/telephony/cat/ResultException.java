/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 */
package com.android.internal.telephony.cat;

import android.annotation.UnsupportedAppUsage;
import com.android.internal.telephony.cat.CatException;
import com.android.internal.telephony.cat.ResultCode;

public class ResultException
extends CatException {
    private int mAdditionalInfo;
    private String mExplanation;
    private ResultCode mResult;

    @UnsupportedAppUsage
    public ResultException(ResultCode resultCode) {
        switch (resultCode) {
            default: {
                this.mResult = resultCode;
                this.mAdditionalInfo = -1;
                this.mExplanation = "";
                return;
            }
            case TERMINAL_CRNTLY_UNABLE_TO_PROCESS: 
            case NETWORK_CRNTLY_UNABLE_TO_PROCESS: 
            case LAUNCH_BROWSER_ERROR: 
            case MULTI_CARDS_CMD_ERROR: 
            case USIM_CALL_CONTROL_PERMANENT: 
            case BIP_ERROR: 
            case FRAMES_ERROR: 
            case MMS_ERROR: 
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("For result code, ");
        stringBuilder.append((Object)resultCode);
        stringBuilder.append(", additional information must be given!");
        throw new AssertionError((Object)stringBuilder.toString());
    }

    public ResultException(ResultCode resultCode, int n) {
        this(resultCode);
        if (n >= 0) {
            this.mAdditionalInfo = n;
            return;
        }
        throw new AssertionError((Object)"Additional info must be greater than zero!");
    }

    public ResultException(ResultCode resultCode, int n, String string) {
        this(resultCode, n);
        this.mExplanation = string;
    }

    public ResultException(ResultCode resultCode, String string) {
        this(resultCode);
        this.mExplanation = string;
    }

    public int additionalInfo() {
        return this.mAdditionalInfo;
    }

    public String explanation() {
        return this.mExplanation;
    }

    public boolean hasAdditionalInfo() {
        boolean bl = this.mAdditionalInfo >= 0;
        return bl;
    }

    public ResultCode result() {
        return this.mResult;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("result=");
        stringBuilder.append((Object)this.mResult);
        stringBuilder.append(" additionalInfo=");
        stringBuilder.append(this.mAdditionalInfo);
        stringBuilder.append(" explantion=");
        stringBuilder.append(this.mExplanation);
        return stringBuilder.toString();
    }

}

