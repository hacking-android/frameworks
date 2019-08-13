/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import org.xml.sax.Attributes;

public interface SerializerTrace {
    public static final int EVENTTYPE_CDATA = 10;
    public static final int EVENTTYPE_CHARACTERS = 5;
    public static final int EVENTTYPE_COMMENT = 8;
    public static final int EVENTTYPE_ENDDOCUMENT = 2;
    public static final int EVENTTYPE_ENDELEMENT = 4;
    public static final int EVENTTYPE_ENTITYREF = 9;
    public static final int EVENTTYPE_IGNORABLEWHITESPACE = 6;
    public static final int EVENTTYPE_OUTPUT_CHARACTERS = 12;
    public static final int EVENTTYPE_OUTPUT_PSEUDO_CHARACTERS = 11;
    public static final int EVENTTYPE_PI = 7;
    public static final int EVENTTYPE_STARTDOCUMENT = 1;
    public static final int EVENTTYPE_STARTELEMENT = 3;

    public void fireGenerateEvent(int var1);

    public void fireGenerateEvent(int var1, String var2);

    public void fireGenerateEvent(int var1, String var2, String var3);

    public void fireGenerateEvent(int var1, String var2, Attributes var3);

    public void fireGenerateEvent(int var1, char[] var2, int var3, int var4);

    public boolean hasTraceListeners();
}

