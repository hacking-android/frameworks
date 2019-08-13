/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.text.NoCopySpan;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.Spanned;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.GrowingArrayUtils;
import java.lang.reflect.Array;
import libcore.util.EmptyArray;

abstract class SpannableStringInternal {
    @UnsupportedAppUsage
    private static final int COLUMNS = 3;
    @UnsupportedAppUsage
    static final Object[] EMPTY = new Object[0];
    @UnsupportedAppUsage
    private static final int END = 1;
    @UnsupportedAppUsage
    private static final int FLAGS = 2;
    @UnsupportedAppUsage
    private static final int START = 0;
    @UnsupportedAppUsage
    private int mSpanCount;
    @UnsupportedAppUsage
    private int[] mSpanData;
    @UnsupportedAppUsage
    private Object[] mSpans;
    @UnsupportedAppUsage
    private String mText;

    @UnsupportedAppUsage
    SpannableStringInternal(CharSequence charSequence, int n, int n2) {
        this(charSequence, n, n2, false);
    }

    SpannableStringInternal(CharSequence charSequence, int n, int n2, boolean bl) {
        this.mText = n == 0 && n2 == charSequence.length() ? charSequence.toString() : charSequence.toString().substring(n, n2);
        this.mSpans = EmptyArray.OBJECT;
        this.mSpanData = EmptyArray.INT;
        if (charSequence instanceof Spanned) {
            if (charSequence instanceof SpannableStringInternal) {
                this.copySpans((SpannableStringInternal)((Object)charSequence), n, n2, bl);
            } else {
                this.copySpans((Spanned)charSequence, n, n2, bl);
            }
        }
    }

    @UnsupportedAppUsage
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
                stringBuilder.append(SpannableStringInternal.region(n, n2));
                stringBuilder.append(" starts before 0");
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" ");
            stringBuilder.append(SpannableStringInternal.region(n, n2));
            stringBuilder.append(" ends beyond length ");
            stringBuilder.append(n3);
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ");
        stringBuilder.append(SpannableStringInternal.region(n, n2));
        stringBuilder.append(" has end before start");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private void copySpans(SpannableStringInternal spannableStringInternal, int n, int n2) {
        this.copySpans(spannableStringInternal, n, n2, false);
    }

    private void copySpans(SpannableStringInternal arrn, int n, int n2, boolean bl) {
        int n3;
        int n4;
        int n5 = 0;
        int[] arrn2 = arrn.mSpanData;
        Object[] arrobject = arrn.mSpans;
        int n6 = arrn.mSpanCount;
        int n7 = 0;
        for (n4 = 0; n4 < n6; ++n4) {
            if (this.isOutOfCopyRange(n, n2, arrn2[n4 * 3 + 0], arrn2[n4 * 3 + 1])) continue;
            if (arrobject[n4] instanceof NoCopySpan) {
                n3 = 1;
                n7 = 1;
                if (bl) {
                    n7 = n3;
                    continue;
                }
            }
            ++n5;
        }
        if (n5 == 0) {
            return;
        }
        if (n7 == 0 && n == 0 && n2 == arrn.length()) {
            this.mSpans = ArrayUtils.newUnpaddedObjectArray(arrn.mSpans.length);
            this.mSpanData = new int[arrn.mSpanData.length];
            this.mSpanCount = arrn.mSpanCount;
            arrobject = arrn.mSpans;
            System.arraycopy(arrobject, 0, this.mSpans, 0, arrobject.length);
            arrobject = arrn.mSpanData;
            arrn = this.mSpanData;
            System.arraycopy(arrobject, 0, arrn, 0, arrn.length);
        } else {
            this.mSpanCount = n5;
            this.mSpans = ArrayUtils.newUnpaddedObjectArray(this.mSpanCount);
            this.mSpanData = new int[this.mSpans.length * 3];
            n4 = 0;
            for (n7 = 0; n7 < n6; ++n7) {
                n3 = arrn2[n7 * 3 + 0];
                int n8 = arrn2[n7 * 3 + 1];
                n5 = n4;
                if (!this.isOutOfCopyRange(n, n2, n3, n8)) {
                    if (bl && arrobject[n7] instanceof NoCopySpan) {
                        n5 = n4;
                    } else {
                        n5 = n3;
                        if (n3 < n) {
                            n5 = n;
                        }
                        n3 = n8;
                        if (n8 > n2) {
                            n3 = n2;
                        }
                        this.mSpans[n4] = arrobject[n7];
                        arrn = this.mSpanData;
                        arrn[n4 * 3 + 0] = n5 - n;
                        arrn[n4 * 3 + 1] = n3 - n;
                        arrn[n4 * 3 + 2] = arrn2[n7 * 3 + 2];
                        n5 = n4 + 1;
                    }
                }
                n4 = n5;
            }
        }
    }

    @UnsupportedAppUsage
    private void copySpans(Spanned spanned, int n, int n2) {
        this.copySpans(spanned, n, n2, false);
    }

    private void copySpans(Spanned spanned, int n, int n2, boolean bl) {
        Object[] arrobject = spanned.getSpans(n, n2, Object.class);
        for (int i = 0; i < arrobject.length; ++i) {
            if (bl && arrobject[i] instanceof NoCopySpan) continue;
            int n3 = spanned.getSpanStart(arrobject[i]);
            int n4 = spanned.getSpanEnd(arrobject[i]);
            int n5 = spanned.getSpanFlags(arrobject[i]);
            int n6 = n3;
            if (n3 < n) {
                n6 = n;
            }
            n3 = n4;
            if (n4 > n2) {
                n3 = n2;
            }
            this.setSpan(arrobject[i], n6 - n, n3 - n, n5, false);
        }
    }

    @UnsupportedAppUsage
    private boolean isIndexFollowsNextLine(int n) {
        boolean bl = n != 0 && n != this.length() && this.charAt(n - 1) != '\n';
        return bl;
    }

    @UnsupportedAppUsage
    private final boolean isOutOfCopyRange(int n, int n2, int n3, int n4) {
        if (n3 <= n2 && n4 >= n) {
            return n3 != n4 && n != n2 && (n3 == n2 || n4 == n);
        }
        return true;
    }

    @UnsupportedAppUsage
    private static String region(int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append(n);
        stringBuilder.append(" ... ");
        stringBuilder.append(n2);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    private void sendSpanAdded(Object object, int n, int n2) {
        SpanWatcher[] arrspanWatcher = this.getSpans(n, n2, SpanWatcher.class);
        int n3 = arrspanWatcher.length;
        for (int i = 0; i < n3; ++i) {
            arrspanWatcher[i].onSpanAdded((Spannable)((Object)this), object, n, n2);
        }
    }

    @UnsupportedAppUsage
    private void sendSpanChanged(Object object, int n, int n2, int n3, int n4) {
        SpanWatcher[] arrspanWatcher = this.getSpans(Math.min(n, n3), Math.max(n2, n4), SpanWatcher.class);
        int n5 = arrspanWatcher.length;
        for (int i = 0; i < n5; ++i) {
            arrspanWatcher[i].onSpanChanged((Spannable)((Object)this), object, n, n2, n3, n4);
        }
    }

    @UnsupportedAppUsage
    private void sendSpanRemoved(Object object, int n, int n2) {
        SpanWatcher[] arrspanWatcher = this.getSpans(n, n2, SpanWatcher.class);
        int n3 = arrspanWatcher.length;
        for (int i = 0; i < n3; ++i) {
            arrspanWatcher[i].onSpanRemoved((Spannable)((Object)this), object, n, n2);
        }
    }

    @UnsupportedAppUsage
    private void setSpan(Object object, int n, int n2, int n3, boolean bl) {
        int n4;
        this.checkRange("setSpan", n, n2);
        if ((n3 & 51) == 51) {
            if (this.isIndexFollowsNextLine(n)) {
                if (!bl) {
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
            if (this.isIndexFollowsNextLine(n2)) {
                if (!bl) {
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
        }
        int n5 = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        for (n4 = 0; n4 < n5; ++n4) {
            if (arrobject[n4] != object) continue;
            n5 = arrn[n4 * 3 + 0];
            int n6 = arrn[n4 * 3 + 1];
            arrn[n4 * 3 + 0] = n;
            arrn[n4 * 3 + 1] = n2;
            arrn[n4 * 3 + 2] = n3;
            this.sendSpanChanged(object, n5, n6, n, n2);
            return;
        }
        n4 = this.mSpanCount;
        if (n4 + 1 >= this.mSpans.length) {
            arrobject = ArrayUtils.newUnpaddedObjectArray(GrowingArrayUtils.growSize(n4));
            arrn = new int[arrobject.length * 3];
            System.arraycopy(this.mSpans, 0, arrobject, 0, this.mSpanCount);
            System.arraycopy(this.mSpanData, 0, arrn, 0, this.mSpanCount * 3);
            this.mSpans = arrobject;
            this.mSpanData = arrn;
        }
        arrobject = this.mSpans;
        n4 = this.mSpanCount;
        arrobject[n4] = object;
        arrobject = this.mSpanData;
        arrobject[n4 * 3 + 0] = n;
        arrobject[n4 * 3 + 1] = n2;
        arrobject[n4 * 3 + 2] = n3;
        this.mSpanCount = n4 + 1;
        if (this instanceof Spannable) {
            this.sendSpanAdded(object, n, n2);
        }
    }

    public final char charAt(int n) {
        return this.mText.charAt(n);
    }

    public boolean equals(Object arrobject) {
        if (arrobject instanceof Spanned && this.toString().equals(arrobject.toString())) {
            Spanned spanned = (Spanned)arrobject;
            arrobject = spanned.getSpans(0, spanned.length(), Object.class);
            Object[] arrobject2 = this.getSpans(0, this.length(), Object.class);
            if (this.mSpanCount == arrobject.length) {
                for (int i = 0; i < this.mSpanCount; ++i) {
                    Object object = arrobject2[i];
                    Object object2 = arrobject[i];
                    if (!(object == this ? spanned != object2 || this.getSpanStart(object) != spanned.getSpanStart(object2) || this.getSpanEnd(object) != spanned.getSpanEnd(object2) || this.getSpanFlags(object) != spanned.getSpanFlags(object2) : !object.equals(object2) || this.getSpanStart(object) != spanned.getSpanStart(object2) || this.getSpanEnd(object) != spanned.getSpanEnd(object2) || this.getSpanFlags(object) != spanned.getSpanFlags(object2))) continue;
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public final void getChars(int n, int n2, char[] arrc, int n3) {
        this.mText.getChars(n, n2, arrc, n3);
    }

    @UnsupportedAppUsage
    public int getSpanEnd(Object object) {
        int n = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        --n;
        while (n >= 0) {
            if (arrobject[n] == object) {
                return arrn[n * 3 + 1];
            }
            --n;
        }
        return -1;
    }

    @UnsupportedAppUsage
    public int getSpanFlags(Object object) {
        int n = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        --n;
        while (n >= 0) {
            if (arrobject[n] == object) {
                return arrn[n * 3 + 2];
            }
            --n;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public int getSpanStart(Object object) {
        int n = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        --n;
        while (n >= 0) {
            if (arrobject[n] == object) {
                return arrn[n * 3 + 0];
            }
            --n;
        }
        return -1;
    }

    @UnsupportedAppUsage
    public <T> T[] getSpans(int n, int n2, Class<T> arrobject) {
        int n3 = 0;
        int n4 = this.mSpanCount;
        Object[] arrobject2 = this.mSpans;
        int[] arrn = this.mSpanData;
        Object[] arrobject3 = null;
        Object object = null;
        int n5 = 0;
        do {
            int n6 = n;
            if (n5 >= n4) break;
            int n7 = arrn[n5 * 3 + 0];
            int n8 = arrn[n5 * 3 + 1];
            if (n7 <= n2 && n8 >= n6 && (n7 == n8 || n6 == n2 || n7 != n2 && n8 != n6) && (arrobject == null || arrobject == Object.class || arrobject.isInstance(arrobject2[n5]))) {
                if (n3 == 0) {
                    object = arrobject2[n5];
                    ++n3;
                } else {
                    if (n3 == 1) {
                        arrobject3 = (Object[])Array.newInstance(arrobject, n4 - n5 + 1);
                        arrobject3[0] = object;
                    }
                    if ((n7 = arrn[n5 * 3 + 2] & 16711680) != 0) {
                        for (n6 = 0; n6 < n3 && n7 <= (this.getSpanFlags(arrobject3[n6]) & 16711680); ++n6) {
                        }
                        System.arraycopy(arrobject3, n6, arrobject3, n6 + 1, n3 - n6);
                        arrobject3[n6] = arrobject2[n5];
                        ++n3;
                    } else {
                        arrobject3[n3] = arrobject2[n5];
                        ++n3;
                    }
                }
            }
            ++n5;
        } while (true);
        if (n3 == 0) {
            return ArrayUtils.emptyArray(arrobject);
        }
        if (n3 == 1) {
            arrobject = (Object[])Array.newInstance(arrobject, 1);
            arrobject[0] = object;
            return arrobject;
        }
        if (n3 == arrobject3.length) {
            return arrobject3;
        }
        arrobject = (Object[])Array.newInstance(arrobject, n3);
        System.arraycopy(arrobject3, 0, arrobject, 0, n3);
        return arrobject;
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

    public final int length() {
        return this.mText.length();
    }

    @UnsupportedAppUsage
    public int nextSpanTransition(int n, int n2, Class class_) {
        int n3 = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        Class<Object> class_2 = class_;
        if (class_ == null) {
            class_2 = Object.class;
        }
        int n4 = n2;
        for (int i = 0; i < n3; ++i) {
            int n5 = arrn[i * 3 + 0];
            int n6 = arrn[i * 3 + 1];
            n2 = n4;
            if (n5 > n) {
                n2 = n4;
                if (n5 < n4) {
                    n2 = n4;
                    if (class_2.isInstance(arrobject[i])) {
                        n2 = n5;
                    }
                }
            }
            n4 = n2;
            if (n6 <= n) continue;
            n4 = n2;
            if (n6 >= n2) continue;
            n4 = n2;
            if (!class_2.isInstance(arrobject[i])) continue;
            n4 = n6;
        }
        return n4;
    }

    @UnsupportedAppUsage
    void removeSpan(Object object) {
        this.removeSpan(object, 0);
    }

    public void removeSpan(Object object, int n) {
        int n2 = this.mSpanCount;
        Object[] arrobject = this.mSpans;
        int[] arrn = this.mSpanData;
        for (int i = n2 - 1; i >= 0; --i) {
            if (arrobject[i] != object) continue;
            int n3 = arrn[i * 3 + 0];
            int n4 = arrn[i * 3 + 1];
            System.arraycopy(arrobject, i + 1, arrobject, i, n2 -= i + 1);
            System.arraycopy(arrn, (i + 1) * 3, arrn, i * 3, n2 * 3);
            --this.mSpanCount;
            if ((n & 512) == 0) {
                this.sendSpanRemoved(object, n3, n4);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    void setSpan(Object object, int n, int n2, int n3) {
        this.setSpan(object, n, n2, n3, true);
    }

    public final String toString() {
        return this.mText;
    }
}

