/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

final class Context2 {
    private static final Enumeration EMPTY_ENUMERATION = new Vector().elements();
    Hashtable attributeNameTable;
    private Context2 child = null;
    private Vector declarations = null;
    String defaultNS = null;
    Hashtable elementNameTable;
    private Context2 parent = null;
    Hashtable prefixTable;
    private boolean tablesDirty = false;
    Hashtable uriTable;

    Context2(Context2 context2) {
        if (context2 == null) {
            this.prefixTable = new Hashtable();
            this.uriTable = new Hashtable();
            this.elementNameTable = null;
            this.attributeNameTable = null;
        } else {
            this.setParent(context2);
        }
    }

    private void copyTables() {
        this.prefixTable = (Hashtable)this.prefixTable.clone();
        this.uriTable = (Hashtable)this.uriTable.clone();
        if (this.elementNameTable != null) {
            this.elementNameTable = new Hashtable();
        }
        if (this.attributeNameTable != null) {
            this.attributeNameTable = new Hashtable();
        }
        this.tablesDirty = true;
    }

    void declarePrefix(String string, String string2) {
        if (!this.tablesDirty) {
            this.copyTables();
        }
        if (this.declarations == null) {
            this.declarations = new Vector();
        }
        string = string.intern();
        string2 = string2.intern();
        if ("".equals(string)) {
            this.defaultNS = "".equals(string2) ? null : string2;
        } else {
            this.prefixTable.put(string, string2);
            this.uriTable.put(string2, string);
        }
        this.declarations.addElement(string);
    }

    Context2 getChild() {
        return this.child;
    }

    Enumeration getDeclaredPrefixes() {
        Vector vector = this.declarations;
        if (vector == null) {
            return EMPTY_ENUMERATION;
        }
        return vector.elements();
    }

    Context2 getParent() {
        return this.parent;
    }

    String getPrefix(String string) {
        Hashtable hashtable = this.uriTable;
        if (hashtable == null) {
            return null;
        }
        return (String)hashtable.get(string);
    }

    Enumeration getPrefixes() {
        Hashtable hashtable = this.prefixTable;
        if (hashtable == null) {
            return EMPTY_ENUMERATION;
        }
        return hashtable.keys();
    }

    String getURI(String string) {
        if ("".equals(string)) {
            return this.defaultNS;
        }
        Hashtable hashtable = this.prefixTable;
        if (hashtable == null) {
            return null;
        }
        return (String)hashtable.get(string);
    }

    String[] processName(String string, boolean bl) {
        Hashtable hashtable;
        if (bl) {
            if (this.elementNameTable == null) {
                this.elementNameTable = new Hashtable();
            }
            hashtable = this.elementNameTable;
        } else {
            if (this.attributeNameTable == null) {
                this.attributeNameTable = new Hashtable();
            }
            hashtable = this.attributeNameTable;
        }
        Object object = (String[])hashtable.get(string);
        if (object != null) {
            return object;
        }
        String[] arrstring = new String[3];
        int n = string.indexOf(58);
        if (n == -1) {
            arrstring[0] = !bl && (object = this.defaultNS) != null ? object : "";
            arrstring[1] = string.intern();
            arrstring[2] = arrstring[1];
        } else {
            object = string.substring(0, n);
            String string2 = string.substring(n + 1);
            if ((object = "".equals(object) ? this.defaultNS : (String)this.prefixTable.get(object)) == null) {
                return null;
            }
            arrstring[0] = object;
            arrstring[1] = string2.intern();
            arrstring[2] = string.intern();
        }
        hashtable.put(arrstring[2], arrstring);
        this.tablesDirty = true;
        return arrstring;
    }

    void setParent(Context2 context2) {
        this.parent = context2;
        context2.child = this;
        this.declarations = null;
        this.prefixTable = context2.prefixTable;
        this.uriTable = context2.uriTable;
        this.elementNameTable = context2.elementNameTable;
        this.attributeNameTable = context2.attributeNameTable;
        this.defaultNS = context2.defaultNS;
        this.tablesDirty = false;
    }
}

