/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xpath.Expression;

public interface ExpressionOwner {
    public Expression getExpression();

    public void setExpression(Expression var1);
}

