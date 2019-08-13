/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Canvas;
import android.view.View;
import android.view.ViewDebug;

public final class _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw
implements ViewDebug.ViewOperation {
    private final /* synthetic */ View f$0;
    private final /* synthetic */ Canvas f$1;

    public /* synthetic */ _$$Lambda$ViewDebug$w986pBwzwNi77yEgLa3IWusjPNw(View view, Canvas canvas) {
        this.f$0 = view;
        this.f$1 = canvas;
    }

    @Override
    public final void run() {
        ViewDebug.lambda$profileViewDraw$2(this.f$0, this.f$1);
    }
}

