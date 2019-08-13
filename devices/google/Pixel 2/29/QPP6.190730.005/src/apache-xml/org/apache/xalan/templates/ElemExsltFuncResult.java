/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class ElemExsltFuncResult
extends ElemVariable {
    static final long serialVersionUID = -3478311949388304563L;
    private int m_callerFrameSize = 0;
    private boolean m_isResultSet = false;
    private XObject m_result = null;

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        Object object = transformerImpl.getXPathContext();
        if (!transformerImpl.currentFuncResultSeen()) {
            object = this.getValue(transformerImpl, ((XPathContext)object).getCurrentNode());
            transformerImpl.popCurrentFuncResult();
            transformerImpl.pushCurrentFuncResult(object);
            return;
        }
        throw new TransformerException("An EXSLT function cannot set more than one result!");
    }

    @Override
    public String getNodeName() {
        return "result";
    }

    @Override
    public int getXSLToken() {
        return 89;
    }
}

