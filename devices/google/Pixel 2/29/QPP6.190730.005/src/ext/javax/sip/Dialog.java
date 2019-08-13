/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.io.Serializable;
import java.util.Iterator;
import javax.sip.ClientTransaction;
import javax.sip.DialogDoesNotExistException;
import javax.sip.DialogState;
import javax.sip.InvalidArgumentException;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.Transaction;
import javax.sip.TransactionDoesNotExistException;
import javax.sip.address.Address;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public interface Dialog
extends Serializable {
    public Request createAck(long var1) throws InvalidArgumentException, SipException;

    public Request createPrack(Response var1) throws DialogDoesNotExistException, SipException;

    public Response createReliableProvisionalResponse(int var1) throws InvalidArgumentException, SipException;

    public Request createRequest(String var1) throws SipException;

    public void delete();

    public Object getApplicationData();

    public CallIdHeader getCallId();

    public String getDialogId();

    public Transaction getFirstTransaction();

    public Address getLocalParty();

    public long getLocalSeqNumber();

    public int getLocalSequenceNumber();

    public String getLocalTag();

    public Address getRemoteParty();

    public long getRemoteSeqNumber();

    public int getRemoteSequenceNumber();

    public String getRemoteTag();

    public Address getRemoteTarget();

    public Iterator getRouteSet();

    public SipProvider getSipProvider();

    public DialogState getState();

    public void incrementLocalSequenceNumber();

    public boolean isSecure();

    public boolean isServer();

    public void sendAck(Request var1) throws SipException;

    public void sendReliableProvisionalResponse(Response var1) throws SipException;

    public void sendRequest(ClientTransaction var1) throws TransactionDoesNotExistException, SipException;

    public void setApplicationData(Object var1);

    public void setBackToBackUserAgent();

    public void terminateOnBye(boolean var1) throws SipException;
}

