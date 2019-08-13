/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.Vector;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AbsPathChecker;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.ElemVariablePsuedo;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.VarNameCollector;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.FilterExprIteratorSimple;
import org.apache.xpath.axes.FilterExprWalker;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.SelfIteratorNoPredicate;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.operations.Variable;
import org.apache.xpath.operations.VariableSafeAbsRef;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class RedundentExprEliminator
extends XSLTVisitor {
    public static final boolean DEBUG = false;
    public static final boolean DIAGNOSE_MULTISTEPLIST = false;
    public static final boolean DIAGNOSE_NUM_PATHS_REDUCED = false;
    static final String PSUEDOVARNAMESPACE = "http://xml.apache.org/xalan/psuedovar";
    private static int m_uniquePseudoVarID = 1;
    AbsPathChecker m_absPathChecker = new AbsPathChecker();
    Vector m_absPaths = new Vector();
    boolean m_isSameContext = true;
    Vector m_paths = null;
    VarNameCollector m_varNameCollector = new VarNameCollector();

    private final void assertIsLocPathIterator(Expression expression, ExpressionOwner expressionOwner) throws RuntimeException {
        if (!(expression instanceof LocPathIterator)) {
            CharSequence charSequence;
            if (expression instanceof Variable) {
                charSequence = new StringBuilder();
                charSequence.append("Programmer's assertion: expr1 not an iterator: ");
                charSequence.append(((Variable)expression).getQName());
                charSequence = charSequence.toString();
            } else {
                charSequence = new StringBuilder();
                charSequence.append("Programmer's assertion: expr1 not an iterator: ");
                charSequence.append(expression.getClass().getName());
                charSequence = charSequence.toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(", ");
            stringBuilder.append(expressionOwner.getClass().getName());
            stringBuilder.append(" ");
            stringBuilder.append(expression.exprGetParent());
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    protected static void assertion(boolean bl, String string) {
        if (bl) {
            return;
        }
        throw new RuntimeException(XSLMessages.createMessage("ER_ASSERT_REDUNDENT_EXPR_ELIMINATOR", new Object[]{string}));
    }

    private static int getPseudoVarID() {
        synchronized (RedundentExprEliminator.class) {
            int n = m_uniquePseudoVarID;
            m_uniquePseudoVarID = n + 1;
            return n;
        }
    }

    private static void validateNewAddition(Vector vector, ExpressionOwner expressionOwner, LocPathIterator locPathIterator) throws RuntimeException {
        boolean bl = expressionOwner.getExpression() == locPathIterator;
        RedundentExprEliminator.assertion(bl, "owner.getExpression() != path!!!");
        int n = vector.size();
        for (int i = 0; i < n; ++i) {
            ExpressionOwner expressionOwner2 = (ExpressionOwner)vector.elementAt(i);
            bl = expressionOwner2 != expressionOwner;
            RedundentExprEliminator.assertion(bl, "duplicate owner on the list!!!");
            bl = expressionOwner2.getExpression() != locPathIterator;
            RedundentExprEliminator.assertion(bl, "duplicate expression on the list!!!");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected ElemVariable addVarDeclToElem(ElemTemplateElement var1_1, LocPathIterator var2_2, ElemVariable var3_3) throws DOMException {
        block5 : {
            var4_4 = var1_1.getFirstChildElem();
            var2_2.callVisitors(null, this.m_varNameCollector);
            var5_5 = var4_4;
            var6_6 = var1_1;
            if (this.m_varNameCollector.getVarCount() > 0) {
                var7_7 = this.getPrevVariableElem(this.getElemFromExpression(var2_2));
                do {
                    var5_5 = var4_4;
                    var6_6 = var1_1;
                    if (var7_7 == null) break;
                    if (this.m_varNameCollector.doesOccur(var7_7.getName())) {
                        var6_6 = var7_7.getParentElem();
                        var5_5 = var7_7.getNextSiblingElem();
                        break;
                    }
                    var7_7 = this.getPrevVariableElem(var7_7);
                } while (true);
            }
            var1_1 = var5_5;
            if (var5_5 == null) break block5;
            var1_1 = var5_5;
            if (41 != var5_5.getXSLToken()) break block5;
            if (this.isParam(var2_2)) {
                return null;
            }
            do lbl-1000: // 3 sources:
            {
                var1_1 = var5_5;
                if (var5_5 == null) break;
                var5_5 = var1_1 = var5_5.getNextSiblingElem();
                if (var1_1 == null) ** GOTO lbl-1000
                var5_5 = var1_1;
            } while (41 == var1_1.getXSLToken());
        }
        var6_6.insertBefore(var3_3, var1_1);
        this.m_varNameCollector.reset();
        return var3_3;
    }

    protected LocPathIterator changePartToRef(QName serializable, WalkingIterator walkingIterator, int n, boolean bl) {
        Variable variable = new Variable();
        variable.setQName((QName)serializable);
        variable.setIsGlobal(bl);
        if (bl) {
            variable.setIndex(this.getElemFromExpression(walkingIterator).getStylesheetRoot().getVariablesAndParamsComposed().size() - 1);
        }
        serializable = walkingIterator.getFirstWalker();
        for (int i = 0; i < n; ++i) {
            bl = serializable != null;
            RedundentExprEliminator.assertion(bl, "Walker should not be null!");
            serializable = ((AxesWalker)serializable).getNextWalker();
        }
        if (serializable != null) {
            FilterExprWalker filterExprWalker = new FilterExprWalker(walkingIterator);
            filterExprWalker.setInnerExpression(variable);
            filterExprWalker.exprSetParent(walkingIterator);
            filterExprWalker.setNextWalker((AxesWalker)serializable);
            ((AxesWalker)serializable).setPrevWalker(filterExprWalker);
            walkingIterator.setFirstWalker(filterExprWalker);
            return walkingIterator;
        }
        serializable = new FilterExprIteratorSimple(variable);
        ((Expression)serializable).exprSetParent(walkingIterator.exprGetParent());
        return serializable;
    }

    protected void changeToVarRef(QName qName, ExpressionOwner expressionOwner, Vector vector, ElemTemplateElement elemTemplateElement) {
        Variable variable = vector == this.m_absPaths ? new VariableSafeAbsRef() : new Variable();
        variable.setQName(qName);
        if (vector == this.m_absPaths) {
            variable.setIndex(((StylesheetRoot)elemTemplateElement).getVariablesAndParamsComposed().size() - 1);
            variable.setIsGlobal(true);
        }
        expressionOwner.setExpression(variable);
    }

    protected int countAncestors(ElemTemplateElement elemTemplateElement) {
        int n = 0;
        while (elemTemplateElement != null) {
            ++n;
            elemTemplateElement = elemTemplateElement.getParentElem();
        }
        return n;
    }

    protected int countSteps(LocPathIterator predicatedNodeTest) {
        if (predicatedNodeTest instanceof WalkingIterator) {
            int n = 0;
            for (predicatedNodeTest = ((WalkingIterator)predicatedNodeTest).getFirstWalker(); predicatedNodeTest != null; predicatedNodeTest = predicatedNodeTest.getNextWalker()) {
                ++n;
            }
            return n;
        }
        return 1;
    }

    protected ElemVariable createGlobalPseudoVarDecl(QName serializable, StylesheetRoot stylesheetRoot, LocPathIterator locPathIterator) throws DOMException {
        ElemVariable elemVariable = new ElemVariable();
        elemVariable.setIsTopLevel(true);
        elemVariable.setSelect(new XPath(locPathIterator));
        elemVariable.setName((QName)serializable);
        serializable = stylesheetRoot.getVariablesAndParamsComposed();
        elemVariable.setIndex(((Vector)serializable).size());
        ((Vector)serializable).addElement(elemVariable);
        return elemVariable;
    }

    protected WalkingIterator createIteratorFromSteps(WalkingIterator predicatedNodeTest, int n) {
        WalkingIterator walkingIterator = new WalkingIterator(((LocPathIterator)predicatedNodeTest).getPrefixResolver());
        predicatedNodeTest = (AxesWalker)((WalkingIterator)predicatedNodeTest).getFirstWalker().clone();
        walkingIterator.setFirstWalker((AxesWalker)predicatedNodeTest);
        predicatedNodeTest.setLocPathIterator(walkingIterator);
        for (int i = 1; i < n; ++i) {
            AxesWalker axesWalker = (AxesWalker)((AxesWalker)predicatedNodeTest).getNextWalker().clone();
            ((AxesWalker)predicatedNodeTest).setNextWalker(axesWalker);
            axesWalker.setLocPathIterator(walkingIterator);
            predicatedNodeTest = axesWalker;
            continue;
        }
        try {
            ((AxesWalker)predicatedNodeTest).setNextWalker(null);
            return walkingIterator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new WrappedRuntimeException(cloneNotSupportedException);
        }
    }

    protected ElemVariable createLocalPseudoVarDecl(QName serializable, ElemTemplateElement elemTemplateElement, LocPathIterator locPathIterator) throws DOMException {
        ElemVariablePsuedo elemVariablePsuedo = new ElemVariablePsuedo();
        ((ElemVariable)elemVariablePsuedo).setSelect(new XPath(locPathIterator));
        elemVariablePsuedo.setName((QName)serializable);
        serializable = this.addVarDeclToElem(elemTemplateElement, locPathIterator, elemVariablePsuedo);
        locPathIterator.exprSetParent((ExpressionNode)((Object)serializable));
        return serializable;
    }

    protected MultistepExprHolder createMultistepExprList(Vector vector) {
        MultistepExprHolder multistepExprHolder = null;
        int n = vector.size();
        for (int i = 0; i < n; ++i) {
            MultistepExprHolder multistepExprHolder2;
            ExpressionOwner expressionOwner = (ExpressionOwner)vector.elementAt(i);
            if (expressionOwner == null) {
                multistepExprHolder2 = multistepExprHolder;
            } else {
                int n2 = this.countSteps((LocPathIterator)expressionOwner.getExpression());
                multistepExprHolder2 = multistepExprHolder;
                if (n2 > 1) {
                    multistepExprHolder2 = multistepExprHolder == null ? new MultistepExprHolder(expressionOwner, n2, null) : multistepExprHolder.addInSortedOrder(expressionOwner, n2);
                }
            }
            multistepExprHolder = multistepExprHolder2;
        }
        if (multistepExprHolder != null && multistepExprHolder.getLength() > 1) {
            return multistepExprHolder;
        }
        return null;
    }

    protected ElemVariable createPseudoVarDecl(ElemTemplateElement elemTemplateElement, LocPathIterator locPathIterator, boolean bl) throws DOMException {
        Serializable serializable = new StringBuilder();
        serializable.append("#");
        serializable.append(RedundentExprEliminator.getPseudoVarID());
        serializable = new QName(PSUEDOVARNAMESPACE, serializable.toString());
        if (bl) {
            return this.createGlobalPseudoVarDecl((QName)serializable, (StylesheetRoot)elemTemplateElement, locPathIterator);
        }
        return this.createLocalPseudoVarDecl((QName)serializable, elemTemplateElement, locPathIterator);
    }

    protected void diagnoseLineNumber(Expression expressionNode) {
        expressionNode = this.getElemFromExpression((Expression)expressionNode);
        PrintStream printStream = System.err;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   ");
        stringBuilder.append(((ElemTemplateElement)expressionNode).getSystemId());
        stringBuilder.append(" Line ");
        stringBuilder.append(((ElemTemplateElement)expressionNode).getLineNumber());
        printStream.println(stringBuilder.toString());
    }

    protected void diagnoseMultistepList(int n, int n2, boolean bl) {
        if (n > 0) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Found multistep matches: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            stringBuilder.append(" length");
            printStream.print(stringBuilder.toString());
            if (bl) {
                System.err.println(" (global)");
            } else {
                System.err.println();
            }
        }
    }

    protected void diagnoseNumPaths(Vector object, int n, int n2) {
        if (n > 0) {
            if (object == this.m_paths) {
                object = System.err;
                Appendable appendable = new StringBuilder();
                ((StringBuilder)appendable).append("Eliminated ");
                ((StringBuilder)appendable).append(n);
                ((StringBuilder)appendable).append(" total paths!");
                ((PrintStream)object).println(((StringBuilder)appendable).toString());
                appendable = System.err;
                object = new StringBuilder();
                ((StringBuilder)object).append("Consolodated ");
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(" redundent paths!");
                ((PrintStream)appendable).println(((StringBuilder)object).toString());
            } else {
                Appendable appendable = System.err;
                object = new StringBuilder();
                ((StringBuilder)object).append("Eliminated ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" total global paths!");
                ((PrintStream)appendable).println(((StringBuilder)object).toString());
                object = System.err;
                appendable = new StringBuilder();
                ((StringBuilder)appendable).append("Consolodated ");
                ((StringBuilder)appendable).append(n2);
                ((StringBuilder)appendable).append(" redundent global paths!");
                ((PrintStream)object).println(((StringBuilder)appendable).toString());
            }
        }
    }

    protected void eleminateRedundent(ElemTemplateElement elemTemplateElement, Vector vector) {
        int n = vector.size();
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            ExpressionOwner expressionOwner = (ExpressionOwner)vector.elementAt(i);
            int n4 = n2;
            int n5 = n3;
            if (expressionOwner != null) {
                n4 = this.findAndEliminateRedundant(i + 1, i, expressionOwner, elemTemplateElement, vector);
                n5 = n3;
                if (n4 > 0) {
                    n5 = n3 + 1;
                }
                n4 = n2 + n4;
            }
            n2 = n4;
            n3 = n5;
        }
        this.eleminateSharedPartialPaths(elemTemplateElement, vector);
    }

    public void eleminateRedundentGlobals(StylesheetRoot stylesheetRoot) {
        this.eleminateRedundent(stylesheetRoot, this.m_absPaths);
    }

    public void eleminateRedundentLocals(ElemTemplateElement elemTemplateElement) {
        this.eleminateRedundent(elemTemplateElement, this.m_paths);
    }

    protected void eleminateSharedPartialPaths(ElemTemplateElement elemTemplateElement, Vector cloneable) {
        Cloneable cloneable2 = this.createMultistepExprList((Vector)cloneable);
        if (cloneable2 != null) {
            boolean bl = cloneable == this.m_absPaths;
            int n = cloneable2.m_stepCount;
            cloneable = cloneable2;
            --n;
            while (n >= 1) {
                cloneable2 = cloneable;
                Cloneable cloneable3 = cloneable;
                cloneable = cloneable2;
                while (cloneable3 != null && ((MultistepExprHolder)cloneable3).m_stepCount >= n) {
                    cloneable = this.matchAndEliminatePartialPaths((MultistepExprHolder)cloneable3, (MultistepExprHolder)cloneable, bl, n, elemTemplateElement);
                    cloneable3 = ((MultistepExprHolder)cloneable3).m_next;
                }
                --n;
            }
        }
    }

    protected int findAndEliminateRedundant(int n, int n2, ExpressionOwner object, ElemTemplateElement elemTemplateElement, Vector vector) throws DOMException {
        int n3;
        block6 : {
            Object object2 = null;
            Object object3 = null;
            int n4 = 0;
            int n5 = vector.size();
            Object object4 = object.getExpression();
            boolean bl = vector == this.m_absPaths;
            LocPathIterator locPathIterator = (LocPathIterator)object4;
            int n6 = this.countSteps(locPathIterator);
            n3 = n;
            n = n4;
            while (n3 < n5) {
                ExpressionOwner expressionOwner = (ExpressionOwner)vector.elementAt(n3);
                if (expressionOwner != null && ((Expression)(object4 = expressionOwner.getExpression())).deepEquals(locPathIterator)) {
                    object4 = (LocPathIterator)object4;
                    object4 = object2;
                    n4 = n;
                    if (object2 == null) {
                        object3 = object4 = new MultistepExprHolder((ExpressionOwner)object, n6, null);
                        n4 = n + 1;
                    }
                    ((MultistepExprHolder)object3).m_next = new MultistepExprHolder(expressionOwner, n6, null);
                    object3 = ((MultistepExprHolder)object3).m_next;
                    vector.setElementAt(null, n3);
                    n = n4 + 1;
                    object2 = object4;
                }
                ++n3;
            }
            object3 = object2;
            n3 = n;
            if (n == 0) {
                object3 = object2;
                n3 = n;
                if (bl) {
                    object3 = new MultistepExprHolder((ExpressionOwner)object, n6, null);
                    n3 = n + 1;
                }
            }
            if (object3 == null) break block6;
            object = bl ? elemTemplateElement : this.findCommonAncestor((MultistepExprHolder)object3);
            elemTemplateElement = this.createPseudoVarDecl((ElemTemplateElement)object, (LocPathIterator)((MultistepExprHolder)object3).m_exprOwner.getExpression(), bl);
            object2 = ((ElemVariable)elemTemplateElement).getName();
            while (object3 != null) {
                this.changeToVarRef((QName)object2, ((MultistepExprHolder)object3).m_exprOwner, vector, (ElemTemplateElement)object);
                object3 = ((MultistepExprHolder)object3).m_next;
            }
            vector.setElementAt(((ElemVariable)elemTemplateElement).getSelect(), n2);
        }
        return n3;
    }

    protected ElemTemplateElement findCommonAncestor(MultistepExprHolder multistepExprHolder) {
        int n;
        int n2;
        int n3;
        int n4 = multistepExprHolder.getLength();
        ElemTemplateElement[] arrelemTemplateElement = new ElemTemplateElement[n4];
        int[] arrn = new int[n4];
        Object object = multistepExprHolder;
        int n5 = 10000;
        for (n = 0; n < n4; ++n) {
            ElemTemplateElement elemTemplateElement;
            arrelemTemplateElement[n] = elemTemplateElement = this.getElemFromExpression(((MultistepExprHolder)object).m_exprOwner.getExpression());
            arrn[n] = n2 = this.countAncestors(elemTemplateElement);
            n3 = n5;
            if (n2 < n5) {
                n3 = n2;
            }
            object = ((MultistepExprHolder)object).m_next;
            n5 = n3;
        }
        for (n = 0; n < n4; ++n) {
            if (arrn[n] <= n5) continue;
            n2 = arrn[n];
            for (n3 = 0; n3 < n2 - n5; ++n3) {
                arrelemTemplateElement[n] = arrelemTemplateElement[n].getParentElem();
            }
        }
        while (n5 >= 0) {
            n2 = 1;
            object = arrelemTemplateElement[0];
            n3 = 1;
            do {
                n = n2;
                if (n3 >= n4) break;
                if (object != arrelemTemplateElement[n3]) {
                    n = 0;
                    break;
                }
                ++n3;
            } while (true);
            if (n != 0 && this.isNotSameAsOwner(multistepExprHolder, (ElemTemplateElement)object) && ((ElemTemplateElement)object).canAcceptVariables()) {
                return object;
            }
            for (n = 0; n < n4; ++n) {
                arrelemTemplateElement[n] = arrelemTemplateElement[n].getParentElem();
            }
            --n5;
        }
        RedundentExprEliminator.assertion(false, "Could not find common ancestor!!!");
        return null;
    }

    protected ElemTemplateElement getElemFromExpression(Expression expressionNode) {
        for (expressionNode = expressionNode.exprGetParent(); expressionNode != null; expressionNode = expressionNode.exprGetParent()) {
            if (!(expressionNode instanceof ElemTemplateElement)) continue;
            return (ElemTemplateElement)expressionNode;
        }
        throw new RuntimeException(XSLMessages.createMessage("ER_ASSERT_NO_TEMPLATE_PARENT", null));
    }

    protected ElemTemplateElement getPrevElementWithinContext(ElemTemplateElement elemTemplateElement) {
        block4 : {
            block5 : {
                ElemTemplateElement elemTemplateElement2;
                ElemTemplateElement elemTemplateElement3 = elemTemplateElement2 = elemTemplateElement.getPreviousSiblingElem();
                if (elemTemplateElement2 == null) {
                    elemTemplateElement3 = elemTemplateElement.getParentElem();
                }
                elemTemplateElement = elemTemplateElement3;
                if (elemTemplateElement3 == null) break block4;
                int n = elemTemplateElement3.getXSLToken();
                if (28 == n || 19 == n) break block5;
                elemTemplateElement = elemTemplateElement3;
                if (25 != n) break block4;
            }
            elemTemplateElement = null;
        }
        return elemTemplateElement;
    }

    protected ElemVariable getPrevVariableElem(ElemTemplateElement elemTemplateElement) {
        block1 : {
            int n;
            do {
                ElemTemplateElement elemTemplateElement2;
                elemTemplateElement = elemTemplateElement2 = this.getPrevElementWithinContext(elemTemplateElement);
                if (elemTemplateElement2 == null) break block1;
            } while (73 != (n = elemTemplateElement.getXSLToken()) && 41 != n);
            return (ElemVariable)elemTemplateElement;
        }
        return null;
    }

    public boolean isAbsolute(LocPathIterator locPathIterator) {
        int n = locPathIterator.getAnalysisBits();
        boolean bl = WalkerFactory.isSet(n, 134217728) || WalkerFactory.isSet(n, 536870912);
        boolean bl2 = bl;
        if (bl) {
            bl2 = this.m_absPathChecker.checkAbsolute(locPathIterator);
        }
        return bl2;
    }

    protected boolean isNotSameAsOwner(MultistepExprHolder multistepExprHolder, ElemTemplateElement elemTemplateElement) {
        while (multistepExprHolder != null) {
            if (this.getElemFromExpression(multistepExprHolder.m_exprOwner.getExpression()) == elemTemplateElement) {
                return false;
            }
            multistepExprHolder = multistepExprHolder.m_next;
        }
        return true;
    }

    protected boolean isParam(ExpressionNode expressionNode) {
        while (expressionNode != null && !(expressionNode instanceof ElemTemplateElement)) {
            expressionNode = expressionNode.exprGetParent();
        }
        if (expressionNode != null) {
            for (expressionNode = (ElemTemplateElement)expressionNode; expressionNode != null; expressionNode = expressionNode.getParentElem()) {
                int n = ((ElemTemplateElement)expressionNode).getXSLToken();
                if (n != 19 && n != 25) {
                    if (n != 41) {
                        continue;
                    }
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    protected MultistepExprHolder matchAndEliminatePartialPaths(MultistepExprHolder object, MultistepExprHolder multistepExprHolder, boolean bl, int n, ElemTemplateElement expressionNode) {
        Object object2;
        if (((MultistepExprHolder)object).m_exprOwner == null) {
            return multistepExprHolder;
        }
        WalkingIterator walkingIterator = (WalkingIterator)((MultistepExprHolder)object).m_exprOwner.getExpression();
        if (this.partialIsVariable((MultistepExprHolder)object, n)) {
            return multistepExprHolder;
        }
        MultistepExprHolder multistepExprHolder2 = multistepExprHolder;
        ExpressionOwner expressionOwner = null;
        Object object3 = null;
        while (multistepExprHolder2 != null) {
            object2 = object3;
            Object object4 = expressionOwner;
            if (multistepExprHolder2 != object) {
                object2 = object3;
                object4 = expressionOwner;
                if (multistepExprHolder2.m_exprOwner != null) {
                    object2 = object3;
                    object4 = expressionOwner;
                    if (this.stepsEqual(walkingIterator, (WalkingIterator)multistepExprHolder2.m_exprOwner.getExpression(), n)) {
                        object2 = object3;
                        if (object3 == null) {
                            object3 = object2 = (MultistepExprHolder)((MultistepExprHolder)object).clone();
                            try {
                                ((MultistepExprHolder)object).m_exprOwner = null;
                                object3 = object2;
                            }
                            catch (CloneNotSupportedException cloneNotSupportedException) {
                                // empty catch block
                            }
                            expressionOwner = object3;
                            ((MultistepExprHolder)expressionOwner).m_next = null;
                            object2 = object3;
                        }
                        try {
                            ((MultistepExprHolder)expressionOwner).m_next = (MultistepExprHolder)multistepExprHolder2.clone();
                            multistepExprHolder2.m_exprOwner = null;
                        }
                        catch (CloneNotSupportedException cloneNotSupportedException) {
                            // empty catch block
                        }
                        object4 = ((MultistepExprHolder)expressionOwner).m_next;
                        ((MultistepExprHolder)object4).m_next = null;
                    }
                }
            }
            multistepExprHolder2 = multistepExprHolder2.m_next;
            object3 = object2;
            expressionOwner = object4;
        }
        if (object3 != null) {
            object = bl ? expressionNode : this.findCommonAncestor((MultistepExprHolder)object3);
            object = this.createPseudoVarDecl((ElemTemplateElement)object, this.createIteratorFromSteps((WalkingIterator)((MultistepExprHolder)object3).m_exprOwner.getExpression(), n), bl);
            while (object3 != null) {
                object2 = ((MultistepExprHolder)object3).m_exprOwner;
                expressionNode = (WalkingIterator)object2.getExpression();
                object2.setExpression(this.changePartToRef(((ElemVariable)object).getName(), (WalkingIterator)expressionNode, n, bl));
                object3 = ((MultistepExprHolder)object3).m_next;
            }
        }
        return multistepExprHolder;
    }

    protected int oldFindAndEliminateRedundant(int n, int n2, ExpressionOwner expressionOwner, ElemTemplateElement elemTemplateElement, Vector vector) throws DOMException {
        Serializable serializable = null;
        int n3 = 0;
        int n4 = 0;
        int n5 = vector.size();
        ExpressionNode expressionNode = expressionOwner.getExpression();
        boolean bl = vector == this.m_absPaths;
        LocPathIterator locPathIterator = (LocPathIterator)expressionNode;
        int n6 = n;
        n = n4;
        while (n6 < n5) {
            ExpressionOwner expressionOwner2 = (ExpressionOwner)vector.elementAt(n6);
            expressionNode = serializable;
            n4 = n3;
            int n7 = n;
            if (expressionOwner2 != null) {
                Expression expression = expressionOwner2.getExpression();
                expressionNode = serializable;
                n4 = n3;
                n7 = n;
                if (expression.deepEquals(locPathIterator)) {
                    expressionNode = (LocPathIterator)expression;
                    n4 = n3;
                    n7 = n;
                    if (n3 == 0) {
                        n4 = 1;
                        expressionNode = this.createPseudoVarDecl(elemTemplateElement, locPathIterator, bl);
                        if (expressionNode == null) {
                            return 0;
                        }
                        serializable = ((ElemVariable)expressionNode).getName();
                        this.changeToVarRef((QName)serializable, expressionOwner, vector, elemTemplateElement);
                        vector.setElementAt(((ElemVariable)expressionNode).getSelect(), n2);
                        n7 = n + 1;
                    }
                    this.changeToVarRef((QName)serializable, expressionOwner2, vector, elemTemplateElement);
                    vector.setElementAt(null, n6);
                    expressionNode = serializable;
                }
            }
            ++n6;
            serializable = expressionNode;
            n3 = n4;
            n = ++n7;
        }
        n3 = n;
        if (n == 0) {
            n3 = n;
            if (vector == this.m_absPaths) {
                serializable = this.createPseudoVarDecl(elemTemplateElement, locPathIterator, true);
                if (serializable == null) {
                    return 0;
                }
                this.changeToVarRef(((ElemVariable)serializable).getName(), expressionOwner, vector, elemTemplateElement);
                vector.setElementAt(((ElemVariable)serializable).getSelect(), n2);
                n3 = n + 1;
            }
        }
        return n3;
    }

    boolean partialIsVariable(MultistepExprHolder multistepExprHolder, int n) {
        return 1 == n && ((WalkingIterator)multistepExprHolder.m_exprOwner.getExpression()).getFirstWalker() instanceof FilterExprWalker;
    }

    protected boolean stepsEqual(WalkingIterator predicatedNodeTest, WalkingIterator predicatedNodeTest2, int n) {
        boolean bl;
        block3 : {
            AxesWalker axesWalker = ((WalkingIterator)predicatedNodeTest).getFirstWalker();
            predicatedNodeTest = ((WalkingIterator)predicatedNodeTest2).getFirstWalker();
            int n2 = 0;
            predicatedNodeTest2 = axesWalker;
            do {
                bl = false;
                if (n2 >= n) break block3;
                if (predicatedNodeTest2 == null || predicatedNodeTest == null) break;
                if (!((AxesWalker)predicatedNodeTest2).deepEquals(predicatedNodeTest)) {
                    return false;
                }
                predicatedNodeTest2 = ((AxesWalker)predicatedNodeTest2).getNextWalker();
                predicatedNodeTest = ((AxesWalker)predicatedNodeTest).getNextWalker();
                ++n2;
            } while (true);
            return false;
        }
        if (predicatedNodeTest2 != null || predicatedNodeTest != null) {
            bl = true;
        }
        RedundentExprEliminator.assertion(bl, "Total match is incorrect!");
        return true;
    }

    @Override
    public boolean visitInstruction(ElemTemplateElement elemTemplateElement) {
        Serializable serializable;
        int n = elemTemplateElement.getXSLToken();
        if (n != 17 && n != 19 && n != 28) {
            if (n != 35 && n != 64) {
                return true;
            }
            boolean bl = this.m_isSameContext;
            this.m_isSameContext = false;
            elemTemplateElement.callChildVisitors(this);
            this.m_isSameContext = bl;
            return false;
        }
        if (n == 28) {
            serializable = (ElemForEach)elemTemplateElement;
            ((ElemForEach)serializable).getSelect().callVisitors((ExpressionOwner)((Object)serializable), this);
        }
        serializable = this.m_paths;
        this.m_paths = new Vector();
        elemTemplateElement.callChildVisitors(this, false);
        this.eleminateRedundentLocals(elemTemplateElement);
        this.m_paths = serializable;
        return false;
    }

    @Override
    public boolean visitLocationPath(ExpressionOwner expressionOwner, LocPathIterator cloneable) {
        AxesWalker axesWalker;
        if (cloneable instanceof SelfIteratorNoPredicate) {
            return true;
        }
        if (cloneable instanceof WalkingIterator && (axesWalker = ((WalkingIterator)cloneable).getFirstWalker()) instanceof FilterExprWalker && axesWalker.getNextWalker() == null && ((FilterExprWalker)axesWalker).getInnerExpression() instanceof Variable) {
            return true;
        }
        if (this.isAbsolute((LocPathIterator)cloneable) && (cloneable = this.m_absPaths) != null) {
            ((Vector)cloneable).addElement(expressionOwner);
        } else if (this.m_isSameContext && (cloneable = this.m_paths) != null) {
            ((Vector)cloneable).addElement(expressionOwner);
        }
        return true;
    }

    @Override
    public boolean visitPredicate(ExpressionOwner expressionOwner, Expression expression) {
        boolean bl = this.m_isSameContext;
        this.m_isSameContext = false;
        expression.callVisitors(expressionOwner, this);
        this.m_isSameContext = bl;
        return false;
    }

    @Override
    public boolean visitTopLevelInstruction(ElemTemplateElement elemTemplateElement) {
        if (elemTemplateElement.getXSLToken() != 19) {
            return true;
        }
        return this.visitInstruction(elemTemplateElement);
    }

    class MultistepExprHolder
    implements Cloneable {
        ExpressionOwner m_exprOwner;
        MultistepExprHolder m_next;
        final int m_stepCount;

        MultistepExprHolder(ExpressionOwner expressionOwner, int n, MultistepExprHolder multistepExprHolder) {
            this.m_exprOwner = expressionOwner;
            boolean bl = this.m_exprOwner != null;
            RedundentExprEliminator.assertion(bl, "exprOwner can not be null!");
            this.m_stepCount = n;
            this.m_next = multistepExprHolder;
        }

        MultistepExprHolder addInSortedOrder(ExpressionOwner object, int n) {
            Object object2 = this;
            MultistepExprHolder multistepExprHolder = this;
            MultistepExprHolder multistepExprHolder2 = null;
            while (multistepExprHolder != null) {
                if (n >= multistepExprHolder.m_stepCount) {
                    object = new MultistepExprHolder((ExpressionOwner)object, n, multistepExprHolder);
                    if (multistepExprHolder2 == null) {
                        object2 = object;
                    } else {
                        multistepExprHolder2.m_next = object;
                    }
                    return object2;
                }
                multistepExprHolder2 = multistepExprHolder;
                multistepExprHolder = multistepExprHolder.m_next;
            }
            multistepExprHolder2.m_next = new MultistepExprHolder((ExpressionOwner)object, n, null);
            return object2;
        }

        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }

        protected void diagnose() {
            Object object = System.err;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Found multistep iterators: ");
            ((StringBuilder)object2).append(this.getLength());
            ((StringBuilder)object2).append("  ");
            ((PrintStream)object).print(((StringBuilder)object2).toString());
            object = this;
            while (object != null) {
                object2 = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("");
                stringBuilder.append(((MultistepExprHolder)object).m_stepCount);
                ((PrintStream)object2).print(stringBuilder.toString());
                object = object2 = ((MultistepExprHolder)object).m_next;
                if (object2 == null) continue;
                System.err.print(", ");
                object = object2;
            }
            System.err.println();
        }

        int getLength() {
            int n = 0;
            MultistepExprHolder multistepExprHolder = this;
            while (multistepExprHolder != null) {
                ++n;
                multistepExprHolder = multistepExprHolder.m_next;
            }
            return n;
        }

        MultistepExprHolder unlink(MultistepExprHolder multistepExprHolder) {
            MultistepExprHolder multistepExprHolder2 = this;
            MultistepExprHolder multistepExprHolder3 = this;
            MultistepExprHolder multistepExprHolder4 = null;
            while (multistepExprHolder3 != null) {
                if (multistepExprHolder3 == multistepExprHolder) {
                    if (multistepExprHolder4 == null) {
                        multistepExprHolder2 = multistepExprHolder3.m_next;
                    } else {
                        multistepExprHolder4.m_next = multistepExprHolder3.m_next;
                    }
                    multistepExprHolder3.m_next = null;
                    return multistepExprHolder2;
                }
                multistepExprHolder4 = multistepExprHolder3;
                multistepExprHolder3 = multistepExprHolder3.m_next;
            }
            RedundentExprEliminator.assertion(false, "unlink failed!!!");
            return null;
        }
    }

}

