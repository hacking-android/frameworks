/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.stack.SIPDialog;
import java.util.EventObject;

public class SIPDialogErrorEvent
extends EventObject {
    public static final int DIALOG_ACK_NOT_RECEIVED_TIMEOUT = 1;
    public static final int DIALOG_ACK_NOT_SENT_TIMEOUT = 2;
    public static final int DIALOG_REINVITE_TIMEOUT = 3;
    private int errorID;

    SIPDialogErrorEvent(SIPDialog sIPDialog, int n) {
        super(sIPDialog);
        this.errorID = n;
    }

    public int getErrorID() {
        return this.errorID;
    }
}

