/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemCallTemplate;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.QName;

public class ElemApplyTemplates
extends ElemCallTemplate {
    static final long serialVersionUID = 2903125371542621004L;
    private boolean m_isDefaultTemplate = false;
    private QName m_mode = null;

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        boolean bl;
        transformerImpl.pushCurrentTemplateRuleIsNull(false);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = bl2;
        try {
            block7 : {
                block9 : {
                    QName qName;
                    block8 : {
                        qName = transformerImpl.getMode();
                        bl = bl3;
                        bl4 = bl2;
                        if (this.m_isDefaultTemplate) break block7;
                        if (qName != null) break block8;
                        bl4 = bl2;
                        if (this.m_mode != null) break block9;
                    }
                    bl = bl3;
                    if (qName == null) break block7;
                    bl = bl3;
                    bl4 = bl2;
                    if (qName.equals(this.m_mode)) break block7;
                }
                bl4 = true;
                bl = true;
                transformerImpl.pushMode(this.m_mode);
            }
            bl4 = bl;
            this.transformSelectedNodes(transformerImpl);
            return;
        }
        finally {
            if (bl) {
                transformerImpl.popMode();
            }
            transformerImpl.popCurrentTemplateRuleIsNull();
        }
    }

    public QName getMode() {
        return this.m_mode;
    }

    @Override
    public String getNodeName() {
        return "apply-templates";
    }

    @Override
    public int getXSLToken() {
        return 50;
    }

    public void setIsDefaultTemplate(boolean bl) {
        this.m_isDefaultTemplate = bl;
    }

    public void setMode(QName qName) {
        this.m_mode = qName;
    }

    /*
     * Exception decompiling
     */
    @Override
    public void transformSelectedNodes(TransformerImpl var1_1) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [12[TRYBLOCK]], but top level block is 47[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }
}

