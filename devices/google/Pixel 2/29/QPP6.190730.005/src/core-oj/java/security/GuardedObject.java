/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.Guard;

public class GuardedObject
implements Serializable {
    private static final long serialVersionUID = -5240450096227834308L;
    private Guard guard;
    private Object object;

    public GuardedObject(Object object, Guard guard) {
        this.guard = guard;
        this.object = object;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        Guard guard = this.guard;
        if (guard != null) {
            guard.checkGuard(this.object);
        }
        objectOutputStream.defaultWriteObject();
    }

    public Object getObject() throws SecurityException {
        Guard guard = this.guard;
        if (guard != null) {
            guard.checkGuard(this.object);
        }
        return this.object;
    }
}

