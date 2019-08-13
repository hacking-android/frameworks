/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.OutputProperties;
import org.apache.xalan.transformer.SerializerSwitcher;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.utils.DOMBuilder;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.WrappedRuntimeException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class TransformerIdentityImpl
extends Transformer
implements TransformerHandler,
DeclHandler {
    URIResolver m_URIResolver;
    private ErrorListener m_errorListener = new DefaultErrorHandler(false);
    boolean m_flushedStartDoc = false;
    boolean m_foundFirstElement;
    private boolean m_isSecureProcessing = false;
    private OutputProperties m_outputFormat = new OutputProperties("xml");
    private FileOutputStream m_outputStream = null;
    private Hashtable m_params;
    private Result m_result;
    private ContentHandler m_resultContentHandler;
    private DTDHandler m_resultDTDHandler;
    private DeclHandler m_resultDeclHandler;
    private LexicalHandler m_resultLexicalHandler;
    private Serializer m_serializer;
    private String m_systemID;

    public TransformerIdentityImpl() {
        this(false);
    }

    public TransformerIdentityImpl(boolean bl) {
        this.m_isSecureProcessing = bl;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void createResultContentHandler(Result object) throws TransformerException {
        block23 : {
            block25 : {
                block26 : {
                    short s;
                    block24 : {
                        if (!(object instanceof SAXResult)) break block24;
                        object = (SAXResult)object;
                        this.m_resultContentHandler = ((SAXResult)object).getHandler();
                        this.m_resultLexicalHandler = ((SAXResult)object).getLexicalHandler();
                        object = this.m_resultContentHandler;
                        if (object instanceof Serializer) {
                            this.m_serializer = (Serializer)object;
                        }
                        break block25;
                    }
                    if (!(object instanceof DOMResult)) break block26;
                    Object object2 = (DOMResult)object;
                    Object object3 = ((DOMResult)object2).getNode();
                    Node node = ((DOMResult)object2).getNextSibling();
                    if (object3 != null) {
                        s = object3.getNodeType();
                        object = 9 == s ? (Document)object3 : object3.getOwnerDocument();
                    } else {
                        block22 : {
                            object2 = DocumentBuilderFactory.newInstance();
                            ((DocumentBuilderFactory)object2).setNamespaceAware(true);
                            boolean bl = this.m_isSecureProcessing;
                            if (!bl) break block22;
                            try {
                                ((DocumentBuilderFactory)object2).setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                            }
                            catch (ParserConfigurationException parserConfigurationException) {
                                // empty catch block
                            }
                        }
                        object3 = object2 = ((DocumentBuilderFactory)object2).newDocumentBuilder().newDocument();
                        s = object3.getNodeType();
                        ((DOMResult)object).setNode((Node)object3);
                        object = object2;
                    }
                    object = 11 == s ? new DOMBuilder((Document)object, (DocumentFragment)object3) : new DOMBuilder((Document)object, (Node)object3);
                    if (node != null) {
                        ((DOMBuilder)object).setNextSibling(node);
                    }
                    this.m_resultContentHandler = object;
                    this.m_resultLexicalHandler = object;
                    break block25;
                    catch (ParserConfigurationException parserConfigurationException) {
                        throw new TransformerException(parserConfigurationException);
                    }
                }
                if (!(object instanceof StreamResult)) {
                    throw new TransformerException(XSLMessages.createMessage("ER_CANNOT_TRANSFORM_TO_RESULT_TYPE", new Object[]{object.getClass().getName()}));
                }
                object = (StreamResult)object;
                try {
                    Serializer serializer;
                    this.m_serializer = serializer = SerializerFactory.getSerializer(this.m_outputFormat.getProperties());
                    if (((StreamResult)object).getWriter() != null) {
                        serializer.setWriter(((StreamResult)object).getWriter());
                    } else if (((StreamResult)object).getOutputStream() != null) {
                        serializer.setOutputStream(((StreamResult)object).getOutputStream());
                    } else {
                        if (((StreamResult)object).getSystemId() == null) break block23;
                        Object object4 = ((StreamResult)object).getSystemId();
                        boolean bl = ((String)object4).startsWith("file:///");
                        if (bl) {
                            object = ((String)object4).substring(8).indexOf(":") > 0 ? ((String)object4).substring(8) : ((String)object4).substring(7);
                        } else {
                            object = object4;
                            if (((String)object4).startsWith("file:/")) {
                                object = ((String)object4).substring(6).indexOf(":") > 0 ? ((String)object4).substring(6) : ((String)object4).substring(5);
                            }
                        }
                        this.m_outputStream = object4 = new FileOutputStream((String)object);
                        serializer.setOutputStream(this.m_outputStream);
                    }
                    this.m_resultContentHandler = serializer.asContentHandler();
                }
                catch (IOException iOException) {
                    throw new TransformerException(iOException);
                }
            }
            object = this.m_resultContentHandler;
            if (object instanceof DTDHandler) {
                this.m_resultDTDHandler = (DTDHandler)object;
            }
            if ((object = this.m_resultContentHandler) instanceof DeclHandler) {
                this.m_resultDeclHandler = (DeclHandler)object;
            }
            if (!((object = this.m_resultContentHandler) instanceof LexicalHandler)) return;
            this.m_resultLexicalHandler = (LexicalHandler)object;
            return;
        }
        object = new TransformerException(XSLMessages.createMessage("ER_NO_OUTPUT_SPECIFIED", null));
        throw object;
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
        DeclHandler declHandler = this.m_resultDeclHandler;
        if (declHandler != null) {
            declHandler.attributeDecl(string, string2, string3, string4, string5);
        }
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.characters(arrc, n, n2);
    }

    @Override
    public void clearParameters() {
        Hashtable hashtable = this.m_params;
        if (hashtable == null) {
            return;
        }
        hashtable.clear();
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.flushStartDoc();
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.comment(arrc, n, n2);
        }
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
        DeclHandler declHandler = this.m_resultDeclHandler;
        if (declHandler != null) {
            declHandler.elementDecl(string, string2);
        }
    }

    @Override
    public void endCDATA() throws SAXException {
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endCDATA();
        }
    }

    @Override
    public void endDTD() throws SAXException {
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endDTD();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.endDocument();
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        this.m_resultContentHandler.endElement(string, string2, string3);
    }

    @Override
    public void endEntity(String string) throws SAXException {
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endEntity(string);
        }
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.endPrefixMapping(string);
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
        DeclHandler declHandler = this.m_resultDeclHandler;
        if (declHandler != null) {
            declHandler.externalEntityDecl(string, string2, string3);
        }
    }

    protected final void flushStartDoc() throws SAXException {
        if (!this.m_flushedStartDoc) {
            if (this.m_resultContentHandler == null) {
                try {
                    this.createResultContentHandler(this.m_result);
                }
                catch (TransformerException transformerException) {
                    throw new SAXException(transformerException);
                }
            }
            this.m_resultContentHandler.startDocument();
            this.m_flushedStartDoc = true;
        }
    }

    @Override
    public ErrorListener getErrorListener() {
        return this.m_errorListener;
    }

    @Override
    public Properties getOutputProperties() {
        return (Properties)this.m_outputFormat.getProperties().clone();
    }

    @Override
    public String getOutputProperty(String string) throws IllegalArgumentException {
        String string2 = this.m_outputFormat.getProperty(string);
        if (string2 == null && !OutputProperties.isLegalPropertyKey(string)) {
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
        }
        return string2;
    }

    @Override
    public Object getParameter(String string) {
        Hashtable hashtable = this.m_params;
        if (hashtable == null) {
            return null;
        }
        return hashtable.get(string);
    }

    @Override
    public String getSystemId() {
        return this.m_systemID;
    }

    @Override
    public Transformer getTransformer() {
        return this;
    }

    @Override
    public URIResolver getURIResolver() {
        return this.m_URIResolver;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        this.m_resultContentHandler.ignorableWhitespace(arrc, n, n2);
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
        DeclHandler declHandler = this.m_resultDeclHandler;
        if (declHandler != null) {
            declHandler.internalEntityDecl(string, string2);
        }
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
        DTDHandler dTDHandler = this.m_resultDTDHandler;
        if (dTDHandler != null) {
            dTDHandler.notationDecl(string, string2, string3);
        }
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.processingInstruction(string, string2);
    }

    @Override
    public void reset() {
        this.m_flushedStartDoc = false;
        this.m_foundFirstElement = false;
        this.m_outputStream = null;
        this.clearParameters();
        this.m_result = null;
        this.m_resultContentHandler = null;
        this.m_resultDeclHandler = null;
        this.m_resultDTDHandler = null;
        this.m_resultLexicalHandler = null;
        this.m_serializer = null;
        this.m_systemID = null;
        this.m_URIResolver = null;
        this.m_outputFormat = new OutputProperties("xml");
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        try {
            if (this.m_resultContentHandler == null) {
                this.createResultContentHandler(this.m_result);
            }
            this.m_resultContentHandler.setDocumentLocator(locator);
            return;
        }
        catch (TransformerException transformerException) {
            throw new WrappedRuntimeException(transformerException);
        }
    }

    @Override
    public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException {
        if (errorListener != null) {
            this.m_errorListener = errorListener;
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_NULL_ERROR_HANDLER", null));
    }

    @Override
    public void setOutputProperties(Properties properties) throws IllegalArgumentException {
        if (properties != null) {
            String string = (String)properties.get("method");
            this.m_outputFormat = string != null ? new OutputProperties(string) : new OutputProperties();
            this.m_outputFormat.copyFrom(properties);
        } else {
            this.m_outputFormat = null;
        }
    }

    @Override
    public void setOutputProperty(String string, String string2) throws IllegalArgumentException {
        if (OutputProperties.isLegalPropertyKey(string)) {
            this.m_outputFormat.setProperty(string, string2);
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
    }

    @Override
    public void setParameter(String string, Object object) {
        if (object != null) {
            if (this.m_params == null) {
                this.m_params = new Hashtable();
            }
            this.m_params.put(string, object);
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_INVALID_SET_PARAM_VALUE", new Object[]{string}));
    }

    @Override
    public void setResult(Result result) throws IllegalArgumentException {
        if (result != null) {
            this.m_result = result;
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_NULL", null));
    }

    @Override
    public void setSystemId(String string) {
        this.m_systemID = string;
    }

    @Override
    public void setURIResolver(URIResolver uRIResolver) {
        this.m_URIResolver = uRIResolver;
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.skippedEntity(string);
    }

    @Override
    public void startCDATA() throws SAXException {
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
        }
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        this.flushStartDoc();
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startDTD(string, string2, string3);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        try {
            if (this.m_resultContentHandler == null) {
                this.createResultContentHandler(this.m_result);
            }
            this.m_flushedStartDoc = false;
            this.m_foundFirstElement = false;
            return;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException.getMessage(), transformerException);
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        block7 : {
            if (!this.m_foundFirstElement && this.m_serializer != null) {
                ContentHandler contentHandler;
                Serializer serializer;
                this.m_foundFirstElement = true;
                try {
                    serializer = SerializerSwitcher.switchSerializerIfHTML(string, string2, this.m_outputFormat.getProperties(), this.m_serializer);
                    if (serializer == this.m_serializer) break block7;
                }
                catch (TransformerException transformerException) {
                    throw new SAXException(transformerException);
                }
                try {
                    contentHandler = this.m_resultContentHandler = serializer.asContentHandler();
                }
                catch (IOException iOException) {
                    throw new SAXException(iOException);
                }
                if (contentHandler instanceof DTDHandler) {
                    this.m_resultDTDHandler = (DTDHandler)((Object)contentHandler);
                }
                if ((contentHandler = this.m_resultContentHandler) instanceof LexicalHandler) {
                    this.m_resultLexicalHandler = (LexicalHandler)((Object)contentHandler);
                }
                this.m_serializer = serializer;
            }
        }
        this.flushStartDoc();
        this.m_resultContentHandler.startElement(string, string2, string3, attributes);
    }

    @Override
    public void startEntity(String string) throws SAXException {
        LexicalHandler lexicalHandler = this.m_resultLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startEntity(string);
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        this.flushStartDoc();
        this.m_resultContentHandler.startPrefixMapping(string, string2);
    }

    /*
     * Exception decompiling
     */
    @Override
    public void transform(Source var1_1, Result var2_8) throws TransformerException {
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

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        DTDHandler dTDHandler = this.m_resultDTDHandler;
        if (dTDHandler != null) {
            dTDHandler.unparsedEntityDecl(string, string2, string3, string4);
        }
    }
}

