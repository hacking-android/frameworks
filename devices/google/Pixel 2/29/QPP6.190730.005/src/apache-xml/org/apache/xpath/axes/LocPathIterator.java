/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.axes.IteratorPool;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.axes.PredicatedNodeTest;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.axes.WalkerFactory;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.patterns.NodeTest;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public abstract class LocPathIterator
extends PredicatedNodeTest
implements Cloneable,
DTMIterator,
Serializable,
PathComponent {
    static final long serialVersionUID = -4602476357268405754L;
    protected boolean m_allowDetach = true;
    protected transient DTM m_cdtm;
    protected transient IteratorPool m_clones = new IteratorPool(this);
    protected transient int m_context = -1;
    protected transient int m_currentContextNode = -1;
    protected transient XPathContext m_execContext;
    private boolean m_isTopLevel = false;
    public transient int m_lastFetched = -1;
    protected transient int m_length = -1;
    protected transient int m_pos = 0;
    private PrefixResolver m_prefixResolver;
    transient int m_stackFrame = -1;

    protected LocPathIterator() {
    }

    protected LocPathIterator(PrefixResolver prefixResolver) {
        this.setLocPathIterator(this);
        this.m_prefixResolver = prefixResolver;
    }

    protected LocPathIterator(Compiler compiler, int n, int n2) throws TransformerException {
        this(compiler, n, n2, true);
    }

    protected LocPathIterator(Compiler compiler, int n, int n2, boolean bl) throws TransformerException {
        this.setLocPathIterator(this);
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

    @Override
    public void allowDetachToRelease(boolean bl) {
        this.m_allowDetach = bl;
    }

    @Override
    public DTMIterator asIterator(XPathContext xPathContext, int n) throws TransformerException {
        XNodeSet xNodeSet = new XNodeSet((LocPathIterator)this.m_clones.getInstance());
        xNodeSet.setRoot(n, xPathContext);
        return xNodeSet;
    }

    @Override
    public int asNode(XPathContext xPathContext) throws TransformerException {
        DTMIterator dTMIterator = this.m_clones.getInstance();
        dTMIterator.setRoot(xPathContext.getCurrentNode(), xPathContext);
        int n = dTMIterator.nextNode();
        dTMIterator.detach();
        return n;
    }

    @Override
    public boolean bool(XPathContext xPathContext) throws TransformerException {
        boolean bl = this.asNode(xPathContext) != -1;
        return bl;
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        if (xPathVisitor.visitLocationPath(expressionOwner, this)) {
            xPathVisitor.visitStep(expressionOwner, this);
            this.callPredicateVisitors(xPathVisitor);
        }
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        LocPathIterator locPathIterator = (LocPathIterator)this.m_clones.getInstanceOrThrow();
        locPathIterator.m_execContext = this.m_execContext;
        locPathIterator.m_cdtm = this.m_cdtm;
        locPathIterator.m_context = this.m_context;
        locPathIterator.m_currentContextNode = this.m_currentContextNode;
        locPathIterator.m_stackFrame = this.m_stackFrame;
        return locPathIterator;
    }

    @Override
    public void detach() {
        if (this.m_allowDetach) {
            this.m_execContext = null;
            this.m_cdtm = null;
            this.m_length = -1;
            this.m_pos = 0;
            this.m_lastFetched = -1;
            this.m_context = -1;
            this.m_currentContextNode = -1;
            this.m_clones.freeInstance(this);
        }
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        XNodeSet xNodeSet = new XNodeSet((LocPathIterator)this.m_clones.getInstance());
        xNodeSet.setRoot(xPathContext.getCurrentNode(), xPathContext);
        return xNodeSet;
    }

    @Override
    public void executeCharsToContentHandler(XPathContext object, ContentHandler contentHandler) throws TransformerException, SAXException {
        LocPathIterator locPathIterator = (LocPathIterator)this.m_clones.getInstance();
        locPathIterator.setRoot(((XPathContext)object).getCurrentNode(), object);
        int n = locPathIterator.nextNode();
        object = locPathIterator.getDTM(n);
        locPathIterator.detach();
        if (n != -1) {
            object.dispatchCharactersEvents(n, contentHandler, false);
        }
    }

    @Override
    public int getAnalysisBits() {
        return WalkerFactory.getAnalysisBitFromAxes(this.getAxis());
    }

    @Override
    public int getAxis() {
        return -1;
    }

    public final int getContext() {
        return this.m_context;
    }

    public final int getCurrentContextNode() {
        return this.m_currentContextNode;
    }

    @Override
    public int getCurrentNode() {
        return this.m_lastFetched;
    }

    @Override
    public final int getCurrentPos() {
        return this.m_pos;
    }

    @Override
    public DTM getDTM(int n) {
        return this.m_execContext.getDTM(n);
    }

    @Override
    public DTMManager getDTMManager() {
        return this.m_execContext.getDTMManager();
    }

    @Override
    public boolean getExpandEntityReferences() {
        return true;
    }

    public DTMFilter getFilter() {
        return null;
    }

    public final boolean getFoundLast() {
        return this.m_foundLast;
    }

    public boolean getIsTopLevel() {
        return this.m_isTopLevel;
    }

    @Override
    public int getLastPos(XPathContext xPathContext) {
        return this.getLength();
    }

    @Override
    public int getLength() {
        LocPathIterator locPathIterator;
        int n;
        boolean bl;
        block6 : {
            bl = this == this.m_execContext.getSubContextList();
            int n2 = this.getPredicateCount();
            if (-1 != this.m_length && bl && this.m_predicateIndex < 1) {
                return this.m_length;
            }
            if (this.m_foundLast) {
                return this.m_pos;
            }
            int n3 = this.m_predicateIndex >= 0 ? this.getProximityPosition() : this.m_pos;
            try {
                locPathIterator = (LocPathIterator)this.clone();
                n = n3;
                if (n2 <= 0) break block6;
                n = n3;
                if (!bl) break block6;
                locPathIterator.m_predCount = this.m_predicateIndex;
                n = n3;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return -1;
            }
        }
        while (-1 != locPathIterator.nextNode()) {
            ++n;
        }
        if (bl && this.m_predicateIndex < 1) {
            this.m_length = n;
        }
        return n;
    }

    public final PrefixResolver getPrefixResolver() {
        if (this.m_prefixResolver == null) {
            this.m_prefixResolver = (PrefixResolver)((Object)this.getExpressionOwner());
        }
        return this.m_prefixResolver;
    }

    @Override
    public int getRoot() {
        return this.m_context;
    }

    @Override
    public int getWhatToShow() {
        return -17;
    }

    public final XPathContext getXPathContext() {
        return this.m_execContext;
    }

    public void incrementCurrentPos() {
        ++this.m_pos;
    }

    @Override
    public boolean isDocOrdered() {
        return true;
    }

    @Override
    public boolean isFresh() {
        boolean bl = this.m_pos == 0;
        return bl;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public boolean isNodesetExpr() {
        return true;
    }

    @Override
    public int item(int n) {
        this.assertion(false, "item(int index) not supported by this iterator!");
        return 0;
    }

    @Override
    public abstract int nextNode();

    @Override
    public int previousNode() {
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_ITERATE", null));
    }

    @Override
    public void reset() {
        this.assertion(false, "This iterator can not reset!");
    }

    protected int returnNextNode(int n) {
        if (-1 != n) {
            ++this.m_pos;
        }
        this.m_lastFetched = n;
        if (-1 == n) {
            this.m_foundLast = true;
        }
        return n;
    }

    @Override
    public void runTo(int n) {
        if (!(this.m_foundLast || n >= 0 && n <= this.getCurrentPos())) {
            if (-1 == n) {
                while (-1 != this.nextNode()) {
                }
            } else {
                while (-1 != this.nextNode() && this.getCurrentPos() < n) {
                }
            }
            return;
        }
    }

    public final void setCurrentContextNode(int n) {
        this.m_currentContextNode = n;
    }

    @Override
    public void setCurrentPos(int n) {
        this.assertion(false, "setCurrentPos not supported by this iterator!");
    }

    public void setEnvironment(Object object) {
    }

    public void setIsTopLevel(boolean bl) {
        this.m_isTopLevel = bl;
    }

    @Override
    public void setItem(int n, int n2) {
        this.assertion(false, "setItem not supported by this iterator!");
    }

    protected void setNextPosition(int n) {
        this.assertion(false, "setNextPosition not supported in this iterator!");
    }

    @Override
    public void setRoot(int n, Object object) {
        this.m_context = n;
        this.m_execContext = object = (XPathContext)object;
        this.m_cdtm = ((XPathContext)object).getDTM(n);
        this.m_currentContextNode = n;
        if (this.m_prefixResolver == null) {
            this.m_prefixResolver = ((XPathContext)object).getNamespaceContext();
        }
        this.m_lastFetched = -1;
        this.m_foundLast = false;
        this.m_pos = 0;
        this.m_length = -1;
        if (this.m_isTopLevel) {
            this.m_stackFrame = ((XPathContext)object).getVarStack().getStackFrame();
        }
    }

    @Override
    public void setShouldCacheNodes(boolean bl) {
        this.assertion(false, "setShouldCacheNodes not supported by this iterater!");
    }

    public int size() {
        this.assertion(false, "size() not supported by this iterator!");
        return 0;
    }
}

