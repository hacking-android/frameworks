/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.media.session.MediaSessionManager;
import java.util.List;

public final class _$$Lambda$MediaSessionManager$Session2TokensChangedWrapper$1$4_TH2zkLY97pxK_e1EPxtPhZwdk
implements Runnable {
    private final /* synthetic */ MediaSessionManager.Session2TokensChangedWrapper.1 f$0;
    private final /* synthetic */ List f$1;

    public /* synthetic */ _$$Lambda$MediaSessionManager$Session2TokensChangedWrapper$1$4_TH2zkLY97pxK_e1EPxtPhZwdk(MediaSessionManager.Session2TokensChangedWrapper.1 var1_1, List list) {
        this.f$0 = var1_1;
        this.f$1 = list;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onSession2TokensChanged$0$MediaSessionManager$Session2TokensChangedWrapper$1(this.f$1);
    }
}

