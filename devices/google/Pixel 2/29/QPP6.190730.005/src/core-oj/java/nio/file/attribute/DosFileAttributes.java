/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.nio.file.attribute.BasicFileAttributes;

public interface DosFileAttributes
extends BasicFileAttributes {
    public boolean isArchive();

    public boolean isHidden();

    public boolean isReadOnly();

    public boolean isSystem();
}

