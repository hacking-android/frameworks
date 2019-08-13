/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.IOException;
import java.io.PrintStream;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.sax.TransformerHandler;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.IncrementalSAXSource_Filter;
import org.apache.xml.dtm.ref.sax2dtm.SAX2DTM;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xpath.XPathContext;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ext.DeclHandler;
import org.xml.sax.ext.LexicalHandler;

public class TransformerHandlerImpl
implements EntityResolver,
DTDHandler,
ContentHandler,
ErrorHandler,
LexicalHandler,
TransformerHandler,
DeclHandler {
    private static boolean DEBUG = false;
    private String m_baseSystemID;
    private ContentHandler m_contentHandler = null;
    private DeclHandler m_declHandler = null;
    private DTDHandler m_dtdHandler = null;
    DTM m_dtm;
    private EntityResolver m_entityResolver = null;
    private ErrorHandler m_errorHandler = null;
    private final boolean m_incremental;
    private boolean m_insideParse = false;
    private LexicalHandler m_lexicalHandler = null;
    private Locator m_locator = null;
    private final boolean m_optimizer;
    private Result m_result = null;
    private final boolean m_source_location;
    private TransformerImpl m_transformer;

    public TransformerHandlerImpl(TransformerImpl transformerImpl, boolean bl, String string) {
        DTM dTM;
        this.m_transformer = transformerImpl;
        this.m_baseSystemID = string;
        this.m_dtm = dTM = transformerImpl.getXPathContext().getDTM(null, true, transformerImpl, true, true);
        dTM.setDocumentBaseURI(string);
        this.m_contentHandler = dTM.getContentHandler();
        this.m_dtdHandler = dTM.getDTDHandler();
        this.m_entityResolver = dTM.getEntityResolver();
        this.m_errorHandler = dTM.getErrorHandler();
        this.m_lexicalHandler = dTM.getLexicalHandler();
        this.m_incremental = transformerImpl.getIncremental();
        this.m_optimizer = transformerImpl.getOptimize();
        this.m_source_location = transformerImpl.getSource_location();
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#attributeDecl: ");
            stringBuilder.append(string);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            stringBuilder.append(", etc...");
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_declHandler) != null) {
            object.attributeDecl(string, string2, string3, string4, string5);
        }
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#characters: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.characters(arrc, n, n2);
        }
    }

    protected void clearCoRoutine() {
        this.clearCoRoutine(null);
    }

    protected void clearCoRoutine(SAXException object) {
        if (object != null) {
            this.m_transformer.setExceptionThrown((Exception)object);
        }
        if (this.m_dtm instanceof SAX2DTM) {
            if (DEBUG) {
                System.err.println("In clearCoRoutine...");
            }
            try {
                object = (SAX2DTM)this.m_dtm;
                if (this.m_contentHandler != null && this.m_contentHandler instanceof IncrementalSAXSource_Filter) {
                    ((IncrementalSAXSource_Filter)this.m_contentHandler).deliverMoreNodes(false);
                }
                ((SAX2DTM)object).clearCoRoutine(true);
                this.m_contentHandler = null;
                this.m_dtdHandler = null;
                this.m_entityResolver = null;
                this.m_errorHandler = null;
                this.m_lexicalHandler = null;
            }
            catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            if (DEBUG) {
                System.err.println("...exiting clearCoRoutine");
            }
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#comment: ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(n2);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_lexicalHandler) != null) {
            object.comment(arrc, n, n2);
        }
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#elementDecl: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_declHandler) != null) {
            object.elementDecl(string, string2);
        }
    }

    @Override
    public void endCDATA() throws SAXException {
        LexicalHandler lexicalHandler;
        if (DEBUG) {
            System.out.println("TransformerHandlerImpl#endCDATA");
        }
        if ((lexicalHandler = this.m_lexicalHandler) != null) {
            lexicalHandler.endCDATA();
        }
    }

    @Override
    public void endDTD() throws SAXException {
        LexicalHandler lexicalHandler;
        if (DEBUG) {
            System.out.println("TransformerHandlerImpl#endDTD");
        }
        if ((lexicalHandler = this.m_lexicalHandler) != null) {
            lexicalHandler.endDTD();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        if (DEBUG) {
            System.out.println("TransformerHandlerImpl#endDocument");
        }
        this.m_insideParse = false;
        ContentHandler contentHandler = this.m_contentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
        if (this.m_incremental) {
            this.m_transformer.waitTransformThread();
        } else {
            this.m_transformer.setSourceTreeDocForThread(this.m_dtm.getDocument());
            this.m_transformer.run();
        }
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#endElement: ");
            stringBuilder.append(string3);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.endElement(string, string2, string3);
        }
    }

    @Override
    public void endEntity(String string) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#endEntity: ");
            ((StringBuilder)object).append(string);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_lexicalHandler) != null) {
            object.endEntity(string);
        }
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#endPrefixMapping: ");
            stringBuilder.append(string);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.endPrefixMapping(string);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        ErrorListener errorListener = this.m_transformer.getErrorListener();
        if (errorListener instanceof ErrorHandler) {
            ((ErrorHandler)((Object)errorListener)).error(sAXParseException);
            ErrorHandler errorHandler = this.m_errorHandler;
            if (errorHandler == null) return;
            errorHandler.error(sAXParseException);
            return;
        }
        try {
            TransformerException transformerException = new TransformerException(sAXParseException);
            errorListener.error(transformerException);
            if (this.m_errorHandler == null) return;
            this.m_errorHandler.error(sAXParseException);
            return;
        }
        catch (TransformerException transformerException) {
            throw sAXParseException;
        }
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#externalEntityDecl: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string3);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_declHandler) != null) {
            object.externalEntityDecl(string, string2, string3);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        Object object = this.m_errorHandler;
        if (object != null) {
            try {
                object.fatalError(sAXParseException);
            }
            catch (SAXParseException sAXParseException2) {
                // empty catch block
            }
        }
        if ((object = this.m_transformer.getErrorListener()) instanceof ErrorHandler) {
            ((ErrorHandler)object).fatalError(sAXParseException);
            object = this.m_errorHandler;
            if (object == null) return;
            object.fatalError(sAXParseException);
            return;
        }
        try {
            TransformerException transformerException = new TransformerException(sAXParseException);
            object.fatalError(transformerException);
            if (this.m_errorHandler == null) return;
            this.m_errorHandler.fatalError(sAXParseException);
            return;
        }
        catch (TransformerException transformerException) {
            throw sAXParseException;
        }
    }

    @Override
    public String getSystemId() {
        return this.m_baseSystemID;
    }

    @Override
    public Transformer getTransformer() {
        return this.m_transformer;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#ignorableWhitespace: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.ignorableWhitespace(arrc, n, n2);
        }
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#internalEntityDecl: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_declHandler) != null) {
            object.internalEntityDecl(string, string2);
        }
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
        DTDHandler dTDHandler = this.m_dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.notationDecl(string, string2, string3);
        }
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#processingInstruction: ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(string2);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.processingInstruction(string, string2);
        }
    }

    @Override
    public InputSource resolveEntity(String string, String string2) throws SAXException, IOException {
        EntityResolver entityResolver = this.m_entityResolver;
        if (entityResolver != null) {
            return entityResolver.resolveEntity(string, string2);
        }
        return null;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#setDocumentLocator: ");
            ((StringBuilder)object).append(locator.getSystemId());
            printStream.println(((StringBuilder)object).toString());
        }
        this.m_locator = locator;
        if (this.m_baseSystemID == null) {
            this.setSystemId(locator.getSystemId());
        }
        if ((object = this.m_contentHandler) != null) {
            object.setDocumentLocator(locator);
        }
    }

    @Override
    public void setResult(Result result) throws IllegalArgumentException {
        if (result != null) {
            try {
                SerializationHandler serializationHandler = this.m_transformer.createSerializationHandler(result);
                this.m_transformer.setSerializationHandler(serializationHandler);
                this.m_result = result;
                return;
            }
            catch (TransformerException transformerException) {
                throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_COULD_NOT_BE_SET", null));
            }
        }
        throw new IllegalArgumentException(XSLMessages.createMessage("ER_RESULT_NULL", null));
    }

    @Override
    public void setSystemId(String string) {
        this.m_baseSystemID = string;
        this.m_dtm.setDocumentBaseURI(string);
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#skippedEntity: ");
            stringBuilder.append(string);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.skippedEntity(string);
        }
    }

    @Override
    public void startCDATA() throws SAXException {
        LexicalHandler lexicalHandler;
        if (DEBUG) {
            System.out.println("TransformerHandlerImpl#startCDATA");
        }
        if ((lexicalHandler = this.m_lexicalHandler) != null) {
            lexicalHandler.startCDATA();
        }
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#startDTD: ");
            stringBuilder.append(string);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            stringBuilder.append(", ");
            stringBuilder.append(string3);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_lexicalHandler) != null) {
            object.startDTD(string, string2, string3);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        if (DEBUG) {
            System.out.println("TransformerHandlerImpl#startDocument");
        }
        this.m_insideParse = true;
        if (this.m_contentHandler != null) {
            if (this.m_incremental) {
                this.m_transformer.setSourceTreeDocForThread(this.m_dtm.getDocument());
                int n = Thread.currentThread().getPriority();
                this.m_transformer.runTransformThread(n);
            }
            this.m_contentHandler.startDocument();
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#startElement: ");
            ((StringBuilder)object).append(string3);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.startElement(string, string2, string3, attributes);
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
        Object object;
        if (DEBUG) {
            PrintStream printStream = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("TransformerHandlerImpl#startEntity: ");
            ((StringBuilder)object).append(string);
            printStream.println(((StringBuilder)object).toString());
        }
        if ((object = this.m_lexicalHandler) != null) {
            object.startEntity(string);
        }
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        Object object;
        if (DEBUG) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TransformerHandlerImpl#startPrefixMapping: ");
            stringBuilder.append(string);
            stringBuilder.append(", ");
            stringBuilder.append(string2);
            ((PrintStream)object).println(stringBuilder.toString());
        }
        if ((object = this.m_contentHandler) != null) {
            object.startPrefixMapping(string, string2);
        }
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        DTDHandler dTDHandler = this.m_dtdHandler;
        if (dTDHandler != null) {
            dTDHandler.unparsedEntityDecl(string, string2, string3, string4);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        ErrorListener errorListener = this.m_transformer.getErrorListener();
        if (errorListener instanceof ErrorHandler) {
            ((ErrorHandler)((Object)errorListener)).warning(sAXParseException);
            return;
        }
        try {
            TransformerException transformerException = new TransformerException(sAXParseException);
            errorListener.warning(transformerException);
            return;
        }
        catch (TransformerException transformerException) {
            throw sAXParseException;
        }
    }
}

