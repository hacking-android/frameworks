/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;

public abstract class PredicatedNodeTest
extends NodeTest
implements SubContextList {
    static final boolean DEBUG_PREDICATECOUNTING = false;
    static final long serialVersionUID = -6193530757296377351L;
    protected transient boolean m_foundLast = false;
    protected LocPathIterator m_lpi;
    protected int m_predCount = -1;
    transient int m_predicateIndex = -1;
    private Expression[] m_predicates;
    protected transient int[] m_proximityPositions;

    PredicatedNodeTest() {
    }

    PredicatedNodeTest(LocPathIterator locPathIterator) {
        this.m_lpi = locPathIterator;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, TransformerException {
        try {
            objectInputStream.defaultReadObject();
            this.m_predicateIndex = -1;
            this.resetProximityPositions();
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new TransformerException(classNotFoundException);
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public short acceptNode(int n) {
        Throwable throwable2222;
        XPathContext xPathContext = this.m_lpi.getXPathContext();
        xPathContext.pushCurrentNode(n);
        if (this.execute(xPathContext, n) == NodeTest.SCORE_NONE) {
            xPathContext.popCurrentNode();
            return 3;
        }
        if (this.getPredicateCount() > 0) {
            this.countProximityPosition(0);
            boolean bl = this.executePredicates(n, xPathContext);
            if (!bl) {
                xPathContext.popCurrentNode();
                return 3;
            }
        }
        xPathContext.popCurrentNode();
        return 1;
        {
            catch (Throwable throwable2222) {
            }
            catch (TransformerException transformerException) {}
            {
                RuntimeException runtimeException = new RuntimeException(transformerException.getMessage());
                throw runtimeException;
            }
        }
        xPathContext.popCurrentNode();
        throw throwable2222;
    }

    public void callPredicateVisitors(XPathVisitor xPathVisitor) {
        Object object = this.m_predicates;
        if (object != null) {
            int n = ((Expression[])object).length;
            for (int i = 0; i < n; ++i) {
                object = new PredOwner(i);
                if (!xPathVisitor.visitPredicate((ExpressionOwner)object, this.m_predicates[i])) continue;
                this.m_predicates[i].callVisitors((ExpressionOwner)object, xPathVisitor);
            }
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

    public Object clone() throws CloneNotSupportedException {
        PredicatedNodeTest predicatedNodeTest = (PredicatedNodeTest)Object.super.clone();
        int[] arrn = this.m_proximityPositions;
        if (arrn != null && arrn == predicatedNodeTest.m_proximityPositions) {
            predicatedNodeTest.m_proximityPositions = new int[arrn.length];
            arrn = this.m_proximityPositions;
            System.arraycopy(arrn, 0, predicatedNodeTest.m_proximityPositions, 0, arrn.length);
        }
        if (predicatedNodeTest.m_lpi == this) {
            predicatedNodeTest.m_lpi = (LocPathIterator)predicatedNodeTest;
        }
        return predicatedNodeTest;
    }

    protected void countProximityPosition(int n) {
        int[] arrn = this.m_proximityPositions;
        if (arrn != null && n < arrn.length) {
            arrn[n] = arrn[n] + 1;
        }
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
        expression = (PredicatedNodeTest)expression;
        Expression[] arrexpression = this.m_predicates;
        if (arrexpression != null) {
            int n = arrexpression.length;
            arrexpression = ((PredicatedNodeTest)expression).m_predicates;
            if (arrexpression == null || arrexpression.length != n) return false;
            for (int i = 0; i < n; ++i) {
                if (this.m_predicates[i].deepEquals(((PredicatedNodeTest)expression).m_predicates[i])) continue;
                return false;
            }
            return true;
        } else {
            if (((PredicatedNodeTest)expression).m_predicates == null) return true;
            return false;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    boolean executePredicates(int var1_1, XPathContext var2_2) throws TransformerException {
        var3_3 = this.getPredicateCount();
        if (var3_3 == 0) {
            return true;
        }
        var2_2.getNamespaceContext();
        try {
            this.m_predicateIndex = 0;
            var2_2.pushSubContextList(this);
            var2_2.pushNamespaceContext(this.m_lpi.getPrefixResolver());
            var2_2.pushCurrentNode(var1_1);
            for (var1_1 = 0; var1_1 < var3_3; ++var1_1) {
                var4_4 = this.m_predicates[var1_1].execute(var2_2);
                if (2 != var4_4.getType()) ** GOTO lbl32
            }
        }
lbl14: // 4 sources:
        catch (Throwable var4_5) {
            var2_2.popCurrentNode();
            var2_2.popNamespaceContext();
            var2_2.popSubContextList();
            this.m_predicateIndex = -1;
            throw var4_5;
        }
        {
            block8 : {
                var5_6 = this.getProximityPosition(this.m_predicateIndex);
                if (var5_6 != (int)(var6_7 = var4_4.num())) {
                    var2_2.popCurrentNode();
                    var2_2.popNamespaceContext();
                    var2_2.popSubContextList();
                    this.m_predicateIndex = -1;
                    return false;
                }
                ** try [egrp 1[TRYBLOCK] [2 : 114->126)] { 
lbl28: // 1 sources:
                if (!this.m_predicates[var1_1].isStableNumber() || var1_1 != var3_3 - 1) break block8;
                this.m_foundLast = true;
                break block8;
lbl32: // 1 sources:
                var8_8 = var4_4.bool();
                if (var8_8) break block8;
                var2_2.popCurrentNode();
                var2_2.popNamespaceContext();
                var2_2.popSubContextList();
                this.m_predicateIndex = -1;
                return false;
            }
            this.m_predicateIndex = var5_6 = this.m_predicateIndex + 1;
            this.countProximityPosition(var5_6);
            continue;
        }
        var2_2.popCurrentNode();
        var2_2.popNamespaceContext();
        var2_2.popSubContextList();
        this.m_predicateIndex = -1;
        return true;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        int n2 = this.getPredicateCount();
        for (int i = 0; i < n2; ++i) {
            this.m_predicates[i].fixupVariables(vector, n);
        }
    }

    @Override
    public abstract int getLastPos(XPathContext var1);

    public LocPathIterator getLocPathIterator() {
        return this.m_lpi;
    }

    public Expression getPredicate(int n) {
        return this.m_predicates[n];
    }

    public int getPredicateCount() {
        int n = this.m_predCount;
        if (-1 == n) {
            Expression[] arrexpression = this.m_predicates;
            n = arrexpression == null ? 0 : arrexpression.length;
            return n;
        }
        return n;
    }

    public int getPredicateIndex() {
        return this.m_predicateIndex;
    }

    public int getProximityPosition() {
        return this.getProximityPosition(this.m_predicateIndex);
    }

    protected int getProximityPosition(int n) {
        n = n >= 0 ? this.m_proximityPositions[n] : 0;
        return n;
    }

    @Override
    public int getProximityPosition(XPathContext xPathContext) {
        return this.getProximityPosition();
    }

    protected void initPredicateInfo(Compiler arrexpression, int n) throws TransformerException {
        if ((n = arrexpression.getFirstPredicateOpPos(n)) > 0) {
            this.m_predicates = arrexpression.getCompiledPredicates(n);
            if (this.m_predicates != null) {
                for (n = 0; n < (arrexpression = this.m_predicates).length; ++n) {
                    arrexpression[n].exprSetParent(this);
                }
            }
        }
    }

    public void initProximityPosition(int n) throws TransformerException {
        this.m_proximityPositions[n] = 0;
    }

    public boolean isReverseAxes() {
        return false;
    }

    protected String nodeToString(int n) {
        if (-1 != n) {
            DTM dTM = this.m_lpi.getXPathContext().getDTM(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(dTM.getNodeName(n));
            stringBuilder.append("{");
            stringBuilder.append(n + 1);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
        return "null";
    }

    public void resetProximityPositions() {
        int n = this.getPredicateCount();
        if (n > 0) {
            if (this.m_proximityPositions == null) {
                this.m_proximityPositions = new int[n];
            }
            for (int i = 0; i < n; ++i) {
                try {
                    this.initProximityPosition(i);
                }
                catch (Exception exception) {
                    throw new WrappedRuntimeException(exception);
                }
            }
        }
    }

    public void setLocPathIterator(LocPathIterator locPathIterator) {
        this.m_lpi = locPathIterator;
        if (this != locPathIterator) {
            locPathIterator.exprSetParent(this);
        }
    }

    public void setPredicateCount(int n) {
        if (n > 0) {
            Expression[] arrexpression = new Expression[n];
            for (int i = 0; i < n; ++i) {
                arrexpression[i] = this.m_predicates[i];
            }
            this.m_predicates = arrexpression;
        } else {
            this.m_predicates = null;
        }
    }

    class PredOwner
    implements ExpressionOwner {
        int m_index;

        PredOwner(int n) {
            this.m_index = n;
        }

        @Override
        public Expression getExpression() {
            return PredicatedNodeTest.this.m_predicates[this.m_index];
        }

        @Override
        public void setExpression(Expression expression) {
            expression.exprSetParent(PredicatedNodeTest.this);
            PredicatedNodeTest.access$000((PredicatedNodeTest)PredicatedNodeTest.this)[this.m_index] = expression;
        }
    }

}

