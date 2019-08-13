/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeSslSession;
import com.android.org.conscrypt.SSLServerSessionCache;
import javax.net.ssl.SSLSession;

public final class ServerSessionContext
extends AbstractSessionContext {
    private SSLServerSessionCache persistentCache;

    ServerSessionContext() {
        super(100);
        NativeCrypto.SSL_CTX_set_session_id_context(this.sslCtxNativePointer, this, new byte[]{32});
    }

    @Override
    NativeSslSession getSessionFromPersistentCache(byte[] object) {
        SSLServerSessionCache sSLServerSessionCache = this.persistentCache;
        if (sSLServerSessionCache != null && (object = sSLServerSessionCache.getSessionData((byte[])object)) != null && (object = NativeSslSession.newInstance(this, (byte[])object, null, -1)) != null && ((NativeSslSession)object).isValid()) {
            this.cacheSession((NativeSslSession)object);
            return object;
        }
        return null;
    }

    @Override
    void onBeforeAddSession(NativeSslSession nativeSslSession) {
        byte[] arrby;
        if (this.persistentCache != null && (arrby = nativeSslSession.toBytes()) != null) {
            this.persistentCache.putSessionData(nativeSslSession.toSSLSession(), arrby);
        }
    }

    @Override
    void onBeforeRemoveSession(NativeSslSession nativeSslSession) {
    }

    public void setPersistentCache(SSLServerSessionCache sSLServerSessionCache) {
        this.persistentCache = sSLServerSessionCache;
    }
}

