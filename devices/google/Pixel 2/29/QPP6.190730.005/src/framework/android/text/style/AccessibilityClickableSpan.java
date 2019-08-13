/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityClickableSpan
extends ClickableSpan
implements ParcelableSpan {
    public static final Parcelable.Creator<AccessibilityClickableSpan> CREATOR = new Parcelable.Creator<AccessibilityClickableSpan>(){

        @Override
        public AccessibilityClickableSpan createFromParcel(Parcel parcel) {
            return new AccessibilityClickableSpan(parcel);
        }

        public AccessibilityClickableSpan[] newArray(int n) {
            return new AccessibilityClickableSpan[n];
        }
    };
    private int mConnectionId = -1;
    private final int mOriginalClickableSpanId;
    private long mSourceNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mWindowId = -1;

    public AccessibilityClickableSpan(int n) {
        this.mOriginalClickableSpanId = n;
    }

    public AccessibilityClickableSpan(Parcel parcel) {
        this.mOriginalClickableSpanId = parcel.readInt();
    }

    public void copyConnectionDataFrom(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mConnectionId = accessibilityNodeInfo.getConnectionId();
        this.mWindowId = accessibilityNodeInfo.getWindowId();
        this.mSourceNodeId = accessibilityNodeInfo.getSourceNodeId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public ClickableSpan findClickableSpan(CharSequence arrclickableSpan) {
        if (!(arrclickableSpan instanceof Spanned)) {
            return null;
        }
        arrclickableSpan = ((Spanned)arrclickableSpan).getSpans(0, arrclickableSpan.length(), ClickableSpan.class);
        for (int i = 0; i < arrclickableSpan.length; ++i) {
            if (arrclickableSpan[i].getId() != this.mOriginalClickableSpanId) continue;
            return arrclickableSpan[i];
        }
        return null;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 25;
    }

    @Override
    public void onClick(View object) {
        object = new Bundle();
        ((Bundle)object).putParcelable("android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN", this);
        if (this.mWindowId != -1 && this.mSourceNodeId != AccessibilityNodeInfo.UNDEFINED_NODE_ID && this.mConnectionId != -1) {
            AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, 16908658, (Bundle)object);
            return;
        }
        throw new RuntimeException("ClickableSpan for accessibility service not properly initialized");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        parcel.writeInt(this.mOriginalClickableSpanId);
    }

}

