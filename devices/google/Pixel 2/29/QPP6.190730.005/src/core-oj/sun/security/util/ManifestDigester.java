/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.security.MessageDigest;
import java.util.HashMap;

public class ManifestDigester {
    public static final String MF_MAIN_ATTRS = "Manifest-Main-Attributes";
    private HashMap<String, Entry> entries;
    private byte[] rawBytes;

    /*
     * Exception decompiling
     */
    public ManifestDigester(byte[] var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [4[WHILELOOP]], but top level block is 1[TRYBLOCK]
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

    private boolean findSection(int n, Position position) {
        int n2 = n;
        int n3 = this.rawBytes.length;
        boolean bl = true;
        position.endOfFirstLine = -1;
        int n4 = n;
        while (n2 < n3) {
            block11 : {
                block10 : {
                    block8 : {
                        block9 : {
                            byte by = this.rawBytes[n2];
                            n = n2;
                            if (by == 10) break block8;
                            if (by == 13) break block9;
                            bl = false;
                            n = n2;
                            break block10;
                        }
                        if (position.endOfFirstLine == -1) {
                            position.endOfFirstLine = n2 - 1;
                        }
                        n = n2;
                        if (n2 < n3) {
                            n = n2;
                            if (this.rawBytes[n2 + 1] == 10) {
                                n = n2 + 1;
                            }
                        }
                    }
                    if (position.endOfFirstLine == -1) {
                        position.endOfFirstLine = n - 1;
                    }
                    if (bl || n == n3 - 1) break block11;
                    n4 = n;
                    bl = true;
                }
                n2 = n + 1;
                continue;
            }
            position.endOfSection = n == n3 - 1 ? n : n4;
            position.startOfNext = n + 1;
            return true;
        }
        return false;
    }

    private boolean isNameAttr(byte[] arrby, int n) {
        boolean bl = !(arrby[n] != 78 && arrby[n] != 110 || arrby[n + 1] != 97 && arrby[n + 1] != 65 || arrby[n + 2] != 109 && arrby[n + 2] != 77 || arrby[n + 3] != 101 && arrby[n + 3] != 69 || arrby[n + 4] != 58 || arrby[n + 5] != 32);
        return bl;
    }

    public Entry get(String object, boolean bl) {
        if ((object = this.entries.get(object)) != null) {
            ((Entry)object).oldStyle = bl;
        }
        return object;
    }

    public byte[] manifestDigest(MessageDigest messageDigest) {
        messageDigest.reset();
        byte[] arrby = this.rawBytes;
        messageDigest.update(arrby, 0, arrby.length);
        return messageDigest.digest();
    }

    public static class Entry {
        int length;
        int lengthWithBlankLine;
        int offset;
        boolean oldStyle;
        byte[] rawBytes;

        public Entry(int n, int n2, int n3, byte[] arrby) {
            this.offset = n;
            this.length = n2;
            this.lengthWithBlankLine = n3;
            this.rawBytes = arrby;
        }

        private void doOldStyle(MessageDigest messageDigest, byte[] arrby, int n, int n2) {
            int n3;
            int n4 = n;
            int n5 = -1;
            for (n3 = n; n3 < n + n2; ++n3) {
                int n6 = n4;
                if (arrby[n3] == 13) {
                    n6 = n4;
                    if (n5 == 32) {
                        messageDigest.update(arrby, n4, n3 - n4 - 1);
                        n6 = n3;
                    }
                }
                n5 = arrby[n3];
                n4 = n6;
            }
            messageDigest.update(arrby, n4, n3 - n4);
        }

        public byte[] digest(MessageDigest messageDigest) {
            messageDigest.reset();
            if (this.oldStyle) {
                this.doOldStyle(messageDigest, this.rawBytes, this.offset, this.lengthWithBlankLine);
            } else {
                messageDigest.update(this.rawBytes, this.offset, this.lengthWithBlankLine);
            }
            return messageDigest.digest();
        }

        public byte[] digestWorkaround(MessageDigest messageDigest) {
            messageDigest.reset();
            messageDigest.update(this.rawBytes, this.offset, this.length);
            return messageDigest.digest();
        }
    }

    static class Position {
        int endOfFirstLine;
        int endOfSection;
        int startOfNext;

        Position() {
        }
    }

}

