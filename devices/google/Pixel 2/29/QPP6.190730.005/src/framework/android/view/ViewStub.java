/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.ref.WeakReference;

@RemoteViews.RemoteView
public final class ViewStub
extends View {
    private OnInflateListener mInflateListener;
    private int mInflatedId;
    private WeakReference<View> mInflatedViewRef;
    private LayoutInflater mInflater;
    private int mLayoutResource;

    public ViewStub(Context context) {
        this(context, 0);
    }

    public ViewStub(Context context, int n) {
        this(context, null);
        this.mLayoutResource = n;
    }

    public ViewStub(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ViewStub(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ViewStub(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ViewStub, n, n2);
        this.saveAttributeDataForStyleable(context, R.styleable.ViewStub, attributeSet, typedArray, n, n2);
        this.mInflatedId = typedArray.getResourceId(2, -1);
        this.mLayoutResource = typedArray.getResourceId(1, 0);
        this.mID = typedArray.getResourceId(0, -1);
        typedArray.recycle();
        this.setVisibility(8);
        this.setWillNotDraw(true);
    }

    private View inflateViewNoAdd(ViewGroup view) {
        LayoutInflater layoutInflater = this.mInflater != null ? this.mInflater : LayoutInflater.from(this.mContext);
        view = layoutInflater.inflate(this.mLayoutResource, (ViewGroup)view, false);
        int n = this.mInflatedId;
        if (n != -1) {
            view.setId(n);
        }
        return view;
    }

    private void replaceSelfWithView(View view, ViewGroup viewGroup) {
        int n = viewGroup.indexOfChild(this);
        viewGroup.removeViewInLayout(this);
        ViewGroup.LayoutParams layoutParams = this.getLayoutParams();
        if (layoutParams != null) {
            viewGroup.addView(view, n, layoutParams);
        } else {
            viewGroup.addView(view, n);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override
    public void draw(Canvas canvas) {
    }

    public int getInflatedId() {
        return this.mInflatedId;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mInflater;
    }

    public int getLayoutResource() {
        return this.mLayoutResource;
    }

    public View inflate() {
        Object object = this.getParent();
        if (object != null && object instanceof ViewGroup) {
            if (this.mLayoutResource != 0) {
                Object object2 = (ViewGroup)object;
                object = this.inflateViewNoAdd((ViewGroup)object2);
                this.replaceSelfWithView((View)object, (ViewGroup)object2);
                this.mInflatedViewRef = new WeakReference<Object>(object);
                object2 = this.mInflateListener;
                if (object2 != null) {
                    object2.onInflate(this, (View)object);
                }
                return object;
            }
            throw new IllegalArgumentException("ViewStub must have a valid layoutResource");
        }
        throw new IllegalStateException("ViewStub must have a non-null ViewGroup viewParent");
    }

    @Override
    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(0, 0);
    }

    @RemotableViewMethod(asyncImpl="setInflatedIdAsync")
    public void setInflatedId(int n) {
        this.mInflatedId = n;
    }

    public Runnable setInflatedIdAsync(int n) {
        this.mInflatedId = n;
        return null;
    }

    public void setLayoutInflater(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    @RemotableViewMethod(asyncImpl="setLayoutResourceAsync")
    public void setLayoutResource(int n) {
        this.mLayoutResource = n;
    }

    public Runnable setLayoutResourceAsync(int n) {
        this.mLayoutResource = n;
        return null;
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @RemotableViewMethod(asyncImpl="setVisibilityAsync")
    @Override
    public void setVisibility(int n) {
        WeakReference<View> weakReference = this.mInflatedViewRef;
        if (weakReference != null) {
            if ((weakReference = (View)weakReference.get()) == null) throw new IllegalStateException("setVisibility called on un-referenced view");
            ((View)((Object)weakReference)).setVisibility(n);
            return;
        } else {
            super.setVisibility(n);
            if (n != 0 && n != 4) return;
            this.inflate();
        }
    }

    public Runnable setVisibilityAsync(int n) {
        if (n != 0 && n != 4) {
            return null;
        }
        return new ViewReplaceRunnable(this.inflateViewNoAdd((ViewGroup)this.getParent()));
    }

    public static interface OnInflateListener {
        public void onInflate(ViewStub var1, View var2);
    }

    public class ViewReplaceRunnable
    implements Runnable {
        public final View view;

        ViewReplaceRunnable(View view) {
            this.view = view;
        }

        @Override
        public void run() {
            ViewStub viewStub = ViewStub.this;
            viewStub.replaceSelfWithView(this.view, (ViewGroup)viewStub.getParent());
        }
    }

}

