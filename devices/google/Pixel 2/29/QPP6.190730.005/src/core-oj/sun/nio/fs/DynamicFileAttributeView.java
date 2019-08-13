/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.util.Map;

interface DynamicFileAttributeView {
    public Map<String, Object> readAttributes(String[] var1) throws IOException;

    public void setAttribute(String var1, Object var2) throws IOException;
}

