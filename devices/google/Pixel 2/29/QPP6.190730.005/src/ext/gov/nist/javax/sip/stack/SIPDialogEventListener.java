/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.stack.SIPDialogErrorEvent;
import java.util.EventListener;

public interface SIPDialogEventListener
extends EventListener {
    public void dialogErrorEvent(SIPDialogErrorEvent var1);
}

