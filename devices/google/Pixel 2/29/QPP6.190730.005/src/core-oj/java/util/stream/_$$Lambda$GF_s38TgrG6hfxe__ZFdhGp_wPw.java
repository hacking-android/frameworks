/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.stream.Sink;
import java.util.stream.SpinedBuffer;

public final class _$$Lambda$GF_s38TgrG6hfxe__ZFdhGp_wPw
implements Sink {
    private final /* synthetic */ SpinedBuffer f$0;

    public /* synthetic */ _$$Lambda$GF_s38TgrG6hfxe__ZFdhGp_wPw(SpinedBuffer spinedBuffer) {
        this.f$0 = spinedBuffer;
    }

    @Override
    public final void accept(Object object) {
        this.f$0.accept(object);
    }
}

