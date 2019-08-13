/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.NamespaceSupport;
import org.xml.sax.helpers.XMLFilterImpl;

public class XMLWriter
extends XMLFilterImpl
implements LexicalHandler {
    public static final String CDATA_SECTION_ELEMENTS = "cdata-section-elements";
    public static final String DOCTYPE_PUBLIC = "doctype-public";
    public static final String DOCTYPE_SYSTEM = "doctype-system";
    public static final String ENCODING = "encoding";
    public static final String INDENT = "indent";
    public static final String MEDIA_TYPE = "media-type";
    public static final String METHOD = "method";
    public static final String OMIT_XML_DECLARATION = "omit-xml-declaration";
    public static final String STANDALONE = "standalone";
    public static final String VERSION = "version";
    private final Attributes EMPTY_ATTS = new AttributesImpl();
    private String[] booleans = new String[]{"checked", "compact", "declare", "defer", "disabled", "ismap", "multiple", "nohref", "noresize", "noshade", "nowrap", "readonly", "selected"};
    private boolean cdataElement = false;
    private Hashtable doneDeclTable;
    private int elementLevel = 0;
    private boolean forceDTD = false;
    private Hashtable forcedDeclTable;
    private boolean hasOutputDTD = false;
    private boolean htmlMode = false;
    private NamespaceSupport nsSupport;
    private Writer output;
    private String outputEncoding = "";
    private Properties outputProperties;
    private String overridePublic = null;
    private String overrideSystem = null;
    private int prefixCounter = 0;
    private Hashtable prefixTable;
    private String standalone = null;
    private boolean unicodeMode = false;
    private String version = null;

    public XMLWriter() {
        this.init(null);
    }

    public XMLWriter(Writer writer) {
        this.init(writer);
    }

    public XMLWriter(XMLReader xMLReader) {
        super(xMLReader);
        this.init(null);
    }

    public XMLWriter(XMLReader xMLReader, Writer writer) {
        super(xMLReader);
        this.init(writer);
    }

    private boolean booleanAttribute(String string, String arrstring, String string2) {
        int n;
        String string3;
        string = string3 = string;
        if (string3 == null) {
            n = arrstring.indexOf(58);
            string = string3;
            if (n != -1) {
                string = arrstring.substring(n + 1, arrstring.length());
            }
        }
        if (!string.equals(string2)) {
            return false;
        }
        for (n = 0; n < (arrstring = this.booleans).length; ++n) {
            if (!string.equals(arrstring[n])) continue;
            return true;
        }
        return false;
    }

    private String doPrefix(String string, String charSequence, boolean bl) {
        String string2;
        int n;
        String string3;
        String string4;
        block22 : {
            block23 : {
                block20 : {
                    block21 : {
                        string3 = this.nsSupport.getURI("");
                        if ("".equals(string)) {
                            if (bl && string3 != null) {
                                this.nsSupport.declarePrefix("", "");
                            }
                            return null;
                        }
                        string4 = bl && string3 != null && string.equals(string3) ? "" : this.nsSupport.getPrefix(string);
                        if (string4 != null) {
                            return string4;
                        }
                        string2 = string4 = (String)this.doneDeclTable.get(string);
                        if (string4 == null) break block20;
                        if ((!bl || string3 != null) && "".equals(string4)) break block21;
                        string2 = string4;
                        if (this.nsSupport.getURI(string4) == null) break block20;
                    }
                    string2 = null;
                }
                string4 = string2;
                if (string2 != null) break block22;
                string4 = string2 = (String)this.prefixTable.get(string);
                if (string2 == null) break block22;
                if ((!bl || string3 != null) && "".equals(string2)) break block23;
                string4 = string2;
                if (this.nsSupport.getURI(string2) == null) break block22;
            }
            string4 = null;
        }
        string2 = string4;
        if (string4 == null) {
            string2 = string4;
            if (charSequence != null) {
                string2 = string4;
                if (!"".equals(charSequence)) {
                    n = ((String)charSequence).indexOf(58);
                    if (n == -1) {
                        string2 = string4;
                        if (bl) {
                            string2 = string4;
                            if (string3 == null) {
                                string2 = "";
                            }
                        }
                    } else {
                        string2 = ((String)charSequence).substring(0, n);
                    }
                }
            }
        }
        do {
            if (string2 != null && this.nsSupport.getURI(string2) == null) {
                this.nsSupport.declarePrefix(string2, string);
                this.doneDeclTable.put(string, string2);
                return string2;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("__NS");
            this.prefixCounter = n = this.prefixCounter + 1;
            ((StringBuilder)charSequence).append(n);
            string2 = ((StringBuilder)charSequence).toString();
        } while (true);
    }

    private void forceNSDecls() {
        Enumeration enumeration = this.forcedDeclTable.keys();
        while (enumeration.hasMoreElements()) {
            this.doPrefix((String)enumeration.nextElement(), null, true);
        }
    }

    private void init(Writer writer) {
        this.setOutput(writer);
        this.nsSupport = new NamespaceSupport();
        this.prefixTable = new Hashtable();
        this.forcedDeclTable = new Hashtable();
        this.doneDeclTable = new Hashtable();
        this.outputProperties = new Properties();
    }

    private void write(char c) throws SAXException {
        try {
            this.output.write(c);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    private void write(String string) throws SAXException {
        try {
            this.output.write(string);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    private void writeAttributes(Attributes attributes) throws SAXException {
        int n = attributes.getLength();
        for (int i = 0; i < n; ++i) {
            char[] arrc = attributes.getValue(i).toCharArray();
            this.write(' ');
            this.writeName(attributes.getURI(i), attributes.getLocalName(i), attributes.getQName(i), false);
            if (this.htmlMode && this.booleanAttribute(attributes.getLocalName(i), attributes.getQName(i), attributes.getValue(i))) break;
            this.write("=\"");
            this.writeEsc(arrc, 0, arrc.length, true);
            this.write('\"');
        }
    }

    private void writeEsc(char[] arrc, int n, int n2, boolean bl) throws SAXException {
        for (int i = n; i < n + n2; ++i) {
            char c = arrc[i];
            if (c != '\"') {
                if (c != '&') {
                    if (c != '<') {
                        if (c != '>') {
                            if (!this.unicodeMode && arrc[i] > '') {
                                this.write("&#");
                                this.write(Integer.toString(arrc[i]));
                                this.write(';');
                                continue;
                            }
                            this.write(arrc[i]);
                            continue;
                        }
                        this.write("&gt;");
                        continue;
                    }
                    this.write("&lt;");
                    continue;
                }
                this.write("&amp;");
                continue;
            }
            if (bl) {
                this.write("&quot;");
                continue;
            }
            this.write('\"');
        }
    }

    private void writeNSDecls() throws SAXException {
        Enumeration enumeration = this.nsSupport.getDeclaredPrefixes();
        while (enumeration.hasMoreElements()) {
            char[] arrc;
            String string = (String)enumeration.nextElement();
            char[] arrc2 = arrc = this.nsSupport.getURI(string);
            if (arrc == null) {
                arrc2 = "";
            }
            arrc2 = arrc2.toCharArray();
            this.write(' ');
            if ("".equals(string)) {
                this.write("xmlns=\"");
            } else {
                this.write("xmlns:");
                this.write(string);
                this.write("=\"");
            }
            this.writeEsc(arrc2, 0, arrc2.length, true);
            this.write('\"');
        }
    }

    private void writeName(String string, String string2, String string3, boolean bl) throws SAXException {
        if ((string = this.doPrefix(string, string3, bl)) != null && !"".equals(string)) {
            this.write(string);
            this.write(':');
        }
        if (string2 != null && !"".equals(string2)) {
            this.write(string2);
        } else {
            this.write(string3.substring(string3.indexOf(58) + 1, string3.length()));
        }
    }

    public void characters(String arrc) throws SAXException {
        arrc = arrc.toCharArray();
        this.characters(arrc, 0, arrc.length);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        if (!this.cdataElement) {
            this.writeEsc(arrc, n, n2, false);
        } else {
            for (int i = n; i < n + n2; ++i) {
                this.write(arrc[i]);
            }
        }
        super.characters(arrc, n, n2);
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        this.write("<!--");
        for (int i = n; i < n + n2; ++i) {
            this.write(arrc[i]);
            if (arrc[i] != '-' || i + 1 > n + n2 || arrc[i + 1] != '-') continue;
            this.write(' ');
        }
        this.write("-->");
    }

    public void dataElement(String string, String string2) throws SAXException {
        this.dataElement("", string, "", this.EMPTY_ATTS, string2);
    }

    public void dataElement(String string, String string2, String string3) throws SAXException {
        this.dataElement(string, string2, "", this.EMPTY_ATTS, string3);
    }

    public void dataElement(String string, String string2, String string3, Attributes attributes, String string4) throws SAXException {
        this.startElement(string, string2, string3, attributes);
        this.characters(string4);
        this.endElement(string, string2, string3);
    }

    public void emptyElement(String string) throws SAXException {
        this.emptyElement("", string, "", this.EMPTY_ATTS);
    }

    public void emptyElement(String string, String string2) throws SAXException {
        this.emptyElement(string, string2, "", this.EMPTY_ATTS);
    }

    public void emptyElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        this.nsSupport.pushContext();
        this.write('<');
        this.writeName(string, string2, string3, true);
        this.writeAttributes(attributes);
        if (this.elementLevel == 1) {
            this.forceNSDecls();
        }
        this.writeNSDecls();
        this.write("/>");
        super.startElement(string, string2, string3, attributes);
        super.endElement(string, string2, string3);
    }

    @Override
    public void endCDATA() throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
        this.write('\n');
        super.endDocument();
        try {
            this.flush();
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public void endElement(String string) throws SAXException {
        this.endElement("", string, "");
    }

    public void endElement(String string, String string2) throws SAXException {
        this.endElement(string, string2, "");
    }

    @Override
    public void endElement(String string, String string2, String string3) throws SAXException {
        if (!(this.htmlMode && (string.equals("http://www.w3.org/1999/xhtml") || string.equals("")) && (string3.equals("area") || string3.equals("base") || string3.equals("basefont") || string3.equals("br") || string3.equals("col") || string3.equals("frame") || string3.equals("hr") || string3.equals("img") || string3.equals("input") || string3.equals("isindex") || string3.equals("link") || string3.equals("meta") || string3.equals("param")))) {
            this.write("</");
            this.writeName(string, string2, string3, true);
            this.write('>');
        }
        if (this.elementLevel == 1) {
            this.write('\n');
        }
        this.cdataElement = false;
        super.endElement(string, string2, string3);
        this.nsSupport.popContext();
        --this.elementLevel;
    }

    @Override
    public void endEntity(String string) throws SAXException {
    }

    public void flush() throws IOException {
        this.output.flush();
    }

    public void forceNSDecl(String string) {
        this.forcedDeclTable.put(string, Boolean.TRUE);
    }

    public void forceNSDecl(String string, String string2) {
        this.setPrefix(string, string2);
        this.forceNSDecl(string);
    }

    public String getOutputProperty(String string) {
        return this.outputProperties.getProperty(string);
    }

    public String getPrefix(String string) {
        return (String)this.prefixTable.get(string);
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        this.writeEsc(arrc, n, n2, false);
        super.ignorableWhitespace(arrc, n, n2);
    }

    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.write("<?");
        this.write(string);
        this.write(' ');
        this.write(string2);
        this.write("?>");
        if (this.elementLevel < 1) {
            this.write('\n');
        }
        super.processingInstruction(string, string2);
    }

    public void reset() {
        this.elementLevel = 0;
        this.prefixCounter = 0;
        this.nsSupport.reset();
    }

    public void setOutput(Writer writer) {
        this.output = writer == null ? new OutputStreamWriter(System.out) : writer;
    }

    public void setOutputProperty(String string, String string2) {
        this.outputProperties.setProperty(string, string2);
        if (string.equals(ENCODING)) {
            this.outputEncoding = string2;
            this.unicodeMode = string2.substring(0, 3).equalsIgnoreCase("utf");
        } else if (string.equals(METHOD)) {
            this.htmlMode = string2.equals("html");
        } else if (string.equals(DOCTYPE_PUBLIC)) {
            this.overridePublic = string2;
            this.forceDTD = true;
        } else if (string.equals(DOCTYPE_SYSTEM)) {
            this.overrideSystem = string2;
            this.forceDTD = true;
        } else if (string.equals(VERSION)) {
            this.version = string2;
        } else if (string.equals(STANDALONE)) {
            this.standalone = string2;
        }
    }

    public void setPrefix(String string, String string2) {
        this.prefixTable.put(string, string2);
    }

    @Override
    public void startCDATA() throws SAXException {
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        char c;
        char c2;
        if (string == null) {
            return;
        }
        if (this.hasOutputDTD) {
            return;
        }
        this.hasOutputDTD = true;
        this.write("<!DOCTYPE ");
        this.write(string);
        string = string3;
        if (string3 == null) {
            string = "";
        }
        if (this.overrideSystem != null) {
            string = this.overrideSystem;
        }
        char c3 = '\"';
        char c4 = string.indexOf(34) != -1 ? (c = '\'') : (c2 = '\"');
        if (this.overridePublic != null) {
            string2 = this.overridePublic;
        }
        if (string2 != null && !"".equals(string2)) {
            char c5 = c3;
            if (string2.indexOf(34) != -1) {
                c5 = c3 = '\'';
            }
            this.write(" PUBLIC ");
            this.write(c5);
            this.write(string2);
            this.write(c5);
            this.write(' ');
        } else {
            this.write(" SYSTEM ");
        }
        this.write(c4);
        this.write(string);
        this.write(c4);
        this.write(">\n");
    }

    @Override
    public void startDocument() throws SAXException {
        this.reset();
        if (!"yes".equals(this.outputProperties.getProperty(OMIT_XML_DECLARATION, "no"))) {
            this.write("<?xml");
            if (this.version == null) {
                this.write(" version=\"1.0\"");
            } else {
                this.write(" version=\"");
                this.write(this.version);
                this.write("\"");
            }
            String string = this.outputEncoding;
            if (string != null && string != "") {
                this.write(" encoding=\"");
                this.write(this.outputEncoding);
                this.write("\"");
            }
            if (this.standalone == null) {
                this.write(" standalone=\"yes\"?>\n");
            } else {
                this.write(" standalone=\"");
                this.write(this.standalone);
                this.write("\"");
            }
        }
        super.startDocument();
    }

    public void startElement(String string) throws SAXException {
        this.startElement("", string, "", this.EMPTY_ATTS);
    }

    public void startElement(String string, String string2) throws SAXException {
        this.startElement(string, string2, "", this.EMPTY_ATTS);
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        ++this.elementLevel;
        this.nsSupport.pushContext();
        if (this.forceDTD && !this.hasOutputDTD) {
            String string4 = string2 == null ? string3 : string2;
            this.startDTD(string4, "", "");
        }
        this.write('<');
        this.writeName(string, string2, string3, true);
        this.writeAttributes(attributes);
        if (this.elementLevel == 1) {
            this.forceNSDecls();
        }
        this.writeNSDecls();
        this.write('>');
        if (this.htmlMode && (string3.equals("script") || string3.equals("style"))) {
            this.cdataElement = true;
        }
        super.startElement(string, string2, string3, attributes);
    }

    @Override
    public void startEntity(String string) throws SAXException {
    }
}

