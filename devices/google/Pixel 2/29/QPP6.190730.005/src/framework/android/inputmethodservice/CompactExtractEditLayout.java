/*
 * Decompiled with CFR 0.145.
 */
package android.inputmethodservice;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class CompactExtractEditLayout
extends LinearLayout {
    private View mInputExtractAccessories;
    private View mInputExtractAction;
    private View mInputExtractEditText;
    private boolean mPerformLayoutChanges;

    public CompactExtractEditLayout(Context context) {
        super(context);
    }

    public CompactExtractEditLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CompactExtractEditLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private int applyFractionInt(int n, int n2) {
        return Math.round(this.getResources().getFraction(n, n2, n2));
    }

    private void applyProportionalLayout(int n, int n2) {
        if (this.getResources().getConfiguration().isScreenRound()) {
            this.setGravity(80);
        }
        CompactExtractEditLayout.setLayoutHeight(this, this.applyFractionInt(18022409, n2));
        this.setPadding(this.applyFractionInt(18022410, n), 0, this.applyFractionInt(18022412, n), 0);
        CompactExtractEditLayout.setLayoutMarginBottom(this.mInputExtractEditText, this.applyFractionInt(18022413, n2));
        CompactExtractEditLayout.setLayoutMarginBottom(this.mInputExtractAccessories, this.applyFractionInt(18022408, n2));
    }

    private static void setLayoutHeight(View view, int n) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = n;
        view.setLayoutParams(layoutParams);
    }

    private static void setLayoutMarginBottom(View view, int n) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        marginLayoutParams.bottomMargin = n;
        view.setLayoutParams(marginLayoutParams);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mPerformLayoutChanges) {
            int n;
            Object object = this.getResources();
            Configuration configuration = ((Resources)object).getConfiguration();
            object = ((Resources)object).getDisplayMetrics();
            int n2 = ((DisplayMetrics)object).widthPixels;
            int n3 = n = ((DisplayMetrics)object).heightPixels;
            if (configuration.isScreenRound()) {
                n3 = n;
                if (n < n2) {
                    n3 = n2;
                }
            }
            this.applyProportionalLayout(n2, n3);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputExtractEditText = this.findViewById(16908325);
        this.mInputExtractAccessories = this.findViewById(16909020);
        this.mInputExtractAction = this.findViewById(16909021);
        if (this.mInputExtractEditText != null && this.mInputExtractAccessories != null && this.mInputExtractAction != null) {
            this.mPerformLayoutChanges = true;
        }
    }
}

