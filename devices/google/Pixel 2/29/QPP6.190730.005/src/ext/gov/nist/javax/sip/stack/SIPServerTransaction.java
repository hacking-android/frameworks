/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.ServerTransactionExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.header.ContentType;
import gov.nist.javax.sip.header.Expires;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.RSeq;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.HopImpl;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.EventObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.DialogTerminatedEvent;
import javax.sip.ListeningPoint;
import javax.sip.ObjectInUseException;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.Timeout;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionState;
import javax.sip.address.Hop;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ExpiresHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

public class SIPServerTransaction
extends SIPTransaction
implements ServerRequestInterface,
ServerTransaction,
ServerTransactionExt {
    private SIPDialog dialog;
    private SIPServerTransaction inviteTransaction;
    protected boolean isAckSeen;
    private SIPResponse pendingReliableResponse;
    private SIPClientTransaction pendingSubscribeTransaction;
    private Semaphore provisionalResponseSem = new Semaphore(1);
    private ProvisionalResponseTask provisionalResponseTask;
    private transient ServerRequestInterface requestOf;
    private boolean retransmissionAlertEnabled;
    private RetransmissionAlertTimerTask retransmissionAlertTimerTask;
    private int rseqNumber;

    protected SIPServerTransaction(SIPTransactionStack sIPTransactionStack, MessageChannel object) {
        super(sIPTransactionStack, (MessageChannel)object);
        if (sIPTransactionStack.maxListenerResponseTime != -1) {
            sIPTransactionStack.getTimer().schedule((TimerTask)new ListenerExecutionMaxTimer(), sIPTransactionStack.maxListenerResponseTime * 1000);
        }
        this.rseqNumber = (int)(Math.random() * 1000.0);
        if (sIPTransactionStack.isLoggingEnabled()) {
            StackLogger stackLogger = sIPTransactionStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("Creating Server Transaction");
            ((StringBuilder)object).append(this.getBranchId());
            stackLogger.logDebug(((StringBuilder)object).toString());
            sIPTransactionStack.getStackLogger().logStackTrace();
        }
    }

    private void fireReliableResponseRetransmissionTimer() {
        try {
            super.sendMessage(this.pendingReliableResponse);
        }
        catch (IOException iOException) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logException(iOException);
            }
            this.setState(TransactionState.TERMINATED);
            this.raiseErrorEvent(2);
        }
    }

    private TransactionState getRealState() {
        return super.getState();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void sendResponse(SIPResponse serializable) throws IOException {
        try {
            if (this.isReliable()) {
                this.getMessageChannel().sendMessage((SIPMessage)serializable);
                return;
            }
            Object object = ((SIPMessage)serializable).getTopmostVia();
            String string = ((Via)object).getTransport();
            if (string != null) {
                int n;
                Object object2;
                Object object3;
                int n2 = n = ((Via)object).getRPort();
                if (n == -1) {
                    n2 = ((Via)object).getPort();
                }
                n = n2;
                if (n2 == -1) {
                    n = string.equalsIgnoreCase("TLS") ? 5061 : 5060;
                }
                if (((Via)object).getMAddr() != null) {
                    object2 = ((Via)object).getMAddr();
                } else {
                    object2 = object3 = ((ParametersHeader)object).getParameter("received");
                    if (object3 == null) {
                        object2 = ((Via)object).getHost();
                    }
                }
                object = this.sipStack.addressResolver;
                object3 = new HopImpl((String)object2, n, string);
                object2 = object.resolveAddress((Hop)object3);
                object3 = this.getSIPStack().createRawMessageChannel(this.getSipProvider().getListeningPoint(object2.getTransport()).getIPAddress(), this.getPort(), (Hop)object2);
                if (object3 != null) {
                    ((MessageChannel)object3).sendMessage((SIPMessage)serializable);
                    return;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Could not create a message channel for ");
                ((StringBuilder)serializable).append(object2);
                object3 = new IOException(((StringBuilder)serializable).toString());
                throw object3;
            }
            serializable = new IOException("missing transport!");
            throw serializable;
        }
        finally {
            this.startTransactionTimer();
        }
    }

    public boolean ackSeen() {
        return this.isAckSeen;
    }

    public void disableRetransmissionAlerts() {
        Object object = this.retransmissionAlertTimerTask;
        if (object != null && this.retransmissionAlertEnabled) {
            ((TimerTask)object).cancel();
            this.retransmissionAlertEnabled = false;
            object = this.retransmissionAlertTimerTask.dialogId;
            if (object != null) {
                this.sipStack.retransmissionAlertTransactions.remove(object);
            }
            this.retransmissionAlertTimerTask = null;
        }
    }

    @Override
    public void enableRetransmissionAlerts() throws SipException {
        if (this.getDialog() == null) {
            if (this.getMethod().equals("INVITE")) {
                this.retransmissionAlertEnabled = true;
                return;
            }
            throw new SipException("Request Method must be INVITE");
        }
        throw new SipException("Dialog associated with tx");
    }

    public boolean equals(Object object) {
        if (!object.getClass().equals(this.getClass())) {
            return false;
        }
        object = (SIPServerTransaction)object;
        return this.getBranch().equalsIgnoreCase(((SIPTransaction)object).getBranch());
    }

    @Override
    protected void fireRetransmissionTimer() {
        try {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("fireRetransmissionTimer() -- ");
            }
            if (this.isInviteTransaction() && this.lastResponse != null) {
                if (this.retransmissionAlertEnabled && !this.sipStack.isTransactionPendingAck(this)) {
                    SipProviderImpl sipProviderImpl = this.getSipProvider();
                    TimeoutEvent timeoutEvent = new TimeoutEvent((Object)sipProviderImpl, this, Timeout.RETRANSMIT);
                    sipProviderImpl.handleEvent(timeoutEvent, this);
                } else if (this.lastResponse.getStatusCode() / 100 > 2 && !this.isAckSeen) {
                    super.sendMessage(this.lastResponse);
                }
            }
        }
        catch (IOException iOException) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logException(iOException);
            }
            this.raiseErrorEvent(2);
        }
    }

    @Override
    protected void fireTimeoutTimer() {
        Serializable serializable;
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("SIPServerTransaction.fireTimeoutTimer this = ");
            ((StringBuilder)serializable).append(this);
            ((StringBuilder)serializable).append(" current state = ");
            ((StringBuilder)serializable).append((Object)this.getRealState());
            ((StringBuilder)serializable).append(" method = ");
            ((StringBuilder)serializable).append(this.getOriginalRequest().getMethod());
            stackLogger.logDebug(((StringBuilder)serializable).toString());
        }
        if (this.getMethod().equals("INVITE") && this.sipStack.removeTransactionPendingAck(this)) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Found tx pending ACK - returning");
            }
            return;
        }
        serializable = this.dialog;
        this.getSIPStack();
        if (SIPTransactionStack.isDialogCreated(this.getOriginalRequest().getMethod()) && (TransactionState.CALLING == this.getRealState() || TransactionState.TRYING == this.getRealState())) {
            ((SIPDialog)serializable).setState(SIPDialog.TERMINATED_STATE);
        } else if (this.getOriginalRequest().getMethod().equals("BYE") && serializable != null && ((SIPDialog)serializable).isTerminatedOnBye()) {
            ((SIPDialog)serializable).setState(SIPDialog.TERMINATED_STATE);
        }
        if (TransactionState.COMPLETED == this.getRealState() && this.isInviteTransaction()) {
            this.raiseErrorEvent(1);
            this.setState(TransactionState.TERMINATED);
            this.sipStack.removeTransaction(this);
        } else if (TransactionState.COMPLETED == this.getRealState() && !this.isInviteTransaction()) {
            this.setState(TransactionState.TERMINATED);
            this.sipStack.removeTransaction(this);
        } else if (TransactionState.CONFIRMED == this.getRealState() && this.isInviteTransaction()) {
            this.setState(TransactionState.TERMINATED);
            this.sipStack.removeTransaction(this);
        } else if (!(this.isInviteTransaction() || TransactionState.COMPLETED != this.getRealState() && TransactionState.CONFIRMED != this.getRealState())) {
            this.setState(TransactionState.TERMINATED);
        } else if (this.isInviteTransaction() && TransactionState.TERMINATED == this.getRealState()) {
            this.raiseErrorEvent(1);
            if (serializable != null) {
                ((SIPDialog)serializable).setState(SIPDialog.TERMINATED_STATE);
            }
        }
    }

    @Override
    public SIPServerTransaction getCanceledInviteTransaction() {
        return this.inviteTransaction;
    }

    @Override
    public Dialog getDialog() {
        return this.dialog;
    }

    @Override
    public SIPResponse getLastResponse() {
        return this.lastResponse;
    }

    public SIPResponse getReliableProvisionalResponse() {
        return this.pendingReliableResponse;
    }

    public MessageChannel getResponseChannel() {
        return this;
    }

    @Override
    public TransactionState getState() {
        if (this.isInviteTransaction() && TransactionState.TRYING == super.getState()) {
            return TransactionState.PROCEEDING;
        }
        return super.getState();
    }

    @Override
    public String getViaHost() {
        return this.getMessageChannel().getViaHost();
    }

    @Override
    public int getViaPort() {
        return this.getMessageChannel().getViaPort();
    }

    @Override
    public boolean isMessagePartOfTransaction(SIPMessage sIPMessage) {
        boolean bl;
        block12 : {
            boolean bl2;
            Via via;
            block18 : {
                block17 : {
                    boolean bl3;
                    String string;
                    Object object;
                    block16 : {
                        boolean bl4;
                        String string2;
                        String string3;
                        block15 : {
                            block14 : {
                                block13 : {
                                    block11 : {
                                        bl2 = false;
                                        string2 = sIPMessage.getCSeq().getMethod();
                                        if (string2.equals("INVITE")) break block11;
                                        bl = bl2;
                                        if (this.isTerminated()) break block12;
                                    }
                                    object = sIPMessage.getViaHeaders();
                                    bl = bl2;
                                    if (object == null) break block12;
                                    via = (Via)((SIPHeaderList)object).getFirst();
                                    string = via.getBranch();
                                    object = string;
                                    if (string != null) {
                                        object = string;
                                        if (!string.toLowerCase().startsWith("z9hg4bk")) {
                                            object = null;
                                        }
                                    }
                                    boolean bl5 = false;
                                    bl3 = false;
                                    bl = false;
                                    if (object == null || this.getBranch() == null) break block13;
                                    if (string2.equals("CANCEL")) {
                                        if (this.getMethod().equals("CANCEL") && this.getBranch().equalsIgnoreCase((String)object) && via.getSentBy().equals(((Via)this.getOriginalRequest().getViaHeaders().getFirst()).getSentBy())) {
                                            bl = true;
                                        }
                                    } else {
                                        bl = this.getBranch().equalsIgnoreCase((String)object) && via.getSentBy().equals(((Via)this.getOriginalRequest().getViaHeaders().getFirst()).getSentBy()) ? true : bl5;
                                    }
                                    break block12;
                                }
                                string3 = this.fromTag;
                                string2 = sIPMessage.getFrom().getTag();
                                bl4 = string3 == null || string2 == null;
                                object = this.toTag;
                                string = sIPMessage.getTo().getTag();
                                if (object == null || string == null) {
                                    bl3 = true;
                                }
                                bl = sIPMessage instanceof SIPResponse;
                                if (!sIPMessage.getCSeq().getMethod().equalsIgnoreCase("CANCEL") || this.getOriginalRequest().getCSeq().getMethod().equalsIgnoreCase("CANCEL")) break block14;
                                bl = false;
                                break block12;
                            }
                            if (bl) break block15;
                            bl = bl2;
                            if (!this.getOriginalRequest().getRequestURI().equals(((SIPRequest)sIPMessage).getRequestURI())) break block12;
                        }
                        if (bl4) break block16;
                        bl = bl2;
                        if (string3 == null) break block12;
                        bl = bl2;
                        if (!string3.equalsIgnoreCase(string2)) break block12;
                    }
                    if (bl3) break block17;
                    bl = bl2;
                    if (object == null) break block12;
                    bl = bl2;
                    if (!((String)object).equalsIgnoreCase(string)) break block12;
                }
                bl = bl2;
                if (!this.getOriginalRequest().getCallId().getCallId().equalsIgnoreCase(sIPMessage.getCallId().getCallId())) break block12;
                bl = bl2;
                if (this.getOriginalRequest().getCSeq().getSeqNumber() != sIPMessage.getCSeq().getSeqNumber()) break block12;
                if (!sIPMessage.getCSeq().getMethod().equals("CANCEL")) break block18;
                bl = bl2;
                if (!this.getOriginalRequest().getMethod().equals(sIPMessage.getCSeq().getMethod())) break block12;
            }
            bl = bl2;
            if (via.equals(this.getOriginalRequest().getViaHeaders().getFirst())) {
                bl = true;
            }
        }
        return bl;
    }

    public boolean isRetransmissionAlertEnabled() {
        return this.retransmissionAlertEnabled;
    }

    public boolean isTransactionMapped() {
        return this.isMapped;
    }

    protected void map() {
        TransactionState transactionState = this.getRealState();
        if (transactionState == null || transactionState == TransactionState.TRYING) {
            if (this.isInviteTransaction() && !this.isMapped && this.sipStack.getTimer() != null) {
                this.isMapped = true;
                this.sipStack.getTimer().schedule((TimerTask)new SendTrying(), 200L);
            } else {
                this.isMapped = true;
            }
        }
        this.sipStack.removePendingTransaction(this);
    }

    public boolean prackRecieved() {
        if (this.pendingReliableResponse == null) {
            return false;
        }
        ProvisionalResponseTask provisionalResponseTask = this.provisionalResponseTask;
        if (provisionalResponseTask != null) {
            provisionalResponseTask.cancel();
        }
        this.pendingReliableResponse = null;
        this.provisionalResponseSem.release();
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processRequest(SIPRequest object, MessageChannel object2) {
        block32 : {
            Object object3;
            boolean bl = false;
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("processRequest: ");
                ((StringBuilder)object3).append(((SIPRequest)object).getFirstLine());
                object2.logDebug(((StringBuilder)object3).toString());
                object3 = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("tx state = ");
                ((StringBuilder)object2).append((Object)this.getRealState());
                object3.logDebug(((StringBuilder)object2).toString());
            }
            object2 = this.getRealState();
            if (object2 == null) {
                this.setOriginalRequest((SIPRequest)object);
                this.setState(TransactionState.TRYING);
                boolean bl2 = true;
                this.setPassToListener();
                bl = bl2;
                if (this.isInviteTransaction()) {
                    bl = bl2;
                    if (this.isMapped) {
                        this.sendMessage(((SIPRequest)object).createResponse(100, "Trying"));
                        bl = bl2;
                    }
                }
            } else {
                if (this.isInviteTransaction() && TransactionState.COMPLETED == this.getRealState() && ((SIPRequest)object).getMethod().equals("ACK")) {
                    this.setState(TransactionState.CONFIRMED);
                    this.disableRetransmissionTimer();
                    if (!this.isReliable()) {
                        this.enableTimeoutTimer(this.TIMER_I);
                    } else {
                        this.setState(TransactionState.TERMINATED);
                    }
                    if (this.sipStack.isNon2XXAckPassedToListener()) {
                        this.requestOf.processRequest((SIPRequest)object, this);
                        return;
                    }
                    if (this.sipStack.isLoggingEnabled()) {
                        object2 = this.sipStack.getStackLogger();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("ACK received for server Tx ");
                        ((StringBuilder)object).append(this.getTransactionId());
                        ((StringBuilder)object).append(" not delivering to application!");
                        object2.logDebug(((StringBuilder)object).toString());
                    }
                    this.semRelease();
                    return;
                }
                if (((SIPRequest)object).getMethod().equals(this.getOriginalRequest().getMethod())) {
                    if (TransactionState.PROCEEDING != this.getRealState() && TransactionState.COMPLETED != this.getRealState()) {
                        if (((SIPRequest)object).getMethod().equals("ACK")) {
                            if (this.requestOf != null) {
                                this.requestOf.processRequest((SIPRequest)object, this);
                            } else {
                                this.semRelease();
                            }
                        }
                    } else {
                        this.semRelease();
                        if (this.lastResponse != null) {
                            super.sendMessage(this.lastResponse);
                        }
                    }
                    if (!this.sipStack.isLoggingEnabled()) return;
                    object3 = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("completed processing retransmitted request : ");
                    ((StringBuilder)object2).append(((SIPRequest)object).getFirstLine());
                    ((StringBuilder)object2).append(this);
                    ((StringBuilder)object2).append(" txState = ");
                    ((StringBuilder)object2).append((Object)this.getState());
                    ((StringBuilder)object2).append(" lastResponse = ");
                    ((StringBuilder)object2).append(this.getLastResponse());
                    object3.logDebug(((StringBuilder)object2).toString());
                    return;
                }
            }
            if (TransactionState.COMPLETED != this.getRealState() && TransactionState.TERMINATED != this.getRealState() && this.requestOf != null) {
                if (this.getOriginalRequest().getMethod().equals(((SIPRequest)object).getMethod())) {
                    if (bl) {
                        this.requestOf.processRequest((SIPRequest)object, this);
                        return;
                    }
                    this.semRelease();
                    return;
                }
                if (this.requestOf != null) {
                    this.requestOf.processRequest((SIPRequest)object, this);
                    return;
                }
                this.semRelease();
                return;
            }
            this.getSIPStack();
            if (SIPTransactionStack.isDialogCreated(this.getOriginalRequest().getMethod()) && this.getRealState() == TransactionState.TERMINATED && ((SIPRequest)object).getMethod().equals("ACK") && this.requestOf != null) {
                object2 = this.dialog;
                if (object2 != null && ((SIPDialog)object2).ackProcessed) {
                    this.semRelease();
                } else {
                    if (object2 != null) {
                        ((SIPDialog)object2).ackReceived((SIPRequest)object);
                        ((SIPDialog)object2).ackProcessed = true;
                    }
                    this.requestOf.processRequest((SIPRequest)object, this);
                }
                break block32;
            }
            if (!((SIPRequest)object).getMethod().equals("CANCEL")) break block32;
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Too late to cancel Transaction");
            }
            this.semRelease();
            try {
                this.sendMessage(((SIPRequest)object).createResponse(200));
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        try {
            if (!this.sipStack.isLoggingEnabled()) return;
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Dropping request ");
            ((StringBuilder)object2).append((Object)this.getRealState());
            object.logDebug(((StringBuilder)object2).toString());
            return;
        }
        catch (IOException iOException) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("IOException ", iOException);
            }
            this.semRelease();
            this.raiseIOExceptionEvent();
        }
    }

    @Override
    public void releaseSem() {
        SIPClientTransaction sIPClientTransaction = this.pendingSubscribeTransaction;
        if (sIPClientTransaction != null) {
            sIPClientTransaction.releaseSem();
        } else if (this.inviteTransaction != null && this.getMethod().equals("CANCEL")) {
            this.inviteTransaction.releaseSem();
        }
        super.releaseSem();
    }

    public void scheduleAckRemoval() throws IllegalStateException {
        if (this.getMethod() != null && this.getMethod().equals("ACK")) {
            this.startTransactionTimer();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Method is null[");
        boolean bl = this.getMethod() == null;
        stringBuilder.append(bl);
        stringBuilder.append("] or method is not ACK[");
        stringBuilder.append(this.getMethod());
        stringBuilder.append("]");
        throw new IllegalStateException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sendMessage(SIPMessage sIPMessage) throws IOException {
        Object object;
        Object object2;
        sIPMessage = (SIPResponse)sIPMessage;
        int n = ((SIPResponse)sIPMessage).getStatusCode();
        try {
            if (this.getOriginalRequest().getTopmostVia().getBranch() != null) {
                sIPMessage.getTopmostVia().setBranch(this.getBranch());
            } else {
                sIPMessage.getTopmostVia().removeParameter("branch");
            }
            if (!this.getOriginalRequest().getTopmostVia().hasPort()) {
                sIPMessage.getTopmostVia().removePort();
            }
        }
        catch (ParseException parseException) {
            parseException.printStackTrace();
        }
        if (!sIPMessage.getCSeq().getMethod().equals(this.getOriginalRequest().getMethod())) {
            this.sendResponse((SIPResponse)sIPMessage);
            this.startTransactionTimer();
            return;
        }
        if (this.getRealState() == TransactionState.TRYING) {
            if (n / 100 == 1) {
                this.setState(TransactionState.PROCEEDING);
            } else if (200 <= n && n <= 699) {
                if (!this.isInviteTransaction()) {
                    if (!this.isReliable()) {
                        this.setState(TransactionState.COMPLETED);
                        this.enableTimeoutTimer(64);
                    } else {
                        this.setState(TransactionState.TERMINATED);
                    }
                } else if (n / 100 == 2) {
                    this.disableRetransmissionTimer();
                    this.disableTimeoutTimer();
                    this.collectionTime = 64;
                    this.setState(TransactionState.TERMINATED);
                    if (this.dialog != null) {
                        this.dialog.setRetransmissionTicks();
                    }
                } else {
                    this.setState(TransactionState.COMPLETED);
                    if (!this.isReliable()) {
                        this.enableRetransmissionTimer();
                    }
                    this.enableTimeoutTimer(64);
                }
            }
        } else if (this.getRealState() == TransactionState.PROCEEDING) {
            if (this.isInviteTransaction()) {
                if (n / 100 == 2) {
                    this.disableRetransmissionTimer();
                    this.disableTimeoutTimer();
                    this.collectionTime = 64;
                    this.setState(TransactionState.TERMINATED);
                    if (this.dialog != null) {
                        this.dialog.setRetransmissionTicks();
                    }
                } else if (300 <= n && n <= 699) {
                    this.setState(TransactionState.COMPLETED);
                    if (!this.isReliable()) {
                        this.enableRetransmissionTimer();
                    }
                    this.enableTimeoutTimer(64);
                }
            } else if (200 <= n && n <= 699) {
                this.setState(TransactionState.COMPLETED);
                if (!this.isReliable()) {
                    this.disableRetransmissionTimer();
                    this.enableTimeoutTimer(64);
                } else {
                    this.setState(TransactionState.TERMINATED);
                }
            }
        } else {
            object2 = TransactionState.COMPLETED;
            object = this.getRealState();
            if (object2 == object) {
                this.startTransactionTimer();
                return;
            }
        }
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("sendMessage : tx = ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" getState = ");
            ((StringBuilder)object).append((Object)this.getState());
            object2.logDebug(((StringBuilder)object).toString());
        }
        this.lastResponse = sIPMessage;
        this.sendResponse((SIPResponse)sIPMessage);
        return;
    }

    protected void sendReliableProvisionalResponse(Response object) throws SipException {
        if (this.pendingReliableResponse == null) {
            this.pendingReliableResponse = (SIPResponse)object;
            RSeq rSeq = (RSeq)object.getHeader("RSeq");
            if (object.getHeader("RSeq") == null) {
                rSeq = new RSeq();
                object.setHeader(rSeq);
            }
            try {
                ++this.rseqNumber;
                rSeq.setSeqNumber(this.rseqNumber);
                this.lastResponse = (SIPResponse)object;
                if (this.getDialog() != null && !this.provisionalResponseSem.tryAcquire(1L, TimeUnit.SECONDS)) {
                    object = new SipException("Unacknowledged response");
                    throw object;
                }
                this.sendMessage((SIPMessage)object);
                this.provisionalResponseTask = object = new ProvisionalResponseTask();
                this.sipStack.getTimer().schedule((TimerTask)this.provisionalResponseTask, 0L, 500L);
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
            }
            return;
        }
        throw new SipException("Unacknowledged response");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sendResponse(Response serializable) throws SipException {
        block36 : {
            SIPDialog sIPDialog;
            Object object;
            Object object2;
            block37 : {
                object2 = (SIPResponse)serializable;
                sIPDialog = this.dialog;
                if (serializable == null) {
                    throw new NullPointerException("null response");
                }
                try {
                    ((SIPResponse)object2).checkHeaders();
                    if (!((SIPMessage)object2).getCSeq().getMethod().equals(this.getMethod())) break block36;
                    if (!this.getMethod().equals("SUBSCRIBE") || serializable.getStatusCode() / 100 != 2) break block37;
                }
                catch (ParseException parseException) {
                    throw new SipException(parseException.getMessage());
                }
                if (serializable.getHeader("Expires") == null) {
                    throw new SipException("Expires header is mandatory in 2xx response of SUBSCRIBE");
                }
                object = (Expires)this.getOriginalRequest().getExpires();
                Expires expires = (Expires)serializable.getExpires();
                if (object != null && expires.getExpires() > ((Expires)object).getExpires()) {
                    throw new SipException("Response Expires time exceeds request Expires time : See RFC 3265 3.1.1");
                }
            }
            if (((SIPResponse)object2).getStatusCode() == 200 && ((SIPMessage)object2).getCSeq().getMethod().equals("INVITE") && ((SIPMessage)object2).getHeader("Contact") == null) {
                throw new SipException("Contact Header is mandatory for the OK to the INVITE");
            }
            if (!this.isMessagePartOfTransaction((SIPMessage)serializable)) {
                throw new SipException("Response does not belong to this transaction.");
            }
            try {
                boolean bl;
                if (this.pendingReliableResponse != null && this.getDialog() != null && this.getState() != TransactionState.TERMINATED && ((SIPResponse)serializable).getContentTypeHeader() != null && serializable.getStatusCode() / 100 == 2 && ((SIPResponse)serializable).getContentTypeHeader().getContentType().equalsIgnoreCase("application") && (bl = ((SIPResponse)serializable).getContentTypeHeader().getContentSubType().equalsIgnoreCase("sdp"))) {
                    try {
                        if (!this.provisionalResponseSem.tryAcquire(1L, TimeUnit.SECONDS)) {
                            object = new SipException("cannot send response -- unacked povisional");
                            throw object;
                        }
                    }
                    catch (Exception exception) {
                        this.sipStack.getStackLogger().logError("Could not acquire PRACK sem ", exception);
                    }
                } else if (this.pendingReliableResponse != null && ((SIPResponse)object2).isFinalResponse()) {
                    this.provisionalResponseTask.cancel();
                    this.provisionalResponseTask = null;
                }
                if (sIPDialog != null) {
                    if (((SIPResponse)object2).getStatusCode() / 100 == 2) {
                        object = this.sipStack;
                        if (SIPTransactionStack.isDialogCreated(((SIPMessage)object2).getCSeq().getMethod())) {
                            if (sIPDialog.getLocalTag() == null && ((SIPMessage)object2).getTo().getTag() == null) {
                                ((SIPMessage)object2).getTo().setTag(Utils.getInstance().generateTag());
                            } else if (sIPDialog.getLocalTag() != null && ((SIPMessage)object2).getToTag() == null) {
                                ((SIPMessage)object2).setToTag(sIPDialog.getLocalTag());
                            } else if (sIPDialog.getLocalTag() != null && ((SIPMessage)object2).getToTag() != null && !sIPDialog.getLocalTag().equals(((SIPMessage)object2).getToTag())) {
                                serializable = new StringBuilder();
                                ((StringBuilder)serializable).append("Tag mismatch dialogTag is ");
                                ((StringBuilder)serializable).append(sIPDialog.getLocalTag());
                                ((StringBuilder)serializable).append(" responseTag is ");
                                ((StringBuilder)serializable).append(((SIPMessage)object2).getToTag());
                                object = new SipException(((StringBuilder)serializable).toString());
                                throw object;
                            }
                        }
                    }
                    if (!((SIPMessage)object2).getCallId().getCallId().equals(sIPDialog.getCallId().getCallId())) {
                        serializable = new SipException("Dialog mismatch!");
                        throw serializable;
                    }
                }
                if ((object = ((SIPRequest)this.getRequest()).getFrom().getTag()) != null && ((SIPMessage)object2).getFromTag() != null && !((SIPMessage)object2).getFromTag().equals(object)) {
                    serializable = new SipException("From tag of request does not match response from tag");
                    throw serializable;
                }
                if (object != null) {
                    ((SIPMessage)object2).getFrom().setTag((String)object);
                } else if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("WARNING -- Null From tag in request!!");
                }
                if (sIPDialog != null && serializable.getStatusCode() != 100) {
                    sIPDialog.setResponseTags((SIPResponse)object2);
                    object2 = sIPDialog.getState();
                    sIPDialog.setLastResponse(this, (SIPResponse)serializable);
                    if (object2 == null && sIPDialog.getState() == DialogState.TERMINATED) {
                        object2 = new DialogTerminatedEvent(sIPDialog.getSipProvider(), sIPDialog);
                        sIPDialog.getSipProvider().handleEvent((EventObject)object2, this);
                    }
                } else if (sIPDialog == null && this.getMethod().equals("INVITE") && this.retransmissionAlertEnabled && this.retransmissionAlertTimerTask == null && serializable.getStatusCode() / 100 == 2) {
                    object2 = ((SIPResponse)serializable).getDialogId(true);
                    this.retransmissionAlertTimerTask = object = new RetransmissionAlertTimerTask((String)object2);
                    this.sipStack.retransmissionAlertTransactions.put((String)object2, this);
                    this.sipStack.getTimer().schedule((TimerTask)this.retransmissionAlertTimerTask, 0L, 500L);
                }
                this.sendMessage((SIPResponse)serializable);
                if (sIPDialog != null) {
                    sIPDialog.startRetransmitTimer(this, (SIPResponse)serializable);
                }
                return;
            }
            catch (ParseException parseException) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logException(parseException);
                }
                this.setState(TransactionState.TERMINATED);
                throw new SipException(parseException.getMessage());
            }
            catch (IOException iOException) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logException(iOException);
                }
                this.setState(TransactionState.TERMINATED);
                this.raiseErrorEvent(2);
                throw new SipException(iOException.getMessage());
            }
        }
        throw new SipException("CSeq method does not match Request method of request that created the tx.");
    }

    public void setAckSeen() {
        this.isAckSeen = true;
    }

    @Override
    public void setDialog(SIPDialog object, String string) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setDialog ");
            stringBuilder.append(this);
            stringBuilder.append(" dialog = ");
            stringBuilder.append(object);
            stackLogger.logDebug(stringBuilder.toString());
        }
        this.dialog = object;
        if (string != null) {
            this.dialog.setAssigned();
        }
        if (this.retransmissionAlertEnabled && (object = this.retransmissionAlertTimerTask) != null) {
            ((TimerTask)object).cancel();
            if (this.retransmissionAlertTimerTask.dialogId != null) {
                this.sipStack.retransmissionAlertTransactions.remove(this.retransmissionAlertTimerTask.dialogId);
            }
            this.retransmissionAlertTimerTask = null;
        }
        this.retransmissionAlertEnabled = false;
    }

    public void setInviteTransaction(SIPServerTransaction sIPServerTransaction) {
        this.inviteTransaction = sIPServerTransaction;
    }

    public void setMapped(boolean bl) {
        this.isMapped = true;
    }

    @Override
    public void setOriginalRequest(SIPRequest sIPRequest) {
        super.setOriginalRequest(sIPRequest);
    }

    public void setPendingSubscribe(SIPClientTransaction sIPClientTransaction) {
        this.pendingSubscribeTransaction = sIPClientTransaction;
    }

    public void setRequestInterface(ServerRequestInterface serverRequestInterface) {
        this.requestOf = serverRequestInterface;
    }

    @Override
    public void setState(TransactionState transactionState) {
        if (transactionState == TransactionState.TERMINATED && this.isReliable() && !this.getSIPStack().cacheServerConnections) {
            this.collectionTime = 64;
        }
        super.setState(transactionState);
    }

    @Override
    protected void startTransactionTimer() {
        if (this.transactionTimerStarted.compareAndSet(false, true) && this.sipStack.getTimer() != null) {
            TransactionTimer transactionTimer = new TransactionTimer();
            this.sipStack.getTimer().schedule((TimerTask)transactionTimer, this.BASE_TIMER_INTERVAL, (long)this.BASE_TIMER_INTERVAL);
        }
    }

    @Override
    public void terminate() throws ObjectInUseException {
        this.setState(TransactionState.TERMINATED);
        RetransmissionAlertTimerTask retransmissionAlertTimerTask = this.retransmissionAlertTimerTask;
        if (retransmissionAlertTimerTask != null) {
            retransmissionAlertTimerTask.cancel();
            if (this.retransmissionAlertTimerTask.dialogId != null) {
                this.sipStack.retransmissionAlertTransactions.remove(this.retransmissionAlertTimerTask.dialogId);
            }
            this.retransmissionAlertTimerTask = null;
        }
    }

    class ListenerExecutionMaxTimer
    extends SIPStackTimerTask {
        SIPServerTransaction serverTransaction;

        ListenerExecutionMaxTimer() {
            this.serverTransaction = SIPServerTransaction.this;
        }

        @Override
        protected void runTask() {
            try {
                if (this.serverTransaction.getState() == null) {
                    this.serverTransaction.terminate();
                    SIPTransactionStack sIPTransactionStack = this.serverTransaction.getSIPStack();
                    sIPTransactionStack.removePendingTransaction(this.serverTransaction);
                    sIPTransactionStack.removeTransaction(this.serverTransaction);
                }
            }
            catch (Exception exception) {
                SIPServerTransaction.this.sipStack.getStackLogger().logError("unexpected exception", exception);
            }
        }
    }

    class ProvisionalResponseTask
    extends SIPStackTimerTask {
        int ticks;
        int ticksLeft;

        public ProvisionalResponseTask() {
            this.ticksLeft = this.ticks = 1;
        }

        @Override
        protected void runTask() {
            SIPServerTransaction sIPServerTransaction = SIPServerTransaction.this;
            if (sIPServerTransaction.isTerminated()) {
                this.cancel();
            } else {
                --this.ticksLeft;
                if (this.ticksLeft == -1) {
                    int n;
                    sIPServerTransaction.fireReliableResponseRetransmissionTimer();
                    this.ticks = n = (this.ticksLeft = this.ticks * 2);
                    if (n >= 64) {
                        this.cancel();
                        SIPServerTransaction.this.setState(SIPTransaction.TERMINATED_STATE);
                        SIPServerTransaction.this.fireTimeoutTimer();
                    }
                }
            }
        }
    }

    class RetransmissionAlertTimerTask
    extends SIPStackTimerTask {
        String dialogId;
        int ticks;
        int ticksLeft;

        public RetransmissionAlertTimerTask(String string) {
            this.ticksLeft = this.ticks = 1;
        }

        @Override
        protected void runTask() {
            SIPServerTransaction sIPServerTransaction = SIPServerTransaction.this;
            --this.ticksLeft;
            if (this.ticksLeft == -1) {
                sIPServerTransaction.fireRetransmissionTimer();
                this.ticksLeft = this.ticks * 2;
            }
        }
    }

    class SendTrying
    extends SIPStackTimerTask {
        protected SendTrying() {
            if (SIPServerTransaction.this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = SIPServerTransaction.this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("scheduled timer for ");
                stringBuilder.append(SIPServerTransaction.this);
                stackLogger.logDebug(stringBuilder.toString());
            }
        }

        @Override
        protected void runTask() {
            block5 : {
                SIPServerTransaction sIPServerTransaction = SIPServerTransaction.this;
                Object object = sIPServerTransaction.getRealState();
                if (object == null || TransactionState.TRYING == object) {
                    Object object2;
                    if (SIPServerTransaction.this.sipStack.isLoggingEnabled()) {
                        object = SIPServerTransaction.this.sipStack.getStackLogger();
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append(" sending Trying current state = ");
                        ((StringBuilder)object2).append((Object)sIPServerTransaction.getRealState());
                        object.logDebug(((StringBuilder)object2).toString());
                    }
                    try {
                        sIPServerTransaction.sendMessage(sIPServerTransaction.getOriginalRequest().createResponse(100, "Trying"));
                        if (SIPServerTransaction.this.sipStack.isLoggingEnabled()) {
                            object2 = SIPServerTransaction.this.sipStack.getStackLogger();
                            object = new StringBuilder();
                            ((StringBuilder)object).append(" trying sent ");
                            ((StringBuilder)object).append((Object)sIPServerTransaction.getRealState());
                            object2.logDebug(((StringBuilder)object).toString());
                        }
                    }
                    catch (IOException iOException) {
                        if (!SIPServerTransaction.this.sipStack.isLoggingEnabled()) break block5;
                        SIPServerTransaction.this.sipStack.getStackLogger().logError("IO error sending  TRYING");
                    }
                }
            }
        }
    }

    class TransactionTimer
    extends SIPStackTimerTask {
        public TransactionTimer() {
            if (SIPServerTransaction.this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = SIPServerTransaction.this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("TransactionTimer() : ");
                stringBuilder.append(SIPServerTransaction.this.getTransactionId());
                stackLogger.logDebug(stringBuilder.toString());
            }
        }

        @Override
        protected void runTask() {
            if (SIPServerTransaction.this.isTerminated()) {
                block4 : {
                    try {
                        this.cancel();
                    }
                    catch (IllegalStateException illegalStateException) {
                        if (SIPServerTransaction.this.sipStack.isAlive()) break block4;
                        return;
                    }
                }
                SIPTransaction.LingerTimer lingerTimer = new SIPTransaction.LingerTimer();
                SIPServerTransaction.this.sipStack.getTimer().schedule((TimerTask)lingerTimer, 8000L);
            } else {
                SIPServerTransaction.this.fireTimer();
            }
        }
    }

}

