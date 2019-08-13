/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.app.Notification;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingPropertyAnimator;

public class MessagingMessageState {
    private MessagingGroup mGroup;
    private final View mHostView;
    private boolean mIsHidingAnimated;
    private boolean mIsHistoric;
    private Notification.MessagingStyle.Message mMessage;

    MessagingMessageState(View view) {
        this.mHostView = view;
    }

    public MessagingGroup getGroup() {
        return this.mGroup;
    }

    public View getHostView() {
        return this.mHostView;
    }

    public Notification.MessagingStyle.Message getMessage() {
        return this.mMessage;
    }

    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    public void recycle() {
        this.mHostView.setAlpha(1.0f);
        this.mHostView.setTranslationY(0.0f);
        MessagingPropertyAnimator.recycle(this.mHostView);
        this.mIsHidingAnimated = false;
        this.mIsHistoric = false;
        this.mGroup = null;
        this.mMessage = null;
    }

    public void setGroup(MessagingGroup messagingGroup) {
        this.mGroup = messagingGroup;
    }

    public void setIsHidingAnimated(boolean bl) {
        ViewParent viewParent = this.mHostView.getParent();
        this.mIsHidingAnimated = bl;
        this.mHostView.invalidate();
        if (viewParent instanceof ViewGroup) {
            ((ViewGroup)viewParent).invalidate();
        }
    }

    public void setIsHistoric(boolean bl) {
        this.mIsHistoric = bl;
    }

    public void setMessage(Notification.MessagingStyle.Message message) {
        this.mMessage = message;
    }
}

