/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.accessibility.-$
 *  android.view.accessibility.-$$Lambda
 *  android.view.accessibility.-$$Lambda$AccessibilityEvent
 *  android.view.accessibility.-$$Lambda$AccessibilityEvent$c6ikd5OkCnJv2aVsheVXIxBvSTk
 *  android.view.accessibility.-$$Lambda$AccessibilityEvent$gjyLj65KEDUo5PJZiVYxPrd2Vug
 */
package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Pools;
import android.view.accessibility.-$;
import android.view.accessibility.AccessibilityRecord;
import android.view.accessibility._$$Lambda$AccessibilityEvent$c6ikd5OkCnJv2aVsheVXIxBvSTk;
import android.view.accessibility._$$Lambda$AccessibilityEvent$gjyLj65KEDUo5PJZiVYxPrd2Vug;
import com.android.internal.util.BitUtils;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;

public final class AccessibilityEvent
extends AccessibilityRecord
implements Parcelable {
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_PANE_APPEARED = 16;
    public static final int CONTENT_CHANGE_TYPE_PANE_DISAPPEARED = 32;
    public static final int CONTENT_CHANGE_TYPE_PANE_TITLE = 8;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    public static final Parcelable.Creator<AccessibilityEvent> CREATOR;
    private static final boolean DEBUG = false;
    public static final boolean DEBUG_ORIGIN = false;
    public static final int INVALID_POSITION = -1;
    private static final int MAX_POOL_SIZE = 10;
    @Deprecated
    public static final int MAX_TEXT_LENGTH = 500;
    public static final int TYPES_ALL_MASK = -1;
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_ASSIST_READING_CONTEXT = 16777216;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_NOTIFICATION_STATE_CHANGED = 64;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_CLICKED = 1;
    public static final int TYPE_VIEW_CONTEXT_CLICKED = 8388608;
    public static final int TYPE_VIEW_FOCUSED = 8;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_LONG_CLICKED = 2;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_SELECTED = 4;
    public static final int TYPE_VIEW_TEXT_CHANGED = 16;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOWS_CHANGED = 4194304;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;
    public static final int TYPE_WINDOW_STATE_CHANGED = 32;
    public static final int WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED = 128;
    public static final int WINDOWS_CHANGE_ACTIVE = 32;
    public static final int WINDOWS_CHANGE_ADDED = 1;
    public static final int WINDOWS_CHANGE_BOUNDS = 8;
    public static final int WINDOWS_CHANGE_CHILDREN = 512;
    public static final int WINDOWS_CHANGE_FOCUSED = 64;
    public static final int WINDOWS_CHANGE_LAYER = 16;
    public static final int WINDOWS_CHANGE_PARENT = 256;
    public static final int WINDOWS_CHANGE_PIP = 1024;
    public static final int WINDOWS_CHANGE_REMOVED = 2;
    public static final int WINDOWS_CHANGE_TITLE = 4;
    private static final Pools.SynchronizedPool<AccessibilityEvent> sPool;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    int mAction;
    int mContentChangeTypes;
    private long mEventTime;
    @UnsupportedAppUsage
    private int mEventType;
    int mMovementGranularity;
    private CharSequence mPackageName;
    private ArrayList<AccessibilityRecord> mRecords;
    int mWindowChangeTypes;
    public StackTraceElement[] originStackTrace = null;

    static {
        sPool = new Pools.SynchronizedPool(10);
        CREATOR = new Parcelable.Creator<AccessibilityEvent>(){

            @Override
            public AccessibilityEvent createFromParcel(Parcel parcel) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
                accessibilityEvent.initFromParcel(parcel);
                return accessibilityEvent;
            }

            public AccessibilityEvent[] newArray(int n) {
                return new AccessibilityEvent[n];
            }
        };
    }

    private AccessibilityEvent() {
    }

    private static String contentChangeTypesToString(int n) {
        return BitUtils.flagsToString(n, (IntFunction<String>)_$$Lambda$AccessibilityEvent$gjyLj65KEDUo5PJZiVYxPrd2Vug.INSTANCE);
    }

    public static String eventTypeToString(int n) {
        if (n == -1) {
            return "TYPES_ALL_MASK";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        int n3 = n;
        n = n2;
        while (n3 != 0) {
            n2 = 1 << Integer.numberOfTrailingZeros(n3);
            n3 &= n2;
            if (n > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(AccessibilityEvent.singleEventTypeToString(n2));
            ++n;
        }
        if (n > 1) {
            stringBuilder.insert(0, '[');
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }

    public static /* synthetic */ String lambda$c6ikd5OkCnJv2aVsheVXIxBvSTk(int n) {
        return AccessibilityEvent.singleWindowChangeTypeToString(n);
    }

    public static /* synthetic */ String lambda$gjyLj65KEDUo5PJZiVYxPrd2Vug(int n) {
        return AccessibilityEvent.singleContentChangeTypeToString(n);
    }

    public static AccessibilityEvent obtain() {
        AccessibilityEvent accessibilityEvent;
        AccessibilityEvent accessibilityEvent2 = accessibilityEvent = sPool.acquire();
        if (accessibilityEvent == null) {
            accessibilityEvent2 = new AccessibilityEvent();
        }
        return accessibilityEvent2;
    }

    public static AccessibilityEvent obtain(int n) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
        accessibilityEvent.setEventType(n);
        return accessibilityEvent;
    }

    public static AccessibilityEvent obtain(AccessibilityEvent accessibilityEvent) {
        AccessibilityEvent accessibilityEvent2 = AccessibilityEvent.obtain();
        accessibilityEvent2.init(accessibilityEvent);
        Object object = accessibilityEvent.mRecords;
        if (object != null) {
            int n = ((ArrayList)object).size();
            accessibilityEvent2.mRecords = new ArrayList(n);
            for (int i = 0; i < n; ++i) {
                object = AccessibilityRecord.obtain(accessibilityEvent.mRecords.get(i));
                accessibilityEvent2.mRecords.add((AccessibilityRecord)object);
            }
        }
        return accessibilityEvent2;
    }

    public static AccessibilityEvent obtainWindowsChangedEvent(int n, int n2) {
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(4194304);
        accessibilityEvent.setWindowId(n);
        accessibilityEvent.setWindowChanges(n2);
        accessibilityEvent.setImportantForAccessibility(true);
        return accessibilityEvent;
    }

    private void readAccessibilityRecordFromParcel(AccessibilityRecord accessibilityRecord, Parcel parcel) {
        accessibilityRecord.mBooleanProperties = parcel.readInt();
        accessibilityRecord.mCurrentItemIndex = parcel.readInt();
        accessibilityRecord.mItemCount = parcel.readInt();
        accessibilityRecord.mFromIndex = parcel.readInt();
        accessibilityRecord.mToIndex = parcel.readInt();
        accessibilityRecord.mScrollX = parcel.readInt();
        accessibilityRecord.mScrollY = parcel.readInt();
        accessibilityRecord.mScrollDeltaX = parcel.readInt();
        accessibilityRecord.mScrollDeltaY = parcel.readInt();
        accessibilityRecord.mMaxScrollX = parcel.readInt();
        accessibilityRecord.mMaxScrollY = parcel.readInt();
        accessibilityRecord.mAddedCount = parcel.readInt();
        accessibilityRecord.mRemovedCount = parcel.readInt();
        accessibilityRecord.mClassName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mBeforeText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        accessibilityRecord.mParcelableData = parcel.readParcelable(null);
        parcel.readList(accessibilityRecord.mText, null);
        accessibilityRecord.mSourceWindowId = parcel.readInt();
        accessibilityRecord.mSourceNodeId = parcel.readLong();
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        accessibilityRecord.mSealed = bl;
    }

    private static String singleContentChangeTypeToString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        if (n != 8) {
                            if (n != 16) {
                                if (n != 32) {
                                    return Integer.toHexString(n);
                                }
                                return "CONTENT_CHANGE_TYPE_PANE_DISAPPEARED";
                            }
                            return "CONTENT_CHANGE_TYPE_PANE_APPEARED";
                        }
                        return "CONTENT_CHANGE_TYPE_PANE_TITLE";
                    }
                    return "CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION";
                }
                return "CONTENT_CHANGE_TYPE_TEXT";
            }
            return "CONTENT_CHANGE_TYPE_SUBTREE";
        }
        return "CONTENT_CHANGE_TYPE_UNDEFINED";
    }

    private static String singleEventTypeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                switch (n) {
                    default: {
                        return Integer.toHexString(n);
                    }
                    case 16777216: {
                        return "TYPE_ASSIST_READING_CONTEXT";
                    }
                    case 8388608: {
                        return "TYPE_VIEW_CONTEXT_CLICKED";
                    }
                    case 4194304: {
                        return "TYPE_WINDOWS_CHANGED";
                    }
                    case 2097152: {
                        return "TYPE_TOUCH_INTERACTION_END";
                    }
                    case 1048576: {
                        return "TYPE_TOUCH_INTERACTION_START";
                    }
                    case 524288: {
                        return "TYPE_GESTURE_DETECTION_END";
                    }
                    case 262144: {
                        return "TYPE_GESTURE_DETECTION_START";
                    }
                    case 131072: {
                        return "TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY";
                    }
                    case 65536: {
                        return "TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED";
                    }
                    case 32768: {
                        return "TYPE_VIEW_ACCESSIBILITY_FOCUSED";
                    }
                    case 16384: {
                        return "TYPE_ANNOUNCEMENT";
                    }
                    case 8192: {
                        return "TYPE_VIEW_TEXT_SELECTION_CHANGED";
                    }
                    case 4096: {
                        return "TYPE_VIEW_SCROLLED";
                    }
                    case 2048: {
                        return "TYPE_WINDOW_CONTENT_CHANGED";
                    }
                    case 1024: {
                        return "TYPE_TOUCH_EXPLORATION_GESTURE_END";
                    }
                    case 512: {
                        return "TYPE_TOUCH_EXPLORATION_GESTURE_START";
                    }
                    case 256: {
                        return "TYPE_VIEW_HOVER_EXIT";
                    }
                    case 128: {
                        return "TYPE_VIEW_HOVER_ENTER";
                    }
                    case 64: {
                        return "TYPE_NOTIFICATION_STATE_CHANGED";
                    }
                    case 32: {
                        return "TYPE_WINDOW_STATE_CHANGED";
                    }
                    case 16: {
                        return "TYPE_VIEW_TEXT_CHANGED";
                    }
                    case 8: {
                        return "TYPE_VIEW_FOCUSED";
                    }
                    case 4: 
                }
                return "TYPE_VIEW_SELECTED";
            }
            return "TYPE_VIEW_LONG_CLICKED";
        }
        return "TYPE_VIEW_CLICKED";
    }

    private static String singleWindowChangeTypeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                if (n != 1024) {
                                                    return Integer.toHexString(n);
                                                }
                                                return "WINDOWS_CHANGE_PIP";
                                            }
                                            return "WINDOWS_CHANGE_CHILDREN";
                                        }
                                        return "WINDOWS_CHANGE_PARENT";
                                    }
                                    return "WINDOWS_CHANGE_ACCESSIBILITY_FOCUSED";
                                }
                                return "WINDOWS_CHANGE_FOCUSED";
                            }
                            return "WINDOWS_CHANGE_ACTIVE";
                        }
                        return "WINDOWS_CHANGE_LAYER";
                    }
                    return "WINDOWS_CHANGE_BOUNDS";
                }
                return "WINDOWS_CHANGE_TITLE";
            }
            return "WINDOWS_CHANGE_REMOVED";
        }
        return "WINDOWS_CHANGE_ADDED";
    }

    private static String windowChangeTypesToString(int n) {
        return BitUtils.flagsToString(n, (IntFunction<String>)_$$Lambda$AccessibilityEvent$c6ikd5OkCnJv2aVsheVXIxBvSTk.INSTANCE);
    }

    private void writeAccessibilityRecordToParcel(AccessibilityRecord accessibilityRecord, Parcel parcel, int n) {
        parcel.writeInt(accessibilityRecord.mBooleanProperties);
        parcel.writeInt(accessibilityRecord.mCurrentItemIndex);
        parcel.writeInt(accessibilityRecord.mItemCount);
        parcel.writeInt(accessibilityRecord.mFromIndex);
        parcel.writeInt(accessibilityRecord.mToIndex);
        parcel.writeInt(accessibilityRecord.mScrollX);
        parcel.writeInt(accessibilityRecord.mScrollY);
        parcel.writeInt(accessibilityRecord.mScrollDeltaX);
        parcel.writeInt(accessibilityRecord.mScrollDeltaY);
        parcel.writeInt(accessibilityRecord.mMaxScrollX);
        parcel.writeInt(accessibilityRecord.mMaxScrollY);
        parcel.writeInt(accessibilityRecord.mAddedCount);
        parcel.writeInt(accessibilityRecord.mRemovedCount);
        TextUtils.writeToParcel(accessibilityRecord.mClassName, parcel, n);
        TextUtils.writeToParcel(accessibilityRecord.mContentDescription, parcel, n);
        TextUtils.writeToParcel(accessibilityRecord.mBeforeText, parcel, n);
        parcel.writeParcelable(accessibilityRecord.mParcelableData, n);
        parcel.writeList(accessibilityRecord.mText);
        parcel.writeInt(accessibilityRecord.mSourceWindowId);
        parcel.writeLong(accessibilityRecord.mSourceNodeId);
        parcel.writeInt((int)accessibilityRecord.mSealed);
    }

    public void appendRecord(AccessibilityRecord accessibilityRecord) {
        this.enforceNotSealed();
        if (this.mRecords == null) {
            this.mRecords = new ArrayList();
        }
        this.mRecords.add(accessibilityRecord);
    }

    @Override
    protected void clear() {
        super.clear();
        this.mEventType = 0;
        this.mMovementGranularity = 0;
        this.mAction = 0;
        this.mContentChangeTypes = 0;
        this.mWindowChangeTypes = 0;
        this.mPackageName = null;
        this.mEventTime = 0L;
        if (this.mRecords != null) {
            while (!this.mRecords.isEmpty()) {
                this.mRecords.remove(0).recycle();
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getAction() {
        return this.mAction;
    }

    public int getContentChangeTypes() {
        return this.mContentChangeTypes;
    }

    public long getEventTime() {
        return this.mEventTime;
    }

    public int getEventType() {
        return this.mEventType;
    }

    public int getMovementGranularity() {
        return this.mMovementGranularity;
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public AccessibilityRecord getRecord(int n) {
        Serializable serializable = this.mRecords;
        if (serializable != null) {
            return ((ArrayList)serializable).get(n);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Invalid index ");
        ((StringBuilder)serializable).append(n);
        ((StringBuilder)serializable).append(", size is 0");
        throw new IndexOutOfBoundsException(((StringBuilder)serializable).toString());
    }

    public int getRecordCount() {
        ArrayList<AccessibilityRecord> arrayList = this.mRecords;
        int n = arrayList == null ? 0 : arrayList.size();
        return n;
    }

    public int getWindowChanges() {
        return this.mWindowChangeTypes;
    }

    void init(AccessibilityEvent accessibilityEvent) {
        super.init(accessibilityEvent);
        this.mEventType = accessibilityEvent.mEventType;
        this.mMovementGranularity = accessibilityEvent.mMovementGranularity;
        this.mAction = accessibilityEvent.mAction;
        this.mContentChangeTypes = accessibilityEvent.mContentChangeTypes;
        this.mWindowChangeTypes = accessibilityEvent.mWindowChangeTypes;
        this.mEventTime = accessibilityEvent.mEventTime;
        this.mPackageName = accessibilityEvent.mPackageName;
    }

    public void initFromParcel(Parcel parcel) {
        int n = parcel.readInt();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        this.mSealed = bl;
        this.mEventType = parcel.readInt();
        this.mMovementGranularity = parcel.readInt();
        this.mAction = parcel.readInt();
        this.mContentChangeTypes = parcel.readInt();
        this.mWindowChangeTypes = parcel.readInt();
        this.mPackageName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mEventTime = parcel.readLong();
        this.mConnectionId = parcel.readInt();
        this.readAccessibilityRecordFromParcel(this, parcel);
        int n2 = parcel.readInt();
        if (n2 > 0) {
            this.mRecords = new ArrayList(n2);
            for (n = 0; n < n2; ++n) {
                AccessibilityRecord accessibilityRecord = AccessibilityRecord.obtain();
                this.readAccessibilityRecordFromParcel(accessibilityRecord, parcel);
                accessibilityRecord.mConnectionId = this.mConnectionId;
                this.mRecords.add(accessibilityRecord);
            }
        }
    }

    @Override
    public void recycle() {
        this.clear();
        sPool.release(this);
    }

    public void setAction(int n) {
        this.enforceNotSealed();
        this.mAction = n;
    }

    public void setContentChangeTypes(int n) {
        this.enforceNotSealed();
        this.mContentChangeTypes = n;
    }

    public void setEventTime(long l) {
        this.enforceNotSealed();
        this.mEventTime = l;
    }

    public void setEventType(int n) {
        this.enforceNotSealed();
        this.mEventType = n;
    }

    public void setMovementGranularity(int n) {
        this.enforceNotSealed();
        this.mMovementGranularity = n;
    }

    public void setPackageName(CharSequence charSequence) {
        this.enforceNotSealed();
        this.mPackageName = charSequence;
    }

    @Override
    public void setSealed(boolean bl) {
        super.setSealed(bl);
        ArrayList<AccessibilityRecord> arrayList = this.mRecords;
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                ((AccessibilityRecord)arrayList.get(i)).setSealed(bl);
            }
        }
    }

    public void setWindowChanges(int n) {
        this.mWindowChangeTypes = n;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EventType: ");
        stringBuilder.append(AccessibilityEvent.eventTypeToString(this.mEventType));
        stringBuilder.append("; EventTime: ");
        stringBuilder.append(this.mEventTime);
        stringBuilder.append("; PackageName: ");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append("; MovementGranularity: ");
        stringBuilder.append(this.mMovementGranularity);
        stringBuilder.append("; Action: ");
        stringBuilder.append(this.mAction);
        stringBuilder.append("; ContentChangeTypes: ");
        stringBuilder.append(AccessibilityEvent.contentChangeTypesToString(this.mContentChangeTypes));
        stringBuilder.append("; WindowChangeTypes: ");
        stringBuilder.append(AccessibilityEvent.windowChangeTypesToString(this.mWindowChangeTypes));
        super.appendTo(stringBuilder);
        stringBuilder.append("; recordCount: ");
        stringBuilder.append(this.getRecordCount());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt((int)this.isSealed());
        parcel.writeInt(this.mEventType);
        parcel.writeInt(this.mMovementGranularity);
        parcel.writeInt(this.mAction);
        parcel.writeInt(this.mContentChangeTypes);
        parcel.writeInt(this.mWindowChangeTypes);
        TextUtils.writeToParcel(this.mPackageName, parcel, 0);
        parcel.writeLong(this.mEventTime);
        parcel.writeInt(this.mConnectionId);
        this.writeAccessibilityRecordToParcel(this, parcel, n);
        int n2 = this.getRecordCount();
        parcel.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            this.writeAccessibilityRecordToParcel(this.mRecords.get(i), parcel, n);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ContentChangeTypes {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EventType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface WindowsChangeTypes {
    }

}

