/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.ConscryptEngineSocket;
import com.android.org.conscrypt.SSLParametersImpl;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.function.BiFunction;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

final class Java8EngineSocket
extends ConscryptEngineSocket {
    private BiFunction<SSLSocket, List<String>, String> selector;

    Java8EngineSocket(SSLParametersImpl sSLParametersImpl) throws IOException {
        super(sSLParametersImpl);
    }

    Java8EngineSocket(String string, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n, sSLParametersImpl);
    }

    Java8EngineSocket(String string, int n, InetAddress inetAddress, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(string, n, inetAddress, n2, sSLParametersImpl);
    }

    Java8EngineSocket(InetAddress inetAddress, int n, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n, sSLParametersImpl);
    }

    Java8EngineSocket(InetAddress inetAddress, int n, InetAddress inetAddress2, int n2, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(inetAddress, n, inetAddress2, n2, sSLParametersImpl);
    }

    Java8EngineSocket(Socket socket, String string, int n, boolean bl, SSLParametersImpl sSLParametersImpl) throws IOException {
        super(socket, string, n, bl, sSLParametersImpl);
    }

    private static ApplicationProtocolSelector toApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> object) {
        object = object == null ? null : new ApplicationProtocolSelector((BiFunction)object){
            final /* synthetic */ BiFunction val$selector;
            {
                this.val$selector = biFunction;
            }

            @Override
            public String selectApplicationProtocol(SSLEngine sSLEngine, List<String> list) {
                throw new UnsupportedOperationException();
            }

            @Override
            public String selectApplicationProtocol(SSLSocket sSLSocket, List<String> list) {
                return (String)this.val$selector.apply(sSLSocket, list);
            }
        };
        return object;
    }

    public BiFunction<SSLSocket, List<String>, String> getHandshakeApplicationProtocolSelector() {
        return this.selector;
    }

    public void setHandshakeApplicationProtocolSelector(BiFunction<SSLSocket, List<String>, String> biFunction) {
        this.selector = biFunction;
        this.setApplicationProtocolSelector(Java8EngineSocket.toApplicationProtocolSelector(biFunction));
    }

}

