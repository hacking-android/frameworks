/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.ProgramList;
import java.util.concurrent.Executor;

public final class _$$Lambda$ProgramList$aDYMynqVdAUqeKXIxfNtN1u67zs
implements ProgramList.OnCompleteListener {
    private final /* synthetic */ Executor f$0;
    private final /* synthetic */ ProgramList.OnCompleteListener f$1;

    public /* synthetic */ _$$Lambda$ProgramList$aDYMynqVdAUqeKXIxfNtN1u67zs(Executor executor, ProgramList.OnCompleteListener onCompleteListener) {
        this.f$0 = executor;
        this.f$1 = onCompleteListener;
    }

    @Override
    public final void onComplete() {
        ProgramList.lambda$addOnCompleteListener$0(this.f$0, this.f$1);
    }
}

