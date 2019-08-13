/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package android.util;

import android.annotation.UnsupportedAppUsage;
import android.util.AttributeSet;
import com.android.internal.util.XmlUtils;
import org.xmlpull.v1.XmlPullParser;

class XmlPullAttributes
implements AttributeSet {
    @UnsupportedAppUsage
    XmlPullParser mParser;

    @UnsupportedAppUsage
    public XmlPullAttributes(XmlPullParser xmlPullParser) {
        this.mParser = xmlPullParser;
    }

    @Override
    public boolean getAttributeBooleanValue(int n, boolean bl) {
        return XmlUtils.convertValueToBoolean(this.getAttributeValue(n), bl);
    }

    @Override
    public boolean getAttributeBooleanValue(String string2, String string3, boolean bl) {
        return XmlUtils.convertValueToBoolean(this.getAttributeValue(string2, string3), bl);
    }

    @Override
    public int getAttributeCount() {
        return this.mParser.getAttributeCount();
    }

    @Override
    public float getAttributeFloatValue(int n, float f) {
        String string2 = this.getAttributeValue(n);
        if (string2 != null) {
            return Float.parseFloat(string2);
        }
        return f;
    }

    @Override
    public float getAttributeFloatValue(String string2, String string3, float f) {
        if ((string2 = this.getAttributeValue(string2, string3)) != null) {
            return Float.parseFloat(string2);
        }
        return f;
    }

    @Override
    public int getAttributeIntValue(int n, int n2) {
        return XmlUtils.convertValueToInt(this.getAttributeValue(n), n2);
    }

    @Override
    public int getAttributeIntValue(String string2, String string3, int n) {
        return XmlUtils.convertValueToInt(this.getAttributeValue(string2, string3), n);
    }

    @Override
    public int getAttributeListValue(int n, String[] arrstring, int n2) {
        return XmlUtils.convertValueToList(this.getAttributeValue(n), arrstring, n2);
    }

    @Override
    public int getAttributeListValue(String string2, String string3, String[] arrstring, int n) {
        return XmlUtils.convertValueToList(this.getAttributeValue(string2, string3), arrstring, n);
    }

    @Override
    public String getAttributeName(int n) {
        return this.mParser.getAttributeName(n);
    }

    @Override
    public int getAttributeNameResource(int n) {
        return 0;
    }

    @Override
    public String getAttributeNamespace(int n) {
        return this.mParser.getAttributeNamespace(n);
    }

    @Override
    public int getAttributeResourceValue(int n, int n2) {
        return XmlUtils.convertValueToInt(this.getAttributeValue(n), n2);
    }

    @Override
    public int getAttributeResourceValue(String string2, String string3, int n) {
        return XmlUtils.convertValueToInt(this.getAttributeValue(string2, string3), n);
    }

    @Override
    public int getAttributeUnsignedIntValue(int n, int n2) {
        return XmlUtils.convertValueToUnsignedInt(this.getAttributeValue(n), n2);
    }

    @Override
    public int getAttributeUnsignedIntValue(String string2, String string3, int n) {
        return XmlUtils.convertValueToUnsignedInt(this.getAttributeValue(string2, string3), n);
    }

    @Override
    public String getAttributeValue(int n) {
        return this.mParser.getAttributeValue(n);
    }

    @Override
    public String getAttributeValue(String string2, String string3) {
        return this.mParser.getAttributeValue(string2, string3);
    }

    @Override
    public String getClassAttribute() {
        return this.getAttributeValue(null, "class");
    }

    @Override
    public String getIdAttribute() {
        return this.getAttributeValue(null, "id");
    }

    @Override
    public int getIdAttributeResourceValue(int n) {
        return this.getAttributeResourceValue(null, "id", n);
    }

    @Override
    public String getPositionDescription() {
        return this.mParser.getPositionDescription();
    }

    @Override
    public int getStyleAttribute() {
        return this.getAttributeResourceValue(null, "style", 0);
    }
}

