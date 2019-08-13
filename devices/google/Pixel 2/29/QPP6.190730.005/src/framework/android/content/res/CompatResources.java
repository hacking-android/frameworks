/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import java.lang.ref.WeakReference;

public class CompatResources
extends Resources {
    private WeakReference<Context> mContext = new WeakReference<Object>(null);

    public CompatResources(ClassLoader classLoader) {
        super(classLoader);
    }

    private Resources.Theme getTheme() {
        Object object = (Context)this.mContext.get();
        object = object != null ? ((Context)object).getTheme() : null;
        return object;
    }

    @Override
    public int getColor(int n) throws Resources.NotFoundException {
        return this.getColor(n, this.getTheme());
    }

    @Override
    public ColorStateList getColorStateList(int n) throws Resources.NotFoundException {
        return this.getColorStateList(n, this.getTheme());
    }

    @Override
    public Drawable getDrawable(int n) throws Resources.NotFoundException {
        return this.getDrawable(n, this.getTheme());
    }

    @Override
    public Drawable getDrawableForDensity(int n, int n2) throws Resources.NotFoundException {
        return this.getDrawableForDensity(n, n2, this.getTheme());
    }

    public void setContext(Context context) {
        this.mContext = new WeakReference<Context>(context);
    }
}

