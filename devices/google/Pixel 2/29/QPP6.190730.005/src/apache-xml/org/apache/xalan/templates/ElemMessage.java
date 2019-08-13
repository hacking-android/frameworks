/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;

public class ElemMessage
extends ElemTemplateElement {
    static final long serialVersionUID = 1530472462155060023L;
    private boolean m_terminate = false;

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        String string = transformerImpl.transformToString(this);
        transformerImpl.getMsgMgr().message(this, string, this.m_terminate);
        if (this.m_terminate) {
            transformerImpl.getErrorListener().fatalError(new TransformerException(XSLMessages.createMessage("ER_STYLESHEET_DIRECTED_TERMINATION", null)));
        }
    }

    @Override
    public String getNodeName() {
        return "message";
    }

    public boolean getTerminate() {
        return this.m_terminate;
    }

    @Override
    public int getXSLToken() {
        return 75;
    }

    public void setTerminate(boolean bl) {
        this.m_terminate = bl;
    }
}

