/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTMManager;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.functions.Function;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.StepPattern;

public class FuncCurrent
extends Function {
    static final long serialVersionUID = 5715316804877715008L;

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        SubContextList subContextList = xPathContext.getCurrentNodeList();
        int n = -1;
        if (subContextList != null) {
            if (subContextList instanceof PredicatedNodeTest) {
                n = ((PredicatedNodeTest)subContextList).getLocPathIterator().getCurrentContextNode();
            } else if (subContextList instanceof StepPattern) {
                throw new RuntimeException(XSLMessages.createMessage("ER_PROCESSOR_ERROR", null));
            }
        } else {
            n = xPathContext.getContextNode();
        }
        return new XNodeSet(n, xPathContext.getDTMManager());
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }
}

