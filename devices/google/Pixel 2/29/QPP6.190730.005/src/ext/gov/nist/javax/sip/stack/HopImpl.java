/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import java.io.PrintStream;
import java.io.Serializable;
import javax.sip.address.Hop;

public final class HopImpl
implements Hop,
Serializable {
    protected boolean defaultRoute;
    protected String host;
    protected int port;
    protected String transport;
    protected boolean uriRoute;

    HopImpl(String string) throws IllegalArgumentException {
        if (string != null) {
            CharSequence charSequence;
            int n = string.indexOf(93);
            int n2 = string.indexOf(58, n);
            int n3 = string.indexOf(47, n2);
            if (n2 > 0) {
                this.host = string.substring(0, n2);
                if (n3 > 0) {
                    charSequence = string.substring(n2 + 1, n3);
                    this.transport = string.substring(n3 + 1);
                } else {
                    charSequence = string.substring(n2 + 1);
                    this.transport = "UDP";
                }
                try {
                    this.port = Integer.parseInt((String)charSequence);
                }
                catch (NumberFormatException numberFormatException) {
                    throw new IllegalArgumentException("Bad port spec");
                }
            } else {
                n2 = 5060;
                if (n3 > 0) {
                    this.host = string.substring(0, n3);
                    this.transport = string.substring(n3 + 1);
                    if (this.transport.equalsIgnoreCase("TLS")) {
                        n2 = 5061;
                    }
                    this.port = n2;
                } else {
                    this.host = string;
                    this.transport = "UDP";
                    this.port = 5060;
                }
            }
            charSequence = this.host;
            if (charSequence != null && ((String)charSequence).length() != 0) {
                this.host = this.host.trim();
                this.transport = this.transport.trim();
                if (n > 0 && this.host.charAt(0) != '[') {
                    throw new IllegalArgumentException("Bad IPv6 reference spec");
                }
                if (this.transport.compareToIgnoreCase("UDP") != 0 && this.transport.compareToIgnoreCase("TLS") != 0 && this.transport.compareToIgnoreCase("TCP") != 0) {
                    PrintStream printStream = System.err;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Bad transport string ");
                    ((StringBuilder)charSequence).append(this.transport);
                    printStream.println(((StringBuilder)charSequence).toString());
                    throw new IllegalArgumentException(string);
                }
                return;
            }
            throw new IllegalArgumentException("no host!");
        }
        throw new IllegalArgumentException("Null arg!");
    }

    public HopImpl(String charSequence, int n, String string) {
        this.host = charSequence;
        if (this.host.indexOf(":") >= 0 && this.host.indexOf("[") < 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[");
            ((StringBuilder)charSequence).append(this.host);
            ((StringBuilder)charSequence).append("]");
            this.host = ((StringBuilder)charSequence).toString();
        }
        this.port = n;
        this.transport = string;
    }

    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public int getPort() {
        return this.port;
    }

    @Override
    public String getTransport() {
        return this.transport;
    }

    @Override
    public boolean isURIRoute() {
        return this.uriRoute;
    }

    @Override
    public void setURIRouteFlag() {
        this.uriRoute = true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.host);
        stringBuilder.append(":");
        stringBuilder.append(this.port);
        stringBuilder.append("/");
        stringBuilder.append(this.transport);
        return stringBuilder.toString();
    }
}

