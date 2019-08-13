/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.patterns.NodeTest;

public class AxesWalker
extends PredicatedNodeTest
implements Cloneable,
PathComponent,
ExpressionOwner {
    static final long serialVersionUID = -2966031951306601247L;
    protected int m_axis = -1;
    private transient int m_currentNode = -1;
    private DTM m_dtm;
    transient boolean m_isFresh;
    protected AxesWalker m_nextWalker;
    AxesWalker m_prevWalker;
    transient int m_root = -1;
    protected DTMAxisTraverser m_traverser;

    public AxesWalker(LocPathIterator locPathIterator, int n) {
        super(locPathIterator);
        this.m_axis = n;
    }

    static AxesWalker findClone(AxesWalker axesWalker, Vector vector) {
        if (vector != null) {
            int n = vector.size();
            for (int i = 0; i < n; i += 2) {
                if (axesWalker != vector.elementAt(i)) continue;
                return (AxesWalker)vector.elementAt(i + 1);
            }
        }
        return null;
    }

    private int returnNextNode(int n) {
        return n;
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitStep(expressionOwner, this)) {
            this.callPredicateVisitors(xPathVisitor);
            expressionOwner = this.m_nextWalker;
            if (expressionOwner != null) {
                ((AxesWalker)expressionOwner).callVisitors(this, xPathVisitor);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (AxesWalker)super.clone();
    }

    AxesWalker cloneDeep(WalkingIterator walkingIterator, Vector vector) throws CloneNotSupportedException {
        AxesWalker axesWalker;
        AxesWalker axesWalker2 = AxesWalker.findClone(this, vector);
        if (axesWalker2 != null) {
            return axesWalker2;
        }
        axesWalker2 = (AxesWalker)this.clone();
        axesWalker2.setLocPathIterator(walkingIterator);
        if (vector != null) {
            vector.addElement(this);
            vector.addElement(axesWalker2);
        }
        if (this.wi().m_lastUsedWalker == this) {
            walkingIterator.m_lastUsedWalker = axesWalker2;
        }
        if ((axesWalker = this.m_nextWalker) != null) {
            axesWalker2.m_nextWalker = axesWalker.cloneDeep(walkingIterator, vector);
        }
        if (vector != null) {
            axesWalker = this.m_prevWalker;
            if (axesWalker != null) {
                axesWalker2.m_prevWalker = axesWalker.cloneDeep(walkingIterator, vector);
            }
        } else if (this.m_nextWalker != null) {
            axesWalker2.m_nextWalker.m_prevWalker = axesWalker2;
        }
        return axesWalker2;
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        expression = (AxesWalker)expression;
        return this.m_axis == ((AxesWalker)expression).m_axis;
    }

    public void detach() {
        this.m_currentNode = -1;
        this.m_dtm = null;
        this.m_traverser = null;
        this.m_isFresh = true;
        this.m_root = -1;
    }

    @Override
    public int getAnalysisBits() {
        return WalkerFactory.getAnalysisBitFromAxes(this.getAxis());
    }

    public int getAxis() {
        return this.m_axis;
    }

    public final int getCurrentNode() {
        return this.m_currentNode;
    }

    public DTM getDTM(int n) {
        return this.wi().getXPathContext().getDTM(n);
    }

    @Override
    public Expression getExpression() {
        return this.m_nextWalker;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public int getLastPos(XPathContext object) {
        AxesWalker axesWalker;
        int n;
        int n2 = this.getProximityPosition();
        try {
            axesWalker = (AxesWalker)this.clone();
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return -1;
        }
        axesWalker.setPredicateCount(this.m_predicateIndex);
        axesWalker.setNextWalker(null);
        axesWalker.setPrevWalker(null);
        WalkingIterator walkingIterator = this.wi();
        object = walkingIterator.getLastUsedWalker();
        try {
            walkingIterator.setLastUsedWalker(axesWalker);
        }
        catch (Throwable throwable) {
            walkingIterator.setLastUsedWalker((AxesWalker)object);
            throw throwable;
        }
        while (-1 != (n = axesWalker.nextNode())) {
            ++n2;
        }
        walkingIterator.setLastUsedWalker((AxesWalker)object);
        return n2;
    }

    protected int getNextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        if (this.m_isFresh) {
            this.m_currentNode = this.m_traverser.first(this.m_root);
            this.m_isFresh = false;
        } else {
            int n = this.m_currentNode;
            if (-1 != n) {
                this.m_currentNode = this.m_traverser.next(this.m_root, n);
            }
        }
        if (-1 == this.m_currentNode) {
            this.m_foundLast = true;
        }
        return this.m_currentNode;
    }

    public AxesWalker getNextWalker() {
        return this.m_nextWalker;
    }

    public AxesWalker getPrevWalker() {
        return this.m_prevWalker;
    }

    public int getRoot() {
        return this.m_root;
    }

    public void init(Compiler compiler, int n, int n2) throws TransformerException {
        this.initPredicateInfo(compiler, n);
    }

    public boolean isDocOrdered() {
        return true;
    }

    public int nextNode() {
        int n = -1;
        AxesWalker axesWalker = this.wi().getLastUsedWalker();
        do {
            AxesWalker axesWalker2;
            block7 : {
                block6 : {
                    if ((axesWalker2 = axesWalker) == null) break block6;
                    n = axesWalker2.getNextNode();
                    if (-1 == n) {
                        axesWalker = axesWalker2.m_prevWalker;
                        continue;
                    }
                    if (axesWalker2.acceptNode(n) != 1) {
                        axesWalker = axesWalker2;
                        continue;
                    }
                    if (axesWalker2.m_nextWalker != null) break block7;
                    this.wi().setLastUsedWalker(axesWalker2);
                }
                return n;
            }
            axesWalker = axesWalker2.m_nextWalker;
            axesWalker.setRoot(n);
            axesWalker.m_prevWalker = axesWalker2;
        } while (true);
    }

    public void setDefaultDTM(DTM dTM) {
        this.m_dtm = dTM;
    }

    @Override
    public void setExpression(Expression expression) {
        expression.exprSetParent(this);
        this.m_nextWalker = (AxesWalker)expression;
    }

    public void setNextWalker(AxesWalker axesWalker) {
        this.m_nextWalker = axesWalker;
    }

    public void setPrevWalker(AxesWalker axesWalker) {
        this.m_prevWalker = axesWalker;
    }

    public void setRoot(int n) {
        this.m_dtm = this.wi().getXPathContext().getDTM(n);
        this.m_traverser = this.m_dtm.getAxisTraverser(this.m_axis);
        this.m_isFresh = true;
        this.m_foundLast = false;
        this.m_root = n;
        this.m_currentNode = n;
        if (-1 != n) {
            this.resetProximityPositions();
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_SETTING_WALKER_ROOT_TO_NULL", null));
    }

    public final WalkingIterator wi() {
        return (WalkingIterator)this.m_lpi;
    }
}

