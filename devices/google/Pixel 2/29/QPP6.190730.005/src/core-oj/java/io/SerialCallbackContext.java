/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.NotActiveException;
import java.io.ObjectStreamClass;

final class SerialCallbackContext {
    private final ObjectStreamClass desc;
    private final Object obj;
    private Thread thread;

    public SerialCallbackContext(Object object, ObjectStreamClass objectStreamClass) {
        this.obj = object;
        this.desc = objectStreamClass;
        this.thread = Thread.currentThread();
    }

    private void checkAndSetUsed() throws NotActiveException {
        if (this.thread == Thread.currentThread()) {
            this.thread = null;
            return;
        }
        throw new NotActiveException("not in readObject invocation or fields already read");
    }

    public void check() throws NotActiveException {
        Object object = this.thread;
        if (object != null && object != Thread.currentThread()) {
            object = new StringBuilder();
            ((StringBuilder)object).append("expected thread: ");
            ((StringBuilder)object).append(this.thread);
            ((StringBuilder)object).append(", but got: ");
            ((StringBuilder)object).append(Thread.currentThread());
            throw new NotActiveException(((StringBuilder)object).toString());
        }
    }

    public ObjectStreamClass getDesc() {
        return this.desc;
    }

    public Object getObj() throws NotActiveException {
        this.checkAndSetUsed();
        return this.obj;
    }

    public void setUsed() {
        this.thread = null;
    }
}

