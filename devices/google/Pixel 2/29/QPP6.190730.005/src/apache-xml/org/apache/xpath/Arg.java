/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xml.utils.QName;
import org.apache.xpath.objects.XObject;

public class Arg {
    private String m_expression;
    private boolean m_isFromWithParam;
    private boolean m_isVisible;
    private QName m_qname;
    private XObject m_val;

    public Arg() {
        this.m_qname = new QName("");
        this.m_val = null;
        this.m_expression = null;
        this.m_isVisible = true;
        this.m_isFromWithParam = false;
    }

    public Arg(QName qName, String string, boolean bl) {
        this.m_qname = qName;
        this.m_val = null;
        this.m_expression = string;
        this.m_isFromWithParam = bl;
        this.m_isVisible = bl ^ true;
    }

    public Arg(QName qName, XObject xObject) {
        this.m_qname = qName;
        this.m_val = xObject;
        this.m_isVisible = true;
        this.m_isFromWithParam = false;
        this.m_expression = null;
    }

    public Arg(QName qName, XObject xObject, boolean bl) {
        this.m_qname = qName;
        this.m_val = xObject;
        this.m_isFromWithParam = bl;
        this.m_isVisible = bl ^ true;
        this.m_expression = null;
    }

    public void detach() {
        XObject xObject = this.m_val;
        if (xObject != null) {
            xObject.allowDetachToRelease(true);
            this.m_val.detach();
        }
    }

    public boolean equals(Object object) {
        if (object instanceof QName) {
            return this.m_qname.equals(object);
        }
        return super.equals(object);
    }

    public String getExpression() {
        return this.m_expression;
    }

    public final QName getQName() {
        return this.m_qname;
    }

    public final XObject getVal() {
        return this.m_val;
    }

    public boolean isFromWithParam() {
        return this.m_isFromWithParam;
    }

    public boolean isVisible() {
        return this.m_isVisible;
    }

    public void setExpression(String string) {
        this.m_expression = string;
    }

    public void setIsVisible(boolean bl) {
        this.m_isVisible = bl;
    }

    public final void setQName(QName qName) {
        this.m_qname = qName;
    }

    public final void setVal(XObject xObject) {
        this.m_val = xObject;
    }
}

