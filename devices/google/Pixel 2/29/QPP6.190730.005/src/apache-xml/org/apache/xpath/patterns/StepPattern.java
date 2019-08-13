/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.patterns;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.Axis;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;

public class StepPattern
extends NodeTest
implements SubContextList,
ExpressionOwner {
    private static final boolean DEBUG_MATCHES = false;
    static final long serialVersionUID = 9071668960168152644L;
    protected int m_axis;
    Expression[] m_predicates;
    StepPattern m_relativePathPattern;
    String m_targetString;

    public StepPattern(int n, int n2, int n3) {
        super(n);
        this.m_axis = n2;
    }

    public StepPattern(int n, String string, String string2, int n2, int n3) {
        super(n, string, string2);
        this.m_axis = n2;
    }

    /*
     * Exception decompiling
     */
    private final boolean checkProximityPosition(XPathContext var1_1, int var2_3, DTM var3_4, int var4_8, int var5_9) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 23[UNCONDITIONALDOLOOP]
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
     * Exception decompiling
     */
    private final int getProximityPosition(XPathContext var1_1, int var2_3, boolean var3_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 4 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    @Override
    public void calcScore() {
        if (this.getPredicateCount() <= 0 && this.m_relativePathPattern == null) {
            super.calcScore();
        } else {
            this.m_score = SCORE_OTHER;
        }
        if (this.m_targetString == null) {
            this.calcTargetString();
        }
    }

    public void calcTargetString() {
        int n = this.getWhatToShow();
        this.m_targetString = n != -1 ? (n != 1 ? (n != 4 && n != 8 && n != 12 ? (n != 128 ? (n != 256 && n != 1280 ? "*" : "/") : "#comment") : "#text") : ("*" == this.m_name ? "*" : this.m_name)) : "*";
    }

    protected void callSubtreeVisitors(XPathVisitor xPathVisitor) {
        Object object = this.m_predicates;
        if (object != null) {
            int n = ((Expression[])object).length;
            for (int i = 0; i < n; ++i) {
                object = new PredOwner(i);
                if (!xPathVisitor.visitPredicate((ExpressionOwner)object, this.m_predicates[i])) continue;
                this.m_predicates[i].callVisitors((ExpressionOwner)object, xPathVisitor);
            }
        }
        if ((object = this.m_relativePathPattern) != null) {
            ((StepPattern)object).callVisitors(this, xPathVisitor);
        }
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitMatchPattern(expressionOwner, this)) {
            this.callSubtreeVisitors(xPathVisitor);
        }
    }

    @Override
    public boolean canTraverseOutsideSubtree() {
        int n = this.getPredicateCount();
        for (int i = 0; i < n; ++i) {
            if (!this.getPredicate(i).canTraverseOutsideSubtree()) continue;
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        expression = (StepPattern)expression;
        Object object = this.m_predicates;
        if (object != null) {
            int n = ((Expression[])object).length;
            object = ((StepPattern)expression).m_predicates;
            if (object == null || ((Expression[])object).length != n) return false;
            for (int i = 0; i < n; ++i) {
                if (this.m_predicates[i].deepEquals(((StepPattern)expression).m_predicates[i])) continue;
                return false;
            }
            object = this.m_relativePathPattern;
            return !(object != null ? !((StepPattern)object).deepEquals(((StepPattern)expression).m_relativePathPattern) : ((StepPattern)expression).m_relativePathPattern != null);
        } else {
            object = this.m_relativePathPattern;
            if (((StepPattern)expression).m_predicates == null) return !(object != null ? !((StepPattern)object).deepEquals(((StepPattern)expression).m_relativePathPattern) : ((StepPattern)expression).m_relativePathPattern != null);
            return false;
        }
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext, xPathContext.getCurrentNode());
    }

    @Override
    public XObject execute(XPathContext xPathContext, int n) throws TransformerException {
        DTM dTM = xPathContext.getDTM(n);
        if (dTM != null) {
            return this.execute(xPathContext, n, dTM, dTM.getExpandedTypeID(n));
        }
        return NodeTest.SCORE_NONE;
    }

    @Override
    public XObject execute(XPathContext xPathContext, int n, DTM object, int n2) throws TransformerException {
        if (this.m_whatToShow == 65536) {
            object = this.m_relativePathPattern;
            if (object != null) {
                return ((StepPattern)object).execute(xPathContext);
            }
            return NodeTest.SCORE_NONE;
        }
        XObject xObject = super.execute(xPathContext, n, (DTM)object, n2);
        if (xObject == NodeTest.SCORE_NONE) {
            return NodeTest.SCORE_NONE;
        }
        if (this.getPredicateCount() != 0 && !this.executePredicates(xPathContext, (DTM)object, n)) {
            return NodeTest.SCORE_NONE;
        }
        StepPattern stepPattern = this.m_relativePathPattern;
        if (stepPattern != null) {
            return stepPattern.executeRelativePathPattern(xPathContext, (DTM)object, n);
        }
        return xObject;
    }

    /*
     * Exception decompiling
     */
    protected final boolean executePredicates(XPathContext var1_1, DTM var2_2, int var3_6) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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

    protected final XObject executeRelativePathPattern(XPathContext xPathContext, DTM object, int n) throws TransformerException {
        XNumber xNumber = NodeTest.SCORE_NONE;
        DTMAxisTraverser dTMAxisTraverser = object.getAxisTraverser(this.m_axis);
        int n2 = dTMAxisTraverser.first(n);
        object = xNumber;
        while (-1 != n2) {
            try {
                xPathContext.pushCurrentNode(n2);
                object = this.execute(xPathContext);
                xNumber = NodeTest.SCORE_NONE;
                if (object != xNumber) {
                    xPathContext.popCurrentNode();
                    break;
                }
                xPathContext.popCurrentNode();
                n2 = dTMAxisTraverser.next(n, n2);
            }
            catch (Throwable throwable) {
                xPathContext.popCurrentNode();
                throw throwable;
            }
        }
        return object;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        Expression[] arrexpression;
        super.fixupVariables(vector, n);
        if (this.m_predicates != null) {
            for (int i = 0; i < (arrexpression = this.m_predicates).length; ++i) {
                arrexpression[i].fixupVariables(vector, n);
            }
        }
        if ((arrexpression = this.m_relativePathPattern) != null) {
            arrexpression.fixupVariables(vector, n);
        }
    }

    public int getAxis() {
        return this.m_axis;
    }

    @Override
    public Expression getExpression() {
        return this.m_relativePathPattern;
    }

    @Override
    public int getLastPos(XPathContext xPathContext) {
        return this.getProximityPosition(xPathContext, xPathContext.getPredicatePos(), true);
    }

    public double getMatchScore(XPathContext xPathContext, int n) throws TransformerException {
        xPathContext.pushCurrentNode(n);
        xPathContext.pushCurrentExpressionNode(n);
        try {
            double d = this.execute(xPathContext).num();
            return d;
        }
        finally {
            xPathContext.popCurrentNode();
            xPathContext.popCurrentExpressionNode();
        }
    }

    public Expression getPredicate(int n) {
        return this.m_predicates[n];
    }

    public final int getPredicateCount() {
        Expression[] arrexpression = this.m_predicates;
        int n = arrexpression == null ? 0 : arrexpression.length;
        return n;
    }

    public Expression[] getPredicates() {
        return this.m_predicates;
    }

    @Override
    public int getProximityPosition(XPathContext xPathContext) {
        return this.getProximityPosition(xPathContext, xPathContext.getPredicatePos(), false);
    }

    public StepPattern getRelativePathPattern() {
        return this.m_relativePathPattern;
    }

    public String getTargetString() {
        return this.m_targetString;
    }

    public void setAxis(int n) {
        this.m_axis = n;
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_relativePathPattern = (StepPattern)expression;
    }

    public void setPredicates(Expression[] arrexpression) {
        this.m_predicates = arrexpression;
        if (arrexpression != null) {
            for (int i = 0; i < arrexpression.length; ++i) {
                arrexpression[i].exprSetParent(this);
            }
        }
        this.calcScore();
    }

    public void setRelativePathPattern(StepPattern stepPattern) {
        this.m_relativePathPattern = stepPattern;
        stepPattern.exprSetParent(this);
        this.calcScore();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        StepPattern stepPattern = this;
        while (stepPattern != null) {
            if (stepPattern != this) {
                stringBuffer.append("/");
            }
            stringBuffer.append(Axis.getNames(stepPattern.m_axis));
            stringBuffer.append("::");
            if (20480 == stepPattern.m_whatToShow) {
                stringBuffer.append("doc()");
            } else if (65536 == stepPattern.m_whatToShow) {
                stringBuffer.append("function()");
            } else if (-1 == stepPattern.m_whatToShow) {
                stringBuffer.append("node()");
            } else if (4 == stepPattern.m_whatToShow) {
                stringBuffer.append("text()");
            } else if (64 == stepPattern.m_whatToShow) {
                stringBuffer.append("processing-instruction(");
                if (stepPattern.m_name != null) {
                    stringBuffer.append(stepPattern.m_name);
                }
                stringBuffer.append(")");
            } else if (128 == stepPattern.m_whatToShow) {
                stringBuffer.append("comment()");
            } else if (stepPattern.m_name != null) {
                if (2 == stepPattern.m_whatToShow) {
                    stringBuffer.append("@");
                }
                if (stepPattern.m_namespace != null) {
                    stringBuffer.append("{");
                    stringBuffer.append(stepPattern.m_namespace);
                    stringBuffer.append("}");
                }
                stringBuffer.append(stepPattern.m_name);
            } else if (2 == stepPattern.m_whatToShow) {
                stringBuffer.append("@");
            } else if (1280 == stepPattern.m_whatToShow) {
                stringBuffer.append("doc-root()");
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("?");
                stringBuilder.append(Integer.toHexString(stepPattern.m_whatToShow));
                stringBuffer.append(stringBuilder.toString());
            }
            if (stepPattern.m_predicates != null) {
                for (int i = 0; i < stepPattern.m_predicates.length; ++i) {
                    stringBuffer.append("[");
                    stringBuffer.append(stepPattern.m_predicates[i]);
                    stringBuffer.append("]");
                }
            }
            stepPattern = stepPattern.m_relativePathPattern;
        }
        return stringBuffer.toString();
    }

    class PredOwner
    implements ExpressionOwner {
        int m_index;

        PredOwner(int n) {
            this.m_index = n;
        }

        @Override
        public Expression getExpression() {
            return StepPattern.this.m_predicates[this.m_index];
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(StepPattern.this);
            StepPattern.this.m_predicates[this.m_index] = expression;
        }
    }

}

