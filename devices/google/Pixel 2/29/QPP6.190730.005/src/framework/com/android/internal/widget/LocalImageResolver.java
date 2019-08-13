/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;

public class LocalImageResolver {
    private static final int MAX_SAFE_ICON_SIZE_PX = 480;

    private static BitmapFactory.Options getBoundsOptionsForImage(Uri object, Context object2) throws IOException {
        object2 = ((Context)object2).getContentResolver().openInputStream((Uri)object);
        object = new BitmapFactory.Options();
        ((BitmapFactory.Options)object).inJustDecodeBounds = true;
        BitmapFactory.decodeStream((InputStream)object2, null, (BitmapFactory.Options)object);
        ((InputStream)object2).close();
        return object;
    }

    private static int getPowerOfTwoForSampleRatio(double d) {
        return Math.max(1, Integer.highestOneBit((int)Math.floor(d)));
    }

    public static Drawable resolveImage(Uri object, Context context) throws IOException {
        Object object2 = LocalImageResolver.getBoundsOptionsForImage((Uri)object, context);
        if (((BitmapFactory.Options)object2).outWidth != -1 && ((BitmapFactory.Options)object2).outHeight != -1) {
            int n = ((BitmapFactory.Options)object2).outHeight > ((BitmapFactory.Options)object2).outWidth ? ((BitmapFactory.Options)object2).outHeight : ((BitmapFactory.Options)object2).outWidth;
            double d = n > 480 ? (double)(n / 480) : 1.0;
            object2 = new BitmapFactory.Options();
            ((BitmapFactory.Options)object2).inSampleSize = LocalImageResolver.getPowerOfTwoForSampleRatio(d);
            object = context.getContentResolver().openInputStream((Uri)object);
            object2 = BitmapFactory.decodeStream((InputStream)object, null, (BitmapFactory.Options)object2);
            ((InputStream)object).close();
            return new BitmapDrawable(context.getResources(), (Bitmap)object2);
        }
        return null;
    }
}

