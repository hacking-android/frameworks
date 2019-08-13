/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.FileDescriptor;

public interface JavaIOFileDescriptorAccess {
    public int get(FileDescriptor var1);

    public long getHandle(FileDescriptor var1);

    public void set(FileDescriptor var1, int var2);

    public void setHandle(FileDescriptor var1, long var2);
}

