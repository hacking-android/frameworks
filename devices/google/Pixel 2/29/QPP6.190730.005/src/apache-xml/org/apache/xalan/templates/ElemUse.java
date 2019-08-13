/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.ArrayList;
import java.util.Vector;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;

public class ElemUse
extends ElemTemplateElement {
    static final long serialVersionUID = 5830057200289299736L;
    private QName[] m_attributeSetsNames = null;

    private void applyAttrSets(TransformerImpl transformerImpl, StylesheetRoot stylesheetRoot, QName[] arrqName) throws TransformerException {
        if (arrqName != null) {
            for (QName qName : arrqName) {
                ArrayList arrayList = stylesheetRoot.getAttributeSetComposed(qName);
                if (arrayList != null) {
                    for (int i = arrayList.size() - 1; i >= 0; --i) {
                        ((ElemAttributeSet)arrayList.get(i)).execute(transformerImpl);
                    }
                    continue;
                }
                throw new TransformerException(XSLMessages.createMessage("ER_NO_ATTRIB_SET", new Object[]{qName}), this);
            }
        }
    }

    public void applyAttrSets(TransformerImpl transformerImpl, StylesheetRoot stylesheetRoot) throws TransformerException {
        this.applyAttrSets(transformerImpl, stylesheetRoot, this.m_attributeSetsNames);
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        if (this.m_attributeSetsNames != null) {
            this.applyAttrSets(transformerImpl, this.getStylesheetRoot(), this.m_attributeSetsNames);
        }
    }

    public QName[] getUseAttributeSets() {
        return this.m_attributeSetsNames;
    }

    public void setUseAttributeSets(Vector vector) {
        int n = vector.size();
        this.m_attributeSetsNames = new QName[n];
        for (int i = 0; i < n; ++i) {
            this.m_attributeSetsNames[i] = (QName)vector.elementAt(i);
        }
    }

    public void setUseAttributeSets(QName[] arrqName) {
        this.m_attributeSetsNames = arrqName;
    }
}

