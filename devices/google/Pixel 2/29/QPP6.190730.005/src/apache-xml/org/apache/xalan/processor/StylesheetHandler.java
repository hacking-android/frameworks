/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.io.Serializable;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.TemplatesHandler;
import org.apache.xalan.extensions.ExpressionVisitor;
import org.apache.xalan.processor.ProcessorStylesheetDoc;
import org.apache.xalan.processor.ProcessorStylesheetElement;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xalan.processor.XSLTElementDef;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.processor.XSLTSchema;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.FuncDocument;
import org.apache.xalan.templates.FuncFormatNumb;
import org.apache.xalan.templates.Stylesheet;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.BoolStack;
import org.apache.xml.utils.NamespaceSupport2;
import org.apache.xml.utils.NodeConsumer;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.XMLCharacterRecognizer;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.compiler.FunctionTable;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.NamespaceSupport;

public class StylesheetHandler
extends DefaultHandler
implements TemplatesHandler,
PrefixResolver,
NodeConsumer {
    public static final int STYPE_IMPORT = 3;
    public static final int STYPE_INCLUDE = 2;
    public static final int STYPE_ROOT = 1;
    Stack m_baseIdentifiers = new Stack();
    private int m_docOrderCount = 0;
    private int m_elementID = 0;
    private Stack m_elems = new Stack();
    private int m_fragmentID = 0;
    private String m_fragmentIDString;
    private FunctionTable m_funcTable = new FunctionTable();
    private Stack m_importSourceStack = new Stack();
    private Stack m_importStack = new Stack();
    private boolean m_incremental = false;
    Stylesheet m_lastPoppedStylesheet;
    Stack m_nsSupportStack = new Stack();
    private boolean m_optimize = true;
    private Node m_originatingNode;
    private boolean m_parsingComplete = false;
    private Vector m_prefixMappings = new Vector();
    private Stack m_processors = new Stack();
    private XSLTSchema m_schema = new XSLTSchema();
    private boolean m_shouldProcess = true;
    private boolean m_source_location = false;
    private BoolStack m_spacePreserveStack = new BoolStack();
    private int m_stylesheetLevel = -1;
    private Stack m_stylesheetLocatorStack = new Stack();
    private TransformerFactoryImpl m_stylesheetProcessor;
    StylesheetRoot m_stylesheetRoot;
    private int m_stylesheetType = 1;
    private Stack m_stylesheets = new Stack();
    private boolean warnedAboutOldXSLTNamespace = false;

    public StylesheetHandler(TransformerFactoryImpl transformerFactoryImpl) throws TransformerConfigurationException {
        this.m_funcTable.installFunction("document", FuncDocument.class);
        this.m_funcTable.installFunction("format-number", FuncFormatNumb.class);
        this.m_optimize = (Boolean)transformerFactoryImpl.getAttribute("http://xml.apache.org/xalan/features/optimize");
        this.m_incremental = (Boolean)transformerFactoryImpl.getAttribute("http://xml.apache.org/xalan/features/incremental");
        this.m_source_location = (Boolean)transformerFactoryImpl.getAttribute("http://xml.apache.org/xalan/properties/source-location");
        this.init(transformerFactoryImpl);
    }

    private void assertion(boolean bl, String string) throws RuntimeException {
        if (bl) {
            return;
        }
        throw new RuntimeException(string);
    }

    private void checkForFragmentID(Attributes attributes) {
        if (!this.m_shouldProcess && attributes != null && this.m_fragmentIDString != null) {
            int n = attributes.getLength();
            for (int i = 0; i < n; ++i) {
                if (!attributes.getQName(i).equals("id") || !attributes.getValue(i).equalsIgnoreCase(this.m_fragmentIDString)) continue;
                this.m_shouldProcess = true;
                this.m_fragmentID = this.m_elementID;
            }
        }
    }

    private void flushCharacters() throws SAXException {
        XSLTElementProcessor xSLTElementProcessor = this.getCurrentProcessor();
        if (xSLTElementProcessor != null) {
            xSLTElementProcessor.startNonText(this);
        }
    }

    private double getElemVersion() {
        double d;
        block3 : {
            double d2;
            ElemTemplateElement elemTemplateElement = this.getElemTemplateElement();
            d = -1.0;
            do {
                d2 = 1.0;
                if (d != -1.0 && d != 1.0 || elemTemplateElement == null) break;
                try {
                    d = Double.valueOf(elemTemplateElement.getXmlVersion());
                }
                catch (Exception exception) {
                    d = -1.0;
                }
                elemTemplateElement = elemTemplateElement.getParentElem();
            } while (true);
            if (d != -1.0) break block3;
            d = d2;
        }
        return d;
    }

    private boolean stackContains(Stack stack, String string) {
        boolean bl;
        int n = stack.size();
        boolean bl2 = false;
        int n2 = 0;
        do {
            bl = bl2;
            if (n2 >= n) break;
            if (((String)stack.elementAt(n2)).equals(string)) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        return bl;
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        if (!this.m_shouldProcess) {
            return;
        }
        XSLTElementProcessor xSLTElementProcessor = this.getCurrentProcessor();
        XSLTElementDef xSLTElementDef = xSLTElementProcessor.getElemDef();
        if (xSLTElementDef.getType() != 2) {
            xSLTElementProcessor = xSLTElementDef.getProcessorFor(null, "text()");
        }
        if (xSLTElementProcessor == null) {
            if (!XMLCharacterRecognizer.isWhiteSpace(arrc, n, n2)) {
                this.error(XSLMessages.createMessage("ER_NONWHITESPACE_NOT_ALLOWED_IN_POSITION", null), null);
            }
        } else {
            xSLTElementProcessor.characters(this, arrc, n, n2);
        }
    }

    XPath createMatchPatternXPath(String object, ElemTemplateElement elemTemplateElement) throws TransformerException {
        object = new XPath((String)object, elemTemplateElement, this, 1, this.m_stylesheetProcessor.getErrorListener(), this.m_funcTable);
        ((XPath)object).callVisitors((ExpressionOwner)object, new ExpressionVisitor(this.getStylesheetRoot()));
        return object;
    }

    public XPath createXPath(String object, ElemTemplateElement elemTemplateElement) throws TransformerException {
        object = new XPath((String)object, elemTemplateElement, this, 0, this.m_stylesheetProcessor.getErrorListener(), this.m_funcTable);
        ((XPath)object).callVisitors((ExpressionOwner)object, new ExpressionVisitor(this.getStylesheetRoot()));
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void endDocument() throws SAXException {
        try {
            XSLTElementProcessor xSLTElementProcessor;
            if (this.getStylesheetRoot() == null) {
                TransformerException transformerException = new TransformerException(XSLMessages.createMessage("ER_NO_STYLESHEETROOT", null));
                throw transformerException;
            }
            if (this.m_stylesheetLevel == 0) {
                this.getStylesheetRoot().recompose();
            }
            if ((xSLTElementProcessor = this.getCurrentProcessor()) != null) {
                xSLTElementProcessor.startNonText(this);
            }
            int n = this.m_stylesheetLevel;
            boolean bl = true;
            this.m_stylesheetLevel = n - 1;
            this.popSpaceHandling();
            if (this.m_stylesheetLevel >= 0) {
                bl = false;
            }
            this.m_parsingComplete = bl;
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        --this.m_elementID;
        if (!this.m_shouldProcess) {
            return;
        }
        if (this.m_elementID + 1 == this.m_fragmentID) {
            this.m_shouldProcess = false;
        }
        this.flushCharacters();
        this.popSpaceHandling();
        this.getCurrentProcessor().endElement(this, string, string2, string3);
        this.popProcessor();
        this.getNamespaceSupport().popContext();
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    protected void error(String object, Exception exception) throws SAXException {
        SAXSourceLocator sAXSourceLocator = this.getLocator();
        ErrorListener errorListener = this.m_stylesheetProcessor.getErrorListener();
        object = !(exception instanceof TransformerException) ? (exception == null ? new TransformerException((String)object, sAXSourceLocator) : new TransformerException((String)object, sAXSourceLocator, exception)) : (TransformerException)exception;
        if (errorListener != null) {
            try {
                errorListener.error((TransformerException)object);
                return;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        throw new SAXException((Exception)object);
    }

    protected void error(String string, Object[] arrobject, Exception exception) throws SAXException {
        this.error(XSLMessages.createMessage(string, arrobject), exception);
    }

    @Override
    public void error(SAXParseException serializable) throws SAXException {
        String string = serializable.getMessage();
        serializable = this.getLocator();
        ErrorListener errorListener = this.m_stylesheetProcessor.getErrorListener();
        try {
            TransformerException transformerException = new TransformerException(string, (SourceLocator)((Object)serializable));
            errorListener.error(transformerException);
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    @Override
    public void fatalError(SAXParseException object) throws SAXException {
        String string = ((SAXException)object).getMessage();
        SAXSourceLocator sAXSourceLocator = this.getLocator();
        object = this.m_stylesheetProcessor.getErrorListener();
        try {
            TransformerException transformerException = new TransformerException(string, sAXSourceLocator);
            object.fatalError(transformerException);
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    @Override
    public String getBaseIdentifier() {
        Object object = this.m_baseIdentifiers.isEmpty() ? null : (Object)this.m_baseIdentifiers.peek();
        String string = (String)object;
        object = string;
        if (string == null) {
            object = this.getLocator();
            object = object == null ? "" : object.getSystemId();
        }
        return object;
    }

    XSLTElementProcessor getCurrentProcessor() {
        return (XSLTElementProcessor)this.m_processors.peek();
    }

    ElemTemplateElement getElemTemplateElement() {
        try {
            ElemTemplateElement elemTemplateElement = (ElemTemplateElement)this.m_elems.peek();
            return elemTemplateElement;
        }
        catch (EmptyStackException emptyStackException) {
            return null;
        }
    }

    public boolean getIncremental() {
        return this.m_incremental;
    }

    Stylesheet getLastPoppedStylesheet() {
        return this.m_lastPoppedStylesheet;
    }

    public SAXSourceLocator getLocator() {
        if (this.m_stylesheetLocatorStack.isEmpty()) {
            SAXSourceLocator sAXSourceLocator = new SAXSourceLocator();
            sAXSourceLocator.setSystemId(this.getStylesheetProcessor().getDOMsystemID());
            return sAXSourceLocator;
        }
        return (SAXSourceLocator)this.m_stylesheetLocatorStack.peek();
    }

    @Override
    public String getNamespaceForPrefix(String string) {
        return this.getNamespaceSupport().getURI(string);
    }

    @Override
    public String getNamespaceForPrefix(String string, Node node) {
        this.assertion(true, "can't process a context node in StylesheetHandler!");
        return null;
    }

    NamespaceSupport getNamespaceSupport() {
        return (NamespaceSupport)this.m_nsSupportStack.peek();
    }

    public boolean getOptimize() {
        return this.m_optimize;
    }

    public Node getOriginatingNode() {
        return this.m_originatingNode;
    }

    XSLTElementProcessor getProcessorFor(String string, String string2, String string3) throws SAXException {
        XSLTElementProcessor xSLTElementProcessor;
        block4 : {
            XSLTElementDef xSLTElementDef;
            block5 : {
                XSLTElementProcessor xSLTElementProcessor2;
                XSLTElementProcessor xSLTElementProcessor3 = this.getCurrentProcessor();
                xSLTElementDef = xSLTElementProcessor3.getElemDef();
                xSLTElementProcessor = xSLTElementProcessor2 = xSLTElementDef.getProcessorFor(string, string2);
                if (xSLTElementProcessor2 != null) break block4;
                xSLTElementProcessor = xSLTElementProcessor2;
                if (xSLTElementProcessor3 instanceof ProcessorStylesheetDoc) break block4;
                if (this.getStylesheet() == null || Double.valueOf(this.getStylesheet().getVersion()) > 1.0 || !string.equals("http://www.w3.org/1999/XSL/Transform") && xSLTElementProcessor3 instanceof ProcessorStylesheetElement) break block5;
                xSLTElementProcessor = xSLTElementProcessor2;
                if (!(this.getElemVersion() > 1.0)) break block4;
            }
            xSLTElementProcessor = xSLTElementDef.getProcessorForUnknown(string, string2);
        }
        if (xSLTElementProcessor == null) {
            this.error(XSLMessages.createMessage("ER_NOT_ALLOWED_IN_POSITION", new Object[]{string3}), null);
        }
        return xSLTElementProcessor;
    }

    public XSLTSchema getSchema() {
        return this.m_schema;
    }

    public boolean getSource_location() {
        return this.m_source_location;
    }

    Stylesheet getStylesheet() {
        Stylesheet stylesheet = this.m_stylesheets.size() == 0 ? null : (Stylesheet)this.m_stylesheets.peek();
        return stylesheet;
    }

    public TransformerFactoryImpl getStylesheetProcessor() {
        return this.m_stylesheetProcessor;
    }

    public StylesheetRoot getStylesheetRoot() {
        StylesheetRoot stylesheetRoot = this.m_stylesheetRoot;
        if (stylesheetRoot != null) {
            stylesheetRoot.setOptimizer(this.m_optimize);
            this.m_stylesheetRoot.setIncremental(this.m_incremental);
            this.m_stylesheetRoot.setSource_location(this.m_source_location);
        }
        return this.m_stylesheetRoot;
    }

    int getStylesheetType() {
        return this.m_stylesheetType;
    }

    @Override
    public String getSystemId() {
        return this.getBaseIdentifier();
    }

    @Override
    public Templates getTemplates() {
        return this.getStylesheetRoot();
    }

    @Override
    public boolean handlesNullPrefixes() {
        return false;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        if (!this.m_shouldProcess) {
            return;
        }
        this.getCurrentProcessor().ignorableWhitespace(this, arrc, n, n2);
    }

    boolean importStackContains(String string) {
        return this.stackContains(this.m_importStack, string);
    }

    void init(TransformerFactoryImpl transformerFactoryImpl) {
        this.m_stylesheetProcessor = transformerFactoryImpl;
        this.m_processors.push(this.m_schema.getElementProcessor());
        this.pushNewNamespaceSupport();
    }

    boolean isSpacePreserve() {
        return this.m_spacePreserveStack.peek();
    }

    public boolean isStylesheetParsingComplete() {
        return this.m_parsingComplete;
    }

    int nextUid() {
        int n = this.m_docOrderCount;
        this.m_docOrderCount = n + 1;
        return n;
    }

    @Override
    public void notationDecl(String string, String string2, String string3) {
        this.getCurrentProcessor().notationDecl(this, string, string2, string3);
    }

    String peekImportURL() {
        return (String)this.m_importStack.peek();
    }

    Source peekSourceFromURIResolver() {
        return (Source)this.m_importSourceStack.peek();
    }

    String popBaseIndentifier() {
        return (String)this.m_baseIdentifiers.pop();
    }

    ElemTemplateElement popElemTemplateElement() {
        return (ElemTemplateElement)this.m_elems.pop();
    }

    Source popImportSource() {
        return (Source)this.m_importSourceStack.pop();
    }

    String popImportURL() {
        return (String)this.m_importStack.pop();
    }

    void popNamespaceSupport() {
        this.m_nsSupportStack.pop();
    }

    XSLTElementProcessor popProcessor() {
        return (XSLTElementProcessor)this.m_processors.pop();
    }

    void popSpaceHandling() {
        this.m_spacePreserveStack.pop();
    }

    Stylesheet popStylesheet() {
        if (!this.m_stylesheetLocatorStack.isEmpty()) {
            this.m_stylesheetLocatorStack.pop();
        }
        if (!this.m_stylesheets.isEmpty()) {
            this.m_lastPoppedStylesheet = (Stylesheet)this.m_stylesheets.pop();
        }
        return this.m_lastPoppedStylesheet;
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        block5 : {
            if (!this.m_shouldProcess) {
                return;
            }
            Object object = "";
            String string3 = string;
            int n = string.indexOf(58);
            if (n >= 0) {
                object = this.getNamespaceForPrefix(string.substring(0, n));
                string3 = string.substring(n + 1);
            }
            try {
                if (!"xalan-doc-cache-off".equals(string) && !"xalan:doc-cache-off".equals(string) && (!"doc-cache-off".equals(string3) || !((String)object).equals("org.apache.xalan.xslt.extensions.Redirect"))) break block5;
                if (this.m_elems.peek() instanceof ElemForEach) {
                    ((ElemForEach)this.m_elems.peek()).m_doc_cache_off = true;
                    break block5;
                }
                object = new TransformerException("xalan:doc-cache-off not allowed here!", this.getLocator());
                throw object;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        this.flushCharacters();
        this.getCurrentProcessor().processingInstruction(this, string, string2);
    }

    void pushBaseIndentifier(String string) {
        if (string != null) {
            int n = string.indexOf(35);
            if (n > -1) {
                this.m_fragmentIDString = string.substring(n + 1);
                this.m_shouldProcess = false;
            } else {
                this.m_shouldProcess = true;
            }
        } else {
            this.m_shouldProcess = true;
        }
        this.m_baseIdentifiers.push(string);
    }

    void pushElemTemplateElement(ElemTemplateElement elemTemplateElement) {
        if (elemTemplateElement.getUid() == -1) {
            elemTemplateElement.setUid(this.nextUid());
        }
        this.m_elems.push(elemTemplateElement);
    }

    void pushImportSource(Source source) {
        this.m_importSourceStack.push(source);
    }

    void pushImportURL(String string) {
        this.m_importStack.push(string);
    }

    void pushNewNamespaceSupport() {
        this.m_nsSupportStack.push(new NamespaceSupport2());
    }

    void pushProcessor(XSLTElementProcessor xSLTElementProcessor) {
        this.m_processors.push(xSLTElementProcessor);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void pushSpaceHandling(Attributes object) throws SAXParseException {
        if ((object = object.getValue("xml:space")) == null) {
            object = this.m_spacePreserveStack;
            ((BoolStack)object).push(((BoolStack)object).peekOrFalse());
            return;
        }
        if (((String)object).equals("preserve")) {
            this.m_spacePreserveStack.push(true);
            return;
        }
        if (((String)object).equals("default")) {
            this.m_spacePreserveStack.push(false);
            return;
        }
        object = this.getLocator();
        ErrorListener errorListener = this.m_stylesheetProcessor.getErrorListener();
        try {
            TransformerException transformerException = new TransformerException(XSLMessages.createMessage("ER_ILLEGAL_XMLSPACE_VALUE", null), (SourceLocator)object);
            errorListener.error(transformerException);
            object = this.m_spacePreserveStack;
            ((BoolStack)object).push(((BoolStack)object).peek());
        }
        catch (TransformerException transformerException) {
            throw new SAXParseException(transformerException.getMessage(), (Locator)object, transformerException);
        }
    }

    void pushSpaceHandling(boolean bl) throws SAXParseException {
        this.m_spacePreserveStack.push(bl);
    }

    public void pushStylesheet(Stylesheet stylesheet) {
        if (this.m_stylesheets.size() == 0) {
            this.m_stylesheetRoot = (StylesheetRoot)stylesheet;
        }
        this.m_stylesheets.push(stylesheet);
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException {
        return this.getCurrentProcessor().resolveEntity(this, string, string2);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        this.m_stylesheetLocatorStack.push(new SAXSourceLocator(locator));
    }

    @Override
    public void setOriginatingNode(Node node) {
        this.m_originatingNode = node;
    }

    void setStylesheetType(int n) {
        this.m_stylesheetType = n;
    }

    @Override
    public void setSystemId(String string) {
        this.pushBaseIndentifier(string);
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        if (!this.m_shouldProcess) {
            return;
        }
        this.getCurrentProcessor().skippedEntity(this, string);
    }

    @Override
    public void startDocument() throws SAXException {
        ++this.m_stylesheetLevel;
        this.pushSpaceHandling(false);
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        Serializable serializable;
        NamespaceSupport namespaceSupport = this.getNamespaceSupport();
        namespaceSupport.pushContext();
        int n = this.m_prefixMappings.size();
        int n2 = 0;
        while (n2 < n) {
            serializable = this.m_prefixMappings;
            int n3 = n2 + 1;
            namespaceSupport.declarePrefix((String)((Vector)serializable).elementAt(n2), (String)this.m_prefixMappings.elementAt(n3));
            n2 = n3 + 1;
        }
        this.m_prefixMappings.removeAllElements();
        ++this.m_elementID;
        this.checkForFragmentID(attributes);
        if (!this.m_shouldProcess) {
            return;
        }
        this.flushCharacters();
        this.pushSpaceHandling(attributes);
        serializable = this.getProcessorFor(string, string2, string3);
        if (serializable != null) {
            this.pushProcessor((XSLTElementProcessor)serializable);
            ((XSLTElementProcessor)serializable).startElement(this, string, string2, string3, attributes);
        } else {
            this.m_shouldProcess = false;
            this.popSpaceHandling();
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        this.m_prefixMappings.addElement(string);
        this.m_prefixMappings.addElement(string2);
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) {
        this.getCurrentProcessor().unparsedEntityDecl(this, string, string2, string3, string4);
    }

    public void warn(String object, Object[] object2) throws SAXException {
        object2 = XSLMessages.createWarning((String)object, object2);
        object = this.getLocator();
        ErrorListener errorListener = this.m_stylesheetProcessor.getErrorListener();
        if (errorListener != null) {
            try {
                TransformerException transformerException = new TransformerException((String)object2, (SourceLocator)object);
                errorListener.warning(transformerException);
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
    }

    @Override
    public void warning(SAXParseException object) throws SAXException {
        object = ((SAXException)object).getMessage();
        SAXSourceLocator sAXSourceLocator = this.getLocator();
        ErrorListener errorListener = this.m_stylesheetProcessor.getErrorListener();
        try {
            TransformerException transformerException = new TransformerException((String)object, sAXSourceLocator);
            errorListener.warning(transformerException);
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }
}

