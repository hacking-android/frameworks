/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.util.DebugUtils;
import com.android.internal.app.procstats.DumpUtils;
import com.android.internal.app.procstats.SparseMappingTable;
import java.io.PrintWriter;

public class SysMemUsageTable
extends SparseMappingTable.Table {
    public SysMemUsageTable(SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    private void dumpCategory(PrintWriter printWriter, String string2, String string3, int n, int n2) {
        printWriter.print(string2);
        printWriter.print(string3);
        printWriter.print(": ");
        DebugUtils.printSizeValue(printWriter, this.getValueForId((byte)n, n2) * 1024L);
        printWriter.print(" min, ");
        DebugUtils.printSizeValue(printWriter, this.getValueForId((byte)n, n2 + 1) * 1024L);
        printWriter.print(" avg, ");
        DebugUtils.printSizeValue(printWriter, this.getValueForId((byte)n, n2 + 2) * 1024L);
        printWriter.println(" max");
    }

    public static void mergeSysMemUsage(long[] arrl, int n, long[] arrl2, int n2) {
        block4 : {
            long l;
            long l2;
            block3 : {
                l2 = arrl[n + 0];
                l = arrl2[n2 + 0];
                if (l2 != 0L) break block3;
                arrl[n + 0] = l;
                for (int i = 1; i < 16; ++i) {
                    arrl[n + i] = arrl2[n2 + i];
                }
                break block4;
            }
            if (l <= 0L) break block4;
            arrl[n + 0] = l2 + l;
            for (int i = 1; i < 16; i += 3) {
                if (arrl[n + i] > arrl2[n2 + i]) {
                    arrl[n + i] = arrl2[n2 + i];
                }
                arrl[n + i + 1] = (long)(((double)arrl[n + i + 1] * (double)l2 + (double)arrl2[n2 + i + 1] * (double)l) / (double)(l2 + l));
                if (arrl[n + i + 2] >= arrl2[n2 + i + 2]) continue;
                arrl[n + i + 2] = arrl2[n2 + i + 2];
            }
        }
    }

    public void dump(PrintWriter printWriter, String string2, int[] arrn, int[] arrn2) {
        int n = -1;
        for (int i = 0; i < arrn.length; ++i) {
            int n2 = -1;
            for (int j = 0; j < arrn2.length; ++j) {
                int n3 = arrn[i];
                int n4 = arrn2[j];
                int n5 = (n3 + n4) * 14;
                long l = this.getValueForId((byte)n5, 0);
                if (l <= 0L) continue;
                printWriter.print(string2);
                if (arrn.length > 1) {
                    n = n != n3 ? n3 : -1;
                    DumpUtils.printScreenLabel(printWriter, n);
                    n = n3;
                }
                if (arrn2.length > 1) {
                    n2 = n2 != n4 ? n4 : -1;
                    DumpUtils.printMemLabel(printWriter, n2, '\u0000');
                    n2 = n4;
                }
                printWriter.print(": ");
                printWriter.print(l);
                printWriter.println(" samples:");
                this.dumpCategory(printWriter, string2, "  Cached", n5, 1);
                this.dumpCategory(printWriter, string2, "  Free", n5, 4);
                this.dumpCategory(printWriter, string2, "  ZRam", n5, 7);
                this.dumpCategory(printWriter, string2, "  Kernel", n5, 10);
                this.dumpCategory(printWriter, string2, "  Native", n5, 13);
            }
        }
    }

    public long[] getTotalMemUsage() {
        long[] arrl = new long[16];
        int n = this.getKeyCount();
        for (int i = 0; i < n; ++i) {
            int n2 = this.getKeyAt(i);
            SysMemUsageTable.mergeSysMemUsage(arrl, 0, this.getArrayForKey(n2), SparseMappingTable.getIndexFromKey(n2));
        }
        return arrl;
    }

    public void mergeStats(int n, long[] arrl, int n2) {
        n = this.getOrAddKey((byte)n, 16);
        SysMemUsageTable.mergeSysMemUsage(this.getArrayForKey(n), SparseMappingTable.getIndexFromKey(n), arrl, n2);
    }

    public void mergeStats(SysMemUsageTable sysMemUsageTable) {
        int n = sysMemUsageTable.getKeyCount();
        for (int i = 0; i < n; ++i) {
            int n2 = sysMemUsageTable.getKeyAt(i);
            this.mergeStats(SparseMappingTable.getIdFromKey(n2), sysMemUsageTable.getArrayForKey(n2), SparseMappingTable.getIndexFromKey(n2));
        }
    }
}

