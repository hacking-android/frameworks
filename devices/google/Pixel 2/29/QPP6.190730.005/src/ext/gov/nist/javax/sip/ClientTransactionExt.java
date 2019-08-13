/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.TransactionExt;
import javax.sip.ClientTransaction;
import javax.sip.address.Hop;

public interface ClientTransactionExt
extends ClientTransaction,
TransactionExt {
    @Override
    public void alertIfStillInCallingStateBy(int var1);

    @Override
    public Hop getNextHop();

    public boolean isSecure();

    @Override
    public void setNotifyOnRetransmit(boolean var1);
}

