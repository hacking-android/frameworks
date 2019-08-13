/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.FileSystemException;

public class NotLinkException
extends FileSystemException {
    static final long serialVersionUID = -388655596416518021L;

    public NotLinkException(String string) {
        super(string);
    }

    public NotLinkException(String string, String string2, String string3) {
        super(string, string2, string3);
    }
}

