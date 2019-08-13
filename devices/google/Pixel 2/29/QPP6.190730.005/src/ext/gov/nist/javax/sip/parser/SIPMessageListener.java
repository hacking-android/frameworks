/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.parser.ParseExceptionListener;

public interface SIPMessageListener
extends ParseExceptionListener {
    public void processMessage(SIPMessage var1) throws Exception;
}

