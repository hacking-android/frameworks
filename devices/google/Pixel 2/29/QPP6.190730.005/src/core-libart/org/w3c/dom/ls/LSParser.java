/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParserFilter;

public interface LSParser {
    public static final short ACTION_APPEND_AS_CHILDREN = 1;
    public static final short ACTION_INSERT_AFTER = 4;
    public static final short ACTION_INSERT_BEFORE = 3;
    public static final short ACTION_REPLACE = 5;
    public static final short ACTION_REPLACE_CHILDREN = 2;

    public void abort();

    public boolean getAsync();

    public boolean getBusy();

    public DOMConfiguration getDomConfig();

    public LSParserFilter getFilter();

    public Document parse(LSInput var1) throws DOMException, LSException;

    public Document parseURI(String var1) throws DOMException, LSException;

    public Node parseWithContext(LSInput var1, Node var2, short var3) throws DOMException, LSException;

    public void setFilter(LSParserFilter var1);
}

