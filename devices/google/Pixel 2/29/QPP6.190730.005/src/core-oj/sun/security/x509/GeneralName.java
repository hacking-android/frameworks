/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.DNSName;
import sun.security.x509.EDIPartyName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.IPAddressName;
import sun.security.x509.OIDName;
import sun.security.x509.OtherName;
import sun.security.x509.RFC822Name;
import sun.security.x509.URIName;
import sun.security.x509.X500Name;

public class GeneralName {
    private GeneralNameInterface name;

    public GeneralName(DerValue derValue) throws IOException {
        this(derValue, false);
    }

    public GeneralName(DerValue object, boolean bl) throws IOException {
        block17 : {
            this.name = null;
            short s = (byte)(((DerValue)object).tag & 31);
            switch (s) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unrecognized GeneralName tag, (");
                    ((StringBuilder)object).append(s);
                    ((StringBuilder)object).append(")");
                    throw new IOException(((StringBuilder)object).toString());
                }
                case 8: {
                    if (((DerValue)object).isContextSpecific() && !((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)6);
                        this.name = new OIDName((DerValue)object);
                        break;
                    }
                    throw new IOException("Invalid encoding of OID name");
                }
                case 7: {
                    if (((DerValue)object).isContextSpecific() && !((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)4);
                        this.name = new IPAddressName((DerValue)object);
                        break;
                    }
                    throw new IOException("Invalid encoding of IP address");
                }
                case 6: {
                    if (((DerValue)object).isContextSpecific() && !((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)22);
                        object = bl ? URIName.nameConstraint((DerValue)object) : new URIName((DerValue)object);
                        this.name = object;
                        break;
                    }
                    throw new IOException("Invalid encoding of URI");
                }
                case 5: {
                    if (((DerValue)object).isContextSpecific() && ((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)48);
                        this.name = new EDIPartyName((DerValue)object);
                        break;
                    }
                    throw new IOException("Invalid encoding of EDI name");
                }
                case 4: {
                    if (((DerValue)object).isContextSpecific() && ((DerValue)object).isConstructed()) {
                        this.name = new X500Name(((DerValue)object).getData());
                        break;
                    }
                    throw new IOException("Invalid encoding of Directory name");
                }
                case 2: {
                    if (((DerValue)object).isContextSpecific() && !((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)22);
                        this.name = new DNSName((DerValue)object);
                        break;
                    }
                    throw new IOException("Invalid encoding of DNS name");
                }
                case 1: {
                    if (((DerValue)object).isContextSpecific() && !((DerValue)object).isConstructed()) {
                        ((DerValue)object).resetTag((byte)22);
                        this.name = new RFC822Name((DerValue)object);
                        break;
                    }
                    throw new IOException("Invalid encoding of RFC822 name");
                }
                case 0: {
                    if (!((DerValue)object).isContextSpecific() || !((DerValue)object).isConstructed()) break block17;
                    ((DerValue)object).resetTag((byte)48);
                    this.name = new OtherName((DerValue)object);
                }
            }
            return;
        }
        throw new IOException("Invalid encoding of Other-Name");
    }

    public GeneralName(GeneralNameInterface generalNameInterface) {
        this.name = null;
        if (generalNameInterface != null) {
            this.name = generalNameInterface;
            return;
        }
        throw new NullPointerException("GeneralName must not be null");
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.name.encode(derOutputStream2);
        int n = this.name.getType();
        if (n != 0 && n != 3 && n != 5) {
            if (n == 4) {
                derOutputStream.write(DerValue.createTag((byte)-128, true, (byte)n), derOutputStream2);
            } else {
                derOutputStream.writeImplicit(DerValue.createTag((byte)-128, false, (byte)n), derOutputStream2);
            }
        } else {
            derOutputStream.writeImplicit(DerValue.createTag((byte)-128, true, (byte)n), derOutputStream2);
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof GeneralName)) {
            return false;
        }
        object = ((GeneralName)object).name;
        try {
            int n = this.name.constrains((GeneralNameInterface)object);
            if (n != 0) {
                bl = false;
            }
            return bl;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return false;
        }
    }

    public GeneralNameInterface getName() {
        return this.name;
    }

    public int getType() {
        return this.name.getType();
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return this.name.toString();
    }
}

