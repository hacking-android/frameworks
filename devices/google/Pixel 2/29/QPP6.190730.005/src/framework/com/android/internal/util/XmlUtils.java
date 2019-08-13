/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Base64;
import android.util.Xml;
import com.android.internal.util.FastXmlSerializer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class XmlUtils {
    private static final String STRING_ARRAY_SEPARATOR = ":";

    @UnsupportedAppUsage
    public static final void beginDocument(XmlPullParser xmlPullParser, String string2) throws XmlPullParserException, IOException {
        int n;
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n == 2) {
            if (xmlPullParser.getName().equals(string2)) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected start tag: found ");
            stringBuilder.append(xmlPullParser.getName());
            stringBuilder.append(", expected ");
            stringBuilder.append(string2);
            throw new XmlPullParserException(stringBuilder.toString());
        }
        throw new XmlPullParserException("No start tag found");
    }

    @UnsupportedAppUsage
    public static final boolean convertValueToBoolean(CharSequence charSequence, boolean bl) {
        block5 : {
            block4 : {
                boolean bl2 = false;
                if (TextUtils.isEmpty(charSequence)) {
                    return bl;
                }
                if (charSequence.equals("1") || charSequence.equals("true")) break block4;
                bl = bl2;
                if (!charSequence.equals("TRUE")) break block5;
            }
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public static final int convertValueToInt(CharSequence charSequence, int n) {
        if (TextUtils.isEmpty(charSequence)) {
            return n;
        }
        charSequence = charSequence.toString();
        int n2 = 1;
        n = 0;
        int n3 = ((String)charSequence).length();
        int n4 = 10;
        if ('-' == ((String)charSequence).charAt(0)) {
            n2 = -1;
            n = 0 + 1;
        }
        if ('0' == ((String)charSequence).charAt(n)) {
            if (n == n3 - 1) {
                return 0;
            }
            n3 = ((String)charSequence).charAt(n + 1);
            if (120 != n3 && 88 != n3) {
                n3 = n + 1;
                n = 8;
            } else {
                n3 = n + 2;
                n = 16;
            }
            n4 = n;
        } else {
            n3 = n;
            if ('#' == ((String)charSequence).charAt(n)) {
                n3 = n + 1;
                n4 = 16;
            }
        }
        return Integer.parseInt(((String)charSequence).substring(n3), n4) * n2;
    }

    public static final int convertValueToList(CharSequence charSequence, String[] arrstring, int n) {
        if (!TextUtils.isEmpty(charSequence)) {
            for (int i = 0; i < arrstring.length; ++i) {
                if (!charSequence.equals(arrstring[i])) continue;
                return i;
            }
        }
        return n;
    }

    public static int convertValueToUnsignedInt(String string2, int n) {
        if (TextUtils.isEmpty(string2)) {
            return n;
        }
        return XmlUtils.parseUnsignedIntAttribute(string2);
    }

    @UnsupportedAppUsage
    public static final void nextElement(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
    }

    public static boolean nextElementWithin(XmlPullParser xmlPullParser, int n) throws IOException, XmlPullParserException {
        int n2;
        while ((n2 = xmlPullParser.next()) != 1 && (n2 != 3 || xmlPullParser.getDepth() != n)) {
            if (n2 != 2 || xmlPullParser.getDepth() != n + 1) continue;
            return true;
        }
        return false;
    }

    public static int parseUnsignedIntAttribute(CharSequence charSequence) {
        int n;
        int n2;
        block4 : {
            block3 : {
                charSequence = charSequence.toString();
                n2 = 0;
                int n3 = ((String)charSequence).length();
                n = 10;
                if ('0' != ((String)charSequence).charAt(0)) break block3;
                if (n3 - 1 == 0) {
                    return 0;
                }
                n2 = ((String)charSequence).charAt(0 + 1);
                if (120 != n2 && 88 != n2) {
                    n2 = 0 + 1;
                    n = 8;
                } else {
                    n2 = 0 + 2;
                    n = 16;
                }
                break block4;
            }
            if ('#' != ((String)charSequence).charAt(0)) break block4;
            n2 = 0 + 1;
            n = 16;
        }
        return (int)Long.parseLong(((String)charSequence).substring(n2), n);
    }

    public static Bitmap readBitmapAttribute(XmlPullParser arrby, String string2) {
        if ((arrby = XmlUtils.readByteArrayAttribute((XmlPullParser)arrby, string2)) != null) {
            return BitmapFactory.decodeByteArray(arrby, 0, arrby.length);
        }
        return null;
    }

    public static boolean readBooleanAttribute(XmlPullParser xmlPullParser, String string2) {
        return Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, string2));
    }

    public static boolean readBooleanAttribute(XmlPullParser object, String string2, boolean bl) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return bl;
        }
        return Boolean.parseBoolean((String)object);
    }

    public static byte[] readByteArrayAttribute(XmlPullParser object, String string2) {
        if (!TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return Base64.decode((String)object, 0);
        }
        return null;
    }

    public static float readFloatAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            float f = Float.parseFloat((String)object);
            return f;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problem parsing ");
            stringBuilder.append(string2);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            stringBuilder.append(" as long");
            throw new ProtocolException(stringBuilder.toString());
        }
    }

    public static int readIntAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            int n = Integer.parseInt((String)object);
            return n;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problem parsing ");
            stringBuilder.append(string2);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            stringBuilder.append(" as int");
            throw new ProtocolException(stringBuilder.toString());
        }
    }

    public static int readIntAttribute(XmlPullParser object, String string2, int n) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return n;
        }
        try {
            int n2 = Integer.parseInt((String)object);
            return n2;
        }
        catch (NumberFormatException numberFormatException) {
            return n;
        }
    }

    public static final ArrayList readListXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return (ArrayList)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static long readLongAttribute(XmlPullParser object, String string2) throws IOException {
        object = object.getAttributeValue(null, string2);
        try {
            long l = Long.parseLong((String)object);
            return l;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("problem parsing ");
            stringBuilder.append(string2);
            stringBuilder.append("=");
            stringBuilder.append((String)object);
            stringBuilder.append(" as long");
            throw new ProtocolException(stringBuilder.toString());
        }
    }

    public static long readLongAttribute(XmlPullParser object, String string2, long l) {
        if (TextUtils.isEmpty((CharSequence)(object = object.getAttributeValue(null, string2)))) {
            return l;
        }
        try {
            long l2 = Long.parseLong((String)object);
            return l2;
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
    }

    @UnsupportedAppUsage
    public static final HashMap<String, ?> readMapXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, StandardCharsets.UTF_8.name());
        return (HashMap)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static final HashSet readSetXml(InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(inputStream, null);
        return (HashSet)XmlUtils.readValueXml(xmlPullParser, new String[1]);
    }

    public static String readStringAttribute(XmlPullParser xmlPullParser, String string2) {
        return xmlPullParser.getAttributeValue(null, string2);
    }

    public static final ArrayMap<String, ?> readThisArrayMapXml(XmlPullParser object, String string2, String[] object2, ReadMapCallback readMapCallback) throws XmlPullParserException, IOException {
        ArrayMap<String, Object> arrayMap = new ArrayMap<String, Object>();
        int n = object.getEventType();
        do {
            if (n == 2) {
                Object object3 = XmlUtils.readThisValueXml((XmlPullParser)object, (String[])object2, readMapCallback, true);
                arrayMap.put(object2[0], object3);
            } else if (n == 3) {
                if (object.getName().equals(string2)) {
                    return arrayMap;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Expected ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" end tag at: ");
                ((StringBuilder)object2).append(object.getName());
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            n = object.next();
        } while (n != 1);
        object = new StringBuilder();
        ((StringBuilder)object).append("Document ended before ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" end tag");
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final boolean[] readThisBooleanArrayXml(XmlPullParser object, String charSequence, String[] object2) throws XmlPullParserException, IOException {
        int n;
        try {
            n = Integer.parseInt(object.getAttributeValue(null, "num"));
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in string-array");
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in string-array");
        }
        object.next();
        object2 = new boolean[n];
        n = 0;
        int n2 = object.getEventType();
        do {
            int n3;
            if (n2 == 2) {
                if (!object.getName().equals("item")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Expected item tag at: ");
                    ((StringBuilder)charSequence).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                try {
                    object2[n] = Boolean.parseBoolean(object.getAttributeValue(null, "value"));
                    n3 = n;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
            } else {
                n3 = n;
                if (n2 == 3) {
                    if (object.getName().equals(charSequence)) {
                        return object2;
                    }
                    if (!object.getName().equals("item")) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected ");
                        ((StringBuilder)object2).append((String)charSequence);
                        ((StringBuilder)object2).append(" end tag at: ");
                        ((StringBuilder)object2).append(object.getName());
                        throw new XmlPullParserException(((StringBuilder)object2).toString());
                    }
                    n3 = n + 1;
                }
            }
            n2 = object.next();
            if (n2 == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Document ended before ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" end tag");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n = n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final byte[] readThisByteArrayXml(XmlPullParser object, String string2, String[] object2) throws XmlPullParserException, IOException {
        block8 : {
            int n;
            try {
                n = Integer.parseInt(object.getAttributeValue(null, "num"));
            }
            catch (NumberFormatException numberFormatException) {
                throw new XmlPullParserException("Not a number in num attribute in byte-array");
            }
            catch (NullPointerException nullPointerException) {
                throw new XmlPullParserException("Need num attribute in byte-array");
            }
            byte[] arrby = new byte[n];
            int n2 = object.getEventType();
            do {
                if (n2 == 4) {
                    if (n <= 0) continue;
                    object2 = object.getText();
                    if (object2 == null || ((String)object2).length() != n * 2) break block8;
                } else {
                    if (n2 != 3) continue;
                    if (object.getName().equals(string2)) {
                        return arrby;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Expected ");
                    ((StringBuilder)object2).append(string2);
                    ((StringBuilder)object2).append(" end tag at: ");
                    ((StringBuilder)object2).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)object2).toString());
                }
                for (n2 = 0; n2 < n; ++n2) {
                    int n3 = ((String)object2).charAt(n2 * 2);
                    int n4 = ((String)object2).charAt(n2 * 2 + 1);
                    n3 = n3 > 97 ? n3 - 97 + 10 : (n3 -= 48);
                    n4 = n4 > 97 ? n4 - 97 + 10 : (n4 -= 48);
                    arrby[n2] = (byte)((n3 & 15) << 4 | n4 & 15);
                }
            } while ((n2 = object.next()) != 1);
            object = new StringBuilder();
            ((StringBuilder)object).append("Document ended before ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" end tag");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid value found in byte-array: ");
        ((StringBuilder)object).append((String)object2);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final double[] readThisDoubleArrayXml(XmlPullParser object, String charSequence, String[] object2) throws XmlPullParserException, IOException {
        int n;
        try {
            n = Integer.parseInt(object.getAttributeValue(null, "num"));
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in double-array");
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in double-array");
        }
        object.next();
        object2 = new double[n];
        n = 0;
        int n2 = object.getEventType();
        do {
            int n3;
            if (n2 == 2) {
                if (!object.getName().equals("item")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Expected item tag at: ");
                    ((StringBuilder)charSequence).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                try {
                    object2[n] = Double.parseDouble(object.getAttributeValue(null, "value"));
                    n3 = n;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
            } else {
                n3 = n;
                if (n2 == 3) {
                    if (object.getName().equals(charSequence)) {
                        return object2;
                    }
                    if (!object.getName().equals("item")) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected ");
                        ((StringBuilder)object2).append((String)charSequence);
                        ((StringBuilder)object2).append(" end tag at: ");
                        ((StringBuilder)object2).append(object.getName());
                        throw new XmlPullParserException(((StringBuilder)object2).toString());
                    }
                    n3 = n + 1;
                }
            }
            n2 = object.next();
            if (n2 == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Document ended before ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" end tag");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n = n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final int[] readThisIntArrayXml(XmlPullParser object, String charSequence, String[] object2) throws XmlPullParserException, IOException {
        int n;
        try {
            n = Integer.parseInt(object.getAttributeValue(null, "num"));
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in int-array");
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in int-array");
        }
        object.next();
        object2 = new int[n];
        int n2 = 0;
        int n3 = object.getEventType();
        do {
            if (n3 == 2) {
                if (!object.getName().equals("item")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Expected item tag at: ");
                    ((StringBuilder)charSequence).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                try {
                    object2[n2] = Integer.parseInt(object.getAttributeValue(null, "value"));
                    n = n2;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
            } else {
                n = n2;
                if (n3 == 3) {
                    if (object.getName().equals(charSequence)) {
                        return object2;
                    }
                    if (!object.getName().equals("item")) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected ");
                        ((StringBuilder)object2).append((String)charSequence);
                        ((StringBuilder)object2).append(" end tag at: ");
                        ((StringBuilder)object2).append(object.getName());
                        throw new XmlPullParserException(((StringBuilder)object2).toString());
                    }
                    n = n2 + 1;
                }
            }
            n3 = object.next();
            if (n3 == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Document ended before ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" end tag");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n2 = n;
        } while (true);
    }

    public static final ArrayList readThisListXml(XmlPullParser xmlPullParser, String string2, String[] arrstring) throws XmlPullParserException, IOException {
        return XmlUtils.readThisListXml(xmlPullParser, string2, arrstring, null, false);
    }

    private static final ArrayList readThisListXml(XmlPullParser object, String string2, String[] object2, ReadMapCallback readMapCallback, boolean bl) throws XmlPullParserException, IOException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        int n = object.getEventType();
        do {
            if (n == 2) {
                arrayList.add(XmlUtils.readThisValueXml((XmlPullParser)object, (String[])object2, readMapCallback, bl));
            } else if (n == 3) {
                if (object.getName().equals(string2)) {
                    return arrayList;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Expected ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" end tag at: ");
                ((StringBuilder)object2).append(object.getName());
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            n = object.next();
        } while (n != 1);
        object = new StringBuilder();
        ((StringBuilder)object).append("Document ended before ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" end tag");
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final long[] readThisLongArrayXml(XmlPullParser object, String charSequence, String[] object2) throws XmlPullParserException, IOException {
        int n;
        try {
            n = Integer.parseInt(object.getAttributeValue(null, "num"));
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in long-array");
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in long-array");
        }
        object.next();
        object2 = new long[n];
        n = 0;
        int n2 = object.getEventType();
        do {
            int n3;
            if (n2 == 2) {
                if (!object.getName().equals("item")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Expected item tag at: ");
                    ((StringBuilder)charSequence).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                try {
                    object2[n] = Long.parseLong(object.getAttributeValue(null, "value"));
                    n3 = n;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
            } else {
                n3 = n;
                if (n2 == 3) {
                    if (object.getName().equals(charSequence)) {
                        return object2;
                    }
                    if (!object.getName().equals("item")) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected ");
                        ((StringBuilder)object2).append((String)charSequence);
                        ((StringBuilder)object2).append(" end tag at: ");
                        ((StringBuilder)object2).append(object.getName());
                        throw new XmlPullParserException(((StringBuilder)object2).toString());
                    }
                    n3 = n + 1;
                }
            }
            n2 = object.next();
            if (n2 == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Document ended before ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" end tag");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n = n3;
        } while (true);
    }

    public static final HashMap<String, ?> readThisMapXml(XmlPullParser xmlPullParser, String string2, String[] arrstring) throws XmlPullParserException, IOException {
        return XmlUtils.readThisMapXml(xmlPullParser, string2, arrstring, null);
    }

    public static final HashMap<String, ?> readThisMapXml(XmlPullParser object, String string2, String[] object2, ReadMapCallback readMapCallback) throws XmlPullParserException, IOException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        int n = object.getEventType();
        do {
            if (n == 2) {
                Object object3 = XmlUtils.readThisValueXml((XmlPullParser)object, (String[])object2, readMapCallback, false);
                hashMap.put(object2[0], object3);
            } else if (n == 3) {
                if (object.getName().equals(string2)) {
                    return hashMap;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Expected ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" end tag at: ");
                ((StringBuilder)object2).append(object.getName());
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            n = object.next();
        } while (n != 1);
        object = new StringBuilder();
        ((StringBuilder)object).append("Document ended before ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" end tag");
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static final Object readThisPrimitiveValueXml(XmlPullParser object, String string2) throws XmlPullParserException, IOException {
        void var1_6;
        try {
            boolean bl = var1_6.equals("int");
            if (bl) {
                return Integer.parseInt(object.getAttributeValue(null, "value"));
            }
            if (var1_6.equals("long")) {
                return Long.valueOf(object.getAttributeValue(null, "value"));
            }
            if (var1_6.equals("float")) {
                return new Float(object.getAttributeValue(null, "value"));
            }
            if (var1_6.equals("double")) {
                return new Double(object.getAttributeValue(null, "value"));
            }
            if (!var1_6.equals("boolean")) return null;
            return Boolean.valueOf(object.getAttributeValue(null, "value"));
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Not a number in value attribute in <");
            stringBuilder.append((String)var1_6);
            stringBuilder.append(">");
            throw new XmlPullParserException(stringBuilder.toString());
        }
        catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Need value attribute in <");
            stringBuilder.append((String)var1_6);
            stringBuilder.append(">");
            throw new XmlPullParserException(stringBuilder.toString());
        }
    }

    public static final HashSet readThisSetXml(XmlPullParser xmlPullParser, String string2, String[] arrstring) throws XmlPullParserException, IOException {
        return XmlUtils.readThisSetXml(xmlPullParser, string2, arrstring, null, false);
    }

    private static final HashSet readThisSetXml(XmlPullParser object, String string2, String[] object2, ReadMapCallback readMapCallback, boolean bl) throws XmlPullParserException, IOException {
        HashSet<Object> hashSet = new HashSet<Object>();
        int n = object.getEventType();
        do {
            if (n == 2) {
                hashSet.add(XmlUtils.readThisValueXml((XmlPullParser)object, (String[])object2, readMapCallback, bl));
            } else if (n == 3) {
                if (object.getName().equals(string2)) {
                    return hashSet;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Expected ");
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(" end tag at: ");
                ((StringBuilder)object2).append(object.getName());
                throw new XmlPullParserException(((StringBuilder)object2).toString());
            }
            n = object.next();
        } while (n != 1);
        object = new StringBuilder();
        ((StringBuilder)object).append("Document ended before ");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" end tag");
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final String[] readThisStringArrayXml(XmlPullParser object, String charSequence, String[] object2) throws XmlPullParserException, IOException {
        int n;
        try {
            n = Integer.parseInt(object.getAttributeValue(null, "num"));
        }
        catch (NumberFormatException numberFormatException) {
            throw new XmlPullParserException("Not a number in num attribute in string-array");
        }
        catch (NullPointerException nullPointerException) {
            throw new XmlPullParserException("Need num attribute in string-array");
        }
        object.next();
        object2 = new String[n];
        int n2 = 0;
        n = object.getEventType();
        do {
            int n3;
            if (n == 2) {
                if (!object.getName().equals("item")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Expected item tag at: ");
                    ((StringBuilder)charSequence).append(object.getName());
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString());
                }
                try {
                    object2[n2] = object.getAttributeValue(null, "value");
                    n3 = n2;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new XmlPullParserException("Not a number in value attribute in item");
                }
                catch (NullPointerException nullPointerException) {
                    throw new XmlPullParserException("Need value attribute in item");
                }
            } else {
                n3 = n2;
                if (n == 3) {
                    if (object.getName().equals(charSequence)) {
                        return object2;
                    }
                    if (!object.getName().equals("item")) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Expected ");
                        ((StringBuilder)object2).append((String)charSequence);
                        ((StringBuilder)object2).append(" end tag at: ");
                        ((StringBuilder)object2).append(object.getName());
                        throw new XmlPullParserException(((StringBuilder)object2).toString());
                    }
                    n3 = n2 + 1;
                }
            }
            n = object.next();
            if (n == 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Document ended before ");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" end tag");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n2 = n3;
        } while (true);
    }

    private static final Object readThisValueXml(XmlPullParser object, String[] arrstring, ReadMapCallback object2, boolean bl) throws XmlPullParserException, IOException {
        String string2;
        String string3;
        block24 : {
            int n;
            block23 : {
                block22 : {
                    string2 = object.getAttributeValue(null, "name");
                    string3 = object.getName();
                    if (!string3.equals("null")) break block22;
                    object2 = null;
                    break block23;
                }
                if (string3.equals("string")) {
                    int n2;
                    object2 = "";
                    while ((n2 = object.next()) != 1) {
                        if (n2 == 3) {
                            if (object.getName().equals("string")) {
                                arrstring[0] = string2;
                                return object2;
                            }
                            arrstring = new StringBuilder();
                            arrstring.append("Unexpected end tag in <string>: ");
                            arrstring.append(object.getName());
                            throw new XmlPullParserException(arrstring.toString());
                        }
                        if (n2 == 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append((String)object2);
                            stringBuilder.append(object.getText());
                            object2 = stringBuilder.toString();
                            continue;
                        }
                        if (n2 != 2) continue;
                        arrstring = new StringBuilder();
                        arrstring.append("Unexpected start tag in <string>: ");
                        arrstring.append(object.getName());
                        throw new XmlPullParserException(arrstring.toString());
                    }
                    throw new XmlPullParserException("Unexpected end of document in <string>");
                }
                Object object3 = XmlUtils.readThisPrimitiveValueXml((XmlPullParser)object, string3);
                if (object3 == null) break block24;
                object2 = object3;
            }
            while ((n = object.next()) != 1) {
                if (n == 3) {
                    if (object.getName().equals(string3)) {
                        arrstring[0] = string2;
                        return object2;
                    }
                    arrstring = new StringBuilder();
                    arrstring.append("Unexpected end tag in <");
                    arrstring.append(string3);
                    arrstring.append(">: ");
                    arrstring.append(object.getName());
                    throw new XmlPullParserException(arrstring.toString());
                }
                if (n != 4) {
                    if (n != 2) continue;
                    arrstring = new StringBuilder();
                    arrstring.append("Unexpected start tag in <");
                    arrstring.append(string3);
                    arrstring.append(">: ");
                    arrstring.append(object.getName());
                    throw new XmlPullParserException(arrstring.toString());
                }
                arrstring = new StringBuilder();
                arrstring.append("Unexpected text in <");
                arrstring.append(string3);
                arrstring.append(">: ");
                arrstring.append(object.getName());
                throw new XmlPullParserException(arrstring.toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected end of document in <");
            ((StringBuilder)object).append(string3);
            ((StringBuilder)object).append(">");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        if (string3.equals("byte-array")) {
            object = XmlUtils.readThisByteArrayXml((XmlPullParser)object, "byte-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("int-array")) {
            object = XmlUtils.readThisIntArrayXml((XmlPullParser)object, "int-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("long-array")) {
            object = XmlUtils.readThisLongArrayXml((XmlPullParser)object, "long-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("double-array")) {
            object = XmlUtils.readThisDoubleArrayXml((XmlPullParser)object, "double-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("string-array")) {
            object = XmlUtils.readThisStringArrayXml((XmlPullParser)object, "string-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("boolean-array")) {
            object = XmlUtils.readThisBooleanArrayXml((XmlPullParser)object, "boolean-array", arrstring);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("map")) {
            object.next();
            object = bl ? XmlUtils.readThisArrayMapXml((XmlPullParser)object, "map", arrstring, (ReadMapCallback)object2) : XmlUtils.readThisMapXml((XmlPullParser)object, "map", arrstring, (ReadMapCallback)object2);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("list")) {
            object.next();
            object = XmlUtils.readThisListXml((XmlPullParser)object, "list", arrstring, (ReadMapCallback)object2, bl);
            arrstring[0] = string2;
            return object;
        }
        if (string3.equals("set")) {
            object.next();
            object = XmlUtils.readThisSetXml((XmlPullParser)object, "set", arrstring, (ReadMapCallback)object2, bl);
            arrstring[0] = string2;
            return object;
        }
        if (object2 != null) {
            object = object2.readThisUnknownObjectXml((XmlPullParser)object, string3);
            arrstring[0] = string2;
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown tag: ");
        ((StringBuilder)object).append(string3);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    public static Uri readUriAttribute(XmlPullParser object, String string2) {
        Object var2_2 = null;
        string2 = object.getAttributeValue(null, string2);
        object = var2_2;
        if (string2 != null) {
            object = Uri.parse(string2);
        }
        return object;
    }

    public static final Object readValueXml(XmlPullParser xmlPullParser, String[] object) throws XmlPullParserException, IOException {
        block2 : {
            block3 : {
                int n = xmlPullParser.getEventType();
                do {
                    if (n == 2) {
                        return XmlUtils.readThisValueXml(xmlPullParser, (String[])object, null, false);
                    }
                    if (n == 3) break block2;
                    if (n == 4) break block3;
                } while ((n = xmlPullParser.next()) != 1);
                throw new XmlPullParserException("Unexpected end of document");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected text: ");
            ((StringBuilder)object).append(xmlPullParser.getText());
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unexpected end tag at: ");
        ((StringBuilder)object).append(xmlPullParser.getName());
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    public static void skipCurrentTag(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n;
        int n2 = xmlPullParser.getDepth();
        while ((n = xmlPullParser.next()) != 1 && (n != 3 || xmlPullParser.getDepth() > n2)) {
        }
    }

    @Deprecated
    public static void writeBitmapAttribute(XmlSerializer xmlSerializer, String string2, Bitmap bitmap) throws IOException {
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, byteArrayOutputStream);
            XmlUtils.writeByteArrayAttribute(xmlSerializer, string2, byteArrayOutputStream.toByteArray());
        }
    }

    public static final void writeBooleanArrayXml(boolean[] arrbl, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrbl == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "boolean-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = arrbl.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        for (int i = 0; i < n; ++i) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Boolean.toString(arrbl[i]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "boolean-array");
    }

    public static void writeBooleanAttribute(XmlSerializer xmlSerializer, String string2, boolean bl) throws IOException {
        xmlSerializer.attribute(null, string2, Boolean.toString(bl));
    }

    public static void writeByteArrayAttribute(XmlSerializer xmlSerializer, String string2, byte[] arrby) throws IOException {
        if (arrby != null) {
            xmlSerializer.attribute(null, string2, Base64.encodeToString(arrby, 0));
        }
    }

    public static final void writeByteArrayXml(byte[] arrby, String charSequence, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrby == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "byte-array");
        if (charSequence != null) {
            xmlSerializer.attribute(null, "name", (String)charSequence);
        }
        int n = arrby.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        charSequence = new StringBuilder(arrby.length * 2);
        for (int i = 0; i < n; ++i) {
            byte by = arrby[i];
            int n2 = by >> 4 & 15;
            n2 = n2 >= 10 ? n2 + 97 - 10 : (n2 += 48);
            ((StringBuilder)charSequence).append((char)n2);
            n2 = by & 15;
            n2 = n2 >= 10 ? n2 + 97 - 10 : (n2 += 48);
            ((StringBuilder)charSequence).append((char)n2);
        }
        xmlSerializer.text(((StringBuilder)charSequence).toString());
        xmlSerializer.endTag(null, "byte-array");
    }

    public static final void writeDoubleArrayXml(double[] arrd, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrd == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "double-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = arrd.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        for (int i = 0; i < n; ++i) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Double.toString(arrd[i]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "double-array");
    }

    public static void writeFloatAttribute(XmlSerializer xmlSerializer, String string2, float f) throws IOException {
        xmlSerializer.attribute(null, string2, Float.toString(f));
    }

    public static final void writeIntArrayXml(int[] arrn, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrn == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "int-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = arrn.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        for (int i = 0; i < n; ++i) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Integer.toString(arrn[i]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "int-array");
    }

    public static void writeIntAttribute(XmlSerializer xmlSerializer, String string2, int n) throws IOException {
        xmlSerializer.attribute(null, string2, Integer.toString(n));
    }

    public static final void writeListXml(List list, OutputStream outputStream) throws XmlPullParserException, IOException {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        xmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        xmlSerializer.startDocument(null, Boolean.valueOf(true));
        xmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        XmlUtils.writeListXml(list, null, xmlSerializer);
        xmlSerializer.endDocument();
    }

    public static final void writeListXml(List list, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (list == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "list");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            XmlUtils.writeValueXml(list.get(i), null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "list");
    }

    public static final void writeLongArrayXml(long[] arrl, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrl == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "long-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = arrl.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        for (int i = 0; i < n; ++i) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", Long.toString(arrl[i]));
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "long-array");
    }

    public static void writeLongAttribute(XmlSerializer xmlSerializer, String string2, long l) throws IOException {
        xmlSerializer.attribute(null, string2, Long.toString(l));
    }

    @UnsupportedAppUsage
    public static final void writeMapXml(Map map, OutputStream outputStream) throws XmlPullParserException, IOException {
        FastXmlSerializer fastXmlSerializer = new FastXmlSerializer();
        fastXmlSerializer.setOutput(outputStream, StandardCharsets.UTF_8.name());
        fastXmlSerializer.startDocument(null, Boolean.valueOf(true));
        fastXmlSerializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
        XmlUtils.writeMapXml(map, null, fastXmlSerializer);
        fastXmlSerializer.endDocument();
    }

    public static final void writeMapXml(Map map, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        XmlUtils.writeMapXml(map, string2, xmlSerializer, null);
    }

    public static final void writeMapXml(Map map, String string2, XmlSerializer xmlSerializer, WriteMapCallback writeMapCallback) throws XmlPullParserException, IOException {
        if (map == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "map");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        XmlUtils.writeMapXml(map, xmlSerializer, writeMapCallback);
        xmlSerializer.endTag(null, "map");
    }

    /*
     * WARNING - void declaration
     */
    public static final void writeMapXml(Map object2, XmlSerializer xmlSerializer, WriteMapCallback writeMapCallback) throws XmlPullParserException, IOException {
        if (object2 == null) {
            return;
        }
        for (Map.Entry entry : object2.entrySet()) {
            void var1_3;
            void var2_4;
            XmlUtils.writeValueXml(entry.getValue(), (String)entry.getKey(), (XmlSerializer)var1_3, (WriteMapCallback)var2_4);
        }
    }

    public static final void writeSetXml(Set object, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (object == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "set");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        object = object.iterator();
        while (object.hasNext()) {
            XmlUtils.writeValueXml(object.next(), null, xmlSerializer);
        }
        xmlSerializer.endTag(null, "set");
    }

    public static final void writeStringArrayXml(String[] arrstring, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        if (arrstring == null) {
            xmlSerializer.startTag(null, "null");
            xmlSerializer.endTag(null, "null");
            return;
        }
        xmlSerializer.startTag(null, "string-array");
        if (string2 != null) {
            xmlSerializer.attribute(null, "name", string2);
        }
        int n = arrstring.length;
        xmlSerializer.attribute(null, "num", Integer.toString(n));
        for (int i = 0; i < n; ++i) {
            xmlSerializer.startTag(null, "item");
            xmlSerializer.attribute(null, "value", arrstring[i]);
            xmlSerializer.endTag(null, "item");
        }
        xmlSerializer.endTag(null, "string-array");
    }

    public static void writeStringAttribute(XmlSerializer xmlSerializer, String string2, CharSequence charSequence) throws IOException {
        if (charSequence != null) {
            xmlSerializer.attribute(null, string2, charSequence.toString());
        }
    }

    public static void writeUriAttribute(XmlSerializer xmlSerializer, String string2, Uri uri) throws IOException {
        if (uri != null) {
            xmlSerializer.attribute(null, string2, uri.toString());
        }
    }

    public static final void writeValueXml(Object object, String string2, XmlSerializer xmlSerializer) throws XmlPullParserException, IOException {
        XmlUtils.writeValueXml(object, string2, xmlSerializer, null);
    }

    private static final void writeValueXml(Object object, String charSequence, XmlSerializer xmlSerializer, WriteMapCallback object2) throws XmlPullParserException, IOException {
        block25 : {
            block21 : {
                block24 : {
                    block23 : {
                        block22 : {
                            block20 : {
                                if (object == null) {
                                    xmlSerializer.startTag(null, "null");
                                    if (charSequence != null) {
                                        xmlSerializer.attribute(null, "name", (String)charSequence);
                                    }
                                    xmlSerializer.endTag(null, "null");
                                    return;
                                }
                                if (object instanceof String) {
                                    xmlSerializer.startTag(null, "string");
                                    if (charSequence != null) {
                                        xmlSerializer.attribute(null, "name", (String)charSequence);
                                    }
                                    xmlSerializer.text(object.toString());
                                    xmlSerializer.endTag(null, "string");
                                    return;
                                }
                                if (!(object instanceof Integer)) break block20;
                                object2 = "int";
                                break block21;
                            }
                            if (!(object instanceof Long)) break block22;
                            object2 = "long";
                            break block21;
                        }
                        if (!(object instanceof Float)) break block23;
                        object2 = "float";
                        break block21;
                    }
                    if (!(object instanceof Double)) break block24;
                    object2 = "double";
                    break block21;
                }
                if (!(object instanceof Boolean)) break block25;
                object2 = "boolean";
            }
            xmlSerializer.startTag(null, (String)object2);
            if (charSequence != null) {
                xmlSerializer.attribute(null, "name", (String)charSequence);
            }
            xmlSerializer.attribute(null, "value", object.toString());
            xmlSerializer.endTag(null, (String)object2);
            return;
        }
        if (object instanceof byte[]) {
            XmlUtils.writeByteArrayXml((byte[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof int[]) {
            XmlUtils.writeIntArrayXml((int[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof long[]) {
            XmlUtils.writeLongArrayXml((long[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof double[]) {
            XmlUtils.writeDoubleArrayXml((double[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof String[]) {
            XmlUtils.writeStringArrayXml((String[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof boolean[]) {
            XmlUtils.writeBooleanArrayXml((boolean[])object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof Map) {
            XmlUtils.writeMapXml((Map)object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof List) {
            XmlUtils.writeListXml((List)object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof Set) {
            XmlUtils.writeSetXml((Set)object, (String)charSequence, xmlSerializer);
            return;
        }
        if (object instanceof CharSequence) {
            xmlSerializer.startTag(null, "string");
            if (charSequence != null) {
                xmlSerializer.attribute(null, "name", (String)charSequence);
            }
            xmlSerializer.text(object.toString());
            xmlSerializer.endTag(null, "string");
            return;
        }
        if (object2 != null) {
            object2.writeUnknownObject(object, (String)charSequence, xmlSerializer);
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("writeValueXml: unable to write value ");
        ((StringBuilder)charSequence).append(object);
        throw new RuntimeException(((StringBuilder)charSequence).toString());
    }

    public static interface ReadMapCallback {
        public Object readThisUnknownObjectXml(XmlPullParser var1, String var2) throws XmlPullParserException, IOException;
    }

    public static interface WriteMapCallback {
        public void writeUnknownObject(Object var1, String var2, XmlSerializer var3) throws XmlPullParserException, IOException;
    }

}

