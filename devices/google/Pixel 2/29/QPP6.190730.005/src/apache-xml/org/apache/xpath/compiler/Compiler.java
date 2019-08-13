/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.compiler;

import java.io.PrintStream;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.ObjectVector;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xpath.Expression;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.UnionPathIterator;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.FunctionTable;
import org.apache.xpath.compiler.OpMap;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.functions.FuncExtFunctionAvailable;
import org.apache.xpath.functions.Function;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XString;
import org.apache.xpath.operations.And;
import org.apache.xpath.operations.Bool;
import org.apache.xpath.operations.Div;
import org.apache.xpath.operations.Equals;
import org.apache.xpath.operations.Gt;
import org.apache.xpath.operations.Gte;
import org.apache.xpath.operations.Lt;
import org.apache.xpath.operations.Lte;
import org.apache.xpath.operations.Minus;
import org.apache.xpath.operations.Mod;
import org.apache.xpath.operations.Mult;
import org.apache.xpath.operations.Neg;
import org.apache.xpath.operations.NotEquals;
import org.apache.xpath.operations.Number;
import org.apache.xpath.operations.Operation;
import org.apache.xpath.operations.Or;
import org.apache.xpath.operations.Plus;
import org.apache.xpath.operations.UnaryOperation;
import org.apache.xpath.operations.Variable;
import org.apache.xpath.patterns.FunctionPattern;
import org.apache.xpath.patterns.StepPattern;
import org.apache.xpath.patterns.UnionPattern;

public class Compiler
extends OpMap {
    private static final boolean DEBUG = false;
    private static long s_nextMethodId = 0L;
    private int locPathDepth = -1;
    private PrefixResolver m_currentPrefixResolver = null;
    ErrorListener m_errorHandler;
    private FunctionTable m_functionTable;
    SourceLocator m_locator;

    public Compiler() {
        this.m_errorHandler = null;
        this.m_locator = null;
    }

    public Compiler(ErrorListener errorListener, SourceLocator sourceLocator, FunctionTable functionTable) {
        this.m_errorHandler = errorListener;
        this.m_locator = sourceLocator;
        this.m_functionTable = functionTable;
    }

    private Expression compileExtension(int n) throws TransformerException {
        int n2 = this.getOp(n + 1);
        int n3 = Compiler.getFirstChildPos(n);
        Object object = (String)this.getTokenQueue().elementAt(this.getOp(n3));
        String string = (String)this.getTokenQueue().elementAt(this.getOp(++n3));
        ++n3;
        object = new FuncExtFunction((String)object, string, String.valueOf(this.getNextMethodId()));
        int n4 = 0;
        while (n3 < n2 + n - 1) {
            try {
                int n5 = this.getNextOpPos(n3);
                ((Function)object).setArg(this.compile(n3), n4);
                n3 = n5;
                ++n4;
            }
            catch (WrongNumberArgsException wrongNumberArgsException) {
                break;
            }
        }
        return object;
    }

    private Expression compileOperation(Operation operation, int n) throws TransformerException {
        int n2 = Compiler.getFirstChildPos(n);
        n = this.getNextOpPos(n2);
        operation.setLeftRight(this.compile(n2), this.compile(n));
        return operation;
    }

    private void compilePredicates(int n, Expression[] arrexpression) throws TransformerException {
        int n2 = 0;
        while (29 == this.getOp(n)) {
            arrexpression[n2] = this.predicate(n);
            n = this.getNextOpPos(n);
            ++n2;
        }
    }

    private Expression compileUnary(UnaryOperation unaryOperation, int n) throws TransformerException {
        unaryOperation.setRight(this.compile(Compiler.getFirstChildPos(n)));
        return unaryOperation;
    }

    private long getNextMethodId() {
        synchronized (this) {
            if (s_nextMethodId == Long.MAX_VALUE) {
                s_nextMethodId = 0L;
            }
            long l = s_nextMethodId;
            s_nextMethodId = 1L + l;
            return l;
        }
    }

    protected Expression and(int n) throws TransformerException {
        return this.compileOperation(new And(), n);
    }

    protected Expression arg(int n) throws TransformerException {
        return this.compile(n + 2);
    }

    public void assertion(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{string}));
    }

    protected Expression bool(int n) throws TransformerException {
        return this.compileUnary(new Bool(), n);
    }

    public Expression compile(int n) throws TransformerException {
        int n2 = this.getOp(n);
        Expression expression = null;
        switch (n2) {
            default: {
                this.error("ER_UNKNOWN_OPCODE", new Object[]{Integer.toString(this.getOp(n))});
                break;
            }
            case 31: {
                expression = this.locationPathPattern(n);
                break;
            }
            case 30: {
                expression = this.matchPattern(n + 2);
                break;
            }
            case 29: {
                expression = null;
                break;
            }
            case 28: {
                expression = this.locationPath(n);
                break;
            }
            case 27: {
                expression = this.numberlit(n);
                break;
            }
            case 26: {
                expression = this.arg(n);
                break;
            }
            case 25: {
                expression = this.compileFunction(n);
                break;
            }
            case 24: {
                expression = this.compileExtension(n);
                break;
            }
            case 23: {
                expression = this.group(n);
                break;
            }
            case 22: {
                expression = this.variable(n);
                break;
            }
            case 21: {
                expression = this.literal(n);
                break;
            }
            case 20: {
                expression = this.union(n);
                break;
            }
            case 19: {
                expression = this.number(n);
                break;
            }
            case 18: {
                expression = this.bool(n);
                break;
            }
            case 17: {
                expression = this.string(n);
                break;
            }
            case 16: {
                expression = this.neg(n);
                break;
            }
            case 15: {
                this.error("ER_UNKNOWN_OPCODE", new Object[]{"quo"});
                break;
            }
            case 14: {
                expression = this.mod(n);
                break;
            }
            case 13: {
                expression = this.div(n);
                break;
            }
            case 12: {
                expression = this.mult(n);
                break;
            }
            case 11: {
                expression = this.minus(n);
                break;
            }
            case 10: {
                expression = this.plus(n);
                break;
            }
            case 9: {
                expression = this.gt(n);
                break;
            }
            case 8: {
                expression = this.gte(n);
                break;
            }
            case 7: {
                expression = this.lt(n);
                break;
            }
            case 6: {
                expression = this.lte(n);
                break;
            }
            case 5: {
                expression = this.equals(n);
                break;
            }
            case 4: {
                expression = this.notequals(n);
                break;
            }
            case 3: {
                expression = this.and(n);
                break;
            }
            case 2: {
                expression = this.or(n);
                break;
            }
            case 1: {
                expression = this.compile(n + 2);
            }
        }
        return expression;
    }

    Expression compileFunction(int n) throws TransformerException {
        int n2 = this.getOp(n + 1);
        int n3 = Compiler.getFirstChildPos(n);
        int n4 = this.getOp(n3);
        if (-1 != n4) {
            Function function = this.m_functionTable.getFunction(n4);
            if (function instanceof FuncExtFunctionAvailable) {
                ((FuncExtFunctionAvailable)function).setFunctionTable(this.m_functionTable);
            }
            function.postCompileStep(this);
            int n5 = 0;
            ++n3;
            while (n3 < n2 + n - 1) {
                function.setArg(this.compile(n3), n5);
                n3 = this.getNextOpPos(n3);
                ++n5;
            }
            try {
                function.checkNumberArgs(n5);
            }
            catch (WrongNumberArgsException wrongNumberArgsException) {
                String string = this.m_functionTable.getFunctionName(n4);
                this.m_errorHandler.fatalError(new TransformerException(XSLMessages.createXPATHMessage("ER_ONLY_ALLOWS", new Object[]{string, wrongNumberArgsException.getMessage()}), this.m_locator));
            }
            return function;
        }
        this.error("ER_FUNCTION_TOKEN_NOT_FOUND", null);
        return null;
    }

    public int countPredicates(int n) throws TransformerException {
        int n2 = 0;
        while (29 == this.getOp(n)) {
            ++n2;
            n = this.getNextOpPos(n);
        }
        return n2;
    }

    protected Expression div(int n) throws TransformerException {
        return this.compileOperation(new Div(), n);
    }

    protected Expression equals(int n) throws TransformerException {
        return this.compileOperation(new Equals(), n);
    }

    @Override
    public void error(String string, Object[] object) throws TransformerException {
        string = XSLMessages.createXPATHMessage(string, (Object[])object);
        object = this.m_errorHandler;
        if (object != null) {
            object.fatalError(new TransformerException(string, this.m_locator));
            return;
        }
        throw new TransformerException(string, (SAXSourceLocator)this.m_locator);
    }

    public Expression[] getCompiledPredicates(int n) throws TransformerException {
        int n2 = this.countPredicates(n);
        if (n2 > 0) {
            Expression[] arrexpression = new Expression[n2];
            this.compilePredicates(n, arrexpression);
            return arrexpression;
        }
        return null;
    }

    FunctionTable getFunctionTable() {
        return this.m_functionTable;
    }

    public int getLocationPathDepth() {
        return this.locPathDepth;
    }

    public PrefixResolver getNamespaceContext() {
        return this.m_currentPrefixResolver;
    }

    /*
     * Exception decompiling
     */
    public int getWhatToShow(int var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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

    protected Expression group(int n) throws TransformerException {
        return this.compile(n + 2);
    }

    protected Expression gt(int n) throws TransformerException {
        return this.compileOperation(new Gt(), n);
    }

    protected Expression gte(int n) throws TransformerException {
        return this.compileOperation(new Gte(), n);
    }

    protected Expression literal(int n) {
        n = Compiler.getFirstChildPos(n);
        return (XString)this.getTokenQueue().elementAt(this.getOp(n));
    }

    public Expression locationPath(int n) throws TransformerException {
        ++this.locPathDepth;
        boolean bl = this.locPathDepth == 0;
        try {
            Expression expression = (Expression)((Object)WalkerFactory.newDTMIterator(this, n, bl));
            return expression;
        }
        finally {
            --this.locPathDepth;
        }
    }

    public Expression locationPathPattern(int n) throws TransformerException {
        return this.stepPattern(Compiler.getFirstChildPos(n), 0, null);
    }

    protected Expression lt(int n) throws TransformerException {
        return this.compileOperation(new Lt(), n);
    }

    protected Expression lte(int n) throws TransformerException {
        return this.compileOperation(new Lte(), n);
    }

    protected Expression matchPattern(int n) throws TransformerException {
        ++this.locPathDepth;
        int n2 = n;
        int n3 = 0;
        do {
            if (this.getOp(n2) != 31) break;
            n2 = this.getNextOpPos(n2);
            ++n3;
        } while (true);
        if (n3 == 1) {
            Expression expression = this.compile(n);
            return expression;
        }
        UnionPattern unionPattern = new UnionPattern();
        StepPattern[] arrstepPattern = new StepPattern[n3];
        n3 = 0;
        do {
            if (this.getOp(n) != 31) break;
            n2 = this.getNextOpPos(n);
            arrstepPattern[n3] = (StepPattern)this.compile(n);
            n = n2;
            ++n3;
        } while (true);
        unionPattern.setPatterns(arrstepPattern);
        --this.locPathDepth;
        return unionPattern;
        finally {
            --this.locPathDepth;
        }
    }

    protected Expression minus(int n) throws TransformerException {
        return this.compileOperation(new Minus(), n);
    }

    protected Expression mod(int n) throws TransformerException {
        return this.compileOperation(new Mod(), n);
    }

    protected Expression mult(int n) throws TransformerException {
        return this.compileOperation(new Mult(), n);
    }

    protected Expression neg(int n) throws TransformerException {
        return this.compileUnary(new Neg(), n);
    }

    protected Expression notequals(int n) throws TransformerException {
        return this.compileOperation(new NotEquals(), n);
    }

    protected Expression number(int n) throws TransformerException {
        return this.compileUnary(new Number(), n);
    }

    protected Expression numberlit(int n) {
        n = Compiler.getFirstChildPos(n);
        return (XNumber)this.getTokenQueue().elementAt(this.getOp(n));
    }

    protected Expression or(int n) throws TransformerException {
        return this.compileOperation(new Or(), n);
    }

    protected Expression plus(int n) throws TransformerException {
        return this.compileOperation(new Plus(), n);
    }

    public Expression predicate(int n) throws TransformerException {
        return this.compile(n + 2);
    }

    public void setNamespaceContext(PrefixResolver prefixResolver) {
        this.m_currentPrefixResolver = prefixResolver;
    }

    protected StepPattern stepPattern(int n, int n2, StepPattern stepPattern) throws TransformerException {
        StepPattern stepPattern2;
        int n3 = this.getOp(n);
        if (-1 == n3) {
            return null;
        }
        int n4 = this.getNextOpPos(n);
        if (n3 != 25) {
            switch (n3) {
                default: {
                    this.error("ER_UNKNOWN_MATCH_OPERATION", null);
                    return null;
                }
                case 53: {
                    int n5 = this.getArgLengthOfStep(n);
                    n3 = Compiler.getFirstChildPosOfStep(n);
                    stepPattern2 = new StepPattern(this.getWhatToShow(n), this.getStepNS(n), this.getStepLocalName(n), 10, 3);
                    n = n5;
                    break;
                }
                case 52: {
                    int n6 = this.getArgLengthOfStep(n);
                    n3 = Compiler.getFirstChildPosOfStep(n);
                    if (1280 == this.getWhatToShow(n)) {
                        // empty if block
                    }
                    stepPattern2 = new StepPattern(this.getWhatToShow(n), this.getStepNS(n), this.getStepLocalName(n), 0, 3);
                    n = n6;
                    break;
                }
                case 51: {
                    int n7 = this.getArgLengthOfStep(n);
                    n3 = Compiler.getFirstChildPosOfStep(n);
                    stepPattern2 = new StepPattern(2, this.getStepNS(n), this.getStepLocalName(n), 10, 2);
                    n = n7;
                    break;
                }
                case 50: {
                    int n8 = this.getArgLengthOfStep(n);
                    n3 = Compiler.getFirstChildPosOfStep(n);
                    stepPattern2 = new StepPattern(1280, 10, 3);
                    n = n8;
                    break;
                }
            }
        } else {
            int n9 = this.getOp(n + 1);
            stepPattern2 = new FunctionPattern(this.compileFunction(n), 10, 3);
            n3 = n;
            n = n9;
        }
        stepPattern2.setPredicates(this.getCompiledPredicates(n3 + n));
        if (stepPattern != null) {
            stepPattern2.setRelativePathPattern(stepPattern);
        }
        stepPattern = this.stepPattern(n4, n2 + 1, stepPattern2);
        if (stepPattern == null) {
            stepPattern = stepPattern2;
        }
        return stepPattern;
    }

    protected Expression string(int n) throws TransformerException {
        return this.compileUnary(new org.apache.xpath.operations.String(), n);
    }

    protected Expression union(int n) throws TransformerException {
        ++this.locPathDepth;
        try {
            LocPathIterator locPathIterator = UnionPathIterator.createUnionIterator(this, n);
            return locPathIterator;
        }
        finally {
            --this.locPathDepth;
        }
    }

    protected Expression variable(int n) throws TransformerException {
        Variable variable = new Variable();
        int n2 = this.getOp(n = Compiler.getFirstChildPos(n));
        String string = -2 == n2 ? null : (String)this.getTokenQueue().elementAt(n2);
        variable.setQName(new QName(string, (String)this.getTokenQueue().elementAt(this.getOp(n + 1))));
        return variable;
    }

    public void warn(String string, Object[] object) throws TransformerException {
        string = XSLMessages.createXPATHWarning(string, (Object[])object);
        object = this.m_errorHandler;
        if (object != null) {
            object.warning(new TransformerException(string, this.m_locator));
        } else {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("; file ");
            ((StringBuilder)object).append(this.m_locator.getSystemId());
            ((StringBuilder)object).append("; line ");
            ((StringBuilder)object).append(this.m_locator.getLineNumber());
            ((StringBuilder)object).append("; column ");
            ((StringBuilder)object).append(this.m_locator.getColumnNumber());
            printStream.println(((StringBuilder)object).toString());
        }
    }
}

