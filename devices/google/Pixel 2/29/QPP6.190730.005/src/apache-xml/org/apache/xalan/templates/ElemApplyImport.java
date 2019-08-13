/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xpath.XPathContext;

public class ElemApplyImport
extends ElemTemplateElement {
    static final long serialVersionUID = 3764728663373024038L;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        return null;
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        int n;
        if (transformerImpl.currentTemplateRuleIsNull()) {
            transformerImpl.getMsgMgr().error(this, "ER_NO_APPLY_IMPORT_IN_FOR_EACH");
        }
        if (-1 != (n = transformerImpl.getXPathContext().getCurrentNode())) {
            transformerImpl.applyTemplateToNode(this, transformerImpl.getMatchedTemplate(), n);
        } else {
            transformerImpl.getMsgMgr().error(this, "ER_NULL_SOURCENODE_APPLYIMPORTS");
        }
    }

    @Override
    public String getNodeName() {
        return "apply-imports";
    }

    @Override
    public int getXSLToken() {
        return 72;
    }
}

