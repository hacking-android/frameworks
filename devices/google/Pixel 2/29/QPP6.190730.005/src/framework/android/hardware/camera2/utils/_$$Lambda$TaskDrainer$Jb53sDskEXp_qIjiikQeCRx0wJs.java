/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

import android.hardware.camera2.utils.TaskDrainer;

public final class _$$Lambda$TaskDrainer$Jb53sDskEXp_qIjiikQeCRx0wJs
implements Runnable {
    private final /* synthetic */ TaskDrainer f$0;

    public /* synthetic */ _$$Lambda$TaskDrainer$Jb53sDskEXp_qIjiikQeCRx0wJs(TaskDrainer taskDrainer) {
        this.f$0 = taskDrainer;
    }

    @Override
    public final void run() {
        this.f$0.lambda$postDrained$0$TaskDrainer();
    }
}

