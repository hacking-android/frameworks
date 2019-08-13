/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemWhen;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;

public class ElemChoose
extends ElemTemplateElement {
    static final long serialVersionUID = -3070117361903102033L;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        block0 : {
            int n = elemTemplateElement.getXSLToken();
            if (n == 38 || n == 39) break block0;
            this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        }
        return super.appendChild(elemTemplateElement);
    }

    @Override
    public boolean canAcceptVariables() {
        return false;
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        boolean bl = false;
        for (ElemTemplateElement elemTemplateElement = this.getFirstChildElem(); elemTemplateElement != null; elemTemplateElement = elemTemplateElement.getNextSiblingElem()) {
            int n = elemTemplateElement.getXSLToken();
            if (38 == n) {
                bl = true;
                ElemWhen elemWhen = (ElemWhen)elemTemplateElement;
                XPathContext xPathContext = transformerImpl.getXPathContext();
                n = xPathContext.getCurrentNode();
                if (!elemWhen.getTest().bool(xPathContext, n, elemWhen)) continue;
                transformerImpl.executeChildTemplates((ElemTemplateElement)elemWhen, true);
                return;
            }
            if (39 != n) continue;
            transformerImpl.executeChildTemplates(elemTemplateElement, true);
            return;
        }
        if (!bl) {
            transformerImpl.getMsgMgr().error(this, "ER_CHOOSE_REQUIRES_WHEN");
        }
    }

    @Override
    public String getNodeName() {
        return "choose";
    }

    @Override
    public int getXSLToken() {
        return 37;
    }
}

