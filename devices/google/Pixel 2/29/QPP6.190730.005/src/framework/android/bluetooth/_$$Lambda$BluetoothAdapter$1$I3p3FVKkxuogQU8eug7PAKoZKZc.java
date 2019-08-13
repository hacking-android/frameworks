/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

public final class _$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc
implements Runnable {
    private final /* synthetic */ BluetoothAdapter.OnMetadataChangedListener f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ byte[] f$3;

    public /* synthetic */ _$$Lambda$BluetoothAdapter$1$I3p3FVKkxuogQU8eug7PAKoZKZc(BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener, BluetoothDevice bluetoothDevice, int n, byte[] arrby) {
        this.f$0 = onMetadataChangedListener;
        this.f$1 = bluetoothDevice;
        this.f$2 = n;
        this.f$3 = arrby;
    }

    @Override
    public final void run() {
        BluetoothAdapter.1.lambda$onMetadataChanged$0(this.f$0, this.f$1, this.f$2, this.f$3);
    }
}

