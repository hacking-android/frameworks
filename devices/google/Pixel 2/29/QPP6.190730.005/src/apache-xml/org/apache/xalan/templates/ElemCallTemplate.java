/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemParam;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemWithParam;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class ElemCallTemplate
extends ElemForEach {
    static final long serialVersionUID = 5009634612916030591L;
    protected ElemWithParam[] m_paramElems = null;
    private ElemTemplate m_template = null;
    public QName m_templateName = null;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        if (2 == elemTemplateElement.getXSLToken()) {
            this.setParamElem((ElemWithParam)elemTemplateElement);
        }
        return super.appendChild(elemTemplateElement);
    }

    @Override
    public void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot elemTemplateElement) throws TransformerException {
        int n;
        super.compose((StylesheetRoot)elemTemplateElement);
        int n2 = this.getParamElemCount();
        for (n = 0; n < n2; ++n) {
            this.getParamElem(n).compose((StylesheetRoot)elemTemplateElement);
        }
        if (this.m_templateName != null && this.m_template == null) {
            this.m_template = this.getStylesheetRoot().getTemplateComposed(this.m_templateName);
            if (this.m_template != null) {
                int n3 = this.getParamElemCount();
                for (n = 0; n < n3; ++n) {
                    ElemWithParam elemWithParam = this.getParamElem(n);
                    elemWithParam.m_index = -1;
                    n2 = 0;
                    for (elemTemplateElement = this.m_template.getFirstChildElem(); elemTemplateElement != null && elemTemplateElement.getXSLToken() == 41; elemTemplateElement = elemTemplateElement.getNextSiblingElem()) {
                        if (((ElemParam)elemTemplateElement).getName().equals(elemWithParam.getName())) {
                            elemWithParam.m_index = n2;
                        }
                        ++n2;
                    }
                }
            } else {
                throw new TransformerException(XSLMessages.createMessage("ER_ELEMTEMPLATEELEM_ERR", new Object[]{this.m_templateName}), this);
            }
        }
    }

    @Override
    public void endCompose(StylesheetRoot stylesheetRoot) throws TransformerException {
        int n = this.getParamElemCount();
        for (int i = 0; i < n; ++i) {
            this.getParamElem(i).endCompose(stylesheetRoot);
        }
        super.endCompose(stylesheetRoot);
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        if (this.m_template != null) {
            XPathContext xPathContext = transformerImpl.getXPathContext();
            VariableStack variableStack = xPathContext.getVarStack();
            int n = variableStack.getStackFrame();
            int n2 = variableStack.link(this.m_template.m_frameSize);
            if (this.m_template.m_inArgsSize > 0) {
                variableStack.clearLocalSlots(0, this.m_template.m_inArgsSize);
                if (this.m_paramElems != null) {
                    int n3 = xPathContext.getCurrentNode();
                    variableStack.setStackFrame(n);
                    for (ElemWithParam sourceLocator2 : this.m_paramElems) {
                        if (sourceLocator2.m_index < 0) continue;
                        XObject xObject = sourceLocator2.getValue(transformerImpl, n3);
                        variableStack.setLocalVariable(sourceLocator2.m_index, xObject, n2);
                    }
                    variableStack.setStackFrame(n2);
                }
            }
            SourceLocator sourceLocator = xPathContext.getSAXLocator();
            try {
                xPathContext.setSAXLocator(this.m_template);
                transformerImpl.pushElemTemplateElement(this.m_template);
                this.m_template.execute(transformerImpl);
            }
            finally {
                transformerImpl.popElemTemplateElement();
                xPathContext.setSAXLocator(sourceLocator);
                variableStack.unlink(n);
            }
        } else {
            transformerImpl.getMsgMgr().error((SourceLocator)this, "ER_TEMPLATE_NOT_FOUND", new Object[]{this.m_templateName});
        }
    }

    public QName getName() {
        return this.m_templateName;
    }

    @Override
    public String getNodeName() {
        return "call-template";
    }

    public ElemWithParam getParamElem(int n) {
        return this.m_paramElems[n];
    }

    public int getParamElemCount() {
        ElemWithParam[] arrelemWithParam = this.m_paramElems;
        int n = arrelemWithParam == null ? 0 : arrelemWithParam.length;
        return n;
    }

    @Override
    public int getXSLToken() {
        return 17;
    }

    public void setName(QName qName) {
        this.m_templateName = qName;
    }

    public void setParamElem(ElemWithParam elemWithParam) {
        ElemWithParam[] arrelemWithParam = this.m_paramElems;
        if (arrelemWithParam == null) {
            this.m_paramElems = new ElemWithParam[1];
            this.m_paramElems[0] = elemWithParam;
        } else {
            int n = arrelemWithParam.length;
            ElemWithParam[] arrelemWithParam2 = new ElemWithParam[n + 1];
            System.arraycopy(arrelemWithParam, 0, arrelemWithParam2, 0, n);
            this.m_paramElems = arrelemWithParam2;
            arrelemWithParam2[n] = elemWithParam;
        }
    }
}

