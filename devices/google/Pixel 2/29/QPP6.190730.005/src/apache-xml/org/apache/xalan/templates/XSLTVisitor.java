/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemExtensionCall;
import org.apache.xalan.templates.ElemLiteralResult;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xpath.XPathVisitor;

public class XSLTVisitor
extends XPathVisitor {
    public boolean visitAVT(AVT aVT) {
        return true;
    }

    public boolean visitExtensionElement(ElemExtensionCall elemExtensionCall) {
        return true;
    }

    public boolean visitInstruction(ElemTemplateElement elemTemplateElement) {
        return true;
    }

    public boolean visitLiteralResultElement(ElemLiteralResult elemLiteralResult) {
        return true;
    }

    public boolean visitStylesheet(ElemTemplateElement elemTemplateElement) {
        return true;
    }

    public boolean visitTopLevelInstruction(ElemTemplateElement elemTemplateElement) {
        return true;
    }

    public boolean visitTopLevelVariableOrParamDecl(ElemTemplateElement elemTemplateElement) {
        return true;
    }

    public boolean visitVariableOrParamDecl(ElemVariable elemVariable) {
        return true;
    }
}

