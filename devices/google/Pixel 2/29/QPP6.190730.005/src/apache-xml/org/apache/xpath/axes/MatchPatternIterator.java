/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.LocPathIterator;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.OpMap;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;
import org.apache.xpath.patterns.StepPattern;

public class MatchPatternIterator
extends LocPathIterator {
    private static final boolean DEBUG = false;
    static final long serialVersionUID = -5201153767396296474L;
    protected StepPattern m_pattern;
    protected int m_superAxis = -1;
    protected DTMAxisTraverser m_traverser;

    MatchPatternIterator(Compiler compiler, int n, int n2) throws TransformerException {
        super(compiler, n, n2, false);
        this.m_pattern = WalkerFactory.loadSteps(this, compiler, OpMap.getFirstChildPos(n), 0);
        n = 0;
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if ((671088640 & n2) != 0) {
            n = 1;
        }
        if ((98066432 & n2) != 0) {
            bl = true;
        }
        if ((458752 & n2) != 0) {
            bl2 = true;
        }
        if ((2129920 & n2) != 0) {
            bl3 = true;
        }
        this.m_superAxis = n == 0 && !bl ? (bl2 ? (bl3 ? 14 : 5) : 16) : (bl3 ? 16 : 17);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public short acceptNode(int n, XPathContext xPathContext) {
        Throwable throwable2222;
        short s;
        xPathContext.pushCurrentNode(n);
        xPathContext.pushIteratorRoot(this.m_context);
        XObject xObject = this.m_pattern.execute(xPathContext);
        XNumber xNumber = NodeTest.SCORE_NONE;
        if (xObject == xNumber) {
            n = 3;
            s = n;
        } else {
            n = 1;
            s = n;
        }
        xPathContext.popCurrentNode();
        xPathContext.popIteratorRoot();
        return s;
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
        xPathContext.popIteratorRoot();
        throw throwable2222;
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            this.m_traverser = null;
            super.detach();
        }
    }

    protected int getNextNode() {
        int n = -1 == this.m_lastFetched ? this.m_traverser.first(this.m_context) : this.m_traverser.next(this.m_context, this.m_lastFetched);
        this.m_lastFetched = n;
        return this.m_lastFetched;
    }

    @Override
    public int nextNode() {
        int n;
        int n2;
        VariableStack variableStack;
        if (this.m_foundLast) {
            return -1;
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
            n = this.getNextNode();
            if (-1 == n) break;
            if (1 != this.acceptNode(n, this.m_execContext) && n != -1) continue;
            break;
        } while (true);
        if (-1 != n) {
            this.incrementCurrentPos();
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
        this.m_traverser = this.m_cdtm.getAxisTraverser(this.m_superAxis);
    }
}

