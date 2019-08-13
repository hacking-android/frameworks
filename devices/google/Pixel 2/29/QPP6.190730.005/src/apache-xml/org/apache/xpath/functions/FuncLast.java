/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncLast
extends Function {
    static final long serialVersionUID = 9205812403085432943L;
    private boolean m_isTopLevel;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return new XNumber(this.getCountOfContextNodeList(xPathContext));
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }

    public int getCountOfContextNodeList(XPathContext object) throws TransformerException {
        SubContextList subContextList = this.m_isTopLevel ? null : ((XPathContext)object).getSubContextList();
        if (subContextList != null) {
            return subContextList.getLastPos((XPathContext)object);
        }
        int n = (object = ((XPathContext)object).getContextNodeList()) != null ? object.getLength() : 0;
        return n;
    }

    @Override
    public void postCompileStep(Compiler compiler) {
        boolean bl = compiler.getLocationPathDepth() == -1;
        this.m_isTopLevel = bl;
    }
}

