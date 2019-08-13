/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralName;

public class GeneralNames {
    private final List<GeneralName> names = new ArrayList<GeneralName>();

    public GeneralNames() {
    }

    public GeneralNames(DerValue derValue) throws IOException {
        this();
        if (derValue.tag == 48) {
            if (derValue.data.available() != 0) {
                while (derValue.data.available() != 0) {
                    this.add(new GeneralName(derValue.data.getDerValue()));
                }
                return;
            }
            throw new IOException("No data available in passed DER encoded value.");
        }
        throw new IOException("Invalid encoding for GeneralNames.");
    }

    public GeneralNames add(GeneralName generalName) {
        if (generalName != null) {
            this.names.add(generalName);
            return this;
        }
        throw new NullPointerException();
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        if (this.isEmpty()) {
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        Iterator<GeneralName> iterator = this.names.iterator();
        while (iterator.hasNext()) {
            iterator.next().encode(derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GeneralNames)) {
            return false;
        }
        object = (GeneralNames)object;
        return this.names.equals(((GeneralNames)object).names);
    }

    public GeneralName get(int n) {
        return this.names.get(n);
    }

    public int hashCode() {
        return this.names.hashCode();
    }

    public boolean isEmpty() {
        return this.names.isEmpty();
    }

    public Iterator<GeneralName> iterator() {
        return this.names.iterator();
    }

    public List<GeneralName> names() {
        return this.names;
    }

    public int size() {
        return this.names.size();
    }

    public String toString() {
        return this.names.toString();
    }
}

