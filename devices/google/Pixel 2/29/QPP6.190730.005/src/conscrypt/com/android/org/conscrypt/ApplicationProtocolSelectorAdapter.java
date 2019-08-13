/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ApplicationProtocolSelector;
import com.android.org.conscrypt.Preconditions;
import com.android.org.conscrypt.SSLUtils;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocket;

final class ApplicationProtocolSelectorAdapter {
    private static final int NO_PROTOCOL_SELECTED = -1;
    private final SSLEngine engine;
    private final ApplicationProtocolSelector selector;
    private final SSLSocket socket;

    ApplicationProtocolSelectorAdapter(SSLEngine sSLEngine, ApplicationProtocolSelector applicationProtocolSelector) {
        this.engine = Preconditions.checkNotNull(sSLEngine, "engine");
        this.socket = null;
        this.selector = Preconditions.checkNotNull(applicationProtocolSelector, "selector");
    }

    ApplicationProtocolSelectorAdapter(SSLSocket sSLSocket, ApplicationProtocolSelector applicationProtocolSelector) {
        this.engine = null;
        this.socket = Preconditions.checkNotNull(sSLSocket, "socket");
        this.selector = Preconditions.checkNotNull(applicationProtocolSelector, "selector");
    }

    int selectApplicationProtocol(byte[] object) {
        if (object != null && ((byte[])object).length != 0) {
            Object object2 = Arrays.asList(SSLUtils.decodeProtocols((byte[])object));
            object = this.engine;
            object = object != null ? this.selector.selectApplicationProtocol((SSLEngine)object, (List<String>)object2) : this.selector.selectApplicationProtocol(this.socket, (List<String>)object2);
            if (object != null && !((String)object).isEmpty()) {
                int n = 0;
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    String string = (String)object2.next();
                    if (((String)object).equals(string)) {
                        return n;
                    }
                    n += string.length() + 1;
                }
                return -1;
            }
            return -1;
        }
        return -1;
    }
}

