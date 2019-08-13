/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom.ls;

import org.w3c.dom.DOMException;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSParser;
import org.w3c.dom.ls.LSSerializer;

public interface DOMImplementationLS {
    public static final short MODE_ASYNCHRONOUS = 2;
    public static final short MODE_SYNCHRONOUS = 1;

    public LSInput createLSInput();

    public LSOutput createLSOutput();

    public LSParser createLSParser(short var1, String var2) throws DOMException;

    public LSSerializer createLSSerializer();
}

