/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.security.cert.CertStoreParameters;

public class LDAPCertStoreParameters
implements CertStoreParameters {
    private static final int LDAP_DEFAULT_PORT = 389;
    private int port;
    private String serverName;

    public LDAPCertStoreParameters() {
        this("localhost", 389);
    }

    public LDAPCertStoreParameters(String string) {
        this(string, 389);
    }

    public LDAPCertStoreParameters(String string, int n) {
        if (string != null) {
            this.serverName = string;
            this.port = n;
            return;
        }
        throw new NullPointerException();
    }

    @Override
    public Object clone() {
        try {
            Object object = super.clone();
            return object;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new InternalError(cloneNotSupportedException.toString(), cloneNotSupportedException);
        }
    }

    public int getPort() {
        return this.port;
    }

    public String getServerName() {
        return this.serverName;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("LDAPCertStoreParameters: [\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  serverName: ");
        stringBuilder.append(this.serverName);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  port: ");
        stringBuilder.append(this.port);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}

