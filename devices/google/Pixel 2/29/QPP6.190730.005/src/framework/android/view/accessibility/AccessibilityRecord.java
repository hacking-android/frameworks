/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AccessibilityRecord {
    protected static final boolean DEBUG_CONCISE_TOSTRING = false;
    private static final int GET_SOURCE_PREFETCH_FLAGS = 7;
    private static final int MAX_POOL_SIZE = 10;
    private static final int PROPERTY_CHECKED = 1;
    private static final int PROPERTY_ENABLED = 2;
    private static final int PROPERTY_FULL_SCREEN = 128;
    private static final int PROPERTY_IMPORTANT_FOR_ACCESSIBILITY = 512;
    private static final int PROPERTY_PASSWORD = 4;
    private static final int PROPERTY_SCROLLABLE = 256;
    private static final int UNDEFINED = -1;
    private static AccessibilityRecord sPool;
    private static final Object sPoolLock;
    private static int sPoolSize;
    int mAddedCount = -1;
    CharSequence mBeforeText;
    int mBooleanProperties = 0;
    CharSequence mClassName;
    int mConnectionId = -1;
    CharSequence mContentDescription;
    int mCurrentItemIndex = -1;
    int mFromIndex = -1;
    private boolean mIsInPool;
    int mItemCount = -1;
    int mMaxScrollX = -1;
    int mMaxScrollY = -1;
    private AccessibilityRecord mNext;
    Parcelable mParcelableData;
    int mRemovedCount = -1;
    int mScrollDeltaX = -1;
    int mScrollDeltaY = -1;
    int mScrollX = -1;
    int mScrollY = -1;
    @UnsupportedAppUsage
    boolean mSealed;
    @UnsupportedAppUsage
    long mSourceNodeId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    int mSourceWindowId = -1;
    final List<CharSequence> mText = new ArrayList<CharSequence>();
    int mToIndex = -1;

    static {
        sPoolLock = new Object();
    }

    AccessibilityRecord() {
    }

    private void append(StringBuilder stringBuilder, String string2, int n) {
        this.appendPropName(stringBuilder, string2).append(n);
    }

    private void append(StringBuilder stringBuilder, String string2, Object object) {
        this.appendPropName(stringBuilder, string2).append(object);
    }

    private StringBuilder appendPropName(StringBuilder stringBuilder, String string2) {
        stringBuilder.append("; ");
        stringBuilder.append(string2);
        stringBuilder.append(": ");
        return stringBuilder;
    }

    private void appendUnless(boolean bl, int n, StringBuilder stringBuilder) {
        bl = this.getBooleanProperty(n);
        this.appendPropName(stringBuilder, AccessibilityRecord.singleBooleanPropertyToString(n)).append(bl);
    }

    private boolean getBooleanProperty(int n) {
        boolean bl = (this.mBooleanProperties & n) == n;
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static AccessibilityRecord obtain() {
        Object object = sPoolLock;
        synchronized (object) {
            if (sPool == null) return new AccessibilityRecord();
            AccessibilityRecord accessibilityRecord = sPool;
            sPool = AccessibilityRecord.sPool.mNext;
            --sPoolSize;
            accessibilityRecord.mNext = null;
            accessibilityRecord.mIsInPool = false;
            return accessibilityRecord;
        }
    }

    public static AccessibilityRecord obtain(AccessibilityRecord accessibilityRecord) {
        AccessibilityRecord accessibilityRecord2 = AccessibilityRecord.obtain();
        accessibilityRecord2.init(accessibilityRecord);
        return accessibilityRecord2;
    }

    private void setBooleanProperty(int n, boolean bl) {
        this.mBooleanProperties = bl ? (this.mBooleanProperties |= n) : (this.mBooleanProperties &= n);
    }

    private static String singleBooleanPropertyToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 128) {
                        if (n != 256) {
                            if (n != 512) {
                                return Integer.toHexString(n);
                            }
                            return "ImportantForAccessibility";
                        }
                        return "Scrollable";
                    }
                    return "FullScreen";
                }
                return "Password";
            }
            return "Enabled";
        }
        return "Checked";
    }

    StringBuilder appendTo(StringBuilder stringBuilder) {
        stringBuilder.append(" [ ClassName: ");
        stringBuilder.append(this.mClassName);
        this.appendPropName(stringBuilder, "Text").append(this.mText);
        this.append(stringBuilder, "ContentDescription", this.mContentDescription);
        this.append(stringBuilder, "ItemCount", this.mItemCount);
        this.append(stringBuilder, "CurrentItemIndex", this.mCurrentItemIndex);
        this.appendUnless(true, 2, stringBuilder);
        this.appendUnless(false, 4, stringBuilder);
        this.appendUnless(false, 1, stringBuilder);
        this.appendUnless(false, 128, stringBuilder);
        this.appendUnless(false, 256, stringBuilder);
        this.append(stringBuilder, "BeforeText", this.mBeforeText);
        this.append(stringBuilder, "FromIndex", this.mFromIndex);
        this.append(stringBuilder, "ToIndex", this.mToIndex);
        this.append(stringBuilder, "ScrollX", this.mScrollX);
        this.append(stringBuilder, "ScrollY", this.mScrollY);
        this.append(stringBuilder, "MaxScrollX", this.mMaxScrollX);
        this.append(stringBuilder, "MaxScrollY", this.mMaxScrollY);
        this.append(stringBuilder, "AddedCount", this.mAddedCount);
        this.append(stringBuilder, "RemovedCount", this.mRemovedCount);
        this.append(stringBuilder, "ParcelableData", this.mParcelableData);
        stringBuilder.append(" ]");
        return stringBuilder;
    }

    void clear() {
        this.mSealed = false;
        this.mBooleanProperties = 0;
        this.mCurrentItemIndex = -1;
        this.mItemCount = -1;
        this.mFromIndex = -1;
        this.mToIndex = -1;
        this.mScrollX = -1;
        this.mScrollY = -1;
        this.mMaxScrollX = -1;
        this.mMaxScrollY = -1;
        this.mAddedCount = -1;
        this.mRemovedCount = -1;
        this.mClassName = null;
        this.mContentDescription = null;
        this.mBeforeText = null;
        this.mParcelableData = null;
        this.mText.clear();
        this.mSourceNodeId = Integer.MAX_VALUE;
        this.mSourceWindowId = -1;
        this.mConnectionId = -1;
    }

    void enforceNotSealed() {
        if (!this.isSealed()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this action on a sealed instance.");
    }

    void enforceSealed() {
        if (this.isSealed()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this action on a not sealed instance.");
    }

    public int getAddedCount() {
        return this.mAddedCount;
    }

    public CharSequence getBeforeText() {
        return this.mBeforeText;
    }

    public CharSequence getClassName() {
        return this.mClassName;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public int getCurrentItemIndex() {
        return this.mCurrentItemIndex;
    }

    public int getFromIndex() {
        return this.mFromIndex;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public int getMaxScrollX() {
        return this.mMaxScrollX;
    }

    public int getMaxScrollY() {
        return this.mMaxScrollY;
    }

    public Parcelable getParcelableData() {
        return this.mParcelableData;
    }

    public int getRemovedCount() {
        return this.mRemovedCount;
    }

    public int getScrollDeltaX() {
        return this.mScrollDeltaX;
    }

    public int getScrollDeltaY() {
        return this.mScrollDeltaY;
    }

    public int getScrollX() {
        return this.mScrollX;
    }

    public int getScrollY() {
        return this.mScrollY;
    }

    public AccessibilityNodeInfo getSource() {
        this.enforceSealed();
        if (this.mConnectionId != -1 && this.mSourceWindowId != -1 && AccessibilityNodeInfo.getAccessibilityViewId(this.mSourceNodeId) != Integer.MAX_VALUE) {
            return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mSourceWindowId, this.mSourceNodeId, false, 7, null);
        }
        return null;
    }

    @UnsupportedAppUsage
    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public List<CharSequence> getText() {
        return this.mText;
    }

    public int getToIndex() {
        return this.mToIndex;
    }

    public int getWindowId() {
        return this.mSourceWindowId;
    }

    void init(AccessibilityRecord accessibilityRecord) {
        this.mSealed = accessibilityRecord.mSealed;
        this.mBooleanProperties = accessibilityRecord.mBooleanProperties;
        this.mCurrentItemIndex = accessibilityRecord.mCurrentItemIndex;
        this.mItemCount = accessibilityRecord.mItemCount;
        this.mFromIndex = accessibilityRecord.mFromIndex;
        this.mToIndex = accessibilityRecord.mToIndex;
        this.mScrollX = accessibilityRecord.mScrollX;
        this.mScrollY = accessibilityRecord.mScrollY;
        this.mMaxScrollX = accessibilityRecord.mMaxScrollX;
        this.mMaxScrollY = accessibilityRecord.mMaxScrollY;
        this.mAddedCount = accessibilityRecord.mAddedCount;
        this.mRemovedCount = accessibilityRecord.mRemovedCount;
        this.mClassName = accessibilityRecord.mClassName;
        this.mContentDescription = accessibilityRecord.mContentDescription;
        this.mBeforeText = accessibilityRecord.mBeforeText;
        this.mParcelableData = accessibilityRecord.mParcelableData;
        this.mText.addAll(accessibilityRecord.mText);
        this.mSourceWindowId = accessibilityRecord.mSourceWindowId;
        this.mSourceNodeId = accessibilityRecord.mSourceNodeId;
        this.mConnectionId = accessibilityRecord.mConnectionId;
    }

    public boolean isChecked() {
        return this.getBooleanProperty(1);
    }

    public boolean isEnabled() {
        return this.getBooleanProperty(2);
    }

    public boolean isFullScreen() {
        return this.getBooleanProperty(128);
    }

    public boolean isImportantForAccessibility() {
        return this.getBooleanProperty(512);
    }

    public boolean isPassword() {
        return this.getBooleanProperty(4);
    }

    public boolean isScrollable() {
        return this.getBooleanProperty(256);
    }

    boolean isSealed() {
        return this.mSealed;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recycle() {
        if (this.mIsInPool) {
            throw new IllegalStateException("Record already recycled!");
        }
        this.clear();
        Object object = sPoolLock;
        synchronized (object) {
            if (sPoolSize <= 10) {
                this.mNext = sPool;
                sPool = this;
                this.mIsInPool = true;
                ++sPoolSize;
            }
            return;
        }
    }

    public void setAddedCount(int n) {
        this.enforceNotSealed();
        this.mAddedCount = n;
    }

    public void setBeforeText(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mBeforeText = charSequence;
    }

    public void setChecked(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(1, bl);
    }

    public void setClassName(CharSequence charSequence) {
        this.enforceNotSealed();
        this.mClassName = charSequence;
    }

    public void setConnectionId(int n) {
        this.enforceNotSealed();
        this.mConnectionId = n;
    }

    public void setContentDescription(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mContentDescription = charSequence;
    }

    public void setCurrentItemIndex(int n) {
        this.enforceNotSealed();
        this.mCurrentItemIndex = n;
    }

    public void setEnabled(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(2, bl);
    }

    public void setFromIndex(int n) {
        this.enforceNotSealed();
        this.mFromIndex = n;
    }

    public void setFullScreen(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(128, bl);
    }

    public void setImportantForAccessibility(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(512, bl);
    }

    public void setItemCount(int n) {
        this.enforceNotSealed();
        this.mItemCount = n;
    }

    public void setMaxScrollX(int n) {
        this.enforceNotSealed();
        this.mMaxScrollX = n;
    }

    public void setMaxScrollY(int n) {
        this.enforceNotSealed();
        this.mMaxScrollY = n;
    }

    public void setParcelableData(Parcelable parcelable) {
        this.enforceNotSealed();
        this.mParcelableData = parcelable;
    }

    public void setPassword(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(4, bl);
    }

    public void setRemovedCount(int n) {
        this.enforceNotSealed();
        this.mRemovedCount = n;
    }

    public void setScrollDeltaX(int n) {
        this.enforceNotSealed();
        this.mScrollDeltaX = n;
    }

    public void setScrollDeltaY(int n) {
        this.enforceNotSealed();
        this.mScrollDeltaY = n;
    }

    public void setScrollX(int n) {
        this.enforceNotSealed();
        this.mScrollX = n;
    }

    public void setScrollY(int n) {
        this.enforceNotSealed();
        this.mScrollY = n;
    }

    public void setScrollable(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(256, bl);
    }

    public void setSealed(boolean bl) {
        this.mSealed = bl;
    }

    public void setSource(View view) {
        this.setSource(view, -1);
    }

    public void setSource(View view, int n) {
        this.enforceNotSealed();
        boolean bl = true;
        int n2 = Integer.MAX_VALUE;
        this.mSourceWindowId = -1;
        if (view != null) {
            bl = view.isImportantForAccessibility();
            n2 = view.getAccessibilityViewId();
            this.mSourceWindowId = view.getAccessibilityWindowId();
        }
        this.setBooleanProperty(512, bl);
        this.mSourceNodeId = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setSourceNodeId(long l) {
        this.mSourceNodeId = l;
    }

    public void setToIndex(int n) {
        this.enforceNotSealed();
        this.mToIndex = n;
    }

    public void setWindowId(int n) {
        this.mSourceWindowId = n;
    }

    public String toString() {
        return this.appendTo(new StringBuilder()).toString();
    }
}

