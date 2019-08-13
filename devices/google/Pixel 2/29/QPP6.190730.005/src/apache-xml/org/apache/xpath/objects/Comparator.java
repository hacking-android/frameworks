/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.utils.XMLString;

abstract class Comparator {
    Comparator() {
    }

    abstract boolean compareNumbers(double var1, double var3);

    abstract boolean compareStrings(XMLString var1, XMLString var2);
}

