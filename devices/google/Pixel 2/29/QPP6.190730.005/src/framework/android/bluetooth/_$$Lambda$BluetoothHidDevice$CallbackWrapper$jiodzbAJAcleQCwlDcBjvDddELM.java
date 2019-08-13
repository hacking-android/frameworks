/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;

public final class _$$Lambda$BluetoothHidDevice$CallbackWrapper$jiodzbAJAcleQCwlDcBjvDddELM
implements Runnable {
    private final /* synthetic */ BluetoothHidDevice.CallbackWrapper f$0;
    private final /* synthetic */ BluetoothDevice f$1;

    public /* synthetic */ _$$Lambda$BluetoothHidDevice$CallbackWrapper$jiodzbAJAcleQCwlDcBjvDddELM(BluetoothHidDevice.CallbackWrapper callbackWrapper, BluetoothDevice bluetoothDevice) {
        this.f$0 = callbackWrapper;
        this.f$1 = bluetoothDevice;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onVirtualCableUnplug$6$BluetoothHidDevice$CallbackWrapper(this.f$1);
    }
}

