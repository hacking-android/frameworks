/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.RecordingCanvas;
import android.view.View;
import android.view.ViewDebug;

public final class _$$Lambda$ViewDebug$flFXZc7_CjFXx7_tYT59WSbUNjI
implements ViewDebug.ViewOperation {
    private final /* synthetic */ View f$0;
    private final /* synthetic */ RecordingCanvas f$1;

    public /* synthetic */ _$$Lambda$ViewDebug$flFXZc7_CjFXx7_tYT59WSbUNjI(View view, RecordingCanvas recordingCanvas) {
        this.f$0 = view;
        this.f$1 = recordingCanvas;
    }

    @Override
    public final void run() {
        ViewDebug.lambda$profileViewDraw$1(this.f$0, this.f$1);
    }
}

