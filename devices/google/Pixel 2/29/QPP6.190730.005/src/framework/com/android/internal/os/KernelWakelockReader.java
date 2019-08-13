/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.os;

import android.os.Process;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.KernelWakelockStats;
import java.util.Collection;
import java.util.Iterator;

public class KernelWakelockReader {
    private static final int[] PROC_WAKELOCKS_FORMAT;
    private static final String TAG = "KernelWakelockReader";
    private static final int[] WAKEUP_SOURCES_FORMAT;
    private static int sKernelWakelockUpdateVersion = 0;
    private static final String sWakelockFile = "/proc/wakelocks";
    private static final String sWakeupSourceFile = "/d/wakeup_sources";
    private final long[] mProcWakelocksData = new long[3];
    private final String[] mProcWakelocksName = new String[3];

    static {
        sKernelWakelockUpdateVersion = 0;
        PROC_WAKELOCKS_FORMAT = new int[]{5129, 8201, 9, 9, 9, 8201};
        WAKEUP_SOURCES_FORMAT = new int[]{4105, 8457, 265, 265, 265, 265, 8457};
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public KernelWakelockStats parseProcWakelocks(byte[] var1_1, int var2_7, boolean var3_8, KernelWakelockStats var4_9) {
        for (var5_10 = 0; var5_10 < var2_7 && var1_1[var5_10] != 10 && var1_1[var5_10] != false; ++var5_10) {
        }
        var6_11 = ++var5_10;
        // MONITORENTER : this
        ++KernelWakelockReader.sKernelWakelockUpdateVersion;
        while (var6_11 < var2_7) {
            block28 : {
                block29 : {
                    for (var6_11 = var5_10; var6_11 < var2_7 && var1_1[var6_11] != 10 && (var7_12 /* !! */  = var1_1[var6_11]) != false; ++var6_11) {
                    }
                    if (var6_11 > var2_7 - 1) break;
                    var8_13 = this.mProcWakelocksName;
                    var9_14 = this.mProcWakelocksData;
                    for (var7_12 /* !! */  = (reference)var5_10; var7_12 /* !! */  < var6_11; ++var7_12 /* !! */ ) {
                        if ((var1_1[var7_12 /* !! */ ] & 128) == 0) continue;
                        var1_1[var7_12 /* !! */ ] = (byte)63;
                    }
                    if (var3_8) {
                        try {
                            var10_15 = KernelWakelockReader.WAKEUP_SOURCES_FORMAT;
                        }
                        catch (Throwable var1_2) {
                            throw var1_6;
                        }
                    } else {
                        var10_15 = KernelWakelockReader.PROC_WAKELOCKS_FORMAT;
                    }
                    try {
                        var11_17 = Process.parseProcLine((byte[])var1_1, var5_10, var6_11, (int[])var10_15, var8_13, (long[])var9_14, null);
                        var10_15 = var8_13[0].trim();
                    }
                    catch (Throwable var1_3) {
                        throw var1_6;
                    }
                    var7_12 /* !! */  = (reference)var9_14[1];
                    if (!var3_8) break block29;
                    var12_18 = var9_14[2] * 1000L;
                    ** GOTO lbl37
                }
                var12_18 = (var9_14[2] + 500L) / 1000L;
lbl37: // 2 sources:
                if (var11_17 && var10_15.length() > 0) {
                    if (!var4_9.containsKey(var10_15)) {
                        var9_14 = new KernelWakelockStats.Entry((int)var7_12 /* !! */ , var12_18, KernelWakelockReader.sKernelWakelockUpdateVersion);
                        var4_9.put(var10_15, var9_14);
                    } else {
                        var10_15 = (KernelWakelockStats.Entry)var4_9.get(var10_15);
                        if (var10_15.mVersion == KernelWakelockReader.sKernelWakelockUpdateVersion) {
                            var10_15.mCount += var7_12 /* !! */ ;
                            var10_15.mTotalTime += var12_18;
                        } else {
                            var10_15.mCount = (int)var7_12 /* !! */ ;
                            var10_15.mTotalTime = var12_18;
                            var10_15.mVersion = KernelWakelockReader.sKernelWakelockUpdateVersion;
                        }
                    }
                    break block28;
                }
                if (var11_17) break block28;
                try {
                    var9_14 = new StringBuilder();
                    var9_14.append("Failed to parse proc line: ");
                    var10_15 = new String((byte[])var1_1, var5_10, var6_11 - var5_10);
                    var9_14.append((String)var10_15);
                    Slog.wtf("KernelWakelockReader", var9_14.toString());
                }
                catch (Exception var10_16) {
                    Slog.wtf("KernelWakelockReader", "Failed to parse proc line!");
                }
            }
            var5_10 = var6_11 + 1;
            continue;
            catch (Throwable var1_4) {
                throw var1_6;
            }
        }
        try {
            var1_1 = var4_9.values().iterator();
            do {
                if (!var1_1.hasNext()) {
                    var4_9.kernelWakelockVersion = KernelWakelockReader.sKernelWakelockUpdateVersion;
                    // MONITOREXIT : this
                    return var4_9;
                }
                if (((KernelWakelockStats.Entry)var1_1.next()).mVersion == KernelWakelockReader.sKernelWakelockUpdateVersion) continue;
                var1_1.remove();
            } while (true);
        }
        catch (Throwable var1_5) {
            // empty catch block
        }
        throw var1_6;
    }

    /*
     * Exception decompiling
     */
    public final KernelWakelockStats readKernelWakelockStats(KernelWakelockStats var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 8[CATCHBLOCK]
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

