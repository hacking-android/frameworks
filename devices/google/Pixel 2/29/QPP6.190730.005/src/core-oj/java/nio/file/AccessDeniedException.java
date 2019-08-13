/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class AccessDeniedException
extends FileSystemException {
    private static final long serialVersionUID = 4943049599949219617L;

    public AccessDeniedException(String string) {
        super(string);
    }

    public AccessDeniedException(String string, String string2, String string3) {
        super(string, string2, string3);
    }
}

