/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class PreferenceImageView
extends ImageView {
    public PreferenceImageView(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public PreferenceImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreferenceImageView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public PreferenceImageView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        int n3;
        block8 : {
            int n4;
            block9 : {
                int n5;
                int n6;
                block7 : {
                    block5 : {
                        block6 : {
                            block4 : {
                                n4 = View.MeasureSpec.getMode(n);
                                if (n4 == Integer.MIN_VALUE) break block4;
                                n3 = n;
                                if (n4 != 0) break block5;
                            }
                            n5 = View.MeasureSpec.getSize(n);
                            n6 = this.getMaxWidth();
                            n3 = n;
                            if (n6 == Integer.MAX_VALUE) break block5;
                            if (n6 < n5) break block6;
                            n3 = n;
                            if (n4 != 0) break block5;
                        }
                        n3 = View.MeasureSpec.makeMeasureSpec(n6, Integer.MIN_VALUE);
                    }
                    if ((n6 = View.MeasureSpec.getMode(n2)) == Integer.MIN_VALUE) break block7;
                    n = n2;
                    if (n6 != 0) break block8;
                }
                n5 = View.MeasureSpec.getSize(n2);
                n4 = this.getMaxHeight();
                n = n2;
                if (n4 == Integer.MAX_VALUE) break block8;
                if (n4 < n5) break block9;
                n = n2;
                if (n6 != 0) break block8;
            }
            n = View.MeasureSpec.makeMeasureSpec(n4, Integer.MIN_VALUE);
        }
        super.onMeasure(n3, n);
    }
}

