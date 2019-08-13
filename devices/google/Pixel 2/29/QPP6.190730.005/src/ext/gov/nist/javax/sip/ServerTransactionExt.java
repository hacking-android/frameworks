/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.TransactionExt;
import javax.sip.ServerTransaction;

public interface ServerTransactionExt
extends ServerTransaction,
TransactionExt {
    @Override
    public ServerTransaction getCanceledInviteTransaction();
}

