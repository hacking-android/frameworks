/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.utils.XMLString;
import org.apache.xpath.objects.Comparator;

class EqualComparator
extends Comparator {
    EqualComparator() {
    }

    @Override
    boolean compareNumbers(double d, double d2) {
        boolean bl = d == d2;
        return bl;
    }

    @Override
    boolean compareStrings(XMLString xMLString, XMLString xMLString2) {
        return xMLString.equals(xMLString2);
    }
}

