/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class ElemParam
extends ElemVariable {
    static final long serialVersionUID = -1131781475589006431L;
    int m_qnameID;

    public ElemParam() {
    }

    public ElemParam(ElemParam elemParam) throws TransformerException {
        super(elemParam);
    }

    @Override
    public void compose(StylesheetRoot elemTemplateElement) throws TransformerException {
        super.compose((StylesheetRoot)elemTemplateElement);
        this.m_qnameID = elemTemplateElement.getComposeState().getQNameID(this.m_qname);
        int n = this.m_parentNode.getXSLToken();
        if (n == 19 || n == 88) {
            elemTemplateElement = (ElemTemplate)this.m_parentNode;
            ++((ElemTemplate)elemTemplateElement).m_inArgsSize;
        }
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        if (!transformerImpl.getXPathContext().getVarStack().isLocalSet(this.m_index)) {
            XObject xObject = this.getValue(transformerImpl, transformerImpl.getXPathContext().getCurrentNode());
            transformerImpl.getXPathContext().getVarStack().setLocalVariable(this.m_index, xObject);
        }
    }

    @Override
    public String getNodeName() {
        return "param";
    }

    @Override
    public int getXSLToken() {
        return 41;
    }
}

