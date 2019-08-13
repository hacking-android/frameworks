/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.Path;

@FunctionalInterface
public interface PathMatcher {
    public boolean matches(Path var1);
}

