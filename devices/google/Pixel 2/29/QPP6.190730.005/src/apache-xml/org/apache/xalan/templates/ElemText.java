/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xalan.templates.ElemTemplateElement;

public class ElemText
extends ElemTemplateElement {
    static final long serialVersionUID = 1383140876182316711L;
    private boolean m_disableOutputEscaping = false;

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        block0 : {
            if (elemTemplateElement.getXSLToken() == 78) break block0;
            this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        }
        return super.appendChild(elemTemplateElement);
    }

    public boolean getDisableOutputEscaping() {
        return this.m_disableOutputEscaping;
    }

    @Override
    public String getNodeName() {
        return "text";
    }

    @Override
    public int getXSLToken() {
        return 42;
    }

    public void setDisableOutputEscaping(boolean bl) {
        this.m_disableOutputEscaping = bl;
    }
}

