/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.EventObject;
import javax.sip.ClientTransaction;
import javax.sip.ServerTransaction;

public class TransactionTerminatedEvent
extends EventObject {
    private ClientTransaction mClientTransaction;
    private boolean mIsServerTransaction;
    private ServerTransaction mServerTransaction;

    public TransactionTerminatedEvent(Object object, ClientTransaction clientTransaction) {
        super(object);
        this.mClientTransaction = clientTransaction;
        this.mIsServerTransaction = false;
    }

    public TransactionTerminatedEvent(Object object, ServerTransaction serverTransaction) {
        super(object);
        this.mServerTransaction = serverTransaction;
        this.mIsServerTransaction = true;
    }

    public ClientTransaction getClientTransaction() {
        return this.mClientTransaction;
    }

    public ServerTransaction getServerTransaction() {
        return this.mServerTransaction;
    }

    public boolean isServerTransaction() {
        return this.mIsServerTransaction;
    }
}

