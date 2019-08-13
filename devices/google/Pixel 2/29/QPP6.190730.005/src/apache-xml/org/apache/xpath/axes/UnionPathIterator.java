/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.AxesWalker;
import org.apache.xpath.axes.FilterExprWalker;
import org.apache.xpath.axes.HasPositionalPredChecker;
import org.apache.xpath.axes.IteratorPool;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.UnionChildIterator;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.axes.WalkingIterator;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public class UnionPathIterator
extends LocPathIterator
implements Cloneable,
DTMIterator,
Serializable,
PathComponent {
    static final long serialVersionUID = -3910351546843826781L;
    protected LocPathIterator[] m_exprs;
    protected DTMIterator[] m_iterators;

    public UnionPathIterator() {
        this.m_iterators = null;
        this.m_exprs = null;
    }

    public UnionPathIterator(Compiler compiler, int n) throws TransformerException {
        this.loadLocationPaths(compiler, OpMap.getFirstChildPos(n), 0);
    }

    public static LocPathIterator createUnionIterator(Compiler object, int n) throws TransformerException {
        LocPathIterator locPathIterator;
        object = new UnionPathIterator((Compiler)object, n);
        int n2 = ((UnionPathIterator)object).m_exprs.length;
        int n3 = 1;
        int n4 = 0;
        do {
            n = n3;
            if (n4 >= n2) break;
            locPathIterator = ((UnionPathIterator)object).m_exprs[n4];
            if (locPathIterator.getAxis() != 3) {
                n = 0;
                break;
            }
            if (HasPositionalPredChecker.check(locPathIterator)) {
                n = 0;
                break;
            }
            ++n4;
        } while (true);
        if (n != 0) {
            locPathIterator = new UnionChildIterator();
            for (n = 0; n < n2; ++n) {
                ((UnionChildIterator)locPathIterator).addNodeTest(((UnionPathIterator)object).m_exprs[n]);
            }
            return locPathIterator;
        }
        return object;
    }

    private void readObject(ObjectInputStream object) throws IOException, TransformerException {
        try {
            ((ObjectInputStream)object).defaultReadObject();
            this.m_clones = object = new IteratorPool(this);
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new TransformerException(classNotFoundException);
        }
    }

    public void addIterator(DTMIterator dTMIterator) {
        DTMIterator[] arrdTMIterator = this.m_iterators;
        if (arrdTMIterator == null) {
            this.m_iterators = new DTMIterator[1];
            this.m_iterators[0] = dTMIterator;
        } else {
            DTMIterator[] arrdTMIterator2 = this.m_iterators;
            int n = arrdTMIterator.length;
            this.m_iterators = new DTMIterator[n + 1];
            System.arraycopy(arrdTMIterator2, 0, this.m_iterators, 0, n);
            this.m_iterators[n] = dTMIterator;
        }
        dTMIterator.nextNode();
        if (dTMIterator instanceof Expression) {
            ((Expression)((Object)dTMIterator)).exprSetParent(this);
        }
    }

    @Override
    public void callVisitors(ExpressionOwner arrlocPathIterator, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitUnionPath((ExpressionOwner)arrlocPathIterator, this) && (arrlocPathIterator = this.m_exprs) != null) {
            int n = arrlocPathIterator.length;
            for (int i = 0; i < n; ++i) {
                this.m_exprs[i].callVisitors(new iterOwner(i), xPathVisitor);
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        UnionPathIterator unionPathIterator = (UnionPathIterator)super.clone();
        DTMIterator[] arrdTMIterator = this.m_iterators;
        if (arrdTMIterator != null) {
            int n = arrdTMIterator.length;
            unionPathIterator.m_iterators = new DTMIterator[n];
            for (int i = 0; i < n; ++i) {
                unionPathIterator.m_iterators[i] = (DTMIterator)this.m_iterators[i].clone();
            }
        }
        return unionPathIterator;
    }

    protected LocPathIterator createDTMIterator(Compiler compiler, int n) throws TransformerException {
        boolean bl = compiler.getLocationPathDepth() <= 0;
        return (LocPathIterator)WalkerFactory.newDTMIterator(compiler, n, bl);
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
        expression = (UnionPathIterator)expression;
        LocPathIterator[] arrlocPathIterator = this.m_exprs;
        if (arrlocPathIterator != null) {
            int n = arrlocPathIterator.length;
            arrlocPathIterator = ((UnionPathIterator)expression).m_exprs;
            if (arrlocPathIterator == null || arrlocPathIterator.length != n) return false;
            for (int i = 0; i < n; ++i) {
                if (this.m_exprs[i].deepEquals(((UnionPathIterator)expression).m_exprs[i])) continue;
                return false;
            }
            return true;
        } else {
            if (((UnionPathIterator)expression).m_exprs == null) return true;
            return false;
        }
    }

    @Override
    public void detach() {
        DTMIterator[] arrdTMIterator;
        if (this.m_allowDetach && (arrdTMIterator = this.m_iterators) != null) {
            int n = arrdTMIterator.length;
            for (int i = 0; i < n; ++i) {
                this.m_iterators[i].detach();
            }
            this.m_iterators = null;
        }
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        LocPathIterator[] arrlocPathIterator;
        for (int i = 0; i < (arrlocPathIterator = this.m_exprs).length; ++i) {
            arrlocPathIterator[i].fixupVariables(vector, n);
        }
    }

    @Override
    public int getAnalysisBits() {
        int n = 0;
        int n2 = 0;
        LocPathIterator[] arrlocPathIterator = this.m_exprs;
        if (arrlocPathIterator != null) {
            int n3 = arrlocPathIterator.length;
            int n4 = 0;
            do {
                n = n2;
                if (n4 >= n3) break;
                n2 |= this.m_exprs[n4].getAnalysisBits();
                ++n4;
            } while (true);
        }
        return n;
    }

    @Override
    public int getAxis() {
        return -1;
    }

    protected void loadLocationPaths(Compiler compiler, int n, int n2) throws TransformerException {
        int n3 = compiler.getOp(n);
        if (n3 == 28) {
            this.loadLocationPaths(compiler, compiler.getNextOpPos(n), n2 + 1);
            this.m_exprs[n2] = this.createDTMIterator(compiler, n);
            this.m_exprs[n2].exprSetParent(this);
        } else {
            switch (n3) {
                default: {
                    this.m_exprs = new LocPathIterator[n2];
                    break;
                }
                case 22: 
                case 23: 
                case 24: 
                case 25: {
                    this.loadLocationPaths(compiler, compiler.getNextOpPos(n), n2 + 1);
                    WalkingIterator walkingIterator = new WalkingIterator(compiler.getNamespaceContext());
                    walkingIterator.exprSetParent(this);
                    if (compiler.getLocationPathDepth() <= 0) {
                        walkingIterator.setIsTopLevel(true);
                    }
                    walkingIterator.m_firstWalker = new FilterExprWalker(walkingIterator);
                    walkingIterator.m_firstWalker.init(compiler, n, n3);
                    this.m_exprs[n2] = walkingIterator;
                }
            }
        }
    }

    @Override
    public int nextNode() {
        if (this.m_foundLast) {
            return -1;
        }
        int n = -1;
        DTMIterator[] arrdTMIterator = this.m_iterators;
        int n2 = n;
        if (arrdTMIterator != null) {
            int n3 = arrdTMIterator.length;
            int n4 = -1;
            for (n2 = 0; n2 < n3; ++n2) {
                int n5;
                int n6 = this.m_iterators[n2].getCurrentNode();
                if (-1 == n6) {
                    n5 = n;
                } else if (-1 == n) {
                    n4 = n2;
                    n5 = n6;
                } else if (n6 == n) {
                    this.m_iterators[n2].nextNode();
                    n5 = n;
                } else {
                    n5 = n;
                    if (this.getDTM(n6).isNodeAfter(n6, n)) {
                        n4 = n2;
                        n5 = n6;
                    }
                }
                n = n5;
            }
            if (-1 != n) {
                this.m_iterators[n4].nextNode();
                this.incrementCurrentPos();
                n2 = n;
            } else {
                this.m_foundLast = true;
                n2 = n;
            }
        }
        this.m_lastFetched = n2;
        return n2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        if (this.m_exprs == null) return;
        int n2 = this.m_exprs.length;
        DTMIterator[] arrdTMIterator = new DTMIterator[n2];
        for (int i = 0; i < n2; ++i) {
            arrdTMIterator[i] = object = this.m_exprs[i].asIterator(this.m_execContext, n);
            object.nextNode();
        }
        try {
            this.m_iterators = arrdTMIterator;
            return;
        }
        catch (Exception exception) {
            throw new WrappedRuntimeException(exception);
        }
    }

    class iterOwner
    implements ExpressionOwner {
        int m_index;

        iterOwner(int n) {
            this.m_index = n;
        }

        @Override
        public Expression getExpression() {
            return UnionPathIterator.this.m_exprs[this.m_index];
        }

        @Override
        public void setExpression(Expression expression) {
            if (!(expression instanceof LocPathIterator)) {
                WalkingIterator walkingIterator = new WalkingIterator(UnionPathIterator.this.getPrefixResolver());
                FilterExprWalker filterExprWalker = new FilterExprWalker(walkingIterator);
                walkingIterator.setFirstWalker(filterExprWalker);
                filterExprWalker.setInnerExpression(expression);
                walkingIterator.exprSetParent(UnionPathIterator.this);
                filterExprWalker.exprSetParent(walkingIterator);
                expression.exprSetParent(filterExprWalker);
                expression = walkingIterator;
            } else {
                expression.exprSetParent(UnionPathIterator.this);
            }
            UnionPathIterator.this.m_exprs[this.m_index] = (LocPathIterator)expression;
        }
    }

}

