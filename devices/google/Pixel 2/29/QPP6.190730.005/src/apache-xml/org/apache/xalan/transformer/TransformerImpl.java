/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import org.apache.xalan.extensions.ExpressionContext;
import org.apache.xalan.extensions.ExtensionsTable;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemAttributeSet;
import org.apache.xalan.templates.ElemForEach;
import org.apache.xalan.templates.ElemSort;
import org.apache.xalan.templates.ElemTemplate;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.ElemTextLiteral;
import org.apache.xalan.templates.ElemVariable;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.templates.StylesheetComposed;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.WhiteSpaceInfo;
import org.apache.xalan.templates.XUnresolvedVariable;
import org.apache.xalan.transformer.ClonerToResultTree;
import org.apache.xalan.transformer.CountersTable;
import org.apache.xalan.transformer.KeyManager;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.NodeSortKey;
import org.apache.xalan.transformer.TransformState;
import org.apache.xalan.transformer.TransformerClient;
import org.apache.xalan.transformer.TransformerHandlerImpl;
import org.apache.xalan.transformer.XalanTransformState;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToSAXHandler;
import org.apache.xml.serializer.ToTextStream;
import org.apache.xml.serializer.ToXMLSAXHandler;
import org.apache.xml.serializer.TransformStateSetter;
import org.apache.xml.utils.BoolStack;
import org.apache.xml.utils.DOMBuilder;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.ObjectPool;
import org.apache.xml.utils.ObjectStack;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.ThreadControllerWrapper;
import org.apache.xpath.Arg;
import org.apache.xpath.ExtensionsProvider;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.SourceTreeManager;
import org.apache.xpath.VariableStack;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FuncExtFunction;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.LexicalHandler;

public class TransformerImpl
extends Transformer
implements Runnable,
DTMWSFilter,
ExtensionsProvider,
SerializerTrace {
    Stack m_attrSetStack = null;
    CountersTable m_countersTable = null;
    ObjectStack m_currentFuncResult = new ObjectStack();
    Stack m_currentMatchTemplates = new Stack();
    NodeVector m_currentMatchedNodes = new NodeVector();
    ObjectStack m_currentTemplateElements = new ObjectStack(4096);
    BoolStack m_currentTemplateRuleIsNull = new BoolStack();
    private int m_doc;
    private ErrorListener m_errorHandler = new DefaultErrorHandler(false);
    private Exception m_exceptionThrown = null;
    private ExtensionsTable m_extensionsTable = null;
    private boolean m_hasBeenReset = false;
    private boolean m_hasTransformThreadErrorCatcher = false;
    private boolean m_incremental = false;
    ContentHandler m_inputContentHandler;
    private KeyManager m_keyManager = new KeyManager();
    private Stack m_modes = new Stack();
    private MsgMgr m_msgMgr;
    private boolean m_optimizer = true;
    private ContentHandler m_outputContentHandler = null;
    private OutputProperties m_outputFormat;
    private FileOutputStream m_outputStream = null;
    private Result m_outputTarget = null;
    private boolean m_quietConflictWarnings = true;
    private Boolean m_reentryGuard = new Boolean(true);
    private SerializationHandler m_serializationHandler;
    private boolean m_shouldReset = true;
    private boolean m_source_location = false;
    private ObjectPool m_stringWriterObjectPool = new ObjectPool(StringWriter.class);
    private StylesheetRoot m_stylesheetRoot = null;
    private ObjectPool m_textResultHandlerObjectPool = new ObjectPool(ToTextStream.class);
    private OutputProperties m_textformat = new OutputProperties("text");
    private Thread m_transformThread;
    private String m_urlOfSource = null;
    Vector m_userParams;
    private XPathContext m_xcontext;

    public TransformerImpl(StylesheetRoot stylesheetRoot) {
        this.m_optimizer = stylesheetRoot.getOptimizer();
        this.m_incremental = stylesheetRoot.getIncremental();
        this.m_source_location = stylesheetRoot.getSource_location();
        this.setStylesheet(stylesheetRoot);
        XPathContext xPathContext = new XPathContext(this);
        xPathContext.setIncremental(this.m_incremental);
        xPathContext.getDTMManager().setIncremental(this.m_incremental);
        xPathContext.setSource_location(this.m_source_location);
        xPathContext.getDTMManager().setSource_location(this.m_source_location);
        if (stylesheetRoot.isSecureProcessing()) {
            xPathContext.setSecureProcessing(true);
        }
        this.setXPathContext(xPathContext);
        this.getXPathContext().setNamespaceContext(stylesheetRoot);
    }

    private void fatalError(Throwable throwable) throws TransformerException {
        if (throwable instanceof SAXParseException) {
            this.m_errorHandler.fatalError(new TransformerException(throwable.getMessage(), new SAXSourceLocator((SAXParseException)throwable)));
        } else {
            this.m_errorHandler.fatalError(new TransformerException(throwable));
        }
    }

    private void replaceOrPushUserParam(QName qName, XObject xObject) {
        for (int i = this.m_userParams.size() - 1; i >= 0; --i) {
            if (!((Arg)this.m_userParams.elementAt(i)).getQName().equals(qName)) continue;
            this.m_userParams.setElementAt(new Arg(qName, xObject, true), i);
            return;
        }
        this.m_userParams.addElement(new Arg(qName, xObject, true));
    }

    private void resetUserParameters() {
        block4 : {
            if (this.m_userParams != null) break block4;
            return;
        }
        for (int i = this.m_userParams.size() - 1; i >= 0; --i) {
            try {
                Arg arg = (Arg)this.m_userParams.elementAt(i);
                QName qName = arg.getQName();
                String string = qName.getNamespace();
                this.setParameter(qName.getLocalPart(), string, arg.getVal().object());
                continue;
            }
            catch (NoSuchElementException noSuchElementException) {
                // empty catch block
                break;
            }
        }
    }

    public static void runTransformThread(Runnable runnable) {
        ThreadControllerWrapper.runThread(runnable, -1);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int transformToRTF(ElemTemplateElement serializable, DTM dTM) throws TransformerException {
        Throwable throwable3222;
        Object object;
        block8 : {
            object = this.m_xcontext;
            ContentHandler contentHandler = dTM.getContentHandler();
            object = this.m_serializationHandler;
            SerializationHandler serializationHandler = new ToXMLSAXHandler();
            ((ToSAXHandler)serializationHandler).setContentHandler(contentHandler);
            ((SerializerBase)serializationHandler).setTransformer(this);
            serializationHandler = this.m_serializationHandler = serializationHandler;
            serializationHandler.startDocument();
            serializationHandler.flushPending();
            this.executeChildTemplates((ElemTemplateElement)serializable, true);
            serializationHandler.flushPending();
            int n = dTM.getDocument();
            serializationHandler.endDocument();
            this.m_serializationHandler = object;
            return n;
            catch (Throwable throwable2) {
                try {
                    serializationHandler.endDocument();
                    throw throwable2;
                }
                catch (Throwable throwable3222) {
                    break block8;
                }
                catch (SAXException sAXException) {
                    serializable = new TransformerException(sAXException);
                    throw serializable;
                }
            }
        }
        this.m_serializationHandler = object;
        throw throwable3222;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean applyTemplateToNode(ElemTemplateElement var1_1, ElemTemplate var2_6, int var3_8) throws TransformerException {
        block30 : {
            block22 : {
                block26 : {
                    block27 : {
                        block28 : {
                            block29 : {
                                block24 : {
                                    block25 : {
                                        block23 : {
                                            block21 : {
                                                var4_9 = this.m_xcontext.getDTM(var3_8);
                                                var5_10 = var4_9.getNodeType(var3_8);
                                                var6_11 = 0;
                                                var7_12 = var1_1 == null ? false : var1_1.getXSLToken() == 72;
                                                if (var2_6 == null || var7_12) break block21;
                                                var1_1 = var2_6;
                                                var8_13 = var6_11;
                                                break block22;
                                            }
                                            if (var7_12) {
                                                var8_13 = var2_6.getStylesheetComposed().getImportCountComposed();
                                                var9_14 = var2_6.getStylesheetComposed().getEndImportCountComposed();
                                                --var8_13;
                                            } else {
                                                var9_14 = 0;
                                                var8_13 = -1;
                                            }
                                            if (!var7_12 || var8_13 != -1) break block23;
                                            var1_1 = null;
                                            break block24;
                                        }
                                        var2_6 = this.m_xcontext;
                                        var2_6.pushNamespaceContext((PrefixResolver)var1_1);
                                        var1_1 = this.getMode();
                                        if (!var7_12) ** GOTO lbl31
                                        var10_15 = this.m_stylesheetRoot;
                                        var11_16 = this.m_quietConflictWarnings;
                                        var1_1 = var10_15.getTemplateComposed((XPathContext)var2_6, var3_8, (QName)var1_1, var8_13, var9_14, var11_16, var4_9);
                                        break block25;
lbl31: // 1 sources:
                                        var1_1 = this.m_stylesheetRoot.getTemplateComposed((XPathContext)var2_6, var3_8, (QName)var1_1, this.m_quietConflictWarnings, var4_9);
                                    }
                                    var2_6.popNamespaceContext();
                                }
                                if (var1_1 != null) break block26;
                                if (var5_10 == 1) break block27;
                                if (var5_10 == 2 || var5_10 == 3 || var5_10 == 4) break block28;
                                if (var5_10 == 9) break block29;
                                if (var5_10 != 11) {
                                    return false;
                                }
                                break block27;
                            }
                            var1_1 = this.m_stylesheetRoot.getDefaultRootRule();
                            var8_13 = var6_11;
                            break block22;
                        }
                        var1_1 = this.m_stylesheetRoot.getDefaultTextRule();
                        var8_13 = 1;
                        break block22;
                    }
                    var1_1 = this.m_stylesheetRoot.getDefaultRule();
                    var8_13 = var6_11;
                    break block22;
                }
                var8_13 = var6_11;
            }
            this.pushElemTemplateElement((ElemTemplateElement)var1_1);
            this.m_xcontext.pushCurrentNode(var3_8);
            this.pushPairCurrentMatched((ElemTemplateElement)var1_1, var3_8);
            if (!var7_12) {
                var2_6 = new NodeSetDTM(var3_8, this.m_xcontext.getDTMManager());
                this.m_xcontext.pushContextNodeList((DTMIterator)var2_6);
            }
            if (var8_13 != 0) {
                if (var5_10 != 2) {
                    if (var5_10 == 3 || var5_10 == 4) {
                        ClonerToResultTree.cloneToResultTree(var3_8, var5_10, var4_9, this.getResultTreeHandler(), false);
                    }
                } else {
                    var4_9.dispatchCharactersEvents(var3_8, this.getResultTreeHandler(), false);
                }
            } else {
                this.m_xcontext.setSAXLocator((SourceLocator)var1_1);
                this.m_xcontext.getVarStack().link(var1_1.m_frameSize);
                this.executeChildTemplates((ElemTemplateElement)var1_1, true);
            }
            if (var8_13 == 0) {
                this.m_xcontext.getVarStack().unlink();
            }
            this.m_xcontext.popCurrentNode();
            if (!var7_12) {
                this.m_xcontext.popContextNodeList();
            }
            this.popCurrentMatched();
            this.popElemTemplateElement();
            return true;
            catch (Throwable var1_3) {}
            break block30;
            catch (Throwable var1_4) {
                // empty catch block
            }
        }
        var2_6.popNamespaceContext();
        throw var1_5;
        {
            catch (Throwable var1_2) {
            }
            catch (SAXException var2_7) {}
            {
                var1_1 = new TransformerException(var2_7);
                throw var1_1;
            }
        }
        if (var8_13 == 0) {
            this.m_xcontext.getVarStack().unlink();
        }
        this.m_xcontext.popCurrentNode();
        if (!var7_12) {
            this.m_xcontext.popContextNodeList();
        }
        this.popCurrentMatched();
        this.popElemTemplateElement();
        throw var1_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void clearParameters() {
        Boolean bl = this.m_reentryGuard;
        synchronized (bl) {
            VariableStack variableStack = new VariableStack();
            this.m_xcontext.setVarStack(variableStack);
            this.m_userParams = null;
            return;
        }
    }

    public SerializationHandler createSerializationHandler(Result result) throws TransformerException {
        return this.createSerializationHandler(result, this.getOutputFormat());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SerializationHandler createSerializationHandler(Result object, OutputProperties object2) throws TransformerException {
        block22 : {
            if (object instanceof DOMResult) {
                short s;
                Node node = ((DOMResult)object).getNode();
                Node node2 = ((DOMResult)object).getNextSibling();
                if (node != null) {
                    s = node.getNodeType();
                    object = 9 == s ? (Document)node : node.getOwnerDocument();
                } else {
                    Document document = DOMHelper.createDocument(this.m_stylesheetRoot.isSecureProcessing());
                    node = document;
                    s = node.getNodeType();
                    ((DOMResult)object).setNode(node);
                    object = document;
                }
                object = 11 == s ? new DOMBuilder((Document)object, (DocumentFragment)node) : new DOMBuilder((Document)object, node);
                if (node2 != null) {
                    ((DOMBuilder)object).setNextSibling(node2);
                }
                object = new ToXMLSAXHandler((ContentHandler)object, (LexicalHandler)object, ((OutputProperties)object2).getProperty("encoding"));
            } else if (object instanceof SAXResult) {
                ContentHandler contentHandler = ((SAXResult)object).getHandler();
                if (contentHandler == null) {
                    throw new IllegalArgumentException("handler can not be null for a SAXResult");
                }
                object = contentHandler instanceof LexicalHandler ? (LexicalHandler)((Object)contentHandler) : null;
                String string = ((OutputProperties)object2).getProperty("encoding");
                ((OutputProperties)object2).getProperty("method");
                object = new ToXMLSAXHandler(contentHandler, (LexicalHandler)object, string);
                ((ToSAXHandler)object).setShouldOutputNSAttr(false);
                string = ((OutputProperties)object2).getProperty("doctype-public");
                object2 = ((OutputProperties)object2).getProperty("doctype-system");
                if (object2 != null) {
                    object.setDoctypeSystem((String)object2);
                }
                if (string != null) {
                    object.setDoctypePublic(string);
                }
                if (contentHandler instanceof TransformerClient) {
                    object2 = new XalanTransformState();
                    ((TransformerClient)((Object)contentHandler)).setTransformState((TransformState)object2);
                    ((ToSAXHandler)object).setTransformState((TransformStateSetter)object2);
                }
            } else {
                if (!(object instanceof StreamResult)) {
                    throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", new Object[]{object.getClass().getName()}));
                }
                object = (StreamResult)object;
                object2 = (SerializationHandler)SerializerFactory.getSerializer(((OutputProperties)object2).getProperties());
                if (((StreamResult)object).getWriter() != null) {
                    object2.setWriter(((StreamResult)object).getWriter());
                } else if (((StreamResult)object).getOutputStream() != null) {
                    object2.setOutputStream(((StreamResult)object).getOutputStream());
                } else {
                    if (((StreamResult)object).getSystemId() == null) break block22;
                    Object object3 = ((StreamResult)object).getSystemId();
                    boolean bl = ((String)object3).startsWith("file:///");
                    if (bl) {
                        object = ((String)object3).substring(8).indexOf(":") > 0 ? ((String)object3).substring(8) : ((String)object3).substring(7);
                    } else {
                        object = object3;
                        if (((String)object3).startsWith("file:/")) {
                            object = ((String)object3).substring(6).indexOf(":") > 0 ? ((String)object3).substring(6) : ((String)object3).substring(5);
                        }
                    }
                    this.m_outputStream = object3 = new FileOutputStream((String)object);
                    object2.setOutputStream(this.m_outputStream);
                }
                object = object2;
            }
            object.setTransformer(this);
            object.setSourceLocator(this.getStylesheet());
            return object;
        }
        try {
            object = new TransformerException(XSLMessages.createMessage("ER_NO_OUTPUT_SPECIFIED", null));
            throw object;
        }
        catch (IOException iOException) {
            throw new TransformerException(iOException);
        }
    }

    public boolean currentFuncResultSeen() {
        boolean bl = !this.m_currentFuncResult.empty() && this.m_currentFuncResult.peek() != null;
        return bl;
    }

    public boolean currentTemplateRuleIsNull() {
        boolean bl = this.m_currentTemplateRuleIsNull.isEmpty();
        boolean bl2 = true;
        if (bl || !this.m_currentTemplateRuleIsNull.peek()) {
            bl2 = false;
        }
        return bl2;
    }

    @Override
    public boolean elementAvailable(String string, String string2) throws TransformerException {
        return this.getExtensionsTable().elementAvailable(string, string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void executeChildTemplates(ElemTemplateElement var1_1, Node var2_3, QName var3_4, ContentHandler var4_5) throws TransformerException {
        var5_6 = this.m_xcontext;
        if (var3_4 == null) ** GOTO lbl5
        try {
            this.pushMode(var3_4);
lbl5: // 2 sources:
            var5_6.pushCurrentNode(var5_6.getDTMHandleFromNode(var2_3));
            this.executeChildTemplates(var1_1, var4_5);
            return;
        }
        finally {
            var5_6.popCurrentNode();
            if (var3_4 != null) {
                this.popMode();
            }
        }
    }

    /*
     * Exception decompiling
     */
    public void executeChildTemplates(ElemTemplateElement var1_1, ContentHandler var2_4) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void executeChildTemplates(ElemTemplateElement var1_1, boolean var2_4) throws TransformerException {
        var3_5 = var1_1.getFirstChildElem();
        if (var3_5 == null) {
            return;
        }
        if (var1_1.hasTextLitOnly() && this.m_optimizer) {
            var1_1 = ((ElemTextLiteral)var3_5).getChars();
            this.pushElemTemplateElement(var3_5);
            this.m_serializationHandler.characters((char[])var1_1, 0, ((Object)var1_1).length);
            this.popElemTemplateElement();
            return;
            {
                catch (Throwable var1_2) {
                }
                catch (SAXException var3_6) {}
                {
                    var1_1 = new TransformerException(var3_6);
                    throw var1_1;
                }
            }
            this.popElemTemplateElement();
            throw var1_2;
        }
        var4_8 = this.m_xcontext;
        var4_8.pushSAXLocatorNull();
        var5_9 = this.m_currentTemplateElements.size();
        this.m_currentTemplateElements.push(null);
        var1_1 = var3_5;
        do {
            if (var1_1 == null) {
                this.m_currentTemplateElements.pop();
                var4_8.popSAXLocator();
                return;
            }
            if (var2_4) ** GOTO lbl34
            try {
                block16 : {
                    if (var1_1.getXSLToken() == 48) break block16;
lbl34: // 2 sources:
                    var4_8.setSAXLocator((SourceLocator)var1_1);
                    this.m_currentTemplateElements.setElementAt(var1_1, var5_9);
                    var1_1.execute(this);
                }
                var3_5 = var1_1.getNextSiblingElem();
                var1_1 = var3_5;
                continue;
            }
            catch (Throwable var1_3) {
                break;
            }
            catch (RuntimeException var3_7) {
                var6_10 = new TransformerException(var3_7);
                var6_10.setLocator((SourceLocator)var1_1);
                throw var6_10;
            }
        } while (true);
        this.m_currentTemplateElements.pop();
        var4_8.popSAXLocator();
        throw var1_3;
    }

    @Override
    public Object extFunction(String string, String string2, Vector vector, Object object) throws TransformerException {
        return this.getExtensionsTable().extFunction(string, string2, vector, object, this.getXPathContext().getExpressionContext());
    }

    @Override
    public Object extFunction(FuncExtFunction funcExtFunction, Vector vector) throws TransformerException {
        return this.getExtensionsTable().extFunction(funcExtFunction, vector, this.getXPathContext().getExpressionContext());
    }

    @Override
    public void fireGenerateEvent(int n) {
    }

    @Override
    public void fireGenerateEvent(int n, String string) {
    }

    @Override
    public void fireGenerateEvent(int n, String string, String string2) {
    }

    @Override
    public void fireGenerateEvent(int n, String string, Attributes attributes) {
    }

    @Override
    public void fireGenerateEvent(int n, char[] arrc, int n2, int n3) {
    }

    @Override
    public boolean functionAvailable(String string, String string2) throws TransformerException {
        return this.getExtensionsTable().functionAvailable(string, string2);
    }

    public ContentHandler getContentHandler() {
        return this.m_outputContentHandler;
    }

    public DTMIterator getContextNodeList() {
        DTMIterator dTMIterator;
        block3 : {
            DTMIterator dTMIterator2;
            dTMIterator = null;
            try {
                dTMIterator2 = this.m_xcontext.getContextNodeList();
                if (dTMIterator2 == null) break block3;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
            dTMIterator = dTMIterator2.cloneWithReset();
        }
        return dTMIterator;
    }

    public CountersTable getCountersTable() {
        if (this.m_countersTable == null) {
            this.m_countersTable = new CountersTable();
        }
        return this.m_countersTable;
    }

    public ElemTemplateElement getCurrentElement() {
        ElemTemplateElement elemTemplateElement = this.m_currentTemplateElements.size() > 0 ? (ElemTemplateElement)this.m_currentTemplateElements.peek() : null;
        return elemTemplateElement;
    }

    public int getCurrentNode() {
        return this.m_xcontext.getCurrentNode();
    }

    public ElemTemplate getCurrentTemplate() {
        ElemTemplateElement elemTemplateElement;
        for (elemTemplateElement = this.getCurrentElement(); elemTemplateElement != null && elemTemplateElement.getXSLToken() != 19; elemTemplateElement = elemTemplateElement.getParentElem()) {
        }
        return (ElemTemplate)elemTemplateElement;
    }

    public ObjectStack getCurrentTemplateElements() {
        return this.m_currentTemplateElements;
    }

    public int getCurrentTemplateElementsCount() {
        return this.m_currentTemplateElements.size();
    }

    @Override
    public ErrorListener getErrorListener() {
        return this.m_errorHandler;
    }

    public Exception getExceptionThrown() {
        return this.m_exceptionThrown;
    }

    public ExtensionsTable getExtensionsTable() {
        return this.m_extensionsTable;
    }

    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        if ("http://xml.org/trax/features/sax/input".equals(string)) {
            return true;
        }
        if ("http://xml.org/trax/features/dom/input".equals(string)) {
            return true;
        }
        throw new SAXNotRecognizedException(string);
    }

    public boolean getIncremental() {
        return this.m_incremental;
    }

    public ContentHandler getInputContentHandler() {
        return this.getInputContentHandler(false);
    }

    public ContentHandler getInputContentHandler(boolean bl) {
        if (this.m_inputContentHandler == null) {
            this.m_inputContentHandler = new TransformerHandlerImpl(this, bl, this.m_urlOfSource);
        }
        return this.m_inputContentHandler;
    }

    public KeyManager getKeyManager() {
        return this.m_keyManager;
    }

    public int getMatchedNode() {
        return this.m_currentMatchedNodes.peepTail();
    }

    public ElemTemplate getMatchedTemplate() {
        return (ElemTemplate)this.m_currentMatchTemplates.peek();
    }

    public QName getMode() {
        QName qName = this.m_modes.isEmpty() ? null : (QName)this.m_modes.peek();
        return qName;
    }

    public MsgMgr getMsgMgr() {
        if (this.m_msgMgr == null) {
            this.m_msgMgr = new MsgMgr(this);
        }
        return this.m_msgMgr;
    }

    public boolean getOptimize() {
        return this.m_optimizer;
    }

    public OutputProperties getOutputFormat() {
        OutputProperties outputProperties;
        block0 : {
            outputProperties = this.m_outputFormat;
            if (outputProperties != null) break block0;
            outputProperties = this.getStylesheet().getOutputComposed();
        }
        return outputProperties;
    }

    @Override
    public Properties getOutputProperties() {
        return (Properties)this.getOutputFormat().getProperties().clone();
    }

    @Override
    public String getOutputProperty(String string) throws IllegalArgumentException {
        String string2 = this.getOutputFormat().getProperty(string);
        if (string2 == null && !OutputProperties.isLegalPropertyKey(string)) {
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
        }
        return string2;
    }

    public String getOutputPropertyNoDefault(String string) throws IllegalArgumentException {
        String string2 = (String)this.getOutputFormat().getProperties().get(string);
        if (string2 == null && !OutputProperties.isLegalPropertyKey(string)) {
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
        }
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object getParameter(String object) {
        int n;
        QName qName;
        block6 : {
            qName = QName.getQNameFromString((String)object);
            if (this.m_userParams != null) break block6;
            return null;
        }
        try {
            n = this.m_userParams.size() - 1;
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
        while (n >= 0) {
            object = (Arg)this.m_userParams.elementAt(n);
            if (((Arg)object).getQName().equals(qName)) {
                return ((Arg)object).getVal().object();
            }
            --n;
        }
        return null;
    }

    public boolean getQuietConflictWarnings() {
        return this.m_quietConflictWarnings;
    }

    public SerializationHandler getResultTreeHandler() {
        return this.m_serializationHandler;
    }

    public SerializationHandler getSerializationHandler() {
        return this.m_serializationHandler;
    }

    @Override
    public short getShouldStripSpace(int n, DTM object) {
        short s;
        block5 : {
            try {
                object = this.m_stylesheetRoot.getWhiteSpaceInfo(this.m_xcontext, n, (DTM)object);
                if (object != null) break block5;
                return 3;
            }
            catch (TransformerException transformerException) {
                return 3;
            }
        }
        boolean bl = ((WhiteSpaceInfo)object).getShouldStripSpace();
        if (bl) {
            n = 2;
            s = n;
        } else {
            n = 1;
            s = n;
        }
        return s;
    }

    public boolean getSource_location() {
        return this.m_source_location;
    }

    public final StylesheetRoot getStylesheet() {
        return this.m_stylesheetRoot;
    }

    public Thread getTransformThread() {
        return this.m_transformThread;
    }

    public Transformer getTransformer() {
        return this;
    }

    @Override
    public URIResolver getURIResolver() {
        return this.m_xcontext.getSourceTreeManager().getURIResolver();
    }

    public final XPathContext getXPathContext() {
        return this.m_xcontext;
    }

    @Override
    public boolean hasTraceListeners() {
        return false;
    }

    public boolean hasTransformThreadErrorCatcher() {
        return this.m_hasTransformThreadErrorCatcher;
    }

    public void init(ToXMLSAXHandler toXMLSAXHandler, Transformer transformer, ContentHandler contentHandler) {
        toXMLSAXHandler.setTransformer(transformer);
        toXMLSAXHandler.setContentHandler(contentHandler);
    }

    public boolean isRecursiveAttrSet(ElemAttributeSet elemAttributeSet) {
        if (this.m_attrSetStack == null) {
            this.m_attrSetStack = new Stack();
        }
        return !this.m_attrSetStack.empty() && this.m_attrSetStack.search(elemAttributeSet) > -1;
    }

    public Object popCurrentFuncResult() {
        return this.m_currentFuncResult.pop();
    }

    public void popCurrentMatched() {
        this.m_currentMatchTemplates.pop();
        this.m_currentMatchedNodes.pop();
    }

    public void popCurrentTemplateRuleIsNull() {
        this.m_currentTemplateRuleIsNull.pop();
    }

    public void popElemAttributeSet() {
        this.m_attrSetStack.pop();
    }

    public void popElemTemplateElement() {
        this.m_currentTemplateElements.pop();
    }

    public void popMode() {
        this.m_modes.pop();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void postExceptionFromThread(Exception exception) {
        this.m_exceptionThrown = exception;
        synchronized (this) {
            this.notifyAll();
            return;
        }
    }

    public Vector processSortKeys(ElemForEach elemForEach, int n) throws TransformerException {
        XPathContext xPathContext = this.m_xcontext;
        int n2 = elemForEach.getSortElemCount();
        Vector<NodeSortKey> vector = n2 > 0 ? new Vector<NodeSortKey>() : null;
        int n3 = 0;
        do {
            ElemForEach elemForEach2 = elemForEach;
            if (n3 >= n2) break;
            ElemSort elemSort = elemForEach2.getSortElem(n3);
            String string = elemSort.getLang() != null ? elemSort.getLang().evaluate(xPathContext, n, elemForEach2) : null;
            Object object = elemSort.getDataType().evaluate(xPathContext, n, elemForEach2);
            int n4 = ((String)object).indexOf(":");
            boolean bl = false;
            if (n4 >= 0) {
                System.out.println("TODO: Need to write the hooks for QNAME sort data type");
            } else if (!((String)object).equalsIgnoreCase("text") && !((String)object).equalsIgnoreCase("number")) {
                elemForEach2.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"data-type", object});
            }
            boolean bl2 = ((String)object).equals("number");
            object = elemSort.getOrder().evaluate(xPathContext, n, elemForEach2);
            if (!((String)object).equalsIgnoreCase("ascending") && !((String)object).equalsIgnoreCase("descending")) {
                elemForEach2.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"order", object});
            }
            boolean bl3 = ((String)object).equals("descending");
            object = elemSort.getCaseOrder();
            if (object != null) {
                if (!((String)(object = ((AVT)object).evaluate(xPathContext, n, elemForEach2))).equalsIgnoreCase("upper-first") && !((String)object).equalsIgnoreCase("lower-first")) {
                    elemForEach2.error("ER_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"case-order", object});
                }
                if (((String)object).equals("upper-first")) {
                    bl = true;
                }
            } else {
                bl = false;
            }
            vector.addElement(new NodeSortKey(this, elemSort.getSelect(), bl2, bl3, string, bl, elemForEach));
            ++n3;
        } while (true);
        return vector;
    }

    public void pushCurrentFuncResult(Object object) {
        this.m_currentFuncResult.push(object);
    }

    public void pushCurrentTemplateRuleIsNull(boolean bl) {
        this.m_currentTemplateRuleIsNull.push(bl);
    }

    public void pushElemAttributeSet(ElemAttributeSet elemAttributeSet) {
        this.m_attrSetStack.push(elemAttributeSet);
    }

    public void pushElemTemplateElement(ElemTemplateElement elemTemplateElement) {
        this.m_currentTemplateElements.push(elemTemplateElement);
    }

    protected void pushGlobalVars(int n) throws TransformerException {
        VariableStack variableStack = this.m_xcontext.getVarStack();
        Vector vector = this.getStylesheet().getVariablesAndParamsComposed();
        int n2 = vector.size();
        variableStack.link(n2);
        while (--n2 >= 0) {
            XUnresolvedVariable xUnresolvedVariable = new XUnresolvedVariable((ElemVariable)vector.elementAt(n2), n, this, variableStack.getStackFrame(), 0, true);
            if (variableStack.elementAt(n2) != null) continue;
            variableStack.setGlobalVariable(n2, xUnresolvedVariable);
        }
    }

    public void pushMode(QName qName) {
        this.m_modes.push(qName);
    }

    public void pushPairCurrentMatched(ElemTemplateElement elemTemplateElement, int n) {
        this.m_currentMatchTemplates.push(elemTemplateElement);
        this.m_currentMatchedNodes.push(n);
    }

    @Override
    public void reset() {
        if (!this.m_hasBeenReset && this.m_shouldReset) {
            this.m_hasBeenReset = true;
            FileOutputStream fileOutputStream = this.m_outputStream;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            this.m_outputStream = null;
            this.m_countersTable = null;
            this.m_xcontext.reset();
            this.m_xcontext.getVarStack().reset();
            this.resetUserParameters();
            this.m_currentTemplateElements.removeAllElements();
            this.m_currentMatchTemplates.removeAllElements();
            this.m_currentMatchedNodes.removeAllElements();
            this.m_serializationHandler = null;
            this.m_outputTarget = null;
            this.m_keyManager = new KeyManager();
            this.m_attrSetStack = null;
            this.m_countersTable = null;
            this.m_currentTemplateRuleIsNull = new BoolStack();
            this.m_doc = -1;
            this.m_transformThread = null;
            this.m_xcontext.getSourceTreeManager().reset();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        this.m_hasBeenReset = false;
        this.transformNode(this.m_doc);
        if (this.m_inputContentHandler instanceof TransformerHandlerImpl == false) return;
        var1_1 = (TransformerHandlerImpl)this.m_inputContentHandler;
lbl7: // 2 sources:
        do {
            var1_1.clearCoRoutine();
            return;
            break;
        } while (true);
        {
            block10 : {
                catch (Throwable var1_2) {
                    break block10;
                }
                catch (Exception var1_3) {}
                {
                    if (this.m_transformThread == null) ** GOTO lbl-1000
                    this.postExceptionFromThread(var1_3);
                }
                if (this.m_inputContentHandler instanceof TransformerHandlerImpl == false) return;
                var1_1 = (TransformerHandlerImpl)this.m_inputContentHandler;
                ** continue;
lbl-1000: // 1 sources:
                {
                    var2_6 = new RuntimeException(var1_3.getMessage());
                    throw var2_6;
                }
            }
            try {
                if (this.m_inputContentHandler instanceof TransformerHandlerImpl == false) throw var1_2;
                ((TransformerHandlerImpl)this.m_inputContentHandler).clearCoRoutine();
                throw var1_2;
            }
            catch (Exception var1_4) {
                if (this.m_transformThread == null) throw new RuntimeException(var1_4.getMessage());
                this.postExceptionFromThread(var1_4);
            }
        }
    }

    public void runTransformThread() {
        ThreadControllerWrapper.runThread(this, -1);
    }

    public void runTransformThread(int n) {
        this.setTransformThread(ThreadControllerWrapper.runThread(this, n));
    }

    public void setBaseURLOfSource(String string) {
        this.m_urlOfSource = string;
    }

    public void setContentHandler(ContentHandler contentHandler) {
        if (contentHandler != null) {
            this.m_outputContentHandler = contentHandler;
            SerializationHandler serializationHandler = this.m_serializationHandler;
            if (serializationHandler == null) {
                serializationHandler = new ToXMLSAXHandler();
                ((ToSAXHandler)serializationHandler).setContentHandler(contentHandler);
                ((SerializerBase)serializationHandler).setTransformer(this);
                this.m_serializationHandler = serializationHandler;
            } else {
                serializationHandler.setContentHandler(contentHandler);
            }
            return;
        }
        throw new NullPointerException(XSLMessages.createMessage("ER_NULL_CONTENT_HANDLER", null));
    }

    public void setCurrentElement(ElemTemplateElement elemTemplateElement) {
        this.m_currentTemplateElements.setTop(elemTemplateElement);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setErrorListener(ErrorListener object) throws IllegalArgumentException {
        Boolean bl = this.m_reentryGuard;
        synchronized (bl) {
            Throwable throwable2;
            if (object != null) {
                try {
                    this.m_errorHandler = object;
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new IllegalArgumentException(XSLMessages.createMessage("ER_NULL_ERROR_HANDLER", null));
                throw object;
            }
            throw throwable2;
        }
    }

    public void setExceptionThrown(Exception exception) {
        this.m_exceptionThrown = exception;
    }

    void setExtensionsTable(StylesheetRoot stylesheetRoot) throws TransformerException {
        try {
            if (stylesheetRoot.getExtensions() != null && !stylesheetRoot.isSecureProcessing()) {
                ExtensionsTable extensionsTable;
                this.m_extensionsTable = extensionsTable = new ExtensionsTable(stylesheetRoot);
            }
        }
        catch (TransformerException transformerException) {
            transformerException.printStackTrace();
        }
    }

    public void setOutputFormat(OutputProperties outputProperties) {
        this.m_outputFormat = outputProperties;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setOutputProperties(Properties properties) throws IllegalArgumentException {
        Boolean bl = this.m_reentryGuard;
        synchronized (bl) {
            if (properties != null) {
                String string = (String)properties.get("method");
                if (string != null) {
                    OutputProperties outputProperties;
                    this.m_outputFormat = outputProperties = new OutputProperties(string);
                } else if (this.m_outputFormat == null) {
                    OutputProperties outputProperties;
                    this.m_outputFormat = outputProperties = new OutputProperties();
                }
                this.m_outputFormat.copyFrom(properties);
                this.m_outputFormat.copyFrom(this.m_stylesheetRoot.getOutputProperties());
            } else {
                this.m_outputFormat = null;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setOutputProperty(String string, String object) throws IllegalArgumentException {
        Boolean bl = this.m_reentryGuard;
        synchronized (bl) {
            if (this.m_outputFormat == null) {
                this.m_outputFormat = (OutputProperties)this.getStylesheet().getOutputComposed().clone();
            }
            if (OutputProperties.isLegalPropertyKey(string)) {
                this.m_outputFormat.setProperty(string, (String)object);
                return;
            }
            object = new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
            throw object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setParameter(String object, Object object2) {
        if (object2 == null) {
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_SET_PARAM_VALUE", new Object[]{object}));
        }
        object = new StringTokenizer((String)object, "{}", false);
        try {
            String string = ((StringTokenizer)object).nextToken();
            object = ((StringTokenizer)object).hasMoreTokens() ? ((StringTokenizer)object).nextToken() : null;
            if (this.m_userParams == null) {
                Vector vector;
                this.m_userParams = vector = new Vector();
            }
            if (object == null) {
                object = new QName(string);
                this.replaceOrPushUserParam((QName)object, XObject.create(object2, this.getXPathContext()));
                this.setParameter(string, null, object2);
                return;
            }
            QName qName = new QName(string, (String)object);
            this.replaceOrPushUserParam(qName, XObject.create(object2, this.getXPathContext()));
            this.setParameter((String)object, string, object2);
            return;
        }
        catch (NoSuchElementException noSuchElementException) {
            // empty catch block
        }
    }

    public void setParameter(String object, String object2, Object object3) {
        VariableStack variableStack = this.getXPathContext().getVarStack();
        object = new QName((String)object2, (String)object);
        object3 = XObject.create(object3, this.getXPathContext());
        object2 = this.m_stylesheetRoot.getVariablesAndParamsComposed();
        int n = ((Vector)object2).size();
        while (--n >= 0) {
            ElemVariable elemVariable = (ElemVariable)((Vector)object2).elementAt(n);
            if (elemVariable.getXSLToken() != 41 || !elemVariable.getName().equals(object)) continue;
            variableStack.setGlobalVariable(n, (XObject)object3);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setParameters(Properties properties) {
        this.clearParameters();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string = properties.getProperty((String)enumeration.nextElement());
            Object object = new StringTokenizer(string, "{}", false);
            try {
                String string2 = ((StringTokenizer)object).nextToken();
                object = ((StringTokenizer)object).hasMoreTokens() ? ((StringTokenizer)object).nextToken() : null;
                if (object == null) {
                    this.setParameter(string2, null, properties.getProperty(string));
                    continue;
                }
                this.setParameter((String)object, string2, properties.getProperty(string));
            }
            catch (NoSuchElementException noSuchElementException) {
            }
        }
        return;
    }

    public void setSerializationHandler(SerializationHandler serializationHandler) {
        this.m_serializationHandler = serializationHandler;
    }

    public void setSourceTreeDocForThread(int n) {
        this.m_doc = n;
    }

    public void setStylesheet(StylesheetRoot stylesheetRoot) {
        this.m_stylesheetRoot = stylesheetRoot;
    }

    public void setTransformThread(Thread thread) {
        this.m_transformThread = thread;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setURIResolver(URIResolver uRIResolver) {
        Boolean bl = this.m_reentryGuard;
        synchronized (bl) {
            this.m_xcontext.getSourceTreeManager().setURIResolver(uRIResolver);
            return;
        }
    }

    public void setXPathContext(XPathContext xPathContext) {
        this.m_xcontext = xPathContext;
    }

    public void transform(Source source) throws TransformerException {
        this.transform(source, true);
    }

    @Override
    public void transform(Source source, Result result) throws TransformerException {
        this.transform(source, result, true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void transform(Source source, Result result, boolean bl) throws TransformerException {
        Boolean bl2 = this.m_reentryGuard;
        synchronized (bl2) {
            this.setSerializationHandler(this.createSerializationHandler(result));
            this.m_outputTarget = result;
            this.transform(source, bl);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    public void transform(Source var1_1, boolean var2_7) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Exception decompiling
     */
    public void transformNode(int var1_1) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 13[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void transformNode(int n, Result result) throws TransformerException {
        this.setSerializationHandler(this.createSerializationHandler(result));
        this.m_outputTarget = result;
        this.transformNode(n);
    }

    public int transformToGlobalRTF(ElemTemplateElement elemTemplateElement) throws TransformerException {
        return this.transformToRTF(elemTemplateElement, this.m_xcontext.getGlobalRTFDTM());
    }

    public int transformToRTF(ElemTemplateElement elemTemplateElement) throws TransformerException {
        return this.transformToRTF(elemTemplateElement, this.m_xcontext.getRTFDTM());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String transformToString(ElemTemplateElement serializable) throws TransformerException {
        Throwable throwable2222;
        Object object = ((ElemTemplateElement)serializable).getFirstChildElem();
        if (object == null) {
            return "";
        }
        if (((ElemTemplateElement)serializable).hasTextLitOnly() && this.m_optimizer) {
            return ((ElemTextLiteral)object).getNodeValue();
        }
        SerializationHandler serializationHandler = this.m_serializationHandler;
        object = (StringWriter)this.m_stringWriterObjectPool.getInstance();
        this.m_serializationHandler = (ToTextStream)this.m_textResultHandlerObjectPool.getInstance();
        if (this.m_serializationHandler == null) {
            this.m_serializationHandler = (SerializationHandler)SerializerFactory.getSerializer(this.m_textformat.getProperties());
        }
        this.m_serializationHandler.setTransformer(this);
        this.m_serializationHandler.setWriter((Writer)object);
        this.executeChildTemplates((ElemTemplateElement)serializable, true);
        this.m_serializationHandler.endDocument();
        String string = ((StringWriter)object).toString();
        ((StringWriter)object).getBuffer().setLength(0);
        try {
            ((StringWriter)object).close();
        }
        catch (Exception exception) {}
        this.m_stringWriterObjectPool.freeInstance(object);
        this.m_serializationHandler.reset();
        this.m_textResultHandlerObjectPool.freeInstance(this.m_serializationHandler);
        this.m_serializationHandler = serializationHandler;
        return string;
        {
            catch (Throwable throwable2222) {
            }
            catch (SAXException sAXException) {}
            {
                serializable = new TransformerException(sAXException);
                throw serializable;
            }
        }
        ((StringWriter)object).getBuffer().setLength(0);
        try {
            ((StringWriter)object).close();
        }
        catch (Exception exception) {}
        this.m_stringWriterObjectPool.freeInstance(object);
        this.m_serializationHandler.reset();
        this.m_textResultHandlerObjectPool.freeInstance(this.m_serializationHandler);
        this.m_serializationHandler = serializationHandler;
        throw throwable2222;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void waitTransformThread() throws SAXException {
        Object object = this.getTransformThread();
        if (object == null) return;
        try {
            ThreadControllerWrapper.waitThread((Thread)object, this);
            if (!this.hasTransformThreadErrorCatcher() && (object = this.getExceptionThrown()) != null) {
                ((Throwable)object).printStackTrace();
                SAXException sAXException = new SAXException((Exception)object);
                throw sAXException;
            }
            this.setTransformThread(null);
            return;
        }
        catch (InterruptedException interruptedException) {
            // empty catch block
        }
    }
}

