/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.text.Editable;
import android.text.Emoji;
import android.text.SpannableStringBuilder;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import java.util.Locale;

public final class BidiFormatter {
    private static final int DEFAULT_FLAGS = 2;
    private static final BidiFormatter DEFAULT_LTR_INSTANCE;
    private static final BidiFormatter DEFAULT_RTL_INSTANCE;
    private static TextDirectionHeuristic DEFAULT_TEXT_DIRECTION_HEURISTIC;
    private static final int DIR_LTR = -1;
    private static final int DIR_RTL = 1;
    private static final int DIR_UNKNOWN = 0;
    private static final String EMPTY_STRING = "";
    private static final int FLAG_STEREO_RESET = 2;
    private static final char LRE = '\u202a';
    private static final char LRM = '\u200e';
    private static final String LRM_STRING;
    private static final char PDF = '\u202c';
    private static final char RLE = '\u202b';
    private static final char RLM = '\u200f';
    private static final String RLM_STRING;
    private final TextDirectionHeuristic mDefaultTextDirectionHeuristic;
    private final int mFlags;
    private final boolean mIsRtlContext;

    static {
        DEFAULT_TEXT_DIRECTION_HEURISTIC = TextDirectionHeuristics.FIRSTSTRONG_LTR;
        LRM_STRING = Character.toString('\u200e');
        RLM_STRING = Character.toString('\u200f');
        DEFAULT_LTR_INSTANCE = new BidiFormatter(false, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
        DEFAULT_RTL_INSTANCE = new BidiFormatter(true, 2, DEFAULT_TEXT_DIRECTION_HEURISTIC);
    }

    private BidiFormatter(boolean bl, int n, TextDirectionHeuristic textDirectionHeuristic) {
        this.mIsRtlContext = bl;
        this.mFlags = n;
        this.mDefaultTextDirectionHeuristic = textDirectionHeuristic;
    }

    private static BidiFormatter getDefaultInstanceFromContext(boolean bl) {
        BidiFormatter bidiFormatter = bl ? DEFAULT_RTL_INSTANCE : DEFAULT_LTR_INSTANCE;
        return bidiFormatter;
    }

    private static int getEntryDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getEntryDir();
    }

    private static int getExitDir(CharSequence charSequence) {
        return new DirectionalityEstimator(charSequence, false).getExitDir();
    }

    public static BidiFormatter getInstance() {
        return BidiFormatter.getDefaultInstanceFromContext(BidiFormatter.isRtlLocale(Locale.getDefault()));
    }

    public static BidiFormatter getInstance(Locale locale) {
        return BidiFormatter.getDefaultInstanceFromContext(BidiFormatter.isRtlLocale(locale));
    }

    public static BidiFormatter getInstance(boolean bl) {
        return BidiFormatter.getDefaultInstanceFromContext(bl);
    }

    private static boolean isRtlLocale(Locale locale) {
        int n = TextUtils.getLayoutDirectionFromLocale(locale);
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    public boolean getStereoReset() {
        boolean bl = (this.mFlags & 2) != 0;
        return bl;
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.mDefaultTextDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
    }

    public boolean isRtl(String string2) {
        return this.isRtl((CharSequence)string2);
    }

    public boolean isRtlContext() {
        return this.mIsRtlContext;
    }

    public String markAfter(CharSequence charSequence, TextDirectionHeuristic textDirectionHeuristic) {
        boolean bl = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getExitDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getExitDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return EMPTY_STRING;
    }

    public String markBefore(CharSequence charSequence, TextDirectionHeuristic textDirectionHeuristic) {
        boolean bl = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        if (!this.mIsRtlContext && (bl || BidiFormatter.getEntryDir(charSequence) == 1)) {
            return LRM_STRING;
        }
        if (this.mIsRtlContext && (!bl || BidiFormatter.getEntryDir(charSequence) == -1)) {
            return RLM_STRING;
        }
        return EMPTY_STRING;
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristic, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristic textDirectionHeuristic) {
        return this.unicodeWrap(charSequence, textDirectionHeuristic, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristic textDirectionHeuristic, boolean bl) {
        if (charSequence == null) {
            return null;
        }
        boolean bl2 = textDirectionHeuristic.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        if (this.getStereoReset() && bl) {
            textDirectionHeuristic = bl2 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            spannableStringBuilder.append(this.markBefore(charSequence, textDirectionHeuristic));
        }
        if (bl2 != this.mIsRtlContext) {
            char c;
            char c2;
            char c3 = bl2 ? (c2 = '\u202b') : (c = '\u202a');
            spannableStringBuilder.append(c3);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append('\u202c');
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (bl) {
            textDirectionHeuristic = bl2 ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR;
            spannableStringBuilder.append(this.markAfter(charSequence, textDirectionHeuristic));
        }
        return spannableStringBuilder;
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean bl) {
        return this.unicodeWrap(charSequence, this.mDefaultTextDirectionHeuristic, bl);
    }

    public String unicodeWrap(String string2) {
        return this.unicodeWrap(string2, this.mDefaultTextDirectionHeuristic, true);
    }

    public String unicodeWrap(String string2, TextDirectionHeuristic textDirectionHeuristic) {
        return this.unicodeWrap(string2, textDirectionHeuristic, true);
    }

    public String unicodeWrap(String string2, TextDirectionHeuristic textDirectionHeuristic, boolean bl) {
        if (string2 == null) {
            return null;
        }
        return this.unicodeWrap((CharSequence)string2, textDirectionHeuristic, bl).toString();
    }

    public String unicodeWrap(String string2, boolean bl) {
        return this.unicodeWrap(string2, this.mDefaultTextDirectionHeuristic, bl);
    }

    public static final class Builder {
        private int mFlags;
        private boolean mIsRtlContext;
        private TextDirectionHeuristic mTextDirectionHeuristic;

        public Builder() {
            this.initialize(BidiFormatter.isRtlLocale(Locale.getDefault()));
        }

        public Builder(Locale locale) {
            this.initialize(BidiFormatter.isRtlLocale(locale));
        }

        public Builder(boolean bl) {
            this.initialize(bl);
        }

        private void initialize(boolean bl) {
            this.mIsRtlContext = bl;
            this.mTextDirectionHeuristic = DEFAULT_TEXT_DIRECTION_HEURISTIC;
            this.mFlags = 2;
        }

        public BidiFormatter build() {
            if (this.mFlags == 2 && this.mTextDirectionHeuristic == DEFAULT_TEXT_DIRECTION_HEURISTIC) {
                return BidiFormatter.getDefaultInstanceFromContext(this.mIsRtlContext);
            }
            return new BidiFormatter(this.mIsRtlContext, this.mFlags, this.mTextDirectionHeuristic);
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristic textDirectionHeuristic) {
            this.mTextDirectionHeuristic = textDirectionHeuristic;
            return this;
        }

        public Builder stereoReset(boolean bl) {
            this.mFlags = bl ? (this.mFlags |= 2) : (this.mFlags &= -3);
            return this;
        }
    }

    @VisibleForTesting
    public static class DirectionalityEstimator {
        private static final byte[] DIR_TYPE_CACHE = new byte[1792];
        private static final int DIR_TYPE_CACHE_SIZE = 1792;
        private int charIndex;
        private final boolean isHtml;
        private char lastChar;
        private final int length;
        private final CharSequence text;

        static {
            for (int i = 0; i < 1792; ++i) {
                DirectionalityEstimator.DIR_TYPE_CACHE[i] = Character.getDirectionality(i);
            }
        }

        DirectionalityEstimator(CharSequence charSequence, boolean bl) {
            this.text = charSequence;
            this.isHtml = bl;
            this.length = charSequence.length();
        }

        private static byte getCachedDirectionality(char c) {
            byte by;
            if (c < '\u0700') {
                c = DIR_TYPE_CACHE[c];
                by = c;
            } else {
                c = (char)DirectionalityEstimator.getDirectionality(c);
                by = c;
            }
            return by;
        }

        public static byte getDirectionality(int n) {
            if (Emoji.isNewEmoji(n)) {
                return 13;
            }
            return Character.getDirectionality(n);
        }

        private byte skipEntityBackward() {
            int n;
            int n2 = this.charIndex;
            while ((n = this.charIndex) > 0) {
                CharSequence charSequence = this.text;
                this.charIndex = --n;
                this.lastChar = charSequence.charAt(n);
                n = this.lastChar;
                if (n == 38) {
                    return 12;
                }
                if (n != 59) continue;
            }
            this.charIndex = n2;
            this.lastChar = (char)59;
            return 13;
        }

        private byte skipEntityForward() {
            int n;
            while ((n = this.charIndex) < this.length) {
                CharSequence charSequence = this.text;
                this.charIndex = n + 1;
                n = charSequence.charAt(n);
                this.lastChar = (char)n;
                if (n != 59) continue;
            }
            return 12;
        }

        private byte skipTagBackward() {
            int n;
            int n2 = this.charIndex;
            block0 : while ((n = this.charIndex) > 0) {
                int n3;
                CharSequence charSequence = this.text;
                this.charIndex = --n;
                this.lastChar = charSequence.charAt(n);
                n = this.lastChar;
                if (n == 60) {
                    return 12;
                }
                if (n == 62) break;
                if (n != 34 && n != 39) continue;
                n = this.lastChar;
                while ((n3 = this.charIndex) > 0) {
                    charSequence = this.text;
                    this.charIndex = --n3;
                    n3 = charSequence.charAt(n3);
                    this.lastChar = (char)n3;
                    if (n3 == n) continue block0;
                }
            }
            this.charIndex = n2;
            this.lastChar = (char)62;
            return 13;
        }

        private byte skipTagForward() {
            int n;
            int n2 = this.charIndex;
            block0 : while ((n = this.charIndex) < this.length) {
                int n3;
                CharSequence charSequence = this.text;
                this.charIndex = n + 1;
                this.lastChar = charSequence.charAt(n);
                n = this.lastChar;
                if (n == 62) {
                    return 12;
                }
                if (n != 34 && n != 39) continue;
                n = this.lastChar;
                while ((n3 = this.charIndex) < this.length) {
                    charSequence = this.text;
                    this.charIndex = n3 + 1;
                    n3 = charSequence.charAt(n3);
                    this.lastChar = (char)n3;
                    if (n3 == n) continue block0;
                }
            }
            this.charIndex = n2;
            this.lastChar = (char)60;
            return 13;
        }

        byte dirTypeBackward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex - 1);
            if (Character.isLowSurrogate(this.lastChar)) {
                int n = Character.codePointBefore(this.text, this.charIndex);
                this.charIndex -= Character.charCount(n);
                return DirectionalityEstimator.getDirectionality(n);
            }
            --this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                char c = this.lastChar;
                if (c == '>') {
                    by2 = by = this.skipTagBackward();
                } else {
                    by2 = by;
                    if (c == ';') {
                        by2 = by = this.skipEntityBackward();
                    }
                }
            }
            return by2;
        }

        byte dirTypeForward() {
            byte by;
            this.lastChar = this.text.charAt(this.charIndex);
            if (Character.isHighSurrogate(this.lastChar)) {
                int n = Character.codePointAt(this.text, this.charIndex);
                this.charIndex += Character.charCount(n);
                return DirectionalityEstimator.getDirectionality(n);
            }
            ++this.charIndex;
            byte by2 = by = DirectionalityEstimator.getCachedDirectionality(this.lastChar);
            if (this.isHtml) {
                char c = this.lastChar;
                if (c == '<') {
                    by2 = by = this.skipTagForward();
                } else {
                    by2 = by;
                    if (c == '&') {
                        by2 = by = this.skipEntityForward();
                    }
                }
            }
            return by2;
        }

        int getEntryDir() {
            this.charIndex = 0;
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            block10 : while (this.charIndex < this.length && n3 == 0) {
                byte by = this.dirTypeForward();
                if (by != 0) {
                    if (by != 1 && by != 2) {
                        if (by == 9) continue;
                        switch (by) {
                            default: {
                                n3 = n;
                                continue block10;
                            }
                            case 18: {
                                --n;
                                n2 = 0;
                                continue block10;
                            }
                            case 16: 
                            case 17: {
                                ++n;
                                n2 = 1;
                                continue block10;
                            }
                            case 14: 
                            case 15: 
                        }
                        ++n;
                        n2 = -1;
                        continue;
                    }
                    if (n == 0) {
                        return 1;
                    }
                    n3 = n;
                    continue;
                }
                if (n == 0) {
                    return -1;
                }
                n3 = n;
            }
            if (n3 == 0) {
                return 0;
            }
            if (n2 != 0) {
                return n2;
            }
            block11 : while (this.charIndex > 0) {
                switch (this.dirTypeBackward()) {
                    default: {
                        continue block11;
                    }
                    case 18: {
                        ++n;
                        continue block11;
                    }
                    case 16: 
                    case 17: {
                        if (n3 == n) {
                            return 1;
                        }
                        --n;
                        continue block11;
                    }
                    case 14: 
                    case 15: 
                }
                if (n3 == n) {
                    return -1;
                }
                --n;
            }
            return 0;
        }

        int getExitDir() {
            this.charIndex = this.length;
            int n = 0;
            int n2 = 0;
            block5 : while (this.charIndex > 0) {
                byte by = this.dirTypeBackward();
                if (by != 0) {
                    if (by != 1 && by != 2) {
                        if (by == 9) continue;
                        switch (by) {
                            default: {
                                if (n2 != 0) continue block5;
                                n2 = n;
                                continue block5;
                            }
                            case 18: {
                                ++n;
                                continue block5;
                            }
                            case 16: 
                            case 17: {
                                if (n2 == n) {
                                    return 1;
                                }
                                --n;
                                continue block5;
                            }
                            case 14: 
                            case 15: 
                        }
                        if (n2 == n) {
                            return -1;
                        }
                        --n;
                        continue;
                    }
                    if (n == 0) {
                        return 1;
                    }
                    if (n2 != 0) continue;
                    n2 = n;
                    continue;
                }
                if (n == 0) {
                    return -1;
                }
                if (n2 != 0) continue;
                n2 = n;
            }
            return 0;
        }
    }

}

