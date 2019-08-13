/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.lang.UCharacter
 *  android.icu.text.CaseMap
 *  android.icu.text.Edits
 *  android.icu.text.Edits$Iterator
 *  android.icu.util.ULocale
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.icu.lang.UCharacter;
import android.icu.text.CaseMap;
import android.icu.text.Edits;
import android.icu.util.ULocale;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.sysprop.DisplayProperties;
import android.text.AndroidCharacter;
import android.text.Annotation;
import android.text.AutoGrowArray;
import android.text.BidiFormatter;
import android.text.Editable;
import android.text.GetChars;
import android.text.Html;
import android.text.MeasuredParagraph;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextPaint;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.EasyEditSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.LineBackgroundSpan;
import android.text.style.LineHeightSpan;
import android.text.style.LocaleSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ReplacementSpan;
import android.text.style.ScaleXSpan;
import android.text.style.SpellCheckSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuggestionRangeSpan;
import android.text.style.SuggestionSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.TtsSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.text.style.UpdateAppearance;
import android.util.Log;
import android.util.Printer;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Array;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class TextUtils {
    public static final int ABSOLUTE_SIZE_SPAN = 16;
    public static final int ACCESSIBILITY_CLICKABLE_SPAN = 25;
    public static final int ACCESSIBILITY_URL_SPAN = 26;
    public static final int ALIGNMENT_SPAN = 1;
    public static final int ANNOTATION = 18;
    public static final int BACKGROUND_COLOR_SPAN = 12;
    public static final int BULLET_SPAN = 8;
    public static final int CAP_MODE_CHARACTERS = 4096;
    public static final int CAP_MODE_SENTENCES = 16384;
    public static final int CAP_MODE_WORDS = 8192;
    public static final Parcelable.Creator<CharSequence> CHAR_SEQUENCE_CREATOR = new Parcelable.Creator<CharSequence>(){

        @Override
        public CharSequence createFromParcel(Parcel object) {
            int n = ((Parcel)object).readInt();
            CharSequence charSequence = ((Parcel)object).readString();
            if (charSequence == null) {
                return null;
            }
            if (n == 1) {
                return charSequence;
            }
            charSequence = new SpannableString(charSequence);
            block30 : while ((n = ((Parcel)object).readInt()) != 0) {
                switch (n) {
                    default: {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("bogus span encoding ");
                        ((StringBuilder)object).append(n);
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    case 28: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new LineHeightSpan.Standard((Parcel)object));
                        continue block30;
                    }
                    case 27: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new LineBackgroundSpan.Standard((Parcel)object));
                        continue block30;
                    }
                    case 26: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new AccessibilityURLSpan((Parcel)object));
                        continue block30;
                    }
                    case 25: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new AccessibilityClickableSpan((Parcel)object));
                        continue block30;
                    }
                    case 24: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new TtsSpan((Parcel)object));
                        continue block30;
                    }
                    case 23: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new LocaleSpan((Parcel)object));
                        continue block30;
                    }
                    case 22: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new EasyEditSpan((Parcel)object));
                        continue block30;
                    }
                    case 21: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new SuggestionRangeSpan((Parcel)object));
                        continue block30;
                    }
                    case 20: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new SpellCheckSpan((Parcel)object));
                        continue block30;
                    }
                    case 19: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new SuggestionSpan((Parcel)object));
                        continue block30;
                    }
                    case 18: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new Annotation((Parcel)object));
                        continue block30;
                    }
                    case 17: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new TextAppearanceSpan((Parcel)object));
                        continue block30;
                    }
                    case 16: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new AbsoluteSizeSpan((Parcel)object));
                        continue block30;
                    }
                    case 15: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new SubscriptSpan((Parcel)object));
                        continue block30;
                    }
                    case 14: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new SuperscriptSpan((Parcel)object));
                        continue block30;
                    }
                    case 13: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new TypefaceSpan((Parcel)object));
                        continue block30;
                    }
                    case 12: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new BackgroundColorSpan((Parcel)object));
                        continue block30;
                    }
                    case 11: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new URLSpan((Parcel)object));
                        continue block30;
                    }
                    case 10: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new LeadingMarginSpan.Standard((Parcel)object));
                        continue block30;
                    }
                    case 9: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new QuoteSpan((Parcel)object));
                        continue block30;
                    }
                    case 8: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new BulletSpan((Parcel)object));
                        continue block30;
                    }
                    case 7: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new StyleSpan((Parcel)object));
                        continue block30;
                    }
                    case 6: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new UnderlineSpan((Parcel)object));
                        continue block30;
                    }
                    case 5: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new StrikethroughSpan((Parcel)object));
                        continue block30;
                    }
                    case 4: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new ScaleXSpan((Parcel)object));
                        continue block30;
                    }
                    case 3: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new RelativeSizeSpan((Parcel)object));
                        continue block30;
                    }
                    case 2: {
                        TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new ForegroundColorSpan((Parcel)object));
                        continue block30;
                    }
                    case 1: 
                }
                TextUtils.readSpan((Parcel)object, (Spannable)charSequence, new AlignmentSpan.Standard((Parcel)object));
            }
            return charSequence;
        }

        public CharSequence[] newArray(int n) {
            return new CharSequence[n];
        }
    };
    public static final int EASY_EDIT_SPAN = 22;
    static final char ELLIPSIS_FILLER = '\ufeff';
    private static final String ELLIPSIS_NORMAL = "\u2026";
    private static final String ELLIPSIS_TWO_DOTS = "\u2025";
    private static String[] EMPTY_STRING_ARRAY;
    public static final int FIRST_SPAN = 1;
    public static final int FOREGROUND_COLOR_SPAN = 2;
    public static final int LAST_SPAN = 28;
    public static final int LEADING_MARGIN_SPAN = 10;
    public static final int LINE_BACKGROUND_SPAN = 27;
    private static final int LINE_FEED_CODE_POINT = 10;
    public static final int LINE_HEIGHT_SPAN = 28;
    public static final int LOCALE_SPAN = 23;
    private static final int NBSP_CODE_POINT = 160;
    private static final int PARCEL_SAFE_TEXT_LENGTH = 100000;
    public static final int QUOTE_SPAN = 9;
    public static final int RELATIVE_SIZE_SPAN = 3;
    public static final int SAFE_STRING_FLAG_FIRST_LINE = 4;
    public static final int SAFE_STRING_FLAG_SINGLE_LINE = 2;
    public static final int SAFE_STRING_FLAG_TRIM = 1;
    public static final int SCALE_X_SPAN = 4;
    public static final int SPELL_CHECK_SPAN = 20;
    public static final int STRIKETHROUGH_SPAN = 5;
    public static final int STYLE_SPAN = 7;
    public static final int SUBSCRIPT_SPAN = 15;
    public static final int SUGGESTION_RANGE_SPAN = 21;
    public static final int SUGGESTION_SPAN = 19;
    public static final int SUPERSCRIPT_SPAN = 14;
    private static final String TAG = "TextUtils";
    public static final int TEXT_APPEARANCE_SPAN = 17;
    public static final int TTS_SPAN = 24;
    public static final int TYPEFACE_SPAN = 13;
    public static final int UNDERLINE_SPAN = 6;
    public static final int URL_SPAN = 11;
    private static Object sLock;
    private static char[] sTemp;

    static {
        sLock = new Object();
        sTemp = null;
        EMPTY_STRING_ARRAY = new String[0];
    }

    private TextUtils() {
    }

    @Deprecated
    public static CharSequence commaEllipsize(CharSequence charSequence, TextPaint textPaint, float f, String string2, String string3) {
        return TextUtils.commaEllipsize(charSequence, textPaint, f, string2, string3, TextDirectionHeuristics.FIRSTSTRONG_LTR);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Deprecated
    public static CharSequence commaEllipsize(CharSequence charSequence, TextPaint object, float f, String string2, String string3, TextDirectionHeuristic textDirectionHeuristic) {
        block16 : {
            measuredParagraph = null;
            object2 = null;
            measuredParagraph2 = null;
            measuredParagraph3 = measuredParagraph;
            object3 = object2;
            n3 = charSequence.length();
            measuredParagraph3 = measuredParagraph;
            object3 = object2;
            measuredParagraph3 = measuredParagraph = MeasuredParagraph.buildForMeasurement((TextPaint)object, charSequence, 0, n3, textDirectionHeuristic, null);
            object3 = object2;
            f2 = measuredParagraph.getWholeWidth();
            if (f2 <= f) {
                measuredParagraph.recycle();
                if (false == false) return charSequence;
                throw new NullPointerException();
            }
            measuredParagraph3 = measuredParagraph;
            object3 = object2;
            arrc = measuredParagraph.getChars();
            n4 = 0;
            for (n2 = 0; n2 < n3; ++n2) {
                n = n4;
                if (arrc[n2] == ',') {
                    n = n4 + 1;
                }
                n4 = n;
            }
            n5 = n4 + 1;
            n = 0;
            object4 = "";
            n6 = 0;
            n7 = 0;
            measuredParagraph3 = measuredParagraph;
            object3 = object2;
            arrf = measuredParagraph.getWidths().getRawArray();
            n4 = n3;
            for (n2 = 0; n2 < n4; ++n2) {
                block18 : {
                    block17 : {
                        n6 = (int)((float)n6 + arrf[n2]);
                        n3 = arrc[n2];
                        if (n3 != 44) break block17;
                        n3 = n7 + 1;
                        n8 = n5 - 1;
                        if (n8 != 1) ** GOTO lbl60
                        object2 = new StringBuilder();
                        object2.append(" ");
                        measuredParagraph3 = measuredParagraph;
                        object3 = measuredParagraph2;
                        {
                            catch (Throwable throwable) {
                                break block16;
                            }
                        }
                        object2.append(string2);
                        measuredParagraph3 = measuredParagraph;
                        object3 = measuredParagraph2;
                        object3 = object2 = object2.toString();
                        ** GOTO lbl75
lbl60: // 1 sources:
                        measuredParagraph3 = measuredParagraph;
                        object3 = measuredParagraph2;
                        measuredParagraph3 = measuredParagraph;
                        object3 = measuredParagraph2;
                        object2 = new StringBuilder();
                        measuredParagraph3 = measuredParagraph;
                        object3 = measuredParagraph2;
                        object2.append(" ");
                        measuredParagraph3 = measuredParagraph2;
                        object2.append(String.format(string3, new Object[]{n8}));
                        measuredParagraph3 = measuredParagraph2;
                        object3 = object2.toString();
lbl75: // 2 sources:
                        measuredParagraph3 = measuredParagraph2;
                        measuredParagraph3 = measuredParagraph2 = MeasuredParagraph.buildForMeasurement((TextPaint)object, (CharSequence)object3, 0, object3.length(), textDirectionHeuristic, measuredParagraph2);
                        f3 = measuredParagraph2.getWholeWidth();
                        n5 = n8;
                        measuredParagraph3 = measuredParagraph2;
                        n7 = n3;
                        if ((float)n6 + f3 <= f) {
                            n = n2 + 1;
                            n5 = n8;
                            measuredParagraph3 = measuredParagraph2;
                            object4 = object3;
                            n7 = n3;
                        }
                        break block18;
                    }
                    measuredParagraph3 = measuredParagraph2;
                }
                measuredParagraph2 = measuredParagraph3;
            }
            measuredParagraph3 = measuredParagraph2;
            try {
                measuredParagraph3 = measuredParagraph2;
                object = new SpannableStringBuilder((CharSequence)object4);
                measuredParagraph3 = measuredParagraph2;
                object.insert(0, charSequence, 0, n);
                measuredParagraph.recycle();
                if (measuredParagraph2 == null) return object;
                measuredParagraph2.recycle();
                return object;
            }
            catch (Throwable throwable) {
                measuredParagraph2 = measuredParagraph3;
            }
            break block16;
            catch (Throwable throwable) {
                measuredParagraph2 = object3;
                measuredParagraph = measuredParagraph3;
            }
        }
        if (measuredParagraph != null) {
            measuredParagraph.recycle();
        }
        if (measuredParagraph2 == null) throw var0_4;
        measuredParagraph2.recycle();
        throw var0_4;
    }

    public static CharSequence concat(CharSequence ... arrcharSequence) {
        if (arrcharSequence.length == 0) {
            return "";
        }
        int n = arrcharSequence.length;
        int n2 = 0;
        int n3 = 0;
        if (n == 1) {
            return arrcharSequence[0];
        }
        int n4 = 0;
        int n5 = arrcharSequence.length;
        int n6 = 0;
        do {
            n = n4;
            if (n6 >= n5) break;
            if (arrcharSequence[n6] instanceof Spanned) {
                n = 1;
                break;
            }
            ++n6;
        } while (true);
        if (n != 0) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            n6 = arrcharSequence.length;
            for (n = n3; n < n6; ++n) {
                CharSequence charSequence = arrcharSequence[n];
                if (charSequence == null) {
                    charSequence = "null";
                }
                spannableStringBuilder.append(charSequence);
            }
            return new SpannedString(spannableStringBuilder);
        }
        StringBuilder stringBuilder = new StringBuilder();
        n6 = arrcharSequence.length;
        for (n = n2; n < n6; ++n) {
            stringBuilder.append(arrcharSequence[n]);
        }
        return stringBuilder.toString();
    }

    public static void copySpansFrom(Spanned spanned, int n, int n2, Class arrobject, Spannable spannable, int n3) {
        Object[] arrobject2 = arrobject;
        if (arrobject == null) {
            arrobject2 = Object.class;
        }
        arrobject = spanned.getSpans(n, n2, arrobject2);
        for (int i = 0; i < arrobject.length; ++i) {
            int n4 = spanned.getSpanStart(arrobject[i]);
            int n5 = spanned.getSpanEnd(arrobject[i]);
            int n6 = spanned.getSpanFlags(arrobject[i]);
            int n7 = n4;
            if (n4 < n) {
                n7 = n;
            }
            n4 = n5;
            if (n5 > n2) {
                n4 = n2;
            }
            spannable.setSpan(arrobject[i], n7 - n + n3, n4 - n + n3, n6);
        }
    }

    static boolean couldAffectRtl(char c) {
        boolean bl = '\u0590' <= c && c <= '\u08ff' || c == '\u200e' || c == '\u200f' || '\u202a' <= c && c <= '\u202e' || '\u2066' <= c && c <= '\u2069' || '\ud800' <= c && c <= '\udfff' || '\ufb1d' <= c && c <= '\ufdff' || '\ufe70' <= c && c <= '\ufefe';
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static boolean delimitedStringContains(String var0, char var1_1, String var2_2) {
        if (TextUtils.isEmpty(var0) != false) return false;
        if (TextUtils.isEmpty(var2_2)) {
            return false;
        }
        var3_3 = -1;
        var4_4 = var0.length();
        do lbl-1000: // 3 sources:
        {
            var3_3 = var5_5 = var0.indexOf(var2_2, var3_3 + 1);
            if (var5_5 == -1) return false;
            if (var3_3 > 0 && var0.charAt(var3_3 - 1) != var1_1) ** GOTO lbl-1000
            var5_5 = var2_2.length() + var3_3;
            if (var5_5 != var4_4) continue;
            return true;
        } while (var0.charAt(var5_5) != var1_1);
        return true;
    }

    static boolean doesNotNeedBidi(char[] arrc, int n, int n2) {
        for (int i = n; i < n + n2; ++i) {
            if (!TextUtils.couldAffectRtl(arrc[i])) continue;
            return false;
        }
        return true;
    }

    public static void dumpSpans(CharSequence charSequence, Printer printer, String string2) {
        if (charSequence instanceof Spanned) {
            Spanned spanned = (Spanned)charSequence;
            Object[] arrobject = spanned.getSpans(0, charSequence.length(), Object.class);
            for (int i = 0; i < arrobject.length; ++i) {
                Object object = arrobject[i];
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append((Object)charSequence.subSequence(spanned.getSpanStart(object), spanned.getSpanEnd(object)));
                stringBuilder.append(": ");
                stringBuilder.append(Integer.toHexString(System.identityHashCode(object)));
                stringBuilder.append(" ");
                stringBuilder.append(object.getClass().getCanonicalName());
                stringBuilder.append(" (");
                stringBuilder.append(spanned.getSpanStart(object));
                stringBuilder.append("-");
                stringBuilder.append(spanned.getSpanEnd(object));
                stringBuilder.append(") fl=#");
                stringBuilder.append(spanned.getSpanFlags(object));
                printer.println(stringBuilder.toString());
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append((Object)charSequence);
            stringBuilder.append(": (no spans)");
            printer.println(stringBuilder.toString());
        }
    }

    public static CharSequence ellipsize(CharSequence charSequence, TextPaint textPaint, float f, TruncateAt truncateAt) {
        return TextUtils.ellipsize(charSequence, textPaint, f, truncateAt, false, null);
    }

    public static CharSequence ellipsize(CharSequence charSequence, TextPaint textPaint, float f, TruncateAt truncateAt, boolean bl, EllipsizeCallback ellipsizeCallback) {
        return TextUtils.ellipsize(charSequence, textPaint, f, truncateAt, bl, ellipsizeCallback, TextDirectionHeuristics.FIRSTSTRONG_LTR, TextUtils.getEllipsisString(truncateAt));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static CharSequence ellipsize(CharSequence var0, TextPaint var1_6, float var2_7, TruncateAt var3_8, boolean var4_9, EllipsizeCallback var5_10, TextDirectionHeuristic var6_11, String var7_12) {
        block28 : {
            block25 : {
                block24 : {
                    var8_15 = var0.length();
                    var9_16 = null;
                    var9_16 = var6_13 = MeasuredParagraph.buildForMeasurement((TextPaint)var1_6, var0, 0, var0.length(), var6_13, null);
                    if (!(var6_13.getWholeWidth() <= var2_7)) break block24;
                    if (var5_12 != null) {
                        var9_16 = var6_13;
                        var5_12.ellipsized(0, 0);
                    }
                    var6_13.recycle();
                    return var0;
                }
                var10_17 = var1_6.measureText((String)var7_14);
                var10_17 = var2_7 - var10_17;
                var11_18 = 0;
                if (var10_17 < 0.0f) {
                    var12_19 = var8_15;
                } else {
                    var2_7 = var10_17;
                    if (var3_8 /* !! */  == TruncateAt.START) {
                        var2_7 = var10_17;
                        var12_19 = var8_15 - var6_13.breakText(var8_15, false, var10_17);
                        break block25;
                    }
                    var2_7 = var10_17;
                    if (var3_8 /* !! */  != TruncateAt.END) {
                        var2_7 = var10_17;
                        if (var3_8 /* !! */  != TruncateAt.END_SMALL) {
                            var2_7 = var10_17;
                            var12_19 = var8_15 - var6_13.breakText(var8_15, false, var10_17 / 2.0f);
                            var2_7 = var10_17;
                            var2_7 = var10_17 -= var6_13.measure(var12_19, var8_15);
                            var11_18 = var6_13.breakText(var12_19, true, var10_17);
                            break block25;
                        }
                    }
                    var2_7 = var10_17;
                    var11_18 = var6_13.breakText(var8_15, true, var10_17);
                    var12_19 = var8_15;
                }
            }
            if (var5_12 == null) ** GOTO lbl42
            var5_12.ellipsized(var11_18, var12_19);
lbl42: // 2 sources:
            var3_9 = var6_13.getChars();
            var1_6 = var0 instanceof Spanned != false ? (Spanned)var0 : null;
            var13_20 = var12_19 - var11_18;
            var14_21 = var8_15 - var13_20;
            if (var4_11 != false) {
                block27 : {
                    block26 : {
                        if (var14_21 > 0) {
                            if (var13_20 < var7_14.length()) break block26;
                            var7_14.getChars(0, var7_14.length(), var3_9, var11_18);
                            var11_18 += var7_14.length();
                        }
                    }
                    while (var11_18 < var12_19) {
                        var3_9[var11_18] = (char)65279;
                        ++var11_18;
                    }
                    var0 = new String(var3_9, 0, var8_15);
                    if (var1_6 != null) break block27;
                    var6_13.recycle();
                    return var0;
                }
                var3_10 = new SpannableString(var0);
                TextUtils.copySpansFrom((Spanned)var1_6, 0, var8_15, Object.class, var3_10, 0);
                var6_13.recycle();
                return var3_10;
            }
            if (var14_21 == 0) {
                var6_13.recycle();
                return "";
            }
            if (var1_6 == null) {
                var0 = new StringBuilder(var14_21 + var7_14.length());
                var0.append(var3_9, 0, var11_18);
                var0.append((String)var7_14);
                var0.append(var3_9, var12_19, var8_15 - var12_19);
                var0 = var0.toString();
                var6_13.recycle();
                return var0;
            }
            try {
                var1_6 = new SpannableStringBuilder();
                var1_6.append(var0, 0, var11_18);
                var1_6.append((CharSequence)var7_14);
                var1_6.append(var0, var12_19, var8_15);
                var6_13.recycle();
                return var1_6;
            }
            catch (Throwable var0_1) {}
            break block28;
            catch (Throwable var0_2) {}
            break block28;
            catch (Throwable var0_3) {
                break block28;
            }
            catch (Throwable var0_4) {
                var6_13 = var9_16;
            }
        }
        if (var6_13 == null) throw var0_5;
        var6_13.recycle();
        throw var0_5;
    }

    public static String emptyIfNull(String string2) {
        block0 : {
            if (string2 != null) break block0;
            string2 = "";
        }
        return string2;
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        if (charSequence == charSequence2) {
            return true;
        }
        if (charSequence != null && charSequence2 != null && (n = charSequence.length()) == charSequence2.length()) {
            if (charSequence instanceof String && charSequence2 instanceof String) {
                return charSequence.equals(charSequence2);
            }
            for (int i = 0; i < n; ++i) {
                if (charSequence.charAt(i) == charSequence2.charAt(i)) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static CharSequence expandTemplate(CharSequence charSequence, CharSequence ... object) {
        if (((Object)object).length > 9) throw new IllegalArgumentException("max of 9 values are supported");
        charSequence = new SpannableStringBuilder(charSequence);
        int n = 0;
        do {
            block9 : {
                int n2;
                block10 : {
                    while (n < ((SpannableStringBuilder)charSequence).length()) {
                        if (((SpannableStringBuilder)charSequence).charAt(n) != '^') break block9;
                        char c = ((SpannableStringBuilder)charSequence).charAt(n + 1);
                        if (c == '^') {
                            ((SpannableStringBuilder)charSequence).delete(n + 1, n + 2);
                            ++n;
                            continue;
                        }
                        if (!Character.isDigit(c)) break block9;
                        n2 = Character.getNumericValue(c);
                        if (--n2 >= 0) {
                            if (n2 >= ((Object)object).length) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("template requests value ^");
                                stringBuilder.append(n2 + 1);
                                stringBuilder.append("; only ");
                                stringBuilder.append(((Object)object).length);
                                stringBuilder.append(" provided");
                                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                                throw illegalArgumentException;
                            }
                            ((SpannableStringBuilder)charSequence).replace(n, n + 2, (CharSequence)object[n2]);
                            n += object[n2].length();
                            continue;
                        }
                        break block10;
                    }
                    return charSequence;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("template requests value ^");
                ((StringBuilder)object).append(n2 + 1);
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(((StringBuilder)object).toString());
                throw illegalArgumentException;
            }
            ++n;
        } while (true);
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            // empty catch block
        }
        return charSequence;
    }

    public static String firstNotEmpty(String string2, String string3) {
        if (TextUtils.isEmpty(string2)) {
            string2 = Preconditions.checkStringNotEmpty(string3);
        }
        return string2;
    }

    public static CharSequence formatSelectedCount(int n) {
        return Resources.getSystem().getQuantityString(18153496, n, n);
    }

    public static int getCapsMode(CharSequence charSequence, int n, int n2) {
        int n3;
        char c;
        char c2;
        if (n < 0) {
            return 0;
        }
        int n4 = 0;
        if ((n2 & 4096) != 0) {
            n4 = 0 | 4096;
        }
        if ((n2 & 24576) == 0) {
            return n4;
        }
        while (n > 0 && ((c = charSequence.charAt(n - 1)) == '\"' || c == '\'' || Character.getType(c) == 21)) {
            --n;
        }
        for (n3 = n; n3 > 0 && ((c2 = charSequence.charAt(n3 - 1)) == ' ' || c2 == '\t'); --n3) {
        }
        if (n3 != 0 && charSequence.charAt(n3 - 1) != '\n') {
            if ((n2 & 16384) == 0) {
                n2 = n4;
                if (n != n3) {
                    n2 = n4 | 8192;
                }
                return n2;
            }
            if (n == n3) {
                return n4;
            }
            for (n2 = n3; n2 > 0 && ((c = charSequence.charAt(n2 - 1)) == '\"' || c == '\'' || Character.getType(c) == 22); --n2) {
            }
            if (n2 > 0 && ((n = (int)charSequence.charAt(n2 - 1)) == 46 || n == 63 || n == 33)) {
                if (n == 46) {
                    for (n = n2 - 2; n >= 0; --n) {
                        c = charSequence.charAt(n);
                        if (c == '.') {
                            return n4;
                        }
                        if (!Character.isLetter(c)) break;
                    }
                }
                return n4 | 16384;
            }
            return n4;
        }
        return n4 | 8192;
    }

    public static void getChars(CharSequence charSequence, int n, int n2, char[] arrc, int n3) {
        Class<?> class_ = charSequence.getClass();
        if (class_ == String.class) {
            ((String)charSequence).getChars(n, n2, arrc, n3);
        } else if (class_ == StringBuffer.class) {
            ((StringBuffer)charSequence).getChars(n, n2, arrc, n3);
        } else if (class_ == StringBuilder.class) {
            ((StringBuilder)charSequence).getChars(n, n2, arrc, n3);
        } else if (charSequence instanceof GetChars) {
            ((GetChars)charSequence).getChars(n, n2, arrc, n3);
        } else {
            while (n < n2) {
                arrc[n3] = charSequence.charAt(n);
                ++n;
                ++n3;
            }
        }
    }

    public static String getEllipsisString(TruncateAt object) {
        object = object == TruncateAt.END_SMALL ? ELLIPSIS_TWO_DOTS : ELLIPSIS_NORMAL;
        return object;
    }

    public static int getLayoutDirectionFromLocale(Locale locale) {
        int n;
        block0 : {
            n = 0;
            if ((locale == null || locale.equals(Locale.ROOT) || !ULocale.forLocale((Locale)locale).isRightToLeft()) && !DisplayProperties.debug_force_rtl().orElse(false).booleanValue()) break block0;
            n = 1;
        }
        return n;
    }

    public static int getOffsetAfter(CharSequence charSequence, int n) {
        int n2 = charSequence.length();
        if (n == n2) {
            return n2;
        }
        if (n == n2 - 1) {
            return n2;
        }
        n2 = charSequence.charAt(n);
        n = n2 >= 55296 && n2 <= 56319 ? ((n2 = (int)charSequence.charAt(n + 1)) >= 56320 && n2 <= 57343 ? (n += 2) : ++n) : ++n;
        int n3 = n;
        if (charSequence instanceof Spanned) {
            ReplacementSpan[] arrreplacementSpan = ((Spanned)charSequence).getSpans(n, n, ReplacementSpan.class);
            n2 = 0;
            do {
                n3 = n;
                if (n2 >= arrreplacementSpan.length) break;
                int n4 = ((Spanned)charSequence).getSpanStart(arrreplacementSpan[n2]);
                int n5 = ((Spanned)charSequence).getSpanEnd(arrreplacementSpan[n2]);
                n3 = n;
                if (n4 < n) {
                    n3 = n;
                    if (n5 > n) {
                        n3 = n5;
                    }
                }
                ++n2;
                n = n3;
            } while (true);
        }
        return n3;
    }

    public static int getOffsetBefore(CharSequence charSequence, int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 0;
        }
        int n2 = charSequence.charAt(n - 1);
        n = n2 >= 56320 && n2 <= 57343 ? ((n2 = charSequence.charAt(n - 2)) >= 55296 && n2 <= 56319 ? (n -= 2) : --n) : --n;
        int n3 = n;
        if (charSequence instanceof Spanned) {
            ReplacementSpan[] arrreplacementSpan = ((Spanned)charSequence).getSpans(n, n, ReplacementSpan.class);
            n2 = 0;
            do {
                n3 = n;
                if (n2 >= arrreplacementSpan.length) break;
                int n4 = ((Spanned)charSequence).getSpanStart(arrreplacementSpan[n2]);
                int n5 = ((Spanned)charSequence).getSpanEnd(arrreplacementSpan[n2]);
                n3 = n;
                if (n4 < n) {
                    n3 = n;
                    if (n5 > n) {
                        n3 = n4;
                    }
                }
                ++n2;
                n = n3;
            } while (true);
        }
        return n3;
    }

    @Deprecated
    public static CharSequence getReverse(CharSequence charSequence, int n, int n2) {
        return new Reverser(charSequence, n, n2);
    }

    public static int getTrimmedLength(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        for (n = 0; n < n2 && charSequence.charAt(n) <= ' '; ++n) {
        }
        while (n2 > n && charSequence.charAt(n2 - 1) <= ' ') {
            --n2;
        }
        return n2 - n;
    }

    public static boolean hasStyleSpan(Spanned spanned) {
        boolean bl = spanned != null;
        Preconditions.checkArgument(bl);
        for (Class class_ : new Class[]{CharacterStyle.class, ParagraphStyle.class, UpdateAppearance.class}) {
            if (spanned.nextSpanTransition(-1, spanned.length(), class_) >= spanned.length()) continue;
            return true;
        }
        return false;
    }

    public static String htmlEncode(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            if (c != '\"') {
                if (c != '<') {
                    if (c != '>') {
                        if (c != '&') {
                            if (c != '\'') {
                                stringBuilder.append(c);
                                continue;
                            }
                            stringBuilder.append("&#39;");
                            continue;
                        }
                        stringBuilder.append("&amp;");
                        continue;
                    }
                    stringBuilder.append("&gt;");
                    continue;
                }
                stringBuilder.append("&lt;");
                continue;
            }
            stringBuilder.append("&quot;");
        }
        return stringBuilder.toString();
    }

    public static int indexOf(CharSequence charSequence, char c) {
        return TextUtils.indexOf(charSequence, c, 0);
    }

    public static int indexOf(CharSequence charSequence, char c, int n) {
        if (charSequence.getClass() == String.class) {
            return ((String)charSequence).indexOf(c, n);
        }
        return TextUtils.indexOf(charSequence, c, n, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, char c, int n, int n2) {
        char[] arrc = charSequence.getClass();
        if (!(charSequence instanceof GetChars) && arrc != StringBuffer.class && arrc != StringBuilder.class && arrc != String.class) {
            while (n < n2) {
                if (charSequence.charAt(n) == c) {
                    return n;
                }
                ++n;
            }
            return -1;
        }
        arrc = TextUtils.obtain(500);
        int n3 = n;
        while (n3 < n2) {
            int n4;
            n = n4 = n3 + 500;
            if (n4 > n2) {
                n = n2;
            }
            TextUtils.getChars(charSequence, n3, n, arrc, 0);
            for (n4 = 0; n4 < n - n3; ++n4) {
                if (arrc[n4] != c) continue;
                TextUtils.recycle(arrc);
                return n4 + n3;
            }
            n3 = n;
        }
        TextUtils.recycle(arrc);
        return -1;
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2) {
        return TextUtils.indexOf(charSequence, charSequence2, 0, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int n) {
        return TextUtils.indexOf(charSequence, charSequence2, n, charSequence.length());
    }

    public static int indexOf(CharSequence charSequence, CharSequence charSequence2, int n, int n2) {
        int n3 = charSequence2.length();
        if (n3 == 0) {
            return n;
        }
        char c = charSequence2.charAt(0);
        while ((n = TextUtils.indexOf(charSequence, c, n)) <= n2 - n3) {
            if (n < 0) {
                return -1;
            }
            if (TextUtils.regionMatches(charSequence, n, charSequence2, 0, n3)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static boolean isDigitsOnly(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        for (int i = 0; i < n2; i += Character.charCount((int)n)) {
            n = Character.codePointAt(charSequence, i);
            if (Character.isDigit(n)) continue;
            return false;
        }
        return true;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        boolean bl = charSequence == null || charSequence.length() == 0;
        return bl;
    }

    @Deprecated
    public static boolean isGraphic(char c) {
        int n = Character.getType(c);
        boolean bl = n != 15 && n != 16 && n != 19 && n != 0 && n != 13 && n != 14 && n != 12;
        return bl;
    }

    public static boolean isGraphic(CharSequence charSequence) {
        int n;
        int n2 = charSequence.length();
        for (int i = 0; i < n2; i += Character.charCount((int)n)) {
            n = Character.codePointAt(charSequence, i);
            int n3 = Character.getType(n);
            if (n3 == 15 || n3 == 16 || n3 == 19 || n3 == 0 || n3 == 13 || n3 == 14 || n3 == 12) continue;
            return true;
        }
        return false;
    }

    private static boolean isNewline(int n) {
        int n2 = Character.getType(n);
        boolean bl = n2 == 14 || n2 == 13 || n == 10;
        return bl;
    }

    public static boolean isPrintableAscii(char c) {
        boolean bl = ' ' <= c && c <= '~' || c == '\r' || c == '\n';
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean isPrintableAsciiOnly(CharSequence charSequence) {
        int n = charSequence.length();
        for (int i = 0; i < n; ++i) {
            if (TextUtils.isPrintableAscii(charSequence.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private static boolean isWhiteSpace(int n) {
        boolean bl = Character.isWhitespace(n) || n == 160;
        return bl;
    }

    public static String join(CharSequence charSequence, Iterable object) {
        Iterator iterator = object.iterator();
        if (!iterator.hasNext()) {
            return "";
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(iterator.next());
        while (iterator.hasNext()) {
            ((StringBuilder)object).append(charSequence);
            ((StringBuilder)object).append(iterator.next());
        }
        return ((StringBuilder)object).toString();
    }

    public static String join(CharSequence charSequence, Object[] arrobject) {
        int n = arrobject.length;
        if (n == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(arrobject[0]);
        for (int i = 1; i < n; ++i) {
            stringBuilder.append(charSequence);
            stringBuilder.append(arrobject[i]);
        }
        return stringBuilder.toString();
    }

    public static int lastIndexOf(CharSequence charSequence, char c) {
        return TextUtils.lastIndexOf(charSequence, c, charSequence.length() - 1);
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int n) {
        if (charSequence.getClass() == String.class) {
            return ((String)charSequence).lastIndexOf(c, n);
        }
        return TextUtils.lastIndexOf(charSequence, c, 0, n);
    }

    public static int lastIndexOf(CharSequence charSequence, char c, int n, int n2) {
        if (n2 < 0) {
            return -1;
        }
        int n3 = n2;
        if (n2 >= charSequence.length()) {
            n3 = charSequence.length() - 1;
        }
        ++n3;
        char[] arrc = charSequence.getClass();
        if (!(charSequence instanceof GetChars) && arrc != StringBuffer.class && arrc != StringBuilder.class && arrc != String.class) {
            for (n2 = n3 - 1; n2 >= n; --n2) {
                if (charSequence.charAt(n2) != c) continue;
                return n2;
            }
            return -1;
        }
        arrc = TextUtils.obtain(500);
        while (n < n3) {
            int n4;
            n2 = n4 = n3 - 500;
            if (n4 < n) {
                n2 = n;
            }
            TextUtils.getChars(charSequence, n2, n3, arrc, 0);
            for (n3 = n3 - n2 - 1; n3 >= 0; --n3) {
                if (arrc[n3] != c) continue;
                TextUtils.recycle(arrc);
                return n3 + n2;
            }
            n3 = n2;
        }
        TextUtils.recycle(arrc);
        return -1;
    }

    public static int length(String string2) {
        int n = string2 != null ? string2.length() : 0;
        return n;
    }

    public static CharSequence listEllipsize(Context object, List<CharSequence> object2, String string2, TextPaint textPaint, float f, int n) {
        BidiFormatter bidiFormatter;
        int n2;
        if (object2 == null) {
            return "";
        }
        int n3 = object2.size();
        if (n3 == 0) {
            return "";
        }
        if (object == null) {
            object = null;
            bidiFormatter = BidiFormatter.getInstance();
        } else {
            object = ((Context)object).getResources();
            bidiFormatter = BidiFormatter.getInstance(((Resources)object).getConfiguration().getLocales().get(0));
        }
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        int[] arrn = new int[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            spannableStringBuilder.append(bidiFormatter.unicodeWrap(object2.get(n2)));
            if (n2 != n3 - 1) {
                spannableStringBuilder.append(string2);
            }
            arrn[n2] = spannableStringBuilder.length();
        }
        for (n2 = n3 - 1; n2 >= 0; --n2) {
            spannableStringBuilder.delete(arrn[n2], spannableStringBuilder.length());
            int n4 = n3 - n2 - 1;
            if (n4 > 0) {
                object2 = object == null ? ELLIPSIS_NORMAL : ((Resources)object).getQuantityString(n, n4, n4);
                spannableStringBuilder.append(bidiFormatter.unicodeWrap((CharSequence)object2));
            }
            if (!(textPaint.measureText(spannableStringBuilder, 0, spannableStringBuilder.length()) <= f)) continue;
            return spannableStringBuilder;
        }
        return "";
    }

    public static CharSequence makeSafeForPresentation(String object, int n, float f, int n2) {
        int n3;
        boolean bl = true;
        boolean bl2 = (n2 & 4) != 0;
        boolean bl3 = (n2 & 2) != 0;
        boolean bl4 = (n2 & 1) != 0;
        Preconditions.checkNotNull(object);
        Preconditions.checkArgumentNonnegative(n);
        Preconditions.checkArgumentNonNegative(f, "ellipsizeDip");
        Preconditions.checkFlagsArgument(n2, 7);
        boolean bl5 = bl;
        if (bl2) {
            bl5 = !bl3 ? bl : false;
        }
        Preconditions.checkArgument(bl5, "Cannot set SAFE_STRING_FLAG_SINGLE_LINE and SAFE_STRING_FLAG_FIRST_LINE at thesame time");
        if (n > 0) {
            object = ((String)object).substring(0, Math.min(((String)object).length(), n));
        }
        object = new StringWithRemovedChars(Html.fromHtml((String)object).toString());
        n2 = -1;
        int n4 = -1;
        int n5 = ((StringWithRemovedChars)object).length();
        for (n = 0; n < n5; n += n3) {
            int n6;
            int n7 = ((StringWithRemovedChars)object).codePointAt(n);
            int n8 = Character.getType(n7);
            n3 = Character.charCount(n7);
            bl5 = TextUtils.isNewline(n7);
            if (bl2 && bl5) {
                ((StringWithRemovedChars)object).removeAllCharAfter(n);
                break;
            }
            if (bl3 && bl5) {
                ((StringWithRemovedChars)object).removeRange(n, n + n3);
                n6 = n2;
                n8 = n4;
            } else if (n8 == 15 && !bl5) {
                ((StringWithRemovedChars)object).removeRange(n, n + n3);
                n6 = n2;
                n8 = n4;
            } else {
                n6 = n2;
                n8 = n4;
                if (bl4) {
                    n6 = n2;
                    n8 = n4;
                    if (!TextUtils.isWhiteSpace(n7)) {
                        n4 = n2;
                        if (n2 == -1) {
                            n4 = n;
                        }
                        n8 = n + n3;
                        n6 = n4;
                    }
                }
            }
            n2 = n6;
            n4 = n8;
        }
        if (bl4) {
            if (n2 == -1) {
                ((StringWithRemovedChars)object).removeAllCharAfter(0);
            } else {
                if (n2 > 0) {
                    ((StringWithRemovedChars)object).removeAllCharBefore(n2);
                }
                if (n4 < n5) {
                    ((StringWithRemovedChars)object).removeAllCharAfter(n4);
                }
            }
        }
        if (f == 0.0f) {
            return ((StringWithRemovedChars)object).toString();
        }
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(42.0f);
        return TextUtils.ellipsize(((StringWithRemovedChars)object).toString(), textPaint, f, TruncateAt.END);
    }

    public static String nullIfEmpty(String string2) {
        block0 : {
            if (!TextUtils.isEmpty(string2)) break block0;
            string2 = null;
        }
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static char[] obtain(int n) {
        char[] arrc;
        char[] arrc2 = sLock;
        synchronized (arrc2) {
            arrc = sTemp;
            sTemp = null;
        }
        if (arrc == null) return ArrayUtils.newUnpaddedCharArray(n);
        arrc2 = arrc;
        if (arrc.length >= n) return arrc2;
        return ArrayUtils.newUnpaddedCharArray(n);
    }

    @UnsupportedAppUsage
    public static long packRangeInLong(int n, int n2) {
        return (long)n << 32 | (long)n2;
    }

    private static void readSpan(Parcel parcel, Spannable spannable, Object object) {
        spannable.setSpan(object, parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void recycle(char[] arrc) {
        if (arrc.length > 1000) {
            return;
        }
        Object object = sLock;
        synchronized (object) {
            sTemp = arrc;
            return;
        }
    }

    public static boolean regionMatches(CharSequence charSequence, int n, CharSequence charSequence2, int n2, int n3) {
        int n4 = n3 * 2;
        if (n4 >= n3) {
            boolean bl;
            char[] arrc = TextUtils.obtain(n4);
            TextUtils.getChars(charSequence, n, n + n3, arrc, 0);
            TextUtils.getChars(charSequence2, n2, n2 + n3, arrc, n3);
            boolean bl2 = true;
            n = 0;
            do {
                bl = bl2;
                if (n >= n3) break;
                if (arrc[n] != arrc[n + n3]) {
                    bl = false;
                    break;
                }
                ++n;
            } while (true);
            TextUtils.recycle(arrc);
            return bl;
        }
        throw new IndexOutOfBoundsException();
    }

    public static <T> T[] removeEmptySpans(T[] arrobject, Spanned spanned, Class<T> class_) {
        Object[] arrobject2 = null;
        int n = 0;
        for (int i = 0; i < arrobject.length; ++i) {
            int n2;
            Object[] arrobject3;
            T t = arrobject[i];
            if (spanned.getSpanStart(t) == spanned.getSpanEnd(t)) {
                arrobject3 = arrobject2;
                n2 = n;
                if (arrobject2 == null) {
                    arrobject3 = (Object[])Array.newInstance(class_, arrobject.length - 1);
                    System.arraycopy(arrobject, 0, arrobject3, 0, i);
                    n2 = i;
                }
            } else {
                arrobject3 = arrobject2;
                n2 = n;
                if (arrobject2 != null) {
                    arrobject2[n] = t;
                    n2 = n + 1;
                    arrobject3 = arrobject2;
                }
            }
            arrobject2 = arrobject3;
            n = n2;
        }
        if (arrobject2 != null) {
            arrobject = (Object[])Array.newInstance(class_, n);
            System.arraycopy(arrobject2, 0, arrobject, 0, n);
            return arrobject;
        }
        return arrobject;
    }

    public static CharSequence replace(CharSequence charSequence, String[] arrstring, CharSequence[] arrcharSequence) {
        int n;
        int n2;
        charSequence = new SpannableStringBuilder(charSequence);
        for (n = 0; n < arrstring.length; ++n) {
            n2 = TextUtils.indexOf(charSequence, arrstring[n]);
            if (n2 < 0) continue;
            ((SpannableStringBuilder)charSequence).setSpan(arrstring[n], n2, arrstring[n].length() + n2, 33);
        }
        for (n = 0; n < arrstring.length; ++n) {
            int n3 = ((SpannableStringBuilder)charSequence).getSpanStart(arrstring[n]);
            n2 = ((SpannableStringBuilder)charSequence).getSpanEnd(arrstring[n]);
            if (n3 < 0) continue;
            ((SpannableStringBuilder)charSequence).replace(n3, n2, arrcharSequence[n]);
        }
        return charSequence;
    }

    public static String safeIntern(String string2) {
        string2 = string2 != null ? string2.intern() : null;
        return string2;
    }

    public static String[] split(String string2, String string3) {
        if (string2.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return string2.split(string3, -1);
    }

    public static String[] split(String string2, Pattern pattern) {
        if (string2.length() == 0) {
            return EMPTY_STRING_ARRAY;
        }
        return pattern.split(string2, -1);
    }

    public static CharSequence stringOrSpannedString(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        if (charSequence instanceof SpannedString) {
            return charSequence;
        }
        if (charSequence instanceof Spanned) {
            return new SpannedString(charSequence);
        }
        return charSequence.toString();
    }

    public static String substring(CharSequence charSequence, int n, int n2) {
        if (charSequence instanceof String) {
            return ((String)charSequence).substring(n, n2);
        }
        if (charSequence instanceof StringBuilder) {
            return ((StringBuilder)charSequence).substring(n, n2);
        }
        if (charSequence instanceof StringBuffer) {
            return ((StringBuffer)charSequence).substring(n, n2);
        }
        char[] arrc = TextUtils.obtain(n2 - n);
        TextUtils.getChars(charSequence, n, n2, arrc, 0);
        charSequence = new String(arrc, 0, n2 - n);
        TextUtils.recycle(arrc);
        return charSequence;
    }

    public static CharSequence toUpperCase(Locale object, CharSequence object22, boolean bl) {
        Edits edits = new Edits();
        if (!bl) {
            object = (StringBuilder)CaseMap.toUpper().apply((Locale)object, (CharSequence)object22, (Appendable)new StringBuilder(), edits);
            if (!edits.hasChanges()) {
                object = object22;
            }
            return object;
        }
        object = (SpannableStringBuilder)CaseMap.toUpper().apply((Locale)object, (CharSequence)object22, (Appendable)new SpannableStringBuilder(), edits);
        if (!edits.hasChanges()) {
            return object22;
        }
        edits = edits.getFineIterator();
        int n = object22.length();
        Spanned spanned = (Spanned)object22;
        for (Object object22 : spanned.getSpans(0, n, Object.class)) {
            int n2 = spanned.getSpanStart(object22);
            int n3 = spanned.getSpanEnd(object22);
            int n4 = spanned.getSpanFlags(object22);
            n2 = n2 == n ? ((SpannableStringBuilder)object).length() : TextUtils.toUpperMapToDest((Edits.Iterator)edits, n2);
            n3 = n3 == n ? ((SpannableStringBuilder)object).length() : TextUtils.toUpperMapToDest((Edits.Iterator)edits, n3);
            ((SpannableStringBuilder)object).setSpan(object22, n2, n3, n4);
        }
        return object;
    }

    private static int toUpperMapToDest(Edits.Iterator iterator, int n) {
        iterator.findSourceIndex(n);
        if (n == iterator.sourceIndex()) {
            return iterator.destinationIndex();
        }
        if (iterator.hasChange()) {
            return iterator.destinationIndex() + iterator.newLength();
        }
        return iterator.destinationIndex() + (n - iterator.sourceIndex());
    }

    public static CharSequence trimNoCopySpans(CharSequence charSequence) {
        if (charSequence != null && charSequence instanceof Spanned) {
            return new SpannableStringBuilder(charSequence);
        }
        return charSequence;
    }

    public static <T extends CharSequence> T trimToLengthWithEllipsis(T object, int n) {
        T t;
        Object object2 = t = TextUtils.trimToSize(object, n);
        if (t.length() < object.length()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(t.toString());
            ((StringBuilder)object).append("...");
            object2 = ((StringBuilder)object).toString();
        }
        return object2;
    }

    public static <T extends CharSequence> T trimToParcelableSize(T t) {
        return TextUtils.trimToSize(t, 100000);
    }

    public static <T extends CharSequence> T trimToSize(T t, int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        if (!TextUtils.isEmpty(t) && t.length() > n) {
            int n2 = n;
            if (Character.isHighSurrogate(t.charAt(n - 1))) {
                n2 = n;
                if (Character.isLowSurrogate(t.charAt(n))) {
                    n2 = n - 1;
                }
            }
            return (T)t.subSequence(0, n2);
        }
        return t;
    }

    @UnsupportedAppUsage
    public static int unpackRangeEndFromLong(long l) {
        return (int)(0xFFFFFFFFL & l);
    }

    @UnsupportedAppUsage
    public static int unpackRangeStartFromLong(long l) {
        return (int)(l >>> 32);
    }

    public static String withoutPrefix(String string2, String string3) {
        if (string2 != null && string3 != null) {
            if (string3.startsWith(string2)) {
                string3 = string3.substring(string2.length());
            }
            return string3;
        }
        return string3;
    }

    public static void wrap(StringBuilder stringBuilder, String string2, String string3) {
        stringBuilder.insert(0, string2);
        stringBuilder.append(string3);
    }

    public static void writeToParcel(CharSequence object, Parcel parcel, int n) {
        if (object instanceof Spanned) {
            parcel.writeInt(0);
            parcel.writeString(object.toString());
            Spanned spanned = (Spanned)object;
            Object[] arrobject = spanned.getSpans(0, object.length(), Object.class);
            for (int i = 0; i < arrobject.length; ++i) {
                Object object2;
                Object object3 = arrobject[i];
                object = object2 = arrobject[i];
                if (object2 instanceof CharacterStyle) {
                    object = ((CharacterStyle)object2).getUnderlying();
                }
                if (!(object instanceof ParcelableSpan)) continue;
                int n2 = (object = (ParcelableSpan)object).getSpanTypeIdInternal();
                if (n2 >= 1 && n2 <= 28) {
                    parcel.writeInt(n2);
                    object.writeToParcelInternal(parcel, n);
                    TextUtils.writeWhere(parcel, spanned, object3);
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("External class \"");
                ((StringBuilder)object2).append(object.getClass().getSimpleName());
                ((StringBuilder)object2).append("\" is attempting to use the frameworks-only ParcelableSpan interface");
                Log.e(TAG, ((StringBuilder)object2).toString());
            }
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            if (object != null) {
                parcel.writeString(object.toString());
            } else {
                parcel.writeString(null);
            }
        }
    }

    private static void writeWhere(Parcel parcel, Spanned spanned, Object object) {
        parcel.writeInt(spanned.getSpanStart(object));
        parcel.writeInt(spanned.getSpanEnd(object));
        parcel.writeInt(spanned.getSpanFlags(object));
    }

    public static interface EllipsizeCallback {
        public void ellipsized(int var1, int var2);
    }

    private static class Reverser
    implements CharSequence,
    GetChars {
        private int mEnd;
        private CharSequence mSource;
        private int mStart;

        public Reverser(CharSequence charSequence, int n, int n2) {
            this.mSource = charSequence;
            this.mStart = n;
            this.mEnd = n2;
        }

        @Override
        public char charAt(int n) {
            return (char)UCharacter.getMirror((int)this.mSource.charAt(this.mEnd - 1 - n));
        }

        @Override
        public void getChars(int n, int n2, char[] arrc, int n3) {
            CharSequence charSequence = this.mSource;
            int n4 = this.mStart;
            TextUtils.getChars(charSequence, n + n4, n4 + n2, arrc, n3);
            AndroidCharacter.mirror(arrc, 0, n2 - n);
            n4 = n2 - n;
            n2 = (n2 - n) / 2;
            for (n = 0; n < n2; ++n) {
                char c = arrc[n3 + n];
                arrc[n3 + n] = arrc[n3 + n4 - n - 1];
                arrc[n3 + n4 - n - 1] = c;
            }
        }

        @Override
        public int length() {
            return this.mEnd - this.mStart;
        }

        @Override
        public CharSequence subSequence(int n, int n2) {
            char[] arrc = new char[n2 - n];
            this.getChars(n, n2, arrc, 0);
            return new String(arrc);
        }

        @Override
        public String toString() {
            return this.subSequence(0, this.length()).toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SafeStringFlags {
    }

    public static class SimpleStringSplitter
    implements StringSplitter,
    Iterator<String> {
        private char mDelimiter;
        private int mLength;
        private int mPosition;
        private String mString;

        public SimpleStringSplitter(char c) {
            this.mDelimiter = c;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.mPosition < this.mLength;
            return bl;
        }

        @Override
        public Iterator<String> iterator() {
            return this;
        }

        @Override
        public String next() {
            int n;
            int n2 = n = this.mString.indexOf(this.mDelimiter, this.mPosition);
            if (n == -1) {
                n2 = this.mLength;
            }
            String string2 = this.mString.substring(this.mPosition, n2);
            this.mPosition = n2 + 1;
            return string2;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setString(String string2) {
            this.mString = string2;
            this.mPosition = 0;
            this.mLength = this.mString.length();
        }
    }

    public static interface StringSplitter
    extends Iterable<String> {
        public void setString(String var1);
    }

    private static class StringWithRemovedChars {
        private final String mOriginal;
        private BitSet mRemovedChars;

        StringWithRemovedChars(String string2) {
            this.mOriginal = string2;
        }

        int codePointAt(int n) {
            return this.mOriginal.codePointAt(n);
        }

        int length() {
            return this.mOriginal.length();
        }

        void removeAllCharAfter(int n) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(n, this.mOriginal.length());
        }

        void removeAllCharBefore(int n) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(0, n);
        }

        void removeRange(int n, int n2) {
            if (this.mRemovedChars == null) {
                this.mRemovedChars = new BitSet(this.mOriginal.length());
            }
            this.mRemovedChars.set(n, n2);
        }

        public String toString() {
            if (this.mRemovedChars == null) {
                return this.mOriginal;
            }
            StringBuilder stringBuilder = new StringBuilder(this.mOriginal.length());
            for (int i = 0; i < this.mOriginal.length(); ++i) {
                if (this.mRemovedChars.get(i)) continue;
                stringBuilder.append(this.mOriginal.charAt(i));
            }
            return stringBuilder.toString();
        }
    }

    public static enum TruncateAt {
        START,
        MIDDLE,
        END,
        MARQUEE,
        END_SMALL;
        
    }

}

