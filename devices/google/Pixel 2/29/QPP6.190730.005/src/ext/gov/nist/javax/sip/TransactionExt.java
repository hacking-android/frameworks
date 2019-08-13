/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip;

import java.security.cert.Certificate;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.sip.SipProvider;
import javax.sip.Transaction;

public interface TransactionExt
extends Transaction {
    public String getCipherSuite() throws UnsupportedOperationException;

    @Override
    public String getHost();

    public Certificate[] getLocalCertificates() throws UnsupportedOperationException;

    @Override
    public String getPeerAddress();

    public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException;

    @Override
    public int getPeerPort();

    @Override
    public int getPort();

    @Override
    public SipProvider getSipProvider();

    @Override
    public String getTransport();
}

