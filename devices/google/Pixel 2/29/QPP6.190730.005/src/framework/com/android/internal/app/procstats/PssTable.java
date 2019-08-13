/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;
import com.android.internal.app.procstats.SparseMappingTable;

public class PssTable
extends SparseMappingTable.Table {
    public PssTable(SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public static void mergeStats(long[] arrl, int n, int n2, long l, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9) {
        long l10 = arrl[n + 0];
        if (l10 == 0L) {
            arrl[n + 0] = n2;
            arrl[n + 1] = l;
            arrl[n + 2] = l2;
            arrl[n + 3] = l3;
            arrl[n + 4] = l4;
            arrl[n + 5] = l5;
            arrl[n + 6] = l6;
            arrl[n + 7] = l7;
            arrl[n + 8] = l8;
            arrl[n + 9] = l9;
        } else {
            arrl[n + 0] = (long)n2 + l10;
            if (arrl[n + 1] > l) {
                arrl[n + 1] = l;
            }
            arrl[n + 2] = (long)(((double)arrl[n + 2] * (double)l10 + (double)l2 * (double)n2) / (double)((long)n2 + l10));
            if (arrl[n + 3] < l3) {
                arrl[n + 3] = l3;
            }
            if (arrl[n + 4] > l4) {
                arrl[n + 4] = l4;
            }
            arrl[n + 5] = (long)(((double)arrl[n + 5] * (double)l10 + (double)l5 * (double)n2) / (double)((long)n2 + l10));
            if (arrl[n + 6] < l6) {
                arrl[n + 6] = l6;
            }
            if (arrl[n + 7] > l7) {
                arrl[n + 7] = l7;
            }
            arrl[n + 8] = (long)(((double)arrl[n + 8] * (double)l10 + (double)l8 * (double)n2) / (double)((long)n2 + l10));
            if (arrl[n + 9] < l9) {
                arrl[n + 9] = l9;
            }
        }
    }

    public static void mergeStats(long[] arrl, int n, long[] arrl2, int n2) {
        PssTable.mergeStats(arrl, n, (int)arrl2[n2 + 0], arrl2[n2 + 1], arrl2[n2 + 2], arrl2[n2 + 3], arrl2[n2 + 4], arrl2[n2 + 5], arrl2[n2 + 6], arrl2[n2 + 7], arrl2[n2 + 8], arrl2[n2 + 9]);
    }

    public static void writeStatsToProto(ProtoOutputStream protoOutputStream, long[] arrl, int n) {
        protoOutputStream.write(1120986464261L, arrl[n + 0]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268038L, arrl[n + 1], arrl[n + 2], arrl[n + 3]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268039L, arrl[n + 4], arrl[n + 5], arrl[n + 6]);
        ProtoUtils.toAggStatsProto(protoOutputStream, 1146756268040L, arrl[n + 7], arrl[n + 8], arrl[n + 9]);
    }

    public void mergeStats(int n, int n2, long l, long l2, long l3, long l4, long l5, long l6, long l7, long l8, long l9) {
        n = this.getOrAddKey((byte)n, 10);
        PssTable.mergeStats(this.getArrayForKey(n), SparseMappingTable.getIndexFromKey(n), n2, l, l2, l3, l4, l5, l6, l7, l8, l9);
    }

    public void mergeStats(PssTable pssTable) {
        int n = pssTable.getKeyCount();
        for (int i = 0; i < n; ++i) {
            int n2 = pssTable.getKeyAt(i);
            int n3 = this.getOrAddKey(SparseMappingTable.getIdFromKey(n2), 10);
            PssTable.mergeStats(this.getArrayForKey(n3), SparseMappingTable.getIndexFromKey(n3), pssTable.getArrayForKey(n2), SparseMappingTable.getIndexFromKey(n2));
        }
    }

    public void writeStatsToProtoForKey(ProtoOutputStream protoOutputStream, int n) {
        PssTable.writeStatsToProto(protoOutputStream, this.getArrayForKey(n), SparseMappingTable.getIndexFromKey(n));
    }
}

