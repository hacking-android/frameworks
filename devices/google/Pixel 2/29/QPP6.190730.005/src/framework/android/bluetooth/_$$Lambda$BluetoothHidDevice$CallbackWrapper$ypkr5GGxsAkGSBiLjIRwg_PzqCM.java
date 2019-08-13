/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$ypkr5GGxsAkGSBiLjIRwg_PzqCM
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ byte f$2;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$ypkr5GGxsAkGSBiLjIRwg_PzqCM(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice, byte by) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
        this.f$2 = by;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSetProtocol$4$BluetoothHidDevice$CallbackWrapper(this.f$1, this.f$2);
    }
}

