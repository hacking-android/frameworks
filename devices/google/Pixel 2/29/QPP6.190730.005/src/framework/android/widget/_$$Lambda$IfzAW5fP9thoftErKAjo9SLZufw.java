/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.widget.TextView;

public final class _$$Lambda$IfzAW5fP9thoftErKAjo9SLZufw
implements Runnable {
    private final /* synthetic */ TextView f$0;

    public /* synthetic */ _$$Lambda$IfzAW5fP9thoftErKAjo9SLZufw(TextView textView) {
        this.f$0 = textView;
    }

    @Override
    public final void run() {
        this.f$0.invalidate();
    }
}

