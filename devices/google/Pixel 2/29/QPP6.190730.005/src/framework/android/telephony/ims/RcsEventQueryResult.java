/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.telephony.ims.RcsEvent;
import android.telephony.ims.RcsQueryContinuationToken;
import java.util.List;

public class RcsEventQueryResult {
    private RcsQueryContinuationToken mContinuationToken;
    private List<RcsEvent> mEvents;

    public RcsEventQueryResult(RcsQueryContinuationToken rcsQueryContinuationToken, List<RcsEvent> list) {
        this.mContinuationToken = rcsQueryContinuationToken;
        this.mEvents = list;
    }

    public RcsQueryContinuationToken getContinuationToken() {
        return this.mContinuationToken;
    }

    public List<RcsEvent> getEvents() {
        return this.mEvents;
    }
}

