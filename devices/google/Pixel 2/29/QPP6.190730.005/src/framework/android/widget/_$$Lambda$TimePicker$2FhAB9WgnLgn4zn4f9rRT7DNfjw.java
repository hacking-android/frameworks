/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.widget.TimePicker;

public final class _$$Lambda$TimePicker$2FhAB9WgnLgn4zn4f9rRT7DNfjw
implements TimePicker.OnTimeChangedListener {
    private final /* synthetic */ TimePicker f$0;
    private final /* synthetic */ Context f$1;

    public /* synthetic */ _$$Lambda$TimePicker$2FhAB9WgnLgn4zn4f9rRT7DNfjw(TimePicker timePicker, Context context) {
        this.f$0 = timePicker;
        this.f$1 = context;
    }

    @Override
    public final void onTimeChanged(TimePicker timePicker, int n, int n2) {
        this.f$0.lambda$new$0$TimePicker(this.f$1, timePicker, n, n2);
    }
}

