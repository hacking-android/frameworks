/*
 * Decompiled with CFR 0.145.
 */
package javax.xml.xpath;

import javax.xml.namespace.QName;

public class XPathConstants {
    public static final QName BOOLEAN;
    public static final String DOM_OBJECT_MODEL = "http://java.sun.com/jaxp/xpath/dom";
    public static final QName NODE;
    public static final QName NODESET;
    public static final QName NUMBER;
    public static final QName STRING;

    static {
        NUMBER = new QName("http://www.w3.org/1999/XSL/Transform", "NUMBER");
        STRING = new QName("http://www.w3.org/1999/XSL/Transform", "STRING");
        BOOLEAN = new QName("http://www.w3.org/1999/XSL/Transform", "BOOLEAN");
        NODESET = new QName("http://www.w3.org/1999/XSL/Transform", "NODESET");
        NODE = new QName("http://www.w3.org/1999/XSL/Transform", "NODE");
    }

    private XPathConstants() {
    }
}

