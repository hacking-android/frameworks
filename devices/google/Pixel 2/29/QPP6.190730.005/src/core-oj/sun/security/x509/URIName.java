/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.DNSName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.IPAddressName;

public class URIName
implements GeneralNameInterface {
    private String host;
    private DNSName hostDNS;
    private IPAddressName hostIP;
    private URI uri;

    public URIName(String string) throws IOException {
        Object object;
        block8 : {
            block9 : {
                block10 : {
                    String string2;
                    try {
                        object = new URI(string);
                        this.uri = object;
                        if (this.uri.getScheme() == null) break block8;
                        this.host = this.uri.getHost();
                        object = this.host;
                        if (object == null) break block9;
                        if (((String)object).charAt(0) != '[') break block10;
                        object = this.host;
                        string2 = ((String)object).substring(1, ((String)object).length() - 1);
                    }
                    catch (URISyntaxException uRISyntaxException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid URI name:");
                        stringBuilder.append(string);
                        throw new IOException(stringBuilder.toString(), uRISyntaxException);
                    }
                    try {
                        this.hostIP = object = new IPAddressName(string2);
                    }
                    catch (IOException iOException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid URI name (host portion is not a valid IPv6 address):");
                        stringBuilder.append(string);
                        throw new IOException(stringBuilder.toString());
                    }
                }
                try {
                    this.hostDNS = object = new DNSName(this.host);
                }
                catch (IOException iOException) {
                    try {
                        IPAddressName iPAddressName;
                        this.hostIP = iPAddressName = new IPAddressName(this.host);
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("invalid URI name (host portion is not a valid DNS name, IPv4 address, or IPv6 address):");
                        stringBuilder.append(string);
                        throw new IOException(stringBuilder.toString());
                    }
                }
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("URI name must include scheme:");
        ((StringBuilder)object).append(string);
        throw new IOException(((StringBuilder)object).toString());
    }

    URIName(URI uRI, String string, DNSName dNSName) {
        this.uri = uRI;
        this.host = string;
        this.hostDNS = dNSName;
    }

    public URIName(DerValue derValue) throws IOException {
        this(derValue.getIA5String());
    }

    public static URIName nameConstraint(DerValue object) throws IOException {
        String string;
        block4 : {
            CharSequence charSequence;
            URI uRI;
            string = ((DerValue)object).getIA5String();
            try {
                uRI = new URI(string);
                if (uRI.getScheme() != null) break block4;
                charSequence = uRI.getSchemeSpecificPart();
            }
            catch (URISyntaxException uRISyntaxException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid URI name constraint:");
                stringBuilder.append(string);
                throw new IOException(stringBuilder.toString(), uRISyntaxException);
            }
            try {
                object = ((String)charSequence).startsWith(".") ? new DNSName(((String)charSequence).substring(1)) : new DNSName((String)charSequence);
                object = new URIName(uRI, (String)charSequence, (DNSName)object);
                return object;
            }
            catch (IOException iOException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("invalid URI name constraint:");
                ((StringBuilder)charSequence).append(string);
                throw new IOException(((StringBuilder)charSequence).toString(), iOException);
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("invalid URI name constraint (should not include scheme):");
        ((StringBuilder)object).append(string);
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public int constrains(GeneralNameInterface object) throws UnsupportedOperationException {
        int n;
        if (object == null) {
            n = -1;
        } else if (object.getType() != 6) {
            n = -1;
        } else {
            String string = ((URIName)object).getHost();
            if (string.equalsIgnoreCase(this.host)) {
                n = 0;
            } else {
                object = ((URIName)object).getHostObject();
                if (this.hostDNS != null && object instanceof DNSName) {
                    String string2 = this.host;
                    boolean bl = false;
                    boolean bl2 = string2.charAt(0) == '.';
                    if (string.charAt(0) == '.') {
                        bl = true;
                    }
                    object = (DNSName)object;
                    int n2 = this.hostDNS.constrains((GeneralNameInterface)object);
                    if (!(bl2 || bl || n2 != 2 && n2 != 1)) {
                        n2 = 3;
                    }
                    n = n2;
                    if (bl2 != bl) {
                        n = n2;
                        if (n2 == 0) {
                            n = bl2 ? 2 : 1;
                        }
                    }
                } else {
                    n = 3;
                }
            }
        }
        return n;
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putIA5String(this.uri.toASCIIString());
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof URIName)) {
            return false;
        }
        object = (URIName)object;
        return this.uri.equals(((URIName)object).getURI());
    }

    public String getHost() {
        return this.host;
    }

    public Object getHostObject() {
        IPAddressName iPAddressName = this.hostIP;
        if (iPAddressName != null) {
            return iPAddressName;
        }
        return this.hostDNS;
    }

    public String getName() {
        return this.uri.toString();
    }

    public String getScheme() {
        return this.uri.getScheme();
    }

    @Override
    public int getType() {
        return 6;
    }

    public URI getURI() {
        return this.uri;
    }

    public int hashCode() {
        return this.uri.hashCode();
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        try {
            DNSName dNSName = new DNSName(this.host);
            return dNSName.subtreeDepth();
        }
        catch (IOException iOException) {
            throw new UnsupportedOperationException(iOException.getMessage());
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("URIName: ");
        stringBuilder.append(this.uri.toString());
        return stringBuilder.toString();
    }
}

