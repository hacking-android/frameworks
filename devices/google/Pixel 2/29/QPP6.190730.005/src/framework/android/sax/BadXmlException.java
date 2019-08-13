/*
 * Decompiled with CFR 0.145.
 */
package android.sax;

import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

class BadXmlException
extends SAXParseException {
    public BadXmlException(String string2, Locator locator) {
        super(string2, locator);
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Line ");
        stringBuilder.append(this.getLineNumber());
        stringBuilder.append(": ");
        stringBuilder.append(super.getMessage());
        return stringBuilder.toString();
    }
}

