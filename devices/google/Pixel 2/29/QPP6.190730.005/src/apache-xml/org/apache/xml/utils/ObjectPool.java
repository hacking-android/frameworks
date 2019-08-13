/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.ObjectFactory;
import org.apache.xml.utils.WrappedRuntimeException;

public class ObjectPool
implements Serializable {
    static final long serialVersionUID = -8519013691660936643L;
    private final ArrayList freeStack;
    private final Class objectType;

    public ObjectPool() {
        this.objectType = null;
        this.freeStack = new ArrayList();
    }

    public ObjectPool(Class class_) {
        this.objectType = class_;
        this.freeStack = new ArrayList();
    }

    public ObjectPool(Class class_, int n) {
        this.objectType = class_;
        this.freeStack = new ArrayList(n);
    }

    public ObjectPool(String string) {
        try {
            this.objectType = ObjectFactory.findProviderClass(string, ObjectFactory.findClassLoader(), true);
            this.freeStack = new ArrayList();
            return;
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new WrappedRuntimeException(classNotFoundException);
        }
    }

    public void freeInstance(Object object) {
        synchronized (this) {
            this.freeStack.add(object);
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Object getInstance() {
        synchronized (this) {
            boolean bl = this.freeStack.isEmpty();
            if (!bl) return this.freeStack.remove(this.freeStack.size() - 1);
            try {
                return this.objectType.newInstance();
            }
            catch (IllegalAccessException illegalAccessException) {
            }
            catch (InstantiationException instantiationException) {
                // empty catch block
            }
            RuntimeException runtimeException = new RuntimeException(XMLMessages.createXMLMessage("ER_EXCEPTION_CREATING_POOL", null));
            throw runtimeException;
        }
    }

    public Object getInstanceIfFree() {
        synchronized (this) {
            if (!this.freeStack.isEmpty()) {
                Object e = this.freeStack.remove(this.freeStack.size() - 1);
                return e;
            }
            return null;
        }
    }
}

