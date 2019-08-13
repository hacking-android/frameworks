/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

public class X509AttributeName {
    private static final char SEPARATOR = '.';
    private String prefix = null;
    private String suffix = null;

    public X509AttributeName(String string) {
        int n = string.indexOf(46);
        if (n < 0) {
            this.prefix = string;
        } else {
            this.prefix = string.substring(0, n);
            this.suffix = string.substring(n + 1);
        }
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getSuffix() {
        return this.suffix;
    }
}

