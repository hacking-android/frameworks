/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.stack.SIPTransaction;
import java.util.EventObject;

class EventWrapper {
    protected EventObject sipEvent;
    protected SIPTransaction transaction;

    EventWrapper(EventObject eventObject, SIPTransaction sIPTransaction) {
        this.sipEvent = eventObject;
        this.transaction = sIPTransaction;
    }
}

