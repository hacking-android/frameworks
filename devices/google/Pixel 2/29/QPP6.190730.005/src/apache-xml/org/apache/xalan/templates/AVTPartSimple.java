/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import org.apache.xalan.templates.AVTPart;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPathContext;

public class AVTPartSimple
extends AVTPart {
    static final long serialVersionUID = -3744957690598727913L;
    private String m_val;

    public AVTPartSimple(String string) {
        this.m_val = string;
    }

    @Override
    public void callVisitors(XSLTVisitor xSLTVisitor) {
    }

    @Override
    public void evaluate(XPathContext xPathContext, FastStringBuffer fastStringBuffer, int n, PrefixResolver prefixResolver) {
        fastStringBuffer.append(this.m_val);
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }

    @Override
    public String getSimpleString() {
        return this.m_val;
    }
}

