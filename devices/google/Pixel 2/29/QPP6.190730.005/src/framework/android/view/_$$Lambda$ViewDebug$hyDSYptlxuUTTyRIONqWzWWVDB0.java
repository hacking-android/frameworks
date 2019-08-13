/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Picture;
import android.view.ViewDebug;
import java.util.concurrent.Callable;
import java.util.function.Function;

public final class _$$Lambda$ViewDebug$hyDSYptlxuUTTyRIONqWzWWVDB0
implements Function {
    private final /* synthetic */ Callable f$0;

    public /* synthetic */ _$$Lambda$ViewDebug$hyDSYptlxuUTTyRIONqWzWWVDB0(Callable callable) {
        this.f$0 = callable;
    }

    public final Object apply(Object object) {
        return ViewDebug.lambda$startRenderingCommandsCapture$4(this.f$0, (Picture)object);
    }
}

