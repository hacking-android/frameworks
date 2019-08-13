/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class FontResourcesParser {
    private static final String TAG = "FontResourcesParser";

    public static FamilyResourceEntry parse(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        int n;
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            return FontResourcesParser.readFamilies(xmlPullParser, resources);
        }
        throw new XmlPullParserException("No start tag found");
    }

    private static FamilyResourceEntry readFamilies(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return FontResourcesParser.readFamily(xmlPullParser, resources);
        }
        FontResourcesParser.skip(xmlPullParser);
        Log.e(TAG, "Failed to find font-family tag");
        return null;
    }

    private static FamilyResourceEntry readFamily(XmlPullParser object, Resources resources) throws XmlPullParserException, IOException {
        Object object2 = resources.obtainAttributes(Xml.asAttributeSet((XmlPullParser)object), R.styleable.FontFamily);
        Object object3 = ((TypedArray)object2).getString(0);
        String string2 = ((TypedArray)object2).getString(2);
        int n = 1;
        String string3 = ((TypedArray)object2).getString(1);
        int n2 = ((TypedArray)object2).getResourceId(3, 0);
        ((TypedArray)object2).recycle();
        if (object3 != null && string2 != null && string3 != null) {
            while (object.next() != 3) {
                FontResourcesParser.skip((XmlPullParser)object);
            }
            object = object2 = null;
            if (n2 != 0) {
                TypedArray typedArray = resources.obtainTypedArray(n2);
                object = object2;
                if (typedArray.length() > 0) {
                    object = new ArrayList();
                    if (typedArray.getResourceId(0, 0) == 0) {
                        n = 0;
                    }
                    if (n != 0) {
                        for (n = 0; n < typedArray.length(); ++n) {
                            object.add(Arrays.asList(resources.getStringArray(typedArray.getResourceId(n, 0))));
                        }
                    } else {
                        object.add(Arrays.asList(resources.getStringArray(n2)));
                    }
                }
            }
            return new ProviderResourceEntry((String)object3, string2, string3, (List<List<String>>)object);
        }
        object2 = new ArrayList();
        while (object.next() != 3) {
            if (object.getEventType() != 2) continue;
            if (object.getName().equals("font")) {
                object3 = FontResourcesParser.readFont((XmlPullParser)object, resources);
                if (object3 == null) continue;
                object2.add(object3);
                continue;
            }
            FontResourcesParser.skip((XmlPullParser)object);
        }
        if (object2.isEmpty()) {
            return null;
        }
        return new FontFamilyFilesResourceEntry(object2.toArray(new FontFileResourceEntry[object2.size()]));
    }

    private static FontFileResourceEntry readFont(XmlPullParser xmlPullParser, Resources object) throws XmlPullParserException, IOException {
        TypedArray typedArray = ((Resources)object).obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.FontFamilyFont);
        int n = typedArray.getInt(1, -1);
        int n2 = typedArray.getInt(2, -1);
        String string2 = typedArray.getString(4);
        int n3 = typedArray.getInt(3, 0);
        object = typedArray.getString(0);
        typedArray.recycle();
        while (xmlPullParser.next() != 3) {
            FontResourcesParser.skip(xmlPullParser);
        }
        if (object == null) {
            return null;
        }
        return new FontFileResourceEntry((String)object, n, n2, string2, n3);
    }

    private static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
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

    public static interface FamilyResourceEntry {
    }

    public static final class FontFamilyFilesResourceEntry
    implements FamilyResourceEntry {
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(FontFileResourceEntry[] arrfontFileResourceEntry) {
            this.mEntries = arrfontFileResourceEntry;
        }

        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static final class FontFileResourceEntry {
        public static final int ITALIC = 1;
        public static final int RESOLVE_BY_FONT_TABLE = -1;
        public static final int UPRIGHT = 0;
        private final String mFileName;
        private int mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(String string2, int n, int n2, String string3, int n3) {
            this.mFileName = string2;
            this.mWeight = n;
            this.mItalic = n2;
            this.mVariationSettings = string3;
            this.mTtcIndex = n3;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public int getItalic() {
            return this.mItalic;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }

        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getWeight() {
            return this.mWeight;
        }
    }

    public static final class ProviderResourceEntry
    implements FamilyResourceEntry {
        private final List<List<String>> mCerts;
        private final String mProviderAuthority;
        private final String mProviderPackage;
        private final String mQuery;

        public ProviderResourceEntry(String string2, String string3, String string4, List<List<String>> list) {
            this.mProviderAuthority = string2;
            this.mProviderPackage = string3;
            this.mQuery = string4;
            this.mCerts = list;
        }

        public String getAuthority() {
            return this.mProviderAuthority;
        }

        public List<List<String>> getCerts() {
            return this.mCerts;
        }

        public String getPackage() {
            return this.mProviderPackage;
        }

        public String getQuery() {
            return this.mQuery;
        }
    }

}

