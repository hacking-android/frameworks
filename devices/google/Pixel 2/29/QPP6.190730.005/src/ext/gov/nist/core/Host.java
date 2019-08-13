/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Host
extends GenericObject {
    protected static final int HOSTNAME = 1;
    protected static final int IPV4ADDRESS = 2;
    protected static final int IPV6ADDRESS = 3;
    private static final long serialVersionUID = -7233564517978323344L;
    protected int addressType;
    protected String hostname;
    private InetAddress inetAddress;
    private boolean stripAddressScopeZones = false;

    public Host() {
        this.addressType = 1;
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
    }

    public Host(String string) throws IllegalArgumentException {
        if (string != null) {
            this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
            this.setHost(string, 2);
            return;
        }
        throw new IllegalArgumentException("null host name");
    }

    public Host(String string, int n) {
        this.stripAddressScopeZones = Boolean.getBoolean("gov.nist.core.STRIP_ADDR_SCOPES");
        this.setHost(string, n);
    }

    private boolean isIPv6Address(String string) {
        boolean bl = string != null && string.indexOf(58) != -1;
        return bl;
    }

    public static boolean isIPv6Reference(String string) {
        boolean bl;
        block0 : {
            bl = false;
            if (string.charAt(0) != '[' || string.charAt(string.length() - 1) != ']') break block0;
            bl = true;
        }
        return bl;
    }

    private void setHost(String string, int n) {
        this.inetAddress = null;
        this.addressType = this.isIPv6Address(string) ? 3 : n;
        if (string != null) {
            this.hostname = string.trim();
            if (this.addressType == 1) {
                this.hostname = this.hostname.toLowerCase();
            }
            if (this.addressType == 3 && this.stripAddressScopeZones && (n = this.hostname.indexOf(37)) != -1) {
                this.hostname = this.hostname.substring(0, n);
            }
        }
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        if (this.addressType == 3 && !Host.isIPv6Reference(this.hostname)) {
            stringBuffer.append('[');
            stringBuffer.append(this.hostname);
            stringBuffer.append(']');
        } else {
            stringBuffer.append(this.hostname);
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!this.getClass().equals(object.getClass())) {
            return false;
        }
        return ((Host)object).hostname.equals(this.hostname);
    }

    public String getAddress() {
        return this.hostname;
    }

    public String getHostname() {
        return this.hostname;
    }

    public InetAddress getInetAddress() throws UnknownHostException {
        String string = this.hostname;
        if (string == null) {
            return null;
        }
        InetAddress inetAddress = this.inetAddress;
        if (inetAddress != null) {
            return inetAddress;
        }
        this.inetAddress = InetAddress.getByName(string);
        return this.inetAddress;
    }

    public String getIpAddress() {
        String string = null;
        String string2 = this.hostname;
        if (string2 == null) {
            return null;
        }
        if (this.addressType == 1) {
            try {
                if (this.inetAddress == null) {
                    this.inetAddress = InetAddress.getByName(string2);
                }
                string = string2 = this.inetAddress.getHostAddress();
            }
            catch (UnknownHostException unknownHostException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not resolve hostname ");
                stringBuilder.append(unknownHostException);
                this.dbgPrint(stringBuilder.toString());
            }
        } else {
            string = this.hostname;
        }
        return string;
    }

    public int hashCode() {
        return this.getHostname().hashCode();
    }

    public boolean isHostname() {
        int n = this.addressType;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean isIPAddress() {
        int n = this.addressType;
        boolean bl = true;
        if (n == 1) {
            bl = false;
        }
        return bl;
    }

    public void setAddress(String string) {
        this.setHostAddress(string);
    }

    public void setHostAddress(String string) {
        this.setHost(string, 2);
    }

    public void setHostname(String string) {
        this.setHost(string, 1);
    }
}

