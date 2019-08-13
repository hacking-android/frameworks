/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class DirectoryNotEmptyException
extends FileSystemException {
    static final long serialVersionUID = 3056667871802779003L;

    public DirectoryNotEmptyException(String string) {
        super(string);
    }
}

