/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.kxml2.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import libcore.internal.StringPool;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class KXmlParser
implements XmlPullParser,
Closeable {
    private static final char[] ANY;
    private static final int ATTLISTDECL = 13;
    private static final char[] COMMENT_DOUBLE_DASH;
    private static final Map<String, String> DEFAULT_ENTITIES;
    private static final char[] DOUBLE_QUOTE;
    private static final int ELEMENTDECL = 11;
    private static final char[] EMPTY;
    private static final char[] END_CDATA;
    private static final char[] END_COMMENT;
    private static final char[] END_PROCESSING_INSTRUCTION;
    private static final int ENTITYDECL = 12;
    private static final String FEATURE_RELAXED = "http://xmlpull.org/v1/doc/features.html#relaxed";
    private static final char[] FIXED;
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final char[] IMPLIED;
    private static final char[] NDATA;
    private static final char[] NOTATION;
    private static final int NOTATIONDECL = 14;
    private static final int PARAMETER_ENTITY_REF = 15;
    private static final String PROPERTY_LOCATION = "http://xmlpull.org/v1/doc/properties.html#location";
    private static final String PROPERTY_XMLDECL_STANDALONE = "http://xmlpull.org/v1/doc/properties.html#xmldecl-standalone";
    private static final String PROPERTY_XMLDECL_VERSION = "http://xmlpull.org/v1/doc/properties.html#xmldecl-version";
    private static final char[] PUBLIC;
    private static final char[] REQUIRED;
    private static final char[] SINGLE_QUOTE;
    private static final char[] START_ATTLIST;
    private static final char[] START_CDATA;
    private static final char[] START_COMMENT;
    private static final char[] START_DOCTYPE;
    private static final char[] START_ELEMENT;
    private static final char[] START_ENTITY;
    private static final char[] START_NOTATION;
    private static final char[] START_PROCESSING_INSTRUCTION;
    private static final char[] SYSTEM;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private static final int XML_DECLARATION = 998;
    private int attributeCount;
    private String[] attributes = new String[16];
    private char[] buffer = new char[8192];
    private StringBuilder bufferCapture;
    private int bufferStartColumn;
    private int bufferStartLine;
    private Map<String, Map<String, String>> defaultAttributes;
    private boolean degenerated;
    private int depth;
    private Map<String, char[]> documentEntities;
    private String[] elementStack = new String[16];
    private String encoding;
    private String error;
    private boolean isWhitespace;
    private boolean keepNamespaceAttributes;
    private int limit = 0;
    private String location;
    private String name;
    private String namespace;
    private ContentSource nextContentSource;
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private boolean parsedTopLevelStartTag;
    private int position = 0;
    private String prefix;
    private boolean processDocDecl;
    private boolean processNsp;
    private String publicId;
    private Reader reader;
    private boolean relaxed;
    private String rootElementName;
    private Boolean standalone;
    public final StringPool stringPool = new StringPool();
    private String systemId;
    private String text;
    private int type;
    private boolean unresolved;
    private String version;

    static {
        DEFAULT_ENTITIES = new HashMap<String, String>();
        DEFAULT_ENTITIES.put("lt", "<");
        DEFAULT_ENTITIES.put("gt", ">");
        DEFAULT_ENTITIES.put("amp", "&");
        DEFAULT_ENTITIES.put("apos", "'");
        DEFAULT_ENTITIES.put("quot", "\"");
        START_COMMENT = new char[]{'<', '!', '-', '-'};
        END_COMMENT = new char[]{'-', '-', '>'};
        COMMENT_DOUBLE_DASH = new char[]{'-', '-'};
        START_CDATA = new char[]{'<', '!', '[', 'C', 'D', 'A', 'T', 'A', '['};
        END_CDATA = new char[]{']', ']', '>'};
        START_PROCESSING_INSTRUCTION = new char[]{'<', '?'};
        END_PROCESSING_INSTRUCTION = new char[]{'?', '>'};
        START_DOCTYPE = new char[]{'<', '!', 'D', 'O', 'C', 'T', 'Y', 'P', 'E'};
        SYSTEM = new char[]{'S', 'Y', 'S', 'T', 'E', 'M'};
        PUBLIC = new char[]{'P', 'U', 'B', 'L', 'I', 'C'};
        START_ELEMENT = new char[]{'<', '!', 'E', 'L', 'E', 'M', 'E', 'N', 'T'};
        START_ATTLIST = new char[]{'<', '!', 'A', 'T', 'T', 'L', 'I', 'S', 'T'};
        START_ENTITY = new char[]{'<', '!', 'E', 'N', 'T', 'I', 'T', 'Y'};
        START_NOTATION = new char[]{'<', '!', 'N', 'O', 'T', 'A', 'T', 'I', 'O', 'N'};
        EMPTY = new char[]{'E', 'M', 'P', 'T', 'Y'};
        ANY = new char[]{'A', 'N', 'Y'};
        NDATA = new char[]{'N', 'D', 'A', 'T', 'A'};
        NOTATION = new char[]{'N', 'O', 'T', 'A', 'T', 'I', 'O', 'N'};
        REQUIRED = new char[]{'R', 'E', 'Q', 'U', 'I', 'R', 'E', 'D'};
        IMPLIED = new char[]{'I', 'M', 'P', 'L', 'I', 'E', 'D'};
        FIXED = new char[]{'F', 'I', 'X', 'E', 'D'};
        SINGLE_QUOTE = new char[]{'\''};
        DOUBLE_QUOTE = new char[]{'\"'};
    }

    private boolean adjustNsp() throws XmlPullParserException {
        CharSequence charSequence;
        CharSequence charSequence2;
        String[] arrstring;
        Object object;
        boolean bl = false;
        int n = 0;
        while (n < (object = this.attributeCount) << 2) {
            boolean bl2;
            block19 : {
                block18 : {
                    block17 : {
                        charSequence2 = this.attributes[n + 2];
                        object = ((String)charSequence2).indexOf(58);
                        if (object == -1) break block17;
                        charSequence = ((String)charSequence2).substring(0, (int)object);
                        charSequence2 = ((String)charSequence2).substring(object + 1);
                        break block18;
                    }
                    bl2 = bl;
                    object = n;
                    if (!((String)charSequence2).equals("xmlns")) break block19;
                    charSequence = charSequence2;
                    charSequence2 = null;
                }
                if (!((String)charSequence).equals("xmlns")) {
                    bl2 = true;
                    object = n;
                } else {
                    charSequence = this.nspCounts;
                    object = this.depth;
                    CharSequence charSequence3 = charSequence[object];
                    charSequence[object] = charSequence3 + true;
                    object = charSequence3 << 1;
                    this.nspStack = this.ensureCapacity(this.nspStack, object + 2);
                    charSequence = this.nspStack;
                    charSequence[object] = charSequence2;
                    arrstring = this.attributes;
                    charSequence[object + 1] = arrstring[n + 3];
                    if (charSequence2 != null && arrstring[n + 3].isEmpty()) {
                        this.checkRelaxed("illegal empty namespace");
                    }
                    if (this.keepNamespaceAttributes) {
                        this.attributes[n] = "http://www.w3.org/2000/xmlns/";
                        bl2 = true;
                        object = n;
                    } else {
                        charSequence2 = this.attributes;
                        this.attributeCount = object = this.attributeCount - 1;
                        System.arraycopy(charSequence2, n + 4, charSequence2, n, (object << 2) - n);
                        object = n - 4;
                        bl2 = bl;
                    }
                }
            }
            n = object + 4;
            bl = bl2;
        }
        if (bl) {
            for (n = (object << 2) - 4; n >= 0; n -= 4) {
                charSequence = this.attributes[n + 2];
                object = ((String)charSequence).indexOf(58);
                if (object == 0 && !this.relaxed) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("illegal attribute name: ");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(" at ");
                    ((StringBuilder)charSequence2).append(this);
                    throw new RuntimeException(((StringBuilder)charSequence2).toString());
                }
                if (object == -1) continue;
                charSequence2 = ((String)charSequence).substring(0, (int)object);
                String string = ((String)charSequence).substring(object + 1);
                charSequence = this.getNamespace((String)charSequence2);
                if (charSequence == null && !this.relaxed) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Undefined Prefix: ");
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(" in ");
                    ((StringBuilder)charSequence).append(this);
                    throw new RuntimeException(((StringBuilder)charSequence).toString());
                }
                arrstring = this.attributes;
                arrstring[n] = charSequence;
                arrstring[n + 1] = charSequence2;
                arrstring[n + 2] = string;
            }
        }
        if ((n = this.name.indexOf(58)) == 0) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("illegal tag name: ");
            ((StringBuilder)charSequence2).append(this.name);
            this.checkRelaxed(((StringBuilder)charSequence2).toString());
        }
        if (n != -1) {
            this.prefix = this.name.substring(0, n);
            this.name = this.name.substring(n + 1);
        }
        this.namespace = this.getNamespace(this.prefix);
        if (this.namespace == null) {
            if (this.prefix != null) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("undefined prefix: ");
                ((StringBuilder)charSequence2).append(this.prefix);
                this.checkRelaxed(((StringBuilder)charSequence2).toString());
            }
            this.namespace = "";
        }
        return bl;
    }

    private void checkRelaxed(String string) throws XmlPullParserException {
        if (this.relaxed) {
            if (this.error == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error: ");
                stringBuilder.append(string);
                this.error = stringBuilder.toString();
            }
            return;
        }
        throw new XmlPullParserException(string, this, null);
    }

    private void defineAttributeDefault(String string, String string2, String string3) {
        Map<String, String> map;
        if (this.defaultAttributes == null) {
            this.defaultAttributes = new HashMap<String, Map<String, String>>();
        }
        Map<String, String> map2 = map = this.defaultAttributes.get(string);
        if (map == null) {
            map2 = new HashMap<String, String>();
            this.defaultAttributes.put(string, map2);
        }
        map2.put(string2, string3);
    }

    private String[] ensureCapacity(String[] arrstring, int n) {
        if (arrstring.length >= n) {
            return arrstring;
        }
        String[] arrstring2 = new String[n + 16];
        System.arraycopy(arrstring, 0, arrstring2, 0, arrstring.length);
        return arrstring2;
    }

    private boolean fillBuffer(int n) throws IOException, XmlPullParserException {
        block8 : {
            int n2;
            int n3;
            while (this.nextContentSource != null) {
                if (this.position >= this.limit) {
                    this.popContentSource();
                    if (this.limit - this.position < n) continue;
                    return true;
                }
                throw new XmlPullParserException("Unbalanced entity!", this, null);
            }
            for (n2 = 0; n2 < (n3 = this.position); ++n2) {
                if (this.buffer[n2] == '\n') {
                    ++this.bufferStartLine;
                    this.bufferStartColumn = 0;
                    continue;
                }
                ++this.bufferStartColumn;
            }
            char[] arrc = this.bufferCapture;
            if (arrc != null) {
                arrc.append(this.buffer, 0, n3);
            }
            if ((n2 = this.limit) != (n3 = this.position)) {
                this.limit = n2 - n3;
                arrc = this.buffer;
                System.arraycopy(arrc, n3, arrc, 0, this.limit);
            } else {
                this.limit = 0;
            }
            this.position = 0;
            do {
                Reader reader = this.reader;
                arrc = this.buffer;
                n2 = this.limit;
                if ((n2 = reader.read(arrc, n2, arrc.length - n2)) == -1) break block8;
                this.limit += n2;
            } while (this.limit < n);
            return true;
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private int next(boolean var1_1) throws IOException, XmlPullParserException {
        if (this.reader == null) throw new XmlPullParserException("setInput() must be called first.", this, null);
        if (this.type == 3) {
            --this.depth;
        }
        if (this.degenerated) {
            this.degenerated = false;
            this.type = 3;
            return this.type;
        }
        var2_2 = this.error;
        if (var2_2 != null) {
            if (var1_1) {
                this.text = var2_2;
                this.type = 9;
                this.error = null;
                return this.type;
            }
            this.error = null;
        }
        this.type = this.peekType(false);
        if (this.type == 998) {
            this.readXmlDeclaration();
            this.type = this.peekType(false);
        }
        this.text = null;
        this.isWhitespace = true;
        this.prefix = null;
        this.name = null;
        this.namespace = null;
        this.attributeCount = -1;
        var3_3 = var1_1 ^ true;
        block11 : do {
            var4_4 = this.type;
            switch (var4_4) {
                default: {
                    throw new XmlPullParserException("Unexpected token", this, null);
                }
                case 10: {
                    this.readDoctype(var1_1);
                    if (this.parsedTopLevelStartTag != false) throw new XmlPullParserException("Unexpected token", this, null);
                    ** GOTO lbl61
                }
                case 9: {
                    var2_2 = this.readComment(var1_1);
                    if (var1_1) {
                        this.text = var2_2;
                    }
                    ** GOTO lbl61
                }
                case 8: {
                    this.read(KXmlParser.START_PROCESSING_INSTRUCTION);
                    var2_2 = this.readUntil(KXmlParser.END_PROCESSING_INSTRUCTION, var1_1);
                    if (var1_1) {
                        this.text = var2_2;
                    }
                    ** GOTO lbl61
                }
                case 6: {
                    if (!var1_1) ** GOTO lbl57
                    var2_2 = new StringBuilder();
                    this.readEntity((StringBuilder)var2_2, true, var3_3, ValueContext.TEXT);
                    this.text = var2_2.toString();
                    ** GOTO lbl61
                }
                case 5: {
                    this.read(KXmlParser.START_CDATA);
                    this.text = this.readUntil(KXmlParser.END_CDATA, true);
                    ** GOTO lbl61
                }
lbl57: // 2 sources:
                case 4: {
                    this.text = this.readValue('<', var1_1 ^ true, var3_3, ValueContext.TEXT);
                    if (this.depth == 0 && this.isWhitespace) {
                        this.type = 7;
                    }
lbl61: // 8 sources:
                    if (this.depth == 0) {
                        var4_4 = this.type;
                        if (var4_4 == 6) throw new XmlPullParserException("Unexpected token", this, null);
                        if (var4_4 == 4) throw new XmlPullParserException("Unexpected token", this, null);
                        if (var4_4 == 5) throw new XmlPullParserException("Unexpected token", this, null);
                    }
                    if (var1_1) {
                        return this.type;
                    }
                    if (this.type == 7) {
                        this.text = null;
                    }
                    var4_4 = this.peekType(false);
                    var2_2 = this.text;
                    if (var2_2 != null && !var2_2.isEmpty() && var4_4 < 4) {
                        this.type = 4;
                        return this.type;
                    }
                    this.type = var4_4;
                    continue block11;
                }
                case 3: {
                    this.readEndTag();
                    return this.type;
                }
                case 2: {
                    this.parseStartTag(false, var3_3);
                    return this.type;
                }
                case 1: 
            }
            break;
        } while (true);
        return var4_4;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void parseStartTag(boolean bl, boolean bl2) throws IOException, XmlPullParserException {
        if (!bl) {
            this.read('<');
        }
        this.name = this.readName();
        this.attributeCount = 0;
        do {
            int n;
            Object object;
            int n2;
            block21 : {
                Map<String, String> map;
                block23 : {
                    block22 : {
                        block20 : {
                            this.skip();
                            if (this.position >= this.limit && !this.fillBuffer(1)) {
                                this.checkRelaxed(UNEXPECTED_EOF);
                                return;
                            }
                            char[] arrc = this.buffer;
                            n = this.position;
                            n2 = arrc[n];
                            if (!bl) break block20;
                            if (n2 == 63) {
                                this.position = n + 1;
                                this.read('>');
                                return;
                            }
                            break block21;
                        }
                        if (n2 != 47) break block22;
                        this.degenerated = true;
                        this.position = n + 1;
                        this.skip();
                        this.read('>');
                        break block23;
                    }
                    if (n2 != 62) break block21;
                    this.position = n + 1;
                }
                n2 = this.depth;
                this.depth = n2 + 1;
                n2 *= 4;
                if (this.depth == 1) {
                    this.parsedTopLevelStartTag = true;
                }
                this.elementStack = this.ensureCapacity(this.elementStack, n2 + 4);
                this.elementStack[n2 + 3] = this.name;
                n = this.depth;
                int[] arrn = this.nspCounts;
                if (n >= arrn.length) {
                    object = new int[n + 4];
                    System.arraycopy(arrn, 0, object, 0, arrn.length);
                    this.nspCounts = object;
                }
                int[] arrn2 = this.nspCounts;
                n = this.depth;
                arrn2[n] = arrn2[n - 1];
                if (this.processNsp) {
                    this.adjustNsp();
                } else {
                    this.namespace = "";
                }
                Map<String, Map<String, String>> map2 = this.defaultAttributes;
                if (map2 != null && (map = map2.get(this.name)) != null) {
                    for (Map.Entry entry : map.entrySet()) {
                        if (this.getAttributeValue(null, (String)entry.getKey()) != null) continue;
                        n = this.attributeCount;
                        this.attributeCount = n + 1;
                        String[] arrstring = this.attributes = this.ensureCapacity(this.attributes, (n *= 4) + 4);
                        arrstring[n] = "";
                        arrstring[n + 1] = null;
                        arrstring[n + 2] = (String)entry.getKey();
                        this.attributes[n + 3] = (String)entry.getValue();
                    }
                }
                String[] arrstring = this.elementStack;
                arrstring[n2] = this.namespace;
                arrstring[n2 + 1] = this.prefix;
                arrstring[n2 + 2] = this.name;
                return;
            }
            String string = this.readName();
            n2 = this.attributeCount;
            this.attributeCount = n2 + 1;
            this.attributes = this.ensureCapacity(this.attributes, (n2 *= 4) + 4);
            object = this.attributes;
            object[n2] = "";
            object[n2 + 1] = null;
            object[n2 + 2] = string;
            this.skip();
            if (this.position >= this.limit && !this.fillBuffer(1)) {
                this.checkRelaxed(UNEXPECTED_EOF);
                return;
            }
            object = this.buffer;
            n = this.position;
            if (object[n] == 61) {
                int n3;
                this.position = n + 1;
                this.skip();
                if (this.position >= this.limit && !this.fillBuffer(1)) {
                    this.checkRelaxed(UNEXPECTED_EOF);
                    return;
                }
                n = this.buffer[this.position];
                if (n != 39 && n != 34) {
                    if (!this.relaxed) throw new XmlPullParserException("attr value delimiter missing!", this, null);
                    n3 = n = 32;
                } else {
                    ++this.position;
                    n3 = n;
                }
                this.attributes[n2 + 3] = this.readValue((char)n3, true, bl2, ValueContext.ATTRIBUTE);
                if (n3 == 32 || this.peekCharacter() != n3) continue;
                ++this.position;
                continue;
            }
            if (this.relaxed) {
                this.attributes[n2 + 3] = string;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attr.value missing f. ");
            ((StringBuilder)object).append(string);
            this.checkRelaxed(((StringBuilder)object).toString());
            this.attributes[n2 + 3] = string;
        } while (true);
    }

    private int peekCharacter() throws IOException, XmlPullParserException {
        if (this.position >= this.limit && !this.fillBuffer(1)) {
            return -1;
        }
        return this.buffer[this.position];
    }

    private int peekType(boolean bl) throws IOException, XmlPullParserException {
        if (this.position >= this.limit && !this.fillBuffer(1)) {
            return 1;
        }
        char[] arrc = this.buffer;
        int n = this.position;
        char c = arrc[n];
        int n2 = 4;
        if (c != '%') {
            if (c != '&') {
                if (c != '<') {
                    return 4;
                }
                if (n + 3 >= this.limit && !this.fillBuffer(4)) {
                    throw new XmlPullParserException("Dangling <", this, null);
                }
                arrc = this.buffer;
                n2 = this.position;
                c = arrc[n2 + 1];
                if (c != '!') {
                    if (c != '/') {
                        if (c != '?') {
                            return 2;
                        }
                        if (!(n2 + 5 >= this.limit && !this.fillBuffer(6) || (arrc = this.buffer)[(n2 = this.position) + 2] != 'x' && arrc[n2 + 2] != 'X' || (arrc = this.buffer)[(n2 = this.position) + 3] != 'm' && arrc[n2 + 3] != 'M' || (arrc = this.buffer)[(n2 = this.position) + 4] != 'l' && arrc[n2 + 4] != 'L' || this.buffer[this.position + 5] != ' ')) {
                            return 998;
                        }
                        return 8;
                    }
                    return 3;
                }
                c = arrc[n2 + 2];
                if (c != '-') {
                    if (c != 'A') {
                        if (c != 'N') {
                            if (c != '[') {
                                if (c != 'D') {
                                    if (c == 'E') {
                                        if ((n2 = arrc[n2 + 3]) != 76) {
                                            if (n2 == 78) {
                                                return 12;
                                            }
                                        } else {
                                            return 11;
                                        }
                                    }
                                    throw new XmlPullParserException("Unexpected <!", this, null);
                                }
                                return 10;
                            }
                            return 5;
                        }
                        return 14;
                    }
                    return 13;
                }
                return 9;
            }
            return 6;
        }
        if (bl) {
            n2 = 15;
        }
        return n2;
    }

    private void popContentSource() {
        this.buffer = this.nextContentSource.buffer;
        this.position = this.nextContentSource.position;
        this.limit = this.nextContentSource.limit;
        this.nextContentSource = this.nextContentSource.next;
    }

    private void pushContentSource(char[] arrc) {
        this.nextContentSource = new ContentSource(this.nextContentSource, this.buffer, this.position, this.limit);
        this.buffer = arrc;
        this.position = 0;
        this.limit = arrc.length;
    }

    private void read(char c) throws IOException, XmlPullParserException {
        int n = this.peekCharacter();
        if (n != c) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected: '");
            stringBuilder.append(c);
            stringBuilder.append("' actual: '");
            stringBuilder.append((char)n);
            stringBuilder.append("'");
            this.checkRelaxed(stringBuilder.toString());
            if (n == -1) {
                return;
            }
        }
        ++this.position;
    }

    private void read(char[] arrc) throws IOException, XmlPullParserException {
        if (this.position + arrc.length > this.limit && !this.fillBuffer(arrc.length)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected: '");
            stringBuilder.append(new String(arrc));
            stringBuilder.append("' but was EOF");
            this.checkRelaxed(stringBuilder.toString());
            return;
        }
        for (int i = 0; i < arrc.length; ++i) {
            if (this.buffer[this.position + i] == arrc[i]) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("expected: \"");
            stringBuilder.append(new String(arrc));
            stringBuilder.append("\" but was \"");
            stringBuilder.append(new String(this.buffer, this.position, arrc.length));
            stringBuilder.append("...\"");
            this.checkRelaxed(stringBuilder.toString());
        }
        this.position += arrc.length;
    }

    private void readAttributeListDeclaration() throws IOException, XmlPullParserException {
        this.read(START_ATTLIST);
        this.skip();
        String string = this.readName();
        do {
            String string2;
            int n;
            Object object;
            int n2;
            block15 : {
                this.skip();
                if (this.peekCharacter() == 62) {
                    ++this.position;
                    return;
                }
                string2 = this.readName();
                this.skip();
                if (this.position + 1 >= this.limit && !this.fillBuffer(2)) {
                    throw new XmlPullParserException("Malformed attribute list", this, null);
                }
                char[] arrc = this.buffer;
                n = this.position;
                n2 = arrc[n];
                object = NOTATION;
                if (n2 == object[0] && arrc[n + 1] == object[1]) {
                    this.read((char[])object);
                    this.skip();
                }
                if (this.peekCharacter() == 40) {
                    ++this.position;
                    do {
                        this.skip();
                        this.readName();
                        this.skip();
                        n = this.peekCharacter();
                        if (n == 41) {
                            ++this.position;
                            break block15;
                        }
                        if (n != 124) break;
                        ++this.position;
                    } while (true);
                    throw new XmlPullParserException("Malformed attribute type", this, null);
                }
                this.readName();
            }
            this.skip();
            n = n2 = this.peekCharacter();
            if (n2 == 35) {
                ++this.position;
                n = this.peekCharacter();
                if (n == 82) {
                    this.read(REQUIRED);
                } else if (n == 73) {
                    this.read(IMPLIED);
                } else {
                    if (n != 70) {
                        throw new XmlPullParserException("Malformed attribute type", this, null);
                    }
                    this.read(FIXED);
                }
                this.skip();
                n = this.peekCharacter();
            }
            if (n != 34 && n != 39) continue;
            ++this.position;
            object = this.readValue((char)n, true, true, ValueContext.ATTRIBUTE);
            if (this.peekCharacter() == n) {
                ++this.position;
            }
            this.defineAttributeDefault(string, string2, (String)object);
        } while (true);
    }

    private String readComment(boolean bl) throws IOException, XmlPullParserException {
        this.read(START_COMMENT);
        if (this.relaxed) {
            return this.readUntil(END_COMMENT, bl);
        }
        String string = this.readUntil(COMMENT_DOUBLE_DASH, bl);
        if (this.peekCharacter() == 62) {
            ++this.position;
            return string;
        }
        throw new XmlPullParserException("Comments may not contain --", this, null);
    }

    private void readContentSpec() throws IOException, XmlPullParserException {
        block15 : {
            block13 : {
                int n;
                char[] arrc;
                block14 : {
                    block12 : {
                        block11 : {
                            int n2;
                            int n3;
                            this.skip();
                            n = this.peekCharacter();
                            if (n != 40) break block12;
                            int n4 = 0;
                            do {
                                if (n == 40) {
                                    n3 = n4 + 1;
                                } else if (n == 41) {
                                    n3 = n4 - 1;
                                } else {
                                    if (n == -1) break block11;
                                    n3 = n4;
                                }
                                ++this.position;
                                n = n2 = this.peekCharacter();
                                n4 = n3;
                            } while (n3 > 0);
                            if (n2 == 42 || n2 == 63 || n2 == 43) {
                                ++this.position;
                            }
                            break block13;
                        }
                        throw new XmlPullParserException("Unterminated element content spec", this, null);
                    }
                    arrc = EMPTY;
                    if (n != arrc[0]) break block14;
                    this.read(arrc);
                    break block13;
                }
                arrc = ANY;
                if (n != arrc[0]) break block15;
                this.read(arrc);
            }
            return;
        }
        throw new XmlPullParserException("Expected element content spec", this, null);
    }

    private void readDoctype(boolean bl) throws IOException, XmlPullParserException {
        this.read(START_DOCTYPE);
        int n = -1;
        if (bl) {
            this.bufferCapture = new StringBuilder();
            n = this.position;
        }
        try {
            this.skip();
            this.rootElementName = this.readName();
            this.readExternalId(true, true);
            this.skip();
            if (this.peekCharacter() == 91) {
                this.readInternalSubset();
            }
            this.skip();
            this.read('>');
            this.skip();
            return;
        }
        finally {
            if (bl) {
                this.bufferCapture.append(this.buffer, 0, this.position);
                this.bufferCapture.delete(0, n);
                this.text = this.bufferCapture.toString();
                this.bufferCapture = null;
            }
        }
    }

    private void readElementDeclaration() throws IOException, XmlPullParserException {
        this.read(START_ELEMENT);
        this.skip();
        this.readName();
        this.readContentSpec();
        this.skip();
        this.read('>');
    }

    private void readEndTag() throws IOException, XmlPullParserException {
        int n;
        block6 : {
            block5 : {
                block4 : {
                    this.read('<');
                    this.read('/');
                    this.name = this.readName();
                    this.skip();
                    this.read('>');
                    int n2 = this.depth;
                    n = (n2 - 1) * 4;
                    if (n2 == 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("read end tag ");
                        stringBuilder.append(this.name);
                        stringBuilder.append(" with no tags open");
                        this.checkRelaxed(stringBuilder.toString());
                        this.type = 9;
                        return;
                    }
                    if (!this.name.equals(this.elementStack[n + 3])) break block4;
                    String[] arrstring = this.elementStack;
                    this.namespace = arrstring[n];
                    this.prefix = arrstring[n + 1];
                    this.name = arrstring[n + 2];
                    break block5;
                }
                if (!this.relaxed) break block6;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected: /");
        stringBuilder.append(this.elementStack[n + 3]);
        stringBuilder.append(" read: ");
        stringBuilder.append(this.name);
        throw new XmlPullParserException(stringBuilder.toString(), this, null);
    }

    private void readEntity(StringBuilder stringBuilder, boolean bl, boolean bl2, ValueContext object) throws IOException, XmlPullParserException {
        int n = stringBuilder.length();
        Object object2 = this.buffer;
        int n2 = this.position;
        this.position = n2 + 1;
        if (object2[n2] == '&') {
            stringBuilder.append('&');
            do {
                if ((n2 = this.peekCharacter()) == 59) {
                    stringBuilder.append(';');
                    ++this.position;
                    object2 = stringBuilder.substring(n + 1, stringBuilder.length() - 1);
                    if (bl) {
                        this.name = object2;
                    }
                    if (((String)object2).startsWith("#")) {
                        try {
                            n2 = ((String)object2).startsWith("#x") ? Integer.parseInt(((String)object2).substring(2), 16) : Integer.parseInt(((String)object2).substring(1));
                            stringBuilder.delete(n, stringBuilder.length());
                            stringBuilder.appendCodePoint(n2);
                            this.unresolved = false;
                            return;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Invalid character reference: &");
                            stringBuilder2.append((String)object2);
                            throw new XmlPullParserException(stringBuilder2.toString());
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Invalid character reference: &");
                            stringBuilder3.append((String)object2);
                            throw new XmlPullParserException(stringBuilder3.toString());
                        }
                    }
                    if (object == ValueContext.ENTITY_DECLARATION) {
                        return;
                    }
                    object = DEFAULT_ENTITIES.get(object2);
                    if (object != null) {
                        stringBuilder.delete(n, stringBuilder.length());
                        this.unresolved = false;
                        stringBuilder.append((String)object);
                        return;
                    }
                    object = this.documentEntities;
                    if (object != null && (object = (char[])object.get(object2)) != null) {
                        stringBuilder.delete(n, stringBuilder.length());
                        this.unresolved = false;
                        if (this.processDocDecl) {
                            this.pushContentSource((char[])object);
                        } else {
                            stringBuilder.append((char[])object);
                        }
                        return;
                    }
                    if (this.systemId != null) {
                        stringBuilder.delete(n, stringBuilder.length());
                        return;
                    }
                    this.unresolved = true;
                    if (bl2) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("unresolved: &");
                        stringBuilder.append((String)object2);
                        stringBuilder.append(";");
                        this.checkRelaxed(stringBuilder.toString());
                    }
                    return;
                }
                if (!(n2 >= 128 || n2 >= 48 && n2 <= 57 || n2 >= 97 && n2 <= 122 || n2 >= 65 && n2 <= 90 || n2 == 95 || n2 == 45 || n2 == 35)) {
                    if (this.relaxed) {
                        return;
                    }
                    throw new XmlPullParserException("unterminated entity ref", this, null);
                }
                ++this.position;
                stringBuilder.append((char)n2);
            } while (true);
        }
        throw new AssertionError();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void readEntityDeclaration() throws IOException, XmlPullParserException {
        String string;
        this.read(START_ENTITY);
        boolean bl = true;
        this.skip();
        if (this.peekCharacter() == 37) {
            bl = false;
            ++this.position;
            this.skip();
        }
        String string2 = this.readName();
        this.skip();
        int n = this.peekCharacter();
        if (n != 34 && n != 39) {
            if (!this.readExternalId(true, false)) throw new XmlPullParserException("Expected entity value or external ID", this, null);
            String string3 = "";
            this.skip();
            n = this.peekCharacter();
            char[] arrc = NDATA;
            string = string3;
            if (n == arrc[0]) {
                this.read(arrc);
                this.skip();
                this.readName();
                string = string3;
            }
        } else {
            ++this.position;
            string = this.readValue((char)n, true, false, ValueContext.ENTITY_DECLARATION);
            if (this.peekCharacter() == n) {
                ++this.position;
            }
        }
        if (bl && this.processDocDecl) {
            if (this.documentEntities == null) {
                this.documentEntities = new HashMap<String, char[]>();
            }
            this.documentEntities.put(string2, string.toCharArray());
        }
        this.skip();
        this.read('>');
    }

    private boolean readExternalId(boolean bl, boolean bl2) throws IOException, XmlPullParserException {
        block10 : {
            int n;
            block9 : {
                block8 : {
                    this.skip();
                    n = this.peekCharacter();
                    if (n != 83) break block8;
                    this.read(SYSTEM);
                    break block9;
                }
                if (n != 80) break block10;
                this.read(PUBLIC);
                this.skip();
                if (bl2) {
                    this.publicId = this.readQuotedId(true);
                } else {
                    this.readQuotedId(false);
                }
            }
            this.skip();
            if (!bl && (n = this.peekCharacter()) != 34 && n != 39) {
                return true;
            }
            if (bl2) {
                this.systemId = this.readQuotedId(true);
            } else {
                this.readQuotedId(false);
            }
            return true;
        }
        return false;
    }

    private void readInternalSubset() throws IOException, XmlPullParserException {
        this.read('[');
        block9 : do {
            this.skip();
            if (this.peekCharacter() == 93) {
                ++this.position;
                return;
            }
            switch (this.peekType(true)) {
                default: {
                    throw new XmlPullParserException("Unexpected token", this, null);
                }
                case 15: {
                    throw new XmlPullParserException("Parameter entity references are not supported", this, null);
                }
                case 14: {
                    this.readNotationDeclaration();
                    continue block9;
                }
                case 13: {
                    this.readAttributeListDeclaration();
                    continue block9;
                }
                case 12: {
                    this.readEntityDeclaration();
                    continue block9;
                }
                case 11: {
                    this.readElementDeclaration();
                    continue block9;
                }
                case 9: {
                    this.readComment(false);
                    continue block9;
                }
                case 8: 
            }
            this.read(START_PROCESSING_INSTRUCTION);
            this.readUntil(END_PROCESSING_INSTRUCTION, false);
        } while (true);
    }

    private String readName() throws IOException, XmlPullParserException {
        if (this.position >= this.limit && !this.fillBuffer(1)) {
            this.checkRelaxed("name expected");
            return "";
        }
        int n = this.position;
        StringBuilder stringBuilder = null;
        int n2 = this.buffer[this.position];
        if (!(n2 >= 97 && n2 <= 122 || n2 >= 65 && n2 <= 90 || n2 == 95 || n2 == 58 || n2 >= 192 || this.relaxed)) {
            this.checkRelaxed("name expected");
            return "";
        }
        ++this.position;
        do {
            n2 = n;
            StringBuilder stringBuilder2 = stringBuilder;
            if (this.position >= this.limit) {
                stringBuilder2 = stringBuilder;
                if (stringBuilder == null) {
                    stringBuilder2 = new StringBuilder();
                }
                stringBuilder2.append(this.buffer, n, this.position - n);
                if (!this.fillBuffer(1)) {
                    return stringBuilder2.toString();
                }
                n2 = this.position;
            }
            if (!((n = this.buffer[this.position]) >= 97 && n <= 122 || n >= 65 && n <= 90 || n >= 48 && n <= 57 || n == 95 || n == 45 || n == 58 || n == 46 || n >= 183)) {
                if (stringBuilder2 == null) {
                    return this.stringPool.get(this.buffer, n2, this.position - n2);
                }
                stringBuilder2.append(this.buffer, n2, this.position - n2);
                return stringBuilder2.toString();
            }
            ++this.position;
            n = n2;
            stringBuilder = stringBuilder2;
        } while (true);
    }

    private void readNotationDeclaration() throws IOException, XmlPullParserException {
        this.read(START_NOTATION);
        this.skip();
        this.readName();
        if (this.readExternalId(false, false)) {
            this.skip();
            this.read('>');
            return;
        }
        throw new XmlPullParserException("Expected external ID or public ID for notation", this, null);
    }

    private String readQuotedId(boolean bl) throws IOException, XmlPullParserException {
        block4 : {
            char[] arrc;
            block3 : {
                int n;
                block2 : {
                    n = this.peekCharacter();
                    if (n != 34) break block2;
                    arrc = DOUBLE_QUOTE;
                    break block3;
                }
                if (n != 39) break block4;
                arrc = SINGLE_QUOTE;
            }
            ++this.position;
            return this.readUntil(arrc, bl);
        }
        throw new XmlPullParserException("Expected a quoted string", this, null);
    }

    private String readUntil(char[] arrc, boolean bl) throws IOException, XmlPullParserException {
        int n = this.position;
        Object object = null;
        int n2 = n;
        Object object2 = object;
        if (bl) {
            n2 = n;
            object2 = object;
            if (this.text != null) {
                object2 = new StringBuilder();
                object2.append(this.text);
                n2 = n;
            }
        }
        block0 : do {
            int n3 = this.position;
            n = n2;
            object = object2;
            if (arrc.length + n3 > this.limit) {
                object = object2;
                if (n2 < n3) {
                    object = object2;
                    if (bl) {
                        object = object2;
                        if (object2 == null) {
                            object = new StringBuilder();
                        }
                        ((StringBuilder)object).append(this.buffer, n2, this.position - n2);
                    }
                }
                if (!this.fillBuffer(arrc.length)) {
                    this.checkRelaxed(UNEXPECTED_EOF);
                    this.type = 9;
                    return null;
                }
                n = this.position;
            }
            for (n2 = 0; n2 < arrc.length; ++n2) {
                object2 = this.buffer;
                n3 = this.position;
                if (object2[n3 + n2] == arrc[n2]) continue;
                this.position = n3 + 1;
                n2 = n;
                object2 = object;
                continue block0;
            }
            break;
        } while (true);
        n2 = this.position;
        this.position += arrc.length;
        if (!bl) {
            return null;
        }
        if (object == null) {
            return this.stringPool.get(this.buffer, n, n2 - n);
        }
        ((StringBuilder)object).append(this.buffer, n, n2 - n);
        return ((StringBuilder)object).toString();
    }

    private String readValue(char c, boolean bl, boolean bl2, ValueContext object) throws IOException, XmlPullParserException {
        char[] arrc;
        int n;
        block23 : {
            int n2;
            n = this.position;
            arrc = null;
            int n3 = n;
            char[] arrc2 = arrc;
            if (object == ValueContext.TEXT) {
                n3 = n;
                arrc2 = arrc;
                if (this.text != null) {
                    arrc2 = new StringBuilder();
                    arrc2.append(this.text);
                    n3 = n;
                }
            }
            do {
                int n4;
                int n5 = this.position;
                n = n3;
                arrc = arrc2;
                if (n5 >= this.limit) {
                    arrc = arrc2;
                    if (n3 < n5) {
                        arrc = arrc2;
                        if (arrc2 == null) {
                            arrc = new StringBuilder();
                        }
                        arrc.append(this.buffer, n3, this.position - n3);
                    }
                    if (!this.fillBuffer(1)) {
                        object = arrc != null ? arrc.toString() : "";
                        return object;
                    }
                    n = this.position;
                }
                if ((n2 = this.buffer[this.position]) == c || c == ' ' && (n2 <= 32 || n2 == 62)) break block23;
                if (n2 == 38 && !bl) break block23;
                n3 = 10;
                n5 = 0;
                if (!(n2 == 13 || n2 == 10 && object == ValueContext.ATTRIBUTE || n2 == 38 || n2 == 60 || n2 == 93 && object == ValueContext.TEXT || n2 == 37 && object == ValueContext.ENTITY_DECLARATION)) {
                    int n6 = this.isWhitespace;
                    n3 = n5;
                    if (n2 <= 32) {
                        n3 = 1;
                    }
                    this.isWhitespace = n6 & n3;
                    ++this.position;
                    n3 = n;
                    arrc2 = arrc;
                    continue;
                }
                arrc2 = arrc;
                if (arrc == null) {
                    arrc2 = new StringBuilder();
                }
                arrc2.append(this.buffer, n, this.position - n);
                if (n2 == 13) {
                    if ((this.position + 1 < this.limit || this.fillBuffer(2)) && (arrc = this.buffer)[(n = this.position) + 1] == '\n') {
                        this.position = n + 1;
                    }
                    if (object == ValueContext.ATTRIBUTE) {
                        n3 = 32;
                    }
                    n4 = n3;
                } else if (n2 == 10) {
                    n4 = n3 = 32;
                } else {
                    if (n2 == 38) {
                        this.isWhitespace = false;
                        this.readEntity((StringBuilder)arrc2, false, bl2, (ValueContext)((Object)object));
                        n3 = this.position;
                        continue;
                    }
                    if (n2 == 60) {
                        if (object == ValueContext.ATTRIBUTE) {
                            this.checkRelaxed("Illegal: \"<\" inside attribute value");
                        }
                        this.isWhitespace = false;
                        n4 = n2;
                    } else {
                        if (n2 != 93) break;
                        if ((this.position + 2 < this.limit || this.fillBuffer(3)) && (arrc = this.buffer)[(n3 = this.position) + 1] == ']' && arrc[n3 + 2] == '>') {
                            this.checkRelaxed("Illegal: \"]]>\" outside CDATA section");
                        }
                        this.isWhitespace = false;
                        n4 = n2;
                    }
                }
                ++this.position;
                arrc2.append((char)n4);
                n3 = this.position;
            } while (true);
            if (n2 == 37) {
                throw new XmlPullParserException("This parser doesn't support parameter entities", this, null);
            }
            throw new AssertionError();
        }
        if (arrc == null) {
            return this.stringPool.get(this.buffer, n, this.position - n);
        }
        arrc.append(this.buffer, n, this.position - n);
        return arrc.toString();
    }

    private void readXmlDeclaration() throws IOException, XmlPullParserException {
        int n;
        if (this.bufferStartLine != 0 || this.bufferStartColumn != 0 || this.position != 0) {
            this.checkRelaxed("processing instructions must not start with xml");
        }
        this.read(START_PROCESSING_INSTRUCTION);
        this.parseStartTag(true, true);
        if (this.attributeCount < 1 || !"version".equals(this.attributes[2])) {
            this.checkRelaxed("version expected");
        }
        Object object = this.attributes;
        this.version = object[3];
        int n2 = n = 1;
        if (1 < this.attributeCount) {
            n2 = n;
            if ("encoding".equals(object[6])) {
                this.encoding = this.attributes[7];
                n2 = 1 + 1;
            }
        }
        n = n2;
        if (n2 < this.attributeCount) {
            n = n2;
            if ("standalone".equals(this.attributes[n2 * 4 + 2])) {
                object = this.attributes[n2 * 4 + 3];
                if ("yes".equals(object)) {
                    this.standalone = Boolean.TRUE;
                } else if ("no".equals(object)) {
                    this.standalone = Boolean.FALSE;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("illegal standalone value: ");
                    stringBuilder.append((String)object);
                    this.checkRelaxed(stringBuilder.toString());
                }
                n = n2 + 1;
            }
        }
        if (n != this.attributeCount) {
            this.checkRelaxed("unexpected attributes in XML declaration");
        }
        this.isWhitespace = true;
        this.text = null;
    }

    private void skip() throws IOException, XmlPullParserException {
        char[] arrc;
        int n;
        while ((this.position < this.limit || this.fillBuffer(1)) && (arrc = this.buffer)[n = this.position] <= ' ') {
            this.position = n + 1;
        }
        return;
    }

    @Override
    public void close() throws IOException {
        Reader reader = this.reader;
        if (reader != null) {
            reader.close();
        }
    }

    @Override
    public void defineEntityReplacementText(String string, String string2) throws XmlPullParserException {
        if (!this.processDocDecl) {
            if (this.reader != null) {
                if (this.documentEntities == null) {
                    this.documentEntities = new HashMap<String, char[]>();
                }
                this.documentEntities.put(string, string2.toCharArray());
                return;
            }
            throw new IllegalStateException("Entity replacement text must be defined after setInput()");
        }
        throw new IllegalStateException("Entity replacement text may not be defined with DOCTYPE processing enabled.");
    }

    @Override
    public int getAttributeCount() {
        return this.attributeCount;
    }

    @Override
    public String getAttributeName(int n) {
        if (n < this.attributeCount) {
            return this.attributes[n * 4 + 2];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getAttributeNamespace(int n) {
        if (n < this.attributeCount) {
            return this.attributes[n * 4];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getAttributePrefix(int n) {
        if (n < this.attributeCount) {
            return this.attributes[n * 4 + 1];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getAttributeType(int n) {
        return "CDATA";
    }

    @Override
    public String getAttributeValue(int n) {
        if (n < this.attributeCount) {
            return this.attributes[n * 4 + 3];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getAttributeValue(String string, String string2) {
        for (int i = this.attributeCount * 4 - 4; i >= 0; i -= 4) {
            if (!this.attributes[i + 2].equals(string2) || string != null && !this.attributes[i].equals(string)) continue;
            return this.attributes[i + 3];
        }
        return null;
    }

    @Override
    public int getColumnNumber() {
        int n = this.bufferStartColumn;
        for (int i = 0; i < this.position; ++i) {
            if (this.buffer[i] == '\n') {
                n = 0;
                continue;
            }
            ++n;
        }
        return n + 1;
    }

    @Override
    public int getDepth() {
        return this.depth;
    }

    @Override
    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    @Override
    public boolean getFeature(String string) {
        if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(string)) {
            return this.processNsp;
        }
        if (FEATURE_RELAXED.equals(string)) {
            return this.relaxed;
        }
        if ("http://xmlpull.org/v1/doc/features.html#process-docdecl".equals(string)) {
            return this.processDocDecl;
        }
        return false;
    }

    @Override
    public String getInputEncoding() {
        return this.encoding;
    }

    @Override
    public int getLineNumber() {
        int n = this.bufferStartLine;
        for (int i = 0; i < this.position; ++i) {
            int n2 = n;
            if (this.buffer[i] == '\n') {
                n2 = n + 1;
            }
            n = n2;
        }
        return n + 1;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNamespace() {
        return this.namespace;
    }

    @Override
    public String getNamespace(String string) {
        if ("xml".equals(string)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(string)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int i = (this.getNamespaceCount((int)this.depth) << 1) - 2; i >= 0; i -= 2) {
            if (string == null) {
                String[] arrstring = this.nspStack;
                if (arrstring[i] != null) continue;
                return arrstring[i + 1];
            }
            if (!string.equals(this.nspStack[i])) continue;
            return this.nspStack[i + 1];
        }
        return null;
    }

    @Override
    public int getNamespaceCount(int n) {
        if (n <= this.depth) {
            return this.nspCounts[n];
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public String getNamespacePrefix(int n) {
        return this.nspStack[n * 2];
    }

    @Override
    public String getNamespaceUri(int n) {
        return this.nspStack[n * 2 + 1];
    }

    @Override
    public String getPositionDescription() {
        CharSequence charSequence = this.type < TYPES.length ? TYPES[this.type] : "unknown";
        StringBuilder stringBuilder = new StringBuilder((String)charSequence);
        stringBuilder.append(' ');
        int n = this.type;
        if (n != 2 && n != 3) {
            if (n != 7) {
                if (n != 4) {
                    stringBuilder.append(this.getText());
                } else if (this.isWhitespace) {
                    stringBuilder.append("(whitespace)");
                } else {
                    String string = this.getText();
                    charSequence = string;
                    if (string.length() > 16) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(string.substring(0, 16));
                        ((StringBuilder)charSequence).append("...");
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    stringBuilder.append((String)charSequence);
                }
            }
        } else {
            if (this.degenerated) {
                stringBuilder.append("(empty) ");
            }
            stringBuilder.append('<');
            if (this.type == 3) {
                stringBuilder.append('/');
            }
            if (this.prefix != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("{");
                ((StringBuilder)charSequence).append(this.namespace);
                ((StringBuilder)charSequence).append("}");
                ((StringBuilder)charSequence).append(this.prefix);
                ((StringBuilder)charSequence).append(":");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            stringBuilder.append(this.name);
            int n2 = this.attributeCount;
            for (n = 0; n < n2 * 4; n += 4) {
                stringBuilder.append(' ');
                if (this.attributes[n + 1] != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("{");
                    ((StringBuilder)charSequence).append(this.attributes[n]);
                    ((StringBuilder)charSequence).append("}");
                    ((StringBuilder)charSequence).append(this.attributes[n + 1]);
                    ((StringBuilder)charSequence).append(":");
                    stringBuilder.append(((StringBuilder)charSequence).toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(this.attributes[n + 2]);
                ((StringBuilder)charSequence).append("='");
                ((StringBuilder)charSequence).append(this.attributes[n + 3]);
                ((StringBuilder)charSequence).append("'");
                stringBuilder.append(((StringBuilder)charSequence).toString());
            }
            stringBuilder.append('>');
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("@");
        ((StringBuilder)charSequence).append(this.getLineNumber());
        ((StringBuilder)charSequence).append(":");
        ((StringBuilder)charSequence).append(this.getColumnNumber());
        stringBuilder.append(((StringBuilder)charSequence).toString());
        if (this.location != null) {
            stringBuilder.append(" in ");
            stringBuilder.append(this.location);
        } else if (this.reader != null) {
            stringBuilder.append(" in ");
            stringBuilder.append(this.reader.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public String getPrefix() {
        return this.prefix;
    }

    @Override
    public Object getProperty(String string) {
        if (string.equals(PROPERTY_XMLDECL_VERSION)) {
            return this.version;
        }
        if (string.equals(PROPERTY_XMLDECL_STANDALONE)) {
            return this.standalone;
        }
        if (string.equals(PROPERTY_LOCATION)) {
            string = this.location;
            if (string == null) {
                string = this.reader.toString();
            }
            return string;
        }
        return null;
    }

    public String getPublicId() {
        return this.publicId;
    }

    public String getRootElementName() {
        return this.rootElementName;
    }

    public String getSystemId() {
        return this.systemId;
    }

    @Override
    public String getText() {
        int n = this.type;
        if (!(n < 4 || n == 6 && this.unresolved)) {
            String string = this.text;
            if (string == null) {
                return "";
            }
            return string;
        }
        return null;
    }

    @Override
    public char[] getTextCharacters(int[] arrn) {
        char[] arrc = this.getText();
        if (arrc == null) {
            arrn[0] = -1;
            arrn[1] = -1;
            return null;
        }
        arrc = arrc.toCharArray();
        arrn[0] = 0;
        arrn[1] = arrc.length;
        return arrc;
    }

    @Override
    public boolean isAttributeDefault(int n) {
        return false;
    }

    @Override
    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type == 2) {
            return this.degenerated;
        }
        throw new XmlPullParserException(ILLEGAL_TYPE, this, null);
    }

    @Override
    public boolean isWhitespace() throws XmlPullParserException {
        int n = this.type;
        if (n != 4 && n != 7 && n != 5) {
            throw new XmlPullParserException(ILLEGAL_TYPE, this, null);
        }
        return this.isWhitespace;
    }

    public void keepNamespaceAttributes() {
        this.keepNamespaceAttributes = true;
    }

    @Override
    public int next() throws XmlPullParserException, IOException {
        return this.next(false);
    }

    @Override
    public int nextTag() throws XmlPullParserException, IOException {
        int n;
        this.next();
        if (this.type == 4 && this.isWhitespace) {
            this.next();
        }
        if ((n = this.type) != 3 && n != 2) {
            throw new XmlPullParserException("unexpected type", this, null);
        }
        return this.type;
    }

    @Override
    public String nextText() throws XmlPullParserException, IOException {
        if (this.type == 2) {
            String string;
            this.next();
            if (this.type == 4) {
                string = this.getText();
                this.next();
            } else {
                string = "";
            }
            if (this.type == 3) {
                return string;
            }
            throw new XmlPullParserException("END_TAG expected", this, null);
        }
        throw new XmlPullParserException("precondition: START_TAG", this, null);
    }

    @Override
    public int nextToken() throws XmlPullParserException, IOException {
        return this.next(true);
    }

    @Override
    public void require(int n, String string, String string2) throws XmlPullParserException, IOException {
        if (n == this.type && (string == null || string.equals(this.getNamespace())) && (string2 == null || string2.equals(this.getName()))) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected: ");
        stringBuilder.append(TYPES[n]);
        stringBuilder.append(" {");
        stringBuilder.append(string);
        stringBuilder.append("}");
        stringBuilder.append(string2);
        throw new XmlPullParserException(stringBuilder.toString(), this, null);
    }

    @Override
    public void setFeature(String string, boolean bl) throws XmlPullParserException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (!"http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(string)) break block2;
                        this.processNsp = bl;
                        break block3;
                    }
                    if (!"http://xmlpull.org/v1/doc/features.html#process-docdecl".equals(string)) break block4;
                    this.processDocDecl = bl;
                    break block3;
                }
                if (!FEATURE_RELAXED.equals(string)) break block5;
                this.relaxed = bl;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("unsupported feature: ");
        stringBuilder.append(string);
        throw new XmlPullParserException(stringBuilder.toString(), this, null);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void setInput(InputStream var1_1, String var2_3) throws XmlPullParserException {
        block37 : {
            block38 : {
                this.position = 0;
                this.limit = 0;
                var3_4 = var2_3 == null;
                if (var1_1 == null) throw new IllegalArgumentException("is == null");
                var4_5 = var2_3;
                if (!var3_4) break block37;
                var5_26 = 0;
                do {
                    if (this.limit >= 4 || (var6_27 = var1_1.read()) == -1) break;
                    var5_26 = var5_26 << 8 | var6_27;
                    var4_7 = this.buffer;
                    var7_28 = this.limit;
                    this.limit = var7_28 + 1;
                    var4_7[var7_28] = (char)var6_27;
                } while (true);
                var6_27 = this.limit;
                var4_8 = var2_3;
                if (var6_27 != 4) break block37;
                switch (var5_26) {
                    default: {
                        if ((var5_26 & -65536) == -16842752) {
                            break;
                        }
                        break block38;
                    }
                    case 1010792557: {
                        do {
                            var6_27 = var1_1.read();
                            if (var6_27 != -1) ** GOTO lbl35
                            var4_10 = var2_3;
                            break block37;
lbl35: // 2 sources:
                            var4_11 = this.buffer;
                            var5_26 = this.limit;
                            this.limit = var5_26 + 1;
                            var4_11[var5_26] = (char)var6_27;
                        } while (var6_27 != 62);
                        var8_29 = new String(this.buffer, 0, this.limit);
                        var5_26 = var8_29.indexOf("encoding");
                        var4_12 = var2_3;
                        if (var5_26 == -1) break block37;
                        do {
                            if (var8_29.charAt(var5_26) == '\"' || var8_29.charAt(var5_26) == '\'') break;
                            ++var5_26;
                        } while (true);
                        var6_27 = var5_26 + 1;
                        var4_13 = var8_29.substring(var6_27, var8_29.indexOf(var8_29.charAt(var5_26), var6_27));
                        break block37;
                    }
                    case 1006649088: {
                        var4_14 = "UTF-16LE";
                        this.buffer[0] = (char)60;
                        this.buffer[1] = (char)63;
                        this.limit = 2;
                        break block37;
                    }
                    case 1006632960: {
                        var4_15 = "UTF-32LE";
                        this.buffer[0] = (char)60;
                        this.limit = 1;
                        break block37;
                    }
                    case 3932223: {
                        var4_16 = "UTF-16BE";
                        this.buffer[0] = (char)60;
                        this.buffer[1] = (char)63;
                        this.limit = 2;
                        break block37;
                    }
                    case 65279: {
                        var4_17 = "UTF-32BE";
                        this.limit = 0;
                        break block37;
                    }
                    case 60: {
                        var4_18 = "UTF-32BE";
                        this.buffer[0] = (char)60;
                        this.limit = 1;
                        break block37;
                    }
                    case -131072: {
                        var4_19 = "UTF-32LE";
                        this.limit = 0;
                        break block37;
                    }
                }
                var4_20 = "UTF-16BE";
                try {
                    this.buffer[0] = (char)(this.buffer[2] << 8 | this.buffer[3]);
                    this.limit = 1;
                    break block37;
                }
                catch (Exception var1_2) {
                    var2_3 = new StringBuilder();
                    var2_3.append("Invalid stream or encoding: ");
                    var2_3.append(var1_2);
                    throw new XmlPullParserException(var2_3.toString(), this, var1_2);
                }
            }
            if ((-65536 & var5_26) == -131072) {
                var4_21 = "UTF-16LE";
                this.buffer[0] = (char)(this.buffer[3] << 8 | this.buffer[2]);
                this.limit = 1;
            } else {
                var4_22 = var2_3;
                if ((var5_26 & -256) == -272908544) {
                    var4_23 = "UTF-8";
                    this.buffer[0] = this.buffer[3];
                    this.limit = 1;
                }
            }
        }
        var2_3 = var4_24;
        if (var4_24 == null) {
            var2_3 = "UTF-8";
        }
        var5_26 = this.limit;
        var4_25 = new InputStreamReader(var1_1, (String)var2_3);
        this.setInput(var4_25);
        this.encoding = var2_3;
        this.limit = var5_26;
        if (var3_4) return;
        if (this.peekCharacter() != 65279) return;
        --this.limit;
        System.arraycopy(this.buffer, 1, this.buffer, 0, this.limit);
    }

    @Override
    public void setInput(Reader reader) throws XmlPullParserException {
        this.reader = reader;
        this.type = 0;
        this.parsedTopLevelStartTag = false;
        this.name = null;
        this.namespace = null;
        this.degenerated = false;
        this.attributeCount = -1;
        this.encoding = null;
        this.version = null;
        this.standalone = null;
        if (reader == null) {
            return;
        }
        this.position = 0;
        this.limit = 0;
        this.bufferStartLine = 0;
        this.bufferStartColumn = 0;
        this.depth = 0;
        this.documentEntities = null;
    }

    @Override
    public void setProperty(String string, Object object) throws XmlPullParserException {
        if (string.equals(PROPERTY_LOCATION)) {
            this.location = String.valueOf(object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unsupported property: ");
        ((StringBuilder)object).append(string);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    static class ContentSource {
        private final char[] buffer;
        private final int limit;
        private final ContentSource next;
        private final int position;

        ContentSource(ContentSource contentSource, char[] arrc, int n, int n2) {
            this.next = contentSource;
            this.buffer = arrc;
            this.position = n;
            this.limit = n2;
        }
    }

    static enum ValueContext {
        ATTRIBUTE,
        TEXT,
        ENTITY_DECLARATION;
        
    }

}

