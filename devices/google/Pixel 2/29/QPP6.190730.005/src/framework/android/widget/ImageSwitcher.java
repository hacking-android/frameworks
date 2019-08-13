/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class ImageSwitcher
extends ViewSwitcher {
    public ImageSwitcher(Context context) {
        super(context);
    }

    public ImageSwitcher(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return ImageSwitcher.class.getName();
    }

    public void setImageDrawable(Drawable drawable2) {
        ((ImageView)this.getNextView()).setImageDrawable(drawable2);
        this.showNext();
    }

    public void setImageResource(int n) {
        ((ImageView)this.getNextView()).setImageResource(n);
        this.showNext();
    }

    public void setImageURI(Uri uri) {
        ((ImageView)this.getNextView()).setImageURI(uri);
        this.showNext();
    }
}

