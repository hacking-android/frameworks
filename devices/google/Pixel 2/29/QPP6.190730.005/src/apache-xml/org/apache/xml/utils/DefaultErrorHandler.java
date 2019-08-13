/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xml.utils.WrappedRuntimeException;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DefaultErrorHandler
implements ErrorHandler,
ErrorListener {
    PrintWriter m_pw;
    boolean m_throwExceptionOnError = true;

    public DefaultErrorHandler() {
        this(true);
    }

    public DefaultErrorHandler(PrintStream printStream) {
        this.m_pw = new PrintWriter(printStream, true);
    }

    public DefaultErrorHandler(PrintWriter printWriter) {
        this.m_pw = printWriter;
    }

    public DefaultErrorHandler(boolean bl) {
        this.m_throwExceptionOnError = bl;
    }

    public static void ensureLocationSet(TransformerException transformerException) {
        SAXSourceLocator sAXSourceLocator = null;
        Throwable throwable = transformerException;
        do {
            SourceLocator sourceLocator;
            if (throwable instanceof SAXParseException) {
                sourceLocator = new SAXSourceLocator((SAXParseException)throwable);
            } else {
                sourceLocator = sAXSourceLocator;
                if (throwable instanceof TransformerException) {
                    SourceLocator sourceLocator2 = throwable.getLocator();
                    sourceLocator = sAXSourceLocator;
                    if (sourceLocator2 != null) {
                        sourceLocator = sourceLocator2;
                    }
                }
            }
            throwable = throwable instanceof TransformerException ? throwable.getCause() : (throwable instanceof SAXException ? ((SAXException)throwable).getException() : null);
            if (throwable == null) {
                transformerException.setLocator(sourceLocator);
                return;
            }
            sAXSourceLocator = sourceLocator;
        } while (true);
    }

    public static void printLocation(PrintStream printStream, TransformerException transformerException) {
        DefaultErrorHandler.printLocation(new PrintWriter(printStream), (Throwable)transformerException);
    }

    public static void printLocation(PrintStream printStream, SAXParseException sAXParseException) {
        DefaultErrorHandler.printLocation(new PrintWriter(printStream), (Throwable)sAXParseException);
    }

    public static void printLocation(PrintWriter printWriter, Throwable object) {
        Serializable serializable = null;
        do {
            SourceLocator sourceLocator;
            if (object instanceof SAXParseException) {
                sourceLocator = new SAXSourceLocator((SAXParseException)object);
            } else {
                sourceLocator = serializable;
                if (object instanceof TransformerException) {
                    SourceLocator sourceLocator2 = ((TransformerException)object).getLocator();
                    sourceLocator = serializable;
                    if (sourceLocator2 != null) {
                        sourceLocator = sourceLocator2;
                    }
                }
            }
            object = object instanceof TransformerException ? ((TransformerException)object).getCause() : (object instanceof WrappedRuntimeException ? ((WrappedRuntimeException)object).getException() : (object instanceof SAXException ? ((SAXException)object).getException() : null));
            if (object == null) {
                if (sourceLocator != null) {
                    object = sourceLocator.getPublicId() != null ? sourceLocator.getPublicId() : (sourceLocator.getSystemId() != null ? sourceLocator.getSystemId() : XMLMessages.createXMLMessage("ER_SYSTEMID_UNKNOWN", null));
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append((String)object);
                    ((StringBuilder)serializable).append("; ");
                    ((StringBuilder)serializable).append(XMLMessages.createXMLMessage("line", null));
                    ((StringBuilder)serializable).append(sourceLocator.getLineNumber());
                    ((StringBuilder)serializable).append("; ");
                    ((StringBuilder)serializable).append(XMLMessages.createXMLMessage("column", null));
                    ((StringBuilder)serializable).append(sourceLocator.getColumnNumber());
                    ((StringBuilder)serializable).append("; ");
                    printWriter.print(((StringBuilder)serializable).toString());
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("(");
                    ((StringBuilder)object).append(XMLMessages.createXMLMessage("ER_LOCATION_UNKNOWN", null));
                    ((StringBuilder)object).append(")");
                    printWriter.print(((StringBuilder)object).toString());
                }
                return;
            }
            serializable = sourceLocator;
        } while (true);
    }

    @Override
    public void error(TransformerException transformerException) throws TransformerException {
        if (!this.m_throwExceptionOnError) {
            PrintWriter printWriter = this.getErrorWriter();
            DefaultErrorHandler.printLocation(printWriter, (Throwable)transformerException);
            printWriter.println(transformerException.getMessage());
            return;
        }
        throw transformerException;
    }

    @Override
    public void error(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    @Override
    public void fatalError(TransformerException transformerException) throws TransformerException {
        if (!this.m_throwExceptionOnError) {
            PrintWriter printWriter = this.getErrorWriter();
            DefaultErrorHandler.printLocation(printWriter, (Throwable)transformerException);
            printWriter.println(transformerException.getMessage());
            return;
        }
        throw transformerException;
    }

    @Override
    public void fatalError(SAXParseException sAXParseException) throws SAXException {
        throw sAXParseException;
    }

    public PrintWriter getErrorWriter() {
        if (this.m_pw == null) {
            this.m_pw = new PrintWriter(System.err, true);
        }
        return this.m_pw;
    }

    @Override
    public void warning(TransformerException transformerException) throws TransformerException {
        PrintWriter printWriter = this.getErrorWriter();
        DefaultErrorHandler.printLocation(printWriter, (Throwable)transformerException);
        printWriter.println(transformerException.getMessage());
    }

    @Override
    public void warning(SAXParseException sAXParseException) throws SAXException {
        PrintWriter printWriter = this.getErrorWriter();
        DefaultErrorHandler.printLocation(printWriter, (Throwable)sAXParseException);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Parser warning: ");
        stringBuilder.append(sAXParseException.getMessage());
        printWriter.println(stringBuilder.toString());
    }
}

