/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.coll;

import android.icu.util.ICUCloneNotSupportedException;
import java.util.concurrent.atomic.AtomicInteger;

public class SharedObject
implements Cloneable {
    private AtomicInteger refCount = new AtomicInteger();

    public final void addRef() {
        this.refCount.incrementAndGet();
    }

    public SharedObject clone() {
        try {
            SharedObject sharedObject = (SharedObject)super.clone();
            sharedObject.refCount = new AtomicInteger();
            return sharedObject;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    public final void deleteIfZeroRefCount() {
    }

    public final int getRefCount() {
        return this.refCount.get();
    }

    public final void removeRef() {
        this.refCount.decrementAndGet();
    }

    public static final class Reference<T extends SharedObject>
    implements Cloneable {
        private T ref;

        public Reference(T t) {
            this.ref = t;
            if (t != null) {
                ((SharedObject)t).addRef();
            }
        }

        public void clear() {
            T t = this.ref;
            if (t != null) {
                ((SharedObject)t).removeRef();
                this.ref = null;
            }
        }

        public Reference<T> clone() {
            try {
                Reference reference = (Reference)super.clone();
                T t = this.ref;
                if (t != null) {
                    ((SharedObject)t).addRef();
                }
                return reference;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new ICUCloneNotSupportedException(cloneNotSupportedException);
            }
        }

        public T copyOnWrite() {
            T t = this.ref;
            if (((SharedObject)t).getRefCount() <= 1) {
                return t;
            }
            SharedObject sharedObject = ((SharedObject)t).clone();
            ((SharedObject)t).removeRef();
            this.ref = sharedObject;
            sharedObject.addRef();
            return (T)sharedObject;
        }

        protected void finalize() throws Throwable {
            super.finalize();
            this.clear();
        }

        public T readOnly() {
            return this.ref;
        }
    }

}

