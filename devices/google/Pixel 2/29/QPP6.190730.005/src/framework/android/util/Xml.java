/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.XmlObjectFactory
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlSerializer
 */
package android.util;

import android.util.AttributeSet;
import android.util.XmlPullAttributes;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import libcore.util.XmlObjectFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Xml {
    public static String FEATURE_RELAXED = "http://xmlpull.org/v1/doc/features.html#relaxed";

    private Xml() {
    }

    public static AttributeSet asAttributeSet(XmlPullParser object) {
        object = object instanceof AttributeSet ? (AttributeSet)object : new XmlPullAttributes((XmlPullParser)object);
        return object;
    }

    public static Encoding findEncodingByName(String string2) throws UnsupportedEncodingException {
        if (string2 == null) {
            return Encoding.UTF_8;
        }
        for (Encoding encoding : Encoding.values()) {
            if (!encoding.expatName.equalsIgnoreCase(string2)) continue;
            return encoding;
        }
        throw new UnsupportedEncodingException(string2);
    }

    public static XmlPullParser newPullParser() {
        try {
            XmlPullParser xmlPullParser = XmlObjectFactory.newXmlPullParser();
            xmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-docdecl", true);
            xmlPullParser.setFeature("http://xmlpull.org/v1/doc/features.html#process-namespaces", true);
            return xmlPullParser;
        }
        catch (XmlPullParserException xmlPullParserException) {
            throw new AssertionError();
        }
    }

    public static XmlSerializer newSerializer() {
        return XmlObjectFactory.newXmlSerializer();
    }

    public static void parse(InputStream object, Encoding encoding, ContentHandler contentHandler) throws IOException, SAXException {
        XMLReader xMLReader = XmlObjectFactory.newXMLReader();
        xMLReader.setContentHandler(contentHandler);
        object = new InputSource((InputStream)object);
        ((InputSource)object).setEncoding(encoding.expatName);
        xMLReader.parse((InputSource)object);
    }

    public static void parse(Reader reader, ContentHandler contentHandler) throws IOException, SAXException {
        XMLReader xMLReader = XmlObjectFactory.newXMLReader();
        xMLReader.setContentHandler(contentHandler);
        xMLReader.parse(new InputSource(reader));
    }

    public static void parse(String string2, ContentHandler object) throws SAXException {
        try {
            XMLReader xMLReader = XmlObjectFactory.newXMLReader();
            xMLReader.setContentHandler((ContentHandler)object);
            object = new StringReader(string2);
            InputSource inputSource = new InputSource((Reader)object);
            xMLReader.parse(inputSource);
            return;
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    public static enum Encoding {
        US_ASCII("US-ASCII"),
        UTF_8("UTF-8"),
        UTF_16("UTF-16"),
        ISO_8859_1("ISO-8859-1");
        
        final String expatName;

        private Encoding(String string3) {
            this.expatName = string3;
        }
    }

}

