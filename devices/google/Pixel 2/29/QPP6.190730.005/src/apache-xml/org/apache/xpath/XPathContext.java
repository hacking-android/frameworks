/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.apache.xml.dtm.ref.sax2dtm.SAX2RTFDTM;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.IntStack;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.ObjectStack;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.SourceTreeManager;
import org.apache.xpath.VariableStack;
import org.apache.xpath.axes.OneStepIteratorForward;
import org.apache.xpath.axes.SubContextList;
import org.apache.xpath.objects.DTMXRTreeFrag;
import org.apache.xpath.objects.XMLStringFactoryImpl;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.XMLReader;

public class XPathContext
extends DTMManager {
    public static final int RECURSIONLIMIT = 4096;
    XPathExpressionContext expressionContext = new XPathExpressionContext();
    private HashMap m_DTMXRTreeFrags = null;
    private Stack m_axesIteratorStack = new Stack();
    private Stack m_contextNodeLists = new Stack();
    private IntStack m_currentExpressionNodes = new IntStack(4096);
    private IntStack m_currentNodes = new IntStack(4096);
    private ErrorListener m_defaultErrorListener;
    protected DTMManager m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
    private ErrorListener m_errorListener;
    private SAX2RTFDTM m_global_rtfdtm = null;
    private boolean m_isSecureProcessing = false;
    private NodeVector m_iteratorRoots = new NodeVector();
    IntStack m_last_pushed_rtfdtm = new IntStack();
    private Object m_owner;
    private Method m_ownerGetErrorListener;
    private IntStack m_predicatePos = new IntStack();
    private NodeVector m_predicateRoots = new NodeVector();
    private ObjectStack m_prefixResolvers = new ObjectStack(4096);
    public XMLReader m_primaryReader;
    private Vector m_rtfdtm_stack = null;
    ObjectStack m_saxLocations = new ObjectStack(4096);
    private SourceTreeManager m_sourceTreeManager = new SourceTreeManager();
    private URIResolver m_uriResolver;
    private VariableStack m_variableStacks;
    private int m_which_rtfdtm = -1;

    public XPathContext() {
        this(true);
    }

    public XPathContext(Object object) {
        this(object, true);
    }

    public XPathContext(Object object, boolean bl) {
        this(bl);
        this.m_owner = object;
        try {
            this.m_ownerGetErrorListener = this.m_owner.getClass().getMethod("getErrorListener", new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
    }

    public XPathContext(boolean bl) {
        this.m_prefixResolvers.push(null);
        this.m_currentNodes.push(-1);
        this.m_currentExpressionNodes.push(-1);
        this.m_saxLocations.push(null);
        VariableStack variableStack = bl ? new VariableStack() : new VariableStack(1);
        this.m_variableStacks = variableStack;
    }

    private void assertion(boolean bl, String string) throws TransformerException {
        ErrorListener errorListener;
        if (!bl && (errorListener = this.getErrorListener()) != null) {
            errorListener.fatalError(new TransformerException(XSLMessages.createMessage("ER_INCORRECT_PROGRAMMER_ASSERTION", new Object[]{string}), (SAXSourceLocator)this.getSAXLocator()));
        }
    }

    private final void releaseDTMXRTreeFrags() {
        Object object = this.m_DTMXRTreeFrags;
        if (object == null) {
            return;
        }
        object = ((HashMap)object).values().iterator();
        while (object.hasNext()) {
            ((DTMXRTreeFrag)object.next()).destruct();
            object.remove();
        }
        this.m_DTMXRTreeFrags = null;
    }

    @Override
    public DTMIterator createDTMIterator(int n) {
        OneStepIteratorForward oneStepIteratorForward = new OneStepIteratorForward(13);
        oneStepIteratorForward.setRoot(n, this);
        return oneStepIteratorForward;
    }

    @Override
    public DTMIterator createDTMIterator(int n, DTMFilter dTMFilter, boolean bl) {
        return this.m_dtmManager.createDTMIterator(n, dTMFilter, bl);
    }

    @Override
    public DTMIterator createDTMIterator(Object object, int n) {
        return this.m_dtmManager.createDTMIterator(object, n);
    }

    @Override
    public DTMIterator createDTMIterator(String string, PrefixResolver prefixResolver) {
        return this.m_dtmManager.createDTMIterator(string, prefixResolver);
    }

    @Override
    public DTM createDocumentFragment() {
        return this.m_dtmManager.createDocumentFragment();
    }

    public Stack getAxesIteratorStackStacks() {
        return this.m_axesIteratorStack;
    }

    public final int getContextNode() {
        return this.getCurrentNode();
    }

    public final DTMIterator getContextNodeList() {
        if (this.m_contextNodeLists.size() > 0) {
            return (DTMIterator)this.m_contextNodeLists.peek();
        }
        return null;
    }

    public Stack getContextNodeListsStack() {
        return this.m_contextNodeLists;
    }

    public final DTMIterator getContextNodes() {
        block3 : {
            DTMIterator dTMIterator;
            try {
                dTMIterator = this.getContextNodeList();
                if (dTMIterator == null) break block3;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
            dTMIterator = dTMIterator.cloneWithReset();
            return dTMIterator;
        }
        return null;
    }

    public final int getCurrentExpressionNode() {
        return this.m_currentExpressionNodes.peek();
    }

    public IntStack getCurrentExpressionNodeStack() {
        return this.m_currentExpressionNodes;
    }

    public final int getCurrentNode() {
        return this.m_currentNodes.peek();
    }

    public SubContextList getCurrentNodeList() {
        SubContextList subContextList = this.m_axesIteratorStack.isEmpty() ? null : (SubContextList)this.m_axesIteratorStack.elementAt(0);
        return subContextList;
    }

    public IntStack getCurrentNodeStack() {
        return this.m_currentNodes;
    }

    @Override
    public DTM getDTM(int n) {
        return this.m_dtmManager.getDTM(n);
    }

    @Override
    public DTM getDTM(Source source, boolean bl, DTMWSFilter dTMWSFilter, boolean bl2, boolean bl3) {
        return this.m_dtmManager.getDTM(source, bl, dTMWSFilter, bl2, bl3);
    }

    @Override
    public int getDTMHandleFromNode(Node node) {
        return this.m_dtmManager.getDTMHandleFromNode(node);
    }

    @Override
    public int getDTMIdentity(DTM dTM) {
        return this.m_dtmManager.getDTMIdentity(dTM);
    }

    public DTMManager getDTMManager() {
        return this.m_dtmManager;
    }

    public DTMXRTreeFrag getDTMXRTreeFrag(int n) {
        if (this.m_DTMXRTreeFrags == null) {
            this.m_DTMXRTreeFrags = new HashMap();
        }
        if (this.m_DTMXRTreeFrags.containsKey(new Integer(n))) {
            return (DTMXRTreeFrag)this.m_DTMXRTreeFrags.get(new Integer(n));
        }
        DTMXRTreeFrag dTMXRTreeFrag = new DTMXRTreeFrag(n, this);
        this.m_DTMXRTreeFrags.put(new Integer(n), dTMXRTreeFrag);
        return dTMXRTreeFrag;
    }

    public final ErrorListener getErrorListener() {
        ErrorListener errorListener;
        ErrorListener errorListener2 = this.m_errorListener;
        if (errorListener2 != null) {
            return errorListener2;
        }
        errorListener2 = errorListener = null;
        try {
            if (this.m_ownerGetErrorListener != null) {
                errorListener2 = (ErrorListener)this.m_ownerGetErrorListener.invoke(this.m_owner, new Object[0]);
            }
        }
        catch (Exception exception) {
            errorListener2 = errorListener;
        }
        errorListener = errorListener2;
        if (errorListener2 == null) {
            if (this.m_defaultErrorListener == null) {
                this.m_defaultErrorListener = new DefaultErrorHandler();
            }
            errorListener = this.m_defaultErrorListener;
        }
        return errorListener;
    }

    public ExpressionContext getExpressionContext() {
        return this.expressionContext;
    }

    public DTM getGlobalRTFDTM() {
        SAX2RTFDTM sAX2RTFDTM = this.m_global_rtfdtm;
        if (sAX2RTFDTM == null || sAX2RTFDTM.isTreeIncomplete()) {
            this.m_global_rtfdtm = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
        }
        return this.m_global_rtfdtm;
    }

    public final int getIteratorRoot() {
        return this.m_iteratorRoots.peepOrNull();
    }

    public final PrefixResolver getNamespaceContext() {
        return (PrefixResolver)this.m_prefixResolvers.peek();
    }

    public Object getOwnerObject() {
        return this.m_owner;
    }

    public final int getPredicatePos() {
        return this.m_predicatePos.peek();
    }

    public final int getPredicateRoot() {
        return this.m_predicateRoots.peepOrNull();
    }

    public final XMLReader getPrimaryReader() {
        return this.m_primaryReader;
    }

    public DTM getRTFDTM() {
        Object object = this.m_rtfdtm_stack;
        if (object == null) {
            this.m_rtfdtm_stack = new Vector();
            object = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
            this.m_rtfdtm_stack.addElement(object);
            ++this.m_which_rtfdtm;
        } else {
            int n = this.m_which_rtfdtm;
            if (n < 0) {
                this.m_which_rtfdtm = ++n;
                object = (SAX2RTFDTM)((Vector)object).elementAt(n);
            } else {
                SAX2RTFDTM sAX2RTFDTM = (SAX2RTFDTM)((Vector)object).elementAt(n);
                object = sAX2RTFDTM;
                if (sAX2RTFDTM.isTreeIncomplete()) {
                    this.m_which_rtfdtm = n = this.m_which_rtfdtm + 1;
                    if (n < this.m_rtfdtm_stack.size()) {
                        object = (SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(this.m_which_rtfdtm);
                    } else {
                        object = (SAX2RTFDTM)this.m_dtmManager.getDTM(null, true, null, false, false);
                        this.m_rtfdtm_stack.addElement(object);
                    }
                }
            }
        }
        return object;
    }

    public SourceLocator getSAXLocator() {
        return (SourceLocator)this.m_saxLocations.peek();
    }

    public final SourceTreeManager getSourceTreeManager() {
        return this.m_sourceTreeManager;
    }

    public SubContextList getSubContextList() {
        SubContextList subContextList = this.m_axesIteratorStack.isEmpty() ? null : (SubContextList)this.m_axesIteratorStack.peek();
        return subContextList;
    }

    public final URIResolver getURIResolver() {
        return this.m_uriResolver;
    }

    public final VariableStack getVarStack() {
        return this.m_variableStacks;
    }

    public boolean isSecureProcessing() {
        return this.m_isSecureProcessing;
    }

    public final void popContextNodeList() {
        if (this.m_contextNodeLists.isEmpty()) {
            System.err.println("Warning: popContextNodeList when stack is empty!");
        } else {
            this.m_contextNodeLists.pop();
        }
    }

    public final void popCurrentExpressionNode() {
        this.m_currentExpressionNodes.quickPop(1);
    }

    public final void popCurrentNode() {
        this.m_currentNodes.quickPop(1);
    }

    public final void popCurrentNodeAndExpression() {
        this.m_currentNodes.quickPop(1);
        this.m_currentExpressionNodes.quickPop(1);
    }

    public final void popExpressionState() {
        this.m_currentNodes.quickPop(1);
        this.m_currentExpressionNodes.quickPop(1);
        this.m_prefixResolvers.pop();
    }

    public final void popIteratorRoot() {
        this.m_iteratorRoots.popQuick();
    }

    public final void popNamespaceContext() {
        this.m_prefixResolvers.pop();
    }

    public final void popPredicatePos() {
        this.m_predicatePos.pop();
    }

    public final void popPredicateRoot() {
        this.m_predicateRoots.popQuick();
    }

    public void popRTFContext() {
        int n = this.m_last_pushed_rtfdtm.pop();
        Vector vector = this.m_rtfdtm_stack;
        if (vector == null) {
            return;
        }
        if (this.m_which_rtfdtm == n) {
            if (n >= 0) {
                ((SAX2RTFDTM)vector.elementAt(n)).popRewindMark();
            }
        } else {
            int n2;
            while ((n2 = this.m_which_rtfdtm--) != n) {
                ((SAX2RTFDTM)this.m_rtfdtm_stack.elementAt(n2)).popRewindMark();
            }
        }
    }

    public void popSAXLocator() {
        this.m_saxLocations.pop();
    }

    public final void popSubContextList() {
        this.m_axesIteratorStack.pop();
    }

    public final void pushContextNodeList(DTMIterator dTMIterator) {
        this.m_contextNodeLists.push(dTMIterator);
    }

    public final void pushCurrentExpressionNode(int n) {
        this.m_currentExpressionNodes.push(n);
    }

    public final void pushCurrentNode(int n) {
        this.m_currentNodes.push(n);
    }

    public final void pushCurrentNodeAndExpression(int n, int n2) {
        this.m_currentNodes.push(n);
        this.m_currentExpressionNodes.push(n);
    }

    public final void pushExpressionState(int n, int n2, PrefixResolver prefixResolver) {
        this.m_currentNodes.push(n);
        this.m_currentExpressionNodes.push(n);
        this.m_prefixResolvers.push(prefixResolver);
    }

    public final void pushIteratorRoot(int n) {
        this.m_iteratorRoots.push(n);
    }

    public final void pushNamespaceContext(PrefixResolver prefixResolver) {
        this.m_prefixResolvers.push(prefixResolver);
    }

    public final void pushNamespaceContextNull() {
        this.m_prefixResolvers.push(null);
    }

    public final void pushPredicatePos(int n) {
        this.m_predicatePos.push(n);
    }

    public final void pushPredicateRoot(int n) {
        this.m_predicateRoots.push(n);
    }

    public void pushRTFContext() {
        this.m_last_pushed_rtfdtm.push(this.m_which_rtfdtm);
        if (this.m_rtfdtm_stack != null) {
            ((SAX2RTFDTM)this.getRTFDTM()).pushRewindMark();
        }
    }

    public void pushSAXLocator(SourceLocator sourceLocator) {
        this.m_saxLocations.push(sourceLocator);
    }

    public void pushSAXLocatorNull() {
        this.m_saxLocations.push(null);
    }

    public final void pushSubContextList(SubContextList subContextList) {
        this.m_axesIteratorStack.push(subContextList);
    }

    @Override
    public boolean release(DTM dTM, boolean bl) {
        Vector vector = this.m_rtfdtm_stack;
        if (vector != null && vector.contains(dTM)) {
            return false;
        }
        return this.m_dtmManager.release(dTM, bl);
    }

    public void reset() {
        this.releaseDTMXRTreeFrags();
        Object object = this.m_rtfdtm_stack;
        if (object != null) {
            object = ((Vector)object).elements();
            while (object.hasMoreElements()) {
                this.m_dtmManager.release((DTM)object.nextElement(), true);
            }
        }
        this.m_rtfdtm_stack = null;
        this.m_which_rtfdtm = -1;
        object = this.m_global_rtfdtm;
        if (object != null) {
            this.m_dtmManager.release((DTM)object, true);
        }
        this.m_global_rtfdtm = null;
        this.m_dtmManager = DTMManager.newInstance(XMLStringFactoryImpl.getFactory());
        this.m_saxLocations.removeAllElements();
        this.m_axesIteratorStack.removeAllElements();
        this.m_contextNodeLists.removeAllElements();
        this.m_currentExpressionNodes.removeAllElements();
        this.m_currentNodes.removeAllElements();
        this.m_iteratorRoots.RemoveAllNoClear();
        this.m_predicatePos.removeAllElements();
        this.m_predicateRoots.RemoveAllNoClear();
        this.m_prefixResolvers.removeAllElements();
        this.m_prefixResolvers.push(null);
        this.m_currentNodes.push(-1);
        this.m_currentExpressionNodes.push(-1);
        this.m_saxLocations.push(null);
    }

    public void setAxesIteratorStackStacks(Stack stack) {
        this.m_axesIteratorStack = stack;
    }

    public void setContextNodeListsStack(Stack stack) {
        this.m_contextNodeLists = stack;
    }

    public void setCurrentExpressionNodeStack(IntStack intStack) {
        this.m_currentExpressionNodes = intStack;
    }

    public void setCurrentNodeStack(IntStack intStack) {
        this.m_currentNodes = intStack;
    }

    public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException {
        if (errorListener != null) {
            this.m_errorListener = errorListener;
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createXPATHMessage("ER_NULL_ERROR_HANDLER", null));
    }

    public final void setNamespaceContext(PrefixResolver prefixResolver) {
        this.m_prefixResolvers.setTop(prefixResolver);
    }

    public void setPrimaryReader(XMLReader xMLReader) {
        this.m_primaryReader = xMLReader;
    }

    public void setSAXLocator(SourceLocator sourceLocator) {
        this.m_saxLocations.setTop(sourceLocator);
    }

    public void setSecureProcessing(boolean bl) {
        this.m_isSecureProcessing = bl;
    }

    public void setSourceTreeManager(SourceTreeManager sourceTreeManager) {
        this.m_sourceTreeManager = sourceTreeManager;
    }

    public void setURIResolver(URIResolver uRIResolver) {
        this.m_uriResolver = uRIResolver;
    }

    public final void setVarStack(VariableStack variableStack) {
        this.m_variableStacks = variableStack;
    }

    public class XPathExpressionContext
    implements ExpressionContext {
        @Override
        public Node getContextNode() {
            int n = XPathContext.this.getCurrentNode();
            return XPathContext.this.getDTM(n).getNode(n);
        }

        @Override
        public NodeIterator getContextNodes() {
            return new DTMNodeIterator(XPathContext.this.getContextNodeList());
        }

        public DTMManager getDTMManager() {
            return XPathContext.this.m_dtmManager;
        }

        @Override
        public ErrorListener getErrorListener() {
            return XPathContext.this.getErrorListener();
        }

        @Override
        public final XObject getVariableOrParam(QName qName) throws TransformerException {
            return XPathContext.this.m_variableStacks.getVariableOrParam(XPathContext.this, qName);
        }

        @Override
        public XPathContext getXPathContext() {
            return XPathContext.this;
        }

        @Override
        public double toNumber(Node node) {
            int n = XPathContext.this.getDTMHandleFromNode(node);
            return ((XString)XPathContext.this.getDTM(n).getStringValue(n)).num();
        }

        @Override
        public String toString(Node node) {
            int n = XPathContext.this.getDTMHandleFromNode(node);
            return XPathContext.this.getDTM(n).getStringValue(n).toString();
        }
    }

}

