/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.Expression;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;

public class DescendantIterator
extends LocPathIterator {
    static final long serialVersionUID = -1190338607743976938L;
    protected int m_axis;
    protected int m_extendedTypeID;
    protected transient DTMAxisTraverser m_traverser;

    public DescendantIterator() {
        super(null);
        this.m_axis = 18;
        this.initNodeTest(-1);
    }

    DescendantIterator(Compiler compiler, int n, int n2) throws TransformerException {
        int n3 = 0;
        super(compiler, n, n2, false);
        int n4 = OpMap.getFirstChildPos(n);
        int n5 = compiler.getOp(n4);
        if (42 == n5) {
            n3 = 1;
        }
        int n6 = 0;
        if (48 == n5) {
            n = 1;
        } else {
            n = n3;
            if (50 == n5) {
                n5 = 1;
                n = n3;
                n6 = n5;
                if (compiler.getOp(compiler.getNextStepPos(n4)) == 42) {
                    n = 1;
                    n6 = n5;
                }
            }
        }
        n3 = n4;
        while ((n3 = compiler.getNextStepPos(n3)) > 0 && -1 != compiler.getOp(n3)) {
            n4 = n3;
        }
        if ((65536 & n2) != 0) {
            n = 0;
        }
        this.m_axis = n6 != 0 ? (n != 0 ? 18 : 17) : (n != 0 ? 5 : 4);
        n = compiler.getWhatToShow(n4);
        if ((n & 67) != 0 && n != -1) {
            this.initNodeTest(n, compiler.getStepNS(n4), compiler.getStepLocalName(n4));
        } else {
            this.initNodeTest(n);
        }
        this.initPredicateInfo(compiler, n4);
    }

    @Override
    public int asNode(XPathContext object) throws TransformerException {
        if (this.getPredicateCount() > 0) {
            return super.asNode((XPathContext)object);
        }
        int n = ((XPathContext)object).getCurrentNode();
        DTM dTM = ((XPathContext)object).getDTM(n);
        DTMAxisTraverser dTMAxisTraverser = dTM.getAxisTraverser(this.m_axis);
        String string = this.getLocalName();
        object = this.getNamespace();
        int n2 = this.m_whatToShow;
        if (-1 != n2 && string != "*" && object != "*") {
            return dTMAxisTraverser.first(n, dTM.getExpandedTypeID((String)object, string, DescendantIterator.getNodeTypeTest(n2)));
        }
        return dTMAxisTraverser.first(n);
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        DescendantIterator descendantIterator = (DescendantIterator)super.cloneWithReset();
        descendantIterator.m_traverser = this.m_traverser;
        descendantIterator.resetProximityPositions();
        return descendantIterator;
    }

    @Override
    public boolean deepEquals(Expression expression) {
        if (!super.deepEquals(expression)) {
            return false;
        }
        return this.m_axis == ((DescendantIterator)expression).m_axis;
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            this.m_traverser = null;
            this.m_extendedTypeID = 0;
            super.detach();
        }
    }

    @Override
    public int getAxis() {
        return this.m_axis;
    }

    @Override
    public int nextNode() {
        int n;
        int n2;
        VariableStack variableStack;
        if (this.m_foundLast) {
            return -1;
        }
        if (-1 == this.m_lastFetched) {
            this.resetProximityPositions();
        }
        if (-1 != this.m_stackFrame) {
            variableStack = this.m_execContext.getVarStack();
            n2 = variableStack.getStackFrame();
            variableStack.setStackFrame(this.m_stackFrame);
        } else {
            variableStack = null;
            n2 = 0;
        }
        do {
            if (this.m_extendedTypeID == 0) {
                n = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched);
                this.m_lastFetched = n;
            } else {
                n = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context, this.m_extendedTypeID) : this.m_traverser.next(this.m_context, this.m_lastFetched, this.m_extendedTypeID);
                this.m_lastFetched = n;
            }
            if (-1 == n) break;
            if (1 != this.acceptNode(n) && n != -1) continue;
            break;
        } while (true);
        if (-1 != n) {
            ++this.m_pos;
            return n;
        }
        try {
            this.m_foundLast = true;
            if (-1 != this.m_stackFrame) {
                variableStack.setStackFrame(n2);
            }
            return -1;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            if (-1 != this.m_stackFrame) {
                variableStack.setStackFrame(n2);
            }
        }
    }

    @Override
    public void setRoot(int n, Object object) {
        super.setRoot(n, object);
        this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_axis);
        String string = this.getLocalName();
        object = this.getNamespace();
        n = this.m_whatToShow;
        if (-1 != n && !"*".equals(string) && !"*".equals(object)) {
            n = DescendantIterator.getNodeTypeTest(n);
            this.m_extendedTypeID = this.m_cdtm.getExpandedTypeID((String)object, string, n);
        } else {
            this.m_extendedTypeID = 0;
        }
    }
}

