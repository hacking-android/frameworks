/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import org.ccil.cowan.tagsoup.AttributesImpl;
import org.ccil.cowan.tagsoup.AutoDetector;
import org.ccil.cowan.tagsoup.Element;
import org.ccil.cowan.tagsoup.ElementType;
import org.ccil.cowan.tagsoup.HTMLScanner;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.ScanHandler;
import org.ccil.cowan.tagsoup.Scanner;
import org.ccil.cowan.tagsoup.Schema;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.DefaultHandler;

public class Parser
extends DefaultHandler
implements ScanHandler,
XMLReader,
LexicalHandler {
    public static final String CDATAElementsFeature = "http://www.ccil.org/~cowan/tagsoup/features/cdata-elements";
    private static boolean DEFAULT_BOGONS_EMPTY = false;
    private static boolean DEFAULT_CDATA_ELEMENTS = false;
    private static boolean DEFAULT_DEFAULT_ATTRIBUTES = false;
    private static boolean DEFAULT_IGNORABLE_WHITESPACE = false;
    private static boolean DEFAULT_IGNORE_BOGONS = false;
    private static boolean DEFAULT_NAMESPACES = false;
    private static boolean DEFAULT_RESTART_ELEMENTS = false;
    private static boolean DEFAULT_ROOT_BOGONS = false;
    private static boolean DEFAULT_TRANSLATE_COLONS = false;
    public static final String XML11Feature = "http://xml.org/sax/features/xml-1.1";
    public static final String autoDetectorProperty = "http://www.ccil.org/~cowan/tagsoup/properties/auto-detector";
    public static final String bogonsEmptyFeature = "http://www.ccil.org/~cowan/tagsoup/features/bogons-empty";
    public static final String defaultAttributesFeature = "http://www.ccil.org/~cowan/tagsoup/features/default-attributes";
    private static char[] etagchars;
    public static final String externalGeneralEntitiesFeature = "http://xml.org/sax/features/external-general-entities";
    public static final String externalParameterEntitiesFeature = "http://xml.org/sax/features/external-parameter-entities";
    public static final String ignorableWhitespaceFeature = "http://www.ccil.org/~cowan/tagsoup/features/ignorable-whitespace";
    public static final String ignoreBogonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons";
    public static final String isStandaloneFeature = "http://xml.org/sax/features/is-standalone";
    private static String legal;
    public static final String lexicalHandlerParameterEntitiesFeature = "http://xml.org/sax/features/lexical-handler/parameter-entities";
    public static final String lexicalHandlerProperty = "http://xml.org/sax/properties/lexical-handler";
    public static final String namespacePrefixesFeature = "http://xml.org/sax/features/namespace-prefixes";
    public static final String namespacesFeature = "http://xml.org/sax/features/namespaces";
    public static final String resolveDTDURIsFeature = "http://xml.org/sax/features/resolve-dtd-uris";
    public static final String restartElementsFeature = "http://www.ccil.org/~cowan/tagsoup/features/restart-elements";
    public static final String rootBogonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/root-bogons";
    public static final String scannerProperty = "http://www.ccil.org/~cowan/tagsoup/properties/scanner";
    public static final String schemaProperty = "http://www.ccil.org/~cowan/tagsoup/properties/schema";
    public static final String stringInterningFeature = "http://xml.org/sax/features/string-interning";
    public static final String translateColonsFeature = "http://www.ccil.org/~cowan/tagsoup/features/translate-colons";
    public static final String unicodeNormalizationCheckingFeature = "http://xml.org/sax/features/unicode-normalization-checking";
    public static final String useAttributes2Feature = "http://xml.org/sax/features/use-attributes2";
    public static final String useEntityResolver2Feature = "http://xml.org/sax/features/use-entity-resolver2";
    public static final String useLocator2Feature = "http://xml.org/sax/features/use-locator2";
    public static final String validationFeature = "http://xml.org/sax/features/validation";
    public static final String xmlnsURIsFeature = "http://xml.org/sax/features/xmlns-uris";
    private boolean CDATAElements = DEFAULT_CDATA_ELEMENTS;
    private boolean bogonsEmpty = DEFAULT_BOGONS_EMPTY;
    private boolean defaultAttributes = DEFAULT_DEFAULT_ATTRIBUTES;
    private boolean ignorableWhitespace = DEFAULT_IGNORABLE_WHITESPACE;
    private boolean ignoreBogons = DEFAULT_IGNORE_BOGONS;
    private boolean namespaces = DEFAULT_NAMESPACES;
    private boolean restartElements = DEFAULT_RESTART_ELEMENTS;
    private boolean rootBogons = DEFAULT_ROOT_BOGONS;
    private String theAttributeName;
    private AutoDetector theAutoDetector;
    private char[] theCommentBuffer;
    private ContentHandler theContentHandler = this;
    private DTDHandler theDTDHandler = this;
    private boolean theDoctypeIsPresent;
    private String theDoctypeName;
    private String theDoctypePublicId;
    private String theDoctypeSystemId;
    private int theEntity;
    private EntityResolver theEntityResolver = this;
    private ErrorHandler theErrorHandler = this;
    private HashMap theFeatures = new HashMap();
    private LexicalHandler theLexicalHandler = this;
    private Element theNewElement;
    private Element thePCDATA;
    private String thePITarget;
    private Element theSaved;
    private Scanner theScanner;
    private Schema theSchema;
    private Element theStack;
    private boolean translateColons = DEFAULT_TRANSLATE_COLONS;
    private boolean virginStack;

    static {
        DEFAULT_NAMESPACES = true;
        DEFAULT_IGNORE_BOGONS = false;
        DEFAULT_BOGONS_EMPTY = false;
        DEFAULT_ROOT_BOGONS = true;
        DEFAULT_DEFAULT_ATTRIBUTES = true;
        DEFAULT_TRANSLATE_COLONS = false;
        DEFAULT_RESTART_ELEMENTS = true;
        DEFAULT_IGNORABLE_WHITESPACE = false;
        DEFAULT_CDATA_ELEMENTS = true;
        etagchars = new char[]{'<', '/', '>'};
        legal = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-'()+,./:=?;!*#@$_%";
    }

    public Parser() {
        this.theFeatures.put(namespacesFeature, Parser.truthValue(DEFAULT_NAMESPACES));
        this.theFeatures.put(namespacePrefixesFeature, Boolean.FALSE);
        this.theFeatures.put(externalGeneralEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(externalParameterEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(isStandaloneFeature, Boolean.FALSE);
        this.theFeatures.put(lexicalHandlerParameterEntitiesFeature, Boolean.FALSE);
        this.theFeatures.put(resolveDTDURIsFeature, Boolean.TRUE);
        this.theFeatures.put(stringInterningFeature, Boolean.TRUE);
        this.theFeatures.put(useAttributes2Feature, Boolean.FALSE);
        this.theFeatures.put(useLocator2Feature, Boolean.FALSE);
        this.theFeatures.put(useEntityResolver2Feature, Boolean.FALSE);
        this.theFeatures.put(validationFeature, Boolean.FALSE);
        this.theFeatures.put(xmlnsURIsFeature, Boolean.FALSE);
        this.theFeatures.put(xmlnsURIsFeature, Boolean.FALSE);
        this.theFeatures.put(XML11Feature, Boolean.FALSE);
        this.theFeatures.put(ignoreBogonsFeature, Parser.truthValue(DEFAULT_IGNORE_BOGONS));
        this.theFeatures.put(bogonsEmptyFeature, Parser.truthValue(DEFAULT_BOGONS_EMPTY));
        this.theFeatures.put(rootBogonsFeature, Parser.truthValue(DEFAULT_ROOT_BOGONS));
        this.theFeatures.put(defaultAttributesFeature, Parser.truthValue(DEFAULT_DEFAULT_ATTRIBUTES));
        this.theFeatures.put(translateColonsFeature, Parser.truthValue(DEFAULT_TRANSLATE_COLONS));
        this.theFeatures.put(restartElementsFeature, Parser.truthValue(DEFAULT_RESTART_ELEMENTS));
        this.theFeatures.put(ignorableWhitespaceFeature, Parser.truthValue(DEFAULT_IGNORABLE_WHITESPACE));
        this.theFeatures.put(CDATAElementsFeature, Parser.truthValue(DEFAULT_CDATA_ELEMENTS));
        this.theNewElement = null;
        this.theAttributeName = null;
        this.theDoctypeIsPresent = false;
        this.theDoctypePublicId = null;
        this.theDoctypeSystemId = null;
        this.theDoctypeName = null;
        this.thePITarget = null;
        this.theStack = null;
        this.theSaved = null;
        this.thePCDATA = null;
        this.theEntity = 0;
        this.virginStack = true;
        this.theCommentBuffer = new char[2000];
    }

    private String cleanPublicid(String string) {
        if (string == null) {
            return null;
        }
        int n = string.length();
        StringBuffer stringBuffer = new StringBuffer(n);
        boolean bl = true;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (legal.indexOf(c) != -1) {
                stringBuffer.append(c);
                bl = false;
                continue;
            }
            if (bl) continue;
            stringBuffer.append(' ');
            bl = true;
        }
        return stringBuffer.toString().trim();
    }

    private String expandEntities(String string) {
        int n = -1;
        int n2 = string.length();
        char[] arrc = new char[n2];
        int n3 = 0;
        for (int i = 0; i < n2; ++i) {
            char c = string.charAt(i);
            int n4 = n3 + 1;
            arrc[n3] = c;
            if (c == '&' && n == -1) {
                n = n4;
            } else if (n != -1 && !Character.isLetter(c) && !Character.isDigit(c) && c != '#') {
                if (c == ';') {
                    n3 = this.lookupEntity(arrc, n, n4 - n - 1);
                    if (n3 > 65535) {
                        n4 = n3 - 65536;
                        arrc[n - 1] = (char)((n4 >> 10) + 55296);
                        arrc[n] = (char)((n4 & 1023) + 56320);
                        n4 = n + 1;
                    } else if (n3 != 0) {
                        arrc[n - 1] = (char)n3;
                        n4 = n;
                    }
                    n = -1;
                } else {
                    n = -1;
                }
            }
            n3 = n4;
        }
        return new String(arrc, 0, n3);
    }

    private boolean foreign(String string, String string2) {
        boolean bl = !string.equals("") && !string2.equals("") && !string2.equals(this.theSchema.getURI());
        return bl;
    }

    private InputStream getInputStream(String charSequence, String string) throws IOException, SAXException {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(System.getProperty("user.dir"));
        ((StringBuilder)charSequence).append("/.");
        return new URL(new URL("file", "", ((StringBuilder)charSequence).toString()), string).openConnection().getInputStream();
    }

    private Reader getReader(InputSource object) throws SAXException, IOException {
        Reader reader = ((InputSource)object).getCharacterStream();
        Closeable closeable = ((InputSource)object).getByteStream();
        String string = ((InputSource)object).getEncoding();
        String string2 = ((InputSource)object).getPublicId();
        String string3 = ((InputSource)object).getSystemId();
        object = reader;
        if (reader == null) {
            object = closeable;
            if (closeable == null) {
                object = this.getInputStream(string2, string3);
            }
            if (string == null) {
                object = this.theAutoDetector.autoDetectingReader((InputStream)object);
            } else {
                try {
                    closeable = new InputStreamReader((InputStream)object, string);
                    object = closeable;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    object = new InputStreamReader((InputStream)object);
                }
            }
        }
        return object;
    }

    private int lookupEntity(char[] arrc, int n, int n2) {
        if (n2 < 1) {
            return 0;
        }
        if (arrc[n] == '#') {
            if (n2 > 1 && (arrc[n + 1] == 'x' || arrc[n + 1] == 'X')) {
                try {
                    String string = new String(arrc, n + 2, n2 - 2);
                    n = Integer.parseInt(string, 16);
                    return n;
                }
                catch (NumberFormatException numberFormatException) {
                    return 0;
                }
            }
            try {
                String string = new String(arrc, n + 1, n2 - 1);
                n = Integer.parseInt(string, 10);
                return n;
            }
            catch (NumberFormatException numberFormatException) {
                return 0;
            }
        }
        return this.theSchema.getEntity(new String(arrc, n, n2));
    }

    private String makeName(char[] arrc, int n, int n2) {
        StringBuffer stringBuffer = new StringBuffer(n2 + 2);
        boolean bl = false;
        char c = '\u0001';
        int n3 = n;
        do {
            boolean bl2;
            char c2 = '_';
            if (n2 <= 0) break;
            char c3 = arrc[n3];
            if (!Character.isLetter(c3) && c3 != '_') {
                if (!Character.isDigit(c3) && c3 != '-' && c3 != '.') {
                    bl2 = bl;
                    n = c;
                    if (c3 == ':') {
                        bl2 = bl;
                        n = c;
                        if (!bl) {
                            bl2 = true;
                            if (c != '\u0000') {
                                stringBuffer.append('_');
                            }
                            n = 1;
                            c3 = this.translateColons ? c2 : (c = c3);
                            stringBuffer.append(c3);
                        }
                    }
                } else {
                    if (c != '\u0000') {
                        stringBuffer.append('_');
                    }
                    n = 0;
                    stringBuffer.append(c3);
                    bl2 = bl;
                }
            } else {
                n = 0;
                stringBuffer.append(c3);
                bl2 = bl;
            }
            ++n3;
            --n2;
            bl = bl2;
            c = n;
        } while (true);
        n = stringBuffer.length();
        if (n == 0 || stringBuffer.charAt(n - 1) == ':') {
            stringBuffer.append('_');
        }
        return stringBuffer.toString().intern();
    }

    private void pop() throws SAXException {
        Object object = this.theStack;
        if (object == null) {
            return;
        }
        String string = ((Element)object).name();
        Object object2 = this.theStack.localName();
        object = this.theStack.namespace();
        String string2 = this.prefixOf(string);
        if (!this.namespaces) {
            object2 = "";
            object = "";
        }
        this.theContentHandler.endElement((String)object, (String)object2, string);
        if (this.foreign(string2, (String)object)) {
            this.theContentHandler.endPrefixMapping(string2);
        }
        object2 = this.theStack.atts();
        for (int i = object2.getLength() - 1; i >= 0; --i) {
            object = object2.getURI(i);
            string2 = this.prefixOf(object2.getQName(i));
            if (!this.foreign(string2, (String)object)) continue;
            this.theContentHandler.endPrefixMapping(string2);
        }
        this.theStack = this.theStack.next();
    }

    private String prefixOf(String string) {
        int n = string.indexOf(58);
        String string2 = "";
        if (n != -1) {
            string2 = string.substring(0, n);
        }
        return string2;
    }

    private void push(Element element) throws SAXException {
        String string = element.name();
        String string2 = element.localName();
        String string3 = element.namespace();
        String string4 = this.prefixOf(string);
        element.clean();
        if (!this.namespaces) {
            string2 = "";
            string3 = "";
        }
        if (this.virginStack && string2.equalsIgnoreCase(this.theDoctypeName)) {
            try {
                this.theEntityResolver.resolveEntity(this.theDoctypePublicId, this.theDoctypeSystemId);
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        if (this.foreign(string4, string3)) {
            this.theContentHandler.startPrefixMapping(string4, string3);
        }
        AttributesImpl attributesImpl = element.atts();
        int n = attributesImpl.getLength();
        for (int i = 0; i < n; ++i) {
            String string5 = attributesImpl.getURI(i);
            string4 = this.prefixOf(attributesImpl.getQName(i));
            if (!this.foreign(string4, string5)) continue;
            this.theContentHandler.startPrefixMapping(string4, string5);
        }
        this.theContentHandler.startElement(string3, string2, string, element.atts());
        element.setNext(this.theStack);
        this.theStack = element;
        this.virginStack = false;
        if (this.CDATAElements && (this.theStack.flags() & 2) != 0) {
            this.theScanner.startCDATA();
        }
    }

    private void rectify(Element object) throws SAXException {
        do {
            Element element;
            Object object2;
            for (element = this.theStack; element != null && !element.canContain((Element)object); element = element.next()) {
            }
            if (element != null || (object2 = ((Element)object).parent()) == null) {
                if (element == null) {
                    return;
                }
                do {
                    Element element2 = this.theStack;
                    object2 = object;
                    if (element2 == element) break;
                    object2 = object;
                    if (element2 == null) break;
                    object2 = object;
                    if (element2.next() == null) break;
                    if (this.theStack.next().next() == null) {
                        object2 = object;
                        break;
                    }
                    this.restartablyPop();
                } while (true);
                while (object2 != null) {
                    object = ((Element)object2).next();
                    if (!((Element)object2).name().equals("<pcdata>")) {
                        this.push((Element)object2);
                    }
                    object2 = object;
                    this.restart((Element)object2);
                }
                this.theNewElement = null;
                return;
            }
            object2 = new Element((ElementType)object2, this.defaultAttributes);
            ((Element)object2).setNext((Element)object);
            object = object2;
        } while (true);
    }

    private void restart(Element element) throws SAXException {
        Element element2;
        while ((element2 = this.theSaved) != null && this.theStack.canContain(element2) && (element == null || this.theSaved.canContain(element))) {
            element2 = this.theSaved.next();
            this.push(this.theSaved);
            this.theSaved = element2;
        }
    }

    private void restartablyPop() throws SAXException {
        Element element = this.theStack;
        this.pop();
        if (this.restartElements && (element.flags() & 1) != 0) {
            element.anonymize();
            element.setNext(this.theSaved);
            this.theSaved = element;
        }
    }

    private void setup() {
        if (this.theSchema == null) {
            this.theSchema = new HTMLSchema();
        }
        if (this.theScanner == null) {
            this.theScanner = new HTMLScanner();
        }
        if (this.theAutoDetector == null) {
            this.theAutoDetector = new AutoDetector(){

                @Override
                public Reader autoDetectingReader(InputStream inputStream) {
                    return new InputStreamReader(inputStream);
                }
            };
        }
        this.theStack = new Element(this.theSchema.getElementType("<root>"), this.defaultAttributes);
        this.thePCDATA = new Element(this.theSchema.getElementType("<pcdata>"), this.defaultAttributes);
        this.theNewElement = null;
        this.theAttributeName = null;
        this.thePITarget = null;
        this.theSaved = null;
        this.theEntity = 0;
        this.virginStack = true;
        this.theDoctypeSystemId = null;
        this.theDoctypePublicId = null;
        this.theDoctypeName = null;
    }

    private static String[] split(String string) throws IllegalArgumentException {
        int n;
        if ((string = string.trim()).length() == 0) {
            return new String[0];
        }
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = string.length();
        for (n = 0; n < n6; ++n) {
            char c = string.charAt(n);
            int n7 = 1;
            int n8 = 1;
            if (n4 == 0 && c == '\'' && n5 != 92) {
                n5 = n3 == 0 ? n8 : 0;
                n3 = n5;
                n5 = n2;
                n7 = n3;
                n8 = n4;
                if (n2 < 0) {
                    n5 = n;
                    n7 = n3;
                    n8 = n4;
                }
            } else if (n3 == 0 && c == '\"' && n5 != 92) {
                n5 = n4 == 0 ? n7 : 0;
                n4 = n5;
                n5 = n2;
                n7 = n3;
                n8 = n4;
                if (n2 < 0) {
                    n5 = n;
                    n7 = n3;
                    n8 = n4;
                }
            } else {
                n5 = n2;
                n7 = n3;
                n8 = n4;
                if (n3 == 0) {
                    n5 = n2;
                    n7 = n3;
                    n8 = n4;
                    if (n4 == 0) {
                        if (Character.isWhitespace(c)) {
                            if (n2 >= 0) {
                                arrayList.add(string.substring(n2, n));
                            }
                            n5 = -1;
                            n7 = n3;
                            n8 = n4;
                        } else {
                            n5 = n2;
                            n7 = n3;
                            n8 = n4;
                            if (n2 < 0) {
                                n5 = n2;
                                n7 = n3;
                                n8 = n4;
                                if (c != ' ') {
                                    n5 = n;
                                    n8 = n4;
                                    n7 = n3;
                                }
                            }
                        }
                    }
                }
            }
            char c2 = c;
            n2 = n5;
            n3 = n7;
            n4 = n8;
            n5 = c2;
        }
        arrayList.add(string.substring(n2, n));
        return arrayList.toArray(new String[0]);
    }

    private static String trimquotes(String string) {
        String string2;
        block5 : {
            block6 : {
                if (string == null) {
                    return string;
                }
                int n = string.length();
                if (n == 0) {
                    return string;
                }
                char c = string.charAt(0);
                string2 = string;
                if (c != string.charAt(n - 1)) break block5;
                if (c == '\'') break block6;
                string2 = string;
                if (c != '\"') break block5;
            }
            string2 = string.substring(1, string.length() - 1);
        }
        return string2;
    }

    private static Boolean truthValue(boolean bl) {
        Boolean bl2 = bl ? Boolean.TRUE : Boolean.FALSE;
        return bl2;
    }

    @Override
    public void adup(char[] object, int n, int n2) throws SAXException {
        String string;
        object = this.theNewElement;
        if (object != null && (string = this.theAttributeName) != null) {
            ((Element)object).setAttribute(string, null, string);
            this.theAttributeName = null;
            return;
        }
    }

    @Override
    public void aname(char[] arrc, int n, int n2) throws SAXException {
        if (this.theNewElement == null) {
            return;
        }
        this.theAttributeName = this.makeName(arrc, n, n2).toLowerCase(Locale.ROOT);
    }

    @Override
    public void aval(char[] object, int n, int n2) throws SAXException {
        if (this.theNewElement != null && this.theAttributeName != null) {
            object = this.expandEntities(new String((char[])object, n, n2));
            this.theNewElement.setAttribute(this.theAttributeName, null, (String)object);
            this.theAttributeName = null;
            return;
        }
    }

    @Override
    public void cdsect(char[] arrc, int n, int n2) throws SAXException {
        this.theLexicalHandler.startCDATA();
        this.pcdata(arrc, n, n2);
        this.theLexicalHandler.endCDATA();
    }

    @Override
    public void cmnt(char[] arrc, int n, int n2) throws SAXException {
        this.theLexicalHandler.comment(arrc, n, n2);
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void decl(char[] object, int n, int n2) throws SAXException {
        object = new String((char[])object, n, n2);
        Object object2 = null;
        Object var5_6 = null;
        String string = null;
        String[] arrstring = Parser.split((String)object);
        Object object3 = object2;
        object = var5_6;
        Object object4 = string;
        if (arrstring.length > 0) {
            object3 = object2;
            object = var5_6;
            object4 = string;
            if ("DOCTYPE".equalsIgnoreCase(arrstring[0])) {
                if (this.theDoctypeIsPresent) {
                    return;
                }
                this.theDoctypeIsPresent = true;
                object3 = object2;
                object = var5_6;
                object4 = string;
                if (arrstring.length > 1) {
                    object2 = arrstring[1];
                    if (arrstring.length > 3 && "SYSTEM".equals(arrstring[2])) {
                        object = arrstring[3];
                        object3 = object2;
                        object4 = string;
                    } else {
                        object3 = object2;
                        object = var5_6;
                        object4 = string;
                        if (arrstring.length > 3) {
                            object3 = object2;
                            object = var5_6;
                            object4 = string;
                            if ("PUBLIC".equals(arrstring[2])) {
                                object4 = arrstring[3];
                                if (arrstring.length > 4) {
                                    object = arrstring[4];
                                    object3 = object2;
                                } else {
                                    object = "";
                                    object3 = object2;
                                }
                            }
                        }
                    }
                }
            }
        }
        object4 = Parser.trimquotes(object4);
        object = Parser.trimquotes((String)object);
        if (object3 != null) {
            object4 = this.cleanPublicid((String)object4);
            this.theLexicalHandler.startDTD((String)object3, (String)object4, (String)object);
            this.theLexicalHandler.endDTD();
            this.theDoctypeName = object3;
            this.theDoctypePublicId = object4;
            object3 = this.theScanner;
            if (object3 instanceof Locator) {
                this.theDoctypeSystemId = ((Locator)object3).getSystemId();
                try {
                    object3 = new URL(this.theDoctypeSystemId);
                    object4 = new URL((URL)object3, (String)object);
                    this.theDoctypeSystemId = ((URL)object4).toString();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    @Override
    public void entity(char[] arrc, int n, int n2) throws SAXException {
        this.theEntity = this.lookupEntity(arrc, n, n2);
    }

    @Override
    public void eof(char[] arrc, int n, int n2) throws SAXException {
        if (this.virginStack) {
            this.rectify(this.thePCDATA);
        }
        while (this.theStack.next() != null) {
            this.pop();
        }
        if (!this.theSchema.getURI().equals("")) {
            this.theContentHandler.endPrefixMapping(this.theSchema.getPrefix());
        }
        this.theContentHandler.endDocument();
    }

    @Override
    public void etag(char[] arrc, int n, int n2) throws SAXException {
        if (this.etag_cdata(arrc, n, n2)) {
            return;
        }
        this.etag_basic(arrc, n, n2);
    }

    public void etag_basic(char[] object, int n, int n2) throws SAXException {
        Element element;
        this.theNewElement = null;
        if (n2 != 0) {
            object = this.makeName((char[])object, n, n2);
            if ((object = this.theSchema.getElementType((String)object)) == null) {
                return;
            }
            object = ((ElementType)object).name();
        } else {
            object = this.theStack.name();
        }
        n = 0;
        for (element = this.theStack; element != null && !element.name().equals(object); element = element.next()) {
            if ((element.flags() & 4) == 0) continue;
            n = 1;
        }
        if (element == null) {
            return;
        }
        if (element.next() != null && element.next().next() != null) {
            if (n != 0) {
                element.preclose();
            } else {
                while (this.theStack != element) {
                    this.restartablyPop();
                }
                this.pop();
            }
            while (this.theStack.isPreclosed()) {
                this.pop();
            }
            this.restart(null);
            return;
        }
    }

    public boolean etag_cdata(char[] arrc, int n, int n2) throws SAXException {
        String string = this.theStack.name();
        if (this.CDATAElements && (this.theStack.flags() & 2) != 0) {
            boolean bl = n2 == string.length();
            boolean bl2 = bl;
            if (bl) {
                int n3 = 0;
                do {
                    bl2 = bl;
                    if (n3 >= n2) break;
                    if (Character.toLowerCase(arrc[n + n3]) != Character.toLowerCase(string.charAt(n3))) {
                        bl2 = false;
                        break;
                    }
                    ++n3;
                } while (true);
            }
            if (!bl2) {
                this.theContentHandler.characters(etagchars, 0, 2);
                this.theContentHandler.characters(arrc, n, n2);
                this.theContentHandler.characters(etagchars, 2, 1);
                this.theScanner.startCDATA();
                return true;
            }
        }
        return false;
    }

    @Override
    public ContentHandler getContentHandler() {
        ContentHandler contentHandler;
        ContentHandler contentHandler2 = contentHandler = this.theContentHandler;
        if (contentHandler == this) {
            contentHandler2 = null;
        }
        return contentHandler2;
    }

    @Override
    public DTDHandler getDTDHandler() {
        DTDHandler dTDHandler;
        DTDHandler dTDHandler2 = dTDHandler = this.theDTDHandler;
        if (dTDHandler == this) {
            dTDHandler2 = null;
        }
        return dTDHandler2;
    }

    @Override
    public int getEntity() {
        return this.theEntity;
    }

    @Override
    public EntityResolver getEntityResolver() {
        EntityResolver entityResolver;
        EntityResolver entityResolver2 = entityResolver = this.theEntityResolver;
        if (entityResolver == this) {
            entityResolver2 = null;
        }
        return entityResolver2;
    }

    @Override
    public ErrorHandler getErrorHandler() {
        ErrorHandler errorHandler;
        ErrorHandler errorHandler2 = errorHandler = this.theErrorHandler;
        if (errorHandler == this) {
            errorHandler2 = null;
        }
        return errorHandler2;
    }

    @Override
    public boolean getFeature(String string) throws SAXNotRecognizedException, SAXNotSupportedException {
        Serializable serializable = (Boolean)this.theFeatures.get(string);
        if (serializable != null) {
            return (Boolean)serializable;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unknown feature ");
        ((StringBuilder)serializable).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)serializable).toString());
    }

    @Override
    public Object getProperty(String object) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (((String)object).equals(lexicalHandlerProperty)) {
            LexicalHandler lexicalHandler = this.theLexicalHandler;
            object = lexicalHandler;
            if (lexicalHandler == this) {
                object = null;
            }
            return object;
        }
        if (((String)object).equals(scannerProperty)) {
            return this.theScanner;
        }
        if (((String)object).equals(schemaProperty)) {
            return this.theSchema;
        }
        if (((String)object).equals(autoDetectorProperty)) {
            return this.theAutoDetector;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown property ");
        stringBuilder.append((String)object);
        throw new SAXNotRecognizedException(stringBuilder.toString());
    }

    @Override
    public void gi(char[] object, int n, int n2) throws SAXException {
        if (this.theNewElement != null) {
            return;
        }
        String string = this.makeName((char[])object, n, n2);
        if (string == null) {
            return;
        }
        ElementType elementType = this.theSchema.getElementType(string);
        object = elementType;
        if (elementType == null) {
            if (this.ignoreBogons) {
                return;
            }
            boolean bl = this.bogonsEmpty;
            n2 = -1;
            n = bl ? 0 : -1;
            if (!this.rootBogons) {
                n2 = Integer.MAX_VALUE;
            }
            this.theSchema.elementType(string, n, n2, 0);
            if (!this.rootBogons) {
                object = this.theSchema;
                ((Schema)object).parent(string, ((Schema)object).rootElementType().name());
            }
            object = this.theSchema.getElementType(string);
        }
        this.theNewElement = new Element((ElementType)object, this.defaultAttributes);
    }

    @Override
    public void parse(String string) throws IOException, SAXException {
        this.parse(new InputSource(string));
    }

    @Override
    public void parse(InputSource object) throws IOException, SAXException {
        this.setup();
        Reader reader = this.getReader((InputSource)object);
        this.theContentHandler.startDocument();
        this.theScanner.resetDocumentLocator(((InputSource)object).getPublicId(), ((InputSource)object).getSystemId());
        object = this.theScanner;
        if (object instanceof Locator) {
            this.theContentHandler.setDocumentLocator((Locator)object);
        }
        if (!this.theSchema.getURI().equals("")) {
            this.theContentHandler.startPrefixMapping(this.theSchema.getPrefix(), this.theSchema.getURI());
        }
        this.theScanner.scan(reader, this);
    }

    @Override
    public void pcdata(char[] arrc, int n, int n2) throws SAXException {
        if (n2 == 0) {
            return;
        }
        boolean bl = true;
        for (int i = 0; i < n2; ++i) {
            if (Character.isWhitespace(arrc[n + i])) continue;
            bl = false;
        }
        if (bl && !this.theStack.canContain(this.thePCDATA)) {
            if (this.ignorableWhitespace) {
                this.theContentHandler.ignorableWhitespace(arrc, n, n2);
            }
        } else {
            this.rectify(this.thePCDATA);
            this.theContentHandler.characters(arrc, n, n2);
        }
    }

    @Override
    public void pi(char[] arrc, int n, int n2) throws SAXException {
        String string;
        if (this.theNewElement == null && (string = this.thePITarget) != null) {
            if ("xml".equalsIgnoreCase(string)) {
                return;
            }
            int n3 = n2;
            if (n2 > 0) {
                n3 = n2;
                if (arrc[n2 - 1] == '?') {
                    n3 = n2 - 1;
                }
            }
            this.theContentHandler.processingInstruction(this.thePITarget, new String(arrc, n, n3));
            this.thePITarget = null;
            return;
        }
    }

    @Override
    public void pitarget(char[] arrc, int n, int n2) throws SAXException {
        if (this.theNewElement != null) {
            return;
        }
        this.thePITarget = this.makeName(arrc, n, n2).replace(':', '_');
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
        if (contentHandler == null) {
            contentHandler = this;
        }
        this.theContentHandler = contentHandler;
    }

    @Override
    public void setDTDHandler(DTDHandler dTDHandler) {
        if (dTDHandler == null) {
            dTDHandler = this;
        }
        this.theDTDHandler = dTDHandler;
    }

    @Override
    public void setEntityResolver(EntityResolver entityResolver) {
        if (entityResolver == null) {
            entityResolver = this;
        }
        this.theEntityResolver = entityResolver;
    }

    @Override
    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            errorHandler = this;
        }
        this.theErrorHandler = errorHandler;
    }

    @Override
    public void setFeature(String string, boolean bl) throws SAXNotRecognizedException, SAXNotSupportedException {
        if ((Boolean)this.theFeatures.get(string) != null) {
            if (bl) {
                this.theFeatures.put(string, Boolean.TRUE);
            } else {
                this.theFeatures.put(string, Boolean.FALSE);
            }
            if (string.equals(namespacesFeature)) {
                this.namespaces = bl;
            } else if (string.equals(ignoreBogonsFeature)) {
                this.ignoreBogons = bl;
            } else if (string.equals(bogonsEmptyFeature)) {
                this.bogonsEmpty = bl;
            } else if (string.equals(rootBogonsFeature)) {
                this.rootBogons = bl;
            } else if (string.equals(defaultAttributesFeature)) {
                this.defaultAttributes = bl;
            } else if (string.equals(translateColonsFeature)) {
                this.translateColons = bl;
            } else if (string.equals(restartElementsFeature)) {
                this.restartElements = bl;
            } else if (string.equals(ignorableWhitespaceFeature)) {
                this.ignorableWhitespace = bl;
            } else if (string.equals(CDATAElementsFeature)) {
                this.CDATAElements = bl;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown feature ");
        stringBuilder.append(string);
        throw new SAXNotRecognizedException(stringBuilder.toString());
    }

    @Override
    public void setProperty(String string, Object object) throws SAXNotRecognizedException, SAXNotSupportedException {
        block10 : {
            block11 : {
                block4 : {
                    block8 : {
                        block9 : {
                            block6 : {
                                block7 : {
                                    block2 : {
                                        block5 : {
                                            block3 : {
                                                if (!string.equals(lexicalHandlerProperty)) break block2;
                                                if (object != null) break block3;
                                                this.theLexicalHandler = this;
                                                break block4;
                                            }
                                            if (!(object instanceof LexicalHandler)) break block5;
                                            this.theLexicalHandler = (LexicalHandler)object;
                                            break block4;
                                        }
                                        throw new SAXNotSupportedException("Your lexical handler is not a LexicalHandler");
                                    }
                                    if (!string.equals(scannerProperty)) break block6;
                                    if (!(object instanceof Scanner)) break block7;
                                    this.theScanner = (Scanner)object;
                                    break block4;
                                }
                                throw new SAXNotSupportedException("Your scanner is not a Scanner");
                            }
                            if (!string.equals(schemaProperty)) break block8;
                            if (!(object instanceof Schema)) break block9;
                            this.theSchema = (Schema)object;
                            break block4;
                        }
                        throw new SAXNotSupportedException("Your schema is not a Schema");
                    }
                    if (!string.equals(autoDetectorProperty)) break block10;
                    if (!(object instanceof AutoDetector)) break block11;
                    this.theAutoDetector = (AutoDetector)object;
                }
                return;
            }
            throw new SAXNotSupportedException("Your auto-detector is not an AutoDetector");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown property ");
        ((StringBuilder)object).append(string);
        throw new SAXNotRecognizedException(((StringBuilder)object).toString());
    }

    @Override
    public void stagc(char[] arrc, int n, int n2) throws SAXException {
        Element element = this.theNewElement;
        if (element == null) {
            return;
        }
        this.rectify(element);
        if (this.theStack.model() == 0) {
            this.etag_basic(arrc, n, n2);
        }
    }

    @Override
    public void stage(char[] arrc, int n, int n2) throws SAXException {
        Element element = this.theNewElement;
        if (element == null) {
            return;
        }
        this.rectify(element);
        this.etag_basic(arrc, n, n2);
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }

}

