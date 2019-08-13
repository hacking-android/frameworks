/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.ccil.cowan.tagsoup.Parser
 */
package android.text;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.BulletSpan;
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
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.ccil.cowan.tagsoup.Parser;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

class HtmlToSpannedConverter
implements ContentHandler {
    private static final float[] HEADING_SIZES = new float[]{1.5f, 1.4f, 1.3f, 1.2f, 1.1f, 1.0f};
    private static Pattern sBackgroundColorPattern;
    private static final Map<String, Integer> sColorMap;
    private static Pattern sForegroundColorPattern;
    private static Pattern sTextAlignPattern;
    private static Pattern sTextDecorationPattern;
    private int mFlags;
    private Html.ImageGetter mImageGetter;
    private XMLReader mReader;
    private String mSource;
    private SpannableStringBuilder mSpannableStringBuilder;
    private Html.TagHandler mTagHandler;

    static {
        sColorMap = new HashMap<String, Integer>();
        Object object = sColorMap;
        Integer n = -5658199;
        object.put((String)"darkgray", (Integer)n);
        Object object2 = sColorMap;
        object = -8355712;
        object2.put((String)"gray", (Integer)object);
        Map<String, Integer> map = sColorMap;
        object2 = -2894893;
        map.put("lightgray", (Integer)object2);
        sColorMap.put("darkgrey", n);
        sColorMap.put("grey", (Integer)object);
        sColorMap.put("lightgrey", (Integer)object2);
        sColorMap.put("green", -16744448);
    }

    public HtmlToSpannedConverter(String string2, Html.ImageGetter imageGetter, Html.TagHandler tagHandler, Parser parser, int n) {
        this.mSource = string2;
        this.mSpannableStringBuilder = new SpannableStringBuilder();
        this.mImageGetter = imageGetter;
        this.mTagHandler = tagHandler;
        this.mReader = parser;
        this.mFlags = n;
    }

    private static void appendNewlines(Editable editable, int n) {
        int n2 = editable.length();
        if (n2 == 0) {
            return;
        }
        int n3 = 0;
        --n2;
        while (n2 >= 0 && editable.charAt(n2) == '\n') {
            ++n3;
            --n2;
        }
        while (n3 < n) {
            editable.append("\n");
            ++n3;
        }
    }

    private static void end(Editable editable, Class class_, Object object) {
        editable.length();
        class_ = HtmlToSpannedConverter.getLast(editable, class_);
        if (class_ != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, class_, object);
        }
    }

    private static void endA(Editable editable) {
        Href href = HtmlToSpannedConverter.getLast(editable, Href.class);
        if (href != null && href.mHref != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, href, new URLSpan(href.mHref));
        }
    }

    private static void endBlockElement(Editable editable) {
        Object object = HtmlToSpannedConverter.getLast(editable, Newline.class);
        if (object != null) {
            HtmlToSpannedConverter.appendNewlines(editable, ((Newline)object).mNumNewlines);
            editable.removeSpan(object);
        }
        if ((object = HtmlToSpannedConverter.getLast(editable, Alignment.class)) != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new AlignmentSpan.Standard(((Alignment)object).mAlignment));
        }
    }

    private static void endBlockquote(Editable editable) {
        HtmlToSpannedConverter.endBlockElement(editable);
        HtmlToSpannedConverter.end(editable, Blockquote.class, new QuoteSpan());
    }

    private static void endCssStyle(Editable editable) {
        Object object = HtmlToSpannedConverter.getLast(editable, Strikethrough.class);
        if (object != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new StrikethroughSpan());
        }
        if ((object = HtmlToSpannedConverter.getLast(editable, Background.class)) != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new BackgroundColorSpan(((Background)object).mBackgroundColor));
        }
        if ((object = HtmlToSpannedConverter.getLast(editable, Foreground.class)) != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new ForegroundColorSpan(((Foreground)object).mForegroundColor));
        }
    }

    private static void endFont(Editable editable) {
        Object object = HtmlToSpannedConverter.getLast(editable, Font.class);
        if (object != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new TypefaceSpan(((Font)object).mFace));
        }
        if ((object = HtmlToSpannedConverter.getLast(editable, Foreground.class)) != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, object, new ForegroundColorSpan(((Foreground)object).mForegroundColor));
        }
    }

    private static void endHeading(Editable editable) {
        Heading heading = HtmlToSpannedConverter.getLast(editable, Heading.class);
        if (heading != null) {
            HtmlToSpannedConverter.setSpanFromMark(editable, heading, new RelativeSizeSpan(HEADING_SIZES[heading.mLevel]), new StyleSpan(1));
        }
        HtmlToSpannedConverter.endBlockElement(editable);
    }

    private static void endLi(Editable editable) {
        HtmlToSpannedConverter.endCssStyle(editable);
        HtmlToSpannedConverter.endBlockElement(editable);
        HtmlToSpannedConverter.end(editable, Bullet.class, new BulletSpan());
    }

    private static Pattern getBackgroundColorPattern() {
        if (sBackgroundColorPattern == null) {
            sBackgroundColorPattern = Pattern.compile("(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b");
        }
        return sBackgroundColorPattern;
    }

    private static Pattern getForegroundColorPattern() {
        if (sForegroundColorPattern == null) {
            sForegroundColorPattern = Pattern.compile("(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b");
        }
        return sForegroundColorPattern;
    }

    private int getHtmlColor(String string2) {
        Integer n;
        if ((this.mFlags & 256) == 256 && (n = sColorMap.get(string2.toLowerCase(Locale.US))) != null) {
            return n;
        }
        return Color.getHtmlColor(string2);
    }

    private static <T> T getLast(Spanned arrT, Class<T> class_) {
        if ((arrT = arrT.getSpans(0, arrT.length(), class_)).length == 0) {
            return null;
        }
        return arrT[arrT.length - 1];
    }

    private int getMargin(int n) {
        if ((this.mFlags & n) != 0) {
            return 1;
        }
        return 2;
    }

    private int getMarginBlockquote() {
        return this.getMargin(32);
    }

    private int getMarginDiv() {
        return this.getMargin(16);
    }

    private int getMarginHeading() {
        return this.getMargin(2);
    }

    private int getMarginList() {
        return this.getMargin(8);
    }

    private int getMarginListItem() {
        return this.getMargin(4);
    }

    private int getMarginParagraph() {
        return this.getMargin(1);
    }

    private static Pattern getTextAlignPattern() {
        if (sTextAlignPattern == null) {
            sTextAlignPattern = Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b");
        }
        return sTextAlignPattern;
    }

    private static Pattern getTextDecorationPattern() {
        if (sTextDecorationPattern == null) {
            sTextDecorationPattern = Pattern.compile("(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b");
        }
        return sTextDecorationPattern;
    }

    private static void handleBr(Editable editable) {
        editable.append('\n');
    }

    private void handleEndTag(String string2) {
        if (string2.equalsIgnoreCase("br")) {
            HtmlToSpannedConverter.handleBr(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("p")) {
            HtmlToSpannedConverter.endCssStyle(this.mSpannableStringBuilder);
            HtmlToSpannedConverter.endBlockElement(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("ul")) {
            HtmlToSpannedConverter.endBlockElement(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("li")) {
            HtmlToSpannedConverter.endLi(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("div")) {
            HtmlToSpannedConverter.endBlockElement(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("span")) {
            HtmlToSpannedConverter.endCssStyle(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("strong")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Bold.class, new StyleSpan(1));
        } else if (string2.equalsIgnoreCase("b")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Bold.class, new StyleSpan(1));
        } else if (string2.equalsIgnoreCase("em")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
        } else if (string2.equalsIgnoreCase("cite")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
        } else if (string2.equalsIgnoreCase("dfn")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
        } else if (string2.equalsIgnoreCase("i")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Italic.class, new StyleSpan(2));
        } else if (string2.equalsIgnoreCase("big")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Big.class, new RelativeSizeSpan(1.25f));
        } else if (string2.equalsIgnoreCase("small")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Small.class, new RelativeSizeSpan(0.8f));
        } else if (string2.equalsIgnoreCase("font")) {
            HtmlToSpannedConverter.endFont(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("blockquote")) {
            HtmlToSpannedConverter.endBlockquote(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("tt")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Monospace.class, new TypefaceSpan("monospace"));
        } else if (string2.equalsIgnoreCase("a")) {
            HtmlToSpannedConverter.endA(this.mSpannableStringBuilder);
        } else if (string2.equalsIgnoreCase("u")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Underline.class, new UnderlineSpan());
        } else if (string2.equalsIgnoreCase("del")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Strikethrough.class, new StrikethroughSpan());
        } else if (string2.equalsIgnoreCase("s")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Strikethrough.class, new StrikethroughSpan());
        } else if (string2.equalsIgnoreCase("strike")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Strikethrough.class, new StrikethroughSpan());
        } else if (string2.equalsIgnoreCase("sup")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Super.class, new SuperscriptSpan());
        } else if (string2.equalsIgnoreCase("sub")) {
            HtmlToSpannedConverter.end(this.mSpannableStringBuilder, Sub.class, new SubscriptSpan());
        } else if (string2.length() == 2 && Character.toLowerCase(string2.charAt(0)) == 'h' && string2.charAt(1) >= '1' && string2.charAt(1) <= '6') {
            HtmlToSpannedConverter.endHeading(this.mSpannableStringBuilder);
        } else {
            Html.TagHandler tagHandler = this.mTagHandler;
            if (tagHandler != null) {
                tagHandler.handleTag(false, string2, this.mSpannableStringBuilder, this.mReader);
            }
        }
    }

    private void handleStartTag(String string2, Attributes object) {
        if (!string2.equalsIgnoreCase("br")) {
            if (string2.equalsIgnoreCase("p")) {
                HtmlToSpannedConverter.startBlockElement(this.mSpannableStringBuilder, (Attributes)object, this.getMarginParagraph());
                this.startCssStyle(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("ul")) {
                HtmlToSpannedConverter.startBlockElement(this.mSpannableStringBuilder, (Attributes)object, this.getMarginList());
            } else if (string2.equalsIgnoreCase("li")) {
                this.startLi(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("div")) {
                HtmlToSpannedConverter.startBlockElement(this.mSpannableStringBuilder, (Attributes)object, this.getMarginDiv());
            } else if (string2.equalsIgnoreCase("span")) {
                this.startCssStyle(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("strong")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Bold());
            } else if (string2.equalsIgnoreCase("b")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Bold());
            } else if (string2.equalsIgnoreCase("em")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Italic());
            } else if (string2.equalsIgnoreCase("cite")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Italic());
            } else if (string2.equalsIgnoreCase("dfn")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Italic());
            } else if (string2.equalsIgnoreCase("i")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Italic());
            } else if (string2.equalsIgnoreCase("big")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Big());
            } else if (string2.equalsIgnoreCase("small")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Small());
            } else if (string2.equalsIgnoreCase("font")) {
                this.startFont(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("blockquote")) {
                this.startBlockquote(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("tt")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Monospace());
            } else if (string2.equalsIgnoreCase("a")) {
                HtmlToSpannedConverter.startA(this.mSpannableStringBuilder, (Attributes)object);
            } else if (string2.equalsIgnoreCase("u")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Underline());
            } else if (string2.equalsIgnoreCase("del")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Strikethrough());
            } else if (string2.equalsIgnoreCase("s")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Strikethrough());
            } else if (string2.equalsIgnoreCase("strike")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Strikethrough());
            } else if (string2.equalsIgnoreCase("sup")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Super());
            } else if (string2.equalsIgnoreCase("sub")) {
                HtmlToSpannedConverter.start(this.mSpannableStringBuilder, new Sub());
            } else if (string2.length() == 2 && Character.toLowerCase(string2.charAt(0)) == 'h' && string2.charAt(1) >= '1' && string2.charAt(1) <= '6') {
                this.startHeading(this.mSpannableStringBuilder, (Attributes)object, string2.charAt(1) - 49);
            } else if (string2.equalsIgnoreCase("img")) {
                HtmlToSpannedConverter.startImg(this.mSpannableStringBuilder, (Attributes)object, this.mImageGetter);
            } else {
                object = this.mTagHandler;
                if (object != null) {
                    object.handleTag(true, string2, this.mSpannableStringBuilder, this.mReader);
                }
            }
        }
    }

    private static void setSpanFromMark(Spannable spannable, Object object, Object ... arrobject) {
        int n = spannable.getSpanStart(object);
        spannable.removeSpan(object);
        int n2 = spannable.length();
        if (n != n2) {
            int n3 = arrobject.length;
            for (int i = 0; i < n3; ++i) {
                spannable.setSpan(arrobject[i], n, n2, 33);
            }
        }
    }

    private static void start(Editable editable, Object object) {
        int n = editable.length();
        editable.setSpan(object, n, n, 17);
    }

    private static void startA(Editable editable, Attributes attributes) {
        HtmlToSpannedConverter.start(editable, new Href(attributes.getValue("", "href")));
    }

    private static void startBlockElement(Editable editable, Attributes object, int n) {
        editable.length();
        if (n > 0) {
            HtmlToSpannedConverter.appendNewlines(editable, n);
            HtmlToSpannedConverter.start(editable, new Newline(n));
        }
        if ((object = object.getValue("", "style")) != null && ((Matcher)(object = HtmlToSpannedConverter.getTextAlignPattern().matcher((CharSequence)object))).find()) {
            if (((String)(object = ((Matcher)object).group(1))).equalsIgnoreCase("start")) {
                HtmlToSpannedConverter.start(editable, new Alignment(Layout.Alignment.ALIGN_NORMAL));
            } else if (((String)object).equalsIgnoreCase("center")) {
                HtmlToSpannedConverter.start(editable, new Alignment(Layout.Alignment.ALIGN_CENTER));
            } else if (((String)object).equalsIgnoreCase("end")) {
                HtmlToSpannedConverter.start(editable, new Alignment(Layout.Alignment.ALIGN_OPPOSITE));
            }
        }
    }

    private void startBlockquote(Editable editable, Attributes attributes) {
        HtmlToSpannedConverter.startBlockElement(editable, attributes, this.getMarginBlockquote());
        HtmlToSpannedConverter.start(editable, new Blockquote());
    }

    private void startCssStyle(Editable editable, Attributes object) {
        if ((object = object.getValue("", "style")) != null) {
            int n;
            Matcher matcher = HtmlToSpannedConverter.getForegroundColorPattern().matcher((CharSequence)object);
            if (matcher.find() && (n = this.getHtmlColor(matcher.group(1))) != -1) {
                HtmlToSpannedConverter.start(editable, new Foreground(n | -16777216));
            }
            if ((matcher = HtmlToSpannedConverter.getBackgroundColorPattern().matcher((CharSequence)object)).find() && (n = this.getHtmlColor(matcher.group(1))) != -1) {
                HtmlToSpannedConverter.start(editable, new Background(-16777216 | n));
            }
            if (((Matcher)(object = HtmlToSpannedConverter.getTextDecorationPattern().matcher((CharSequence)object))).find() && ((Matcher)object).group(1).equalsIgnoreCase("line-through")) {
                HtmlToSpannedConverter.start(editable, new Strikethrough());
            }
        }
    }

    private void startFont(Editable editable, Attributes object) {
        int n;
        String string2 = object.getValue("", "color");
        object = object.getValue("", "face");
        if (!TextUtils.isEmpty(string2) && (n = this.getHtmlColor(string2)) != -1) {
            HtmlToSpannedConverter.start(editable, new Foreground(-16777216 | n));
        }
        if (!TextUtils.isEmpty((CharSequence)object)) {
            HtmlToSpannedConverter.start(editable, new Font((String)object));
        }
    }

    private void startHeading(Editable editable, Attributes attributes, int n) {
        HtmlToSpannedConverter.startBlockElement(editable, attributes, this.getMarginHeading());
        HtmlToSpannedConverter.start(editable, new Heading(n));
    }

    private static void startImg(Editable editable, Attributes object, Html.ImageGetter object2) {
        String string2 = object.getValue("", "src");
        object = null;
        if (object2 != null) {
            object = object2.getDrawable(string2);
        }
        object2 = object;
        if (object == null) {
            object2 = Resources.getSystem().getDrawable(17303762);
            ((Drawable)object2).setBounds(0, 0, ((Drawable)object2).getIntrinsicWidth(), ((Drawable)object2).getIntrinsicHeight());
        }
        int n = editable.length();
        editable.append("\ufffc");
        editable.setSpan(new ImageSpan((Drawable)object2, string2), n, editable.length(), 33);
    }

    private void startLi(Editable editable, Attributes attributes) {
        HtmlToSpannedConverter.startBlockElement(editable, attributes, this.getMarginListItem());
        HtmlToSpannedConverter.start(editable, new Bullet());
        this.startCssStyle(editable, attributes);
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n2; ++i) {
            char c = arrc[i + n];
            if (c != ' ' && c != '\n') {
                stringBuilder.append(c);
                continue;
            }
            int n3 = stringBuilder.length();
            n3 = n3 == 0 ? ((n3 = this.mSpannableStringBuilder.length()) == 0 ? 10 : (int)this.mSpannableStringBuilder.charAt(n3 - 1)) : (int)stringBuilder.charAt(n3 - 1);
            if (n3 == 32 || n3 == 10) continue;
            stringBuilder.append(' ');
        }
        this.mSpannableStringBuilder.append(stringBuilder);
    }

    public Spanned convert() {
        ParagraphStyle[] arrparagraphStyle;
        this.mReader.setContentHandler(this);
        try {
            arrparagraphStyle = this.mReader;
            StringReader stringReader = new StringReader(this.mSource);
            InputSource inputSource = new InputSource(stringReader);
            arrparagraphStyle.parse(inputSource);
            arrparagraphStyle = this.mSpannableStringBuilder;
            arrparagraphStyle = arrparagraphStyle.getSpans(0, arrparagraphStyle.length(), ParagraphStyle.class);
        }
        catch (SAXException sAXException) {
            throw new RuntimeException(sAXException);
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
        for (int i = 0; i < arrparagraphStyle.length; ++i) {
            int n;
            int n2 = this.mSpannableStringBuilder.getSpanStart(arrparagraphStyle[i]);
            int n3 = n = this.mSpannableStringBuilder.getSpanEnd(arrparagraphStyle[i]);
            if (n - 2 >= 0) {
                n3 = n;
                if (this.mSpannableStringBuilder.charAt(n - 1) == '\n') {
                    n3 = n;
                    if (this.mSpannableStringBuilder.charAt(n - 2) == '\n') {
                        n3 = n - 1;
                    }
                }
            }
            if (n3 == n2) {
                this.mSpannableStringBuilder.removeSpan(arrparagraphStyle[i]);
                continue;
            }
            this.mSpannableStringBuilder.setSpan(arrparagraphStyle[i], n2, n3, 51);
        }
        return this.mSpannableStringBuilder;
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String string2, String string3, String string4) throws SAXException {
        this.handleEndTag(string3);
    }

    @Override
    public void endPrefixMapping(String string2) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void processingInstruction(String string2, String string3) throws SAXException {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void skippedEntity(String string2) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void startElement(String string2, String string3, String string4, Attributes attributes) throws SAXException {
        this.handleStartTag(string3, attributes);
    }

    @Override
    public void startPrefixMapping(String string2, String string3) throws SAXException {
    }

    private static class Alignment {
        private Layout.Alignment mAlignment;

        public Alignment(Layout.Alignment alignment) {
            this.mAlignment = alignment;
        }
    }

    private static class Background {
        private int mBackgroundColor;

        public Background(int n) {
            this.mBackgroundColor = n;
        }
    }

    private static class Big {
        private Big() {
        }
    }

    private static class Blockquote {
        private Blockquote() {
        }
    }

    private static class Bold {
        private Bold() {
        }
    }

    private static class Bullet {
        private Bullet() {
        }
    }

    private static class Font {
        public String mFace;

        public Font(String string2) {
            this.mFace = string2;
        }
    }

    private static class Foreground {
        private int mForegroundColor;

        public Foreground(int n) {
            this.mForegroundColor = n;
        }
    }

    private static class Heading {
        private int mLevel;

        public Heading(int n) {
            this.mLevel = n;
        }
    }

    private static class Href {
        public String mHref;

        public Href(String string2) {
            this.mHref = string2;
        }
    }

    private static class Italic {
        private Italic() {
        }
    }

    private static class Monospace {
        private Monospace() {
        }
    }

    private static class Newline {
        private int mNumNewlines;

        public Newline(int n) {
            this.mNumNewlines = n;
        }
    }

    private static class Small {
        private Small() {
        }
    }

    private static class Strikethrough {
        private Strikethrough() {
        }
    }

    private static class Sub {
        private Sub() {
        }
    }

    private static class Super {
        private Super() {
        }
    }

    private static class Underline {
        private Underline() {
        }
    }

}

