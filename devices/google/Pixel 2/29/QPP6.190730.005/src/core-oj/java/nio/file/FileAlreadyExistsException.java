/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class FileAlreadyExistsException
extends FileSystemException {
    static final long serialVersionUID = 7579540934498831181L;

    public FileAlreadyExistsException(String string) {
        super(string);
    }

    public FileAlreadyExistsException(String string, String string2, String string3) {
        super(string, string2, string3);
    }
}

