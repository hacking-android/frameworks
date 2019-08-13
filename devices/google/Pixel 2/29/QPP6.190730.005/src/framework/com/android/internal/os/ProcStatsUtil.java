/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import com.android.internal.annotations.VisibleForTesting;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
public final class ProcStatsUtil {
    private static final boolean DEBUG = false;
    private static final int READ_SIZE = 1024;
    private static final String TAG = "ProcStatsUtil";

    private ProcStatsUtil() {
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    public static String readNullSeparatedFile(String string2) {
        String string3 = ProcStatsUtil.readSingleLineProcFile(string2);
        if (string3 == null) {
            return null;
        }
        int n = string3.indexOf("\u0000\u0000");
        string2 = string3;
        if (n != -1) {
            string2 = string3.substring(0, n);
        }
        return string2.replace("\u0000", " ");
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PROTECTED)
    public static String readSingleLineProcFile(String string2) {
        return ProcStatsUtil.readTerminatedProcFile(string2, (byte)10);
    }

    /*
     * Exception decompiling
     */
    public static String readTerminatedProcFile(String var0, byte var1_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 18[UNCONDITIONALDOLOOP]
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
}

