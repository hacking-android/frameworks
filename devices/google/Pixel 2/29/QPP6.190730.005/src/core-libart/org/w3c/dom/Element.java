/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.TypeInfo;

public interface Element
extends Node {
    public String getAttribute(String var1);

    public String getAttributeNS(String var1, String var2) throws DOMException;

    public Attr getAttributeNode(String var1);

    public Attr getAttributeNodeNS(String var1, String var2) throws DOMException;

    public NodeList getElementsByTagName(String var1);

    public NodeList getElementsByTagNameNS(String var1, String var2) throws DOMException;

    public TypeInfo getSchemaTypeInfo();

    public String getTagName();

    public boolean hasAttribute(String var1);

    public boolean hasAttributeNS(String var1, String var2) throws DOMException;

    public void removeAttribute(String var1) throws DOMException;

    public void removeAttributeNS(String var1, String var2) throws DOMException;

    public Attr removeAttributeNode(Attr var1) throws DOMException;

    public void setAttribute(String var1, String var2) throws DOMException;

    public void setAttributeNS(String var1, String var2, String var3) throws DOMException;

    public Attr setAttributeNode(Attr var1) throws DOMException;

    public Attr setAttributeNodeNS(Attr var1) throws DOMException;

    public void setIdAttribute(String var1, boolean var2) throws DOMException;

    public void setIdAttributeNS(String var1, String var2, boolean var3) throws DOMException;

    public void setIdAttributeNode(Attr var1, boolean var2) throws DOMException;
}

