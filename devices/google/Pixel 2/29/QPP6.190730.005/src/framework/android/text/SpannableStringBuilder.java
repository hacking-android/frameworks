/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.BaseCanvas;
import android.graphics.Paint;
import android.text.Editable;
import android.text.GetChars;
import android.text.GraphicsOperations;
import android.text.InputFilter;
import android.text.NoCopySpan;
import android.text.Selection;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.IdentityHashMap;
import libcore.util.EmptyArray;

public class SpannableStringBuilder
implements CharSequence,
GetChars,
Spannable,
Editable,
Appendable,
GraphicsOperations {
    private static final int END_MASK = 15;
    private static final int MARK = 1;
    private static final InputFilter[] NO_FILTERS = new InputFilter[0];
    private static final int PARAGRAPH = 3;
    private static final int POINT = 2;
    private static final int SPAN_ADDED = 2048;
    private static final int SPAN_END_AT_END = 32768;
    private static final int SPAN_END_AT_START = 16384;
    private static final int SPAN_START_AT_END = 8192;
    private static final int SPAN_START_AT_START = 4096;
    private static final int SPAN_START_END_MASK = 61440;
    private static final int START_MASK = 240;
    private static final int START_SHIFT = 4;
    private static final String TAG = "SpannableStringBuilder";
    @GuardedBy(value={"sCachedIntBuffer"})
    private static final int[][] sCachedIntBuffer = new int[6][0];
    private InputFilter[] mFilters = NO_FILTERS;
    @UnsupportedAppUsage
    private int mGapLength;
    @UnsupportedAppUsage
    private int mGapStart;
    private IdentityHashMap<Object, Integer> mIndexOfSpan;
    private int mLowWaterMark;
    @UnsupportedAppUsage
    private int mSpanCount;
    @UnsupportedAppUsage
    private int[] mSpanEnds;
    @UnsupportedAppUsage
    private int[] mSpanFlags;
    private int mSpanInsertCount;
    private int[] mSpanMax;
    private int[] mSpanOrder;
    @UnsupportedAppUsage
    private int[] mSpanStarts;
    @UnsupportedAppUsage
    private Object[] mSpans;
    @UnsupportedAppUsage
    private char[] mText;
    private int mTextWatcherDepth;

    public SpannableStringBuilder() {
        this("");
    }

    public SpannableStringBuilder(CharSequence charSequence) {
        this(charSequence, 0, charSequence.length());
    }

    public SpannableStringBuilder(CharSequence charSequence, int n, int n2) {
        int n3 = n2 - n;
        if (n3 >= 0) {
            this.mText = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(n3));
            this.mGapStart = n3;
            Object[] arrobject = this.mText;
            this.mGapLength = arrobject.length - n3;
            TextUtils.getChars(charSequence, n, n2, arrobject, 0);
            this.mSpanCount = 0;
            this.mSpanInsertCount = 0;
            this.mSpans = EmptyArray.OBJECT;
            this.mSpanStarts = EmptyArray.INT;
            this.mSpanEnds = EmptyArray.INT;
            this.mSpanFlags = EmptyArray.INT;
            this.mSpanMax = EmptyArray.INT;
            this.mSpanOrder = EmptyArray.INT;
            if (charSequence instanceof Spanned) {
                charSequence = (Spanned)charSequence;
                arrobject = charSequence.getSpans(n, n2, Object.class);
                for (int i = 0; i < arrobject.length; ++i) {
                    if (arrobject[i] instanceof NoCopySpan) continue;
                    int n4 = charSequence.getSpanStart(arrobject[i]) - n;
                    int n5 = charSequence.getSpanEnd(arrobject[i]) - n;
                    int n6 = charSequence.getSpanFlags(arrobject[i]);
                    n3 = n4;
                    if (n4 < 0) {
                        n3 = 0;
                    }
                    n4 = n3 > n2 - n ? n2 - n : n3;
                    n3 = n5;
                    if (n5 < 0) {
                        n3 = 0;
                    }
                    if (n3 > n2 - n) {
                        n3 = n2 - n;
                    }
                    this.setSpan(false, arrobject[i], n4, n3, n6, false);
                }
                this.restoreInvariants();
            }
            return;
        }
        throw new StringIndexOutOfBoundsException();
    }

    private int calcMax(int n) {
        int n2 = 0;
        if ((n & 1) != 0) {
            n2 = this.calcMax(SpannableStringBuilder.leftChild(n));
        }
        int n3 = n2;
        if (n < this.mSpanCount) {
            n3 = n2 = Math.max(n2, this.mSpanEnds[n]);
            if ((n & 1) != 0) {
                n3 = Math.max(n2, this.calcMax(SpannableStringBuilder.rightChild(n)));
            }
        }
        this.mSpanMax[n] = n3;
        return n3;
    }

    private void change(int n, int n2, CharSequence charSequence, int n3, int n4) {
        int n5;
        Object[] arrobject;
        int n6;
        int n7 = n3;
        int n8 = n4;
        int n9 = n2 - n;
        int n10 = n8 - n7;
        int n11 = n10 - n9;
        int n12 = this.mSpanCount;
        int n13 = 0;
        for (int i = n12 - 1; i >= 0; --i) {
            n12 = n6 = this.mSpanStarts[i];
            if (n6 > this.mGapStart) {
                n12 = n6 - this.mGapLength;
            }
            n6 = n5 = this.mSpanEnds[i];
            if (n5 > this.mGapStart) {
                n6 = n5 - this.mGapLength;
            }
            int n14 = n12;
            n5 = n6;
            int n15 = n13;
            if ((this.mSpanFlags[i] & 51) == 51) {
                n15 = this.length();
                n14 = n12;
                if (n12 > n) {
                    n14 = n12;
                    if (n12 <= n2) {
                        n5 = n2;
                        do {
                            n14 = ++n5;
                            if (n5 >= n15) break;
                            if (n5 <= n2 || this.charAt(n5 - 1) != '\n') continue;
                            n14 = n5;
                            break;
                        } while (true);
                    }
                }
                if (n6 > n && n6 <= n2) {
                    for (n5 = n2; n5 < n15 && (n5 <= n2 || this.charAt(n5 - 1) != '\n'); ++n5) {
                    }
                } else {
                    n5 = n6;
                }
                if (n14 == n12 && n5 == n6) {
                    n15 = n13;
                } else {
                    this.setSpan(false, this.mSpans[i], n14, n5, this.mSpanFlags[i], true);
                    n15 = 1;
                }
            }
            n12 = 0;
            if (n14 == n) {
                n12 = 0 | 4096;
            } else if (n14 == n2 + n11) {
                n12 = 0 | 8192;
            }
            if (n5 == n) {
                n6 = n12 | 16384;
            } else {
                n6 = n12;
                if (n5 == n2 + n11) {
                    n6 = n12 | 32768;
                }
            }
            arrobject = this.mSpanFlags;
            arrobject[i] = arrobject[i] | n6;
            n13 = n15;
        }
        if (n13 != 0) {
            this.restoreInvariants();
        }
        this.moveGapTo(n2);
        n12 = this.mGapLength;
        if (n11 >= n12) {
            this.resizeFor(this.mText.length + n11 - n12);
        }
        boolean bl = n10 == 0;
        boolean bl2 = bl;
        if (n9 > 0) {
            while (this.mSpanCount > 0 && this.removeSpansForChange(n, n2, bl2, this.treeRoot())) {
            }
        }
        this.mGapStart += n11;
        this.mGapLength -= n11;
        if (this.mGapLength < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        TextUtils.getChars(charSequence, n7, n8, this.mText, n);
        if (n9 > 0) {
            bl = this.mGapStart + this.mGapLength == this.mText.length;
            for (n2 = 0; n2 < this.mSpanCount; ++n2) {
                n12 = this.mSpanFlags[n2];
                arrobject = this.mSpanStarts;
                arrobject[n2] = this.updatedIntervalBound(arrobject[n2], n, n11, (n12 & 240) >> 4, bl, bl2);
                n12 = this.mSpanFlags[n2];
                arrobject = this.mSpanEnds;
                arrobject[n2] = this.updatedIntervalBound(arrobject[n2], n, n11, n12 & 15, bl, bl2);
            }
            n12 = n8;
            n2 = n7;
            this.restoreInvariants();
        } else {
            n12 = n8;
            n2 = n7;
        }
        if (charSequence instanceof Spanned) {
            charSequence = (Spanned)charSequence;
            arrobject = charSequence.getSpans(n2, n12, Object.class);
            for (n6 = 0; n6 < arrobject.length; ++n6) {
                n8 = charSequence.getSpanStart(arrobject[n6]);
                n7 = charSequence.getSpanEnd(arrobject[n6]);
                n5 = n8;
                if (n8 < n2) {
                    n5 = n3;
                }
                n8 = n7;
                if (n7 > n12) {
                    n8 = n4;
                }
                if (this.getSpanStart(arrobject[n6]) < 0) {
                    n12 = charSequence.getSpanFlags(arrobject[n6]);
                    this.setSpan(false, arrobject[n6], n5 - n2 + n, n8 - n2 + n, n12 | 2048, false);
                }
                n2 = n3;
                n12 = n4;
            }
            this.restoreInvariants();
        }
    }

    private void checkRange(String string2, int n, int n2) {
        if (n2 >= n) {
            int n3 = this.length();
            if (n <= n3 && n2 <= n3) {
                if (n >= 0 && n2 >= 0) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append(" ");
                stringBuilder.append(SpannableStringBuilder.region(n, n2));
                stringBuilder.append(" starts before 0");
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(SpannableStringBuilder.region(n, n2));
            stringBuilder.append(" ends beyond length ");
            stringBuilder.append(n3);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(SpannableStringBuilder.region(n, n2));
        stringBuilder.append(" has end before start");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static int[] checkSortBuffer(int[] arrn, int n) {
        if (arrn != null && n <= arrn.length) {
            return arrn;
        }
        return ArrayUtils.newUnpaddedIntArray(GrowingArrayUtils.growSize(n));
    }

    private final int compareSpans(int n, int n2, int[] arrn, int[] arrn2) {
        int n3 = arrn[n];
        int n4 = arrn[n2];
        if (n3 == n4) {
            return Integer.compare(arrn2[n], arrn2[n2]);
        }
        return Integer.compare(n4, n3);
    }

    private int countSpans(int n, int n2, Class class_, int n3) {
        int n4;
        block9 : {
            int n5;
            block10 : {
                int n6;
                block12 : {
                    block11 : {
                        int n7;
                        n6 = n4 = 0;
                        if ((n3 & 1) != 0) {
                            n7 = SpannableStringBuilder.leftChild(n3);
                            n5 = n6 = this.mSpanMax[n7];
                            if (n6 > this.mGapStart) {
                                n5 = n6 - this.mGapLength;
                            }
                            n6 = n4;
                            if (n5 >= n) {
                                n6 = this.countSpans(n, n2, class_, n7);
                            }
                        }
                        n4 = n6;
                        if (n3 >= this.mSpanCount) break block9;
                        n7 = n5 = this.mSpanStarts[n3];
                        if (n5 > this.mGapStart) {
                            n7 = n5 - this.mGapLength;
                        }
                        n4 = n6;
                        if (n7 > n2) break block9;
                        n4 = n5 = this.mSpanEnds[n3];
                        if (n5 > this.mGapStart) {
                            n4 = n5 - this.mGapLength;
                        }
                        n5 = n6;
                        if (n4 < n) break block10;
                        if (n7 == n4 || n == n2) break block11;
                        n5 = n6;
                        if (n7 == n2) break block10;
                        n5 = n6;
                        if (n4 == n) break block10;
                    }
                    if (Object.class == class_) break block12;
                    n5 = n6;
                    if (!class_.isInstance(this.mSpans[n3])) break block10;
                }
                n5 = n6 + 1;
            }
            n4 = n5;
            if ((n3 & 1) != 0) {
                n4 = n5 + this.countSpans(n, n2, class_, SpannableStringBuilder.rightChild(n3));
            }
        }
        return n4;
    }

    private <T> int getSpansRec(int n, int n2, Class<T> class_, int n3, T[] arrT, int[] arrn, int[] arrn2, int n4, boolean bl) {
        block12 : {
            int n5;
            int n6;
            if ((n3 & 1) != 0) {
                n6 = SpannableStringBuilder.leftChild(n3);
                n5 = this.mSpanMax[n6];
                if (n5 > this.mGapStart) {
                    n5 -= this.mGapLength;
                }
                if (n5 >= n) {
                    n4 = this.getSpansRec(n, n2, class_, n6, arrT, arrn, arrn2, n4, bl);
                }
            }
            if (n3 >= this.mSpanCount) {
                return n4;
            }
            n5 = this.mSpanStarts[n3];
            if (n5 > this.mGapStart) {
                n5 -= this.mGapLength;
            }
            if (n5 > n2) break block12;
            n6 = this.mSpanEnds[n3];
            if (n6 > this.mGapStart) {
                n6 -= this.mGapLength;
            }
            if (n6 >= n && (n5 == n6 || n == n2 || n5 != n2 && n6 != n) && (Object.class == class_ || class_.isInstance(this.mSpans[n3]))) {
                n6 = this.mSpanFlags[n3] & 16711680;
                n5 = n4;
                if (bl) {
                    arrn[n5] = n6;
                    arrn2[n5] = this.mSpanOrder[n3];
                } else if (n6 != 0) {
                    for (n5 = 0; n5 < n4 && n6 <= (this.getSpanFlags(arrT[n5]) & 16711680); ++n5) {
                    }
                    System.arraycopy(arrT, n5, arrT, n5 + 1, n4 - n5);
                }
                arrT[n5] = this.mSpans[n3];
                ++n4;
            }
            if (n4 < arrT.length && (n3 & 1) != 0) {
                n4 = this.getSpansRec(n, n2, class_, SpannableStringBuilder.rightChild(n3), arrT, arrn, arrn2, n4, bl);
            }
        }
        return n4;
    }

    private static boolean hasNonExclusiveExclusiveSpanAt(CharSequence arrobject, int n) {
        if (arrobject instanceof Spanned) {
            Spanned spanned = (Spanned)arrobject;
            arrobject = spanned.getSpans(n, n, Object.class);
            int n2 = arrobject.length;
            for (n = 0; n < n2; ++n) {
                if (spanned.getSpanFlags(arrobject[n]) == 33) continue;
                return true;
            }
        }
        return false;
    }

    private void invalidateIndex(int n) {
        this.mLowWaterMark = Math.min(n, this.mLowWaterMark);
    }

    private boolean isInvalidParagraph(int n, int n2) {
        boolean bl = n2 == 3 && n != 0 && n != this.length() && this.charAt(n - 1) != '\n';
        return bl;
    }

    private static int leftChild(int n) {
        return n - ((n + 1 & n) >> 1);
    }

    private void moveGapTo(int n) {
        block12 : {
            char[] arrc;
            int n2;
            if (n == this.mGapStart) {
                return;
            }
            boolean bl = n == this.length();
            int n3 = this.mGapStart;
            if (n < n3) {
                n2 = n3 - n;
                arrc = this.mText;
                System.arraycopy(arrc, n, arrc, n3 + this.mGapLength - n2, n2);
            } else {
                n2 = n - n3;
                arrc = this.mText;
                System.arraycopy(arrc, this.mGapLength + n - n2, arrc, n3, n2);
            }
            if (this.mSpanCount == 0) break block12;
            for (int i = 0; i < this.mSpanCount; ++i) {
                int n4;
                block17 : {
                    block18 : {
                        int n5;
                        block16 : {
                            block14 : {
                                block15 : {
                                    block13 : {
                                        n3 = this.mSpanStarts[i];
                                        n4 = this.mSpanEnds[i];
                                        n2 = n3;
                                        if (n3 > this.mGapStart) {
                                            n2 = n3 - this.mGapLength;
                                        }
                                        if (n2 <= n) break block13;
                                        n3 = n2 + this.mGapLength;
                                        break block14;
                                    }
                                    n3 = n2;
                                    if (n2 != n) break block14;
                                    n5 = (this.mSpanFlags[i] & 240) >> 4;
                                    if (n5 == 2) break block15;
                                    n3 = n2;
                                    if (!bl) break block14;
                                    n3 = n2;
                                    if (n5 != 3) break block14;
                                }
                                n3 = n2 + this.mGapLength;
                            }
                            n2 = n4;
                            if (n4 > this.mGapStart) {
                                n2 = n4 - this.mGapLength;
                            }
                            if (n2 <= n) break block16;
                            n4 = n2 + this.mGapLength;
                            break block17;
                        }
                        n4 = n2;
                        if (n2 != n) break block17;
                        n5 = this.mSpanFlags[i] & 15;
                        if (n5 == 2) break block18;
                        n4 = n2;
                        if (!bl) break block17;
                        n4 = n2;
                        if (n5 != 3) break block17;
                    }
                    n4 = n2 + this.mGapLength;
                }
                this.mSpanStarts[i] = n3;
                this.mSpanEnds[i] = n4;
            }
            this.calcMax(this.treeRoot());
        }
        this.mGapStart = n;
    }

    private int nextSpanTransitionRec(int n, int n2, Class class_, int n3) {
        int n4;
        int n5 = n2;
        if ((n3 & 1) != 0) {
            n4 = SpannableStringBuilder.leftChild(n3);
            n5 = n2;
            if (this.resolveGap(this.mSpanMax[n4]) > n) {
                n5 = this.nextSpanTransitionRec(n, n2, class_, n4);
            }
        }
        n4 = n5;
        if (n3 < this.mSpanCount) {
            int n6 = this.resolveGap(this.mSpanStarts[n3]);
            int n7 = this.resolveGap(this.mSpanEnds[n3]);
            n4 = n5;
            if (n6 > n) {
                n4 = n5;
                if (n6 < n5) {
                    n4 = n5;
                    if (class_.isInstance(this.mSpans[n3])) {
                        n4 = n6;
                    }
                }
            }
            n2 = n4;
            if (n7 > n) {
                n2 = n4;
                if (n7 < n4) {
                    n2 = n4;
                    if (class_.isInstance(this.mSpans[n3])) {
                        n2 = n7;
                    }
                }
            }
            n4 = n2;
            if (n6 < n2) {
                n4 = n2;
                if ((n3 & 1) != 0) {
                    n4 = this.nextSpanTransitionRec(n, n2, class_, SpannableStringBuilder.rightChild(n3));
                }
            }
        }
        return n4;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static int[] obtain(int n) {
        int[] arrn = null;
        int[][] arrn2 = sCachedIntBuffer;
        synchronized (arrn2) {
            int n2;
            int n3 = -1;
            int n4 = sCachedIntBuffer.length - 1;
            do {
                n2 = n3;
                if (n4 < 0) break;
                n2 = n3;
                if (sCachedIntBuffer[n4] != null) {
                    if (sCachedIntBuffer[n4].length >= n) {
                        n2 = n4;
                        break;
                    }
                    n2 = n3;
                    if (n3 == -1) {
                        n2 = n4;
                    }
                }
                --n4;
                n3 = n2;
            } while (true);
            if (n2 != -1) {
                arrn = sCachedIntBuffer[n2];
                SpannableStringBuilder.sCachedIntBuffer[n2] = null;
            }
            return SpannableStringBuilder.checkSortBuffer(arrn, n);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void recycle(int[] arrn) {
        int[][] arrn2 = sCachedIntBuffer;
        synchronized (arrn2) {
            for (int i = 0; i < sCachedIntBuffer.length; ++i) {
                if (sCachedIntBuffer[i] != null && arrn.length <= sCachedIntBuffer[i].length) {
                    continue;
                }
                SpannableStringBuilder.sCachedIntBuffer[i] = arrn;
                break;
            }
            return;
        }
    }

    private static String region(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(n);
        stringBuilder.append(" ... ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private void removeSpan(int n, int n2) {
        Object object = this.mSpans[n];
        int n3 = this.mSpanStarts[n];
        int n4 = this.mSpanEnds[n];
        int n5 = n3;
        if (n3 > this.mGapStart) {
            n5 = n3 - this.mGapLength;
        }
        n3 = n4;
        if (n4 > this.mGapStart) {
            n3 = n4 - this.mGapLength;
        }
        n4 = this.mSpanCount - (n + 1);
        Object[] arrobject = this.mSpans;
        System.arraycopy(arrobject, n + 1, arrobject, n, n4);
        arrobject = this.mSpanStarts;
        System.arraycopy(arrobject, n + 1, arrobject, n, n4);
        arrobject = this.mSpanEnds;
        System.arraycopy(arrobject, n + 1, arrobject, n, n4);
        arrobject = this.mSpanFlags;
        System.arraycopy(arrobject, n + 1, arrobject, n, n4);
        arrobject = this.mSpanOrder;
        System.arraycopy(arrobject, n + 1, arrobject, n, n4);
        --this.mSpanCount;
        this.invalidateIndex(n);
        this.mSpans[this.mSpanCount] = null;
        this.restoreInvariants();
        if ((n2 & 512) == 0) {
            this.sendSpanRemoved(object, n5, n3);
        }
    }

    private boolean removeSpansForChange(int n, int n2, boolean bl, int n3) {
        boolean bl2 = true;
        if ((n3 & 1) != 0 && this.resolveGap(this.mSpanMax[n3]) >= n && this.removeSpansForChange(n, n2, bl, SpannableStringBuilder.leftChild(n3))) {
            return true;
        }
        if (n3 < this.mSpanCount) {
            int n4;
            int n5;
            int n6;
            int[] arrn;
            int[] arrn2;
            if ((this.mSpanFlags[n3] & 33) == 33 && (arrn2 = this.mSpanStarts)[n3] >= n && (n6 = arrn2[n3]) < (n5 = this.mGapStart) + (n4 = this.mGapLength) && (arrn = this.mSpanEnds)[n3] >= n && arrn[n3] < n4 + n5 && (bl || arrn2[n3] > n || arrn[n3] < n5)) {
                this.mIndexOfSpan.remove(this.mSpans[n3]);
                this.removeSpan(n3, 0);
                return true;
            }
            bl = this.resolveGap(this.mSpanStarts[n3]) <= n2 && (n3 & 1) != 0 && this.removeSpansForChange(n, n2, bl, SpannableStringBuilder.rightChild(n3)) ? bl2 : false;
            return bl;
        }
        return false;
    }

    private void resizeFor(int n) {
        int n2 = this.mText.length;
        if (n + 1 <= n2) {
            return;
        }
        char[] arrc = ArrayUtils.newUnpaddedCharArray(GrowingArrayUtils.growSize(n));
        System.arraycopy(this.mText, 0, arrc, 0, this.mGapStart);
        n = arrc.length;
        int n3 = n - n2;
        int n4 = n2 - (this.mGapStart + this.mGapLength);
        System.arraycopy(this.mText, n2 - n4, arrc, n - n4, n4);
        this.mText = arrc;
        this.mGapLength += n3;
        if (this.mGapLength < 1) {
            new Exception("mGapLength < 1").printStackTrace();
        }
        if (this.mSpanCount != 0) {
            for (n = 0; n < this.mSpanCount; ++n) {
                arrc = this.mSpanStarts;
                if (arrc[n] > this.mGapStart) {
                    arrc[n] = arrc[n] + n3;
                }
                if ((arrc = this.mSpanEnds)[n] <= this.mGapStart) continue;
                arrc[n] = arrc[n] + n3;
            }
            this.calcMax(this.treeRoot());
        }
    }

    private int resolveGap(int n) {
        block0 : {
            if (n <= this.mGapStart) break block0;
            n -= this.mGapLength;
        }
        return n;
    }

    private void restoreInvariants() {
        Object object;
        int n;
        if (this.mSpanCount == 0) {
            return;
        }
        for (n = 1; n < this.mSpanCount; ++n) {
            int n2;
            Object[] arrobject = this.mSpanStarts;
            if (arrobject[n] >= arrobject[n - 1]) continue;
            object = this.mSpans[n];
            int n3 = arrobject[n];
            int n4 = this.mSpanEnds[n];
            int n5 = this.mSpanFlags[n];
            int n6 = this.mSpanOrder[n];
            int n7 = n;
            do {
                arrobject = this.mSpans;
                arrobject[n7] = arrobject[n7 - 1];
                arrobject = this.mSpanStarts;
                arrobject[n7] = arrobject[n7 - 1];
                int[] arrn = this.mSpanEnds;
                arrn[n7] = arrn[n7 - 1];
                arrn = this.mSpanFlags;
                arrn[n7] = arrn[n7 - 1];
                arrn = this.mSpanOrder;
                arrn[n7] = arrn[n7 - 1];
                n2 = n7 - 1;
                if (n2 <= 0) break;
                n7 = n2;
            } while (n3 < arrobject[n2 - 1]);
            this.mSpans[n2] = object;
            this.mSpanStarts[n2] = n3;
            this.mSpanEnds[n2] = n4;
            this.mSpanFlags[n2] = n5;
            this.mSpanOrder[n2] = n6;
            this.invalidateIndex(n2);
        }
        this.calcMax(this.treeRoot());
        if (this.mIndexOfSpan == null) {
            this.mIndexOfSpan = new IdentityHashMap();
        }
        for (n = this.mLowWaterMark; n < this.mSpanCount; ++n) {
            object = this.mIndexOfSpan.get(this.mSpans[n]);
            if (object != null && (Integer)object == n) continue;
            this.mIndexOfSpan.put(this.mSpans[n], n);
        }
        this.mLowWaterMark = Integer.MAX_VALUE;
    }

    private static int rightChild(int n) {
        return ((n + 1 & n) >> 1) + n;
    }

    private void sendAfterTextChanged(TextWatcher[] arrtextWatcher) {
        int n = arrtextWatcher.length;
        ++this.mTextWatcherDepth;
        for (int i = 0; i < n; ++i) {
            arrtextWatcher[i].afterTextChanged(this);
        }
        --this.mTextWatcherDepth;
    }

    private void sendBeforeTextChanged(TextWatcher[] arrtextWatcher, int n, int n2, int n3) {
        int n4 = arrtextWatcher.length;
        ++this.mTextWatcherDepth;
        for (int i = 0; i < n4; ++i) {
            arrtextWatcher[i].beforeTextChanged(this, n, n2, n3);
        }
        --this.mTextWatcherDepth;
    }

    private void sendSpanAdded(Object object, int n, int n2) {
        SpanWatcher[] arrspanWatcher = this.getSpans(n, n2, SpanWatcher.class);
        int n3 = arrspanWatcher.length;
        for (int i = 0; i < n3; ++i) {
            arrspanWatcher[i].onSpanAdded(this, object, n, n2);
        }
    }

    private void sendSpanChanged(Object object, int n, int n2, int n3, int n4) {
        SpanWatcher[] arrspanWatcher = this.getSpans(Math.min(n, n3), Math.min(Math.max(n2, n4), this.length()), SpanWatcher.class);
        int n5 = arrspanWatcher.length;
        for (int i = 0; i < n5; ++i) {
            arrspanWatcher[i].onSpanChanged(this, object, n, n2, n3, n4);
        }
    }

    private void sendSpanRemoved(Object object, int n, int n2) {
        SpanWatcher[] arrspanWatcher = this.getSpans(n, n2, SpanWatcher.class);
        int n3 = arrspanWatcher.length;
        for (int i = 0; i < n3; ++i) {
            arrspanWatcher[i].onSpanRemoved(this, object, n, n2);
        }
    }

    private void sendTextChanged(TextWatcher[] arrtextWatcher, int n, int n2, int n3) {
        int n4 = arrtextWatcher.length;
        ++this.mTextWatcherDepth;
        for (int i = 0; i < n4; ++i) {
            arrtextWatcher[i].onTextChanged(this, n, n2, n3);
        }
        --this.mTextWatcherDepth;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    private void sendToSpanWatchers(int var1_1, int var2_2, int var3_3) {
        for (var4_4 = 0; var4_4 < this.mSpanCount; ++var4_4) {
            block15 : {
                block14 : {
                    block13 : {
                        block12 : {
                            var5_5 = this.mSpanFlags[var4_4];
                            if ((var5_5 & 2048) != 0) continue;
                            var6_6 = this.mSpanStarts[var4_4];
                            var7_7 = this.mSpanEnds[var4_4];
                            var8_8 = var6_6;
                            if (var6_6 > this.mGapStart) {
                                var8_8 = var6_6 - this.mGapLength;
                            }
                            var6_6 = var7_7;
                            if (var7_7 > this.mGapStart) {
                                var6_6 = var7_7 - this.mGapLength;
                            }
                            var9_9 = var2_2 + var3_3;
                            var7_7 = 0;
                            if (var8_8 <= var9_9) break block12;
                            if (var3_3 == 0) ** GOTO lbl-1000
                            var7_7 = 1;
                            var10_10 = var8_8 - var3_3;
                            break block13;
                        }
                        if (!(var8_8 < var1_1 || var8_8 == var1_1 && (var5_5 & 4096) == 4096 || var8_8 == var9_9 && (var5_5 & 8192) == 8192)) {
                            var7_7 = 1;
                            var10_10 = var8_8;
                        } else lbl-1000: // 2 sources:
                        {
                            var10_10 = var8_8;
                        }
                    }
                    if (var6_6 <= var9_9) break block14;
                    if (var3_3 == 0) ** GOTO lbl-1000
                    var7_7 = 1;
                    var5_5 = var6_6 - var3_3;
                    break block15;
                }
                if (!(var6_6 < var1_1 || var6_6 == var1_1 && (var5_5 & 16384) == 16384 || var6_6 == var9_9 && (var5_5 & 32768) == 32768)) {
                    var7_7 = 1;
                    var5_5 = var6_6;
                } else lbl-1000: // 2 sources:
                {
                    var5_5 = var6_6;
                }
            }
            if (var7_7 != 0) {
                this.sendSpanChanged(this.mSpans[var4_4], var10_10, var5_5, var8_8, var6_6);
            }
            var11_11 = this.mSpanFlags;
            var11_11[var4_4] = var11_11[var4_4] & -61441;
        }
        var1_1 = 0;
        while (var1_1 < this.mSpanCount) {
            var11_11 = this.mSpanFlags;
            if ((var11_11[var1_1] & 2048) != 0) {
                var11_11[var1_1] = var11_11[var1_1] & -2049;
                var3_3 = this.mSpanStarts[var1_1];
                var8_8 = this.mSpanEnds[var1_1];
                var2_2 = var3_3;
                if (var3_3 > this.mGapStart) {
                    var2_2 = var3_3 - this.mGapLength;
                }
                var3_3 = var8_8;
                if (var8_8 > this.mGapStart) {
                    var3_3 = var8_8 - this.mGapLength;
                }
                this.sendSpanAdded(this.mSpans[var1_1], var2_2, var3_3);
            }
            ++var1_1;
        }
    }

    private void setSpan(boolean bl, Object object, int n, int n2, int n3, boolean bl2) {
        this.checkRange("setSpan", n, n2);
        int n4 = (n3 & 240) >> 4;
        if (this.isInvalidParagraph(n, n4)) {
            if (!bl2) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("PARAGRAPH span must start at paragraph boundary (");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" follows ");
            ((StringBuilder)object).append(this.charAt(n - 1));
            ((StringBuilder)object).append(")");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        int n5 = n3 & 15;
        if (this.isInvalidParagraph(n2, n5)) {
            if (!bl2) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("PARAGRAPH span must end at paragraph boundary (");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" follows ");
            ((StringBuilder)object).append(this.charAt(n2 - 1));
            ((StringBuilder)object).append(")");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        if (n4 == 2 && n5 == 1 && n == n2) {
            if (bl) {
                Log.e(TAG, "SPAN_EXCLUSIVE_EXCLUSIVE spans cannot have a zero length");
            }
            return;
        }
        int n6 = this.mGapStart;
        n6 = n > n6 ? n + this.mGapLength : (n == n6 && (n4 == 2 || n4 == 3 && n == this.length()) ? n + this.mGapLength : n);
        n4 = this.mGapStart;
        n5 = n2 > n4 ? this.mGapLength + n2 : (n2 == n4 && (n5 == 2 || n5 == 3 && n2 == this.length()) ? this.mGapLength + n2 : n2);
        Serializable serializable = this.mIndexOfSpan;
        if (serializable != null && (serializable = ((IdentityHashMap)serializable).get(object)) != null) {
            int n7 = (Integer)serializable;
            n4 = this.mSpanStarts[n7];
            int n8 = this.mSpanEnds[n7];
            if (n4 > this.mGapStart) {
                n4 -= this.mGapLength;
            }
            if (n8 > this.mGapStart) {
                n8 -= this.mGapLength;
            }
            this.mSpanStarts[n7] = n6;
            this.mSpanEnds[n7] = n5;
            this.mSpanFlags[n7] = n3;
            if (bl) {
                this.restoreInvariants();
                this.sendSpanChanged(object, n4, n8, n, n2);
            }
            return;
        }
        this.mSpans = GrowingArrayUtils.append(this.mSpans, this.mSpanCount, object);
        this.mSpanStarts = GrowingArrayUtils.append(this.mSpanStarts, this.mSpanCount, n6);
        this.mSpanEnds = GrowingArrayUtils.append(this.mSpanEnds, this.mSpanCount, n5);
        this.mSpanFlags = GrowingArrayUtils.append(this.mSpanFlags, this.mSpanCount, n3);
        this.mSpanOrder = GrowingArrayUtils.append(this.mSpanOrder, this.mSpanCount, this.mSpanInsertCount);
        this.invalidateIndex(this.mSpanCount);
        ++this.mSpanCount;
        ++this.mSpanInsertCount;
        n3 = this.treeRoot() * 2 + 1;
        if (this.mSpanMax.length < n3) {
            this.mSpanMax = new int[n3];
        }
        if (bl) {
            this.restoreInvariants();
            this.sendSpanAdded(object, n, n2);
        }
    }

    private final <T> void siftDown(int n, T[] arrT, int n2, int[] arrn, int[] arrn2) {
        int n3 = n * 2 + 1;
        int n4 = n;
        while (n3 < n2) {
            n = n3;
            if (n3 < n2 - 1) {
                n = n3;
                if (this.compareSpans(n3, n3 + 1, arrn, arrn2) < 0) {
                    n = n3 + 1;
                }
            }
            if (this.compareSpans(n4, n, arrn, arrn2) >= 0) break;
            T t = arrT[n4];
            arrT[n4] = arrT[n];
            arrT[n] = t;
            n3 = arrn[n4];
            arrn[n4] = arrn[n];
            arrn[n] = n3;
            n3 = arrn2[n4];
            arrn2[n4] = arrn2[n];
            arrn2[n] = n3;
            n3 = n * 2 + 1;
            n4 = n;
        }
    }

    private final <T> void sort(T[] arrT, int[] arrn, int[] arrn2) {
        int n;
        int n2 = arrT.length;
        for (n = n2 / 2 - 1; n >= 0; --n) {
            this.siftDown(n, arrT, n2, arrn, arrn2);
        }
        for (n = n2 - 1; n > 0; --n) {
            T t = arrT[0];
            arrT[0] = arrT[n];
            arrT[n] = t;
            n2 = arrn[0];
            arrn[0] = arrn[n];
            arrn[n] = n2;
            n2 = arrn2[0];
            arrn2[0] = arrn2[n];
            arrn2[n] = n2;
            this.siftDown(0, arrT, n, arrn, arrn2);
        }
    }

    private int treeRoot() {
        return Integer.highestOneBit(this.mSpanCount) - 1;
    }

    private int updatedIntervalBound(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        int n5;
        int n6;
        if (n >= n2 && n < (n6 = this.mGapStart) + (n5 = this.mGapLength)) {
            if (n4 == 2) {
                if (bl2 || n > n2) {
                    return this.mGapStart + this.mGapLength;
                }
            } else if (n4 == 3) {
                if (bl) {
                    return n6 + n5;
                }
            } else {
                if (!bl2 && n >= n6 - n3) {
                    return n6;
                }
                return n2;
            }
        }
        return n;
    }

    public static SpannableStringBuilder valueOf(CharSequence charSequence) {
        if (charSequence instanceof SpannableStringBuilder) {
            return (SpannableStringBuilder)charSequence;
        }
        return new SpannableStringBuilder(charSequence);
    }

    @Override
    public SpannableStringBuilder append(char c) {
        return this.append(String.valueOf(c));
    }

    @Override
    public SpannableStringBuilder append(CharSequence charSequence) {
        int n = this.length();
        return this.replace(n, n, charSequence, 0, charSequence.length());
    }

    @Override
    public SpannableStringBuilder append(CharSequence charSequence, int n, int n2) {
        int n3 = this.length();
        return this.replace(n3, n3, charSequence, n, n2);
    }

    public SpannableStringBuilder append(CharSequence charSequence, Object object, int n) {
        int n2 = this.length();
        this.append(charSequence);
        this.setSpan(object, n2, this.length(), n);
        return this;
    }

    @Override
    public char charAt(int n) {
        int n2 = this.length();
        if (n >= 0) {
            if (n < n2) {
                if (n >= this.mGapStart) {
                    return this.mText[this.mGapLength + n];
                }
                return this.mText[n];
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("charAt: ");
            stringBuilder.append(n);
            stringBuilder.append(" >= length ");
            stringBuilder.append(n2);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("charAt: ");
        stringBuilder.append(n);
        stringBuilder.append(" < 0");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    public void clear() {
        this.replace(0, this.length(), "", 0, 0);
        this.mSpanInsertCount = 0;
    }

    @Override
    public void clearSpans() {
        IdentityHashMap<Object, Integer> identityHashMap;
        int n = this.mSpanCount - 1;
        while (n >= 0) {
            identityHashMap = this.mSpans[n];
            int n2 = this.mSpanStarts[n];
            int n3 = this.mSpanEnds[n];
            int n4 = n2;
            if (n2 > this.mGapStart) {
                n4 = n2 - this.mGapLength;
            }
            n2 = n3;
            if (n3 > this.mGapStart) {
                n2 = n3 - this.mGapLength;
            }
            this.mSpanCount = n--;
            this.mSpans[n] = null;
            this.sendSpanRemoved(identityHashMap, n4, n2);
        }
        identityHashMap = this.mIndexOfSpan;
        if (identityHashMap != null) {
            identityHashMap.clear();
        }
        this.mSpanInsertCount = 0;
    }

    @Override
    public SpannableStringBuilder delete(int n, int n2) {
        Editable editable = this.replace(n, n2, "", 0, 0);
        if (this.mGapLength > this.length() * 2) {
            this.resizeFor(this.length());
        }
        return editable;
    }

    @Override
    public void drawText(BaseCanvas baseCanvas, int n, int n2, float f, float f2, Paint paint) {
        this.checkRange("drawText", n, n2);
        int n3 = this.mGapStart;
        if (n2 <= n3) {
            baseCanvas.drawText(this.mText, n, n2 - n, f, f2, paint);
        } else if (n >= n3) {
            baseCanvas.drawText(this.mText, n + this.mGapLength, n2 - n, f, f2, paint);
        } else {
            char[] arrc = TextUtils.obtain(n2 - n);
            this.getChars(n, n2, arrc, 0);
            baseCanvas.drawText(arrc, 0, n2 - n, f, f2, paint);
            TextUtils.recycle(arrc);
        }
    }

    @Override
    public void drawTextRun(BaseCanvas baseCanvas, int n, int n2, int n3, int n4, float f, float f2, boolean bl, Paint paint) {
        this.checkRange("drawTextRun", n, n2);
        int n5 = n4 - n3;
        n2 -= n;
        int n6 = this.mGapStart;
        if (n4 <= n6) {
            baseCanvas.drawTextRun(this.mText, n, n2, n3, n5, f, f2, bl, paint);
        } else if (n3 >= n6) {
            char[] arrc = this.mText;
            n4 = this.mGapLength;
            baseCanvas.drawTextRun(arrc, n + n4, n2, n3 + n4, n5, f, f2, bl, paint);
        } else {
            char[] arrc = TextUtils.obtain(n5);
            this.getChars(n3, n4, arrc, 0);
            baseCanvas.drawTextRun(arrc, n - n3, n2, 0, n5, f, f2, bl, paint);
            TextUtils.recycle(arrc);
        }
    }

    public boolean equals(Object object) {
        if (object instanceof Spanned && this.toString().equals(object.toString())) {
            Spanned spanned = (Spanned)object;
            Object[] arrobject = spanned.getSpans(0, spanned.length(), Object.class);
            Object[] arrobject2 = this.getSpans(0, this.length(), Object.class);
            if (this.mSpanCount == arrobject.length) {
                for (int i = 0; i < this.mSpanCount; ++i) {
                    Object object2 = arrobject2[i];
                    object = arrobject[i];
                    if (!(object2 == this ? spanned != object || this.getSpanStart(object2) != spanned.getSpanStart(object) || this.getSpanEnd(object2) != spanned.getSpanEnd(object) || this.getSpanFlags(object2) != spanned.getSpanFlags(object) : !object2.equals(object) || this.getSpanStart(object2) != spanned.getSpanStart(object) || this.getSpanEnd(object2) != spanned.getSpanEnd(object) || this.getSpanFlags(object2) != spanned.getSpanFlags(object))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        this.checkRange("getChars", n, n2);
        int n4 = this.mGapStart;
        if (n2 <= n4) {
            System.arraycopy(this.mText, n, arrc, n3, n2 - n);
        } else if (n >= n4) {
            System.arraycopy(this.mText, this.mGapLength + n, arrc, n3, n2 - n);
        } else {
            System.arraycopy(this.mText, n, arrc, n3, n4 - n);
            char[] arrc2 = this.mText;
            n4 = this.mGapStart;
            System.arraycopy(arrc2, this.mGapLength + n4, arrc, n4 - n + n3, n2 - n4);
        }
    }

    @Override
    public InputFilter[] getFilters() {
        return this.mFilters;
    }

    @Override
    public int getSpanEnd(Object object) {
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        int n = -1;
        if (identityHashMap == null) {
            return -1;
        }
        if ((object = identityHashMap.get(object)) != null) {
            n = this.resolveGap(this.mSpanEnds[(Integer)object]);
        }
        return n;
    }

    @Override
    public int getSpanFlags(Object object) {
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        int n = 0;
        if (identityHashMap == null) {
            return 0;
        }
        if ((object = identityHashMap.get(object)) != null) {
            n = this.mSpanFlags[(Integer)object];
        }
        return n;
    }

    @Override
    public int getSpanStart(Object object) {
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        int n = -1;
        if (identityHashMap == null) {
            return -1;
        }
        if ((object = identityHashMap.get(object)) != null) {
            n = this.resolveGap(this.mSpanStarts[(Integer)object]);
        }
        return n;
    }

    @Override
    public <T> T[] getSpans(int n, int n2, Class<T> class_) {
        return this.getSpans(n, n2, class_, true);
    }

    @UnsupportedAppUsage
    public <T> T[] getSpans(int n, int n2, Class<T> class_, boolean bl) {
        if (class_ == null) {
            return ArrayUtils.emptyArray(Object.class);
        }
        if (this.mSpanCount == 0) {
            return ArrayUtils.emptyArray(class_);
        }
        int n3 = this.countSpans(n, n2, class_, this.treeRoot());
        if (n3 == 0) {
            return ArrayUtils.emptyArray(class_);
        }
        Object[] arrobject = (Object[])Array.newInstance(class_, n3);
        int[] arrn = bl ? SpannableStringBuilder.obtain(n3) : EmptyArray.INT;
        int[] arrn2 = bl ? SpannableStringBuilder.obtain(n3) : EmptyArray.INT;
        this.getSpansRec(n, n2, class_, this.treeRoot(), arrobject, arrn, arrn2, 0, bl);
        if (bl) {
            this.sort(arrobject, arrn, arrn2);
            SpannableStringBuilder.recycle(arrn);
            SpannableStringBuilder.recycle(arrn2);
        }
        return arrobject;
    }

    @Override
    public float getTextRunAdvances(int n, int n2, int n3, int n4, boolean bl, float[] arrf, int n5, Paint paint) {
        float f;
        int n6 = n4 - n3;
        int n7 = n2 - n;
        int n8 = this.mGapStart;
        if (n2 <= n8) {
            f = paint.getTextRunAdvances(this.mText, n, n7, n3, n6, bl, arrf, n5);
        } else if (n >= n8) {
            char[] arrc = this.mText;
            n2 = this.mGapLength;
            f = paint.getTextRunAdvances(arrc, n + n2, n7, n3 + n2, n6, bl, arrf, n5);
        } else {
            char[] arrc = TextUtils.obtain(n6);
            this.getChars(n3, n4, arrc, 0);
            f = paint.getTextRunAdvances(arrc, n - n3, n7, 0, n6, bl, arrf, n5);
            TextUtils.recycle(arrc);
        }
        return f;
    }

    @Deprecated
    public int getTextRunCursor(int n, int n2, int n3, int n4, int n5, Paint paint) {
        boolean bl = true;
        if (n3 != 1) {
            bl = false;
        }
        return this.getTextRunCursor(n, n2, bl, n4, n5, paint);
    }

    @Override
    public int getTextRunCursor(int n, int n2, boolean bl, int n3, int n4, Paint paint) {
        int n5 = n2 - n;
        int n6 = this.mGapStart;
        if (n2 <= n6) {
            n = paint.getTextRunCursor(this.mText, n, n5, bl, n3, n4);
        } else if (n >= n6) {
            char[] arrc = this.mText;
            n2 = this.mGapLength;
            n = paint.getTextRunCursor(arrc, n + n2, n5, bl, n3 + n2, n4) - this.mGapLength;
        } else {
            char[] arrc = TextUtils.obtain(n5);
            this.getChars(n, n2, arrc, 0);
            n = paint.getTextRunCursor(arrc, 0, n5, bl, n3 - n, n4) + n;
            TextUtils.recycle(arrc);
        }
        return n;
    }

    public int getTextWatcherDepth() {
        return this.mTextWatcherDepth;
    }

    @Override
    public int getTextWidths(int n, int n2, float[] arrf, Paint paint) {
        this.checkRange("getTextWidths", n, n2);
        int n3 = this.mGapStart;
        if (n2 <= n3) {
            n = paint.getTextWidths(this.mText, n, n2 - n, arrf);
        } else if (n >= n3) {
            n = paint.getTextWidths(this.mText, this.mGapLength + n, n2 - n, arrf);
        } else {
            char[] arrc = TextUtils.obtain(n2 - n);
            this.getChars(n, n2, arrc, 0);
            n = paint.getTextWidths(arrc, 0, n2 - n, arrf);
            TextUtils.recycle(arrc);
        }
        return n;
    }

    public int hashCode() {
        int n = this.toString().hashCode() * 31 + this.mSpanCount;
        for (int i = 0; i < this.mSpanCount; ++i) {
            Object object = this.mSpans[i];
            int n2 = n;
            if (object != this) {
                n2 = n * 31 + object.hashCode();
            }
            n = ((n2 * 31 + this.getSpanStart(object)) * 31 + this.getSpanEnd(object)) * 31 + this.getSpanFlags(object);
        }
        return n;
    }

    @Override
    public SpannableStringBuilder insert(int n, CharSequence charSequence) {
        return this.replace(n, n, charSequence, 0, charSequence.length());
    }

    @Override
    public SpannableStringBuilder insert(int n, CharSequence charSequence, int n2, int n3) {
        return this.replace(n, n, charSequence, n2, n3);
    }

    @Override
    public int length() {
        return this.mText.length - this.mGapLength;
    }

    @Override
    public float measureText(int n, int n2, Paint paint) {
        float f;
        this.checkRange("measureText", n, n2);
        int n3 = this.mGapStart;
        if (n2 <= n3) {
            f = paint.measureText(this.mText, n, n2 - n);
        } else if (n >= n3) {
            f = paint.measureText(this.mText, this.mGapLength + n, n2 - n);
        } else {
            char[] arrc = TextUtils.obtain(n2 - n);
            this.getChars(n, n2, arrc, 0);
            f = paint.measureText(arrc, 0, n2 - n);
            TextUtils.recycle(arrc);
        }
        return f;
    }

    @Override
    public int nextSpanTransition(int n, int n2, Class class_) {
        if (this.mSpanCount == 0) {
            return n2;
        }
        Class<Object> class_2 = class_;
        if (class_ == null) {
            class_2 = Object.class;
        }
        return this.nextSpanTransitionRec(n, n2, class_2, this.treeRoot());
    }

    @Override
    public void removeSpan(Object object) {
        this.removeSpan(object, 0);
    }

    @Override
    public void removeSpan(Object object, int n) {
        IdentityHashMap<Object, Integer> identityHashMap = this.mIndexOfSpan;
        if (identityHashMap == null) {
            return;
        }
        if ((object = identityHashMap.remove(object)) != null) {
            this.removeSpan((Integer)object, n);
        }
    }

    @Override
    public SpannableStringBuilder replace(int n, int n2, CharSequence charSequence) {
        return this.replace(n, n2, charSequence, 0, charSequence.length());
    }

    @Override
    public SpannableStringBuilder replace(int n, int n2, CharSequence object, int n3, int n4) {
        TextWatcher[] arrtextWatcher;
        this.checkRange("replace", n, n2);
        int n5 = this.mFilters.length;
        int n6 = n4;
        int n7 = 0;
        n4 = n3;
        for (n3 = n7; n3 < n5; ++n3) {
            arrtextWatcher = this.mFilters[n3].filter((CharSequence)object, n4, n6, this, n, n2);
            if (arrtextWatcher == null) continue;
            n6 = arrtextWatcher.length();
            object = arrtextWatcher;
            n4 = 0;
        }
        int n8 = n2 - n;
        int n9 = n6 - n4;
        if (n8 == 0 && n9 == 0 && !SpannableStringBuilder.hasNonExclusiveExclusiveSpanAt((CharSequence)object, n4)) {
            return this;
        }
        arrtextWatcher = this.getSpans(n, n + n8, TextWatcher.class);
        this.sendBeforeTextChanged(arrtextWatcher, n, n8, n9);
        n7 = n8 != 0 && n9 != 0 ? 1 : 0;
        if (n7 != 0) {
            n5 = Selection.getSelectionStart(this);
            n3 = Selection.getSelectionEnd(this);
        } else {
            n5 = 0;
            n3 = 0;
        }
        this.change(n, n2, (CharSequence)object, n4, n6);
        if (n7 != 0) {
            long l;
            n4 = 0;
            if (n5 > n && n5 < n2) {
                l = n5 - n;
                n4 = n + Math.toIntExact((long)n9 * l / (long)n8);
                this.setSpan(false, Selection.SELECTION_START, n4, n4, 34, true);
                n4 = 1;
            }
            if (n3 > n && n3 < n2) {
                l = n3 - n;
                n4 = n + Math.toIntExact((long)n9 * l / (long)n8);
                n6 = 1;
                object = Selection.SELECTION_END;
                n3 = n4;
                this.setSpan(false, object, n4, n3, 34, true);
                n4 = n3;
                n3 = n6;
            } else {
                n6 = n4;
                n4 = n3;
                n3 = n6;
            }
            if (n3 != 0) {
                this.restoreInvariants();
            }
        }
        this.sendTextChanged(arrtextWatcher, n, n8, n9);
        this.sendAfterTextChanged(arrtextWatcher);
        this.sendToSpanWatchers(n, n2, n9 - n8);
        return this;
    }

    @Override
    public void setFilters(InputFilter[] arrinputFilter) {
        if (arrinputFilter != null) {
            this.mFilters = arrinputFilter;
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public void setSpan(Object object, int n, int n2, int n3) {
        this.setSpan(true, object, n, n2, n3, true);
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        return new SpannableStringBuilder(this, n, n2);
    }

    @UnsupportedAppUsage
    public String substring(int n, int n2) {
        char[] arrc = new char[n2 - n];
        this.getChars(n, n2, arrc, 0);
        return new String(arrc);
    }

    @Override
    public String toString() {
        int n = this.length();
        char[] arrc = new char[n];
        this.getChars(0, n, arrc, 0);
        return new String(arrc);
    }
}

