/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemAttribute;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemUse;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;

public class ElemAttributeSet
extends ElemUse {
    static final long serialVersionUID = -426740318278164496L;
    public QName m_qname = null;

    public ElemTemplateElement appendChildElem(ElemTemplateElement elemTemplateElement) {
        block0 : {
            if (elemTemplateElement.getXSLToken() == 48) break block0;
            this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        }
        return super.appendChild(elemTemplateElement);
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        if (!transformerImpl.isRecursiveAttrSet(this)) {
            transformerImpl.pushElemAttributeSet(this);
            super.execute(transformerImpl);
            for (ElemAttribute elemAttribute = (ElemAttribute)this.getFirstChildElem(); elemAttribute != null; elemAttribute = (ElemAttribute)elemAttribute.getNextSiblingElem()) {
                elemAttribute.execute(transformerImpl);
            }
            transformerImpl.popElemAttributeSet();
            return;
        }
        throw new TransformerException(XSLMessages.createMessage("ER_XSLATTRSET_USED_ITSELF", new Object[]{this.m_qname.getLocalPart()}));
    }

    public QName getName() {
        return this.m_qname;
    }

    @Override
    public String getNodeName() {
        return "attribute-set";
    }

    @Override
    public int getXSLToken() {
        return 40;
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeAttributeSets(this);
    }

    public void setName(QName qName) {
        this.m_qname = qName;
    }
}

