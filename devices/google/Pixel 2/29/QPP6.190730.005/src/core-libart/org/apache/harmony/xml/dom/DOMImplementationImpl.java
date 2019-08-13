/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.DocumentTypeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;

public final class DOMImplementationImpl
implements DOMImplementation {
    private static DOMImplementationImpl instance;

    DOMImplementationImpl() {
    }

    public static DOMImplementationImpl getInstance() {
        if (instance == null) {
            instance = new DOMImplementationImpl();
        }
        return instance;
    }

    @Override
    public Document createDocument(String string, String string2, DocumentType documentType) throws DOMException {
        return new DocumentImpl(this, string, string2, documentType, null);
    }

    @Override
    public DocumentType createDocumentType(String string, String string2, String string3) throws DOMException {
        return new DocumentTypeImpl(null, string, string2, string3);
    }

    @Override
    public Object getFeature(String object, String string) {
        object = this.hasFeature((String)object, string) ? this : null;
        return object;
    }

    @Override
    public boolean hasFeature(String string, String string2) {
        block12 : {
            boolean bl;
            block14 : {
                block13 : {
                    boolean bl2;
                    boolean bl3;
                    String string3;
                    block9 : {
                        block11 : {
                            block10 : {
                                boolean bl4 = false;
                                bl2 = false;
                                bl = false;
                                bl3 = string2 == null || string2.length() == 0;
                                string3 = string;
                                if (string.startsWith("+")) {
                                    string3 = string.substring(1);
                                }
                                if (string3.equalsIgnoreCase("Core")) {
                                    if (bl3 || string2.equals("1.0") || string2.equals("2.0") || string2.equals("3.0")) {
                                        bl = true;
                                    }
                                    return bl;
                                }
                                if (!string3.equalsIgnoreCase("XML")) break block9;
                                if (bl3 || string2.equals("1.0") || string2.equals("2.0")) break block10;
                                bl = bl4;
                                if (!string2.equals("3.0")) break block11;
                            }
                            bl = true;
                        }
                        return bl;
                    }
                    if (!string3.equalsIgnoreCase("XMLVersion")) break block12;
                    if (bl3 || string2.equals("1.0")) break block13;
                    bl = bl2;
                    if (!string2.equals("1.1")) break block14;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }
}

