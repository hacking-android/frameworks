/*
 * Decompiled with CFR 0.145.
 */
package javax.net.ssl;

import java.util.List;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLSession;

public abstract class ExtendedSSLSession
implements SSLSession {
    public abstract String[] getLocalSupportedSignatureAlgorithms();

    public abstract String[] getPeerSupportedSignatureAlgorithms();

    public List<SNIServerName> getRequestedServerNames() {
        throw new UnsupportedOperationException();
    }
}

