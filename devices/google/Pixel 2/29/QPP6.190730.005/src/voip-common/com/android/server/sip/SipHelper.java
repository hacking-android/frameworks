/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.Rlog
 *  gov.nist.javax.sip.SipStackExt
 *  gov.nist.javax.sip.clientauthutils.AccountManager
 *  gov.nist.javax.sip.clientauthutils.AuthenticationHelper
 *  gov.nist.javax.sip.header.extensions.ReferredByHeader
 *  javax.sip.ClientTransaction
 *  javax.sip.Dialog
 *  javax.sip.DialogTerminatedEvent
 *  javax.sip.InvalidArgumentException
 *  javax.sip.ListeningPoint
 *  javax.sip.PeerUnavailableException
 *  javax.sip.RequestEvent
 *  javax.sip.ResponseEvent
 *  javax.sip.ServerTransaction
 *  javax.sip.SipException
 *  javax.sip.SipFactory
 *  javax.sip.SipProvider
 *  javax.sip.SipStack
 *  javax.sip.Transaction
 *  javax.sip.TransactionState
 *  javax.sip.TransactionTerminatedEvent
 *  javax.sip.address.Address
 *  javax.sip.address.AddressFactory
 *  javax.sip.address.SipURI
 *  javax.sip.address.URI
 *  javax.sip.header.CSeqHeader
 *  javax.sip.header.CallIdHeader
 *  javax.sip.header.ContactHeader
 *  javax.sip.header.ContentTypeHeader
 *  javax.sip.header.EventHeader
 *  javax.sip.header.ExpiresHeader
 *  javax.sip.header.FromHeader
 *  javax.sip.header.Header
 *  javax.sip.header.HeaderFactory
 *  javax.sip.header.MaxForwardsHeader
 *  javax.sip.header.SubscriptionStateHeader
 *  javax.sip.header.ToHeader
 *  javax.sip.header.ViaHeader
 *  javax.sip.message.Message
 *  javax.sip.message.MessageFactory
 *  javax.sip.message.Request
 *  javax.sip.message.Response
 */
package com.android.server.sip;

import android.net.sip.SipProfile;
import android.telephony.Rlog;
import gov.nist.javax.sip.SipStackExt;
import gov.nist.javax.sip.clientauthutils.AccountManager;
import gov.nist.javax.sip.clientauthutils.AuthenticationHelper;
import gov.nist.javax.sip.header.extensions.ReferredByHeader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;
import java.util.regex.Pattern;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogTerminatedEvent;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.PeerUnavailableException;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipFactory;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.Transaction;
import javax.sip.TransactionState;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.ContentTypeHeader;
import javax.sip.header.EventHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.HeaderFactory;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.SubscriptionStateHeader;
import javax.sip.header.ToHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Message;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;

class SipHelper {
    private static final boolean DBG = false;
    private static final boolean DBG_PING = false;
    private static final String TAG = SipHelper.class.getSimpleName();
    private AddressFactory mAddressFactory;
    private HeaderFactory mHeaderFactory;
    private MessageFactory mMessageFactory;
    private SipProvider mSipProvider;
    private SipStack mSipStack;

    public SipHelper(SipStack sipStack, SipProvider sipProvider) throws PeerUnavailableException {
        this.mSipStack = sipStack;
        this.mSipProvider = sipProvider;
        sipStack = SipFactory.getInstance();
        this.mAddressFactory = sipStack.createAddressFactory();
        this.mHeaderFactory = sipStack.createHeaderFactory();
        this.mMessageFactory = sipStack.createMessageFactory();
    }

    private CSeqHeader createCSeqHeader(String string) throws ParseException, InvalidArgumentException {
        long l = (long)(Math.random() * 10000.0);
        return this.mHeaderFactory.createCSeqHeader(l, string);
    }

    private CallIdHeader createCallIdHeader() {
        return this.mSipProvider.getNewCallId();
    }

    private ContactHeader createContactHeader(SipProfile sipProfile) throws ParseException, SipException {
        return this.createContactHeader(sipProfile, null, 0);
    }

    private ContactHeader createContactHeader(SipProfile sipProfile, String string, int n) throws ParseException, SipException {
        string = string == null ? this.createSipUri(sipProfile.getUserName(), sipProfile.getProtocol(), this.getListeningPoint()) : this.createSipUri(sipProfile.getUserName(), sipProfile.getProtocol(), string, n);
        string = this.mAddressFactory.createAddress((URI)string);
        string.setDisplayName(sipProfile.getDisplayName());
        return this.mHeaderFactory.createContactHeader((Address)string);
    }

    private FromHeader createFromHeader(SipProfile sipProfile, String string) throws ParseException {
        return this.mHeaderFactory.createFromHeader(sipProfile.getSipAddress(), string);
    }

    private MaxForwardsHeader createMaxForwardsHeader() throws InvalidArgumentException {
        return this.mHeaderFactory.createMaxForwardsHeader(70);
    }

    private MaxForwardsHeader createMaxForwardsHeader(int n) throws InvalidArgumentException {
        return this.mHeaderFactory.createMaxForwardsHeader(n);
    }

    private Request createRequest(String string, SipProfile sipProfile, SipProfile sipProfile2, String string2) throws ParseException, SipException {
        FromHeader fromHeader = this.createFromHeader(sipProfile, string2);
        string2 = this.createToHeader(sipProfile2);
        SipURI sipURI = sipProfile2.getUri();
        List<ViaHeader> list = this.createViaHeaders();
        CallIdHeader callIdHeader = this.createCallIdHeader();
        sipProfile2 = this.createCSeqHeader(string);
        MaxForwardsHeader maxForwardsHeader = this.createMaxForwardsHeader();
        string = this.mMessageFactory.createRequest((URI)sipURI, string, callIdHeader, (CSeqHeader)sipProfile2, fromHeader, (ToHeader)string2, list, maxForwardsHeader);
        string.addHeader((Header)this.createContactHeader(sipProfile));
        return string;
    }

    private Request createRequest(String string, SipProfile sipProfile, String string2) throws ParseException, SipException {
        FromHeader fromHeader = this.createFromHeader(sipProfile, string2);
        string2 = this.createToHeader(sipProfile);
        Object object = new StringBuilder();
        ((StringBuilder)object).append(sipProfile.getUserName());
        ((StringBuilder)object).append("@");
        object = Pattern.quote(((StringBuilder)object).toString());
        SipURI sipURI = this.mAddressFactory.createSipURI(sipProfile.getUriString().replaceFirst((String)object, ""));
        object = this.createViaHeaders();
        CallIdHeader callIdHeader = this.createCallIdHeader();
        CSeqHeader cSeqHeader = this.createCSeqHeader(string);
        sipProfile = this.createMaxForwardsHeader();
        string = this.mMessageFactory.createRequest((URI)sipURI, string, callIdHeader, cSeqHeader, fromHeader, (ToHeader)string2, (List)object, (MaxForwardsHeader)sipProfile);
        string.addHeader(this.mHeaderFactory.createHeader("User-Agent", "SIPAUA/0.1.001"));
        return string;
    }

    private SipURI createSipUri(String string, String string2, String string3, int n) throws ParseException {
        string = this.mAddressFactory.createSipURI(string, string3);
        try {
            string.setPort(n);
            string.setTransportParam(string2);
            return string;
        }
        catch (InvalidArgumentException invalidArgumentException) {
            throw new RuntimeException(invalidArgumentException);
        }
    }

    private SipURI createSipUri(String string, String string2, ListeningPoint listeningPoint) throws ParseException {
        return this.createSipUri(string, string2, listeningPoint.getIPAddress(), listeningPoint.getPort());
    }

    private ToHeader createToHeader(SipProfile sipProfile) throws ParseException {
        return this.createToHeader(sipProfile, null);
    }

    private ToHeader createToHeader(SipProfile sipProfile, String string) throws ParseException {
        return this.mHeaderFactory.createToHeader(sipProfile.getSipAddress(), string);
    }

    private List<ViaHeader> createViaHeaders() throws ParseException, SipException {
        ArrayList<ViaHeader> arrayList = new ArrayList<ViaHeader>(1);
        ListeningPoint listeningPoint = this.getListeningPoint();
        listeningPoint = this.mHeaderFactory.createViaHeader(listeningPoint.getIPAddress(), listeningPoint.getPort(), listeningPoint.getTransport(), null);
        listeningPoint.setRPort();
        arrayList.add((ViaHeader)listeningPoint);
        return arrayList;
    }

    private ContactHeader createWildcardContactHeader() {
        ContactHeader contactHeader = this.mHeaderFactory.createContactHeader();
        contactHeader.setWildCard();
        return contactHeader;
    }

    public static String getCallId(EventObject object) {
        if (object == null) {
            return null;
        }
        if (object instanceof RequestEvent) {
            return SipHelper.getCallId((Message)((RequestEvent)object).getRequest());
        }
        if (object instanceof ResponseEvent) {
            return SipHelper.getCallId((Message)((ResponseEvent)object).getResponse());
        }
        if (object instanceof DialogTerminatedEvent) {
            ((DialogTerminatedEvent)object).getDialog();
            return SipHelper.getCallId(((DialogTerminatedEvent)object).getDialog());
        }
        if (object instanceof TransactionTerminatedEvent) {
            object = (object = (TransactionTerminatedEvent)object).isServerTransaction() ? object.getServerTransaction() : object.getClientTransaction();
            return SipHelper.getCallId((Transaction)object);
        }
        if ((object = ((EventObject)object).getSource()) instanceof Transaction) {
            return SipHelper.getCallId((Transaction)object);
        }
        if (object instanceof Dialog) {
            return SipHelper.getCallId((Dialog)object);
        }
        return "";
    }

    private static String getCallId(Dialog dialog) {
        return dialog.getCallId().getCallId();
    }

    public static String getCallId(Transaction object) {
        object = object != null ? SipHelper.getCallId((Message)object.getRequest()) : "";
        return object;
    }

    private static String getCallId(Message message) {
        return ((CallIdHeader)message.getHeader("Call-ID")).getCallId();
    }

    private ListeningPoint getListeningPoint() throws SipException {
        ListeningPoint listeningPoint;
        ListeningPoint listeningPoint2 = listeningPoint = this.mSipProvider.getListeningPoint("UDP");
        if (listeningPoint == null) {
            listeningPoint2 = this.mSipProvider.getListeningPoint("TCP");
        }
        listeningPoint = listeningPoint2;
        if (listeningPoint2 == null) {
            ListeningPoint[] arrlisteningPoint = this.mSipProvider.getListeningPoints();
            listeningPoint = listeningPoint2;
            if (arrlisteningPoint != null) {
                listeningPoint = listeningPoint2;
                if (arrlisteningPoint.length > 0) {
                    listeningPoint = arrlisteningPoint[0];
                }
            }
        }
        if (listeningPoint != null) {
            return listeningPoint;
        }
        throw new SipException("no listening point is available");
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    public ServerTransaction getServerTransaction(RequestEvent requestEvent) throws SipException {
        ServerTransaction serverTransaction = requestEvent.getServerTransaction();
        if (serverTransaction == null) {
            requestEvent = requestEvent.getRequest();
            return this.mSipProvider.getNewServerTransaction((Request)requestEvent);
        }
        return serverTransaction;
    }

    public ClientTransaction handleChallenge(ResponseEvent responseEvent, AccountManager accountManager) throws SipException {
        accountManager = ((SipStackExt)this.mSipStack).getAuthenticationHelper(accountManager, this.mHeaderFactory);
        ClientTransaction clientTransaction = responseEvent.getClientTransaction();
        responseEvent = accountManager.handleChallenge(responseEvent.getResponse(), clientTransaction, this.mSipProvider, 5);
        responseEvent.sendRequest();
        return responseEvent;
    }

    public void sendBye(Dialog dialog) throws SipException {
        Request request = dialog.createRequest("BYE");
        dialog.sendRequest(this.mSipProvider.getNewClientTransaction(request));
    }

    public void sendCancel(ClientTransaction clientTransaction) throws SipException {
        clientTransaction = clientTransaction.createCancel();
        this.mSipProvider.getNewClientTransaction((Request)clientTransaction).sendRequest();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ClientTransaction sendInvite(SipProfile sipProfile, SipProfile sipProfile2, String string, String string2, ReferredByHeader referredByHeader, String string3) throws SipException {
        try {
            sipProfile = this.createRequest("INVITE", sipProfile, sipProfile2, string2);
            if (referredByHeader != null) {
                sipProfile.addHeader((Header)referredByHeader);
            }
            if (string3 != null) {
                sipProfile.addHeader(this.mHeaderFactory.createHeader("Replaces", string3));
            }
            sipProfile.setContent((Object)string, this.mHeaderFactory.createContentTypeHeader("application", "sdp"));
            sipProfile = this.mSipProvider.getNewClientTransaction((Request)sipProfile);
            sipProfile.sendRequest();
            return sipProfile;
        }
        catch (ParseException parseException) {
            throw new SipException("sendInvite()", (Throwable)parseException);
        }
    }

    public void sendInviteAck(ResponseEvent responseEvent, Dialog dialog) throws SipException {
        dialog.sendAck(dialog.createAck(((CSeqHeader)responseEvent.getResponse().getHeader("CSeq")).getSeqNumber()));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendInviteBusyHere(RequestEvent requestEvent, ServerTransaction serverTransaction) throws SipException {
        try {
            Request request = requestEvent.getRequest();
            Response response = this.mMessageFactory.createResponse(486, request);
            request = serverTransaction;
            if (serverTransaction == null) {
                request = this.getServerTransaction(requestEvent);
            }
            if (request.getState() != TransactionState.COMPLETED) {
                request.sendResponse(response);
            }
            return;
        }
        catch (ParseException parseException) {
            throw new SipException("sendInviteBusyHere()", (Throwable)parseException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ServerTransaction sendInviteOk(RequestEvent requestEvent, SipProfile sipProfile, String string, ServerTransaction serverTransaction, String string2, int n) throws SipException {
        try {
            Request request = requestEvent.getRequest();
            request = this.mMessageFactory.createResponse(200, request);
            request.addHeader((Header)this.createContactHeader(sipProfile, string2, n));
            request.setContent((Object)string, this.mHeaderFactory.createContentTypeHeader("application", "sdp"));
            sipProfile = serverTransaction;
            if (serverTransaction == null) {
                sipProfile = this.getServerTransaction(requestEvent);
            }
            if (sipProfile.getState() != TransactionState.COMPLETED) {
                sipProfile.sendResponse((Response)request);
            }
            return sipProfile;
        }
        catch (ParseException parseException) {
            throw new SipException("sendInviteOk()", (Throwable)parseException);
        }
    }

    public void sendInviteRequestTerminated(Request request, ServerTransaction serverTransaction) throws SipException {
        try {
            serverTransaction.sendResponse(this.mMessageFactory.createResponse(487, request));
            return;
        }
        catch (ParseException parseException) {
            throw new SipException("sendInviteRequestTerminated()", (Throwable)parseException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ClientTransaction sendOptions(SipProfile var1_1, SipProfile var2_3, String var3_4) throws SipException {
        if (var1_1 != var2_3) ** GOTO lbl5
        try {
            block2 : {
                var1_1 = this.createRequest("OPTIONS", var1_1, var3_4);
                break block2;
lbl5: // 1 sources:
                var1_1 = this.createRequest("OPTIONS", var1_1, var2_3, var3_4);
            }
            var1_1 = this.mSipProvider.getNewClientTransaction((Request)var1_1);
            var1_1.sendRequest();
            return var1_1;
        }
        catch (Exception var1_2) {
            throw new SipException("sendOptions()", (Throwable)var1_2);
        }
    }

    public void sendReferNotify(Dialog dialog, String string) throws SipException {
        try {
            Request request = dialog.createRequest("NOTIFY");
            request.addHeader((Header)this.mHeaderFactory.createSubscriptionStateHeader("active;expires=60"));
            request.setContent((Object)string, this.mHeaderFactory.createContentTypeHeader("message", "sipfrag"));
            request.addHeader((Header)this.mHeaderFactory.createEventHeader("refer"));
            dialog.sendRequest(this.mSipProvider.getNewClientTransaction(request));
            return;
        }
        catch (ParseException parseException) {
            throw new SipException("sendReferNotify()", (Throwable)parseException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ClientTransaction sendRegister(SipProfile sipProfile, String string, int n) throws SipException {
        try {
            string = this.createRequest("REGISTER", sipProfile, string);
            if (n == 0) {
                string.addHeader((Header)this.createWildcardContactHeader());
            } else {
                string.addHeader((Header)this.createContactHeader(sipProfile));
            }
            string.addHeader((Header)this.mHeaderFactory.createExpiresHeader(n));
            sipProfile = this.mSipProvider.getNewClientTransaction((Request)string);
            sipProfile.sendRequest();
            return sipProfile;
        }
        catch (ParseException parseException) {
            throw new SipException("sendRegister()", (Throwable)parseException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ClientTransaction sendReinvite(Dialog dialog, String string) throws SipException {
        try {
            Request request = dialog.createRequest("INVITE");
            request.setContent((Object)string, this.mHeaderFactory.createContentTypeHeader("application", "sdp"));
            string = (ViaHeader)request.getHeader("Via");
            if (string != null) {
                string.setRPort();
            }
            string = this.mSipProvider.getNewClientTransaction(request);
            dialog.sendRequest((ClientTransaction)string);
            return string;
        }
        catch (ParseException parseException) {
            throw new SipException("sendReinvite()", (Throwable)parseException);
        }
    }

    public void sendResponse(RequestEvent requestEvent, int n) throws SipException {
        try {
            Request request = requestEvent.getRequest();
            request = this.mMessageFactory.createResponse(n, request);
            this.getServerTransaction(requestEvent).sendResponse((Response)request);
            return;
        }
        catch (ParseException parseException) {
            throw new SipException("sendResponse()", (Throwable)parseException);
        }
    }

    public ServerTransaction sendRinging(RequestEvent requestEvent, String string) throws SipException {
        try {
            Request request = requestEvent.getRequest();
            requestEvent = this.getServerTransaction(requestEvent);
            request = this.mMessageFactory.createResponse(180, request);
            ToHeader toHeader = (ToHeader)request.getHeader("To");
            toHeader.setTag(string);
            request.addHeader((Header)toHeader);
            requestEvent.sendResponse((Response)request);
            return requestEvent;
        }
        catch (ParseException parseException) {
            throw new SipException("sendRinging()", (Throwable)parseException);
        }
    }
}

