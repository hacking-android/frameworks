/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class PictureDrawable
extends Drawable {
    private Picture mPicture;

    public PictureDrawable(Picture picture) {
        this.mPicture = picture;
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mPicture != null) {
            Rect rect = this.getBounds();
            canvas.save();
            canvas.clipRect(rect);
            canvas.translate(rect.left, rect.top);
            canvas.drawPicture(this.mPicture);
            canvas.restore();
        }
    }

    @Override
    public int getIntrinsicHeight() {
        Picture picture = this.mPicture;
        int n = picture != null ? picture.getHeight() : -1;
        return n;
    }

    @Override
    public int getIntrinsicWidth() {
        Picture picture = this.mPicture;
        int n = picture != null ? picture.getWidth() : -1;
        return n;
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    public Picture getPicture() {
        return this.mPicture;
    }

    @Override
    public void setAlpha(int n) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
    }

    public void setPicture(Picture picture) {
        this.mPicture = picture;
    }
}

