/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class NoSuchFileException
extends FileSystemException {
    static final long serialVersionUID = -1390291775875351931L;

    public NoSuchFileException(String string) {
        super(string);
    }

    public NoSuchFileException(String string, String string2, String string3) {
        super(string, string2, string3);
    }
}

