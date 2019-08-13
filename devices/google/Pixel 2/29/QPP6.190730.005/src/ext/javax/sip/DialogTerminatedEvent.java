/*
 * Decompiled with CFR 0.145.
 */
package javax.sip;

import java.util.EventObject;
import javax.sip.Dialog;

public class DialogTerminatedEvent
extends EventObject {
    private Dialog mDialog;

    public DialogTerminatedEvent(Object object, Dialog dialog) {
        super(object);
        this.mDialog = dialog;
    }

    public Dialog getDialog() {
        return this.mDialog;
    }
}

