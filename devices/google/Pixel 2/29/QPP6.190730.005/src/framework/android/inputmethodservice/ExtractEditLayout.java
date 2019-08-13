/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class ExtractEditLayout
extends LinearLayout {
    Button mExtractActionButton;

    public ExtractEditLayout(Context context) {
        super(context);
    }

    public ExtractEditLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mExtractActionButton = (Button)this.findViewById(16909021);
    }
}

