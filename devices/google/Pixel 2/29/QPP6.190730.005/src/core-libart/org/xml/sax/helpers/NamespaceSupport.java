/*
 * Decompiled with CFR 0.145.
 */
package org.xml.sax.helpers;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.Enumeration;
import java.util.Hashtable;

public class NamespaceSupport {
    @UnsupportedAppUsage
    private static final Enumeration EMPTY_ENUMERATION = Collections.enumeration(Collections.emptyList());
    public static final String NSDECL = "http://www.w3.org/xmlns/2000/";
    public static final String XMLNS = "http://www.w3.org/XML/1998/namespace";
    @UnsupportedAppUsage
    private int contextPos;
    @UnsupportedAppUsage
    private Context[] contexts;
    @UnsupportedAppUsage
    private Context currentContext;
    @UnsupportedAppUsage
    private boolean namespaceDeclUris;

    public NamespaceSupport() {
        this.reset();
    }

    public boolean declarePrefix(String string, String string2) {
        if (!string.equals("xml") && !string.equals("xmlns")) {
            this.currentContext.declarePrefix(string, string2);
            return true;
        }
        return false;
    }

    public Enumeration getDeclaredPrefixes() {
        return this.currentContext.getDeclaredPrefixes();
    }

    public String getPrefix(String string) {
        return this.currentContext.getPrefix(string);
    }

    public Enumeration getPrefixes() {
        return this.currentContext.getPrefixes();
    }

    public Enumeration getPrefixes(String string) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Enumeration enumeration = this.getPrefixes();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            if (!string.equals(this.getURI(string2))) continue;
            arrayList.add(string2);
        }
        return Collections.enumeration(arrayList);
    }

    public String getURI(String string) {
        return this.currentContext.getURI(string);
    }

    public boolean isNamespaceDeclUris() {
        return this.namespaceDeclUris;
    }

    public void popContext() {
        this.contexts[this.contextPos].clear();
        --this.contextPos;
        int n = this.contextPos;
        if (n >= 0) {
            this.currentContext = this.contexts[n];
            return;
        }
        throw new EmptyStackException();
    }

    public String[] processName(String arrstring, String[] arrstring2, boolean bl) {
        if ((arrstring = this.currentContext.processName((String)arrstring, bl)) == null) {
            return null;
        }
        arrstring2[0] = arrstring[0];
        arrstring2[1] = arrstring[1];
        arrstring2[2] = arrstring[2];
        return arrstring2;
    }

    public void pushContext() {
        Context[] arrcontext;
        Object object = this.contexts;
        int n = ((Context[])object).length;
        int n2 = this.contextPos;
        object[n2].declsOK = false;
        this.contextPos = n2 + 1;
        if (this.contextPos >= n) {
            arrcontext = new Context[n * 2];
            System.arraycopy(object, 0, arrcontext, 0, n);
            this.contexts = arrcontext;
        }
        arrcontext = this.contexts;
        n = this.contextPos;
        this.currentContext = arrcontext[n];
        if (this.currentContext == null) {
            this.currentContext = object = new Context();
            arrcontext[n] = object;
        }
        if ((n = this.contextPos) > 0) {
            this.currentContext.setParent(this.contexts[n - 1]);
        }
    }

    public void reset() {
        Context context;
        this.contexts = new Context[32];
        this.namespaceDeclUris = false;
        this.contextPos = 0;
        Context[] arrcontext = this.contexts;
        int n = this.contextPos;
        this.currentContext = context = new Context();
        arrcontext[n] = context;
        this.currentContext.declarePrefix("xml", XMLNS);
    }

    public void setNamespaceDeclUris(boolean bl) {
        int n = this.contextPos;
        if (n == 0) {
            if (bl == this.namespaceDeclUris) {
                return;
            }
            this.namespaceDeclUris = bl;
            if (bl) {
                this.currentContext.declarePrefix("xmlns", NSDECL);
            } else {
                Context context;
                Context[] arrcontext = this.contexts;
                this.currentContext = context = new Context();
                arrcontext[n] = context;
                this.currentContext.declarePrefix("xml", XMLNS);
            }
            return;
        }
        throw new IllegalStateException();
    }

    final class Context {
        Hashtable attributeNameTable;
        private boolean declSeen = false;
        private ArrayList<String> declarations = null;
        boolean declsOK = true;
        String defaultNS = null;
        Hashtable elementNameTable;
        private Context parent = null;
        Hashtable prefixTable;
        Hashtable uriTable;

        @UnsupportedAppUsage
        Context() {
            this.copyTables();
        }

        private void copyTables() {
            Hashtable hashtable = this.prefixTable;
            this.prefixTable = hashtable != null ? (Hashtable)hashtable.clone() : new Hashtable();
            hashtable = this.uriTable;
            this.uriTable = hashtable != null ? (Hashtable)hashtable.clone() : new Hashtable();
            this.elementNameTable = new Hashtable();
            this.attributeNameTable = new Hashtable();
            this.declSeen = true;
        }

        void clear() {
            this.parent = null;
            this.prefixTable = null;
            this.uriTable = null;
            this.elementNameTable = null;
            this.attributeNameTable = null;
            this.defaultNS = null;
        }

        void declarePrefix(String string, String string2) {
            if (this.declsOK) {
                if (!this.declSeen) {
                    this.copyTables();
                }
                if (this.declarations == null) {
                    this.declarations = new ArrayList();
                }
                string = string.intern();
                string2 = string2.intern();
                if ("".equals(string)) {
                    this.defaultNS = "".equals(string2) ? null : string2;
                } else {
                    this.prefixTable.put(string, string2);
                    this.uriTable.put(string2, string);
                }
                this.declarations.add(string);
                return;
            }
            throw new IllegalStateException("can't declare any more prefixes in this context");
        }

        Enumeration getDeclaredPrefixes() {
            Object object = this.declarations;
            object = object == null ? EMPTY_ENUMERATION : Collections.enumeration(object);
            return object;
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
            block6 : {
                Hashtable hashtable;
                String[] arrstring;
                block5 : {
                    int n;
                    block4 : {
                        this.declsOK = false;
                        hashtable = bl ? this.attributeNameTable : this.elementNameTable;
                        arrstring = (String[])hashtable.get(string);
                        if (arrstring != null) {
                            return arrstring;
                        }
                        arrstring = new String[3];
                        arrstring[2] = string.intern();
                        n = string.indexOf(58);
                        if (n != -1) break block4;
                        arrstring[0] = bl ? (string == "xmlns" && NamespaceSupport.this.namespaceDeclUris ? NamespaceSupport.NSDECL : "") : ((string = this.defaultNS) == null ? "" : string);
                        arrstring[1] = arrstring[2];
                        break block5;
                    }
                    String string2 = string.substring(0, n);
                    String string3 = string.substring(n + 1);
                    string = "".equals(string2) ? this.defaultNS : (String)this.prefixTable.get(string2);
                    if (string == null || !bl && "xmlns".equals(string2)) break block6;
                    arrstring[0] = string;
                    arrstring[1] = string3.intern();
                }
                hashtable.put(arrstring[2], arrstring);
                return arrstring;
            }
            return null;
        }

        void setParent(Context context) {
            this.parent = context;
            this.declarations = null;
            this.prefixTable = context.prefixTable;
            this.uriTable = context.uriTable;
            this.elementNameTable = context.elementNameTable;
            this.attributeNameTable = context.attributeNameTable;
            this.defaultNS = context.defaultNS;
            this.declSeen = false;
            this.declsOK = true;
        }
    }

}

