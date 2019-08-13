/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.StackLogger;
import gov.nist.javax.sip.DialogFilter;
import gov.nist.javax.sip.ListeningPointImpl;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.MessageProcessor;
import gov.nist.javax.sip.stack.SIPTransaction;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import gov.nist.javax.sip.stack.ServerResponseInterface;
import gov.nist.javax.sip.stack.StackMessageFactory;
import javax.sip.TransactionState;

class NistSipMessageFactoryImpl
implements StackMessageFactory {
    private SipStackImpl sipStack;

    public NistSipMessageFactoryImpl(SipStackImpl sipStackImpl) {
        this.sipStack = sipStackImpl;
    }

    @Override
    public ServerRequestInterface newSIPServerRequest(SIPRequest sIPRequest, MessageChannel messageChannel) {
        if (messageChannel != null && sIPRequest != null) {
            DialogFilter dialogFilter = new DialogFilter((SipStackImpl)messageChannel.getSIPStack());
            if (messageChannel instanceof SIPTransaction) {
                dialogFilter.transactionChannel = (SIPTransaction)messageChannel;
            }
            dialogFilter.listeningPoint = messageChannel.getMessageProcessor().getListeningPoint();
            if (dialogFilter.listeningPoint == null) {
                return null;
            }
            if (this.sipStack.isLoggingEnabled()) {
                StackLogger stackLogger = this.sipStack.getStackLogger();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Returning request interface for ");
                stringBuilder.append(sIPRequest.getFirstLine());
                stringBuilder.append(" ");
                stringBuilder.append(dialogFilter);
                stringBuilder.append(" messageChannel = ");
                stringBuilder.append(messageChannel);
                stackLogger.logDebug(stringBuilder.toString());
            }
            return dialogFilter;
        }
        throw new IllegalArgumentException("Null Arg!");
    }

    @Override
    public ServerResponseInterface newSIPServerResponse(SIPResponse object, MessageChannel object2) {
        Object object3 = ((MessageChannel)object2).getSIPStack();
        object3 = ((SIPTransactionStack)object3).findTransaction((SIPMessage)object, false);
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found Transaction ");
            stringBuilder.append(object3);
            stringBuilder.append(" for ");
            stringBuilder.append(object);
            stackLogger.logDebug(stringBuilder.toString());
        }
        if (object3 != null) {
            if (((SIPTransaction)object3).getState() == null) {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("Dropping response - null transaction state");
                }
                return null;
            }
            if (TransactionState.COMPLETED == ((SIPTransaction)object3).getState() && ((SIPResponse)object).getStatusCode() / 100 == 1) {
                if (this.sipStack.isLoggingEnabled()) {
                    object3 = this.sipStack.getStackLogger();
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Dropping response - late arriving ");
                    ((StringBuilder)object2).append(((SIPResponse)object).getStatusCode());
                    object3.logDebug(((StringBuilder)object2).toString());
                }
                return null;
            }
        }
        object = new DialogFilter(this.sipStack);
        ((DialogFilter)object).transactionChannel = object3;
        ((DialogFilter)object).listeningPoint = ((MessageChannel)object2).getMessageProcessor().getListeningPoint();
        return object;
    }
}

