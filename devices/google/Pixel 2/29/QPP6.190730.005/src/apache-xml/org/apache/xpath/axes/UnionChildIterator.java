/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.ChildTestIterator;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;

public class UnionChildIterator
extends ChildTestIterator {
    static final long serialVersionUID = 3500298482193003495L;
    private PredicatedNodeTest[] m_nodeTests = null;

    public UnionChildIterator() {
        super(null);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public short acceptNode(int n) {
        Throwable throwable2222;
        XPathContext xPathContext = this.getXPathContext();
        xPathContext.pushCurrentNode(n);
        for (int i = 0; i < this.m_nodeTests.length; ++i) {
            PredicatedNodeTest predicatedNodeTest = this.m_nodeTests[i];
            if (predicatedNodeTest.execute(xPathContext, n) == NodeTest.SCORE_NONE) continue;
            if (predicatedNodeTest.getPredicateCount() <= 0) {
                xPathContext.popCurrentNode();
                return 1;
            }
            boolean bl = predicatedNodeTest.executePredicates(n, xPathContext);
            if (!bl) continue;
            xPathContext.popCurrentNode();
            return 1;
        }
        xPathContext.popCurrentNode();
        return 3;
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

    public void addNodeTest(PredicatedNodeTest predicatedNodeTest) {
        PredicatedNodeTest[] arrpredicatedNodeTest = this.m_nodeTests;
        if (arrpredicatedNodeTest == null) {
            this.m_nodeTests = new PredicatedNodeTest[1];
            this.m_nodeTests[0] = predicatedNodeTest;
        } else {
            PredicatedNodeTest[] arrpredicatedNodeTest2 = this.m_nodeTests;
            int n = arrpredicatedNodeTest.length;
            this.m_nodeTests = new PredicatedNodeTest[n + 1];
            System.arraycopy(arrpredicatedNodeTest2, 0, this.m_nodeTests, 0, n);
            this.m_nodeTests[n] = predicatedNodeTest;
        }
        predicatedNodeTest.exprSetParent(this);
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
        if (this.m_nodeTests != null) {
            PredicatedNodeTest[] arrpredicatedNodeTest;
            for (int i = 0; i < (arrpredicatedNodeTest = this.m_nodeTests).length; ++i) {
                arrpredicatedNodeTest[i].fixupVariables(vector, n);
            }
        }
    }
}

