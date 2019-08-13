/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$xW99_tc95OmGApoKnpQ9q1TXb9k
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ byte f$2;
    private final /* synthetic */ byte[] f$3;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$xW99_tc95OmGApoKnpQ9q1TXb9k(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice, byte by, byte[] arrby) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
        this.f$2 = by;
        this.f$3 = arrby;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onInterruptData$5$BluetoothHidDevice$CallbackWrapper(this.f$1, this.f$2, this.f$3);
    }
}

