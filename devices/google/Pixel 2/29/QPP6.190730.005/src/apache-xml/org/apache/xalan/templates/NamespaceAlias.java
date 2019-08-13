/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;

public class NamespaceAlias
extends ElemTemplateElement {
    static final long serialVersionUID = 456173966637810718L;
    private String m_ResultNamespace;
    private String m_ResultPrefix;
    private String m_StylesheetNamespace;
    private String m_StylesheetPrefix;

    public NamespaceAlias(int n) {
        this.m_docOrderNumber = n;
    }

    public String getResultNamespace() {
        return this.m_ResultNamespace;
    }

    public String getResultPrefix() {
        return this.m_ResultPrefix;
    }

    public String getStylesheetNamespace() {
        return this.m_StylesheetNamespace;
    }

    public String getStylesheetPrefix() {
        return this.m_StylesheetPrefix;
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeNamespaceAliases(this);
    }

    public void setResultNamespace(String string) {
        this.m_ResultNamespace = string;
    }

    public void setResultPrefix(String string) {
        this.m_ResultPrefix = string;
    }

    public void setStylesheetNamespace(String string) {
        this.m_StylesheetNamespace = string;
    }

    public void setStylesheetPrefix(String string) {
        this.m_StylesheetPrefix = string;
    }
}

