/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XObject;

public class XUnresolvedVariable
extends XObject {
    static final long serialVersionUID = -256779804767950188L;
    private transient int m_context;
    private transient boolean m_doneEval = true;
    private boolean m_isGlobal;
    private transient TransformerImpl m_transformer;
    private transient int m_varStackContext;
    private transient int m_varStackPos = -1;

    public XUnresolvedVariable(ElemVariable elemVariable, int n, TransformerImpl transformerImpl, int n2, int n3, boolean bl) {
        super(elemVariable);
        this.m_context = n;
        this.m_transformer = transformerImpl;
        this.m_varStackPos = n2;
        this.m_varStackContext = n3;
        this.m_isGlobal = bl;
    }

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        if (!this.m_doneEval) {
            this.m_transformer.getMsgMgr().error(((XPathContext)object).getSAXLocator(), "ER_REFERENCING_ITSELF", new Object[]{((ElemVariable)this.object()).getName().getLocalName()});
        }
        VariableStack variableStack = ((XPathContext)object).getVarStack();
        int n = variableStack.getStackFrame();
        object = (ElemVariable)this.m_obj;
        try {
            this.m_doneEval = false;
            if (-1 != ((ElemVariable)object).m_frameSize) {
                variableStack.link(((ElemVariable)object).m_frameSize);
            }
            XObject xObject = ((ElemVariable)object).getValue(this.m_transformer, this.m_context);
            this.m_doneEval = true;
            return xObject;
        }
        finally {
            if (-1 != ((ElemVariable)object).m_frameSize) {
                variableStack.unlink(n);
            }
        }
    }

    @Override
    public int getType() {
        return 600;
    }

    @Override
    public String getTypeString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("XUnresolvedVariable (");
        stringBuilder.append(this.object().getClass().getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void setVarStackContext(int n) {
        this.m_varStackContext = n;
    }

    public void setVarStackPos(int n) {
        this.m_varStackPos = n;
    }
}

