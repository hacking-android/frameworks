/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.message.SIPMessage;
import java.text.ParseException;

public interface ParseExceptionListener {
    public void handleException(ParseException var1, SIPMessage var2, Class var3, String var4, String var5) throws ParseException;
}

