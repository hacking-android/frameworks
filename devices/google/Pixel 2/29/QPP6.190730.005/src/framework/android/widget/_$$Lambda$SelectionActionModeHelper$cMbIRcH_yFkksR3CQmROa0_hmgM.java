/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.text.Layout;
import android.widget.SelectionActionModeHelper;
import java.util.List;

public final class _$$Lambda$SelectionActionModeHelper$cMbIRcH_yFkksR3CQmROa0_hmgM
implements Layout.SelectionRectangleConsumer {
    private final /* synthetic */ List f$0;

    public /* synthetic */ _$$Lambda$SelectionActionModeHelper$cMbIRcH_yFkksR3CQmROa0_hmgM(List list) {
        this.f$0 = list;
    }

    @Override
    public final void accept(float f, float f2, float f3, float f4, int n) {
        SelectionActionModeHelper.lambda$convertSelectionToRectangles$2(this.f$0, f, f2, f3, f4, n);
    }
}

