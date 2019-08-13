/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

public interface Text
extends CharacterData {
    public String getWholeText();

    public boolean isElementContentWhitespace();

    public Text replaceWholeText(String var1) throws DOMException;

    public Text splitText(int var1) throws DOMException;
}

