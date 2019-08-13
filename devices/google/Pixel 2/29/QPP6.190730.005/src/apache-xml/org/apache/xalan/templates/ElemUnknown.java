/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemFallback;
import org.apache.xalan.templates.ElemLiteralResult;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformerImpl;

public class ElemUnknown
extends ElemLiteralResult {
    static final long serialVersionUID = -4573981712648730168L;

    private void executeFallbacks(TransformerImpl transformerImpl) throws TransformerException {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            if (elemTemplateElement.getXSLToken() == 57) {
                try {
                    transformerImpl.pushElemTemplateElement(elemTemplateElement);
                    ((ElemFallback)elemTemplateElement).executeFallback(transformerImpl);
                }
                finally {
                    transformerImpl.popElemTemplateElement();
                }
            }
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
    }

    private boolean hasFallbackChildren() {
        ElemTemplateElement elemTemplateElement = this.m_firstChild;
        while (elemTemplateElement != null) {
            if (elemTemplateElement.getXSLToken() == 57) {
                return true;
            }
            elemTemplateElement = elemTemplateElement.m_nextSibling;
        }
        return false;
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        try {
            if (this.hasFallbackChildren()) {
                this.executeFallbacks(transformerImpl);
            }
        }
        catch (TransformerException transformerException) {
            transformerImpl.getErrorListener().fatalError(transformerException);
        }
    }

    @Override
    public int getXSLToken() {
        return -1;
    }
}

