/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Editable;
import android.text.Layout;
import android.text.PackedIntVector;
import android.text.PackedObjectVector;
import android.text.PrecomputedText;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.Spanned;
import android.text.StaticLayout;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ReplacementSpan;
import android.text.style.UpdateLayout;
import android.text.style.WrapTogetherSpan;
import android.util.ArraySet;
import android.util.Pools;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.ref.WeakReference;

public class DynamicLayout
extends Layout {
    private static final int BLOCK_MINIMUM_CHARACTER_LENGTH = 400;
    private static final int COLUMNS_ELLIPSIZE = 7;
    private static final int COLUMNS_NORMAL = 5;
    private static final int DESCENT = 2;
    private static final int DIR = 0;
    private static final int DIR_SHIFT = 30;
    private static final int ELLIPSIS_COUNT = 6;
    private static final int ELLIPSIS_START = 5;
    private static final int ELLIPSIS_UNDEFINED = Integer.MIN_VALUE;
    private static final int EXTRA = 3;
    private static final int HYPHEN = 4;
    private static final int HYPHEN_MASK = 255;
    public static final int INVALID_BLOCK_INDEX = -1;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM = 4;
    private static final int MAY_PROTRUDE_FROM_TOP_OR_BOTTOM_MASK = 256;
    private static final int PRIORITY = 128;
    private static final int START = 0;
    private static final int START_MASK = 536870911;
    private static final int TAB = 0;
    private static final int TAB_MASK = 536870912;
    private static final int TOP = 1;
    private static StaticLayout.Builder sBuilder;
    private static final Object[] sLock;
    @UnsupportedAppUsage
    private static StaticLayout sStaticLayout;
    private CharSequence mBase;
    private int[] mBlockEndLines;
    private int[] mBlockIndices;
    private ArraySet<Integer> mBlocksAlwaysNeedToBeRedrawn;
    private int mBottomPadding;
    private int mBreakStrategy;
    private CharSequence mDisplay;
    private boolean mEllipsize;
    private TextUtils.TruncateAt mEllipsizeAt;
    private int mEllipsizedWidth;
    private boolean mFallbackLineSpacing;
    private int mHyphenationFrequency;
    private boolean mIncludePad;
    private int mIndexFirstChangedBlock;
    private PackedIntVector mInts;
    private int mJustificationMode;
    private int mNumberOfBlocks;
    private PackedObjectVector<Layout.Directions> mObjects;
    private Rect mTempRect = new Rect();
    private int mTopPadding;
    private ChangeWatcher mWatcher;

    static {
        sStaticLayout = null;
        sBuilder = null;
        sLock = new Object[0];
    }

    private DynamicLayout(Builder builder) {
        super(DynamicLayout.createEllipsizer(builder.mEllipsize, builder.mDisplay), builder.mPaint, builder.mWidth, builder.mAlignment, builder.mTextDir, builder.mSpacingMult, builder.mSpacingAdd);
        this.mDisplay = builder.mDisplay;
        this.mIncludePad = builder.mIncludePad;
        this.mBreakStrategy = builder.mBreakStrategy;
        this.mJustificationMode = builder.mJustificationMode;
        this.mHyphenationFrequency = builder.mHyphenationFrequency;
        this.generate(builder);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, boolean bl) {
        this(charSequence, charSequence, textPaint, n, alignment, f, f2, bl);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, CharSequence charSequence2, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, boolean bl) {
        this(charSequence, charSequence2, textPaint, n, alignment, f, f2, bl, null, 0);
    }

    @Deprecated
    public DynamicLayout(CharSequence charSequence, CharSequence charSequence2, TextPaint textPaint, int n, Layout.Alignment alignment, float f, float f2, boolean bl, TextUtils.TruncateAt truncateAt, int n2) {
        this(charSequence, charSequence2, textPaint, n, alignment, TextDirectionHeuristics.FIRSTSTRONG_LTR, f, f2, bl, 0, 0, 0, truncateAt, n2);
    }

    @Deprecated
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public DynamicLayout(CharSequence object, CharSequence charSequence, TextPaint textPaint, int n, Layout.Alignment alignment, TextDirectionHeuristic textDirectionHeuristic, float f, float f2, boolean bl, int n2, int n3, int n4, TextUtils.TruncateAt truncateAt, int n5) {
        super(DynamicLayout.createEllipsizer(truncateAt, charSequence), textPaint, n, alignment, textDirectionHeuristic, f, f2);
        object = Builder.obtain((CharSequence)object, textPaint, n).setAlignment(alignment).setTextDirection(textDirectionHeuristic).setLineSpacing(f2, f).setEllipsizedWidth(n5).setEllipsize(truncateAt);
        this.mDisplay = charSequence;
        this.mIncludePad = bl;
        this.mBreakStrategy = n2;
        this.mJustificationMode = n4;
        this.mHyphenationFrequency = n3;
        this.generate((Builder)object);
        Builder.recycle((Builder)object);
    }

    private void addBlockAtOffset(int n) {
        n = this.getLineForOffset(n);
        int[] arrn = this.mBlockEndLines;
        if (arrn == null) {
            arrn = this.mBlockEndLines = ArrayUtils.newUnpaddedIntArray(1);
            int n2 = this.mNumberOfBlocks++;
            arrn[n2] = n;
            this.updateAlwaysNeedsToBeRedrawn(n2);
            return;
        }
        int n3 = this.mNumberOfBlocks;
        if (n > arrn[n3 - 1]) {
            this.mBlockEndLines = GrowingArrayUtils.append(arrn, n3, n);
            this.updateAlwaysNeedsToBeRedrawn(this.mNumberOfBlocks);
            ++this.mNumberOfBlocks;
        }
    }

    private boolean contentMayProtrudeFromLineTopOrBottom(CharSequence object, int n, int n2) {
        boolean bl = object instanceof Spanned;
        boolean bl2 = true;
        if (bl && ((Spanned)object).getSpans(n, n2, ReplacementSpan.class).length > 0) {
            return true;
        }
        TextPaint textPaint = this.getPaint();
        if (object instanceof PrecomputedText) {
            ((PrecomputedText)object).getBounds(n, n2, this.mTempRect);
        } else {
            textPaint.getTextBounds((CharSequence)object, n, n2, this.mTempRect);
        }
        object = textPaint.getFontMetricsInt();
        bl = bl2;
        if (this.mTempRect.top >= ((Paint.FontMetricsInt)object).top) {
            bl = this.mTempRect.bottom > ((Paint.FontMetricsInt)object).bottom ? bl2 : false;
        }
        return bl;
    }

    private void createBlocks() {
        int n = 400;
        this.mNumberOfBlocks = 0;
        CharSequence charSequence = this.mDisplay;
        do {
            if ((n = TextUtils.indexOf(charSequence, '\n', n)) < 0) {
                this.addBlockAtOffset(charSequence.length());
                this.mBlockIndices = new int[this.mBlockEndLines.length];
                for (n = 0; n < this.mBlockEndLines.length; ++n) {
                    this.mBlockIndices[n] = -1;
                }
                return;
            }
            this.addBlockAtOffset(n);
            n += 400;
        } while (true);
    }

    private static CharSequence createEllipsizer(TextUtils.TruncateAt truncateAt, CharSequence charSequence) {
        if (truncateAt == null) {
            return charSequence;
        }
        if (charSequence instanceof Spanned) {
            return new Layout.SpannedEllipsizer(charSequence);
        }
        return new Layout.Ellipsizer(charSequence);
    }

    private void generate(Builder object) {
        Object[] arrobject;
        this.mBase = ((Builder)object).mBase;
        this.mFallbackLineSpacing = ((Builder)object).mFallbackLineSpacing;
        if (((Builder)object).mEllipsize != null) {
            this.mInts = new PackedIntVector(7);
            this.mEllipsizedWidth = ((Builder)object).mEllipsizedWidth;
            this.mEllipsizeAt = ((Builder)object).mEllipsize;
            arrobject = (int[])this.getText();
            arrobject.mLayout = this;
            arrobject.mWidth = ((Builder)object).mEllipsizedWidth;
            arrobject.mMethod = ((Builder)object).mEllipsize;
            this.mEllipsize = true;
        } else {
            this.mInts = new PackedIntVector(5);
            this.mEllipsizedWidth = ((Builder)object).mWidth;
            this.mEllipsizeAt = null;
        }
        this.mObjects = new PackedObjectVector(1);
        if (((Builder)object).mEllipsize != null) {
            arrobject = new int[7];
            arrobject[5] = Integer.MIN_VALUE;
        } else {
            arrobject = new int[5];
        }
        Layout.Directions directions = DIRS_ALL_LEFT_TO_RIGHT;
        Paint.FontMetricsInt fontMetricsInt = ((Builder)object).mFontMetricsInt;
        ((Builder)object).mPaint.getFontMetricsInt(fontMetricsInt);
        int n = fontMetricsInt.ascent;
        int n2 = fontMetricsInt.descent;
        arrobject[0] = 1073741824;
        arrobject[1] = 0;
        arrobject[2] = n2;
        this.mInts.insertAt(0, (int[])arrobject);
        arrobject[1] = n2 - n;
        this.mInts.insertAt(1, (int[])arrobject);
        this.mObjects.insertAt(0, new Layout.Directions[]{directions});
        n = this.mBase.length();
        this.reflow(this.mBase, 0, 0, n);
        if (this.mBase instanceof Spannable) {
            if (this.mWatcher == null) {
                this.mWatcher = new ChangeWatcher(this);
            }
            object = (Spannable)this.mBase;
            arrobject = object.getSpans(0, n, ChangeWatcher.class);
            for (n2 = 0; n2 < arrobject.length; ++n2) {
                object.removeSpan(arrobject[n2]);
            }
            object.setSpan(this.mWatcher, 0, n, 8388626);
        }
    }

    private boolean getContentMayProtrudeFromTopOrBottom(int n) {
        boolean bl = (this.mInts.getValue(n, 4) & 256) != 0;
        return bl;
    }

    private void updateAlwaysNeedsToBeRedrawn(int n) {
        int n2 = this.mBlockEndLines[n];
        for (int i = n == 0 ? 0 : this.mBlockEndLines[n - 1] + 1; i <= n2; ++i) {
            if (!this.getContentMayProtrudeFromTopOrBottom(i)) continue;
            if (this.mBlocksAlwaysNeedToBeRedrawn == null) {
                this.mBlocksAlwaysNeedToBeRedrawn = new ArraySet();
            }
            this.mBlocksAlwaysNeedToBeRedrawn.add(n);
            return;
        }
        ArraySet<Integer> arraySet = this.mBlocksAlwaysNeedToBeRedrawn;
        if (arraySet != null) {
            arraySet.remove(n);
        }
    }

    @UnsupportedAppUsage
    public int[] getBlockEndLines() {
        return this.mBlockEndLines;
    }

    public int getBlockIndex(int n) {
        return this.mBlockIndices[n];
    }

    @UnsupportedAppUsage
    public int[] getBlockIndices() {
        return this.mBlockIndices;
    }

    public ArraySet<Integer> getBlocksAlwaysNeedToBeRedrawn() {
        return this.mBlocksAlwaysNeedToBeRedrawn;
    }

    @Override
    public int getBottomPadding() {
        return this.mBottomPadding;
    }

    @Override
    public int getEllipsisCount(int n) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(n, 6);
    }

    @Override
    public int getEllipsisStart(int n) {
        if (this.mEllipsizeAt == null) {
            return 0;
        }
        return this.mInts.getValue(n, 5);
    }

    @Override
    public int getEllipsizedWidth() {
        return this.mEllipsizedWidth;
    }

    @Override
    public int getEndHyphenEdit(int n) {
        return StaticLayout.unpackEndHyphenEdit(this.mInts.getValue(n, 4) & 255);
    }

    @UnsupportedAppUsage
    public int getIndexFirstChangedBlock() {
        return this.mIndexFirstChangedBlock;
    }

    @Override
    public boolean getLineContainsTab(int n) {
        PackedIntVector packedIntVector = this.mInts;
        boolean bl = false;
        if ((packedIntVector.getValue(n, 0) & 536870912) != 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public int getLineCount() {
        return this.mInts.size() - 1;
    }

    @Override
    public int getLineDescent(int n) {
        return this.mInts.getValue(n, 2);
    }

    @Override
    public final Layout.Directions getLineDirections(int n) {
        return this.mObjects.getValue(n, 0);
    }

    @Override
    public int getLineExtra(int n) {
        return this.mInts.getValue(n, 3);
    }

    @Override
    public int getLineStart(int n) {
        return this.mInts.getValue(n, 0) & 536870911;
    }

    @Override
    public int getLineTop(int n) {
        return this.mInts.getValue(n, 1);
    }

    @UnsupportedAppUsage
    public int getNumberOfBlocks() {
        return this.mNumberOfBlocks;
    }

    @Override
    public int getParagraphDirection(int n) {
        return this.mInts.getValue(n, 0) >> 30;
    }

    @Override
    public int getStartHyphenEdit(int n) {
        return StaticLayout.unpackStartHyphenEdit(this.mInts.getValue(n, 4) & 255);
    }

    @Override
    public int getTopPadding() {
        return this.mTopPadding;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void reflow(CharSequence object, int n, int n2, int n3) {
        Object object2;
        int n4;
        Object object3;
        int n5;
        int n6;
        if (object != this.mBase) {
            return;
        }
        CharSequence charSequence = this.mDisplay;
        int n7 = charSequence.length();
        int n8 = TextUtils.lastIndexOf(charSequence, '\n', n - 1);
        n8 = n8 < 0 ? 0 : ++n8;
        int n9 = n - n8;
        n8 = n3 + n9;
        n3 = TextUtils.indexOf(charSequence, '\n', (n -= n9) + n8);
        n3 = n3 < 0 ? n7 : ++n3;
        int n10 = n3 - (n + n8);
        n3 = n2 + n9 + n10;
        n8 += n10;
        if (!(charSequence instanceof Spanned)) {
            n2 = n;
            n = n8;
            n6 = n3;
            n3 = n2;
        } else {
            object3 = (Spanned)charSequence;
            n2 = n8;
            n8 = n;
            do {
                n10 = 0;
                object = object3.getSpans(n8, n8 + n2, WrapTogetherSpan.class);
                n = n2;
                n2 = n3;
                n3 = n8;
                for (n5 = 0; n5 < ((Object)object).length; ++n5) {
                    n4 = object3.getSpanStart(object[n5]);
                    object2 = object3.getSpanEnd(object[n5]);
                    n6 = n3;
                    n9 = n2;
                    n8 = n;
                    if (n4 < n3) {
                        n10 = 1;
                        n6 = n3 - n4;
                        n9 = n2 + n6;
                        n8 = n + n6;
                        n6 = n3 - n6;
                    }
                    n2 = n9;
                    n = n8;
                    if (object2 > n6 + n8) {
                        n = object2 - (n6 + n8);
                        n2 = n9 + n;
                        n = n8 + n;
                        n10 = 1;
                    }
                    n3 = n6;
                }
                if (n10 == 0) {
                    n6 = n2;
                    break;
                }
                n8 = n3;
                n3 = n2;
                n2 = n;
            } while (true);
        }
        int n11 = this.getLineForOffset(n3);
        n4 = this.getLineTop(n11);
        n9 = this.getLineForOffset(n3 + n6);
        if (n3 + n == n7) {
            n9 = this.getLineCount();
        }
        int n12 = this.getLineTop(n9);
        n5 = n9 == this.getLineCount() ? 1 : 0;
        Object object4 = sLock;
        // MONITORENTER : object4
        object3 = sStaticLayout;
        object = sBuilder;
        sStaticLayout = null;
        sBuilder = null;
        // MONITOREXIT : object4
        if (object3 == null) {
            object3 = new StaticLayout(null);
            object = StaticLayout.Builder.obtain(charSequence, n3, n3 + n, this.getPaint(), this.getWidth());
        }
        object4 = ((StaticLayout.Builder)object).setText(charSequence, n3, n3 + n).setPaint(this.getPaint()).setWidth(this.getWidth()).setTextDirection(this.getTextDirectionHeuristic()).setLineSpacing(this.getSpacingAdd(), this.getSpacingMultiplier()).setUseLineSpacingFromFallbacks(this.mFallbackLineSpacing).setEllipsizedWidth(this.mEllipsizedWidth).setEllipsize(this.mEllipsizeAt).setBreakStrategy(this.mBreakStrategy).setHyphenationFrequency(this.mHyphenationFrequency).setJustificationMode(this.mJustificationMode);
        boolean bl = n5 == 0;
        ((StaticLayout.Builder)object4).setAddLastLineLineSpacing(bl);
        ((StaticLayout)object3).generate((StaticLayout.Builder)object, false, true);
        n10 = ((StaticLayout)object3).getLineCount();
        if (n3 + n != n7 && ((StaticLayout)object3).getLineStart(n10 - 1) == n3 + n) {
            --n10;
        }
        this.mInts.deleteAt(n11, n9 - n11);
        this.mObjects.deleteAt(n11, n9 - n11);
        n7 = ((StaticLayout)object3).getLineTop(n10);
        object2 = 0;
        n8 = n7;
        n2 = object2;
        if (this.mIncludePad) {
            n8 = n7;
            n2 = object2;
            if (n11 == 0) {
                this.mTopPadding = n2 = ((StaticLayout)object3).getTopPadding();
                n8 = n7 - n2;
            }
        }
        if (this.mIncludePad && n5 != 0) {
            this.mBottomPadding = n7 = ((StaticLayout)object3).getBottomPadding();
            n5 = n7;
            n8 += n7;
        } else {
            n5 = 0;
        }
        this.mInts.adjustValuesBelow(n11, 0, n - n6);
        this.mInts.adjustValuesBelow(n11, 1, n4 - n12 + n8);
        if (this.mEllipsize) {
            object4 = new int[7];
            object4[5] = Integer.MIN_VALUE;
        } else {
            object4 = new int[5];
        }
        Layout.Directions[] arrdirections = new Layout.Directions[1];
        n6 = 0;
        do {
            if (n6 >= n10) {
                this.updateBlocks(n11, n9 - 1, n10);
                ((StaticLayout.Builder)object).finish();
                object4 = sLock;
                // MONITORENTER : object4
                sStaticLayout = object3;
                sBuilder = object;
                // MONITOREXIT : object4
                return;
            }
            n12 = ((StaticLayout)object3).getLineStart(n6);
            object4[0] = n12;
            object4[0] = object4[0] | ((StaticLayout)object3).getParagraphDirection(n6) << 30;
            object2 = object4[0];
            n7 = ((StaticLayout)object3).getLineContainsTab(n6) ? 536870912 : 0;
            object4[0] = object2 | n7;
            n7 = object2 = ((StaticLayout)object3).getLineTop(n6) + n4;
            if (n6 > 0) {
                n7 = object2 - n2;
            }
            object4[1] = n7;
            n7 = object2 = ((StaticLayout)object3).getLineDescent(n6);
            if (n6 == n10 - 1) {
                n7 = object2 + n5;
            }
            object4[2] = n7;
            object4[3] = ((StaticLayout)object3).getLineExtra(n6);
            arrdirections[0] = ((StaticLayout)object3).getLineDirections(n6);
            n7 = n6 == n10 - 1 ? n3 + n : ((StaticLayout)object3).getLineStart(n6 + 1);
            object4[4] = StaticLayout.packHyphenEdit(((StaticLayout)object3).getStartHyphenEdit(n6), ((StaticLayout)object3).getEndHyphenEdit(n6));
            object2 = object4[4];
            n7 = this.contentMayProtrudeFromLineTopOrBottom(charSequence, n12, n7) ? 256 : 0;
            object4[4] = object2 | n7;
            if (this.mEllipsize) {
                object4[5] = ((StaticLayout)object3).getEllipsisStart(n6);
                object4[6] = ((StaticLayout)object3).getEllipsisCount(n6);
            }
            this.mInts.insertAt(n11 + n6, (int[])object4);
            this.mObjects.insertAt(n11 + n6, arrdirections);
            ++n6;
        } while (true);
    }

    public void setBlockIndex(int n, int n2) {
        this.mBlockIndices[n] = n2;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void setBlocksDataForTest(int[] object, int[] arrn, int n, int n2) {
        this.mBlockEndLines = new int[((int[])object).length];
        this.mBlockIndices = new int[arrn.length];
        System.arraycopy(object, 0, this.mBlockEndLines, 0, ((int[])object).length);
        System.arraycopy(arrn, 0, this.mBlockIndices, 0, arrn.length);
        this.mNumberOfBlocks = n;
        while (this.mInts.size() < n2) {
            object = this.mInts;
            ((PackedIntVector)object).insertAt(((PackedIntVector)object).size(), new int[5]);
        }
    }

    @UnsupportedAppUsage
    public void setIndexFirstChangedBlock(int n) {
        this.mIndexFirstChangedBlock = n;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void updateBlocks(int n, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        int n7;
        Object object;
        if (this.mBlockEndLines == null) {
            this.createBlocks();
            return;
        }
        int n8 = -1;
        int n9 = -1;
        int n10 = 0;
        do {
            n7 = n8;
            if (n10 >= this.mNumberOfBlocks) break;
            if (this.mBlockEndLines[n10] >= n) {
                n7 = n10;
                break;
            }
            ++n10;
        } while (true);
        n10 = n7;
        do {
            n5 = n9;
            if (n10 >= this.mNumberOfBlocks) break;
            if (this.mBlockEndLines[n10] >= n2) {
                n5 = n10;
                break;
            }
            ++n10;
        } while (true);
        Object object2 = this.mBlockEndLines;
        int n11 = object2[n5];
        n10 = n7 == 0 ? 0 : object2[n7 - 1] + 1;
        n8 = n > n10 ? 1 : 0;
        boolean bl = n3 > 0;
        boolean bl2 = n2 < this.mBlockEndLines[n5];
        n9 = 0;
        if (n8 != 0) {
            n9 = 0 + 1;
        }
        n10 = n9;
        if (bl) {
            n10 = n9 + 1;
        }
        n9 = n10;
        if (bl2) {
            n9 = n10 + 1;
        }
        if ((n6 = (n10 = this.mNumberOfBlocks) + n9 - (n4 = n5 - n7 + 1)) == 0) {
            this.mBlockEndLines[0] = 0;
            this.mBlockIndices[0] = -1;
            this.mNumberOfBlocks = 1;
            return;
        }
        object2 = this.mBlockEndLines;
        if (n6 > ((int[])object2).length) {
            object = ArrayUtils.newUnpaddedIntArray(Math.max(((int[])object2).length * 2, n6));
            object2 = new int[((int[])object).length];
            System.arraycopy(this.mBlockEndLines, 0, object, 0, n7);
            System.arraycopy(this.mBlockIndices, 0, object2, 0, n7);
            System.arraycopy(this.mBlockEndLines, n5 + 1, object, n7 + n9, this.mNumberOfBlocks - n5 - 1);
            System.arraycopy(this.mBlockIndices, n5 + 1, object2, n7 + n9, this.mNumberOfBlocks - n5 - 1);
            this.mBlockEndLines = object;
            this.mBlockIndices = object2;
        } else if (n9 + n4 != 0) {
            System.arraycopy(object2, n5 + 1, object2, n7 + n9, n10 - n5 - 1);
            object2 = this.mBlockIndices;
            System.arraycopy(object2, n5 + 1, object2, n7 + n9, this.mNumberOfBlocks - n5 - 1);
        }
        if (n9 + n4 != 0 && this.mBlocksAlwaysNeedToBeRedrawn != null) {
            object = new ArraySet();
            for (n10 = 0; n10 < this.mBlocksAlwaysNeedToBeRedrawn.size(); ++n10) {
                object2 = this.mBlocksAlwaysNeedToBeRedrawn.valueAt(n10);
                if ((Integer)object2 < n7) {
                    ((ArraySet)object).add(object2);
                }
                if ((Integer)object2 <= n5) continue;
                ((ArraySet)object).add((Integer)object2 + (n9 - n4));
            }
            this.mBlocksAlwaysNeedToBeRedrawn = object;
        }
        this.mNumberOfBlocks = n6;
        n5 = n3 - (n2 - n + 1);
        if (n5 != 0) {
            for (n10 = n2 = n7 + n9; n10 < this.mNumberOfBlocks; ++n10) {
                object2 = this.mBlockEndLines;
                object2[n10] = object2[n10] + n5;
            }
        } else {
            n2 = this.mNumberOfBlocks;
        }
        this.mIndexFirstChangedBlock = Math.min(this.mIndexFirstChangedBlock, n2);
        n2 = n7;
        if (n8 != 0) {
            this.mBlockEndLines[n7] = n - 1;
            this.updateAlwaysNeedsToBeRedrawn(n7);
            this.mBlockIndices[n7] = -1;
            n2 = n7 + 1;
        }
        n7 = n2;
        if (bl) {
            this.mBlockEndLines[n2] = n + n3 - 1;
            this.updateAlwaysNeedsToBeRedrawn(n2);
            this.mBlockIndices[n2] = -1;
            n7 = n2 + 1;
        }
        if (bl2) {
            this.mBlockEndLines[n7] = n11 + n5;
            this.updateAlwaysNeedsToBeRedrawn(n7);
            this.mBlockIndices[n7] = -1;
        }
    }

    public static final class Builder {
        private static final Pools.SynchronizedPool<Builder> sPool = new Pools.SynchronizedPool(3);
        private Layout.Alignment mAlignment;
        private CharSequence mBase;
        private int mBreakStrategy;
        private CharSequence mDisplay;
        private TextUtils.TruncateAt mEllipsize;
        private int mEllipsizedWidth;
        private boolean mFallbackLineSpacing;
        private final Paint.FontMetricsInt mFontMetricsInt = new Paint.FontMetricsInt();
        private int mHyphenationFrequency;
        private boolean mIncludePad;
        private int mJustificationMode;
        private TextPaint mPaint;
        private float mSpacingAdd;
        private float mSpacingMult;
        private TextDirectionHeuristic mTextDir;
        private int mWidth;

        private Builder() {
        }

        public static Builder obtain(CharSequence charSequence, TextPaint textPaint, int n) {
            Builder builder;
            Builder builder2 = builder = sPool.acquire();
            if (builder == null) {
                builder2 = new Builder();
            }
            builder2.mBase = charSequence;
            builder2.mDisplay = charSequence;
            builder2.mPaint = textPaint;
            builder2.mWidth = n;
            builder2.mAlignment = Layout.Alignment.ALIGN_NORMAL;
            builder2.mTextDir = TextDirectionHeuristics.FIRSTSTRONG_LTR;
            builder2.mSpacingMult = 1.0f;
            builder2.mSpacingAdd = 0.0f;
            builder2.mIncludePad = true;
            builder2.mFallbackLineSpacing = false;
            builder2.mEllipsizedWidth = n;
            builder2.mEllipsize = null;
            builder2.mBreakStrategy = 0;
            builder2.mHyphenationFrequency = 0;
            builder2.mJustificationMode = 0;
            return builder2;
        }

        private static void recycle(Builder builder) {
            builder.mBase = null;
            builder.mDisplay = null;
            builder.mPaint = null;
            sPool.release(builder);
        }

        public DynamicLayout build() {
            DynamicLayout dynamicLayout = new DynamicLayout(this);
            Builder.recycle(this);
            return dynamicLayout;
        }

        public Builder setAlignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
            return this;
        }

        public Builder setBreakStrategy(int n) {
            this.mBreakStrategy = n;
            return this;
        }

        public Builder setDisplayText(CharSequence charSequence) {
            this.mDisplay = charSequence;
            return this;
        }

        public Builder setEllipsize(TextUtils.TruncateAt truncateAt) {
            this.mEllipsize = truncateAt;
            return this;
        }

        public Builder setEllipsizedWidth(int n) {
            this.mEllipsizedWidth = n;
            return this;
        }

        public Builder setHyphenationFrequency(int n) {
            this.mHyphenationFrequency = n;
            return this;
        }

        public Builder setIncludePad(boolean bl) {
            this.mIncludePad = bl;
            return this;
        }

        public Builder setJustificationMode(int n) {
            this.mJustificationMode = n;
            return this;
        }

        public Builder setLineSpacing(float f, float f2) {
            this.mSpacingAdd = f;
            this.mSpacingMult = f2;
            return this;
        }

        public Builder setTextDirection(TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDir = textDirectionHeuristic;
            return this;
        }

        public Builder setUseLineSpacingFromFallbacks(boolean bl) {
            this.mFallbackLineSpacing = bl;
            return this;
        }
    }

    private static class ChangeWatcher
    implements TextWatcher,
    SpanWatcher {
        private WeakReference<DynamicLayout> mLayout;

        public ChangeWatcher(DynamicLayout dynamicLayout) {
            this.mLayout = new WeakReference<DynamicLayout>(dynamicLayout);
        }

        private void reflow(CharSequence charSequence, int n, int n2, int n3) {
            DynamicLayout dynamicLayout = (DynamicLayout)this.mLayout.get();
            if (dynamicLayout != null) {
                dynamicLayout.reflow(charSequence, n, n2, n3);
            } else if (charSequence instanceof Spannable) {
                ((Spannable)charSequence).removeSpan(this);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        @Override
        public void onSpanAdded(Spannable spannable, Object object, int n, int n2) {
            if (object instanceof UpdateLayout) {
                this.reflow(spannable, n, n2 - n, n2 - n);
            }
        }

        @Override
        public void onSpanChanged(Spannable spannable, Object object, int n, int n2, int n3, int n4) {
            if (object instanceof UpdateLayout) {
                int n5 = n;
                if (n > n2) {
                    n5 = 0;
                }
                this.reflow(spannable, n5, n2 - n5, n2 - n5);
                this.reflow(spannable, n3, n4 - n3, n4 - n3);
            }
        }

        @Override
        public void onSpanRemoved(Spannable spannable, Object object, int n, int n2) {
            if (object instanceof UpdateLayout) {
                this.reflow(spannable, n, n2 - n, n2 - n);
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            this.reflow(charSequence, n, n2, n3);
        }
    }

}

