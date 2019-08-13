/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPath;

public class KeyDeclaration
extends ElemTemplateElement {
    static final long serialVersionUID = 7724030248631137918L;
    private XPath m_matchPattern = null;
    private QName m_name;
    private XPath m_use;

    public KeyDeclaration(Stylesheet stylesheet, int n) {
        this.m_parentNode = stylesheet;
        this.setUid(n);
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
        Vector vector = stylesheetRoot.getComposeState().getVariableNames();
        XPath xPath = this.m_matchPattern;
        if (xPath != null) {
            xPath.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        }
        if ((xPath = this.m_use) != null) {
            xPath.fixupVariables(vector, stylesheetRoot.getComposeState().getGlobalsSize());
        }
    }

    public XPath getMatch() {
        return this.m_matchPattern;
    }

    public QName getName() {
        return this.m_name;
    }

    @Override
    public String getNodeName() {
        return "key";
    }

    public XPath getUse() {
        return this.m_use;
    }

    @Override
    public int getXSLToken() {
        return 31;
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeKeys(this);
    }

    public void setMatch(XPath xPath) {
        this.m_matchPattern = xPath;
    }

    public void setName(QName qName) {
        this.m_name = qName;
    }

    public void setUse(XPath xPath) {
        this.m_use = xPath;
    }
}

