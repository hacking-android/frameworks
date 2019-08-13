/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pools;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.LocalImageResolver;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.MessagingMessageState;
import com.android.internal.widget.MessagingTextMessage;
import java.io.IOException;

@RemoteViews.RemoteView
public class MessagingImageMessage
extends ImageView
implements MessagingMessage {
    private static final String TAG = "MessagingImageMessage";
    private static Pools.SimplePool<MessagingImageMessage> sInstancePool = new Pools.SynchronizedPool<MessagingImageMessage>(10);
    private int mActualHeight;
    private int mActualWidth;
    private float mAspectRatio;
    private Drawable mDrawable;
    private final int mExtraSpacing;
    private ImageResolver mImageResolver;
    private final int mImageRounding;
    private boolean mIsIsolated;
    private final int mIsolatedSize;
    private final int mMaxImageHeight;
    private final int mMinImageHeight;
    private final Path mPath = new Path();
    private final MessagingMessageState mState = new MessagingMessageState(this);

    public MessagingImageMessage(Context context) {
        this(context, null);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public MessagingImageMessage(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mMinImageHeight = context.getResources().getDimensionPixelSize(17105282);
        this.mMaxImageHeight = context.getResources().getDimensionPixelSize(17105281);
        this.mImageRounding = context.getResources().getDimensionPixelSize(17105283);
        this.mExtraSpacing = context.getResources().getDimensionPixelSize(17105280);
        this.setMaxHeight(this.mMaxImageHeight);
        this.mIsolatedSize = this.getResources().getDimensionPixelSize(17105278);
    }

    static MessagingMessage createMessage(MessagingLayout messagingLayout, Notification.MessagingStyle.Message message, ImageResolver imageResolver) {
        MessagingImageMessage messagingImageMessage;
        MessagingLinearLayout messagingLinearLayout = messagingLayout.getMessagingLinearLayout();
        MessagingImageMessage messagingImageMessage2 = messagingImageMessage = sInstancePool.acquire();
        if (messagingImageMessage == null) {
            messagingImageMessage2 = (MessagingImageMessage)LayoutInflater.from(messagingLayout.getContext()).inflate(17367207, (ViewGroup)messagingLinearLayout, false);
            messagingImageMessage2.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingImageMessage2.setImageResolver(imageResolver);
        if (!messagingImageMessage2.setMessage(message)) {
            messagingImageMessage2.recycle();
            return MessagingTextMessage.createMessage(messagingLayout, message);
        }
        return messagingImageMessage2;
    }

    public static void dropCache() {
        sInstancePool = new Pools.SynchronizedPool<MessagingImageMessage>(10);
    }

    private void setImageResolver(ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    public int getActualHeight() {
        return this.mActualHeight;
    }

    public int getActualWidth() {
        return this.mActualWidth;
    }

    @Override
    public int getConsumedLines() {
        return 3;
    }

    @Override
    public int getExtraSpacing() {
        return this.mExtraSpacing;
    }

    @Override
    public int getMeasuredType() {
        int n = this.getMeasuredHeight();
        int n2 = this.mIsIsolated ? this.mIsolatedSize : this.mMinImageHeight;
        if ((n2 = n < n2 && n != this.mDrawable.getIntrinsicHeight() ? 1 : 0) != 0) {
            return 2;
        }
        return !this.mIsIsolated && n != this.mDrawable.getIntrinsicHeight();
    }

    public Path getRoundedRectPath() {
        int n = this.getActualWidth();
        int n2 = this.getActualHeight();
        this.mPath.reset();
        int n3 = this.mImageRounding;
        float f = n3;
        float f2 = n3;
        f = Math.min((float)((n - 0) / 2), f);
        f2 = Math.min((float)((n2 - 0) / 2), f2);
        this.mPath.moveTo((float)false, (float)false + f2);
        this.mPath.quadTo((float)false, (float)false, (float)false + f, (float)false);
        this.mPath.lineTo((float)n - f, (float)false);
        this.mPath.quadTo(n, (float)false, n, (float)false + f2);
        this.mPath.lineTo(n, (float)n2 - f2);
        this.mPath.quadTo(n, n2, (float)n - f, n2);
        this.mPath.lineTo((float)false + f, n2);
        this.mPath.quadTo((float)false, n2, (float)false, (float)n2 - f2);
        this.mPath.close();
        return this.mPath;
    }

    @Override
    public MessagingMessageState getState() {
        return this.mState;
    }

    public int getStaticWidth() {
        if (this.mIsIsolated) {
            return this.getWidth();
        }
        return (int)((float)this.getHeight() * this.mAspectRatio);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.clipPath(this.getRoundedRectPath());
        int n = (int)Math.max((float)this.getActualWidth(), (float)this.getActualHeight() * this.mAspectRatio);
        int n2 = (int)((float)n / this.mAspectRatio);
        int n3 = (int)((float)(this.getActualWidth() - n) / 2.0f);
        this.mDrawable.setBounds(n3, 0, n3 + n, n2);
        this.mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.setActualWidth(this.getStaticWidth());
        this.setActualHeight(this.getHeight());
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mIsIsolated) {
            this.setMeasuredDimension(View.MeasureSpec.getSize(n), View.MeasureSpec.getSize(n2));
        }
    }

    @Override
    public void recycle() {
        MessagingMessage.super.recycle();
        this.setImageBitmap(null);
        this.mDrawable = null;
        sInstancePool.release(this);
    }

    public void setActualHeight(int n) {
        this.mActualHeight = n;
        this.invalidate();
    }

    public void setActualWidth(int n) {
        this.mActualWidth = n;
        this.invalidate();
    }

    public void setIsolated(boolean bl) {
        if (this.mIsIsolated != bl) {
            this.mIsIsolated = bl;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)this.getLayoutParams();
            int n = bl ? 0 : this.mExtraSpacing;
            marginLayoutParams.topMargin = n;
            this.setLayoutParams(marginLayoutParams);
        }
    }

    @Override
    public void setMaxDisplayedLines(int n) {
    }

    @Override
    public boolean setMessage(Notification.MessagingStyle.Message message) {
        Object object;
        block3 : {
            MessagingMessage.super.setMessage(message);
            try {
                object = message.getDataUri();
                object = this.mImageResolver != null ? this.mImageResolver.loadImage((Uri)object) : LocalImageResolver.resolveImage((Uri)object, this.getContext());
                if (object != null) break block3;
                return false;
            }
            catch (IOException | SecurityException exception) {
                exception.printStackTrace();
                return false;
            }
        }
        int n = ((Drawable)object).getIntrinsicHeight();
        if (n == 0) {
            Log.w(TAG, "Drawable with 0 intrinsic height was returned");
            return false;
        }
        this.mDrawable = object;
        this.mAspectRatio = (float)this.mDrawable.getIntrinsicWidth() / (float)n;
        this.setImageDrawable((Drawable)object);
        this.setContentDescription(message.getText());
        return true;
    }
}

