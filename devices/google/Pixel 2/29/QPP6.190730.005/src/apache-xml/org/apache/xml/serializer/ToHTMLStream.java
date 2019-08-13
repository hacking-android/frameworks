/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.Writer;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.xml.serializer.AttributesImplSerializer;
import org.apache.xml.serializer.CharInfo;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.ElemDesc;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.OutputPropertyUtils;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.ToStream;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ToHTMLStream
extends ToStream {
    private static final ElemDesc m_dummy;
    static final Trie m_elementFlags;
    private Trie m_htmlInfo = new Trie(m_elementFlags);
    private final CharInfo m_htmlcharInfo = CharInfo.getCharInfo(CharInfo.HTML_ENTITIES_RESOURCE, "html");
    private boolean m_inBlockElem = false;
    protected boolean m_inDTD = false;
    private boolean m_omitMetaTag = false;
    private boolean m_specialEscapeURLs = true;

    static {
        m_elementFlags = new Trie();
        ToHTMLStream.initTagReference(m_elementFlags);
        m_dummy = new ElemDesc(8);
    }

    public ToHTMLStream() {
        this.m_doIndent = true;
        this.m_charInfo = this.m_htmlcharInfo;
        this.m_prefixMap = new NamespaceMappings();
    }

    public static final ElemDesc getElemDesc(String object) {
        if ((object = m_elementFlags.get((String)object)) != null) {
            return (ElemDesc)object;
        }
        return m_dummy;
    }

    private ElemDesc getElemDesc2(String object) {
        if ((object = this.m_htmlInfo.get2((String)object)) != null) {
            return (ElemDesc)object;
        }
        return m_dummy;
    }

    private final boolean getOmitMetaTag() {
        return this.m_omitMetaTag;
    }

    private final boolean getSpecialEscapeURLs() {
        return this.m_specialEscapeURLs;
    }

    static void initTagReference(Trie trie) {
        trie.put("BASEFONT", new ElemDesc(2));
        trie.put("FRAME", new ElemDesc(10));
        trie.put("FRAMESET", new ElemDesc(8));
        trie.put("NOFRAMES", new ElemDesc(8));
        trie.put("ISINDEX", new ElemDesc(10));
        trie.put("APPLET", new ElemDesc(2097152));
        trie.put("CENTER", new ElemDesc(8));
        trie.put("DIR", new ElemDesc(8));
        trie.put("MENU", new ElemDesc(8));
        trie.put("TT", new ElemDesc(4096));
        trie.put("I", new ElemDesc(4096));
        trie.put("B", new ElemDesc(4096));
        trie.put("BIG", new ElemDesc(4096));
        trie.put("SMALL", new ElemDesc(4096));
        trie.put("EM", new ElemDesc(8192));
        trie.put("STRONG", new ElemDesc(8192));
        trie.put("DFN", new ElemDesc(8192));
        trie.put("CODE", new ElemDesc(8192));
        trie.put("SAMP", new ElemDesc(8192));
        trie.put("KBD", new ElemDesc(8192));
        trie.put("VAR", new ElemDesc(8192));
        trie.put("CITE", new ElemDesc(8192));
        trie.put("ABBR", new ElemDesc(8192));
        trie.put("ACRONYM", new ElemDesc(8192));
        trie.put("SUP", new ElemDesc(98304));
        trie.put("SUB", new ElemDesc(98304));
        trie.put("SPAN", new ElemDesc(98304));
        trie.put("BDO", new ElemDesc(98304));
        trie.put("BR", new ElemDesc(98314));
        trie.put("BODY", new ElemDesc(8));
        trie.put("ADDRESS", new ElemDesc(56));
        trie.put("DIV", new ElemDesc(56));
        trie.put("A", new ElemDesc(32768));
        trie.put("MAP", new ElemDesc(98312));
        trie.put("AREA", new ElemDesc(10));
        trie.put("LINK", new ElemDesc(131082));
        trie.put("IMG", new ElemDesc(2195458));
        trie.put("OBJECT", new ElemDesc(2326528));
        trie.put("PARAM", new ElemDesc(2));
        trie.put("HR", new ElemDesc(58));
        trie.put("P", new ElemDesc(56));
        trie.put("H1", new ElemDesc(262152));
        trie.put("H2", new ElemDesc(262152));
        trie.put("H3", new ElemDesc(262152));
        trie.put("H4", new ElemDesc(262152));
        trie.put("H5", new ElemDesc(262152));
        trie.put("H6", new ElemDesc(262152));
        trie.put("PRE", new ElemDesc(1048584));
        trie.put("Q", new ElemDesc(98304));
        trie.put("BLOCKQUOTE", new ElemDesc(56));
        trie.put("INS", new ElemDesc(0));
        trie.put("DEL", new ElemDesc(0));
        trie.put("DL", new ElemDesc(56));
        trie.put("DT", new ElemDesc(8));
        trie.put("DD", new ElemDesc(8));
        trie.put("OL", new ElemDesc(524296));
        trie.put("UL", new ElemDesc(524296));
        trie.put("LI", new ElemDesc(8));
        trie.put("FORM", new ElemDesc(8));
        trie.put("LABEL", new ElemDesc(16384));
        trie.put("INPUT", new ElemDesc(18434));
        trie.put("SELECT", new ElemDesc(18432));
        trie.put("OPTGROUP", new ElemDesc(0));
        trie.put("OPTION", new ElemDesc(0));
        trie.put("TEXTAREA", new ElemDesc(18432));
        trie.put("FIELDSET", new ElemDesc(24));
        trie.put("LEGEND", new ElemDesc(0));
        trie.put("BUTTON", new ElemDesc(18432));
        trie.put("TABLE", new ElemDesc(56));
        trie.put("CAPTION", new ElemDesc(8));
        trie.put("THEAD", new ElemDesc(8));
        trie.put("TFOOT", new ElemDesc(8));
        trie.put("TBODY", new ElemDesc(8));
        trie.put("COLGROUP", new ElemDesc(8));
        trie.put("COL", new ElemDesc(10));
        trie.put("TR", new ElemDesc(8));
        trie.put("TH", new ElemDesc(0));
        trie.put("TD", new ElemDesc(0));
        trie.put("HEAD", new ElemDesc(4194312));
        trie.put("TITLE", new ElemDesc(8));
        trie.put("BASE", new ElemDesc(10));
        trie.put("META", new ElemDesc(131082));
        trie.put("STYLE", new ElemDesc(131336));
        trie.put("SCRIPT", new ElemDesc(229632));
        trie.put("NOSCRIPT", new ElemDesc(56));
        trie.put("HTML", new ElemDesc(8388616));
        trie.put("FONT", new ElemDesc(4096));
        trie.put("S", new ElemDesc(4096));
        trie.put("STRIKE", new ElemDesc(4096));
        trie.put("U", new ElemDesc(4096));
        trie.put("NOBR", new ElemDesc(4096));
        trie.put("IFRAME", new ElemDesc(56));
        trie.put("LAYER", new ElemDesc(56));
        trie.put("ILAYER", new ElemDesc(56));
        ElemDesc elemDesc = (ElemDesc)trie.get("a");
        elemDesc.setAttr("HREF", 2);
        elemDesc.setAttr("NAME", 2);
        elemDesc = (ElemDesc)trie.get("area");
        elemDesc.setAttr("HREF", 2);
        elemDesc.setAttr("NOHREF", 4);
        ((ElemDesc)trie.get("base")).setAttr("HREF", 2);
        ((ElemDesc)trie.get("button")).setAttr("DISABLED", 4);
        ((ElemDesc)trie.get("blockquote")).setAttr("CITE", 2);
        ((ElemDesc)trie.get("del")).setAttr("CITE", 2);
        ((ElemDesc)trie.get("dir")).setAttr("COMPACT", 4);
        elemDesc = (ElemDesc)trie.get("div");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("NOWRAP", 4);
        ((ElemDesc)trie.get("dl")).setAttr("COMPACT", 4);
        ((ElemDesc)trie.get("form")).setAttr("ACTION", 2);
        elemDesc = (ElemDesc)trie.get("frame");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("LONGDESC", 2);
        elemDesc.setAttr("NORESIZE", 4);
        ((ElemDesc)trie.get("head")).setAttr("PROFILE", 2);
        ((ElemDesc)trie.get("hr")).setAttr("NOSHADE", 4);
        elemDesc = (ElemDesc)trie.get("iframe");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("LONGDESC", 2);
        ((ElemDesc)trie.get("ilayer")).setAttr("SRC", 2);
        elemDesc = (ElemDesc)trie.get("img");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("LONGDESC", 2);
        elemDesc.setAttr("USEMAP", 2);
        elemDesc.setAttr("ISMAP", 4);
        elemDesc = (ElemDesc)trie.get("input");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("USEMAP", 2);
        elemDesc.setAttr("CHECKED", 4);
        elemDesc.setAttr("DISABLED", 4);
        elemDesc.setAttr("ISMAP", 4);
        elemDesc.setAttr("READONLY", 4);
        ((ElemDesc)trie.get("ins")).setAttr("CITE", 2);
        ((ElemDesc)trie.get("layer")).setAttr("SRC", 2);
        ((ElemDesc)trie.get("link")).setAttr("HREF", 2);
        ((ElemDesc)trie.get("menu")).setAttr("COMPACT", 4);
        elemDesc = (ElemDesc)trie.get("object");
        elemDesc.setAttr("CLASSID", 2);
        elemDesc.setAttr("CODEBASE", 2);
        elemDesc.setAttr("DATA", 2);
        elemDesc.setAttr("ARCHIVE", 2);
        elemDesc.setAttr("USEMAP", 2);
        elemDesc.setAttr("DECLARE", 4);
        ((ElemDesc)trie.get("ol")).setAttr("COMPACT", 4);
        ((ElemDesc)trie.get("optgroup")).setAttr("DISABLED", 4);
        elemDesc = (ElemDesc)trie.get("option");
        elemDesc.setAttr("SELECTED", 4);
        elemDesc.setAttr("DISABLED", 4);
        ((ElemDesc)trie.get("q")).setAttr("CITE", 2);
        elemDesc = (ElemDesc)trie.get("script");
        elemDesc.setAttr("SRC", 2);
        elemDesc.setAttr("FOR", 2);
        elemDesc.setAttr("DEFER", 4);
        elemDesc = (ElemDesc)trie.get("select");
        elemDesc.setAttr("DISABLED", 4);
        elemDesc.setAttr("MULTIPLE", 4);
        ((ElemDesc)trie.get("table")).setAttr("NOWRAP", 4);
        ((ElemDesc)trie.get("td")).setAttr("NOWRAP", 4);
        elemDesc = (ElemDesc)trie.get("textarea");
        elemDesc.setAttr("DISABLED", 4);
        elemDesc.setAttr("READONLY", 4);
        ((ElemDesc)trie.get("th")).setAttr("NOWRAP", 4);
        ((ElemDesc)trie.get("tr")).setAttr("NOWRAP", 4);
        ((ElemDesc)trie.get("ul")).setAttr("COMPACT", 4);
    }

    private boolean isASCIIDigit(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    private boolean isHHSign(String string) {
        boolean bl = true;
        try {
            Integer.parseInt(string, 16);
        }
        catch (NumberFormatException numberFormatException) {
            bl = false;
        }
        return bl;
    }

    private static String makeHHString(int n) {
        String string = Integer.toHexString(n).toUpperCase();
        CharSequence charSequence = string;
        if (string.length() == 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("0");
            ((StringBuilder)charSequence).append(string);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void outputDocTypeDecl(String string) throws SAXException {
        if (this.m_needToOutputDocTypeDecl) {
            String string2 = this.getDoctypeSystem();
            String string3 = this.getDoctypePublic();
            if (string2 != null || string3 != null) {
                Writer writer = this.m_writer;
                try {
                    writer.write("<!DOCTYPE ");
                    writer.write(string);
                    if (string3 != null) {
                        writer.write(" PUBLIC \"");
                        writer.write(string3);
                        writer.write(34);
                    }
                    if (string2 != null) {
                        if (string3 == null) {
                            writer.write(" SYSTEM \"");
                        } else {
                            writer.write(" \"");
                        }
                        writer.write(string2);
                        writer.write(34);
                    }
                    writer.write(62);
                    this.outputLineSep();
                }
                catch (IOException iOException) {
                    throw new SAXException(iOException);
                }
            }
        }
        this.m_needToOutputDocTypeDecl = false;
    }

    private void resetToHTMLStream() {
        this.m_inBlockElem = false;
        this.m_inDTD = false;
        this.m_omitMetaTag = false;
        this.m_specialEscapeURLs = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void addUniqueAttribute(String string, String string2, int n) throws SAXException {
        try {
            Writer writer = this.m_writer;
            if ((n & 1) > 0 && this.m_htmlcharInfo.onlyQuotAmpLtGt) {
                writer.write(32);
                writer.write(string);
                writer.write("=\"");
                writer.write(string2);
                writer.write(34);
                return;
            }
            if ((n & 2) > 0 && (string2.length() == 0 || string2.equalsIgnoreCase(string))) {
                writer.write(32);
                writer.write(string);
                return;
            }
            writer.write(32);
            writer.write(string);
            writer.write("=\"");
            if ((n & 4) > 0) {
                this.writeAttrURI(writer, string2, this.m_specialEscapeURLs);
            } else {
                this.writeAttrString(writer, string2, this.getEncoding());
            }
            writer.write(34);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String string5) throws SAXException {
    }

    @Override
    public final void cdata(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_elemContext.m_elementName != null && (this.m_elemContext.m_elementName.equalsIgnoreCase("SCRIPT") || this.m_elemContext.m_elementName.equalsIgnoreCase("STYLE"))) {
            try {
                if (this.m_elemContext.m_startTagOpen) {
                    this.closeStartTag();
                    this.m_elemContext.m_startTagOpen = false;
                }
                this.m_ispreserve = true;
                if (this.shouldIndent()) {
                    this.indent();
                }
                this.writeNormalizedChars(arrc, n, n2, true, this.m_lineSepUse);
            }
            catch (IOException iOException) {
                throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), iOException);
            }
        } else {
            super.cdata(arrc, n, n2);
        }
    }

    @Override
    public final void characters(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_elemContext.m_isRaw) {
            try {
                if (this.m_elemContext.m_startTagOpen) {
                    this.closeStartTag();
                    this.m_elemContext.m_startTagOpen = false;
                }
                this.m_ispreserve = true;
                this.writeNormalizedChars(arrc, n, n2, false, this.m_lineSepUse);
                if (this.m_tracer != null) {
                    super.fireCharEvent(arrc, n, n2);
                }
                return;
            }
            catch (IOException iOException) {
                throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), iOException);
            }
        }
        super.characters(arrc, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void closeStartTag() throws SAXException {
        try {
            int n;
            if (this.m_tracer != null) {
                super.fireStartElem(this.m_elemContext.m_elementName);
            }
            if ((n = this.m_attributes.getLength()) > 0) {
                this.processAttributes(this.m_writer, n);
                this.m_attributes.clear();
            }
            this.m_writer.write(62);
            if (this.m_CdataElems != null) {
                this.m_elemContext.m_isCdataSection = this.isCdataSection();
            }
            if (this.m_doIndent) {
                this.m_isprevtext = false;
                this.m_preserves.push(this.m_ispreserve);
            }
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void comment(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_inDTD) {
            return;
        }
        if (this.m_elemContext.m_startTagOpen) {
            this.closeStartTag();
            this.m_elemContext.m_startTagOpen = false;
        } else if (this.m_cdataTagOpen) {
            this.closeCDATA();
        } else if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
        }
        if (this.m_needToOutputDocTypeDecl) {
            this.outputDocTypeDecl("html");
        }
        super.comment(arrc, n, n2);
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
        this.m_inDTD = false;
    }

    @Override
    public final void endDocument() throws SAXException {
        this.flushPending();
        if (this.m_doIndent && !this.m_isprevtext) {
            try {
                this.outputLineSep();
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
        }
        this.flushWriter();
        if (this.m_tracer != null) {
            super.fireEndDoc();
        }
    }

    @Override
    public final void endElement(String string) throws SAXException {
        this.endElement(null, null, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public final void endElement(String object, String object2, String string) throws SAXException {
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
        }
        if (object != null && ((String)object).length() > 0) {
            super.endElement((String)object, (String)object2, string);
            return;
        }
        try {
            boolean bl;
            int n;
            int n2;
            block18 : {
                boolean bl2;
                block20 : {
                    block21 : {
                        boolean bl3;
                        block19 : {
                            object2 = this.m_elemContext;
                            n = ((ElemContext)object2).m_elementDesc.getFlags();
                            bl = (n & 2) != 0;
                            if (!this.m_doIndent) break block18;
                            n2 = (n & 8) != 0 ? 1 : 0;
                            bl3 = false;
                            if (!this.m_ispreserve) break block19;
                            this.m_ispreserve = false;
                            bl2 = bl3;
                            break block20;
                        }
                        bl2 = bl3;
                        if (!this.m_doIndent) break block20;
                        if (!this.m_inBlockElem) break block21;
                        bl2 = bl3;
                        if (n2 == 0) break block20;
                    }
                    this.m_startNewLine = true;
                    bl2 = true;
                }
                if (!((ElemContext)object2).m_startTagOpen && bl2) {
                    this.indent(((ElemContext)object2).m_currentElemDepth - 1);
                }
                boolean bl4 = n2 == 0;
                this.m_inBlockElem = bl4;
            }
            object = this.m_writer;
            if (!((ElemContext)object2).m_startTagOpen) {
                ((Writer)object).write("</");
                ((Writer)object).write(string);
                ((Writer)object).write(62);
            } else {
                if (this.m_tracer != null) {
                    super.fireStartElem(string);
                }
                if ((n2 = this.m_attributes.getLength()) > 0) {
                    this.processAttributes(this.m_writer, n2);
                    this.m_attributes.clear();
                }
                if (!bl) {
                    ((Writer)object).write("></");
                    ((Writer)object).write(string);
                    ((Writer)object).write(62);
                } else {
                    ((Writer)object).write(62);
                }
            }
            if ((2097152 & n) != 0) {
                this.m_ispreserve = true;
            }
            this.m_isprevtext = false;
            if (this.m_tracer != null) {
                super.fireEndElem(string);
            }
            if (bl) {
                this.m_elemContext = ((ElemContext)object2).m_prev;
                return;
            }
            if (!((ElemContext)object2).m_startTagOpen && this.m_doIndent && !this.m_preserves.isEmpty()) {
                this.m_preserves.pop();
            }
            this.m_elemContext = ((ElemContext)object2).m_prev;
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public final void entityReference(String string) throws SAXException {
        try {
            Writer writer = this.m_writer;
            writer.write(38);
            writer.write(string);
            writer.write(59);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
    }

    @Override
    public void namespaceAfterStartElement(String string, String string2) throws SAXException {
        if (this.m_elemContext.m_elementURI == null && ToHTMLStream.getPrefixPart(this.m_elemContext.m_elementName) == null && "".equals(string)) {
            this.m_elemContext.m_elementURI = string2;
        }
        this.startPrefixMapping(string, string2, false);
    }

    protected void processAttribute(Writer writer, String string, String string2, ElemDesc elemDesc) throws IOException {
        writer.write(32);
        if ((string2.length() == 0 || string2.equalsIgnoreCase(string)) && elemDesc != null && elemDesc.isAttrFlagSet(string, 4)) {
            writer.write(string);
        } else {
            writer.write(string);
            writer.write("=\"");
            if (elemDesc != null && elemDesc.isAttrFlagSet(string, 2)) {
                this.writeAttrURI(writer, string2, this.m_specialEscapeURLs);
            } else {
                this.writeAttrString(writer, string2, this.getEncoding());
            }
            writer.write(34);
        }
    }

    @Override
    public void processAttributes(Writer writer, int n) throws IOException, SAXException {
        for (int i = 0; i < n; ++i) {
            this.processAttribute(writer, this.m_attributes.getQName(i), this.m_attributes.getValue(i), this.m_elemContext.m_elementDesc);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void processingInstruction(String string, String string2) throws SAXException {
        this.flushPending();
        if (string.equals("javax.xml.transform.disable-output-escaping")) {
            this.startNonEscaping();
        } else if (string.equals("javax.xml.transform.enable-output-escaping")) {
            this.endNonEscaping();
        } else {
            if (this.m_elemContext.m_startTagOpen) {
                this.closeStartTag();
                this.m_elemContext.m_startTagOpen = false;
            } else if (this.m_cdataTagOpen) {
                this.closeCDATA();
            } else if (this.m_needToCallStartDocument) {
                this.startDocumentInternal();
            }
            if (this.m_needToOutputDocTypeDecl) {
                this.outputDocTypeDecl("html");
            }
            if (this.shouldIndent()) {
                this.indent();
            }
            Writer writer = this.m_writer;
            writer.write("<?");
            writer.write(string);
            if (string2.length() > 0 && !Character.isSpaceChar(string2.charAt(0))) {
                writer.write(32);
            }
            writer.write(string2);
            writer.write(62);
            if (this.m_elemContext.m_currentElemDepth <= 0) {
                this.outputLineSep();
            }
            this.m_startNewLine = true;
        }
        if (this.m_tracer == null) return;
        super.fireEscapingEvent(string, string2);
        return;
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public boolean reset() {
        if (!super.reset()) {
            return false;
        }
        this.resetToHTMLStream();
        return true;
    }

    public void setOmitMetaTag(boolean bl) {
        this.m_omitMetaTag = bl;
    }

    @Override
    public void setOutputFormat(Properties properties) {
        if (properties.getProperty("{http://xml.apache.org/xalan}use-url-escaping") != null) {
            this.m_specialEscapeURLs = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}use-url-escaping", properties);
        }
        if (properties.getProperty("{http://xml.apache.org/xalan}omit-meta-tag") != null) {
            this.m_omitMetaTag = OutputPropertyUtils.getBooleanProperty("{http://xml.apache.org/xalan}omit-meta-tag", properties);
        }
        super.setOutputFormat(properties);
    }

    public void setSpecialEscapeURLs(boolean bl) {
        this.m_specialEscapeURLs = bl;
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        this.m_inDTD = true;
        super.startDTD(string, string2, string3);
    }

    @Override
    protected void startDocumentInternal() throws SAXException {
        super.startDocumentInternal();
        this.m_needToCallStartDocument = false;
        this.m_needToOutputDocTypeDecl = true;
        this.m_startNewLine = false;
        this.setOmitXMLDeclaration(true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void startElement(String object, String string, String string2, Attributes object2) throws SAXException {
        ElemContext elemContext;
        Object object3;
        block21 : {
            block23 : {
                block22 : {
                    elemContext = this.m_elemContext;
                    if (elemContext.m_startTagOpen) {
                        this.closeStartTag();
                        elemContext.m_startTagOpen = false;
                    } else if (this.m_cdataTagOpen) {
                        this.closeCDATA();
                        this.m_cdataTagOpen = false;
                    } else if (this.m_needToCallStartDocument) {
                        this.startDocumentInternal();
                        this.m_needToCallStartDocument = false;
                    }
                    if (!this.m_needToOutputDocTypeDecl) break block21;
                    String string3 = string2;
                    if (string3 == null) break block22;
                    object3 = string3;
                    if (string3.length() != 0) break block23;
                }
                object3 = string;
            }
            this.outputDocTypeDecl((String)object3);
        }
        if (object != null && ((String)object).length() > 0) {
            super.startElement((String)object, string, string2, (Attributes)object2);
            return;
        }
        try {
            object3 = this.getElemDesc2(string2);
            int n = ((ElemDesc)object3).getFlags();
            boolean bl = this.m_doIndent;
            boolean bl2 = true;
            if (bl) {
                boolean bl3 = (n & 8) != 0;
                if (this.m_ispreserve) {
                    this.m_ispreserve = false;
                } else if (elemContext.m_elementName != null && (!this.m_inBlockElem || bl3)) {
                    this.m_startNewLine = true;
                    this.indent();
                }
                bl = !bl3;
                this.m_inBlockElem = bl;
            }
            if (object2 != null) {
                this.addAttributes((Attributes)object2);
            }
            this.m_isprevtext = false;
            object2 = this.m_writer;
            ((Writer)object2).write(60);
            ((Writer)object2).write(string2);
            if (this.m_tracer != null) {
                this.firePseudoAttributes();
            }
            if ((n & 2) != 0) {
                this.m_elemContext = elemContext.push();
                this.m_elemContext.m_elementName = string2;
                this.m_elemContext.m_elementDesc = object3;
                return;
            }
            this.m_elemContext = object = elemContext.push((String)object, string, string2);
            ((ElemContext)object).m_elementDesc = object3;
            bl = (n & 256) != 0 ? bl2 : false;
            ((ElemContext)object).m_isRaw = bl;
            if ((4194304 & n) != 0) {
                this.closeStartTag();
                ((ElemContext)object).m_startTagOpen = false;
                if (!this.m_omitMetaTag) {
                    if (this.m_doIndent) {
                        this.indent();
                    }
                    ((Writer)object2).write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=");
                    ((Writer)object2).write(Encodings.getMimeEncoding(this.getEncoding()));
                    ((Writer)object2).write("\">");
                }
            }
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void writeAttrString(Writer writer, String string, String arrc) throws IOException {
        int n = string.length();
        if (n > this.m_attrBuff.length) {
            this.m_attrBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_attrBuff, 0);
        arrc = this.m_attrBuff;
        int n2 = 0;
        char c = '\u0000';
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            char c2 = arrc[i];
            if (this.escapingNotNeeded(c2) && !this.m_charInfo.shouldMapAttrChar(c2)) {
                ++n2;
            } else if ('<' != c2 && '>' != c2) {
                if ('&' == c2 && i + 1 < n && '{' == arrc[i + 1]) {
                    ++n2;
                } else {
                    if (n2 > 0) {
                        writer.write(arrc, n3, n2);
                        n2 = 0;
                    }
                    if (i != (n3 = this.accumDefaultEntity(writer, c2, i, arrc, n, false, true))) {
                        i = n3 - 1;
                    } else {
                        char c3;
                        String string2;
                        if (Encodings.isHighUTF16Surrogate(c2)) {
                            this.writeUTF16Surrogate(c2, arrc, i, n);
                            ++i;
                        }
                        if ((string2 = this.m_charInfo.getOutputStringForChar(c3 = c2)) != null) {
                            writer.write(string2);
                        } else if (this.escapingNotNeeded(c3)) {
                            writer.write(c3);
                        } else {
                            writer.write("&#");
                            writer.write(Integer.toString(c3));
                            writer.write(59);
                        }
                    }
                    n3 = i + 1;
                }
            } else {
                ++n2;
            }
            c = c2;
        }
        if (n2 > 1) {
            if (n3 == 0) {
                writer.write(string);
            } else {
                writer.write(arrc, n3, n2);
            }
        } else if (n2 == 1) {
            writer.write(c);
        }
    }

    public void writeAttrURI(Writer writer, String string, boolean bl) throws IOException {
        int n = string.length();
        if (n > this.m_attrBuff.length) {
            this.m_attrBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_attrBuff, 0);
        char[] arrc = this.m_attrBuff;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        for (int i = 0; i < n; ++i) {
            int n5;
            block19 : {
                block25 : {
                    int n6;
                    block22 : {
                        block20 : {
                            block24 : {
                                block23 : {
                                    block21 : {
                                        block18 : {
                                            n6 = arrc[i];
                                            if (n6 < 32 || n6 > 126) break block18;
                                            if (n6 == 34) {
                                                n4 = n3;
                                                if (n3 > 0) {
                                                    writer.write(arrc, n2, n3);
                                                    n4 = 0;
                                                }
                                                if (bl) {
                                                    writer.write("%22");
                                                } else {
                                                    writer.write("&quot;");
                                                }
                                                n5 = i + 1;
                                                n3 = n4;
                                                n4 = n6;
                                            } else if (n6 == 38) {
                                                n4 = n3;
                                                if (n3 > 0) {
                                                    writer.write(arrc, n2, n3);
                                                    n4 = 0;
                                                }
                                                writer.write("&amp;");
                                                n5 = i + 1;
                                                n3 = n4;
                                                n4 = n6;
                                            } else {
                                                ++n3;
                                                n5 = n2;
                                                n4 = n6;
                                            }
                                            break block19;
                                        }
                                        n5 = n3;
                                        if (n3 > 0) {
                                            writer.write(arrc, n2, n3);
                                            n5 = 0;
                                        }
                                        if (!bl) break block20;
                                        if (n6 > 127) break block21;
                                        writer.write(37);
                                        writer.write(ToHTMLStream.makeHHString(n6));
                                        break block22;
                                    }
                                    if (n6 > 2047) break block23;
                                    writer.write(37);
                                    writer.write(ToHTMLStream.makeHHString(n6 >> 6 | 192));
                                    writer.write(37);
                                    writer.write(ToHTMLStream.makeHHString(n6 & 63 | 128));
                                    break block22;
                                }
                                if (!Encodings.isHighUTF16Surrogate((char)n6)) break block24;
                                n2 = n6 & 1023;
                                n4 = ((n2 & 960) >> 6) + 1;
                                n3 = arrc[++i];
                                int n7 = n3 & 1023;
                                writer.write(37);
                                writer.write(ToHTMLStream.makeHHString(n4 >> 2 | 240));
                                writer.write(37);
                                writer.write(ToHTMLStream.makeHHString((n4 & 3) << 4 & 48 | 128 | (n2 & 60) >> 2));
                                writer.write(37);
                                writer.write(ToHTMLStream.makeHHString((n7 & 960) >> 6 | (n2 & 3) << 4 & 48 | 128));
                                writer.write(37);
                                writer.write(ToHTMLStream.makeHHString(n7 & 63 | 128));
                                break block25;
                            }
                            writer.write(37);
                            writer.write(ToHTMLStream.makeHHString(n6 >> 12 | 224));
                            writer.write(37);
                            writer.write(ToHTMLStream.makeHHString((n6 & 4032) >> 6 | 128));
                            writer.write(37);
                            writer.write(ToHTMLStream.makeHHString(n6 & 63 | 128));
                            break block22;
                        }
                        if (this.escapingNotNeeded((char)n6)) {
                            writer.write(n6);
                        } else {
                            writer.write("&#");
                            writer.write(Integer.toString(n6));
                            writer.write(59);
                        }
                    }
                    n3 = n6;
                }
                n2 = i + 1;
                n4 = n3;
                n3 = n5;
                n5 = n2;
            }
            n2 = n5;
        }
        if (n3 > 1) {
            if (n2 == 0) {
                writer.write(string);
            } else {
                writer.write(arrc, n2, n3);
            }
        } else if (n3 == 1) {
            writer.write(n4);
        }
    }

    static class Trie {
        public static final int ALPHA_SIZE = 128;
        final Node m_Root;
        private char[] m_charBuffer = new char[0];
        private final boolean m_lowerCaseOnly;

        public Trie() {
            this.m_Root = new Node();
            this.m_lowerCaseOnly = false;
        }

        public Trie(Trie trie) {
            this.m_Root = trie.m_Root;
            this.m_lowerCaseOnly = trie.m_lowerCaseOnly;
            this.m_charBuffer = new char[trie.getLongestKeyLength()];
        }

        public Trie(boolean bl) {
            this.m_Root = new Node();
            this.m_lowerCaseOnly = bl;
        }

        public Object get(String object) {
            int n = ((String)object).length();
            if (this.m_charBuffer.length < n) {
                return null;
            }
            Node node = this.m_Root;
            if (n != 0) {
                if (n != 1) {
                    for (int i = 0; i < n; ++i) {
                        char c = ((String)object).charAt(i);
                        if ('' <= c) {
                            return null;
                        }
                        node = node.m_nextChar[c];
                        if (node != null) continue;
                        return null;
                    }
                    return node.m_Value;
                }
                char c = ((String)object).charAt(0);
                if (c < '' && (object = node.m_nextChar[c]) != null) {
                    return ((Node)object).m_Value;
                }
                return null;
            }
            return null;
        }

        public Object get2(String object) {
            char[] arrc = this.m_charBuffer;
            int n = ((String)object).length();
            if (arrc.length < n) {
                return null;
            }
            Node node = this.m_Root;
            if (n != 0) {
                if (n != 1) {
                    ((String)object).getChars(0, n, arrc, 0);
                    object = node;
                    for (int i = 0; i < n; ++i) {
                        char c = this.m_charBuffer[i];
                        if ('' <= c) {
                            return null;
                        }
                        object = ((Node)object).m_nextChar[c];
                        if (object != null) continue;
                        return null;
                    }
                    return ((Node)object).m_Value;
                }
                char c = ((String)object).charAt(0);
                if (c < '' && (object = node.m_nextChar[c]) != null) {
                    return ((Node)object).m_Value;
                }
                return null;
            }
            return null;
        }

        public int getLongestKeyLength() {
            return this.m_charBuffer.length;
        }

        public Object put(String object, Object object2) {
            Node node;
            block5 : {
                int n;
                int n2 = ((String)object).length();
                if (n2 > this.m_charBuffer.length) {
                    this.m_charBuffer = new char[n2];
                }
                Node node2 = this.m_Root;
                int n3 = 0;
                do {
                    node = node2;
                    if (n3 >= n2) break block5;
                    node = node2.m_nextChar[Character.toLowerCase(((String)object).charAt(n3))];
                    n = n3++;
                    if (node == null) break;
                    node2 = node;
                } while (true);
                do {
                    node = node2;
                    if (n >= n2) break;
                    node = new Node();
                    if (this.m_lowerCaseOnly) {
                        node2.m_nextChar[Character.toLowerCase((char)object.charAt((int)n))] = node;
                    } else {
                        node2.m_nextChar[Character.toUpperCase((char)object.charAt((int)n))] = node;
                        node2.m_nextChar[Character.toLowerCase((char)object.charAt((int)n))] = node;
                    }
                    node2 = node;
                    ++n;
                } while (true);
            }
            object = node.m_Value;
            node.m_Value = object2;
            return object;
        }

        private class Node {
            Object m_Value = null;
            final Node[] m_nextChar = new Node[128];

            Node() {
            }
        }

    }

}

