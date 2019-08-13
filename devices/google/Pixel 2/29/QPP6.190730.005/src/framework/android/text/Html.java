/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.ccil.cowan.tagsoup.HTMLSchema
 *  org.ccil.cowan.tagsoup.Parser
 */
package android.text;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.HtmlToSpannedConverter;
import android.text.Layout;
import android.text.Spanned;
import android.text.TextDirectionHeuristic;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TypefaceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.DisplayMetrics;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

public class Html {
    public static final int FROM_HTML_MODE_COMPACT = 63;
    public static final int FROM_HTML_MODE_LEGACY = 0;
    public static final int FROM_HTML_OPTION_USE_CSS_COLORS = 256;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_BLOCKQUOTE = 32;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_DIV = 16;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_HEADING = 2;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST = 8;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM = 4;
    public static final int FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH = 1;
    private static final int TO_HTML_PARAGRAPH_FLAG = 1;
    public static final int TO_HTML_PARAGRAPH_LINES_CONSECUTIVE = 0;
    public static final int TO_HTML_PARAGRAPH_LINES_INDIVIDUAL = 1;

    private Html() {
    }

    private static void encodeTextAlignmentByDiv(StringBuilder stringBuilder, Spanned spanned, int n) {
        int n2 = spanned.length();
        int n3 = 0;
        while (n3 < n2) {
            int n4 = spanned.nextSpanTransition(n3, n2, ParagraphStyle.class);
            ParagraphStyle[] arrparagraphStyle = spanned.getSpans(n3, n4, ParagraphStyle.class);
            String string2 = " ";
            boolean bl = false;
            for (int i = 0; i < arrparagraphStyle.length; ++i) {
                Object object = string2;
                if (arrparagraphStyle[i] instanceof AlignmentSpan) {
                    object = ((AlignmentSpan)arrparagraphStyle[i]).getAlignment();
                    bl = true;
                    if (object == Layout.Alignment.ALIGN_CENTER) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("align=\"center\" ");
                        ((StringBuilder)object).append(string2);
                        object = ((StringBuilder)object).toString();
                    } else if (object == Layout.Alignment.ALIGN_OPPOSITE) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("align=\"right\" ");
                        ((StringBuilder)object).append(string2);
                        object = ((StringBuilder)object).toString();
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("align=\"left\" ");
                        ((StringBuilder)object).append(string2);
                        object = ((StringBuilder)object).toString();
                    }
                }
                string2 = object;
            }
            if (bl) {
                stringBuilder.append("<div ");
                stringBuilder.append(string2);
                stringBuilder.append(">");
            }
            Html.withinDiv(stringBuilder, spanned, n3, n4, n);
            if (bl) {
                stringBuilder.append("</div>");
            }
            n3 = n4;
        }
    }

    public static String escapeHtml(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder();
        Html.withinStyle(stringBuilder, charSequence, 0, charSequence.length());
        return stringBuilder.toString();
    }

    @Deprecated
    public static Spanned fromHtml(String string2) {
        return Html.fromHtml(string2, 0, null, null);
    }

    public static Spanned fromHtml(String string2, int n) {
        return Html.fromHtml(string2, n, null, null);
    }

    public static Spanned fromHtml(String string2, int n, ImageGetter imageGetter, TagHandler tagHandler) {
        Parser parser = new Parser();
        try {
            parser.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/schema", (Object)HtmlParser.schema);
            return new HtmlToSpannedConverter(string2, imageGetter, tagHandler, parser, n).convert();
        }
        catch (SAXNotSupportedException sAXNotSupportedException) {
            throw new RuntimeException(sAXNotSupportedException);
        }
        catch (SAXNotRecognizedException sAXNotRecognizedException) {
            throw new RuntimeException(sAXNotRecognizedException);
        }
    }

    @Deprecated
    public static Spanned fromHtml(String string2, ImageGetter imageGetter, TagHandler tagHandler) {
        return Html.fromHtml(string2, 0, imageGetter, tagHandler);
    }

    private static String getTextDirection(Spanned spanned, int n, int n2) {
        if (TextDirectionHeuristics.FIRSTSTRONG_LTR.isRtl(spanned, n, n2 - n)) {
            return " dir=\"rtl\"";
        }
        return " dir=\"ltr\"";
    }

    private static String getTextStyles(Spanned object, int n, int n2, boolean bl, boolean bl2) {
        String string2 = null;
        AlignmentSpan alignmentSpan = null;
        if (bl) {
            string2 = "margin-top:0; margin-bottom:0;";
        }
        Object object2 = alignmentSpan;
        if (bl2) {
            AlignmentSpan[] arralignmentSpan = object.getSpans(n, n2, AlignmentSpan.class);
            n = arralignmentSpan.length - 1;
            do {
                object2 = alignmentSpan;
                if (n < 0) break;
                object2 = arralignmentSpan[n];
                if ((object.getSpanFlags(object2) & 51) == 51) {
                    object = object2.getAlignment();
                    if (object == Layout.Alignment.ALIGN_NORMAL) {
                        object2 = "text-align:start;";
                        break;
                    }
                    if (object == Layout.Alignment.ALIGN_CENTER) {
                        object2 = "text-align:center;";
                        break;
                    }
                    object2 = alignmentSpan;
                    if (object != Layout.Alignment.ALIGN_OPPOSITE) break;
                    object2 = "text-align:end;";
                    break;
                }
                --n;
            } while (true);
        }
        if (string2 == null && object2 == null) {
            return "";
        }
        object = new StringBuilder(" style=\"");
        if (string2 != null && object2 != null) {
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" ");
            ((StringBuilder)object).append((String)object2);
        } else if (string2 != null) {
            ((StringBuilder)object).append(string2);
        } else if (object2 != null) {
            ((StringBuilder)object).append((String)object2);
        }
        ((StringBuilder)object).append("\"");
        return ((StringBuilder)object).toString();
    }

    @Deprecated
    public static String toHtml(Spanned spanned) {
        return Html.toHtml(spanned, 0);
    }

    public static String toHtml(Spanned spanned, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        Html.withinHtml(stringBuilder, spanned, n);
        return stringBuilder.toString();
    }

    private static void withinBlockquote(StringBuilder stringBuilder, Spanned spanned, int n, int n2, int n3) {
        if ((n3 & 1) == 0) {
            Html.withinBlockquoteConsecutive(stringBuilder, spanned, n, n2);
        } else {
            Html.withinBlockquoteIndividual(stringBuilder, spanned, n, n2);
        }
    }

    private static void withinBlockquoteConsecutive(StringBuilder stringBuilder, Spanned spanned, int n, int n2) {
        stringBuilder.append("<p");
        stringBuilder.append(Html.getTextDirection(spanned, n, n2));
        stringBuilder.append(">");
        int n3 = n;
        while (n3 < n2) {
            int n4;
            int n5 = n4 = TextUtils.indexOf((CharSequence)spanned, '\n', n3, n2);
            if (n4 < 0) {
                n5 = n2;
            }
            n4 = 0;
            while (n5 < n2 && spanned.charAt(n5) == '\n') {
                ++n4;
                ++n5;
            }
            Html.withinParagraph(stringBuilder, spanned, n3, n5 - n4);
            if (n4 == 1) {
                stringBuilder.append("<br>\n");
            } else {
                for (n3 = 2; n3 < n4; ++n3) {
                    stringBuilder.append("<br>");
                }
                if (n5 != n2) {
                    stringBuilder.append("</p>\n");
                    stringBuilder.append("<p");
                    stringBuilder.append(Html.getTextDirection(spanned, n, n2));
                    stringBuilder.append(">");
                }
            }
            n3 = n5;
        }
        stringBuilder.append("</p>\n");
    }

    private static void withinBlockquoteIndividual(StringBuilder stringBuilder, Spanned spanned, int n, int n2) {
        int n3 = 0;
        int n4 = n;
        n = n3;
        while (n4 <= n2) {
            int n5 = n3 = TextUtils.indexOf((CharSequence)spanned, '\n', n4, n2);
            if (n3 < 0) {
                n5 = n2;
            }
            if (n5 == n4) {
                n3 = n;
                if (n != 0) {
                    n3 = 0;
                    stringBuilder.append("</ul>\n");
                }
                stringBuilder.append("<br>\n");
                n = n3;
            } else {
                Object object;
                int n6;
                int n7 = 0;
                ParagraphStyle[] arrparagraphStyle = spanned.getSpans(n4, n5, ParagraphStyle.class);
                int n8 = arrparagraphStyle.length;
                boolean bl = false;
                n3 = 0;
                do {
                    n6 = n7;
                    if (n3 >= n8) break;
                    object = arrparagraphStyle[n3];
                    if ((spanned.getSpanFlags(object) & 51) == 51 && object instanceof BulletSpan) {
                        n6 = 1;
                        break;
                    }
                    ++n3;
                } while (true);
                n3 = n;
                if (n6 != 0) {
                    n3 = n;
                    if (n == 0) {
                        n3 = 1;
                        stringBuilder.append("<ul");
                        stringBuilder.append(Html.getTextStyles(spanned, n4, n5, true, false));
                        stringBuilder.append(">\n");
                    }
                }
                n7 = n3;
                if (n3 != 0) {
                    n7 = n3;
                    if (n6 == 0) {
                        n7 = 0;
                        stringBuilder.append("</ul>\n");
                    }
                }
                object = n6 != 0 ? "li" : "p";
                stringBuilder.append("<");
                stringBuilder.append((String)object);
                stringBuilder.append(Html.getTextDirection(spanned, n4, n5));
                if (n6 == 0) {
                    bl = true;
                }
                stringBuilder.append(Html.getTextStyles(spanned, n4, n5, bl, true));
                stringBuilder.append(">");
                Html.withinParagraph(stringBuilder, spanned, n4, n5);
                stringBuilder.append("</");
                stringBuilder.append((String)object);
                stringBuilder.append(">\n");
                n = n7;
                if (n5 == n2) {
                    n = n7;
                    if (n7 != 0) {
                        n = 0;
                        stringBuilder.append("</ul>\n");
                    }
                }
            }
            n4 = n5 + 1;
        }
    }

    private static void withinDiv(StringBuilder stringBuilder, Spanned spanned, int n, int n2, int n3) {
        while (n < n2) {
            QuoteSpan quoteSpan;
            int n4;
            int n5 = spanned.nextSpanTransition(n, n2, QuoteSpan.class);
            QuoteSpan[] arrquoteSpan = spanned.getSpans(n, n5, QuoteSpan.class);
            int n6 = arrquoteSpan.length;
            int n7 = 0;
            for (n4 = 0; n4 < n6; ++n4) {
                quoteSpan = arrquoteSpan[n4];
                stringBuilder.append("<blockquote>");
            }
            Html.withinBlockquote(stringBuilder, spanned, n, n5, n3);
            n4 = arrquoteSpan.length;
            for (n = n7; n < n4; ++n) {
                quoteSpan = arrquoteSpan[n];
                stringBuilder.append("</blockquote>\n");
            }
            n = n5;
        }
    }

    private static void withinHtml(StringBuilder stringBuilder, Spanned spanned, int n) {
        if ((n & 1) == 0) {
            Html.encodeTextAlignmentByDiv(stringBuilder, spanned, n);
            return;
        }
        Html.withinDiv(stringBuilder, spanned, 0, spanned.length(), n);
    }

    private static void withinParagraph(StringBuilder stringBuilder, Spanned spanned, int n, int n2) {
        while (n < n2) {
            int n3;
            int n4 = spanned.nextSpanTransition(n, n2, CharacterStyle.class);
            CharacterStyle[] arrcharacterStyle = spanned.getSpans(n, n4, CharacterStyle.class);
            for (n3 = 0; n3 < arrcharacterStyle.length; ++n3) {
                if (arrcharacterStyle[n3] instanceof StyleSpan) {
                    int n5 = ((StyleSpan)arrcharacterStyle[n3]).getStyle();
                    if ((n5 & 1) != 0) {
                        stringBuilder.append("<b>");
                    }
                    if ((n5 & 2) != 0) {
                        stringBuilder.append("<i>");
                    }
                }
                if (arrcharacterStyle[n3] instanceof TypefaceSpan && "monospace".equals(((TypefaceSpan)arrcharacterStyle[n3]).getFamily())) {
                    stringBuilder.append("<tt>");
                }
                if (arrcharacterStyle[n3] instanceof SuperscriptSpan) {
                    stringBuilder.append("<sup>");
                }
                if (arrcharacterStyle[n3] instanceof SubscriptSpan) {
                    stringBuilder.append("<sub>");
                }
                if (arrcharacterStyle[n3] instanceof UnderlineSpan) {
                    stringBuilder.append("<u>");
                }
                if (arrcharacterStyle[n3] instanceof StrikethroughSpan) {
                    stringBuilder.append("<span style=\"text-decoration:line-through;\">");
                }
                if (arrcharacterStyle[n3] instanceof URLSpan) {
                    stringBuilder.append("<a href=\"");
                    stringBuilder.append(((URLSpan)arrcharacterStyle[n3]).getURL());
                    stringBuilder.append("\">");
                }
                if (arrcharacterStyle[n3] instanceof ImageSpan) {
                    stringBuilder.append("<img src=\"");
                    stringBuilder.append(((ImageSpan)arrcharacterStyle[n3]).getSource());
                    stringBuilder.append("\">");
                    n = n4;
                }
                if (arrcharacterStyle[n3] instanceof AbsoluteSizeSpan) {
                    float f;
                    AbsoluteSizeSpan absoluteSizeSpan = (AbsoluteSizeSpan)arrcharacterStyle[n3];
                    float f2 = f = (float)absoluteSizeSpan.getSize();
                    if (!absoluteSizeSpan.getDip()) {
                        f2 = f / ActivityThread.currentApplication().getResources().getDisplayMetrics().density;
                    }
                    stringBuilder.append(String.format("<span style=\"font-size:%.0fpx\";>", Float.valueOf(f2)));
                }
                if (arrcharacterStyle[n3] instanceof RelativeSizeSpan) {
                    stringBuilder.append(String.format("<span style=\"font-size:%.2fem;\">", Float.valueOf(((RelativeSizeSpan)arrcharacterStyle[n3]).getSizeChange())));
                }
                if (arrcharacterStyle[n3] instanceof ForegroundColorSpan) {
                    stringBuilder.append(String.format("<span style=\"color:#%06X;\">", ((ForegroundColorSpan)arrcharacterStyle[n3]).getForegroundColor() & 16777215));
                }
                if (!(arrcharacterStyle[n3] instanceof BackgroundColorSpan)) continue;
                stringBuilder.append(String.format("<span style=\"background-color:#%06X;\">", 16777215 & ((BackgroundColorSpan)arrcharacterStyle[n3]).getBackgroundColor()));
            }
            Html.withinStyle(stringBuilder, spanned, n, n4);
            for (n = arrcharacterStyle.length - 1; n >= 0; --n) {
                if (arrcharacterStyle[n] instanceof BackgroundColorSpan) {
                    stringBuilder.append("</span>");
                }
                if (arrcharacterStyle[n] instanceof ForegroundColorSpan) {
                    stringBuilder.append("</span>");
                }
                if (arrcharacterStyle[n] instanceof RelativeSizeSpan) {
                    stringBuilder.append("</span>");
                }
                if (arrcharacterStyle[n] instanceof AbsoluteSizeSpan) {
                    stringBuilder.append("</span>");
                }
                if (arrcharacterStyle[n] instanceof URLSpan) {
                    stringBuilder.append("</a>");
                }
                if (arrcharacterStyle[n] instanceof StrikethroughSpan) {
                    stringBuilder.append("</span>");
                }
                if (arrcharacterStyle[n] instanceof UnderlineSpan) {
                    stringBuilder.append("</u>");
                }
                if (arrcharacterStyle[n] instanceof SubscriptSpan) {
                    stringBuilder.append("</sub>");
                }
                if (arrcharacterStyle[n] instanceof SuperscriptSpan) {
                    stringBuilder.append("</sup>");
                }
                if (arrcharacterStyle[n] instanceof TypefaceSpan && ((TypefaceSpan)arrcharacterStyle[n]).getFamily().equals("monospace")) {
                    stringBuilder.append("</tt>");
                }
                if (!(arrcharacterStyle[n] instanceof StyleSpan)) continue;
                n3 = ((StyleSpan)arrcharacterStyle[n]).getStyle();
                if ((n3 & 1) != 0) {
                    stringBuilder.append("</b>");
                }
                if ((n3 & 2) == 0) continue;
                stringBuilder.append("</i>");
            }
            n = n4;
        }
    }

    @UnsupportedAppUsage
    private static void withinStyle(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
        while (n < n2) {
            int n3;
            char c = charSequence.charAt(n);
            if (c == '<') {
                stringBuilder.append("&lt;");
                n3 = n;
            } else if (c == '>') {
                stringBuilder.append("&gt;");
                n3 = n;
            } else if (c == '&') {
                stringBuilder.append("&amp;");
                n3 = n;
            } else if (c >= '\ud800' && c <= '\udfff') {
                n3 = n;
                if (c < '\udc00') {
                    n3 = n;
                    if (n + 1 < n2) {
                        char c2 = charSequence.charAt(n + 1);
                        n3 = n;
                        if (c2 >= '\udc00') {
                            n3 = n;
                            if (c2 <= '\udfff') {
                                n3 = n + 1;
                                stringBuilder.append("&#");
                                stringBuilder.append(c - 55296 << 10 | 65536 | c2 - 56320);
                                stringBuilder.append(";");
                            }
                        }
                    }
                }
            } else if (c <= '~' && c >= ' ') {
                if (c == ' ') {
                    while (n + 1 < n2 && charSequence.charAt(n + 1) == ' ') {
                        stringBuilder.append("&nbsp;");
                        ++n;
                    }
                    stringBuilder.append(' ');
                    n3 = n;
                } else {
                    stringBuilder.append(c);
                    n3 = n;
                }
            } else {
                stringBuilder.append("&#");
                stringBuilder.append((int)c);
                stringBuilder.append(";");
                n3 = n;
            }
            n = n3 + 1;
        }
    }

    private static class HtmlParser {
        private static final HTMLSchema schema = new HTMLSchema();

        private HtmlParser() {
        }
    }

    public static interface ImageGetter {
        public Drawable getDrawable(String var1);
    }

    public static interface TagHandler {
        public void handleTag(boolean var1, String var2, Editable var3, XMLReader var4);
    }

}

