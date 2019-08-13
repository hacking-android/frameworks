/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Build
 *  android.telephony.Rlog
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.Rlog;
import com.android.internal.annotations.VisibleForTesting;

@TargetApi(value=8)
public class RilWakelockInfo {
    private final String LOG_TAG = RilWakelockInfo.class.getSimpleName();
    private int mConcurrentRequests;
    private long mLastAggregatedTime;
    private long mRequestTime;
    private long mResponseTime;
    private int mRilRequestSent;
    private int mTokenNumber;
    private long mWakelockTimeAttributedSoFar;

    RilWakelockInfo(int n, int n2, int n3, long l) {
        n3 = this.validateConcurrentRequests(n3);
        this.mRilRequestSent = n;
        this.mTokenNumber = n2;
        this.mConcurrentRequests = n3;
        this.mRequestTime = l;
        this.mWakelockTimeAttributedSoFar = 0L;
        this.mLastAggregatedTime = l;
    }

    private int validateConcurrentRequests(int n) {
        int n2 = n;
        if (n <= 0) {
            if (!Build.IS_DEBUGGABLE) {
                n2 = 1;
            } else {
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("concurrentRequests should always be greater than 0.");
                Rlog.e((String)this.LOG_TAG, (String)illegalArgumentException.toString());
                throw illegalArgumentException;
            }
        }
        return n2;
    }

    @VisibleForTesting
    public int getConcurrentRequests() {
        return this.mConcurrentRequests;
    }

    int getRilRequestSent() {
        return this.mRilRequestSent;
    }

    int getTokenNumber() {
        return this.mTokenNumber;
    }

    long getWakelockTimeAttributedToClient() {
        return this.mWakelockTimeAttributedSoFar;
    }

    void setResponseTime(long l) {
        this.updateTime(l);
        this.mResponseTime = l;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WakelockInfo{rilRequestSent=");
        stringBuilder.append(this.mRilRequestSent);
        stringBuilder.append(", tokenNumber=");
        stringBuilder.append(this.mTokenNumber);
        stringBuilder.append(", requestTime=");
        stringBuilder.append(this.mRequestTime);
        stringBuilder.append(", responseTime=");
        stringBuilder.append(this.mResponseTime);
        stringBuilder.append(", mWakelockTimeAttributed=");
        stringBuilder.append(this.mWakelockTimeAttributedSoFar);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    void updateConcurrentRequests(int n, long l) {
        n = this.validateConcurrentRequests(n);
        this.updateTime(l);
        this.mConcurrentRequests = n;
    }

    void updateTime(long l) {
        synchronized (this) {
            this.mWakelockTimeAttributedSoFar += (l - this.mLastAggregatedTime) / (long)this.mConcurrentRequests;
            this.mLastAggregatedTime = l;
            return;
        }
    }
}

