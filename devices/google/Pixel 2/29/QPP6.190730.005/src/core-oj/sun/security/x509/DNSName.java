/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Locale;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;

public class DNSName
implements GeneralNameInterface {
    private static final String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String alphaDigitsAndHyphen = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-";
    private static final String digitsAndHyphen = "0123456789-";
    private String name;

    public DNSName(String string) throws IOException {
        if (string != null && string.length() != 0) {
            if (string.indexOf(32) == -1) {
                if (string.charAt(0) != '.' && string.charAt(string.length() - 1) != '.') {
                    int n = 0;
                    while (n < string.length()) {
                        int n2;
                        int n3 = n2 = string.indexOf(46, n);
                        if (n2 < 0) {
                            n3 = string.length();
                        }
                        if (n3 - n >= 1) {
                            if (alpha.indexOf(string.charAt(n)) >= 0) {
                                ++n;
                                while (n < n3) {
                                    if (alphaDigitsAndHyphen.indexOf(string.charAt(n)) >= 0) {
                                        ++n;
                                        continue;
                                    }
                                    throw new IOException("DNSName components must consist of letters, digits, and hyphens");
                                }
                                n = n3 + 1;
                                continue;
                            }
                            throw new IOException("DNSName components must begin with a letter");
                        }
                        throw new IOException("DNSName SubjectAltNames with empty components are not permitted");
                    }
                    this.name = string;
                    return;
                }
                throw new IOException("DNS names or NameConstraints may not begin or end with a .");
            }
            throw new IOException("DNS names or NameConstraints with blank components are not permitted");
        }
        throw new IOException("DNS name must not be null");
    }

    public DNSName(DerValue derValue) throws IOException {
        this.name = derValue.getIA5String();
    }

    @Override
    public int constrains(GeneralNameInterface object) throws UnsupportedOperationException {
        String string;
        int n = object == null ? -1 : (object.getType() != 2 ? -1 : (((String)(object = ((DNSName)object).getName().toLowerCase(Locale.ENGLISH))).equals(string = this.name.toLowerCase(Locale.ENGLISH)) ? 0 : (string.endsWith((String)object) ? (string.charAt(string.lastIndexOf((String)object) - 1) == '.' ? 2 : 3) : (((String)object).endsWith(string) ? (((String)object).charAt(((String)object).lastIndexOf(string) - 1) == '.' ? 1 : 3) : 3))));
        return n;
    }

    @Override
    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putIA5String(this.name);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof DNSName)) {
            return false;
        }
        object = (DNSName)object;
        return this.name.equalsIgnoreCase(((DNSName)object).name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return 2;
    }

    public int hashCode() {
        return this.name.toUpperCase(Locale.ENGLISH).hashCode();
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        int n = 1;
        int n2 = this.name.indexOf(46);
        while (n2 >= 0) {
            ++n;
            n2 = this.name.indexOf(46, n2 + 1);
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DNSName: ");
        stringBuilder.append(this.name);
        return stringBuilder.toString();
    }
}

