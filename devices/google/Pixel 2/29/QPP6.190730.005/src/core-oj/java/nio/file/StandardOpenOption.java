/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file;

import java.nio.file.OpenOption;

public enum StandardOpenOption implements OpenOption
{
    READ,
    WRITE,
    APPEND,
    TRUNCATE_EXISTING,
    CREATE,
    CREATE_NEW,
    DELETE_ON_CLOSE,
    SPARSE,
    SYNC,
    DSYNC;
    
}

