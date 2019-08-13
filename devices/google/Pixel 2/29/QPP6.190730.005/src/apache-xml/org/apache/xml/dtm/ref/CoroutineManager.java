/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.util.BitSet;
import org.apache.xml.res.XMLMessages;

public class CoroutineManager {
    static final int ANYBODY = -1;
    static final int NOBODY = -1;
    static final int m_unreasonableId = 1024;
    BitSet m_activeIDs = new BitSet();
    int m_nextCoroutine = -1;
    Object m_yield = null;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object co_entry_pause(int n) throws NoSuchMethodException {
        synchronized (this) {
            if (!this.m_activeIDs.get(n)) {
                NoSuchMethodException noSuchMethodException = new NoSuchMethodException();
                throw noSuchMethodException;
            }
            int n2;
            while ((n2 = this.m_nextCoroutine) != n) {
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {
                }
            }
            return this.m_yield;
        }
    }

    public void co_exit(int n) {
        synchronized (this) {
            this.m_activeIDs.clear(n);
            this.m_nextCoroutine = -1;
            this.notify();
            return;
        }
    }

    public void co_exit_to(Object object, int n, int n2) throws NoSuchMethodException {
        synchronized (this) {
            if (this.m_activeIDs.get(n2)) {
                this.m_yield = object;
                this.m_nextCoroutine = n2;
                this.m_activeIDs.clear(n);
                this.notify();
                return;
            }
            object = new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_NOT_AVAIL", new Object[]{Integer.toString(n2)}));
            throw object;
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public int co_joinCoroutineSet(int var1_1) {
        // MONITORENTER : this
        if (var1_1 < 0) ** GOTO lbl-1000
        if (var1_1 >= 1024) {
            // MONITOREXIT : this
            return -1;
        }
        var2_2 = this.m_activeIDs.get(var1_1);
        var3_4 = var1_1;
        if (var2_2) {
            return -1;
        }
        ** GOTO lbl-1000
lbl-1000: // 2 sources:
        {
            ** for (var1_1 = 0;
            ; var1_1 < 1024 && (var2_3 = this.m_activeIDs.get((int)var1_1)); ++var1_1)
        }
lbl-1000: // 1 sources:
        {
            continue;
        }
lbl16: // 1 sources:
        var3_4 = var1_1;
        if (var1_1 < 1024) ** GOTO lbl-1000
        // MONITOREXIT : this
        return -1;
lbl-1000: // 2 sources:
        {
            this.m_activeIDs.set(var3_4);
            // MONITOREXIT : this
            return var3_4;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Object co_resume(Object object, int n, int n2) throws NoSuchMethodException {
        synchronized (this) {
            void var3_4;
            if (!this.m_activeIDs.get((int)var3_4)) {
                object = new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_NOT_AVAIL", new Object[]{Integer.toString((int)var3_4)}));
                throw object;
            }
            this.m_yield = object;
            this.m_nextCoroutine = var3_4;
            this.notify();
            do {
                void var2_3;
                if (this.m_nextCoroutine == var2_3 && this.m_nextCoroutine != -1 && this.m_nextCoroutine != -1) {
                    if (this.m_nextCoroutine != -1) {
                        return this.m_yield;
                    }
                    this.co_exit((int)var2_3);
                    object = new NoSuchMethodException(XMLMessages.createXMLMessage("ER_COROUTINE_CO_EXIT", null));
                    throw object;
                }
                try {
                    this.wait();
                }
                catch (InterruptedException interruptedException) {
                }
            } while (true);
        }
    }
}

