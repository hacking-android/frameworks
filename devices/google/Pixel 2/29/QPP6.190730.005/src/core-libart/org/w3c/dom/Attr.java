/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.TypeInfo;

public interface Attr
extends Node {
    public String getName();

    public Element getOwnerElement();

    public TypeInfo getSchemaTypeInfo();

    public boolean getSpecified();

    public String getValue();

    public boolean isId();

    public void setValue(String var1) throws DOMException;
}

