/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.dataconnection;

import com.android.internal.telephony.dataconnection.AccessNetworksManager;
import com.android.internal.telephony.dataconnection.TransportManager;

public final class _$$Lambda$TransportManager$Dk_40vYVb_woK0GbaXsHT3AecbY
implements TransportManager.HandoverParams.HandoverCallback {
    private final /* synthetic */ TransportManager f$0;
    private final /* synthetic */ AccessNetworksManager.QualifiedNetworks f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$TransportManager$Dk_40vYVb_woK0GbaXsHT3AecbY(TransportManager transportManager, AccessNetworksManager.QualifiedNetworks qualifiedNetworks, int n) {
        this.f$0 = transportManager;
        this.f$1 = qualifiedNetworks;
        this.f$2 = n;
    }

    @Override
    public final void onCompleted(boolean bl) {
        this.f$0.lambda$updateAvailableNetworks$0$TransportManager(this.f$1, this.f$2, bl);
    }
}

