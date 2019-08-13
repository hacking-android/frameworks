/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import dalvik.system.BaseDexClassLoader;
import java.io.File;

public class PathClassLoader
extends BaseDexClassLoader {
    public PathClassLoader(String string, ClassLoader classLoader) {
        super(string, null, null, classLoader);
    }

    public PathClassLoader(String string, String string2, ClassLoader classLoader) {
        super(string, null, string2, classLoader);
    }

    public PathClassLoader(String string, String string2, ClassLoader classLoader, ClassLoader[] arrclassLoader) {
        super(string, string2, classLoader, arrclassLoader);
    }
}

