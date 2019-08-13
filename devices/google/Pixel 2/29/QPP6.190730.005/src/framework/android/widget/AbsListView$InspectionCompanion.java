/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.AbsListView;
import android.widget._$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI;
import java.util.Objects;
import java.util.function.IntFunction;

public final class AbsListView$InspectionCompanion
implements InspectionCompanion<AbsListView> {
    private int mCacheColorHintId;
    private int mChoiceModeId;
    private int mDrawSelectorOnTopId;
    private int mFastScrollEnabledId;
    private int mListSelectorId;
    private boolean mPropertiesMapped = false;
    private int mScrollingCacheId;
    private int mSmoothScrollbarId;
    private int mStackFromBottomId;
    private int mTextFilterEnabledId;
    private int mTranscriptModeId;

    @Override
    public void mapProperties(PropertyMapper propertyMapper) {
        this.mCacheColorHintId = propertyMapper.mapColor("cacheColorHint", 16843009);
        SparseArray<String> sparseArray = new SparseArray<String>();
        sparseArray.put(0, "none");
        sparseArray.put(1, "singleChoice");
        sparseArray.put(2, "multipleChoice");
        sparseArray.put(3, "multipleChoiceModal");
        Objects.requireNonNull(sparseArray);
        this.mChoiceModeId = propertyMapper.mapIntEnum("choiceMode", 16843051, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mDrawSelectorOnTopId = propertyMapper.mapBoolean("drawSelectorOnTop", 16843004);
        this.mFastScrollEnabledId = propertyMapper.mapBoolean("fastScrollEnabled", 16843302);
        this.mListSelectorId = propertyMapper.mapObject("listSelector", 16843003);
        this.mScrollingCacheId = propertyMapper.mapBoolean("scrollingCache", 16843006);
        this.mSmoothScrollbarId = propertyMapper.mapBoolean("smoothScrollbar", 16843313);
        this.mStackFromBottomId = propertyMapper.mapBoolean("stackFromBottom", 16843005);
        this.mTextFilterEnabledId = propertyMapper.mapBoolean("textFilterEnabled", 16843007);
        sparseArray = new SparseArray();
        sparseArray.put(0, "disabled");
        sparseArray.put(1, "normal");
        sparseArray.put(2, "alwaysScroll");
        Objects.requireNonNull(sparseArray);
        this.mTranscriptModeId = propertyMapper.mapIntEnum("transcriptMode", 16843008, new _$$Lambda$QY3N4tzLteuFdjRnyJFCbR1ajSI(sparseArray));
        this.mPropertiesMapped = true;
    }

    @Override
    public void readProperties(AbsListView absListView, PropertyReader propertyReader) {
        if (this.mPropertiesMapped) {
            propertyReader.readColor(this.mCacheColorHintId, absListView.getCacheColorHint());
            propertyReader.readIntEnum(this.mChoiceModeId, absListView.getChoiceMode());
            propertyReader.readBoolean(this.mDrawSelectorOnTopId, absListView.isDrawSelectorOnTop());
            propertyReader.readBoolean(this.mFastScrollEnabledId, absListView.isFastScrollEnabled());
            propertyReader.readObject(this.mListSelectorId, absListView.getSelector());
            propertyReader.readBoolean(this.mScrollingCacheId, absListView.isScrollingCacheEnabled());
            propertyReader.readBoolean(this.mSmoothScrollbarId, absListView.isSmoothScrollbarEnabled());
            propertyReader.readBoolean(this.mStackFromBottomId, absListView.isStackFromBottom());
            propertyReader.readBoolean(this.mTextFilterEnabledId, absListView.isTextFilterEnabled());
            propertyReader.readIntEnum(this.mTranscriptModeId, absListView.getTranscriptMode());
            return;
        }
        throw new InspectionCompanion.UninitializedPropertyMapException();
    }
}

