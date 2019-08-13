/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.File;

@FunctionalInterface
public interface FileFilter {
    public boolean accept(File var1);
}

