/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.io.IOException;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.UserPrincipal;

public interface FileOwnerAttributeView
extends FileAttributeView {
    public UserPrincipal getOwner() throws IOException;

    @Override
    public String name();

    public void setOwner(UserPrincipal var1) throws IOException;
}

