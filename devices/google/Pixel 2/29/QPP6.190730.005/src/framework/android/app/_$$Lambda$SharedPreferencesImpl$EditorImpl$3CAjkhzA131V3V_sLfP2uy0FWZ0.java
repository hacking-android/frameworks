/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.SharedPreferencesImpl;

public final class _$$Lambda$SharedPreferencesImpl$EditorImpl$3CAjkhzA131V3V_sLfP2uy0FWZ0
implements Runnable {
    private final /* synthetic */ SharedPreferencesImpl.EditorImpl f$0;
    private final /* synthetic */ SharedPreferencesImpl.MemoryCommitResult f$1;

    public /* synthetic */ _$$Lambda$SharedPreferencesImpl$EditorImpl$3CAjkhzA131V3V_sLfP2uy0FWZ0(SharedPreferencesImpl.EditorImpl editorImpl, SharedPreferencesImpl.MemoryCommitResult memoryCommitResult) {
        this.f$0 = editorImpl;
        this.f$1 = memoryCommitResult;
    }

    @Override
    public final void run() {
        this.f$0.lambda$notifyListeners$0$SharedPreferencesImpl$EditorImpl(this.f$1);
    }
}

