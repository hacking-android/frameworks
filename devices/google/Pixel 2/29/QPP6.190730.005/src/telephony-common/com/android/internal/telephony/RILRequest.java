/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.SystemClock
 *  android.os.WorkSource
 *  android.os.WorkSource$WorkChain
 *  android.telephony.Rlog
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$RILRequest
 *  com.android.internal.telephony.-$$Lambda$RILRequest$VaC9ddQXT8qxCl7rcNKtUadFQoI
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.WorkSource;
import android.telephony.Rlog;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony._$$Lambda$RILRequest$VaC9ddQXT8qxCl7rcNKtUadFQoI;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

public class RILRequest {
    static final String LOG_TAG = "RilRequest";
    private static final int MAX_POOL_SIZE = 4;
    static AtomicInteger sNextSerial;
    private static RILRequest sPool;
    private static int sPoolSize;
    private static Object sPoolSync;
    static Random sRandom;
    String mClientId;
    RILRequest mNext;
    @UnsupportedAppUsage
    int mRequest;
    @UnsupportedAppUsage
    Message mResult;
    @UnsupportedAppUsage
    int mSerial;
    long mStartTimeMs;
    int mWakeLockType;
    WorkSource mWorkSource;

    static {
        sRandom = new Random();
        sNextSerial = new AtomicInteger(0);
        sPoolSync = new Object();
        sPool = null;
        sPoolSize = 0;
    }

    private RILRequest() {
    }

    static /* synthetic */ int lambda$obtain$0(int n) {
        return (n + 1) % Integer.MAX_VALUE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    private static RILRequest obtain(int n, Message message) {
        RILRequest rILRequest = null;
        Object object = sPoolSync;
        synchronized (object) {
            if (sPool != null) {
                rILRequest = sPool;
                sPool = rILRequest.mNext;
                rILRequest.mNext = null;
                --sPoolSize;
            }
        }
        object = rILRequest;
        if (rILRequest == null) {
            object = new RILRequest();
        }
        ((RILRequest)object).mSerial = sNextSerial.getAndUpdate((IntUnaryOperator)_$$Lambda$RILRequest$VaC9ddQXT8qxCl7rcNKtUadFQoI.INSTANCE);
        ((RILRequest)object).mRequest = n;
        ((RILRequest)object).mResult = message;
        ((RILRequest)object).mWakeLockType = -1;
        ((RILRequest)object).mWorkSource = null;
        ((RILRequest)object).mStartTimeMs = SystemClock.elapsedRealtime();
        if (message == null) return object;
        if (message.getTarget() == null) throw new NullPointerException("Message target must not be null");
        return object;
    }

    public static RILRequest obtain(int n, Message object, WorkSource object2) {
        object = RILRequest.obtain(n, (Message)object);
        if (object2 != null) {
            ((RILRequest)object).mWorkSource = object2;
            ((RILRequest)object).mClientId = ((RILRequest)object).getWorkSourceClientId();
        } else {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("null workSource ");
            ((StringBuilder)object2).append(n);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
        }
        return object;
    }

    static void resetSerial() {
        sNextSerial.set(sRandom.nextInt(Integer.MAX_VALUE));
    }

    public int getRequest() {
        return this.mRequest;
    }

    public Message getResult() {
        return this.mResult;
    }

    public int getSerial() {
        return this.mSerial;
    }

    public String getWorkSourceClientId() {
        Object object = this.mWorkSource;
        if (object != null && !object.isEmpty()) {
            if (this.mWorkSource.size() > 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mWorkSource.get(0));
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(this.mWorkSource.getName(0));
                return ((StringBuilder)object).toString();
            }
            object = this.mWorkSource.getWorkChains();
            if (object != null && !((ArrayList)object).isEmpty()) {
                WorkSource.WorkChain workChain = (WorkSource.WorkChain)((ArrayList)object).get(0);
                object = new StringBuilder();
                ((StringBuilder)object).append(workChain.getAttributionUid());
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(workChain.getTags()[0]);
                return ((StringBuilder)object).toString();
            }
            return null;
        }
        return null;
    }

    @UnsupportedAppUsage
    void onError(int n, Object object) {
        CommandException commandException = CommandException.fromRilErrno(n);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.serialString());
        stringBuilder.append("< ");
        stringBuilder.append(RIL.requestToString(this.mRequest));
        stringBuilder.append(" error: ");
        stringBuilder.append(commandException);
        stringBuilder.append(" ret=");
        stringBuilder.append(RIL.retToString(this.mRequest, object));
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
        stringBuilder = this.mResult;
        if (stringBuilder != null) {
            AsyncResult.forMessage((Message)stringBuilder, (Object)object, (Throwable)commandException);
            this.mResult.sendToTarget();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void release() {
        Object object = sPoolSync;
        synchronized (object) {
            if (sPoolSize < 4) {
                this.mNext = sPool;
                sPool = this;
                ++sPoolSize;
                this.mResult = null;
                if (this.mWakeLockType != -1 && this.mWakeLockType == 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("RILRequest releasing with held wake lock: ");
                    stringBuilder.append(this.serialString());
                    Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                }
            }
            return;
        }
    }

    @UnsupportedAppUsage
    String serialString() {
        StringBuilder stringBuilder = new StringBuilder(8);
        String string = Integer.toString(this.mSerial % 10000);
        stringBuilder.append('[');
        int n = string.length();
        for (int i = 0; i < 4 - n; ++i) {
            stringBuilder.append('0');
        }
        stringBuilder.append(string);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

