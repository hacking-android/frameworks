/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public interface DocumentType
extends Node {
    public NamedNodeMap getEntities();

    public String getInternalSubset();

    public String getName();

    public NamedNodeMap getNotations();

    public String getPublicId();

    public String getSystemId();
}

