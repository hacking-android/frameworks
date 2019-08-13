/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.annotation.SystemApi;
import android.bluetooth.le.ResultStorageDescriptor;
import android.bluetooth.le.ScanFilter;
import java.util.List;

@SystemApi
public final class TruncatedFilter {
    private final ScanFilter mFilter;
    private final List<ResultStorageDescriptor> mStorageDescriptors;

    public TruncatedFilter(ScanFilter scanFilter, List<ResultStorageDescriptor> list) {
        this.mFilter = scanFilter;
        this.mStorageDescriptors = list;
    }

    public ScanFilter getFilter() {
        return this.mFilter;
    }

    public List<ResultStorageDescriptor> getStorageDescriptors() {
        return this.mStorageDescriptors;
    }
}

