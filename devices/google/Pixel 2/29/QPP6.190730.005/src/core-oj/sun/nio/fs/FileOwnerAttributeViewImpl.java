/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.AclFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.attribute.FileOwnerAttributeView;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.HashMap;
import java.util.Map;
import sun.nio.fs.DynamicFileAttributeView;

final class FileOwnerAttributeViewImpl
implements FileOwnerAttributeView,
DynamicFileAttributeView {
    private static final String OWNER_NAME = "owner";
    private final boolean isPosixView;
    private final FileAttributeView view;

    FileOwnerAttributeViewImpl(AclFileAttributeView aclFileAttributeView) {
        this.view = aclFileAttributeView;
        this.isPosixView = false;
    }

    FileOwnerAttributeViewImpl(PosixFileAttributeView posixFileAttributeView) {
        this.view = posixFileAttributeView;
        this.isPosixView = true;
    }

    @Override
    public UserPrincipal getOwner() throws IOException {
        if (this.isPosixView) {
            return ((PosixFileAttributeView)this.view).readAttributes().owner();
        }
        return ((AclFileAttributeView)this.view).getOwner();
    }

    @Override
    public String name() {
        return OWNER_NAME;
    }

    @Override
    public Map<String, Object> readAttributes(String[] object) throws IOException {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        int n = ((String[])object).length;
        for (int i = 0; i < n; ++i) {
            String string = object[i];
            if (!string.equals("*") && !string.equals(OWNER_NAME)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(this.name());
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("' not recognized");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            hashMap.put(OWNER_NAME, this.getOwner());
        }
        return hashMap;
    }

    @Override
    public void setAttribute(String string, Object object) throws IOException {
        if (string.equals(OWNER_NAME)) {
            this.setOwner((UserPrincipal)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("'");
        ((StringBuilder)object).append(this.name());
        ((StringBuilder)object).append(":");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("' not recognized");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public void setOwner(UserPrincipal userPrincipal) throws IOException {
        if (this.isPosixView) {
            ((PosixFileAttributeView)this.view).setOwner(userPrincipal);
        } else {
            ((AclFileAttributeView)this.view).setOwner(userPrincipal);
        }
    }
}

