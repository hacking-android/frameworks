/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializerFilter;

public interface LSSerializer {
    public DOMConfiguration getDomConfig();

    public LSSerializerFilter getFilter();

    public String getNewLine();

    public void setFilter(LSSerializerFilter var1);

    public void setNewLine(String var1);

    public boolean write(Node var1, LSOutput var2) throws LSException;

    public String writeToString(Node var1) throws DOMException, LSException;

    public boolean writeToURI(Node var1, String var2) throws LSException;
}

