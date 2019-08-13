/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.TtmlNode;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class TtmlUtils {
    public static final String ATTR_BEGIN = "begin";
    public static final String ATTR_DURATION = "dur";
    public static final String ATTR_END = "end";
    private static final Pattern CLOCK_TIME = Pattern.compile("^([0-9][0-9]+):([0-9][0-9]):([0-9][0-9])(?:(\\.[0-9]+)|:([0-9][0-9])(?:\\.([0-9]+))?)?$");
    public static final long INVALID_TIMESTAMP = Long.MAX_VALUE;
    private static final Pattern OFFSET_TIME = Pattern.compile("^([0-9]+(?:\\.[0-9]+)?)(h|m|s|ms|f|t)$");
    public static final String PCDATA = "#pcdata";
    public static final String TAG_BODY = "body";
    public static final String TAG_BR = "br";
    public static final String TAG_DIV = "div";
    public static final String TAG_HEAD = "head";
    public static final String TAG_LAYOUT = "layout";
    public static final String TAG_METADATA = "metadata";
    public static final String TAG_P = "p";
    public static final String TAG_REGION = "region";
    public static final String TAG_SMPTE_DATA = "smpte:data";
    public static final String TAG_SMPTE_IMAGE = "smpte:image";
    public static final String TAG_SMPTE_INFORMATION = "smpte:information";
    public static final String TAG_SPAN = "span";
    public static final String TAG_STYLE = "style";
    public static final String TAG_STYLING = "styling";
    public static final String TAG_TT = "tt";

    private TtmlUtils() {
    }

    public static String applyDefaultSpacePolicy(String string2) {
        return TtmlUtils.applySpacePolicy(string2, true);
    }

    public static String applySpacePolicy(String string2, boolean bl) {
        block0 : {
            string2 = string2.replaceAll("\r\n", "\n").replaceAll(" *\n *", "\n");
            if (!bl) break block0;
            string2 = string2.replaceAll("\n", " ");
        }
        return string2.replaceAll("[ \t\\x0B\f\r]+", " ");
    }

    public static String extractText(TtmlNode ttmlNode, long l, long l2) {
        StringBuilder stringBuilder = new StringBuilder();
        TtmlUtils.extractText(ttmlNode, l, l2, stringBuilder, false);
        return stringBuilder.toString().replaceAll("\n$", "");
    }

    private static void extractText(TtmlNode ttmlNode, long l, long l2, StringBuilder stringBuilder, boolean bl) {
        if (ttmlNode.mName.equals(PCDATA) && bl) {
            stringBuilder.append(ttmlNode.mText);
        } else if (ttmlNode.mName.equals(TAG_BR) && bl) {
            stringBuilder.append("\n");
        } else if (!ttmlNode.mName.equals(TAG_METADATA) && ttmlNode.isActive(l, l2)) {
            boolean bl2 = ttmlNode.mName.equals(TAG_P);
            int n = stringBuilder.length();
            for (int i = 0; i < ttmlNode.mChildren.size(); ++i) {
                TtmlNode ttmlNode2 = ttmlNode.mChildren.get(i);
                boolean bl3 = bl2 || bl;
                TtmlUtils.extractText(ttmlNode2, l, l2, stringBuilder, bl3);
            }
            if (bl2 && n != stringBuilder.length()) {
                stringBuilder.append("\n");
            }
        }
    }

    public static String extractTtmlFragment(TtmlNode ttmlNode, long l, long l2) {
        StringBuilder stringBuilder = new StringBuilder();
        TtmlUtils.extractTtmlFragment(ttmlNode, l, l2, stringBuilder);
        return stringBuilder.toString();
    }

    private static void extractTtmlFragment(TtmlNode ttmlNode, long l, long l2, StringBuilder stringBuilder) {
        if (ttmlNode.mName.equals(PCDATA)) {
            stringBuilder.append(ttmlNode.mText);
        } else if (ttmlNode.mName.equals(TAG_BR)) {
            stringBuilder.append("<br/>");
        } else if (ttmlNode.isActive(l, l2)) {
            stringBuilder.append("<");
            stringBuilder.append(ttmlNode.mName);
            stringBuilder.append(ttmlNode.mAttributes);
            stringBuilder.append(">");
            for (int i = 0; i < ttmlNode.mChildren.size(); ++i) {
                TtmlUtils.extractTtmlFragment(ttmlNode.mChildren.get(i), l, l2, stringBuilder);
            }
            stringBuilder.append("</");
            stringBuilder.append(ttmlNode.mName);
            stringBuilder.append(">");
        }
    }

    public static long parseTimeExpression(String string2, int n, int n2, int n3) throws NumberFormatException {
        Object object = CLOCK_TIME.matcher(string2);
        if (((Matcher)object).matches()) {
            double d = Long.parseLong(((Matcher)object).group(1)) * 3600L;
            double d2 = Long.parseLong(((Matcher)object).group(2)) * 60L;
            double d3 = Long.parseLong(((Matcher)object).group(3));
            string2 = ((Matcher)object).group(4);
            double d4 = string2 != null ? Double.parseDouble(string2) : 0.0;
            string2 = ((Matcher)object).group(5);
            double d5 = string2 != null ? (double)Long.parseLong(string2) / (double)n : 0.0;
            string2 = ((Matcher)object).group(6);
            double d6 = string2 != null ? (double)Long.parseLong(string2) / (double)n2 / (double)n : 0.0;
            return (long)(1000.0 * (d + d2 + d3 + d4 + d5 + d6));
        }
        object = OFFSET_TIME.matcher(string2);
        if (((Matcher)object).matches()) {
            double d = Double.parseDouble(((Matcher)object).group(1));
            string2 = ((Matcher)object).group(2);
            if (string2.equals("h")) {
                d *= 3.6E9;
            } else if (string2.equals("m")) {
                d *= 6.0E7;
            } else if (string2.equals("s")) {
                d *= 1000000.0;
            } else if (string2.equals("ms")) {
                d *= 1000.0;
            } else if (string2.equals("f")) {
                d = d / (double)n * 1000000.0;
            } else if (string2.equals("t")) {
                d = d / (double)n3 * 1000000.0;
            }
            return (long)d;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Malformed time expression : ");
        ((StringBuilder)object).append(string2);
        throw new NumberFormatException(((StringBuilder)object).toString());
    }
}

