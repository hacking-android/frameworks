/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.io.IOException;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.processor.TransformerFactoryImpl;
import org.apache.xalan.processor.XSLTElementProcessor;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xml.utils.DOM2Helper;
import org.apache.xml.utils.DOMHelper;
import org.apache.xml.utils.SystemIDResolver;
import org.apache.xml.utils.TreeWalker;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class ProcessorInclude
extends XSLTElementProcessor {
    static final long serialVersionUID = -4570078731972673481L;
    private String m_href = null;

    private String getBaseURIOfIncludedStylesheet(StylesheetHandler object, Source object2) throws TransformerException {
        object = object2 != null && (object2 = object2.getSystemId()) != null ? object2 : SystemIDResolver.getAbsoluteURI(this.getHref(), ((StylesheetHandler)object).getBaseIdentifier());
        return object;
    }

    private Source getSourceFromUriResolver(StylesheetHandler stylesheetHandler) throws TransformerException {
        Source source = null;
        URIResolver uRIResolver = stylesheetHandler.getStylesheetProcessor().getURIResolver();
        if (uRIResolver != null) {
            source = uRIResolver.resolve(this.getHref(), stylesheetHandler.getBaseIdentifier());
        }
        return source;
    }

    public String getHref() {
        return this.m_href;
    }

    protected String getStylesheetInclErr() {
        return "ER_STYLESHEET_INCLUDES_ITSELF";
    }

    protected int getStylesheetType() {
        return 2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void parse(StylesheetHandler var1_1, String var2_2, String var3_9, String var4_13, Attributes var5_14) throws SAXException {
        block25 : {
            block26 : {
                var3_9 = var1_1.getStylesheetProcessor().getURIResolver();
                var2_2 = null;
                if (var3_9 == null) ** GOTO lbl24
                var2_2 = var3_9 = var1_1.peekSourceFromURIResolver();
                if (var3_9 != null) {
                    var2_2 = var3_9;
                    if (var3_9 instanceof DOMSource) {
                        var5_14 = ((DOMSource)var3_9).getNode();
                        var2_2 = var1_1.peekImportURL();
                        if (var2_2 != null) {
                            var1_1.pushBaseIndentifier((String)var2_2);
                        }
                        var4_13 = new DOM2Helper();
                        var3_9 = new TreeWalker(var1_1, (DOMHelper)var4_13, (String)var2_2);
                        try {
                            var3_9.traverse((Node)var5_14);
                            if (var2_2 == null) return;
                            var1_1.popBaseIndentifier();
                        }
                        catch (SAXException var2_3) {
                            var3_9 = new TransformerException(var2_3);
                            throw var3_9;
                        }
                        return;
                    }
                }
lbl24: // 5 sources:
                var3_9 = var2_2;
                if (var2_2 == null) {
                    var2_2 = SystemIDResolver.getAbsoluteURI(this.getHref(), var1_1.getBaseIdentifier());
                    var3_9 = new StreamSource((String)var2_2);
                }
                var3_9 = this.processSource(var1_1, (Source)var3_9);
                var2_2 = null;
                if (var3_9 instanceof SAXSource) {
                    var2_2 = ((SAXSource)var3_9).getXMLReader();
                }
                var4_13 = SAXSource.sourceToInputSource((Source)var3_9);
                var3_9 = var2_2;
                if (var2_2 == null) {
                    try {
                        var5_14 = SAXParserFactory.newInstance();
                        var5_14.setNamespaceAware(true);
                        var6_15 = var1_1.getStylesheetProcessor().isSecureProcessing();
                        if (var6_15) {
                            try {
                                var5_14.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
                            }
                            catch (SAXException var3_10) {
                                // empty catch block
                            }
                        }
                        var2_2 = var3_9 = var5_14.newSAXParser().getXMLReader();
                    }
                    catch (AbstractMethodError var3_11) {
                    }
                    catch (NoSuchMethodError var3_12) {}
                    var3_9 = var2_2;
                    break block26;
                    catch (FactoryConfigurationError var2_6) {
                        var3_9 = new SAXException(var2_6.toString());
                        throw var3_9;
                    }
                    catch (ParserConfigurationException var2_7) {
                        var3_9 = new SAXException(var2_7);
                        throw var3_9;
                    }
                }
            }
            var2_2 = var3_9;
            if (var3_9 == null) {
                var2_2 = XMLReaderFactory.createXMLReader();
            }
            if (var2_2 == null) return;
            var2_2.setContentHandler(var1_1);
            var1_1.pushBaseIndentifier(var4_13.getSystemId());
            try {
                var2_2.parse((InputSource)var4_13);
            }
            catch (Throwable var2_8) {
                var1_1.popBaseIndentifier();
                throw var2_8;
            }
            try {
                var1_1.popBaseIndentifier();
                return;
            }
            catch (TransformerException var2_4) {
            }
            catch (IOException var2_5) {
                break block25;
            }
            var1_1.error(var2_4.getMessage(), var2_4);
            return;
        }
        var1_1.error("ER_IOEXCEPTION", new Object[]{this.getHref()}, var2_5);
    }

    protected Source processSource(StylesheetHandler stylesheetHandler, Source source) {
        return source;
    }

    public void setHref(String string) {
        this.m_href = string;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void startElement(StylesheetHandler var1_1, String var2_2, String var3_5, String var4_6, Attributes var5_7) throws SAXException {
        this.setPropertiesFromAttributes(var1_1, var4_6, var5_7, this);
        var6_8 = this.getSourceFromUriResolver(var1_1);
        var7_9 = this.getBaseURIOfIncludedStylesheet(var1_1, var6_8);
        if (var1_1.importStackContains(var7_9)) ** GOTO lbl29
        var1_1.pushImportURL(var7_9);
        var1_1.pushImportSource(var6_8);
        var8_10 = var1_1.getStylesheetType();
        var1_1.setStylesheetType(this.getStylesheetType());
        var1_1.pushNewNamespaceSupport();
        this.parse(var1_1, (String)var2_2, var3_5, var4_6, var5_7);
        {
            catch (Throwable var2_3) {
                var1_1.setStylesheetType(var8_10);
                var1_1.popImportURL();
                var1_1.popImportSource();
                var1_1.popNamespaceSupport();
                throw var2_3;
            }
        }
        try {
            var1_1.setStylesheetType(var8_10);
            var1_1.popImportURL();
            var1_1.popImportSource();
            var1_1.popNamespaceSupport();
            return;
lbl29: // 1 sources:
            var2_2 = new SAXException(XSLMessages.createMessage(this.getStylesheetInclErr(), new Object[]{var7_9}));
            throw var2_2;
        }
        catch (TransformerException var2_4) {
            var1_1.error(var2_4.getMessage(), var2_4);
        }
    }
}

