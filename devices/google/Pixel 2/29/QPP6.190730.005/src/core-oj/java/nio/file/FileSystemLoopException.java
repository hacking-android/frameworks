/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class FileSystemLoopException
extends FileSystemException {
    private static final long serialVersionUID = 4843039591949217617L;

    public FileSystemLoopException(String string) {
        super(string);
    }
}

