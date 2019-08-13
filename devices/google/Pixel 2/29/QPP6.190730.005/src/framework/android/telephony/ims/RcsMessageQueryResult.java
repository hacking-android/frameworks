/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsControllerCall;
import android.telephony.ims.RcsIncomingMessage;
import android.telephony.ims.RcsMessage;
import android.telephony.ims.RcsMessageQueryResultParcelable;
import android.telephony.ims.RcsOutgoingMessage;
import android.telephony.ims.RcsQueryContinuationToken;
import android.telephony.ims._$$Lambda$RcsMessageQueryResult$20XnTdVu75hlh0utIOyf1L_ZpTE;
import com.android.ims.RcsTypeIdPair;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RcsMessageQueryResult {
    private final RcsControllerCall mRcsControllerCall;
    private final RcsMessageQueryResultParcelable mRcsMessageQueryResultParcelable;

    RcsMessageQueryResult(RcsControllerCall rcsControllerCall, RcsMessageQueryResultParcelable rcsMessageQueryResultParcelable) {
        this.mRcsControllerCall = rcsControllerCall;
        this.mRcsMessageQueryResultParcelable = rcsMessageQueryResultParcelable;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mRcsMessageQueryResultParcelable.mContinuationToken;
    }

    public List<RcsMessage> getMessages() {
        return this.mRcsMessageQueryResultParcelable.mMessageTypeIdPairs.stream().map(new _$$Lambda$RcsMessageQueryResult$20XnTdVu75hlh0utIOyf1L_ZpTE(this)).collect(Collectors.toList());
    }

    public /* synthetic */ RcsMessage lambda$getMessages$0$RcsMessageQueryResult(RcsTypeIdPair object) {
        object = ((RcsTypeIdPair)object).getType() == 1 ? new RcsIncomingMessage(this.mRcsControllerCall, ((RcsTypeIdPair)object).getId()) : new RcsOutgoingMessage(this.mRcsControllerCall, ((RcsTypeIdPair)object).getId());
        return object;
    }
}

