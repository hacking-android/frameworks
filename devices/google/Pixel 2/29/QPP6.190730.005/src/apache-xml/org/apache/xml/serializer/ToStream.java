/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import org.apache.xml.serializer.AttributesImplSerializer;
import org.apache.xml.serializer.CharInfo;
import org.apache.xml.serializer.ElemContext;
import org.apache.xml.serializer.EncodingInfo;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.NamespaceMappings;
import org.apache.xml.serializer.SecuritySupport;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.SerializerTrace;
import org.apache.xml.serializer.SerializerTraceWriter;
import org.apache.xml.serializer.TreeWalker;
import org.apache.xml.serializer.WriterChain;
import org.apache.xml.serializer.WriterToASCI;
import org.apache.xml.serializer.WriterToUTF8Buffered;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.apache.xml.serializer.utils.WrappedRuntimeException;
import org.w3c.dom.Node;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public abstract class ToStream
extends SerializerBase {
    private static final String COMMENT_BEGIN = "<!--";
    private static final String COMMENT_END = "-->";
    private static final char[] s_systemLineSep = SecuritySupport.getInstance().getSystemProperty("line.separator").toCharArray();
    protected boolean m_cdataStartCalled = false;
    protected CharInfo m_charInfo;
    protected BoolStack m_disableOutputEscapingStates = new BoolStack();
    EncodingInfo m_encodingInfo = new EncodingInfo(null, null, '\u0000');
    protected boolean m_escaping = true;
    private boolean m_expandDTDEntities = true;
    protected boolean m_inDoctype = false;
    boolean m_isUTF8 = false;
    protected boolean m_ispreserve = false;
    protected boolean m_isprevtext = false;
    protected char[] m_lineSep = s_systemLineSep;
    protected int m_lineSepLen = this.m_lineSep.length;
    protected boolean m_lineSepUse = true;
    OutputStream m_outputStream;
    protected BoolStack m_preserves = new BoolStack();
    boolean m_shouldFlush = true;
    protected boolean m_spaceBeforeClose = false;
    boolean m_startNewLine;
    private boolean m_writer_set_by_user;

    private void DTDprolog() throws SAXException, IOException {
        Writer writer = this.m_writer;
        if (this.m_needToOutputDocTypeDecl) {
            this.outputDocTypeDecl(this.m_elemContext.m_elementName, false);
            this.m_needToOutputDocTypeDecl = false;
        }
        if (this.m_inDoctype) {
            writer.write(" [");
            writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            this.m_inDoctype = false;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int accumDefaultEscape(Writer object, char c, int n, char[] object2, int n2, boolean bl, boolean bl2) throws IOException {
        int n3 = this.accumDefaultEntity((Writer)object, c, n, (char[])object2, n2, bl, bl2);
        if (n != n3) return n3;
        if (Encodings.isHighUTF16Surrogate(c)) {
            if (n + 1 >= n2) throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[]{Integer.toHexString(c)}));
            Object object3 = object2[n + 1];
            if (Encodings.isLowUTF16Surrogate((char)object3)) {
                n = Encodings.toCodePoint(c, (char)object3);
                ((Writer)object).write("&#");
                ((Writer)object).write(Integer.toString(n));
                ((Writer)object).write(59);
                return n3 + 2;
            }
            object = Utils.messages;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(Integer.toHexString(c));
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(Integer.toHexString((int)object3));
            throw new IOException(((Messages)object).createMessage("ER_INVALID_UTF16_SURROGATE", new Object[]{((StringBuilder)object2).toString()}));
        }
        if (!ToStream.isCharacterInC0orC1Range(c) && !ToStream.isNELorLSEPCharacter(c)) {
            if ((!this.escapingNotNeeded(c) || bl && this.m_charInfo.shouldMapTextChar(c) || !bl && this.m_charInfo.shouldMapAttrChar(c)) && this.m_elemContext.m_currentElemDepth > 0) {
                ((Writer)object).write("&#");
                ((Writer)object).write(Integer.toString(c));
                ((Writer)object).write(59);
                return n3 + 1;
            } else {
                ((Writer)object).write(c);
            }
            return n3 + 1;
        } else {
            ((Writer)object).write("&#");
            ((Writer)object).write(Integer.toString(c));
            ((Writer)object).write(59);
        }
        return n3 + 1;
    }

    private void addCdataSectionElement(String object, Vector vector) {
        object = new StringTokenizer((String)object, "{}", false);
        String string = ((StringTokenizer)object).nextToken();
        if ((object = ((StringTokenizer)object).hasMoreTokens() ? ((StringTokenizer)object).nextToken() : null) == null) {
            vector.addElement(null);
            vector.addElement(string);
        } else {
            vector.addElement(string);
            vector.addElement(object);
        }
    }

    private static boolean isCharacterInC0orC1Range(char c) {
        block2 : {
            boolean bl;
            block4 : {
                block3 : {
                    boolean bl2 = false;
                    if (c == '\t' || c == '\n' || c == '\r') break block2;
                    if (c >= '' && c <= '\u009f') break block3;
                    bl = bl2;
                    if (c < '\u0001') break block4;
                    bl = bl2;
                    if (c > '\u001f') break block4;
                }
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private boolean isEscapingDisabled() {
        return this.m_disableOutputEscapingStates.peekOrFalse();
    }

    private static boolean isNELorLSEPCharacter(char c) {
        boolean bl = c == '\u0085' || c == '\u2028';
        return bl;
    }

    private void printSpace(int n) throws IOException {
        Writer writer = this.m_writer;
        for (int i = 0; i < n; ++i) {
            writer.write(32);
        }
    }

    private int processDirty(char[] arrc, int n, int n2, char c, int n3, boolean bl) throws IOException {
        int n4 = n2;
        if (n4 > ++n3) {
            this.m_writer.write(arrc, n3, n4 - n3);
        }
        if ('\n' == c && bl) {
            this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
        } else {
            n4 = this.accumDefaultEscape(this.m_writer, c, n2, arrc, n, bl, false) - 1;
        }
        return n4;
    }

    private int processLineFeed(char[] arrc, int n, int n2, Writer writer) throws IOException {
        int n3 = n2;
        if (this.m_lineSepUse) {
            if (this.m_lineSepLen == 1 && this.m_lineSep[0] == '\n') {
                n3 = n2;
            } else {
                this.writeOutCleanChars(arrc, n, n2);
                writer.write(this.m_lineSep, 0, this.m_lineSepLen);
                n3 = n;
            }
        }
        return n3;
    }

    private void resetToStream() {
        this.m_cdataStartCalled = false;
        this.m_disableOutputEscapingStates.clear();
        this.m_escaping = true;
        this.m_expandDTDEntities = true;
        this.m_inDoctype = false;
        this.m_ispreserve = false;
        this.m_isprevtext = false;
        this.m_isUTF8 = false;
        char[] arrc = s_systemLineSep;
        this.m_lineSep = arrc;
        this.m_lineSepLen = arrc.length;
        this.m_lineSepUse = true;
        this.m_preserves.clear();
        this.m_shouldFlush = true;
        this.m_spaceBeforeClose = false;
        this.m_startNewLine = false;
        this.m_writer_set_by_user = false;
    }

    private void setCdataSectionElements(String charSequence, Properties object) {
        block6 : {
            if ((object = ((Properties)object).getProperty((String)charSequence)) == null) break block6;
            Vector vector = new Vector();
            int n = ((String)object).length();
            boolean bl = false;
            charSequence = new StringBuffer();
            for (int i = 0; i < n; ++i) {
                boolean bl2;
                block9 : {
                    char c;
                    block8 : {
                        block7 : {
                            c = ((String)object).charAt(i);
                            if (!Character.isWhitespace(c)) break block7;
                            bl2 = bl;
                            if (bl) break block8;
                            bl2 = bl;
                            if (((StringBuffer)charSequence).length() > 0) {
                                this.addCdataSectionElement(((StringBuffer)charSequence).toString(), vector);
                                ((StringBuffer)charSequence).setLength(0);
                                bl2 = bl;
                            }
                            break block9;
                        }
                        if ('{' == c) {
                            bl2 = true;
                        } else {
                            bl2 = bl;
                            if ('}' == c) {
                                bl2 = false;
                            }
                        }
                    }
                    ((StringBuffer)charSequence).append(c);
                }
                bl = bl2;
            }
            if (((StringBuffer)charSequence).length() > 0) {
                this.addCdataSectionElement(((StringBuffer)charSequence).toString(), vector);
                ((StringBuffer)charSequence).setLength(0);
            }
            this.setCdataSectionElements(vector);
        }
    }

    private void setOutputStreamInternal(OutputStream outputStream, boolean bl) {
        this.m_outputStream = outputStream;
        String string = this.getOutputProperty("encoding");
        if ("UTF-8".equalsIgnoreCase(string)) {
            this.setWriterInternal(new WriterToUTF8Buffered(outputStream), false);
        } else if (!("WINDOWS-1250".equals(string) || "US-ASCII".equals(string) || "ASCII".equals(string))) {
            if (string != null) {
                Writer writer;
                try {
                    writer = Encodings.getWriter(outputStream, string);
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    writer = null;
                }
                Closeable closeable = writer;
                if (writer == null) {
                    closeable = System.out;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Warning: encoding \"");
                    stringBuilder.append(string);
                    stringBuilder.append("\" not supported, using ");
                    stringBuilder.append("UTF-8");
                    ((PrintStream)closeable).println(stringBuilder.toString());
                    this.setEncoding("UTF-8");
                    try {
                        closeable = Encodings.getWriter(outputStream, "UTF-8");
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        unsupportedEncodingException.printStackTrace();
                        closeable = writer;
                    }
                }
                this.setWriterInternal((Writer)closeable, false);
            } else {
                this.setWriterInternal(new OutputStreamWriter(outputStream), false);
            }
        } else {
            this.setWriterInternal(new WriterToASCI(outputStream), false);
        }
    }

    private void setWriterInternal(Writer writer, boolean bl) {
        this.m_writer_set_by_user = bl;
        this.m_writer = writer;
        if (this.m_tracer != null) {
            boolean bl2;
            boolean bl3 = true;
            writer = this.m_writer;
            do {
                bl2 = bl3;
                if (!(writer instanceof WriterChain)) break;
                if (writer instanceof SerializerTraceWriter) {
                    bl2 = false;
                    break;
                }
                writer = ((WriterChain)((Object)writer)).getWriter();
            } while (true);
            if (bl2) {
                this.m_writer = new SerializerTraceWriter(this.m_writer, this.m_tracer);
            }
        }
    }

    private void writeOutCleanChars(char[] arrc, int n, int n2) throws IOException {
        if (++n2 < n) {
            this.m_writer.write(arrc, n2, n - n2);
        }
    }

    int accumDefaultEntity(Writer writer, char c, int n, char[] object, int n2, boolean bl, boolean bl2) throws IOException {
        block4 : {
            block5 : {
                block3 : {
                    block2 : {
                        if (bl2 || '\n' != c) break block2;
                        writer.write(this.m_lineSep, 0, this.m_lineSepLen);
                        break block3;
                    }
                    if ((!bl || !this.m_charInfo.shouldMapTextChar(c)) && (bl || !this.m_charInfo.shouldMapAttrChar(c))) break block4;
                    object = this.m_charInfo.getOutputStringForChar(c);
                    if (object == null) break block5;
                    writer.write((String)object);
                }
                return n + 1;
            }
            return n;
        }
        return n;
    }

    @Override
    public boolean addAttributeAlways(String string, String string2, String string3, String string4, String string5, boolean bl) {
        int n = string != null && string2 != null && string.length() != 0 ? this.m_attributes.getIndex(string, string2) : this.m_attributes.getIndex(string3);
        if (n >= 0) {
            string = null;
            if (this.m_tracer != null) {
                string = string2 = this.m_attributes.getValue(n);
                if (string5.equals(string2)) {
                    string = null;
                }
            }
            this.m_attributes.setValue(n, string5);
            bl = false;
            if (string != null) {
                this.firePseudoAttributes();
            }
        } else {
            boolean bl2;
            CharSequence charSequence = string3;
            if (bl) {
                n = string3.indexOf(58);
                charSequence = string3;
                if (n > 0) {
                    charSequence = string3.substring(0, n);
                    NamespaceMappings.MappingRecord mappingRecord = this.m_prefixMap.getMappingFromPrefix((String)charSequence);
                    charSequence = string3;
                    if (mappingRecord != null) {
                        charSequence = string3;
                        if (mappingRecord.m_declarationDepth == this.m_elemContext.m_currentElemDepth) {
                            charSequence = string3;
                            if (!mappingRecord.m_uri.equals(string)) {
                                charSequence = this.m_prefixMap.lookupPrefix(string);
                                string3 = charSequence;
                                if (charSequence == null) {
                                    string3 = this.m_prefixMap.generateNextPrefix();
                                }
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append(string3);
                                ((StringBuilder)charSequence).append(':');
                                ((StringBuilder)charSequence).append(string2);
                                charSequence = ((StringBuilder)charSequence).toString();
                            }
                        }
                    }
                }
                try {
                    this.ensureAttributesNamespaceIsDeclared(string, string2, (String)charSequence);
                }
                catch (SAXException sAXException) {
                    sAXException.printStackTrace();
                }
            }
            this.m_attributes.addAttribute(string, string2, (String)charSequence, string4, string5);
            bl = bl2 = true;
            if (this.m_tracer != null) {
                this.firePseudoAttributes();
                bl = bl2;
            }
        }
        return bl;
    }

    public void addCdataSectionElements(String string) {
        if (string != null) {
            this.initCdataElems(string);
        }
        if (this.m_StringOfCDATASections == null) {
            this.m_StringOfCDATASections = string;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.m_StringOfCDATASections);
            stringBuilder.append(" ");
            stringBuilder.append(string);
            this.m_StringOfCDATASections = stringBuilder.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void attributeDecl(String string, String string2, String string3, String string4, String object) throws SAXException {
        if (this.m_inExternalDTD) {
            return;
        }
        try {
            object = this.m_writer;
            this.DTDprolog();
            ((Writer)object).write("<!ATTLIST ");
            ((Writer)object).write(string);
            ((Writer)object).write(32);
            ((Writer)object).write(string2);
            ((Writer)object).write(32);
            ((Writer)object).write(string3);
            if (string4 != null) {
                ((Writer)object).write(32);
                ((Writer)object).write(string4);
            }
            ((Writer)object).write(62);
            ((Writer)object).write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void cdata(char[] var1_1, int var2_3, int var3_4) throws SAXException {
        block8 : {
            try {
                var4_5 = this.m_elemContext.m_startTagOpen;
                var5_6 = false;
                if (var4_5) {
                    this.closeStartTag();
                    this.m_elemContext.m_startTagOpen = false;
                }
                this.m_ispreserve = true;
                if (this.shouldIndent()) {
                    this.indent();
                }
                if (var3_4 >= 1 && this.escapingNotNeeded(var1_1[var2_3])) {
                    var5_6 = true;
                }
                if (var5_6 && !this.m_cdataTagOpen) {
                    this.m_writer.write("<![CDATA[");
                    this.m_cdataTagOpen = true;
                }
                if (this.isEscapingDisabled()) {
                    this.charactersRaw(var1_1, var2_3, var3_4);
                    break block8;
                }
                this.writeNormalizedChars(var1_1, var2_3, var3_4, true, this.m_lineSepUse);
            }
            catch (IOException var1_2) {
                throw new SAXException(Utils.messages.createMessage("ER_OIERROR", null), var1_2);
            }
        }
        if (!var5_6 || var1_1[var2_3 + var3_4 - 1] != ']') ** GOTO lbl25
        this.closeCDATA();
lbl25: // 2 sources:
        if (this.m_tracer == null) return;
        super.fireCDATAEvent(var1_1, var2_3, var3_4);
    }

    @Override
    public void characters(String string) throws SAXException {
        if (this.m_inEntityRef && !this.m_expandDTDEntities) {
            return;
        }
        int n = string.length();
        if (n > this.m_charsBuff.length) {
            this.m_charsBuff = new char[n * 2 + 1];
        }
        string.getChars(0, n, this.m_charsBuff, 0);
        this.characters(this.m_charsBuff, 0, n);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void characters(char[] var1_1, int var2_3, int var3_4) throws SAXException {
        block42 : {
            block43 : {
                block45 : {
                    block44 : {
                        if (var3_4 == 0 || this.m_inEntityRef && !this.m_expandDTDEntities) return;
                        this.m_docIsEmpty = false;
                        if (this.m_elemContext.m_startTagOpen) {
                            this.closeStartTag();
                            this.m_elemContext.m_startTagOpen = false;
                        } else if (this.m_needToCallStartDocument) {
                            this.startDocumentInternal();
                        }
                        if (this.m_cdataStartCalled || this.m_elemContext.m_isCdataSection) break block42;
                        if (this.m_cdataTagOpen) {
                            this.closeCDATA();
                        }
                        if (this.m_disableOutputEscapingStates.peekOrFalse() || !this.m_escaping) break block43;
                        if (this.m_elemContext.m_startTagOpen) {
                            this.closeStartTag();
                            this.m_elemContext.m_startTagOpen = false;
                        }
                        var4_5 = var2_3 + var3_4;
                        var5_6 = var2_3 - 1;
                        var6_7 = this.m_writer;
                        var7_8 = true;
                        var8_9 = var2_3;
                        while (var8_9 < var4_5 && var7_8) {
                            block33 : {
                                block34 : {
                                    block35 : {
                                        var9_10 = var1_1[var8_9];
                                        if (this.m_charInfo.shouldMapTextChar(var9_10)) {
                                            this.writeOutCleanChars(var1_1, var8_9, var5_6);
                                            var6_7.write(this.m_charInfo.getOutputStringForChar(var9_10));
                                            var7_8 = false;
                                            var5_6 = var8_9;
                                            var10_11 = var8_9 + 1;
                                        }
                                        if (var9_10 == '\t') break block33;
                                        if (var9_10 == '\n') break block34;
                                        if (var9_10 == '\r') break block35;
                                        if (var9_10 != ' ') {
                                            var7_8 = false;
                                            var10_11 = var8_9;
                                        }
                                        var10_11 = var8_9 + 1;
                                    }
                                    this.writeOutCleanChars(var1_1, var8_9, var5_6);
                                    var6_7.write("&#13;");
                                    var5_6 = var8_9;
                                    var10_11 = var8_9 + 1;
                                }
                                var5_6 = this.processLineFeed(var1_1, var8_9, var5_6, var6_7);
                                var10_11 = var8_9 + 1;
                            }
                            var10_11 = var8_9 + 1;
                            var8_9 = var10_11;
                        }
                        if (var8_9 < var4_5) break block44;
                        var11_12 = var5_6;
                        var10_11 = var8_9;
                        if (var7_8) break block45;
                    }
                    this.m_ispreserve = true;
                    var10_11 = var8_9;
                    var11_12 = var5_6;
                }
                while (var10_11 < var4_5) {
                    block36 : {
                        block37 : {
                            block38 : {
                                block39 : {
                                    block40 : {
                                        var9_10 = var1_1[var10_11];
                                        if (this.m_charInfo.shouldMapTextChar(var9_10)) {
                                            this.writeOutCleanChars(var1_1, var10_11, var11_12);
                                            var6_7.write(this.m_charInfo.getOutputStringForChar(var9_10));
                                            var8_9 = var10_11;
                                            break block36;
                                        }
                                        if (var9_10 > '\u001f') break block37;
                                        if (var9_10 == '\t') break block38;
                                        if (var9_10 == '\n') break block39;
                                        if (var9_10 == '\r') break block40;
                                        this.writeOutCleanChars(var1_1, var10_11, var11_12);
                                        var6_7.write("&#");
                                        var6_7.write(Integer.toString(var9_10));
                                        var6_7.write(59);
                                        var8_9 = var10_11;
                                    }
                                    this.writeOutCleanChars(var1_1, var10_11, var11_12);
                                    var6_7.write("&#13;");
                                    var8_9 = var10_11;
                                }
                                var8_9 = this.processLineFeed(var1_1, var10_11, var11_12, var6_7);
                            }
                            var8_9 = var11_12;
                            break block36;
                        }
                        if (var9_10 < '') {
                            var8_9 = var11_12;
                        } else {
                            block41 : {
                                if (var9_10 <= '\u009f') {
                                    this.writeOutCleanChars(var1_1, var10_11, var11_12);
                                    var6_7.write("&#");
                                    var6_7.write(Integer.toString(var9_10));
                                    var6_7.write(59);
                                    var8_9 = var10_11;
                                }
                                if (var9_10 == '\u2028') {
                                    this.writeOutCleanChars(var1_1, var10_11, var11_12);
                                    var6_7.write("&#8232;");
                                    var8_9 = var10_11;
                                }
                                if (!this.m_encodingInfo.isInEncoding(var9_10)) break block41;
                                var8_9 = var11_12;
                            }
                            this.writeOutCleanChars(var1_1, var10_11, var11_12);
                            var6_7.write("&#");
                            var6_7.write(Integer.toString(var9_10));
                            var6_7.write(59);
                            var8_9 = var10_11;
                        }
                    }
                    ++var10_11;
                    var11_12 = var8_9;
                }
                var8_9 = var11_12 + 1;
                if (var10_11 <= var8_9) ** GOTO lbl136
                try {
                    this.m_writer.write(var1_1, var8_9, var10_11 - var8_9);
lbl136: // 2 sources:
                    this.m_isprevtext = true;
                    if (this.m_tracer == null) return;
                    super.fireCharEvent(var1_1, var2_3, var3_4);
                    return;
                }
                catch (IOException var1_2) {
                    throw new SAXException(var1_2);
                }
            }
            this.charactersRaw(var1_1, var2_3, var3_4);
            if (this.m_tracer == null) return;
            super.fireCharEvent(var1_1, var2_3, var3_4);
            return;
        }
        this.cdata(var1_1, var2_3, var3_4);
    }

    protected void charactersRaw(char[] arrc, int n, int n2) throws SAXException {
        if (this.m_inEntityRef) {
            return;
        }
        try {
            if (this.m_elemContext.m_startTagOpen) {
                this.closeStartTag();
                this.m_elemContext.m_startTagOpen = false;
            }
            this.m_ispreserve = true;
            this.m_writer.write(arrc, n, n2);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    protected void closeCDATA() throws SAXException {
        try {
            this.m_writer.write("]]>");
            this.m_cdataTagOpen = false;
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void closeStartTag() throws SAXException {
        block4 : {
            if (!this.m_elemContext.m_startTagOpen) return;
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
                if (this.m_CdataElems == null) break block4;
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
            this.m_elemContext.m_isCdataSection = this.isCdataSection();
        }
        if (!this.m_doIndent) return;
        this.m_isprevtext = false;
        this.m_preserves.push(this.m_ispreserve);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void comment(char[] var1_1, int var2_3, int var3_4) throws SAXException {
        if (this.m_inEntityRef) {
            return;
        }
        if (this.m_elemContext.m_startTagOpen) {
            this.closeStartTag();
            this.m_elemContext.m_startTagOpen = false;
        } else if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
            this.m_needToCallStartDocument = false;
        }
        var4_5 = var2_3 + var3_4;
        var5_6 = false;
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
        }
        if (this.shouldIndent()) {
            this.indent();
        }
        var6_7 = this.m_writer;
        var6_7.write("<!--");
        var7_8 = var2_3;
        var8_9 = var2_3;
        do {
            var9_10 = var8_9;
            var10_11 = true;
            if (var7_8 >= var4_5) break;
            var8_9 = var9_10;
            if (var5_6) {
                var8_9 = var9_10;
                if (var1_1[var7_8] == '-') {
                    var6_7.write(var1_1, var9_10, var7_8 - var9_10);
                    var6_7.write(" -");
                    var8_9 = var7_8 + 1;
                }
            }
            var5_6 = var1_1[var7_8] == '-' ? var10_11 : false;
            ++var7_8;
        } while (true);
        if (var3_4 <= 0) ** GOTO lbl44
        var8_9 = var4_5 - var9_10;
        if (var8_9 > 0) {
            var6_7.write(var1_1, var9_10, var8_9);
        }
        if (var1_1[var4_5 - 1] != '-') ** GOTO lbl44
        try {
            var6_7.write(32);
lbl44: // 3 sources:
            var6_7.write("-->");
            this.m_startNewLine = true;
            if (this.m_tracer == null) return;
            super.fireCommentEvent(var1_1, var2_3, var3_4);
            return;
        }
        catch (IOException var1_2) {
            throw new SAXException(var1_2);
        }
    }

    @Override
    public void elementDecl(String string, String string2) throws SAXException {
        if (this.m_inExternalDTD) {
            return;
        }
        try {
            Writer writer = this.m_writer;
            this.DTDprolog();
            writer.write("<!ELEMENT ");
            writer.write(string);
            writer.write(32);
            writer.write(string2);
            writer.write(62);
            writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void endCDATA() throws SAXException {
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
        }
        this.m_cdataStartCalled = false;
    }

    @Override
    public void endDTD() throws SAXException {
        try {
            if (this.m_needToOutputDocTypeDecl) {
                this.outputDocTypeDecl(this.m_elemContext.m_elementName, false);
                this.m_needToOutputDocTypeDecl = false;
            }
            Writer writer = this.m_writer;
            if (!this.m_inDoctype) {
                writer.write("]>");
            } else {
                writer.write(62);
            }
            writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    @Override
    public void endElement(String string) throws SAXException {
        this.endElement(null, null, string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void endElement(String object, String string, String string2) throws SAXException {
        if (this.m_inEntityRef) {
            return;
        }
        this.m_prefixMap.popNamespaces(this.m_elemContext.m_currentElemDepth, null);
        try {
            object = this.m_writer;
            if (this.m_elemContext.m_startTagOpen) {
                int n;
                if (this.m_tracer != null) {
                    super.fireStartElem(this.m_elemContext.m_elementName);
                }
                if ((n = this.m_attributes.getLength()) > 0) {
                    this.processAttributes(this.m_writer, n);
                    this.m_attributes.clear();
                }
                if (this.m_spaceBeforeClose) {
                    ((Writer)object).write(" />");
                } else {
                    ((Writer)object).write("/>");
                }
            } else {
                if (this.m_cdataTagOpen) {
                    this.closeCDATA();
                }
                if (this.shouldIndent()) {
                    this.indent(this.m_elemContext.m_currentElemDepth - 1);
                }
                ((Writer)object).write(60);
                ((Writer)object).write(47);
                ((Writer)object).write(string2);
                ((Writer)object).write(62);
            }
            if (!this.m_elemContext.m_startTagOpen && this.m_doIndent) {
                boolean bl = this.m_preserves.isEmpty() ? false : this.m_preserves.pop();
                this.m_ispreserve = bl;
            }
            this.m_isprevtext = false;
            if (this.m_tracer != null) {
                super.fireEndElem(string2);
            }
            this.m_elemContext = this.m_elemContext.m_prev;
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    public void endNonEscaping() throws SAXException {
        this.m_disableOutputEscapingStates.pop();
    }

    @Override
    public void endPrefixMapping(String string) throws SAXException {
    }

    protected String ensureAttributesNamespaceIsDeclared(String string, String string2, String charSequence) throws SAXException {
        if (string != null && string.length() > 0) {
            int n = ((String)charSequence).indexOf(":");
            string2 = n < 0 ? "" : ((String)charSequence).substring(0, n);
            if (n > 0) {
                charSequence = this.m_prefixMap.lookupNamespace(string2);
                if (charSequence != null && ((String)charSequence).equals(string)) {
                    return null;
                }
                this.startPrefixMapping(string2, string, false);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("xmlns:");
                ((StringBuilder)charSequence).append(string2);
                this.addAttribute("http://www.w3.org/2000/xmlns/", string2, ((StringBuilder)charSequence).toString(), "CDATA", string, false);
                return string2;
            }
            string2 = this.m_prefixMap.lookupPrefix(string);
            if (string2 == null) {
                string2 = this.m_prefixMap.generateNextPrefix();
                this.startPrefixMapping(string2, string, false);
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("xmlns:");
                ((StringBuilder)charSequence).append(string2);
                this.addAttribute("http://www.w3.org/2000/xmlns/", string2, ((StringBuilder)charSequence).toString(), "CDATA", string, false);
                string = string2;
            } else {
                string = string2;
            }
            return string;
        }
        return null;
    }

    void ensurePrefixIsDeclared(String string, String string2) throws SAXException {
        if (string != null && string.length() > 0) {
            String string3;
            int n = string2.indexOf(":");
            boolean bl = n < 0;
            string2 = bl ? "" : string2.substring(0, n);
            if (!(string2 == null || (string3 = this.m_prefixMap.lookupNamespace(string2)) != null && string3.equals(string))) {
                this.startPrefixMapping(string2, string);
                CharSequence charSequence = "xmlns";
                string3 = bl ? "xmlns" : string2;
                if (bl) {
                    string2 = charSequence;
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("xmlns:");
                    ((StringBuilder)charSequence).append(string2);
                    string2 = ((StringBuilder)charSequence).toString();
                }
                this.addAttributeAlways("http://www.w3.org/2000/xmlns/", string3, string2, "CDATA", string, false);
            }
        }
    }

    protected boolean escapingNotNeeded(char c) {
        boolean bl = c < '' ? c >= ' ' || '\n' == c || '\r' == c || '\t' == c : this.m_encodingInfo.isInEncoding(c);
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void externalEntityDecl(String string, String string2, String string3) throws SAXException {
        try {
            this.DTDprolog();
            this.m_writer.write("<!ENTITY ");
            this.m_writer.write(string);
            if (string2 != null) {
                this.m_writer.write(" PUBLIC \"");
                this.m_writer.write(string2);
            } else {
                this.m_writer.write(" SYSTEM \"");
                this.m_writer.write(string3);
            }
            this.m_writer.write("\" >");
            this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void firePseudoAttributes() {
        if (this.m_tracer == null) return;
        try {
            char[] arrc;
            this.m_writer.flush();
            StringBuffer stringBuffer = new StringBuffer();
            int n = this.m_attributes.getLength();
            if (n > 0) {
                arrc = new WritertoStringBuffer(stringBuffer);
                this.processAttributes((Writer)arrc, n);
            }
            stringBuffer.append('>');
            arrc = stringBuffer.toString().toCharArray();
            this.m_tracer.fireGenerateEvent(11, arrc, 0, arrc.length);
            return;
        }
        catch (SAXException sAXException) {
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void flushPending() throws SAXException {
        if (this.m_needToCallStartDocument) {
            this.startDocumentInternal();
            this.m_needToCallStartDocument = false;
        }
        if (this.m_elemContext.m_startTagOpen) {
            this.closeStartTag();
            this.m_elemContext.m_startTagOpen = false;
        }
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
            this.m_cdataTagOpen = false;
        }
        if (this.m_writer != null) {
            try {
                this.m_writer.flush();
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
    }

    protected final void flushWriter() throws SAXException {
        Writer writer = this.m_writer;
        if (writer != null) {
            try {
                if (writer instanceof WriterToUTF8Buffered) {
                    if (this.m_shouldFlush) {
                        ((WriterToUTF8Buffered)writer).flush();
                    } else {
                        ((WriterToUTF8Buffered)writer).flushBuffer();
                    }
                }
                if (writer instanceof WriterToASCI) {
                    if (this.m_shouldFlush) {
                        writer.flush();
                    }
                } else {
                    writer.flush();
                }
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
        }
    }

    @Override
    public int getIndentAmount() {
        return this.m_indentAmount;
    }

    @Override
    public Properties getOutputFormat() {
        Object object = new Properties();
        for (Object object2 : this.getOutputPropDefaultKeys()) {
            ((Hashtable)object).put(object2, this.getOutputPropertyDefault((String)object2));
        }
        Properties properties = new Properties((Properties)object);
        for (Object object3 : this.getOutputPropKeys()) {
            object = this.getOutputPropertyNonDefault((String)object3);
            if (object == null) continue;
            properties.put(object3, object);
        }
        return properties;
    }

    @Override
    public OutputStream getOutputStream() {
        return this.m_outputStream;
    }

    @Override
    public Writer getWriter() {
        return this.m_writer;
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
        if (n2 == 0) {
            return;
        }
        this.characters(arrc, n, n2);
    }

    protected void indent() throws IOException {
        this.indent(this.m_elemContext.m_currentElemDepth);
    }

    protected void indent(int n) throws IOException {
        if (this.m_startNewLine) {
            this.outputLineSep();
        }
        if (this.m_indentAmount > 0) {
            this.printSpace(this.m_indentAmount * n);
        }
    }

    @Override
    public void internalEntityDecl(String string, String string2) throws SAXException {
        if (this.m_inExternalDTD) {
            return;
        }
        try {
            this.DTDprolog();
            this.outputEntityDecl(string, string2);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void notationDecl(String string, String string2, String string3) throws SAXException {
        try {
            this.DTDprolog();
            this.m_writer.write("<!NOTATION ");
            this.m_writer.write(string);
            if (string2 != null) {
                this.m_writer.write(" PUBLIC \"");
                this.m_writer.write(string2);
            } else {
                this.m_writer.write(" SYSTEM \"");
                this.m_writer.write(string3);
            }
            this.m_writer.write("\" >");
            this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void outputDocTypeDecl(String string, boolean bl) throws SAXException {
        if (this.m_cdataTagOpen) {
            this.closeCDATA();
        }
        try {
            Writer writer = this.m_writer;
            writer.write("<!DOCTYPE ");
            writer.write(string);
            String string2 = this.getDoctypePublic();
            if (string2 != null) {
                writer.write(" PUBLIC \"");
                writer.write(string2);
                writer.write(34);
            }
            if ((string = this.getDoctypeSystem()) == null) return;
            if (string2 == null) {
                writer.write(" SYSTEM \"");
            } else {
                writer.write(" \"");
            }
            writer.write(string);
            if (bl) {
                writer.write("\">");
                writer.write(this.m_lineSep, 0, this.m_lineSepLen);
                return;
            }
            writer.write(34);
            return;
        }
        catch (IOException iOException) {
            throw new SAXException(iOException);
        }
    }

    void outputEntityDecl(String string, String string2) throws IOException {
        Writer writer = this.m_writer;
        writer.write("<!ENTITY ");
        writer.write(string);
        writer.write(" \"");
        writer.write(string2);
        writer.write("\">");
        writer.write(this.m_lineSep, 0, this.m_lineSepLen);
    }

    protected final void outputLineSep() throws IOException {
        this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
    }

    public void processAttributes(Writer writer, int n) throws IOException, SAXException {
        String string = this.getEncoding();
        for (int i = 0; i < n; ++i) {
            String string2 = this.m_attributes.getQName(i);
            String string3 = this.m_attributes.getValue(i);
            writer.write(32);
            writer.write(string2);
            writer.write("=\"");
            this.writeAttrString(writer, string3, string);
            writer.write(34);
        }
    }

    @Override
    public boolean reset() {
        boolean bl = false;
        if (super.reset()) {
            this.resetToStream();
            bl = true;
        }
        return bl;
    }

    @Override
    public void serialize(Node node) throws IOException {
        try {
            TreeWalker treeWalker = new TreeWalker(this);
            treeWalker.traverse(node);
            return;
        }
        catch (SAXException sAXException) {
            throw new WrappedRuntimeException(sAXException);
        }
    }

    @Override
    public void setCdataSectionElements(Vector vector) {
        int n;
        if (vector != null && (n = vector.size() - 1) > 0) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < n; i += 2) {
                if (i != 0) {
                    stringBuffer.append(' ');
                }
                String string = (String)vector.elementAt(i);
                String string2 = (String)vector.elementAt(i + 1);
                if (string != null) {
                    stringBuffer.append('{');
                    stringBuffer.append(string);
                    stringBuffer.append('}');
                }
                stringBuffer.append(string2);
            }
            this.m_StringOfCDATASections = stringBuffer.toString();
        }
        this.initCdataElems(this.m_StringOfCDATASections);
    }

    @Override
    public void setContentHandler(ContentHandler contentHandler) {
    }

    @Override
    public void setDTDEntityExpansion(boolean bl) {
        this.m_expandDTDEntities = bl;
    }

    @Override
    public void setEncoding(String string) {
        this.setOutputProperty("encoding", string);
    }

    @Override
    public boolean setEscaping(boolean bl) {
        boolean bl2 = this.m_escaping;
        this.m_escaping = bl;
        return bl2;
    }

    @Override
    public void setIndentAmount(int n) {
        this.m_indentAmount = n;
    }

    public boolean setLineSepUse(boolean bl) {
        boolean bl2 = this.m_lineSepUse;
        this.m_lineSepUse = bl;
        return bl2;
    }

    public void setNewLine(char[] arrc) {
        this.m_lineSep = arrc;
        this.m_lineSepLen = arrc.length;
    }

    @Override
    public void setOutputFormat(Properties properties) {
        Object object;
        boolean bl = this.m_shouldFlush;
        if (properties != null) {
            object = properties.propertyNames();
            while (object.hasMoreElements()) {
                String string = (String)object.nextElement();
                String string2 = properties.getProperty(string);
                String string3 = (String)properties.get(string);
                if (string3 == null && string2 != null) {
                    this.setOutputPropertyDefault(string, string2);
                }
                if (string3 == null) continue;
                this.setOutputProperty(string, string3);
            }
        }
        if ((object = (String)properties.get("{http://xml.apache.org/xalan}entities")) != null) {
            this.m_charInfo = CharInfo.getCharInfo((String)object, (String)properties.get("method"));
        }
        this.m_shouldFlush = bl;
    }

    @Override
    public void setOutputStream(OutputStream outputStream) {
        this.setOutputStreamInternal(outputStream, true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    void setProp(String var1_1, String var2_2, boolean var3_5) {
        block30 : {
            block24 : {
                block25 : {
                    block26 : {
                        block27 : {
                            block28 : {
                                block29 : {
                                    var4_6 = var2_2;
                                    if (var4_6 == null) return;
                                    var5_7 = ToStream.getFirstCharLocName(var1_1);
                                    if (var5_7 == 'i') break block24;
                                    if (var5_7 == 'o') break block25;
                                    if (var5_7 == 's') break block26;
                                    if (var5_7 == 'v') break block27;
                                    if (var5_7 == 'l') break block28;
                                    if (var5_7 == 'm') break block29;
                                    switch (var5_7) {
                                        default: {
                                            var6_8 = var4_6;
                                            ** break;
                                        }
                                        case 'e': {
                                            var6_8 = var2_2;
                                            if (!"encoding".equals(var1_1)) ** GOTO lbl85
                                            if ((var2_2 = Encodings.getMimeEncoding((String)var2_2)) != null) {
                                                super.setProp("mime-name", (String)var2_2, var3_5);
                                            }
                                            var7_9 = this.getOutputPropertyNonDefault("encoding");
                                            var2_2 = this.getOutputPropertyDefault("encoding");
                                            if (var3_5 && (var2_2 == null || !var2_2.equalsIgnoreCase((String)var6_8))) ** GOTO lbl26
                                            if (var3_5) ** GOTO lbl82
                                            if (var7_9 == null || !var7_9.equalsIgnoreCase((String)var6_8)) ** GOTO lbl26
                                            var2_2 = var4_6;
                                            ** GOTO lbl83
lbl26: // 2 sources:
                                            var8_10 = Encodings.getEncodingInfo((String)var6_8);
                                            if (var8_10.name != null) ** GOTO lbl62
                                            var2_2 = Utils.messages.createMessage("ER_ENCODING_NOT_SUPPORTED", new Object[]{var6_8});
                                            var4_6 = new StringBuilder();
                                            var4_6.append("Warning: encoding \"");
                                            var4_6.append((String)var6_8);
                                            var4_6.append("\" not supported, using ");
                                            var4_6.append("UTF-8");
                                            var4_6 = var4_6.toString();
                                            var6_8 = super.getTransformer();
                                            if (var6_8 == null) ** GOTO lbl52
                                            if ((var6_8 = var6_8.getErrorListener()) == null || this.m_sourceLocator == null) ** GOTO lbl49
                                            try {
                                                var8_10 = new TransformerException((String)var2_2, this.m_sourceLocator);
                                                var6_8.warning((TransformerException)var8_10);
                                                var2_2 = new TransformerException((String)var4_6, this.m_sourceLocator);
                                                var6_8.warning((TransformerException)var2_2);
                                                ** GOTO lbl59
lbl49: // 1 sources:
                                                System.out.println((String)var2_2);
                                                System.out.println((String)var4_6);
                                                ** GOTO lbl59
lbl52: // 1 sources:
                                                System.out.println((String)var2_2);
                                                System.out.println((String)var4_6);
                                            }
                                            catch (Exception var2_3) {}
                                            ** GOTO lbl59
                                            catch (Exception var2_4) {
                                                // empty catch block
                                            }
lbl59: // 5 sources:
                                            var4_6 = "UTF-8";
                                            var8_10 = Encodings.getEncodingInfo("UTF-8");
                                            var6_8 = "UTF-8";
lbl62: // 2 sources:
                                            if (!var3_5) ** GOTO lbl65
                                            var2_2 = var4_6;
                                            if (var7_9 != null) ** GOTO lbl83
lbl65: // 2 sources:
                                            this.m_encodingInfo = var8_10;
                                            this.m_isUTF8 = var6_8.equals("UTF-8");
                                            var8_10 = this.getOutputStream();
                                            var2_2 = var4_6;
                                            if (var8_10 == null) ** GOTO lbl83
                                            var2_2 = this.getWriter();
                                            var7_9 = this.getOutputProperty("encoding");
                                            if (var2_2 == null) ** GOTO lbl75
                                            var2_2 = var4_6;
                                            if (this.m_writer_set_by_user) ** GOTO lbl83
lbl75: // 2 sources:
                                            var2_2 = var4_6;
                                            if (!var6_8.equalsIgnoreCase(var7_9)) {
                                                super.setProp(var1_1, (String)var4_6, var3_5);
                                                this.setOutputStreamInternal((OutputStream)var8_10, false);
                                                var2_2 = var4_6;
                                            }
                                            ** GOTO lbl83
lbl82: // 1 sources:
                                            var2_2 = var4_6;
lbl83: // 6 sources:
                                            var6_8 = var2_2;
                                            ** break;
lbl85: // 1 sources:
                                            var6_8 = var4_6;
                                            ** break;
                                        }
                                        case 'd': {
                                            if ("doctype-system".equals(var1_1)) {
                                                this.m_doctypeSystem = var4_6;
                                                var6_8 = var4_6;
                                                ** break;
                                            }
                                            var6_8 = var4_6;
                                            if (!"doctype-public".equals(var1_1)) break;
                                            this.m_doctypePublic = var4_6;
                                            var6_8 = var4_6;
                                            if (!var4_6.startsWith("-//W3C//DTD XHTML")) break;
                                            this.m_spaceBeforeClose = true;
                                            var6_8 = var4_6;
                                            ** break;
                                        }
                                        case 'c': {
                                            var6_8 = var4_6;
                                            if (!"cdata-section-elements".equals(var1_1)) break;
                                            this.addCdataSectionElements((String)var2_2);
                                            var6_8 = var4_6;
                                            ** break;
lbl106: // 6 sources:
                                            break;
                                        }
                                    }
                                    break block30;
                                }
                                var6_8 = var4_6;
                                if ("media-type".equals(var1_1)) {
                                    this.m_mediatype = var4_6;
                                    var6_8 = var4_6;
                                }
                                break block30;
                            }
                            var6_8 = var4_6;
                            if ("{http://xml.apache.org/xalan}line-separator".equals(var1_1)) {
                                this.m_lineSep = var2_2.toCharArray();
                                this.m_lineSepLen = this.m_lineSep.length;
                                var6_8 = var4_6;
                            }
                            break block30;
                        }
                        var6_8 = var4_6;
                        if ("version".equals(var1_1)) {
                            this.m_version = var4_6;
                            var6_8 = var4_6;
                        }
                        break block30;
                    }
                    var6_8 = var4_6;
                    if ("standalone".equals(var1_1)) {
                        if (var3_5) {
                            this.setStandaloneInternal((String)var4_6);
                            var6_8 = var4_6;
                        } else {
                            this.m_standaloneWasSpecified = true;
                            this.setStandaloneInternal((String)var4_6);
                            var6_8 = var4_6;
                        }
                    }
                    break block30;
                }
                var6_8 = var4_6;
                if ("omit-xml-declaration".equals(var1_1)) {
                    this.m_shouldNotWriteXMLHeader = "yes".equals(var4_6);
                    var6_8 = var4_6;
                }
                break block30;
            }
            if ("{http://xml.apache.org/xalan}indent-amount".equals(var1_1)) {
                this.setIndentAmount(Integer.parseInt((String)var2_2));
                var6_8 = var4_6;
            } else {
                var6_8 = var4_6;
                if ("indent".equals(var1_1)) {
                    this.m_doIndent = "yes".equals(var4_6);
                    var6_8 = var4_6;
                }
            }
        }
        super.setProp(var1_1, (String)var6_8, var3_5);
    }

    @Override
    public void setTransformer(Transformer transformer) {
        super.setTransformer(transformer);
        if (this.m_tracer != null && !(this.m_writer instanceof SerializerTraceWriter)) {
            this.setWriterInternal(new SerializerTraceWriter(this.m_writer, this.m_tracer), false);
        }
    }

    @Override
    public void setWriter(Writer writer) {
        this.setWriterInternal(writer, true);
    }

    protected boolean shouldIndent() {
        boolean bl = this.m_doIndent && !this.m_ispreserve && !this.m_isprevtext && this.m_elemContext.m_currentElemDepth > 0;
        return bl;
    }

    @Override
    public void skippedEntity(String string) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
        this.m_cdataStartCalled = true;
    }

    @Override
    public void startDTD(String string, String string2, String string3) throws SAXException {
        this.setDoctypeSystem(string3);
        this.setDoctypePublic(string2);
        this.m_elemContext.m_elementName = string;
        this.m_inDoctype = true;
    }

    @Override
    public void startElement(String string) throws SAXException {
        this.startElement(null, null, string, null);
    }

    @Override
    public void startElement(String string, String string2, String string3) throws SAXException {
        this.startElement(string, string2, string3, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        block12 : {
            if (this.m_inEntityRef) {
                return;
            }
            if (this.m_needToCallStartDocument) {
                this.startDocumentInternal();
                this.m_needToCallStartDocument = false;
                this.m_docIsEmpty = false;
            } else if (this.m_cdataTagOpen) {
                this.closeCDATA();
            }
            try {
                if (this.m_needToOutputDocTypeDecl) {
                    if (this.getDoctypeSystem() != null) {
                        this.outputDocTypeDecl(string3, true);
                    }
                    this.m_needToOutputDocTypeDecl = false;
                }
                if (this.m_elemContext.m_startTagOpen) {
                    this.closeStartTag();
                    this.m_elemContext.m_startTagOpen = false;
                }
                if (string != null) {
                    this.ensurePrefixIsDeclared(string, string3);
                }
                this.m_ispreserve = false;
                if (this.shouldIndent() && this.m_startNewLine) {
                    this.indent();
                }
                this.m_startNewLine = true;
                Writer writer = this.m_writer;
                writer.write(60);
                writer.write(string3);
                if (attributes == null) break block12;
            }
            catch (IOException iOException) {
                throw new SAXException(iOException);
            }
            this.addAttributes(attributes);
        }
        this.m_elemContext = this.m_elemContext.push(string, string2, string3);
        this.m_isprevtext = false;
        if (this.m_tracer != null) {
            this.firePseudoAttributes();
        }
    }

    @Override
    public void startEntity(String string) throws SAXException {
        if (string.equals("[dtd]")) {
            this.m_inExternalDTD = true;
        }
        if (!this.m_expandDTDEntities && !this.m_inExternalDTD) {
            this.startNonEscaping();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("&");
            stringBuilder.append(string);
            stringBuilder.append(';');
            this.characters(stringBuilder.toString());
            this.endNonEscaping();
        }
        this.m_inEntityRef = true;
    }

    public void startNonEscaping() throws SAXException {
        this.m_disableOutputEscapingStates.push(true);
    }

    @Override
    public void startPrefixMapping(String string, String string2) throws SAXException {
        this.startPrefixMapping(string, string2, true);
    }

    @Override
    public boolean startPrefixMapping(String string, String string2, boolean bl) throws SAXException {
        int n;
        if (bl) {
            this.flushPending();
            n = this.m_elemContext.m_currentElemDepth + 1;
        } else {
            n = this.m_elemContext.m_currentElemDepth;
        }
        bl = this.m_prefixMap.pushNamespace(string, string2, n);
        if (bl) {
            if ("".equals(string)) {
                this.addAttributeAlways("http://www.w3.org/2000/xmlns/", "xmlns", "xmlns", "CDATA", string2, false);
            } else if (!"".equals(string2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("xmlns:");
                stringBuilder.append(string);
                this.addAttributeAlways("http://www.w3.org/2000/xmlns/", string, stringBuilder.toString(), "CDATA", string2, false);
            }
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unparsedEntityDecl(String string, String string2, String string3, String string4) throws SAXException {
        try {
            this.DTDprolog();
            this.m_writer.write("<!ENTITY ");
            this.m_writer.write(string);
            if (string2 != null) {
                this.m_writer.write(" PUBLIC \"");
                this.m_writer.write(string2);
            } else {
                this.m_writer.write(" SYSTEM \"");
                this.m_writer.write(string3);
            }
            this.m_writer.write("\" NDATA ");
            this.m_writer.write(string4);
            this.m_writer.write(" >");
            this.m_writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void writeAttrString(Writer writer, String arrc, String string) throws IOException {
        int n = arrc.length();
        if (n > this.m_attrBuff.length) {
            this.m_attrBuff = new char[n * 2 + 1];
        }
        arrc.getChars(0, n, this.m_attrBuff, 0);
        arrc = this.m_attrBuff;
        for (int i = 0; i < n; ++i) {
            char c = arrc[i];
            if (this.m_charInfo.shouldMapAttrChar(c)) {
                this.accumDefaultEscape(writer, c, i, arrc, n, false, true);
                continue;
            }
            if (c >= '\u0000' && c <= '\u001f') {
                if (c != '\t') {
                    if (c != '\n') {
                        if (c != '\r') {
                            writer.write("&#");
                            writer.write(Integer.toString(c));
                            writer.write(59);
                            continue;
                        }
                        writer.write("&#13;");
                        continue;
                    }
                    writer.write("&#10;");
                    continue;
                }
                writer.write("&#9;");
                continue;
            }
            if (c < '') {
                writer.write(c);
                continue;
            }
            if (c <= '\u009f') {
                writer.write("&#");
                writer.write(Integer.toString(c));
                writer.write(59);
                continue;
            }
            if (c == '\u2028') {
                writer.write("&#8232;");
                continue;
            }
            if (this.m_encodingInfo.isInEncoding(c)) {
                writer.write(c);
                continue;
            }
            writer.write("&#");
            writer.write(Integer.toString(c));
            writer.write(59);
        }
    }

    void writeNormalizedChars(char[] arrc, int n, int n2, boolean bl, boolean bl2) throws IOException, SAXException {
        Writer writer = this.m_writer;
        n2 = n + n2;
        while (n < n2) {
            char c = arrc[n];
            if ('\n' == c && bl2) {
                writer.write(this.m_lineSep, 0, this.m_lineSepLen);
            } else if (bl && !this.escapingNotNeeded(c)) {
                if (this.m_cdataTagOpen) {
                    this.closeCDATA();
                }
                if (Encodings.isHighUTF16Surrogate(c)) {
                    this.writeUTF16Surrogate(c, arrc, n, n2);
                    ++n;
                } else {
                    writer.write("&#");
                    writer.write(Integer.toString(c));
                    writer.write(59);
                }
            } else if (bl && n < n2 - 2 && ']' == c && ']' == arrc[n + 1] && '>' == arrc[n + 2]) {
                writer.write("]]]]><![CDATA[>");
                n += 2;
            } else if (this.escapingNotNeeded(c)) {
                if (bl && !this.m_cdataTagOpen) {
                    writer.write("<![CDATA[");
                    this.m_cdataTagOpen = true;
                }
                writer.write(c);
            } else if (Encodings.isHighUTF16Surrogate(c)) {
                if (this.m_cdataTagOpen) {
                    this.closeCDATA();
                }
                this.writeUTF16Surrogate(c, arrc, n, n2);
                ++n;
            } else {
                if (this.m_cdataTagOpen) {
                    this.closeCDATA();
                }
                writer.write("&#");
                writer.write(Integer.toString(c));
                writer.write(59);
            }
            ++n;
        }
    }

    protected int writeUTF16Surrogate(char c, char[] object, int n, int n2) throws IOException {
        int n3 = 0;
        if (n + 1 < n2) {
            char c2 = object[n + 1];
            if (Encodings.isLowUTF16Surrogate(c2)) {
                Writer writer = this.m_writer;
                if (this.m_encodingInfo.isInEncoding(c, c2)) {
                    writer.write((char[])object, n, 2);
                    n = n3;
                } else if (this.getEncoding() != null) {
                    n = Encodings.toCodePoint(c, c2);
                    writer.write(38);
                    writer.write(35);
                    writer.write(Integer.toString(n));
                    writer.write(59);
                } else {
                    writer.write((char[])object, n, 2);
                    n = n3;
                }
                return n;
            }
            object = Utils.messages;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Integer.toHexString(c));
            stringBuilder.append(" ");
            stringBuilder.append(Integer.toHexString(c2));
            throw new IOException(((Messages)object).createMessage("ER_INVALID_UTF16_SURROGATE", new Object[]{stringBuilder.toString()}));
        }
        throw new IOException(Utils.messages.createMessage("ER_INVALID_UTF16_SURROGATE", new Object[]{Integer.toHexString(c)}));
    }

    static final class BoolStack {
        private int m_allocatedSize;
        private int m_index;
        private boolean[] m_values;

        public BoolStack() {
            this(32);
        }

        public BoolStack(int n) {
            this.m_allocatedSize = n;
            this.m_values = new boolean[n];
            this.m_index = -1;
        }

        private void grow() {
            this.m_allocatedSize *= 2;
            boolean[] arrbl = new boolean[this.m_allocatedSize];
            System.arraycopy(this.m_values, 0, arrbl, 0, this.m_index + 1);
            this.m_values = arrbl;
        }

        public final void clear() {
            this.m_index = -1;
        }

        public boolean isEmpty() {
            boolean bl = this.m_index == -1;
            return bl;
        }

        public final boolean peek() {
            return this.m_values[this.m_index];
        }

        public final boolean peekOrFalse() {
            int n = this.m_index;
            boolean bl = n > -1 ? this.m_values[n] : false;
            return bl;
        }

        public final boolean peekOrTrue() {
            int n = this.m_index;
            boolean bl = n > -1 ? this.m_values[n] : true;
            return bl;
        }

        public final boolean pop() {
            boolean[] arrbl = this.m_values;
            int n = this.m_index;
            this.m_index = n - 1;
            return arrbl[n];
        }

        public final boolean popAndTop() {
            --this.m_index;
            int n = this.m_index;
            boolean bl = n >= 0 ? this.m_values[n] : false;
            return bl;
        }

        public final boolean push(boolean bl) {
            int n;
            if (this.m_index == this.m_allocatedSize - 1) {
                this.grow();
            }
            boolean[] arrbl = this.m_values;
            this.m_index = n = this.m_index + 1;
            arrbl[n] = bl;
            return bl;
        }

        public final void setTop(boolean bl) {
            this.m_values[this.m_index] = bl;
        }

        public final int size() {
            return this.m_index + 1;
        }
    }

    private class WritertoStringBuffer
    extends Writer {
        private final StringBuffer m_stringbuf;

        WritertoStringBuffer(StringBuffer stringBuffer) {
            this.m_stringbuf = stringBuffer;
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        public void flush() throws IOException {
        }

        @Override
        public void write(int n) {
            this.m_stringbuf.append((char)n);
        }

        @Override
        public void write(String string) {
            this.m_stringbuf.append(string);
        }

        @Override
        public void write(char[] arrc, int n, int n2) throws IOException {
            this.m_stringbuf.append(arrc, n, n2);
        }
    }

}

