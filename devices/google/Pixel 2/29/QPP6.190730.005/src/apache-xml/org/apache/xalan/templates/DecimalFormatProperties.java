/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.text.DecimalFormatSymbols;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.QName;

public class DecimalFormatProperties
extends ElemTemplateElement {
    static final long serialVersionUID = -6559409339256269446L;
    DecimalFormatSymbols m_dfs = new DecimalFormatSymbols();
    private QName m_qname = null;

    public DecimalFormatProperties(int n) {
        this.m_dfs.setInfinity("Infinity");
        this.m_dfs.setNaN("NaN");
        this.m_docOrderNumber = n;
    }

    public DecimalFormatSymbols getDecimalFormatSymbols() {
        return this.m_dfs;
    }

    public char getDecimalSeparator() {
        return this.m_dfs.getDecimalSeparator();
    }

    public char getDigit() {
        return this.m_dfs.getDigit();
    }

    public char getGroupingSeparator() {
        return this.m_dfs.getGroupingSeparator();
    }

    public String getInfinity() {
        return this.m_dfs.getInfinity();
    }

    public char getMinusSign() {
        return this.m_dfs.getMinusSign();
    }

    public String getNaN() {
        return this.m_dfs.getNaN();
    }

    public QName getName() {
        QName qName = this.m_qname;
        if (qName == null) {
            return new QName("");
        }
        return qName;
    }

    @Override
    public String getNodeName() {
        return "decimal-format";
    }

    public char getPatternSeparator() {
        return this.m_dfs.getPatternSeparator();
    }

    public char getPerMille() {
        return this.m_dfs.getPerMill();
    }

    public char getPercent() {
        return this.m_dfs.getPercent();
    }

    @Override
    public int getXSLToken() {
        return 83;
    }

    public char getZeroDigit() {
        return this.m_dfs.getZeroDigit();
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) {
        stylesheetRoot.recomposeDecimalFormats(this);
    }

    public void setDecimalSeparator(char c) {
        this.m_dfs.setDecimalSeparator(c);
    }

    public void setDigit(char c) {
        this.m_dfs.setDigit(c);
    }

    public void setGroupingSeparator(char c) {
        this.m_dfs.setGroupingSeparator(c);
    }

    public void setInfinity(String string) {
        this.m_dfs.setInfinity(string);
    }

    public void setMinusSign(char c) {
        this.m_dfs.setMinusSign(c);
    }

    public void setNaN(String string) {
        this.m_dfs.setNaN(string);
    }

    public void setName(QName qName) {
        this.m_qname = qName;
    }

    public void setPatternSeparator(char c) {
        this.m_dfs.setPatternSeparator(c);
    }

    public void setPerMille(char c) {
        this.m_dfs.setPerMill(c);
    }

    public void setPercent(char c) {
        this.m_dfs.setPercent(c);
    }

    public void setZeroDigit(char c) {
        this.m_dfs.setZeroDigit(c);
    }
}

