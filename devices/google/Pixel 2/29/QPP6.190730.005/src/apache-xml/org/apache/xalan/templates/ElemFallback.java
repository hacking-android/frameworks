/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.PrintStream;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.TransformerImpl;

public class ElemFallback
extends ElemTemplateElement {
    static final long serialVersionUID = 1782962139867340703L;

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
    }

    public void executeFallback(TransformerImpl transformerImpl) throws TransformerException {
        int n = this.m_parentNode.getXSLToken();
        if (79 != n && -1 != n) {
            System.out.println("Error!  parent of xsl:fallback must be an extension or unknown element!");
        } else {
            transformerImpl.executeChildTemplates((ElemTemplateElement)this, true);
        }
    }

    @Override
    public String getNodeName() {
        return "fallback";
    }

    @Override
    public int getXSLToken() {
        return 57;
    }
}

