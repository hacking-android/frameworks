/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xpath.XPath;

public class WhiteSpaceInfo
extends ElemTemplate {
    static final long serialVersionUID = 6389208261999943836L;
    private boolean m_shouldStripSpace;

    public WhiteSpaceInfo(Stylesheet stylesheet) {
        this.setStylesheet(stylesheet);
    }

    public WhiteSpaceInfo(XPath xPath, boolean bl, Stylesheet stylesheet) {
        this.m_shouldStripSpace = bl;
        this.setMatch(xPath);
        this.setStylesheet(stylesheet);
    }

    public boolean getShouldStripSpace() {
        return this.m_shouldStripSpace;
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeWhiteSpaceInfo(this);
    }
}

