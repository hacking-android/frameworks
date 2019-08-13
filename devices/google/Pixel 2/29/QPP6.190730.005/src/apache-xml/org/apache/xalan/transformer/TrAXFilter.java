/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.transformer.TransformerImpl;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

public class TrAXFilter
extends XMLFilterImpl {
    private Templates m_templates;
    private TransformerImpl m_transformer;

    public TrAXFilter(Templates templates) throws TransformerConfigurationException {
        this.m_templates = templates;
        this.m_transformer = (TransformerImpl)templates.newTransformer();
    }

    private void setupParse() {
        XMLReader xMLReader = this.getParent();
        if (xMLReader != null) {
            xMLReader.setContentHandler(this.m_transformer.getInputContentHandler());
            xMLReader.setEntityResolver(this);
            xMLReader.setDTDHandler(this);
            xMLReader.setErrorHandler(this);
            return;
        }
        throw new NullPointerException(XSLMessages.createMessage("ER_NO_PARENT_FOR_FILTER", null));
    }

    public TransformerImpl getTransformer() {
        return this.m_transformer;
    }

    @Override
    public void parse(String string) throws SAXException, IOException {
        this.parse(new InputSource(string));
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void parse(InputSource object) throws SAXException, IOException {
        block13 : {
            block12 : {
                if (this.getParent() != null) break block12;
                XMLReader xMLReader = null;
                try {
                    XMLReader xMLReader2;
                    SAXParserFactory sAXParserFactory = SAXParserFactory.newInstance();
                    sAXParserFactory.setNamespaceAware(true);
                    boolean bl = this.m_transformer.getStylesheet().isSecureProcessing();
                    if (bl) {
                        try {
                            sAXParserFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                        }
                        catch (SAXException sAXException) {
                            // empty catch block
                        }
                    }
                    xMLReader = xMLReader2 = sAXParserFactory.newSAXParser().getXMLReader();
                }
                catch (AbstractMethodError abstractMethodError) {
                }
                catch (NoSuchMethodError noSuchMethodError) {}
                if (xMLReader == null) {
                    xMLReader = XMLReaderFactory.createXMLReader();
                }
                try {
                    xMLReader.setFeature("http://xml.org/sax/features/namespace-prefixes", true);
                }
                catch (SAXException sAXException) {
                    // empty catch block
                }
                this.setParent(xMLReader);
                break block13;
                catch (FactoryConfigurationError factoryConfigurationError) {
                    throw new SAXException(factoryConfigurationError.toString());
                }
                catch (ParserConfigurationException parserConfigurationException) {
                    throw new SAXException(parserConfigurationException);
                }
            }
            this.setupParse();
        }
        if (this.m_transformer.getContentHandler() == null) throw new SAXException(XSLMessages.createMessage("ER_CANNOT_CALL_PARSE", null));
        this.getParent().parse((InputSource)object);
        object = this.m_transformer.getExceptionThrown();
        if (object == null) return;
        if (!(object instanceof SAXException)) throw new SAXException((Exception)object);
        throw (SAXException)object;
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        this.m_transformer.setContentHandler(contentHandler);
    }

    public void setErrorListener(ErrorListener errorListener) {
        this.m_transformer.setErrorListener(errorListener);
    }

    @Override
    public void setParent(XMLReader xMLReader) {
        super.setParent(xMLReader);
        if (xMLReader.getContentHandler() != null) {
            this.setContentHandler(xMLReader.getContentHandler());
        }
        this.setupParse();
    }
}

