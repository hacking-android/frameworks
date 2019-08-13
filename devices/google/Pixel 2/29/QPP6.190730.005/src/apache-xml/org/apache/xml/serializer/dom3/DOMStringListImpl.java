/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.util.Vector;
import org.w3c.dom.DOMStringList;

final class DOMStringListImpl
implements DOMStringList {
    private Vector fStrings;

    DOMStringListImpl() {
        this.fStrings = new Vector();
    }

    DOMStringListImpl(Vector vector) {
        this.fStrings = vector;
    }

    DOMStringListImpl(String[] arrstring) {
        this.fStrings = new Vector();
        if (arrstring != null) {
            for (int i = 0; i < arrstring.length; ++i) {
                this.fStrings.add(arrstring[i]);
            }
        }
    }

    public void add(String string) {
        this.fStrings.add(string);
    }

    @Override
    public boolean contains(String string) {
        return this.fStrings.contains(string);
    }

    @Override
    public int getLength() {
        return this.fStrings.size();
    }

    @Override
    public String item(int n) {
        try {
            String string = (String)this.fStrings.elementAt(n);
            return string;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            return null;
        }
    }
}

