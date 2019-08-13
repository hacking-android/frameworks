/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.annotation.UnsupportedAppUsage;
import android.os.storage.DiskInfo;
import android.os.storage.VolumeInfo;
import android.os.storage.VolumeRecord;

public class StorageEventListener {
    @UnsupportedAppUsage
    public void onDiskDestroyed(DiskInfo diskInfo) {
    }

    @UnsupportedAppUsage
    public void onDiskScanned(DiskInfo diskInfo, int n) {
    }

    @UnsupportedAppUsage
    public void onStorageStateChanged(String string2, String string3, String string4) {
    }

    @UnsupportedAppUsage
    public void onUsbMassStorageConnectionChanged(boolean bl) {
    }

    @UnsupportedAppUsage
    public void onVolumeForgotten(String string2) {
    }

    @UnsupportedAppUsage
    public void onVolumeRecordChanged(VolumeRecord volumeRecord) {
    }

    @UnsupportedAppUsage
    public void onVolumeStateChanged(VolumeInfo volumeInfo, int n, int n2) {
    }
}

