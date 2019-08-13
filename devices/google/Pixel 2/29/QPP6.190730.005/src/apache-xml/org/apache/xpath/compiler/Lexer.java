/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.compiler;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.utils.ObjectVector;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.compiler.Compiler;
import org.apache.xpath.compiler.Keywords;
import org.apache.xpath.compiler.XPathParser;

class Lexer {
    static final int TARGETEXTRA = 10000;
    private Compiler m_compiler;
    PrefixResolver m_namespaceContext;
    private int[] m_patternMap = new int[100];
    private int m_patternMapSize;
    XPathParser m_processor;

    Lexer(Compiler compiler, PrefixResolver prefixResolver, XPathParser xPathParser) {
        this.m_compiler = compiler;
        this.m_namespaceContext = prefixResolver;
        this.m_processor = xPathParser;
    }

    private final void addToTokenQueue(String string) {
        this.m_compiler.getTokenQueue().addElement(string);
    }

    private int getTokenQueuePosFromMap(int n) {
        block0 : {
            if ((n = this.m_patternMap[n]) < 10000) break block0;
            n -= 10000;
        }
        return n;
    }

    private int mapNSTokens(String string, int n, int n2, int n3) throws TransformerException {
        String string2;
        String string3;
        block10 : {
            string2 = string3 = "";
            if (n >= 0) {
                string2 = string3;
                if (n2 >= 0) {
                    string2 = string.substring(n, n2);
                }
            }
            if (this.m_namespaceContext != null && !string2.equals("*") && !string2.equals("xmlns")) {
                try {
                    if (string2.length() > 0) {
                        string3 = this.m_namespaceContext.getNamespaceForPrefix(string2);
                        break block10;
                    }
                    string3 = this.m_namespaceContext.getNamespaceForPrefix(string2);
                }
                catch (ClassCastException classCastException) {
                    string3 = this.m_namespaceContext.getNamespaceForPrefix(string2);
                }
            } else {
                string3 = string2;
            }
        }
        if (string3 != null && string3.length() > 0) {
            this.addToTokenQueue(string3);
            this.addToTokenQueue(":");
            string = string.substring(n2 + 1, n3);
            if (string.length() > 0) {
                this.addToTokenQueue(string);
            }
        } else {
            this.m_processor.errorForDOM3("ER_PREFIX_MUST_RESOLVE", new String[]{string2});
        }
        return -1;
    }

    private boolean mapPatternElemPos(int n, boolean bl, boolean bl2) {
        boolean bl3 = bl;
        if (n == 0) {
            int[] arrn;
            n = this.m_patternMapSize;
            int[] arrn2 = this.m_patternMap;
            if (n >= arrn2.length) {
                arrn = this.m_patternMap;
                int n2 = arrn2.length;
                this.m_patternMap = new int[n + 100];
                System.arraycopy(arrn, 0, this.m_patternMap, 0, n2);
            }
            if (!bl) {
                arrn = this.m_patternMap;
                n = this.m_patternMapSize - 1;
                arrn[n] = arrn[n] - 10000;
            }
            this.m_patternMap[this.m_patternMapSize] = this.m_compiler.getTokenQueueSize() - bl2 + 10000;
            ++this.m_patternMapSize;
            bl3 = false;
        }
        return bl3;
    }

    private void recordTokenString(Vector vector) {
        int n = this.getTokenQueuePosFromMap(this.m_patternMapSize - 1);
        this.resetTokenMark(n + 1);
        if (this.m_processor.lookahead('(', 1)) {
            int n2 = this.getKeywordToken(this.m_processor.m_token);
            if (n2 != 35) {
                if (n2 != 36) {
                    switch (n2) {
                        default: {
                            vector.addElement("*");
                            break;
                        }
                        case 1033: {
                            vector.addElement("*");
                            break;
                        }
                        case 1032: {
                            vector.addElement("*");
                            break;
                        }
                        case 1031: {
                            vector.addElement("#text");
                            break;
                        }
                        case 1030: {
                            vector.addElement("#comment");
                            break;
                        }
                    }
                } else {
                    vector.addElement("*");
                }
            } else {
                vector.addElement("/");
            }
        } else {
            int n3 = n;
            if (this.m_processor.tokenIs('@')) {
                n3 = n + 1;
                this.resetTokenMark(n3 + 1);
            }
            n = n3;
            if (this.m_processor.lookahead(':', 1)) {
                n = n3 + 2;
            }
            vector.addElement(this.m_compiler.getTokenQueue().elementAt(n));
        }
    }

    private final void resetTokenMark(int n) {
        int n2 = this.m_compiler.getTokenQueueSize();
        XPathParser xPathParser = this.m_processor;
        if (n > 0) {
            if (n <= n2) {
                --n;
            }
        } else {
            n = 0;
        }
        xPathParser.m_queueMark = n;
        if (this.m_processor.m_queueMark < n2) {
            XPathParser xPathParser2 = this.m_processor;
            ObjectVector objectVector = this.m_compiler.getTokenQueue();
            xPathParser = this.m_processor;
            n = xPathParser.m_queueMark;
            xPathParser.m_queueMark = n + 1;
            xPathParser2.m_token = (String)objectVector.elementAt(n);
            xPathParser = this.m_processor;
            xPathParser.m_tokenChar = xPathParser.m_token.charAt(0);
        } else {
            xPathParser = this.m_processor;
            xPathParser.m_token = null;
            xPathParser.m_tokenChar = (char)(false ? 1 : 0);
        }
    }

    final int getKeywordToken(String object) {
        int n;
        block5 : {
            block4 : {
                object = (Integer)Keywords.getKeyWord((String)object);
                if (object == null) break block4;
                try {
                    n = (Integer)object;
                }
                catch (ClassCastException classCastException) {
                    n = 0;
                    break block5;
                }
                catch (NullPointerException nullPointerException) {
                    n = 0;
                }
            }
            n = 0;
        }
        return n;
    }

    void tokenize(String string) throws TransformerException {
        this.tokenize(string, null);
    }

    /*
     * Exception decompiling
     */
    void tokenize(String var1_1, Vector var2_2) throws TransformerException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:376)
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
}

