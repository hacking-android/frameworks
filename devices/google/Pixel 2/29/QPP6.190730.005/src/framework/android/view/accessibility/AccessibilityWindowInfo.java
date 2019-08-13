/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.LongArray;
import android.util.Pools;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class AccessibilityWindowInfo
implements Parcelable {
    public static final int ACTIVE_WINDOW_ID = Integer.MAX_VALUE;
    public static final int ANY_WINDOW_ID = -2;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 4;
    private static final int BOOLEAN_PROPERTY_ACTIVE = 1;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 2;
    private static final int BOOLEAN_PROPERTY_PICTURE_IN_PICTURE = 8;
    public static final Parcelable.Creator<AccessibilityWindowInfo> CREATOR;
    private static final boolean DEBUG = false;
    private static final int MAX_POOL_SIZE = 10;
    public static final int PICTURE_IN_PICTURE_ACTION_REPLACER_WINDOW_ID = -3;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SPLIT_SCREEN_DIVIDER = 5;
    public static final int TYPE_SYSTEM = 3;
    public static final int UNDEFINED_WINDOW_ID = -1;
    private static AtomicInteger sNumInstancesInUse;
    private static final Pools.SynchronizedPool<AccessibilityWindowInfo> sPool;
    private long mAnchorId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
    private int mBooleanProperties;
    private final Rect mBoundsInScreen = new Rect();
    private LongArray mChildIds;
    private int mConnectionId = -1;
    private int mId = -1;
    private int mLayer = -1;
    private int mParentId = -1;
    private CharSequence mTitle;
    private int mType = -1;

    static {
        sPool = new Pools.SynchronizedPool(10);
        CREATOR = new Parcelable.Creator<AccessibilityWindowInfo>(){

            @Override
            public AccessibilityWindowInfo createFromParcel(Parcel parcel) {
                AccessibilityWindowInfo accessibilityWindowInfo = AccessibilityWindowInfo.obtain();
                accessibilityWindowInfo.initFromParcel(parcel);
                return accessibilityWindowInfo;
            }

            public AccessibilityWindowInfo[] newArray(int n) {
                return new AccessibilityWindowInfo[n];
            }
        };
    }

    private AccessibilityWindowInfo() {
    }

    AccessibilityWindowInfo(AccessibilityWindowInfo accessibilityWindowInfo) {
        this.init(accessibilityWindowInfo);
    }

    private void clear() {
        this.mType = -1;
        this.mLayer = -1;
        this.mBooleanProperties = 0;
        this.mId = -1;
        this.mParentId = -1;
        this.mBoundsInScreen.setEmpty();
        LongArray longArray = this.mChildIds;
        if (longArray != null) {
            longArray.clear();
        }
        this.mConnectionId = -1;
        this.mAnchorId = AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        this.mTitle = null;
    }

    private boolean getBooleanProperty(int n) {
        boolean bl = (this.mBooleanProperties & n) != 0;
        return bl;
    }

    private void init(AccessibilityWindowInfo accessibilityWindowInfo) {
        this.mType = accessibilityWindowInfo.mType;
        this.mLayer = accessibilityWindowInfo.mLayer;
        this.mBooleanProperties = accessibilityWindowInfo.mBooleanProperties;
        this.mId = accessibilityWindowInfo.mId;
        this.mParentId = accessibilityWindowInfo.mParentId;
        this.mBoundsInScreen.set(accessibilityWindowInfo.mBoundsInScreen);
        this.mTitle = accessibilityWindowInfo.mTitle;
        this.mAnchorId = accessibilityWindowInfo.mAnchorId;
        LongArray longArray = accessibilityWindowInfo.mChildIds;
        if (longArray != null && longArray.size() > 0) {
            longArray = this.mChildIds;
            if (longArray == null) {
                this.mChildIds = accessibilityWindowInfo.mChildIds.clone();
            } else {
                longArray.addAll(accessibilityWindowInfo.mChildIds);
            }
        }
        this.mConnectionId = accessibilityWindowInfo.mConnectionId;
    }

    private void initFromParcel(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mLayer = parcel.readInt();
        this.mBooleanProperties = parcel.readInt();
        this.mId = parcel.readInt();
        this.mParentId = parcel.readInt();
        this.mBoundsInScreen.readFromParcel(parcel);
        this.mTitle = parcel.readCharSequence();
        this.mAnchorId = parcel.readLong();
        int n = parcel.readInt();
        if (n > 0) {
            if (this.mChildIds == null) {
                this.mChildIds = new LongArray(n);
            }
            for (int i = 0; i < n; ++i) {
                int n2 = parcel.readInt();
                this.mChildIds.add(n2);
            }
        }
        this.mConnectionId = parcel.readInt();
    }

    public static AccessibilityWindowInfo obtain() {
        Object object = sPool.acquire();
        AccessibilityWindowInfo accessibilityWindowInfo = object;
        if (object == null) {
            accessibilityWindowInfo = new AccessibilityWindowInfo();
        }
        if ((object = sNumInstancesInUse) != null) {
            ((AtomicInteger)object).incrementAndGet();
        }
        return accessibilityWindowInfo;
    }

    public static AccessibilityWindowInfo obtain(AccessibilityWindowInfo accessibilityWindowInfo) {
        AccessibilityWindowInfo accessibilityWindowInfo2 = AccessibilityWindowInfo.obtain();
        accessibilityWindowInfo2.init(accessibilityWindowInfo);
        return accessibilityWindowInfo2;
    }

    private void setBooleanProperty(int n, boolean bl) {
        this.mBooleanProperties = bl ? (this.mBooleanProperties |= n) : (this.mBooleanProperties &= n);
    }

    public static void setNumInstancesInUseCounter(AtomicInteger atomicInteger) {
        if (sNumInstancesInUse != null) {
            sNumInstancesInUse = atomicInteger;
        }
    }

    private static String typeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n != 5) {
                            return "<UNKNOWN>";
                        }
                        return "TYPE_SPLIT_SCREEN_DIVIDER";
                    }
                    return "TYPE_ACCESSIBILITY_OVERLAY";
                }
                return "TYPE_SYSTEM";
            }
            return "TYPE_INPUT_METHOD";
        }
        return "TYPE_APPLICATION";
    }

    public void addChild(int n) {
        if (this.mChildIds == null) {
            this.mChildIds = new LongArray();
        }
        this.mChildIds.add(n);
    }

    public boolean changed(AccessibilityWindowInfo accessibilityWindowInfo) {
        if (accessibilityWindowInfo.mId == this.mId) {
            if (accessibilityWindowInfo.mType == this.mType) {
                if (!this.mBoundsInScreen.equals(accessibilityWindowInfo.mBoundsInScreen)) {
                    return true;
                }
                if (this.mLayer != accessibilityWindowInfo.mLayer) {
                    return true;
                }
                if (this.mBooleanProperties != accessibilityWindowInfo.mBooleanProperties) {
                    return true;
                }
                if (this.mParentId != accessibilityWindowInfo.mParentId) {
                    return true;
                }
                LongArray longArray = this.mChildIds;
                return longArray == null ? accessibilityWindowInfo.mChildIds != null : !longArray.equals(accessibilityWindowInfo.mChildIds);
            }
            throw new IllegalArgumentException("Not same type.");
        }
        throw new IllegalArgumentException("Not same window.");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int differenceFrom(AccessibilityWindowInfo accessibilityWindowInfo) {
        if (accessibilityWindowInfo.mId == this.mId) {
            if (accessibilityWindowInfo.mType == this.mType) {
                int n = 0;
                if (!TextUtils.equals(this.mTitle, accessibilityWindowInfo.mTitle)) {
                    n = 0 | 4;
                }
                int n2 = n;
                if (!this.mBoundsInScreen.equals(accessibilityWindowInfo.mBoundsInScreen)) {
                    n2 = n | 8;
                }
                n = n2;
                if (this.mLayer != accessibilityWindowInfo.mLayer) {
                    n = n2 | 16;
                }
                n2 = n;
                if (this.getBooleanProperty(1) != accessibilityWindowInfo.getBooleanProperty(1)) {
                    n2 = n | 32;
                }
                n = n2;
                if (this.getBooleanProperty(2) != accessibilityWindowInfo.getBooleanProperty(2)) {
                    n = n2 | 64;
                }
                n2 = n;
                if (this.getBooleanProperty(4) != accessibilityWindowInfo.getBooleanProperty(4)) {
                    n2 = n | 128;
                }
                n = n2;
                if (this.getBooleanProperty(8) != accessibilityWindowInfo.getBooleanProperty(8)) {
                    n = n2 | 1024;
                }
                n2 = n;
                if (this.mParentId != accessibilityWindowInfo.mParentId) {
                    n2 = n | 256;
                }
                n = n2;
                if (!Objects.equals(this.mChildIds, accessibilityWindowInfo.mChildIds)) {
                    n = n2 | 512;
                }
                return n;
            }
            throw new IllegalArgumentException("Not same type.");
        }
        throw new IllegalArgumentException("Not same window.");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityWindowInfo)object;
        if (this.mId != ((AccessibilityWindowInfo)object).mId) {
            bl = false;
        }
        return bl;
    }

    public AccessibilityNodeInfo getAnchor() {
        if (this.mConnectionId != -1 && this.mAnchorId != AccessibilityNodeInfo.UNDEFINED_NODE_ID && this.mParentId != -1) {
            return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mParentId, this.mAnchorId, true, 0, null);
        }
        return null;
    }

    public void getBoundsInScreen(Rect rect) {
        rect.set(this.mBoundsInScreen);
    }

    public AccessibilityWindowInfo getChild(int n) {
        LongArray longArray = this.mChildIds;
        if (longArray != null) {
            if (this.mConnectionId == -1) {
                return null;
            }
            n = (int)longArray.get(n);
            return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, n);
        }
        throw new IndexOutOfBoundsException();
    }

    public int getChildCount() {
        LongArray longArray = this.mChildIds;
        int n = longArray != null ? longArray.size() : 0;
        return n;
    }

    public int getId() {
        return this.mId;
    }

    public int getLayer() {
        return this.mLayer;
    }

    public AccessibilityWindowInfo getParent() {
        if (this.mConnectionId != -1 && this.mParentId != -1) {
            return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mParentId);
        }
        return null;
    }

    public AccessibilityNodeInfo getRoot() {
        if (this.mConnectionId == -1) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mId, AccessibilityNodeInfo.ROOT_NODE_ID, true, 4, null);
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return this.mId;
    }

    public boolean isAccessibilityFocused() {
        return this.getBooleanProperty(4);
    }

    public boolean isActive() {
        return this.getBooleanProperty(1);
    }

    public boolean isFocused() {
        return this.getBooleanProperty(2);
    }

    public boolean isInPictureInPictureMode() {
        return this.getBooleanProperty(8);
    }

    public void recycle() {
        this.clear();
        sPool.release(this);
        AtomicInteger atomicInteger = sNumInstancesInUse;
        if (atomicInteger != null) {
            atomicInteger.decrementAndGet();
        }
    }

    public void setAccessibilityFocused(boolean bl) {
        this.setBooleanProperty(4, bl);
    }

    public void setActive(boolean bl) {
        this.setBooleanProperty(1, bl);
    }

    public void setAnchorId(long l) {
        this.mAnchorId = l;
    }

    public void setBoundsInScreen(Rect rect) {
        this.mBoundsInScreen.set(rect);
    }

    public void setConnectionId(int n) {
        this.mConnectionId = n;
    }

    public void setFocused(boolean bl) {
        this.setBooleanProperty(2, bl);
    }

    public void setId(int n) {
        this.mId = n;
    }

    public void setLayer(int n) {
        this.mLayer = n;
    }

    public void setParentId(int n) {
        this.mParentId = n;
    }

    public void setPictureInPicture(boolean bl) {
        this.setBooleanProperty(8, bl);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
    }

    public void setType(int n) {
        this.mType = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AccessibilityWindowInfo[");
        stringBuilder.append("title=");
        stringBuilder.append(this.mTitle);
        stringBuilder.append(", id=");
        stringBuilder.append(this.mId);
        stringBuilder.append(", type=");
        stringBuilder.append(AccessibilityWindowInfo.typeToString(this.mType));
        stringBuilder.append(", layer=");
        stringBuilder.append(this.mLayer);
        stringBuilder.append(", bounds=");
        stringBuilder.append(this.mBoundsInScreen);
        stringBuilder.append(", focused=");
        stringBuilder.append(this.isFocused());
        stringBuilder.append(", active=");
        stringBuilder.append(this.isActive());
        stringBuilder.append(", pictureInPicture=");
        stringBuilder.append(this.isInPictureInPictureMode());
        stringBuilder.append(", hasParent=");
        int n = this.mParentId;
        boolean bl = true;
        boolean bl2 = n != -1;
        stringBuilder.append(bl2);
        stringBuilder.append(", isAnchored=");
        bl2 = this.mAnchorId != AccessibilityNodeInfo.UNDEFINED_NODE_ID;
        stringBuilder.append(bl2);
        stringBuilder.append(", hasChildren=");
        LongArray longArray = this.mChildIds;
        bl2 = longArray != null && longArray.size() > 0 ? bl : false;
        stringBuilder.append(bl2);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mLayer);
        parcel.writeInt(this.mBooleanProperties);
        parcel.writeInt(this.mId);
        parcel.writeInt(this.mParentId);
        this.mBoundsInScreen.writeToParcel(parcel, n);
        parcel.writeCharSequence(this.mTitle);
        parcel.writeLong(this.mAnchorId);
        LongArray longArray = this.mChildIds;
        if (longArray == null) {
            parcel.writeInt(0);
        } else {
            int n2 = longArray.size();
            parcel.writeInt(n2);
            for (n = 0; n < n2; ++n) {
                parcel.writeInt((int)longArray.get(n));
            }
        }
        parcel.writeInt(this.mConnectionId);
    }

}

