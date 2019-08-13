/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import java.util.List;
import java.util.function.BiConsumer;

public final class _$$Lambda$BluetoothAdapter$2$INSd_aND_SGWhhPZUtIqya_Uxw4
implements BiConsumer {
    private final /* synthetic */ BluetoothAdapter.2 f$0;

    public /* synthetic */ _$$Lambda$BluetoothAdapter$2$INSd_aND_SGWhhPZUtIqya_Uxw4(BluetoothAdapter.2 var1_1) {
        this.f$0 = var1_1;
    }

    public final void accept(Object object, Object object2) {
        this.f$0.lambda$onBluetoothServiceUp$0$BluetoothAdapter$2((BluetoothDevice)object, (List)object2);
    }
}

