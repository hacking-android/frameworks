/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;
import java.io.InputStream;

public class ImageSpan
extends DynamicDrawableSpan {
    private Uri mContentUri;
    private Context mContext;
    @UnsupportedAppUsage
    private Drawable mDrawable;
    private int mResourceId;
    private String mSource;

    public ImageSpan(Context context, int n) {
        this(context, n, 0);
    }

    public ImageSpan(Context context, int n, int n2) {
        super(n2);
        this.mContext = context;
        this.mResourceId = n;
    }

    public ImageSpan(Context context, Bitmap bitmap) {
        this(context, bitmap, 0);
    }

    public ImageSpan(Context object, Bitmap bitmap, int n) {
        super(n);
        this.mContext = object;
        object = object != null ? new BitmapDrawable(((Context)object).getResources(), bitmap) : new BitmapDrawable(bitmap);
        this.mDrawable = object;
        n = this.mDrawable.getIntrinsicWidth();
        int n2 = this.mDrawable.getIntrinsicHeight();
        object = this.mDrawable;
        if (n <= 0) {
            n = 0;
        }
        if (n2 <= 0) {
            n2 = 0;
        }
        ((Drawable)object).setBounds(0, 0, n, n2);
    }

    public ImageSpan(Context context, Uri uri) {
        this(context, uri, 0);
    }

    public ImageSpan(Context context, Uri uri, int n) {
        super(n);
        this.mContext = context;
        this.mContentUri = uri;
        this.mSource = uri.toString();
    }

    @Deprecated
    public ImageSpan(Bitmap bitmap) {
        this(null, bitmap, 0);
    }

    @Deprecated
    public ImageSpan(Bitmap bitmap, int n) {
        this(null, bitmap, n);
    }

    public ImageSpan(Drawable drawable2) {
        this(drawable2, 0);
    }

    public ImageSpan(Drawable drawable2, int n) {
        super(n);
        this.mDrawable = drawable2;
    }

    public ImageSpan(Drawable drawable2, String string2) {
        this(drawable2, string2, 0);
    }

    public ImageSpan(Drawable drawable2, String string2, int n) {
        super(n);
        this.mDrawable = drawable2;
        this.mSource = string2;
    }

    @Override
    public Drawable getDrawable() {
        Object object = null;
        Object object2 = null;
        if (this.mDrawable != null) {
            object = this.mDrawable;
        } else if (this.mContentUri != null) {
            Object object3;
            object = object2;
            InputStream inputStream = this.mContext.getContentResolver().openInputStream(this.mContentUri);
            object = object2;
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            object = object2;
            object = object2;
            object = object2 = (object3 = new BitmapDrawable(this.mContext.getResources(), bitmap));
            ((Drawable)object2).setBounds(0, 0, ((Drawable)object2).getIntrinsicWidth(), ((Drawable)object2).getIntrinsicHeight());
            object = object2;
            try {
                inputStream.close();
                object = object2;
            }
            catch (Exception exception) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Failed to loaded content ");
                ((StringBuilder)object3).append(this.mContentUri);
                Log.e("ImageSpan", ((StringBuilder)object3).toString(), exception);
            }
        } else {
            object = object2 = this.mContext.getDrawable(this.mResourceId);
            try {
                ((Drawable)object2).setBounds(0, 0, ((Drawable)object2).getIntrinsicWidth(), ((Drawable)object2).getIntrinsicHeight());
                object = object2;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to find resource: ");
                stringBuilder.append(this.mResourceId);
                Log.e("ImageSpan", stringBuilder.toString());
            }
        }
        return object;
    }

    public String getSource() {
        return this.mSource;
    }
}

