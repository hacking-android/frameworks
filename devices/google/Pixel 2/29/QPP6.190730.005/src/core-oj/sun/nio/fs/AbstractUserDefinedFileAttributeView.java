/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import sun.nio.fs.DynamicFileAttributeView;

abstract class AbstractUserDefinedFileAttributeView
implements UserDefinedFileAttributeView,
DynamicFileAttributeView {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    protected AbstractUserDefinedFileAttributeView() {
    }

    protected void checkAccess(String string, boolean bl, boolean bl2) {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            if (bl) {
                securityManager.checkRead(string);
            }
            if (bl2) {
                securityManager.checkWrite(string);
            }
            securityManager.checkPermission(new RuntimePermission("accessUserDefinedAttributes"));
        }
    }

    @Override
    public final String name() {
        return "user";
    }

    @Override
    public final Map<String, Object> readAttributes(String[] arrobject) throws IOException {
        Cloneable cloneable;
        int n;
        int n2;
        Object object;
        block4 : {
            cloneable = new ArrayList();
            n2 = arrobject.length;
            n = 0;
            do {
                object = cloneable;
                if (n >= n2) break block4;
                object = arrobject[n];
                if (((String)object).equals("*")) {
                    object = this.list();
                    break block4;
                }
                if (((String)object).length() == 0) break;
                cloneable.add(object);
                ++n;
            } while (true);
            throw new IllegalArgumentException();
        }
        cloneable = new HashMap();
        Iterator iterator = object.iterator();
        while (iterator.hasNext()) {
            object = (String)iterator.next();
            n2 = this.read((String)object, ByteBuffer.wrap((byte[])(arrobject = new byte[n = this.size((String)object)])));
            if (n2 != n) {
                arrobject = Arrays.copyOf((byte[])arrobject, n2);
            }
            cloneable.put(object, arrobject);
        }
        return cloneable;
    }

    @Override
    public final void setAttribute(String string, Object object) throws IOException {
        object = object instanceof byte[] ? ByteBuffer.wrap((byte[])object) : (ByteBuffer)object;
        this.write(string, (ByteBuffer)object);
    }
}

