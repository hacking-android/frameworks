/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.Node;

public interface Entity
extends Node {
    public String getInputEncoding();

    public String getNotationName();

    public String getPublicId();

    public String getSystemId();

    public String getXmlEncoding();

    public String getXmlVersion();
}

