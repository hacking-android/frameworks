/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$3bTGVlfKj7Y0SZdifW_Ya2myDKs
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ byte f$2;
    private final /* synthetic */ byte f$3;
    private final /* synthetic */ byte[] f$4;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$3bTGVlfKj7Y0SZdifW_Ya2myDKs(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice, byte by, byte by2, byte[] arrby) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
        this.f$2 = by;
        this.f$3 = by2;
        this.f$4 = arrby;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSetReport$3$BluetoothHidDevice$CallbackWrapper(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

