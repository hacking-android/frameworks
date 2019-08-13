/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.fonts.FontVariationAxis;
import android.text.FontConfig;
import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontListParser {
    private static final Pattern FILENAME_WHITESPACE_PATTERN = Pattern.compile("^[ \\n\\r\\t]+|[ \\n\\r\\t]+$");

    @UnsupportedAppUsage
    public static FontConfig parse(InputStream inputStream) throws XmlPullParserException, IOException {
        return FontListParser.parse(inputStream, "/system/fonts");
    }

    public static FontConfig parse(InputStream inputStream, String object) throws XmlPullParserException, IOException {
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setInput(inputStream, null);
            xmlPullParser.nextTag();
            object = FontListParser.readFamilies(xmlPullParser, (String)object);
            return object;
        }
        finally {
            inputStream.close();
        }
    }

    public static FontConfig.Alias readAlias(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String string2 = xmlPullParser.getAttributeValue(null, "name");
        String string3 = xmlPullParser.getAttributeValue(null, "to");
        String string4 = xmlPullParser.getAttributeValue(null, "weight");
        int n = string4 == null ? 400 : Integer.parseInt(string4);
        FontListParser.skip(xmlPullParser);
        return new FontConfig.Alias(string2, string3, n);
    }

    private static FontVariationAxis readAxis(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        String string2 = xmlPullParser.getAttributeValue(null, "tag");
        String string3 = xmlPullParser.getAttributeValue(null, "stylevalue");
        FontListParser.skip(xmlPullParser);
        return new FontVariationAxis(string2, Float.parseFloat(string3));
    }

    private static FontConfig readFamilies(XmlPullParser xmlPullParser, String string2) throws XmlPullParserException, IOException {
        ArrayList<FontConfig.Family> arrayList = new ArrayList<FontConfig.Family>();
        ArrayList<FontConfig.Alias> arrayList2 = new ArrayList<FontConfig.Alias>();
        xmlPullParser.require(2, null, "familyset");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() != 2) continue;
            String string3 = xmlPullParser.getName();
            if (string3.equals("family")) {
                arrayList.add(FontListParser.readFamily(xmlPullParser, string2));
                continue;
            }
            if (string3.equals("alias")) {
                arrayList2.add(FontListParser.readAlias(xmlPullParser));
                continue;
            }
            FontListParser.skip(xmlPullParser);
        }
        return new FontConfig(arrayList.toArray(new FontConfig.Family[arrayList.size()]), arrayList2.toArray(new FontConfig.Alias[arrayList2.size()]));
    }

    public static FontConfig.Family readFamily(XmlPullParser xmlPullParser, String string2) throws XmlPullParserException, IOException {
        int n;
        String string3 = xmlPullParser.getAttributeValue(null, "name");
        String string4 = xmlPullParser.getAttributeValue("", "lang");
        String string5 = xmlPullParser.getAttributeValue(null, "variant");
        ArrayList<FontConfig.Font> arrayList = new ArrayList<FontConfig.Font>();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() != 2) continue;
            if (xmlPullParser.getName().equals("font")) {
                arrayList.add(FontListParser.readFont(xmlPullParser, string2));
                continue;
            }
            FontListParser.skip(xmlPullParser);
        }
        int n2 = n = 0;
        if (string5 != null) {
            if (string5.equals("compact")) {
                n2 = 1;
            } else {
                n2 = n;
                if (string5.equals("elegant")) {
                    n2 = 2;
                }
            }
        }
        return new FontConfig.Family(string3, arrayList.toArray(new FontConfig.Font[arrayList.size()]), string4, n2);
    }

    private static FontConfig.Font readFont(XmlPullParser object, String string2) throws XmlPullParserException, IOException {
        Object object2 = object.getAttributeValue(null, "index");
        int n = object2 == null ? 0 : Integer.parseInt((String)object2);
        object2 = new ArrayList();
        String string3 = object.getAttributeValue(null, "weight");
        int n2 = string3 == null ? 400 : Integer.parseInt(string3);
        boolean bl = "italic".equals(object.getAttributeValue(null, "style"));
        string3 = object.getAttributeValue(null, "fallbackFor");
        StringBuilder stringBuilder = new StringBuilder();
        while (object.next() != 3) {
            if (object.getEventType() == 4) {
                stringBuilder.append(object.getText());
            }
            if (object.getEventType() != 2) continue;
            if (object.getName().equals("axis")) {
                object2.add(FontListParser.readAxis(object));
                continue;
            }
            FontListParser.skip(object);
        }
        object = FILENAME_WHITESPACE_PATTERN.matcher(stringBuilder).replaceAll("");
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append((String)object);
        return new FontConfig.Font(stringBuilder.toString(), n, object2.toArray(new FontVariationAxis[object2.size()]), n2, bl, string3);
    }

    public static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n = 1;
        while (n > 0) {
            int n2 = xmlPullParser.next();
            if (n2 != 2) {
                if (n2 != 3) continue;
                --n;
                continue;
            }
            ++n;
        }
    }
}

