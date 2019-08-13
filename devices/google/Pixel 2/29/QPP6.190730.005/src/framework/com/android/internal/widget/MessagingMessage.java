/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.app.ActivityManager;
import android.app.Notification;
import android.net.Uri;
import android.view.View;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessageState;
import com.android.internal.widget.MessagingTextMessage;
import com.android.internal.widget._$$Lambda$MessagingMessage$goi5oiwdlMBbUvfJzNl7fGbZ_K0;
import java.util.Objects;

public interface MessagingMessage
extends MessagingLinearLayout.MessagingChild {
    public static final String IMAGE_MIME_TYPE_PREFIX = "image/";

    public static MessagingMessage createMessage(MessagingLayout messagingLayout, Notification.MessagingStyle.Message message, ImageResolver imageResolver) {
        if (MessagingMessage.hasImage(message) && !ActivityManager.isLowRamDeviceStatic()) {
            return MessagingImageMessage.createMessage(messagingLayout, message, imageResolver);
        }
        return MessagingTextMessage.createMessage(messagingLayout, message);
    }

    public static void dropCache() {
        MessagingTextMessage.dropCache();
        MessagingImageMessage.dropCache();
    }

    public static boolean hasImage(Notification.MessagingStyle.Message message) {
        boolean bl = message.getDataUri() != null && message.getDataMimeType() != null && message.getDataMimeType().startsWith(IMAGE_MIME_TYPE_PREFIX);
        return bl;
    }

    public static /* synthetic */ void lambda$hideAnimated$0(MessagingMessage messagingMessage) {
        messagingMessage.setIsHidingAnimated(false);
    }

    default public MessagingGroup getGroup() {
        return this.getState().getGroup();
    }

    default public Notification.MessagingStyle.Message getMessage() {
        return this.getState().getMessage();
    }

    public MessagingMessageState getState();

    default public View getView() {
        return (View)((Object)this);
    }

    public int getVisibility();

    default public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    default public void hideAnimated() {
        this.setIsHidingAnimated(true);
        this.getGroup().performRemoveAnimation(this.getView(), new _$$Lambda$MessagingMessage$goi5oiwdlMBbUvfJzNl7fGbZ_K0(this));
    }

    @Override
    default public boolean isHidingAnimated() {
        return this.getState().isHidingAnimated();
    }

    default public void recycle() {
        this.getState().recycle();
    }

    default public void removeMessage() {
        this.getGroup().removeMessage(this);
    }

    default public boolean sameAs(Notification.MessagingStyle.Message message) {
        Notification.MessagingStyle.Message message2 = this.getMessage();
        if (!Objects.equals(message.getText(), message2.getText())) {
            return false;
        }
        if (!Objects.equals(message.getSender(), message2.getSender())) {
            return false;
        }
        boolean bl = message.isRemoteInputHistory() != message2.isRemoteInputHistory();
        if (!bl && !Objects.equals(message.getTimestamp(), message2.getTimestamp())) {
            return false;
        }
        if (!Objects.equals(message.getDataMimeType(), message2.getDataMimeType())) {
            return false;
        }
        return Objects.equals(message.getDataUri(), message2.getDataUri());
    }

    default public boolean sameAs(MessagingMessage messagingMessage) {
        return this.sameAs(messagingMessage.getMessage());
    }

    default public void setColor(int n) {
    }

    default public void setIsHidingAnimated(boolean bl) {
        this.getState().setIsHidingAnimated(bl);
    }

    default public void setIsHistoric(boolean bl) {
        this.getState().setIsHistoric(bl);
    }

    default public boolean setMessage(Notification.MessagingStyle.Message message) {
        this.getState().setMessage(message);
        return true;
    }

    default public void setMessagingGroup(MessagingGroup messagingGroup) {
        this.getState().setGroup(messagingGroup);
    }

    public void setVisibility(int var1);
}

