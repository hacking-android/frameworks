/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.Serializable;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xml.utils.QName;
import org.apache.xpath.XPathContext;
import org.apache.xpath.patterns.StepPattern;

class TemplateSubPatternAssociation
implements Serializable,
Cloneable {
    static final long serialVersionUID = -8902606755229903350L;
    private TemplateSubPatternAssociation m_next = null;
    private String m_pattern;
    StepPattern m_stepPattern;
    private String m_targetString;
    private ElemTemplate m_template;
    private boolean m_wild;

    TemplateSubPatternAssociation(ElemTemplate elemTemplate, StepPattern stepPattern, String string) {
        this.m_pattern = string;
        this.m_template = elemTemplate;
        this.m_stepPattern = stepPattern;
        this.m_targetString = this.m_stepPattern.getTargetString();
        this.m_wild = this.m_targetString.equals("*");
    }

    private boolean matchModes(QName qName, QName qName2) {
        boolean bl = qName == null && qName2 == null || qName != null && qName2 != null && qName.equals(qName2);
        return bl;
    }

    public Object clone() throws CloneNotSupportedException {
        TemplateSubPatternAssociation templateSubPatternAssociation = (TemplateSubPatternAssociation)super.clone();
        templateSubPatternAssociation.m_next = null;
        return templateSubPatternAssociation;
    }

    public int getDocOrderPos() {
        return this.m_template.getUid();
    }

    public final int getImportLevel() {
        return this.m_template.getStylesheetComposed().getImportCountComposed();
    }

    public final TemplateSubPatternAssociation getNext() {
        return this.m_next;
    }

    public final String getPattern() {
        return this.m_pattern;
    }

    public final StepPattern getStepPattern() {
        return this.m_stepPattern;
    }

    public final String getTargetString() {
        return this.m_targetString;
    }

    public final ElemTemplate getTemplate() {
        return this.m_template;
    }

    public final boolean isWild() {
        return this.m_wild;
    }

    boolean matchMode(QName qName) {
        return this.matchModes(qName, this.m_template.getMode());
    }

    public boolean matches(XPathContext xPathContext, int n, QName qName) throws TransformerException {
        boolean bl = Double.NEGATIVE_INFINITY != this.m_stepPattern.getMatchScore(xPathContext, n) && this.matchModes(qName, this.m_template.getMode());
        return bl;
    }

    public void setNext(TemplateSubPatternAssociation templateSubPatternAssociation) {
        this.m_next = templateSubPatternAssociation;
    }

    public void setTargetString(String string) {
        this.m_targetString = string;
    }
}

