/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.io.Serializable;
import java.util.ArrayList;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.WrappedRuntimeException;

public final class IteratorPool
implements Serializable {
    static final long serialVersionUID = -460927331149566998L;
    private final ArrayList m_freeStack;
    private final DTMIterator m_orig;

    public IteratorPool(DTMIterator dTMIterator) {
        this.m_orig = dTMIterator;
        this.m_freeStack = new ArrayList();
    }

    public void freeInstance(DTMIterator dTMIterator) {
        synchronized (this) {
            this.m_freeStack.add(dTMIterator);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public DTMIterator getInstance() {
        synchronized (this) {
            boolean bl = this.m_freeStack.isEmpty();
            if (!bl) {
                return (DTMIterator)this.m_freeStack.remove(this.m_freeStack.size() - 1);
            }
            try {
                return (DTMIterator)this.m_orig.clone();
            }
            catch (Exception exception) {
                WrappedRuntimeException wrappedRuntimeException = new WrappedRuntimeException(exception);
                throw wrappedRuntimeException;
            }
        }
    }

    public DTMIterator getInstanceOrThrow() throws CloneNotSupportedException {
        synchronized (this) {
            block4 : {
                if (!this.m_freeStack.isEmpty()) break block4;
                DTMIterator dTMIterator = (DTMIterator)this.m_orig.clone();
                return dTMIterator;
            }
            DTMIterator dTMIterator = (DTMIterator)this.m_freeStack.remove(this.m_freeStack.size() - 1);
            return dTMIterator;
        }
    }
}

