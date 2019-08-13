/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionMultiArgs;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncConcat
extends FunctionMultiArgs {
    static final long serialVersionUID = 1737228885202314413L;

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n < 2) {
            this.reportWrongNumberArgs();
        }
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.m_arg0.execute(xPathContext).str());
        stringBuffer.append(this.m_arg1.execute(xPathContext).str());
        if (this.m_arg2 != null) {
            stringBuffer.append(this.m_arg2.execute(xPathContext).str());
        }
        if (this.m_args != null) {
            for (int i = 0; i < this.m_args.length; ++i) {
                stringBuffer.append(this.m_args[i].execute(xPathContext).str());
            }
        }
        return new XString(stringBuffer.toString());
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("gtone", null));
    }
}

