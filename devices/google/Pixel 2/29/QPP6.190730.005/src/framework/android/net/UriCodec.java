/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;

public final class UriCodec {
    private static final char INVALID_INPUT_CHARACTER = '\ufffd';

    private UriCodec() {
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void appendDecoded(StringBuilder var0, String var1_1, boolean var2_3, Charset var3_4, boolean var4_5) {
        var5_6 = var3_4.newDecoder().onMalformedInput(CodingErrorAction.REPLACE).replaceWith("\ufffd").onUnmappableCharacter(CodingErrorAction.REPORT);
        var3_4 = ByteBuffer.allocate(var1_1.length());
        var6_7 = '\u0000';
        do {
            block9 : {
                if (var6_7 >= var1_1.length()) {
                    UriCodec.flushDecodingByteAccumulator(var0, var5_6, var3_4, var4_5);
                    return;
                }
                var7_8 = var1_1.charAt(var6_7);
                var8_9 = var6_7 + 1;
                if (var7_8 != '%') {
                    var6_7 = '+';
                    if (var7_8 != '+') {
                        UriCodec.flushDecodingByteAccumulator(var0, var5_6, var3_4, var4_5);
                        var0.append(var7_8);
                        var6_7 = var8_9;
                        continue;
                    }
                    UriCodec.flushDecodingByteAccumulator(var0, var5_6, var3_4, var4_5);
                    var7_8 = var6_7;
                    if (var2_3) {
                        var7_8 = var6_7 = ' ';
                    }
                    var0.append(var7_8);
                    var6_7 = var8_9;
                    continue;
                }
                var6_7 = '\u0000';
                var9_10 = 0;
                var10_11 = var6_7;
                do lbl-1000: // 2 sources:
                {
                    var6_7 = var8_9;
                    if (var9_10 >= 2) break block9;
                    var7_8 = UriCodec.getNextCharacter(var1_1, var8_9, var1_1.length(), null);
                    ++var8_9;
                    break;
                } while (true);
                catch (URISyntaxException var1_2) {
                    if (var4_5 != false) throw new IllegalArgumentException(var1_2);
                    UriCodec.flushDecodingByteAccumulator(var0, var5_6, var3_4, var4_5);
                    var0.append('\ufffd');
                    return;
                }
                {
                    var6_7 = UriCodec.hexCharToValue(var7_8);
                    if (var6_7 < '\u0000') {
                        if (var4_5 != false) throw new IllegalArgumentException(UriCodec.unexpectedCharacterException(var1_1, null, var7_8, var8_9 - 1));
                        UriCodec.flushDecodingByteAccumulator(var0, var5_6, var3_4, var4_5);
                        var0.append('\ufffd');
                        var6_7 = var8_9;
                        break block9;
                    }
                    var6_7 = (char)(var10_11 * 16 + var6_7);
                    ++var9_10;
                    var10_11 = var6_7;
                    ** while (true)
                }
            }
            var3_4.put((byte)var10_11);
        } while (true);
    }

    public static String decode(String string2, boolean bl, Charset charset, boolean bl2) {
        StringBuilder stringBuilder = new StringBuilder(string2.length());
        UriCodec.appendDecoded(stringBuilder, string2, bl, charset, bl2);
        return stringBuilder.toString();
    }

    /*
     * Exception decompiling
     */
    private static void flushDecodingByteAccumulator(StringBuilder var0, CharsetDecoder var1_2, ByteBuffer var2_4, boolean var3_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    private static char getNextCharacter(String string2, int n, int n2, String string3) throws URISyntaxException {
        if (n >= n2) {
            StringBuilder stringBuilder;
            if (string3 == null) {
                string3 = "";
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(" in [");
                stringBuilder.append(string3);
                stringBuilder.append("]");
                string3 = stringBuilder.toString();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected end of string");
            stringBuilder.append(string3);
            throw new URISyntaxException(string2, stringBuilder.toString(), n);
        }
        return string2.charAt(n);
    }

    private static int hexCharToValue(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        if ('a' <= c && c <= 'f') {
            return c + 10 - 97;
        }
        if ('A' <= c && c <= 'F') {
            return c + 10 - 65;
        }
        return -1;
    }

    private static URISyntaxException unexpectedCharacterException(String string2, String string3, char c, int n) {
        StringBuilder stringBuilder;
        if (string3 == null) {
            string3 = "";
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append(" in [");
            stringBuilder.append(string3);
            stringBuilder.append("]");
            string3 = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected character");
        stringBuilder.append(string3);
        stringBuilder.append(": ");
        stringBuilder.append(c);
        return new URISyntaxException(string2, stringBuilder.toString(), n);
    }
}

