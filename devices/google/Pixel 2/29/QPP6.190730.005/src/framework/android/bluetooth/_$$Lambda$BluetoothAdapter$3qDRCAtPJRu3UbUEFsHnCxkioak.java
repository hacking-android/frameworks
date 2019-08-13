/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.util.Pair;
import java.util.function.Predicate;

public final class _$$Lambda$BluetoothAdapter$3qDRCAtPJRu3UbUEFsHnCxkioak
implements Predicate {
    private final /* synthetic */ BluetoothAdapter.OnMetadataChangedListener f$0;

    public /* synthetic */ _$$Lambda$BluetoothAdapter$3qDRCAtPJRu3UbUEFsHnCxkioak(BluetoothAdapter.OnMetadataChangedListener onMetadataChangedListener) {
        this.f$0 = onMetadataChangedListener;
    }

    public final boolean test(Object object) {
        return BluetoothAdapter.lambda$addOnMetadataChangedListener$0(this.f$0, (Pair)object);
    }
}

