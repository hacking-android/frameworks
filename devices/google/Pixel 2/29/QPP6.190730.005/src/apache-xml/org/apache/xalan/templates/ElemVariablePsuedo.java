/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.XUnresolvedVariableSimple;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class ElemVariablePsuedo
extends ElemVariable {
    static final long serialVersionUID = 692295692732588486L;
    XUnresolvedVariableSimple m_lazyVar;

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        transformerImpl.getXPathContext().getVarStack().setLocalVariable(this.m_index, this.m_lazyVar);
    }

    @Override
    public void setSelect(XPath xPath) {
        super.setSelect(xPath);
        this.m_lazyVar = new XUnresolvedVariableSimple(this);
    }
}

