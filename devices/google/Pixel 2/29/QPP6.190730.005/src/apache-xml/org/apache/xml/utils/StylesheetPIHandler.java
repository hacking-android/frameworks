/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import org.apache.xml.utils.StopParseException;
import org.apache.xml.utils.SystemIDResolver;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class StylesheetPIHandler
extends DefaultHandler {
    String m_baseID;
    String m_charset;
    String m_media;
    Vector m_stylesheets = new Vector();
    String m_title;
    URIResolver m_uriResolver;

    public StylesheetPIHandler(String string, String string2, String string3, String string4) {
        this.m_baseID = string;
        this.m_media = string2;
        this.m_title = string3;
        this.m_charset = string4;
    }

    public Source getAssociatedStylesheet() {
        int n = this.m_stylesheets.size();
        if (n > 0) {
            return (Source)this.m_stylesheets.elementAt(n - 1);
        }
        return null;
    }

    public String getBaseId() {
        return this.m_baseID;
    }

    public URIResolver getURIResolver() {
        return this.m_uriResolver;
    }

    @Override
    public void processingInstruction(String object, String object2) throws SAXException {
        block33 : {
            if (!((String)object).equals("xml-stylesheet")) break block33;
            Object object3 = null;
            String string = null;
            String string2 = null;
            String string3 = null;
            String string4 = null;
            boolean bl = false;
            StringTokenizer stringTokenizer = new StringTokenizer((String)object2, " \t=\n", true);
            boolean bl2 = false;
            object2 = null;
            object = "";
            while (stringTokenizer.hasMoreTokens()) {
                if (!bl2) {
                    object = stringTokenizer.nextToken();
                } else {
                    bl2 = false;
                }
                if (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) continue;
                if (((String)object).equals("type")) {
                    object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                        object = stringTokenizer.nextToken();
                    }
                    string = ((String)object).substring(1, ((String)object).length() - 1);
                    continue;
                }
                if (((String)object).equals("href")) {
                    Object object4;
                    object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                        object = stringTokenizer.nextToken();
                    }
                    object2 = object4 = object;
                    boolean bl3 = bl2;
                    if (stringTokenizer.hasMoreTokens()) {
                        object3 = stringTokenizer.nextToken();
                        do {
                            object2 = object4;
                            object = object3;
                            bl3 = bl2;
                            if (!((String)object3).equals("=")) break;
                            object2 = object4;
                            object = object3;
                            bl3 = bl2;
                            if (!stringTokenizer.hasMoreTokens()) break;
                            object = new StringBuilder();
                            ((StringBuilder)object).append((String)object4);
                            ((StringBuilder)object).append((String)object3);
                            ((StringBuilder)object).append(stringTokenizer.nextToken());
                            object2 = object4 = ((StringBuilder)object).toString();
                            object = object3;
                            bl3 = bl2;
                            if (!stringTokenizer.hasMoreTokens()) break;
                            object3 = stringTokenizer.nextToken();
                            bl2 = true;
                        } while (true);
                    }
                    object2 = ((String)object2).substring(1, ((String)object2).length() - 1);
                    try {
                        if (this.m_uriResolver != null) {
                            object3 = this.m_uriResolver.resolve((String)object2, this.m_baseID);
                        } else {
                            object2 = SystemIDResolver.getAbsoluteURI((String)object2, this.m_baseID);
                            object3 = new InputSource((String)object2);
                            object3 = new SAXSource((InputSource)object3);
                        }
                        object4 = object3;
                        object3 = object2;
                        bl2 = bl3;
                        object2 = object4;
                        continue;
                    }
                    catch (TransformerException transformerException) {
                        throw new SAXException(transformerException);
                    }
                }
                if (((String)object).equals("title")) {
                    object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                        object = stringTokenizer.nextToken();
                    }
                    string2 = ((String)object).substring(1, ((String)object).length() - 1);
                    continue;
                }
                if (((String)object).equals("media")) {
                    object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                        object = stringTokenizer.nextToken();
                    }
                    string3 = ((String)object).substring(1, ((String)object).length() - 1);
                    continue;
                }
                if (((String)object).equals("charset")) {
                    object = stringTokenizer.nextToken();
                    while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                        object = stringTokenizer.nextToken();
                    }
                    string4 = ((String)object).substring(1, ((String)object).length() - 1);
                    continue;
                }
                if (!((String)object).equals("alternate")) continue;
                object = stringTokenizer.nextToken();
                while (stringTokenizer.hasMoreTokens() && (((String)object).equals(" ") || ((String)object).equals("\t") || ((String)object).equals("="))) {
                    object = stringTokenizer.nextToken();
                }
                bl = ((String)object).substring(1, ((String)object).length() - 1).equals("yes");
            }
            if (string != null && (string.equals("text/xsl") || string.equals("text/xml") || string.equals("application/xml+xslt")) && object3 != null) {
                object = this.m_media;
                if (object != null) {
                    if (string3 != null) {
                        if (!string3.equals(object)) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if ((object = this.m_charset) != null) {
                    if (string4 != null) {
                        if (!string4.equals(object)) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if ((object = this.m_title) != null) {
                    if (string2 != null) {
                        if (!string2.equals(object)) {
                            return;
                        }
                    } else {
                        return;
                    }
                }
                this.m_stylesheets.addElement(object2);
            }
        }
    }

    public void setBaseId(String string) {
        this.m_baseID = string;
    }

    public void setURIResolver(URIResolver uRIResolver) {
        this.m_uriResolver = uRIResolver;
    }

    @Override
    public void startElement(String string, String string2, String string3, Attributes attributes) throws SAXException {
        throw new StopParseException();
    }
}

