/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.message.SIPResponse;
import gov.nist.javax.sip.stack.MessageChannel;
import gov.nist.javax.sip.stack.ServerRequestInterface;
import gov.nist.javax.sip.stack.ServerResponseInterface;

public interface StackMessageFactory {
    public ServerRequestInterface newSIPServerRequest(SIPRequest var1, MessageChannel var2);

    public ServerResponseInterface newSIPServerResponse(SIPResponse var1, MessageChannel var2);
}

