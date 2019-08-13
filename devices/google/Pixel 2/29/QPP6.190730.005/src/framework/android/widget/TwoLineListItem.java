/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
public class TwoLineListItem
extends RelativeLayout {
    private TextView mText1;
    private TextView mText2;

    public TwoLineListItem(Context context) {
        this(context, null, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public TwoLineListItem(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TwoLineListItem, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.TwoLineListItem, attributeSet, typedArray, n, n2);
        typedArray.recycle();
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return TwoLineListItem.class.getName();
    }

    public TextView getText1() {
        return this.mText1;
    }

    public TextView getText2() {
        return this.mText2;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mText1 = (TextView)this.findViewById(16908308);
        this.mText2 = (TextView)this.findViewById(16908309);
    }
}

