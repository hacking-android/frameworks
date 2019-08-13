/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.fonts;

import android.graphics.FontListParser;
import android.text.FontConfig;
import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontCustomizationParser {
    public static Result parse(InputStream inputStream, String string2) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        xmlPullParser.nextTag();
        return FontCustomizationParser.readFamilies(xmlPullParser, string2);
    }

    private static Result readFamilies(XmlPullParser xmlPullParser, String string2) throws XmlPullParserException, IOException {
        Result result = new Result();
        xmlPullParser.require(2, null, "fonts-modification");
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() != 2) continue;
            String string3 = xmlPullParser.getName();
            if (string3.equals("family")) {
                FontCustomizationParser.readFamily(xmlPullParser, string2, result);
                continue;
            }
            if (string3.equals("alias")) {
                result.mAdditionalAliases.add(FontListParser.readAlias(xmlPullParser));
                continue;
            }
            FontListParser.skip(xmlPullParser);
        }
        FontCustomizationParser.validate(result);
        return result;
    }

    private static void readFamily(XmlPullParser object, String string2, Result result) throws XmlPullParserException, IOException {
        String string3 = object.getAttributeValue(null, "customizationType");
        if (string3 != null) {
            if (string3.equals("new-named-family")) {
                result.mAdditionalNamedFamilies.add(FontListParser.readFamily((XmlPullParser)object, string2));
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown customizationType=");
            ((StringBuilder)object).append(string3);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("customizationType must be specified");
    }

    private static void validate(Result result) {
        HashSet<String> hashSet = new HashSet<String>();
        for (int i = 0; i < result.mAdditionalNamedFamilies.size(); ++i) {
            String string2 = result.mAdditionalNamedFamilies.get(i).getName();
            if (string2 != null) {
                if (hashSet.add(string2)) {
                    continue;
                }
                throw new IllegalArgumentException("new-named-family requires unique name attribute");
            }
            throw new IllegalArgumentException("new-named-family requires name attribute");
        }
    }

    public static class Result {
        ArrayList<FontConfig.Alias> mAdditionalAliases = new ArrayList();
        ArrayList<FontConfig.Family> mAdditionalNamedFamilies = new ArrayList();
    }

}

