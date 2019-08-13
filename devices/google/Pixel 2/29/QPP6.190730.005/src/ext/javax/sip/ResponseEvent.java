/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.EventObject;
import javax.sip.ClientTransaction;
import javax.sip.Dialog;
import javax.sip.message.Response;

public class ResponseEvent
extends EventObject {
    private ClientTransaction mClientTransaction;
    private Dialog mDialog;
    private Response mResponse;

    public ResponseEvent(Object object, ClientTransaction clientTransaction, Dialog dialog, Response response) {
        super(object);
        this.mDialog = dialog;
        this.mResponse = response;
        this.mClientTransaction = clientTransaction;
    }

    public ClientTransaction getClientTransaction() {
        return this.mClientTransaction;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public Response getResponse() {
        return this.mResponse;
    }
}

