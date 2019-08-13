/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Locale;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNameInterface;

public class RFC822Name
implements GeneralNameInterface {
    private String name;

    public RFC822Name(String string) throws IOException {
        this.parseName(string);
        this.name = string;
    }

    public RFC822Name(DerValue derValue) throws IOException {
        this.name = derValue.getIA5String();
        this.parseName(this.name);
    }

    @Override
    public int constrains(GeneralNameInterface object) throws UnsupportedOperationException {
        int n;
        if (object == null) {
            n = -1;
        } else if (object.getType() != 1) {
            n = -1;
        } else {
            String string = ((RFC822Name)object).getName().toLowerCase(Locale.ENGLISH);
            object = this.name.toLowerCase(Locale.ENGLISH);
            n = string.equals(object) ? 0 : (((String)object).endsWith(string) ? (string.indexOf(64) != -1 ? 3 : (string.startsWith(".") ? 2 : (((String)object).charAt(((String)object).lastIndexOf(string) - 1) == '@' ? 2 : 3))) : (string.endsWith((String)object) ? (((String)object).indexOf(64) != -1 ? 3 : (((String)object).startsWith(".") ? 1 : (string.charAt(string.lastIndexOf((String)object) - 1) == '@' ? 1 : 3))) : 3));
        }
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
        if (!(object instanceof RFC822Name)) {
            return false;
        }
        object = (RFC822Name)object;
        return this.name.equalsIgnoreCase(((RFC822Name)object).name);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public int getType() {
        return 1;
    }

    public int hashCode() {
        return this.name.toUpperCase(Locale.ENGLISH).hashCode();
    }

    public void parseName(String string) throws IOException {
        if (string != null && string.length() != 0) {
            if ((string = string.substring(string.indexOf(64) + 1)).length() != 0) {
                if (string.startsWith(".") && string.length() == 1) {
                    throw new IOException("RFC822Name domain may not be just .");
                }
                return;
            }
            throw new IOException("RFC822Name may not end with @");
        }
        throw new IOException("RFC822Name may not be null or empty");
    }

    @Override
    public int subtreeDepth() throws UnsupportedOperationException {
        String string = this.name;
        int n = 1;
        int n2 = string.lastIndexOf(64);
        String string2 = string;
        if (n2 >= 0) {
            n = 1 + 1;
            string2 = string.substring(n2 + 1);
        }
        while (string2.lastIndexOf(46) >= 0) {
            string2 = string2.substring(0, string2.lastIndexOf(46));
            ++n;
        }
        return n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RFC822Name: ");
        stringBuilder.append(this.name);
        return stringBuilder.toString();
    }
}

