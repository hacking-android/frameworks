/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.widget.-$
 *  com.android.internal.widget.-$$Lambda
 *  com.android.internal.widget.-$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10
 */
package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.util.ContrastColorUtil;
import com.android.internal.widget.-$;
import com.android.internal.widget.ImageMessageConsumer;
import com.android.internal.widget.ImageResolver;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.MessagingMessage;
import com.android.internal.widget.MessagingPropertyAnimator;
import com.android.internal.widget._$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10;
import com.android.internal.widget._$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RemoteViews.RemoteView
public class MessagingLayout
extends FrameLayout
implements ImageMessageConsumer {
    private static final float COLOR_SHIFT_AMOUNT = 60.0f;
    public static final Interpolator FAST_OUT_LINEAR_IN;
    public static final Interpolator FAST_OUT_SLOW_IN;
    private static final Pattern IGNORABLE_CHAR_PATTERN;
    public static final Interpolator LINEAR_OUT_SLOW_IN;
    public static final View.OnLayoutChangeListener MESSAGING_PROPERTY_ANIMATOR;
    private static final Consumer<MessagingMessage> REMOVE_MESSAGE;
    private static final Pattern SPECIAL_CHAR_PATTERN;
    private ArrayList<MessagingGroup> mAddedGroups = new ArrayList();
    private Icon mAvatarReplacement;
    private int mAvatarSize;
    private CharSequence mConversationTitle;
    private boolean mDisplayImagesAtEnd;
    private ArrayList<MessagingGroup> mGroups = new ArrayList();
    private List<MessagingMessage> mHistoricMessages = new ArrayList<MessagingMessage>();
    private ImageResolver mImageResolver;
    private boolean mIsOneToOne;
    private int mLayoutColor;
    private int mMessageTextColor;
    private List<MessagingMessage> mMessages = new ArrayList<MessagingMessage>();
    private MessagingLinearLayout mMessagingLinearLayout;
    private CharSequence mNameReplacement;
    private Paint mPaint = new Paint(1);
    private int mSenderTextColor;
    private boolean mShowHistoricMessages;
    private Paint mTextPaint = new Paint();
    private TextView mTitleView;
    private Person mUser;

    static {
        IGNORABLE_CHAR_PATTERN = Pattern.compile("[\\p{C}\\p{Z}]");
        SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        REMOVE_MESSAGE = _$$Lambda$DKD2sNhLnyRFoBkFvfwKyxoEx10.INSTANCE;
        LINEAR_OUT_SLOW_IN = new PathInterpolator(0.0f, 0.0f, 0.2f, 1.0f);
        FAST_OUT_LINEAR_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
        FAST_OUT_SLOW_IN = new PathInterpolator(0.4f, 0.0f, 0.2f, 1.0f);
        MESSAGING_PROPERTY_ANIMATOR = new MessagingPropertyAnimator();
    }

    public MessagingLayout(Context context) {
        super(context);
    }

    public MessagingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MessagingLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public MessagingLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    private void addMessagesToGroups(List<MessagingMessage> list, List<MessagingMessage> list2, boolean bl) {
        ArrayList<List<MessagingMessage>> arrayList = new ArrayList<List<MessagingMessage>>();
        ArrayList<Person> arrayList2 = new ArrayList<Person>();
        this.findGroups(list, list2, arrayList, arrayList2);
        this.createGroupViews(arrayList, arrayList2, bl);
    }

    private void addRemoteInputHistoryToMessages(List<Notification.MessagingStyle.Message> list, CharSequence[] arrcharSequence) {
        if (arrcharSequence != null && arrcharSequence.length != 0) {
            for (int i = arrcharSequence.length - 1; i >= 0; --i) {
                list.add(new Notification.MessagingStyle.Message(arrcharSequence[i], 0L, null, true));
            }
            return;
        }
    }

    private void bind(List<Notification.MessagingStyle.Message> list, List<Notification.MessagingStyle.Message> list2, boolean bl) {
        list2 = this.createMessages(list2, true);
        list = this.createMessages(list, false);
        ArrayList<MessagingGroup> arrayList = new ArrayList<MessagingGroup>(this.mGroups);
        this.addMessagesToGroups(list2, list, bl);
        this.removeGroups(arrayList);
        this.mMessages.forEach(REMOVE_MESSAGE);
        this.mHistoricMessages.forEach(REMOVE_MESSAGE);
        this.mMessages = list;
        this.mHistoricMessages = list2;
        this.updateHistoricMessageVisibility();
        this.updateTitleAndNamesDisplay();
    }

    private void createGroupViews(List<List<MessagingMessage>> list, List<Person> list2, boolean bl) {
        this.mGroups.clear();
        for (int i = 0; i < list.size(); ++i) {
            List<MessagingMessage> list3 = list.get(i);
            Object object = null;
            int n = list3.size();
            boolean bl2 = true;
            --n;
            while (n >= 0 && (object = list3.get(n).getGroup()) == null) {
                --n;
            }
            MessagingGroup messagingGroup = object;
            if (object == null) {
                messagingGroup = MessagingGroup.createGroup(this.mMessagingLinearLayout);
                this.mAddedGroups.add(messagingGroup);
            }
            messagingGroup.setDisplayImagesAtEnd(this.mDisplayImagesAtEnd);
            messagingGroup.setLayoutColor(this.mLayoutColor);
            messagingGroup.setTextColors(this.mSenderTextColor, this.mMessageTextColor);
            Person person = list2.get(i);
            Object var11_11 = null;
            object = var11_11;
            if (person != this.mUser) {
                object = var11_11;
                if (this.mNameReplacement != null) {
                    object = this.mNameReplacement;
                }
            }
            messagingGroup.setSender(person, (CharSequence)object);
            if (i != list.size() - 1 || !bl) {
                bl2 = false;
            }
            messagingGroup.setSending(bl2);
            this.mGroups.add(messagingGroup);
            if (this.mMessagingLinearLayout.indexOfChild(messagingGroup) != i) {
                this.mMessagingLinearLayout.removeView(messagingGroup);
                this.mMessagingLinearLayout.addView((View)messagingGroup, i);
            }
            messagingGroup.setMessages(list3);
        }
    }

    private List<MessagingMessage> createMessages(List<Notification.MessagingStyle.Message> list, boolean bl) {
        ArrayList<MessagingMessage> arrayList = new ArrayList<MessagingMessage>();
        for (int i = 0; i < list.size(); ++i) {
            MessagingMessage messagingMessage;
            Notification.MessagingStyle.Message message = list.get(i);
            MessagingMessage messagingMessage2 = messagingMessage = this.findAndRemoveMatchingMessage(message);
            if (messagingMessage == null) {
                messagingMessage2 = MessagingMessage.createMessage(this, message, this.mImageResolver);
            }
            messagingMessage2.setIsHistoric(bl);
            arrayList.add(messagingMessage2);
        }
        return arrayList;
    }

    private MessagingMessage findAndRemoveMatchingMessage(Notification.MessagingStyle.Message message) {
        int n;
        MessagingMessage messagingMessage;
        for (n = 0; n < this.mMessages.size(); ++n) {
            messagingMessage = this.mMessages.get(n);
            if (!messagingMessage.sameAs(message)) continue;
            this.mMessages.remove(n);
            return messagingMessage;
        }
        for (n = 0; n < this.mHistoricMessages.size(); ++n) {
            messagingMessage = this.mHistoricMessages.get(n);
            if (!messagingMessage.sameAs(message)) continue;
            this.mHistoricMessages.remove(n);
            return messagingMessage;
        }
        return null;
    }

    private int findColor(CharSequence charSequence, int n) {
        double d = ContrastColorUtil.calculateLuminance(n);
        return ContrastColorUtil.getShiftedColor(n, (int)(60.0f * (float)((double)((float)((double)((float)(Math.abs(charSequence.hashCode()) % 5) / 4.0f - 0.5f) + Math.max(0.30000001192092896 - d, 0.0))) - Math.max(0.30000001192092896 - (1.0 - d), 0.0))));
    }

    private void findGroups(List<MessagingMessage> list, List<MessagingMessage> list2, List<List<MessagingMessage>> list3, List<Person> list4) {
        Person person = null;
        ArrayList<MessagingMessage> arrayList = null;
        int n = list.size();
        for (int i = 0; i < list2.size() + n; ++i) {
            MessagingMessage messagingMessage = i < n ? list.get(i) : list2.get(i - n);
            boolean bl = arrayList == null;
            Person person2 = messagingMessage.getMessage().getSenderPerson();
            CharSequence charSequence = person2 == null ? null : (person2.getKey() == null ? person2.getName() : person2.getKey());
            Object object = person;
            if (true ^ TextUtils.equals(charSequence, person) | bl) {
                arrayList = new ArrayList<MessagingMessage>();
                list3.add(arrayList);
                person = person2;
                if (person2 == null) {
                    person = this.mUser;
                }
                list4.add(person);
                object = charSequence;
            }
            arrayList.add(messagingMessage);
            person = object;
        }
    }

    private String findNameSplit(String charSequence) {
        String[] arrstring = ((String)charSequence).split(" ");
        if (arrstring.length > 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(Character.toString(arrstring[0].charAt(0)));
            ((StringBuilder)charSequence).append(Character.toString(arrstring[1].charAt(0)));
            return ((StringBuilder)charSequence).toString();
        }
        return ((String)charSequence).substring(0, 1);
    }

    private void removeGroups(ArrayList<MessagingGroup> arrayList) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            MessagingGroup messagingGroup = arrayList.get(i);
            if (this.mGroups.contains(messagingGroup)) continue;
            List<MessagingMessage> list = messagingGroup.getMessages();
            _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg = new _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg(this, messagingGroup);
            boolean bl = messagingGroup.isShown();
            this.mMessagingLinearLayout.removeView(messagingGroup);
            if (bl && !MessagingLinearLayout.isGone(messagingGroup)) {
                this.mMessagingLinearLayout.addTransientView(messagingGroup, 0);
                messagingGroup.removeGroupAnimated(_$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg);
            } else {
                _$$Lambda$MessagingLayout$AR_BLYGwVbm8HbmaOhECHwnOBBg.run();
            }
            this.mMessages.removeAll(list);
            this.mHistoricMessages.removeAll(list);
        }
    }

    private void updateHistoricMessageVisibility() {
        MessagingLinearLayout.MessagingChild messagingChild;
        int n;
        int n2 = this.mHistoricMessages.size();
        int n3 = 0;
        do {
            n = 0;
            if (n3 >= n2) break;
            messagingChild = this.mHistoricMessages.get(n3);
            if (!this.mShowHistoricMessages) {
                n = 8;
            }
            messagingChild.setVisibility(n);
            ++n3;
        } while (true);
        int n4 = this.mGroups.size();
        for (n3 = 0; n3 < n4; ++n3) {
            messagingChild = this.mGroups.get(n3);
            int n5 = 0;
            List<MessagingMessage> list = ((MessagingGroup)messagingChild).getMessages();
            int n6 = list.size();
            for (n = 0; n < n6; ++n) {
                n2 = n5;
                if (list.get(n).getVisibility() != 8) {
                    n2 = n5 + 1;
                }
                n5 = n2;
            }
            if (n5 > 0 && ((View)((Object)messagingChild)).getVisibility() == 8) {
                ((View)((Object)messagingChild)).setVisibility(0);
                continue;
            }
            if (n5 != 0 || ((View)((Object)messagingChild)).getVisibility() == 8) continue;
            ((View)((Object)messagingChild)).setVisibility(8);
        }
    }

    private void updateTitleAndNamesDisplay() {
        Object object;
        int n;
        Object object2;
        ArrayMap<Object, String> arrayMap = new ArrayMap<Object, String>();
        Object object3 = new ArrayMap();
        for (n = 0; n < this.mGroups.size(); ++n) {
            object = this.mGroups.get(n);
            object2 = ((MessagingGroup)object).getSenderName();
            if (!((MessagingGroup)object).needsGeneratedAvatar() || TextUtils.isEmpty((CharSequence)object2) || arrayMap.containsKey(object2)) continue;
            object = IGNORABLE_CHAR_PATTERN.matcher((CharSequence)object2).replaceAll("");
            char c = ((String)object).charAt(0);
            if (((ArrayMap)object3).containsKey(Character.valueOf(c))) {
                object = (CharSequence)((ArrayMap)object3).get(Character.valueOf(c));
                if (object != null) {
                    arrayMap.put(object, this.findNameSplit((String)object));
                    ((ArrayMap)object3).put(Character.valueOf(c), null);
                }
                arrayMap.put(object2, this.findNameSplit((String)object2));
                continue;
            }
            arrayMap.put(object2, Character.toString(c));
            ((ArrayMap)object3).put(Character.valueOf(c), object);
        }
        object = new ArrayMap();
        for (n = 0; n < this.mGroups.size(); ++n) {
            object3 = this.mGroups.get(n);
            boolean bl = ((MessagingGroup)object3).getSender() == this.mUser;
            object2 = ((MessagingGroup)object3).getSenderName();
            if (!((MessagingGroup)object3).needsGeneratedAvatar() || TextUtils.isEmpty((CharSequence)object2) || this.mIsOneToOne && this.mAvatarReplacement != null && !bl || (object3 = ((MessagingGroup)object3).getAvatarSymbolIfMatching((CharSequence)object2, (String)arrayMap.get(object2), this.mLayoutColor)) == null) continue;
            ((ArrayMap)object).put(object2, object3);
        }
        for (n = 0; n < this.mGroups.size(); ++n) {
            MessagingGroup messagingGroup = this.mGroups.get(n);
            CharSequence charSequence = messagingGroup.getSenderName();
            if (!messagingGroup.needsGeneratedAvatar() || TextUtils.isEmpty(charSequence)) continue;
            if (this.mIsOneToOne && this.mAvatarReplacement != null && messagingGroup.getSender() != this.mUser) {
                messagingGroup.setAvatar(this.mAvatarReplacement);
                continue;
            }
            object2 = object3 = (Icon)((ArrayMap)object).get(charSequence);
            if (object3 == null) {
                object2 = this.createAvatarSymbol(charSequence, (String)arrayMap.get(charSequence), this.mLayoutColor);
                ((ArrayMap)object).put(charSequence, object2);
            }
            messagingGroup.setCreatedAvatar((Icon)object2, charSequence, (String)arrayMap.get(charSequence), this.mLayoutColor);
        }
    }

    public Icon createAvatarSymbol(CharSequence object, String object2, int n) {
        if (!(((String)object2).isEmpty() || TextUtils.isDigitsOnly((CharSequence)object2) || SPECIAL_CHAR_PATTERN.matcher((CharSequence)object2).find())) {
            float f;
            float f2;
            int n2 = this.mAvatarSize;
            Bitmap bitmap = Bitmap.createBitmap(n2, n2, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            float f3 = (float)this.mAvatarSize / 2.0f;
            n = this.findColor((CharSequence)object, n);
            this.mPaint.setColor(n);
            canvas.drawCircle(f3, f3, f3, this.mPaint);
            n = ColorUtils.calculateLuminance(n) > 0.5 ? 1 : 0;
            object = this.mTextPaint;
            n = n != 0 ? -16777216 : -1;
            ((Paint)object).setColor(n);
            object = this.mTextPaint;
            if (((String)object2).length() == 1) {
                f2 = this.mAvatarSize;
                f = 0.5f;
            } else {
                f2 = this.mAvatarSize;
                f = 0.3f;
            }
            ((Paint)object).setTextSize(f2 * f);
            canvas.drawText((String)object2, f3, (int)(f3 - (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0f), this.mTextPaint);
            return Icon.createWithBitmap(bitmap);
        }
        object2 = Icon.createWithResource(this.getContext(), 17303022);
        ((Icon)object2).setTint(this.findColor((CharSequence)object, n));
        return object2;
    }

    public ArrayList<MessagingGroup> getMessagingGroups() {
        return this.mGroups;
    }

    public MessagingLinearLayout getMessagingLinearLayout() {
        return this.mMessagingLinearLayout;
    }

    public /* synthetic */ void lambda$removeGroups$0$MessagingLayout(MessagingGroup messagingGroup) {
        this.mMessagingLinearLayout.removeTransientView(messagingGroup);
        messagingGroup.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mMessagingLinearLayout = (MessagingLinearLayout)this.findViewById(16909179);
        this.mMessagingLinearLayout.setMessagingLayout(this);
        Object object = this.getResources().getDisplayMetrics();
        int n = Math.max(((DisplayMetrics)object).widthPixels, ((DisplayMetrics)object).heightPixels);
        object = new Rect(0, 0, n, n);
        this.mMessagingLinearLayout.setClipBounds((Rect)object);
        this.mTitleView = (TextView)this.findViewById(16908310);
        this.mAvatarSize = this.getResources().getDimensionPixelSize(17105278);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (!this.mAddedGroups.isEmpty()) {
            this.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){

                @Override
                public boolean onPreDraw() {
                    for (MessagingGroup messagingGroup : MessagingLayout.this.mAddedGroups) {
                        if (!messagingGroup.isShown()) continue;
                        MessagingPropertyAnimator.fadeIn(messagingGroup.getAvatar());
                        MessagingPropertyAnimator.fadeIn(messagingGroup.getSenderView());
                        MessagingPropertyAnimator.startLocalTranslationFrom(messagingGroup, messagingGroup.getHeight(), LINEAR_OUT_SLOW_IN);
                    }
                    MessagingLayout.this.mAddedGroups.clear();
                    MessagingLayout.this.getViewTreeObserver().removeOnPreDrawListener(this);
                    return true;
                }
            });
        }
    }

    @RemotableViewMethod
    public void setAvatarReplacement(Icon icon) {
        this.mAvatarReplacement = icon;
    }

    @RemotableViewMethod
    public void setData(Bundle bundle) {
        Object object = bundle.getParcelableArray("android.messages");
        object = Notification.MessagingStyle.Message.getMessagesFromBundleArray(object);
        Object object2 = bundle.getParcelableArray("android.messages.historic");
        object2 = Notification.MessagingStyle.Message.getMessagesFromBundleArray(object2);
        this.setUser((Person)bundle.getParcelable("android.messagingUser"));
        this.mConversationTitle = null;
        TextView textView = (TextView)this.findViewById(16908977);
        if (textView != null) {
            this.mConversationTitle = textView.getText();
        }
        this.addRemoteInputHistoryToMessages((List<Notification.MessagingStyle.Message>)object, bundle.getCharSequenceArray("android.remoteInputHistory"));
        this.bind((List<Notification.MessagingStyle.Message>)object, (List<Notification.MessagingStyle.Message>)object2, bundle.getBoolean("android.remoteInputSpinner", false));
    }

    @RemotableViewMethod
    public void setDisplayImagesAtEnd(boolean bl) {
        this.mDisplayImagesAtEnd = bl;
    }

    @Override
    public void setImageResolver(ImageResolver imageResolver) {
        this.mImageResolver = imageResolver;
    }

    @RemotableViewMethod
    public void setIsOneToOne(boolean bl) {
        this.mIsOneToOne = bl;
    }

    @RemotableViewMethod
    public void setLayoutColor(int n) {
        this.mLayoutColor = n;
    }

    @RemotableViewMethod
    public void setMessageTextColor(int n) {
        this.mMessageTextColor = n;
    }

    @RemotableViewMethod
    public void setNameReplacement(CharSequence charSequence) {
        this.mNameReplacement = charSequence;
    }

    @RemotableViewMethod
    public void setSenderTextColor(int n) {
        this.mSenderTextColor = n;
    }

    public void setUser(Person parcelable) {
        this.mUser = parcelable;
        if (this.mUser.getIcon() == null) {
            parcelable = Icon.createWithResource(this.getContext(), 17303022);
            ((Icon)parcelable).setTint(this.mLayoutColor);
            this.mUser = this.mUser.toBuilder().setIcon((Icon)parcelable).build();
        }
    }

    public void showHistoricMessages(boolean bl) {
        this.mShowHistoricMessages = bl;
        this.updateHistoricMessageVisibility();
    }

}

