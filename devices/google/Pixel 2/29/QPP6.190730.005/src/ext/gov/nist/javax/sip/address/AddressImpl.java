/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.Host;
import gov.nist.core.HostPort;
import gov.nist.core.Match;
import gov.nist.javax.sip.address.GenericURI;
import gov.nist.javax.sip.address.NetObject;
import gov.nist.javax.sip.address.SipUri;
import javax.sip.address.Address;
import javax.sip.address.URI;

public final class AddressImpl
extends NetObject
implements Address {
    public static final int ADDRESS_SPEC = 2;
    public static final int NAME_ADDR = 1;
    public static final int WILD_CARD = 3;
    private static final long serialVersionUID = 429592779568617259L;
    protected GenericURI address;
    protected int addressType = 1;
    protected String displayName;

    @Override
    public Object clone() {
        AddressImpl addressImpl = (AddressImpl)super.clone();
        GenericURI genericURI = this.address;
        if (genericURI != null) {
            addressImpl.address = (GenericURI)genericURI.clone();
        }
        return addressImpl;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        if (this.addressType == 3) {
            stringBuffer.append('*');
        } else {
            if (this.displayName != null) {
                stringBuffer.append("\"");
                stringBuffer.append(this.displayName);
                stringBuffer.append("\"");
                stringBuffer.append(" ");
            }
            if (this.address != null) {
                if (this.addressType == 1 || this.displayName != null) {
                    stringBuffer.append("<");
                }
                this.address.encode(stringBuffer);
                if (this.addressType == 1 || this.displayName != null) {
                    stringBuffer.append(">");
                }
            }
        }
        return stringBuffer;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof Address) {
            object = (Address)object;
            return this.getURI().equals(object.getURI());
        }
        return false;
    }

    public int getAddressType() {
        return this.addressType;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getHost() {
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            return ((SipUri)genericURI).getHostPort().getHost().getHostname();
        }
        throw new RuntimeException("address is not a SipUri");
    }

    public HostPort getHostPort() {
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            return ((SipUri)genericURI).getHostPort();
        }
        throw new RuntimeException("address is not a SipUri");
    }

    @Override
    public int getPort() {
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            return ((SipUri)genericURI).getHostPort().getPort();
        }
        throw new RuntimeException("address is not a SipUri");
    }

    @Override
    public URI getURI() {
        return this.address;
    }

    @Override
    public String getUserAtHostPort() {
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            return ((SipUri)genericURI).getUserAtHostPort();
        }
        return genericURI.toString();
    }

    @Override
    public boolean hasDisplayName() {
        boolean bl = this.displayName != null;
        return bl;
    }

    @Override
    public int hashCode() {
        return this.address.hashCode();
    }

    @Override
    public boolean isSIPAddress() {
        return this.address instanceof SipUri;
    }

    @Override
    public boolean isWildcard() {
        boolean bl = this.addressType == 3;
        return bl;
    }

    @Override
    public boolean match(Object object) {
        boolean bl = true;
        if (object == null) {
            return true;
        }
        if (!(object instanceof Address)) {
            return false;
        }
        if (((GenericObject)(object = (AddressImpl)object)).getMatcher() != null) {
            return ((GenericObject)object).getMatcher().match(this.encode());
        }
        if (((AddressImpl)object).displayName != null && this.displayName == null) {
            return false;
        }
        String string = ((AddressImpl)object).displayName;
        if (string == null) {
            return this.address.match(((AddressImpl)object).address);
        }
        if (!this.displayName.equalsIgnoreCase(string) || !this.address.match(((AddressImpl)object).address)) {
            bl = false;
        }
        return bl;
    }

    public void removeDisplayName() {
        this.displayName = null;
    }

    public void removeParameter(String string) {
        GenericURI genericURI = this.address;
        if (genericURI instanceof SipUri) {
            ((SipUri)genericURI).removeParameter(string);
            return;
        }
        throw new RuntimeException("address is not a SipUri");
    }

    public void setAddess(URI uRI) {
        this.address = (GenericURI)uRI;
    }

    public void setAddressType(int n) {
        this.addressType = n;
    }

    @Override
    public void setDisplayName(String string) {
        this.displayName = string;
        this.addressType = 1;
    }

    @Override
    public void setURI(URI uRI) {
        this.address = (GenericURI)uRI;
    }

    public void setUser(String string) {
        ((SipUri)this.address).setUser(string);
    }

    @Override
    public void setWildCardFlag() {
        this.addressType = 3;
        this.address = new SipUri();
        ((SipUri)this.address).setUser("*");
    }
}

