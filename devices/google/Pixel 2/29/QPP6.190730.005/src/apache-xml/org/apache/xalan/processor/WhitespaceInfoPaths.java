/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.util.Vector;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.WhiteSpaceInfo;

public class WhitespaceInfoPaths
extends WhiteSpaceInfo {
    static final long serialVersionUID = 5954766719577516723L;
    private Vector m_elements;

    public WhitespaceInfoPaths(Stylesheet stylesheet) {
        super(stylesheet);
        this.setStylesheet(stylesheet);
    }

    public void clearElements() {
        this.m_elements = null;
    }

    Vector getElements() {
        return this.m_elements;
    }

    public void setElements(Vector vector) {
        this.m_elements = vector;
    }
}

