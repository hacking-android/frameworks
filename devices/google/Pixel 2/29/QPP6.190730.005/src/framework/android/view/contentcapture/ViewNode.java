/*
 * Decompiled with CFR 0.145.
 */
package android.view.contentcapture;

import android.annotation.SystemApi;
import android.app.assist.AssistStructure;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewStructure;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillValue;
import com.android.internal.util.Preconditions;

@SystemApi
public final class ViewNode
extends AssistStructure.ViewNode {
    private static final long FLAGS_ACCESSIBILITY_FOCUSED = 131072L;
    private static final long FLAGS_ACTIVATED = 0x200000L;
    private static final long FLAGS_ASSIST_BLOCKED = 1024L;
    private static final long FLAGS_CHECKABLE = 262144L;
    private static final long FLAGS_CHECKED = 524288L;
    private static final long FLAGS_CLICKABLE = 4096L;
    private static final long FLAGS_CONTEXT_CLICKABLE = 16384L;
    private static final long FLAGS_DISABLED = 2048L;
    private static final long FLAGS_FOCUSABLE = 32768L;
    private static final long FLAGS_FOCUSED = 65536L;
    private static final long FLAGS_HAS_AUTOFILL_HINTS = 0x200000000L;
    private static final long FLAGS_HAS_AUTOFILL_ID = 32L;
    private static final long FLAGS_HAS_AUTOFILL_OPTIONS = 0x400000000L;
    private static final long FLAGS_HAS_AUTOFILL_PARENT_ID = 64L;
    private static final long FLAGS_HAS_AUTOFILL_TYPE = 0x80000000L;
    private static final long FLAGS_HAS_AUTOFILL_VALUE = 0x100000000L;
    private static final long FLAGS_HAS_CLASSNAME = 16L;
    private static final long FLAGS_HAS_COMPLEX_TEXT = 2L;
    private static final long FLAGS_HAS_CONTENT_DESCRIPTION = 0x800000L;
    private static final long FLAGS_HAS_EXTRAS = 0x1000000L;
    private static final long FLAGS_HAS_ID = 128L;
    private static final long FLAGS_HAS_INPUT_TYPE = 0x4000000L;
    private static final long FLAGS_HAS_LARGE_COORDS = 256L;
    private static final long FLAGS_HAS_LOCALE_LIST = 0x2000000L;
    private static final long FLAGS_HAS_MAX_TEXT_EMS = 0x10000000L;
    private static final long FLAGS_HAS_MAX_TEXT_LENGTH = 0x20000000L;
    private static final long FLAGS_HAS_MIN_TEXT_EMS = 0x8000000L;
    private static final long FLAGS_HAS_SCROLL = 512L;
    private static final long FLAGS_HAS_TEXT = 1L;
    private static final long FLAGS_HAS_TEXT_ID_ENTRY = 0x40000000L;
    private static final long FLAGS_LONG_CLICKABLE = 8192L;
    private static final long FLAGS_OPAQUE = 0x400000L;
    private static final long FLAGS_SELECTED = 0x100000L;
    private static final long FLAGS_VISIBILITY_MASK = 12L;
    private static final String TAG = ViewNode.class.getSimpleName();
    private String[] mAutofillHints;
    private AutofillId mAutofillId;
    private CharSequence[] mAutofillOptions;
    private int mAutofillType;
    private AutofillValue mAutofillValue;
    private String mClassName;
    private CharSequence mContentDescription;
    private Bundle mExtras;
    private long mFlags;
    private int mHeight;
    private int mId = -1;
    private String mIdEntry;
    private String mIdPackage;
    private String mIdType;
    private int mInputType;
    private LocaleList mLocaleList;
    private int mMaxEms = -1;
    private int mMaxLength = -1;
    private int mMinEms = -1;
    private AutofillId mParentAutofillId;
    private int mScrollX;
    private int mScrollY;
    private ViewNodeText mText;
    private String mTextIdEntry;
    private int mWidth;
    private int mX;
    private int mY;

    public ViewNode() {
        this.mAutofillType = 0;
    }

    private ViewNode(long l, Parcel parcel) {
        boolean bl = false;
        this.mAutofillType = 0;
        this.mFlags = l;
        if ((32L & l) != 0L) {
            this.mAutofillId = (AutofillId)parcel.readParcelable(null);
        }
        if ((64L & l) != 0L) {
            this.mParentAutofillId = (AutofillId)parcel.readParcelable(null);
        }
        if ((1L & l) != 0L) {
            if ((2L & l) == 0L) {
                bl = true;
            }
            this.mText = new ViewNodeText(parcel, bl);
        }
        if ((16L & l) != 0L) {
            this.mClassName = parcel.readString();
        }
        if ((128L & l) != 0L) {
            this.mId = parcel.readInt();
            if (this.mId != -1) {
                this.mIdEntry = parcel.readString();
                if (this.mIdEntry != null) {
                    this.mIdType = parcel.readString();
                    this.mIdPackage = parcel.readString();
                }
            }
        }
        if ((256L & l) != 0L) {
            this.mX = parcel.readInt();
            this.mY = parcel.readInt();
            this.mWidth = parcel.readInt();
            this.mHeight = parcel.readInt();
        } else {
            int n = parcel.readInt();
            this.mX = n & 32767;
            this.mY = n >> 16 & 32767;
            n = parcel.readInt();
            this.mWidth = n & 32767;
            this.mHeight = n >> 16 & 32767;
        }
        if ((512L & l) != 0L) {
            this.mScrollX = parcel.readInt();
            this.mScrollY = parcel.readInt();
        }
        if ((0x800000L & l) != 0L) {
            this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        }
        if ((0x1000000L & l) != 0L) {
            this.mExtras = parcel.readBundle();
        }
        if ((0x2000000L & l) != 0L) {
            this.mLocaleList = (LocaleList)parcel.readParcelable(null);
        }
        if ((0x4000000L & l) != 0L) {
            this.mInputType = parcel.readInt();
        }
        if ((0x8000000L & l) != 0L) {
            this.mMinEms = parcel.readInt();
        }
        if ((0x10000000L & l) != 0L) {
            this.mMaxEms = parcel.readInt();
        }
        if ((0x20000000L & l) != 0L) {
            this.mMaxLength = parcel.readInt();
        }
        if ((0x40000000L & l) != 0L) {
            this.mTextIdEntry = parcel.readString();
        }
        if ((0x80000000L & l) != 0L) {
            this.mAutofillType = parcel.readInt();
        }
        if ((0x200000000L & l) != 0L) {
            this.mAutofillHints = parcel.readStringArray();
        }
        if ((0x100000000L & l) != 0L) {
            this.mAutofillValue = (AutofillValue)parcel.readParcelable(null);
        }
        if ((0x400000000L & l) != 0L) {
            this.mAutofillOptions = parcel.readCharSequenceArray();
        }
    }

    public static ViewNode readFromParcel(Parcel object) {
        long l = ((Parcel)object).readLong();
        object = l == 0L ? null : new ViewNode(l, (Parcel)object);
        return object;
    }

    private void writeSelfToParcel(Parcel parcel, int n) {
        long l;
        long l2;
        ViewNodeText viewNodeText;
        block49 : {
            block48 : {
                block47 : {
                    block46 : {
                        l2 = l = this.mFlags;
                        if (this.mAutofillId != null) {
                            l2 = l | 32L;
                        }
                        l = l2;
                        if (this.mParentAutofillId != null) {
                            l = l2 | 64L;
                        }
                        viewNodeText = this.mText;
                        l2 = l;
                        if (viewNodeText != null) {
                            l2 = l |= 1L;
                            if (!viewNodeText.isSimple()) {
                                l2 = l | 2L;
                            }
                        }
                        l = l2;
                        if (this.mClassName != null) {
                            l = l2 | 16L;
                        }
                        l2 = l;
                        if (this.mId != -1) {
                            l2 = l | 128L;
                        }
                        if ((this.mX & -32768) != 0 || (this.mY & -32768) != 0) break block46;
                        boolean bl = (this.mWidth & -32768) != 0;
                        boolean bl2 = (this.mHeight & -32768) != 0;
                        l = l2;
                        if (!(bl | bl2)) break block47;
                    }
                    l = l2 | 256L;
                }
                if (this.mScrollX != 0) break block48;
                l2 = l;
                if (this.mScrollY == 0) break block49;
            }
            l2 = l | 512L;
        }
        long l3 = l2;
        if (this.mContentDescription != null) {
            l3 = l2 | 0x800000L;
        }
        l = l3;
        if (this.mExtras != null) {
            l = l3 | 0x1000000L;
        }
        l2 = l;
        if (this.mLocaleList != null) {
            l2 = l | 0x2000000L;
        }
        l = l2;
        if (this.mInputType != 0) {
            l = l2 | 0x4000000L;
        }
        l2 = l;
        if (this.mMinEms > -1) {
            l2 = l | 0x8000000L;
        }
        l = l2;
        if (this.mMaxEms > -1) {
            l = l2 | 0x10000000L;
        }
        l2 = l;
        if (this.mMaxLength > -1) {
            l2 = l | 0x20000000L;
        }
        l = l2;
        if (this.mTextIdEntry != null) {
            l = l2 | 0x40000000L;
        }
        l2 = l;
        if (this.mAutofillValue != null) {
            l2 = l | 0x100000000L;
        }
        l = l2;
        if (this.mAutofillType != 0) {
            l = l2 | 0x80000000L;
        }
        l2 = l;
        if (this.mAutofillHints != null) {
            l2 = l | 0x200000000L;
        }
        l = l2;
        if (this.mAutofillOptions != null) {
            l = l2 | 0x400000000L;
        }
        parcel.writeLong(l);
        if ((l & 32L) != 0L) {
            parcel.writeParcelable(this.mAutofillId, n);
        }
        if ((l & 64L) != 0L) {
            parcel.writeParcelable(this.mParentAutofillId, n);
        }
        if ((l & 1L) != 0L) {
            viewNodeText = this.mText;
            boolean bl = (l & 2L) == 0L;
            viewNodeText.writeToParcel(parcel, bl);
        }
        if ((16L & l) != 0L) {
            parcel.writeString(this.mClassName);
        }
        if ((l & 128L) != 0L) {
            parcel.writeInt(this.mId);
            if (this.mId != -1) {
                parcel.writeString(this.mIdEntry);
                if (this.mIdEntry != null) {
                    parcel.writeString(this.mIdType);
                    parcel.writeString(this.mIdPackage);
                }
            }
        }
        if ((l & 256L) != 0L) {
            parcel.writeInt(this.mX);
            parcel.writeInt(this.mY);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
        } else {
            parcel.writeInt(this.mY << 16 | this.mX);
            parcel.writeInt(this.mHeight << 16 | this.mWidth);
        }
        if ((l & 512L) != 0L) {
            parcel.writeInt(this.mScrollX);
            parcel.writeInt(this.mScrollY);
        }
        if ((l & 0x800000L) != 0L) {
            TextUtils.writeToParcel(this.mContentDescription, parcel, 0);
        }
        if ((l & 0x1000000L) != 0L) {
            parcel.writeBundle(this.mExtras);
        }
        if ((0x2000000L & l) != 0L) {
            parcel.writeParcelable(this.mLocaleList, 0);
        }
        if ((0x4000000L & l) != 0L) {
            parcel.writeInt(this.mInputType);
        }
        if ((0x8000000L & l) != 0L) {
            parcel.writeInt(this.mMinEms);
        }
        if ((0x10000000L & l) != 0L) {
            parcel.writeInt(this.mMaxEms);
        }
        if ((0x20000000L & l) != 0L) {
            parcel.writeInt(this.mMaxLength);
        }
        if ((0x40000000L & l) != 0L) {
            parcel.writeString(this.mTextIdEntry);
        }
        if ((0x80000000L & l) != 0L) {
            parcel.writeInt(this.mAutofillType);
        }
        if ((0x200000000L & l) != 0L) {
            parcel.writeStringArray(this.mAutofillHints);
        }
        if ((0x100000000L & l) != 0L) {
            parcel.writeParcelable(this.mAutofillValue, 0);
        }
        if ((0x400000000L & l) != 0L) {
            parcel.writeCharSequenceArray(this.mAutofillOptions);
        }
    }

    public static void writeToParcel(Parcel parcel, ViewNode viewNode, int n) {
        if (viewNode == null) {
            parcel.writeLong(0L);
        } else {
            viewNode.writeSelfToParcel(parcel, n);
        }
    }

    @Override
    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    @Override
    public AutofillId getAutofillId() {
        return this.mAutofillId;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return this.mAutofillOptions;
    }

    @Override
    public int getAutofillType() {
        return this.mAutofillType;
    }

    @Override
    public AutofillValue getAutofillValue() {
        return this.mAutofillValue;
    }

    @Override
    public String getClassName() {
        return this.mClassName;
    }

    @Override
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @Override
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override
    public int getHeight() {
        return this.mHeight;
    }

    @Override
    public String getHint() {
        Object object = this.mText;
        object = object != null ? ((ViewNodeText)object).mHint : null;
        return object;
    }

    @Override
    public int getId() {
        return this.mId;
    }

    @Override
    public String getIdEntry() {
        return this.mIdEntry;
    }

    @Override
    public String getIdPackage() {
        return this.mIdPackage;
    }

    @Override
    public String getIdType() {
        return this.mIdType;
    }

    @Override
    public int getInputType() {
        return this.mInputType;
    }

    @Override
    public int getLeft() {
        return this.mX;
    }

    @Override
    public LocaleList getLocaleList() {
        return this.mLocaleList;
    }

    @Override
    public int getMaxTextEms() {
        return this.mMaxEms;
    }

    @Override
    public int getMaxTextLength() {
        return this.mMaxLength;
    }

    @Override
    public int getMinTextEms() {
        return this.mMinEms;
    }

    public AutofillId getParentAutofillId() {
        return this.mParentAutofillId;
    }

    @Override
    public int getScrollX() {
        return this.mScrollX;
    }

    @Override
    public int getScrollY() {
        return this.mScrollY;
    }

    @Override
    public CharSequence getText() {
        Object object = this.mText;
        object = object != null ? ((ViewNodeText)object).mText : null;
        return object;
    }

    @Override
    public int getTextBackgroundColor() {
        ViewNodeText viewNodeText = this.mText;
        int n = viewNodeText != null ? viewNodeText.mTextBackgroundColor : 1;
        return n;
    }

    @Override
    public int getTextColor() {
        ViewNodeText viewNodeText = this.mText;
        int n = viewNodeText != null ? viewNodeText.mTextColor : 1;
        return n;
    }

    @Override
    public String getTextIdEntry() {
        return this.mTextIdEntry;
    }

    @Override
    public int[] getTextLineBaselines() {
        Object object = this.mText;
        object = object != null ? object.mLineBaselines : null;
        return object;
    }

    @Override
    public int[] getTextLineCharOffsets() {
        Object object = this.mText;
        object = object != null ? object.mLineCharOffsets : null;
        return object;
    }

    @Override
    public int getTextSelectionEnd() {
        ViewNodeText viewNodeText = this.mText;
        int n = viewNodeText != null ? viewNodeText.mTextSelectionEnd : -1;
        return n;
    }

    @Override
    public int getTextSelectionStart() {
        ViewNodeText viewNodeText = this.mText;
        int n = viewNodeText != null ? viewNodeText.mTextSelectionStart : -1;
        return n;
    }

    @Override
    public float getTextSize() {
        ViewNodeText viewNodeText = this.mText;
        float f = viewNodeText != null ? viewNodeText.mTextSize : 0.0f;
        return f;
    }

    @Override
    public int getTextStyle() {
        ViewNodeText viewNodeText = this.mText;
        int n = viewNodeText != null ? viewNodeText.mTextStyle : 0;
        return n;
    }

    @Override
    public int getTop() {
        return this.mY;
    }

    @Override
    public int getVisibility() {
        return (int)(this.mFlags & 12L);
    }

    @Override
    public int getWidth() {
        return this.mWidth;
    }

    @Override
    public boolean isAccessibilityFocused() {
        boolean bl = (this.mFlags & 131072L) != 0L;
        return bl;
    }

    @Override
    public boolean isActivated() {
        boolean bl = (this.mFlags & 0x200000L) != 0L;
        return bl;
    }

    @Override
    public boolean isAssistBlocked() {
        boolean bl = (this.mFlags & 1024L) != 0L;
        return bl;
    }

    @Override
    public boolean isCheckable() {
        boolean bl = (this.mFlags & 262144L) != 0L;
        return bl;
    }

    @Override
    public boolean isChecked() {
        boolean bl = (this.mFlags & 524288L) != 0L;
        return bl;
    }

    @Override
    public boolean isClickable() {
        boolean bl = (this.mFlags & 4096L) != 0L;
        return bl;
    }

    @Override
    public boolean isContextClickable() {
        boolean bl = (this.mFlags & 16384L) != 0L;
        return bl;
    }

    @Override
    public boolean isEnabled() {
        boolean bl = (this.mFlags & 2048L) == 0L;
        return bl;
    }

    @Override
    public boolean isFocusable() {
        boolean bl = (this.mFlags & 32768L) != 0L;
        return bl;
    }

    @Override
    public boolean isFocused() {
        boolean bl = (this.mFlags & 65536L) != 0L;
        return bl;
    }

    @Override
    public boolean isLongClickable() {
        boolean bl = (this.mFlags & 8192L) != 0L;
        return bl;
    }

    @Override
    public boolean isOpaque() {
        boolean bl = (this.mFlags & 0x400000L) != 0L;
        return bl;
    }

    @Override
    public boolean isSelected() {
        boolean bl = (this.mFlags & 0x100000L) != 0L;
        return bl;
    }

    static final class ViewNodeText {
        String mHint;
        int[] mLineBaselines;
        int[] mLineCharOffsets;
        CharSequence mText;
        int mTextBackgroundColor = 1;
        int mTextColor = 1;
        int mTextSelectionEnd;
        int mTextSelectionStart;
        float mTextSize;
        int mTextStyle;

        ViewNodeText() {
        }

        ViewNodeText(Parcel parcel, boolean bl) {
            this.mText = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mTextSize = parcel.readFloat();
            this.mTextStyle = parcel.readInt();
            this.mTextColor = parcel.readInt();
            if (!bl) {
                this.mTextBackgroundColor = parcel.readInt();
                this.mTextSelectionStart = parcel.readInt();
                this.mTextSelectionEnd = parcel.readInt();
                this.mLineCharOffsets = parcel.createIntArray();
                this.mLineBaselines = parcel.createIntArray();
                this.mHint = parcel.readString();
            }
        }

        boolean isSimple() {
            int n = this.mTextBackgroundColor;
            boolean bl = true;
            if (n != 1 || this.mTextSelectionStart != 0 || this.mTextSelectionEnd != 0 || this.mLineCharOffsets != null || this.mLineBaselines != null || this.mHint != null) {
                bl = false;
            }
            return bl;
        }

        void writeToParcel(Parcel parcel, boolean bl) {
            TextUtils.writeToParcel(this.mText, parcel, 0);
            parcel.writeFloat(this.mTextSize);
            parcel.writeInt(this.mTextStyle);
            parcel.writeInt(this.mTextColor);
            if (!bl) {
                parcel.writeInt(this.mTextBackgroundColor);
                parcel.writeInt(this.mTextSelectionStart);
                parcel.writeInt(this.mTextSelectionEnd);
                parcel.writeIntArray(this.mLineCharOffsets);
                parcel.writeIntArray(this.mLineBaselines);
                parcel.writeString(this.mHint);
            }
        }
    }

    public static final class ViewStructureImpl
    extends ViewStructure {
        final ViewNode mNode = new ViewNode();

        public ViewStructureImpl(View object) {
            this.mNode.mAutofillId = Preconditions.checkNotNull(object).getAutofillId();
            object = ((View)object).getParent();
            if (object instanceof View) {
                this.mNode.mParentAutofillId = ((View)object).getAutofillId();
            }
        }

        public ViewStructureImpl(AutofillId autofillId, long l, int n) {
            this.mNode.mParentAutofillId = Preconditions.checkNotNull(autofillId);
            this.mNode.mAutofillId = new AutofillId(autofillId, l, n);
        }

        private ViewNodeText getNodeText() {
            if (this.mNode.mText != null) {
                return this.mNode.mText;
            }
            this.mNode.mText = new ViewNodeText();
            return this.mNode.mText;
        }

        @Override
        public int addChildCount(int n) {
            Log.w(TAG, "addChildCount() is not supported");
            return 0;
        }

        @Override
        public void asyncCommit() {
            Log.w(TAG, "asyncCommit() is not supported");
        }

        @Override
        public ViewStructure asyncNewChild(int n) {
            Log.w(TAG, "asyncNewChild() is not supported");
            return null;
        }

        @Override
        public AutofillId getAutofillId() {
            return this.mNode.mAutofillId;
        }

        @Override
        public int getChildCount() {
            Log.w(TAG, "getChildCount() is not supported");
            return 0;
        }

        @Override
        public Bundle getExtras() {
            if (this.mNode.mExtras != null) {
                return this.mNode.mExtras;
            }
            this.mNode.mExtras = new Bundle();
            return this.mNode.mExtras;
        }

        @Override
        public CharSequence getHint() {
            return this.mNode.getHint();
        }

        public ViewNode getNode() {
            return this.mNode;
        }

        @Override
        public Rect getTempRect() {
            Log.w(TAG, "getTempRect() is not supported");
            return null;
        }

        @Override
        public CharSequence getText() {
            return this.mNode.getText();
        }

        @Override
        public int getTextSelectionEnd() {
            return this.mNode.getTextSelectionEnd();
        }

        @Override
        public int getTextSelectionStart() {
            return this.mNode.getTextSelectionStart();
        }

        @Override
        public boolean hasExtras() {
            boolean bl = this.mNode.mExtras != null;
            return bl;
        }

        @Override
        public ViewStructure newChild(int n) {
            Log.w(TAG, "newChild() is not supported");
            return null;
        }

        @Override
        public ViewStructure.HtmlInfo.Builder newHtmlInfoBuilder(String string2) {
            Log.w(TAG, "newHtmlInfoBuilder() is not supported");
            return null;
        }

        @Override
        public void setAccessibilityFocused(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 131072L : 0L;
            viewNode.mFlags = l & -131073L | l2;
        }

        @Override
        public void setActivated(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 0x200000L : 0L;
            viewNode.mFlags = l & -2097153L | l2;
        }

        @Override
        public void setAlpha(float f) {
            Log.w(TAG, "setAlpha() is not supported");
        }

        @Override
        public void setAssistBlocked(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 1024L : 0L;
            viewNode.mFlags = l & -1025L | l2;
        }

        @Override
        public void setAutofillHints(String[] arrstring) {
            this.mNode.mAutofillHints = arrstring;
        }

        @Override
        public void setAutofillId(AutofillId autofillId) {
            this.mNode.mAutofillId = Preconditions.checkNotNull(autofillId);
        }

        @Override
        public void setAutofillId(AutofillId autofillId, int n) {
            this.mNode.mParentAutofillId = Preconditions.checkNotNull(autofillId);
            this.mNode.mAutofillId = new AutofillId(autofillId, n);
        }

        @Override
        public void setAutofillOptions(CharSequence[] arrcharSequence) {
            this.mNode.mAutofillOptions = arrcharSequence;
        }

        @Override
        public void setAutofillType(int n) {
            this.mNode.mAutofillType = n;
        }

        @Override
        public void setAutofillValue(AutofillValue autofillValue) {
            this.mNode.mAutofillValue = autofillValue;
        }

        @Override
        public void setCheckable(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 262144L : 0L;
            viewNode.mFlags = l & -262145L | l2;
        }

        @Override
        public void setChecked(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 524288L : 0L;
            viewNode.mFlags = l & -524289L | l2;
        }

        @Override
        public void setChildCount(int n) {
            Log.w(TAG, "setChildCount() is not supported");
        }

        @Override
        public void setClassName(String string2) {
            this.mNode.mClassName = string2;
        }

        @Override
        public void setClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 4096L : 0L;
            viewNode.mFlags = l & -4097L | l2;
        }

        @Override
        public void setContentDescription(CharSequence charSequence) {
            this.mNode.mContentDescription = charSequence;
        }

        @Override
        public void setContextClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 16384L : 0L;
            viewNode.mFlags = l & -16385L | l2;
        }

        @Override
        public void setDataIsSensitive(boolean bl) {
            Log.w(TAG, "setDataIsSensitive() is not supported");
        }

        @Override
        public void setDimens(int n, int n2, int n3, int n4, int n5, int n6) {
            this.mNode.mX = n;
            this.mNode.mY = n2;
            this.mNode.mScrollX = n3;
            this.mNode.mScrollY = n4;
            this.mNode.mWidth = n5;
            this.mNode.mHeight = n6;
        }

        @Override
        public void setElevation(float f) {
            Log.w(TAG, "setElevation() is not supported");
        }

        @Override
        public void setEnabled(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 0L : 2048L;
            viewNode.mFlags = l & -2049L | l2;
        }

        @Override
        public void setFocusable(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 32768L : 0L;
            viewNode.mFlags = l & -32769L | l2;
        }

        @Override
        public void setFocused(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 65536L : 0L;
            viewNode.mFlags = l & -65537L | l2;
        }

        @Override
        public void setHint(CharSequence charSequence) {
            ViewNodeText viewNodeText = this.getNodeText();
            charSequence = charSequence != null ? charSequence.toString() : null;
            viewNodeText.mHint = charSequence;
        }

        @Override
        public void setHtmlInfo(ViewStructure.HtmlInfo htmlInfo) {
            Log.w(TAG, "setHtmlInfo() is not supported");
        }

        @Override
        public void setId(int n, String string2, String string3, String string4) {
            this.mNode.mId = n;
            this.mNode.mIdPackage = string2;
            this.mNode.mIdType = string3;
            this.mNode.mIdEntry = string4;
        }

        @Override
        public void setInputType(int n) {
            this.mNode.mInputType = n;
        }

        @Override
        public void setLocaleList(LocaleList localeList) {
            this.mNode.mLocaleList = localeList;
        }

        @Override
        public void setLongClickable(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 8192L : 0L;
            viewNode.mFlags = l & -8193L | l2;
        }

        @Override
        public void setMaxTextEms(int n) {
            this.mNode.mMaxEms = n;
        }

        @Override
        public void setMaxTextLength(int n) {
            this.mNode.mMaxLength = n;
        }

        @Override
        public void setMinTextEms(int n) {
            this.mNode.mMinEms = n;
        }

        @Override
        public void setOpaque(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 0x400000L : 0L;
            viewNode.mFlags = l & -4194305L | l2;
        }

        @Override
        public void setSelected(boolean bl) {
            ViewNode viewNode = this.mNode;
            long l = viewNode.mFlags;
            long l2 = bl ? 0x100000L : 0L;
            viewNode.mFlags = l & -1048577L | l2;
        }

        @Override
        public void setText(CharSequence charSequence) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            viewNodeText.mTextSelectionEnd = -1;
            viewNodeText.mTextSelectionStart = -1;
        }

        @Override
        public void setText(CharSequence charSequence, int n, int n2) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mText = TextUtils.trimNoCopySpans(charSequence);
            viewNodeText.mTextSelectionStart = n;
            viewNodeText.mTextSelectionEnd = n2;
        }

        @Override
        public void setTextIdEntry(String string2) {
            this.mNode.mTextIdEntry = Preconditions.checkNotNull(string2);
        }

        @Override
        public void setTextLines(int[] arrn, int[] arrn2) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mLineCharOffsets = arrn;
            viewNodeText.mLineBaselines = arrn2;
        }

        @Override
        public void setTextStyle(float f, int n, int n2, int n3) {
            ViewNodeText viewNodeText = this.getNodeText();
            viewNodeText.mTextColor = n;
            viewNodeText.mTextBackgroundColor = n2;
            viewNodeText.mTextSize = f;
            viewNodeText.mTextStyle = n3;
        }

        @Override
        public void setTransformation(Matrix matrix) {
            Log.w(TAG, "setTransformation() is not supported");
        }

        @Override
        public void setVisibility(int n) {
            ViewNode viewNode = this.mNode;
            viewNode.mFlags = viewNode.mFlags & -13L | (long)n & 12L;
        }

        @Override
        public void setWebDomain(String string2) {
            Log.w(TAG, "setWebDomain() is not supported");
        }
    }

}

