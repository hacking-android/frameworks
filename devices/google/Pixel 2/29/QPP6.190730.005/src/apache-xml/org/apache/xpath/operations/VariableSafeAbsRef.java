/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.operations;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.operations.Variable;

public class VariableSafeAbsRef
extends Variable {
    static final long serialVersionUID = -9174661990819967452L;

    @Override
    public XObject execute(XPathContext xPathContext, boolean bl) throws TransformerException {
        XNodeSet xNodeSet = (XNodeSet)super.execute(xPathContext, bl);
        DTMManager dTMManager = xPathContext.getDTMManager();
        int n = xPathContext.getContextNode();
        XNodeSet xNodeSet2 = xNodeSet;
        if (dTMManager.getDTM(xNodeSet.getRoot()).getDocument() != dTMManager.getDTM(n).getDocument()) {
            xNodeSet2 = (XNodeSet)((Expression)((Object)xNodeSet.getContainedIter())).asIterator(xPathContext, n);
        }
        return xNodeSet2;
    }
}

