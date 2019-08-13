/*
 * Decompiled with CFR 0.145.
 */
package java.net;

final class UrlDeserializedState {
    private final String authority;
    private final String file;
    private final int hashCode;
    private final String host;
    private final int port;
    private final String protocol;
    private final String ref;

    public UrlDeserializedState(String string, String string2, int n, String string3, String string4, String string5, int n2) {
        this.protocol = string;
        this.host = string2;
        this.port = n;
        this.authority = string3;
        this.file = string4;
        this.ref = string5;
        this.hashCode = n2;
    }

    String getAuthority() {
        return this.authority;
    }

    String getFile() {
        return this.file;
    }

    int getHashCode() {
        return this.hashCode;
    }

    String getHost() {
        return this.host;
    }

    int getPort() {
        return this.port;
    }

    String getProtocol() {
        return this.protocol;
    }

    String getRef() {
        return this.ref;
    }

    String reconstituteUrlString() {
        int n = this.protocol.length() + 1;
        CharSequence charSequence = this.authority;
        int n2 = n;
        if (charSequence != null) {
            n2 = n;
            if (((String)charSequence).length() > 0) {
                n2 = n + (this.authority.length() + 2);
            }
        }
        charSequence = this.file;
        n = n2;
        if (charSequence != null) {
            n = n2 + ((String)charSequence).length();
        }
        charSequence = this.ref;
        n2 = n;
        if (charSequence != null) {
            n2 = n + (((String)charSequence).length() + 1);
        }
        charSequence = new StringBuilder(n2);
        ((StringBuilder)charSequence).append(this.protocol);
        ((StringBuilder)charSequence).append(":");
        String string = this.authority;
        if (string != null && string.length() > 0) {
            ((StringBuilder)charSequence).append("//");
            ((StringBuilder)charSequence).append(this.authority);
        }
        if ((string = this.file) != null) {
            ((StringBuilder)charSequence).append(string);
        }
        if (this.ref != null) {
            ((StringBuilder)charSequence).append("#");
            ((StringBuilder)charSequence).append(this.ref);
        }
        return ((StringBuilder)charSequence).toString();
    }
}

