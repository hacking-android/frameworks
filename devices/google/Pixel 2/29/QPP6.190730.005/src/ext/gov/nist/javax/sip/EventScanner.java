/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.StackLogger;
import gov.nist.javax.sip.DialogTimeoutEvent;
import gov.nist.javax.sip.EventWrapper;
import gov.nist.javax.sip.SipListenerExt;
import gov.nist.javax.sip.SipProviderImpl;
import gov.nist.javax.sip.SipStackImpl;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.SIPClientTransaction;
import gov.nist.javax.sip.stack.SIPDialog;
import gov.nist.javax.sip.stack.SIPServerTransaction;
import gov.nist.javax.sip.stack.SIPTransaction;
import java.io.Serializable;
import java.util.EventObject;
import java.util.LinkedList;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.DialogState;
import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.ServerTransaction;
import javax.sip.SipListener;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionState;
import javax.sip.TransactionTerminatedEvent;
import javax.sip.header.CSeqHeader;
import javax.sip.message.Request;
import javax.sip.message.Response;

class EventScanner
implements Runnable {
    private int[] eventMutex = new int[]{0};
    private boolean isStopped;
    private LinkedList pendingEvents = new LinkedList();
    private int refCount;
    private SipStackImpl sipStack;

    public EventScanner(SipStackImpl sipStackImpl) {
        Thread thread = new Thread(this);
        thread.setDaemon(false);
        this.sipStack = sipStackImpl;
        thread.setName("EventScannerThread");
        thread.start();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addEvent(EventWrapper eventWrapper) {
        Object object;
        if (this.sipStack.isLoggingEnabled()) {
            StackLogger stackLogger = this.sipStack.getStackLogger();
            object = new StringBuilder();
            ((StringBuilder)object).append("addEvent ");
            ((StringBuilder)object).append(eventWrapper);
            stackLogger.logDebug(((StringBuilder)object).toString());
        }
        object = this.eventMutex;
        synchronized (object) {
            this.pendingEvents.add(eventWrapper);
            this.eventMutex.notify();
            return;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void deliverEvent(EventWrapper var1_1) {
        block52 : {
            var2_9 = var1_1.sipEvent;
            if (this.sipStack.isLoggingEnabled()) {
                var3_10 = this.sipStack.getStackLogger();
                var4_11 = new StringBuilder();
                var4_11.append("sipEvent = ");
                var4_11.append(var2_9);
                var4_11.append("source = ");
                var4_11.append(var2_9.getSource());
                var3_10.logDebug(var4_11.toString());
            }
            var4_11 = var2_9 instanceof IOExceptionEvent == false ? ((SipProviderImpl)var2_9.getSource()).getSipListener() : this.sipStack.getSipListener();
            if (!(var2_9 instanceof RequestEvent)) break block52;
            try {
                var3_10 = (SIPRequest)((RequestEvent)var2_9).getRequest();
                if (this.sipStack.isLoggingEnabled()) {
                    var5_16 = this.sipStack.getStackLogger();
                    var6_18 = new StringBuilder();
                    var6_18.append("deliverEvent : ");
                    var6_18.append(var3_10.getFirstLine());
                    var6_18.append(" transaction ");
                    var6_18.append(var1_1.transaction);
                    var6_18.append(" sipEvent.serverTx = ");
                    var6_18.append(((RequestEvent)var2_9).getServerTransaction());
                    var5_16.logDebug(var6_18.toString());
                }
                if ((var5_16 = (SIPServerTransaction)this.sipStack.findTransaction((SIPMessage)var3_10, true)) == null || var5_16.passToListener()) ** GOTO lbl48
                if (var3_10.getMethod().equals("ACK") && var5_16.isInviteTransaction() && (var5_16.getLastResponse().getStatusCode() / 100 == 2 || this.sipStack.isNon2XXAckPassedToListener())) {
                    if (this.sipStack.isLoggingEnabled()) {
                        this.sipStack.getStackLogger().logDebug("Detected broken client sending ACK with same branch! Passing...");
                    }
                } else {
                    if (this.sipStack.isLoggingEnabled() == false) return;
                    var4_11 = this.sipStack.getStackLogger();
                    var3_10 = new StringBuilder();
                    var3_10.append("transaction already exists! ");
                    var3_10.append(var5_16);
                    var4_11.logDebug(var3_10.toString());
                    return;
lbl48: // 1 sources:
                    if (this.sipStack.findPendingTransaction((SIPRequest)var3_10) != null) {
                        if (this.sipStack.isLoggingEnabled() == false) return;
                        this.sipStack.getStackLogger().logDebug("transaction already exists!!");
                        return;
                    }
                    var5_16 = (SIPServerTransaction)var1_1.transaction;
                    this.sipStack.putPendingTransaction((SIPServerTransaction)var5_16);
                }
                var3_10.setTransaction(var1_1.transaction);
                try {
                    if (this.sipStack.isLoggingEnabled()) {
                        var5_16 = this.sipStack.getStackLogger();
                        var6_18 = new StringBuilder();
                        var6_18.append("Calling listener ");
                        var6_18.append(var3_10.getFirstLine());
                        var5_16.logDebug(var6_18.toString());
                        var5_16 = this.sipStack.getStackLogger();
                        var6_18 = new StringBuilder();
                        var6_18.append("Calling listener ");
                        var6_18.append(var1_1.transaction);
                        var5_16.logDebug(var6_18.toString());
                    }
                    if (var4_11 != null) {
                        var4_11.processRequest((RequestEvent)var2_9);
                    }
                    if (this.sipStack.isLoggingEnabled()) {
                        var4_11 = this.sipStack.getStackLogger();
                        var5_16 = new StringBuilder();
                        var5_16.append("Done processing Message ");
                        var5_16.append(var3_10.getFirstLine());
                        var4_11.logDebug(var5_16.toString());
                    }
                    if (var1_1.transaction == null) return;
                    var4_11 = (SIPDialog)var1_1.transaction.getDialog();
                    if (var4_11 == null) return;
                    var4_11.requestConsumed();
                    return;
                }
                catch (Exception var4_12) {
                    this.sipStack.getStackLogger().logException(var4_12);
                    return;
                }
            }
            finally {
                if (this.sipStack.isLoggingEnabled()) {
                    var3_10 = this.sipStack.getStackLogger();
                    var4_11 = new StringBuilder();
                    var4_11.append("Done processing Message ");
                    var4_11.append(((SIPRequest)((RequestEvent)var2_9).getRequest()).getFirstLine());
                    var3_10.logDebug(var4_11.toString());
                }
                if (var1_1.transaction != null && ((SIPServerTransaction)var1_1.transaction).passToListener()) {
                    ((SIPServerTransaction)var1_1.transaction).releaseSem();
                }
                if (var1_1.transaction != null) {
                    this.sipStack.removePendingTransaction((SIPServerTransaction)var1_1.transaction);
                }
                if (var1_1.transaction.getOriginalRequest().getMethod().equals("ACK")) {
                    var1_1.transaction.setState(TransactionState.TERMINATED);
                }
            }
        }
        if (var2_9 instanceof ResponseEvent) {
            try {
                var5_17 = (ResponseEvent)var2_9;
                var3_10 = (SIPResponse)var5_17.getResponse();
                var5_17 = (SIPDialog)var5_17.getDialog();
                try {
                    if (this.sipStack.isLoggingEnabled()) {
                        var6_19 = this.sipStack.getStackLogger();
                        var7_20 = new StringBuilder();
                        var7_20.append("Calling listener for ");
                        var7_20.append(var3_10.getFirstLine());
                        var6_19.logDebug(var7_20.toString());
                    }
                    if (var4_11 != null) {
                        var6_19 = var1_1.transaction;
                        if (var6_19 != null) {
                            var6_19.setPassToListener();
                        }
                        var4_11.processResponse((ResponseEvent)var2_9);
                    }
                    if (!(var5_17 == null || var5_17.getState() != null && var5_17.getState().equals((Object)DialogState.TERMINATED) || var3_10.getStatusCode() != 481 && var3_10.getStatusCode() != 408)) {
                        if (this.sipStack.isLoggingEnabled()) {
                            this.sipStack.getStackLogger().logDebug("Removing dialog on 408 or 481 response");
                        }
                        var5_17.doDeferredDelete();
                    }
                    if (var3_10.getCSeq().getMethod().equals("INVITE") && var5_17 != null && var3_10.getStatusCode() == 200) {
                        if (this.sipStack.isLoggingEnabled()) {
                            var4_11 = this.sipStack.getStackLogger();
                            var2_9 = new StringBuilder();
                            var2_9.append("Warning! unacknowledged dialog. ");
                            var2_9.append((Object)var5_17.getState());
                            var4_11.logDebug(var2_9.toString());
                        }
                        var5_17.doDeferredDeleteIfNoAckSent(var3_10.getCSeq().getSeqNumber());
                    }
                }
                catch (Exception var4_14) {
                    this.sipStack.getStackLogger().logException(var4_14);
                }
                var4_11 = (SIPClientTransaction)var1_1.transaction;
                if (var4_11 == null) return;
                if (TransactionState.COMPLETED != var4_11.getState()) return;
                if (var4_11.getOriginalRequest() == null) return;
                if (var4_11.getOriginalRequest().getMethod().equals("INVITE") != false) return;
                var4_11.clearState();
                return;
            }
            finally {
                if (var1_1.transaction != null && var1_1.transaction.passToListener()) {
                    var1_1.transaction.releaseSem();
                }
            }
        }
        if (var2_9 instanceof TimeoutEvent) {
            if (var4_11 == null) return;
            try {
                var4_11.processTimeout((TimeoutEvent)var2_9);
                return;
            }
            catch (Exception var1_2) {
                this.sipStack.getStackLogger().logException(var1_2);
                return;
            }
        }
        if (var2_9 instanceof DialogTimeoutEvent) {
            if (var4_11 == null) return;
            try {
                if (var4_11 instanceof SipListenerExt == false) return;
                ((SipListenerExt)var4_11).processDialogTimeout((DialogTimeoutEvent)var2_9);
                return;
            }
            catch (Exception var1_3) {
                this.sipStack.getStackLogger().logException(var1_3);
                return;
            }
        }
        if (var2_9 instanceof IOExceptionEvent) {
            if (var4_11 == null) return;
            try {
                var4_11.processIOException((IOExceptionEvent)var2_9);
                return;
            }
            catch (Exception var1_4) {
                this.sipStack.getStackLogger().logException(var1_4);
                return;
            }
        }
        if (var2_9 instanceof TransactionTerminatedEvent) {
            try {
                if (this.sipStack.isLoggingEnabled()) {
                    this.sipStack.getStackLogger().logDebug("About to deliver transactionTerminatedEvent");
                    var1_1 = this.sipStack.getStackLogger();
                    var3_10 = new StringBuilder();
                    var3_10.append("tx = ");
                    var3_10.append(((TransactionTerminatedEvent)var2_9).getClientTransaction());
                    var1_1.logDebug(var3_10.toString());
                    var3_10 = this.sipStack.getStackLogger();
                    var1_1 = new StringBuilder();
                    var1_1.append("tx = ");
                    var1_1.append(((TransactionTerminatedEvent)var2_9).getServerTransaction());
                    var3_10.logDebug(var1_1.toString());
                }
                if (var4_11 == null) return;
                var4_11.processTransactionTerminated((TransactionTerminatedEvent)var2_9);
                return;
            }
            catch (Exception var1_5) {
                this.sipStack.getStackLogger().logException(var1_5);
                return;
            }
            catch (AbstractMethodError var1_6) {
                if (this.sipStack.isLoggingEnabled() == false) return;
                this.sipStack.getStackLogger().logWarning("Unable to call sipListener.processTransactionTerminated");
                return;
            }
        }
        if (!(var2_9 instanceof DialogTerminatedEvent)) {
            var4_11 = this.sipStack.getStackLogger();
            var1_1 = new StringBuilder();
            var1_1.append("bad event");
            var1_1.append(var2_9);
            var4_11.logFatalError(var1_1.toString());
            return;
        }
        if (var4_11 == null) return;
        try {
            var4_11.processDialogTerminated((DialogTerminatedEvent)var2_9);
            return;
        }
        catch (Exception var1_7) {
            this.sipStack.getStackLogger().logException(var1_7);
            return;
        }
        catch (AbstractMethodError var1_8) {
            if (this.sipStack.isLoggingEnabled() == false) return;
            this.sipStack.getStackLogger().logWarning("Unable to call sipListener.processDialogTerminated");
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void forceStop() {
        int[] arrn = this.eventMutex;
        synchronized (arrn) {
            this.isStopped = true;
            this.refCount = 0;
            this.eventMutex.notify();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void incrementRefcount() {
        int[] arrn = this.eventMutex;
        synchronized (arrn) {
            ++this.refCount;
            return;
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 13[DOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() {
        int[] arrn = this.eventMutex;
        synchronized (arrn) {
            if (this.refCount > 0) {
                --this.refCount;
            }
            if (this.refCount == 0) {
                this.isStopped = true;
                this.eventMutex.notify();
            }
            return;
        }
    }
}

