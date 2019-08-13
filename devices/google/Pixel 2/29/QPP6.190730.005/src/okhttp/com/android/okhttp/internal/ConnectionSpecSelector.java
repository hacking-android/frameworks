/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal;

import com.android.okhttp.ConnectionSpec;
import com.android.okhttp.internal.Internal;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ProtocolException;
import java.net.UnknownServiceException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLProtocolException;
import javax.net.ssl.SSLSocket;

public final class ConnectionSpecSelector {
    private final List<ConnectionSpec> connectionSpecs;
    private boolean isFallback;
    private boolean isFallbackPossible;
    private int nextModeIndex = 0;

    public ConnectionSpecSelector(List<ConnectionSpec> list) {
        this.connectionSpecs = list;
    }

    private boolean isFallbackPossible(SSLSocket sSLSocket) {
        for (int i = this.nextModeIndex; i < this.connectionSpecs.size(); ++i) {
            if (!this.connectionSpecs.get(i).isCompatible(sSLSocket)) continue;
            return true;
        }
        return false;
    }

    public ConnectionSpec configureSecureSocket(SSLSocket sSLSocket) throws IOException {
        Object object;
        ConnectionSpec connectionSpec = null;
        int n = this.nextModeIndex;
        int n2 = this.connectionSpecs.size();
        do {
            object = connectionSpec;
            if (n >= n2) break;
            object = this.connectionSpecs.get(n);
            if (((ConnectionSpec)object).isCompatible(sSLSocket)) {
                this.nextModeIndex = n + 1;
                break;
            }
            ++n;
        } while (true);
        if (object != null) {
            this.isFallbackPossible = this.isFallbackPossible(sSLSocket);
            Internal.instance.apply((ConnectionSpec)object, sSLSocket, this.isFallback);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to find acceptable protocols. isFallback=");
        ((StringBuilder)object).append(this.isFallback);
        ((StringBuilder)object).append(", modes=");
        ((StringBuilder)object).append(this.connectionSpecs);
        ((StringBuilder)object).append(", supported protocols=");
        ((StringBuilder)object).append(Arrays.toString(sSLSocket.getEnabledProtocols()));
        throw new UnknownServiceException(((StringBuilder)object).toString());
    }

    public boolean connectionFailed(IOException iOException) {
        boolean bl = true;
        this.isFallback = true;
        if (!this.isFallbackPossible) {
            return false;
        }
        if (iOException instanceof ProtocolException) {
            return false;
        }
        if (iOException instanceof InterruptedIOException) {
            return false;
        }
        if (iOException instanceof SSLHandshakeException && iOException.getCause() instanceof CertificateException) {
            return false;
        }
        if (iOException instanceof SSLPeerUnverifiedException) {
            return false;
        }
        boolean bl2 = bl;
        if (!(iOException instanceof SSLHandshakeException)) {
            bl2 = iOException instanceof SSLProtocolException ? bl : false;
        }
        return bl2;
    }
}

