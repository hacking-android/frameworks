/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeIterator;

public interface ContextNodeList {
    public Object clone() throws CloneNotSupportedException;

    public NodeIterator cloneWithReset() throws CloneNotSupportedException;

    public Node getCurrentNode();

    public int getCurrentPos();

    public int getLast();

    public boolean isFresh();

    public void reset();

    public void runTo(int var1);

    public void setCurrentPos(int var1);

    public void setLast(int var1);

    public void setShouldCacheNodes(boolean var1);

    public int size();
}

