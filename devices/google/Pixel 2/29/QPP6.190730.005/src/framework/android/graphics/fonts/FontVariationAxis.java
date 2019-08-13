/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.fonts;

import android.annotation.UnsupportedAppUsage;
import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FontVariationAxis {
    private static final Pattern STYLE_VALUE_PATTERN;
    private static final Pattern TAG_PATTERN;
    @UnsupportedAppUsage
    private final float mStyleValue;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mTag;
    private final String mTagString;

    static {
        TAG_PATTERN = Pattern.compile("[ -~]{4}");
        STYLE_VALUE_PATTERN = Pattern.compile("-?(([0-9]+(\\.[0-9]+)?)|(\\.[0-9]+))");
    }

    public FontVariationAxis(String string2, float f) {
        if (FontVariationAxis.isValidTag(string2)) {
            this.mTag = FontVariationAxis.makeTag(string2);
            this.mTagString = string2;
            this.mStyleValue = f;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal tag pattern: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static FontVariationAxis[] fromFontVariationSettings(String string2) {
        if (string2 != null && !string2.isEmpty()) {
            Serializable serializable = new ArrayList();
            int n = string2.length();
            for (int i = 0; i < n; ++i) {
                char c = string2.charAt(i);
                if (Character.isWhitespace(c)) continue;
                if ((c == '\'' || c == '\"') && n >= i + 6 && string2.charAt(i + 5) == c) {
                    int n2;
                    float f;
                    String string3 = string2.substring(i + 1, i + 5);
                    int n3 = i + 6;
                    i = n2 = string2.indexOf(44, n3);
                    if (n2 == -1) {
                        i = n;
                    }
                    try {
                        f = Float.parseFloat(string2.substring(n3, i));
                    }
                    catch (NumberFormatException numberFormatException) {
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("Failed to parse float string: ");
                        ((StringBuilder)serializable).append(numberFormatException.getMessage());
                        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
                    }
                    ((ArrayList)serializable).add(new FontVariationAxis(string3, f));
                    continue;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Tag should be wrapped with double or single quote: ");
                ((StringBuilder)serializable).append(string2);
                throw new IllegalArgumentException(((StringBuilder)serializable).toString());
            }
            if (((ArrayList)serializable).isEmpty()) {
                return null;
            }
            return ((ArrayList)serializable).toArray(new FontVariationAxis[0]);
        }
        return null;
    }

    private static boolean isValidTag(String string2) {
        boolean bl = string2 != null && TAG_PATTERN.matcher(string2).matches();
        return bl;
    }

    private static boolean isValidValueFormat(String string2) {
        boolean bl = string2 != null && STYLE_VALUE_PATTERN.matcher(string2).matches();
        return bl;
    }

    public static int makeTag(String string2) {
        return string2.charAt(0) << 24 | string2.charAt(1) << 16 | string2.charAt(2) << 8 | string2.charAt(3);
    }

    public static String toFontVariationSettings(FontVariationAxis[] arrfontVariationAxis) {
        if (arrfontVariationAxis == null) {
            return "";
        }
        return TextUtils.join((CharSequence)",", arrfontVariationAxis);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object != null && object instanceof FontVariationAxis) {
            object = (FontVariationAxis)object;
            if (((FontVariationAxis)object).mTag != this.mTag || ((FontVariationAxis)object).mStyleValue != this.mStyleValue) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int getOpenTypeTagValue() {
        return this.mTag;
    }

    public float getStyleValue() {
        return this.mStyleValue;
    }

    public String getTag() {
        return this.mTagString;
    }

    public int hashCode() {
        return Objects.hash(this.mTag, Float.valueOf(this.mStyleValue));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append(this.mTagString);
        stringBuilder.append("' ");
        stringBuilder.append(Float.toString(this.mStyleValue));
        return stringBuilder.toString();
    }
}

