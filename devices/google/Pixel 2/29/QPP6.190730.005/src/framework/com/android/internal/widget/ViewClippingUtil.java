/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.widget.MessagingPropertyAnimator;

public class ViewClippingUtil {
    private static final int CLIP_CHILDREN_TAG = 16908813;
    private static final int CLIP_CLIPPING_SET = 16908812;
    private static final int CLIP_TO_PADDING = 16908815;

    public static void setClippingDeactivated(View view, boolean bl, ClippingParameters clippingParameters) {
        if (!bl && !clippingParameters.isClippingEnablingAllowed(view)) {
            return;
        }
        if (!(view.getParent() instanceof ViewGroup)) {
            return;
        }
        ViewParent viewParent = (ViewGroup)view.getParent();
        do {
            Boolean bl2;
            if (!bl && !clippingParameters.isClippingEnablingAllowed(view)) {
                return;
            }
            Object object = (ArraySet<View>)((View)((Object)viewParent)).getTag(16908812);
            ArraySet<View> arraySet = object;
            if (object == null) {
                arraySet = new ArraySet<View>();
                ((View)((Object)viewParent)).setTagInternal(16908812, arraySet);
            }
            Boolean bl3 = (Boolean)((View)((Object)viewParent)).getTag(16908813);
            object = bl3;
            if (bl3 == null) {
                object = ((ViewGroup)viewParent).getClipChildren();
                ((View)((Object)viewParent)).setTagInternal(16908813, object);
            }
            bl3 = bl2 = (Boolean)((View)((Object)viewParent)).getTag(16908815);
            if (bl2 == null) {
                bl3 = ((ViewGroup)viewParent).getClipToPadding();
                ((View)((Object)viewParent)).setTagInternal(16908815, bl3);
            }
            if (!bl) {
                arraySet.remove(view);
                if (arraySet.isEmpty()) {
                    ((ViewGroup)viewParent).setClipChildren((Boolean)object);
                    ((ViewGroup)viewParent).setClipToPadding(bl3);
                    ((View)((Object)viewParent)).setTagInternal(16908812, null);
                    clippingParameters.onClippingStateChanged((View)((Object)viewParent), true);
                }
            } else {
                arraySet.add(view);
                ((ViewGroup)viewParent).setClipChildren(false);
                ((ViewGroup)viewParent).setClipToPadding(false);
                clippingParameters.onClippingStateChanged((View)((Object)viewParent), false);
            }
            if (clippingParameters.shouldFinish((View)((Object)viewParent))) {
                return;
            }
            if (!((viewParent = ((View)((Object)viewParent)).getParent()) instanceof ViewGroup)) break;
            viewParent = (ViewGroup)viewParent;
        } while (true);
    }

    public static interface ClippingParameters {
        default public boolean isClippingEnablingAllowed(View view) {
            return MessagingPropertyAnimator.isAnimatingTranslation(view) ^ true;
        }

        default public void onClippingStateChanged(View view, boolean bl) {
        }

        public boolean shouldFinish(View var1);
    }

}

