/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaController2;
import android.media.Session2CommandGroup;

public final class _$$Lambda$MediaController2$gnK9yj9twHASv8Ka73nuD8kdCG8
implements Runnable {
    private final /* synthetic */ MediaController2 f$0;
    private final /* synthetic */ Session2CommandGroup f$1;

    public /* synthetic */ _$$Lambda$MediaController2$gnK9yj9twHASv8Ka73nuD8kdCG8(MediaController2 mediaController2, Session2CommandGroup session2CommandGroup) {
        this.f$0 = mediaController2;
        this.f$1 = session2CommandGroup;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onConnected$2$MediaController2(this.f$1);
    }
}

