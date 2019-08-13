/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.okhttp.internalandroidapi.Dns
 */
package android.net;

import android.net.Network;
import com.android.okhttp.internalandroidapi.Dns;
import java.util.List;

public final class _$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ
implements Dns {
    private final /* synthetic */ Network f$0;

    public /* synthetic */ _$$Lambda$Network$KD6DxaMRJIcajhj36TU1K7lJnHQ(Network network) {
        this.f$0 = network;
    }

    public final List lookup(String string2) {
        return this.f$0.lambda$maybeInitUrlConnectionFactory$0$Network(string2);
    }
}

