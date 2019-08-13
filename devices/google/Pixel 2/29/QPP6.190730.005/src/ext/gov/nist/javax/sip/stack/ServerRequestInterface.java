/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.message.SIPRequest;
import gov.nist.javax.sip.stack.MessageChannel;

public interface ServerRequestInterface {
    public void processRequest(SIPRequest var1, MessageChannel var2);
}

