/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.FunctionTable;
import org.apache.xpath.compiler.XPathParser;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;

public class XPath
implements Serializable,
ExpressionOwner {
    private static final boolean DEBUG_MATCHES = false;
    public static final int MATCH = 1;
    public static final double MATCH_SCORE_NODETEST = -0.5;
    public static final double MATCH_SCORE_NONE = Double.NEGATIVE_INFINITY;
    public static final double MATCH_SCORE_NSWILD = -0.25;
    public static final double MATCH_SCORE_OTHER = 0.5;
    public static final double MATCH_SCORE_QNAME = 0.0;
    public static final int SELECT = 0;
    static final long serialVersionUID = 3976493477939110553L;
    private transient FunctionTable m_funcTable;
    private Expression m_mainExp;
    String m_patternString;

    public XPath(String string, SourceLocator sourceLocator, PrefixResolver prefixResolver, int n) throws TransformerException {
        this(string, sourceLocator, prefixResolver, n, null);
    }

    public XPath(String object, SourceLocator sourceLocator, PrefixResolver prefixResolver, int n, ErrorListener object2) throws TransformerException {
        block7 : {
            Object object3;
            block6 : {
                block5 : {
                    this.m_funcTable = null;
                    this.initFunctionTable();
                    object3 = object2;
                    if (object2 == null) {
                        object3 = new DefaultErrorHandler();
                    }
                    this.m_patternString = object;
                    object2 = new XPathParser((ErrorListener)object3, sourceLocator);
                    object3 = new Compiler((ErrorListener)object3, sourceLocator, this.m_funcTable);
                    if (n != 0) break block5;
                    ((XPathParser)object2).initXPath((Compiler)object3, (String)object, prefixResolver);
                    break block6;
                }
                if (1 != n) break block7;
                ((XPathParser)object2).initMatchPattern((Compiler)object3, (String)object, prefixResolver);
            }
            object = ((Compiler)object3).compile(0);
            this.setExpression((Expression)object);
            if (sourceLocator != null && sourceLocator instanceof ExpressionNode) {
                ((Expression)object).exprSetParent((ExpressionNode)sourceLocator);
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[]{Integer.toString(n)}));
    }

    public XPath(String object, SourceLocator sourceLocator, PrefixResolver prefixResolver, int n, ErrorListener object2, FunctionTable object3) throws TransformerException {
        block7 : {
            block6 : {
                block5 : {
                    this.m_funcTable = null;
                    this.m_funcTable = object3;
                    object3 = object2;
                    if (object2 == null) {
                        object3 = new DefaultErrorHandler();
                    }
                    this.m_patternString = object;
                    object2 = new XPathParser((ErrorListener)object3, sourceLocator);
                    object3 = new Compiler((ErrorListener)object3, sourceLocator, this.m_funcTable);
                    if (n != 0) break block5;
                    ((XPathParser)object2).initXPath((Compiler)object3, (String)object, prefixResolver);
                    break block6;
                }
                if (1 != n) break block7;
                ((XPathParser)object2).initMatchPattern((Compiler)object3, (String)object, prefixResolver);
            }
            object = ((Compiler)object3).compile(0);
            this.setExpression((Expression)object);
            if (sourceLocator != null && sourceLocator instanceof ExpressionNode) {
                ((Expression)object).exprSetParent((ExpressionNode)sourceLocator);
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_CANNOT_DEAL_XPATH_TYPE", new Object[]{Integer.toString(n)}));
    }

    public XPath(Expression expression) {
        this.m_funcTable = null;
        this.setExpression(expression);
        this.initFunctionTable();
    }

    private void initFunctionTable() {
        this.m_funcTable = new FunctionTable();
    }

    public void assertion(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{string}));
    }

    /*
     * Exception decompiling
     */
    public boolean bool(XPathContext var1_1, int var2_2, PrefixResolver var3_3) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        this.m_mainExp.callVisitors(this, xPathVisitor);
    }

    public void error(XPathContext object, int n, String string, Object[] object2) throws TransformerException {
        string = XSLMessages.createXPATHMessage(string, (Object[])object2);
        object2 = ((XPathContext)object).getErrorListener();
        if (object2 != null) {
            object2.fatalError(new TransformerException(string, (SAXSourceLocator)((XPathContext)object).getSAXLocator()));
        } else {
            object2 = ((XPathContext)object).getSAXLocator();
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append("; file ");
            stringBuilder.append(object2.getSystemId());
            stringBuilder.append("; line ");
            stringBuilder.append(object2.getLineNumber());
            stringBuilder.append("; column ");
            stringBuilder.append(object2.getColumnNumber());
            ((PrintStream)object).println(stringBuilder.toString());
        }
    }

    /*
     * Exception decompiling
     */
    public XObject execute(XPathContext var1_1, int var2_2, PrefixResolver var3_3) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    public XObject execute(XPathContext xPathContext, Node node, PrefixResolver prefixResolver) throws TransformerException {
        return this.execute(xPathContext, xPathContext.getDTMHandleFromNode(node), prefixResolver);
    }

    public void fixupVariables(Vector vector, int n) {
        this.m_mainExp.fixupVariables(vector, n);
    }

    @Override
    public Expression getExpression() {
        return this.m_mainExp;
    }

    public SourceLocator getLocator() {
        return this.m_mainExp;
    }

    public double getMatchScore(XPathContext xPathContext, int n) throws TransformerException {
        xPathContext.pushCurrentNode(n);
        xPathContext.pushCurrentExpressionNode(n);
        try {
            double d = this.m_mainExp.execute(xPathContext).num();
            return d;
        }
        finally {
            xPathContext.popCurrentNode();
            xPathContext.popCurrentExpressionNode();
        }
    }

    public String getPatternString() {
        return this.m_patternString;
    }

    @Override
    public void setExpression(Expression expression) {
        Expression expression2 = this.m_mainExp;
        if (expression2 != null) {
            expression.exprSetParent(expression2.exprGetParent());
        }
        this.m_mainExp = expression;
    }

    public void warn(XPathContext xPathContext, int n, String string, Object[] object) throws TransformerException {
        string = XSLMessages.createXPATHWarning(string, (Object[])object);
        object = xPathContext.getErrorListener();
        if (object != null) {
            object.warning(new TransformerException(string, (SAXSourceLocator)xPathContext.getSAXLocator()));
        }
    }
}

