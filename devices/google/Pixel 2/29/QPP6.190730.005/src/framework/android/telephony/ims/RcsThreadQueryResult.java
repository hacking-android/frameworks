/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.Rcs1To1Thread;
import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsGroupThread;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims.RcsThread;
import android.telephony.ims.RcsThreadQueryResultParcelable;
import android.telephony.ims._$$Lambda$RcsThreadQueryResult$HsaNrgQR1ZYF_F0J6msBz3OMF6s;
import com.android.ims.RcsTypeIdPair;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RcsThreadQueryResult {
    private final RcsControllerCall mRcsControllerCall;
    private final RcsThreadQueryResultParcelable mRcsThreadQueryResultParcelable;

    RcsThreadQueryResult(RcsControllerCall rcsControllerCall, RcsThreadQueryResultParcelable rcsThreadQueryResultParcelable) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRcsThreadQueryResultParcelable = rcsThreadQueryResultParcelable;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mRcsThreadQueryResultParcelable.mContinuationToken;
    }

    public List<RcsThread> getThreads() {
        return this.mRcsThreadQueryResultParcelable.mRcsThreadIds.stream().map(new _$$Lambda$RcsThreadQueryResult$HsaNrgQR1ZYF_F0J6msBz3OMF6s(this)).collect(Collectors.toList());
    }

    public /* synthetic */ RcsThread lambda$getThreads$0$RcsThreadQueryResult(RcsTypeIdPair object) {
        object = ((RcsTypeIdPair)object).getType() == 0 ? new Rcs1To1Thread(this.mRcsControllerCall, ((RcsTypeIdPair)object).getId()) : new RcsGroupThread(this.mRcsControllerCall, ((RcsTypeIdPair)object).getId());
        return object;
    }
}

