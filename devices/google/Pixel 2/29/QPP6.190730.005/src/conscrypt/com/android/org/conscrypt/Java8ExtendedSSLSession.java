/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ExternalSession;
import com.android.org.conscrypt.Java7ExtendedSSLSession;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;

class Java8ExtendedSSLSession
extends Java7ExtendedSSLSession {
    public Java8ExtendedSSLSession(ExternalSession externalSession) {
        super(externalSession);
    }

    @Override
    public final List<SNIServerName> getRequestedServerNames() {
        String string = this.delegate.getRequestedServerName();
        if (string == null) {
            return null;
        }
        return Collections.singletonList(new SNIHostName(string));
    }
}

