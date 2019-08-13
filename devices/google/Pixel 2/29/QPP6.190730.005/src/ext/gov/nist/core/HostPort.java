/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import gov.nist.core.Host;
import java.net.InetAddress;
import java.net.UnknownHostException;

public final class HostPort
extends GenericObject {
    private static final long serialVersionUID = -7103412227431884523L;
    protected Host host = null;
    protected int port = -1;

    @Override
    public Object clone() {
        HostPort hostPort = (HostPort)super.clone();
        Host host = this.host;
        if (host != null) {
            hostPort.host = (Host)host.clone();
        }
        return hostPort;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        this.host.encode(stringBuffer);
        if (this.port != -1) {
            stringBuffer.append(":");
            stringBuffer.append(this.port);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (HostPort)object;
        boolean bl2 = bl;
        if (this.port == ((HostPort)object).port) {
            bl2 = bl;
            if (this.host.equals(((HostPort)object).host)) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public Host getHost() {
        return this.host;
    }

    public InetAddress getInetAddress() throws UnknownHostException {
        Host host = this.host;
        if (host == null) {
            return null;
        }
        return host.getInetAddress();
    }

    public int getPort() {
        return this.port;
    }

    public boolean hasPort() {
        boolean bl = this.port != -1;
        return bl;
    }

    public int hashCode() {
        return this.host.hashCode() + this.port;
    }

    @Override
    public void merge(Object object) {
        super.merge(object);
        if (this.port == -1) {
            this.port = ((HostPort)object).port;
        }
    }

    public void removePort() {
        this.port = -1;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public void setPort(int n) {
        this.port = n;
    }

    public String toString() {
        return this.encode();
    }
}

