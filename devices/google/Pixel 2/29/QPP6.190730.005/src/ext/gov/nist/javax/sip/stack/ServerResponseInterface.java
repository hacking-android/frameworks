/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.SIPDialog;

public interface ServerResponseInterface {
    public void processResponse(SIPResponse var1, MessageChannel var2);

    public void processResponse(SIPResponse var1, MessageChannel var2, SIPDialog var3);
}

