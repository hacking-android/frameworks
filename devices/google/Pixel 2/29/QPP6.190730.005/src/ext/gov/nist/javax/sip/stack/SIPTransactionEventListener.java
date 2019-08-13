/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.stack.SIPTransactionErrorEvent;
import java.util.EventListener;

public interface SIPTransactionEventListener
extends EventListener {
    public void transactionErrorEvent(SIPTransactionErrorEvent var1);
}

