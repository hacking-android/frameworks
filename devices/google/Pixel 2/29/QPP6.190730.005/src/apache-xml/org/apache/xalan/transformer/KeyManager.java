/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.KeyTable;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNodeSet;

public class KeyManager {
    private transient Vector m_key_tables = null;

    public XNodeSet getNodeSetDTMByKey(XPathContext object, int n, QName qName, XMLString xMLString, PrefixResolver prefixResolver) throws TransformerException {
        Object object2 = null;
        Vector vector = null;
        Vector vector2 = null;
        ElemTemplateElement elemTemplateElement = (ElemTemplateElement)prefixResolver;
        Cloneable cloneable = vector;
        if (elemTemplateElement != null) {
            cloneable = vector;
            if (elemTemplateElement.getStylesheetRoot().getKeysComposed() != null) {
                boolean bl;
                boolean bl2 = false;
                cloneable = this.m_key_tables;
                if (cloneable == null) {
                    this.m_key_tables = new Vector(4);
                    vector2 = object2;
                    bl = bl2;
                } else {
                    int n2 = cloneable.size();
                    int n3 = 0;
                    cloneable = vector2;
                    do {
                        vector2 = cloneable;
                        bl = bl2;
                        if (n3 >= n2) break;
                        object2 = (KeyTable)this.m_key_tables.elementAt(n3);
                        vector2 = cloneable;
                        if (((KeyTable)object2).getKeyTableName().equals(qName)) {
                            vector2 = cloneable;
                            if (n == ((KeyTable)object2).getDocKey()) {
                                vector2 = cloneable = ((KeyTable)object2).getNodeSetDTMByKey(qName, xMLString);
                                if (cloneable != null) {
                                    bl = true;
                                    vector2 = cloneable;
                                    break;
                                }
                            }
                        }
                        ++n3;
                        cloneable = vector2;
                    } while (true);
                }
                cloneable = vector2;
                if (vector2 == null) {
                    cloneable = vector2;
                    if (!bl) {
                        object = new KeyTable(n, prefixResolver, qName, elemTemplateElement.getStylesheetRoot().getKeysComposed(), (XPathContext)object);
                        this.m_key_tables.addElement(object);
                        cloneable = vector2;
                        if (n == ((KeyTable)object).getDocKey()) {
                            cloneable = ((KeyTable)object).getNodeSetDTMByKey(qName, xMLString);
                        }
                    }
                }
            }
        }
        return cloneable;
    }
}

