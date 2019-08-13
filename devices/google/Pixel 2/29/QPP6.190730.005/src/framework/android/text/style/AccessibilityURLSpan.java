/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;

public class AccessibilityURLSpan
extends URLSpan
implements Parcelable {
    final AccessibilityClickableSpan mAccessibilityClickableSpan;

    public AccessibilityURLSpan(Parcel parcel) {
        super(parcel);
        this.mAccessibilityClickableSpan = new AccessibilityClickableSpan(parcel);
    }

    public AccessibilityURLSpan(URLSpan uRLSpan) {
        super(uRLSpan.getURL());
        this.mAccessibilityClickableSpan = new AccessibilityClickableSpan(uRLSpan.getId());
    }

    public void copyConnectionDataFrom(AccessibilityNodeInfo accessibilityNodeInfo) {
        this.mAccessibilityClickableSpan.copyConnectionDataFrom(accessibilityNodeInfo);
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 26;
    }

    @Override
    public void onClick(View view) {
        this.mAccessibilityClickableSpan.onClick(view);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        super.writeToParcelInternal(parcel, n);
        this.mAccessibilityClickableSpan.writeToParcel(parcel, n);
    }
}

