/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.graphics.fonts.FontVariationAxis;
import android.net.Uri;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class FontConfig {
    private final Alias[] mAliases;
    private final Family[] mFamilies;

    public FontConfig(Family[] arrfamily, Alias[] arralias) {
        this.mFamilies = arrfamily;
        this.mAliases = arralias;
    }

    public Alias[] getAliases() {
        return this.mAliases;
    }

    @UnsupportedAppUsage
    public Family[] getFamilies() {
        return this.mFamilies;
    }

    public static final class Alias {
        private final String mName;
        private final String mToName;
        private final int mWeight;

        public Alias(String string2, String string3, int n) {
            this.mName = string2;
            this.mToName = string3;
            this.mWeight = n;
        }

        public String getName() {
            return this.mName;
        }

        public String getToName() {
            return this.mToName;
        }

        public int getWeight() {
            return this.mWeight;
        }
    }

    public static final class Family {
        public static final int VARIANT_COMPACT = 1;
        public static final int VARIANT_DEFAULT = 0;
        public static final int VARIANT_ELEGANT = 2;
        private final Font[] mFonts;
        private final String mLanguages;
        private final String mName;
        private final int mVariant;

        public Family(String string2, Font[] arrfont, String string3, int n) {
            this.mName = string2;
            this.mFonts = arrfont;
            this.mLanguages = string3;
            this.mVariant = n;
        }

        @UnsupportedAppUsage
        public Font[] getFonts() {
            return this.mFonts;
        }

        public String getLanguages() {
            return this.mLanguages;
        }

        @UnsupportedAppUsage
        public String getName() {
            return this.mName;
        }

        @UnsupportedAppUsage
        public int getVariant() {
            return this.mVariant;
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Variant {
        }

    }

    public static final class Font {
        private final FontVariationAxis[] mAxes;
        private final String mFallbackFor;
        private final String mFontName;
        private final boolean mIsItalic;
        private final int mTtcIndex;
        private Uri mUri;
        private final int mWeight;

        public Font(String string2, int n, FontVariationAxis[] arrfontVariationAxis, int n2, boolean bl, String string3) {
            this.mFontName = string2;
            this.mTtcIndex = n;
            this.mAxes = arrfontVariationAxis;
            this.mWeight = n2;
            this.mIsItalic = bl;
            this.mFallbackFor = string3;
        }

        @UnsupportedAppUsage
        public FontVariationAxis[] getAxes() {
            return this.mAxes;
        }

        public String getFallbackFor() {
            return this.mFallbackFor;
        }

        public String getFontName() {
            return this.mFontName;
        }

        @UnsupportedAppUsage
        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public Uri getUri() {
            return this.mUri;
        }

        @UnsupportedAppUsage
        public int getWeight() {
            return this.mWeight;
        }

        @UnsupportedAppUsage
        public boolean isItalic() {
            return this.mIsItalic;
        }

        public void setUri(Uri uri) {
            this.mUri = uri;
        }
    }

}

