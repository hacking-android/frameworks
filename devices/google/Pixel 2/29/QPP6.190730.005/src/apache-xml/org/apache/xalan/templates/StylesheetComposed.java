/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.DecimalFormatProperties;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xalan.templates.NamespaceAlias;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.WhiteSpaceInfo;

public class StylesheetComposed
extends Stylesheet {
    static final long serialVersionUID = -3444072247410233923L;
    private int m_endImportCountComposed;
    private int m_importCountComposed;
    private int m_importNumber = -1;
    private transient Vector m_includesComposed;

    public StylesheetComposed(Stylesheet stylesheet) {
        super(stylesheet);
    }

    public int getEndImportCountComposed() {
        return this.m_endImportCountComposed;
    }

    public StylesheetComposed getImportComposed(int n) throws ArrayIndexOutOfBoundsException {
        return this.getStylesheetRoot().getGlobalImport(this.m_importNumber + 1 + n);
    }

    public int getImportCountComposed() {
        return this.m_importCountComposed;
    }

    public Stylesheet getIncludeComposed(int n) throws ArrayIndexOutOfBoundsException {
        if (-1 == n) {
            return this;
        }
        Vector vector = this.m_includesComposed;
        if (vector != null) {
            return (Stylesheet)vector.elementAt(n);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public int getIncludeCountComposed() {
        Vector vector = this.m_includesComposed;
        int n = vector != null ? vector.size() : 0;
        return n;
    }

    @Override
    public boolean isAggregatedType() {
        return true;
    }

    public void recompose(Vector vector) throws TransformerException {
        int n = this.getIncludeCountComposed();
        for (int i = -1; i < n; ++i) {
            int n2;
            Stylesheet stylesheet = this.getIncludeComposed(i);
            int n3 = stylesheet.getOutputCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getOutput(n2));
            }
            n3 = stylesheet.getAttributeSetCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getAttributeSet(n2));
            }
            n3 = stylesheet.getDecimalFormatCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getDecimalFormat(n2));
            }
            n3 = stylesheet.getKeyCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getKey(n2));
            }
            n3 = stylesheet.getNamespaceAliasCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getNamespaceAlias(n2));
            }
            n3 = stylesheet.getTemplateCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getTemplate(n2));
            }
            n3 = stylesheet.getVariableOrParamCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getVariableOrParam(n2));
            }
            n3 = stylesheet.getStripSpaceCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getStripSpace(n2));
            }
            n3 = stylesheet.getPreserveSpaceCount();
            for (n2 = 0; n2 < n3; ++n2) {
                vector.addElement(stylesheet.getPreserveSpace(n2));
            }
        }
    }

    void recomposeImports() {
        int n;
        this.m_importNumber = this.getStylesheetRoot().getImportNumber(this);
        this.m_importCountComposed = this.getStylesheetRoot().getGlobalImportCount() - this.m_importNumber - 1;
        int n2 = this.getImportCount();
        if (n2 > 0) {
            this.m_endImportCountComposed += n2;
            while (n2 > 0) {
                n = this.m_endImportCountComposed;
                this.m_endImportCountComposed = n + this.getImport(--n2).getEndImportCountComposed();
            }
        }
        n2 = this.getIncludeCountComposed();
        while (n2 > 0) {
            n = n2 - 1;
            n2 = this.getIncludeComposed(n).getImportCount();
            this.m_endImportCountComposed += n2;
            while (n2 > 0) {
                int n3 = this.m_endImportCountComposed;
                Stylesheet stylesheet = this.getIncludeComposed(n);
                this.m_endImportCountComposed = n3 + stylesheet.getImport(--n2).getEndImportCountComposed();
            }
            n2 = n;
        }
    }

    void recomposeIncludes(Stylesheet stylesheet) {
        int n = stylesheet.getIncludeCount();
        if (n > 0) {
            if (this.m_includesComposed == null) {
                this.m_includesComposed = new Vector();
            }
            for (int i = 0; i < n; ++i) {
                Stylesheet stylesheet2 = stylesheet.getInclude(i);
                this.m_includesComposed.addElement(stylesheet2);
                this.recomposeIncludes(stylesheet2);
            }
        }
    }

    public void recomposeTemplates(boolean bl) throws TransformerException {
    }
}

