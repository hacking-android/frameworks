/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.InternalErrorHandler;
import gov.nist.core.NameValueList;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.ClientTransactionExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.address.AddressImpl;
import gov.nist.javax.sip.header.AddressParametersHeader;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.ContactList;
import gov.nist.javax.sip.header.ParametersHeader;
import gov.nist.javax.sip.header.RecordRoute;
import gov.nist.javax.sip.header.RecordRouteList;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.header.SIPHeaderList;
import gov.nist.javax.sip.header.TimeStamp;
import gov.nist.javax.sip.header.To;
import gov.nist.javax.sip.header.Via;
import gov.nist.javax.sip.header.ViaList;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPStackTimerTask;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.ServerResponseInterface;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.EventObject;
import java.util.ListIterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.InvalidArgumentException;
import javax.sip.ObjectInUseException;
import javax.sip.SipException;
import javax.sip.Timeout;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionState;
import javax.sip.address.Address;
import javax.sip.address.Hop;
import javax.sip.address.SipURI;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;

public class SIPClientTransaction
extends SIPTransaction
implements ServerResponseInterface,
ClientTransaction,
ClientTransactionExt {
    private int callingStateTimeoutCount;
    private SIPDialog defaultDialog;
    private SIPRequest lastRequest;
    private Hop nextHop;
    private boolean notifyOnRetransmit;
    private transient ServerResponseInterface respondTo;
    private ConcurrentHashMap<String, SIPDialog> sipDialogs;
    private boolean timeoutIfStillInCallingState;
    private String viaHost;
    private int viaPort;

    protected SIPClientTransaction(SIPTransactionStack object, MessageChannel object2) {
        super((SIPTransactionStack)object, (MessageChannel)object2);
        this.setBranch(Utils.getInstance().generateBranchId());
        this.messageProcessor = ((MessageChannel)object2).messageProcessor;
        this.setEncapsulatedChannel((MessageChannel)object2);
        this.notifyOnRetransmit = false;
        this.timeoutIfStillInCallingState = false;
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Creating clientTransaction ");
            ((StringBuilder)object2).append(this);
            object.logDebug(((StringBuilder)object2).toString());
            this.sipStack.getStackLogger().logStackTrace();
        }
        this.sipDialogs = new ConcurrentHashMap();
    }

    private final Request createErrorAck() throws SipException, ParseException {
        Serializable serializable = this.getOriginalRequest();
        if (serializable != null) {
            if (this.getMethod().equals("INVITE")) {
                if (this.lastResponse != null) {
                    if (this.lastResponse.getStatusCode() < 200) {
                        if (this.sipStack.isLoggingEnabled()) {
                            StackLogger stackLogger = this.sipStack.getStackLogger();
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("lastResponse = ");
                            ((StringBuilder)serializable).append(this.lastResponse);
                            stackLogger.logDebug(((StringBuilder)serializable).toString());
                        }
                        throw new SipException("Cannot ACK a provisional response!");
                    }
                    return ((SIPRequest)serializable).createErrorAck((To)this.lastResponse.getTo());
                }
                throw new SipException("bad Transaction state");
            }
            throw new SipException("Can only ACK an INVITE!");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("bad state ");
        ((StringBuilder)serializable).append((Object)this.getState());
        throw new SipException(((StringBuilder)serializable).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void inviteClientTransaction(SIPResponse sIPResponse, MessageChannel object, SIPDialog sIPDialog) throws IOException {
        int n = sIPResponse.getStatusCode();
        if (TransactionState.TERMINATED == this.getState()) {
            int n2;
            n = n2 = 0;
            if (sIPDialog != null) {
                n = n2;
                if (sIPDialog.isAckSeen()) {
                    n = n2;
                    if (sIPDialog.getLastAckSent() != null) {
                        n = n2;
                        if (sIPDialog.getLastAckSent().getCSeq().getSeqNumber() == sIPResponse.getCSeq().getSeqNumber()) {
                            n = n2;
                            if (sIPResponse.getFromTag().equals(sIPDialog.getLastAckSent().getFromTag())) {
                                n = 1;
                            }
                        }
                    }
                }
            }
            if (sIPDialog != null && n != 0 && sIPResponse.getCSeq().getMethod().equals(sIPDialog.getMethod())) {
                try {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("resending ACK");
                    }
                    sIPDialog.resendAck();
                }
                catch (SipException sipException) {
                    // empty catch block
                }
            }
            this.semRelease();
            return;
        }
        if (TransactionState.CALLING == this.getState()) {
            if (n / 100 == 2) {
                this.disableRetransmissionTimer();
                this.disableTimeoutTimer();
                this.setState(TransactionState.TERMINATED);
                object = this.respondTo;
                if (object != null) {
                    object.processResponse(sIPResponse, this, sIPDialog);
                    return;
                }
                this.semRelease();
                return;
            }
            if (n / 100 == 1) {
                this.disableRetransmissionTimer();
                this.disableTimeoutTimer();
                this.setState(TransactionState.PROCEEDING);
                object = this.respondTo;
                if (object != null) {
                    object.processResponse(sIPResponse, this, sIPDialog);
                    return;
                }
                this.semRelease();
                return;
            }
            if (300 > n) return;
            if (n > 699) return;
            try {
                this.sendMessage((SIPRequest)this.createErrorAck());
            }
            catch (Exception exception) {
                this.sipStack.getStackLogger().logError("Unexpected Exception sending ACK -- sending error AcK ", exception);
            }
            object = this.respondTo;
            if (object != null) {
                object.processResponse(sIPResponse, this, sIPDialog);
            } else {
                this.semRelease();
            }
            if (this.getDialog() != null && ((SIPDialog)this.getDialog()).isBackToBackUserAgent()) {
                ((SIPDialog)this.getDialog()).releaseAckSem();
            }
            if (!this.isReliable()) {
                this.setState(TransactionState.COMPLETED);
                this.enableTimeoutTimer(this.TIMER_D);
                return;
            }
            this.setState(TransactionState.TERMINATED);
            return;
        }
        if (TransactionState.PROCEEDING == this.getState()) {
            if (n / 100 == 1) {
                object = this.respondTo;
                if (object != null) {
                    object.processResponse(sIPResponse, this, sIPDialog);
                    return;
                }
                this.semRelease();
                return;
            }
            if (n / 100 == 2) {
                this.setState(TransactionState.TERMINATED);
                object = this.respondTo;
                if (object != null) {
                    object.processResponse(sIPResponse, this, sIPDialog);
                    return;
                }
                this.semRelease();
                return;
            }
            if (300 > n) return;
            if (n > 699) return;
            try {
                this.sendMessage((SIPRequest)this.createErrorAck());
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
            }
            if (this.getDialog() != null) {
                ((SIPDialog)this.getDialog()).releaseAckSem();
            }
            if (!this.isReliable()) {
                this.setState(TransactionState.COMPLETED);
                this.enableTimeoutTimer(this.TIMER_D);
            } else {
                this.setState(TransactionState.TERMINATED);
            }
            object = this.respondTo;
            if (object != null) {
                object.processResponse(sIPResponse, this, sIPDialog);
                return;
            }
            this.semRelease();
            return;
        }
        if (TransactionState.COMPLETED != this.getState()) return;
        if (300 > n) return;
        if (n > 699) return;
        try {
            try {
                this.sendMessage((SIPRequest)this.createErrorAck());
            }
            catch (Exception exception) {
                InternalErrorHandler.handleException(exception);
            }
        }
        catch (Throwable throwable2) {}
        this.semRelease();
        return;
        this.semRelease();
        throw throwable2;
    }

    private void nonInviteClientTransaction(SIPResponse object, MessageChannel object2, SIPDialog sIPDialog) throws IOException {
        int n = ((SIPResponse)object).getStatusCode();
        if (TransactionState.TRYING == this.getState()) {
            if (n / 100 == 1) {
                this.setState(TransactionState.PROCEEDING);
                this.enableRetransmissionTimer(8);
                this.enableTimeoutTimer(64);
                object2 = this.respondTo;
                if (object2 != null) {
                    object2.processResponse((SIPResponse)object, this, sIPDialog);
                } else {
                    this.semRelease();
                }
            } else if (200 <= n && n <= 699) {
                object2 = this.respondTo;
                if (object2 != null) {
                    object2.processResponse((SIPResponse)object, this, sIPDialog);
                } else {
                    this.semRelease();
                }
                if (!this.isReliable()) {
                    this.setState(TransactionState.COMPLETED);
                    this.enableTimeoutTimer(this.TIMER_K);
                } else {
                    this.setState(TransactionState.TERMINATED);
                }
            }
        } else if (TransactionState.PROCEEDING == this.getState()) {
            if (n / 100 == 1) {
                object2 = this.respondTo;
                if (object2 != null) {
                    object2.processResponse((SIPResponse)object, this, sIPDialog);
                } else {
                    this.semRelease();
                }
            } else if (200 <= n && n <= 699) {
                object2 = this.respondTo;
                if (object2 != null) {
                    object2.processResponse((SIPResponse)object, this, sIPDialog);
                } else {
                    this.semRelease();
                }
                this.disableRetransmissionTimer();
                this.disableTimeoutTimer();
                if (!this.isReliable()) {
                    this.setState(TransactionState.COMPLETED);
                    this.enableTimeoutTimer(this.TIMER_K);
                } else {
                    this.setState(TransactionState.TERMINATED);
                }
            }
        } else {
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(" Not sending response to TU! ");
                ((StringBuilder)object2).append((Object)this.getState());
                object.logDebug(((StringBuilder)object2).toString());
            }
            this.semRelease();
        }
    }

    @Override
    public void alertIfStillInCallingStateBy(int n) {
        this.timeoutIfStillInCallingState = true;
        this.callingStateTimeoutCount = n;
    }

    public boolean checkFromTag(SIPResponse sIPResponse) {
        String string = ((SIPRequest)this.getRequest()).getFromTag();
        if (this.defaultDialog != null) {
            boolean bl;
            boolean bl2 = string == null;
            if (bl2 ^ (bl = sIPResponse.getFrom().getTag() == null)) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("From tag mismatch -- dropping response");
                }
                return false;
            }
            if (string != null && !string.equalsIgnoreCase(sIPResponse.getFrom().getTag())) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("From tag mismatch -- dropping response");
                }
                return false;
            }
        }
        return true;
    }

    public void clearState() {
    }

    @Override
    public Request createAck() throws SipException {
        Object object = this.getOriginalRequest();
        if (object != null) {
            if (!this.getMethod().equalsIgnoreCase("ACK")) {
                if (this.lastResponse != null) {
                    if (this.lastResponse.getStatusCode() < 200) {
                        if (this.sipStack.isLoggingEnabled()) {
                            object = this.sipStack.getStackLogger();
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("lastResponse = ");
                            stringBuilder.append(this.lastResponse);
                            object.logDebug(stringBuilder.toString());
                        }
                        throw new SipException("Cannot ACK a provisional response!");
                    }
                    SIPRequest sIPRequest = ((SIPRequest)object).createAckRequest((To)this.lastResponse.getTo());
                    object = this.lastResponse.getRecordRouteHeaders();
                    if (object == null) {
                        if (this.lastResponse.getContactHeaders() != null && this.lastResponse.getStatusCode() / 100 != 3) {
                            sIPRequest.setRequestURI((URI)((Contact)this.lastResponse.getContactHeaders().getFirst()).getAddress().getURI().clone());
                        }
                        return sIPRequest;
                    }
                    sIPRequest.removeHeader("Route");
                    RouteList routeList = new RouteList();
                    Object object2 = ((SIPHeaderList)object).listIterator(((SIPHeaderList)object).size());
                    while (object2.hasPrevious()) {
                        object = (RecordRoute)object2.previous();
                        Route route = new Route();
                        route.setAddress((AddressImpl)((AddressImpl)((AddressParametersHeader)object).getAddress()).clone());
                        route.setParameters((NameValueList)((ParametersHeader)object).getParameters().clone());
                        routeList.add(route);
                    }
                    object = null;
                    if (this.lastResponse.getContactHeaders() != null) {
                        object = (Contact)this.lastResponse.getContactHeaders().getFirst();
                    }
                    if (!((SipURI)((Route)routeList.getFirst()).getAddress().getURI()).hasLrParam()) {
                        object2 = null;
                        if (object != null) {
                            object2 = new Route();
                            ((AddressParametersHeader)object2).setAddress((AddressImpl)((AddressImpl)((Contact)object).getAddress()).clone());
                        }
                        object = (Route)routeList.getFirst();
                        routeList.removeFirst();
                        sIPRequest.setRequestURI(((AddressParametersHeader)object).getAddress().getURI());
                        if (object2 != null) {
                            routeList.add(object2);
                        }
                        sIPRequest.addHeader(routeList);
                    } else if (object != null) {
                        sIPRequest.setRequestURI((URI)((Contact)object).getAddress().getURI().clone());
                        sIPRequest.addHeader(routeList);
                    }
                    return sIPRequest;
                }
                throw new SipException("bad Transaction state");
            }
            throw new SipException("Cannot ACK an ACK!");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("bad state ");
        ((StringBuilder)object).append((Object)this.getState());
        throw new SipException(((StringBuilder)object).toString());
    }

    @Override
    public Request createCancel() throws SipException {
        Serializable serializable = this.getOriginalRequest();
        if (serializable != null) {
            if (((SIPRequest)serializable).getMethod().equals("INVITE")) {
                if (!((SIPRequest)serializable).getMethod().equalsIgnoreCase("ACK")) {
                    serializable = ((SIPRequest)serializable).createCancelRequest();
                    ((SIPRequest)serializable).setInviteTransaction(this);
                    return serializable;
                }
                throw new SipException("Cannot Cancel ACK!");
            }
            throw new SipException("Only INIVTE may be cancelled");
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Bad state ");
        ((StringBuilder)serializable).append((Object)this.getState());
        throw new SipException(((StringBuilder)serializable).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void fireRetransmissionTimer() {
        try {
            if (this.getState() == null) return;
            if (!this.isMapped) {
                return;
            }
            boolean bl = this.isInviteTransaction();
            Object object = this.getState();
            if (!bl || TransactionState.CALLING != object) {
                if (bl) return;
                if (TransactionState.TRYING != object) {
                    if (TransactionState.PROCEEDING != object) return;
                }
            }
            if (this.lastRequest == null) return;
            if (this.sipStack.generateTimeStampHeader && this.lastRequest.getHeader("Timestamp") != null) {
                long l = System.currentTimeMillis();
                object = new TimeStamp();
                float f = l;
                try {
                    ((TimeStamp)object).setTimeStamp(f);
                }
                catch (InvalidArgumentException invalidArgumentException) {
                    InternalErrorHandler.handleException(invalidArgumentException);
                }
                this.lastRequest.setHeader((Header)object);
            }
            super.sendMessage(this.lastRequest);
            if (this.notifyOnRetransmit) {
                object = new TimeoutEvent((Object)this.getSipProvider(), this, Timeout.RETRANSMIT);
                this.getSipProvider().handleEvent((EventObject)object, this);
            }
            if (!this.timeoutIfStillInCallingState) return;
            if (this.getState() != TransactionState.CALLING) return;
            --this.callingStateTimeoutCount;
            if (this.callingStateTimeoutCount != 0) return;
            object = new TimeoutEvent((Object)this.getSipProvider(), this, Timeout.RETRANSMIT);
            this.getSipProvider().handleEvent((EventObject)object, this);
            this.timeoutIfStillInCallingState = false;
            return;
        }
        catch (IOException iOException) {
            this.raiseIOExceptionEvent();
            this.raiseErrorEvent(2);
        }
    }

    @Override
    protected void fireTimeoutTimer() {
        Serializable serializable;
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("fireTimeoutTimer ");
            ((StringBuilder)serializable).append(this);
            stackLogger.logDebug(((StringBuilder)serializable).toString());
        }
        serializable = (SIPDialog)this.getDialog();
        if (TransactionState.CALLING == this.getState() || TransactionState.TRYING == this.getState() || TransactionState.PROCEEDING == this.getState()) {
            if (serializable != null && (((SIPDialog)serializable).getState() == null || ((SIPDialog)serializable).getState() == DialogState.EARLY)) {
                this.getSIPStack();
                if (SIPTransactionStack.isDialogCreated(this.getOriginalRequest().getMethod())) {
                    ((SIPDialog)serializable).delete();
                }
            } else if (serializable != null && this.getOriginalRequest().getMethod().equalsIgnoreCase("BYE") && ((SIPDialog)serializable).isTerminatedOnBye()) {
                ((SIPDialog)serializable).delete();
            }
        }
        if (TransactionState.COMPLETED != this.getState()) {
            this.raiseErrorEvent(1);
            if (this.getOriginalRequest().getMethod().equalsIgnoreCase("CANCEL") && (serializable = (SIPClientTransaction)this.getOriginalRequest().getInviteTransaction()) != null && (((SIPTransaction)serializable).getState() == TransactionState.CALLING || ((SIPTransaction)serializable).getState() == TransactionState.PROCEEDING) && ((SIPClientTransaction)serializable).getDialog() != null) {
                ((SIPClientTransaction)serializable).setState(TransactionState.TERMINATED);
            }
        } else {
            this.setState(TransactionState.TERMINATED);
        }
    }

    public SIPDialog getDefaultDialog() {
        return this.defaultDialog;
    }

    public SIPDialog getDialog(String string) {
        return this.sipDialogs.get(string);
    }

    @Override
    public Dialog getDialog() {
        SIPDialog sIPDialog = null;
        Serializable serializable = sIPDialog;
        if (this.lastResponse != null) {
            serializable = sIPDialog;
            if (this.lastResponse.getFromTag() != null) {
                serializable = sIPDialog;
                if (this.lastResponse.getToTag() != null) {
                    serializable = sIPDialog;
                    if (this.lastResponse.getStatusCode() != 100) {
                        serializable = this.getDialog(this.lastResponse.getDialogId(false));
                    }
                }
            }
        }
        sIPDialog = serializable;
        if (serializable == null) {
            sIPDialog = this.defaultDialog;
        }
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append(" sipDialogs =  ");
            ((StringBuilder)serializable).append(this.sipDialogs);
            ((StringBuilder)serializable).append(" default dialog ");
            ((StringBuilder)serializable).append(this.defaultDialog);
            ((StringBuilder)serializable).append(" retval ");
            ((StringBuilder)serializable).append(sIPDialog);
            stackLogger.logDebug(((StringBuilder)serializable).toString());
        }
        return sIPDialog;
    }

    @Override
    public Hop getNextHop() {
        return this.nextHop;
    }

    public Via getOutgoingViaHeader() {
        return this.getMessageProcessor().getViaHeader();
    }

    public MessageChannel getRequestChannel() {
        return this;
    }

    @Override
    public String getViaHost() {
        return this.viaHost;
    }

    @Override
    public int getViaPort() {
        return this.viaPort;
    }

    @Override
    public boolean isMessagePartOfTransaction(SIPMessage sIPMessage) {
        ViaList viaList = sIPMessage.getViaHeaders();
        String string = ((Via)viaList.getFirst()).getBranch();
        String string2 = this.getBranch();
        boolean bl = true;
        boolean bl2 = string2 != null && string != null && this.getBranch().toLowerCase().startsWith("z9hg4bk") && string.toLowerCase().startsWith("z9hg4bk");
        boolean bl3 = false;
        if (TransactionState.COMPLETED == this.getState()) {
            if (bl2) {
                if (!this.getBranch().equalsIgnoreCase(((Via)viaList.getFirst()).getBranch()) || !this.getMethod().equals(sIPMessage.getCSeq().getMethod())) {
                    bl = false;
                }
            } else {
                bl = this.getBranch().equals(sIPMessage.getTransactionId());
            }
        } else {
            bl = bl3;
            if (!this.isTerminated()) {
                if (bl2) {
                    bl = bl3;
                    if (this.getBranch().equalsIgnoreCase(((Via)viaList.getFirst()).getBranch())) {
                        bl = this.getOriginalRequest().getCSeq().getMethod().equals(sIPMessage.getCSeq().getMethod());
                    }
                } else {
                    bl = this.getBranch() != null ? this.getBranch().equalsIgnoreCase(sIPMessage.getTransactionId()) : this.getOriginalRequest().getTransactionId().equalsIgnoreCase(sIPMessage.getTransactionId());
                }
            }
        }
        return bl;
    }

    public boolean isNotifyOnRetransmit() {
        return this.notifyOnRetransmit;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void processResponse(SIPResponse var1_1, MessageChannel var2_2) {
        block18 : {
            block17 : {
                var3_3 = null;
                var4_4 = var1_1.getCSeq().getMethod();
                var5_5 = var1_1.getDialogId(false);
                if (var4_4.equals("CANCEL") && (var6_6 = this.lastRequest) != null) {
                    if ((var6_6 = (SIPClientTransaction)var6_6.getInviteTransaction()) != null) {
                        var3_3 = var6_6.defaultDialog;
                    }
                } else {
                    var3_3 = this.getDialog((String)var5_5);
                }
                if (var3_3 != null) break block17;
                var7_7 = var1_1.getStatusCode();
                if (var7_7 <= 100 || var7_7 >= 300 || var1_1.getToTag() == null && !this.sipStack.isRfc2543Supported()) ** GOTO lbl-1000
                var6_6 = this.sipStack;
                if (SIPTransactionStack.isDialogCreated((String)var4_4)) {
                    // MONITORENTER : this
                    if (this.defaultDialog != null) {
                        if (var1_1.getFromTag() == null) {
                            var1_1 = new RuntimeException("Response without from-tag");
                            throw var1_1;
                        }
                        var6_6 = this.defaultDialog.getLastResponse();
                        var3_3 = this.defaultDialog.getDialogId();
                        if (!(var6_6 == null || var4_4.equals("SUBSCRIBE") && var6_6.getCSeq().getMethod().equals("NOTIFY") && var3_3.equals(var5_5))) {
                            var3_3 = var5_5 = this.sipStack.getDialog((String)var5_5);
                            if (var5_5 == null) {
                                var3_3 = var5_5;
                                if (this.defaultDialog.isAssigned()) {
                                    var3_3 = this.sipStack.createDialog(this, (SIPResponse)var1_1);
                                }
                            }
                        } else {
                            this.defaultDialog.setLastResponse(this, (SIPResponse)var1_1);
                            var3_3 = this.defaultDialog;
                        }
                        if (var3_3 != null) {
                            this.setDialog((SIPDialog)var3_3, var3_3.getDialogId());
                        } else {
                            var5_5 = this.sipStack.getStackLogger();
                            var4_4 = new NullPointerException();
                            var5_5.logError("dialog is unexpectedly null", (Exception)var4_4);
                        }
                    } else if (this.sipStack.isAutomaticDialogSupportEnabled) {
                        var3_3 = this.sipStack.createDialog(this, (SIPResponse)var1_1);
                        this.setDialog((SIPDialog)var3_3, var3_3.getDialogId());
                    }
                    // MONITOREXIT : this
                } else lbl-1000: // 2 sources:
                {
                    var3_3 = this.defaultDialog;
                }
                break block18;
            }
            var3_3.setLastResponse(this, (SIPResponse)var1_1);
        }
        this.processResponse((SIPResponse)var1_1, var2_2, (SIPDialog)var3_3);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void processResponse(SIPResponse sIPResponse, MessageChannel messageChannel, SIPDialog sIPDialog) {
        synchronized (this) {
            void var3_4;
            int n;
            Object object = this.getState();
            if (object == null) {
                return;
            }
            if ((TransactionState.COMPLETED == this.getState() || TransactionState.TERMINATED == this.getState()) && (n = sIPResponse.getStatusCode() / 100) == 1) {
                return;
            }
            if (this.sipStack.isLoggingEnabled()) {
                object = this.sipStack.getStackLogger();
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("processing ");
                ((StringBuilder)object2).append(sIPResponse.getFirstLine());
                ((StringBuilder)object2).append("current state = ");
                ((StringBuilder)object2).append((Object)this.getState());
                object.logDebug(((StringBuilder)object2).toString());
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("dialog = ");
                ((StringBuilder)object).append(var3_4);
                object2.logDebug(((StringBuilder)object).toString());
            }
            this.lastResponse = sIPResponse;
            try {
                void var2_3;
                if (this.isInviteTransaction()) {
                    this.inviteClientTransaction(sIPResponse, (MessageChannel)var2_3, (SIPDialog)var3_4);
                } else {
                    this.nonInviteClientTransaction(sIPResponse, (MessageChannel)var2_3, (SIPDialog)var3_4);
                }
            }
            catch (IOException iOException) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logException(iOException);
                }
                this.setState(TransactionState.TERMINATED);
                this.raiseErrorEvent(2);
            }
            return;
        }
    }

    @Override
    public void sendMessage(SIPMessage object) throws IOException {
        try {
            SIPRequest sIPRequest = (SIPRequest)object;
            Object object2 = (Via)sIPRequest.getViaHeaders().getFirst();
            try {
                ((Via)object2).setBranch(this.getBranch());
            }
            catch (ParseException parseException) {
                // empty catch block
            }
            if (this.sipStack.isLoggingEnabled()) {
                object2 = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Sending Message ");
                stringBuilder.append(object);
                object2.logDebug(stringBuilder.toString());
                object2 = this.sipStack.getStackLogger();
                object = new StringBuilder();
                ((StringBuilder)object).append("TransactionState ");
                ((StringBuilder)object).append((Object)this.getState());
                object2.logDebug(((StringBuilder)object).toString());
            }
            if (((object2 = TransactionState.PROCEEDING) == (object = this.getState()) || TransactionState.CALLING == this.getState()) && sIPRequest.getMethod().equals("ACK")) {
                if (this.isReliable()) {
                    this.setState(TransactionState.TERMINATED);
                } else {
                    this.setState(TransactionState.COMPLETED);
                }
                super.sendMessage(sIPRequest);
                return;
            }
            this.lastRequest = sIPRequest;
            if (this.getState() == null) {
                this.setOriginalRequest(sIPRequest);
                if (sIPRequest.getMethod().equals("INVITE")) {
                    this.setState(TransactionState.CALLING);
                } else if (sIPRequest.getMethod().equals("ACK")) {
                    this.setState(TransactionState.TERMINATED);
                } else {
                    this.setState(TransactionState.TRYING);
                }
                if (!this.isReliable()) {
                    this.enableRetransmissionTimer();
                }
                if (this.isInviteTransaction()) {
                    this.enableTimeoutTimer(64);
                } else {
                    this.enableTimeoutTimer(64);
                }
            }
            super.sendMessage(sIPRequest);
            return;
        }
        finally {
            this.isMapped = true;
            this.startTransactionTimer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void sendRequest() throws SipException {
        Serializable serializable;
        SIPRequest sIPRequest = this.getOriginalRequest();
        if (this.getState() != null) {
            throw new SipException("Request already sent");
        }
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("sendRequest() ");
            ((StringBuilder)serializable).append(sIPRequest);
            stackLogger.logDebug(((StringBuilder)serializable).toString());
        }
        try {
            sIPRequest.checkHeaders();
        }
        catch (ParseException parseException) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("missing required header");
            }
            throw new SipException(parseException.getMessage());
        }
        if (this.getMethod().equals("SUBSCRIBE") && sIPRequest.getHeader("Expires") == null && this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logWarning("Expires header missing in outgoing subscribe -- Notifier will assume implied value on event package");
        }
        try {
            boolean bl = this.getOriginalRequest().getMethod().equals("CANCEL");
            if (bl && this.sipStack.isCancelClientTransactionChecked()) {
                serializable = (SIPClientTransaction)this.sipStack.findCancelTransaction(this.getOriginalRequest(), false);
                if (serializable == null) {
                    SipException sipException = new SipException("Could not find original tx to cancel. RFC 3261 9.1");
                    throw sipException;
                }
                if (((SIPTransaction)serializable).getState() == null) {
                    SipException sipException = new SipException("State is null no provisional response yet -- cannot cancel RFC 3261 9.1");
                    throw sipException;
                }
                if (!((SIPTransaction)serializable).getMethod().equals("INVITE")) {
                    SipException sipException = new SipException("Cannot cancel non-invite requests RFC 3261 9.1");
                    throw sipException;
                }
            } else if (this.getOriginalRequest().getMethod().equals("BYE") || this.getOriginalRequest().getMethod().equals("NOTIFY")) {
                serializable = this.sipStack.getDialog(this.getOriginalRequest().getDialogId(false));
                if (this.getSipProvider().isAutomaticDialogSupportEnabled() && serializable != null) {
                    SipException sipException = new SipException("Dialog is present and AutomaticDialogSupport is enabled for  the provider -- Send the Request using the Dialog.sendRequest(transaction)");
                    throw sipException;
                }
            }
            if (this.getMethod().equals("INVITE") && (serializable = this.getDefaultDialog()) != null && ((SIPDialog)serializable).isBackToBackUserAgent() && !((SIPDialog)serializable).takeAckSem()) {
                SipException sipException = new SipException("Failed to take ACK semaphore");
                throw sipException;
            }
            this.isMapped = true;
            this.sendMessage(sIPRequest);
            return;
        }
        catch (IOException iOException) {
            this.setState(TransactionState.TERMINATED);
            throw new SipException("IO Error sending request", iOException);
        }
    }

    @Override
    public void setDialog(SIPDialog sIPDialog, String string) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setDialog: ");
            stringBuilder.append(string);
            stringBuilder.append("sipDialog = ");
            stringBuilder.append(sIPDialog);
            stackLogger.logDebug(stringBuilder.toString());
        }
        if (sIPDialog == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("NULL DIALOG!!");
            }
            throw new NullPointerException("bad dialog null");
        }
        if (this.defaultDialog == null) {
            this.defaultDialog = sIPDialog;
            if (this.getMethod().equals("INVITE") && this.getSIPStack().maxForkTime != 0) {
                this.getSIPStack().addForkedClientTransaction(this);
            }
        }
        if (string != null && sIPDialog.getDialogId() != null) {
            this.sipDialogs.put(string, sIPDialog);
        }
    }

    public void setNextHop(Hop hop) {
        this.nextHop = hop;
    }

    @Override
    public void setNotifyOnRetransmit(boolean bl) {
        this.notifyOnRetransmit = bl;
    }

    public void setResponseInterface(ServerResponseInterface serverResponseInterface) {
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Setting response interface for ");
            stringBuilder.append(this);
            stringBuilder.append(" to ");
            stringBuilder.append(serverResponseInterface);
            stackLogger.logDebug(stringBuilder.toString());
            if (serverResponseInterface == null) {
                this.sipStack.getStackLogger().logStackTrace();
                this.sipStack.getStackLogger().logDebug("WARNING -- setting to null!");
            }
        }
        this.respondTo = serverResponseInterface;
    }

    @Override
    public void setState(TransactionState transactionState) {
        if (transactionState == TransactionState.TERMINATED && this.isReliable() && !this.getSIPStack().cacheClientConnections) {
            this.collectionTime = 64;
        }
        if (super.getState() != TransactionState.COMPLETED && (transactionState == TransactionState.COMPLETED || transactionState == TransactionState.TERMINATED)) {
            this.sipStack.decrementActiveClientTransactionCount();
        }
        super.setState(transactionState);
    }

    protected void setViaHost(String string) {
        this.viaHost = string;
    }

    protected void setViaPort(int n) {
        this.viaPort = n;
    }

    @Override
    protected void startTransactionTimer() {
        if (this.transactionTimerStarted.compareAndSet(false, true)) {
            TransactionTimer transactionTimer = new TransactionTimer();
            if (this.sipStack.getTimer() != null) {
                this.sipStack.getTimer().schedule((TimerTask)transactionTimer, this.BASE_TIMER_INTERVAL, (long)this.BASE_TIMER_INTERVAL);
            }
        }
    }

    @Override
    public void terminate() throws ObjectInUseException {
        this.setState(TransactionState.TERMINATED);
    }

    public class TransactionTimer
    extends SIPStackTimerTask {
        @Override
        protected void runTask() {
            Object object = SIPClientTransaction.this;
            Object object2 = ((SIPClientTransaction)object).sipStack;
            if (((SIPTransaction)object).isTerminated()) {
                block10 : {
                    if (((SIPTransactionStack)object2).isLoggingEnabled()) {
                        StackLogger stackLogger = ((SIPTransactionStack)object2).getStackLogger();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("removing  = ");
                        stringBuilder.append(object);
                        stringBuilder.append(" isReliable ");
                        stringBuilder.append(((SIPTransaction)object).isReliable());
                        stackLogger.logDebug(stringBuilder.toString());
                    }
                    ((SIPTransactionStack)object2).removeTransaction((SIPTransaction)object);
                    try {
                        this.cancel();
                    }
                    catch (IllegalStateException illegalStateException) {
                        if (((SIPTransactionStack)object2).isAlive()) break block10;
                        return;
                    }
                }
                if (!((SIPTransactionStack)object2).cacheClientConnections && ((SIPTransaction)object).isReliable()) {
                    int n;
                    object = ((SIPTransaction)object).getMessageChannel();
                    ((MessageChannel)object).useCount = n = ((MessageChannel)object).useCount - 1;
                    if (n <= 0) {
                        object = new SIPTransaction.LingerTimer();
                        ((SIPTransactionStack)object2).getTimer().schedule((TimerTask)object, 8000L);
                    }
                } else if (((SIPTransactionStack)object2).isLoggingEnabled() && ((SIPTransaction)object).isReliable()) {
                    int n = object.getMessageChannel().useCount;
                    if (((SIPTransactionStack)object2).isLoggingEnabled()) {
                        object2 = ((SIPTransactionStack)object2).getStackLogger();
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Client Use Count = ");
                        ((StringBuilder)object).append(n);
                        object2.logDebug(((StringBuilder)object).toString());
                    }
                }
            } else {
                ((SIPTransaction)object).fireTimer();
            }
        }
    }

}

