/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import javax.xml.transform.SourceLocator;

public interface ExpressionNode
extends SourceLocator {
    public void exprAddChild(ExpressionNode var1, int var2);

    public ExpressionNode exprGetChild(int var1);

    public int exprGetNumChildren();

    public ExpressionNode exprGetParent();

    public void exprSetParent(ExpressionNode var1);
}

