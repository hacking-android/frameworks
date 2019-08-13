/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public interface FileVisitor<T> {
    public FileVisitResult postVisitDirectory(T var1, IOException var2) throws IOException;

    public FileVisitResult preVisitDirectory(T var1, BasicFileAttributes var2) throws IOException;

    public FileVisitResult visitFile(T var1, BasicFileAttributes var2) throws IOException;

    public FileVisitResult visitFileFailed(T var1, IOException var2) throws IOException;
}

