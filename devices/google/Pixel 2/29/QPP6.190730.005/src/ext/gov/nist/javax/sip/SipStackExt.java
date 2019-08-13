/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import gov.nist.core.net.AddressResolver;
import gov.nist.javax.sip.clientauthutils.AccountManager;
import gov.nist.javax.sip.clientauthutils.AuthenticationHelper;
import gov.nist.javax.sip.clientauthutils.SecureAccountManager;
import gov.nist.javax.sip.header.extensions.JoinHeader;
import gov.nist.javax.sip.header.extensions.ReplacesHeader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.util.Collection;
import javax.sip.Dialog;
import javax.sip.SipStack;
import javax.sip.header.HeaderFactory;

public interface SipStackExt
extends SipStack {
    public AuthenticationHelper getAuthenticationHelper(AccountManager var1, HeaderFactory var2);

    @Override
    public Collection<Dialog> getDialogs();

    public Dialog getJoinDialog(JoinHeader var1);

    public Dialog getReplacesDialog(ReplacesHeader var1);

    public AuthenticationHelper getSecureAuthenticationHelper(SecureAccountManager var1, HeaderFactory var2);

    public SocketAddress obtainLocalAddress(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException;

    public void setAddressResolver(AddressResolver var1);

    public void setEnabledCipherSuites(String[] var1);
}

