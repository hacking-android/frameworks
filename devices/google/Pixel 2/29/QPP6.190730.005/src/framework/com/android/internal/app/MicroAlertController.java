/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.android.internal.app.AlertController;

public class MicroAlertController
extends AlertController {
    public MicroAlertController(Context context, DialogInterface dialogInterface, Window window) {
        super(context, dialogInterface, window);
    }

    @Override
    protected void setupButtons(ViewGroup viewGroup) {
        super.setupButtons(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }

    @Override
    protected void setupContent(ViewGroup viewGroup) {
        this.mScrollView = (ScrollView)this.mWindow.findViewById(16909311);
        this.mMessageView = (TextView)viewGroup.findViewById(16908299);
        if (this.mMessageView == null) {
            return;
        }
        if (this.mMessage != null) {
            this.mMessageView.setText(this.mMessage);
        } else {
            this.mMessageView.setVisibility(8);
            viewGroup.removeView(this.mMessageView);
            if (this.mListView != null) {
                viewGroup = this.mScrollView.findViewById(16909482);
                ((ViewGroup)viewGroup.getParent()).removeView(viewGroup);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(viewGroup.getLayoutParams());
                layoutParams.gravity = 48;
                viewGroup.setLayoutParams(layoutParams);
                layoutParams = this.mScrollView.findViewById(16908778);
                ((ViewGroup)((View)((Object)layoutParams)).getParent()).removeView((View)((Object)layoutParams));
                Object object = new FrameLayout.LayoutParams(((View)((Object)layoutParams)).getLayoutParams());
                ((FrameLayout.LayoutParams)object).gravity = 80;
                ((View)((Object)layoutParams)).setLayoutParams((ViewGroup.LayoutParams)object);
                object = (ViewGroup)this.mScrollView.getParent();
                ((ViewGroup)object).removeViewAt(((ViewGroup)object).indexOfChild(this.mScrollView));
                ((ViewGroup)object).addView((View)this.mListView, new ViewGroup.LayoutParams(-1, -1));
                ((ViewGroup)object).addView(viewGroup);
                ((ViewGroup)object).addView((View)((Object)layoutParams));
            } else {
                viewGroup.setVisibility(8);
            }
        }
    }

    @Override
    protected void setupTitle(ViewGroup viewGroup) {
        super.setupTitle(viewGroup);
        if (viewGroup.getVisibility() == 8) {
            viewGroup.setVisibility(4);
        }
    }
}

