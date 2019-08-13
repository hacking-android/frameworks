/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.w3c.dom.DOMException;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;

public final class DocumentTypeImpl
extends LeafNodeImpl
implements DocumentType {
    private String publicId;
    private String qualifiedName;
    private String systemId;

    public DocumentTypeImpl(DocumentImpl object, String string, String string2, String string3) {
        block5 : {
            block9 : {
                block8 : {
                    block6 : {
                        block7 : {
                            super((DocumentImpl)object);
                            if (string == null || "".equals(string)) break block5;
                            int n = string.lastIndexOf(":");
                            if (n == -1) break block6;
                            object = string.substring(0, n);
                            String string4 = string.substring(n + 1);
                            if (!DocumentImpl.isXMLIdentifier((String)object)) break block7;
                            if (!DocumentImpl.isXMLIdentifier(string4)) {
                                throw new DOMException(5, string);
                            }
                            break block8;
                        }
                        throw new DOMException(14, string);
                    }
                    if (!DocumentImpl.isXMLIdentifier(string)) break block9;
                }
                this.qualifiedName = string;
                this.publicId = string2;
                this.systemId = string3;
                return;
            }
            throw new DOMException(5, string);
        }
        throw new DOMException(14, string);
    }

    @Override
    public NamedNodeMap getEntities() {
        return null;
    }

    @Override
    public String getInternalSubset() {
        return null;
    }

    @Override
    public String getName() {
        return this.qualifiedName;
    }

    @Override
    public String getNodeName() {
        return this.qualifiedName;
    }

    @Override
    public short getNodeType() {
        return 10;
    }

    @Override
    public NamedNodeMap getNotations() {
        return null;
    }

    @Override
    public String getPublicId() {
        return this.publicId;
    }

    @Override
    public String getSystemId() {
        return this.systemId;
    }

    @Override
    public String getTextContent() throws DOMException {
        return null;
    }
}

