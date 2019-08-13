/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.util.EventListener;
import java.util.prefs.NodeChangeEvent;

public interface NodeChangeListener
extends EventListener {
    public void childAdded(NodeChangeEvent var1);

    public void childRemoved(NodeChangeEvent var1);
}

