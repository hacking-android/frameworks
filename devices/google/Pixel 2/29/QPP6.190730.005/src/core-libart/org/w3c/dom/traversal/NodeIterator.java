/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.traversal;

import dalvik.annotation.compat.UnsupportedAppUsage;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;

public interface NodeIterator {
    @UnsupportedAppUsage
    public void detach();

    public boolean getExpandEntityReferences();

    public NodeFilter getFilter();

    public Node getRoot();

    public int getWhatToShow();

    @UnsupportedAppUsage
    public Node nextNode() throws DOMException;

    public Node previousNode() throws DOMException;
}

