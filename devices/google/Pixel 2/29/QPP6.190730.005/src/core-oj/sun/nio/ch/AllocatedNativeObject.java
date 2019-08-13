/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import sun.misc.Unsafe;
import sun.nio.ch.NativeObject;

class AllocatedNativeObject
extends NativeObject {
    AllocatedNativeObject(int n, boolean bl) {
        super(n, bl);
    }

    void free() {
        synchronized (this) {
            if (this.allocationAddress != 0L) {
                unsafe.freeMemory(this.allocationAddress);
                this.allocationAddress = 0L;
            }
            return;
        }
    }
}

