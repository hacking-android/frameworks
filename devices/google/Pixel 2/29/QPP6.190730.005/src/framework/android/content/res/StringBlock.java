/*
 * Decompiled with CFR 0.145.
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Annotation;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.LineHeightSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.SparseArray;
import com.android.internal.annotations.GuardedBy;

final class StringBlock {
    private static final String TAG = "AssetManager";
    private static final boolean localLOGV = false;
    private final long mNative;
    @GuardedBy(value={"this"})
    private boolean mOpen = true;
    private final boolean mOwnsNative;
    private SparseArray<CharSequence> mSparseStrings;
    private CharSequence[] mStrings;
    StyleIDs mStyleIDs = null;
    private final boolean mUseSparse;

    @UnsupportedAppUsage
    StringBlock(long l, boolean bl) {
        this.mNative = l;
        this.mUseSparse = bl;
        this.mOwnsNative = false;
    }

    public StringBlock(byte[] arrby, int n, int n2, boolean bl) {
        this.mNative = StringBlock.nativeCreate(arrby, n, n2);
        this.mUseSparse = bl;
        this.mOwnsNative = true;
    }

    public StringBlock(byte[] arrby, boolean bl) {
        this.mNative = StringBlock.nativeCreate(arrby, 0, arrby.length);
        this.mUseSparse = bl;
        this.mOwnsNative = true;
    }

    private static void addParagraphSpan(Spannable spannable, Object object, int n, int n2) {
        int n3;
        int n4;
        block9 : {
            int n5;
            block8 : {
                n5 = spannable.length();
                n3 = n;
                if (n != 0) {
                    n3 = n;
                    if (n != n5) {
                        n3 = n;
                        if (spannable.charAt(n - 1) != '\n') {
                            n3 = n;
                            do {
                                n3 = n = n3 - 1;
                                if (n <= 0) break block8;
                                n3 = n;
                            } while (spannable.charAt(n - 1) != '\n');
                            n3 = n;
                        }
                    }
                }
            }
            n4 = n2;
            if (n2 != 0) {
                n4 = n2;
                if (n2 != n5) {
                    n4 = n2;
                    if (spannable.charAt(n2 - 1) != '\n') {
                        do {
                            n4 = n = n2 + 1;
                            if (n >= n5) break block9;
                            n2 = n;
                        } while (spannable.charAt(n - 1) != '\n');
                        n4 = n;
                    }
                }
            }
        }
        spannable.setSpan(object, n3, n4, 51);
    }

    private CharSequence applyStyles(String charSequence, int[] arrn, StyleIDs styleIDs) {
        if (arrn.length == 0) {
            return charSequence;
        }
        charSequence = new SpannableString(charSequence);
        for (int i = 0; i < arrn.length; i += 3) {
            int n;
            int n2 = arrn[i];
            if (n2 == styleIDs.boldId) {
                ((SpannableString)charSequence).setSpan(new StyleSpan(1), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.italicId) {
                ((SpannableString)charSequence).setSpan(new StyleSpan(2), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.underlineId) {
                ((SpannableString)charSequence).setSpan(new UnderlineSpan(), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.ttId) {
                ((SpannableString)charSequence).setSpan(new TypefaceSpan("monospace"), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.bigId) {
                ((SpannableString)charSequence).setSpan(new RelativeSizeSpan(1.25f), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.smallId) {
                ((SpannableString)charSequence).setSpan(new RelativeSizeSpan(0.8f), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.subId) {
                ((SpannableString)charSequence).setSpan(new SubscriptSpan(), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.supId) {
                ((SpannableString)charSequence).setSpan(new SuperscriptSpan(), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.strikeId) {
                ((SpannableString)charSequence).setSpan(new StrikethroughSpan(), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (n2 == styleIDs.listItemId) {
                StringBlock.addParagraphSpan((Spannable)charSequence, new BulletSpan(10), arrn[i + 1], arrn[i + 2] + 1);
                continue;
            }
            if (n2 == styleIDs.marqueeId) {
                ((SpannableString)charSequence).setSpan((Object)TextUtils.TruncateAt.MARQUEE, arrn[i + 1], arrn[i + 2] + 1, 18);
                continue;
            }
            String string2 = StringBlock.nativeGetString(this.mNative, n2);
            if (string2.startsWith("font;")) {
                String string3 = StringBlock.subtag(string2, ";height=");
                if (string3 != null) {
                    StringBlock.addParagraphSpan((Spannable)charSequence, new Height(Integer.parseInt(string3)), arrn[i + 1], arrn[i + 2] + 1);
                }
                if ((string3 = StringBlock.subtag(string2, ";size=")) != null) {
                    ((SpannableString)charSequence).setSpan(new AbsoluteSizeSpan(Integer.parseInt(string3), true), arrn[i + 1], arrn[i + 2] + 1, 33);
                }
                if ((string3 = StringBlock.subtag(string2, ";fgcolor=")) != null) {
                    ((SpannableString)charSequence).setSpan(StringBlock.getColor(string3, true), arrn[i + 1], arrn[i + 2] + 1, 33);
                }
                if ((string3 = StringBlock.subtag(string2, ";color=")) != null) {
                    ((SpannableString)charSequence).setSpan(StringBlock.getColor(string3, true), arrn[i + 1], arrn[i + 2] + 1, 33);
                }
                if ((string3 = StringBlock.subtag(string2, ";bgcolor=")) != null) {
                    ((SpannableString)charSequence).setSpan(StringBlock.getColor(string3, false), arrn[i + 1], arrn[i + 2] + 1, 33);
                }
                if ((string2 = StringBlock.subtag(string2, ";face=")) == null) continue;
                ((SpannableString)charSequence).setSpan(new TypefaceSpan(string2), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (string2.startsWith("a;")) {
                if ((string2 = StringBlock.subtag(string2, ";href=")) == null) continue;
                ((SpannableString)charSequence).setSpan(new URLSpan(string2), arrn[i + 1], arrn[i + 2] + 1, 33);
                continue;
            }
            if (!string2.startsWith("annotation;")) continue;
            int n3 = string2.length();
            int n4 = string2.indexOf(59);
            while (n4 < n3 && (n = string2.indexOf(61, n4)) >= 0) {
                int n5;
                n2 = n5 = string2.indexOf(59, n);
                if (n5 < 0) {
                    n2 = n3;
                }
                ((SpannableString)charSequence).setSpan(new Annotation(string2.substring(n4 + 1, n), string2.substring(n + 1, n2)), arrn[i + 1], arrn[i + 2] + 1, 33);
                n4 = n2;
            }
        }
        return new SpannedString(charSequence);
    }

    private static CharacterStyle getColor(String object, boolean bl) {
        int n;
        int n2 = n = -16777216;
        if (!TextUtils.isEmpty((CharSequence)object)) {
            if (((String)object).startsWith("@")) {
                Resources resources = Resources.getSystem();
                int n3 = resources.getIdentifier(((String)object).substring(1), "color", "android");
                n2 = n;
                if (n3 != 0) {
                    object = resources.getColorStateList(n3, null);
                    if (bl) {
                        return new TextAppearanceSpan(null, 0, 0, (ColorStateList)object, null);
                    }
                    n2 = ((ColorStateList)object).getDefaultColor();
                }
            } else {
                try {
                    n2 = Color.parseColor((String)object);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    n2 = -16777216;
                }
            }
        }
        if (bl) {
            return new ForegroundColorSpan(n2);
        }
        return new BackgroundColorSpan(n2);
    }

    private static native long nativeCreate(byte[] var0, int var1, int var2);

    private static native void nativeDestroy(long var0);

    private static native int nativeGetSize(long var0);

    private static native String nativeGetString(long var0, int var2);

    private static native int[] nativeGetStyle(long var0, int var2);

    private static String subtag(String string2, String string3) {
        int n = string2.indexOf(string3);
        if (n < 0) {
            return null;
        }
        int n2 = n + string3.length();
        n = string2.indexOf(59, n2);
        if (n < 0) {
            return string2.substring(n2);
        }
        return string2.substring(n2, n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() throws Throwable {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                if (this.mOwnsNative) {
                    StringBlock.nativeDestroy(this.mNative);
                }
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        try {
            super.finalize();
            return;
        }
        finally {
            this.close();
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public CharSequence get(int n) {
        synchronized (this) {
            Object object;
            int n2;
            if (this.mStrings != null) {
                object = this.mStrings[n];
                if (object != null) {
                    return object;
                }
            } else if (this.mSparseStrings != null) {
                object = this.mSparseStrings.get(n);
                if (object != null) {
                    return object;
                }
            } else {
                n2 = StringBlock.nativeGetSize(this.mNative);
                if (this.mUseSparse && n2 > 250) {
                    object = new SparseArray();
                    this.mSparseStrings = object;
                } else {
                    this.mStrings = new CharSequence[n2];
                }
            }
            String string2 = StringBlock.nativeGetString(this.mNative, n);
            object = string2;
            int[] arrn = StringBlock.nativeGetStyle(this.mNative, n);
            if (arrn != null) {
                if (this.mStyleIDs == null) {
                    this.mStyleIDs = object = new StyleIDs();
                }
                for (n2 = 0; n2 < arrn.length; n2 += 3) {
                    int n3 = arrn[n2];
                    if (n3 == this.mStyleIDs.boldId || n3 == this.mStyleIDs.italicId || n3 == this.mStyleIDs.underlineId || n3 == this.mStyleIDs.ttId || n3 == this.mStyleIDs.bigId || n3 == this.mStyleIDs.smallId || n3 == this.mStyleIDs.subId || n3 == this.mStyleIDs.supId || n3 == this.mStyleIDs.strikeId || n3 == this.mStyleIDs.listItemId || n3 == this.mStyleIDs.marqueeId) continue;
                    object = StringBlock.nativeGetString(this.mNative, n3);
                    if (((String)object).equals("b")) {
                        this.mStyleIDs.boldId = n3;
                        continue;
                    }
                    if (((String)object).equals("i")) {
                        this.mStyleIDs.italicId = n3;
                        continue;
                    }
                    if (((String)object).equals("u")) {
                        this.mStyleIDs.underlineId = n3;
                        continue;
                    }
                    if (((String)object).equals("tt")) {
                        this.mStyleIDs.ttId = n3;
                        continue;
                    }
                    if (((String)object).equals("big")) {
                        this.mStyleIDs.bigId = n3;
                        continue;
                    }
                    if (((String)object).equals("small")) {
                        this.mStyleIDs.smallId = n3;
                        continue;
                    }
                    if (((String)object).equals("sup")) {
                        this.mStyleIDs.supId = n3;
                        continue;
                    }
                    if (((String)object).equals("sub")) {
                        this.mStyleIDs.subId = n3;
                        continue;
                    }
                    if (((String)object).equals("strike")) {
                        this.mStyleIDs.strikeId = n3;
                        continue;
                    }
                    if (((String)object).equals("li")) {
                        this.mStyleIDs.listItemId = n3;
                        continue;
                    }
                    if (!((String)object).equals("marquee")) continue;
                    this.mStyleIDs.marqueeId = n3;
                }
                object = this.applyStyles(string2, arrn, this.mStyleIDs);
            }
            if (this.mStrings != null) {
                this.mStrings[n] = object;
            } else {
                this.mSparseStrings.put(n, (CharSequence)object);
            }
            return object;
        }
    }

    private static class Height
    implements LineHeightSpan.WithDensity {
        private static float sProportion = 0.0f;
        private int mSize;

        public Height(int n) {
            this.mSize = n;
        }

        @Override
        public void chooseHeight(CharSequence charSequence, int n, int n2, int n3, int n4, Paint.FontMetricsInt fontMetricsInt) {
            this.chooseHeight(charSequence, n, n2, n3, n4, fontMetricsInt, null);
        }

        @Override
        public void chooseHeight(CharSequence object, int n, int n2, int n3, int n4, Paint.FontMetricsInt fontMetricsInt, TextPaint paint) {
            n = n2 = this.mSize;
            if (paint != null) {
                n = (int)((float)n2 * ((TextPaint)paint).density);
            }
            if (fontMetricsInt.bottom - fontMetricsInt.top < n) {
                fontMetricsInt.top = fontMetricsInt.bottom - n;
                fontMetricsInt.ascent -= n;
            } else {
                if (sProportion == 0.0f) {
                    paint = new Paint();
                    paint.setTextSize(100.0f);
                    object = new Rect();
                    paint.getTextBounds("ABCDEFG", 0, 7, (Rect)object);
                    sProportion = (float)((Rect)object).top / paint.ascent();
                }
                if (n - fontMetricsInt.descent >= (n2 = (int)Math.ceil((float)(-fontMetricsInt.top) * sProportion))) {
                    fontMetricsInt.top = fontMetricsInt.bottom - n;
                    fontMetricsInt.ascent = fontMetricsInt.descent - n;
                } else if (n >= n2) {
                    fontMetricsInt.ascent = n2 = -n2;
                    fontMetricsInt.top = n2;
                    fontMetricsInt.descent = n = fontMetricsInt.top + n;
                    fontMetricsInt.bottom = n;
                } else {
                    fontMetricsInt.ascent = n = -n;
                    fontMetricsInt.top = n;
                    fontMetricsInt.descent = 0;
                    fontMetricsInt.bottom = 0;
                }
            }
        }
    }

    static final class StyleIDs {
        private int bigId = -1;
        private int boldId = -1;
        private int italicId = -1;
        private int listItemId = -1;
        private int marqueeId = -1;
        private int smallId = -1;
        private int strikeId = -1;
        private int subId = -1;
        private int supId = -1;
        private int ttId = -1;
        private int underlineId = -1;

        StyleIDs() {
        }
    }

}

