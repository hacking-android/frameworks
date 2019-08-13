/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.TemplateSubPatternAssociation;
import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xpath.Expression;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;
import org.apache.xpath.patterns.UnionPattern;

public class TemplateList
implements Serializable {
    static final boolean DEBUG = false;
    static final long serialVersionUID = 5803675288911728791L;
    private TemplateSubPatternAssociation m_commentPatterns = null;
    private TemplateSubPatternAssociation m_docPatterns = null;
    private Hashtable m_namedTemplates = new Hashtable(89);
    private Hashtable m_patternTable = new Hashtable(89);
    private TemplateSubPatternAssociation m_textPatterns = null;
    private TemplateSubPatternAssociation m_wildCardPatterns = null;

    private void addObjectIfNotFound(Object object, Vector vector) {
        boolean bl;
        int n = vector.size();
        boolean bl2 = true;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (vector.elementAt(n2) == object) {
                bl = false;
                break;
            }
            ++n2;
        } while (true);
        if (bl) {
            vector.addElement(object);
        }
    }

    private void checkConflicts(TemplateSubPatternAssociation templateSubPatternAssociation, XPathContext xPathContext, int n, QName qName) {
    }

    private TemplateSubPatternAssociation getHead(String string) {
        return (TemplateSubPatternAssociation)this.m_patternTable.get(string);
    }

    private Hashtable getNamedTemplates() {
        return this.m_namedTemplates;
    }

    private double getPriorityOrScore(TemplateSubPatternAssociation serializable) {
        double d = serializable.getTemplate().getPriority();
        if (d == Double.NEGATIVE_INFINITY && (serializable = serializable.getStepPattern()) instanceof NodeTest) {
            return ((NodeTest)serializable).getDefaultScore();
        }
        return d;
    }

    private TemplateSubPatternAssociation insertAssociationIntoList(TemplateSubPatternAssociation object, TemplateSubPatternAssociation templateSubPatternAssociation, boolean bl) {
        double d = this.getPriorityOrScore(templateSubPatternAssociation);
        int n = templateSubPatternAssociation.getImportLevel();
        int n2 = templateSubPatternAssociation.getDocOrderPos();
        TemplateSubPatternAssociation templateSubPatternAssociation2 = object;
        do {
            TemplateSubPatternAssociation templateSubPatternAssociation3;
            block14 : {
                double d2;
                block13 : {
                    if ((templateSubPatternAssociation3 = templateSubPatternAssociation2.getNext()) == null) break block13;
                    d2 = this.getPriorityOrScore(templateSubPatternAssociation3);
                    if (n > templateSubPatternAssociation3.getImportLevel()) break block13;
                    if (n < templateSubPatternAssociation3.getImportLevel()) {
                        templateSubPatternAssociation2 = templateSubPatternAssociation3;
                        continue;
                    }
                    if (d > d2) break block13;
                    if (d < d2) {
                        templateSubPatternAssociation2 = templateSubPatternAssociation3;
                        continue;
                    }
                    if (n2 < templateSubPatternAssociation3.getDocOrderPos()) break block14;
                }
                if (templateSubPatternAssociation3 != null && templateSubPatternAssociation2 != object) {
                    n2 = 0;
                } else {
                    d2 = this.getPriorityOrScore(templateSubPatternAssociation2);
                    n2 = n > templateSubPatternAssociation2.getImportLevel() ? 1 : (n < templateSubPatternAssociation2.getImportLevel() ? 0 : (d > d2 ? 1 : (d < d2 ? 0 : (n2 >= templateSubPatternAssociation2.getDocOrderPos() ? 1 : 0))));
                }
                if (bl) {
                    if (n2 != 0) {
                        templateSubPatternAssociation.setNext(templateSubPatternAssociation2);
                        object = templateSubPatternAssociation2.getTargetString();
                        templateSubPatternAssociation.setTargetString((String)object);
                        this.putHead((String)object, templateSubPatternAssociation);
                        return templateSubPatternAssociation;
                    }
                    templateSubPatternAssociation.setNext(templateSubPatternAssociation3);
                    templateSubPatternAssociation2.setNext(templateSubPatternAssociation);
                    return object;
                }
                if (n2 != 0) {
                    templateSubPatternAssociation.setNext(templateSubPatternAssociation2);
                    if (!templateSubPatternAssociation2.isWild() && !templateSubPatternAssociation.isWild()) {
                        this.putHead(templateSubPatternAssociation.getTargetString(), templateSubPatternAssociation);
                    } else {
                        this.m_wildCardPatterns = templateSubPatternAssociation;
                    }
                    return templateSubPatternAssociation;
                }
                templateSubPatternAssociation.setNext(templateSubPatternAssociation3);
                templateSubPatternAssociation2.setNext(templateSubPatternAssociation);
                return object;
            }
            templateSubPatternAssociation2 = templateSubPatternAssociation3;
        } while (true);
    }

    private void insertPatternInTable(StepPattern serializable, ElemTemplate serializable2) {
        String string = serializable.getTargetString();
        if (string != null) {
            boolean bl = ((TemplateSubPatternAssociation)(serializable2 = new TemplateSubPatternAssociation((ElemTemplate)serializable2, (StepPattern)serializable, ((ElemTemplate)serializable2).getMatch().getPatternString()))).isWild();
            if ((serializable = bl ? this.m_wildCardPatterns : this.getHead(string)) == null) {
                if (bl) {
                    this.m_wildCardPatterns = serializable2;
                } else {
                    this.putHead(string, (TemplateSubPatternAssociation)serializable2);
                }
            } else {
                this.insertAssociationIntoList((TemplateSubPatternAssociation)serializable, (TemplateSubPatternAssociation)serializable2, false);
            }
        }
    }

    private void putHead(String string, TemplateSubPatternAssociation templateSubPatternAssociation) {
        if (string.equals("#text")) {
            this.m_textPatterns = templateSubPatternAssociation;
        } else if (string.equals("/")) {
            this.m_docPatterns = templateSubPatternAssociation;
        } else if (string.equals("#comment")) {
            this.m_commentPatterns = templateSubPatternAssociation;
        }
        this.m_patternTable.put(string, templateSubPatternAssociation);
    }

    private void setNamedTemplates(Hashtable hashtable) {
        this.m_namedTemplates = hashtable;
    }

    public void compose(StylesheetRoot serializable) {
        if (this.m_wildCardPatterns != null) {
            Enumeration enumeration = this.m_patternTable.elements();
            while (enumeration.hasMoreElements()) {
                TemplateSubPatternAssociation templateSubPatternAssociation = (TemplateSubPatternAssociation)enumeration.nextElement();
                for (serializable = this.m_wildCardPatterns; serializable != null; serializable = serializable.getNext()) {
                    try {
                        TemplateSubPatternAssociation templateSubPatternAssociation2;
                        templateSubPatternAssociation = templateSubPatternAssociation2 = this.insertAssociationIntoList(templateSubPatternAssociation, (TemplateSubPatternAssociation)((TemplateSubPatternAssociation)serializable).clone(), true);
                        continue;
                    }
                    catch (CloneNotSupportedException cloneNotSupportedException) {
                        // empty catch block
                    }
                }
            }
        }
    }

    void dumpAssociationTables() {
        Appendable appendable;
        TemplateSubPatternAssociation templateSubPatternAssociation;
        Object object = this.m_patternTable.elements();
        while (object.hasMoreElements()) {
            for (templateSubPatternAssociation = (TemplateSubPatternAssociation)object.nextElement(); templateSubPatternAssociation != null; templateSubPatternAssociation = templateSubPatternAssociation.getNext()) {
                appendable = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("(");
                stringBuilder.append(templateSubPatternAssociation.getTargetString());
                stringBuilder.append(", ");
                stringBuilder.append(templateSubPatternAssociation.getPattern());
                stringBuilder.append(")");
                ((PrintStream)appendable).print(stringBuilder.toString());
            }
            System.out.println("\n.....");
        }
        System.out.print("wild card list: ");
        for (templateSubPatternAssociation = this.m_wildCardPatterns; templateSubPatternAssociation != null; templateSubPatternAssociation = templateSubPatternAssociation.getNext()) {
            object = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("(");
            ((StringBuilder)appendable).append(templateSubPatternAssociation.getTargetString());
            ((StringBuilder)appendable).append(", ");
            ((StringBuilder)appendable).append(templateSubPatternAssociation.getPattern());
            ((StringBuilder)appendable).append(")");
            ((PrintStream)object).print(((StringBuilder)appendable).toString());
        }
        System.out.println("\n.....");
    }

    public TemplateSubPatternAssociation getHead(XPathContext object, int n, DTM dTM) {
        block8 : {
            switch (dTM.getNodeType(n)) {
                default: {
                    object = (TemplateSubPatternAssociation)this.m_patternTable.get(dTM.getNodeName(n));
                    break;
                }
                case 9: 
                case 11: {
                    object = this.m_docPatterns;
                    break;
                }
                case 8: {
                    object = this.m_commentPatterns;
                    break;
                }
                case 7: {
                    object = (TemplateSubPatternAssociation)this.m_patternTable.get(dTM.getLocalName(n));
                    break;
                }
                case 5: 
                case 6: {
                    object = (TemplateSubPatternAssociation)this.m_patternTable.get(dTM.getNodeName(n));
                    break;
                }
                case 3: 
                case 4: {
                    object = this.m_textPatterns;
                    break;
                }
                case 1: 
                case 2: {
                    object = (TemplateSubPatternAssociation)this.m_patternTable.get(dTM.getLocalName(n));
                }
            }
            if (object != null) break block8;
            object = this.m_wildCardPatterns;
        }
        return object;
    }

    public ElemTemplate getTemplate(QName qName) {
        return (ElemTemplate)this.m_namedTemplates.get(qName);
    }

    /*
     * Exception decompiling
     */
    public ElemTemplate getTemplate(XPathContext var1_1, int var2_2, QName var3_3, int var4_5, int var5_6, boolean var6_7, DTM var7_8) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[DOLOOP]], but top level block is 2[TRYBLOCK]
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ElemTemplate getTemplate(XPathContext xPathContext, int n, QName qName, boolean bl, DTM object) throws TransformerException {
        if ((object = this.getHead(xPathContext, n, (DTM)object)) == null) return null;
        xPathContext.pushNamespaceContextNull();
        xPathContext.pushCurrentNodeAndExpression(n, n);
        try {
            Serializable serializable;
            do {
                serializable = ((TemplateSubPatternAssociation)object).getTemplate();
                xPathContext.setNamespaceContext((PrefixResolver)((Object)serializable));
                if (((TemplateSubPatternAssociation)object).m_stepPattern.execute(xPathContext, n) != NodeTest.SCORE_NONE && ((TemplateSubPatternAssociation)object).matchMode(qName)) {
                    if (bl) {
                        this.checkConflicts((TemplateSubPatternAssociation)object, xPathContext, n, qName);
                    }
                    xPathContext.popCurrentNodeAndExpression();
                    xPathContext.popNamespaceContext();
                    return serializable;
                }
                serializable = ((TemplateSubPatternAssociation)object).getNext();
                object = serializable;
            } while (serializable != null);
            xPathContext.popCurrentNodeAndExpression();
            xPathContext.popNamespaceContext();
            return null;
        }
        catch (Throwable throwable) {
            xPathContext.popCurrentNodeAndExpression();
            xPathContext.popNamespaceContext();
            throw throwable;
        }
    }

    /*
     * Exception decompiling
     */
    public ElemTemplate getTemplateFast(XPathContext var1_1, int var2_2, int var3_3, QName var4_4, int var5_6, boolean var6_7, DTM var7_8) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [11[DOLOOP]], but top level block is 0[TRYBLOCK]
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

    public TemplateWalker getWalker() {
        return new TemplateWalker();
    }

    public void setTemplate(ElemTemplate elemTemplate) {
        int n;
        int n2;
        StepPattern[] arrstepPattern = elemTemplate.getMatch();
        if (elemTemplate.getName() == null && arrstepPattern == null) {
            elemTemplate.error("ER_NEED_NAME_OR_MATCH_ATTRIB", new Object[]{"xsl:template"});
        }
        if (elemTemplate.getName() != null) {
            ElemTemplate elemTemplate2 = (ElemTemplate)this.m_namedTemplates.get(elemTemplate.getName());
            if (elemTemplate2 == null) {
                this.m_namedTemplates.put(elemTemplate.getName(), elemTemplate);
            } else {
                n2 = elemTemplate2.getStylesheetComposed().getImportCountComposed();
                n = elemTemplate.getStylesheetComposed().getImportCountComposed();
                if (n > n2) {
                    this.m_namedTemplates.put(elemTemplate.getName(), elemTemplate);
                } else if (n == n2) {
                    elemTemplate.error("ER_DUPLICATE_NAMED_TEMPLATE", new Object[]{elemTemplate.getName()});
                }
            }
        }
        if (arrstepPattern != null) {
            if ((arrstepPattern = arrstepPattern.getExpression()) instanceof StepPattern) {
                this.insertPatternInTable((StepPattern)arrstepPattern, elemTemplate);
            } else if (arrstepPattern instanceof UnionPattern) {
                arrstepPattern = ((UnionPattern)arrstepPattern).getPatterns();
                n2 = arrstepPattern.length;
                for (n = 0; n < n2; ++n) {
                    this.insertPatternInTable(arrstepPattern[n], elemTemplate);
                }
            }
        }
    }

    public class TemplateWalker {
        private TemplateSubPatternAssociation curPattern;
        private Enumeration hashIterator;
        private boolean inPatterns;
        private Hashtable m_compilerCache = new Hashtable();

        private TemplateWalker() {
            this.hashIterator = TemplateList.this.m_patternTable.elements();
            this.inPatterns = true;
            this.curPattern = null;
        }

        public ElemTemplate next() {
            TemplateSubPatternAssociation templateSubPatternAssociation = null;
            do {
                Serializable serializable = templateSubPatternAssociation;
                if (this.inPatterns) {
                    serializable = this.curPattern;
                    if (serializable != null) {
                        this.curPattern = ((TemplateSubPatternAssociation)serializable).getNext();
                    }
                    if ((serializable = this.curPattern) != null) {
                        serializable = ((TemplateSubPatternAssociation)serializable).getTemplate();
                    } else if (this.hashIterator.hasMoreElements()) {
                        this.curPattern = (TemplateSubPatternAssociation)this.hashIterator.nextElement();
                        serializable = this.curPattern.getTemplate();
                    } else {
                        this.inPatterns = false;
                        this.hashIterator = TemplateList.this.m_namedTemplates.elements();
                        serializable = templateSubPatternAssociation;
                    }
                }
                if (!this.inPatterns) {
                    if (this.hashIterator.hasMoreElements()) {
                        serializable = (ElemTemplate)this.hashIterator.nextElement();
                    } else {
                        return null;
                    }
                }
                if ((ElemTemplate)this.m_compilerCache.get(new Integer(((ElemTemplateElement)serializable).getUid())) == null) {
                    this.m_compilerCache.put(new Integer(((ElemTemplateElement)serializable).getUid()), serializable);
                    return serializable;
                }
                templateSubPatternAssociation = serializable;
            } while (true);
        }
    }

}

