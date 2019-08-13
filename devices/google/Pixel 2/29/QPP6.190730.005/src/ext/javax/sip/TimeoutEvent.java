/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.ClientTransaction;
import javax.sip.ServerTransaction;
import javax.sip.Timeout;
import javax.sip.TransactionTerminatedEvent;

public class TimeoutEvent
extends TransactionTerminatedEvent {
    private Timeout mTimeout;

    public TimeoutEvent(Object object, ClientTransaction clientTransaction, Timeout timeout) {
        super(object, clientTransaction);
        this.mTimeout = timeout;
    }

    public TimeoutEvent(Object object, ServerTransaction serverTransaction, Timeout timeout) {
        super(object, serverTransaction);
        this.mTimeout = timeout;
    }

    public Timeout getTimeout() {
        return this.mTimeout;
    }
}

