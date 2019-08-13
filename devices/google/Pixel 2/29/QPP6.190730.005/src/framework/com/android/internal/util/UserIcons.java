/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

public class UserIcons {
    private static final int[] USER_ICON_COLORS = new int[]{17171004, 17171005, 17171006, 17171007, 17171008, 17171009, 17171010, 17171011};

    public static Bitmap convertToBitmap(Drawable drawable2) {
        if (drawable2 == null) {
            return null;
        }
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(n, n2, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable2.setBounds(0, 0, n, n2);
        drawable2.draw(canvas);
        return bitmap;
    }

    public static Drawable getDefaultUserIcon(Resources resources, int n, boolean bl) {
        Object object;
        int n2 = bl ? 17171013 : 17171012;
        if (n != -10000) {
            object = USER_ICON_COLORS;
            n2 = object[n % ((int[])object).length];
        }
        object = resources.getDrawable(17302280, null).mutate();
        ((Drawable)object).setColorFilter(resources.getColor(n2, null), PorterDuff.Mode.SRC_IN);
        ((Drawable)object).setBounds(0, 0, ((Drawable)object).getIntrinsicWidth(), ((Drawable)object).getIntrinsicHeight());
        return object;
    }
}

