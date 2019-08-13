/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package android.content.res;

import android.util.AttributeSet;
import org.xmlpull.v1.XmlPullParser;

public interface XmlResourceParser
extends XmlPullParser,
AttributeSet,
AutoCloseable {
    @Override
    public void close();

    @Override
    public String getAttributeNamespace(int var1);
}

