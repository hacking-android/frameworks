/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.util.Pair;
import java.util.function.Predicate;

public final class _$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc
implements Predicate {
    private final /* synthetic */ BluetoothAdapter.OnMetadataChangedListener f$0;

    public /* synthetic */ _$$Lambda$BluetoothAdapter$dhiyWTssvWZcLytIeq61ARYDHrc(BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
        this.f$0 = onMetadataChangedListener;
    }

    public final boolean test(Object object) {
        return BluetoothAdapter.lambda$removeOnMetadataChangedListener$1(this.f$0, (Pair)object);
    }
}

