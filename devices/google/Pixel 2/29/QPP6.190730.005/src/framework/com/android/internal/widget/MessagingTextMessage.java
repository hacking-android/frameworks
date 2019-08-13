/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.app.Notification;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Pools;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.MessagingMessageState;

@RemoteViews.RemoteView
public class MessagingTextMessage
extends ImageFloatingTextView
implements MessagingMessage {
    private static Pools.SimplePool<MessagingTextMessage> sInstancePool = new Pools.SynchronizedPool<MessagingTextMessage>(20);
    private final MessagingMessageState mState = new MessagingMessageState(this);

    public MessagingTextMessage(Context context) {
        super(context);
    }

    public MessagingTextMessage(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MessagingTextMessage(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public MessagingTextMessage(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    static MessagingMessage createMessage(MessagingLayout messagingLayout, Notification.MessagingStyle.Message message) {
        MessagingTextMessage messagingTextMessage;
        MessagingLinearLayout messagingLinearLayout = messagingLayout.getMessagingLinearLayout();
        MessagingTextMessage messagingTextMessage2 = messagingTextMessage = sInstancePool.acquire();
        if (messagingTextMessage == null) {
            messagingTextMessage2 = (MessagingTextMessage)LayoutInflater.from(messagingLayout.getContext()).inflate(17367208, (ViewGroup)messagingLinearLayout, false);
            messagingTextMessage2.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingTextMessage2.setMessage(message);
        return messagingTextMessage2;
    }

    public static void dropCache() {
        sInstancePool = new Pools.SynchronizedPool<MessagingTextMessage>(10);
    }

    @Override
    public int getConsumedLines() {
        return this.getLineCount();
    }

    public int getLayoutHeight() {
        Layout layout2 = this.getLayout();
        if (layout2 == null) {
            return 0;
        }
        return layout2.getHeight();
    }

    @Override
    public int getMeasuredType() {
        boolean bl = this.getMeasuredHeight() < this.getLayoutHeight() + this.getPaddingTop() + this.getPaddingBottom();
        if (bl && this.getLineCount() <= 1) {
            return 2;
        }
        Layout layout2 = this.getLayout();
        if (layout2 == null) {
            return 2;
        }
        return layout2.getEllipsisCount(layout2.getLineCount() - 1) > 0;
    }

    @Override
    public MessagingMessageState getState() {
        return this.mState;
    }

    @Override
    public void recycle() {
        MessagingMessage.super.recycle();
        sInstancePool.release(this);
    }

    @Override
    public void setColor(int n) {
        this.setTextColor(n);
    }

    @Override
    public void setMaxDisplayedLines(int n) {
        this.setMaxLines(n);
    }

    @Override
    public boolean setMessage(Notification.MessagingStyle.Message message) {
        MessagingMessage.super.setMessage(message);
        this.setText(message.getText());
        return true;
    }
}

