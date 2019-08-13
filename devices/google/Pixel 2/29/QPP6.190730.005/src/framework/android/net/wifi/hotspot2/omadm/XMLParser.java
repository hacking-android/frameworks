/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.hotspot2.omadm;

import android.net.wifi.hotspot2.omadm.XMLNode;
import android.text.TextUtils;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParser
extends DefaultHandler {
    private XMLNode mCurrent = null;
    private XMLNode mRoot = null;

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
        this.mCurrent.addText(new String(arrc, n, n2));
    }

    @Override
    public void endElement(String charSequence, String string2, String string3) throws SAXException {
        if (string3.equals(this.mCurrent.getTag())) {
            this.mCurrent.close();
            this.mCurrent = this.mCurrent.getParent();
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("End tag '");
        ((StringBuilder)charSequence).append(string3);
        ((StringBuilder)charSequence).append("' doesn't match current node: ");
        ((StringBuilder)charSequence).append(this.mCurrent);
        throw new SAXException(((StringBuilder)charSequence).toString());
    }

    public XMLNode parse(String object) throws IOException, SAXException {
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.mRoot = null;
            this.mCurrent = null;
            try {
                SAXParser sAXParser = SAXParserFactory.newInstance().newSAXParser();
                StringReader stringReader = new StringReader((String)object);
                InputSource inputSource = new InputSource(stringReader);
                sAXParser.parse(inputSource, (DefaultHandler)this);
                object = this.mRoot;
                return object;
            }
            catch (ParserConfigurationException parserConfigurationException) {
                throw new SAXException(parserConfigurationException);
            }
        }
        throw new IOException("XML string not provided");
    }

    @Override
    public void startElement(String object, String string2, String string3, Attributes attributes) throws SAXException {
        block4 : {
            block3 : {
                block2 : {
                    object = this.mCurrent;
                    this.mCurrent = new XMLNode((XMLNode)object, string3);
                    if (this.mRoot != null) break block2;
                    this.mRoot = this.mCurrent;
                    break block3;
                }
                if (object == null) break block4;
                ((XMLNode)object).addChild(this.mCurrent);
            }
            return;
        }
        throw new SAXException("More than one root nodes");
    }
}

