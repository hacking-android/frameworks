/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

public interface DTMAxisIterator
extends Cloneable {
    public static final int END = -1;

    public DTMAxisIterator cloneIterator();

    public int getLast();

    public int getNodeByPosition(int var1);

    public int getPosition();

    public int getStartNode();

    public void gotoMark();

    public boolean isReverse();

    public int next();

    public DTMAxisIterator reset();

    public void setMark();

    public void setRestartable(boolean var1);

    public DTMAxisIterator setStartNode(int var1);
}

