/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.AttributeSet;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsSeekBar;

public class SeekBar
extends AbsSeekBar {
    @UnsupportedAppUsage
    private OnSeekBarChangeListener mOnSeekBarChangeListener;

    public SeekBar(Context context) {
        this(context, null);
    }

    public SeekBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842875);
    }

    public SeekBar(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SeekBar(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return SeekBar.class.getName();
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.canUserSetProgress()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
        }
    }

    @UnsupportedAppUsage
    @Override
    void onProgressRefresh(float f, boolean bl, int n) {
        super.onProgressRefresh(f, bl, n);
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onProgressChanged(this, n, bl);
        }
    }

    @Override
    void onStartTrackingTouch() {
        super.onStartTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStartTrackingTouch(this);
        }
    }

    @Override
    void onStopTrackingTouch() {
        super.onStopTrackingTouch();
        OnSeekBarChangeListener onSeekBarChangeListener = this.mOnSeekBarChangeListener;
        if (onSeekBarChangeListener != null) {
            onSeekBarChangeListener.onStopTrackingTouch(this);
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener onSeekBarChangeListener) {
        this.mOnSeekBarChangeListener = onSeekBarChangeListener;
    }

    public static interface OnSeekBarChangeListener {
        public void onProgressChanged(SeekBar var1, int var2, boolean var3);

        public void onStartTrackingTouch(SeekBar var1);

        public void onStopTrackingTouch(SeekBar var1);
    }

}

