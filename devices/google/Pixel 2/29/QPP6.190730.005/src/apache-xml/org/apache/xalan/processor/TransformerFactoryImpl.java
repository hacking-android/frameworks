/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TemplatesHandler;
import javax.xml.transform.sax.TransformerHandler;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.transformer.TrAXFilter;
import org.apache.xalan.transformer.TransformerIdentityImpl;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.utils.DOM2Helper;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.DefaultErrorHandler;
import org.apache.xml.utils.TreeWalker;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;

public class TransformerFactoryImpl
extends SAXTransformerFactory {
    public static final String FEATURE_INCREMENTAL = "http://xml.apache.org/xalan/features/incremental";
    public static final String FEATURE_OPTIMIZE = "http://xml.apache.org/xalan/features/optimize";
    public static final String FEATURE_SOURCE_LOCATION = "http://xml.apache.org/xalan/properties/source-location";
    public static final String XSLT_PROPERTIES = "org/apache/xalan/res/XSLTInfo.properties";
    private String m_DOMsystemID = null;
    private ErrorListener m_errorListener = new DefaultErrorHandler(false);
    private boolean m_incremental = false;
    private boolean m_isSecureProcessing = false;
    private boolean m_optimize = true;
    private boolean m_source_location = false;
    URIResolver m_uriResolver;

    /*
     * Exception decompiling
     */
    @Override
    public Source getAssociatedStylesheet(Source var1, String var2, String var3, String var4) throws TransformerConfigurationException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Malformed exception block, to < from
        // org.benf.cfr.reader.entities.exceptions.ExceptionTableEntry.<init>(ExceptionTableEntry.java:40)
        // org.benf.cfr.reader.entities.exceptions.ExceptionTableEntry.copyWithRange(ExceptionTableEntry.java:61)
        // org.benf.cfr.reader.entities.exceptions.IntervalOverlapper.processEntry(IntervalOverlapper.java:123)
        // org.benf.cfr.reader.entities.exceptions.IntervalOverlapper.processEntries(IntervalOverlapper.java:22)
        // org.benf.cfr.reader.entities.exceptions.IntervalOverlapper.<init>(IntervalOverlapper.java:17)
        // org.benf.cfr.reader.entities.exceptions.ExceptionAggregator.<init>(ExceptionAggregator.java:327)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:257)
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
    public Object getAttribute(String string) throws IllegalArgumentException {
        if (string.equals(FEATURE_INCREMENTAL)) {
            return new Boolean(this.m_incremental);
        }
        if (string.equals(FEATURE_OPTIMIZE)) {
            return new Boolean(this.m_optimize);
        }
        if (string.equals(FEATURE_SOURCE_LOCATION)) {
            return new Boolean(this.m_source_location);
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_ATTRIB_VALUE_NOT_RECOGNIZED", new Object[]{string}));
    }

    String getDOMsystemID() {
        return this.m_DOMsystemID;
    }

    @Override
    public ErrorListener getErrorListener() {
        return this.m_errorListener;
    }

    @Override
    public boolean getFeature(String string) {
        if (string != null) {
            if ("http://javax.xml.transform.dom.DOMResult/feature" != string && "http://javax.xml.transform.dom.DOMSource/feature" != string && "http://javax.xml.transform.sax.SAXResult/feature" != string && "http://javax.xml.transform.sax.SAXSource/feature" != string && "http://javax.xml.transform.stream.StreamResult/feature" != string && "http://javax.xml.transform.stream.StreamSource/feature" != string && "http://javax.xml.transform.sax.SAXTransformerFactory/feature" != string && "http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter" != string) {
                if (!("http://javax.xml.transform.dom.DOMResult/feature".equals(string) || "http://javax.xml.transform.dom.DOMSource/feature".equals(string) || "http://javax.xml.transform.sax.SAXResult/feature".equals(string) || "http://javax.xml.transform.sax.SAXSource/feature".equals(string) || "http://javax.xml.transform.stream.StreamResult/feature".equals(string) || "http://javax.xml.transform.stream.StreamSource/feature".equals(string) || "http://javax.xml.transform.sax.SAXTransformerFactory/feature".equals(string) || "http://javax.xml.transform.sax.SAXTransformerFactory/feature/xmlfilter".equals(string))) {
                    if (string.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
                        return this.m_isSecureProcessing;
                    }
                    return false;
                }
                return true;
            }
            return true;
        }
        throw new NullPointerException(XSLMessages.createMessage("ER_GET_FEATURE_NULL_NAME", null));
    }

    @Override
    public URIResolver getURIResolver() {
        return this.m_uriResolver;
    }

    public boolean isSecureProcessing() {
        return this.m_isSecureProcessing;
    }

    /*
     * Exception decompiling
     */
    @Override
    public Templates newTemplates(Source var1_1) throws TransformerConfigurationException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK], 1[TRYBLOCK]], but top level block is 9[CATCHBLOCK]
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

    @Override
    public TemplatesHandler newTemplatesHandler() throws TransformerConfigurationException {
        return new StylesheetHandler(this);
    }

    @Override
    public Transformer newTransformer() throws TransformerConfigurationException {
        return new TransformerIdentityImpl(this.m_isSecureProcessing);
    }

    @Override
    public Transformer newTransformer(Source object) throws TransformerConfigurationException {
        block7 : {
            try {
                object = this.newTemplates((Source)object);
                if (object != null) break block7;
                return null;
            }
            catch (TransformerConfigurationException transformerConfigurationException) {
                ErrorListener errorListener = this.m_errorListener;
                if (errorListener != null) {
                    try {
                        errorListener.fatalError(transformerConfigurationException);
                        return null;
                    }
                    catch (TransformerException transformerException) {
                        throw new TransformerConfigurationException(transformerException);
                    }
                    catch (TransformerConfigurationException transformerConfigurationException2) {
                        throw transformerConfigurationException2;
                    }
                }
                throw transformerConfigurationException;
            }
        }
        object = object.newTransformer();
        ((Transformer)object).setURIResolver(this.m_uriResolver);
        return object;
    }

    @Override
    public TransformerHandler newTransformerHandler() throws TransformerConfigurationException {
        return new TransformerIdentityImpl(this.m_isSecureProcessing);
    }

    @Override
    public TransformerHandler newTransformerHandler(Source object) throws TransformerConfigurationException {
        if ((object = this.newTemplates((Source)object)) == null) {
            return null;
        }
        return this.newTransformerHandler((Templates)object);
    }

    @Override
    public TransformerHandler newTransformerHandler(Templates object) throws TransformerConfigurationException {
        try {
            object = (TransformerImpl)object.newTransformer();
            ((TransformerImpl)object).setURIResolver(this.m_uriResolver);
            object = (TransformerHandler)((TransformerImpl)object).getInputContentHandler(true);
            return object;
        }
        catch (TransformerConfigurationException transformerConfigurationException) {
            object = this.m_errorListener;
            if (object != null) {
                try {
                    object.fatalError(transformerConfigurationException);
                    return null;
                }
                catch (TransformerException transformerException) {
                    throw new TransformerConfigurationException(transformerException);
                }
                catch (TransformerConfigurationException transformerConfigurationException2) {
                    throw transformerConfigurationException2;
                }
            }
            throw transformerConfigurationException;
        }
    }

    @Override
    public XMLFilter newXMLFilter(Source object) throws TransformerConfigurationException {
        if ((object = this.newTemplates((Source)object)) == null) {
            return null;
        }
        return this.newXMLFilter((Templates)object);
    }

    @Override
    public XMLFilter newXMLFilter(Templates object) throws TransformerConfigurationException {
        try {
            object = new TrAXFilter((Templates)object);
            return object;
        }
        catch (TransformerConfigurationException transformerConfigurationException) {
            object = this.m_errorListener;
            if (object != null) {
                try {
                    object.fatalError(transformerConfigurationException);
                    return null;
                }
                catch (TransformerException transformerException) {
                    throw new TransformerConfigurationException(transformerException);
                }
                catch (TransformerConfigurationException transformerConfigurationException2) {
                    throw transformerConfigurationException2;
                }
            }
            throw transformerConfigurationException;
        }
    }

    public Templates processFromNode(Node object) throws TransformerConfigurationException {
        try {
            TemplatesHandler templatesHandler = this.newTemplatesHandler();
            DOM2Helper dOM2Helper = new DOM2Helper();
            TreeWalker treeWalker = new TreeWalker(templatesHandler, dOM2Helper, templatesHandler.getSystemId());
            treeWalker.traverse((Node)object);
            object = templatesHandler.getTemplates();
            return object;
        }
        catch (Exception exception) {
            object = this.m_errorListener;
            if (object != null) {
                try {
                    TransformerException transformerException = new TransformerException(exception);
                    object.fatalError(transformerException);
                    return null;
                }
                catch (TransformerException transformerException) {
                    throw new TransformerConfigurationException(transformerException);
                }
                catch (TransformerConfigurationException transformerConfigurationException) {
                    throw transformerConfigurationException;
                }
            }
            throw new TransformerConfigurationException(XSLMessages.createMessage("ER_PROCESSFROMNODE_FAILED", null), exception);
        }
        catch (TransformerConfigurationException transformerConfigurationException) {
            throw transformerConfigurationException;
        }
        catch (SAXException sAXException) {
            ErrorListener errorListener = this.m_errorListener;
            if (errorListener != null) {
                try {
                    object = new TransformerException(sAXException);
                    errorListener.fatalError((TransformerException)object);
                    return null;
                }
                catch (TransformerException transformerException) {
                    throw new TransformerConfigurationException(transformerException);
                }
                catch (TransformerConfigurationException transformerConfigurationException) {
                    throw transformerConfigurationException;
                }
            }
            throw new TransformerConfigurationException(XSLMessages.createMessage("ER_PROCESSFROMNODE_FAILED", null), sAXException);
        }
    }

    Templates processFromNode(Node node, String string) throws TransformerConfigurationException {
        this.m_DOMsystemID = string;
        return this.processFromNode(node);
    }

    @Override
    public void setAttribute(String string, Object object) throws IllegalArgumentException {
        block9 : {
            block11 : {
                block4 : {
                    block10 : {
                        block6 : {
                            block8 : {
                                block7 : {
                                    block2 : {
                                        block5 : {
                                            block3 : {
                                                if (!string.equals(FEATURE_INCREMENTAL)) break block2;
                                                if (!(object instanceof Boolean)) break block3;
                                                this.m_incremental = (Boolean)object;
                                                break block4;
                                            }
                                            if (!(object instanceof String)) break block5;
                                            this.m_incremental = new Boolean((String)object);
                                            break block4;
                                        }
                                        throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[]{string, object}));
                                    }
                                    if (!string.equals(FEATURE_OPTIMIZE)) break block6;
                                    if (!(object instanceof Boolean)) break block7;
                                    this.m_optimize = (Boolean)object;
                                    break block4;
                                }
                                if (!(object instanceof String)) break block8;
                                this.m_optimize = new Boolean((String)object);
                                break block4;
                            }
                            throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[]{string, object}));
                        }
                        if (!string.equals(FEATURE_SOURCE_LOCATION)) break block9;
                        if (!(object instanceof Boolean)) break block10;
                        this.m_source_location = (Boolean)object;
                        break block4;
                    }
                    if (!(object instanceof String)) break block11;
                    this.m_source_location = new Boolean((String)object);
                }
                return;
            }
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_BAD_VALUE", new Object[]{string, object}));
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_NOT_SUPPORTED", new Object[]{string}));
    }

    @Override
    public void setErrorListener(ErrorListener errorListener) throws IllegalArgumentException {
        if (errorListener != null) {
            this.m_errorListener = errorListener;
            return;
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_ERRORLISTENER", null));
    }

    @Override
    public void setFeature(String string, boolean bl) throws TransformerConfigurationException {
        if (string != null) {
            if (string.equals("http://javax.xml.XMLConstants/feature/secure-processing")) {
                this.m_isSecureProcessing = bl;
                return;
            }
            throw new TransformerConfigurationException(XSLMessages.createMessage("ER_UNSUPPORTED_FEATURE", new Object[]{string}));
        }
        throw new NullPointerException(XSLMessages.createMessage("ER_SET_FEATURE_NULL_NAME", null));
    }

    @Override
    public void setURIResolver(URIResolver uRIResolver) {
        this.m_uriResolver = uRIResolver;
    }
}

