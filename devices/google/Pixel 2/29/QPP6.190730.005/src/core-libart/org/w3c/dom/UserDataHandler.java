/*
 * Decompiled with CFR 0.145.
 */
package org.w3c.dom;

import org.w3c.dom.Node;

public interface UserDataHandler {
    public static final short NODE_ADOPTED = 5;
    public static final short NODE_CLONED = 1;
    public static final short NODE_DELETED = 3;
    public static final short NODE_IMPORTED = 2;
    public static final short NODE_RENAMED = 4;

    public void handle(short var1, String var2, Object var3, Node var4, Node var5);
}

