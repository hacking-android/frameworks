/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.content.pm;

import android.annotation.UnsupportedAppUsage;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public interface XmlSerializerAndParser<T> {
    @UnsupportedAppUsage
    public T createFromXml(XmlPullParser var1) throws IOException, XmlPullParserException;

    @UnsupportedAppUsage
    public void writeAsXml(T var1, XmlSerializer var2) throws IOException;
}

