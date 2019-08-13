/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.compat.UnsupportedAppUsage
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.AbstractSessionContext;
import com.android.org.conscrypt.NativeSslSession;
import com.android.org.conscrypt.SSLClientSessionCache;
import com.android.org.conscrypt.SSLParametersImpl;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSession;

public final class ClientSessionContext
extends AbstractSessionContext {
    private SSLClientSessionCache persistentCache;
    private final Map<HostAndPort, List<NativeSslSession>> sessionsByHostAndPort = new HashMap<HostAndPort, List<NativeSslSession>>();

    ClientSessionContext() {
        super(10);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private NativeSslSession getSession(String object, int n) {
        if (object == null) {
            return null;
        }
        HostAndPort hostAndPort = new HostAndPort((String)object, n);
        NativeSslSession nativeSslSession = null;
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        // MONITORENTER : map
        List<NativeSslSession> list = this.sessionsByHostAndPort.get(hostAndPort);
        Object object2 = nativeSslSession;
        if (list != null) {
            object2 = nativeSslSession;
            if (list.size() > 0) {
                object2 = list.get(0);
            }
        }
        // MONITOREXIT : map
        if (object2 != null && object2.isValid()) {
            return object2;
        }
        object2 = this.persistentCache;
        if (object2 == null) return null;
        if ((object2 = object2.getSessionData((String)object, n)) == null) return null;
        if ((object = NativeSslSession.newInstance(this, object2, (String)object, n)) == null) return null;
        if (!((NativeSslSession)object).isValid()) return null;
        this.putSession(hostAndPort, (NativeSslSession)object);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void putSession(HostAndPort hostAndPort, NativeSslSession nativeSslSession) {
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            ArrayList<NativeSslSession> arrayList;
            ArrayList<NativeSslSession> arrayList2 = arrayList = this.sessionsByHostAndPort.get(hostAndPort);
            if (arrayList == null) {
                arrayList2 = new ArrayList<NativeSslSession>();
                this.sessionsByHostAndPort.put(hostAndPort, arrayList2);
            }
            if (arrayList2.size() > 0 && ((NativeSslSession)arrayList2.get(0)).isSingleUse() != nativeSslSession.isSingleUse()) {
                while (!arrayList2.isEmpty()) {
                    this.removeSession((NativeSslSession)arrayList2.get(0));
                }
                this.sessionsByHostAndPort.put(hostAndPort, arrayList2);
            }
            arrayList2.add(nativeSslSession);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeSession(HostAndPort hostAndPort, NativeSslSession nativeSslSession) {
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            List<NativeSslSession> list = this.sessionsByHostAndPort.get(hostAndPort);
            if (list != null) {
                list.remove(nativeSslSession);
                if (list.isEmpty()) {
                    this.sessionsByHostAndPort.remove(hostAndPort);
                }
            }
            return;
        }
    }

    NativeSslSession getCachedSession(String object, int n, SSLParametersImpl arrstring) {
        synchronized (this) {
            int n2;
            boolean bl;
            block16 : {
                if (object == null) {
                    return null;
                }
                object = this.getSession((String)object, n);
                if (object != null) break block16;
                return null;
            }
            String string = ((NativeSslSession)object).getProtocol();
            int n3 = 0;
            Object object2 = arrstring.enabledProtocols;
            int n4 = ((String[])object2).length;
            int n5 = 0;
            n = 0;
            do {
                block17 : {
                    n2 = n3;
                    if (n >= n4) break;
                    bl = string.equals(object2[n]);
                    if (!bl) break block17;
                    n2 = 1;
                    break;
                }
                ++n;
            } while (true);
            if (n2 == 0) {
                return null;
            }
            object2 = ((NativeSslSession)object).getCipherSuite();
            n3 = 0;
            arrstring = arrstring.getEnabledCipherSuites();
            n4 = arrstring.length;
            n2 = n5;
            do {
                block18 : {
                    n = n3;
                    if (n2 >= n4) break;
                    bl = ((String)object2).equals(arrstring[n2]);
                    if (!bl) break block18;
                    n = 1;
                    break;
                }
                ++n2;
            } while (true);
            if (n == 0) {
                return null;
            }
            if (((NativeSslSession)object).isSingleUse()) {
                this.removeSession((NativeSslSession)object);
            }
            return object;
        }
    }

    @Override
    NativeSslSession getSessionFromPersistentCache(byte[] arrby) {
        return null;
    }

    @Override
    void onBeforeAddSession(NativeSslSession nativeSslSession) {
        byte[] arrby = nativeSslSession.getPeerHost();
        int n = nativeSslSession.getPeerPort();
        if (arrby == null) {
            return;
        }
        this.putSession(new HostAndPort((String)arrby, n), nativeSslSession);
        if (this.persistentCache != null && !nativeSslSession.isSingleUse() && (arrby = nativeSslSession.toBytes()) != null) {
            this.persistentCache.putSessionData(nativeSslSession.toSSLSession(), arrby);
        }
    }

    @Override
    void onBeforeRemoveSession(NativeSslSession nativeSslSession) {
        String string = nativeSslSession.getPeerHost();
        if (string == null) {
            return;
        }
        this.removeSession(new HostAndPort(string, nativeSslSession.getPeerPort()), nativeSslSession);
    }

    @UnsupportedAppUsage
    public void setPersistentCache(SSLClientSessionCache sSLClientSessionCache) {
        this.persistentCache = sSLClientSessionCache;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    int size() {
        int n = 0;
        Map<HostAndPort, List<NativeSslSession>> map = this.sessionsByHostAndPort;
        synchronized (map) {
            Iterator<List<NativeSslSession>> iterator = this.sessionsByHostAndPort.values().iterator();
            while (iterator.hasNext()) {
                n += iterator.next().size();
            }
            return n;
        }
    }

    private static final class HostAndPort {
        final String host;
        final int port;

        HostAndPort(String string, int n) {
            this.host = string;
            this.port = n;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof HostAndPort;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (HostAndPort)object;
            bl = bl2;
            if (this.host.equals(((HostAndPort)object).host)) {
                bl = bl2;
                if (this.port == ((HostAndPort)object).port) {
                    bl = true;
                }
            }
            return bl;
        }

        public int hashCode() {
            return this.host.hashCode() * 31 + this.port;
        }
    }

}

