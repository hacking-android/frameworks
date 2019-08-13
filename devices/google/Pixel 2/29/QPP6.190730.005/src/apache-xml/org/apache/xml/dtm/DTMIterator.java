/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMManager;

public interface DTMIterator {
    public static final short FILTER_ACCEPT = 1;
    public static final short FILTER_REJECT = 2;
    public static final short FILTER_SKIP = 3;

    public void allowDetachToRelease(boolean var1);

    public Object clone() throws CloneNotSupportedException;

    public DTMIterator cloneWithReset() throws CloneNotSupportedException;

    public void detach();

    public int getAxis();

    public int getCurrentNode();

    public int getCurrentPos();

    public DTM getDTM(int var1);

    public DTMManager getDTMManager();

    public boolean getExpandEntityReferences();

    public int getLength();

    public int getRoot();

    public int getWhatToShow();

    public boolean isDocOrdered();

    public boolean isFresh();

    public boolean isMutable();

    public int item(int var1);

    public int nextNode();

    public int previousNode();

    public void reset();

    public void runTo(int var1);

    public void setCurrentPos(int var1);

    public void setItem(int var1, int var2);

    public void setRoot(int var1, Object var2);

    public void setShouldCacheNodes(boolean var1);
}

