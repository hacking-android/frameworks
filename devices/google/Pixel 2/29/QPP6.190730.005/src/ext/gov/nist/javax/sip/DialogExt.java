/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import javax.sip.Dialog;
import javax.sip.SipProvider;

public interface DialogExt
extends Dialog {
    public void disableSequenceNumberValidation();

    @Override
    public SipProvider getSipProvider();

    @Override
    public void setBackToBackUserAgent();
}

