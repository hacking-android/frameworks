/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$NFluHjT4zTfYBRXClu_2k6mPKFI
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;
    private final /* synthetic */ boolean f$2;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$NFluHjT4zTfYBRXClu_2k6mPKFI(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice, boolean bl) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
        this.f$2 = bl;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onAppStatusChanged$0$BluetoothHidDevice$CallbackWrapper(this.f$1, this.f$2);
    }
}

