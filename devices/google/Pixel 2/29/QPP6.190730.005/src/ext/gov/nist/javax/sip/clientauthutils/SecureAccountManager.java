/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.clientauthutils;

import gov.nist.javax.sip.clientauthutils.UserCredentialHash;
import javax.sip.ClientTransaction;

public interface SecureAccountManager {
    public UserCredentialHash getCredentialHash(ClientTransaction var1, String var2);
}

