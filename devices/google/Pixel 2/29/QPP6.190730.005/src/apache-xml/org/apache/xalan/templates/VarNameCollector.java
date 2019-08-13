/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import org.apache.xml.utils.QName;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.operations.Variable;

public class VarNameCollector
extends XPathVisitor {
    Vector m_refs = new Vector();

    boolean doesOccur(QName qName) {
        return this.m_refs.contains(qName);
    }

    public int getVarCount() {
        return this.m_refs.size();
    }

    public void reset() {
        this.m_refs.removeAllElements();
    }

    @Override
    public boolean visitVariableRef(ExpressionOwner expressionOwner, Variable variable) {
        this.m_refs.addElement(variable.getQName());
        return true;
    }
}

