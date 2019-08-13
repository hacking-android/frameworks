/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.AclEntry;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.util.List;

public interface AclFileAttributeView
extends FileOwnerAttributeView {
    public List<AclEntry> getAcl() throws IOException;

    @Override
    public String name();

    public void setAcl(List<AclEntry> var1) throws IOException;
}

