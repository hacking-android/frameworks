/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Hashtable;
import javax.xml.transform.TransformerException;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.utils.SystemIDResolver;
import org.apache.xml.serializer.utils.WrappedRuntimeException;

final class CharInfo {
    static final int ASCII_MAX = 128;
    public static final String HTML_ENTITIES_RESOURCE;
    private static final int LOW_ORDER_BITMASK = 31;
    private static final int SHIFT_PER_WORD = 5;
    static final char S_CARRIAGERETURN = '\r';
    static final char S_GT = '>';
    static final char S_HORIZONAL_TAB = '\t';
    static final char S_LINEFEED = '\n';
    static final char S_LINE_SEPARATOR = '\u2028';
    static final char S_LT = '<';
    static final char S_NEL = '\u0085';
    static final char S_QUOTE = '\"';
    static final char S_SPACE = ' ';
    public static final String XML_ENTITIES_RESOURCE;
    private static Hashtable m_getCharInfoCache;
    private final int[] array_of_bits;
    private int firstWordNotUsed;
    private final CharKey m_charKey;
    private HashMap m_charToString;
    boolean onlyQuotAmpLtGt;
    private final boolean[] shouldMapAttrChar_ASCII;
    private final boolean[] shouldMapTextChar_ASCII;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SerializerBase.PKG_NAME);
        stringBuilder.append(".HTMLEntities");
        HTML_ENTITIES_RESOURCE = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(SerializerBase.PKG_NAME);
        stringBuilder.append(".XMLEntities");
        XML_ENTITIES_RESOURCE = stringBuilder.toString();
        m_getCharInfoCache = new Hashtable();
    }

    private CharInfo() {
        this.array_of_bits = this.createEmptySetOfIntegers(65535);
        this.firstWordNotUsed = 0;
        this.shouldMapAttrChar_ASCII = new boolean[128];
        this.shouldMapTextChar_ASCII = new boolean[128];
        this.m_charKey = new CharKey();
        this.onlyQuotAmpLtGt = true;
    }

    /*
     * Exception decompiling
     */
    private CharInfo(String var1_1, String var2_4, boolean var3_7) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[TRYBLOCK], 1[TRYBLOCK]], but top level block is 19[WHILELOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static int arrayIndex(int n) {
        return n >> 5;
    }

    private static int bit(int n) {
        return 1 << (n & 31);
    }

    private int[] createEmptySetOfIntegers(int n) {
        this.firstWordNotUsed = 0;
        return new int[CharInfo.arrayIndex(n - 1) + 1];
    }

    private boolean defineEntity(String string, char c) {
        StringBuffer stringBuffer = new StringBuffer("&");
        stringBuffer.append(string);
        stringBuffer.append(';');
        return this.defineChar2StringMapping(stringBuffer.toString(), c);
    }

    private boolean extraEntity(String string, int n) {
        boolean bl;
        boolean bl2 = bl = false;
        if (n < 128) {
            if (n != 34) {
                if (n != 38) {
                    if (n != 60) {
                        if (n != 62) {
                            bl2 = true;
                        } else {
                            bl2 = bl;
                            if (!string.equals("&gt;")) {
                                bl2 = true;
                            }
                        }
                    } else {
                        bl2 = bl;
                        if (!string.equals("&lt;")) {
                            bl2 = true;
                        }
                    }
                } else {
                    bl2 = bl;
                    if (!string.equals("&amp;")) {
                        bl2 = true;
                    }
                }
            } else {
                bl2 = bl;
                if (!string.equals("&quot;")) {
                    bl2 = true;
                }
            }
        }
        return bl2;
    }

    private final boolean get(int n) {
        boolean bl = false;
        int n2 = n >> 5;
        if (n2 < this.firstWordNotUsed) {
            n2 = this.array_of_bits[n2];
            bl = true;
            if ((n2 & 1 << (n & 31)) == 0) {
                bl = false;
            }
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static CharInfo getCharInfo(String string, String string2) {
        CharInfo charInfo = (CharInfo)m_getCharInfoCache.get(string);
        if (charInfo != null) {
            return CharInfo.mutableCopyOf(charInfo);
        }
        try {
            charInfo = CharInfo.getCharInfoBasedOnPrivilege(string, string2, true);
            m_getCharInfoCache.put(string, charInfo);
            return CharInfo.mutableCopyOf(charInfo);
        }
        catch (Exception exception) {
            try {
                return CharInfo.getCharInfoBasedOnPrivilege(string, string2, false);
            }
            catch (Exception exception2) {
                if (string.indexOf(58) < 0) {
                    SystemIDResolver.getAbsoluteURIFromRelative(string);
                    return CharInfo.getCharInfoBasedOnPrivilege(string, string2, false);
                }
                try {
                    SystemIDResolver.getAbsoluteURI(string, null);
                }
                catch (TransformerException transformerException) {
                    throw new WrappedRuntimeException(transformerException);
                }
                return CharInfo.getCharInfoBasedOnPrivilege(string, string2, false);
            }
        }
    }

    private static CharInfo getCharInfoBasedOnPrivilege(final String string, final String string2, final boolean bl) {
        return (CharInfo)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                return new CharInfo(string, string2, bl);
            }
        });
    }

    private static CharInfo mutableCopyOf(CharInfo charInfo) {
        CharInfo charInfo2 = new CharInfo();
        Object[] arrobject = charInfo.array_of_bits;
        int n = arrobject.length;
        System.arraycopy(arrobject, 0, charInfo2.array_of_bits, 0, n);
        charInfo2.firstWordNotUsed = charInfo.firstWordNotUsed;
        arrobject = charInfo.shouldMapAttrChar_ASCII;
        n = arrobject.length;
        System.arraycopy(arrobject, 0, charInfo2.shouldMapAttrChar_ASCII, 0, n);
        arrobject = charInfo.shouldMapTextChar_ASCII;
        n = arrobject.length;
        System.arraycopy(arrobject, 0, charInfo2.shouldMapTextChar_ASCII, 0, n);
        charInfo2.m_charToString = (HashMap)charInfo.m_charToString.clone();
        charInfo2.onlyQuotAmpLtGt = charInfo.onlyQuotAmpLtGt;
        return charInfo2;
    }

    private final void set(int n) {
        this.setASCIItextDirty(n);
        this.setASCIIattrDirty(n);
        int n2 = n >> 5;
        int n3 = n2 + 1;
        if (this.firstWordNotUsed < n3) {
            this.firstWordNotUsed = n3;
        }
        int[] arrn = this.array_of_bits;
        arrn[n2] = arrn[n2] | 1 << (n & 31);
    }

    private void setASCIIattrDirty(int n) {
        if (n >= 0 && n < 128) {
            this.shouldMapAttrChar_ASCII[n] = true;
        }
    }

    private void setASCIItextDirty(int n) {
        if (n >= 0 && n < 128) {
            this.shouldMapTextChar_ASCII[n] = true;
        }
    }

    boolean defineChar2StringMapping(String string, char c) {
        CharKey charKey = new CharKey(c);
        this.m_charToString.put(charKey, string);
        this.set(c);
        return this.extraEntity(string, c);
    }

    String getOutputStringForChar(char c) {
        this.m_charKey.setChar(c);
        return (String)this.m_charToString.get(this.m_charKey);
    }

    final boolean shouldMapAttrChar(int n) {
        if (n < 128) {
            return this.shouldMapAttrChar_ASCII[n];
        }
        return this.get(n);
    }

    final boolean shouldMapTextChar(int n) {
        if (n < 128) {
            return this.shouldMapTextChar_ASCII[n];
        }
        return this.get(n);
    }

    private static class CharKey {
        private char m_char;

        public CharKey() {
        }

        public CharKey(char c) {
            this.m_char = c;
        }

        public final boolean equals(Object object) {
            boolean bl = ((CharKey)object).m_char == this.m_char;
            return bl;
        }

        public final int hashCode() {
            return this.m_char;
        }

        public final void setChar(char c) {
            this.m_char = c;
        }
    }

}

