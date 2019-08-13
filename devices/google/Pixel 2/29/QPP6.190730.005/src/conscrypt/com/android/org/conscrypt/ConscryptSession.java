/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

interface ConscryptSession
extends SSLSession {
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException;

    public byte[] getPeerSignedCertificateTimestamp();

    public String getRequestedServerName();

    public List<byte[]> getStatusResponses();
}

