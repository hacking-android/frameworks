/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.javax.sip.message.SIPMessage;

public interface RawMessageChannel {
    public void processMessage(SIPMessage var1) throws Exception;
}

