/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class FuncPosition
extends Function {
    static final long serialVersionUID = -9092846348197271582L;
    private boolean m_isTopLevel;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return new XNumber(this.getPositionInContextNodeList(xPathContext));
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int getPositionInContextNodeList(XPathContext xPathContext) {
        int n;
        int n2;
        Object object = this.m_isTopLevel ? null : xPathContext.getSubContextList();
        if (object != null) {
            return object.getProximityPosition(xPathContext);
        }
        DTMIterator dTMIterator = xPathContext.getContextNodeList();
        if (dTMIterator == null) return -1;
        object = dTMIterator;
        if (dTMIterator.getCurrentNode() != -1) return object.getCurrentPos();
        if (dTMIterator.getCurrentPos() == 0) {
            return 0;
        }
        try {
            dTMIterator = dTMIterator.cloneWithReset();
            n2 = xPathContext.getContextNode();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new WrappedRuntimeException(cloneNotSupportedException);
        }
        do {
            n = dTMIterator.nextNode();
            object = dTMIterator;
            if (-1 == n) return object.getCurrentPos();
        } while (n != n2);
        object = dTMIterator;
        return object.getCurrentPos();
    }

    @Override
    public void postCompileStep(Compiler compiler) {
        boolean bl = compiler.getLocationPathDepth() == -1;
        this.m_isTopLevel = bl;
    }
}

