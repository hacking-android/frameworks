/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import org.apache.xml.dtm.ref.CoroutineManager;
import org.apache.xml.dtm.ref.IncrementalSAXSource;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.ThreadControllerWrapper;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;

public class IncrementalSAXSource_Filter
implements IncrementalSAXSource,
ContentHandler,
DTDHandler,
LexicalHandler,
ErrorHandler,
Runnable {
    boolean DEBUG = false;
    private ContentHandler clientContentHandler = null;
    private DTDHandler clientDTDHandler = null;
    private ErrorHandler clientErrorHandler = null;
    private LexicalHandler clientLexicalHandler = null;
    private int eventcounter;
    private int fControllerCoroutineID = -1;
    private CoroutineManager fCoroutineManager = null;
    private boolean fNoMoreEvents = false;
    private int fSourceCoroutineID = -1;
    private XMLReader fXMLReader = null;
    private InputSource fXMLReaderInputSource = null;
    private int frequency = 5;

    public IncrementalSAXSource_Filter() {
        this.init(new CoroutineManager(), -1, -1);
    }

    public IncrementalSAXSource_Filter(CoroutineManager coroutineManager, int n) {
        this.init(coroutineManager, n, -1);
    }

    private void co_entry_pause() throws SAXException {
        if (this.fCoroutineManager == null) {
            this.init(null, -1, -1);
        }
        try {
            if (this.fCoroutineManager.co_entry_pause(this.fSourceCoroutineID) == Boolean.FALSE) {
                this.co_yield(false);
            }
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            if (this.DEBUG) {
                noSuchMethodException.printStackTrace();
            }
            throw new SAXException(noSuchMethodException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void co_yield(boolean bl) throws SAXException {
        if (this.fNoMoreEvents) {
            return;
        }
        try {
            Object object = Boolean.FALSE;
            if (bl) {
                object = this.fCoroutineManager.co_resume(Boolean.TRUE, this.fSourceCoroutineID, this.fControllerCoroutineID);
            }
            if (object != Boolean.FALSE) return;
            this.fNoMoreEvents = true;
            if (this.fXMLReader == null) {
                this.fCoroutineManager.co_exit_to(Boolean.FALSE, this.fSourceCoroutineID, this.fControllerCoroutineID);
                return;
            }
            object = new StopException();
            throw object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            this.fNoMoreEvents = true;
            this.fCoroutineManager.co_exit(this.fSourceCoroutineID);
            throw new SAXException(noSuchMethodException);
        }
    }

    public static IncrementalSAXSource createIncrementalSAXSource(CoroutineManager coroutineManager, int n) {
        return new IncrementalSAXSource_Filter(coroutineManager, n);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler;
        int n3;
        this.eventcounter = n3 = this.eventcounter - 1;
        if (n3 <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.characters(arrc, n, n2);
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.comment(arrc, n, n2);
        }
    }

    protected void count_and_yield(boolean bl) throws SAXException {
        int n;
        if (!bl) {
            this.eventcounter = 0;
        }
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Object deliverMoreNodes(boolean bl) {
        if (this.fNoMoreEvents) {
            return Boolean.FALSE;
        }
        try {
            CoroutineManager coroutineManager = this.fCoroutineManager;
            Object object = bl ? Boolean.TRUE : Boolean.FALSE;
            object = coroutineManager.co_resume(object, this.fControllerCoroutineID, this.fSourceCoroutineID);
            if (object == Boolean.FALSE) {
                this.fCoroutineManager.co_exit(this.fControllerCoroutineID);
            }
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return noSuchMethodException;
        }
    }

    @Override
    public void endCDATA() throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endCDATA();
        }
    }

    @Override
    public void endDTD() throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endDTD();
        }
    }

    @Override
    public void endDocument() throws SAXException {
        ContentHandler contentHandler = this.clientContentHandler;
        if (contentHandler != null) {
            contentHandler.endDocument();
        }
        this.eventcounter = 0;
        this.co_yield(false);
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        ContentHandler contentHandler;
        int n;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.endElement(string, string2, string3);
        }
    }

    @Override
    public void endEntity(String string) throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.endEntity(string);
        }
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
        int n;
        ContentHandler contentHandler;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.endPrefixMapping(string);
        }
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.clientErrorHandler;
        if (errorHandler != null) {
            errorHandler.error(sAXParseException);
        }
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.clientErrorHandler;
        if (errorHandler != null) {
            errorHandler.error(sAXParseException);
        }
        this.eventcounter = 0;
        this.co_yield(false);
    }

    public int getControllerCoroutineID() {
        return this.fControllerCoroutineID;
    }

    public CoroutineManager getCoroutineManager() {
        return this.fCoroutineManager;
    }

    public int getSourceCoroutineID() {
        return this.fSourceCoroutineID;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        ContentHandler contentHandler;
        int n3;
        this.eventcounter = n3 = this.eventcounter - 1;
        if (n3 <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.ignorableWhitespace(arrc, n, n2);
        }
    }

    public void init(CoroutineManager coroutineManager, int n, int n2) {
        CoroutineManager coroutineManager2 = coroutineManager;
        if (coroutineManager == null) {
            coroutineManager2 = new CoroutineManager();
        }
        this.fCoroutineManager = coroutineManager2;
        this.fControllerCoroutineID = coroutineManager2.co_joinCoroutineSet(n);
        this.fSourceCoroutineID = coroutineManager2.co_joinCoroutineSet(n2);
        if (this.fControllerCoroutineID != -1 && this.fSourceCoroutineID != -1) {
            this.fNoMoreEvents = false;
            this.eventcounter = this.frequency;
            return;
        }
        throw new RuntimeException(XMLMessages.createXMLMessage("ER_COJOINROUTINESET_FAILED", null));
    }

    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
        DTDHandler dTDHandler = this.clientDTDHandler;
        if (dTDHandler != null) {
            dTDHandler.notationDecl(string, string2, string3);
        }
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        int n;
        ContentHandler contentHandler;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.processingInstruction(string, string2);
        }
    }

    @Override
    public void run() {
        Boolean bl;
        if (this.fXMLReader == null) {
            return;
        }
        if (this.DEBUG) {
            System.out.println("IncrementalSAXSource_Filter parse thread launched");
        }
        Serializable serializable = Boolean.FALSE;
        try {
            this.fXMLReader.parse(this.fXMLReaderInputSource);
            bl = serializable;
        }
        catch (SAXException sAXException) {
            Exception exception = sAXException.getException();
            if (exception instanceof StopException) {
                bl = serializable;
                if (this.DEBUG) {
                    System.out.println("Active IncrementalSAXSource_Filter normal stop exception");
                    bl = serializable;
                }
            } else if (this.DEBUG) {
                PrintStream printStream = System.out;
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Active IncrementalSAXSource_Filter UNEXPECTED SAX exception: ");
                ((StringBuilder)serializable).append(exception);
                printStream.println(((StringBuilder)serializable).toString());
                exception.printStackTrace();
            }
        }
        catch (StopException stopException) {
            bl = serializable;
            if (this.DEBUG) {
                System.out.println("Active IncrementalSAXSource_Filter normal stop exception");
                bl = serializable;
            }
        }
        catch (IOException iOException) {
            // empty catch block
        }
        this.fXMLReader = null;
        try {
            this.fNoMoreEvents = true;
            this.fCoroutineManager.co_exit_to(bl, this.fSourceCoroutineID, this.fControllerCoroutineID);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            noSuchMethodException.printStackTrace(System.err);
            this.fCoroutineManager.co_exit(this.fSourceCoroutineID);
        }
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.clientContentHandler = contentHandler;
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        this.clientDTDHandler = dTDHandler;
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        int n;
        ContentHandler contentHandler;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.setDocumentLocator(locator);
        }
    }

    public void setErrHandler(ErrorHandler errorHandler) {
        this.clientErrorHandler = errorHandler;
    }

    @Override
    public void setLexicalHandler(LexicalHandler lexicalHandler) {
        this.clientLexicalHandler = lexicalHandler;
    }

    public void setReturnFrequency(int n) {
        int n2 = n;
        if (n < 1) {
            n2 = 1;
        }
        this.eventcounter = n2;
        this.frequency = n2;
    }

    public void setXMLReader(XMLReader xMLReader) {
        this.fXMLReader = xMLReader;
        xMLReader.setContentHandler(this);
        xMLReader.setDTDHandler(this);
        xMLReader.setErrorHandler(this);
        try {
            xMLReader.setProperty("http://xml.org/sax/properties/lexical-handler", this);
        }
        catch (SAXNotSupportedException sAXNotSupportedException) {
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            // empty catch block
        }
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
        int n;
        ContentHandler contentHandler;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.skippedEntity(string);
        }
    }

    @Override
    public void startCDATA() throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startCDATA();
        }
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startDTD(string, string2, string3);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        ContentHandler contentHandler;
        int n;
        this.co_entry_pause();
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.startDocument();
        }
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        ContentHandler contentHandler;
        int n;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.startElement(string, string2, string3, attributes);
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
        LexicalHandler lexicalHandler = this.clientLexicalHandler;
        if (lexicalHandler != null) {
            lexicalHandler.startEntity(string);
        }
    }

    @Override
    public void startParse(InputSource inputSource) throws SAXException {
        if (!this.fNoMoreEvents) {
            if (this.fXMLReader != null) {
                this.fXMLReaderInputSource = inputSource;
                ThreadControllerWrapper.runThread(this, -1);
                return;
            }
            throw new SAXException(XMLMessages.createXMLMessage("ER_XMLRDR_NOT_BEFORE_STARTPARSE", null));
        }
        throw new SAXException(XMLMessages.createXMLMessage("ER_INCRSAXSRCFILTER_NOT_RESTARTABLE", null));
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        int n;
        ContentHandler contentHandler;
        this.eventcounter = n = this.eventcounter - 1;
        if (n <= 0) {
            this.co_yield(true);
            this.eventcounter = this.frequency;
        }
        if ((contentHandler = this.clientContentHandler) != null) {
            contentHandler.startPrefixMapping(string, string2);
        }
    }

    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        DTDHandler dTDHandler = this.clientDTDHandler;
        if (dTDHandler != null) {
            dTDHandler.unparsedEntityDecl(string, string2, string3, string4);
        }
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        ErrorHandler errorHandler = this.clientErrorHandler;
        if (errorHandler != null) {
            errorHandler.error(sAXParseException);
        }
    }

    class StopException
    extends RuntimeException {
        static final long serialVersionUID = -1129245796185754956L;

        StopException() {
        }
    }

}

