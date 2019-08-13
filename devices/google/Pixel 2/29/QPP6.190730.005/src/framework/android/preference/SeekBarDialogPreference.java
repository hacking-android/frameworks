/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

@Deprecated
public class SeekBarDialogPreference
extends DialogPreference {
    private final Drawable mMyIcon;

    public SeekBarDialogPreference(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public SeekBarDialogPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17957065);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SeekBarDialogPreference(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.createActionButtons();
        this.mMyIcon = this.getDialogIcon();
        this.setDialogIcon(null);
    }

    protected static SeekBar getSeekBar(View view) {
        return (SeekBar)view.findViewById(16909328);
    }

    public void createActionButtons() {
        this.setPositiveButtonText(17039370);
        this.setNegativeButtonText(17039360);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        view = (ImageView)view.findViewById(16908294);
        Drawable drawable2 = this.mMyIcon;
        if (drawable2 != null) {
            ((ImageView)view).setImageDrawable(drawable2);
        } else {
            ((ImageView)view).setVisibility(8);
        }
    }
}

