/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.TooManyListenersException;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.SipStack;
import javax.sip.Transaction;
import javax.sip.TransactionAlreadyExistsException;
import javax.sip.TransactionUnavailableException;
import javax.sip.header.CallIdHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public interface SipProvider {
    public void addListeningPoint(ListeningPoint var1) throws ObjectInUseException;

    public void addSipListener(SipListener var1) throws TooManyListenersException;

    public ListeningPoint getListeningPoint();

    public ListeningPoint getListeningPoint(String var1);

    public ListeningPoint[] getListeningPoints();

    public CallIdHeader getNewCallId();

    public ClientTransaction getNewClientTransaction(Request var1) throws TransactionUnavailableException;

    public Dialog getNewDialog(Transaction var1) throws SipException;

    public ServerTransaction getNewServerTransaction(Request var1) throws TransactionAlreadyExistsException, TransactionUnavailableException;

    public SipStack getSipStack();

    public boolean isAutomaticDialogSupportEnabled();

    public void removeListeningPoint(ListeningPoint var1) throws ObjectInUseException;

    public void removeListeningPoints();

    public void removeSipListener(SipListener var1);

    public void sendRequest(Request var1) throws SipException;

    public void sendResponse(Response var1) throws SipException;

    public void setAutomaticDialogSupportEnabled(boolean var1);

    public void setListeningPoint(ListeningPoint var1) throws ObjectInUseException;
}

