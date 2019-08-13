/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsParticipant;
import android.telephony.ims.RcsParticipantQueryResultParcelable;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims._$$Lambda$RcsParticipantQueryResult$5cUqqqG_A5Xe8Jrc2zruOvBMj44;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RcsParticipantQueryResult {
    private final RcsControllerCall mRcsControllerCall;
    private final RcsParticipantQueryResultParcelable mRcsParticipantQueryResultParcelable;

    RcsParticipantQueryResult(RcsControllerCall rcsControllerCall, RcsParticipantQueryResultParcelable rcsParticipantQueryResultParcelable) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRcsParticipantQueryResultParcelable = rcsParticipantQueryResultParcelable;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mRcsParticipantQueryResultParcelable.mContinuationToken;
    }

    public List<RcsParticipant> getParticipants() {
        return this.mRcsParticipantQueryResultParcelable.mParticipantIds.stream().map(new _$$Lambda$RcsParticipantQueryResult$5cUqqqG_A5Xe8Jrc2zruOvBMj44(this)).collect(Collectors.toList());
    }

    public /* synthetic */ RcsParticipant lambda$getParticipants$0$RcsParticipantQueryResult(Integer n) {
        return new RcsParticipant(this.mRcsControllerCall, n);
    }
}

