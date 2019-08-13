/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.SipException;
import javax.sip.Transaction;
import javax.sip.address.Hop;
import javax.sip.message.Request;

public interface ClientTransaction
extends Transaction {
    public void alertIfStillInCallingStateBy(int var1);

    public Request createAck() throws SipException;

    public Request createCancel() throws SipException;

    public Hop getNextHop();

    public void sendRequest() throws SipException;

    public void setNotifyOnRetransmit(boolean var1);
}

