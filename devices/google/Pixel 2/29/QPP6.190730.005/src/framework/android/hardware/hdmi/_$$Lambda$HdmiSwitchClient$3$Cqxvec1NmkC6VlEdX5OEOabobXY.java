/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiSwitchClient;
import com.android.internal.util.FunctionalUtils;
import java.util.concurrent.Executor;

public final class _$$Lambda$HdmiSwitchClient$3$Cqxvec1NmkC6VlEdX5OEOabobXY
implements FunctionalUtils.ThrowingRunnable {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ HdmiSwitchClient.OnSelectListener f$1;
    private final /* synthetic */ int f$2;

    public /* synthetic */ _$$Lambda$HdmiSwitchClient$3$Cqxvec1NmkC6VlEdX5OEOabobXY(Executor executor, HdmiSwitchClient.OnSelectListener onSelectListener, int n) {
        this.f$0 = executor;
        this.f$1 = onSelectListener;
        this.f$2 = n;
    }

    @Override
    public final void runOrThrow() {
        HdmiSwitchClient.3.lambda$onComplete$1(this.f$0, this.f$1, this.f$2);
    }
}

