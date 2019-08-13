/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pools;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import com.android.internal.widget.ImageFloatingTextView;
import com.android.internal.widget.MessagingImageMessage;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.internal.widget._$$Lambda$MessagingGroup$QKnXYzCylYJqF8wEQG98SXlcu2M;
import com.android.internal.widget._$$Lambda$MessagingGroup$buM2CBWR7uz4neT0lee_MKMDx5M;
import com.android.internal.widget._$$Lambda$MessagingGroup$uEKViIlAuE6AYNmbbTgLGe5mU7I;
import java.util.ArrayList;
import java.util.List;

@RemoteViews.RemoteView
public class MessagingGroup
extends LinearLayout
implements MessagingLinearLayout.MessagingChild {
    private static Pools.SimplePool<MessagingGroup> sInstancePool = new Pools.SynchronizedPool<MessagingGroup>(10);
    private ArrayList<MessagingMessage> mAddedMessages = new ArrayList();
    private Icon mAvatarIcon;
    private CharSequence mAvatarName = "";
    private String mAvatarSymbol = "";
    private ImageView mAvatarView;
    private Point mDisplaySize = new Point();
    private boolean mFirstLayout;
    private ViewGroup mImageContainer;
    private boolean mImagesAtEnd;
    private boolean mIsHidingAnimated;
    private MessagingImageMessage mIsolatedMessage;
    private int mLayoutColor;
    private MessagingLinearLayout mMessageContainer;
    private List<MessagingMessage> mMessages;
    private boolean mNeedsGeneratedAvatar;
    private Person mSender;
    private ImageFloatingTextView mSenderName;
    private ProgressBar mSendingSpinner;
    private View mSendingSpinnerContainer;
    private int mSendingTextColor;
    private int mTextColor;
    private boolean mTransformingImages;

    public MessagingGroup(Context context) {
        super(context);
    }

    public MessagingGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MessagingGroup(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public MessagingGroup(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private int calculateSendingTextColor() {
        TypedValue typedValue = new TypedValue();
        this.mContext.getResources().getValue(17105342, typedValue, true);
        float f = typedValue.getFloat();
        return Color.valueOf(Color.red(this.mTextColor), Color.green(this.mTextColor), Color.blue(this.mTextColor), f).toArgb();
    }

    static MessagingGroup createGroup(MessagingLinearLayout messagingLinearLayout) {
        MessagingGroup messagingGroup;
        MessagingGroup messagingGroup2 = messagingGroup = sInstancePool.acquire();
        if (messagingGroup == null) {
            messagingGroup2 = (MessagingGroup)LayoutInflater.from(messagingLinearLayout.getContext()).inflate(17367206, (ViewGroup)messagingLinearLayout, false);
            messagingGroup2.addOnLayoutChangeListener(MessagingLayout.MESSAGING_PROPERTY_ANIMATOR);
        }
        messagingLinearLayout.addView(messagingGroup2);
        return messagingGroup2;
    }

    public static void dropCache() {
        sInstancePool = new Pools.SynchronizedPool<MessagingGroup>(10);
    }

    private int getDistanceFromParent(View view, ViewGroup viewGroup) {
        int n = 0;
        while (view != viewGroup) {
            n = (int)((float)n + ((float)view.getTop() + view.getTranslationY()));
            view = (View)((Object)view.getParent());
        }
        return n;
    }

    static /* synthetic */ void lambda$removeMessage$0(ViewGroup viewGroup, View view, MessagingMessage messagingMessage) {
        viewGroup.removeTransientView(view);
        messagingMessage.recycle();
    }

    private void performRemoveAnimation(View view, int n, Runnable runnable) {
        MessagingPropertyAnimator.startLocalTranslationTo(view, n, MessagingLayout.FAST_OUT_LINEAR_IN);
        MessagingPropertyAnimator.fadeOut(view, runnable);
    }

    private boolean removeFromParentIfDifferent(MessagingMessage messagingMessage, ViewGroup viewGroup) {
        ViewParent viewParent = messagingMessage.getView().getParent();
        if (viewParent != viewGroup) {
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup)viewParent).removeView(messagingMessage.getView());
            }
            return true;
        }
        return false;
    }

    private void setIsHidingAnimated(boolean bl) {
        ViewParent viewParent = this.getParent();
        this.mIsHidingAnimated = bl;
        this.invalidate();
        if (viewParent instanceof ViewGroup) {
            ((ViewGroup)viewParent).invalidate();
        }
    }

    private void updateImageContainerVisibility() {
        ViewGroup viewGroup = this.mImageContainer;
        int n = this.mIsolatedMessage != null && this.mImagesAtEnd ? 0 : 8;
        viewGroup.setVisibility(n);
    }

    private void updateMessageColor() {
        if (this.mMessages != null) {
            int n = this.mSendingSpinnerContainer.getVisibility() == 0 ? this.mSendingTextColor : this.mTextColor;
            for (MessagingMessage messagingMessage : this.mMessages) {
                int n2 = messagingMessage.getMessage().isRemoteInputHistory() ? n : this.mTextColor;
                messagingMessage.setColor(n2);
            }
        }
    }

    public int calculateGroupCompatibility(MessagingGroup messagingGroup) {
        if (TextUtils.equals(this.getSenderName(), messagingGroup.getSenderName())) {
            int n = 1;
            for (int i = 0; i < this.mMessages.size() && i < messagingGroup.mMessages.size(); ++i) {
                List<MessagingMessage> list = this.mMessages;
                MessagingMessage messagingMessage = list.get(list.size() - 1 - i);
                list = messagingGroup.mMessages;
                if (!messagingMessage.sameAs(list.get(list.size() - 1 - i))) {
                    return n;
                }
                ++n;
            }
            return n;
        }
        return 0;
    }

    public View getAvatar() {
        return this.mAvatarView;
    }

    public Icon getAvatarSymbolIfMatching(CharSequence charSequence, String string2, int n) {
        if (this.mAvatarName.equals(charSequence) && this.mAvatarSymbol.equals(string2) && n == this.mLayoutColor) {
            return this.mAvatarIcon;
        }
        return null;
    }

    @Override
    public int getConsumedLines() {
        int n;
        block2 : {
            n = 0;
            for (int i = 0; i < this.mMessageContainer.getChildCount(); ++i) {
                View view = this.mMessageContainer.getChildAt(i);
                int n2 = n;
                if (view instanceof MessagingLinearLayout.MessagingChild) {
                    n2 = n + ((MessagingLinearLayout.MessagingChild)((Object)view)).getConsumedLines();
                }
                n = n2;
            }
            if (this.mIsolatedMessage == null) break block2;
            n = Math.max(n, 1);
        }
        return n + 1;
    }

    public MessagingImageMessage getIsolatedMessage() {
        return this.mIsolatedMessage;
    }

    @Override
    public int getMeasuredType() {
        if (this.mIsolatedMessage != null) {
            return 1;
        }
        boolean bl = false;
        int n = this.mMessageContainer.getChildCount() - 1;
        do {
            boolean bl2;
            boolean bl3 = false;
            if (n < 0) break;
            View view = this.mMessageContainer.getChildAt(n);
            if (view.getVisibility() == 8) {
                bl2 = bl;
            } else {
                bl2 = bl;
                if (view instanceof MessagingLinearLayout.MessagingChild) {
                    int n2 = ((MessagingLinearLayout.MessagingChild)((Object)view)).getMeasuredType();
                    bl2 = bl3;
                    if (n2 == 2) {
                        bl2 = true;
                    }
                    if (bl2 | ((MessagingLinearLayout.LayoutParams)view.getLayoutParams()).hide) {
                        if (bl) {
                            return 1;
                        }
                        return 2;
                    }
                    if (n2 == 1) {
                        return 1;
                    }
                    bl2 = true;
                }
            }
            --n;
            bl = bl2;
        } while (true);
        return 0;
    }

    public MessagingLinearLayout getMessageContainer() {
        return this.mMessageContainer;
    }

    public List<MessagingMessage> getMessages() {
        return this.mMessages;
    }

    public Person getSender() {
        return this.mSender;
    }

    public CharSequence getSenderName() {
        return this.mSenderName.getText();
    }

    public View getSenderView() {
        return this.mSenderName;
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override
    public void hideAnimated() {
        this.setIsHidingAnimated(true);
        this.removeGroupAnimated(new _$$Lambda$MessagingGroup$buM2CBWR7uz4neT0lee_MKMDx5M(this));
    }

    @Override
    public boolean isHidingAnimated() {
        return this.mIsHidingAnimated;
    }

    public /* synthetic */ void lambda$hideAnimated$2$MessagingGroup() {
        this.setIsHidingAnimated(false);
    }

    public /* synthetic */ void lambda$removeGroupAnimated$1$MessagingGroup(Runnable runnable) {
        this.setAlpha(1.0f);
        MessagingPropertyAnimator.setToLaidOutPosition(this);
        if (runnable != null) {
            runnable.run();
        }
    }

    public boolean needsGeneratedAvatar() {
        return this.mNeedsGeneratedAvatar;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessageContainer = (MessagingLinearLayout)this.findViewById(16908969);
        this.mSenderName = (ImageFloatingTextView)this.findViewById(16909106);
        this.mAvatarView = (ImageView)this.findViewById(16909105);
        this.mImageContainer = (ViewGroup)this.findViewById(16909108);
        this.mSendingSpinner = (ProgressBar)this.findViewById(16909109);
        this.mSendingSpinnerContainer = this.findViewById(16909110);
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        this.mDisplaySize.x = displayMetrics.widthPixels;
        this.mDisplaySize.y = displayMetrics.heightPixels;
    }

    @Override
    protected void onLayout(final boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (!this.mAddedMessages.isEmpty()) {
            bl = this.mFirstLayout;
            this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

                @Override
                public boolean onPreDraw() {
                    for (MessagingMessage messagingMessage : MessagingGroup.this.mAddedMessages) {
                        if (!messagingMessage.getView().isShown()) continue;
                        MessagingPropertyAnimator.fadeIn(messagingMessage.getView());
                        if (bl) continue;
                        MessagingPropertyAnimator.startLocalTranslationFrom(messagingMessage.getView(), messagingMessage.getView().getHeight(), MessagingLayout.LINEAR_OUT_SLOW_IN);
                    }
                    MessagingGroup.this.mAddedMessages.clear();
                    MessagingGroup.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
        this.mFirstLayout = false;
        this.updateClipRect();
    }

    public void performRemoveAnimation(View view, Runnable runnable) {
        this.performRemoveAnimation(view, -view.getHeight(), runnable);
    }

    public void recycle() {
        MessagingMessage messagingMessage = this.mIsolatedMessage;
        if (messagingMessage != null) {
            this.mImageContainer.removeView((View)((Object)messagingMessage));
        }
        for (int i = 0; i < this.mMessages.size(); ++i) {
            messagingMessage = this.mMessages.get(i);
            this.mMessageContainer.removeView(messagingMessage.getView());
            messagingMessage.recycle();
        }
        this.setAvatar(null);
        this.mAvatarView.setAlpha(1.0f);
        this.mAvatarView.setTranslationY(0.0f);
        this.mSenderName.setAlpha(1.0f);
        this.mSenderName.setTranslationY(0.0f);
        this.setAlpha(1.0f);
        this.mIsolatedMessage = null;
        this.mMessages = null;
        this.mAddedMessages.clear();
        this.mFirstLayout = true;
        MessagingPropertyAnimator.recycle(this);
        sInstancePool.release(this);
    }

    public void removeGroupAnimated(Runnable runnable) {
        this.performRemoveAnimation(this, new _$$Lambda$MessagingGroup$QKnXYzCylYJqF8wEQG98SXlcu2M(this, runnable));
    }

    public void removeMessage(MessagingMessage object) {
        View view = object.getView();
        boolean bl = view.isShown();
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        if (viewGroup == null) {
            return;
        }
        viewGroup.removeView(view);
        object = new _$$Lambda$MessagingGroup$uEKViIlAuE6AYNmbbTgLGe5mU7I(viewGroup, view, (MessagingMessage)object);
        if (bl && !MessagingLinearLayout.isGone(view)) {
            viewGroup.addTransientView(view, 0);
            this.performRemoveAnimation(view, (Runnable)object);
        } else {
            object.run();
        }
    }

    public void setAvatar(Icon icon) {
        this.mAvatarIcon = icon;
        this.mAvatarView.setImageIcon(icon);
        this.mAvatarSymbol = "";
        this.mAvatarName = "";
    }

    public void setCreatedAvatar(Icon icon, CharSequence charSequence, String string2, int n) {
        if (!this.mAvatarName.equals(charSequence) || !this.mAvatarSymbol.equals(string2) || n != this.mLayoutColor) {
            this.setAvatar(icon);
            this.mAvatarSymbol = string2;
            this.setLayoutColor(n);
            this.mAvatarName = charSequence;
        }
    }

    public void setDisplayImagesAtEnd(boolean bl) {
        if (this.mImagesAtEnd != bl) {
            this.mImagesAtEnd = bl;
            this.updateImageContainerVisibility();
        }
    }

    public void setLayoutColor(int n) {
        if (n != this.mLayoutColor) {
            this.mLayoutColor = n;
            this.mSendingSpinner.setIndeterminateTintList(ColorStateList.valueOf(this.mLayoutColor));
        }
    }

    @Override
    public void setMaxDisplayedLines(int n) {
        this.mMessageContainer.setMaxDisplayedLines(n);
    }

    public void setMessages(List<MessagingMessage> list) {
        int n = 0;
        MessagingImageMessage messagingImageMessage = null;
        for (int i = 0; i < list.size(); ++i) {
            MessagingMessage messagingMessage = list.get(i);
            if (messagingMessage.getGroup() != this) {
                messagingMessage.setMessagingGroup(this);
                this.mAddedMessages.add(messagingMessage);
            }
            boolean bl = messagingMessage instanceof MessagingImageMessage;
            if (this.mImagesAtEnd && bl) {
                messagingImageMessage = (MessagingImageMessage)messagingMessage;
                continue;
            }
            if (this.removeFromParentIfDifferent(messagingMessage, this.mMessageContainer)) {
                ViewGroup.LayoutParams layoutParams = messagingMessage.getView().getLayoutParams();
                if (layoutParams != null && !(layoutParams instanceof MessagingLinearLayout.LayoutParams)) {
                    messagingMessage.getView().setLayoutParams(this.mMessageContainer.generateDefaultLayoutParams());
                }
                this.mMessageContainer.addView(messagingMessage.getView(), n);
            }
            if (bl) {
                ((MessagingImageMessage)messagingMessage).setIsolated(false);
            }
            if (n != this.mMessageContainer.indexOfChild(messagingMessage.getView())) {
                this.mMessageContainer.removeView(messagingMessage.getView());
                this.mMessageContainer.addView(messagingMessage.getView(), n);
            }
            ++n;
        }
        if (messagingImageMessage != null) {
            if (this.removeFromParentIfDifferent(messagingImageMessage, this.mImageContainer)) {
                this.mImageContainer.removeAllViews();
                this.mImageContainer.addView(messagingImageMessage.getView());
            }
            messagingImageMessage.setIsolated(true);
        } else if (this.mIsolatedMessage != null) {
            this.mImageContainer.removeAllViews();
        }
        this.mIsolatedMessage = messagingImageMessage;
        this.updateImageContainerVisibility();
        this.mMessages = list;
        this.updateMessageColor();
    }

    public void setSender(Person object, CharSequence object2) {
        this.mSender = object;
        CharSequence charSequence = object2;
        if (object2 == null) {
            charSequence = ((Person)object).getName();
        }
        this.mSenderName.setText(charSequence);
        object2 = ((Person)object).getIcon();
        int n = 0;
        boolean bl = object2 == null;
        this.mNeedsGeneratedAvatar = bl;
        if (!this.mNeedsGeneratedAvatar) {
            this.setAvatar(((Person)object).getIcon());
        }
        this.mAvatarView.setVisibility(0);
        object = this.mSenderName;
        if (TextUtils.isEmpty(charSequence)) {
            n = 8;
        }
        ((View)object).setVisibility(n);
    }

    public void setSending(boolean bl) {
        int n = bl ? 0 : 8;
        if (this.mSendingSpinnerContainer.getVisibility() != n) {
            this.mSendingSpinnerContainer.setVisibility(n);
            this.updateMessageColor();
        }
    }

    public void setTextColors(int n, int n2) {
        this.mTextColor = n2;
        this.mSendingTextColor = this.calculateSendingTextColor();
        this.updateMessageColor();
        this.mSenderName.setTextColor(n);
    }

    public void setTransformingImages(boolean bl) {
        this.mTransformingImages = bl;
    }

    public void updateClipRect() {
        Object object;
        if (this.mSenderName.getVisibility() != 8 && !this.mTransformingImages) {
            object = (ViewGroup)this.mSenderName.getParent();
            int n = this.getDistanceFromParent(this.mSenderName, (ViewGroup)object);
            int n2 = this.getDistanceFromParent(this.mMessageContainer, (ViewGroup)object);
            int n3 = this.mSenderName.getHeight();
            int n4 = Math.max(this.mDisplaySize.x, this.mDisplaySize.y);
            object = new Rect(0, n - n2 + n3, n4, n4);
        } else {
            object = null;
        }
        this.mMessageContainer.setClipBounds((Rect)object);
    }

}

