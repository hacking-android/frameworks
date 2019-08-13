/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.Pools;
import android.view.View;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityWindowInfo;
import com.android.internal.util.BitUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class AccessibilityNodeInfo
implements Parcelable {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN = "android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN";
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int ACTION_TYPE_MASK = -16777216;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 1024;
    private static final int BOOLEAN_PROPERTY_CHECKABLE = 1;
    private static final int BOOLEAN_PROPERTY_CHECKED = 2;
    private static final int BOOLEAN_PROPERTY_CLICKABLE = 32;
    private static final int BOOLEAN_PROPERTY_CONTENT_INVALID = 65536;
    private static final int BOOLEAN_PROPERTY_CONTEXT_CLICKABLE = 131072;
    private static final int BOOLEAN_PROPERTY_DISMISSABLE = 16384;
    private static final int BOOLEAN_PROPERTY_EDITABLE = 4096;
    private static final int BOOLEAN_PROPERTY_ENABLED = 128;
    private static final int BOOLEAN_PROPERTY_FOCUSABLE = 4;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 8;
    private static final int BOOLEAN_PROPERTY_IMPORTANCE = 262144;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 1048576;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 4194304;
    private static final int BOOLEAN_PROPERTY_LONG_CLICKABLE = 64;
    private static final int BOOLEAN_PROPERTY_MULTI_LINE = 32768;
    private static final int BOOLEAN_PROPERTY_OPENS_POPUP = 8192;
    private static final int BOOLEAN_PROPERTY_PASSWORD = 256;
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 524288;
    private static final int BOOLEAN_PROPERTY_SCROLLABLE = 512;
    private static final int BOOLEAN_PROPERTY_SELECTED = 16;
    private static final int BOOLEAN_PROPERTY_VISIBLE_TO_USER = 2048;
    public static final Parcelable.Creator<AccessibilityNodeInfo> CREATOR;
    private static final boolean DEBUG = false;
    private static final AccessibilityNodeInfo DEFAULT;
    public static final String EXTRA_DATA_REQUESTED_KEY = "android.view.accessibility.AccessibilityNodeInfo.extra_data_requested";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 8;
    public static final int FLAG_PREFETCH_DESCENDANTS = 4;
    public static final int FLAG_PREFETCH_PREDECESSORS = 1;
    public static final int FLAG_PREFETCH_SIBLINGS = 2;
    public static final int FLAG_REPORT_VIEW_IDS = 16;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    public static final int LAST_LEGACY_STANDARD_ACTION = 2097152;
    private static final int MAX_POOL_SIZE = 50;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    public static final int ROOT_ITEM_ID = 2147483646;
    public static final long ROOT_NODE_ID;
    private static final String TAG = "AccessibilityNodeInfo";
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_ITEM_ID = Integer.MAX_VALUE;
    public static final long UNDEFINED_NODE_ID;
    public static final int UNDEFINED_SELECTION_INDEX = -1;
    private static final long VIRTUAL_DESCENDANT_ID_MASK = -4294967296L;
    private static final int VIRTUAL_DESCENDANT_ID_SHIFT = 32;
    private static AtomicInteger sNumInstancesInUse;
    private static final Pools.SynchronizedPool<AccessibilityNodeInfo> sPool;
    private ArrayList<AccessibilityAction> mActions;
    private int mBooleanProperties;
    private final Rect mBoundsInParent;
    private final Rect mBoundsInScreen;
    @UnsupportedAppUsage
    private LongArray mChildNodeIds;
    private CharSequence mClassName;
    private CollectionInfo mCollectionInfo;
    private CollectionItemInfo mCollectionItemInfo;
    private int mConnectionId;
    private CharSequence mContentDescription;
    private int mDrawingOrderInParent;
    private CharSequence mError;
    private ArrayList<String> mExtraDataKeys;
    private Bundle mExtras;
    private CharSequence mHintText;
    private int mInputType;
    private long mLabelForId;
    private long mLabeledById;
    private int mLiveRegion;
    private int mMaxTextLength;
    private int mMovementGranularities;
    private CharSequence mOriginalText;
    private CharSequence mPackageName;
    private CharSequence mPaneTitle;
    private long mParentNodeId;
    private RangeInfo mRangeInfo;
    @UnsupportedAppUsage
    private boolean mSealed;
    @UnsupportedAppUsage
    private long mSourceNodeId;
    private CharSequence mText;
    private int mTextSelectionEnd;
    private int mTextSelectionStart;
    private CharSequence mTooltipText;
    private TouchDelegateInfo mTouchDelegateInfo;
    private long mTraversalAfter;
    private long mTraversalBefore;
    private String mViewIdResourceName;
    private int mWindowId = -1;

    static {
        UNDEFINED_NODE_ID = AccessibilityNodeInfo.makeNodeId(Integer.MAX_VALUE, Integer.MAX_VALUE);
        ROOT_NODE_ID = AccessibilityNodeInfo.makeNodeId(2147483646, -1);
        sPool = new Pools.SynchronizedPool(50);
        DEFAULT = new AccessibilityNodeInfo();
        CREATOR = new Parcelable.Creator<AccessibilityNodeInfo>(){

            @Override
            public AccessibilityNodeInfo createFromParcel(Parcel parcel) {
                AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
                accessibilityNodeInfo.initFromParcel(parcel);
                return accessibilityNodeInfo;
            }

            public AccessibilityNodeInfo[] newArray(int n) {
                return new AccessibilityNodeInfo[n];
            }
        };
    }

    private AccessibilityNodeInfo() {
        long l;
        this.mSourceNodeId = l = UNDEFINED_NODE_ID;
        this.mParentNodeId = l;
        this.mLabelForId = l;
        this.mLabeledById = l;
        this.mTraversalBefore = l;
        this.mTraversalAfter = l;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
    }

    AccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        long l;
        this.mSourceNodeId = l = UNDEFINED_NODE_ID;
        this.mParentNodeId = l;
        this.mLabelForId = l;
        this.mLabeledById = l;
        this.mTraversalBefore = l;
        this.mTraversalAfter = l;
        this.mBoundsInParent = new Rect();
        this.mBoundsInScreen = new Rect();
        this.mMaxTextLength = -1;
        this.mTextSelectionStart = -1;
        this.mTextSelectionEnd = -1;
        this.mInputType = 0;
        this.mLiveRegion = 0;
        this.mConnectionId = -1;
        this.init(accessibilityNodeInfo);
    }

    private void addActionUnchecked(AccessibilityAction accessibilityAction) {
        if (accessibilityAction == null) {
            return;
        }
        if (this.mActions == null) {
            this.mActions = new ArrayList();
        }
        this.mActions.remove(accessibilityAction);
        this.mActions.add(accessibilityAction);
    }

    private void addChildInternal(View view, int n, boolean bl) {
        long l;
        int n2;
        this.enforceNotSealed();
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new LongArray();
        }
        if ((l = AccessibilityNodeInfo.makeNodeId(n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE, n)) == this.mSourceNodeId) {
            Log.e(TAG, "Rejecting attempt to make a View its own child");
            return;
        }
        if (bl && this.mChildNodeIds.indexOf(l) >= 0) {
            return;
        }
        this.mChildNodeIds.add(l);
    }

    private void addStandardActions(long l) {
        while (l > 0L) {
            long l2 = 1L << Long.numberOfTrailingZeros(l);
            l &= l2;
            this.addAction(AccessibilityNodeInfo.getActionSingletonBySerializationFlag(l2));
        }
    }

    private static boolean canPerformRequestOverConnection(int n, int n2, long l) {
        boolean bl = n2 != -1 && AccessibilityNodeInfo.getAccessibilityViewId(l) != Integer.MAX_VALUE && n != -1;
        return bl;
    }

    private void clear() {
        this.init(DEFAULT);
    }

    private void enforceValidFocusDirection(int n) {
        if (n != 1 && n != 2 && n != 17 && n != 33 && n != 66 && n != 130) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown direction: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    private void enforceValidFocusType(int n) {
        if (n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown focus type: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    @UnsupportedAppUsage
    public static int getAccessibilityViewId(long l) {
        return (int)l;
    }

    private static AccessibilityAction getActionSingleton(int n) {
        int n2 = AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < n2; ++i) {
            AccessibilityAction accessibilityAction = AccessibilityAction.sStandardActions.valueAt(i);
            if (n != accessibilityAction.getId()) continue;
            return accessibilityAction;
        }
        return null;
    }

    private static AccessibilityAction getActionSingletonBySerializationFlag(long l) {
        int n = AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < n; ++i) {
            AccessibilityAction accessibilityAction = AccessibilityAction.sStandardActions.valueAt(i);
            if (l != accessibilityAction.mSerializationFlag) continue;
            return accessibilityAction;
        }
        return null;
    }

    private static String getActionSymbolicName(int n) {
        if (n != 1) {
            if (n != 2) {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                switch (n) {
                                    default: {
                                        return "ACTION_UNKNOWN";
                                    }
                                    case 16908361: {
                                        return "ACTION_PAGE_RIGHT";
                                    }
                                    case 16908360: {
                                        return "ACTION_PAGE_LEFT";
                                    }
                                    case 16908359: {
                                        return "ACTION_PAGE_DOWN";
                                    }
                                    case 16908358: {
                                        return "ACTION_PAGE_UP";
                                    }
                                    case 16908357: {
                                        return "ACTION_HIDE_TOOLTIP";
                                    }
                                    case 16908356: 
                                }
                                return "ACTION_SHOW_TOOLTIP";
                            }
                            case 16908349: {
                                return "ACTION_SET_PROGRESS";
                            }
                            case 16908348: {
                                return "ACTION_CONTEXT_CLICK";
                            }
                            case 16908347: {
                                return "ACTION_SCROLL_RIGHT";
                            }
                            case 16908346: {
                                return "ACTION_SCROLL_DOWN";
                            }
                            case 16908345: {
                                return "ACTION_SCROLL_LEFT";
                            }
                            case 16908344: {
                                return "ACTION_SCROLL_UP";
                            }
                            case 16908343: {
                                return "ACTION_SCROLL_TO_POSITION";
                            }
                            case 16908342: 
                        }
                        return "ACTION_SHOW_ON_SCREEN";
                    }
                    case 2097152: {
                        return "ACTION_SET_TEXT";
                    }
                    case 1048576: {
                        return "ACTION_DISMISS";
                    }
                    case 524288: {
                        return "ACTION_COLLAPSE";
                    }
                    case 262144: {
                        return "ACTION_EXPAND";
                    }
                    case 131072: {
                        return "ACTION_SET_SELECTION";
                    }
                    case 65536: {
                        return "ACTION_CUT";
                    }
                    case 32768: {
                        return "ACTION_PASTE";
                    }
                    case 16384: {
                        return "ACTION_COPY";
                    }
                    case 8192: {
                        return "ACTION_SCROLL_BACKWARD";
                    }
                    case 4096: {
                        return "ACTION_SCROLL_FORWARD";
                    }
                    case 2048: {
                        return "ACTION_PREVIOUS_HTML_ELEMENT";
                    }
                    case 1024: {
                        return "ACTION_NEXT_HTML_ELEMENT";
                    }
                    case 512: {
                        return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
                    }
                    case 256: {
                        return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
                    }
                    case 128: {
                        return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
                    }
                    case 64: {
                        return "ACTION_ACCESSIBILITY_FOCUS";
                    }
                    case 32: {
                        return "ACTION_LONG_CLICK";
                    }
                    case 16: {
                        return "ACTION_CLICK";
                    }
                    case 8: {
                        return "ACTION_CLEAR_SELECTION";
                    }
                    case 4: 
                }
                return "ACTION_SELECT";
            }
            return "ACTION_CLEAR_FOCUS";
        }
        return "ACTION_FOCUS";
    }

    private boolean getBooleanProperty(int n) {
        boolean bl = (this.mBooleanProperties & n) != 0;
        return bl;
    }

    private static String getMovementGranularitySymbolicName(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n == 16) {
                            return "MOVEMENT_GRANULARITY_PAGE";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown movement granularity: ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return "MOVEMENT_GRANULARITY_PARAGRAPH";
                }
                return "MOVEMENT_GRANULARITY_LINE";
            }
            return "MOVEMENT_GRANULARITY_WORD";
        }
        return "MOVEMENT_GRANULARITY_CHARACTER";
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int n, int n2, long l) {
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(n, n2, l)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(n, n2, l, false, 7, null);
    }

    @UnsupportedAppUsage
    public static int getVirtualDescendantId(long l) {
        return (int)((-4294967296L & l) >> 32);
    }

    private static String idItemToString(int n) {
        if (n != -1) {
            switch (n) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("");
                    stringBuilder.append(n);
                    return stringBuilder.toString();
                }
                case Integer.MAX_VALUE: {
                    return "UNDEFINED";
                }
                case 2147483646: 
            }
            return "ROOT";
        }
        return "HOST";
    }

    public static String idToString(long l) {
        CharSequence charSequence;
        int n = AccessibilityNodeInfo.getAccessibilityViewId(l);
        int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
        if (n2 == -1) {
            charSequence = AccessibilityNodeInfo.idItemToString(n);
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(AccessibilityNodeInfo.idItemToString(n));
            ((StringBuilder)charSequence).append(":");
            ((StringBuilder)charSequence).append(AccessibilityNodeInfo.idItemToString(n2));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private void init(AccessibilityNodeInfo object) {
        Cloneable cloneable;
        this.mSealed = ((AccessibilityNodeInfo)object).mSealed;
        this.mSourceNodeId = ((AccessibilityNodeInfo)object).mSourceNodeId;
        this.mParentNodeId = ((AccessibilityNodeInfo)object).mParentNodeId;
        this.mLabelForId = ((AccessibilityNodeInfo)object).mLabelForId;
        this.mLabeledById = ((AccessibilityNodeInfo)object).mLabeledById;
        this.mTraversalBefore = ((AccessibilityNodeInfo)object).mTraversalBefore;
        this.mTraversalAfter = ((AccessibilityNodeInfo)object).mTraversalAfter;
        this.mWindowId = ((AccessibilityNodeInfo)object).mWindowId;
        this.mConnectionId = ((AccessibilityNodeInfo)object).mConnectionId;
        this.mBoundsInParent.set(((AccessibilityNodeInfo)object).mBoundsInParent);
        this.mBoundsInScreen.set(((AccessibilityNodeInfo)object).mBoundsInScreen);
        this.mPackageName = ((AccessibilityNodeInfo)object).mPackageName;
        this.mClassName = ((AccessibilityNodeInfo)object).mClassName;
        this.mText = ((AccessibilityNodeInfo)object).mText;
        this.mOriginalText = ((AccessibilityNodeInfo)object).mOriginalText;
        this.mHintText = ((AccessibilityNodeInfo)object).mHintText;
        this.mError = ((AccessibilityNodeInfo)object).mError;
        this.mContentDescription = ((AccessibilityNodeInfo)object).mContentDescription;
        this.mPaneTitle = ((AccessibilityNodeInfo)object).mPaneTitle;
        this.mTooltipText = ((AccessibilityNodeInfo)object).mTooltipText;
        this.mViewIdResourceName = ((AccessibilityNodeInfo)object).mViewIdResourceName;
        Object object2 = this.mActions;
        if (object2 != null) {
            ((ArrayList)object2).clear();
        }
        if ((cloneable = ((AccessibilityNodeInfo)object).mActions) != null && ((ArrayList)cloneable).size() > 0) {
            object2 = this.mActions;
            if (object2 == null) {
                this.mActions = new ArrayList<AccessibilityAction>((Collection<AccessibilityAction>)((Object)cloneable));
            } else {
                ((ArrayList)object2).addAll(((AccessibilityNodeInfo)object).mActions);
            }
        }
        this.mBooleanProperties = ((AccessibilityNodeInfo)object).mBooleanProperties;
        this.mMaxTextLength = ((AccessibilityNodeInfo)object).mMaxTextLength;
        this.mMovementGranularities = ((AccessibilityNodeInfo)object).mMovementGranularities;
        object2 = this.mChildNodeIds;
        if (object2 != null) {
            ((LongArray)object2).clear();
        }
        if ((cloneable = ((AccessibilityNodeInfo)object).mChildNodeIds) != null && ((LongArray)cloneable).size() > 0) {
            object2 = this.mChildNodeIds;
            if (object2 == null) {
                this.mChildNodeIds = ((LongArray)cloneable).clone();
            } else {
                ((LongArray)object2).addAll((LongArray)cloneable);
            }
        }
        this.mTextSelectionStart = ((AccessibilityNodeInfo)object).mTextSelectionStart;
        this.mTextSelectionEnd = ((AccessibilityNodeInfo)object).mTextSelectionEnd;
        this.mInputType = ((AccessibilityNodeInfo)object).mInputType;
        this.mLiveRegion = ((AccessibilityNodeInfo)object).mLiveRegion;
        this.mDrawingOrderInParent = ((AccessibilityNodeInfo)object).mDrawingOrderInParent;
        this.mExtraDataKeys = ((AccessibilityNodeInfo)object).mExtraDataKeys;
        object2 = ((AccessibilityNodeInfo)object).mExtras;
        cloneable = null;
        object2 = object2 != null ? new Bundle((Bundle)object2) : null;
        this.mExtras = object2;
        object2 = this.mRangeInfo;
        if (object2 != null) {
            ((RangeInfo)object2).recycle();
        }
        object2 = (object2 = ((AccessibilityNodeInfo)object).mRangeInfo) != null ? RangeInfo.obtain((RangeInfo)object2) : null;
        this.mRangeInfo = object2;
        object2 = this.mCollectionInfo;
        if (object2 != null) {
            ((CollectionInfo)object2).recycle();
        }
        object2 = (object2 = ((AccessibilityNodeInfo)object).mCollectionInfo) != null ? CollectionInfo.obtain((CollectionInfo)object2) : null;
        this.mCollectionInfo = object2;
        object2 = this.mCollectionItemInfo;
        if (object2 != null) {
            ((CollectionItemInfo)object2).recycle();
        }
        object2 = (object2 = ((AccessibilityNodeInfo)object).mCollectionItemInfo) != null ? CollectionItemInfo.obtain((CollectionItemInfo)object2) : null;
        this.mCollectionItemInfo = object2;
        object = ((AccessibilityNodeInfo)object).mTouchDelegateInfo;
        object = object != null ? new TouchDelegateInfo(((TouchDelegateInfo)object).mTargetMap, true) : cloneable;
        this.mTouchDelegateInfo = object;
    }

    private void initFromParcel(Parcel parcel) {
        int n;
        long l = parcel.readLong();
        int n2 = 0 + 1;
        boolean bl = BitUtils.isBitSet(l, 0) ? parcel.readInt() == 1 : AccessibilityNodeInfo.DEFAULT.mSealed;
        int n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mSourceNodeId = parcel.readLong();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mWindowId = parcel.readInt();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mParentNodeId = parcel.readLong();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mLabelForId = parcel.readLong();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mLabeledById = parcel.readLong();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mTraversalBefore = parcel.readLong();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mTraversalAfter = parcel.readLong();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mConnectionId = parcel.readInt();
        }
        n3 = n2 + 1;
        boolean bl2 = BitUtils.isBitSet(l, n2);
        Object var8_7 = null;
        if (bl2) {
            n = parcel.readInt();
            if (n <= 0) {
                this.mChildNodeIds = null;
            } else {
                this.mChildNodeIds = new LongArray(n);
                for (n2 = 0; n2 < n; ++n2) {
                    long l2 = parcel.readLong();
                    this.mChildNodeIds.add(l2);
                }
            }
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mBoundsInParent.top = parcel.readInt();
            this.mBoundsInParent.bottom = parcel.readInt();
            this.mBoundsInParent.left = parcel.readInt();
            this.mBoundsInParent.right = parcel.readInt();
        }
        n = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mBoundsInScreen.top = parcel.readInt();
            this.mBoundsInScreen.bottom = parcel.readInt();
            this.mBoundsInScreen.left = parcel.readInt();
            this.mBoundsInScreen.right = parcel.readInt();
        }
        n3 = n + 1;
        if (BitUtils.isBitSet(l, n)) {
            this.addStandardActions(parcel.readLong());
            n = parcel.readInt();
            for (n2 = 0; n2 < n; ++n2) {
                this.addActionUnchecked(new AccessibilityAction(parcel.readInt(), parcel.readCharSequence()));
            }
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mMaxTextLength = parcel.readInt();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mMovementGranularities = parcel.readInt();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mBooleanProperties = parcel.readInt();
        }
        n = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mPackageName = parcel.readCharSequence();
        }
        n3 = n + 1;
        if (BitUtils.isBitSet(l, n)) {
            this.mClassName = parcel.readCharSequence();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mText = parcel.readCharSequence();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mHintText = parcel.readCharSequence();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mError = parcel.readCharSequence();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mContentDescription = parcel.readCharSequence();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mPaneTitle = parcel.readCharSequence();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mTooltipText = parcel.readCharSequence();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mViewIdResourceName = parcel.readString();
        }
        n = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mTextSelectionStart = parcel.readInt();
        }
        n3 = n + 1;
        if (BitUtils.isBitSet(l, n)) {
            this.mTextSelectionEnd = parcel.readInt();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mInputType = parcel.readInt();
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            this.mLiveRegion = parcel.readInt();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            this.mDrawingOrderInParent = parcel.readInt();
        }
        n3 = n2 + 1;
        Object object = BitUtils.isBitSet(l, n2) ? parcel.createStringArrayList() : null;
        this.mExtraDataKeys = object;
        n2 = n3 + 1;
        object = BitUtils.isBitSet(l, n3) ? parcel.readBundle() : null;
        this.mExtras = object;
        object = this.mRangeInfo;
        if (object != null) {
            ((RangeInfo)object).recycle();
        }
        n = n2 + 1;
        object = BitUtils.isBitSet(l, n2) ? RangeInfo.obtain(parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat()) : null;
        this.mRangeInfo = object;
        object = this.mCollectionInfo;
        if (object != null) {
            ((CollectionInfo)object).recycle();
        }
        n3 = n + 1;
        if (BitUtils.isBitSet(l, n)) {
            n = parcel.readInt();
            n2 = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            object = CollectionInfo.obtain(n, n2, bl2, parcel.readInt());
        } else {
            object = null;
        }
        this.mCollectionInfo = object;
        object = this.mCollectionItemInfo;
        if (object != null) {
            ((CollectionItemInfo)object).recycle();
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            int n4 = parcel.readInt();
            n = parcel.readInt();
            int n5 = parcel.readInt();
            n3 = parcel.readInt();
            bl2 = parcel.readInt() == 1;
            boolean bl3 = parcel.readInt() == 1;
            object = CollectionItemInfo.obtain(n4, n, n5, n3, bl2, bl3);
        } else {
            object = var8_7;
        }
        this.mCollectionItemInfo = object;
        if (BitUtils.isBitSet(l, n2)) {
            this.mTouchDelegateInfo = TouchDelegateInfo.CREATOR.createFromParcel(parcel);
        }
        this.mSealed = bl;
    }

    private static boolean isDefaultStandardAction(AccessibilityAction accessibilityAction) {
        boolean bl = accessibilityAction.mSerializationFlag != -1L && TextUtils.isEmpty(accessibilityAction.getLabel());
        return bl;
    }

    public static long makeNodeId(int n, int n2) {
        return (long)n2 << 32 | (long)n;
    }

    public static AccessibilityNodeInfo obtain() {
        AccessibilityNodeInfo accessibilityNodeInfo = sPool.acquire();
        AtomicInteger atomicInteger = sNumInstancesInUse;
        if (atomicInteger != null) {
            atomicInteger.incrementAndGet();
        }
        if (accessibilityNodeInfo == null) {
            accessibilityNodeInfo = new AccessibilityNodeInfo();
        }
        return accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfo obtain(View view) {
        AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
        accessibilityNodeInfo.setSource(view);
        return accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfo obtain(View view, int n) {
        AccessibilityNodeInfo accessibilityNodeInfo = AccessibilityNodeInfo.obtain();
        accessibilityNodeInfo.setSource(view, n);
        return accessibilityNodeInfo;
    }

    public static AccessibilityNodeInfo obtain(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfo2 = AccessibilityNodeInfo.obtain();
        accessibilityNodeInfo2.init(accessibilityNodeInfo);
        return accessibilityNodeInfo2;
    }

    private void setBooleanProperty(int n, boolean bl) {
        this.enforceNotSealed();
        this.mBooleanProperties = bl ? (this.mBooleanProperties |= n) : (this.mBooleanProperties &= n);
    }

    public static void setNumInstancesInUseCounter(AtomicInteger atomicInteger) {
        sNumInstancesInUse = atomicInteger;
    }

    @Deprecated
    public void addAction(int n) {
        this.enforceNotSealed();
        if ((-16777216 & n) == 0) {
            this.addStandardActions(n);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Action is not a combination of the standard actions: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void addAction(AccessibilityAction accessibilityAction) {
        this.enforceNotSealed();
        this.addActionUnchecked(accessibilityAction);
    }

    public void addChild(View view) {
        this.addChildInternal(view, -1, true);
    }

    public void addChild(View view, int n) {
        this.addChildInternal(view, n, true);
    }

    public void addChildUnchecked(View view) {
        this.addChildInternal(view, -1, false);
    }

    public boolean canOpenPopup() {
        return this.getBooleanProperty(8192);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected void enforceNotSealed() {
        if (!this.isSealed()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this action on a sealed instance.");
    }

    protected void enforceSealed() {
        if (this.isSealed()) {
            return;
        }
        throw new IllegalStateException("Cannot perform this action on a not sealed instance.");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (AccessibilityNodeInfo)object;
        if (this.mSourceNodeId != ((AccessibilityNodeInfo)object).mSourceNodeId) {
            return false;
        }
        return this.mWindowId == ((AccessibilityNodeInfo)object).mWindowId;
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2) {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.emptyList();
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByText(this.mConnectionId, this.mWindowId, this.mSourceNodeId, string2);
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(String string2) {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.emptyList();
        }
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfosByViewId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, string2);
    }

    public AccessibilityNodeInfo findFocus(int n) {
        this.enforceSealed();
        this.enforceValidFocusType(n);
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, this.mWindowId, this.mSourceNodeId, n);
    }

    public AccessibilityNodeInfo focusSearch(int n) {
        this.enforceSealed();
        this.enforceValidFocusDirection(n);
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().focusSearch(this.mConnectionId, this.mWindowId, this.mSourceNodeId, n);
    }

    public List<AccessibilityAction> getActionList() {
        return CollectionUtils.emptyIfNull(this.mActions);
    }

    @Deprecated
    public int getActions() {
        int n = 0;
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList == null) {
            return 0;
        }
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            int n3 = this.mActions.get(i).getId();
            int n4 = n;
            if (n3 <= 2097152) {
                n4 = n | n3;
            }
            n = n4;
        }
        return n;
    }

    public List<String> getAvailableExtraData() {
        ArrayList<String> arrayList = this.mExtraDataKeys;
        if (arrayList != null) {
            return Collections.unmodifiableList(arrayList);
        }
        return Collections.EMPTY_LIST;
    }

    @Deprecated
    public void getBoundsInParent(Rect rect) {
        rect.set(this.mBoundsInParent.left, this.mBoundsInParent.top, this.mBoundsInParent.right, this.mBoundsInParent.bottom);
    }

    public Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public void getBoundsInScreen(Rect rect) {
        rect.set(this.mBoundsInScreen.left, this.mBoundsInScreen.top, this.mBoundsInScreen.right, this.mBoundsInScreen.bottom);
    }

    public AccessibilityNodeInfo getChild(int n) {
        this.enforceSealed();
        if (this.mChildNodeIds == null) {
            return null;
        }
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        long l = this.mChildNodeIds.get(n);
        return AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, l, false, 4, null);
    }

    public int getChildCount() {
        LongArray longArray = this.mChildNodeIds;
        int n = longArray == null ? 0 : longArray.size();
        return n;
    }

    public long getChildId(int n) {
        LongArray longArray = this.mChildNodeIds;
        if (longArray != null) {
            return longArray.get(n);
        }
        throw new IndexOutOfBoundsException();
    }

    public LongArray getChildNodeIds() {
        return this.mChildNodeIds;
    }

    public CharSequence getClassName() {
        return this.mClassName;
    }

    public CollectionInfo getCollectionInfo() {
        return this.mCollectionInfo;
    }

    public CollectionItemInfo getCollectionItemInfo() {
        return this.mCollectionItemInfo;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public int getDrawingOrder() {
        return this.mDrawingOrderInParent;
    }

    public CharSequence getError() {
        return this.mError;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public CharSequence getHintText() {
        return this.mHintText;
    }

    public int getInputType() {
        return this.mInputType;
    }

    public AccessibilityNodeInfo getLabelFor() {
        this.enforceSealed();
        return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabelForId);
    }

    public AccessibilityNodeInfo getLabeledBy() {
        this.enforceSealed();
        return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabeledById);
    }

    public int getLiveRegion() {
        return this.mLiveRegion;
    }

    public int getMaxTextLength() {
        return this.mMaxTextLength;
    }

    public int getMovementGranularities() {
        return this.mMovementGranularities;
    }

    public CharSequence getOriginalText() {
        return this.mOriginalText;
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public CharSequence getPaneTitle() {
        return this.mPaneTitle;
    }

    public AccessibilityNodeInfo getParent() {
        this.enforceSealed();
        return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId);
    }

    public long getParentNodeId() {
        return this.mParentNodeId;
    }

    public RangeInfo getRangeInfo() {
        return this.mRangeInfo;
    }

    @UnsupportedAppUsage
    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public CharSequence getText() {
        AccessibilityClickableSpan[] arraccessibilityClickableSpan = this.mText;
        if (arraccessibilityClickableSpan instanceof Spanned) {
            int n;
            AccessibilityURLSpan[] arraccessibilityURLSpan = (AccessibilityURLSpan[])arraccessibilityClickableSpan;
            arraccessibilityClickableSpan = arraccessibilityURLSpan.getSpans(0, arraccessibilityClickableSpan.length(), AccessibilityClickableSpan.class);
            for (n = 0; n < arraccessibilityClickableSpan.length; ++n) {
                arraccessibilityClickableSpan[n].copyConnectionDataFrom(this);
            }
            arraccessibilityURLSpan = arraccessibilityURLSpan.getSpans(0, this.mText.length(), AccessibilityURLSpan.class);
            for (n = 0; n < arraccessibilityURLSpan.length; ++n) {
                arraccessibilityURLSpan[n].copyConnectionDataFrom(this);
            }
        }
        return this.mText;
    }

    public int getTextSelectionEnd() {
        return this.mTextSelectionEnd;
    }

    public int getTextSelectionStart() {
        return this.mTextSelectionStart;
    }

    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public TouchDelegateInfo getTouchDelegateInfo() {
        TouchDelegateInfo touchDelegateInfo = this.mTouchDelegateInfo;
        if (touchDelegateInfo != null) {
            touchDelegateInfo.setConnectionId(this.mConnectionId);
            this.mTouchDelegateInfo.setWindowId(this.mWindowId);
        }
        return this.mTouchDelegateInfo;
    }

    public AccessibilityNodeInfo getTraversalAfter() {
        this.enforceSealed();
        return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalAfter);
    }

    public AccessibilityNodeInfo getTraversalBefore() {
        this.enforceSealed();
        return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalBefore);
    }

    public String getViewIdResourceName() {
        return this.mViewIdResourceName;
    }

    public AccessibilityWindowInfo getWindow() {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().getWindow(this.mConnectionId, this.mWindowId);
    }

    public int getWindowId() {
        return this.mWindowId;
    }

    public boolean hasExtras() {
        boolean bl = this.mExtras != null;
        return bl;
    }

    public int hashCode() {
        return ((1 * 31 + AccessibilityNodeInfo.getAccessibilityViewId(this.mSourceNodeId)) * 31 + AccessibilityNodeInfo.getVirtualDescendantId(this.mSourceNodeId)) * 31 + this.mWindowId;
    }

    public boolean isAccessibilityFocused() {
        return this.getBooleanProperty(1024);
    }

    public boolean isCheckable() {
        return this.getBooleanProperty(1);
    }

    public boolean isChecked() {
        return this.getBooleanProperty(2);
    }

    public boolean isClickable() {
        return this.getBooleanProperty(32);
    }

    public boolean isContentInvalid() {
        return this.getBooleanProperty(65536);
    }

    public boolean isContextClickable() {
        return this.getBooleanProperty(131072);
    }

    public boolean isDismissable() {
        return this.getBooleanProperty(16384);
    }

    public boolean isEditable() {
        return this.getBooleanProperty(4096);
    }

    public boolean isEnabled() {
        return this.getBooleanProperty(128);
    }

    public boolean isFocusable() {
        return this.getBooleanProperty(4);
    }

    public boolean isFocused() {
        return this.getBooleanProperty(8);
    }

    public boolean isHeading() {
        boolean bl = this.getBooleanProperty(2097152);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        CollectionItemInfo collectionItemInfo = this.getCollectionItemInfo();
        if (collectionItemInfo == null || !collectionItemInfo.mHeading) {
            bl2 = false;
        }
        return bl2;
    }

    public boolean isImportantForAccessibility() {
        return this.getBooleanProperty(262144);
    }

    public boolean isLongClickable() {
        return this.getBooleanProperty(64);
    }

    public boolean isMultiLine() {
        return this.getBooleanProperty(32768);
    }

    public boolean isPassword() {
        return this.getBooleanProperty(256);
    }

    public boolean isScreenReaderFocusable() {
        return this.getBooleanProperty(524288);
    }

    public boolean isScrollable() {
        return this.getBooleanProperty(512);
    }

    @UnsupportedAppUsage
    public boolean isSealed() {
        return this.mSealed;
    }

    public boolean isSelected() {
        return this.getBooleanProperty(16);
    }

    public boolean isShowingHintText() {
        return this.getBooleanProperty(1048576);
    }

    public boolean isTextEntryKey() {
        return this.getBooleanProperty(4194304);
    }

    public boolean isVisibleToUser() {
        return this.getBooleanProperty(2048);
    }

    public boolean performAction(int n) {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        return AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, n, null);
    }

    public boolean performAction(int n, Bundle bundle) {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        return AccessibilityInteractionClient.getInstance().performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, n, bundle);
    }

    public void recycle() {
        this.clear();
        sPool.release(this);
        AtomicInteger atomicInteger = sNumInstancesInUse;
        if (atomicInteger != null) {
            atomicInteger.decrementAndGet();
        }
    }

    public boolean refresh() {
        return this.refresh(null, true);
    }

    @UnsupportedAppUsage
    public boolean refresh(Bundle parcelable, boolean bl) {
        this.enforceSealed();
        if (!AccessibilityNodeInfo.canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        parcelable = AccessibilityInteractionClient.getInstance().findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, bl, 0, (Bundle)parcelable);
        if (parcelable == null) {
            return false;
        }
        this.enforceSealed();
        this.init((AccessibilityNodeInfo)parcelable);
        ((AccessibilityNodeInfo)parcelable).recycle();
        return true;
    }

    public boolean refreshWithExtraData(String string2, Bundle bundle) {
        bundle.putString(EXTRA_DATA_REQUESTED_KEY, string2);
        return this.refresh(bundle, true);
    }

    @Deprecated
    public void removeAction(int n) {
        this.enforceNotSealed();
        this.removeAction(AccessibilityNodeInfo.getActionSingleton(n));
    }

    public boolean removeAction(AccessibilityAction accessibilityAction) {
        this.enforceNotSealed();
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList != null && accessibilityAction != null) {
            return arrayList.remove(accessibilityAction);
        }
        return false;
    }

    public void removeAllActions() {
        ArrayList<AccessibilityAction> arrayList = this.mActions;
        if (arrayList != null) {
            arrayList.clear();
        }
    }

    public boolean removeChild(View view) {
        return this.removeChild(view, -1);
    }

    public boolean removeChild(View view, int n) {
        this.enforceNotSealed();
        LongArray longArray = this.mChildNodeIds;
        if (longArray == null) {
            return false;
        }
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        n = longArray.indexOf(AccessibilityNodeInfo.makeNodeId(n2, n));
        if (n < 0) {
            return false;
        }
        longArray.remove(n);
        return true;
    }

    public void setAccessibilityFocused(boolean bl) {
        this.setBooleanProperty(1024, bl);
    }

    public void setAvailableExtraData(List<String> list) {
        this.enforceNotSealed();
        this.mExtraDataKeys = new ArrayList<String>(list);
    }

    @Deprecated
    public void setBoundsInParent(Rect rect) {
        this.enforceNotSealed();
        this.mBoundsInParent.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setBoundsInScreen(Rect rect) {
        this.enforceNotSealed();
        this.mBoundsInScreen.set(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void setCanOpenPopup(boolean bl) {
        this.enforceNotSealed();
        this.setBooleanProperty(8192, bl);
    }

    public void setCheckable(boolean bl) {
        this.setBooleanProperty(1, bl);
    }

    public void setChecked(boolean bl) {
        this.setBooleanProperty(2, bl);
    }

    public void setClassName(CharSequence charSequence) {
        this.enforceNotSealed();
        this.mClassName = charSequence;
    }

    public void setClickable(boolean bl) {
        this.setBooleanProperty(32, bl);
    }

    public void setCollectionInfo(CollectionInfo collectionInfo) {
        this.enforceNotSealed();
        this.mCollectionInfo = collectionInfo;
    }

    public void setCollectionItemInfo(CollectionItemInfo collectionItemInfo) {
        this.enforceNotSealed();
        this.mCollectionItemInfo = collectionItemInfo;
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

    public void setContentInvalid(boolean bl) {
        this.setBooleanProperty(65536, bl);
    }

    public void setContextClickable(boolean bl) {
        this.setBooleanProperty(131072, bl);
    }

    public void setDismissable(boolean bl) {
        this.setBooleanProperty(16384, bl);
    }

    public void setDrawingOrder(int n) {
        this.enforceNotSealed();
        this.mDrawingOrderInParent = n;
    }

    public void setEditable(boolean bl) {
        this.setBooleanProperty(4096, bl);
    }

    public void setEnabled(boolean bl) {
        this.setBooleanProperty(128, bl);
    }

    public void setError(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mError = charSequence;
    }

    public void setFocusable(boolean bl) {
        this.setBooleanProperty(4, bl);
    }

    public void setFocused(boolean bl) {
        this.setBooleanProperty(8, bl);
    }

    public void setHeading(boolean bl) {
        this.setBooleanProperty(2097152, bl);
    }

    public void setHintText(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mHintText = charSequence;
    }

    public void setImportantForAccessibility(boolean bl) {
        this.setBooleanProperty(262144, bl);
    }

    public void setInputType(int n) {
        this.enforceNotSealed();
        this.mInputType = n;
    }

    public void setLabelFor(View view) {
        this.setLabelFor(view, -1);
    }

    public void setLabelFor(View view, int n) {
        this.enforceNotSealed();
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mLabelForId = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setLabeledBy(View view) {
        this.setLabeledBy(view, -1);
    }

    public void setLabeledBy(View view, int n) {
        this.enforceNotSealed();
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mLabeledById = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setLiveRegion(int n) {
        this.enforceNotSealed();
        this.mLiveRegion = n;
    }

    public void setLongClickable(boolean bl) {
        this.setBooleanProperty(64, bl);
    }

    public void setMaxTextLength(int n) {
        this.enforceNotSealed();
        this.mMaxTextLength = n;
    }

    public void setMovementGranularities(int n) {
        this.enforceNotSealed();
        this.mMovementGranularities = n;
    }

    public void setMultiLine(boolean bl) {
        this.setBooleanProperty(32768, bl);
    }

    public void setPackageName(CharSequence charSequence) {
        this.enforceNotSealed();
        this.mPackageName = charSequence;
    }

    public void setPaneTitle(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mPaneTitle = charSequence;
    }

    public void setParent(View view) {
        this.setParent(view, -1);
    }

    public void setParent(View view, int n) {
        this.enforceNotSealed();
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mParentNodeId = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setPassword(boolean bl) {
        this.setBooleanProperty(256, bl);
    }

    public void setRangeInfo(RangeInfo rangeInfo) {
        this.enforceNotSealed();
        this.mRangeInfo = rangeInfo;
    }

    public void setScreenReaderFocusable(boolean bl) {
        this.setBooleanProperty(524288, bl);
    }

    public void setScrollable(boolean bl) {
        this.setBooleanProperty(512, bl);
    }

    @UnsupportedAppUsage
    public void setSealed(boolean bl) {
        this.mSealed = bl;
    }

    public void setSelected(boolean bl) {
        this.setBooleanProperty(16, bl);
    }

    public void setShowingHintText(boolean bl) {
        this.setBooleanProperty(1048576, bl);
    }

    public void setSource(View view) {
        this.setSource(view, -1);
    }

    public void setSource(View view, int n) {
        this.enforceNotSealed();
        int n2 = Integer.MAX_VALUE;
        int n3 = view != null ? view.getAccessibilityWindowId() : Integer.MAX_VALUE;
        this.mWindowId = n3;
        n3 = n2;
        if (view != null) {
            n3 = view.getAccessibilityViewId();
        }
        this.mSourceNodeId = AccessibilityNodeInfo.makeNodeId(n3, n);
    }

    public void setSourceNodeId(long l, int n) {
        this.enforceNotSealed();
        this.mSourceNodeId = l;
        this.mWindowId = n;
    }

    public void setText(CharSequence object) {
        ClickableSpan[] arrclickableSpan;
        this.enforceNotSealed();
        this.mOriginalText = object;
        if (object instanceof Spanned && (arrclickableSpan = ((Spanned)object).getSpans(0, object.length(), ClickableSpan.class)).length > 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)object);
            for (int i = 0; i < arrclickableSpan.length && !((object = arrclickableSpan[i]) instanceof AccessibilityClickableSpan) && !(object instanceof AccessibilityURLSpan); ++i) {
                int n = spannableStringBuilder.getSpanStart(object);
                int n2 = spannableStringBuilder.getSpanEnd(object);
                int n3 = spannableStringBuilder.getSpanFlags(object);
                spannableStringBuilder.removeSpan(object);
                object = object instanceof URLSpan ? new AccessibilityURLSpan((URLSpan)object) : new AccessibilityClickableSpan(((ClickableSpan)object).getId());
                spannableStringBuilder.setSpan(object, n, n2, n3);
            }
            this.mText = spannableStringBuilder;
            return;
        }
        object = object == null ? null : object.subSequence(0, object.length());
        this.mText = object;
    }

    public void setTextEntryKey(boolean bl) {
        this.setBooleanProperty(4194304, bl);
    }

    public void setTextSelection(int n, int n2) {
        this.enforceNotSealed();
        this.mTextSelectionStart = n;
        this.mTextSelectionEnd = n2;
    }

    public void setTooltipText(CharSequence charSequence) {
        this.enforceNotSealed();
        charSequence = charSequence == null ? null : charSequence.subSequence(0, charSequence.length());
        this.mTooltipText = charSequence;
    }

    public void setTouchDelegateInfo(TouchDelegateInfo touchDelegateInfo) {
        this.enforceNotSealed();
        this.mTouchDelegateInfo = touchDelegateInfo;
    }

    public void setTraversalAfter(View view) {
        this.setTraversalAfter(view, -1);
    }

    public void setTraversalAfter(View view, int n) {
        this.enforceNotSealed();
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mTraversalAfter = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setTraversalBefore(View view) {
        this.setTraversalBefore(view, -1);
    }

    public void setTraversalBefore(View view, int n) {
        this.enforceNotSealed();
        int n2 = view != null ? view.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mTraversalBefore = AccessibilityNodeInfo.makeNodeId(n2, n);
    }

    public void setViewIdResourceName(String string2) {
        this.enforceNotSealed();
        this.mViewIdResourceName = string2;
    }

    public void setVisibleToUser(boolean bl) {
        this.setBooleanProperty(2048, bl);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("; boundsInParent: ");
        stringBuilder.append(this.mBoundsInParent);
        stringBuilder.append("; boundsInScreen: ");
        stringBuilder.append(this.mBoundsInScreen);
        stringBuilder.append("; packageName: ");
        stringBuilder.append(this.mPackageName);
        stringBuilder.append("; className: ");
        stringBuilder.append(this.mClassName);
        stringBuilder.append("; text: ");
        stringBuilder.append(this.mText);
        stringBuilder.append("; error: ");
        stringBuilder.append(this.mError);
        stringBuilder.append("; maxTextLength: ");
        stringBuilder.append(this.mMaxTextLength);
        stringBuilder.append("; contentDescription: ");
        stringBuilder.append(this.mContentDescription);
        stringBuilder.append("; tooltipText: ");
        stringBuilder.append(this.mTooltipText);
        stringBuilder.append("; viewIdResName: ");
        stringBuilder.append(this.mViewIdResourceName);
        stringBuilder.append("; checkable: ");
        stringBuilder.append(this.isCheckable());
        stringBuilder.append("; checked: ");
        stringBuilder.append(this.isChecked());
        stringBuilder.append("; focusable: ");
        stringBuilder.append(this.isFocusable());
        stringBuilder.append("; focused: ");
        stringBuilder.append(this.isFocused());
        stringBuilder.append("; selected: ");
        stringBuilder.append(this.isSelected());
        stringBuilder.append("; clickable: ");
        stringBuilder.append(this.isClickable());
        stringBuilder.append("; longClickable: ");
        stringBuilder.append(this.isLongClickable());
        stringBuilder.append("; contextClickable: ");
        stringBuilder.append(this.isContextClickable());
        stringBuilder.append("; enabled: ");
        stringBuilder.append(this.isEnabled());
        stringBuilder.append("; password: ");
        stringBuilder.append(this.isPassword());
        stringBuilder.append("; scrollable: ");
        stringBuilder.append(this.isScrollable());
        stringBuilder.append("; importantForAccessibility: ");
        stringBuilder.append(this.isImportantForAccessibility());
        stringBuilder.append("; visible: ");
        stringBuilder.append(this.isVisibleToUser());
        stringBuilder.append("; actions: ");
        stringBuilder.append(this.mActions);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelNoRecycle(parcel, n);
        this.recycle();
    }

    public void writeToParcelNoRecycle(Parcel parcel, int n) {
        Object object;
        int n2;
        long l = 0L;
        if (this.isSealed() != DEFAULT.isSealed()) {
            l = 0L | BitUtils.bitAt(0);
        }
        int n3 = 0 + 1;
        long l2 = l;
        if (this.mSourceNodeId != AccessibilityNodeInfo.DEFAULT.mSourceNodeId) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        long l3 = l2;
        if (this.mWindowId != AccessibilityNodeInfo.DEFAULT.mWindowId) {
            l3 = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l3;
        if (this.mParentNodeId != AccessibilityNodeInfo.DEFAULT.mParentNodeId) {
            l = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (this.mLabelForId != AccessibilityNodeInfo.DEFAULT.mLabelForId) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (this.mLabeledById != AccessibilityNodeInfo.DEFAULT.mLabeledById) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l;
        if (this.mTraversalBefore != AccessibilityNodeInfo.DEFAULT.mTraversalBefore) {
            l3 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l3;
        if (this.mTraversalAfter != AccessibilityNodeInfo.DEFAULT.mTraversalAfter) {
            l2 = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (this.mConnectionId != AccessibilityNodeInfo.DEFAULT.mConnectionId) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!LongArray.elementsEqual(this.mChildNodeIds, AccessibilityNodeInfo.DEFAULT.mChildNodeIds)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mBoundsInParent, AccessibilityNodeInfo.DEFAULT.mBoundsInParent)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mBoundsInScreen, AccessibilityNodeInfo.DEFAULT.mBoundsInScreen)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mActions, AccessibilityNodeInfo.DEFAULT.mActions)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l;
        if (this.mMaxTextLength != AccessibilityNodeInfo.DEFAULT.mMaxTextLength) {
            l3 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l3;
        if (this.mMovementGranularities != AccessibilityNodeInfo.DEFAULT.mMovementGranularities) {
            l2 = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l2;
        if (this.mBooleanProperties != AccessibilityNodeInfo.DEFAULT.mBooleanProperties) {
            l3 = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l3;
        if (!Objects.equals(this.mPackageName, AccessibilityNodeInfo.DEFAULT.mPackageName)) {
            l = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mClassName, AccessibilityNodeInfo.DEFAULT.mClassName)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mText, AccessibilityNodeInfo.DEFAULT.mText)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mHintText, AccessibilityNodeInfo.DEFAULT.mHintText)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mError, AccessibilityNodeInfo.DEFAULT.mError)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mContentDescription, AccessibilityNodeInfo.DEFAULT.mContentDescription)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mPaneTitle, AccessibilityNodeInfo.DEFAULT.mPaneTitle)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l;
        if (!Objects.equals(this.mTooltipText, AccessibilityNodeInfo.DEFAULT.mTooltipText)) {
            l3 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l3;
        if (!Objects.equals(this.mViewIdResourceName, AccessibilityNodeInfo.DEFAULT.mViewIdResourceName)) {
            l2 = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (this.mTextSelectionStart != AccessibilityNodeInfo.DEFAULT.mTextSelectionStart) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l;
        if (this.mTextSelectionEnd != AccessibilityNodeInfo.DEFAULT.mTextSelectionEnd) {
            l3 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l3;
        if (this.mInputType != AccessibilityNodeInfo.DEFAULT.mInputType) {
            l2 = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l3 = l2;
        if (this.mLiveRegion != AccessibilityNodeInfo.DEFAULT.mLiveRegion) {
            l3 = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l3;
        if (this.mDrawingOrderInParent != AccessibilityNodeInfo.DEFAULT.mDrawingOrderInParent) {
            l = l3 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mExtraDataKeys, AccessibilityNodeInfo.DEFAULT.mExtraDataKeys)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mExtras, AccessibilityNodeInfo.DEFAULT.mExtras)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mRangeInfo, AccessibilityNodeInfo.DEFAULT.mRangeInfo)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        ++n3;
        l = l2;
        if (!Objects.equals(this.mCollectionInfo, AccessibilityNodeInfo.DEFAULT.mCollectionInfo)) {
            l = l2 | BitUtils.bitAt(n3);
        }
        ++n3;
        l2 = l;
        if (!Objects.equals(this.mCollectionItemInfo, AccessibilityNodeInfo.DEFAULT.mCollectionItemInfo)) {
            l2 = l | BitUtils.bitAt(n3);
        }
        l = l2;
        if (!Objects.equals(this.mTouchDelegateInfo, AccessibilityNodeInfo.DEFAULT.mTouchDelegateInfo)) {
            l = l2 | BitUtils.bitAt(n3 + 1);
        }
        parcel.writeLong(l);
        n3 = 0 + 1;
        if (BitUtils.isBitSet(l, 0)) {
            parcel.writeInt((int)this.isSealed());
        }
        int n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeLong(this.mSourceNodeId);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mWindowId);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeLong(this.mParentNodeId);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeLong(this.mLabelForId);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeLong(this.mLabeledById);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeLong(this.mTraversalBefore);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeLong(this.mTraversalAfter);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mConnectionId);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            object = this.mChildNodeIds;
            if (object == null) {
                parcel.writeInt(0);
            } else {
                n2 = ((LongArray)object).size();
                parcel.writeInt(n2);
                for (n3 = 0; n3 < n2; ++n3) {
                    parcel.writeLong(((LongArray)object).get(n3));
                }
            }
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mBoundsInParent.top);
            parcel.writeInt(this.mBoundsInParent.bottom);
            parcel.writeInt(this.mBoundsInParent.left);
            parcel.writeInt(this.mBoundsInParent.right);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mBoundsInScreen.top);
            parcel.writeInt(this.mBoundsInScreen.bottom);
            parcel.writeInt(this.mBoundsInScreen.left);
            parcel.writeInt(this.mBoundsInScreen.right);
        }
        n2 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            object = this.mActions;
            if (object != null && !((ArrayList)object).isEmpty()) {
                int n5 = this.mActions.size();
                n4 = 0;
                l2 = 0L;
                for (n3 = 0; n3 < n5; ++n3) {
                    object = this.mActions.get(n3);
                    if (AccessibilityNodeInfo.isDefaultStandardAction((AccessibilityAction)object)) {
                        l2 |= ((AccessibilityAction)object).mSerializationFlag;
                        continue;
                    }
                    ++n4;
                }
                parcel.writeLong(l2);
                parcel.writeInt(n4);
                for (n3 = 0; n3 < n5; ++n3) {
                    object = this.mActions.get(n3);
                    if (AccessibilityNodeInfo.isDefaultStandardAction((AccessibilityAction)object)) continue;
                    parcel.writeInt(((AccessibilityAction)object).getId());
                    parcel.writeCharSequence(((AccessibilityAction)object).getLabel());
                }
            } else {
                parcel.writeLong(0L);
                parcel.writeInt(0);
            }
        }
        n4 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            parcel.writeInt(this.mMaxTextLength);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mMovementGranularities);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mBooleanProperties);
        }
        n2 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeCharSequence(this.mPackageName);
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            parcel.writeCharSequence(this.mClassName);
        }
        n2 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeCharSequence(this.mText);
        }
        n4 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            parcel.writeCharSequence(this.mHintText);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeCharSequence(this.mError);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeCharSequence(this.mContentDescription);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeCharSequence(this.mPaneTitle);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeCharSequence(this.mTooltipText);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeString(this.mViewIdResourceName);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mTextSelectionStart);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mTextSelectionEnd);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mInputType);
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mLiveRegion);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mDrawingOrderInParent);
        }
        n2 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeStringList(this.mExtraDataKeys);
        }
        n3 = n2 + 1;
        if (BitUtils.isBitSet(l, n2)) {
            parcel.writeBundle(this.mExtras);
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mRangeInfo.getType());
            parcel.writeFloat(this.mRangeInfo.getMin());
            parcel.writeFloat(this.mRangeInfo.getMax());
            parcel.writeFloat(this.mRangeInfo.getCurrent());
        }
        n3 = n4 + 1;
        if (BitUtils.isBitSet(l, n4)) {
            parcel.writeInt(this.mCollectionInfo.getRowCount());
            parcel.writeInt(this.mCollectionInfo.getColumnCount());
            parcel.writeInt((int)this.mCollectionInfo.isHierarchical());
            parcel.writeInt(this.mCollectionInfo.getSelectionMode());
        }
        n4 = n3 + 1;
        if (BitUtils.isBitSet(l, n3)) {
            parcel.writeInt(this.mCollectionItemInfo.getRowIndex());
            parcel.writeInt(this.mCollectionItemInfo.getRowSpan());
            parcel.writeInt(this.mCollectionItemInfo.getColumnIndex());
            parcel.writeInt(this.mCollectionItemInfo.getColumnSpan());
            parcel.writeInt((int)this.mCollectionItemInfo.isHeading());
            parcel.writeInt((int)this.mCollectionItemInfo.isSelected());
        }
        if (BitUtils.isBitSet(l, n4)) {
            this.mTouchDelegateInfo.writeToParcel(parcel, n);
        }
    }

    public static final class AccessibilityAction {
        public static final AccessibilityAction ACTION_ACCESSIBILITY_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_ACCESSIBILITY_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_FOCUS;
        public static final AccessibilityAction ACTION_CLEAR_SELECTION;
        public static final AccessibilityAction ACTION_CLICK;
        public static final AccessibilityAction ACTION_COLLAPSE;
        public static final AccessibilityAction ACTION_CONTEXT_CLICK;
        public static final AccessibilityAction ACTION_COPY;
        public static final AccessibilityAction ACTION_CUT;
        public static final AccessibilityAction ACTION_DISMISS;
        public static final AccessibilityAction ACTION_EXPAND;
        public static final AccessibilityAction ACTION_FOCUS;
        public static final AccessibilityAction ACTION_HIDE_TOOLTIP;
        public static final AccessibilityAction ACTION_LONG_CLICK;
        public static final AccessibilityAction ACTION_MOVE_WINDOW;
        public static final AccessibilityAction ACTION_NEXT_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityAction ACTION_NEXT_HTML_ELEMENT;
        public static final AccessibilityAction ACTION_PAGE_DOWN;
        public static final AccessibilityAction ACTION_PAGE_LEFT;
        public static final AccessibilityAction ACTION_PAGE_RIGHT;
        public static final AccessibilityAction ACTION_PAGE_UP;
        public static final AccessibilityAction ACTION_PASTE;
        public static final AccessibilityAction ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY;
        public static final AccessibilityAction ACTION_PREVIOUS_HTML_ELEMENT;
        public static final AccessibilityAction ACTION_SCROLL_BACKWARD;
        public static final AccessibilityAction ACTION_SCROLL_DOWN;
        public static final AccessibilityAction ACTION_SCROLL_FORWARD;
        public static final AccessibilityAction ACTION_SCROLL_LEFT;
        public static final AccessibilityAction ACTION_SCROLL_RIGHT;
        public static final AccessibilityAction ACTION_SCROLL_TO_POSITION;
        public static final AccessibilityAction ACTION_SCROLL_UP;
        public static final AccessibilityAction ACTION_SELECT;
        public static final AccessibilityAction ACTION_SET_PROGRESS;
        public static final AccessibilityAction ACTION_SET_SELECTION;
        public static final AccessibilityAction ACTION_SET_TEXT;
        public static final AccessibilityAction ACTION_SHOW_ON_SCREEN;
        public static final AccessibilityAction ACTION_SHOW_TOOLTIP;
        public static final ArraySet<AccessibilityAction> sStandardActions;
        private final int mActionId;
        private final CharSequence mLabel;
        public long mSerializationFlag = -1L;

        static {
            sStandardActions = new ArraySet();
            ACTION_FOCUS = new AccessibilityAction(1);
            ACTION_CLEAR_FOCUS = new AccessibilityAction(2);
            ACTION_SELECT = new AccessibilityAction(4);
            ACTION_CLEAR_SELECTION = new AccessibilityAction(8);
            ACTION_CLICK = new AccessibilityAction(16);
            ACTION_LONG_CLICK = new AccessibilityAction(32);
            ACTION_ACCESSIBILITY_FOCUS = new AccessibilityAction(64);
            ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityAction(128);
            ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(256);
            ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(512);
            ACTION_NEXT_HTML_ELEMENT = new AccessibilityAction(1024);
            ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityAction(2048);
            ACTION_SCROLL_FORWARD = new AccessibilityAction(4096);
            ACTION_SCROLL_BACKWARD = new AccessibilityAction(8192);
            ACTION_COPY = new AccessibilityAction(16384);
            ACTION_PASTE = new AccessibilityAction(32768);
            ACTION_CUT = new AccessibilityAction(65536);
            ACTION_SET_SELECTION = new AccessibilityAction(131072);
            ACTION_EXPAND = new AccessibilityAction(262144);
            ACTION_COLLAPSE = new AccessibilityAction(524288);
            ACTION_DISMISS = new AccessibilityAction(1048576);
            ACTION_SET_TEXT = new AccessibilityAction(2097152);
            ACTION_SHOW_ON_SCREEN = new AccessibilityAction(16908342);
            ACTION_SCROLL_TO_POSITION = new AccessibilityAction(16908343);
            ACTION_SCROLL_UP = new AccessibilityAction(16908344);
            ACTION_SCROLL_LEFT = new AccessibilityAction(16908345);
            ACTION_SCROLL_DOWN = new AccessibilityAction(16908346);
            ACTION_SCROLL_RIGHT = new AccessibilityAction(16908347);
            ACTION_PAGE_UP = new AccessibilityAction(16908358);
            ACTION_PAGE_DOWN = new AccessibilityAction(16908359);
            ACTION_PAGE_LEFT = new AccessibilityAction(16908360);
            ACTION_PAGE_RIGHT = new AccessibilityAction(16908361);
            ACTION_CONTEXT_CLICK = new AccessibilityAction(16908348);
            ACTION_SET_PROGRESS = new AccessibilityAction(16908349);
            ACTION_MOVE_WINDOW = new AccessibilityAction(16908354);
            ACTION_SHOW_TOOLTIP = new AccessibilityAction(16908356);
            ACTION_HIDE_TOOLTIP = new AccessibilityAction(16908357);
        }

        private AccessibilityAction(int n) {
            this(n, null);
            this.mSerializationFlag = BitUtils.bitAt(sStandardActions.size());
            sStandardActions.add(this);
        }

        public AccessibilityAction(int n, CharSequence charSequence) {
            if ((-16777216 & n) == 0 && Integer.bitCount(n) != 1) {
                throw new IllegalArgumentException("Invalid standard action id");
            }
            this.mActionId = n;
            this.mLabel = charSequence;
        }

        public boolean equals(Object object) {
            boolean bl = false;
            if (object == null) {
                return false;
            }
            if (object == this) {
                return true;
            }
            if (this.getClass() != object.getClass()) {
                return false;
            }
            if (this.mActionId == ((AccessibilityAction)object).mActionId) {
                bl = true;
            }
            return bl;
        }

        public int getId() {
            return this.mActionId;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }

        public int hashCode() {
            return this.mActionId;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AccessibilityAction: ");
            stringBuilder.append(AccessibilityNodeInfo.getActionSymbolicName(this.mActionId));
            stringBuilder.append(" - ");
            stringBuilder.append((Object)this.mLabel);
            return stringBuilder.toString();
        }
    }

    public static final class CollectionInfo {
        private static final int MAX_POOL_SIZE = 20;
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        private static final Pools.SynchronizedPool<CollectionInfo> sPool = new Pools.SynchronizedPool(20);
        private int mColumnCount;
        private boolean mHierarchical;
        private int mRowCount;
        private int mSelectionMode;

        private CollectionInfo(int n, int n2, boolean bl, int n3) {
            this.mRowCount = n;
            this.mColumnCount = n2;
            this.mHierarchical = bl;
            this.mSelectionMode = n3;
        }

        private void clear() {
            this.mRowCount = 0;
            this.mColumnCount = 0;
            this.mHierarchical = false;
            this.mSelectionMode = 0;
        }

        public static CollectionInfo obtain(int n, int n2, boolean bl) {
            return CollectionInfo.obtain(n, n2, bl, 0);
        }

        public static CollectionInfo obtain(int n, int n2, boolean bl, int n3) {
            CollectionInfo collectionInfo = sPool.acquire();
            if (collectionInfo == null) {
                return new CollectionInfo(n, n2, bl, n3);
            }
            collectionInfo.mRowCount = n;
            collectionInfo.mColumnCount = n2;
            collectionInfo.mHierarchical = bl;
            collectionInfo.mSelectionMode = n3;
            return collectionInfo;
        }

        public static CollectionInfo obtain(CollectionInfo collectionInfo) {
            return CollectionInfo.obtain(collectionInfo.mRowCount, collectionInfo.mColumnCount, collectionInfo.mHierarchical, collectionInfo.mSelectionMode);
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public int getRowCount() {
            return this.mRowCount;
        }

        public int getSelectionMode() {
            return this.mSelectionMode;
        }

        public boolean isHierarchical() {
            return this.mHierarchical;
        }

        void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

    public static final class CollectionItemInfo {
        private static final int MAX_POOL_SIZE = 20;
        private static final Pools.SynchronizedPool<CollectionItemInfo> sPool = new Pools.SynchronizedPool(20);
        private int mColumnIndex;
        private int mColumnSpan;
        private boolean mHeading;
        private int mRowIndex;
        private int mRowSpan;
        private boolean mSelected;

        private CollectionItemInfo(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            this.mRowIndex = n;
            this.mRowSpan = n2;
            this.mColumnIndex = n3;
            this.mColumnSpan = n4;
            this.mHeading = bl;
            this.mSelected = bl2;
        }

        private void clear() {
            this.mColumnIndex = 0;
            this.mColumnSpan = 0;
            this.mRowIndex = 0;
            this.mRowSpan = 0;
            this.mHeading = false;
            this.mSelected = false;
        }

        public static CollectionItemInfo obtain(int n, int n2, int n3, int n4, boolean bl) {
            return CollectionItemInfo.obtain(n, n2, n3, n4, bl, false);
        }

        public static CollectionItemInfo obtain(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
            CollectionItemInfo collectionItemInfo = sPool.acquire();
            if (collectionItemInfo == null) {
                return new CollectionItemInfo(n, n2, n3, n4, bl, bl2);
            }
            collectionItemInfo.mRowIndex = n;
            collectionItemInfo.mRowSpan = n2;
            collectionItemInfo.mColumnIndex = n3;
            collectionItemInfo.mColumnSpan = n4;
            collectionItemInfo.mHeading = bl;
            collectionItemInfo.mSelected = bl2;
            return collectionItemInfo;
        }

        public static CollectionItemInfo obtain(CollectionItemInfo collectionItemInfo) {
            return CollectionItemInfo.obtain(collectionItemInfo.mRowIndex, collectionItemInfo.mRowSpan, collectionItemInfo.mColumnIndex, collectionItemInfo.mColumnSpan, collectionItemInfo.mHeading, collectionItemInfo.mSelected);
        }

        public int getColumnIndex() {
            return this.mColumnIndex;
        }

        public int getColumnSpan() {
            return this.mColumnSpan;
        }

        public int getRowIndex() {
            return this.mRowIndex;
        }

        public int getRowSpan() {
            return this.mRowSpan;
        }

        public boolean isHeading() {
            return this.mHeading;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

    public static final class RangeInfo {
        private static final int MAX_POOL_SIZE = 10;
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        private static final Pools.SynchronizedPool<RangeInfo> sPool = new Pools.SynchronizedPool(10);
        private float mCurrent;
        private float mMax;
        private float mMin;
        private int mType;

        private RangeInfo(int n, float f, float f2, float f3) {
            this.mType = n;
            this.mMin = f;
            this.mMax = f2;
            this.mCurrent = f3;
        }

        private void clear() {
            this.mType = 0;
            this.mMin = 0.0f;
            this.mMax = 0.0f;
            this.mCurrent = 0.0f;
        }

        public static RangeInfo obtain(int n, float f, float f2, float f3) {
            RangeInfo rangeInfo = sPool.acquire();
            if (rangeInfo == null) {
                return new RangeInfo(n, f, f2, f3);
            }
            rangeInfo.mType = n;
            rangeInfo.mMin = f;
            rangeInfo.mMax = f2;
            rangeInfo.mCurrent = f3;
            return rangeInfo;
        }

        public static RangeInfo obtain(RangeInfo rangeInfo) {
            return RangeInfo.obtain(rangeInfo.mType, rangeInfo.mMin, rangeInfo.mMax, rangeInfo.mCurrent);
        }

        public float getCurrent() {
            return this.mCurrent;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getMin() {
            return this.mMin;
        }

        public int getType() {
            return this.mType;
        }

        void recycle() {
            this.clear();
            sPool.release(this);
        }
    }

    public static final class TouchDelegateInfo
    implements Parcelable {
        public static final Parcelable.Creator<TouchDelegateInfo> CREATOR = new Parcelable.Creator<TouchDelegateInfo>(){

            @Override
            public TouchDelegateInfo createFromParcel(Parcel parcel) {
                int n = parcel.readInt();
                if (n == 0) {
                    return null;
                }
                ArrayMap<Region, Long> arrayMap = new ArrayMap<Region, Long>(n);
                for (int i = 0; i < n; ++i) {
                    arrayMap.put(Region.CREATOR.createFromParcel(parcel), parcel.readLong());
                }
                return new TouchDelegateInfo(arrayMap, false);
            }

            public TouchDelegateInfo[] newArray(int n) {
                return new TouchDelegateInfo[n];
            }
        };
        private int mConnectionId;
        private ArrayMap<Region, Long> mTargetMap;
        private int mWindowId;

        TouchDelegateInfo(ArrayMap<Region, Long> arrayMap, boolean bl) {
            boolean bl2 = !arrayMap.isEmpty() && !arrayMap.containsKey(null) && !arrayMap.containsValue(null);
            Preconditions.checkArgument(bl2);
            if (bl) {
                this.mTargetMap = new ArrayMap(arrayMap.size());
                this.mTargetMap.putAll(arrayMap);
            } else {
                this.mTargetMap = arrayMap;
            }
        }

        public TouchDelegateInfo(Map<Region, View> map) {
            boolean bl = !map.isEmpty() && !map.containsKey(null) && !map.containsValue(null);
            Preconditions.checkArgument(bl);
            this.mTargetMap = new ArrayMap(map.size());
            for (Region region : map.keySet()) {
                View view = map.get(region);
                this.mTargetMap.put(region, Long.valueOf(view.getAccessibilityViewId()));
            }
        }

        private void setConnectionId(int n) {
            this.mConnectionId = n;
        }

        private void setWindowId(int n) {
            this.mWindowId = n;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public long getAccessibilityIdForRegion(Region region) {
            return this.mTargetMap.get(region);
        }

        public Region getRegionAt(int n) {
            return this.mTargetMap.keyAt(n);
        }

        public int getRegionCount() {
            return this.mTargetMap.size();
        }

        public AccessibilityNodeInfo getTargetForRegion(Region region) {
            return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTargetMap.get(region));
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mTargetMap.size());
            for (int i = 0; i < this.mTargetMap.size(); ++i) {
                Region region = this.mTargetMap.keyAt(i);
                Long l = this.mTargetMap.valueAt(i);
                region.writeToParcel(parcel, n);
                parcel.writeLong(l);
            }
        }

    }

}

