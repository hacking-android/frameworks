/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.content.ContentInsertHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.util.Xml;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class DefaultDataHandler
implements ContentInsertHandler {
    private static final String ARG = "arg";
    private static final String COL = "col";
    private static final String DEL = "del";
    private static final String POSTFIX = "postfix";
    private static final String ROW = "row";
    private static final String SELECT = "select";
    private static final String URI_STR = "uri";
    private ContentResolver mContentResolver;
    private Stack<Uri> mUris = new Stack();
    private ContentValues mValues;

    private Uri insertRow() {
        Uri uri = this.mContentResolver.insert((Uri)this.mUris.lastElement(), this.mValues);
        this.mValues = null;
        return uri;
    }

    private void parseRow(Attributes object) throws SAXException {
        block5 : {
            block4 : {
                block2 : {
                    Object object2;
                    block3 : {
                        object2 = object.getValue(URI_STR);
                        if (object2 == null) break block2;
                        if ((object2 = Uri.parse((String)object2)) == null) break block3;
                        object = object2;
                        break block4;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("attribute ");
                    ((StringBuilder)object2).append(object.getValue(URI_STR));
                    ((StringBuilder)object2).append(" parsing failure");
                    throw new SAXException(((StringBuilder)object2).toString());
                }
                if (this.mUris.size() <= 0) break block5;
                object = (object = object.getValue(POSTFIX)) != null ? Uri.withAppendedPath((Uri)this.mUris.lastElement(), (String)object) : (Uri)this.mUris.lastElement();
            }
            this.mUris.push((Uri)object);
            return;
        }
        throw new SAXException("attribute parsing failure");
    }

    @Override
    public void characters(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void endElement(String string2, String string3, String string4) throws SAXException {
        if (ROW.equals(string3)) {
            if (!this.mUris.empty()) {
                if (this.mValues != null) {
                    this.insertRow();
                }
                this.mUris.pop();
            } else {
                throw new SAXException("uri mismatch");
            }
        }
    }

    @Override
    public void endPrefixMapping(String string2) throws SAXException {
    }

    @Override
    public void ignorableWhitespace(char[] arrc, int n, int n2) throws SAXException {
    }

    @Override
    public void insert(ContentResolver contentResolver, InputStream inputStream) throws IOException, SAXException {
        this.mContentResolver = contentResolver;
        Xml.parse(inputStream, Xml.Encoding.UTF_8, this);
    }

    @Override
    public void insert(ContentResolver contentResolver, String string2) throws SAXException {
        this.mContentResolver = contentResolver;
        Xml.parse(string2, (ContentHandler)this);
    }

    @Override
    public void processingInstruction(String string2, String string3) throws SAXException {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void skippedEntity(String string2) throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public void startElement(String object, String object2, String string2, Attributes attributes) throws SAXException {
        void var4_12;
        Object object3;
        if (ROW.equals(object3)) {
            if (this.mValues != null) {
                if (this.mUris.empty()) throw new SAXException("uri is empty");
                Uri uri = this.insertRow();
                if (uri != null) {
                    this.mUris.pop();
                    this.mUris.push(uri);
                    this.parseRow((Attributes)var4_12);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("insert to uri ");
                stringBuilder.append(((Uri)this.mUris.lastElement()).toString());
                stringBuilder.append(" failure");
                throw new SAXException(stringBuilder.toString());
            }
            if (var4_12.getLength() == 0) {
                Stack<Uri> stack = this.mUris;
                stack.push((Uri)stack.lastElement());
                return;
            }
            this.parseRow((Attributes)var4_12);
            return;
        }
        if (COL.equals(object3)) {
            int n = var4_12.getLength();
            if (n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("illegal attributes number ");
                stringBuilder.append(n);
                throw new SAXException(stringBuilder.toString());
            }
            String string3 = var4_12.getValue(0);
            object3 = var4_12.getValue(1);
            if (string3 == null) throw new SAXException("illegal attributes value");
            if (string3.length() <= 0) throw new SAXException("illegal attributes value");
            if (object3 == null) throw new SAXException("illegal attributes value");
            if (((String)object3).length() <= 0) throw new SAXException("illegal attributes value");
            if (this.mValues == null) {
                this.mValues = new ContentValues();
            }
            this.mValues.put(string3, (String)object3);
            return;
        }
        if (!DEL.equals(object3)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown element: ");
            stringBuilder.append((String)object3);
            throw new SAXException(stringBuilder.toString());
        }
        object3 = Uri.parse(var4_12.getValue(URI_STR));
        if (object3 == null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("attribute ");
            stringBuilder.append(var4_12.getValue(URI_STR));
            stringBuilder.append(" parsing failure");
            throw new SAXException(stringBuilder.toString());
        }
        int n = var4_12.getLength() - 2;
        if (n > 0) {
            String[] arrstring = new String[n];
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mContentResolver.delete((Uri)object3, var4_12.getValue(1), arrstring);
                    return;
                }
                arrstring[n2] = var4_12.getValue(n2 + 2);
                ++n2;
            } while (true);
        }
        if (n == 0) {
            this.mContentResolver.delete((Uri)object3, var4_12.getValue(1), null);
            return;
        }
        this.mContentResolver.delete((Uri)object3, null, null);
    }

    @Override
    public void startPrefixMapping(String string2, String string3) throws SAXException {
    }
}

