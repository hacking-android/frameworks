/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.Transaction;
import javax.sip.message.Response;

public interface ServerTransaction
extends Transaction {
    public void enableRetransmissionAlerts() throws SipException;

    public ServerTransaction getCanceledInviteTransaction();

    public void sendResponse(Response var1) throws SipException, InvalidArgumentException;
}

