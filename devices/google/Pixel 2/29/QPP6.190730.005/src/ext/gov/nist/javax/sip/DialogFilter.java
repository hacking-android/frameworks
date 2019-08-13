/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.HostPort;
import gov.nist.core.InternalErrorHandler;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.ClientTransactionExt;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.ResponseEventExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.Utils;
import gov.nist.javax.sip.address.SipUri;
import gov.nist.javax.sip.header.Contact;
import gov.nist.javax.sip.header.Event;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.header.Route;
import gov.nist.javax.sip.header.RouteList;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import gov.nist.javax.sip.stack.ServerResponseInterface;
import java.io.IOException;
import java.io.Serializable;
import java.util.EventObject;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.RequestEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipException;
import javax.sip.SipListener;
import javax.sip.TransactionState;
import javax.sip.address.Address;
import javax.sip.address.URI;
import javax.sip.header.CSeqHeader;
import javax.sip.header.FromHeader;
import javax.sip.header.Header;
import javax.sip.header.ReferToHeader;
import javax.sip.header.ServerHeader;
import javax.sip.header.ToHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

class DialogFilter
implements ServerRequestInterface,
ServerResponseInterface {
    protected ListeningPointImpl listeningPoint;
    private SipStackImpl sipStack;
    protected SIPTransaction transactionChannel;

    public DialogFilter(SipStackImpl sipStackImpl) {
        this.sipStack = sipStackImpl;
    }

    private void sendBadRequestResponse(SIPRequest sIPRequest, SIPServerTransaction sIPServerTransaction, String object) {
        SIPResponse sIPResponse = sIPRequest.createResponse(400);
        if (object != null) {
            sIPResponse.setReasonPhrase((String)object);
        }
        if ((object = MessageFactoryImpl.getDefaultServerHeader()) != null) {
            sIPResponse.setHeader((Header)object);
        }
        try {
            if (sIPRequest.getMethod().equals("INVITE")) {
                this.sipStack.addTransactionPendingAck(sIPServerTransaction);
            }
            sIPServerTransaction.sendResponse((Response)sIPResponse);
            sIPServerTransaction.releaseSem();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Problem sending error response", exception);
            sIPServerTransaction.releaseSem();
            this.sipStack.removeTransaction(sIPServerTransaction);
        }
    }

    private void sendCallOrTransactionDoesNotExistResponse(SIPRequest sIPRequest, SIPServerTransaction sIPServerTransaction) {
        SIPResponse sIPResponse = sIPRequest.createResponse(481);
        ServerHeader serverHeader = MessageFactoryImpl.getDefaultServerHeader();
        if (serverHeader != null) {
            sIPResponse.setHeader(serverHeader);
        }
        try {
            if (sIPRequest.getMethod().equals("INVITE")) {
                this.sipStack.addTransactionPendingAck(sIPServerTransaction);
            }
            sIPServerTransaction.sendResponse((Response)sIPResponse);
            sIPServerTransaction.releaseSem();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Problem sending error response", exception);
            sIPServerTransaction.releaseSem();
            this.sipStack.removeTransaction(sIPServerTransaction);
        }
    }

    private void sendLoopDetectedResponse(SIPRequest sIPMessage, SIPServerTransaction sIPServerTransaction) {
        sIPMessage = ((SIPRequest)sIPMessage).createResponse(482);
        ServerHeader serverHeader = MessageFactoryImpl.getDefaultServerHeader();
        if (serverHeader != null) {
            sIPMessage.setHeader(serverHeader);
        }
        try {
            this.sipStack.addTransactionPendingAck(sIPServerTransaction);
            sIPServerTransaction.sendResponse((Response)((Object)sIPMessage));
            sIPServerTransaction.releaseSem();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Problem sending error response", exception);
            sIPServerTransaction.releaseSem();
            this.sipStack.removeTransaction(sIPServerTransaction);
        }
    }

    private void sendRequestPendingResponse(SIPRequest sIPRequest, SIPServerTransaction sIPServerTransaction) {
        SIPResponse sIPResponse = sIPRequest.createResponse(491);
        Header header = MessageFactoryImpl.getDefaultServerHeader();
        if (header != null) {
            sIPResponse.setHeader(header);
        }
        try {
            header = new RetryAfter();
            ((RetryAfter)header).setRetryAfter(1);
            sIPResponse.setHeader(header);
            if (sIPRequest.getMethod().equals("INVITE")) {
                this.sipStack.addTransactionPendingAck(sIPServerTransaction);
            }
            sIPServerTransaction.sendResponse((Response)sIPResponse);
            sIPServerTransaction.releaseSem();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Problem sending error response", exception);
            sIPServerTransaction.releaseSem();
            this.sipStack.removeTransaction(sIPServerTransaction);
        }
    }

    private void sendServerInternalErrorResponse(SIPRequest sIPMessage, SIPServerTransaction sIPServerTransaction) {
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logDebug("Sending 500 response for out of sequence message");
        }
        sIPMessage = ((SIPRequest)sIPMessage).createResponse(500);
        ((SIPResponse)sIPMessage).setReasonPhrase("Request out of order");
        if (MessageFactoryImpl.getDefaultServerHeader() != null) {
            sIPMessage.setHeader(MessageFactoryImpl.getDefaultServerHeader());
        }
        try {
            RetryAfter retryAfter = new RetryAfter();
            retryAfter.setRetryAfter(10);
            sIPMessage.setHeader(retryAfter);
            this.sipStack.addTransactionPendingAck(sIPServerTransaction);
            sIPServerTransaction.sendResponse((Response)((Object)sIPMessage));
            sIPServerTransaction.releaseSem();
        }
        catch (Exception exception) {
            this.sipStack.getStackLogger().logError("Problem sending response", exception);
            sIPServerTransaction.releaseSem();
            this.sipStack.removeTransaction(sIPServerTransaction);
        }
    }

    public String getProcessingInfo() {
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void processRequest(SIPRequest var1_1, MessageChannel var2_10) {
        block160 : {
            block154 : {
                block155 : {
                    block157 : {
                        block158 : {
                            block159 : {
                                block156 : {
                                    block149 : {
                                        block151 : {
                                            block153 : {
                                                block152 : {
                                                    block150 : {
                                                        block148 : {
                                                            block146 : {
                                                                block147 : {
                                                                    if (this.sipStack.isLoggingEnabled()) {
                                                                        var3_15 = this.sipStack.getStackLogger();
                                                                        var2_10 = new StringBuilder();
                                                                        var2_10.append("PROCESSING INCOMING REQUEST ");
                                                                        var2_10.append(var1_1);
                                                                        var2_10.append(" transactionChannel = ");
                                                                        var2_10.append(this.transactionChannel);
                                                                        var2_10.append(" listening point = ");
                                                                        var2_10.append(this.listeningPoint.getIPAddress());
                                                                        var2_10.append(":");
                                                                        var2_10.append(this.listeningPoint.getPort());
                                                                        var3_15.logDebug(var2_10.toString());
                                                                    }
                                                                    if (this.listeningPoint == null) {
                                                                        if (this.sipStack.isLoggingEnabled() == false) return;
                                                                        this.sipStack.getStackLogger().logDebug("Dropping message: No listening point registered!");
                                                                        return;
                                                                    }
                                                                    var4_18 = (SipStackImpl)this.transactionChannel.getSIPStack();
                                                                    var5_19 = this.listeningPoint.getProvider();
                                                                    if (var5_19 == null) {
                                                                        if (var4_18.isLoggingEnabled() == false) return;
                                                                        var4_18.getStackLogger().logDebug("No provider - dropping !!");
                                                                        return;
                                                                    }
                                                                    if (var4_18 == null) {
                                                                        InternalErrorHandler.handleException("Egads! no sip stack!");
                                                                    }
                                                                    if ((var6_20 = (SIPServerTransaction)this.transactionChannel) != null && var4_18.isLoggingEnabled()) {
                                                                        var2_10 = var4_18.getStackLogger();
                                                                        var3_15 = new StringBuilder();
                                                                        var3_15.append("transaction state = ");
                                                                        var3_15.append((Object)var6_20.getState());
                                                                        var2_10.logDebug(var3_15.toString());
                                                                    }
                                                                    var7_21 = var1_1.getDialogId(true);
                                                                    var3_15 = var8_22 = var4_18.getDialog(var7_21);
                                                                    if (var8_22 == null) break block146;
                                                                    var3_15 = var8_22;
                                                                    if (var5_19 == var8_22.getSipProvider()) break block146;
                                                                    var2_10 = var8_22.getMyContactHeader();
                                                                    var3_15 = var8_22;
                                                                    if (var2_10 == null) break block146;
                                                                    var2_10 = (SipUri)var2_10.getAddress().getURI();
                                                                    var9_23 = var2_10.getHost();
                                                                    var10_24 = var2_10.getPort();
                                                                    var2_10 = var3_15 = var2_10.getTransportParam();
                                                                    if (var3_15 == null) {
                                                                        var2_10 = "udp";
                                                                    }
                                                                    var11_25 = var10_24;
                                                                    if (var10_24 == -1) {
                                                                        var11_25 = !var2_10.equals("udp") && !var2_10.equals("tcp") ? 5061 : 5060;
                                                                    }
                                                                    var3_15 = var8_22;
                                                                    if (var9_23 == null) break block146;
                                                                    if (!var9_23.equals(this.listeningPoint.getIPAddress())) break block147;
                                                                    var3_15 = var8_22;
                                                                    if (var11_25 == this.listeningPoint.getPort()) break block146;
                                                                }
                                                                if (var4_18.isLoggingEnabled()) {
                                                                    var2_10 = var4_18.getStackLogger();
                                                                    var3_15 = new StringBuilder();
                                                                    var3_15.append("nulling dialog -- listening point mismatch!  ");
                                                                    var3_15.append(var11_25);
                                                                    var3_15.append("  lp port = ");
                                                                    var3_15.append(this.listeningPoint.getPort());
                                                                    var2_10.logDebug(var3_15.toString());
                                                                }
                                                                var3_15 = null;
                                                            }
                                                            if (var5_19.isAutomaticDialogSupportEnabled() && var5_19.isDialogErrorsAutomaticallyHandled() && var1_1.getToTag() == null && var4_18.findMergedTransaction((SIPRequest)var1_1) != null) {
                                                                this.sendLoopDetectedResponse((SIPRequest)var1_1, var6_20);
                                                                return;
                                                            }
                                                            if (var4_18.isLoggingEnabled()) {
                                                                var2_10 = var4_18.getStackLogger();
                                                                var8_22 = new StringBuilder();
                                                                var8_22.append("dialogId = ");
                                                                var8_22.append(var7_21);
                                                                var2_10.logDebug(var8_22.toString());
                                                                var8_22 = var4_18.getStackLogger();
                                                                var2_10 = new StringBuilder();
                                                                var2_10.append("dialog = ");
                                                                var2_10.append(var3_15);
                                                                var8_22.logDebug(var2_10.toString());
                                                            }
                                                            if (var1_1.getHeader("Route") != null && var6_20.getDialog() != null) {
                                                                var2_10 = var1_1.getRouteHeaders();
                                                                var8_22 = (SipUri)((Route)var2_10.getFirst()).getAddress().getURI();
                                                                var11_25 = var8_22.getHostPort().hasPort() != false ? var8_22.getHostPort().getPort() : (this.listeningPoint.getTransport().equalsIgnoreCase("TLS") != false ? 5061 : 5060);
                                                                if (((var8_22 = var8_22.getHost()).equals(this.listeningPoint.getIPAddress()) || var8_22.equalsIgnoreCase(this.listeningPoint.getSentBy())) && var11_25 == this.listeningPoint.getPort()) {
                                                                    if (var2_10.size() == 1) {
                                                                        var1_1.removeHeader("Route");
                                                                    } else {
                                                                        var2_10.removeFirst();
                                                                    }
                                                                }
                                                            }
                                                            if (!var1_1.getMethod().equals("REFER") || var3_15 == null || !var5_19.isDialogErrorsAutomaticallyHandled()) break block148;
                                                            if ((ReferToHeader)var1_1.getHeader("Refer-To") == null) {
                                                                this.sendBadRequestResponse((SIPRequest)var1_1, var6_20, "Refer-To header is missing");
                                                                return;
                                                            }
                                                            var2_10 = var3_15.getLastTransaction();
                                                            if (var2_10 != null && var5_19.isDialogErrorsAutomaticallyHandled()) {
                                                                var8_22 = (SIPRequest)var2_10.getRequest();
                                                                if (var2_10 instanceof SIPServerTransaction) {
                                                                    if (!var3_15.isAckSeen() && var8_22.getMethod().equals("INVITE")) {
                                                                        this.sendRequestPendingResponse((SIPRequest)var1_1, var6_20);
                                                                        return;
                                                                    }
                                                                } else if (var2_10 instanceof SIPClientTransaction) {
                                                                    var12_26 = var8_22.getCSeqHeader().getSeqNumber();
                                                                    if (var8_22.getMethod().equals("INVITE") && !var3_15.isAckSent(var12_26)) {
                                                                        this.sendRequestPendingResponse((SIPRequest)var1_1, var6_20);
                                                                        return;
                                                                    }
                                                                }
                                                            }
                                                            var2_10 = "CANCEL";
                                                            var8_22 = var3_15;
                                                            break block149;
                                                        }
                                                        if (!var1_1.getMethod().equals("UPDATE")) break block150;
                                                        if (var5_19.isAutomaticDialogSupportEnabled() && var3_15 == null) {
                                                            this.sendCallOrTransactionDoesNotExistResponse((SIPRequest)var1_1, var6_20);
                                                            return;
                                                        }
                                                        var2_10 = "CANCEL";
                                                        var8_22 = var3_15;
                                                        break block149;
                                                    }
                                                    if (!var1_1.getMethod().equals("ACK")) break block151;
                                                    if (var6_20 == null || !var6_20.isInviteTransaction()) break block152;
                                                    if (var4_18.isLoggingEnabled()) {
                                                        var4_18.getStackLogger().logDebug("Processing ACK for INVITE Tx ");
                                                        var2_10 = "CANCEL";
                                                        var8_22 = var3_15;
                                                    } else {
                                                        var2_10 = "CANCEL";
                                                        var8_22 = var3_15;
                                                    }
                                                    break block149;
                                                }
                                                if (var4_18.isLoggingEnabled()) {
                                                    var8_22 = var4_18.getStackLogger();
                                                    var2_10 = new StringBuilder();
                                                    var2_10.append("Processing ACK for dialog ");
                                                    var2_10.append(var3_15);
                                                    var8_22.logDebug(var2_10.toString());
                                                }
                                                if (var3_15 != null) break block153;
                                                if (var4_18.isLoggingEnabled()) {
                                                    var2_10 = var4_18.getStackLogger();
                                                    var8_22 = new StringBuilder();
                                                    var8_22.append("Dialog does not exist ");
                                                    var8_22.append(var1_1.getFirstLine());
                                                    var8_22.append(" isServerTransaction = ");
                                                    var8_22.append(true);
                                                    var2_10.logDebug(var8_22.toString());
                                                }
                                                if ((var2_10 = var4_18.getRetransmissionAlertTransaction(var7_21)) != null && var2_10.isRetransmissionAlertEnabled()) {
                                                    var2_10.disableRetransmissionAlerts();
                                                }
                                                if ((var2_10 = var4_18.findTransactionPendingAck((SIPRequest)var1_1)) != null) {
                                                    if (var4_18.isLoggingEnabled()) {
                                                        var4_18.getStackLogger().logDebug("Found Tx pending ACK");
                                                    }
                                                    try {
                                                        var2_10.setAckSeen();
                                                        var4_18.removeTransaction((SIPTransaction)var2_10);
                                                        var4_18.removeTransactionPendingAck((SIPServerTransaction)var2_10);
                                                        return;
                                                    }
                                                    catch (Exception var1_2) {
                                                        if (var4_18.isLoggingEnabled() == false) return;
                                                        var4_18.getStackLogger().logError("Problem terminating transaction", var1_2);
                                                    }
                                                    return;
                                                }
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                                break block149;
                                            }
                                            if (var3_15.handleAck(var6_20)) ** GOTO lbl223
                                            if (!var3_15.isSequnceNumberValidation()) {
                                                if (var4_18.isLoggingEnabled()) {
                                                    var8_22 = var4_18.getStackLogger();
                                                    var2_10 = new StringBuilder();
                                                    var2_10.append("Dialog exists with loose dialog validation ");
                                                    var2_10.append(var1_1.getFirstLine());
                                                    var2_10.append(" isServerTransaction = ");
                                                    var2_10.append(true);
                                                    var2_10.append(" dialog = ");
                                                    var2_10.append(var3_15.getDialogId());
                                                    var8_22.logDebug(var2_10.toString());
                                                }
                                                if ((var2_10 = var4_18.getRetransmissionAlertTransaction(var7_21)) != null && var2_10.isRetransmissionAlertEnabled()) {
                                                    var2_10.disableRetransmissionAlerts();
                                                }
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            } else {
                                                if (var4_18.isLoggingEnabled()) {
                                                    var4_18.getStackLogger().logDebug("Dropping ACK - cannot find a transaction or dialog");
                                                }
                                                if ((var1_1 = var4_18.findTransactionPendingAck((SIPRequest)var1_1)) == null) return;
                                                if (var4_18.isLoggingEnabled()) {
                                                    var4_18.getStackLogger().logDebug("Found Tx pending ACK");
                                                }
                                                try {
                                                    var1_1.setAckSeen();
                                                    var4_18.removeTransaction((SIPTransaction)var1_1);
                                                    var4_18.removeTransactionPendingAck((SIPServerTransaction)var1_1);
                                                    return;
                                                }
                                                catch (Exception var1_3) {
                                                    if (var4_18.isLoggingEnabled() == false) return;
                                                    var4_18.getStackLogger().logError("Problem terminating transaction", var1_3);
                                                }
                                                return;
lbl223: // 1 sources:
                                                var6_20.passToListener();
                                                var3_15.addTransaction(var6_20);
                                                var3_15.addRoute((SIPRequest)var1_1);
                                                var6_20.setDialog((SIPDialog)var3_15, var7_21);
                                                if (var1_1.getMethod().equals("INVITE") && var5_19.isDialogErrorsAutomaticallyHandled()) {
                                                    var4_18.putInMergeTable(var6_20, (SIPRequest)var1_1);
                                                }
                                                if (var4_18.deliverTerminatedEventForAck) {
                                                    try {
                                                        var4_18.addTransaction(var6_20);
                                                        var6_20.scheduleAckRemoval();
                                                    }
                                                    catch (IOException var2_11) {
                                                        // empty catch block
                                                    }
                                                    var2_10 = "CANCEL";
                                                    var8_22 = var3_15;
                                                } else {
                                                    var6_20.setMapped(true);
                                                    var2_10 = "CANCEL";
                                                    var8_22 = var3_15;
                                                }
                                            }
                                            break block149;
                                        }
                                        if (var1_1.getMethod().equals("PRACK")) {
                                            if (var4_18.isLoggingEnabled()) {
                                                var8_22 = var4_18.getStackLogger();
                                                var2_10 = new StringBuilder();
                                                var2_10.append("Processing PRACK for dialog ");
                                                var2_10.append(var3_15);
                                                var8_22.logDebug(var2_10.toString());
                                            }
                                            if (var3_15 == null && var5_19.isAutomaticDialogSupportEnabled()) {
                                                if (var4_18.isLoggingEnabled()) {
                                                    var2_10 = var4_18.getStackLogger();
                                                    var3_15 = new StringBuilder();
                                                    var3_15.append("Dialog does not exist ");
                                                    var3_15.append(var1_1.getFirstLine());
                                                    var3_15.append(" isServerTransaction = ");
                                                    var3_15.append(true);
                                                    var2_10.logDebug(var3_15.toString());
                                                }
                                                if (var4_18.isLoggingEnabled()) {
                                                    var4_18.getStackLogger().logDebug("Sending 481 for PRACK - automatic dialog support is enabled -- cant find dialog!");
                                                }
                                                var1_1 = var1_1.createResponse(481);
                                                try {
                                                    var5_19.sendResponse((Response)var1_1);
                                                }
                                                catch (SipException var1_4) {
                                                    var4_18.getStackLogger().logError("error sending response", var1_4);
                                                }
                                                if (var6_20 == null) return;
                                                var4_18.removeTransaction(var6_20);
                                                var6_20.releaseSem();
                                                return;
                                            }
                                            if (var3_15 != null) {
                                                if (!var3_15.handlePrack((SIPRequest)var1_1)) {
                                                    if (var4_18.isLoggingEnabled()) {
                                                        var4_18.getStackLogger().logDebug("Dropping out of sequence PRACK ");
                                                    }
                                                    if (var6_20 == null) return;
                                                    var4_18.removeTransaction(var6_20);
                                                    var6_20.releaseSem();
                                                    return;
                                                }
                                                try {
                                                    var4_18.addTransaction(var6_20);
                                                    var3_15.addTransaction(var6_20);
                                                    var3_15.addRoute((SIPRequest)var1_1);
                                                    var6_20.setDialog((SIPDialog)var3_15, var7_21);
                                                }
                                                catch (Exception var2_12) {
                                                    InternalErrorHandler.handleException(var2_12);
                                                }
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            } else if (var4_18.isLoggingEnabled()) {
                                                var4_18.getStackLogger().logDebug("Processing PRACK without a DIALOG -- this must be a proxy element");
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            } else {
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            }
                                        } else if (var1_1.getMethod().equals("BYE")) {
                                            if (var3_15 != null && !var3_15.isRequestConsumable((SIPRequest)var1_1)) {
                                                if (var4_18.isLoggingEnabled()) {
                                                    var2_10 = var4_18.getStackLogger();
                                                    var8_22 = new StringBuilder();
                                                    var8_22.append("Dropping out of sequence BYE ");
                                                    var8_22.append(var3_15.getRemoteSeqNumber());
                                                    var8_22.append(" ");
                                                    var8_22.append(var1_1.getCSeq().getSeqNumber());
                                                    var2_10.logDebug(var8_22.toString());
                                                }
                                                if (var3_15.getRemoteSeqNumber() >= var1_1.getCSeq().getSeqNumber() && var6_20.getState() == TransactionState.TRYING) {
                                                    this.sendServerInternalErrorResponse((SIPRequest)var1_1, var6_20);
                                                }
                                                if (var6_20 == null) return;
                                                var4_18.removeTransaction(var6_20);
                                                return;
                                            }
                                            if (var3_15 == null && var5_19.isAutomaticDialogSupportEnabled()) {
                                                var1_1 = var1_1.createResponse(481);
                                                var1_1.setReasonPhrase("Dialog Not Found");
                                                if (var4_18.isLoggingEnabled()) {
                                                    var4_18.getStackLogger().logDebug("dropping request -- automatic dialog support enabled and dialog does not exist!");
                                                }
                                                try {
                                                    var6_20.sendResponse((Response)var1_1);
                                                }
                                                catch (SipException var1_5) {
                                                    var4_18.getStackLogger().logError("Error in sending response", var1_5);
                                                }
                                                if (var6_20 == null) return;
                                                var4_18.removeTransaction(var6_20);
                                                var6_20.releaseSem();
                                                return;
                                            }
                                            if (var6_20 != null && var3_15 != null) {
                                                try {
                                                    if (var5_19 == var3_15.getSipProvider()) {
                                                        var4_18.addTransaction(var6_20);
                                                        var3_15.addTransaction(var6_20);
                                                        var6_20.setDialog((SIPDialog)var3_15, var7_21);
                                                    }
                                                }
                                                catch (IOException var2_13) {
                                                    InternalErrorHandler.handleException(var2_13);
                                                }
                                            }
                                            if (var4_18.isLoggingEnabled()) {
                                                var8_22 = var4_18.getStackLogger();
                                                var2_10 = new StringBuilder();
                                                var2_10.append("BYE Tx = ");
                                                var2_10.append(var6_20);
                                                var2_10.append(" isMapped =");
                                                var2_10.append(var6_20.isTransactionMapped());
                                                var8_22.logDebug(var2_10.toString());
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            } else {
                                                var2_10 = "CANCEL";
                                                var8_22 = var3_15;
                                            }
                                        } else {
                                            var2_10 = var1_1.getMethod();
                                            if (var2_10.equals(var14_27 = "CANCEL")) {
                                                var9_23 = (SIPServerTransaction)var4_18.findCancelTransaction((SIPRequest)var1_1, true);
                                                if (var4_18.isLoggingEnabled()) {
                                                    var2_10 = var4_18.getStackLogger();
                                                    var8_22 = new StringBuilder();
                                                    var8_22.append("Got a CANCEL, InviteServerTx = ");
                                                    var8_22.append(var9_23);
                                                    var8_22.append(" cancel Server Tx ID = ");
                                                    var8_22.append(var6_20);
                                                    var8_22.append(" isMapped = ");
                                                    var8_22.append(var6_20.isTransactionMapped());
                                                    var2_10.logDebug(var8_22.toString());
                                                }
                                                if (var1_1.getMethod().equals(var14_27)) {
                                                    if (var9_23 != null && var9_23.getState() == SIPTransaction.TERMINATED_STATE) {
                                                        if (var4_18.isLoggingEnabled()) {
                                                            var4_18.getStackLogger().logDebug("Too late to cancel Transaction");
                                                        }
                                                        try {
                                                            var6_20.sendResponse((Response)var1_1.createResponse(200));
                                                            return;
                                                        }
                                                        catch (Exception var1_6) {
                                                            if (var1_6.getCause() == null) return;
                                                            if (var1_6.getCause() instanceof IOException == false) return;
                                                            var9_23.raiseIOExceptionEvent();
                                                        }
                                                        return;
                                                    }
                                                    if (var4_18.isLoggingEnabled()) {
                                                        var2_10 = var4_18.getStackLogger();
                                                        var8_22 = new StringBuilder();
                                                        var8_22.append("Cancel transaction = ");
                                                        var8_22.append(var9_23);
                                                        var2_10.logDebug(var8_22.toString());
                                                    }
                                                }
                                                if (var6_20 != null && var9_23 != null && var9_23.getDialog() != null) {
                                                    var6_20.setDialog((SIPDialog)var9_23.getDialog(), var7_21);
                                                    var2_10 = (SIPDialog)var9_23.getDialog();
                                                } else {
                                                    var2_10 = var3_15;
                                                    if (var9_23 == null) {
                                                        var2_10 = var3_15;
                                                        if (var5_19.isAutomaticDialogSupportEnabled()) {
                                                            var2_10 = var3_15;
                                                            if (var6_20 != null) {
                                                                var1_1 = var1_1.createResponse(481);
                                                                if (var4_18.isLoggingEnabled()) {
                                                                    var4_18.getStackLogger().logDebug("dropping request -- automatic dialog support enabled and INVITE ST does not exist!");
                                                                }
                                                                try {
                                                                    var5_19.sendResponse((Response)var1_1);
                                                                }
                                                                catch (SipException var1_7) {
                                                                    InternalErrorHandler.handleException(var1_7);
                                                                }
                                                                var4_18.removeTransaction(var6_20);
                                                                var6_20.releaseSem();
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                                var8_22 = var2_10;
                                                if (var9_23 != null) {
                                                    var8_22 = var2_10;
                                                    if (var6_20 != null) {
                                                        try {
                                                            var4_18.addTransaction(var6_20);
                                                            var6_20.setPassToListener();
                                                            var6_20.setInviteTransaction((SIPServerTransaction)var9_23);
                                                            var9_23.acquireSem();
                                                            var8_22 = var2_10;
                                                        }
                                                        catch (Exception var3_16) {
                                                            InternalErrorHandler.handleException(var3_16);
                                                            var8_22 = var2_10;
                                                        }
                                                    }
                                                }
                                            } else {
                                                var8_22 = var3_15;
                                                if (var1_1.getMethod().equals("INVITE")) {
                                                    var2_10 = var3_15 == null ? null : var3_15.getInviteTransaction();
                                                }
                                            }
                                            var2_10 = var14_27;
                                        }
                                        break block149;
                                        if (var3_15 != null && var6_20 != null && var2_10 != null && var1_1.getCSeq().getSeqNumber() > var3_15.getRemoteSeqNumber() && var2_10 instanceof SIPServerTransaction && var5_19.isDialogErrorsAutomaticallyHandled() && var3_15.isSequnceNumberValidation() && var2_10.isInviteTransaction() && var2_10.getState() != TransactionState.COMPLETED && var2_10.getState() != TransactionState.TERMINATED && var2_10.getState() != TransactionState.CONFIRMED) {
                                            if (var4_18.isLoggingEnabled()) {
                                                var4_18.getStackLogger().logDebug("Sending 500 response for out of sequence message");
                                            }
                                            this.sendServerInternalErrorResponse((SIPRequest)var1_1, var6_20);
                                            return;
                                        }
                                        var9_23 = var3_15 == null ? null : var3_15.getLastTransaction();
                                        if (var3_15 != null && var5_19.isDialogErrorsAutomaticallyHandled() && var9_23 != null && var9_23.isInviteTransaction() && var9_23 instanceof ClientTransaction && var9_23.getLastResponse() != null && var9_23.getLastResponse().getStatusCode() == 200 && !var3_15.isAckSent(var9_23.getLastResponse().getCSeq().getSeqNumber())) {
                                            if (var4_18.isLoggingEnabled()) {
                                                var4_18.getStackLogger().logDebug("Sending 491 response for client Dialog ACK not sent.");
                                            }
                                            this.sendRequestPendingResponse((SIPRequest)var1_1, var6_20);
                                            return;
                                        }
                                        var8_22 = var3_15;
                                        var2_10 = var14_27;
                                        if (var3_15 != null) {
                                            var8_22 = var3_15;
                                            var2_10 = var14_27;
                                            if (var9_23 != null) {
                                                var8_22 = var3_15;
                                                var2_10 = var14_27;
                                                if (var5_19.isDialogErrorsAutomaticallyHandled()) {
                                                    var8_22 = var3_15;
                                                    var2_10 = var14_27;
                                                    if (var9_23.isInviteTransaction()) {
                                                        var8_22 = var3_15;
                                                        var2_10 = var14_27;
                                                        if (var9_23 instanceof ServerTransaction) {
                                                            var8_22 = var3_15;
                                                            var2_10 = var14_27;
                                                            if (!var3_15.isAckSeen()) {
                                                                if (var4_18.isLoggingEnabled()) {
                                                                    var4_18.getStackLogger().logDebug("Sending 491 response for server Dialog ACK not seen.");
                                                                }
                                                                this.sendRequestPendingResponse((SIPRequest)var1_1, var6_20);
                                                                return;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (var4_18.isLoggingEnabled()) {
                                        var9_23 = var4_18.getStackLogger();
                                        var3_15 = new StringBuilder();
                                        var3_15.append("CHECK FOR OUT OF SEQ MESSAGE ");
                                        var3_15.append(var8_22);
                                        var3_15.append(" transaction ");
                                        var3_15.append(var6_20);
                                        var9_23.logDebug(var3_15.toString());
                                    }
                                    if (!(var8_22 == null || var6_20 == null || var1_1.getMethod().equals("BYE") || var1_1.getMethod().equals(var2_10) || var1_1.getMethod().equals("ACK") || var1_1.getMethod().equals("PRACK"))) {
                                        if (!var8_22.isRequestConsumable((SIPRequest)var1_1)) {
                                            if (var4_18.isLoggingEnabled()) {
                                                var3_15 = var4_18.getStackLogger();
                                                var2_10 = new StringBuilder();
                                                var2_10.append("Dropping out of sequence message ");
                                                var2_10.append(var8_22.getRemoteSeqNumber());
                                                var2_10.append(" ");
                                                var2_10.append(var1_1.getCSeq());
                                                var3_15.logDebug(var2_10.toString());
                                            }
                                            if (var8_22.getRemoteSeqNumber() < var1_1.getCSeq().getSeqNumber()) return;
                                            if (var5_19.isDialogErrorsAutomaticallyHandled() == false) return;
                                            if (var6_20.getState() != TransactionState.TRYING) {
                                                if (var6_20.getState() != TransactionState.PROCEEDING) return;
                                            }
                                            this.sendServerInternalErrorResponse((SIPRequest)var1_1, var6_20);
                                            return;
                                        }
                                        try {
                                            if (var5_19 == var8_22.getSipProvider()) {
                                                var4_18.addTransaction(var6_20);
                                                var8_22.addTransaction(var6_20);
                                                var8_22.addRoute((SIPRequest)var1_1);
                                                var6_20.setDialog((SIPDialog)var8_22, var7_21);
                                            }
                                        }
                                        catch (IOException var1_8) {
                                            var6_20.raiseIOExceptionEvent();
                                            var4_18.removeTransaction(var6_20);
                                            return;
                                        }
                                    }
                                    if (var4_18.isLoggingEnabled()) {
                                        var3_15 = var4_18.getStackLogger();
                                        var2_10 = new StringBuilder();
                                        var2_10.append(var1_1.getMethod());
                                        var2_10.append(" transaction.isMapped = ");
                                        var2_10.append(var6_20.isTransactionMapped());
                                        var3_15.logDebug(var2_10.toString());
                                    }
                                    if (var8_22 != null || !var1_1.getMethod().equals("NOTIFY")) break block154;
                                    var8_22 = var4_18.findSubscribeTransaction((SIPRequest)var1_1, this.listeningPoint);
                                    if (var4_18.isLoggingEnabled()) {
                                        var2_10 = var4_18.getStackLogger();
                                        var3_15 = new StringBuilder();
                                        var3_15.append("PROCESSING NOTIFY  DIALOG == null ");
                                        var3_15.append(var8_22);
                                        var2_10.logDebug(var3_15.toString());
                                    }
                                    if (var5_19.isAutomaticDialogSupportEnabled() && var8_22 == null && !var4_18.deliverUnsolicitedNotify) {
                                        try {
                                            if (var4_18.isLoggingEnabled()) {
                                                var4_18.getStackLogger().logDebug("Could not find Subscription for Notify Tx.");
                                            }
                                            var1_1 = var1_1.createResponse(481);
                                            var1_1.setReasonPhrase("Subscription does not exist");
                                            var5_19.sendResponse((Response)var1_1);
                                            return;
                                        }
                                        catch (Exception var1_9) {
                                            var4_18.getStackLogger().logError("Exception while sending error response statelessly", var1_9);
                                            return;
                                        }
                                    }
                                    if (var8_22 == null) break block155;
                                    var6_20.setPendingSubscribe((SIPClientTransaction)var8_22);
                                    var3_15 = var8_22.getDefaultDialog();
                                    if (var3_15 == null || var3_15.getDialogId() == null || !var3_15.getDialogId().equals(var7_21)) break block156;
                                    var6_20.setDialog((SIPDialog)var3_15, var7_21);
                                    if (!var6_20.isTransactionMapped()) {
                                        this.sipStack.mapTransaction(var6_20);
                                        var6_20.setPassToListener();
                                        try {
                                            this.sipStack.addTransaction(var6_20);
                                        }
                                        catch (Exception var2_14) {
                                            // empty catch block
                                        }
                                    }
                                    var4_18.putDialog((SIPDialog)var3_15);
                                    var3_15.addTransaction((SIPTransaction)var8_22);
                                    var8_22.setDialog((SIPDialog)var3_15, var7_21);
                                    break block157;
                                }
                                if (var3_15 != null && var3_15.getDialogId() == null) {
                                    var3_15.setDialogId(var7_21);
                                } else {
                                    var3_15 = var8_22.getDialog(var7_21);
                                }
                                if (var4_18.isLoggingEnabled()) {
                                    var2_10 = var4_18.getStackLogger();
                                    var9_23 = new StringBuilder();
                                    var9_23.append("PROCESSING NOTIFY Subscribe DIALOG ");
                                    var9_23.append(var3_15);
                                    var2_10.logDebug(var9_23.toString());
                                }
                                var2_10 = var3_15;
                                if (var3_15 != null) break block158;
                                if (var5_19.isAutomaticDialogSupportEnabled()) break block159;
                                var2_10 = var3_15;
                                if (var8_22.getDefaultDialog() == null) break block158;
                            }
                            var2_10 = var3_15;
                            if (var4_18.isEventForked(((Event)var1_1.getHeader("Event")).getEventType())) {
                                var2_10 = SIPDialog.createFromNOTIFY((SIPClientTransaction)var8_22, var6_20);
                            }
                        }
                        var3_15 = var2_10;
                        if (var2_10 != null) {
                            var6_20.setDialog((SIPDialog)var2_10, var7_21);
                            var2_10.setState(DialogState.CONFIRMED.getValue());
                            var4_18.putDialog((SIPDialog)var2_10);
                            var8_22.setDialog((SIPDialog)var2_10, var7_21);
                            var3_15 = var2_10;
                            if (!var6_20.isTransactionMapped()) {
                                this.sipStack.mapTransaction(var6_20);
                                var6_20.setPassToListener();
                                try {
                                    this.sipStack.addTransaction(var6_20);
                                    var3_15 = var2_10;
                                }
                                catch (Exception var3_17) {
                                    var3_15 = var2_10;
                                }
                            }
                        }
                    }
                    var1_1 = var6_20.isTransactionMapped() ? new RequestEvent(var5_19, var6_20, (Dialog)var3_15, (Request)var1_1) : new RequestEvent(var5_19, null, (Dialog)var3_15, (Request)var1_1);
                    break block160;
                }
                if (var4_18.isLoggingEnabled()) {
                    var4_18.getStackLogger().logDebug("could not find subscribe tx");
                }
                var1_1 = new RequestEvent(var5_19, null, null, (Request)var1_1);
                break block160;
            }
            var1_1 = var6_20 != null && var6_20.isTransactionMapped() != false ? new RequestEvent(var5_19, var6_20, (Dialog)var8_22, (Request)var1_1) : new RequestEvent(var5_19, null, (Dialog)var8_22, (Request)var1_1);
        }
        var5_19.handleEvent((EventObject)var1_1, var6_20);
    }

    @Override
    public void processResponse(SIPResponse sIPResponse, MessageChannel object) {
        boolean bl;
        Serializable serializable;
        boolean bl2;
        Object object2 = sIPResponse.getDialogId(false);
        Object object3 = this.sipStack.getDialog((String)object2);
        Object object4 = sIPResponse.getCSeq().getMethod();
        if (this.sipStack.isLoggingEnabled()) {
            object = this.sipStack.getStackLogger();
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("PROCESSING INCOMING RESPONSE: ");
            ((StringBuilder)serializable).append(sIPResponse.encodeMessage());
            object.logDebug(((StringBuilder)serializable).toString());
        }
        if (this.sipStack.checkBranchId() && !Utils.getInstance().responseBelongsToUs(sIPResponse)) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("Detected stray response -- dropping");
            }
            return;
        }
        object = this.listeningPoint;
        if (object == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping message: No listening point registered!");
            }
            return;
        }
        SipProviderImpl sipProviderImpl = ((ListeningPointImpl)object).getProvider();
        if (sipProviderImpl == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping message:  no provider");
            }
            return;
        }
        if (sipProviderImpl.getSipListener() == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logDebug("Dropping message:  no sipListener registered!");
            }
            return;
        }
        serializable = (SIPClientTransaction)this.transactionChannel;
        object = object3;
        if (object3 == null) {
            object = object3;
            if (serializable != null) {
                object = object3 = ((SIPClientTransaction)serializable).getDialog((String)object2);
                if (object3 != null) {
                    object = object3;
                    if (((SIPDialog)object3).getState() == DialogState.TERMINATED) {
                        object = null;
                    }
                }
            }
        }
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("Transaction = ");
            ((StringBuilder)object3).append(serializable);
            ((StringBuilder)object3).append(" sipDialog = ");
            ((StringBuilder)object3).append(object);
            object2.logDebug(((StringBuilder)object3).toString());
        }
        if ((object3 = this.transactionChannel) != null) {
            object3 = ((SIPRequest)((SIPTransaction)object3).getRequest()).getFromTag();
            bl = true;
            bl2 = object3 == null;
            if (sIPResponse.getFrom().getTag() != null) {
                bl = false;
            }
            if (bl ^ bl2) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("From tag mismatch -- dropping response");
                }
                return;
            }
            if (object3 != null && !((String)object3).equalsIgnoreCase(sIPResponse.getFrom().getTag())) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("From tag mismatch -- dropping response");
                }
                return;
            }
        }
        object3 = this.sipStack;
        if (SipStackImpl.isDialogCreated((String)object4) && sIPResponse.getStatusCode() != 100 && sIPResponse.getFrom().getTag() != null && sIPResponse.getTo().getTag() != null && object == null) {
            object3 = object;
            if (sipProviderImpl.isAutomaticDialogSupportEnabled()) {
                object4 = this.transactionChannel;
                if (object4 != null) {
                    object3 = object;
                    if (object == null) {
                        object3 = this.sipStack.createDialog((SIPClientTransaction)object4, sIPResponse);
                        this.transactionChannel.setDialog((SIPDialog)object3, sIPResponse.getDialogId(false));
                    }
                } else {
                    object3 = this.sipStack.createDialog(sipProviderImpl, sIPResponse);
                }
            }
        } else {
            object3 = object;
            if (object != null) {
                object3 = object;
                if (serializable == null) {
                    object3 = object;
                    if (((SIPDialog)object).getState() != DialogState.TERMINATED) {
                        if (sIPResponse.getStatusCode() / 100 != 2) {
                            object3 = object;
                            if (this.sipStack.isLoggingEnabled()) {
                                object4 = this.sipStack.getStackLogger();
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("status code != 200 ; statusCode = ");
                                ((StringBuilder)object3).append(sIPResponse.getStatusCode());
                                object4.logDebug(((StringBuilder)object3).toString());
                                object3 = object;
                            }
                        } else {
                            if (((SIPDialog)object).getState() == DialogState.TERMINATED) {
                                if (this.sipStack.isLoggingEnabled()) {
                                    this.sipStack.getStackLogger().logDebug("Dialog is terminated -- dropping response!");
                                }
                                if (sIPResponse.getStatusCode() / 100 == 2 && sIPResponse.getCSeq().getMethod().equals("INVITE")) {
                                    try {
                                        ((SIPDialog)object).sendAck(((SIPDialog)object).createAck(sIPResponse.getCSeq().getSeqNumber()));
                                    }
                                    catch (Exception exception) {
                                        this.sipStack.getStackLogger().logError("Error creating ack", exception);
                                    }
                                }
                                return;
                            }
                            bl2 = bl = false;
                            if (((SIPDialog)object).isAckSeen()) {
                                bl2 = bl;
                                if (((SIPDialog)object).getLastAckSent() != null) {
                                    bl2 = bl;
                                    if (((SIPDialog)object).getLastAckSent().getCSeq().getSeqNumber() == sIPResponse.getCSeq().getSeqNumber()) {
                                        bl2 = bl;
                                        if (sIPResponse.getDialogId(false).equals(((SIPDialog)object).getLastAckSent().getDialogId(false))) {
                                            bl2 = true;
                                        }
                                    }
                                }
                            }
                            object3 = object;
                            if (bl2) {
                                object3 = object;
                                if (sIPResponse.getCSeq().getMethod().equals(((SIPDialog)object).getMethod())) {
                                    try {
                                        if (this.sipStack.isLoggingEnabled()) {
                                            this.sipStack.getStackLogger().logDebug("resending ACK");
                                        }
                                        ((SIPDialog)object).resendAck();
                                        return;
                                    }
                                    catch (SipException sipException) {
                                        object3 = object;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (this.sipStack.isLoggingEnabled()) {
            this.sipStack.getStackLogger().logDebug("sending response to TU for processing ");
        }
        if (object3 != null && sIPResponse.getStatusCode() != 100 && sIPResponse.getTo().getTag() != null) {
            ((SIPDialog)object3).setLastResponse((SIPTransaction)serializable, sIPResponse);
        }
        object = new ResponseEventExt(sipProviderImpl, (ClientTransactionExt)serializable, (Dialog)object3, sIPResponse);
        if (sIPResponse.getCSeq().getMethod().equals("INVITE")) {
            ((ResponseEventExt)object).setOriginalTransaction(this.sipStack.getForkedTransaction(sIPResponse.getTransactionId()));
        }
        sipProviderImpl.handleEvent((EventObject)object, (SIPTransaction)serializable);
    }

    @Override
    public void processResponse(SIPResponse sIPResponse, MessageChannel object, SIPDialog serializable) {
        Object object2;
        StringBuilder stringBuilder;
        if (this.sipStack.isLoggingEnabled()) {
            object2 = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("PROCESSING INCOMING RESPONSE");
            ((StringBuilder)object).append(sIPResponse.encodeMessage());
            object2.logDebug(((StringBuilder)object).toString());
        }
        if (this.listeningPoint == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("Dropping message: No listening point registered!");
            }
            return;
        }
        if (this.sipStack.checkBranchId() && !Utils.getInstance().responseBelongsToUs(sIPResponse)) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("Dropping response - topmost VIA header does not originate from this stack");
            }
            return;
        }
        object2 = this.listeningPoint.getProvider();
        if (object2 == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("Dropping message:  no provider");
            }
            return;
        }
        if (((SipProviderImpl)object2).getSipListener() == null) {
            if (this.sipStack.isLoggingEnabled()) {
                this.sipStack.getStackLogger().logError("No listener -- dropping response!");
            }
            return;
        }
        object = (SIPClientTransaction)this.transactionChannel;
        Object object3 = ((SipProviderImpl)object2).sipStack;
        if (this.sipStack.isLoggingEnabled()) {
            object3 = ((SIPTransactionStack)object3).getStackLogger();
            stringBuilder = new StringBuilder();
            stringBuilder.append("Transaction = ");
            stringBuilder.append(object);
            object3.logDebug(stringBuilder.toString());
        }
        if (object == null) {
            if (serializable != null) {
                boolean bl;
                if (sIPResponse.getStatusCode() / 100 != 2) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("Response is not a final response and dialog is found for response -- dropping response!");
                    }
                    return;
                }
                if (((SIPDialog)serializable).getState() == DialogState.TERMINATED) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("Dialog is terminated -- dropping response!");
                    }
                    return;
                }
                boolean bl2 = bl = false;
                if (((SIPDialog)serializable).isAckSeen()) {
                    bl2 = bl;
                    if (((SIPDialog)serializable).getLastAckSent() != null) {
                        bl2 = bl;
                        if (((SIPDialog)serializable).getLastAckSent().getCSeq().getSeqNumber() == sIPResponse.getCSeq().getSeqNumber()) {
                            bl2 = true;
                        }
                    }
                }
                if (bl2 && sIPResponse.getCSeq().getMethod().equals(((SIPDialog)serializable).getMethod())) {
                    try {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("Retransmission of OK detected: Resending last ACK");
                        }
                        ((SIPDialog)serializable).resendAck();
                        return;
                    }
                    catch (SipException sipException) {
                        this.sipStack.getStackLogger().logError("could not resend ack", sipException);
                    }
                }
            }
            if (this.sipStack.isLoggingEnabled()) {
                object3 = this.sipStack.getStackLogger();
                stringBuilder = new StringBuilder();
                stringBuilder.append("could not find tx, handling statelessly Dialog =  ");
                stringBuilder.append(serializable);
                object3.logDebug(stringBuilder.toString());
            }
            serializable = new ResponseEventExt(object2, (ClientTransactionExt)object, (Dialog)serializable, sIPResponse);
            if (sIPResponse.getCSeqHeader().getMethod().equals("INVITE")) {
                ((ResponseEventExt)serializable).setOriginalTransaction(this.sipStack.getForkedTransaction(sIPResponse.getTransactionId()));
            }
            ((SipProviderImpl)object2).handleEvent((EventObject)serializable, (SIPTransaction)object);
            return;
        }
        object3 = new ResponseEventExt(object2, (ClientTransactionExt)object, (Dialog)serializable, sIPResponse);
        if (sIPResponse.getCSeqHeader().getMethod().equals("INVITE")) {
            ((ResponseEventExt)object3).setOriginalTransaction(this.sipStack.getForkedTransaction(sIPResponse.getTransactionId()));
        }
        if (serializable != null && sIPResponse.getStatusCode() != 100) {
            ((SIPDialog)serializable).setLastResponse((SIPTransaction)object, sIPResponse);
            ((SIPClientTransaction)object).setDialog((SIPDialog)serializable, ((SIPDialog)serializable).getDialogId());
        }
        ((SipProviderImpl)object2).handleEvent((EventObject)object3, (SIPTransaction)object);
    }
}

