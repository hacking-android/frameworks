/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.ClientTransactionExt;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.ResponseEvent;
import javax.sip.message.Response;

public class ResponseEventExt
extends ResponseEvent {
    private ClientTransactionExt m_originalTransaction;

    public ResponseEventExt(Object object, ClientTransactionExt clientTransactionExt, Dialog dialog, Response response) {
        super(object, clientTransactionExt, dialog, response);
        this.m_originalTransaction = clientTransactionExt;
    }

    public ClientTransactionExt getOriginalTransaction() {
        return this.m_originalTransaction;
    }

    public boolean isForkedResponse() {
        boolean bl = super.getClientTransaction() == null && this.m_originalTransaction != null;
        return bl;
    }

    public void setOriginalTransaction(ClientTransactionExt clientTransactionExt) {
        this.m_originalTransaction = clientTransactionExt;
    }
}

