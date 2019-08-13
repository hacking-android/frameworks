/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class NotDirectoryException
extends FileSystemException {
    private static final long serialVersionUID = -9011457427178200199L;

    public NotDirectoryException(String string) {
        super(string);
    }
}

