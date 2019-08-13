/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$qtStwQVkGfOs2iJIiePWqJJpi0w
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$qtStwQVkGfOs2iJIiePWqJJpi0w(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice, int n) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
        this.f$2 = n;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onConnectionStateChanged$1$BluetoothHidDevice$CallbackWrapper(this.f$1, this.f$2);
    }
}

