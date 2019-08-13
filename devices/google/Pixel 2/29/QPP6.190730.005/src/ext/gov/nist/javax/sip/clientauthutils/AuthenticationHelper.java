/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import javax.sip.ClientTransaction;
import javax.sip.SipException;
import javax.sip.SipProvider;
import javax.sip.message.Request;
import javax.sip.message.Response;

public interface AuthenticationHelper {
    public ClientTransaction handleChallenge(Response var1, ClientTransaction var2, SipProvider var3, int var4) throws SipException, NullPointerException;

    public void removeCachedAuthenticationHeaders(String var1);

    public void setAuthenticationHeaders(Request var1);
}

