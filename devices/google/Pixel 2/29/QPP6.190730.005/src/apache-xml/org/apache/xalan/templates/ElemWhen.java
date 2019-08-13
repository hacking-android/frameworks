/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathVisitor;

public class ElemWhen
extends ElemTemplateElement {
    static final long serialVersionUID = 5984065730262071360L;
    private XPath m_test;

    @Override
    protected void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        if (bl) {
            this.m_test.getExpression().callVisitors(this.m_test, xSLTVisitor);
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        Vector vector = stylesheetRoot.getComposeState().getVariableNames();
        XPath xPath = this.m_test;
        if (xPath != null) {
            xPath.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        }
    }

    @Override
    public String getNodeName() {
        return "when";
    }

    public XPath getTest() {
        return this.m_test;
    }

    @Override
    public int getXSLToken() {
        return 38;
    }

    public void setTest(XPath xPath) {
        this.m_test = xPath;
    }
}

