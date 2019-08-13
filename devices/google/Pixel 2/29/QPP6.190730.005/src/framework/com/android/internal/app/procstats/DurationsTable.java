/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app.procstats;

import com.android.internal.app.procstats.SparseMappingTable;

public class DurationsTable
extends SparseMappingTable.Table {
    public DurationsTable(SparseMappingTable sparseMappingTable) {
        super(sparseMappingTable);
    }

    public void addDuration(int n, long l) {
        n = this.getOrAddKey((byte)n, 1);
        this.setValue(n, this.getValue(n) + l);
    }

    public void addDurations(DurationsTable durationsTable) {
        int n = durationsTable.getKeyCount();
        for (int i = 0; i < n; ++i) {
            int n2 = durationsTable.getKeyAt(i);
            this.addDuration(SparseMappingTable.getIdFromKey(n2), durationsTable.getValue(n2));
        }
    }
}

