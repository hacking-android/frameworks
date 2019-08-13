/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.NameValueList;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.DialogExt;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.SipListenerExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Authorization;
import gov.nist.javax.sip.header.CSeq;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.From;
import gov.nist.javax.sip.header.MaxForwards;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.RAck;
import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.Reason;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.Require;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.TimeStamp;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialogErrorEvent;
import gov.nist.javax.sip.stack.SIPDialogEventListener;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogDoesNotExistException;
import javax.sip.DialogState;
import javax.sip.IOExceptionEvent;
import javax.sip.InvalidArgumentException;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.SipProvider;
import javax.sip.SipStack;
import javax.sip.Transaction;
import javax.sip.TransactionDoesNotExistException;
import javax.sip.TransactionState;
import javax.sip.address.Address;
import javax.sip.address.Hop;
import javax.sip.address.Router;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ContactHeader;
import javax.sip.header.EventHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.MaxForwardsHeader;
import javax.sip.header.OptionTag;
import javax.sip.header.RequireHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class SIPDialog
implements Dialog,
DialogExt {
    public static final int CONFIRMED_STATE;
    private static final int DIALOG_LINGER_TIME = 8;
    public static final int EARLY_STATE;
    public static final int NULL_STATE = -1;
    public static final int TERMINATED_STATE;
    private static final long serialVersionUID = -1429794423085204069L;
    private transient int ackLine;
    protected transient boolean ackProcessed;
    protected transient boolean ackSeen;
    private transient Semaphore ackSem = new Semaphore(1);
    private transient Object applicationData;
    public transient long auditTag = 0L;
    private transient boolean byeSent;
    protected CallIdHeader callIdHeader;
    protected Contact contactHeader;
    private transient DialogDeleteIfNoAckSentTask dialogDeleteIfNoAckSentTask;
    private transient DialogDeleteTask dialogDeleteTask;
    private String dialogId;
    private int dialogState = -1;
    private transient boolean dialogTerminatedEventDelivered;
    private transient String earlyDialogId;
    private EventHeader eventHeader;
    private transient Set<SIPDialogEventListener> eventListeners;
    private transient SIPTransaction firstTransaction;
    protected String firstTransactionId;
    protected boolean firstTransactionIsServerTransaction;
    protected String firstTransactionMethod;
    protected int firstTransactionPort = 5060;
    protected boolean firstTransactionSecure;
    protected boolean firstTransactionSeen;
    private transient long highestSequenceNumberAcknowledged = -1L;
    protected String hisTag;
    private transient boolean isAcknowledged;
    private transient boolean isAssigned;
    private boolean isBackToBackUserAgent;
    private SIPRequest lastAckReceived;
    private transient SIPRequest lastAckSent;
    private transient long lastInviteOkReceived;
    private SIPResponse lastResponse;
    private transient SIPTransaction lastTransaction;
    protected Address localParty;
    private long localSequenceNumber = 0L;
    private String method;
    protected String myTag;
    protected transient Long nextSeqno;
    private long originalLocalSequenceNumber;
    private transient SIPRequest originalRequest;
    private transient int prevRetransmissionTicks;
    private boolean reInviteFlag;
    private transient int reInviteWaitTime = 100;
    protected Address remoteParty;
    private long remoteSequenceNumber = -1L;
    private Address remoteTarget;
    private transient int retransmissionTicksLeft;
    private RouteList routeList = new RouteList();
    private boolean sequenceNumberValidation = true;
    private boolean serverTransactionFlag;
    private transient SipProviderImpl sipProvider;
    private transient SIPTransactionStack sipStack;
    private transient String stackTrace;
    private boolean terminateOnBye = true;
    protected transient DialogTimerTask timerTask;
    private Semaphore timerTaskLock = new Semaphore(1);

    static {
        EARLY_STATE = DialogState._EARLY;
        CONFIRMED_STATE = DialogState._CONFIRMED;
        TERMINATED_STATE = DialogState._TERMINATED;
    }

    private SIPDialog(SipProviderImpl sipProviderImpl) {
        this.sipProvider = sipProviderImpl;
        this.eventListeners = new CopyOnWriteArraySet<SIPDialogEventListener>();
    }

    public SIPDialog(SipProviderImpl object, SIPResponse object2) {
        this((SipProviderImpl)object);
        this.sipStack = (SIPTransactionStack)((Object)((SipProviderImpl)object).getSipStack());
        this.setLastResponse(null, (SIPResponse)object2);
        this.originalLocalSequenceNumber = this.localSequenceNumber = ((SIPMessage)object2).getCSeq().getSeqNumber();
        this.myTag = ((SIPMessage)object2).getFrom().getTag();
        this.hisTag = ((SIPMessage)object2).getTo().getTag();
        this.localParty = ((SIPMessage)object2).getFrom().getAddress();
        this.remoteParty = ((SIPMessage)object2).getTo().getAddress();
        this.method = ((SIPMessage)object2).getCSeq().getMethod();
        this.callIdHeader = ((SIPMessage)object2).getCallId();
        this.serverTransactionFlag = false;
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Creating a dialog : ");
            ((StringBuilder)object).append(this);
            object2.logDebug(((StringBuilder)object).toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
        this.isBackToBackUserAgent = this.sipStack.isBackToBackUserAgent;
        this.addEventListener(this.sipStack);
    }

    public SIPDialog(SIPClientTransaction sIPClientTransaction, SIPResponse sIPResponse) {
        this(sIPClientTransaction);
        if (sIPResponse != null) {
            this.setLastResponse(sIPClientTransaction, sIPResponse);
            this.isBackToBackUserAgent = this.sipStack.isBackToBackUserAgent;
            return;
        }
        throw new NullPointerException("Null SipResponse");
    }

    public SIPDialog(SIPTransaction object) {
        this(((SIPTransaction)object).getSipProvider());
        Object object2 = (SIPRequest)((SIPTransaction)object).getRequest();
        this.callIdHeader = ((SIPMessage)object2).getCallId();
        this.earlyDialogId = ((SIPRequest)object2).getDialogId(false);
        this.sipStack = ((SIPTransaction)object).sipStack;
        this.sipProvider = ((SIPTransaction)object).getSipProvider();
        if (this.sipProvider != null) {
            this.addTransaction((SIPTransaction)object);
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Creating a dialog : ");
                ((StringBuilder)object2).append(this);
                object.logDebug(((StringBuilder)object2).toString());
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("provider port = ");
                ((StringBuilder)object).append(this.sipProvider.getListeningPoint().getPort());
                object2.logDebug(((StringBuilder)object).toString());
                this.sipStack.getStackLogger().logStackTrace();
            }
            this.isBackToBackUserAgent = this.sipStack.isBackToBackUserAgent;
            this.addEventListener(this.sipStack);
            return;
        }
        throw new NullPointerException("Null Provider!");
    }

    static /* synthetic */ boolean access$600(SIPDialog sIPDialog, int n) {
        return sIPDialog.toRetransmitFinalResponse(n);
    }

    static /* synthetic */ void access$700(SIPDialog sIPDialog, String string, int n, String string2) {
        sIPDialog.raiseIOException(string, n, string2);
    }

    static /* synthetic */ int access$800(SIPDialog sIPDialog) {
        return sIPDialog.dialogState;
    }

    private void addRoute(RecordRouteList object) {
        Object object2;
        Object object3;
        try {
            if (this.isClientDialog()) {
                object3 = new RouteList();
                this.routeList = object3;
                object2 = ((SIPHeaderList)object).listIterator(((SIPHeaderList)object).size());
                while (object2.hasPrevious()) {
                    object = (RecordRoute)object2.previous();
                    if (!true) continue;
                    object3 = new Route();
                    ((AddressParametersHeader)object3).setAddress((AddressImpl)((AddressImpl)((AddressParametersHeader)object).getAddress()).clone());
                    ((ParametersHeader)object3).setParameters((NameValueList)((ParametersHeader)object).getParameters().clone());
                    this.routeList.add(object3);
                }
            } else {
                object3 = new RouteList();
                this.routeList = object3;
                object2 = ((SIPHeaderList)object).listIterator();
                while (object2.hasNext()) {
                    object3 = (RecordRoute)object2.next();
                    if (!true) continue;
                    object = new Route();
                    ((AddressParametersHeader)object).setAddress((AddressImpl)((AddressImpl)((AddressParametersHeader)object3).getAddress()).clone());
                    ((ParametersHeader)object).setParameters((NameValueList)((ParametersHeader)object3).getParameters().clone());
                    this.routeList.add(object);
                }
            }
            return;
        }
        finally {
            if (this.sipStack.getStackLogger().isLoggingEnabled()) {
                object = this.routeList.iterator();
                while (object.hasNext()) {
                    if (((SipURI)((Route)object.next()).getAddress().getURI()).hasLrParam() || !this.sipStack.isLoggingEnabled()) continue;
                    object2 = this.sipStack.getStackLogger();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("NON LR route in Route set detected for dialog : ");
                    ((StringBuilder)object3).append(this);
                    object2.logWarning(((StringBuilder)object3).toString());
                    this.sipStack.getStackLogger().logStackTrace();
                }
            }
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void addRoute(SIPResponse genericObject) {
        synchronized (this) {
            void var1_4;
            block21 : {
                block28 : {
                    Object object;
                    block26 : {
                        block27 : {
                            block24 : {
                                block25 : {
                                    int n;
                                    block22 : {
                                        block23 : {
                                            block20 : {
                                                boolean bl = this.sipStack.isLoggingEnabled();
                                                if (!bl) break block20;
                                                try {
                                                    object = this.sipStack.getStackLogger();
                                                    StringBuilder stringBuilder = new StringBuilder();
                                                    stringBuilder.append("setContact: dialogState: ");
                                                    stringBuilder.append(this);
                                                    stringBuilder.append("state = ");
                                                    stringBuilder.append((Object)this.getState());
                                                    object.logDebug(stringBuilder.toString());
                                                }
                                                catch (Throwable throwable) {
                                                    break block21;
                                                }
                                            }
                                            n = ((SIPResponse)genericObject).getStatusCode();
                                            if (n != 100) break block22;
                                            if (!this.sipStack.isLoggingEnabled()) break block23;
                                            this.sipStack.getStackLogger().logStackTrace();
                                        }
                                        return;
                                    }
                                    int n2 = this.dialogState;
                                    n = TERMINATED_STATE;
                                    if (n2 != n) break block24;
                                    if (!this.sipStack.isLoggingEnabled()) break block25;
                                    this.sipStack.getStackLogger().logStackTrace();
                                }
                                return;
                            }
                            if (this.dialogState != CONFIRMED_STATE) break block26;
                            if (((SIPResponse)genericObject).getStatusCode() / 100 == 2 && !this.isServer() && (object = ((SIPMessage)genericObject).getContactHeaders()) != null && SIPRequest.isTargetRefresh(((SIPMessage)genericObject).getCSeq().getMethod())) {
                                this.setRemoteTarget((ContactHeader)((SIPHeaderList)object).getFirst());
                            }
                            if (!this.sipStack.isLoggingEnabled()) break block27;
                            this.sipStack.getStackLogger().logStackTrace();
                        }
                        return;
                    }
                    try {
                        if (!this.isServer()) {
                            if (this.getState() != DialogState.CONFIRMED && this.getState() != DialogState.TERMINATED) {
                                object = ((SIPMessage)genericObject).getRecordRouteHeaders();
                                if (object != null) {
                                    this.addRoute((RecordRouteList)object);
                                } else {
                                    this.routeList = object = new RouteList();
                                }
                            }
                            if ((genericObject = ((SIPMessage)genericObject).getContactHeaders()) != null) {
                                this.setRemoteTarget((ContactHeader)((SIPHeaderList)genericObject).getFirst());
                            }
                        }
                        if (!this.sipStack.isLoggingEnabled()) break block28;
                        this.sipStack.getStackLogger().logStackTrace();
                    }
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                return;
            }
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logStackTrace();
            }
            throw var1_4;
        }
    }

    public static SIPDialog createFromNOTIFY(SIPClientTransaction sIPClientTransaction, SIPTransaction serializable) {
        SIPDialog sIPDialog = new SIPDialog((SIPTransaction)serializable);
        sIPDialog.serverTransactionFlag = false;
        sIPDialog.lastTransaction = sIPClientTransaction;
        SIPDialog.storeFirstTransactionInfo(sIPDialog, sIPClientTransaction);
        sIPDialog.terminateOnBye = false;
        sIPDialog.localSequenceNumber = sIPClientTransaction.getCSeq();
        serializable = (SIPRequest)((SIPTransaction)serializable).getRequest();
        sIPDialog.remoteSequenceNumber = ((SIPMessage)serializable).getCSeq().getSeqNumber();
        sIPDialog.setDialogId(((SIPRequest)serializable).getDialogId(true));
        sIPDialog.setLocalTag(((SIPMessage)serializable).getToTag());
        sIPDialog.setRemoteTag(((SIPMessage)serializable).getFromTag());
        sIPDialog.setLastResponse(sIPClientTransaction, sIPClientTransaction.getLastResponse());
        sIPDialog.localParty = ((SIPMessage)serializable).getTo().getAddress();
        sIPDialog.remoteParty = ((SIPMessage)serializable).getFrom().getAddress();
        sIPDialog.addRoute((SIPRequest)serializable);
        sIPDialog.setState(CONFIRMED_STATE);
        return sIPDialog;
    }

    private Request createRequest(String object, SIPResponse sIPMessage) throws SipException {
        if (object != null && sIPMessage != null) {
            if (!((String)object).equals("CANCEL")) {
                if (!(this.getState() == null || this.getState().getValue() == TERMINATED_STATE && !((String)object).equalsIgnoreCase("BYE") || this.isServer() && this.getState().getValue() == EARLY_STATE && ((String)object).equalsIgnoreCase("BYE"))) {
                    Serializable serializable;
                    if (this.getRemoteTarget() != null) {
                        serializable = (SipUri)this.getRemoteTarget().getURI().clone();
                    } else {
                        serializable = (SipUri)this.getRemoteParty().getURI().clone();
                        ((SipUri)serializable).clearUriParms();
                    }
                    CSeq cSeq = new CSeq();
                    try {
                        cSeq.setMethod((String)object);
                        cSeq.setSeqNumber(this.getLocalSeqNumber());
                    }
                    catch (Exception exception) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logError("Unexpected error");
                        }
                        InternalErrorHandler.handleException(exception);
                    }
                    ListeningPointImpl listeningPointImpl = (ListeningPointImpl)this.sipProvider.getListeningPoint(sIPMessage.getTopmostVia().getTransport());
                    if (listeningPointImpl == null) {
                        if (this.sipStack.isLoggingEnabled()) {
                            object = this.sipStack.getStackLogger();
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Cannot find listening point for transport ");
                            ((StringBuilder)serializable).append(sIPMessage.getTopmostVia().getTransport());
                            object.logError(((StringBuilder)serializable).toString());
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot find listening point for transport ");
                        ((StringBuilder)object).append(sIPMessage.getTopmostVia().getTransport());
                        throw new SipException(((StringBuilder)object).toString());
                    }
                    Via via = listeningPointImpl.getViaHeader();
                    From from = new From();
                    from.setAddress(this.localParty);
                    To to = new To();
                    to.setAddress(this.remoteParty);
                    sIPMessage = ((SIPResponse)sIPMessage).createRequest((SipUri)serializable, via, cSeq, from, to);
                    if (SIPRequest.isTargetRefresh((String)object)) {
                        serializable = ((ListeningPointImpl)this.sipProvider.getListeningPoint(listeningPointImpl.getTransport())).createContactHeader();
                        ((SipURI)serializable.getAddress().getURI()).setSecure(this.isSecure());
                        sIPMessage.setHeader((Header)serializable);
                    }
                    try {
                        ((CSeq)sIPMessage.getCSeq()).setSeqNumber(this.localSequenceNumber + 1L);
                    }
                    catch (InvalidArgumentException invalidArgumentException) {
                        InternalErrorHandler.handleException(invalidArgumentException);
                    }
                    if (((String)object).equals("SUBSCRIBE") && (object = this.eventHeader) != null) {
                        sIPMessage.addHeader((Header)object);
                    }
                    try {
                        if (this.getLocalTag() != null) {
                            from.setTag(this.getLocalTag());
                        } else {
                            from.removeTag();
                        }
                        if (this.getRemoteTag() != null) {
                            to.setTag(this.getRemoteTag());
                        } else {
                            to.removeTag();
                        }
                    }
                    catch (ParseException parseException) {
                        InternalErrorHandler.handleException(parseException);
                    }
                    this.updateRequest((SIPRequest)sIPMessage);
                    return sIPMessage;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Dialog  ");
                ((StringBuilder)object).append(this.getDialogId());
                ((StringBuilder)object).append(" not yet established or terminated ");
                ((StringBuilder)object).append((Object)this.getState());
                throw new SipException(((StringBuilder)object).toString());
            }
            throw new SipException("Dialog.createRequest(): Invalid request");
        }
        throw new NullPointerException("null argument");
    }

    private void doTargetRefresh(SIPMessage genericObject) {
        if ((genericObject = ((SIPMessage)genericObject).getContactHeaders()) != null) {
            this.setRemoteTarget((Contact)((SIPHeaderList)genericObject).getFirst());
        }
    }

    private RouteList getRouteList() {
        synchronized (this) {
            Object object;
            Object object2;
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("getRouteList ");
                ((StringBuilder)object2).append(this);
                object.logDebug(((StringBuilder)object2).toString());
            }
            new gov.nist.javax.sip.header.RouteList();
            object = new RouteList();
            if (this.routeList != null) {
                object2 = this.routeList.listIterator();
                while (object2.hasNext()) {
                    ((SIPHeaderList)object).add((Route)((Route)object2.next()).clone());
                }
            }
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("----- ");
                StackLogger stackLogger = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("getRouteList for ");
                ((StringBuilder)object2).append(this);
                stackLogger.logDebug(((StringBuilder)object2).toString());
                stackLogger = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("RouteList = ");
                ((StringBuilder)object2).append(((RouteList)object).encode());
                stackLogger.logDebug(((StringBuilder)object2).toString());
                if (this.routeList != null) {
                    stackLogger = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("myRouteList = ");
                    ((StringBuilder)object2).append(this.routeList.encode());
                    stackLogger.logDebug(((StringBuilder)object2).toString());
                }
                this.sipStack.getStackLogger().logDebug("----- ");
            }
            return object;
        }
    }

    private boolean isClientDialog() {
        return (SIPTransaction)this.getFirstTransaction() instanceof SIPClientTransaction;
    }

    private static final boolean optionPresent(ListIterator listIterator, String string) {
        while (listIterator.hasNext()) {
            OptionTag optionTag = (OptionTag)listIterator.next();
            if (optionTag == null || !string.equalsIgnoreCase(optionTag.getOptionTag())) continue;
            return true;
        }
        return false;
    }

    private void printRouteList() {
        if (this.sipStack.isLoggingEnabled()) {
            Object object = this.sipStack.getStackLogger();
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("this : ");
            ((StringBuilder)object2).append(this);
            object.logDebug(((StringBuilder)object2).toString());
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("printRouteList : ");
            ((StringBuilder)object).append(this.routeList.encode());
            object2.logDebug(((StringBuilder)object).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void raiseErrorEvent(int n) {
        SIPDialogErrorEvent sIPDialogErrorEvent = new SIPDialogErrorEvent(this, n);
        Set<SIPDialogEventListener> set = this.eventListeners;
        synchronized (set) {
            Iterator<SIPDialogEventListener> iterator = this.eventListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().dialogErrorEvent(sIPDialogErrorEvent);
            }
        }
        this.eventListeners.clear();
        if (n != 2 && n != 1 && n != 3) {
            this.delete();
        }
        this.stopTimer();
    }

    private void raiseIOException(String object, int n, String string) {
        object = new IOExceptionEvent(this, (String)object, n, string);
        this.sipProvider.handleEvent((EventObject)object, null);
        this.setState(TERMINATED_STATE);
    }

    private void recordStackTrace() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        new Exception().printStackTrace(printWriter);
        this.stackTrace = stringWriter.getBuffer().toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendAck(Request object, boolean bl) throws SipException {
        Object object2;
        Object object3;
        Serializable serializable = (SIPRequest)object;
        if (this.sipStack.isLoggingEnabled()) {
            object3 = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("sendAck");
            ((StringBuilder)object2).append(this);
            object3.logDebug(((StringBuilder)object2).toString());
        }
        if (!((SIPRequest)serializable).getMethod().equals("ACK")) {
            throw new SipException("Bad request method -- should be ACK");
        }
        if (this.getState() != null && this.getState().getValue() != EARLY_STATE) {
            block24 : {
                IOException iOException2;
                block25 : {
                    if (!this.getCallId().getCallId().equals(((SIPRequest)object).getCallId().getCallId())) {
                        if (this.sipStack.isLoggingEnabled()) {
                            object = this.sipStack.getStackLogger();
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("CallID ");
                            ((StringBuilder)object3).append(this.getCallId());
                            object.logError(((StringBuilder)object3).toString());
                            object = this.sipStack.getStackLogger();
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("RequestCallID = ");
                            ((StringBuilder)object3).append(((SIPMessage)serializable).getCallId().getCallId());
                            object.logError(((StringBuilder)object3).toString());
                            object = this.sipStack.getStackLogger();
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("dialog =  ");
                            ((StringBuilder)serializable).append(this);
                            object.logError(((StringBuilder)serializable).toString());
                        }
                        throw new SipException("Bad call ID in request");
                    }
                    try {
                        if (this.sipStack.isLoggingEnabled()) {
                            object3 = this.sipStack.getStackLogger();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("setting from tag For outgoing ACK= ");
                            ((StringBuilder)object2).append(this.getLocalTag());
                            object3.logDebug(((StringBuilder)object2).toString());
                            object3 = this.sipStack.getStackLogger();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("setting To tag for outgoing ACK = ");
                            ((StringBuilder)object2).append(this.getRemoteTag());
                            object3.logDebug(((StringBuilder)object2).toString());
                            object3 = this.sipStack.getStackLogger();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("ack = ");
                            ((StringBuilder)object2).append(serializable);
                            object3.logDebug(((StringBuilder)object2).toString());
                        }
                        if (this.getLocalTag() != null) {
                            ((SIPMessage)serializable).getFrom().setTag(this.getLocalTag());
                        }
                        if (this.getRemoteTag() != null) {
                            ((SIPMessage)serializable).getTo().setTag(this.getRemoteTag());
                        }
                        if ((object3 = this.sipStack.getNextHop((SIPRequest)serializable)) == null) break block24;
                    }
                    catch (ParseException parseException) {
                        throw new SipException(parseException.getMessage());
                    }
                    try {
                        Object object4;
                        if (this.sipStack.isLoggingEnabled()) {
                            object4 = this.sipStack.getStackLogger();
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("hop = ");
                            ((StringBuilder)object2).append(object3);
                            object4.logDebug(((StringBuilder)object2).toString());
                        }
                        if ((object2 = (ListeningPointImpl)this.sipProvider.getListeningPoint(object3.getTransport())) == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No listening point for this provider registered at ");
                            ((StringBuilder)object).append(object3);
                            serializable = new SipException(((StringBuilder)object).toString());
                            throw serializable;
                        }
                        object4 = InetAddress.getByName(object3.getHost());
                        object2 = ((ListeningPointImpl)object2).getMessageProcessor().createMessageChannel((InetAddress)object4, object3.getPort());
                        boolean bl2 = false;
                        if (!this.isAckSent(((SIPRequest)object).getCSeq().getSeqNumber())) {
                            bl2 = true;
                        }
                        this.setLastAckSent((SIPRequest)serializable);
                        ((MessageChannel)object2).sendMessage((SIPMessage)serializable);
                        this.isAcknowledged = true;
                        this.highestSequenceNumberAcknowledged = Math.max(this.highestSequenceNumberAcknowledged, ((SIPMessage)serializable).getCSeq().getSeqNumber());
                        if (bl2 && this.isBackToBackUserAgent) {
                            this.releaseAckSem();
                        } else if (this.sipStack.isLoggingEnabled()) {
                            object = this.sipStack.getStackLogger();
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Not releasing ack sem for ");
                            ((StringBuilder)serializable).append(this);
                            ((StringBuilder)serializable).append(" isAckSent ");
                            ((StringBuilder)serializable).append(bl2);
                            object.logDebug(((StringBuilder)serializable).toString());
                        }
                    }
                    catch (Exception exception) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logException(exception);
                        }
                        throw new SipException("Could not create message channel", exception);
                    }
                    catch (SipException sipException) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logException(sipException);
                        }
                        throw sipException;
                    }
                    catch (IOException iOException2) {
                        if (bl) break block25;
                        this.raiseIOException(object3.getHost(), object3.getPort(), object3.getTransport());
                    }
                    object = this.dialogDeleteTask;
                    if (object != null) {
                        ((TimerTask)object).cancel();
                        this.dialogDeleteTask = null;
                    }
                    this.ackSeen = true;
                    return;
                }
                throw new SipException("Could not send ack", iOException2);
            }
            throw new SipException("No route!");
        }
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Bad Dialog State for ");
            ((StringBuilder)serializable).append(this);
            ((StringBuilder)serializable).append(" dialogID = ");
            ((StringBuilder)serializable).append(this.getDialogId());
            object.logError(((StringBuilder)serializable).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad dialog state ");
        ((StringBuilder)object).append((Object)this.getState());
        throw new SipException(((StringBuilder)object).toString());
    }

    private void setCallId(SIPRequest sIPRequest) {
        this.callIdHeader = sIPRequest.getCallId();
    }

    private void setLastAckReceived(SIPRequest sIPRequest) {
        this.lastAckReceived = sIPRequest;
    }

    private void setLastAckSent(SIPRequest sIPRequest) {
        this.lastAckSent = sIPRequest;
    }

    private void setLocalParty(SIPMessage sIPMessage) {
        this.localParty = !this.isServer() ? sIPMessage.getFrom().getAddress() : sIPMessage.getTo().getAddress();
    }

    private void setLocalSequenceNumber(long l) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setLocalSequenceNumber: original  ");
            stringBuilder.append(this.localSequenceNumber);
            stringBuilder.append(" new  = ");
            stringBuilder.append(l);
            stackLogger.logDebug(stringBuilder.toString());
        }
        if (l > this.localSequenceNumber) {
            this.localSequenceNumber = l;
            return;
        }
        throw new RuntimeException("Sequence number should not decrease !");
    }

    private void setLocalTag(String string) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("set Local tag ");
            stringBuilder.append(string);
            stringBuilder.append(" ");
            stringBuilder.append(this.dialogId);
            stackLogger.logDebug(stringBuilder.toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
        this.myTag = string;
    }

    private void setRemoteParty(SIPMessage object) {
        this.remoteParty = !this.isServer() ? ((SIPMessage)object).getTo().getAddress() : ((SIPMessage)object).getFrom().getAddress();
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("settingRemoteParty ");
            stringBuilder.append(this.remoteParty);
            object.logDebug(stringBuilder.toString());
        }
    }

    private void setRemoteTag(String string) {
        Object object;
        Object object2;
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("setRemoteTag(): ");
            ((StringBuilder)object2).append(this);
            ((StringBuilder)object2).append(" remoteTag = ");
            ((StringBuilder)object2).append(this.hisTag);
            ((StringBuilder)object2).append(" new tag = ");
            ((StringBuilder)object2).append(string);
            object.logDebug(((StringBuilder)object2).toString());
        }
        if ((object2 = this.hisTag) != null && string != null && !string.equals(object2)) {
            if (this.getState() != DialogState.EARLY) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("Dialog is already established -- ignoring remote tag re-assignment");
                }
                return;
            }
            if (this.sipStack.isRemoteTagReassignmentAllowed()) {
                if (this.sipStack.isLoggingEnabled()) {
                    object2 = this.sipStack.getStackLogger();
                    object = new StringBuilder();
                    ((StringBuilder)object).append("UNSAFE OPERATION !  tag re-assignment ");
                    ((StringBuilder)object).append(this.hisTag);
                    ((StringBuilder)object).append(" trying to set to ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" can cause unexpected effects ");
                    object2.logDebug(((StringBuilder)object).toString());
                }
                boolean bl = false;
                if (this.sipStack.getDialog(this.dialogId) == this) {
                    this.sipStack.removeDialog(this.dialogId);
                    bl = true;
                }
                this.dialogId = null;
                this.hisTag = string;
                if (bl) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("ReInserting Dialog");
                    }
                    this.sipStack.putDialog(this);
                }
            }
        } else if (string != null) {
            this.hisTag = string;
        } else if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logWarning("setRemoteTag : called with null argument ");
        }
    }

    private static void storeFirstTransactionInfo(SIPDialog sIPDialog, SIPTransaction serializable) {
        sIPDialog.firstTransaction = serializable;
        sIPDialog.firstTransactionSeen = true;
        sIPDialog.firstTransactionIsServerTransaction = ((SIPTransaction)serializable).isServerTransaction();
        sIPDialog.firstTransactionSecure = ((SIPTransaction)serializable).getRequest().getRequestURI().getScheme().equalsIgnoreCase("sips");
        sIPDialog.firstTransactionPort = ((SIPTransaction)serializable).getPort();
        sIPDialog.firstTransactionId = ((SIPTransaction)serializable).getBranchId();
        sIPDialog.firstTransactionMethod = ((SIPTransaction)serializable).getMethod();
        if (sIPDialog.isServer()) {
            serializable = (serializable = ((SIPServerTransaction)serializable).getLastResponse()) != null ? ((SIPMessage)serializable).getContactHeader() : null;
            sIPDialog.contactHeader = serializable;
        } else {
            serializable = (SIPClientTransaction)serializable;
            sIPDialog.contactHeader = ((SIPTransaction)serializable).getOriginalRequest().getContactHeader();
        }
    }

    private boolean toRetransmitFinalResponse(int n) {
        int n2;
        this.retransmissionTicksLeft = n2 = this.retransmissionTicksLeft - 1;
        if (n2 == 0) {
            n2 = this.prevRetransmissionTicks;
            this.retransmissionTicksLeft = n2 * 2 <= n ? n2 * 2 : n2;
            this.prevRetransmissionTicks = this.retransmissionTicksLeft;
            return true;
        }
        return false;
    }

    private void updateRequest(SIPRequest sIPRequest) {
        RouteList routeList = this.getRouteList();
        if (routeList.size() > 0) {
            sIPRequest.setHeader((Header)routeList);
        } else {
            sIPRequest.removeHeader("Route");
        }
        if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
            sIPRequest.setHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
        }
    }

    void ackReceived(SIPRequest object) {
        block9 : {
            if (this.ackSeen) {
                return;
            }
            SIPServerTransaction sIPServerTransaction = this.getInviteTransaction();
            if (sIPServerTransaction != null && sIPServerTransaction.getCSeq() == ((SIPMessage)object).getCSeq().getSeqNumber()) {
                Serializable serializable;
                block8 : {
                    this.acquireTimerTaskSem();
                    if (this.timerTask != null) {
                        this.timerTask.cancel();
                        this.timerTask = null;
                    }
                    this.ackSeen = true;
                    serializable = this.dialogDeleteTask;
                    if (serializable == null) break block8;
                    ((TimerTask)((Object)serializable)).cancel();
                    this.dialogDeleteTask = null;
                }
                this.setLastAckReceived((SIPRequest)object);
                if (this.sipStack.isLoggingEnabled()) {
                    object = this.sipStack.getStackLogger();
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("ackReceived for ");
                    ((StringBuilder)serializable).append(sIPServerTransaction.getMethod());
                    object.logDebug(((StringBuilder)serializable).toString());
                    this.ackLine = this.sipStack.getStackLogger().getLineCount();
                    this.printDebugInfo();
                }
                if (this.isBackToBackUserAgent) {
                    this.releaseAckSem();
                }
                this.setState(CONFIRMED_STATE);
                break block9;
                finally {
                    this.releaseTimerTaskSem();
                }
            }
        }
    }

    public void acquireTimerTaskSem() {
        boolean bl;
        try {
            bl = this.timerTaskLock.tryAcquire(10L, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            bl = false;
        }
        if (bl) {
            return;
        }
        throw new IllegalStateException("Impossible to acquire the dialog timer task lock");
    }

    public void addEventListener(SIPDialogEventListener sIPDialogEventListener) {
        this.eventListeners.add(sIPDialogEventListener);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addRoute(SIPRequest genericObject) {
        synchronized (this) {
            Object object;
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("setContact: dialogState: ");
                stringBuilder.append(this);
                stringBuilder.append("state = ");
                stringBuilder.append((Object)this.getState());
                object.logDebug(stringBuilder.toString());
            }
            if (this.dialogState == CONFIRMED_STATE && SIPRequest.isTargetRefresh(((SIPRequest)genericObject).getMethod())) {
                this.doTargetRefresh((SIPMessage)genericObject);
            }
            if (this.dialogState != CONFIRMED_STATE && this.dialogState != TERMINATED_STATE) {
                object = ((SIPMessage)genericObject).getToTag();
                if (object != null) {
                    return;
                }
                object = ((SIPMessage)genericObject).getRecordRouteHeaders();
                if (object != null) {
                    this.addRoute((RecordRouteList)object);
                } else {
                    this.routeList = object = new RouteList();
                }
                genericObject = ((SIPMessage)genericObject).getContactHeaders();
                if (genericObject != null) {
                    this.setRemoteTarget((ContactHeader)((SIPHeaderList)genericObject).getFirst());
                }
                return;
            }
            return;
        }
    }

    public void addTransaction(SIPTransaction sIPTransaction) {
        Object object = sIPTransaction.getOriginalRequest();
        if (this.firstTransactionSeen && !this.firstTransactionId.equals(sIPTransaction.getBranchId()) && sIPTransaction.getMethod().equals(this.firstTransactionMethod)) {
            this.reInviteFlag = true;
        }
        if (!this.firstTransactionSeen) {
            SIPDialog.storeFirstTransactionInfo(this, sIPTransaction);
            if (((SIPRequest)object).getMethod().equals("SUBSCRIBE")) {
                this.eventHeader = (EventHeader)((SIPMessage)object).getHeader("Event");
            }
            this.setLocalParty((SIPMessage)object);
            this.setRemoteParty((SIPMessage)object);
            this.setCallId((SIPRequest)object);
            if (this.originalRequest == null) {
                this.originalRequest = object;
            }
            if (this.method == null) {
                this.method = ((SIPRequest)object).getMethod();
            }
            if (sIPTransaction instanceof SIPServerTransaction) {
                this.hisTag = ((SIPMessage)object).getFrom().getTag();
            } else {
                this.setLocalSequenceNumber(((SIPMessage)object).getCSeq().getSeqNumber());
                this.originalLocalSequenceNumber = this.localSequenceNumber;
                this.myTag = ((SIPMessage)object).getFrom().getTag();
                if (this.myTag == null && this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logError("The request's From header is missing the required Tag parameter.");
                }
            }
        } else if (sIPTransaction.getMethod().equals(this.firstTransactionMethod) && this.firstTransactionIsServerTransaction != sIPTransaction.isServerTransaction()) {
            SIPDialog.storeFirstTransactionInfo(this, sIPTransaction);
            this.setLocalParty((SIPMessage)object);
            this.setRemoteParty((SIPMessage)object);
            this.setCallId((SIPRequest)object);
            this.originalRequest = object;
            this.method = ((SIPRequest)object).getMethod();
        }
        if (sIPTransaction instanceof SIPServerTransaction) {
            this.setRemoteSequenceNumber(((SIPMessage)object).getCSeq().getSeqNumber());
        }
        this.lastTransaction = sIPTransaction;
        if (this.sipStack.isLoggingEnabled()) {
            Object object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Transaction Added ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(this.myTag);
            ((StringBuilder)object).append("/");
            ((StringBuilder)object).append(this.hisTag);
            object2.logDebug(((StringBuilder)object).toString());
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("TID = ");
            ((StringBuilder)object2).append(sIPTransaction.getTransactionId());
            ((StringBuilder)object2).append("/");
            ((StringBuilder)object2).append(sIPTransaction.isServerTransaction());
            object.logDebug(((StringBuilder)object2).toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Request createAck(long l) throws InvalidArgumentException, SipException {
        Serializable serializable;
        Object object;
        if (!this.method.equals("INVITE")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dialog was not created with an INVITE");
            stringBuilder.append(this.method);
            throw new SipException(stringBuilder.toString());
        }
        if (l <= 0L) {
            throw new InvalidArgumentException("bad cseq <= 0 ");
        }
        if (l > 0xFFFFFFFFL) {
            throw new InvalidArgumentException("bad cseq > 4294967295");
        }
        if (this.remoteTarget == null) {
            throw new SipException("Cannot create ACK - no remote Target!");
        }
        if (this.sipStack.isLoggingEnabled()) {
            serializable = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("createAck ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" cseqno ");
            ((StringBuilder)object).append(l);
            serializable.logDebug(((StringBuilder)object).toString());
        }
        if (this.lastInviteOkReceived < l) {
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("WARNING : Attempt to crete ACK without OK ");
                ((StringBuilder)serializable).append(this);
                object.logDebug(((StringBuilder)serializable).toString());
                serializable = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("LAST RESPONSE = ");
                ((StringBuilder)object).append(this.lastResponse);
                serializable.logDebug(((StringBuilder)object).toString());
            }
            throw new SipException("Dialog not yet established -- no OK response!");
        }
        try {
            NameValueList nameValueList;
            Object object2;
            serializable = this.routeList != null && !this.routeList.isEmpty() ? (SipURI)((Route)this.routeList.getFirst()).getAddress().getURI() : (SipURI)this.remoteTarget.getURI();
            object = object2 = serializable.getTransportParam();
            if (object2 == null) {
                object = serializable.isSecure() ? "TLS" : "UDP";
            }
            if ((ListeningPointImpl)this.sipProvider.getListeningPoint((String)object) == null) {
                if (this.sipStack.isLoggingEnabled()) {
                    object2 = this.sipStack.getStackLogger();
                    Object object3 = new StringBuilder();
                    ((StringBuilder)object3).append("remoteTargetURI ");
                    ((StringBuilder)object3).append(this.remoteTarget.getURI());
                    object2.logError(((StringBuilder)object3).toString());
                    object3 = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("uri4transport = ");
                    ((StringBuilder)object2).append(serializable);
                    object3.logError(((StringBuilder)object2).toString());
                    object2 = this.sipStack.getStackLogger();
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("No LP found for transport=");
                    ((StringBuilder)serializable).append((String)object);
                    object2.logError(((StringBuilder)serializable).toString());
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot create ACK - no ListeningPoint for transport towards next hop found:");
                ((StringBuilder)object2).append((String)object);
                serializable = new SipException(((StringBuilder)object2).toString());
                throw serializable;
            }
            object = new SIPRequest();
            ((SIPRequest)object).setMethod("ACK");
            ((SIPRequest)object).setRequestURI((SipUri)this.getRemoteTarget().getURI().clone());
            ((SIPMessage)object).setCallId(this.callIdHeader);
            serializable = new CSeq(l, "ACK");
            ((SIPMessage)object).setCSeq((CSeqHeader)serializable);
            serializable = new ArrayList();
            object2 = this.lastResponse.getTopmostVia();
            ((ParametersHeader)object2).removeParameters();
            if (this.originalRequest != null && this.originalRequest.getTopmostVia() != null && (nameValueList = this.originalRequest.getTopmostVia().getParameters()) != null && nameValueList.size() > 0) {
                ((ParametersHeader)object2).setParameters((NameValueList)nameValueList.clone());
            }
            ((Via)object2).setBranch(Utils.getInstance().generateBranchId());
            serializable.add(object2);
            ((SIPMessage)object).setVia((List)((Object)serializable));
            serializable = new From();
            ((From)serializable).setAddress(this.localParty);
            ((From)serializable).setTag(this.myTag);
            ((SIPMessage)object).setFrom((FromHeader)serializable);
            serializable = new To();
            ((AddressParametersHeader)serializable).setAddress(this.remoteParty);
            if (this.hisTag != null) {
                ((To)serializable).setTag(this.hisTag);
            }
            ((SIPMessage)object).setTo((ToHeader)serializable);
            serializable = new MaxForwards(70);
            ((SIPMessage)object).setMaxForwards((MaxForwardsHeader)serializable);
            if (this.originalRequest != null && (serializable = this.originalRequest.getAuthorization()) != null) {
                ((SIPMessage)object).setHeader((Header)serializable);
            }
            this.updateRequest((SIPRequest)object);
            return object;
        }
        catch (Exception exception) {
            InternalErrorHandler.handleException(exception);
            throw new SipException("unexpected exception ", exception);
        }
    }

    @Override
    public Request createPrack(Response cloneable) throws DialogDoesNotExistException, SipException {
        if (this.getState() != null && !this.getState().equals((Object)DialogState.TERMINATED)) {
            if ((RSeq)cloneable.getHeader("RSeq") != null) {
                try {
                    SIPResponse sIPResponse = (SIPResponse)cloneable;
                    SIPRequest sIPRequest = (SIPRequest)this.createRequest("PRACK", (SIPResponse)cloneable);
                    sIPRequest.setToTag(sIPResponse.getTo().getTag());
                    RAck rAck = new RAck();
                    cloneable = (RSeq)cloneable.getHeader("RSeq");
                    rAck.setMethod(sIPResponse.getCSeq().getMethod());
                    rAck.setCSequenceNumber((int)sIPResponse.getCSeq().getSeqNumber());
                    rAck.setRSequenceNumber(((RSeq)cloneable).getSeqNumber());
                    sIPRequest.setHeader(rAck);
                    return sIPRequest;
                }
                catch (Exception exception) {
                    InternalErrorHandler.handleException(exception);
                    return null;
                }
            }
            throw new SipException("Missing RSeq Header");
        }
        throw new DialogDoesNotExistException("Dialog not initialized or terminated");
    }

    @Override
    public Response createReliableProvisionalResponse(int n) throws InvalidArgumentException, SipException {
        if (this.firstTransactionIsServerTransaction) {
            if (n > 100 && n <= 199) {
                GenericObject genericObject = this.originalRequest;
                if (((SIPRequest)genericObject).getMethod().equals("INVITE")) {
                    ListIterator<SIPHeader> listIterator = ((SIPMessage)genericObject).getHeaders("Supported");
                    if (listIterator != null && SIPDialog.optionPresent(listIterator, "100rel") || (listIterator = ((SIPMessage)genericObject).getHeaders("Require")) != null && SIPDialog.optionPresent(listIterator, "100rel")) {
                        listIterator = ((SIPRequest)genericObject).createResponse(n);
                        Require require = new Require();
                        try {
                            require.setOptionTag("100rel");
                        }
                        catch (Exception exception) {
                            InternalErrorHandler.handleException(exception);
                        }
                        ((SIPMessage)((Object)listIterator)).addHeader(require);
                        new RSeq().setSeqNumber(1L);
                        genericObject = ((SIPMessage)genericObject).getRecordRouteHeaders();
                        if (genericObject != null) {
                            ((SIPMessage)((Object)listIterator)).setHeader((Header)((RecordRouteList)((RecordRouteList)genericObject).clone()));
                        }
                        return listIterator;
                    }
                    throw new SipException("No Supported/Require 100rel header in the request");
                }
                throw new SipException("Bad method");
            }
            throw new InvalidArgumentException("Bad status code ");
        }
        throw new SipException("Not a Server Dialog!");
    }

    @Override
    public Request createRequest(String string) throws SipException {
        if (!string.equals("ACK") && !string.equals("PRACK")) {
            SIPResponse sIPResponse = this.lastResponse;
            if (sIPResponse != null) {
                return this.createRequest(string, sIPResponse);
            }
            throw new SipException("Dialog not yet established -- no response!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid method specified for createRequest:");
        stringBuilder.append(string);
        throw new SipException(stringBuilder.toString());
    }

    @Override
    public void delete() {
        this.setState(TERMINATED_STATE);
    }

    @Override
    public void disableSequenceNumberValidation() {
        this.sequenceNumberValidation = false;
    }

    public void doDeferredDelete() {
        if (this.sipStack.getTimer() == null) {
            this.setState(TERMINATED_STATE);
        } else {
            this.dialogDeleteTask = new DialogDeleteTask();
            this.sipStack.getTimer().schedule((TimerTask)this.dialogDeleteTask, 32000L);
        }
    }

    public void doDeferredDeleteIfNoAckSent(long l) {
        synchronized (this) {
            if (this.sipStack.getTimer() == null) {
                this.setState(TERMINATED_STATE);
            } else if (this.dialogDeleteIfNoAckSentTask == null) {
                DialogDeleteIfNoAckSentTask dialogDeleteIfNoAckSentTask;
                this.dialogDeleteIfNoAckSentTask = dialogDeleteIfNoAckSentTask = new DialogDeleteIfNoAckSentTask(l);
                this.sipStack.getTimer().schedule((TimerTask)this.dialogDeleteIfNoAckSentTask, 32000L);
            }
            return;
        }
    }

    @Override
    public Object getApplicationData() {
        return this.applicationData;
    }

    @Override
    public CallIdHeader getCallId() {
        return this.callIdHeader;
    }

    @Override
    public String getDialogId() {
        SIPResponse sIPResponse;
        if (this.dialogId == null && (sIPResponse = this.lastResponse) != null) {
            this.dialogId = sIPResponse.getDialogId(this.isServer());
        }
        return this.dialogId;
    }

    String getEarlyDialogId() {
        return this.earlyDialogId;
    }

    EventHeader getEventHeader() {
        return this.eventHeader;
    }

    @Override
    public Transaction getFirstTransaction() {
        return this.firstTransaction;
    }

    public SIPServerTransaction getInviteTransaction() {
        DialogTimerTask dialogTimerTask = this.timerTask;
        if (dialogTimerTask != null) {
            return dialogTimerTask.transaction;
        }
        return null;
    }

    protected SIPRequest getLastAckReceived() {
        return this.lastAckReceived;
    }

    public SIPRequest getLastAckSent() {
        return this.lastAckSent;
    }

    public SIPResponse getLastResponse() {
        return this.lastResponse;
    }

    public SIPTransaction getLastTransaction() {
        return this.lastTransaction;
    }

    @Override
    public Address getLocalParty() {
        return this.localParty;
    }

    @Override
    public long getLocalSeqNumber() {
        return this.localSequenceNumber;
    }

    @Override
    public int getLocalSequenceNumber() {
        return (int)this.localSequenceNumber;
    }

    @Override
    public String getLocalTag() {
        return this.myTag;
    }

    public String getMethod() {
        return this.method;
    }

    public Contact getMyContactHeader() {
        return this.contactHeader;
    }

    public long getOriginalLocalSequenceNumber() {
        return this.originalLocalSequenceNumber;
    }

    @Override
    public Address getRemoteParty() {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("gettingRemoteParty ");
            stringBuilder.append(this.remoteParty);
            stackLogger.logDebug(stringBuilder.toString());
        }
        return this.remoteParty;
    }

    @Override
    public long getRemoteSeqNumber() {
        return this.remoteSequenceNumber;
    }

    @Override
    public int getRemoteSequenceNumber() {
        return (int)this.remoteSequenceNumber;
    }

    @Override
    public String getRemoteTag() {
        return this.hisTag;
    }

    @Override
    public Address getRemoteTarget() {
        return this.remoteTarget;
    }

    @Override
    public Iterator getRouteSet() {
        if (this.routeList == null) {
            return new LinkedList().listIterator();
        }
        return this.getRouteList().listIterator();
    }

    @Override
    public SipProviderImpl getSipProvider() {
        return this.sipProvider;
    }

    SIPTransactionStack getStack() {
        return this.sipStack;
    }

    @Override
    public DialogState getState() {
        int n = this.dialogState;
        if (n == -1) {
            return null;
        }
        return DialogState.getObject(n);
    }

    public boolean handleAck(SIPServerTransaction sIPServerTransaction) {
        SIPRequest sIPRequest = sIPServerTransaction.getOriginalRequest();
        boolean bl = this.isAckSeen();
        SIPResponse sIPResponse = null;
        if (bl && this.getRemoteSeqNumber() == sIPRequest.getCSeq().getSeqNumber()) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("ACK already seen by dialog -- dropping Ack retransmission");
            }
            this.acquireTimerTaskSem();
            try {
                if (this.timerTask != null) {
                    this.timerTask.cancel();
                    this.timerTask = null;
                }
                return false;
            }
            finally {
                this.releaseTimerTaskSem();
            }
        }
        if (this.getState() == DialogState.TERMINATED) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dialog is terminated -- dropping ACK");
            }
            return false;
        }
        SIPServerTransaction sIPServerTransaction2 = this.getInviteTransaction();
        if (sIPServerTransaction2 != null) {
            sIPResponse = sIPServerTransaction2.getLastResponse();
        }
        if (sIPServerTransaction2 != null && sIPResponse != null && sIPResponse.getStatusCode() / 100 == 2 && sIPResponse.getCSeq().getMethod().equals("INVITE") && sIPResponse.getCSeq().getSeqNumber() == sIPRequest.getCSeq().getSeqNumber()) {
            sIPServerTransaction.setDialog(this, sIPResponse.getDialogId(false));
            this.ackReceived(sIPRequest);
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("ACK for 2XX response --- sending to TU ");
            }
            return true;
        }
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logDebug(" INVITE transaction not found  -- Discarding ACK");
        }
        return false;
    }

    public boolean handlePrack(SIPRequest genericObject) {
        if (!this.isServer()) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- not a server Dialog");
            }
            return false;
        }
        SIPServerTransaction sIPServerTransaction = (SIPServerTransaction)this.getFirstTransaction();
        GenericObject genericObject2 = sIPServerTransaction.getReliableProvisionalResponse();
        if (genericObject2 == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- ReliableResponse not found");
            }
            return false;
        }
        if ((genericObject = (RAck)((SIPMessage)genericObject).getHeader("RAck")) == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- rack header not found");
            }
            return false;
        }
        CSeq cSeq = (CSeq)((SIPMessage)genericObject2).getCSeq();
        if (!((RAck)genericObject).getMethod().equals(cSeq.getMethod())) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- CSeq Header does not match PRACK");
            }
            return false;
        }
        if (((RAck)genericObject).getCSeqNumberLong() != cSeq.getSeqNumber()) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- CSeq Header does not match PRACK");
            }
            return false;
        }
        genericObject2 = (RSeq)((SIPMessage)genericObject2).getHeader("RSeq");
        if (((RAck)genericObject).getRSequenceNumber() != ((RSeq)genericObject2).getSeqNumber()) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping Prack -- RSeq Header does not match PRACK");
            }
            return false;
        }
        return sIPServerTransaction.prackRecieved();
    }

    @Override
    public void incrementLocalSequenceNumber() {
        ++this.localSequenceNumber;
    }

    public boolean isAckSeen() {
        return this.ackSeen;
    }

    public boolean isAckSent(long l) {
        SIPTransaction sIPTransaction = this.getLastTransaction();
        boolean bl = true;
        if (sIPTransaction == null) {
            return true;
        }
        if (this.getLastTransaction() instanceof ClientTransaction) {
            if (this.getLastAckSent() == null) {
                return false;
            }
            if (l > this.getLastAckSent().getCSeq().getSeqNumber()) {
                bl = false;
            }
            return bl;
        }
        return true;
    }

    public boolean isAssigned() {
        return this.isAssigned;
    }

    public boolean isAtleastOneAckSent() {
        return this.isAcknowledged;
    }

    public boolean isBackToBackUserAgent() {
        return this.isBackToBackUserAgent;
    }

    protected boolean isReInvite() {
        return this.reInviteFlag;
    }

    public boolean isRequestConsumable(SIPRequest serializable) {
        synchronized (this) {
            block5 : {
                boolean bl;
                block7 : {
                    block6 : {
                        if (((SIPRequest)serializable).getMethod().equals("ACK")) break block5;
                        boolean bl2 = this.isSequnceNumberValidation();
                        bl = true;
                        if (bl2) break block6;
                        return true;
                    }
                    long l = this.remoteSequenceNumber;
                    long l2 = ((SIPMessage)serializable).getCSeq().getSeqNumber();
                    if (l < l2) break block7;
                    bl = false;
                }
                return bl;
            }
            serializable = new RuntimeException("Illegal method");
            throw serializable;
        }
    }

    @Override
    public boolean isSecure() {
        return this.firstTransactionSecure;
    }

    public boolean isSequnceNumberValidation() {
        return this.sequenceNumberValidation;
    }

    @Override
    public boolean isServer() {
        if (!this.firstTransactionSeen) {
            return this.serverTransactionFlag;
        }
        return this.firstTransactionIsServerTransaction;
    }

    boolean isTerminatedOnBye() {
        return this.terminateOnBye;
    }

    public void printDebugInfo() {
        if (this.sipStack.isLoggingEnabled()) {
            Object object = this.sipStack.getStackLogger();
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("isServer = ");
            ((StringBuilder)object2).append(this.isServer());
            object.logDebug(((StringBuilder)object2).toString());
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("localTag = ");
            ((StringBuilder)object2).append(this.getLocalTag());
            object.logDebug(((StringBuilder)object2).toString());
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("remoteTag = ");
            ((StringBuilder)object2).append(this.getRemoteTag());
            object.logDebug(((StringBuilder)object2).toString());
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("localSequenceNumer = ");
            ((StringBuilder)object).append(this.getLocalSeqNumber());
            object2.logDebug(((StringBuilder)object).toString());
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("remoteSequenceNumer = ");
            ((StringBuilder)object2).append(this.getRemoteSeqNumber());
            object.logDebug(((StringBuilder)object2).toString());
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ackLine:");
            ((StringBuilder)object2).append(this.getRemoteTag());
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(this.ackLine);
            object.logDebug(((StringBuilder)object2).toString());
        }
    }

    void releaseAckSem() {
        if (this.isBackToBackUserAgent) {
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("releaseAckSem]");
                stringBuilder.append(this);
                stackLogger.logDebug(stringBuilder.toString());
            }
            this.ackSem.release();
        }
    }

    public void releaseTimerTaskSem() {
        this.timerTaskLock.release();
    }

    public void removeEventListener(SIPDialogEventListener sIPDialogEventListener) {
        this.eventListeners.remove(sIPDialogEventListener);
    }

    public void requestConsumed() {
        synchronized (this) {
            this.nextSeqno = this.getRemoteSeqNumber() + 1L;
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Request Consumed -- next consumable Request Seqno = ");
                stringBuilder.append(this.nextSeqno);
                stackLogger.logDebug(stringBuilder.toString());
            }
            return;
        }
    }

    public void resendAck() throws SipException {
        if (this.getLastAckSent() != null) {
            if (this.getLastAckSent().getHeader("Timestamp") != null && this.sipStack.generateTimeStampHeader) {
                TimeStamp timeStamp = new TimeStamp();
                try {
                    timeStamp.setTimeStamp(System.currentTimeMillis());
                    this.getLastAckSent().setHeader(timeStamp);
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    // empty catch block
                }
            }
            this.sendAck(this.getLastAckSent(), false);
        }
    }

    @Override
    public void sendAck(Request request) throws SipException {
        this.sendAck(request, true);
    }

    @Override
    public void sendReliableProvisionalResponse(Response response) throws SipException {
        if (this.isServer()) {
            SIPResponse sIPResponse = (SIPResponse)response;
            if (response.getStatusCode() != 100) {
                if (response.getStatusCode() / 100 <= 2) {
                    if (sIPResponse.getToTag() != null) {
                        Object object = response.getHeaders("Require");
                        boolean bl = false;
                        boolean bl2 = false;
                        if (object != null) {
                            do {
                                bl = bl2;
                                if (!object.hasNext()) break;
                                bl = bl2;
                                if (bl2) break;
                                if (!((RequireHeader)object.next()).getOptionTag().equalsIgnoreCase("100rel")) continue;
                                bl2 = true;
                            } while (true);
                        }
                        if (!bl) {
                            response.addHeader(new Require("100rel"));
                            if (this.sipStack.isLoggingEnabled()) {
                                this.sipStack.getStackLogger().logDebug("Require header with optionTag 100rel is needed -- adding one");
                            }
                        }
                        object = (SIPServerTransaction)this.getFirstTransaction();
                        this.setLastResponse((SIPTransaction)object, sIPResponse);
                        this.setDialogId(sIPResponse.getDialogId(true));
                        ((SIPServerTransaction)object).sendReliableProvisionalResponse(response);
                        this.startRetransmitTimer((SIPServerTransaction)object, response);
                        return;
                    }
                    throw new SipException("Badly formatted response -- To tag mandatory for Reliable Provisional Response");
                }
                throw new SipException("Response code is not a 1xx response - should be in the range 101 to 199 ");
            }
            throw new SipException("Cannot send 100 as a reliable provisional response");
        }
        throw new SipException("Not a Server Dialog");
    }

    @Override
    public void sendRequest(ClientTransaction clientTransaction) throws TransactionDoesNotExistException, SipException {
        this.sendRequest(clientTransaction, this.isBackToBackUserAgent ^ true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendRequest(ClientTransaction object, boolean bl) throws TransactionDoesNotExistException, SipException {
        Object object2;
        Object object3;
        if (!bl && object.getRequest().getMethod().equals("INVITE")) {
            new Thread(new ReInviteSender((ClientTransaction)object)).start();
            return;
        }
        SIPRequest sIPRequest = ((SIPClientTransaction)object).getOriginalRequest();
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("dialog.sendRequest  dialog = ");
            ((StringBuilder)object3).append(this);
            ((StringBuilder)object3).append("\ndialogRequest = \n");
            ((StringBuilder)object3).append(sIPRequest);
            object2.logDebug(((StringBuilder)object3).toString());
        }
        if (object == null) {
            throw new NullPointerException("null parameter");
        }
        if (!sIPRequest.getMethod().equals("ACK") && !sIPRequest.getMethod().equals("CANCEL")) {
            block36 : {
                Object object4;
                if (this.byeSent && this.isTerminatedOnBye() && !sIPRequest.getMethod().equals("BYE")) {
                    if (this.sipStack.isLoggingEnabled()) {
                        object2 = this.sipStack.getStackLogger();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("BYE already sent for ");
                        ((StringBuilder)object).append(this);
                        object2.logError(((StringBuilder)object).toString());
                    }
                    throw new SipException("Cannot send request; BYE already sent");
                }
                if (sIPRequest.getTopmostVia() == null) {
                    sIPRequest.addHeader(((SIPClientTransaction)object).getOutgoingViaHeader());
                }
                if (!this.getCallId().getCallId().equalsIgnoreCase(sIPRequest.getCallId().getCallId())) {
                    if (this.sipStack.isLoggingEnabled()) {
                        object = this.sipStack.getStackLogger();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("CallID ");
                        ((StringBuilder)object2).append(this.getCallId());
                        object.logError(((StringBuilder)object2).toString());
                        object = this.sipStack.getStackLogger();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("RequestCallID = ");
                        ((StringBuilder)object2).append(sIPRequest.getCallId().getCallId());
                        object.logError(((StringBuilder)object2).toString());
                        object2 = this.sipStack.getStackLogger();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("dialog =  ");
                        ((StringBuilder)object).append(this);
                        object2.logError(((StringBuilder)object).toString());
                    }
                    throw new SipException("Bad call ID in request");
                }
                ((SIPClientTransaction)object).setDialog(this, this.dialogId);
                this.addTransaction((SIPTransaction)object);
                ((SIPClientTransaction)object).isMapped = true;
                object2 = (From)sIPRequest.getFrom();
                Object object5 = (To)sIPRequest.getTo();
                if (this.getLocalTag() != null && ((From)object2).getTag() != null && !((From)object2).getTag().equals(this.getLocalTag())) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("From tag mismatch expecting  ");
                    ((StringBuilder)object).append(this.getLocalTag());
                    throw new SipException(((StringBuilder)object).toString());
                }
                if (this.getRemoteTag() != null && ((To)object5).getTag() != null && !((To)object5).getTag().equals(this.getRemoteTag()) && this.sipStack.isLoggingEnabled()) {
                    object4 = this.sipStack.getStackLogger();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("To header tag mismatch expecting ");
                    ((StringBuilder)object3).append(this.getRemoteTag());
                    object4.logWarning(((StringBuilder)object3).toString());
                }
                if (this.getLocalTag() == null && sIPRequest.getMethod().equals("NOTIFY")) {
                    if (!this.getMethod().equals("SUBSCRIBE")) {
                        throw new SipException("Trying to send NOTIFY without SUBSCRIBE Dialog!");
                    }
                    this.setLocalTag(((From)object2).getTag());
                }
                try {
                    if (this.getLocalTag() != null) {
                        ((From)object2).setTag(this.getLocalTag());
                    }
                    if (this.getRemoteTag() != null) {
                        ((To)object5).setTag(this.getRemoteTag());
                    }
                }
                catch (ParseException parseException) {
                    InternalErrorHandler.handleException(parseException);
                }
                object2 = ((SIPClientTransaction)object).getNextHop();
                if (this.sipStack.isLoggingEnabled()) {
                    object4 = this.sipStack.getStackLogger();
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Using hop = ");
                    ((StringBuilder)object3).append(object2.getHost());
                    ((StringBuilder)object3).append(" : ");
                    ((StringBuilder)object3).append(object2.getPort());
                    object4.logDebug(((StringBuilder)object3).toString());
                }
                try {
                    object3 = this.sipStack.createRawMessageChannel(this.getSipProvider().getListeningPoint(object2.getTransport()).getIPAddress(), this.firstTransactionPort, (Hop)object2);
                    object4 = ((SIPClientTransaction)object).getMessageChannel();
                    ((MessageChannel)object4).uncache();
                    if (!this.sipStack.cacheClientConnections) {
                        --((MessageChannel)object4).useCount;
                        if (this.sipStack.isLoggingEnabled()) {
                            object5 = this.sipStack.getStackLogger();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("oldChannel: useCount ");
                            stringBuilder.append(((MessageChannel)object4).useCount);
                            object5.logDebug(stringBuilder.toString());
                        }
                    }
                    if (object3 == null) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("Null message channel using outbound proxy !");
                        }
                        if ((object3 = this.sipStack.getRouter(sIPRequest).getOutboundProxy()) == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("No route found! hop=");
                            ((StringBuilder)object).append(object2);
                            object3 = new SipException(((StringBuilder)object).toString());
                            throw object3;
                        }
                        object2 = this.sipStack.createRawMessageChannel(this.getSipProvider().getListeningPoint(object3.getTransport()).getIPAddress(), this.firstTransactionPort, (Hop)object3);
                        if (object2 != null) {
                            ((SIPClientTransaction)object).setEncapsulatedChannel((MessageChannel)object2);
                        }
                    } else {
                        ((SIPClientTransaction)object).setEncapsulatedChannel((MessageChannel)object3);
                        object2 = object3;
                        if (this.sipStack.isLoggingEnabled()) {
                            object2 = this.sipStack.getStackLogger();
                            object5 = new StringBuilder();
                            ((StringBuilder)object5).append("using message channel ");
                            ((StringBuilder)object5).append(object3);
                            object2.logDebug(((StringBuilder)object5).toString());
                            object2 = object3;
                        }
                    }
                    if (object2 != null) {
                        ++((MessageChannel)object2).useCount;
                    }
                    if (this.sipStack.cacheClientConnections || ((MessageChannel)object4).useCount > 0) break block36;
                    ((MessageChannel)object4).close();
                }
                catch (Exception exception) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logException(exception);
                    }
                    throw new SipException("Could not create message channel", exception);
                }
            }
            try {
                ++this.localSequenceNumber;
                sIPRequest.getCSeq().setSeqNumber(this.getLocalSeqNumber());
            }
            catch (InvalidArgumentException invalidArgumentException) {
                this.sipStack.getStackLogger().logFatalError(invalidArgumentException.getMessage());
            }
            try {
                ((SIPClientTransaction)object).sendMessage(sIPRequest);
                if (sIPRequest.getMethod().equals("BYE")) {
                    this.byeSent = true;
                    if (this.isTerminatedOnBye()) {
                        this.setState(DialogState._TERMINATED);
                    }
                }
                return;
            }
            catch (IOException iOException) {
                throw new SipException("error sending message", iOException);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Bad Request Method. ");
        ((StringBuilder)object).append(sIPRequest.getMethod());
        throw new SipException(((StringBuilder)object).toString());
    }

    @Override
    public void setApplicationData(Object object) {
        this.applicationData = object;
    }

    public void setAssigned() {
        this.isAssigned = true;
    }

    @Override
    public void setBackToBackUserAgent() {
        this.isBackToBackUserAgent = true;
    }

    public void setDialogId(String string) {
        this.dialogId = string;
    }

    void setEarlyDialogId(String string) {
        this.earlyDialogId = string;
    }

    void setEventHeader(EventHeader eventHeader) {
        this.eventHeader = eventHeader;
    }

    public void setLastResponse(SIPTransaction serializable, SIPResponse object) {
        Object object2;
        Object object3;
        this.callIdHeader = ((SIPMessage)object).getCallId();
        int n = ((SIPResponse)object).getStatusCode();
        if (n == 100) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logWarning("Invalid status code - 100 in setLastResponse - ignoring");
            }
            return;
        }
        this.lastResponse = object;
        this.setAssigned();
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("sipDialog: setLastResponse:");
            ((StringBuilder)object3).append(this);
            ((StringBuilder)object3).append(" lastResponse = ");
            ((StringBuilder)object3).append(this.lastResponse.getFirstLine());
            object2.logDebug(((StringBuilder)object3).toString());
        }
        if (this.getState() == DialogState.TERMINATED) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("sipDialog: setLastResponse -- dialog is terminated - ignoring ");
            }
            if (((SIPMessage)object).getCSeq().getMethod().equals("INVITE") && n == 200) {
                this.lastInviteOkReceived = Math.max(((SIPMessage)object).getCSeq().getSeqNumber(), this.lastInviteOkReceived);
            }
            return;
        }
        object2 = ((SIPMessage)object).getCSeq().getMethod();
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logStackTrace();
            object3 = this.sipStack.getStackLogger();
            Object object4 = new StringBuilder();
            ((StringBuilder)object4).append("cseqMethod = ");
            ((StringBuilder)object4).append((String)object2);
            object3.logDebug(((StringBuilder)object4).toString());
            object4 = this.sipStack.getStackLogger();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("dialogState = ");
            ((StringBuilder)object3).append((Object)this.getState());
            object4.logDebug(((StringBuilder)object3).toString());
            object3 = this.sipStack.getStackLogger();
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("method = ");
            ((StringBuilder)object4).append(this.getMethod());
            object3.logDebug(((StringBuilder)object4).toString());
            object3 = this.sipStack.getStackLogger();
            object4 = new StringBuilder();
            ((StringBuilder)object4).append("statusCode = ");
            ((StringBuilder)object4).append(n);
            object3.logDebug(((StringBuilder)object4).toString());
            object4 = this.sipStack.getStackLogger();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("transaction = ");
            ((StringBuilder)object3).append(serializable);
            object4.logDebug(((StringBuilder)object3).toString());
        }
        if (serializable != null && !(serializable instanceof ClientTransaction)) {
            if (((String)object2).equals("BYE") && n / 100 == 2 && this.isTerminatedOnBye()) {
                this.setState(TERMINATED_STATE);
            } else {
                boolean bl;
                boolean bl2 = bl = false;
                if (this.getLocalTag() == null) {
                    bl2 = bl;
                    if (((SIPMessage)object).getTo().getTag() != null) {
                        object3 = this.sipStack;
                        bl2 = bl;
                        if (SIPTransactionStack.isDialogCreated((String)object2)) {
                            bl2 = bl;
                            if (((String)object2).equals(this.getMethod())) {
                                this.setLocalTag(((SIPMessage)object).getTo().getTag());
                                bl2 = true;
                            }
                        }
                    }
                }
                if (n / 100 != 2) {
                    if (n / 100 == 1) {
                        if (bl2) {
                            this.setState(EARLY_STATE);
                            this.setDialogId(((SIPResponse)object).getDialogId(true));
                            this.sipStack.putDialog(this);
                        }
                    } else if (n == 489 && (((String)object2).equals("NOTIFY") || ((String)object2).equals("SUBSCRIBE"))) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("RFC 3265 : Not setting dialog to TERMINATED for 489");
                        }
                    } else if (!this.isReInvite() && this.getState() != DialogState.CONFIRMED) {
                        this.setState(TERMINATED_STATE);
                    }
                } else {
                    if (this.dialogState <= EARLY_STATE && (((String)object2).equals("INVITE") || ((String)object2).equals("SUBSCRIBE") || ((String)object2).equals("REFER"))) {
                        this.setState(CONFIRMED_STATE);
                    }
                    if (bl2) {
                        this.setDialogId(((SIPResponse)object).getDialogId(true));
                        this.sipStack.putDialog(this);
                    }
                    if (((SIPTransaction)serializable).getState() != TransactionState.TERMINATED && ((SIPResponse)object).getStatusCode() == 200 && ((String)object2).equals("INVITE") && this.isBackToBackUserAgent && !this.takeAckSem()) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("Delete dialog -- cannot acquire ackSem");
                        }
                        this.delete();
                        return;
                    }
                }
            }
        } else {
            object3 = this.sipStack;
            if (SIPTransactionStack.isDialogCreated((String)object2)) {
                if (this.getState() == null && n / 100 == 1) {
                    this.setState(EARLY_STATE);
                    if ((((SIPMessage)object).getToTag() != null || this.sipStack.rfc2543Supported) && this.getRemoteTag() == null) {
                        this.setRemoteTag(((SIPMessage)object).getToTag());
                        this.setDialogId(((SIPResponse)object).getDialogId(false));
                        this.sipStack.putDialog(this);
                        this.addRoute((SIPResponse)object);
                    }
                } else if (this.getState() != null && this.getState().equals((Object)DialogState.EARLY) && n / 100 == 1) {
                    if (((String)object2).equals(this.getMethod()) && serializable != null && (((SIPMessage)object).getToTag() != null || this.sipStack.rfc2543Supported)) {
                        this.setRemoteTag(((SIPMessage)object).getToTag());
                        this.setDialogId(((SIPResponse)object).getDialogId(false));
                        this.sipStack.putDialog(this);
                        this.addRoute((SIPResponse)object);
                    }
                } else if (n / 100 == 2) {
                    if (((String)object2).equals(this.getMethod()) && (((SIPMessage)object).getToTag() != null || this.sipStack.rfc2543Supported) && this.getState() != DialogState.CONFIRMED) {
                        this.setRemoteTag(((SIPMessage)object).getToTag());
                        this.setDialogId(((SIPResponse)object).getDialogId(false));
                        this.sipStack.putDialog(this);
                        this.addRoute((SIPResponse)object);
                        this.setState(CONFIRMED_STATE);
                    }
                    if (((String)object2).equals("INVITE")) {
                        this.lastInviteOkReceived = Math.max(((SIPMessage)object).getCSeq().getSeqNumber(), this.lastInviteOkReceived);
                    }
                } else if (n >= 300 && n <= 699 && (this.getState() == null || ((String)object2).equals(this.getMethod()) && this.getState().getValue() == EARLY_STATE)) {
                    this.setState(TERMINATED_STATE);
                }
                if (this.getState() != DialogState.CONFIRMED && this.getState() != DialogState.TERMINATED && (serializable = this.originalRequest) != null && (serializable = ((SIPMessage)serializable).getRecordRouteHeaders()) != null) {
                    object = ((SIPHeaderList)serializable).listIterator(((SIPHeaderList)serializable).size());
                    while (object.hasPrevious()) {
                        object2 = (RecordRoute)object.previous();
                        serializable = (Route)this.routeList.getFirst();
                        if (serializable != null && ((AddressParametersHeader)object2).getAddress().equals(((AddressParametersHeader)serializable).getAddress())) {
                            this.routeList.removeFirst();
                            continue;
                        }
                        break;
                    }
                }
            } else if (((String)object2).equals("NOTIFY") && (this.getMethod().equals("SUBSCRIBE") || this.getMethod().equals("REFER")) && ((SIPResponse)object).getStatusCode() / 100 == 2 && this.getState() == null) {
                this.setDialogId(((SIPResponse)object).getDialogId(true));
                this.sipStack.putDialog(this);
                this.setState(CONFIRMED_STATE);
            } else if (((String)object2).equals("BYE") && n / 100 == 2 && this.isTerminatedOnBye()) {
                this.setState(TERMINATED_STATE);
            }
        }
    }

    void setReInviteFlag(boolean bl) {
        this.reInviteFlag = bl;
    }

    public void setRemoteSequenceNumber(long l) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setRemoteSeqno ");
            stringBuilder.append(this);
            stringBuilder.append("/");
            stringBuilder.append(l);
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.remoteSequenceNumber = l;
    }

    void setRemoteTarget(ContactHeader object) {
        this.remoteTarget = object.getAddress();
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Dialog.setRemoteTarget: ");
            stringBuilder.append(this.remoteTarget);
            object.logDebug(stringBuilder.toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
    }

    public void setResponseTags(SIPResponse sIPResponse) {
        if (this.getLocalTag() == null && this.getRemoteTag() == null) {
            String string = sIPResponse.getFromTag();
            if (string != null) {
                if (string.equals(this.getLocalTag())) {
                    sIPResponse.setToTag(this.getRemoteTag());
                } else if (string.equals(this.getRemoteTag())) {
                    sIPResponse.setToTag(this.getLocalTag());
                }
            } else if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logWarning("No from tag in response! Not RFC 3261 compatible.");
            }
            return;
        }
    }

    protected void setRetransmissionTicks() {
        this.retransmissionTicksLeft = 1;
        this.prevRetransmissionTicks = 1;
    }

    void setRouteList(RouteList routeList) {
        this.routeList = routeList;
    }

    void setServerTransactionFlag(boolean bl) {
        this.serverTransactionFlag = bl;
    }

    public void setSipProvider(SipProviderImpl sipProviderImpl) {
        this.sipProvider = sipProviderImpl;
    }

    void setStack(SIPTransactionStack sIPTransactionStack) {
        this.sipStack = sIPTransactionStack;
    }

    public void setState(int n) {
        if (this.sipStack.isLoggingEnabled()) {
            Object object = this.sipStack.getStackLogger();
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Setting dialog state for ");
            ((StringBuilder)object2).append(this);
            ((StringBuilder)object2).append("newState = ");
            ((StringBuilder)object2).append(n);
            object.logDebug(((StringBuilder)object2).toString());
            this.sipStack.getStackLogger().logStackTrace();
            if (n != -1 && n != this.dialogState && this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append("  old dialog state is ");
                ((StringBuilder)object).append((Object)this.getState());
                object2.logDebug(((StringBuilder)object).toString());
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append("  New dialog state is ");
                ((StringBuilder)object).append((Object)DialogState.getObject(n));
                object2.logDebug(((StringBuilder)object).toString());
            }
        }
        this.dialogState = n;
        if (n == TERMINATED_STATE) {
            if (this.sipStack.getTimer() != null) {
                this.sipStack.getTimer().schedule((TimerTask)new LingerTimer(), 8000L);
            }
            this.stopTimer();
        }
    }

    public void startRetransmitTimer(SIPServerTransaction sIPServerTransaction, Response response) {
        if (sIPServerTransaction.getRequest().getMethod().equals("INVITE") && response.getStatusCode() / 100 == 2) {
            this.startTimer(sIPServerTransaction);
        }
    }

    protected void startTimer(SIPServerTransaction serializable) {
        Object object = this.timerTask;
        if (object != null && ((DialogTimerTask)object).transaction == serializable) {
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Timer already running for ");
                ((StringBuilder)serializable).append(this.getDialogId());
                object.logDebug(((StringBuilder)serializable).toString());
            }
            return;
        }
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Starting dialog timer for ");
            ((StringBuilder)object).append(this.getDialogId());
            stackLogger.logDebug(((StringBuilder)object).toString());
        }
        this.ackSeen = false;
        this.acquireTimerTaskSem();
        try {
            if (this.timerTask != null) {
                this.timerTask.transaction = serializable;
            } else {
                this.timerTask = object = new DialogTimerTask((SIPServerTransaction)serializable);
                this.sipStack.getTimer().schedule((TimerTask)this.timerTask, 500L, 500L);
            }
            this.setRetransmissionTicks();
            return;
        }
        finally {
            this.releaseTimerTaskSem();
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void stopTimer() {
        block5 : {
            this.acquireTimerTaskSem();
            if (this.timerTask == null) break block5;
            this.timerTask.cancel();
            this.timerTask = null;
            {
                catch (Throwable throwable) {
                    this.releaseTimerTaskSem();
                    throw throwable;
                }
            }
        }
        try {
            this.releaseTimerTaskSem();
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    boolean takeAckSem() {
        block7 : {
            StackLogger stackLogger;
            StringBuilder stringBuilder;
            if (this.sipStack.isLoggingEnabled()) {
                stackLogger = this.sipStack.getStackLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("[takeAckSem ");
                stringBuilder.append(this);
                stackLogger.logDebug(stringBuilder.toString());
            }
            try {
                if (this.ackSem.tryAcquire(2L, TimeUnit.SECONDS)) break block7;
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logError("Cannot aquire ACK semaphore");
                }
                if (this.sipStack.isLoggingEnabled()) {
                    stackLogger = this.sipStack.getStackLogger();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Semaphore previously acquired at ");
                    stringBuilder.append(this.stackTrace);
                    stackLogger.logDebug(stringBuilder.toString());
                    this.sipStack.getStackLogger().logStackTrace();
                }
                return false;
            }
            catch (InterruptedException interruptedException) {
                this.sipStack.getStackLogger().logError("Cannot aquire ACK semaphore");
                return false;
            }
        }
        if (this.sipStack.isLoggingEnabled()) {
            this.recordStackTrace();
        }
        return true;
    }

    @Override
    public void terminateOnBye(boolean bl) throws SipException {
        this.terminateOnBye = bl;
    }

    boolean testAndSetIsDialogTerminatedEventDelivered() {
        synchronized (this) {
            boolean bl = this.dialogTerminatedEventDelivered;
            this.dialogTerminatedEventDelivered = true;
            return bl;
        }
    }

    class DialogDeleteIfNoAckSentTask
    extends SIPStackTimerTask
    implements Serializable {
        private long seqno;

        public DialogDeleteIfNoAckSentTask(long l) {
            this.seqno = l;
        }

        @Override
        protected void runTask() {
            if (SIPDialog.this.highestSequenceNumberAcknowledged < this.seqno) {
                SIPDialog.this.dialogDeleteIfNoAckSentTask = null;
                if (!SIPDialog.this.isBackToBackUserAgent) {
                    if (SIPDialog.this.sipStack.isLoggingEnabled()) {
                        SIPDialog.this.sipStack.getStackLogger().logError("ACK Was not sent. killing dialog");
                    }
                    if (SIPDialog.this.sipProvider.getSipListener() instanceof SipListenerExt) {
                        SIPDialog.this.raiseErrorEvent(2);
                    } else {
                        SIPDialog.this.delete();
                    }
                } else {
                    if (SIPDialog.this.sipStack.isLoggingEnabled()) {
                        SIPDialog.this.sipStack.getStackLogger().logError("ACK Was not sent. Sending BYE");
                    }
                    if (SIPDialog.this.sipProvider.getSipListener() instanceof SipListenerExt) {
                        SIPDialog.this.raiseErrorEvent(2);
                    } else {
                        try {
                            Request request = SIPDialog.this.createRequest("BYE");
                            if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
                                request.addHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
                            }
                            Serializable serializable = new Reason();
                            serializable.setProtocol("SIP");
                            serializable.setCause(1025);
                            serializable.setText("Timed out waiting to send ACK");
                            request.addHeader((Header)serializable);
                            serializable = SIPDialog.this.getSipProvider().getNewClientTransaction(request);
                            SIPDialog.this.sendRequest((ClientTransaction)serializable);
                            return;
                        }
                        catch (Exception exception) {
                            SIPDialog.this.delete();
                        }
                    }
                }
            }
        }
    }

    class DialogDeleteTask
    extends SIPStackTimerTask
    implements Serializable {
        DialogDeleteTask() {
        }

        @Override
        protected void runTask() {
            SIPDialog.this.delete();
        }
    }

    class DialogTimerTask
    extends SIPStackTimerTask
    implements Serializable {
        int nRetransmissions;
        SIPServerTransaction transaction;

        public DialogTimerTask(SIPServerTransaction sIPServerTransaction) {
            this.transaction = sIPServerTransaction;
            this.nRetransmissions = 0;
        }

        /*
         * Exception decompiling
         */
        @Override
        protected void runTask() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[UNCONDITIONALDOLOOP]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    class LingerTimer
    extends SIPStackTimerTask
    implements Serializable {
        @Override
        protected void runTask() {
            SIPDialog sIPDialog = SIPDialog.this;
            if (SIPDialog.this.eventListeners != null) {
                SIPDialog.this.eventListeners.clear();
            }
            SIPDialog.this.timerTaskLock = null;
            SIPDialog.this.sipStack.removeDialog(sIPDialog);
        }
    }

    public class ReInviteSender
    implements Runnable,
    Serializable {
        private static final long serialVersionUID = 1019346148741070635L;
        ClientTransaction ctx;

        public ReInviteSender(ClientTransaction clientTransaction) {
            this.ctx = clientTransaction;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void run() {
            Throwable throwable22222;
            block20 : {
                block19 : {
                    block18 : {
                        long l = 0L;
                        long l2 = System.currentTimeMillis();
                        if (!SIPDialog.this.takeAckSem()) {
                            if (SIPDialog.this.sipStack.isLoggingEnabled()) {
                                SIPDialog.this.sipStack.getStackLogger().logError("Could not send re-INVITE time out ClientTransaction");
                            }
                            ((SIPClientTransaction)this.ctx).fireTimeoutTimer();
                            if (SIPDialog.this.sipProvider.getSipListener() != null && SIPDialog.this.sipProvider.getSipListener() instanceof SipListenerExt) {
                                SIPDialog.this.raiseErrorEvent(3);
                            } else {
                                Request request = SIPDialog.this.createRequest("BYE");
                                if (MessageFactoryImpl.getDefaultUserAgentHeader() != null) {
                                    request.addHeader(MessageFactoryImpl.getDefaultUserAgentHeader());
                                }
                                Serializable serializable = new Reason();
                                serializable.setCause(1024);
                                serializable.setText("Timed out waiting to re-INVITE");
                                request.addHeader((Header)serializable);
                                serializable = SIPDialog.this.getSipProvider().getNewClientTransaction(request);
                                SIPDialog.this.sendRequest((ClientTransaction)serializable);
                                this.ctx = null;
                                return;
                            }
                        }
                        if (SIPDialog.this.getState() != DialogState.TERMINATED) {
                            l = System.currentTimeMillis();
                            l -= l2;
                        }
                        if (l == 0L) break block18;
                        try {
                            Thread.sleep(SIPDialog.this.reInviteWaitTime);
                        }
                        catch (InterruptedException interruptedException) {
                            if (SIPDialog.this.sipStack.isLoggingEnabled()) {
                                SIPDialog.this.sipStack.getStackLogger().logDebug("Interrupted sleep");
                            }
                            this.ctx = null;
                            return;
                        }
                    }
                    try {
                        if (SIPDialog.this.getState() != DialogState.TERMINATED) {
                            SIPDialog.this.sendRequest(this.ctx, true);
                        }
                        if (SIPDialog.this.sipStack.isLoggingEnabled()) {
                            SIPDialog.this.sipStack.getStackLogger().logDebug("re-INVITE successfully sent");
                        }
                        break block19;
                    }
                    catch (Throwable throwable22222) {
                        break block20;
                    }
                    catch (Exception exception) {
                        SIPDialog.this.sipStack.getStackLogger().logError("Error sending re-INVITE", exception);
                    }
                }
                this.ctx = null;
                return;
            }
            this.ctx = null;
            throw throwable22222;
        }

        public void terminate() {
            try {
                this.ctx.terminate();
                Thread.currentThread().interrupt();
            }
            catch (ObjectInUseException objectInUseException) {
                SIPDialog.this.sipStack.getStackLogger().logError("unexpected error", objectInUseException);
            }
        }
    }

}

