/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ByteArray;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeSslSession;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionContext;

abstract class AbstractSessionContext
implements SSLSessionContext {
    private static final int DEFAULT_SESSION_TIMEOUT_SECONDS = 28800;
    private volatile int maximumSize;
    private final Map<ByteArray, NativeSslSession> sessions = new LinkedHashMap<ByteArray, NativeSslSession>(){

        @Override
        protected boolean removeEldestEntry(Map.Entry<ByteArray, NativeSslSession> entry) {
            if (AbstractSessionContext.this.maximumSize > 0 && this.size() > AbstractSessionContext.this.maximumSize) {
                AbstractSessionContext.this.onBeforeRemoveSession(entry.getValue());
                return true;
            }
            return false;
        }
    };
    final long sslCtxNativePointer = NativeCrypto.SSL_CTX_new();
    private volatile int timeout = 28800;

    AbstractSessionContext(int n) {
        this.maximumSize = n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void trimToSize() {
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            int n = this.sessions.size();
            if (n > this.maximumSize) {
                n -= this.maximumSize;
                Iterator<NativeSslSession> iterator = this.sessions.values().iterator();
                while (n > 0) {
                    this.onBeforeRemoveSession(iterator.next());
                    iterator.remove();
                    --n;
                }
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void cacheSession(NativeSslSession nativeSslSession) {
        byte[] arrby = nativeSslSession.getId();
        if (arrby == null) return;
        if (arrby.length == 0) {
            return;
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            ByteArray byteArray = new ByteArray(arrby);
            if (this.sessions.containsKey(byteArray)) {
                this.removeSession(this.sessions.get(byteArray));
            }
            this.onBeforeAddSession(nativeSslSession);
            this.sessions.put(byteArray, nativeSslSession);
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            NativeCrypto.SSL_CTX_free(this.sslCtxNativePointer, this);
            return;
        }
        finally {
            super.finalize();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final Enumeration<byte[]> getIds() {
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            final Iterator<NativeSslSession> iterator = Arrays.asList(this.sessions.values().toArray(new NativeSslSession[this.sessions.size()])).iterator();
            return new Enumeration<byte[]>(){
                private NativeSslSession next;

                @Override
                public boolean hasMoreElements() {
                    if (this.next != null) {
                        return true;
                    }
                    while (iterator.hasNext()) {
                        NativeSslSession nativeSslSession = (NativeSslSession)iterator.next();
                        if (!nativeSslSession.isValid()) continue;
                        this.next = nativeSslSession;
                        return true;
                    }
                    this.next = null;
                    return false;
                }

                @Override
                public byte[] nextElement() {
                    if (this.hasMoreElements()) {
                        byte[] arrby = this.next.getId();
                        this.next = null;
                        return arrby;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public final SSLSession getSession(byte[] object) {
        if (object == null) throw new NullPointerException("sessionId");
        Object object2 = new ByteArray((byte[])object);
        object = this.sessions;
        // MONITORENTER : object
        object2 = this.sessions.get(object2);
        // MONITOREXIT : object
        if (object2 == null) return null;
        if (!((NativeSslSession)object2).isValid()) return null;
        return ((NativeSslSession)object2).toSSLSession();
    }

    @Override
    public final int getSessionCacheSize() {
        return this.maximumSize;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    final NativeSslSession getSessionFromCache(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        // MONITORENTER : map
        Map<ByteArray, NativeSslSession> map2 = this.sessions;
        Object object = new ByteArray(arrby);
        object = map2.get(object);
        // MONITOREXIT : map
        if (object == null) return this.getSessionFromPersistentCache(arrby);
        if (!((NativeSslSession)object).isValid()) return this.getSessionFromPersistentCache(arrby);
        if (!((NativeSslSession)object).isSingleUse()) return object;
        this.removeSession((NativeSslSession)object);
        return object;
    }

    abstract NativeSslSession getSessionFromPersistentCache(byte[] var1);

    @Override
    public final int getSessionTimeout() {
        return this.timeout;
    }

    abstract void onBeforeAddSession(NativeSslSession var1);

    abstract void onBeforeRemoveSession(NativeSslSession var1);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void removeSession(NativeSslSession object) {
        Object object2 = ((NativeSslSession)object).getId();
        if (object2 == null) return;
        if (((byte[])object2).length == 0) {
            return;
        }
        this.onBeforeRemoveSession((NativeSslSession)object);
        object2 = new ByteArray((byte[])object2);
        object = this.sessions;
        synchronized (object) {
            this.sessions.remove(object2);
            return;
        }
    }

    @Override
    public final void setSessionCacheSize(int n) throws IllegalArgumentException {
        if (n >= 0) {
            int n2 = this.maximumSize;
            this.maximumSize = n;
            if (n < n2) {
                this.trimToSize();
            }
            return;
        }
        throw new IllegalArgumentException("size < 0");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void setSessionTimeout(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("seconds < 0");
        }
        Map<ByteArray, NativeSslSession> map = this.sessions;
        synchronized (map) {
            this.timeout = n;
            if (n > 0) {
                NativeCrypto.SSL_CTX_set_timeout(this.sslCtxNativePointer, this, n);
            } else {
                NativeCrypto.SSL_CTX_set_timeout(this.sslCtxNativePointer, this, Integer.MAX_VALUE);
            }
            Iterator<NativeSslSession> iterator = this.sessions.values().iterator();
            while (iterator.hasNext()) {
                NativeSslSession nativeSslSession = iterator.next();
                if (nativeSslSession.isValid()) continue;
                this.onBeforeRemoveSession(nativeSslSession);
                iterator.remove();
            }
            return;
        }
    }

}

