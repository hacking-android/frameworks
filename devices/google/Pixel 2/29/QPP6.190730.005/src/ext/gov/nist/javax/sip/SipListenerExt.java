/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.javax.sip.DialogTimeoutEvent;
import javax.sip.SipListener;

public interface SipListenerExt
extends SipListener {
    public void processDialogTimeout(DialogTimeoutEvent var1);
}

