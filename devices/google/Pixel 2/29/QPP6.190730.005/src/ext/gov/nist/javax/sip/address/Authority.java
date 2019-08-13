/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.javax.sip.address.NetObject;
import gov.nist.javax.sip.address.UserInfo;

public class Authority
extends NetObject {
    private static final long serialVersionUID = -3570349777347017894L;
    protected HostPort hostPort;
    protected UserInfo userInfo;

    @Override
    public Object clone() {
        Authority authority = (Authority)super.clone();
        GenericObject genericObject = this.hostPort;
        if (genericObject != null) {
            authority.hostPort = (HostPort)((HostPort)genericObject).clone();
        }
        if ((genericObject = this.userInfo) != null) {
            authority.userInfo = (UserInfo)genericObject.clone();
        }
        return authority;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        UserInfo userInfo = this.userInfo;
        if (userInfo != null) {
            userInfo.encode(stringBuffer);
            stringBuffer.append("@");
            this.hostPort.encode(stringBuffer);
        } else {
            this.hostPort.encode(stringBuffer);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        NetObject netObject = (Authority)object;
        if (!this.hostPort.equals(netObject.hostPort)) {
            return false;
        }
        object = this.userInfo;
        return object == null || (netObject = netObject.userInfo) == null || ((UserInfo)object).equals(netObject);
    }

    public Host getHost() {
        HostPort hostPort = this.hostPort;
        if (hostPort == null) {
            return null;
        }
        return hostPort.getHost();
    }

    public HostPort getHostPort() {
        return this.hostPort;
    }

    public String getPassword() {
        UserInfo userInfo = this.userInfo;
        if (userInfo == null) {
            return null;
        }
        return userInfo.password;
    }

    public int getPort() {
        HostPort hostPort = this.hostPort;
        if (hostPort == null) {
            return -1;
        }
        return hostPort.getPort();
    }

    public String getUser() {
        Object object = this.userInfo;
        object = object != null ? ((UserInfo)object).user : null;
        return object;
    }

    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    public int hashCode() {
        HostPort hostPort = this.hostPort;
        if (hostPort != null) {
            return hostPort.encode().hashCode();
        }
        throw new UnsupportedOperationException("Null hostPort cannot compute hashcode");
    }

    public void removePort() {
        HostPort hostPort = this.hostPort;
        if (hostPort != null) {
            hostPort.removePort();
        }
    }

    public void removeUserInfo() {
        this.userInfo = null;
    }

    public void setHost(Host host) {
        if (this.hostPort == null) {
            this.hostPort = new HostPort();
        }
        this.hostPort.setHost(host);
    }

    public void setHostPort(HostPort hostPort) {
        this.hostPort = hostPort;
    }

    public void setPassword(String string) {
        if (this.userInfo == null) {
            this.userInfo = new UserInfo();
        }
        this.userInfo.setPassword(string);
    }

    public void setPort(int n) {
        if (this.hostPort == null) {
            this.hostPort = new HostPort();
        }
        this.hostPort.setPort(n);
    }

    public void setUser(String string) {
        if (this.userInfo == null) {
            this.userInfo = new UserInfo();
        }
        this.userInfo.setUser(string);
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}

