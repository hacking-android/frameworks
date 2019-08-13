/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathVisitor;

public interface XPathVisitable {
    public void callVisitors(ExpressionOwner var1, XPathVisitor var2);
}

