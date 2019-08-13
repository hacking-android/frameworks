/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.LogLevels;
import gov.nist.javax.sip.message.SIPMessage;
import java.util.Properties;
import javax.sip.SipStack;

public interface ServerLogger
extends LogLevels {
    public void closeLogFile();

    public void logException(Exception var1);

    public void logMessage(SIPMessage var1, String var2, String var3, String var4, boolean var5);

    public void logMessage(SIPMessage var1, String var2, String var3, String var4, boolean var5, long var6);

    public void logMessage(SIPMessage var1, String var2, String var3, boolean var4, long var5);

    public void setSipStack(SipStack var1);

    public void setStackProperties(Properties var1);
}

