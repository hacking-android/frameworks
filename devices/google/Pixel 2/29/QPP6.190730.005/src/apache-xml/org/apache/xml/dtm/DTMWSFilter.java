/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import org.apache.xml.dtm.DTM;

public interface DTMWSFilter {
    public static final short INHERIT = 3;
    public static final short NOTSTRIP = 1;
    public static final short STRIP = 2;

    public short getShouldStripSpace(int var1, DTM var2);
}

