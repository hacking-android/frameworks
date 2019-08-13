/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FunctionDef1Arg
extends FunctionOneArg {
    static final long serialVersionUID = 2325189412814149264L;

    public boolean Arg0IsNodesetExpr() {
        boolean bl = this.m_arg0 == null ? true : this.m_arg0.isNodesetExpr();
        return bl;
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        boolean bl = this.m_arg0 == null ? false : super.canTraverseOutsideSubtree();
        return bl;
    }

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n > 1) {
            this.reportWrongNumberArgs();
        }
    }

    protected int getArg0AsNode(XPathContext xPathContext) throws TransformerException {
        int n = this.m_arg0 == null ? xPathContext.getCurrentNode() : this.m_arg0.asNode(xPathContext);
        return n;
    }

    protected double getArg0AsNumber(XPathContext xPathContext) throws TransformerException {
        if (this.m_arg0 == null) {
            int n = xPathContext.getCurrentNode();
            if (-1 == n) {
                return 0.0;
            }
            return xPathContext.getDTM(n).getStringValue(n).toDouble();
        }
        return this.m_arg0.execute(xPathContext).num();
    }

    protected XMLString getArg0AsString(XPathContext xPathContext) throws TransformerException {
        if (this.m_arg0 == null) {
            int n = xPathContext.getCurrentNode();
            if (-1 == n) {
                return XString.EMPTYSTRING;
            }
            return xPathContext.getDTM(n).getStringValue(n);
        }
        return this.m_arg0.execute(xPathContext).xstr();
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createXPATHMessage("ER_ZERO_OR_ONE", null));
    }
}

