/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xalan.templates.ElemTemplateElement;

public class ElemExtensionScript
extends ElemTemplateElement {
    static final long serialVersionUID = -6995978265966057744L;
    private String m_lang = null;
    private String m_src = null;

    public String getLang() {
        return this.m_lang;
    }

    public String getSrc() {
        return this.m_src;
    }

    @Override
    public int getXSLToken() {
        return 86;
    }

    public void setLang(String string) {
        this.m_lang = string;
    }

    public void setSrc(String string) {
        this.m_src = string;
    }
}

