/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import org.apache.xml.utils.XMLString;
import org.apache.xpath.objects.Comparator;

class LessThanOrEqualComparator
extends Comparator {
    LessThanOrEqualComparator() {
    }

    @Override
    boolean compareNumbers(double d, double d2) {
        boolean bl = d <= d2;
        return bl;
    }

    @Override
    boolean compareStrings(XMLString xMLString, XMLString xMLString2) {
        boolean bl = xMLString.toDouble() <= xMLString2.toDouble();
        return bl;
    }
}

