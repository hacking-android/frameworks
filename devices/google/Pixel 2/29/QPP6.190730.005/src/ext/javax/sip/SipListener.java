/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import javax.sip.DialogTerminatedEvent;
import javax.sip.IOExceptionEvent;
import javax.sip.RequestEvent;
import javax.sip.ResponseEvent;
import javax.sip.TimeoutEvent;
import javax.sip.TransactionTerminatedEvent;

public interface SipListener {
    public void processDialogTerminated(DialogTerminatedEvent var1);

    public void processIOException(IOExceptionEvent var1);

    public void processRequest(RequestEvent var1);

    public void processResponse(ResponseEvent var1);

    public void processTimeout(TimeoutEvent var1);

    public void processTransactionTerminated(TransactionTerminatedEvent var1);
}

