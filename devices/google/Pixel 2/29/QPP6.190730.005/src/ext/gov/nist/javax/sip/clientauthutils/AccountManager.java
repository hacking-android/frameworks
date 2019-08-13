/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import gov.nist.javax.sip.clientauthutils.UserCredentials;
import javax.sip.ClientTransaction;

public interface AccountManager {
    public UserCredentials getCredentials(ClientTransaction var1, String var2);
}

