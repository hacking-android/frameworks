/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public interface ProcessingInstruction
extends Node {
    public String getData();

    public String getTarget();

    public void setData(String var1) throws DOMException;
}

