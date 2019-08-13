/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.IBinder;
import android.util.Slog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import android.widget.TextView;

public class TooltipPopup {
    private static final String TAG = "TooltipPopup";
    private final View mContentView;
    private final Context mContext;
    private final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
    private final TextView mMessageView;
    private final int[] mTmpAnchorPos = new int[2];
    private final int[] mTmpAppPos = new int[2];
    private final Rect mTmpDisplayFrame = new Rect();

    public TooltipPopup(Context object) {
        this.mContext = object;
        this.mContentView = LayoutInflater.from(this.mContext).inflate(17367326, null);
        this.mMessageView = (TextView)this.mContentView.findViewById(16908299);
        this.mLayoutParams.setTitle(this.mContext.getString(17041146));
        this.mLayoutParams.packageName = this.mContext.getOpPackageName();
        object = this.mLayoutParams;
        ((WindowManager.LayoutParams)object).type = 1005;
        ((WindowManager.LayoutParams)object).width = -2;
        ((WindowManager.LayoutParams)object).height = -2;
        ((WindowManager.LayoutParams)object).format = -3;
        ((WindowManager.LayoutParams)object).windowAnimations = 16974600;
        ((WindowManager.LayoutParams)object).flags = 24;
    }

    private void computePosition(View arrn, int n, int n2, boolean bl, WindowManager.LayoutParams layoutParams) {
        int n3;
        layoutParams.token = arrn.getApplicationWindowToken();
        int n4 = this.mContext.getResources().getDimensionPixelOffset(17105483);
        if (arrn.getWidth() < n4) {
            n = arrn.getWidth() / 2;
        }
        if (arrn.getHeight() >= n4) {
            n4 = this.mContext.getResources().getDimensionPixelOffset(17105482);
            n3 = n2 + n4;
            n4 = n2 - n4;
            n2 = n3;
        } else {
            n2 = arrn.getHeight();
            n4 = 0;
        }
        layoutParams.gravity = 49;
        Object object = this.mContext.getResources();
        n3 = bl ? 17105486 : 17105485;
        n3 = ((Resources)object).getDimensionPixelOffset(n3);
        object = WindowManagerGlobal.getInstance().getWindowView(arrn.getApplicationWindowToken());
        if (object == null) {
            Slog.e(TAG, "Cannot find app view");
            return;
        }
        ((View)object).getWindowVisibleDisplayFrame(this.mTmpDisplayFrame);
        ((View)object).getLocationOnScreen(this.mTmpAppPos);
        arrn.getLocationOnScreen(this.mTmpAnchorPos);
        int[] arrn2 = this.mTmpAnchorPos;
        int n5 = arrn2[0];
        arrn = this.mTmpAppPos;
        arrn2[0] = n5 - arrn[0];
        arrn2[1] = arrn2[1] - arrn[1];
        layoutParams.x = arrn2[0] + n - ((View)object).getWidth() / 2;
        n = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.mContentView.measure(n, n);
        n = this.mContentView.getMeasuredHeight();
        arrn = this.mTmpAnchorPos;
        n4 = arrn[1] + n4 - n3 - n;
        n2 = arrn[1] + n2 + n3;
        layoutParams.y = bl ? (n4 >= 0 ? n4 : n2) : (n2 + n <= this.mTmpDisplayFrame.height() ? n2 : n4);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public void hide() {
        if (!this.isShowing()) {
            return;
        }
        ((WindowManager)this.mContext.getSystemService("window")).removeView(this.mContentView);
    }

    public boolean isShowing() {
        boolean bl = this.mContentView.getParent() != null;
        return bl;
    }

    public void show(View view, int n, int n2, boolean bl, CharSequence charSequence) {
        if (this.isShowing()) {
            this.hide();
        }
        this.mMessageView.setText(charSequence);
        this.computePosition(view, n, n2, bl, this.mLayoutParams);
        ((WindowManager)this.mContext.getSystemService("window")).addView(this.mContentView, this.mLayoutParams);
    }
}

