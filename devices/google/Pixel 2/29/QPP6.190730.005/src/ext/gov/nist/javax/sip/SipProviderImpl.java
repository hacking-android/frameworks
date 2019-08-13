/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.DialogTimeoutEvent;
import gov.nist.javax.sip.EventScanner;
import gov.nist.javax.sip.EventWrapper;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.SipProviderExt;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.address.RouterExt;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.header.RequestLine;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.HopImpl;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPDialogErrorEvent;
import gov.nist.javax.sip.stack.SIPDialogEventListener;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionErrorEvent;
import gov.nist.javax.sip.stack.SIPTransactionEventListener;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Collection;
import java.util.EventObject;
import java.util.Iterator;
import java.util.TooManyListenersException;
import java.util.concurrent.ConcurrentHashMap;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.Timeout;
import javax.sip.TimeoutEvent;
import javax.sip.Transaction;
import javax.sip.TransactionAlreadyExistsException;
import javax.sip.TransactionState;
import javax.sip.TransactionUnavailableException;
import javax.sip.address.Hop;
import javax.sip.address.Router;
import javax.sip.header.CallIdHeader;
import javax.sip.header.Header;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class SipProviderImpl
implements SipProvider,
SipProviderExt,
SIPTransactionEventListener,
SIPDialogEventListener {
    private String IN6_ADDR_ANY = "::0";
    private String IN_ADDR_ANY = "0.0.0.0";
    private String address;
    private boolean automaticDialogSupportEnabled;
    private boolean dialogErrorsAutomaticallyHandled = true;
    private EventScanner eventScanner;
    private ConcurrentHashMap listeningPoints;
    private int port;
    private SipListener sipListener;
    protected SipStackImpl sipStack;

    private SipProviderImpl() {
    }

    protected SipProviderImpl(SipStackImpl sipStackImpl) {
        this.eventScanner = sipStackImpl.getEventScanner();
        this.sipStack = sipStackImpl;
        this.eventScanner.incrementRefcount();
        this.listeningPoints = new ConcurrentHashMap();
        this.automaticDialogSupportEnabled = this.sipStack.isAutomaticDialogSupportEnabled();
        this.dialogErrorsAutomaticallyHandled = this.sipStack.isAutomaticDialogErrorHandlingEnabled();
    }

    @Override
    public void addListeningPoint(ListeningPoint object) throws ObjectInUseException {
        synchronized (this) {
            block10 : {
                String string;
                ListeningPointImpl listeningPointImpl;
                block9 : {
                    block8 : {
                        listeningPointImpl = (ListeningPointImpl)object;
                        if (listeningPointImpl.sipProvider != null && listeningPointImpl.sipProvider != this) {
                            object = new ObjectInUseException("Listening point assigned to another provider");
                            throw object;
                        }
                        string = listeningPointImpl.getTransport().toUpperCase();
                        if (!this.listeningPoints.isEmpty()) break block8;
                        this.address = object.getIPAddress();
                        this.port = object.getPort();
                        break block9;
                    }
                    if (!this.address.equals(object.getIPAddress()) || this.port != object.getPort()) break block10;
                }
                if (this.listeningPoints.containsKey(string) && this.listeningPoints.get(string) != object) {
                    object = new ObjectInUseException("Listening point already assigned for transport!");
                    throw object;
                }
                listeningPointImpl.sipProvider = this;
                this.listeningPoints.put(string, listeningPointImpl);
                return;
            }
            object = new ObjectInUseException("Provider already has different IP Address associated");
            throw object;
        }
    }

    @Override
    public void addSipListener(SipListener sipListener) throws TooManyListenersException {
        block6 : {
            block5 : {
                block4 : {
                    if (this.sipStack.sipListener != null) break block4;
                    this.sipStack.sipListener = sipListener;
                    break block5;
                }
                if (this.sipStack.sipListener != sipListener) break block6;
            }
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("add SipListener ");
                stringBuilder.append(sipListener);
                stackLogger.logDebug(stringBuilder.toString());
            }
            this.sipListener = sipListener;
            return;
        }
        throw new TooManyListenersException("Stack already has a listener. Only one listener per stack allowed");
    }

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    public void dialogErrorEvent(SIPDialogErrorEvent serializable) {
        synchronized (this) {
            SIPDialog sIPDialog = (SIPDialog)((EventObject)serializable).getSource();
            DialogTimeoutEvent.Reason reason = DialogTimeoutEvent.Reason.AckNotReceived;
            if (((SIPDialogErrorEvent)serializable).getErrorID() == 2) {
                reason = DialogTimeoutEvent.Reason.AckNotSent;
            } else if (((SIPDialogErrorEvent)serializable).getErrorID() == 3) {
                reason = DialogTimeoutEvent.Reason.ReInviteTimeout;
            }
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Dialog TimeoutError occured on ");
                ((StringBuilder)serializable).append(sIPDialog);
                stackLogger.logDebug(((StringBuilder)serializable).toString());
            }
            serializable = new DialogTimeoutEvent(this, sIPDialog, reason);
            this.handleEvent((EventObject)serializable, null);
            return;
        }
    }

    @Override
    public ListeningPoint getListeningPoint() {
        if (this.listeningPoints.size() > 0) {
            return (ListeningPoint)this.listeningPoints.values().iterator().next();
        }
        return null;
    }

    @Override
    public ListeningPoint getListeningPoint(String string) {
        if (string != null) {
            return (ListeningPoint)this.listeningPoints.get(string.toUpperCase());
        }
        throw new NullPointerException("Null transport param");
    }

    @Override
    public ListeningPoint[] getListeningPoints() {
        synchronized (this) {
            ListeningPoint[] arrlisteningPoint = new ListeningPointImpl[this.listeningPoints.size()];
            this.listeningPoints.values().toArray(arrlisteningPoint);
            return arrlisteningPoint;
        }
    }

    @Override
    public CallIdHeader getNewCallId() {
        String string = Utils.getInstance().generateCallIdentifier(this.getListeningPoint().getIPAddress());
        CallID callID = new CallID();
        try {
            callID.setCallId(string);
        }
        catch (ParseException parseException) {
            // empty catch block
        }
        return callID;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public ClientTransaction getNewClientTransaction(Request serializable) throws TransactionUnavailableException {
        block24 : {
            Object object;
            String string;
            SIPRequest sIPRequest;
            Object object2;
            block23 : {
                if (serializable == null) throw new NullPointerException("null request");
                if (!this.sipStack.isAlive()) throw new TransactionUnavailableException("Stack is stopped");
                sIPRequest = (SIPRequest)serializable;
                if (sIPRequest.getTransaction() != null) throw new TransactionUnavailableException("Transaction already assigned to request");
                if (sIPRequest.getMethod().equals("ACK")) throw new TransactionUnavailableException("Cannot create client transaction for  ACK");
                if (sIPRequest.getTopmostVia() == null) {
                    serializable.setHeader(((ListeningPointImpl)this.getListeningPoint("udp")).getViaHeader());
                }
                sIPRequest.checkHeaders();
                if (sIPRequest.getTopmostVia().getBranch() == null || !sIPRequest.getTopmostVia().getBranch().startsWith("z9hG4bK")) break block23;
                if (this.sipStack.findTransaction((SIPRequest)serializable, false) != null) throw new TransactionUnavailableException("Transaction already exists!");
            }
            if (serializable.getMethod().equalsIgnoreCase("CANCEL") && (object2 = (SIPClientTransaction)this.sipStack.findCancelTransaction((SIPRequest)serializable, false)) != null) {
                serializable = this.sipStack.createClientTransaction((SIPRequest)serializable, ((SIPTransaction)object2).getMessageChannel());
                ((SIPTransaction)serializable).addEventListener(this);
                this.sipStack.addTransaction((SIPClientTransaction)serializable);
                if (((SIPClientTransaction)object2).getDialog() == null) return serializable;
                ((SIPClientTransaction)serializable).setDialog((SIPDialog)((SIPClientTransaction)object2).getDialog(), sIPRequest.getDialogId(false));
                return serializable;
            }
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("could not find existing transaction for ");
                ((StringBuilder)object).append(((SIPRequest)serializable).getFirstLine());
                ((StringBuilder)object).append(" creating a new one ");
                object2.logDebug(((StringBuilder)object).toString());
            }
            try {
                object2 = this.sipStack.getNextHop((SIPRequest)serializable);
                if (object2 == null) break block24;
                string = object2.getTransport();
            }
            catch (SipException sipException) {
                throw new TransactionUnavailableException("Cannot resolve next hop -- transaction unavailable", sipException);
            }
            Object object3 = (ListeningPointImpl)this.getListeningPoint(string);
            object = sIPRequest.getDialogId(false);
            if ((object = this.sipStack.getDialog((String)object)) != null && ((SIPDialog)object).getState() == DialogState.TERMINATED) {
                this.sipStack.removeDialog((SIPDialog)object);
            }
            try {
                Object object4;
                if (sIPRequest.getTopmostVia().getBranch() == null || !sIPRequest.getTopmostVia().getBranch().startsWith("z9hG4bK") || this.sipStack.checkBranchId()) {
                    object4 = Utils.getInstance().generateBranchId();
                    sIPRequest.getTopmostVia().setBranch((String)object4);
                }
                if (((Via)(object4 = sIPRequest.getTopmostVia())).getTransport() == null) {
                    ((Via)object4).setTransport(string);
                }
                if (((Via)object4).getPort() == -1) {
                    ((Via)object4).setPort(((ListeningPointImpl)object3).getPort());
                }
                string = sIPRequest.getTopmostVia().getBranch();
                if ((object3 = (SIPClientTransaction)this.sipStack.createMessageChannel(sIPRequest, ((ListeningPointImpl)object3).getMessageProcessor(), (Hop)object2)) == null) {
                    serializable = new TransactionUnavailableException("Cound not create tx");
                    throw serializable;
                }
                ((SIPClientTransaction)object3).setNextHop((Hop)object2);
                ((SIPTransaction)object3).setOriginalRequest(sIPRequest);
                ((SIPTransaction)object3).setBranch(string);
                object2 = this.sipStack;
                if (SipStackImpl.isDialogCreated(serializable.getMethod())) {
                    if (object != null) {
                        ((SIPClientTransaction)object3).setDialog((SIPDialog)object, sIPRequest.getDialogId(false));
                    } else if (this.isAutomaticDialogSupportEnabled()) {
                        ((SIPClientTransaction)object3).setDialog(this.sipStack.createDialog((SIPTransaction)object3), sIPRequest.getDialogId(false));
                    }
                } else if (object != null) {
                    ((SIPClientTransaction)object3).setDialog((SIPDialog)object, sIPRequest.getDialogId(false));
                }
                ((SIPTransaction)object3).addEventListener(this);
                return object3;
            }
            catch (InvalidArgumentException invalidArgumentException) {
                InternalErrorHandler.handleException(invalidArgumentException);
                throw new TransactionUnavailableException("Unexpected Exception FIXME! ", invalidArgumentException);
            }
            catch (ParseException parseException) {
                InternalErrorHandler.handleException(parseException);
                throw new TransactionUnavailableException("Unexpected Exception FIXME! ", parseException);
            }
            catch (IOException iOException) {
                throw new TransactionUnavailableException("Could not resolve next hop or listening point unavailable! ", iOException);
            }
        }
        serializable = new TransactionUnavailableException("Cannot resolve next hop -- transaction unavailable");
        throw serializable;
        catch (ParseException parseException) {
            throw new TransactionUnavailableException(parseException.getMessage(), parseException);
        }
    }

    @Override
    public Dialog getNewDialog(Transaction object) throws SipException {
        block8 : {
            block9 : {
                block10 : {
                    Object object2;
                    block11 : {
                        block14 : {
                            block15 : {
                                block13 : {
                                    SIPTransaction sIPTransaction;
                                    block12 : {
                                        if (object == null) break block8;
                                        if (!this.sipStack.isAlive()) break block9;
                                        if (this.isAutomaticDialogSupportEnabled()) break block10;
                                        object2 = this.sipStack;
                                        if (!SipStackImpl.isDialogCreated(object.getRequest().getMethod())) break block11;
                                        sIPTransaction = (SIPTransaction)object;
                                        if (!(object instanceof ServerTransaction)) break block12;
                                        SIPServerTransaction sIPServerTransaction = (SIPServerTransaction)object;
                                        object2 = sIPServerTransaction.getLastResponse();
                                        if (object2 != null && object2.getStatusCode() != 100) {
                                            throw new SipException("Cannot set dialog after response has been sent");
                                        }
                                        SIPRequest sIPRequest = (SIPRequest)object.getRequest();
                                        object2 = sIPRequest.getDialogId(true);
                                        if ((object2 = this.sipStack.getDialog((String)object2)) == null) {
                                            object = this.sipStack.createDialog((SIPTransaction)object);
                                            ((SIPDialog)object).addTransaction(sIPTransaction);
                                            ((SIPDialog)object).addRoute(sIPRequest);
                                            sIPTransaction.setDialog((SIPDialog)object, null);
                                        } else {
                                            sIPTransaction.setDialog((SIPDialog)object2, sIPRequest.getDialogId(true));
                                            object = object2;
                                        }
                                        if (sIPRequest.getMethod().equals("INVITE") && this.isDialogErrorsAutomaticallyHandled()) {
                                            this.sipStack.putInMergeTable(sIPServerTransaction, sIPRequest);
                                        }
                                        break block13;
                                    }
                                    object2 = (SIPClientTransaction)object;
                                    if (((SIPTransaction)object2).getLastResponse() != null) break block14;
                                    object = ((SIPRequest)((SIPTransaction)object2).getRequest()).getDialogId(false);
                                    if (this.sipStack.getDialog((String)object) != null) break block15;
                                    object = this.sipStack.createDialog(sIPTransaction);
                                    ((SIPClientTransaction)object2).setDialog((SIPDialog)object, null);
                                }
                                ((SIPDialog)object).addEventListener(this);
                                return object;
                            }
                            throw new SipException("Dialog already exists!");
                        }
                        throw new SipException("Cannot call this method after response is received!");
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Dialog cannot be created for this method ");
                    ((StringBuilder)object2).append(object.getRequest().getMethod());
                    throw new SipException(((StringBuilder)object2).toString());
                }
                throw new SipException(" Error - AUTOMATIC_DIALOG_SUPPORT is on");
            }
            throw new SipException("Stack is stopped.");
        }
        throw new NullPointerException("Null transaction!");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ServerTransaction getNewServerTransaction(Request object) throws TransactionAlreadyExistsException, TransactionUnavailableException {
        SIPRequest sIPRequest;
        block28 : {
            if (!this.sipStack.isAlive()) throw new TransactionUnavailableException("Stack is stopped");
            sIPRequest = (SIPRequest)object;
            try {
                sIPRequest.checkHeaders();
                if (!object.getMethod().equals("ACK")) break block28;
            }
            catch (ParseException parseException) {
                throw new TransactionUnavailableException(parseException.getMessage(), parseException);
            }
            if (!this.sipStack.isLoggingEnabled()) throw new TransactionUnavailableException("Cannot create Server transaction for ACK ");
            this.sipStack.getStackLogger().logError("Creating server transaction for ACK -- makes no sense!");
            throw new TransactionUnavailableException("Cannot create Server transaction for ACK ");
        }
        if (sIPRequest.getMethod().equals("NOTIFY") && sIPRequest.getFromTag() != null && sIPRequest.getToTag() == null && this.sipStack.findSubscribeTransaction(sIPRequest, (ListeningPointImpl)this.getListeningPoint()) == null) {
            if (!this.sipStack.deliverUnsolicitedNotify) throw new TransactionUnavailableException("Cannot find matching Subscription (and gov.nist.javax.sip.DELIVER_UNSOLICITED_NOTIFY not set)");
        }
        if (!this.sipStack.acquireSem()) throw new TransactionUnavailableException("Transaction not available -- could not acquire stack lock");
        try {
            Object object2 = this.sipStack;
            if (SipStackImpl.isDialogCreated(sIPRequest.getMethod())) {
                if (this.sipStack.findTransaction((SIPRequest)object, true) != null) {
                    object = new TransactionAlreadyExistsException("server transaction already exists!");
                    throw object;
                }
                object2 = (SIPServerTransaction)((SIPRequest)object).getTransaction();
                if (object2 == null) {
                    object = new TransactionUnavailableException("Transaction not available");
                    throw object;
                }
                if (((SIPTransaction)object2).getOriginalRequest() == null) {
                    ((SIPServerTransaction)object2).setOriginalRequest(sIPRequest);
                }
                try {
                    this.sipStack.addTransaction((SIPServerTransaction)object2);
                }
                catch (IOException iOException) {
                    TransactionUnavailableException transactionUnavailableException = new TransactionUnavailableException("Error sending provisional response");
                    throw transactionUnavailableException;
                }
                ((SIPTransaction)object2).addEventListener(this);
                object = object2;
                if (!this.isAutomaticDialogSupportEnabled()) return object;
                object = sIPRequest.getDialogId(true);
                SIPDialog sIPDialog = this.sipStack.getDialog((String)object);
                object = sIPDialog;
                if (sIPDialog == null) {
                    object = this.sipStack.createDialog((SIPTransaction)object2);
                }
                ((SIPServerTransaction)object2).setDialog((SIPDialog)object, sIPRequest.getDialogId(true));
                if (sIPRequest.getMethod().equals("INVITE") && this.isDialogErrorsAutomaticallyHandled()) {
                    this.sipStack.putInMergeTable((SIPServerTransaction)object2, sIPRequest);
                }
                ((SIPDialog)object).addRoute(sIPRequest);
                if (((SIPDialog)object).getRemoteTag() != null && ((SIPDialog)object).getLocalTag() != null) {
                    this.sipStack.putDialog((SIPDialog)object);
                }
                object = object2;
                return object;
            }
            boolean bl = this.isAutomaticDialogSupportEnabled();
            if (bl) {
                if ((SIPServerTransaction)this.sipStack.findTransaction((SIPRequest)object, true) == null) {
                    if ((object = (SIPServerTransaction)((SIPRequest)object).getTransaction()) != null) {
                        if (((SIPTransaction)object).getOriginalRequest() == null) {
                            ((SIPServerTransaction)object).setOriginalRequest(sIPRequest);
                        }
                        try {
                            this.sipStack.addTransaction((SIPServerTransaction)object);
                            object2 = sIPRequest.getDialogId(true);
                        }
                        catch (IOException iOException) {
                            TransactionUnavailableException transactionUnavailableException = new TransactionUnavailableException("Could not send back provisional response!");
                            throw transactionUnavailableException;
                        }
                        object2 = this.sipStack.getDialog((String)object2);
                        if (object2 == null) return object;
                        ((SIPDialog)object2).addTransaction((SIPTransaction)object);
                        ((SIPDialog)object2).addRoute(sIPRequest);
                        ((SIPServerTransaction)object).setDialog((SIPDialog)object2, sIPRequest.getDialogId(true));
                        return object;
                    }
                    object = new TransactionUnavailableException("Transaction not available!");
                    throw object;
                }
                object = new TransactionAlreadyExistsException("Transaction exists! ");
                throw object;
            }
            if ((SIPServerTransaction)this.sipStack.findTransaction((SIPRequest)object, true) == null) {
                if ((object = (SIPServerTransaction)((SIPRequest)object).getTransaction()) != null) {
                    if (((SIPTransaction)object).getOriginalRequest() == null) {
                        ((SIPServerTransaction)object).setOriginalRequest(sIPRequest);
                    }
                    this.sipStack.mapTransaction((SIPServerTransaction)object);
                    object2 = sIPRequest.getDialogId(true);
                    object2 = this.sipStack.getDialog((String)object2);
                    if (object2 == null) return object;
                    ((SIPDialog)object2).addTransaction((SIPTransaction)object);
                    ((SIPDialog)object2).addRoute(sIPRequest);
                    ((SIPServerTransaction)object).setDialog((SIPDialog)object2, sIPRequest.getDialogId(true));
                    return object;
                }
                object = (MessageChannel)sIPRequest.getMessageChannel();
                if ((object = this.sipStack.createServerTransaction((MessageChannel)object)) != null) {
                    ((SIPServerTransaction)object).setOriginalRequest(sIPRequest);
                    this.sipStack.mapTransaction((SIPServerTransaction)object);
                    object2 = sIPRequest.getDialogId(true);
                    object2 = this.sipStack.getDialog((String)object2);
                    if (object2 == null) return object;
                    ((SIPDialog)object2).addTransaction((SIPTransaction)object);
                    ((SIPDialog)object2).addRoute(sIPRequest);
                    ((SIPServerTransaction)object).setDialog((SIPDialog)object2, sIPRequest.getDialogId(true));
                    return object;
                }
                object = new TransactionUnavailableException("Transaction unavailable -- too many servrer transactions");
                throw object;
            }
            object = new TransactionAlreadyExistsException("Transaction exists! ");
            throw object;
        }
        finally {
            this.sipStack.releaseSem();
        }
    }

    public SipListener getSipListener() {
        return this.sipListener;
    }

    @Override
    public SipStack getSipStack() {
        return this.sipStack;
    }

    public void handleEvent(EventObject object, SIPTransaction sIPTransaction) {
        if (this.sipStack.isLoggingEnabled()) {
            Object object2 = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleEvent ");
            stringBuilder.append(object);
            stringBuilder.append("currentTransaction = ");
            stringBuilder.append(sIPTransaction);
            stringBuilder.append("this.sipListener = ");
            stringBuilder.append(this.getSipListener());
            stringBuilder.append("sipEvent.source = ");
            stringBuilder.append(((EventObject)object).getSource());
            object2.logDebug(stringBuilder.toString());
            if (object instanceof RequestEvent) {
                object2 = ((RequestEvent)object).getDialog();
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Dialog = ");
                    stringBuilder.append(object2);
                    stackLogger.logDebug(stringBuilder.toString());
                }
            } else if (object instanceof ResponseEvent) {
                object2 = ((ResponseEvent)object).getDialog();
                if (this.sipStack.isLoggingEnabled()) {
                    StackLogger stackLogger = this.sipStack.getStackLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Dialog = ");
                    stringBuilder.append(object2);
                    stackLogger.logDebug(stringBuilder.toString());
                }
            }
            this.sipStack.getStackLogger().logStackTrace();
        }
        object = new EventWrapper((EventObject)object, sIPTransaction);
        if (!this.sipStack.reEntrantListener) {
            this.eventScanner.addEvent((EventWrapper)object);
        } else {
            this.eventScanner.deliverEvent((EventWrapper)object);
        }
    }

    @Override
    public boolean isAutomaticDialogSupportEnabled() {
        return this.automaticDialogSupportEnabled;
    }

    public boolean isDialogErrorsAutomaticallyHandled() {
        return this.dialogErrorsAutomaticallyHandled;
    }

    @Override
    public void removeListeningPoint(ListeningPoint object) throws ObjectInUseException {
        synchronized (this) {
            object = (ListeningPointImpl)object;
            if (!((ListeningPointImpl)object).messageProcessor.inUse()) {
                this.listeningPoints.remove(((ListeningPointImpl)object).getTransport().toUpperCase());
                return;
            }
            object = new ObjectInUseException("Object is in use");
            throw object;
        }
    }

    @Override
    public void removeListeningPoints() {
        synchronized (this) {
            Iterator iterator = this.listeningPoints.values().iterator();
            while (iterator.hasNext()) {
                ((ListeningPointImpl)iterator.next()).messageProcessor.stop();
                iterator.remove();
            }
            return;
        }
    }

    @Override
    public void removeSipListener(SipListener object) {
        if (object == this.getSipListener()) {
            this.sipListener = null;
        }
        boolean bl = false;
        object = this.sipStack.getSipProviders();
        while (object.hasNext()) {
            if (((SipProviderImpl)object.next()).getSipListener() == null) continue;
            bl = true;
        }
        if (!bl) {
            this.sipStack.sipListener = null;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void sendRequest(Request var1_1) throws SipException {
        block13 : {
            block12 : {
                if (this.sipStack.isAlive() == false) throw new SipException("Stack is stopped.");
                if (((SIPRequest)var1_1).getRequestLine() != null && var1_1.getMethod().equals("ACK") && (var2_2 = this.sipStack.getDialog(((SIPRequest)var1_1).getDialogId(false))) != null && var2_2.getState() != null && this.sipStack.isLoggingEnabled()) {
                    var3_5 = this.sipStack.getStackLogger();
                    var4_6 = new StringBuilder();
                    var4_6.append("Dialog exists -- you may want to use Dialog.sendAck() ");
                    var4_6.append((Object)var2_2.getState());
                    var3_5.logWarning(var4_6.toString());
                }
                if ((var3_5 = this.sipStack.getRouter((SIPRequest)var1_1).getNextHop(var1_1)) == null) throw new SipException("could not determine next hop!");
                var4_6 = (SIPRequest)var1_1;
                if (!var4_6.isNullRequest()) {
                    if (var4_6.getTopmostVia() == null) throw new SipException("Invalid SipRequest -- no via header!");
                }
                if (!(var4_6.isNullRequest() || (var2_2 = (var5_7 = var4_6.getTopmostVia()).getBranch()) != null && var2_2.length() != 0)) {
                    var5_7.setBranch(var4_6.getTransactionId());
                }
                var2_2 = null;
                if (this.listeningPoints.containsKey(var3_5.getTransport().toUpperCase())) {
                    var2_2 = this.sipStack.createRawMessageChannel(this.getListeningPoint(var3_5.getTransport()).getIPAddress(), this.getListeningPoint(var3_5.getTransport()).getPort(), (Hop)var3_5);
                }
                if (var2_2 == null) break block12;
                var2_2.sendMessage((SIPMessage)var4_6, (Hop)var3_5);
                if (this.sipStack.isLoggingEnabled() == false) return;
                var4_6 = this.sipStack.getStackLogger();
                var2_2 = new StringBuilder();
                ** GOTO lbl43
            }
            var4_6 = new StringBuilder();
            var4_6.append("Could not create a message channel for ");
            var4_6.append(var3_5.toString());
            var2_2 = new SipException(var4_6.toString());
            throw var2_2;
            {
                catch (Throwable var5_8) {
                    break block13;
                }
                catch (ParseException var2_3) {
                    InternalErrorHandler.handleException(var2_3);
                    if (this.sipStack.isLoggingEnabled() == false) return;
                    var4_6 = this.sipStack.getStackLogger();
                    var2_2 = new StringBuilder();
                }
lbl43: // 2 sources:
                var2_2.append("done sending ");
                var2_2.append(var1_1.getMethod());
                var2_2.append(" to hop ");
                var2_2.append(var3_5);
                var4_6.logDebug(var2_2.toString());
                return;
                catch (IOException var2_4) {}
                {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logException(var2_4);
                    }
                    var4_6 = new SipException("IO Exception occured while Sending Request", var2_4);
                    throw var4_6;
                }
            }
        }
        if (this.sipStack.isLoggingEnabled() == false) throw var5_8;
        var4_6 = this.sipStack.getStackLogger();
        var2_2 = new StringBuilder();
        var2_2.append("done sending ");
        var2_2.append(var1_1.getMethod());
        var2_2.append(" to hop ");
        var2_2.append(var3_5);
        var4_6.logDebug(var2_2.toString());
        throw var5_8;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sendResponse(Response object) throws SipException {
        int n;
        Object object2;
        if (!this.sipStack.isAlive()) {
            throw new SipException("Stack is stopped");
        }
        SIPResponse sIPResponse = (SIPResponse)object;
        Via via = sIPResponse.getTopmostVia();
        if (via == null) {
            throw new SipException("No via header in response!");
        }
        if ((object = (SIPServerTransaction)this.sipStack.findTransaction((SIPMessage)object, true)) != null && ((SIPServerTransaction)object).getState() != TransactionState.TERMINATED && this.isAutomaticDialogSupportEnabled()) {
            throw new SipException("Transaction exists -- cannot send response statelessly");
        }
        String string = via.getTransport();
        object = object2 = via.getReceived();
        if (object2 == null) {
            object = via.getHost();
        }
        int n2 = n = via.getRPort();
        if (n == -1) {
            n2 = n = via.getPort();
            if (n == -1) {
                n2 = string.equalsIgnoreCase("TLS") ? 5061 : 5060;
            }
        }
        object2 = object;
        if (((String)object).indexOf(":") > 0) {
            object2 = object;
            if (((String)object).indexOf("[") < 0) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[");
                ((StringBuilder)object2).append((String)object);
                ((StringBuilder)object2).append("]");
                object2 = ((StringBuilder)object2).toString();
            }
        }
        object = this.sipStack.getAddressResolver().resolveAddress(new HopImpl((String)object2, n2, string));
        try {
            object2 = (ListeningPointImpl)this.getListeningPoint(string);
            if (object2 != null) {
                this.sipStack.createRawMessageChannel(this.getListeningPoint(object.getTransport()).getIPAddress(), ((ListeningPointImpl)object2).port, (Hop)object).sendMessage(sIPResponse);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("whoopsa daisy! no listening point found for transport ");
            ((StringBuilder)object).append(string);
            object2 = new SipException(((StringBuilder)object).toString());
            throw object2;
        }
        catch (IOException iOException) {
            throw new SipException(iOException.getMessage());
        }
    }

    @Override
    public void setAutomaticDialogSupportEnabled(boolean bl) {
        this.automaticDialogSupportEnabled = bl;
        if (this.automaticDialogSupportEnabled) {
            this.dialogErrorsAutomaticallyHandled = true;
        }
    }

    @Override
    public void setDialogErrorsAutomaticallyHandled() {
        this.dialogErrorsAutomaticallyHandled = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setListeningPoint(ListeningPoint object) {
        synchronized (this) {
            Throwable throwable2;
            if (object != null) {
                try {
                    Object object2 = (ListeningPointImpl)object;
                    ((ListeningPointImpl)object2).sipProvider = this;
                    object2 = ((ListeningPointImpl)object2).getTransport().toUpperCase();
                    this.address = object.getIPAddress();
                    this.port = object.getPort();
                    this.listeningPoints.clear();
                    this.listeningPoints.put(object2, object);
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new NullPointerException("Null listening point");
                throw object;
            }
            throw throwable2;
        }
    }

    protected void stop() {
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logDebug("Exiting provider");
        }
        Iterator iterator = this.listeningPoints.values().iterator();
        while (iterator.hasNext()) {
            ((ListeningPointImpl)iterator.next()).removeSipProvider();
        }
        this.eventScanner.stop();
    }

    @Override
    public void transactionErrorEvent(SIPTransactionErrorEvent object) {
        block9 : {
            Object object2;
            block10 : {
                block8 : {
                    Object object3;
                    object2 = (SIPTransaction)object.getSource();
                    if (object.getErrorID() != 2) break block8;
                    if (this.sipStack.isLoggingEnabled()) {
                        object3 = this.sipStack.getStackLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("TransportError occured on ");
                        stringBuilder.append(object2);
                        object3.logDebug(stringBuilder.toString());
                    }
                    object2 = object.getSource();
                    object = Timeout.TRANSACTION;
                    if (object2 instanceof SIPServerTransaction) {
                        object = new TimeoutEvent((Object)this, (ServerTransaction)object2, (Timeout)((Object)object));
                    } else {
                        object3 = ((SIPClientTransaction)object2).getNextHop();
                        if (this.sipStack.getRouter() instanceof RouterExt) {
                            ((RouterExt)this.sipStack.getRouter()).transactionTimeout((Hop)object3);
                        }
                        object = new TimeoutEvent((Object)this, (ClientTransaction)object2, (Timeout)((Object)object));
                    }
                    this.handleEvent((EventObject)object, (SIPTransaction)object2);
                    break block9;
                }
                if (object.getErrorID() != 1) break block10;
                object2 = object.getSource();
                object = Timeout.TRANSACTION;
                if (object2 instanceof SIPServerTransaction) {
                    object = new TimeoutEvent((Object)this, (ServerTransaction)object2, (Timeout)((Object)object));
                } else {
                    Hop hop = ((SIPClientTransaction)object2).getNextHop();
                    if (this.sipStack.getRouter() instanceof RouterExt) {
                        ((RouterExt)this.sipStack.getRouter()).transactionTimeout(hop);
                    }
                    object = new TimeoutEvent((Object)this, (ClientTransaction)object2, (Timeout)((Object)object));
                }
                this.handleEvent((EventObject)object, (SIPTransaction)object2);
                break block9;
            }
            if (object.getErrorID() != 3) break block9;
            object2 = object.getSource();
            if (((Transaction)object2).getDialog() != null) {
                InternalErrorHandler.handleException("Unexpected event !", this.sipStack.getStackLogger());
            }
            object = Timeout.RETRANSMIT;
            object = object2 instanceof SIPServerTransaction ? new TimeoutEvent((Object)this, (ServerTransaction)object2, (Timeout)((Object)object)) : new TimeoutEvent((Object)this, (ClientTransaction)object2, (Timeout)((Object)object));
            this.handleEvent((EventObject)object, (SIPTransaction)object2);
        }
    }
}

