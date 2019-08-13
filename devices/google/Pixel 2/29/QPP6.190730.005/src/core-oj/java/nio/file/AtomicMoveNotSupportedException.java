/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class AtomicMoveNotSupportedException
extends FileSystemException {
    static final long serialVersionUID = 5402760225333135579L;

    public AtomicMoveNotSupportedException(String string, String string2, String string3) {
        super(string, string2, string3);
    }
}

