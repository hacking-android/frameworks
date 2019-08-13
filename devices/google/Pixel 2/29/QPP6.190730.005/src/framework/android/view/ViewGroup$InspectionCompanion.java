/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.util.SparseArray;
import android.view.ViewGroup;
import android.view._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import android.view.animation.LayoutAnimationController;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import java.util.Objects;
import java.util.function.IntFunction;

public final class ViewGroup$InspectionCompanion
implements InspectionCompanion<ViewGroup> {
    private int mAddStatesFromChildrenId;
    private int mAlwaysDrawnWithCacheId;
    private int mAnimationCacheId;
    private int mClipChildrenId;
    private int mClipToPaddingId;
    private int mDescendantFocusabilityId;
    private int mLayoutAnimationId;
    private int mLayoutModeId;
    private int mPersistentDrawingCacheId;
    private boolean mPropertiesMapped = false;
    private int mSplitMotionEventsId;
    private int mTouchscreenBlocksFocusId;
    private int mTransitionGroupId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mAddStatesFromChildrenId = propertyMapper.mapBoolean("addStatesFromChildren", 16842992);
        this.mAlwaysDrawnWithCacheId = propertyMapper.mapBoolean("alwaysDrawnWithCache", 16842991);
        this.mAnimationCacheId = propertyMapper.mapBoolean("animationCache", 16842989);
        this.mClipChildrenId = propertyMapper.mapBoolean("clipChildren", 16842986);
        this.mClipToPaddingId = propertyMapper.mapBoolean("clipToPadding", 16842987);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(131072, "beforeDescendants");
        sparseArray.put(262144, "afterDescendants");
        sparseArray.put(393216, "blocksDescendants");
        Objects.requireNonNull(sparseArray);
        this.mDescendantFocusabilityId = propertyMapper.mapIntEnum("descendantFocusability", 16842993, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mLayoutAnimationId = propertyMapper.mapObject("layoutAnimation", 16842988);
        sparseArray = new SparseArray();
        sparseArray.put(0, "clipBounds");
        sparseArray.put(1, "opticalBounds");
        Objects.requireNonNull(sparseArray);
        this.mLayoutModeId = propertyMapper.mapIntEnum("layoutMode", 16843738, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        sparseArray = new SparseArray();
        sparseArray.put(0, "none");
        sparseArray.put(1, "animation");
        sparseArray.put(2, "scrolling");
        sparseArray.put(3, "all");
        Objects.requireNonNull(sparseArray);
        this.mPersistentDrawingCacheId = propertyMapper.mapIntEnum("persistentDrawingCache", 16842990, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mSplitMotionEventsId = propertyMapper.mapBoolean("splitMotionEvents", 16843503);
        this.mTouchscreenBlocksFocusId = propertyMapper.mapBoolean("touchscreenBlocksFocus", 16843919);
        this.mTransitionGroupId = propertyMapper.mapBoolean("transitionGroup", 16843777);
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(ViewGroup viewGroup, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readBoolean(this.mAddStatesFromChildrenId, viewGroup.addStatesFromChildren());
            propertyReader.readBoolean(this.mAlwaysDrawnWithCacheId, viewGroup.isAlwaysDrawnWithCacheEnabled());
            propertyReader.readBoolean(this.mAnimationCacheId, viewGroup.isAnimationCacheEnabled());
            propertyReader.readBoolean(this.mClipChildrenId, viewGroup.getClipChildren());
            propertyReader.readBoolean(this.mClipToPaddingId, viewGroup.getClipToPadding());
            propertyReader.readIntEnum(this.mDescendantFocusabilityId, viewGroup.getDescendantFocusability());
            propertyReader.readObject(this.mLayoutAnimationId, viewGroup.getLayoutAnimation());
            propertyReader.readIntEnum(this.mLayoutModeId, viewGroup.getLayoutMode());
            propertyReader.readIntEnum(this.mPersistentDrawingCacheId, viewGroup.getPersistentDrawingCache());
            propertyReader.readBoolean(this.mSplitMotionEventsId, viewGroup.isMotionEventSplittingEnabled());
            propertyReader.readBoolean(this.mTouchscreenBlocksFocusId, viewGroup.getTouchscreenBlocksFocus());
            propertyReader.readBoolean(this.mTransitionGroupId, viewGroup.isTransitionGroup());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

