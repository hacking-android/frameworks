/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.fonts;

import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class FontStyle {
    public static final int FONT_SLANT_ITALIC = 1;
    public static final int FONT_SLANT_UPRIGHT = 0;
    public static final int FONT_WEIGHT_BLACK = 900;
    public static final int FONT_WEIGHT_BOLD = 700;
    public static final int FONT_WEIGHT_EXTRA_BOLD = 800;
    public static final int FONT_WEIGHT_EXTRA_LIGHT = 200;
    public static final int FONT_WEIGHT_LIGHT = 300;
    public static final int FONT_WEIGHT_MAX = 1000;
    public static final int FONT_WEIGHT_MEDIUM = 500;
    public static final int FONT_WEIGHT_MIN = 1;
    public static final int FONT_WEIGHT_NORMAL = 400;
    public static final int FONT_WEIGHT_SEMI_BOLD = 600;
    public static final int FONT_WEIGHT_THIN = 100;
    private static final String TAG = "FontStyle";
    private final int mSlant;
    private final int mWeight;

    public FontStyle() {
        this.mWeight = 400;
        this.mSlant = 0;
    }

    public FontStyle(int n, int n2) {
        boolean bl;
        block3 : {
            block2 : {
                boolean bl2 = false;
                bl = 1 <= n && n <= 1000;
                Preconditions.checkArgument(bl, "weight value must be [1, 1000]");
                if (n2 == 0) break block2;
                bl = bl2;
                if (n2 != 1) break block3;
            }
            bl = true;
        }
        Preconditions.checkArgument(bl, "slant value must be FONT_SLANT_UPRIGHT or FONT_SLANT_UPRIGHT");
        this.mWeight = n;
        this.mSlant = n2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object != null && object instanceof FontStyle) {
            object = (FontStyle)object;
            if (((FontStyle)object).mWeight != this.mWeight || ((FontStyle)object).mSlant != this.mSlant) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getMatchScore(FontStyle fontStyle) {
        int n = Math.abs(this.getWeight() - fontStyle.getWeight()) / 100;
        int n2 = this.getSlant() == fontStyle.getSlant() ? 0 : 2;
        return n + n2;
    }

    public int getSlant() {
        return this.mSlant;
    }

    public int getWeight() {
        return this.mWeight;
    }

    public int hashCode() {
        return Objects.hash(this.mWeight, this.mSlant);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FontStyle { weight=");
        stringBuilder.append(this.mWeight);
        stringBuilder.append(", slant=");
        stringBuilder.append(this.mSlant);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FontSlant {
    }

}

